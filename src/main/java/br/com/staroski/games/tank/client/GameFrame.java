package br.com.staroski.games.tank.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.VolatileImage;

import javax.swing.JFrame;

import br.com.staroski.games.GameInput;
import br.com.staroski.games.GameSettings;
import br.com.staroski.games.tank.Resources;

final class GameFrame extends JFrame {

    private static final long serialVersionUID = 1;

    private static String merge(String gameName, String playerName) {
        playerName = playerName.trim();
        if (!playerName.isEmpty()) {
            return gameName + " - " + playerName;
        }
        return gameName;
    }

    private final GameSettings settings;
    // cada monitor tem sua propria configura��o grafica
    private final GraphicsDevice[] devices;

    private final VolatileImage[] offscreens;

    private final MouseAdapter mouseAdapter = new MouseAdapter() {

        public void mouseDragged(MouseEvent e) {
            GameInput.get().setMousePoint(e.getX(), e.getY());
        };

        public void mouseMoved(MouseEvent e) {
            GameInput.get().setMousePoint(e.getX(), e.getY());
        };

        public void mousePressed(MouseEvent e) {
            GameInput.get().pressMouse(e.getButton());
        };

        public void mouseReleased(MouseEvent e) {
            GameInput.get().releaseMouse(e.getButton());
        };
    };

    private final KeyAdapter keyAdapter = new KeyAdapter() {

        @Override
        public void keyPressed(final KeyEvent e) {
            GameInput.get().pressKey(e.getKeyCode());
        }

        @Override
        public void keyReleased(KeyEvent e) {
            GameInput.get().releaseKey(e.getKeyCode());
        }
    };

    public GameFrame(String playerName, GameSettings settings) {
        super(merge(settings.getName(), playerName));
        this.settings = settings;
        final GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        devices = environment.getScreenDevices();
        this.offscreens = createOffscreenImages(devices);

        new Presets().execute();

        initGUI();
    }

    @Override
    public void addNotify() {
        super.addNotify();
        createBufferStrategy(2);
    }

    public Graphics2D getDrawBuffer() {
        return offscreens[getCurrentDevice()].createGraphics();
    }

    @Override
    public Dimension getPreferredSize() {
        Insets insets = getInsets();
        return new Dimension(insets.left + settings.getWidth() + insets.right, insets.top + settings.getHeigth() + insets.bottom);
    }

    public void showDrawBuffer() {
        BufferStrategy drawBuffer = getBufferStrategy();
        if (drawBuffer == null) {
            return;
        }
        Graphics2D graphics = (Graphics2D) drawBuffer.getDrawGraphics();
        Insets insets = getInsets();
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, settings.getWidth(), settings.getHeigth());
        graphics.drawImage(offscreens[getCurrentDevice()], insets.left, insets.top, null);
        graphics.dispose();
        drawBuffer.show();
    }

    private VolatileImage[] createOffscreenImages(GraphicsDevice[] devices) {
        VolatileImage[] offScreens = new VolatileImage[devices.length];
        for (int i = 0; i < devices.length; i++) {
            GraphicsConfiguration config = devices[i].getDefaultConfiguration();
            offScreens[i] = config.createCompatibleVolatileImage(settings.getWidth(), settings.getHeigth());
        }
        return offScreens;
    }

    private int getCurrentDevice() {
        GraphicsDevice device = getGraphicsConfiguration().getDevice();
        for (int i = 0; i < devices.length; i++) {
            if (device == devices[i]) {
                return i;
            }
        }
        return 0;
    }

    private void initGUI() {
        this.setIconImage(Resources.ICON_IMAGE);
        this.setLayout(new BorderLayout());
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addKeyListener(keyAdapter);
        this.addMouseListener(mouseAdapter);
        this.addMouseMotionListener(mouseAdapter);
    }
}
