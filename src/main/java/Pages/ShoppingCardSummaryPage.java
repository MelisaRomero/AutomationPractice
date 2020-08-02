package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCardSummaryPage {

    @FindBy(id = "cart_title")
    private WebElement h1SummaryPageTittle;

    @FindBy(xpath = "//*[@id=\"order_step\"]")
    private List<WebElement> getStepsListItems;

    @FindBy(xpath = "//*[@id=\"product_7_34_0_0\"]/td[2]/p/a")
    private  WebElement getProductDescription;

    @FindBy(xpath = "//*[@id=\"product_7_34_0_0\"]/td[3]/span")
    private WebElement getStockContainer;

    @FindBy(xpath = "//*[@id=\"center_column\"]/p[2]/a[1]")
    private WebElement getProceedToCheckoutButton;

    public ShoppingCardSummaryPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }
    public String split (String cadena, Integer start, Integer end) {
        String cadenaStr = cadena;
        String cadenaResult = cadenaStr.substring(start, end);
        return cadenaResult;
    }

    public String getSummaryTittle(){
        String TitleH1 = split(h1SummaryPageTittle.getText(), 0, 21);
        return TitleH1;
    }

    public int getTotalItems(){
        String Items = split(h1SummaryPageTittle.getText(), 51, 52);
        //System.out.println(h1SummaryPageTittle.getText());
        //System.out.println("items "+ Items);
        int TotalItems = Integer.parseInt(Items);
        //System.out.println("totall "+ TotalItems);
        return TotalItems;
    }

    public boolean validateStepOrder(String keywordStep){
        //System.out.println("lista: "+getStepsListItems.size());
        //System.out.println(getStepsListItems.get(0));
        String StepName = null;
        for (WebElement a: getStepsListItems) {
            if (a.getText().contains(keywordStep)) {
                //System.out.println("---> " + a + a.getText());
                StepName = a.getText();
                return true;
            }
        }
        return false;
    }

    public String setGetProductDescription(){
        //System.out.println("descripcion: "+getProductDescription.getText());
        return getProductDescription.getText();
    }

    public String validateStock(){
       // System.out.println("botonn "+getStockContainer.getText());
        return getStockContainer.getText();
    }

    public void setGetProceedToCheckoutButton(){
        getProceedToCheckoutButton.click();
    }

}
