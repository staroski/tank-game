package br.com.staroski.games.tank.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import br.com.staroski.games.tank.Resources;

final class PropertiesFile {

    public static PropertiesFile load() throws IOException {
        return new PropertiesFile();
    }

    private static File getFile() throws IOException {
        String path = System.getProperty("user.home");
        File file = new File(path, Resources.GAME_NAME + ".properties");
        file.createNewFile();
        return file;
    }

    private String proxyHost;
    private String proxyPort;
    private String proxyUsername;
    private String proxyPassword;

    private String host;
    private String instance;
    private String username;
    private String password;

    private PropertiesFile() throws IOException {
        InputStream input = new FileInputStream(getFile());
        Properties prop = new Properties();
        prop.load(input);
        proxyHost = prop.getProperty("proxyHost");
        proxyPort = prop.getProperty("proxyPort");
        proxyUsername = prop.getProperty("proxyUsername");
        proxyPassword = prop.getProperty("proxyPassword");
        host = prop.getProperty("host");
        instance = prop.getProperty("instance");
        username = prop.getProperty("username");
        password = prop.getProperty("password");
        input.close();
    }

    public String getHost() {
        return host;
    }

    public String getInstance() {
        return instance;
    }

    public String getPassword() {
        return password;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public String getProxyPassword() {
        return proxyPassword;
    }

    public String getProxyPort() {
        return proxyPort;
    }

    public String getProxyUsername() {
        return proxyUsername;
    }

    public String getUsername() {
        return username;
    }

    public void save() throws IOException {
        OutputStream output = new FileOutputStream(getFile());
        Properties prop = new Properties();

        prop.setProperty("proxyHost", proxyHost);
        prop.setProperty("proxyPort", proxyPort);
        prop.setProperty("proxyUsername", proxyUsername);
        prop.setProperty("proxyPassword", proxyPassword);

        prop.setProperty("host", host);
        prop.setProperty("instance", instance);
        prop.setProperty("username", username);
        prop.setProperty("password", password);

        prop.store(output, Resources.GAME_NAME + " Properties File");
        output.flush();
        output.close();
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public void setProxyPassword(String proxyPassword) {
        this.proxyPassword = proxyPassword;
    }

    public void setProxyPort(String proxyPort) {
        this.proxyPort = proxyPort;
    }

    public void setProxyUsername(String proxyUsername) {
        this.proxyUsername = proxyUsername;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
