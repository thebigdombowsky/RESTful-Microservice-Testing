import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import com.kms.katalon.core.testdata.InternalData as InternalData
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import internal.GlobalVariable as GlobalVariable

InternalData locationCommandsdata = findTestData('addLocations')
CustomKeywords.'com.sql.connectDB'('10.10.100.12', '1433', 'DMLOCATION', 'DM', 'R6c2i1d9C2y0')

for (def index : (0..locationCommandsdata.getRowNumbers() - 1)) {

	def sqlStatement = "select * from dbo.locations where id = " + locationCommandsdata.internallyGetValue('locationID', index)
	println sqlStatement
	
	def sqlResults = CustomKeywords.'com.sql.executeQuery'(sqlStatement)
	println sqlResults
	
    CustomKeywords.'com.DeliveryManager.addLocation'(locationCommandsdata.internallyGetValue('customerName', index), locationCommandsdata.internallyGetValue(
            'commandType', index), locationCommandsdata.internallyGetValue('locationID', index), locationCommandsdata.internallyGetValue(
            'locationName', index), locationCommandsdata.internallyGetValue('parent', index), locationCommandsdata.internallyGetValue(
            'locationIdentifier', index), locationCommandsdata.internallyGetValue('identifierType', index))
}

CustomKeywords.'com.sql.closeDatabaseConnection'()
