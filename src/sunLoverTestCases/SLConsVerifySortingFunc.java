package sunLoverTestCases;

import java.util.Arrays;
import java.util.Iterator;
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
import pageObjects.SLCVPaymentPgObj;
import pageObjects.SLVCBookingSummaryPgobj;
import pageObjects.SLVCBroucherPgObj;
import pageObjects.SLVCHomePgObj;
import sunLoverModule.SLConHomePageAction;
import sunLoverModule.SLConsBookingFactory;
import sunLoverModule.SLConsPropertySelectionFactory;

public class SLConsVerifySortingFunc {
	static String URL = LaunchSunloverCons.URL;
	WebDriverWait wait = new WebDriverWait(Driver.driver, 3000);
	SLConHomePageAction homePgFact = PageFactory.initElements(Driver.driver, SLConHomePageAction.class);
	SLConsBookingFactory bookingFact = PageFactory.initElements(Driver.driver, SLConsBookingFactory.class);
	SLConsPropertySelectionFactory selectionFactory = PageFactory.initElements(Driver.driver,
			SLConsPropertySelectionFactory.class);
	SLVCHomePgObj homePgObj = PageFactory.initElements(Driver.driver, SLVCHomePgObj.class);
	SLVCBroucherPgObj BrouchPgobj = PageFactory.initElements(Driver.driver, SLVCBroucherPgObj.class);
	SLCVPaymentPgObj payPgobj = PageFactory.initElements(Driver.driver, SLCVPaymentPgObj.class);
	SLVCBookingSummaryPgobj summaryPgobj = PageFactory.initElements(Driver.driver, SLVCBookingSummaryPgobj.class);
	SLConsPropertySelectionFactory BrochPgFunction = PageFactory.initElements(Driver.driver,
			SLConsPropertySelectionFactory.class);
	XLReader excel = new XLReader();
	SLConHomePageAction pgAct = PageFactory.initElements(Driver.driver, SLConHomePageAction.class);

	public SLConsVerifySortingFunc() {
		// TODO Auto-generated constructor stub
		PageFactory.initElements(Driver.driver, this);
	}

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		Driver.driver.get(URL);
		Driver.driver.manage().window().maximize();
		Driver.driver.findElement(By.linkText("Accommodation")).click();
		new WaitForPageToLoad();
		System.out.println("Accomodation tab clicked.");
	}

	@Test(priority = 1)
	public void TC_RG_SLC_SortingbyPrice() {

		try {
			System.out.println("Searching by Location....");
			String location = excel.getCellData(15, 1);
			System.out.println("Location value: " + location);
			pgAct.selectLocation(location);
			Thread.sleep(500);
			if (homePgObj.getGo_button().isEnabled()) {
				System.out.println("search button appeared..");
				homePgObj.getGo_button().click();
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("searchFormWaiter_P")));
			} else {
				System.out.println("Search button is disable wait..");
				new WebDriverWait(Driver.driver, 5).until(ExpectedConditions.visibilityOf(homePgObj.getGo_button()));
				homePgObj.getGo_button().click();
			}
			BrouchPgobj.select_total_price_radioButton().click();
			new WaitForPageToLoad();
			List<WebElement> priceinau = Driver.driver.findElements(By.cssSelector(".rate.total"));
			Iterator<WebElement> rateitr = priceinau.iterator();

			System.out.println(" Search results after Sorting by PRICE  :" + priceinau.size());
			String rateValue[] = new String[priceinau.size()];
			int rateinIntArr[] = new int[priceinau.size()];

			int i = 0;
			while (rateitr.hasNext()) {
				rateValue[i] = rateitr.next().getText().replaceAll("[^\\d.]", "");
				rateinIntArr[i] = Integer.parseInt(rateValue[i]);
				i++;
			}

			for (int j = 0; j < rateinIntArr.length - 1; j++) {
				Assert.assertTrue(rateinIntArr[j] <= rateinIntArr[j + 1]);
			}
			System.out.println("Sorting by Price is verified Successfully");

		}

		catch (Exception e) {
			e.printStackTrace();
			AssertJUnit.fail(e.getMessage());
			
		}

	}

	@Test(priority = 2)
	public void TC_RG_SLC_SortingbyName() {
		try{
		System.out.println("Sorting by Supplier Name");
		//BrouchPgobj.select_hotelName_radioButton().click();
		new WaitForPageToLoad();
		List<WebElement> hotelNames = Driver.driver.findElements(By.cssSelector(("a.supplierName")));
		Iterator<WebElement> namesitr = hotelNames.iterator();
		String temp[] = new String[hotelNames.size()];
		String supplierName[] = new String[hotelNames.size()];
		System.out.println("Total search Results are :" + hotelNames.size());
		System.out.println("Iterating Supplier List");
		int j = 0;
		while (namesitr.hasNext()) {
			temp[j] = namesitr.next().getText();
			supplierName[j] = temp[j];
			j++;
		}
		System.out.println("Iterating in Alpgabetical Order");
		Arrays.sort(temp);
		for (int a = 0; a < temp.length; a++) {
			Assert.assertTrue(temp[a].toString().contentEquals(supplierName[a].toString()));

		}
		System.out.println("Sorting by Name is verified Successfully");
		}
		catch (Exception e) {
			e.printStackTrace();
			AssertJUnit.fail(e.getMessage());
		}
	}

	@Test(priority = 3)

	public void TC_RG_SLC_SortingbyStarRating() {
		try{
			BrouchPgobj.select_star_rating_radioButton().click();
			new WaitForPageToLoad();
			List<WebElement> ratingStars = Driver.driver.findElements(By.cssSelector((".starRating")));
			String starRating[] = new String[ratingStars.size()];
			System.out.println("Search result of Star Rating are: " + ratingStars.size());
			float rating[] = new float[ratingStars.size()];
			Iterator<WebElement> ratingitr = ratingStars.iterator();
			int k = 0;
			while (ratingitr.hasNext()) {
				starRating[k] = ratingitr.next().getAttribute("title").replaceAll("[^\\d.]", "");
				rating[k] = Float.parseFloat(starRating[k]);
				k++;
			}
			for (int j = 0; j < rating.length - 1; j++) {
				Assert.assertTrue(rating[j] >= rating[j + 1]);
			}
			System.out.println("Sorting by Star is Verified");
		}
		catch (Exception e) {
			e.printStackTrace();
			AssertJUnit.fail(e.getMessage());
			
		}
	}

}
