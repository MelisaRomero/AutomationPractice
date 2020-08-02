package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreateAccountPage {

    @FindBy(xpath = "//*[@id=\"header\"]/div[2]/div/div/nav/div[1]/a")
    private WebElement loginButton;

    //Authentication Page
    //user already registered
    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "passwd")
    private WebElement passwordInput;

    @FindBy(id = "SubmitLogin")
    private WebElement loginButtonClick;

    //@FindBy(className = "alert alert-danger")
    @FindBy(xpath = "//*[@id=\"center_column\"]/div[1]")
    private WebElement alertaMessage;

    //user creation
    @FindBy(id = "email_create")
    private WebElement emailCreateInput;

    @FindBy(id = "SubmitCreate")
    private WebElement submitCreateButton;

    //Create an Account Page
    @FindBy(xpath = "//*[@id=\"account-creation_form\"]/div[1]/h3")
    private WebElement h3Tittle;

    public CreateAccountPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void clickLoginButton(){
        loginButton.click();
    }

    public void completeLoginForm() {
        emailInput.sendKeys("melisa_stefania@hotmail.com");
        passwordInput.sendKeys("Password");
    }

    public boolean validateAccount() {
        alertaMessage.isDisplayed();
        System.out.println(alertaMessage.getText());
        return true;
    }

    public void submitLoginButton(){
        loginButtonClick.click();
    }

    public void createAnAccountForm(){
        emailCreateInput.sendKeys("melisa_stefania@hotmail.com");
    }

    public void submitCreateButton(){
        submitCreateButton.click();
    }

    public boolean createAnAccountFormTittle(){
        h3Tittle.isDisplayed();
        System.out.println(h3Tittle.getText());
        return true;
    }

}