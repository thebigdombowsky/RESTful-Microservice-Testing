package com

import com.kms.katalon.core.annotation.Keyword
import groovy.json.JsonBuilder as JsonBuilder
import groovy.json.JsonOutput as JsonOutput
import groovy.json.JsonSlurper as JsonSlurper
import io.restassured.RestAssured as RestAssured
import io.restassured.path.json.JsonPath as JsonPath
import io.restassured.response.Response as Response
import org.testng.Assert as Assert

import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement
import java.text.SimpleDateFormat

public class Utilities {
	/**
	 * @return system time formatted as displayed in Delivery Manager
	 */
	@Keyword
	def static getCurrentTime() {

		String CDATE = new SimpleDateFormat("mm/dd/yyyy").format(Calendar.getInstance().getTime())
		String CTIME = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime())
		String timestamp = (CDATE + "T" + CTIME + "Z")
		return timestamp
	}

	/**
	 * @return transactionId of concatenated current date/time string
	 */
	@Keyword
	def static getTransactionId() {

		String transactionID = System.currentTimeMillis()
		return transactionID
	}
}

public class DeliveryManager {

	@Keyword
	def createNewOrder(def quantity, def itemDescription, def destinationId) {

		def slurper = new JsonSlurper()

		// Specify the base URL and port to the RESTful web service
		RestAssured.baseURI = 'http://10.10.100.12'

		RestAssured.port = 8002

		//Define input files containing data for endpoint(s) under test
		JsonBuilder createOrderBuilder = new JsonBuilder()

		createOrderBuilder({
			destinationLocationId(destinationId)

			identifiers([
				{
					identifier('')

					type('BARCODE')
				}
			])

			items([
				{
					count(quantity)

					itemId('')

					itemName(itemDescription)
				}
			])

			"location.template"('dispenseOrder.MANUAL')

			orderType('STAT')

			orderNumber('KAT-' + new SimpleDateFormat("HHmmss").format(Calendar.getInstance().getTime()))

			sourceType('MANUAL')

			status('PROCESSING')

			deliveryMethod('PTS')
		})

		String dispenseOrderJson = JsonOutput.prettyPrint(createOrderBuilder.toString())

		println(dispenseOrderJson)

		//Call the dispenseorders endpoint to create an order

		Response dispenseOrderResponse = RestAssured.given().body(dispenseOrderJson).log().all().contentType('application/vnd.swisslog.common-dispense-order.v1+json').post(
				'/api/dispenseorders')

		println(dispenseOrderResponse.statusCode())

		Assert.assertEquals(dispenseOrderResponse.statusCode() /*actual value*/, 200 /*expected value*/, "Status code returned")

		JsonPath jsonPathEvaluator = dispenseOrderResponse.jsonPath()

		def orderLocationId = jsonPathEvaluator.get('locationId')

		def uniqueSWLGId = jsonPathEvaluator.get('swisslogUniqueId')

		println('locationId received from Response ' + orderLocationId)

		println('SwisslogUniqueId received from Response ' + uniqueSWLGId)

		def result = slurper.parseText(dispenseOrderResponse.asString())

		println(result)

		println(orderLocationId)

		println(uniqueSWLGId)

		return result

	}

	@Keyword
	def updateOrderQuantity (def quantity, def OrderLocationId) {

		// Specify the base URL and port to the RESTful web service
		RestAssured.baseURI = 'http://10.10.100.12'

		RestAssured.port = 8002

		//Define input files containing data for endpoint(s) under test
		JsonBuilder updateOrderQuantityBuilder = new JsonBuilder()

		updateOrderQuantityBuilder({

			count(quantity)
			locationId(OrderLocationId)
			itemId('')

			identifiers([{
				}])

			items([{
				}])

		})

		String updateOrderQuantityJson = JsonOutput.prettyPrint(updateOrderQuantityBuilder.toString())

		println(updateOrderQuantityJson)

		//Call the dispenseorders/itemCount endpoint to update the quantity of an order

		def updateOrderQuantityResponse = RestAssured.given().body(updateOrderQuantityJson).log().all().contentType('application/vnd.swisslog.common-dispense-order-count-change.v1+json').post(
				'/api/dispenseorders/itemCount')

		println(updateOrderQuantityResponse.statusCode())

		Assert.assertEquals(updateOrderQuantityResponse.statusCode() /*actual value*/, 200 /*expected value*/, "Status code returned")

		return updateOrderQuantityResponse
	}

	@Keyword
	def cancelOrder (def orderLocationId, def destinationId, def itemIdentifier, def orderNum, def originalCount, def nameOfProduct) {

		// Specify the base URL and port to the RESTful web service
		RestAssured.baseURI = 'http://10.10.100.12'

		RestAssured.port = 8002

		//Define input files containing data for endpoint(s) under test
		JsonBuilder cancelOrderBuilder = new JsonBuilder()

		cancelOrderBuilder({

			cancellationReason('Other')
			destinationLocationId(destinationId)
			locationId(orderLocationId)



			identifiers([
				{
					identifier(itemIdentifier)
					type('BARCODE')
				}
			])

			items([
				{

					count(originalCount)
					itemId('')
					itemName(nameOfProduct)

				}
			])

			"location.template"('dispenseOrder.MANUAL')

			orderType('STAT')

			orderNumber(orderNum)

			sourceType('MANUAL')

			status('CANCELLED')

			deliveryMethod('PTS')

		})

		String cancelOrderJson = JsonOutput.prettyPrint(cancelOrderBuilder.toString())

		println(cancelOrderJson)

		//Call the dispenseorders/itemCount endpoint to update the quantity of an order

		def cancelOrderResponse = RestAssured.given().body(cancelOrderJson).log().all().contentType('application/vnd.swisslog.common-dispense-order.v1+json').post(
				'/api/dispenseorders')

		println(cancelOrderResponse.statusCode())

		Assert.assertEquals(cancelOrderResponse.statusCode() /*actual value*/, 200 /*expected value*/, "Status code returned")

		return cancelOrderResponse
	}

	@Keyword
	def registerCarrier(def Carrier, def Type, def prepStation, def ItemIdentifier) {

		// Specify the base URL and port to the RESTful web service
		RestAssured.baseURI = 'http://10.10.100.12'
		RestAssured.port = 8001

		//Define input files containing data for endpoint(s) under test
		JsonBuilder registerCarrierBuilder = new JsonBuilder()

		registerCarrierBuilder({
			carrierId(Carrier)
			carrierType(Type)

			(items([
				{
					count(1)

					identifier(ItemIdentifier)
				}
			])
			)
			prepStationId(prepStation)

			registered("true")

			locked("false")

		})

		String registerCarrierJson = JsonOutput.prettyPrint(registerCarrierBuilder.toString())

		println(registerCarrierJson)

		//Call the carriers PATCH endpoint to register a carrier

		def mimeType = null

		switch (Type.toUpperCase()) {
			case  "PTS CARRIER":
				mimeType = "application/json"
				break
			case "TRANSPONET":
				mimeType = "application/vnd.swisslog.dm-transponet-carrier-registration.v1+json"
				break
		}

		Response registerCarrierResponse = RestAssured.given().body(registerCarrierJson).log().all().contentType(mimeType).patch('/api/carriers/' + Carrier)

		Assert.assertEquals(registerCarrierResponse.statusCode() /*actual value*/, 200 /*expected value*/, "Status code returned")

	}

	@Keyword
	def postCarrierEvent(def carrier, def type, def eventType, def sourceId, def stationAddress, def stationId, def transactionId, def user) {

		/* Specify the base URL and port to the RESTful web service*/
		RestAssured.baseURI = 'http://10.10.100.12'
		RestAssured.port = 8001

		/*Declare new JsonBuilder*/
		JsonBuilder carrierEventBuilder = new JsonBuilder()

		/*Build the appropriate json body and submit the request based on carrier type*/
		switch (type.toUpperCase()) {
			case  "PTS CARRIER":
				carrierEventBuilder({
					authenticationCode(11111)
					carrierId(carrier)
					carrierType(type)
					sourceStationId(sourceId)
					status(eventType)
					targetStationAddress(stationAddress)
					targetStationId(stationId)
					transCode(transactionId)
					userName(user)
					statusInfo(transactionId)
				})

				String carrierEventJson = JsonOutput.prettyPrint(carrierEventBuilder.toString())
				println(carrierEventJson)

			//Call the api/translogic/carriers/ptsEvents endpoint to update carrier status
				Response carrierEventResponse = RestAssured.given().body(carrierEventJson).log().all().contentType('application/vnd.swisslog.dm-translogic-carrier-event.v1+json').post(
						'/api/translogic/carriers/ptsEvents')

				Assert.assertEquals(carrierEventResponse.statusCode() /*actual value*/, 201 /*expected value*/, "Status code returned")

				break
			case "TRANSPONET":
				carrierEventBuilder({
					authenticationCode(11111)
					carrierId(carrier)
					carrierIdExpected(carrier)
					carrierType(type)
					sourceStationId(sourceId)
					status(eventType)
					targetStationAddress(stationAddress)
					targetStationId(stationId)
					transCode(transactionId)
					userName(user)
					statusInfo(transactionId)

				})

				String carrierEventJson = JsonOutput.prettyPrint(carrierEventBuilder.toString())
				println(carrierEventJson)

			//Call the api/carriers/{identifier}/ptsevents endpoint to update carrier status
				Response carrierEventResponse = RestAssured.given().body(carrierEventJson).log().all().contentType('application/vnd.swisslog.dm-transponet-carrier-event.v1+json').post(
						'/api/carriers/' + carrier + '/ptsevents')

				Assert.assertEquals(carrierEventResponse.statusCode() /*actual value*/, 201 /*expected value*/, "Status code returned")

				break
		}
	}

	@Keyword
	def addLocation (def customerName, def commandType, def locationID, def locationName, def parent, def locationIdentifier, def locationType) {

		/* Specify the base URL and port to the RESTful web service*/
		RestAssured.baseURI = 'http://10.10.100.12'
		RestAssured.port = 8002

		JsonBuilder addLocationBuilder = new JsonBuilder()

		addLocationBuilder({
			customer(customerName)

			commands([
				{
					type(commandType)

					location({
						locationId(locationID)
						name(locationName)
						parentId(parent)
						identifiers([
							{
								identifier(locationIdentifier)
								type(locationType)

								//identifier('T3A1P2')
								//type('RFID')

								//identifier('T3B0T5')
								//type('RFID')
							}
						])

						"security.clearItemsEnabled"("true")
					})
				}
			])
		})

		String addLocationJson = JsonOutput.prettyPrint(addLocationBuilder.toString())

		println addLocationJson

		Response addLocationResponse = RestAssured.given().body(addLocationJson).log().all().contentType('application/json').post('/api/locationCommands/')

		String addLocationResponseJson = JsonOutput.prettyPrint(addLocationBuilder.toString())

		println addLocationResponseJson

		Assert.assertEquals(addLocationResponse.statusCode /*actual value*/, 200 /*expected value*/, "Status code returned")

	}
}

public class sql {

	private static Connection connection = null

	@Keyword
	def connectDB(String server, String port, String dbname, String username, String password){
		String url = "jdbc:sqlserver://" + server + ":" + port + ";databaseName=" + dbname + ";user=" + username + ";password=" + password

		if(connection != null && !connection.isClosed()){
			connection.close()
		}
		connection = DriverManager.getConnection(url)
		return connection
	}

	@Keyword
	def executeQuery(String queryString) {
		Statement stm = connection.createStatement()
		ResultSet rs = stm.executeQuery(queryString)
		return rs
	}

	@Keyword
	def closeDatabaseConnection() {
		if(connection != null && !connection.isClosed()){
			connection.close()
		}
		connection = null
	}

}