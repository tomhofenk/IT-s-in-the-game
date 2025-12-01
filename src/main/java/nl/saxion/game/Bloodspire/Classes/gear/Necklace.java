package nl.saxion.game.Bloodspire.Classes.gear;

public class Necklace {
    String itemName = "";
    String rarity = "";
    double hitpointsValue = 0;
    double defenseValue = 0;
    double speedPenalty = 0;
    int itemID = 600;

//Constructor voor de Necklace
    public Necklace (String itemName, String rarity, double hitpointsValue, double defenseValue, double speedPenalty, int itemID) {
        this.itemName = itemName;
        this.rarity = rarity;
        this.hitpointsValue = hitpointsValue;
        this.defenseValue = defenseValue;
        this.speedPenalty = speedPenalty;
        this.itemID = itemID;
    }


    @Override //text weergave voor items bekijken
    public String toString() {
        return itemName + ", " + rarity + " (Hitpoints: " + hitpointsValue + ", Defense: " +
                defenseValue + ", Speed impact: " + speedPenalty + ", item ID: " + itemID + ")";
    }
}
