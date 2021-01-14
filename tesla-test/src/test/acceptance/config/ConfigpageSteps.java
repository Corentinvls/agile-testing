package test.acceptance.config;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import jdk.internal.dynalink.linker.LinkerServices;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class ConfigpageSteps {

	public static WebDriver driver;
	private Actions actions;
	private Integer priceLoa;
	private Integer priceLoaWithAutoPilot;

	@Before
	public void beforeScenario() {
		String OS = System.getProperty("os.name").toLowerCase();

		System.out.println("OS = " + OS);

		System.setProperty("webdriver.chrome.driver",
				OS.contains("win") ?
						"chromedriver.exe" :
						"/Library/Java/JUNIT/chromedriver");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--lang=fr");

		driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		actions = new Actions(driver);
	}

	@Given("^je suis sur la page configurateur$")
	public void je_suis_sur_la_page_configurateur() throws Throwable {
		driver.get("https://www.tesla.com/fr_fr/models");
	}

	@When("^je clique sur le bouton commander$")
	public void je_clique_sur_le_bouton_commander() throws Throwable {
		String xPathBtnCommander = "/html/body/div[1]/main/div/div[2]/div/div/div/div/section[1]/div/section/div/div[1]/div/div[2]/div/div[6]/a";
		driver.findElement(By.xpath(xPathBtnCommander)).click();
		for (String handle: driver.getWindowHandles()) {
			driver.switchTo().window(handle);
		}
	}

	@Then("^l'url de la page doit être \"([^\"]*)\"$")
	public void l_url_de_la_page_doit_être(String arg1) throws Throwable {
		assertEquals(arg1, driver.getCurrentUrl());
	}


	@Then("^le prix affiché est un prix \"([^\"]*)\" avec \"([^\"]*)\"$")
	public void le_prix_affiché_est_un_prix_avec(String arg1, String arg2) throws Throwable {
		String xPathLOA = "/html/body/div[1]/div/main/div/div/div[3]/div/div/div/div[1]/div/div";
		String xPathPrice = "/html/body/div[1]/div/main/div/div/div[3]/div/div/div/div[2]/div[1]/div/p";

		String LOA = driver.findElement(By.xpath(xPathLOA)).getText();
		String price = driver.findElement(By.xpath(xPathPrice)).getText();

		assertEquals(LOA, arg1);
		assertEquals(price, arg2);
	}

	@When("^je sélectionne le modèle \"([^\"]*)\"$")
	public void je_sélectionne_le_modèle(String arg1) throws Throwable {
		String xPathBtnAutoPlus = "/html/body/div/div/main/div/div/div[2]/div[2]/div/div[3]/div[1]/div[3]/div[1]";
		String xPathBtnPerformance = "/html/body/div/div/main/div/div/div[2]/div[2]/div/div[3]/div[1]/div[3]/div[2]";

		if(arg1.equals("Grande Autonomie Plus")) {
			actions.click(driver.findElement(By.xpath(xPathBtnAutoPlus)));
		} else {
			actions.click(driver.findElement(By.xpath(xPathBtnPerformance)));
		}
		actions.build().perform();

	}


	@Then("^une économies de carburant estimées avec \"([^\"]*)\"$")
	public void une_économies_de_carburant_estimées_avec(String arg1) throws Throwable {
		String xPathFuel = "/html/body/div/div/main/div/div/div[3]/div/div/div/div[2]/div[2]/p";
		String fuel = driver.findElement(By.xpath(xPathFuel)).getText();

		assertEquals(fuel, arg1);
	}

	@Then("^un montant total avec achat au terme du contrat de \"([^\"]*)\"$")
	public void un_montant_total_avec_achat_au_terme_du_contrat_de(String arg1) throws Throwable {
		String xPathDetail = "/html/body/div/div/main/div/div/div[3]/div/div/div/div[2]/div[3]/div/a";

		actions.click(driver.findElement(By.xpath(xPathDetail)));
		actions.build().perform();

		String selectorTotalPrice = "#totalLeaseAmount";
		String totalPrice = driver.findElement(By.cssSelector(selectorTotalPrice)).getAttribute("value");
		assertEquals(totalPrice, arg1);
	}



	@When("^je veux la capacité de conduite entièrement autonome$")
	public void je_veux_la_capacité_de_conduite_entièrement_autonome() throws Throwable {
		String xPathAutoPilot = "/html/body/div/div/main/div/div/div[1]/div[2]/ul/li[4]";

		actions.click(driver.findElement(By.xpath(xPathAutoPilot)));
		actions.build().perform();

		String xPathPriceLoa = "/html/body/div/div/main/div/div/div[3]/div/div/div/div[2]/div[1]/div/p";
		String xPatchCheckBoxOptionAutoPilot = "/html/body/div/div/main/div/div/div[2]/div[2]/div/div/div/div[2]/div[2]/div[2]/div[1]/i";
		priceLoa = Integer
				.parseInt(driver.findElement(By.xpath(xPathPriceLoa))
				.getText()
				.replaceAll("[^0-9]", ""));

		actions.click(driver.findElement(By.xpath(xPatchCheckBoxOptionAutoPilot)));
		actions.build().perform();

		priceLoaWithAutoPilot = Integer
				.parseInt(driver.findElement(By.xpath(xPathPriceLoa))
						.getText().replaceAll("[^0-9]", ""));
	}


	@Then("^le prix augmente de (\\d+) €/mois$")
	public void le_prix_augmente_de_€_mois(int arg1) throws Throwable {
		assertEquals(priceLoaWithAutoPilot - priceLoa, arg1);
	}

	@When("^je navique et click sur le lien Localisation$")
	public void je_navique_et_click_sur_le_lien_Localisation() throws Throwable {
		String xPathLogoTesla = "/html/body/div/div/main/div/div/div[1]/div[1]/h1/a";

		actions.click(driver.findElement(By.xpath(xPathLogoTesla)));
		actions.build().perform();

		Thread.sleep(2000);

		String cssSelectorSelectFr = "#locale-modal > section > main > div > div.locale-region--Europe > ul > li.region-item.i18n-fr_fr > a";

		driver.findElement((By.cssSelector(cssSelectorSelectFr))).click();

		Thread.sleep(2000);

		String xPathH1 = "/html/body/div[1]/main/div/div[2]/div/div/div/div/div/section/div[1]/h1";

		for (int i = 0 ; i <= 5; i++) {
			actions.click(driver.findElement(By.xpath(xPathH1))).build().perform();
			actions.sendKeys(Keys.PAGE_DOWN).build().perform();
			Thread.sleep(500);
		}

		String xPathLocalisation = "#page-new-homepage > div.dialog-off-canvas-main-canvas.dialog-off-canvas-main-canvas--with-scroll-snapping > footer > ol > li:nth-child(7)";

		driver.findElement(By.cssSelector(xPathLocalisation)).click();
	}


	@After
	public void afterScenario() {
//		driver.quit();
	}

}
