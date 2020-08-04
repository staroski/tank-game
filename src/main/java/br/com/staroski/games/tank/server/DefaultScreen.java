package br.com.staroski.games.tank.server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.staroski.games.GameEvent;
import br.com.staroski.games.GameInput;
import br.com.staroski.games.GameServer;
import br.com.staroski.games.GameSettings;
import br.com.staroski.games.ObjectBinder;
import br.com.staroski.games.Screen;
import br.com.staroski.games.ServerObject;

abstract class AbstractScreen implements Screen {

    private final List<ServerObject> objectsToInsert;
    private final List<Long> objectsToRemove;
    private final Map<Long, ServerObject> objects;

    public AbstractScreen() {
        objectsToInsert = new ArrayList<ServerObject>();
        objectsToRemove = new ArrayList<Long>();
        objects = new HashMap<Long, ServerObject>();
    }

    @Override
    public void addObject(ServerObject object) throws RemoteException {
        GameServer server = TankServer.get();
        GameSettings settings = server.getSettings();
        ObjectBinder locator = ObjectBinder.get();
        locator.bindObject(ObjectBinder.getLocalHost(), settings, object);
        objectsToInsert.add(object);
    }

    @Override
    public ServerObject[] getObjects() throws RemoteException {
        Collection<ServerObject> objects = this.objects.values();
        int size = objects.size();
        ServerObject[] array = new ServerObject[size];
        objects.toArray(array);
        return array;
    }

    @Override
    public void onInput(GameInput input) throws RemoteException {
        for (ServerObject object : getObjects()) {
            try {
                if (object.isAccessible()) {
                    object.onInput(input);
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                try {
                    removeObject(object);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onUpdate(GameEvent event) throws RemoteException {
        checkObjects();
        for (ServerObject object : getObjects()) {
            try {
                if (object.isAccessible()) {
                    object.onUpdate(event);
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                try {
                    removeObject(object);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    @Override
    public void removeObject(ServerObject object) throws RemoteException {
        objectsToRemove.add(Long.valueOf(object.getId()));
        ObjectBinder locator = ObjectBinder.get();
        GameServer server = TankServer.get();
        GameSettings settings = server.getSettings();
        locator.unbindObject(ObjectBinder.getLocalHost(), settings, object);
    }

    private void checkObjects() {
        Long key = null;
        ServerObject object;
        List<Long> objectsToRemove = this.objectsToRemove;
        while (!objectsToRemove.isEmpty()) {
            try {
                key = objectsToRemove.remove(0);
                objects.remove(key);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        List<ServerObject> objectsToInsert = this.objectsToInsert;
        if (!objectsToInsert.isEmpty()) {
            try {
                object = objectsToInsert.remove(0);
                key = Long.valueOf(object.getId());
                objects.put(key, object);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
