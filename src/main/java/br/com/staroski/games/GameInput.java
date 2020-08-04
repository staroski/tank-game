package br.com.staroski.games;

import java.io.Serializable;

public final class GameInput implements Serializable {

    private static final class Holder {

        static final GameInput INSTANCE = new GameInput();
    }

    private static final long serialVersionUID = 1;

    public static GameInput get() {
        return Holder.INSTANCE;
    }

    private final boolean[] keyUp = new boolean[256];
    private final boolean[] keyDown = new boolean[256];

    private final boolean[] mouseUp = new boolean[3];
    private final boolean[] mouseDown = new boolean[3];
    private Point mousePoint = new Point(0, 0);

    private GameInput() {}

    public synchronized Point getMousePoint() {
        return mousePoint;
    }

    public synchronized boolean isKeyDown(final int key) {
        return keyDown[key];
    }

    public synchronized boolean isKeyUp(final int key) {
        return keyUp[key];
    }

    public synchronized boolean isMouseDown(int button) {
        return mouseDown[button];
    }

    public synchronized boolean isMouseUp(int button) {
        return mouseUp[button];
    }

    public synchronized void pressKey(final int code) {
        if (code > -1 && code < 256) {
            keyDown[code] = true;
            keyUp[code] = false;
        }
    }

    public synchronized void pressMouse(final int button) {
        if (button > -1 && button < 3) {
            mouseDown[button] = true;
            mouseUp[button] = false;
        }
    }

    public synchronized void releaseKey(final int code) {
        if (code > -1 && code < 256) {
            keyUp[code] = true;
            keyDown[code] = false;
        }
    }

    public synchronized void releaseMouse(final int button) {
        if (button > -1 && button < 3) {
            mouseUp[button] = true;
            mouseDown[button] = false;
        }
    }

    public synchronized void setMousePoint(int x, int y) {
        mousePoint = new Point(x, y);
    }
}