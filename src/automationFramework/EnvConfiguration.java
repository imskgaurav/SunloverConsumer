package automationFramework;

public class EnvConfiguration {
	
	public static final String TST_URL = "http://sunloverconsumer.tst.travel-bookings.net/";
	
	public static final String BRD_URL = "http://sunloverconsumer.brd.travel-bookings.net/";
	
	public static final String TRN_URL = "http://sunloverconsumer.trn.travel-bookings.net/";
	 
	
	public static final String Current_Dir = System.getProperty("user.dir");
	
	public static final String relative_testDataPath = "\\src\\testData\\";
	
//    public static final String Path_TestData = "C:\\Automation Project\\iResConsumerSites\\src\\testData\\";				// This need to change as per system configuration
	
	public static final String Path_TestData =  Current_Dir.concat(relative_testDataPath);

    public static final String File_TestData = "Consumer SiteTest Data.xls";

}
