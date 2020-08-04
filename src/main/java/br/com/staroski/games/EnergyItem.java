package br.com.staroski.games;

import java.rmi.*;

public interface EnergyItem extends ServerObject {

	int getEnergy() throws RemoteException;
}
