package sunLoverTestCases;

import java.util.Iterator;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.webdriven.commands.WaitForPageToLoad;

import automationFramework.Driver;
import automationFramework.EnvConfiguration;
import automationFramework.XLReader;
import pageObjects.SLCVPaymentPgObj;
import pageObjects.SLVCBookingSummaryPgobj;
import pageObjects.SLVCCarHirePgObj;
import sunLoverModule.SLConCarHirePageAction;
import sunLoverModule.SLConHomePageAction;
import sunLoverModule.SLConsBookingFactory;

public class SLConCarHireBookingTC {

	WebDriverWait wait = new WebDriverWait(Driver.driver, 2000);
	SLConHomePageAction pgAct = PageFactory.initElements(Driver.driver, SLConHomePageAction.class);
	SLVCCarHirePgObj pgObj = PageFactory.initElements(Driver.driver, SLVCCarHirePgObj.class);
	static String URL = EnvConfiguration.TRN_URL;
	XLReader excel = new XLReader();
	SLConCarHirePageAction CarPgAct = PageFactory.initElements(Driver.driver, SLConCarHirePageAction.class);

	SLConsBookingFactory bookingFact = PageFactory.initElements(Driver.driver, SLConsBookingFactory.class);
	SLCVPaymentPgObj payPgobj = PageFactory.initElements(Driver.driver, SLCVPaymentPgObj.class);
	SLVCBookingSummaryPgobj summaryPgObj = PageFactory.initElements(Driver.driver, SLVCBookingSummaryPgobj.class);

	public SLConCarHireBookingTC() {
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

	}

	@Test(priority = 1)

	public void TC_Car_Hire_Booking() throws Exception {

		String location = excel.getCellData(34, 1);
		String ChkInMonth = excel.getCellData(34, 3);
		String ChkInday = excel.getCellData(34, 4);
		String ChkOutMonth = excel.getCellData(34, 5);
		String ChkOutday = excel.getCellData(34, 6);
		/*String pickUpTime = excel.getCellData(34, 14);
		// System.out.println(pickUpTime);
		String dropOffTime = excel.getCellData(34, 15);*/
		String adult = excel.getCellData(34, 3);
		// System.out.println(dropOffTime);
		CarPgAct.selectLocation(location);
		CarPgAct.select_CheckInMonth(ChkInMonth);
		CarPgAct.select_CheckInDay(ChkInday);

		CarPgAct.select_CheckOutMonth(ChkOutMonth);
		CarPgAct.select_CheckOutDay(ChkOutday);
		CarPgAct.Click_Go();
		new WaitForPageToLoad();
		

		// new WebDriverWait(Driver.driver,
		// 20).until((ExpectedConditions.presenceOfElementLocated(By.id("results"))));

		// wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("toolbar")));

		List<WebElement> bookbtn = Driver.driver.findElements(By.xpath("//input[starts-with(@id, 'btn']"));
		Iterator<WebElement> btnItr = bookbtn.iterator();
		while (btnItr.hasNext()) {

			System.out.println("Printing Bookbutton" + btnItr.next());

		}
		int bookbtnnumber = bookbtn.size();
		System.out.println("Number of Result on Search Page :" + bookbtnnumber);

		// Driver.driver.findElement(By.id("btn_bookbtnnumber")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("ires")));

		pgObj.getTerms_checkbox();

		pgObj.getnotes_checkbox().click();
		pgObj.get_bookbtn();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("totalprice")));

		pgObj.get_Itn_BookBtn().click();

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ires")));

		bookingFact.Enter_Adult_Detail(adult);
		bookingFact.Enter_Booking_Details();
		bookingFact.Enter_CC_Details("MasterCard");
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

	}

}
