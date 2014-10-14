package skyscape;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

public class Search extends Base {
 
  String id = "testmobiuso33@gmail.com";
  String search_title = "Search Results";
  String product = "5-Minute Pediatric Consult";
  String keyword1 = "davis";	
  String keyword2 = "labs 360";
  String keyword3 = "fever";
  String account = "My Account | Skyscape";

  @Test(priority=0)
  public void Search_with_Davis() {
	  w.findElement(By.name("q")).sendKeys(keyword1);
	  w.findElement(By.id("search-button")).sendKeys("\n");
	  w.getTitle().contains(search_title);
	  w.getPageSource().contains("Search Results");
//	  List<WebElement> w1 = w.findElements(By.cssSelector("#ProductArea > table > tbody > tr"));
//	  int search1_count = w1.size();
	  w.getPageSource().contains("keyword1");
//	  Assert.assertEquals(search1_count, 50);
  }
  
  @Test(priority=0)
  public void Search_with_Fever() throws InterruptedException {
	  w.findElement(By.name("q")).clear();
	  w.findElement(By.name("q")).sendKeys(keyword3);
	  w.findElement(By.id("search-button")).sendKeys("\n");
	  Thread.sleep(2000);
	  w.getTitle().contains(search_title);
	  w.getPageSource().contains("Search Results");
//	  List<WebElement> w2 = w.findElements(By.cssSelector("#ProductArea > table > tbody > tr"));
//	  int search2_count = w2.size();
	  Thread.sleep(2000);
	  w.getPageSource().contains("keyword3");
//	  Assert.assertEquals(search2_count, 5);
  }
    
  
  
//  @Test(priority=0)
//  public void Search_no_results_found() throws InterruptedException {
//	  //w.findElement(By.name("q")).sendKeys(keyword3);
//	  w.findElement(By.id("search-button")).sendKeys("\n");
//	  Thread.sleep(2000);
//	  w.getTitle().contains(search_title);
//	  w.getPageSource().contains("Your search for [ ] returned no products. Please try a different search term.");
//	  }
  
  @Test(priority=1)
  public void search_without_login() throws InterruptedException {
	  w.findElement(By.linkText("MY ACCOUNT")).sendKeys("\n");
	  if (w.getTitle().contains(account)){
		  w.findElement(By.linkText("Sign Out")).sendKeys("\n");
		   }
	  w.findElement(By.name("q")).clear();
	  Thread.sleep(500);
	  w.findElement(By.name("q")).sendKeys(keyword1);
	  w.findElement(By.id("search-button")).sendKeys("\n");
	  Thread.sleep(2000);
	  w.getTitle().contains(search_title);
	  w.getPageSource().contains("Search Results");
//	  List<WebElement> w3 = w.findElements(By.cssSelector("#ProductArea > table > tbody > tr"));
//	  int search3_count = w3.size();
	  w.getPageSource().contains("keyword1");
//	  Assert.assertEquals(search3_count, 50);
  }
  
  
  @BeforeClass
  public void Login_for_Search() {
	  w.get("https://www.skyscape.com");
	  
	  w.findElement(By.linkText("MY ACCOUNT")).sendKeys("\n");
	  w.findElement(By.name("EmailAddress")).clear();
		w.findElement(By.name("EmailAddress")).sendKeys(id);
		w.findElement(By.name("Password")).clear();
		w.findElement(By.name("Password")).sendKeys("tester123");
		w.findElement(By.id("LoginButton")).sendKeys(Keys.ENTER);
	  	w.getPageSource().contains(id);
  }

  @AfterClass
  public void Logout_After_Search() {
	  w.findElement(By.linkText("MY ACCOUNT")).sendKeys("\n");
  	  if (w.getTitle().contains(account)) {
			w.getPageSource().contains("Sign Out");
		System.out.println("search executed successfully");
  }
  }
  }
