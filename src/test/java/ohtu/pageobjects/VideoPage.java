package ohtu.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class VideoPage extends PageObject {

    public VideoPage(WebDriver driver, String baseUrl) {
        super(driver, baseUrl);
    }

    public WebElement deleteButton() {
        return driver.findElement(By.name("poispois"));
    }

}
