package br.com.staroski.games.tank.client;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;

import br.com.staroski.games.GameClient;
import br.com.staroski.games.GameEngine;
import br.com.staroski.games.GameInput;
import br.com.staroski.games.GameSettings;
import br.com.staroski.games.IdGenerator;
import br.com.staroski.games.ObjectBinder;
import br.com.staroski.games.Player;
import br.com.staroski.games.Renderer;
import br.com.staroski.games.RendererMapper;
import br.com.staroski.games.Renderizable;
import br.com.staroski.games.Screen;

public final class TankClient implements GameClient {

    private static final class Holder {

        private static final TankClient INSTANCE;

        static {
            try {
                INSTANCE = new TankClient();
            } catch (RemoteException e) {
                e.printStackTrace();
                throw new ExceptionInInitializerError(e);
            }
        }
    }

    public static TankClient get() {
        return Holder.INSTANCE;
    }

    static void setProxy(final String host, final String port, final String user, final String password) {
        try {
            System.out.println("configurando proxy...");
            Authenticator.setDefault(new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, password.toCharArray());
                }
            });

            System.setProperty("http.proxyHost", host);
            System.setProperty("http.proxyPort", port);
            System.setProperty("http.proxyUser", user);
            System.setProperty("http.proxyPassword", password);
            System.out.println("proxy configurado com sucesso!");
        } catch (Exception e) {
            System.out.println("erro ao configurar proxy:");
            e.printStackTrace();
        }
    }
    private GameSettings settings;
    private GameEngine engine;
    private GameFrame gameFrame;
    private Player player;

    private Screen screen;

    private final KeyListener keyListener = new KeyAdapter() {

        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                checkClose();
            }
        }
    };

    private final WindowListener windowListener = new WindowAdapter() {

        @Override
        public void windowClosing(WindowEvent we) {
            checkClose();
        }

    };

    private final LoginListener loginListener = new LoginListener() {

        @Override
        public void onCancel() {
            System.exit(0);// faz nada
        }

        public boolean onLogin(String host, String instance, String username, String password) {
            try {
                final GameClient client = TankClient.this;
                ObjectBinder locator = ObjectBinder.get();
                engine = locator.lookupEngine(host, instance);

                // o client � 'bindeado' nesta maquina
                settings = engine.getSettings();
                locator.bindClient(ObjectBinder.getLocalHost(), settings, client);

                engine.addClient(client, username, password); // vai disparar o setPlayer
                screen = engine.getScreen();

                String playerName = getPlayer().getName().trim();
                gameFrame = new GameFrame(playerName, settings);
                gameFrame.addWindowListener(windowListener);
                gameFrame.addKeyListener(keyListener);
                gameFrame.setVisible(true);
                return true;
            } catch (RemoteException e) {
                e.printStackTrace();
                return false;
            }
        }
    };

    private final long id;

    private TankClient() throws RemoteException {
        id = IdGenerator.newId();
    }

    @Override
    public long getId() throws RemoteException {
        return id;
    }

    @Override
    public Player getPlayer() throws RemoteException {
        return player;
    }

    @Override
    public Screen getScreen() throws RemoteException {
        return screen;
    }

    @Override
    public GameSettings getSettings() {
        return settings;
    }

    @Override
    public boolean isAccessible() {
        return true;
    }

    @Override
    public void onDraw() throws RemoteException {
        if (gameFrame == null) {
            return;
        }
        Graphics2D g = gameFrame.getDrawBuffer();

        // limpar a tela
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, settings.getWidth(), settings.getHeigth());

        Renderizable renderizable = getScreen();
        Renderer renderer = RendererMapper.get().get(renderizable.getRendererId());
        renderer.render(renderizable, g);

        // apresentar
        gameFrame.showDrawBuffer();
    }

    @Override
    public void onInput() throws RemoteException {
        getPlayer().onInput(GameInput.get());
    }

    @Override
    public void setPlayer(Player player) throws RemoteException {
        this.player = player;
    }

    private void checkClose() {
        int opcao = JOptionPane.showConfirmDialog(gameFrame, "Deseja realmente sair?", "Confirmação", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (opcao == JOptionPane.YES_OPTION) {
            try {
                engine.removeClient(TankClient.this);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.exit(0);
            }
        }
    }

    void execute() throws Exception {
        LoginFrame loginFrame = new LoginFrame(loginListener);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);
    }
}
