package com.notes;

import java.io.IOException;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
 
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
 
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CreateNoteExtentReport {
	
	String outh_token;
	String notes_id;
	String rand_title;
	WebDriver driver;

	// builds a new report using the html template
	ExtentSparkReporter htmlReporter;
	ExtentReports extent;
	// helps to generate the logs in test report.
	ExtentTest test;

	@BeforeClass()
	public void startReport() {
		// initialize the HtmlReporter
		htmlReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/ExtentReport/CreateNotesResult.html");
		// initialize ExtentReports and attach the HtmlReporter
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		// To add system or environment info by using the setSystemInfo method.
		extent.setSystemInfo("OS", System.getProperty("os.name"));
		extent.setSystemInfo("QA Name", "Vishvajit");

		// configuration items to change the look and feel
		// add content, manage tests etc
		// htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setDocumentTitle("Create Notes Report for API's Test");
		htmlReporter.config().setReportName("Smoke Test");
		// htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.DARK);
		htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");

	}

	@AfterClass
	public void CloseExtentReport()

	{
		// to write or update test information to reporter
		extent.flush();
	}

	@BeforeTest
	public void getToken() {

		outh_token = Base_Class.GetToken("vishvajit@abc.com", "abc123");

	}

	@Test(priority = 1)
	public void createNotes() throws IOException, ParseException {

		test = extent.createTest("Test Case 1", "Create Notes using File");
		RestAssured.baseURI = "https://practice.expandtesting.com";
		RequestSpecification request = RestAssured.given();

		// Add a header stating the Request body is a JSON
		request.header("Content-Type", "application/json");
		request.header("x-auth-token", outh_token);
		request.body(Base_Class.readFile("Create_Note.json").toJSONString());
		// POST the Response
		Response response = request.request(Method.POST, "/notes/api/notes");
		// Response response = request.request(Method.POST,"/spree_oauth/token");
		response.prettyPrint();
		int statusCode = response.getStatusCode();
		// System.out.println(response.asString());
		Assert.assertEquals(statusCode, 200);
		// To get the Token from JSON Response
		JsonPath jsonPathEvaluator = response.getBody().jsonPath();
		String success_msg = jsonPathEvaluator.get("message").toString();
		Assert.assertEquals(success_msg, "Note successfully created");

		// Capture Title and Verify that Title got generated
		String fname = jsonPathEvaluator.get("data.title").toString();
		System.out.println("Title is =>  " + fname);
		Assert.assertEquals("Data_File", fname);
		// Get the ID of the Notes
		notes_id = jsonPathEvaluator.get("data.id").toString();

	}

	@Test(priority = 2)
	public void updateNotes() {

		test = extent.createTest("Test Case 2", "Update Notes using File");
		RestAssured.baseURI = "https://practice.expandtesting.com";
		RequestSpecification request = RestAssured.given();

		JSONObject requestParams = new JSONObject();
		requestParams.put("title", "TestNG_API_Updated");
		requestParams.put("description", "Updated via RestAssured");
		requestParams.put("category", "Work");
		requestParams.put("completed", "true");
		// Add a header stating the Request body is a JSON
		request.header("Content-Type", "application/json");
		request.header("x-auth-token", outh_token);
		request.body(requestParams.toJSONString());
		// POST the Response
		Response response = request.request(Method.PUT, "/notes/api/notes/" + notes_id);
		response.prettyPrint();
		int statusCode = response.getStatusCode();
		// System.out.println(response.asString());
		Assert.assertEquals(statusCode, 200);
		// To get the Token from JSON Response
		JsonPath jsonPathEvaluator = response.getBody().jsonPath();
		String success_msg = jsonPathEvaluator.get("message").toString();
		Assert.assertEquals(success_msg, "Note successfully Updated");

	}

	@Test(priority = 3)
	public void DeleteNotes() {
		test = extent.createTest("Test Case 3", "Delete Notes After Create Notes");

		RestAssured.baseURI = "https://practice.expandtesting.com";
		RequestSpecification request = RestAssured.given();

		request.header("x-auth-token", outh_token);
		// POST the Response
		Response response = request.request(Method.DELETE, "/notes/api/notes/" + notes_id);
		// Response response = request.request(Method.POST,"/spree_oauth/token");
		response.prettyPrint();
		int statusCode = response.getStatusCode();
		// System.out.println(response.asString());
		Assert.assertEquals(statusCode, 200);
		// To get the Token from JSON Response
		JsonPath jsonPathEvaluator = response.getBody().jsonPath();
		String success_msg = jsonPathEvaluator.get("message").toString();
		Assert.assertEquals(success_msg, "Note successfully deleted");

	}

	@AfterMethod
	public void getResult(ITestResult result) throws Exception {
		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " = FAILED ", ExtentColor.RED));
			test.fail(result.getThrowable());

		} else if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " = PASSED ", ExtentColor.GREEN));
		} else {
			test.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " = SKIPPED ", ExtentColor.ORANGE));
			test.skip(result.getThrowable());
		}
	}

}
