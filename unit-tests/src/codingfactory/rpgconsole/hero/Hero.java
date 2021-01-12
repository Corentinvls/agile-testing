package codingfactory.rpgconsole.hero;

import codingfactory.rpgconsole.enemy.Enemy;
import java.util.Random;

public class Hero {

    private final String name;
    private int level;
    private int hp;
    private int damage;

    public Hero(String name){ //construct
        this.name = name;
        this.level = 1;
        this.hp = 20;
        this.damage = 2;
    }

    public String getName() {
       return this.name;
    }

    public int getHp(){
        return this.hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getDamage(){
        return this.damage;
    }

    private void setDamage(int damage) {
        this.damage = damage;
    }

    public int getLevel(){
        return this.level;
    }

    private void setLevel(int newLevel) {
        this.level = newLevel;
    }

    public void takeDamage(int damage){
        this.hp -= damage;
        if (this.hp < 0) {
            this.setHp(0);
        }
    }

    public void attack(Enemy enemy) {
        Random rand = new Random();
        int randomNum = rand.nextInt(this.level + 1);
        enemy.takeDamage(this.damage + randomNum); //enemy take damage for hero attack
    }

    public void levelUp() {
        this.setLevel(this.level + 1);
        this.setHp(20 * this.level);
        this.setDamage(2 * this.level);
    }
}
