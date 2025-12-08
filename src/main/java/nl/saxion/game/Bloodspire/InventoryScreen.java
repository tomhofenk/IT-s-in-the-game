package nl.saxion.game.Bloodspire;

import com.badlogic.gdx.Input;
import nl.saxion.game.Bloodspire.Classes.Inventory;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;


public class InventoryScreen extends ScalableGameScreen {
    public static Inventory inventory = new Inventory();



    public InventoryScreen() {
        super(10 , 5);
    }

    @Override
    public void show() {
    }

    public void render(float delta) {
        GameApp.clearScreen();


        //terug naar main menu
        if (GameApp.isKeyJustPressed(Input.Keys.ESCAPE)) {
            GameApp.switchScreen("MyLevelScreen");
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

        if (GameApp.isKeyJustPressed(Input.Keys.G)) {
            System.out.println(MyLevelScreen.mainPlayer);
        }

        //testen (Stats maxen)
        if (GameApp.isKeyJustPressed(Input.Keys.M)) {
            inventory.addToInventory(0);
            inventory.equipItem(0);
        }

        // ALWAYS CALL super.render(delta) AFTERWARDS!!!
        // This applies the camera settings to the shape renderer and sprite batch.
        super.render(delta);

    }
    


    @Override
    public void hide() {

    }
}