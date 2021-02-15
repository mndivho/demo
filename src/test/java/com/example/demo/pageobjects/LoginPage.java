package com.example.demo.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.springframework.stereotype.Component;


public class LoginPage {

    @FindBy(how = How.XPATH, using = "//input[@type='email']")
    public WebElement username;

    @FindBy(how = How.XPATH, using = "//input[@type='password']")
    public WebElement password;

    @FindBy(how = How.XPATH, using = "//button[@type='submit']")
    public WebElement loginButton;

}
