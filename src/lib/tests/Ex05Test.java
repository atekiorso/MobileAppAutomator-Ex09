package lib.tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.ReadingListPageObject;
import lib.ui.SearchPageObject;

// Тест из задания Ex5 после рефакторинга под паттерн PageObject
public class Ex05Test extends CoreTestCase {
    private SearchPageObject searchPageObject;
    private ArticlePageObject articlePageObject;
    private ReadingListPageObject readingListPageObject;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        searchPageObject = new SearchPageObject(this.driver);
        articlePageObject = new ArticlePageObject(this.driver);
        readingListPageObject = new ReadingListPageObject(this.driver);
    }

    public void testAddAndDeleteArticlesInReadingList() {
        // Добавляем первую статью в новый лист чтения
        searchPageObject.initializeSearch();
        final String searchText = "star wars";
        searchPageObject.enterSearchText(searchText);
        final String firstArticleTitle = "Star Wars";
        searchPageObject.openArticleFromSearchResults(firstArticleTitle);
        articlePageObject.callMoreOptionsMenu();
        articlePageObject.callAddToReadingListAction();
        final String readingListName = "Star Wars Reading List";
        readingListPageObject.addArticleToNewReadingList(readingListName);
        articlePageObject.closeArticle();

        // Добавляем вторую статью в уже созданный лист чтения
        searchPageObject.initializeSearch();
        searchPageObject.enterSearchText(searchText);
        final String secondArticleTitle = "Star Wars (film)";
        searchPageObject.openArticleFromSearchResults(secondArticleTitle);
        articlePageObject.callMoreOptionsMenu();
        articlePageObject.callAddToReadingListAction();
        readingListPageObject.addArticleToExistingReadingList(readingListName);
        articlePageObject.closeArticle();

        // Удаляем первую статью из листа чтения, проверяем, что в нем осталась только вторая статья
        readingListPageObject.openMyLists();
        readingListPageObject.removeArticleFromReadingList(readingListName, firstArticleTitle);
        assertFalse(readingListPageObject.isArticlePresentInCurrentReadingList(firstArticleTitle));
        assertTrue(readingListPageObject.isArticlePresentInCurrentReadingList(secondArticleTitle));

        // Открываем вторую статью и проверяем ее заголовок на соответствие ожидаемому значению
        readingListPageObject.openArticleFromCurrentReadingList(secondArticleTitle);
        final String openArticleTitle = articlePageObject.getOpenArticleTitle();
        assertEquals(secondArticleTitle, openArticleTitle);
    }
}
