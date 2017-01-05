package main.java;

import java.awt.Graphics;
import java.io.IOException;
import java.lang.*;

import javax.imageio.ImageIO;

/**
 * @ Description: Platforms class, used for creating the platform objects in each level
 * @ Author: Ryan Wang
 * @ Version: v2.0
 * September 2016
 */


public class Platforms extends Object {

	private int width;

	Platforms (int x, int y, int w) {
		super(x, y);
		width = w;
	}

	protected int getWidth() {
		return width;
	}

	protected int getHeight() {
		final int height = 14;
		return height;
	}

	protected void paintPlatforms(Graphics g) {
		try {
			String imagePath = "/main/resources/images/platform";
			switch (width) {
				case 100: imagePath += "1.png"; break;
				case 150: imagePath += "2.png"; break;
				case 175: imagePath += "3.png"; break;
				case 200: imagePath += "4.png"; break;
				default: break;
			}
			g.drawImage(ImageIO.read((this.getClass().getResource(imagePath))), getX(), getY(), null);
		}
		catch (IOException e) {
			System.out.println("Error drawing platform");
		}
	}
}
