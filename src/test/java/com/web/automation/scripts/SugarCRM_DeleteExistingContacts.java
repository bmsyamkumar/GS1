package com.web.automation.scripts;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.web.automation.accelerators.TestEngineWeb;
import com.web.automation.fileutils.ExcelReader;
import com.web.automation.logs.ExtentLogs;
import com.web.automation.pages.SugarCRMPage;
import com.web.automation.pages.SugarContactsInput;
import com.web.automation.utilities.CommonVariables;

public class SugarCRM_DeleteExistingContacts extends TestEngineWeb {

	private String testCaseFailureReason = "";
	private boolean testCaseStatus = true;
	protected String sheetPath = System.getProperty("user.dir").replace("\\", "/") + "/testdata/TestData.xlsx";
	protected String sheetName = "SugarCRM";
	Map<String, List<String>> testdata = null;
	private ExtentLogs extenLogs = new ExtentLogs();
	private SugarContactsInput testData=new SugarContactsInput();
	private SugarCRMPage SugarPage;
	boolean isLoginSuccessfull,isCreateContactSuccessful,IsVerificationOfContactSuccessfull,
	isDeleteSuccessfull,isDeleteExistingSuccessfull;
	public void TestCaseStatus(Boolean status, String reason) {
		if (status == false) {
			Assert.fail("Test Case Failed because - " + reason);
		}
	}
	@BeforeMethod(alwaysRun = true)
	public SugarContactsInput beforeMethod() throws Throwable {
		getTestData();
		SugarPage = new SugarCRMPage(CommonVariables.CommonDriver.get());
		return testData;
	}
	@Test(description = "SugarCRM", groups = { "smoke", "regression" })
	public void contactValidationDeleteExistingContacts() throws Throwable {
		try {
			
			// **************LoginSugarCRM********************************************************
			// ------------------------------------------------------------------------------------
			isLoginSuccessfull=SugarPage.login(testData);
			if(!isLoginSuccessfull){			
				testCaseFailureReason = "Failed to login in the application";				
				extenLogs.fail("SugarCRM ", testCaseFailureReason + "Failed Reason : Unable "
						+ "to login");
				testCaseStatus = false; 
				throw new Exception();
			}
			
			// ------------------------------------------------------------------------------------			
			// **************DeleteContacts********************************************************
				isDeleteExistingSuccessfull=SugarPage.deleteContact(testData);
				if(isDeleteSuccessfull){
				testCaseFailureReason = "Deletion of Existing contact card failed";				
				extenLogs.fail("SugarCRM ", testCaseFailureReason + "Failed Reason : "
								+ "Contact was not found");						
				testCaseStatus = false;
				throw new Exception();							
				}
			// ------------------------------------------------------------------------------------
			// **************LogOutCRM*************************************************************				
			SugarPage.logout();
			// ------------------------------------------------------------------------------------
			
			
		}
		catch (Exception e) {
			testCaseFailureReason = "Failed to complete Customer Search Using Customer#.";
			String stackTrace = extenLogs.getStackTraceAsString("Test", testCaseFailureReason, e);
			extenLogs.fail("SugarCRM ", testCaseFailureReason + "Failed Reason : " + stackTrace);
			testCaseStatus = false;
		}
		TestCaseStatus(testCaseStatus, testCaseFailureReason);
	}
	
	
	
	private void getTestData() throws IOException {
		try {
			testData.UserName = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "UserName");
			testData.Password = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "Password");
			testData.Salutation = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "Salutation");
			testData.FirstName = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "FirstName");
			testData.LastName = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "LastName");
			testData.LeadSource = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "LeadSource");
			testData.Title = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "Title");
			testData.Department = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "Department");
			testData.ReportsTo = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "ReportsTo");
			testData.SyncToOutLookl = Boolean.parseBoolean(ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "SyncToOutLookl"));
			testData.DoNotCall = Boolean.parseBoolean(ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "DoNotCall"));
			testData.AssignTo = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "AssignTo");
			testData.Email = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "Email");
			testData.OptedOut = Boolean.parseBoolean(ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "OptedOut"));
			testData.Invalid = Boolean.parseBoolean(ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "Invalid"));
			testData.OfficePhone = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "OfficePhone");
			testData.Mobile = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "Mobile");
			testData.Home = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "Home");
			testData.OtherPhone = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "OtherPhone");
			testData.Fax = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "Fax");
			testData.Birthdate = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "Birthdate");
			testData.Assistant = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "Assistant");
			testData.AssistantPhone = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "AssistantPhone");
			testData.PrimaryAddress = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "PrimaryAddress");
			testData.PrimaryAddressCity = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "PrimaryAddressCity");
			testData.PrimaryAddressState = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "PrimaryAddressState");
			testData.PrimaryAddressPostalCode = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "PrimaryAddressPostalCode");
			testData.PrimaryAddressCountry = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "PrimaryAddressCountry");
			testData.OtherAddress = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "OtherAddress");
			testData.OtherAddressCity = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "OtherAddressCity");
			testData.OtherAddressState = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "OtherAddressState");
			testData.OtherAddressPostalCode = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "OtherAddressPostalCode");
			testData.OtherAddressCountry = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "OtherAddressCountry");
			testData.Description = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "Description");
		} catch (Exception e) {
			String stackTrace = extenLogs.getStackTraceAsString("Test", testCaseFailureReason, e);
			extenLogs.fail("SugarCRM ", testCaseFailureReason + "Failed Reason : " + stackTrace);
		}
	}
}