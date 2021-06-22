/**
 * 
 */
package sunLoverTestCases;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import automationFramework.Driver;
import automationFramework.XLReader;
import automationFramework.XLWriter;
import pageObjects.SLCVItineraryPgObj;
import pageObjects.SLCVPaymentPgObj;
import pageObjects.SLCVServiceDetailsPgObj;
import pageObjects.SLVCBookingSummaryPgobj;
import pageObjects.SLVCBroucherPgObj;
import pageObjects.SLVCCarHirePgObj;
import pageObjects.SLVCHomePgObj;
import pageObjects.SLVCTransfersPgObj;
import pageObjects.SLVCTransfersSearchResultPgObj;
import sunLoverModule.SLConCarHirePageAction;
import sunLoverModule.SLConHomePageAction;
import sunLoverModule.SLConItineraryPageAction;
import sunLoverModule.SLConServiceDetailsAction;
import sunLoverModule.SLConTransfersPageAction;
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
public class SLConsAllServicesBookingTC {

	/** Test Objective : Booking of multiple service in one
	 *  Positive Test Case
	 *  Expected Result : User should able to book multiple service in single booking 
	 */
	
	static String URL = LaunchSunloverCons.URL;
	WebDriverWait wait = new WebDriverWait(Driver.driver, 3000);
	SeleniumWaitClass seleniumWait = new SeleniumWaitClass();
	JavascriptExecutor js = ((JavascriptExecutor) Driver.driver);
	Actions action = new Actions(Driver.driver);
	SLConHomePageAction homePgAct = PageFactory.initElements(Driver.driver, SLConHomePageAction.class);
	SLConsBookingFactory bookingFactory = PageFactory.initElements(Driver.driver, SLConsBookingFactory.class);
	SLConsPropertySelectionFactory selectionFactory = PageFactory.initElements(Driver.driver, SLConsPropertySelectionFactory.class);
	XLReader excelSheet = PageFactory.initElements(Driver.driver, XLReader.class);
	SLConsBookingPayViaCCAction CCAction = PageFactory.initElements(Driver.driver, SLConsBookingPayViaCCAction.class);
	SLVCHomePgObj homePgObj = PageFactory.initElements(Driver.driver, SLVCHomePgObj.class);
	SLVCBroucherPgObj BrouchPgobj = PageFactory.initElements(Driver.driver, SLVCBroucherPgObj.class);
	SLCVItineraryPgObj itnyPgobj = PageFactory.initElements(Driver.driver, SLCVItineraryPgObj.class);
	SLConItineraryPageAction itnyPgAct = PageFactory.initElements(Driver.driver, SLConItineraryPageAction.class);
	SLConCarHirePageAction CarPgAct = PageFactory.initElements(Driver.driver, SLConCarHirePageAction.class);
	SLVCCarHirePgObj CarPgObj = PageFactory.initElements(Driver.driver, SLVCCarHirePgObj.class);
	SLConTransfersPageAction trsfsPgAct = PageFactory.initElements(Driver.driver, SLConTransfersPageAction.class);
	SLVCTransfersPgObj trsfsPgObj = PageFactory.initElements(Driver.driver, SLVCTransfersPgObj.class);
	SLVCTransfersSearchResultPgObj trsfsSearchResultPgObj = PageFactory.initElements(Driver.driver, SLVCTransfersSearchResultPgObj.class);
	SLCVServiceDetailsPgObj srvsDetailsPgObj = PageFactory.initElements(Driver.driver, SLCVServiceDetailsPgObj.class);
	SLConServiceDetailsAction servFact = PageFactory.initElements(Driver.driver, SLConServiceDetailsAction.class);
	SLCVPaymentPgObj payPgobj = PageFactory.initElements(Driver.driver, SLCVPaymentPgObj.class);
	SLVCBookingSummaryPgobj summaryPgObj = PageFactory.initElements(Driver.driver, SLVCBookingSummaryPgobj.class);
	XLWriter writer = PageFactory.initElements(Driver.driver, XLWriter.class);
	
	public SLConsAllServicesBookingTC() {
		// TODO Auto-generated constructor stub
		
		PageFactory.initElements(Driver.driver, this);
	}
	
	@BeforeClass(alwaysRun=true)
	public void beforeClass() {
		Driver.driver.get(URL);
		Driver.driver.manage().window().maximize();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header"))).isDisplayed();
		Driver.driver.findElement(By.linkText("Accommodation")).click();
		seleniumWait.waitforPageToLoad();
		Driver.driver.manage().deleteAllCookies();
		System.out.println("Accomodation tab clicked.");
		Driver.driver.navigate().refresh();
	}
	
	
	@Test
	public void TC_RG_SLC_Add_Booking_with_AllService() throws Exception 
	{
		try{
		Thread.sleep(3000);
		System.out.println("*******Running a test case to book all services into one booking*******");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("AccomSearchForm"))).isDisplayed();
		
// Now add any ON REQUEST Accommodation to an itinerary
		String location = excelSheet.getCellData(14, 1);
		System.out.println("Location read from xl: " + location);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("locationId")));
		homePgAct.selectLocation(location);
		Thread.sleep(3000);
		CCAction.Enter_CheckIn_out_details(14);
		homePgObj.getGo_button().click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("searchFormWaiter_pleaseWait")));
		selectionFactory.Click_SelectRQ_button();
		seleniumWait.waitforPageToLoad();
		Assert.assertTrue(BrouchPgobj.getSupplier_header().isDisplayed(), "Brochure page not loaded for selected property");
		Thread.sleep(3000);
		String supHeaderBroPg = BrouchPgobj.getSupplier_header().getText();
		selectionFactory.Click_AddToItineraryRQ_button();
		wait.until(ExpectedConditions.textToBePresentInElement(itnyPgobj.getHeader(), "Itinerary"));
		itnyPgAct.validate_Itinerary(supHeaderBroPg); 
		Thread.sleep(3000);
		homePgObj.getView_itinerary_tab().click();
		seleniumWait.waitforPageToLoad();
		
// Now add an Instant Confirm Accommodation to an itinerary............
//This code is commented as last min or pegaseus product as no room availabilty 
	/*	System.out.println("Add any accommodation service into itinerary..");
		itnyPgobj.getAdd_accomodation_btn().click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("AccomSearchForm"))).isDisplayed();
		homePgAct.selectLocation(location);
		Thread.sleep(3000);
		CCAction.Enter_CheckIn_out_details(14);
		homePgObj.getGo_button().click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("searchFormWaiter_pleaseWait")));
		selectionFactory.Click_SelectInstant_Button();
		seleniumWait.waitforPageToLoad();
		Assert.assertTrue(BrouchPgobj.getSupplier_header().isDisplayed(), "Brochure page not loaded for selected property");
		Thread.sleep(3000);
		String supHeaderBroPg2 = BrouchPgobj.getSupplier_header().getText();
		selectionFactory.Click_AddToItinerary_button();
		wait.until(ExpectedConditions.textToBePresentInElement(itnyPgobj.getHeader(), "Itinerary"));
		itnyPgAct.validate_Itinerary(supHeaderBroPg2);
		Thread.sleep(3000);
		homePgObj.getView_itinerary_tab().click();
		seleniumWait.waitforPageToLoad();
	*/
		
// Now add any Car Hire Service to an itinerary......
		System.out.println("Add a car hire service into itinerary....");
		action.moveToElement(itnyPgobj.getAdd_carHire_btn());
		itnyPgobj.getAdd_carHire_btn().click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ires>h1")));
		CarPgAct.selectLocation(location);
		CarPgAct.select_CheckInMonth(excelSheet.getCellData(33, 3));
		CarPgAct.select_CheckInDay(excelSheet.getCellData(33, 4));
		CarPgAct.select_CheckOutMonth(excelSheet.getCellData(33, 5));
		CarPgAct.select_CheckOutDay(excelSheet.getCellData(33, 6));
		wait.until(ExpectedConditions.elementToBeClickable(CarPgObj.get_GoButton()));
		CarPgAct.Click_Go();
		seleniumWait.waitforPageToLoad();
		List<WebElement> bookbtn1 = Driver.driver.findElements(By.xpath("//img[starts-with(@id, 'btn_')]"));					
		int numOfResult = bookbtn1.size();
		System.out.println("Result list of Car Hire in selected criteria is: "+numOfResult);
		WebElement Book_button = Driver.driver.findElement(By.xpath(".//*[@id='btn_43']"));
		Book_button.click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("ires")));
		CarPgObj.getTerms_checkbox().click();;
		CarPgObj.getnotes_checkbox().click();
		wait.until(ExpectedConditions.elementToBeClickable(CarPgObj.get_bookbtn()));
		CarPgObj.get_bookbtn().click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("totalprice")));
		Thread.sleep(3000);
		homePgObj.getView_itinerary_tab().click();
		seleniumWait.waitforPageToLoad();
		action.moveToElement(Driver.driver.findElement(By.id("addMoreServices")));
		js.executeScript("document.getElementById('addTransfers').scrollIntoView(true);");
		
// Now add any Transfer Service to an itinerary...........
		System.out.println("Now Add a transfer service to an itinerary......");
	//	wait.until(ExpectedConditions.elementToBeClickable(itnyPgobj.getAdd_transfer_btn()));
		itnyPgobj.getAdd_transfer_btn().click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mainpanel>div>h1")));
		trsfsPgAct.selectLocation(location);
		trsfsPgAct.select_TransMonth(excelSheet.getCellData(31, 3));
		trsfsPgAct.select_TransDay(excelSheet.getCellData(31, 4));
		wait.until(ExpectedConditions.elementToBeClickable(trsfsPgObj.getGoBtn()));
		trsfsPgAct.click_Go();
		seleniumWait.waitforPageToLoad();
		List<WebElement> bookbtn2 = Driver.driver.findElements(By.xpath("//img[starts-with(@id, 'btn_')]"));
		System.out.println("Result list of Transfers in selected criteria is: " + bookbtn2.size());
		trsfsSearchResultPgObj.getBookBtn().click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("ires")));
		srvsDetailsPgObj.getPickupLocation_txtbox().sendKeys("Adelaide");
		if(Driver.driver.findElement(By.name("dropoffRemark")).isDisplayed())
		{
			srvsDetailsPgObj.getDropOff_req().sendKeys("Test");
		}
		srvsDetailsPgObj.getCheckbox().click();
		wait.until(ExpectedConditions.elementToBeClickable(srvsDetailsPgObj.getBook_button()));
		srvsDetailsPgObj.getBook_button().click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("totalprice")));
		Thread.sleep(3000);
		homePgObj.getView_itinerary_tab().click();
		seleniumWait.waitforPageToLoad();
		action.moveToElement(Driver.driver.findElement(By.id("addMoreServices")));
		
// Now add any Tour Service to an itinerary........
		System.out.println("Add a tour service to an itinerary......");
		itnyPgobj.getAdd_tours_btn().click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ires>h1")));
		servFact.selectLocation(location);
		homePgAct.select_CheckInmonth(excelSheet.getCellData(29, 3));
		homePgAct.select_CheckInDay(excelSheet.getCellData(29, 4));
		wait.until(ExpectedConditions.elementToBeClickable(servFact.getGoBtn()));
		servFact.click_Go();
		seleniumWait.waitforPageToLoad();
		servFact.click_book_btn();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("ires")));
		servFact.getPickUp_request().sendKeys("test");
		servFact.getInfoAgree_checkbox().click();
		servFact.getBook_service_btn().click();
		seleniumWait.waitforPageToLoad();
//		itnyPgobj.getBookBtn().click();
		Thread.sleep(3000);
		homePgObj.getView_itinerary_tab().click();
		seleniumWait.waitforPageToLoad();
		js.executeScript("window.scrollBy(0,250)", "");
		js.executeScript("document.getElementById('addAttractions').scrollIntoView(true);");
		
// Now add an Attraction to an itinerary.......
		System.out.println("Add an attraction to an itinerary.....");
		itnyPgobj.getAdd_attraction_btn().click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ires>h1")));
		servFact.selectLocation(location);
		homePgAct.select_CheckInmonth(excelSheet.getCellData(28, 3));
		homePgAct.select_CheckInDay(excelSheet.getCellData(28, 4));
		wait.until(ExpectedConditions.elementToBeClickable(servFact.getGoBtn()));
		servFact.click_Go();
		seleniumWait.waitforPageToLoad();
		List<WebElement> bookbtn = Driver.driver.findElements(By.xpath("//img[starts-with(@id, 'btn_')]"));
		System.out.println("Result list of Transfers in selected criteria is: " + bookbtn.size());
		servFact.getBookBtn().click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("ires")));
		servFact.getInfoAgree_checkbox().click();
		servFact.getBook_service_btn().click();
		seleniumWait.waitforPageToLoad();
		Assert.assertTrue(Driver.driver.getCurrentUrl().contains("serviceLineId")==true, "Itinerary page not loaded");
//Now add payment details for all services....		
		// wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ires")));
		itnyPgobj.getBookBtn().click();
		wait.until(ExpectedConditions.textToBePresentInElement(payPgobj.getHeader(), "Payment"));
		// Handling adult count from 2DB/ 2TW string..
		String adults = excelSheet.getCellData(14, 8);
		String child = excelSheet.getCellData(14, 9);
		String child_age = excelSheet.getCellData(14, 10);
		if(adults.contentEquals("2 (TW)") || adults.contentEquals("2 (DB)"))
		{
			String adultNum = adults.substring(0,1);
			adults = adultNum;
			System.out.println("Adults no: "+adults);
		}
		bookingFactory.Enter_Passenger_Detail(adults, child, child_age);
		//Handle copy passengers link here.....
		bookingFactory.click_Copy_Passengers_From_Top();
		//Assert.assertTrue(Driver.driver.findElement(By.xpath("//select[starts-with(@id, 'passengerName')]")).isSelected());
		bookingFactory.Enter_Booking_Details();
		String CardType = "MASTERCARD";
		bookingFactory.Enter_CC_Details(CardType);
		payPgobj.getTerms_checkbox().click();
		payPgobj.getCnsSubscription_checkbox().click();
		Thread.sleep(2000);
		payPgobj.getBookNPayNow_button().click();
		System.out.println("Pay Now button clicked... Please Wait......");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("progress_bar")));
		String Booking_Ref = summaryPgObj.getBookingNo().getText();
		System.out.println("Booking Reference no. receive is: " + Booking_Ref);
		String Booking_Status = summaryPgObj.getBookingStatus().getText();
		System.out.println("booking Summary Text: "+Booking_Status);
		
		// write o/p in XL file
		String methodName = excelSheet.getCellData(14, 0);
		System.out.println("Test Case Executed: " + methodName);
		writer.write_Test_Result(13, methodName, Booking_Ref);
		Assert.assertTrue(Driver.driver.getCurrentUrl().contains(Booking_Ref), "Booking Failed please see an error on booking summary");
		}
		catch(Exception ae)
		{
			ae.printStackTrace();
			AssertJUnit.fail(ae.getMessage());
			System.out.println("Booking with all service is failed.....");
		}
	}
		
	

}
