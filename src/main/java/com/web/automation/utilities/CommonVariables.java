package com.web.automation.utilities;

import java.io.BufferedWriter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.winium.WiniumDriver;
import com.desktop.automation.accelerators.DesktopLibrary;
import com.relevantcodes.extentreports.ExtentTest;
import com.web.automation.accelerators.ActionsLibrary;
import com.web.automation.testmanagement.JiraIntegration;
import com.web.automation.logs.DetailedLogs;

public class CommonVariables {
	public static ThreadLocal<WebDriver> CommonDriver = new ThreadLocal<WebDriver>();
	public static ThreadLocal<WiniumDriver> winiumDriver = new ThreadLocal<WiniumDriver>();
	public static ThreadLocal<Logger> TestClassLog = new ThreadLocal<Logger>();
	public static ThreadLocal<Logger> TestCaseLog = new ThreadLocal<Logger>();
	public static ThreadLocal<String> TestCaseLogPath = new ThreadLocal<String>();
	public static ThreadLocal<String> TestClassName = new ThreadLocal<String>();
	public static ThreadLocal<String> TestCaseName = new ThreadLocal<String>();
	public static ThreadLocal<String> TestMethodName = new ThreadLocal<String>();
	public static ThreadLocal<String> TestClassResult = new ThreadLocal<String>();
	public static ThreadLocal<String> TestCaseResult = new ThreadLocal<String>();
	public static ThreadLocal<DetailedLogs> DetailedLogs = new ThreadLocal<DetailedLogs>();
	public static ThreadLocal<String> LastMethodName = new ThreadLocal<String>();
	public static ThreadLocal<ArrayList<String>> ScenariosHighLevelLog = new ThreadLocal<ArrayList<String>>();
	public static ThreadLocal<ArrayList<String>> TestCasessHighLevelLog = new ThreadLocal<ArrayList<String>>();
	public static ThreadLocal<Map<String, String>> TestMethodDescriptions = new ThreadLocal<Map<String, String>>();
	public static ThreadLocal<Map<String, String>> ResultSheet = new ThreadLocal<Map<String, String>>();
	public static ThreadLocal<Logger> CurrentGlobalLog = new ThreadLocal<Logger>();
	public static ThreadLocal<BufferedWriter> HighLevelLog = new ThreadLocal<BufferedWriter>();
	public static ThreadLocal<Boolean> isEmailCallsent=new ThreadLocal<Boolean>();
	public static ThreadLocal<JiraIntegration> JI = new ThreadLocal<JiraIntegration>();
	public static ThreadLocal<HashMap<String, String>> jiramap = new ThreadLocal<HashMap<String, String>>();
	public static ThreadLocal<Integer> TestCase_Data_Iterator = new ThreadLocal<Integer>();
	public static ThreadLocal<Integer> DataProviderIterator = new ThreadLocal<Integer>();
	public static ThreadLocal<Integer> TotalTCCount = new ThreadLocal<Integer>();
	public static ThreadLocal<Integer> PassTCCount = new ThreadLocal<Integer>();
	public static ThreadLocal<Integer> FailTCCount = new ThreadLocal<Integer>();
	public static ThreadLocal<Integer> SkipTCCount = new ThreadLocal<Integer>();
	public static ThreadLocal<String> PlatformName = new ThreadLocal<String>();
	public static ThreadLocal<String> Version = new ThreadLocal<String>();
	public static ThreadLocal<String> DeviceName = new ThreadLocal<String>();
	public static ThreadLocal<String> TCStartTime = new ThreadLocal<String>();
	public static ThreadLocal<String> TCEndTime = new ThreadLocal<String>();
	public static ThreadLocal<String> ExecutionDate = new ThreadLocal<String>();
	public static ThreadLocal<String> ExecutionStartTime = new ThreadLocal<String>();
	public static ThreadLocal<String> ExecutionEndTime = new ThreadLocal<String>();
	public static ThreadLocal<String> browser = new ThreadLocal<String>();
	public static ThreadLocal<String> Environment = new ThreadLocal<String>();
	public static ThreadLocal<ActionsLibrary> objCommonFunc=new ThreadLocal<ActionsLibrary>();
	public static ThreadLocal<DesktopLibrary> desktopLib=new ThreadLocal<DesktopLibrary>();
	public static ThreadLocal<Boolean> IsGridExecution= new ThreadLocal<Boolean>();
	public static ThreadLocal<Boolean> IsCloudExecution= new ThreadLocal<Boolean>();
	public static ThreadLocal<Boolean> IsLocalExecution= new ThreadLocal<Boolean>();
	public static ThreadLocal<String> userRegion = new ThreadLocal<String>();
	public static ThreadLocal<String> appiumPort = new ThreadLocal<String>();
	public static ThreadLocal<String> emailReport = new ThreadLocal<String>();
	public static ThreadLocal<String> MachineHostName = new ThreadLocal<String>();
	public static ThreadLocal<String> MachineHostAddress = new ThreadLocal<String>();
	public static ThreadLocal<String> gridHubIP = new ThreadLocal<String>();
	public static Integer portNumForBMP = 8019;
	public static String testNGGroupNames = "";
	public static ThreadLocal<String> machineIP = new ThreadLocal<String>();
	public static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();
	public static ThreadLocal<String> extentReportPath = new ThreadLocal<String>();
	public static ThreadLocal<String> screenShotPath = new ThreadLocal<String>();
	public static ThreadLocal<String> ScenarioResultFolderPath = new ThreadLocal<String>();
	public static ThreadLocal<String> RootResultFolderPath = new ThreadLocal<String>();
	public static ThreadLocal<String> Jenkins_JobName = new ThreadLocal<String>();
	public static ThreadLocal<String> Jenkins_BuildNumber = new ThreadLocal<String>();

	/**
	 * <b>Description:</b> This is setter for ActionObject.
	 */
	public static void setActionLib(){
		if(getActionLib()==null)
			objCommonFunc.set(new ActionsLibrary(CommonDriver.get()));
	}

	/**
	 * <b>Description:</b> This is setter for ActionObject.
	 */
	public static void setDesktopActionLib(){
		if(getDesktopActionLib()==null)
			try {
				desktopLib.set(new DesktopLibrary(winiumDriver.get()));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	/**
	 * <b>Description:</b> This is getter for ActionObject.
	 * @return Instance of ActionLib
	 */
	public static ActionsLibrary getActionLib(){
		return objCommonFunc.get();
	}

	/**
	 * <b>Description:</b> This is getter for ActionObject.
	 * @return Instance of ActionLib
	 */
	public static DesktopLibrary getDesktopActionLib(){
		return desktopLib.get();
	}

	/**
	 * <b>Description:</b> This is setter for WebDriver.
	 */
	public static void setDriver(WebDriver driver){
		CommonDriver.set(driver);
	}

	/**
	 * <b>Description:</b> This is setter for WebDriver.
	 */
	public static void setDriver(WiniumDriver driver){
		winiumDriver.set(driver);
	}

	/**
	 * <b>Description:</b> This is getter for WebDriver.
	 * @return Instance of ActionLib
	 */
	public static WebDriver getDriver(){
		return CommonDriver.get();
	}

	/**
	 * @author Cigniti
	 * @return
	 */
	public static WiniumDriver getWiniumDriver(){
		return winiumDriver.get();
	}

	public static void setDriverToNull(){
		CommonDriver.set(null);
	}

	public static void setWiniumDriverToNull(){
		winiumDriver.set(null);
	}
}