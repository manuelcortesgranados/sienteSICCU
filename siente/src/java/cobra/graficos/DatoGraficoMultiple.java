/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cobra.graficos;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un dato para la gráfica
 * @author Jhon Eduard Ortiz S
 */
public class DatoGraficoMultiple {

    /**
     * Valor que representa el eje x del gráfico
     */
    private String valorX;
    /**
     * Listado de valores que representan el eje Y del gráfico
     */
    private List<ValorGraficoMultiple> valoresY = new ArrayList<ValorGraficoMultiple>();

    public String getValorX() {
        return valorX;
    }

    public void setValorX(String valorX) {
        this.valorX = valorX;
    }

    public List<ValorGraficoMultiple> getValoresY() {
        return valoresY;
    }

    public void setValoresY(List<ValorGraficoMultiple> valoresY) {
        this.valoresY = valoresY;
    }

    public DatoGraficoMultiple() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DatoGraficoMultiple other = (DatoGraficoMultiple) obj;
        if ((this.valorX == null) ? (other.valorX != null) : !this.valorX.equals(other.valorX)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.valorX != null ? this.valorX.hashCode() : 0);
        return hash;
    }

}
