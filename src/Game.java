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

public class Game extends Applet implements Runnable {

	static Thread th;

	private Player megaMan;
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

	private Enemy enemyArray[];
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

	public void init()  {
		this.resize(1000, 400);

		turretArray = new turret[2];
		turretShotArray = new turretShot[12];
		turretShotArrayBox = new Rectangle[12];
		generateTurrets();

		walls = new background[32];
		wallBoxes = new Rectangle[32];
		createWalls();

		playerShotArray = new shot[5];
		playerShotArrayBox = new Rectangle[5];

		platformArray = new platforms[5];
		platformBoxes = new Rectangle[5];
		generatePlatforms();

		enemyArray = new Enemy[4];
		enemyHitBox = new Rectangle[4];
		enemyWallCollisionBox = new Rectangle[9];
		generateEnemyWalls();
		generateEnemy(0);

		megaMan = new Player(20, 270, 100, "Still", "Right");
		megaMan.setOnGround(true);
		playerBox = new Rectangle(20, 270, 30, 34);
		playerCollisionBox = new Rectangle(45, 329);

		healthBoost = new healthPack(level);
		healthPackBox = new Rectangle(healthBoost.getX(), healthBoost.getY(), 22, 15);

		try {
			background = ImageIO.read((this.getClass().getResource("/images/background.png")));
		}
		catch (IOException e) {
			System.out.println("Error loading background");
		}
	}

	public void start ()  {
		th = new Thread(this);
		th.start();
	}

	public void stop()  {
		th.stop();
	}

	public void run ()  {

		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		while (true) {
			health = megaMan.getHealth();
			if (health <= 0) {
				Control.loseFrame = new LoseScreen();
				Control.loseFrame.setVisible(true);
				GameFrame.audioClip.stop();
				Control.gameJFrame.dispose();
			}

			if (megaMan.getState().equalsIgnoreCase("Still")) {
				megaMan.setRunning(false);
				megaMan.setOnGround(true);
				megaMan.setUpDown(false, false);
			}

			if (megaMan.getState().equalsIgnoreCase("Run")) {
				if (!megaMan.isJumping()) {
					megaMan.setOnGround(true);
				} else {
					megaMan.setOnGround(false);
				}
				//Collision testing with the right wall
				if (megaMan.getDirection().equalsIgnoreCase("Right")) {
					moveRightLogic();
				} else {
					moveLeftLogic();
				}
			}

			if (megaMan.getState().equalsIgnoreCase("Jump")) {
				megaMan.setY(megaMan.getY() - 5);
				megaMan.setOnGround(false);
				//If the imageNum in the jump animation is less than 5, player moves up
				//If imageNum > 5 in the jump animation, moves down
				if (megaMan.getImageNum() < 5) {
					moveUpLogic();
				} else {
					moveDownLogic();
				}
			}

			if (megaMan.getState().equalsIgnoreCase("Jump Move")) {
				megaMan.setOnGround(false);
				if (megaMan.getImageNum() < 5) {
					moveUpLogic();
				} else {
					moveDownLogic();
				}
				//Makes character move while jumping, based on its current direction
				//Collision testing with frame sides same as before
				if (megaMan.getDirection().equalsIgnoreCase("Right")) {
					moveRightLogic();
				} else {
					moveLeftLogic();
				}
			}

			//Two timers controlling when a shot if created
			turret1ShotTimer++;
			turret2ShotTimer++;

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
			} else {
				turretShotNum = 0;
			}

			//Player with platform collision testing
			for (int i = 0; i < platformBoxes.length; i++) {
				if (platformBoxes[i] != null) {
					//If playerBox intersects any wall, sets the y value of player so that it's on top of platform
					if (playerBox.intersects(platformBoxes[i])) {
						megaMan.setOnGround(true);
						intersecting = true;
						if ((megaMan.isDown()) ||(!megaMan.isDown())) {
							megaMan.setY(platformBoxes[i].y - 48);
						}
					}
				}
			}

			//Turret shot with player and frame collision
			for (int i = 0; i < turretShotArray.length; i++) {
				if (turretShotArray[i] != null) {
					turretShotArray[i].moveShot();

					if (turretShotArrayBox[i].intersects(playerBox)) {
						megaMan.setHealth(megaMan.getHealth() - 5);
						turretShotArray[i] = null;
						turretShotArrayBox[i] = null;
					}
					else {
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
			}

			if ((healthPackBox != null) && (healthBoost != null)) {
				if (playerBox.intersects(healthPackBox)) {
					if ((megaMan.getHealth() + 10) <= 100) {
						megaMan.setHealth(megaMan.getHealth() + 15);
					} else {
						megaMan.setHealth(100);
					}
					healthBoost = null;
					healthPackBox = null;
				}
			}

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
					Control.completeFrame = new CompleteScreen();
					Control.completeFrame.setVisible(true);
					GameFrame.audioClip.stop();
					Control.gameJFrame.dispose();
					th.stop();
				}
			}

			this.repaint();

			//Makes thread sleep before rerunning loop
			try {
				Thread.sleep(50);
			}
			catch (InterruptedException ex) {
			}

			Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		}

	}

	public boolean keyDown(Event e, int key)  {

		if(key == Event.LEFT) {
			megaMan.setDirection("Left");
			setMoveLogic();
		}
		else if(key == Event.RIGHT) {
			megaMan.setDirection("Right");
			setMoveLogic();
		}
		else if(key == Event.UP)    {
			megaMan.setJumping(false);
			megaMan.setRunning(false);
			megaMan.setState("Still");
			megaMan.setImageNum(1);
		}
		else if ((key == 102) || (key == 70)) { //F key is pressed
			if (playerShotArray[playerShotNum] == null) {
				if (!megaMan.isJumping()) {
					megaMan.setState("Shoot");
					megaMan.setImageNum(1);
				}

				if (megaMan.getDirection().equalsIgnoreCase("Right")) {
					playerShotArray[playerShotNum] = new shot(megaMan.getX() + 30, megaMan.getY() + 15, playerShotRadius, megaMan.getDirection());
					playerShotArrayBox[playerShotNum] = new Rectangle(megaMan.getX() + 30, megaMan.getY() + 15, playerShotRadius, playerShotRadius);
				} else {
					playerShotArray[playerShotNum] = new shot(megaMan.getX(), megaMan.getY() + 15, playerShotRadius, megaMan.getDirection());
					playerShotArrayBox[playerShotNum] = new Rectangle(megaMan.getX(), megaMan.getY() + 15, playerShotRadius, playerShotRadius);
				}

				playerShotNum++;
				megaMan.setBulletCount(megaMan.getBulletCount() - 1);
				if (playerShotNum > (playerShotArray.length - 1)) {
					playerShotNum = 0;
				}
			}
		}
		else if (key == 32)  { //Space bar is pressed
			if (!megaMan.isJumping()) {
				megaMan.setJumping(true);
				if (megaMan.getState().equalsIgnoreCase("Run")) {
					megaMan.setState("Jump Move");
				} else {
					megaMan.setState("Jump");
				}
				megaMan.setImageNum(1);
			}
		}
		else if (key == Event.ESCAPE) {
			Control.pauseFrame = new PauseScreen(this);
			Control.pauseFrame.setVisible(true);
			stop();
		}
		return true;
	}

	public void update (Graphics g)  {
		if (dbImage == null)    {
			dbImage = createImage (this.getSize().width, this.getSize().height);
			dbg = dbImage.getGraphics();
		}

		dbg.setColor(getBackground());
		dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);

		dbg.setColor(getForeground());
		paint (dbg);

		g.drawImage (dbImage, 0, 0, this);

	}

	public void paint (Graphics g)  {
		super.paint(g);

		g.setColor(Color.black);
		g.drawString("Health:", 200, 449);
		g.drawString("Points: ", 200, 500);
		g.drawString("Bullet Count:", 200, 475);
		g.drawString(String.valueOf(megaMan.getBulletCount()), 285, 475);
		g.drawString(String.valueOf(points), 250, 500);

		this.resize(1000, 500);
		g.drawImage(background, 0, 0, null);

		//Health bar
		g.setColor(Color.green);
		g.fillRect(250, 440, (megaMan.getHealth() * 5), 10);

		for (int i = 0; i < turretShotArray.length; i++) {
			if (turretShotArray[i] != null) {
				turretShotArrayBox[i].setBounds(turretShotArray[i].getX(), turretShotArray[i].getY(), 3, 3);
				turretShotArray[i].paintShot(g);
			}
		}
		if ((healthBoost != null) && (healthPackBox != null)) {
			healthBoost.paintPack(g);
		}

		for (int i = 0; i < platformArray.length; i++) {
			if (platformArray[i] != null) {
				platformArray[i].paintPlatforms(g);
			}
		}

		for (int i = 0; i < turretArray.length; i++) {
			if (turretArray[i] != null) {
				turretArray[i].paintTurrets(g);
			}
		}

		for (int i = 0; i < enemyArray.length; i++) {
			if (enemyArray[i] != null) {
				enemyArray[i].paintEnemy(g);
			}
		}

		playerCollisionBox.setBounds(megaMan.getX(), megaMan.getY() + 34, 30, 2);
		megaMan.paintPlayer(g);

		if (megaMan.getState().equalsIgnoreCase("Still")) {
			playerBox.setBounds(megaMan.getX(), megaMan.getY(), 30, 34);
		}
		else if (megaMan.getState().equalsIgnoreCase("Run")) {
			playerBox.setBounds(megaMan.getX(), megaMan.getY(), 35, 35);
		}
		else if ((megaMan.getState().equalsIgnoreCase("Jump")) || (megaMan.getState().equalsIgnoreCase("Jump Move"))) {
			playerBox.setBounds(megaMan.getX(), megaMan.getY(), 30, 45);
		}

		for (int i = 0; i < playerShotArray.length; i++) {
			if (playerShotArray[i] != null) {
				playerShotArray[i].paintShot(g);
				playerShotArrayBox[i].setBounds(playerShotArray[i].getX(), playerShotArray[i].getY(), playerShotRadius, playerShotRadius);
			}
		}
	}

	private void enemyMovement() {
		for (int i = 0; i < enemyArray.length; i++) {
			if ((enemyArray[i] != null) && (enemyHitBox[i] != null)) {
				if (playerBox.intersects(enemyHitBox[i])) {
					megaMan.setHealth(megaMan.getHealth() - 1);
				}

				for (int k = 0; k < enemyWallCollisionBox.length; k++) {
					if (enemyHitBox[i].intersects(enemyWallCollisionBox[k])) {
						if (!enemyArray[i].getIntersectsWall()) {
							enemyArray[i].setIntersectsWall(true);
							if (enemyArray[i].getState().equalsIgnoreCase("Walk Right")) {
								enemyArray[i].setState("Walk Left");
							}
							else if (enemyArray[i].getState().equalsIgnoreCase("Walk Left")){
								enemyArray[i].setState("Walk Right");
							}
						}
					}

					else if (!(enemyHitBox[i].intersects(enemyWallCollisionBox[k]))) {
						if (enemyArray[i].getIntersectsWall()) {
							enemyArray[i].setIntersectsWall(false);
						}
					}
				}

				enemyArray[i].moveEnemy();
				enemyHitBox[i].setBounds(enemyArray[i].getX(), enemyArray[i].getY(), 50, 40);
			}
		}
	}

	private void shotMoveCollisionTest() {

		for (int i = 0; i < playerShotArray.length; i++) {
			if (playerShotArray[i] != null) {
				playerShotArray[i].moveShot();

				if (playerShotArray[i].getDirection().equalsIgnoreCase("Right")) {
					if (playerShotArray[i].getX() > (this.getWidth() - 10)) {
						megaMan.setBulletCount(megaMan.getBulletCount() + 1);
						playerShotArray[i] = null;
						playerShotArrayBox[i] = null;
					}
				} else {
					if (playerShotArray[i].getX() < 0) {
						megaMan.setBulletCount(megaMan.getBulletCount() + 1);
						playerShotArray[i] = null;
						playerShotArrayBox[i] = null;
					}
				}
			}

			//Shot and Wall Collision
			for (int k = 0; k < walls.length; k++) {
				if (playerShotArray[i] != null) {
					if (playerShotArrayBox[i].intersects(wallBoxes[k])) {
						megaMan.setBulletCount(megaMan.getBulletCount() + 1);
						playerShotArray[i] = null;
						playerShotArrayBox[i] = null;
					}
				}
			}

			//Shot and enemy collision
			for (int k = 0; k < enemyArray.length; k++) {
				if ((playerShotArray[i] != null) && (enemyArray[k] != null) && (enemyHitBox[k] != null))  {
					if (playerShotArrayBox[i].intersects(enemyHitBox[k])) {
						megaMan.setBulletCount(megaMan.getBulletCount() + 1);
						playerShotArray[i] = null;
						playerShotArrayBox[i] = null;

						if (enemyArray[k].getTimesHit() == 4) {
							points = points + 100;
							enemyArray[k].setImageNum(1);
							enemyArray[k].setDestroyed(true);
							enemyHitBox[k] = null;
							if (enemyArray[k].getImageNum() == 11) {
								enemyArray[k] = null;
							}
						} else {
							enemyArray[k].setTimesHit(enemyArray[k].getTimesHit() + 1);
						}
					}
				}
			}

			//Shot and Platform Collision
			for (int k = 0; k < platformArray.length; k++) {
				if ((playerShotArray[i] != null) && (platformBoxes[k] != null)) {
					if (playerShotArrayBox[i].intersects(platformBoxes[k])) {
						megaMan.setBulletCount(megaMan.getBulletCount() + 1);
						playerShotArray[i] = null;
						playerShotArrayBox[i] = null;
					}
				}
			}
		}
	}

	private void onGroundCollisionTest() {
		for (int i = 0; i < walls.length; i++) {
			if (playerCollisionBox.intersects(wallBoxes[i])) {
				if (!megaMan.onGround()) {
					if (!megaMan.isJumping()) {
						intersecting = true;
						megaMan.setOnGround(true);
						if (walls[i].getType().equalsIgnoreCase("Bottom")) {
							megaMan.setY(walls[i].getY() - 34);
							break;
						}
					}
				}
			} else {
				megaMan.setOnGround(false);
				intersecting = false;
			}
		}

		if (!intersecting) {
			if (!megaMan.onGround())	{
				if (!megaMan.isJumping()) {
					megaMan.setY(megaMan.getY() + 15);
				}
			}
		}
	}

	private void verticalWallCollisionTest() {
		for (int i = 0; i < walls.length; i++) {
			if (walls[i].getOrientation().equalsIgnoreCase("Vertical")) {
				if ((megaMan.getDirection().equalsIgnoreCase("Right")) && (megaMan.getState().equalsIgnoreCase("Run"))) {
					if (!megaMan.isJumping()) {
						if (playerBox.intersects(wallBoxes[i])) {
							megaMan.setState("Still");
							megaMan.setImageNum(1);
							megaMan.setX(walls[i].getX() - 35);
						}
					}
				}
				else if ((megaMan.getDirection().equalsIgnoreCase("Left")) && (megaMan.getState().equalsIgnoreCase("Run"))) {
					if (!megaMan.isJumping()) {
						if (playerBox.intersects(wallBoxes[i])) {
							megaMan.setState("Still");
							megaMan.setImageNum(1);
							megaMan.setX(walls[i].getX() + 12);
							megaMan.setY(walls[i].getY() + 5);
						}
					}
				}
			}
		}
	}

	private void createWalls() {
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

		for (int i = 0; i < wallBoxes.length; i++) {
			wallBoxes[i] = new Rectangle(walls[i].getX(), walls[i].getY(), walls[i].getWidth(), walls[i].getHeight());
		} 
	}

	private void generateEnemyWalls() {
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
	}

	private void generatePlatforms() {
		if (level == 1) {
			platformArray[0] = new platforms(0, 175, 100);
			platformArray[1] = new platforms(95, 270, 200);
			platformArray[2] = new platforms(345, 200, 175);
			platformArray[3] = new platforms(600, 225, 200);
			platformArray[4] = new platforms(850, 165, 150);
		}
		else if (level == 2) {
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

		for (int i = 0; i < platformBoxes.length; i++) {
			platformBoxes[i] = new Rectangle(platformArray[i].getX(), platformArray[i].getY(), platformArray[i].getWidth(), platformArray[i].getHeight());
		}
	}

	private void generateEnemy(int i) {
		if (i < enemyArray.length) {
			enemyArray[i] = new Enemy(level);
			enemyHitBox[i] = new Rectangle(enemyArray[i].getX(), enemyArray[i].getY());
			i++;
			generateEnemy(i);
		}
	}

	private void generateTurrets() {
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
	}

	private void resetNextLevel() {
		if (level == 2) {
			megaMan.setX(20);
			megaMan.setY(126);
		}
		if (level == 3) {
			megaMan.setX(20);
			megaMan.setY(300);
		}
		megaMan.setDirection("Right");
		for (int i = 0; i < turretShotArray.length; i++) {
			if (turretShotArray[i] != null) {
				turretShotArray[i] = null;
				turretShotArrayBox[i] = null;
			}
		}

		for (int i = 0; i < playerShotArray.length; i++) {
			megaMan.setBulletCount(5);
			if (playerShotArray[i] != null) {
				playerShotArray[i] = null;
				playerShotArrayBox[i] = null;
			}
		}

		healthBoost = new healthPack(level);
		healthPackBox = new Rectangle(healthBoost.getX(), healthBoost.getY(), 22, 15);

		generatePlatforms();
		generateTurrets();
		generateEnemy(0);
		generateEnemyWalls();
	}

	private void moveRightLogic() {
		if (megaMan.getX() < (this.getWidth() - 30)) {
			megaMan.moveRight();
		} else {
			megaMan.setX(this.getWidth() - 30);
		}
	}

	private void moveLeftLogic() {
		//Collision testing with the left wall
		if (megaMan.getX() > 0) {
			megaMan.moveLeft();
		} else {
			megaMan.setX(0);
		}
	}

	private void moveUpLogic() {
		if (megaMan.getY() >= 0) {
			megaMan.moveUp();
			megaMan.setUpDown(true, false);
		}
	}

	private void moveDownLogic() {
		megaMan.moveDown();
		megaMan.setUpDown(false, true);
	}

	private void setMoveLogic() {
		if (!megaMan.isRunning()) {
			megaMan.setRunning(true);
			if (!megaMan.isJumping()) {
				megaMan.setState("Run");
				megaMan.setImageNum(1);
			} else {
				megaMan.setState("Jump Move");
			}
		}
	}

}


