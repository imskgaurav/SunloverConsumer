package automationFramework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

public class BaseTestClass {

	protected WebDriver driver;
	protected Selenium selenium;

	@BeforeSuite(alwaysRun = true)
	@Parameters({ "browser", "URL" })
	public void instantiateWebDriver(String browser, String url) throws Exception {

		try {
			if (browser.equalsIgnoreCase("Firefox")) {
				FirefoxProfile profile = new FirefoxProfile(); // new File("C:/WebProfile"));
				driver = new FirefoxDriver(profile);
			} else if (browser.equalsIgnoreCase("IE")) {
				driver = new InternetExplorerDriver();
			} else if (browser.equalsIgnoreCase("*chrome"))
				driver = new ChromeDriver();

			selenium = new WebDriverBackedSelenium(driver, url);
			driver.get(url);
			driver.manage().window().maximize();

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	@AfterSuite(alwaysRun = true)
	public void stopWebDriver() {
		driver.manage().deleteAllCookies();
		driver.close();
	}
}
