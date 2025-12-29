package api.test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ReqresApi {

    private static Integer userId;
    private static String token;

    @Test
    public void postRegister200()  {
        Response registerResponse =
                given()
                        .baseUri("https://reqres.in")
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
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

        userId = registerResponse.path("id");
        token = registerResponse.path("token");
        System.out.println("REGISTER OK → id=" + userId + ", token=" + token);
    }

    @Test(dependsOnMethods = "postRegister200")
    public void getUserById_shouldReturn200() {
        if (userId == null) {
            throw new IllegalStateException("userId no disponible. El test de registro falló o no se ejecutó.");
        }

        given()
                .baseUri("https://reqres.in")
                .accept(ContentType.JSON)
                .header("x-api-key", "reqres_df4042a4c7c54a208f8747b40e2daede")
                .header("Authorization", "Bearer " + token) // opcional según mock
                .pathParam("id", userId)
                .when()
                .get("/api/users/{id}")
                .then()
                .statusCode(200)
                .log().all();
    }
}