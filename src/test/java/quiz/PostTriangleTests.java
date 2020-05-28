package quiz;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Test;
import org.junit.runner.RunWith;
import steps.QaQuizSteps;

import javax.json.Json;
import javax.json.JsonObject;
import java.math.BigDecimal;

import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.math.BigDecimal.valueOf;
import static java.util.concurrent.ThreadLocalRandom.current;

@RunWith(SerenityRunner.class)
public class PostTriangleTests {

    @Steps
    QaQuizSteps qaQuizSteps;

    private final String customToken = "0928iaoc-9oal-90ap-a9zk-9aoskals919s";

    private final double min = 3.0;
    private final double max = 7.0;

    BigDecimal a = valueOf(current().nextDouble(min, max)).setScale(1, ROUND_HALF_UP);
    BigDecimal b = valueOf(current().nextDouble(min, max)).setScale(1, ROUND_HALF_UP);
    BigDecimal c = valueOf(current().nextDouble(min, max)).setScale(1, ROUND_HALF_UP);

    String triangleSides = a + ";" + b + ";" + c;

    JsonObject withSeparatorJson = Json.createObjectBuilder()
            .add("input", triangleSides)
            .add("separator", ";")
            .build();

    JsonObject woSeparatorJson = Json.createObjectBuilder()
            .add("input", triangleSides)
            .build();

    @Test
    @Title("Send post request to add new triangle")
    public void successAddNewTriangleByPostRequest(){

        qaQuizSteps.sendPostRequest(withSeparatorJson);
        String id = qaQuizSteps.getId();
        qaQuizSteps.getStatusCode(200);
        qaQuizSteps.sendDeleteRequest(id);
    }

    @Test
    @Title("Send post request to add new triangle without separator")
    public void successAddNewTriangleWithOutSeparatorByPostRequest(){

        qaQuizSteps.sendPostRequest(woSeparatorJson);
        String id = qaQuizSteps.getId();
        qaQuizSteps.getStatusCode(200);
        qaQuizSteps.sendDeleteRequest(id);
    }

    @Test
    @Title("Send post request with custom token")
    public void errorAddNewTriangleWithCustomTokenByPostRequest(){

        qaQuizSteps.sendPostRequestWithCustomToken(withSeparatorJson, customToken);
        qaQuizSteps.getStatusCode(401);
    }

    @Test
    @Title("Send post request without token")
    public void errorAddNewTriangleWithOutTokenByPostRequest(){

        qaQuizSteps.sendPostRequestWithOutToken(withSeparatorJson);
        qaQuizSteps.getStatusCode(401);
    }
}