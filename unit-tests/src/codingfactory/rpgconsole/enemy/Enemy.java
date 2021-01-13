package codingfactory.rpgconsole.enemy;

import codingfactory.rpgconsole.hero.Hero;
import java.util.Random;

public class Enemy {

    private final String name;
    private int level;
    private int hp;
    private int damage;

    public Enemy(String name, int level){ //construct
        this.name = name;
        this.level = level;
        this.hp = 15 * level;
        this.damage = level;
    }

    public String getName(){
        return this.name;
    }

    public int getHp(){
        return this.hp;
    }

    public void setHp(int hp){
        this.hp = hp;
    }

    public int getLevel() { return this.level; }

    public void setLevel(int level){
        this.level = level;
    }

    public int getDamage(){
        return this.damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void levelUp() {
        this.setLevel(this.level + 1);
        this.setHp(15 * this.level);
        this.setDamage(this.level);
    }

    public void takeDamage(int damage) {
        this.hp -= damage;
        if (this.hp < 0) {
            this.setHp(0);
        }
    }

    public void attack(Hero hero) {
        Random rand = new Random();
        int randomNum = rand.nextInt(this.level + 1);
        hero.takeDamage(this.damage + randomNum); //hero take damage for enemy attack
    }
}
