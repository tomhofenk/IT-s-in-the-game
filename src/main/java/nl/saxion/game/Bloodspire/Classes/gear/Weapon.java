package nl.saxion.game.Bloodspire.Classes.gear;

public class Weapon {
    String itemName = "";
    String rarity = "";
    double hitpointsValue = 0;
    double defenseValue = 0;
    double speedPenalty = 0;
    int itemID = 500;


    @Override //text weergave voor items bekijken
    public String toString() {
        return itemName + ", " + rarity + " (Hitpoints: " + hitpointsValue + ", Defense: " +
                defenseValue + ", Speed impact: " + speedPenalty + ", item ID: " + itemID + ")";
    }
}
