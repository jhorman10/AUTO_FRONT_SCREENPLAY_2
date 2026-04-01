# AUTO_FRONT_SCREENPLAY

Proyecto de automatizacion E2E con patron Screenplay sobre Automation Exercise.

Este repositorio implementa y valida el flujo del Test Case 12 (Add Products in Cart) usando:
- Serenity BDD
- Cucumber
- Java 17
- Gradle Wrapper

---

## Alcance actual

Feature implementada y validada:
- Add Products in Cart (Test Case 12)
- Fuente: https://automationexercise.com/test_cases

Flujo automatizado:
1. Abrir navegador
2. Ir a home de Automation Exercise
3. Verificar home visible
4. Ir a Products
5. Agregar primer producto
6. Continuar comprando
7. Agregar segundo producto
8. Ver carrito
9. Verificar 2 productos en carrito
10. Verificar precio, cantidad y total por producto

---

## Stack tecnico

| Componente | Version |
|---|---|
| Java | 17 |
| Gradle Wrapper | 8.6 |
| Serenity BDD | 4.1.x |
| Cucumber | 7.15.0 |
| Navegador | Chrome |

---

## Estructura real del proyecto

src/
- test/
  - java/com/screenplay/
    - config/
      - TestConstants.java
      - UiLabels.java
      - CartDataset.java
      - CartDataLoader.java
    - ui/
      - ProductPageTargets.java
    - tasks/
      - NavigateToHomePage.java
      - NavigateToProductsPage.java
      - AddProductToCart.java
      - ContinueShopping.java
      - ViewCart.java
    - questions/
      - CartContainsProducts.java
      - ProductDetailsAreCorrect.java
    - stepdefinitions/
      - AddProductsInCartStepDef.java
    - runners/
      - AddProductsInCartRunner.java
  - resources/
    - features/
      - add_products_in_cart.feature
    - testdata/
      - carrito_base_2_items.json
      - productos.yml
    - serenity.conf

Configuracion ASDD y agentes:
- .github/

---

## Requisitos previos (todos los sistemas)

1. Git instalado
2. Java 17 instalado
3. Google Chrome instalado
4. Conexion a internet para descarga de ChromeDriver via WebDriverManager

Verificar versiones:

Git:

```bash
git --version
```

Java:

```bash
java -version
```

---

## Inicio rapido por sistema operativo

### Linux / macOS

```bash
git clone <URL_DEL_REPOSITORIO>
cd AUTO_FRONT_SCREENPLAY
chmod +x gradlew
./gradlew clean test --tests "**AddProductsInCartRunner*" aggregate
```

Abrir reporte:

Linux:

```bash
xdg-open target/site/serenity/index.html
```

macOS:

```bash
open target/site/serenity/index.html
```

### Windows PowerShell

```powershell
git clone <URL_DEL_REPOSITORIO>
cd AUTO_FRONT_SCREENPLAY
$env:BASE_URL="https://automationexercise.com"
.\gradlew.bat clean test --tests "**AddProductsInCartRunner*" aggregate
Start-Process .\target\site\serenity\index.html
```

### Windows CMD

```bat
git clone <URL_DEL_REPOSITORIO>
cd AUTO_FRONT_SCREENPLAY
set BASE_URL=https://automationexercise.com
gradlew.bat clean test --tests "**AddProductsInCartRunner*" aggregate
start "" target\site\serenity\index.html
```

---

## Variables de entorno

| Variable | Uso | Valor recomendado |
|---|---|---|
| BASE_URL | URL base del sitio bajo prueba | https://automationexercise.com |
| BASE_URL_LOCAL | Perfil local Serenity | https://automationexercise.com |
| BASE_URL_CI | Perfil CI Serenity | https://automationexercise.com |

Ejemplo Linux/macOS:

```bash
export BASE_URL=https://automationexercise.com
```

Ejemplo PowerShell:

```powershell
$env:BASE_URL="https://automationexercise.com"
```

Ejemplo CMD:

```bat
set BASE_URL=https://automationexercise.com
```

---

## Comandos clave

Ejecutar toda la suite:

Linux/macOS:

```bash
./gradlew test
```

Windows:

```bat
gradlew.bat test
```

Ejecutar solo el runner de este proyecto:

Linux/macOS:

```bash
./gradlew test --tests "**AddProductsInCartRunner*"
```

Windows:

```bat
gradlew.bat test --tests "**AddProductsInCartRunner*"
```

Generar reporte Serenity:

Linux/macOS:

```bash
./gradlew aggregate
```

Windows:

```bat
gradlew.bat aggregate
```

Limpiar historial y regenerar reporte limpio:

Linux/macOS:

```bash
rm -rf target/site/serenity build/reports/serenity && ./gradlew clean test --tests "**AddProductsInCartRunner*" aggregate
```

Windows PowerShell:

```powershell
Remove-Item -Recurse -Force target/site/serenity, build/reports/serenity -ErrorAction SilentlyContinue
.\gradlew.bat clean test --tests "**AddProductsInCartRunner*" aggregate
```

Windows CMD:

```bat
rmdir /s /q target\site\serenity
rmdir /s /q build\reports\serenity
gradlew.bat clean test --tests "**AddProductsInCartRunner*" aggregate
```

---

## Reporte Serenity

Ruta principal del dashboard:
- target/site/serenity/index.html

Archivos utiles:
- target/site/serenity/summary.txt
- target/site/serenity/SERENITY-JUNIT-*.xml
- target/site/serenity/*.json

---

## Flujo ASDD en este repositorio

El repositorio mantiene flujo ASDD en .github para trazabilidad:
- Requirements en .github/requirements/
- Specs en .github/specs/
- Agentes y skills en .github/agents/ y .github/skills/

Regla operativa:
- No implementar cambios de feature sin spec APPROVED.

Ciclo de vida de spec:
- DRAFT -> APPROVED -> IN_PROGRESS -> IMPLEMENTED -> DEPRECATED

---

## Estado del proyecto

- Proyecto listo para ejecutar en Linux, macOS y Windows.
- Test Case 12 automatizado con Screenplay.
- Reporte Serenity generado por Gradle aggregate.
