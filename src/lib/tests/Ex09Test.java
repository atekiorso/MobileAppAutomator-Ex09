package lib.tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import org.openqa.selenium.WebElement;

import java.util.List;

public class Ex09Test extends CoreTestCase {
    private SearchPageObject searchPageObject;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        searchPageObject = new SearchPageObject(this.driver);
    }

    public void testCheckSearchResultByTitleAndDescription() {
        final String searchText = "java";
        final String expectedTitle = "Java (programming language)";
        final String expectedDescription = "Object-oriented programming language";

        searchPageObject.initializeSearch();
        searchPageObject.enterSearchText(searchText);
        searchPageObject.waitForSearchResultsPresent();
        searchPageObject.waitForElementByTitleAndDescription(expectedTitle, expectedDescription);
    }

    public void testFirstThreeResultsTitles() {
        final String searchText = "world of warcraft";
        final int expectedMinResultsCount = 3;
        String errorMessage;
        String title;

        searchPageObject.initializeSearch();
        searchPageObject.enterSearchText(searchText);
        searchPageObject.waitForSearchResultsPresent();

        List<WebElement> elements = searchPageObject.getResultsTitles();

        int resultsCount = elements.size();
        errorMessage = "Результаты поиска \"" + searchText + "\" содержат количество статей: " + resultsCount +
                ", что меньше ожидаемых " + expectedMinResultsCount + "!";
        assertTrue(errorMessage, resultsCount >= expectedMinResultsCount);

        for (int i = 0; i < expectedMinResultsCount; i++) {
            title = elements.get(i).getText();
            errorMessage = "Заголовок статьи № " + (i + 1) + ":\"" + title + "\" " +
                    "в результатах поиска не содержит ожидаемый текст: \"" + searchText + "\"";
            assertTrue(errorMessage, title.toLowerCase().contains(searchText));
        }
    }
}
