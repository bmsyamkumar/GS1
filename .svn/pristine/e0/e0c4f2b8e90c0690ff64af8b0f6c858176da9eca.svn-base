package com.web.automation.accelerators;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie2;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.remote.SessionNotFoundException;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;
import com.google.common.base.Optional;
import com.web.automation.bmp.BMP_Proxy_Controller;
import com.web.automation.listeners.MyListener;
import com.web.automation.listeners.WebDriverListener;
import com.web.automation.logs.CustomLogName;
import com.web.automation.logs.CustomLogs;
import com.web.automation.logs.ExtentLogs;
import com.web.automation.utilities.CaptureScreenShot;
import com.web.automation.utilities.CommonVariables;
import com.web.automation.utilities.ConfigManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import net.lightbody.bmp.proxy.jetty.html.Page;

public class ActionsLibrary extends MyListener {

	public static WebDriver wd;
	public static EventFiringWebDriver driver;
	private WebDriverListener eventListener;
	public static RemoteWebDriver rdriver;
	private AppiumDriver appiumDriver;
	private WebDriverWait wait;
	public static int driverStartfailureCount = 1;
	private DesiredCapabilities objCapabilities;
	private ChromeOptions chromeoptions;
	private OperaOptions operaOptions;
	private Set<String> arrKnownBrowserHwnd; // Stores windows handle when
												// launching a new browser
	public String hwndFirstWindow; // This will store handle of original window
	public String hwndMostRecentWindow; // This will store handle of most
										// recently known window
	public Boolean locationServiceEnabled;
	private Boolean doFullReset;
	private String chromeProfile;
	private String firefoxProfilePath;
	
	private String safariProfilePath;
	private LoggingPreferences prefs;
	private ExtentLogs extentLogs = new ExtentLogs();
	private FileWriter fw;
	private PrintWriter pw;
	private int CurrentMonthDays;
	private static String downloadedfilepath;
	private FirefoxProfile firefoxProfile;
	public static String gStrErrMsg = " ";
	
	@SuppressWarnings("static-access")
	public ActionsLibrary(WebDriver driver) {
		this.driver = (EventFiringWebDriver) driver;
		initPropertiesFile();
		this.locationServiceEnabled = Boolean
				.parseBoolean(ConfigManager.getProperties().getProperty("locationServiceEnabled").trim().toLowerCase());
		doFullReset = true;
		if (this.locationServiceEnabled) {
			this.chromeProfile = "browser-profile-location-boston";
			this.firefoxProfilePath = "Firefox_profile_For_Location_Simulate";
			this.safariProfilePath = "Safari_Shared_Location_Profile";
		}
	}

	/**
	 * @author Cigniti
	 * @description: setter for EventFiringWebDriver
	 * @param driver
	 */
	public void setDriver(EventFiringWebDriver driver) {
		this.driver = driver;
	}

	/**
	 * @author Cigniti
	 */
	public ActionsLibrary() {
		initPropertiesFile();
	}

	/**
	 * @author Cigniti
	 * @description: Intialize config properties
	 */
	private void initPropertiesFile() {
		try {
			if (!ConfigManager.ArePropertiesSet.get()) {
				ConfigManager.getProperties();
				ConfigManager.UpdateProperties();
			}
		} catch (Exception e) {
			e.getLocalizedMessage();
		}
	}

	/**
	 * @author Cigniti
	 * @description : Webdriver Wait For Page with time
	 * @param time
	 */
	@SuppressWarnings("static-access")
	public void WebdriverWaitForPage() {
		this.driver.manage().timeouts().implicitlyWait(
				Long.parseLong(ConfigManager.getProperties().getProperty("globalTimeOut")), TimeUnit.SECONDS);
	}

	/**
	 * @author Cigniti
	 * @description : start webdriver with an instance of browser type.
	 * @param browserType
	 * @return
	 */
	@SuppressWarnings("static-access")
	public WebDriver instantiateDriver(String browserType) {
		String chromeprofilepath = null;
		Process process = null;
		try {
			String osname = System.getProperty("os.name");
			String DriverPath = "";
			tearDown();
			System.out.println("************ Starting the driver: " + browserType + " ***************");
			downloadedfilepath = System.getProperty("user.dir").replace("\\", "/") + "/Tool/downloadedFiles";
			switch (browserType.trim().toLowerCase()) {
			case "linux-chrome":
				if (ConfigManager.getProperties().getProperty("KillDriverBinaries").toLowerCase().trim()
						.contains("yes")) {
					KillWindowProcess("chromedriver.exe");
				}
				chromeoptions = new ChromeOptions();
				chromeoptions.addArguments("test-type");
				chromeoptions.addArguments("disable-session-crashed-bubble");
				chromeoptions.addArguments("disable-popup-blocking");
				chromeoptions.addArguments("--lang=" + ConfigManager.getProperties().getProperty("lacaleLang"));
				objCapabilities = new DesiredCapabilities();
				objCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeoptions);
				objCapabilities.setCapability("platform", Platform.LINUX);
				objCapabilities.setCapability("browserName", "chrome");
				objCapabilities.setCapability("takesScreenshot", true);
				objCapabilities.setCapability("handlesAlerts", true);
				objCapabilities.setCapability("browserTimeout", 9000000);
				objCapabilities.setCapability("username", ConfigManager.getProperties().getProperty("sauceLabUser"));
				objCapabilities.setCapability("accessKey", ConfigManager.getProperties().getProperty("sauceLabKey"));
				this.setBrowserProxy();
				if (ConfigManager.getProperties().getProperty("seleniumGrid").toLowerCase().trim().contains("true")) {
					rdriver = null;
					rdriver = new RemoteWebDriver(new URL(
							"http://" + ConfigManager.getProperties().getProperty("gridHubIP") + ":4444/wd/hub"),
							objCapabilities);
					driver = new EventFiringWebDriver(rdriver);
					eventListener = null;
					eventListener = new WebDriverListener(driver);
					driver.register(eventListener);
				} else if (ConfigManager.getProperties().getProperty("cloudExecution").toLowerCase().trim()
						.contains("true")) {
					rdriver = null;
					rdriver = new RemoteWebDriver(new URL(
							"http://" + ConfigManager.getProperties().getProperty("sauceLabURL") + ":80/wd/hub"),
							objCapabilities);
					driver = new EventFiringWebDriver(rdriver);
					eventListener = null;
					eventListener = new WebDriverListener(driver);
					driver.register(eventListener);
				} else {
					DriverPath = System.getProperty("user.dir").replace("\\", "/")
							+ "/Tool/chromedrivers/linux/chromedriver.exe";
					System.setProperty("webdriver.chrome.driver", DriverPath);
					this.wd = null;
					this.wd = new ChromeDriver(objCapabilities);
					this.driver = new EventFiringWebDriver(wd);
					eventListener = null;
					eventListener = new WebDriverListener(this.driver);
					this.driver.register(eventListener);
				}
				this.driver.manage().window().maximize();
				this.driver.getWrappedDriver().manage().deleteAllCookies();
				suppressCredential();
				break;
			case "mac-chrome":
				if (ConfigManager.getProperties().getProperty("KillDriverBinaries").toLowerCase().trim()
						.contains("yes")) {
					KillWindowProcess("chromedriver.exe");
				}
				LoggingPreferences prefs1 = new LoggingPreferences();
				prefs1.enable(LogType.BROWSER, Level.ALL);
				setDownloadPathForFile(downloadedfilepath);
				chromeoptions = new ChromeOptions();
				chromeoptions.addArguments("test-type");
				chromeoptions.addArguments("disable-session-crashed-bubble");
				chromeoptions.addArguments("disable-popup-blocking");
				chromeoptions.addArguments("--lang=" + ConfigManager.getProperties().getProperty("lacaleLang"));
				objCapabilities = new DesiredCapabilities();
				objCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeoptions);
				objCapabilities.setCapability(CapabilityType.LOGGING_PREFS, prefs1);
				objCapabilities.setCapability("platform", CommonVariables.PlatformName.get().toUpperCase());
				objCapabilities.setCapability("browserName", "chrome");
				objCapabilities.setCapability("username", ConfigManager.getProperties().getProperty("sauceLabUser"));
				objCapabilities.setCapability("accessKey", ConfigManager.getProperties().getProperty("sauceLabKey"));
				this.setBrowserProxy();
				if (ConfigManager.getProperties().getProperty("seleniumGrid").toLowerCase().trim().contains("true")) {
					rdriver = null;
					rdriver = new RemoteWebDriver(new URL(
							"http://" + ConfigManager.getProperties().getProperty("gridHubIP") + ":4444/wd/hub"),
							objCapabilities);
					driver = new EventFiringWebDriver(rdriver);
					eventListener = null;
					eventListener = new WebDriverListener(driver);
					driver.register(eventListener);
				} else if (ConfigManager.getProperties().getProperty("cloudExecution").toLowerCase().trim()
						.contains("true")) {
					rdriver = null;
					rdriver = new RemoteWebDriver(new URL(
							"http://" + ConfigManager.getProperties().getProperty("sauceLabURL") + ":80/wd/hub"),
							objCapabilities);
					driver = new EventFiringWebDriver(rdriver);
					eventListener = null;
					eventListener = new WebDriverListener(driver);
					driver.register(eventListener);
				} else {
					//DriverPath = System.getProperty("user.dir").replace("\\", "/")
					//		+ "/Tool/chromedrivers/mac/chromedriver";
					//System.setProperty("webdriver.chrome.driver", DriverPath);
					this.wd = null;
					this.wd = new ChromeDriver(objCapabilities);
					this.driver = new EventFiringWebDriver(wd);
					eventListener = null;
					eventListener = new WebDriverListener(this.driver);
					this.driver.register(eventListener);
				}
				this.driver.manage().window().maximize();
				driver.getWrappedDriver().manage().deleteAllCookies();
				suppressCredential();
				break;
			case "windows-chrome":
				if (ConfigManager.getProperties().getProperty("KillDriverBinaries").toLowerCase().trim()
						.contains("yes")) {
					KillWindowProcess("chromedriver.exe");
				}
				chromeoptions = new ChromeOptions();
				chromeoptions.addArguments("test-type");
				chromeoptions.addArguments("disable-session-crashed-bubble");
				chromeoptions.addArguments("disable-popup-blocking");
				chromeoptions.addArguments("--lang=" + ConfigManager.getProperties().getProperty("lacaleLang"));
				if (locationServiceEnabled != null) {
					if (!locationServiceEnabled) {
						Map<String, Object> prefs = new HashMap<String, Object>();
						prefs.put("profile.default_content_settings.geolocation", 2);
						chromeoptions.setExperimentalOption("prefs", prefs);
					} else if (locationServiceEnabled) {
						chromeprofilepath = System.getProperty("user.dir").replace("\\", "/")
								+ "/Tool/BrowserProfiles/Chrome/" + chromeProfile;
						if (locationServiceEnabled && ConfigManager.getProperties().getProperty("seleniumGrid")
								.toLowerCase().trim().contains("true"))
							chromeprofilepath = System.getProperty("user.dir").replace("\\", "/")
									+ "/Tool/BrowserProfiles/Chrome/" + chromeProfile;
						chromeoptions.addArguments("user-data-dir=" + chromeprofilepath);
					}
				}
				prefs = new LoggingPreferences();
				prefs.enable(LogType.BROWSER, Level.ALL);
				setDownloadPathForFile(downloadedfilepath);
				objCapabilities = new DesiredCapabilities();
				objCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeoptions);
				objCapabilities.setCapability(CapabilityType.LOGGING_PREFS, prefs);
				objCapabilities.setCapability("platform", CommonVariables.PlatformName.get().toUpperCase().trim());
				objCapabilities.setCapability("browserName", "chrome");
				objCapabilities.setCapability("version", CommonVariables.Version.get());
				objCapabilities.setCapability("takesScreenshot", true);
				objCapabilities.setCapability("handlesAlerts", true);
				objCapabilities.setCapability("username", ConfigManager.getProperties().getProperty("sauceLabUser"));
				objCapabilities.setCapability("accessKey", ConfigManager.getProperties().getProperty("sauceLabKey"));
				this.setBrowserProxy();
				if (ConfigManager.getProperties().getProperty("seleniumGrid").toLowerCase().trim().contains("true")) {
					rdriver = null;
					rdriver = new RemoteWebDriver(new URL(
							"http://" + ConfigManager.getProperties().getProperty("gridHubIP") + ":4444/wd/hub"),
							objCapabilities);
					driver = new EventFiringWebDriver(rdriver);
					eventListener = null;
					eventListener = new WebDriverListener(driver);
					driver.register(eventListener);
				} else if (ConfigManager.getProperties().getProperty("cloudExecution").toLowerCase().trim()
						.contains("true")) {
					rdriver = null;
					rdriver = new RemoteWebDriver(new URL(
							"http://" + ConfigManager.getProperties().getProperty("sauceLabURL") + ":80/wd/hub"),
							objCapabilities);
					driver = new EventFiringWebDriver(rdriver);
					eventListener = null;
					eventListener = new WebDriverListener(driver);
					driver.register(eventListener);
				} else {
					DriverPath = System.getProperty("user.dir").replace("\\", "/")
							+ "/Tool/chromedrivers/windows/chromedriver.exe";
					System.setProperty("webdriver.chrome.driver", DriverPath);
					this.wd = null;
					this.wd = new ChromeDriver(objCapabilities);
					this.driver = new EventFiringWebDriver(wd);
					eventListener = null;
					eventListener = new WebDriverListener(this.driver);
					this.driver.register(eventListener);
				}
				this.driver.manage().window().maximize();
				this.driver.getWrappedDriver().manage().deleteAllCookies();
				if (locationServiceEnabled) {
					driver.navigate().to("chrome://settings/clearBrowserData");
					WebElement frame = driver
							.findElement(By.xpath("//iframe[@src='chrome://settings-frame/clearBrowserData']"));
					WebDriver frameDriver = driver.switchTo().frame(frame);
					Select dropDown = new Select(frameDriver.findElement(By.id("clear-browser-data-time-period")));
					dropDown.selectByIndex(4);
					WebElement elm = driver.findElement(By.id("delete-cookies-checkbox"));
					if (!elm.isSelected())
						elm.click();
					elm = driver.findElement(By.xpath("//button[@id='clear-browser-data-commit']"));
					elm.click();
					(new WebDriverWait(driver, 90)).until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//button[@id='clear-browser-data-commit']")));
				}
				/*
				 * process = Runtime .getRuntime() .exec(
				 * "RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 255");
				 * process.waitFor(); Thread.sleep(3000);
				 */
				suppressCredential();
				break;
			case "mac-safari":
				SafariOptions safariOptions = new SafariOptions();
				objCapabilities = DesiredCapabilities.safari();
				objCapabilities.setCapability("takesScreenshot", true);
				objCapabilities.setCapability("handlesAlerts", true);
				objCapabilities.setCapability("cssSelectorsEnabled", true);
				objCapabilities.setCapability("databaseEnabled", true);
				objCapabilities.setCapability("platform", CommonVariables.PlatformName.get().toUpperCase());
				objCapabilities.setCapability("browserName", "safari");
				objCapabilities.setCapability("nativeEvents", true);
				objCapabilities.setCapability("acceptSslCerts", true);
				objCapabilities.setCapability("safariIgnoreFraudWarning", true);
				objCapabilities.setCapability("WarnAboutFraudulentWebsite", false);
				if (locationServiceEnabled != null) {
					objCapabilities.setCapability("locationContextEnabled", locationServiceEnabled);
				}
				objCapabilities.setCapability("username", ConfigManager.getProperties().getProperty("sauceLabUser"));
				objCapabilities.setCapability("accessKey", ConfigManager.getProperties().getProperty("sauceLabKey"));
				if (locationServiceEnabled != null) {
					objCapabilities.setCapability("locationContextEnabled", locationServiceEnabled);
				}
				SafariOptions.fromCapabilities(objCapabilities);
				try {
					this.setBrowserProxy();
					if (ConfigManager.getProperties().getProperty("seleniumGrid").toLowerCase().trim()
							.contains("true")) {
						rdriver = null;
						rdriver = new RemoteWebDriver(new URL(
								"http://" + ConfigManager.getProperties().getProperty("gridHubIP") + ":4444/wd/hub"),
								objCapabilities);
						driver = new EventFiringWebDriver(rdriver);
						eventListener = null;
						eventListener = new WebDriverListener(driver);
						driver.register(eventListener);
					} else if (ConfigManager.getProperties().getProperty("cloudExecution").toLowerCase().trim()
							.contains("true")) {
						rdriver = null;
						rdriver = new RemoteWebDriver(new URL(
								"http://" + ConfigManager.getProperties().getProperty("sauceLabURL") + ":80/wd/hub"),
								objCapabilities);
						driver = new EventFiringWebDriver(rdriver);
						eventListener = null;
						eventListener = new WebDriverListener(driver);
						driver.register(eventListener);
					} else {
						String SafariDriverPath = System.getProperty("user.dir").replace("\\", "/")
								+ "/Tool/SafariDriver/SafariDriver.safariextz";
						System.setProperty("webdriver.safari.driver", SafariDriverPath);
						System.setProperty("webdriver.safari.noinstall", "true");
						this.wd = null;
						this.wd = new SafariDriver(safariOptions);
						this.driver = new EventFiringWebDriver(wd);
						eventListener = null;
						eventListener = new WebDriverListener(this.driver);
						this.driver.register(eventListener);
					}
				} catch (Exception e1) {
					if (e1.getClass().toString().contains("UnreachableBrowserException")
							|| e1.getClass().toString().toLowerCase().contains("timeoutException")) {
						System.out.println("org.openqa.selenium.remote.UnreachableBrowserException occured");
						KillWindowProcess("Safari");
						try {
							Thread.sleep(22000);
						} catch (InterruptedException e2) {
						}
						if (ConfigManager.getProperties().getProperty("seleniumGrid").toLowerCase().trim()
								.contains("true")) {
							rdriver = null;
							rdriver = new RemoteWebDriver(new URL("http://"
									+ ConfigManager.getProperties().getProperty("SeleniumGridHubIP") + ":4444/wd/hub"),
									objCapabilities);
							this.driver = new EventFiringWebDriver(rdriver);
							eventListener = null;
							eventListener = new WebDriverListener(driver);
							this.driver.register(eventListener);
						} else if (ConfigManager.getProperties().getProperty("cloudExecution").toLowerCase().trim()
								.contains("true")) {
							rdriver = null;
							rdriver = new RemoteWebDriver(new URL("http://"
									+ ConfigManager.getProperties().getProperty("sauceLabURL") + ":80/wd/hub"),
									objCapabilities);
							this.driver = new EventFiringWebDriver(rdriver);
							eventListener = null;
							eventListener = new WebDriverListener(driver);
							this.driver.register(eventListener);
						} else {
							wd = null;
							wd = new SafariDriver(safariOptions);
							this.driver = new EventFiringWebDriver(wd);
							eventListener = null;
							eventListener = new WebDriverListener(driver);
							this.driver.register(eventListener);
						}
					} else {
						throw e1;
					}
				}
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e2) {
					e2.printStackTrace();
				}
				this.driver.manage().window().maximize();
				suppressCredential();
				break;
			case "windows-internet_explorer":
			case "windows-internet explorer":
			case "windows-Internet Explorer":
			case "windows-ie":
				if (ConfigManager.getProperties().getProperty("KillDriverBinaries").toLowerCase().trim()
						.contains("yes")) {
					KillWindowProcess("IEDriverServer.exe");
				}
				objCapabilities = new DesiredCapabilities();
				objCapabilities = DesiredCapabilities.internetExplorer();
				objCapabilities.setCapability("platform", CommonVariables.PlatformName.get().toUpperCase());
				objCapabilities.setCapability("username", ConfigManager.getProperties().getProperty("sauceLabUser"));
				objCapabilities.setCapability("accessKey", ConfigManager.getProperties().getProperty("sauceLabKey"));
				objCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
						true);
				objCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				objCapabilities.setJavascriptEnabled(true);
				objCapabilities.setCapability("requireWindowFocus", true);
				objCapabilities.setCapability("enablePersistentHover", false);
				/*
				 * objCapabilities.setCapability("ignoreZoomSetting", true);
				 * objCapabilities.setCapability("nativeEvents",false);
				 */
				objCapabilities.setCapability(InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR, true);
				objCapabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
				this.setBrowserProxy();
				if (ConfigManager.getProperties().getProperty("seleniumGrid").toLowerCase().trim().contains("true")) {
					rdriver = null;
					rdriver = new RemoteWebDriver(new URL(
							"http://" + ConfigManager.getProperties().getProperty("gridHubIP") + ":4444/wd/hub"),
							objCapabilities);
					driver = new EventFiringWebDriver(rdriver);
					eventListener = null;
					eventListener = new WebDriverListener(driver);
					driver.register(eventListener);
				} else if (ConfigManager.getProperties().getProperty("cloudExecution").toLowerCase().trim()
						.contains("true")) {
					rdriver = null;
					rdriver = new RemoteWebDriver(new URL(
							"http://" + ConfigManager.getProperties().getProperty("sauceLabURL") + ":80/wd/hub"),
							objCapabilities);
					driver = new EventFiringWebDriver(rdriver);
					eventListener = null;
					eventListener = new WebDriverListener(driver);
					driver.register(eventListener);
				} else {
					File fileIE = new File("Tool/IEDrivers/IEDriverServer.exe");
					System.setProperty("webdriver.ie.driver", fileIE.getAbsolutePath());
					this.wd = null;
					this.wd = new InternetExplorerDriver(objCapabilities);
					this.driver = new EventFiringWebDriver(wd);
					eventListener = null;
					eventListener = new WebDriverListener(this.driver);
					this.driver.register(eventListener);
				}
				this.driver.manage().window().maximize();
				this.driver.manage().deleteAllCookies();
				/*
				 * process = Runtime .getRuntime() .exec(
				 * "RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 255");
				 * process.waitFor(); Thread.sleep(3000);
				 */
				suppressCredential();
				// code to start windows batch file for windows focus during
				// execution
				try {
					if (System.getProperty("os.name").toLowerCase().contains("windows")) {
						String command = "cmd /c start  " + " CALL " + "\"" + System.getProperty("user.dir")
								+ "\\Drivers\\MakeDisplayActive.bat" + "\"";
						String command1 = "cmd /c start  " + " CALL " + "\"" + System.getProperty("user.dir")
								+ "\\Drivers\\EnableDisplayContentIESetting.bat" + "\"";
						String command2 = "cmd /c start  " + " CALL " + "\"" + System.getProperty("user.dir")
								+ "\\Drivers\\ReturnSessionToConsole.bat" + "\"";
						Runtime runtime = Runtime.getRuntime();
						@SuppressWarnings("unused")
						Process pr = runtime.exec(command);
						@SuppressWarnings("unused")
						Process pr1 = runtime.exec(command1);
						@SuppressWarnings("unused")
						Process pr2 = runtime.exec(command2);
					} else {
						System.out.println(
								"****** Batch file to make display active is not executed because this is not the windows platform. ******");
					}
				} catch (Exception e) {
					System.out.println("Unable to execute windows focus batch file due to exception :- "
							+ e.getLocalizedMessage());
				}
				// code to handle security dialog in IE browser
				if (CommonVariables.CommonDriver.get().getPageSource().trim()
						.contains("There is a problem with this website")) {
					CommonVariables.CommonDriver.get().navigate()
							.to("javascript:document.getElementById('overridelink').click()");
					try {
						Thread.sleep(Long.parseLong("3000"));
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;
			case "windows-firefox":
				KillWindowProcess("geckodriver.exe");
				objCapabilities = new DesiredCapabilities();
				objCapabilities = DesiredCapabilities.firefox();
				objCapabilities.setCapability("marionette", false);
				objCapabilities.setCapability("platform", CommonVariables.PlatformName.get().toUpperCase());
				objCapabilities.setCapability("browserName", "firefox");
				objCapabilities.setCapability("username", ConfigManager.getProperties().getProperty("sauceLabUser"));
				objCapabilities.setCapability("accessKey", ConfigManager.getProperties().getProperty("sauceLabKey"));
				if (this.locationServiceEnabled == true) {
					firefoxProfile = new FirefoxProfile(new File(System.getProperty("user.dir").replace("\\", "/")
							+ "/Tool/BrowserProfiles/Firefox/" + firefoxProfilePath));
					if (ConfigManager.getProperties().getProperty("seleniumGrid").toLowerCase().trim()
							.contains("true")) {
						firefoxProfile = new FirefoxProfile(new File(System.getProperty("user.dir").replace("\\", "/")
								+ "/Tool/BrowserProfiles/Firefox/" + firefoxProfilePath));
					}
					firefoxProfile.setPreference("geo.enabled", true);
					firefoxProfile.setPreference("extensions.ui.locale.hidden", true);
					firefoxProfile.setPreference("intl.accept_languages",
							ConfigManager.getProperties().getProperty("lacaleLang"));
					firefoxProfile.setPreference("extensions.enabledAddons", "georelocate%40netzgewitter.com:0.2.3");
					firefoxProfile.setPreference("extensions.georelocate.latitude",
							ConfigManager.getProperties().getProperty("latitude"));
					firefoxProfile.setPreference("extensions.georelocate.longitude",
							ConfigManager.getProperties().getProperty("longitude"));
					firefoxProfile.setPreference("extensions.georelocate@netzgewitter.com.install-event-fired", true);
					firefoxProfile.setPreference("browser.cache.disk.enable", false);
					firefoxProfile.setPreference("browser.cache.memory.enable", false);
					firefoxProfile.setPreference("browser.cache.offline.enable", false);
					firefoxProfile.setPreference("network.http.use-cache", false);
					firefoxProfile.setPreference("browser.download.folderList", 2);
					firefoxProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
					firefoxProfile.setPreference("browser.download.manager.showWhenStarting", false);
					firefoxProfile.setPreference("browser.download.dir", System.getProperty("user.dir") + "\\testdata");
					firefoxProfile.setPreference("browser.download.defaultFolder",
							System.getProperty("user.dir") + "\\testdata");
					firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk",
							"text/anytext ,text/plain,text/html,application/plain");
					firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/vnd.ms-excel");
					firefoxProfile.setPreference("pdfjs.disabled", true);
					firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf");
					firefoxProfile.setPreference("browser.download.manager.closeWhenDone", true);
					firefoxProfile.setPreference("browser.download.manager.useWindow", false);
					firefoxProfile.setPreference("browser.download.manager.alertOnExeOpen", false);
					ProfilesIni allProfiles = new ProfilesIni();
					allProfiles.getProfile("");
				} else {
					firefoxProfile = new FirefoxProfile();
					firefoxProfile.setPreference("geo.enabled", false);
				}
				firefoxProfile.setAcceptUntrustedCertificates(true);
				firefoxProfile.setEnableNativeEvents(true);
				firefoxProfile.setAssumeUntrustedCertificateIssuer(true);
				objCapabilities.setCapability(FirefoxDriver.PROFILE, firefoxProfile);
				this.setBrowserProxy();
				if (ConfigManager.getProperties().getProperty("seleniumGrid").toLowerCase().trim().contains("true")) {
					rdriver = null;
					rdriver = new RemoteWebDriver(new URL(
							"http://" + ConfigManager.getProperties().getProperty("gridHubIP") + ":4444/wd/hub"),
							objCapabilities);
					driver = new EventFiringWebDriver(rdriver);
					eventListener = null;
					eventListener = new WebDriverListener(driver);
					driver.register(eventListener);
				} else if (ConfigManager.getProperties().getProperty("cloudExecution").toLowerCase().trim()
						.contains("true")) {
					rdriver = null;
					rdriver = new RemoteWebDriver(new URL(
							"http://" + ConfigManager.getProperties().getProperty("sauceLabURL") + ":80/wd/hub"),
							objCapabilities);
					driver = new EventFiringWebDriver(rdriver);
					eventListener = null;
					eventListener = new WebDriverListener(driver);
					driver.register(eventListener);
				} else {
					DriverPath = System.getProperty("user.dir").replace("\\", "/")
							+ "/Tool/FirefoxDrivers/windows/geckodriver.exe";
					System.setProperty("webdriver.gecko.driver", DriverPath);
					this.wd = null;
					this.wd = new FirefoxDriver(objCapabilities);
					this.driver = new EventFiringWebDriver(wd);
					eventListener = null;
					eventListener = new WebDriverListener(this.driver);
					this.driver.register(eventListener);
				}
				this.driver.getWrappedDriver().manage().deleteAllCookies();
				/*
				 * process = Runtime .getRuntime() .exec(
				 * "RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 255");
				 * process.waitFor(); Thread.sleep(3000);
				 */
				suppressCredential();
				break;
			case "mac-firefox":
				objCapabilities = new DesiredCapabilities();
				objCapabilities = DesiredCapabilities.firefox();
				objCapabilities.setCapability("marionette", false);
				objCapabilities.setCapability("platform", "MAC");
				objCapabilities.setCapability("browserName", "firefox");
				objCapabilities.setCapability("username", ConfigManager.getProperties().getProperty("sauceLabUser"));
				objCapabilities.setCapability("accessKey", ConfigManager.getProperties().getProperty("sauceLabKey"));
				firefoxProfile = new FirefoxProfile();
				firefoxProfile.setPreference("geo.enabled", false);
				firefoxProfile.setPreference("extensions.ui.locale.hidden", true);
				firefoxProfile.setPreference("intl.accept_languages",
						ConfigManager.getProperties().getProperty("lacaleLang"));
				firefoxProfile.setAcceptUntrustedCertificates(true);
				firefoxProfile.setEnableNativeEvents(true);
				firefoxProfile.setPreference("browser.cache.disk.enable", false);
				firefoxProfile.setPreference("browser.cache.memory.enable", false);
				firefoxProfile.setPreference("browser.cache.offline.enable", false);
				firefoxProfile.setPreference("network.http.use-cache", false);
				firefoxProfile.setPreference("browser.download.folderList", 2);
				firefoxProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
				firefoxProfile.setPreference("browser.download.manager.showWhenStarting", false);
				firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk",
						"text/anytext ,text/plain,text/html,application/plain");
				firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/vnd.ms-excel");
				firefoxProfile.setPreference("pdfjs.disabled", true);
				firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf");
				firefoxProfile.setPreference("browser.download.manager.closeWhenDone", true);
				firefoxProfile.setPreference("browser.download.manager.useWindow", false);
				firefoxProfile.setPreference("browser.download.manager.alertOnExeOpen", false);
				objCapabilities.setCapability(FirefoxDriver.PROFILE, firefoxProfile);
				this.setBrowserProxy();
				if (ConfigManager.getProperties().getProperty("seleniumGrid").toLowerCase().trim().contains("true")) {
					rdriver = null;
					rdriver = new RemoteWebDriver(new URL(
							"http://" + ConfigManager.getProperties().getProperty("gridHubIP") + ":4444/wd/hub"),
							objCapabilities);
					driver = new EventFiringWebDriver(rdriver);
					eventListener = null;
					eventListener = new WebDriverListener(driver);
					driver.register(eventListener);
				} else if (ConfigManager.getProperties().getProperty("cloudExecution").toLowerCase().trim()
						.contains("true")) {
					rdriver = null;
					rdriver = new RemoteWebDriver(new URL(
							"http://" + ConfigManager.getProperties().getProperty("sauceLabURL") + ":80/wd/hub"),
							objCapabilities);
					driver = new EventFiringWebDriver(rdriver);
					eventListener = null;
					eventListener = new WebDriverListener(driver);
					driver.register(eventListener);
				} else {
					//DriverPath = System.getProperty("user.dir").replace("\\", "/")
						//	+ "/Tool/FirefoxDrivers/mac/geckodriver";
					//System.setProperty("webdriver.gecko.driver", DriverPath);
					this.wd = null;
					this.wd = new FirefoxDriver(objCapabilities);
					this.driver = new EventFiringWebDriver(wd);
					eventListener = null;
					eventListener = new WebDriverListener(this.driver);
					this.driver.register(eventListener);
				}
				driver.getWrappedDriver().manage().deleteAllCookies();
				suppressCredential();
				break;
			case "linux-firefox":
				objCapabilities = new DesiredCapabilities();
				objCapabilities = DesiredCapabilities.firefox();
				objCapabilities.setCapability("marionette", false);
				objCapabilities.setCapability("platform", "LINUX");
				objCapabilities.setCapability("browserName", "firefox");
				objCapabilities.setCapability("username", ConfigManager.getProperties().getProperty("sauceLabUser"));
				objCapabilities.setCapability("accessKey", ConfigManager.getProperties().getProperty("sauceLabKey"));
				firefoxProfile = new FirefoxProfile();
				firefoxProfile.setPreference("geo.enabled", false);
				firefoxProfile.setAcceptUntrustedCertificates(true);
				firefoxProfile.setEnableNativeEvents(true);
				firefoxProfile.setPreference("extensions.ui.locale.hidden", true);
				firefoxProfile.setPreference("intl.accept_languages",
						ConfigManager.getProperties().getProperty("lacaleLang"));
				firefoxProfile.setPreference("browser.cache.disk.enable", false);
				firefoxProfile.setPreference("browser.cache.memory.enable", false);
				firefoxProfile.setPreference("browser.cache.offline.enable", false);
				firefoxProfile.setPreference("network.http.use-cache", false);
				firefoxProfile.setPreference("browser.download.folderList", 2);
				firefoxProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
				firefoxProfile.setPreference("browser.download.manager.showWhenStarting", false);
				firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk",
						"text/anytext ,text/plain,text/html,application/plain");
				firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/vnd.ms-excel");
				firefoxProfile.setPreference("pdfjs.disabled", true);
				firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf");
				firefoxProfile.setPreference("browser.download.manager.closeWhenDone", true);
				firefoxProfile.setPreference("browser.download.manager.useWindow", false);
				firefoxProfile.setPreference("browser.download.manager.alertOnExeOpen", false);
				objCapabilities.setCapability(FirefoxDriver.PROFILE, firefoxProfile);
				objCapabilities.setCapability(FirefoxDriver.PROFILE, firefoxProfile);
				this.setBrowserProxy();
				if (ConfigManager.getProperties().getProperty("seleniumGrid").toLowerCase().trim().contains("true")) {
					rdriver = null;
					rdriver = new RemoteWebDriver(new URL(
							"http://" + ConfigManager.getProperties().getProperty("gridHubIP") + ":4444/wd/hub"),
							objCapabilities);
					driver = new EventFiringWebDriver(rdriver);
					eventListener = null;
					eventListener = new WebDriverListener(driver);
					driver.register(eventListener);
				} else if (ConfigManager.getProperties().getProperty("cloudExecution").toLowerCase().trim()
						.contains("true")) {
					rdriver = null;
					rdriver = new RemoteWebDriver(
							new URL("http://" + ConfigManager.getProperties().getProperty("sauceLabURL") + "/wd	/hub"),
							objCapabilities);
					driver = new EventFiringWebDriver(rdriver);
					eventListener = null;
					eventListener = new WebDriverListener(driver);
					driver.register(eventListener);
				} else {
					DriverPath = System.getProperty("user.dir").replace("\\", "/")
							+ "/Tool/FirefoxDrivers/linux/geckodriver";
					System.setProperty("webdriver.gecko.driver", DriverPath);
					this.wd = null;
					this.wd = new FirefoxDriver(objCapabilities);
					this.driver = new EventFiringWebDriver(wd);
					eventListener = null;
					eventListener = new WebDriverListener(this.driver);
					this.driver.register(eventListener);
				}
				driver.getWrappedDriver().manage().deleteAllCookies();
				suppressCredential();
				break;
			case "mac-iphone-device":
			case "mac-iphone_device":
				objCapabilities = new DesiredCapabilities().iphone();
				objCapabilities.setCapability("deviceName", "iPhone");
				objCapabilities.setCapability("version", ConfigManager.getProperties().getProperty("Version"));
				objCapabilities.setCapability("launchTimeout", "300000");
				objCapabilities.setCapability("newCommandTimeout", "600");
				objCapabilities.setCapability("platformVersion",
						ConfigManager.getProperties().getProperty("macPlatformVersion"));
				objCapabilities.setCapability("udid", ConfigManager.getProperties().getProperty("iphoneUdId"));
				objCapabilities.setCapability("username", ConfigManager.getProperties().getProperty("sauceLabUser"));
				objCapabilities.setCapability("accessKey", ConfigManager.getProperties().getProperty("sauceLabKey"));
				File file = new File(ConfigManager.getProperties().getProperty("safariLuancherPath"));
				objCapabilities.setCapability("app", file.getAbsolutePath());
				try {
					// this.setBrowserProxy();
					if (ConfigManager.getProperties().getProperty("seleniumGrid").toLowerCase().trim()
							.contains("true")) {
						rdriver = null;
						rdriver = new RemoteWebDriver(new URL(
								"http://" + ConfigManager.getProperties().getProperty("gridHubIP") + ":4444/wd/hub"),
								objCapabilities);
						Thread.sleep(20000);
						for (String winHandle : this.driver.getWindowHandles()) {
							this.driver.switchTo().window(winHandle);
						}
						this.driver = new EventFiringWebDriver(rdriver);
						eventListener = null;
						eventListener = new WebDriverListener(driver);
						driver.register(eventListener);
						break;
					} else if (ConfigManager.getProperties().getProperty("cloudExecution").toLowerCase().trim()
							.contains("true")) {
						rdriver = null;
						rdriver = new RemoteWebDriver(new URL(
								"http://" + ConfigManager.getProperties().getProperty("sauceLabURL") + ":80/wd/hub"),
								objCapabilities);
						Thread.sleep(20000);
						for (String winHandle : this.driver.getWindowHandles()) {
							this.driver.switchTo().window(winHandle);
						}
						this.driver = new EventFiringWebDriver(rdriver);
						eventListener = null;
						eventListener = new WebDriverListener(driver);
						driver.register(eventListener);
						break;
					} else {
						rdriver = null;
						this.appiumDriver = new IOSDriver(
								new URL("http://" + ConfigManager.getProperties().getProperty("appiumServerIP") + ":"
										+ ConfigManager.getProperties().getProperty("appiumPort") + "/wd/hub"),
								objCapabilities);
						this.rdriver = appiumDriver;
						Thread.sleep(20000);
						for (String winHandle : this.driver.getWindowHandles()) {
							this.driver.switchTo().window(winHandle);
						}
						this.driver = new EventFiringWebDriver(rdriver);
						eventListener = null;
						eventListener = new WebDriverListener(driver);
						driver.register(eventListener);
					}
					try {
						Thread.sleep(2500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					suppressCredential();
					break;
				} catch (MalformedURLException e) {
					e.printStackTrace();
					break;
				}
			case "mac-iphone-simulator":
			case "mac-iphone_simulator":
				objCapabilities = new DesiredCapabilities();
				objCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
				objCapabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Safari");
				objCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,
						ConfigManager.getProperties().getProperty("Version").toUpperCase().trim());
				objCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME,
						ConfigManager.getProperties().getProperty("iPhoneDeviceName").trim());
				objCapabilities.setCapability("locale", "US");
				objCapabilities.setCapability("deviceReadyTimeout", "450");
				objCapabilities.setCapability("newCommandTimeout", "600000");
				objCapabilities.setCapability("safariIgnoreFraudWarning", true);
				objCapabilities.setCapability("safariAllowPopups", true);
				objCapabilities.setCapability("fullReset", false);
				objCapabilities.setCapability("autoLaunch", true);
				objCapabilities.setCapability("autoAcceptAlerts", true);
				objCapabilities.setCapability("username", ConfigManager.getProperties().getProperty("sauceLabUser"));
				objCapabilities.setCapability("accessKey", ConfigManager.getProperties().getProperty("sauceLabKey"));
				if (locationServiceEnabled != null) {
					objCapabilities.setCapability("locationServicesEnabled", locationServiceEnabled);
					objCapabilities.setCapability("useLocationServices", true);
					objCapabilities.setCapability("bundleId", "com.apple.mobilesafari.app");
					objCapabilities.setCapability("locationServicesAuthorized", true);
				}
				objCapabilities.setCapability("orientation", ConfigManager.getProperties().getProperty("Orientation"));
				try {
					// this.setBrowserProxy();
					if (ConfigManager.getProperties().getProperty("seleniumGrid").toLowerCase().trim()
							.contains("true")) {
						rdriver = null;
						rdriver = new RemoteWebDriver(new URL(
								"http://" + ConfigManager.getProperties().getProperty("gridHubIP") + ":4444/wd/hub"),
								objCapabilities);
						this.driver = new EventFiringWebDriver(rdriver);
						eventListener = null;
						eventListener = new WebDriverListener(driver);
						driver.register(eventListener);
					} else if (ConfigManager.getProperties().getProperty("cloudExecution").toLowerCase().trim()
							.contains("true")) {
						rdriver = null;
						rdriver = new RemoteWebDriver(new URL(
								"http://" + ConfigManager.getProperties().getProperty("sauceLabURL") + ":80/wd/hub"),
								objCapabilities);
						this.driver = new EventFiringWebDriver(rdriver);
						eventListener = null;
						eventListener = new WebDriverListener(driver);
						driver.register(eventListener);
						break;
					} else {
						rdriver = null;
						this.appiumDriver = new IOSDriver(
								new URL("http://" + ConfigManager.getProperties().getProperty("appiumServerIP") + ":"
										+ ConfigManager.getProperties().getProperty("appiumPort") + "/wd/hub"),
								objCapabilities);
						this.rdriver = appiumDriver;
						Thread.sleep(20000);
						for (String winHandle : this.driver.getWindowHandles()) {
							this.driver.switchTo().window(winHandle);
						}
						this.driver = new EventFiringWebDriver(rdriver);
						eventListener = null;
						eventListener = new WebDriverListener(driver);
						driver.register(eventListener);
					}
					try {
						Thread.sleep(2500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					suppressCredential();
					break;
				} catch (MalformedURLException e) {
					e.printStackTrace();
					break;
				}
			case "mac-ipad_safari":
			case "mac-ipad-simulator":
			case "mac-ipad_simulator":
				// Appium iPad Driver
				objCapabilities = new DesiredCapabilities();
				objCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
				objCapabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Safari");
				objCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,
						ConfigManager.getProperties().getProperty("Version").toUpperCase().trim());
				objCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME,
						ConfigManager.getProperties().getProperty("iPadDeviceName").trim());
				objCapabilities.setCapability("locale", "US");
				objCapabilities.setCapability("deviceReadyTimeout", "450"); // Timeout
				objCapabilities.setCapability("newCommandTimeout", "600000");
				objCapabilities.setCapability("safariIgnoreFraudWarning", true);
				objCapabilities.setCapability("safariAllowPopups", true);
				objCapabilities.setCapability("sendKeyStrategy", "grouped");
				objCapabilities.setCapability("fullReset", false); // for ios
				objCapabilities.setCapability("autoLaunch", true);
				objCapabilities.setCapability("autoAcceptAlerts", true);
				objCapabilities.setCapability("username", ConfigManager.getProperties().getProperty("sauceLabUser"));
				objCapabilities.setCapability("accessKey", ConfigManager.getProperties().getProperty("sauceLabKey"));
				if (locationServiceEnabled != null) {
					objCapabilities.setCapability("locationServicesEnabled", locationServiceEnabled);
					objCapabilities.setCapability("useLocationServices", true);
					objCapabilities.setCapability("bundleId", "com.apple.mobilesafari.app");
					objCapabilities.setCapability("locationServicesAuthorized", true);
				}
				objCapabilities.setCapability("orientation", ConfigManager.getProperties().getProperty("Orientation"));
				try {
					// this.setBrowserProxy();
					if (ConfigManager.getProperties().getProperty("seleniumGrid").toLowerCase().trim()
							.contains("true")) {
						rdriver = null;
						rdriver = new RemoteWebDriver(new URL(
								"http://" + ConfigManager.getProperties().getProperty("gridHubIP") + ":4444/wd/hub"),
								objCapabilities);
						this.driver = new EventFiringWebDriver(rdriver);
						eventListener = null;
						eventListener = new WebDriverListener(driver);
						driver.register(eventListener);
					} else if (ConfigManager.getProperties().getProperty("cloudExecution").toLowerCase().trim()
							.contains("true")) {
						rdriver = null;
						rdriver = new RemoteWebDriver(new URL(
								"http://" + ConfigManager.getProperties().getProperty("sauceLabURL") + ":80/wd/hub"),
								objCapabilities);
						this.driver = new EventFiringWebDriver(rdriver);
						eventListener = null;
						eventListener = new WebDriverListener(driver);
						driver.register(eventListener);
						break;
					} else {
						rdriver = null;
						this.appiumDriver = new IOSDriver(
								new URL("http://" + ConfigManager.getProperties().getProperty("appiumServerIP") + ":"
										+ ConfigManager.getProperties().getProperty("appiumPort") + "/wd/hub"),
								objCapabilities);
						this.rdriver = appiumDriver;
						Thread.sleep(20000);
						for (String winHandle : this.driver.getWindowHandles()) {
							this.driver.switchTo().window(winHandle);
						}
						this.driver = new EventFiringWebDriver(rdriver);
						eventListener = null;
						eventListener = new WebDriverListener(driver);
						driver.register(eventListener);
					}
					try {
						Thread.sleep(2500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					suppressCredential();
					break;
				} catch (MalformedURLException e) {
					e.printStackTrace();
					break;
				}
			case "windows-android-android_device":
				objCapabilities = new DesiredCapabilities().iphone();
				objCapabilities.setCapability("deviceName", "iPhone");
				objCapabilities.setCapability("version", ConfigManager.getProperties().getProperty("Version"));
				objCapabilities.setCapability("launchTimeout", "300000");
				objCapabilities.setCapability("newCommandTimeout", "600");
				objCapabilities.setCapability("platformVersion",
						ConfigManager.getProperties().getProperty("macPlatformVersion"));
				objCapabilities.setCapability("udid", ConfigManager.getProperties().getProperty("iphoneUdId"));
				objCapabilities.setCapability("username", ConfigManager.getProperties().getProperty("sauceLabUser"));
				objCapabilities.setCapability("accessKey", ConfigManager.getProperties().getProperty("sauceLabKey"));
				File file1 = new File(ConfigManager.getProperties().getProperty("safariLuancherPath"));
				objCapabilities.setCapability("app", file1.getAbsolutePath());
				try {
					// this.setBrowserProxy();
					if (ConfigManager.getProperties().getProperty("seleniumGrid").toLowerCase().trim()
							.contains("true")) {
						rdriver = null;
						rdriver = new RemoteWebDriver(new URL(
								"http://" + ConfigManager.getProperties().getProperty("gridHubIP") + ":4444/wd/hub"),
								objCapabilities);
						Thread.sleep(20000);
						for (String winHandle : this.driver.getWindowHandles()) {
							this.driver.switchTo().window(winHandle);
						}
						this.driver = new EventFiringWebDriver(rdriver);
						eventListener = null;
						eventListener = new WebDriverListener(driver);
						driver.register(eventListener);
						break;
					} else if (ConfigManager.getProperties().getProperty("cloudExecution").toLowerCase().trim()
							.contains("true")) {
						rdriver = null;
						rdriver = new RemoteWebDriver(new URL(
								"http://" + ConfigManager.getProperties().getProperty("sauceLabURL") + ":80/wd/hub"),
								objCapabilities);
						Thread.sleep(20000);
						for (String winHandle : this.driver.getWindowHandles()) {
							this.driver.switchTo().window(winHandle);
						}
						this.driver = new EventFiringWebDriver(rdriver);
						eventListener = null;
						eventListener = new WebDriverListener(driver);
						driver.register(eventListener);
						break;
					} else {
						rdriver = null;
						this.appiumDriver = new IOSDriver(
								new URL("http://" + ConfigManager.getProperties().getProperty("appiumServerIP") + ":"
										+ ConfigManager.getProperties().getProperty("appiumPort") + "/wd/hub"),
								objCapabilities);
						this.rdriver = appiumDriver;
						Thread.sleep(20000);
						for (String winHandle : this.driver.getWindowHandles()) {
							this.driver.switchTo().window(winHandle);
						}
						this.driver = new EventFiringWebDriver(rdriver);
						eventListener = null;
						eventListener = new WebDriverListener(driver);
						driver.register(eventListener);
					}
					try {
						Thread.sleep(2500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					suppressCredential();
					break;
				} catch (MalformedURLException e) {
					e.printStackTrace();
					break;
				}
			case "android-chrome":
			case "mac-android-chrome":
			case "mac-android_chrome":
				objCapabilities = null;
				objCapabilities = DesiredCapabilities.chrome();
				chromeoptions = new ChromeOptions();
				chromeoptions.setExperimentalOption("androidPackage", "com.android.chrome");
				objCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeoptions);
				objCapabilities.setCapability("platform", "WINDOWS");
				objCapabilities.setCapability("browserName", "chrome");
				objCapabilities.setCapability("app", "chrome");
				objCapabilities.setCapability("enableHighAccuracy", true);
				objCapabilities.setCapability("launchTimeout", "300000");
				objCapabilities.setCapability("newCommandTimeout", "300");
				objCapabilities.setCapability("version", "android");
				objCapabilities.setCapability("deviceName",
						ConfigManager.getProperties().getProperty("androidDeviceName"));
				objCapabilities.setCapability("UDID", ConfigManager.getProperties().getProperty("androidDeviceId"));
				objCapabilities.setCapability("username", ConfigManager.getProperties().getProperty("sauceLabUser"));
				objCapabilities.setCapability("accessKey", ConfigManager.getProperties().getProperty("sauceLabKey"));
				if (osname.toLowerCase().contains("windows") || osname.toLowerCase().contains("android")) {
					DriverPath = System.getProperty("user.dir").replace("\\", "/")
							+ "/Tool/chromedrivers/windows/chromedriver.exe";
				}
				System.setProperty("webdriver.chrome.driver", DriverPath);
				// this.setBrowserProxy();
				if (ConfigManager.getProperties().getProperty("seleniumGrid").toLowerCase().trim().contains("true")) {
					rdriver = null;
					rdriver = new RemoteWebDriver(new URL(
							"http://" + ConfigManager.getProperties().getProperty("gridHubIP") + ":4444/wd/hub"),
							objCapabilities);
					this.driver = new EventFiringWebDriver(rdriver);
					eventListener = null;
					eventListener = new WebDriverListener(driver);
					driver.register(eventListener);
				} else if (ConfigManager.getProperties().getProperty("cloudExecution").toLowerCase().trim()
						.contains("true")) {
					rdriver = null;
					rdriver = new RemoteWebDriver(new URL(
							"http://" + ConfigManager.getProperties().getProperty("sauceLabURL") + ":80/wd/hub"),
							objCapabilities);
					this.driver = new EventFiringWebDriver(rdriver);
					eventListener = null;
					eventListener = new WebDriverListener(driver);
					driver.register(eventListener);
				} else {
					this.wd = null;
					this.wd = new ChromeDriver(objCapabilities);
					this.driver = new EventFiringWebDriver(wd);
					eventListener = null;
					eventListener = new WebDriverListener(this.driver);
					this.driver.register(eventListener);
				}
				suppressCredential();
				break;
			case "windows-PhantomJS":
			case "windows-phantomjs":
				if (ConfigManager.getProperties().getProperty("KillDriverBinaries").toLowerCase().trim()
						.contains("yes")) {
					KillWindowProcess("phantomjs.exe");
				}
				String GhostPath = System.getProperty("user.dir").replace("\\", "/")
						+ "/Tool/PhantomJS/ghostdriver-1.1.1/src/main.js";
				prefs = new LoggingPreferences();
				prefs.enable(LogType.BROWSER, Level.ALL);
				objCapabilities = new DesiredCapabilities();
				objCapabilities.setCapability(CapabilityType.LOGGING_PREFS, prefs);
				objCapabilities.setCapability("platform", CommonVariables.PlatformName.get().toUpperCase());
				// objCapabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
				// PhantomJSPath);
				objCapabilities.setCapability(PhantomJSDriverService.PHANTOMJS_GHOSTDRIVER_PATH_PROPERTY, GhostPath);
				// objCapabilities.setCapability(PhantomJSDriverService.PHANTOMJS_GHOSTDRIVER_CLI_ARGS,
				// "");
				objCapabilities.setCapability("browserName", "phantomjs");
				objCapabilities.setJavascriptEnabled(true);
				objCapabilities.setCapability("takesScreenshot", true);
				objCapabilities.setCapability("handlesAlerts", true);
				objCapabilities.setCapability("username", ConfigManager.getProperties().getProperty("sauceLabUser"));
				objCapabilities.setCapability("accessKey", ConfigManager.getProperties().getProperty("sauceLabKey"));
				this.setBrowserProxy();
				if (ConfigManager.getProperties().getProperty("seleniumGrid").toLowerCase().trim().contains("true")) {
					rdriver = null;
					rdriver = new RemoteWebDriver(new URL(
							"http://" + ConfigManager.getProperties().getProperty("gridHubIP") + ":4444/wd/hub"),
							objCapabilities);
					this.driver = new EventFiringWebDriver(rdriver);
					eventListener = null;
					eventListener = new WebDriverListener(driver);
					driver.register(eventListener);
				} else if (ConfigManager.getProperties().getProperty("cloudExecution").toLowerCase().trim()
						.contains("true")) {
					rdriver = null;
					rdriver = new RemoteWebDriver(new URL(
							"http://" + ConfigManager.getProperties().getProperty("sauceLabURL") + ":80/wd/hub"),
							objCapabilities);
					this.driver = new EventFiringWebDriver(rdriver);
					eventListener = null;
					eventListener = new WebDriverListener(driver);
					driver.register(eventListener);
				} else {
					System.setProperty("phantomjs.binary.path",
							System.getProperty("user.dir").replace("\\", "/") + "/Tool/PhantomJS/phantomjs.exe");
					this.wd = null;
					this.wd = new PhantomJSDriver(objCapabilities);
					this.driver = new EventFiringWebDriver(wd);
					eventListener = null;
					eventListener = new WebDriverListener(this.driver);
					this.driver.register(eventListener);
				}
				// this.driver.manage().window().maximize();
				this.driver.getWrappedDriver().manage().deleteAllCookies();
				suppressCredential();
				break;
			case "windows-edge":
				if (ConfigManager.getProperties().getProperty("KillDriverBinaries").toLowerCase().trim()
						.contains("yes")) {
					KillWindowProcess("MicrosoftWebDriver.exe");
				}
				objCapabilities = new DesiredCapabilities();
				objCapabilities.setCapability(CapabilityType.LOGGING_PREFS, prefs);
				objCapabilities = DesiredCapabilities.internetExplorer();
				objCapabilities.setCapability("platform", Platform.WINDOWS);
				objCapabilities.setCapability("browser", "edge");
				objCapabilities.setCapability("version", objCapabilities.getVersion());
				objCapabilities.setCapability("takesScreenshot", true);
				objCapabilities.setCapability("handlesAlerts", true);
				objCapabilities.setCapability("username", ConfigManager.getProperties().getProperty("sauceLabUser"));
				objCapabilities.setCapability("accessKey", ConfigManager.getProperties().getProperty("sauceLabKey"));
				objCapabilities.setJavascriptEnabled(true);
				this.setBrowserProxy();
				if (ConfigManager.getProperties().getProperty("seleniumGrid").toLowerCase().trim().contains("true")) {
					rdriver = null;
					rdriver = new RemoteWebDriver(new URL(
							"http://" + ConfigManager.getProperties().getProperty("gridHubIP") + ":4444/wd/hub"),
							objCapabilities);
					this.driver = new EventFiringWebDriver(rdriver);
					eventListener = null;
					eventListener = new WebDriverListener(driver);
					driver.register(eventListener);
				} else if (ConfigManager.getProperties().getProperty("cloudExecution").toLowerCase().trim()
						.contains("true")) {
					rdriver = null;
					rdriver = new RemoteWebDriver(new URL(
							"http://" + ConfigManager.getProperties().getProperty("sauceLabURL") + ":80/wd/hub"),
							objCapabilities);
					this.driver = new EventFiringWebDriver(rdriver);
					eventListener = null;
					eventListener = new WebDriverListener(driver);
					driver.register(eventListener);
				} else {
					this.wd = null;
					// File fileIE = new File("\\Program Files (x86)\\Microsoft
					// Web Driver\\MicrosoftWebDriver.exe");
					DriverPath = System.getProperty("user.dir").replace("\\", "/")
							+ "/Tool/EdgeDriver/MicrosoftWebDriver.exe";
					System.setProperty("webdriver.edge.driver", DriverPath);// fileIE.getAbsolutePath()
					this.wd = new EdgeDriver();
					this.driver = new EventFiringWebDriver(wd);
					eventListener = null;
					eventListener = new WebDriverListener(this.driver);
					this.driver.register(eventListener);
				}
				this.driver.manage().window().maximize();
				driver.getWrappedDriver().manage().deleteAllCookies();
				suppressCredential();
				break;
			case "windows-opera":
				if (ConfigManager.getProperties().getProperty("KillDriverBinaries").toLowerCase().trim()
						.contains("yes")) {
					KillWindowProcess("operadriver.exe");
				}
				operaOptions = new OperaOptions();
				operaOptions.addArguments("test-type");
				operaOptions.addArguments("disable-session-crashed-bubble");
				if (locationServiceEnabled != null) {
					if (!locationServiceEnabled) {
						Map<String, Object> prefs = new HashMap<String, Object>();
						prefs.put("profile.default_content_settings.geolocation", 2);
						operaOptions.setExperimentalOption("prefs", prefs);
					} else if (locationServiceEnabled) {
						chromeprofilepath = System.getProperty("user.dir").replace("\\", "/")
								+ "/Tool/BrowserProfiles/Chrome/" + chromeProfile;
						if (locationServiceEnabled && ConfigManager.getProperties().getProperty("seleniumGrid")
								.toLowerCase().trim().contains("true"))
							chromeprofilepath = System.getProperty("user.dir").replace("\\", "/")
									+ "/Tool/BrowserProfiles/Chrome/" + chromeProfile;
						operaOptions.addArguments("user-data-dir=" + chromeprofilepath);
					}
				}
				prefs = new LoggingPreferences();
				prefs.enable(LogType.BROWSER, Level.ALL);
				setDownloadPathForFile(downloadedfilepath);
				objCapabilities = new DesiredCapabilities();
				objCapabilities.setCapability(CapabilityType.LOGGING_PREFS, prefs);
				objCapabilities.setCapability(operaOptions.CAPABILITY, operaOptions);
				objCapabilities.setCapability("platform", Platform.WINDOWS);
				objCapabilities.setCapability("browser", "opera");
				objCapabilities.setCapability("version", objCapabilities.getVersion());
				objCapabilities.setCapability("takesScreenshot", true);
				objCapabilities.setCapability("handlesAlerts", true);
				objCapabilities.setCapability("opera.logging.level", Level.CONFIG);
				// objCapabilities.setCapability("opera.logging.file",
				// "/var/log/operadriver.log");
				objCapabilities.setCapability("opera.display", 8);
				objCapabilities.setJavascriptEnabled(true);
				objCapabilities.setCapability("username", ConfigManager.getProperties().getProperty("sauceLabUser"));
				objCapabilities.setCapability("accessKey", ConfigManager.getProperties().getProperty("sauceLabKey"));
				this.setBrowserProxy();
				if (ConfigManager.getProperties().getProperty("seleniumGrid").toLowerCase().trim().contains("true")) {
					rdriver = null;
					rdriver = new RemoteWebDriver(new URL(
							"http://" + ConfigManager.getProperties().getProperty("gridHubIP") + ":4444/wd/hub"),
							objCapabilities);
					this.driver = new EventFiringWebDriver(rdriver);
					eventListener = null;
					eventListener = new WebDriverListener(driver);
					driver.register(eventListener);
				} else if (ConfigManager.getProperties().getProperty("cloudExecution").toLowerCase().trim()
						.contains("true")) {
					rdriver = null;
					rdriver = new RemoteWebDriver(new URL(
							"http://" + ConfigManager.getProperties().getProperty("sauceLabURL") + ":80/wd/hub"),
							objCapabilities);
					this.driver = new EventFiringWebDriver(rdriver);
					eventListener = null;
					eventListener = new WebDriverListener(driver);
					driver.register(eventListener);
				} else {
					if (osname.toLowerCase().contains("windows")) {
						DriverPath = System.getProperty("user.dir").replace("\\", "/")
								+ "/Tool/Operadrivers/windows/operadriver.exe";
					} else {
						DriverPath = System.getProperty("user.dir").replace("\\", "/")
								+ "/Tool/Operadrivers/operadriver";
					}
					System.setProperty("webdriver.opera.driver", DriverPath);
					this.wd = new EdgeDriver();
					this.driver = new EventFiringWebDriver(wd);
					eventListener = null;
					eventListener = new WebDriverListener(this.driver);
					this.driver.register(eventListener);
				}
				this.driver.manage().window().maximize();
				driver.getWrappedDriver().manage().deleteAllCookies();
				process = Runtime.getRuntime().exec("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 255");
				process.waitFor();
				Thread.sleep(3000);
				if (locationServiceEnabled) {
					chromeprofilepath = System.getProperty("user.dir").replace("\\", "/")
							+ "/Tool/BrowserProfiles/Chrome/" + chromeProfile;
					if (locationServiceEnabled && ConfigManager.getProperties().getProperty("seleniumGrid")
							.toLowerCase().trim().contains("true"))
						chromeprofilepath = System.getProperty("user.dir").replace("\\", "/")
								+ "/Tool/BrowserProfiles/Chrome/" + chromeProfile;
					chromeoptions.addArguments("user-data-dir=" + chromeprofilepath);
				}
				suppressCredential();
				break;
			default:
				return null;

			}
			if (ConfigManager.getProperties().getProperty("seleniumGrid").toLowerCase().trim().contains("true")) {
				CommonVariables.gridHubIP.set(GetIPOfSeleniumGridNode(this.driver));
			} else {
				CommonVariables.gridHubIP.set(CommonVariables.MachineHostName.get());
			}

			System.out.println("Successfully Launched the driver: " + browserType + " on the node IP: "
					+ CommonVariables.gridHubIP.get());
			if (browserType.toLowerCase().contains("iphone") || browserType.toLowerCase().contains("ipad")) {
			} else if (ConfigManager.getProperties().getProperty("cloudExecution").toLowerCase().trim()
					.contains("false")) {
				hwndFirstWindow = driver.getWindowHandle();
				arrKnownBrowserHwnd = driver.getWindowHandles();
			}
			/**
			 * If the location service is enabled set the geo location codes
			 * under test
			 */
			if (this.locationServiceEnabled && (ConfigManager.getProperties().getProperty("deviceType").toLowerCase()
					.contains("tablet")
					|| ConfigManager.getProperties().getProperty("deviceType").toLowerCase().contains("iPad")
					|| ConfigManager.getProperties().getProperty("deviceType").toLowerCase().contains("android")
					|| ConfigManager.getProperties().getProperty("deviceType").toLowerCase().contains("iPhone"))) {
				setGeoLocation();
			}
			driverStartfailureCount = 1;
			CommonVariables.setDriver(this.driver);
			return driver;
		} catch (org.openqa.selenium.remote.SessionNotFoundException
				| org.openqa.selenium.remote.UnreachableBrowserException ex) {
			System.out.println(ex.getLocalizedMessage());
			System.out.println(
					"Caught SessionNotFoundException/UnreachableBrowserException/SessionNotCreatedException in Start Driver Method. Try to start one time more.");
			if (driverStartfailureCount == 1) {
				driverStartfailureCount = 2;
				try {
					Thread.sleep(50000);
				} catch (InterruptedException intr) {
				}
				this.driver = (EventFiringWebDriver) instantiateDriver(browserType);
				return driver;
			} else {
				System.out.println(
						"Again Caught UnreachableBrowserException in Start Driver Method. Stopping the execution");
				Assert.fail("Browser Unreachable twice. Exception message: " + ex.getMessage());
				return driver;
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			return driver;
		}
	}

	/**
	 * @author Cigniti
	 * @description : shut down driver :- This method will close all instances
	 *              of all drivers.
	 */
	public void tearDown() {
		try {
			if (driver != null && CommonVariables.DeviceName.get().toLowerCase().contains("chrome")
					&& CommonVariables.PlatformName.get().toLowerCase().contains("windows")) {
				LogEntries logEntries = null;
				try {
					logEntries = driver.manage().logs().get(LogType.BROWSER);
				} catch (Exception e) {
				}
				if (logEntries != null) {
					for (LogEntry entry : logEntries) {
						if ("SEVERE".equals(entry.getLevel().toString())) {
							writeCapturedJSErrors(
									new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
						}
					}
				}
			}
		} catch (Exception e) {
		}
		try {
			try {
				if (rdriver != null) {
					rdriver.quit();
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e1) {
					} catch (IllegalStateException e2) {
					}
				}
			} catch (Exception ex) {
			}

			try {
				if (wd != null) {
					System.out.println("Quitting the driver");
					wd.quit();
					try {
						Thread.sleep(8000);
					} catch (InterruptedException e1) {
					} catch (IllegalStateException e2) {
					}
				}
			} catch (Exception ex) {
			}

			try {
				if (driver != null) {
					driver.quit();
					try {
						Thread.sleep(8000);
					} catch (InterruptedException e1) {
					} catch (IllegalStateException e2) {
					}
				}
			} catch (Exception ex) {
			}

			try {
				CommonVariables.CommonDriver.get().quit();
				CommonVariables.CommonDriver.set(null);
			} catch (Exception e) {
				CommonVariables.CommonDriver.set(null);
			}

			if (ConfigManager.getProperties().getProperty("deviceType").trim().toLowerCase().contains("android")
					|| CommonVariables.DeviceName.get().toLowerCase().contains("safari")
					|| CommonVariables.DeviceName.get().toLowerCase().contains("iphone")) {
				if (driver != null || wd != null || rdriver != null) {
					try {
						Thread.sleep(20000);
					} catch (InterruptedException e1) {
					}
					;
					rdriver = null;
					driver = null;
					wd = null;
				}
			}
		} catch (WebDriverException e) {
			CommonVariables.CommonDriver.set(null);
			try {
				Thread.sleep(2500);
			} catch (InterruptedException e1) {
			} catch (IllegalStateException e2) {
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		CommonVariables.CommonDriver.set(null);
	}

	/**
	 * @author Cigniti
	 * @description : In this method, it will set the proxy for BMP server
	 */
	@SuppressWarnings("rawtypes")
	private void setBrowserProxy() {
		if (ConfigManager.getProperties().getProperty("IsBrowserMobProxyRequire").trim().equalsIgnoreCase("yes")) {
			System.out.println("BMP Proxy will be used for this browser session");
			Proxy proxy = null;
			try {
				// proxy = new Proxy();
				proxy = BMP_Proxy_Controller.Server.seleniumProxy();
				proxy.setAutodetect(false);
				if (ConfigManager.getProperties().getProperty("seleniumGrid").toLowerCase().trim().contains("true")
						|| ConfigManager.getProperties().getProperty("cloudExecution").toLowerCase().trim()
								.contains("true")) {
					String localIP = "";
					Enumeration e = NetworkInterface.getNetworkInterfaces();
					while (e.hasMoreElements()) {
						NetworkInterface n = (NetworkInterface) e.nextElement();
						Enumeration ee = n.getInetAddresses();
						while (ee.hasMoreElements()) {
							InetAddress i = (InetAddress) ee.nextElement();
							if (i.getHostAddress().toString()
									.startsWith(ConfigManager.getProperties().getProperty("proxyIPSeries"))) {
								System.out.println(i.getHostAddress());
								localIP = i.getHostAddress().toString();
								break;
							}
						}
					}
					if (localIP.equals("")) {
						InetAddress addr = InetAddress.getLocalHost();
						String ipAddress = addr.getHostAddress();
						localIP = ipAddress;
					}
					proxy.setHttpProxy(localIP + ":" + CommonVariables.portNumForBMP);
					proxy.setSslProxy(localIP + ":" + CommonVariables.portNumForBMP);
				} else if ((ConfigManager.getProperties().getProperty("isAWSExecution").trim()
						.equalsIgnoreCase("true"))) {
					String AWSBMPmachineIP = ConfigManager.getProperties().getProperty("AWSMachineIP");
					String httpProxy = AWSBMPmachineIP + ":" + CommonVariables.portNumForBMP;
					proxy = new Proxy();
					proxy.setProxyType(Proxy.ProxyType.MANUAL);
					proxy.setHttpProxy(httpProxy);
					proxy.setSslProxy(httpProxy);
					objCapabilities.setCapability(CapabilityType.PROXY, proxy);
					objCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
					System.out.println("BMP proxy for the browser set to: " + httpProxy);
				} else {
					proxy.setHttpProxy("localhost:" + CommonVariables.portNumForBMP);
					proxy.setSslProxy("localhost:" + CommonVariables.portNumForBMP);
				}

				if (CommonVariables.DeviceName.get().toLowerCase().contains("iphone")) {
					objCapabilities.setCapability(MobileCapabilityType.PROXY, proxy);
					objCapabilities.setCapability(MobileCapabilityType.ACCEPT_SSL_CERTS, true);
				} else {
					objCapabilities.setCapability(CapabilityType.PROXY, proxy);
					objCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				}
				System.out.println("setting browser proxy on port:" + CommonVariables.portNumForBMP);
				Thread.sleep(3000);

			} catch (Exception e) {
				System.out.println("Exception while setting the proxy for browser");
				e.printStackTrace();
			}
		}
	}

	/**
	 * @author Cigniti
	 * @description : In this method, it will return the driver information
	 *              like, if android or iOS return mobile else windows on this
	 *              basis of browser selected by the user.
	 * @return
	 */
	public static Map<String, String> GetDriverInfo() {
		Map<String, String> DriverInfo = new HashMap<String, String>();
		WebDriver driver = null;
		if (wd != null) {
			driver = wd;
		} else if (rdriver != null) {
			driver = rdriver;
		} else {
			driver = ActionsLibrary.driver;
		}
		try {
			String DriverType = "";
			String DriverName = "";
			String osName = "";
			if (driver == null || driver.getClass().toString().toLowerCase().contains("androiddriver")
					|| driver.getClass().toString().toLowerCase().contains("iosdriver")
					|| driver.getClass().toString().toLowerCase().contains("remotewebdriver")) {
				try {
					osName = CommonVariables.PlatformName.get();
					DriverName = CommonVariables.DeviceName.get();
				} catch (NullPointerException e) {
				}
				switch (osName.toUpperCase()) {
				case "WINDOWS": {
					if (DriverName.equals("android-chrome")) {
						DriverType = "mobile";
					} else {
						DriverType = "desktop";
					}
				}
					break;

				case "MAC": {
					if (DriverName.toLowerCase().contains("ipad")) {
						DriverType = "tablet";
					} else if (DriverName.toLowerCase().contains("iphone")) {
						DriverType = "mobile";
					} else {
						DriverType = "desktop";
					}
				}
					break;

				case "ANDROID": {
					DriverType = "mobile";
				}
					break;

				default: {
				}
				}
			} else if (driver.getClass().toString().toLowerCase().contains("chrome")) {
				if (driver.toString().contains("chrome on ANDROID")) {
					DriverType = "mobile";
					DriverName = CommonVariables.DeviceName.get();
				} else {
					DriverType = "Desktop";
					DriverName = "Chrome";
				}
			} else if (driver.getClass().toString().toLowerCase().contains("safari")) {
				DriverType = "Desktop";
				DriverName = "Safari";
			} else if (driver.getClass().toString().toLowerCase().contains("firefox")) {
				DriverType = "Desktop";
				DriverName = "Firefox";
			} else if (driver.getClass().toString().toLowerCase().contains("ie")
					|| driver.getClass().toString().toLowerCase().contains("internet_explorer")) {
				DriverType = "Desktop";
				DriverName = "internet_explorer";
			} else if (driver.getClass().toString().toLowerCase().contains("Edge")
					|| driver.getClass().toString().toLowerCase().contains("Microsoft")) {
				DriverType = "Desktop";
				DriverName = "Edge";
			} else if (driver.getClass().toString().toLowerCase().contains("Opera")) {
				DriverType = "Desktop";
				DriverName = "Opera";
			} else if (driver.getClass().toString().toLowerCase().contains("PhantomJS")) {
				DriverType = "Desktop";
				DriverName = "PhantomJS";
			}
			DriverInfo.put("DriverType", DriverType);
			// DriverInfo.put("DriverType", "tablet");
			DriverInfo.put("DriverName", DriverName);
			return DriverInfo;
		} catch (Exception e) {
			return DriverInfo;
		}
	}

	/**
	 * @author Cigniti
	 * @description : write JS Errors
	 * @param errors
	 */
	private void writeCapturedJSErrors(String errors) {
		try {
			// archiveFile();
			String filePath = System.getProperty("user.dir").replace("\\", "/") + "/logs/CapturedJSErrors/jsErrors.txt";
			fw = new FileWriter(filePath, true);
			pw = new PrintWriter(fw);
			pw.write(errors);
			pw.write("\n");
			pw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Cigniti
	 * @description : Archive File
	 * @throws IOException
	 */
	private void archiveFile(String srcFilePath, String destFilePath) throws IOException {
		try {
			File source = new File(srcFilePath);
			File destination = new File(destFilePath);
			FileUtils.copyFile(source, destination);
		} catch (FileNotFoundException e) {
			e.getLocalizedMessage();
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
	public WebElement FindElement(By by, Optional<Long> timeoutInSeconds) {
		long timeout = timeoutInSeconds.isPresent() ? timeoutInSeconds.get() : -1;
		if (timeout == -1) {
			timeout = Long.parseLong(ConfigManager.getProperties().getProperty("globalTimeOut"));
		}
		WebElement webElement = null;
		try {
			AcceptAlert();
			webElement = (new WebDriverWait(driver, timeout)).until(ExpectedConditions.presenceOfElementLocated(by));
			try {
				if (webElement != null || CommonVariables.TestCaseLog.get() != null) {
					extentLogs.pass("Find Element",
							"Successfully find an element on '" + driver.getTitle() + "' page.");
					CommonVariables.TestCaseLog.get()
							.info("Successfully find '" + by + "' element on '" + driver.getTitle() + "' page");
				} else {
					extentLogs.fail("Find Element", "Element not found on '" + driver.getTitle() + "' page.");
					CommonVariables.TestClassLog.get()
							.info("Successfully find '" + by + "' element on '" + driver.getTitle() + "' page");
				}
			} catch (NullPointerException e) {
				extentLogs.error("Find Element", "Element not found on '" + driver.getTitle()
						+ "' page due to exception - " + e.getLocalizedMessage());
				throw new NullPointerException();
			}
			try {
				ScrollToElementVisible(by);
			} catch (org.openqa.selenium.ElementNotVisibleException e) {
				if (CommonVariables.TestCaseLog.get() != null) {
					CommonVariables.TestCaseLog.get()
							.info("Element (" + by + ") is not visible on '" + driver.getTitle() + "' page");
				} else {
					CommonVariables.TestClassLog.get()
							.info("Element (" + by + ") is not visible on '" + driver.getTitle() + "' page");
				}
				extentLogs.error("Find Element", "Element not visible on '" + driver.getTitle()
						+ "' page due to exception - " + e.getLocalizedMessage());
				throw new ElementNotVisibleException("Element not visible.");
			} catch (org.openqa.selenium.WebDriverException e) {
				if (CommonVariables.TestCaseLog.get() != null) {
					CommonVariables.TestCaseLog.get()
							.info("Failed to move Element (" + by + ") on the running driver type.");
				} else {
					CommonVariables.TestClassLog.get()
							.info("Failed to move Element (" + by + ") on the running driver type.");
				}
				extentLogs.error("Find Element",
						"Failed to move to an Element due to exception - " + e.getLocalizedMessage());
				throw new WebDriverException();
			} catch (NullPointerException ex) {
				System.out.println("Getting exception when movetoElement under FindElement method in" + getClass()
						+ " class. Driver info under CommonVariable :" + CommonVariables.getDriver());
				throw new NullPointerException();
			}
			return webElement;
		} catch (Exception ex) {
			if (ex.getCause().toString().contains("SessionNotFoundException")) {
				throw new SessionNotFoundException();
			} else if (ex.getCause().toString().contains("UnhandledAlertException")) {
				if (AcceptAlert()) {
					return driver.findElement(by);
				}
				if (CommonVariables.TestCaseLog.get() != null) {
					CommonVariables.TestCaseLog.get().info("caught 'UnhandledAlertException' exception while finding '"
							+ by + "' element on '" + driver.getTitle() + "' page");
				} else {
					CommonVariables.TestClassLog.get().info("caught 'UnhandledAlertException' exception while finding '"
							+ by + "' element on '" + driver.getTitle() + "' page");
				}
				extentLogs.error("Find Element",
						"caught 'UnhandledAlertException' exception while finding an element on '" + driver.getTitle()
								+ "' page");
				throw new UnhandledAlertException("");
			} else if (ex.getCause().toString().contains("NoSuchElementException")) {
				if (AcceptAlert()) {
					return driver.findElement(by);
				}
				if (CommonVariables.TestCaseLog.get() != null) {
					CommonVariables.TestCaseLog.get().info("No such element(" + by + ") found on '" + driver.getTitle()
							+ "' page at " + driver.getCurrentUrl() + "' url.");
				} else {
					CommonVariables.TestClassLog.get().info("No such element(" + by + ") found on '" + driver.getTitle()
							+ "' page at " + driver.getCurrentUrl() + "' url.");
				}
				extentLogs.error("Find Element", "No such element éxception found on '" + driver.getTitle()
						+ "' page at " + driver.getCurrentUrl() + "' url.");
				throw new NoSuchElementException("No such Element");
			} else if (ex.getCause().toString().contains("TimeoutException")) {
				if (AcceptAlert()) {
					return driver.findElement(by);
				} else {
					if (CommonVariables.TestCaseLog.get() != null) {
						CommonVariables.TestCaseLog.get()
								.info("No such element(" + by + ") found on '" + driver.getTitle() + "' page at "
										+ driver.getCurrentUrl() + "' url within timelimit (" + timeout + ").");
					} else {
						CommonVariables.TestClassLog.get()
								.info("No such element(" + by + ") found on '" + driver.getTitle() + "' page at "
										+ driver.getCurrentUrl() + "' url within timelimit (" + timeout + ").");
					}
					extentLogs.error("Find Element", "No such element exception found on '" + driver.getTitle()
							+ "' page at " + driver.getCurrentUrl() + "' url within timelimit (" + timeout + ").");
				}
				throw new TimeoutException();
			} else if (ex.getCause().toString().contains("StaleElementReferenceException")) {
				if (CommonVariables.TestCaseLog.get() != null) {
					CommonVariables.TestCaseLog.get()
							.info("Info. Caught 'StaleElementReferenceException' exception while finding (" + by
									+ ") element. Class: " + ex.getClass());
				} else {
					CommonVariables.TestClassLog.get()
							.info("Info. Caught 'StaleElementReferenceException' exception while finding (" + by
									+ ") element. Class: " + ex.getClass());
				}
				extentLogs.error("Find Element",
						"Caught 'StaleElementReferenceException' exception while finding an element. Class: "
								+ ex.getClass());
			}
			AcceptAlert();
			try {
				webElement = driver.findElement(by);
				ScrollToElementVisible(by);
				return webElement;
			} catch (Exception e) {
				if (CommonVariables.TestCaseLog.get() != null) {
					CommonVariables.TestCaseLog.get()
							.info("Error. Caught 'StaleElementReferenceException' exception while finding (" + by
									+ ") element. Exception Type: " + e.getClass());
				} else {
					CommonVariables.TestClassLog.get()
							.info("Error. Caught 'StaleElementReferenceException' exception while finding (" + by
									+ ") element. Exception Type: " + e.getClass());
				}
				throw new StaleElementReferenceException("Stale Exception while finding an element.");
			}
		}
	}

	/**
	 * @author Cigniti
	 * @description: click on webElement
	 * @param webElement
	 * @return
	 */
	public boolean Click(WebElement webElement) {
		return Click(webElement, 4);
	}

	/**
	 * @author Cigniti
	 * @description: click on webElement
	 * @param webElement
	 * @return
	 */
	public boolean Click(WebElement webElement, String str) {
		return Click(webElement, 4, str);
	}

	/**
	 * @author Cigniti
	 * @description: click on webElement
	 * @param webElement
	 * @return
	 */
	public boolean Click(By locators, String str) {
		WebElement webElement=driver.findElement(locators);
		return Click(webElement, 4, str);
	}
	
	/**
	 * @author Cigniti
	 * @description : Webdriver and Java Script Click. Wait time in seconds i.e.
	 *              8 for 8 seconds
	 * @param webElement
	 *            and wait time in seconds
	 * @return
	 */
	public boolean Click(WebElement webElement, Integer waitTime) {
		boolean state = false;
		try {
			Thread.sleep(500);
			webElement.click();
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
			extentLogs.error("Click", "Element is not visible.");
			if (CommonVariables.TestCaseLog.get() != null) {
				CommonVariables.TestCaseLog.get().debug("Error: Element (" + webElement + ") is not visible.");
			} else {
				CommonVariables.TestClassLog.get().debug("Error: Element (" + webElement + ") is not visible.");
			}
		} catch (StaleElementReferenceException e2) {
			extentLogs.error("Click", "Caught 'StaleElementReferenceException' exception while clicking on element.");
			if (CommonVariables.TestCaseLog.get() != null) {
				CommonVariables.TestCaseLog.get()
						.debug("Error. Caught 'StaleElementReferenceException' exception while clicking on '"
								+ webElement + "' element.");
			} else {
				CommonVariables.TestClassLog.get()
						.debug("Error. Caught 'StaleElementReferenceException' exception while clicking on '"
								+ webElement + "' element.");
			}
			AcceptAlert();
			// driver.navigate().refresh();
			try {
				if (webElement.isEnabled()) {
					webElement.click();
				}
			} catch (Exception e) {
			}
		} catch (org.openqa.selenium.UnhandledAlertException e3) {
			extentLogs.error("Click", "Error. Caught 'UnhandledAlertException' exception while clicking on element.");
			AcceptAlert();
			try {
				if (webElement.isDisplayed()) {
					webElement.click();
				}
			} catch (Exception e) {
				if (CommonVariables.TestCaseLog.get() != null) {
					CommonVariables.TestCaseLog.get()
							.info("Info. Caught 'UnhandledAlertException' exception while clicking on '" + webElement
									+ "' element.");
				} else {
					CommonVariables.TestClassLog.get()
							.info("Info. Caught 'UnhandledAlertException' exception while clicking on '" + webElement
									+ "' element.");
				}
				e.printStackTrace();
			}
		} catch (NullPointerException e5) {
			extentLogs.error("Click", "Error. Caught 'NullPointerException' exception while clicking element on '"
					+ driver.getTitle() + "'.");
			if (CommonVariables.TestCaseLog.get() != null) {
				CommonVariables.TestCaseLog.get()
						.debug("Info. Caught 'NullPointerException' exception while clicking element on '"
								+ driver.getTitle() + "'.");
			} else {
				CommonVariables.TestClassLog.get()
						.debug("Info. Caught 'NullPointerException' exception while clicking element on '"
								+ driver.getTitle() + "'.");
			}
		} catch (org.openqa.selenium.WebDriverException e6) {
			if (e6.getMessage().toString().toLowerCase().contains("user supplied")) {
				try {
					JavascriptExecutor js = (JavascriptExecutor) (driver);
					js.executeScript("arguments[0].click();", webElement);
					Thread.sleep(8000);
					state = true;
				} catch (Exception e) {
					if (!state) {
						try {
							JavascriptExecutor js = (JavascriptExecutor) (driver);
							js.executeScript("$(arguments[0]).focusin();", webElement);
							Thread.sleep(8000);
							state = true;
						} catch (Exception e1) {
							state = false;
						}
					}
				}
			}
			if (e6.getMessage().toString().toLowerCase().contains("Element is not clickable")) {
				extentLogs.error("Click",
						"Caught exception about 'Element is not clickable' while clicking element on '"
								+ driver.getTitle()
								+ "'. So Scroll Top/Bottom and wait for sometime to make clickable");

				if (CommonVariables.TestCaseLog.get() != null) {
					CommonVariables.TestCaseLog.get()
							.info("Info: Caught exception about 'Element is not clickable' while clicking element on '"
									+ driver.getTitle() + "'. So Scroll Top/Bottom and wait for sometime to make '"
									+ webElement + "' clickable");
				} else {
					CommonVariables.TestClassLog.get()
							.info("Info: Caught exception about 'Element is not clickable' while clicking element on '"
									+ driver.getTitle() + "'. So Scroll Top/Bottom and wait for sometime to make '"
									+ webElement + "' clickable");
				}
			}
			try {
				try {
					if (ScrollToElementVisible(webElement)) {
						try {
							new WebDriverWait(driver, Long.parseLong("2"))
									.until(ExpectedConditions.elementToBeClickable(webElement)).click();
							Thread.sleep(8000);
							return true;
						} catch (Exception ex) {
							try {
								JavascriptExecutor js = (JavascriptExecutor) (CommonVariables.CommonDriver.get());
								js.executeScript("arguments[0].click();", webElement);
							} catch (Exception e) {
								return false;
							}
						}
					}
				} catch (Exception e) {
					return false;
				}
				try {
					ScrollToTop();
					JavascriptExecutor js = (JavascriptExecutor) (CommonVariables.CommonDriver.get());
					js.executeScript("arguments[0].focusin();", webElement);
					new WebDriverWait(driver, Long.parseLong("10"))
							.until(ExpectedConditions.elementToBeClickable(webElement)).click();
					Thread.sleep(8000);
					state = true;
				} catch (Exception e1) {
					if (e6.getMessage().toString().toLowerCase().contains("element is not clickable")) {
						try {
							ScrollToBottom();
							JavascriptExecutor js = (JavascriptExecutor) (CommonVariables.CommonDriver.get());
							js.executeScript("arguments[0].focusin();", webElement);
							new WebDriverWait(driver, Long.parseLong("10"))
									.until(ExpectedConditions.elementToBeClickable(webElement)).click();
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
					Actions clicker = new Actions(CommonVariables.CommonDriver.get());
					clicker.moveToElement(webElement).click().perform();

				} catch (Exception e7) {
					state = false;
					extentLogs.error("Click", "Element is not clickable.");
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

	public boolean Click(WebElement webElement, Integer waitTime, String str) {
		boolean state = false;
		try {
			Thread.sleep(500);
			webElement.click();
			try {
				Thread.sleep(waitTime * 1000);
				extentLogs.pass("Click", "Successfully clicked '" + str + "'");
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
			extentLogs.error("Click", "Element is not visible.");
			if (CommonVariables.TestCaseLog.get() != null) {
				CommonVariables.TestCaseLog.get().debug("Error: Element (" + webElement + ") is not visible.");
			} else {
				CommonVariables.TestClassLog.get().debug("Error: Element (" + webElement + ") is not visible.");
			}
		} catch (StaleElementReferenceException e2) {
			extentLogs.error("Click", "Caught 'StaleElementReferenceException' exception while clicking on element.");
			if (CommonVariables.TestCaseLog.get() != null) {
				CommonVariables.TestCaseLog.get()
						.debug("Error. Caught 'StaleElementReferenceException' exception while clicking on '"
								+ webElement + "' element.");
			} else {
				CommonVariables.TestClassLog.get()
						.debug("Error. Caught 'StaleElementReferenceException' exception while clicking on '"
								+ webElement + "' element.");
			}
			AcceptAlert();
			// driver.navigate().refresh();
			try {
				if (webElement.isEnabled()) {
					webElement.click();
				}
			} catch (Exception e) {
			}
		} catch (org.openqa.selenium.UnhandledAlertException e3) {
			extentLogs.error("Click", "Error. Caught 'UnhandledAlertException' exception while clicking on element.");
			AcceptAlert();
			try {
				if (webElement.isDisplayed()) {
					webElement.click();
				}
			} catch (Exception e) {
				if (CommonVariables.TestCaseLog.get() != null) {
					CommonVariables.TestCaseLog.get()
							.info("Info. Caught 'UnhandledAlertException' exception while clicking on '" + webElement
									+ "' element.");
				} else {
					CommonVariables.TestClassLog.get()
							.info("Info. Caught 'UnhandledAlertException' exception while clicking on '" + webElement
									+ "' element.");
				}
				e.printStackTrace();
			}
		} catch (NullPointerException e5) {
			extentLogs.error("Click", "Error. Caught 'NullPointerException' exception while clicking element on '"
					+ driver.getTitle() + "'.");
			if (CommonVariables.TestCaseLog.get() != null) {
				CommonVariables.TestCaseLog.get()
						.debug("Info. Caught 'NullPointerException' exception while clicking element on '"
								+ driver.getTitle() + "'.");
			} else {
				CommonVariables.TestClassLog.get()
						.debug("Info. Caught 'NullPointerException' exception while clicking element on '"
								+ driver.getTitle() + "'.");
			}
		} catch (org.openqa.selenium.WebDriverException e6) {
			if (e6.getMessage().toString().toLowerCase().contains("user supplied")) {
				try {
					JavascriptExecutor js = (JavascriptExecutor) (driver);
					js.executeScript("arguments[0].click();", webElement);
					Thread.sleep(8000);
					state = true;
				} catch (Exception e) {
					if (!state) {
						try {
							JavascriptExecutor js = (JavascriptExecutor) (driver);
							js.executeScript("$(arguments[0]).focusin();", webElement);
							Thread.sleep(8000);
							state = true;
						} catch (Exception e1) {
							state = false;
						}
					}
				}
			}
			if (e6.getMessage().toString().toLowerCase().contains("Element is not clickable")) {
				extentLogs.error("Click",
						"Caught exception about 'Element is not clickable' while clicking element on '"
								+ driver.getTitle()
								+ "'. So Scroll Top/Bottom and wait for sometime to make clickable");

				if (CommonVariables.TestCaseLog.get() != null) {
					CommonVariables.TestCaseLog.get()
							.info("Info: Caught exception about 'Element is not clickable' while clicking element on '"
									+ driver.getTitle() + "'. So Scroll Top/Bottom and wait for sometime to make '"
									+ webElement + "' clickable");
				} else {
					CommonVariables.TestClassLog.get()
							.info("Info: Caught exception about 'Element is not clickable' while clicking element on '"
									+ driver.getTitle() + "'. So Scroll Top/Bottom and wait for sometime to make '"
									+ webElement + "' clickable");
				}
			}
			try {
				try {
					if (ScrollToElementVisible(webElement)) {
						try {
							new WebDriverWait(driver, Long.parseLong("2"))
									.until(ExpectedConditions.elementToBeClickable(webElement)).click();
							Thread.sleep(8000);
							return true;
						} catch (Exception ex) {
							try {
								JavascriptExecutor js = (JavascriptExecutor) (CommonVariables.CommonDriver.get());
								js.executeScript("arguments[0].click();", webElement);
							} catch (Exception e) {
								return false;
							}
						}
					}
				} catch (Exception e) {
					return false;
				}
				try {
					ScrollToTop();
					JavascriptExecutor js = (JavascriptExecutor) (CommonVariables.CommonDriver.get());
					js.executeScript("arguments[0].focusin();", webElement);
					new WebDriverWait(driver, Long.parseLong("10"))
							.until(ExpectedConditions.elementToBeClickable(webElement)).click();
					Thread.sleep(8000);
					state = true;
				} catch (Exception e1) {
					if (e6.getMessage().toString().toLowerCase().contains("element is not clickable")) {
						try {
							ScrollToBottom();
							JavascriptExecutor js = (JavascriptExecutor) (CommonVariables.CommonDriver.get());
							js.executeScript("arguments[0].focusin();", webElement);
							new WebDriverWait(driver, Long.parseLong("10"))
									.until(ExpectedConditions.elementToBeClickable(webElement)).click();
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
					Actions clicker = new Actions(CommonVariables.CommonDriver.get());
					clicker.moveToElement(webElement).click().perform();

				} catch (Exception e7) {
					state = false;
					extentLogs.error("Click", "Element is not clickable.");
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
	 * @description : javascript Click
	 * @param webElement
	 * @return
	 */

	public boolean JSClick(By loc)
	{
		WebElement elem=driver.findElement(loc);
		return javascriptClick(elem);
	}
	
	/**
	 * @author Cigniti
	 * @description : javascript Click
	 * @param webElement
	 * @return
	 */
	public boolean javascriptClick(WebElement webElement) {
		boolean state = false;
		try {
			JavascriptExecutor js = (JavascriptExecutor) (CommonVariables.CommonDriver.get());
			js.executeScript("arguments[0].click();", webElement);
			Thread.sleep(4000);
			state = true;
			return true;
		} catch (NullPointerException ex) {
			return false;
		} catch (Exception e) {
			try {
				if (ScrollToElementVisible(By.xpath(getElementXPath(webElement)))) {
					JavascriptExecutor js = (JavascriptExecutor) (CommonVariables.CommonDriver.get());
					js.executeScript("$(arguments[0]).click();", webElement);
					Thread.sleep(4000);
					state = true;
					return true;
				} else {
					return false;
				}
			} catch (Exception ex) {

			}
			if (!state) {
				try {
					JavascriptExecutor js = (JavascriptExecutor) (CommonVariables.CommonDriver.get());
					js.executeScript("$(arguments[0]).focusin();", webElement);
					Thread.sleep(8000);
					state = true;
				}

				catch (Exception e1) {
					state = false;
				}
			}
		}
		return state;
	}

	/**
	 * @author Cigniti
	 * @description : javascript Send Keys
	 * @param webElement
	 * @return
	 */
	public boolean javascriptSendKeys(WebElement locator, String valueToType) {
		boolean state = false;
		try {
			JavascriptExecutor js = (JavascriptExecutor) (CommonVariables.CommonDriver.get());
			js.executeScript("arguments[0].value=\"" + valueToType + "\";", locator);
			state = true;
			extentLogs.pass("Type", "Successfully entered text value - '" + valueToType + "'.");
		} catch (Exception ex) {
			extentLogs.error("Type",
					"Failed to enter text value - '" + valueToType + "' due to execption - " + ex.getMessage() + ".");
			state = false;
		}
		return state;
	}

	/**
	 * @author Cigniti
	 * @description: webdriver send keys
	 * @param locator
	 *            the locator
	 * @param valueToType
	 *            the value to type
	 * @param locatorName
	 *            the locator name
	 * @return true, if successful
	 * @throws Throwable
	 *             the throwable
	 */
	public boolean type(WebElement element, String valueToType) throws Throwable {
		boolean status = false;
		try {
			WebDriverWait wait = new WebDriverWait(this.driver, 60);
			ScrollToElementVisible(element);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.clear();
			element.sendKeys(valueToType);
			status = true;
			extentLogs.pass("Type", "Successfully entered text value - '" + valueToType + "'.");
		} catch (Exception e) {
			status = false;
			extentLogs.error("Type",
					"Failed to enter text value - '" + valueToType + "' due to execption - " + e.getMessage() + ".");
			if (CommonVariables.TestCaseLog.get() != null) {
				CommonVariables.TestCaseLog.get().error("Error: Failed to enter text value - '" + valueToType
						+ "' due to execption - " + e.getMessage() + ".");
			} else {
				CommonVariables.TestClassLog.get().error("Error: Failed to enter text value - '" + valueToType
						+ "' due to execption - " + e.getMessage() + ".");
			}
		}
		return status;
	}
	
	
	public boolean type(By locators, String valueToType,String objectName) throws Throwable
	{
		WebElement element=driver.findElement(locators);
		return type(element,valueToType,objectName);
	}
	
	public boolean type(WebElement element, String valueToType,String objectName) throws Throwable {
		boolean status = false;
		try {
			WebDriverWait wait = new WebDriverWait(this.driver, 60);
			ScrollToElementVisible(element);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.clear();
			element.sendKeys(valueToType);
			status = true;
			extentLogs.pass("Type", "Successfully entered text value - '" + valueToType +" at "+objectName+  "'.");
		} catch (Exception e) {
			status = false;
			extentLogs.error("Type",
					"Failed to enter text value - '" + valueToType + "' at "+objectName+"due to execption - " + e.getMessage() + ".");
			if (CommonVariables.TestCaseLog.get() != null) {
				CommonVariables.TestCaseLog.get().error("Error: Failed to enter text value - '" + valueToType
						 +" at "+objectName+ "' due to execption - " + e.getMessage() + ".");
			} else {
				CommonVariables.TestClassLog.get().error("Error: Failed to enter text value - '" + valueToType
						+" at "+objectName+ "' due to execption - " + e.getMessage() + ".");
			}
		}
		return status;
	}

	/**
	 * @author Cigniti
	 * @description: browser specific send keys.
	 * @param locator
	 * @param value
	 * @param locatorName
	 * @throws Throwable
	 */
	public void browserSpecificSendKeys(WebElement locator, String valueToType) throws Throwable {
		String browserName = CommonVariables.DeviceName.get();
		try {
			switch (browserName.toLowerCase()) {
			case "firefox":
			case "chrome":
			case "opera": {
				type(locator, valueToType);
				break;
			}
			case "ie":
			case "safari":
			case "edge":
			case "pahntomjs": {
				javascriptSendKeys(locator, valueToType);
				break;
			}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Select by visible text.
	 * 
	 * @description: select a value from drop down list on the basis of visible
	 *               text
	 * @param locator
	 *            the locator
	 * @param value
	 *            the value
	 * @param locatorName
	 *            the locator name
	 * @return true, if successful
	 * @throws Throwable
	 *             the throwable
	 */
	public boolean selectByVisibleText(By locator, String value, String locatorName) throws Throwable {
		boolean flag = false;
		try {
			ScrollToElementVisible(locator);
			WebElement dropDownListBox = driver.findElement(locator);
			Select clickThis = new Select(dropDownListBox);
			clickThis.selectByVisibleText(value);
			fluentWait(30, 6);
			flag = true;
		} catch (Exception e) {
			extentLogs.error("Select", "'" + value + "'" + "is not selected from the DropDown " + locatorName
					+ " due to exception - " + e.getLocalizedMessage());
			flag = false;
		} finally {
			if (flag == false) {
				extentLogs.fail("Select", "'" + value + "'" + "is not selected from the DropDown " + locatorName);
			} else if (flag == true) {
				extentLogs.pass("Select", "'" + value + "'" + "is selected from the DropDown " + locatorName);
			}
		}
		return flag;
	}

	public boolean asserTitle(String title) throws Throwable {
		boolean flag = false;

		try {
			By windowTitle = By.xpath("//title[contains(text(),'" + title
					+ "')]");
			if (waitForTitlePresent(windowTitle)) {
				Assert.assertEquals(GetPageTitle(), title);
				flag = true;
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		} finally {

			if (!flag) {
				extentLogs.fail("AsserTitle ",
						"Page title is not matched with " + title);
				return false;
			} else if (flag) {
				extentLogs.pass("AsserTitle ",
						" Page title is verified with " + title);
			}
		}

	}
	
	public boolean isElementPresent(By by, String locatorName)
			throws Throwable {
		boolean flag = false;
		try {
			driver.findElement(by);
			flag = true;
			return true;
		} catch (Exception e) {
			Assert.assertTrue(flag,"Element: <b>"+ locatorName+"</b> is <b>NOT</b> present on the page ");
			e.printStackTrace();
			return false;
		} finally {
			if (!flag) {
				extentLogs.fail("Is Element Present ", locatorName
						+ "Element: <b>"+ locatorName+"</b> is <b>NOT</b> present on the page ");
				Assert.assertTrue(flag,"Element: <b>"+ locatorName+"</b> is <b>NOT</b> present on the page ");
			} else if (flag) {
				extentLogs.pass("IsElementPresent ",
						"Able to locate element <b>" + locatorName + "</b>");
			}

		}
	}
	
	public boolean waitForVisibilityOfElementWithOutAssertion(By by, String locator)
			throws Throwable {
		boolean flag = false;
		WebDriverWait wait = new WebDriverWait(driver, 20,30);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			flag = true;
			return true;
		} catch (Exception e) {
			//Assert.assertTrue(flag," Element "
			//		+ locator + " is not visible");
			return false;
		} finally {
			if (!flag) {
				extentLogs.fail("WsaitForVisibilityOfElementWithOutAssertion ", " Element "
						+ locator + " is not visible");
			} else if ( flag) {
				extentLogs.pass("WaitForVisibilityOfElementWithOutAssertion ", " Element "
						+ locator + "  is visible");
			}
		}
	}
	
	public boolean waitForVisibilityOfElement(By by, String locator)
			throws Throwable {
		boolean flag = false;
		WebDriverWait wait = new WebDriverWait(driver, 20,30);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			flag = true;
			return true;
		} catch (Exception e) {
			Assert.assertTrue(flag," Element "
					+ locator + " is not visible");
			return false;
		} finally {
			if (!flag) {
				extentLogs.fail("WaitForVisibilityOfElement ", " Element "
						+ locator + " is not visible");
			} else if ( flag) {
				extentLogs.pass("WaitForVisibilityOfElement ", " Element "
						+ locator + "  is visible");
			}
		}
	}
	
	public List<String> fnGetAllList(By locator,String locatorName) throws Throwable{
		List<String>totalContentList=new ArrayList<String>();
		List<WebElement>contentElement =null;
		try{
			contentElement=driver.findElements(locator);
			extentLogs.pass("Total List Found ", "Available list is <b>"+contentElement.size()+"</b>");
			for(int i=0;i<contentElement.size();i++){
				totalContentList.add(contentElement.get(i).getText());
				
			}
		}catch(Exception e){
			e.printStackTrace();
			extentLogs.fail("Total List Not Found ", "List is not available <b>"+contentElement.size()+"</b>");
			e.printStackTrace();
		}
		return totalContentList;
	}
	public List<WebElement> fnGetAllWebElementList(By locator,String locatorName) throws Throwable{
		List<WebElement>totalContentWebElementList=new ArrayList<WebElement>();
		List<WebElement>contentElement =null;
		try{
			contentElement=driver.findElements(locator);
			extentLogs.pass("Total WebElement List Found ", "Available WebElement list is <b>"+contentElement.size()+"</b>");
			for(int i=1;i<contentElement.size();i++){
				System.out.println("contentElement.size() "+contentElement.size());
				totalContentWebElementList.add(contentElement.get(i));
				//System.out.println("totalContentWebElementList "+totalContentWebElementList);
				//return totalContentWebElementList;
			}
			
		}catch(Exception e){
			e.printStackTrace();
			extentLogs.fail("Total WebElement List Not Found ", "WebElement List is not available <b>"+contentElement.size()+"</b>");
			
		}
		System.out.println("totalContentWebElementList +++"+totalContentWebElementList);
		return totalContentWebElementList;
	}
	
	
	public  List<WebElement> fnGetAllElementList(By locator,String locatorName) throws Throwable{
		List<WebElement>totalContentWebElementList=new ArrayList<WebElement>();
		List<WebElement>contentElement =null;
		try{
			contentElement=driver.findElements(locator);
			extentLogs.pass("Total WebElement List Found ", "Available WebElement list is <b>"+contentElement.size()+"</b>");
			for(int i=0;i<contentElement.size();i++){
				System.out.println("contentElement.size() "+contentElement.size());
				totalContentWebElementList.add(contentElement.get(i));
				//System.out.println("totalContentWebElementList "+totalContentWebElementList);
				//return totalContentWebElementList;
			}
			
		}catch(Exception e){
			e.printStackTrace();
			extentLogs.fail("Total WebElement List Not Found ", "WebElement List is not available <b>"+contentElement.size()+"</b>");
			
		}
		System.out.println("totalContentWebElementList +++"+totalContentWebElementList);
		return totalContentWebElementList;
	}
	
	public boolean mousehoverWithOutAssertion(By locator, String locatorName)
			throws Throwable {
		boolean flag = false;
		try {
			WebElement mo = driver.findElement(locator);
			new Actions(driver).moveToElement(mo).build().perform();
			
			flag = true;
			return true;
		} catch (Exception e) {
			//Assert.assertTrue(flag,"MouseOver action is not performed on " + locatorName);
			return false;
		} finally {
			if (!flag) {
				extentLogs.fail("MouseOver",
						"MouseOver action is not performed on " + locatorName);
				//Assert.assertTrue(flag,"Unable find the element "+ locatorName);
			} else if ( flag) {

				extentLogs.pass("MouseOver ",
						"MouserOver Action is performed on <b>" + locatorName + "</b>");
			}
		}
	}
	
	public boolean mouseHoverByJavaScriptByLocator(By locator, String locatorName)
			throws Throwable {
		boolean flag = false;
		try {
			WebElement mo = driver.findElement(locator);
			String javaScript = "var evObj = document.createEvent('MouseEvents');"
					+ "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);"
					+ "arguments[0].dispatchEvent(evObj);";
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(javaScript, mo);
			flag = true;
			return true;
		}

		catch (Exception e) {

			return false;
		} finally {
			if (!flag) {
				extentLogs.fail("MouseOver ",
						"MouseOver Action was <b>NOT</b> perform on <b>" + locatorName + "</b>");
			} else if ( flag) {
				extentLogs.pass("MouseOver ",
						"MouserOver Action was performed on <b>" + locatorName + "</b>");
			}
		}
	}
	
	public boolean isElementDisplayed(By loc)
			throws Throwable {
		boolean flag = false;
		try {
			 WebDriverWait newWait = new WebDriverWait(driver,20,30);
				WebElement element = null;
				element  = newWait.until(ExpectedConditions.presenceOfElementLocated(loc));
					flag = element.isDisplayed();
		} catch (Exception e) {
			return false;
		}
		return flag;
	}
	public boolean mouseHoverByJavaScript(WebElement we)
			throws Throwable {
		boolean flag = false;
		try {
			WebElement mo = we;
			String javaScript = "var evObj = document.createEvent('MouseEvents');"
					+ "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);"
					+ "arguments[0].dispatchEvent(evObj);";
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(javaScript, mo);
			flag = true;
			return true;
		}
		catch (Exception e) {
			return false;
		} /*finally {
			if (!flag) {
				Reporter.failureReport("MouseOver ",
						" MouseOver action is not perform on " + locatorName);
			} else if (b && flag) {
				Reporter.SuccessReport("MouseOver ",
						" MouserOver Action is Done on " + locatorName);
			}
		}*/
	}
	
	public boolean waitForTitlePresent(By locator) throws Throwable {

		boolean flag = false;
		boolean bValue = false;

		try {
			for (int i = 0; i < 200; i++) {
				if (driver.findElements(locator).size() > 0) {
					flag = true;
					bValue = true;
					break;
				} else {
					driver.wait(50);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (!flag) {
				extentLogs.fail("WaitForTitlePresent ", "Title is wrong");

			} else if (flag) {
				extentLogs.pass("WaitForTitlePresent ",
						"Launched successfully expected Title ");
			}
		}
		return bValue;
	}
	
	
	/**
	 * @author Cigniti Select a value from Dropdown using send keys.
	 *
	 * @param locator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * @param value
	 *            : Value wish type in dropdown list
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:Year Dropdown, items
	 *            Listbox etc..)
	 * @return true, if successful
	 * @throws Throwable
	 *             the throwable
	 */
	public boolean selectBySendkeys(By locator, String value, String locatorName) throws Throwable {
		boolean flag = false;
		try {
			WebDriverWait wait = new WebDriverWait(this.driver, 60);
			ScrollToElementVisible(locator);
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			if (CommonVariables.DeviceName.get().equalsIgnoreCase("ie")
					|| CommonVariables.DeviceName.get().equalsIgnoreCase("safari")) {
				this.driver.findElement(locator).click();
				Select drp = new Select(this.driver.findElement(locator));
				drp.selectByVisibleText(value);
			} else {
				this.driver.findElement(locator).sendKeys(value);
			}
			fluentWait(30, 6);
			flag = true;
		} catch (Exception e) {
			extentLogs.error("Select", "'" + value + "'" + "is not selected from the DropDown " + locatorName
					+ " due to exception - " + e.getLocalizedMessage());
			flag = false;
		} finally {
			if (flag == false) {
				extentLogs.fail("Select", "'" + value + "'" + "is not selected from the DropDown " + locatorName);
			} else if (flag == true) {
				extentLogs.pass("Select", "'" + value + "'" + "is selected from the DropDown " + locatorName);
			}
		}
		return flag;
	}

	/**
	 * @author Cigniti select value from DropDown by using selectByIndex.
	 * @param locator:
	 *            Action to be performed on element (Get it from Object
	 *            repository)
	 * @param index:
	 *            Index of value wish to select from dropdown list.
	 * @param locatorName:
	 *            Meaningful name to the element (Ex:Year Dropdown, items
	 *            Listbox etc..)
	 * @return true, if successful
	 * @throws Throwable
	 *             the throwable
	 */
	public boolean selectByIndex(By locator, int index, String locatorName) throws Throwable {
		boolean flag = false;
		try {
			WebDriverWait wait = new WebDriverWait(this.driver, 60);
			ScrollToElementVisible(locator);
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			Select s = new Select(this.driver.findElement(locator));
			s.selectByIndex(index);
			flag = true;
		} catch (Exception e) {
			extentLogs.error("Select", " Option at index " + index + " is Not selected from the DropDown" + locatorName
					+ " due to exception - " + e.getLocalizedMessage());
			flag = false;
		} finally {
			if (flag == false) {
				extentLogs.fail("Select",
						" Option at index " + index + " is not Selected from the DropDown" + locatorName);
			} else if (flag == true) {
				extentLogs.pass("Select", " Option at index " + index + " is Selected from the DropDown" + locatorName);
			}
		}
		return flag;
	}

	/**
	 * @description: waitForVisibilityOfElement
	 * @param locator
	 * @param locatorName
	 * @param timeOutInSeconds
	 * @return
	 * @throws Throwable
	 */
	public boolean waitForVisibilityOfElement(By locator, String locatorName, long timeOutInSeconds) throws Throwable {
		try {
			WebDriverWait wait = new WebDriverWait(this.driver, timeOutInSeconds);
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			this.driver.findElement(locator);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * @author Cigniti
	 * @description: Get all the options from drop down list
	 * @param locator
	 * @param value
	 * @param locatorName
	 * @return
	 * @throws Throwable
	 */
	public List<String> getAllOptionsFromDropdown(By locator, String locatorName) throws Throwable {
		try {
			Select dropdown = new Select(this.driver.findElement(locator));
			List<WebElement> optionsList = dropdown.getOptions();
			List<String> optionNamesList = new ArrayList<String>();
			for (WebElement option : optionsList) {
				optionNamesList.add(option.getText());
			}
			return optionNamesList;
		} catch (Exception e) {

			return null;
		}
	}

	/**
	 * @author Cigniti
	 * @description: Verify alert present or not
	 * @return true, if successful
	 * @throws Throwable
	 *             the throwable
	 * @return: Boolean (True: If alert preset, False: If no alert)
	 */
	public boolean JSAcceptAlert() throws Throwable {

		boolean flag = false;
		try {
			JavascriptExecutor executor = (JavascriptExecutor) this.driver;
			executor.executeScript("confirm = function(message){return true;};");
			executor.executeScript("alert = function(message){return true;};");
			executor.executeScript("prompt = function(message){return true;}");
			flag = true;
		} catch (Exception e) {
			extentLogs.fail("Accept Alert", "Failed to accept alert due to execption - " + e.getLocalizedMessage());
		} finally {
			if (flag == false) {
				extentLogs.fail("Accept Alert", "Failed to accept alert.");
				return flag;
			} else if (flag == true) {
				extentLogs.fail("Accept Alert", "Alert accepted successfully.");
				return flag;
			}
		}
		return flag;
	}

	public  boolean selectByValue(By locator, String value,
			String locatorName) throws Throwable {
		boolean flag = false;
		try {
			Select s = new Select(driver.findElement(locator));
			s.selectByValue(value);
			flag = true;
			return true;
		} catch (Exception e) {
			Assert.assertTrue(flag,"Option with value attribute " + value
					+ " is Not Selected from the DropDown "
					+ locatorName);
			e.printStackTrace();
			return false;
		} finally {
			if (!flag) {
				extentLogs.fail("Select",
						"Option with value attribute " + value
								+ " is Not Select from the DropDown "
								+ locatorName);

			} else if ( flag) {
				extentLogs.pass("Select ",
						"Option with value attribute " + value
								+ " is  Selected from the DropDown "
								+ locatorName);
			}
		}
	}
	
	public String getSelectedOption(By locator,
			String locatorName) throws Throwable {
		String strSelectdOption="";
		boolean flag = false;
		try {
			Select s = new Select(driver.findElement(locator));
			strSelectdOption=s.getFirstSelectedOption().getText();
			flag = true;
			return strSelectdOption;
		} catch (Exception e) {

			return strSelectdOption;
		} finally {
			if (!flag) {
				extentLogs.fail("Get Selected Value","Not Able to Get Selected Value from the DropDown " + locatorName);
			} else if (flag) {
				extentLogs.pass("Get Selected Value ",strSelectdOption + "is Selected from the DropDown " + locatorName);
			}
		}
	}
	
	
	/**
	 * @author Cigniti
	 * @description: Switch to window
	 * @param driver
	 * @return
	 */
	public static String switchToLatestWindow(WebDriver driver) {
		String newWindowHandle = null;
		try {
			Iterator<String> allWindowHandles = driver.getWindowHandles().iterator();
			while (allWindowHandles.hasNext()) {
				newWindowHandle = allWindowHandles.next();
				driver.switchTo().window(newWindowHandle);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return newWindowHandle;
	}

	/**
	 * @author Cigniti
	 * @description : Set Window Handles
	 */
	public void SetWindowHandles() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
		}
		try {
			// Retrieve handles for all opened browser windows
			if (appiumDriver != null && this.driver != null) {
				/*
				 * arrKnownBrowserHwnd= appiumDriver.getContextHandles();
				 */} else {
				arrKnownBrowserHwnd = driver.getWindowHandles();
			}
			if (appiumDriver == null) {
				if (arrKnownBrowserHwnd.size() >= 1) {
					for (String winHandle : arrKnownBrowserHwnd) {
						hwndMostRecentWindow = winHandle; // Set variable value
															// to the newly
															// opened window
					}
				} else if (arrKnownBrowserHwnd.size() == 0) {

					// No window appeared
					hwndMostRecentWindow = null;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * @author Cigniti
	 * @description : Switch to most recent browser
	 * @return
	 */
	public String switchToMostRecentBrowser() {
		return switchToMostRecentBrowser("");
	}

	/**
	 * @author Cigniti
	 * @description : Switch to most recent browser with title
	 * @param windowTitle
	 * @return
	 */
	public String switchToMostRecentBrowser(String windowTitle) {
		try {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException excep) {
			}
			;
			SetWindowHandles();
			if (windowTitle.isEmpty()) {
				if (appiumDriver != null && this.driver != null) {
					/*
					 * appiumDriver.context(hwndMostRecentWindow); this.driver =
					 * appiumDriver; driver = new
					 * EventFiringWebDriver(this.driver); eventListener = null;
					 * eventListener = new WebDriverListeners(driver);
					 * driver.register(eventListener);
					 */} else {
					driver.getWrappedDriver().switchTo().window(hwndMostRecentWindow);
				}

				// If its IE browser and locally running, set focus as well
				if (ConfigManager.getProperties().getProperty("seleniumGrid").toLowerCase().trim().contains("false")) {
					activateCurrentBrowserWindow();
				} else if (ConfigManager.getProperties().getProperty("cloudExecution").toLowerCase().trim()
						.contains("false")) {
					activateCurrentBrowserWindow();
				}
			} else {
				if (appiumDriver != null && this.driver != null) {
					/*
					 * appiumDriver.context(windowTitle.trim()); this.driver =
					 * appiumDriver; driver = new
					 * EventFiringWebDriver(this.driver); eventListener = null;
					 * eventListener = new WebDriverListeners(driver);
					 * driver.register(eventListener);
					 */} else {
					windowTitle.trim().toLowerCase();
					for (String winHandle : arrKnownBrowserHwnd) {
						if (driver.switchTo().window(winHandle).getTitle().trim().toLowerCase().contains(windowTitle)) {
							WebdriverWaitForPage();
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			return null;
		}
		return hwndMostRecentWindow;
	}

	/**
	 * @author Cigniti
	 * @description : Activate current browser window
	 * @return
	 */
	public String activateCurrentBrowserWindow() {
		// Try to switch to most recent browser window, if require
		return hwndMostRecentWindow;
	}

	/**
	 * @author Cigniti
	 * @description : open application using url
	 * @param url
	 */
	public void OpenApplication(String url) {
		OpenUrl(url);
	}

	/**
	 * @author Cigniti
	 * @description : Open url
	 * @param url
	 * @return
	 */
	public boolean OpenUrl(String url) {
		try {
			driverStartfailureCount = 1;
			if (CommonVariables.TestCaseLog.get() != null) {
				CommonVariables.TestClassLog.get().info("Info: navigating URL : '" + url + "'");
			} else {
				CommonVariables.TestClassLog.get().info("Info: navigating URL : '" + url + "'");
			}
			driver.navigate().to(url);
			return true;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			if (AcceptAlert()) {
				driver.navigate().to(url);
				try {
					Thread.sleep(15000);
				} catch (InterruptedException e1) {
				}
				return true;
			} else {
				extentLogs.info("Open URL",
						"caught 'NoSuchElementException' exception while try to open '" + url + "'.");
				if (CommonVariables.TestCaseLog.get() != null) {
					CommonVariables.TestClassLog.get()
							.info("Info: caught 'NoSuchElementException' exception while try to open '" + url + "'.");
				} else {
					CommonVariables.TestClassLog.get()
							.info("Info: caught 'NoSuchElementException' exception while try to open '" + url + "'.");
				}
			}
			throw new NoSuchElementException("");
		} catch (org.openqa.selenium.UnhandledAlertException e) {
			if (AcceptAlert()) {
				driver.navigate().to(url);
				try {
					Thread.sleep(15000);
				} catch (InterruptedException e1) {
				}
				return true;
			}
			extentLogs.info("Open URL", "caught 'UnhandledAlertException' exception while try to open '" + url + "'.");
			throw new UnhandledAlertException("");
		} catch (TimeoutException e1) {
			extentLogs.info("Open URL", "Timed out receiving message from renderer.");
			this.RefreshBrowser();
			throw new TimeoutException();
		} catch (Exception e) {
			AcceptAlert();
			extentLogs.info("Open URL",
					"Failed to open Url. Err : " + e.getMessage() + "Stace track" + e.getStackTrace());
			return false;
		}
	}

	/**
	 * @author Cigniti
	 * @description : open url using SupressCredential for BMP
	 */
	public void suppressCredential() {
		if (ConfigManager.getProperties().getProperty("IsBrowserMobProxyRequire").trim().equalsIgnoreCase("yes")) {
			Boolean credentials = false;
			if (ConfigManager.getProperties().getProperty("isBMPAuthenticationRequired").trim()
					.equalsIgnoreCase("yes")) {
				credentials = ConfigManager.getProperties().getProperty("urlSuppressCredential").trim().contains("@");
			}
			if (credentials) {
				String url = ConfigManager.getProperties().getProperty("urlSuppressCredential");
				url = url.replace("https://", "http://");
				OpenUrl(url);
				url = url.replace("http://", "https://");
				OpenUrl(url);
			}
		} else {
			ConfigManager.getProperties().setProperty("urlSuppressCredential",
					ConfigManager.getProperties().getProperty("urlSuppressCredential"));
		}
	}

	/**
	 * @author Cigniti
	 * @description: Get WebElement attribute value
	 * @param objWebElement
	 * @param attribute
	 * @return
	 */
	public boolean checkRegularExpression(String pattern, String actual)
			throws Throwable {
		boolean flag = false;
		try {
			 Pattern patternRegex;
			 Matcher matcher;
			 patternRegex = Pattern.compile(pattern);
		       matcher = patternRegex.matcher(actual);
			 flag = matcher.matches();
		} catch (Exception e) {
			return false;
		}
		return flag;
	}
	
	/**
	 * @author Cigniti
	 * @description: Get WebElement attribute value
	 * @param objWebElement
	 * @param attribute
	 * @return
	 */
	public  boolean verifyElementAbsent(By by, String locatorName)
			throws Throwable {
		boolean flag = true;
		try {
			driver.findElement(by);
			flag = false;
			return false;
		} catch (Exception e) {
			extentLogs.pass("Verify Element is NOT displayed",
					"Element: <b>" + locatorName + " </b> is <b> NOT </b> displayed");
			return true;
		} finally {
			if (!flag) {
				extentLogs.fail("Verify Element is NOT displayed",
						"Element: <b>" + locatorName + " </b> is <b> displayed</b>");
				Assert.assertTrue(flag,"Failed to Assert Element is absent"+ locatorName);
			} else if (flag) {
				extentLogs.pass("Verify Element is NOT displayed",
						"Element: <b>" + locatorName + " </b> is <b> NOT </b> displayed");
				return flag;
			}

		}
	}
	/**
	 * @author Cigniti
	 * @description: Get switchToFrameByIndex
	 * @param objWebElement
	 * @param attribute
	 * @return
	 */
	public boolean switchToFrameByIndex(int index) throws Throwable {
		boolean flag = false;
		try {
			driver.switchTo().frame(index);
			flag = true;
			return true;
		} catch (Exception e) {

			return false;
		} finally {
			if (!flag) {
				extentLogs.fail("SelectFrame ", " Frame with index "
						+ index + " is not selected");
			} else if ( flag) {
				extentLogs.pass("SelectFrame ", " Frame with index "
						+ index + " is selected");
			}
		}
	}
	
	/**
	 * @author Cigniti
	 * @description: Get WebElement attribute value
	 * @param objWebElement
	 * @param attribute
	 * @return
	 */
	public boolean assertText(By by, String text) throws Throwable {
		boolean flag = false;
		try {
			Assert.assertEquals(getElementText(by, text).trim(), text.trim());
			flag = true;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (!flag) {
				extentLogs.fail("AssertText ", text
						+ " is not present in the element ");
				return false;

			} else if (flag) {
				extentLogs.pass("AssertText ", text
						+ " is  present in the element ");
			}
		}

	}
	
	
	/**
	 * @author Cigniti
	 * @description: Get WebElement attribute value
	 * @param objWebElement
	 * @param attribute
	 * @return
	 */
	public boolean switchToDefaultFrame() throws Throwable {
		boolean flag = false;
		try {
			driver.switchTo().defaultContent();
			flag = true;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (!flag) {
				extentLogs.fail("SelectFrame ",
						" The Frame is not selected");
			} else if (flag) {
				extentLogs.pass("SelectFrame ",
						" Frame with Name is selected");
			}
		}
	}
	
	/**
	 * @author Cigniti
	 * @description: Get WebElement attribute value
	 * @param objWebElement
	 * @param attribute
	 * @return
	 */
	public String GetElementAttributeValue(WebElement objWebElement, String attribute) {
		try {
			return objWebElement.getAttribute(attribute);
		} catch (org.openqa.selenium.NoSuchElementException e) {
			extentLogs.error("Attribute Value",
					"caught 'ElementNotFoundException' exception. Failed to get attribute - '" + attribute + "' on "
							+ driver.getTitle() + "' page.");
			CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "info",
					"Error: caught 'ElementNotFoundException' exception. Failed to get '" + attribute + "' value for '"
							+ objWebElement + "' on '" + driver.getTitle() + "' page");
			return "";
		} catch (ElementNotVisibleException e) {
			extentLogs.error("Attribute Value",
					"caught 'ElementNotVisibleException' exception. Failed to get attribute - '" + attribute + "' on "
							+ driver.getTitle() + "' page.");
			CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "info",
					"Error: caught 'ElementNotVisibleException' exception. Failed to get '" + attribute
							+ "' value for '" + objWebElement + "' on '" + driver.getTitle() + "' page");
			return "";
		} catch (WebDriverException e) {
			extentLogs.error("Attribute Value", "caught 'WebDriverException' exception. Failed to attribute - '"
					+ attribute + "' on " + driver.getTitle() + "' page.");
			CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "info",
					"Error: caught 'WebDriverException' exception. Failed to get '" + attribute + "' value for '"
							+ objWebElement + "' on '" + driver.getTitle() + "' page");
			return "";
		} catch (NullPointerException e5) {
			extentLogs.error("Attribute Value", "Caught 'NullPointerException' exception while try to get attribute - '"
					+ attribute + "' on " + driver.getTitle() + "' page.");
			CommonVariables.TestCaseLog.get()
					.info("Info. Caught 'NullPointerException' exception while try to get Element Attribute ("
							+ attribute + ") value on '" + driver.getTitle() + "'.");
			return "";
		} catch (Exception e) {
			extentLogs.error("Attribute Value", "Failed to attribute - '" + attribute + "' on " + driver.getTitle()
					+ "' page due to exception - " + e.getMessage());
			CommonVariables.TestCaseLog.get()
					.error("Failed to get '" + attribute + "' value. Error Message: " + e.getMessage());
			return "";
		}
	}

	/**
	 * @author Cigniti
	 * @description: check the existence of an element in DOM
	 * @param by
	 * @param timeoutInSeconds
	 * @return
	 */
	public boolean IsElementExist(By by, Optional<Long> timeoutInSeconds) {
		long timeout = timeoutInSeconds.isPresent() ? timeoutInSeconds.get() : 999999;
		if (timeout == 999999) {
			timeout = Long.parseLong(ConfigManager.getProperties().getProperty("globalTimeOut"));
		}
		try {
			AcceptAlert();
			if (FindElement(by, Optional.of(timeout)) != null) {
				extentLogs.pass("Presence of Element", "Element exists on '" + driver.getTitle() + "' page.");
				if (CommonVariables.TestCaseLog.get() != null) {
					CommonVariables.TestCaseLog.get()
							.info("Info. Element '" + by + "' exists on '" + driver.getTitle() + "' page.");
				} else {
					CommonVariables.TestClassLog.get()
							.info("Info. Element '" + by + "' exists on '" + driver.getTitle() + "' page.");
				}
				return true;
			} else {
				extentLogs.fail("Presence of Element", "Element does not exist on '" + driver.getTitle() + "' page.");
				if (CommonVariables.TestCaseLog.get() != null) {
					CommonVariables.TestCaseLog.get()
							.info("Info. Element '" + by + "' deos not exist on '" + driver.getTitle() + "' page.");
				} else {
					CommonVariables.TestClassLog.get()
							.info("Info. Element '" + by + "' deos not exist on '" + driver.getTitle() + "' page.");
				}
				return false;
			}
		} catch (NullPointerException e) {
			extentLogs.error("Presence of Element",
					"Element not exist on " + driver.getTitle() + "' page due to exception - " + e.getMessage());
			throw e;
		} catch (NoSuchElementException e) {
			extentLogs.error("Presence of Element",
					"Element not exist on " + driver.getTitle() + "' page due to exception - " + e.getMessage());
			throw e;
		} catch (Exception e) {
			extentLogs.error("Presence of Element",
					"Element not exist on " + driver.getTitle() + "' page due to exception - " + e.getMessage());
			throw e;
		}
	}

	/**
	 * @author Cigniti
	 * @description : Get URL
	 * @return
	 */
	public String GetURl() {
		try {
			extentLogs.pass("Get URL", "Successfully navigate to url - '" + driver.getCurrentUrl() + "'.");
			return driver.getCurrentUrl();
		} catch (StaleElementReferenceException e2) {
			extentLogs.error("Get URL", "caught 'StaleElementReferenceException' exception while getting '"
					+ driver.getTitle() + "' page.");
			CommonVariables.TestCaseLog.get()
					.error("Info: caught 'StaleElementReferenceException' exception while getting '" + driver.getTitle()
							+ "' page title");
			return "";
		} catch (org.openqa.selenium.UnhandledAlertException e) {
			extentLogs.error("Get URL",
					"caught 'UnhandledAlertException' exception while getting '" + driver.getTitle() + "' page.");
			CommonVariables.TestCaseLog.get().error("Info: caught 'UnhandledAlertException' exception while getting '"
					+ driver.getTitle() + "' page title");
			return "";
		}
	}

	/**
	 * @author Cigniti
	 * @description : Navigate Back
	 */
	public void NavigateBack() {
		try {
			driver.navigate().back();
			extentLogs.pass("Navigate Back", "Successfully navigate back to page - '" + driver.getTitle() + "' page.");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}
		} catch (org.openqa.selenium.UnhandledAlertException e) {
			extentLogs.error("Navigate Back", "caught 'UnhandledAlertException' exception while navigating back from '"
					+ driver.getTitle() + "' page.");
			CommonVariables.TestCaseLog.get()
					.info("Info: caught 'UnhandledAlertException' exception while navigating back from '"
							+ driver.getTitle() + "' page");
			AcceptAlert();
		} catch (org.openqa.selenium.StaleElementReferenceException e) {
			extentLogs.error("Navigate Back",
					"caught 'StaleElementReferenceException' exception while navigating back from '" + driver.getTitle()
							+ "' page.");
			CommonVariables.TestCaseLog.get()
					.error("Info: caught 'StaleElementReferenceException' exception while navigating back from '"
							+ driver.getTitle() + "' page");
			AcceptAlert();
		}
	}

	/**
	 * @author Cigniti
	 * @description : Refresh browser
	 */
	public void RefreshBrowser() {
		try {
			driver.navigate().refresh();
			extentLogs.pass("Refresh Browser",
					"Successfully refresh browser at page - '" + driver.getTitle() + "' page.");
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				AcceptAlert();
			}
		} catch (org.openqa.selenium.UnhandledAlertException e) {
			extentLogs.error("Refresh Browser",
					"caught 'UnhandledAlertException' exception while refresh '" + driver.getTitle() + "' page");
			if (CommonVariables.TestCaseLog.get() != null) {
				CommonVariables.TestCaseLog.get()
						.info("Info: caught 'UnhandledAlertException' exception while refresh '" + driver.getTitle()
								+ "' page");
			} else {
				CommonVariables.TestClassLog.get()
						.info("Info: caught 'UnhandledAlertException' exception while refresh '" + driver.getTitle()
								+ "' page");
			}
			AcceptAlert();
		} catch (org.openqa.selenium.StaleElementReferenceException e) {
			extentLogs.error("Refresh Browser", "caught 'StaleElementReferenceException' exception while refresh '"
					+ driver.getTitle() + "' page.");
			if (CommonVariables.TestCaseLog.get() != null) {
				CommonVariables.TestCaseLog.get()
						.info("Info: caught 'StaleElementReferenceException' exception while refresh '"
								+ driver.getTitle() + "' page");
			} else {
				CommonVariables.TestClassLog.get()
						.info("Info: caught 'StaleElementReferenceException' exception while refresh '"
								+ driver.getTitle() + "' page");
			}
			AcceptAlert();
		} catch (org.openqa.selenium.UnsupportedCommandException e) {
			extentLogs.error("Refresh Browser",
					"caught 'UnsupportedCommandException' exception while refresh '" + driver.getTitle() + "' page.");
			if (CommonVariables.TestCaseLog.get() != null) {
				CommonVariables.TestCaseLog.get()
						.info("Info: caught 'UnsupportedCommandException' exception while refresh '" + driver.getTitle()
								+ "' page");
			} else {
				CommonVariables.TestClassLog.get()
						.info("Info: caught 'UnsupportedCommandException' exception while refresh '" + driver.getTitle()
								+ "' page");
			}
			try {
				driver.getWrappedDriver().navigate().refresh();
			} catch (Exception e2) {
				extentLogs.error("Refresh Browser",
						"caught 'Exception' exception while refresh '" + driver.getTitle() + "' page.");
				if (CommonVariables.TestCaseLog.get() != null) {
					CommonVariables.TestCaseLog.get()
							.info("Info: caught 'Exception' exception while refresh '" + driver.getTitle() + "' page");
				} else {
					CommonVariables.TestClassLog.get()
							.info("Info: caught 'Exception' exception while refresh '" + driver.getTitle() + "' page");
				}
				driver.navigate().to(driver.getCurrentUrl());
				try {
					Thread.sleep(6000);
				} catch (InterruptedException e1) {
					AcceptAlert();
				}
			}
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e1) {
				AcceptAlert();
			}
		}
	}

	/**
	 * @author Cigniti
	 * @description : get Page Title
	 * @return
	 */
	public String GetPageTitle() {
		try {
			return driver.getTitle();
		} catch (org.openqa.selenium.UnhandledAlertException e) {
			extentLogs.error("Page Title",
					"caught 'UnhandledAlertException' exception while getting '" + driver.getTitle() + "' page.");
			CommonVariables.TestCaseLog.get().info("Info: caught 'UnhandledAlertException' exception while getting '"
					+ driver.getTitle() + "' page title");
			if (AcceptAlert()) {
				return driver.getTitle();
			}
			return null;
		} catch (org.openqa.selenium.StaleElementReferenceException e) {
			extentLogs.error("Page Title", "caught 'StaleElementReferenceException' exception while getting '"
					+ driver.getTitle() + "' page.");
			CommonVariables.TestCaseLog.get()
					.info("Info: caught 'StaleElementReferenceException' exception while getting '" + driver.getTitle()
							+ "' page title");
			try {
				if (AcceptAlert()) {
					return driver.getTitle();
				} else {
					return null;
				}
			} catch (WebDriverException ex) {
				return null;
			}
		} catch (org.openqa.selenium.TimeoutException e) {
			extentLogs.error("Page Title",
					"caught 'TimeoutException' exception while getting '" + driver.getTitle() + "' page.");
			CommonVariables.TestCaseLog.get().info(
					"Info: caught 'TimeoutException' exception while getting '" + driver.getTitle() + "' page title");
			return null;
		}
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
			webElement = (new WebDriverWait(driver, timeout))
					.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
			extentLogs.pass("Find Elements", "Successfully find matched elements on '" + driver.getTitle() + "' page.");
			CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "info",
					"Info: Successfully find matched elements by '" + by + "' on '" + driver.getTitle() + "' page.");
			return webElement;
		} catch (org.openqa.selenium.UnhandledAlertException e) {
			if (AcceptAlert()) {
				return FindElements(by, timeoutInSeconds);
			}
			extentLogs.error("Find Elements",
					"caught 'UnhandledAlertException' exception while finding an elements on '" + driver.getTitle()
							+ "' page.");
			CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "info",
					"Info: caught 'UnhandledAlertException' exception while finding '" + by + "' element on '"
							+ driver.getTitle() + "' page");
			throw new UnhandledAlertException("");
		} catch (StaleElementReferenceException e2) {
			if (AcceptAlert()) {
				return FindElements(by, timeoutInSeconds);
			}
			extentLogs.error("Find Elements", "caught 'StaleElementReferenceException' exception while getting '"
					+ driver.getTitle() + "' page title.");
			CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "info",
					"Info: caught 'StaleElementReferenceException' exception while getting '" + driver.getTitle()
							+ "' page title");
			throw new StaleElementReferenceException("");
		} catch (TimeoutException e3) {
			if (AcceptAlert()) {
				return FindElements(by, timeoutInSeconds);
			}
			extentLogs.error("Find Elements",
					"caught 'TimeoutException' exception while getting '" + driver.getTitle() + "' page title.");
			CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "info",
					"Info: caught 'TimeoutException' exception while getting '" + driver.getTitle() + "' page title");
			throw new TimeoutException();
		} catch (NoSuchElementException e4) {
			if (AcceptAlert()) {
				return FindElements(by, timeoutInSeconds);
			}
			extentLogs.error("Find Elements",
					"caught 'NoSuchElement' exception while getting '" + driver.getTitle() + "' page title.");
			CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "info",
					"Info: caught 'NoSuchElement' exception while getting '" + driver.getTitle() + "' page title");
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
			Alert alert = driver.switchTo().alert();
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
			Alert alert = driver.switchTo().alert();
			alert.dismiss();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * @author Cigniti
	 * @description : Wait for Element Visible
	 * @param by
	 * @param waitTime
	 */
	public void WaitForElementVisible(final By by, int waitTime) {
		wait = new WebDriverWait(driver, waitTime);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			extentLogs.pass("Element Visible", "Element visibile at '" + driver.getTitle() + "' page.");
		} catch (TimeoutException e) {
			extentLogs.error("Element Visible",
					"caught 'TimeoutException' exception while wait for element visibility at '" + driver.getTitle()
							+ "' page.");
			if (CommonVariables.TestCaseLog.get() != null) {
				CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "info",
						"Info: caught 'TimeoutException' exception while wait for element ( " + by.toString()
								+ " ) visibility at '" + driver.getTitle() + "' page");
			} else {
				CommonVariables.TestClassLog.get()
						.info("Info: caught 'TimeoutException' exception while wait for element ( " + by.toString()
								+ " ) visibility at '" + driver.getTitle() + "' page");
			}
		}
	}

	/**
	 * @author Cigniti Description: capture screenshot
	 * @param driver
	 * @param fileName
	 */
	private void takesScreenShot(WebDriver driver, String fileName) {
		WebDriver driverScreenShot = new Augmenter().augment(driver);
		File scrFile = ((TakesScreenshot) driverScreenShot).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Cigniti
	 * @description : Save Screenshot
	 * @param ImgPath
	 * @return
	 */
	public static boolean saveScreenshot(String ImgPath) {
		try {
			// This is the ultimate implementation. Right now, we are able to
			// get the joined images in chrome ONLY.
			if (GetDriverInfo().get("DriverName").contains("Chrome")) {
				try {
					saveFullScreenShot(ImgPath);
					return true;
				} catch (Exception e) {
					System.out.println(
							"Getting exception while taking full screenshot. Exception message:" + e.getStackTrace());
				}
			}

			// This needs to be removed if we are able to get the joined images
			// in other browsers
			if (CommonVariables.getDriver() != null && !driver.getWrappedDriver().getWindowHandle().isEmpty()) {
				File screenshot = null;
				if (ConfigManager.getProperties().getProperty("seleniumGrid").trim().equalsIgnoreCase("true")) {
					org.openqa.selenium.WebDriver augmentedDriver = new Augmenter()
							.augment(CommonVariables.getDriver());
					try {
						screenshot = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
					} catch (WebDriverException e) {
						e.printStackTrace();
					}
				} else if (ConfigManager.getProperties().getProperty("cloudExecution").trim()
						.equalsIgnoreCase("true")) {
					org.openqa.selenium.WebDriver augmentedDriver = new Augmenter().augment(driver.getWrappedDriver());
					try {
						screenshot = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
					} catch (WebDriverException e) {
						e.printStackTrace();
					}
				} else if (driver.getWrappedDriver().getClass().toString().toLowerCase().contains("chromedriver")
						|| driver.getWrappedDriver().getClass().toString().toLowerCase().contains("safari")
						|| driver.getWrappedDriver().getClass().toString().toLowerCase().contains("firefox")
						|| driver.getWrappedDriver().getClass().toString().toLowerCase().contains("internet")
						|| driver.getWrappedDriver().getClass().toString().toLowerCase().contains("ie")
						|| driver.getWrappedDriver().getClass().toString().toLowerCase().contains("edge")
						|| driver.getWrappedDriver().getClass().toString().toLowerCase().contains("Microsoft")
						|| driver.getWrappedDriver().getClass().toString().toLowerCase().contains("opera")
						|| driver.getWrappedDriver().getClass().toString().toLowerCase().contains("PhantomJS")
						|| driver.getWrappedDriver().getClass().toString().toLowerCase().contains("remotewebdriver")) {
					screenshot = ((TakesScreenshot) driver.getWrappedDriver()).getScreenshotAs(OutputType.FILE);
				}
				File screenshotfile = new File(ImgPath);
				try {
					FileUtils.copyFile(screenshot, screenshotfile);
					if (driver.getWrappedDriver().getClass().toString().toLowerCase().contains("internet")
							|| driver.getWrappedDriver().getClass().toString().toLowerCase().contains("ie")) {
						Thread.sleep(1500);
					}
					return true;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			System.out.println();
			e.printStackTrace();
			return false;
		}
		return false;
	}

	/**
	 * @author Cigniti
	 * @description : Capture Full Screenshot
	 * @param ImgPath
	 */
	public static void saveFullScreenShot(String ImgPath) {
		CaptureScreenShot fullscreenshot = new CaptureScreenShot();
		try {
			fullscreenshot.seleniumCaptureBrowserScreenShot(driver, ImgPath);
		} catch (InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/****************************************************************************************************************************
	 * Function Name : isElementEnabled() Description : Element is disabled or not
	 * 
	 * @param objLocator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * @param strLocatorName
	 *            : Meaningful name to the element (Ex:Login Button, UserName
	 *            Textbox etc..)
	 * @return: boolean value (True: if the element is enabled, false: if it not
	 *          enabled).
	 ****************************************************************************************************************************/
	public boolean isElementEnabled(WebElement objLocator, String strLocatorName) throws Throwable {
		Boolean blnFlag = false;
		try {
			if ((objLocator).isEnabled()) {
				blnFlag = true;
				extentLogs.pass("Web Locator Status", "Web locator is enabled for - '" + strLocatorName + "'.");
			}
			return blnFlag;
		} catch (NoSuchElementException e) {
			gStrErrMsg = e.getMessage();
			return blnFlag;
		} 
	}
	/****************************************************************************************************************************
	 * Function Name : isElementDisabled() Description : Element is disabled or not
	 * 
	 * @param objLocator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * @param strLocatorName
	 *            : Meaningful name to the element (Ex:Login Button, UserName
	 *            Textbox etc..)
	 * @return: boolean value (True: if the element is enabled, false: if it not
	 *          enabled).
	 ****************************************************************************************************************************/
	public boolean isElementDisabled(WebElement objLocator, String strLocatorName) throws Throwable {
		Boolean blnFlag = false;
		try {
			if (!(objLocator).isEnabled()) {
				blnFlag = true;
				extentLogs.pass("Web Locator Status", "Web locator is disabled for - '" + strLocatorName + "'.");
			}
			return blnFlag;
		} catch (NoSuchElementException e) {
			gStrErrMsg = e.getMessage();
			return blnFlag;
		} 
	}

	/**
	 * @author Cigniti
	 * @description : Is Element Visible in UI
	 * @param by
	 * @return
	 */
	public boolean IsElementVisible(final By by) {
		try {
			wait = new WebDriverWait(driver, 1);
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			if ((driver.findElement(by).getSize().height == 0) && (driver.findElement(by).getSize().width == 0)) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			try {
				if ((e.getClass().isInstance(new TimeoutException(""))
						|| getClass().isInstance(new ElementNotVisibleException("")))
						|| (e.getClass().isInstance(new NoSuchElementException("")))) {
					if (ScrollToElementVisible(by)) {
						wait.until(ExpectedConditions.visibilityOfElementLocated(by));
						if ((driver.findElement(by).getSize().height == 0)
								&& (driver.findElement(by).getSize().width == 0)) {
							return false;
						} else {
							return true;
						}
					} else {
						throw e;
					}
				} else {
					throw e;
				}
			} catch (Exception e1) {
				if (e1.getCause().toString().contains("NoSuchElementException")) {
					throw new NoSuchElementException("");
				} else if (e1.getCause().toString().contains("ElementNotVisibleException")) {
					throw new ElementNotVisibleException("");
				} else if (e1.getCause().toString().contains("TimeoutException")) {
					throw new TimeoutException();
				} else {
					throw e1;
				}
			}
		}
	}

	/**
	 * @author Cigniti
	 * @description : Is Element Visible in UI
	 * @param by
	 * @return
	 */
	public boolean IsElementVisible(WebElement element) {
		try {
			wait = new WebDriverWait(driver, 1);
			wait.until(ExpectedConditions.visibilityOf(element));
			if ((element.getSize().height == 0) && (element.getSize().width == 0)) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			try {
				if ((e.getClass().isInstance(new TimeoutException(""))
						|| getClass().isInstance(new ElementNotVisibleException("")))
						|| (e.getClass().isInstance(new NoSuchElementException("")))) {
					if (ScrollToElementVisible(element)) {
						wait.until(ExpectedConditions.visibilityOf(element));
						if ((element.getSize().height == 0) && (element.getSize().width == 0)) {
							return false;
						} else {
							return true;
						}
					} else {
						throw e;
					}
				} else {
					throw e;
				}
			} catch (Exception e1) {
				if (e1.getCause().toString().contains("NoSuchElementException")) {
					throw new NoSuchElementException("");
				} else if (e1.getCause().toString().contains("ElementNotVisibleException")) {
					throw new ElementNotVisibleException("");
				} else if (e1.getCause().toString().contains("TimeoutException")) {
					throw new TimeoutException();
				} else {
					throw e1;
				}
			}
		}
	}

	/**
	 * @author Cigniti
	 * @description : Start Video
	 * @param element
	 */
	public void StartVideo(WebElement element) {
		try {
			double browser_top_offset = 80;
			if (GetDriverInfo().get("DriverName").contains("iphone")) {
				browser_top_offset = 50;
			} else if (GetDriverInfo().get("DriverName").contains("ipad")) {
				browser_top_offset = 80;
			}
			RemoteWebElement remoteelem = ((RemoteWebElement) element);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			String script = "return Math.max(document.documentElement.clientHeight, window.innerHeight || 0)";
			Long pageheight1 = (Long) js.executeScript(script);
			Long pageheight2 = (Long) js.executeScript("return window.innerHeight");
			Point eloc = remoteelem.getLocation();
			double yloc = eloc.getY();
			double elemheight = remoteelem.getSize().getHeight() / 2;
			double yratio = (yloc + elemheight + browser_top_offset) / pageheight1;
			HashMap<String, Double> tapObject = new HashMap<String, Double>();
			tapObject.put("x", 0.5);
			tapObject.put("y", yratio);
			js.executeScript("mobile: tap", tapObject);
		} catch (Exception e) {

		}
	}

	/**
	 * @author Cigniti
	 * @description: scroll to top of the page
	 */
	public void ScrollToTop() {
		if (GetDriverInfo().get("DriverType").trim().equalsIgnoreCase("desktop")) {
			try {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("window.scrollTo(0,0);");
			} catch (Exception e) {
			}
		} else {
			try {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("$('body').scrollTop(0);");
			} catch (Exception e) {
				System.out.println(
						"Failed to Swipe on Top on Non-Desktop device. Check 'ScrollToTop' method under action library");
			}
		}
	}

	/**
	 * @author Cigniti
	 * @description: scroll page to bottom of the page
	 */
	public void ScrollToBottom() {
		if (GetDriverInfo().get("DriverType").trim().equalsIgnoreCase("desktop")) {
			try {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("window.scrollTo(0,document.documentElement.scrollHeight);");
			} catch (Exception e) {
			}
		} else {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			int height_covered = 0, pageHeightLeft = 0;
			Long pageheight1 = (Long) js.executeScript("return window.innerHeight");
			Long maxPageHeight1 = (Long) js
					.executeScript("return Math.max(document.documentElement.scrollHeight, document.body.scrollHeight,"
							+ "document.documentElement.clientHeight, window.innerHeight)");
			float sections = (float) maxPageHeight1 / pageheight1;
			int numberOfRows = (int) Math.ceil(sections);
			int pageheight = pageheight1.intValue();
			int maxPageHeight = maxPageHeight1.intValue();
			for (int row = 0; row < numberOfRows; row++) {
				pageHeightLeft = maxPageHeight - height_covered;
				if ((pageHeightLeft < pageheight)) {
				} else {
					height_covered = height_covered + pageheight;
					js.executeScript("window.scrollTo(0," + height_covered + ")");
				}
			}
		}
	}

	/**
	 * @author Cigniti
	 * @description : Set GeoLocation with latitide and longitude
	 * @param latitude
	 * @param longitude
	 */
	public void setGeoLocation(double latitude, double longitude) {
		try {
			if (GetDriverInfo().toString().toLowerCase().contains("ipad")
					|| GetDriverInfo().toString().toLowerCase().contains("iphone")
					|| GetDriverInfo().toString().toLowerCase().contains("android")) {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				// AppiumDriver dr = (AppiumDriver) this.driver;
				Location loc = new Location(latitude, longitude, 10);
				appiumDriver.setLocation(loc);
				try {
					Thread.sleep(2000);
				} catch (Exception e) {
				}
			} else if (ConfigManager.getProperties().getProperty("deviceType").contains("android")) {
				System.out.println("Skipping location set..need to be impletemented on Android.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * @author Cigniti
	 * @description : Set GeoLocation
	 */
	public void setGeoLocation() {
		setGeoLocation(Double.valueOf(ConfigManager.getProperties().getProperty("latitude").trim()),
				Double.valueOf(ConfigManager.getProperties().getProperty("longitude").trim()));
	}

	/**
	 * @param driver
	 *            Current driver instance, which is to be nullified.
	 * @param locationServiceEnabled.
	 *            Permitted values are: true/false/ask
	 * @param latitude
	 *            The latitude of the location to be mocked, if provided with 0,
	 *            then this method will pick value from configuration file.
	 * @param longitude
	 *            The longitude of the location to be mocked, if provided with
	 *            0, then this method will pick value from configuration file.
	 * @return driver Returns updated driver instance.
	 */
	public WebDriver resetLocationCapability(WebDriver driver, String locationServiceEnabled, double latitude,
			double longitude) {
		if (ConfigManager.getProperties().getProperty("deviceType").toLowerCase().contains("safari-mac")) {
			if (locationServiceEnabled.trim().toLowerCase().contains("false")
					|| locationServiceEnabled.trim().toLowerCase().contains("ask")) {
				throw new SkipException(
						"Skipping because of can't handle location Pop-up. So set-up in Safari profile to share the location always.");
			} else
				return driver;
		}
		String previousUrl = "";
		if (GetURl().toLowerCase().contains("http://") || GetURl().toLowerCase().contains("https://")) {
			previousUrl = GetURl();
		}
		tearDown();
		driver = null;
		if (GetDriverInfo().toString().toLowerCase().contains("ipad")
				| GetDriverInfo().toString().toLowerCase().contains("iphone")) {
			initPropertiesFile();
			if (locationServiceEnabled.trim().toLowerCase().contains("false")) {
				this.locationServiceEnabled = false;
			} else
				this.locationServiceEnabled = true;
			this.doFullReset = true;
			driver = instantiateDriver(ConfigManager.getProperties().getProperty("deviceType"));
			this.driver = (EventFiringWebDriver) driver;
			if (locationServiceEnabled != null && (!locationServiceEnabled.trim().toLowerCase().contains("false"))) {
				if (locationServiceEnabled.trim().toLowerCase().contains("ask")) {
				} else if (latitude == 0 && longitude == 0) {
					setGeoLocation();
				} else if (latitude != 0 && longitude != 0) {
					setGeoLocation(latitude, longitude);
				} else {
					System.err.println("Invalid value under locationServiceEnabled flag. Can not start webdriver.");
				}
			}
			if (previousUrl.isEmpty()) {
				OpenUrl(ConfigManager.getProperties().getProperty("urlSuppressCredential"));
			} else {
				if (!previousUrl.contains("@"))
					OpenUrl(ConfigManager.getProperties().getProperty("urlSuppressCredential"));
				OpenUrl(previousUrl);
			}
			AcceptAlert();
			try {
				Thread.sleep(15000);
			} catch (InterruptedException ex) {
			}
		} else if (GetDriverInfo().toString().toLowerCase().contains("chrome")) {

			if (locationServiceEnabled.toLowerCase().contains("true")) {
				this.chromeProfile = "browser-profile-location-boston";
				this.locationServiceEnabled = true;
				driver = instantiateDriver(CommonVariables.PlatformName.get() + "-" + CommonVariables.DeviceName.get());
			} else if (locationServiceEnabled.toLowerCase().contains("false")) {
				this.chromeProfile = "browser-profile-location-No";
				driver = instantiateDriver(CommonVariables.PlatformName.get() + "-" + CommonVariables.DeviceName.get());
			} else if (locationServiceEnabled.toLowerCase().contains("ask")) {
				this.chromeProfile = "browser-profile-location-yes-ask";
				driver = instantiateDriver(CommonVariables.PlatformName.get() + "-" + CommonVariables.DeviceName.get());
			} else {
				System.err.println("Invalid value under locationServiceEnabled flag. Can not start webdriver.");
			}
			if (driver != null) {
				if (previousUrl.isEmpty()) {
					OpenUrl(ConfigManager.getProperties().getProperty("urlSuppressCredential"));
				} else {
					if (!previousUrl.contains("@"))
						OpenUrl(ConfigManager.getProperties().getProperty("urlSuppressCredential"));
					OpenUrl(previousUrl);
				}
				AcceptAlert();
				try {
					Thread.sleep(10000);
				} catch (InterruptedException ex) {
				}
			}
		} else if (GetDriverInfo().toString().toLowerCase().contains("firefox")) {

			if (locationServiceEnabled.toLowerCase().contains("true")) {
				this.locationServiceEnabled = true;
				this.firefoxProfilePath = "Firefox_profile_For_Location_Simulate";
				driver = instantiateDriver(ConfigManager.getProperties().getProperty("deviceType"));
			} else if (locationServiceEnabled.toLowerCase().contains("false")) {
				this.locationServiceEnabled = false;
				driver = instantiateDriver(ConfigManager.getProperties().getProperty("deviceType"));
			} else if (locationServiceEnabled.toLowerCase().contains("ask")) {
				this.locationServiceEnabled = true;
				this.firefoxProfilePath = "Firefox_Profile__Ask_Location_To_Share";
				driver = instantiateDriver(ConfigManager.getProperties().getProperty("deviceType"));
			} else {
				System.err.println("Invalid value under locationServiceEnabled flag. Can not start webdriver.");
			}
			if (driver != null) {
				if (previousUrl.isEmpty()) {
					OpenUrl(ConfigManager.getProperties().getProperty("urlSuppressCredential"));
				} else {
					if (!previousUrl.contains("@"))
						OpenUrl(ConfigManager.getProperties().getProperty("urlSuppressCredential"));
					OpenUrl(previousUrl);
				}
				AcceptAlert();
				try {
					Thread.sleep(10000);
				} catch (InterruptedException ex) {
				}
			}
		} else if (GetDriverInfo().toString().toLowerCase().contains("android-chrome")) {

		}
		return driver;
	}

	/**
	 * @author Cigniti
	 * @description : Location Alert Present
	 * @param alertShouldBeThereOrNot
	 * @param waitingTime
	 * @return
	 */
	public boolean assertLocationAlertPresent(boolean alertShouldBeThereOrNot, Long waitingTime) {
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
			 * should have Allow and Don't Allow buttons.
			 */
			String alertText = alert.getText();
			if (!alertText.equals("\"Safari\" Would Like to Use Your Current Location")) {
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

	public boolean isLocationServiceEnabled() {
		boolean value = false;

		if (locationServiceEnabled != null)
			value = this.locationServiceEnabled.booleanValue();
		return value;

	}

	/**
	 * @author Cigniti
	 * @description : Get Element Xpath
	 * @param element
	 * @return
	 */
	public String getElementXPath(WebElement element) {
		try {
			String str = element.toString().split("->")[1].trim();
			if (str.contains("xpath")) {
				String str2 = str.split("xpath: ")[1].trim();
				str = str2.substring(0, str2.length() - 1);
			} else {
				String str2 = str.split(": ")[1].trim();
				str = str.split(": ")[0].trim() + "=" + str2.substring(0, str2.length() - 1);
			}
			return str;
		} catch (Exception e) {
			return "<failed to retrive xpath>";
		}
	}

	/**
	 * @author Cigniti
	 * @description : Kill window process
	 * @param processName
	 * @throws Exception
	 */
	public void KillWindowProcess(String processName) throws Exception {
		Platform p = Platform.getCurrent();
		if (!p.is(Platform.MAC)) {
			String TASKLIST = "tasklist";
			String KILL = "taskkill /F /IM ";
			Process proc = Runtime.getRuntime().exec(TASKLIST);
			BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.contains(processName)) {
					Runtime.getRuntime().exec(KILL + processName);
				}
			}
		} else {
			try {
				Runtime.getRuntime().exec("killall " + processName);
				Runtime.getRuntime().exec("taskkill /F /IM " + processName);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * @author Cigniti
	 * @description: Select values from drop down button using visible text
	 * @param ddb
	 * @param value
	 * @param index
	 */
	public void SelectFromDropDown(WebElement ddb, String value, int index) {
		try {
			Select dropdown = new Select(ddb);
			dropdown.selectByVisibleText(value);
		} catch (Exception e) {
			try {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].selectedIndex = arguments[1];", ddb, index);
				js.executeScript("$(arguments[0]).trigger('change');", ddb);
			} catch (Exception e5) {
			}
		}

	}

	/**
	 * @author Cigniti
	 * @description: Elements counts
	 * @param by
	 * @param timeoutInSeconds
	 * @param description
	 * @return
	 */
	public Integer CountElement(By by, Optional<Long> timeoutInSeconds, String description) {
		try {
			if (!description.equals("")) {
				extentLogs.info("Elements Count", "Get the count for " + description);
			}
			return FindElements(by, timeoutInSeconds).size();
		} catch (NoSuchElementException e) {
			extentLogs.error("Elements Count",
					"Unable to get the count of elements due to exeception - " + e.getLocalizedMessage());
			return 0;
		} catch (Exception e) {
			extentLogs.error("Elements Count",
					"Unable to get the count of elements due to exeception - " + e.getLocalizedMessage());
			return 0;
		}
	}

	/**
	 * @author Cigniti
	 * @description: get element inner text directly using locator value
	 * @param by
	 * @param description
	 * @return
	 */
	public String getElementInnerText(By by, String description) throws Throwable {
		try {
			if (!description.equals("")) {
				extentLogs.pass("Inner Text of Element", "Get text for " + description);
			}
			WebElement elem = FindElement(by, Optional.of(Long.parseLong("4")));
			JavascriptExecutor js = (JavascriptExecutor) (CommonVariables.CommonDriver.get());
			String text = (String) js.executeScript("return arguments[0].innerHTML.toString();", elem);
			return text;

		} catch (NoSuchElementException e) {
			extentLogs.error("Inner Text of Element",
					"No such element found to get inner text due to execption - " + e.getLocalizedMessage());
			return "";
		} catch (Exception e) {
			driver.findElement(by).getText();
			return "";
		}
	}

	/**
	 * @author Cigniti
	 * @description: get element inner text using WebElement
	 * @param elem
	 * @param description
	 * @return
	 */
	public String getElementInnerText(WebElement elem, String description) {
		try {
			if (!description.equals("")) {
				extentLogs.pass("Inner Text of Element", "Get text for " + description);
				CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "info", "Get text for " + description);
			}
			JavascriptExecutor js = (JavascriptExecutor) (CommonVariables.CommonDriver.get());
			String text = (String) js.executeScript("return arguments[0].innerHTML.toString();", elem);
			return text;
		} catch (NoSuchElementException e) {
			extentLogs.error("Inner Text of Element", "No such element found to get element inner text.");
			CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "error", "No such element found");
			return "";
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * @author Cigniti
	 * @description: get text between open and close tag
	 * @param by
	 * @param description
	 * @return
	 */
	public String getElementText(By by, String description) throws Throwable {
		try {
			if (!description.equals("")) {
				extentLogs.pass("Element Text", "Get text for " + description);
				CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "info", "Get text for " + description);
			}
			return FindElement(by, Optional.of(Long.parseLong("4"))).getText();
		} catch (NoSuchElementException e) {
			extentLogs.error("Element Text",
					"No such element found to get element text due to exception - " + e.getLocalizedMessage());
			CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "info", "No such element found XPATH: " + by);
			return "";
		} catch (NullPointerException e) {
			extentLogs.error("Element Text",
					"No such element found to get element text due to exception - " + e.getLocalizedMessage());
			CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "info", "No such element found XPATH: " + by);
			return "";
		} catch (Exception e) {
			driver.findElement(by).getText();
			extentLogs.error("Element Text",
					"No such element found to get element text due to exception - " + e.getLocalizedMessage());
			CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "info", e.getLocalizedMessage() + " - " + by);
			return "";
		}
	}

	/**
	 * @author Cigniti
	 * @description: compare values for validation
	 * @param actual
	 * @param expected
	 * @param errorText
	 * @return
	 */
	public boolean verifyText(By by, String text, String locatorName)
			throws Throwable {
		boolean flag = false;

		try {

			String vtxt = getElementText(by, locatorName).trim();
			vtxt.equals(text.trim());
			flag = true;
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			if (!flag) {
				extentLogs.fail("VerifyText ", "<b>" +text
						+ "</b> is <b>NOT</b> present in the location: <b>" + locatorName + "</b>");
				flag = true;
			} else if (flag) {
				extentLogs.pass("VerifyText ", "<b>" +text
						+ "</b> is present in the location: <b>" + locatorName + "</b>");
				flag = false;
			}
		}
	}
	
	/**
	 * @author Cigniti
	 * @description: compare values for validation
	 * @param actual
	 * @param expected
	 * @param errorText
	 * @return
	 */
	public Boolean compareValues(String actual, String expected, String errorText) {
		boolean flag = true;
		if (!actual.trim().equalsIgnoreCase(expected.trim())) {
			extentLogs.fail("Compare Text Values",
					"" + errorText + " did not match. Expected text is-" + expected + " and Actual text is-" + actual);
			CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "error",
					errorText + " did not match. Expected text is-" + expected + " and Actual text is-" + actual);
			flag = false;
		} else {
			extentLogs.pass("Compare Text Values",
					"Actual and Expected Value are matched, expected and actual value is " + expected);
			CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "info",
					"Actual and Expected Value are matched, expected and actual value is " + expected);
			flag = true;
		}
		return flag;
	}

	/**
	 * @author Cigniti
	 * @description: verify string contains for validation
	 * @param actual
	 * @param expected
	 * @param errorText
	 * @return
	 */
	public Boolean checkStringContains(String actual, String expected, String errorText) {
		boolean flag = true;
		if (!actual.trim().contains(expected.trim())) {
			extentLogs.fail("Presence of Text", "" + errorText + " did not contain expected String. Expected string is-"
					+ expected + " and Actual string is-" + actual);
			CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "error",
					errorText + " did not contain expected String. Expected string is-" + expected
							+ " and Actual string is-" + actual);
			flag = false;
		} else {
			extentLogs.pass("Presence of Text",
					"Actual string contains the expected String, expected and actual string is " + expected);
			CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "PASS",
					"Actual string contains the expected String, expected and actual string is " + expected);
			flag = true;
		}
		return flag;
	}

	/**
	 * @author Cigniti
	 * @description: get element attribute value with description.
	 * @param by
	 * @param attribute
	 * @param description
	 * @return
	 */
	public String getElementAttribute(By by, String attribute, String description) {
		try {
			return driver.findElement(by).getAttribute(attribute).toString();
		} catch (NoSuchElementException e) {
			extentLogs.error("Attribute Value",
					"No such element found to get attribute value due to exception - " + e.getLocalizedMessage());
			return "";
		} catch (Exception e) {
			driver.findElement(by).getText();
			extentLogs.error("Attribute Value",
					"No such element found to get attribute value due to exception - " + e.getLocalizedMessage());
			return "";
		}
	}

	/**
	 * @author Cigniti
	 */
	public static enum Mode {
		ALPHA, ALPHANUMERIC, NUMERIC
	}

	/**
	 * @author Cigniti
	 * @description: get calendar month value in numeric format
	 * @param monthCharValue
	 * @return
	 */
	public String getMonthNumericValue(String monthCharValue) {
		switch (monthCharValue.trim().toUpperCase()) {
		case "JAN":
		case "JANUARY":
			return "01";
		case "FEB":
		case "FEBRUARY":
			return "02";
		case "MAR":
		case "MARCH":
			return "03";
		case "APR":
		case "APRIL":
			return "04";
		case "MAY":
			return "05";
		case "JUN":
		case "JUNE":
			return "06";
		case "JULY":
		case "JUL":
			return "07";
		case "AUGUST":
		case "AUG":
			return "08";
		case "SEP":
		case "SEPTEMBER":
			return "09";
		case "OCT":
		case "OCTOBER":
			return "10";
		case "NOV":
		case "NOVEMBER":
			return "11";
		case "DECEMBER":
		case "DEC":
			return "12";
		default:
			extentLogs.info("Numeric value of the Month", "Incorrect value specified :" + monthCharValue);
			return "";
		}
	}

	/**
	 * Get Month Name
	 * 
	 * @param monthNumericValue
	 * @return
	 */
	public String getMonthName(String monthNumericValue) {
		switch (monthNumericValue.trim()) {
		case "01":
			return "January";
		case "02":
			return "February";
		case "03":
			return "March";
		case "04":
			return "April";
		case "05":
			return "May";
		case "06":
			return "June";
		case "07":
			return "July";
		case "08":
			return "August";
		case "09":
			return "September";
		case "10":
			return "October";
		case "11":
			return "November";
		case "12":
			return "December";
		default:
			return "";
		}
	}

	/**
	 * @author Cigniti
	 * @descriton: generate random number - alpa,num,alphnum
	 * @param length
	 * @param mode
	 * @return
	 */
	public static String generateRandomString(int length, Mode mode) {
		StringBuffer buffer = new StringBuffer();
		String characters = "";
		Boolean isNumericOnly = false;
		String automationTextPrefix = "";
		switch (mode) {
		case ALPHA:
			characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
			break;

		case ALPHANUMERIC:
			characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
			break;

		case NUMERIC:
			isNumericOnly = true;
			characters = "123456789";
			break;
		}
		int charactersLength = characters.length();
		if (length >= 3 && !isNumericOnly) {
			length = length - 2;
			automationTextPrefix = "AT";
		}
		for (int i = 0; i < length; i++) {
			double index = Math.random() * charactersLength;
			buffer.append(characters.charAt((int) index));
		}
		if (isNumericOnly) {
			return buffer.toString();
		} else {
			return automationTextPrefix + buffer.toString();
		}
	}

	/**
	 * @author Cigniti
	 * @description: download pdf document
	 * @param pdfPagedriver
	 * @param documentName
	 * @param filePath
	 */
	public void DownloadPDFDocument(WebDriver pdfPagedriver, String documentName, String filePath) {

		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BEST_MATCH);
		CookieStore cookieStore = new BasicCookieStore();
		Set<org.openqa.selenium.Cookie> cookies = pdfPagedriver.manage().getCookies();
		for (org.openqa.selenium.Cookie cookie : cookies) {
			BasicClientCookie2 cookie2 = new BasicClientCookie2(cookie.getName(), cookie.getValue());
			cookie2.setAttribute(ClientCookie.VERSION_ATTR, "1");
			cookie2.setAttribute(ClientCookie.DOMAIN_ATTR, cookie.getDomain());
			cookie2.setDomain(cookie.getDomain());
			cookie2.setPath(cookie.getPath());
			cookie2.setExpiryDate(cookie.getExpiry());
			cookieStore.addCookie(cookie2);
		}
		HttpContext httpContext = new BasicHttpContext();
		httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
		try {
			HttpResponse httpResponse = httpClient.execute(new HttpGet(pdfPagedriver.getCurrentUrl()), httpContext);
			InputStream inputStream = httpResponse.getEntity().getContent();

			FileOutputStream fos = new FileOutputStream(filePath + "\\" + documentName.replaceAll("\\s+", "") + ".pdf");

			byte[] buffer = new byte[2048];
			int bytesRead = 0;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				fos.write(buffer, 0, bytesRead);
			}
			fos.close();
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}
		httpClient.getConnectionManager().shutdown();
	}

	/**
	 * @author Cigniti
	 * @description: verify text presence in UI
	 * @param txtValue
	 * @return
	 */
	public boolean isTextPresentOnPage(String txtValue) {
		boolean b = false;
		try {
			String bodyText = driver.findElement(By.tagName("body")).getText();
			b = bodyText.contains(txtValue.trim());
			if (b) {
				extentLogs.pass("Presence of Text", "Info : '" + txtValue + "' is found on Page.");
				b = true;
			} else {
				extentLogs.fail("Presence of Text", "Info : '" + txtValue + "' is not found on Page ");
				b = false;
			}
		} catch (Exception e) {
			extentLogs.error("Presence of Text", "Exception in isTextPresentOnPage" + e.getMessage());
			b = false;
		}
		return b;
	}

	/**
	 * @author Cigniti
	 * @description e.g. for Jul 16 2014 03:32:06 AM format pass 'MMM dd yyyy
	 *              hh:mm:ss aa' as dateformat value.
	 * @param dateformat
	 * @return
	 */
	public String getCurrentDataTime(String dateformat) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
		try {
			return sdf.format(cal.getTime());
		} catch (Exception e) {
			e.getMessage();
			return "";
		}
	}

	/**
	 * @author Cigniti
	 * @param -
	 *            dateValue = "Jul 16 2014 03:32:06 AM" and format =
	 *            "MMM dd yyyy hh:mm:ss aa"
	 * @param -
	 *            dateValue = "7-Jun-2013" and format = "dd-MMM-yyyy"
	 * @param dateValue
	 * @param format
	 * @return
	 */
	public Date getDateTimeFromProvidedString(String dateValue, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		try {
			Date date = formatter.parse(dateValue);
			return date;
		} catch (ParseException e) {
			e.getMessage();
			return null;
		}
	}

	/**
	 * @author Cigniti
	 * @description: get date in user's required format
	 * @param date
	 * @param dateformat
	 * @return
	 */
	public String getDateTimeFromProvidedString(Date date, String dateformat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
		try {
			return sdf.format(date);
		} catch (Exception e) {
			// AddToLog("CurrentTestCaseLog", "error","Failed to convert current
			// Date and Time in the provided format ("+dateformat+")");
			return "";
		}
	}

	/**
	 * @author Cigniti
	 * @description: wait for element present
	 * @param by
	 * @param timeout
	 * @return
	 */
	public Boolean waitForElementPresent(By by, Integer timeout) {
		Boolean flag = false;
		for (Integer i = 1; i <= timeout; i++) {
			if (IsElementExist(by, Optional.of(Long.parseLong("0")))) {
				flag = true;
				break;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
		return flag;
	}

	/**
	 * @author Cigniti
	 * @description: wait for element not present
	 * @param by
	 * @param timeout
	 * @return
	 */
	public Boolean waitForElementNotPresent(By by, Integer timeout) {
		Boolean flag = false;
		for (Integer i = 1; i <= timeout; i++) {
			if (!IsElementExist(by, Optional.of(Long.parseLong("0")))) {
				flag = true;
				break;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
		return flag;
	}

	/**
	 * @author Cigniti
	 * @description: move to element directly using locator value
	 * @param xpath
	 */
	public void setFocusByXpath(String xpath) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) (CommonVariables.CommonDriver.get());
			js.executeScript(
					"$(document.evaluate(\"" + xpath + "\", document, null, 9, null).singleNodeValue).focusin();");
		} catch (NullPointerException ex) {
			try {
				JavascriptExecutor js = (JavascriptExecutor) (driver);
				js.executeScript(
						"$(document.evaluate(\"" + xpath + "\", document, null, 9, null).singleNodeValue).focusin();");
			} catch (Exception e) {

			}
		}
	}

	/**
	 * @author Cigniti
	 * @description: get current date and time
	 * @param format
	 * @return
	 */
	public String getCurrentDateTime(String timeZOne, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setTimeZone(TimeZone.getTimeZone(timeZOne));
		Calendar c = Calendar.getInstance();
		String fdate = sdf.format(c.getTime());
		return fdate;
	}

	/**
	 * @author Cigniti
	 * @description: add no. of days from current date
	 * @param format
	 * @param AddNoOfDays
	 * @return
	 */
	public String AddNoOfDaysInCurrentDate(String format, int AddNoOfDays) {
		String Timezone = ConfigManager.getProperties().getProperty("usTimeZone".trim());
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setTimeZone(TimeZone.getTimeZone(Timezone));
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, AddNoOfDays);
		String fdate = sdf.format(c.getTime());
		return fdate;
	}

	/**
	 * @author Cigniti
	 * @description: change first character of string to Capital Letter
	 * @param str
	 * @return
	 */
	public static String ChangeStringFirstCharToCapital(String str) {
		StringBuilder b = new StringBuilder(str);
		int i = 0;
		do {
			b.replace(i, i + 1, b.substring(i, i + 1).toUpperCase());
			i = b.indexOf(" ", i) + 1;
		} while (i > 0 && i < b.length());
		return b.toString();
	}

	/**
	 * @author Cigniti
	 * @description: get first day of current week
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public String getFirstDayOfCurrentWeek(String format) throws ParseException {
		String fdate;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		fdate = sdf.format(cal.getTime());
		Date date1 = sdf.parse(fdate);
		Date date = new Date();
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		Date date2 = sdf2.parse(sdf2.format(date));
		if (date1.after(date2)) {
			Calendar cal1 = Calendar.getInstance();
			SimpleDateFormat sdf1 = new SimpleDateFormat(format);
			cal1.add(Calendar.DAY_OF_WEEK, -6);
			fdate = sdf1.format(cal1.getTime());
		}
		return fdate;
	}

	/**
	 * @author Cigniti
	 * @description: get last day of current week
	 * @param format
	 * @return
	 */
	public String getLastDayOfCurrentWeek(String format) {
		String ldate;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal.add(Calendar.DAY_OF_WEEK, 6);
		ldate = sdf.format(cal.getTime());
		return ldate;
	}

	/**
	 * @author Cigniti
	 * @description: get last day of current month in user's format
	 * @param format
	 * @return
	 */
	public String getLastDayOfMonth(String format) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
		String ldate = sdf.format(cal.getTime());
		return ldate;
	}

	/**
	 * '
	 * 
	 * @author Cigniti
	 * @description: get last day of current month and year
	 * @param month
	 * @param year
	 * @return
	 */
	public String getLastDayOfMonth(int month, int year) {
		Calendar calendar = Calendar.getInstance();
		// passing month-1 because 0-->jan, 1-->feb... 11-->dec
		calendar.set(year, month - 1, 1);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		Date date = calendar.getTime();
		DateFormat DATE_FORMAT = new SimpleDateFormat("dd");
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		sdf.format(date);
		DATE_FORMAT.format(date);
		return DATE_FORMAT.format(date);
	}

	/**
	 * @author Cigniti
	 * @description: get http url response code, RGV2UHJldmlldzpFcXVpbm94MSE=
	 * @param URL
	 * @return
	 */
	public boolean GetResponseCode(String URL) {
		boolean passflag = true;
		try {
			URL url = new URL(URL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			// connection.setDoInput(true);
			// connection.setRequestProperty("Authorization", "Basic " + "");
			connection.connect();
			int code = connection.getResponseCode();
			if (HttpURLConnection.HTTP_OK == code && code != 200) {
				passflag = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			passflag = false;
		}
		return passflag;
	}

	/**
	 * @author Cigniti
	 * @description: get last sunday from current date/day in user's format
	 * @param format
	 * @return
	 */
	public String getPreviousSunday(String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		Calendar now = new GregorianCalendar();
		Calendar start = new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH),
				now.get(Calendar.DAY_OF_MONTH));
		while (start.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
			start.add(Calendar.DAY_OF_WEEK, -1);
		}
		Calendar end = (Calendar) start.clone();
		end.add(Calendar.DAY_OF_MONTH, 7);
		return df.format(start.getTime());
	}

	/**
	 * @author Cigniti
	 * @description: get next sunday from current date/day in user's format
	 * @param format
	 * @return
	 */
	public String getNextSunday(String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		// DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL,
		// DateFormat.FULL);
		Calendar now = new GregorianCalendar();
		Calendar start = new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH),
				now.get(Calendar.DAY_OF_MONTH));
		while (start.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
			start.add(Calendar.DAY_OF_WEEK, +1);
		}
		Calendar end = (Calendar) start.clone();
		end.add(Calendar.DAY_OF_MONTH, 7);
		return df.format(start.getTime());
	}

	/**
	 * @author Cigniti
	 * @description : get Date In Expected Format
	 * @param format
	 * @param dateInString
	 * @param returnformat
	 * @return
	 */
	public String getDateInExpectedFormat(String format, String dateInString, String returnformat) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		SimpleDateFormat returnFormatter = new SimpleDateFormat(returnformat);
		Date date = null;
		try {
			date = formatter.parse(dateInString);
			// formatter = new SimpleDateFormat(returnformat);
			return returnFormatter.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @author Cigniti
	 * @description : Get Total Number of days in the Current Month
	 * @return
	 */
	public int getTotalNoOfDayInMonth() {
		try {
			Calendar c = Calendar.getInstance();
			int monthMaxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
			CurrentMonthDays = monthMaxDays;
		} catch (Exception e) {
			e.getMessage();
		}
		return CurrentMonthDays;
	}

	/**
	 * @author Cigniti
	 * @description : Swipe To Element Visible
	 * @param elementBy
	 * @return
	 */
	public boolean ScrollToElementVisible(By elementBy) {
		try {
			WebElement elem = (new WebDriverWait(driver, 1))
					.until(ExpectedConditions.presenceOfElementLocated(elementBy));
			return ScrollToElementVisible(elem);
		} catch (Exception ex) {
			ex.getLocalizedMessage();
			return false;
		}
	}

	/**
	 * @author Cigniti
	 * @description : Scroll To Element Visible
	 * @param WebElement
	 * @return
	 */
	public boolean ScrollToElementVisible(WebElement element) {
		if (CommonVariables.PlatformName.get().equalsIgnoreCase("windows")) {
			try {
				ScrollToTop();
				Point p = element.getLocation();
				if (p.getX() == 0 && p.getY() == 0) {
					return false;
				} else {
					((JavascriptExecutor) driver)
							.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 120) + ");");
					try {
						if (driver.getWrappedDriver().getClass().toString().toLowerCase().contains("internet")
								|| driver.getWrappedDriver().getClass().toString().toLowerCase().contains("ie")
								|| driver.getWrappedDriver().getClass().toString().toLowerCase().contains("edge")
								|| driver.getWrappedDriver().getClass().toString().toLowerCase().contains("safari")
								|| driver.getWrappedDriver().getClass().toString().toLowerCase().contains("opera")) {
							Thread.sleep(800);
						}
					} catch (Exception e) {
					}
					return true;
				}
			} catch (Exception ex) {
				throw ex;
			}
		} else {
			try {
				Dimension d = element.getSize();
				if (d.height != 0 && d.width != 0) {
					new Actions(driver.getWrappedDriver()).moveToElement(element).perform();
					return true;
				} else {
					JavascriptExecutor js = (JavascriptExecutor) driver;
					int height_covered = 0, pageHeightLeft = 0;
					Long pageCurrentHeight = (Long) js.executeScript("return window.innerHeight");
					ScrollToTop();
					Long pageheight1 = (Long) js.executeScript("return window.innerHeight");
					Long maxPageHeight1 = (Long) js.executeScript(
							"return Math.max(document.documentElement.scrollHeight, document.body.scrollHeight,"
									+ "document.documentElement.clientHeight, window.innerHeight)");
					float sections = (float) maxPageHeight1 / pageheight1;
					int numberOfRows = (int) Math.ceil(sections);
					int pageheight = pageheight1.intValue();
					int maxPageHeight = maxPageHeight1.intValue();
					for (int row = 0; row < numberOfRows; row++) {
						pageHeightLeft = maxPageHeight - height_covered;
						if ((pageHeightLeft < pageheight)) {
							d = element.getSize();
							if (d.height == 0 && d.width == 0) {
								js.executeScript("window.scrollTo(0," + pageCurrentHeight + ")");
								return false;
							} else {
								new Actions(driver.getWrappedDriver()).moveToElement(element).perform();
								return true;
							}
						} else {
							d = element.getSize();
							if (!(d.height != 0 && d.width != 0)) {
								height_covered = height_covered + pageheight;
								js.executeScript("window.scrollTo(0," + height_covered + ")");
								try {
									Thread.sleep(100);
								} catch (InterruptedException e) {
								}
							} else {
								new Actions(driver.getWrappedDriver()).moveToElement(element).perform();
								return true;
							}
						}
					}
					ScrollToTop();
					return false;
				}
			} catch (org.openqa.selenium.ElementNotVisibleException e) {
				throw new ElementNotVisibleException("");
			} catch (Exception ex) {
				throw ex;
			}
		}
	}

	/**
	 * @author Cigniti
	 * @description : Mouse Over
	 * @param webelement
	 */
	public void Mouseover(WebElement webelement) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) (CommonVariables.CommonDriver.get());
			js.executeScript("$(arguments[0]).trigger('mouseover');", webelement);
		} catch (Exception e) {
			e.getLocalizedMessage();
		}
	}

	public String GetXpathFromBy(By by) {
		try {
			return by.toString().split("By.xpath: ")[1].trim();
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * @author Cigniti
	 * @description : Is Element Display In Current View
	 * @param xpath
	 * @return
	 */
	
	   public boolean verifyElementDisplayed(By by, String elementName) throws Throwable{
	        boolean displayFlag = false;
	        try {
		        if(IsElementDisplayInCurrentView(by)){
		        	displayFlag = true;
		        	extentLogs.pass("Verify Element is displayed","<b>" + elementName + "</b>"  + " is displayed");
		        }else{
		        	
		        	extentLogs.fail("Verify Element is displayed","<b>" + elementName + "</b>" + " is NOT displayed");
		        }
	        }
	        catch (Exception e) {
				e.printStackTrace();
	        }
			return displayFlag;
	        
	    }
	
	/**
	 * @author Cigniti
	 * @description : Is Element Display In Current View
	 * @param xpath
	 * @return
	 */
	public boolean IsElementDisplayInCurrentView(By xpath) {
		try {
			return IsElementDisplayInCurrentView(driver.findElement(xpath));
		} catch (Exception ex) {
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
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			Long pageheight = (Long) js.executeScript("return $(window).height()");
			double elemPos = 0;
			try {
				Long posY = (Long) js.executeScript("return $(arguments[0]).offset().top - $(window).scrollTop()",
						webElement);
				elemPos = posY.doubleValue();
			} catch (ClassCastException cc) {
				elemPos = (Double) js.executeScript("return $(arguments[0]).offset().top - $(window).scrollTop()",
						webElement);
			}
			double pageheight1 = pageheight.doubleValue();
			if (elemPos >= browser_top_offset && elemPos < pageheight1)
				return true;
			else
				return false;
		} catch (Exception ex) {
			System.out.println("" + ex.getMessage());
			return false;
		}
	}

	/**
	 * @author Cigniti
	 * @description : Calculate Age
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public int calculateAge(int year, int month, int day) // Enter Date of Birth
															// //
	{
		try {
			LocalDate birthdate = new LocalDate(year, month, day);
			LocalDate now = new LocalDate();
			Years ageInYear = Years.yearsBetween(birthdate, now);
			return ageInYear.getYears();
		} catch (Exception ex) {
			throw ex;
		}
	}

	/**
	 * @author Cigniti
	 * @param dob
	 *            1986-10-31, 1955-30-12
	 * @param format
	 *            - yyyy-mm-dd , yyyy-dd-mm
	 * @return Age as a integer
	 */
	@SuppressWarnings("deprecation")
	public int calculateAge(String dob, String format) throws Exception// Enter
																		// Date
																		// od
																		// Birth
																		// //
	{
		try {
			Date dob_DateObj = getDateTimeFromProvidedString(dob, format);
			long ageInMillis = new Date().getTime() - dob_DateObj.getTime();
			return new Date(ageInMillis).getYear();
		} catch (IllegalArgumentException ex) {
			extentLogs.info("calculate Age",
					"Failed to get Age from provided format (" + format + ") and date value (" + dob + ")");
			throw ex;
		} catch (NullPointerException ex) {
			extentLogs.info("calculate Age",
					"Failed to get Age from provided format (" + format + ") and dob value (" + dob + ")");
			throw ex;
		}
	}

	/**
	 * @author Cigniti
	 * @param url
	 * @param userAgent
	 * @return
	 */
	public Map<String, String> getHTTPResponseCodeAndSize(String url, String userAgent) {
		Map<String, String> httpResponseMetaData = new HashMap<String, String>();
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", userAgent);

			httpResponseMetaData.put("code", String.valueOf(con.getResponseCode()));
			httpResponseMetaData.put("size", String.valueOf(con.getContentLength()));
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			httpResponseMetaData.put("content", response.toString());
			return httpResponseMetaData;
		} catch (Exception e) {
			httpResponseMetaData.put("code", "500");
			httpResponseMetaData.put("size", "0");
			httpResponseMetaData.put("content", e.getMessage());
			return httpResponseMetaData;
		}
	}

	/**
	 * @author Cigniti
	 * @description : get cookies
	 * @return
	 */
	public String[] getcookies() {
		String[] stringArray = null;
		try {
			Set<Cookie> cookie = CommonVariables.CommonDriver.get().manage().getCookies();
			Object[] cookie_text = cookie.toArray();
			stringArray = new String[cookie_text.length];
			for (int i = 0; i < cookie_text.length; i++) {
				stringArray[i] = cookie_text[i].toString();
			}
		} catch (Exception e) {
			System.out.println("Not able to get cookies for the selected page due to error message :" + e.getMessage());
		}
		return stringArray;
	}

	/**
	 * @param upper
	 *            upper limit
	 * @param lower
	 *            lower limit
	 * @return
	 */
	public Integer getRandomNumberBetween(Integer upper, Integer lower) {
		return (int) ((Math.random() * (upper - lower)) + lower);
	}

	public void setDownloadPathForFile(String downloadpath) {
		try {
			String downloadFilepath = downloadpath;
			File fl = new File(downloadFilepath);
			if (!fl.exists()) {
				fl.mkdir();
			}
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("profile.default_content_settings.popups", 0);
			prefs.put("download.default_directory", downloadFilepath);
			prefs.put("savefile.default_directory", downloadFilepath);
			chromeoptions.setExperimentalOption("prefs", prefs);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	/**
	 * @author Cigniti
	 * @description: hightlight WebElement
	 * @param elem
	 * @param durationInSeconds
	 */
	public void HighlightWebElement(WebElement elem, int durationInSeconds) {
		try {
			durationInSeconds = durationInSeconds * 1000;
			String original_style = elem.getAttribute("style");
			((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", elem,
					"style", "border: 2px solid red; border-style: dashed;");
			if (durationInSeconds > 0) {
				Thread.sleep(durationInSeconds);
				((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute(arguments[1], arguments[2])",
						elem, "style", original_style);
			}
		} catch (Exception e) {

		}
	}

	/**
	 * @author Cigniti
	 * @description : Switch In to iFrame
	 * @param iFrameID
	 * @return
	 */
	public boolean SwitchIntoFrame(WebElement iFrameID) {
		try {
			driver.switchTo().frame(iFrameID);
			System.out.println("switch to frame sucessfully.");
			return true;
		} catch (Exception e) {
			System.out.println("Failed to switch to frame due to error :" + e.getMessage());
			return false;
		}
	}

	/**
	 * @author Cigniti
	 * @return
	 */
	public WebDriver SwitchOutfromFrame() {
		return driver.switchTo().defaultContent();

	}

	/**
	 * @author Cigniti
	 * @description : get Current Month
	 * @return
	 */
	public String getCurrentMonth() {
		String CurrentMonth = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
			Calendar cal = Calendar.getInstance();
			CurrentMonth = sdf.format(cal.getTime());
			System.out.println("Current Month :" + CurrentMonth);
		} catch (Exception e) {
			e.getStackTrace();
		}
		return CurrentMonth;
	}

	/**
	 * @author Cigniti
	 * @description : get Next Month
	 * @return
	 */
	public String getNextMonth() {
		String NextMonth = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, 1);
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
			NextMonth = sdf.format(cal.getTime());
			System.out.println("Next Month :" + NextMonth);
		} catch (Exception e) {
			e.getStackTrace();
		}
		return NextMonth;
	}

	/**
	 * @author Cigniti
	 * @param file
	 * @return
	 */
	public Boolean deleteFile(File file) {
		try {
			if (file.isDirectory()) {
				String fileList[] = file.list();
				if (fileList.length == 0) {
					extentLogs.info("Delete File", "Deleting Directory : " + file.getPath());
					file.delete();
					extentLogs.info("Delete File", "Deleted Directory : " + file.getPath());
				} else {
					int size = fileList.length;
					for (int i = 0; i < size; i++) {
						String fileName = fileList[i];
						extentLogs.info("Delete File", "File path : " + file.getPath() + " and name :" + fileName);
						String fullPath = file.getPath() + "/" + fileName;
						File fileOrFolder = new File(fullPath);
						extentLogs.info("Delete File", "File copied, full path is: " + fileOrFolder.getPath());
						deleteFile(fileOrFolder);
					}
				}
				return true;
			} else {
				extentLogs.info("Delete File", "Deleting file : " + file.getPath());
				file.delete();
				extentLogs.info("Delete File", "Deleted file : " + file.getPath());
				return true;
			}
		} catch (Exception e) {
			extentLogs.error("Delete File", "Deleted file : " + file.getPath());
			return false;
		}
	}

	/**
	 * @description: Verify the presence of a file at a particular location.
	 *               File name is not case sensitive and can provide
	 *               complete/partial name of the file.
	 * @param path
	 *            - Complete Path of the folder which suppose to contain file
	 * @param fileName
	 *            - Name of the file. It'll compare case
	 * @return True, if file found.
	 */
	public Boolean verifyPresenceOfFileOrFolderAtLocation(String path, String fileName) {
		try {
			File file = new File(path);
			File[] listOfFiles = file.listFiles();
			if (listOfFiles.length != 0) {
				for (int i = 0; i < listOfFiles.length; i++) {
					if (listOfFiles[i].getName().toLowerCase().contains(fileName.toLowerCase())) {
						return true;
					}
				}
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * <b>Description:</b> Copies the entire src File to Dest
	 * 
	 * @param src
	 * @param dest
	 * @throws Exception
	 */
	public void copyFile(File src, File dest) throws Exception {
		if (src.isDirectory()) {
			if (!dest.exists()) {
				dest.mkdir();
				System.out.println("Directory copied from " + src + " to " + dest);
			}
			String files[] = src.list();
			for (String file : files) {
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				copyFile(srcFile, destFile);
			}
		} else {
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);

			byte[] buffer = new byte[1024];

			int length;
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
			in.close();
			out.close();
			System.out.println("File copied from " + src + " to " + dest);
		}
	}

	/**
	 * <b>Description:</b> Copies the entire src File to Dest
	 * 
	 * @param src
	 * @param dest
	 * @throws Exception
	 */
	public static void staticCopyFile(File src, File dest) throws Exception {
		if (src.isDirectory()) {
			if (!dest.exists()) {
				dest.mkdir();
				System.out.println("Directory copied from " + src + " to " + dest);
			}

			String files[] = src.list();

			for (String file : files) {
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				staticCopyFile(srcFile, destFile);
			}

		} else {
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);

			byte[] buffer = new byte[1024];

			int length;
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}

			in.close();
			out.close();
		}
	}

	/**
	 * @author Cigniti
	 * @param startTime
	 *            - Start Time as a Date class object
	 * @param endTime
	 *            - End Time as a Date class object
	 */
	public long getTimeDifferenceInMin(Date startTime, Date endTime) {
		long ageInMillis = endTime.getTime() - startTime.getTime();
		return ageInMillis / (1000 * 60);
	}

	/**
	 * @author Cigniti
	 * @description: get IP of selenium grid 'node'
	 * @param remotewebdriver
	 * @return
	 */
	public String GetIPOfSeleniumGridNode(WebDriver remotewebdriver) {
		String hostFound = null;
		try {
			HttpCommandExecutor commandexecutor = (HttpCommandExecutor) ((RemoteWebDriver) remotewebdriver)
					.getCommandExecutor();
			String hostName = commandexecutor.getAddressOfRemoteServer().getHost();
			int port = commandexecutor.getAddressOfRemoteServer().getPort();
			HttpHost host = new HttpHost(hostName, port);
			@SuppressWarnings("deprecation")
			DefaultHttpClient client = new DefaultHttpClient();
			URL sessionURL = new URL("http://" + hostName + ":" + port + "/grid/api/testsession?session="
					+ ((RemoteWebDriver) remotewebdriver).getSessionId());
			BasicHttpEntityEnclosingRequest r = new BasicHttpEntityEnclosingRequest("POST",
					sessionURL.toExternalForm());
			HttpResponse response = client.execute(host, r);
			JSONObject object = extractObject(response);
			URL myURL = new URL(object.getString("proxyId"));
			if ((myURL.getHost() != null) && (myURL.getPort() != -1)) {
				hostFound = myURL.getHost();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hostFound;
	}

	/**
	 * @author Cigniti
	 * @param resp
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	private static JSONObject extractObject(HttpResponse resp) throws IOException, JSONException {
		InputStream contents = resp.getEntity().getContent();
		StringWriter writer = new StringWriter();
		IOUtils.copy(contents, writer, "UTF8");
		JSONObject objToReturn = new JSONObject(writer.toString());
		return objToReturn;
	}

	/**
	 * @author Cigniti
	 * @description: WebElement click
	 * @param webElement
	 * @param time
	 * @return
	 */
	public boolean javascriptClick(WebElement webElement, long time) {
		boolean state = false;
		try {
			JavascriptExecutor js = (JavascriptExecutor) (CommonVariables.CommonDriver.get());
			js.executeScript("arguments[0].click();", webElement);
			Thread.sleep(time);
			state = true;
			return true;
		} catch (NullPointerException ex) {
			return false;
		} catch (Exception e) {
			try {
				if (ScrollToElementVisible(By.xpath(getElementXPath(webElement)))) {
					JavascriptExecutor js = (JavascriptExecutor) (CommonVariables.CommonDriver.get());
					js.executeScript("$(arguments[0]).click();", webElement);
					Thread.sleep(time);
					state = true;
					return true;
				} else {
					return false;
				}
			} catch (Exception ex) {

			}
			if (!state) {
				try {
					JavascriptExecutor js = (JavascriptExecutor) (CommonVariables.CommonDriver.get());
					js.executeScript("$(arguments[0]).focusin();", webElement);
					Thread.sleep(time);
					state = true;
				}

				catch (Exception e1) {
					state = false;
				}
			}
		}
		return state;
	}

	/**
	 * @author Cigniti
	 * @description : get Last Month
	 * @return
	 */
	public String getLastMonth() {
		String LastMonth = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, -1);
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
			LastMonth = sdf.format(cal.getTime());
			System.out.println("Next Month :" + LastMonth);
		} catch (Exception e) {
			e.getStackTrace();
		}
		return LastMonth;
	}

	/**
	 * @author Cigniti
	 * @description: get alert
	 * @return
	 */
	public String getAlertText() {
		try {
			Alert alert = driver.switchTo().alert();
			return alert.getText();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @author Cigniti
	 * @param remotewebdriver
	 * @return
	 */
	public String getIPOfSeleniumGridNode(RemoteWebDriver remotewebdriver) {
		String hostFound = null;
		try {
			HttpCommandExecutor commandexecutor = (HttpCommandExecutor) remotewebdriver.getCommandExecutor();
			String hostName = commandexecutor.getAddressOfRemoteServer().getHost();
			int port = commandexecutor.getAddressOfRemoteServer().getPort();
			HttpHost host = new HttpHost(hostName, port);
			@SuppressWarnings("deprecation")
			DefaultHttpClient client = new DefaultHttpClient();
			URL sessionURL = new URL("http://" + hostName + ":" + port + "/grid/api/testsession?session="
					+ remotewebdriver.getSessionId());
			BasicHttpEntityEnclosingRequest r = new BasicHttpEntityEnclosingRequest("POST",
					sessionURL.toExternalForm());
			HttpResponse response = client.execute(host, r);
			JSONObject object = extractObject(response);
			URL myURL = new URL(object.getString("proxyId"));
			if ((myURL.getHost() != null) && (myURL.getPort() != -1)) {
				hostFound = myURL.getHost();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hostFound;
	}

	/**
	 * @return Clipboard text
	 * @throws UnsupportedFlavorException
	 * @throws IOException
	 */
	public String getTextFromClipboard() throws UnsupportedFlavorException, IOException {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Clipboard clipboard = toolkit.getSystemClipboard();
		return (String) clipboard.getData(DataFlavor.stringFlavor);
	}

	/**
	 * @author Cigniti
	 * @description: kill unwanted pop in web
	 */
	public void killPopup(String textInURL) {
		try {
			String windowHandle = driver.getWindowHandle();
			for (String window : driver.getWindowHandles()) {
				WebDriver temp = driver.switchTo().window(window);
				if (temp.getCurrentUrl().toLowerCase().contains(textInURL)) {
					temp.close();
				}
			}
			driver.switchTo().window(windowHandle);
		} catch (Exception e) {
		}
	}

	/**
	 * @author Cigniti
	 * @description: switch to iframe
	 * @param frame_element
	 * @return
	 */
	public static WebDriver SwitchToiFrame(WebElement frame_element) {
		CommonVariables.getDriver().switchTo().frame(frame_element);
		CommonVariables.setDriver((EventFiringWebDriver) CommonVariables.getDriver());
		return CommonVariables.getDriver();
	}

	/**
	 * @author Cigniti
	 * @description: switch back to default content from iframe
	 * @return
	 */
	public static WebDriver SwitchBackFromiFrameToMaincontent() {
		CommonVariables.getDriver().switchTo().defaultContent();
		CommonVariables.setDriver((EventFiringWebDriver) CommonVariables.getDriver());
		return CommonVariables.getDriver();
	}

	/**
	 * @author Cigniti
	 * @description: get current time zone
	 * @return
	 */
	public static String getCurrentTimeZone() {
		String timeZone = null;
		// get Calendar instance
		Calendar now = Calendar.getInstance();
		// get current TimeZone using getTimeZone method of Calendar class
		TimeZone tZone = now.getTimeZone();
		// display current TimeZone using getDisplayName() method of TimeZone
		// class
		timeZone = tZone.getDisplayName();
		return timeZone;
	}

	/**
	 * @author Cigniti
	 * @description: Robot class should be use only when keyboard inputs are
	 *               required and all other Options like, ACtion Class,
	 *               Webdriver Send Keys.. are not working.
	 * @return
	 * @throws AWTException
	 */
	public Robot roboticMethod() throws AWTException {
		Robot robot = new Robot();
		return robot;
	}

	/**
	 * @author Cigniti
	 * @description: Maximum '6' times user can use tab click through robotium.
	 * @param numberofClick
	 * @throws AWTException
	 */
	public void roboticTabClick(int numberofClick) throws AWTException {
		switch (numberofClick) {
		case 1:
			roboticMethod().keyPress(KeyEvent.VK_TAB);
			break;
		case 2:
			roboticMethod().keyPress(KeyEvent.VK_TAB);
			roboticMethod().keyPress(KeyEvent.VK_TAB);
			break;
		case 3:
			roboticMethod().keyPress(KeyEvent.VK_TAB);
			roboticMethod().keyPress(KeyEvent.VK_TAB);
			roboticMethod().keyPress(KeyEvent.VK_TAB);
			break;
		case 4:
			roboticMethod().keyPress(KeyEvent.VK_TAB);
			roboticMethod().keyPress(KeyEvent.VK_TAB);
			roboticMethod().keyPress(KeyEvent.VK_TAB);
			roboticMethod().keyPress(KeyEvent.VK_TAB);
			break;
		case 5:
			roboticMethod().keyPress(KeyEvent.VK_TAB);
			roboticMethod().keyPress(KeyEvent.VK_TAB);
			roboticMethod().keyPress(KeyEvent.VK_TAB);
			roboticMethod().keyPress(KeyEvent.VK_TAB);
			roboticMethod().keyPress(KeyEvent.VK_TAB);
			break;
		case 6:
			roboticMethod().keyPress(KeyEvent.VK_TAB);
			roboticMethod().keyPress(KeyEvent.VK_TAB);
			roboticMethod().keyPress(KeyEvent.VK_TAB);
			roboticMethod().keyPress(KeyEvent.VK_TAB);
			roboticMethod().keyPress(KeyEvent.VK_TAB);
			roboticMethod().keyPress(KeyEvent.VK_TAB);
			break;
		default:
			System.out.println("Tab not clicked.");
			break;
		}
	}

	/**
	 * @author Cigniti
	 * @return
	 */
	public static Action actionMethod() {
		Action action = (Action) new Actions(CommonVariables.CommonDriver.get());
		return action;
	}

	public String GetCurrentTime() {
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss a");
		String currenttime = df.format(Calendar.getInstance().getTime());
		return currenttime;
	}

	/**
	 * @author Cigniti
	 * @param filename
	 */
	public void fileSeparator(String filename) {
		try {
			String workingDirectory = System.getProperty("user.dir").replace("\\", "/");
			String absoluteFilePath = "";
			String osname = System.getProperty("os.name");
			String fileSeparator = File.separator;
			if (osname.contains("windows")) {
				fileSeparator = "/";
			} else {
				if (osname.contains("linux") || osname.contains("Unix") || osname.contains("Mac")) {
					fileSeparator = "/";
				}
			}
			absoluteFilePath = workingDirectory + fileSeparator + filename;
			System.out.println("Final filepath : " + absoluteFilePath);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Cigniti
	 * @param workbookName
	 * @throws IOException
	 */
	public void DeleteFiles(String workbookName) throws IOException {
		try {
			System.out.println("Called deleteFiles");
			File file = new File(System.getProperty("user.dir") + "\\TestData\\" + workbookName);
			String[] myFiles;
			if (file.isDirectory()) {
				myFiles = file.list();
				for (int i = 0; i < myFiles.length; i++) {
					File myFile = new File(file, myFiles[i]);
					System.out.println(myFile);
					myFile.delete();
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * @author Cigniti
	 * @param waitTime
	 * @param pollingCycle
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void fluentWait(int waitTime, int pollingCycle) {
		try {
			FluentWait wait = new FluentWait(this.driver);
			wait.withTimeout(waitTime, TimeUnit.SECONDS);
			wait.pollingEvery(pollingCycle, TimeUnit.SECONDS);
			wait.ignoring(NoSuchElementException.class);
		} catch (Exception e) {
			System.out.println("unable to perform an action for fluent wait due to execption - " + e.getMessage());
		}
	}

	/**
	 * Wrap the Selenium run time exception with more debug info.
	 */
	protected String makeSeleniumExceptionMessage(Page page, Throwable e) {

		String result = "SeleniumException found on page " + page.getClass();
		String fullMessage = e.getMessage();
		return result + "\n" + fullMessage.substring(0, fullMessage.indexOf("\n"));
	}

	/**
	 * @author Cigniti
	 * @param folderPath
	 * @return
	 */
	public static File[] getFiles(String folderPath) {
		File arrayFiles[] = null;
		List<File> listFiles = new ArrayList<File>();
		traverseFolder(new File(folderPath), listFiles);
		// get the size of listFiles to initialize the size of array
		arrayFiles = new File[listFiles.size()];
		arrayFiles = listFiles.toArray(arrayFiles);

		return arrayFiles;
	}

	/**
	 * @author Cigniti
	 * @param file
	 * @param listFiles
	 */
	private static void traverseFolder(File file, List<File> listFiles) {
		// if current file is folder, look for files inside it.
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File aFile : files) {
				// recursive call
				traverseFolder(aFile, listFiles);
			}
		} else // add the file to list
		{
			listFiles.add(file);
		}
	}

	/**
	 * @author Cigniti readTextFile
	 * @param filePath
	 */
	public List<String> readTextFile(String filePath) {
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
	 * Robot mouse over.
	 * 
	 * @author Cigniti
	 * @param by
	 *            the by
	 * @throws Exception
	 *             the exception
	 */
	public void robotMouseOver(By by) throws Exception {
		try {
			Point point = this.driver.findElement(by).getLocation();
			int xcord = point.getX();
			int ycord = point.getY();
			Robot robot = new Robot();
			robot.mouseMove(xcord + 30, ycord + 100); // move mouse point to
														// specific location
			robot.delay(1500); // delay is to make code wait for mentioned
								// milliseconds before executing next step
			/*
			 * robot.mousePress(InputEvent.BUTTON1_DOWN_MASK); // press left
			 * click robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK); //
			 * release left click robot.delay(1500);
			 * robot.keyPress(KeyEvent.VK_DOWN); // press keyboard arrow key to
			 * select Save radio button Thread.sleep(2000);
			 * robot.keyPress(KeyEvent.VK_ENTER); Thread.sleep(3000);
			 */
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Wait for text.
	 *
	 * @param by
	 *            the by
	 * @param locator
	 *            the locator
	 * @param secs
	 *            the secs
	 * @return true, if successful
	 * @throws Throwable
	 *             the throwable
	 */
	public boolean waitForText(By by, String locator, int secs) throws Throwable {
		boolean status = false;
		try {
			WebDriverWait wait = new WebDriverWait(this.driver, 60);
			ScrollToElementVisible(by);
			wait.until(ExpectedConditions.textToBePresentInElementLocated(by, locator));
			for (int i = 0; i < secs / 2; i++) {
				List<WebElement> elements = this.driver.findElements(by);
				if (elements.size() > 0) {
					status = true;
					return status;
				} else {
					this.driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
				}
			}
		} catch (Exception e) {

			return status;
		}
		return status;
	}

	/**
	 * Close all other windows.
	 * 
	 * @author Cigniti
	 * @param driver
	 *            the driver
	 * @param openWindowHandle
	 *            the open window handle
	 * @return true, if successful
	 */
	public static boolean closeAllOtherWindows(WebDriver driver, String openWindowHandle) {
		Set<String> allWindowHandles = driver.getWindowHandles();
		for (String currentWindowHandle : allWindowHandles) {
			if (!currentWindowHandle.equals(openWindowHandle)) {
				driver.switchTo().window(currentWindowHandle);
				driver.close();
			}
		}
		driver.switchTo().window(openWindowHandle);
		if (driver.getWindowHandles().size() == 1)
			return true;
		else
			return false;
	}

	/**
	 * Select value from drop down for edge browser
	 * 
	 * @param elementBy
	 * @param selectionValue
	 */
	public void selectListValueEdge(By elementBy, String selectionValue) {
		try {
			WebElement webElement = this.driver.findElement(elementBy);
			webElement.click();
			webElement.sendKeys(selectionValue);
			webElement.sendKeys(Keys.ENTER);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * @author Cigniti Browse and upload.
	 * @param filePath
	 *            the file path
	 * @param locator
	 *            the locator
	 * @param locatorName
	 *            the locator name
	 * @return true, if successful
	 * @throws Throwable
	 *             the throwable
	 */
	public boolean browseAndUpload(String filePath, WebElement webElement) throws Throwable {
		boolean flag = false;
		try {
			Click(webElement);
			Thread.sleep(4000);
			// Copy the file's absolute path to the clipboard
			StringSelection ss = new StringSelection(filePath);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
			// native key strokes for CTRL, V and ENTER keys
			Robot robot = new Robot();
			Thread.sleep(2000);
			robot.keyPress(KeyEvent.VK_CONTROL);
			Thread.sleep(2000);
			robot.keyPress(KeyEvent.VK_V);
			Thread.sleep(2000);
			robot.keyRelease(KeyEvent.VK_V);
			Thread.sleep(2000);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			Thread.sleep(2000);
			robot.keyPress(KeyEvent.VK_ENTER);
			Thread.sleep(2000);
			robot.keyRelease(KeyEvent.VK_ENTER);
			Thread.sleep(2000);
			flag = true;

		} catch (NoAlertPresentException ex) {
			ex.printStackTrace();
		}
		return flag;
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
	public boolean uploadFile(String filePath, WebElement element, int time, String locatorName) throws Throwable {
		boolean flag = false;
		try {
			WebDriverWait wait = new WebDriverWait(this.driver, 60);
			ScrollToElementVisible(element);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			Click(element, time);
			Thread.sleep(5000);
			Actions KeyboardEvent = new Actions(this.driver);
			KeyboardEvent.sendKeys(filePath);
			Thread.sleep(1000);
			KeyboardEvent.sendKeys(Keys.ENTER);
			flag = true;
		} catch (NoAlertPresentException ex) {
			ex.printStackTrace();
			flag = false;
		}
		return flag;
	}

	/**
	 * @author Cigniti
	 */
	public void ScrollToTop(WebDriver driver) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,-1000)", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Cigniti
	 * @param element
	 */
	public void SwipeRight(WebElement element) {
		// Executing swipe on in the case of iOS simulators. Skipping it for
		// Android Chrome as this swipe will not yet implemented on it.
		try {
			String driverName = GetDriverInfo().get("DriverName").toLowerCase();
			if ((!driverName.equals("android-chrome")) && (!driverName.equals("chrome"))
					&& (!driverName.equals("chrome-mac")) && (!driverName.equals("safari"))
					&& (!driverName.equals("firefox")) && (!driverName.equals("ie")) && (!driverName.equals("edge"))
					&& (!driverName.equals("opera"))) {
				double browser_top_offset = 0.0;
				if (GetDriverInfo().get("DriverType").trim().equalsIgnoreCase("mobile")) {
					browser_top_offset = 0;
				} else if (GetDriverInfo().get("DriverType").trim().equalsIgnoreCase("tablet")) {
					browser_top_offset = 80;
				}
				/*
				 * if(ConfigManager.getProperties().getProperty("seleniumGrid").
				 * trim().equalsIgnoreCase("true")
				 * ||GetDriverInfo().get("DriverType").trim().equalsIgnoreCase(
				 * "mobile")
				 * ||GetDriverInfo().get("DriverType").trim().equalsIgnoreCase(
				 * "tablet")){ }
				 */
				// RemoteWebElement remoteelem = ((RemoteWebElement)element);
				WebElement remoteelem = element;
				JavascriptExecutor js = (JavascriptExecutor) driver;
				String script = "return Math.max(document.documentElement.clientHeight, window.innerHeight || 0)";
				Long pageheight1 = (Long) js.executeScript(script);
				Long pagewidth1 = (Long) js
						.executeScript("return Math.max(document.documentElement.clientWidth, window.innerWidth || 0)");
				// Long pageheight2=(Long)js.executeScript("return
				// window.innerHeight");
				Point eloc = remoteelem.getLocation();
				double yloc = eloc.getY();
				double xstartloc = eloc.getX();
				double xendloc = eloc.getX() + remoteelem.getSize().width;
				Double swipe_startxratio = xstartloc / pagewidth1;
				Double swipe_endxratio = xendloc / pagewidth1;
				double elemheight = remoteelem.getSize().getHeight() / 2;
				Double yratio = (yloc + elemheight / 2 + browser_top_offset) / pageheight1;
				if (swipe_startxratio < 0.1) {
					swipe_startxratio = 0.1;
				}
				if (swipe_endxratio > 0.9) {
					swipe_endxratio = 0.9;
				}
				/*
				 * HashMap<String, Double> swipeObject = new HashMap<String,
				 * Double>(); swipeObject.put("startX", swipe_endxratio);
				 * swipeObject.put("startY", yratio); swipeObject.put("endX",
				 * swipe_startxratio); swipeObject.put("endY", yratio);
				 * swipeObject.put("duration", 0.8); // js.executeScript(
				 * "mobile: swipe", swipeObject); appiumDriver.execute(
				 * "mobile: swipe", swipeObject);
				 */
				if (appiumDriver != null) {
					appiumDriver.swipe(swipe_endxratio.intValue(), yratio.intValue(), swipe_startxratio.intValue(),
							yratio.intValue(), 1);
				}
			}
			if (GetDriverInfo().get("DriverName").toLowerCase().equals("chrome")
					| GetDriverInfo().get("DriverName").toLowerCase().equals("chrome-mac")
					| GetDriverInfo().get("DriverName").toLowerCase().equals("internet_explorer")) {
				Actions builder = new Actions(driver);
				// element.getSize();
				Action dragAndDrop = builder.clickAndHold(element)
						.moveToElement(element, element.getLocation().x + 90, element.getLocation().y).release()
						.build();
				dragAndDrop.perform();
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
				}
			}
			if (GetDriverInfo().get("DriverName").toLowerCase().equals("safari")
					|| GetDriverInfo().get("DriverName").toLowerCase().equals("firefox")) {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("$('.owl-wrapper').trigger('owl.next')");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * @author Cigniti
	 * @description : Swipe Bottom
	 * @param element
	 */
	public void SwipeBottom(WebElement remoteelem) {
		if (remoteelem != null) {
			if (!GetDriverInfo().get("DriverName").equals("androidchrome")
					&& !GetDriverInfo().get("DriverType").toLowerCase().equals("desktop")) {
				double browser_top_offset = 0.0;
				if (GetDriverInfo().get("DriverType").trim().equalsIgnoreCase("mobile")) {
					browser_top_offset = 0;
				} else if (GetDriverInfo().get("DriverType").trim().equalsIgnoreCase("tablet")) {
					browser_top_offset = 240;
				}
				// RemoteWebElement remoteelem = ((RemoteWebElement)element);
				// JavascriptExecutor js = (JavascriptExecutor)driver;
				Point eloc = remoteelem.getLocation();
				double yloc = eloc.getY();
				double xloc = eloc.getX() + remoteelem.getSize().width / 2;
				Double swipe_xratio = xloc;
				double elemheight = remoteelem.getSize().getHeight();
				Double yStartRatio = (yloc + elemheight + browser_top_offset) / 2;
				Double yEndRatio = (eloc.getY() + browser_top_offset);
				if (swipe_xratio < 10.0) {
					swipe_xratio = 10.0;
				}
				if (yEndRatio < 50.0) {
					yEndRatio = 50.0;
				}
				HashMap<String, Double> swipeObject = new HashMap<String, Double>();
				swipeObject.put("startX", swipe_xratio);
				swipeObject.put("startY", yStartRatio);
				swipeObject.put("endX", swipe_xratio);
				swipeObject.put("endY", yEndRatio);
				swipeObject.put("duration", 1.0);
				// js.executeScript("mobile: swipe", swipeObject);
				if (appiumDriver != null && appiumDriver.getClass().toString().toUpperCase().contains("IOS")) {
					appiumDriver.swipe(swipe_xratio.intValue(), yStartRatio.intValue(), swipe_xratio.intValue(),
							yEndRatio.intValue(), 1);
				}
			}
		}
	}

	/**
	 * @author Cigniti
	 * @description : Swipe Left
	 * @param element
	 */
	public void SwipeLeft(WebElement element) {
		// Executing swipe on in the case of iOS simulators. Skipping it for
		// Android Chrome as this swipe will not yet implemented on it.
		if ((!GetDriverInfo().get("DriverName").toLowerCase().equals("androidchrome"))
				&& (!GetDriverInfo().get("DriverName").toLowerCase().equals("chrome"))
				&& (!GetDriverInfo().get("DriverName").toLowerCase().equals("chrome-mac"))
				&& (!GetDriverInfo().get("DriverName").toLowerCase().equals("safari"))) {
			if (appiumDriver != null) {
				double browser_top_offset = 0.0;
				if (GetDriverInfo().get("DriverType").trim().equalsIgnoreCase("mobile")) {
					browser_top_offset = 0;
				} else if (GetDriverInfo().get("DriverType").trim().equalsIgnoreCase("tablet")) {
					browser_top_offset = 80;
				}
				// RemoteWebElement remoteelem = ((RemoteWebElement)element);
				WebElement remoteelem = element;
				JavascriptExecutor js = (JavascriptExecutor) this.driver;
				String script = "return Math.max(document.documentElement.clientHeight, window.innerHeight || 0)";
				Long pageheight1 = (Long) js.executeScript(script);
				Long pagewidth1 = (Long) js
						.executeScript("return Math.max(document.documentElement.clientWidth, window.innerWidth || 0)");
				// Long pageheight2=(Long)js.executeScript("return
				// window.innerHeight");
				Point eloc = remoteelem.getLocation();
				double yloc = eloc.getY();
				double xstartloc = eloc.getX();
				double xendloc = eloc.getX() + remoteelem.getSize().width;
				Double swipe_startxratio = xstartloc / pagewidth1;
				Double swipe_endxratio = xendloc / pagewidth1;
				double elemheight = remoteelem.getSize().getHeight() / 2;
				Double yratio = (yloc + elemheight / 2 + browser_top_offset) / pageheight1;
				if (swipe_startxratio < 0.05) {
					swipe_startxratio = 0.05;
				}
				if (swipe_endxratio > .95) {
					swipe_endxratio = 0.95;
				}
				/*
				 * HashMap<String, Double> swipeObject = new HashMap<String,
				 * Double>(); swipeObject.put("startX", swipe_startxratio);
				 * swipeObject.put("startY", yratio); swipeObject.put("endX",
				 * swipe_endxratio); swipeObject.put("endY", yratio);
				 * swipeObject.put("duration", 0.8); js.executeScript(
				 * "mobile: swipe", swipeObject);
				 */
				appiumDriver.swipe(swipe_endxratio.intValue(), yratio.intValue(), swipe_startxratio.intValue(),
						yratio.intValue(), 1);
			}
		}
		if (GetDriverInfo().get("DriverName").toLowerCase().equals("chrome")
				| GetDriverInfo().get("DriverName").toLowerCase().equals("chrome-mac")) {
			Actions builder = new Actions(driver);
			Action dragAndDrop = builder.clickAndHold(element)
					.moveToElement(element, element.getLocation().x + 800, element.getLocation().y + 10).release()
					.build();
			dragAndDrop.perform();
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
			}
		}
		if (GetDriverInfo().get("DriverName").toLowerCase().equals("safari")) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("$('.owl-wrapper').trigger('owl.prev')");
		}
	}

	/**
	 * @author Cigniti
	 * @description : Pinch Open
	 */
	public void PinchOpen() {
		JavascriptExecutor js = (JavascriptExecutor) this.driver;
		HashMap<String, Double> swipeObject = new HashMap<String, Double>();
		swipeObject.put("startX", (double) 114);
		swipeObject.put("startY", (double) 198);
		swipeObject.put("endX", (double) 257);
		swipeObject.put("endY", (double) 256);
		swipeObject.put("duration", 1.8);
		js.executeScript("mobile: pinchOpen", swipeObject);
	}

	/**
	 * @author Cigniti
	 * @description : Pinch Close
	 */
	public void PinchClose() {
		JavascriptExecutor js = (JavascriptExecutor) this.driver;
		HashMap<String, Double> swipeObject = new HashMap<String, Double>();
		swipeObject.put("startX", (double) 150);
		swipeObject.put("startY", (double) 230);
		swipeObject.put("endX", (double) 200);
		swipeObject.put("endY", (double) 260);
		swipeObject.put("duration", 1.8);
		js.executeScript("mobile: pinchOpen", swipeObject);
	}

	/**
	 * @author Cigniti
	 * @description : Change Orientation - // "LANDSCAPELEFT" , "LANDSCAPERIGHT"
	 *              , "PORTRAIT"
	 * @param Orientation
	 */
	public void ChangeOrientation(String Orientation) { // Valid values are:
														// "LANDSCAPELEFT" ,
														// "LANDSCAPERIGHT" ,
														// "PORTRAIT"
		JavascriptExecutor js = (JavascriptExecutor) this.driver;
		js.executeScript("target.setDeviceOrientation(UIA_DEVICE_ORIENTATION_LANDSCAPERIGHT);");
	}
	
	public boolean mouseHoverByJavaScript(By locator, String locatorName)
			throws Throwable {
		boolean flag = false;
		try {
			WebElement mo = driver.findElement(locator);
			String javaScript = "var evObj = document.createEvent('MouseEvents');"
					+ "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);"
					+ "arguments[0].dispatchEvent(evObj);";
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(javaScript, mo);
			flag = true;
			return true;
		}

		catch (Exception e) {

			return false;
		} finally {
			if (!flag) {
				extentLogs.fail("MouseOver ",
						"MouseOver Action was <b>NOT</b> perform on <b>" + locatorName + "</b>");
			} else if (flag) {
				extentLogs.pass("MouseOver ",
						"MouserOver Action was performed on <b>" + locatorName + "</b>");
			}
		}
	}
	
	public  boolean mouseover(By locator, String locatorName)
			throws Throwable {
		boolean flag = false;
		try {
			WebElement mo = driver.findElement(locator);
			new Actions(driver).moveToElement(mo).build().perform();
			
			flag = true;
			return true;
		} catch (Exception e) {
			Assert.assertTrue(flag,"MouseOver action is not performed on " + locatorName);
			return false;
		} finally {
			if (!flag) {
				extentLogs.fail("MouseOver",
						"MouseOver action is not performed on " + locatorName);
				Assert.assertTrue(flag,"Unable find the element "+ locatorName);
			} else if (flag) {

				extentLogs.pass("MouseOver ",
						"MouserOver Action is performed on <b>" + locatorName + "</b>");
			}
		}
	}
}