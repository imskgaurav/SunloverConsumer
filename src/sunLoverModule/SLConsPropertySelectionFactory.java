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
import org.testng.Assert;
import org.testng.AssertJUnit;

import com.thoughtworks.selenium.webdriven.commands.WaitForPageToLoad;

import automationFramework.Driver;
import pageObjects.SLVCBroucherPgObj;

/**
 * @author tetambes
 *
 */
public class SLConsPropertySelectionFactory {

	WebDriverWait wait = new WebDriverWait(Driver.driver, 3000);
	SLVCBroucherPgObj BrouchPgobj = PageFactory.initElements(Driver.driver,
			SLVCBroucherPgObj.class);
	JavascriptExecutor js = ((JavascriptExecutor) Driver.driver);
	SLConHomePageAction HomePgAct = PageFactory.initElements(Driver.driver,
			SLConHomePageAction.class);

	
	public void Select_Hotel_From_SearchResult(String SupplierName, String optionCode) {
		try {
			List<WebElement> stripResult_table = Driver.driver.findElements(By.xpath("//table[@class='stripresult']/*"));
			wait.until(ExpectedConditions.visibilityOfAllElements(stripResult_table));
			System.out.println("No. of Strip result table: " + stripResult_table.size());
			outer : for (int i = 1; i < stripResult_table.size(); i++) {
				List<WebElement> result_List = Driver.driver.findElements(By
						.xpath("//table[@class='stripresult']//td[@class='servicehead serviceName']/*"));
				System.out.println(
						"result list in table" + i + "is" + result_List.size());
				for (int j = 1; j < result_List.size(); j++) {
					String text = result_List.get(j).getText();
					if (text.contains(SupplierName)) {
						System.out.println("text matched: " + text);
						WebElement table_row = Driver.driver.findElement(By
								.xpath("//table[@class='stripresult']//tr[contains(@id, '"
										+ optionCode + "')]"));
						js.executeScript("arguments[0].scrollIntoView(true);",
								table_row);
						WebElement hotel_link = Driver.driver.findElement(
								By.xpath("//tr[contains(@id, '" + optionCode
										+ "')]/td[2]/div[1]/a/span"));
						System.out.println(hotel_link.getText());
						hotel_link.click();
						Thread.sleep(2000);
						new WaitForPageToLoad();
						Assert.assertTrue(
								BrouchPgobj.getSupplier_header().isDisplayed(),
								"Broucher page didnt appeared......");
						break outer;
					}
				}
			}
		} catch (Exception e) {
			e.getCause();
			e.printStackTrace();
			System.out.println("Supplier Not Found in search result");
			AssertJUnit.fail(e.getMessage());
		}
	}

	public void Select_Room_Available(String roomType) throws Exception {
		wait.until(ExpectedConditions
				.visibilityOf(BrouchPgobj.getSupplier_header()));
		if (BrouchPgobj.getRoomPanel().getText()
				.contains("There are no available rooms for your criteria.")) {
			try {
				System.out.println("No Search result for seleced criteria");
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("No Room available");
				e.getMessage();
			}
		} else {
			System.out.println(
					"Selected room type from xl sheet is : " + roomType);
			List<WebElement> allRoom = Driver.driver.findElements(
					By.xpath(".//*[@id='rooms']//span[@class='roomName']"));
			System.out.println("Room Count: " + allRoom.size());
			outer : for (WebElement roomName : allRoom) {
				if (roomName.getText().contentEquals(roomType)) {
					try {
						BrouchPgobj.getBookNow_button().click();
						System.out.println("Book Now button clicked..");
						break outer;
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println(
								"No Room Type available that we are looking for");
						e.printStackTrace();
					}
				} else {
					System.out.println(
							"This Room Type not matched with xl one..");
				}
			}
		}
	}

	// This method is used to select hotel from search by hotel name.
	/**
	 * @param hotelKeyword
	 * @return
	 */
	public String Choose_Hotel_Name(String hotelKeyword) {

		try {
			List<WebElement> all_Hotel_List = Driver.driver.findElements(By.xpath("//ul[@class='ui-autocomplete ui-menu ui-widget ui-widget-content ui-corner-all']/*"));
			wait.until(ExpectedConditions.visibilityOfAllElements(all_Hotel_List));
			outer : for (int i = 1; i < all_Hotel_List.size(); i++) {
				String text = all_Hotel_List.get(i).getText();
				if (text.contains(hotelKeyword)) {
					WebElement hotel_link = Driver.driver.findElement(By.xpath("//ul[@class='ui-autocomplete ui-menu ui-widget ui-widget-content ui-corner-all']/li["+ i + "]/a"));
					System.out.println(hotel_link.getText());
					Thread.sleep(2000);
					hotel_link.click();
					break outer;
				}
			}
		} catch (Exception e) {
			e.getCause();
			e.printStackTrace();
			System.out.println("Property Not Found");
		}
		return hotelKeyword;
	}

	//This method is use to click Select button for any On Request property...
	public boolean Click_SelectRQ_button() throws Exception {
		try {
			List<WebElement> stripResult_table = Driver.driver.findElements(By.xpath("//table[@class='stripresult']//tr/*"));
			wait.until(ExpectedConditions.visibilityOfAllElements(stripResult_table));
			System.out.println("No. of Strip result: " + stripResult_table.size());
			for (int i = 0; i < stripResult_table.size(); i++) {
				List<WebElement> select_RQbutton = Driver.driver.findElements(By.xpath("//td[@class='servicehead buttoncell']//img[@src='/libimages/book_RQ.png']"));
				for(int j=1; j <= select_RQbutton.size();)
				{
					if (!select_RQbutton.isEmpty()) {
						System.out.println("On Request property's in a list : "+ select_RQbutton.size());
						select_RQbutton.get(j).click();
						return true;
					} else {
						System.out.println("Ön Request Accomodation not available.. Please select another region");
						HomePgAct.selectLocation("Adelaide City and Surrounds (SA)");
						HomePgAct.Go_button.click();
						Thread.sleep(3000);
						select_RQbutton.get(j).click();
						return true;
					}
				}
			}
		} catch (AssertionError ae) {
			ae.printStackTrace();
			return false;
		}
		return true;
	}

	//This method is use to click first select instance button from search result.
	public boolean Click_SelectInstant_Button() throws Exception {
		try {
			List<WebElement> stripResult_table = Driver.driver.findElements(By.xpath("//table[@class='stripresult']//tr/*"));
			wait.until(ExpectedConditions.visibilityOfAllElements(stripResult_table));
			System.out.println("No. of Strip result: " + stripResult_table.size());
			for (int i = 1; i < stripResult_table.size();)
			{
				List<WebElement> select_InstantButton = Driver.driver.findElements(By.xpath("//img[@src='/libimages/book_instant.png']"));
				if (!select_InstantButton.isEmpty()) 
				{	
					System.out.println("Select Instant confirmation property no: "+ select_InstantButton.size());
					select_InstantButton.get(i).click();
					return true;
				} 
				else 
				{
					System.out.println("Accomodation not available.. Please select another region");
					HomePgAct.selectLocation("Adelaide City and Surrounds (SA)");
					HomePgAct.Go_button.click();
					List<WebElement> select_instance = Driver.driver.findElements(By.xpath("//td[@class='servicehead buttoncell']//img[@src='/libimages/icon_instant.png']"));
					select_instance.get(i).click();
					return false;
				}
			}
		} catch (AssertionError ae) {
			ae.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean Click_Book_Now_Button() throws Exception {
		try {
			wait.until(ExpectedConditions
					.invisibilityOfElementLocated(By.id("roomsPanelWait")));
			List<WebElement> allRoom = Driver.driver.findElements(
					By.xpath(".//*[@id='rooms']//span[@class='roomName']"));
			// wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".roomName")));
			if (allRoom.size() >= 1) {
				List<WebElement> roomUnit = Driver.driver.findElements(By.xpath(
						"//*[@id='rooms']//div[@class='roomActions']/*"));
				// Relative path for first Book button:
				// //*[@id='rooms']/div[2]//div[@class='roomBookButton']//img[@class='bookbutton']
				// wait.until(ExpectedConditions.visibilityOfAllElements(roomUnit));
				for (int i = 0; i <= roomUnit.size();) {
					List<WebElement> Book_Now = Driver.driver.findElements(
							By.xpath("//div[@class='roomBookButton']//img"));
					Book_Now.get(i).click();
					Thread.sleep(3000);
					System.out.println("Book Now button Clicked....");
					return true;
				}
			} else {
				System.out.println("Room Panel not loaded...");
				String errorTxt = Driver.driver.findElement(By.xpath(".//*[@id='roomsPanel']/div")).getText();
				Assert.assertEquals(errorTxt, "There are no available rooms for your criteria", "No available rooms for your criteria message appeared...");
				Driver.driver.findElement(By.xpath(".//*[@id='searchresultslink']/a")).click();
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//table[@class='stripresult']//tr/*")));
				List<WebElement> select_InstantButton = Driver.driver.findElements(By.xpath("//tr[contains(@id, 'LA')]//img[@src='/libimages/book_instant.png']"));
				for(WebElement button : select_InstantButton)
				{
					button.click();
					System.out.println("Some other property selected..");
				}
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return false;
	}

	public boolean Click_AddToItineraryRQ_button() throws Exception {
		try {
			wait.until(ExpectedConditions
					.invisibilityOfElementLocated(By.id("roomsPanelWait")));
			List<WebElement> allRoom = Driver.driver.findElements(
					By.xpath(".//*[@id='rooms']//span[@class='roomName']"));
			if (allRoom.size() >=1) 
			{
				List<WebElement> roomUnit = Driver.driver.findElements(By.xpath("//*[@id='rooms']//div[@class='roomActions']/*"));
				// wait.until(ExpectedConditions.visibilityOfAllElements(roomUnit));
				for (int i = 0; i <= roomUnit.size();) {
					List<WebElement> Add_To_Itinerary_RQ = Driver.driver.findElements(By.xpath("//img[@src='/libimages/add-to-itinerary_RQ.png']"));
					if(Add_To_Itinerary_RQ.get(i).isDisplayed())
					{
						Add_To_Itinerary_RQ.get(i).click();
						Thread.sleep(3000);
						System.out.println("AddToItinerary button clicked now");
						new WaitForPageToLoad();
						Assert.assertTrue(Driver.driver.getCurrentUrl().contains("itinerary"), "Itinerary page not loaded OR Add To Itineary button not working");
						break;
					}else{
						BrouchPgobj.getAddToItinerary_button().click();
					}
				
				}
			} else {
				System.out.println("Room Panel not loaded...");
				String errorTxt = Driver.driver.findElement(By.xpath(".//*[@id='roomsPanel']/div")).getText();
				Assert.assertEquals(errorTxt, "There are no available rooms for your criteria", "No available rooms for your criteria message appeared...");
				Driver.driver.findElement(By.xpath(".//*[@id='searchresultslink']/a")).click();
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//table[@class='stripresult']//tr/*")));
				List<WebElement> select_InstantButton = Driver.driver.findElements(By.xpath("//tr[contains(@id, 'LA')]//img[@src='/libimages/book_instant.png']"));
				for(WebElement button : select_InstantButton)
				{
					button.click();
					System.out.println("Some other property selected..");
				}
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return false;
	}
	
	public boolean Click_AddToItinerary_button() throws Exception {
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("roomsPanelWait")));
			List<WebElement> allRoom = Driver.driver.findElements(By.xpath(".//*[@id='rooms']//span[@class='roomName']"));
			if (allRoom.size() >=1) {
				List<WebElement> roomUnit = Driver.driver.findElements(By.xpath("//*[@id='rooms']//div[@class='roomActions']/*"));
				// wait.until(ExpectedConditions.visibilityOfAllElements(roomUnit));
				for (int i = 1; i <= roomUnit.size();) 
				{
					List<WebElement> Add_To_Itinerary = Driver.driver.findElements(By.xpath("//img[@src='/libimages/add-to-itinerary.png']"));
					wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//img[@src='/libimages/add-to-itinerary.png']")));
					Add_To_Itinerary.get(i).click();
					new WaitForPageToLoad();
					Assert.assertTrue(Driver.driver.getCurrentUrl().contains("itinerary"), "Itinerary page not loaded OR Add To Itineary button not working");
					break;
				}
			} else {
				System.out.println("Room Panel not loaded...");
				String errorTxt = Driver.driver.findElement(By.xpath(".//*[@id='roomsPanel']/div")).getText();
				Assert.assertEquals(errorTxt, "There are no available rooms for your criteria", "No available rooms for your criteria message appeared...");
				Driver.driver.findElement(By.xpath(".//*[@id='searchresultslink']/a")).click();
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//table[@class='stripresult']//tr/*")));
				List<WebElement> select_InstantButton = Driver.driver.findElements(By.xpath("//tr[contains(@id, 'LA')]//img[@src='/libimages/book_instant.png']"));
				for(WebElement button : select_InstantButton)
				{
					button.click();
					System.out.println("Some other property selected..");
				}
			}
		} catch (Exception e) {
			e.getMessage();
			e.getCause();
		}
		return false;
	}
	
// This method is to click AddToItinerary button from broucher page.
	public void Select_Room_Add_to_Itinerary(String roomType) throws Exception {

        wait.until(ExpectedConditions.visibilityOf(BrouchPgobj.getSupplier_header()));



        if (BrouchPgobj.getRoomPanel().getText().contains("There are no available rooms for your criteria.")) {

               try {

                     System.out.println("No Search result for seleced criteria");

               } catch (Exception e) {

                     // TODO: handle exception

                     System.out.println("No Room available");

                     e.getMessage();

               }

        } else {

               System.out.println("Selected room type from xl sheet is : " + roomType);

               List<WebElement> allRoom = Driver.driver

                            .findElements(By.xpath(".//*[@id='rooms']//span[@class='roomName']"));

               outer: for (WebElement roomName : allRoom) {

                     if (roomName.getText().contentEquals(roomType)) {

                            try {

                                   BrouchPgobj.getAddToItinerary_button().click();

                                   System.out.println("Add to Itinerary button clicked..");
                                   	new WaitForPageToLoad();
               						Assert.assertTrue(Driver.driver.getCurrentUrl().contains("itinerary"), "Itinerary page not loaded OR Add To Itineary button not working");

                                   break outer;

                            } catch (Exception e) {

                                   // TODO: handle exception

                                   System.out.println("No Room Type available that we are looking for");

                                   e.printStackTrace();

                            }

                     } else {

                            System.out.println("This Room Type does not match with xl one..");

                     }

               }

        }

 }



}
