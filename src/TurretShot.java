import java.awt.Color;
import java.awt.Graphics;

/**
 * @ Description: TurretShot class, used for creating the turret shots
 * @ Author: Ryan Wang
 * @ Version: v2.0
 * September 2016
 */


public class TurretShot extends Object{

	private int radius;
	private String direction;

	TurretShot (int x, int y, int r, String directionInput) {
		super(x, y);
		radius = r;
		direction = directionInput;
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
	}


	protected String getDirection() {
		return direction;
	}

	protected void paintShot (Graphics g)  {
		g.setColor(Color.RED);
		g.fillOval(x, y, radius, radius);

	}

}
