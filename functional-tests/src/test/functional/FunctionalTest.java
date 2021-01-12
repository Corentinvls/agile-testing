package test.functional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
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
            System.out.println("\nEnd\n");
        }
    }

    @Test
    public void testMeetups(){
        driver.get("https://www.meetup.com/fr-FR/jeune-randonneurs-franciliens-idf-18-30-ans/");

        WebElement stripeMenu = driver.findElement(By.xpath("/html/body/div[1]/div/div[3]/div[2]/div[3]/main/div[3]"));
        WebElement list = stripeMenu.findElement(By.tagName("ul"));
        List<WebElement> menuLinks = list.findElements(By.tagName("li"))
                .stream()
                .filter(item -> !item.getAttribute("class").equals("display-none"))
                .collect(Collectors.toList());

        List<String> links = Arrays.asList("Événements", "Photos", "Membres", "Discussions", "À propos", "Plus");

        for (WebElement menuLink : menuLinks) {
            WebElement anchor = menuLink.findElement(By.tagName("span"));
            String txt = anchor.getText();
            assertThat(links, hasItem(txt));
        }
    }

    @Test
    public void testViewAllLinks(){
        driver.get("https://www.meetup.com/fr-FR/jeune-randonneurs-franciliens-idf-18-30-ans/");

        List<String> xpaths = Arrays.asList(
                "/html/body/div[1]/div/div[3]/div[2]/div[3]/main/div[4]/div/div/div/div[1]/section[2]/div/div[1]/div[2]/a/span",
                "/html/body/div[1]/div/div[3]/div[2]/div[3]/main/div[4]/div/div/div/div[2]/section[2]/div[1]/div[2]/a/span",
                "/html/body/div[1]/div/div[3]/div[2]/div[3]/main/div[4]/div/div/div/div[1]/section[3]/div[1]/div[2]/a/span",
                "/html/body/div[1]/div/div[3]/div[2]/div[3]/main/div[5]/div/section/div[1]/div/div[2]/a/span");

        List<String> viewAll = Collections.singletonList("Voir tout");

        for (String xpath : xpaths) {
            System.out.println(xpath);
            String txt = driver.findElement(By.xpath(xpath)).getText();
            assertThat(viewAll, hasItem(txt));
        }
    }

    @Test
    public void testContact(){
        driver.get("https://www.meetup.com/fr-FR/jeune-randonneurs-franciliens-idf-18-30-ans/");
        String expedctedUrl = "https://secure.meetup.com/fr-FR/login/";

        driver.findElement(By.className("orgInfo-message")).click();

        String redirectUrl = driver.getCurrentUrl();
        assertTrue(redirectUrl.contains(expedctedUrl));
    }

    @Test
    public void testNextMeetup() {
        driver.get("https://www.meetup.com/fr-FR/promenades-et-randonnees/");

        try {
            driver.findElement(By.className("emptyEventCard")).isDisplayed();
            assertTrue(driver.findElement(By.className("emptyEventCard")).isEnabled());
        } catch (Exception e) {
            driver.findElement(By.className("groupHome-eventsList-upcomingEvents")).isDisplayed();
            assertTrue(driver.findElement(By.className("groupHome-eventsList-upcomingEvents")).isEnabled());
        }
    }

    @Test
    public void testLogin(){
        driver.get("https://www.meetup.com/fr-FR/promenades-et-randonnees/");

        driver.findElement(By.id("actionButtonLink")).click();

        assertEquals(driver.findElement(By.cssSelector("div.signUpModal-wrapper > div:nth-child(3) > a > div > div:nth-child(2) > span")).getText(),"Continuer avec Facebook");
        assertEquals(driver.findElement(By.cssSelector("div.signUpModal-wrapper > div:nth-child(4) > a > div > div:nth-child(2) > span")).getText(),"Continuer avec Google");
        assertEquals(driver.findElement(By.cssSelector("div.signUpModal-wrapper > div:nth-child(5) > a > div > div:nth-child(2) > span")).getText(),"Continue with Apple");

        String expedctedUrl = "https://secure.meetup.com/fr-FR/register/";

        driver.findElement(By.className("signUpModal-email")).click();

        String redirectUrl = driver.getCurrentUrl();
        assertTrue(redirectUrl.contains(expedctedUrl));
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

}
