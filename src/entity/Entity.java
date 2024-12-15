package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
    public int worldX, worldY;
    public int speed;

    public String direction;
    public BufferedImage stationary, up1, up2, down1, down2, left1, left2, right1, right2;
    public int imageNum = 1;
    public int animCounter = 0;

    public Rectangle solidArea;
    public boolean collisionOn = false;
}
