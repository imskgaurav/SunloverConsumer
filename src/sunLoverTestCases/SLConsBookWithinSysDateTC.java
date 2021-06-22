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
import pageObjects.SLCVItineraryPgObj;
import pageObjects.SLCVPaymentPgObj;
import pageObjects.SLVCBookingSummaryPgobj;
import pageObjects.SLVCBroucherPgObj;
import pageObjects.SLVCHomePgObj;
import sunLoverModule.SLConHomePageAction;
import sunLoverModule.SLConsBookingFactory;
import sunLoverModule.SLConsPropertySelectionFactory;
import utilities.ScreenShotOnFailure;

/**
 * @author tetambes
 *
 */
@Listeners(ScreenShotOnFailure.class)
public class SLConsBookWithinSysDateTC {
		// TestObjective:- to check User should not do ONREQUEST booking within 3 days from Today/ System Date
		// Expected result:- User should not do ONREQUEST booking within 3 days from SYS DATE
		// E2E booking validation
		// Negative Test Case
	
	static String URL = LaunchSunloverCons.URL;
	WebDriverWait wait = new WebDriverWait(Driver.driver, 3000);
	SLConHomePageAction homePgFact = PageFactory.initElements(Driver.driver, SLConHomePageAction.class);
	SLConsBookingFactory bookingFact = PageFactory.initElements(Driver.driver, SLConsBookingFactory.class);
	SLConsPropertySelectionFactory selectionFactory = PageFactory.initElements(Driver.driver, SLConsPropertySelectionFactory.class);
	SLVCHomePgObj homePgObj = PageFactory.initElements(Driver.driver, SLVCHomePgObj.class);
	SLVCBroucherPgObj BrouchPgobj = PageFactory.initElements(Driver.driver, SLVCBroucherPgObj.class);
	SLCVItineraryPgObj itnyPgobj = PageFactory.initElements(Driver.driver, SLCVItineraryPgObj.class);
	SLCVPaymentPgObj payPgobj = PageFactory.initElements(Driver.driver, SLCVPaymentPgObj.class);
	SLVCBookingSummaryPgobj summaryPgobj = PageFactory.initElements(Driver.driver, SLVCBookingSummaryPgobj.class);
	XLReader excel = new XLReader();

	public SLConsBookWithinSysDateTC() {
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
	
	@Test()
	public void TC_RG_SLC_Book_Service_within_3Days_from_SysDate() throws Exception
	{
	 try{
		Thread.sleep(3000);
		System.out.println("*******************Running a test to book an property within 3 days from System date************************");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("AccomSearchForm"))).isDisplayed();
		String location = excel.getCellData(11, 1);
		String nights = excel.getCellData(11, 7);
		homePgFact.selectLocation(location);
		new WaitForPageToLoad();
		homePgFact.select_System_Date();
		homePgFact.enter_NightsToStay(nights);
		String adults = (String)excel.getCellData(11, 8);
		String child = (String)excel.getCellData(11, 9);
		String child_age = (String)excel.getCellData(11, 10);
		homePgFact.select_Adults(adults);
		homePgFact.select_Child_specify_age(child, child_age);
		homePgObj.getGo_button().click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("searchFormWaiter_pleaseWait")));
		selectionFactory.Click_SelectRQ_button();
		new WaitForPageToLoad();
		// Verify data on broacher page
		String costOnBroucherScrn = BrouchPgobj.getTotal_Cost().getText();
		String[] CostOnBrch = costOnBroucherScrn.split("\\R+");
		System.out.println("Cost on Broucher page: "+CostOnBrch[0]);
		String Title = Driver.driver.getTitle();
		System.out.println("Supplier selected: "+Title);
		// Verify the title of Supplier name selected..
		Assert.assertEquals(Driver.driver.getTitle(), Title);
		//Clicking AddToItinerary button from Broacher page...
		selectionFactory.Click_AddToItineraryRQ_button();
		//Driver.driver.manage().deleteAllCookies();
		//Verify the cost appearing on Screen..
		wait.until(ExpectedConditions.textToBePresentInElement(itnyPgobj.getHeader(), "Itinerary"));
		String CostOnItrPg = Driver.driver.findElement(By.cssSelector(".tabledata.maintintseven>p>strong>span")).getText();
		String CostOnItr = CostOnItrPg.substring(0, CostOnItrPg.indexOf("."));
		System.out.println("Cost on Itinerary page: "+CostOnItr);	
		Assert.assertEquals(CostOnItr, CostOnBrch[0]);
		System.out.println("roomType selected is: "+Driver.driver.findElement(By.xpath(".//*[@id='detailsForm']/div/div[1]/table/tbody/tr[4]/td[2]/p/span[1]")).getText());
		String validationMsg = "One or more services on this itinerary are travelling within 3 days. Please contact us to confirm availability.";
		String actualMsg = Driver.driver.findElement(By.cssSelector(".messageArea>h3")).getText();
		Assert.assertEquals(actualMsg, validationMsg);
		Boolean condition = Driver.driver.findElement(By.cssSelector(".consumerBookDisabled")).isDisplayed();
		Assert.assertTrue(condition, "Book Now button is not disabled for booking within 3 Days from System date");
	} 
	catch (Exception e) {
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
