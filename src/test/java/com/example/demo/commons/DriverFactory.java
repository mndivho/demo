package com.example.demo.commons;

import com.example.demo.ConfigsClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

//@Configuration
@Component
public class DriverFactory {


    private  WebDriver driver;
    private  Properties prop;

    private String scenarioPath;
    private static Logger log = LogManager.getLogger(DriverFactory.class.getName());
    Map<String, Map<String, String>> outerMap = new HashMap<>();
    ArrayList<Map> arrayList = new ArrayList();
//
//    @Autowired
//    private ConfigsClass configsClass;
//    @Autowired
//    private ApplicationContext appContext;

    public WebDriver getDriver() {

        //configsClass = new ConfigsClass();
        prop = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/environmentVariables.properties");
            prop.load(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        InputStream is = JavaClassName.class.getClassLoader().getResourceAsStream("file.txt");
        String browserName = "chrome";
        switch (browserName) {
            case "chrome":
                //System.out.println(prop.getProperty("chrome") +prop.getProperty("chromePath")+"******************************************************");
                System.setProperty(prop.getProperty("chrome"), prop.getProperty("chromePath"));
                ///System.setProperty("webdriver.chrome.driver", "src/main/resources/ChromeDriver/chromedriver.exe");
                driver = new ChromeDriver();
                System.out.println(prop.getProperty("url"));
                driver.get(prop.getProperty("url"));
                driver.manage().window().maximize();
                break;

            case "firefox":
                System.setProperty(prop.getProperty("firefox"), prop.getProperty("geckoPath"));
                driver = new FirefoxDriver();
                driver.get("https://ui.uatwamly.co.za/");
                break;

            case "IE":
                System.setProperty(prop.getProperty("IE"), prop.getProperty("IEPath"));
                driver = new FirefoxDriver();
                driver.get("https://ui.uatwamly.co.za/");
                break;
            default:
                throw new RuntimeException("Invalid browser");
        }
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        return driver;
    }

//    public  WebDriver getDriver() {
//        return driver;
//    }
}
