package stepDefinitions;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {

	@Before("@DeletePlace")
	public void beforeScenario() throws IOException
	{
		//execute this code only when place id is null
		//write a code to generate place id
		StepDefinition sd = new StepDefinition();
		if(StepDefinition.placeId==null)
		{
			sd.add_place_payload_with("Pater", "French", "France");
			sd.users_calls_with_http_request("AddPlaceAPI", "POST");
			sd.verify_place_id_created_maps_to_using("Pater", "getPlaceAPI");
		}
	}
	
}
