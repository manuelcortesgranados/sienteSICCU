/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.graficos;

import java.io.Serializable;

/**
 * Clase que representa un dato para la gráfica
 *
 * @author Jhon Eduard Ortiz S
 */
public class DatoGrafico implements Serializable {

    /**
     * Valor que representa el eje x del gráfico
     */
    private String valorX;
    /**
     * Valor que represente el eje Y del gráfico
     */
    private String valorY;
    /**
     * Etiqueta relacionada al dato
     */
    private String etiqueta;

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getValorX() {
        return valorX;
    }

    public void setValorX(String valorX) {
        this.valorX = valorX;
    }

    public String getValorY() {
        return valorY;
    }

    public void setValorY(String valorY) {
        this.valorY = valorY;
    }

    public DatoGrafico() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DatoGrafico other = (DatoGrafico) obj;
        if ((this.valorX == null) ? (other.valorX != null) : !this.valorX.equals(other.valorX)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + (this.valorX != null ? this.valorX.hashCode() : 0);
        return hash;
    }
}
