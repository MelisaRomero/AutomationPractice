package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class SearchResultsPage {
    // Resultados de la Busqueda
    @FindBy(xpath = "//ul[@class='product_list grid row']/li//h5/a")
    private List<WebElement> listitems;

    @FindBy(xpath = "//*[@id=\"center_column\"]/ul")
    private WebElement contentItemsContainer;

    @FindBy(xpath = "//*[@id=\"center_column\"]/h1/span[2]")
    private WebElement getCounterResults;

    @FindBy(xpath = "//*[@id=\"center_column\"]/ul/li[2]")
    private WebElement productSelected;

    @FindBy(xpath = "//*[@id=\"center_column\"]/ul/li[2]/div/div[2]/h5")
    private WebElement getProductTittle;

    @FindBy(xpath = "//*[@id=\"center_column\"]/p")
    private WebElement alertMessage;

    // Ordernar los Resultados
    @FindBy(id="selectProductSort" )
    private WebElement dropSortBySelector;

    // Seleccionar un Resultado
    @FindBy(xpath = "//*[@id='center_column']/ul/li[2]")
    private WebElement mouseHoverPositioning;

    @FindBy(xpath = "//*[@id='center_column']/ul/li[2]/div/div[2]/div[2]/a[1]")
    private WebElement clickAddToCardButton;

    @FindBy(xpath = "//*[@id='center_column']/ul/li[2]/div")
    private WebElement mouseHoverContainer;

    @FindBy(xpath = "//*[@id=\"center_column\"]/ul/li[2]/div/div[1]/div/div[2]/span[1]")
    private WebElement getPriceFromContainer;

    // Capa de Confirmacion de Producto Agregado
    @FindBy(id = "layer_cart")
    private WebElement layer;

    @FindBy(xpath = "//*[@id=\"layer_cart\"]/div[1]/div[1]/h2")
    private WebElement getLayerTittle;

    @FindBy(xpath = "//*[@id=\"layer_cart_product_title\"]")
    private WebElement getLayerItemTittle;

    @FindBy(xpath = "//*[@id=\"layer_cart\"]/div[1]/div[2]/div[4]/span")
    private WebElement getContinueShoppingButton;

    @FindBy(xpath = "//*[@id=\"layer_cart\"]/div[1]/div[2]/div[4]/a")
    private WebElement getProceedToCheckoutButton;

    @FindBy(xpath = "//*[@id=\"layer_cart\"]/div[1]/div[2]/h2")
    private WebElement getLayerTittleOfArticlesAdded;

    public SearchResultsPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public String getCounterResults(){
        return getCounterResults.getText();
    }

    public int getListItemsSize(){
        return listitems.size();
    }

    public String split (String cadena, Integer start, Integer end) {
      String cadenaStr = cadena;
      String cadenaResult = cadenaStr.substring(start, end);
      return cadenaResult;
    }

    //public boolean searchByKeyword(String keyword) {
    public String searchByKeyword(String keyword) {
        //System.out.println(listitems.size());
        String palabra = null;
        for (WebElement a : listitems) {
            if (a.getText().toLowerCase().contains(keyword)) {
                //System.out.println("se encontraron valores que matchearon " + a.getText());
                palabra = a.getText();
            }
        }
        return palabra;
    }

    public void sortByVisibleText(String text){
        Select select = new Select(dropSortBySelector);
        select.selectByVisibleText(text);
    }

    public void selectProduct(){
        System.out.println("product selected  "+productSelected.getText());
       /* Actions builder = new Actions(webDriver);

        builder.moveToElement(mousehover).perform();
        System.out.println("hover "+mousehover.isDisplayed());
        clickAddToCard.click();*/
        System.out.println("button  "+clickAddToCardButton.getText());
    }

    public WebElement setMousehover(){
        return mouseHoverPositioning;
    }

    public WebElement setMousehoverContainer(){
        return mouseHoverContainer;
    }

    public WebElement getClickAddToCard(){
        return clickAddToCardButton;
    }

    public String validateResultsNotFound(){
        return alertMessage.getText();
    }

    public boolean validateResultsFound() {
        return contentItemsContainer.isDisplayed();
    }

    public WebElement validateLayerVisibility(){
        layer.isDisplayed();
        layer.click();
        System.out.println(" titulo "+ getLayerTittle.getText());
        return layer;
    }

    public String validateLayerItemTittle(){
        return getLayerItemTittle.getText();
    }

    public String getGetProductTittle(){
        return getProductTittle.getText();
    }

    public void getContinueShoppingButton(){
        getContinueShoppingButton.click();
    }

    public int getLayerTittleOfArticlesAdded(){
        String articles = split(getLayerTittleOfArticlesAdded.getText(), 9, 10);
        int articlesTotal = Integer.parseInt(articles);
         return articlesTotal;
    }

    public void getProceedToCheckoutButton(){
        getProceedToCheckoutButton.click();
    }
}

