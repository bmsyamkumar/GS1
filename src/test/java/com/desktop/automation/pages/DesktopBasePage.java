package com.desktop.automation.pages;

import org.openqa.selenium.winium.WiniumDriver;
import com.desktop.automation.accelerators.DesktopLibrary;
import com.google.common.base.Optional;
import com.web.automation.logs.ExtentLogs;
import com.web.automation.utilities.CommonVariables;
import com.web.automation.utilities.ConfigManager;

public class DesktopBasePage
{
	public WiniumDriver driver;
	public DesktopLibrary desktopLib;
	public ExtentLogs extentLogs=new ExtentLogs();
	Optional<Long> timeoutInSecond = Optional.of(Long.parseLong("5"));
	
	public DesktopBasePage(WiniumDriver driver)
	{
		if(CommonVariables.getWiniumDriver()==null){
			CommonVariables.setDriver(driver);
		}
		desktopLib = CommonVariables.getDesktopActionLib();
		try {
			if(!ConfigManager.ArePropertiesSet.get()){
				ConfigManager.setProperties();
			}
		} 
		catch (Exception e) {
			System.out.println("Failed to load Properties file");
		}
	}

	public Optional<Long> timeout(Integer time)
	{
		return Optional.of(Long.parseLong(String.valueOf(time)));
	}
}