/**
 * 
 */
package sunLoverModule;

import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.AssertJUnit;

import automationFramework.Driver;
import automationFramework.EnvConfiguration;
import automationFramework.XLReader;
import pageObjects.SLCVItineraryPgObj;
import pageObjects.SLCVPaymentPgObj;
import pageObjects.SLCVServiceDetailsPgObj;
import pageObjects.SLVCBookingSummaryPgobj;
import pageObjects.SLVCBroucherPgObj;
import pageObjects.SLVCHomePgObj;

/**
 * @author bolem
 *
 */
public class SLConItineraryPageAction {
	static String URL = EnvConfiguration.BRD_URL;
	WebDriverWait wait = new WebDriverWait(Driver.driver, 3000);
	SLConHomePageAction homePgFact = PageFactory.initElements(Driver.driver, SLConHomePageAction.class);
	SLConsBookingFactory bookingFact = PageFactory.initElements(Driver.driver, SLConsBookingFactory.class);
	SLConsPropertySelectionFactory selectionFactory = PageFactory.initElements(Driver.driver, SLConsPropertySelectionFactory.class);
	SLVCHomePgObj homePgObj = PageFactory.initElements(Driver.driver, SLVCHomePgObj.class);
	SLVCBroucherPgObj BrouchPgobj = PageFactory.initElements(Driver.driver, SLVCBroucherPgObj.class);
	SLCVPaymentPgObj payPgobj = PageFactory.initElements(Driver.driver, SLCVPaymentPgObj.class);
	SLCVItineraryPgObj itnyPgobj = PageFactory.initElements(Driver.driver, SLCVItineraryPgObj.class);
	SLCVServiceDetailsPgObj serviceDetailsPgobj = PageFactory.initElements(Driver.driver, SLCVServiceDetailsPgObj.class);
	SLVCBookingSummaryPgobj summaryPgobj = PageFactory.initElements(Driver.driver, SLVCBookingSummaryPgobj.class);
	XLReader excel = new XLReader();
	
	// Clicking on Add to itinerary button applicable to all Itinerary test cases
	public void  Add_to_Itinerary(int row) throws Exception {

		try {
			Thread.sleep(3000);
			System.out.println("Add to Itinerary");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("AccomSearchForm"))).isDisplayed();
			String hotel = excel.getCellData(row, 2);
			String nights = excel.getCellData(row, 7);
			System.out.println("Property selected from XL: " + hotel);
			homePgObj.getHotel_name().click();
			homePgObj.getHotel_name().clear();
			homePgObj.getHotel_name().sendKeys(hotel);
			selectionFactory.Choose_Hotel_Name(hotel);
			homePgFact.select_CheckInmonth(excel.getCellData(row, 3));
			homePgFact.select_CheckInDay(excel.getCellData(row, 4));
			homePgFact.enter_NightsToStay(nights);
			homePgFact.select_Adults(excel.getCellData(row, 8));
			homePgObj.getGo_button().click();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("searchFormWaiter_pleaseWait")));
			Assert.assertTrue(BrouchPgobj.getSupplier_header().isDisplayed(), "Brochure page not loaded for selected property");
			Thread.sleep(3000);
			String supHeaderBroPg = BrouchPgobj.getSupplier_header().getText();
			selectionFactory.Click_AddToItineraryRQ_button();
			wait.until(ExpectedConditions.textToBePresentInElement(itnyPgobj.getHeader(), "Itinerary"));
			String actHeader = itnyPgobj.getHeader().getText();
			Assert.assertEquals(actHeader, "Itinerary");
			System.out.println("Selected hotel is added to the Itinerary page");
			List<WebElement>supHeaderItnyPg =  Driver.driver.findElements(By.xpath(".//*[@id='detailsForm']//h2"));
			for(int i=0; i<=supHeaderItnyPg.size(); i++)
			{
				if(supHeaderItnyPg.contains(supHeaderBroPg)){
					System.out.println("Supplier name found on Itinerary");
					Assert.assertEquals(supHeaderItnyPg, supHeaderBroPg);
				}
			}
		} 
		
		catch(NoSuchElementException nse){
			
			
		}
		catch (Exception e) {
			e.getCause();
			e.printStackTrace();
			System.out.println("Add to Itinerary not working properly.");
			AssertJUnit.fail(e.getMessage());
		}
		
		finally{
			
			
		}
	}	
	
	public boolean validate_Itinerary(String supHeaderBroPg) throws Exception
	{
	 try{
		String actHeader = itnyPgobj.getHeader().getText();
		Assert.assertEquals(actHeader, "Itinerary");
		System.out.println("Selected hotel is added to the Itinerary page");
		List<WebElement>supHeaderItnyPg =  Driver.driver.findElements(By.xpath(".//*[@id='detailsForm']//h2"));
		for(int i=0; i<=supHeaderItnyPg.size(); i++)
		{
			if(supHeaderItnyPg.contains(supHeaderBroPg)){
				System.out.println("Supplier name found on Itinerary");
				Assert.assertEquals(supHeaderItnyPg, supHeaderBroPg);
				return true;
			}
		}
	}catch(AssertionError error)
	 {
		error.getCause();
		AssertJUnit.fail(error.getMessage());
	 }
	return false;
	}

}
