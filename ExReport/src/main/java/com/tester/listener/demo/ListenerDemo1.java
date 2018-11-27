package com.tester.listener.demo;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class ListenerDemo1 extends TestListenerAdapter {
    @Override
    public void onTestFailure(ITestResult tr) {
        super.onTestFailure(tr);
        System.out.println("testNG  testFailer");
    }
}
