package br.com.staroski.games.tank.server;

import java.rmi.RemoteException;

import br.com.staroski.games.Player;

abstract class AbstractPlayer extends AbstractServerObject implements Player {

    private final String name;
    private double life;
    private double maxLife;
    private double gunHeading;

    AbstractPlayer(String name, long id) {
        super(id);
        this.name = name;
    }

    @Override
    public double getGunHeading() {
        return gunHeading;
    }

    @Override
    public double getLife() throws RemoteException {
        return life;
    }

    @Override
    public double getMaxLife() throws RemoteException {
        return maxLife;
    }

    @Override
    public final String getName() throws RemoteException {
        return name;
    }

    @Override
    public void setGunHeading(double angle) throws RemoteException {
        this.gunHeading = angle;
    }

    @Override
    public void setLife(double life) throws RemoteException {
        this.life = life < 0 ? 0 : life > maxLife ? maxLife : life;
    }

    @Override
    public void setMaxLife(double maxLife) throws RemoteException {
        this.maxLife = maxLife;
    }

}
