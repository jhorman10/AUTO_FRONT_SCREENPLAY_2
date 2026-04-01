package com.screenplay.config;

public final class UiLabels {

    private UiLabels() {
    }

    public static final String ACTOR_NAME = "Comprador";

    public static final String STEP_NAVIGATE_HOME = "{0} navega a la página principal";
    public static final String STEP_NAVIGATE_PRODUCTS = "{0} navega a la página de productos";
    public static final String STEP_ADD_FIRST_PRODUCT = "{0} agrega el primer producto al carrito";
    public static final String STEP_ADD_SECOND_PRODUCT = "{0} agrega el segundo producto al carrito";
    public static final String STEP_ADD_PRODUCT = "{0} agrega un producto al carrito";
    public static final String STEP_CONTINUE_SHOPPING = "{0} hace clic en Continuar comprando";
    public static final String STEP_VIEW_CART = "{0} hace clic en Ver carrito";

    public static final String DATASET_NOT_FOUND = "Dataset no encontrado en classpath: ";
    public static final String DATASET_LOAD_ERROR = "Error al cargar el dataset: ";

    public static final String ASSERT_CART_COUNT = "El carrito debe contener el número esperado de productos";
    public static final String ASSERT_QUANTITY = "La cantidad del producto debe ser ";
    public static final String ASSERT_TOTAL_EQUALS_PRICE
            = "El total del producto debe coincidir con el precio unitario cuando la cantidad es 1";
}
