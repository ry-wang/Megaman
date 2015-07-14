//Imports needed
import java.awt.Graphics;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @ Description: platforms class, used for creating the platform objects in each level
 * @ Author: Ryan Wang
 * @ Version: v1.0
 * June 14th, 2015
 */


public class platforms {
	
	//Attributes of each platform object
	private int xPosition;
	private int yPosition;
	private int width;
	private int height = 14;
	
	//Constructor for the platform, sets each object's attributes to the data received from the game class
	platforms (int x, int y, int w) {
		xPosition = x;
		yPosition = y;
		width = w;
	}//End constructor

	//Method that returns platform's x position
	public int getX() {
		return xPosition;
	}//End getX method
	
	//Method that returns platform's y position
	public int getY() {
		return yPosition;
	}//End getY method
	
	//Method that returns platform's width
	public int getWidth() {
		return width;
	}//End getWidth method
	
	//Method that returns platform's height
	public int getHeight() {
		return height;
	}//End getHeight method
	
	//Method that paints all the platforms based on width
	public void paintPlatforms(Graphics g) {
		try {
			//Paints different size platforms based on width
			if (width == 100) {
				g.drawImage(ImageIO.read((this.getClass().getResource("/images/platform1.png"))), getX(), getY(), null);
			}
			else if (width == 150) {
				g.drawImage(ImageIO.read((this.getClass().getResource("/images/platform2.png"))), getX(), getY(), null);
			}
			else if (width == 175) {
				g.drawImage(ImageIO.read((this.getClass().getResource("/images/platform3.png"))), getX(), getY(), null);
			}
			else if (width == 200) {
				g.drawImage(ImageIO.read((this.getClass().getResource("/images/platform4.png"))), getX(), getY(), null);
			}
		}
		catch (IOException e) {
			System.out.println("error");
		}
		//g.fillRect(xPosition, yPosition, getWidth(), getHeight());
	}//End paintPlatforms method
}//End platforms class
