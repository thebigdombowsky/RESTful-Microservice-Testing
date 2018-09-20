import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import com.kms.katalon.core.testdata.InternalData


//Call the createNewOrder Keyword to create a new order via REST endpoint api/atp/dispenseorders
//Parameters - quantity, item description, item identifer, destination path
WebUI.comment('Call the createNewOrder Keyword to create a new order via REST endpoint api/atp/dispenseorders')

def transactionId = null

InternalData data = findTestData("registerTLCarriersData")

for (def index : 0..data.getRowNumbers() - 1) {

	transactionId = CustomKeywords.'com.Utilities.getTransactionId'()
	
	CustomKeywords.'com.DeliveryManager.registerCarrier' data.internallyGetValue("Carrier", index), data.internallyGetValue("Type", index), data.internallyGetValue("PrepStation", index), data.internallyGetValue("Identifier", index), 'true'

}
