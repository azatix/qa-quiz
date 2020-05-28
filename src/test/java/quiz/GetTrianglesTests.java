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
public class GetTrianglesTests {

    @Steps
    QaQuizSteps qaQuizSteps;

    private final String customToken = "0928iaoc-9oal-90ap-a9zk-9aoskals919s";
    private final String customTriangle = "cc559d86-3b1c-4e13-bc00-8eeba6b5ccaf";

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

    @Test
    @Title("Send get request to obtain triangle data")
    public void successGetTriangleByGetById(){

        qaQuizSteps.sendPostRequest(withSeparatorJson);
        String id = qaQuizSteps.getId();
        qaQuizSteps.sendGetTriangleRequest(id);
        qaQuizSteps.getStatusCode(200);
        qaQuizSteps.checkTriangleSides(a,b,c);
        qaQuizSteps.sendDeleteRequest(id);
    }

    @Test
    @Title("Send get request to obtain custom triangle")
    public void errorGetTriangleByCustomId(){

        qaQuizSteps.sendGetTriangleRequest(customTriangle);
        qaQuizSteps.getStatusCode(404);
    }

    @Test
    @Title("Send get request with custom token")
    public void errorGetTriangleByCustomToken(){

        qaQuizSteps.sendGetTriangleRequestWithCustomToken(customToken, customTriangle);
        qaQuizSteps.getStatusCode(401);
    }

    @Test
    @Title("Send get request without token")
    public void errorGetTriangleWithOutToken(){

        qaQuizSteps.sendGetTriangleRequestWithOutToken(customTriangle);
        qaQuizSteps.getStatusCode(401);
    }
}
