package br.com.staroski.games.tank.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.rmi.RemoteException;

import br.com.staroski.games.GameClient;
import br.com.staroski.games.GameSettings;
import br.com.staroski.games.Player;
import br.com.staroski.games.Point;
import br.com.staroski.games.Renderer;
import br.com.staroski.games.RendererMapper;
import br.com.staroski.games.Renderizable;
import br.com.staroski.games.Screen;
import br.com.staroski.games.ServerObject;
import br.com.staroski.games.tank.Fonts;
import br.com.staroski.games.tank.Resources;

final class STAGE_01_Renderer implements Renderer {

    private Point offset = new Point(0, 0);

    public STAGE_01_Renderer() {}

    @Override
    public void render(Renderizable renderizable, Graphics2D graphics) throws RemoteException {
        Screen screen = (Screen) renderizable;
        drawBackground(screen, graphics);
        drawObjects(screen, graphics);

        GameClient client = TankClient.get();
        Player player = client.getPlayer();
        drawPlayerLifeBar(player, graphics);

        // game over?
        if (client.getPlayer().getLife() == 0) {
            drawGameOver(client.getSettings(), graphics);
        }
    }

    private void drawBackground(Screen screen, Graphics2D g) {
        GameSettings settings = TankClient.get().getSettings();
        BufferedImage background = Resources.STAGE_01.getImage(0);
        int w = settings.getWidth();
        int h = settings.getHeigth();
        int iw = background.getWidth();
        int ih = background.getHeight();
        int colunas = w / iw;
        int linhas = h / ih;
        if (colunas * iw < w) {
            colunas++;
        }
        if (linhas * ih < h) {
            linhas++;
        }

        // background
        if (Debugger.showImages) {
            int offsetX = (int) (offset.x + 0.5);
            for (int i = 0; i < linhas; i++) {
                int y = i * ih;
                if (y > h) {
                    break;
                }
                for (int j = 0; j < colunas; j++) {
                    int x = j * iw + offsetX;
                    if (j == 0 && x > 0) {
                        g.drawImage(background, -(iw - x), y, null);
                    }
                    if (j == colunas - 1 && x < w) {
                        g.drawImage(background, x + iw, y, null);
                    }
                    if (x > w) {
                        break;
                    }
                    if (x < -iw) {
                        break;
                    }
                    g.drawImage(background, x, y, null);
                }
            }
        }
    }

    // desenha barra de vida do jogador desta instancia do jogo
    private void drawEnemyLifeBar(Player player, Graphics2D g) throws RemoteException {
        double life = player.getLife();
        double maxLife = player.getMaxLife();

        Rectangle r = player.getBounds().toAwtRectangle();

        int w = 70;
        int h = 6;

        int rX = r.x + (r.width - w) / 2;
        int rY = r.y - h - h;
        int rW = w;
        int rH = h;

        GameSettings settings = TankClient.get().getSettings();
        if (rX < 0) {
            rX = 0;
        }
        if ((rX + rW) > settings.getWidth()) {
            rX = settings.getWidth() - rW;
        }
        if (rY < 0) {
            rY = r.y + r.height + rH;
        }
        if ((rY + rH) > settings.getHeigth()) {
            rY = r.y - rH;
        }

        g.setColor(Color.RED.darker());
        g.fillRect(rX, rY, rW, rH);

        int lifeW = (int) (life * rW / maxLife);

        g.setColor(Color.BLUE);
        g.fillRect(rX, rY, lifeW, rH);

        g.setColor(Color.YELLOW);
        g.drawRect(rX - 1, rY - 1, rW + 1, rH + 1);

        g.setFont(Fonts.COURIER.deriveFont(Font.BOLD, 12));
        FontMetrics fontMetrics = g.getFontMetrics();
        String text = player.getName();
        char[] chars = text.toCharArray();
        int textH = fontMetrics.getHeight();
        int textW = fontMetrics.charsWidth(chars, 0, chars.length);
        int tx = rX + (rW - textW) / 2;
        int ty = rY - textH / 2;
        g.setColor(Color.RED);
        g.drawString(text, tx, ty);
        g.setColor(Color.YELLOW);
        g.drawString(text, tx - 1, ty - 1);
    }

    private void drawGameOver(GameSettings settings, Graphics2D g) {
        g.setFont(Fonts.BUNDY_YELLOW_SOLID.deriveFont(Font.BOLD, 100));
        FontMetrics fontMetrics = g.getFontMetrics();
        String msg = "GAME OVER";
        char[] chars = msg.toCharArray();
        int textW = fontMetrics.charsWidth(chars, 0, chars.length);
        int textH = fontMetrics.getHeight();
        int w = settings.getWidth();
        int h = settings.getHeigth();
        int x = (w - textW) / 2;
        int y = (h - textH) / 2;

        // tom vermelho pra ficar sinistro!
        g.setColor(new Color(0x50FF0000, true));
        g.fillRect(0, 0, w, h);

        g.setColor(Color.YELLOW);
        g.drawString(msg, x, y);
    }

    private void drawObjects(Screen screen, Graphics2D g) throws RemoteException {
        long playerId = TankClient.get().getPlayer().getId();
        for (ServerObject object : screen.getObjects()) {
            Renderer renderer = RendererMapper.get().get(object.getRendererId());
            renderer.render(object, g);
            if (object instanceof Player) {
                if (object.getId() != playerId) {
                    drawEnemyLifeBar((Player) object, g);
                }
            }
        }
    }

    // desenha barra de vida do jogador desta instancia do jogo
    private void drawPlayerLifeBar(Player player, Graphics2D g) throws RemoteException {
        GameSettings settings = TankClient.get().getSettings();
        double life = player.getLife();
        double maxLife = player.getMaxLife();

        int w = settings.getWidth();
//        int h = settings.getHeigth();
        int gap = 50;
        int rX = gap;
        int rY = gap / 4;
        int rW = w - gap - gap;
        int rH = gap;

        g.setColor(Color.RED.darker().darker());
        g.fillRect(rX, rY, rW, rH);

        int lifeW = (int) (life * rW / maxLife);

        g.setColor(Color.BLUE.darker());
        g.fillRect(rX, rY, lifeW, rH);

        g.setColor(Color.YELLOW);
        g.drawRect(rX, rY, rW, rH);
        g.drawRect(rX + 1, rY + 1, rW - 2, rH - 2);

        g.setFont(Fonts.BUNDY_YELLOW_SOLID.deriveFont(19.0F));
//        FontMetrics fontMetrics = g.getFontMetrics();
        String text = "Soldado: " + player.getName();
        text += "        Blindagem: " + ((int) player.getLife()) + " / " + ((int) player.getMaxLife());
        text += "        Velocidade: " + ((int) player.getSpeed() / 5);
//        int textH = fontMetrics.getHeight();
        int tx = rX + 10;
        int ty = rY + 35;
        g.setColor(Color.YELLOW);
        g.drawString(text, tx, ty);
//        g.setColor(Color.YELLOW);
//        g.drawString(text, tx - 1, ty - 1);
    }
}
