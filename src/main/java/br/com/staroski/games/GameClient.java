package br.com.staroski.games;

import java.rmi.*;

public interface GameClient extends ClientListener, RemoteAccessible {

	Player getPlayer() throws RemoteException;

	Screen getScreen() throws RemoteException;
	
	GameSettings getSettings() throws RemoteException;

	void setPlayer(Player player) throws RemoteException;
}