package com.automation.Generic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverUtils {

	private static WebDriver driver;

	public static void createWebDriver() {
		WebDriverManager.chromedriver().setup(); //external maven dependency
		//if above does not work, uncomment and execute the following code
//		System.setProperty("webdriver.chrome.driver","./driver/chromedriver.exe");
		driver = new ChromeDriver();
	}

	/**
	 * Use this overloaded form to pass chrome options
	 * @param args string options
	 * 
	 */
	public static void createWebDriver(String... args) {
		ChromeOptions options = new ChromeOptions();
		options.addArguments(args);
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver(options);
	}

	public static WebDriver getDriver() {
		if (driver == null)
			createWebDriver();
		return driver;
	}
	

	public static void switchTab() {
		String parent = driver.getWindowHandle();
		Set<String> handles = driver.getWindowHandles();
		for (String handle : handles) {
			if (!handle.equals(parent)) {
				driver.switchTo().window(handle);
			}
		}
	}

	public static String ReadExcelData(String filename, String sheetname, int row, int col) {
		try {
			FileInputStream fs = new FileInputStream(filename);
			XSSFWorkbook workbook = new XSSFWorkbook(fs);
			XSSFSheet sheet = workbook.getSheet(sheetname);
			DataFormatter formatter = new DataFormatter();
			Row r = sheet.getRow(row);
			workbook.close();
			return formatter.formatCellValue(r.getCell(col));
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}

	}

	/**
	 * Saves images in the screenshots folder with randomly generated image name
	 * 
	 */
	public static void CaptureScreenShot() {
		try {
			TakesScreenshot shot = (TakesScreenshot) driver;
			File src = shot.getScreenshotAs(OutputType.FILE);
			File folder = new File("./data/screenshots");
			if (!folder.exists()) folder.mkdir();
			String uniqueID = UUID.randomUUID().toString();
			File dest = new File("./data/screenshots/img_" + uniqueID + ".jpg");
			FileUtils.copyFile(src, dest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Saves screenshot at the desired location
	 * @param filename full path of the file (must include file extension)
	 */
	public static void CaptureScreenShot(String filename) {
		try {
			TakesScreenshot shot = (TakesScreenshot) driver;
			File src = shot.getScreenshotAs(OutputType.FILE);
			File dest = new File(filename);
			int fileIndex = 0;
			while(dest.exists()) {
				String name = filename.split("\\.")[0];
				dest = new File(name+"_"+fileIndex+"."+FilenameUtils.getExtension(filename));
				fileIndex++;
			}
			FileUtils.copyFile(src, dest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
