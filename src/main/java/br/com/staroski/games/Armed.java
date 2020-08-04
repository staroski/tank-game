package br.com.staroski.games;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Armed extends Remote {

    public double getGunHeading() throws RemoteException;
    
    public void setGunHeading(double angle) throws RemoteException;
}
