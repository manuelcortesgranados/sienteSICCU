/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.indicadores;

import com.interkont.cobra.util.DatoBarrasComparativo;
import java.util.List;

/**
 *
 * @author carlosloaiza
 */
public class GraficoBarrasComparativo {

    private String tituloCampo;
    private String valorCampo;
    private String valorCampo2;
    private String depth3D;
    private String angulo;
    private List<DatoBarrasComparativo> listadatos;
    private String nombreDiv;
    private String scriptbarra3d;
 

    public GraficoBarrasComparativo(String tituloCampo, String valorCampo, String valorCampo2, String depth3D, String angulo, List<DatoBarrasComparativo> listadatos, String nombreDiv, boolean rotate) {
        this.tituloCampo = tituloCampo;
        this.valorCampo = valorCampo;
        this.depth3D = depth3D;
        this.angulo = angulo;
        this.listadatos = listadatos;
        this.nombreDiv = nombreDiv;

        scriptbarra3d = "<script type=\"text/javascript\">var chart" + nombreDiv + ";var chartData" + nombreDiv + " = [";
        int i = 0;
        while (i < listadatos.size()) {

            scriptbarra3d = scriptbarra3d + "{" + tituloCampo + ": \"" + listadatos.get(i).getEtiqueta() + "\",";
            scriptbarra3d = scriptbarra3d + valorCampo + ": " + listadatos.get(i).getValor() + ",";
            scriptbarra3d = scriptbarra3d + valorCampo2 + ": " + listadatos.get(i).getValor2() + "}";
            i++;
            if (i < listadatos.size()) {
                scriptbarra3d = scriptbarra3d + ",";
            }
        }
        scriptbarra3d = scriptbarra3d + "];";
        
        int alto=30*listadatos.size();   
        if(listadatos.size()<=10)
        {
            alto=400;
        }    
             
        scriptbarra3d = scriptbarra3d + "AmCharts.ready(function () {"
                + "chart" + nombreDiv + " = new AmCharts.AmSerialChart();"
                + "chart" + nombreDiv + ".dataProvider = chartData" + nombreDiv + ";"
                + "chart" + nombreDiv + ".categoryField = \"" + tituloCampo + "\";"
                + "chart" + nombreDiv + ".rotate = "+rotate+";"
                + "chart" + nombreDiv + ".depth3D = " + depth3D + ";"
                + "chart" + nombreDiv + ".angle = " + angulo + ";"
                //+ "chart" + nombreDiv + ".columnWidth = " + 0.5 + ";"
                + "chart" + nombreDiv + ".startDuration = 1;"            
                + "chart" + nombreDiv + ".plotAreaBorderColor = \"#DADADA\";"  
                + "chart" + nombreDiv + ".plotAreaBorderAlpha = 1;"
                + "chart" + nombreDiv + ".marginBottom = 10;"
                             
                
                + "var categoryAxis = chart" + nombreDiv + ".categoryAxis;"
                + "categoryAxis.gridPosition = \"start\";"
                //+ "categoryAxis.axisColor = \"#DADADA\";"
                //+ "categoryAxis.fillAlpha = 1;"
                + "categoryAxis.gridAlpha = 0.1;"
                + "categoryAxis.gridAlpha = 0;"
                + "categoryAxis.labelRotation = 45;"
                + "categoryAxis.fontSize = 8;"
                
                
                //+ "categoryAxis.fillColor = \"#FAFAFA\";"
                
           
                
                
                + "var valueAxis = new AmCharts.ValueAxis();"
                //+ "valueAxis.axisColor = \"#DADADA\";"
                // //+"valueAxis.title = \"Aprobado miles de pesos, $\";"+
                +"valueAxis.gridAlpha = 0.1;"
                +"valueAxis.position = \"left\";"
                +"valueAxis.tickLength = 0;"
                
                
                
                
                //+ "valueAxis.minimum = 0;"
                //+ "valueAxis.step = 20000000;"
                //+ "valueAxis.maximum = 60000000;"
                + "chart" + nombreDiv + ".addValueAxis(valueAxis);"
                + "var graph = new AmCharts.AmGraph();"
                + "graph.title = \"" + valorCampo + "\";"
                + "graph.valueField = \"" + valorCampo + "\";"
                + "graph.type = \"column\";"
                + "graph.balloonText = \"" + valorCampo + " en [[category]]:[[value]]\";"
                + "graph.lineAlpha = 0;"
                + "graph.fillColors = \"#ADD981\";"
                + "graph.fillAlphas = 1;"
                +"graph.cornerRadiusTop = 22;"
                //+ "graph.colorField = \"color\";"
                //+ "graph.fontSize = 4;"
                
                + "chart" + nombreDiv + ".addGraph(graph);"
                
                + "var graph2 = new AmCharts.AmGraph();"
                + "graph2.title = \"" + valorCampo2 + "\";"
                + "graph2.valueField = \"" + valorCampo2 + "\";"
                + "graph2.type = \"column\";"
                + "graph2.balloonText = \"" + valorCampo2 + " en [[category]]:[[value]]\";"
                + "graph2.lineAlpha = 0;"
                + "graph2.fillColors = \"#81acd9\";"
                + "graph2.fillAlphas = 1;"
                //+ "graph2.colorField = \"color\";"
                //+ "graph2.fontSize = 4;"
                + "chart" + nombreDiv + ".addGraph(graph2);"
                
                +"var legend = new AmCharts.AmLegend();"
                +"chart" + nombreDiv + ".addLegend(legend);"
                
                + "chart" + nombreDiv + ".write(\"" + nombreDiv + "\");"
                + "});"
                + "</script><div id=\"" + nombreDiv + "\" style=\"width: 100%; height: "+alto+"px;\"></div>";


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

    public List<DatoBarrasComparativo> getListadatos() {
        return listadatos;
    }

    public void setListadatos(List<DatoBarrasComparativo> listadatos) {
        this.listadatos = listadatos;
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

    public String getScriptbarra3d() {
        return scriptbarra3d;
    }

    public void setScriptbarra3d(String scriptbarra3d) {
        this.scriptbarra3d = scriptbarra3d;
    }

    public String getValorCampo2() {
        return valorCampo2;
    }

    public void setValorCampo2(String valorCampo2) {
        this.valorCampo2 = valorCampo2;
    }
    
    
}
