package nl.saxion.game.Bloodspire.Classes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Objects;

public class Inventory {
    ArrayList<Item> itemsInInventory = new ArrayList<>();
    ArrayList<String> itemTypes = new ArrayList<>();
    ArrayList<Item> itemList = new ArrayList<>();
    ArrayList<Item> equipped = new ArrayList<>();


    //Csv-bestand met alle items inladen en in een arraylist plaatsen
    public void loadItems() {
        //als de itemlist niet leeg is wordt de csv niet ingeladen om performance te verbeteren
        if (itemList.isEmpty()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader("src/main/java/nl/saxion/game/Bloodspire/csv/GearItems.csv"));
                String line;

                boolean firstLine = true;

                while ((line = reader.readLine()) != null) {
                    if (firstLine) {
                        firstLine = false;
                        continue;
                    }
                    String[] parts = line.split(",");


                    if (parts.length >= 8) {
                        String itemName = parts[0].trim();
                        String itemType = parts[1].trim();
                        String rarity = parts[2].trim();
                        double hitpointsValue = Double.parseDouble(parts[3].trim());
                        double defenseValue = Double.parseDouble(parts[4].trim());
                        double damageValue = Double.parseDouble(parts[5].trim());
                        double speedPenalty = Double.parseDouble(parts[6].trim());
                        int itemID = Integer.parseInt(parts[7].trim());

                        // Nieuw Item aanmaken en toevoegen aan lijst
                        Item item = new Item(itemName, itemType, rarity,
                                hitpointsValue, defenseValue,
                                damageValue, speedPenalty, itemID);
                        itemList.add(item);

                        // Unieke types opslaan voor gebruik in het equippen van gear
                        if (!itemTypes.contains(itemType)) {
                            itemTypes.add(itemType);
                        }

                    }
                }

                reader.close();
                System.out.println("Lijst succesvol ingeladen!");
                //starter gear equippen als er nog niks equipped is.
                if (equipped.isEmpty()) {
                    for (int i =0;i<=6;i++) {
                        equipped.add(i,itemList.get(i+183));
                    }
                    System.out.println("Starting gear equipped");
                }
            } catch (Exception ignored) {}
        } else {
            System.out.println("Lijst was al ingeladen!");
        }
    }

    //Inventory weergeven in de console
    public void showInventory() {
        if (itemsInInventory.isEmpty()) {
            System.out.println("Inventory empty");
        } else {
            System.out.println("Dit zit er in de inventory:");
            for (Item item : itemsInInventory){
                System.out.println(item);
            }
        }
    }

    public void addToInventory(int itemID){
        itemsInInventory.add(itemList.get(itemID));
        System.out.println("Item added to inventory: " + this.itemList.get(itemID));
    }

    public void removeItems(int itemLocation){
        int itemID = itemsInInventory.get(itemLocation).itemID;
        itemsInInventory.remove(itemLocation);
        System.out.println("Item removed from inventory: " + itemList.get(itemID));
    }

    //Controleren of het item wel in de inventory zit voordat je hem kan equippen
    public boolean checkIfInInventory(int itemID) {
        System.out.println("Checking if player has the item");
        if (!itemsInInventory.isEmpty()) {
            for (Item tempItem : itemsInInventory){
                if (tempItem.itemID == itemID){
                    System.out.println("Player has item in inventory!");
                    return true;
                }
            }
        } else {
            System.out.println("Inventory is empty");
        }
        System.out.println("Player doesn't have the item!");
        return false;
    }
    
    public void equipItem(int itemID){
        //initializing the item and getting the right spot for the array
        Item currentItem = itemList.get(itemID);
        int equipslot = itemTypes.indexOf(currentItem.itemType);

        if (checkIfInInventory(itemID)) {
            //putting the currently equipped item in the inventory
            addToInventory(equipped.get(equipslot).itemID);
            //equipping the new item and removing it from the inventory
            equipped.add(equipslot, currentItem);
            System.out.println("Item equipped: " + currentItem);
            removeItems(itemsInInventory.indexOf(currentItem));
        } else {
            System.out.println("Cannot equip an item if you dont have it!");
        }
    }
}
