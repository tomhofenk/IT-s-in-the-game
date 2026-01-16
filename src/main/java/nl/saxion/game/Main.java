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

        MapData.setLevel(1, CsvLoader.levelLoadCsv("src/main/java/nl/saxion/game/Bloodspire/csv/Level1Tile.csv"));
        LevelVars.setLevelStartX(1, 15);
        LevelVars.setLevelStartY(1, 14);
        MapData.setLevel(2, CsvLoader.levelLoadCsv("src/main/java/nl/saxion/game/Bloodspire/csv/Level2Tile.csv"));
        LevelVars.setLevelStartX(2, 14);
        LevelVars.setLevelStartY(2, 25);
        MapData.setLevel(3, CsvLoader.levelLoadCsv("src/main/java/nl/saxion/game/Bloodspire/csv/LevelTestTile.csv"));
        LevelVars.setLevelStartX(3, 10);
        LevelVars.setLevelStartY(3, 10);

        EnemyData.EnemyArraylist = CsvLoader.enemyLoadCsv("src/main/java/nl/saxion/game/Bloodspire/csv/Enemy.csv");
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

//    private static ArrayList<Tile> loadLevelCsv(String path) {
//        ArrayList<Tile> tiles = new ArrayList<>();
//
//        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
//            String line;
//            br.readLine(); // Skip de headerregel
//
//            while ((line = br.readLine()) != null) {
//                String[] columns = line.split(",");  // Split de lijn op komma's
//                try {
//                    // Parse de waarden uit de CSV
//                    int tileID = Integer.parseInt(columns[0].trim());
//                    int worldX = Integer.parseInt(columns[1].trim());
//                    int worldY = Integer.parseInt(columns[2].trim());
//                    int gridX = Integer.parseInt(columns[3].trim());
//                    int gridY = Integer.parseInt(columns[4].trim());
//                    boolean hasEnemy = columns[5].trim().equals("true");
//                    String enemyID = columns[6].trim().equals("\\N") ? null : columns[6].trim();
//                    String tileType = columns[7].trim();  // Nieuwe kolom tileType
//                    boolean walkable = columns[8].trim().equals("true");
//
//                    // Maak een Tile object van de CSV-gegevens
//                    Tile tile = new Tile(tileID, worldX, worldY, gridX, gridY, hasEnemy, enemyID, tileType, walkable);
//                    tiles.add(tile);
//                } catch (NumberFormatException e) {
//                    System.err.println("Fout bij het parsen van de CSV-regel: " + line);
//                }
//            }
//
//        } catch (IOException e) {
//            System.err.println("Fout bij het lezen van bestand: " + path);
//            e.printStackTrace();
//        }
//
//        return tiles;
//    }
//
//    private static ArrayList<Enemy> loadEnemyCsv(String path) {
//        ArrayList<Enemy> enemys = new ArrayList<>();
//
//        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
//            String line;
//            br.readLine(); // Skip de headerregel
//
//            while ((line = br.readLine()) != null) {
//                String[] columns = line.split(",");  // Split de lijn op komma's
//                try {
//                    Enemy newEnemy = new Enemy();
//                    // Parse de waarden uit de CSV
//                    newEnemy.enemyID = Integer.parseInt(columns[0].trim());
//                    newEnemy.hitPoints = Integer.parseInt(columns[1].trim());
//                    newEnemy.attackSpeed = Integer.parseInt(columns[2].trim());
//                    newEnemy.attackDamage = Integer.parseInt(columns[3].trim());
//                    newEnemy.defense = Integer.parseInt(columns[4].trim());
//                    newEnemy.experiencePoints = Integer.parseInt(columns[5].trim());
//                    newEnemy.texture = columns[6].trim();  // Nieuwe kolom tileType
//
//                    // Maak een Tile object van de CSV-gegevens
//                    //Enemy enemy = new Enemy(enemyID, hitPoints, attackSpeed, attackDamage, defense, experiencePoints, texture);
//                    enemys.add(newEnemy);
//                } catch (NumberFormatException e) {
//                    System.err.println("Fout bij het parsen van de CSV-regel: " + line);
//                }
//            }
//
//        } catch (IOException e) {
//            System.err.println("Fout bij het lezen van bestand: " + path);
//            e.printStackTrace();
//        }
//
//        return enemys;
//    }
}
