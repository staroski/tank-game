package br.com.staroski.games;

import java.rmi.*;

public interface Solid extends Remote {

    Bounds getBounds() throws RemoteException;

    Shape getShape() throws RemoteException;

    void setBounds(Bounds bounds) throws RemoteException;

    void setShape(Shape shape) throws RemoteException;
}
