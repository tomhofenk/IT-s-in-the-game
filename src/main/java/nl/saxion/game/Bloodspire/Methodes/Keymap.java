package nl.saxion.game.Bloodspire.Methodes;

import com.badlogic.gdx.Input;
import java.util.HashMap;
import java.util.Map;

public class Keymap {
    public static final Map<String, Integer> keyMap = new HashMap<>();

    static {
        keyMap.put("W", 51);
        keyMap.put("A", 29);
        keyMap.put("S", 47);
        keyMap.put("D", 32);
    }
}
