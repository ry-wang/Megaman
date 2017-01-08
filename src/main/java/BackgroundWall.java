package main.java;

import java.awt.*;

/**
 * @ Description: background class, creates all the background walls
 * @ Author: Ryan Wang
 * @ Version: v2.0
 * September 2016
 */

public class BackgroundWall extends Object {

	private String orientation;
	private String wallType;
	private Rectangle wallBox;

	BackgroundWall(int x, int y, int w, int h, String orient, String type) {
		super(x, y);
		orientation = orient;
		wallType = type;
		wallBox = new Rectangle(x, y, w, h);
	}

	protected String getOrientation() {
		return orientation;
	}

	protected String getType() {
		return wallType;
	}

	protected Rectangle getBox() {
		return wallBox;
	}
}
