package main.java;

/**
 * @ Description: background class, creates all the background walls
 * @ Author: Ryan Wang
 * @ Version: v2.0
 * September 2016
 */

public class Background extends Object {

	private int width;
	private int height;
	private String orientation;
	private String wallType;

	Background(int x, int y, int w, int h, String orient, String type) {
		super(x, y);
		width = w;
		height = h;
		orientation = orient;
		wallType = type;
	}

	protected int getWidth() {
		return width;
	}

	protected int getHeight() {
		return height;
	}

	protected String getOrientation() {
		return orientation;
	}

	protected String getType() {
		return wallType;
	}
}
