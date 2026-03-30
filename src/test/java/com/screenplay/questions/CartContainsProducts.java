package com.screenplay.questions;

import com.screenplay.ui.ProductPageTargets;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.By;

public final class CartContainsProducts implements Question<Integer> {

    @Override
    public Integer answeredBy(Actor actor) {
        return BrowseTheWeb.as(actor)
                           .getDriver()
                           .findElements(By.cssSelector(ProductPageTargets.CART_ROWS_CSS))
                           .size();
    }

    public static CartContainsProducts inTheCart() {
        return new CartContainsProducts();
    }
}
