package test;

import codingfactory.rpgconsole.enemy.Enemy;
import codingfactory.rpgconsole.hero.Hero;
import org.junit.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class EnemyTest {

	Hero hero;
	Enemy enemy;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Avant le démarrage");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("Après tous les tests");
	}

	@Before
	public void setUp() throws Exception {
		hero = new Hero("Jaina Portvaillant");
		enemy = new Enemy("toto", 1);
		System.out.println("Avant un test");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("Après un test");
	}

	@Test
	public void testEnemyTakeDamage() throws Exception {
		enemy.takeDamage(10);
		assertThat(enemy, hasProperty("hp", is(5)));
	}

	@Test
	public void testEnemyAttack() throws Exception {
		enemy.attack(hero);
		assertThat(hero, hasProperty("hp", lessThan(20)));
	}

	@Test
	public void testEnemyProperties() throws Exception {
		assertThat(enemy, hasProperty("name"));
        assertThat(enemy, hasProperty("name", is("toto")));
		assertThat(enemy, hasProperty("hp"));
		assertThat(enemy, hasProperty("hp", is(15)));
	}
}
