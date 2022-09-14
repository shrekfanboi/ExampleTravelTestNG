package com.automation.Generic;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listener implements ITestListener {

		
	@Override
	public void onTestSkipped(ITestResult result) {
		System.out.println("Test Case "+result.getName().toUpperCase()+" was skipped");
	}
	
	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedWithTimeout(result);
	}
	@Override
	public void onStart(ITestContext context) {
		if(context.getName().contains("_")) System.out.println("Test "+context.getName().split("_")[0].toUpperCase()+" started");
		else  System.out.println("Test "+context.getName().toUpperCase()+" started");
		try {
			ScreenRecorderUtil.startRecording("myRecording");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void onFinish(ITestContext context) {
		if(context.getName().contains("_")) System.out.println("Test "+context.getName().split("_")[0].toUpperCase()+" finished");
		else System.out.println("Test "+context.getName().toUpperCase()+" finished");
		try {
			ScreenRecorderUtil.stopRecording();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override  
	public void onTestSuccess(ITestResult result) {  
	System.out.println("Test  Case "+result.getName().toUpperCase()+" has suceeded");  
	}  
	@Override
	public void onTestFailure(ITestResult result) {
		System.out.println("Test Case "+result.getName().toUpperCase()+" has failed");
		System.out.println(result.getThrowable().getMessage());
		DriverUtils.CaptureScreenShot();
	}
}
