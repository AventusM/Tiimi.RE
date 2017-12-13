package ohtu.pageobjects;

import org.openqa.selenium.WebDriver;

public class UnReadBookMarksPage extends PageObject {

    public UnReadBookMarksPage(WebDriver driver, String baseUrl) {
        super(driver, baseUrl);
    }

    public void goToPage() {
        driver.get(baseUrl + "unread");
    }

}
