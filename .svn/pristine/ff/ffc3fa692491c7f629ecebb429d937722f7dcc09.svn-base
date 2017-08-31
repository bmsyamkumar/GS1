package com.desktop.automation.accelerators;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import org.openqa.selenium.winium.WiniumDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.log4testng.Logger;

import com.amazonaws.services.cognitosync.model.Platform;
import com.google.common.base.Optional;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.relevantcodes.extentreports.ExtentTest;
import com.web.automation.accelerators.ActionsLibrary;
import com.web.automation.accelerators.TestEngineWeb;
import com.web.automation.email.SendEmail;
import com.web.automation.fileutils.ExcelReader;
import com.web.automation.fileutils.ReadCSVFile;
import com.web.automation.googledrive.GmailAPI;
import com.web.automation.googledrive.GoogleSpreadsheetAPI;
import com.web.automation.listeners.MyListener;
import com.web.automation.logs.CustomLogName;
import com.web.automation.logs.CustomLogs;
import com.web.automation.logs.DetailedLogs;
import com.web.automation.logs.ExtentLogName;
import com.web.automation.reports.ExtentHTML;
import com.web.automation.reports.ExtentManager;
import com.web.automation.testmanagement.JiraIntegration;
import com.web.automation.utilities.CommonVariables;
import com.web.automation.utilities.ConfigManager;

public class DesktopEngine extends MyListener{

	private WiniumDriver driver;
	public DesktopLibrary objCommonFunc;
	protected DateFormat dateFormat;
	protected String methodName;
	private String Rerun = "";
	private String APIReportPath = "";
	private String FailedAPIReportPath = "";
	private static long threadCount = 1;
	protected long threadId;
	protected Boolean isRunningViaDesktopXML = false;
	public static String emailAddress;
	private ExcelReader excel; 
	private String emailReport=null;
	public static String[] to;
	private static final Logger LOG = Logger.getLogger(TestEngineWeb.class);
	protected static String timestamp = "";
	public Properties config_prop;
	private ExtentTest etest;
	public String log;
	private int itr_cnt = 1;
	private Calendar startTime;
	private static String reportPath = "";
	private ExtentManager extentManager;
	@SuppressWarnings("rawtypes")
	public static Map sysInfo = new HashMap();
	public static String extentReporth;
	public static List<Map<String,String>> jiraTestCaseMapping = new ArrayList<Map<String,String>>();
	protected String jiraMappingSheet= System.getProperty("user.dir").replace("\\", "/")+ "/testdata/JiraMapping.csv";
	private String resultSheetForGoogleDrive=System.getProperty("user.dir").replace("\\", "/")+ "/testdata/.xlsx";
	private String executorFile =System.getProperty("user.dir").replace("\\", "/")+ "/Tool/Winium/Winium.Desktop.Driver.exe";
	private GmailAPI gmailApi;
	protected String rootPath=System.getProperty("user.dir").replace("\\", "/")+"/logs/Results/";

	public static synchronized ExtentTest getExtentReportTest() {
		return CommonVariables.extentTest.get();
	}

	public static synchronized void setExtentReportTest(ExtentTest etest){
		CommonVariables.extentTest.set(etest);
	}

	/**
	 * @author Cigniti
	 * @param context
	 * @throws IOException
	 */
	@BeforeSuite(alwaysRun = true, groups = {"smoke","regression"})
	public void beforeSuite(ITestContext context) throws IOException {
		try {	
			if(System.getenv("jenkins_home")!=null && System.getenv("jenkins_home").length()>1){
				String buildNumber=System.getenv("BUILD_NUMBER");
				String JobName=System.getenv("JOB_NAME");
				CommonVariables.Jenkins_BuildNumber.set(buildNumber);
				CommonVariables.Jenkins_JobName.set(JobName);
			}

			System.out.println("Executing the test suite on " + context.getSuite().getXmlSuite().getTests().size()
					+ " number of threads.");
			System.out.println("Executing the test suite on  number of threads : "
					+ context.getSuite().getXmlSuite().getThreadCount() + " number of threads.");
			timestamp = new SimpleDateFormat("yyyy_MM_dd_hh_mm_sss_a").format(new Date());

			Properties config_prop = new Properties();
			try{
				FileReader reader;
				reader = new FileReader("config.properties");
				config_prop.load(reader);
			}
			catch (IOException e) {
				System.out.println("Failed to fetch/read 'config.properties' file.");
			}

			// code to integrate with Google Drive
			if(config_prop.getProperty("isGmailIntegrationRequired").equals("yes")){
				gmailApi = new GmailAPI();
				try {
					gmailApi.authenticate();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}

			//code to get TestNG Groups
			CommonVariables.testNGGroupNames = getTestNGGroups(context).substring(1, getTestNGGroups(context).length()).replace(":", ",");

			// code to read email addresses from local
			String emailFilePath=System.getProperty("user.dir").replace("\\", "/")+"/emails.txt";
			//ActionsLibrary actionLib=new ActionsLibrary();
			List<String> emailList=readTextFile(emailFilePath);
			emailAddress=emailList.toString().replace("[", "").replace("]", "");

			// code to read email addresses from Jenkins
			if(System.getenv("jenkins_home")!=null && System.getenv("EmailAddress")!=null){
				String emailAddress = "";
				try {
					emailAddress = System.getenv("EmailAddress");
				} catch (NullPointerException e2) {
				}
				if (emailAddress != null && (!(emailAddress.isEmpty()))) {
					emailAddress = emailAddress + "," + emailAddress.trim();
				} else if (System.getProperty("EmailAddress") != null
						&& (!System.getProperty("EmailAddress").isEmpty())) {
					emailAddress = emailAddress + "," + System.getProperty("EmailAddress");
				}
			}
			if(emailAddress.contains(",")){
				to=emailAddress.split(",");
			}
			LOG.info("Debug: List of Email Addresses = " + to);

			// code to start winium server
			 Runtime runtime = Runtime.getRuntime();
			    try {
			     Process process =runtime.exec("cmd /c start "+this.executorFile);
			     process.waitFor();
			    } catch (IOException e) {
			     // TODO Auto-generated catch block
			     e.printStackTrace();
			    }

			if(config_prop.getProperty("IsJiraIntegrationRequired").equals("yes")){
				// code to read data from from csv file for jira test cases
				jiraTestCaseMapping=ReadCSVFile.getJiraCSVData(this.jiraMappingSheet);
				System.out.println("CSV sheet : "+jiraTestCaseMapping);
			}

			// code to read testNg failed xml, if exist.
			File src = null;
			File dest = null;
			try {
				src = new File(System.getProperty("user.dir").replace("\\", "/") + "/test-output/testng-failed.xml");
				BufferedReader in = new BufferedReader(
						new FileReader(System.getProperty("user.dir").replace("\\", "/") + "/logs/CapturedJSErrors/lastRunResultFolder.txt"));
				String destString;
				while ((destString = in.readLine()) != null) {
					dest = new File(destString + "/testng-failed.xml");
				}
				DesktopLibrary.staticCopyFile(src, dest);
				in.close();
			} catch (Exception e1) {
				try {
					String timestamp = new SimpleDateFormat("yyyy_MM_dd").format(new Date());
					DesktopLibrary.staticCopyFile(src, new File(System.getProperty("user.dir").replace("\\", "/")+"/logs/Failed-testng_xmls/test-failed_"+timestamp+"_"+System.nanoTime()+".xml"));
				} catch (Exception e) {e.getLocalizedMessage();
				}
			}
		}catch(Exception e){e.printStackTrace();}
	}


	/**
	 *  @author Cigniti
	 * @throws IOException 
	 */
	@SuppressWarnings("static-access")
	@AfterSuite(alwaysRun = true, groups = {"smoke","regression"})
	public void afterSuite() throws IOException {
		try {
			PrintWriter writer = new PrintWriter(System.getProperty("user.dir").replace("\\", "/")+"/logs/CapturedJSErrors/lastRunResultFolder.txt", "UTF-8");
			writer.println(extentReporth.split("M_")[0]+"M");
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// code to send an email notification
		if (this.emailReport.toLowerCase().trim().contains("true")
				|| ((System.getenv("EmailAddress") != null
				&& !System.getenv("EmailAddress").isEmpty()))) {
			String subject = " Automation Execution Results : " + CommonVariables.PlatformName.get() + " - "
					+ CommonVariables.DeviceName.get() + " - " + timestamp;
			try {
				if (Rerun.equals("true") || ConfigManager.getProperties().get("IsFailedXMLRequired").equals("true")) {
					subject = subject + "Re-run : ";
				}
			} catch (Exception e) {
			}

			subject = "Automation Results: " + CommonVariables.PlatformName.get() + " - "
					+ CommonVariables.DeviceName.get() + " - " + timestamp;
			try{
				String hostname=System.getProperty("hostname");
				String port=System.getProperty("email_port");
				String sender = ConfigManager.getProperties().getProperty("user_email");
				System.out.println(sender);
				String password = ConfigManager.getProperties().getProperty("user_password");
				System.out.println(password);
				SendEmail send = new SendEmail();
				String mailBody="Dear Recipient," + "\n\n Please find attached automation execution results.";
				send.sendEmailReport(hostname,port,sender,password, to, subject, mailBody, CommonVariables.extentReportPath.get());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		else{
			System.out.println("Unable to send an Email Report status is set as false.");
		}
		// code to kill all command prompt instances
		if(System.getProperty("os.name").toLowerCase().contains("windows") && System.getenv("jenkins_home")==null){
			String command = "cmd /c start " + "taskkill /F /IM cmd.exe";
			Runtime rt = Runtime.getRuntime();
			try {
				@SuppressWarnings("unused")
				Process pr = rt.exec(command);
				System.out.println("All command prompt instances are closed successfully.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// code for extent reporting
		extentManager.closeReport();
	}

	/**
	 * @author Cigniti
	 * @param context
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@BeforeTest(alwaysRun = true, groups = {"smoke","regression" })
	public void beforeTest(ITestContext context) throws IOException {
		String hostname = InetAddress.getLocalHost().getHostName();
		String hostAddress = InetAddress.getLocalHost().getHostAddress();
		this.threadId=Thread.currentThread().getId();
		CommonVariables.MachineHostName.set(hostname);
		CommonVariables.MachineHostAddress.set(hostAddress);
		System.out.println("Executing the test on thread id : " + threadId);
		try {
			Thread.sleep(1500 * threadCount);
			threadCount = threadCount + 1;
		} catch (InterruptedException ex) {ex.printStackTrace();}
		CommonVariables.ResultSheet.set(new HashMap<String, String>());
		CommonVariables.TestMethodDescriptions.set(new HashMap<String, String>());
		// code to set detailed logs
		CommonVariables.DetailedLogs.set(new DetailedLogs());
		// code to set config properties
		ConfigManager.setProperties();
		// code to set instance variables for detail level logging.
		CommonVariables.winiumDriver.set(null);
		CommonVariables.TestCaseName.set("");
		CommonVariables.TotalTCCount.set(new Integer(0));
		CommonVariables.PassTCCount.set(new Integer(0));
		CommonVariables.FailTCCount.set(new Integer(0));
		CommonVariables.SkipTCCount.set(new Integer(0));
		CommonVariables.TestCasessHighLevelLog.set(new ArrayList<String>());
		CommonVariables.ScenariosHighLevelLog.set(new ArrayList<String>());
		CommonVariables.LastMethodName.set("");
		CommonVariables.TestClassResult.set("PASS");

		// code to get environment from jenkins like stag, qa, dev etc.
		final String Environment;
		if (System.getenv("Environment") != null
				&& (!System.getenv("Environment").isEmpty())) {
			Environment = System.getenv("Environment");
		} else if (System.getProperty("Environment") != null
				&& (!System.getProperty("Environment").isEmpty())) {
			Environment = System.getProperty("Environment");
		} else {
			Environment = null;
		}

		// code to get user region from jenkins like US, India etc.
		final String region;
		if (System.getenv("userRegion") != null && (!System.getenv("userRegion").isEmpty())) {
			region = System.getenv("userRegion");
		} else if (System.getProperty("userRegion") != null && (!System.getProperty("userRegion").isEmpty())) {
			region = System.getProperty("userRegion");
		} else {
			region = null;
		}
		// code to get boolean status of failed.xml i.e. ReRUN=true or false
		try {
			Rerun = System.getenv("RERUN").toString().toLowerCase();
		} catch (Exception e1) {
			e1.getMessage();
		}
		// code to set and get the testNG parameter values
		try {
			if (context.getCurrentXmlTest().getTestParameters().size() > 0) {
				isRunningViaDesktopXML = true;
			}
		} catch (Exception ex) {ex.getMessage();}
		CommonVariables.ExecutionDate.set(GetCurrentTime().split(" ")[0]);
		CommonVariables.ExecutionStartTime.set(GetCurrentTime());
		try {
			if (context.getCurrentXmlTest().getTestParameters().size() > 0) {
				if (context.getCurrentXmlTest().getTestParameters().containsKey("seleniumGrid")) {
					CommonVariables.IsGridExecution
					.set(Boolean.parseBoolean(context.getCurrentXmlTest().getParameter("seleniumGrid").trim()));
				} else {
					CommonVariables.IsGridExecution
					.set(Boolean.parseBoolean(context.getCurrentXmlTest().getParameter("seleniumGrid").trim()));
				}
			} else {
				CommonVariables.IsGridExecution
				.set(Boolean.parseBoolean(ConfigManager.getProperties().getProperty("seleniumGrid").trim()));
			}
		} catch (Exception e) {
			CommonVariables.IsGridExecution.set(false);
		}
		try {
			if (context.getCurrentXmlTest().getTestParameters().size() > 0) {
				if (context.getCurrentXmlTest().getTestParameters().containsKey("localExecution")) {
					CommonVariables.IsLocalExecution
					.set(Boolean.parseBoolean(context.getCurrentXmlTest().getParameter("localExecution").trim()));
				} else {
					CommonVariables.IsLocalExecution
					.set(Boolean.parseBoolean(context.getCurrentXmlTest().getParameter("localExecution").trim()));
				}
			} else {
				CommonVariables.IsLocalExecution
				.set(Boolean.parseBoolean(ConfigManager.getProperties().getProperty("localExecution").trim()));
			}
		} catch (Exception e) {
			CommonVariables.IsLocalExecution.set(false);
		}
		try {
			if (context.getCurrentXmlTest().getTestParameters().size() > 0) {
				if (context.getCurrentXmlTest().getTestParameters().containsKey("cloudExecution")) {
					CommonVariables.IsCloudExecution
					.set(Boolean.parseBoolean(context.getCurrentXmlTest().getParameter("cloudExecution").trim()));
				} else {
					CommonVariables.IsCloudExecution
					.set(Boolean.parseBoolean(context.getCurrentXmlTest().getParameter("cloudExecution").trim()));
				}
			} else {
				CommonVariables.IsCloudExecution
				.set(Boolean.parseBoolean(ConfigManager.getProperties().getProperty("cloudExecution").trim()));
			}
		} catch (Exception e) {
			CommonVariables.IsCloudExecution.set(false);
		}

		// code to get email report status from jenkins like true or false.
		try {
			if (System.getenv("jenkins_home") != null && System.getenv("jenkins_home").length() > 1) {
				CommonVariables.emailReport.set("true");
			} else if (context.getCurrentXmlTest().getTestParameters().size() > 0) {
				if (context.getCurrentXmlTest().getTestParameters().containsKey("emailReport")) {
					CommonVariables.emailReport.set(context.getCurrentXmlTest().getParameter("emailReport").trim());
				} else {
					CommonVariables.emailReport.set(ConfigManager.getProperties().getProperty("emailReport"));
				}
			}else{
				CommonVariables.emailReport.set(ConfigManager.getProperties().getProperty("emailReport"));
			}
		} catch (Exception e) {
			CommonVariables.emailReport.set(ConfigManager.getProperties().getProperty("emailReport"));
		}
		this.emailReport=CommonVariables.emailReport.get();
		System.out.println("Email Report Status: " + CommonVariables.emailReport.get());
		System.out.println("Running on Grid: " + CommonVariables.IsGridExecution.get());
		System.out.println("Running on local: " + CommonVariables.IsLocalExecution.get());
		System.out.println("Running on cloud: " + CommonVariables.IsCloudExecution.get());
		String DeviceType = "Unknown Device";
		String platform="";

		try {
			try {
				if (Environment != null && (!Environment.isEmpty())) {
					CommonVariables.Environment.set(Environment);
				} else if (context.getCurrentXmlTest().getParameter("environment") != null
						&& (!context.getCurrentXmlTest().getParameter("environment").isEmpty())) {
					CommonVariables.Environment
					.set(context.getCurrentXmlTest().getParameter("environment"));
				} else {
					CommonVariables.Environment
					.set(ConfigManager.getProperties().getProperty("environment"));
				}
			} catch (Exception e) {
				System.out.println("Getting exception while getting environment type:" + e.getMessage());
				CommonVariables.Environment.set(ConfigManager.getProperties().getProperty("environment"));
			}
			System.out.println("Running on Environment : " + CommonVariables.Environment.get());
			try{
				if (region != null && (!region.isEmpty())) {
					CommonVariables.userRegion.set(region);
				} else if (context.getCurrentXmlTest().getParameter("userRegion") != null
						&& (!context.getCurrentXmlTest().getParameter("userRegion").isEmpty())) {
					CommonVariables.userRegion.set(context.getCurrentXmlTest().getParameter("userRegion"));
				} else {
					CommonVariables.userRegion.set(ConfigManager.getProperties().getProperty("userRegion"));
				}
			} catch (Exception e) {
				System.out.println("Getting exception while getting User Region:" + e.getMessage());
				CommonVariables.userRegion.set(ConfigManager.getProperties().getProperty("userRegion"));
			}
			System.out.println("Running for Region : " + CommonVariables.userRegion.get());

			try {
				String deviceName="";
				if (!context.getCurrentXmlTest().getParameter("deviceType").isEmpty()
						&& (!context.getCurrentXmlTest().getParameter("platform").isEmpty())) {
					deviceName=context.getCurrentXmlTest().getParameter("deviceType");
					if(deviceName.trim().equalsIgnoreCase("desktop")){
						CommonVariables.DeviceName.set(deviceName);
					}else{
						CommonVariables.DeviceName.set("desktop");
					}
					CommonVariables.PlatformName.set(context.getCurrentXmlTest().getParameter("platform"));
				} else {
					deviceName=ConfigManager.getProperties().getProperty("deviceType");
					if(deviceName.trim().equalsIgnoreCase("desktop")){
						CommonVariables.DeviceName.set(ConfigManager.getProperties().getProperty("deviceType"));
					}else{
						CommonVariables.DeviceName.set("desktop");
					}
					CommonVariables.PlatformName.set(ConfigManager.getProperties().getProperty("platform"));
				}
			} catch (Exception e) {
				System.out
				.println("Getting exception while setting platform and device type values: " + e.getMessage());
				CommonVariables.DeviceName.set("desktop");
				CommonVariables.PlatformName.set(ConfigManager.getProperties().getProperty("platform"));
			}
			DeviceType = CommonVariables.DeviceName.get();
			platform=CommonVariables.PlatformName.get();
			System.out.println("Running on Device Type : " + DeviceType);
			System.out.println("Running on Platform : " + CommonVariables.PlatformName.get());
			if (Environment != null && DeviceType != null
					&& CommonVariables.IsGridExecution.get() == false) {
				ConfigManager.getProperties().setProperty("deviceType", DeviceType);
				ConfigManager.getProperties().setProperty("platform", platform);
				ConfigManager.getProperties().setProperty("environment", Environment);
			}
			if (context.getCurrentXmlTest().getParameter("machineIP") == null) {
				CommonVariables.machineIP.set(ConfigManager.getProperties().getProperty("machineIP"));
			} else {
				CommonVariables.machineIP.set(context.getCurrentXmlTest().getParameter("machineIP"));
			}
		} catch (Exception e) {
			System.out.println("Getting exception in Before Test block." + e.getMessage());
			e.printStackTrace();
		}

		// code for extent reporting
		sysInfo.put("Machine IP Address", CommonVariables.MachineHostAddress.get());
		sysInfo.put("Environment", CommonVariables.Environment.get());
		sysInfo.put("Platform", platform);
		sysInfo.put("Region", CommonVariables.userRegion.get());
		sysInfo.put("Time Zone", ActionsLibrary.getCurrentTimeZone());
		sysInfo.put("Device", DeviceType);
		sysInfo.put("Execution Date and Time", CommonVariables.ExecutionStartTime.get());

		dateFormat = new SimpleDateFormat("yyyy_MM_dd HH:mm:ss");
		startTime = Calendar.getInstance();
		// code to set report path
		String Report_Root_Path = rootPath + timestamp;
		reportPath=Report_Root_Path;
		CommonVariables.DetailedLogs.get().CreateFolder(Report_Root_Path);
		Report_Root_Path =rootPath + timestamp + "/" + DeviceType + "_" + Thread.currentThread().getId();
		CommonVariables.DetailedLogs.get().CreateFolder(Report_Root_Path);
		CommonVariables.RootResultFolderPath.set(Report_Root_Path);

		CommonVariables.CurrentGlobalLog.set(CommonVariables.DetailedLogs.get().StartLogs("Global_Log", Report_Root_Path));
		CommonVariables.CurrentGlobalLog.get().info("***** Staring the Suite *****");

		File highlevellogfile = new File(Report_Root_Path + "/" + "HighLevelLog" + ".log");
		FileWriter highlevellogfw = new FileWriter(highlevellogfile.getAbsoluteFile());
		CommonVariables.HighLevelLog.set(new BufferedWriter(highlevellogfw));

		// Jira Integration
		if(ConfigManager.getProperties().getProperty("IsJiraIntegrationRequired").equals("yes")){
			CommonVariables.JI.set(new JiraIntegration());
			CommonVariables.jiramap.set(new HashMap<String, String>());
			CommonVariables.JI.get().LoadJiraMappingSheet(jiraMappingSheet);
		}

		// code to generate intermediate results during automation execution
		try {
			createIntermediateTestResult("/" + DeviceType + "_" + Thread.currentThread().getId()+"/intermediateResults-"+Thread.currentThread().getId(), ",");
		} catch (IOException e) {
			System.out.println("ERROR---> COULD NOT CREATE INTERMEDIATE RESULT FILE");
			e.printStackTrace();
		}
	}

	/**
	 * @author Cigniti
	 * @throws IOException 
	 */
	@AfterTest(alwaysRun = true, groups = {"smoke","regression"})
	public void afterTest() throws IOException {
		if (CommonVariables.winiumDriver.get() != null) {
			try {
				CommonVariables.winiumDriver.get().close();
				CommonVariables.winiumDriver.get().quit();
			} catch (Exception e) {

			}
		}
		CommonVariables.ExecutionEndTime.set(GetCurrentTime());
		CommonVariables.CurrentGlobalLog.get().info("Stopping the Suite");
		try {
			CommonVariables.HighLevelLog.get().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		CommonVariables.ExecutionEndTime.set(GetCurrentTime());

		// code to replace extent report with logos
		ExtentHTML.createExtentHTML();

		// code to generate report through command prompt 
		try {
			String osname = System.getProperty("os.name");
			if(System.getenv("jenkins_home")==null){
				if (osname.toLowerCase().contains("windows")) {
					String command = "cmd /c start " + System.getProperty("user.dir") + "/LaunchReport.bat " + "\""
							+ CommonVariables.extentReportPath.get();
					Runtime rt = Runtime.getRuntime();
					@SuppressWarnings("unused")
					Process pr = rt.exec(command);
				} else if(osname.toLowerCase().contains("mac")) {
					String[] mac_command = { "open", "/Applications/Google Chrome.app",
							CommonVariables.extentReportPath.get() };
					Runtime rt = Runtime.getRuntime();
					@SuppressWarnings("unused")
					Process pr = rt.exec(mac_command);
				}
				else{
					String[] linux_command = { "google-chrome", "/usr/bin/X11/google-chrome",
							CommonVariables.extentReportPath.get() };
					Runtime rt = Runtime.getRuntime();
					@SuppressWarnings("unused")
					Process pr = rt.exec(linux_command);
				}
			}
			System.out.println("***** HTML report Successfully Generated ***** !!!!");
		} catch (IOException e) {
			System.out.println("Failed to execute 'Launch Report' : " + e.getStackTrace());
		}
		if(ConfigManager.getProperties().getProperty("IsJiraIntegrationRequired").equals("yes")){
			// code for jira integration
			try{
				FileWriter fw = new FileWriter(CommonVariables.RootResultFolderPath.get() + "/Results.csv");
				PrintWriter out = new PrintWriter(fw);
				FileWriter fwJiraStatus = new FileWriter(CommonVariables.RootResultFolderPath.get() + "/JiraTestCaseStatus.csv");
				PrintWriter pwJiraStatus = new PrintWriter(fwJiraStatus);
				Map<String,String[]> jiraStatusStore = new HashMap<String,String[]>();
				Iterator it = CommonVariables.ResultSheet.get().entrySet().iterator();
				out.println("TestcaseName,ItrationCount,JiraTCID,Status,DeviceName,Method Description,JiraTestCaseId,Owner,TestCaseType");
				while (it.hasNext()) {
					Map.Entry pairs = (Map.Entry) it.next();
					@SuppressWarnings("unused")
					String key = pairs.getKey().toString();
					String val = pairs.getValue().toString();
					out.println(val);
					try {
						String[] abc = (String[]) pairs.getValue().toString().split(",");
						if (abc != null && abc.length > 6 && !abc[6].isEmpty()) {
							String[] allAssociatedId = abc[6].trim().toUpperCase().split("\\|\\|");
							for (String jiratestcaseid : allAssociatedId) {
								if (jiraStatusStore.containsKey(jiratestcaseid)) {
									String[] value = jiraStatusStore.get("JiraTCID");
									if (abc[3].trim().toLowerCase().contains("fail")) {
										value[0] = "FAIL";
									}
									if (!value[1].trim().toLowerCase().contains(abc[0].toLowerCase())) {
										value[1] = value[1] + "||" + abc[0];
									}
									if (abc.length > 7) {
										if (!value[2].trim().toLowerCase().contains(abc[7].toLowerCase())) {
											value[2] = value[2] + "||" + abc[7];
										}
									}
									jiraStatusStore.put(jiratestcaseid, value);
								} else {
									String[] value = null;
									if (abc.length > 7) {
										value = new String[] { abc[3], abc[0], abc[7] };
									} else {
										value = new String[] { abc[3], abc[0], "" };
									}
									jiraStatusStore.put(jiratestcaseid, value);
								}
							}
						}
					} catch (Exception ex) {
					}
					it.remove();
				}
				out.flush();
				out.close();
				fw.close();
				Set<Entry<String, String[]>> jiraEntrySet = jiraStatusStore.entrySet();
				for (Entry<String, String[]> individualEntry : jiraEntrySet) {
					String jiraDetail = individualEntry.getKey();
					for (String val : individualEntry.getValue()) {
						jiraDetail = jiraDetail + "," + val;
					}
					jiraDetail = jiraDetail.replaceAll("\\s", ",");
					pwJiraStatus.println(jiraDetail);
				}
				pwJiraStatus.flush();
				pwJiraStatus.close();
				fwJiraStatus.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (isRunningViaDesktopXML
						&& ConfigManager.getProperties().getProperty("IsResultUploadInDrive").equalsIgnoreCase("true")) {
					ReadCSVFile.createFileContainsFailedAndSkipTC(
							CommonVariables.RootResultFolderPath.get() + "/Results.csv", Optional.<String> absent());
					@SuppressWarnings("static-access")
					List<Map<String, String>> allresults = this.jiraTestCaseMapping;
					// Create Google SpreadSheet API Service
					GoogleSpreadsheetAPI googleApiObj = new GoogleSpreadsheetAPI();
					WorksheetEntry worksheetEntry = null;
					String month_Year = new SimpleDateFormat("MMM").format(Calendar.getInstance().getTime()) + "_"
							+ new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime());
					if (CommonVariables.Environment.get().equalsIgnoreCase("prod")) {
						worksheetEntry = googleApiObj.getYourWorkSheetEntry(
								Optional.of(CommonVariables.Environment.get() + "_"
										+ CommonVariables.DeviceName.get() + "_" + timestamp),
								Optional.of("Prod_" + month_Year), true, false,
								resultSheetForGoogleDrive);
					} else {
						worksheetEntry = googleApiObj.getYourWorkSheetEntry(
								Optional.of(CommonVariables.Environment.get() + "_"
										+ CommonVariables.DeviceName.get() + "_" + timestamp),
								Optional.of("NonProd_" + month_Year), true, false,resultSheetForGoogleDrive);
					}
					// Insert all the Failed and Skipped Test Cases.
					// Insert all the Failed and Skipped Test Cases.
					if (!googleApiObj.insertDataInWorkSheet(worksheetEntry, allresults)) {
						System.out.println("Could not upload file");
						System.out.println("System will now create a Results.csv file.");
						String filePath = CommonVariables.RootResultFolderPath.get() + "/Results.csv";
						FileWriter fw = new FileWriter(filePath);
						PrintWriter pw = new PrintWriter(fw);
						for (Map.Entry<String, String> resultEntry : allresults.get(0).entrySet()) {
							pw.append(resultEntry.getKey());
							pw.append(",");
						}
						pw.flush();
						pw.close();
						fw.close();
						for (Map<String, String> result : allresults) {
							fw = new FileWriter(filePath, true);
							pw = new PrintWriter(fw);
							pw.append('\n');
							for (Map.Entry<String, String> resultEntry : result.entrySet()) {
								pw.append(resultEntry.getValue());
								pw.append(",");
							}
							pw.flush();
							pw.close();
							fw.close();
						}
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}else{
			System.out.println("***** @After Test: Jira Integration is marked as 'no' in config.properties file. *****");
		}
	}

	/**
	 * @author Cigniti
	 * @throws IOException
	 */
	@BeforeClass(inheritGroups=true, alwaysRun = true, groups = {"smoke","regression"})
	public void beforeClass() throws IOException {
		try {
			// code to set properties file
			if(!ConfigManager.ArePropertiesSet.get()){
				ConfigManager.setProperties();
			}
			// code to set results
			CommonVariables.TestClassResult.set("PASS");
			String completeclassName = this.getClass().getName();
			System.out.println("Running the Test Class: " + completeclassName);
			String[] sArr = completeclassName.split("\\.");
			int last_array_item_index = sArr.length - 1;
			String className=sArr[last_array_item_index];
			String classLogName = className + "_" + Thread.currentThread().getId();
			CommonVariables.TestClassName.set(className);

			String Scenario_Report_Folder = CommonVariables.RootResultFolderPath.get() + "/" + CommonVariables.TestClassName.get();
			CommonVariables.DetailedLogs.get().CreateFolder(Scenario_Report_Folder);
			CommonVariables.ScenarioResultFolderPath.set(Scenario_Report_Folder);

			String TestCases_Report_Folder = CommonVariables.ScenarioResultFolderPath.get() + "/" + "TestCases";
			CommonVariables.DetailedLogs.get().CreateFolder(TestCases_Report_Folder);

			String testCases_ScreenShot_Folder = CommonVariables.ScenarioResultFolderPath.get() + "/" + "Screenshots";
			CommonVariables.DetailedLogs.get().CreateFolder(testCases_ScreenShot_Folder);

			// set screenshot path
			String ScreenShotPath = testCases_ScreenShot_Folder+ "/" + CommonVariables.TestClassName.get()+"_" + new SimpleDateFormat("yyyy_MM_dd_hh_mm_sss_a").format(new Date()) + ".jpg";
			CommonVariables.screenShotPath.set(ScreenShotPath);
			CommonVariables.TestClassLog.set(CommonVariables.DetailedLogs.get().StartLogs(classLogName, Scenario_Report_Folder));

			// set object of Desktop Action Library Class
			CommonVariables.setDesktopActionLib();
			objCommonFunc = CommonVariables.getDesktopActionLib();
			try {
				if (!ConfigManager.ArePropertiesSet.get()) {
					ConfigManager.setProperties();
				}
			} catch (NullPointerException e) {
				System.out.println(
						"Failed to load and update ConfigManager.getProperties() in @BeforeClass method under BaseTestCase class.");
			}
			CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "info", "hello");
			CustomLogs.addToLog(CustomLogName.CurrentTestClassLog, "info", "Staring the class");
			CommonVariables.TestClassLog.get().debug("In Before Class method inside TestEngineWeb.");
			CommonVariables.TestCaseResult.set("");
			CommonVariables.winiumDriver.set(null);
		} catch (Exception e) {
			CommonVariables.winiumDriver.set(null);
			System.out.println("Getting exception in @BeforeClass block of DesktopTestEngineWeb class. Exception info: \n"
					+ e.getMessage());
		}
		// code to set driver instance and open an application before every test class
		objCommonFunc.instantiateDriver();
	}

	/**
	 * @author Cigniti
	 */
	@AfterClass(alwaysRun = true, groups = {"smoke","regression"})
	public void afterClass() {
		try {
			CustomLogs.addToLog(CustomLogName.CurrentTestClassLog, "info", "Stopping the class");
			if (CommonVariables.TestClassResult.get().equalsIgnoreCase("PASS")) {
				CommonVariables.ScenariosHighLevelLog.get().add(CommonVariables.TestClassName.get() + ": PASS");
			} else {
				CommonVariables.ScenariosHighLevelLog.get().add(CommonVariables.TestClassName.get() + ": FAIL");
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
			}
			if (CommonVariables.getWiniumDriver() == null) {
			} else {
				CommonVariables.getDesktopActionLib().tearDown();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(CommonVariables.TestCasessHighLevelLog.get().toString());
		CommonVariables.TestCasessHighLevelLog.get().clear();
	}

	/**
	 * @author Cigniti
	 * @param method
	 * @throws IOException
	 */
	@SuppressWarnings("static-access")
	@BeforeMethod(alwaysRun = true, groups = { "smoke","regression" })
	public void beforeMethod(Method method) throws IOException {
		this.methodName = method.getName();
		CommonVariables.TestMethodName.set(this.methodName);
		if(ConfigManager.getProperties().getProperty("IsJiraIntegrationRequired").equals("yes")){
			//JiraIntegration.JiraStatus(jiraTestCaseMapping,methodName); // this code to reset the jira status
		}
		String testName = method.getName();
		String testClassName = CommonVariables.TestClassName.get();
		String testcaseLogName = testClassName + "-" + testName + "_" + Thread.currentThread().getId();

		// code to generate logs before each test method
		if (CommonVariables.TestCaseName.get().equals(testName)) {
			if (CommonVariables.TestCaseLog.get() != null) {
				CommonVariables.TestCase_Data_Iterator.set(CommonVariables.TestCase_Data_Iterator.get() + 1);
				CommonVariables.TestCaseLog.get().info("***** Dataprovider Iteration No: "+ CommonVariables.TestCase_Data_Iterator.get()+ " : *****");
			} else {
				CommonVariables.DataProviderIterator.set(1);
				CommonVariables.TestCaseName.set(testName);
				CommonVariables.TestCase_Data_Iterator.set(CommonVariables.TestCase_Data_Iterator.get() + 1);
				CommonVariables.TestCaseLog.set(CommonVariables.DetailedLogs.get().StartLogs(testcaseLogName,
						CommonVariables.ScenarioResultFolderPath.get() + "/" + "TestCases"));
				CommonVariables.TestCaseLog.get().info("***** Dataprovider Iteration No: "+ CommonVariables.TestCase_Data_Iterator.get()+ " : *****");
			}
		} else {
			CommonVariables.TestCaseName.set(testName);
			CommonVariables.TestCase_Data_Iterator.set(1);
			CommonVariables.TestCaseLog.set(CommonVariables.DetailedLogs.get().StartLogs(testcaseLogName,CommonVariables.ScenarioResultFolderPath.get() + "/" + "TestCases"));
			CommonVariables.TestCaseLog.get().info("Testcase executed on the Node IP: "+ CommonVariables.machineIP.get());
			CommonVariables.TestCaseLog.get().info("***** Dataprovider Iteration No: "+ CommonVariables.TestCase_Data_Iterator.get() + " : *****");
		}
		CommonVariables.TCStartTime.set(GetCurrentTime());

		// code to get the Method Description
		String MethodDescription = "";
		java.lang.annotation.Annotation[] annotations = method.getAnnotations();
		for (java.lang.annotation.Annotation annotation : annotations) {
			if (annotation instanceof org.testng.annotations.Test) {
				org.testng.annotations.Test myAnnotation = (org.testng.annotations.Test) annotation;
				MethodDescription = myAnnotation.description().toString().replace(",", ";");
				System.out.println("Method Description : " + MethodDescription);
			}
		}
		// code to set driver instance and open application before each test method
		//objCommonFunc.instantiateDriver();

		// code for extent reporting
		extentManager=new ExtentManager();
		extentManager.getTest(method.getName(),"Description : "+MethodDescription);		
		etest = ExtentManager.getTest();
		etest.assignCategory("Groups : "+CommonVariables.testNGGroupNames.toString());
		etest.assignAuthor("Developer : Cigniti Automation Team");
		etest.log(ExtentLogName.INFO,"Info", "Executing Test Case : ***** "+this.methodName+" *****");
		CommonVariables.extentTest.set(etest);
		this.extentReporth=CommonVariables.extentReportPath.get();
	}

	/**
	 * @author Cigniti
	 * @param method
	 * @param result
	 * @param context
	 * @throws IOException 
	 */
	@SuppressWarnings("unused")
	@AfterMethod(alwaysRun = true, groups = {"smoke","regression"})
	public void afterMethod(Method method, ITestResult result) throws IOException {

		String MethodDescription = "";
		java.lang.annotation.Annotation[] annotations = method.getAnnotations();
		for (java.lang.annotation.Annotation annotation : annotations) {
			if (annotation instanceof org.testng.annotations.Test) {
				org.testng.annotations.Test myAnnotation = (org.testng.annotations.Test) annotation;
				MethodDescription = myAnnotation.description().toString().replace(",", ";");
				System.out.println("********* Method Description : "+MethodDescription+" *********");
			}
		}

		if(ConfigManager.getProperties().getProperty("IsJiraIntegrationRequired").equals("yes")){
			// code to update status in Jira tracker
			/*try {
				JiraIntegration.updateStatusInJiraTracker(JiraIntegration.getJiraStatus());
			} catch (IllegalArgumentException e1) {
				e1.printStackTrace();
			} catch (Exception e2) {
				e2.printStackTrace();
			}*/}

		String testName = method.getName();
		Integer testResult=result.getStatus();

		// code for update intermediate results
		updateIntermediateTestResult("/" + CommonVariables.DeviceName.get() + "_" + Thread.currentThread().getId()+"/intermediateResults-"+Thread.currentThread().getId(), ",",testName, testResult);

		CommonVariables.TestCaseName.set(testName);

		// code to get the list of data providers iteration.
		String DataParameters = "";
		try {
			String[] TestCasePArametersArray = (String[]) result.getParameters()[0];
			DataParameters = Arrays.toString(TestCasePArametersArray);
		} catch (java.lang.ClassCastException e) {
			try {
				Object[] arrDataObj = (Object[]) result.getParameters()[0];
				for (Object obj : arrDataObj) {
					DataParameters = DataParameters + obj.toString() + "||";
				}
			} catch (java.lang.ClassCastException e1) {
				DataParameters = Arrays.toString(result.getParameters());
			}
		} catch (Exception e) {
			DataParameters = Arrays.toString(result.getParameters());
		}
		if (DataParameters.equals("[]"))
			DataParameters = "";
		CommonVariables.TotalTCCount.set(CommonVariables.TotalTCCount.get() + 1);
		CommonVariables.TCEndTime.set(GetCurrentTime());
		String completeclassName = this.getClass().getName();
		String testcasename = CommonVariables.TestCaseName.get();
		String CompleteTCName = completeclassName + "." + testcasename;
		String testCases_ScreenShot_Folder = CommonVariables.ScenarioResultFolderPath.get() + "/" + "ScreenShots";

		// code to get the test method execution status
		String TestCaseResult = "";
		if (result.getStatus() == ITestResult.FAILURE) {
			TestCaseResult = "FAIL";
			CommonVariables.TestClassResult.set("FAIL");
			CommonVariables.FailTCCount.set(CommonVariables.FailTCCount.get() + 1);
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			TestCaseResult = "PASS";
			CommonVariables.TestClassResult.set("PASS");
			CommonVariables.PassTCCount.set(CommonVariables.PassTCCount.get() + 1);
		} else if (result.getStatus() == ITestResult.SKIP) {
			TestCaseResult = "SKIP";
			CommonVariables.TestClassResult.set("SKIP");
			CommonVariables.SkipTCCount.set(CommonVariables.SkipTCCount.get() + 1);
		} else {
			TestCaseResult = "UNKNOWN";
		}
		String TestDescriptionKey = CommonVariables.TestClassName.get() + "-"+ CommonVariables.TestCaseName.get();

		if(ConfigManager.getProperties().getProperty("IsJiraIntegrationRequired").equals("yes")){
			String jiraTestCaseIDs = CommonVariables.jiramap.get().get(CompleteTCName);}

		if (CommonVariables.LastMethodName.get().equals(testName)) {
			itr_cnt++;
		} else {
			itr_cnt = 1;
		}
		String NewCompleteTCName = CompleteTCName + ">Itr" + itr_cnt;

		if(ConfigManager.getProperties().getProperty("IsJiraIntegrationRequired").equals("yes")){
			Map<String,String> associateJiraIdInfo=null;
			// code to write data in JiraTestCaseStatus.csv file
			associateJiraIdInfo = CommonVariables.JI.get().associatedJiraIdDetail(jiraTestCaseMapping,testName,CommonVariables.TestClassName.get());
			// code to put data in Results.csv sheet.
			CommonVariables.ResultSheet.get().put(NewCompleteTCName,
					CompleteTCName + "," + itr_cnt + "," + "," + TestCaseResult + ","
							+ CommonVariables.PlatformName.get() + "-" + CommonVariables.DeviceName.get() + ","
							+ MethodDescription + "," + associateJiraIdInfo.get("JiraTCID") + ","
							+ associateJiraIdInfo.get("Owner") + ","
							+ associateJiraIdInfo.get("TestCaseType"));
			CommonVariables.TestMethodDescriptions.get().put(TestDescriptionKey, MethodDescription);
			CommonVariables.TestCaseResult.set(TestCaseResult);
			CommonVariables.TestCasessHighLevelLog.get().add(CommonVariables.TestCaseName.get() + ":" + TestCaseResult + ":" + DataParameters);
			CommonVariables.LastMethodName.set(testName);
			try {
				if (associateJiraIdInfo != null) {
					CommonVariables.HighLevelLog.get().write(CommonVariables.TestClassName.get() + "\t"
							+ CommonVariables.TestCaseName.get() + "\t" + TestCaseResult + "\t" + DataParameters
							+ "\t" + CommonVariables.TCStartTime.get() + "\t" + CommonVariables.TCEndTime.get() + "\t"
							+ associateJiraIdInfo.get("JiraTCID") + "\t"
							+ associateJiraIdInfo.get("Owner") + "\t"
							+ associateJiraIdInfo.get("TestCaseType") + "\r\n");
				} else
					CommonVariables.HighLevelLog.get()
					.write(CommonVariables.TestClassName.get() + "\t"
							+ CommonVariables.TestCaseName.get() + "\t" + TestCaseResult + "\t"
							+ DataParameters + "\t" + CommonVariables.TCStartTime.get() + "\t"
							+ CommonVariables.TCEndTime.get() + "\t" + CommonVariables.TestMethodDescriptions.get().get(TestDescriptionKey) + "\t" + "-" + "\t" + "-" + "\r\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			System.out.println("***** @After Method: Jira Integration is marked as 'no' in config.properties file. *****");
		}
		// code for extent reporting
		ExtentManager.closeTest(testName);
	}

	public Optional<Long> timeout(Integer time) {
		return Optional.of(Long.parseLong(String.valueOf(time)));
	}

	public String GetCurrentTime() {
		DateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss a");
		String currenttime = df.format(Calendar.getInstance().getTime());
		return currenttime;
	}

	/**
	 * @author Cigniti
	 * @param fileName
	 * @param separator
	 * @throws IOException
	 */
	public void createIntermediateTestResult(String fileName, String separator) throws IOException {
		String filePath = reportPath + fileName + ".csv";
		FileWriter fw = new FileWriter(filePath);
		PrintWriter pw = new PrintWriter(fw);
		pw.print("MethodName");
		pw.print(separator);
		pw.print("TestResult");
		pw.flush();
		pw.close();
		fw.close();
	}

	/**
	 * @author Cigniti
	 * @param fileName
	 * @param separator
	 * @param methodName
	 * @param testResult
	 */
	public void updateIntermediateTestResult(String fileName, String separator, String methodName, Integer testResult){
		try {
			String filePath=reportPath+fileName+".csv";
			FileWriter fw = new FileWriter(filePath, true);
			PrintWriter pw = new PrintWriter(fw);
			pw.println();
			pw.append(methodName);
			pw.append(separator);
			if(testResult==1)
			{
				pw.append("PASS");
			}
			else if(testResult==2)
			{
				pw.append("FAIL");
			}
			else if(testResult==3)
			{
				pw.append("SKIP");
			}
			else if(testResult==4)
			{
				pw.append("SUCCESS_PERCENTAGE_FAILURE");
			}
			else if(testResult==16)
			{
				pw.append("STARTED");
			}
			else{
				pw.append("UNKNOWN");
			}
			pw.flush();
			pw.close();
			fw.close();
		} catch (IOException e) {
		}
		catch (Exception e) {
		}
	}

	/**
	 * @author Cigniti
	 * @param context
	 * @return
	 */
	public String getTestNGGroups(ITestContext context) {
		String sArrrGroups[] = context.getIncludedGroups();
		String groupName = "";
		int groupsCount = sArrrGroups.length;
		try {
			for (int i = 0; i < groupsCount; i++) {
				String sArrtemp[] = sArrrGroups[i].toString().split("\\.");
				for (int j = 0; j < sArrtemp.length; j++) {
					String name = sArrtemp[j].toString().trim().toLowerCase();
					if (name.equals("smoke")) {
						if (!groupName.contains("Smoke"))
							groupName = groupName + ":" + "Smoke";
					} else if (name.equals("regression")) {
						if (!groupName.contains("Regression"))
							groupName = groupName + ":" + "Regression";
					} 
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		groupName = ActionsLibrary.ChangeStringFirstCharToCapital(groupName);
		return groupName;
	}

	/**
	 * @author Cigniti
	 * readTextFile
	 * @param filePath
	 */
	public List<String> readTextFile(String filePath){
		List<String> list = new ArrayList<String>();
		String line = null;
		try {
			FileReader reader = new FileReader(filePath);
			BufferedReader bufferedReader = new BufferedReader(reader);

			while ((line = bufferedReader.readLine()) != null) {
				list.add(line);
			}
			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @author Cigniti
	 * @param location
	 */
	public void CreateFolder(String location){
		new File(location).mkdir();
	}
}