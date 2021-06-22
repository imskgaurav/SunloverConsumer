package sunLoverTestCases;

import java.util.List;

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

import com.thoughtworks.selenium.webdriven.commands.WaitForPageToLoad;

import automationFramework.Driver;
import automationFramework.XLReader;
import automationFramework.XLWriter;
import pageObjects.SLCVPaymentPgObj;
import pageObjects.SLVCBookingSummaryPgobj;
import pageObjects.SLVCCarHirePgObj;
import sunLoverModule.SLConCarHirePageAction;
import sunLoverModule.SLConHomePageAction;
import sunLoverModule.SLConsBookingFactory;
import utilities.ScreenShotOnFailure;


/**
 * Test Objective: Add Car Hire booking
 * Expected Result: User should successfully book any Car Hire service
 * positive test case
 */
@Listeners(ScreenShotOnFailure.class)
public class SLConsCarHireBookingTC {

	WebDriverWait wait = new WebDriverWait(Driver.driver, 2000);
	SLConHomePageAction pgAct = PageFactory.initElements(Driver.driver, SLConHomePageAction.class);
	static String URL = LaunchSunloverCons.URL;
	XLReader excel = new XLReader();
	SLConCarHirePageAction CarPgAct = PageFactory.initElements(Driver.driver, SLConCarHirePageAction.class);
	SLVCCarHirePgObj pgObj = PageFactory.initElements(Driver.driver, SLVCCarHirePgObj.class);
	SLConsBookingFactory bookingFact = PageFactory.initElements(Driver.driver, SLConsBookingFactory.class);
	SLCVPaymentPgObj payPgobj = PageFactory.initElements(Driver.driver, SLCVPaymentPgObj.class);
	SLVCBookingSummaryPgobj summaryPgObj = PageFactory.initElements(Driver.driver, SLVCBookingSummaryPgobj.class);
	XLWriter writer = PageFactory.initElements(Driver.driver, XLWriter.class);
	
	public SLConsCarHireBookingTC() {
		// TODO Auto-generated constructor stub
		PageFactory.initElements(Driver.driver, this);

	}

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {

		Driver.driver.get(URL);
		Driver.driver.manage().window().maximize();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header"))).isDisplayed();
		Driver.driver.findElement(By.linkText("Car Hire")).click();
		new WaitForPageToLoad();
		System.out.println("Car Hire tab clicked.");
		Driver.driver.manage().deleteAllCookies();
	}

	/**
	 * Test Objective: To do Car Hire Booking
	 * Expected: User should able to do Booking for any Car Hire.
	 * Positive Test Case
	 * @throws Exception
	 */
	@Test()
	public void TC_Car_Hire_Booking() throws Exception 
	{
	 try{
		System.out.println("************************ Running a Test Case of Car Hire Booking************************");
		String location = excel.getCellData(33, 1);
		String ChkInMonth = excel.getCellData(33, 3);
		String ChkInday = excel.getCellData(33, 4);
		String ChkOutMonth = excel.getCellData(33, 5);
		String ChkOutday = excel.getCellData(33, 6);
		String adult = excel.getCellData(33, 8);
		// System.out.println(dropOffTime);
		Thread.sleep(3000);
		CarPgAct.selectLocation(location);
		CarPgAct.select_CheckInMonth(ChkInMonth);
		CarPgAct.select_CheckInDay(ChkInday);
		CarPgAct.select_CheckOutMonth(ChkOutMonth);
		CarPgAct.select_CheckOutDay(ChkOutday);
		wait.until(ExpectedConditions.elementToBeClickable(pgObj.get_GoButton()));
		CarPgAct.Click_Go();
		new WaitForPageToLoad();
		//Selecting first car service from result list
		List<WebElement> bookbtn = Driver.driver.findElements(By.xpath("//img[starts-with(@id, 'btn_')]"));					
		int numOfResult = bookbtn.size();
		System.out.println("Reult list of Car Hire in selected criteria is: "+numOfResult);
		WebElement Book_button = Driver.driver.findElement(By.xpath(".//*[@id='btn_43']"));
		Book_button.click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("ires")));
		pgObj.getTerms_checkbox().click();;
		pgObj.getnotes_checkbox().click();
		wait.until(ExpectedConditions.elementToBeClickable(pgObj.get_bookbtn()));
		pgObj.get_bookbtn().click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("totalprice")));
		pgObj.get_Itn_BookBtn().click();
//		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ires")));
		wait.until(ExpectedConditions.textToBePresentInElement(payPgobj.getHeader(), "Payment"));
		bookingFact.Enter_Adult_Detail(adult);
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
		
		// Code to write Test output in excel
		String methodName = excel.getCellData(33, 0);
		System.out.println("Test Case Executed: " + methodName);
		int row = 8;
		writer.write_Test_Result(row, methodName, Booking_Ref);
		Assert.assertTrue(Driver.driver.getCurrentUrl().contains(Booking_Ref), "Booking Failed please see an error on booking summary");
	 }
	 catch(Exception e)
	 {
		 e.getCause();
		 e.printStackTrace();
		 AssertJUnit.fail(e.getMessage());
	 }
	}
	
	
	@AfterMethod(alwaysRun=true)
	public void clear_cookies(){
		
		Driver.driver.manage().deleteAllCookies();
		Driver.driver.navigate().refresh();
	}

}
