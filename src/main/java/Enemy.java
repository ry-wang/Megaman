package main.java;

import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * @ Description: Enemy class for game, controls activity for enemies
 * @ Author: Ryan Wang
 * @ Version: v1.0
 * September 2016
 */

public class Enemy extends Object {

	private int imageNum = 1;
	private int timesHit = 0;
	private boolean isDestroyed = false;
	private String motion;
	private boolean deathAnimationComplete = false;
	private boolean intersectsWall = false;
	private int delayAnimation = 0;
	private Rectangle enemyBox;

	Enemy(int level) {
		super(0, 0);
		generateSpawnLocation(level);
		enemyBox = new Rectangle(x, y, 50, 40);
	}

	protected void setX(int input) {
		x = input;
	}

	protected boolean getIntersectsWall() {
		return intersectsWall;
	}

	protected void setIntersectsWall(boolean input) {
		intersectsWall = input;
	}

	protected int getImageNum() {
		return imageNum;
	}

	protected void setTimesHit(int input) {
		timesHit = input;
	}

	protected int getTimesHit() {
		return timesHit;
	}
	
	protected void setImageNum(int input) {
		imageNum = input;
	}

	protected void setDestroyed() {
		isDestroyed = true;
	}

	protected String getState() {
		return motion;
	}

	protected void setState(String input) {
		motion = input;
	}

	protected boolean deathAnimationComplete() {
		return deathAnimationComplete;
	}

	protected Rectangle getBox() {
		return enemyBox;
	}

	protected void move() {
		int speed = 2;
		if (motion.equalsIgnoreCase("Walk Right")) {
			x += speed;
		}
		else {
			x -= speed;
		}
		enemyBox.setLocation(x, y);
	}

	protected void updateEnemyState() {
		int delayGoal = 1;
		if (!isDestroyed) {
	    	//Delays the animation slightly so it looks more real
			if (delayAnimation == delayGoal) {
				delayAnimation = 0;
				imageNum++;
			}
			else {
				delayAnimation++;
			}
			if (imageNum == 8) {
				imageNum = 1;
			}
		}
		else {
			enemyBox = null;
			if (!deathAnimationComplete) {
				imageNum++;
				if (imageNum == 11) {
					deathAnimationComplete = true;
				}
			}
		}
	}

	protected void paintEnemy(Graphics g) {
		try {
			if (!isDestroyed) {
				g.setColor(Color.red);
				int enemyHealth = 50 - (timesHit * 10);
				g.fillRect(x, y - 10, enemyHealth, 3);

				String direction = motion.equalsIgnoreCase("Walk Left") ? "L" : "";
				String imagePath = "/main/resources/images/E" + direction + imageNum + ".png";
				g.drawImage(ImageIO.read(this.getClass().getResource(imagePath)), x, y, null);
			}
			else {
				if (!deathAnimationComplete) {
					g.drawImage(ImageIO.read((this.getClass().getResource("/main/resources/images/EN" + imageNum + ".png"))), x, y, null);
				}
			}
		}
		catch (IOException e) {
			System.out.println("Error loading images");
		}
	}

	protected void generateSpawnLocation(int level) {
		boolean valid = false;
		int direction = (int)(Math.random() * 2 + 1);
		int ySetting = (int)(Math.random() * 4 + 1);

		motion = (direction == 1) ? "Walk Right" : "Walk Left";

		//Keeps on looping until valid numbers are generated
		while (!valid) {
			x = (int)(Math.random() * 1000 + 300);

			if (ySetting == 1) {
				y = 300;
			}
			else if (ySetting == 2) {
				y = 334;
			}
			else if (ySetting == 3 || ySetting == 4) {
				//Each level has different yPosition based on different platform placement
				if (level == 1 || level == 3) {
					y = 160;
				}
				else if (level == 2) {
					y = 235;
				}
			}
			//Testing to make sure xPosition is in specified spot based on yPosition
			if (y == 300) {
				if (x > 292 && x < 425) {
					valid = true;
				}
				else if (x > 680 && x < 824) {
					valid = true;
				}
			}
			else if (y == 334) {
				if (x > 485 && x < 622) {
					valid = true;
				}
			}

			//Testing to make sure xPosition is in specified spot based on yPosition and level (for platforms)
			if (level == 1 || level == 3) {
				if (y == 160) {
					if (x > 350 && x < 460) {
						valid = true;
					}
				}
				else if (y == 235) {
					if (x > 605 && x < 745) {
						valid = true;
					}
				}
			}
			else if (level == 2) {
				if (y == 235) {
					if (x > 127 && x < 258 || x > 515 && x < 620) {
						valid = true;
					}
				}
			}
		}
	}

}
