/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.graficos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Clase que permite la generación de un gráfico de series (Líneas, Barras,
 * columnas...)
 *
 * @author Jhon Eduard Ortiz S
 */
public abstract class GraficoSeries extends Grafico implements Serializable {

    /**
     * Grafico de columnas
     */
    public static int TIPO_COLUMNAS = 1;
    /**
     * Gráfico de líneas
     */
    public static int TIPO_LINEAS = 2;
    /**
     * Tipo de gráfico (1 = Columnas, 2 = Líneas)
     */
    protected int tipoGrafico;
    /**
     * Conjunto de datos que se presentarán en el gráfico, cada elemento de la
     * lista corresponde a una de las series que se presentarán en el gráfico
     */
    protected List<ConjuntoDatosGrafico> conjuntosDatosGrafico = new ArrayList<ConjuntoDatosGrafico>();
    /**
     * Conjunto de datos que se presentarán en el gráfico, mezclando en un solo
     * objeto los valores correspondientes a varias series para el mismo ítem
     * del eje Y
     */
    protected List<DatoGraficoMultiple> datosGraficoMultiple = new ArrayList<DatoGraficoMultiple>();

    /**
     * Carga los datos que provienen de varios conjuntos de datos,mezclando en
     * un solo objeto los valores correspondientes a varias líneas para el mismo
     * ítem del eje Y
     */
    public void cargarDatosGraficoMultiple() {
        for (ConjuntoDatosGrafico conjuntoDatosGrafico : conjuntosDatosGrafico) {
            for (DatoGrafico datoGrafico : conjuntoDatosGrafico.getListaDatos()) {
                DatoGraficoMultiple datoGraficoMultiple = new DatoGraficoMultiple();
                datoGraficoMultiple.setValorX(datoGrafico.getValorX());
                ValorGraficoMultiple valorGraficoMultiple = new ValorGraficoMultiple();
                valorGraficoMultiple.setCodigoConjuntoDatos(conjuntoDatosGrafico.getCodigo());
                valorGraficoMultiple.setValor(datoGrafico.getValorY());
                valorGraficoMultiple.setEtiqueta(datoGrafico.getEtiqueta());
                if (!datosGraficoMultiple.contains(datoGraficoMultiple)) {
                    datoGraficoMultiple.getValoresY().add(valorGraficoMultiple);
                    datosGraficoMultiple.add(datoGraficoMultiple);
                } else {
                    datosGraficoMultiple.get(datosGraficoMultiple.indexOf(datoGraficoMultiple)).getValoresY().add(valorGraficoMultiple);
                }

            }
        }
        if (tipoDatoEjeX == Grafico.CADENA || tipoDatoEjeX == Grafico.FECHA) {
            Collections.sort(datosGraficoMultiple, new Comparator() {
                public int compare(Object o1, Object o2) {
                    DatoGraficoMultiple obj1 = (DatoGraficoMultiple) o1;
                    DatoGraficoMultiple obj2 = (DatoGraficoMultiple) o2;

                    if (obj1.getValorX().compareTo(obj2.getValorX()) > 0) {
                        return 1;
                    } else if (obj1.getValorX().compareTo(obj2.getValorX()) < 0) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
        }

        if (tipoDatoEjeX == Grafico.NUMERO) {
            Collections.sort(datosGraficoMultiple, new Comparator() {
                public int compare(Object o1, Object o2) {
                    DatoGraficoMultiple obj1 = (DatoGraficoMultiple) o1;
                    DatoGraficoMultiple obj2 = (DatoGraficoMultiple) o2;

                    if (new BigDecimal(obj1.getValorX()).compareTo(new BigDecimal(obj2.getValorX())) > 0) {
                        return 1;
                    } else if (new BigDecimal(obj1.getValorX()).compareTo(new BigDecimal(obj2.getValorX())) < 0) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
        }
    }

    public List<ConjuntoDatosGrafico> getConjuntosDatosGrafico() {
        return conjuntosDatosGrafico;
    }

    public void setConjuntosDatosGrafico(List<ConjuntoDatosGrafico> conjuntosDatosGrafico) {
        this.conjuntosDatosGrafico = conjuntosDatosGrafico;
    }

    public List<DatoGraficoMultiple> getDatosGraficoMultiple() {
        return datosGraficoMultiple;
    }

    public void setDatosGraficoMultiple(List<DatoGraficoMultiple> datosGraficoMultiple) {
        this.datosGraficoMultiple = datosGraficoMultiple;
    }

    public int getTipoGrafico() {
        return tipoGrafico;
    }

    public void setTipoGrafico(int tipoGrafico) {
        this.tipoGrafico = tipoGrafico;
    }
}
