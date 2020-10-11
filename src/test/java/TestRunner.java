import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"classpath:features"},
        glue = {"stepdefs","classpath:data"},
        monochrome = true,
        plugin = {
                "pretty",
                "json:target/reports/report.json",
                "html:target/reports/report",
                "junit:target/reports/Cucumber.xml"
        },
        strict = true
)
public class TestRunner {
}
