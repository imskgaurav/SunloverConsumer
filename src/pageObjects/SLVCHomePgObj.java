/**
 * 
 */
package pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author tetambes
 *
 */
public class SLVCHomePgObj {

	@FindBy(id = "locationId")
	private WebElement location;

	@FindBy(id = "lookuphotel")
	private WebElement hotel_name;

	@FindBy(name = "dateFrom_month")
	private WebElement Check_In_Month;

	/*
	 * @FindBy(className="#nextyear") private WebElement Check_In_Month;
	 */

	@FindBy(name = "dateFrom_day")
	private WebElement checkInDay;

	@FindBy(name = "checkout_month")
	private WebElement checkout_month;

	@FindBy(name = "checkout_day")
	private WebElement checkout_day;

	@FindBy(xpath = "//input[@name='nights']")
	private WebElement Nights;

	@FindBy(xpath = "//select[@name='adults']")
	private WebElement pax_adults_count;

	@FindBy(xpath = "//select[@id='childpaxamount']")
	private WebElement pax_children_count;

	@FindBy(xpath = "//select[@id='childAge1']")
	private WebElement children_age_class1;

	@FindBy(xpath = "//select[@id='childAge2']")
	private WebElement children_age_class2;

	@FindBy(xpath = "//select[@id='childAge3']")
	private WebElement children_age_class3;

	@FindBy(xpath = "//td[@class='dpDayToday']")
	private WebElement todays_date;

	@FindBy(xpath = "//div[@id='hotelcheckin']//a[@class='datePicker_icon']")
	private WebElement Date_Picker_Icon;

	public WebElement getTodays_date() {
		return todays_date;
	}

	public WebElement getDate_Picker_Icon() {
		return Date_Picker_Icon;
	}

	public WebElement getChildren_age_class2() {
		return children_age_class2;
	}

	public WebElement getChildren_age_class3() {
		return children_age_class3;
	}

	@FindBy(css = "#btn_10")
	private WebElement search_button;

	@FindBy(id = "searchFormWaiter_P")
	private WebElement searchWait;

	@FindBy(name = "search")
	private WebElement go_button;

	@FindBy(xpath = ".//*[@id='accommodationtab']/a")
	private WebElement accomodation_tab;

	public WebElement getAccomodation_tab() {
		return accomodation_tab;
	}

	public WebElement getGo_button() {
		return go_button;
	}

	public WebElement getLocation() {
		return location;
	}

	public WebElement getSearchWait() {
		return searchWait;
	}

	public WebElement getHotel_name() {
		return hotel_name;
	}

	public WebElement getCheck_In_Month() {
		return Check_In_Month;
	}

	public WebElement getCheckInDay() {
		return checkInDay;
	}

	public WebElement getCheckout_month() {
		return checkout_month;
	}

	public WebElement getCheckout_day() {
		return checkout_day;
	}

	public WebElement getNights() {
		return Nights;
	}

	public WebElement getPax_adults_count() {
		return pax_adults_count;
	}

	public WebElement getPax_children_count() {
		return pax_children_count;
	}

	public WebElement getChildren_age_class1() {
		return children_age_class1;
	}

	public WebElement getSearch_button() {
		return search_button;
	}

	public WebElement getView_itinerary_tab() {
		// TODO Auto-generated method stub
		return null;
	}

}
