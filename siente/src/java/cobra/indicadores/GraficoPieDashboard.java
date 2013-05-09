/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.indicadores;

import com.interkont.cobra.util.DatoPie;
import java.util.List;

/**
 *
 * @author carlosloaiza
 */
public class GraficoPieDashboard {

    private String tituloCampo;
    private String valorCampo;
    private String colorLinea;
    private String lineaAlpha;
    private String puntosLinea;
    private String depth3D;
    private String angulo;
    private List<DatoPie> listadatos;
    private String nombreDiv;
    private String scriptPieDashboard;
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

    public GraficoPieDashboard(String tituloCampo, String valorCampo, String descriptionField, String colorLinea, String lineaAlpha, String puntosLinea, String depth3D, String angulo, List<DatoPie> listadatos, String nombreDiv) {
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

        scriptPieDashboard = "<script type=\"text/javascript\">var chart;var chartData = [";
        int i = 0;
        while (i < listadatos.size()) {

            scriptPieDashboard = scriptPieDashboard + "{" + tituloCampo + ": \"" + listadatos.get(i).getEtiqueta() + "\",";
            scriptPieDashboard = scriptPieDashboard + valorCampo + ": Math.round(" + listadatos.get(i).getValor() + "), ";
            scriptPieDashboard = scriptPieDashboard + "show: true, ";

            scriptPieDashboard = scriptPieDashboard + descriptionField + ": \"" + listadatos.get(i).getDescripcion();
            scriptPieDashboard = scriptPieDashboard + "\"}";

            i++;
            if (i < listadatos.size()) {
                scriptPieDashboard = scriptPieDashboard + ",";
            }
        }
        scriptPieDashboard = scriptPieDashboard + "];";

        scriptPieDashboard = scriptPieDashboard + "AmCharts.ready(function () {\n";
        scriptPieDashboard = scriptPieDashboard + "var legend = new AmCharts.AmLegend();\n";
        scriptPieDashboard = scriptPieDashboard + "legend.valueText = \"[[proyectospie]]\";\n";
        scriptPieDashboard = scriptPieDashboard + "chart = new AmCharts.AmPieChart();\n";
        scriptPieDashboard = scriptPieDashboard + "chart.dataProvider = chartData;\n";
        scriptPieDashboard = scriptPieDashboard + "chart.titleField = \"" + tituloCampo + "\";\n";
        scriptPieDashboard = scriptPieDashboard + "chart.valueField = \"" + valorCampo + "\";\n";
        scriptPieDashboard = scriptPieDashboard + "chart.descriptionField = \"" + descriptionField + "\";\n";

        scriptPieDashboard = scriptPieDashboard + "chart.outlineColor = \"" + colorLinea + "\";\n";
        scriptPieDashboard = scriptPieDashboard + "chart.outlineAlpha = " + lineaAlpha + ";\n";
        scriptPieDashboard = scriptPieDashboard + "chart.outlineThickness = " + puntosLinea + ";\n";
        scriptPieDashboard = scriptPieDashboard + "chart.depth3D = "+depth3D+";\n";
        scriptPieDashboard = scriptPieDashboard + "chart.angle = " + angulo + ";\n";
        scriptPieDashboard = scriptPieDashboard + "chart.minRadius = " + 10 + ";\n";
        scriptPieDashboard = scriptPieDashboard + "chart.startDuration = 3;\n";
        scriptPieDashboard = scriptPieDashboard + "chart.visibleInLegendField = \"show\";\n";
        scriptPieDashboard = scriptPieDashboard + "chart.labelsEnabled = true;\n";
        scriptPieDashboard = scriptPieDashboard + "chart.labelText= \"[[percents]]%\";\n";
        scriptPieDashboard = scriptPieDashboard + "chart.balloonText= \"[[title]]: ([[value]])\";\n";
        scriptPieDashboard = scriptPieDashboard + "chart.color=\"#FFFFFF\";\n";
        scriptPieDashboard = scriptPieDashboard + "chart.labelRadius = -30;\n";
        scriptPieDashboard = scriptPieDashboard + "chart.addLegend(legend);\n";
        scriptPieDashboard = scriptPieDashboard + "chart.write(\"" + nombreDiv + "\");\n});\n</script>";


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

    public String getScriptPieDashboard() {
        return scriptPieDashboard;
    }

    public void setScriptPieDashboard(String scriptPieDashboard) {
        this.scriptPieDashboard = scriptPieDashboard;
    }

    public String getDescriptionField() {
        return descriptionField;
    }

    public void setDescriptionField(String descriptionField) {
        this.descriptionField = descriptionField;
    }
}
