package ohtu;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static java.lang.Thread.sleep;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import ohtu.pageobjects.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class Stepdefs {

    WebDriver driver = new HtmlUnitDriver();
    String baseUrl = "http://localhost:4567/";
    private HomePage homePage = new HomePage(driver, baseUrl);
    private BooksPage booksPage = new BooksPage(driver, baseUrl);
    private BookPage bookPage = new BookPage(driver, baseUrl);
    private BookEditPage bookEditPage = new BookEditPage(driver, baseUrl);
    private VideosPage videosPage = new VideosPage(driver, baseUrl);
    private VideoPage videoPage = new VideoPage(driver, baseUrl);
    private VideoEditPage videoEditPage = new VideoEditPage(driver, baseUrl);
    private SearchPage searchPage = new SearchPage(driver, baseUrl);
    private ReadBookMarksPage readBookMarksPage = new ReadBookMarksPage(driver, baseUrl);
    private UnReadBookMarksPage unReadBookMarksPage = new UnReadBookMarksPage(driver, baseUrl);

    public Stepdefs() {
        homePage.goToPage();
    }

    @Given("^books are selected$")
    public void books_are_selected() throws Throwable {
        homePage.booksLink().click();
    }

    @Given("^videos are selected")
    public void videos_are_selected() throws Throwable {
        homePage.videosLink().click();
    }

    @When("^author \"([^\"]*)\" and book name \"([^\"]*)\" and ISBN \"([^\"]*)\" are submitted")
    public void author_and_book_name_are_given(String author, String title, String ISBN) throws Throwable {
        booksPage.authorField().sendKeys(author);
        booksPage.titleField().sendKeys(title);
        booksPage.isbnField().sendKeys(ISBN);
        booksPage.submitButton().click();
    }

    @When("^author \"([^\"]*)\" and book name \"([^\"]*)\" and ISBN \"([^\"]*)\" are new values of the book")
    public void author_and_book_name_and_isbn_are_edited(String newAuthor, String newTitle, String newISBN) {
        bookEditPage.inputAuthor(newAuthor);
        bookEditPage.inputTitle(newTitle);
        bookEditPage.inputIsbn(newISBN);
        bookEditPage.sendButton().click();
    }

    @When("^book \"([^\"]*)\" is selected")
    public void book_name_is_selected(String title) throws InterruptedException {
        Thread.sleep(1000);
        booksPage.bookLink(title).click();
    }

    @Then("^book named \"([^\"]*)\" has been added$")
    public void user_has_added_new_bookmark(String title) throws Throwable {
        pageHasContent(title);
    }

    @Then("^book named \"([^\"]*)\" has not been added$")
    public void book_named_has_not_been_added(String arg1) throws Throwable {
        pageHasNoContent(arg1);
    }

    @Then("^book named \"([^\"]*)\" has been edited and its new name is \"([^\"]*)\"")
    public void user_has_changed_the_name_of_an_existing_bookmark(String originalTitle, String newTitle) {
        pageHasNoContent(originalTitle);
        pageHasContent(newTitle);
    }

    @Given("^new book has been added$")
    public void new_book_by_sipser_has_been_added() throws Throwable {
        homePage.booksLink().click();
        booksPage.titleField().sendKeys("Introduction to the Theory of Computation");
        booksPage.authorField().sendKeys("Michael Sipser");
        booksPage.isbnField().sendKeys("978-1133187790");
        booksPage.tagsFields().sendKeys("Laskennan mallit");
        booksPage.submitButton().click();
    }

    @When("^edit button is pressed")
    public void book_edit_button_has_been_pressed() {
        bookPage.editButton().click();
    }

    @When("^book is deleted$")
    public void book_is_deleted() throws Throwable {
        bookPage.deleteButton().click();
    }

    @Then("^book isn't listed$")
    public void book_isn_t_listed() throws Throwable {
        pageHasNoContent("Introduction to the Theory of Computation");
    }

    @Given("^book has been selected to be edit$")
    public void book_has_been_selected_to_be_edit() throws Throwable {
        booksPage.bookInstanceLink().click();
        bookPage.editButton().click();
    }

    @When("^user change title to \"([^\"]*)\"$")
    public void user_change_title_to(String title) throws Throwable {
        bookEditPage.inputTitle(title);
        bookEditPage.sendButton().click();
    }

    @When("^user change author to \"([^\"]*)\"$")
    public void user_change_author_to(String author) throws Throwable {
        bookEditPage.inputAuthor(author);
        bookEditPage.sendButton().click();
    }

    @When("^user change tags to \"([^\"]*)\"$")
    public void user_change_tags_to(String tags) throws Throwable {
        bookEditPage.inputTags(tags);
        bookEditPage.sendButton().click();
    }

    @When("^user change ISBN to \"([^\"]*)\"$")
    public void user_change_ISBN_to(String isbn) throws Throwable {
        bookEditPage.inputIsbn(isbn);
        bookEditPage.sendButton().click();
    }

    @Then("^book data contains \"([^\"]*)\"$")
    public void book_data_contains(String arg1) throws Throwable {
        pageHasContent(arg1);
    }

    @Then("^book by name \"([^\"]*)\" is not listed anymore$")
    public void book_by_name_is_not_listed_anymore(String title) throws Throwable {
        pageHasNoContent(title);
    }

    @When("^title \"([^\"]*)\" and video url \"([^\"]*)\" are submitted$")
    public void title_and_video_url_are_submitted(String title, String url) throws Throwable {
        videosPage.titleField().sendKeys(title);
        videosPage.urlField().sendKeys(url);
        videosPage.submitButton().click();
    }

    @Then("^video named \"([^\"]*)\" has been added$")
    public void video_named_has_been_added(String videoTitle) throws Throwable {
        pageHasContent(videoTitle);
    }

    @When("^video \"([^\"]*)\" is selected$")
    public void video_is_selected(String videoTitle) throws Throwable {
        videosPage.videoLink(videoTitle).click();
    }

    @When("^video is removed$")
    public void video_is_removed() throws Throwable {
        videoPage.deleteButton().click();
    }

    @Then("^video by name \"([^\"]*)\" is listed no more$")
    public void video_by_name_is_listed_no_more(String videoTitle) throws Throwable {
        pageHasNoContent(videoTitle);
    }

    @When("^new name \"([^\"]*)\" is given to the video$")
    public void new_name_is_given_to_the_video(String title) throws Throwable {
        videoEditPage.inputTitle(title);
        videoEditPage.sendButton().click();
    }

    @Then("^video is named \"([^\"]*)\"$")
    public void video_is_named(String newTitle) throws Throwable {
        pageHasContent(newTitle);
    }

    @When("^book has been set to read$")
    public void book_has_been_set_to_read() throws Throwable {
        if (!bookEditPage.checkBox().isSelected()) {
            bookEditPage.checkBox().click();
        }
        bookEditPage.sendButton().click();
    }

    @Then("^the book has been read$")
    public void the_book_has_been_read() throws Throwable {
        pageHasContent("yes");
    }

    @When("^search is selected$")
    public void search_is_selected() throws Throwable {
        searchPage.goToPage();
    }

    @Then("^query returns book by name \"([^\"]*)\"$")
    public void query_returns_book_by_name(String bookTitle) throws Throwable {
        pageHasContent(bookTitle);
    }

    @When("^the book has been marked read on its own page$")
    public void the_book_has_been_marked_read_on_its_own_page() throws Throwable {
        Thread.sleep(1000);
        //Jos kirja on jostain syystä luettu aikaisemmin, niin poistetaan muutos
        //Esim aiempi testi saattoi aiheuttaa tämän -> ratkaisuna ehkä tietokannan
        //clearaus ennen jokaista testiä?
        bookPage.markAsReadButton().click();
    }

    @Then("^viewing all read books shows that the book by name \"([^\"]*)\" has been read$")
    public void viewing_all_read_books_shows_that_the_book_by_name_has_been_read(String title) throws Throwable {
        readBookMarksPage.goToPage();
        pageHasContent(title);
    }

    @When("^book by name \"([^\"]*)\" is selected$")
    public void book_by_name_is_selected(String bookTitle) throws Throwable {
        Thread.sleep(1000);
        booksPage.bookLink(bookTitle).click();
    }

    @Then("^viewing all unread books shows that the book by name \"([^\"]*)\" has not been read$")
    public void viewing_all_unread_books_shows_that_the_book_by_name_has_not_been_read(String bookTitle) throws Throwable {
        unReadBookMarksPage.goToPage();
        pageHasContent(bookTitle);
    }

    @When("^video by title \"([^\"]*)\" is selected$")
    public void video_by_title_is_selected(String videoTitle) throws Throwable {
        videosPage.videoLink(videoTitle).click();
    }

    @When("^the video has been set to viewed on its own page$")
    public void the_video_has_been_set_to_viewed_on_its_own_page() throws Throwable {
        Thread.sleep(1000);
        videoPage.markAsWatchedButton().click();
    }

    @Then("^viewing all viewed videos shows that the video by name \"([^\"]*)\" has been viewed$")
    public void viewing_all_viewed_videos_shows_that_the_video_by_name_has_been_viewed(String videoTitle) throws Throwable {
        readBookMarksPage.goToPage();
        pageHasContent(videoTitle);
    }

    @When("^search is performed by keyword \"([^\"]*)\"$")
    public void search_is_performed_by_keyword(String tags) throws Throwable {
        searchPage.goToPage();
        searchPage.inputSearchField(tags);
        searchPage.tagSearchButton().click();
    }

    @When("^search is performed by title \"([^\"]*)\"$")
    public void search_is_performed_by_title(String title) throws Throwable {
        searchPage.goToPage();
        searchPage.inputSearchField(title);
        searchPage.titleSearchButton().click();
    }

    @When("^author \"([^\"]*)\" and book name \"([^\"]*)\" and ISBN \"([^\"]*)\" and tags \"([^\"]*)\" have been submitted$")
    public void author_and_book_name_and_ISBN_and_tags_have_been_submitted(String author, String title, String ISBN, String tags) throws Throwable {
        booksPage.authorField().sendKeys(author);
        booksPage.titleField().sendKeys(title);
        booksPage.isbnField().sendKeys(ISBN);
        booksPage.tagsFields().sendKeys(tags);
        booksPage.submitButton().click();
    }


    /* helper methods */
    private void pageHasContent(String content) {
        assertTrue(driver.getPageSource().contains(content));
    }

    private void pageHasNoContent(String content) {
        assertFalse(driver.getPageSource().contains(content));
    }

    @After
    public void tearDown() {
        driver.quit();
    }

}
