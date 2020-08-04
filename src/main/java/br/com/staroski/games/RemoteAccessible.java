package br.com.staroski.games;

import java.rmi.*;

public interface RemoteAccessible extends Remote {

	long getId() throws RemoteException;
	
	boolean isAccessible() throws RemoteException;
}
