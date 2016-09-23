import java.awt.Graphics;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * @ Description: player class, everything to do with the player object in the game is run here
 * @ Author: Ryan Wang
 * @ Version: v2.0
 * September 2016
 */


public class player {

	private int xPosition;
	private int yPosition;
	private int xSpeed = 8;
	private int ySpeed = 20;
	private int bulletCount = 5;
	private int imageNum = 1;
	private int health = 100;
	private String characterState;
	private String characterDirection;
	private boolean isJumping = false;
	private boolean isRunning = false;
	private boolean movingUp = false;
	private boolean movingDown = false;
	private boolean onGround = true;

	player (int x, int y, int h, String state, String direction) {
		xPosition = x;
		yPosition = y;
		health = h;
		characterState = state;
		characterDirection = direction;
	}

	public boolean isUp() {
		return movingUp;
	}

	public boolean isDown() {
		return movingDown;
	}

	public void setUpDown(boolean input1, boolean input2) {
		movingUp = input1;
		movingDown = input2;
	}

	public int getX() {
		return xPosition;
	}

	public int getY() {
		return yPosition;
	}

	public void setRunning(boolean input) {
		isRunning = input;
	}//End setRunning method
	
	public boolean isRunning() {
		return isRunning;
	}//End isRunning method
	
	public void setBulletCount(int input) {
		bulletCount = input;
	}//End of setBulletCount method
	
	public int getBulletCount() {
		return bulletCount;
	}//End of getBulletCount method

	public int getHealth() {
		return health;
	}//End getHealth method

	public void setHealth(int input) {
		health = input;
	}//End setHealth method

	public void setX(int input) {
		xPosition = input;
	}//End of setX method
	
	public void setY(int input) {
		yPosition = input;
	}//End setY method

	public void setState(String input) {
		characterState = input;
	}//End setState method

	public void setDirection(String input) {
		characterDirection = input;
	}//End setDirection method

	public boolean isJumping() {
		return isJumping;
	}//End isJumping method
	
	public void setJumping(boolean input) {
		isJumping = input;
	}//End setJumping method
	
	public boolean onGround() {
		return onGround;
	}//End onGround method
	
	public void setOnGround(boolean input) {
		onGround = input;
	}//End setOnGround method

	public String getState() {
		return characterState;
	}//End getState method

	public String getDirection() {
		return characterDirection;
	}//End getDirection method

	public void setImageNum(int input) {
		imageNum = input;
	}//End setImageNum method
	
	public int getImageNum() {
		return imageNum;
	}//End getImageNum method
	
	public void moveRight() {
		xPosition += xSpeed;
	}//End moveRight method
	
	public void moveLeft() {
		xPosition -= xSpeed;
	}//End moveLeft method
	
	public void moveUp() {
		yPosition -= ySpeed;
	}//End moveUp method
	
	public void moveDown() {
		yPosition += ySpeed;
	}//End moveDown method

	public void paintPlayer (Graphics g)  {
		if (characterState.equalsIgnoreCase("Still")) {
			try {
				if (characterDirection.equalsIgnoreCase("Right")) {
					g.drawImage(ImageIO.read((this.getClass().getResource("/images/S" + imageNum + ".png"))), xPosition, yPosition, null);
				}
				else {
					g.drawImage(ImageIO.read((this.getClass().getResource("/images/LS" + imageNum + ".png"))), xPosition, yPosition, null);
				}
				imageNum++;
				if (imageNum == 4) {
					imageNum = 1;
				}
			}
			catch (IOException e) {
				System.out.println("Error");
			}
		}
		if (characterState.equalsIgnoreCase("Run")) {
			try {
				if (characterDirection.equalsIgnoreCase("Right")) {
					g.drawImage(ImageIO.read((this.getClass().getResource("/images/R" + imageNum + ".png"))), xPosition, yPosition, null);
				}
				else {
					g.drawImage(ImageIO.read((this.getClass().getResource("/images/LR" + imageNum + ".png"))), xPosition, yPosition, null);
				}
				imageNum++;
				if (imageNum == 12) {
					imageNum = 1;
				}
			}
			catch (IOException e) {
				System.out.println("Error");
			}
		}
		if (characterState.equalsIgnoreCase("Jump")) {
			try {
				if (characterDirection.equalsIgnoreCase("Right")) {
					g.drawImage(ImageIO.read((this.getClass().getResource("/images/J" + imageNum + ".png"))), xPosition, yPosition, null);
				}
				else {
					g.drawImage(ImageIO.read((this.getClass().getResource("/images/LJ" + imageNum + ".png"))), xPosition, yPosition, null);
				}
				imageNum++;
				if (imageNum == 9) {
					imageNum = 1;
					isJumping = false;
					characterState = "Still";
				}
			}
			catch (IOException e) {
				System.out.println("Error");
			}
		}
		if (characterState.equalsIgnoreCase("Jump Move")) {
			try {
				if (characterDirection.equalsIgnoreCase("Right")) {
					g.drawImage(ImageIO.read((this.getClass().getResource("/images/J" + imageNum + ".png"))), xPosition, yPosition, null);
				}
				else {
					g.drawImage(ImageIO.read((this.getClass().getResource("/images/LJ" + imageNum + ".png"))), xPosition, yPosition, null);
				}
				imageNum++;
				if (imageNum == 9) {
					imageNum = 1;
					isJumping = false;
					characterState = "Run";
				}
			}
			catch (IOException e) {
				System.out.println("Error");
			}
		}
		if (characterState.equalsIgnoreCase("Shoot")) {
			try {
				if (characterDirection.equalsIgnoreCase("Right")) {
					g.drawImage(ImageIO.read((this.getClass().getResource("/images/SH" + imageNum + ".png"))), xPosition, yPosition, null);
				}
				else {
					g.drawImage(ImageIO.read((this.getClass().getResource("/images/LSH" + imageNum + ".png"))), xPosition, yPosition, null);
				}
				imageNum++;
				if (imageNum == 3) {
					imageNum = 1;
					characterState = "Still";
				}
			}
			catch (IOException e) {
				System.out.println("Error");
			}
		}
	}
	
}
