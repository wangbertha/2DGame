package main;

import entity.Entity;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1 = -1;
        int tileNum2 = -1;

        // Identify the type of the 2 possible tiles that the entity newly encounters
        if (entity.direction == "up") {
            entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;

            tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
            tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
        } else if (entity.direction == "down") {
            entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;

            // Identify the 2 possible tiles that the entity newly encounters
            tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
            tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
        } else if (entity.direction == "left") {
            entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;

            // Identify the 2 possible tiles that the entity newly encounters
            tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
            tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
        } else if (entity.direction == "right") {
            entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;

            // Identify the 2 possible tiles that the entity newly encounters
            tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
            tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
        }

        // Check if the encountered tiles are collision tiles
        if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
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

                if (entity.direction == "up") {
                    entity.solidArea.y -= entity.speed;
                } else if (entity.direction == "down") {
                    entity.solidArea.y += entity.speed;
                } else if (entity.direction == "left") {
                    entity.solidArea.x -= entity.speed;
                } else if (entity.direction == "right") {
                    entity.solidArea.x += entity.speed;
                }
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
}
