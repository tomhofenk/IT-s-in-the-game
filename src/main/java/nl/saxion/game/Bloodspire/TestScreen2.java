package nl.saxion.game.Bloodspire;

import nl.saxion.gameapp.screens.ScalableGameScreen;
import nl.saxion.gameapp.GameApp;


public class TestScreen2 extends ScalableGameScreen {

    public TestScreen2() {
        super(10 , 5);
    }

    @Override
    public void show() {
        GameApp.addTexture("CharacterTexture", "textures/DungeonCharacterpng.png");

    }

    @Override
    public void render(float delta) {
        super.render(delta);

    }

    @Override
    public void hide() {

    }
}