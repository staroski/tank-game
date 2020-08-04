package br.com.staroski.games;

import java.awt.*;
import java.rmi.*;

public interface Renderer {

	void render(Renderizable renderizable, Graphics2D graphics) throws RemoteException;
}
