Feature: Validating Place API's
@AddPlace @Regression
Scenario Outline: Verify if place is being add sucessfully using AddPlaceAPLI

	Given Add Place Payload with "<name>" "<language>" "<address>"
	When users calls "AddPlaceAPI" with "Post" http request
	Then the API call is success with status code 200
	And "status" in response body is "OK"
	And "scope" in response body is "APP"
	And verify place_id created maps to "<name>" using "getPlaceAPI"
	
Examples:
	|name |language | address		|
	|abc  | English | 12 ABC Street |
	|Xyz  | Dutch   | 234 xyz St    |

@DeletePlace
Scenario: Verify delete functionality working
	
		Given DeletePlace payload
		When users calls "deletePlaceAPI" with "Post" http request
		Then the API call is success with status code 200
		And "status" in response body is "OK"
		
 