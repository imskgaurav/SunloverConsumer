/**
 * 
 */
package sunLoverTestCases;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.webdriven.commands.WaitForPageToLoad;

import automationFramework.Driver;
import automationFramework.XLReader;
import pageObjects.SLVCHomePgObj;
import pageObjects.SLVCSearchResultPgObj;
import sunLoverModule.SLConHomePageAction;
import sunLoverModule.SLConsSearchResultPageAction;


/**
 * @author bhattp
 *
 */
public class SLConAdvSearch_TC {
	
	static String URL = LaunchSunloverCons.URL;
	WebDriverWait wait = new WebDriverWait(Driver.driver, 3000);
	SLConHomePageAction homePgFact = PageFactory.initElements(Driver.driver, SLConHomePageAction.class);
	//SLConsPropertySelectionFactory selectionFactory = PageFactory.initElements(Driver.driver, SLConsPropertySelectionFactory.class);
	SLVCHomePgObj homePgObj = PageFactory.initElements(Driver.driver, SLVCHomePgObj.class);
	//SLVCBroucherPgObj BrouchPgobj = PageFactory.initElements(Driver.driver, SLVCBroucherPgObj.class);
	SLVCSearchResultPgObj searchPgobj=PageFactory.initElements(Driver.driver,SLVCSearchResultPgObj.class);
	JavascriptExecutor jse = (JavascriptExecutor) Driver.driver;
	SLConsSearchResultPageAction resultPgFact= PageFactory.initElements(Driver.driver, SLConsSearchResultPageAction.class);
	XLReader excel = new XLReader();
	
	public SLConAdvSearch_TC()
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
	
	
	
	@Test(priority = 1)
	public void TC_RG_SLC_Verify_MinimumRating() throws Exception {

		try {
			System.out.println("**************Running a test to verify Minimum Rating in Advanced Search Options*****************");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("AccomSearchForm"))).isDisplayed();
			String location = excel.getCellData(16, 1);
			System.out.println("Location value: " + location);
			homePgFact.selectLocation(location);
			String checkInMonth = excel.getCellData(16, 3);
			String checkInDay = excel.getCellData(16, 4);
			String checkOutMonth = excel.getCellData(16, 5);
			String checkOutDay = excel.getCellData(16, 6);
			String nights = excel.getCellData(16, 7);
			homePgFact.select_CheckInmonth(checkInMonth);
			homePgFact.select_CheckOutmonth(checkOutMonth);
			Thread.sleep(3000);
			jse.executeScript("scroll(250, 0)");
			homePgFact.select_CheckInDay(checkInDay);
			homePgFact.select_CheckOutDay(checkOutDay);
			homePgFact.enter_NightsToStay(nights);
			homePgFact.select_Adults(excel.getCellData(16, 8));
			homePgObj.getGo_button().click();
			
			
			Thread.sleep(3000);
			System.out.println("......Before Refining the Results.....");
			if(resultPgFact.noResult_Check())
				System.out.println("No results returned");
			else
			{
				System.out.println("Results returned");
				System.out.println("Number of hotels returned: "+resultPgFact.searchResult_Check());
			}
			
			//Select minimum Star Rating of 5 Stars...
			Select selectRating= new Select(searchPgobj.getMinRating());
			selectRating.selectByVisibleText("5 Stars");
			searchPgobj.getRefine_button().click();
			
			System.out.println("......After Refining the Results.....");
			if(resultPgFact.noResultCheck_onRefining())
				System.out.println("No results returned");
			else
			{
				System.out.println("Results returned");
				System.out.println("Number of hotels returned: "+resultPgFact.searchResult_Check());
			}

		}
		catch(Exception E)
		{
			E.printStackTrace();
			AssertJUnit.fail(E.getMessage());
		}
	}

	
	@Test(priority = 2)
	public void TC_RG_Verify_AccStyle()
	{
		try{
			System.out.println("**********************Running a test to verify Accommodation Style in Advanced Search Options*****************");
			searchPgobj.getClearFilters().click();
			
			Thread.sleep(3000);
			
			System.out.println("......Before Refining the Results.....");
			if(resultPgFact.noResult_Check())
				System.out.println("No results returned");
			else
			{
				System.out.println("Results returned");
				System.out.println("Number of hotels returned: "+resultPgFact.searchResult_Check());
			}
						
			
			Select select_AccStyle = new Select(searchPgobj.getAccomStyle());
			select_AccStyle.selectByVisibleText("Hotels");
			searchPgobj.getRefine_button().click();
			
			
			System.out.println("......After Refining the Results.....");
			if(resultPgFact.noResultCheck_onRefining())
				System.out.println("No results returned");
			else
			{
				System.out.println("Results returned");
				System.out.println("Number of hotels returned: "+resultPgFact.searchResult_Check());
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
			AssertJUnit.fail(e.getMessage());
		}
	}
	
	
	@Test(priority = 3)
	public void TC_RG_Verify_RoomType()
	{
		try{
			
			System.out.println("**********************Running a test to verify Room Type in Advanced Search Options*****************");
			searchPgobj.getClearFilters().click();
			
			Thread.sleep(3000);
			
			System.out.println("......Before Refining the Results.....");
			if(resultPgFact.noResult_Check())
				System.out.println("No results returned");
			else
			{
				System.out.println("Results returned");
				System.out.println("Number of hotels returned: "+resultPgFact.searchResult_Check());
			}
						
			
			Select select_roomType = new Select(searchPgobj.getRoomType());
			select_roomType.selectByVisibleText("3+ Bedroom");
			searchPgobj.getRefine_button().click();
			
			
			System.out.println("......After Refining the Results.....");
			if(resultPgFact.noResultCheck_onRefining())
				System.out.println("No results returned");
			else
			{
				System.out.println("Results returned");
				System.out.println("Number of hotels returned: "+resultPgFact.searchResult_Check());
			}
		}
		catch(Exception e){
			e.printStackTrace();
			AssertJUnit.fail(e.getMessage());
		}
	}
	
	
	@Test(priority = 4)
	public void TC_RG_Verify_AmenitiesSelection()
	{
		try{
			
			System.out.println("**********************Running a test to verify Amenities in Advanced Search Options*****************");
			searchPgobj.getClearFilters().click();
			
			Thread.sleep(3000);
			
			System.out.println("......Before Refining the Results.....");
			if(resultPgFact.noResult_Check())
				System.out.println("No results returned");
			else
			{
				System.out.println("Results returned");
				System.out.println("Number of hotels returned: "+resultPgFact.searchResult_Check());
			}
						
			
			searchPgobj.getAmenities_showHideLink().click();
			searchPgobj.getRoomService().click();
			searchPgobj.getAirConditioning().click();
			searchPgobj.getRefine_button().click();
			
			System.out.println("......After Refining the Results.....");
			if(resultPgFact.noResultCheck_onRefining())
				System.out.println("No results returned");
			else
			{
				System.out.println("Results returned");
				System.out.println("Number of hotels returned: "+resultPgFact.searchResult_Check());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			AssertJUnit.fail(e.getMessage());
			
		}
		
	}	
}
	
	
	
	

