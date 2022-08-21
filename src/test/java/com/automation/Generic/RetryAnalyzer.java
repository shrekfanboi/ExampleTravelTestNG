package com.automation.Generic;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer{

	int retryCount = 0;
	int maxRetry = 10;
	@Override
	public boolean retry(ITestResult result) {
		if(retryCount < maxRetry) {
			System.out.println("Retrying "+result.getName()+" for "+retryCount+"/"+maxRetry);
			retryCount++;
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}

}
