package br.com.staroski.games;

import java.rmi.*;

final class CollisionHandler {

	private boolean collides(ServerObject a, ServerObject b) throws RemoteException {
		Bounds r1 = a.getBounds();
		Bounds r2 = b.getBounds();
		return r1.intersects(r2);
	}

	void checkCollisions(ServerObject[] objects) throws RemoteException {
		int size = objects.length;
		for (int i = 0; i < size; i++) {
			ServerObject a = objects[i];
			for (int j = i + 1; j < size; j++) {
				ServerObject b = objects[j];
				if (collides(a, b)) {
					a.onCollision(b);
					b.onCollision(a);
				}
			}
		}
	}
}
