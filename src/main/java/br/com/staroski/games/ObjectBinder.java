package br.com.staroski.games;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import br.com.staroski.games.tank.Resources;

public final class ObjectBinder {

    private static final class Holder {

        static final ObjectBinder INSTANCE;
        static {
            try {
                INSTANCE = new ObjectBinder();
            } catch (Exception e) {
                e.printStackTrace();
                throw new ExceptionInInitializerError(e);
            }
        }
    }

    private static final int PORT = 8008;

    public static ObjectBinder get() {
        return Holder.INSTANCE;
    }

    public static String getLocalHost() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (final UnknownHostException e) {
            e.printStackTrace();
            return "localhost";
        }
    }

    private static void checkSettings(final GameSettings settings) {
        if (settings == null) {
            throw new IllegalArgumentException("settings are null");
        }
        String name = settings.getName();
        if (name == null) {
            throw new IllegalStateException("game name is null in settings");
        }
        name = name.trim();
        if ("".equals(name)) {
            throw new IllegalStateException("game name is empty in settings");
        }
    }

    private ObjectBinder() throws RemoteException {
        try {
            System.out.println("tentando criar rmi registry...");
            LocateRegistry.createRegistry(PORT);
            System.out.println("rmi registry criado com sucesso!");
        } catch (Exception e) {
            System.out.println("rmi registry possivelmente ja foi criado, tentando obte-lo...");
            LocateRegistry.getRegistry(PORT);
            System.out.println("rmi registry obtido com sucesso!");
        }
    }

    public String bindClient(String host, GameSettings settings, final RemoteAccessible client) throws RemoteException {
        if (client == null) {
            throw new IllegalArgumentException("client is null");
        }
        checkSettings(settings);
        try {
            // Registry registry = LocateRegistry.getRegistry(host, PORT);
            final String name = getInstanceName(settings, client.getClass().getSimpleName(), client.getId());
            RemoteAccessible stub = (RemoteAccessible) UnicastRemoteObject.exportObject(client, 0);

            String url = "//" + host + ":" + PORT + "/" + name;
            Naming.bind(url, stub);
            // registry.rebind(name, stub);
            debug(name);
            return name;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage(), e);
        }
    }

    public String bindEngine(GameSettings settings, final GameEngine engine) throws RemoteException {
        if (engine == null) {
            throw new IllegalArgumentException("engine is null");
        }
        checkSettings(settings);
        try {
            // Registry registry = LocateRegistry.createRegistry(PORT);
            final String name = getInstanceName(settings, engine.getClass().getSimpleName(), engine.getId());
            GameEngine stub = (GameEngine) UnicastRemoteObject.exportObject(engine, 0);

            String url = "//" + getLocalHost() + ":" + PORT + "/" + name;
            Naming.bind(url, stub);
            // registry.rebind(name, stub);
            debug(name);
            showConfig(getLocalHost(), name);
            return name;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage(), e);
        }
    }

    public String bindObject(String host, GameSettings settings, final RemoteAccessible object) throws RemoteException {
        if (object == null) {
            throw new IllegalArgumentException("object is null");
        }
        checkSettings(settings);
        try {
            // Registry registry = LocateRegistry.getRegistry(host, PORT);

            final String name = getInstanceName(settings, object.getClass().getSimpleName(), object.getId());
            RemoteAccessible stub = (RemoteAccessible) UnicastRemoteObject.exportObject(object, 0);

            String url = "//" + host + ":" + PORT + "/" + name;
            Naming.bind(url, stub);
            // registry.bind(name, stub);
            debug(name);
            return name;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage(), e);
        }
    }

    public String bindScreen(String host, GameSettings settings, final RemoteAccessible screen) throws RemoteException {
        if (screen == null) {
            throw new IllegalArgumentException("screen is null");
        }
        checkSettings(settings);
        try {
            // Registry registry = LocateRegistry.getRegistry(host, PORT);
            final String name = getInstanceName(settings, screen.getClass().getSimpleName(), screen.getId());
            RemoteAccessible stub = (RemoteAccessible) UnicastRemoteObject.exportObject(screen, 0);

            String url = "//" + host + ":" + PORT + "/" + name;
            Naming.bind(url, stub);
            // registry.rebind(name, stub);
            debug(name);
            return name;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage(), e);
        }
    }

    public GameEngine lookupEngine(final String host, final String name) throws RemoteException {
        try {
            String url = "//" + host + ":" + PORT + "/" + name;
            final GameEngine engine = (GameEngine) Naming.lookup(url);
            return engine;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage(), e);
        }
    }

    public void unbindObject(String host, GameSettings settings, final RemoteAccessible object) throws RemoteException {
        if (object == null) {
            throw new IllegalArgumentException("object is null");
        }
        checkSettings(settings);
        try {
            final String name = getInstanceName(settings, object.getClass().getSimpleName(), object.getId());
            String url = "//" + host + ":" + PORT + "/" + name;
            Naming.unbind(url);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage(), e);
        }
    }

    private void debug(String name) {
        System.out.println("host:     " + getLocalHost());
        System.out.println("instance: " + name);
    }

    private String getInstanceName(final GameSettings settings, String objectName, long id) {
        return (System.getProperty("user.name") + "." + settings.getName() + "." + objectName + "." + Long.toHexString(id)).replaceAll("\\s", ".").toLowerCase();
    }

    private void showConfig(String host, String name) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final JFrame frame = new JFrame("Configurações de Acesso");
        frame.setIconImage(Resources.ICON_IMAGE);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(frame, "Deseja realmente sair?", "Confirmação", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (option == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        JPanel hostPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        hostPanel.add(new JLabel("Host do Jogo: "));
        JTextField hostField = new JTextField(host);
        hostField.setEditable(false);
        hostField.setPreferredSize(new Dimension(480, 25));
        hostPanel.add(hostField);

        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        namePanel.add(new JLabel("Instância do Jogo: "));
        JTextField nameField = new JTextField(name);
        nameField.setEditable(false);
        nameField.setPreferredSize(new Dimension(480, 25));
        namePanel.add(nameField);

        Container contents = frame.getContentPane();
        contents.setLayout(new GridLayout(2, 1));
        contents.add(hostPanel);
        contents.add(namePanel);

        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
