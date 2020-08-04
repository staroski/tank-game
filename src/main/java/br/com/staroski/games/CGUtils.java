package br.com.staroski.games;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class CGUtils {

    private static final Component OBSERVER = new Component() {

        private static final long serialVersionUID = 1L;
    };

    public static BufferedImage bufferize(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }
        try {
            MediaTracker tracker = new MediaTracker(OBSERVER);
            tracker.addImage(img, 0);
            tracker.waitForID(0);
            tracker.removeImage(img, 0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(OBSERVER), img.getHeight(OBSERVER), BufferedImage.TYPE_INT_ARGB);
        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        // Return the buffered image
        return bimage;
    }

    public static Point computeDelta(Point a, Point b) {
        return new Point(b.x - a.x, b.y - a.y);
    }

    public static BufferedImage[] createRotatedImages(BufferedImage original, double deltaDegrees) {
        return createRotatedImages(original, 0, deltaDegrees);
    }

    public static BufferedImage[] createRotatedImages(BufferedImage original, int initialAngle, double deltaDegrees) {
        int count = (int) (360 / deltaDegrees + 0.5);
        BufferedImage[] frames = new BufferedImage[count];
        for (int i = 0; i < count; i++) {
            frames[i] = rotateImage(original, initialAngle + i * deltaDegrees);
        }
        return frames;
    }

    public static Shape[] createRotatedShapes(Shape original, double deltaDegrees) {
        return createRotatedShapes(original, 0, deltaDegrees);
    }

    public static Shape[] createRotatedShapes(Shape original, int initialAngle, double deltaDegrees) {
        int count = (int) (360 / deltaDegrees + 0.5);
        Shape[] polygons = new Shape[count];
        for (int i = 0; i < count; i++) {
            polygons[i] = rotateShape(original, initialAngle + i * deltaDegrees);
        }
        return polygons;
    }

    public static double getAngle(Point p1, Point p2) {
        double deltaX = p2.x - p1.x;
        double deltaY = p2.y - p1.y;
        return Math.atan2(deltaY, deltaX) * 180 / Math.PI;
    }

    public static byte[] getBytes(String resource) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            InputStream input = Object.class.getResourceAsStream(resource);
            int max = 4096;
            byte[] buffer = new byte[max];
            for (int lidos = -1; (lidos = input.read(buffer, 0, max)) != -1; bytes.write(buffer, 0, lidos)) {}
            return bytes.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    public static double getDistance(Point p1, Point p2) {
        double deltaX = p2.x - p1.x;
        double deltaY = p2.y - p1.y;
        return Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
    }

    public static Point getDrawPoint(BufferedImage image, Shape shape) {
        Point c = shape.getCenter();
        double x = c.x - image.getWidth() / 2.0;
        double y = c.y - image.getHeight() / 2.0;
        return new Point(x, y);
    }

    public static BufferedImage loadBufferedImage(String resource) {
        try {
            InputStream input = CGUtils.class.getResourceAsStream(resource);
            return ImageIO.read(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Point movePoint(Point p, double distance, double angle) {
        // coordenadas da origem
        double x0 = p.x;
        double y0 = p.y;
        switch ((int) (angle + 0.5)) {
            case 0:
                return new Point(x0 + distance, y0);
            case 180:
                return new Point(x0 - distance, y0);
            case 90:
                return new Point(x0, p.y + distance);
            case 270:
                return new Point(x0, p.y - distance);
            default:
                // coordenadas da origem com transla��o no eixo x
                double x1 = x0 + distance;
                double y1 = y0;
                // coordenadas ap�s a rota��o
                double radians = Math.toRadians(angle);
                double cosA = Math.cos(radians);
                double sinA = Math.sin(radians);
                double x2 = x0 + ((x1 - x0) * cosA - (y1 - y0) * sinA);
                double y2 = y0 + ((x1 - x0) * sinA + (y1 - y0) * cosA);
                // devolver ponto rotacionado
                return new Point(x2, y2);
        }
    }

    public static java.awt.Shape rotateAwtShape(java.awt.Shape shape, double degrees) {
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(degrees));
        return transform.createTransformedShape(shape);
    }

    public static BufferedImage rotateImage(BufferedImage img, double degrees) {
        // a imagem rotacionada pode ter uma �rea quadrada maior que a original
        // por isso criamos uma nova imagem com base no tamanho da hipotenusa
        // do triangulo formado entre o centro da imagem e um dos cantos
        Rectangle bounds = new Rectangle(0, 0, img.getWidth(), img.getHeight());
        int a = bounds.width - (int) (bounds.getCenterX() + 0.5);
        int b = bounds.height - (int) (bounds.getCenterY() + 0.5);
        int hypotenusa = (int) (Math.sqrt(a * a + b * b) + 0.5);

        // o tamanho da nova imagem vai ser o dobro da hipotenusa
        // assim n�o haver� corte na imagem original ap�s a rota��o
        final int side = hypotenusa * 2;
        BufferedImage squareImg = new BufferedImage(side, side, img.getType());

        // criar objeto de transforma��o da rota��o
        AffineTransform transform = new AffineTransform();
        // rotacionar
        transform.rotate(Math.toRadians(degrees), hypotenusa, hypotenusa);
        // centralizar
        transform.translate((side - bounds.width) / 2.0, (side - bounds.height) / 2.0);

        // desenhar a imagem rotacionada
        Graphics2D g = squareImg.createGraphics();
        g.drawImage(img, transform, OBSERVER);
        g.dispose();

        // devolver a nova imagem
        return squareImg;
    }

    public static Shape rotateShape(Shape shape, double degrees) {
        Point center = shape.getCenter();
        Shape rotated = new Shape();
        int length = shape.size();
        for (int i = 0; i < length; i++) {
            Point p = shape.getPoint(i);
            double distance = getDistance(center, p);
            double angle = getAngle(center, p);
            rotated.add(movePoint(center, distance, degrees + angle));
        }
        return rotated;
    }
}
