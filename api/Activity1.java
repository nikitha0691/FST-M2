package activities;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Activity1 {

    String baseURI = "https://petstore.swagger.io/v2/pet";
    Map<String,String> reqBody1;
    @Test
    public void postMethod(){
        reqBody1 = new HashMap<String,String>();
        reqBody1.put("id","77232");
        reqBody1.put("name","Riley");
        reqBody1.put("status","alive");
        //String reqBody = "{\"id\" : 77232,\"name\" : \"Riley\", \"status\" : \"alive\"}";
        Response response = given().contentType(ContentType.JSON).body(reqBody1).when().post(baseURI);
        System.out.println(response.body().asPrettyString());
        //String res = response.body().asString();
        response.then().body("id",equalTo(77232));
        response.then().body("name",equalTo("Riley"));
        response.then().body("status",equalTo("alive"));
    }

    @Test
    public void getMethod(){
        Response response = given().contentType(ContentType.JSON)
                .pathParam("petId",77232).when().get(baseURI+"/{petId}");

        response.then().body("id",equalTo(77232));
        response.then().body("name",equalTo("Riley"));
        response.then().body("status",equalTo("alive"));

    }

    @Test
    public void deleteMethod(){
        Response response = given().contentType(ContentType.JSON).when()
                .pathParam("petId",77232).delete(baseURI+"/{petId}");
        response.then().body("code",equalTo(200));
        response.then().body("message",equalTo("77232"));

    }


}
