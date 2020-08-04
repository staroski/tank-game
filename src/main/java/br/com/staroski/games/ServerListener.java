package br.com.staroski.games;

import java.rmi.*;

public interface ServerListener extends Remote {

    void onInput(GameInput input) throws RemoteException;

    void onUpdate(GameEvent event) throws RemoteException;
}
