package com.web.automation.objectrepository;

import org.openqa.selenium.By;

public class Login_OR {

	public static By USER_NAME = By.cssSelector("input[id$=_login]");
	public static By PASSWORD = By.cssSelector("input[id$=_password]");
	public static By SIGNIN = By.cssSelector("button[type='submit']");
	public static By LOGOUT = By.cssSelector("a[href$='/logout']");
	public static By SIGNIN_FORM = By.cssSelector(".form.sign-in-form");
	public static By SEARCH_TAB = By.cssSelector("a[href$='search']");
	
	public static By SEARCH_CUSTOMER = By.cssSelector("a[href$='customer']");
	public static By LASTNAME = By.cssSelector("#lastName");
	public static By FIRSTNAME = By.cssSelector("input[name='firstName']");
	public static By SEARCH = By.cssSelector("button[type='submit']");
	public static By STORE_00488 = By.xpath("//div[@class='span1 ng-binding' and contains(.,'00488')]");
	public static By STOP_HANDSYMBOL = By.cssSelector(".rac-stop.danger");
	public static By PDF_FILE = By.cssSelector("a[href$='agreement/pdf/9999198306047']>i");
	
	
	public static By CUSTOMER_NO = By.cssSelector("#customerNo");
	public static By SSN = By.cssSelector("#ssn");
	public static By DATEOFBIRTH = By.cssSelector("input[name='dateOfBirth']");
	public static By EMAIL = By.cssSelector("#email");
	public static By PHONENO = By.cssSelector("#phoneNo");
	public static By STATEID = By.cssSelector("#stateId");
	public static By GOVERNMENTIDTYPEID = By.cssSelector("#governmentIdTypeId");
	public static By GOVERNMENTIDNO = By.cssSelector("#governmentIdNo");
	public static By STORENO = By.cssSelector("#storeNo");
	public static By LINEOFBUSINESSID = By.cssSelector("#lineOfBusinessId");
	
	
	public static By btnContinue = By.cssSelector("#ctl00_ContentPlaceHolder1_btnEnrol");
	public static By loggedInUser = By.cssSelector("span[id$='lblName']");

}
