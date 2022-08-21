package com.automation.Generic;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listener implements ITestListener {

		
	@Override
	public void onTestSkipped(ITestResult result) {
		System.out.println("Test Case "+result.getName()+" was skipped");
	}
	
	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedWithTimeout(result);
	}
	@Override
	public void onStart(ITestContext context) {
		System.out.println("Test "+context.getName()+" started");
		try {
			ScreenRecorderUtil.startRecording("myRecording");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		System.out.println("Test "+context.getName()+" finished");
		try {
			ScreenRecorderUtil.stopRecording();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override  
	public void onTestSuccess(ITestResult result) {  
	System.out.println("Test  Case "+result.getName()+" has suceeded");  
	}  
	@Override
	public void onTestFailure(ITestResult result) {
		System.out.println("Test Case "+result.getName()+" has failed");
		DriverUtils.CaptureScreenShot();
	}
}
