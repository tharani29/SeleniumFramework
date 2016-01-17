package selenium.modules;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.testng.ITestContext;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class WebDriverFactory extends RemoteWebDriver  {
	
	WebElementServer elementServer = new WebElementServer();
	static DesiredCapabilities capability = DesiredCapabilities.firefox();
	Integer timeout=5;
    String test;
    DateFormat dateFormat = new SimpleDateFormat("MM.dd.HH.mm.ss");
    //get current date time with Date()
    Date date = new Date();
    public static String group;
    public static WebDriver driver;


    //For remote Selenium Server
    /*
    public static RemoteWebDriver driver;
    public static String getHost(){
		return System.getProperty("seleniumHost");
	}

	public static RemoteWebDriver createRemoteDriver() throws MalformedURLException {
		System.out.println("Accessing selenium host: " + getHost());
        driver = new RemoteWebDriver(new URL(getHost()), capability);
        if (driver==null){
            org.testng.Assert.fail("Driver could not be retrieved, ensure selenium server is running");
        }
		return driver;
	}*/
    public static WebDriver createDriver() {
        driver = new FirefoxDriver();
        return  driver;
    }
	public WebDriver getDriver(){
		return driver;
	}
    public RemoteWebDriver getTestName(String testName){
        test=testName;
        return this;
    }
			
	public WebDriverFactory getPage(String url) throws MalformedURLException{
        createDriver().get(url);
		return this;
	}

	public WebElement findMyElement(String elementName) throws IOException {	
		String[] elementData = retrieveElement(elementName);
		WebElement myElement = null;

		if (elementData[1].equals("id")){
			WebDriverWait wait = new WebDriverWait(getDriver(), timeout);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(elementData[0])));
			wait.until(ExpectedConditions.elementToBeClickable(By.id(elementData[0])));
			myElement= getDriver().findElement(By.id(elementData[0]));
            elementHighlight(myElement);
		}
		else if (elementData[1].equals("xpath")){
			WebDriverWait wait = new WebDriverWait(getDriver(), timeout);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(elementData[0])));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(elementData[0])));
			myElement= getDriver().findElement(By.xpath(elementData[0]));
            elementHighlight(myElement);
		}
		return myElement;
	}

    /**
     * Used when there are multiple identical elements in a page.
     * @param relative The element that you want to use as a reference
     * @param elementName The elementName that you want to interact with. The xpath Must be related to the relative element
     */
	public WebElement findMyElementWithRelative(WebElement relative, String elementName) throws IOException {
		String[] elementData = retrieveElement(elementName);

		WebElement myElement = null;

        if (elementData[1].equals("id")){
            WebDriverWait wait = new WebDriverWait(getDriver(), timeout);
            wait.until(ExpectedConditions.visibilityOf(relative.findElement(By.id(elementData[0]))));
			wait.until(ExpectedConditions.elementToBeClickable(relative.findElement(By.id(elementData[0]))));
            myElement= relative.findElement(By.id(elementData[0]));
            elementHighlight(myElement);
        }
        else if (elementData[1].equals("xpath")){
            WebDriverWait wait = new WebDriverWait(getDriver(), timeout);
            wait.until(ExpectedConditions.visibilityOf(relative.findElement(By.xpath(elementData[0]))));
			wait.until(ExpectedConditions.elementToBeClickable(relative.findElement(By.xpath(elementData[0]))));
            myElement=relative.findElement(By.xpath(elementData[0]));
            elementHighlight(myElement);
        }
		return myElement;
	}

    /**
     * Used when there are identical elements with different text. e.g. the rows of a table
     * @param elementName The html element that contains the text
     * @param text The text you are trying to locate
     * @return
     * @throws IOException
     */
    public WebElement findMyElementWithText(String elementName, String text) throws IOException {
        String[] elementData = retrieveElement(elementName);
        List<WebElement> elementList = new ArrayList<>();
        WebElement myElement = null;

        if (elementData[1].equals("id")){
            WebDriverWait wait = new WebDriverWait(getDriver(), timeout);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(elementData[0])));
			wait.until(ExpectedConditions.elementToBeClickable(By.id(elementData[0])));
            elementList= getDriver().findElements(By.id(elementData[0]));
        }
        else if (elementData[1].equals("xpath")){
            WebDriverWait wait = new WebDriverWait(getDriver(), timeout);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(elementData[0])));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(elementData[0])));
            elementList= getDriver().findElements(By.xpath(elementData[0]));
        }
        if ( elementList.size()>0) {

            for (int i = 0; i < elementList.size(); i++) {

                if (elementList.get(i).getText().equals(text))
                    myElement = elementList.get(i);
                if (myElement != null) {
                    elementHighlight(myElement);
                    break;
                }
            }
        }
        if (myElement==null){
            System.out.println("    No element with text: " + text + " was found");
        }
        return myElement;
    }
	public String[] retrieveElement(String str) throws IOException{
		return elementServer.getElement(str);
	}

	public WebDriverFactory closeDriver() throws MalformedURLException{
			getDriver().close();
		return this;
	}
    public WebDriverFactory selectElement(WebElement elementName,String option) throws IOException{
        Select select = new Select(elementName);
        select.selectByVisibleText(option);
        return this;
    }
        public void perform(Runnable action, final Callable<Boolean> expectation) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(getDriver())
                .withTimeout(5000, TimeUnit.MILLISECONDS)
                .pollingEvery(250, TimeUnit.MILLISECONDS);

        wait.until(new ExpectedCondition<Boolean>() {
                       public Boolean apply(WebDriver wd) {

                           action.run();

                           try {
                               return expectation.call();
                           } catch (Exception e) {
                               System.out.println("    Attempting to find element again");
                           }
                           return false;
                       }
                   }
        );
    }
	public void takeScreenshot () throws IOException{

			File scrFile = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File("screenshots/screenshot" + test+date + ".png"));
	}

    public Boolean elementExists (String elementName)throws IOException{
        String[] elementData = retrieveElement(elementName);
        List<WebElement> elementList = new ArrayList<>();
        Boolean exists = false;

        if (elementData[1].equals("id")){
            WebDriverWait wait = new WebDriverWait(getDriver(), timeout);
            try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(elementData[0])));
            elementList= getDriver().findElements(By.id(elementData[0]));
            } catch (TimeoutException e) {
            }

        }
        else if (elementData[1].equals("xpath")){
            WebDriverWait wait = new WebDriverWait(getDriver(), timeout);
            try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(elementData[0])));
            elementList= getDriver().findElements(By.xpath(elementData[0]));
            } catch (TimeoutException e) {
            }
        }
        if ( elementList.size()>0) {
            exists=true;
        }
        return exists;
    }
    public void elementHighlight(WebElement element) {
        for (int i = 0; i < 2; i++) {
            JavascriptExecutor js = (JavascriptExecutor) getDriver();
            js.executeScript(
                    "arguments[0].setAttribute('style', arguments[1]);",
                    element, "color: red; border: 3px solid red;");
            js.executeScript(
                    "arguments[0].setAttribute('style', arguments[1]);",
                    element, "");
        }
    }
    public void refreshBrowser (){
        getDriver().navigate().refresh();
    }
}
