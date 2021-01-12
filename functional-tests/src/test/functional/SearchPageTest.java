package test.functional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SearchPageTest {
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
    public void testResearch() {
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
    public void testSort() {
        driver.get("https://www.meetup.com/fr-FR/find/outdoors-adventure/");

        WebElement findOrder = driver.findElement(By.id("simple-find-order"));
        WebElement anchor = findOrder.findElement(By.tagName("a"));
        WebElement span = anchor.findElement(By.tagName("span"));
        assertEquals(span.getText(), "pertinence");
    }

    @Test
    public void testSortChoices() {
        driver.get("https://www.meetup.com/fr-FR/find/outdoors-adventure/");
        List<String> choices = new ArrayList<>();
        choices.add("pertinence");
        choices.add("plus récents");
        choices.add("nombre de membres");
        choices.add("proximité");

        WebElement findOrder = driver.findElement(By.id("simple-find-order"));
        WebElement ul = findOrder.findElement(By.tagName("ul"));
        List<WebElement> items = ul.findElements(By.tagName("li"))
                .stream()
                .filter(item -> !item.getAttribute("class").equals("display-none"))
                .collect(Collectors.toList());

        assertEquals(items.size(), 4);

        for (WebElement item : items) {
            WebElement anchor = item.findElement(By.tagName("a"));
            String txt = anchor.getAttribute("data-copy");
            assertThat(choices, hasItem(txt));
            choices.remove(txt);
        }
    }

    @Test
    public void testClickCalendar() {
        driver.get("https://www.meetup.com/fr-FR/find/outdoors-adventure/");
        Actions actions = new Actions(driver);
        String xpathListElement = "/html/body/div[3]/div[2]/div/div/div/div[2]/div[1]/ul";

        actions.click(driver.findElement(By.id("simple-view-selector-event")));
        actions.build().perform();
        assertEquals(driver.findElement(By.xpath(xpathListElement)).getAttribute("data-view"), "list");
        assertTrue(driver.findElement(By.id("simple-date-selector")).isEnabled());
        List<WebElement> lists = driver.findElement(By.xpath(xpathListElement)).findElements(By.tagName("li"));
        for (WebElement list : lists) {
            if (list.getAttribute("data-day") != null) {
                int day = Integer.parseInt(list.getAttribute("data-day"));
                assertTrue(day <= 31 && day >= 1);
            }
        }
    }

    @Test
    public void testClickOnTwentyOne() {
        driver.get("https://www.meetup.com/fr-FR/find/outdoors-adventure/");
        Actions actions = new Actions(driver);
        String xpathTwentyOneCalendar = "/html/body/div[3]/div[2]/div/div/div/div[2]/div[2]/div[1]/div/table/tbody/tr[4]/td[4]";
        String xpathListElement = "/html/body/div[3]/div[2]/div/div/div/div[2]/div[1]/ul";
        String xpathDate = "/html/body/div[1]/div/div[3]/div/div/div/div[3]/div[3]/main/div[2]/div[1]/div[1]/div/section/div[2]/div/section/div[1]/div/div[2]/div/time/span[1]/span[1]";

        actions.click(driver.findElement(By.id("simple-view-selector-event")));
        actions.build().perform();

        actions.click(driver.findElement(By.xpath(xpathTwentyOneCalendar)));
        actions.build().perform();

        List<WebElement> lists = driver.findElement(By.xpath(xpathListElement)).findElements(By.tagName("li"));
        String date = lists.get(0).getText() + " " + lists.get(0).getAttribute("data-year");

        assertTrue(Integer.parseInt(lists.get(0).getAttribute("data-day")) >= 21);
        assertTrue(Integer.parseInt(lists.get(0).getAttribute("data-month")) >= 1);

        actions.click(lists.get(1).findElement(By.tagName("a")));
        actions.build().perform();

        assertEquals(date, driver.findElement(By.xpath(xpathDate)).getText());
    }
}
