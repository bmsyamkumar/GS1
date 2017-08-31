package com.web.automation.pages;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.google.common.base.Optional;
import com.web.automation.accelerators.ActionsLibrary;
import com.web.automation.logs.ExtentLogs;
import com.web.automation.objectrepository.Login_OR;
import com.web.automation.objectrepository.SugarCRM_OR;
import com.web.automation.utilities.CommonVariables;

public class SugarCRMPage extends BasePage {

	public EventFiringWebDriver driver;
	public ExtentLogs extentLogs = new ExtentLogs();
	Optional<Long> timeoutInSecond = Optional.of(Long.parseLong("5"));

	public SugarCRMPage(WebDriver driver) {
		super(driver);
		
	}

	public boolean login(SugarContactsInput objData) throws Throwable {
			try {
				extentLogs.info("Login to Sugar CRM", "Login with User :- " + objData.UserName);
			if (actionLib.IsElementVisible(SugarCRM_OR.USER_NAME)) {
				actionLib.type(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.USER_NAME), objData.UserName,"Login UserName");
				actionLib.type(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.PASSWORD), objData.Password,"Login Password");
				actionLib.Click(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.Login), "Login BUTTON");
				extentLogs.pass("SIGNIN", "Clicked on SIGNIN Button successfully.");
				actionLib.IsElementVisible(SugarCRM_OR.LogOut);
				
				return true;
			} else {
				extentLogs.fail("User Name", "'User Name' User Name not Found.");
				return false;
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public void logout() throws Throwable {
		try {
			extentLogs.info("Logout to Sugar CRM", "Logout ");
			if (actionLib.IsElementVisible(SugarCRM_OR.LogOut)) {
				actionLib.Click(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.LogOut));
				extentLogs.pass("LOGOUT", "Clicked on LOGOUT Button successfully.");
				actionLib.IsElementVisible(SugarCRM_OR.USER_NAME);
				extentLogs.pass("LOGIN_FORM", "LOGIN_FORM is visible, You are in Login page.");

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

	/****************************************************************************************************************************
	 * Function Name : Enter Contact Details
	 * window.
	 * @throws Throwable 
	 ***************************************************************************************************************************/
	public  boolean createContact(SugarContactsInput objData) throws Throwable
	{
		try
		{
			extentLogs.info("Create Contacts", "Create Contacts with User :- " + objData.UserName);
			//Click On Contacts
			actionLib.Click(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.lnkHome), "Home");
			actionLib.Click(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.lnkContactsHeader), "CONTACTS TAB");
			extentLogs.pass("Contacts Tab", "Clicked on CONTACTS Tab successfully.");
			actionLib.Click(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.lnkCreateContacts), "CREATE CONTACTS Shortcuts");
			//Enter Details
			actionLib.selectByVisibleText(SugarCRM_OR.cboSalutation,objData.Salutation,"Salutation");
			actionLib.type(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.txtFirst_Name), objData.FirstName,"Contact First Name");
			actionLib.type(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.txtLast_Name), objData.LastName,"Contact Last Name");
			actionLib.type(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.txtOffice_Phone), objData.OfficePhone,"Office Phone");
			actionLib.type(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.txtMobile), objData.Mobile,"Mobile");
			actionLib.type(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.txtHome), objData.Home,"Home");
			actionLib.type(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.txtOtherPhone), objData.OtherPhone,"Other Phone");
			actionLib.type(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.txtFax), objData.Fax,"Fax");
			actionLib.type(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.txtBirthdate), objData.Birthdate,"Birth Date");
			actionLib.selectByVisibleText(SugarCRM_OR.cboLeadSource,objData.LeadSource,"Lead Source");
			actionLib.type(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.txtTitle), objData.Title,"Title");
			actionLib.type(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.txtDepartment), objData.Department,"Department");
			//Check To Sync to OutLook
			if(objData.SyncToOutLookl)
			{
				actionLib.Click(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.chkSyncToOutlook), "Sync to OutLook");
			}
			//Check to Do Not Call
			if(objData.DoNotCall)
			{
				actionLib.Click(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.chkDoNotCall), "Do Not Call");
			}
			actionLib.type(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.AssignTo), objData.AssignTo,"Assign To");
			actionLib.type(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.txtAssistant), objData.Assistant,"Assistant");
			actionLib.type(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.txtAssistantPhone), objData.AssistantPhone,"Assistant Phone");
			//Email Address
			actionLib.type(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.txtEmailAddress), objData.Email,"Email");
			if(objData.OptedOut)
			{
				actionLib.Click(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.chkOptedOut), "Email Opted Out");
			}
			if(objData.Invalid)
			{
				actionLib.Click(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.chkInvalid), "Email Invalid");
			}
			
			//Primary Address Information
			actionLib.type(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.txtPrimaryAddress), objData.PrimaryAddress,"Primary Address");
			actionLib.type(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.txtPrimaryCity), objData.PrimaryAddressCity,"Primary Address City");
			actionLib.type(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.txtPrimaryState), objData.PrimaryAddressState,"Primary Address State");
			actionLib.type(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.txtPrimaryPostalCode), objData.PrimaryAddressPostalCode,"Primary Address Postal Code");
			actionLib.type(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.txtPrimaryCountry), objData.PrimaryAddressCountry,"Primary Address Country");
			//Other Address
			actionLib.type(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.txtOtherAddress), objData.OtherAddress,"Other Address");
			actionLib.type(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.txtOtherCity), objData.OtherAddressCity,"Other Address City");
			actionLib.type(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.txtOtherState), objData.OtherAddressState,"Other Address State");
			actionLib.type(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.txtOtherPostalCode), objData.OtherAddressPostalCode,"Other Address Postal Code");
			actionLib.type(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.txtOtherCountry), objData.OtherAddressCountry,"Other Address Country");
			
			//Enter Description
			actionLib.type(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.txtDescription), objData.Description,"Description");
			Thread.sleep(1000);
			//Click on Save Button
			actionLib.Click(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.btnSaveContact), "Save Contact Details");
			Thread.sleep(1000);
			if(actionLib.IsElementVisible(SugarCRM_OR.btnSaveContact))
			{
				actionLib.Click(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.btnSaveContact), "Save Contact Details");
			}
			return true;
		}
		catch(Exception e)
		{	 
			throw e;
		}
	}
	
	/****************************************************************************************************************************
	 * Function Name : Verify Contact Details
	 * window.
	 * @throws Throwable 
	 ***************************************************************************************************************************/
	public  boolean verifyContactDetails(SugarContactsInput objData) throws Throwable
	{
		try
		{
			extentLogs.info("Verify Contacts", "Verify Contacts for User :- " + objData.UserName);
			//Click On Contacts
			actionLib.Click(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.lnkHome), "Home");
			actionLib.Click(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.lnkContactsHeader), "CONTACTS TAB");
			extentLogs.pass("Contacts Tab", "Clicked on CONTACTS Tab successfully.");
			actionLib.Click(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.lnkContacts), "CONTACTS Shortcuts");
			//Search the Contacts
			//Click on Basic Search
			actionLib.Click(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.tabBasicSearch), "BASIC SEARCH");
			//Clear the Search Result
			actionLib.Click(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.tabBasicSearchClear), "CLEAR BASIC SEARCH");
			//Enter First Name
			actionLib.type(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.txtSearch_FirstName), objData.FirstName,"First Name");
			//Enter Last Name
			actionLib.type(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.txtSearch_LastName), objData.LastName,"Last Name");
			//Click On Search Button
			actionLib.Click(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.btnSearch_Search), "BASIC SEARCH");
			WebElement ele=CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.lnkSearch_SearchResult);
			List<WebElement> lstDetails=ele.findElements(By.tagName("tr"));
			System.out.println(lstDetails.size());
			boolean blnContact=false;
			if(lstDetails.size() > 0)
			{
				boolean blnStatus = true;
				actionLib.Click(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.lnkSearch_ContactResult), "Search Result for " + objData.FirstName);
				String strName=actionLib.getElementText(SugarCRM_OR.txtDetails_Name, "Name");
				String strLeadSource=actionLib.getElementText(SugarCRM_OR.txtDetails_LeadSource, "Lead Source");
				String strTitle=actionLib.getElementText(SugarCRM_OR.txtDetails_Title, "Title");
				String strDepartment=actionLib.getElementText(SugarCRM_OR.txtDetails_Department, "Department");
				boolean blnSyncToOutLook =CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.txtDetails_SyncToOutlook).isSelected();
				boolean blnDoNotCall =CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.txtDetails_DoNotCall).isSelected();
				String strDateModified=actionLib.getElementText(SugarCRM_OR.txtDetails_DateModified, "Date Modified");
				String strPrimaryAddress=actionLib.getElementText(SugarCRM_OR.txtDetails_PrimaryAddress, "Primary Address");
				String strDetailDescription=actionLib.getElementText(SugarCRM_OR.txtDetails_Description, "Description");
				String strEmail=actionLib.getElementText(SugarCRM_OR.txtDetails_Email, "Email");
				String strOfficePhone=actionLib.getElementText(SugarCRM_OR.txtDetails_OfficePhone, "Office Phone");
				String strMobile=actionLib.getElementText(SugarCRM_OR.txtDetails_Mobile, "Mobile");
				String strHome=actionLib.getElementText(SugarCRM_OR.txtDetails_Home, "Home");
				String strOtherPhone=actionLib.getElementText(SugarCRM_OR.txtDetails_OtherPhone, "Other Phone");
				String strFax=actionLib.getElementText(SugarCRM_OR.txtDetails_Fax, "Fax");
				String strBirthDate=actionLib.getElementText(SugarCRM_OR.txtDetails_BirthDate, "Birthdate");
				String strAssistant=actionLib.getElementText(SugarCRM_OR.txtDetails_Assistant, "Assistant");
				String strAssistantPhone=actionLib.getElementText(SugarCRM_OR.txtDetails_AssistantPhone, "Assistant Phone");
				try {
					if(strName.contains(objData.FirstName) && strName.contains(objData.LastName))
					{
						extentLogs.pass("Contact Name","First Name :- " + objData.FirstName + " Last Name :- " + objData.LastName + " added successfully");
					}
					else
					{
						extentLogs.fail("Contact Name","First Name :- " + objData.FirstName + " Last Name :- " + objData.LastName + " is not added successfully");
						blnStatus=false;
					}
				} catch (Exception e) {
					
					throw e;
				}
				try {
					if(strLeadSource.contains(objData.LeadSource))
					{
						extentLogs.pass("Lead Source Name","Lead Source Name :- "+ objData.LeadSource + " added successfully");
					}
					else
					{
						extentLogs.fail("Lead Source Name","Lead Source Name :- "+ objData.LeadSource + " is not added successfully");
						blnStatus=false;
					}
				} catch (Exception e) {
					
					throw e;
				}
				try {
					if(strTitle.contains(objData.Title))
					{
						extentLogs.pass("Title","Title :- "+ objData.Title + " added successfully");
					}
					else
					{
						extentLogs.fail("Title","Title :- "+ objData.Title + " is not added successfully");
						blnStatus=false;
					}
				} catch (Exception e) {
					
					throw e;
				}
				try {
					if(strDepartment.contains(objData.Department))
					{
						extentLogs.pass("Department","Department :- "+ objData.Department + " added successfully");
					}
					else
					{
						extentLogs.fail("Department","Department :- "+ objData.Department + " is not added successfully");
						blnStatus=false;
					}
				} catch (Exception e) {
					
					throw e;
				}
				try {
					if(blnSyncToOutLook==objData.SyncToOutLookl)
					{
						extentLogs.pass("Sync To OutLook","Sync To Outlook :- "+ objData.SyncToOutLookl + " checked successfully");
					}
					else
					{
						extentLogs.fail("Sync To OutLook","Sync To Outlook :- "+ objData.SyncToOutLookl + " not checked successfully");
						blnStatus=false;
					}
				} catch (Exception e) {
					
					throw e;
				}
				try {
					if(blnDoNotCall==objData.DoNotCall)
					{
						extentLogs.pass("Do Not Call","Do Not Call :- "+ objData.DoNotCall + " checked successfully");
					}
					else
					{
						extentLogs.fail("Do Not Call","Do Not Call :- "+ objData.DoNotCall + " checked successfully");
						blnStatus=false;
					}
				} catch (Exception e) {
					
					throw e;
				}
				try {
					if(strDateModified.length()> 5)
					{
						extentLogs.pass("Data Modified Status","Date Modified "+ strDateModified + " display successfully");				
					}
					else
					{
						extentLogs.fail("Data Modified Status","Date Modified "+ strDateModified + " display successfully");	
						blnStatus=false;				
					}
				} catch (Exception e) {
					
					throw e;
				}	
				try {
					if(strPrimaryAddress.contains(objData.PrimaryAddress) && strPrimaryAddress.contains(objData.PrimaryAddressCity) && strPrimaryAddress.contains(objData.PrimaryAddressCountry) && 
							strPrimaryAddress.contains(objData.PrimaryAddressPostalCode) && strPrimaryAddress.contains(objData.PrimaryAddressState)
							)
					{
						extentLogs.pass("Primary Address","Primary Address Matched successfully" + strPrimaryAddress);
					}
					else
					{
						extentLogs.fail("Primary Address","Primary Address not Matched successfully" + strPrimaryAddress);
						blnStatus=false;
					}
				} catch (Exception e) {
					
					throw e;
				}
				try {
					if(strDetailDescription.contains(objData.Description))
					{
						extentLogs.pass("Description","Description :- "+ objData.Description + " added successfully");
					}
					else
					{
						extentLogs.fail("Description","Description :- "+ objData.Description + " is not added successfully");
						blnStatus=false;
					}
				} catch (Exception e) {
					
					throw e;
				}
				try {
					if(strEmail.contains(objData.Email))
					{
						extentLogs.pass("Email","Email :- "+ objData.Email + " added successfully");
					}
					else
					{
						extentLogs.fail("Email","Email :- "+ objData.Email + " is not added successfully");
						blnStatus=false;
					}
				} catch (Exception e) {
					
					throw e;
				}
				try {
					String strNewPhone=strOfficePhone.replace("(","").replace(")", "").replace("-", "").replace(" ", "");
					if(strNewPhone.contains(objData.OfficePhone))
					{
						extentLogs.pass("Office Phone","Office Phone :- "+ objData.OfficePhone + " added successfully");
					}
					else
					{
						extentLogs.fail("Office Phone","Office Phone :- "+ objData.OfficePhone + " is not added successfully");
						blnStatus=false;
					}
				} catch (Exception e) {
					
					throw e;
				}
				try {
					String strNewMobile=strMobile.replace("(","").replace(")", "").replace("-", "").replace(" ", "");
					if(strNewMobile.contains(objData.Mobile))
					{
						extentLogs.pass("Mobile","Mobile :- "+ objData.Mobile + " added successfully");
					}
					else
					{
						extentLogs.fail("Mobile","Mobile :- "+ objData.Mobile + " is not added successfully");
						blnStatus=false;
					}
				} catch (Exception e) {
					
					throw e;
				}
				try {
					String strNewHome=strHome.replace("(","").replace(")", "").replace("-", "").replace(" ", "");
					if(strNewHome.contains(objData.Home))
					{
						extentLogs.pass("Home","Home :- "+ objData.Home + " added successfully");
					}
					else
					{
						extentLogs.fail("Home","Home :- "+ objData.Home + " is not added successfully");
						blnStatus=false;
					}
				} catch (Exception e) {
					
					throw e;
				}
				try {
					String strNewOtherPhone=strOtherPhone.replace("(","").replace(")", "").replace("-", "").replace(" ", "");
					if(strNewOtherPhone.contains(objData.OtherPhone))
					{
						extentLogs.pass("Other Phone","Other Phone :- "+ objData.OtherPhone + " added successfully");
					}
					else
					{
						extentLogs.fail("Other Phone","Other Phone :- "+ objData.OtherPhone + " is not added successfully");
						blnStatus=false;
					}
				} catch (Exception e) {
					
					throw e;
				}
				try {
					String strNewFax=strFax.replace("(","").replace(")", "").replace("-", "").replace(" ", "");
					if(strNewFax.contains(objData.Fax))
					{
						extentLogs.pass("Fax","Fax :- "+ objData.Fax + " added successfully");
					}
					else
					{
						extentLogs.fail("Fax","Fax :- "+ objData.Fax + " added successfully");
						blnStatus=false;
					}
				} catch (Exception e) {
					
					throw e;
				}
				try {
					if(strBirthDate.contains(objData.Birthdate))
					{
						extentLogs.pass("BirthDate","BirthDate :- "+ objData.Birthdate + " added successfully");
					}
					else
					{
						extentLogs.pass("BirthDate","BirthDate :- "+ objData.Birthdate + " is not added successfully");
						blnStatus=false;
					}
				} catch (Exception e) {
					
					throw e;
				}
				try {
					if(strAssistant.contains(objData.Assistant))
					{
						extentLogs.pass("Assistant Name","Assistant Name :- "+ objData.Assistant + " added successfully");
					}
					else
					{
						extentLogs.fail("Assistant Name","Assistant Name :- "+ objData.Assistant + " is not added successfully");
						blnStatus=false;
					}
				} catch (Exception e) {
					
					throw e;
				}
				try {
					if(strAssistantPhone.contains(objData.AssistantPhone))
					{
						extentLogs.pass("Assistant Phone","Assistant Phone :- "+ objData.AssistantPhone + " added successfully");
					}
					else
					{
						extentLogs.fail("Assistant Phone","Assistant Phone :- "+ objData.AssistantPhone + " is not added successfully");
						blnStatus=false;
					}
				} catch (Exception e) {
					
					throw e;
				}
				//Report Overall Report
				if(blnStatus)
				{
					extentLogs.pass("Verify contact details","Contact details data validation successfully passed" );
				}
				else
				{
					extentLogs.fail("Verify contact details","Contact details data validation successfully passed" );
				}
				blnContact=blnStatus;
			}
			return blnContact;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	/****************************************************************************************************************************
	 * Function Name : Delete Contact Details
	 * window.
	 * @throws Throwable 
	 ***************************************************************************************************************************/
	public boolean deleteContact(SugarContactsInput objData) throws Throwable
	{
		try
		{
			extentLogs.info("Delete Contacts", "Delete Contacts with User :- " + objData.UserName);
			//Click On Contacts
			actionLib.Click(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.lnkHome), "Home");
			actionLib.Click(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.lnkContactsHeader), "CONTACTS TAB");
			extentLogs.pass("Contacts Tab", "Clicked on CONTACTS Tab successfully.");
			actionLib.Click(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.lnkContacts), "CONTACTS Shortcuts");
			//Search the Contacts
			//Click on Basic Search
			actionLib.Click(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.tabBasicSearch), "BASIC SEARCH");
			//Clear the Search Result
			actionLib.Click(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.tabBasicSearchClear), "CLEAR BASIC SEARCH");
			//Enter First Name
			actionLib.type(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.txtSearch_FirstName), objData.FirstName,"First Name");
			//Enter Last Name
			actionLib.type(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.txtSearch_LastName), objData.LastName,"Last Name");
			//Click On Search Button
			actionLib.Click(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.btnSearch_Search), "BASIC SEARCH");
			
			WebElement ele=CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.lnkSearch_SearchResult);
			List<WebElement> lstDetails=ele.findElements(By.tagName("tr"));
			boolean blnContact=false;
			if(lstDetails.size() > 5)
			{
			
				actionLib.Click(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.lnkSearch_ContactResult), "Search Result for " + objData.FirstName);
				String strName=actionLib.getElementText(SugarCRM_OR.txtDetails_Name, "Name");
				if(strName.contains(objData.FirstName) && strName.contains(objData.LastName))
				{
					extentLogs.pass("Contact Name Search","First Name :- " + objData.FirstName + " Last Name :- " + objData.LastName + " added successfully");
					actionLib.Click(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.btnContact_Delete), "Delete Contact for " + objData.FirstName);
					//Thread.sleep(5000);
					actionLib.AcceptAlert();					
				}
				extentLogs.info("Verify Contacts After Delete", "Verify Contacts after Delete for User :- " + objData.UserName);
				//Click On Contacts
				actionLib.Click(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.lnkHome), "Home");
				actionLib.Click(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.lnkContactsHeader), "CONTACTS TAB");
				extentLogs.pass("Contacts Tab", "Clicked on CONTACTS Tab successfully.");
				actionLib.Click(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.lnkContacts), "CONTACTS Shortcuts");
				//Search the Contacts
				//Click on Basic Search
				actionLib.Click(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.tabBasicSearch), "BASIC SEARCH");
				//Clear the Search Result
				actionLib.Click(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.tabBasicSearchClear), "CLEAR BASIC SEARCH");
				//Enter First Name
				actionLib.type(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.txtSearch_FirstName), objData.FirstName,"First Name");
				//Enter Last Name
				actionLib.type(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.txtSearch_LastName), objData.LastName,"Last Name");
				//Click On Search Button
				actionLib.Click(CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.btnSearch_Search), "BASIC SEARCH");
				
				WebElement eleDelete=CommonVariables.CommonDriver.get().findElement(SugarCRM_OR.lnkSearch_SearchResult);
				List<WebElement> lstDelete=eleDelete.findElements(By.tagName("tr"));
				if(lstDelete.size() >5)
				{
					extentLogs.fail("Contact Name Delete ","First Name :- " + objData.FirstName + " Last Name :- " + objData.LastName + " is  not Deleted  successfully");
					blnContact=true;
				}
				else
				{
					extentLogs.pass("Contact Name Delete ","First Name :- " + objData.FirstName + " Last Name :- " + objData.LastName + " Deleted  successfully");					
				}
			}else
			{
				blnContact=true;
			}
			return blnContact;
		}
		catch(Exception e)
		{
			throw e;
			
		}
	}
}
