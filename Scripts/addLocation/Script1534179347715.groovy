import com.kms.katalon.core.annotation.Keyword
import groovy.json.JsonBuilder as JsonBuilder
import groovy.json.JsonOutput as JsonOutput
import groovy.json.JsonSlurper as JsonSlurper
import io.restassured.RestAssured as RestAssured
import io.restassured.path.json.JsonPath as JsonPath
import io.restassured.response.Response as Response
import org.testng.Assert as Assert
import java.text.SimpleDateFormat

		/* Specify the base URL and port to the RESTful web service*/
		RestAssured.baseURI = 'http://10.10.100.12'
		RestAssured.port = 8002

		JsonBuilder addLocationBuilder = new JsonBuilder()

		addLocationBuilder({
			customer("Swisslog")

			commands([
				{
					type("ADD_LOCATION")

					location({
						locationId("unit/HouseInstallation/Foothills/Main/ER")
						name("Foothills ER")
						parentId("building/HouseInstallation/Foothills/Main")
						identifiers([
							{
								identifier("T2N2C1")
								type("BARCODE")

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

		Assert.assertEquals(addLocationResponse.statusCode /*actual value*/, 200 /*expected value*/, "Status code returned")

