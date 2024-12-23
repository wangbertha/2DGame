package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {
        // Applies Player to GamePanel and keyboard interactivity
        this.gp = gp;
        this.keyH = keyH;

        // Location (relative to screen) (px)
        screenX = gp.screenWidth / 2 - gp.tileSize / 2; // Screen center
        screenY = gp.screenHeight / 2 - gp.tileSize / 2; // Screen center

        // Solid Body
        solidArea = new Rectangle(10, 16, 28, 28);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        // Initialize location relative to world map, speed, and direction
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        // Location: (12, 16) tiles
        worldX = gp.tileSize * 12;
        worldY = gp.tileSize * 16;

        // Pixels per update interval
        speed = 4;

        // Direction
        direction = "stationary";
    }

    public void getPlayerImage() {
        try {
            // Load player images
            // 2 images are used for each direction to create animation
            stationary = ImageIO.read(getClass().getResourceAsStream("/res/player/front.png"));
            up1 = ImageIO.read(getClass().getResourceAsStream("/res/player/up-1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/res/player/up-2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/res/player/down-1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/res/player/down-2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/res/player/left-1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/res/player/left-2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/res/player/right-1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/res/player/right-2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        // Set direction to stationary when no key is pressed
        if (!keyH.upPressed && !keyH.downPressed && !keyH.leftPressed && !keyH.rightPressed) {
            direction = "stationary";
        }

        // Set direction according to key pressed
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed) {
                direction = "right";
            }

            // Re-check player colliding conditions
            collisionOn = false;
            gp.collisionCh.checkTile(this);

            int objIndex = gp.collisionCh.checkObject(this);
            pushObject(objIndex);
            if (gp.collisionCh.checkWin(objIndex)) {
                System.out.println("You win!");
            }

            // Only update location if player is not colliding
            if (collisionOn == false) {
                if (direction == "up") {
                    worldY -= speed;
                } else if (direction == "down") {
                    worldY += speed;
                } else if (direction == "left") {
                    worldX -= speed;
                } else if (direction == "right") {
                    worldX += speed;
                }
            }

            // Alternate images to animate player
            animCounter++;
            if (animCounter > 12) {
                if (imageNum == 1) {
                    imageNum = 2;
                } else if (imageNum == 2) {
                    imageNum = 1;
                }
                animCounter = 0;
            }
        }
    }

    public void pushObject(int i) {
        if (i != -1) {
            // If object does not collide, move the object
            if (!gp.collisionCh.checkObjectTile(direction, speed, gp.obj[i])) {
                if (direction == "up") {
                    gp.obj[i].worldY -= speed;
                } else if (direction == "down") {
                    gp.obj[i].worldY += speed;
                } else if (direction == "left") {
                    gp.obj[i].worldX -= speed;
                } else if (direction == "right") {
                    gp.obj[i].worldX += speed;
                }
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        if (direction == "stationary") {
            image = stationary;
        } else if (direction == "up") {
            if (imageNum == 1) {
                image = up1;
            } else if (imageNum == 2) {
                image = up2;
            }
        } else if (direction == "down") {
            if (imageNum == 1) {
                image = down1;
            } else if (imageNum == 2) {
                image = down2;
            }
        } else if (direction == "left") {
            if (imageNum == 1) {
                image = left1;
            } else if (imageNum == 2) {
                image = left2;
            }
        } else if (direction == "right") {
            if (imageNum == 1) {
                image = right1;
            } else if (imageNum == 2) {
                image = right2;
            }
        }

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}