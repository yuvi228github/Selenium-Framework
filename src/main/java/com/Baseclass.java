package com;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.utility.Constants;

public class Baseclass {

	public static WebDriver driver;

	public static void webLaunch(String browser) {

		if (browser.equalsIgnoreCase("Edge")) {
			System.setProperty("webdriver.edge.driver", Constants.EDGEDRIVERPATH);
			driver = new EdgeDriver();
		} else if (browser.equalsIgnoreCase("Chrome")) {
			System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-extensions", "--incognito");
			driver = new ChromeDriver(options);

		} else if (browser.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();

		} else if (browser.equalsIgnoreCase("IE")) {
			driver = new InternetExplorerDriver();
		}
		
		EventFiringWebDriver eventdriver = new EventFiringWebDriver(driver);
		EventListener event = new EventListener();
		eventdriver.register(event);
		driver = eventdriver;
		
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();

	}

}
