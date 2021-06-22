/**
 * 
 */
package sunLoverTestCases;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.webdriven.commands.WaitForPageToLoad;

import automationFramework.Driver;
import automationFramework.XLReader;
import automationFramework.XLWriter;
import pageObjects.SLCVItineraryPgObj;
import pageObjects.SLCVPaymentPgObj;
import pageObjects.SLVCBookingSummaryPgobj;
import sunLoverModule.SLConHomePageAction;
import sunLoverModule.SLConServiceDetailsAction;
import sunLoverModule.SLConsBookingFactory;
import utilities.ScreenShotOnFailure;

/**
 * @author tetambes
 *
 */

@Listeners(ScreenShotOnFailure.class)
public class SLConsTourBookingTC {

	/**
	 * Test Objective: Add Day/Night Tour booking
	 * Expected Result: User should successfully book any Tour
	 * positive test case
	 */
	
	WebDriverWait wait = new WebDriverWait(Driver.driver, 2000);
	static String URL = LaunchSunloverCons.URL;
	XLReader excel = new XLReader();
	SLConHomePageAction homePgAct = PageFactory.initElements(Driver.driver, SLConHomePageAction.class);
	SLConServiceDetailsAction servFact = PageFactory.initElements(Driver.driver, SLConServiceDetailsAction.class);
	SLCVItineraryPgObj itnyPgobj = PageFactory.initElements(Driver.driver, SLCVItineraryPgObj.class);
	SLCVPaymentPgObj payPgobj = PageFactory.initElements(Driver.driver, SLCVPaymentPgObj.class);
	SLVCBookingSummaryPgobj summaryPgObj = PageFactory.initElements(Driver.driver, SLVCBookingSummaryPgobj.class);
	SLConsBookingFactory bookingFact = PageFactory.initElements(Driver.driver, SLConsBookingFactory.class);
	XLWriter writer = PageFactory.initElements(Driver.driver, XLWriter.class);
	
	
	public SLConsTourBookingTC() {
		// TODO Auto-generated constructor stub
		PageFactory.initElements(Driver.driver, this);
	}
	
	@BeforeClass(alwaysRun = true)
	public void beforeClass() {

		Driver.driver.get(URL);
		Driver.driver.manage().window().maximize();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header"))).isDisplayed();
		Driver.driver.findElement(By.linkText("Tours & Cruises")).click();
		new WaitForPageToLoad();
		System.out.println("Tours & Cruises tab clicked.");
	}
	
	@BeforeMethod(alwaysRun= true)
	public void clearCookiesBefore()
	{
		Driver.driver.manage().deleteAllCookies();
	}
	
	
	@Test(priority=1)
	public void TC_RG_SLC_Add_Day_Tour_Booking() throws Exception
	{
		try{
			Thread.sleep(3000);
			System.out.println("********Running a test case to book any Day Tour...**********");
			String location = excel.getCellData(29, 1);
			String start_mm = excel.getCellData(29, 3);
			String start_dd = excel.getCellData(29, 4);
			String adult = excel.getCellData(29,8);
			servFact.selectLocation(location);
			homePgAct.select_CheckInmonth(start_mm);
			homePgAct.select_CheckInDay(start_dd);
			wait.until(ExpectedConditions.elementToBeClickable(servFact.getGoBtn()));
			servFact.click_Go();
			new WaitForPageToLoad();
			// Selecting first service from result list
			servFact.click_book_btn();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("ires")));
			//update adult details..
			servFact.selectAdults(adult);
			// clicking on update rate button
			servFact.getUpdateRates_btn().click();
			Thread.sleep(2000);
			String price = Driver.driver.findElement(By.xpath(".//*[@id='serviceDetailsForm']//tr[5]/td[2]//span[1]")).getText();
			System.out.println("Price shown on service detail page is: "+price);
			servFact.getPickUp_request().sendKeys("test");
			servFact.getInfoAgree_checkbox().click();
			servFact.getBook_service_btn().click();
			new WaitForPageToLoad();
			Assert.assertTrue(Driver.driver.getCurrentUrl().contains("serviceLineId")==true, "Itinerary page not loaded");
			String ItineryCost = Driver.driver.findElement(By.cssSelector(".tabledata.maintintseven>p>strong>span")).getText();
			System.out.println("Price shown on Itinerary page is: "+ItineryCost);
			itnyPgobj.getBookBtn().click();
			//enter payment details
			wait.until(ExpectedConditions.textToBePresentInElement(payPgobj.getHeader(), "Payment"));
			bookingFact.Enter_Passenger_Detail(adult, "0", "0");
			bookingFact.Enter_Booking_Details();
			String CardType = "MASTERCARD";
			bookingFact.Enter_CC_Details(CardType);
			payPgobj.getTerms_checkbox().click();
			payPgobj.getCnsSubscription_checkbox().click();
			Thread.sleep(3000);
			payPgobj.getBookNPayNow_button().click();
			System.out.println("Booking is in progess.. Please Wait......");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("progress_bar")));
			String Booking_Ref = summaryPgObj.getBookingNo().getText();
			System.out.println("Booking Reference no. receive is: " + Booking_Ref);
			String Booking_Status = summaryPgObj.getBookingStatus().getText();
			System.out.println("booking Summary Text: " + Booking_Status);
			Assert.assertEquals(price, ItineryCost);
			Assert.assertTrue(Driver.driver.getCurrentUrl().contains(Booking_Ref),"Booking Failed please see an error on booking summary");
			// Code to write Test output in excel
			writer.write_Test_Result(11, "TC_RG_SLC_Add_Day_Tour_Booking", Booking_Ref);
		}
		catch(Exception e)
		 {
			 e.getCause();
			 e.printStackTrace();
			 AssertJUnit.fail(e.getMessage());
		 }
	}
	
	@AfterMethod(alwaysRun= true)
	public void clearCookiesAfter()
	{
		Driver.driver.manage().deleteAllCookies();
	}
	
	
	@Test(priority=2)
	public void TC_RG_SLC_Add_Extended_Overnight_Cruise_Booking() throws Exception
	{
		try{
			Thread.sleep(3000);
			System.out.println("********Running a test case to book any Extended overnight Cruise booking...**********");
			Driver.driver.findElement(By.linkText("Tours & Cruises")).click();
			new WaitForPageToLoad();
			String location = excel.getCellData(29, 1);
			String start_mm = excel.getCellData(29, 3);
			String start_dd = excel.getCellData(29, 4);
			String adult = excel.getCellData(29,8);
			servFact.selectTourType();
			servFact.selectLocation(location);
			homePgAct.select_CheckInmonth(start_mm);
			homePgAct.select_CheckInDay(start_dd);
			wait.until(ExpectedConditions.elementToBeClickable(servFact.getGoBtn()));
			servFact.click_Go();
			new WaitForPageToLoad();
			// Selecting first service from result list
			servFact.click_book_btn();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("ires")));
			//update adult details..
			servFact.selectAdults(adult);
			// clicking on update rate button
			servFact.getUpdateRates_btn().click();
			Thread.sleep(2000);
			String price = Driver.driver.findElement(By.xpath(".//*[@id='serviceDetailsForm']//tr[5]/td[2]//span[1]")).getText();
			System.out.println("Price shown on service detail page is: "+price);
			servFact.getPickUp_request().sendKeys("test");
			servFact.getInfoAgree_checkbox().click();
			servFact.getBook_service_btn().click();
			new WaitForPageToLoad();
			Assert.assertTrue(Driver.driver.getCurrentUrl().contains("serviceLineId")==true, "Itinerary page not loaded");
			String ItineryCost = Driver.driver.findElement(By.cssSelector(".tabledata.maintintseven>p>strong>span")).getText();
			System.out.println("Price shown on Itinerary page is: "+ItineryCost);
			itnyPgobj.getBookBtn().click();
			//enter payment details
			wait.until(ExpectedConditions.textToBePresentInElement(payPgobj.getHeader(), "Payment"));
			bookingFact.Enter_Passenger_Detail(adult, "0", "0");
			bookingFact.Enter_Booking_Details();
			String CardType = "MASTERCARD";
			bookingFact.Enter_CC_Details(CardType);
			payPgobj.getTerms_checkbox().click();
			payPgobj.getCnsSubscription_checkbox().click();
			Thread.sleep(3000);
			payPgobj.getBookNPayNow_button().click();
			System.out.println("Booking is in progess.. Please Wait......");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("progress_bar")));
			String Booking_Ref = summaryPgObj.getBookingNo().getText();
			System.out.println("Booking Reference no. receive is: " + Booking_Ref);
			String Booking_Status = summaryPgObj.getBookingStatus().getText();
			System.out.println("booking Summary Text: " + Booking_Status);
			Assert.assertEquals(price, ItineryCost);
			Assert.assertTrue(Driver.driver.getCurrentUrl().contains(Booking_Ref),"Booking Failed please see an error on booking summary");
			// Code to write Test output in excel
			writer.write_Test_Result(12, "TC_RG_SLC_Add_Extended_Overnight_Cruise_Booking", Booking_Ref);
		}
		catch(Exception e)
		 {
			 e.getCause();
			 e.printStackTrace();
			 AssertJUnit.fail(e.getMessage());
		 }
	}
}
