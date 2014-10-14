package skyscape;

import java.io.File;
import java.io.IOException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Login extends Base {

	String xlFilePathlogin = "Excels/login.xls";
	String login = "Sign In | Skyscape";
	String My_Account_Title = "My Account | Skyscape";

	@DataProvider(name = "logindata", parallel = false)
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

	@Test(dataProvider = "logindata")
	public void Login_All_scenarios(String Category, String Emailid,
			String Password) throws BiffException, IOException,
			InterruptedException, RowsExceededException, WriteException {

		w.get("https://www.skyscape.com/register/login.aspx?ReturnUrl=%2fsecure%2fmyaccount.aspx");
		w.findElement(By.name("EmailAddress")).clear();
		w.findElement(By.name("EmailAddress")).sendKeys(Emailid);
		Thread.sleep(400);
		w.findElement(By.name("Password")).clear();
		w.findElement(By.name("Password")).sendKeys(Password);
		w.findElement(By.id("LoginButton")).sendKeys("\n");

		if (w.getPageSource().contains("Sign Out")){
			System.out.println(Emailid + " logged in");
			Assert.assertEquals(My_Account_Title, w.getTitle());
			Assert.assertTrue(true);
			w.getPageSource().contains(Emailid);
			Thread.sleep(400);
			w.findElement(By.linkText("Sign Out")).sendKeys("\n");
			w.getPageSource().contains("LOGIN TO SKYSCAPE");

		} else {
			Assert.assertEquals(login, w.getTitle());
			System.out.println(Emailid + "Fail");
			Assert.assertTrue(false);
		}

	}
}
