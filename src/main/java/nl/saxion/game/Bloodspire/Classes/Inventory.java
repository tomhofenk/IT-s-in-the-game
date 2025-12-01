package nl.saxion.game.Bloodspire.Classes;

import java.util.ArrayList;

public class Inventory {
    ArrayList<Item> itemsInInventory;
    ArrayList<String> itemTypes;
    ArrayList<Item> itemList;


    //Csv-bestand met alle items inladen en in een arraylist plaatsen
    public void loadItems(){
        Item currentItem = new Item();


        itemList.add(currentItem);

        //lijst aanmaken met de verschillende item types, voor gebruik bij het equippen van gear
        if (!itemTypes.contains(currentItem.itemType)) {
            itemTypes.add(currentItem.itemType);
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
