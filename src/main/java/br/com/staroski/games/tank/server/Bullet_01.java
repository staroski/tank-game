package br.com.staroski.games.tank.server;

import static br.com.staroski.games.CGUtils.getDistance;
import static br.com.staroski.games.CGUtils.movePoint;

import java.rmi.RemoteException;

import br.com.staroski.games.Bullet;
import br.com.staroski.games.GameEvent;
import br.com.staroski.games.GameInput;
import br.com.staroski.games.Player;
import br.com.staroski.games.Point;
import br.com.staroski.games.ServerObject;
import br.com.staroski.games.Shape;
import br.com.staroski.games.tank.Resources;

final class Bullet_01 extends AbstractServerObject implements Bullet {

    private final ServerObject owner;
    private Point origin;
    private double range;
    private double power;
    private double distanceMoved;

    protected Bullet_01(long id, ServerObject owner, double direction) throws RemoteException {
        super(id);
        this.owner = owner;

        setDirection(direction);
        Point p = owner.getLocation();
        origin = new Point(p);
        setLocation(new Point(p));
    }

    @Override
    public ServerObject getOwner() throws RemoteException {
        return owner;
    }

    @Override
    public double getPower() throws RemoteException {
        return power;
    }

    @Override
    public double getRange() throws RemoteException {
        return range;
    }

    @Override
    public int getRendererId() throws RemoteException {
        return Resources.BULLET_01.getId();
    }

    @Override
    public void onCollision(ServerObject other) throws RemoteException {
        if (other == owner) {
            return;
        }
        if (other instanceof Player) {
            removeMyself();
        }
    }

    @Override
    public void onInput(GameInput input) throws RemoteException {
        // faz nada
    }

    @Override
    public void onUpdate(GameEvent evt) throws RemoteException {
        Point location = getLocation();
        distanceMoved = getDistance(origin, location);
        if (distanceMoved < range) {
            double distance = getSpeed() * evt.time;
            setLocation(movePoint(location, distance, getDirection()));
        } else {
            removeMyself();
        }
    }

    @Override
    public void setOwner(ServerObject object) throws RemoteException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setPower(double power) throws RemoteException {
        this.power = power;
    }

    @Override
    public void setRange(double range) throws RemoteException {
        this.range = range;
    }

    @Override
    protected Shape checkBounds(Shape shape) throws RemoteException {
        return shape;
    }

    @Override
    protected Shape getShape(double direction) {
        return Resources.BULLET_01.getShape(direction);
    }
}
