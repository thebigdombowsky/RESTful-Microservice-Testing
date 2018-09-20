import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import com.kms.katalon.core.testdata.InternalData

InternalData data = findTestData("Translogic Carrier Events/postTLCarrierFreerunReroute")
InternalData freerun = findTestData("Translogic Carrier Events/postTLCarrierFreerunReroute1")
InternalData registerCarrierdata = findTestData("Translogic Carrier Events/registerTLCarriersData")

for (def index : 0..data.getRowNumbers() - 1) {
	
	def transactionId = CustomKeywords.'com.Utilities.getTransactionId'()
	def cT = registerCarrierdata.internallyGetValue("Type", index)
	println cT
		
	CustomKeywords.'com.DeliveryManager.registerCarrier' data.internallyGetValue("Carrier", index), data.internallyGetValue("PrepStation", index), data.internallyGetValue("Identifier", index), transactionId, 'true'
	
	CustomKeywords.'com.DeliveryManager.postTranslogicCarrierEvent' data.internallyGetValue("Carrier", index), data.internallyGetValue("EventType", index), data.internallyGetValue("SourceStationId", index), data.internallyGetValue("DestinationStationAddress", index), data.internallyGetValue("DestinationStationId", index), transactionId, data.internallyGetValue("User", index)

	if (transactionId.isInteger()) {
		transactionId = transactionId as Integer
		println transactionId
	  }
	
	transactionId++
	
	def freerunIncrement = transactionId
		
	for (def freerunIndex : 0..freerun.getRowNumbers() - 1) {
		
		CustomKeywords.'com.DeliveryManager.postTranslogicCarrierEvent' freerun.internallyGetValue("Carrier", freerunIndex), freerun.internallyGetValue("EventType", freerunIndex), freerun.internallyGetValue("SourceStationId", freerunIndex), freerun.internallyGetValue("DestinationStationAddress", freerunIndex), freerun.internallyGetValue("DestinationStationId", freerunIndex), freerunIncrement, freerun.internallyGetValue("User", freerunIndex)
	
		}
}

