import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import com.kms.katalon.core.testdata.InternalData


//Call the createNewOrder Keyword to create a new order via REST endpoint api/atp/dispenseorders
//Parameters - quantity, item description, item identifer, destination path

InternalData data = findTestData("dispenseOrdersData")

	for (def index : 0..data.getRowNumbers() - 1) {
	
	def output = CustomKeywords.'com.DeliveryManager.createNewOrder' data.internallyGetValue("Quantity", index), data.internallyGetValue("Description", index), data.internallyGetValue("Identifier", index), data.internallyGetValue("Location", index)

	def destinationLocationId = output.destinationLocationId
	def orderLocationId = output.locationId
	
	println(destinationLocationId)
	println(orderLocationId)

}
