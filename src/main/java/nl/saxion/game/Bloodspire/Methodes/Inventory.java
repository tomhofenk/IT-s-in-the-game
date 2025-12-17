package nl.saxion.game.Bloodspire.Methodes;

import nl.saxion.game.Bloodspire.Classes.Item;
import nl.saxion.game.Bloodspire.Classes.Player;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Inventory {
    ArrayList<Item> itemsInInventory = new ArrayList<>();
    ArrayList<String> itemTypes = new ArrayList<>();
    ArrayList<Item> itemList = new ArrayList<>();
    ArrayList<Item> equipped = new ArrayList<>();
    public Player mainPlayer = new Player();


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
                        int hitpointsValue = Integer.parseInt(parts[3].trim());
                        int defenseValue = Integer.parseInt(parts[4].trim());
                        int damageValue = Integer.parseInt(parts[5].trim());
                        int speedPenalty = Integer.parseInt(parts[6].trim());
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
                        Item currentItem = itemList.get(i+183);
                        equipped.add(i,currentItem);
                        System.out.println("Item equipped: " + currentItem);
                        changeStats(currentItem);
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
        System.out.println("Item toegevoegd aan de inventory: " + this.itemList.get(itemID));
    }

    public void removeItems(int itemLocation){
        try {
            int itemID = itemsInInventory.get(itemLocation).itemID;
            itemsInInventory.remove(itemLocation);
            System.out.println("Item verwijderd van de inventory: " + itemList.get(itemID));
        } catch (Exception e){
            System.out.println("Item zit niet in je inventory!!");
        }

    }

    //Controleren of het item wel in de inventory zit voordat je hem kan equippen
    public boolean checkIfInInventory(int itemID) {
        System.out.println("Checken of de speler het item heeft");
        if (!itemsInInventory.isEmpty()) {
            for (Item tempItem : itemsInInventory){
                if (tempItem.itemID == itemID){
                    System.out.println("Speler heeft het item in zijn inventory!");
                    return true;
                }
            }
        } else {
            System.out.println("Inventory is leeg");
        }
        System.out.println("Speler heeft het item niet!");
        return false;
    }

    public void equipItem(int itemID){
        Item currentItem = itemList.get(itemID);
        int equipSlot = itemTypes.indexOf(currentItem.itemType);

        if (checkIfInInventory(itemID)) {
            //Stopt het huidige equipped item terug in de inventory
            addToInventory(equipped.get(equipSlot).itemID);
            //equipped het nieuwe item en haalt het uit de inventory
            equipped.add(equipSlot, currentItem);
            System.out.println("Item equipped: " + currentItem);
            changeStats(currentItem);
            removeItems(itemsInInventory.indexOf(currentItem));
        } else {
            System.out.println("Je kan een item niet equippen als je hem niet hebt!");
        }
    }

    public void changeStats(Item item) {
        mainPlayer.setHitpoints(mainPlayer.getHitpoints()+item.hitpointsValue);
        mainPlayer.setAttackDamage(mainPlayer.getAttackDamage()+item.damageValue);
        mainPlayer.setDefense(mainPlayer.getDefense()+item.defenseValue);
        mainPlayer.setAttackSpeed(mainPlayer.getAttackSpeed()+item.speedPenalty);
        System.out.println("Nieuwe speler stats: \n" + mainPlayer);
    }


}
