package br.com.staroski.games;

import java.rmi.*;

public interface ServerObject extends Solid, Cinetic, Renderizable, ServerListener, RemoteAccessible {

	void onCollision(ServerObject other) throws RemoteException;
}
