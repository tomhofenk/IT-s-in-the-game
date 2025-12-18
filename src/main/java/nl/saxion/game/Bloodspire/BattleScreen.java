package nl.saxion.game.Bloodspire;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import nl.saxion.game.Bloodspire.Classes.Enemy;
import nl.saxion.game.Bloodspire.Classes.EnemyData;
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
    private int playerHitPoints;
    private int maxPlayerHitPoints = 0;
    private int maxEnemyHitPoints = 0;
    private int enenenasemyHitPoints = 0;
    private boolean inGevecht = true;
    private boolean klaar = false;
    private float battleTimer = 0f;       // telt seconden op
    private float attackIntervalPlayer;// elke 2 seconden
    private float attackIntervalEnemy;
    private boolean battleStarted = false;
    int frameCounter = 0;


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
        System.out.println(enemyTileData.enemyID + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        currentEnemy = getEnemyStats2();
        System.out.println(currentEnemy.enemyID + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        // Alleen bij de eerste keer initialiseren, niet elke keer resetten
        if (maxPlayerHitPoints != mainPlayer.getHitpoints()) {
            maxPlayerHitPoints = mainPlayer.getHitpoints();
        }
        playerHitPoints = mainPlayer.getHitpoints();
        maxEnemyHitPoints = currentEnemy.hitPoints;
        inGevecht = true;
        klaar = false;
        battleStarted = false;
        battleTimer = 0f;
        GameApp.addFont("Basic", "fonts/basic.ttf", 2);
        //EnemyData.EnemyArraylist

        attackIntervalPlayer = (GameApp.getFramesPerSecond()*2)*((100+mainPlayer.getAttackSpeed())/100);
        attackIntervalEnemy = (GameApp.getFramesPerSecond()*2)*((100+currentEnemy.attackSpeed)/100);
        enenenasemyHitPoints = currentEnemy.hitPoints;

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        frameCounter++;
        GameApp.clearScreen();

        renderEnemyAndCharacter();
        renderHpBars();

        enemySideFight();
        playerSideFight();



        // ESC om terug naar menu
        if (GameApp.isKeyJustPressed(Input.Keys.ESCAPE)) {
            GameApp.switchScreen("MainMenuScreen");
        }
    }

    @Override
    public void hide() {
        //mainPlayer.setHitpoints(maxPlayerHitPoints);
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
        GameApp.drawRect(105, 75, (int)(50 * (enenenasemyHitPoints / (float)maxEnemyHitPoints)), 10, Color.GREEN);
        GameApp.drawRect(5, 75, (50*((float) playerHitPoints /maxPlayerHitPoints)), 10, Color.GREEN);
        //GameApp.drawRect(105, 75, (int)(50*(currentEnemy.hitPoints/maxEnemyHitPoints)), 10, Color.GREEN);
        //GameApp.drawRect(105, 75, 50, 10, Color.GREEN);
        GameApp.endShapeRendering();
    }

    private Enemy getEnemyStats() {
        for (Tile t : mv.mapData) {
            if (t.gridX == lv.getOldX() && t.gridY == lv.getOldY() && t.tileType.equalsIgnoreCase("Enemy")) {
                for (Enemy ce : EnemyData.EnemyArraylist) {
                    if (t.enemyID == ce.enemyID) {
                        // Maak een nieuwe kopie van de enemy
                        Enemy copy = new Enemy();
                        copy.enemyID = ce.enemyID;
                        copy.hitPoints = ce.hitPoints;
                        copy.attackSpeed = ce.attackSpeed;
                        copy.attackDamage = ce.attackDamage;
                        copy.defense = ce.defense;
                        copy.experiencePoints = ce.experiencePoints;
                        copy.texture = ce.texture;
                        return copy;
                    }
                }
            }
        }
        return null;
    }

    private Enemy getEnemyStats2() {
        for (Enemy ce : EnemyData.EnemyArraylist) {
            if (ce.enemyID == enemyTileData.enemyID) {
                return ce;
            }
        }
        return null;
    }

    private void AttackEnemy(){
        if (inGevecht) {
            // Damage berekening: Player attackDamage - Enemy defense
            double rawDamage = mainPlayer.getAttackDamage() - currentEnemy.defense;

            // Zorg dat damage nooit negatief wordt
            int damage = (int)Math.max(1, rawDamage);
            // bijvoorbeeld
            currentEnemy.hitPoints -= damage;
            Tile toRemove = null;

            for (Tile t : mv.mapData) {
                if (t.gridX == lv.getOldX() && t.gridY == lv.getOldY() && t.tileType.equalsIgnoreCase("Enemy")) {
                    toRemove = t;
                    break;
                }
            }
            if (playerHitPoints <= 0) {
                GameApp.switchScreen("MainMenuScreen");
                mainPlayer.setHitpoints(100);
            }

            if (currentEnemy.hitPoints <= 0) {
                klaar = true;
                System.out.println("Enemy defeated!");
               // mainPlayer.setHitpoints(100);

                // eventueel terug naar level screen
                if (klaar) {
                    mv.mapData.remove(toRemove);
                    //System.out.println("Tile verwijderd & blijft weg zolang app draait.");
                    GameApp.switchScreen("MyLevelScreen");
                }
            }
        }
    }

    private void enemySideFight() {
        if (frameCounter % attackIntervalEnemy == 0) {
            // Enemy valt player aan
            int rawDamageToPlayer = currentEnemy.attackDamage - mainPlayer.getDefense();
            int damageToPlayer = Math.max(1, rawDamageToPlayer);
            playerHitPoints -= damageToPlayer;
            mainPlayer.setHitpoints(playerHitPoints);
            // Check einde gevecht

            if (playerHitPoints <= 0) {
                inGevecht = false;
                System.out.println("Player defeated!");
                GameApp.switchScreen("MainMenuScreen");
            }
        }
    }
    private void playerSideFight() {
        if (frameCounter % attackIntervalPlayer == 0) {
            System.out.println(mv.framesCounter);
            // Player valt enemy aan
            int rawDamageToEnemy = mainPlayer.getAttackDamage() - currentEnemy.defense;
            int damageToEnemy = Math.max(0, rawDamageToEnemy);
            enenenasemyHitPoints -= damageToEnemy;

            if (enenenasemyHitPoints <= 0) {
                inGevecht = false;
                System.out.println("Enemy defeated!");
                Tile toRemove = null;
                for (Tile t : mv.mapData) {
                    if (t.gridX == lv.getOldX() && t.gridY == lv.getOldY() && t.tileType.equalsIgnoreCase("Enemy")) {
                        toRemove = t;
                        break;
                    }
                }
                if (toRemove != null) {
                    mv.mapData.remove(toRemove);
                }
                GameApp.switchScreen("MyLevelScreen");
            }

        }
    }
}

