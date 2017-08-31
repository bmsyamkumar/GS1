package com.web.automation.scripts;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.mobile.automation.accelerators.MobileLocator;
import com.mobile.automation.accelerators.MobileActionLibrary;

public class Mobile_Demo {
	@Test
	public void sampleMobile()
	{
		System.out.println("Sample");
	}
	@SuppressWarnings("raws")
	private static AndroidDriver driver;
	private static MobileActionLibrary mob_actionLib;
	public String appName = "test1.apk";

	@org.testng.annotations.AfterMethod
	public void afterMethod() {
		mob_actionLib.ShutDownDriver();
	}

	@org.testng.annotations.BeforeMethod
	public void beforeMethod() {
		mob_actionLib = new MobileActionLibrary(driver);
	}

	@org.testng.annotations.Test(priority = 1)
	public void verifySwitchingBtwDifferentViews() {
		boolean passflag = true;
		try {
			mob_actionLib.DriverNoResetFlag = false;
			appName = "BestWestern.apk";
			this.driver = mob_actionLib.StartAppiumDriver(appName);
			mob_actionLib.type(mob_actionLib.FindElement(MobileLocator.ById,"com.piti.webviewtester:id/weburl", 2), "http://www.bing.com/");
			mob_actionLib.click(mob_actionLib.FindElement(MobileLocator.ById,"com.piti.webviewtester:id/websubmit", 2));
			mob_actionLib.driver = mob_actionLib.SwitchToWebView(mob_actionLib.driver);
			mob_actionLib.click(mob_actionLib.FindElement(By.xpath("//*[@='submit']"),2));
			mob_actionLib.type(mob_actionLib.FindElement(By.xpath("//input[1]"),2), "Gallop");
			mob_actionLib.click(mob_actionLib.FindElement(By.xpath("//*[@='submit']"),2));
			driver.navigate().back();
			driver = mob_actionLib.SwichToNativeView(mob_actionLib.driver);
			System.out.println(driver.getContext());
			driver.navigate().back();
		} catch (Exception e) {
			passflag = false;
		}
		if (passflag == false) {
			Assert.fail();
		}
	}

	@org.testng.annotations.Test(priority = 1)
	public void launchResetCloseAndRemoveApp() {
		boolean passflag = true;
		try {
			mob_actionLib.DriverNoResetFlag = false;
			appName = "test1.apk";
			this.driver = mob_actionLib.StartAppiumDriver(appName);
			mob_actionLib.type(mob_actionLib.FindElement(MobileLocator.ById,"com.piti.webviewtester:id/weburl", 2), "http://www.bing.com/");
			mob_actionLib.click(mob_actionLib.FindElement(MobileLocator.ById,"com.piti.webviewtester:id/websubmit", 2));
			driver.resetApp();
			driver.closeApp();
			try {
				driver.launchApp();
			} catch (Exception e) {
				// Trying to start logcat capture but it's already started.
				e.printStackTrace();
			}
			mob_actionLib.removeApp(appName);
			if(	!driver.isAppInstalled("com.piti.webviewtester")){
				System.out.println("App removed successfully.");
			}
		} catch (Exception e) {
			passflag = false;
		}
		if (passflag == false) {
			Assert.fail();
		}
	}

	@org.testng.annotations.Test(priority = 1)
	public void setGeoLocationUseUiAutomatorMethodDirectly() {
		boolean passflag = true;
		try {
			mob_actionLib.DriverNoResetFlag = false;
			appName = "test2.apk";
			this.driver = mob_actionLib.StartAppiumDriver(appName);
			// http://www.mapcoordinates.net/en
			mob_actionLib.setGeoLocation(42.346886, -71.075529);
			WebElement elem = mob_actionLib.driver.findElementByAndroidUIAutomator("new UiSelector().text(\"Later\")");
			System.out.println(elem.isEnabled());
			elem.click();
		} catch (Exception e) {
			passflag = false;
		}
		if (passflag == false) {
			Assert.fail();
		}
	}

	@org.testng.annotations.Test(priority = 1)
	public void HideKeyboardLockScreenRunAppInBackground() {
		boolean passflag = true;
		try {
			mob_actionLib.DriverNoResetFlag = true;
			appName = "BestWestern.apk";
		//	this.driver =	mob_actionLib.switchToAnotherApp(appPath, appPackage, appActivityStart);
			this.driver = mob_actionLib.StartAppiumDriver(appName);
			WebElement elem = driver.findElementById("com.piti.webviewtester:id/weburl");
			elem.sendKeys("Gallop");
			driver.hideKeyboard();
			lockScreen(10);
			runAppInBackgroud(4);
		} catch (Exception e) {
			passflag = false;
		}
		if (passflag == false) {
			Assert.fail();
		}
	}

	@org.testng.annotations.Test(priority = 1)
	public void switchBtwDifferentAppActivityAndScrollInList() {
		mob_actionLib.DriverNoResetFlag = false;
		appName = "test1.apk";
		this.driver = mob_actionLib.StartAppiumDriver(appName);
		try {driver.hideKeyboard();} catch (Exception e) {}
		this.driver = mob_actionLib.switchToAnotherApp("","com.android.settings", "com.android.settings.Settings");
		driver.scrollTo("Apps");
		mob_actionLib.click(mob_actionLib.FindElement(MobileLocator.ByName, "Apps", 2));
		driver.scrollTo("WebView Tester");
		mob_actionLib.click(mob_actionLib.FindElement(MobileLocator.ByName	, "WebView Tester", 2));
		driver.navigate().back();
		mob_actionLib.SwipeLeft(mob_actionLib.FindElement(MobileLocator.ById	, "com.android.settings:id/app_name", 2));
		driver.navigate().back();
		mob_actionLib.SwipeLeft(mob_actionLib.FindElement(MobileLocator.ByClassName	, "android.widget.GridLayout", 2));
		driver = mob_actionLib.switchToAnotherApp("","com.piti.webviewtester", ".MainActivity");
	}

	@org.testng.annotations.Test(priority = 1)
	public void pullPushFile(){
		try{
			mob_actionLib.DriverNoResetFlag = true;
			appName = "test4.apk";
			this.driver = mob_actionLib.StartAppiumDriver(appName);
			mob_actionLib.type(mob_actionLib.FindElement(MobileLocator.ById,"test4:id/file_name", 2), "systemLogsInfo");
			mob_actionLib.click(mob_actionLib.FindElement(MobileLocator.ById,"test4:id/take_log", 2));
			if(mob_actionLib.FindElement(MobileLocator.ById,"android:id/alertTitle", 2)!=null){
				mob_actionLib.driver.navigate().back();
			}
			if(mob_actionLib.FindElement(MobileLocator.ById,"android:id/action_bar_title", 2)!=null){
				mob_actionLib.driver.navigate().back();
			}
			String timestamp = new SimpleDateFormat("yyyy_MM_dd_hh_mm_sss_a").format(new Date());
			File appDir = new File(System.getProperty("user.dir")+ "\\downloadFiles");
			if(!appDir.exists()){
				appDir.mkdirs();
			}
			appDir = new File(System.getProperty("user.dir")+ "\\downloadFiles\\"+timestamp);
			appDir.mkdirs();
			mob_actionLib.downloadFile("/mnt/sdcard/Syslog",appDir.getAbsolutePath());
			appDir = new File(System.getProperty("user.dir")+ "\\downloadFiles\\fileToUpload.txt");
			mob_actionLib.uploadFile(appDir.getAbsolutePath(),"/mnt/sdcard/Syslog");
		}
		catch(Exception es){
			es.printStackTrace();
		}
	}

	@org.testng.annotations.Test(priority = 1)
	public void mobileGesture() {
		try{
			mob_actionLib.DriverNoResetFlag = true;
			appName = "Demo1.apk";
			this.driver = mob_actionLib.StartAppiumDriver(appName);
			System.out.println("Current Activity nam: "+driver.currentActivity());
			//TAP on an element
			mob_actionLib.AndroidTap(mob_actionLib.FindElement(MobileLocator.ByName,"hierarchy", 2));
			AndroidElement scrollElem = (AndroidElement) driver.scrollTo("Selenium");
			System.out.println(scrollElem.getText());
			WebElement list = mob_actionLib.FindElement(MobileLocator.ById,"android:id/list", 2);
			List<WebElement> listItems = list.findElements(MobileBy.id("android:id/text1"));
			for(WebElement item : listItems){
				if(mob_actionLib.GetElementAttributeValue(item, "text").trim().equalsIgnoreCase("Selenium")){
					mob_actionLib.SwipeRight(item);
					break;
				}
			}
			// Change Mobile Orientation
			mob_actionLib.ChangeOrientation(ScreenOrientation.LANDSCAPE);
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	@org.testng.annotations.Test(priority = 2)
	public void VerifyMobileGesturesOnRealDevice() {
		boolean passflag = true;
		try {
			mob_actionLib.DriverNoResetFlag = true;
			appName = "test2.apk";
			mob_actionLib.StartAppiumDriver(appName);
			this.driver = mob_actionLib.driver;
			executeSomeMethodsOnRealDevice();
		} catch (Exception e) {
			passflag = false;
		}
		if (passflag == false) {
			Assert.fail();
		}
	}

	public void executeSomeMethodsOnRealDevice() {
		try{
			//Tap to Open Menu
			driver.tap(1, ((MobileElement) mob_actionLib.FindElement(MobileLocator.ById,"test2:id/action.home.menu", 5)), 1);

			//'Scrolling to the Sub Menu name 'Events' and then 'Home'
			driver.scrollTo("Events");
			driver.scrollToExact("Home");

			//Swipe left from Home 
			WebElement elem = mob_actionLib.FindElement(MobileLocator.ById,"test2:id/row_title", 5);
			Integer pagewidth  = driver.manage().window().getSize().width;
			Point eloc = elem.getLocation();
			Integer swipe_startxratio = eloc.getX();
			Integer swipe_endxratio = eloc.getX() + elem.getSize().width;
			Integer yratio =eloc.getY()+(elem.getSize().getHeight())/ 2;
			if (swipe_startxratio < 10) {
				swipe_startxratio = 10;
			}
			if (swipe_endxratio >= pagewidth) {
				swipe_endxratio = (int) (pagewidth-10);
			}
			driver.swipe(swipe_endxratio, yratio, swipe_startxratio, yratio, 2000);

			// Get Current Device Orientation
			ScreenOrientation orient = driver.getOrientation();
			String currentOrientation = orient.toString();
			System.out.println(currentOrientation);

			// Set Device Orientation to Landscape and then PORTRAIT
			driver.rotate(ScreenOrientation.LANDSCAPE);
			try{Thread.sleep(2500);} catch(InterruptedException e){}
			driver.rotate(ScreenOrientation.PORTRAIT);

			//Check that app goes to Background for a given period of time and then appears in foreground
			try {
				driver.runAppInBackground(5);
			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
			}

			//Check hiding of the keyboard
			mob_actionLib.AndroidTap(mob_actionLib.FindElement(MobileLocator.ById,"test2:id/action.setting", 5));
			mob_actionLib.AndroidTap(mob_actionLib.FindElement(MobileLocator.ById,"test2:id/tv_user_name", 5));
			mob_actionLib.AndroidTap(mob_actionLib.FindElement(MobileLocator.ById,"test2:id/b.login.details.email", 5));
			mob_actionLib.FindElement(MobileLocator.ById,"test2:id/b.login.details.email", 5).sendKeys("abc");
			driver.hideKeyboard();

			//Verify Lock Screen and Once screen unlocked correct activity
			lockScreen(5);
			driver.tap(1, ((MobileElement) mob_actionLib.FindElement(MobileLocator.ById,"test2:id/action.home.menu", 5)), 1);
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	public void lockScreen(int timeInSecond){
		String activitybeforeLock = driver.currentActivity();
		driver.lockScreen(timeInSecond);
		try{Thread.sleep(timeInSecond);} catch(InterruptedException e){}
		String activityAfterUnlock =driver.currentActivity();
		if(activityAfterUnlock.equalsIgnoreCase(activitybeforeLock)){
			System.out.println("pass");
		}
		else{
			System.out.println("fail");
		}
	}

	public void runAppInBackgroud(int timeInSecond){
		String activityRunningBeforeHide = driver.currentActivity();
		driver.runAppInBackground(timeInSecond);
		try{Thread.sleep(timeInSecond);} catch(InterruptedException e){}
		String activityAfterAppActiveAgain =driver.currentActivity();
		if(activityAfterAppActiveAgain.equalsIgnoreCase(activityRunningBeforeHide)){
			System.out.println("passs");
		}
		else{
			System.out.println("fail");
		}
	}

	public void login() {
		try {
			mob_actionLib.AndroidTap(mob_actionLib.FindElement(MobileLocator.ByName,"login", 2));
			WebElement userName = mob_actionLib.FindElement(MobileLocator.ById,"test3.demo1:id/login_usr", 2);
			mob_actionLib.ClearInputField(userName);
			userName.sendKeys("demo");
			WebElement password = mob_actionLib.FindElement(MobileLocator.ById,"test3.demo1:id/login_pwd", 2);
			mob_actionLib.ClearInputField(password);
			password.sendKeys("demo");	
			WebElement loginBtn = mob_actionLib.FindElement(MobileLocator.ById,"test3.demo1:id/login_btn", 2);
			loginBtn.click();
			WebElement welcomeMsg = mob_actionLib.FindElement(MobileLocator.ById,"test3.demo1:id/logout_txt", 10);
			if(welcomeMsg==null || !welcomeMsg.getText().trim().equalsIgnoreCase("Welcome, demo!")){
				Assert.fail("Failed to login");
			}
		} catch (Exception e) {
		}
	}
}