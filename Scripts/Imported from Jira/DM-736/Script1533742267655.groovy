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
import com.kms.katalon.core.logging.KeywordLogger as KeywordLogger
import io.restassured.http.Method as Method
import io.restassured.response.Response as Response
import io.restassured.specification.RequestSpecification as RequestSpecification
import io.restassured.RestAssured as RestAssured
import io.restassured.matcher.RestAssuredMatchers as RestAssuredMatchers
import groovy.json.JsonOutput as JsonOutput
import groovy.json.JsonSlurper as JsonSlurper
import org.testng.Assert as Assert
import com.kms.katalon.core.testdata.InternalData

//Call the createNewOrder Keyword to create a new order via REST endpoint api/atp/dispenseorders
//Parameters - quantity, item description, item identifer, destination path
WebUI.comment('Call the createNewOrder Keyword to create a new order via REST endpoint api/atp/dispenseorders')

InternalData registerCarrierdata = findTestData("registerTLCarriersData")
InternalData data = findTestData("postTLCarrierEvent")

def transactionId = CustomKeywords.'com.Utilities.getTransactionId'()

for (def index : 0..registerCarrierdata.getRowNumbers() - 1) {
		
	CustomKeywords.'com.DeliveryManager.registerCarrier' registerCarrierdata.internallyGetValue("Carrier", index), registerCarrierdata.internallyGetValue("Type", index), registerCarrierdata.internallyGetValue("Identifier", index), registerCarrierdata.internallyGetValue("PrepStation", index), 'true'
	
	CustomKeywords.'com.DeliveryManager.registerCarrier' registerCarrierdata.internallyGetValue("Carrier", index), registerCarrierdata.internallyGetValue("Type", index), registerCarrierdata.internallyGetValue("Identifier", index), registerCarrierdata.internallyGetValue("PrepStation", index), 'false'

	CustomKeywords.'com.DeliveryManager.registerCarrier' registerCarrierdata.internallyGetValue("Carrier", index), registerCarrierdata.internallyGetValue("Type", index), registerCarrierdata.internallyGetValue("Identifier", index), registerCarrierdata.internallyGetValue("PrepStation", index), 'true'
	
	for (def eventsIndex : 0..data.getRowNumbers() - 1) {
		
		CustomKeywords.'com.DeliveryManager.postTranslogicCarrierEvent' data.internallyGetValue("Carrier", eventsIndex), data.internallyGetValue("EventType", eventsIndex), data.internallyGetValue("SourceStationId", eventsIndex), data.internallyGetValue("DestinationStationAddress", eventsIndex), data.internallyGetValue("DestinationStationId", eventsIndex), transactionId, data.internallyGetValue("User", eventsIndex)
	}	
	
	CustomKeywords.'com.DeliveryManager.registerCarrier' registerCarrierdata.internallyGetValue("Carrier", index), registerCarrierdata.internallyGetValue("Type", index), registerCarrierdata.internallyGetValue("Identifier", index), registerCarrierdata.internallyGetValue("PrepStation", index), 'false'
	
}
