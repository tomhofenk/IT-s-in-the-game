package nl.saxion.game.Bloodspire;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.CameraControlledGameScreen;

public class MyLevelScreen extends CameraControlledGameScreen {
    private float playerX, playerY;
    float speed = 20;

    public MyLevelScreen() {
        // Define camera viewport (visible area) and world size
        // Example: viewport 16x9 is, world has size 100x50
        super(160, 90, 10000, 5000);
    }

    @Override
    public void show() {
        enableHUD(160, 90);
        // Initialize your objects, e.g., player starting position
        playerX = getWorldWidth()/2;
        playerY = getWorldHeight()/2;

        // Start camera centered on the player
        setCameraTargetInstantly(playerX, playerY);
        GameApp.addTexture("CharacterTexture", "textures/DungeonCharacterpng.png");
    }

    @Override
    public void render(float delta) {
        // Handle your game logic and input

        // Movement character WASD
        if (GameApp.isKeyPressed(Input.Keys.W)) {
            playerY += speed * delta;
        }
        if (GameApp.isKeyPressed(Input.Keys.A)) {
            playerX -= speed * delta;
        }
        if (GameApp.isKeyPressed(Input.Keys.S)) {
            playerY -= speed * delta;
        }
        if (GameApp.isKeyPressed(Input.Keys.D)) {
            playerX += speed * delta;
        }

        // Movement character Mouse
        if (GameApp.isButtonJustPressed(Input.Buttons.LEFT)) {
            playerX = getMouseX();
            playerY = getMouseY();
        }

        // Dash
        if (GameApp.isKeyJustPressed(Input.Keys.SPACE)) {
            if (GameApp.isKeyPressed(Input.Keys.W)) {
                playerY = playerY + 5;
            }
            if (GameApp.isKeyPressed(Input.Keys.A)) {
                playerX = playerX - 5;
            }
            if (GameApp.isKeyPressed(Input.Keys.S)) {
                playerY = playerY - 5;
            }
            if (GameApp.isKeyPressed(Input.Keys.D)) {
                playerX = playerX + 5;
            }
        }

        // Quit to mainmenu
        if (GameApp.isKeyPressed(Input.Keys.ESCAPE)) {
            GameApp.switchScreen("MainMenuScreen");
        }

        // When you have moved the player, let the camera know (so it stays in the center of the screen).
        setCameraTarget(playerX, playerY);

        // ALWAYS CALL super.render(delta) AFTERWARDS!!!
        // This applies the camera settings to the shape renderer and sprite batch.
        super.render(delta);

        // Now render all your objects
        GameApp.clearScreen();
        // World rendering
        GameApp.startShapeRenderingFilled();
        GameApp.drawRectCentered(getWorldWidth()/2, getWorldHeight()/2, 200, 200, "yellow-500");
        GameApp.drawRectCentered(getWorldWidth()/2, getWorldHeight()/2, 100, 100, "blue-500");
        GameApp.endShapeRendering();
        GameApp.startSpriteRendering();
        GameApp.drawTextureCentered("CharacterTexture", playerX, playerY);
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
}