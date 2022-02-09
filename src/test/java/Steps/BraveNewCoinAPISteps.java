package Steps;

import java.net.URI;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class BraveNewCoinAPISteps {

    private static RequestSpecification request;
    private Response response;
    private ValidatableResponse json;

    @Given("^I have a valid API Key for the (.*) URI$")
    public void iSetTheRequestParams(String URI) {
        request = RestAssured
            .given()
                .relaxedHTTPSValidation()//para confiar en el sitio sin necesidad de tener los certificados localmente para pegarle al servicio
                .header("x-rapidapi-key", "6fgh4j58f64gfd6h4fd86ghgf86h48g64h")
                .header("x-rapidapi-host", "bravenewcoin.p.rapidapi.com")
                .contentType(ContentType.JSON)
                .baseUri(URI)
                .log().all();
    }

    @When("^I send a POST request with a valid body to the endpoint (.+) endpoint$")
    public void sendPOSTRequest(String endpoint) {
        String requestBody = "{}";//json con audience y grant_type, ó...
        //File requestBody = new File("src/test/resources/payloads" + nombreDelArchivoConJson); // ese archivo puede venir parametrizado

        response = request
            .when()
                .body(requestBody)
                .post(endpoint)
                .prettyPeek();
    }

    @Then("^I can validate I receive a valid token in the response$")
    public void validateTheToken() {

    }

}
