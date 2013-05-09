/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cobra.graficos;

/**
 * Clase que represente un valor para graficos múltples
 * @author Jhon Eduard Ortiz S
 */
public class ValorGraficoMultiple {

    /**
     * Identificador del conjunto de datos al que pertenece el valor (No debe
     * contener espacios en blanco)
     */
    private String codigoConjuntoDatos;

    /**
     * Valor correspondiente al eje Y del gráfico
     */
    private String valor;
    /**
     * Etiqueta que identifica la línea a la que pertenece el valor
     */
    private String etiqueta;

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiquetaGrafico) {
        this.etiqueta = etiquetaGrafico;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getCodigoConjuntoDatos() {
        return codigoConjuntoDatos;
    }

    public void setCodigoConjuntoDatos(String codigoConjuntoDatos) {
        this.codigoConjuntoDatos = codigoConjuntoDatos;
    }

    public ValorGraficoMultiple() {
    }

}
