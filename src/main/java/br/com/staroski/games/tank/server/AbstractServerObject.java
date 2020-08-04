package br.com.staroski.games.tank.server;

import java.awt.Rectangle;
import java.rmi.RemoteException;

import br.com.staroski.games.Bounds;
import br.com.staroski.games.Cinetic;
import br.com.staroski.games.GameSettings;
import br.com.staroski.games.Point;
import br.com.staroski.games.ServerObject;
import br.com.staroski.games.Shape;
import br.com.staroski.games.Solid;

abstract class AbstractServerObject implements ServerObject {

	private final Solid solidPart;

	private final Cinetic cineticPart;

	private final long id;

	protected AbstractServerObject(long id) {
		this.id = id;
		solidPart = new SolidObject();
		cineticPart = new CineticObject();
	}
	public double getAcceleration() throws RemoteException {
		return cineticPart.getAcceleration();
	}

	public Bounds getBounds() throws RemoteException {
		return solidPart.getBounds();
	}

	public double getDirection() throws RemoteException {
		return cineticPart.getDirection();
	}

	public long getId() {
		return id;
	}

	public Point getLocation() throws RemoteException {
		return cineticPart.getLocation();
	}

	public double getMaxSpeed() throws RemoteException {
		return cineticPart.getMaxSpeed();
	}

	public double getMinSpeed() throws RemoteException {
		return cineticPart.getMinSpeed();
	}

	public Shape getShape() throws RemoteException {
		return solidPart.getShape();
	}

	public double getSpeed() throws RemoteException {
		return cineticPart.getSpeed();
	}

	public double getTurnSpeed() throws RemoteException {
		return cineticPart.getTurnSpeed();
	}

	public boolean isAccessible() throws RemoteException {
		return true;
	}

	public void setAcceleration(double acceleration) throws RemoteException {
		cineticPart.setAcceleration(acceleration);
	}

	public void setBounds(Bounds bounds) throws RemoteException {
		solidPart.setBounds(bounds);
	}

	public void setDirection(double direction) throws RemoteException {
		cineticPart.setDirection(direction);
		setShape(getShape(direction));
	}

	public void setLocation(Point location) throws RemoteException {
		Shape shape = getShape();
		shape = shape.setLocation(location);
		shape = checkBounds(shape);
		cineticPart.setLocation(shape.getCenter());
		setShape(shape);
	}

	public void setMaxSpeed(double speed) throws RemoteException {
		cineticPart.setMaxSpeed(speed);
	}

	public void setMinSpeed(double speed) throws RemoteException {
		cineticPart.setMinSpeed(speed);
	}

	public void setShape(Shape shape) throws RemoteException {
		shape = shape.setLocation(getLocation());
		solidPart.setShape(shape);
		solidPart.setBounds(shape.getBounds());
	}

	public void setSpeed(double speed) throws RemoteException {
		cineticPart.setSpeed(speed);
	}

	public void setTurnSpeed(double turnSpeed) throws RemoteException {
		cineticPart.setTurnSpeed(turnSpeed);
	}

	protected Shape checkBounds(Shape shape) throws RemoteException {
		GameSettings settings = TankServer.get().getSettings();
		int worldX1 = 0;
		int worldY1 = 0;
		int worldX2 = settings.getWidth();
		int worldY2 = settings.getHeigth();

		Rectangle bounds = shape.getBounds().toAwtRectangle();
		int centerX = bounds.width / 2;
		int centerY = bounds.height / 2;
		int x1 = bounds.x;
		int x2 = bounds.x + bounds.width;
		int y1 = bounds.y;
		int y2 = bounds.y + bounds.height;
		Point p = shape.getCenter();

		boolean adjust = false;
		// esquerda
		if (x1 < worldX1) {
			p = new Point(worldX1 + centerX, p.y);
			adjust = true;
		}
		// direita
		if (x2 > worldX2) {
			p = new Point(worldX2 - centerX, p.y);
			adjust = true;
		}
		// superior
		if (y1 < worldY1) {
			p = new Point(p.x, worldY1 + centerY);
			adjust = true;
		}
		// inferior
		if (y2 > worldY2) {
			p = new Point(p.x, worldY2 - centerY);
			adjust = true;
		}
		if (adjust) {
			shape = shape.setLocation(p);
		}
		return shape;
	}

	protected abstract Shape getShape(double direction);

	protected void removeMyself() throws RemoteException {
		TankServer.get().getScreen().removeObject(this);
	}
}
