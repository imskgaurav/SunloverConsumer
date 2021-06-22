package pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SLVCCarHirePgObj {

	@FindBy(id = "SearchForm")
	private WebElement header;

	@FindBy(name = "location")
	private WebElement location;

	@FindBy(name = "dateFrom_month")
	private WebElement Check_In_Month;

	@FindBy(name = "dateFrom_day")
	private WebElement checkInDay;

	@FindBy(name = "dateFrom_time")
	private WebElement PickUp_time;

	@FindBy(name = "checkout_time")
	private WebElement Dropoff_Time;

	@FindBy(name = "checkout_month")
	private WebElement checkout_month;

	@FindBy(name = "checkout_day")
	private WebElement checkout_day;

	// @FindBy(xpath = "//div[@class='formrow submit']//input[@id='btn_10']")

	@FindBy(xpath = "//div[@class='formrow submit']//input[@name='search']")
	private WebElement GoButton;

	// @FindBy(xpath="//input[@name='search']")

	@FindBy(id = "hireTermsAgree")

	private WebElement terms_checkbox;

	@FindBy(id = "infoAgree")

	private WebElement notes_checkbox;

	@FindBy(xpath = ".//*[@id='btn_21']")
	private WebElement bookbtn;

	@FindBy(css = ".consumerBookEnabled")
	private WebElement consumerBookEnabled;

	/*
	 * 
	 * 
	 * name ="pickupDate_month"
	 * 
	 * name ='pickupDate_day'
	 * 
	 * name ='pickupDate_time'
	 * 
	 * name ='dropoffDate_month' //
	 * 
	 * /* Method to Return WebElement
	 */

	public WebElement getSearchForm() {

		return header;
	}

	public WebElement getLocation() {
		return location;

	}

	public WebElement getCheck_In_Month() {

		return Check_In_Month;
	}

	public WebElement getcheckInDay() {

		return checkInDay;

	}

	public WebElement getCheck_Out_Month() {
		return checkout_month;

	}

	public WebElement getCheckout_day() {

		return checkout_day;
	}

	public WebElement getPickUpTime() {

		return Dropoff_Time;

	}

	public WebElement getDropOffTime() {
		return Dropoff_Time;

	}

	public WebElement getTerms_checkbox() {

		return terms_checkbox;
	}

	public WebElement getnotes_checkbox() {

		return notes_checkbox;
	}

	public WebElement get_GoButton() {

		return GoButton;
	}

	public WebElement get_bookbtn() {

		return bookbtn;
	}

	public WebElement get_Itn_BookBtn() {

		return consumerBookEnabled;
	}
}
