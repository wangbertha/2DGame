package object;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ObjBox extends SuperObject {
    public ObjBox() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/box.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        name = "Box";
        collision = true;
        solidArea = new Rectangle(4, 4, 40, 40);
    }

    public void update(String direction, int speed) {
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
}
