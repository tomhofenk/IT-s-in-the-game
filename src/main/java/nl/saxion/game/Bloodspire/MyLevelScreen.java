package nl.saxion.game.Bloodspire;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import nl.saxion.game.Bloodspire.Classes.Player;
import nl.saxion.game.Bloodspire.Classes.Tile;
import nl.saxion.game.Bloodspire.Methodes.CsvLoader;
import nl.saxion.game.Bloodspire.Methodes.LevelVars;
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
    public LevelVars lv = new LevelVars();
    private CsvLoader csvLoader;
    private int framesCounter = 0;
    public static Player mainPlayer = new Player();


    public MyLevelScreen(int viewportWidth, int viewportHeight, int worldWidth, int worldHeight) {
        super(viewportWidth, viewportHeight, worldWidth, worldHeight);
    }


    @Override
    public void show() {
        //items inladen als dit nog niet gedaan is, en het equippen van de starterkit.
        InventoryScreen.inventory.loadItems();
        enableHUD(160, 90);

        // startpositie (in pixels) — hier 0,0 maar je kunt dit veranderen
        int startX = 11;
        int startY = 11;
        //lv  = new LevelVars();

        mv = new MovementVars(
                startX*64,
                startY*64,
                (int)getWorldHeight(),
                (int)getWorldWidth(),
                (int)getMouseX(),
                (int)getMouseY(),
                GameApp.getFramesPerSecond() / 3,
                csvLoader.loadCsv("src/main/java/nl/saxion/game/Bloodspire/csv/Level1Tile.csv")
        );

        methodes = new Methodes();
        methodes.getOldCords(mv, lv, startX, startY);

        // camera direct naar de speler
        setCameraTargetInstantly(mv.playerWorldX, mv.playerWorldY);

        methodes.addAllTextures();

    }


    @Override
    public void render(float delta) {
        updateMV();
        methodes.gameLogic(mv);

        // camera volgen
        setCameraTarget(mv.playerWorldX, mv.playerWorldY);

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


//    public class CsvLoader {
//
//        public static ArrayList<Tile> loadCsv(String path) {
//            ArrayList<Tile> tiles = new ArrayList<>();
//
//            try (BufferedReader br = new BufferedReader(new FileReader(path))) {
//                String line;
//                br.readLine(); // Skip de headerregel
//
//                while ((line = br.readLine()) != null) {
//                    String[] columns = line.split(",");  // Split de lijn op komma's
//                    try {
//                        // Parse de waarden uit de CSV
//                        int tileID = Integer.parseInt(columns[0].trim());
//                        int worldX = Integer.parseInt(columns[1].trim());
//                        int worldY = Integer.parseInt(columns[2].trim());
//                        int gridX = Integer.parseInt(columns[3].trim());
//                        int gridY = Integer.parseInt(columns[4].trim());
//                        boolean hasEnemy = columns[5].trim().equals("true");
//                        String enemyID = columns[6].trim().equals("\\N") ? null : columns[6].trim();
//                        String tileType = columns[7].trim();  // Nieuwe kolom tileType
//                        boolean walkable = columns[8].trim().equals("true");
//
//                        // Maak een Tile object van de CSV-gegevens
//                        Tile tile = new Tile(tileID, worldX, worldY, gridX, gridY, hasEnemy, enemyID, tileType, walkable);
//                        tiles.add(tile);
//                    } catch (NumberFormatException e) {
//                        System.err.println("Fout bij het parsen van de CSV-regel: " + line);
//                    }
//                }
//
//            } catch (IOException e) {
//                System.err.println("Fout bij het lezen van bestand: " + path);
//                e.printStackTrace();
//            }
//
//            return tiles;
//        }
//    }
}

