package sunLoverTestCases;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.webdriven.commands.WaitForPageToLoad;

import automationFramework.Driver;
import automationFramework.XLReader;
import automationFramework.XLWriter;
import pageObjects.SLCVItineraryPgObj;
import pageObjects.SLCVPaymentPgObj;
import pageObjects.SLCVServiceDetailsPgObj;
import pageObjects.SLVCBookingSummaryPgobj;
import pageObjects.SLVCTransfersPgObj;
import pageObjects.SLVCTransfersSearchResultPgObj;
import sunLoverModule.SLConTransfersPageAction;
import sunLoverModule.SLConsBookingFactory;
import utilities.ScreenShotOnFailure;

@Listeners(ScreenShotOnFailure.class)
public class SLConTransfersBookingTC {

	/**
	 * Test Objective: Add Transfer booking
	 * Expected Result: User should successfully book any Transfer service
	 * positive test case
	 */
	
	WebDriverWait wait = new WebDriverWait(Driver.driver, 2000);
	static String URL = LaunchSunloverCons.URL;
	XLReader excel = new XLReader();
	SLConTransfersPageAction trsfsPgAct = PageFactory.initElements(Driver.driver, SLConTransfersPageAction.class);
	SLVCTransfersPgObj trsfsPgObj = PageFactory.initElements(Driver.driver, SLVCTransfersPgObj.class);
	SLVCTransfersSearchResultPgObj trsfsSearchResultPgObj = PageFactory.initElements(Driver.driver,
	SLVCTransfersSearchResultPgObj.class);
	SLCVServiceDetailsPgObj srvsDetailsPgObj = PageFactory.initElements(Driver.driver, SLCVServiceDetailsPgObj.class);
	SLCVItineraryPgObj itnyPgobj = PageFactory.initElements(Driver.driver, SLCVItineraryPgObj.class);
	SLConsBookingFactory bookingFact = PageFactory.initElements(Driver.driver, SLConsBookingFactory.class);
	SLCVPaymentPgObj payPgobj = PageFactory.initElements(Driver.driver, SLCVPaymentPgObj.class);
	SLVCBookingSummaryPgobj summaryPgObj = PageFactory.initElements(Driver.driver, SLVCBookingSummaryPgobj.class);
	XLWriter writer = PageFactory.initElements(Driver.driver, XLWriter.class);
	

	public SLConTransfersBookingTC() {
		// TODO Auto-generated constructor stub
		PageFactory.initElements(Driver.driver, this);
	}

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {

		Driver.driver.get(URL);
		Driver.driver.manage().window().maximize();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header"))).isDisplayed();
		Driver.driver.findElement(By.linkText("Transfers")).click();
		new WaitForPageToLoad();
		System.out.println("Transfers tab clicked.");
	}

	@Test(priority = 1)
	public void TC_Book_Transfers() throws Exception {
		try{
			Thread.sleep(3000);
			System.out.println("Running a test to book a Transfer...");
			String departs_From = excel.getCellData(31, 1);
			String transMonth = excel.getCellData(31, 3);
			String transDay = excel.getCellData(31, 4);
			String adult = excel.getCellData(31, 8);
			trsfsPgAct.selectLocation(departs_From);
			trsfsPgAct.select_TransMonth(transMonth);
			trsfsPgAct.select_TransDay(transDay);
			wait.until(ExpectedConditions.elementToBeClickable(trsfsPgObj.getGoBtn()));
			trsfsPgAct.click_Go();
			new WaitForPageToLoad();
			// Selecting first transfer service from result list
			List<WebElement> bookbtn = Driver.driver.findElements(By.xpath("//img[starts-with(@id, 'btn_')]"));
			int numOfResult = bookbtn.size();
			System.out.println("Result list of Transfers in selected criteria is: " + numOfResult);
			trsfsSearchResultPgObj.getBookBtn().click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("ires")));
			srvsDetailsPgObj.getPickupLocation_txtbox().sendKeys("Adelaide");
			srvsDetailsPgObj.getCheckbox().click();
			wait.until(ExpectedConditions.elementToBeClickable(srvsDetailsPgObj.getBook_button()));
			srvsDetailsPgObj.getBook_button().click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("totalprice")));
			itnyPgobj.getBookBtn().click();
			// wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ires")));
			wait.until(ExpectedConditions.textToBePresentInElement(payPgobj.getHeader(), "Payment"));
			bookingFact.Enter_Passenger_Detail(adult, "0", "0");
			bookingFact.Enter_Booking_Details();
			String CardType = "MASTERCARD";
			bookingFact.Enter_CC_Details(CardType);
			payPgobj.getTerms_checkbox().click();
			payPgobj.getCnsSubscription_checkbox().click();
			Thread.sleep(2000);
			payPgobj.getBookNPayNow_button().click();
			System.out.println("Booking is in progess.. Please Wait......");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("progress_bar")));
			String Booking_Ref = summaryPgObj.getBookingNo().getText();
			System.out.println("Booking Reference no. receive is: " + Booking_Ref);
			String Booking_Status = summaryPgObj.getBookingStatus().getText();
			System.out.println("booking Summary Text: " + Booking_Status);
			System.out.println("Test to verify Transfer booking completed successfully");
	
			// Code to write Test output in excel
			String methodName = excel.getCellData(31, 0);
			System.out.println("Test Case Executed: " + methodName);
			int row = 9;
			writer.write_Test_Result(row, methodName, Booking_Ref);
			AssertJUnit.assertTrue("Booking Failed please see an error on booking summary", Driver.driver.getCurrentUrl().contains(Booking_Ref));
		}
		 catch(Exception e)
		 {
			 e.getCause();
			 e.printStackTrace();
			 AssertJUnit.fail(e.getMessage());
		 }
	}
	
// This Test Case is to verify rates are updated with pax changes.
	@Test(priority=2)
	public void TC_RG_SLC_UpdateModify_Transfer_Details() throws Exception
	{
		try{
			Thread.sleep(3000);
			Driver.driver.findElement(By.linkText("Transfers")).click();
			System.out.println("Transfers tab clicked again.");
			new WaitForPageToLoad();
			System.out.println("Running a test case to validate Update rates for any transfer service");
			String departs_From = excel.getCellData(31, 1);
			String transMonth = excel.getCellData(31, 3);
			String transDay = excel.getCellData(31, 4);
			String adult = excel.getCellData(31, 8);
			trsfsPgAct.selectLocation(departs_From);
			trsfsPgAct.select_TransMonth(transMonth);
			trsfsPgAct.select_TransDay(transDay);
			wait.until(ExpectedConditions.elementToBeClickable(trsfsPgObj.getGoBtn()));
			trsfsPgAct.click_Go();
			new WaitForPageToLoad();
			// Selecting first transfer service from result list
			List<WebElement> bookbtn = Driver.driver.findElements(By.xpath("//img[starts-with(@id, 'btn_')]"));
			int numOfResult = bookbtn.size();
			System.out.println("Result list of Transfers in selected criteria is: " + numOfResult);
			trsfsSearchResultPgObj.getBookBtn().click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("ires")));
			String actPrice = trsfsPgObj.getPrice().getText();
			trsfsPgAct.update_pax_on_service_details(adult);
			String unexpPrice = trsfsPgObj.getPrice().getText();
			Assert.assertNotEquals("Price not updated for updating pax details...", unexpPrice, actPrice);
		}
		catch(Exception e)
		 {
			 e.getCause();
			 e.printStackTrace();
			 AssertJUnit.fail(e.getMessage());
		 }
		
	}

}
