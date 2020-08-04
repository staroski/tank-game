package br.com.staroski.games;

import java.rmi.*;

public interface GameServer {

	Screen getScreen() throws RemoteException;

	GameSettings getSettings() throws RemoteException;

	Player login(String username, String password) throws RemoteException;

	void logout(Player player) throws RemoteException;
}
