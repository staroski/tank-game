package br.com.staroski.games;

import java.rmi.*;

public interface Screen extends ServerListener, Renderizable, RemoteAccessible {

	public ServerObject[] getObjects() throws RemoteException;

	void addObject(ServerObject object) throws RemoteException;

	void removeObject(ServerObject object) throws RemoteException;

}
