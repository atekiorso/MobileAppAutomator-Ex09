package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {
    public static final String
            TITLE_ID = "org.wikipedia:id/view_page_title_text",
            MORE_OPTIONS_BUTTON_XPATH = "//android.widget.ImageView[@content-desc='More options']",
            ADD_TO_READING_LIST_MENU_ITEM_XPATH = "//android.widget.TextView[@resource-id='org.wikipedia:id/title'][@text='Add to reading list']",
            NAVIGATE_UP_BUTTON_XPATH = "//android.widget.ImageButton[@content-desc='Navigate up']";

    public ArticlePageObject(AppiumDriver<WebElement> driver) {
        super(driver);
    }

    @SuppressWarnings("UnusedReturnValue")
    public WebElement findArticleTitleWithoutWaiting() {
        return this.findElementWithoutWaiting(By.id(TITLE_ID));
    }

    public void callMoreOptionsMenu() {
        this.waitForElementAndClick(By.xpath(MORE_OPTIONS_BUTTON_XPATH), LONG_WAITING_TIMEOUT_IN_SECONDS);
    }

    public void callAddToReadingListAction() {
        this.waitForElementAndClick(By.xpath(ADD_TO_READING_LIST_MENU_ITEM_XPATH), LONG_WAITING_TIMEOUT_IN_SECONDS);
    }

    public void closeArticle() {
        this.waitForElementAndClick(By.xpath(NAVIGATE_UP_BUTTON_XPATH));
    }

    public String getOpenArticleTitle() {
        WebElement articleTitle = this.waitForElementPresent(By.id(TITLE_ID));
        return articleTitle.getText();
    }
}
