package br.com.staroski.games;

import java.rmi.RemoteException;

public interface Player extends ServerObject, Armed {

    public double getLife() throws RemoteException;

    public double getMaxLife() throws RemoteException;

    public String getName() throws RemoteException;

    public void setLife(double life) throws RemoteException;

    public void setMaxLife(double maxLife) throws RemoteException;
}
