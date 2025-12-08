package nl.saxion.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import nl.saxion.game.Bloodspire.*;
import nl.saxion.gameapp.GameApp;

public class Main {
    public static void main(String[] args) {

        // Variabels for size of world and viewport (for now only in MyLevelScreen)
        // Amount of pixels per tile
        int pixelsPerGridTile = 64; // 64 Because sprite is 64x64
        // 16 Wide * 9 High viewport (16*9)*64
        int viewportWidth = 16 *  pixelsPerGridTile;
        int viewportHeight = 9 *  pixelsPerGridTile;
        // 64 Wide * 64 High viewport (64*64)*64
        int worldWidth = 64 *  pixelsPerGridTile;
        int worldHeight = 64 *  pixelsPerGridTile;

        // Add screens
        GameApp.addScreen("MainMenuScreen", new MainMenuScreen());
        GameApp.addScreen("BloodspireScreen", new BloodspireScreen());
        GameApp.addScreen("MyLevelScreen", new MyLevelScreen(viewportWidth, viewportHeight, worldWidth, worldHeight));
        GameApp.addScreen("TestScreen", new TestScreen());
        GameApp.addScreen("InventoryScreen", new InventoryScreen());

        // Start game loop and show main menu screen
        GameApp.start("Bloodspire", 800, 450, 60, false, "MainMenuScreen");
    }
}
