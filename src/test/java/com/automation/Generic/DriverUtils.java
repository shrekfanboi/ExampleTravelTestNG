package com.automation.Generic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
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
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;

import io.github.bonigarcia.wdm.WebDriverManager;




public class DriverUtils {

	private static WebDriver driver;

	public static void createWebDriver() {
		WebDriverManager.chromedriver().setup(); 
		driver = new ChromeDriver();
	}
	public static void createWebDriver(String browser,String...args) {
		Browser browserType = Browser.valueOf(browser);
		args = resolveBrowserOptions(args,browserType);
		switch(browserType) {
		case Chrome:
			ChromeOptions chrome_options = new ChromeOptions();
			chrome_options.addArguments(args);
			try {
				System.setProperty("webdriver.gecko.driver", "./src/test/resources/driver/chromedriver.exe");
				driver = new ChromeDriver(chrome_options);
			}
			catch(IllegalStateException e) {
				WebDriverManager.chromedriver().setup(); 
				driver  = new ChromeDriver(chrome_options);
			}
			break;
		case Firefox:
			FirefoxOptions firefox_options = new FirefoxOptions();
			firefox_options.addArguments(args);
			try {
				System.setProperty("webdriver.gecko.driver", "./src/test/resources/driver/geckodriver.exe");
				driver = new FirefoxDriver(firefox_options);				
			}
			catch(IllegalStateException e) {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver(firefox_options);
			}
			break;
		case Edge:
			EdgeOptions edge_options = new EdgeOptions();
			edge_options.addArguments(args);
			try {
				System.setProperty("webdriver.edge.driver", "./src/test/resources/driver/msedgedriver.exe");
				driver = new EdgeDriver(edge_options);
			}
			catch(IllegalStateException e) {
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver(edge_options);				
			}
			break;
		case Safari:
			WebDriverManager.safaridriver().setup();
			driver = new SafariDriver();
			break;
		default:
			break;
		}
	}


	

	public static WebDriver getDriver() {
		if (driver == null)
			createWebDriver();
		return driver;
	}
	
	public static String[] resolveBrowserOptions(String[] args,Browser  browser){
		switch(browser) {
		case Chrome:
			args = Arrays.stream(args).filter(e->!e.isEmpty()).map(e->"--"+e).toArray(String[]::new);
		case Firefox:
		case Edge:
			args = Arrays.stream(args).filter(e->!e.isEmpty()).map(e->"-"+e).toArray(String[]::new);
		default:
			break;
		}
		return args;
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
