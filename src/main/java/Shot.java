package main.java;

import org.w3c.dom.css.Rect;

import java.awt.*;
import java.lang.*;

/**
 * @ Description: Shot class, used for creating the player shots
 * @ Author: Ryan Wang
 * @ Version: v2.0
 * September 2016
 */

public class Shot extends Object {

	private int radius;
	private String direction;
	private Rectangle shotBox;

	Shot(int x, int y, int r, String directionInput) {
		super(x, y);
		radius = r;
		direction = directionInput;
		shotBox = new Rectangle(x, y, radius, radius);
	}

	protected void moveShot() {
		final int speed = 20;
		if (direction.equalsIgnoreCase("Right")) {
			x += speed;
		} else {
			x -= speed;
		}
		shotBox.setLocation(x, y);
	}

	protected String getDirection() {
		return direction;
	}

	protected Rectangle getShotBox() {
		return shotBox;
	}

	protected void paintShot (Graphics g)  {
		g.setColor(Color.BLUE);
		g.fillOval(x, y, radius, radius);
	}
}
