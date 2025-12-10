package nl.saxion.game.Bloodspire;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;

import java.util.ArrayList;

public class DrawMapScreen extends ScalableGameScreen {
    //String randomBoxColor = "violet-500";
    String randomBackgroundColor = "black";
    //Box centeredBox = new Box();
    ArrayList<DrawnTile> DrawnTiles = new ArrayList<>();
    Color selecterColor = new Color();
    int Size = 1;
    int mouseX = 0;
    int mouseY = 0;

    public DrawMapScreen() {
        super(114, 64);
    }

    @Override
    public void show() {
        for (int x = 0; x < 64; x++) {
            for (int y = 0; y < 64; y++) {
                DrawnTile newTile = new DrawnTile();
                newTile.x = x;
                newTile.y = y;
                newTile.color = Color.BLACK;
                DrawnTiles.add(newTile);
            }
        }
        printColors();
        printSizes();
        GameApp.addColor("GridColor", 255,255,255,102);
        GameApp.addFont("basic", "fonts/5x5_pixel.ttf", 1);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        mouseX = (int)getMouseX();
        mouseY = (int)getMouseY();

        getSelecterColor();
        getSelectedSize();

        if (GameApp.isButtonPressed(Input.Buttons.LEFT)) {
            int index = 0;
            DrawnTile replaceTile = new DrawnTile();
            if (Size == 1) {
                for (DrawnTile dt : DrawnTiles) {
                    replaceTile = DrawnTiles.get(index);
                    if (dt.x == mouseX && dt.y == mouseY) {
                        replaceTile.color = selecterColor;
                        //System.out.println(dt.color);
                        DrawnTiles.set(index, replaceTile);
                    }
                    index++;
                }
            }
            if (Size == 3) {
                for (DrawnTile dt : DrawnTiles) {
                    replaceTile = DrawnTiles.get(index);
                    if (dt.x >= mouseX-1 && dt.x <= mouseX+1 && dt.y >= mouseY-1 && dt.y <= mouseY+1) {
                        replaceTile.color = selecterColor;
                        //System.out.println(dt.color);
                        DrawnTiles.set(index, replaceTile);
                    }
                    index++;
                }
            }
            if (Size == 5) {
                for (DrawnTile dt : DrawnTiles) {
                    replaceTile = DrawnTiles.get(index);
                    if (dt.x >= mouseX-2 && dt.x <= mouseX+2 && dt.y >= mouseY-2 && dt.y <= mouseY+2) {
                        replaceTile.color = selecterColor;
                        //System.out.println(dt.color);
                        DrawnTiles.set(index, replaceTile);
                    }
                    index++;
                }
            }
        }

        if (GameApp.isKeyJustPressed(Input.Keys.S)) {
            System.out.println(DrawnTiles.size());
        }
        if (GameApp.isKeyJustPressed(Input.Keys.P)) {
            printData();
        }

        //terug naar main menu
        if (GameApp.isKeyJustPressed(Input.Keys.ESCAPE)) {
            GameApp.switchScreen("MainMenuScreen");
        }


        // Draw elements
        GameApp.clearScreen(Color.WHITE);
        renderTiles();
        renderGrid();
        renderHoverTiles();
        drawMenu();

    }

    @Override
    public void hide() {

    }

    private class DrawnTile {
        int x;
        int y;
        Color color;
    }

    private void renderTiles() {
        GameApp.startShapeRenderingFilled();
        for (DrawnTile dt : DrawnTiles) {
            GameApp.drawRect(dt.x,dt.y, 1, 1, dt.color);
        }
        GameApp.endShapeRendering();
    }

    private void printData() {
        int index = 0;
        for (DrawnTile dt : DrawnTiles) {
            if (dt.color != Color.BLACK) {
                if (dt.color == Color.RED) {
                    System.out.println(index + "," + dt.x*64 + "," + dt.y*64 + "," + dt.x + "," + dt.y + "," + "true" + "," + "1" + "," + "Enemy" + "," + "true");
                    index++;
                }
                if (dt.color == Color.GRAY) {
                    System.out.println(index + "," + dt.x*64 + "," + dt.y*64 + "," + dt.x + "," + dt.y + "," + "false" + "," + "\\N" + "," + "Wall" + "," + "false");
                    index++;
                }

            }
        }
    }

    private void printColors() {
        System.out.println("Black: 1");
        System.out.println("Gray: 2");
        System.out.println("Red: 3");
    }

    private void printSizes() {
        System.out.println("Size 1: NUMPAD1");
        System.out.println("Size 3: NUMPAD3");
        System.out.println("Size 5: NUMPAD5");
    }

    private void drawMenu() {
        GameApp.startShapeRenderingFilled();
        GameApp.drawRectCentered(68, 60, Size, Size, Color.BLACK);
        GameApp.drawRect(66, 3, 5,5, selecterColor);
        GameApp.drawRect(66, 11, 5,5, Color.BLACK);
        GameApp.drawRect(66, 19, 5,5, Color.GRAY);
        GameApp.drawRect(66, 27, 5,5, Color.RED);
        GameApp.endShapeRendering();
        GameApp.startSpriteRendering();
        GameApp.drawText("basic", "Size", 72, 58, Color.BLACK);
        GameApp.drawText("basic", "Selected", 72, 3, Color.BLACK);
        GameApp.drawText("basic", "1 Empty", 72, 11, Color.BLACK);
        GameApp.drawText("basic", "2 Wall", 72, 19, Color.BLACK);
        GameApp.drawText("basic", "3 Enemy", 72, 27, Color.BLACK);
        GameApp.endSpriteRendering();
    }

    private void getSelecterColor() {
        if (GameApp.isKeyJustPressed(Input.Keys.NUM_1)) {
            selecterColor = Color.BLACK;
        }
        if (GameApp.isKeyJustPressed(Input.Keys.NUM_2)) {
            selecterColor = Color.GRAY;
        }
        if (GameApp.isKeyJustPressed(Input.Keys.NUM_3)) {
            selecterColor = Color.RED;
        }
    }

    private void getSelectedSize() {
        if (GameApp.isKeyJustPressed(Input.Keys.NUMPAD_1)) {
            Size = 1;
        }
        if (GameApp.isKeyJustPressed(Input.Keys.NUMPAD_3)) {
            Size = 3;
        }
        if (GameApp.isKeyJustPressed(Input.Keys.NUMPAD_5)) {
            Size = 5;
        }
        if (GameApp.isKeyJustPressed(Input.Keys.PAGE_UP)) {
            Size += 2;
            if (Size >5) {
                Size = 1;
            }
        }
        if (GameApp.isKeyJustPressed(Input.Keys.PAGE_DOWN)) {
            Size -= 2;
            if (Size < 1) {
                Size = 5;
            }
        }
    }

    private void renderGrid() {
        GameApp.enableTransparency();
        GameApp.startShapeRenderingOutlined();
        GameApp.setLineWidth(1/15);
        for (int x = 0; x < 64; x++) {
            for (int y = 0; y < 64; y++) {
                GameApp.drawRect(x, y, 1, 1, "GridColor");

            }
        }
        GameApp.endShapeRendering();
        GameApp.disableTransparency();
    }

    private void renderHoverTiles() {
        GameApp.enableTransparency();
        GameApp.startShapeRenderingFilled();
        if (Size == 1) {
            for (DrawnTile dt : DrawnTiles) {
                if (dt.x == mouseX && dt.y == mouseY) {
                    GameApp.drawRect(dt.x,dt.y,1, 1, "GridColor");
                }

            }
        }
        if (Size == 3) {
            for (DrawnTile dt : DrawnTiles) {
                if (dt.x >= mouseX-1 && dt.x <= mouseX+1 && dt.y >= mouseY-1 && dt.y <= mouseY+1) {
                    GameApp.drawRect(dt.x,dt.y,1, 1, "GridColor");
                }
            }
        }
        if (Size == 5) {
            for (DrawnTile dt : DrawnTiles) {
                if (dt.x >= mouseX-2 && dt.x <= mouseX+2 && dt.y >= mouseY-2 && dt.y <= mouseY+2) {
                    GameApp.drawRect(dt.x,dt.y,1, 1, "GridColor");
                }
            }
        }
        GameApp.endShapeRendering();
        GameApp.disableTransparency();
    }
}
