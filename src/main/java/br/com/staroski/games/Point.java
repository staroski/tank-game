package br.com.staroski.games;

import java.io.*;

public class Point implements Serializable {

	private static final long serialVersionUID = 1;

	public double x;
	public double y;

	public Point(final double x, final double y) {
		this.x = x;
		this.y = y;
	}

	public Point(final java.awt.Point p) {
		this(p.x, p.y);
	}

	public Point(final Point p) {
		this(p.x, p.y);
	}

	public void copy(final Point p) {
		this.x = p.x;
		this.y = p.y;
	}

	public java.awt.Point toAwtPoint() {
		return new java.awt.Point((int) (x + 0.5), (int) (y + 0.5));
	}

	@Override
	public String toString() {
		return "p(" + x + ", " + y + ")";
	}
}
