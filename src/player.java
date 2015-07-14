//Imports needed
import java.awt.Graphics;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * @ Description: player class, everything to do with the player object in the game is run here
 * @ Author: Ryan Wang
 * @ Version: v1.0
 * June 14th, 2015
 */


public class player {

	//Attributes of the player object
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

	//Constructor for the player, sets values based on what was received from the game class
	player (int x, int y, int h, String state, String direction) {
		xPosition = x;
		yPosition = y;
		health = h;
		characterState = state;
		characterDirection = direction;
	}//End player constructor

	//Method that tests whether character is moving up or not
	public boolean isUp() {
		return movingUp;
	}//End isUp method

	//Method that tests whether character is moving down or not
	public boolean isDown() {
		return movingDown;
	}//End isDown method

	//Method that sets the up and down state of the player
	public void setUpDown(boolean input1, boolean input2) {
		movingUp = input1;
		movingDown = input2;
	}//End setUpDown method

	//Method for getting xPosition
	public int getX() {
		return xPosition;
	}//End getX method

	//Method for getting yPosition
	public int getY() {
		return yPosition;
	}//End getY position

	//Method that sets the player to running or not
	public void setRunning(boolean input) {
		isRunning = input;
	}//End setRunning method
	
	//Method that checks if player is running or not
	public boolean isRunning() {
		return isRunning;
	}//End isRunning method
	
	//Method that sets the bullet count of the player
	public void setBulletCount(int input) {
		bulletCount = input;
	}//End of setBulletCount method
	
	//Method that gets how many bullets the player still has
	public int getBulletCount() {
		return bulletCount;
	}//End of getBulletCount method

	//Method that returns health
	public int getHealth() {
		return health;
	}//End getHealth method

	//Method that sets player health based on input
	public void setHealth(int input) {
		health = input;
	}//End setHealth method

	//Method that sets x position of player based on input
	public void setX(int input) {
		xPosition = input;
	}//End of setX method
	
	//Method that sets y position of player based on input
	public void setY(int input) {
		yPosition = input;
	}//End setY method

	//Method that sets the state of the object based on input
	public void setState(String input) {
		characterState = input;
	}//End setState method

	//Method that sets the direction of the object based on input
	public void setDirection(String input) {
		characterDirection = input;
	}//End setDirection method

	//Checks if character if jumping
	public boolean isJumping() {
		return isJumping;
	}//End isJumping method
	
	//Sets the player to jumping or not based on input
	public void setJumping(boolean input) {
		isJumping = input;
	}//End setJumping method
	
	//Checks if the player is onGround or not based on input
	public boolean onGround() {
		return onGround;
	}//End onGround method
	
	//Sets the player to onGround or not based on input
	public void setOnGround(boolean input) {
		onGround = input;
	}//End setOnGround method

	//Method that returns the state of character
	public String getState() {
		return characterState;
	}//End getState method

	//Method that returns the direction of character
	public String getDirection() {
		return characterDirection;
	}//End getDirection method

	//Sets imageNum based on input
	public void setImageNum(int input) {
		imageNum = input;
	}//End setImageNum method
	
	//Gets the imageNum of the object
	public int getImageNum() {
		return imageNum;
	}//End getImageNum method
	
	//Method that moves right
	public void moveRight() {
		xPosition += xSpeed;
	}//End moveRight method
	
	//Method that moves left
	public void moveLeft() {
		xPosition -= xSpeed;
	}//End moveLeft method
	
	//Method that moves up
	public void moveUp() {
		yPosition -= ySpeed;
	}//End moveUp method
	
	//Method that moves down
	public void moveDown() {
		yPosition += ySpeed;
	}//End moveDown method

	//Paint method
	public void paintPlayer (Graphics g)  {
		
		//Paints still animation images if character is still
		if (characterState.equalsIgnoreCase("Still")) {
			try {
				//Paints different image based on what direction the player is facing
				if (characterDirection.equalsIgnoreCase("Right")) {
					g.drawImage(ImageIO.read((this.getClass().getResource("/images/S" + imageNum + ".png"))), xPosition, yPosition, null);
				}
				else {
					g.drawImage(ImageIO.read((this.getClass().getResource("/images/LS" + imageNum + ".png"))), xPosition, yPosition, null);
				}
				//Loops through the animation
				imageNum++;
				if (imageNum == 4) {
					imageNum = 1;
				}
			}
			catch (IOException e) {
				System.out.println("Error");
			}
		}
		//Paints the running images
		if (characterState.equalsIgnoreCase("Run")) {
			try {
				//Paints different image based on what direction the player is facing
				if (characterDirection.equalsIgnoreCase("Right")) {
					g.drawImage(ImageIO.read((this.getClass().getResource("/images/R" + imageNum + ".png"))), xPosition, yPosition, null);
				}
				else {

					g.drawImage(ImageIO.read((this.getClass().getResource("/images/LR" + imageNum + ".png"))), xPosition, yPosition, null);
				}
				//Loops through the animation
				imageNum++;
				if (imageNum == 12) {
					imageNum = 1;
				}
			}
			catch (IOException e) {
				System.out.println("Error");
			}
		}
		//Paints the jumping images
		if (characterState.equalsIgnoreCase("Jump")) {
			try {
				//Paints different image based on what direction the player is facing
				if (characterDirection.equalsIgnoreCase("Right")) {
					g.drawImage(ImageIO.read((this.getClass().getResource("/images/J" + imageNum + ".png"))), xPosition, yPosition, null);
				}
				else {
					g.drawImage(ImageIO.read((this.getClass().getResource("/images/LJ" + imageNum + ".png"))), xPosition, yPosition, null);
				}
				//Loops through the animation
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
		//Paints the jumping and moving images
		if (characterState.equalsIgnoreCase("Jump Move")) {
			try {
				//Paints different image based on what direction the player is facing
				if (characterDirection.equalsIgnoreCase("Right")) {
					g.drawImage(ImageIO.read((this.getClass().getResource("/images/J" + imageNum + ".png"))), xPosition, yPosition, null);
				}
				else {

					g.drawImage(ImageIO.read((this.getClass().getResource("/images/LJ" + imageNum + ".png"))), xPosition, yPosition, null);
				}
				//Loops through the animation
				imageNum++;
				if (imageNum == 9) {
					imageNum = 1;
					isJumping = false;
					//If player isn't jumping anymore, sets state to run
					characterState = "Run";
				}
			}
			catch (IOException e) {
				System.out.println("Error");
			}
		}
		//Paints the shooting animation images
		if (characterState.equalsIgnoreCase("Shoot")) {
			try {
				//Paints different image based on what direction the player is facing
				if (characterDirection.equalsIgnoreCase("Right")) {
					g.drawImage(ImageIO.read((this.getClass().getResource("/images/SH" + imageNum + ".png"))), xPosition, yPosition, null);
				}
				else {
					g.drawImage(ImageIO.read((this.getClass().getResource("/images/LSH" + imageNum + ".png"))), xPosition, yPosition, null);
				}
				//Loops through the animation
				imageNum++;
				if (imageNum == 3) {
					imageNum = 1;
					//Sets character state to still after shooting
					characterState = "Still";
				}
			}
			catch (IOException e) {
				System.out.println("Error");
			}
		}
	}//End of paintPlayer class
	
}//End of player class
