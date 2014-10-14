package skyscape;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

public class Purchase extends Base {

	String logintitle = "Sign In | Skyscape";
	String password = "tester123";
	String Product2 = "Medical Eponyms";
	String Product2Detail = "MEDICAL EPONYMS";
	String Product1_title = "Medical Eponyms | Skyscape";
	String ExpSelectedoption4 = "iOS 6.0 or higher";
	String OSverify = "iOS";
	String checkoutmsg = "BILLING INFORMATION";
	String confirmscreen = "CONFIRM";
	String xlFilePathlogin = "Excels/Shopping.xls";
	String PurchTitle = "Thank You for Your Order";
	String PurchaseTitleExp = "Order Confirm";
	// String PurchaseSuc = "Thank You! Your order has been placed.";

	@DataProvider(name = "Purchase", parallel = false)
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

	@Test(priority = 0)
	public void Shopping_without_login() throws IOException,
			InterruptedException {

		Select s1 = new Select(w.findElement(By.name("AvailablePlatforms")));
		s1.selectByVisibleText(ExpSelectedoption4);
		w.findElement(By.id("AddToCart")).sendKeys("\n");
		List<WebElement> w2 = w
				.findElements(By
						.cssSelector("#CartSection > table.mycart.pad_left > tbody > tr:nth-child(3)"));
		int itemsincart = w2.size();
		if (itemsincart > 1) {
			if (w.findElement(By.cssSelector("#CartItems__ctl1_RemoveItem"))
					.isEnabled()) {
				w.findElement(By.cssSelector("#CartItems__ctl1_RemoveItem"))
						.sendKeys("\n");
			} else if (w.findElement(
					By.cssSelector("#CartItems__ctl2_RemoveItem")).isEnabled()) {
				w.findElement(By.cssSelector("#CartItems__ctl2_RemoveItem"))
						.sendKeys("\n");
			}

		} else {
			w.findElement(By.id("CheckoutLink")).sendKeys("\n");
			if (w.getTitle().contains(logintitle)) {
				Assert.assertTrue(true);
				String email = readFromFile();
				String validemail = email + "@gmail.com";
				System.out.println(validemail);
				w.findElement(By.name("EmailAddress")).clear();
				w.findElement(By.name("EmailAddress")).sendKeys(validemail);
				Thread.sleep(400);
				w.findElement(By.name("Password")).clear();
				w.findElement(By.name("Password")).sendKeys(password);
				w.findElement(By.id("LoginButton")).sendKeys("\n");

				if (w.getPageSource().contains(
						"Form Entries Incomplete or Invalid")) {
					w.findElement(By.name("EmailAddress")).clear();
					w.findElement(By.name("EmailAddress")).sendKeys(
							"testauto2@gmail.com");
					Thread.sleep(400);
					w.findElement(By.name("Password")).clear();
					w.findElement(By.name("Password")).sendKeys("tester123");
					w.findElement(By.id("LoginButton")).sendKeys("\n");
				}
			}
			Assert.assertEquals(
					w.findElement(
							By.xpath("//section[@id='section-cart']/div/div/div/h2"))
							.getText(), checkoutmsg);
			// w.getPageSource().contains("Billing Information");

		}

	}

	@Test(dataProvider = "Purchase", priority = 1)
	public void Shopping_with_CreditCard(String Condition, String CardNumber,
			String Month, String CVV, String Expected_Result)
			throws BiffException, IOException, RowsExceededException,
			WriteException, InterruptedException {

		w.findElement(By.id("BillPhoneNumber")).clear();
		w.findElement(By.id("BillPhoneNumber")).sendKeys("1234567890");
		w.findElement(By.id("CheckoutWithCC")).sendKeys("\n");
		if (CardNumber.length() != 0) {
			w.findElement(By.id("PaymentAccount")).clear();
			w.findElement(By.id("PaymentAccount")).sendKeys(CardNumber);
		}
		if (Month.length() != 0) {
			Select d4 = new Select(w.findElement(By.id("PaymentExpMonth")));
			d4.selectByVisibleText(Month);
		}
		if (CVV.length() != 0) {
			w.findElement(By.id("PaymentCvd")).clear();
			w.findElement(By.id("PaymentCvd")).sendKeys(CVV);
		}
		w.findElement(By.id("ContinueLink")).sendKeys("\n");

		Thread.sleep(2000);
		System.out.println(w.getTitle());
		boolean PurchaseStatus = w.getTitle().contains(PurchaseTitleExp);
		System.out.println(PurchaseStatus);
		if (PurchaseStatus==true) {
			String orderconfirm = w.findElement(
					By.xpath("//section[@id='section-cart']/div/div/div/h2"))
					.getText();
			Assert.assertEquals(confirmscreen, orderconfirm);
			w.getPageSource().contains("Please review and submit your order.");
			w.getPageSource().contains(Product2);
			w.findElement(By.id("PlaceOrderBottomLink")).sendKeys("\n");
			Thread.sleep(200);
			Assert.assertEquals(PurchTitle, w.getTitle());
			Assert.assertEquals(Expected_Result,w.findElement(By.xpath("//div[@id='mainDiv']/table/tbody/tr/td[2]/h1")).getText());
			w.getPageSource().contains(OSverify);

		} else {
			// add here assert to verify message
			boolean error = w.getPageSource().contains(Expected_Result);
			Assert.assertTrue(error);
		}
	}

	@BeforeTest
	public void beforeTest() throws InterruptedException {

		w.get("http://54.83.8.95/estore/ProductDetail.aspx?ProductId=2625");
		// w.findElement(By.linkText("PRODUCTS")).sendKeys("\n");
		// Thread.sleep(200);
		// w.findElement(By.linkText(" View All Products")).sendKeys("\n");
		// w.getTitle().contentEquals("Available Resources | Skyscape");
		// Thread.sleep(200);
		// w.findElement(By.linkText(Product2)).sendKeys("\n");
		Thread.sleep(500);
		// w.getTitle().contentEquals(Product1_title);
		Assert.assertEquals(Product1_title, w.getTitle());
		Thread.sleep(500);
		Assert.assertEquals(Product2Detail,
				w.findElement(By.xpath("//form[@id='Form1']/div/h2")).getText());
	}

	@AfterTest
	public void afterMethod() throws InterruptedException {
//		w.findElement(By.xpath("//ul[@id='primary']/li[6]/a")).sendKeys("\n");
//		Thread.sleep(800);
//		w.findElement(By.linkText("Sign Out")).sendKeys("\n");
		System.out.println("Done");
	}

	public static String readFromFile() throws IOException {

		FileInputStream fstream1 = new FileInputStream(
				"Excels/Registereduserid.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream1));

		StringBuilder email = new StringBuilder();

		String line;
		try {
			while ((line = br.readLine()) != null) {
				email.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return email.toString();
	}

}