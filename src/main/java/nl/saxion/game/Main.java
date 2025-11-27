package nl.saxion.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import nl.saxion.game.Bloodspire.*;
import nl.saxion.gameapp.GameApp;

public class Main {
    public static void main(String[] args) {
        // Add screens
        GameApp.addScreen("MainMenuScreen", new MainMenuScreen());
        GameApp.addScreen("BloodspireScreen", new BloodspireScreen());
        GameApp.addScreen("MyLevelScreen", new MyLevelScreen());
        GameApp.addScreen("TestScreen", new TestScreen());
        GameApp.addScreen("TestSreen2", new TestSreen2());

        // Start game loop and show main menu screen
        GameApp.start("Bloodspire", 800, 450, 60, false, "MainMenuScreen");
    }
}
