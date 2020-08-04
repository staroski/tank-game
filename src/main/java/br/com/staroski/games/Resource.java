package br.com.staroski.games;

import java.awt.image.*;
import java.io.*;

public interface Resource extends Serializable {

	int getId();

	BufferedImage getImage(double direction);

	Shape getShape(double direction);
}
