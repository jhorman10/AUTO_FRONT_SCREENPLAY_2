# CONTEXTO TECNICO COMPLETO - AUTO_FRONT_SCREENPLAY

## 1. Objetivo del proyecto (explicado para desarrolladores no QA)

AUTO_FRONT_SCREENPLAY es un proyecto Java de automatizacion E2E que valida un flujo funcional de una aplicacion web real (Automation Exercise) usando el patron Screenplay.

Si vienes de desarrollo backend o frontend y no de QA, piensalo asi:

- Un test E2E aqui es un "cliente robot" que abre el navegador y hace el recorrido de negocio como un usuario real.
- Screenplay organiza ese recorrido con responsabilidades claras:
  - Actor: quien ejecuta acciones.
  - Task: accion de negocio atomica.
  - Question: validacion del resultado observable.
  - Targets: localizadores de UI centralizados.
- Cucumber (Gherkin) define el comportamiento en lenguaje cercano a negocio.
- Serenity agrega trazabilidad y reporte visual.

El caso implementado corresponde a Test Case 12: Add Products in Cart.

## 2. Flujo funcional validado por el proyecto

Secuencia de negocio automatizada:

1. Abrir el navegador.
2. Navegar a la home.
3. Verificar que la home este visible.
4. Ir a Products.
5. Agregar primer producto al carrito.
6. Continuar comprando.
7. Agregar segundo producto al carrito.
8. Ir al carrito.
9. Verificar que hay dos productos.
10. Verificar para cada producto que cantidad = 1 y total = precio unitario (regla del caso).

## 3. Arquitectura tecnica de alto nivel

## 3.1 Stack real

- Java 17
- Gradle Wrapper 8.6
- Serenity BDD 4.2.34
- Cucumber JVM 7.15.0
- JUnit 4 runner para Cucumber
- Selenium WebDriver (via serenity-screenplay-webdriver)

## 3.2 Capas del proyecto

- Gherkin: define comportamiento.
- Runner Cucumber+Serenity: arranca ejecucion.
- Step Definitions: traduce pasos de negocio a tareas/preguntas.
- Tasks: comandos de interaccion UI.
- Questions: consultas para validar estado.
- Config/Testdata: valores y datasets reutilizables.
- UI Targets: selectores centralizados.
- Serenity report: evidencia de ejecucion.

## 4. Estructura de carpetas explicada punto a punto

## 4.1 Raiz del repositorio

- build.gradle
  - Define plugins, dependencias y task de test.
- settings.gradle
  - Nombre del proyecto Gradle.
- gradlew / gradlew.bat
  - Wrapper para ejecutar sin instalar Gradle global.
- src/
  - Codigo de pruebas.
- target/site/serenity/
  - Reportes HTML/JSON/XML de Serenity.
- .github/
  - Especificaciones y lineamientos ASDD.
- docs/
  - Salidas QA y documentacion adicional.

## 4.2 src/test/resources

- features/add_products_in_cart.feature
  - Escenario de negocio en Gherkin.
- testdata/carrito_base_2_items.json
  - Dataset principal de expectativas.
- testdata/productos.yml
  - Catalogo de referencia de productos.
- serenity.conf
  - Configuracion de driver, URL base y reporte.

## 4.3 src/test/java/com/screenplay

- config/
  - Constantes, labels y carga de testdata.
- ui/
  - Selectores y targets de UI.
- tasks/
  - Acciones atomicas (navegar, agregar, continuar, ver carrito).
- questions/
  - Validaciones de estado observable.
- stepdefinitions/
  - Puente entre Gherkin y Screenplay.
- runners/
  - Entrada de ejecucion Cucumber con Serenity.

## 5. build.gradle explicado en lenguaje de desarrollo

Archivo: build.gradle

Puntos clave:

1. Plugin de Serenity Gradle
- Carga capacidades de reporte/aggregates de Serenity.

2. Dependencias de prueba
- serenity-core: motor base de Serenity.
- serenity-cucumber: integracion Cucumber+Serenity.
- serenity-screenplay: patron Screenplay.
- serenity-screenplay-webdriver: acciones web.
- cucumber-java/cucumber-junit: lenguaje Gherkin y runner.
- assertj: aserciones legibles.
- jackson databind/yaml: parseo de testdata.

3. Task test
- Usa JUnit.
- Inyecta BASE_URL como systemProperty con fallback a automationexercise.com.
- Muestra eventos passed/skipped/failed en consola.

## 6. serenity.conf explicado

Archivo: src/test/resources/serenity.conf

- webdriver.driver = chrome
  - Usa Chrome.
- webdriver.autodownload = true
  - Descarga driver automaticamente.
- headless.mode = false
  - Modo visible por defecto.
- webdriver.base.url = ${?BASE_URL}
  - Lee BASE_URL del entorno si existe.
- environments.local / environments.ci
  - Permiten sobrescribir URL por entorno.
- serenity.reporting.root = target/site/serenity
  - Ubicacion de reportes.
- cucumber.options
  - features, glue y plugin JSON.

## 7. Gherkin explicado linea por linea

Archivo: src/test/resources/features/add_products_in_cart.feature

- Tags: @AddProductsInCart @Screenplay
  - Permiten filtrar ejecuciones.

- Feature
  - Describe objetivo de negocio: agregar productos y validar precios/cantidades/totales.

- Scenario Outline
  - Estructura parametrizable por dataset.
  - Pasos:
    1. Given inicia compra con dataset.
    2. When agrega dos productos.
    3. And continua flujo (view cart).
    4. Then valida carrito esperado.
    5. And valida reglas de precio/cantidad/total.

- Examples
  - Usa dataset carrito_base_2_items.

Nota tecnica importante:
- Hoy existe una fila en Examples.
- El outline esta listo para crecer a multiples datasets sin reescribir pasos.

## 8. Mapeo Gherkin -> metodos de Step Definitions

Archivo: src/test/java/com/screenplay/stepdefinitions/AddProductsInCartStepDef.java

## 8.1 setStage()
- Hook @Before.
- Inicializa el escenario de actores (OnStage) con OnlineCast.

## 8.2 elCompradorIniciaLaCompraConElDataset(String datasetKey)
- Carga dataset JSON por clave.
- Crea actor "Comprador".
- Guarda dataset en memoria del actor (KEY_DATASET).
- Ejecuta NavigateToHomePage.

## 8.3 agregaAlCarritoLosProductos(String producto1Key, String producto2Key)
- Ejecuta secuencia de tasks:
  - NavigateToProductsPage
  - AddFirstProductToCart
  - ContinueShopping
  - AddSecondProductToCart

## 8.4 continuaElFlujoDCompraDefinidoPara(String datasetKey)
- Ejecuta ViewCart.

## 8.5 elCarritoReflejaLosProductosEsperadosEn(String datasetKey)
- Recupera dataset desde memoria actor.
- Valida cantidad de filas en carrito con CartContainsProducts.

## 8.6 losPreciosCantidadesYTotalesCumplenLasReglas(String datasetKey)
- Ejecuta ProductDetailsAreCorrect y espera true.

## 9. Tasks explicadas funcion a funcion

## 9.1 NavigateToHomePage
Archivo: tasks/NavigateToHomePage.java

- Constructor
  - Lee BASE_URL del entorno.
  - Si no existe, usa FALLBACK_BASE_URL.

- performAs
  - Open.url(baseUrl)
  - WaitUntil HOME_PAGE_LOGO visible.

- now()
  - Fabrica Task instrumentada por Serenity.

## 9.2 NavigateToProductsPage
Archivo: tasks/NavigateToProductsPage.java

- Constructor
  - Misma logica de URL base.

- performAs
  - Busca link a /products en la navegacion.
  - Si existe y visible: click.
  - Si no: abre baseUrl + /products.
  - Espera PRODUCTS_PAGE_TITLE visible.

- now()
  - Fabrica instrumentada.

## 9.3 AddFirstProductToCart
Archivo: tasks/AddFirstProductToCart.java

- performAs
  - Obtiene lista de botones add-to-cart visibles.
  - Toma indice FIRST_PRODUCT_INDEX.
  - Hace scroll + click por JavaScript.
  - Espera modal de carrito visible.

## 9.4 ContinueShopping
Archivo: tasks/ContinueShopping.java

- performAs
  - Espera modal visible.
  - Espera boton Continue Shopping clickeable.
  - Ejecuta click via JavaScript.

## 9.5 AddSecondProductToCart
Archivo: tasks/AddSecondProductToCart.java

- performAs
  - Obtiene lista de botones add-to-cart visibles.
  - Toma indice SECOND_PRODUCT_INDEX.
  - Scroll + click via JavaScript (mitiga click interceptado por overlays/ads).
  - Espera modal visible.

## 9.6 ViewCart
Archivo: tasks/ViewCart.java

- performAs
  - Espera modal visible.
  - Espera link View Cart clickeable.
  - Click via JavaScript.

## 10. Questions explicadas

## 10.1 CartContainsProducts
Archivo: questions/CartContainsProducts.java

- answeredBy
  - Cuenta filas del carrito por selector CART_ROWS_CSS.
  - Retorna entero con total de productos en tabla.

## 10.2 ProductDetailsAreCorrect
Archivo: questions/ProductDetailsAreCorrect.java

- answeredBy
  - Recorre filas del carrito.
  - Lee price, quantity y total por fila.
  - Verifica:
    - quantity == EXPECTED_QUANTITY_PER_ITEM
    - total == price
  - Si todo pasa, retorna true.

## 11. Capa UI (selectores y targets)

Archivo: ui/ProductPageTargets.java

Rol de esta clase:
- Unificar los selectores en un solo lugar.
- Evitar duplicacion y reducir costo de mantenimiento cuando cambia el DOM.

Constantes CSS relevantes:
- CART_MODAL_SHOWN_CSS
- CONTINUE_SHOPPING_BUTTON_CSS
- VIEW_CART_IN_MODAL_CSS
- CART_ROWS_CSS
- CART_PRICE_IN_ROW_CSS
- CART_QTY_IN_ROW_CSS
- CART_TOTAL_IN_ROW_CSS

Targets relevantes:
- HOME_PAGE_LOGO
- PRODUCTS_NAV_BUTTON
- PRODUCTS_PAGE_TITLE
- CONTINUE_SHOPPING_BUTTON
- VIEW_CART_BUTTON_MODAL

## 12. Config y datos (constantes y testdata)

## 12.1 TestConstants
Archivo: config/TestConstants.java

Constantes clave:
- ENV_BASE_URL: nombre variable entorno.
- FALLBACK_BASE_URL: URL fallback.
- PRODUCTS_PATH: sufijo ruta productos.
- FIRST_PRODUCT_INDEX, SECOND_PRODUCT_INDEX: indices 0-based.
- EXPECTED_QUANTITY_PER_ITEM: regla cantidad esperada.
- ELEMENT_WAIT_SECS: tiempo de espera explicita.
- TESTDATA_PATH y TESTDATA_JSON_EXT: convencion de archivos.
- KEY_DATASET: clave memoria actor.

## 12.2 UiLabels
Archivo: config/UiLabels.java

Centraliza:
- Nombre del actor.
- Mensajes de @Step para trazabilidad.
- Mensajes de error de carga de datasets.
- Textos de asercion para reportes legibles.

## 12.3 CartDataset
Archivo: config/CartDataset.java

Es un DTO para mapear JSON:
- datasetId
- expectedCartItemCount
- expectedQuantityPerItem
- items[] con key y productIndex

Incluye clase interna CartItem para cada item esperado.

## 12.4 CartDataLoader
Archivo: config/CartDataLoader.java

Metodo principal:
- load(String datasetKey)

Funcionamiento:
1. Construye path: /testdata/<key>.json
2. Lee recurso del classpath.
3. Si no existe, lanza IllegalArgumentException.
4. Si falla parseo, lanza IllegalStateException.

## 12.5 Dataset actual
Archivo: testdata/carrito_base_2_items.json

Define:
- expectedCartItemCount = 2
- expectedQuantityPerItem = 1
- items con indices 0 y 1

Archivo: testdata/productos.yml
- Catalogo descriptivo del indice 0 y 1.

## 13. Runner y ejecucion

Archivo: runners/AddProductsInCartRunner.java

Anotaciones:
- @RunWith(CucumberWithSerenity.class)
- @CucumberOptions con:
  - features path
  - glue package
  - plugins
  - tag @AddProductsInCart

Esto permite ejecutar solo esta feature de forma controlada.

## 14. Comandos operativos (desarrollador)

Ejecucion minima:
- ./gradlew test

Ejecucion del runner objetivo:
- ./gradlew test --tests "**AddProductsInCartRunner*"

Generar reporte:
- ./gradlew aggregate

Ejecucion limpia (recomendada para demo):
- rm -rf target/site/serenity build/reports/serenity && ./gradlew clean test --tests "**AddProductsInCartRunner*" aggregate

Abrir reporte Linux:
- xdg-open target/site/serenity/index.html

## 15. Estandares y convenciones aplicadas

## 15.1 Estandares de codigo

- Nombres semanticos en clases y metodos.
- Separacion por responsabilidades (SRP por Task).
- Reuso de constantes y labels en config.
- Sin comentarios dentro de clases (criterio de rubrica aplicado).

## 15.2 Estandares de automatizacion

- Patrón Screenplay completo:
  - Actor
  - Tasks
  - Questions
  - Targets
- Esperas explicitas para estabilidad.
- Selectores centralizados.
- Datos externalizados en testdata.
- Reportabilidad via Serenity.

## 15.3 Estandares de configuracion

- URL parametrizable por variable de entorno.
- Fallback controlado para entorno local.
- Soporte de perfiles local/ci en serenity.conf.

## 15.4 Estandares Gherkin

- Escenario declarativo orientado a negocio.
- Uso de Scenario Outline para escalabilidad de datasets.
- Separacion clara Given/When/Then.

Nota de diseno:
- Los parametros producto_1 y producto_2 del Outline hoy no alteran la ejecucion en tasks (la seleccion actual es por indices constantes 0 y 1).
- Para evolucion futura, se puede consumir esos parametros directamente desde dataset/items para hacer el outline totalmente dinamico.

## 16. Como pensar este proyecto si vienes de backend/frontend

Analogias utiles:

- StepDefinition = Controller de orquestacion (sin logica pesada).
- Task = Caso de uso/command atomico.
- Question = Query/read-model para validaciones.
- Targets = Adapter de infraestructura (selectores DOM).
- CartDataLoader = repositorio de datos de prueba.
- serenity.conf = archivo de configuracion de runtime.

## 17. Puntos fuertes actuales

1. Flujo de negocio del caso 12 funcional y estable.
2. Reporte Serenity legible para auditoria/demo.
3. Estructura extensible para nuevos escenarios.
4. Convenciones de no hardcode aplicadas en gran parte.
5. Setup reproducible por Gradle Wrapper.

## 18. Riesgos tecnicos y mantenimiento

1. Sitio externo puede cambiar DOM sin aviso.
   - Mitigacion: centralizar selectores en ProductPageTargets.

2. Publicidad/overlays pueden interceptar clics.
   - Mitigacion: click via JS + waits de visibilidad/clickability.

3. Variacion de browser/CDP segun version de Chrome.
   - Mitigacion: fijar versiones y revisar warnings en CI.

## 19. Ruta rapida de extension (si quieres agregar mas casos)

1. Crear nuevo feature .feature.
2. Agregar tag para filtrado.
3. Crear/ajustar stepdefinitions minimas.
4. Reutilizar tasks existentes o crear nuevas SRP.
5. Agregar questions de validacion observable.
6. Centralizar nuevos selectores en ProductPageTargets.
7. Agregar testdata en JSON/YAML.
8. Ejecutar test y aggregate.

## 20. Estado actual verificado

- Build y test de escenario objetivo: OK.
- Reporte Serenity agregado: OK.
- Spec de feature en estado IMPLEMENTED.

Este documento resume el proyecto para que un desarrollador generalista pueda entender rapidamente su arquitectura, su flujo y su mantenimiento sin depender de experiencia previa fuerte en QA.
