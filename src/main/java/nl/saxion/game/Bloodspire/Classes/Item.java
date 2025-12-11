package nl.saxion.game.Bloodspire.Classes;

public class Item {
    String itemName;
    String itemType;
    String rarity;
    int hitpointsValue;
    int defenseValue;
    int damageValue;
    int speedPenalty;
    int itemID;

    public Item(String itemName, String itemType, String rarity, int hitpointsValue, int defenseValue, int damageValue, int speedPenalty, int itemID) {
        this.itemName = itemName;
        this.itemType = itemType;
        this.rarity = rarity;
        this.hitpointsValue = hitpointsValue;
        this.defenseValue = defenseValue;
        this.damageValue = damageValue;
        this.speedPenalty = speedPenalty;
        this.itemID = itemID;
    }

    @Override //text weergave voor items bekijken
    public String toString() {
        return itemName + ", " + rarity + "(Item type: " + itemType + ", Hitpoints: " + hitpointsValue +
                ", Defense: " + defenseValue + "Damage value: " + damageValue + ", Speed impact: " + speedPenalty + ", item ID: " + itemID + ")";
    }
}
