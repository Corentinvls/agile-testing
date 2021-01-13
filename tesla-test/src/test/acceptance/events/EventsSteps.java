package test.acceptance.events;

import java.util.concurrent.TimeUnit;

import java.lang.*;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import static org.junit.Assert.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;

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

	@After
	public void afterScenario() {
		driver.quit();
	}

}
