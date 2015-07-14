//Imports needed
import java.awt.Color;
import java.awt.Graphics;

/**
 * @ Description: turretShot class, used for creating the turret shots
 * @ Author: Ryan Wang
 * @ Version: v1.0
 * June 14th, 2015
 */


public class turretShot {

	//Variables to hold data 
	private int xPosition;
	private int yPosition;
	private int speed = 10;
	private int radius;
	private String direction;

	//Constructor for turretShot class, sets data received to variables in this class
	turretShot (int x, int y, int r, String directionInput) {
		xPosition = x;
		yPosition = y;
		radius = r;
		direction = directionInput;
	}//End constructor

	//Move shot method
	public void moveShot() {
		if (direction.equalsIgnoreCase("Right")) {
			xPosition += speed;
		}
		else if (direction.equalsIgnoreCase("Left")) {
			xPosition -= speed;
		}
		//Moves shot up, since one turret fires up
		else {
			yPosition -= speed;
		}
	}//End moveShot method

	//Method that gets direction of shot
	public String getDirection() {
		return direction;
	}//End getDirection method
	
	//Method for getting x Position
	public int getX() {
		return xPosition;
	}//end getX method

	//Method for getting y Position
	public int getY() {
		return yPosition;
	}//end getY method

	//Paint class for drawing shots
	public void paintShot (Graphics g)  {

		//Sets color to red, draws a circle at the turret shot's current x and y position
		g.setColor(Color.RED);
		g.fillOval(xPosition, yPosition, radius, radius);

	}//End paint method

}//End shot class
