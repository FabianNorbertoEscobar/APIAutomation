package Steps;

import java.util.List;

import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import io.restassured.RestAssured;

public class APISteps {
    
    private static RequestSpecification request;
    private Response response;
    private ValidatableResponse json;

    @Given("^I send a GET request to the (.+) URI$")
    public void sendGETRequest(String URI){
        request = RestAssured
            .given()
                .baseUri(URI)
                .contentType(ContentType.JSON);
    }

    @Then("^I get a (\\d+) status code$")
    public void validateListOfUsers(int statusCode) {
        response = request
            .when()
                .get("/users/TheFreeRangeTester/repos");

        json = response
            .then()
                .statusCode(statusCode);
    }

    @Then("^I validate there are (\\d+) items on the (.+) endpoint$")
    public void validateSize(int expectedSize, String endpoint) {
        response = request
            .when()
            .get(endpoint);

        List<String> jsonResponse = response.jsonPath().getList("$");
        int actualSize = jsonResponse.size();

        Assert.assertEquals(expectedSize, actualSize);
    }

    @Then("^I validate that is a value: (.+) in the response at (.+) endpoint$")
    public void validateValue(String expectedValue, String endpoint) {
        response = request
            .when()
            .get(endpoint);

        List<String> jsonResponse = response.jsonPath().getList("username");
        
        Assert.assertTrue("El valor " + expectedValue + " no se encuentra en la lista", jsonResponse.contains(expectedValue));
    }

    @Then("^I validate the nested value: (.+) in the response at (.+) endpoint$")
    public void validateNestedValue(String expectedStreet, String endpoint) {
        response = request
            .when()
            .get(endpoint);

        String jsonResponse = response.jsonPath().getString("address.street");
        
        Assert.assertTrue("La calle " + expectedStreet + " no pertenece a ningún usuario", jsonResponse.contains(expectedStreet));
    }

}
