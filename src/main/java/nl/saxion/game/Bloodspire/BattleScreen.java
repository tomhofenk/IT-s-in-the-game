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
    private Tile enemyTileData;
    private Enemy currentEnemy = new Enemy();
    private int playerHitPoints;
    private int maxPlayerHitPoints = 0;
    private int maxEnemyHitPoints = 0;
    private int enenenasemyHitPoints = 0;
    private int enemyXP;
    private boolean inGevecht = true;
    private boolean klaar = false;
    private float battleTimer = 0f;       // telt seconden op
    private float attackIntervalPlayer;// elke 2 seconden
    private float attackIntervalEnemy;
    private boolean battleStarted = false;
    int frameCounter = 0;
    int damageToPlayer = 0;


    public BattleScreen() {
        super(2560, 1600);
    }

    @Override
    public void show() {
        mv = new MovementVars(0,0,0,0,0,0,0,
                MapData.getLevel(LevelVars.getCurrentLevel())
        );
        methodes = new Methodes();
        methodes.addAllTextures();
        GameApp.addTexture("Enemy_gr", "textures/Enemy_gr.png");
        GameApp.addTexture("Jij_gr", "textures/Player_gr.png");
        GameApp.addTexture("BattleBG", "textures/bg_battle.png");
        GameApp.addTexture("Trans", "textures/HUDShadow.png");

        enemyTileData = getEnemeyData();
        assert enemyTileData != null;
        System.out.println(enemyTileData.enemyID + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        currentEnemy = getEnemyStats2();
        assert currentEnemy != null;
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
        GameApp.addFont("VS", "fonts/basic.ttf", 500);
        GameApp.addFont("DamagePopUp", "fonts/5x5_pixel.ttf", 400);
        //EnemyData.EnemyArraylist

        attackIntervalPlayer = (GameApp.getFramesPerSecond())*((100+mainPlayer.getAttackSpeed())/100);
        attackIntervalEnemy = (GameApp.getFramesPerSecond())*((100+currentEnemy.attackSpeed)/100);
        System.out.println("AIP: " + attackIntervalPlayer + " AIE: " + attackIntervalEnemy);
        enenenasemyHitPoints = currentEnemy.hitPoints;
        enemyXP = currentEnemy.experiencePoints;

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        frameCounter++;
        GameApp.clearScreen();
        GameApp.startSpriteRendering();
        GameApp.drawTexture("BattleBG", 0, 0, 2560, 1600);
        GameApp.endSpriteRendering();


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
        mainPlayer.setHitpoints(maxPlayerHitPoints);
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
        GameApp.drawTexture("Jij_gr", 270, 438, 640, 640);
        GameApp.drawTexture("Enemy_gr", getWorldWidth()/2+450, 600, 640, 530, 0, true, false);
        GameApp.drawTextCentered("VS", "VS", 1300, 800, Color.WHITE);
        GameApp.endSpriteRendering();
    }

    private void renderHpBars() {
        GameApp.startShapeRenderingOutlined();
        GameApp.drawRect(64,1311,832,213, Color.WHITE);
        GameApp.drawRect(1664,1311,832,213, Color.WHITE);
        GameApp.endShapeRendering();
        GameApp.startShapeRenderingFilled();
        GameApp.drawRect(1680, 1333, (int)(800 * ((float)enenenasemyHitPoints / maxEnemyHitPoints)), 178, Color.GREEN);
        GameApp.drawRect(80, 1333, (800*((float) playerHitPoints /(float)maxPlayerHitPoints)), 178, Color.GREEN);
        GameApp.endShapeRendering();
        GameApp.startSpriteRendering();
        String VisibleHPEnemy = enenenasemyHitPoints + "";
        GameApp.drawTextCentered("DamagePopUp", VisibleHPEnemy, 2080, 1422, Color.WHITE);
        String VisibleHPPLayer = playerHitPoints + "";
        GameApp.drawTextCentered("DamagePopUp", VisibleHPPLayer, 480, 1422, Color.WHITE);
        GameApp.endSpriteRendering();
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
            damageToPlayer = Math.max(1, rawDamageToPlayer);
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
                InventoryScreen.inventory.giveRandomItem(enemyXP);
                Tile toRemove = null;
                for (Tile t : mv.mapData) {
                    if (t.gridX == lv.getOldX() && t.gridY == lv.getOldY() && t.tileType.equalsIgnoreCase("Enemy")) {
                        toRemove = t;
                        break;
                    }
                }
                if (toRemove != null) {
                    mv.mapData.remove(toRemove);
                    System.out.println("DOEIDOEI ENEMY");
                }
                GameApp.switchScreen("MyLevelScreen");
            }

        }
    }
}

