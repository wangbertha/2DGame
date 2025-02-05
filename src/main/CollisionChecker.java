package main;

import entity.Entity;
import object.SuperObject;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        // Calculate boundaries for entity solid area relative to world map (px)
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        // Calculate boundaries for entity solid area relative to world map (tiles)
        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        // Initialize tile type variable
        int tileNum1Type = -1;
        int tileNum2Type = -1;

        // Identify the type of the 2 possible tiles that the entity newly encounters
        // - Shift entity solid area boundary towards direction
        if (entity.direction == "up") {
            entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
            tileNum1Type = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
            tileNum2Type = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
        } else if (entity.direction == "down") {
            entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
            tileNum1Type = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
            tileNum2Type = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
        } else if (entity.direction == "left") {
            entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
            tileNum1Type = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
            tileNum2Type = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
        } else if (entity.direction == "right") {
            entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
            tileNum1Type = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
            tileNum2Type = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
        }

        // Check if the encountered tiles are collision tiles
        if (gp.tileM.tile[tileNum1Type].collision || gp.tileM.tile[tileNum2Type].collision) {
            entity.collisionOn = true;
        }
    }

    public int checkObject(Entity entity) {
        int index = -1;

        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null) {
                // Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // Get object's solid area position
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

                // Shift entity solid area boundary towards direction
                if (entity.direction == "up") {
                    entity.solidArea.y -= entity.speed;
                } else if (entity.direction == "down") {
                    entity.solidArea.y += entity.speed;
                } else if (entity.direction == "left") {
                    entity.solidArea.x -= entity.speed;
                } else if (entity.direction == "right") {
                    entity.solidArea.x += entity.speed;
                }

                // Check if entity solid area and object solid area intersects
                // If intersects, store value
                if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                    if (gp.obj[i].collision) {
                        entity.collisionOn = true;
                    }
                    index = i;
                }

                // Reset entity solidArea values
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
            }
        }

        return index;
    }

    public boolean checkObjectTile(String direction, int speed, SuperObject obj) {
        // Calculate boundaries for obj solid area relative to world map (px)
        int objLeftWorldX = obj.worldX + obj.solidArea.x;
        int objRightWorldX = obj.worldX + obj.solidArea.x + obj.solidArea.width;
        int objTopWorldY = obj.worldY + obj.solidArea.y;
        int objBottomWorldY = obj.worldY + obj.solidArea.y + obj.solidArea.height;

        // Calculate boundaries for obj solid area relative to world map (tiles)
        int objLeftCol = objLeftWorldX / gp.tileSize;
        int objRightCol = objRightWorldX / gp.tileSize;
        int objTopRow = objTopWorldY / gp.tileSize;
        int objBottomRow = objBottomWorldY / gp.tileSize;

        // Initialize tile type variable
        int tileNum1Type = -1;
        int tileNum2Type = -1;

        // Identify the type of the 2 possible tiles that the obj newly encounters
        // - Shift obj solid area boundary towards direction
        if (direction == "up") {
            objTopRow = (objTopWorldY - speed) / gp.tileSize;
            tileNum1Type = gp.tileM.mapTileNum[objLeftCol][objTopRow];
            tileNum2Type = gp.tileM.mapTileNum[objRightCol][objTopRow];
        } else if (direction == "down") {
            objBottomRow = (objBottomWorldY + speed) / gp.tileSize;
            tileNum1Type = gp.tileM.mapTileNum[objLeftCol][objBottomRow];
            tileNum2Type = gp.tileM.mapTileNum[objRightCol][objBottomRow];
        } else if (direction == "left") {
            objLeftCol = (objLeftWorldX - speed) / gp.tileSize;
            tileNum1Type = gp.tileM.mapTileNum[objLeftCol][objTopRow];
            tileNum2Type = gp.tileM.mapTileNum[objLeftCol][objBottomRow];
        } else if (direction == "right") {
            objRightCol = (objRightWorldX + speed) / gp.tileSize;
            tileNum1Type = gp.tileM.mapTileNum[objRightCol][objTopRow];
            tileNum2Type = gp.tileM.mapTileNum[objRightCol][objBottomRow];
        }

        // Check if the encountered tiles are collision tiles
        if (gp.tileM.tile[tileNum1Type].collision || gp.tileM.tile[tileNum2Type].collision) {
            return true;
        }

        return false;
    }

    public boolean checkWin(SuperObject obj) {
        // Calculate boundaries for obj solid area relative to world map (px)
        int objLeftWorldX = obj.worldX + obj.solidArea.x;
        int objRightWorldX = obj.worldX + obj.solidArea.x + obj.solidArea.width;
        int objTopWorldY = obj.worldY + obj.solidArea.y;
        int objBottomWorldY = obj.worldY + obj.solidArea.y + obj.solidArea.height;

        // Calculate boundaries for obj solid area relative to world map (tiles)
        int objLeftCol = objLeftWorldX / gp.tileSize;
        int objRightCol = objRightWorldX / gp.tileSize;
        int objTopRow = objTopWorldY / gp.tileSize;
        int objBottomRow = objBottomWorldY / gp.tileSize;

        // Check if obj is entirely contained in one tile, and tile is a goal type
        if (objLeftCol == objRightCol && objTopRow == objBottomRow
                && gp.tileM.tile[gp.tileM.mapTileNum[objLeftCol][objTopRow]].goal) {
            return true;
        }

        return false;
    }
}
