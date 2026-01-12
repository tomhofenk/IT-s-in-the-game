package nl.saxion.game.Bloodspire;

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
    int selected = 1;



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

        //Switchen van geselecteerde item
        if (GameApp.isKeyJustPressed(Input.Keys.W) || GameApp.isKeyJustPressed(Input.Keys.UP)) {
            selected--;
            if (selected <= 0){
                selected = inventory.getItemsInInventory().size();
            }
        }
        if (GameApp.isKeyJustPressed(Input.Keys.S) || GameApp.isKeyJustPressed(Input.Keys.DOWN)) {
            selected++;
            if (selected > inventory.getItemsInInventory().size()){
                selected = 1;
            }
        }


        //equippen en/of verwijderen van een item
        if (GameApp.isKeyJustPressed(Input.Keys.ENTER)) {
            inventory.equipItem(inventory.getItemsInInventory().get(selected-1).itemID);
        }
        if (GameApp.isKeyJustPressed(Input.Keys.FORWARD_DEL)) {
            inventory.removeItems(selected-1);
        }


        //testen
        if (GameApp.isKeyJustPressed(Input.Keys.B)) {
            inventory.showInventory();
        }
        if (GameApp.isKeyJustPressed(Input.Keys.Z)) {
            inventory.addToInventory(0);
        }
        if (GameApp.isKeyJustPressed(Input.Keys.X)) {
            inventory.removeItems(0);
        }
        if (GameApp.isKeyJustPressed(Input.Keys.C)) {
            inventory.equipItem(selected);
        }
        if (GameApp.isKeyJustPressed(Input.Keys.V)) {
            System.out.println(mainPlayer.toString());
        }
        if (GameApp.isKeyJustPressed(Input.Keys.B)) {
            inventory.giveRandomItem();
        }
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
    }

    private void showItems(){
        int yInventoryPlacement = 1570;
        int i = 1;
        GameApp.startSpriteRendering();
        //Inventory kant
        for (Item currentItem : inventory.getItemsInInventory()) {
            if (i == selected) {
                GameApp.drawText("Basic", i + ": " + currentItem.toString(), 0, yInventoryPlacement, Color.ORANGE);
            } else {
                GameApp.drawText("Basic", i + ": " + currentItem.toString(), 0, yInventoryPlacement, Color.WHITE);
            }
            yInventoryPlacement -= 40;
            i++;
        }
        //Equipment kant
        yInventoryPlacement = 1570;
        i = 1;
        for (Item currentItem  : inventory.getEquipped()) {
            GameApp.drawText("Basic", i + ": " + currentItem.toString(), 1290, yInventoryPlacement, Color.WHITE);
            yInventoryPlacement -= 40;
            i++;
        }
        GameApp.endSpriteRendering();
    }


}