package nl.saxion.game.Bloodspire.Methodes;

import nl.saxion.game.Bloodspire.Classes.Enemy;
import nl.saxion.game.Bloodspire.Classes.Tile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

// Niet actief nodig maar voor zekerheid laten staan
public class CsvLoader {

    public static ArrayList<Tile> levelLoadCsv(String path) {
        ArrayList<Tile> tiles = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            br.readLine(); // Skip de headerregel

            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");  // Split de lijn op komma's
                try {
                    // Parse de waarden uit de CSV
                    int tileID = Integer.parseInt(columns[0].trim());
                    int worldX = Integer.parseInt(columns[1].trim());
                    int worldY = Integer.parseInt(columns[2].trim());
                    int gridX = Integer.parseInt(columns[3].trim());
                    int gridY = Integer.parseInt(columns[4].trim());
                    boolean hasEnemy = columns[5].trim().equals("true");
                    String enemyID = columns[6].trim().equals("\\N") ? null : columns[6].trim();
                    String tileType = columns[7].trim();  // Nieuwe kolom tileType
                    boolean walkable = columns[8].trim().equals("true");

                    // Maak een Tile object van de CSV-gegevens
                    Tile tile = new Tile(tileID, worldX, worldY, gridX, gridY, hasEnemy, enemyID, tileType, walkable);
                    tiles.add(tile);
                } catch (NumberFormatException e) {
                    System.err.println("Fout bij het parsen van de CSV-regel: " + line);
                }
            }

        } catch (IOException e) {
            System.err.println("Fout bij het lezen van bestand: " + path);
            e.printStackTrace();
        }

        return tiles;
    }

    public static ArrayList<Enemy> enemyLoadCsv(String path) {
        ArrayList<Enemy> enemys = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            br.readLine(); // Skip de headerregel

            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");  // Split de lijn op komma's
                try {
                    Enemy newEnemy = new Enemy();
                    // Parse de waarden uit de CSV
                    newEnemy.enemyID = Integer.parseInt(columns[0].trim());
                    newEnemy.hitPoints = Integer.parseInt(columns[1].trim());
                    newEnemy.attackSpeed = Integer.parseInt(columns[2].trim());
                    newEnemy.attackDamage = Integer.parseInt(columns[3].trim());
                    newEnemy.defense = Integer.parseInt(columns[4].trim());
                    newEnemy.experiencePoints = Integer.parseInt(columns[5].trim());
                    newEnemy.texture = columns[6].trim();  // Nieuwe kolom tileType

                    // Maak een Tile object van de CSV-gegevens
                    //Enemy enemy = new Enemy(enemyID, hitPoints, attackSpeed, attackDamage, defense, experiencePoints, texture);
                    enemys.add(newEnemy);
                } catch (NumberFormatException e) {
                    System.err.println("Fout bij het parsen van de CSV-regel: " + line);
                }
            }

        } catch (IOException e) {
            System.err.println("Fout bij het lezen van bestand: " + path);
            e.printStackTrace();
        }

        return enemys;
    }
}
