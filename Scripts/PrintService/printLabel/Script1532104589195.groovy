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
import com.kms.katalon.core.testobject.RequestObject as RequestObject
import com.kms.katalon.core.logging.KeywordLogger as KeywordLogger
import io.restassured.http.Method as Method
import io.restassured.response.Response as Response
import io.restassured.specification.RequestSpecification as RequestSpecification
import io.restassured.RestAssured as RestAssured
import io.restassured.matcher.RestAssuredMatchers as RestAssuredMatchers
import groovy.json.JsonOutput as JsonOutput
import groovy.json.JsonSlurper as JsonSlurper
import org.testng.Assert as Assert

// Specify the base URL to the RESTful web service
RestAssured.baseURI = 'http://10.10.100.12'

RestAssured.port = 8013

inputFile = new File('C:\\Dev\\dataFiles\\printLabel.json')

KeywordLogger Log = new KeywordLogger()

//Call the printLabel endpoint to ensure a label may be printed
try {
    Response response = RestAssured.given().body(inputFile).log().all().contentType('application/vnd.swisslog.common.label-print-request.v1+json').post(
        '/api/PrintService/printLabel', FailureHandling.STOP_ON_FAILURE)

    println(response.statusCode())

    def slurper = new JsonSlurper()

    def result = slurper.parseText(response.asString())

    println(result)
}
catch (def e) {
    println(e)


} 

