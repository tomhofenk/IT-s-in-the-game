package nl.saxion.game.Bloodspire;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import nl.saxion.game.Bloodspire.Methodes.MovementVars;
import nl.saxion.game.Bloodspire.Methodes.Methodes;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.CameraControlledGameScreen;

public class MyLevelScreen extends CameraControlledGameScreen {

    private Methodes methodes;
    private MovementVars movementVars;
    private int framesCounter = 0;

    public MyLevelScreen(int viewportWidth, int viewportHeight, int worldWidth, int worldHeight) {
        super(viewportWidth, viewportHeight, worldWidth, worldHeight);
    }

    @Override
    public void show() {
        enableHUD(160, 90);

        // startpositie (in pixels) — hier 0,0 maar je kunt dit veranderen
        int startX = 0;
        int startY = 0;

        movementVars = new MovementVars(
                startX,
                startY,
                (int)getWorldHeight(),
                (int)getWorldWidth(),
                (int)getMouseX(),
                (int)getMouseY(),
                GameApp.getFramesPerSecond() / 3
        );

        methodes = new Methodes();

        // camera direct naar de speler
        setCameraTargetInstantly(movementVars.playerWorldX, movementVars.playerWorldY);

        GameApp.addTexture("CharacterTexture", "textures/DungeonCharacterpng.png");
    }

    @Override
    public void render(float delta) {
        gameLogic();

        // camera volgen
        setCameraTarget(movementVars.playerWorldX, movementVars.playerWorldY);

        super.render(delta);

        GameApp.clearScreen();
        renderWorld();
        renderHUD();
    }

    @Override
    public void hide() {
        GameApp.disposeTexture("CharacterTexture");
    }

    public void gameLogic() {
        // verhoog lokale frame counter en geef door aan movementVars
        framesCounter++;
        movementVars.framesCounter = framesCounter;
        movementVars.minTimeBetweenMovement = GameApp.getFramesPerSecond() / 3;

        // update input-based waarden in movementVars
        movementVars.mouseX = (int)getMouseX();
        movementVars.mouseY = (int)getMouseY();

        // tile coords blijven consistent (kan ook in Methodes, maar we houden hem hier up-to-date vóór movement)
        movementVars.playerTileX = movementVars.playerWorldX / movementVars.pixelPerGridTile;
        movementVars.playerTileY = movementVars.playerWorldY / movementVars.pixelPerGridTile;

        // roep de gedeelde movement aan
        methodes.Movement(movementVars);

        // optioneel: debug print
        if (GameApp.isKeyJustPressed(Input.Keys.P)) {
            System.out.println("tile: " + movementVars.playerTileX + "x " + movementVars.playerTileY
                    + "y world: " + movementVars.playerWorldX + "x " + movementVars.playerWorldY + "y");
        }

        // escape -> main menu
        if (GameApp.isKeyPressed(Input.Keys.ESCAPE)) {
            GameApp.switchScreen("MainMenuScreen");
        }
    }

    public void renderWorld() {
        switchToWorldRendering();

        renderGridTiles(movementVars.pixelPerGridTile);

        GameApp.startSpriteRendering();
        GameApp.drawTexture("CharacterTexture", movementVars.playerWorldX, movementVars.playerWorldY);
        GameApp.endSpriteRendering();
    }

    public void renderHUD() {
        switchToHudRendering();

        GameApp.startShapeRenderingFilled();
        GameApp.drawRect(10, 10, 30, 5, Color.WHITE);
        GameApp.endShapeRendering();
    }

    public void renderGridTiles(int pixelsPerGridTile) {
        switchToWorldRendering();
        GameApp.startShapeRenderingOutlined();
        GameApp.setLineWidth(1);

        for (int y = 0; y < getWorldHeight() / pixelsPerGridTile; y++) {
            for (int x = 0; x < getWorldWidth() / pixelsPerGridTile; x++) {
                GameApp.drawRect(x * pixelsPerGridTile, y * pixelsPerGridTile, pixelsPerGridTile, pixelsPerGridTile, "stone-500");
            }
        }

        // highlight tiles rond speler
        int tx = movementVars.playerTileX;
        int ty = movementVars.playerTileY;

        GameApp.drawRect(tx * pixelsPerGridTile, ty * pixelsPerGridTile, pixelsPerGridTile, pixelsPerGridTile, "stone-50");
        GameApp.drawRect((tx - 1) * pixelsPerGridTile, ty * pixelsPerGridTile, pixelsPerGridTile, pixelsPerGridTile, "stone-50");
        GameApp.drawRect((tx + 1) * pixelsPerGridTile, ty * pixelsPerGridTile, pixelsPerGridTile, pixelsPerGridTile, "stone-50");
        GameApp.drawRect(tx * pixelsPerGridTile, (ty - 1) * pixelsPerGridTile, pixelsPerGridTile, pixelsPerGridTile, "stone-50");
        GameApp.drawRect(tx * pixelsPerGridTile, (ty + 1) * pixelsPerGridTile, pixelsPerGridTile, pixelsPerGridTile, "stone-50");

        GameApp.endShapeRendering();
    }
}
