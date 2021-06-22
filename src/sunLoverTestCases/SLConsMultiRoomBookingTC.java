package sunLoverTestCases;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SLConsMultiRoomBookingTC {
	// TestObjective:- to check multiple booking
	// Expected result:- User should able to book many accommodation in single
	// booking
	// Positive Test Case
	private WebDriver driver;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testSUNLoverCMultipleBookingTC() throws Exception {
		driver.get("http://sunloverconsumer.brd.travel-bookings.net/");
		driver.findElement(By.id("lookuphotel")).clear();
		driver.findElement(By.id("lookuphotel")).sendKeys("canberra");
		// driver.findElement(By.id("btn_10")).click();

		List<String[]> dateList = new ArrayList<String[]>();
		dateList.add(new String[] { "2016-02", "Tue23 22", "Dec", "Wed 31" });
		dateList.add(new String[] { "Jan", "Wed 22", "Jan", "Thu 23" });

		for (String[] dates : dateList) {
			new Select(driver.findElement(By.name("dateFrom_month"))).selectByValue(dates[0]);
			new Select(driver.findElement(By.name("dateFrom_day"))).selectByVisibleText(dates[1]);
			new Select(driver.findElement(By.name("checkout_month"))).selectByVisibleText(dates[2]);
			new Select(driver.findElement(By.name("checkout_day"))).selectByVisibleText(dates[3]);
			driver.findElement(By.id("btn_10")).click();

			// Wait the search result comes back
			WebElement wait = (new WebDriverWait(driver, 180)).until(ExpectedConditions.presenceOfElementLocated(By
					.xpath("//DIV[@id='page']/DIV[@id='contentsurround']/DIV[@id='content']/DIV[2]/FORM[@id='toolbar']/DIV/P")));

			// Check if "no available" wording appear
			String noResultTxt = driver
					.findElement(By
							.xpath("//DIV[@id='page']/DIV[@id='contentsurround']/DIV[@id='content']/DIV[2]/FORM[@id='toolbar']/DIV/P"))
					.getText();
			if (noResultTxt.contains("no available")) {
				continue;
			} else {
				break;
			}

		}
		System.exit(0);
		// ERROR: Caught exception [ERROR: Unsupported command [selectWindow |
		// null | ]]
		/*
		 * try {
		 * assertTrue(driver.findElement(By.cssSelector("BODY")).getText().
		 * matches("^[\\s\\S]*$")); } catch (Error e) {
		 * verificationErrors.append(e.toString()); }
		 */

		System.out.println("Start trying to get an available option...");
		boolean hasAvailableOption = getAvailableOption();
		System.out.println("End trying to get an available option, result is:" + hasAvailableOption);
		if (!hasAvailableOption) {
			fail("No available option");
		}

		if (!hasAvailableOption) {
			fail("No available option");
		}

		// driver.findElement(By.cssSelector("span.supplierName")).click();

		// ERROR: Caught exception [ERROR: Unsupported command [waitForCondition
		// | | 9000]]
		driver.findElement(By.cssSelector("div.roomAddButton > a.instantBook > img.bookbutton")).click();
		System.out.println("Add the option to itinerary...");
		// ERROR: Caught exception [ERROR: Unsupported command [waitForCondition
		// | | 9000]]

		System.out.println(
				"-------------------------------------------Start book the second option-------------------------------------------");
		driver.findElement(By.id("addAccommodation")).click();

		new Select(driver.findElement(By.id("locationId"))).selectByVisibleText("Melbourne");
		driver.findElement(By.name("search")).click();

		// driver.findElement(By.cssSelector("img.bookbutton")).click();
		System.out.println("Start trying to get an available option...");
		hasAvailableOption = getAvailableOption();
		System.out.println("End trying to get an available option, result is:" + hasAvailableOption);
		if (!hasAvailableOption) {
			fail("No available option");
		}

		// ERROR: Caught exception [ERROR: Unsupported command [selectWindow |
		// null | ]]
		// ERROR: Caught exception [ERROR: Unsupported command [waitForCondition
		// | | 9000]]

		System.out.println("End trying to get an available option, result is:" + hasAvailableOption);
		driver.findElement(By.cssSelector("div.roomAddButton > a.instantBook > img.bookbutton")).click();
		System.out.println("Add the option to itinerary...");

		driver.findElement(By.linkText("Book")).click();
		System.out.println(
				"******************************************Start booking process******************************************");
		// ERROR: Caught exception [ERROR: Unsupported command [waitForCondition
		// | | 3000]]
		driver.findElement(By.id("passengerSurname_A_0")).clear();
		driver.findElement(By.id("passengerSurname_A_0")).sendKeys("rane");
		driver.findElement(By.id("passengerForename_A_0")).clear();
		driver.findElement(By.id("passengerForename_A_0")).sendKeys("ujjwala");
		new Select(driver.findElement(By.id("passengerTitle_A_0"))).selectByVisibleText("Ms");
		driver.findElement(By.linkText("Copy passengers from top")).click();
		driver.findElement(By.xpath("(//a[contains(text(),'Copy passengers from top')])[2]")).click();
		new Select(driver.findElement(By.name("title"))).selectByVisibleText("Ms");
		driver.findElement(By.name("forename")).clear();
		driver.findElement(By.name("forename")).sendKeys("ujjwala");
		driver.findElement(By.name("surname")).clear();
		driver.findElement(By.name("surname")).sendKeys("rane");
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys("testa.aot@gmail.com");
		driver.findElement(By.name("phone")).clear();
		driver.findElement(By.name("phone")).sendKeys("1234567897");
		driver.findElement(By.name("address")).clear();
		driver.findElement(By.name("address")).sendKeys("st. Marry Street");
		driver.findElement(By.name("city")).clear();
		driver.findElement(By.name("city")).sendKeys("Melton");
		driver.findElement(By.name("state")).clear();
		driver.findElement(By.name("state")).sendKeys("NSW");
		driver.findElement(By.name("postcode")).clear();
		driver.findElement(By.name("postcode")).sendKeys("456321");
		driver.findElement(By.name("cardHolderName")).clear();
		driver.findElement(By.name("cardHolderName")).sendKeys("ujjwala rane");
		new Select(driver.findElement(By.name("cardType"))).selectByVisibleText("VISA");
		driver.findElement(By.name("cardNumber")).clear();
		driver.findElement(By.name("cardNumber")).sendKeys("4557012345678902");
		new Select(driver.findElement(By.name("expiryDate_month"))).selectByVisibleText("05");
		new Select(driver.findElement(By.name("expiryDate_year"))).selectByVisibleText("17");
		driver.findElement(By.name("cardSecurityCode")).clear();
		driver.findElement(By.name("cardSecurityCode")).sendKeys("123");
		driver.findElement(By.id("terms")).click();
		driver.findElement(By.id("consumerSubscription")).click();
		driver.findElement(By.name("submit")).click();
		System.out.println("Click submit...");

		// Wait the booking finished
		System.out.println("Waiting the booking to be finished...");
		WebElement wait = (new WebDriverWait(driver, 180)).until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//DIV[@id='page']/DIV[@id='contentsurround']/DIV[@id='content']/DIV/H1")));

		String resultTxt = driver
				.findElement(
						By.xpath("//DIV[@id='page']/DIV[@id='contentsurround']/DIV[@id='content']/DIV/DIV/P[1]/STRONG"))
				.getText();
		if (resultTxt.toUpperCase().contains("CONFIRMED") || resultTxt.toUpperCase().contains("REQUEST")) {
			System.out.println("Booking Succeed!!!");
		} else {
			System.out.println("Booking Failed!!!");
		}
	}

	private boolean getAvailableOption() {
		int index = 0;
		int webElementNum = 20;
		while (index < webElementNum && index < 20) {
			List<WebElement> supplierNameList = driver.findElements(By.cssSelector("span.supplierName"));

			webElementNum = supplierNameList.size();

			WebElement supplierNameWE = supplierNameList.get(index++);
			if (null == supplierNameWE) {
				System.out.println("Empty supplier, skip...");
				continue;
			}

			supplierNameWE.click();
			System.out.println("Find a possible supplier, get into brochure page");

			// Fake the date to unavailable dates
			driver.findElement(By.name("dateFrom_month")).click();

			// Fake code to make the option unavailable
			System.out.println("Force to set the date as Dec 31 to try to make the option not available");
			new Select(driver.findElement(By.name("dateFrom_month"))).selectByVisibleText("Dec");
			new Select(driver.findElement(By.name("dateFrom_day"))).selectByVisibleText("Wed 31");

			driver.findElement(By.id("btn_10")).click();

			// Check the availability
			String roomInfoText = driver.findElement(By.id("roomsPanel")).getText();
			while (roomInfoText.contains("Please wait") || roomInfoText.contains("Error")) {
				if (roomInfoText.contains("Error")) {
					System.out.println("Ajax request error!");
					System.exit(1);
				}
				System.out.println("Waiting Ajax request return...");
				driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
				roomInfoText = driver.findElement(By.id("roomsPanel")).getText();
			}

			if (roomInfoText.contains("There are no available rooms for your criteria")) {
				System.out.println("No available option");
				// Go back to search result page
				driver.findElement(By.linkText("Accommodation")).click();

				System.out.println("Click the [Accommodation] menu to go back, and force the date as Jan 21-Jan 28");
				// Fake code to recover the search parameter
				new Select(driver.findElement(By.name("dateFrom_month"))).selectByVisibleText("Jan");
				new Select(driver.findElement(By.name("dateFrom_day"))).selectByVisibleText("Tue 21");
				new Select(driver.findElement(By.name("checkout_month"))).selectByVisibleText("Jan");
				new Select(driver.findElement(By.name("checkout_day"))).selectByVisibleText("Tue 28");

				driver.findElement(By.name("search")).click();
				System.out.println("Search again...");
			} else {
				System.out.println("Found available option");
				return true;
			}
		}

		return false;
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
}
