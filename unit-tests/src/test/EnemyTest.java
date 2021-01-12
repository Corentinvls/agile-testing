package test;

import codingfactory.rpgconsole.enemy.Enemy;
import org.junit.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;

public class EnemyTest {

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
		enemy = new Enemy("Zazu le Mordu", 1);
		System.out.println("\nBefore a test...");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("Test done");
	}

	@Test
	public void testHeroLevelUp() throws Exception {
		int lvl = enemy.getLevel();
		enemy.levelUp();
		assertThat(enemy, hasProperty("level", is(lvl + 1)));
	}

	@Test
	public void testHeroProperties() throws Exception {
        assertThat(enemy, hasProperty("name", is("Zazu le Mordu")));
		assertThat(enemy, hasProperty("hp", is(15)));
		assertThat(enemy, hasProperty("level", is(1)));
		assertThat(enemy, hasProperty("damage", is(1)));
	}
}
