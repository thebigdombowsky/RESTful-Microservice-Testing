import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import com.kms.katalon.core.testdata.InternalData


InternalData registerData = findTestData("registerTPNCarriersData")
InternalData data = findTestData("postTPNCarrierTimeout")

def transactionId = CustomKeywords.'com.Utilities.getTransactionId'()

for (def index : 0..data.getRowNumbers() - 1) {
		
	CustomKeywords.'com.DeliveryManager.registerCarrier' registerData.internallyGetValue("Carrier", index), registerData.internallyGetValue("Type", index), registerData.internallyGetValue("PrepStation", index), registerData.internallyGetValue("Identifier", index), 'true'

	println("Waiting for timeout to expire...")
	sleep(61000)
	
	CustomKeywords.'com.DeliveryManager.postCarrierEvent' data.internallyGetValue("Carrier", index), data.internallyGetValue("CarrierType", index), data.internallyGetValue("EventType", index), data.internallyGetValue("SourceStationId", index), data.internallyGetValue("DestinationStationAddress", index), data.internallyGetValue("DestinationStationId", index), transactionId, data.internallyGetValue("User", index)

}

