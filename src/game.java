import java.applet.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * @ Description: Main applet class for summative game, controls activity that happens in other classes related to the game
 * @ Author: Ryan Wang
 * @ Version: v1.0
 * June 13th, 2015
 */

public class game extends Applet implements Runnable {

	//Creation of all variables
	static Thread th;

	private player megaMan;
	private Rectangle playerBox;
	private Rectangle playerCollisionBox;

	static int points = 0;
	private int timer = 0;
	static int time = 0;
	static int health;
	private int level = 1;

	private turret turretArray[];
	private turretShot turretShotArray[];
	private Rectangle turretShotArrayBox[];
	private int turretShotNum = 0;
	private int turret1ShotTimer = 0;
	private int turret2ShotTimer = 0;
	private int turretShotRadius = 10;

	private shot playerShotArray[];
	private Rectangle playerShotArrayBox[];

	private platforms platformArray[];
	private Rectangle platformBoxes[];

	private enemy enemyArray[];
	private Rectangle enemyHitBox[];
	private Rectangle enemyWallCollisionBox[];

	private background walls[];
	private Rectangle wallBoxes[];

	private boolean intersecting;

	private int playerShotNum = 0;
	private int playerShotRadius = 10;

	private healthPack healthBoost;
	private Rectangle healthPackBox;

	private BufferedImage background;

	// double buffering
	private Image dbImage;
	private Graphics dbg;

	//Initializes the applet when loading
	public void init()  {

		//Sets window size
		this.resize(1000, 400);

		//Creates turret objects and their shot objects
		turretArray = new turret[2];
		turretShotArray = new turretShot[12];
		turretShotArrayBox = new Rectangle[12];
		//Calls method to generate those turrets
		generateTurrets();

		//Create wall objects and rectangles for the background walls
		walls = new background[32];
		wallBoxes = new Rectangle[32];
		//Calls method that generates walls
		createWalls();

		//Creates the player shot objects and its respective rectangles
		playerShotArray = new shot[5];
		playerShotArrayBox = new Rectangle[5];

		//Creates the platform objects and their rectangles
		platformArray = new platforms[5];
		platformBoxes = new Rectangle[5];
		//Calls method which initializes those platforms
		generatePlatforms();

		//Creates enemy objects and its respective rectangles
		enemyArray = new enemy[4];
		enemyHitBox = new Rectangle[4];
		//Creates the collision boxes used for enemy movement
		enemyWallCollisionBox = new Rectangle[9];
		//Initializes those walls and enemies
		generateEnemyWalls();
		generateEnemy(0);

		//Creates player and its collision boxes
		megaMan = new player(20, 270, 100, "Still", "Right");
		megaMan.setOnGround(true);
		playerBox = new Rectangle(20, 270, 30, 34);
		playerCollisionBox = new Rectangle(45, 329);

		healthBoost = new healthPack(level);
		healthPackBox = new Rectangle(healthBoost.getX(), healthBoost.getY(), 22, 15);

		//Draws background image
		try {
			background = ImageIO.read((this.getClass().getResource("/images/background.png")));
		}
		//Catches any error when loading image
		catch (IOException e) {
			System.out.println("error");
		}

	}//End of initialize applet method


	//Starts the applet
	public void start ()  {
		th = new Thread(this);
		th.start();
	}//End start method

	//Stops the applet
	public void stop()  {
		th.stop();
	}//End stop method


	//Run method, where the majority of the game is run in a loop
	public void run ()  {

		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		while (true) {

			//Sets the health to whatever value is returned from the player
			health = megaMan.getHealth();
			//If health falls below 0, game ends, game over screen shows
			if (health <= 0) {
				control.loseFrame = new loseScreen();
				control.loseFrame.setVisible(true);
				gameFrame.audioClip.stop();
				control.gameJFrame.dispose();
				th.stop();
			}

			//Various if statements to test for movement of player
			//Sets info if player is not moving
			if (megaMan.getState().equalsIgnoreCase("Still")) {
				megaMan.setRunning(false);
				megaMan.setOnGround(true);
				megaMan.setUpDown(false, false);
			}
			//Sets info if player is moving
			if (megaMan.getState().equalsIgnoreCase("Run")) {
				if (megaMan.isJumping() == false) {
					megaMan.setOnGround(true);
				}
				else {
					megaMan.setOnGround(false);
				}
				//Collision testing with the right wall
				if (megaMan.getDirection().equalsIgnoreCase("Right")) {
					if (megaMan.getX() < (this.getWidth() - 30)) {
						megaMan.moveRight();
					}
					else {
						megaMan.setX(this.getWidth() - 30);
					}
				}
				else {
					//Collision testing with the left wall
					if (megaMan.getX() > 0) {
						megaMan.moveLeft();
					}
					else {
						megaMan.setX(0);
					}
				}
			}
			//Sets info if player is jumping
			if (megaMan.getState().equalsIgnoreCase("Jump")) {
				megaMan.setY(megaMan.getY() - 5);
				megaMan.setOnGround(false);
				//If the imageNum in the jump animation is less than 5, player moves up
				//If imageNum > 5 in the jump animation, moves down
				if (megaMan.getImageNum() < 5) {
					if (megaMan.getY() >= 0) {
						megaMan.moveUp();
						megaMan.setUpDown(true, false);
					}
				}
				else {
					megaMan.moveDown();
					megaMan.setUpDown(false, true);
				}
			}
			//Sets info if player is jumping while moving
			if (megaMan.getState().equalsIgnoreCase("Jump Move")) {
				//Same jumping code as before
				megaMan.setOnGround(false);
				if (megaMan.getImageNum() < 5) {
					if (megaMan.getY() >= 0) {
						megaMan.moveUp();
						megaMan.setUpDown(true, false);
					}
				}
				else {
					megaMan.moveDown();
					megaMan.setUpDown(false, true);
				}
				//Makes character move while jumping, based on its current direction
				//Collision testing with frame sides same as before
				if (megaMan.getDirection().equalsIgnoreCase("Right")) {
					if (megaMan.getX() < (this.getWidth() - 30)) {
						megaMan.moveRight();
					}
					else {
						megaMan.setX(this.getWidth() - 30);
					}
				}
				else {
					if (megaMan.getX() > 0) {
						megaMan.moveLeft();
					}
					else {
						megaMan.setX(0);
					}
				}
			}

			//Two timers controlling when a shot if created
			turret1ShotTimer++;
			turret2ShotTimer++;

			//Only runs code if the shotNumber generated by the turrets is less than 10, or else will reset to 0, so it can loop
			if (turretShotNum < 10) {
				//If timer for 1st turret is >= 100, initializes a new shot and its rectangle
				if (turret1ShotTimer >= 100) {
					turret1ShotTimer = 0;
					if (turretArray[0] != null) {
						if (turretShotArray[turretShotNum] == null) {
							//Shots have different properties based on which level player is on
							if (level == 1) {
								turretShotArray[turretShotNum] = new turretShot(turretArray[0].getX(), turretArray[0].getY() + 5, turretShotRadius, "Left");
							}
							if (level == 2) {
								turretShotArray[turretShotNum] = new turretShot(turretArray[0].getX(), turretArray[0].getY() + 5, turretShotRadius, "Left");
							}
							if (level == 3) {
								turretShotArray[turretShotNum] = new turretShot(turretArray[0].getX(), turretArray[0].getY() + 5, turretShotRadius, "Right");
							}
							turretShotArrayBox[turretShotNum] = new Rectangle(turretShotArray[turretShotNum].getX(), turretShotArray[turretShotNum].getY(), turretShotRadius, turretShotRadius);
							turretShotNum++;
						}
					}
				}
				//If timer for 2nd turret is >= 60, initializes a new shot and its rectangle
				if (turret2ShotTimer >= 60) {
					turret2ShotTimer = 0;
					if (turretArray[1] != null) {
						if (turretShotArray[turretShotNum] == null) {
							turretShotArray[turretShotNum] = new turretShot(turretArray[1].getX() + 15, turretArray[1].getY() - 2, turretShotRadius, "Up");
							turretShotArrayBox[turretShotNum] = new Rectangle(turretShotArray[turretShotNum].getX(), turretShotArray[turretShotNum].getY(), turretShotRadius, turretShotRadius);
							turretShotNum++;
						}
					}
				}
			}
			else {
				turretShotNum = 0;
			}

			//Player with platform collision testing
			//For loop that runs through all the platforms
			for (int i = 0; i < platformBoxes.length; i++) {
				if (platformBoxes[i] != null) {
					//If playerBox intersects any wall, sets the y value of player so that it's on top of platform
					if (playerBox.intersects(platformBoxes[i])) {
						megaMan.setOnGround(true);
						intersecting = true;
						if ((megaMan.isDown() == true) ||((megaMan.isDown() == false) && (megaMan.isDown() == false))) {
							megaMan.setY(platformBoxes[i].y - 48);
						}
					}
				}
			}//End for loop

			//Turret shot with player and frame collision
			//For loop that runs through each non-null shot object
			for (int i = 0; i < turretShotArray.length; i++) {
				if (turretShotArray[i] != null) {
					//Moves the shot
					turretShotArray[i].moveShot();
					//If shot hits player, sets to null, player loses health
					if (turretShotArrayBox[i].intersects(playerBox)) {
						megaMan.setHealth(megaMan.getHealth() - 5);
						turretShotArray[i] = null;
						turretShotArrayBox[i] = null;
					}
					else {
						//Based on direction, if it hits either the left, right, or top wall, shot and its rectangle become null
						if (turretShotArray[i].getDirection().equalsIgnoreCase("Left")) {
							if (turretShotArray[i].getX() < 5) {
								turretShotArray[i] = null;
								turretShotArrayBox[i] = null;
							}
						}
						else if (turretShotArray[i].getDirection().equalsIgnoreCase("Right")) {
							if (turretShotArray[i].getX() > 985) {
								turretShotArray[i] = null;
								turretShotArrayBox[i] = null;
							}
						}
						else {
							if (turretShotArray[i].getY() < 20) {
								turretShotArray[i] = null;
								turretShotArrayBox[i] = null;
							}
						}
					}
				}
			}//End for loop

			//Testing for if player collects health pack
			if ((healthPackBox != null) && (healthBoost != null)) {
				if (playerBox.intersects(healthPackBox)) {
					//Max health is 100, so if current health + 10 > 100, health is set to 100
					if ((megaMan.getHealth() + 10) <= 100) {
						megaMan.setHealth(megaMan.getHealth() + 15);
					}
					else {
						megaMan.setHealth(100);
					}
					//Objects become null
					healthBoost = null;
					healthPackBox = null;
				}
			}

			//Calls methods that are responsible for everything in the program running
			enemyMovement();
			shotMoveCollisionTest();
			onGroundCollisionTest();
			verticalWallCollisionTest();

			//Increase of timer value
			timer++;
			//If timer == 20, that means 1 second has passed since refresh rate is 50ms
			if (timer == 20) {
				//Increases real time value and resets timer value
				time++;
				timer = 0;
			}
			//If player reaches a certain spot, next level starts and method which sets that up is called
			if (level == 1) {
				if ((megaMan.getX() >= 960) && (megaMan.getY() < 200)) {
					level++;
					resetNextLevel();
				}
			}
			if (level == 2) {
				if ((megaMan.getX() >= 960) && (megaMan.getY() > 330)) {
					level++;
					resetNextLevel();
				}
			}
			if (level == 3) {
				if ((megaMan.getX() >= 960) && (megaMan.getY() < 200)) {
					control.completeFrame = new completeScreen();
					control.completeFrame.setVisible(true);
					gameFrame.audioClip.stop();
					control.gameJFrame.dispose();
					th.stop();
				}
			}

			//Calls repaint 
			this.repaint();

			//Makes thread sleep before rerunning loop
			try {
				Thread.sleep(50);
			}
			catch (InterruptedException ex) {
				// do nothing
			}

			Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		}

	}

	//Method for testing key presses
	public boolean keyDown(Event e, int key)  {

		//Depending on certain key presses, certain actions are run
		//Left makes character move left, sets imageNum to 1
		if(key == Event.LEFT) {
			megaMan.setDirection("Left");
			if (megaMan.isRunning() == false) {
				megaMan.setRunning(true);
				if (megaMan.isJumping() == false) {
					megaMan.setState("Run");
					megaMan.setImageNum(1);
				}
				else {
					megaMan.setState("Jump Move");
				}
			}
		}//Right makes character move right, sets imageNum to 1 
		else if(key == Event.RIGHT) {
			megaMan.setDirection("Right");
			if (megaMan.isRunning() == false) {
				megaMan.setRunning(true);
				if (megaMan.isJumping() == false) {
					megaMan.setState("Run");
					megaMan.setImageNum(1);
				}
				else {
					megaMan.setState("Jump Move");
				}
			}
		}//Right makes character jump, sets imageNum to 1 
		else if(key == Event.UP)    {
			megaMan.setJumping(false);
			megaMan.setRunning(false);
			megaMan.setState("Still");
			megaMan.setImageNum(1);
		}    
		//F key, used for shooting
		else if ((key == 102) || (key == 70)) {
			//Generates shot if it's null at that position in the array
			if (playerShotArray[playerShotNum] == null) {
				//Runs through shooting animation
				if (megaMan.isJumping() == false) {
					megaMan.setState("Shoot");
					megaMan.setImageNum(1);
				}
				//Creates shot based on direction and location of the player
				if (megaMan.getDirection().equalsIgnoreCase("Right")) {
					playerShotArray[playerShotNum] = new shot(megaMan.getX() + 30, megaMan.getY() + 15, playerShotRadius, megaMan.getDirection());
					playerShotArrayBox[playerShotNum] = new Rectangle(megaMan.getX() + 30, megaMan.getY() + 15, playerShotRadius, playerShotRadius);
				}
				else {
					playerShotArray[playerShotNum] = new shot(megaMan.getX(), megaMan.getY() + 15, playerShotRadius, megaMan.getDirection());
					playerShotArrayBox[playerShotNum] = new Rectangle(megaMan.getX(), megaMan.getY() + 15, playerShotRadius, playerShotRadius);
				}
				//Increases playerShotNum, which will reset once it reaches the end of the array length
				playerShotNum++;
				//Changes bullet count
				megaMan.setBulletCount(megaMan.getBulletCount() - 1);
				if (playerShotNum > (playerShotArray.length - 1)) {
					playerShotNum = 0;
				}
			}
		}    
		//Space bar, used for jumping
		else if(key == 32)  {
			//Will only jump if not currently jumping, this stops players from double jumping
			if (megaMan.isJumping() == false) {
				megaMan.setJumping(true);
				//Sets state based on whether character is running or not
				if (megaMan.getState().equalsIgnoreCase("Run")) {
					megaMan.setState("Jump Move");
				}
				else {
					megaMan.setState("Jump");
				}
				megaMan.setImageNum(1);
			}
		}
		//If escape is pressed, pause screen shows, thread is paused
		else if (key == Event.ESCAPE) {
			control.pauseFrame = new pauseScreen(this);
			control.pauseFrame.setVisible(true);
			stop();
		}
		return true;
	}

	//Update method
	public void update (Graphics g)  {
		//Code supplied for Mr. B, refreshes the screen
		if (dbImage == null)    {
			dbImage = createImage (this.getSize().width, this.getSize().height);
			dbg = dbImage.getGraphics ();
		}

		dbg.setColor (getBackground ());
		dbg.fillRect (0, 0, this.getSize().width, this.getSize().height);

		dbg.setColor (getForeground());
		paint (dbg);

		g.drawImage (dbImage, 0, 0, this);

	}//End update method

	//Paint method
	public void paint (Graphics g)  {

		super.paint(g);

		//Paints the info labels, as well as bullet count and points
		g.setColor(Color.black);
		g.drawString("Health:", 200, 449);
		g.drawString("Points: ", 200, 500);
		g.drawString("Bullet Count:", 200, 475);
		g.drawString(String.valueOf(megaMan.getBulletCount()), 285, 475);
		g.drawString(String.valueOf(points), 250, 500);

		//Resizes frame, and draws the background image
		this.resize(1000, 500);
		g.drawImage(background, 0, 0, null);

		//Health bar
		g.setColor(Color.green);
		g.fillRect(250, 440, (megaMan.getHealth() * 5), 10);

		//Draws all the turret shots in the array
		for (int i = 0; i < turretShotArray.length; i++) {
			if (turretShotArray[i] != null) {
				turretShotArrayBox[i].setBounds(turretShotArray[i].getX(), turretShotArray[i].getY(), 3, 3);
				turretShotArray[i].paintShot(g);
			}
		}
		if ((healthBoost != null) && (healthPackBox != null)) {
			healthBoost.paintPack(g);
		}

		//Paint platforms, as long as they're not null
		for (int i = 0; i < platformArray.length; i++) {
			if (platformArray[i] != null) {
				platformArray[i].paintPlatforms(g);
			}
		}

		//Paint turrets, as long as they're not null
		for (int i = 0; i < turretArray.length; i++) {
			if (turretArray[i] != null) {
				turretArray[i].paintTurrets(g);
			}
		}

		//Paint enemies, as long as they're not null
		for (int i = 0; i < enemyArray.length; i++) {
			if (enemyArray[i] != null) {
				enemyArray[i].paintEnemy(g);
			}
		}

		//Sets bounds for one of the player collision boxes
		playerCollisionBox.setBounds(megaMan.getX(), megaMan.getY() + 34, 30, 2);
		//Paints player
		megaMan.paintPlayer(g);
		//Player box is a different size based on its state because image dimensions vary slightly
		if (megaMan.getState().equalsIgnoreCase("Still")) {
			playerBox.setBounds(megaMan.getX(), megaMan.getY(), 30, 34);
		}
		if (megaMan.getState().equalsIgnoreCase("Run")) {
			playerBox.setBounds(megaMan.getX(), megaMan.getY(), 35, 35);
		}
		if ((megaMan.getState().equalsIgnoreCase("Jump")) || (megaMan.getState().equalsIgnoreCase("Jump Move"))) {
			playerBox.setBounds(megaMan.getX(), megaMan.getY(), 30, 45);
		}

		//Paints all the shots that have been created by the player, also changes the location of the rectangles to follow the shots as they move
		for (int i = 0; i < playerShotArray.length; i++) {
			if (playerShotArray[i] != null) {
				playerShotArray[i].paintShot(g);
				playerShotArrayBox[i].setBounds(playerShotArray[i].getX(), playerShotArray[i].getY(), playerShotRadius, playerShotRadius);
			}
		}
	}//End of paint

	//Method for enemy movement
	public void enemyMovement() {
		//Loop that runs through all the enemies in the array
		for (int i = 0; i < enemyArray.length; i++) {
			//Only runs code if enemy is not null
			if ((enemyArray[i] != null) && (enemyHitBox[i] != null)) {
				//If player intersects enemy, player loses health
				if (playerBox.intersects(enemyHitBox[i])) {
					megaMan.setHealth(megaMan.getHealth() - 1);
				}
				//Loop through all enemy collision walls
				for (int k = 0; k < enemyWallCollisionBox.length; k++) {
					//If enemy intersects with those walls, makes the enemy bounce and walk in the opposite direction
					if (enemyHitBox[i].intersects(enemyWallCollisionBox[k])) {
						if (enemyArray[i].getIntersectsWall() == false) {
							enemyArray[i].setIntersectsWall(true);
							if (enemyArray[i].getState().equalsIgnoreCase("Walk Right")) {
								enemyArray[i].setState("Walk Left");
							}
							else if (enemyArray[i].getState().equalsIgnoreCase("Walk Left")){
								enemyArray[i].setState("Walk Right");
							}
						}
					}
					//If not intersecting anymore, sets intersecting to false
					else if (!(enemyHitBox[i].intersects(enemyWallCollisionBox[k]))) {
						if (enemyArray[i].getIntersectsWall() == true) {
							enemyArray[i].setIntersectsWall(false);
						}
					}
				}
				//Moves enemies and moves the boxes along with the objects
				enemyArray[i].moveEnemy();
				enemyHitBox[i].setBounds(enemyArray[i].getX(), enemyArray[i].getY(), 50, 40);
			}
		}
	}//End enemyMovement method

	//Method for moving shots and its collision with various objects
	public void shotMoveCollisionTest() {

		for (int i = 0; i < playerShotArray.length; i++) {
			//Movement of shots and collision with frame sides, as long as they're not null
			if (playerShotArray[i] != null) {
				//Moves shot
				playerShotArray[i].moveShot();
				//Collision test for the right frame side, if collides, increases bullet count and sets the shot to null
				if (playerShotArray[i].getDirection().equalsIgnoreCase("Right")) {
					if (playerShotArray[i].getX() > (this.getWidth() - 10)) {
						megaMan.setBulletCount(megaMan.getBulletCount() + 1);
						playerShotArray[i] = null;
						playerShotArrayBox[i] = null;
					}
				}//Collision test for the left frame side, if collides, increases bullet count and sets the shot to null
				else {
					if (playerShotArray[i].getX() < 0) {
						megaMan.setBulletCount(megaMan.getBulletCount() + 1);
						playerShotArray[i] = null;
						playerShotArrayBox[i] = null;
					}
				}
			}
			//Shot and Wall Collision, if intersects, increases bullet count and sets the shot to null
			for (int k = 0; k < walls.length; k++) {
				if (playerShotArray[i] != null) {
					if (playerShotArrayBox[i].intersects(wallBoxes[k])) {
						megaMan.setBulletCount(megaMan.getBulletCount() + 1);
						playerShotArray[i] = null;
						playerShotArrayBox[i] = null;
					}
				}
			}
			//Shot and enemy collision, if intersects, increases bullet count and sets the shot to null only if it is hit 4 times
			for (int k = 0; k < enemyArray.length; k++) {
				if ((playerShotArray[i] != null) && (enemyArray[k] != null) && (enemyHitBox[k] != null))  {
					if (playerShotArrayBox[i].intersects(enemyHitBox[k])) {
						megaMan.setBulletCount(megaMan.getBulletCount() + 1);
						playerShotArray[i] = null;
						playerShotArrayBox[i] = null;
						//If it is hit 4 times, adds points, sets the enemy to destroyed, then sets the object to null after animation is complete
						if (enemyArray[k].getTimesHit() == 4) {
							points = points + 100;
							enemyArray[k].setImageNum(1);
							enemyArray[k].setDestroyed(true);
							enemyHitBox[k] = null;
							if (enemyArray[k].getImageNum() == 11) {
								enemyArray[k] = null;
							}
						}
						//If it hasn't been hit 4 times, increases timesHit
						else {
							enemyArray[k].setTimesHit(enemyArray[k].getTimesHit() + 1);
						}
					}
				}
			}//End of for loop

			//Shot and Platform Collision
			for (int k = 0; k < platformArray.length; k++) {
				//As long as platform and shot is not null
				if ((playerShotArray[i] != null) && (platformBoxes[k] != null)) {
					//If it hits platform, increases bullet count, sets object to null
					if (playerShotArrayBox[i].intersects(platformBoxes[k])) {
						megaMan.setBulletCount(megaMan.getBulletCount() + 1);
						playerShotArray[i] = null;
						playerShotArrayBox[i] = null;
					}
				}
			}
		}

	}//End of shotMoveCollisionTest method

	//Method for testing if player if on ground
	public void onGroundCollisionTest() {
		//On ground collision testing
		//Runs through all the background walls
		for (int i = 0; i < walls.length; i++) {
			if (playerCollisionBox.intersects(wallBoxes[i])) {
				if (megaMan.onGround() == false) {
					if (megaMan.isJumping() == false) {
						intersecting = true;
						megaMan.setOnGround(true);
						//Sets the player to be on the ground when it intersects
						if (walls[i].getType().equalsIgnoreCase("Bottom")) {
							megaMan.setY(walls[i].getY() - 34);
							break;
						}
					}
				}
			}
			//If it isn't intersecting, sets on ground to false, which makes character fall
			else {
				megaMan.setOnGround(false);
				intersecting = false;
			}
		}
		//If not intersecting a bottom wall, character falls
		if (intersecting == false) {
			if (megaMan.onGround() == false)	{			
				if (megaMan.isJumping() == false) {	
					megaMan.setY(megaMan.getY() + 15);
				}
			}
		}
	}//End onGroundCollisionTest method

	//Method for testing when character runs into a vertical wall
	public void verticalWallCollisionTest() {
		//Vertical wall collision testing
		//Loops through all the background walls
		for (int i = 0; i < walls.length; i++) {
			//Only runs if wall type is vertical
			if (walls[i].getOrientation().equalsIgnoreCase("Vertical")) {
				//Stops character when it hits the wall running right
				if ((megaMan.getDirection().equalsIgnoreCase("Right")) && (megaMan.getState().equalsIgnoreCase("Run"))) {
					if (megaMan.isJumping() == false) {
						if (playerBox.intersects(wallBoxes[i])) {
							megaMan.setState("Still");
							megaMan.setImageNum(1);
							megaMan.setX(walls[i].getX() - 35);
						}
					}
				}
				//Stops character when it hits the wall running left
				else if ((megaMan.getDirection().equalsIgnoreCase("Left")) && (megaMan.getState().equalsIgnoreCase("Run"))) {
					if (megaMan.isJumping() == false) {
						if (playerBox.intersects(wallBoxes[i])) {
							megaMan.setState("Still");
							megaMan.setImageNum(1);
							megaMan.setX(walls[i].getX() + 12);
							megaMan.setY(walls[i].getY() + 5);
						}
					}
				}
			}
		}//End for loop
	}//End verticalWallCollisionTest method

	//Method that instantiates all the background walls
	public void createWalls() {
		//Creates all the walls based on its constructors
		walls[0] = new background(0, 50, 92, 4, "Horizontal", "Top");
		walls[1] = new background(92, 50, 4, 40, "Vertical", "Top");
		walls[2] = new background(92, 90, 50, 4, "Horizontal", "Top");
		walls[3] = new background(142, 50, 4, 44, "Vertical", "Top");

		walls[4] = new background(142, 50, 100, 4, "Horizontal", "Top");
		walls[5] = new background(241, 50, 4, 40, "Vertical", "Top");
		walls[6] = new background(241, 90, 50, 4, "Horizontal", "Top");
		walls[7] = new background(291, 50, 4, 44, "Vertical", "Top");

		walls[8] = new background(295, 50, 187, 4, "Horizontal", "Top");
		walls[9] = new background(482, 50, 4, 40, "Vertical", "Top");
		walls[10] = new background(482, 90, 50, 4, "Horizontal", "Top");
		walls[11] = new background(532, 50, 4, 44, "Vertical", "Top");

		walls[12] = new background(536, 50, 96, 4, "Horizontal", "Top");
		walls[13] = new background(632, 50, 4, 40, "Vertical", "Top");
		walls[14] = new background(632, 90, 50, 4, "Horizontal", "Top");
		walls[15] = new background(682, 50, 4, 44, "Vertical", "Top");

		walls[16] = new background(682, 50, 187, 4, "Horizontal", "Top");
		walls[17] = new background(869, 50, 4, 40, "Vertical", "Top");
		walls[18] = new background(869, 90, 50, 4, "Horizontal", "Top");
		walls[19] = new background(919, 50, 4, 44, "Vertical", "Top");
		walls[20] = new background(919, 50, 81, 4, "Horizontal", "Top");

		walls[21] = new background(0, 334, 87, 100, "Horizontal", "Bottom");
		walls[22] = new background(92, 338, 4, 43, "Vertical", "Bottom");
		walls[23] = new background(92, 377, 200, 80, "Horizontal", "Bottom");
		walls[24] = new background(288, 338, 4, 47, "Vertical", "Bottom");

		walls[25] = new background(288, 334, 188, 100, "Horizontal", "Bottom");
		walls[26] = new background(482, 338, 4, 43, "Vertical", "Bottom");
		walls[27] = new background(482, 377, 200, 80, "Horizontal", "Bottom");
		walls[28] = new background(680, 338, 4, 47, "Vertical", "Bottom");

		walls[29] = new background(680, 334, 188, 100, "Horizontal", "Bottom");
		walls[30] = new background(874, 338, 4, 43, "Vertical", "Bottom");
		walls[31] = new background(874, 377, 200, 40, "Horizontal", "Bottom");

		//Loop to create a rectangle for each object, used for collision testing
		for (int i = 0; i < wallBoxes.length; i++) {
			wallBoxes[i] = new Rectangle(walls[i].getX(), walls[i].getY(), walls[i].getWidth(), walls[i].getHeight());
		} 
	}//End createWalls method

	//Method that instantiates all the enemy walls
	public void generateEnemyWalls() {
		//Creates the rectangles based on level
		//Once level is 2, uses setBounds for efficiency
		if (level == 1) {
			enemyWallCollisionBox[0] = new Rectangle(92, 300, 1, 100);
			enemyWallCollisionBox[1] = new Rectangle(285, 300, 3, 100);
			enemyWallCollisionBox[2] = new Rectangle(482, 300, 3, 100);
			enemyWallCollisionBox[3] = new Rectangle(680, 300, 3, 100);
			enemyWallCollisionBox[4] = new Rectangle(345, 150, 3, 100);
			enemyWallCollisionBox[5] = new Rectangle(520, 150, 3, 100);
			enemyWallCollisionBox[6] = new Rectangle(600, 175, 3, 100);
			enemyWallCollisionBox[7] = new Rectangle(800, 175, 3, 100);
			enemyWallCollisionBox[8] = new Rectangle(870, 300, 3, 100);
		}
		else if (level == 2) {
			enemyWallCollisionBox[4].setBounds(870, 300, 3, 100);
			enemyWallCollisionBox[5].setBounds(510, 200, 3, 100);
			enemyWallCollisionBox[6].setBounds(685, 200, 3, 100);
			enemyWallCollisionBox[7].setBounds(125, 200, 3, 100);
			enemyWallCollisionBox[8].setBounds(300, 200, 3, 100);
		}
		else if (level == 3) {
			enemyWallCollisionBox[4].setBounds(345, 150, 3, 100);
			enemyWallCollisionBox[5].setBounds(520, 150, 3, 100);
			enemyWallCollisionBox[6].setBounds(600, 175, 3, 100);
			enemyWallCollisionBox[7].setBounds(800, 175, 3, 100);
			enemyWallCollisionBox[8].setBounds(870, 300, 3, 100);
		}
	}//End generateEnemyWalls method

	//Method that creates instantiates platform objects and their respective rectangles
	public void generatePlatforms() {
		//Instantiates platforms based on level
		if (level == 1) {
			platformArray[0] = new platforms(0, 175, 100);
			platformArray[1] = new platforms(95, 270, 200);
			platformArray[2] = new platforms(345, 200, 175);
			platformArray[3] = new platforms(600, 225, 200);
			platformArray[4] = new platforms(850, 165, 150);
		}
		else if (level == 2) {
			//Resets the size of array due to different level design
			platformBoxes = new Rectangle[4];
			platformArray = new platforms[4];
			platformArray[0] = new platforms(0, 150, 100);
			platformArray[1] = new platforms(510, 270, 175);
			platformArray[2] = new platforms(125, 270, 175);
			platformArray[3] = new platforms(850, 150, 150);
		}
		else if (level == 3) {
			platformBoxes = new Rectangle[5];
			platformArray = new platforms[5];
			platformArray[0] = new platforms(0, 175, 100);
			platformArray[1] = new platforms(95, 270, 200);
			platformArray[2] = new platforms(345, 200, 175);
			platformArray[3] = new platforms(600, 225, 200);
			platformArray[4] = new platforms(850, 185, 150);
		}
		//Instantiates all the rectangles in the platform array
		for (int i = 0; i < platformBoxes.length; i++) {
			platformBoxes[i] = new Rectangle(platformArray[i].getX(), platformArray[i].getY(), platformArray[i].getWidth(), platformArray[i].getHeight());
		}

	}//End generatePlatforms method

	//Method that generates enemies using recursion
	public void generateEnemy(int i) {
		if (i < enemyArray.length) {
			//Instantiates a new enemy and its respective rectangle
			enemyArray[i] = new enemy(level);
			enemyHitBox[i] = new Rectangle(enemyArray[i].getX(), enemyArray[i].getY());
			//Recursion because it calls the method again, will stop once i value is greater than length of array
			i++;
			generateEnemy(i);
		}
	}//End generateEnemy method

	//Method that instantiates all the turrets
	public void generateTurrets() {
		//Creates different turret objects based on level design
		if (level == 1) {
			turretArray[0] = new turret(772, 210, 1, "Left");
			turretArray[1] = new turret(905, 145, 2, "Up");
		}
		else if (level == 2) {
			turretArray[0] = new turret(950, 135, 1, "Left");
			turretArray[1] = null;
		}
		else if (level == 3) {
			turretArray[0] = new turret(75, 160, 1, "Right");
			turretArray[1] = new turret(295, 320, 2, "Up");
		}
	}//End generateTurrets level

	//Method for resetting the level once the end is reached
	public void resetNextLevel() {
		//Changes x and y position of player
		if (level == 2) {
			megaMan.setX(20);
			megaMan.setY(126);
		}
		if (level == 3) {
			megaMan.setX(20);
			megaMan.setY(300);
		}
		megaMan.setDirection("Right");
		//Makes all the turret shots null
		for (int i = 0; i < turretShotArray.length; i++) {
			if (turretShotArray[i] != null) {
				turretShotArray[i] = null;
				turretShotArrayBox[i] = null;
			}
		}
		//Makes all player shots null;
		for (int i = 0; i < playerShotArray.length; i++) {
			megaMan.setBulletCount(5);
			if (playerShotArray[i] != null) {
				playerShotArray[i] = null;
				playerShotArrayBox[i] = null;
			}
		}
		//Creates a new healthpack
		healthBoost = new healthPack(level);
		healthPackBox = new Rectangle(healthBoost.getX(), healthBoost.getY(), 22, 15);

		//Recreates platforms, turrets, enemies and their collision boxes based on level
		generatePlatforms();
		generateTurrets();
		generateEnemy(0);
		generateEnemyWalls();
	}//End resetNextLevel method

}//End of game class


