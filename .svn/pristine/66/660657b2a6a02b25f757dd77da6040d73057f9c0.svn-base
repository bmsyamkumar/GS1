package com.web.automation.scripts;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.web.automation.logs.ExtentLogs;
import com.web.automation.objectrepository.Login_OR;
import com.web.automation.accelerators.TestEngineWeb;
import com.web.automation.fileutils.ExcelReader;
import com.web.automation.pages.signUpPage;
import com.web.automation.utilities.CommonVariables;

public class LoginValidations extends TestEngineWeb {
	private signUpPage signupPg;
	private String testCaseFailureReason = "";
	private boolean testCaseStatus = true;
	private String username, password, lastname, firstname, Customer, CustomerInformation;
	protected String sheetPath = System.getProperty("user.dir").replace("\\", "/") + "/testdata/TestData.xlsx";
	protected String sheetName = "LoginValidations";
	Map<String, List<String>> testdata = null;
	private ExtentLogs extenLogs = new ExtentLogs();

	/**
	 * @author Cigniti
	 * @param status
	 * @param reason
	 */
	public void TestCaseStatus(Boolean status, String reason) {
		if (status == false) {
			Assert.fail("Test Case Failed because - " + reason);
		}
	}

	@BeforeMethod(alwaysRun = true, groups = { "s.moke", "regression" })
	public void beforeMethod() throws IOException {
		this.username = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "Username");
		this.password = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "Password");
		this.lastname = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "LastName");
		this.firstname = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "FirstName");
		this.Customer = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "Customer");
		this.CustomerInformation = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "CustomerInformation");
	}

	/**
	 * @author Cigniti
	 * @throws Throwable
	 * @test case id: ENTMgmt-01
	 */
	@Test(description = "Login", groups = { "smoke", "regression" })
	public void Login() throws Throwable {
		try {
			signupPg = new signUpPage(CommonVariables.CommonDriver.get());
			signupPg.login(username, password);
			signupPg.actionLib.Click(CommonVariables.CommonDriver.get().findElement(Login_OR.SEARCH_TAB), "SEARCH TAB");
			signupPg.actionLib.Click(CommonVariables.CommonDriver.get().findElement(Login_OR.SEARCH_CUSTOMER),
					"SEARCH_CUSTOMER");
			signupPg.actionLib.isTextPresentOnPage(Customer);
			signupPg.actionLib.type(CommonVariables.CommonDriver.get().findElement(Login_OR.LASTNAME), lastname);
			signupPg.actionLib.type(CommonVariables.CommonDriver.get().findElement(Login_OR.FIRSTNAME), firstname);
			signupPg.actionLib.Click(CommonVariables.CommonDriver.get().findElement(Login_OR.SEARCH), "SEARCH BUTTON");
			signupPg.actionLib.Click(CommonVariables.CommonDriver.get().findElement(Login_OR.STORE_00488),
					"STORE_00488");
			signupPg.actionLib.isTextPresentOnPage(CustomerInformation);
			signupPg.actionLib
					.IsElementVisible(CommonVariables.CommonDriver.get().findElement(Login_OR.STOP_HANDSYMBOL));
			signupPg.extentLogs.pass("STOP_HANDSYMBOL", "Successfully verified 'STOP_HANDSYMBOL' on Page.");

			String parentWindow = signupPg.getCurrentWindow();
			System.out.println(signupPg.actionLib.GetPageTitle());
			signupPg.actionLib.Click(CommonVariables.CommonDriver.get().findElement(Login_OR.PDF_FILE), "PDF_FILE");
			signupPg.switchToRecentOpenedWindow();
			signupPg.closeAllAndSwitchToMainWindow(parentWindow);
			signupPg.logout();
		}

		catch (Exception e) {
			testCaseFailureReason = "Failed to complete Login.";
			String stackTrace = extenLogs.getStackTraceAsString("Test", testCaseFailureReason, e);
			extenLogs.fail("Enterprise Management Login ", testCaseFailureReason + "Failed Reason : " + stackTrace);
			testCaseStatus = false;
		}
		TestCaseStatus(testCaseStatus, testCaseFailureReason);
	}

}