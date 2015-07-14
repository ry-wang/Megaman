//Imports needed
import java.awt.Color;
import java.awt.Graphics;

/**
 * @ Description: Shot class, used for creating the player shots
 * @ Author: Ryan Wang
 * @ Version: v1.0
 * June 12th, 2015
 */

public class shot {

	//Variables to hold data 
	private int xPosition;
	private int yPosition;
	private int speed = 20;
	private int radius;
	private String direction;

	//Constructor for shot class, sets data received to variables in this class
	shot (int x, int y, int r, String directionInput) {
		xPosition = x;
		yPosition = y;
		radius = r;
		direction = directionInput;
	}

	//Move shot method, based on direction
	public void moveShot() {
		if (direction.equalsIgnoreCase("Right")) {
			xPosition += speed;
		}
		else {
			xPosition -= speed;
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

		//Sets color to red, draws a circle at the spacecraft's x and y position
		g.setColor(Color.BLUE);
		g.fillOval(xPosition, yPosition, radius, radius);

	}//End paint method

}//End shot class
