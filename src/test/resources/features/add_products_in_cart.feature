@AddProductsInCart @Screenplay
Feature: Agregar productos al carrito
  Como comprador que visita la tienda en línea
  Quiero agregar múltiples productos al carrito desde la página de productos
  Para verificar que los totales, cantidades y precios sean correctos

  Scenario Outline: Agregar dos productos al carrito y verificar detalle con datos reutilizables
    Given el comprador inicia la compra con el dataset "<dataset_carrito>"
    When agrega al carrito los productos "<producto_1>" y "<producto_2>"
    And continúa el flujo de compra definido para "<dataset_carrito>"
    Then el carrito refleja los productos esperados en "<dataset_carrito>"
    And los precios, cantidades y totales cumplen las reglas del dataset "<dataset_carrito>"

    Examples:
      | dataset_carrito      | producto_1 | producto_2 |
      | carrito_base_2_items | producto_1 | producto_2 |
