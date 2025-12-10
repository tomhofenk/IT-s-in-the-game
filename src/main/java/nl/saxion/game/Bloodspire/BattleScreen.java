package nl.saxion.game.Bloodspire;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import nl.saxion.game.Bloodspire.Classes.Enemy;
import nl.saxion.game.Bloodspire.Classes.Player;
import nl.saxion.game.Bloodspire.Classes.Tile;
import nl.saxion.game.Bloodspire.Methodes.*;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;

public class BattleScreen extends ScalableGameScreen {

    private MovementVars mv;
    public LevelVars lv = new LevelVars();
    public Player mainPlayer = new Player();
    private Methodes methodes;
    private Tile enemyTileData = null;
    private Enemy currentEnemy = new Enemy();
    private int maxHitPoints = 0;

    public BattleScreen() {
        super(160, 90);
    }

    @Override
    public void show() {
        mv = new MovementVars(0,0,0,0,0,0,0,
                MapData.getLevel(1)
        );
        methodes = new Methodes();
        methodes.addAllTextures();
        enemyTileData = getEnemeyData();
        maxHitPoints = (int)mainPlayer.getHitpoints();
        GameApp.addFont("Basic", "fonts/basic.ttf", 2);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        GameApp.clearScreen();

        renderEnemyAndCharacter();
        renderHpBars();

        if (GameApp.isKeyJustPressed(Input.Keys.SPACE)) {
            mainPlayer.setHitpoints(mainPlayer.getHitpoints() - 5);
        }

        // Kill the enemy and return to MyLevelScreen (For Testing)
        if (GameApp.isKeyJustPressed(Input.Keys.K)) {
            Tile toRemove = null;

            for (Tile t : mv.mapData) {
                if (t.gridX == lv.getOldX() && t.gridY == lv.getOldY() && t.tileType.equalsIgnoreCase("Enemy")) {
                    toRemove = t;
                    break;
                }
            }

            if (toRemove != null) {
                mv.mapData.remove(toRemove);
                System.out.println("Tile verwijderd & blijft weg zolang app draait.");
                GameApp.switchScreen("MyLevelScreen");
            }

        }

        //terug naar main menu
        if (GameApp.isKeyJustPressed(Input.Keys.ESCAPE)) {
            GameApp.switchScreen("MainMenuScreen");
        }
    }

    @Override
    public void hide() {
        methodes.disposeAllTextures();
    }

    private Tile getEnemeyData() {
        for (Tile t : mv.mapData) {
            if (t.gridX == lv.getOldX() && t.gridY == lv.getOldY() && t.tileType.equalsIgnoreCase("Enemy")) {
                return t;
            }
        }
        return null;
    }

    private void renderEnemyAndCharacter() {
        GameApp.startSpriteRendering();
        GameApp.drawTexture("CharacterTexture", 5, 5);
        GameApp.drawTexture(enemyTileData.tileType, getWorldWidth()/2+25, 5, 64, 64, 0, true, false);
        GameApp.drawTextCentered("Basic", "VS", 90, 45, Color.RED);
        GameApp.endSpriteRendering();
    }

    private void renderHpBars() {
        GameApp.startShapeRenderingOutlined();
        GameApp.drawRect(4,74,52,12, Color.WHITE);
        GameApp.drawRect(104,74,52,12, Color.WHITE);
        GameApp.endShapeRendering();
        GameApp.startShapeRenderingFilled();
        GameApp.drawRect(5, 75, (int)(50*(mainPlayer.getHitpoints()/maxHitPoints)), 10, Color.GREEN);
        GameApp.drawRect(105, 75, 50, 10, Color.GREEN);
        GameApp.endShapeRendering();
    }
}

