package test.functional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MeetUpDetailedView {

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
    public void testHeader() {
        String url = "https://www.meetup.com/fr-FR/jeune-randonneurs-franciliens-idf-18-30-ans/";
        String headerSelector = "#mupMain > div.groupHomeHeader.stripe.border--none > div > section";
        String titleSelector = "a.groupHomeHeader-groupNameLink";
        String locationSelector = "ul.organizer-city";
        String memberNumSelector = "a.groupHomeHeaderInfo-memberLink";
        String leaderSelector = "div.flex-item.organizedByLabel > a";
        String joinButtonId = "actionButtonLink";
        String imageXpath = "//*[@id=\"mupMain\"]/div[1]/div/section/div/div[1]/div/div/div";

        driver.get(url);
        assertTrue(driver.findElement(By.cssSelector(headerSelector)).isEnabled());
        assertTrue(driver.findElement(By.cssSelector(titleSelector)).isEnabled());
        assertEquals(url, driver.findElement(By.cssSelector(titleSelector)).getAttribute("href"));
        assertTrue("Test Location", driver.findElement(By.cssSelector(locationSelector)).isEnabled());
        assertTrue("Test Member number", driver.findElement(By.cssSelector(memberNumSelector)).isEnabled());
        assertTrue("Test Leader", driver.findElement(By.cssSelector(leaderSelector)).isEnabled());
        assertTrue("Test Join button", driver.findElement(By.id(joinButtonId)).isEnabled());
        assertTrue("Test image", driver.findElement(By.xpath(imageXpath)).isEnabled());
    }

    @Test
    public void testStripeMenu() {
        driver.get("https://www.meetup.com/fr-FR/jeune-randonneurs-franciliens-idf-18-30-ans/");

        WebElement stripeMenu = driver.findElement(By.xpath("/html/body/div[1]/div/div[3]/div[2]/div[3]/main/div[3]"));
        WebElement list = stripeMenu.findElement(By.tagName("ul"));
        List<WebElement> menuLinks = list.findElements(By.tagName("li"))
                .stream()
                .filter(item -> !item.getAttribute("class").equals("display-none"))
                .collect(Collectors.toList());

        List<String> links = new ArrayList<String>();
        links.add("Événements");
        links.add("Photos");
        links.add("Membres");
        links.add("Discussions");
        links.add("À propos");
        links.add("Plus");

        for (WebElement menuLink : menuLinks) {
            WebElement anchor = menuLink.findElement(By.tagName("span"));
            String txt = anchor.getText();
            assertThat(links, hasItem(txt));
            links.remove(txt);
        }
    }

    @Test
    public void testViewAllLinks() {
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
    public void testNextMeetup() {
        driver.get("https://www.meetup.com/fr-FR/promenades-et-randonnees/");

        try {
            WebElement nextMeetup = driver.findElement(By.className("groupHome-eventsList-upcomingEvents"));
            assertTrue(nextMeetup.isDisplayed());
        }
        catch (Exception e) {
            WebElement empty = driver.findElement(By.className("emptyEventCard"));
            assertTrue(empty.isDisplayed());
        }
        driver.get("https://www.meetup.com/fr-FR/L-Experience-Lab/");
        try {
            WebElement empty = driver.findElement(By.className("emptyEventCard"));
            assertTrue(empty.isDisplayed());
        }
        catch (Exception e) {
            WebElement nextMeetup = driver.findElement(By.className("groupHome-eventsList-upcomingEvents"));
            assertTrue(nextMeetup.isDisplayed());
        }
    }

    @Test
    public void testContact() {
        driver.get("https://www.meetup.com/fr-FR/jeune-randonneurs-franciliens-idf-18-30-ans/");
        String expectedUrl = "https://secure.meetup.com/fr-FR/login/";

        driver.findElement(By.className("orgInfo-message")).click();

        String redirectUrl = driver.getCurrentUrl();
        assertTrue(redirectUrl.contains(expectedUrl));
    }

    @Test
    public void testLogin() {
        driver.get("https://www.meetup.com/fr-FR/promenades-et-randonnees/");

        driver.findElement(By.id("actionButtonLink")).click();

        assertEquals(driver.findElement(
                By.cssSelector("div.signUpModal-wrapper > div:nth-child(3) > a > div > div:nth-child(2) > span"))
                .getText(),"Continuer avec Facebook");
        assertEquals(driver.findElement(
                By.cssSelector("div.signUpModal-wrapper > div:nth-child(4) > a > div > div:nth-child(2) > span"))
                .getText(),"Continuer avec Google");
        assertEquals(driver.findElement(
                By.cssSelector("div.signUpModal-wrapper > div:nth-child(5) > a > div > div:nth-child(2) > span"))
                .getText(),"Continue with Apple");

        String expectedUrl = "https://secure.meetup.com/fr-FR/register/";
        driver.findElement(By.className("signUpModal-email")).click();
        String redirectUrl = driver.getCurrentUrl();
        assertTrue(redirectUrl.contains(expectedUrl));
    }
}
