package ftn.uns.ac.rs.NVTKTS20222023.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class ForgotPage {

    private WebDriver driver;

    @FindBy(how = How.ID, using = "exampleInputUsername")
    private WebElement usernameField;

    @FindBy(how = How.ID, using = "exampleInputPassword")
    private WebElement passwordField;

    @FindBy(how = How.ID, using = "exampleInputConfirm")
    private WebElement confirmField;

    @FindBy(how = How.ID, using = "exampleFormControlSelect1")
    private WebElement selectField;

    @FindBy(how = How.ID, using = "reset-button")
    private WebElement resetButton;

    public ForgotPage(WebDriver driver) {
        this.driver = driver;

        PageFactory.initElements(driver, this);
    }

    public void enterForm(String username, String password , String confirm , String role) {

        usernameField.clear();

        usernameField.sendKeys(username);

        passwordField.clear();

        passwordField.sendKeys(password);

        confirmField.clear();

        confirmField.sendKeys(confirm);

        // Create a Select object
        Select dropdown = new Select(selectField);

        // Select an option by visible text
        dropdown.selectByVisibleText(role);

    }

    public void clickResetButton() {

        resetButton.click();

    }

    public boolean isResetPassworUrl(){
        return driver.getCurrentUrl().contains("reset-password");
    }

}
