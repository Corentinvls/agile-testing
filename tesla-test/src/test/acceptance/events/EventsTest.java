package test.acceptance.events;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
	features = {"src/test/acceptance/events"}, // ou se situe votre fichier .feature
	plugin = {"pretty", "json:cucumber-reports/events.json"}
	)
public class EventsTest {

}