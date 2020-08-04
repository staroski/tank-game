package br.com.staroski.games.tank;

import static br.com.staroski.games.CGUtils.bufferize;
import static br.com.staroski.games.CGUtils.createRotatedImages;
import static br.com.staroski.games.CGUtils.loadBufferedImage;

import java.awt.Image;
import java.awt.image.BufferedImage;

import br.com.staroski.games.Resource;
import br.com.staroski.games.Shape;

public abstract class Resources implements Resource {

    private static final long serialVersionUID = 1;

    public static final BufferedImage ICON_IMAGE = loadBufferedImage("/objects/PNG/Bombs/Bomb_B.png");

    public static final String GAME_NAME = "Tank";

    private static int resourceId;

    public static final Resources PLAYER_01 = new Resources(++resourceId) {

        private static final long serialVersionUID = 1;

        @Override
        BufferedImage[] createImages() {
            BufferedImage image = loadBufferedImage("/assets/PNG/Hulls_Color_A/Hull_01.png");
            image = bufferize(image.getScaledInstance(64, -1, Image.SCALE_SMOOTH));
            return createRotatedImages(image, 90, TURN_ANGLE);
        }
    };

    public static final Resources PLAYER_01_GUN = new Resources(++resourceId) {

        private static final long serialVersionUID = 1;

        @Override
        BufferedImage[] createImages() {
            BufferedImage image = loadBufferedImage("/assets/PNG/Weapon_Color_A_256X256/Gun_01.png");
            image = bufferize(image.getScaledInstance(64, -1, Image.SCALE_SMOOTH));
            return createRotatedImages(image, 90, TURN_ANGLE);
        }
    };

    public static final Resources STAGE_01 = new Resources(++resourceId) {

        private static final long serialVersionUID = 1;

        @Override
        BufferedImage[] createImages() {
            return new BufferedImage[] { loadBufferedImage("/tileset/PNG/Tiles/Ground_Tile_01_C.png") };
        }
    };

    public static final Resources BULLET_01 = new Resources(++resourceId) {

        private static final long serialVersionUID = 1;

        @Override
        BufferedImage[] createImages() {
            BufferedImage image = loadBufferedImage("/assets/PNG/Effects/Medium_Shell.png");
            image = bufferize(image.getScaledInstance(64, -1, Image.SCALE_SMOOTH));
            return createRotatedImages(image, 90, TURN_ANGLE);
        }
    };

    private static final int TURN_ANGLE = 1;

    private static int getFrame(double direction) {
        return (int) direction / TURN_ANGLE;
    }

    private transient BufferedImage[] images;
    private transient Shape[] shapes;

    private final int id;

    private Resources(int id) {
        this.id = id;
        images = createImages();
        shapes = createShapes();
    }

    @Override
    public int getId() {
        return id;
    }

    public BufferedImage getImage(double direction) {
        if (images == null) {
            images = createImages();
        }
        return images[getFrame(direction)];
    }

    public Shape getShape(double direction) {
        if (shapes == null) {
            shapes = createShapes();
        }
        return shapes[getFrame(direction)];
    }

    abstract BufferedImage[] createImages();

    /**
     * Implementação padrão cria shapes retangulares do tamanho da imagem
     */
    Shape[] createShapes() {
        if (images == null) {
            images = createImages();
        }
        int size = images.length;
        Shape[] shapes = new Shape[size];
        for (int i = 0; i < size; i++) {
            Shape shape = new Shape();
            int x = 0;
            int y = 0;
            int w = images[i].getWidth();
            int h = images[i].getHeight();
            shape.addPoint(x, y);
            shape.addPoint(x + w, y);
            shape.addPoint(x + w, y + h);
            shape.addPoint(y, y + h);
            shapes[i] = shape;
        }
        return shapes;
    }
}
