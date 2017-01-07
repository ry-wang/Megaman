package main.java;

import org.w3c.dom.css.Rect;

import java.awt.*;
import java.lang.*;

/**
 * @ Description: TurretShot class, used for creating the turret shots
 * @ Author: Ryan Wang
 * @ Version: v2.0
 * September 2016
 */


public class TurretShot extends Object {

	private int radius;
	private String direction;
	private Rectangle shotBox;

	TurretShot (int x, int y, int r, String directionInput) {
		super(x, y);
		radius = r;
		direction = directionInput;
		shotBox = new Rectangle(x, y, radius, radius);
	}

	protected void moveShot() {
		int speed = 10;
		if (direction.equalsIgnoreCase("Right")) {
			x += speed;
		}
		else if (direction.equalsIgnoreCase("Left")) {
			x -= speed;
		}
		else {
			y -= speed;
		}
		shotBox.setLocation(x, y);
	}

	protected Rectangle getShotBox() {
		return shotBox;
	}


	protected String getDirection() {
		return direction;
	}

	protected void paintShot (Graphics g)  {
		g.setColor(Color.RED);
		g.fillOval(x, y, radius, radius);

	}

}
