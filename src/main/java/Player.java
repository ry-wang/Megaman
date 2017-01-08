package main.java;

import java.awt.*;
import java.io.IOException;
import java.lang.*;
import javax.imageio.ImageIO;

/**
 * @ Description: player class, everything to do with the player object in the game is run here
 * @ Author: Ryan Wang
 * @ Version: v2.0
 * September 2016
 */


public class Player extends Object {

	private final int xSpeed = 8;
	private final int ySpeed = 20;
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

	protected void updateImageNum(int max) {
		imageNum++;
		if (imageNum == max) {
			imageNum = 1;

			if (characterState.equalsIgnoreCase("Jump") || characterState.equalsIgnoreCase("Jump Move")) {
				setJumping(false);
			}

			if (characterState.equalsIgnoreCase("Jump") || characterState.equalsIgnoreCase("Shoot")) {
				setState("Still");
			}
			else if (characterState.equalsIgnoreCase("Jump Move")) {
				setState("Run");
			}
		}
	}

	protected void paintPlayer (Graphics g)  {
		String state = characterState.substring(0, 1);
		String direction = "";
		if (characterDirection.equalsIgnoreCase("Left")) {
			direction = "L";
		}
		if (characterState.equalsIgnoreCase("Shoot")) {
			state = "SH";
		}
		String imagePath = "/main/resources/images/" + direction + state + imageNum + ".png";

		try {
			g.drawImage(ImageIO.read(this.getClass().getResource(imagePath)), x, y, null);
		}
		catch (IOException e) {
			System.out.println("Error loading image");
		}
	}
	
}
