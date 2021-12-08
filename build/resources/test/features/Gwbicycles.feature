#language:en
#Autor: Jhonatan Paternina Rojas

Feature: HU-001 Buscar productos en gwbicycles
  yo como usuario de gwbicycles
  quiero buscar cinco productos para ver los nombres en pantalla

  Scenario: Buscar productos
    Given que me encuentro en la pagina de Gwbicycles
    When busque los productos 'Productos.xlsx'
    Then podre ver los nombres en pantalla