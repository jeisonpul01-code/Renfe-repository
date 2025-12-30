package api.test;

import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.Report;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class ReqresApi {

    private static Integer userId;
    private static String token;

    private static final String BASE_URI = "https://reqres.in";
    private static final String API_KEY  = "reqres_df4042a4c7c54a208f8747b40e2daede";

    @Test
    public void postApiRegister_200() {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos, true, StandardCharsets.UTF_8);

        Report.step("POST /api/register - executing");

        Response registerResponse =
                given()
                        .baseUri(BASE_URI)
                        .accept(ContentType.JSON)
                        .contentType(ContentType.JSON)
                        .header("x-api-key", API_KEY)
                        .body("""
                {
                  "email": "eve.holt@reqres.in",
                  "password": "pistol"
                }
                """)
                        .filter(new RequestLoggingFilter(ps))
                        .filter(new ResponseLoggingFilter(ps))
                        .when()
                        .post("/api/register")
                        .then()
                        .statusCode(200)
                        .body("id", notNullValue())
                        .body("token", notNullValue())
                        .extract()
                        .response();

        // log request/response into Extent
        Report.info("<pre>" + baos.toString(StandardCharsets.UTF_8) + "</pre>");

        userId = registerResponse.path("id");
        token  = registerResponse.path("token");

        Report.pass("Stored id=" + userId + " and token=" + token);
    }

    @Test(dependsOnMethods = "postApiRegister_200")
    public void getApiUserId_200() {

        Assert.assertNotNull(userId, "userId is null - register test did not store it");
        Assert.assertNotNull(token, "token is null - register test did not store it");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos, true, StandardCharsets.UTF_8);

        Report.step("GET /api/users/{id} - executing with id=" + userId);

        given()
                .baseUri(BASE_URI)
                .accept(ContentType.JSON)
                .header("x-api-key", API_KEY)
                .pathParam("id", userId)
                .filter(new RequestLoggingFilter(ps))
                .filter(new ResponseLoggingFilter(ps))
                .when()
                .get("/api/users/{id}")
                .then()
                .statusCode(200);

        // log request/response into Extent
        Report.info("<pre>" + baos.toString(StandardCharsets.UTF_8) + "</pre>");

        Report.pass("GET user OK for id=" + userId);
    }
}