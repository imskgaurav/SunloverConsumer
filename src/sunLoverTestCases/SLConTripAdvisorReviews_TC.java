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
import org.testng.annotations.Test;

import com.thoughtworks.selenium.webdriven.commands.WaitForPageToLoad;

import automationFramework.Driver;
import automationFramework.XLReader;
import pageObjects.SLVCBroucherPgObj;
import pageObjects.SLVCHomePgObj;
import pageObjects.SLVCSearchResultPgObj;
import sunLoverModule.SLConHomePageAction;
import sunLoverModule.SLConsPropertySelectionFactory;

/**
 * @author bhattp
 *
 */
public class SLConTripAdvisorReviews_TC {
	
	static String URL = LaunchSunloverCons.URL;
	WebDriverWait wait = new WebDriverWait(Driver.driver, 3000);
	SLConHomePageAction homePgFact = PageFactory.initElements(Driver.driver, SLConHomePageAction.class);
	SLConsPropertySelectionFactory selectionFactory = PageFactory.initElements(Driver.driver, SLConsPropertySelectionFactory.class);
	SLVCHomePgObj homePgObj = PageFactory.initElements(Driver.driver, SLVCHomePgObj.class);
	SLVCBroucherPgObj BrouchPgobj = PageFactory.initElements(Driver.driver, SLVCBroucherPgObj.class);
	SLVCSearchResultPgObj searchPgobj=PageFactory.initElements(Driver.driver,SLVCSearchResultPgObj.class);
	XLReader excel = new XLReader();
		
	public SLConTripAdvisorReviews_TC()
	{
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
	}
	
	@Test
	public void TC_RG_SLC_Verify_TripAdvisorReviews() throws Exception {

		try {
			System.out.println("Running a test to verify Trip Advisor Reviews....");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("AccomSearchForm"))).isDisplayed();
			String hotel = excel.getCellData(18, 2);
			String nights = excel.getCellData(18, 7);
			System.out.println("Property selected from XL: " + hotel);
			homePgObj.getHotel_name().click();
			homePgObj.getHotel_name().clear();
			homePgObj.getHotel_name().sendKeys(hotel);
			selectionFactory.Choose_Hotel_Name(hotel);
			homePgFact.select_CheckInmonth(excel.getCellData(18, 3));
			homePgFact.select_CheckInDay(excel.getCellData(18, 4));
			homePgFact.enter_NightsToStay(nights);
			homePgFact.select_Adults(excel.getCellData(18, 8));
			homePgObj.getGo_button().click();
		
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("searchFormWaiter_P")));
			wait.until(ExpectedConditions.textToBePresentInElement(BrouchPgobj.getSupplier_header(), hotel));
			Thread.sleep(3000);
			Assert.assertTrue(BrouchPgobj.getTPreviews().isDisplayed(), "TP tab not available..");
			
			BrouchPgobj.getTPreviews().click();
			System.out.println("TP tab displayed");
			Thread.sleep(1000);
			
			Driver.driver.switchTo().frame("tripAdvisorDetail");
			List<WebElement> allReviews = Driver.driver.findElements(By.xpath("//dl[@name='sortableReviewPair']/*"));
			
			System.out.println(allReviews.size());
			if(allReviews.size()>=1)
				System.out.println("Reviews present....");
			else
				System.out.println("No Reviews added");
	
			Thread.sleep(5000);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			AssertJUnit.fail(e.getMessage());
		}
		}
}
