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
import com.kms.katalon.core.util.KeywordUtil

/*
 * PRINT SERVICES
 * BASE URL:http://10.10.100.12:8013
 * REST ENDPOINT: /api/PrintService/printLabel
 * TEST NAME: REG PRNTR-REST Endpoint PrintLabel Code 128
 * DESCRIPTION: This test will execute the above named endpoint and will use parameters to print to the REGULAR PRINTER, 
 *  a virtual printer named 'DM Printer'. However this is a NEGATIVE TEST because it is using a
 *  baseURI of http://10.10.100.12:8013 which does not have the DM Print Service on its network and the endpoint and 
 *  should return a 400.
 *  
 * Parameter Notes: 
 *   requestID must have a UUID format, and can be arbitrary for test purposes as long as it has the UUID format.
 *   requestLayout points to the directory (C:\Swisslog\PrintService\LayoutSets\letter) where the JasperSoft 
 * 		layout set (Delivery Manager Order Label.jrxml) is located.
 *   requestPrinter is the name of the virtual printer specified in the printservice.yml file on the C:\Swisslog\config folder
 *   swisslogUniqueId is, for test purposes, any of the items listed in the 'Delivery Item ID's column under Delivery Manager QA Instance 
 *      (aus-w-vm-d00009.thedevcloud.net/portal/#/)->Orders->View/Edit Orders
 *   itemName specifies the name of the item being labeled
 *   destinationLocationName specifies where the item should end up
 * EXPECTED RESULTS: Endpoint should returns an http code of 400 because there is no "DM Printer" on the '.12' network.
 *                  
 */
FileWriter fr = new FileWriter('C:\\Users\\d4scark\\Desktop\\PrintServiceAutoTestReport.txt', true //true - so this will allow for appending to file
)

BufferedWriter br = new BufferedWriter(fr)

PrintWriter out = new PrintWriter(br)

// Specify the base URI to the RESTful web service
String URIString

//RestAssured.baseURI = 'http://localhost:8013'
RestAssured.baseURI = 'http://10.10.100.12:8013'

URIString = RestAssured.baseURI

out.println('**************************************************************')

out.println('**     NEGATIVE TEST POST TestPrinter - REST Endpoint       **')

out.println('**                Bar code Type Code128                     **')

out.println('**        POST /api/PrintService/printLabel                 **')

out.println('**   This test should return a 400 because this printer     **')
out.println('**    is not on the network for:                            **')

out.println(('**            baseURI: ' + URIString) + '                **')

out.println('**                                                          **')

Response response = RestAssured.given().contentType('application/vnd.swisslog.common.label-print-request.v1+json').body(
    (((('{"requestID": "9ae9e09b-9a1d-4ba0-b941-3e86f5421219",\t' + '"requestLayout": "Delivery Manager Order Label Type Code 128",') + 
    '"requestPrinter": "TestPrinter",') + '"swisslogUniqueId": "SWLG-1000-DO-502",') + '"itemName": "TestPrinter Printer Code 128",') + 
    '"destinationLocationName": "TestPrinter Printer Destination"}').post('/api/PrintService/printLabel')

println('response.statuscode = ' + response.statusCode)

// assert.assertEuals(response.statusCode, 200)
if ((response.statusCode == 400)) {
    out.println("** Endpoint ran successfully with a http code = " + response.statusCode + "         **")}

else {
    out.println(('** Endpoint FAILURE -- BAD http code = ' + response.statusCode) + '         **')

    out.println('** Response = ' + response.asString())
	
	KeywordUtil.markFailed("Response HTTP status code does not match. Expected: 400 but Actual: " + response.statusCode)
}

out.println('**                                                          **')

out.println('**************************************************************')

out.println('**                                                          **')

br.close()

out.close()

