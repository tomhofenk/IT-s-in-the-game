package nl.saxion.game.Bloodspire.Classes;

import java.util.List;

public class Inventory<T> {
    private List<T> gear;


    public void showInventory() {
        if (gear.isEmpty()) {
            System.out.println("Inventory empty");

        } else {
            for (T equipment : gear){
                System.out.println(equipment);
            }
        }
    }


    public List<T> getGear() {
        return gear;
    }
}
