package test.functional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FunctionalTest {

	private WebDriver driver;

    @Before
    public void setUp() throws Exception {
        String OS = System.getProperty("os.name").toLowerCase();
        System.setProperty("webdriver.chrome.driver", OS.contains("win") ? "chromedriver.exe" : "/Library/Java/JUNIT/chromedriver");
		driver = new ChromeDriver();
		// Seems no more working in last Chrome versions
		// driver.manage().window().maximize();
  		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
     }

    // Test de la Story #1-homepage (https://trello.com/c/WKTneu9o/1-homepage)

	@Test
    public void testHomepage() throws Exception {
        String h1 = "/html/body/div[1]/div/div[5]/div[3]/main/div[1]/div/section/div/div[1]/h1/span";
        String subH1 = "/html/body/div[1]/div/div[5]/div[3]/main/div[1]/div/section/div/div[2]/p/span";
        String joinButton = "/html/body/div[1]/div/div[5]/div[3]/main/div[1]/div/section/div/div[3]/a";
        String description = "/html/head/meta[6]";

        driver.get("https://www.meetup.com/fr-FR/");
		assertEquals(driver.getTitle(), "Partagez vos passions | Meetup");
		assertEquals(driver.findElement(By.xpath(h1)).getText(), "Le monde vous tend les bras");
		assertEquals(driver.findElement(By.xpath(subH1)).getText(), "Rejoignez un groupe local pour rencontrer du monde, tester une nouvelle activité ou partager vos passions.");
		assertEquals(driver.findElement(By.xpath(joinButton)).getCssValue("background"), "rgb(241, 58, 89) none repeat scroll 0% 0% / auto padding-box border-box");
		assertEquals(driver.findElement(By.xpath(joinButton)).getText(), "Rejoindre Meetup");
		assertEquals(driver.findElement(By.xpath(joinButton)).getAttribute("href"), "https://www.meetup.com/fr-FR/register/");
		assertEquals(driver.findElement(By.xpath(description)).getAttribute("content"), "Partagez vos passions et faites bouger votre ville\u00a0! Meetup vous aide à rencontrer des personnes près de chez vous, autour de vos centres d'intérêt.");
    }

    @Test
    public void testResearch() throws Exception {

        String h1XPath = "/html/body/div[3]/div[1]/div[1]/h1";

        driver.get("https://www.meetup.com/fr-FR/find/outdoors-adventure/");

        WebElement navBar = driver.findElement(By.id("findNavBar"));

        assertTrue(driver.getTitle().contains("Nature et aventure"));
        assertTrue(driver.findElement(By.xpath(h1XPath)).getText().contains("Nature et aventure"));

        assertTrue(navBar.isEnabled());
        assertTrue(navBar.findElement(By.id("mainKeywords")).isEnabled());
        assertTrue(navBar.findElement(By.id("simple-radius")).isEnabled());
        assertTrue(navBar.findElement(By.id("simple-location")).isEnabled());
        assertTrue(navBar.findElement(By.id("simple-view-selector")).isEnabled());
    }

    @Test
    public void testSort() throws Exception {
        driver.get("https://www.meetup.com/fr-FR/find/outdoors-adventure/");

        WebElement findOrder = driver.findElement(By.id("simple-find-order"));
        WebElement anchor = findOrder.findElement(By.tagName("a"));
        WebElement span = anchor.findElement(By.tagName("span"));
        assertEquals(span.getText(), "pertinence");
    }

    @Test
    public void testSortChoices() throws Exception {
        driver.get("https://www.meetup.com/fr-FR/find/outdoors-adventure/");

        WebElement findOrder = driver.findElement(By.id("simple-find-order"));
        WebElement ul = findOrder.findElement(By.tagName("ul"));
        List<WebElement> items = ul.findElements(By.tagName("li"))
                .stream()
                .filter(item -> !item.getAttribute("class").equals("display-none"))
                .collect(Collectors.toList());

        assertEquals(items.size(), 4);

        List<String> choices = Arrays.asList("pertinence", "plus récents", "nombre de membres", "proximité");

        for (WebElement item : items) {
            System.out.println("Start\n");
            for (String s: choices) {
                System.out.println("t:" + s);
            }
            WebElement anchor = item.findElement(By.tagName("a"));
            String txt = anchor.getAttribute("data-copy");
            System.out.println("text: '" + txt + "'");
            assertThat(choices, hasItem(txt));
            choices.remove(txt);
            System.out.println("\nEnd\n");

        }
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

}
