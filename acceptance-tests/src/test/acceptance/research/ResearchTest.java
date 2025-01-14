package test.acceptance.research;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = {"src/test/acceptance/research"}, // ou se situe votre fichier .feature
		plugin = { "pretty", "json:cucumber-reports/research.json" }
	)
public class ResearchTest {

}