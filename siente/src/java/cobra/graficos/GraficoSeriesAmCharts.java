/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.graficos;

import java.math.BigDecimal;

/**
 * Clase que permite la generación de un gráfico de líneas de amcharts
 *
 * @author Jhon Eduard Ortiz S
 */
public class GraficoSeriesAmCharts extends GraficoSeries {

    private String nombreDiv;

    public String getNombreDiv() {
        return nombreDiv;
    }

    public void setNombreDiv(String nombreDiv) {
        this.nombreDiv = nombreDiv;
    }

    public GraficoSeriesAmCharts(String nombreDiv) {
        this.nombreDiv = nombreDiv;
    }

    @Override
    public void generarGrafico() {
        cargarDatosGraficoMultiple();
        int countDatosGraficoMultiple = 1;
        StringBuilder script = new StringBuilder();

        script.append("<script type='text/javascript'>\n");

        //Código que configura el datasource del gráfico
        script.append("var ").append(nombreDiv).append("Data = [\n");
        for (DatoGraficoMultiple datoGraficoMultiple : datosGraficoMultiple) {
            script.append("{\n");
            for (ValorGraficoMultiple valorGraficoMultiple : datoGraficoMultiple.getValoresY()) {
                if (valorGraficoMultiple.getValor() != null) {
                    script.append("etiqueta").append(valorGraficoMultiple.getCodigoConjuntoDatos()).append(":'").append(valorGraficoMultiple.getEtiqueta()).append("',\n");
                    script.append("valor").append(valorGraficoMultiple.getCodigoConjuntoDatos()).append(":'").append(valorGraficoMultiple.getValor()).append("',\n");
                }
            }
            if (tipoDatoEjeX == Grafico.FECHA) {
                script.append("valorX: ").append("new Date(").append(datoGraficoMultiple.getValorX()).append(")\n");
            } else {
                script.append("valorX: '").append(datoGraficoMultiple.getValorX()).append("'\n");
            }
            script.append("}\n");
            if (countDatosGraficoMultiple < datosGraficoMultiple.size()) {
                script.append(",\n");
            }
            countDatosGraficoMultiple++;
        }
        script.append("];");
        script.append("\n\n");

        //Fin del código del datasource
        script.append("AmCharts.ready(function () {");
        script.append("\n\n");
        // SERIAL CHART
        script.append("var ").append(nombreDiv).append(" = new AmCharts.AmSerialChart();\n");
        if (estilo.isAgregarimagenes()) {
            script.append(nombreDiv).append(".pathToImages ='").append(estilo.getRutaimages()).append("';\n");
        }
        script.append(nombreDiv).append(".dataProvider = ").append(nombreDiv).append("Data;\n");
        script.append(nombreDiv).append(".categoryField = 'valorX';\n");
        if (estilo.isAnimado()) {
            script.append(nombreDiv).append(".startDuration = 2;\n");
        }
        if (estilo.isRotate()) {
            script.append(nombreDiv).append(".rotate = true;\n");
        }
        // the following lines makes chart 3D
        if (estilo.isTresD()) {
            script.append(nombreDiv).append(".depth3D = 20").append(";\n");
            script.append(nombreDiv).append(".angle = 30").append(";\n");
        }
        if (titulo != null) {
            script.append(nombreDiv).append(".addTitle('").append(titulo).append("');\n");
        }

        if (estilo.getColorTexto() != null) {
            script.append(nombreDiv).append(".color='").append(estilo.getColorTexto()).append("';\n");
        }

        if (estilo.getTamanoTexto() != null) {
            script.append(nombreDiv).append(".fontSize='").append(estilo.getTamanoTexto()).append("';\n");
        }

        if (estilo.getTipoTexto() != null) {
            script.append(nombreDiv).append(".fontFamily='").append(estilo.getTipoTexto()).append("';\n");
        }

        script.append("\n\n");

        // AXES
        // category
        script.append("var categoryAxis = ").append(nombreDiv).append(".categoryAxis;\n");
        if (tipoDatoEjeX == Grafico.FECHA) {
            script.append("categoryAxis.parseDates = true;\n");
            script.append("categoryAxis.minPeriod = 'DD';\n");
//            script.append("categoryAxis.equalSpacing = true;\n");
        }
        if (tituloEjeX != null) {
            script.append("categoryAxis.title = '").append(tituloEjeX).append("';\n");
        }
        if (estilo.isAvancefisicozoom()) {
            script.append("categoryAxis.labelsEnabled = false;\n");
            script.append("categoryAxis.gridAlpha = 0;\n");
            script.append("categoryAxis.axisAlpha = 0;\n");
        }
        if (estilo.isAvancefisicosiente()) {
            script.append("categoryAxis.gridAlpha = 0.15;\n");
        }
        if (estilo.isEvolucionproyectociudadano()) {
            script.append("categoryAxis.gridPosition = 'start';\n\n");
        } else {
            script.append("categoryAxis.dashLength = 2  ;\n");
            script.append("categoryAxis.axisColor = '");
            script.append(estilo.getColorlineasplano());
            script.append("';\n");
            script.append("\n\n");
        }

        // value
        script.append("var valueAxis = new AmCharts.ValueAxis();\n");
        if (tituloEjeY != null) {
            script.append("valueAxis.title = '").append(tituloEjeY).append("';\n");
        }
        if (estilo.getColorEjeY() != null) {
            script.append("valueAxis.axisColor = '").append(estilo.getColorEjeY()).append("';\n");
        }

        if (estilo.getTipoPila() != null) {
            script.append("valueAxis.stackType = '").append(estilo.getTipoPila()).append("';\n");
        }

        if (valorYMaximo != null) {
            if (!estilo.isAvancefisicozoom()) {
                script.append("valueAxis.maximum = '").append(valorYMaximo).append("';\n");
            } else {
                script.append("valueAxis.minimum = 0;\n");
            }
        }
        if (estilo.isEvolucionproyectociudadano()) {
            script.append("valueAxis.dashLength = 5;\n");
            script.append("valueAxis.integersOnly = true;\n");
            script.append("valueAxis.gridCount = 10;\n");
        } else {
            script.append("valueAxis.dashLength = 2;\n");
            script.append("valueAxis.axisThickness = 2;\n");
            script.append("valueAxis.gridAlpha = ").append(estilo.getGridalpha()).append(" \n");
        }
        if (estilo.isOcultarEjeY()) {
            script.append("valueAxis.labelsEnabled = false;\n");
            script.append("valueAxis.axisAlpha = 0;\n");
        }

        script.append(nombreDiv).append(".addValueAxis(valueAxis);");
        script.append("\n\n");

        // GRAPH
        for (ConjuntoDatosGrafico conjuntoDatosGrafico : conjuntosDatosGrafico) {
            script.append("var graph").append(conjuntoDatosGrafico.getCodigo())
                    .append(" = new AmCharts.AmGraph();\n");

            if (tipoGrafico == TIPO_COLUMNAS) {
                script.append("graph").append(conjuntoDatosGrafico.getCodigo())
                        .append(".type = 'column'").append(";\n");

                script.append("graph").append(conjuntoDatosGrafico.getCodigo())
                        .append(".lineAlpha = 0").append(";\n");

                script.append("graph").append(conjuntoDatosGrafico.getCodigo())
                        .append(".fillAlphas = 0.8").append(";\n");
            }

            if (conjuntoDatosGrafico.getEstilo().getColorTexto() != null) {
                script.append("graph").append(conjuntoDatosGrafico.getCodigo())
                        .append(".color = '").append(conjuntoDatosGrafico.getEstilo().getColorTexto()).append("';\n");
            }

            script.append("graph").append(conjuntoDatosGrafico.getCodigo());
            script.append(".valueAxis = valueAxis").append(";\n");

            if (conjuntoDatosGrafico.getEtiqueta() != null) {
                script.append("graph").append(conjuntoDatosGrafico.getCodigo());
                script.append(".title = '");
                script.append(conjuntoDatosGrafico.getEtiqueta()).append("';\n");
            }

            if (tipoGrafico == TIPO_LINEAS) {
                if (conjuntoDatosGrafico.getEstilo().getColorSerie() != null) {
                    script.append("graph").append(conjuntoDatosGrafico.getCodigo());
                    script.append(".lineColor = '");
                    script.append(conjuntoDatosGrafico.getEstilo().getColorSerie()).append("';\n");
                }

                if (conjuntoDatosGrafico.getEstilo().getFigura() != null) {
                    script.append("graph").append(conjuntoDatosGrafico.getCodigo());
                    script.append(".bullet = '");
                    script.append(conjuntoDatosGrafico.getEstilo().getFigura()).append("';\n");
                }
            }

            if ((conjuntoDatosGrafico.getEstilo().getColorSerie() != null) && (!estilo.isEvolucionproyectociudadano())) {
                if (conjuntoDatosGrafico.getEstilo().getColorSerie2() == null) {
                    script.append("graph").append(conjuntoDatosGrafico.getCodigo());
                    script.append(".fillColors = ['");
                    script.append(conjuntoDatosGrafico.getEstilo().getColorSerie());
                    script.append("'];\n");
                } else {
                    script.append("graph").append(conjuntoDatosGrafico.getCodigo());
                    script.append(".fillColors = ['");
                    script.append(conjuntoDatosGrafico.getEstilo().getColorSerie());
                    script.append("','");
                    script.append(conjuntoDatosGrafico.getEstilo().getColorSerie2());
                    script.append("'];\n");
                }
            }
            if (estilo.isDegradadohorizontal()) {
                script.append("graph").append(conjuntoDatosGrafico.getCodigo());
                script.append(".gradientOrientation = 'horizontal';\n");
            }else{
                script.append("graph").append(conjuntoDatosGrafico.getCodigo());
                script.append(".gradientOrientation = 'vertical';\n");
            }

            if (estilo.isAvancefisicozoom()) {
                script.append("graph").append(conjuntoDatosGrafico.getCodigo());
                script.append(".labelPosition = 'bottom';\n");
            }

            if (estilo.isPorcentaje()) {
                if (estilo.isAvancefisicozoom()) {
                    script.append("graph").append(conjuntoDatosGrafico.getCodigo());
                    script.append(".labelText = '[[valorX]]");
                    script.append(" : [[");
                    script.append("valor").append(conjuntoDatosGrafico.getCodigo());
                    script.append("]]%'").append(";\n");
                    script.append("graph").append(conjuntoDatosGrafico.getCodigo());
                    script.append(".balloonText = '[[valorX]]");
                    script.append(" : [[");
                    script.append("valor").append(conjuntoDatosGrafico.getCodigo());
                    script.append("]]%'").append(";\n");
                } else {
                    script.append("graph").append(conjuntoDatosGrafico.getCodigo());
                    script.append(".labelText = '[[");
                    script.append("valor").append(conjuntoDatosGrafico.getCodigo());
                    script.append("]]%'").append(";\n");

                    script.append("graph").append(conjuntoDatosGrafico.getCodigo());
                    script.append(".balloonText = '[[");
                    script.append("valor").append(conjuntoDatosGrafico.getCodigo());
                    script.append("]]%'").append(";\n");
                }
            }
            if (estilo.isEvolucionproyectociudadano()) {
                script.append("graph").append(conjuntoDatosGrafico.getCodigo());
                script.append(".bulletBorderColor = '");
                script.append(conjuntoDatosGrafico.getEstilo().getColorSerie()).append("';\n");
            }

            script.append("graph").append(conjuntoDatosGrafico.getCodigo());
            script.append(".valueField = 'valor");
            script.append(conjuntoDatosGrafico.getCodigo()).append("';");
            script.append("\n\n");

            script.append(nombreDiv).append(".addGraph(graph");
            script.append(conjuntoDatosGrafico.getCodigo()).append(");");
            script.append("\n\n");
        }

        // CURSOR
        if (estilo.isVerCursor()) {
            script.append("var chartCursor = new AmCharts.ChartCursor();\n");
            script.append("chartCursor.cursorPosition = 'mouse';\n");
            script.append("chartCursor.zoomable=");
            script.append(estilo.isZoomcursor());
            script.append(";\n");
            script.append("chartCursor.color='");
            script.append(estilo.getColortextocursor());
            script.append("';\n");
            script.append("chartCursor.cursorColor='");
            script.append(estilo.getColorfondoocursor());
            script.append("';\n");
            script.append(nombreDiv).append(".addChartCursor(chartCursor);");
            script.append("\n\n");
        }

        // SCROLLBAR
        if (estilo.isVerScroll()) {
            script.append("var chartScrollbar = new AmCharts.ChartScrollbar();\n");
            script.append(nombreDiv).append(".addChartScrollbar(chartScrollbar);");
            script.append("\n\n");
        }

        // LEGEND
        if (estilo.isVerLeyenda()) {
            script.append("var legend = new AmCharts.AmLegend();\n");
            script.append("legend.useGraphSettings = ");
            script.append(estilo.isUselinealegenda());
            script.append(";\n");
            script.append("legend.markerLabelGap=80;\n");
            script.append("legend.top=-5;\n");
            script.append(nombreDiv).append(".addLegend(legend);\n\n");
        }
        // WRITE
        script.append(nombreDiv).append(".write('").append(nombreDiv).append("');\n");
        script.append("});\n");
        script.append("</script>\n");

        scriptGrafico = script.toString();

    }
}
