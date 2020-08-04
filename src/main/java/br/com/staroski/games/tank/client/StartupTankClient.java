package br.com.staroski.games.tank.client;

import javax.swing.UIManager;

public final class StartupTankClient {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            TankClient tankClient = TankClient.get();
            tankClient.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private StartupTankClient() {}
}
