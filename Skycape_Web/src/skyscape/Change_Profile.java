package skyscape;

import java.io.File;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Change_Profile extends Base {

	String xlFilePathlogin = "Excels/Change_Profile.xls";
	String MyProfile = "UPDATE YOUR ACCOUNT DETAILS";
	String My_Account_Title = "My Account | Skyscape";
	String loggedin = "MY ACCOUNT";

	@DataProvider(name = "Change_Profile", parallel = false)
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

	@Test(dataProvider = "Change_Profile", priority = 0)
	public void Change_Details(String Email_Id, String Password,
			String Updated_FirstName, String Updated_Country,
			String Updated_City, String Updated_Profession,
			String Updated_Specialty) throws InterruptedException {
		
		//w.manage().deleteAllCookies();
		w.get("https://www.skyscape.com");
		Thread.sleep(500);
		w.findElement(By.linkText("MY ACCOUNT")).sendKeys("\n");
		Thread.sleep(500);
		w.getPageSource().contains("LOGIN TO SKYSCAPE");
		Thread.sleep(1000);
		w.findElement(By.name("EmailAddress")).clear();
		Thread.sleep(200);
		w.findElement(By.name("EmailAddress")).sendKeys(Email_Id);
		Thread.sleep(200);
		w.findElement(By.name("Password")).clear();
		Thread.sleep(200);
		w.findElement(By.name("Password")).sendKeys(Password);
		w.findElement(By.id("LoginButton")).sendKeys("\n");
		Thread.sleep(2000);
		boolean signoutpresent = w.findElements(
				By.xpath("//section[@id='section-login']/div/div/div/h5/a"))
				.size() != 0;
		if (signoutpresent == true) {
			Assert.assertEquals(
					w.findElement(
							By.xpath("//section[@id='section-login']/div/div/div/h2"))
							.getText(), loggedin);
			w.getPageSource().contains(Email_Id);
			w.findElement(By.linkText("My Profile")).sendKeys("\n");
			Thread.sleep(500);
			// System.out.println(w.findElement(By.xpath("//form[@id='Form1']/div/div/div/h2")).getText());
			Assert.assertEquals(
					w.findElement(
							By.xpath("//form[@id='Form1']/div/div/div/h2"))
							.getText(), MyProfile);
			w.getPageSource().contains("Personal Information");
			w.findElement(By.name("FirstName")).clear();
			w.findElement(By.name("FirstName")).sendKeys(Updated_FirstName);
			w.findElement(By.id("City")).clear();
			w.findElement(By.id("City")).sendKeys(Updated_City);
			Thread.sleep(400);
			Select d = new Select(w.findElement(By.id("Country")));
			d.selectByVisibleText(Updated_Country);
			Thread.sleep(400);

			if (Updated_Country.equalsIgnoreCase("United States")) {
				Select d1 = new Select(w.findElement(By.id("State")));
				d1.selectByVisibleText("Guam");
			}
			if (Updated_Country.equalsIgnoreCase("United States")) {
				w.findElement(By.id("Street1")).clear();
				w.findElement(By.id("Street1")).sendKeys("updatedadd");
			}
			w.findElement(By.id("PostalCode")).clear();
			w.findElement(By.id("PostalCode")).sendKeys("369369");
			Select d2 = new Select(w.findElement(By.id("Profession")));
			d2.selectByVisibleText(Updated_Profession);
			Thread.sleep(800);
			if (Updated_Profession.equals("Physician")
					|| Updated_Profession.equals("Medical Student")
					|| Updated_Profession.equals("Nurse Practitioner")
					|| Updated_Profession.equals("Physician Assistant")
					|| Updated_Profession
							.equals("Licensed Practical Nurse (LPN)")
					|| Updated_Profession.equals("Registered Nurse (RN)")
					|| Updated_Profession.equals("Dentist")) {
				Select d3 = new Select(
						w.findElement(By.id("CustomerSpecialty")));
				d3.selectByVisibleText(Updated_Specialty);
			}
			Thread.sleep(3000);
			w.findElement(By.id("SaveLinkText")).sendKeys("\n");
			Thread.sleep(10000);
			System.out.println(w.getTitle());
			
			Assert.assertEquals(w.getTitle(), My_Account_Title);
			Thread.sleep(300);
			w.getPageSource().contains(Email_Id);
			Assert.assertEquals(
					w.findElement(
							By.xpath("//section[@id='section-login']/div/div/div/h2"))
							.getText(), loggedin);
			System.out.println("verified");
			Thread.sleep(500);
			w.findElement(By.cssSelector("#section-login > div > div > div > h5 > a")).sendKeys("\n");
			Thread.sleep(500);
		}
		
	}

}
