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

	public boolean isDown() {
		return movingDown;
	}

	public void setUpDown(boolean input1, boolean input2) {
		movingUp = input1;
		movingDown = input2;
	}

	public void setRunning(boolean input) {
		isRunning = input;
	}
	
	public boolean isRunning() {
		return isRunning;
	}
	
	public void setBulletCount(int input) {
		bulletCount = input;
	}
	
	public int getBulletCount() {
		return bulletCount;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int input) {
		health = input;
	}

	public void setX(int input) {
		x = input;
	}
	
	public void setY(int input) {
		y = input;
	}

	public void setState(String input) {
		characterState = input;
	}

	public void setDirection(String input) {
		characterDirection = input;
	}

	public boolean isJumping() {
		return isJumping;
	}
	
	public void setJumping(boolean input) {
		isJumping = input;
	}
	
	public boolean onGround() {
		return onGround;
	}
	
	public void setOnGround(boolean input) {
		onGround = input;
	}

	public String getState() {
		return characterState;
	}

	public String getDirection() {
		return characterDirection;
	}

	public void setImageNum(int input) {
		imageNum = input;
	}
	
	public int getImageNum() {
		return imageNum;
	}
	
	public void moveRight() {
		x += xSpeed;
	}
	
	public void moveLeft() {
		x -= xSpeed;
	}
	
	public void moveUp() {
		y -= ySpeed;
	}
	
	public void moveDown() {
		y += ySpeed;
	}

	public void paintPlayer (Graphics g)  {
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
