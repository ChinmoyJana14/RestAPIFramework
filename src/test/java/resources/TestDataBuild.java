package resources;

import java.util.ArrayList;
import java.util.List;

import pojo.AddPlace;
import pojo.Location;

public class TestDataBuild {
	
	public AddPlace addPlacePayLoad(String name, String language, String address) {
		AddPlace ap = new AddPlace();
		ap.setLanguage(language);
		ap.setAddress(address);
		ap.setName(name);
		ap.setPhone_number("123443444");
		ap.setWebsite("abc.com");
		ap.setAccuracy(50);
		
		List<String> type = new ArrayList<String>();
		type.add("shop");
		type.add("wine_shop");
		ap.setTypes(type);
		
		Location l = new Location();
		l.setLat(1223.233);
		l.setLng(-1211.32432);
		ap.setLocation(l);
		return ap;
	}
	
	public String deletePlacePayload(String placeId)//using https://www.freeformatter.com/json-escape.html
	{
		return "{\r\n    \"place_id\":\""+placeId+"\"\r\n}";
	}

}
