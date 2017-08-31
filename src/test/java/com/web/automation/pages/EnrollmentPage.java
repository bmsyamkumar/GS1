package com.web.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import com.google.common.base.Optional;
import com.web.automation.logs.ExtentLogs;
import com.web.automation.objectrepository.Login_OR;
import com.web.automation.utilities.CommonVariables;

public class EnrollmentPage extends BasePage {

	public EventFiringWebDriver driver;
	public ExtentLogs extentLogs = new ExtentLogs();
	Optional<Long> timeoutInSecond = Optional.of(Long.parseLong("5"));

	public EnrollmentPage(WebDriver driver) {
		super(driver);
		if (!actionLib.GetURl().contains("Enrollment")) {
			throw new IllegalStateException("This is not the 'Enrollment' page.");
		}
	}

	/*
	 * public void enrollment(String firstname, String lastname, String address,
	 * String suite, String city, String state, String zip, String answer,String
	 * phoneNumber) throws Throwable{ try{
	 * actionLib.WaitForElementVisible(Login_OR.txtFirstName, 20);
	 * actionLib.type(CommonVariables.CommonDriver.get().findElement(Login_OR.
	 * txtFirstName), firstname); extentLogs.pass("Firstname",
	 * "User successfully entered user name : ******* "+firstname+
	 * " ******* in enrollment page.");
	 * 
	 * actionLib.type(CommonVariables.CommonDriver.get().findElement(Login_OR.
	 * txtLastName), lastname); extentLogs.pass("Lastname",
	 * "User successfully entered lastname : ******* "+lastname+
	 * " ******* in enrollment page.");
	 * 
	 * actionLib.type(CommonVariables.CommonDriver.get().findElement(Login_OR.
	 * txtAddress), address); extentLogs.pass("Address",
	 * "User successfully entered address : ******* "+address+
	 * " ******* in enrollment page.");
	 * 
	 * actionLib.type(CommonVariables.CommonDriver.get().findElement(Login_OR.
	 * txtSuite), address); extentLogs.pass("Suite Name",
	 * "User successfully entered address : ******* "+address+
	 * " ******* in enrollment page.");
	 * 
	 * actionLib.type(CommonVariables.CommonDriver.get().findElement(Login_OR.
	 * txtCity), city); extentLogs.pass("City",
	 * "User successfully entered city : ******* "+city+
	 * " ******* in enrollment page.");
	 * 
	 * actionLib.SelectFromDropDown(CommonVariables.CommonDriver.get().
	 * findElement(Login_OR.dropdownState), state, 1); extentLogs.pass("State",
	 * "User successfully selected state : ******* "+state+
	 * " ******* in enrollment page.");
	 * 
	 * actionLib.type(CommonVariables.CommonDriver.get().findElement(Login_OR.
	 * txtPostalcode), zip); extentLogs.pass("City",
	 * "User successfully entered zip code : ******* "+zip+
	 * " ******* in enrollment page.");
	 * 
	 * actionLib.type(CommonVariables.CommonDriver.get().findElement(Login_OR.
	 * txtChallengeAnswer), answer); extentLogs.pass("Challenging Answer",
	 * "User successfully entered challenging answer : ******* "+answer+
	 * " ******* in enrollment page.");
	 * 
	 * actionLib.type(CommonVariables.CommonDriver.get().findElement(
	 * Frontstream_OR.txtMobilePhoneNumber), mobileNumber); extentLogs.pass(
	 * "Mobile Phone Number",
	 * "User successfully entered mobile phone number : ******* "+mobileNumber+
	 * " ******* in enrollment page.");
	 * 
	 * actionLib.type(CommonVariables.CommonDriver.get().findElement(Login_OR.
	 * txtPhoneNumber), phoneNumber); extentLogs.pass("Phone Number",
	 * "User successfully entered phone number : ******* "+phoneNumber+
	 * " ******* in enrollment page.");
	 * 
	 * actionLib.Click(actionLib.FindElement(Login_OR.btnContinue,
	 * timeoutInSecond), 20); extentLogs.pass("Continue Button",
	 * "'Continue' button clicked successfully."); }catch(Exception e){ throw e;
	 * } }
	 */

	public Boolean verifyEnrollment(String username) throws Throwable {
		try {
			if (actionLib.getElementText(Login_OR.loggedInUser, "LoggedIn User in Enrollment Page.")
					.equalsIgnoreCase(username)) {
				extentLogs.pass("Loggedin User", "User successfully verified Loggedin User : ******* " + username
						+ " ******* in enrollment page.");
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw e;
		}
	}
}
