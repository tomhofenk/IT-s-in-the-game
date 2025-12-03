package nl.saxion.game.Bloodspire;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import nl.saxion.game.Bloodspire.Classes.Tile;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.CameraControlledGameScreen;
import nl.saxion.gameapp.screens.GameScreenWithHUD;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class MyLevelScreen extends CameraControlledGameScreen {
    private float playerX, playerY;
    int pixelPerGridTile = 64; // Same value as in Main.java
    int minTimeBetweenMovement = 0;
    int playerTileX = 0;
    int playerTileY = 0;
    int framesCounter = 0;
    int framesWIsPressed = 0;
    int framesAIsPressed = 0;
    int framesSIsPressed = 0;
    int framesDIsPressed = 0;
    int framesWhenWWasPressed = 0;
    int framesWhenAWasPressed = 0;
    int framesWhenSWAasPressed = 0;
    int framesWhenDWAasPressed = 0;
    boolean hasWBeenPressedOnce = false;
    boolean hasABeenPressed = false;
    boolean hasSBeenPressed = false;
    boolean hasDBeenPressed = false;
    int framesMouseIsPressed = 0;
    int framesWhenMouseWasPressed = 0;
    boolean hasMouseBeenPressed = false;
    public ArrayList<Tile> mapData;

    public MyLevelScreen(int viewportWidth, int viewportHeight, int worldWidth, int worldHeight) {
        // Define camera viewport (visible area) and world size
        // Example: viewport 16x9 is, world has size 100x50
        super(viewportWidth, viewportHeight, worldWidth, worldHeight);
    }


    @Override
    public void show() {
        enableHUD(160, 90);
        // Initialize your objects, e.g., player starting position
        playerX = 0;
        playerY = 0;
        mapData = CsvLoader.loadCsv("src/main/java/nl/saxion/game/Bloodspire/csv/Level1Tile.csv");

        // Start camera centered on the player
        setCameraTargetInstantly(playerX, playerY);
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
        // Handle your game logic and input
        gameLogic();

        // When you have moved the player, let the camera know (so it stays in the center of the screen).
        setCameraTarget(playerX, playerY);

        // ALWAYS CALL super.render(delta) AFTERWARDS!!!
        // This applies the camera settings to the shape renderer and sprite batch.
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
        framesCounter++;

        playerTileX = (int) playerX / pixelPerGridTile;
        playerTileY = (int) playerY / pixelPerGridTile;

        // Beweging van de speler
        minTimeBetweenMovement = GameApp.getFramesPerSecond() / 3;
        Movement();

        // Quit naar mainmenu
        if (GameApp.isKeyPressed(Input.Keys.ESCAPE)) {
            GameApp.switchScreen("MainMenuScreen");
        }

        // Print X en Y (Voor testen)
        if (GameApp.isKeyJustPressed(Input.Keys.P)) {
            // Controleer de mapData lijst en print informatie over de Tile objecten
            System.out.println("Aantal tiles in mapData: " + mapData.size());

            for (Tile tile : mapData) {
                System.out.println("Tile: " + tile);  // Dit gebruikt de toString() methode van de Tile class
            }
        }
    }


    public void renderWorld() {
        switchToWorldRendering();

        renderGridTiles(pixelPerGridTile);

        GameApp.startSpriteRendering();
        GameApp.drawTexture("CharacterTexture", playerX, playerY);
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
                // Draw each tile without filling so only the border
                GameApp.drawRect((x * pixelsPerGridTile), (y * pixelsPerGridTile), pixelsPerGridTile, pixelsPerGridTile, "stone-500");
            }
        }


        for (Tile tile : mapData) {
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

        // Teken de vijf tiles rondom de speler (witte rand)
        GameApp.drawRect(playerTileX * pixelsPerGridTile, playerTileY * pixelsPerGridTile, pixelsPerGridTile, pixelsPerGridTile, Color.WHITE);
        GameApp.drawRect((playerTileX - 1) * pixelsPerGridTile, playerTileY * pixelsPerGridTile, pixelsPerGridTile, pixelsPerGridTile, Color.WHITE);
        GameApp.drawRect((playerTileX + 1) * pixelsPerGridTile, playerTileY * pixelsPerGridTile, pixelsPerGridTile, pixelsPerGridTile, Color.WHITE);
        GameApp.drawRect(playerTileX * pixelsPerGridTile, (playerTileY - 1) * pixelsPerGridTile, pixelsPerGridTile, pixelsPerGridTile, Color.WHITE);
        GameApp.drawRect(playerTileX * pixelsPerGridTile, (playerTileY + 1) * pixelsPerGridTile, pixelsPerGridTile, pixelsPerGridTile, Color.WHITE);

        GameApp.endShapeRendering();

        GameApp.startSpriteRendering();
        for (Tile tile : mapData) {
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


    private void Movement() {

        // per 60 : w3 frames dat je een knop vast hebt verplaats je (IPV elke keer opnieuw moeten klikken)
        // Laatste argument is om te zorgen dat je niet buiten de map kan
        if ((GameApp.isKeyPressed(Input.Keys.W) || GameApp.isKeyPressed(Input.Keys.UP))
                && playerTileY < (getWorldHeight() / pixelPerGridTile - 1)) {
            if (framesWIsPressed % minTimeBetweenMovement == 0
                    && (framesWhenWWasPressed + GameApp.getFramesPerSecond() / 3 <= framesCounter || !hasWBeenPressedOnce)) {
                playerY += pixelPerGridTile;
                framesWhenWWasPressed = framesCounter;
            }
            framesWIsPressed++;
            hasWBeenPressedOnce = true;
        } else {
            framesWIsPressed = 0;
        }
        // A
        if ((GameApp.isKeyPressed(Input.Keys.A) || GameApp.isKeyPressed(Input.Keys.LEFT))
                && playerTileX > 0) {
            if (framesAIsPressed % minTimeBetweenMovement == 0
                    && (framesWhenAWasPressed + GameApp.getFramesPerSecond() / 3 <= framesCounter || !hasABeenPressed)) {
                playerX -= pixelPerGridTile;
                framesWhenAWasPressed = framesCounter;
            }
            framesAIsPressed++;
            hasABeenPressed = true;
        } else {
            framesAIsPressed = 0;
        }
        // S
        if ((GameApp.isKeyPressed(Input.Keys.S) || GameApp.isKeyPressed(Input.Keys.DOWN))
                && playerTileY > 0) {
            if (framesSIsPressed % minTimeBetweenMovement == 0
                    && (framesWhenSWAasPressed + GameApp.getFramesPerSecond() / 3 <= framesCounter || !hasSBeenPressed)) {
                playerY -= pixelPerGridTile;
                framesWhenSWAasPressed = framesCounter;
            }
            framesSIsPressed++;
            hasSBeenPressed = true;
        } else {
            framesSIsPressed = 0;
        }
        // D
        if ((GameApp.isKeyPressed(Input.Keys.D) || GameApp.isKeyPressed(Input.Keys.RIGHT))
                && playerTileX < (getWorldWidth() / pixelPerGridTile - 1)) {
            if (framesDIsPressed % minTimeBetweenMovement == 0
                    && (framesWhenDWAasPressed + GameApp.getFramesPerSecond() / 3 <= framesCounter || !hasDBeenPressed)) {
                playerX += pixelPerGridTile;
                framesWhenDWAasPressed = framesCounter;
            }
            framesDIsPressed++;
            hasDBeenPressed = true;
        } else {
            framesDIsPressed = 0;
        }
        // Mouse left click
        if (GameApp.isButtonJustPressed(Input.Buttons.LEFT)) {
            if (framesMouseIsPressed % minTimeBetweenMovement == 0
                    && (framesWhenMouseWasPressed + minTimeBetweenMovement <= framesCounter || !hasMouseBeenPressed)) {

                int mouseTileX = (int) (getMouseX() / pixelPerGridTile);
                int mouseTileY = (int) (getMouseY() / pixelPerGridTile);

                // Alleen bewegen als muis naast of gelijk aan speler staat
                if (mouseTileX >= playerTileX - 1 && mouseTileX <= playerTileX + 1 && mouseTileY == playerTileY) {
                    playerX = mouseTileX * pixelPerGridTile;
                    playerY = mouseTileY * pixelPerGridTile;
                } else if (mouseTileY >= playerTileY - 1 && mouseTileY <= playerTileY + 1 && mouseTileX == playerTileX) {
                    playerX = mouseTileX * pixelPerGridTile;
                    playerY = mouseTileY * pixelPerGridTile;
                }

                framesWhenMouseWasPressed = framesCounter;
            }
            framesMouseIsPressed++;
            hasMouseBeenPressed = true;
        } else {
            framesMouseIsPressed = 0;
        }
    }
    /*
    public class CsvLoader {

        // Leest een CSV-bestand en geeft elke regel terug als array van kolommen
        public static ArrayList<String[]> loadCsv(String path) {
            ArrayList<String[]> rows = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(new FileReader(path))) {
                String line;
                br.readLine();

                while ((line = br.readLine()) != null) {
                    String[] columns = line.split(",");  // split op komma
                    rows.add(columns);
                }

            } catch (IOException e) {
                System.err.println("Bestand niet gevonden: " + path);
            }

            return rows;
        }
    } */

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