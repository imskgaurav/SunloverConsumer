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
public class SLVCBroucherPgObj {

	@FindBy(id = "supplieraddress")
	private WebElement supplier_header;

	public WebElement getSupplier_header() {
		return supplier_header;
	}

	@FindBy(className = "currency")
	private WebElement priceinAU;

	public WebElement get_Default_RoomRate() {

		return priceinAU;
	}

	@FindBy(id = "btn_10")
	private WebElement update_button;

	@FindBy(id = "roomPanelWait")
	private WebElement broucherPgWait;

	@FindBy(xpath = "//img[@alt='Book Now']")
	private WebElement bookNow_button;

	@FindBy(xpath = "//img[@alt='Add to itinerary']")
	private WebElement AddToItinerary_button;

	@FindBy(id = "currencySelect")
	private WebElement currencyConv;

	public WebElement getTotal_Cost() {
		return Total_Cost;
	}

	@FindBy(css = ".currencyAprox")
	private WebElement NewCurrencyValue;

	public WebElement get_NewCurrency_Value() {

		return NewCurrencyValue;
	}
	@FindBy(xpath = ".//*[@id='roomsPanel']/div")
	private WebElement roomPanel;

	@FindBy(xpath = ".//*[@id='rooms']//span[@class='roomName']")
	private WebElement roomName;

	@FindBy(css = ".currency")
	private WebElement Total_Cost;

	public WebElement getRoomPanel() {
		return roomPanel;
	}

	public WebElement getRoomName() {
		return roomName;
	}

	public WebElement getUpdate_button() {
		return update_button;
	}

	public WebElement getBroucherPgWait() {
		return broucherPgWait;
	}

	public WebElement getBookNow_button() {
		return bookNow_button;
	}

	public WebElement getAddToItinerary_button() {
		return AddToItinerary_button;
	}

	public WebElement getCurrencyConv() {
		return currencyConv;
	}

	public WebElement getRoomInfo() {
		return roomInfo;
	}

	@FindBy(xpath = "//a[@class='moreInfo']")
	private WebElement roomInfo;

}
