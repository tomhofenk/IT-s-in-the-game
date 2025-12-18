package nl.saxion.game.Bloodspire.Classes;

public class Player {
    private static int hitpoints = 100;
    private static int attackSpeed = 1;
    private static int attackDamage = 1;
    private static int defense = 0;
    private static int experience = 0;
    private static int level = 1;



    public int getHitpoints() {
        return hitpoints;
    }

    public void setHitpoints(int hitpoints) {
        Player.hitpoints = hitpoints;
    }

    public int getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(int attackSpeed) {
        Player.attackSpeed = attackSpeed;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        Player.attackDamage = attackDamage;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        Player.defense = defense;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        Player.experience = experience;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        Player.level = level;
    }

    @Override
    public String toString() {
        return "HP: " + hitpoints + "\nAttack Damage: " + attackDamage + "\nAttack speed: " + attackSpeed
                + "\nDefense: " + defense + "\nLevel: " + level + "\nExperience: " + experience;
    }
}
