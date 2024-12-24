package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class SuperObject {
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;

    public void update(GamePanel gp, String direction, int speed) {
        if (direction == "up") {
            worldY -= speed;
        } else if (direction == "down") {
            worldY += speed;
        } else if (direction == "left") {
            worldX -= speed;
        } else if (direction == "right") {
            worldX += speed;
        }
        if (gp.collisionCh.checkWin(this)) {
            System.out.println("You win!");
        }
    }

    public void draw(Graphics2D g2, GamePanel gp) {
        // Calculate location relative to screen
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        // Draw object if within screen boundaries
        if (worldX > gp.player.worldX - gp.player.screenX - gp.tileSize
                && worldX < gp.player.worldX + gp.player.screenX + gp.tileSize
                && worldY > gp.player.worldY - gp.player.screenY - gp.tileSize
                && worldY < gp.player.worldY + gp.player.screenY + gp.tileSize) {
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
}
