package api.test;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ReqresApi {

@Test
public void register_shouldReturnIdAndToken_thenGetUserById()  {
    Response registerResponse =
        given()
            .baseUri("https://reqres.in")
            .contentType(io.restassured.http.ContentType.JSON)
            .accept(io.restassured.http.ContentType.JSON)
            .header("x-api-key", "reqres_df4042a4c7c54a208f8747b40e2daede")
            .body("""
            {
              "email": "george.bluth@reqres.in",
              "password": "string1212",
              "name": "Morpheus"
            }
            """)
            .when()
            .post("/api/register")
            .then()
            .statusCode(200)
            .body("id", notNullValue())
            .body("token", notNullValue())
            .extract()
            .response();

    int userId = registerResponse.path("id");
    String token = registerResponse.path("token");
    System.out.println("REGISTER OK → id=" + userId + ", token=" + token);


        // ===== 2. GET USER BY ID =====
    given()
            .baseUri("https://reqres.in")
            .accept(io.restassured.http.ContentType.JSON)
            .header("x-api-key", "reqres_df4042a4c7c54a208f8747b40e2daede")
            .pathParam("id", userId)
            .when()
            .get("/api/users/{id}")
            .then()
            .statusCode(200)
            .log().all();
    }
}
