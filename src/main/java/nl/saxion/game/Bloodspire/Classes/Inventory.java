package nl.saxion.game.Bloodspire.Classes;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Inventory {
    ArrayList<Item> itemsInInventory;
    ArrayList<String> itemTypes;
    ArrayList<Item> itemList;


    //Csv-bestand met alle items inladen en in een arraylist plaatsen
    public void loadItems() {
        //als de itemlist niet leeg is wordt de csv niet ingeladen om performance te verbeteren
        if (itemList.isEmpty()) {

            try {
                BufferedReader reader = new BufferedReader(new FileReader("src/main/java/nl/saxion/game/Bloodspire/csv/GearItems.csv"));
                String line;
                boolean firstLine = true;

                while ((line = reader.readLine()) != null) {

                    // Header overslaan
                    if (firstLine) {
                        firstLine = false;
                        continue;
                    }

                    // CSV-regel splitsen op komma’s
                    String[] parts = line.split(",");

                    // Check op correcte lengte
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

                        // Unieke types opslaan voor gebruik in 
                        if (!itemTypes.contains(itemType)) {
                            itemTypes.add(itemType);
                        }
                    }
                }

                reader.close();

            } catch (Exception e) {
                System.out.println("FOUT bij inlezen CSV items: " + e.getMessage());
            }
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

    public void removeItems(int itemNumber){
        itemsInInventory.remove(itemNumber);
    }
}
