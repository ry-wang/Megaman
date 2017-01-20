package main.java;

import java.applet.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 * @ Description: Main applet class for summative game, controls activity that happens in other classes related to the game
 * @ Author: Ryan Wang
 * @ Version: v2.0
 * September 2016
 */


public class Game extends Applet implements Runnable {

    static Thread th;

    private Player megaMan;
    private Rectangle playerBox;
    private Rectangle playerCollisionBox;

    private final Map maxPlayerImageNum = new HashMap();

    static int points = 0;
    private int timer = 0;
    static int time = 0;
    static int health;
    private int level = 1;

    private Turret turretArray[];
    private TurretShot turretShotArray[];
    private int turretShotNum = 0;
    private int turret1ShotTimer = 0;
    private int turret2ShotTimer = 0;

    private Shot playerShotArray[];

    private Platform platformArray[];

    private Enemy enemyArray[];
    private Rectangle enemyWallCollisionBox[];

    private BackgroundWall backgroundWalls[];

    private boolean intersecting;
    private int playerShotNum = 0;

    private HealthPack healthBoost;

    private BufferedImage Background;

    // double buffering
    private Image dbImage;
    private Graphics dbg;

    public void init()  {
        this.resize(1000, 500);

        initPlayerImageMap();

        turretArray = new Turret[2];
        turretShotArray = new TurretShot[12];
        generateTurrets();

        backgroundWalls = new BackgroundWall[32];
        createBackgroundWalls();

        playerShotArray = new Shot[5];

        platformArray = new Platform[5];
        generatePlatforms();

        enemyArray = new Enemy[4];
        enemyWallCollisionBox = new Rectangle[9];
        generateEnemyWalls();
        generateEnemies(0);

        megaMan = new Player(20, 270, 100, "Still", "Right");
        playerBox = new Rectangle(20, 270, 30, 34);
        playerCollisionBox = new Rectangle(45, 329);

        healthBoost = new HealthPack(level);

        try {
            Background = ImageIO.read((this.getClass().getResource("/main/resources/images/background.png")));
        }
        catch (IOException e) {
            System.out.println("Error loading Background");
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

            else if (megaMan.getState().equalsIgnoreCase("Run")) {
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

            else if (megaMan.getState().equalsIgnoreCase("Jump")) {
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

            else if (megaMan.getState().equalsIgnoreCase("Jump Move")) {
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
                    if (turretShotArray[turretShotNum] == null) {
                        String direction;
                        if (level == 1 || level == 2) {
                            direction = "Left";
                        }
                        else {
                            direction = "Right";
                        }
                        turretShotArray[turretShotNum] = new TurretShot(turretArray[0].getX(), turretArray[0].getY() + 5, 10, direction);
                        turretShotNum++;
                    }
                }

                //If timer for 2nd turret is >= 60, initializes a new shot and its rectangle
                if (turret2ShotTimer >= 60) {
                    turret2ShotTimer = 0;
                    if (turretArray[1] != null) {
                        if (turretShotArray[turretShotNum] == null) {
                            turretShotArray[turretShotNum] = new TurretShot(turretArray[1].getX() + 15, turretArray[1].getY() - 2, 10, "Up");
                            turretShotNum++;
                        }
                    }
                }
            } else {
                turretShotNum = 0;
            }

            //Player with platform collision testing
            for (Platform i: platformArray) {
                if (i != null) {
                    //If playerBox intersects any wall, sets the y value of player so that it's on top of platform
                    if (playerBox.intersects(i.getPlatformBox())) {
                        megaMan.setOnGround(true);
                        intersecting = true;
                        if ((megaMan.isDown()) ||(!megaMan.isDown())) {
                            megaMan.setY(i.y - 48);
                        }
                    }
                }
            }

            //Turret shot with player and frame collision
            for (int i = 0; i < turretShotArray.length; i++) {
                if (turretShotArray[i] != null) {
                    turretShotArray[i].moveShot();

                    if (turretShotArray[i].getBox().intersects(playerBox)) {
                        megaMan.setHealth(megaMan.getHealth() - 5);
                        destroyTurretShot(i);
                    }
                    else {
                        if (turretShotArray[i].getDirection().equalsIgnoreCase("Left")) {
                            if (turretShotArray[i].getX() < 5) {
                                destroyTurretShot(i);
                            }
                        }
                        else if (turretShotArray[i].getDirection().equalsIgnoreCase("Right")) {
                            if (turretShotArray[i].getX() > 985) {
                                destroyTurretShot(i);
                            }
                        }
                        else {
                            if (turretShotArray[i].getY() < 20) {
                                destroyTurretShot(i);
                            }
                        }
                    }
                }
            }

            if (healthBoost != null) {
                if (playerBox.intersects(healthBoost.getBox())) {
                    if ((megaMan.getHealth() + 10) <= 100) {
                        megaMan.setHealth(megaMan.getHealth() + 15);
                    } else {
                        megaMan.setHealth(100);
                    }
                    healthBoost = null;
                }
            }

            moveEnemies();
            playerShotMoveAndCollisionCheck();
            onGroundCollisionCheck();
            verticalWallCollisionCheck();
            updateGameTime();

            if (level == 1) {
                if (megaMan.getX() >= 960 && megaMan.getY() < 200) {
                    level++;
                    resetNextLevel();
                }
            }
            else if (level == 2) {
                if (megaMan.getX() >= 960 && megaMan.getY() > 330) {
                    level++;
                    resetNextLevel();
                }
            }
            else if (level == 3) {
                if (megaMan.getX() >= 960 && megaMan.getY() < 200) {
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
                System.out.println("Applet encountered error.");
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

                int offset = megaMan.getDirection().equalsIgnoreCase("Right") ? 30 : 0;
                playerShotArray[playerShotNum] = new Shot(megaMan.getX() + offset, megaMan.getY() + 15, 10, megaMan.getDirection());
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
        g.drawImage(Background, 0, 0, null);

        //Health bar
        g.setColor(Color.green);
        g.fillRect(250, 440, (megaMan.getHealth() * 5), 10);

        if (healthBoost != null) {
            healthBoost.paintPack(g);
        }

        for (TurretShot turretShot: turretShotArray) {
            if (turretShot != null) {
                turretShot.paintShot(g);
            }
        }

        for (Shot playerShot: playerShotArray) {
            if (playerShot != null) {
                playerShot.paintShot(g);
            }
        }

        for (Platform platform: platformArray) {
            if (platform != null) {
                platform.paintPlatform(g);
            }
        }

        for (Turret turret: turretArray) {
            if (turret != null) {
                turret.paintTurrets(g);
            }
        }

        for (int i = 0; i < enemyArray.length; i++) {
            if (enemyArray[i] != null) {
                enemyArray[i].paintEnemy(g);
                enemyArray[i].updateEnemyState();
                if (enemyArray[i].deathAnimationComplete()) {
                    enemyArray[i] = null;
                }
            }
        }

        playerCollisionBox.setBounds(megaMan.getX(), megaMan.getY() + 34, 30, 2);
        megaMan.paintPlayer(g);
        megaMan.updateImageNum((int) maxPlayerImageNum.get(megaMan.getState()));

        if (megaMan.getState().equalsIgnoreCase("Still")) {
            playerBox.setBounds(megaMan.getX(), megaMan.getY(), 30, 34);
        }
        else if (megaMan.getState().equalsIgnoreCase("Run")) {
            playerBox.setBounds(megaMan.getX(), megaMan.getY(), 35, 35);
        }
        else if ((megaMan.getState().equalsIgnoreCase("Jump")) || (megaMan.getState().equalsIgnoreCase("Jump Move"))) {
            playerBox.setBounds(megaMan.getX(), megaMan.getY(), 30, 45);
        }

    }

    private void moveEnemies() {
        for (Enemy enemy: enemyArray) {
            if (enemy != null && enemy.getBox() != null) {
                if (playerBox.intersects(enemy.getBox())) {
                    megaMan.setHealth(megaMan.getHealth() - 1);
                }

                for (Rectangle enemyWall: enemyWallCollisionBox) {
                    if (enemy.getBox().intersects(enemyWall)) {
                        if (!enemy.getIntersectsWall()) {
                            enemy.setIntersectsWall(true);
                            String direction = enemy.getState().equalsIgnoreCase("Walk Right") ? "Walk Left" : "Walk Right";
                            enemy.setState(direction);
                        }
                    }
                    else if (!(enemy.getBox().intersects(enemyWall))) {
                        if (enemy.getIntersectsWall()) {
                            enemy.setIntersectsWall(false);
                        }
                    }
                }

                enemy.move();
            }
        }
    }

    private void destroyTurretShot(int i) {
        turretShotArray[i] = null;
    }

    private void shotCollisionUpdate(int i) {
        megaMan.setBulletCount(megaMan.getBulletCount() + 1);
        playerShotArray[i] = null;
    }

    private void updateGameTime() {
        timer++;
        //If timer == 20, that means 1 second has passed since refresh rate is 50ms
        if (timer == 20) {
            time++;
            timer = 0;
        }
    }

    private void playerShotMoveAndCollisionCheck() {
        for (int i = 0; i < playerShotArray.length; i++) {
            if (playerShotArray[i] != null) {
                playerShotArray[i].moveShot();

                if (playerShotArray[i].getDirection().equalsIgnoreCase("Right")) {
                    if (playerShotArray[i].getX() > (this.getWidth() - 10)) {
                        shotCollisionUpdate(i);
                    }
                } else {
                    if (playerShotArray[i].getX() < 0) {
                        shotCollisionUpdate(i);
                    }
                }
            }

            //Shot and Wall Collision
            for (BackgroundWall backgroundWall: backgroundWalls) {
                if (playerShotArray[i] != null) {
                    if (playerShotArray[i].getShotBox().intersects(backgroundWall.getBox())) {
                        shotCollisionUpdate(i);
                    }
                }
            }

            //Shot and enemy collision
            for (Enemy enemy: enemyArray) {
                if (playerShotArray[i] != null && enemy != null && enemy.getBox() != null) {
                    if (playerShotArray[i].getShotBox().intersects(enemy.getBox())) {
                        shotCollisionUpdate(i);

                        if (enemy.getTimesHit() == 4) {
                            points += 100;
                            enemy.setImageNum(1);
                            enemy.setDestroyed();
                        } else {
                            enemy.setTimesHit(enemy.getTimesHit() + 1);
                        }
                    }
                }
            }

            //Shot and Platform Collision
            for (Platform platform: platformArray) {
                if ((playerShotArray[i] != null) && (platform.getPlatformBox() != null)) {
                    if (playerShotArray[i].getShotBox().intersects(platform.getPlatformBox())) {
                        shotCollisionUpdate(i);
                    }
                }
            }
        }
    }

    private void onGroundCollisionCheck() {
        for (BackgroundWall wall: backgroundWalls) {
            if (playerCollisionBox.intersects(wall.getBox())) {
                if (!megaMan.onGround()) {
                    if (!megaMan.isJumping()) {
                        intersecting = true;
                        megaMan.setOnGround(true);
                        if (wall.getType().equalsIgnoreCase("Bottom")) {
                            megaMan.setY(wall.getY() - 34);
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

    private void verticalWallCollisionCheck() {
        for (BackgroundWall wall: backgroundWalls) {
            if (wall.getOrientation().equalsIgnoreCase("Vertical")) {
                if ((megaMan.getDirection().equalsIgnoreCase("Right")) && (megaMan.getState().equalsIgnoreCase("Run"))) {
                    if (!megaMan.isJumping()) {
                        if (playerBox.intersects(wall.getBox())) {
                            megaMan.setState("Still");
                            megaMan.setImageNum(1);
                            megaMan.setX(wall.getX() - 35);
                        }
                    }
                }
                else if ((megaMan.getDirection().equalsIgnoreCase("Left")) && (megaMan.getState().equalsIgnoreCase("Run"))) {
                    if (!megaMan.isJumping()) {
                        if (playerBox.intersects(wall.getBox())) {
                            megaMan.setState("Still");
                            megaMan.setImageNum(1);
                            megaMan.setX(wall.getX() + 12);
                            megaMan.setY(wall.getY() + 5);
                        }
                    }
                }
            }
        }
    }

    private void createBackgroundWalls() {
        backgroundWalls[0] = new BackgroundWall(0, 50, 92, 4, "Horizontal", "Top");
        backgroundWalls[1] = new BackgroundWall(92, 50, 4, 40, "Vertical", "Top");
        backgroundWalls[2] = new BackgroundWall(92, 90, 50, 4, "Horizontal", "Top");
        backgroundWalls[3] = new BackgroundWall(142, 50, 4, 44, "Vertical", "Top");

        backgroundWalls[4] = new BackgroundWall(142, 50, 100, 4, "Horizontal", "Top");
        backgroundWalls[5] = new BackgroundWall(241, 50, 4, 40, "Vertical", "Top");
        backgroundWalls[6] = new BackgroundWall(241, 90, 50, 4, "Horizontal", "Top");
        backgroundWalls[7] = new BackgroundWall(291, 50, 4, 44, "Vertical", "Top");

        backgroundWalls[8] = new BackgroundWall(295, 50, 187, 4, "Horizontal", "Top");
        backgroundWalls[9] = new BackgroundWall(482, 50, 4, 40, "Vertical", "Top");
        backgroundWalls[10] = new BackgroundWall(482, 90, 50, 4, "Horizontal", "Top");
        backgroundWalls[11] = new BackgroundWall(532, 50, 4, 44, "Vertical", "Top");

        backgroundWalls[12] = new BackgroundWall(536, 50, 96, 4, "Horizontal", "Top");
        backgroundWalls[13] = new BackgroundWall(632, 50, 4, 40, "Vertical", "Top");
        backgroundWalls[14] = new BackgroundWall(632, 90, 50, 4, "Horizontal", "Top");
        backgroundWalls[15] = new BackgroundWall(682, 50, 4, 44, "Vertical", "Top");

        backgroundWalls[16] = new BackgroundWall(682, 50, 187, 4, "Horizontal", "Top");
        backgroundWalls[17] = new BackgroundWall(869, 50, 4, 40, "Vertical", "Top");
        backgroundWalls[18] = new BackgroundWall(869, 90, 50, 4, "Horizontal", "Top");
        backgroundWalls[19] = new BackgroundWall(919, 50, 4, 44, "Vertical", "Top");
        backgroundWalls[20] = new BackgroundWall(919, 50, 81, 4, "Horizontal", "Top");

        backgroundWalls[21] = new BackgroundWall(0, 334, 87, 100, "Horizontal", "Bottom");
        backgroundWalls[22] = new BackgroundWall(92, 338, 4, 43, "Vertical", "Bottom");
        backgroundWalls[23] = new BackgroundWall(92, 377, 200, 80, "Horizontal", "Bottom");
        backgroundWalls[24] = new BackgroundWall(288, 338, 4, 47, "Vertical", "Bottom");

        backgroundWalls[25] = new BackgroundWall(288, 334, 188, 100, "Horizontal", "Bottom");
        backgroundWalls[26] = new BackgroundWall(482, 338, 4, 43, "Vertical", "Bottom");
        backgroundWalls[27] = new BackgroundWall(482, 377, 200, 80, "Horizontal", "Bottom");
        backgroundWalls[28] = new BackgroundWall(680, 338, 4, 47, "Vertical", "Bottom");

        backgroundWalls[29] = new BackgroundWall(680, 334, 188, 100, "Horizontal", "Bottom");
        backgroundWalls[30] = new BackgroundWall(874, 338, 4, 43, "Vertical", "Bottom");
        backgroundWalls[31] = new BackgroundWall(874, 377, 200, 40, "Horizontal", "Bottom");
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
            platformArray[0] = new Platform(0, 175, 100);
            platformArray[1] = new Platform(95, 270, 200);
            platformArray[2] = new Platform(345, 200, 175);
            platformArray[3] = new Platform(600, 225, 200);
            platformArray[4] = new Platform(850, 165, 150);
        }
        else if (level == 2) {
            platformArray = new Platform[4];
            platformArray[0] = new Platform(0, 150, 100);
            platformArray[1] = new Platform(510, 270, 175);
            platformArray[2] = new Platform(125, 270, 175);
            platformArray[3] = new Platform(850, 150, 150);
        }
        else if (level == 3) {
            platformArray = new Platform[5];
            platformArray[0] = new Platform(0, 175, 100);
            platformArray[1] = new Platform(95, 270, 200);
            platformArray[2] = new Platform(345, 200, 175);
            platformArray[3] = new Platform(600, 225, 200);
            platformArray[4] = new Platform(850, 185, 150);
        }

    }

    private void generateEnemies(int i) {
        if (i < enemyArray.length) {
            enemyArray[i] = new Enemy(level);
            i++;
            generateEnemies(i);
        }
    }

    private void generateTurrets() {
        if (level == 1) {
            turretArray[0] = new Turret(772, 210, 1, "Left");
            turretArray[1] = new Turret(905, 145, 2, "Up");
        }
        else if (level == 2) {
            turretArray[0] = new Turret(950, 135, 1, "Left");
            turretArray[1] = null;
        }
        else if (level == 3) {
            turretArray[0] = new Turret(75, 160, 1, "Right");
            turretArray[1] = new Turret(295, 320, 2, "Up");
        }
    }

    private void resetNextLevel() {
        if (level == 2) {
            megaMan.setX(20);
            megaMan.setY(126);
        }
        else if (level == 3) {
            megaMan.setX(20);
            megaMan.setY(300);
        }
        megaMan.setDirection("Right");
        for (int i = 0; i < turretShotArray.length; i++) {
            if (turretShotArray[i] != null) {
                turretShotArray[i] = null;
            }
        }

        for (int i = 0; i < playerShotArray.length; i++) {
            if (playerShotArray[i] != null) {
                playerShotArray[i] = null;
            }
        }
        megaMan.setBulletCount(5);
        healthBoost = new HealthPack(level);

        generatePlatforms();
        generateTurrets();
        generateEnemies(0);
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

    private void initPlayerImageMap() {
        maxPlayerImageNum.put("Still", 4);
        maxPlayerImageNum.put("Run", 12);
        maxPlayerImageNum.put("Jump", 9);
        maxPlayerImageNum.put("Jump Move", 9);
        maxPlayerImageNum.put("Shoot", 3);
    }

}


