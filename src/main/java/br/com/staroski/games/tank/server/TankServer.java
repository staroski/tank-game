package br.com.staroski.games.tank.server;

import java.rmi.RemoteException;

import br.com.staroski.games.GameServer;
import br.com.staroski.games.GameSettings;
import br.com.staroski.games.IdGenerator;
import br.com.staroski.games.Player;
import br.com.staroski.games.Screen;
import br.com.staroski.games.tank.Resources;

public final class TankServer implements GameServer {

    private static class Holder {

        private static final TankServer INSTANCE;
        static {
            try {
                INSTANCE = new TankServer();
            } catch (RemoteException e) {
                e.printStackTrace();
                throw new ExceptionInInitializerError(e);
            }
        }
    }

    public static TankServer get() {
        return Holder.INSTANCE;
    }

    private Screen screen;
    private final GameSettings settings;

    private TankServer() throws RemoteException {
        settings = new GameSettings(Resources.GAME_NAME, 1024, 768, 32);
        screen = new Stage_01();
//		ObjectBinder locator = ObjectBinder.get();
//		locator.bindScreen(settings, screen);
    }

    @Override
    public Screen getScreen() throws RemoteException {
        return screen;
    }

    @Override
    public GameSettings getSettings() throws RemoteException {
        return settings;
    }

    @Override
    public Player login(String username, String password) throws RemoteException {
        // TODO fazer login a partir de outro servidor
        return new Player_01(username, IdGenerator.newId());
    }

    @Override
    public void logout(Player player) throws RemoteException {
        //
    }
}
