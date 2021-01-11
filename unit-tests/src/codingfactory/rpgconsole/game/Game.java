package codingfactory.rpgconsole.game;
import codingfactory.rpgconsole.enemy.Enemy;
import codingfactory.rpgconsole.hero.Hero;

public class Game {

    private Boolean isGameOver;
    private final InputHandler inputHandler;

    /** CONSTRUCTOR METHOD - initialize variable gameOver */
    public Game() {
        isGameOver = false;
        inputHandler = new InputHandler();
    }

    public void gameLoop() {
        System.out.println("Game Loop.");

        Hero hero = createNewHero();
        Enemy enemy = createNewEnemy();

        while (!isGameOver) {
            System.out.println("Fight !!");
            //verification of hp
            if(hero.getHp() <= 0) {
                System.out.println(hero.getName() + " died. You loose! Try again ...");
                isGameOver = true;
            }
            else {
                //how plays?
                System.out.println(enemy.getName() + "(HP: " + enemy.getHp() + ")"); //name of enemy
                System.out.println(hero.getName() + "(HP: " + hero.getHp() + "). Your turn!"); //name of hero
                System.out.println("'x' to quit.");
                System.out.println("'m' hero infos.");
                System.out.println("'a' attack.");

                //take character
                Character c = inputHandler.getChar(); //read char "c"
                if (c.equals('x')) {
                    isGameOver = true;
                }
                else if(c.equals('a')) {
                    hero.attack(enemy);
                    if(enemy.getHp() <= 0) {
                        System.out.println(enemy.getName() + " died!");
                        hero.levelUp();
                        System.out.println(hero.getName() + " has now level up ! New level: " + hero.getLevel());
                        enemy = createNewEnemy(); //if enemy died, create a new enemy
                    }
                    else { //if enemy not died, enemy attack hero
                        enemy.attack(hero);
                    }
                }
                else if (c.equals('m')) {
                    int maxAttack = hero.getDamage() + hero.getLevel();
                    System.out.println("*** Hero Infos ***");
                    System.out.println(hero.getName());
                    System.out.println("(LEVEL: " + hero.getLevel() + ")");
                    System.out.println("(HP: " + hero.getHp() + ")");
                    System.out.println("(ATTACK: Between " + hero.getDamage() + " and " + maxAttack + ")");
                }
            }
        }
    }

    private Hero createNewHero(){
        System.out.println("Enter hero name: ");
        String heroName = inputHandler.getString(); //name of hero
        return new Hero(heroName); //instance new hero
    }

    private Enemy createNewEnemy() {
        String name = "Skeleton";
        System.out.println("A " + name + " appeared!");
        int level = 1;
        return new Enemy(name, level);
    }
}
