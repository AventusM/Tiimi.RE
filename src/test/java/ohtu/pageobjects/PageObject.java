package ohtu.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class PageObject {

    protected WebDriver driver;
    protected String baseUrl;

    public PageObject(WebDriver driver, String baseUrl) {
        this.driver = driver;
        this.baseUrl = baseUrl;
    }

    protected void clearFieldAndInput(WebElement element, String text) {
        element.clear();
        element.sendKeys(text);
    }

}
