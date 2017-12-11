package ohtu.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class VideoEditPage extends PageObject {


    public VideoEditPage(WebDriver driver, String baseUrl) {
        super(driver, baseUrl);
    }

    public void inputTitle(String title) {
        clearFieldAndInput(titleField(), title);
    }

    public WebElement titleField() {
        return driver.findElement(By.name("title"));
    }

    public WebElement sendButton() {
        return driver.findElement(By.name("send"));
    }

}
