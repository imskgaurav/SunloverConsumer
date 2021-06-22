/**
 * 
 */
package sunLoverModule;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;

import automationFramework.Driver;
import pageObjects.SLCVPaymentPgObj;
import utilities.SelectDropdown;

/**
 * @author tetambes
 *
 */
public class SLConsBookingFactory {

	SLCVPaymentPgObj payPgobj = PageFactory.initElements(Driver.driver,
			SLCVPaymentPgObj.class);
	SelectDropdown drpdwn = PageFactory.initElements(Driver.driver,
			SelectDropdown.class);
	String propertyFilePath = "\\src\\testData\\Card Details.properties";
	WebDriverWait wait = new WebDriverWait(Driver.driver, 3000);

	// Booking details test data
	String surname = "Test";
	String firstname = "Book";
	String email = "testa.aot@gmail.com";
	String clientcontact = "0312345678";
	String strtAddr = "testAddr";
	String suburb = "testTown";
	String state = "test";
	String postCode = "123";

	// This method is used to enter booking details [common for all E2E booking TC]
	public void Enter_Booking_Details() throws Exception {
		try {
			System.out.println("Ënter booking details");
			Select selTitle = new Select(payPgobj.getTitle_drpdwn());
			selTitle.selectByValue("Mr");
			Thread.sleep(3000);
			payPgobj.getFirstname().sendKeys(firstname);
			payPgobj.getLastname().sendKeys(surname);
			payPgobj.getEmail().sendKeys(email);
			payPgobj.getPhone().sendKeys(clientcontact);
			payPgobj.getCity().sendKeys(suburb);
			payPgobj.getState().sendKeys(state);
			payPgobj.getPostcode().sendKeys(postCode);
			System.out.println("Booking detailed entered....");

		} catch (Exception e) {
			e.getCause();
			e.printStackTrace();
		}

	}

	// This method is to enter credit crd details as per card type selection
	public String Enter_CC_Details(String CardType)
	// TODO Auto-generated method stub
	{
		try {
			System.out.println("Now Entering CC details");
			String Current_Dir = System.getProperty("user.dir");
			System.out.println("Current working dir: " + Current_Dir);
			String relative_path = Current_Dir.concat(propertyFilePath);
			File file = new File(relative_path);

			FileInputStream fileInput = null;
			try {
				fileInput = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			Properties prop = new Properties();

			// load properties file
			try {
				prop.load(fileInput);
			} catch (IOException e) {
				e.printStackTrace();
			}
			payPgobj.getCardHolderName()
					.sendKeys(prop.getProperty("cardHoldername"));
			Select selCardType = new Select(payPgobj.getCardType());
			selCardType.selectByVisibleText(CardType);
			String selected_CardType = selCardType.getFirstSelectedOption()
					.getText();
			System.out.println("Credit Card selected: " + selected_CardType);

			if (selected_CardType.contentEquals("MASTERCARD")) {
				System.out.println("Entering mastercard details...");
				payPgobj.getCardNum()
						.sendKeys(prop.getProperty("MasterCardNo"));
				Thread.sleep(3000);
				drpdwn.selectValueFromElement(payPgobj.getExpiryDate_mm(),
						prop.getProperty("expiryMonth"));
				drpdwn.selectValueFromElement(payPgobj.getExpiryDate_yy(),
						prop.getProperty("expiryYear"));
				payPgobj.getCVV().sendKeys(prop.getProperty("SecurityCode1"));
			} else {
				if (selected_CardType.contentEquals("AMERICAN EXPRESS")) {
					System.out.println("Entering american express details...");
					payPgobj.getCardNum()
							.sendKeys(prop.getProperty("AmericanExpress"));
					drpdwn.selectValueFromElement(payPgobj.getExpiryDate_mm(),
							prop.getProperty("expiryMonth"));
					drpdwn.selectValueFromElement(payPgobj.getExpiryDate_yy(),
							prop.getProperty("expiryYear"));
					payPgobj.getCVV()
							.sendKeys(prop.getProperty("SecurityCode2"));
				} else {
					if (selected_CardType.contentEquals("VISA")) {
						System.out.println("Entering visa details...");
						payPgobj.getCardNum()
								.sendKeys(prop.getProperty("VisaCardNo"));
						drpdwn.selectValueFromElement(
								payPgobj.getExpiryDate_mm(),
								prop.getProperty("expiryMonth"));
						drpdwn.selectValueFromElement(
								payPgobj.getExpiryDate_yy(),
								prop.getProperty("expiryYear"));
						payPgobj.getCVV()
								.sendKeys(prop.getProperty("SecurityCode1"));
					} else {
						if (selected_CardType.contentEquals("DINNERS")) {
							System.out.println("Entering Dinners details...");
							payPgobj.getCardNum()
									.sendKeys(prop.getProperty("Diners"));
							drpdwn.selectValueFromElement(
									payPgobj.getExpiryDate_mm(),
									prop.getProperty("expiryMonth"));
							drpdwn.selectValueFromElement(
									payPgobj.getExpiryDate_yy(),
									prop.getProperty("expiryYear"));
						} else {
							System.out
									.println("enterd card type not matched..");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			AssertJUnit.fail(e.getMessage());
		}
		return CardType;
	}

	// This method is to enter passenger details as per pax selection.
	// To use this method if adult string contains characters too then split it
	// into integer before use.
	public boolean Enter_Passenger_Detail(String adult, String child,
			String child_age) throws Exception {
		try {
			int Adult_no = Integer.parseInt(adult);
			System.out.println("Adults details: " + Adult_no);
			int Child_no = Integer.parseInt(child);
			System.out.println("Children details: " + Child_no);

			if (Adult_no > 1 || Child_no >= 1) {
				/*
				 * Select selectAdult = new Select(payPgobj.getAdultsDrpdwn());
				 * selectAdult.selectByValue(adult); System.out.println(
				 * "No. of Adults: " + selectAdult);
				 * 
				 * //Code to select child Age if child exist. if(Child_no!=0){
				 * Select selectChild = new Select(payPgobj.getChildDrpdwn());
				 * selectChild.selectByValue(child); System.out.println(
				 * "No. of Children: " + selectChild); WebElement selectChildAge
				 * = payPgobj.getChildAgeDrpdwn(); System.out.println(
				 * "Selected Child Age is: "+selectChildAge.getText()); }
				 */
				List<WebElement> all_surName = Driver.driver.findElements(
						By.xpath("//table[@id='paxDetails']//tr/td[2]/*"));
				System.out.println(
						"no. of surnames to entered: " + all_surName.size());
				for (int i = 2; i <= all_surName.size(); i++) {
					WebElement surname_ele = Driver.driver.findElement(
							By.xpath("//table[@id='paxDetails']//tr[" + i
									+ "]/td[2]//input[contains(@id, 'passengerSurname')]"));
					surname_ele.clear();
					surname_ele.sendKeys(surname);
					String[] firstName = new String[]{"", "", "Chris", "Harry", "Alex",
							"Book", "Shweta", "Peter", "John", "David"};
					WebElement firstname_ele = Driver.driver.findElement(
							By.xpath("//table[@id='paxDetails']//tr[" + i
									+ "]/td[3]//input[contains(@id, 'passengerForename')]"));
					firstname_ele.clear();
					firstname_ele.sendKeys(firstName[i]);
					System.out.println(
							"Enter Passenger name: " + firstName[i] + surname);
					wait.until(ExpectedConditions
							.visibilityOf(payPgobj.getPax_title()));
					WebElement title_ele = Driver.driver.findElement(
							By.xpath("//table[@id='paxDetails']//tr[" + i
									+ "]/td[@class='passengertitle']"));
					if (title_ele.getText().contains("Adult")) {
						Select title = new Select(Driver.driver.findElement(
								By.xpath("//table[@id='paxDetails']//tr[ " + i
										+ "]//select[starts-with(@id, 'passengerTitle')]")));
						title.selectByValue("Mr");
					} else {
						if (title_ele.getText().contains("Child")) {
							Select title = new Select(Driver.driver.findElement(
									By.xpath("//table[@id='paxDetails']//tr[ "
											+ i
											+ "]//select[starts-with(@id, 'passengerTitle')]")));
							title.selectByValue("Mstr");
						}
					}
				}

			} else {
				payPgobj.getSurname_ele().clear();
				payPgobj.getSurname_ele().sendKeys(surname);
				payPgobj.getFirstname_ele().clear();
				payPgobj.getFirstname_ele().sendKeys(firstname);
				wait.until(ExpectedConditions
						.visibilityOf(payPgobj.getPax_title()));
				Select title = new Select(payPgobj.getPax_title());
				title.selectByValue("Mr");
				Thread.sleep(3000);
				System.out.printf("Passenger details entered are: " + surname,
						firstname, title.getFirstSelectedOption());
				System.out.printf(
						"Entered passenger: "
								+ payPgobj.getSurname_ele().getText(),
						payPgobj.getFirstname_ele().getText());
				return true;
			}

		} catch (Exception ae) {
			ae.printStackTrace();
			AssertJUnit.fail(ae.getMessage());
		}
		return false;

	}

	// This method is used to enter passenger details if adult count is 1 and no
	// children
	public void Enter_Adult_Detail(String adult) throws Exception {
		try {
			int Adult_no = Integer.parseInt(adult);
			System.out.println("No. of Adult from xl: " + Adult_no);
			Select selectAdult = new Select(payPgobj.getAdultsDrpdwn());
			// Validating adult selection on broacher page
			if (selectAdult.getFirstSelectedOption().getText() != adult) {
				System.out.println("validating adults selected..");
				selectAdult.selectByValue(adult);
				System.out.println("No. of Adults selected:  "
						+ selectAdult.getFirstSelectedOption().getText());
			} else {
				System.out.println("Adult selection is correct.......");
			}
			if (Adult_no == 1) {
				payPgobj.getSurname_ele().clear();
				payPgobj.getSurname_ele().sendKeys(surname);
				payPgobj.getFirstname_ele().clear();
				payPgobj.getFirstname_ele().sendKeys(firstname);
				wait.until(ExpectedConditions
						.visibilityOf(payPgobj.getPax_title()));
				Select title = new Select(payPgobj.getPax_title());
				title.selectByValue("Mr");
				Thread.sleep(3000);
				System.out.println("Passenger name is: " + surname + ","
						+ firstname + ".");
			}
		} catch (Exception ae) {
			ae.printStackTrace();
			ae.getCause();
			AssertJUnit.fail(ae.getMessage());
		}

	}
	
	//This method is to click Copy passenger link for n no.of services
	public boolean click_Copy_Passengers_From_Top()
	{
		WebElement booking_summary = Driver.driver.findElement(By.xpath("//span[@class='service-lines']"));
		List<WebElement> linkname = booking_summary.findElements(By.linkText("Copy passengers from top"));
		System.out.println("No. of service added on payment pageis : "+linkname.size());
		for(int i=0; i< linkname.size(); i++)
		{
			linkname.get(i).click();
		}
		return true;
		
	}

}
