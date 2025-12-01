package nl.saxion.game.Bloodspire.Classes;

import nl.saxion.game.Bloodspire.Classes.gear.Helmet;

import java.util.ArrayList;
import java.util.List;

//class nog niet zeker of hij gebruikt kan worden, maar in ieder geval
//een start richtlijn van de stats die de speler heeft
public class Player {
    double hitpoints = 100;
    double attackSpeed = 1;
    double attackDamage = 1;
    double defense = 0;
    double experience = 0;
    int level = 1;

    List[] equipped = new List[7];
    //TODO slots voor gear maken


    @Override
    public String toString() {
        return "HP: " + hitpoints + "\n Attack Damage: " + attackDamage;
    }
}
