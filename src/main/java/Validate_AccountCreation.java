import Pages.CreateAccountPage;
import Pages.HomePage;
import com.beust.jcommander.Parameter;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.managers.FirefoxDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.*;

import static io.github.bonigarcia.wdm.WebDriverManager.*;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Validate_AccountCreation {

    private WebDriver webDriver;

    @BeforeSuite
    //@Parameters({"browser"})
   // public void beforeSuite(@Optional("firefox") String browser)  {
     public void beforeSuite(ITestContext context){
        String seleniumBrowser = context.getCurrentXmlTest().getParameter("browser");
        System.out.println(" aqui "+seleniumBrowser);
        try {
            chromedriver().setup();
            System.out.println("browser entre "+seleniumBrowser);
        } catch (Exception e) {
            firefoxdriver().setup();
            System.out.println("browser entre F "+seleniumBrowser);
        }

    }

    @BeforeMethod
    public void beforeMethod(ITestContext context){
        String seleniumBrowser = context.getCurrentXmlTest().getParameter("browser");
        
        try {
            webDriver = new ChromeDriver();
        } catch (Exception e) {
            webDriver = new FirefoxDriver();
        }
        webDriver.manage().window().maximize();
        webDriver.get("http://automationpractice.com/index.php");
    }

    @AfterMethod
    public void afterMethod() {
        webDriver.close();
        //webDriver.quit();
    }

    public void waiting(int time){
        WebDriver.Timeouts waiting = webDriver.manage().timeouts().implicitlyWait(time, SECONDS);  //implicit wait
    }
    public void waiting_exp(){
        WebDriverWait wait_exp = new WebDriverWait(webDriver, 1000);
    }

    public void pageLoad(int time){
        WebDriver.Timeouts pageLoad =  webDriver.manage().timeouts().pageLoadTimeout(time, SECONDS);
    }

    @Test
    public void Create_Account() throws InterruptedException {
        CreateAccountPage createAccountPage = new CreateAccountPage(webDriver);
        HomePage homePage = new HomePage(webDriver);

        homePage.clickLoginButton();
        Thread.sleep(4000);
        createAccountPage.createAnAccountForm();
        createAccountPage.submitCreateButton();
        Thread.sleep(4000);
        Assert.assertTrue(createAccountPage.createAnAccountFormTittle(), "El Usuario no ha ingresado a la pagina" );
        System.out.println("El usuario ha ingresado a la pagina y puede completar el formulario para registrarse");
    }

    @Test
    public void login(){
        CreateAccountPage createAccountPage = new CreateAccountPage(webDriver);
        HomePage homePage = new HomePage(webDriver);

        homePage.clickLoginButton();
        waiting(1000);
        createAccountPage.completeLoginForm();
        waiting(1000);
        createAccountPage.submitLoginButton();
        pageLoad(4000);
        boolean loginuser = createAccountPage.validateAccount();
        Assert.assertFalse(loginuser, "El usuario no se ha podido Loguear");
        System.out.println("El usuario se ha podido loguear correctamente");
    }
}
