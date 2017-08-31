package com.web.poc.script;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.web.automation.accelerators.TestEngineWeb;
import com.web.automation.fileutils.ExcelReader;
import com.web.automation.logs.ExtentLogs;
import com.web.automation.utilities.CommonVariables;
import com.web.poc.testObject.GS1InputData;
import com.web.poc.workflow.HomePage;
import com.web.poc.workflow.LoginPage;

public class GS1_HomePage extends TestEngineWeb {

	private String testCaseFailureReason = "";
	private boolean testCaseStatus = true;
	protected String sheetPath = System.getProperty("user.dir").replace("\\", "/") + "/testdata/GS1_TestData.xlsx";
	protected String sheetName = "HomePage";
	Map<String, List<String>> testdata = null;
	private ExtentLogs extentLogs = new ExtentLogs();
	private GS1InputData testData=new GS1InputData();
	private LoginPage LoginPageWorkflow;
	private HomePage HomePageWorkflow;
	boolean isLoginSuccessfull,isLogOutSuccessfull;
	
	public void TestCaseStatus(Boolean status, String reason) {
		if (status == false) {
			Assert.fail("Test Case Failed because - " + reason);
		}
	}
	
	@BeforeMethod(alwaysRun = true)
	public GS1InputData beforeMethod() throws Throwable {
		getTestData();
		LoginPageWorkflow = new LoginPage(CommonVariables.CommonDriver.get());
		HomePageWorkflow = new HomePage(CommonVariables.CommonDriver.get());
		return testData;
	}
	
	@Test(description = "GS1", groups = { "smoke", "regression" })
	public void GS1_HomePage() throws Throwable {
		try {			   
			    //Information About Test Script
				extentLogs.info("GS1 Login - Automation Demo","GS1 Login - Automation Demo from Cigniti");
			
				//GS1 Login
				boolean blnLogin=LoginPageWorkflow.fnLogin(testData);
				if(blnLogin)
				{
					extentLogs.pass("GS1 Login Status","Successfully Logged into GS1 application");
				}
				else
				{	
					testCaseFailureReason = "Failed to Login into GS1 application";
					extentLogs.fail("GS1_Login status","Not able to login to GS1");
					testCaseStatus = false;
				}
				
				//GS1 Verify Home page
				boolean blnHomePageValidation=HomePageWorkflow.fnVerifyMainMenu();
				if(blnHomePageValidation)
				{
					extentLogs.pass("GS1 Logout Status","Successfully verified Home page links in GS1 application");
				}
				else
				{	
					testCaseFailureReason = "Failed to verify Home page links in GS1 application";
					extentLogs.fail("Home page validation status","Failed to verify Home page links in GS1 application");
					testCaseStatus = false;
				}
				
				//GS1 Logout
				boolean blnLogout=HomePageWorkflow.fnLogout();
				if(blnLogout)
				{
					extentLogs.pass("GS1 Logout Status","Successfully Logged out from GS1 application");
				}
				else
				{	
					testCaseFailureReason = "Failed to Logout from GS1";
					extentLogs.fail("GS1_Logout status","Not able to logout from GS1");
					testCaseStatus = false;
				}
				
				
			}
		catch (Exception e) {
			testCaseFailureReason = "Failed to complete GS1_Home Page validations";
			String stackTrace = extentLogs.getStackTraceAsString("Test", testCaseFailureReason, e);
			extentLogs.fail("GS1_Homepage ", testCaseFailureReason + "Failed Reason : " + stackTrace);
			testCaseStatus = false;
		}
		TestCaseStatus(testCaseStatus, testCaseFailureReason);
	}
		
	private void getTestData() throws IOException {
		try {
			
			testData.Username = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "Username");
			testData.Password = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "Password");			
			} 
		catch (Exception e) {
			String stackTrace = extentLogs.getStackTraceAsString("Test", testCaseFailureReason, e);
			extentLogs.fail("GS1_Login ", testCaseFailureReason + "Failed Reason : " + stackTrace);
		}
	}

}

