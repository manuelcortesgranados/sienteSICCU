/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cobra.graficos;

/**
 * Clase engargada de establecer el estilo de un elemento del gráfico
 * @author Jhon Eduard Ortiz S
 */
public class EstiloGrafico {

    private String colorFondo;
    private String colorTexto;
    private String colorSerie = "#000000";
    private String colorSerie2;
    private String colorEjeX;
    private String colorEjeY;
    private String figura = "round";
    private boolean tresD;
    private boolean verLeyenda;
    private boolean porcentaje;
    private boolean verScroll;
    private boolean verCursor;
    private boolean ocultarEjeY;
    private boolean animado;
    private boolean rotate;
    private boolean degradadohorizontal = false;
    private boolean avancefisico = false;
    private String tamanoTexto;
    private String tipoTexto;
    private String tipoPila;
    private String gridalpha = "0.15";
    private String colorlineasplano = "#AA975E";

    public boolean isAvancefisico() {
        return avancefisico;
    }

    public void setAvancefisico(boolean avancefisico) {
        this.avancefisico = avancefisico;
    }

    public String getColorlineasplano() {
        return colorlineasplano;
    }

    public void setColorlineasplano(String colorlineasplano) {
        this.colorlineasplano = colorlineasplano;
    }

    public boolean isDegradadohorizontal() {
        return degradadohorizontal;
    }

    public void setDegradadohorizontal(boolean degradadohorizontal) {
        this.degradadohorizontal = degradadohorizontal;
    }
    
    public String getGridalpha() {
        return gridalpha;
    }

    public void setGridalpha(String gridalpha) {
        this.gridalpha = gridalpha;
    }
    
    public boolean isRotate() {
        return rotate;
    }

    public void setRotate(boolean rotate) {
        this.rotate = rotate;
    }

    public boolean isAnimado() {
        return animado;
    }

    public void setAnimado(boolean animado) {
        this.animado = animado;
    }
    
    public String getColorEjeX() {
        return colorEjeX;
    }

    public void setColorEjeX(String colorEjeX) {
        this.colorEjeX = colorEjeX;
    }

    public String getColorEjeY() {
        return colorEjeY;
    }

    public void setColorEjeY(String colorEjeY) {
        this.colorEjeY = colorEjeY;
    }

    public String getColorSerie() {
        return colorSerie;
    }

    public void setColorSerie(String colorLinea) {
        this.colorSerie = colorLinea;
    }

    public String getColorSerie2() {
        return colorSerie2;
    }

    public void setColorSerie2(String colorSerie2) {
        this.colorSerie2 = colorSerie2;
    }

    public String getFigura() {
        return figura;
    }

    public void setFigura(String figura) {
        this.figura = figura;
    }

    public String getColorFondo() {
        return colorFondo;
    }

    public void setColorFondo(String colorFondo) {
        this.colorFondo = colorFondo;
    }

    public boolean isTresD() {
        return tresD;
    }

    public void setTresD(boolean tresD) {
        this.tresD = tresD;
    }

    public boolean isVerLeyenda() {
        return verLeyenda;
    }

    public void setVerLeyenda(boolean verLeyenda) {
        this.verLeyenda = verLeyenda;
    }

    public boolean isPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(boolean porcentaje) {
        this.porcentaje = porcentaje;
    }

    public boolean isVerCursor() {
        return verCursor;
    }

    public void setVerCursor(boolean verCursor) {
        this.verCursor = verCursor;
    }

    public boolean isVerScroll() {
        return verScroll;
    }

    public void setVerScroll(boolean verScroll) {
        this.verScroll = verScroll;
    }

    public boolean isOcultarEjeY() {
        return ocultarEjeY;
    }

    public void setOcultarEjeY(boolean ocultarEjeY) {
        this.ocultarEjeY = ocultarEjeY;
    }

    public String getColorTexto() {
        return colorTexto;
    }

    public void setColorTexto(String colorTexto) {
        this.colorTexto = colorTexto;
    }

    public String getTamanoTexto() {
        return tamanoTexto;
    }

    public void setTamanoTexto(String tamanoTexto) {
        this.tamanoTexto = tamanoTexto;
    }

    public String getTipoTexto() {
        return tipoTexto;
    }

    public void setTipoTexto(String tipoTexto) {
        this.tipoTexto = tipoTexto;
    }

    public String getTipoPila() {
        return tipoPila;
    }

    public void setTipoPila(String tipoPila) {
        this.tipoPila = tipoPila;
    }
    
    public EstiloGrafico() {
    }
}
