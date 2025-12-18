package nl.saxion.game.Bloodspire;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import nl.saxion.game.Bloodspire.Classes.Item;
import nl.saxion.game.Bloodspire.Methodes.Inventory;
import nl.saxion.game.Bloodspire.Classes.Player;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;


public class InventoryScreen extends ScalableGameScreen {
    public static Inventory inventory = new Inventory();
    public Player mainPlayer = new Player();



    public InventoryScreen() {
        super(2560, 1600);
    }

    @Override
    public void show() {

        GameApp.addFont("Basic","fonts/basic.ttf",getWorldWidth()/75);
    }

    public void render(float delta) {
        super.render(delta);
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
            System.out.println(mainPlayer.toString());
        }

        //testen (Stats max)
        if (GameApp.isKeyJustPressed(Input.Keys.M)) {
            inventory.addToInventory(0);
            inventory.equipItem(0);
        }

        // ALWAYS CALL super.render(delta) AFTERWARDS!!!
        // This applies the camera settings to the shape renderer and sprite batch.
        renderLayout();
        showItems();
    }
    


    @Override
    public void hide() {

    }


    private void renderLayout() {
        GameApp.startShapeRenderingFilled();
        GameApp.drawRect(0,0,1270,1600, Color.GRAY);
        GameApp.drawRect(1290,0,1250,1600, Color.GRAY);
        GameApp.endShapeRendering();

        GameApp.startSpriteRendering();
        GameApp.drawText("Basic", "Test", 1280,800,Color.GREEN);
        GameApp.endSpriteRendering();
    }

    private void showItems(){
        int yInventoryPlacement = 1570;
        int i = 1;
        GameApp.startSpriteRendering();
        //Inventory kant
        for (Item currentItem : inventory.getItemsInInventory()) {
            GameApp.drawText("Basic", i + ": " + currentItem.toString(), 0, yInventoryPlacement, Color.WHITE);
            yInventoryPlacement -= 40;
            i++;
        }
        //Equipment kant
        for (Item currentItem  : inventory.getEquipped()) {
            GameApp.drawText("Basic", i + ": " + currentItem.toString(), 1290, yInventoryPlacement, Color.WHITE);
            yInventoryPlacement -= 40;
            i++;
        }
        GameApp.endSpriteRendering();
    }


}