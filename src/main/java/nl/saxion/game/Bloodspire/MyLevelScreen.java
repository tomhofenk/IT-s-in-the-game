package nl.saxion.game.Bloodspire;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.CameraControlledGameScreen;
import nl.saxion.gameapp.screens.GameScreenWithHUD;

public class MyLevelScreen extends CameraControlledGameScreen {
    private float playerX, playerY;
    int pixelPerGridTile = 64; // Same value as in Main.java
    int minTimeBetweenMovement = 0;
    int playerTileX = 0;
    int playerTileY = 0;
    int framesCounter = 0;
    int framesWIsPressed = 0;
    int framesAIsPressed = 0;
    int framesSIsPressed = 0;
    int framesDIsPressed = 0;
    int framesWhenWWasPressed = 0;
    int framesWhenAWasPressed = 0;
    int framesWhenSWAasPressed = 0;
    int framesWhenDWAasPressed = 0;
    boolean hasWBeenPressedOnce = false;
    boolean hasABeenPressed = false;
    boolean hasSBeenPressed = false;
    boolean hasDBeenPressed = false;
    int framesMouseIsPressed = 0;
    int framesWhenMouseWasPressed = 0;
    boolean hasMouseBeenPressed = false;

    public MyLevelScreen(int viewportWidth, int viewportHeight, int worldWidth, int worldHeight) {
        // Define camera viewport (visible area) and world size
        // Example: viewport 16x9 is, world has size 100x50
        super(viewportWidth, viewportHeight, worldWidth, worldHeight);
    }

    @Override
    public void show() {
        enableHUD(160, 90);
        // Initialize your objects, e.g., player starting position
        playerX = 0;
        playerY = 0;

        // Start camera centered on the player
        setCameraTargetInstantly(playerX, playerY);
        GameApp.addTexture("CharacterTexture", "textures/DungeonCharacterpng.png");
    }

    @Override
    public void render(float delta) {
        // Handle your game logic and input
        gameLogic();

        // When you have moved the player, let the camera know (so it stays in the center of the screen).
        setCameraTarget(playerX, playerY);

        // ALWAYS CALL super.render(delta) AFTERWARDS!!!
        // This applies the camera settings to the shape renderer and sprite batch.
        super.render(delta);

        GameApp.clearScreen();
        renderWorld();
        renderHUD();
    }

    @Override
    public void hide() {
        GameApp.disposeTexture("CharacterTexture");
    }

    public void gameLogic() {
        framesCounter++;

        playerTileX = (int) playerX / pixelPerGridTile;
        playerTileY = (int) playerY / pixelPerGridTile;

        // Movement character WASD/Arrows/Mouse Grid based
        minTimeBetweenMovement = GameApp.getFramesPerSecond()/3;
        Movement();

        // Quit to mainmenu
        if (GameApp.isKeyPressed(Input.Keys.ESCAPE)) {
            GameApp.switchScreen("MainMenuScreen");
        }

        // Print X and Y (For Testing)
        if (GameApp.isKeyJustPressed(Input.Keys.P)) {
            System.out.println(playerTileX + " " + playerTileY);
        }
    }

    public void renderWorld() {
        switchToWorldRendering();

        renderGridTiles(pixelPerGridTile);

        GameApp.startSpriteRendering();
        GameApp.drawTexture("CharacterTexture", playerX, playerY);
        GameApp.endSpriteRendering();
    }

    public void renderHUD() {
        switchToHudRendering();

        GameApp.startShapeRenderingFilled();
        GameApp.drawRect(10, 10, 30, 5, Color.WHITE);
        GameApp.endShapeRendering();
    }

    public void renderGridTiles(int pixelsPerGridTile) {
        switchToWorldRendering();
        GameApp.startShapeRenderingOutlined();
        GameApp.setLineWidth(1);
        for (int y = 0; y < getWorldHeight() / pixelsPerGridTile; y++) {
            for (int x = 0; x < getWorldWidth() / pixelsPerGridTile; x++) {
                // Draw each tile without filling so only the border
                GameApp.drawRect((x * pixelsPerGridTile), (y * pixelsPerGridTile), pixelsPerGridTile, pixelsPerGridTile, "stone-500");
            }
        }
        // Draw the white 5 tiles around the player
        GameApp.drawRect(playerTileX * pixelsPerGridTile, playerTileY * pixelsPerGridTile, pixelsPerGridTile, pixelsPerGridTile, "stone-50");
        GameApp.drawRect((playerTileX - 1) * pixelsPerGridTile, playerTileY * pixelsPerGridTile, pixelsPerGridTile, pixelsPerGridTile, "stone-50");
        GameApp.drawRect((playerTileX + 1) * pixelsPerGridTile, playerTileY * pixelsPerGridTile, pixelsPerGridTile, pixelsPerGridTile, "stone-50");
        GameApp.drawRect(playerTileX * pixelsPerGridTile, (playerTileY - 1) * pixelsPerGridTile, pixelsPerGridTile, pixelsPerGridTile, "stone-50");
        GameApp.drawRect(playerTileX * pixelsPerGridTile, (playerTileY + 1) * pixelsPerGridTile, pixelsPerGridTile, pixelsPerGridTile, "stone-50");
        GameApp.endShapeRendering();
    }

    private void Movement() {

        // per 60 : w3 frames dat je een knop vast hebt verplaats je (IPV elke keer opnieuw moeten klikken)
        // Laatste argument is om te zorgen dat je niet buiten de map kan
        if ((GameApp.isKeyPressed(Input.Keys.W) || GameApp.isKeyPressed(Input.Keys.UP))
                && playerTileY < (getWorldHeight() / pixelPerGridTile - 1)) {
            if (framesWIsPressed % minTimeBetweenMovement == 0
                    && (framesWhenWWasPressed + GameApp.getFramesPerSecond() / 3 <= framesCounter || !hasWBeenPressedOnce)) {
                playerY += pixelPerGridTile;
                framesWhenWWasPressed = framesCounter;
            }
            framesWIsPressed++;
            hasWBeenPressedOnce = true;
        } else {
            framesWIsPressed = 0;
        }
        // A
        if ((GameApp.isKeyPressed(Input.Keys.A) || GameApp.isKeyPressed(Input.Keys.LEFT))
                && playerTileX > 0) {
            if (framesAIsPressed % minTimeBetweenMovement == 0
                    && (framesWhenAWasPressed + GameApp.getFramesPerSecond() / 3 <= framesCounter || !hasABeenPressed)) {
                playerX -= pixelPerGridTile;
                framesWhenAWasPressed = framesCounter;
            }
            framesAIsPressed++;
            hasABeenPressed = true;
        } else {
            framesAIsPressed = 0;
        }
        // S
        if ((GameApp.isKeyPressed(Input.Keys.S) || GameApp.isKeyPressed(Input.Keys.DOWN))
                && playerTileY > 0) {
            if (framesSIsPressed % minTimeBetweenMovement == 0
                    && (framesWhenSWAasPressed + GameApp.getFramesPerSecond() / 3 <= framesCounter || !hasSBeenPressed)) {
                playerY -= pixelPerGridTile;
                framesWhenSWAasPressed = framesCounter;
            }
            framesSIsPressed++;
            hasSBeenPressed = true;
        } else {
            framesSIsPressed = 0;
        }
        // D
        if ((GameApp.isKeyPressed(Input.Keys.D) || GameApp.isKeyPressed(Input.Keys.RIGHT))
                && playerTileX < (getWorldWidth() / pixelPerGridTile - 1)) {
            if (framesDIsPressed % minTimeBetweenMovement == 0
                    && (framesWhenDWAasPressed + GameApp.getFramesPerSecond() / 3 <= framesCounter || !hasDBeenPressed)) {
                playerX += pixelPerGridTile;
                framesWhenDWAasPressed = framesCounter;
            }
            framesDIsPressed++;
            hasDBeenPressed = true;
        } else {
            framesDIsPressed = 0;
        }
        // Mouse left click
        if (GameApp.isButtonJustPressed(Input.Buttons.LEFT)) {
            if (framesMouseIsPressed % minTimeBetweenMovement == 0
                    && (framesWhenMouseWasPressed + minTimeBetweenMovement <= framesCounter || !hasMouseBeenPressed)) {

                int mouseTileX = (int) (getMouseX() / pixelPerGridTile);
                int mouseTileY = (int) (getMouseY() / pixelPerGridTile);

                // Alleen bewegen als muis naast of gelijk aan speler staat
                if (mouseTileX >= playerTileX - 1 && mouseTileX <= playerTileX + 1 && mouseTileY == playerTileY) {
                    playerX = mouseTileX * pixelPerGridTile;
                    playerY = mouseTileY * pixelPerGridTile;
                } else if (mouseTileY >= playerTileY - 1 && mouseTileY <= playerTileY + 1 && mouseTileX == playerTileX) {
                    playerX = mouseTileX * pixelPerGridTile;
                    playerY = mouseTileY * pixelPerGridTile;
                }

                framesWhenMouseWasPressed = framesCounter;
            }
            framesMouseIsPressed++;
            hasMouseBeenPressed = true;
        } else {
            framesMouseIsPressed = 0;
        }
    }
}