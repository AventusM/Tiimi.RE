package ohtu.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BookEditPage extends PageObject {

    public BookEditPage(WebDriver driver, String baseUrl) {
        super(driver, baseUrl);
    }

    public void inputAuthor(String author) {
        clearFieldAndInput(authorField(), author);
    }

    public void inputTitle(String title) {
        clearFieldAndInput(titleField(), title);
    }

    public void inputIsbn(String isbn) {
        clearFieldAndInput(isbnField(), isbn);
    }

    public void inputTags(String tags) {
        clearFieldAndInput(tagsField(), tags);
    }

    public WebElement sendButton() {
        return driver.findElement(By.name("send"));
    }

    public WebElement tagsField() {
        return driver.findElement(By.name("tags"));
    }

    public WebElement authorField() {
        return driver.findElement(By.name("author"));
    }

    public WebElement titleField() {
        return driver.findElement(By.name("title"));
    }

    public WebElement isbnField() {
        return driver.findElement(By.name("ISBN"));
    }

    public WebElement checkBox() {
        return driver.findElement(By.name("box"));
    }

}
