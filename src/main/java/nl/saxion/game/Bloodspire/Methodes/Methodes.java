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


}
