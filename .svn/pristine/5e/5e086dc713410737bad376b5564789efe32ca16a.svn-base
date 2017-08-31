package com.desktop.automation.scripts;

import java.io.IOException;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.desktop.automation.accelerators.DesktopEngine;
import com.desktop.automation.accelerators.DesktopLibrary;
import com.desktop.automation.pages.DesktopPage;
import com.web.automation.logs.ExtentLogs;
import com.web.automation.utilities.CommonVariables;

public class DesktopValidation extends DesktopEngine{

	private String testCaseFailureReason="";
	private boolean testCaseStatus = true;
	private DesktopPage desktoPg;
	private ExtentLogs extenLogs=new ExtentLogs();

	/**
	 * @author Cigniti
	 * @param status
	 * @param reason
	 */
	public void TestCaseStatus(Boolean status, String reason)
	{
		if(status== false)
		{
			Assert.fail("Test Case Failed because - "+reason);
		}
	}

	/**
	 * @author Cigniti
	 * @throws Throwable 
	 * @test case id: TCM-01
	 */
	@Test(enabled=false, priority=1, description="Desktop Teamviewer Scenario",groups = {"smoke","regression"})
	public void Desktop_Teamviewer_Scenario() throws Throwable{
		try{
			String id=objCommonFunc.generateRandomString(8, DesktopLibrary.Mode.NUMERIC);
			desktoPg=new DesktopPage(CommonVariables.winiumDriver.get());
			Boolean bool=desktoPg.teamviewer(id);
			if(bool){
				extenLogs.pass("Desktop Application:: TCM01", "Successfully opened an application.");
			}else{
				testCaseFailureReason = "Failed to open a desktop application.";
				extenLogs.fail("Desktop Application:: TCM01", testCaseFailureReason);
				testCaseStatus=false;
			}
		}
		catch(Exception e)
		{
			testCaseFailureReason = "Failed to open a desktop application.";
			String stackTrace=extenLogs.getStackTraceAsString("Test01",testCaseFailureReason,e);
			extenLogs.fail("Desktop Application:: TCM01", testCaseFailureReason+"Failed Reason : "+stackTrace);
			testCaseStatus=false;
		}
		TestCaseStatus(testCaseStatus,testCaseFailureReason);
	}

	/**
	 * @author Cigniti
	 * @throws Throwable 
	 * @test case id: TCM-02
	 */
	@Test(priority=2, description="Desktop Notepad Scenario",groups = {"smoke","regression"})
	public void Desktop_Notepad_Scenario() throws Throwable{
		try{
			String note="Selenium Automation Note :- "+objCommonFunc.generateRandomString(8, DesktopLibrary.Mode.ALPHA)+"!!!!!";
			String fileName="demo.txt";
			desktoPg=new DesktopPage(CommonVariables.winiumDriver.get());
			Boolean bool=desktoPg.notepad(note,fileName);
			if(bool){
				extenLogs.pass("Desktop Application:: TCM02", "Successfully opened an application.");
			}else{
				testCaseFailureReason = "Failed to open a desktop application.";
				extenLogs.fail("Desktop Application:: TCM02", testCaseFailureReason);
				testCaseStatus=false;
			}
		}
		catch(Exception e)
		{
			testCaseFailureReason = "Failed to open a desktop application.";
			String stackTrace=extenLogs.getStackTraceAsString("Test02",testCaseFailureReason,e);
			extenLogs.fail("Desktop Application:: TCM02", testCaseFailureReason+"Failed Reason : "+stackTrace);
			testCaseStatus=false;
		}
		TestCaseStatus(testCaseStatus,testCaseFailureReason);
	}

	public static void main(String[] args) throws Throwable{
		try {
			DesktopValidation obj=new DesktopValidation();
			//obj.Desktop_Teamviewer_Scenario();
			obj.Desktop_Notepad_Scenario();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}