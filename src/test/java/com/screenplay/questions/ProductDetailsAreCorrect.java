package com.screenplay.questions;

import com.screenplay.config.TestConstants;
import com.screenplay.config.UiLabels;
import com.screenplay.ui.ProductPageTargets;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public final class ProductDetailsAreCorrect implements Question<Boolean> {

    @Override
    public Boolean answeredBy(Actor actor) {
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();
        List<WebElement> rows = driver.findElements(By.cssSelector(ProductPageTargets.CART_ROWS_CSS));

        for (WebElement row : rows) {
            String price = row.findElement(By.cssSelector(ProductPageTargets.CART_PRICE_IN_ROW_CSS))
                              .getText().trim();
            String quantity = row.findElement(By.cssSelector(ProductPageTargets.CART_QTY_IN_ROW_CSS))
                                 .getText().trim();
            String total = row.findElement(By.cssSelector(ProductPageTargets.CART_TOTAL_IN_ROW_CSS))
                              .getText().trim();

            Assertions.assertThat(quantity)
                      .as(UiLabels.ASSERT_QUANTITY + TestConstants.EXPECTED_QUANTITY_PER_ITEM)
                      .isEqualTo(String.valueOf(TestConstants.EXPECTED_QUANTITY_PER_ITEM));

            Assertions.assertThat(total)
                      .as(UiLabels.ASSERT_TOTAL_EQUALS_PRICE)
                      .isEqualTo(price);
        }
        return true;
    }

    public static ProductDetailsAreCorrect forAllCartItems() {
        return new ProductDetailsAreCorrect();
    }
}
