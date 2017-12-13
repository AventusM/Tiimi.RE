package ohtu.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SearchPage extends PageObject {

    public SearchPage(WebDriver driver, String baseUrl) {
        super(driver, baseUrl);
    }

    public void goToPage() {
        driver.get(baseUrl + "search");
    }

    public void inputSearchField(String input) {
        clearFieldAndInput(SearchField(), input);
    }

    public WebElement tagSearchButton() {
        return driver.findElement(By.name("tagsearchbutton"));
    }

    public WebElement titleSearchButton() {
        return driver.findElement(By.name("titlesearchbutton"));
    }

    public WebElement SearchField() {
        return driver.findElement(By.name("tagsearch"));
    }

}
