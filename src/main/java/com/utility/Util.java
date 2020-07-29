package com.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Util {

	// Read Property File
	public static String getPropertyfileData(String key) {

		Properties prop = null;
		try {
			prop = new Properties();
			File file = Exceloperations.getFile(Constants.MAINRESOURCEPATH, Constants.CONFIGPROPERTYFILE);
			FileInputStream inputstream = Exceloperations.getFileinputStream(file);
			prop.load(inputstream);
		} catch (IOException e) {
			Logutil.info(e);
		}

		return prop.getProperty(key);
	}

	// Generic Methods
	public static void sendkeys(WebDriver driver, By locator, String val) {
		explicitwait(driver, locator);
		WebElement ele = Util.findElement(driver, locator);
		ele.clear();
		ele.sendKeys(val);

	}

	public static void sendkeywithJS(WebDriver driver, By locator, String val) {
		WebElement wb = driver.findElement(locator);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].value='" + val + "';", wb);

	}

	public static void refresh(WebDriver driver) {
		driver.navigate().refresh();
	}

	// clicks
	public static void click(WebDriver driver, By locator) {
		explicitwait(driver, locator);

		try {
			driver.findElement(locator).click();
		} catch (ElementClickInterceptedException | StaleElementReferenceException e) {

			try {
				WebElement element = driver.findElement(locator);
				explicitwaitclickable(driver, element);
			} catch (Exception e2) {

				Logutil.info("StackTrace Exception ");
				e2.printStackTrace();
				Logutil.info(e2);

			}

		}

	}

	// Click with JavaScript
	public static void clickElementwithjs(WebDriver driver, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		try {
			js.executeScript("arguments[0].click();", element);
			Logutil.info("Clicked on Element Done with JS");
		} catch (Exception e) {
			Logutil.info("Exception in clickElementwithjs");
			Logutil.info(e);
		}
	}

	// Click with Action Class
	public static void clickElementwithactions(WebDriver driver, WebElement element) {

		Actions action = new Actions(driver);

		try {
			action.moveToElement(element).click().build().perform();
		} catch (Exception e) {
			Logutil.info("Exception in clickElementwithactions");
			Logutil.info(e);
		}

	}

	// webelement

	public static WebElement findElement(WebDriver driver, By locator) {

		WebElement element = null;
		try {
			element = driver.findElement(locator);
		} catch (NoSuchElementException | StaleElementReferenceException | TimeoutException e) {

			Logutil.info("Element not Found Exception in findElement");
			Logutil.info(e);
		}

		return element;
	}

	// getText

	public static String getTextofElement(WebDriver driver, By locator) {
		String text = null;
		try {
			text = driver.findElement(locator).getText();
		} catch (Exception e) {

			text = driver.findElement(locator).getText();
		}

		return text;

	}

	// Convert String to int
	public static int convertToInt(String text) {
		int number = Integer.parseInt(text);

		return number;
	}

	// Convert int to String
	public static String convertToString(int number) {
		String text = Integer.toString(number);

		return text;
	}

	// Waits
	public static void implicitlyWait(WebDriver driver, int seconds) {
		driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);

	}

	public static void explicitwait(WebDriver driver, By element) {

		WebDriverWait wait = new WebDriverWait(driver, 30);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(element));

		} catch (TimeoutException | NoSuchElementException e) {

			Logutil.info("Element not Found");
			Logutil.info(e);

		}

	}

	public static void waitUntilelementNotvisible(WebDriver driver, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));

	}

	public static void explicitwaitclickable(WebDriver driver, WebElement element) {

		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(element));
		clickElementwithactions(driver, element);
	}

	// Select Dropdown value
	public static void selectDropdownvalue(WebDriver driver, WebElement dropdownelement, By dropdownvaluelocator) {

		// Click on Dropdown element
		clickElementwithactions(driver, dropdownelement);

		// Click on Dropdown value Element

		Util.explicitwait(driver, dropdownvaluelocator);
		WebElement dropdownvalue = driver.findElement(dropdownvaluelocator);
		clickElementwithactions(driver, dropdownvalue);

	}

	// Screen Shots
	public static String getScreenshot(WebDriver driver, String screenshotName) {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);

		String destination = Constants.TESTSCREENSHOTPATH + screenshotName + dateName + ".png";
		File finalDestination = new File(destination);
		try {
			FileUtils.copyFile(source, finalDestination);
		} catch (IOException e) {

			Logutil.info(e);
		}
		return destination;
	}

	// Scroll
	public static JavascriptExecutor scrolltoBottom(WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

		return js;
	}

	public static JavascriptExecutor scrolltillElementvisible(WebDriver driver, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("arguments[0].scrollIntoView();", element);
		return js;
	}

	// Hover
	public static void hoveronElement(WebDriver driver, WebElement element) {

		Actions action = new Actions(driver);
		action.moveToElement(element).build().perform();
	}

	// Element is display or not
	public static boolean isElementDisplayed(WebDriver driver, By locator) {

		WebElement element = driver.findElement(locator);
		try {
			WebDriverWait wait = new WebDriverWait(driver, 3);
			wait.until(ExpectedConditions.visibilityOf(element));

			boolean eledisplay = element.isDisplayed();
			Logutil.info("Element display " + eledisplay);

			return eledisplay;
		} catch (org.openqa.selenium.NoSuchElementException | org.openqa.selenium.StaleElementReferenceException
				| org.openqa.selenium.TimeoutException e) {
			return false;
		}
	}

	// To send Email
	public static void sendEmail(String senderEmailid,String senderPassword,String receivedEmailid) {

		// Create object of Property file
		Properties props = new Properties();

		// this will set host of server- you can change based on your requirement
		props.put("mail.smtp.host", "smtp.gmail.com");

		// set the port of socket factory
		props.put("mail.smtp.socketFactory.port", "465");

		// set socket factory
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		// set the authentication to true
		props.put("mail.smtp.auth", "true");

		// set the port of SMTP server
		props.put("mail.smtp.port", "465");

		// This will handle the complete authentication
		Session session = Session.getDefaultInstance(props,

				new javax.mail.Authenticator() {

					protected PasswordAuthentication getPasswordAuthentication() {

						return new PasswordAuthentication(senderEmailid, senderPassword);

					}

				});

		try {

			// Create object of MimeMessage class
			Message message = new MimeMessage(session);

			// Set the from address
			message.setFrom(new InternetAddress(senderEmailid));

			// Set the recipient address
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receivedEmailid));

			// Add the subject link
			message.setSubject("Test Automation Report");

			// Create object to add multimedia type content
			BodyPart messageBodyPart1 = new MimeBodyPart();

			// Set the body of email
			messageBodyPart1.setText("Please click on attachment file");

			// Create another object to add another content
			MimeBodyPart messageBodyPart2 = new MimeBodyPart();

			// Mention the file which you want to send
			String filename = Constants.EXTENTREPORT;

			// Create data source and pass the filename
			DataSource source = new FileDataSource(filename);

			// set the handler
			messageBodyPart2.setDataHandler(new DataHandler(source));

			// set the file
			messageBodyPart2.setFileName(filename);

			// Create object of MimeMultipart class
			Multipart multipart = new MimeMultipart();

			// add body part 1
			multipart.addBodyPart(messageBodyPart2);

			// add body part 2
			multipart.addBodyPart(messageBodyPart1);

			// set the content
			message.setContent(multipart);

			// finally send the email
			Transport.send(message);

			Logutil.info("=====Email Sent=====");

		} catch (MessagingException e) {

			throw new RuntimeException(e);

		}
	}

}
