# AutomatizacionGwbicycles
El siguiente reto consiste en realizar la automatización de la pagina web https://gwbicycles.com/, la cual debe realizar la búsqueda de 5 productos que son cargados a traves de un archivo excel y asi poder realizar la validacion correspondiente.

## Conceptos tecnicos
> ### Selenium
>Es una herramienta de código abierto para la automatización de pruebas,de navegadores web, Selenium nos proporciona una herramienta con la cual podremos grabar y/o reproducir, editar y depurar casos de prueba, con la cual nos permitirá ejecutar las pruebas repetidamente las veces que sean necesarias. 
> >Ref: (https://inmediatum.com/blog/piensa-digital/que-es-selenium-y-para-que-sirve/).
> ### Selenium Webdriver
> Selenium Webdriver es el sucesor de Selenium RC, por lo cual es una herramienta que permite automatizar pruebas UI (User Interface) o Interfaz de usuario de aplicaciones Web pero se basa en un enfoque más moderno y estable que la versión de Selenium RC, por lo que Webdriver a diferencia de RC no utiliza middleware sino controla el navegador comunicándose directamente con él.
> >Ref: (https://inmediatum.com/blog/piensa-digital/que-es-selenium-y-para-que-sirve/).
> ### Cucumber
> Cucumber es una herramienta para implementar metodologías como el Behaviour Driven Development (BDD) o desarrollo basado en comportamiento, que permite ejecutar descripciones funcionales en texto plano como pruebas de software automatizadas.
Estas descripciones funcionales se escriben en un lenguaje específico de dominio, legible por el área de negocio, denominado Gherkin, que soporta más de 60 idiomas. Gherkin sirve simultáneamente como documentación de apoyo al desarrollo y a las pruebas automatizadas.
> >Ref: (https://www.pragma.com.co/blog/junit-vs.-cucumber-herramientas-de-automatizacion-de-pruebas)
> ### Gherkin
> El lenguaje Gherkin define la estructura y una sintaxis básica para la descripción de las pruebas que pueden ser entendidas tanto por los integrantes técnicos del equipo como así también por los Analistas/PO o quien quiera que este como representante del cliente. De esta manera mientras se generan pruebas se esta generando documentación viva que describe perfectamente como se comporta el sistema enriqueciendo y manteniendo la documentación.
> >Ref: (https://josepablosarco.wordpress.com/2015/03/11/lenguaje-gherkin/)

## IntelliJ, IDE ultilizado para el desarrollo
`IntelliJ IDEA es un IDE inteligente y sensible al contexto para trabajar con Java y otros lenguajes JVM como Kotlin, Scala y Groovy en todo tipo de aplicaciones. Además, IntelliJ IDEA Ultimate puede ayudarle a desarrollar aplicaciones web de pila completa, gracias a sus potentes herramientas integradas, compatibilidad con JavaScript y tecnologías relacionadas, y compatibilidad avanzada con marcos de trabajo populares como Spring, Spring Boot, Jakarta EE, Micronaut, Quarkus y Helidon. Además, puede ampliar IntelliJ IDEA con complementos gratuitos desarrollados por JetBrains, lo que le permite trabajar con otros lenguajes de programación, como Go, Python, SQL, Ruby o PHP.`
> Ref: (https://www.jetbrains.com/es-es/idea/)

### Estructura del Proyecto

> El proyecto esta estructurado de la sigueinte manera: 
```java
+ gradle
+ idea
+ build
+ gradle
- history
-- src
--- main
----- java
------+ drivers
------+ pages
------+ steps
---- resourse
--- test
------ java
-------+ excel
-------+ runners
-------+ stepsDefinitions
------ resourse
-------+ feature
+ target
```
> En la carpeta <strong>*src*</strong> se encuentran las carpetas principales en las cuales se trabajo todo el proyecto.
### 1.    Dentro de la carpeta <strong>*main*</strong>/<strong>*java*</strong> se encuentran las carpetas: 
> -    - La carpeta drivers en la cual declaramos einicializamos los datos del navegador que se estara utilizando para renderizar las pruebas:
```java
package drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class GoogleChromeDriver {
    public static WebDriver driver;

    public static void chromeWebDriver(String url){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--disable-infobars");
        driver = new ChromeDriver(options);
        driver.get(url);
    }
}
```

> -    - Carpeta <strong>*page*</strong> en la cual se declaran todas las variables en las cuales se almacenan los distintos <strong>XPATH</strong> de las diferentes acciones que se tienen que ejecutar para llevar acabo la automatizacion.

```java
package pages;
import org.openqa.selenium.By;

public class GwbicyclesPage {
    By btnOpcionBuscar = By.xpath("//a[contains(@href,'/search')]");
    By btnInputText = By.xpath("//input[contains(@placeholder,'buscar en nuestra tienda')]");
    By btnBuscar = By.xpath("//button[contains(@type,'submit')]");
    By VerDetalle = By.xpath("//div[contains(@class,'grid-product__meta')]/div[@class='grid-product__title grid-product__title--heading']");
    By VerTexto;

    public By getVerTexto() { return VerTexto; }

    public By getVerDetalle() {return VerDetalle;}

    public By getBtnInputText() { return btnInputText; }

    public By getBtnBuscar() { return btnBuscar; }

    public By getBtnOpcionBuscar() {return btnOpcionBuscar;}

    public void setVerTexto(String producto) {
        this.VerTexto = By.xpath("//h1[contains(text(),'"+producto+"')]");
    }

}

```
> -    - En la carpeta <strong>*steps*</strong> se declaran los diferntes metodos en los cuales realizaremos la ejecucion de los diferentes <strong>XPATH</strong> declarados en el archivo anterior que se encuentra en la carpeta <strong>*page*</strong>

```java
package steps;

import drivers.GoogleChromeDriver;
import org.junit.Assert;
import pages.GwbicyclesPage;

public class GwbicyclesSteps {
    GwbicyclesPage gwbicyclesPage = new GwbicyclesPage();

    public void abrirPagina(){
        GoogleChromeDriver.chromeWebDriver("https://gwbicycles.com/");
    }

    public void bucarProductosEnGwbicycles(String producto){
        GoogleChromeDriver.driver.findElement(gwbicyclesPage.getBtnOpcionBuscar()).click();
        GoogleChromeDriver.driver.findElement(gwbicyclesPage.getBtnInputText()).click();
        GoogleChromeDriver.driver.findElement(gwbicyclesPage.getBtnInputText()).sendKeys(producto);
        GoogleChromeDriver.driver.findElement(gwbicyclesPage.getBtnBuscar()).click();
        GoogleChromeDriver.driver.findElement(gwbicyclesPage.getVerDetalle()).click();
        gwbicyclesPage.setVerTexto(producto);
    }

    public void validarElementoEnPantalla(String producto){
        gwbicyclesPage.setVerTexto(producto);
        String texto = GoogleChromeDriver.driver.findElement(gwbicyclesPage.getVerTexto()).getText();
        String[] s = texto.split(" ");
        String output ="";

        for (String word : s){
            String temp="";
            for (int i=0; i<word.length(); i++){
                if (i==0){
                    temp+=(word.charAt(i)+"").toUpperCase();
                }else {
                    temp+=(word.charAt(i)+"").toLowerCase();
                }
            }
            output+=temp+" ";
        }
        System.out.println(output);
        Assert.assertEquals(producto, output.trim());
    }
}
```

### 2.    Dentro de la carpeta <strong>*src*</strong>/<strong>*test*</strong> se encuentran las carpetas: 
> -    - Dentro de la carpeta <strong>*test*</strong>/<strong>*java*</strong> se encuentran las carpetas: 
>  - En la carpeta <strong>*excel*</strong> se encuentran el archivo que contiene todo el codigo que permite leer el archivo excel que con tiene la lista de productos que debemos buscar en la pagina.

```java
public class Excel {
    public static ArrayList<Map<String, String>> leerDatosDeHojaDeExcel(String rutaDeExcel, String hojaDeExcel) throws IOException {
        ArrayList<Map<String, String>> arrayListDataPlanWork = new ArrayList<Map<String, String>>();
        Map<String, String> informationProject;
        File file = new File(rutaDeExcel);
        FileInputStream inputStream = new FileInputStream(file);
        XSSFWorkbook newWorkbook = new XSSFWorkbook(inputStream);
        XSSFSheet newSheet = newWorkbook.getSheet(hojaDeExcel);
        Iterator<Row> rowIterator = newSheet.iterator();
        Row tittles = rowIterator.next();
        while (rowIterator.hasNext()) {
            informationProject = new HashMap<String, String>();
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                cell.getColumnIndex();
                switch (cell.getCellTypeEnum()) {
                    case STRING:
                        informationProject.put(tittles.getCell(cell.getColumnIndex()).toString(), cell.getStringCellValue());
                        break;
                    case NUMERIC:
                        informationProject.put(tittles.getCell(cell.getColumnIndex()).toString(), String.valueOf((long) cell.getNumericCellValue()));
                        break;
                    case BLANK:
                        informationProject.put(tittles.getCell(cell.getColumnIndex()).toString(), "");
                        break;
                    default:
                }
            }
            arrayListDataPlanWork.add(informationProject);
        }
        return arrayListDataPlanWork;
    }
}
```

>- -En la carpeta <strong>runners</strong> se declara el codigo que permite realizar la ejecucion del proyecto.
```java
package runners;
import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src\\test\\resources\\features",
        glue = "stepsDefinitions",
        snippets = SnippetType.CAMELCASE
)

public class GwbicyclesRunner {
}
```

>- -En la carpeta <strong>stepsDefinitions</strong> es donde se defininen los pasos en que se ejecuta el proyecto segun el ecenario que se desee probar.
```java
package stepsDefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import drivers.GoogleChromeDriver;
import excel.Excel;
import steps.GwbicyclesSteps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class GwbicyclesStepsDefinitions {

    public ArrayList<Map<String, String>> listaProductos;
    GwbicyclesSteps gwbicyclesSteps = new GwbicyclesSteps();

    @Given("^que me encuentro en la pagina de Gwbicycles$")
    public void queMeEncuentroEnLaPaginaDeGwbicycles() {
        gwbicyclesSteps.abrirPagina();
    }

    @When("^busque los productos '(.*)'$")
    public void busqueLosProductosExcelXlsx(String producto) throws IOException {

        listaProductos = Excel.leerDatosDeHojaDeExcel(producto,"Hoja1");

        for (Map<String, String> datos: listaProductos){
            gwbicyclesSteps.bucarProductosEnGwbicycles(datos.get("Producto"));
            gwbicyclesSteps.validarElementoEnPantalla(datos.get("Producto"));
        }

    }

    @Then("^podre ver los nombres en pantalla$")
    public void podreVerLosNombresEnPantalla() {
        GoogleChromeDriver.driver.quit();
    }
}

```
>- -En la carpeta <strong>resourse</strong>/<strong>features</strong> encontramos el archivo que contiene la declaracion de los pasos que conforman el ecenario a probar.
```java
Feature: HU-001 Buscar productos en gwbicycles
  yo como usuario de gwbicycles
  quiero buscar cinco productos para ver los nombres en pantalla

  Scenario: Buscar productos
    Given que me encuentro en la pagina de Gwbicycles
    When busque los productos 'Productos.xlsx'
    Then podre ver los nombres en pantalla
```

### Archivo *build.gradle* el cual se guardan toda la informacion de las dependencias utilizadas en el proyecto, 
```java
apply plugin: 'java-library'
apply plugin: 'net.serenity-bdd.aggregator'
apply plugin: 'eclipse'

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
}

buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
        jcenter()
    }
    dependencies {
        classpath("net.serenity-bdd:serenity-gradle-plugin:2.0.80")
    }
}

dependencies {
    implementation 'net.serenity-bdd:serenity-junit:2.0.80'
    implementation 'net.serenity-bdd:serenity-cucumber:1.9.45'
    implementation 'net.serenity-bdd:serenity-core:2.0.80'
    implementation 'org.slf4j:slf4j-simple:1.7.7'
    implementation group: 'org.apache.poi', name: 'poi', version: '3.17'
    implementation group: 'org.apache.poi', name: 'poi-ooxml', version: '3.17'
}

test {
    ignoreFailures = true
}
gradle.startParameter.continueOnFailure = true
```
