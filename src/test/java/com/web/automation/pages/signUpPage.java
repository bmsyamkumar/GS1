package com.web.automation.pages;

import java.util.Set;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import com.google.common.base.Optional;
import com.web.automation.accelerators.ActionsLibrary;
import com.web.automation.logs.ExtentLogs;
import com.web.automation.objectrepository.Login_OR;
import com.web.automation.utilities.CommonVariables;

public class signUpPage extends BasePage {

	public EventFiringWebDriver driver;
	public ExtentLogs extentLogs = new ExtentLogs();
	Optional<Long> timeoutInSecond = Optional.of(Long.parseLong("5"));

	public signUpPage(WebDriver driver) {
		super(driver);
		if (!actionLib.GetURl().contains("renters-choice-inc.com")) {
			throw new IllegalStateException("This is not the 'Signup' page.");
		}
	}

	public void login(String username, String password) throws Throwable {
		try {
			if (actionLib.IsElementVisible(Login_OR.USER_NAME)) {
				actionLib.type(CommonVariables.CommonDriver.get().findElement(Login_OR.USER_NAME), username);
				actionLib.type(CommonVariables.CommonDriver.get().findElement(Login_OR.PASSWORD), password);
				actionLib.Click(CommonVariables.CommonDriver.get().findElement(Login_OR.SIGNIN), "SIGNIN BUTTON");
				extentLogs.pass("SIGNIN", "Clicked on SIGNIN Button successfully.");
				actionLib.IsElementVisible(Login_OR.LOGOUT);

			} else {
				extentLogs.fail("User Name", "'User Name' User Name not Found.");
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public void logout() throws Throwable {
		try {
			if (actionLib.IsElementVisible(Login_OR.LOGOUT)) {
				actionLib.Click(CommonVariables.CommonDriver.get().findElement(Login_OR.LOGOUT));
				extentLogs.pass("LOGOUT", "Clicked on LOGOUT Button successfully.");
				actionLib.IsElementVisible(Login_OR.SIGNIN_FORM);
				extentLogs.pass("SIGNIN_FORM", "SIGNIN_FORM is visible, You are in signup page.");

			} else {
				extentLogs.fail("User Name", "'User Name' User Name not Found.");
			}
		} catch (Exception e) {
			throw e;
		}
	}

	

	/****************************************************************************************************************************
	 * Function Name : switchToRecentOpenedWindow() Description : To switch
	 * form old window to recent opened window
	 * 
	 * @throws InterruptedException
	 ***************************************************************************************************************************/
	public void switchToRecentOpenedWindow() throws InterruptedException {
		String strPrntWnd = ActionsLibrary.driver.getWindowHandle();
		Thread.sleep(2000);
		Set<String> strWnds = ActionsLibrary.driver.getWindowHandles();
		for (String a : strWnds) {
			if (!a.equalsIgnoreCase(strPrntWnd))
				ActionsLibrary.driver.switchTo().window(a);
			Thread.sleep(2000);
		}
	}

	/****************************************************************************************************************************
	 * Function Name : closeAllAndSwitchToMainWindow() Description : This method
	 * switch to parent window on web page.
	 ***************************************************************************************************************************/
	public void closeAllAndSwitchToMainWindow(String currentWindow) {
		for (String handle : ActionsLibrary.driver.getWindowHandles()) {
			if (!handle.equals(currentWindow)) {
				ActionsLibrary.driver.switchTo().window(handle);
				ActionsLibrary.driver.close();
			}
		}
		switchToWindow(currentWindow);
	}

	/****************************************************************************************************************************
	 * Function Name : switchToWindow() Description : This method switches to a
	 * window.
	 ***************************************************************************************************************************/
	public void switchToWindow(String window) {

		ActionsLibrary.driver.switchTo().window(window);
	}

	/****************************************************************************************************************************
	 * Function Name : getCurrentWindow() Description : This method get the
	 * current window.
	 ***************************************************************************************************************************/
	public String getCurrentWindow() {

		return ActionsLibrary.driver.getWindowHandle();
	}
	/*
	 * public EnrollmentPage registration(String email, String password) throws
	 * Throwable { EnrollmentPage enrollmentPg = null; try {
	 * actionLib.WaitForElementVisible(Login_OR.txtEmailAddress, 20); if
	 * (actionLib.IsElementVisible(Login_OR.txtEmailAddress)) {
	 * actionLib.type(CommonVariables.CommonDriver.get().findElement(Login_OR.
	 * txtEmailAddress), email); extentLogs.pass("Email",
	 * "User successfully entered email : ******* " + email +
	 * " ******* in signup page.");
	 * actionLib.type(CommonVariables.CommonDriver.get().findElement(Login_OR.
	 * txtPassword), password); extentLogs.pass("Password",
	 * "User successfully entered password : ******* " + password +
	 * " ******* in signup page.");
	 * actionLib.type(CommonVariables.CommonDriver.get().findElement(Login_OR.
	 * txtRecConfirmPassword), password); extentLogs.pass("Confirm Password",
	 * "User successfully entered confirm password : ******* " + password +
	 * " ******* in signup page.");
	 * actionLib.Click(actionLib.FindElement(Login_OR.lnkContinue,
	 * timeoutInSecond), 20); extentLogs.pass("Continue Button",
	 * "'Continue' button clicked successfully."); enrollmentPg = new
	 * EnrollmentPage(CommonVariables.CommonDriver.get()); } } catch (Exception
	 * e) { throw e; } return enrollmentPg; }
	 */
}
