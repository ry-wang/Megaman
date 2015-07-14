//Imports needed
import java.awt.Color;
import java.awt.Graphics;

/**
 * @ Description: background class, creates all the background walls
 * @ Author: Ryan Wang
 * @ Version: v1.0
 * June 14th, 2015
 */

public class background {

	//Variables for each background wall object
	private int xPosition;
	private int yPosition;
	private int width;
	private int height;
	private String orientation;
	private String wallType;

	//Constructor for the background wall object
	background(int x, int y, int w, int h, String orient, String type) {
		xPosition = x;
		yPosition = y;
		width = w;
		height = h;
		orientation = orient;
		wallType = type;
	}//End constructor

	//Method that returns wall's x position
	public int getX() {
		return xPosition;
	}//End getX method

	//Method that returns wall's y position
	public int getY() {
		return yPosition;
	}//End getY method

	//Method that returns wall's width
	public int getWidth() {
		return width;
	}//End getWidth method

	//Method that returns wall's height
	public int getHeight() {
		return height;
	}//End getHeight method

	//Gets the orientation of the wall
	public String getOrientation() {
		return orientation;
	}//End getOrientation method
	
	//Gets the type of wall
	public String getType() {
		return wallType;
	}//End getType method

}//End of background class
