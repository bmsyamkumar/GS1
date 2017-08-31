package com.desktop.automation.accelerators;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.remote.SessionNotFoundException;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.KeyboardSimulatorType;
import org.openqa.selenium.winium.WiniumDriver;
import org.openqa.selenium.winium.WiniumDriverCommandExecutor;
import org.openqa.selenium.winium.WiniumDriverService;
import org.openqa.selenium.winium.WiniumOptions;
import org.testng.Assert;
import com.google.common.base.Optional;
import com.web.automation.logs.CustomLogName;
import com.web.automation.logs.CustomLogs;
import com.web.automation.logs.ExtentLogs;
import com.web.automation.utilities.CommonVariables;
import com.web.automation.utilities.ConfigManager;
import winium.elements.desktop.DataGrid;
import winium.elements.desktop.extensions.WebElementExtensions;

public class DesktopLibrary {

	public WiniumDriver windriver;
	private DesiredCapabilities desiredCapabilities;
	private WebDriverWait wait;
	private DesktopOptions options;
	private WiniumDriverService service;
	private WiniumDriverCommandExecutor executor;
	private DataGrid dataGrid;
	private RemoteWebElement remoteElement;
	public static RemoteWebDriver rdriver;
	private String executorFile =System.getProperty("user.dir").replace("\\", "/")+ "/Tool/Winium/Winium.Desktop.Driver.exe";
	private ExtentLogs extentLogs=new ExtentLogs();


	public DesktopLibrary(WiniumDriver driver) throws MalformedURLException{
		ConfigManager.setProperties();
		options = new DesktopOptions();
	}

	/**
	 * @author Cigniti
	 * @description: setter for EventFiringWebDriver
	 * @param driver
	 */
	public void setDriver(WiniumDriver driver){
		this.windriver=driver;
	}

	/**
	 * @description: 
	 */
	public WiniumOptions setDriverOptions(){
		try{
			/*service = new WiniumDriverService.Builder()
					.usingDriverExecutable(new File(executorFile))
					.usingAnyFreePort()
					.withVerbose(true)
					.withSilent(false)
					.buildDesktopService();
			service.start();*/
			options = new DesktopOptions();
			options.setApplicationPath(ConfigManager.getProperties().getProperty("desktopApplicatinPath"));
			//options.setApplicationPath(System.setProperty("webdriver.winium.driver.desktop",ConfigManager.getProperties().getProperty("desktopApplicatinPath")));
			options.setKeyboardSimulator(KeyboardSimulatorType.BasedOnWindowsFormsSendKeysClass);
			options.setLaunchDelay(0);
			options.toCapabilities();
			desiredCapabilities = new DesiredCapabilities();
		}catch(Exception e){
			e.printStackTrace();
		}
		return options;
	}

	/**
	 * @author Cigniti
	 * @description : start driver
	 * @return
	 */
	public WiniumDriver instantiateDriver() {
		try {
			String osname = System.getProperty("os.name");
			System.out.println("***** Execution is happening in platform - "+osname+" *****");
			WiniumOptions options=setDriverOptions();
			if (ConfigManager.getProperties().getProperty("seleniumGrid").toLowerCase().trim().contains("true")) {
				rdriver = null;
				rdriver = new RemoteWebDriver(new URL("http://"
						+ ConfigManager.getProperties().getProperty("gridHubIP") + ":4444/wd/hub"),
						desiredCapabilities);
				try{
					windriver=new WiniumDriver(new URL("http://"+ConfigManager.getProperties().getProperty("wininServerPath")+":9999"), (WiniumOptions) desiredCapabilities);
				}catch(WebDriverException e){
					if(windriver==null){
						try{
							instantiateDriver();
							return windriver;}catch(Exception e1){e1.getLocalizedMessage();}
					}
				}
				catch(IllegalStateException e){
					if(windriver==null){
						try{
							instantiateDriver();
							return windriver;}catch(Exception e1){e1.getLocalizedMessage();}
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}
			} else if (ConfigManager.getProperties().getProperty("cloudExecution").toLowerCase().trim().contains("true")){
				rdriver = null;
				rdriver = new RemoteWebDriver(new URL("http://" + ConfigManager.getProperties().getProperty("sauceLabURL") + ":80/wd/hub"), desiredCapabilities);
				try{
					windriver=new WiniumDriver(new URL("http://"+ConfigManager.getProperties().getProperty("wininServerPath")+":9999"), (WiniumOptions) desiredCapabilities);
				}catch(WebDriverException e){
					if(windriver==null){
						try {
							Thread.sleep(10000);
						} catch (InterruptedException intr) {}
						try{
							instantiateDriver();}catch(Exception e1){e1.getLocalizedMessage();}
					}
				}
				catch(IllegalStateException e){
					if(windriver==null){
						try{
							try {
								Thread.sleep(10000);
							} catch (InterruptedException intr) {}
							instantiateDriver();
							return windriver;}catch(Exception e1){e1.getLocalizedMessage();}
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}else{
				try{
					windriver=new WiniumDriver(new URL("http://"+ConfigManager.getProperties().getProperty("wininServerPath")+":9999"), options);
				}catch(WebDriverException e){
					if(windriver==null){
						try {
							Thread.sleep(10000);
						} catch (InterruptedException intr) {}
						try{
							instantiateDriver();
							return windriver;}catch(Exception e1){e1.getLocalizedMessage();}
					}
				}
				catch(IllegalStateException e){
					if(windriver==null){
						try{
							instantiateDriver();
							return windriver;}catch(Exception e1){e1.getLocalizedMessage();}
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
			CommonVariables.setDriver(this.windriver);
			return windriver;
		}
		catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			return windriver;
		}
	}

	/**
	 * @author Cigniti
	 * @description : Method to Find Element
	 * @param by
	 * @param timeoutInSeconds
	 * @return
	 * @throws Throwable 
	 */
	public RemoteWebElement FindElement(By by, Optional<Long> timeoutInSeconds){
		long timeout = timeoutInSeconds.isPresent() ? timeoutInSeconds.get() : -1;
		if (timeout == -1) {
			timeout = Long.parseLong(ConfigManager.getProperties().getProperty("globalTimeOut"));
		}
		remoteElement = (RemoteWebElement) windriver.findElement(by);
		dataGrid = WebElementExtensions.toDataGrid(by.findElement(remoteElement));
		remoteElement = (RemoteWebElement) dataGrid.findElement(by);
		try {
			AcceptAlert();
			remoteElement = (RemoteWebElement) (new WebDriverWait(windriver, timeout)).until(ExpectedConditions.presenceOfElementLocated(by));
			try {
				if (remoteElement != null || CommonVariables.TestCaseLog.get() != null) {
					extentLogs.pass("Find Element", "Successfully find an element on page.");
					CommonVariables.TestCaseLog.get().info("Successfully find '" + by + "' element on page");
				} else {
					extentLogs.fail("Find Element", "Element not found on page.");
					CommonVariables.TestClassLog.get().info("Successfully find '" + by + "' element on page");
				}
			} catch (NullPointerException e) {
				extentLogs.error("Find Element", "Element not found on page due to exception - "+e.getLocalizedMessage());
				throw new NullPointerException();
			}
			catch (org.openqa.selenium.ElementNotVisibleException e) {
				if (CommonVariables.TestCaseLog.get() != null) {
					CommonVariables.TestCaseLog.get()
					.info("Element (" + remoteElement + ") is not visible on page");
				} else {
					CommonVariables.TestClassLog.get()
					.info("Element (" + remoteElement + ") is not visible on page");
				}
				extentLogs.error("Find Element", "Element not visible on page due to exception - "+e.getLocalizedMessage());
				throw new ElementNotVisibleException("Element not visible.");
			} 
			catch (org.openqa.selenium.WebDriverException e) {
				if (CommonVariables.TestCaseLog.get() != null) {
					CommonVariables.TestCaseLog.get()
					.info("Failed to move Element (" + by + ") on the running driver type.");
				} else {
					CommonVariables.TestClassLog.get()
					.info("Failed to move Element (" + by + ") on the running driver type.");
				}
				extentLogs.error("Find Element", "Failed to move to an Element due to exception - "+e.getLocalizedMessage());
				throw new WebDriverException();
			} 
		}catch(Exception e){
			e.getMessage();
		}
		return remoteElement;
	}


	/**
	 * @author Cigniti
	 * @description : shut down driver :- This method will close all instances of all drivers.
	 */
	public void tearDown() {
		try {
			if(windriver != null && CommonVariables.DeviceName.get().toLowerCase().contains("desktop")
					&& CommonVariables.PlatformName.get().toLowerCase().contains("windows")) {
				try {
					try {
						if (rdriver != null) {
							rdriver.quit();
							try {
								Thread.sleep(5000);
							} catch (InterruptedException e1) {
							}
							catch (IllegalStateException e2) {
							}
						}
					} catch (Exception ex) {
					}

					try {
						if (windriver != null) {
							System.out.println("Quitting the driver");
							windriver.quit();
							try {
								Thread.sleep(8000);
							} catch (InterruptedException e1) {
							}catch (IllegalStateException e2) {
							}
						}
					} catch (Exception ex) {
					}

					try {
						if (windriver != null) {
							windriver.quit();
							try {
								Thread.sleep(8000);
							} catch (InterruptedException e1) {
							}catch (IllegalStateException e2) {
							}
						}
					} catch (Exception ex) {
					}

					try {
						CommonVariables.winiumDriver.get().quit();
						CommonVariables.winiumDriver.set(null);
					} catch (Exception e) {
						CommonVariables.winiumDriver.set(null);
					}
				}catch(Exception e){}
			}
		}catch(Exception e1){}
		CommonVariables.winiumDriver.set(null);
	}

	/**
	 * @author Cigniti
	 * @description : Webdriver and Java Script Click. Wait time in seconds i.e. 8 for 8 seconds
	 * @param webElement and wait time in seconds
	 * @return
	 */
	public boolean Click (RemoteWebElement element, Integer waitTime)
	{
		boolean state = false;
		try {
			Thread.sleep(500);
			remoteElement = (RemoteWebElement) element;
			/*dataGrid = WebElementExtensions.toDataGrid(element);
			remoteElement = (RemoteWebElement) dataGrid.findElement(by);*/
			remoteElement.click();
			try {
				Thread.sleep(waitTime * 1000);
				extentLogs.pass("Click", "Successfully clicked.");
				if (CommonVariables.TestCaseLog.get() != null) {
					CustomLogs.addToLog("CurrentTestCaseLog", "info", "Passed. Successfully clicked.");
				} else {
					CustomLogs.addToLog("CurrentTestClassLog", "info", "Passed. Successfully clicked.");
				}
				state = true;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (ElementNotVisibleException e1) {
			extentLogs.error("Click","Element is not visible.");
			if (CommonVariables.TestCaseLog.get() != null) {
				CommonVariables.TestCaseLog.get().debug("Error: Element (" + remoteElement + ") is not visible.");
			} else {
				CommonVariables.TestClassLog.get().debug("Error: Element (" + remoteElement + ") is not visible.");
			}
		} catch (StaleElementReferenceException e2) {
			extentLogs.error("Click","Caught 'StaleElementReferenceException' exception while clicking on element.");
			if (CommonVariables.TestCaseLog.get() != null) {
				CommonVariables.TestCaseLog.get()
				.debug("Error. Caught 'StaleElementReferenceException' exception while clicking on '"
						+ remoteElement + "' element.");
			} else {
				CommonVariables.TestClassLog.get()
				.debug("Error. Caught 'StaleElementReferenceException' exception while clicking on '"
						+ remoteElement + "' element.");
			}
			AcceptAlert();
			try {
				if (remoteElement.isEnabled()) {
					remoteElement.click();
				}
			} catch (Exception e) {
			}
		} catch (org.openqa.selenium.UnhandledAlertException e3) {
			extentLogs.error("Click","Error. Caught 'UnhandledAlertException' exception while clicking on element.");
			AcceptAlert();
			try {
				if (remoteElement.isDisplayed()) {
					remoteElement.click();
				}
			} catch (Exception e) {
				if (CommonVariables.TestCaseLog.get() != null) {
					CommonVariables.TestCaseLog.get()
					.info("Info. Caught 'UnhandledAlertException' exception while clicking on '" + remoteElement
							+ "' element.");
				} else {
					CommonVariables.TestClassLog.get()
					.info("Info. Caught 'UnhandledAlertException' exception while clicking on '" + remoteElement
							+ "' element.");
				}e.printStackTrace();
			}
		} catch (NullPointerException e5) {
			extentLogs.error("Click","Error. Caught 'NullPointerException' exception while clicking element on '"+ windriver.getTitle() + "'.");
			if (CommonVariables.TestCaseLog.get() != null) {
				CommonVariables.TestCaseLog.get()
				.debug("Info. Caught 'NullPointerException' exception while clicking element on '"
						+ windriver.getTitle() + "'.");
			} else {
				CommonVariables.TestClassLog.get()
				.debug("Info. Caught 'NullPointerException' exception while clicking element on '"
						+ windriver.getTitle() + "'.");
			}
		} catch (org.openqa.selenium.WebDriverException e6) {
			if (e6.getMessage().toString().toLowerCase().contains("user supplied")) {
				try {
					JavascriptExecutor js = (JavascriptExecutor) (windriver);
					js.executeScript("arguments[0].click();", remoteElement);
					Thread.sleep(8000);
					state = true;
				} catch (Exception e) {
					if (!state) {
						try {
							JavascriptExecutor js = (JavascriptExecutor) (windriver);
							js.executeScript("$(arguments[0]).focusin();", remoteElement);
							Thread.sleep(8000);
							state = true;
						} catch (Exception e1) {
							state = false;
						}
					}
				}
			}
			if (e6.getMessage().toString().toLowerCase().contains("Element is not clickable")) {
				extentLogs.error("Click","Caught exception about 'Element is not clickable' while clicking element on '"+ windriver.getTitle() + "'. So Scroll Top/Bottom and wait for sometime to make clickable");
				if (CommonVariables.TestCaseLog.get() != null) {
					CommonVariables.TestCaseLog.get()
					.info("Info: Caught exception about 'Element is not clickable' while clicking element on '"
							+ windriver.getTitle() + "'. So Scroll Top/Bottom and wait for sometime to make '"
							+ remoteElement + "' clickable");
				} else {
					CommonVariables.TestClassLog.get()
					.info("Info: Caught exception about 'Element is not clickable' while clicking element on '"
							+ windriver.getTitle() + "'. So Scroll Top/Bottom and wait for sometime to make '"
							+ remoteElement + "' clickable");
				}
			}
			try {
				try {
					ScrollToTop();
					JavascriptExecutor js = (JavascriptExecutor) (windriver);
					js.executeScript("arguments[0].focusin();", remoteElement);
					new WebDriverWait(windriver, Long.parseLong("10"))
					.until(ExpectedConditions.elementToBeClickable(remoteElement)).click();
					Thread.sleep(8000);
					state = true;
				} catch (Exception e1) {
					if (e6.getMessage().toString().toLowerCase().contains("element is not clickable")) {
						try {
							ScrollToBottom();
							JavascriptExecutor js = (JavascriptExecutor) (windriver);
							js.executeScript("arguments[0].focusin();", remoteElement);
							new WebDriverWait(windriver, Long.parseLong("10"))
							.until(ExpectedConditions.elementToBeClickable(remoteElement)).click();
							state = true;
						} catch (Exception e) {
							return false;
						}
					} else {
						state = false;
					}
				}
			} catch (Exception e) {
				try {
					Actions clicker = new Actions(windriver);
					clicker.moveToElement(remoteElement).click().perform();

				} catch (Exception e7) {
					state = false;
					extentLogs.error("Click","Element is not clickable.");
					if (CommonVariables.TestCaseLog.get() != null) {
						CommonVariables.TestCaseLog.get().error("Error: element is not clickable.");
					} else {
						CommonVariables.TestClassLog.get().error("Error: element is not clickable.");
					}
				}

			}
		} catch (Exception e4) {
			state = false;
		}
		return state;
	}

	/**
	 * @author Cigniti
	 * @description : Get collection webelements using Find Elements
	 * @param by
	 * @param timeoutInSeconds
	 * @return
	 */
	public List<WebElement> FindElements(By by, Optional<Long> timeoutInSeconds) {
		long timeout = timeoutInSeconds.isPresent() ? timeoutInSeconds.get() : -1;
		if (timeout == -1) {
			timeout = Long.parseLong(ConfigManager.getProperties().getProperty("globalTimeOut"));
		}
		List<WebElement> webElement;
		try {
			webElement = (new WebDriverWait(windriver, timeout))
					.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
			extentLogs.pass("Find Elements","Successfully find matched elements by '" + by + " in existing page.");
			CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "info","Info: Successfully find matched elements by '" + by + " in existing page.");
			return webElement;
		} catch (org.openqa.selenium.UnhandledAlertException e) {
			if (AcceptAlert()) {
				return FindElements(by, timeoutInSeconds);
			}
			extentLogs.error("Find Elements","caught 'UnhandledAlertException' exception while finding an elements.");
			CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "info","Info: caught 'UnhandledAlertException' exception while finding '" + by + "' element.");
			throw new UnhandledAlertException("");
		} catch (StaleElementReferenceException e2) {
			if (AcceptAlert()) {
				return FindElements(by, timeoutInSeconds);
			}
			extentLogs.error("Find Elements","caught 'StaleElementReferenceException' exception.");
			CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "info","Info: caught 'StaleElementReferenceException' exception.");
			throw new StaleElementReferenceException("");
		}
		catch (TimeoutException e3) {
			if (AcceptAlert()) {
				return FindElements(by, timeoutInSeconds);
			}
			extentLogs.error("Find Elements","caught 'TimeoutException' exception");
			CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "info","Info: caught 'TimeoutException' exception.");
			throw new TimeoutException();
		}
		catch (NoSuchElementException e4) {
			if (AcceptAlert()) {
				return FindElements(by, timeoutInSeconds);
			}
			extentLogs.error("Find Elements","caught 'NoSuchElement' exception.");
			CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "info","Info: caught 'NoSuchElement' exception");
			throw new NoSuchElementException("No such Element");
		}
	}

	/**
	 * @author Cigniti
	 * @Description: Accept alert pop-ups
	 * @return
	 */
	public boolean AcceptAlert() {
		try {
			Alert alert = windriver.switchTo().alert();
			alert.accept();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * @author Cigniti
	 * @description : Decline Alert
	 * @return
	 */
	public boolean declineAlert() {
		try {
			Alert alert = windriver.switchTo().alert();
			alert.dismiss();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * @author Cigniti
	 * @description: change first character of string to Capital Letter
	 * @param str
	 * @return
	 */
	public static String ChangeStringFirstCharToCapital(String str)
	{
		StringBuilder b = new StringBuilder(str);
		int i = 0;
		do 
		{
			b.replace(i, i + 1, b.substring(i,i + 1).toUpperCase());
			i =  b.indexOf(" ", i) + 1;
		} 
		while (i > 0 && i < b.length());
		return b.toString();
	}

	/**
	 * @author Cigniti
	 * @description : Is Element Display In Current View
	 * @param xpath
	 * @return
	 */
	public boolean IsElementDisplayInCurrentView(By xpath) {
		try
		{
			return IsElementDisplayInCurrentView(windriver.findElement(xpath));
		}
		catch(Exception ex){
			return false;
		}
	}

	/**
	 * @author Cigniti
	 * @description: verify visibility of an element in current open view in UI
	 * @param webElement
	 * @return
	 */
	public boolean IsElementDisplayInCurrentView(WebElement webElement) {
		double browser_top_offset = 0.0;
		try{
			JavascriptExecutor js = (JavascriptExecutor)windriver;
			Long pageheight=(Long)js.executeScript("return $(window).height()");
			double elemPos = 0;
			try{
				Long posY=(Long)js.executeScript("return $(arguments[0]).offset().top - $(window).scrollTop()",webElement);
				elemPos = posY.doubleValue();
			}
			catch(ClassCastException cc){
				elemPos = (Double)js.executeScript("return $(arguments[0]).offset().top - $(window).scrollTop()",webElement);
			}
			double pageheight1 =pageheight.doubleValue();
			if(elemPos>=browser_top_offset && elemPos<pageheight1)
				return true;
			else
				return false;
		}
		catch(Exception ex){
			System.out.println(""+ex.getMessage());
			return false;
		}
	}

	/**
	 * @author Cigniti
	 * @description: scroll to top of the page
	 */
	public void ScrollToTop(){
		try{
			JavascriptExecutor js = (JavascriptExecutor) windriver;
			js.executeScript("$('body').scrollTop(0);");
		}catch(Exception e){	
			System.out.println("Failed to Swipe on Top on Non-Desktop device. Check 'ScrollToTop' method under action library");
		}
	}

	/**
	 * @author Cigniti
	 * @description: scroll page to bottom of the page
	 */
	public void ScrollToBottom(){
		JavascriptExecutor js = (JavascriptExecutor)windriver;
		int height_covered=0,pageHeightLeft=0;
		Long pageheight1=(Long)js.executeScript("return window.innerHeight");
		Long maxPageHeight1=(Long)js.executeScript("return Math.max(document.documentElement.scrollHeight, document.body.scrollHeight," +
				"document.documentElement.clientHeight, window.innerHeight)");
		float sections=(float)maxPageHeight1/pageheight1;
		int numberOfRows=(int)Math.ceil(sections);
		int pageheight=pageheight1.intValue();
		int maxPageHeight=maxPageHeight1.intValue();
		for (int row=0; row<numberOfRows ; row++) {
			pageHeightLeft=maxPageHeight-height_covered;
			if((pageHeightLeft < pageheight)){
			}
			else{
				height_covered=height_covered+pageheight;
				js.executeScript("window.scrollTo(0,"+height_covered+")");
			}
		}
	}

	/**
	 * @author Cigniti
	 * @description: winium driver send keys
	 * @param locator the locator
	 * @param valueToType the value to type
	 * @param locatorName the locator name
	 * @return true, if successful
	 * @throws Throwable the throwable
	 */
	public boolean type(RemoteWebElement element, String valueToType) throws Throwable
	{
		boolean status = false;
		try
		{
			WebDriverWait wait = new WebDriverWait(this.windriver, 60);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.clear();
			element.sendKeys(valueToType);
			status = true;
			extentLogs.pass("Type", "Successfully entered text value - '"+valueToType+"'.");
		}
		catch(Exception e)
		{
			status = false;
			extentLogs.error("Type", "Failed to enter text value - '"+valueToType+"' due to execption - "+e.getMessage()+".");
			if (CommonVariables.TestCaseLog.get() != null) {
				CommonVariables.TestCaseLog.get().error("Error: Failed to enter text value - '"+valueToType+"' due to execption - "+e.getMessage()+".");
			} else {
				CommonVariables.TestClassLog.get().error("Error: Failed to enter text value - '"+valueToType+"' due to execption - "+e.getMessage()+".");
			}
		}
		return status;
	}

	public static Response getTrueResponse() { 
		Response result = new Response(); 
		result.setValue(true); 
		return result; 
	} 

	public static Response getIntResponse(int value) { 
		Response result = new Response(); 
		result.setValue(value); 
		return result; 
	} 

	/**
	 * @return Clipboard text
	 * @throws UnsupportedFlavorException
	 * @throws IOException
	 */
	public String getTextFromClipboard() throws UnsupportedFlavorException, IOException
	{
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Clipboard clipboard = toolkit.getSystemClipboard();
		return (String) clipboard.getData(DataFlavor.stringFlavor);
	}

	/**
	 * @author Cigniti
	 * @param filePath
	 * @param element
	 * @param time
	 * @param locatorName
	 * @return
	 * @throws Throwable
	 */
	public boolean uploadFile(String filePath, RemoteWebElement element, int time, String locatorName) throws Throwable {
		boolean flag = false;
		try {
			WebDriverWait wait = new WebDriverWait(windriver, 60);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			Click(element, time);
			Thread.sleep(5000);
			Actions KeyboardEvent = new Actions(windriver);
			KeyboardEvent.sendKeys(filePath);
			Thread.sleep(1000);
			KeyboardEvent.sendKeys(Keys.ENTER);
			flag=true;
		} catch (NoAlertPresentException ex) {
			ex.printStackTrace();
			flag=false;
		}
		return flag;
	}

	/**
	 * @author Cigniti
	 * Description: capture screenshot
	 * @param driver
	 * @param fileName
	 */
	@SuppressWarnings("unused")
	private void takesScreenShot(WiniumDriver driver, String fileName) {
		WebDriver driverScreenShot = new Augmenter().augment(driver);
		File scrFile = ((TakesScreenshot) driverScreenShot)
				.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Cigniti
	 * @description : Archive File
	 * @throws IOException
	 */
	private void archiveFile(String srcFilePath,String destFilePath) throws IOException {
		try {
			File source=new File(srcFilePath);
			File destination=new File(destFilePath);
			FileUtils.copyFile(source, destination);
		} catch (FileNotFoundException e) {
			e.getLocalizedMessage();
		}
	}

	/** <b>Description:</b> Copies the entire src File to Dest 
	 * @param src
	 * @param dest
	 * @throws Exception
	 */
	public static void staticCopyFile(File src, File dest) throws Exception
	{
		if(src.isDirectory()){
			if(!dest.exists()){
				dest.mkdir();
				System.out.println("Directory copied from " + src + " to " + dest);
			}

			String files[] = src.list();

			for (String file : files) {
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				staticCopyFile(srcFile,destFile);
			}

		}else{
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest); 

			byte[] buffer = new byte[1024];

			int length;
			while ((length = in.read(buffer)) > 0){
				out.write(buffer, 0, length);
			}

			in.close();
			out.close();
		}
	}

	/**
	 * @author Cigniti
	 */
	public static enum Mode {
		ALPHA, ALPHANUMERIC, NUMERIC

	};

	/**
	 * @author Cigniti
	 * @descriton: generate random number - alpa,num,alphnum
	 * @param length
	 * @param mode
	 * @return
	 */
	public static String generateRandomString(int length, Mode mode){
		StringBuffer buffer = new StringBuffer();
		String characters = "";
		Boolean isNumericOnly=false;
		String automationTextPrefix="";
		switch (mode) {
		case ALPHA:
			characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
			break;

		case ALPHANUMERIC:
			characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
			break;

		case NUMERIC:
			isNumericOnly=true;
			characters = "123456789";
			break;
		}
		int charactersLength = characters.length();
		if(length>=3&&!isNumericOnly){
			length=length-2;
			automationTextPrefix="AT";
		}
		for (int i = 0; i < length; i++) {
			double index = Math.random() * charactersLength;
			buffer.append(characters.charAt((int) index));
		}
		if (isNumericOnly) {
			return buffer.toString();
		}
		else{
			return automationTextPrefix+buffer.toString();
		}
	}
}