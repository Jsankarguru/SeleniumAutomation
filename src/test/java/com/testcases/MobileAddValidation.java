package com.testcases;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.main.BaseClass;
import com.pages.MobileCheck;

public class MobileAddValidation extends BaseClass {

	@Test(priority = 1)
	public static void gioneeAndTwoGBMobileSelectionTest() throws Throwable {

		try {
			startLogger("TC_GIONEE Brand & 2GB RAM Mobile Collection Test");
			log.info("Mobile selection execution is started");
			logger.info("Mobile selection execution is started");
			// waitForElement("pageValidation");
			// verifyByTitle("homePageTitle");
			// Search for mobile
			type("searchTextBox", "ProductSelection");
			click("searchIcon");
			MobileCheck.mobileSearchValidation();
			// Brand Selection and verification
			waitForElement("brandSearchbox");
			type("brandSearchbox", "brandName");
			click("gioneeBrandCheckbox");
			waitForElement("gioneeBrandValidation");
			verifyByText("gioneeBrandValidation", "brandAddValidation");
			// 2GB Ram Selection and validation
			waitForElement("gioneeRamCheckbox");
			click("gioneeRamCheckbox");
			waitForElement("ramTwoGBValidation");
			verifyByText("ramTwoGBValidation", "gbAddValidation");
			// Mobile result and capacity validation
			MobileCheck.mobileVerification();
			// Brand Removal validation
			MobileCheck.clearBrandValidation();
			log.info("Mobile selection Test execution is completed");
			logger.info("Mobile selection Test execution is completed");
			logger.log(Status.PASS, "Mobile Selection and Removal Execution is completed successfully");
		} catch (Exception e) {
			log.info("Mobile selection Test execution is failed");
			logger.info("Mobile selection Test execution is failed");
			logger.log(Status.FAIL, e);
			extentScreenshot("MobileSelectionFail");
			throw new RuntimeException("Mobile Selection and Removal Execution is failed");
		}
	}

}
