package sunLoverTestCases;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
import pageObjects.SLCVPaymentPgObj;
import pageObjects.SLVCBookingSummaryPgobj;
import pageObjects.SLVCBroucherPgObj;
import pageObjects.SLVCHomePgObj;
import sunLoverModule.SLConHomePageAction;
import sunLoverModule.SLConsBookingFactory;
import sunLoverModule.SLConsPropertySelectionFactory;
import utilities.ScreenShotOnFailure;

@Listeners(ScreenShotOnFailure.class)
public class SLConsFreeNightBookingTC {

	static String URL = LaunchSunloverCons.URL;
	By Header = By.xpath("//*[@id='srch']/div/h1");
	SLConHomePageAction pgAct = PageFactory.initElements(Driver.driver, SLConHomePageAction.class);
	SLVCHomePgObj HomePgObj = PageFactory.initElements(Driver.driver, SLVCHomePgObj.class);
	SLVCBroucherPgObj BroPgObj = PageFactory.initElements(Driver.driver, SLVCBroucherPgObj.class);
	SLConsPropertySelectionFactory selectionFactory = PageFactory.initElements(Driver.driver,
			SLConsPropertySelectionFactory.class);
	WebDriverWait wait = new WebDriverWait(Driver.driver, 10);
	XLReader excel = new XLReader();
	JavascriptExecutor jse = (JavascriptExecutor) Driver.driver;
	SLCVPaymentPgObj payPgobj = PageFactory.initElements(Driver.driver, SLCVPaymentPgObj.class);
	SLConsBookingFactory bookingFact = PageFactory.initElements(Driver.driver, SLConsBookingFactory.class);
	SLVCBookingSummaryPgobj summaryPgObj = PageFactory.initElements(Driver.driver, SLVCBookingSummaryPgobj.class);
	XLWriter writer = PageFactory.initElements(Driver.driver, XLWriter.class);

	public SLConsFreeNightBookingTC() {

		// TODO Auto-generated constructor stub
		PageFactory.initElements(Driver.driver, this);
	}

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		Driver.driver.get(URL);
		Driver.driver.manage().window().maximize();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header"))).isDisplayed();
		Driver.driver.findElement(By.linkText("Home")).click();
		new WaitForPageToLoad();
		Driver.driver.manage().deleteAllCookies();
	}

	/**
	 * Test Objective: To do Free Night Booking Expected: User should able to do
	 * Booking for any Free night accommodation. Positive Test Case
	 * 
	 * @throws Exception
	 */
	@Test()
	public void TC_RG_SLC_Verify_Free_Nights_Booking() throws InterruptedException {
		try {
			new WebDriverWait(Driver.driver, 20).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(Header));
			System.out.println("Finding Accomodation SearchBy Hotel name......");
			String Header_Text = Driver.driver.findElement(By.xpath("//*[@id='srch']/div/h1")).getText();
			System.out.println("Header Text is:" + Header_Text);
			String hotel_name = excel.getCellData(21, 2);
			System.out.println("Property selected from XL: " + hotel_name);
			HomePgObj.getHotel_name().click();
			HomePgObj.getHotel_name().clear();
			HomePgObj.getHotel_name().sendKeys(hotel_name);
			selectionFactory.Choose_Hotel_Name(hotel_name);
			String checkInMonth = excel.getCellData(21, 3);
			System.out.println(checkInMonth);
			String checkInDay = excel.getCellData(21, 4);
			String checkOutMonth = excel.getCellData(21, 5);
			String checkOutDay = excel.getCellData(21, 6);
			pgAct.select_CheckInmonth(checkInMonth);
			pgAct.select_CheckInDay(checkInDay);
			Thread.sleep(2000);
			pgAct.select_CheckOutmonth(checkOutMonth);
			pgAct.select_CheckOutDay(checkOutDay);
			String adult = excel.getCellData(21, 8);
			String roomType = excel.getCellData(21, 11);
			Thread.sleep(2000);

			if (HomePgObj.getSearch_button().isEnabled()) {
				System.out.println("Search Button is appeared");
				// HomePgObj.getSearch_button().click();
				jse.executeScript("arguments[0].click();", HomePgObj.getSearch_button());
				new WebDriverWait(Driver.driver, 20)
						.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector(".bookbutton"))));
			}
			selectionFactory.Select_Room_Available(roomType);
			System.out.println(" Now clicking on Book Button");
			new WebDriverWait(Driver.driver, 20)
					.until((ExpectedConditions.presenceOfElementLocated(By.className("ires"))));
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
			String Booking_Status = summaryPgObj.getBookingStatus().getAttribute("Strong");
			System.out.println("booking Summary Text: " + Booking_Status);

			// Code to write Test output in excel
			String methodName = excel.getCellData(21, 0);
			System.out.println("Test Case Executed: " + methodName);
			int row = 7;
			writer.write_Test_Result(row, methodName, Booking_Ref);
			Assert.assertTrue(Driver.driver.getCurrentUrl().contains(Booking_Ref),
					"Booking Failed please see an error on booking summary");
		} catch (Exception e) {
			e.printStackTrace();
			AssertJUnit.fail(e.getMessage());
		}

	}
}
