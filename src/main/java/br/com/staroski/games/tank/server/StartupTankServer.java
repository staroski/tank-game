package br.com.staroski.games.tank.server;

import java.rmi.RemoteException;

import br.com.staroski.games.DefaultGameEngine;
import br.com.staroski.games.GameEngine;
import br.com.staroski.games.GameServer;

public final class StartupTankServer {

	public static void main(String[] args) {
		try {
			new StartupTankServer().execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	private StartupTankServer() {
	}

	private void execute() throws RemoteException {
		GameServer server = TankServer.get();
		GameEngine engine = new DefaultGameEngine(server);
		engine.startup();
	}
}
