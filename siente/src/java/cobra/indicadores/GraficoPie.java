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
public class GraficoPie implements Serializable{


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

    public GraficoPie(String tituloCampo, String valorCampo, String descriptionField, String colorLinea, String lineaAlpha, String puntosLinea, String depth3D, String angulo, List<DatoPie> listadatos, String nombreDiv)  {
        this.tituloCampo = tituloCampo;
        this.valorCampo = valorCampo;
        this.colorLinea = colorLinea;
        this.lineaAlpha = lineaAlpha;
        this.puntosLinea = puntosLinea;
        this.depth3D = depth3D;
        this.angulo = angulo;
        this.listadatos = listadatos;
        this.nombreDiv=nombreDiv;
        this.descriptionField=descriptionField;

        scriptPie="<script type=\"text/javascript\">var chart;\n\n var chartData = [\n";
        int i=0;
        while(i<listadatos.size())
        {

            scriptPie=scriptPie+"{\n"+tituloCampo+": \""+listadatos.get(i).getEtiqueta() +"\",\n";
            scriptPie=scriptPie+valorCampo+": "+listadatos.get(i).getValor()+", \n";
            scriptPie=scriptPie+"show: true, \n";

            scriptPie=scriptPie+descriptionField+": \""+listadatos.get(i).getDescripcion();
            scriptPie=scriptPie+"\"}\n";

            i++;
            if(i<listadatos.size())
            {
                scriptPie=scriptPie+",";
            }
        }
        scriptPie=scriptPie+"];\n\n";

        scriptPie=scriptPie+"AmCharts.ready(function () {\n";
        scriptPie=scriptPie+"var legend = new AmCharts.AmLegend();\n"
                + "legend.borderColor = \"black\";\nlegend.align = \"center\";\n\n";
                //+ "legend.addListener(\"clickLabel\", function (event) {window.location.href = event.dataItem.url;}"
                //+ ");";

        scriptPie=scriptPie+" chart = new AmCharts.AmPieChart();\n";
        scriptPie=scriptPie+"chart.dataProvider = chartData;\n";
        scriptPie=scriptPie+"chart.titleField = \""+tituloCampo+"\";\n";
        scriptPie=scriptPie+"chart.valueField = \""+valorCampo+"\";\n";
        scriptPie=scriptPie+"chart.descriptionField = \""+descriptionField+"\";\n";


        scriptPie=scriptPie+"chart.outlineColor = \""+colorLinea+"\";\n";
        scriptPie=scriptPie+"chart.outlineAlpha = "+lineaAlpha+";\n";
        scriptPie=scriptPie+"chart.outlineThickness = "+puntosLinea+";\n";
        scriptPie=scriptPie+"chart.depth3D = "+depth3D+";\n";
        scriptPie=scriptPie+"chart.angle = "+angulo+";\n";
        scriptPie=scriptPie+"chart.minRadius = "+200+";\n";
        scriptPie=scriptPie+"chart.visibleInLegendField = \"show\";\n";
         scriptPie=scriptPie+"chart.labelsEnabled = true;\n";
         scriptPie=scriptPie+"chart.labelRadius = 5;\n";
        scriptPie=scriptPie+"chart.addLegend(legend);\n\n";

        scriptPie=scriptPie+"chart.write(\""+nombreDiv+"\");\n});\n</script>";


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
