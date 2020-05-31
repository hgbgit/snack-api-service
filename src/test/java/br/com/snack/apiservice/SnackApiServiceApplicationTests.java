package br.com.snack.apiservice;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "br.com.snack.apiservice",
        plugin = "pretty",
        strict = true)
public class SnackApiServiceApplicationTests {
}
