package object;

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
    }
}
