package nl.saxion.game.Bloodspire.Classes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
            } catch (Exception e) {

            }
        } else {
            System.out.println("Lijst was al ingeladen!");
        }
    }

    //Inventory weergeven in de console
    public void showInventory() {
        if (itemsInInventory.isEmpty()) {
            System.out.println("Inventory empty");
        } else {
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
        System.out.println("Player doesnt have the item!");
        return false;
    }

    //om snel te kunnen testen tot waar code werkt
    public void test(){
        System.out.println("Test");
    }

    //TODO equipItem geen error laten geven
    public void equipItem(int itemID){
        Item currentItem = itemList.get(itemID);
        for (int i = 0; i <= 6; i++){
            try {
                if (equipped.get(i).itemType.equals(itemTypes.get(i))) {
                    addToInventory(equipped.get(i).itemID);
                    equipped.add(i, itemList.get(itemID));
                    System.out.println("Item equipped: " + this.equipped.get(i));
                } else {
                    System.out.println("No item currently equipped in this slot");
                }
            } catch (Exception e){
                System.out.println("BOEM");
                e.printStackTrace();
            }
            if (Objects.equals(currentItem.itemType, itemTypes.get(i))) {
                equipped.add(i,currentItem);
                System.out.println("Item equipped: " + equipped.get(i));
            }
        }
    }
}
