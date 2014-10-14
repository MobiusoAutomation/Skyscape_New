package skyscape;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

@SuppressWarnings("unused")
public class Shopping_cart extends Base {
	
	
	String id = "testmobiuso30@gmail.com";
	String product = "5-Minute Pediatric Consult";
	String productcost = "$99.95";
	String costafterupdate = "$199.90";
	String quantity = "2";
	String ExpSelectedoptioninitial = "Android 2.2 or higher"; 
	String ExpSelectedoptionfinal = "iOS 6.0 or higher";
	String addtocartpage = "SHOPPING CART > BILLING > CONFIRM";
	String afterremove = "Your shopping cart is empty, please proceed to the store to continue.";	
	String checkoutmsg = "BILLING INFORMATION";
	
@Test(priority = 0)
  public void Checkout() throws InterruptedException {

	  Thread.sleep(2000);
	  String addto = w.findElement(By.xpath("//section[@id='section-cart']/div/div/div/h2")).getText();
	  Assert.assertEquals(addto, addtocartpage);
	  w.findElement(By.id("CheckoutLink")).sendKeys("\n");
	  Thread.sleep(200);
	  String addto1 = w.findElement(By.xpath("//section[@id='section-cart']/div/div/div/h2")).getText();
	  Assert.assertEquals(addto1, checkoutmsg);
	  String id_1 = w.findElement(By.name("BillEmailAddress")).getAttribute("value");
	  Assert.assertEquals(id_1, id);
	  System.out.println("checkout-Pass");
	  w.navigate().back();
	  Thread.sleep(200);
	  String addto2 = w.findElement(By.xpath("//section[@id='section-cart']/div/div/div/h2")).getText();
	  Assert.assertEquals(addto2, addtocartpage);
	  
	  }
  
 
  @Test (priority = 3)
  public void Remove_resource_from_cart() throws InterruptedException {
	  w.findElement(By.cssSelector("#primary > li:nth-child(7) > a")).sendKeys("\n");
	  Thread.sleep(800);
	  w.findElement(By.cssSelector("#CartItems__ctl0_RemoveItem")).sendKeys("\n");
	  Thread.sleep(800);
	  Assert.assertNotEquals(w.getPageSource().contains("5-Minute Pediatric Consult"), product);
	  Assert.assertEquals(w.findElement(By.xpath("//section[@id='section-cart']/div/div/div/h4")).getText(), afterremove);
	  System.out.println("Remove_resource_from_cart_pass"); 
  }
  
 @Test(priority = 1)
  public void Change_Platform() throws InterruptedException {
	  
	 w.findElement(By.cssSelector("#primary > li:nth-child(7) > a")).sendKeys("\n");
	 System.out.println("Test1");
	  Thread.sleep(500);
	  w.findElement(By.cssSelector("#CartSection > table.mycart.pad_left > tbody > tr.mycartitem > td.item-info > span.removebutton > a.APEdocument.APEinternal.box_link")).sendKeys("\n");
	  Thread.sleep(800);
	  System.out.println("Test2");
	  WebElement frameID = w.findElement(By.cssSelector("#cboxLoadedContent > iframe"));
	  w.switchTo().frame(frameID);
	  Thread.sleep(300);
	  Select d1 = new Select(w.findElement(By.name("AvailablePlatforms")));
	  d1.selectByVisibleText(ExpSelectedoptionfinal);
	  Thread.sleep(800);
	  w.findElement(By.id("Change")).sendKeys("\n");
	  System.out.println("Test3");
	  w.switchTo().defaultContent();
	  Thread.sleep(800);
	  w.getPageSource().contains(product);
	  Thread.sleep(500);
	  w.findElement(By.cssSelector("#CartSection > table.mycart.pad_left > tbody > tr.mycartitem > td.item-info > span.removebutton > a.APEdocument.APEinternal.box_link")).sendKeys("\n");
	  WebElement frameID1 = w.findElement(By.cssSelector("#cboxLoadedContent > iframe"));
	  w.switchTo().frame(frameID1);
	  Thread.sleep(300);
	  System.out.println("Test4");
	  String SelectedOption3 = new Select(w.findElement(By.name("AvailablePlatforms"))).getFirstSelectedOption().getText();
	  Assert.assertEquals(SelectedOption3, ExpSelectedoptionfinal);
	  w.findElement(By.id("Change")).sendKeys("\n");
	  w.switchTo().defaultContent();
	  Thread.sleep(300);
	  System.out.println("Change_Platform_Testcase Passed");
	  }
   
 @Test(priority = 2)
 public void Update_Cart () throws InterruptedException {
	
	 w.findElement(By.cssSelector("#primary > li:nth-child(7) > a")).sendKeys("\n");

	  Thread.sleep(800);
	  
	 // String beforeupdatecost = w.findElement(By.cssSelector("#CartSection > table.mycart.pad_left > tbody > tr:nth-child(4) > td:nth-child(3)")).getText();
	  //Assert.assertEquals(beforeupdatecost, productcost);
	  w.findElement(By.name("CartItems:_ctl0:Quantity")).clear();
	  w.findElement(By.name("CartItems:_ctl0:Quantity")).sendKeys(quantity);
	  w.findElement(By.id("UpdateCart")).sendKeys("\n");
	  String afterupdatecost = w.findElement(By.cssSelector("#CartSection > table.mycart.pad_left > tbody > tr:nth-child(4) > td:nth-child(3)")).getText();
	  Assert.assertEquals(costafterupdate, afterupdatecost);
	 System.out.println("Update_Cart_pass"); 
 }
 
  
  @BeforeTest
  public void Login_for_Shopping() throws InterruptedException {
    
	  w.get("https://www.skyscape.com");
	 // w.manage().deleteAllCookies();
	  Thread.sleep(5000);
	  w.findElement(By.linkText("MY ACCOUNT")).sendKeys("\n");
	  w.findElement(By.name("EmailAddress")).clear();
		w.findElement(By.name("EmailAddress")).sendKeys(id);
		w.findElement(By.name("Password")).clear();
		w.findElement(By.name("Password")).sendKeys("tester123");
		w.findElement(By.id("LoginButton")).sendKeys(Keys.ENTER);
	  	w.getPageSource().contains(id);
	  	Thread.sleep(500);
	  	w.manage().deleteAllCookies();
	  	Thread.sleep(2000);
	  	 w.findElement(By.linkText("MY ACCOUNT")).sendKeys("\n");
	  	Thread.sleep(200);
	  	 if(w.getTitle().contains("Sign In | Skyscape")){
		 w.findElement(By.name("EmailAddress")).sendKeys(id);
			w.findElement(By.name("Password")).sendKeys("tester123");
			w.findElement(By.id("LoginButton")).sendKeys(Keys.ENTER);
		  	w.getPageSource().contains(id);
	  	 }
	  	Thread.sleep(500);
	  	w.findElement(By.linkText("PRODUCTS")).sendKeys("\n");
	  	Thread.sleep(500);
//		w.findElement(By.linkText("Physicians")).sendKeys("\n");
//		Thread.sleep(500);
		w.findElement(By.linkText(product)).sendKeys("\n"); 
		Thread.sleep(500);
		Select d0 = new Select(w.findElement(By.name("AvailablePlatforms")));
		  d0.selectByVisibleText(ExpSelectedoptioninitial);
		 w.findElement(By.id("AddToCart")).sendKeys("\n");
		  Thread.sleep(2000);
		  Assert.assertTrue(w.getPageSource().contains(product));
  }

  @AfterTest
  public void Logout_after_shopping() throws InterruptedException {
	  w.findElement(By.linkText("MY ACCOUNT")).sendKeys("\n");
	  Thread.sleep(800);
	  w.findElement(By.linkText("Sign Out")).sendKeys("\n");
	 
  }
  
}
