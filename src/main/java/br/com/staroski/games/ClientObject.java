package br.com.staroski.games;

import java.rmi.*;

public interface ClientObject extends Solid, Cinetic, Renderizable, ClientListener {

	Renderer getRenderer() throws RemoteException;

	void setRenderer(Renderer renderer) throws RemoteException;
}
