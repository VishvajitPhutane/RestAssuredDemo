package com.notes;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import io.restassured.RestAssured;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class All_SOAP_Requests extends SOAP_TestData {

	@Test(dataProvider = "allSOAPrequest")
		public void post_operation(String fileName, String result, String value) {

		File input = new File(".\\TestData\\"+fileName);
	         RestAssured.baseURI="http://www.dneonline.com";
	         
	         Response response=given()
	                .header("Content-Type", "text/xml")
	                .and()
	                .body(input)
	         .when()
	            .post("/calculator.asmx")
	         .then()
	                .statusCode(200)
	                .and()
	                .log().all()
	                .extract().response();
	         
	        XmlPath xmlpath= new XmlPath(response.asString());//Converting string into xml path to assert
	        String rate=xmlpath.getString(result);
	        Assert.assertEquals(value, rate);
	        System.out.println("Result value returned is: " +  rate);
	}
	
}