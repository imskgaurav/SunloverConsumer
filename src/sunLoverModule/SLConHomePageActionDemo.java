/**
 * 
 */
package sunLoverModule;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import automationFramework.Driver;
import pageObjects.SLVCHomePgObj;

/**
 * @author tetambes
 *
 */
public class SLConHomePageActionDemo {

	SLVCHomePgObj HomePgObj = PageFactory.initElements(Driver.driver, SLVCHomePgObj.class);
	WebDriverWait wait = new WebDriverWait(Driver.driver, 5);
	JavascriptExecutor jse = (JavascriptExecutor) Driver.driver;

	@FindBy(xpath = ".//*[@id='mastersubmit']/input")
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
		}
	}

	public void select_CheckInmonth(String month) {
		try {
			WebElement checkInMonth = HomePgObj.getCheck_In_Month();
			Select select_month = new Select(checkInMonth);
			select_month.selectByVisibleText(month);
			String selected_month = select_month.getFirstSelectedOption().getText();
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
		}
	}

	public void select_CheckOutmonth(String month) {
		try {
			WebElement checkOutMonth = HomePgObj.getCheckout_month();
			Select select_month = new Select(checkOutMonth);
			select_month.selectByVisibleText(month);
			String selected_month = select_month.getFirstSelectedOption().getText();
			System.out.println("Check Out Month selected: " + selected_month);
		} catch (Exception ex) {
			ex.getCause();
			ex.printStackTrace();
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
		}
	}

	public void select_Adults(String adult) {
		try {
			WebElement adults = HomePgObj.getPax_adults_count();
			Select select_adult = new Select(adults);
			select_adult.selectByVisibleText(adult);
			new WebDriverWait(Driver.driver, 5);
			String selected_adult = select_adult.getFirstSelectedOption().getText();
			System.out.println("No. of Adults Selected: " + selected_adult);
		} catch (Exception ex) {
			ex.getCause();
			ex.printStackTrace();
		}
	}

	public void select_Child_specify_age(String childCount, String childAge) throws Exception {
		try {
			new WebDriverWait(Driver.driver, 5);
			WebElement children = HomePgObj.getPax_children_count();
			Select select_child = new Select(children);
			select_child.selectByVisibleText(childCount);
			WebElement age = HomePgObj.getChildren_age_class1();
			Select select_childAge = new Select(age);
			select_childAge.selectByVisibleText(childAge);
			String selected_child = select_child.getFirstSelectedOption().getText();
			System.out.println("No. of Children Selected: " + selected_child);
		} catch (Exception ex) {
			ex.getCause();
			ex.printStackTrace();
		}
	}

	public void Enter_HotelName(String hotel) {

		try {
			WebElement hotel_name = HomePgObj.getHotel_name();
			hotel_name.clear();
			hotel_name.sendKeys(hotel);
			String hotelName_SearchFor = hotel_name.getText();
			if (hotelName_SearchFor == hotel) {
				System.out.println("property selected..");
			} else {
				System.out.println("Property not found.. Please select another..");
				hotel_name.clear();
				hotel_name.sendKeys("Mercure Canberra");
			}
			System.out.println("Hotel name looking for: " + hotelName_SearchFor);

		} catch (Exception ex) {
			ex.getCause();
			ex.printStackTrace();
		}
	}

	public void Click_Search() {
		if (Go_button.isEnabled()) {
			Go_button.click();

		} else {
			System.out.println("Search button failed to click");
		}
	}

	public String enter_NightsToStay(String nights) throws Exception {
		WebElement nights_ele = HomePgObj.getNights();
		nights_ele.clear();
		nights_ele.sendKeys(nights);
		String selectedNights = nights;
		System.out.println("No. of Nights to stay: " + selectedNights);
		return selectedNights;
	}

}
