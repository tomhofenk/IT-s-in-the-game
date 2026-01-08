package nl.saxion.game.Bloodspire.Methodes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LevelVars {

    private static int oldX;
    private static int oldY;
    private static int currentLevel = 1;
    private static int oldLevel = 1;
    private static Map<Integer, Integer> levelStartX = new HashMap<>();
    private static Map<Integer, Integer> levelStartY = new HashMap<>();

    public static int getOldLevel() {
        return oldLevel;
    }

    public static void setOldLevel(int oldLevel) {
        LevelVars.oldLevel = oldLevel;
    }

    public static int getLevelStartY(int level) {
        return levelStartY.get(level);
    }

//    public static void setLevelStartY(Map<Integer, Integer> levelStartY) {
//        LevelVars.levelStartY = levelStartY;
//    }

    public static int getLevelStartX(int level) {
        return levelStartX.get(level);
    }
//
//    public static void setLevelStartX(Map<Integer, Integer> levelStartX) {
//        LevelVars.levelStartX = levelStartX;
//    }

    public static int getCurrentLevel() {
        return currentLevel;
    }

    public static void setCurrentLevel(int currentLevel) {
        LevelVars.currentLevel = currentLevel;
    }

    public static void setLevelStartX(int i, int i1) {
        LevelVars.levelStartX.put(i, i1);
    }

    public static void setLevelStartY(int i, int i1) {
        LevelVars.levelStartY.put(i, i1);
    }


    public int getOldX() {
        return oldX;
    }

    public void setOldX(int oldX) {
        this.oldX = oldX;
    }

    public int getOldY() {
        return oldY;
    }

    public void setOldY(int oldY) {
        this.oldY = oldY;
    }
}
