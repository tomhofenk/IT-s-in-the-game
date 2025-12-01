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
    int playerTileX = 0;
    int playerTileY = 0;
    int framesWIsPressed = 0;
    int framesAIsPressed = 0;
    int framesSIsPressed = 0;
    int framesDIsPressed = 0;

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

        playerTileX = (int)playerX/pixelPerGridTile;
        playerTileY = (int)playerY/pixelPerGridTile;

        // Movement character Mouse
        if (GameApp.isButtonJustPressed(Input.Buttons.LEFT)) {
            if ((int)getMouseX()/pixelPerGridTile >= playerTileX-1 && (int)getMouseX()/pixelPerGridTile <= playerTileX+1 && (int)getMouseY()/pixelPerGridTile == playerTileY) {
                playerX = ((int)getMouseX()/pixelPerGridTile)*pixelPerGridTile;
                playerY = ((int)getMouseY()/pixelPerGridTile)*pixelPerGridTile;
            } else if ((int)getMouseY()/pixelPerGridTile >= playerTileY-1 && (int)getMouseY()/pixelPerGridTile <= playerTileY+1 && (int)getMouseX()/pixelPerGridTile == playerTileX) {
                playerX = ((int)getMouseX()/pixelPerGridTile)*pixelPerGridTile;
                playerY = ((int)getMouseY()/pixelPerGridTile)*pixelPerGridTile;
            }

        }
        // Movement character WASD and Arrows Grid based
        // per 30 frames dat je een knop vast hebt verplaats je (IPV elke keer opnieuw moeten klikken)
        // Laatste argument is om te zorgen dat je niet buiten de map kan
        if ((GameApp.isKeyPressed(Input.Keys.W) || GameApp.isKeyPressed(Input.Keys.UP)) && playerTileY < (getWorldHeight()/pixelPerGridTile-1)) {
            if (framesWIsPressed % 30 == 0) {
                playerY += pixelPerGridTile;
            }
            framesWIsPressed++;
        } else {
            framesWIsPressed = 0;
        }
        if ((GameApp.isKeyPressed(Input.Keys.A) || GameApp.isKeyPressed(Input.Keys.LEFT)) &&  playerTileX > 0) {
            if (framesAIsPressed % 30 == 0) {
                playerX -= pixelPerGridTile;
            }
            framesAIsPressed++;
        } else {
            framesAIsPressed = 0;
        }
        if ((GameApp.isKeyPressed(Input.Keys.S) || GameApp.isKeyPressed(Input.Keys.DOWN)) &&  playerTileY > 0) {
            if (framesSIsPressed % 30 == 0) {
                playerY -= pixelPerGridTile;
            }
            framesSIsPressed++;
        } else {
            framesSIsPressed = 0;
        }
        if ((GameApp.isKeyPressed(Input.Keys.D) ||  GameApp.isKeyPressed(Input.Keys.RIGHT)) && playerTileX < (getWorldHeight()/pixelPerGridTile-1)) {
            if (framesDIsPressed % 30 == 0) {
                playerX += pixelPerGridTile;
            }
            framesDIsPressed++;
        } else {
            framesDIsPressed = 0;
        }


        // Quit to mainmenu
        if (GameApp.isKeyPressed(Input.Keys.ESCAPE)) {
            GameApp.switchScreen("MainMenuScreen");
        }

        // Print X and Y (For Testing)
        if  (GameApp.isKeyJustPressed(Input.Keys.P)) {
            System.out.println(playerTileX + " " +  playerTileY);
        }

        // When you have moved the player, let the camera know (so it stays in the center of the screen).
        setCameraTarget(playerX, playerY);

        // ALWAYS CALL super.render(delta) AFTERWARDS!!!
        // This applies the camera settings to the shape renderer and sprite batch.
        super.render(delta);

        // Now render all your objects
        GameApp.clearScreen();

        renderGridTiles(pixelPerGridTile);

        // World rendering
        GameApp.startShapeRenderingFilled();

        GameApp.endShapeRendering();
        GameApp.startSpriteRendering();
        GameApp.drawTexture("CharacterTexture", playerX, playerY);
        GameApp.endSpriteRendering();

        // HUD rendering
        switchToHudRendering();
        GameApp.startShapeRenderingFilled();
        GameApp.drawRect(10, 10, 30, 5, Color.WHITE);
        GameApp.endShapeRendering();
    }

    @Override
    public void hide() {

    }

    public void renderGridTiles (int pixelsPerGridTile) {
        for  (int y = 0; y < getWorldHeight()/pixelsPerGridTile; y++) {
            for  (int x = 0; x < getWorldWidth()/pixelsPerGridTile; x++) {
                switchToWorldRendering();
                GameApp.startShapeRenderingOutlined();
                GameApp.setLineWidth(1);
                // Draw each tile without filling so only the border
                GameApp.drawRect((x*pixelsPerGridTile), (y*pixelsPerGridTile), pixelsPerGridTile, pixelsPerGridTile, "stone-500");
                GameApp.endShapeRendering();
            }
        }
        // Draw the white 5 tiles around the player
        switchToWorldRendering();
        GameApp.startShapeRenderingOutlined();
        GameApp.drawRect(playerTileX*pixelsPerGridTile, playerTileY*pixelsPerGridTile, pixelsPerGridTile, pixelsPerGridTile, "stone-50");
        GameApp.drawRect((playerTileX-1)*pixelsPerGridTile, playerTileY*pixelsPerGridTile, pixelsPerGridTile, pixelsPerGridTile, "stone-50");
        GameApp.drawRect((playerTileX+1)*pixelsPerGridTile, playerTileY*pixelsPerGridTile, pixelsPerGridTile, pixelsPerGridTile, "stone-50");
        GameApp.drawRect(playerTileX*pixelsPerGridTile, (playerTileY-1)*pixelsPerGridTile, pixelsPerGridTile, pixelsPerGridTile, "stone-50");
        GameApp.drawRect(playerTileX*pixelsPerGridTile, (playerTileY+1)*pixelsPerGridTile, pixelsPerGridTile, pixelsPerGridTile, "stone-50");
        GameApp.endShapeRendering();
    }

}