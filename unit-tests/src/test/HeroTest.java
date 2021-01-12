package test;

import org.junit.Test;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import codingfactory.rpgconsole.hero.Hero;
import codingfactory.rpgconsole.enemy.Enemy;

public class HeroTest {

	Hero hero;
	Enemy enemy;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Before starting");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("\nEnd of tests");
	}

	@Before
	public void setUp() throws Exception {
		hero = new Hero("Jaina Portvaillant");
		enemy = new Enemy("toto", 1);
		System.out.println("Avant un test");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("Test done");
	}

	@Test
	public void testHeroLevelUp() throws Exception {
		int lvl = hero.getLevel();
		hero.levelUp();
		assertThat(hero, hasProperty("level", is(lvl + 1)));
	}

	@Test
	public void testHeroTakeDamage() throws Exception {
		hero.takeDamage(10);
		assertThat(hero, hasProperty("hp", is(10)));
		hero.takeDamage(40);
		assertThat(hero, hasProperty("hp", is(0)));
	}

	@Test
	public void testHeroAttack() throws Exception {
		hero.attack(enemy);
		assertThat(enemy, hasProperty("hp", lessThan(15)));
	}

	@Test
	public void testHeroProperties() throws Exception {
        assertThat(hero, hasProperty("name", is("Jaina Portvaillant")));
		assertThat(hero, hasProperty("hp", is(20)));
		assertThat(hero, hasProperty("level", is(1)));
		assertThat(hero, hasProperty("damage", is(2)));
	}
}
