package nl.saxion.game.Bloodspire;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import nl.saxion.game.Bloodspire.Classes.Inventory;
import nl.saxion.game.Bloodspire.Classes.Tile;
import nl.saxion.game.Bloodspire.Methodes.MovementVars;
import nl.saxion.game.Bloodspire.Methodes.Methodes;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.CameraControlledGameScreen;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class MyLevelScreen extends CameraControlledGameScreen {

    private Methodes methodes;
    private MovementVars movementVars;
    private int framesCounter = 0;


    //test purposes
    private Inventory inventory;



    public MyLevelScreen(int viewportWidth, int viewportHeight, int worldWidth, int worldHeight) {
        super(viewportWidth, viewportHeight, worldWidth, worldHeight);
    }


    @Override
    public void show() {
        enableHUD(160, 90);
        //mapData = CsvLoader.loadCsv("src/main/java/nl/saxion/game/Bloodspire/csv/Level1Tile.csv");

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
                GameApp.getFramesPerSecond() / 3,
                CsvLoader.loadCsv("src/main/java/nl/saxion/game/Bloodspire/csv/Level1Tile.csv")
        );

        methodes = new Methodes();

        // camera direct naar de speler
        setCameraTargetInstantly(movementVars.playerWorldX, movementVars.playerWorldY);

        GameApp.addTexture("CharacterTexture", "textures/DungeonCharacterpng.png");
        GameApp.addTexture("TileTexture", "textures/DungeonCharacter.png");
        // Objecten
        GameApp.addTexture("Stone", "textures/stone.png");
        GameApp.addTexture("Grass", "textures/grass.png");
        GameApp.addTexture("Water", "textures/water.png");
        GameApp.addTexture("Dirt", "textures/dirt.png");


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

        inventory = new Inventory();

        if (GameApp.isKeyJustPressed(Input.Keys.I)) {
            inventory.loadItems();
            inventory.addToInventory(1);
            inventory.showInventory();
            inventory.removeItems(0);
            inventory.showInventory();
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


        for (Tile tile : movementVars.mapData) {
            int x = tile.gridX * pixelsPerGridTile;
            int y = tile.gridY * pixelsPerGridTile;


            // Haal het juiste texture op basis van tileType
            String textureName = getTextureForTileType(tile.tileType);

            // Teken de tile met het juiste texture
//            if (textureName != null) {
//                GameApp.drawTexture(textureName, x, y, pixelsPerGridTile, pixelsPerGridTile);
//            } else {
//                // Als er geen texture is, teken de tegel met een kleur
//                Color tileColor = getTileColor(tile.tileType);  // Gebruik tileType voor kleur
//                GameApp.drawRect(x, y, pixelsPerGridTile, pixelsPerGridTile, tileColor);
//            }

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

        GameApp.startSpriteRendering();
        for (Tile tile : movementVars.mapData) {
            String textureName = getTextureForTileType(tile.tileType);

            if (textureName != null) {
                GameApp.drawTexture(textureName, tile.worldX, tile.worldY, pixelsPerGridTile, pixelsPerGridTile);
            } else {
                // fallback: kleur
                GameApp.drawRect(tile.worldX, tile.worldY, pixelsPerGridTile, pixelsPerGridTile, Color.GRAY);
            }
        }


        GameApp.endSpriteRendering();
    }

    // Methode die de juiste texture naam retourneert op basis van tileType
    private String getTextureForTileType(String tileType) {
        switch (tileType) {
            case "Stone":
                return "Stone";
            case "Water":
                return "Water";
            case "Grass":
                return "Grass";
            case "Dirt":
                return "Dirt";
            default:
                return null;  // Geen texture, gebruik kleur
        }
    }



    // Voeg deze methode toe aan de MyLevelScreen of de klasse waar je de tiles renderen
    private Color getTileColor(String tileType) {
        switch (tileType) {
            case "stone":
                return new Color(0.5f, 0.5f, 0.5f, 1f);  // Grijze kleur voor stenen tegels
            case "water":
                return new Color(0f, 0f, 1f, 1f);  // Blauw voor water
            case "grass":
                return new Color(0f, 1f, 0f, 1f);  // Groen voor gras
            case "dirt":
                return new Color(0.6f, 0.3f, 0f, 1f);  // Bruin voor aarde
            default:
                return new Color(1f, 1f, 1f, 1f);  // Wit als default
        }
    }

    private boolean canMoveTo(int gridX, int gridY) {

        for (Tile t : movementVars.mapData) {
            if (t.gridX == gridX && t.gridY == gridY) {
                return t.isWalkable();
            }
        }

        return false; // als tile niet bestaat → blokkeren
    }
   
    public class CsvLoader {

        public static ArrayList<Tile> loadCsv(String path) {
            ArrayList<Tile> tiles = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(new FileReader(path))) {
                String line;
                br.readLine(); // Skip de headerregel

                while ((line = br.readLine()) != null) {
                    String[] columns = line.split(",");  // Split de lijn op komma's

                    // Log de ruwe lijn om te controleren of de CSV juist wordt gelezen
                    System.out.println("Ruwe CSV lijn: " + line);

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

                        // Maak een Tile object van de CSV-gegevens
                        Tile tile = new Tile(tileID, worldX, worldY, gridX, gridY, hasEnemy, enemyID, tileType);
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

