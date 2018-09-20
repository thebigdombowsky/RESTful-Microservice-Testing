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
import com.kms.katalon.core.testdata.InternalData as InternalData

//Call the updateOrderQuantity Keyword to create a new order via REST endpoint api/dispenseorders/itemCount
//Parameters - quantity, destination location Id
WebUI.comment('Call the createNewOrder Keyword to create a new order via REST endpoint api/dispenseorders')
WebUI.comment('Call the cancelOrder Keyword to change the quantity of the order just created via REST endpoint api/dispenseorders')

InternalData data = findTestData('dispenseOrdersData')

for (def index : (0..data.getRowNumbers() - 1)) {
    def dispenseOrderResult = CustomKeywords.'com.SwisslogUtilities.createNewOrder'(data.internallyGetValue('Quantity', 
            index), data.internallyGetValue('Description', index), data.internallyGetValue('Identifier', index), data.internallyGetValue(
            'Location', index))
	
	CustomKeywords.'com.SwisslogUtilities.cancelOrder'dispenseOrderResult.locationId, dispenseOrderResult.destinationLocationId, 
	dispenseOrderResult.swisslogUniqueId, dispenseOrderResult.orderNumber, data.internallyGetValue('Quantity', 
            index), data.internallyGetValue('Description', index)
}