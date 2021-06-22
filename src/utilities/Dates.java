package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class Dates {

	WebDriver driver;

	public Dates(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * @param month
	 * @param year
	 * @param driver
	 * @param element
	 * @throws Exception
	 */
	public void date_performed(String month, String year, WebDriver driver,
			WebElement element) throws Exception {
		try {
			WebElement date = element;
			System.out.println("Date data:" + date.getText());
			Select select_month = new Select(date
					.findElement(By.xpath("//select[@class='input-medium']")));
			select_month.selectByVisibleText(month);
			WebElement select_year = date.findElement(
					By.xpath("//input[contains(@id, 'year-value')]"));
			select_year.click();
			select_year.clear();
			select_year.sendKeys(year);
		} catch (Exception e) {
			e.getCause();
			e.printStackTrace();
		}
	}

	public WebElement select_month(String month, String duration_month) {
		try {
			WebElement input_year = driver.findElement(
					By.xpath("//span[@id='" + duration_month + "']/span"));
			String id_month = input_year.getAttribute("id")
					.concat("month-value");
			WebElement select_id_month = driver.findElement(By.id(id_month));
			Select select_month = new Select(select_id_month);
			select_month.selectByVisibleText(month);
		} catch (Exception ex) {
			ex.getCause();
			ex.printStackTrace();
		}
		return null;
	}

	public WebElement select_year(String year, String duration_year) {
		WebElement input_year = null;
		try {
			input_year = driver.findElement(
					By.xpath("//span[@id='" + duration_year + "']/span"));
			String id_year = input_year.getAttribute("id").concat("year-value");
			WebElement year_input = driver.findElement(By.id(id_year));
			year_input.click();
			year_input.clear();
			year_input.sendKeys(year);
		} catch (Exception ex) {
			ex.printStackTrace();
			ex.getCause();
		}
		return input_year;
	}

}
