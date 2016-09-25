//Imports needed
import java.awt.Color;
import java.awt.Graphics;

/**
 * @ Description: Shot class, used for creating the player shots
 * @ Author: Ryan Wang
 * @ Version: v2.0
 * September 2016
 */

public class shot extends Object {

	private int radius;
	private String direction;

	shot(int x, int y, int r, String directionInput) {
		super(x, y);
		radius = r;
		direction = directionInput;
	}

	public void moveShot() {
		final int speed = 20;
		if (direction.equalsIgnoreCase("Right")) {
			x += speed;
		} else {
			x -= speed;
		}
	}

	public String getDirection() {
		return direction;
	}

	public int getX() {
		return x;
	}


	public int getY() {
		return y;
	}

	public void paintShot (Graphics g)  {

		g.setColor(Color.BLUE);
		g.fillOval(x, y, radius, radius);

	}
}
