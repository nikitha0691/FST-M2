import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ProjectTest {
    RequestSpecification reqSpec;
    String name;
     public int id;
    Response response;
    ProjectTest pt;
    Map<String, String> reqBody;
    String sshKey ="ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCStrEhokJCSNqrp7ElMFoZMCRjL9ICXcLs6vlg5poOIkP32qwOuBGQ3mHDP2VJPFbybX4biw7ers/+rTHQ2Jm25fVbSl670p8012VcF/CLO2P3IezbI++GevqWVrQjcjBgyLdjx1l/95XQQdVWtV2uNGjZ+ouf650NRLpCNhabpV2WzVAQG+faSJOXepuCCyFlHS57EaPs363o/dG4mgsV9gDQdPDst/Wcb3gKseEu9vGaUhkow9PfBtNiLSvVR313D4aI4ZHMMzOdHU5iHKuuq34VL46ZkjCr1VjlBaqfqNdxFPJBBW6vfORIuDbd5GLUziUPW0QlI8KNVkwYzCT5 \n";

    @BeforeClass
    public void setUp(){
        reqSpec=new RequestSpecBuilder().setContentType(ContentType.JSON)
                .addHeader("Authorization","token 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").setBaseUri("https://api.github.com").build();
        reqBody = new HashMap<String, String>();
        reqBody.put("title","TestAPIKey");
        reqBody.put("key",sshKey);
    }

    @Test(priority = 1)
    public void postMethod(){

        response = given().spec(reqSpec).body(reqBody).when().post("/user/keys");
        System.out.println(response.asPrettyString());
        id = response.then().extract().path("id");
        System.out.println("id="+id);
        response.then().statusCode(201);



    }

    @Test(priority = 2)
    public void getMethod(){

        response = given().spec(reqSpec).when().pathParam("keyId",id).get("/user/keys"+"/{keyId}");

        int code = response.getStatusCode();
        System.out.println(code);
        response.then().statusCode(200);
        Reporter.log(response.asPrettyString());
    }

    @Test(priority = 3)
    public void deleteMethod(){

        Response response = given().spec(reqSpec).when().pathParam("keyId",id).delete("/user/keys/{keyId}");
        response.then().statusCode(204);
    }



}
