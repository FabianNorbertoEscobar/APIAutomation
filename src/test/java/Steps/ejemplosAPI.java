package Steps;

import static io.restassured.RestAssured.given;

import java.util.Base64;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ejemplosAPI {

    public void GETRequest() {
        given()
            .baseUri("https://api.github.com")
        .when()
            .get("/users/TheFreeRangeTester/repos")
        .getBody().toString();
    }

    public void POSTRequest() {
        given()
            .baseUri("https://api.blogEjemplo.com")
        .when()
            .post("/posts", "Titulo:Texto");
    }

    public void PUTRequest() {
        given()
            .baseUri("https://api.github.com")
        .when()
            .put("/put", "");
    }

    public void DELETERequest() {
        given()
            .baseUri("https://api.blogEjemplo.com/posts/Texto")
        .when()
            .delete();
    }

    public void SOAPRequest() {
        String xmlEnvelope= "<>";
        given()
            .header("SOAPAction", "Define")
            .baseUri("https://api.blogspotEjemplo.com")
        .when()
            .body(xmlEnvelope)
        .post("/endpoint");
    }

    public void basicAuth(String username, String password) {
        given()
            .auth().basic(username, password)
        .when()
            .get("AUTH_ENDPOINT")
        .then()
            .assertThat().statusCode(200);
    }

    public void formAuth(String username, String password) {
        given()
            .auth().form(username, password)
        .when()
            .get("SERVICE")
        .then()
            .assertThat().statusCode(200);
    }

    /**
     * 1 - Obtener el código del servicio original para obtener el token.
     * 2 - Obtener el token intercambiando el código que obtuvimos.
     * 3 - Acceder al recurso protegido, con nuestro token.
     */

    public static String clientId = "";
    public static String redirectUri = "";
    public static String scope = "";
    public static String username = "";
    public static String password = "";
    public static String grantType = "";
    public static String accessToken = "";

    public static String encode(String str1, String str2) {
        return new String(Base64.getEncoder().encode((str1 + ":" + str2).getBytes()));
    }

    public static Response getCode() {
        String authorization = encode(username, password);

        return
            given()
                .header(authorization, "Basic " + authorization)
                .contentType(ContentType.URLENC)
                .formParam("response_type", "code")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("grant_type", grantType)
                .post("/oauth/token")
            .then()
                .statusCode(200)
                .extract()
                .response();
    }

    public static String parseForOAuthCode(Response response) {
        return response.jsonPath().getString("code");
    }

    public static Response getToken(String authCode) {
        String authorization = encode(username, password);

        return
            given()
                .header(authorization, "Basic " + authorization)
                .contentType(ContentType.URLENC)
                .formParam("response_type", authCode)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("grant_type", scope)
                .post("/oauth/authorize")
            .then()
                .statusCode(200)
                .extract()
                .response();        
    }

    public static String parseForToken(Response loginResponse) {
        return loginResponse.jsonPath().getString("access_token");
    }

    public static void getFinalService() {
        given()
            .auth().oauth2(accessToken)
        .when()
            .get("/service")
        .then()
            .statusCode(200);
    }

    public static void getFinalService2() {
        given()
            .header("Authorization", "Bearer " + accessToken)
        .when()
            .get("/service")
        .then()
            .statusCode(200);
    }

}
