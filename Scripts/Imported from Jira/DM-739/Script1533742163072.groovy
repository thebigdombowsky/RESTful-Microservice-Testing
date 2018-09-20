import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import com.kms.katalon.core.testdata.InternalData

InternalData registerCarrierdata = findTestData("registerTPNCarriersData")

for (def index : 0..registerCarrierdata.getRowNumbers() - 1) {

        def cT
        def transactionId = CustomKeywords.'com.Utilities.getTransactionId'()
        cT = registerCarrierdata.internallyGetValue("Type", index)
        println cT
        CustomKeywords.'com.DeliveryManager.registerCarrier' registerCarrierdata.internallyGetValue("Carrier", index), registerCarrierdata.internallyGetValue("Type", index), registerCarrierdata.internallyGetValue("Identifier", index), registerCarrierdata.internallyGetValue("PrepStation", index), 'true'

    switch (cT.toUpperCase()) {
        case  "PTS CARRIER":
            InternalData data = findTestData("postTLCarrierEvent")
            for (def eventsIndex : 0..data.getRowNumbers() - 1) {
                println data.internallyGetValue("Carrier", eventsIndex)
                CustomKeywords.'com.DeliveryManager.postCarrierEvent' data.internallyGetValue("Carrier", eventsIndex), data.internallyGetValue("CarrierType", eventsIndex), data.internallyGetValue("EventType", eventsIndex), data.internallyGetValue("SourceStationId", eventsIndex), data.internallyGetValue("DestinationStationAddress", eventsIndex), data.internallyGetValue("DestinationStationId", eventsIndex), transactionId, data.internallyGetValue("User", eventsIndex)
            }
            break
        case "TRANSPONET":
            InternalData data = findTestData("postTPNCarrierEvent")
            for (def eventsIndex : 0..data.getRowNumbers() - 1) {
                println eventsIndex
                CustomKeywords.'com.DeliveryManager.postCarrierEvent' data.internallyGetValue("Carrier", eventsIndex), data.internallyGetValue("CarrierType", eventsIndex), data.internallyGetValue("EventType", eventsIndex), data.internallyGetValue("SourceStationId", eventsIndex), data.internallyGetValue("DestinationStationAddress", eventsIndex), data.internallyGetValue("DestinationStationId", eventsIndex), transactionId, data.internallyGetValue("User", eventsIndex)
            }
            break
    }
}
