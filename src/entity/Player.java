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
        this.gp = gp;
        this.keyH = keyH;

        // Player position relative to screen (px)
        screenX = gp.screenWidth / 2 - gp.tileSize / 2;
        screenY = gp.screenHeight / 2 - gp.tileSize / 2;

        solidArea = new Rectangle(10, 16, 28, 28);

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 12;
        worldY = gp.tileSize * 16;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        try {
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
        if (!keyH.upPressed && !keyH.downPressed && !keyH.leftPressed && !keyH.rightPressed) {
            direction = "stationary";
        }
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

            collisionOn = false;
            gp.collisionCh.checkTile(this);

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