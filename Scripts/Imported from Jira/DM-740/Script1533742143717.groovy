import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import com.kms.katalon.core.testdata.InternalData

InternalData registerCarrierdata = findTestData("registerTPNCarriersData")
InternalData data = findTestData("postTPNCarrierReroute")

def transactionId = CustomKeywords.'com.Utilities.getTransactionId'()

for (def index : 0..registerCarrierdata.getRowNumbers() - 1) {
		
	CustomKeywords.'com.DeliveryManager.registerCarrier' registerCarrierdata.internallyGetValue("Carrier", index), registerCarrierdata.internallyGetValue("Type", index), registerCarrierdata.internallyGetValue("Identifier", index), registerCarrierdata.internallyGetValue("PrepStation", index), 'true'

	for (def eventsIndex : 0..data.getRowNumbers() - 1) {
	
		CustomKeywords.'com.DeliveryManager.postCarrierEvent' data.internallyGetValue("Carrier", eventsIndex), data.internallyGetValue("CarrierType", index), data.internallyGetValue("EventType", eventsIndex), data.internallyGetValue("SourceStationId", eventsIndex), data.internallyGetValue("DestinationStationAddress", eventsIndex), data.internallyGetValue("DestinationStationId", eventsIndex), transactionId, data.internallyGetValue("User", eventsIndex)
	
	}	
	
}
