//package nl.saxion.game.Bloodspire.Methodes;
//
//import nl.saxion.game.Bloodspire.Classes.Tile;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//public class MapData {
//
////    public ArrayList<Tile> Level1MapData = loadCsv("src/main/java/nl/saxion/game/Bloodspire/csv/Level1Tile.csv");
////    public ArrayList<Tile> Level2MapData = new ArrayList<>();
//
//    public static Map<Integer, ArrayList<Tile>> LevelMapData = new HashMap<>();
//    static {
////        LevelMapData.put(1, new ArrayList<Tile>(loadCsv("src/main/java/nl/saxion/game/Bloodspire/csv/Level1Tile.csv")));
//        LevelMapData.put(1, new ArrayList<Tile>());
//        LevelMapData.put(2, new ArrayList<Tile>());
//    }
//    // Niet hier uitlezen maar in de main of mainmenuscreen 1x wanneer het spel start
//
//
//    public static Map<Integer, ArrayList<Tile>> getLevelMapData() {
//        return LevelMapData;
//    }
//
//    public static void setLevelMapData(Map<Integer, ArrayList<Tile>> levelMapData) {
//        LevelMapData = levelMapData;
//    }
//}

package nl.saxion.game.Bloodspire.Methodes;

import nl.saxion.game.Bloodspire.Classes.Tile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapData {

    // Globale statische opslag
    public static Map<Integer, ArrayList<Tile>> levelMapData = new HashMap<>();

    // Haal level op
    public static ArrayList<Tile> getLevel(int level) {
        return levelMapData.get(level);
    }

    // Sla level op
    public static void setLevel(int level, ArrayList<Tile> data) {
        levelMapData.put(level, data);
    }
}
