package nl.saxion.game.Bloodspire.Classes;

import java.util.ArrayList;

public class Item {
    String itemName;
    String itemType;
    String rarity;
    double hitpointsValue;
    double defenseValue;
    double speedPenalty;
    int itemID;

//constructor voor item
    public Item (String itemName, String itemType, String rarity, double hitpointsValue, double defenseValue, double speedPenalty, int itemID) {
        this.itemName = itemName;
        this.itemType = itemType;
        this.rarity = rarity;
        this.hitpointsValue = hitpointsValue;
        this.defenseValue = defenseValue;
        this.speedPenalty = speedPenalty;
        this.itemID = itemID;
    }

    public Item loadItems(){
        ArrayList<Item> itemList = new ArrayList<>();
        ArrayList<String> itemTypes = new ArrayList<>();




        return null;
    }


    @Override //text weergave voor items bekijken
    public String toString() {
        return itemName + ", " + rarity + "(Item type: " + itemType + ", Hitpoints: " + hitpointsValue +
                ", Defense: " + defenseValue + ", Speed impact: " + speedPenalty + ", item ID: " + itemID + ")";
    }
}
