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
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.webdriven.commands.WaitForPageToLoad;

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
import sunLoverModule.SLConsSearchResultPageAction;
import utilities.ScreenShotOnFailure;

/**
 * @author tetambes
 *
 */
@Listeners(ScreenShotOnFailure.class)
public class SLConsPegaseusBookingTC {
	
	/**
	 * Test Objective: Add Pegaseus booking
	 * Expected Result: User should successfully book any pegaseus service
	 * positive test case
	 */
	
	static String URL = LaunchSunloverCons.URL;
	WebDriverWait wait = new WebDriverWait(Driver.driver, 3000);
	SLConHomePageAction homePgFact = PageFactory.initElements(Driver.driver, SLConHomePageAction.class);
	SLConsSearchResultPageAction pgAct = PageFactory.initElements(Driver.driver, SLConsSearchResultPageAction.class);
	XLReader excel = new XLReader();
	SLConsPropertySelectionFactory selectionFactory = PageFactory.initElements(Driver.driver, SLConsPropertySelectionFactory.class);
	SLConsBookingPayViaCCAction CCAction = PageFactory.initElements(Driver.driver, SLConsBookingPayViaCCAction.class);
	SLVCHomePgObj homePgObj = PageFactory.initElements(Driver.driver, SLVCHomePgObj.class);
	SLVCBroucherPgObj BrouchPgobj = PageFactory.initElements(Driver.driver, SLVCBroucherPgObj.class);
	SLCVPaymentPgObj payPgobj = PageFactory.initElements(Driver.driver, SLCVPaymentPgObj.class);
	SLVCBookingSummaryPgobj summaryPgObj = PageFactory.initElements(Driver.driver, SLVCBookingSummaryPgobj.class);
	SLConsBookingFactory bookingFactory = PageFactory.initElements(Driver.driver, SLConsBookingFactory.class);
	XLWriter writer = PageFactory.initElements(Driver.driver, XLWriter.class);
	
	
	
	public SLConsPegaseusBookingTC() {
		// TODO Auto-generated constructor stub
		PageFactory.initElements(Driver.driver, this);
	}

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		Driver.driver.get(URL);
		Driver.driver.manage().window().maximize();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header"))).isDisplayed();
		Driver.driver.findElement(By.linkText("Accommodation")).click();
		new WaitForPageToLoad();
		System.out.println("Accomodation tab clicked.");
		Driver.driver.manage().deleteAllCookies();
		Driver.driver.navigate().refresh();
	}
	
	
	/**
	 * Test Objective: To do Pegaseus Booking
	 * Expected: User should able to do Booking for any Pegaseus product.
	 * Positive Test Case
	 * @throws Exception
	 */
	@Test()
	public void TC_RG_SLC_Add_Pegaseus_Booking() throws AssertionError
	{
		try{
			Thread.sleep(3000);
			System.out.println("************************Running a test to book an Pegaseus property ...************************");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("AccomSearchForm"))).isDisplayed();
			String location = excel.getCellData(13, 1);
			String nights = excel.getCellData(13, 7);
			homePgFact.selectLocation(location);
			new WaitForPageToLoad();
//			homePgFact.select_date_from_Next_Month_of_SysDate();									// function to select any date from next month of system date
			homePgFact.select_CheckInmonth(excel.getCellData(13, 3));
			homePgFact.select_CheckInDay(excel.getCellData(13, 4));
			homePgFact.enter_NightsToStay(nights);
			String adults = (String)excel.getCellData(13, 8);
			String child = (String)excel.getCellData(13, 9);
			String child_age = (String)excel.getCellData(13, 10);
			homePgFact.select_Adults(adults);
			homePgFact.select_Child_specify_age(child, child_age);
			homePgObj.getGo_button().click();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("searchFormWaiter_pleaseWait")));
			pgAct.select_Pegaseus_Property();
			new WaitForPageToLoad();
			String Title = Driver.driver.getTitle();
			System.out.println("Supplier selected: "+Title);
			// Verify the title of Supplier name selected..
			Assert.assertEquals(Driver.driver.getTitle(), Title);
			//Clicking Book button from Broacher page...
			selectionFactory.Click_Book_Now_Button();
			wait.until(ExpectedConditions.textToBePresentInElement(payPgobj.getHeader(), "Payment"));
			// Handling adult count from 2DB/ 2TW string..
			if(adults.contentEquals("2 (TW)") || adults.contentEquals("2 (DB)"))
			{
				String adultNum = adults.substring(0,1);
				adults = adultNum;
				System.out.println("Adults no: "+adults);
			}
			bookingFactory.Enter_Passenger_Detail(adults, child, child_age);
			bookingFactory.Enter_Booking_Details();
			String CardType = "MASTERCARD";
			bookingFactory.Enter_CC_Details(CardType);
			payPgobj.getTerms_checkbox().click();
			payPgobj.getCnsSubscription_checkbox().click();
			Thread.sleep(2000);
			payPgobj.getBookNPayNow_button().click();
			System.out.println("Pay Now button clicked... Please Wait......");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("progress_bar")));
//			Assert.assertTrue(Driver.driver.getCurrentUrl().contains(Title), "Booking Summary Page not loaded..");
			if(!Driver.driver.getCurrentUrl().contains("failedBooking")){
				String Booking_Ref = summaryPgObj.getBookingNo().getText();
				System.out.println("Booking Reference no. receive is: " + Booking_Ref);
				String Booking_Status = summaryPgObj.getBookingStatus().getText();
				System.out.println("booking Summary Text: "+Booking_Status);
				// write o/p in XL file
				String methodName = excel.getCellData(13,0);
				System.out.println("Test Case Executed: " + methodName);
				writer.write_Test_Result(6, methodName, Booking_Ref);
				Assert.assertTrue(Driver.driver.getCurrentUrl().contains(Booking_Ref), "Booking Failed please see an error on booking summary");
			}
			else{
				Assert.assertFalse(Driver.driver.getCurrentUrl().contains("failedBooking="), "Booking Failed..");
				Assert.assertEquals(Driver.driver.findElement(By.xpath("//h3[@class='bookingFailedMsg']")).getText(), "There has been a problem with your booking. Please contact reservations.");
			}
			
		}
		catch(Exception e){
			e.getMessage();
			e.printStackTrace();
			AssertJUnit.fail(e.getMessage());
		}
	}
	
	@AfterMethod(alwaysRun=true)
	public void clear_cookies(){
		Driver.driver.manage().deleteAllCookies();
	}

}
