package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ReadingListPageObject extends MainPageObject {
    public static final String
            GOT_IT_BUTTON_ID = "org.wikipedia:id/onboarding_button",
            CREATE_NEW_BUTTON_ID = "org.wikipedia:id/create_button",
            INPUT_READING_LIST_NAME_ID = "org.wikipedia:id/text_input",
            INPUT_READING_LIST_NAME_OK_BUTTON_ID = "android:id/button1",
            MY_LISTS_BUTTON_ACCESSIBILITY_ID = "My lists",
            EXISTING_READING_LIST_XPATH_TEMPLATE = "//android.widget.TextView[@resource-id='org.wikipedia:id/item_title'][@text='{" +
                    REPLACEABLE_TEMPLATE_SUBSTRING + "}']",
            ARTICLE_IN_READING_LIST_XPATH_TEMPLATE = "//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title'][@text='{" +
                    REPLACEABLE_TEMPLATE_SUBSTRING + "}']/..";

    public ReadingListPageObject(AppiumDriver<WebElement> driver) {
        super(driver);
    }

    public void addArticleToNewReadingList(String readingListName) {
        // Если появилась форма с описанием возможностей Reading List - нажимаем кнопку "GOT IT", иначе - кнопку создания нового Reading List
        if (this.isElementPresent(By.id(GOT_IT_BUTTON_ID))) {
            this.waitForElementAndClick(By.id(GOT_IT_BUTTON_ID));
        } else {
            this.waitForElementAndClick(By.id(CREATE_NEW_BUTTON_ID));
        }

        enterReadingListName(readingListName);
        clickInputReadingListOkButton();
    }

    public void addArticleToExistingReadingList(String readingListName) {
        final String existingReadingListXpath = getExistingReadingListXpath(readingListName);
        this.waitForElementAndClick(By.xpath(existingReadingListXpath));
    }

    public void removeArticleFromReadingList(String readingListName, String articleTitle) {
        openReadingList(readingListName);
        final String articleInReadingListXpath = getArticleInReadingListXpath(articleTitle);
        this.waitForElementAndSwipeLeft(By.xpath(articleInReadingListXpath));
    }

    public void openMyLists() {
        this.waitForElementAndClick(MobileBy.AccessibilityId(MY_LISTS_BUTTON_ACCESSIBILITY_ID));
    }

    public void openReadingList(String readingListName) {
        final String readingListXpath = getExistingReadingListXpath(readingListName);
        this.waitForElementAndClick(By.xpath(readingListXpath));
    }

    public void openArticleFromCurrentReadingList(String articleTitle) {
        final String articleXpath = getArticleInReadingListXpath(articleTitle);
        this.waitForElementAndClick(By.xpath(articleXpath));
    }

    public boolean isArticlePresentInCurrentReadingList(String articleTitle) {
        final String articleXpath = getArticleInReadingListXpath(articleTitle);
        return this.isElementPresent(By.xpath(articleXpath));
    }

    private void enterReadingListName(String readingListName) {
        this.waitForElementAndSendKeys(By.id(INPUT_READING_LIST_NAME_ID), readingListName);
    }

    private void clickInputReadingListOkButton() {
        this.waitForElementAndClick(By.id(INPUT_READING_LIST_NAME_OK_BUTTON_ID));
    }

    private String getExistingReadingListXpath(String readingListName) {
        return this.replaceSubstringInTemplate(EXISTING_READING_LIST_XPATH_TEMPLATE, readingListName);
    }

    /* TEMPLATES METHODS */
    private String getArticleInReadingListXpath(String articleTitle) {
        return this.replaceSubstringInTemplate(ARTICLE_IN_READING_LIST_XPATH_TEMPLATE, articleTitle);
    }
    /* TEMPLATES METHODS */
}
