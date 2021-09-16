package steps;

import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import utils.TriangleCalculator;

import javax.json.JsonObject;
import java.math.BigDecimal;

import static java.math.BigDecimal.ROUND_HALF_UP;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class QaQuizSteps {

    private final String host = ""; // set host
    private final String token = ""; // set token

    private Response response;

    TriangleCalculator triangleCalculator = new TriangleCalculator();

    @Step
    public void sendPostRequest(JsonObject jsonObject){
        response = SerenityRest
                .given()
                .contentType("application/json")
                .header("X-User",token)
                .body(jsonObject.toString())
                .when()
                .post(host + "/triangle");
    }

    @Step
    public void sendPostRequestWithCustomToken(JsonObject jsonObject, String customToken){
        response = SerenityRest
                .given()
                .contentType("application/json")
                .header("X-User", customToken)
                .body(jsonObject.toString())
                .when()
                .post(host + "/triangle");
    }

    @Step
    public void sendPostRequestWithOutToken(JsonObject jsonObject){
        response = SerenityRest
                .given()
                .contentType("application/json")
                .body(jsonObject.toString())
                .when()
                .post(host + "/triangle");
    }

    @Step
    public void sendGetTriangleRequest(String id){
        response = SerenityRest
                .given()
                .header("X-User", token)
                .when()
                .get(host + "/triangle/" + id);
    }

    @Step
    public void sendGetTriangleRequestWithCustomToken(String customToken, String id){
        response = SerenityRest
                .given()
                .header("X-User", customToken)
                .when()
                .get(host + "/triangle/" + id);
    }

    @Step
    public void sendGetTriangleRequestWithOutToken(String id){
        response = SerenityRest
                .given()
                .when()
                .get(host + "/triangle/" + id);
    }

    @Step
    public void sendDeleteRequest(String id){
        response = SerenityRest
                .given()
                .header("X-User", token)
                .when()
                .delete(host + "/triangle/" + id);
    }

    @Step
    public void sendDeleteRequestWithCustomToken(String customToken, String id){
        response = SerenityRest
                .given()
                .header("X-User", customToken)
                .when()
                .delete(host + "/triangle/" + id);
    }

    @Step
    public void sendDeleteRequestWithOutToken( String id){
        response = SerenityRest
                .given()
                .when()
                .delete(host + "/triangle/" + id);
    }

    @Step
    public void sendGetAllRequest(){
        response = SerenityRest
                .given()
                .header("X-User",token)
                .when()
                .get(host + "/triangle/all");

    }

    @Step
    public void sendGetTrianglePerimeterRequest(String id){
        response = SerenityRest
                .given()
                .header("X-User",token)
                .when()
                .get(host + "/triangle/" + id + "/perimeter");
    }

    @Step
    public void calculateTrianglePerimeter(BigDecimal aSide, BigDecimal bSide, BigDecimal cSide){
        BigDecimal initialTrianglePerimeter = triangleCalculator.calculatePerimeter(aSide, bSide, cSide);
        BigDecimal quizTrianglePerimeter = new BigDecimal(response.jsonPath().get("result").toString());
        assertThat(quizTrianglePerimeter, equalTo(initialTrianglePerimeter));
    }

    @Step
    public void sendGetTriangleAreaRequest(String id){
        response = SerenityRest
                .given()
                .header("X-User",token)
                .when()
                .get(host + "/triangle/" + id + "/area");
    }

    @Step
    public void calculateTriangleArea(BigDecimal aSide, BigDecimal bSide, BigDecimal cSide){
        BigDecimal initialTriangleArea = triangleCalculator.calculateArea(aSide, bSide, cSide);
        BigDecimal quizTriangleArea = new BigDecimal(response.jsonPath().get("result").toString()).setScale(2, ROUND_HALF_UP);
        assertThat(quizTriangleArea, equalTo(initialTriangleArea));
    }

    @Step
    public void checkId(String idTriangle){
        String idOne = response.jsonPath().getString("id");
        assertThat(idTriangle,equalTo(idOne.substring(1,idOne.length()-1)));
    }

    @Step
    public void getStatusCode(int code){
        response.then().statusCode(code);
    }

    @Step
    public String getId(){
        String id = response.jsonPath().get("id");
        assertThat(id, not(isEmptyOrNullString()));
        return id;
    }

    @Step
    public void checkTriangleSides(BigDecimal aSide, BigDecimal bSide, BigDecimal cSide){
        BigDecimal a = new BigDecimal(response.jsonPath().get("firstSide").toString());
        BigDecimal b = new BigDecimal(response.jsonPath().get("secondSide").toString());
        BigDecimal c = new BigDecimal(response.jsonPath().get("thirdSide").toString());
        assertThat(aSide, equalTo(a));
        assertThat(bSide, equalTo(b));
        assertThat(cSide, equalTo(c));
    }
}
