package test.acceptance.meetup;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = {"src/test/acceptance/meetup.json"}, // ou se situe votre fichier .feature
		plugin = { "pretty", "json:cucumber-reports/meetup.json.json" }
	)
public class MeetupTest {

}