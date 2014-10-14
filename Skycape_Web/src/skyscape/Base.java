package skyscape;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

public class Base {
	
	 public static WebDriver w;
	
	
  @BeforeSuite
  public void setup() {
	  //System.setProperty("webdriver.chrome.driver", "C:/Users/Dattatray/Desktop/selenium/chromedriver.exe");
	// w = new ChromeDriver();
	 w = new FirefoxDriver();
	  
	  w.manage().timeouts().implicitlyWait(1, TimeUnit.MINUTES);
	  w.manage().window().maximize();
  }

  
  @AfterSuite
   public void tearDown() throws IOException{
	  
//	  TakesScreenshot scrShot =((TakesScreenshot)w);
//	  File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
//	  File DestFile=new File("StatusScreenshot.png");
//	  FileUtils.copyFile(SrcFile, DestFile);
	  
          try{
        	  w.close();
              }catch (Exception e){
              System.out.println("exception caught while closing driver"
+e);
          }finally{
        	  w.quit();
  }

}
}

