package test.acceptance.events;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import java.lang.*;
import java.util.stream.Collectors;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.*;

import cucumber.api.java.en.When;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;

public class EventsSteps {

	public static WebDriver driver;

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

	public static void humanInteractionWait() throws Exception {
		Thread.sleep(3000);
	}

	@Given("^je suis sur la homepage$")
	public void je_suis_sur_la_homepage() throws Throwable {
		driver.get("https://www.tesla.com/fr_FR");
	}

	@When("^je clique sur le menu burger puis le plus puis événements$")
	public void je_clique_sur_le_menu_burger_puis_le_plus_puis_événements() throws Throwable {
		Actions actions = new Actions(driver);

		humanInteractionWait();

		actions.click(driver.findElement(By.xpath("/html/body/div[1]/div/header/div/div/label/span/i")));
		actions.build().perform();

		humanInteractionWait();

		actions.click(driver.findElement(By.xpath("/html/body/div[1]/div/header/div/div/nav/div[1]/nav[1]/ol/li[10]/a")));
		actions.build().perform();
	}

	@Then("^je dois arriver sur le page \"([^\"]*)\"$")
	public void je_dois_arriver_sur_le_page(String arg1) throws Throwable {
		String redirectUrl = driver.getCurrentUrl();
		assertTrue(redirectUrl.contains(arg1));
	}

	@Given("^je suis sur la page événements$")
	public void je_suis_sur_la_page_événements() throws Throwable {
		driver.get("https://www.tesla.com/fr_FR/events");
	}

	@When("^je choisi un lieu dans le monde$")
	public void je_choisi_un_lieu_dans_le_monde() throws Throwable {
		String inputSearch = "/html/body/div[2]/div/div[3]/div/main/div/div[1]/div/div/section[2]/div/div[1]/div[3]/div/form/div/div/fieldset/div/div/div/input";

		driver.findElement(By.xpath(inputSearch)).clear();

		humanInteractionWait();

		driver.findElement(By.xpath(inputSearch)).sendKeys("Paris");

		humanInteractionWait();

		driver.findElement(By.xpath(inputSearch)).sendKeys(Keys.ENTER);
	}

	@Then("^je dois avoir les prochains événements$")
	public void je_dois_avoir_les_prochains_événements() throws Throwable {
		humanInteractionWait();

		int elementCount = driver.findElements(By.className("content")).size();

		assertEquals(15, elementCount);
	}

	@Then("^je dois avoir les deux liens$")
	public void je_dois_avoir_les_deux_liens(DataTable arg1) throws Throwable {
		String plus = driver.findElement(By.xpath("/html/body/div[2]/div/div[3]/div/main/div/div[1]/div/div/section[2]/div/div[1]/div[7]/div/div/div[3]/ul/li/a")).getText();
		String tous = driver.findElement(By.className("view-all")).getText();

		List<String>items = new ArrayList<String>();
		items.add(plus);
		items.add(tous);

		List<List<String>> rows = arg1.asLists(String.class);
		List<String> links =new ArrayList<>(rows.get(0));

		for (String item : items) {
			assertThat(links, hasItem(item));
			links.remove(item);
		}
	}

	@Then("^le formulaire doit contenir$")
	public void le_formulaire_doit_contenir(DataTable arg1) throws Throwable {

		WebElement findForm = driver.findElement(By.className("sidebar"));

		WebElement form = findForm.findElement(By.id("test-drive-details"));
		List<WebElement> items = form.findElements(By.tagName("input"))
				.stream()
				.filter(item -> !item.getAttribute("type").equals("hidden"))
				.collect(Collectors.toList());

		items.add(form.findElement(By.tagName("select")));

		assertEquals(items.size(), 7);

		List<List<String>> rows = arg1.asLists(String.class);
		List<String> links = new ArrayList<>(rows.get(0)) ; ;

		for (WebElement item : items) { ;
			String txt = item.getAttribute("name");
			assertThat(links, hasItem(txt));
			links.remove(txt);
		}
	}

	@Then("^un bouton d'envoi dont le texte est \"([^\"]*)\"$")
	public void un_bouton_d_envoi_dont_le_texte_est(String arg1) throws Throwable {
		String submit = driver.findElement(By.id("edit-submit-td-ajax")).getAttribute("value");

		assertTrue(submit.contains(arg1));
	}

	@When("^j'envoi le formulaire sans remplir l'email$")
	public void j_envoi_le_formulaire_sans_remplir_l_email() throws Throwable {
		Actions actions = new Actions(driver);

		WebElement email = driver.findElement(By.id("edit-usermail-td"));
		WebElement name = driver.findElement(By.id("edit-firstname-td"));
		name.sendKeys("Brian");
		email.clear();

		humanInteractionWait();

		actions.click(driver.findElement(By.name("ajax-submit")));
		actions.build().perform();
	}

	@Then("^un message rouge apparait en indiquant \"([^\"]*)\"$")
	public void un_message_rouge_apparait_en_indiquant(String arg1) throws Throwable {
		String obligatoire = "/html/body/div[2]/div/div[3]/div/main/div/div[1]/div/div/section[2]/div/div[2]/div/div[1]/div/div/form/div/div[1]/div[4]/div/ul/li";
		String obligatoireRouge = driver.findElement(By.xpath(obligatoire)).getText();

		assertTrue(obligatoireRouge.contains(arg1));
	}

	@When("^je recherche \"([^\"]*)\"$")
	public void je_recherche(String arg1) throws Throwable {
		String inputSearch = "/html/body/div[2]/div/div[3]/div/main/div/div[1]/div/div/section[2]/div/div[1]/div[3]/div/form/div/div/fieldset/div/div/div/input";

		driver.findElement(By.xpath(inputSearch)).clear();

		humanInteractionWait();

		driver.findElement(By.xpath(inputSearch)).sendKeys(arg1);

		humanInteractionWait();

		driver.findElement(By.xpath(inputSearch)).sendKeys(Keys.ENTER);
	}

	@Then("^le premier resultat doit être localisé à \"([^\"]*)\"$")
	public void le_premier_resultat_doit_être_localisé_à(String arg1) throws Throwable {
		humanInteractionWait();

		String location = driver.findElement(By.className("location-teaser")).getText();
		assertTrue(location.contains(arg1));
	}

	@When("^je recherche et clique sur un événement \"([^\"]*)\"$")
	public void je_recherche_et_clique_sur_un_événement(String arg1) throws Throwable {
		String inputSearch = "/html/body/div[2]/div/div[3]/div/main/div/div[1]/div/div/section[2]/div/div[1]/div[3]/div/form/div/div/fieldset/div/div/div/input";

		driver.findElement(By.xpath(inputSearch)).clear();

		humanInteractionWait();

		driver.findElement(By.xpath(inputSearch)).sendKeys(arg1);

		humanInteractionWait();

		driver.findElement(By.xpath(inputSearch)).sendKeys(Keys.ENTER);

		Actions actions = new Actions(driver);

		actions.click(driver.findElement(By.cssSelector("#node-54371 > div > h2 > a")));
		actions.build().perform();
	}

	@Then("^je suis redirigé vers la page \"([^\"]*)\"$")
	public void je_suis_redirigé_vers_la_page(String arg1) throws Throwable {
		humanInteractionWait();

		String redirectUrl = driver.getCurrentUrl();
		assertTrue(redirectUrl.contains(arg1));
	}

	@After
	public void afterScenario() {
		driver.quit();
	}

}
