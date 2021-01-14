package test.acceptance.config;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
	features = {"src/test/acceptance/config"},
	plugin = { "pretty", "json:cucumber-reports/config.json" }
	)
public class ConfigpageTest {

}