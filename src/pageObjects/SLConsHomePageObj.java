/**
 * 
 */
package pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

/**
 * @author tetambes
 *
 */
public class SLConsHomePageObj {
	//FindAll Concept Learning/
	
	@FindAll({
		
		@FindBy(id="lookuphotel"),
		@FindBy(css="#lookuphotel")
		
	})
	
	
	@FindBy(xpath="//*[@id='locationId']")
	private WebElement location;
	
	/*
	 * @FindBy(xpath= "//") private WebElement place;
	 */
    
	@FindBy(id = "lookuphotel")
	private WebElement hotel_name;
	
	@FindBy(name="dateFrom_month")
	private WebElement Check_In_Month;
	
	@FindBy(name="dateFrom_day")
	private WebElement checkInDay;
	
	@FindBy(name="checkout_month")
	private WebElement checkout_month;
	
	@FindBy(name="checkout_day")
	private WebElement checkout_day;

	@FindBy(xpath="//input[@name='nights']")
	private WebElement Nights;
	
	@FindBy(xpath="//select[@name='adults']")
	private WebElement pax_adults_count;
	
	@FindBy(xpath="//select[@id='childpaxamount']")
	private WebElement pax_children_count;
	
	@FindBy(xpath="//div[@id='hotelchildrenages']")
	private WebElement children_age_class;
	
	
	
	public WebElement getLocation() {
		return location;
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

	public WebElement getChildren_age_class() {
		return children_age_class;
	}


}
