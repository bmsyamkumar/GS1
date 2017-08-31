
package com.mobile.automation.accelerators;

import io.appium.java_client.MobileBy;
import io.appium.java_client.SwipeElementDirection;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.SessionNotFoundException;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.github.genium_framework.appium.support.server.AppiumServer;
import com.github.genium_framework.server.ServerArguments;
import com.web.automation.logs.ExtentLogs;
import com.web.automation.utilities.CommonVariables;
import com.web.automation.utilities.ConfigManager;

/**
 * @author Cigniti
 */
public class MobileActionLibrary {
	public AndroidDriver driver;
	private String iOSAppPath;
	public boolean DriverNoResetFlag;
	private WebDriverWait wait;
	private DesiredCapabilities objCapabilities;
	private ExtentLogs extentLogs=new ExtentLogs();
	/**
	 * These objects are made non-static. The objects of this class are
	 * re-created at some point of time.
	 */
	public FileWriter fw;
	public PrintWriter pw;

	// Constructor with WebDriver argument
	public MobileActionLibrary(AndroidDriver appiumDriver) {
		this.driver = appiumDriver;
		initPropertiesFile();
	}

	/**
	 * @author Cigniti
	 * @description: Intialize config properties
	 */
	private void initPropertiesFile(){
		try {
			if(!ConfigManager.ArePropertiesSet.get()){
				ConfigManager.getProperties();
				ConfigManager.UpdateProperties();
			}
		} 
		catch (Exception e) {
			e.getLocalizedMessage();
		}
	}

	public void WebdriverWaitForPage(String time) {
		driver.manage().timeouts()
		.implicitlyWait(Long.parseLong(time), TimeUnit.SECONDS);
	}

	public void WebdriverWaitForPage() {
		WebdriverWaitForPage("180");
	}

	/**
	 * @author Cigniti
	 * @param appiumServerNode
	 * @param appiumServerPort
	 * @param bootStrapPort
	 * @param udid
	 * @param chromedriverPort
	 * @throws Exception
	 */
	public void startAppiumServer() throws Exception {
		ServerArguments serverArguments = new ServerArguments();
		serverArguments.setArgument("--address", ConfigManager.getProperties().getProperty("appiumServerIP"));
		serverArguments.setArgument("--port", ConfigManager.getProperties().getProperty("appiumPort"));
		serverArguments.setArgument("--log", String.format("C:/%s.txt", ConfigManager.getProperties().getProperty("androidDeviceId")));
		serverArguments.setArgument("--chromedriver-port",  ConfigManager.getProperties().getProperty("chromedriverPort"));
		serverArguments.setArgument("--bootstrap-port", ConfigManager.getProperties().getProperty("bootStrapPort"));
		serverArguments.setArgument("--no-reset", true);
		serverArguments.setArgument("--local-timezone", true);
		AppiumServer _appiumServer = null;
		try
		{	synchronized(MobileActionLibrary.class)
			{
			_appiumServer = new AppiumServer(new File(ConfigManager.getProperties().getProperty("appiumServerPath")), serverArguments);
			_appiumServer.startServer();
			Thread.sleep(2000);
			}
		extentLogs.info("Appium Server", "***** Appium Server strated successfully. *****");
		}
		catch(Exception e)
		{
			System.out.println("AppiumServer start Exception encounterd - " + e.getMessage());
			extentLogs.error("Appium Server", "***** Error to start ppium Server strated successfully. *****");
			throw e;
		}
	}

	/**
	 * @author Cigniti
	 * @param appName
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public AndroidDriver StartAppiumDriver(String appName) {
		try {
			System.out.println("AppName :- "+appName);
			iOSAppPath = appName;
			ConfigManager.setProperties();
			File appDir = new File(System.getProperty("user.dir")+ "\\Tool");
			File androidAppPath = new File(appDir, iOSAppPath.trim());
			objCapabilities = new DesiredCapabilities();
			objCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,MobilePlatform.ANDROID);
			objCapabilities.setCapability("app", androidAppPath);
			objCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME,ConfigManager.getProperties().getProperty("androidDeviceName"));
			objCapabilities.setCapability("automationName", ConfigManager.getProperties().getProperty("androidAutomationName"));
			objCapabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "android");
			objCapabilities.setCapability("autoLaunch", true);
			objCapabilities.setCapability("noReset", DriverNoResetFlag);
			objCapabilities.setCapability("deviceReadyTimeout", "300"); 
			//			objCapabilities.setCapability("androidDeviceReadyTimeout","420");
			objCapabilities.setCapability("avdLaunchTimeout", "300000"); 
			objCapabilities.setCapability("newCommandTimeout", "600000");
			try {
				//  http://0.0.0.0:4723/wd/hub
				//driver = new AndroidDriver(new URL(ConfigManager.getProperties().getProperty("gridHubIP") + ":4444/wd/hub"), objCapabilities);
				driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), objCapabilities);
				Thread.sleep(1500);
			} catch (org.openqa.selenium.SessionNotCreatedException ex) {
				try {
					Thread.sleep(3000);
					driver = new AndroidDriver(new URL(ConfigManager.getProperties().getProperty("gridHubIP") + ":4444/wd/hub"), objCapabilities);
					Thread.sleep(1500);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (UnreachableBrowserException e3) {
				try {
					Thread.sleep(2000);
					driver = new AndroidDriver(new URL(ConfigManager.getProperties().getProperty("gridHubIP") + ":4444/wd/hub"),
							objCapabilities);
					Thread.sleep(4000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				Assert.fail("***** Failed to start Appium Driver. Skipping further steps. ******");
			}
			return driver;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @author Cigniti
	 * @param appPath
	 * @param appPackage
	 * @param appActivityStart
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public AndroidDriver switchToAnotherApp(String appPath, String appPackage, String appActivityStart){
		try {
			objCapabilities = new DesiredCapabilities();
			if(appPath!=null && !appPath.isEmpty())
				objCapabilities.setCapability("app", appPath);
			objCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,MobilePlatform.ANDROID);
			objCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME,ConfigManager.getProperties().getProperty("androidDeviceName")); // Android Emulator    4df1ad83692c7fef
			objCapabilities.setCapability("automationName",  ConfigManager.getProperties().getProperty("androidAutomationName"));
			objCapabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "android");
			objCapabilities.setCapability("autoLaunch", true);
			objCapabilities.setCapability("deviceReadyTimeout", "300"); 
			objCapabilities.setCapability("androidDeviceReadyTimeout","420");
			objCapabilities.setCapability("avdLaunchTimeout", "300000"); 
			objCapabilities.setCapability("newCommandTimeout", "600000");
			if(appPackage!=null && !appPackage.isEmpty())
				objCapabilities.setCapability("appPackage",appPackage);
			if(appActivityStart!=null && !appActivityStart.isEmpty())
				objCapabilities.setCapability("appActivity", appActivityStart);
			driver = new AndroidDriver(new URL("http://"+ConfigManager.getProperties().getProperty("appiumServerIP") +":"+ConfigManager.getProperties().getProperty("appiumPort")+"/wd/hub"), objCapabilities);
			return driver;
		} catch (Exception e) {
			return driver;
		}
	}

	/**
	 * @author Cigniti
	 */
	public void ShutDownDriver() {
		if (driver != null) {
			try {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
				}
				driver.closeApp();
				driver.quit();
			} catch (WebDriverException e) {
				try {
					Thread.sleep(2500);
				} catch (InterruptedException e1) {
				}
			}
		}
	}

	/**
	 * @author Cigniti
	 * @param mobBy
	 * @param timeoutInSeconds
	 * @return
	 */
	public WebElement FindElement(By mobBy, int timeoutInSeconds) {
		WebElement webElement;
		WebDriverWait wait;
		try {
			wait = new WebDriverWait(driver, timeoutInSeconds);
			webElement = wait.until(ExpectedConditions
					.presenceOfElementLocated(mobBy));
			try {
				if (webElement != null || CommonVariables.TestCaseLog.get() != null) {
					extentLogs.pass("Find Element", "Successfully find an element on '" + driver.getTitle() + "' page.");
					CommonVariables.TestCaseLog.get().info("Successfully find '" + mobBy + "' element on '" + driver.getTitle() + "' page");
				} else {
					extentLogs.fail("Find Element", "Element not found on '" + driver.getTitle() + "' page.");
					CommonVariables.TestClassLog.get().info("Successfully find '" + mobBy + "' element on '" + driver.getTitle() + "' page");
				}
			} catch (NullPointerException e) {
				extentLogs.error("Find Element", "Element not found on '" + driver.getTitle() + "' page due to exception - "+e.getLocalizedMessage());
				throw new NullPointerException();
			} 
			catch (org.openqa.selenium.ElementNotVisibleException e) {
				if (CommonVariables.TestCaseLog.get() != null) {
					CommonVariables.TestCaseLog.get()
					.info("Element (" + mobBy + ") is not visible on '" + driver.getTitle() + "' page");
				} else {
					CommonVariables.TestClassLog.get()
					.info("Element (" + mobBy + ") is not visible on '" + driver.getTitle() + "' page");
				}
				extentLogs.error("Find Element", "Element not visible on '" + driver.getTitle() + "' page due to exception - "+e.getLocalizedMessage());
				throw new ElementNotVisibleException("Element not visible.");
			} 
			catch (org.openqa.selenium.WebDriverException e) {
				if (CommonVariables.TestCaseLog.get() != null) {
					CommonVariables.TestCaseLog.get()
					.info("Failed to move Element (" + mobBy + ") on the running driver type.");
				} else {
					CommonVariables.TestClassLog.get()
					.info("Failed to move Element (" + mobBy + ") on the running driver type.");
				}
				extentLogs.error("Find Element", "Failed to move to an Element due to exception - "+e.getLocalizedMessage());
				throw new WebDriverException();
			}
			return webElement;
		} 
		catch(Exception ex){
			if(ex.getCause().toString().contains("SessionNotFoundException")){
				throw new SessionNotFoundException();
			}
			else if(ex.getCause().toString().contains("UnhandledAlertException")){
				if (AcceptAlert()) {
					return driver.findElement(mobBy);
				}
				if (CommonVariables.TestCaseLog.get() != null) {
					CommonVariables.TestCaseLog.get()
					.info("caught 'UnhandledAlertException' exception while finding '" + mobBy + "' element on '"
							+ driver.getTitle() + "' page");
				} else {
					CommonVariables.TestClassLog.get()
					.info("caught 'UnhandledAlertException' exception while finding '" + mobBy + "' element on '"
							+ driver.getTitle() + "' page");
				}
				extentLogs.error("Find Element", "caught 'UnhandledAlertException' exception while finding an element on '"
						+ driver.getTitle() + "' page");
				throw new UnhandledAlertException(""); 
			}
			else if(ex.getCause().toString().contains("NoSuchElementException")){
				if (AcceptAlert()) {
					return driver.findElement(mobBy);
				}
				if (CommonVariables.TestCaseLog.get() != null) {
					CommonVariables.TestCaseLog.get().info("No such element(" + mobBy + ") found on '"
							+ driver.getTitle() + "' page.");
				} else {
					CommonVariables.TestClassLog.get().info("No such element(" + mobBy + ") found on '"
							+ driver.getTitle() + "' page at " + driver.getTitle() + "' url.");
				}
				extentLogs.error("Find Element", "No such element éxception found on '"
						+ driver.getTitle() + "' page at " + driver.getTitle() + "' url.");
				throw new NoSuchElementException("No such Element");
			}
			else if(ex.getCause().toString().contains("TimeoutException")){
				if (AcceptAlert()) {
					return driver.findElement(mobBy);
				} else {
					if (CommonVariables.TestCaseLog.get() != null) {
						CommonVariables.TestCaseLog.get()
						.info("No such element(" + mobBy + ") found on '" + driver.getTitle() + "' page at "
								+ driver.getTitle() + "' url within timelimit (" + timeoutInSeconds + ").");
					} else {
						CommonVariables.TestClassLog.get()
						.info("No such element(" + mobBy + ") found on '" + driver.getTitle() + "' page at "
								+ driver.getTitle() + "' url within timelimit (" + timeoutInSeconds + ").");
					}
					extentLogs.error("Find Element", "No such element exception found on '" + driver.getTitle() + "' page at "
							+ driver.getTitle() + "' url within timelimit (" + timeoutInSeconds + ").");
				}
				throw new TimeoutException();
			}
			else if(ex.getCause().toString().contains("StaleElementReferenceException")){
				if (CommonVariables.TestCaseLog.get() != null) {
					CommonVariables.TestCaseLog.get()
					.info("Info. Caught 'StaleElementReferenceException' exception while finding (" + mobBy
							+ ") element. Class: " + ex.getClass());
				} else {
					CommonVariables.TestClassLog.get()
					.info("Info. Caught 'StaleElementReferenceException' exception while finding (" + mobBy
							+ ") element. Class: " + ex.getClass());
				}
				extentLogs.error("Find Element", "Caught 'StaleElementReferenceException' exception while finding an element. Class: " + ex.getClass());
			}
			AcceptAlert();
			try {
				webElement = driver.findElement(mobBy);
				return webElement;
			} catch (Exception e) {
				if (CommonVariables.TestCaseLog.get() != null) {
					CommonVariables.TestCaseLog.get()
					.info("Error. Caught 'StaleElementReferenceException' exception while finding (" + mobBy
							+ ") element. Exception Type: " + e.getClass());
				} else {
					CommonVariables.TestClassLog.get()
					.info("Error. Caught 'StaleElementReferenceException' exception while finding (" + mobBy
							+ ") element. Exception Type: " + e.getClass());
				}
				throw new StaleElementReferenceException("Stale Exception while finding an element.");
			}
		}
	}	

	/**
	 * @author Cigniti
	 * @param LocatorType
	 * @param LocatorString
	 * @param timeoutInSeconds
	 * @return
	 */
	public WebElement FindElement(MobileLocator LocatorType,String LocatorString, int timeoutInSeconds) {
		WebElement webElement;
		WebDriverWait wait;
		try {
			switch (LocatorType) {
			case ByAccessibilityId:
				if (timeoutInSeconds > 0) {
					wait = new WebDriverWait(driver, timeoutInSeconds);
					webElement = wait.until(ExpectedConditions
							.presenceOfElementLocated(MobileBy
									.AccessibilityId(LocatorString)));
				} else {
					webElement = driver.findElement(MobileBy
							.AccessibilityId(LocatorString));
					extentLogs.pass("MobileLocator", "***** Appium Server strated successfully. *****");
				}
				extentLogs.pass("Find Element", "Successfully find an element using locator type - "+LocatorType+".");
				break;
			case ByXPath:
				if (timeoutInSeconds > 0) {
					wait = new WebDriverWait(driver, timeoutInSeconds);
					webElement = wait.until(ExpectedConditions
							.presenceOfElementLocated(MobileBy
									.xpath(LocatorString)));
				} else {
					webElement = driver.findElement(MobileBy
							.xpath(LocatorString));
				}
				extentLogs.pass("Find Element", "Successfully find an element using locator type - "+LocatorType+".");
				break;
			case ByClassName:
				if (timeoutInSeconds > 0) {
					wait = new WebDriverWait(driver, timeoutInSeconds);
					webElement = wait.until(ExpectedConditions
							.presenceOfElementLocated(MobileBy
									.className(LocatorString)));
				} else {
					webElement = driver.findElement(MobileBy
							.className(LocatorString));
				}
				extentLogs.pass("Find Element", "Successfully find an element using locator type - "+LocatorType+".");
				break;
			case ById:
				if (timeoutInSeconds > 0) {
					wait = new WebDriverWait(driver, timeoutInSeconds);
					webElement = wait.until(ExpectedConditions
							.presenceOfElementLocated(MobileBy
									.id(LocatorString)));
				} else {
					webElement = driver.findElement(MobileBy
							.IosUIAutomation(LocatorString));
				}
				extentLogs.pass("Find Element", "Successfully find an element using locator type - "+LocatorType+".");
				break;
			case AndroidUIAutomator:
				if (timeoutInSeconds > 0) {
					wait = new WebDriverWait(driver, timeoutInSeconds);
					webElement = wait.until(ExpectedConditions
							.presenceOfElementLocated(MobileBy
									.AndroidUIAutomator(LocatorString)));
				} else {
					webElement = driver.findElement(MobileBy
							.IosUIAutomation(LocatorString));
				}
				extentLogs.pass("Find Element", "Successfully find an element using locator type - "+LocatorType+".");
				break;
			case ByName:
				if (timeoutInSeconds > 0) {
					wait = new WebDriverWait(driver, timeoutInSeconds);
					webElement = wait.until(ExpectedConditions
							.presenceOfElementLocated(MobileBy
									.name(LocatorString)));
				} else {
					webElement = driver.findElement(MobileBy
							.name(LocatorString));
				}
				extentLogs.pass("Find Element", "Successfully find an element using locator type - "+LocatorType+".");
				break;
			default:
				return null;

			}
			return webElement;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @author Cigniti
	 * @param webElement
	 * @return
	 */
	public boolean ClearInputField(WebElement webElement) {
		try {
			IOSElement mobileWebElement = (IOSElement) webElement;
			mobileWebElement.clear();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * @author Cigniti
	 * @param webElement
	 * @param value
	 * @return
	 */
	public boolean type(WebElement webElement, String value) {
		boolean state = false;
		try {
			try {
				webElement.sendKeys(value);
				state = true;
				try {	driver.hideKeyboard();	} catch (WebDriverException e1) {		}	
			} catch (Exception e) {
				state = false;
			}
			extentLogs.pass("Type", "Successfully entered text value - '"+value+"'.");
		} catch (Exception e) {
			extentLogs.error("Type", "Failed to enter text value - '"+value+"' due to execption - "+e.getMessage()+".");
			e.printStackTrace();
		}
		return state;
	}

	/**
	 * @author Cigniti
	 * @param webElement
	 * @return
	 */
	public boolean AndroidTap(WebElement webElement) {
		try {
			AndroidElement elemIOS = (AndroidElement) webElement;
			elemIOS.tap(1, 4);
			extentLogs.pass("Tap", "Successfully tap on an element - "+webElement+".");
			return true;
		} catch (Exception e) {
			extentLogs.error("Tap", "Failed to tap on an element - "+webElement+".");
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * @author Cigniti
	 * @param webElement
	 * @return
	 */
	public boolean click(WebElement webElement) {
		boolean state = false;
		try {
			try {	driver.hideKeyboard();	} catch (WebDriverException e1) {		}
			webElement.click();
			extentLogs.pass("Click", "Successfully clicked.");
			try {
				Thread.sleep(2000);
				state = true;
			} catch (InterruptedException e) {
				extentLogs.error("Click","Element is not clickable.");
			}
		} catch (Exception e) {
			extentLogs.error("Click","Element is not clickable.");
		}
		return state;
	}

	/**
	 * @author Cigniti
	 * @param objWebElement
	 * @param attribute
	 * @return
	 */
	public String GetElementAttributeValue(WebElement objWebElement,
			String attribute) {
		return objWebElement.getAttribute(attribute);
	}

	/**
	 * @author Cigniti
	 * @return
	 */
	public boolean AcceptAlert() {
		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * @author Cigniti
	 * @return
	 */
	public boolean declineAlert() {
		try {
			Alert alert = driver.switchTo().alert();
			alert.dismiss();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * @author Cigniti
	 * @param element
	 */
	public void SwipeRight(WebElement element) {
		SwipeElement(element, "right"); 
	}

	/**
	 * @author ashsih jain
	 * @param element
	 */
	public void SwipeBottom(WebElement element) {
		try {
			AndroidElement elemIOS = (AndroidElement) element;
			elemIOS.swipe(SwipeElementDirection.DOWN, 0);
			extentLogs.pass("Swipe", "Successfully Swiped.");
		} catch (Exception e) {
			extentLogs.error("Swipe", "Failed to swipe.");
			e.printStackTrace();
		}
	}

	/**
	 * @author Cigniti
	 * @param remoteelem
	 */
	public void SwipeLeft(WebElement remoteelem) {
		SwipeElement(remoteelem, "left"); 
	}

	/**
	 * @author Cigniti
	 * @param remoteelem
	 * @param direction
	 */
	public void SwipeElement(WebElement remoteelem, String direction) {
		Integer pagewidth  = driver.manage().window().getSize().width;
		Point eloc = remoteelem.getLocation();
		Integer swipe_startxratio = eloc.getX();
		Integer swipe_endxratio = eloc.getX() + remoteelem.getSize().width;
		Integer yratio =eloc.getY()+(remoteelem.getSize().getHeight())/ 2;
		if (swipe_startxratio < 10) {
			swipe_startxratio = 10;
		}

		if (swipe_endxratio >= pagewidth) {
			swipe_endxratio = (int) (pagewidth-10);
		}
		if(direction.trim().equalsIgnoreCase("left"))
			driver.swipe(swipe_startxratio, yratio, swipe_endxratio, yratio, 1);
		if(direction.trim().equalsIgnoreCase("right"))
			driver.swipe(swipe_endxratio, yratio, swipe_startxratio, yratio, 1);
	}

	public void ChangeOrientation(ScreenOrientation orientation) {
		try {
			driver.rotate(orientation);
			/*			driver.execute(DriverCommand.SET_SCREEN_ORIENTATION, ImmutableMap.of(
					"orientation", orientation.value().toUpperCase()));*/
		}catch (Exception e) {
			e.printStackTrace();
		}
	}



	/**
	 * @author Cigniti
	 * @param by
	 * @return
	 */
	public boolean IsElementVisible(final By by) {
		Boolean flag=false;
		try {
			wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
			if ((driver.findElement(by).getSize().height == 0)&& (driver.findElement(by).getSize().width == 0)) {
				flag=false;
			} else {
				flag=true;
			}
		} catch (Exception e1){
			flag=false;
		}
		return flag;
	}

	public void SwipeTop(WebElement element) {
		try {
			AndroidElement elemIOS = (AndroidElement) element;
			elemIOS.swipe(SwipeElementDirection.UP, 0);
		} catch (Exception e1) {

		}
	}

	public void setGeoLocation(double latitude, double longitude) {
		Location loc = new Location(latitude, longitude, 0);
		driver.setLocation(loc);
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
		}
	}

	public void setGeoLocation() {
		setGeoLocation(
				Double.valueOf(42.346886),
				Double.valueOf(-71.075529));
	}

	/**
	 * @author Cigniti
	 * @param alertShouldBeThereOrNot
	 * @param waitingTime
	 * @return
	 */
	public boolean assertLocationAlertPresent(boolean alertShouldBeThereOrNot,
			Long waitingTime) {
		WebDriverWait wait = new WebDriverWait(driver, waitingTime);
		Alert alert = null;
		try {
			alert = wait.until(ExpectedConditions.alertIsPresent());
		} catch (TimeoutException e) {

		}
		Set<String> windowHandles = driver.getWindowHandles();
		if (windowHandles.size() > 1 && alertShouldBeThereOrNot == true) {
			alert = driver.switchTo().alert();
			/**
			 * The alert text should be the one that is shown on IPhone/IPad. It
			 * should have Allow and Dont Allow buttons.
			 */
			String alertText = alert.getText();
			if (!alertText
					.equals("\"Safari\" Would Like to Use Your Current Location")) {
				alert = null;
			}
		}

		if (alert != null) {
			System.out.println("alert is present");
		} else {
			System.out.println("alert is not present");
		}

		if (alertShouldBeThereOrNot == true && alert != null) {
			return true;
		} else if (alertShouldBeThereOrNot == true && alert == null) {
			return false;
		}
		if (alertShouldBeThereOrNot == false && alert != null) {
			return false;
		} else if (alertShouldBeThereOrNot == false && alert == null) {
			return true;
		} else
			return false;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public AndroidDriver SwitchToWebView(AndroidDriver driver2) {
		try {
			Set<String> contextNames = driver2.getContextHandles();
			if(contextNames.size()>1){
				for(String contextType : contextNames){
					if (contextType.toLowerCase().contains("webview_com.piti")) {
						driver2.context(contextType); // set context  to WEBVIEW_1
						driver = driver2;
						System.out.println(driver.getContext());
						return driver2;
					} 
				}
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return driver2;
	}

	@SuppressWarnings("rawtypes")
	public AndroidDriver SwichToNativeView(AndroidDriver driver2) {
		try {
			driver2.context("NATIVE_APP");
			this.driver = driver2;
			return driver2;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @author Cigniti
	 * @param LocatorType
	 * @param LocatorString
	 * @param timeoutInSeconds
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<WebElement> FindElements(MobileLocator LocatorType,String LocatorString, int timeoutInSeconds) {
		List<WebElement> webElement;
		WebDriverWait wait;
		try {
			switch (LocatorType) {
			case ByAccessibilityId:
				if (timeoutInSeconds > 0) {
					wait = new WebDriverWait(driver, timeoutInSeconds);
					webElement = wait.until(ExpectedConditions
							.visibilityOfAllElementsLocatedBy(MobileBy
									.AccessibilityId(LocatorString)));

				} else {
					webElement = driver.findElements(MobileBy
							.AccessibilityId(LocatorString));
				}
				break;
			case ByXPath:
				if (timeoutInSeconds > 0) {
					wait = new WebDriverWait(driver, timeoutInSeconds);
					webElement = driver.findElements(MobileBy.xpath(LocatorString));
					// webElement = wait.until(
					// ExpectedConditions.visibilityOfAllElementsLocatedBy(MobileBy.xpath(LocatorString)));
				} else {
					webElement = driver.findElements(MobileBy.xpath(LocatorString));
				}
				break;
			case ByClassName:
				if (timeoutInSeconds > 0) {
					wait = new WebDriverWait(driver, timeoutInSeconds);
					webElement = driver.findElements(MobileBy.className(LocatorString));
					// webElement = wait.until(
					// ExpectedConditions.visibilityOfAllElementsLocatedBy(MobileBy.className(LocatorString)));

				} else {
					webElement = driver.findElements(MobileBy.className(LocatorString));
				}
				break;
			case AndroidUIAutomator:
				if (timeoutInSeconds > 0) {
					wait = new WebDriverWait(driver, timeoutInSeconds);
					webElement = wait.until(ExpectedConditions
							.visibilityOfAllElementsLocatedBy(MobileBy
									.IosUIAutomation(LocatorString)));

				} else {
					webElement = driver.findElements(MobileBy.IosUIAutomation(LocatorString));
				}
				break;
			default:
				return null;

			}
			return webElement;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @author Cigniti
	 * @param LocatorType
	 * @param LocatorString
	 * @param timeoutInSeconds
	 * @return
	 */
	public boolean isElementPresent(MobileLocator LocatorType,String LocatorString, Integer timeoutInSeconds) {
		if (FindElement(LocatorType, LocatorString, timeoutInSeconds) != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @author Cigniti
	 * @param mobBy
	 * @param timeoutInSeconds
	 * @return
	 */
	public boolean isElementPresent(By mobBy, Integer timeoutInSeconds) {
		if (FindElement(mobBy, timeoutInSeconds) != null) {
			return true;
		} else {

			return false;
		}
	}

	/**
	 * @author Cigniti
	 * @param e
	 * @return
	 */
	public String getStackTraceAsString(Object e) {
		Exception ex = null;
		try {
			ex = (Exception) e;
			StringBuilder sb = new StringBuilder();
			for (StackTraceElement element : ex.getStackTrace()) {
				sb.append(element.toString());
				sb.append("][");
			}
			return "<b>" + ex.getClass().toString().toUpperCase()
					+ "</b> Here is the stacktrace: " + sb.toString();
		} catch (Exception e2) {
			return "Stack trace not available.";
		}
	}

	/**
	 * @author Cigniti
	 * @param element
	 * @param index
	 */
	public void ScrollToUIATableViewUsingCellIndex(WebElement element, int index) {
		try {
			((AndroidElement) element).findElementByAndroidUIAutomator(".cells()["
					+ index + "].scrollToVisible();");
		} catch (Exception e) {
		}
	}

	/**
	 * @author Cigniti
	 * @param source
	 * @param destination
	 * @return
	 */
	public boolean downloadFile(String source, String destination){
		try {
			System.out.println("adb pull -p "+source+" "+destination);
			Runtime.getRuntime().exec("adb pull -p "+source+" "+destination);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public boolean uploadFile(String source, String destination){
		try {
			System.out.println("adb push "+source+" "+destination);
			Runtime.getRuntime().exec("adb push "+source+" "+destination);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * @author Cigniti
	 * @param appName
	 */
	public void removeApp(String appName) {
		if (driver != null) {
			try {
				String bundleId = "";
				File appDir = new File(System.getProperty("user.dir")+ "\\tool\\"+appName);
				String command = "cmd /c \"cd %android_home%\\build-tools\\22.0.1 && D: && aapt dump permissions "+appDir.getAbsolutePath()+"\"";
				System.out.println(command);
				Process proc = Runtime.getRuntime().exec(command);
				BufferedReader reader = new BufferedReader(new InputStreamReader(
						proc.getInputStream()));
				String line;
				while ((line = reader.readLine()) != null) {
					if(line.toLowerCase().contains("package:")){
						bundleId = line.split(" ")[1].trim();
						break;
					}
				}
				if(!bundleId.isEmpty()){
					driver.removeApp(bundleId);
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			catch (WebDriverException e) {}
		}
	}

	/**
	 * @author Cigniti
	 * @param by
	 * @param locator
	 * @param secs
	 * @return
	 * @throws Throwable
	 */
	@SuppressWarnings("unchecked")
	public boolean waitForElementPresent(By by, String locator, int secs) throws Throwable {
		boolean status = false;
		try {
			WebDriverWait wait = new WebDriverWait(this.driver, 60);
			wait.until(ExpectedConditions.elementToBeClickable(by));
			for (int i = 0; i < secs/2; i++)
			{
				List<WebElement> elements = this.driver.findElements(by);
				if (elements.size()>0)
				{
					status = true;
					return status;
				} 
				else
				{
					this.driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
				}
			}
		} 
		catch (Exception e) {

			return status;
		} 
		return status;
	}
}