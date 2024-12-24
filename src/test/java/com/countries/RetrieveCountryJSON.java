package com.countries;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RetrieveCountryJSON {
	@Test
	public void retrieveCountry() {
		RestAssured.baseURI = "https://demo.spreecommerce.org";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.request(Method.GET, "/api/v2/storefront/countries/ind");

		String responseBody = response.getBody().asString();
		System.out.println("Response Body is =>  " + responseBody);
		int statusCode = response.getStatusCode();
		System.out.println("Status code is =>  " + statusCode);
		Assert.assertEquals(200, statusCode);

		Assert.assertEquals(responseBody.contains("INDIA") /* Expected value */, true /* Actual Value */);
		JsonPath jsonPathEvaluator = response.getBody().jsonPath();
		String iso_name_act = jsonPathEvaluator.get("data.attributes.iso_name").toString();
		Assert.assertEquals(iso_name_act, "INDIA");

		String name_act = jsonPathEvaluator.get("data.attributes.name").toString();
		Assert.assertEquals(name_act, "India");
	}
}
