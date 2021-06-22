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
import sunLoverModule.SLConsPropertySelectionFactory;
import utilities.ScreenShotOnFailure;

@Listeners(ScreenShotOnFailure.class)
public class SLConSGBookingTC {
	// TestObjective:- to check Single Room booking for single person
	// Expected result:- User should able to book any accommodation in single
	// booking
	// Positive TestCase

	static String URL = LaunchSunloverCons.URL;
	WebDriverWait wait = new WebDriverWait(Driver.driver, 3000);
	SLConHomePageAction homePgFact = PageFactory.initElements(Driver.driver, SLConHomePageAction.class);
	SLConsBookingFactory bookingFact = PageFactory.initElements(Driver.driver, SLConsBookingFactory.class);
	SLConsPropertySelectionFactory selectionFactory = PageFactory.initElements(Driver.driver, SLConsPropertySelectionFactory.class);
	SLVCHomePgObj homePgObj = PageFactory.initElements(Driver.driver, SLVCHomePgObj.class);
	SLVCBroucherPgObj BrouchPgobj = PageFactory.initElements(Driver.driver, SLVCBroucherPgObj.class);
	SLCVPaymentPgObj payPgobj = PageFactory.initElements(Driver.driver, SLCVPaymentPgObj.class);
	SLVCBookingSummaryPgobj summaryPgobj = PageFactory.initElements(Driver.driver, SLVCBookingSummaryPgobj.class);
	XLReader excel = new XLReader();
	XLWriter writer = PageFactory.initElements(Driver.driver, XLWriter.class);

	public SLConSGBookingTC() {
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
	}

	@Test
	public void TC_RG_SLC_Book_SingleRoom_Property() throws Exception {

		try {
			Thread.sleep(3000);
			System.out.println("Running a test to book an property for 3 night stay..");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("AccomSearchForm"))).isDisplayed();
			String hotel = excel.getCellData(6, 2);
			String nights = excel.getCellData(6, 7);
			System.out.println("Property selected from XL: " + hotel);
			homePgObj.getHotel_name().click();
			homePgObj.getHotel_name().clear();
			homePgObj.getHotel_name().sendKeys(hotel);
			selectionFactory.Choose_Hotel_Name(hotel);
			homePgFact.select_CheckInmonth(excel.getCellData(6, 3));
			homePgFact.select_CheckInDay(excel.getCellData(6, 4));
			homePgFact.enter_NightsToStay(nights);
			homePgFact.select_Adults(excel.getCellData(6, 8));
			homePgObj.getGo_button().click();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("searchFormWaiter_pleaseWait")));
			//This line is giving me an error whenever search by hotel name not gives us any result then broacher page is not loading.
			Assert.assertTrue(BrouchPgobj.getSupplier_header().isDisplayed(), "Broacher page not loaded for selected property");
			Thread.sleep(3000);
			String roomType = excel.getCellData(6, 11);
			selectionFactory.Select_Room_Available(roomType);
			wait.until(ExpectedConditions.textToBePresentInElement(payPgobj.getHeader(), "Payment"));
			String adult = excel.getCellData(6, 8);
			bookingFact.Enter_Adult_Detail(adult);
			// assert adult copied..
			bookingFact.Enter_Booking_Details();
			String CardType = "MASTERCARD";
			bookingFact.Enter_CC_Details(CardType);
			Thread.sleep(2000);
			payPgobj.getTerms_checkbox().click();
			Thread.sleep(2000);
			payPgobj.getCnsSubscription_checkbox().click();
			Thread.sleep(3000);
			payPgobj.getBookNPayNow_button().click();
			System.out.println("Booking is in progess.. Please Wait......");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("progress_bar")));
			String Booking_Ref = summaryPgobj.getBookingNo().getText();
			System.out.println("Booking Reference no. receive is: " + Booking_Ref);
			String Booking_Status = summaryPgobj.getBookingStatus().getAttribute("Strong");
			System.out.println("booking Summary Text: " + Booking_Status);
			
			// Code to write Test output in excel
			String methodName = excel.getCellData(6, 0);
			System.out.println("Test Case Executed: " + methodName);
			int row = 1;
			writer.write_Test_Result(row, methodName, Booking_Ref);
			Assert.assertTrue(Driver.driver.getCurrentUrl().contains(Booking_Ref), "Booking Failed please see an error on booking summary");
		} catch (Exception e) {
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
