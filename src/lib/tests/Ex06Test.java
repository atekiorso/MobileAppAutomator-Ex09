package lib.tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;

// Тест из задания Ex6 после рефакторинга под паттерн PageObject
public class Ex06Test extends CoreTestCase {
    private SearchPageObject searchPageObject;
    private ArticlePageObject articlePageObject;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        searchPageObject = new SearchPageObject(this.driver);
        articlePageObject = new ArticlePageObject(this.driver);
    }

    public void testFindArticleTitleWithoutWaiting() {
        searchPageObject.initializeSearch();
        final String searchText = "android";
        searchPageObject.enterSearchText(searchText);
        searchPageObject.waitForSearchResultsPresent();
        final String articleName = "Android (operating system)";
        searchPageObject.openArticleFromSearchResults(articleName);

        articlePageObject.findArticleTitleWithoutWaiting();
    }
}
