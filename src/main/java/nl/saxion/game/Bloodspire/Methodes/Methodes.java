package nl.saxion.game.Bloodspire.Methodes;

import com.badlogic.gdx.Input;
import nl.saxion.gameapp.GameApp;

/**
 * Utility-class met shared methodes. Hier staat Movement.
 */
public class Methodes {

    // public zodat MyLevelScreen deze kan aanroepen: methodes.Movement(mv);
    public void Movement(MovementVars mv) {

        // veiligheids-check: voorkom modulo 0
        if (mv.minTimeBetweenMovement <= 0) mv.minTimeBetweenMovement = 1;

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
    }
}
