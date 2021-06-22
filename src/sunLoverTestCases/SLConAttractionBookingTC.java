/**
 * 
 */
package sunLoverTestCases;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
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
public class SLConAttractionBookingTC {

	/**
	 * Test Objective: Add attraction booking
	 * Expected Result: User should successfully book any attraction
	 * positive test case
	 */
	
	
	WebDriverWait wait = new WebDriverWait(Driver.driver, 2000);
	static String URL = LaunchSunloverCons.URL;
	XLReader excel = new XLReader();
	SLConHomePageAction homePgAct = PageFactory.initElements(Driver.driver, SLConHomePageAction.class);
	SLConServiceDetailsAction pgFact = PageFactory.initElements(Driver.driver, SLConServiceDetailsAction.class);
	SLCVItineraryPgObj itnyPgobj = PageFactory.initElements(Driver.driver, SLCVItineraryPgObj.class);
	SLCVPaymentPgObj payPgobj = PageFactory.initElements(Driver.driver, SLCVPaymentPgObj.class);
	SLVCBookingSummaryPgobj summaryPgObj = PageFactory.initElements(Driver.driver, SLVCBookingSummaryPgobj.class);
	SLConsBookingFactory bookingFact = PageFactory.initElements(Driver.driver, SLConsBookingFactory.class);
	XLWriter writer = PageFactory.initElements(Driver.driver, XLWriter.class);
	
	public SLConAttractionBookingTC() {
		// TODO Auto-generated constructor stub
		PageFactory.initElements(Driver.driver, this);
	}
	
	@BeforeClass(alwaysRun = true)
	public void beforeClass() {

		Driver.driver.get(URL);
		Driver.driver.manage().window().maximize();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header"))).isDisplayed();
		Driver.driver.findElement(By.linkText("Attractions")).click();
		new WaitForPageToLoad();
		System.out.println("Attractions tab clicked.");
		
	}

	
	
	@Test
	public void TC_RG_SLC_Add_Attraction_Booking() throws Exception
	{
	try{
		Thread.sleep(3000);
		System.out.println("********Running a test case to book any attraction.**********");
		String location = excel.getCellData(28, 1);
		String start_mm = excel.getCellData(28, 3);
		String start_dd = excel.getCellData(28, 4);
		String adult = excel.getCellData(28,8);
		pgFact.selectLocation(location);
		homePgAct.select_CheckInmonth(start_mm);
		homePgAct.select_CheckInDay(start_dd);
		wait.until(ExpectedConditions.elementToBeClickable(pgFact.getGoBtn()));
		pgFact.click_Go();
		new WaitForPageToLoad();
		// Selecting first service from result list
		List<WebElement> bookbtn = Driver.driver.findElements(By.xpath("//img[starts-with(@id, 'btn_')]"));
		int numOfResult = bookbtn.size();
		System.out.println("Result list of Transfers in selected criteria is: " + numOfResult);
		pgFact.getBookBtn().click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("ires")));
		String price = Driver.driver.findElement(By.xpath(".//*[@id='serviceDetailsForm']//tr[4]/td[2]//span[1]")).getText();
		System.out.println("Price shown on service detail page is: "+price);
		pgFact.getInfoAgree_checkbox().click();
		pgFact.getBook_service_btn().click();
		new WaitForPageToLoad();
//		WebElement Itinerary_header = Driver.driver.findElement(By.xpath(".//*[@id='content']/div[2]/div[1]/h1"));
//		wait.until(ExpectedConditions.textToBePresentInElementValue(Itinerary_header, "Itinerary"));
		Assert.assertTrue(Driver.driver.getCurrentUrl().contains("serviceLineId")==true, "Itinerary page not loaded");
		String ItineryCost = Driver.driver.findElement(By.cssSelector(".tabledata.maintintseven>p>strong>span")).getText();
		System.out.println("Price shown on Itinerary page is: "+ItineryCost);
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
		Assert.assertEquals(price, ItineryCost);
		Assert.assertTrue(Driver.driver.getCurrentUrl().contains(Booking_Ref),"Booking Failed please see an error on booking summary");
		// Code to write Test output in excel
		String methodName = excel.getCellData(28,0);
		System.out.println("Test Case Executed: " + methodName);
		int row = 10;
		writer.write_Test_Result(row, methodName, Booking_Ref);
		}
		catch(Exception e)
		 {
			 e.getCause();
			 e.printStackTrace();
			 AssertJUnit.fail(e.getMessage());
		 }
	}

}
