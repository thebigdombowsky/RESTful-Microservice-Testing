import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import com.kms.katalon.core.testdata.InternalData


InternalData data = findTestData("dispenseOrdersData")

	for (def index : 0..data.getRowNumbers() - 1) {
	
	def dispenseOrderResult = CustomKeywords.'com.DeliveryManager.createNewOrder' data.internallyGetValue("Quantity", index), data.internallyGetValue("Description", index), data.internallyGetValue("Identifier", index), data.internallyGetValue("Location", index)
	
	CustomKeywords.'com.DeliveryManager.updateOrderQuantity' '225', dispenseOrderResult.locationId

}