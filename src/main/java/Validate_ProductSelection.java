import Pages.HomePage;
import Pages.SearchResultsPage;
import Pages.ShoppingCardSummaryPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.*;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Validate_ProductSelection {

    private WebDriver webDriver;

    @BeforeSuite
    public void beforeSuite() {
        WebDriverManager.chromedriver().setup();
        // WebDriverManager.firefoxdriver().setup();

    }

    @BeforeMethod
    public void beforeMethod() {
        webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        webDriver.get("http://automationpractice.com/index.php");
        webDriver.manage().timeouts().pageLoadTimeout(1500, SECONDS);
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
    }

    @BeforeTest
    public void BeforeTest(){
        SearchResultsPage searchResultsPage = new SearchResultsPage(webDriver);
    }

    @AfterMethod
    public void afterMethod() {
        webDriver.close();
        //webDriver.quit();
    }

    @DataProvider(name="searchData")
    public Object[][] searchData(){
        Object[][] data = new Object[1][1];
        data[0][0]="dress";
        //data[1][0]="jean";
        //data[1][0]="dress";
        return data;
    }

    public void waiting(int time){
        WebDriver.Timeouts waiting = webDriver.manage().timeouts().implicitlyWait(time, SECONDS);  //implicit wait
    }

    //Por que si pongo al wait dentro de una clase no me sirve como metodo ???? <-------------------------------------------------
    //public void waiting_exp(){
       // WebDriverWait wait_exp = new WebDriverWait(webDriver, 3000);
    //}

    public void pageLoad(int time){
        WebDriver.Timeouts pageLoad =  webDriver.manage().timeouts().pageLoadTimeout(time, SECONDS);
    }

    @Test(dataProvider="searchData")
    public void searchProduct(String keyword){
        HomePage homePage = new HomePage(webDriver);
        homePage.search(keyword);
        homePage.searchClick();
        pageLoad(5000);
    }

    @Test(dependsOnMethods = "searchProduct", alwaysRun = false, enabled = false)
    public void validateResultsNotFound(@Optional("banana") String keyword) {
        SearchResultsPage searchResultsPage = new SearchResultsPage(webDriver);
        searchProduct(keyword);
        waiting(1000);
        String contiene_resultados = searchResultsPage.validateResultsNotFound();
        Assert.assertEquals("No results were found for your search \"" +keyword+'"', contiene_resultados);
    }

    @Test(dataProvider="searchData")
    public void validateResultsFound(String keyword){
        SearchResultsPage searchResultsPage = new SearchResultsPage(webDriver);
        searchProduct(keyword);
        waiting(2000);
        boolean contiene_resultados = searchResultsPage.validateResultsFound();
        Assert.assertTrue(contiene_resultados, "No se han encontrado resultados");
        pageLoad(5000);
    }

    @Test(dataProvider="searchData")
    public void validateItemsCounter(String keyword){
        SearchResultsPage searchResultsPage = new SearchResultsPage(webDriver);

        validateResultsFound(keyword);
        String getCounterResults = searchResultsPage.getCounterResults().replace(" results have been found.", "");
        Assert.assertEquals(searchResultsPage.getListItemsSize(), Integer.parseInt(getCounterResults), "Los elementros encontrados no coinciden con los elementos contados");
        System.out.println("Existen "+getCounterResults+" encontrados con la palabra "+ keyword);
    }

   @Test(dataProvider="searchData")
    public void selectProduct(String keyword){
        SearchResultsPage searchResultsPage = new SearchResultsPage(webDriver);
        Actions actions = new Actions(webDriver);
        ShoppingCardSummaryPage shoppingCardSummaryPage = new ShoppingCardSummaryPage(webDriver);
        pageLoad(6000);

        validateResultsFound(keyword);
        String contiene = searchResultsPage.searchByKeyword(keyword);
        Assert.assertNotNull(contiene, "No existen productos con dicho nombre");

        searchResultsPage.sortByVisibleText("Price: Lowest first");
        pageLoad(2000);

        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("window.scrollBy(0,300)", "");
        pageLoad(1000);

        actions.moveToElement(searchResultsPage.setMousehover()).build().perform();
        actions.moveToElement(searchResultsPage.setMousehoverContainer()).build().perform();
        waiting(2000);

        //searchResultsPage.getClickAddToCard();
        actions.moveToElement(searchResultsPage.getClickAddToCard()).perform();
        waiting(2000);

        //searchResultsPage.getClickAddToCard().isEnabled();
        searchResultsPage.getClickAddToCard().click();

       // System.out.println("attribute: "+ searchResultsPage.getClickAddToCard());
        waiting(30000);

        WebElement layerVisibility = searchResultsPage.validateLayerVisibility();
        //Assert.assertTrue(layerVisibility, "No se ha abierto el popup");

        WebDriverWait wait_exp = new WebDriverWait(webDriver, 3000);
        wait_exp.until(ExpectedConditions.visibilityOf(layerVisibility));

        String productSeleccionado = searchResultsPage.validateLayerItemTittle();
        System.out.println("producto "+searchResultsPage.validateLayerItemTittle() + " compara "+searchResultsPage.getGetProductTittle());
        Assert.assertEquals(searchResultsPage.getGetProductTittle(), searchResultsPage.validateLayerItemTittle(), "El producto Aceptado no coincide con el producto seleccionado");
        waiting(30000);

        int articulosSeleccionados = searchResultsPage.getLayerTittleOfArticlesAdded();

        searchResultsPage.getProceedToCheckoutButton();

        shoppingCardSummaryPage.getSummaryTittle();
        Assert.assertEquals("SHOPPING-CART SUMMARY", shoppingCardSummaryPage.getSummaryTittle(), "El usuario no se encuentra en la pagina de Resumen");
        //searchResultsPage.getContinueShoppingButton();
        waiting(90000);

        int articulosTotal = shoppingCardSummaryPage.getTotalItems();
        Assert.assertEquals(articulosSeleccionados, articulosTotal, "La cantidad de elementos que ha seleccionado no coincide con la cantidad de elementos en el Resumen");

        String stepOne = "01. Summary";
        boolean stepOneFound = shoppingCardSummaryPage.validateStepOrder(stepOne);
        Assert.assertTrue(stepOneFound, "No se ha encontrado el Step");

        String producto = shoppingCardSummaryPage.setGetProductDescription();
        Assert.assertEquals(productSeleccionado, producto , "El producto seleccionado no es el mismo que se encuentra en la pagina del resumen");
        Assert.assertEquals("In stock", shoppingCardSummaryPage.validateStock(), "Este producto se encuentra sin stock");
        shoppingCardSummaryPage.setGetProceedToCheckoutButton();

        String stepTwo = "02. Sign in";
        boolean stepTwoFound = shoppingCardSummaryPage.validateStepOrder(stepTwo);
        Assert.assertTrue(stepTwoFound, "No se ha encontrado el Step");

        System.out.println("Fin");
    }
}
