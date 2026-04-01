@AddProductsInCart @Screenplay
Feature: Agregar productos al carrito
  Como comprador que visita la tienda en línea
  Quiero agregar múltiples productos al carrito desde la página de productos
  Para verificar que los totales, cantidades y precios sean correctos

  Scenario Outline: Agregar los productos definidos por el dataset y verificar el carrito
    Given el dataset "<dataset_carrito>" está cargado para el comprador
    When los productos definidos por el dataset son añadidos al carrito
    Then el carrito contiene los productos esperados para "<dataset_carrito>"
    And los precios, cantidades y totales son consistentes con "<dataset_carrito>"

    Examples:
      | dataset_carrito      |
      | carrito_base_2_items |
