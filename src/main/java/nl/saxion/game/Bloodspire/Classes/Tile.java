package nl.saxion.game.Bloodspire.Classes;

public class Tile {
    // Maak de velden public zodat ze toegankelijk zijn van buitenaf
    public int tileID;
    public int worldX, worldY;
    public int gridX, gridY;
    public boolean hasEnemy;
    public String enemyID;
    public String tileType;

    // Constructor
    public Tile(int tileID, int worldX, int worldY, int gridX, int gridY, boolean hasEnemy, String enemyID, String tileType) {
        this.tileID = tileID;
        this.worldX = worldX;
        this.worldY = worldY;
        this.gridX = gridX;
        this.gridY = gridY;
        this.hasEnemy = hasEnemy;
        this.enemyID = enemyID;
        this.tileType = tileType;

    }

    // Override toString voor debugging
    @Override
    public String toString() {
        return "TileID: " + tileID + ", GridX: " + gridX + ", GridY: " + gridY + ", HasEnemy: " + hasEnemy + ", TileType: " + tileType;
    }

    public boolean isWalkable() {

        // Je CSV gebruikt hoofdletters (Grass, Water, Stone, Dirt)
        switch (tileType) {
            case "Water":
                return false;   // blokkeren
            case "Stone":
                return true;    // mag lopen
            case "Grass":
                return true;
            case "Dirt":
                return true;
            default:
                return true;    // onbekende tile? → gewoon walkable
        }


    }





}
