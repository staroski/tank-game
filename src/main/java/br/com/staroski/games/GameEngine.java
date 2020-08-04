package br.com.staroski.games;

import java.rmi.*;

public interface GameEngine extends RemoteAccessible {

	void addClient(GameClient client, String username, String password) throws RemoteException;

	Screen getScreen() throws RemoteException;

	GameSettings getSettings() throws RemoteException;

	void removeClient(GameClient client) throws RemoteException;

	void shutdown() throws RemoteException;

	void startup() throws RemoteException;

}