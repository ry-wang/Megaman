//Imports needed
import java.awt.Graphics;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @ Description: healthPack class, creates the health boost object in each level
 * @ Author: Ryan Wang
 * @ Version: v1.0
 * June 14th, 2015
 */

public class healthPack {
	
	//Attributes of the healthpack
	private int xPosition;
	private int yPosition;

	healthPack(int level) {
		//Places the healthpack at various positions based on level
		if (level == 1) {
			xPosition = 20;
			yPosition = 162;
		}
		else if(level == 2) {
			xPosition = 370;
			yPosition = 323;
		}
		else if(level == 3) {
			xPosition = 20;
			yPosition = 162;
		}
	}

	//Method for getting xPosition
	public int getX() {
		return xPosition;
	}//End getX method

	//Method for getting yPosition
	public int getY() {
		return yPosition;
	}//End getY position

	//Paint method
	public void paintPack(Graphics g) {
		try {
			//Paints health pack at x and y position
			g.drawImage(ImageIO.read((this.getClass().getResource("/images/healthBoost.png"))), xPosition, yPosition, null);
		}
		catch (IOException e) {
			System.out.println("error");
		}
	}//End paintPack method

}//End healthPack class
