package selenium.Tests;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.*;
import org.testng.annotations.Listeners;
import selenium.modules.MyTestListener;
import selenium.modules.ReadPropertiesFile;
import selenium.modules.WebDriverFactory;
import selenium.pageObjects.HomePage;
import selenium.pageObjects.SearchResultsPage;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by stratos on 17/01/2016.
 */
@Listeners({MyTestListener.class})
public class SortByPriceTest {
    String fromFilter= "Berlin";
    String toFilter= "Prague";
    String url= ReadPropertiesFile.main("environmentUrl");
    WebDriverFactory webFactory = new WebDriverFactory();
    HomePage homePage = new HomePage();
    SearchResultsPage searchResultsPage = new SearchResultsPage();
    @BeforeTest
    public void setUp(final ITestContext testContext) throws Exception {
        System.out.println("*** Running: " + testContext.getName() + " ***"); // prints the name of the test
        webFactory.getPage(url);
    }
    @Test
    public void searchFare() throws IOException{
        homePage.Search(fromFilter,toFilter);
    }
    @Test(dependsOnMethods = { "searchFare" })
    public void sortByPrice() throws IOException {
        Boolean sorted=true;
        float[] currencies = searchResultsPage.SortByPrice();
        for (int i = 0; i < currencies.length-1; i++) {
            if (currencies[i] > currencies[i+1]){
                sorted=false;
            }
        }
        Assert.assertTrue(sorted);
    }
    @AfterTest
    public void tearDown() throws MalformedURLException {
        webFactory.closeDriver();
    }
}
