package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class SearchPageObject extends MainPageObject {
    private static final String
            SEARCH_INIT_XPATH = "//android.widget.TextView[@text='Search Wikipedia']",
            SEARCH_INPUT_ID = "org.wikipedia:id/search_src_text",
            SEARCH_RESULTS_ID = "org.wikipedia:id/fragment_search_results",
            SEARCH_CLOSE_BUTTON_ID = "org.wikipedia:id/search_close_btn",
            SEARCH_RESULT_BY_TITLE_XPATH_TEMPLATE = "//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title']" +
                    "[@text='{" + REPLACEABLE_TEMPLATE_SUBSTRING + "}']";

    public SearchPageObject(AppiumDriver<WebElement> driver) {
        super(driver);
    }

    public void initializeSearch() {
        this.waitForElementAndClick(By.xpath(SEARCH_INIT_XPATH));
        this.waitForElementPresent(By.id(SEARCH_INPUT_ID));
    }

    public void enterSearchText(String searchText) {
        this.waitForElementAndSendKeys(By.id(SEARCH_INPUT_ID), searchText);
    }

    public void closeSearch() {
        this.waitForElementAndClick(By.id(SEARCH_CLOSE_BUTTON_ID));
    }

    public void waitForSearchResultsPresent() {
        this.waitForElementPresent(By.id(SEARCH_RESULTS_ID));
    }

    public void waitForSearchResultsNotPresent() {
        this.waitForElementNotPresent(By.id(SEARCH_RESULTS_ID));
    }

    public void openArticleFromSearchResults(String title) {
        final String searchResultXpath = getSearchResultXpath(title);
        waitForElementAndClick(By.xpath(searchResultXpath));
    }

    /* TEMPLATES METHODS */
    private String getSearchResultXpath(String title) {
        return this.replaceSubstringInTemplate(SEARCH_RESULT_BY_TITLE_XPATH_TEMPLATE, title);
    }
    /* TEMPLATES METHODS */
}
