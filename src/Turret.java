import java.awt.Graphics;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @ Description: Turret class, creates the turret objects and paints them
 * @ Author: Ryan Wang
 * @ Version: v2.0
 * September 2016
 */

public class Turret extends Object{

	private int type;
	private String direction;

	Turret(int x, int y, int t, String d) {
		super(x, y);
		type = t;
		direction = d;
	}

	protected void paintTurrets(Graphics g) {
		try {
			//Paints different turrets based on direction and type
			if (type == 1) {
				if (direction.equalsIgnoreCase("Right")) {
					g.drawImage(ImageIO.read((this.getClass().getResource("/images/RTurret1.png"))), x, y, null);
				} else {
					g.drawImage(ImageIO.read((this.getClass().getResource("/images/LTurret1.png"))), x, y, null);
				}
			} else {
				g.drawImage(ImageIO.read((this.getClass().getResource("/images/turret2.png"))), x, y, null);
			}
		}
		catch (IOException e) {
			System.out.println("error");
		}
	}
}
