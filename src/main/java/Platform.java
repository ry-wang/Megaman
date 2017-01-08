package main.java;

import java.awt.*;
import java.io.IOException;
import java.lang.*;

import javax.imageio.ImageIO;

/**
 * @ Description: Platform class, used for creating the platform objects in each level
 * @ Author: Ryan Wang
 * @ Version: v2.0
 * September 2016
 */


public class Platform extends Object {

	private int width;
	private Rectangle platformBox;

	Platform(int x, int y, int w) {
		super(x, y);
		width = w;
		platformBox = new Rectangle(x, y, width, 14);
	}

	protected Rectangle getPlatformBox() {
		return platformBox;
	}

	protected void paintPlatform(Graphics g) {
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
