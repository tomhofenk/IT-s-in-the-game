package nl.saxion.game.Bloodspire;

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
    int frameCounter = 0;
    int frames = 0;

    ArrayList<Box> boxesInventorySide = new ArrayList<>();
    ArrayList<Box> boxesEquipedSide = new ArrayList<>();
    Box selectedBoxInv = new Box();
    Box selectedBoxEquiped = new Box();


    public InventoryScreen() {
        super(2560, 1600);

    }

    @Override
    public void show() {
        selectedBoxInv.x = 32;
        selectedBoxInv.y = 1440;
        selectedBoxInv.index = 0;

        GameApp.addFont("Basic", "fonts/basic.ttf", getWorldWidth() / 75);
        GameApp.addFont("Basic2", "fonts/basic.ttf", getWorldWidth() / 25);
        GameApp.addFont("Basic3", "fonts/basic.ttf", getWorldWidth() / 15);

        GameApp.addTexture("CharacterTexture", "textures/DungeonCharacterpng.png");

        GameApp.addTexture("helmet", "textures/Helm.png");
        GameApp.addTexture("chestplate", "textures/Chest.png");
        GameApp.addTexture("leggings", "textures/Legs.png");
        GameApp.addTexture("boots", "textures/Boots.png");
        GameApp.addTexture("necklace", "textures/Necklace.png");
        GameApp.addTexture("sword", "textures/Sword.png");
        GameApp.addTexture("shield", "textures/Shield.png");


        GameApp.addTexture("starter", "textures/Starter.png");
        GameApp.addTexture("common", "textures/Common.png");
        GameApp.addTexture("uncommon", "textures/Uncommon.png");
        GameApp.addTexture("rare", "textures/Rare.png");
        GameApp.addTexture("epic", "textures/Epic.png");
        GameApp.addTexture("legendary", "textures/Legendary.png");
        GameApp.addTexture("OP", "textures/Exotic.png");
        GameApp.addTexture("selected", "textures/Selected.png");

        GameApp.addColor("starter", 57, 19, 19);
        GameApp.addColor("common", 180, 180, 180);
        GameApp.addColor("uncommon", 30, 255, 0);
        GameApp.addColor("rare", 0, 122, 211);
        GameApp.addColor("epic", 163, 53, 238);
        GameApp.addColor("legendary", 255, 198, 17);
        GameApp.addColor("OP", 2, 255, 255);

        GameApp.addTexture("HP", "textures/HP.png");
        GameApp.addTexture("Def", "textures/Def.png");
        GameApp.addTexture("Speed", "textures/Speed.png");
        GameApp.addTexture("DMG", "textures/DMG.png");

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
        if (GameApp.isKeyJustPressed(Input.Keys.ENTER) || (GameApp.isButtonJustPressed(Input.Buttons.LEFT) && getMouseX() < 1300)) {
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
        showPlayerStats();

        remakeBoxesInventorySide();
        remakeBoxesEquipedSide();

        showItems();

        if (inventory.getItemsInInventory().isEmpty()) {
            emptyInventory();
        } else {
            hoverOverItemsInventorySide();
        }
        hoverOverItemsEquipedSide();

    }


    @Override
    public void hide() {

    }


    private void renderLayout() {
        GameApp.startShapeRenderingFilled();
        GameApp.drawRect(0, 0, 1270, 1600, Color.BLACK);
        GameApp.drawRect(1290, 0, 1250, 1600, Color.GRAY);
        GameApp.endShapeRendering();
        GameApp.startSpriteRendering();
        GameApp.drawTexture("CharacterTexture", 1700, 400, 700, 700);
        GameApp.endSpriteRendering();
    }

    private void showItems() {
        int Yplacement = (int) GameApp.getWorldHeight() - 160;
        int Xplacement = 32;
        int itemCounter = 0;

        GameApp.startSpriteRendering();
        for (Item currentItem : inventory.getItemsInInventory()) {
            itemCounter++;

            GameApp.drawTexture(currentItem.rarity, Xplacement, Yplacement);
            GameApp.drawTexture(currentItem.itemType, Xplacement, Yplacement);

            if (itemCounter % 7 == 0 && itemCounter != 0) {
                Yplacement -= 160;
                Xplacement = 32;
            } else {
                Xplacement += 160;
            }
        }

        //Equipment kant
        int yEquipedPlacement = 1150;
        for (Item currentItem : inventory.getEquipped()) {
            //GameApp.drawText("Basic", i + ": " + currentItem.toString(), 1290, yEquipedPlacement, Color.WHITE);

            itemCounter++;

            GameApp.drawTexture(currentItem.rarity, 1400, yEquipedPlacement);
            GameApp.drawTexture(currentItem.itemType, 1400, yEquipedPlacement);

            yEquipedPlacement -= 140;
        }
        GameApp.endSpriteRendering();
    }

    public void remakeBoxesInventorySide() {

        int itemCounter = 0;
        int tempX = 32;
        int tempY = (int) GameApp.getWorldHeight() - 160;
        int numberOfItemsInInventory = inventory.getItemsInInventory().size();
        if (numberOfItemsInInventory != 0) {
            boxesInventorySide.clear();
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
                boxesInventorySide.add(tempBox);

            }
        }
    }

    public void remakeBoxesEquipedSide() {
        int itemCounter = 0;
        int tempX = 1400;
        int tempY = 1150;
        int numberOfItemsEquiped = inventory.getEquipped().size();
        if (numberOfItemsEquiped != 0) {
            boxesEquipedSide.clear();
            for (int i = 1; i <= numberOfItemsEquiped; i++) {
                Box tempBox = new Box();

                if (itemCounter != 0) {
                    tempY -= 140;
                } else {
                    tempX = 1400;
                    tempY = 1150;
                }
                itemCounter++;
                tempBox.x = tempX;
                tempBox.y = tempY;
                tempBox.index = i - 1;
                boxesEquipedSide.add(tempBox);
            }
        }
    }

    public void hoverOverItemsInventorySide() {
        int mouseX = GameApp.getMousePositionInWindowX();
        int mouseY = (int) GameApp.getWorldHeight() - GameApp.getMousePositionInWindowY();
        boolean isSomethingSelected = false;


        for (Box currentBox : boxesInventorySide) {

            if (mouseX >= currentBox.x + 120 && mouseX <= currentBox.x + 228 && mouseY >= currentBox.y + 20 && mouseY <= currentBox.y + 128) {

                selectedBoxInv = currentBox;
                isSomethingSelected = true;
                frames = frameCounter;

            }
        }
        if (!isSomethingSelected) {
            if (frames + (GameApp.getFramesPerSecond() / 2) < frameCounter) {
                selectedBoxInv.x = 0;
                selectedBoxInv.y = 0;
                selectedBoxInv.index = 0;
            }
        }

        GameApp.startSpriteRendering();
        GameApp.drawTexture("selected", selectedBoxInv.x - 6, selectedBoxInv.y - 6);
        GameApp.endSpriteRendering();

        int index = 0;
        for (Item currentItem : inventory.getItemsInInventory()) {
            if (index == selectedBoxInv.index) {
                selected = index + 1;
                GameApp.startShapeRenderingFilled();

                GameApp.drawRect(selectedBoxInv.x + 134, selectedBoxInv.y - 6, 300, 140, Color.WHITE);

                GameApp.endShapeRendering();
                GameApp.startSpriteRendering();

                GameApp.drawTexture(currentItem.rarity, selectedBoxInv.x, selectedBoxInv.y);
                GameApp.drawTexture(currentItem.itemType, selectedBoxInv.x, selectedBoxInv.y);

                GameApp.drawText("Basic", currentItem.itemName, selectedBoxInv.x + 140, selectedBoxInv.y + 100, currentItem.rarity);
                GameApp.drawText("Basic", "HP: " + currentItem.hitpointsValue, selectedBoxInv.x + 140, selectedBoxInv.y + 70, Color.BLACK);
                GameApp.drawText("Basic", "Def: " + currentItem.defenseValue, selectedBoxInv.x + 250, selectedBoxInv.y + 70, Color.BLACK);
                GameApp.drawText("Basic", "DMG: " + currentItem.damageValue, selectedBoxInv.x + 140, selectedBoxInv.y + 40, Color.BLACK);
                GameApp.drawText("Basic", "SP: " + currentItem.speedPenalty, selectedBoxInv.x + 250, selectedBoxInv.y + 40, Color.BLACK);

                GameApp.endSpriteRendering();
            }
            index++;
        }
        GameApp.startShapeRenderingFilled();
        GameApp.drawRect(0, 0, 500, 300, Color.BLACK);
        GameApp.endShapeRendering();
    }

    public void hoverOverItemsEquipedSide() {
        int mouseX = GameApp.getMousePositionInWindowX();
        int mouseY = (int) GameApp.getWorldHeight() - GameApp.getMousePositionInWindowY();
        boolean isSomethingSelected = false;


        for (Box currentBox : boxesEquipedSide) {

            if (mouseX >= currentBox.x + 120 && mouseX <= currentBox.x + 228 && mouseY >= currentBox.y + 20 && mouseY <= currentBox.y + 128) {

                selectedBoxEquiped = currentBox;
                isSomethingSelected = true;
                frames = frameCounter;

            }
        }
        if (!isSomethingSelected) {
            if (frames + (GameApp.getFramesPerSecond() / 2) < frameCounter) {
                selectedBoxEquiped.x = 0;
                selectedBoxEquiped.y = 0;
                selectedBoxEquiped.index = 0;
            }
        }

        GameApp.startSpriteRendering();
        GameApp.drawTexture("selected", selectedBoxEquiped.x - 6, selectedBoxEquiped.y - 6);
        GameApp.endSpriteRendering();

        int index = 0;
        for (Item currentItem : inventory.getEquipped()) {
            if (index == selectedBoxEquiped.index) {
                GameApp.startShapeRenderingFilled();

                GameApp.drawRect(selectedBoxEquiped.x + 134, selectedBoxEquiped.y - 6, 300, 140, Color.WHITE);

                GameApp.endShapeRendering();
                GameApp.startSpriteRendering();

                GameApp.drawTexture(currentItem.rarity, selectedBoxEquiped.x, selectedBoxEquiped.y);
                GameApp.drawTexture(currentItem.itemType, selectedBoxEquiped.x, selectedBoxEquiped.y);

                GameApp.drawText("Basic", currentItem.itemName, selectedBoxEquiped.x + 140, selectedBoxEquiped.y + 100, currentItem.rarity);
                GameApp.drawText("Basic", "HP: " + currentItem.hitpointsValue, selectedBoxEquiped.x + 140, selectedBoxEquiped.y + 70, Color.BLACK);
                GameApp.drawText("Basic", "Def: " + currentItem.defenseValue, selectedBoxEquiped.x + 250, selectedBoxEquiped.y + 70, Color.BLACK);
                GameApp.drawText("Basic", "DMG: " + currentItem.damageValue, selectedBoxEquiped.x + 140, selectedBoxEquiped.y + 40, Color.BLACK);
                GameApp.drawText("Basic", "SP: " + currentItem.speedPenalty, selectedBoxEquiped.x + 250, selectedBoxEquiped.y + 40, Color.BLACK);

                GameApp.endSpriteRendering();
            }
            index++;
        }
        GameApp.startShapeRenderingFilled();
        GameApp.drawRect(0, 0, 500, 300, Color.BLACK);
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

    public void showPlayerStats() {
        GameApp.startSpriteRendering();
        GameApp.drawTexture("HP", 1400, 1450);
        String playerHP = mainPlayer.getHitpoints() + "";
        GameApp.drawText("Basic3", playerHP, 1550, 1450, Color.WHITE);
        GameApp.drawTexture("DMG", 1400, 1300);
        String playerDMG = mainPlayer.getAttackDamage() + "";
        GameApp.drawText("Basic3", playerDMG, 1550, 1300, Color.WHITE);
        GameApp.drawTexture("Def", 2000, 1450);
        String playerDef = mainPlayer.getDefense() + "";
        GameApp.drawText("Basic3", playerDef, 2150, 1450, Color.WHITE);
        GameApp.drawTexture("Speed", 2000, 1300);
        String playerSpeed = mainPlayer.getAttackSpeed() + "";
        GameApp.drawText("Basic3", playerSpeed, 2150, 1300, Color.WHITE);
        GameApp.endSpriteRendering();
    }

    private static class Box {
        int x;
        int y;
        int index;
    }

}