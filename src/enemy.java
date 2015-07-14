//Imports needed
import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * @ Description: enemy class for summative game, controls activity for enemies
 * @ Author: Ryan Wang
 * @ Version: v1.0
 * June 13th, 2015
 */

public class enemy {
	
	//Creation of all variables/attributes of each enemy
	private int xPosition;
	private int yPosition;
	private int imageNum = 1;
	private int timesHit = 0;
	private boolean isDestroyed = false;
	private String motion;
	private int speed = 2;
	private boolean deathAnimationComplete = false;
	private boolean intersectsWall = false;
	private int delayAnimation = 0;
	private int delayGoal = 1;
	private int level;
	
	//Enemy constructor, only level is sent from game class
	enemy (int l) {
		level = l;
		//Randomize values
		//Valid is false until a valid x and y set of coordinates is generated (enemy won't spawn midair)
		boolean valid = false;
		//Generates direction
		int direction = (int)(Math.random() * 2 + 1);
		//Generates a ySetting, each one corresponding to a specific platform / ground
		int ySetting = (int)(Math.random() * 4 + 1);
		//Sets direction to right or left based on generated value
		if (direction == 1) {
			motion = "Walk Right";
		}
		else if (direction == 2) {
			motion = "Walk Left";
		}
		//Keeps on looping until valid numbers are generated
		while (valid == false) {
			//Generates xPosition
			xPosition = (int)(Math.random() * 1000 + 300);
			//Sets y position based on ySetting generated
			if (ySetting == 1) {
				yPosition = 300;
			}
			else if (ySetting == 2) {
				yPosition = 334;
			}
			else if (ySetting == 3) {
				//Each level has different yPosition based on different platform placement
				if ((level == 1) || (level == 3)) {
					yPosition = 160;
				}
				if (level == 2) {
					yPosition = 235;
				}
			}
			else if (ySetting == 4) {
				//Each level has different yPosition based on different platform placement
				if ((level == 1) || (level == 3)) {
					yPosition = 160;
				}
				if (level == 2) {
					yPosition = 235;
				}
			}
			//Testing to make sure xPosition is in specified spot based on yPosition
			if (yPosition == 300) {
				if ((xPosition > 292) && (xPosition < 425)) {
					valid = true;
				}
				else if ((xPosition > 680) && (xPosition < 824)) {
					valid = true;
				}
			}
			//Testing to make sure xPosition is in specified spot based on yPosition
			if (yPosition == 334) {
				if ((xPosition > 485) && (xPosition < 622)) {
					valid = true;
				}
			}
			//Testing to make sure xPosition is in specified spot based on yPosition and level (for platforms)
			if ((level == 1) || (level == 3)) {
				if (yPosition == 160) {
					if ((xPosition > 350) && (xPosition < 460)) {
						valid = true;
					}
				}
				if (yPosition == 235) {
					if ((xPosition > 605) && (xPosition < 745)) {
						valid = true;
					}
				}
			}
			//Testing to make sure xPosition is in specified spot based on yPosition and level (for platforms)
			else if (level == 2) {
				if (yPosition == 235) {
					if (((xPosition > 127) && (xPosition < 258)) || ((xPosition > 515) && (xPosition < 620))) {
						valid = true;
					}
				}
			}
		}
	}//End of constructor for enemy
	
	//Method for getting xPosition
	public int getX() {
		return xPosition;
	}//End getX method

	//Method for getting yPosition
	public int getY() {
		return yPosition;
	}//End getY position

	//Method that sets xPosition based on input
	public void setX(int input) {
		xPosition = input;
	}//End setX method

	//Method that tests whether object is intersecting wall
	public boolean getIntersectsWall() {
		return intersectsWall;
	}//End of getIntersectsWall method
	
	//Sets the attribute of the object to intersecting or not based on input
	public void setIntersectsWall(boolean input) {
		intersectsWall = input;
	}//End setIntersectsWall method

	//Method that gets currentImageNum in the animation cycle
	public int getImageNum() {
		return imageNum;
	}//End of getImgaeNum method

	//Method that sets how many times the enemy has been hit by a player bullet
	public void setTimesHit(int input) {
		timesHit = input;
	}//End setTimesHit method
	
	//Method that returns how many times the object has been hit
	public int getTimesHit() {
		return timesHit;
	}//End getTimesHit method
	
	//Method that sets the animation number
	public void setImageNum(int input) {
		imageNum = input;
	}//End setImageNum method

	//Method that sets the object to destroyed or not
	public void setDestroyed(boolean input) {
		isDestroyed = input;
	}//End setDestroyed method

	//Method that checks if object is destroyed or not
	public boolean isDestroyed() {
		return isDestroyed;
	}//End isDestroyed method

	//Method that gets the current direction of the enemy
	public String getState() {
		return motion;
	}//End getState method

	//Method that sets the direction of the enemy
	public void setState(String input) {
		motion = input;
	}//End setState method

	//Method that moves enemy based on direction
	public void moveEnemy() {
		if (motion.equalsIgnoreCase("Walk Right")) {
			xPosition += speed;
		}
		if (motion.equalsIgnoreCase("Walk Left")){
			xPosition -= speed;
		}
	}//End moveEnemy method

	//Paint method for the enemy ship
	public void paintEnemy(Graphics g) {
		//Paints through the moving image cycle if not destroyed
		if (isDestroyed == false) {
			g.setColor(Color.red);
			int enemyHealth = 50 - (timesHit * 10);
			//Creates a health bar for the enemy if it has been hit more than once
			if (timesHit > 0) {
				g.fillRect(xPosition, yPosition - 10, enemyHealth, 3);
			}
			//Draws each image for moving right
			if (motion.equalsIgnoreCase("Walk Right")) {
				try {
					g.drawImage(ImageIO.read((this.getClass().getResource("/images/E" + imageNum + ".png"))), xPosition, yPosition, null);
					//Delays the animation slightly so it looks more real
					if (delayAnimation == delayGoal) {
						delayAnimation = 0;
						imageNum++;
					}
					else {
						delayAnimation++;
					}
					//This lets the animation forever loop until the enemy is destroyed
					if (imageNum == 8) {
						imageNum = 1;
					}
				}
				catch (IOException e) {
					System.out.println("error");
				}
			}
			//Same code as above, only image is different because enemy is moving left
			else {
				try {
					g.drawImage(ImageIO.read((this.getClass().getResource("/images/EL" + imageNum + ".png"))), xPosition, yPosition, null);
					if (delayAnimation == delayGoal) {
						delayAnimation = 0;
						imageNum++;
					}
					else {
						delayAnimation++;
					}
					//This lets the animation forever loop until the enemy is destroyed
					if (imageNum == 8) {
						imageNum = 1;
					}
				}
				catch (IOException e) {
					System.out.println("error");
				}
			}
		}
		//If enemy has been destroyed, runs this code
		else {
			//Loops through the explosion animation
			if (deathAnimationComplete == false) {
				try {
					g.drawImage(ImageIO.read((this.getClass().getResource("/images/EN" + imageNum + ".png"))), xPosition, yPosition, null);
					imageNum++;
					if (imageNum == 11) {
						deathAnimationComplete = true;
					}
				}
				catch (IOException e) {
					System.out.println("error");
				}
			}
		}
	}//End of paintEnemy method

}//End of class
