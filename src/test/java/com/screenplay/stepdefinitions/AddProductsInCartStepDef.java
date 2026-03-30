package com.screenplay.stepdefinitions;

import com.screenplay.config.CartDataLoader;
import com.screenplay.config.CartDataset;
import com.screenplay.config.TestConstants;
import com.screenplay.config.UiLabels;
import com.screenplay.questions.CartContainsProducts;
import com.screenplay.questions.ProductDetailsAreCorrect;
import com.screenplay.tasks.AddFirstProductToCart;
import com.screenplay.tasks.AddSecondProductToCart;
import com.screenplay.tasks.ContinueShopping;
import com.screenplay.tasks.NavigateToHomePage;
import com.screenplay.tasks.NavigateToProductsPage;
import com.screenplay.tasks.ViewCart;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class AddProductsInCartStepDef {

    private Actor comprador;

    @Before
    public void setStage() {
        OnStage.setTheStage(new OnlineCast());
    }

    @Given("el comprador inicia la compra con el dataset {string}")
    public void elCompradorIniciaLaCompraConElDataset(String datasetKey) {
        CartDataset dataset = CartDataLoader.load(datasetKey);
        comprador = OnStage.theActorCalled(UiLabels.ACTOR_NAME);
        comprador.remember(TestConstants.KEY_DATASET, dataset);
        comprador.attemptsTo(NavigateToHomePage.now());
    }

    @When("agrega al carrito los productos {string} y {string}")
    public void agregaAlCarritoLosProductos(String producto1Key, String producto2Key) {
        comprador.attemptsTo(
                NavigateToProductsPage.now(),
                AddFirstProductToCart.now(),
                ContinueShopping.now(),
                AddSecondProductToCart.now()
        );
    }

    @And("continúa el flujo de compra definido para {string}")
    public void continuaElFlujoDCompraDefinidoPara(String datasetKey) {
        comprador.attemptsTo(ViewCart.now());
    }

    @Then("el carrito refleja los productos esperados en {string}")
    public void elCarritoReflejaLosProductosEsperadosEn(String datasetKey) {
        CartDataset dataset = comprador.recall(TestConstants.KEY_DATASET);
        comprador.should(
                seeThat(UiLabels.ASSERT_CART_COUNT,
                        CartContainsProducts.inTheCart(),
                        equalTo(dataset.getExpectedCartItemCount()))
        );
    }

    @And("los precios, cantidades y totales cumplen las reglas del dataset {string}")
    public void losPreciosCantidadesYTotalesCumplenLasReglas(String datasetKey) {
        comprador.should(
                seeThat(UiLabels.ASSERT_TOTAL_EQUALS_PRICE,
                        ProductDetailsAreCorrect.forAllCartItems(),
                        is(true))
        );
    }
}
