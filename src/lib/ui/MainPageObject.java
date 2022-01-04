package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MainPageObject {
    protected AppiumDriver<WebElement> driver;
    public static final long
            DEFAULT_WAITING_TIMEOUT_IN_SECONDS = 5,
            LONG_WAITING_TIMEOUT_IN_SECONDS = 15,
            DEFAULT_SWIPE_DURATION_IN_MILLIS = 500;
    public static final String
            REPLACEABLE_TEMPLATE_SUBSTRING = "SUBSTRING";

    public MainPageObject(AppiumDriver<WebElement> driver) {
        this.driver = driver;
    }

    /* TEMPLATES METHODS */
    protected String replaceSubstringInTemplate(String template, String replacement) {
        final String target = "{" + REPLACEABLE_TEMPLATE_SUBSTRING + "}";

        return template.replace(target, replacement);
    }

    // Подстановка произвольного количества нумерованных параметров в шаблоне, начиная с {SUBSTRING1}
    protected String replaceSubstringsInTemplate(String template, List<String> replacements) {
        int replacementIndex = 0;
        String target;
        String result = template;

        for (String replacement : replacements) {
            replacementIndex ++;
            target = "{" + REPLACEABLE_TEMPLATE_SUBSTRING + replacementIndex + "}";
            result = result.replace(target, replacement);
        }

        return result;
    }
    /* TEMPLATES METHODS */

    protected boolean isElementPresent(By by) {
        return isElementPresent(by, DEFAULT_WAITING_TIMEOUT_IN_SECONDS);
    }

    @SuppressWarnings("SameParameterValue")
    protected boolean isElementPresent(By by, long timeoutInSeconds) {
        try {
            waitForElementPresent(by, timeoutInSeconds);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    @SuppressWarnings("UnusedReturnValue")
    protected WebElement waitForElementAndClick(By by) {
        return waitForElementAndClick(by, DEFAULT_WAITING_TIMEOUT_IN_SECONDS);
    }

    protected WebElement waitForElementAndClick(By by, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, timeoutInSeconds);
        element.click();

        return element;
    }

    @SuppressWarnings("UnusedReturnValue")
    protected WebElement waitForElementAndSendKeys(By by, String charSequences) {
        return waitForElementAndSendKeys(by, charSequences, DEFAULT_WAITING_TIMEOUT_IN_SECONDS);
    }

    @SuppressWarnings("SameParameterValue")
    protected WebElement waitForElementAndSendKeys(By by, String charSequences, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, timeoutInSeconds);
        element.clear();
        element.sendKeys(charSequences);

        return element;
    }

    protected void waitForElementAndSwipeLeft(By by) {
        waitForElementAndSwipeLeft(by, DEFAULT_WAITING_TIMEOUT_IN_SECONDS);
    }

    @SuppressWarnings("SameParameterValue")
    protected void waitForElementAndSwipeLeft(By by, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, timeoutInSeconds);

        Point elementLocation = element.getLocation();
        int elementYCenter = element.getLocation().y + (element.getSize().height / 2);

        new TouchAction<>(driver)
                .press(PointOption.point(elementLocation.x + element.getSize().width, elementYCenter))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(DEFAULT_SWIPE_DURATION_IN_MILLIS)))
                .moveTo(PointOption.point(elementLocation.x, elementYCenter))
                .release()
                .perform();
    }

    @SuppressWarnings("UnusedReturnValue")
    protected WebElement waitForElementPresent(By by) {
        return waitForElementPresent(by, DEFAULT_WAITING_TIMEOUT_IN_SECONDS);
    }

    protected WebElement waitForElementPresent(By by, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage("Не найден элемент! " + by.toString());

        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    protected void waitForElementNotPresent(By by) {
        waitForElementNotPresent(by, DEFAULT_WAITING_TIMEOUT_IN_SECONDS);
    }

    @SuppressWarnings("SameParameterValue")
    protected void waitForElementNotPresent(By by, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage("Элемент, который должен отсутствовать, найден! " + by.toString());
        wait.until(ExpectedConditions.numberOfElementsToBe(by, 0));
    }

    protected WebElement findElementWithoutWaiting(By by) {
        return driver.findElement(by);
    }

    protected List<WebElement> getElements(By by) {
        return getElements(by, DEFAULT_WAITING_TIMEOUT_IN_SECONDS);
    }

    @SuppressWarnings("SameParameterValue")
    protected List<WebElement> getElements(By by, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage("Не найдены элементы! " + by.toString());
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }
}
