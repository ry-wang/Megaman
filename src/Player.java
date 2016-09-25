import java.awt.Graphics;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * @ Description: player class, everything to do with the player object in the game is run here
 * @ Author: Ryan Wang
 * @ Version: v2.0
 * September 2016
 */


public class Player extends Object {

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

	Player(int x, int y, int h, String state, String direction) {
		super(x, y);
		health = h;
		characterState = state;
		characterDirection = direction;
	}

	protected boolean isDown() {
		return movingDown;
	}

	protected void setUpDown(boolean input1, boolean input2) {
		movingUp = input1;
		movingDown = input2;
	}

	protected void setRunning(boolean input) {
		isRunning = input;
	}

	protected boolean isRunning() {
		return isRunning;
	}

	protected void setBulletCount(int input) {
		bulletCount = input;
	}

	protected int getBulletCount() {
		return bulletCount;
	}

	protected int getHealth() {
		return health;
	}

	protected void setHealth(int input) {
		health = input;
	}

	protected void setX(int input) {
		x = input;
	}

	protected void setY(int input) {
		y = input;
	}

	protected void setState(String input) {
		characterState = input;
	}

	protected void setDirection(String input) {
		characterDirection = input;
	}

	protected boolean isJumping() {
		return isJumping;
	}

	protected void setJumping(boolean input) {
		isJumping = input;
	}

	protected boolean onGround() {
		return onGround;
	}

	protected void setOnGround(boolean input) {
		onGround = input;
	}

	protected String getState() {
		return characterState;
	}

	protected String getDirection() {
		return characterDirection;
	}

	protected void setImageNum(int input) {
		imageNum = input;
	}

	protected int getImageNum() {
		return imageNum;
	}

	protected void moveRight() {
		x += xSpeed;
	}

	protected void moveLeft() {
		x -= xSpeed;
	}

	protected void moveUp() {
		y -= ySpeed;
	}

	protected void moveDown() {
		y += ySpeed;
	}

	protected void paintPlayer (Graphics g)  {
		if (characterState.equalsIgnoreCase("Still")) {
			try {
				if (characterDirection.equalsIgnoreCase("Right")) {
					g.drawImage(ImageIO.read((this.getClass().getResource("/images/S" + imageNum + ".png"))), x, y, null);
				} else {
					g.drawImage(ImageIO.read((this.getClass().getResource("/images/LS" + imageNum + ".png"))), x, y, null);
				}
				imageNum++;
				if (imageNum == 4) {
					imageNum = 1;
				}
			}
			catch (IOException e) {
				System.out.println("Error loading image");
			}
		}
		if (characterState.equalsIgnoreCase("Run")) {
			try {
				if (characterDirection.equalsIgnoreCase("Right")) {
					g.drawImage(ImageIO.read((this.getClass().getResource("/images/R" + imageNum + ".png"))), x, y, null);
				} else {
					g.drawImage(ImageIO.read((this.getClass().getResource("/images/LR" + imageNum + ".png"))), x, y, null);
				}
				imageNum++;
				if (imageNum == 12) {
					imageNum = 1;
				}
			}
			catch (IOException e) {
				System.out.println("Error loading image");
			}
		}
		if (characterState.equalsIgnoreCase("Jump")) {
			try {
				if (characterDirection.equalsIgnoreCase("Right")) {
					g.drawImage(ImageIO.read((this.getClass().getResource("/images/J" + imageNum + ".png"))), x, y, null);
				}
				else {
					g.drawImage(ImageIO.read((this.getClass().getResource("/images/LJ" + imageNum + ".png"))), x, y, null);
				}
				imageNum++;
				if (imageNum == 9) {
					imageNum = 1;
					isJumping = false;
					characterState = "Still";
				}
			}
			catch (IOException e) {
				System.out.println("Error loading image");
			}
		}
		if (characterState.equalsIgnoreCase("Jump Move")) {
			try {
				if (characterDirection.equalsIgnoreCase("Right")) {
					g.drawImage(ImageIO.read((this.getClass().getResource("/images/J" + imageNum + ".png"))), x, y, null);
				}
				else {
					g.drawImage(ImageIO.read((this.getClass().getResource("/images/LJ" + imageNum + ".png"))), x, y, null);
				}
				imageNum++;
				if (imageNum == 9) {
					imageNum = 1;
					isJumping = false;
					characterState = "Run";
				}
			}
			catch (IOException e) {
				System.out.println("Error loading image");
			}
		}
		if (characterState.equalsIgnoreCase("Shoot")) {
			try {
				if (characterDirection.equalsIgnoreCase("Right")) {
					g.drawImage(ImageIO.read((this.getClass().getResource("/images/SH" + imageNum + ".png"))), x, y, null);
				} else {
					g.drawImage(ImageIO.read((this.getClass().getResource("/images/LSH" + imageNum + ".png"))), x, y, null);
				}
				imageNum++;
				if (imageNum == 3) {
					imageNum = 1;
					characterState = "Still";
				}
			}
			catch (IOException e) {
				System.out.println("Error loading image");
			}
		}
	}
	
}
