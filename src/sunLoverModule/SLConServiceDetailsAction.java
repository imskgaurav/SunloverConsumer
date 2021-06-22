/**
 * 
 */
package sunLoverModule;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.testng.AssertJUnit;

import automationFramework.Driver;
import utilities.SelectDropdown;

/**
 * @author tetambes
 *
 */
public class SLConServiceDetailsAction {

	JavascriptExecutor jse = (JavascriptExecutor) Driver.driver;
	SelectDropdown drpdwn = new SelectDropdown();
	
	@FindBy(css = ".tabledata.maintintseven.roomtype>p>select")		
	public WebElement room_type_ele;
	
	@FindBy(css = ".formrow.tourtype>select")
	private WebElement tourType_ele;

	@FindBy(css = ".formrow.location>select")
	private WebElement Location_ele;

	@FindBy(css = "#adults")
	public WebElement adults_drpdwn;
	
	@FindBy(css = "#childpaxamount")
	private WebElement children_drpdwn;
	
	@FindBy(css= "#childAge1")
	private WebElement childAge1;
	
	@FindBy(css= "#childAge2")
	private WebElement childAge2;
	
	@FindBy(xpath = "//input[@id='btn_10']")
	private WebElement updateRates_btn;
	
	@FindBy(css = "#btn_10")
	private WebElement goBtn;

	@FindBy(css = "#btn_43")
	private WebElement bookBtn;

	@FindBy(css = "#btn_21")
	private WebElement book_service_btn;

	@FindBy(css = "#pickUpInput")
	private WebElement pickUp_request;
	
	@FindBy(css = "#infoAgree")
	private WebElement infoAgree_checkbox;
	
	public WebElement getPickUp_request() {
		return pickUp_request;
	}

	public WebElement getInfoAgree_checkbox() {
		return infoAgree_checkbox;
	}
	
	public WebElement getAdults_drpdwn() {
		return adults_drpdwn;
	}

	public WebElement getBook_service_btn() {
		return book_service_btn;
	}

	public WebElement getBookBtn() {
		return bookBtn;
	}

	public WebElement getLocation_ele() {
		return Location_ele;
	}

	public WebElement getGoBtn() {
		return goBtn;
	}

	public WebElement getUpdateRates_btn() {
		return updateRates_btn;
	}

	
	// Method to select any other tour type
	public void selectTourType() throws Exception
	{
		try{
			drpdwn.selectValueFromElement(tourType_ele, "TOURS_EXTENDED");
			
		}catch (Exception ex) {
			ex.getCause();
			ex.printStackTrace();
		}
	}
	
	// method to select location
	public void selectLocation(String location) {
		try {
			Select locationId = new Select(Location_ele);
			locationId.selectByVisibleText(location);
			String selected_Loc = locationId.getFirstSelectedOption().getText();
			System.out.println("Departs From location selected is:  " + selected_Loc);
		} catch (Exception ex) {
			AssertJUnit.fail(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	//Method to update adults from service detail page
	public void selectAdults(String adult) {
		try {
			Select adults_drpdwn = new Select(getAdults_drpdwn());
			adults_drpdwn.selectByVisibleText(adult);
			String selected_Adult = adults_drpdwn.getFirstSelectedOption().getText();
			System.out.println("Adults selected are:  " + selected_Adult);
		} catch (Exception ex) {
			AssertJUnit.fail(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	//Method to update children amount 
	public void selectChildrens(String childAmount, String childAge) throws Exception
	{
		try{
			drpdwn.selectValueFromElement(children_drpdwn, childAmount);
			int children = Integer.parseInt(childAmount);
			if(children>1){
				drpdwn.selectTextFromElement(childAge1, childAge);
				drpdwn.selectTextFromElement(childAge2, childAge);
			}else{
				drpdwn.selectTextFromElement(childAge1, childAge);
			}
			
		}catch (Exception ex) {
			ex.getCause();
			ex.printStackTrace();
			AssertJUnit.fail(ex.getMessage());
		}
	}
	
	public void click_Go() throws Exception {
		try {
			if (goBtn.isEnabled()) {
				System.out.println("Go button is enabled...");
//				goBtn.click();
				jse.executeScript("arguments[0].click();", getGoBtn());
			} else {
				System.out.println("Go button failed to click");
			}
		} catch (Exception e) {
			e.printStackTrace();
			AssertJUnit.fail(e.getMessage());
		}
	}
	
	public boolean click_book_btn() throws Exception {
		if(Driver.driver.findElement(By.cssSelector(".ires.searchresults")).isDisplayed())
		{
			List<WebElement> bookbtn = Driver.driver.findElements(By.xpath("//img[starts-with(@id, 'btn_')]"));			//img[starts-with(@id, 'btn_')]
			int numOfResult = bookbtn.size();
			System.out.println("Result list of Transfers in selected criteria is: " + numOfResult);
			for(int i=0; i<=numOfResult;)
			{
				Driver.driver.findElement(By.xpath(".//*[@id='btn_45']")).click();
				return true;
			}
		}
		else{
			System.out.println("No service found in result list....");
		}
		return false;
	}
	
	
	public boolean update_room_type(String roomType) throws Exception
	{
		try{
			Select roomTypedrpdwn = new Select(room_type_ele);
			if(roomTypedrpdwn.getAllSelectedOptions().contains(roomType)){
				System.out.println("room type is available..");
				roomTypedrpdwn.selectByVisibleText(roomType);
			}else{
				System.out.println("room type is not available now..");
			}
			
			
		}catch (Exception ex) {
			ex.getCause();
			ex.printStackTrace();
		}
		return false;
	}
}
