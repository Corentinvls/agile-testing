package test.functional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class FunctionalTest {

	private WebDriver driver;

    @Before
    public void setUp() {
        String OS = System.getProperty("os.name").toLowerCase();
        System.setProperty("webdriver.chrome.driver",
                OS.contains("win") ?
                        "chromedriver.exe" :
                        "/Library/Java/JUNIT/chromedriver");
        driver = new ChromeDriver();
  		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
     }

    @After
    public void tearDown() {
        driver.quit();
    }

	@Test
    public void testHomepage() {
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
}
