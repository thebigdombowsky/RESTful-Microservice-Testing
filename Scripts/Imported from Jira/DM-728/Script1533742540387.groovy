import static com.kms.katalon.core.testdata.TestDataFactory.findTestData

import com.kms.katalon.core.testdata.InternalData as InternalData

InternalData data = findTestData('dispenseOrdersData')

for (def index : (0..data.getRowNumbers() - 1)) {
	def dispenseOrderResult = CustomKeywords.'com.DeliveryManager.createNewOrder'(data.internallyGetValue('Quantity',
			index), data.internallyGetValue('Description', index), data.internallyGetValue('Location', index))
	
	print dispenseOrderResult.swisslogUniqueId
	
	CustomKeywords.'com.DeliveryManager.cancelOrder'dispenseOrderResult.locationId, dispenseOrderResult.destinationLocationId,
	dispenseOrderResult.swisslogUniqueId, dispenseOrderResult.orderNumber, data.internallyGetValue('Quantity',
			index), data.internallyGetValue('Description', index)
}