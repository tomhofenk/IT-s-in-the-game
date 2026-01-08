package nl.saxion.game.Bloodspire.Methodes;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Interpolation;
import nl.saxion.game.Bloodspire.Classes.Tile;
import nl.saxion.game.Bloodspire.MyLevelScreen;
import nl.saxion.gameapp.GameApp;

import java.util.ArrayList;

import static nl.saxion.game.Bloodspire.Methodes.LevelVars.*;

/**
 * Utility-class met shared methodes. Hier staat Movement.
 */
public class Methodes {

    public MapData mp = new MapData();

    // public zodat MyLevelScreen deze kan aanroepen: methodes.Movement(mv);
    public void Movement(MovementVars mv) {
        ArrayList<String> Directions = canMoveTo(mv);

        if (GameApp.isKeyJustPressed(Input.Keys.L)) {
            System.out.println("Directions gevonden: " + Directions);
        }

        // veiligheids-check: voorkom modulo 0
        if (mv.minTimeBetweenMovement <= 0) mv.minTimeBetweenMovement = 1;


        for (String currentDirection : Directions) {
            int keyCode = Keymap.keyMap.get(currentDirection);
            if (GameApp.isKeyPressed(keyCode)) {
                if (MovementVars.framesKeyIsPressed.get(currentDirection) % mv.minTimeBetweenMovement == 0
                        && (MovementVars.framesWhenKeyWasPressed.get(currentDirection) + GameApp.getFramesPerSecond() / 3 <= mv.framesCounter
                        || !MovementVars.hasKeyBeenPressedOnce.get(currentDirection))
                        && (mv.framesSinceLastMovement + GameApp.getFramesPerSecond() / 4 <= mv.framesCounter)) {
                    switch (currentDirection) {
                        case "W" -> mv.playerWorldY += mv.pixelPerGridTile;   // omhoog
                        case "A" -> mv.playerWorldX -= mv.pixelPerGridTile;   // links
                        case "S" -> mv.playerWorldY -= mv.pixelPerGridTile;   // omlaag
                        case "D" -> mv.playerWorldX += mv.pixelPerGridTile;   // rechts
                    }
                    MovementVars.framesWhenKeyWasPressed.put(currentDirection, mv.framesCounter);
                    mv.framesSinceLastMovement = mv.framesCounter;
                }
                MovementVars.framesKeyIsPressed.put(currentDirection, MovementVars.framesKeyIsPressed.get(currentDirection) + 1
                );
                MovementVars.hasKeyBeenPressedOnce.put(currentDirection, true);
            } else {
                MovementVars.framesKeyIsPressed.put(currentDirection, 0);
            }
        }
        switchToBattle(mv);
    }

    public ArrayList<String> canMoveTo(MovementVars mv) {

        ArrayList<String> possibleDirections = new ArrayList<>();
        possibleDirections.add("W");
        possibleDirections.add("A");
        possibleDirections.add("S");
        possibleDirections.add("D");

        for (Tile ct : mv.mapData) { //ct = currentTile
            // W (omhoog)
            if (mv.playerTileY + 1 == ct.gridY && mv.playerTileX == ct.gridX) {
                if (!ct.walkable) possibleDirections.remove("W");
            }

            // A (links)
            if (mv.playerTileX - 1 == ct.gridX && mv.playerTileY == ct.gridY) {
                if (!ct.walkable) possibleDirections.remove("A");
            }

            // S (omlaag)
            if (mv.playerTileY - 1 == ct.gridY && mv.playerTileX == ct.gridX) {
                if (!ct.walkable) possibleDirections.remove("S");
            }

            // D (rechts)
            if (mv.playerTileX + 1 == ct.gridX && mv.playerTileY == ct.gridY) {
                if (!ct.walkable) possibleDirections.remove("D");
            }

        }

        return possibleDirections;
    }

    public void getOldCords (MovementVars mv, LevelVars lv, int startX, int startY) {
        if ((startX != lv.getOldX() || startY != lv.getOldY()) && lv.getOldX() != 0 && lv.getOldY() != 0) {
            mv.playerWorldX = lv.getOldX() * mv.pixelPerGridTile;
            mv.playerWorldY = lv.getOldY() *  mv.pixelPerGridTile;
        }
    }

    public void setOldCords (MovementVars mv, LevelVars lv) {
        lv.setOldX(mv.playerTileX);
        lv.setOldY(mv.playerTileY);
    }

    public void addAllTextures () {
        GameApp.addTexture("CharacterTexture", "textures/DungeonCharacterpng.png");
        GameApp.addTexture("Enemy", "textures/Enemy.png");
        GameApp.addTexture("Black", "textures/Black.png");
        GameApp.addTexture("BlackGrid", "textures/BlackGrid.png");
        GameApp.addTexture("BlackHighlight", "textures/BlackHighlight.png");
        GameApp.addTexture("HUDShadow", "textures/HUDShadow.png");
        // Objecten
        GameApp.addTexture("WallOpenSide", "textures/Wall20.png");
        GameApp.addTexture("WallLeftSide", "textures/Wall21.png");
        GameApp.addTexture("WallRightSide", "textures/Wall22.png");
        GameApp.addTexture("WallBothSide", "textures/Wall23.png");
    }

    public void disposeAllTextures () {
        GameApp.disposeTexture("CharacterTexture");
        GameApp.disposeTexture("Enemy");
        GameApp.disposeTexture("Black");
        GameApp.disposeTexture("BlackGrid");
        GameApp.disposeTexture("BlackHighlight");
        GameApp.disposeTexture("HUDShadow");
        // Objecten
        GameApp.disposeTexture("WallOpenSide");
        GameApp.disposeTexture("WallLeftSide");
        GameApp.disposeTexture("WallRightSide");
        GameApp.disposeTexture("WallBothSide");
    }

    public void gameLogic(MovementVars mv) {

        // roep de gedeelde movement aan
        Movement(mv);

        // optioneel: debug print
        if (GameApp.isKeyJustPressed(Input.Keys.P)) {
            System.out.println("tile: " + mv.playerTileX + "x " + mv.playerTileY
                    + "y world: " + mv.playerWorldX + "x " + mv.playerWorldY + "y");
        }

        // escape -> main menu
        if (GameApp.isKeyJustPressed(Input.Keys.ESCAPE)) {
            GameApp.switchScreen("MainMenuScreen");
        }

        //Inventory scherm openen
        if (GameApp.isKeyJustPressed(Input.Keys.I)) {
            GameApp.switchScreen("InventoryScreen");
        }
    }

    public void renderWorld(MovementVars mv) {

        renderGridTiles(mv);

        GameApp.startSpriteRendering();
        GameApp.drawTexture("CharacterTexture", mv.playerWorldX, mv.playerWorldY);
        GameApp.endSpriteRendering();

    }

    public void renderHUD() {
        drawShadows();
    }

    public boolean checkEnemy(MovementVars mv){
        for (Tile t : mv.mapData) {
            if (t.tileType.equalsIgnoreCase("Enemy")) {
                System.out.println("Er zitten nog enemies in het level!");;
                return false;
            }
        }
        System.out.println("Alle enemies zijn verslagen, op naar het volgende level!!!!");
        mv.mapData = MapData.getLevel(LevelVars.getCurrentLevel());
        return true;
    }

    private void renderGridTiles(MovementVars mv) {
        drawGrid(mv);
        drawHighlightedTiles(mv);
        renderTextures(mv);

    }

    private void renderTextures(MovementVars mv) {
        boolean rightSideIsWall;
        boolean leftSideIsWall;

        GameApp.startSpriteRendering();
        for (Tile tile : mv.mapData) {
            //String textureName = getTextureForTileType(tile.tileType);
            rightSideIsWall = false;
            leftSideIsWall = false;
            if (tile.tileType.equals("Wall")) {
                //GameApp.drawTexture(textureName, tile.worldX, tile.worldY, mv.pixelPerGridTile, mv.pixelPerGridTile);
                for (Tile tile2 : mv.mapData) {
                    if ((tile2.gridX-1 == tile.gridX && tile2.gridY == tile.gridY) && tile2.tileType.equalsIgnoreCase("Wall")) {
                        leftSideIsWall = true;
                    }
                    if ((tile2.gridX+1 == tile.gridX && tile2.gridY == tile.gridY) && tile2.tileType.equalsIgnoreCase("Wall")) {
                        rightSideIsWall = true;
                    }
                }
                if (leftSideIsWall && rightSideIsWall) {
                    GameApp.drawTexture("WallOpenSide", tile.worldX, tile.worldY, mv.pixelPerGridTile, mv.pixelPerGridTile);
                } else if (!leftSideIsWall && rightSideIsWall) {
                    GameApp.drawTexture("WallRightSide", tile.worldX, tile.worldY, mv.pixelPerGridTile, mv.pixelPerGridTile);
                } else if (leftSideIsWall) {
                    GameApp.drawTexture("WallLeftSide", tile.worldX, tile.worldY, mv.pixelPerGridTile, mv.pixelPerGridTile);
                } else {
                    GameApp.drawTexture("WallBothSide", tile.worldX, tile.worldY, mv.pixelPerGridTile, mv.pixelPerGridTile);
                }

            } else {
                GameApp.drawTexture(tile.tileType, tile.worldX, tile.worldY, mv.pixelPerGridTile, mv.pixelPerGridTile);

            }

        }
        GameApp.endSpriteRendering();
    }

    private void drawShadows() {
        GameApp.enableTransparency();
        GameApp.startSpriteRendering();
        GameApp.drawTexture("HUDShadow", 0, 0);
        GameApp.endSpriteRendering();
        GameApp.disableTransparency();
    }

    private void drawGrid(MovementVars mv) {
        GameApp.startSpriteRendering();
        for (int y = 0; y < mv.worldHeight / mv.pixelPerGridTile; y++) {
            for (int x = 0; x < mv.worldWidth / mv.pixelPerGridTile; x++) {
                GameApp.drawTexture("BlackGrid",x * mv.pixelPerGridTile, y * mv.pixelPerGridTile, mv.pixelPerGridTile, mv.pixelPerGridTile);
            }
        }
        GameApp.endSpriteRendering();
    }

    private void drawHighlightedTiles(MovementVars mv) {
        int tx = mv.playerTileX;
        int ty = mv.playerTileY;

        GameApp.startSpriteRendering();
        GameApp.drawTexture("BlackHighLight", tx * mv.pixelPerGridTile, ty * mv.pixelPerGridTile, mv.pixelPerGridTile, mv.pixelPerGridTile);
        GameApp.drawTexture("BlackHighLight", (tx - 1) * mv.pixelPerGridTile, ty * mv.pixelPerGridTile, mv.pixelPerGridTile, mv.pixelPerGridTile);
        GameApp.drawTexture("BlackHighLight", (tx + 1) * mv.pixelPerGridTile, ty * mv.pixelPerGridTile, mv.pixelPerGridTile, mv.pixelPerGridTile);
        GameApp.drawTexture("BlackHighLight", tx * mv.pixelPerGridTile, (ty - 1) * mv.pixelPerGridTile, mv.pixelPerGridTile, mv.pixelPerGridTile);
        GameApp.drawTexture("BlackHighLight", tx * mv.pixelPerGridTile, (ty + 1) * mv.pixelPerGridTile, mv.pixelPerGridTile, mv.pixelPerGridTile);
        GameApp.endSpriteRendering();
    }

    private void switchToBattle(MovementVars mv) {
        for (Tile ct : mv.mapData) {
            if (ct.tileType.equalsIgnoreCase("Enemy") && ct.gridX == mv.playerTileX && ct.gridY == mv.playerTileY) {
                GameApp.switchScreen("BattleScreen");
            }
        }
    }
}
