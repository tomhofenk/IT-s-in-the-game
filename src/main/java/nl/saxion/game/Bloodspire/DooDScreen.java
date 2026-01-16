package nl.saxion.game.Bloodspire;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import nl.saxion.game.Bloodspire.Methodes.Inventory;
import nl.saxion.game.Bloodspire.Methodes.LevelVars;
import nl.saxion.game.Main;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;

import java.awt.*;

public class DooDScreen extends ScalableGameScreen {

    Inventory inventory = new Inventory();

    public DooDScreen() {
        super(8000, 4500);
    }

    @Override
    public void show() {
        GameApp.addFont("Basic", "fonts/basic.ttf", 1000);
        GameApp.addFont("Basic2", "fonts/basic.ttf", 400);
        GameApp.addTexture("StoutWoutje", "textures/TheAlmightyStoutWoutje.jpg");
        Main.setMapAndEnemyData();
        LevelVars.setOldLevel(1);
        LevelVars.setCurrentLevel(1);
        LevelVars.setCurrentLevel(1);
        LevelVars.setOldX(0);
        LevelVars.setOldY(0);
        inventory.purge();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        // escape -> main menu
        if (GameApp.isKeyJustPressed(Input.Keys.ESCAPE)) {
            GameApp.switchScreen("MainMenuScreen");
        }

        if (GameApp.isKeyJustPressed(Input.Keys.ENTER)) {
            GameApp.switchScreen("MyLevelScreen");
        }

        GameApp.clearScreen(Color.BLACK);
        drawDoodScreen();
    }

    @Override
    public void hide() {

    }

    private void drawDoodScreen() {
        GameApp.startSpriteRendering();
        GameApp.drawTextCentered("Basic" ,"Defeated", getWorldWidth()/2, getWorldHeight()-500, Color.RED);
        GameApp.drawTextCentered("Basic2", "By The Almighty Stout Woutje", getWorldWidth()/2, getWorldHeight()-750, Color.WHITE);
        GameApp.drawTextureCentered("StoutWoutje", getWorldWidth()/2, getWorldHeight()/2);
        GameApp.drawTextCentered("Basic2", "Press Escape to go to the main menu",  getWorldWidth()/2, getWorldHeight()-3400, Color.WHITE);
        GameApp.drawTextCentered("Basic2", "Press Enter to start over",  getWorldWidth()/2, getWorldHeight()-3550, Color.WHITE);
        GameApp.endSpriteRendering();
    }

}
