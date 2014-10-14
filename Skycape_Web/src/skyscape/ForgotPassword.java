package skyscape;

import java.io.File;
import java.io.IOException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ForgotPassword extends Base {

	String xlFilePath = "Excels/ForgotPassword.xls";
	String ForgotTitle = "Password Reminder";
	
	@DataProvider(name = "dp1", parallel = false)
	public Object[][] data() throws Exception {
		Object[][] retObjArr=getTableArray(xlFilePath,"TestDataSheet", "Start", "End");
        return(retObjArr);
	}
	
	public String[][] getTableArray(String xlFilePath, String sheetName, String tableStartName, String tableEndName) throws Exception{
        String[][] tabArray=null;
        
            Workbook workbook = Workbook.getWorkbook(new File(xlFilePath));
            Sheet sheet = workbook.getSheet(sheetName); 
            int startRow,startCol, endRow, endCol,ci,cj;
            Cell tableStart=sheet.findCell(tableStartName);
            startRow=tableStart.getRow();
            startCol=tableStart.getColumn();

            Cell tableEnd= sheet.findCell(tableEndName, startCol+1,startRow+1, 100, 100,  false);                

            endRow=tableEnd.getRow();
            endCol=tableEnd.getColumn();
            tabArray=new String[endRow-startRow-1][endCol-startCol-1];
            ci=0;

            for (int i=startRow+1;i<endRow;i++,ci++){
                cj=0;
                for (int j=startCol+1;j<endCol;j++,cj++){
                    tabArray[ci][cj]=sheet.getCell(j,i).getContents();
                }
            }
        

        return(tabArray);
    }

	@Test(dataProvider = "dp1")
	public void ForgotTest(String Emailid, String Expected) throws Exception,
			IOException {

		w.get("https://www.skyscape.com/register/login.aspx?ReturnUrl=%2fsecure%2fmyaccount.aspx");
		Thread.sleep(1000);
		w.findElement(By.id("ForgotPwdLinkText")).sendKeys("\n");
		Thread.sleep(1000);
		Assert.assertEquals(w.getTitle(), ForgotTitle);
		w.findElement(By.name("txtUserEmail")).clear();
		w.findElement(By.name("txtUserEmail")).sendKeys(Emailid);
		System.out.println(Emailid);
		w.findElement(By.id("GetPwdLinkText")).sendKeys("\n");
		Thread.sleep(5000);
		System.out.println(Emailid);
		if (w.getTitle().contains(ForgotTitle)) {
			Assert.assertTrue(true);
			boolean aa = w.getPageSource().contains(Expected);		
			Assert.assertTrue(aa);
			System.out.println(Emailid + " Pass");
		}else{
				System.out.println(Emailid + " Fail");
				
			}
			

		}
	
}