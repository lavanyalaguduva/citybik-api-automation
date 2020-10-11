package stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class RetrieveCompanyDetails {
    private static final String BASE_URL = "http://api.citybik.es/v2/networks";
    private static RequestSpecification requestSpec;
    private static ResponseSpecification responseSpec;
    private static Response response;
    private static RequestSpecification request;

    @Given("citybik service is available to get its network details")
    public void citybikServiceIsAvailableToGetItsNetworkDetails() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .build();
        request = given();

        responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();

    }

    @When("I request the service to give all the company details")
    public void iRequestTheServiceToGiveAllTheCompanyDetails() {
        response = request.spec(requestSpec).when().get("/");
    }

    @Then("I should get details of {int} companies")
    public void iShouldGetDetailsOfCompanies(int noOfCompanies) {
        response
                .then().spec(responseSpec)
                .and().assertThat().body("networks", hasSize(noOfCompanies));
    }

    @When("I request the service to give the company details with id {string}")
    public void iRequestTheServiceToGiveTheCompanyDetailsWithId(String networkId) {
        response = request.spec(requestSpec).when().get("/" + networkId);
    }

    @Then("I should get the following details")
    public void iShouldGetTheFollowingDetails(Map<String, String> responseFields) {
        response.then().spec(responseSpec);
        for (Map.Entry<String, String> field : responseFields.entrySet()) {
            if (field.getValue().matches("[-+]?[0-9]*\\.?[0-9]+")) {
                response.then().assertThat().body(field.getKey(), equalTo(Float.parseFloat(field.getValue())));
            } else {
                response.then().assertThat().body(field.getKey(), equalTo(field.getValue()));
            }
        }
    }

    @And("the response should confirm the defined schema")
    public void theResponseShouldConfirmTheDefinedSchema() {
        response.then().assertThat().body(matchesJsonSchemaInClasspath("retrieve_network_for_id.json"));

    }

    @When("I request the service to give the company details with id {string} using the following filers")
    public void iRequestTheServiceToGiveTheCompanyDetailsWithIdUsingTheFollowingFilers(String networkId, List<String> filterFields) {
        response = request
                .spec(requestSpec)
                .when()
                    .queryParam("fields",String.join(", ", filterFields))
                    .get("/"+ networkId);
        response.then().log().all();
    }

    @Then("I should see the response is filtered")
    public void iShouldSeeTheResponseIsFiltered() {
        response.then()
                .spec(responseSpec)
                .assertThat().body(matchesJsonSchemaInClasspath("filter_network_details.json"));

    }
}
