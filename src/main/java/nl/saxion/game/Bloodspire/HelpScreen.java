package nl.saxion.game.Bloodspire;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;

public class HelpScreen extends ScalableGameScreen {
    public HelpScreen() {
        super(8000, 4500);
    }

    @Override
    public void show() {
        GameApp.addFont("Basic", "fonts/basic.ttf", 1000);
        GameApp.addFont("Basic2", "fonts/basic.ttf", 400);

    }

    @Override
    public void render(float delta){
        super.render(delta);
        GameApp.clearScreen();

        if (GameApp.isKeyJustPressed(Input.Keys.ESCAPE)) {
            GameApp.switchScreen("MainMenuScreen");
        }

        if (GameApp.isKeyJustPressed(Input.Keys.ENTER)) {
            GameApp.switchScreen("MyLevelScreen");
        }

        drawHelpScherm();


    }

    @Override
    public void hide() {

    }


    private void drawHelpScherm(){
        GameApp.startSpriteRendering();
        GameApp.drawTextCentered("Basic2" ,"Use WASD to move", getWorldWidth()/2, getWorldHeight()-500, Color.WHITE);
        GameApp.drawTextCentered("Basic2", "Press I to open your inventory.", getWorldWidth()/2, getWorldHeight()-750, Color.WHITE);
        GameApp.drawTextCentered("Basic2", "Walk onto a red enemy to fight him", getWorldWidth()/2, getWorldHeight()-1000, Color.WHITE);
        GameApp.drawTextCentered("Basic2", "Once you defeated all enemies, press TAB to move to the next level.", getWorldWidth()/2, getWorldHeight()-1250, Color.WHITE);
        GameApp.drawTextCentered("Basic2", "Press escape to go back to the menu.",  getWorldWidth()/2, getWorldHeight()-3400, Color.WHITE);
        GameApp.drawTextCentered("Basic2", "Press ENTER to go to the first level.",  getWorldWidth()/2, getWorldHeight()-3550, Color.WHITE);
        GameApp.endSpriteRendering();
    }
}
