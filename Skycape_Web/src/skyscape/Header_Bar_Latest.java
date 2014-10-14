package skyscape;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Header_Bar_Latest extends Base {
	
	String xlFilePathlogin = "Excels/HeaderBar.xls";
	
	String id = "testmobiuso33@gmail.com";
	String password = "tester123";
	String LTitle ="My Account | Skyscape";
	
	@DataProvider(name = "Header bar", parallel = false)
	public Object[][] data() throws Exception {
		Object[][] retObjArrlogin =getTableArray(xlFilePathlogin,"TestDataSheet", "Start", "End");
        return(retObjArrlogin);
	}
	
	
	public String[][] getTableArray(String xlFilePathlogin, String sheetName, String tableStartName, String tableEndName) throws Exception{
        String[][] tabArraylogin=null;
        
            Workbook workbook = Workbook.getWorkbook(new File(xlFilePathlogin));
            Sheet sheet = workbook.getSheet(sheetName); 
            int startRow,startCol, endRow, endCol,ci,cj;
            Cell tableStart=sheet.findCell(tableStartName);
            startRow=tableStart.getRow();
            startCol=tableStart.getColumn();

            Cell tableEnd= sheet.findCell(tableEndName, startCol+1,startRow+1, 100, 100,  false);                

            endRow=tableEnd.getRow();
            endCol=tableEnd.getColumn();
            tabArraylogin=new String[endRow-startRow-1][endCol-startCol-1];
            ci=0;

            for (int i=startRow+1;i<endRow;i++,ci++){
                cj=0;
                for (int j=startCol+1;j<endCol;j++,cj++){
                	tabArraylogin[ci][cj]=sheet.getCell(j,i).getContents();
                }
            }
        

        return(tabArraylogin);
    }
	
	@Test(dataProvider = "Header bar")
  public void Heder_Bar_link_Launch(String HeaderTitleText, String Expected_Links, String Expected_Title) throws InterruptedException {
		
		w.get("https://www.skyscape.com/index/home.aspx");
		if(!Expected_Links.equalsIgnoreCase("https://www.skyscape.com/index/home.aspx")){
		w.findElement(By.partialLinkText(HeaderTitleText)).sendKeys("\n");
		
		String actualhome = w.getCurrentUrl();
		Assert.assertEquals(actualhome, Expected_Links);
		String HTitle = w.getTitle();
		System.out.println(HTitle);
		Assert.assertEquals(HTitle, Expected_Title);
		}
		if(w.getTitle().contains("Sign In | Skyscape")){
			w.findElement(By.name("EmailAddress")).sendKeys(id);
			w.findElement(By.name("Password")).sendKeys(password);
			w.findElement(By.id("LoginButton")).sendKeys("\n");
		  	w.getPageSource().contains(id);
			String MyTitle = w.getTitle();
			System.out.println(MyTitle);
			Assert.assertEquals(MyTitle, LTitle);
			w.findElement(By.linkText("MY ACCOUNT")).sendKeys("\n");
			  Thread.sleep(500);
			  w.findElement(By.linkText("Sign Out")).sendKeys("\n");
		}
				
		
  }
}
