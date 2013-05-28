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
public class GraficoColumnaPorcentual implements Serializable{

    private String tituloCampo;
    private String valorCampo;
    private String depth3D;
    private String angulo;
    private List<DatoPie> listadatos;
    private String nombreDiv;
    private String scriptcolumnaporcentual;

    public GraficoColumnaPorcentual(String tituloCampo, String valorCampo, String depth3D, String angulo, List<DatoPie> listadatos, String nombreDiv) {
        this.tituloCampo = tituloCampo;
        this.valorCampo = valorCampo;
        this.depth3D = depth3D;
        this.angulo = angulo;
        this.listadatos = listadatos;
        this.nombreDiv = nombreDiv;



        scriptcolumnaporcentual = "<script type=\"text/javascript\">var chart" + nombreDiv + ";var chartData" + nombreDiv + " = [{";
        int i = 0;
        while (i < listadatos.size()) {

            scriptcolumnaporcentual = scriptcolumnaporcentual +  listadatos.get(i).getEtiqueta() + ": \"" + listadatos.get(i).getValor()+"\"";
            //scriptcolumnaporcentual = scriptcolumnaporcentual + "color: \"" + listadatos.get(i).getDescripcion() ;
            i++;
            if (i < listadatos.size()) {
                scriptcolumnaporcentual = scriptcolumnaporcentual + ",";
            }
        }
        scriptcolumnaporcentual = scriptcolumnaporcentual+ "}];";


        scriptcolumnaporcentual = scriptcolumnaporcentual + "AmCharts.ready(function () {"
                + "chart" + nombreDiv + " = new AmCharts.AmSerialChart();"
                + "chart" + nombreDiv + ".dataProvider = chartData" + nombreDiv + ";"
                + "chart" + nombreDiv + ".categoryField = \"" + tituloCampo + "\";"
                + "chart" + nombreDiv + ".depth3D = " + depth3D + ";"
                + "chart" + nombreDiv + ".angle = " + angulo + ";"
                + "chart" + nombreDiv + ".startDuration = 5;"
                //"chart"+nombreDiv+".columnWidth = "+0.5+";"+
               
                +"var categoryAxis = chart" + nombreDiv + ".categoryAxis;"
                + "categoryAxis.gridPosition = \"start\";"
                + //"categoryAxis.axisColor = \"#DADADA\";"+
                "categoryAxis.axisAlpha = 0;"
                + "categoryAxis.gridAlpha = 0;"
                + // "categoryAxis.fillColor = \"#FAFAFA\";"+
                "var valueAxis = new AmCharts.ValueAxis();"
               // + "valueAxis.axisColor = \"#DADADA\";"
                + "valueAxis.stackType = \"100%\";"+
                "valueAxis.gridAlpha = 0;"
                //+ "valueAxis.minimum = 0;"
                + "valueAxis.axisAlpha = 0;"
                + "valueAxis.labelsEnabled = false;"
                + "chart" + nombreDiv + ".addValueAxis(valueAxis);";
                
                i = 0;
        String graf="";
        while (i < listadatos.size()) {
            graf=graf+"var graph = new AmCharts.AmGraph();"
            + "graph.title = \"" + listadatos.get(i).getEtiqueta() + "\";"
                + "graph.valueField = \"" + listadatos.get(i).getEtiqueta() + "\";"
                + "graph.type = \"column\";"
                + "graph.balloonText = \"[[value]] ([[percents]]%)\";"
                + "graph.lineAlpha = 0;"
                //+ "graph.fillColors = \"#bf1c25\";"
                + "graph.fillAlphas = 1;"
                + "graph.lineColor = \""+listadatos.get(i).getDescripcion()+"\";"
                + "graph.fontSize = 4;"
                + "chart" + nombreDiv + ".addGraph(graph);";

           i++;
        }
                scriptcolumnaporcentual=scriptcolumnaporcentual+graf
                
                              
                + "chart" + nombreDiv + ".write(\"" + nombreDiv + "\");"
                + "});"
                + "</script><div id=\"" + nombreDiv + "\" style=\"width: 100px; height: 80px;\"></div>";


    }

    public String getAngulo() {
        return angulo;
    }

    public void setAngulo(String angulo) {
        this.angulo = angulo;
    }

    public String getDepth3D() {
        return depth3D;
    }

    public void setDepth3D(String depth3D) {
        this.depth3D = depth3D;
    }

    public List<DatoPie> getListadatos() {
        return listadatos;
    }

    public void setListadatos(List<DatoPie> listadatos) {
        this.listadatos = listadatos;
    }

    public String getNombreDiv() {
        return nombreDiv;
    }

    public void setNombreDiv(String nombreDiv) {
        this.nombreDiv = nombreDiv;
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

    public String getScriptcolumnaporcentual() {
        return scriptcolumnaporcentual;
    }

    public void setScriptcolumnaporcentual(String scriptcolumnaporcentual) {
        this.scriptcolumnaporcentual = scriptcolumnaporcentual;
    }
}
