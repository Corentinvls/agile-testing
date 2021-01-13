package test.acceptance.meetup;

import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

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

public class MeetupSteps {
    private WebDriver driver;

    @Before
    public void beforeScenario() {
        String OS = System.getProperty("os.name").toLowerCase();

        System.out.println("OS = " + OS);

        System.setProperty("webdriver.chrome.driver",
                OS.contains("win") ?
                        "chromedriver.exe" :
                        "/Library/Java/JUNIT/chromedriver");

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
        assertTrue(driver.findElement(By.cssSelector(locationSelector)).isEnabled());

    }

    @And("^le total des membres$")
    public void leTotalDesMembres() {
        String memberNumSelector = "a.groupHomeHeaderInfo-memberLink";
        assertTrue(driver.findElement(By.cssSelector(memberNumSelector)).isEnabled());

    }

    @And("^les organisateurs$")
    public void lesOrganisateurs() {
        String leaderSelector = "div.flex-item.organizedByLabel > a";
        assertTrue(driver.findElement(By.cssSelector(leaderSelector)).isEnabled());

    }

    @And("^un bouton avec un label \"([^\"]*)\"$")
    public void unBoutonAvecUnLabel(String arg0) throws Throwable {
        String joinButtonId = "actionButtonLink";
        assertTrue(driver.findElement(By.id(joinButtonId)).isEnabled());
        assertEquals(arg0, driver.findElement(By.id(joinButtonId)).getText());
    }

    @And("^une photo de presentation\\.$")
    public void unePhotoDePresentation() {
        String imageXpath = "//*[@id=\"mupMain\"]/div[1]/div/section/div/div[1]/div/div/div";
        assertTrue("Test image", driver.findElement(By.xpath(imageXpath)).isEnabled());
    }

    @Then("^La fiche doit contenir un bandeau d'onglets avec les bouttons suivant$")
    public void la_fiche_doit_contenir_un_bandeau_d_onglets_avec_les_bouttons_suivant(DataTable arg1) throws Throwable {
        WebElement stripeMenu = driver.findElement(By.xpath("/html/body/div[1]/div/div[3]/div[2]/div[3]/main/div[3]"));
        WebElement list = stripeMenu.findElement(By.tagName("ul"));
        List<WebElement> menuLinks = list.findElements(By.tagName("li"))
                .stream()
                .filter(item -> !item.getAttribute("class").equals("display-none"))
                .collect(Collectors.toList());

        List<List<String>> rows = arg1.asLists(String.class);
        List<String> links = new ArrayList<>(rows.get(0));
        for (WebElement menuLink : menuLinks) {
            WebElement anchor = menuLink.findElement(By.tagName("span"));
            String txt = anchor.getText();
            assertThat(links, hasItem(txt));
            links.remove(txt);
        }
    }

    @After
    public void afterScenario() {
        driver.quit();
    }


    @Then("^La page doit contenir les quatre liens \"([^\"]*)\"$")
    public void laPageDoitContenirLesQuatreLiens(String arg0) throws Throwable {
        List<String> xpaths = Arrays.asList(
                "/html/body/div[1]/div/div[3]/div[2]/div[3]/main/div[4]/div/div/div/div[1]/section[2]/div/div[1]/div[2]/a/span",
                "/html/body/div[1]/div/div[3]/div[2]/div[3]/main/div[4]/div/div/div/div[2]/section[2]/div[1]/div[2]/a/span",
                "/html/body/div[1]/div/div[3]/div[2]/div[3]/main/div[4]/div/div/div/div[1]/section[3]/div[1]/div[2]/a/span",
                "/html/body/div[1]/div/div[3]/div[2]/div[3]/main/div[5]/div/section/div[1]/div/div[2]/a/span");

        List<String> viewAll = Collections.singletonList(arg0);

        for (String xpath : xpaths) {
            String txt = driver.findElement(By.xpath(xpath)).getText();
            assertThat(viewAll, hasItem(txt));
        }
    }

    @Given("^je suis sur une page meetup avec un evenement a venir$")
    public void jeSuisSurUnePageMeetupAvecUnEvenementAVenir() {
        driver.get("https://www.meetup.com/fr-FR/promenades-et-randonnees/");
    }

    @Then("^La page doit contenir une card pour le meetup a venir ou un signalement aucun evenement$")
    public void laPageDoitContenirUneCardPourLeMeetupAVenirOuUnSignalementAucunEvenement() {
        try {
            WebElement nextMeetup = driver.findElement(By.className("groupHome-eventsList-upcomingEvents"));
            assertTrue(nextMeetup.isDisplayed());
        } catch (Exception e) {
            WebElement empty = driver.findElement(By.className("emptyEventCard"));
            assertTrue(empty.isDisplayed());
        }
    }

    @When("^je clicke sur le bouton Rejoindre$")
    public void jeClickeSurLeBoutonRejoindre() {
        Actions action = new Actions(driver);
        WebElement btnAnchor = driver.findElement(By.id("actionButtonLink"));

        action.click(btnAnchor);
        action.build().perform();
    }

    @Then("^je dois pouvoir m'identifier avec un lien \"([^\"]*)\"$")
    public void jeDoisPouvoirMIdentifierAvecUnLien(String arg0) throws Throwable {
        assertEquals(driver.findElement(
                By.cssSelector("div.signUpModal-wrapper > div:nth-child(2) > a > span"))
                .getText(), "Connexion");
        assertTrue(driver.findElement(
                By.cssSelector("div.signUpModal-wrapper > div:nth-child(2) > a"))
                .getAttribute("href").contains(arg0));
    }

    @And("^je dois pouvoir me connecter avec facebook ou google$")
    public void jeDoisPouvoirMeConnecterAvecFacebookOuGoogle() {
        assertEquals(driver.findElement(
                By.cssSelector("div.signUpModal-wrapper > div:nth-child(3) > a > div > div:nth-child(2) > span"))
                .getText(), "Continuer avec Facebook");
        assertEquals(driver.findElement(
                By.cssSelector("div.signUpModal-wrapper > div:nth-child(4) > a > div > div:nth-child(2) > span"))
                .getText(), "Continuer avec Google");
    }


    @When("^je peux aussi clicke sur m'inscrire$")
    public void jePeuxAussiClickeSurMInscrire() {
        Actions action = new Actions(driver);
        WebElement btnAnchor = driver.findElement(By.className("signUpModal-email"));

        action.click(btnAnchor);
        action.build().perform();
    }

    @And("^etre rediriger vers \"([^\"]*)\"$")
    public void etreRedirigerVers(String arg0) throws Throwable {
        String redirectUrl = driver.getCurrentUrl();
        assertTrue(redirectUrl.contains(arg0));
    }


    @When("^je clicke sur le boutton contacter$")
    public void jeClickeSurLeBouttonContacter() {
        Actions action = new Actions(driver);
        WebElement btnAnchor = driver.findElement(By.className("orgInfo-message"));
        action.click(btnAnchor);
        action.build().perform();
    }

    @Then("^je dois etre rediriger sur \"([^\"]*)\"$")
    public void jeDoisEtreRedirigerSur(String arg0) throws Throwable {
        String redirectUrl = driver.getCurrentUrl();
        assertTrue(redirectUrl.contains(arg0));
    }
}