package test.acceptance.feature;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = {"src/test/acceptance/feature"}, // ou se situe votre fichier .feature
		plugin = { "pretty", "json:cucumber-reports/feature.json" }
)
public class FeaturePageTest {

}