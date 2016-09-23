import java.awt.Graphics;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * @ Description: healthPack class, creates the health boost object in each level
 * @ Author: Ryan Wang
 * @ Version: v1.0
 * September 2016
 */

public class healthPack {
	
	private int xPosition;
	private int yPosition;

	healthPack(int level) {
		if (level == 1) {
			xPosition = 20;
			yPosition = 162;
		}
		else if (level == 2) {
			xPosition = 370;
			yPosition = 323;
		}
		else if (level == 3) {
			xPosition = 20;
			yPosition = 162;
		}
	}

	public int getX() {
		return xPosition;
	}

	public int getY() {
		return yPosition;
	}

	public void paintPack(Graphics g) {
		try {
			g.drawImage(ImageIO.read((this.getClass().getResource("/images/healthBoost.png"))), xPosition, yPosition, null);
		}
		catch (IOException e) {
			System.out.println("error");
		}
	}

}
