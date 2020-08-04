package br.com.staroski.games.tank.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import br.com.staroski.games.tank.Resources;

class LoginFrame extends JFrame {

    private static final long serialVersionUID = 1;

    private final LoginListener listener;

    LoginFrame(LoginListener listener) throws IOException {
        super(Resources.GAME_NAME + " - Login");
        this.listener = listener;
        initGUI();
    }

    private void initGUI() throws IOException {
        this.setIconImage(Resources.ICON_IMAGE);
        final PropertiesFile props = PropertiesFile.load();
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                listener.onCancel();
            }
        });
        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel(new GridLayout(4, 1));

        // painel do campo proxy host
        JPanel proxyHostPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel proxyHostLabel = new JLabel("Host Proxy:");
        proxyHostLabel.setPreferredSize(new Dimension(100, 24));
        final JTextField proxyHostField = new JTextField();
        proxyHostField.setPreferredSize(new Dimension(200, 24));
        proxyHostPanel.add(proxyHostLabel);
        proxyHostPanel.add(proxyHostField);

        // painel do campo proxy port
        JPanel proxyPortPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel proxyPortLabel = new JLabel("Porta Proxy:");
        proxyPortLabel.setPreferredSize(new Dimension(100, 24));
        final JTextField proxyPortField = new JTextField();
        proxyPortField.setPreferredSize(new Dimension(200, 24));
        proxyPortPanel.add(proxyPortLabel);
        proxyPortPanel.add(proxyPortField);

        // painel do campo usuario
        JPanel proxyUserPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel proxyUserLabel = new JLabel("Usuário Proxy:");
        proxyUserLabel.setPreferredSize(new Dimension(100, 24));
        final JTextField proxyUserField = new JTextField();
        proxyUserField.setPreferredSize(new Dimension(200, 24));
        proxyUserPanel.add(proxyUserLabel);
        proxyUserPanel.add(proxyUserField);

        // painel do campo senha
        JPanel proxyPassPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel proxyPassLabel = new JLabel("Senha:");
        proxyPassLabel.setPreferredSize(new Dimension(100, 24));
        final JPasswordField proxyPassField = new JPasswordField();
        proxyPassField.setPreferredSize(new Dimension(200, 24));
        proxyPassPanel.add(proxyPassLabel);
        proxyPassPanel.add(proxyPassField);

        // painel do campo host
        JPanel hostPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel hostLabel = new JLabel("Host Jogo:");
        hostLabel.setPreferredSize(new Dimension(100, 24));
        final JTextField hostField = new JTextField();
        hostField.setPreferredSize(new Dimension(200, 24));
        hostPanel.add(hostLabel);
        hostPanel.add(hostField);

        // painel do campo instance
        JPanel instancePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel instanceLabel = new JLabel("Instância do Jogo:");
        instanceLabel.setPreferredSize(new Dimension(100, 24));
        final JTextField instanceField = new JTextField();
        instanceField.setPreferredSize(new Dimension(200, 24));
        instancePanel.add(instanceLabel);
        instancePanel.add(instanceField);

        // painel do campo usuario
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel userLabel = new JLabel("Usuário Jogo:");
        userLabel.setPreferredSize(new Dimension(100, 24));
        final JTextField userField = new JTextField();
        userField.setPreferredSize(new Dimension(200, 24));
        userPanel.add(userLabel);
        userPanel.add(userField);

        // painel do campo senha
        JPanel passPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel passLabel = new JLabel("Senha:");
        passLabel.setPreferredSize(new Dimension(100, 24));
        final JPasswordField passField = new JPasswordField();
        passField.setPreferredSize(new Dimension(200, 24));
        passPanel.add(passLabel);
        passPanel.add(passField);

        // inicializar valor default dos campos
        proxyHostField.setText(props.getProxyHost());
        proxyPortField.setText(props.getProxyPort());
        proxyUserField.setText(props.getProxyUsername());
        proxyPassField.setText(props.getProxyHost());

        hostField.setText(props.getHost());
        instanceField.setText(props.getInstance());
        userField.setText(props.getUsername());
        passField.setText(props.getPassword());

        // painel dos bot�es login e cancelar
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton buttonLogin = new JButton("Login");
        buttonLogin.setPreferredSize(new Dimension(100, 24));
        buttonLogin.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    props.setProxyHost(proxyHostField.getText());
                    props.setProxyPort(proxyPortField.getText());
                    props.setProxyUsername(proxyUserField.getText());
                    props.setProxyPassword(String.valueOf(proxyPassField.getPassword()));

                    props.setHost(hostField.getText());
                    props.setInstance(instanceField.getText());
                    props.setUsername(userField.getText());
                    props.setPassword(String.valueOf(passField.getPassword()));

                    props.save();

                    TankClient.setProxy(props.getProxyHost(), props.getProxyPort(), props.getProxyUsername(), props.getProxyPassword());

                    if (listener.onLogin(props.getHost(), props.getInstance(), props.getUsername(), props.getPassword())) {
                        setVisible(false);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        JButton buttonCancel = new JButton("Cancelar");
        buttonCancel.setPreferredSize(new Dimension(100, 24));
        buttonCancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                listener.onCancel();
            }
        });
        buttonPanel.add(buttonLogin);
        buttonPanel.add(buttonCancel);

        centerPanel.add(proxyHostPanel);
        centerPanel.add(proxyPortPanel);
        centerPanel.add(proxyUserPanel);
        centerPanel.add(proxyPassPanel);
        centerPanel.add(hostPanel);
        centerPanel.add(instancePanel);
        centerPanel.add(userPanel);
        centerPanel.add(passPanel);
        this.add(BorderLayout.CENTER, centerPanel);
        this.add(BorderLayout.SOUTH, buttonPanel);
        pack();
        this.setResizable(false);
    }
}
