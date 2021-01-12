package test.functional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;
import java.util.List;

import org.openqa.selenium.interactions.Actions;

import static org.junit.Assert.assertEquals;

public class JobsTest {

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
    public void testPunchline() {
        driver.get("https://www.meetup.com/fr-FR/careers/");
        String punchLineXPath = "/html/body/div[1]/div/div[5]/div[3]/main/div[1]/section/div/div[1]/p";

        WebElement punchLine = driver.findElement(By.xpath(punchLineXPath));
        assertEquals(punchLine.getText(), "Join our team, find your people");
    }

    @Test
    public void testBtnExplore() {
        String btnXPath = "/html/body/div[1]/div/div[5]/div[3]/main/div[1]/section/div/a";
        driver.get("https://www.meetup.com/fr-FR/careers/");
        WebElement btnAnchor = driver.findElement(By.xpath(btnXPath));
        assertEquals(btnAnchor.getText(), "Explore Opportunities");
    }

    @Test
    public void testClickBtnExplore() {
        driver.get("https://www.meetup.com/fr-FR/careers/");
        Actions action = new Actions(driver);

        String btnXPath = "/html/body/div[1]/div/div[5]/div[3]/main/div[1]/section/div/a";
        String ulCardListXPath = "/html/body/div[1]/div/div[5]/div[3]/main/div[4]/section[1]/div[3]/ul";
        String anchorSeeAllXPath = "/html/body/div[1]/div/div[5]/div[3]/main/div[4]/section[2]/div[1]/span/a";

        WebElement btnAnchor = driver.findElement(By.xpath(btnXPath));
        action.click(btnAnchor);
        action.build().perform();

        WebElement ulCardList = driver.findElement(By.xpath(ulCardListXPath));
        List<WebElement> cardList = ulCardList.findElements(By.tagName("li"));

        assertEquals(cardList.size(), 9);

        WebElement anchorSeeAll = driver.findElement(By.xpath(anchorSeeAllXPath));
        assertEquals(anchorSeeAll.getAttribute("href"), "https://www.meetup.com/careers/all-opportunities");
    }

    @Test
    public void testPunchlineBottom() {
        driver.get("https://www.meetup.com/fr-FR/careers/");
        String punchlineXPath = "/html/body/div[1]/div/div[5]/div[3]/main/div[6]/section/div/div/p/span";
        String ulXPath = "/html/body/div[1]/div/div[5]/div[3]/main/div[6]/section/div/ul";

        WebElement punchline = driver.findElement(By.xpath(punchlineXPath));
        assertEquals(punchline.getText(), "Perks and benefits");

        WebElement ulCardList = driver.findElement(By.xpath(ulXPath));
        List<WebElement> cardList = ulCardList.findElements(By.tagName("li"));

        assertEquals(cardList.size(), 6);
    }
}
