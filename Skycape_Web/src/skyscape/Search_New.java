package skyscape;

import java.io.File;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

public class Search_New extends Base {
	
	String id = "testmobiuso33@gmail.com";
	String xlFilePathlogin = "Excels/Search.xls";
	String account = "My Account | Skyscape";
	String search_title = "ALL SKYSCAPE MEDICAL REFERENCES";
	
	
  @DataProvider(name = "SearchData", parallel = false)
	public Object[][] data() throws Exception {
		Object[][] retObjArrlogin = getTableArray(xlFilePathlogin,
				"TestDataSheet", "Start", "End");
		return (retObjArrlogin);
	}

  
  public String[][] getTableArray(String xlFilePathlogin, String sheetName,
			String tableStartName, String tableEndName) throws Exception {
		String[][] tabArraylogin = null;

		Workbook workbook = Workbook.getWorkbook(new File(xlFilePathlogin));
		Sheet sheet = workbook.getSheet(sheetName);

		int startRow, startCol, endRow, endCol, ci, cj;
		Cell tableStart = sheet.findCell(tableStartName);
		startRow = tableStart.getRow();
		startCol = tableStart.getColumn();

		Cell tableEnd = sheet.findCell(tableEndName, startCol + 1,
				startRow + 1, 100, 100, false);

		endRow = tableEnd.getRow();
		endCol = tableEnd.getColumn();
		tabArraylogin = new String[endRow - startRow - 1][endCol - startCol - 1];
		ci = 0;

		for (int i = startRow + 1; i < endRow; i++, ci++) {
			cj = 0;
			for (int j = startCol + 1; j < endCol; j++, cj++) {
				tabArraylogin[ci][cj] = sheet.getCell(j, i).getContents();
			}
		}

		return (tabArraylogin);
	}
  
   @Test(dataProvider = "SearchData", priority=0)
  public void Search_with_Keyword(String Search_Keyword, String PageTitle, String Count, String Expected) throws InterruptedException {
	  w.findElement(By.name("q")).clear();
	  w.findElement(By.name("q")).sendKeys(Search_Keyword);
	  Thread.sleep(2000);
	  w.findElement(By.id("search-button")).click();
	  Assert.assertEquals(search_title, w.findElement(By.xpath("//section[@id='section-title']/div/div/div/h2")).getText());
	  System.out.println(w.findElement(By.xpath("//section[@id='section-title']/div/div/div/h2")).getText());
	  Assert.assertEquals(Count, w.findElement(By.xpath("//section[@id='section-title']/div/div/div/div[3]/div[2]/span")).getText());
	  System.out.println(w.findElement(By.xpath("//section[@id='section-title']/div/div/div/div[3]/div[2]/span")).getText());
	  Thread.sleep(2000);
	  boolean elementpresent = w.findElements(By.partialLinkText(Expected)).size()!=0;
	  System.out.println(elementpresent);
	  Assert.assertTrue(elementpresent);
    }
  
    
//  @Test(dataProvider = "SearchData", priority=1)
//  public void search_without_login(String Search_Keyword, String PageTitle, String Count, String Expected) throws InterruptedException {
//	  w.findElement(By.linkText("MY ACCOUNT")).sendKeys("\n");
//	  if (w.getTitle().contains(account)){
//		  w.findElement(By.linkText("Sign Out")).sendKeys("\n");
//		   }
//	  Thread.sleep(500);
//	  w.findElement(By.name("q")).clear();
//	  w.findElement(By.name("q")).sendKeys(Search_Keyword);
//	  Thread.sleep(2000);
//	  w.findElement(By.id("search-button")).click();
//	  Assert.assertEquals(search_title, w.findElement(By.xpath("//section[@id='section-title']/div/div/div/h2")).getText());
//	  System.out.println(w.findElement(By.xpath("//section[@id='section-title']/div/div/div/h2")).getText());
//	  Assert.assertEquals(Count, w.findElement(By.xpath("//section[@id='section-title']/div/div/div/div[3]/div[2]/span")).getText());
//	  System.out.println(w.findElement(By.xpath("//section[@id='section-title']/div/div/div/div[3]/div[2]/span")).getText());
//	  Assert.assertTrue(w.getPageSource().equals(Expected));
//  }
  
  
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
