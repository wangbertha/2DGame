package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
    // Location (relative to world map) (px) and speed
    public int worldX, worldY;
    public int speed;

    // Animation
    public String direction;
    public BufferedImage stationary, up1, up2, down1, down2, left1, left2, right1, right2;
    public int imageNum = 1;
    public int animCounter = 0;

    // Solid-body and collisions
    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
}
