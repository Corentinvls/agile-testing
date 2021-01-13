package test.acceptance.jobs;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JobsSteps {

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
		// Seems no more working in last Chrome versions
		// driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	@Given("^je suis sur \"([^\"]*)\"$")
	public void je_suis_sur(String arg1) throws Throwable {
		driver.get(arg1);
	}

	@Then("^le titre pour job doit être \"([^\"]*)\"$")
	public void le_titre_pour_job_doit_être(String arg1) throws Throwable {
		String punchLineXPath = "/html/body/div[1]/div/div[5]/div[3]/main/div[1]/section/div/div[1]/p";

		WebElement punchLine = driver.findElement(By.xpath(punchLineXPath));
		assertEquals(punchLine.getText(), arg1);
	}


	@Then("^la button contient \"([^\"]*)\"$")
	public void la_button_contient(String arg1) throws Throwable {
		String btnXPath = "/html/body/div[1]/div/div[5]/div[3]/main/div[1]/section/div/a";
		WebElement punchLine = driver.findElement(By.xpath(btnXPath));
		assertEquals(punchLine.getText(), arg1);
	}

	@When("^je clicke sur le bouton Explore Opportunities$")
	public void je_clicke_sur_le_bouton_Explore_Opportunities() throws Throwable {
		Actions action = new Actions(driver);
		String btnXPath = "/html/body/div[1]/div/div[5]/div[3]/main/div[1]/section/div/a";
		WebElement btnAnchor = driver.findElement(By.xpath(btnXPath));

		action.click(btnAnchor);
		action.build().perform();
	}

	@Then("^Le nombre d'emploies affiché est de \"([^\"]*)\"$")
	public void le_nombre_d_emploies_affiché_est_de(String arg1) throws Throwable {
		String ulCardListXPath = "/html/body/div[1]/div/div[5]/div[3]/main/div[4]/section[1]/div[3]/ul";
		WebElement ulCardList = driver.findElement(By.xpath(ulCardListXPath));
		List<WebElement> cardList = ulCardList.findElements(By.tagName("li"));

		assertEquals(cardList.size(), Integer.parseInt(arg1));
	}

	@Then("^le bouton all opportunities redirige vers \"([^\"]*)\"$")
	public void le_bouton_all_opportunities_redirige_vers(String arg1) throws Throwable {
		String anchorSeeAllXPath = "/html/body/div[1]/div/div[5]/div[3]/main/div[4]/section[2]/div[1]/span/a";

		WebElement anchorSeeAll = driver.findElement(By.xpath(anchorSeeAllXPath));
		assertEquals(anchorSeeAll.getAttribute("href"), arg1);
	}


	@Then("^la punchline est \"([^\"]*)\"$")
	public void la_punchline_est(String arg1) throws Throwable {
		String punchlineXPath = "/html/body/div[1]/div/div[5]/div[3]/main/div[6]/section/div/div/p/span";
		WebElement punchline = driver.findElement(By.xpath(punchlineXPath));
		assertEquals(punchline.getText(), "Perks and benefits");
	}

	@Then("^Le nombre d'illustration affiché est de \"([^\"]*)\"$")
	public void le_nombre_d_illustration_affiché_est_de(String arg1) throws Throwable {
		String ulXPath = "/html/body/div[1]/div/div[5]/div[3]/main/div[6]/section/div/ul";
		WebElement ulCardList = driver.findElement(By.xpath(ulXPath));
		List<WebElement> cardList = ulCardList.findElements(By.tagName("li"));

		assertEquals(cardList.size(), 6);
	}




	@After
	public void afterScenario() {
		driver.quit();
	}

}
