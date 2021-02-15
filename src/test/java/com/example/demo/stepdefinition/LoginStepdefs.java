package com.example.demo.stepdefinition;


import com.example.demo.commons.ExtentReportBase;
import com.example.demo.commons.SeleniumUtility;
import com.example.demo.integration.CucumberRoot;
import com.example.demo.pageobjects.LoginPage;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

//public class LoginStepdefs extends CucumberRoot {

public class LoginStepdefs extends CucumberRoot {

    @Autowired
    private WebDriver driver;

    @Autowired
    private WebDriverWait wait;

    @Autowired
    private SeleniumUtility utility;

    @Autowired
    private ExtentReportBase extentReportBase;

    private LoginPage loginPage;// = PageFactory.initElements(driver, LoginPage.class);
    ExtentTest test;

    @PostConstruct
    public void init() {
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        extentReportBase.setReports(new ExtentReports("src\\test\\resources\\reports\\results.html"));
        test = extentReportBase.getReports().startTest("Testing");
    }

//    @Then("^User log in with username <\"([^\"]*)\"> and password <\"([^\"]*)\">$")
//    public void userLogInWithUsernameAndPassword(String username, String password) throws Throwable {
//
//    }

    @Then("^User log in with username \"([^\"]*)\" and password \"([^\"]*)\">$")
    public void userLogInWithUsernameAndPassword(String username, String password) throws Throwable {
        utility.webDriverWait(x -> loginPage.username.isDisplayed());

        //driver.findElement(By.xpath("//input[@type='email']")).sendKeys(username);
        loginPage.username.sendKeys(username);
        loginPage.password.sendKeys(password);
        loginPage.loginButton.click();
        //utility.takeScreenshot();

        //writeReportTearDownBrowzer();
    }

    @After
    public void writeReportTearDownBrowzer() {
        System.out.println(test);
        test.log(LogStatus.PASS, "Navigated to project successfully");
        extentReportBase.getReports().endTest(test);
        extentReportBase.getReports().flush();
    }
}
