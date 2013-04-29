/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cobra.graficos;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que encapsula un conjunto de datos que se presentarán en una gráfica
 * @author Jhon Eduard Ortiz S
 */
public class ConjuntoDatosGrafico {
    /**
     * Código que identifica al conjunto de datos. (No debe contener espacios)
     */
    private String codigo;
    
    /**
     * Etiqueta que se relacionará al conjunto de datos en la gráfica
     */
    private String etiqueta;

    /**
     * EstiloGrafico que se le aplicará al conjunto de datos en el gráfico
         */
    private EstiloGrafico estilo = new EstiloGrafico();

    /**
     * Conjunto de datos para ser presentados en un gráfico
     */
    private List<DatoGrafico> listaDatos = new ArrayList<DatoGrafico>();

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public EstiloGrafico getEstilo() {
        return estilo;
    }

    public void setEstilo(EstiloGrafico estilo) {
        this.estilo = estilo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public List<DatoGrafico> getListaDatos() {
        return listaDatos;
    }

    public void setListaDatos(List<DatoGrafico> listaDatos) {
        this.listaDatos = listaDatos;
    }

    public ConjuntoDatosGrafico() {
    }

}
