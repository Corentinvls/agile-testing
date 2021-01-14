package test.acceptance.feature;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class FeaturePageSteps {

	public static WebDriver driver;
	private Actions actions;
	private String selectorWeight;
	private String selectorAcceleration;
	private String selectorAutonomy;


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
		driver.manage().window().setSize(new Dimension(1920,1080));
		actions = new Actions(driver);

	}

	@Given("^je suis sur la page model$")
	public void je_suis_sur_la_page_model() throws Throwable {
		driver.get("https://www.tesla.com/fr_fr/model3");
	}

	@When("^je suis sur l'onglet caractéristique$")
	public void je_suis_sur_l_onglet_caractéristique() throws Throwable {
		String xPathBtnCharacteristic = "/html/body/div[1]/main/div/div[2]/div/div/div/div/ul/li[8]";
		actions.click(driver.findElement(By.xpath(xPathBtnCharacteristic))).build().perform();
	}

	@When("^que je selectionne la \"([^\"]*)\"$")
	public void que_je_selectionne_la(String arg1) throws Throwable {
		if (arg1.equals("Performance")) {
			String xPathBtnPerformance = "/html/body/div[1]/main/div/div[2]/div/div/div/div/section[8]/div/section/div/div[2]/div/nav/a[1]";
			selectorWeight = "#tesla-spec-showcase-74-1-panel > div:nth-child(2) > ul > li:nth-child(1) > span.tds-list-item_value";
			selectorAcceleration = "#tesla-spec-showcase-74-1-panel > div:nth-child(1) > ul > li:nth-child(2)";
			selectorAutonomy = "#tesla-spec-showcase-74-1-panel > div:nth-child(1) > ul > li:nth-child(3) > span.tds-list-item_value";
			actions.click(driver.findElement(By.xpath(xPathBtnPerformance))).build().perform();
		} else if (arg1.equals("Grande Automonie AWD")) {
			String xPathBtnGrandeAutonomy = "/html/body/div[1]/main/div/div[2]/div/div/div/div/section[8]/div/section/div/div[2]/div/nav/a[2]";
			selectorWeight = "#tesla-spec-showcase-74-2-panel > div:nth-child(2) > ul > li:nth-child(1) > span.tds-list-item_value";
			selectorAcceleration = "#tesla-spec-showcase-74-2-panel > div:nth-child(1) > ul > li:nth-child(2)";
			selectorAutonomy = "#tesla-spec-showcase-74-2-panel > div:nth-child(1) > ul > li:nth-child(3) > span.tds-list-item_value";
			actions.click(driver.findElement(By.xpath(xPathBtnGrandeAutonomy))).build().perform();
		} else {
			String xPathBtnStandard = "/html/body/div[1]/main/div/div[2]/div/div/div/div/section[8]/div/section/div/div[2]/div/nav/a[3]";
			selectorWeight = "#tesla-spec-showcase-74-3-panel > div:nth-child(2) > ul > li:nth-child(1) > span.tds-list-item_value";
			selectorAcceleration = "#tesla-spec-showcase-74-3-panel > div:nth-child(1) > ul > li:nth-child(2)";
			selectorAutonomy = "#tesla-spec-showcase-74-3-panel > div:nth-child(1) > ul > li:nth-child(3) > span.tds-list-item_value";
			actions.click(driver.findElement(By.xpath(xPathBtnStandard))).build().perform();
		}
		Thread.sleep(2000);
	}

	@Then("^le poids doit etre \"([^\"]*)\"$")
	public void le_poids_doit_etre(String arg1) throws Throwable {

		String weight = driver.findElement(By.cssSelector(selectorWeight)).getAttribute("innerHTML");

		assertEquals(arg1, weight.replaceAll("&"+"nbsp;", " "));
	}

	@Then("^l'accéleration doit etre \"([^\"]*)\"$")
	public void l_accéleration_doit_etre(String arg1) throws Throwable {
		String acceleration = driver.findElement(By.cssSelector(selectorAcceleration)).getAttribute("innerHTML");

		assertEquals(arg1.trim(), acceleration.substring(53).trim().replaceAll("&"+"nbsp;", " "));
	}

	@Then("^l'autonomie doit etre \"([^\"]*)\"$")
	public void l_autonomie_doit_etre(String arg1) throws Throwable {
		String autonomy = driver.findElement(By.cssSelector(selectorAutonomy)).getAttribute("innerHTML");
		assertEquals(arg1.trim(), autonomy.trim());
	}

	@After
	public void afterScenario() {
		driver.quit();
	}
}
