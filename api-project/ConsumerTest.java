package liveProject;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

@ExtendWith(PactConsumerTestExt.class)
public class ConsumerTest {
    Map<String, String> requestHeaders = new HashMap<>();
    String resource_path = "/api/users";


    @Pact(consumer = "UserConsumer", provider = "UserProvider")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        //Set Headers
        requestHeaders.put("Content-Type", "application/json");

        DslPart reqResBody = new PactDslJsonBody()
                .numberType("id", 123)
                .stringType("firstName", "Pratima")
                .stringType("lastName", "Rajput")
                .stringType("email", "pratima@gmail.com");

        return builder.given("Request to create a user ")
                .uponReceiving("Request to create a user ")
                .method("POST")
                .headers(requestHeaders)
                .path(resource_path)
                .body(reqResBody)
                .willRespondWith()
                .status(201)
                .body(reqResBody)
                .toPact();
    }
  

    @Test
    @PactTestFor(providerName = "UserProvider", port = "8282")   
    public void consumerTest() {
    
        String baseURI = "http://localhost:8282";

        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put("id", 123);
        reqBody.put("firstName", "Pratima");
        reqBody.put("lastName", "Rajput");
        reqBody.put("email", "pratimarajput@gmail.com");


        Response response = given().headers(requestHeaders).body(reqBody)
                .when().post(baseURI + resource_path);

        System.out.println(response.body().asPrettyString());

        response.then().statusCode(201);
    }

}
