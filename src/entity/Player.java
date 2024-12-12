package entity;

import java.awt.Color;
import java.awt.Graphics2D;

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

        setDefaultValues();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 12;
        worldY = gp.tileSize * 16;
        speed = 4;
    }

    public void update() {
        if (keyH.upPressed == true) {
            worldY -= speed;
        } else if (keyH.downPressed == true) {
            worldY += speed;
        } else if (keyH.leftPressed == true) {
            worldX -= speed;
        } else if (keyH.rightPressed == true) {
            worldX += speed;
        }
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.white);
        g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
    }
}
