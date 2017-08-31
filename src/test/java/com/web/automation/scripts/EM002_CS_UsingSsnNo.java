package com.web.automation.scripts;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.web.automation.logs.ExtentLogs;
import com.web.automation.objectrepository.Login_OR;
import com.web.automation.accelerators.ActionsLibrary;
import com.web.automation.accelerators.ActionsLibrary.Mode;
import com.web.automation.accelerators.TestEngineWeb;
import com.web.automation.fileutils.ExcelReader;
import com.web.automation.pages.signUpPage;
import com.web.automation.utilities.CommonVariables;

public class EM002_CS_UsingSsnNo extends TestEngineWeb {
	private signUpPage signupPg;
	private String testCaseFailureReason = "";
	private boolean testCaseStatus = true;
	private String username, password, ssn;
	protected String sheetPath = System.getProperty("user.dir").replace("\\", "/") + "/testdata/TestData.xlsx";
	protected String sheetName = "EM002CSUsingSsnNo";
	Map<String, List<String>> testdata = null;
	private ExtentLogs extenLogs = new ExtentLogs();

	/**
	 * _
	 * 
	 * @author Cigniti
	 * @param status
	 * @param reason
	 */
	public void TestCaseStatus(Boolean status, String reason) {
		if (status == false) {
			Assert.fail("Test Case Failed because - " + reason);
		}
	}

	@BeforeMethod(alwaysRun = true, groups = { "smoke", "regression" })
	public void beforeMethod() throws IOException {
		this.username = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "Username");
		this.password = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "Password");
		this.ssn = ActionsLibrary.generateRandomString(9, Mode.NUMERIC);

	}

	/**
	 * @author Cigniti0
	 * @throws Throwable
	 * @test case id: ENTMgmt-01
	 */
	@Test(description = "EM002CSUsingSsnNo", groups = { "smoke", "regression" })
	public void EM002CSUsingSsnNo() throws Throwable {
		try {
			signupPg = new signUpPage(CommonVariables.CommonDriver.get());
			signupPg.login(username, password);
			signupPg.actionLib.Click(CommonVariables.CommonDriver.get().findElement(Login_OR.SEARCH_TAB), "SEARCH TAB");
			signupPg.actionLib.Click(CommonVariables.CommonDriver.get().findElement(Login_OR.SEARCH_CUSTOMER),
					"SEARCH_CUSTOMER");
			signupPg.actionLib.isTextPresentOnPage("Customer");
			signupPg.actionLib.isElementDisabled(CommonVariables.CommonDriver.get().findElement(Login_OR.SEARCH),
					"SEARCH BUTTON");
			signupPg.actionLib.type(CommonVariables.CommonDriver.get().findElement(Login_OR.SSN), ssn);
			signupPg.actionLib.isElementDisabled(CommonVariables.CommonDriver.get().findElement(Login_OR.CUSTOMER_NO),
					"CUSTOMERNO");
			signupPg.actionLib.isElementDisabled(CommonVariables.CommonDriver.get().findElement(Login_OR.FIRSTNAME),
					"FIRSTNAME");
			signupPg.actionLib.isElementDisabled(CommonVariables.CommonDriver.get().findElement(Login_OR.LASTNAME),
					"LASTNAME");
			signupPg.actionLib.isElementDisabled(CommonVariables.CommonDriver.get().findElement(Login_OR.SSN), "SSN");
			signupPg.actionLib.isElementDisabled(CommonVariables.CommonDriver.get().findElement(Login_OR.DATEOFBIRTH),
					"DATEOFBIRTH");
			signupPg.actionLib.isElementDisabled(CommonVariables.CommonDriver.get().findElement(Login_OR.EMAIL),
					"EMAIL");
			signupPg.actionLib.isElementDisabled(CommonVariables.CommonDriver.get().findElement(Login_OR.PHONENO),
					"PHONENO");
			signupPg.actionLib.isElementDisabled(CommonVariables.CommonDriver.get().findElement(Login_OR.STATEID),
					"STATEID");
			signupPg.actionLib.isElementDisabled(
					CommonVariables.CommonDriver.get().findElement(Login_OR.GOVERNMENTIDTYPEID), "GOVERNMENTIDTYPEID");
			signupPg.actionLib.isElementDisabled(
					CommonVariables.CommonDriver.get().findElement(Login_OR.GOVERNMENTIDNO), "GOVERNMENTIDNO");
			signupPg.actionLib.isElementDisabled(CommonVariables.CommonDriver.get().findElement(Login_OR.STORENO),
					"STORENO");
			signupPg.actionLib.isElementDisabled(
					CommonVariables.CommonDriver.get().findElement(Login_OR.LINEOFBUSINESSID), "LINEOFBUSINESSID");
			signupPg.actionLib.isElementEnabled(CommonVariables.CommonDriver.get().findElement(Login_OR.SEARCH),
					"SEARCH BUTTON");
		}

		catch (Exception e) {
			testCaseFailureReason = "Failed to complete Customer Search Using Customer#.";
			String stackTrace = extenLogs.getStackTraceAsString("Test", testCaseFailureReason, e);
			extenLogs.fail("EM002CSUsingSsnNo ",
					testCaseFailureReason + "Failed Reason : " + stackTrace);
			testCaseStatus = false;
		}
		TestCaseStatus(testCaseStatus, testCaseFailureReason);
	}

}