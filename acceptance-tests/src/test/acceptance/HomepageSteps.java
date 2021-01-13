package test.acceptance;

import java.util.concurrent.TimeUnit;

import java.lang.*;

import org.junit.Test;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.*;

import static org.hamcrest.Matchers.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;

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

		driver = new ChromeDriver();

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	@Given("^je suis sur la homepage$")
	public void je_suis_sur_la_homepage() throws Throwable {
		driver.get("https://www.meetup.com/fr-FR/");
	}

	@Then("^le titre doit être \"([^\"]*)\"$")
	public void le_titre_doit_être(String arg1) throws Throwable {
	    assertEquals(driver.getTitle(), arg1);
	}

	@Then("^la description contient \"([^\"]*)\"$")
	public void la_description_doit_être(String arg1) throws Throwable {
		assertTrue(driver.findElement(By.cssSelector("meta[name='description']")).getAttribute("content").contains(arg1));
	}

	@Then("^La punchline doit être \"([^\"]*)\"$")
	public void la_punchline_doit_être(String arg1) throws Throwable {
		String h1 = "/html/body/div[1]/div/div[5]/div[3]/main/div[1]/div/section/div/div[1]/h1/span";

		assertEquals(driver.findElement(By.xpath(h1)).getText(), arg1);
	}

	@Then("^La sous-punchline doit être \"([^\"]*)\"$")
	public void la_sous_punchline_doit_être(String arg1) throws Throwable {
		String subH1 = "/html/body/div[1]/div/div[5]/div[3]/main/div[1]/div/section/div/div[2]/p/span";

		assertEquals(driver.findElement(By.xpath(subH1)).getText(), arg1);
	}

	@Then("^Le bouton doit être \"([^\"]*)\"$")
	public void le_bouton_doit_être(String arg1) throws Throwable {
		String joinButton = "/html/body/div[1]/div/div[5]/div[3]/main/div[1]/div/section/div/div[3]/a";

		assertEquals(driver.findElement(By.xpath(joinButton)).getCssValue("background"), arg1);
	}

	@Then("^Le bouton doit avoir ecrit \"([^\"]*)\"$")
	public void le_bouton_doit_avoir_ecrit(String arg1) throws Throwable {
		String joinButton = "/html/body/div[1]/div/div[5]/div[3]/main/div[1]/div/section/div/div[3]/a";

		assertEquals(driver.findElement(By.xpath(joinButton)).getText(), arg1);
	}

	@Then("^Le bouton doit contenir le lien \"([^\"]*)\"$")
	public void le_bouton_doit_contenir_le_lien(String arg1) throws Throwable {
		String joinButton = "/html/body/div[1]/div/div[5]/div[3]/main/div[1]/div/section/div/div[3]/a";

		assertEquals(driver.findElement(By.xpath(joinButton)).getAttribute("href"), arg1);
	}

	@After
	public void afterScenario() {
		driver.quit();
	}

}
