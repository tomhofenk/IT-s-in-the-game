package nl.saxion.game.Bloodspire.Classes;

public class Player {
    private static double hitpoints = 100;
    private static double attackSpeed = 1;
    private static double attackDamage = 1;
    private static double defense = 0;
    private static double experience = 0;
    private static int level = 1;



    public double getHitpoints() {
        return hitpoints;
    }

    public void setHitpoints(double hitpoints) {
        this.hitpoints = hitpoints;
    }

    public double getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(double attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public double getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(double attackDamage) {
        this.attackDamage = attackDamage;
    }

    public double getDefense() {
        return defense;
    }

    public void setDefense(double defense) {
        this.defense = defense;
    }

    public double getExperience() {
        return experience;
    }

    public void setExperience(double experience) {
        this.experience = experience;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "HP: " + hitpoints + "\nAttack Damage: " + attackDamage + "\nAttack speed: " + attackSpeed
                + "\nDefense: " + defense + "\nLevel: " + level + "\nExperience: " + experience;
    }
}
