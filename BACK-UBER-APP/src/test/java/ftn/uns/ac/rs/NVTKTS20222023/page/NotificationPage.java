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

public class NotificationPage {

    private WebDriver driver;

    @FindBy(how = How.ID, using = "notification-button")
    private WebElement notificationButton;

    public NotificationPage(WebDriver driver) {
        this.driver = driver;

        PageFactory.initElements(driver, this);

    }

    public void clickNotificationButton() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.elementToBeClickable(notificationButton)).click();

//        notificationButton.click();

    }

}
