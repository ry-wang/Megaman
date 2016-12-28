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
	private int height = 14;

	Platforms (int x, int y, int w) {
		super(x, y);
		width = w;
	}

	protected int getWidth() {
		return width;
	}

	protected int getHeight() {
		return height;
	}

	protected void paintPlatforms(Graphics g) {
		try {
			if (width == 100) {
				g.drawImage(ImageIO.read((this.getClass().getResource("/main/resources/images/platform1.png"))), getX(), getY(), null);
			}
			else if (width == 150) {
				g.drawImage(ImageIO.read((this.getClass().getResource("/main/resources/images/platform2.png"))), getX(), getY(), null);
			}
			else if (width == 175) {
				g.drawImage(ImageIO.read((this.getClass().getResource("/main/resources/images/platform3.png"))), getX(), getY(), null);
			}
			else if (width == 200) {
				g.drawImage(ImageIO.read((this.getClass().getResource("/main/resources/images/platform4.png"))), getX(), getY(), null);
			}
		}
		catch (IOException e) {
			System.out.println("error");
		}
	}
}
