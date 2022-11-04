package activities;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Activity2{
    String baseURI = "https://petstore.swagger.io/v2/user";

    @Test(priority = 0)
    public void postMethod() throws IOException {

        FileInputStream fis = new FileInputStream("src/test/resources/input.json");

        String reqBody = new String(fis.readAllBytes());

        Response response = given().header("content-Type","application/json").body(reqBody).when().post(baseURI);
        System.out.println(response.getBody().asPrettyString());
        fis.close();
        response.then().body("code",equalTo(200));
        response.then().body("message",equalTo("9901"));
       // response.then().body("username",equalTo("hazelt"));

    }
    @Test(priority = 1)
    public void getMethod(){
        Response response = given().contentType(ContentType.JSON)
                .pathParam("username","hazelt").when().post(baseURI+"/{username}");
        System.out.println(response.getBody().asPrettyString());



    }


}
