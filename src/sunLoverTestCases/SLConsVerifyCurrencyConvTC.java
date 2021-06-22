package sunLoverTestCases;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.webdriven.commands.WaitForPageToLoad;

import automationFramework.Driver;
import automationFramework.XLReader;
import pageObjects.SLCVPaymentPgObj;
import pageObjects.SLVCBookingSummaryPgobj;
import pageObjects.SLVCBroucherPgObj;
import pageObjects.SLVCHomePgObj;
import sunLoverModule.SLConHomePageAction;
import sunLoverModule.SLConsBookingFactory;
import sunLoverModule.SLConsPropertySelectionFactory;
import utilities.ScreenShotOnFailure;


@Listeners(ScreenShotOnFailure.class)
public class SLConsVerifyCurrencyConvTC {

	static String URL = LaunchSunloverCons.URL;
	WebDriverWait wait = new WebDriverWait(Driver.driver, 3000);
	SLConHomePageAction homePgFact = PageFactory.initElements(Driver.driver, SLConHomePageAction.class);
	SLConsBookingFactory bookingFact = PageFactory.initElements(Driver.driver, SLConsBookingFactory.class);
	SLConsPropertySelectionFactory selectionFactory = PageFactory.initElements(Driver.driver, SLConsPropertySelectionFactory.class);
	SLVCHomePgObj homePgObj = PageFactory.initElements(Driver.driver, SLVCHomePgObj.class);
	SLVCBroucherPgObj BrouchPgobj = PageFactory.initElements(Driver.driver, SLVCBroucherPgObj.class);
	SLCVPaymentPgObj payPgobj = PageFactory.initElements(Driver.driver, SLCVPaymentPgObj.class);
	SLVCBookingSummaryPgobj summaryPgobj = PageFactory.initElements(Driver.driver, SLVCBookingSummaryPgobj.class);
	SLConsPropertySelectionFactory BrochPgFunction = PageFactory.initElements(Driver.driver, SLConsPropertySelectionFactory.class);
	XLReader excel = new XLReader();

	public SLConsVerifyCurrencyConvTC() {
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
	}

	
	/**
	 * Test Objective: To Verify currency converted rates on broacher page
	 * Expected: User should able to verify rates coming for any currency selection.
	 * Positive Unit Test Case
	 * @throws Exception
	 */
	@Test
	public void TC_RG_SLC_CurrencyCon_BroucherPage() throws Exception {

		try {

			Thread.sleep(3000);
			System.out.println("Running a test to verify currency converter...");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("AccomSearchForm"))).isDisplayed();
			String hotel = excel.getCellData(19, 2);
			String nights = excel.getCellData(19, 7);
			System.out.println("Property selected from XL: " + hotel);
			homePgObj.getHotel_name().click();
			homePgObj.getHotel_name().clear();
			homePgObj.getHotel_name().sendKeys(hotel);
			selectionFactory.Choose_Hotel_Name(hotel);
			homePgFact.select_CheckInmonth(excel.getCellData(19, 3));
			homePgFact.select_CheckInDay(excel.getCellData(19, 4));
			homePgFact.enter_NightsToStay(nights);
			homePgObj.getGo_button().click();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("searchFormWaiter_pleaseWait")));
			Assert.assertTrue(BrouchPgobj.getSupplier_header().isDisplayed(), "Brochure page not loaded for selected property");
			Thread.sleep(3000);
			String currenyType = "FJD";
			System.out.println(currenyType);
			String defaultRate = BrouchPgobj.get_Default_RoomRate().getText();
			System.out.println(defaultRate);
			String defaultRateArr[] = defaultRate.split("\\R+");
			System.out.println("Spliting Rate value in AU$ & Nights");
			System.out.println(defaultRateArr[0]);
			String priceInAU = defaultRateArr[0];
			String roomNight = defaultRateArr[1];
			System.out.println(roomNight);
			String priceInNum = priceInAU.substring(2);
			System.out.println("Australian price in num:" + priceInNum);
			Select drpdwn = new Select(BrouchPgobj.getCurrencyConv());
			drpdwn.selectByVisibleText(currenyType);
			String newCurrencyPrice = BrouchPgobj.get_NewCurrency_Value().getText(); 
			System.out.println(newCurrencyPrice);
			
			if (newCurrencyPrice.contains(currenyType)) {
				WebElement option = Driver.driver.findElement(By.cssSelector("#currencySelect"));
				List<WebElement> ratesinList = (List<WebElement>) option.findElements(By.tagName("option"));
				String selectedRateConValue = null;
				String ratefactor[] = new String[ratesinList.size()];
				int i = 0;
				while (i < ratesinList.size()) {
					ratefactor[i] = ratesinList.get(i).getAttribute("value");
					
					if(currenyType.contentEquals(ratesinList.get(i).getText()))
					{
						selectedRateConValue = ratesinList.get(i).getAttribute("value");
						System.out.println("Convertion amount: "+selectedRateConValue);
					}
					i++;
				}
				System.out.println("Newly selected Currency symbol is displayed");
				String[] newRate = newCurrencyPrice.split(" ");
				String rateIn_newCurreny = newRate[1];
				System.out.println("Rate in Selected Currency is :" + rateIn_newCurreny);
				int newcurrency_rate = Integer.parseInt(rateIn_newCurreny);
				int AUpriceNum = Integer.parseInt(priceInNum);
				double ratemultifact = Double.parseDouble(selectedRateConValue);
				System.out.println("Today Currency Conversion Rate for selected Currency is :" + ratemultifact);
				double convertedRate = AUpriceNum * ratemultifact;
				System.out.println("RATE in selected currency is :" + convertedRate);
				int convertedrateIntValue = (int) Math.round(convertedRate);
				System.out.println(convertedrateIntValue);
				Assert.assertEquals(newcurrency_rate, convertedrateIntValue);
				System.out.println("Currency Conversion is working Fine");
			} else
				System.out.println("Currency  Conversion failed....");
		}

		catch (Exception e) 
		{
			e.printStackTrace();
			AssertJUnit.fail(e.getMessage());
		}

	}

}
