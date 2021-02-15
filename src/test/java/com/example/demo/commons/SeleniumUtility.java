package com.example.demo.commons;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

@Component
public class SeleniumUtility {

    @Autowired
    private  WebDriver driver;

    final static Logger logger = Logger.getLogger(SeleniumUtility.class);

    public  String takeScreenshot() {


        String screenshotsBaseDir = System.getProperty("screenshot.base.dir") + "/";
        new File(screenshotsBaseDir).mkdirs();

        try {
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            String filePath = screenshotsBaseDir + System.currentTimeMillis() + ".png";
            File destFile = new File(filePath);
            FileUtils.copyFile(scrFile, destFile);
            return screenshotsBaseDir.substring(screenshotsBaseDir.lastIndexOf("screenshots")) + destFile.getName();
        } catch (WebDriverException e) {
        } catch (IOException e) {
        }

        return null;
    }

    private  WebElement getElement(By by) {
        return driver.findElement(by);
    }
    public  void removeElement(By by) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].innerHTML= \"\";", getElement(by));
        } catch (WebDriverException e) {
        }
    }
    public  String acceptAlert() {
        return closeAlert(true);
    }
    private  String handle;

    /**
     * Window PopUp and IFrames handling
     */
    public  void webDriverWait(ExpectedCondition expectedCondition) {
        if (!isAlertPresent()) {
            try {
                int sleep = Integer.parseInt(System.getProperty("sleep"));
                (new WebDriverWait(driver, sleep)).until(expectedCondition);
            } catch (Exception e) {
            }
        } else {
            String message = getAlertText();
            if (message.contains("500 Internal Server Error")) {
                acceptAlert();
            }
        }
    }

    public  void switchToPopup() {
        try {
            handle = driver.getWindowHandle();
            webDriverWait(ExpectedConditions.numberOfWindowsToBe(2));

            Set<String> set = driver.getWindowHandles();
            set.remove(handle);

            if (set.size() >= 1) {
               driver.switchTo().window((String) set.toArray()[0]);
                driver.manage().window().maximize();
            }
        } catch (Exception e) {
        }

    }
    private  WebElement frame;
    public  boolean isElementDisplayed(By by) {
        try {
            return getElement(by).isDisplayed();
        } catch (Exception e) {

        }
        return false;
    }
    private  boolean isDocumentReady() {
        try {
            return (((JavascriptExecutor) driver).executeScript("return document.readyState;").toString()
                    .equalsIgnoreCase("complete"));
        } catch (Exception e) {
            return true;
        }
    }
    private  void waitForJs() {
        try {
            if (!isAlertPresent()) {
                if (!isDocumentReady()) {
                    ExpectedCondition<Boolean> jsLoad = driver -> (((JavascriptExecutor) driver)
                            .executeScript("return document.readyState;").toString().equalsIgnoreCase("complete"));
                    webDriverWait(jsLoad);

                    ExpectedCondition<Boolean> jqueryLoad = driver -> (((JavascriptExecutor) driver)
                            .executeScript("return jQuery.active == 0;").equals(true));
                    webDriverWait(jqueryLoad);
                }
                //waitForAjax();

            } else {
                String message = getAlertText();
                if (message.contains("500 Internal Server Error")) {
                    acceptAlert();
                    waitForJs();
                }
            }
        } catch (Exception e) {
        }
    }

    public  void switchToFrame(By by) {
        try {
            int attempt = 0;
            while (!isElementDisplayed(by)) {
                try {
                    frame = getElement(By.tagName("iframe"));
                    if (frame != null) {
                        driver.switchTo().frame(frame);
                        waitForJs();
                    }

                } catch (NoSuchElementException e) {
                    return;
                }

                attempt++;
                if (attempt == 5) {
                    frame = null;
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public  void waitForElementVisibility(By by) {
        webDriverWait(ExpectedConditions.visibilityOfElementLocated(by));
    }
    private  String getTagName(WebElement webElement) {
        try {
            return ((JavascriptExecutor) driver).executeScript("return arguments[0].tagName;", webElement).toString()
                    .toLowerCase();
        } catch (Exception e) {
        }
        return "";
    }

    public  void scrollToElement(WebElement webElement) {
        WebElement element = webElement; //= driver.findElement(By.xpath("bla-bla-bla"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", webElement);
    }
    private  void copyFile(HashMap hashMap, String fileExt) throws IOException {
        String downloadPath = System.getProperty("user.home") + "/Downloads/";

        //CoreDto coreDto = new CoreDto(hashMap);
        //String testId = coreDto.getTestId() != null && !coreDto.getTestId().isEmpty() ? coreDto.getTestId() : "0";
        //String targetDir = System.getProperty("dir.report.downloads") + "/" + testId;

        File srcFile = new File("");//ResourceUtil().getTheNewestFile(downloadPath, fileExt);
        if (srcFile != null) {
            File destFile = new File("");
            FileUtils.forceMkdir(destFile);
            FileUtils.copyFileToDirectory(srcFile, destFile);
            FileUtils.forceDelete(srcFile);
        }

    }
    public  void dragAndDropElement(WebElement elementToDrag,WebElement destinationLocator)
    {
        Actions drop = new Actions(driver);
        drop.dragAndDrop(elementToDrag,destinationLocator).build().perform();
    }
    public  void doubleClickElementByXPath(WebElement element)
    {
        Actions act = new Actions(driver);
        act.doubleClick(element);
    }

    private  void clickElementAndRemove(By by, String tagName, String value) {
        // clickElement(by,tagName, value);
        removeElement(by);
    }

    private  String closeAlert(boolean isAccept) {
        try {
            boolean isClosedAllAlerts = false;
            String alertText = "";
            while (!isClosedAllAlerts) {
                try {
                    Alert alert = driver.switchTo().alert();
                    if (isAccept) {
                        alert.accept();
                    } else {
                        alert.dismiss();
                    }
                    logger.error(alert.getText());
                    alertText += alert.getText() + "\n";
                } catch (NoAlertPresentException e) {
                    isClosedAllAlerts = true;
                }
            }
            return alertText;
        } catch (Exception e) {
            return "";
        }
    }
    private  String dismissAlert() {
        return closeAlert(false);
    }

    private  boolean isAlertPresent() {
        try {

            System.out.println(driver);
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException | NoSuchWindowException e) {
            return false;
        }
    }
    private  String getAlertText() {
        try {
            if (isAlertPresent()) {
                Alert alert = driver.switchTo().alert();
                return alert.getText();
            }
        } catch (NoAlertPresentException e) {

        }
        return "";
    }

}
