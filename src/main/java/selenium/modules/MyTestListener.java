package selenium.modules;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.IOException;

public class MyTestListener extends TestListenerAdapter {

    WebDriverFactory webFactory = new WebDriverFactory();

    @Override
    public void onTestFailure(ITestResult result){
        if (result.getStatus() == ITestResult.FAILURE) {
            try {
                webFactory.takeScreenshot();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
            }
