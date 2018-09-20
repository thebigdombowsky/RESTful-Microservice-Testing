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
import io.restassured.function.RestAssuredFunction as RestAssuredFunction
import io.restassured.matcher.RestAssuredMatchers as RestAssuredMatchers
import groovy.json.JsonOutput as JsonOutput
import groovy.json.JsonSlurper as JsonSlurper
import org.hamcrest.Matcher as Matcher
import com.kms.katalon.core.util.KeywordUtil// as KeywordUtil

/*
 * PRINT SERVICES
 * BASE URL:http://localhost:8013
 * REST ENDPOINT: /api/PrintService/printLabel
 * TEST NAME: GET printers-REST Endpoint
 * DESCRIPTION: This test will execute the above named endpoint and will obtain a list of available Windows printers
 *
 *  
 */
FileWriter fr = new FileWriter('C:\\Users\\d4scark\\Desktop\\PrintServiceAutoTestReport.txt', true)

BufferedWriter br = new BufferedWriter(fr)

PrintWriter out = new PrintWriter(br)

// Specify the base URI to the RESTful web service
String URIString

//RestAssured.baseURI = 'http://localhost:8013'
RestAssured.baseURI = 'http://10.10.100.12:8013'

URIString = RestAssured.baseURI

out.println('**************************************************************')

out.println('**               GET Printers - REST Endpoint               **')

out.println('**                                                          **')

out.println('**        GET /api/PrintService/printers                    **')

out.println('**          List all Windows printers                       **')

out.println('**                                                          **')

out.println(('**            baseURI: ' + URIString) + '             **')

out.println('**                                                          **')

Response response = RestAssured.given().contentType('application/vnd.swisslog.common.label-print-request.v1+json').get('/api/PrintService/printers')

//response.statusCode = 400 //Force a failure
if ((response.statusCode == 200) || (response.statusCode == 201)) {
    out.println(('** Endpoint ran successfully with a http code = ' + response.statusCode) + '         **')
} else {
    KeywordUtil.markFailed('Endpoint FAILURE -- BAD http code = ' + response.statusCode)

    out.println(('** Endpoint FAILURE -- BAD http code = ' + response.statusCode) + '         **')

    out.println('** Response = ' + response.asString())
}

out.println('**                                                          **')

out.println('**************************************************************')

out.println('**                                                          **')

br.close()

out.close()

//response.body()
//println("Response.body() = " + response.body().asString())

String listOfPrinters = response.body().asString()

println('listOfPrinters = ' + listOfPrinters)

if (URIString.equals('http://10.10.100.12:8013'))
{
	String Dot12ListOfPrinters = 'Intermec PC43t (203 dpi)\r\nMicrosoft XPS Document Writer\r\n'
	println("Dot12ListOfPrinters = " + Dot12ListOfPrinters)

	if (listOfPrinters.equals(Dot12ListOfPrinters)) {
		println('listOfPrinters is Correct')} 
	else {
		println('listOfPrinters is NOT Correct')
		KeywordUtil.markFailed('Returned List of Printers is incorrect')
		println('listOfPrinters = ' + listOfPrinters)
		println('Dot12ListOfPrinters = ' + Dot12ListOfPrinters)
		}
}
else
{
	String localListOfPrinters	= 'Send To OneNote 2016\r\nMicrosoft XPS Document Writer\r\nMicrosoft Print to PDF\r\nIntermec PC43t (203 dpi)\r\nCutePDF Writer\r\n\\\\SUSD4S-PRT001\\SUSB4P-0001\r\n\\\\SUSD4S-PRT001\\SUSW4P-0010\r\n\\\\SUSD4S-PRT001\\SUSW4P-0009\r\n\\\\SUSD4S-PRT001\\SUSB4P-0002\r\n'
	if (listOfPrinters.equals(localListOfPrinters)) {
		println('listOfPrinters is Correct')}
	else {
		println('listOfPrinters is NOT Correct')}
	
}

