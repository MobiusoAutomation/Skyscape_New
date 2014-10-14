package skyscape;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ProductDetail extends Base{
  
  String Product1 = "5-Minute Clinical Consult";
  String Product1_title = "5-Minute Clinical Consult | Skyscape";
  String ProductAtDetail = "5-Minute Clinical Consult";
  String ExpSelectedoption = "-- Choose a device/smartphone --";
  String selectedOption;
  String addtocartpage = "SHOPPING CART > BILLING > CONFIRM";
  
  
  @Test(priority=0)
  public void Launch_Product_Detail() throws InterruptedException {  
	 
	  w.findElement(By.linkText(Product1)).sendKeys("\n");
	  Thread.sleep(400);
	  System.out.println(w.getTitle());
	  Assert.assertEquals(w.getTitle(), Product1_title);
	  Thread.sleep(500);
	  Assert.assertTrue(w.getPageSource().contains(ProductAtDetail));
	  }
  
  
  @Test(priority=1)
  public void Selecting_Platform() throws InterruptedException {
	  
	  w.findElement(By.linkText(Product1)).sendKeys("\n");	
	  Thread.sleep(400);
	  Assert.assertTrue(w.getPageSource().contains(ProductAtDetail));
	  Select s1 = new Select(w.findElement(By.name("AvailablePlatforms")));
	  s1.selectByVisibleText(ExpSelectedoption);
	  Thread.sleep(400);
	  selectedOption = new Select(w.findElement(By.name("AvailablePlatforms"))).getFirstSelectedOption().getText();
	  Assert.assertEquals(ExpSelectedoption, selectedOption);
	  Thread.sleep(400);
	  w.findElement(By.id("AddToCart")).sendKeys("\n");
	  Thread.sleep(3000);
	  boolean testexp1 =  w.getPageSource().contains("Please choose a device/smartphone.");
	  Assert.assertTrue(testexp1);
	  System.out.println("dropdwon_platform-Pass");
	  }
  
  @Test(priority=2)
  public void Add_Product_In_The_Cart() throws InterruptedException {
	  
	  w.findElement(By.linkText(Product1)).sendKeys("\n");
	  Thread.sleep(200);
	  Assert.assertTrue(w.getPageSource().contains(ProductAtDetail));
	  String SelectedOption1 = new Select(w.findElement(By.name("AvailablePlatforms"))).getFirstSelectedOption().getText();
	  System.out.println(SelectedOption1);
	  if(!SelectedOption1.equalsIgnoreCase("Please choose a device/smartphone.")){
	  w.findElement(By.id("AddToCart")).sendKeys("\n");
	  Thread.sleep(500);
	  String addto = w.findElement(By.xpath("//section[@id='section-cart']/div/div/div/h2")).getText();
	  Assert.assertEquals(addto, addtocartpage);
	  Assert.assertTrue(w.getPageSource().contains(Product1));
	  }
	  }
  
  @BeforeMethod
  public void All_Products_Page_Launch() throws InterruptedException
  {
	  w.manage().deleteAllCookies();  
	  Thread.sleep(200);
	  w.get("https://www.skyscape.com/index/home.aspx");
	  w.findElement(By.linkText("PRODUCTS")).sendKeys("\n");
	  Thread.sleep(200);
//	  w.findElement(By.linkText("Allied Health/Other HCPs")).sendKeys("\n");
//	  Thread.sleep(200);
	  w.getPageSource().contains(Product1); 
  }
  
  @AfterMethod
  public void All_Products_Page_complete() throws InterruptedException
  {
	  
	  System.out.println("tested");
	    
  }
  
}

