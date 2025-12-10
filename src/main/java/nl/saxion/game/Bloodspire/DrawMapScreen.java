package nl.saxion.game.Bloodspire;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;

import java.util.ArrayList;

public class DrawMapScreen extends ScalableGameScreen {
    String randomBoxColor = "violet-500";
    String randomBackgroundColor = "black";
    Box centeredBox = new Box();
    ArrayList<DrawnTile> DrawnTiles = new ArrayList<>();

    public DrawMapScreen() {
        super(160, 90);
    }

    @Override
    public void show() {
        for (int x = 0; x < getWorldWidth(); x++) {
            for (int y = 0; y < getWorldHeight(); y++) {
                DrawnTile newTile = new DrawnTile();
                newTile.x = x;
                newTile.y = y;
                newTile.color = "Color.BLACK";
                DrawnTiles.add(newTile);
            }
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (GameApp.isButtonJustPressed(Input.Buttons.LEFT)) {
            float mouseX = getMouseX();
            float mouseY = getMouseY();
        }

        // Draw elements
        GameApp.clearScreen(randomBackgroundColor);
        GameApp.startShapeRenderingFilled();
        //GameApp.drawRect(boxX, boxY, centeredBox.width, centeredBox.height, randomBoxColor);
        GameApp.endShapeRendering();

    }

    @Override
    public void hide() {

    }

    private class DrawnTile {
        int x;
        int y;
        String color;
    }
}
