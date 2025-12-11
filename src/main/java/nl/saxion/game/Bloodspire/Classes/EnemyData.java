package nl.saxion.game.Bloodspire.Classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EnemyData {

//    // Globale statische opslag
//    public static Map<Integer, ArrayList<Enemy>> levelEnemyData = new HashMap<>();
//
//    // Haal level op
//    public static ArrayList<Enemy> getId(int Id) {
//        return levelEnemyData.get(Id);
//    }
//
//    // Sla level op
//    public static void setId(int Id, ArrayList<Enemy> data) {
//        levelEnemyData.put(Id, data);
//    }
    public static ArrayList<Enemy> EnemyArraylist = new ArrayList<>();

    public Enemy getEnemyFromID(int id) {
        for (Enemy ce : EnemyArraylist) {
            if (ce.enemyID == id) {
                return ce;
            }
        }
        return null;
    }
}
