package nl.saxion.game.Bloodspire;

import com.badlogic.gdx.Input;
import nl.saxion.game.Bloodspire.Methodes.CsvLoader;
import nl.saxion.game.Bloodspire.Methodes.MapData;
import nl.saxion.game.Bloodspire.Methodes.MovementVars;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;

public class BattleScreen extends ScalableGameScreen {

    private MovementVars mv;
    private CsvLoader csvLoader;

    public BattleScreen() {
        super(1280, 720);
    }

    @Override
    public void show() {
        mv = new MovementVars(0,0,0,0,0,0,0,
                MapData.getLevel(1)
        );
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        GameApp.clearScreen();

        if (GameApp.isKeyJustPressed(Input.Keys.K)) {
            System.out.println("Killed");
            mv.mapData.remove(48);
            System.out.println("Deleted");

        }

        //terug naar main menu
        if (GameApp.isKeyJustPressed(Input.Keys.ESCAPE)) {
            GameApp.switchScreen("MyLevelScreen");
        }


        //terugkeren naar het speelscherm
        if (GameApp.isKeyJustPressed(Input.Keys.I)) {
            GameApp.switchScreen("MyLevelScreen");
        }
    }

    @Override
    public void hide() {

    }
}

