package test.acceptance;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MeetupSteps {
    private WebDriver driver;

    @Before
    public void beforeScenario() {
        System.setProperty("webdriver.chrome.driver", "/Library/Java/JUNIT/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    @Given("^je suis sur une page meetup$")
    public void jeSuisSurUnePageMeetup() {
        driver.get("https://www.meetup.com/fr-FR/jeune-randonneurs-franciliens-idf-18-35-ans/");
    }

    @Then("^le titre doit etre cliquable avec le nom du meetup$")
    public void leTitreDoitEtreCliquableAvecLeNomDuMeetup() {
        String titleSelector = "a.groupHomeHeader-groupNameLink";
        assertTrue(driver.findElement(By.cssSelector(titleSelector)).isEnabled());
        assertEquals("Jeune randonneurs franciliens IDF 18-35 ans", driver.findElement(By.cssSelector(titleSelector)).getText());
    }

    @And("^un lieu$")
    public void unLieu() {
        String locationSelector = "ul.organizer-city";
        assertTrue( driver.findElement(By.cssSelector(locationSelector)).isEnabled());

    }

    @And("^le total des membres$")
    public void leTotalDesMembres() {
        String memberNumSelector = "a.groupHomeHeaderInfo-memberLink";
        assertTrue( driver.findElement(By.cssSelector(memberNumSelector)).isEnabled());

    }

    @And("^les organisateurs$")
    public void lesOrganisateurs() {
        String leaderSelector = "div.flex-item.organizedByLabel > a";
        assertTrue( driver.findElement(By.cssSelector(leaderSelector)).isEnabled());

    }

    @And("^un bouton avec un label \"([^\"]*)\"$")
    public void unBoutonAvecUnLabel(String arg0) throws Throwable {
        String joinButtonId = "actionButtonLink";
        assertTrue( driver.findElement(By.id(joinButtonId)).isEnabled());
        assertEquals(arg0, driver.findElement(By.id(joinButtonId)).getText());
        throw new PendingException();
    }

    @And("^une photo de presentation\\.$")
    public void unePhotoDePresentation() {
        String imageXpath = "//*[@id=\"mupMain\"]/div[1]/div/section/div/div[1]/div/div/div";
        assertTrue("Test image", driver.findElement(By.xpath(imageXpath)).isEnabled());
    }

    @After
    public void afterScenario() {
        driver.quit();
    }

}
