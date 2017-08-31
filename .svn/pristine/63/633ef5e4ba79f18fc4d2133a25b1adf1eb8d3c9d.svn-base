package com.web.automation.scripts;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;
import com.web.automation.accelerators.TestEngineWeb;
import com.web.automation.fileutils.ExcelReader;
import com.web.automation.logs.ExtentLogName;
import com.web.automation.logs.ExtentLogs;
import com.web.automation.objectrepository.Login_OR;
import com.web.automation.pages.signUpPage;
import com.web.automation.utilities.CommonVariables;

public class CustomerSearchFieldValidations extends TestEngineWeb {
	private ExtentTest etest;
	private signUpPage signupPg;
	private String testCaseFailureReason = "";
	private boolean testCaseStatus = true;
	private String username, password, specialchars, lastname, firstname, socialsecurity, customerno, dateofbirth,
			email, phoneno, storeno, state, governmentidtype, governmentidno, lineofbusiness, errormessage1,
			errormessage2, errormessage3, errormessage4;
	protected String sheetPath = System.getProperty("user.dir").replace("\\", "/") + "/testdata/TestData.xlsx";
	protected String sheetName = "CustomerSearchFieldValidations";
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
	public void beforeMethod() throws Throwable {
		this.username = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "Username");
		this.password = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "Password");
		this.specialchars = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "SpecialChars");
		signupPg = new signUpPage(CommonVariables.CommonDriver.get());
	}

	/**
	 * @author Cigniti
	 * @throws Throwable
	 * @test case id: ENTMgmt-01
	 */
	@Test(description = "CustomerSearchFieldValidations", groups = { "smoke", "regression" })
	public void CustomerSearchFieldValidations() throws Throwable {

		this.lastname = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "LastName");
		this.errormessage1 = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "ErrorMessage1");
		this.errormessage2 = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "ErrorMessage2");
		this.errormessage3 = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "ErrorMessage3");

		try {

			// **************CustomerSearchLastNameValidation*************************
			// -------------------------------------------------------------------------------------------------------------------------------//
			signupPg.login(username, password);
			signupPg.actionLib.Click(CommonVariables.CommonDriver.get().findElement(Login_OR.SEARCH_TAB), "SEARCH TAB");
			signupPg.actionLib.Click(CommonVariables.CommonDriver.get().findElement(Login_OR.SEARCH_CUSTOMER),
					"SEARCH_CUSTOMER");
			signupPg.actionLib.isTextPresentOnPage("Customer");
			signupPg.actionLib.isElementDisabled(CommonVariables.CommonDriver.get().findElement(Login_OR.SEARCH),
					"SEARCH BUTTON");
			signupPg.actionLib.type(CommonVariables.CommonDriver.get().findElement(Login_OR.LASTNAME), lastname);
			signupPg.actionLib.isTextPresentOnPage(errormessage1);
			signupPg.actionLib.isTextPresentOnPage(errormessage3);
			signupPg.actionLib.type(CommonVariables.CommonDriver.get().findElement(Login_OR.LASTNAME), specialchars);
			signupPg.actionLib.isTextPresentOnPage(errormessage2);
			// -------------------------------------------------------------------------------------------------------------------------------//

			// **************CustomerSearchFirstNameValidation*************************
			// -------------------------------------------------------------------------------------------------------------------------------//
			this.firstname = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 2, "FirstName");
			this.errormessage1 = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 2, "ErrorMessage1");
			this.errormessage2 = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 2, "ErrorMessage2");
			this.errormessage3 = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 2, "ErrorMessage3");

			signupPg.actionLib.RefreshBrowser();
			signupPg.actionLib.isElementDisabled(CommonVariables.CommonDriver.get().findElement(Login_OR.SEARCH),
					"SEARCH BUTTON");
			signupPg.actionLib.type(CommonVariables.CommonDriver.get().findElement(Login_OR.FIRSTNAME), firstname);
			signupPg.actionLib.isTextPresentOnPage(errormessage1);
			signupPg.actionLib.isTextPresentOnPage(errormessage2);
			signupPg.actionLib.type(CommonVariables.CommonDriver.get().findElement(Login_OR.FIRSTNAME), specialchars);
			signupPg.actionLib.isTextPresentOnPage(errormessage3);
			// -------------------------------------------------------------------------------------------------------------------------------//

			// **************CustomerSearchSSNValidation*************************
			// -------------------------------------------------------------------------------------------------------------------------------//
			this.socialsecurity = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 3, "SocialSecurity");
			this.specialchars = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 3, "SpecialChars");
			this.errormessage1 = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 3, "ErrorMessage1");
			this.errormessage2 = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 3, "ErrorMessage2");
			this.errormessage3 = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 3, "ErrorMessage3");
			this.errormessage4 = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 3, "ErrorMessage4");

			signupPg.actionLib.RefreshBrowser();
			signupPg.actionLib.isElementDisabled(CommonVariables.CommonDriver.get().findElement(Login_OR.SEARCH),
					"SEARCH BUTTON");
			signupPg.actionLib.type(CommonVariables.CommonDriver.get().findElement(Login_OR.SSN), socialsecurity);
			signupPg.actionLib.isTextPresentOnPage(errormessage1);
			signupPg.actionLib.isTextPresentOnPage(errormessage2);
			signupPg.actionLib.isTextPresentOnPage(errormessage3);
			signupPg.actionLib.type(CommonVariables.CommonDriver.get().findElement(Login_OR.SSN), specialchars);
			signupPg.actionLib.isTextPresentOnPage(errormessage1);
			signupPg.actionLib.isTextPresentOnPage(errormessage2);
			signupPg.actionLib.isTextPresentOnPage(errormessage3);
			signupPg.actionLib.isTextPresentOnPage(errormessage4);
			// -------------------------------------------------------------------------------------------------------------------------------//

			// **************CustomerSearchCustomer#Validation*************************
			// -------------------------------------------------------------------------------------------------------------------------------//
			this.customerno = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 4, "CustomerNo");

			signupPg.actionLib.RefreshBrowser();
			signupPg.actionLib.isElementDisabled(CommonVariables.CommonDriver.get().findElement(Login_OR.SEARCH),
					"SEARCH BUTTON");
			signupPg.actionLib.type(CommonVariables.CommonDriver.get().findElement(Login_OR.CUSTOMER_NO), customerno);
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
			// -------------------------------------------------------------------------------------------------------------------------------//
		}

		catch (Exception e) {
			testCaseFailureReason = "Failed to complete Customer Search Using Customer#.";
			String stackTrace = extenLogs.getStackTraceAsString("Test", testCaseFailureReason, e);
			extenLogs.fail("CustomerSearchFieldValidations ", testCaseFailureReason + "Failed Reason : " + stackTrace);
			testCaseStatus = false;
		}
		TestCaseStatus(testCaseStatus, testCaseFailureReason);
	}

}