package ohtu.pageobjects;

import org.openqa.selenium.WebDriver;

public class ReadBookMarksPage extends PageObject {

    public ReadBookMarksPage(WebDriver driver, String baseUrl) {
        super(driver, baseUrl);
    }

    public void goToPage() {
        driver.get(baseUrl + "read");
    }

}
