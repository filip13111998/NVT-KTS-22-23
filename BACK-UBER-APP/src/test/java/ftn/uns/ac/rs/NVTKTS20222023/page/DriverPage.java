package ftn.uns.ac.rs.NVTKTS20222023.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DriverPage {

    private WebDriver driver;

    @FindBy(how = How.ID, using = "finish-button")
    private WebElement finishButton;

    @FindBy(how = How.ID, using = "notification-link-driver")
    private WebElement notificationLink;


    public DriverPage(WebDriver driver) {
        this.driver = driver;

        PageFactory.initElements(driver, this);

    }

    public void clickFinishButton() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.elementToBeClickable(finishButton)).click();

//        forgotButton.click();

    }

    public void notificationLink(){

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.elementToBeClickable(notificationLink)).click();

    }

    public boolean isDataEqual() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement current = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("current-text")));

        WebElement future = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("future-text")));

        return current.getText().equals(future.getText());
//        return element.isDisplayed();
    }

}
