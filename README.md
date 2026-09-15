# WEB Final - Framework de Automatización SauceDemo

Proyecto del Examen Final de Certificación 2 para automatizar **https://www.saucedemo.com/**.

## Cumplimiento de la consigna

- Selenium WebDriver
- JUnit 5
- Cucumber 7
- Page Object Model (POM)
- Hooks `@Before` y `@After`
- `Background` / `Antecedentes`
- `Scenario Outline` / `Esquema del escenario`
- DataTables
- Assertions con JUnit
- Mínimo 5 escenarios de negocio
- Captura de evidencia automática cuando falla un escenario
- Reporte Extent HTML
- Reporte Extent Spark HTML
- Reporte Extent PDF
- Código preparado para subir a un repositorio Git nuevo

## Escenarios automatizados

1. Agregar un producto al carrito.
2. Agregar varios productos usando DataTable.
3. Ordenar productos por precio usando Scenario Outline (ascendente y descendente).
4. Eliminar un producto desde el carrito.
5. Completar una compra correctamente usando DataTable.

> El Scenario Outline tiene dos filas de ejemplos, por lo que Maven ejecuta más de cinco casos concretos, aunque existen cinco escenarios de negocio definidos en el feature.

## Requisitos

- Java JDK 21
- Maven
- Google Chrome
- Git (para subir el proyecto)

No es necesario instalar ChromeDriver manualmente. Selenium Manager resuelve el driver automáticamente.

## Ejecutar todo

Desde la raíz del proyecto:

```bash
mvn clean test
```

Para ejecutar Chrome en modo headless:

```bash
mvn clean test -Dheadless=true
```

## Reportes

Después de ejecutar las pruebas se generan:

```text
test-output/
├── ExtentReport/
│   └── ExtentReport.html
├── SparkReport/
│   └── Spark.html
└── PdfReport/
    └── ExtentPdf.pdf
```

También se generan reportes estándar de Cucumber en:

```text
target/cucumber-report.html
target/cucumber.json
```

## Estructura

```text
src/
├── main/java/com/upb/
│   ├── pages/        # Page Object Model
│   └── utils/        # Driver y reporte Extent adicional
└── test/
    ├── java/com/upb/
    │   ├── hooks/    # Before / After
    │   ├── runners/  # Runner JUnit + Cucumber
    │   └── steps/    # Step Definitions
    └── resources/
        ├── features/ # Gherkin
        └── extent.properties
```
## Nota

El login se encuentra en `Antecedentes` porque funciona como precondición común de los escenarios y no se contabiliza como escenario independiente. Esto evita usar login/logout como uno de los casos principales y permite demostrar correctamente el concepto de Background de Cucumber.
