# Casos Gherkin QA - add-products-in-cart-screenplay

## Estado de entrada
- Spec fuente: .github/specs/add-products-in-cart-screenplay.spec.md
- Estado de ejecución observado: 1 escenario ejecutado, 1 fallido
- Falla principal: localización de Continue Shopping en modal

## Flujos críticos

```gherkin
# language: es
Característica: Agregar dos productos al carrito desde la página de productos
  Como comprador
  Quiero agregar dos productos distintos
  Para validar presencia en carrito y coherencia de precio, cantidad y total

  @smoke @critico @happy-path
  Escenario: Agregar primer y segundo producto y ver carrito
    Dado que el comprador está en la página principal
    Cuando navega a Productos
    Y agrega el primer producto al carrito
    Y confirma Continue Shopping en el modal
    Y agrega el segundo producto al carrito
    Y selecciona View Cart
    Entonces el carrito muestra ambos productos
    Y cada producto tiene cantidad 1
    Y el total por producto coincide con precio unitario por cantidad

  @critico @error-path
  Escenario: Modal visible pero botón Continue Shopping no localizable
    Dado que el comprador agregó un producto al carrito
    Cuando se espera el botón Continue Shopping con un selector inválido o no estable
    Entonces la automatización falla por elemento no visible
    Y el flujo de negocio no puede continuar al segundo agregado

  @edge-case
  Escenario: Persistencia del primer producto al agregar el segundo
    Dado que el primer producto ya fue agregado
    Cuando se agrega un segundo producto desde la grilla
    Entonces el primer producto se mantiene en el carrito
    Y la cantidad de ambos productos permanece en 1
```

## Datos de prueba sugeridos

| Escenario | Campo | Válido | Inválido | Borde |
|---|---|---|---|---|
| Happy path 2 productos | dataset_carrito | carrito_base_2_items | dataset inexistente | dataset con 1 solo item |
| Error path Continue Shopping | selector_continue_shopping | botón visible en modal activo | selector sin contexto de modal | selector ambiguo con múltiples matches |
| Persistencia del carrito | producto_1 / producto_2 | producto_1 y producto_2 | key de producto no existente | producto_1 == producto_2 |

## Evidencia base
- Feature: src/test/resources/features/add_products_in_cart.feature
- Step definitions: src/test/java/com/screenplay/stepdefinitions/AddProductsInCartStepDef.java
- Reporte de falla: build/reports/tests/test/classes/Agregar#20productos#20al#20carrito.html
