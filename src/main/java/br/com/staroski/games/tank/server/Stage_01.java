package br.com.staroski.games.tank.server;

import java.rmi.RemoteException;

import br.com.staroski.games.IdGenerator;
import br.com.staroski.games.tank.Resources;

class Stage_01 extends AbstractScreen {

    private final long id;

    Stage_01() {
        id = IdGenerator.newId();
    }

    @Override
    public long getId() throws RemoteException {
        return id;
    }

    @Override
    public int getRendererId() {
        return Resources.STAGE_01.getId();
    }

    public boolean isAccessible() throws RemoteException {
        return true;
    }
}
