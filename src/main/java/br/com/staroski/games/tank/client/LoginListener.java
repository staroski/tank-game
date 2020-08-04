package br.com.staroski.games.tank.client;

public interface LoginListener {

	void onCancel();

	boolean onLogin(String host, String instance, String username, String password);
}
