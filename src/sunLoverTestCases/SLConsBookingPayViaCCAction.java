package sunLoverTestCases;

import org.openqa.selenium.support.PageFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import automationFramework.Driver;
import automationFramework.XLReader;

import pageObjects.SLCVPaymentPgObj;
import pageObjects.SLVCBroucherPgObj;
import pageObjects.SLVCHomePgObj;
import sunLoverModule.SLConHomePageAction;
import utilities.SelectDropdown;

public class SLConsBookingPayViaCCAction {

	SLVCBroucherPgObj BrouchPgobj = PageFactory.initElements(Driver.driver, SLVCBroucherPgObj.class);
	SLConHomePageAction homePgFact = PageFactory.initElements(Driver.driver, SLConHomePageAction.class);
	SLVCHomePgObj homePgObj = PageFactory.initElements(Driver.driver, SLVCHomePgObj.class);
	SLCVPaymentPgObj payPgobj = PageFactory.initElements(Driver.driver, SLCVPaymentPgObj.class);
	SelectDropdown drpdwn = PageFactory.initElements(Driver.driver, SelectDropdown.class);
	XLReader excel = new XLReader();
	String propertyFilePath = "D:\\Automation Project\\iResConsumerSites\\InvalidCard.properties";
	WebDriverWait wait = new WebDriverWait(Driver.driver, 5000);

	/*
	 * public void Enter_CheckIn_out_details() throws Exception { try { Actions
	 * actions = new Actions(Driver.driver);
	 * actions.keyUp(Keys.CONTROL).sendKeys(Keys.HOME).perform(); String
	 * checkInMonth = excel.getCellData(7, 3); String checkInDay =
	 * excel.getCellData(7, 4); String checkOutMonth = excel.getCellData(7, 5);
	 * String checkOutDay = excel.getCellData(7, 6);
	 * homePgFact.select_CheckInmonth(checkInMonth);
	 * homePgFact.select_CheckOutmonth(checkOutMonth);
	 * actions.keyUp(Keys.CONTROL).sendKeys(Keys.HOME).perform(); String nights
	 * = excel.getCellData(7, 7); homePgFact.enter_NightsToStay(nights);
	 * homePgFact.select_CheckInDay(checkInDay);
	 * homePgFact.select_CheckOutDay(checkOutDay); String adult =
	 * excel.getCellData(7, 7); String child = (String) excel.getCellData(7, 8);
	 * System.out.println("Pax Details: " + adult + "&" + child);
	 * homePgFact.select_Adults(adult); homePgObj.getSearch_button().click(); //
	 * Wait for room loader
	 * wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(
	 * "roomPanelWait")));
	 * 
	 * } catch (Exception ae) { ae.printStackTrace(); } }
	 */
	// String CardType=PayPageObj.getCardType();

	public boolean validate_Credit_Card(String CardType) {

		try {
			System.out.println("Now Entering CC details");
			String Current_Dir = System.getProperty("user.dir");
			System.out.println("Current working dir: " + Current_Dir);
			String relative_path = Current_Dir.concat(propertyFilePath);
			File file = new File(relative_path);

			FileInputStream fileInput = null;
			try {
				fileInput = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			Properties prop = new Properties();

			// load properties file
			try {
				prop.load(fileInput);
			} catch (IOException e) {
				e.printStackTrace();
			}
			payPgobj.getCardHolderName().sendKeys(prop.getProperty("cardHoldername"));
			Select selCardType = new Select(payPgobj.getCardType());
			selCardType.selectByVisibleText(CardType);
			String selected_CardType = selCardType.getFirstSelectedOption().getText();
			System.out.println("Credit Card selected: " + selected_CardType);

			if (selected_CardType.contentEquals("MASTERCARD")) {
				System.out.println("Entering mastercard details...");
				payPgobj.getCardNum().sendKeys(prop.getProperty("MasterCardNo"));
				Thread.sleep(3000);
				drpdwn.selectElement(payPgobj.getExpiryDate_mm(), prop.getProperty("expiryMonth"));
				drpdwn.selectElement(payPgobj.getExpiryDate_yy(), prop.getProperty("expiryYear"));
				payPgobj.getCVV().sendKeys(prop.getProperty("SecurityCode1"));
				payPgobj.getBookNPayNow_button().click();
				String actText = payPgobj.get_Error_Msg().getText();
				String expText = "Please enter a valid credit card number";
				Assert.assertEquals(actText, expText);
				System.out.println("MasterCard Number in Invalid");

			}

			else {
				if (selected_CardType.contentEquals("AMERICAN EXPRESS")) {
					System.out.println("Entering american express details...");
					payPgobj.getCardNum().sendKeys(prop.getProperty("AmericanExpress"));
					drpdwn.selectElement(payPgobj.getExpiryDate_mm(), prop.getProperty("expiryMonth"));
					drpdwn.selectElement(payPgobj.getExpiryDate_yy(), prop.getProperty("expiryYear"));
					payPgobj.getCVV().sendKeys(prop.getProperty("SecurityCode2"));
					payPgobj.getBookNPayNow_button().click();
					String actText = payPgobj.get_Error_Msg().getText();
					String expText = "Please enter a valid credit card number";
					Assert.assertEquals(actText, expText);
					System.out.println("AMERICANCard Number in Invalid");

				} else {
					if (selected_CardType.contentEquals("Visa")) {
						System.out.println("Entering visa details...");
						payPgobj.getCardNum().sendKeys(prop.getProperty("VisaCardNo"));
						drpdwn.selectElement(payPgobj.getExpiryDate_mm(), prop.getProperty("expiryMonth"));
						drpdwn.selectElement(payPgobj.getExpiryDate_yy(), prop.getProperty("expiryYear"));
						payPgobj.getCVV().sendKeys(prop.getProperty("SecurityCode1"));
						payPgobj.getBookNPayNow_button().click();
						String actText = payPgobj.get_Error_Msg().getText();
						String expText = "Please enter a valid credit card number";
						Assert.assertEquals(actText, expText);
						System.out.println("VisaNumber in Invalid");
					} else {
						if (selected_CardType.contentEquals("Diners")) {
							System.out.println("Entering Dinners details...");
							payPgobj.getCardNum().sendKeys(prop.getProperty("Diners"));
							drpdwn.selectElement(payPgobj.getExpiryDate_mm(), prop.getProperty("expiryMonth"));
							drpdwn.selectElement(payPgobj.getExpiryDate_yy(), prop.getProperty("expiryYear"));
							payPgobj.getBookNPayNow_button().click();
							String actText = payPgobj.get_Error_Msg().getText();
							String expText = "Please enter a valid credit card number";
							Assert.assertEquals(actText, expText);
							System.out.println("Dinners Number in Invalid");
						} else {

							if (payPgobj.get_Progress_Bar().isDisplayed()) {
								System.out.println(
										"Test case is Failed" + "Booking is process even after Wrong Card entry");
							}

							return false;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean validate_CC_Expiry() {
		return false;

	}

	public boolean validate_CC_Security_Code() {
		return false;

	}

}
