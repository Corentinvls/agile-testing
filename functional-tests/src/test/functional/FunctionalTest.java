package test.functional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FunctionalTest {

	private WebDriver driver;

    @Before
    public void setUp() throws Exception {

        String OS = System.getProperty("os.name").toLowerCase();
        System.out.println(OS);
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

        driver.get("https://www.meetup.com/fr-FR/");
		assertEquals(driver.getTitle(), "Partagez vos passions | Meetup");
		assertEquals(driver.findElement(By.xpath(h1)).getText(), "Le monde vous tend les bras");
		assertEquals(driver.findElement(By.xpath(subH1)).getText(), "Rejoignez un groupe local pour rencontrer du monde, tester une nouvelle activité ou partager vos passions.");
		assertEquals(driver.findElement(By.xpath(joinButton)).getCssValue("background"), "rgb(241, 58, 89) none repeat scroll 0% 0% / auto padding-box border-box");
		assertEquals(driver.findElement(By.xpath(joinButton)).getText(), "Rejoindre Meetup");
		assertEquals(driver.findElement(By.xpath(joinButton)).getAttribute("href"), "https://www.meetup.com/fr-FR/register/");
    }

    @Test
    public void testResearch() throws Exception {

        String h1 = "/html/body/div[3]/div[1]/div[1]/h1";
        String navBar = "/html/body/div[3]/div[1]/div[4]/div[1]";

        driver.get("https://www.meetup.com/fr-FR/find/outdoors-adventure/");

        WebElement navbarComponent = driver.findElement(By.xpath(navBar));

        assertTrue(driver.getTitle().contains("Nature et aventure"));
        assertTrue(driver.findElement(By.xpath(h1)).getText().contains("Nature et aventure"));

        assertTrue(navbarComponent.isEnabled());
        assertTrue(navbarComponent.findElement(By.id("mainKeywords")).isEnabled());
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

}
