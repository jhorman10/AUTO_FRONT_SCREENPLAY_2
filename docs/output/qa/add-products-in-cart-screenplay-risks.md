# Matriz de Riesgos QA - add-products-in-cart-screenplay

## Resumen ASD
- Total riesgos: 4
- Alto (A): 2
- Medio (S): 1
- Bajo (D): 1

## Detalle de riesgos priorizados

| ID | HU | Riesgo | Evidencia | Nivel ASD | Impacto | Mitigación mínima |
|---|---|---|---|---|---|---|
| R-001 | HU-01 | Falla en localización de Continue Shopping impide completar flujo crítico | AssertionError por button[data-dismiss='modal'] no visible en ejecución | A | Bloquea escenario principal de negocio | Ajustar locator al contexto real del modal y esperar visibilidad/clickability del modal antes del click |
| R-002 | HU-01 | Cobertura funcional insuficiente por única prueba en rojo | 1 test, 1 fallo, 0% success en reporte Gradle | A | No hay evidencia de cumplimiento de criterios de aceptación | Corregir locator y re-ejecutar suite hasta 100% en feature crítica |
| R-003 | Proceso QA/ASDD | Inconsistencia de estado de spec (frontmatter IMPLEMENTED vs encabezado APPROVED) | Metadato y cuerpo de spec no alineados | S | Riesgo de trazabilidad y gate ambiguo | Unificar estado oficial de spec antes de cierre de ciclo |
| R-004 | Ejecución técnica | Advertencias CDP por versión Chrome/Selenium pueden introducir inestabilidad futura | Warning de CDP sin match en reporte | D | No bloquea hoy, posible flaky behavior | Alinear dependencia Selenium DevTools con versión de navegador o fijar versión del browser en CI |

## Plan de mitigación para riesgos ALTO

### R-001 - Locator Continue Shopping
- Acción: reemplazar selector frágil por selector anclado al modal activo y texto del botón.
- Acción: agregar espera explícita de modal abierto antes de interactuar.
- Criterio de cierre: paso When completa sin timeout en 3 corridas consecutivas.
- Bloqueante de release: Sí.

### R-002 - Suite crítica en rojo
- Acción: ejecutar gradle test para la feature tras corrección del locator.
- Acción: validar asserts de carrito (items, precio, cantidad, total) sin skips.
- Criterio de cierre: 1/1 escenario en verde y 0 fallos en reporte.
- Bloqueante de release: Sí.

## Decisión QA recomendada
- Estado actual: NO-GO.
- Condición para GO: cierre de R-001 y R-002 con evidencia de ejecución estable en reportes.

## Evidencia base
- Spec: .github/specs/add-products-in-cart-screenplay.spec.md
- Reporte de prueba: build/reports/tests/test/classes/Agregar#20productos#20al#20carrito.html
- Implementación Screenplay: src/test/java/com/screenplay/tasks/
