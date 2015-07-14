//Imports needed
import java.awt.Graphics;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @ Description: turret class, creates the turret objects and paints them
 * @ Author: Ryan Wang
 * @ Version: v1.0
 * June 14th, 2015
 */

public class turret {

	//Variables for each turret object
	private int xPosition;
	private int yPosition;
	private int type;
	private String direction;

	//Constructor for turret object, sets values based on inputs
	turret (int x, int y, int t, String d) {
		xPosition = x;
		yPosition = y;
		type = t;
		direction = d;
	}//End constructor

	//Method for getting xPosition
	public int getX() {
		return xPosition;
	}//End getX method

	//Method for getting yPosition
	public int getY() {
		return yPosition;
	}//End getY position

	//Method that paints the turret
	public void paintTurrets(Graphics g) {
		try {
			//Paints different turrets based on direction and type
			if (type == 1) {
				if (direction.equalsIgnoreCase("Right")) {
					g.drawImage(ImageIO.read((this.getClass().getResource("/images/RTurret1.png"))), xPosition, yPosition, null);
				}
				else {
					g.drawImage(ImageIO.read((this.getClass().getResource("/images/LTurret1.png"))), xPosition, yPosition, null);
				}
			}
			else {
				g.drawImage(ImageIO.read((this.getClass().getResource("/images/turret2.png"))), xPosition, yPosition, null);
			}
		}
		catch (IOException e) {
			System.out.println("error");
		}
	}//End paintTurrets method

}//End turret class
