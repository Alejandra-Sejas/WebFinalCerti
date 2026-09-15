# language: es
Característica: Framework de automatización WEB para SauceDemo
  Como equipo de QA
  Quiero automatizar los principales flujos de la tienda
  Para validar su comportamiento usando Selenium, JUnit, Cucumber y POM

  Antecedentes:
    Dado que el usuario ingresa a SauceDemo
    Y inicia sesión con usuario "standard_user" y contraseña "secret_sauce"

  @carrito @smoke
  Escenario: Agregar un producto al carrito
    Cuando agrega el producto "Sauce Labs Backpack" al carrito
    Entonces el contador del carrito debe mostrar 1
    Y el producto "Sauce Labs Backpack" debe estar dentro del carrito

  @datatable @carrito
  Escenario: Agregar varios productos utilizando DataTable
    Cuando agrega los siguientes productos al carrito:
      | Sauce Labs Backpack              |
      | Sauce Labs Bike Light            |
      | Sauce Labs Bolt T-Shirt          |
    Entonces el contador del carrito debe mostrar 3
    Y el carrito debe contener exactamente los siguientes productos:
      | Sauce Labs Backpack              |
      | Sauce Labs Bike Light            |
      | Sauce Labs Bolt T-Shirt          |

  @outline @ordenamiento
  Esquema del escenario: Ordenar los productos por precio
    Cuando ordena los productos por "<valor>"
    Entonces los precios deben quedar ordenados en forma "<direccion>"

    Ejemplos:
      | valor | direccion    |
      | lohi  | ascendente   |
      | hilo  | descendente  |

  @carrito @regresion
  Escenario: Eliminar un producto desde el carrito
    Cuando agrega "Sauce Labs Bike Light" y abre el carrito
    Y elimina "Sauce Labs Bike Light" desde el carrito
    Entonces el carrito debe quedar vacío

  @checkout @datatable @regresion
  Escenario: Completar una compra correctamente
    Cuando agrega "Sauce Labs Onesie" y realiza el checkout con los datos:
      | nombre       | Kevin |
      | apellido     | Cazon |
      | codigoPostal | 0000  |
    Entonces debe mostrarse el mensaje de compra "Thank you for your order!"
