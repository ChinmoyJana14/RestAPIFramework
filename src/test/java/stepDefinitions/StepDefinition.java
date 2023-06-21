package stepDefinitions;

import static io.restassured.RestAssured.given;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

public class StepDefinition extends Utils {
	
	RequestSpecification res;
	ResponseSpecification responseSpec;
	Response response;
	TestDataBuild data =  new TestDataBuild();
	static String placeId;//if not static variable will set to null for second test case
	JsonPath js;
	
	@Given("Add Place Payload with {string} {string} {string}")
	public void add_place_payload_with(String name, String language, String address) throws IOException {
		
		res = given().spec(requestSpecification())
		.body(data.addPlacePayLoad(name,language, address));		
	}
	@When("users calls {string} with {string} http request")
	public void users_calls_with_http_request(String resource, String method) {
		
		APIResources apiResources = APIResources.valueOf(resource);//invoking the constructor of the enum class with the value of the enum
		System.out.println(apiResources);
		responseSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		if (method.equalsIgnoreCase("POST"))		
			response = res.when().post(apiResources.getResource());
		else if (method.equalsIgnoreCase("GET"))
			response = res.when().get(apiResources.getResource());
	}
	
	@Then("the API call is success with status code {int}")
	public void the_api_call_is_success_with_status_code(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	   Assert.assertEquals(response.getStatusCode(),200);
	}
	
	@Then("{string} in response body is {string}")
	public void in_response_body_is(String key, String ExpectedValue) {
	    // Write code here that turns the phrase above into concrete actions
		   Assert.assertEquals(getJsonPath(response,key),ExpectedValue);
	}

	@Then("verify place_id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String ExpectedName, String resource) throws IOException {
		
		//RequestSpec
		placeId = getJsonPath(response,"place_id");
		res = given().spec(requestSpecification()).queryParam("place_id", placeId);
		users_calls_with_http_request(resource, "GET");	
		String actualName =getJsonPath(response,"name");//Now response has the output of getApi call
		Assert.assertEquals(actualName,ExpectedName);
	}

	@Given("DeletePlace payload")
	public void delete_place_payload() throws IOException {
		res = given().spec(requestSpecification()).body(data.deletePlacePayload(placeId));		
	}





}
