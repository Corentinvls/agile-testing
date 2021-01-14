package test.acceptance.home;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = {"src/test/acceptance/home"}, // ou se situe votre fichier .feature
		plugin = { "pretty", "json:cucumber-reports/home.json" }
)
public class HomepageTest {

}