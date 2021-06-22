/**
 * 
 */
package sunLoverModule;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.AssertJUnit;

import automationFramework.Driver;
import pageObjects.SLVCHomePgObj;
import utilities.SelectDropdown;

/**
 * @author tetambes
 *
 */
public class SLConHomePageAction {

	SLVCHomePgObj HomePgObj = PageFactory.initElements(Driver.driver,
			SLVCHomePgObj.class);
	WebDriverWait wait = new WebDriverWait(Driver.driver, 5);
	JavascriptExecutor jse = (JavascriptExecutor) Driver.driver;
	SelectDropdown drpDwn = PageFactory.initElements(Driver.driver,
			SelectDropdown.class);

	@FindBy(name = "search")
	WebElement Go_button;

	/*
	 * public SLConsHomePageAction() { PageFactory.initElements(Driver.driver,
	 * this); // Select select = PageFactory.initElements(Driver.driver,
	 * Select.class); }
	 */

	public void selectLocation(String location) {
		try {
			WebElement location_id = HomePgObj.getLocation();
			Select locationId = new Select(location_id);
			locationId.selectByVisibleText(location);
			String selected_Loc = locationId.getFirstSelectedOption().getText();
			System.out.println("Location selected is:  " + selected_Loc);
		} catch (Exception ex) {
			ex.getCause();
			ex.printStackTrace();
			System.out.println("Location not found...");
			AssertJUnit.fail(ex.getMessage());
		}
	}

	public void select_CheckInmonth(String month) {
		try {
			WebElement checkInMonth = HomePgObj.getCheck_In_Month();
			Select select_month = new Select(checkInMonth);
			select_month.selectByVisibleText(month);
			String selected_month = select_month.getFirstSelectedOption()
					.getText();
			System.out.println("Check In Month selected: " + selected_month);
		} catch (Exception ex) {
			ex.getCause();
			ex.printStackTrace();
		}
	}

	public void select_CheckInDay(String day) {
		try {
			WebElement checkInDay = HomePgObj.getCheckInDay();
			Select select_day = new Select(checkInDay);
			select_day.selectByValue(day);
			String selected_day = select_day.getFirstSelectedOption().getText();
			System.out.println("Check In Day selected: " + selected_day);
		} catch (Exception ex) {
			ex.getCause();
			ex.printStackTrace();
			AssertJUnit.fail(ex.getMessage());
		}
	}

	public void select_CheckOutmonth(String month) {
		try {
			WebElement checkOutMonth = HomePgObj.getCheckout_month();
			Select select_month = new Select(checkOutMonth);
			select_month.selectByVisibleText(month);
			String selected_month = select_month.getFirstSelectedOption()
					.getText();
			System.out.println("Check Out Month selected: " + selected_month);
		} catch (Exception ex) {
			ex.getCause();
			ex.printStackTrace();
			AssertJUnit.fail();
		}
	}

	public void select_CheckOutDay(String day) {
		try {
			WebElement checkOutDay = HomePgObj.getCheckout_day();
			Select select_day = new Select(checkOutDay);
			select_day.selectByValue(day);
			String selected_day = select_day.getFirstSelectedOption().getText();
			System.out.println("Check Out Day selected: " + selected_day);
		} catch (Exception ex) {
			ex.getCause();
			ex.printStackTrace();
			AssertJUnit.fail(ex.getMessage());
		}
	}

	public void select_Adults(String adult) {
		try {
			WebElement adults = HomePgObj.getPax_adults_count();
			Select select_adult = new Select(adults);
			select_adult.selectByVisibleText(adult);
			new WebDriverWait(Driver.driver, 5);
			String selected_adult = select_adult.getFirstSelectedOption()
					.getText();
			System.out.println("No. of Adults Selected: " + selected_adult);
		} catch (Exception ex) {
			ex.getCause();
			ex.printStackTrace();
			AssertJUnit.fail(ex.getMessage());
		}
	}

	public void select_Child_specify_age(String childCount, String childAge)
			throws Exception {
		try {
			new WebDriverWait(Driver.driver, 5);
			WebElement children = HomePgObj.getPax_children_count();
			Select select_child = new Select(children);
			select_child.selectByVisibleText(childCount);
			String selected_child = select_child.getFirstSelectedOption()
					.getText();
			System.out.println("Selected no. of Childs: " + selected_child);
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.id("hotelchildrenages")))
					.isDisplayed();
			if (selected_child.contentEquals("< 1")) {
				WebElement age1 = HomePgObj.getChildren_age_class1();
				Select select_childAge1 = new Select(age1);
				select_childAge1.selectByVisibleText(childAge);
				System.out
						.println("No. of Children Selected: " + selected_child);
			} else if (selected_child.contentEquals("1")) {
				WebElement age1 = HomePgObj.getChildren_age_class1();
				Select select_childAge1 = new Select(age1);
				select_childAge1.selectByVisibleText(childAge);
				System.out
						.println("No. of Children Selected: " + selected_child);
			} else if (selected_child.contentEquals("2")) {
				WebElement age1 = HomePgObj.getChildren_age_class1();
				WebElement age2 = HomePgObj.getChildren_age_class2();
				drpDwn.selectTextFromElement(age1, childAge);
				drpDwn.selectTextFromElement(age2, childAge);
				System.out
						.println("No. of Children Selected: " + selected_child);
			} else if (selected_child.contentEquals("3")) {
				WebElement age1 = HomePgObj.getChildren_age_class1();
				WebElement age2 = HomePgObj.getChildren_age_class2();
				WebElement age3 = HomePgObj.getChildren_age_class3();
				drpDwn.selectTextFromElement(age1, childAge);
				drpDwn.selectTextFromElement(age2, childAge);
				drpDwn.selectTextFromElement(age3, childAge);
				System.out
						.println("No. of Children Selected: " + selected_child);
			} else {
				System.out.println("child count exceed limit of 3..");
			}
		} catch (Exception ex) {
			ex.getCause();
			ex.printStackTrace();
			AssertJUnit.fail(ex.getMessage());
		}
	}

	public void Enter_HotelName(String hotel) {

		try {
			WebElement hotel_name = HomePgObj.getHotel_name();
			hotel_name.clear();
			hotel_name.sendKeys(hotel);
			String hotelName_SearchFor = hotel_name.getText();
			if (hotelName_SearchFor.contentEquals(hotel)) {
				System.out.println("property entered..");
			} /*
				 * else { System.out.println(
				 * "Property not found.. Please select another..");
				 * hotel_name.clear(); hotel_name.sendKeys("Mercure Canberra");
				 * }
				 */
			System.out
					.println("Hotel name looking for: " + hotelName_SearchFor);

		} catch (Exception ex) {
			ex.getCause();
			ex.printStackTrace();
			AssertJUnit.fail(ex.getMessage());
		}
	}

	public void Click_Search() {
		if (Go_button.isEnabled()) {
			System.out.println("go button appeared..");
			Go_button.click();
			// wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("searchFormWaiter_pleaseWait")));

		} else {
			System.out.println("Search button failed to click");
		}
	}

	public String enter_NightsToStay(String nights) throws Exception {
		Thread.sleep(3000);
		WebElement nights_ele = HomePgObj.getNights();
		nights_ele.clear();
		nights_ele.sendKeys(nights);
		Thread.sleep(3000);
		String selectedNights = nights;
		System.out.println("No. of Nights to stay: " + selectedNights);
		return selectedNights;
	}

	public void select_System_Date() throws Exception {
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.cssSelector(".datePicker_icon>img")));
		Driver.driver.manage().deleteAllCookies();
		HomePgObj.getDate_Picker_Icon().click();
		wait.until(ExpectedConditions
				.visibilityOf(HomePgObj.getDate_Picker_Icon()));
		// wait.until(ExpectedConditions.elementSelectionStateToBe(HomePgObj.getTodays_date(),
		// true));
		HomePgObj.getTodays_date().click();
		Thread.sleep(3000);
		Select selected_Date = new Select(HomePgObj.getCheckInDay());
		System.out.println("Selected Sys Date is: "
				+ selected_Date.getFirstSelectedOption().getText());

	}

	public void select_date_from_Next_Month_of_SysDate() throws Exception {
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.cssSelector(".datePicker_icon>img")));
		HomePgObj.getDate_Picker_Icon().click();
		wait.until(ExpectedConditions
				.visibilityOf(HomePgObj.getDate_Picker_Icon()));
		if (HomePgObj.getTodays_date().isDisplayed()) {
			Thread.sleep(3000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.cssSelector(".dpNavNextMonth>a")));
			Driver.driver.findElement(By.xpath("//a[@title='Next Month.']"))
					.click();
			wait.until(ExpectedConditions
					.elementSelectionStateToBe(By.linkText("25"), true));
			Driver.driver.findElement(By.linkText("25")).click();
		} else {
			HomePgObj.getTodays_date().click();
		}
		Select selected_date = new Select(HomePgObj.getCheckInDay());
		System.out.println("Selected booking date is: "
				+ selected_date.getFirstSelectedOption().getText());
	}
	
	public void compare_Headers(String expHeader, WebElement actHeaderContainer) {
		try {
			new WebDriverWait(Driver.driver, 15);
			String actHeader = actHeaderContainer.getText();
			Assert.assertEquals(actHeader, expHeader);
			Driver.driver.findElement(By.id("logo")).click();
			new WebDriverWait(Driver.driver, 15);
			jse.executeScript("scroll(250, 0)");
		} catch (Exception ex) {
			ex.getCause();
			ex.printStackTrace();
			AssertJUnit.fail(ex.getMessage());
		}
	}

}
