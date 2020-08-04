package br.com.staroski.games;

import java.io.*;

public final class GameSettings implements Serializable {

	private static final long serialVersionUID = 1;

	private String name;
	private int width;
	private int heigth;
	private int colorDepth;

	public GameSettings() {
	}

	public GameSettings(final String name, final int width, final int heigth, final int colorDepth) {
		this.name = name;
		this.width = width;
		this.heigth = heigth;
		this.colorDepth = colorDepth;
	}

	public int getColorDepth() {
		return colorDepth;
	}

	public int getHeigth() {
		return heigth;
	}

	public String getName() {
		return name;
	}

	public int getWidth() {
		return width;
	}

	public void setColorDepth(final int colorDepth) {
		this.colorDepth = colorDepth;
	}

	public void setHeigth(final int heigth) {
		this.heigth = heigth;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setWidth(final int width) {
		this.width = width;
	}
}
