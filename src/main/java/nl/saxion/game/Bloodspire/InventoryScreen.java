package nl.saxion.game.Bloodspire;

import com.badlogic.gdx.Input;
import nl.saxion.game.Bloodspire.Classes.Inventory;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;


public class InventoryScreen extends ScalableGameScreen {
    Inventory inventory = new Inventory();



    public InventoryScreen() {
        super(10 , 5);
    }

    @Override
    public void show() {
        //items inladen als dit nog niet gedaan is.
        inventory.loadItems();
    }
    public void render(float delta) {
        GameApp.clearScreen();


        //terug naar main menu
        if (GameApp.isKeyPressed(Input.Keys.ESCAPE)) {
            GameApp.switchScreen("MainMenuScreen");
        }


        //terugkeren naar het speelscherm
        if (GameApp.isKeyJustPressed(Input.Keys.I)) {
            GameApp.switchScreen("MyLevelScreen");
        }

        if (GameApp.isKeyJustPressed(Input.Keys.A)) {
            inventory.showInventory();
        }

        if (GameApp.isKeyJustPressed(Input.Keys.S)) {
            inventory.addToInventory(0);
        }

        if (GameApp.isKeyJustPressed(Input.Keys.D)) {
            inventory.removeItems(0);
        }

        if (GameApp.isKeyJustPressed(Input.Keys.F)) {
            inventory.equipItem(0);
        }

        //testen
        if (GameApp.isKeyJustPressed(Input.Keys.K)) {
            inventory.addToInventory(1);
            inventory.checkIfInInventory(1);
            inventory.removeItems(0);
            inventory.showInventory();
        }

        // ALWAYS CALL super.render(delta) AFTERWARDS!!!
        // This applies the camera settings to the shape renderer and sprite batch.
        super.render(delta);

    }
    


    @Override
    public void hide() {

    }
}