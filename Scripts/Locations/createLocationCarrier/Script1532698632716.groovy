package com

import com.kms.katalon.core.logging.KeywordLogger as KeywordLogger
import io.restassured.response.Response as Response
import io.restassured.RestAssured as RestAssured
import groovy.json.JsonOutput as JsonOutput
import groovy.json.JsonSlurper as JsonSlurper
import org.testng.Assert as Assert
import groovy.json.JsonBuilder as JsonBuilder


// Specify the base URL and port to the RESTful web service
RestAssured.baseURI = 'http://10.10.100.12'
RestAssured.port = 8002

//Create a new log object to communicate test status
KeywordLogger Log = new KeywordLogger()

def slurper = new JsonSlurper()

//Set IDs, timestamps, etc for use later if needed
String transactionId = CustomKeywords.'com.SwisslogUtilities.getTransactionId'()

String currentTime = CustomKeywords.'com.SwisslogUtilities.getCurrentTime'()

//Define input files containing data for endpoint(s) under test
JsonBuilder addLocationCarrierBuilder = new JsonBuilder()

addLocationCarrierBuilder({
		customer('Swisslog')

			commands([{
				type("ADD_LOCATION")

				location({
					locationId("nursingStation/HouseInstallation/Foothills/Arrythmia Unit")
					name("Foothills Cardiac Rehab - Arrythmia Unit")
					parentId("nursingStation/HouseInstallation/Foothills")					
				identifiers([{
					identifier('T3A1P2')
					type('LOGICAL')
					
					//identifier('T3A1P2')
					//type('RFID')
					
					//identifier('T3B0T5')
					//type('RFID')
				}])

				"security.clearItemsEnabled" (true)
				})
			}])
})

String addLocationCarrierJson = JsonOutput.prettyPrint(addLocationCarrierBuilder.toString())

println(addLocationCarrierJson)

//Call the dispenseorders endpoint to create an order
try {
	Response addLocationCarrierResponse = RestAssured.given().body(addLocationCarrierJson).log().all().contentType('application/json').post(
		'/api/locationCommands/')

	Assert.assertEquals(addLocationCarrierResponse.statusCode /*actual value*/, 200 /*expected value*/, "Status code returned")

}
catch (Exception e) {
	e.printStackTrace()
}
			
