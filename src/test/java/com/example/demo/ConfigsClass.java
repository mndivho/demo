package com.example.demo;

import com.example.demo.commons.DriverFactory;
import com.example.demo.commons.ExtentReportBase;
import com.example.demo.commons.SeleniumUtility;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.test.context.TestPropertySource;

//@TestConfiguration
//@TestPropertySource("classpath:testconfigs.properties")
//@ComponentScan(basePackages = "za.co.commandquality.testautomationframework")

@Component
public class ConfigsClass {
    @Value("chrome.class.loader")
    private String chromeClassLoader;

    @Value("${default.timeout:60}")
    private int timeout;

//    @Value("${chrome.driver.path}")
//    private String chromeDroverPath;
//
//    @Value("${gecko.class.loader}")
//    private String geckoClassLoader;
//
//    @Value("${gecko.driver.path}")
//    private String geckoDriverPath;
//
//    @Value("${ie.class.loader}")
//    private String ieClassLoader;
//
//    @Value("${ie.driver.path}")
//
//    private String iePriverPath;
//
//    public String getChromeClassLoader() {
//        return chromeClassLoader;
//    }
//
//    public String getChromeDroverPath() {
//        return chromeDroverPath;
//    }
//
//    public String getGeckoClassLoader() {
//        return geckoClassLoader;
//    }
//
//    public String getGeckoDriverPath() {
//        return geckoDriverPath;
//    }
//
//    public String getIeClassLoader() {
//        return ieClassLoader;
//    }
//
//    public String getIePriverPath() {
//        return iePriverPath;
//    }

    @Bean
    public WebDriver createDriver() {
        WebDriver driver = new DriverFactory().getDriver();
        return driver;
    }

    @Bean
    public WebDriverWait webDriverWait(WebDriver driver) {
        return new WebDriverWait(driver, this.timeout);
    }

    @Bean
    public SeleniumUtility utility() {
        return new SeleniumUtility();
    }

    @Bean
    public ExtentReportBase initiateExtentReport(){
         return new ExtentReportBase();
    }
}
