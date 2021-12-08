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
