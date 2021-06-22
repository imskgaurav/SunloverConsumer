/**
 * 
 */
package sunLoverModule;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;

import automationFramework.Driver;
import pageObjects.SLVCBroucherPgObj;
import pageObjects.SLVCSearchResultPgObj;

/**
 * @author tetambes
 *
 */
public class SLConsSearchResultPageAction {

	WebDriverWait wait = new WebDriverWait(Driver.driver, 3000);
	SLVCBroucherPgObj BrouchPgobj = PageFactory.initElements(Driver.driver, SLVCBroucherPgObj.class);
	JavascriptExecutor js = ((JavascriptExecutor) Driver.driver);
	SLConHomePageAction HomePgAct = PageFactory.initElements(Driver.driver,SLConHomePageAction.class);
	SLVCSearchResultPgObj searchPgObj = PageFactory.initElements(Driver.driver, SLVCSearchResultPgObj.class);
	
	/*
	 * @FindBy(xpath=
	 * "//tr[contains(@id, '@3')]//img[@src='/libimages/icon_information.png']")
	 * WebElement icon_info;
	 * 
	 * @FindBy(xpath="//img[@src='/libimages/icon_information.png']") 
	 * WebElement
	 * pegs_icon;
	 */
	
	
	public boolean noResult_Check()
	{
		boolean present=false;
		try{
			Driver.driver.findElement(By.xpath("//div[@class='no_results']")); //|| (Driver.driver.findElement(By.xpath("//span[@class='trailerTotal']")).getText().equals("0")));
			present=true;
		}
		catch(Exception e)
		{
			present=false;
		}
		return present;
	}
	
	
	public boolean noResultCheck_onRefining()
	{
		boolean present=false;
		try{
			System.out.println("trailerTotal displayed: "+Driver.driver.findElement(By.xpath("//span[@class='trailerTotal']")).getText());
			
			if(Driver.driver.findElement(By.xpath("//span[@class='trailerTotal']")).getText().equalsIgnoreCase("0")){
				present=true;
			}
			else{
				present=false;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return present;
	}
	
	public String searchResult_Check()
	{
		//String result=null;
		int count = 0;
		List<WebElement> result_List=null;
		try{
					List<WebElement> stripResult_table = Driver.driver.findElements(By.xpath("//table[@class='stripresult']/*"));
					wait.until(ExpectedConditions.visibilityOfAllElements(stripResult_table));
					//System.out.println("No. of Strip result table: "+ stripResult_table.size());
					
					List<WebElement> search_page=Driver.driver.findElements(By.xpath("//span[@class='pages']/*"));
					
					if(search_page.size()==0)
					{
						result_List = Driver.driver.findElements(By.xpath("//a[@class='supplierName']/*"));
						count=result_List.size();
					}
					else
					{
						int search_pageCount=search_page.size()/2;
						
						System.out.println("Page count : "+ search_pageCount);
						
						for(int i=0; i<search_pageCount;i++)
						{
							result_List = Driver.driver.findElements(By.xpath("//a[@class='supplierName']/*"));
							
							count += result_List.size();
							
							if(i < search_pageCount-1)
								searchPgObj.getNext_page().click();
							
						}
				
			}
			
		}	
		catch(Exception e)
		{
			e.printStackTrace();
			AssertJUnit.fail(e.getMessage());
		}
		
		return String.valueOf(count);
		
	}


	public boolean select_Last_Minute_Property() throws Exception {

		try {
			List<WebElement> stripResult_table = Driver.driver.findElements(By.xpath("//tr[contains(@id, '@3')]/*")); // To search NIN product from search list
			wait.until(ExpectedConditions.visibilityOfAllElements(stripResult_table));
			System.out.println("No. of Strips: " + stripResult_table.size());
			for (int i = 0; i <= stripResult_table.size();) {
				if (stripResult_table.get(i).findElement(By.xpath("//img[@src='/libimages/icon_information.png']")).isDisplayed()) {
					System.out.println("NIN Property exist in search result list..");
					List<WebElement> Select_button = Driver.driver.findElements(By.xpath("//tr[contains(@id, '@3')]/td[3]//img[@src='/libimages/book_instant.png']"));
					Select_button.get(i).click();
					return true;
				} 
				else 
				{
					System.out.println("NIN property doesnt exist in result list..");
					HomePgAct.selectLocation("Adelaide City and Surrounds (SA)");
					HomePgAct.Go_button.click();
					List<WebElement> select_instance = Driver.driver.findElements(By.xpath("//td[@class='servicehead buttoncell']//img[@src='/libimages/icon_instant.png']"));
					select_instance.get(i).click();
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			AssertJUnit.fail(e.getMessage());
		}
		return false;
	}

	public boolean select_Pegaseus_Property() throws Exception {

		try {
			List<WebElement> stripResult_table = Driver.driver.findElements(By.xpath("//tr[contains(@id, 'PXT')]/*")); // To search Pegaseus product from search list
			wait.until(ExpectedConditions.visibilityOfAllElements(stripResult_table));
			System.out.println("No. of Strips: " + stripResult_table.size());
			for (int i = 0; i <= stripResult_table.size();) {
				if (stripResult_table.get(i).findElement(By.xpath("//img[@src='/libimages/icon_information.png']")).isDisplayed()) {
					System.out.println("Pegaseus Property exist in search result list..");
					List<WebElement> Select_button = Driver.driver
							.findElements(By.xpath(
									"//tr[contains(@id, 'PXT')]/td[3]//img[@src='/libimages/book_instant.png']"));
					Select_button.get(i).click();
					return true;
				} else {
					System.out.println("Pegaseus property doesnt exist in result list..");
					HomePgAct.selectLocation("Adelaide City and Surrounds (SA)");
					HomePgAct.Go_button.click();
					List<WebElement> select_instance = Driver.driver.findElements(By.xpath(
									"//tr[contains(@id, 'PXT')]/td[3]//img[@src='/libimages/book_instant.png']"));
					select_instance.get(i).click();
					return true;
				}
			}
		} catch (Exception e) {
			AssertJUnit.fail(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

}
