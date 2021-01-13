package test.acceptance.research;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
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
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ResearchSteps {

	public static WebDriver driver;
	private String date;

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

	@Given("^je suis sur la recherche$")
	public void je_suis_sur_la_recherche() throws Throwable {
		driver.get("https://www.meetup.com/fr-FR/find/outdoors-adventure/");
	}

	@Then("^le titre contient \"([^\"]*)\"$")
	public void le_titre_contient(String arg1) throws Throwable {
		assertTrue(driver.getTitle().contains(arg1));
	}

	@Then("^le h1 contient \"([^\"]*)\"$")
	public void le_h1_contient(String arg1) throws Throwable {
		String h1XPath = "/html/body/div[3]/div[1]/div[1]/h1";
		assertTrue(driver.findElement(By.xpath(h1XPath)).getText().contains(arg1));
	}

	@Then("^le champs de recherche est$")
	public void le_champs_de_recherche_est() throws Throwable {
		WebElement navBar = driver.findElement(By.id("findNavBar"));

		assertTrue(navBar.findElement(By.id("mainKeywords")).isEnabled());
	}

	@Then("^le rayon est$")
	public void le_rayon_est() throws Throwable {
		WebElement navBar = driver.findElement(By.id("findNavBar"));

		assertTrue(navBar.findElement(By.id("simple-radius")).isEnabled());
	}

	@Then("^la ville est$")
	public void la_ville_est() throws Throwable {
		WebElement navBar = driver.findElement(By.id("findNavBar"));

		assertTrue(navBar.findElement(By.id("simple-location")).isEnabled());
	}

	@Then("^le choix d'affichage est$")
	public void le_choix_affichage_est() throws Throwable {
		WebElement navBar = driver.findElement(By.id("findNavBar"));

		assertTrue(navBar.findElement(By.id("simple-view-selector")).isEnabled());
	}

	@Then("^le tri par défaut est \"([^\"]*)\"$")
	public void le_tri_par_defaut_est(String arg0) throws Throwable {
		WebElement findOrder = driver.findElement(By.id("simple-find-order"));
		WebElement anchor = findOrder.findElement(By.tagName("a"));
		WebElement span = anchor.findElement(By.tagName("span"));
		assertEquals(span.getText(), arg0);
	}

	@And("^les tris possibles sont$")
	public void lesTrisPossiblesSont(DataTable arg1) {
		WebElement findOrder = driver.findElement(By.id("simple-find-order"));
		WebElement ul = findOrder.findElement(By.tagName("ul"));
		List<WebElement> items = ul.findElements(By.tagName("li"))
				.stream()
				.filter(item -> !item.getAttribute("class").equals("display-none"))
				.collect(Collectors.toList());

		assertEquals(items.size(), 4);

		List<List<String>> rows = arg1.asLists(String.class);
		List<String> links =new ArrayList<>(rows.get(0)) ; ;

		for (WebElement item : items) {
			WebElement anchor = item.findElement(By.tagName("a"));
			String txt = anchor.getAttribute("data-copy");
			assertThat(links, hasItem(txt));
			links.remove(txt);
		}
	}

	@When("^je clique sur le bouton$")
	public void jeCliqueSurLeBouton() {
		Actions actions = new Actions(driver);

		actions.click(driver.findElement(By.id("simple-view-selector-event")));
		actions.build().perform();
	}

	@Then("^affiche les évènements et le calendrier de la \"([^\"]*)\"$")
	public void affiche_les_evenements_et_le_calendrier_de_la(String arg0) throws Throwable {
		String xpathListElement = "/html/body/div[3]/div[2]/div/div/div/div[2]/div[1]/ul";

		assertEquals(driver.findElement(By.xpath(xpathListElement)).getAttribute("data-view"), arg0);
		assertTrue(driver.findElement(By.id("simple-date-selector")).isEnabled());
		List<WebElement> lists = driver.findElement(By.xpath(xpathListElement)).findElements(By.tagName("li"));
		for (WebElement list : lists) {
			if (list.getAttribute("data-day") != null) {
				int day = Integer.parseInt(list.getAttribute("data-day"));
				assertTrue(day <= 31 && day >= 1);
			}
		}
	}

	@When("^je clique sur le /21 du mois courant$")
	public void jeCliqueSurLe21DuMoisCourant() {
		Actions actions = new Actions(driver);
		String xpathTwentyOneCalendar = "/html/body/div[3]/div[2]/div/div/div/div[2]/div[2]/div[1]/div/table/tbody/tr[4]/td[4]";

		actions.click(driver.findElement(By.id("simple-view-selector-event")));
		actions.build().perform();

		actions.click(driver.findElement(By.xpath(xpathTwentyOneCalendar)));
		actions.build().perform();
	}

	@Then("^affiche les évènements du /21$")
	public void affiche_les_evenements_du_21() {
		String xpathListElement = "/html/body/div[3]/div[2]/div/div/div/div[2]/div[1]/ul";

		List<WebElement> lists = driver.findElement(By.xpath(xpathListElement)).findElements(By.tagName("li"));

		assertTrue(Integer.parseInt(lists.get(0).getAttribute("data-day")) >= 21);
		assertTrue(Integer.parseInt(lists.get(0).getAttribute("data-month")) >= 1);
	}

	@When("^je clique sur un évènement de la liste$")
	public void je_clique_sur_un_evenement_de_la_liste() {
		Actions actions = new Actions(driver);

		String xpathListElement = "/html/body/div[3]/div[2]/div/div/div/div[2]/div[1]/ul";
		List<WebElement> lists = driver.findElement(By.xpath(xpathListElement)).findElements(By.tagName("li"));

		date = lists.get(0).getText() + " " + lists.get(0).getAttribute("data-year");

		actions.click(lists.get(1).findElement(By.tagName("a")));
		actions.build().perform();
	}

	@Then("^je suis redirigé vers la page de l'évènement$")
	public void je_suis_redirige_vers_la_page_de_levenement() {
		String xpathDate = "/html/body/div[1]/div/div[3]/div/div/div/div[3]/div[3]/main/div[2]/div[1]/div[1]/div/section/div[2]/div/section/div[1]/div/div[2]/div/time/span[1]/span[1]";

		assertEquals(date, driver.findElement(By.xpath(xpathDate)).getText());
	}

	@After
	public void afterScenario() {
		driver.quit();
	}
}
