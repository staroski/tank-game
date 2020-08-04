package br.com.staroski.games;

import java.awt.*;
import java.io.*;

public final class Bounds implements Serializable {

	private static final long serialVersionUID = 1;

	public double x;
	public double y;
	public double width;
	public double height;

	public Bounds() {
	}

	public Bounds(final Bounds bounds) {
		this(bounds.x, bounds.y, bounds.width, bounds.height);
	}

	public Bounds(final double x, final double y, final double width, final double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public Bounds(final Rectangle bounds) {
		this(bounds.x, bounds.y, bounds.width, bounds.height);
	}

	public boolean intersects(Bounds other) {
		double thisW = this.width;
		double thisH = this.height;
		double thatW = other.width;
		double thatH = other.height;
		if (thisW <= 0 || thisH <= 0 || thatW <= 0 || thatH <= 0) {
			return false;
		}
		double thisX = this.x;
		double thisY = this.y;
		double thatX = other.x;
		double thatY = other.y;
		thisW += thatX;
		thisH += thatY;
		thatW += thisX;
		thatH += thisY;
		return ((thisW < thatX || thisW > thisX) && (thisH < thatY || thisH > thisY) && (thatW < thisX || thatW > thatX) && (thatH < thisY || thatH > thatY));
	}

	public java.awt.Rectangle toAwtRectangle() {
		return new java.awt.Rectangle((int) (x + 0.5), (int) (y + 0.5), (int) (width + 0.5), (int) (height + 0.5));
	}

	@Override
	public String toString() {
		return "r(" + x + ", " + y + ", " + width + ", " + height + ")";
	}
}
