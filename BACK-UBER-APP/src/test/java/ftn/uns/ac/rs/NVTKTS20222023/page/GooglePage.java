package ftn.uns.ac.rs.NVTKTS20222023.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GooglePage {

    private WebDriver driver;


    public GooglePage(WebDriver driver) {

        this.driver = driver;

        PageFactory.initElements(driver, this);

    }

    public void clickVaskeUser() {

        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='vaskebonf22@gmail.com']")));

        element.click();

    }

    public boolean isHomeUrl(){

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Wait for the URL to be updated to the expected value
        wait.until(ExpectedConditions.urlToBe("http://localhost:4200/citizen-home"));

        return driver.getCurrentUrl().contains("home");
    }

}
