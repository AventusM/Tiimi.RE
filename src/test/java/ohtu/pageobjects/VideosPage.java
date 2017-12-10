package ohtu.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class VideosPage extends PageObject {

    public VideosPage(WebDriver driver, String baseUrl) {
        super(driver, baseUrl);
    }

    public void goToPage() {
        driver.get(baseUrl + "videos");
    }

    public WebElement videoLink(String videoTitle) {
        return driver.findElement(By.linkText(videoTitle));
    }

    public WebElement titleField() {
        return driver.findElement(By.name("title"));
    }

    public WebElement urlField() {
        return driver.findElement(By.name("url"));
    }

    public WebElement submitButton() {
        return driver.findElement(By.name("submitvideo"));
    }

}
