package com.screenplay.ui;

import net.serenitybdd.screenplay.targets.Target;

public final class ProductPageTargets {

    private ProductPageTargets() {
    }

    public static final String ALL_PRODUCT_CARDS_CSS = ".features_items .product-image-wrapper";
    public static final String ADD_TO_CART_IN_CARD_CSS = ".product-overlay .add-to-cart";
    public static final String CART_ROWS_CSS = "#cart_info_table tbody tr";
    public static final String CART_PRICE_IN_ROW_CSS = ".cart_price p";
    public static final String CART_QTY_IN_ROW_CSS = ".cart_quantity button";
    public static final String CART_TOTAL_IN_ROW_CSS = ".cart_total p";
    public static final String CART_MODAL_SHOWN_CSS
            = "#cartModal.in, #cartModal.show, #cartModal[style*='display: block']";
    public static final String CONTINUE_SHOPPING_BUTTON_CSS
            = "#cartModal .modal-footer button.close-modal[data-dismiss='modal']";
    public static final String VIEW_CART_IN_MODAL_CSS = "#cartModal .modal-body a[href='/view_cart']";

    public static final Target HOME_PAGE_LOGO
            = Target.the("logo de la página principal")
                    .locatedBy("#header .logo a img[alt='Website for automation practice']");

    public static final Target PRODUCTS_NAV_BUTTON
            = Target.the("botón Productos en la navegación")
                    .locatedBy("a[href='/products']");

    public static final Target PRODUCTS_PAGE_TITLE
            = Target.the("título de la página de productos")
                    .locatedBy(".features_items h2");

    public static final Target CONTINUE_SHOPPING_BUTTON
            = Target.the("botón Continuar comprando del modal")
                    .locatedBy(CONTINUE_SHOPPING_BUTTON_CSS);

    public static final Target VIEW_CART_BUTTON_MODAL
            = Target.the("botón Ver carrito del modal")
                    .locatedBy("#cartModal a[href='/view_cart']");

    public static final Target CART_ROWS
            = Target.the("filas del carrito")
                    .locatedBy(CART_ROWS_CSS);

    public static final Target CART_PRODUCT_NAME
            = Target.the("nombre del producto en el carrito")
                    .locatedBy(".cart_description h4 a");

    public static final Target CART_PRODUCT_PRICE
            = Target.the("precio del producto en el carrito")
                    .locatedBy(".cart_price p");

    public static final Target CART_PRODUCT_QUANTITY
            = Target.the("cantidad del producto en el carrito")
                    .locatedBy(".cart_quantity button");

    public static final Target CART_PRODUCT_TOTAL
            = Target.the("total del producto en el carrito")
                    .locatedBy(".cart_total p");
}
