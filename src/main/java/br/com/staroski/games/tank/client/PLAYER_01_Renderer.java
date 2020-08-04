package br.com.staroski.games.tank.client;

import static br.com.staroski.games.CGUtils.getDrawPoint;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.rmi.RemoteException;

import br.com.staroski.games.Player;
import br.com.staroski.games.Point;
import br.com.staroski.games.Renderer;
import br.com.staroski.games.Renderizable;
import br.com.staroski.games.Shape;
import br.com.staroski.games.tank.Resources;

final class PLAYER_01_Renderer implements Renderer {

    @Override
    public void render(Renderizable renderizable, Graphics2D g) throws RemoteException {
        Player player = (Player) renderizable;
        double direction = player.getDirection();
        double gunHeading = player.getGunHeading();
        BufferedImage hullImage = Resources.PLAYER_01.getImage(direction);
        BufferedImage gunImage = Resources.PLAYER_01_GUN.getImage(gunHeading);
        Shape shape = player.getShape();

        Point p;
        int x;
        int y;

        // Hull
        {
            p = getDrawPoint(hullImage, shape);
            x = (int) (p.x + 0.5);
            y = (int) (p.y + 0.5);

            // image
            if (Debugger.showImages) {
                g.drawImage(hullImage, x, y, null);
            }

            // image area
            if (Debugger.showImagesBox) {
                g.setColor(Color.GREEN);
                g.drawRect(x, y, hullImage.getWidth(), hullImage.getHeight());
            }
        }

        // Gun
        {
            p = getDrawPoint(gunImage, shape);
            x = (int) (p.x + 0.5);
            y = (int) (p.y + 0.5);

            // image
            if (Debugger.showImages) {
                g.drawImage(gunImage, x, y, null);
            }

            // image area
            if (Debugger.showImagesBox=false) {
                g.setColor(Color.GREEN);
                g.drawRect(x, y, gunImage.getWidth(), gunImage.getHeight());
            }
        }

        // shape
        if (Debugger.showShapes) {
            g.setColor(Color.YELLOW);
            shape.draw(g);
            Point c = shape.getCenter();
            g.drawRect((int) (c.x + 0.5), (int) (c.y + 0.5), 1, 1);
        }

        // bound box
        if (Debugger.showShapesBox) {
            Rectangle r = shape.getBounds().toAwtRectangle();
            g.setColor(Color.RED);
            g.drawRect(r.x, r.y, r.width, r.height);
        }
    }
}
