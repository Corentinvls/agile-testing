package test.acceptance.home;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import java.lang.*;

import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;

import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

public class HomepageSteps {

	public static WebDriver driver;

	@Before
	public void beforeScenario() {
		String OS = System.getProperty("os.name").toLowerCase();

		System.out.println("OS = " + OS);

		System.setProperty("webdriver.chrome.driver",
				OS.contains("win") ?
						"chromedriver.exe" :
						"/Library/Java/JUNIT/chromedriver");

		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--lang=fr");
		chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);

		driver = new ChromeDriver(chromeOptions);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		//driver.manage().window().setSize(new Dimension(1920, 1080));
	}

	@After
	public void afterScenario() {
		driver.quit();
	}

	@Given("^je suis sur la page d'accueil$")
	public void je_suis_sur_la_page_d_accueil() {
		driver.get("https://www.tesla.com/fr_FR/");
	}

	@Then("^le titre doit etre \"([^\"]*)\"$")
	public void le_titre_doit_etre(String arg1) {
		assertEquals(driver.getTitle(), arg1);
	}

	@Then("^la description doit etre \"([^\"]*)\"$")
	public void la_description_doit_etre(String arg1) {
		assertEquals(driver.findElement(By.cssSelector("meta[name='description']")).getAttribute("content"), arg1);
	}

	@Then("^la page contient plusieurs punchlines qui doivent etre$")
	public void la_page_contient_plusieurs_punchlines_qui_doivent_etre(DataTable arg1) {
		List<List<String>> rows = arg1.asLists(String.class);
		List<String> punchlines = new ArrayList<>(rows.get(0));
		String punchLineCssSelector = "section.tcl-hero-parallax__copy > div > h1";

		for (String punchline : punchlines) {
			assertEquals(driver.findElement(By.cssSelector(punchLineCssSelector)).getAttribute("innerHTML"), punchline);
			new Actions(driver).click(driver.findElement(By.cssSelector(punchLineCssSelector))).build().perform();
			new Actions(driver).sendKeys(Keys.PAGE_DOWN).build().perform();
			try {
				Thread.sleep(500);
			}
			catch (InterruptedException ignored) { }
		}
	}

	@Then("^l'item (\\d+) du menu doit etre \"([^\"]*)\" et redirige vers \"([^\"]*)\"$")
	public void l_item_du_menu_doit_etre_et_redirige_vers(int arg1, String arg2, String arg3) throws Throwable {
		String olXPath = "/html/body/div[1]/div/header/div/div/nav/nav[1]/ol";
		WebElement menu = driver.findElement(By.xpath(olXPath));
		List<WebElement> liList = menu.findElements(By.tagName("li"));
		WebElement anchor = liList.get(arg1).findElement(By.tagName("a"));
		assertEquals(anchor.getAttribute("innerHTML"), arg2);
		assertEquals(anchor.getAttribute("href"), arg3);
	}

	@When("^je clique sur le menu burger$")
	public void je_clique_sur_le_menu_burger() {
		Actions actions = new Actions(driver);
		String iconMenuXPath = "/html/body/div[1]/div/header/div/div/label/span";
		actions.click(driver.findElement(By.xpath(iconMenuXPath))).build().perform();
	}

	@Then("^le menu contient$")
	public void le_menu_contient(DataTable arg1) {
		List<List<String>> rows = arg1.asLists(String.class);
		List<String> items = new ArrayList<>(rows.get(0));
		String olXPath = "/html/body/div[1]/div/header/div/div/nav/div[1]/nav[1]/ol";
		WebElement menu = driver.findElement(By.xpath(olXPath));
		List<WebElement> liList = menu.findElements(By.tagName("li"));

		for (int i = 0; i < items.size(); i++) {
			WebElement anchor = liList.get(i).findElement(By.tagName("a"));
			assertEquals(anchor.getAttribute("innerHTML"), items.get(i));
		}
	}
}
