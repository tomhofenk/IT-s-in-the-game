package nl.saxion.game.Bloodspire;

import nl.saxion.game.Bloodspire.Classes.Item;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;


public class InventoryScreen extends ScalableGameScreen {



    public InventoryScreen() {
        super(10 , 5);
    }

    @Override
    public void show() {

    }
    public void render(float delta) {


        // ALWAYS CALL super.render(delta) AFTERWARDS!!!
        // This applies the camera settings to the shape renderer and sprite batch.
        super.render(delta);

    }


    @Override
    public void hide() {

    }
}