package main;

import object.ObjBox;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        // Create Box object and place at (6, 6)
        gp.obj[0] = new ObjBox();
        gp.obj[0].worldX = 6 * gp.tileSize;
        gp.obj[0].worldY = 6 * gp.tileSize;
    }
}
