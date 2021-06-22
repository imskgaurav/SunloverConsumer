/**
 * 
 */
package pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SLCVPaymentPgObj {

	@FindBy(xpath = "//div[@class='ires']//h3")
	private WebElement header;

	@FindBy(id = "passengerSurname_A_1_0")
	private WebElement surname_ele;

	@FindBy(id = "passengerForename_A_1_0")
	private WebElement firstname_ele;

	@FindBy(xpath = "//select[@class='paxTitle']")
	private WebElement pax_title;

	@FindBy(xpath = ".//*[@id='numAdults']")
	private WebElement adultsDrpdwn;

	@FindBy(xpath = ".//*[@id='numChildren']")
	private WebElement childDrpdwn;

	@FindBy(xpath = ".//*[@id='childAge1']")
	private WebElement childAgeDrpdwn;

	@FindBy(linkText = "Copy passengers from top")
	private WebElement copyFrmToplink;

	@FindBy(xpath = "//p[@class='error']")
	private WebElement error_msg;

	public WebElement getError_msg() {
		return error_msg;
	}

	public WebElement getChildAgeDrpdwn() {
		return childAgeDrpdwn;
	}

	public WebElement getCopyFrmToplink() {
		return copyFrmToplink;
	}

	@FindBy(linkText = "Change")
	private WebElement Change_link;

	@FindBy(linkText = "Delete")
	private WebElement Delete_link;

	@FindBy(name = "pickupDate_time_1")
	private WebElement arrivalTime;;

	@FindBy(xpath = "//span[@class='specialRequest']")
	private WebElement addSpecialReqLink;;

	@FindBy(xpath = "//select[@name='title']")
	private WebElement title_drpdwn;

	@FindBy(name = "forename")
	private WebElement firstname;

	@FindBy(name = "surname")
	private WebElement lastname;

	@FindBy(name = "email")
	private WebElement email;

	@FindBy(name = "phone")
	private WebElement phone;

	@FindBy(xpath = ".//*[@id='country']")
	private WebElement country;

	@FindBy(name = "address")
	private WebElement addr;

	@FindBy(name = "city")
	private WebElement city;

	@FindBy(name = "state")
	private WebElement state;

	@FindBy(name = "postcode")
	private WebElement postcode;

	@FindBy(name = "cardHolderName")
	private WebElement cardHolderName;

	@FindBy(xpath = "//select[@name='cardType']")
	private WebElement cardType;

	@FindBy(name = "cardNumber")
	private WebElement cardNum;

	@FindBy(xpath = "//select[@name='expiryDate_month']")
	private WebElement expiryDate_mm;

	@FindBy(xpath = "//select[@name='expiryDate_year']")
	private WebElement expiryDate_yy;

	@FindBy(name = "cardSecurityCode")
	private WebElement CVV;

	@FindBy(name = "terms")
	private WebElement terms_checkbox;

	@FindBy(name = "consumerSubscription")
	private WebElement cnsSubscription_checkbox;

	@FindBy(xpath = "//input[@class='finalise']")
	private WebElement bookNPayNow_button;

	@FindBy(xpath = ".//*[@id='paymentForm']//tr[@class='serviceLineHeader']")
	private WebElement serviceLineHeader;

	public WebElement getPax_title() {
		return pax_title;
	}

	public WebElement getAdultsDrpdwn() {
		return adultsDrpdwn;
	}

	public WebElement getChildDrpdwn() {
		return childDrpdwn;
	}

	public WebElement getServiceLineHeader() {
		return serviceLineHeader;
	}

	public WebElement getHeader() {
		return header;
	}

	public WebElement getSurname_ele() {
		return surname_ele;
	}

	public WebElement getFirstname_ele() {
		return firstname_ele;
	}

	public WebElement getChange_link() {
		return Change_link;
	}

	public WebElement getDelete_link() {
		return Delete_link;
	}

	public WebElement getArrivalTime() {
		return arrivalTime;
	}

	public WebElement getAddSpecialReqLink() {
		return addSpecialReqLink;
	}

	public WebElement getTitle_drpdwn() {
		return title_drpdwn;
	}

	public WebElement getFirstname() {
		return firstname;
	}

	public WebElement getLastname() {
		return lastname;
	}

	public WebElement getEmail() {
		return email;
	}

	public WebElement getPhone() {
		return phone;
	}

	public WebElement getCountry() {
		return country;
	}

	public WebElement getAddr() {
		return addr;
	}

	public WebElement getCity() {
		return city;
	}

	public WebElement getState() {
		return state;
	}

	public WebElement getPostcode() {
		return postcode;
	}

	public WebElement getCardHolderName() {
		return cardHolderName;
	}

	public WebElement getCardType() {
		return cardType;
	}

	public WebElement getCardNum() {
		return cardNum;
	}

	public WebElement getExpiryDate_mm() {
		return expiryDate_mm;
	}

	public WebElement getExpiryDate_yy() {
		return expiryDate_yy;
	}

	public WebElement getCVV() {
		return CVV;
	}

	public WebElement getTerms_checkbox() {
		return terms_checkbox;
	}

	public WebElement getCnsSubscription_checkbox() {
		return cnsSubscription_checkbox;
	}

	public WebElement getBookNPayNow_button() {
		return bookNPayNow_button;
	}

}
