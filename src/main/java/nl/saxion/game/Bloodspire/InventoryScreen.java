package nl.saxion.game.Bloodspire;

import com.badlogic.gdx.Input;
import nl.saxion.game.Bloodspire.Classes.Inventory;
import nl.saxion.game.Bloodspire.Classes.Item;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;


public class InventoryScreen extends ScalableGameScreen {
    Inventory inventory = new Inventory();



    public InventoryScreen() {
        super(10 , 5);
    }

    @Override
    public void show() {

    }
    public void render(float delta) {
        //scherm leegmaken en (als dat nog niet eerder is gedaan) items inladen.
        GameApp.clearScreen();
        inventory.loadItems();


        //terug naar main menu
        if (GameApp.isKeyPressed(Input.Keys.ESCAPE)) {
            GameApp.switchScreen("MainMenuScreen");
        }


        //terugkeren naar het speelscherm
        if (GameApp.isKeyJustPressed(Input.Keys.I)) {
            GameApp.switchScreen("MyLevelScreen");
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