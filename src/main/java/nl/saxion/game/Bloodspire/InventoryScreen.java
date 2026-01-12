package nl.saxion.game.Bloodspire;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import nl.saxion.game.Bloodspire.Classes.Item;
import nl.saxion.game.Bloodspire.Methodes.Inventory;
import nl.saxion.game.Bloodspire.Classes.Player;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;

import java.util.ArrayList;


public class InventoryScreen extends ScalableGameScreen {
    public static Inventory inventory = new Inventory();
    public Player mainPlayer = new Player();
    int selected = 1;

    ArrayList<Box> boxes = new ArrayList<Box>();
    int oldNumberOfItemsInInventory = 0;



    public InventoryScreen() {
        super(2560, 1600);
    }

    @Override
    public void show() {

        GameApp.addFont("Basic","fonts/basic.ttf",getWorldWidth()/75);
        GameApp.addTexture("ChestPiece", "textures/EmptyChestSlot.png");

        GameApp.addTexture("starter", "textures/Starter.png");
        GameApp.addTexture("common", "textures/Common.png");
        GameApp.addTexture("uncommon", "textures/Uncommon.png");
        GameApp.addTexture("rare", "textures/Rare.png");
        GameApp.addTexture("epic", "textures/Epic.png");
        GameApp.addTexture("legendary", "textures/Legendary.png");
        GameApp.addTexture("legend", "textures/Legendary.png");
        GameApp.addTexture("OP","textures/Exotic.png");
        GameApp.addTexture("selected","textures/Selected.png");

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
        remakeBoxes();
        GameApp.startShapeRenderingFilled();
        for (Box box : boxes) {
            GameApp.drawRect(box.x-6, box.y-6, 140,140, Color.RED);
        }
        GameApp.endShapeRendering();
        hoverOverItems();
        showItems();



        if (GameApp.isKeyJustPressed(Input.Keys.NUMPAD_1)) {
            System.out.println("MousX: " + GameApp.getMousePositionInWindowX() + " MouseY: " + GameApp.getMousePositionInWindowY());
        }
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
        int Yplacement = (int)GameApp.getWorldHeight()-160;
        int Xplacement = 32;
        int itemCounter = 0;
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

        for (Item currentItem : inventory.getItemsInInventory()) {
            itemCounter++;

            GameApp.drawTexture(currentItem.rarity, Xplacement, Yplacement);
            GameApp.drawTexture("ChestPiece", Xplacement, Yplacement);
            yInventoryPlacement -= 40;
            i++;

            if (itemCounter % 7 == 0 && itemCounter != 0) {
                Yplacement -= 160;
                Xplacement = 32;
            } else {
                Xplacement += 160;
            }
            inventory.getItemsInInventory();
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

    public void remakeBoxes() {

        int itemCounter = 0;
        int tempX = 32;
        int tempY = (int)GameApp.getWorldHeight()-160;
        int numberOfItemsInInventory = inventory.getItemsInInventory().size();
        if (numberOfItemsInInventory != 0 && numberOfItemsInInventory != oldNumberOfItemsInInventory) {
            boxes.clear();
            for (int i = 1; i <= numberOfItemsInInventory; i++) {
                Box tempBox = new Box();
                itemCounter++;
                if (itemCounter != 0) {
                    if (itemCounter % 7 == 0) {
                        tempY -= 160;
                        tempX = 32;
                    } else {
                        tempX += 160;
                    }
                } else {
                    tempX = 32;
                    tempY = (int)GameApp.getWorldHeight()-160;
                }

                tempBox.x = tempX;
                tempBox.y = tempY;
                tempBox.index = i-1;
                boxes.add(tempBox);

            }
        }
        oldNumberOfItemsInInventory = numberOfItemsInInventory;
    }

    public void hoverOverItems() {
        int mouseX = GameApp.getMousePositionInWindowX();
        int mouseY = (int)GameApp.getWorldHeight() - GameApp.getMousePositionInWindowY();

        if (GameApp.isKeyJustPressed(Input.Keys.NUMPAD_1)) {
            System.out.println("MousX: " + GameApp.getMousePositionInWindowX() + " MouseY: " + GameApp.getMousePositionInWindowY());
        }
        //System.out.println("mouseX: " + mouseX + ", mouseY: " + mouseY);

        GameApp.startSpriteRendering();
        for (Box currentBox : boxes) {
            if (mouseX >= currentBox.x && mouseX <= currentBox.x+128 && mouseY >= currentBox.y && mouseY <= currentBox.y+128) {
                GameApp.drawTexture("selected", currentBox.x-6, currentBox.y-6);
            }
        }
        GameApp.endSpriteRendering();
    }

    private class Box {
        int x;
        int y;
        int index;
    }

}