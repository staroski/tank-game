package br.com.staroski.games;

import java.rmi.*;

public interface Renderizable extends Remote {

    int getRendererId() throws RemoteException;
}
