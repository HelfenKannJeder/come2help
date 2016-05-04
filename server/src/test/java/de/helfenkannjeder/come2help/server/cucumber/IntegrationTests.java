package de.helfenkannjeder.come2help.server.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(format = { "pretty", "html:target/cucumber"}, strict = true, tags = "@TemporaryDisabled")
public class IntegrationTests {
}