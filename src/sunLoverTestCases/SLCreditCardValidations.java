package sunLoverTestCases;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import automationFramework.Driver;

//import automationFramework.Driver;

public class SLCreditCardValidations {

	WebDriver driver = new FirefoxDriver();
	WebDriverWait wait = new WebDriverWait(driver, 15);

	@BeforeTest

	public void HandleSLCPopUp() throws Exception {
		try {
			driver.get("http://sunloverconsumer.trn.travel-bookings.net/");
			driver.manage().window().maximize();

			new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("popup")));

			if (driver.findElement(By.id("popup")).isDisplayed()) {

				System.out.println("Subcription popup displayed...");
				WebElement element = Driver.driver.findElement(By.name("submit"));
				JavascriptExecutor executor = (JavascriptExecutor) Driver.driver;
				executor.executeScript("arguments[0].click();", element); // handling
																			// close
																			// button
																			// of
																			// pop-up
			}

			AssertJUnit.assertTrue(Driver.driver.findElement(By.id("popup")).isDisplayed() == false);
			System.out.println("popup disappeared................");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// WebDriverWait wait = new WebDriverWait(Driver.driver, 15);
	@Test
	public void SearchHotel() {

		driver.findElement(By.id("hotelname")).sendKeys("Mercure Brisbane");
		driver.findElement(By.id("btn_10")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("rooms")));

	}
}
