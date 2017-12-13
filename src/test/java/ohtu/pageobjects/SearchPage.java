package ohtu.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SearchPage extends PageObject {

    public SearchPage(WebDriver driver, String baseUrl) {
        super(driver, baseUrl);
    }

    public void goToPage() {
        driver.get(baseUrl + "search/");
    }

    public void inputTagSearchField(String tags) {
        clearFieldAndInput(tagSearchField(), tags);
    }

    public WebElement tagSearchButton() {
        return driver.findElement(By.name("tagsearchbutton"));
    }

    public WebElement tagSearchField() {
        return driver.findElement(By.name("tagsearchfield"));
    }

}
