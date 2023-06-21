package resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utils {

	public static RequestSpecification req;//one copy of the variable in memory, regardless of how many instances of the class are created
	
	public RequestSpecification requestSpecification() throws IOException
	{
		if (req==null)
		{
			PrintStream log =  new PrintStream(new FileOutputStream("logging.txt"));
			//POST		
			req = new RequestSpecBuilder().setBaseUri(getGlobalValues("baseURL")).addQueryParam("key", "qaclick123")
					.addFilter(RequestLoggingFilter.logRequestTo(log))
					.addFilter(ResponseLoggingFilter.logResponseTo(log))
			.setContentType(ContentType.JSON).build();
			return req;
		}
		return req;
	}
	
	public static String getGlobalValues(String key) throws IOException
	{
		Properties prop =  new Properties();
		FileInputStream stream = new FileInputStream(System.getProperty("user.dir")+ File.separator +"src\\test\\java\\resources\\global.properties");
		prop.load(stream);
		return prop.getProperty(key);
	}
	
	public String getJsonPath(Response response, String key)
	{
		   String responseStr = response.asString();
		   JsonPath js = new JsonPath(responseStr);
		   return js.get(key).toString();
	}
}
