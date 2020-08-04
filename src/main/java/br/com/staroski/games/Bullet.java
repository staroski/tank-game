package br.com.staroski.games;

import java.rmi.*;

public interface Bullet extends Cinetic {

	ServerObject getOwner() throws RemoteException;

	double getPower() throws RemoteException;

	double getRange() throws RemoteException;

	void setOwner(ServerObject object) throws RemoteException;

	void setPower(double power) throws RemoteException;

	void setRange(double range) throws RemoteException;
}
