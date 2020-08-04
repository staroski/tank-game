package br.com.staroski.games.tank.server;

import java.rmi.RemoteException;

import br.com.staroski.games.Cinetic;
import br.com.staroski.games.Point;

class CineticObject implements Cinetic {

	private Point location = new Point(0, 0);
	private double minSpeed;
	private double maxSpeed;
	private double speed;
	private double direction;
	private double acceleration;
	private double turnSpeed;

	@Override
	public double getAcceleration() throws RemoteException {
		return acceleration;
	}

	@Override
	public double getDirection() throws RemoteException {
		return direction;
	}

	@Override
	public Point getLocation() throws RemoteException {
		return location;
	}

	@Override
	public double getMaxSpeed() throws RemoteException {
		return maxSpeed;
	}

	@Override
	public double getMinSpeed() throws RemoteException {
		return minSpeed;
	}

	@Override
	public double getSpeed() throws RemoteException {
		return speed;
	}

	@Override
	public double getTurnSpeed() throws RemoteException {
		return turnSpeed;
	}

	@Override
	public void setAcceleration(double acceleration) throws RemoteException {
		this.acceleration = acceleration;
	}

	@Override
	public void setDirection(double direction) throws RemoteException {
		this.direction = direction;
	}

	@Override
	public void setLocation(Point location) throws RemoteException {
		this.location = location;
	}

	@Override
	public void setMaxSpeed(double speed) throws RemoteException {
		this.maxSpeed = speed;
	}

	@Override
	public void setMinSpeed(double speed) throws RemoteException {
		this.minSpeed = speed;
	}

	@Override
	public void setSpeed(double speed) throws RemoteException {
		this.speed = speed;
	}

	@Override
	public void setTurnSpeed(double turnSpeed) throws RemoteException {
		this.turnSpeed = turnSpeed;

	}

}
