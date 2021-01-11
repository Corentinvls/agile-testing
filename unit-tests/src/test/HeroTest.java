package test;

import org.junit.Test;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import codingfactory.rpgconsole.hero.Hero;

public class HeroTest {

	Hero hero;

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
		System.out.println("\nBefore a test...");
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
	public void testHeroProperties() throws Exception {
        assertThat(hero, hasProperty("name", is("Jaina Portvaillant")));
		assertThat(hero, hasProperty("hp", is(20)));
		assertThat(hero, hasProperty("level", is(1)));
		assertThat(hero, hasProperty("damage", is(2)));
	}
}
