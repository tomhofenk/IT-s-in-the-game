package nl.saxion.game.Bloodspire.Classes;

public class Item {
    String itemName;
    String itemType;
    String rarity;
    double hitpointsValue;
    double defenseValue;
    double damageValue;
    double speedPenalty;
    int itemID;

    @Override //text weergave voor items bekijken
    public String toString() {
        return itemName + ", " + rarity + "(Item type: " + itemType + ", Hitpoints: " + hitpointsValue +
                ", Defense: " + defenseValue + "Damage value: " + damageValue + ", Speed impact: " + speedPenalty + ", item ID: " + itemID + ")";
    }
}
