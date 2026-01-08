package nl.saxion.game.Bloodspire;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import nl.saxion.game.Bloodspire.Classes.Player;
import nl.saxion.game.Bloodspire.Methodes.*;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.CameraControlledGameScreen;


public class MyLevelScreen extends CameraControlledGameScreen {

    private Methodes methodes;
    private MovementVars mv;
    public LevelVars lv = new LevelVars();
    private int framesCounter = 0;
    //public Player mainPlayer = new Player();
    boolean nextLevel = false;


    public MyLevelScreen(int viewportWidth, int viewportHeight, int worldWidth, int worldHeight) {
        super(viewportWidth, viewportHeight, worldWidth, worldHeight);
    }


    @Override
    public void show() {
        //items inladen als dit nog niet gedaan is, en het equippen van de starterkit.
        InventoryScreen.inventory.loadItems();
        enableHUD(160, 90);

        // startpositie (in pixels) — hier 0,0 maar je kunt dit veranderen
        int startX = LevelVars.getLevelStartX(lv.getCurrentLevel());
        int startY = LevelVars.getLevelStartY(lv.getCurrentLevel());
        System.out.println("startX: " + startX + " startY: " + startY);
        mv = new MovementVars(
                startX*64,
                startY*64,
                (int)getWorldHeight(),
                (int)getWorldWidth(),
                (int)getMouseX(),
                (int)getMouseY(),
                GameApp.getFramesPerSecond() / 3,
                MapData.getLevel(lv.getCurrentLevel())
        );
        System.out.println(lv.getCurrentLevel());

        methodes = new Methodes();
        if (lv.getCurrentLevel() == lv.getOldLevel()) {
            methodes.getOldCords(mv, lv, startX, startY);
        }

        // camera direct naar de speler
        setCameraTargetInstantly((mv.playerWorldX+mv.pixelPerGridTile/2), (mv.playerWorldY+mv.pixelPerGridTile/2));

        methodes.addAllTextures();

    }


    @Override
    public void render(float delta) {
        updateMV();
        methodes.gameLogic(mv);
        if (GameApp.isKeyJustPressed(Input.Keys.TAB)) {
            nextLevel = methodes.checkEnemy(mv);
            GameApp.switchScreen("MainMenuScreen");
        }

        if (GameApp.isKeyJustPressed(Input.Keys.ENTER)) {
            GameApp.switchScreen("DooDScreen");
        }


        // camera volgen
        setCameraTarget((mv.playerWorldX+mv.pixelPerGridTile/2), (mv.playerWorldY+mv.pixelPerGridTile/2));

        super.render(delta);

        GameApp.clearScreen();
        switchToWorldRendering();
        methodes.renderWorld(mv);
        switchToHudRendering();
        methodes.renderHUD();
    }

    @Override
    public void hide() {
        methodes.disposeAllTextures();
        methodes.setOldCords(mv, lv);
        LevelVars.setOldLevel(lv.getCurrentLevel());
        System.out.println(LevelVars.getOldLevel());
        if (nextLevel) {
            LevelVars.setCurrentLevel(lv.getCurrentLevel()+1);
        }
    }

    private void updateMV() {
        framesCounter++;
        mv.framesCounter = framesCounter;
        mv.minTimeBetweenMovement = GameApp.getFramesPerSecond() / 3;

        // update input-based waarden in mv
        mv.mouseX = (int)getMouseX();
        mv.mouseY = (int)getMouseY();

        // tile coords blijven consistent (kan ook in Methodes, maar we houden hem hier up-to-date vóór movement)
        mv.playerTileX = mv.playerWorldX / mv.pixelPerGridTile;
        mv.playerTileY = mv.playerWorldY / mv.pixelPerGridTile;
    }
}

