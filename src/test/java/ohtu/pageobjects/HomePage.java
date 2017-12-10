package ohtu.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends PageObject {

    public HomePage(WebDriver driver, String baseUrl) {
        super(driver, baseUrl);
    }

    public WebElement booksLink() {
        return driver.findElement(By.linkText("Books"));
    }

    public WebElement videosLink() {
        return driver.findElement(By.linkText("Videos"));
    }

    public void goToPage() {
        driver.get(baseUrl);
    }

}
