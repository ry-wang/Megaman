/**
 * @ Description: background class, creates all the background walls
 * @ Author: Ryan Wang
 * @ Version: v2.0
 * September 2016
 */

public class background {

	private int xPosition;
	private int yPosition;
	private int width;
	private int height;
	private String orientation;
	private String wallType;

	background(int x, int y, int w, int h, String orient, String type) {
		xPosition = x;
		yPosition = y;
		width = w;
		height = h;
		orientation = orient;
		wallType = type;
	}

	public int getX() {
		return xPosition;
	}

	public int getY() {
		return yPosition;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getOrientation() {
		return orientation;
	}
	
	public String getType() {
		return wallType;
	}
}
