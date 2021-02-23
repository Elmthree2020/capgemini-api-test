package stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class StepDefinitions {

    private Response response;

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://api.citybik.es";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Given("I make a get request to {string}")
    public void iMakeGetRequestTo(String path){
        response = given().get(path);
    }

    @Then("the status code is {int}")
    public void verifyStatusCode(int statusCode){
        response.then().statusCode(statusCode);
    }

    Map<String, Object> getLocationMapForCity(String city) {
        var list = new ArrayList<>();
        List<Map<String, Object>> networks = response.body().jsonPath().get("networks");
        for (Map<String, Object> network : networks) {
            if (((Map<String, Object>) network.get("location")).get("city").equals(city))  {
                list.add(network);
            };
        }
        var locationMap = (Map<String, Object>) ((Map<String, Object>) list.get(0)).get("location");
        return locationMap;
    }

    @And("network for city {string} should have {string} as {string}")
    public void networkForCityShouldHavePropertyStringValue(String city, String value, String property) {
        var locationMap = getLocationMapForCity(city);
        Assert.assertEquals(locationMap.get(property), value);
    }

    @And("network for city {string} should have {float} as {string}")
    public void networkForCityShouldHavePropertyFloatValue(String city, float value, String property) {
        var locationMap = getLocationMapForCity(city);
        Assert.assertEquals(locationMap.get(property), value);
    }
}
