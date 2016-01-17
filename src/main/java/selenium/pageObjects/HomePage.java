package selenium.pageObjects;

import selenium.modules.WebDriverFactory;
import java.io.IOException;

/**
 * Created by stratos on 17/01/2016.
 */
public class HomePage {
    WebDriverFactory webfactory = new WebDriverFactory();

    public HomePage Search(String fromFilter, String toFilter) throws IOException {
        webfactory.findMyElement("checkboxHotel").click();
        webfactory.findMyElement("textfieldFromFilter").clear();
        webfactory.findMyElement("textfieldFromFilter").sendKeys(fromFilter);
        webfactory.findMyElement("textfieldToFilter").clear();
        webfactory.findMyElement("textfieldToFilter").sendKeys(toFilter);
        webfactory.findMyElement("buttonSearch").click();
        webfactory.findMyElement("buttonSearch").click();
        return this;
    }

}
