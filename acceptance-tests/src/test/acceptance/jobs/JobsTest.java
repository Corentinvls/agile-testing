package test.acceptance.jobs;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
	features = {"../src/test/acceptance/jobs"}, // ou se situe votre fichier .feature
	plugin = {"pretty"}
	)
public class JobsTest {

}