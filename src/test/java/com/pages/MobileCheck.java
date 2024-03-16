package com.pages;

import org.openqa.selenium.By;
import org.testng.Assert;

import com.main.BaseClass;

public class MobileCheck extends BaseClass {
	
	//Search mobile validation

	public static void mobileSearchValidation() {
		
		waitForElement("searchTextBox");
		String actualText = driver.findElement(By.xpath(getLocator("searchTextBox"))).getAttribute("value");		
		if (actualText.equalsIgnoreCase(getProperty("ProductSelection"))) {
			Assert.assertTrue(true);
			logger.info("Mobile filter is applied properly");
			log.info("Mobile filter is applied properly");
		} else {
			logger.info("Mobile filter is not applied properly, Please check!!!");
			log.info("Mobile filter is not applied properly, Please check!!!");
			throw new RuntimeException("Failed");

		}
	}

	public static void mobileVerification() {

		// Validate the result contains “Gionee” displayed

		waitForElement("ramCheck");
		int mobileList = driver.findElements(By.xpath(getLocator("mobileCheck"))).size();

		if (mobileList >= 1) {
			Assert.assertTrue(true);
			logger.info("GIONEE Mobile results are listed properly");
			log.info("GIONEE Mobile results are listed properly");
		} else {
			logger.info("GIONEE Mobile results are not listed properly, Please check!!!");
			log.info("GIONEE Mobile results are not listed properly, Please check!!!");
			throw new RuntimeException("Failed");
		}

		// Capacity should be 2GB
		String actualRam = driver.findElement(By.xpath(getLocator("ramCheck"))).getText();

		if (actualRam.contains(getProperty("gbCheck"))) {
			Assert.assertTrue(true);
			logger.info("Capacity 2 GB RAM is listed properly");
			log.info("Capacity 2 GB RAM is listed properly");
		} else {
			logger.info("Capacity 2 GB RAM is listed properly");
			log.info("Capacity 2 GB RAM is listed properly");
			throw new RuntimeException("Failed");
		}
	}

	// Brand filter removal validation
	public static void clearBrandValidation() throws Throwable {
		click("gioneeBrandCloseIcon");
		Thread.sleep(1000);
		waitForElement("gioneeBrandCheckbox");
		int filterSize = driver.findElements(By.xpath(getLocator("ginoeeDeletionCheck"))).size();
		if (filterSize == 1) {
			Assert.assertTrue(true);
			logger.info("GIONEE Mobile filter is removed successfully");
			log.info("GIONEE Mobile filter is removed successfully");
		} else {
			logger.info("GIONEE Mobile filter is not removed, Please check!");
			log.info("GIONEE Mobile filter is not removed, Please check!");
			throw new RuntimeException("Failed");
		}

	}
}
