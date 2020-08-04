package br.com.staroski.games.tank.server;

import java.rmi.RemoteException;

import br.com.staroski.games.Bounds;
import br.com.staroski.games.Shape;
import br.com.staroski.games.Solid;

class SolidObject implements Solid {

	private Shape shape = new Shape();
	private Bounds bounds = new Bounds();

	@Override
	public Bounds getBounds() throws RemoteException {
		return bounds;
	}

	@Override
	public Shape getShape() throws RemoteException {
		return shape;
	}

	@Override
	public void setBounds(Bounds bounds) throws RemoteException {
		this.bounds = new Bounds(bounds);
	}

	@Override
	public void setShape(Shape shape) throws RemoteException {
		this.shape = shape.copy();
	}
}
