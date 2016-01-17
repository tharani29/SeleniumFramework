package selenium.pageObjects;

/**
 * Created by stratos on 17/01/2016.
 */

import org.openqa.selenium.WebElement;
import selenium.modules.WebDriverFactory;
import java.io.IOException;
import java.util.List;

public class SearchResultsPage {
    WebDriverFactory webfactory = new WebDriverFactory();

    public float[] SortByPrice() throws IOException {
        webfactory.findMyElement("buttonSortByPrice").click();
        List<WebElement> beforeCommaList = webfactory.findAllElements("labelCurrencyBeforecomma");
        List<WebElement> decimalsList = webfactory.findAllElements("labelCurrencyDecimals");
        List<Float> currencyList = null;
        float[] currencies;
        currencies = new float[beforeCommaList.size()];
        for (int i = 0; i < beforeCommaList.size(); i++) {
            currencies[i]=formatCurrency(beforeCommaList.get(i).getText(),decimalsList.get(i).getText());
        }
        return currencies;
    }

    public Float formatCurrency(String beforeComma, String Decimals ){
        int firstPart = Integer.parseInt(beforeComma);
        float decimals = Float.parseFloat(Decimals.replaceAll("\\D+",""))/100;
        float finalCurrency = firstPart+decimals;
        return finalCurrency;
    }
}
