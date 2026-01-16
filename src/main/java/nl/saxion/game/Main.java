package nl.saxion.game;

import nl.saxion.game.Bloodspire.*;
import nl.saxion.game.Bloodspire.Classes.Enemy;
import nl.saxion.game.Bloodspire.Classes.EnemyData;
import nl.saxion.game.Bloodspire.Classes.Tile;
import nl.saxion.game.Bloodspire.Methodes.CsvLoader;
import nl.saxion.game.Bloodspire.Methodes.LevelVars;
import nl.saxion.game.Bloodspire.Methodes.MapData;
import nl.saxion.gameapp.GameApp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Main {
//    public MapData md = new MapData();


    public static void main(String[] args) {

        setMapAndEnemyData();


        //EnemyData.setId(1, loadEnemyCsv("src/main/java/nl/saxion/game/Bloodspire/csv/Enemy.csv"));
        // Variabels for size of world and viewport (for now only in MyLevelScreen)
        // Amount ofd pixels per tile
        int pixelsPerGridTile = 64; // 64 Because sprite is 64x64
        // 16 Wide * 9 High viewport (16*9)*64
        int viewportWidth = 16 *  pixelsPerGridTile;
        int viewportHeight = 9 *  pixelsPerGridTile;
        // 64 Wide * 64 High viewport (64*64)*64
        int worldWidth = 64 *  pixelsPerGridTile;
        int worldHeight = 64 *  pixelsPerGridTile;

        // Add screens
        GameApp.addScreen("MainMenuScreen", new MainMenuScreen());
        GameApp.addScreen("DrawMapScreen", new DrawMapScreen());
        GameApp.addScreen("MyLevelScreen", new MyLevelScreen(viewportWidth, viewportHeight, worldWidth, worldHeight));
        GameApp.addScreen("BattleScreen", new BattleScreen());
        GameApp.addScreen("InventoryScreen", new InventoryScreen());
        GameApp.addScreen("DooDScreen", new DooDScreen());

        // Start game loop and show main menu screen
        GameApp.start("Bloodspire", 2560, 1600, 60, true, "MainMenuScreen");
    }

    public static void setMapAndEnemyData() {
        EnemyData.EnemyArraylist = CsvLoader.enemyLoadCsv("src/main/java/nl/saxion/game/Bloodspire/csv/Enemy.csv");

        MapData.setLevel(1, CsvLoader.levelLoadCsv("src/main/java/nl/saxion/game/Bloodspire/csv/Level1Tile.csv"));
        LevelVars.setLevelStartX(1, 15);
        LevelVars.setLevelStartY(1, 14);
        MapData.setLevel(2, CsvLoader.levelLoadCsv("src/main/java/nl/saxion/game/Bloodspire/csv/Level2Tile.csv"));
        LevelVars.setLevelStartX(2, 14);
        LevelVars.setLevelStartY(2, 25);
        MapData.setLevel(3, CsvLoader.levelLoadCsv("src/main/java/nl/saxion/game/Bloodspire/csv/Level3Tile.csv"));
        LevelVars.setLevelStartX(3, 22);
        LevelVars.setLevelStartY(3, 22);
        MapData.setLevel(4, CsvLoader.levelLoadCsv("src/main/java/nl/saxion/game/Bloodspire/csv/Level4Tile.csv"));
        LevelVars.setLevelStartX(4, 21);
        LevelVars.setLevelStartY(4, 24);
        MapData.setLevel(5, CsvLoader.levelLoadCsv("src/main/java/nl/saxion/game/Bloodspire/csv/Level5Tile.csv"));
        LevelVars.setLevelStartX(5, 15);
        LevelVars.setLevelStartY(5, 14);

    }
}
