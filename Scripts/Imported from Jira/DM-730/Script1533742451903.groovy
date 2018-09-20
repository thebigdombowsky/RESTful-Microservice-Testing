import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import com.kms.katalon.core.testdata.InternalData


InternalData data = findTestData("dispenseOrdersCharacterLimit")

	for (def index : 0..data.getRowNumbers() - 1) {
	
	def output = CustomKeywords.'com.DeliveryManager.createNewOrder' data.internallyGetValue("Quantity", index), data.internallyGetValue("Description", index), data.internallyGetValue("Identifier", index), data.internallyGetValue("Location", index)

	def destinationLocationId = output.destinationLocationId
	def orderLocationId = output.locationId

	CustomKeywords.'com.DeliveryManager.cancelOrder'output.locationId, output.destinationLocationId,
	output.swisslogUniqueId, output.orderNumber, data.internallyGetValue('Quantity',
			index), data.internallyGetValue('Description', index)

}

