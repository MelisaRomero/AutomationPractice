package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    @FindBy(id = "search_query_top")
    private WebElement search_input;

    @FindBy(how = How.NAME, using = "submit_search")
    private WebElement Search_Click_ButtonGlass;

    @FindBy(xpath = "//*[@id=\"header\"]/div[2]/div/div/nav/div[1]/a")
    private WebElement loginButton;

    public HomePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void search(String keyword){
        System.out.println("The following element has been Search: "+keyword);
        search_input.sendKeys(keyword);
    }

    public void searchClick(){
        Search_Click_ButtonGlass.click();
    }

    public void clickLoginButton(){
        loginButton.click();
    }


}
