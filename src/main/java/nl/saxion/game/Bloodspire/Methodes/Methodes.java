package nl.saxion.game.Bloodspire.Methodes;

import com.badlogic.gdx.Input;
import nl.saxion.game.Bloodspire.Classes.Tile;
import nl.saxion.gameapp.GameApp;

import java.util.ArrayList;

/**
 * Utility-class met shared methodes. Hier staat Movement.
 */
public class Methodes {

    //private MovementVars movementVars;

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

    public int getStartX (int oldX, boolean hasBeenPlayed, int startX) {
        if (hasBeenPlayed) {
            return oldX;
        } else {
            return startX;
        }
    }

/*
        // -- W (omhoog)
        if ((GameApp.isKeyPressed(Input.Keys.W) || GameApp.isKeyPressed(Input.Keys.UP))
                && mv.playerTileY < (mv.worldHeight / mv.pixelPerGridTile - 1)) {

            if (mv.framesWIsPressed % mv.minTimeBetweenMovement == 0
                    && (mv.framesWhenWWasPressed + GameApp.getFramesPerSecond() / 3 <= mv.framesCounter
                    || !mv.hasWBeenPressedOnce)) {

                mv.playerWorldY += mv.pixelPerGridTile;
                mv.framesWhenWWasPressed = mv.framesCounter;
            }
            mv.framesWIsPressed++;
            mv.hasWBeenPressedOnce = true;

        } else {
            mv.framesWIsPressed = 0;
        }

        // -- A (links)
        if ((GameApp.isKeyPressed(Input.Keys.A) || GameApp.isKeyPressed(Input.Keys.LEFT))
                && mv.playerTileX > 0) {

            if (mv.framesAIsPressed % mv.minTimeBetweenMovement == 0
                    && (mv.framesWhenAWasPressed + GameApp.getFramesPerSecond() / 3 <= mv.framesCounter
                    || !mv.hasABeenPressedOnce)) {

                mv.playerWorldX -= mv.pixelPerGridTile;
                mv.framesWhenAWasPressed = mv.framesCounter;
            }
            mv.framesAIsPressed++;
            mv.hasABeenPressedOnce = true;

        } else {
            mv.framesAIsPressed = 0;
        }

        // -- S (omlaag)
        if ((GameApp.isKeyPressed(Input.Keys.S) || GameApp.isKeyPressed(Input.Keys.DOWN))
                && mv.playerTileY > 0) {

            if (mv.framesSIsPressed % mv.minTimeBetweenMovement == 0
                    && (mv.framesWhenSWasPressed + GameApp.getFramesPerSecond() / 3 <= mv.framesCounter
                    || !mv.hasSBeenPressedOnce)) {

                mv.playerWorldY -= mv.pixelPerGridTile;
                mv.framesWhenSWasPressed = mv.framesCounter;
            }
            mv.framesSIsPressed++;
            mv.hasSBeenPressedOnce = true;

        } else {
            mv.framesSIsPressed = 0;
        }

        // -- D (rechts)
        if ((GameApp.isKeyPressed(Input.Keys.D) || GameApp.isKeyPressed(Input.Keys.RIGHT))
                && mv.playerTileX < (mv.worldWidth / mv.pixelPerGridTile - 1)) {

            if (mv.framesDIsPressed % mv.minTimeBetweenMovement == 0
                    && (mv.framesWhenDWasPressed + GameApp.getFramesPerSecond() / 3 <= mv.framesCounter
                    || !mv.hasDBeenPressedOnce)) {

                mv.playerWorldX += mv.pixelPerGridTile;
                mv.framesWhenDWasPressed = mv.framesCounter;
            }
            mv.framesDIsPressed++;
            mv.hasDBeenPressedOnce = true;

        } else {
            mv.framesDIsPressed = 0;
        }

        // -- MOUSE (linkerknop)
        if (GameApp.isButtonJustPressed(Input.Buttons.LEFT)) {

            if (mv.framesMouseIsPressed % mv.minTimeBetweenMovement == 0
                    && (mv.framesWhenMouseWasPressed + mv.minTimeBetweenMovement <= mv.framesCounter
                    || !mv.hasMouseBeenPressedOnce)) {

                int mouseTileX = mv.mouseX / mv.pixelPerGridTile;
                int mouseTileY = mv.mouseY / mv.pixelPerGridTile;

                if (mouseTileX >= mv.playerTileX - 1 && mouseTileX <= mv.playerTileX + 1
                        && mouseTileY == mv.playerTileY) {

                    mv.playerWorldX = mouseTileX * mv.pixelPerGridTile;
                    mv.playerWorldY = mouseTileY * mv.pixelPerGridTile;

                } else if (mouseTileY >= mv.playerTileY - 1 && mouseTileY <= mv.playerTileY + 1
                        && mouseTileX == mv.playerTileX) {

                    mv.playerWorldX = mouseTileX * mv.pixelPerGridTile;
                    mv.playerWorldY = mouseTileY * mv.pixelPerGridTile;
                }

                mv.framesWhenMouseWasPressed = mv.framesCounter;
            }

            mv.framesMouseIsPressed++;
            mv.hasMouseBeenPressedOnce = true;

        } else {
            mv.framesMouseIsPressed = 0;
        }

        // Zorg dat tile-coördinaten consistent blijven na beweging
        mv.playerTileX = mv.playerWorldX / mv.pixelPerGridTile;
        mv.playerTileY = mv.playerWorldY / mv.pixelPerGridTile;
*/

    /*
    - for lus met W,A,S,D als opties in een set, arraylist of list en dan alleen degene checken die erin zitten bij movement
    dus eerst de opties vullen met W,A,S,D en dan movement
    - Dezelfde movement maar dan met canMoveTo met een direction in de argumenten van de Movement
     */

}
