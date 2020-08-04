package br.com.staroski.games;

import java.rmi.*;

public interface Cinetic extends Remote {

    double getAcceleration() throws RemoteException;

    double getDirection() throws RemoteException;

    Point getLocation() throws RemoteException;

    double getMaxSpeed() throws RemoteException;

    double getMinSpeed() throws RemoteException;

    double getSpeed() throws RemoteException;

    double getTurnSpeed() throws RemoteException;

    void setAcceleration(double acceleration) throws RemoteException;

    void setDirection(double direction) throws RemoteException;

    void setLocation(Point location) throws RemoteException;

    void setMaxSpeed(double speed) throws RemoteException;

    void setMinSpeed(double speed) throws RemoteException;

    void setSpeed(double speed) throws RemoteException;

    void setTurnSpeed(double turnSpeed) throws RemoteException;
}
