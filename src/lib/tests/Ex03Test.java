package lib.tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;

// Тест из задания Ex3 после рефакторинга под паттерн PageObject
public class Ex03Test extends CoreTestCase {
    public void testCancelSearch() {
        SearchPageObject searchPageObject = new SearchPageObject(this.driver);

        searchPageObject.initializeSearch();
        final String searchText = "Java";
        searchPageObject.enterSearchText(searchText);
        searchPageObject.waitForSearchResultsPresent();
        searchPageObject.closeSearch();
        searchPageObject.waitForSearchResultsNotPresent();
    }
}
