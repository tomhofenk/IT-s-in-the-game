package nl.saxion.game.Bloodspire;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;

import java.awt.*;

public class DooDScreen extends ScalableGameScreen {

    public DooDScreen() {
        super(1600, 900);
    }

    @Override
    public void show() {
        GameApp.addFont("Basic", "fonts/basic.ttf", 25);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        // escape -> main menu
        if (GameApp.isKeyJustPressed(Input.Keys.ESCAPE)) {
            GameApp.switchScreen("MainMenuScreen");
        }

        GameApp.clearScreen(Color.BLACK);
        GameApp.startSpriteRendering();
        GameApp.drawTextCentered("Basic" ,"STOUT WOUTJE", getWorldWidth()/2, getWorldHeight()/2, Color.WHITE);
        GameApp.endSpriteRendering();
    }

    @Override
    public void hide() {

    }
}
