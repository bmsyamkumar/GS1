package com.desktop.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.winium.WiniumDriver;
import com.google.common.base.Optional;
import com.web.automation.logs.ExtentLogs;
import com.web.automation.utilities.CommonVariables;

public class DesktopPage extends DesktopBasePage {

	public WiniumDriver driver;
	public ExtentLogs extentLogs=new ExtentLogs();
	Optional<Long> timeoutInSecond = Optional.of(Long.parseLong("5"));
	public DesktopPage (WiniumDriver driver)
	{
		super(driver);
	}

	/**
	 * @authorCigniti
	 * @description:
	 * @param value
	 * @return
	 * @throws Throwable 
	 */
	public Boolean teamviewer(String value) throws Throwable{
		Boolean isClicked=false;
		try{		
			desktopLib.type(desktopLib.FindElement(By.className("Edit"), Optional.of(Long.parseLong("2"))), value);
			Thread.sleep(Long.parseLong("1200"));
			extentLogs.pass("Team Viewer Id","User successfully entered team viewer id : ******* "+value+" *******");
			desktopLib.Click(desktopLib.FindElement(By.name("Connect to partner"), Optional.of(Long.parseLong("10"))),1);
			extentLogs.pass("Connect Partner Button","'Connect Partner' button clicked successfully.");
			isClicked=true;
		}
		catch(Exception ex){
			isClicked=false;
			throw ex;
		}
		return isClicked;
	}
	
	/**
	 * @authorCigniti
	 * @description:
	 * @param value
	 * @return
	 * @throws Throwable 
	 */
	public Boolean notepad(String value, String fileName) throws Throwable{
		Boolean isClicked=false;
		try{
			Thread.sleep(Long.parseLong("1000"));
			desktopLib.type(desktopLib.FindElement(By.className("Edit"), Optional.of(Long.parseLong("2"))), value);
			extentLogs.pass("Notepad Text","User successfully entered vale : ******* "+value+" ******* in notepad.");
			desktopLib.Click(desktopLib.FindElement(By.name("Close"), Optional.of(Long.parseLong("10"))),1);
			extentLogs.pass("Close Button","'Close' button clicked successfully to close notepad file.");
			desktopLib.Click(desktopLib.FindElement(By.name("Save"), Optional.of(Long.parseLong("10"))),1);
			extentLogs.pass("Save Button","'Save' button clicked successfully to save notepad file.");
			Thread.sleep(Long.parseLong("1000"));
			desktopLib.type(desktopLib.FindElement(By.name("File name:"), Optional.of(Long.parseLong("2"))), fileName);
			extentLogs.pass("Notepad File Name","User successfully entered file name : ******* "+fileName+" *******i8i.");
			desktopLib.Click(desktopLib.FindElement(By.name("Save"), Optional.of(Long.parseLong("10"))),1);
			extentLogs.pass("Save Button","'Save' button clicked successfully to save notepad file in fole dialog.");
			isClicked=true;
		}
		catch(Exception ex){
			isClicked=false;
			throw ex;
		}
		return isClicked;
	}
}