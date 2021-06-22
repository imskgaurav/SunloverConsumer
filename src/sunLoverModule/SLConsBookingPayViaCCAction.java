/**
 * 
 */
package sunLoverModule;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.AssertJUnit;

import automationFramework.Driver;
import automationFramework.XLReader;
import pageObjects.SLCVPaymentPgObj;
import utilities.SelectDropdown;

/**
 * @author tetambes
 *
 */
public class SLConsBookingPayViaCCAction {

	SLConHomePageAction homePgFact = PageFactory.initElements(Driver.driver,
			SLConHomePageAction.class);
	XLReader excel = new XLReader();
	JavascriptExecutor jse = (JavascriptExecutor) Driver.driver;
	SLCVPaymentPgObj payPgobj = PageFactory.initElements(Driver.driver,
			SLCVPaymentPgObj.class);
	SelectDropdown drpdwn = PageFactory.initElements(Driver.driver,
			SelectDropdown.class);
	String propertyFilePath = "\\src\\testData\\Card Details.properties";

	public void Enter_CheckIn_out_details(int rowNum) throws Exception {
		try {
			Thread.sleep(3000);
			String checkInMonth = excel.getCellData(rowNum, 3);
			String checkInDay = excel.getCellData(rowNum, 4);
			String checkOutMonth = excel.getCellData(rowNum, 5);
			String checkOutDay = excel.getCellData(rowNum, 6);
			homePgFact.select_CheckInmonth(checkInMonth);
			homePgFact.select_CheckInDay(checkInDay);
			Thread.sleep(3000);
			jse.executeScript("scroll(250, 0)");
			homePgFact.select_CheckOutmonth(checkOutMonth);
			homePgFact.select_CheckOutDay(checkOutDay);
			String nights = excel.getCellData(rowNum, 7);
			homePgFact.enter_NightsToStay(nights);
			String adult = excel.getCellData(rowNum, 8);
			String child = (String) excel.getCellData(rowNum, 9);
			String childAge = excel.getCellData(rowNum, 10);
			System.out.println("Pax Details: " + adult + "&" + child);
			homePgFact.select_Adults(adult);
			homePgFact.select_Child_specify_age(child, childAge);
			} 
		catch (Exception ae) {
				ae.printStackTrace();
				AssertJUnit.fail(ae.getMessage());
			}
	}

	// This method is to enter invalid credit card details as per card type
	// selection
	public String Enter_InValid_CC_Details(String CardType)
	// TODO Auto-generated method stub
	{
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
			payPgobj.getCardHolderName()
					.sendKeys(prop.getProperty("cardHoldername"));
			Select selCardType = new Select(payPgobj.getCardType());
			selCardType.selectByVisibleText(CardType);
			String selected_CardType = selCardType.getFirstSelectedOption()
					.getText();
			System.out.println("Credit Card selected: " + selected_CardType);

			if (selected_CardType.contentEquals("MASTERCARD")) {
				System.out.println("Entering mastercard details...");
				payPgobj.getCardNum()
						.sendKeys(prop.getProperty("wrongMasterCard"));
				Thread.sleep(3000);
				drpdwn.selectValueFromElement(payPgobj.getExpiryDate_mm(),
						prop.getProperty("InvalidExpiryMM"));
				drpdwn.selectValueFromElement(payPgobj.getExpiryDate_yy(),
						prop.getProperty("InvalidExpiryYY"));
				payPgobj.getCVV()
						.sendKeys(prop.getProperty("InvalidSecurityCode"));
			} else {
				if (selected_CardType.contentEquals("AMERICAN EXPRESS")) {
					System.out.println("Entering american express details...");
					payPgobj.getCardNum()
							.sendKeys(prop.getProperty("wrongAmericanCard"));
					drpdwn.selectValueFromElement(payPgobj.getExpiryDate_mm(),
							prop.getProperty("InvalidExpiryMM"));
					drpdwn.selectValueFromElement(payPgobj.getExpiryDate_yy(),
							prop.getProperty("InvalidExpiryYY"));
					payPgobj.getCVV()
							.sendKeys(prop.getProperty("InvalidSecurityCode"));
				} else {
					if (selected_CardType.contentEquals("VISA")) {
						System.out.println("Entering visa details...");
						payPgobj.getCardNum()
								.sendKeys(prop.getProperty("wrongVisaCard"));
						drpdwn.selectValueFromElement(
								payPgobj.getExpiryDate_mm(),
								prop.getProperty("InvalidExpiryMM"));
						drpdwn.selectValueFromElement(
								payPgobj.getExpiryDate_yy(),
								prop.getProperty("InvalidExpiryYY"));
						payPgobj.getCVV().sendKeys(
								prop.getProperty("InvalidSecurityCode"));
					} else {
						if (selected_CardType.contentEquals("DINNERS")) {
							System.out.println("Entering Dinners details...");
							payPgobj.getCardNum()
									.sendKeys(prop.getProperty("Diners"));
							drpdwn.selectValueFromElement(
									payPgobj.getExpiryDate_mm(),
									prop.getProperty("InvalidExpiryMM"));
							drpdwn.selectValueFromElement(
									payPgobj.getExpiryDate_yy(),
									prop.getProperty("InvalidExpiryMM"));
						} else {
							System.out
									.println("enterd card type not matched..");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			AssertJUnit.fail(e.getMessage());
		}
		return CardType;
	}
}
