package ohtu.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BooksPage extends PageObject {

    public BooksPage(WebDriver driver, String baseUrl) {
        super(driver, baseUrl);
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

    public WebElement submitButton() {
        return driver.findElement(By.name("submitbook"));
    }

    public WebElement bookInstanceLink() {
        return driver.findElement(By.name("bookInstance"));
    }

    public WebElement tagsFields() {
        return driver.findElement(By.name("tags"));
    }

    public WebElement bookLink(String title) {
        return driver.findElement(By.linkText(title));
    }

}
