package nl.saxion.game.Bloodspire.Methodes;

import nl.saxion.game.Bloodspire.Classes.Tile;

/**
 * Alle movement-gerelateerde variabelen zitten hier.
 * Maak velden public zodat andere packages er direct bij kunnen (eenvoudig).
 */
public class MovementVars {
    // tile / world settings
    public int pixelPerGridTile = 64;

    // position in world pixels (gebruik deze om te renderen)
    public int playerWorldX = 0;
    public int playerWorldY = 0;

    // position in tile coords (playerWorld / pixelPerGridTile)
    public int playerTileX = 0;
    public int playerTileY = 0;

    // world boundaries (moet door screen gezet worden bij show())
    public int worldHeight;
    public int worldWidth;

    // input state (screen vult mouse elke frame)
    public int mouseX;
    public int mouseY;

    // timing / frames
    public int minTimeBetweenMovement = 1; // >0 belangrijk
    public int framesCounter = 0;

    // toets-hold state
    public int framesWIsPressed = 0;
    public boolean hasWBeenPressedOnce = false;
    public int framesWhenWWasPressed = 0;

    public int framesAIsPressed = 0;
    public boolean hasABeenPressedOnce = false;
    public int framesWhenAWasPressed = 0;

    public int framesSIsPressed = 0;
    public boolean hasSBeenPressedOnce = false;
    public int framesWhenSWasPressed = 0;

    public int framesDIsPressed = 0;
    public boolean hasDBeenPressedOnce = false;
    public int framesWhenDWasPressed = 0;

    public int framesMouseIsPressed = 0;
    public boolean hasMouseBeenPressedOnce = false;
    public int framesWhenMouseWasPressed = 0;

    public Tile mapData = new Tile();

    // constructor: geef world sizes en startpositie (pixel coords)
    public MovementVars(int startWorldX, int startWorldY, int worldHeight, int worldWidth, int mouseX, int mouseY, int minTimeBetweenMovement, Tile mapData) {
        this.playerWorldX = startWorldX;
        this.playerWorldY = startWorldY;
        this.worldHeight = worldHeight;
        this.worldWidth = worldWidth;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.minTimeBetweenMovement = Math.max(1, minTimeBetweenMovement); // veilig
        this.mapData = mapData;
    }
}
