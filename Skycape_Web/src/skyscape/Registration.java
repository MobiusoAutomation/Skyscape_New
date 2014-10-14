package skyscape;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Registration extends Base {

	String xlFilePathlogin = "Excels/Registrationnew.xls";
	String login = "Sign In | Skyscape";
	String My_Account_Title = "My Account | Skyscape";
	String email;
	
	
	
	@DataProvider(name = "Registration", parallel = false)
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
		WritableWorkbook copy = Workbook.createWorkbook(new File(
				"RegistrataionResult.xls"), workbook);
		WritableSheet sheet2 = copy.getSheet(0);
		email = null;
				
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
				email = readFromFile();
				Label emailid = new Label(j, 11, email);
				sheet2.addCell(emailid);
			}
			copy.write();
			copy.close();
		}

		return (tabArraylogin);
	}

	@Test(dataProvider = "Registration")
	public void main(String Scenario, String FirstName,
			String LastName, String Country, String Address1, String City,
			String State, String Zip, String Profession, String Specialty, String EmailId, String Password, String Confirm, String Expected)
			throws BiffException, IOException, InterruptedException,
			RowsExceededException, WriteException {
		
		String validemail = email + "@gmail.com";
		System.out.println(validemail);
		w.get("http://54.83.8.95/register/login.aspx?ReturnUrl=%2fsecure%2fmyaccount.aspx");
		Thread.sleep(400);
		w.findElement(By.id("NewRegistration")).sendKeys("\n");
		Thread.sleep(400);
		WebElement frameID = w.findElement(By
				.cssSelector("#cboxLoadedContent > iframe"));
		w.switchTo().frame(frameID);
		Thread.sleep(400);
		w.findElement(By.id("SSORegistration_txtFirstName"))
				.sendKeys(FirstName);
		w.findElement(By.id("SSORegistration_txtLastName")).sendKeys(LastName);
		if (Country.length() != 0) {
			Select d = new Select(w.findElement(By
					.id("SSORegistration_ddlCountry")));
			d.selectByVisibleText(Country);
		}
		if (Country.equalsIgnoreCase("United States")) {
			w.findElement(By.id("SSORegistration_txtAddress1")).sendKeys(
					Address1);
		}
		Thread.sleep(800);
		if (City.length() != 0) {
			w.findElement(By.id("SSORegistration_txtCity")).sendKeys(City);
		}
		if (Country.equalsIgnoreCase("United States")) {
			if (State.length() != 0) {
				Select d1 = new Select(w.findElement(By
						.id("SSORegistration_ddlState")));
				d1.selectByVisibleText(State);
			}
		}
		w.findElement(By.id("SSORegistration_txtZip")).sendKeys(Zip);
		if (Profession.length() != 0) {
			Select d2 = new Select(w.findElement(By
					.id("SSORegistration_ddlOccupation")));
			d2.selectByVisibleText(Profession);
		}
		Thread.sleep(400);
		if (Profession.equals("Physician")
				|| Profession.equals("Medical Student")
				|| Profession.equals("Nurse Practitioner")
				|| Profession.equals("Physician Assistant")
				|| Profession.equals("Licensed Practical Nurse (LPN)")
				|| Profession.equals("Registered Nurse (RN)")
				|| Profession.equals("Dentist")) {
			if (Specialty.length() != 0) {
				Select d3 = new Select(w.findElement(By
						.id("SSORegistration_ddlSpecialty")));
				d3.selectByVisibleText(Specialty);
			}
		}
		w.findElement(By.id("SSORegistration_txtEmail")).sendKeys(validemail);
		System.out.println("validemail");

		w.findElement(By.id("SSORegistration_txtPassword")).sendKeys(Password);
		w.findElement(By.id("SSORegistration_txtConfirmPassword")).sendKeys(
				Confirm);
		w.findElement(By.id("SSORegistration_chkTermsOfservice")).click();
		w.findElement(By.id("SSORegistration_btnCreateAccount")).sendKeys("\n");
		Thread.sleep(10000);
		// String actual =
		boolean regi_form = w.findElements(By.xpath("//div[@id='SSORegistration_pnlReg']/div")).size()!=0;
		System.out.println(regi_form);
		// w.findElement(By.cssSelector("#SSORegistration_valSummary")).getText();
		if(regi_form==true){
			w.getTitle().contains(login);
			Assert.assertEquals(login, w.getTitle());
			w.getPageSource().contains("Please correct the highlighted fields:");
			System.out.println("User id creation is failed");
		}else{
			w.switchTo().defaultContent();
			Thread.sleep(1000);
			Assert.assertEquals(My_Account_Title, w.getTitle());
			w.getPageSource().contains("Sign Out");
			writeToFile2(email);
			String newEmail = getNextAlphaNumeric(email);
			Thread.sleep(2000);
			System.out.println(newEmail + "after creation");
			writeToFile(newEmail);
			Thread.sleep(2000);
			System.out.println("User id created successfully");
			w.findElement(By.linkText("Sign Out")).sendKeys("\n");
		} 
		
		if(w.getPageSource().contains("Sign Up")){
			w.getTitle().contains(login);
			Assert.assertEquals(login, w.getTitle());
			w.getPageSource().contains("Please correct the highlighted fields:");
			System.out.println("User id creation is failed");
		}
		}
		
	private void writeToFile2(String email) throws FileNotFoundException {
		PrintWriter out = new PrintWriter("Excels/Registereduserid.txt");
		System.out.println("current id" + email);
		if (email.length() != 0) {
			out.println(email);
			System.out.println(email + "written-Registered");
			out.close();
		}
				
	}

	public static String readFromFile() throws IOException {

		FileInputStream fstream = new FileInputStream(
				"Excels/Emailidtxt123.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

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

	public static String getNextAlphaNumeric(String oldAlphaNumericString) {

		// number of alphabets in alphanumeric string

		int noOfAlphabets = 8;
		String subStringAlphabet = oldAlphaNumericString.substring(0,
				noOfAlphabets);
		String subStringNoAlpha = oldAlphaNumericString
				.substring(noOfAlphabets);
		String leadingZeros = "";
		String noLeadingZeros = "";
		String newAlphaNumeric = "";
		// To count number of leading zeros

		for (int i = 0; i < subStringNoAlpha.length(); i++) {
			if (subStringNoAlpha.charAt(i) == '0') {
				leadingZeros += subStringNoAlpha.charAt(i);
			}

			else {
				break;
			}
		}
		if (leadingZeros.length() > 0) {
			// number to be added
			int addToAN = 1;
			noLeadingZeros += subStringNoAlpha.substring(leadingZeros.length());
			String noLeadingZeroNewAN = ""
					+ (Long.parseLong(noLeadingZeros) + addToAN);
			if (noLeadingZeroNewAN.length() != noLeadingZeros.length()) {
				// To check whether number of digits changed. Eg : 99 to 100
				int diff = noLeadingZeroNewAN.length()
						- noLeadingZeros.length();
				for (int i = 0; i < leadingZeros.length() - diff; i++) {
					newAlphaNumeric += leadingZeros.charAt(i);
				}
				newAlphaNumeric += "" + noLeadingZeroNewAN;
			} else {
				for (int i = 0; i < leadingZeros.length() - noOfAlphabets; i++) {
					newAlphaNumeric += leadingZeros.charAt(i);
				}
				newAlphaNumeric += "" + noLeadingZeroNewAN;
			}
		}

		else {
			newAlphaNumeric += "" + (Long.parseLong(subStringNoAlpha) + 1);
		}
		return subStringAlphabet + newAlphaNumeric;
	}

	private static void writeToFile(String newEmail) throws IOException {

		PrintWriter out = new PrintWriter("Excels/Emailidtxt123.txt");

		if (newEmail.length() != 0) {

			out.println(newEmail);
			System.out.println(newEmail + "written");
			out.close();
		}
		
		
	}
	}
