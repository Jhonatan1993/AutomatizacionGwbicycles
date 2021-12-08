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
