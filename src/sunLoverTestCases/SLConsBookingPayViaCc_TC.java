/**
 * 
 */
package sunLoverTestCases;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import automationFramework.Driver;
import automationFramework.EnvConfiguration;
import automationFramework.XLReader;
import pageObjects.SLCVPaymentPgObj;
import pageObjects.SLVCHomePgObj;
import sunLoverModule.SLConHomePageAction;
import sunLoverModule.SLConsBookingFactory;
import sunLoverModule.SLConsBookingPayViaCCAction;
import sunLoverModule.SLConsPropertySelectionFactory;

/**
 * @author tetambes
 *
 */
public class SLConsBookingPayViaCc_TC {

	static String URL = EnvConfiguration.TRN_URL;
	WebDriverWait wait = new WebDriverWait(Driver.driver, 3000);
	SLConHomePageAction homePgAct = PageFactory.initElements(Driver.driver, SLConHomePageAction.class);
	SLConsBookingFactory bookingFactory = PageFactory.initElements(Driver.driver, SLConsBookingFactory.class);
	SLConsPropertySelectionFactory selectionFactory = PageFactory.initElements(Driver.driver, SLConsPropertySelectionFactory.class);
	SLConsBookingPayViaCCAction CCAction = PageFactory.initElements(Driver.driver, SLConsBookingPayViaCCAction.class);
	SLVCHomePgObj homePgObj = PageFactory.initElements(Driver.driver, SLVCHomePgObj.class);
	SLCVPaymentPgObj payPgobj = PageFactory.initElements(Driver.driver, SLCVPaymentPgObj.class);
	XLReader excelSheet = PageFactory.initElements(Driver.driver, XLReader.class);

	public SLConsBookingPayViaCc_TC() {

		// TODO Auto-generated constructor stub
		PageFactory.initElements(Driver.driver, this);
	}

	/*
	 * @BeforeClass(alwaysRun=true) public void beforeClass() throws Exception {
	 * Driver.driver.get(URL); Driver.driver.manage().window().maximize();
	 * String excelPath = EnvConfiguration.Path_TestData +
	 * EnvConfiguration.File_TestData; excelSheet.setExcelFile(excelPath,
	 * "SunLoverCN");
	 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header"))
	 * ).isDisplayed();
	 * Driver.driver.findElement(By.linkText("Accommodation")).click(); new
	 * WaitForPageToLoad(); System.out.println("Accomodation tab clicked."); }
	 */
	@Test
	public void TC_RG_SLC_Book_Pay_Via_MasterCard() {

		try {
			Thread.sleep(3000);
			System.out.println("Running a test case for SG Booking pay via CC- MasterCard");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header"))).isDisplayed();
			Driver.driver.findElement(By.linkText("Accommodation")).click();
			// new WaitForPageToLoad();
			System.out.println("Accomodation tab clicked.");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("AccomSearchForm"))).isDisplayed();
			String location = excelSheet.getCellData(7, 1);
			System.out.println("Location read from xl: " + location);
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("locationId")));
			homePgAct.selectLocation(location);
			Thread.sleep(3000);
			CCAction.Enter_CheckIn_out_details();
			homePgAct.Click_Search();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("searchFormWaiter_pleaseWait")));
			selectionFactory.Select_Hotel_From_SearchResult("Travelodge Perth");
			// Broacher page action
			String roomType = excelSheet.getCellData(7, 11);
			selectionFactory.Select_Room_Available(roomType);
			wait.until(ExpectedConditions.textToBePresentInElement(payPgobj.getHeader(), "Payment"));
			String adult = excelSheet.getCellData(7, 7);
			bookingFactory.Enter_Adult_Detail(adult);
			bookingFactory.Enter_Booking_Details();
			String CardType = "MASTERCARD";
			bookingFactory.Enter_CC_Details(CardType);
			payPgobj.getTerms_checkbox().click();
			payPgobj.getCnsSubscription_checkbox().click();
			Thread.sleep(2000);
			payPgobj.getBookNPayNow_button().click();
			System.out.println("Booking is in progess.. Please Wait......");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("progress_bar")));
			String Booking_Ref = payPgobj.getBookingNo().getText();
			System.out.println("Booking Reference no. receive is: " + Booking_Ref);
			// write o/p in XL file

		} catch (Exception e) {
			e.getCause();
			e.printStackTrace();
		}
	}

}
