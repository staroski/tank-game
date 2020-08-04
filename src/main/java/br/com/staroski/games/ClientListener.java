package br.com.staroski.games;

import java.rmi.*;

public interface ClientListener extends Remote {

    void onDraw() throws RemoteException;

    void onInput() throws RemoteException;
}
