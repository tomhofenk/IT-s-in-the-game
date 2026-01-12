package nl.saxion.game.Bloodspire;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import nl.saxion.game.Bloodspire.Classes.Item;
import nl.saxion.game.Bloodspire.Methodes.Inventory;
import nl.saxion.game.Bloodspire.Classes.Player;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;

import java.awt.*;
import java.util.ArrayList;


public class InventoryScreen extends ScalableGameScreen {
    public static Inventory inventory = new Inventory();
    public Player mainPlayer = new Player();
    int selected = 1;
    int frameCounter = 0;
    int frames = 0;

    ArrayList<Box> boxes = new ArrayList<Box>();
    Box selectedBox = new Box();


    public InventoryScreen() {
        super(2560, 1600);

    }

    @Override
    public void show() {
        selectedBox.x = 32;
        selectedBox.y = 1440;
        selectedBox.index = 0;

        GameApp.addFont("Basic", "fonts/basic.ttf", getWorldWidth() / 75);
        GameApp.addFont("Basic2", "fonts/basic.ttf", getWorldWidth() / 25);
        GameApp.addTexture("ChestPiece", "textures/EmptyChestSlot.png");

        GameApp.addTexture("starter", "textures/Starter.png");
        GameApp.addTexture("common", "textures/Common.png");
        GameApp.addTexture("uncommon", "textures/Uncommon.png");
        GameApp.addTexture("rare", "textures/Rare.png");
        GameApp.addTexture("epic", "textures/Epic.png");
        GameApp.addTexture("legendary", "textures/Legendary.png");
        GameApp.addTexture("OP", "textures/Exotic.png");
        GameApp.addTexture("selected", "textures/Selected.png");

        GameApp.addColor("starter", 75, 75, 75);
        GameApp.addColor("common", 180, 180, 180);
        GameApp.addColor("uncommon", 30, 255, 0);
        GameApp.addColor("rare", 0, 122, 211);
        GameApp.addColor("epic", 163, 53, 238);
        GameApp.addColor("legendary", 255, 198, 17);
        GameApp.addColor("OP", 2, 255, 255);

    }

    public void render(float delta) {
        super.render(delta);
        GameApp.clearScreen();
        frameCounter++;

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
            if (selected <= 0) {
                selected = inventory.getItemsInInventory().size();
            }
        }
        if (GameApp.isKeyJustPressed(Input.Keys.S) || GameApp.isKeyJustPressed(Input.Keys.DOWN)) {
            selected++;
            if (selected > inventory.getItemsInInventory().size()) {
                selected = 1;
            }
        }


        //equippen en/of verwijderen van een item
        if (GameApp.isKeyJustPressed(Input.Keys.ENTER) || GameApp.isButtonJustPressed(Input.Buttons.LEFT)) {
            inventory.equipItem(inventory.getItemsInInventory().get(selected - 1).itemID);
        }
        if (GameApp.isKeyJustPressed(Input.Keys.FORWARD_DEL)) {
            inventory.removeItems(selected - 1);
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
            inventory.giveRandomItem(5);
        }
        renderLayout();

//        GameApp.startShapeRenderingFilled();
//        for (Box box : boxes) {
//            GameApp.drawRect(box.x-6, box.y-6, 140,140, Color.RED);
//        }
//        GameApp.endShapeRendering();
        remakeBoxes();

        showItems();

        if (inventory.getItemsInInventory().size() == 0) {
            emptyInventory();
        } else {
            hoverOverItems();
        }

    }


    @Override
    public void hide() {

    }


    private void renderLayout() {
        GameApp.startShapeRenderingFilled();
        GameApp.drawRect(0, 0, 1270, 1600, Color.BLACK);
        GameApp.drawRect(1290, 0, 1250, 1600, Color.GRAY);
        GameApp.endShapeRendering();
    }

    private void showItems() {
        int yInventoryPlacement = 1570;
        int i = 1;
        int Yplacement = (int) GameApp.getWorldHeight() - 160;
        int Xplacement = 32;
        int itemCounter = 0;

        GameApp.startSpriteRendering();
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
        }

        //Equipment kant
        yInventoryPlacement = 1570;
        i = 1;
        for (Item currentItem : inventory.getEquipped()) {
            GameApp.drawText("Basic", i + ": " + currentItem.toString(), 1290, yInventoryPlacement, Color.WHITE);
            yInventoryPlacement -= 40;
            i++;
        }
        GameApp.endSpriteRendering();
    }

    public void remakeBoxes() {

        int itemCounter = 0;
        int tempX = 32;
        int tempY = (int) GameApp.getWorldHeight() - 160;
        int numberOfItemsInInventory = inventory.getItemsInInventory().size();
        if (numberOfItemsInInventory != 0) {
            boxes.clear();
            for (int i = 1; i <= numberOfItemsInInventory; i++) {
                Box tempBox = new Box();

                if (itemCounter != 0) {
                    if (itemCounter % 7 == 0) {
                        tempY -= 160;
                        tempX = 32;
                    } else {
                        tempX += 160;
                    }
                } else {
                    tempX = 32;
                    tempY = (int) GameApp.getWorldHeight() - 160;
                }
                itemCounter++;
                tempBox.x = tempX;
                tempBox.y = tempY;
                tempBox.index = i - 1;
                boxes.add(tempBox);

            }
        }
    }

    public void hoverOverItems() {
        int mouseX = GameApp.getMousePositionInWindowX();
        int mouseY = (int) GameApp.getWorldHeight() - GameApp.getMousePositionInWindowY();
        boolean isSomethingSelected = false;


        for (Box currentBox : boxes) {
            System.out.println(currentBox.index);
            if (mouseX >= currentBox.x + 120 && mouseX <= currentBox.x + 228 && mouseY >= currentBox.y + 20 && mouseY <= currentBox.y + 128) {

                selectedBox = currentBox;
                isSomethingSelected = true;
                frames = frameCounter;

            }
        }
        if (!isSomethingSelected) {
            if (frames + (GameApp.getFramesPerSecond() / 2) < frameCounter) {
                selectedBox.x = 0;
                selectedBox.y = 0;
                selectedBox.index = 0;
            }
        }

        GameApp.startSpriteRendering();
        GameApp.drawTexture("selected", selectedBox.x - 6, selectedBox.y - 6);
        GameApp.endSpriteRendering();

        int index = 0;
        for (Item currentItem : inventory.getItemsInInventory()) {
            if (index == selectedBox.index) {
                selected = index + 1;
                GameApp.startShapeRenderingFilled();

                GameApp.drawRect(selectedBox.x + 134, selectedBox.y - 6, 300, 140, Color.WHITE);

                GameApp.endShapeRendering();
                GameApp.startSpriteRendering();

                GameApp.drawTexture(currentItem.rarity, selectedBox.x, selectedBox.y);
                GameApp.drawTexture("ChestPiece", selectedBox.x, selectedBox.y);

                GameApp.drawText("Basic", currentItem.itemName, selectedBox.x + 140, selectedBox.y + 100, currentItem.rarity);
                GameApp.drawText("Basic", "HP: " + currentItem.hitpointsValue, selectedBox.x + 140, selectedBox.y + 70, Color.BLACK);
                GameApp.drawText("Basic", "Def: " + currentItem.defenseValue, selectedBox.x + 250, selectedBox.y + 70, Color.BLACK);
                GameApp.drawText("Basic", "DMG: " + currentItem.damageValue, selectedBox.x + 140, selectedBox.y + 40, Color.BLACK);
                GameApp.drawText("Basic", "SP: " + currentItem.speedPenalty, selectedBox.x + 250, selectedBox.y + 40, Color.BLACK);

                GameApp.endSpriteRendering();
            }
            index++;
        }
        GameApp.startShapeRenderingFilled();
        //GameApp.drawRect(0,0,200,1500, Color.BLACK);
        GameApp.endShapeRendering();
    }

    public void emptyInventory() {
        GameApp.startShapeRenderingFilled();
        GameApp.drawRect(0, 0, 1270, 1600, Color.BLACK);
        GameApp.endShapeRendering();
        GameApp.startSpriteRendering();
        GameApp.drawText("Basic2", "Inventory is empty!", 50, GameApp.getWorldHeight() - 200, Color.WHITE);
        GameApp.endSpriteRendering();
    }

    private class Box {
        int x;
        int y;
        int index;
    }

}