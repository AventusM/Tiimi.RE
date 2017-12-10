package ohtu.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BookPage extends PageObject {

    public BookPage(WebDriver driver, String baseUrl) {
        super(driver, baseUrl);
    }

    public WebElement editButton() {
        return  driver.findElement(By.name("editbutton"));
    }

    public WebElement deleteButton() {
        return driver.findElement(By.name("poispois"));
    }

    public void goToPage(long id) {
        driver.get(baseUrl + "books/" + id);
    }

}
