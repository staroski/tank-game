package br.com.staroski.games;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class DefaultGameEngine implements GameEngine {

    private boolean started;
    private boolean running;
    private final List<GameClient> clientsToAdd;
    private final List<Long> clientsToRemove;
    private final Map<Long, GameClient> clients;
    private final GameServer server;
    private final long id;

    public DefaultGameEngine(GameServer server) throws RemoteException {
        this.id = IdGenerator.newId();
        this.server = server;
        this.clientsToAdd = new LinkedList<GameClient>();
        this.clientsToRemove = new LinkedList<Long>();
        this.clients = new HashMap<Long, GameClient>();
    }

    @Override
    public void addClient(final GameClient client, String username, String password) throws RemoteException {
        synchronized (this) {
            Player player = server.login(username, password);
            getScreen().addObject(player);
            client.setPlayer(player);
            clientsToAdd.add(client);
        }
    }

    @Override
    public long getId() throws RemoteException {
        return id;
    }

    @Override
    public Screen getScreen() throws RemoteException {
        return server.getScreen();
    }

    @Override
    public GameSettings getSettings() throws RemoteException {
        return server.getSettings();
    }

    public boolean isAccessible() throws RemoteException {
        return true;
    }

    @Override
    public void removeClient(final GameClient client) throws RemoteException {
        synchronized (this) {
            Player player = client.getPlayer();
            server.logout(player);
            Screen screen = server.getScreen();
            screen.removeObject(player);
            clientsToRemove.add(Long.valueOf(player.getId()));
        }
    }

    @Override
    public void shutdown() throws RemoteException {
        running = false;
        started = false;
    }

    @Override
    public void startup() throws RemoteException {
        running = true;

        GameSettings settings = getSettings();

        ObjectBinder binder = ObjectBinder.get();
        binder.bindEngine(settings, this);

        Screen screen = server.getScreen();
        binder.bindScreen(ObjectBinder.getLocalHost(), settings, screen);

        execute();
    }

    private void checkClients() {
        Map<Long, GameClient> clients = this.clients;
        Long key = null;
        GameClient client = null;
        List<Long> clientsToRemove = this.clientsToRemove;
        while (!clientsToRemove.isEmpty()) {
            try {
                key = clientsToRemove.remove(0);
                clients.remove(key);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        List<GameClient> clientsToAdd = this.clientsToAdd;
        while (!clientsToAdd.isEmpty()) {
            try {
                client = clientsToAdd.remove(0);
                key = Long.valueOf(client.getPlayer().getId());
                clients.put(key, client);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void execute() throws RemoteException {
        try {
            final int ONE_SECOND = 1000000000; // nanos

            final int updatesPerSecond = 50;
            final int maxFrameSkip = 5;
            final int updateInterval = ONE_SECOND / updatesPerSecond;
            long nextTick = tick();

            long start = nextTick;

            int fps = 0;
            int ups = 0;
            int frames = 0;
            int updates = 0;
            long last = 0;
            while (running) {
                synchronized (this) {
                    checkClients();
                }
                int loop = 0;
                while (tick() > nextTick && loop < maxFrameSkip) {
                    // processar entrada do usuario
                    onInput();

                    // atualizar estado do jogo
                    final double delta = (tick() - last) / (double) ONE_SECOND;
                    onUpdate(delta, ups, fps);

                    nextTick += updateInterval;
                    // incrementar frame
                    loop++;
                    last = tick();
                    updates++;
                }
                frames++;

                // compute FPS and UPS
                if (tick() - start > ONE_SECOND) {
                    fps = frames;
                    ups = updates;
                    frames = 0;
                    updates = 0;
                    start = tick();
                }
                onDraw();

                started = true; // s� passa a notificar os clients a partir da segunda itera��o}
            }
        } catch (final Throwable t) {
            t.printStackTrace();
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private Entry<Long, GameClient>[] getClients() throws RemoteException {
        Set<Entry<Long, GameClient>> clients = this.clients.entrySet();
        int size = clients.size();
        Entry[] array = new Entry[size];
        clients.toArray(array);
        return (Entry<Long, GameClient>[]) array;
    }

    private void onDraw() throws RemoteException {
        if (started) {
            for (final Entry<Long, GameClient> entry : getClients()) {
                final Long id = entry.getKey();
                final GameClient client = entry.getValue();
                try {
                    if (client.isAccessible()) {
                        client.onDraw();
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                    try {
                        removeClient(client);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        this.clients.remove(id);
                    }
                }
            }
        }
    }

    private void onInput() throws RemoteException {
        if (started) {
            for (final Entry<Long, GameClient> entry : getClients()) {
                final Long id = entry.getKey();
                final GameClient client = entry.getValue();
                try {
                    if (client.isAccessible()) {
                        client.onInput();
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                    try {
                        removeClient(client);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        this.clients.remove(id);
                    }
                }
            }
        }
    }

    private void onUpdate(final double time, final int ups, final int fps) throws RemoteException {
        if (started) {
            final GameEvent event = new GameEvent(time, ups, fps);
            // System.out.println(event);
            Screen screen = server.getScreen();
            screen.onUpdate(event);
            new CollisionHandler().checkCollisions(screen.getObjects());
        }
    }

    private long tick() {
        return System.nanoTime();
    }
}
