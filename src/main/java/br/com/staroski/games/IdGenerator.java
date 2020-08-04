package br.com.staroski.games;

public final class IdGenerator {

	private static long lastId;

	public static synchronized long newId() {
		long newId = System.currentTimeMillis();
		if (newId > lastId) {
			lastId = newId;
		} else {
			lastId++;
			newId = lastId;
		}
		return newId;
	}

	private IdGenerator() {
		throw new UnsupportedOperationException();
	}
}
