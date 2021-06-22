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
public class SLVCSearchResultPgObj {

	@FindBy(xpath = "//form[@id='toolbar']/div[@class='resultscontainer']")
	private WebElement results_list;

	@FindBy(xpath = "//form[@id='toolbar']/div[@class='no_results']")
	private WebElement no_results_list;

	@FindBy(xpath = "//img[@class='bookbutton']")
	private WebElement select_button;

	@FindBy(xpath = "//img[@src='/libimages/icon_information.png']")
	private WebElement intuitive_symbol;

	@FindBy(id = "priceRange")
	private WebElement rate_Slider;

	@FindBy(id = "starRating")
	private WebElement minRating;

	@FindBy(id = "accomStyle")
	private WebElement accomStyle;

	@FindBy(id = "roomType")
	private WebElement roomType;

	@FindBy(id = "amenitiesContainer")
	private WebElement amenities_container;

	@FindBy(xpath = "//div[@id='refineSubmit']/input")
	private WebElement refine_button;

	@FindBy(id = "ORDER_PREFERRED_SUPPLIER")
	private WebElement Recommended_sortBy;

	@FindBy(id = "ORDER_INDICATIVE_RATE")
	private WebElement TotalPrice_sortBy;

	@FindBy(id = "ORDER_STAR_RATING")
	private WebElement StarRate_sortBy;

	@FindBy(id = "ORDER_SUPPLIER_NAME")
	private WebElement HotelNmae_sortBy;

	@FindBy(id = "ORDER_AVAILABILITY")
	private WebElement Availability_sortBy;

	@FindBy(id = "currencySelect")
	private WebElement currncy_conv;

	@FindBy(xpath = "//a[@class='supplierName']")
	private WebElement SupplierName_link;

	@FindBy(xpath = "//table[@class='stripresult']")
	private WebElement search_result_table;

	@FindBy(xpath = "//a[@class='supplierName']")
	private WebElement hotel_link;

	@FindBy(id = "map-link")
	private WebElement show_map_link;

	public WebElement getNext_page() {
		// TODO Auto-generated method stub
		return null;
	}

}
