package br.com.staroski.games;

import java.io.*;

public final class GameEvent implements Serializable {

	private static final long serialVersionUID = 1;

	public final double time;
	public final int ups;
	public final int fps;

	public GameEvent(final double time, final int ups, final int fps) {
		this.time = time;
		this.ups = ups;
		this.fps = fps;
	}

	@Override
	public String toString() {
		return "GameEvent[ time: " + time + ", ups: " + ups + ", fps: " + fps + "]";
	}
}
