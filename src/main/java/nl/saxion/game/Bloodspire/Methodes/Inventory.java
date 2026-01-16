package nl.saxion.game.Bloodspire.Methodes;

import nl.saxion.game.Bloodspire.Classes.Item;
import nl.saxion.game.Bloodspire.Classes.Player;
import nl.saxion.game.Bloodspire.InventoryScreen;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Inventory {
    public static ArrayList<Item> itemsInInventory = new ArrayList<>();
    ArrayList<String> itemTypes = new ArrayList<>();
    ArrayList<Item> itemList = new ArrayList<>();
    ArrayList<Item> equipped = new ArrayList<>();
    public Player mainPlayer = new Player();
    ArrayList<String> rarity = new ArrayList<>();


    //Csv-bestand met alle items inladen en in een arraylist plaatsen
    public void loadItems() {
        //als de itemlist niet leeg is wordt de csv niet ingeladen om performance te verbeteren
        if (itemList.isEmpty()) {
            try {
                rarity.add("Legendary");
                rarity.add("Epic");
                rarity.add("Rare");
                rarity.add("Uncommon");
                rarity.add("Common");
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
                        changeStats(itemList.get(190), currentItem);
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
            changeStats(equipped.get(equipSlot), currentItem);
            equipped.remove(equipSlot);
            equipped.add(equipSlot, currentItem);
            System.out.println("Item equipped: " + currentItem);
            removeItems(itemsInInventory.indexOf(currentItem));
        } else {
            System.out.println("Je kan een item niet equippen als je hem niet hebt!");
        }
    }

    public void changeStats(Item oldItem, Item newItem) {
        //Stats verwijderen van oude gear
        mainPlayer.setHitpoints(mainPlayer.getHitpoints() - oldItem.hitpointsValue);
        mainPlayer.setAttackDamage(mainPlayer.getAttackDamage() - oldItem.damageValue);
        mainPlayer.setDefense(mainPlayer.getDefense() - oldItem.defenseValue);
        mainPlayer.setAttackSpeed(mainPlayer.getAttackSpeed() - oldItem.speedPenalty);

        //Stats toevoegen van nieuwe gear
        mainPlayer.setHitpoints(mainPlayer.getHitpoints() + newItem.hitpointsValue);
        mainPlayer.setAttackDamage(mainPlayer.getAttackDamage() + newItem.damageValue);
        mainPlayer.setDefense(mainPlayer.getDefense() + newItem.defenseValue);
        mainPlayer.setAttackSpeed(mainPlayer.getAttackSpeed() + newItem.speedPenalty);

        System.out.println("Nieuwe speler stats: \n" + mainPlayer);
    }

    public void giveRandomItem(int xpLevelEnemy){
        ArrayList<Item> tempList = new ArrayList<>();
        int chancePercentage = 1 + (int)(Math.random() * ((100-1) + 1));
        int location = getLocation(xpLevelEnemy, chancePercentage);

        for (Item tempItem : itemList) {
            if (tempItem.rarity.equalsIgnoreCase(rarity.get(location))){
                tempList.add(tempItem);
            }
        }
        int randomInt = 1 + (int)(Math.random() * ((tempList.size() - 1) + 1));
        System.out.println(tempList.size());
        System.out.println(randomInt);
        addToInventory(tempList.get(randomInt-1).itemID);
        tempList.clear();
    }

    private static int getLocation(int xpLevelEnemy, int chancePercentage) {
        int legendary = 100 - xpLevelEnemy;
        int epic = legendary - xpLevelEnemy *2;
        int rare = epic - xpLevelEnemy *3;
        int uncommon = rare - (rare/3);
        int location;

        if (chancePercentage > legendary){
            location=0;
        } else if (chancePercentage > epic){
            location=1;
        } else if (chancePercentage > rare) {
            location=2;
        } else if (chancePercentage > uncommon) {
            location=3;
        } else {
            location=4;
        }
        return location;
    }

    public void purge() {
        itemList.clear();
        itemTypes.clear();
        itemsInInventory.clear();
        equipped.clear();
    }

    public ArrayList<Item> getEquipped() {
        return equipped;
    }

    public ArrayList<Item> getItemsInInventory() {
        return itemsInInventory;
    }
}
