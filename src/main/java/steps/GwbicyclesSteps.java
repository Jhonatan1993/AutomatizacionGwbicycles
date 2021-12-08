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
