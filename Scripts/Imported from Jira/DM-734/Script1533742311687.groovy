/*Import libraries needed for test case*/
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import com.kms.katalon.core.testdata.InternalData

/*Declare carrier registration data*/
InternalData registerCarrierdata = findTestData("registerTLCarriersData")

(0..registerCarrierdata.getRowNumbers() - 1).each { index ->
    def transactionId = CustomKeywords.'com.Utilities.getTransactionId'()
	def cT = registerCarrierdata.internallyGetValue("Type", index)

    CustomKeywords.'com.DeliveryManager.registerCarrier' registerCarrierdata.internallyGetValue("Carrier", index), cT, registerCarrierdata.internallyGetValue("PrepStation", index), registerCarrierdata.internallyGetValue("Identifier", index)

    switch (cT.toUpperCase()) {
        case "PTS CARRIER":
            InternalData data = findTestData("postTLCarrierReroute")
            for (def eventsIndex : 0..data.getRowNumbers() - 1) {
                println(data.internallyGetValue("Carrier", eventsIndex))
                CustomKeywords.'com.DeliveryManager.postCarrierEvent' data.internallyGetValue("Carrier", eventsIndex), data.internallyGetValue("CarrierType", eventsIndex), data.internallyGetValue("EventType", eventsIndex), data.internallyGetValue("SourceStationId", eventsIndex), data.internallyGetValue("DestinationStationAddress", eventsIndex), data.internallyGetValue("DestinationStationId", eventsIndex), transactionId, data.internallyGetValue("User", eventsIndex)
            }
            break
        case "TRANSPONET":
            InternalData data = findTestData("postTPNCarrierReroute")
            for (def eventsIndex : 0..data.getRowNumbers() - 1) {
                println(eventsIndex)
                CustomKeywords.'com.DeliveryManager.postCarrierEvent' data.internallyGetValue("Carrier", eventsIndex), data.internallyGetValue("CarrierType", eventsIndex), data.internallyGetValue("EventType", eventsIndex), data.internallyGetValue("SourceStationId", eventsIndex), data.internallyGetValue("DestinationStationAddress", eventsIndex), data.internallyGetValue("DestinationStationId", eventsIndex), transactionId, data.internallyGetValue("User", eventsIndex)
            }
            break
    }
}