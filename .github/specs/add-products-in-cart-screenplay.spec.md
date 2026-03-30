---
id: SPEC-001
status: IMPLEMENTED
feature: add-products-in-cart-screenplay
created: 2026-03-27
updated: 2026-03-29
author: spec-generator
version: "1.0"
related-specs: []
---

# Spec: Add Products in Cart — Patrón Screenplay (Test Case 12)

> **Estado:** `IMPLEMENTED`.
> **Ciclo de vida:** DRAFT → APPROVED → IN_PROGRESS → IMPLEMENTED → DEPRECATED

---

## 1. REQUERIMIENTOS

### Descripción

Automatizar el caso de prueba **Test Case 12: Add Products in Cart** del sitio [automationexercise.com](https://automationexercise.com), aplicando el patrón **Screenplay** con Serenity BDD y Cucumber sobre Java/Gradle. El escenario valida que un comprador pueda agregar dos productos distintos al carrito desde la página de productos y verificar precios, cantidad y total.

### Requerimiento de Negocio

> Reto de nivelación — Automatización Front-End con patrón Screenplay (punto 2 de la rúbrica).
> Escenario tomado de https://automationexercise.com/test_cases (Test Case 12, diferente al automatizado con POM).
> Patrones: Screenplay (Actores, Tareas, Acciones, Preguntas).
> Restricción: aplicar Principio de Responsabilidad Única en cada Task.

### Historias de Usuario

#### HU-01: Agregar dos productos al carrito y verificar totales

```
Como:        Comprador que visita la tienda en línea
Quiero:      Agregar el primer y segundo producto al carrito desde la página de productos
Para:        Verificar que ambos productos aparecen en el carrito con precio, cantidad y total correctos

Prioridad:   Alta
Estimación:  M
Dependencias: Ninguna
Capa:        Frontend (Automatización E2E)
```

#### Criterios de Aceptación — HU-01

**Happy Path**
```gherkin
CRITERIO-1.1: Ambos productos son agregados al carrito exitosamente
  Dado que:  el comprador está en la página principal de automationexercise.com
  Cuando:    navega a la página de productos
             Y hace hover sobre el primer producto y hace clic en "Add to cart"
             Y hace clic en "Continue Shopping"
             Y hace hover sobre el segundo producto y hace clic en "Add to cart"
             Y hace clic en "View Cart"
  Entonces:  ambos productos aparecen en el carrito
             Y el precio unitario de cada producto es visible y correcto
             Y la cantidad de cada producto es 1
             Y el precio total de cada producto coincide con su precio unitario
```

**Edge Case**
```gherkin
CRITERIO-1.2: El modal de confirmación aparece al agregar cada producto
  Dado que:  el comprador está en la página de productos
  Cuando:    hace hover sobre cualquier producto y hace clic en "Add to cart"
  Entonces:  aparece el modal de confirmación con las opciones "Continue Shopping" y "View Cart"
```

### Reglas de Negocio

1. El primer producto agregado debe permanecer en el carrito cuando se agrega el segundo.
2. La cantidad por defecto al agregar un producto desde la página de listado es **1**.
3. El total de cada producto = precio unitario × cantidad.
4. El escenario **no puede** ser el mismo automatizado con POM (Test Case 12 fue asignado a Screenplay; POM usa un escenario distinto).
5. Cada `Task` del patrón Screenplay debe tener **una única responsabilidad** (SRP).
6. Política de **cero hardcode**: ningún string o valor variable debe quedar hardcodeado en `Tasks`, `Questions`, `Step Definitions`, configuración o Gherkin técnico. Todos los valores reutilizables deben centralizarse en archivos de `config` y/o `testdata`.

---

## 2. DISEÑO

### Stack Tecnológico

| Elemento | Tecnología / Versión |
|---|---|
| Lenguaje | Java 17 |
| Framework BDD | Serenity BDD 4.x |
| Test Runner | Cucumber 7.x |
| Build Tool | Gradle 8.x |
| Driver Manager | WebDriverManager (auto) |
| Navegador | Chrome (configurable en `serenity.conf`) |
| Patrón UI | Screenplay (Serenity) |
| Reporte | Serenity HTML Report |

### Arquitectura Screenplay

#### Componentes del patrón

| Componente | Rol | Responsabilidad única |
|---|---|---|
| `Actor` (Comprador) | Sujeto de las pruebas | Ejecutar Tasks y formular Questions |
| `Task: NavigateToHomePage` | Task | Abrir el navegador y cargar la URL base |
| `Task: NavigateToProductsPage` | Task | Hacer clic en el botón "Products" de la nav |
| `Task: AddFirstProductToCart` | Task | Hacer hover sobre el primer producto y clic en "Add to cart" |
| `Task: ContinueShopping` | Task | Hacer clic en el botón "Continue Shopping" del modal |
| `Task: AddSecondProductToCart` | Task | Hacer hover sobre el segundo producto y clic en "Add to cart" |
| `Task: ViewCart` | Task | Hacer clic en el botón "View Cart" del modal |
| `Question: CartContainsProducts` | Question | Verificar que ambos productos están en el carrito |
| `Question: ProductDetailsAreCorrect` | Question | Verificar precio, cantidad y total de cada producto |

#### Targets (Page Elements)

| Target | Referencia CSS / XPath | Descripción |
|---|---|---|
| `HOME_PAGE_LOGO` | `#header img[alt='Website for practice automation']` | Logo de la home page |
| `PRODUCTS_NAV_BUTTON` | `a[href='/products']` | Enlace "Products" en la barra de navegación |
| `PRODUCT_CARDS` | `.features_items .product-image-wrapper` | Lista de tarjetas de producto |
| `ADD_TO_CART_BUTTON` | `.product-overlay .add-to-cart` | Botón "Add to cart" superpuesto en cada tarjeta |
| `CONTINUE_SHOPPING_BUTTON` | `button[data-dismiss='modal']` | Botón "Continue Shopping" del modal |
| `VIEW_CART_BUTTON` | `a[href='/view_cart']` dentro del modal | Botón "View Cart" del modal |
| `CART_ROWS` | `#cart_info_table tbody tr` | Filas de productos en el carrito |
| `CART_PRODUCT_NAME` | `.cart_description h4 a` | Nombre del producto en el carrito |
| `CART_PRODUCT_PRICE` | `.cart_price p` | Precio unitario en el carrito |
| `CART_PRODUCT_QUANTITY` | `.cart_quantity button` | Cantidad en el carrito |
| `CART_PRODUCT_TOTAL` | `.cart_total p` | Total por producto en el carrito |

### Estructura de Paquetes

```
src/
└── test/
    ├── java/
    │   └── com/
    │       └── screenplay/
    │           ├── config/
    │           │   ├── TestConstants.java
    │           │   └── UiLabels.java
    │           ├── runners/
    │           │   └── AddProductsInCartRunner.java
    │           ├── stepdefinitions/
    │           │   └── AddProductsInCartStepDef.java
    │           ├── tasks/
    │           │   ├── NavigateToHomePage.java
    │           │   ├── NavigateToProductsPage.java
    │           │   ├── AddFirstProductToCart.java
    │           │   ├── ContinueShopping.java
    │           │   ├── AddSecondProductToCart.java
    │           │   └── ViewCart.java
    │           ├── questions/
    │           │   ├── CartContainsProducts.java
    │           │   └── ProductDetailsAreCorrect.java
    │           └── ui/
    │               └── ProductPageTargets.java
    └── resources/
        ├── features/
        │   └── add_products_in_cart.feature
        ├── testdata/
        │   ├── carrito_base_2_items.json
        │   └── productos.yml
        └── serenity.conf
```

### Feature File (Gherkin)

**Archivo**: `src/test/resources/features/add_products_in_cart.feature`

```gherkin
# language: es
@AddProductsInCart @Screenplay
Feature: Agregar productos al carrito
  Como comprador que visita la tienda en línea
  Quiero agregar múltiples productos al carrito desde la página de productos
  Para verificar que los totales, cantidades y precios sean correctos

  Scenario Outline: Agregar dos productos al carrito y verificar detalle con datos reutilizables
    Given el comprador inicia la compra con el dataset <dataset_carrito>
    When agrega al carrito los productos <producto_1> y <producto_2>
    And continúa el flujo de compra definido para <dataset_carrito>
    Then el carrito refleja los productos esperados en <dataset_carrito>
    And los precios, cantidades y totales cumplen las reglas del dataset <dataset_carrito>

    Examples:
      | dataset_carrito      | producto_1 | producto_2 |
      | carrito_base_2_items | producto_1 | producto_2 |
```

### Configuración del Driver

**Archivo**: `src/test/resources/serenity.conf`

```hocon
webdriver {
  driver = chrome
  autodownload = true
}

headless.mode = false
webdriver.base.url = ${?BASE_URL}

environments {
  local {
    webdriver.base.url = ${?BASE_URL_LOCAL}
  }
  ci {
    webdriver.base.url = ${?BASE_URL_CI}
  }
}

serenity {
  project.name = "AUTO_FRONT_SCREENPLAY"
  test.root = "com.screenplay"
  reporting.root = "target/site/serenity"
}

cucumber.options {
  features = "src/test/resources/features"
  glue = "com.screenplay.stepdefinitions"
  plugin = ["pretty", "json:target/cucumber.json"]
}
```

Notas de configuración:
- La URL base no debe hardcodearse.
- Se prioriza `BASE_URL` para ejecución local/CI.
- Los perfiles de entorno solo sobreescriben configuración; no reemplazan el uso de constantes/datasets.

### Configuración Gradle

**Archivo**: `build.gradle`

Dependencias clave requeridas:

```groovy
dependencies {
    testImplementation "net.serenity-bdd:serenity-core:4.1.+"
    testImplementation "net.serenity-bdd:serenity-cucumber:4.1.+"
    testImplementation "net.serenity-bdd:serenity-screenplay:4.1.+"
    testImplementation "net.serenity-bdd:serenity-screenplay-webdriver:4.1.+"
    testImplementation "io.cucumber:cucumber-java:7.+"
    testImplementation "io.cucumber:cucumber-junit:7.+"
    testImplementation "junit:junit:4.13.+"
}
```

### Notas de Implementación

- El `Actor` se inicializa en el `@Before` del step definition con `OnStage.setTheStage(new OnlineCast())`.
- Cada `Task` implementa la interfaz `Performable` y el método `performAs(Actor actor)`.
- Los `Targets` se definen con `Target.the("descripción").locatedBy("css-selector")`.
- Las `Questions` retornan un valor verificable con `actor.asksAboutThe(question)` o directamente con `Ensure.that(...)`.
- El hover se realiza con la interacción `MouseMovement.to(target)` o `MoveMouseTo.the(target)` de Serenity.
- El runner usa `@CucumberOptions` apuntando al feature file y al paquete de step definitions.

#### Política de Externalización de Valores (No Hardcode)

- Datos de negocio: almacenar en `src/test/resources/testdata` (JSON/YAML/properties).
- Textos y labels de UI: centralizar en `com.screenplay.config.UiLabels`.
- Timeouts, retries e índices: declarar en `com.screenplay.config.TestConstants`.
- Prohibido repetir literales en `Tasks`, `Questions` y `Step Definitions`; usar claves y constantes nombradas.
- Excepciones permitidas: `0`, `1`, `-1`, `true`, `false` cuando su semántica sea obvia y no ambigua.

---

## 3. LISTA DE TAREAS

> Checklist accionable para el agente `frontend-developer`. Marcar cada ítem (`[x]`) al completarlo.

### Configuración del Proyecto

- [ ] Inicializar proyecto Gradle con `gradle init` (tipo: `java-application`)
- [ ] Configurar `build.gradle` con las dependencias de Serenity BDD, Screenplay y Cucumber
- [ ] Crear estructura de paquetes bajo `com.screenplay`
- [ ] Crear `serenity.conf` en `src/test/resources/` con configuración de Chrome
- [ ] Parametrizar `webdriver.base.url` con variables de entorno (`BASE_URL`, `BASE_URL_LOCAL`, `BASE_URL_CI`)
- [ ] Verificar que `gradle test` resuelve dependencias sin errores

### Implementación — Configuración y Datos Reutilizables

- [ ] Crear `TestConstants.java` con constantes compartidas (timeouts, retries, cantidad esperada, etc.)
- [ ] Crear `UiLabels.java` con textos/labels reutilizables de interfaz
- [ ] Crear datasets en `src/test/resources/testdata/` para productos y expectativas del carrito
- [ ] Reemplazar literales de negocio por claves de dataset y constantes nombradas

### Implementación — UI Targets

- [ ] Crear `ProductPageTargets.java` con todos los `Target` definidos en la sección de diseño
- [ ] Verificar selectores CSS contra el DOM real de automationexercise.com

### Implementación — Tasks

- [ ] Crear `NavigateToHomePage.java` — abre el navegador usando URL base parametrizable
- [ ] Crear `NavigateToProductsPage.java` — clic en botón "Products"
- [ ] Crear `AddFirstProductToCart.java` — hover + clic "Add to cart" en primer producto
- [ ] Crear `ContinueShopping.java` — clic en "Continue Shopping" del modal
- [ ] Crear `AddSecondProductToCart.java` — hover + clic "Add to cart" en segundo producto
- [ ] Crear `ViewCart.java` — clic en "View Cart" del modal
- [ ] Verificar que cada Task tiene una **única responsabilidad** (SRP)

### Implementación — Questions

- [ ] Crear `CartContainsProducts.java` — verifica que el carrito tiene exactamente 2 productos
- [ ] Crear `ProductDetailsAreCorrect.java` — verifica precio, cantidad (= 1) y total por producto

### Implementación — Step Definitions y Runner

- [ ] Crear `AddProductsInCartStepDef.java` con mapeo de todos los pasos Gherkin
- [ ] Inicializar `OnStage` en `@Before` con `OnlineCast`
- [ ] Crear `AddProductsInCartRunner.java` con `@RunWith(CucumberWithSerenity.class)` y `@CucumberOptions`

### Implementación — Feature File

- [ ] Crear `add_products_in_cart.feature` con el escenario Gherkin del diseño
- [ ] Validar que el escenario es **declarativo** (orientado a negocio, sin detalles técnicos de UI)
- [ ] Validar que el feature use `Scenario Outline` + `Examples` con claves de dataset reutilizables
- [ ] Validar que no haya strings hardcodeados de UI en los pasos Gherkin
- [ ] Verificar que los `Given/When/Then` mapean 1:1 con los step definitions

### Validaciones de Buenas Prácticas

- [ ] Ausencia total de código comentado en todas las clases
- [ ] Nomenclatura semántica en variables, métodos y clases
- [ ] Sin lógica duplicada entre Tasks
- [ ] Cada clase tiene una única responsabilidad
- [ ] Cero hardcode en `Tasks`, `Questions`, `Step Definitions` y `feature file`
- [ ] Todos los valores variables provienen de `config` o `testdata`

### QA

- [ ] Ejecutar `gradle test` y verificar que el escenario pasa (verde)
- [ ] Ejecutar `gradle aggregate` para generar el reporte Serenity HTML
- [ ] Verificar que el reporte en `target/site/serenity/index.html` muestra el escenario como PASS
- [ ] Documentar instrucciones de ejecución en `README.md` del repositorio
- [ ] Actualizar estado spec: `status: IMPLEMENTED`

---

## 4. Referencias de Buenas Prácticas (Web)

- Twelve-Factor App — Configuración en variables de entorno: https://12factor.net/config
- Cucumber Gherkin Reference — especificaciones declarativas orientadas a negocio: https://cucumber.io/docs/gherkin/reference/
- Replace Magic Number with Symbolic Constant — eliminación de valores mágicos: https://refactoring.guru/es/replace-magic-number-with-symbolic-constant
