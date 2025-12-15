package nl.saxion.game.Bloodspire;

import com.badlogic.gdx.Input;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;

public class MainMenuScreen extends ScalableGameScreen {
    int selectedOption = 0;

    public MainMenuScreen() {
        super(1280, 720);
    }


    @Override
    public void show() {
        GameApp.addFont("basic", "fonts/basic.ttf", getWorldWidth()/12);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        // When the user presses enter, go to the next screen
        if (GameApp.isKeyJustPressed(Input.Keys.SPACE) || GameApp.isKeyJustPressed(Input.Keys.ENTER)) {
            if (selectedOption == 0) {
                GameApp.switchScreen("MyLevelScreen");
            } else if (selectedOption == 1) {
                GameApp.quit();
            } else if (selectedOption == 2) {
                GameApp.switchScreen("DrawMapScreen");
            }

        }

        // Select option
        if (GameApp.isKeyJustPressed(Input.Keys.S) || GameApp.isKeyJustPressed(Input.Keys.DOWN)) {
            selectedOption = (selectedOption + 1)%3;
        }
        
        if (GameApp.isKeyJustPressed(Input.Keys.W) || GameApp.isKeyJustPressed(Input.Keys.UP)) {
            selectedOption = (selectedOption - 1)%3;
            if (selectedOption < 0) {
                selectedOption = 2;
            }
        }

        // Render the main menu
        GameApp.clearScreen("black");
        GameApp.startSpriteRendering();
        GameApp.drawTextCentered("Basic", "Bloodspire", getWorldWidth()/2, getWorldHeight()/2+100, "red-500" );
        // Color selected item
        if (selectedOption == 0) { //start
            GameApp.drawTextCentered("basic", "Start", getWorldWidth()/2, getWorldHeight()/2, "amber-500");
        } else {
            GameApp.drawTextCentered("basic", "Start", getWorldWidth()/2, getWorldHeight()/2, "white");
        }
        if (selectedOption == 1) { //quit
            GameApp.drawTextCentered("basic", "Quit", getWorldWidth()/2, getWorldHeight()/2-100, "amber-500");
        } else {
            GameApp.drawTextCentered("basic", "Quit", getWorldWidth()/2, getWorldHeight()/2-100, "white");
        }
        if (selectedOption == 2) {
            GameApp.drawTextCentered("basic", "Draw a Map", getWorldWidth()/2, getWorldHeight()/2-200, "amber-500");
        } else {
            GameApp.drawTextCentered("basic", "Draw a Map", getWorldWidth()/2, getWorldHeight()/2-200, "white");
        }
        GameApp.endSpriteRendering();
    }

    @Override
    public void hide() {

        GameApp.disposeFont("basic");
    }
}
