/**
 * 
 */
package sunLoverTestCases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import automationFramework.Driver;
import automationFramework.XLReader;
import automationFramework.XLWriter;
import pageObjects.SLCVPaymentPgObj;
import pageObjects.SLVCBookingSummaryPgobj;
import pageObjects.SLVCBroucherPgObj;
import pageObjects.SLVCHomePgObj;
import sunLoverModule.SLConHomePageAction;
import sunLoverModule.SLConsBookingFactory;
import sunLoverModule.SLConsBookingPayViaCCAction;
import sunLoverModule.SLConsPropertySelectionFactory;
import utilities.ScreenShotOnFailure;
import utilities.SeleniumWaitClass;

/**
 * @author tetambes
 *
 */

@Listeners(ScreenShotOnFailure.class)
public class SLConsValidatePayViaCc_TC {

	/**
	 * Test Objective : To validate Booking for Invalid CC details
	 * Expected Result: Booking should not happen for Invalid Card details inc card no, expire date, CVV no.
	 * 
	 */
	static String URL = LaunchSunloverCons.URL;
	WebDriverWait wait = new WebDriverWait(Driver.driver, 3000);
	SeleniumWaitClass waitClassObj = new SeleniumWaitClass();
	SLConHomePageAction homePgAct = PageFactory.initElements(Driver.driver, SLConHomePageAction.class);
	SLConsBookingFactory bookingFactory = PageFactory.initElements(Driver.driver, SLConsBookingFactory.class);
	SLConsPropertySelectionFactory selectionFactory = PageFactory.initElements(Driver.driver, SLConsPropertySelectionFactory.class);
	SLConsBookingPayViaCCAction CCAction = PageFactory.initElements(Driver.driver, SLConsBookingPayViaCCAction.class);
	SLVCHomePgObj homePgObj = PageFactory.initElements(Driver.driver, SLVCHomePgObj.class);
	SLVCBroucherPgObj BrouchPgobj = PageFactory.initElements(Driver.driver, SLVCBroucherPgObj.class);
	SLCVPaymentPgObj payPgobj = PageFactory.initElements(Driver.driver, SLCVPaymentPgObj.class);
	SLVCBookingSummaryPgobj summaryPgObj = PageFactory.initElements(Driver.driver, SLVCBookingSummaryPgobj.class);
	XLReader excelSheet = PageFactory.initElements(Driver.driver, XLReader.class);
	XLWriter writer = PageFactory.initElements(Driver.driver, XLWriter.class);
	
	public SLConsValidatePayViaCc_TC() {
		// TODO Auto-generated constructor stub
		PageFactory.initElements(Driver.driver, this);
	}

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		Driver.driver.get(URL);
		Driver.driver.manage().window().maximize();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header"))).isDisplayed();
		Driver.driver.findElement(By.linkText("Accommodation")).click();
		waitClassObj.pageLoadTime();
		System.out.println("Accomodation tab clicked.");
		Driver.driver.manage().deleteAllCookies();
	}
	
	@Test(priority=1)
	public void TC_RG_SLC__ValidateBooking_Pay_Via_MasterCard() {
		
		try {
			Thread.sleep(3000);
			System.out.println("************************Running a test case to validate Booking pay via CC- MasterCard************************");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header"))).isDisplayed();
			Driver.driver.findElement(By.linkText("Accommodation")).click();
			waitClassObj.waitforPageToLoad(); 
			System.out.println("Accomodation tab clicked.");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("AccomSearchForm"))).isDisplayed();
			String location = excelSheet.getCellData(10, 1);
			System.out.println("Location read from xl: " + location);
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("locationId")));
			homePgAct.selectLocation(location);
			waitClassObj.pageLoadTime();
			homePgAct.select_CheckInmonth(excelSheet.getCellData(10, 3));
			homePgAct.select_CheckInDay(excelSheet.getCellData(10, 4));
			String nights = (String)excelSheet.getCellData(10, 7);
			homePgAct.enter_NightsToStay(nights);
			String adults = (String)excelSheet.getCellData(10, 8);
			String child = (String)excelSheet.getCellData(10, 9);
			String child_age = (String)excelSheet.getCellData(10, 10);
			homePgAct.select_Adults(adults);
			homePgAct.select_Child_specify_age(child, child_age);
			homePgObj.getGo_button().click();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("searchFormWaiter_pleaseWait")));
			selectionFactory.Click_SelectRQ_button();
			waitClassObj.waitforPageToLoad(); 
			// Verify data on broacher page
			String costOnBroucherScrn = BrouchPgobj.getTotal_Cost().getText();
			String[] CostOnBrch = costOnBroucherScrn.split("\\R+");
			System.out.println("Cost on Broucher page: "+CostOnBrch[0]);
			String Title = Driver.driver.getTitle();
			System.out.println("Supplier selected: "+Title);
			// Verify the title of Supplier name selected..
			Assert.assertEquals(Driver.driver.getTitle(), Title);
			//Clicking Book button from Broacher page...
			selectionFactory.Click_Book_Now_Button();
			wait.until(ExpectedConditions.textToBePresentInElement(payPgobj.getHeader(), "Payment"));
			// Handling adult count from 2DB/2TW string..
			if(adults.contentEquals("2 (TW)") || adults.contentEquals("2 (DB)"))
			{
				String adultNum = adults.substring(0,1);
				adults = adultNum;
				System.out.println("Adults no: "+adults);
			}
			bookingFactory.Enter_Passenger_Detail(adults, child, child_age);
			bookingFactory.Enter_Booking_Details();
			String CardType = "MASTERCARD";
			CCAction.Enter_InValid_CC_Details(CardType);
			payPgobj.getTerms_checkbox().click();
			payPgobj.getCnsSubscription_checkbox().click();
			Thread.sleep(2000);
			payPgobj.getBookNPayNow_button().click();
			waitClassObj.pageLoadTime();
			System.out.println("Pay Now button clicked... Please Wait......");
			String expErr = "There was an error processing your booking. See below for details.";
			WebElement errormsg = Driver.driver.findElement(By.cssSelector(".error"));
			Assert.assertTrue(errormsg.isDisplayed(), "Validation Error not displayed for InValid card detail..");
			String actErr = errormsg.getText();
			System.out.println(actErr);
			Assert.assertEquals(actErr, expErr, "Validation for CC detailed failed...");
		
		}
		catch (Exception e) {
			e.getCause();
			e.printStackTrace();
			AssertJUnit.fail(e.getMessage());
		}
	}
		
	
	@Test(priority=2)
	public void TC_RG_SLC_ValidateBooking_Pay_Via_VisaCard() {
		try {
			Thread.sleep(3000);
			System.out.println("************************Running a test case to validate Booking pay via CC- VisaCard************************");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header"))).isDisplayed();
			Driver.driver.findElement(By.linkText("Accommodation")).click();
			waitClassObj.waitforPageToLoad(); 
			System.out.println("Accomodation tab clicked.");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("AccomSearchForm"))).isDisplayed();
			String location = excelSheet.getCellData(10, 1);
			System.out.println("Location read from xl: " + location);
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("locationId")));
			homePgAct.selectLocation(location);
			waitClassObj.pageLoadTime();
			homePgAct.select_CheckInmonth(excelSheet.getCellData(10, 3));
			homePgAct.select_CheckInDay(excelSheet.getCellData(10, 4));
			String nights = (String)excelSheet.getCellData(10, 7);
			homePgAct.enter_NightsToStay(nights);
			String adults = (String)excelSheet.getCellData(10, 8);
			String child = (String)excelSheet.getCellData(10, 9);
			String child_age = (String)excelSheet.getCellData(10, 10);
			homePgAct.select_Adults(adults);
			homePgAct.select_Child_specify_age(child, child_age);
			homePgObj.getGo_button().click();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("searchFormWaiter_pleaseWait")));
			selectionFactory.Click_SelectRQ_button();
			waitClassObj.waitforPageToLoad(); 
			// Verify data on broacher page
			String costOnBroucherScrn = BrouchPgobj.getTotal_Cost().getText();
			String[] CostOnBrch = costOnBroucherScrn.split("\\R+");
			System.out.println("Cost on Broucher page: "+CostOnBrch[0]);
			String Title = Driver.driver.getTitle();
			System.out.println("Supplier selected: "+Title);
			// Verify the title of Supplier name selected..
			Assert.assertEquals(Driver.driver.getTitle(), Title);
			//Clicking Book button from Broacher page...
			selectionFactory.Click_Book_Now_Button();
			wait.until(ExpectedConditions.textToBePresentInElement(payPgobj.getHeader(), "Payment"));
			// Handling adult count from 2DB/2TW string..
			if(adults.contentEquals("2 (TW)") || adults.contentEquals("2 (DB)"))
			{
				String adultNum = adults.substring(0,1);
				adults = adultNum;
				System.out.println("Adults no: "+adults);
			}
			bookingFactory.Enter_Passenger_Detail(adults, child, child_age);
			bookingFactory.Enter_Booking_Details();
			String CardType = "VISA";
			CCAction.Enter_InValid_CC_Details(CardType);
			payPgobj.getTerms_checkbox().click();
			payPgobj.getCnsSubscription_checkbox().click();
			Thread.sleep(2000);
			payPgobj.getBookNPayNow_button().click();
			waitClassObj.pageLoadTime();
			System.out.println("Pay Now button clicked... Please Wait......");
			String expErr = "There was an error processing your booking. See below for details.";
			WebElement errormsg = Driver.driver.findElement(By.cssSelector(".error"));
			Assert.assertTrue(errormsg.isDisplayed(), "Validation Error not displayed for InValid card detail..");
			String actErr = errormsg.getText();
			System.out.println(actErr);
			Assert.assertEquals(actErr, expErr, "Validation for CC detailed failed...");
		
		}
		catch (Exception e) {
			e.getCause();
			e.printStackTrace();
			AssertJUnit.fail(e.getMessage());
		}
	}
	
	@Test(priority=3)
	public void TC_RG_SLC_ValidateBooking_Pay_Via_AmericanExpress() {
		try {
			Thread.sleep(3000);
			System.out.println("************************Running a test case to validate Booking pay via CC- American Express Card************************");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header"))).isDisplayed();
			Driver.driver.findElement(By.linkText("Accommodation")).click();
			waitClassObj.waitforPageToLoad(); 
			System.out.println("Accomodation tab clicked.");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("AccomSearchForm"))).isDisplayed();
			String location = excelSheet.getCellData(10, 1);
			System.out.println("Location read from xl: " + location);
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("locationId")));
			homePgAct.selectLocation(location);
			waitClassObj.pageLoadTime();
			homePgAct.select_CheckInmonth(excelSheet.getCellData(10, 3));
			homePgAct.select_CheckInDay(excelSheet.getCellData(10, 4));
			String nights = (String)excelSheet.getCellData(10, 7);
			homePgAct.enter_NightsToStay(nights);
			String adults = (String)excelSheet.getCellData(10, 8);
			String child = (String)excelSheet.getCellData(10, 9);
			String child_age = (String)excelSheet.getCellData(10, 10);
			homePgAct.select_Adults(adults);
			homePgAct.select_Child_specify_age(child, child_age);
			homePgObj.getGo_button().click();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("searchFormWaiter_pleaseWait")));
			selectionFactory.Click_SelectRQ_button();
			waitClassObj.waitforPageToLoad(); 
			// Verify data on broacher page
			String costOnBroucherScrn = BrouchPgobj.getTotal_Cost().getText();
			String[] CostOnBrch = costOnBroucherScrn.split("\\R+");
			System.out.println("Cost on Broucher page: "+CostOnBrch[0]);
			String Title = Driver.driver.getTitle();
			System.out.println("Supplier selected: "+Title);
			// Verify the title of Supplier name selected..
			Assert.assertEquals(Driver.driver.getTitle(), Title);
			//Clicking Book button from Broacher page...
			selectionFactory.Click_Book_Now_Button();
			wait.until(ExpectedConditions.textToBePresentInElement(payPgobj.getHeader(), "Payment"));
			// Handling adult count from 2DB/2TW string..
			if(adults.contentEquals("2 (TW)") || adults.contentEquals("2 (DB)"))
			{
				String adultNum = adults.substring(0,1);
				adults = adultNum;
				System.out.println("Adults no: "+adults);
			}
			bookingFactory.Enter_Passenger_Detail(adults, child, child_age);
			bookingFactory.Enter_Booking_Details();
			String CardType = "AMERICAN EXPRESS";
			CCAction.Enter_InValid_CC_Details(CardType);
			payPgobj.getTerms_checkbox().click();
			payPgobj.getCnsSubscription_checkbox().click();
			Thread.sleep(2000);
			payPgobj.getBookNPayNow_button().click();
			System.out.println("Pay Now button clicked... Please Wait......");
			waitClassObj.pageLoadTime();
			String expErr = "There was an error processing your booking. See below for details.";
			WebElement errormsg = Driver.driver.findElement(By.cssSelector(".error"));
			Assert.assertTrue(errormsg.isDisplayed(), "Validation Error not displayed for InValid card detail..");
			String actErr = errormsg.getText();
			System.out.println(actErr);
			Assert.assertEquals(actErr, expErr, "Validation for CC detailed failed...");
		
		}
		catch (Exception e) {
			e.getCause();
			e.printStackTrace();
			AssertJUnit.fail(e.getMessage());
		}
	}
	
	
	@AfterMethod(alwaysRun=true)
	public void clear_cookies(){
		Driver.driver.manage().deleteAllCookies();
	}

}
