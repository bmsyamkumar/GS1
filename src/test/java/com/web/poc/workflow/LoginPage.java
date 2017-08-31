package com.web.poc.workflow;


import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.server.handler.SendKeys;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Optional;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.web.automation.accelerators.ActionsLibrary;
import com.web.automation.logs.ExtentLogs;
import com.web.automation.pages.BasePage;
import com.web.automation.utilities.ConfigManager;
import com.web.poc.testObject.GS1InputData;
import com.web.poc.testObject.GS1_LoginPage_Locators;
import com.web.poc.testObject.GS1_HomePage_Locators;

public class LoginPage extends BasePage {	
	public WebDriver driver=ActionsLibrary.driver;
	//public EventFiringWebDriver driver;
	public ExtentLogs extentLogs = new ExtentLogs();
	Optional<Long> timeoutInSecond = Optional.of(Long.parseLong("5"));

	public LoginPage(WebDriver driver) {
		super(driver);
		
	}
	
	
	//GS1 Login page functions
	public boolean fnLogin(GS1InputData objData) throws Throwable
	{
		boolean blnStatus=false;
		try
		{
			extentLogs.info("GS1 Login","GS1 Login");
						
			if(actionLib.waitForElementPresent(GS1_LoginPage_Locators.userName_txt, 15))
			{				
				actionLib.type(GS1_LoginPage_Locators.userName_txt, objData.Username, "UserName Field");
				actionLib.type(GS1_LoginPage_Locators.password_txt, objData.Password, "Password Field");
				actionLib.Click(GS1_LoginPage_Locators.login_btn, "Login Button");
				actionLib.waitForElementPresent(GS1_HomePage_Locators.logout_lnk, 15);
				blnStatus=true;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			blnStatus=false;
		}
		return blnStatus;
	}

	
	
}




