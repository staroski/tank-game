package br.com.staroski.games;

import java.awt.*;
import java.io.*;

public final class Shape implements Serializable {

	private static final long serialVersionUID = 1;

	private static double computeArea(final double[] x, final double[] y) {
		final int n = x.length;
		double area = 0.0F;
		for (int i = 0; i < n - 1; i++) {
			area += x[i] * y[i + 1] - x[i + 1] * y[i];
		}
		area += x[n - 1] * y[0] - x[0] * y[n - 1];
		area *= 0.5;
		return area;
	}

	private static Bounds computeBounds(final double[] x, final double[] y) {
		double minX = x[0];
		double maxX = x[0];
		double minY = y[0];
		double maxY = y[0];
		final int length = x.length;
		for (int i = 1; i < length; i++) {
			if (x[i] < minX) {
				minX = x[i];
			}
			if (x[i] > maxX) {
				maxX = x[i];
			}
			if (y[i] < minY) {
				minY = y[i];
			}
			if (y[i] > maxY) {
				maxY = y[i];
			}
		}
		return new Bounds(minX, minY, maxX - minX, maxY - minY);
	}

	private static Point computeCenter(final double[] x, final double[] y) {
		double cx = 0.0F;
		double cy = 0.0F;

		final int n = x.length;
		for (int i = 0; i < n - 1; i++) {
			final double a = x[i] * y[i + 1] - x[i + 1] * y[i];
			cx += (x[i] + x[i + 1]) * a;
			cy += (y[i] + y[i + 1]) * a;
		}
		final double a = x[n - 1] * y[0] - x[0] * y[n - 1];
		cx += (x[n - 1] + x[0]) * a;
		cy += (y[n - 1] + y[0]) * a;

		final double area = computeArea(x, y);

		cx /= 6 * area;
		cy /= 6 * area;

		return new Point(cx, cy);
	}

	private double x[];
	private double y[];

	private Point center;
	private Bounds bounds;

	public Shape() {
		this(new double[0], new double[0]);
	}

	public Shape(final double x[], final double y[]) {
		if (x.length != y.length) {
			throw new IllegalArgumentException("x and y arrays should have the same length");
		}
		this.x = x;
		this.y = y;
		update();
	}

	public void add(final double x, final double y) {
		int length = size();
		final double[] newX = new double[length + 1];
		final double[] newY = new double[length + 1];
		System.arraycopy(this.x, 0, newX, 0, length);
		System.arraycopy(this.y, 0, newY, 0, length);
		newX[length] = x;
		newY[length] = y;
		this.x = newX;
		this.y = newY;
		length++;
		update();
	}

	public void add(final Point p) {
		add(p.x, p.y);
	}

	public void addPoint(final double x, final double y) {
		add(x, y);
		update();
	}

	public Shape copy() {
		final int length = size();
		final double[] xCopy = new double[length];
		final double[] yCopy = new double[length];
		System.arraycopy(this.x, 0, xCopy, 0, length);
		System.arraycopy(this.y, 0, yCopy, 0, length);
		final Shape copy = new Shape();
		copy.x = xCopy;
		copy.y = yCopy;
		copy.center = new Point(center);
		copy.bounds = new Bounds(bounds);
		return copy;
	}

	public void draw(final Graphics2D g) {
		final int length = size();
		if (length < 1) {
			return;
		}
		if (length > 1) {
			for (int i = 1; i < length; i++) {
				g.drawLine((int) (x[i - 1] + 0.5), (int) (y[i - 1] + 0.5), (int) (x[i] + 0.5), (int) (y[i] + 0.5));
			}
			g.drawLine((int) (x[length - 1] + 0.5), (int) (y[length - 1] + 0.5), (int) (x[0] + 0.5), (int) (y[0] + 0.5));
			return;
		}
		g.drawLine((int) (x[0] + 0.5), (int) (y[0] + 0.5), (int) (x[0] + 0.5), (int) (y[0] + 0.5));
	}

	public Bounds getBounds() {
		return bounds;
	}

	public Point getCenter() {
		return center;
	}

	public Point getPoint(final int index) {
		return new Point(x[index], y[index]);
	}

	public Shape setLocation(final double newX, final double newY) {
		final Point p = getCenter();
		if (p.x == newX && p.y == newY) {
			return this;
		}
		final double deltaX = newX - p.x;
		final double deltaY = newY - p.y;
		final Shape moved = new Shape();
		final double[] x = this.x;
		final double[] y = this.y;
		final int length = size();
		for (int i = 0; i < length; i++) {
			moved.add(x[i] + deltaX, y[i] + deltaY);
		}
		moved.update();
		return moved;
	}

	public Shape setLocation(final Point p) {
		return setLocation(p.x, p.y);
	}

	public int size() {
		return x.length;
	}

	public Shape translate(final double deltaX, final double deltaY) {
		final Shape translated = new Shape();
		final double[] x = this.x;
		final double[] y = this.y;
		final int length = size();
		for (int i = 0; i < length; i++) {
			translated.add(x[i] + deltaX, y[i] + deltaY);
		}
		translated.update();
		return translated;
	}

	private void update() {
		final int length = size();
		if (length < 1) {
			return;
		}
		center = computeCenter(x, y);
		bounds = computeBounds(x, y);
	}
}
