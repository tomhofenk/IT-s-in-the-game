package nl.saxion.game.Bloodspire;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import nl.saxion.game.Bloodspire.Classes.Inventory;
import nl.saxion.game.Bloodspire.Classes.Tile;
import nl.saxion.game.Bloodspire.Methodes.MovementVars;
import nl.saxion.game.Bloodspire.Methodes.Methodes;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.CameraControlledGameScreen;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class MyLevelScreen extends CameraControlledGameScreen {

    private Methodes methodes;
    private MovementVars mv;
    private int framesCounter = 0;


    public MyLevelScreen(int viewportWidth, int viewportHeight, int worldWidth, int worldHeight) {
        super(viewportWidth, viewportHeight, worldWidth, worldHeight);
    }


    @Override
    public void show() {
        enableHUD(160, 90);
        //mapData = CsvLoader.loadCsv("src/main/java/nl/saxion/game/Bloodspire/csv/Level1Tile.csv");

        // startpositie (in pixels) — hier 0,0 maar je kunt dit veranderen
        int startX = 11;
        int startY = 11;

        mv = new MovementVars(
                startX*64,
                startY*64,
                (int)getWorldHeight(),
                (int)getWorldWidth(),
                (int)getMouseX(),
                (int)getMouseY(),
                GameApp.getFramesPerSecond() / 3,
                CsvLoader.loadCsv("src/main/java/nl/saxion/game/Bloodspire/csv/Level1Tile.csv")
        );

        methodes = new Methodes();

        // camera direct naar de speler
        setCameraTargetInstantly(mv.playerWorldX, mv.playerWorldY);

        GameApp.addTexture("CharacterTexture", "textures/DungeonCharacterpng.png");
        GameApp.addTexture("TileTexture", "textures/DungeonCharacter.png");
        GameApp.addTexture("Black", "textures/Black.png");
        GameApp.addTexture("BlackGrid", "textures/BlackGrid.png");
        GameApp.addTexture("BlackHighlight", "textures/BlackHighlight.png");
        GameApp.addTexture("HUDShadow", "textures/HUDShadow.png");
        // Objecten
        GameApp.addTexture("WallOpenSide", "textures/Wall20.png");
        GameApp.addTexture("WallLeftSide", "textures/Wall21.png");
        GameApp.addTexture("WallRightSide", "textures/Wall22.png");
        GameApp.addTexture("WallBothSide", "textures/Wall23.png");

    }


    @Override
    public void render(float delta) {
        gameLogic();

        // camera volgen
        setCameraTarget(mv.playerWorldX, mv.playerWorldY);

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
        // verhoog lokale frame counter en geef door aan mv
        framesCounter++;
        mv.framesCounter = framesCounter;
        mv.minTimeBetweenMovement = GameApp.getFramesPerSecond() / 3;

        // update input-based waarden in mv
        mv.mouseX = (int)getMouseX();
        mv.mouseY = (int)getMouseY();

        // tile coords blijven consistent (kan ook in Methodes, maar we houden hem hier up-to-date vóór movement)
        mv.playerTileX = mv.playerWorldX / mv.pixelPerGridTile;
        mv.playerTileY = mv.playerWorldY / mv.pixelPerGridTile;

        // roep de gedeelde movement aan
        methodes.Movement(mv);

        // optioneel: debug print
        if (GameApp.isKeyJustPressed(Input.Keys.P)) {
            System.out.println("tile: " + mv.playerTileX + "x " + mv.playerTileY
                    + "y world: " + mv.playerWorldX + "x " + mv.playerWorldY + "y");
        }

        // escape -> main menu
        if (GameApp.isKeyPressed(Input.Keys.ESCAPE)) {
            GameApp.switchScreen("MainMenuScreen");
        }

        //Inventory scherm openen //TODO als je terugswitched wordt je bij het begin geplaatst, manier maken om plek te onthouden.
        if (GameApp.isKeyJustPressed(Input.Keys.I)) {
            GameApp.switchScreen("InventoryScreen");
        }
    }

    public void renderWorld() {
        switchToWorldRendering();

        renderGridTiles();

        GameApp.startSpriteRendering();
        GameApp.drawTexture("CharacterTexture", mv.playerWorldX, mv.playerWorldY);
        GameApp.endSpriteRendering();

    }

    public void renderHUD() {
        switchToHudRendering();

        drawShadows();
    }

    public void renderGridTiles() {
        switchToWorldRendering();

        drawGrid();
        drawHighlightedTiles();
        renderTextures();

    }

    private void renderTextures() {
        switchToWorldRendering();
        boolean rightSideIsWall;
        boolean leftSideIsWall;

        GameApp.startSpriteRendering();
        for (Tile tile : mv.mapData) {
            //String textureName = getTextureForTileType(tile.tileType);
            rightSideIsWall = false;
            leftSideIsWall = false;
            if (tile.tileType.equals("Wall")) {
                //GameApp.drawTexture(textureName, tile.worldX, tile.worldY, mv.pixelPerGridTile, mv.pixelPerGridTile);
                for (Tile tile2 : mv.mapData) {
                    if ((tile2.gridX-1 == tile.gridX && tile2.gridY == tile.gridY) && tile2.tileType.equalsIgnoreCase("Wall")) {
                        leftSideIsWall = true;
                    }
                    if ((tile2.gridX+1 == tile.gridX && tile2.gridY == tile.gridY) && tile2.tileType.equalsIgnoreCase("Wall")) {
                        rightSideIsWall = true;
                    }
                }
                if (leftSideIsWall && rightSideIsWall) {
                    GameApp.drawTexture("WallOpenSide", tile.worldX, tile.worldY, mv.pixelPerGridTile, mv.pixelPerGridTile);
                } else if (!leftSideIsWall && rightSideIsWall) {
                    GameApp.drawTexture("WallRightSide", tile.worldX, tile.worldY, mv.pixelPerGridTile, mv.pixelPerGridTile);
                } else if (leftSideIsWall && !rightSideIsWall) {
                    GameApp.drawTexture("WallLeftSide", tile.worldX, tile.worldY, mv.pixelPerGridTile, mv.pixelPerGridTile);
                } else {
                    GameApp.drawTexture("WallBothSide", tile.worldX, tile.worldY, mv.pixelPerGridTile, mv.pixelPerGridTile);
                }

            } else {
                GameApp.drawTexture(tile.tileType, tile.worldX, tile.worldY, mv.pixelPerGridTile, mv.pixelPerGridTile);

            }

        }
        GameApp.endSpriteRendering();
    }

    private void drawShadows() {
        switchToHudRendering();
        GameApp.enableTransparency();
        GameApp.startSpriteRendering();
        GameApp.drawTexture("HUDShadow", 0, 0);
        GameApp.endSpriteRendering();
        GameApp.disableTransparency();
    }

    private void drawGrid() {
        switchToWorldRendering();
        GameApp.startSpriteRendering();
        for (int y = 0; y < getWorldHeight() / mv.pixelPerGridTile; y++) {
            for (int x = 0; x < getWorldWidth() / mv.pixelPerGridTile; x++) {
                GameApp.drawTexture("BlackGrid",x * mv.pixelPerGridTile, y * mv.pixelPerGridTile, mv.pixelPerGridTile, mv.pixelPerGridTile);
            }
        }
        GameApp.endSpriteRendering();
    }

    private void drawHighlightedTiles() {
        switchToWorldRendering();
        int tx = mv.playerTileX;
        int ty = mv.playerTileY;

        GameApp.startSpriteRendering();
        GameApp.drawTexture("BlackHighLight", tx * mv.pixelPerGridTile, ty * mv.pixelPerGridTile, mv.pixelPerGridTile, mv.pixelPerGridTile);
        GameApp.drawTexture("BlackHighLight", (tx - 1) * mv.pixelPerGridTile, ty * mv.pixelPerGridTile, mv.pixelPerGridTile, mv.pixelPerGridTile);
        GameApp.drawTexture("BlackHighLight", (tx + 1) * mv.pixelPerGridTile, ty * mv.pixelPerGridTile, mv.pixelPerGridTile, mv.pixelPerGridTile);
        GameApp.drawTexture("BlackHighLight", tx * mv.pixelPerGridTile, (ty - 1) * mv.pixelPerGridTile, mv.pixelPerGridTile, mv.pixelPerGridTile);
        GameApp.drawTexture("BlackHighLight", tx * mv.pixelPerGridTile, (ty + 1) * mv.pixelPerGridTile, mv.pixelPerGridTile, mv.pixelPerGridTile);
        GameApp.endSpriteRendering();
    }

    public class CsvLoader {

        public static ArrayList<Tile> loadCsv(String path) {
            ArrayList<Tile> tiles = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(new FileReader(path))) {
                String line;
                br.readLine(); // Skip de headerregel

                while ((line = br.readLine()) != null) {
                    String[] columns = line.split(",");  // Split de lijn op komma's
                    try {
                        // Parse de waarden uit de CSV
                        int tileID = Integer.parseInt(columns[0].trim());
                        int worldX = Integer.parseInt(columns[1].trim());
                        int worldY = Integer.parseInt(columns[2].trim());
                        int gridX = Integer.parseInt(columns[3].trim());
                        int gridY = Integer.parseInt(columns[4].trim());
                        boolean hasEnemy = columns[5].trim().equals("true");
                        String enemyID = columns[6].trim().equals("\\N") ? null : columns[6].trim();
                        String tileType = columns[7].trim();  // Nieuwe kolom tileType
                        boolean walkable = columns[8].trim().equals("true");

                        // Maak een Tile object van de CSV-gegevens
                        Tile tile = new Tile(tileID, worldX, worldY, gridX, gridY, hasEnemy, enemyID, tileType, walkable);
                        tiles.add(tile);
                    } catch (NumberFormatException e) {
                        System.err.println("Fout bij het parsen van de CSV-regel: " + line);
                    }
                }

            } catch (IOException e) {
                System.err.println("Fout bij het lezen van bestand: " + path);
                e.printStackTrace();
            }

            return tiles;
        }
    }
}

