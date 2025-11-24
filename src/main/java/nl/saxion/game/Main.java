package nl.saxion.game;

import nl.saxion.game.Bloodspire.BloodspireScreen;
import nl.saxion.game.Bloodspire.MainMenuScreen;
import nl.saxion.gameapp.GameApp;

public class Main {
    public static void main(String[] args) {
        // Add screens
        GameApp.addScreen("MainMenuScreen", new MainMenuScreen());
        GameApp.addScreen("BloodspireScreen", new BloodspireScreen());

        // Start game loop and show main menu screen
        GameApp.start("Bloodspire", 800, 450, 60, false, "MainMenuScreen");
    }
}
