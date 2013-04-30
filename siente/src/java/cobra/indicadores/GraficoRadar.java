/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.indicadores;

import com.interkont.cobra.util.DatoPie;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author carlosloaiza
 */
public class GraficoRadar implements Serializable {

    private String tituloCampo;
    private String valorCampo;
    private String colorLinea;
    private String lineaAlpha;
    private String puntosLinea;
    private String depth3D;
    private String angulo;
    private List<DatoPie> listadatos;
    private String nombreDiv;
    private String scriptPie;
    private String descriptionField;
    /*
     alphaField	String	Name of the field in chart's dataProvider which holds slice's alpha.
     colorField	String	Name of the field in chart's dataProvider which holds slice's color.
     descriptionField	String	Name of the field in chart's dataProvider which holds a string with description.
     pulledField	String	Name of the field in chart's dataProvider which holds a boolean value telling the chart whether this slice must be pulled or not.
     titleField	String	Name of the field in chart's dataProvider which holds slice's title.
     urlField	String	Name of the field in chart's dataProvider which holds url which would be accessed if the user clicks on a slice.
     valueField	String	Name of the field in chart's dataProvider which holds slice's value.
     visibleInLegendField	String	Name of the field in chart's dataProvider which holds boolean variable defining whether this data item should have an entry in the legend.
    
     */

    public GraficoRadar(String tituloCampo, String valorCampo, String descriptionField, String colorLinea, String lineaAlpha, String puntosLinea, String depth3D, String angulo, List<DatoPie> listadatos, String nombreDiv) {
        this.tituloCampo = tituloCampo;
        this.valorCampo = valorCampo;
        this.colorLinea = colorLinea;
        this.lineaAlpha = lineaAlpha;
        this.puntosLinea = puntosLinea;
        this.depth3D = depth3D;
        this.angulo = angulo;
        this.listadatos = listadatos;
        this.nombreDiv = nombreDiv;
        this.descriptionField = descriptionField;

        /*
        
         AmCharts.ready(function () {
         // RADAR CHART
         chart = new AmCharts.AmRadarChart();
         chart.dataProvider = chartData;
         chart.categoryField = "country";
         chart.startDuration = 2;

         // VALUE AXIS
         var valueAxis = new AmCharts.ValueAxis();
         valueAxis.axisAlpha = 0.15;
         valueAxis.minimum = 0;
         valueAxis.dashLength = 3;
         valueAxis.axisTitleOffset = 20;
         valueAxis.gridCount = 5;
         chart.addValueAxis(valueAxis);

         // GRAPH
         var graph = new AmCharts.AmGraph();
         graph.valueField = "litres";
         graph.bullet = "round";
         graph.balloonText = "[[value]] litres of beer per year"
         chart.addGraph(graph);

         // WRITE
         chart.write("chartdiv");*/

        scriptPie = "<script type=\"text/javascript\">var chart" + nombreDiv + ";var chartData" + nombreDiv + " = [";
        int i = 0;
        while (i < listadatos.size()) {

            scriptPie = scriptPie + "{" + tituloCampo + ": \"" + listadatos.get(i).getEtiqueta() + "\",";
            scriptPie = scriptPie + valorCampo + ": " + listadatos.get(i).getValor() + "}";

            i++;
            if (i < listadatos.size()) {
                scriptPie = scriptPie + ",";
            }
        }
        scriptPie = scriptPie + "];";

        scriptPie = scriptPie + "AmCharts.ready(function () {"
                + "chart" + nombreDiv + " = new AmCharts.AmRadarChart();"
                + "chart" + nombreDiv + ".dataProvider = chartData" + nombreDiv + ";"
                + "chart" + nombreDiv + ".categoryField = \"" + tituloCampo + "\";"
                + "chart" + nombreDiv + ".startDuration = 2;"
                + "var valueAxis = new AmCharts.ValueAxis();"
                + "valueAxis.axisAlpha = 0.15;"
                + "valueAxis.minimum = 0;"
                + "valueAxis.dashLength = 3;"
                + "valueAxis.axisTitleOffset = 20;"
                + "valueAxis.gridCount = 5;"
                + "chart" + nombreDiv + ".addValueAxis(valueAxis);"
                + "var graph = new AmCharts.AmGraph();"
                + "graph.valueField = \"" + valorCampo + "\";"
                + "graph.bullet = \"round\";"
                + "graph.balloonText = \"[[value]] " + descriptionField + "\";"
                + "chart" + nombreDiv + ".addGraph(graph);";


        scriptPie = scriptPie + "chart" + nombreDiv + ".write(\"" + nombreDiv + "\");});</script>";

        scriptPie = scriptPie + "<div id=\"" + nombreDiv + "\" style=\"width: 100%; height: 600px;\"></div>";


    }

    public String getAngulo() {
        return angulo;
    }

    public void setAngulo(String angulo) {
        this.angulo = angulo;
    }

    public String getColorLinea() {
        return colorLinea;
    }

    public void setColorLinea(String colorLinea) {
        this.colorLinea = colorLinea;
    }

    public String getDepth3D() {
        return depth3D;
    }

    public void setDepth3D(String depth3D) {
        this.depth3D = depth3D;
    }

    public String getLineaAlpha() {
        return lineaAlpha;
    }

    public void setLineaAlpha(String lineaAlpha) {
        this.lineaAlpha = lineaAlpha;
    }

    public List<DatoPie> getListadatos() {
        return listadatos;
    }

    public void setListadatos(List<DatoPie> listadatos) {
        this.listadatos = listadatos;
    }

    public String getPuntosLinea() {
        return puntosLinea;
    }

    public void setPuntosLinea(String puntosLinea) {
        this.puntosLinea = puntosLinea;
    }

    public String getTituloCampo() {
        return tituloCampo;
    }

    public void setTituloCampo(String tituloCampo) {
        this.tituloCampo = tituloCampo;
    }

    public String getValorCampo() {
        return valorCampo;
    }

    public void setValorCampo(String valorCampo) {
        this.valorCampo = valorCampo;
    }

    public String getNombreDiv() {
        return nombreDiv;
    }

    public void setNombreDiv(String nombreDiv) {
        this.nombreDiv = nombreDiv;
    }

    public String getScriptPie() {
        return scriptPie;
    }

    public void setScriptPie(String scriptPie) {
        this.scriptPie = scriptPie;
    }

    public String getDescriptionField() {
        return descriptionField;
    }

    public void setDescriptionField(String descriptionField) {
        this.descriptionField = descriptionField;
    }
}
