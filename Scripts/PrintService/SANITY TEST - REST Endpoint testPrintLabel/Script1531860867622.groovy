import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory as CheckpointFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as MobileBuiltInKeywords
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testcase.TestCaseFactory as TestCaseFactory
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testdata.TestDataFactory as TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository as ObjectRepository
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WSBuiltInKeywords
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUiBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory as CheckpointFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as MobileBuiltInKeywords
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testcase.TestCaseFactory as TestCaseFactory
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testdata.TestDataFactory as TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository as ObjectRepository
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WSBuiltInKeywords
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUiBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.RequestObject as RequestObject
//import com.kms.katalon.core.testobject.ResponseObject as ResponseObject
import com.kms.katalon.core.logging.KeywordLogger as KeywordLogger
import io.restassured.http.Method as Method
import io.restassured.response.Response as Response
import io.restassured.specification.RequestSpecification as RequestSpecification
import io.restassured.RestAssured as RestAssured
import io.restassured.function.RestAssuredFunction
import io.restassured.matcher.RestAssuredMatchers as RestAssuredMatchers
import groovy.json.JsonOutput as JsonOutput
import groovy.json.JsonSlurper as JsonSlurper
import org.hamcrest.Matcher as Matcher
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import com.kms.katalon.core.util.KeywordUtil 

/*
 * PRINT SERVICES
 * BASE URL:http://localhost:8013
 * REST ENDPOINT: /api/PrintService/testPrintLabel
 * TEST NAME: SANITY TEST - REST Endpointtest PrintLabel
 * DESCRIPTION: This test will execute the above named endpoint to assure the Print Service is stable.
 * EXPECTED REULTS: Check DM printer prints out a 8.5" x 11" sheet of paper titled "Example Label"
 *  
 */
FileWriter fr=new FileWriter("C:\\Users\\d4scark\\Desktop\\PrintServiceAutoTestReport.txt") //No ',true' so this will create a new file
BufferedWriter br=new BufferedWriter(fr)
PrintWriter out = new PrintWriter(br)

/*
Scanner scan = new Scanner(System.in)
String inputString = scan.nextLine()
print("Enter something")

println("keyboard input = " + inputString)
*/

String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date())

// Specify the base URI to the RESTful web service
String URIString
RestAssured.baseURI = 'http://localhost:8013'
//RestAssured.baseURI = 'http://10.10.100.12:8013'
URIString = RestAssured.baseURI

out.println("**************************************************************")
out.println("**        PRINT SERVICE AUTOMATION TEST REPORT              **")
out.println("**                                                          **")
out.println("**            Runtime: " +  timeStamp + "                  **")
out.println("**                                                          **")
out.println("**************************************************************")
out.println("**               SANITY TEST - REST Endpoint                **")
out.println("**                                                          **")
out.println("**        POST /api/PrintService/testPrintLabel             **")
out.println("**      Test calling the print service to print one label   **")
out.println("**           (Parameters are hardcoded)                     **")
out.println("**                                                          **")
out.println("**            baseURI: " +  URIString + "                **")
out.println("**                                                          **")

if (URIString == "http://localhost:8013"){
	Response response = RestAssured.given().contentType("application/vnd.swisslog.common.label-print-request.v1+json").post('/api/PrintService/testPrintLabel')

//response.statusCode = 400 //Force a failure
	
	if (response.statusCode == 200 || response.statusCode == 201)
	{out.println("** Endpoint ran successfully with a http code = " + response.statusCode + "         **")}
	else
	{
		out.println("** Endpoint FAILURE -- BAD http code = ", response.statusCode, "         **")
		KeywordUtil.markFailed('** Endpoint FAILURE -- BAD http code = ' + response.statusCode)
	}
}
else
{
	out.println("** Endpoint not run, NO DM printer on this URI.             **")
	out.println("**                                                          **")
}
out.println("**                                                          **")
out.println("**************************************************************")
out.println("**                                                          **")
br.close()
out.close()
//println("Response = " + response.asString())

//println"***"
