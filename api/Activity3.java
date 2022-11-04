package activities;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Activity3 {

    RequestSpecification reqSpec;
    ResponseSpecification resSpec;
    Map<Object,Object> addPets1;
    Response response;

    @BeforeClass
    public void setUp() {
        reqSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).setBaseUri("https://petstore.swagger.io/v2/pet")
                .build();
        resSpec = new ResponseSpecBuilder().expectStatusCode(200)
                .expectBody("status", equalTo("alive")).build();

    }

    @DataProvider
    public Object[][] petDataProvider() {
        Object[][] petDetails = new Object[][]{
                {77232, "Riley", "alive"},
                {77233, "Hansel", "alive"}
        };
        return petDetails;
    }

    @Test(priority=1)
    public void postmethod(){
        addPets1 = new HashMap<Object,Object>();
        addPets1.put("id",77232);
        addPets1.put("name","Riley");
        addPets1.put("status","alive");
        addPets1.put("id",77233);
        addPets1.put("name","Hansel");
        addPets1.put("status","alive");

        response = given().spec(reqSpec).body(addPets1).when().post();
        System.out.println(response.getBody().asPrettyString());

    }

    @Test(dataProvider = "petDataProvider",priority = 2)
    public void getMethod(int id,String name, String status){
        response = given().spec(reqSpec).pathParam("petId",id).when().get("/{petId}");
        response.then().spec(resSpec).body("name",equalTo(name));

    }

    @Test(dataProvider = "petDataProvider", priority=3)
    public void deletePets(int id, String name, String status) {
        Response response = given().spec(reqSpec)
                .pathParam("petId", id)
                .when().delete("/{petId}");

        response.then().body("code", equalTo(200));
    }
}

