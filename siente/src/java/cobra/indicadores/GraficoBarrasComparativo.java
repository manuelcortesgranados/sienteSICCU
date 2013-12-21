/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.indicadores;

import com.interkont.cobra.util.DatoBarrasComparativo;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author carlosloaiza
 */
public class GraficoBarrasComparativo implements Serializable {

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

        scriptbarra3d = "<script type=\"text/javascript\">var chart" + nombreDiv + ";\n var chartData" + nombreDiv + " = [";
        int i = 0;
        while (i < listadatos.size()) {

            scriptbarra3d = scriptbarra3d + "\n{\n" + tituloCampo + ": \"" + listadatos.get(i).getEtiqueta() + "\",\n";
            scriptbarra3d = scriptbarra3d + valorCampo + ": " + listadatos.get(i).getValor() + ",\n";
            scriptbarra3d = scriptbarra3d + valorCampo2 + ": " + listadatos.get(i).getValor2() + "\n}";
            i++;
            if (i < listadatos.size()) {
                scriptbarra3d = scriptbarra3d + ",\n";
            }
        }
        scriptbarra3d = scriptbarra3d + "];\n\n";
        
        int alto=30*listadatos.size();   
        if(listadatos.size()<=10)
        {
            alto=400;
        }    
             
        scriptbarra3d = scriptbarra3d + "AmCharts.ready(function () {\n\n"
                + "chart" + nombreDiv + " = new AmCharts.AmSerialChart();\n"
                + "chart" + nombreDiv + ".dataProvider = chartData" + nombreDiv + ";\n"
                + "chart" + nombreDiv + ".categoryField = \"" + tituloCampo + "\";\n"
                + "chart" + nombreDiv + ".rotate = "+rotate+";\n"
                + "chart" + nombreDiv + ".depth3D = " + depth3D + ";\n"
                + "chart" + nombreDiv + ".angle = " + angulo + ";\n"
                //+ "chart" + nombreDiv + ".columnWidth = " + 0.5 + ";"
                + "chart" + nombreDiv + ".startDuration = 1;\n"            
                + "chart" + nombreDiv + ".plotAreaBorderColor = \"#DADADA\";\n"  
                + "chart" + nombreDiv + ".plotAreaBorderAlpha = 1;\n"
                + "chart" + nombreDiv + ".marginBottom = 10;\n"
                             
                
                + "var categoryAxis = chart" + nombreDiv + ".categoryAxis;\n"
                + "categoryAxis.gridPosition = \"start\";\n"
                //+ "categoryAxis.axisColor = \"#DADADA\";"
                //+ "categoryAxis.fillAlpha = 1;"
                + "categoryAxis.gridAlpha = 0.1;\n"
                + "categoryAxis.gridAlpha = 0;\n"
                + "categoryAxis.labelRotation = 45;\n"
                + "categoryAxis.fontSize = 8;\n\n"
                
                
                //+ "categoryAxis.fillColor = \"#FAFAFA\";"
                
           
                
                
                + "var valueAxis = new AmCharts.ValueAxis();\n"
                //+ "valueAxis.axisColor = \"#DADADA\";"
                // //+"valueAxis.title = \"Aprobado miles de pesos, $\";"+
                +"valueAxis.gridAlpha = 0.1;\n"
                +"valueAxis.position = \"left\";\n"
                +"valueAxis.tickLength = 0;\n\n"
                
                
                
                
                //+ "valueAxis.minimum = 0;"
                //+ "valueAxis.step = 20000000;"
                //+ "valueAxis.maximum = 60000000;"
                + "chart" + nombreDiv + ".addValueAxis(valueAxis);\n"
                + "var graph = new AmCharts.AmGraph();\n"
                + "graph.title = \"" + valorCampo + "\";\n"
                + "graph.valueField = \"" + valorCampo + "\";\n"
                + "graph.type = \"column\";\n"
                + "graph.balloonText = \"" + valorCampo + " en [[category]]:[[value]]\";\n"
                + "graph.lineAlpha = 0;\n"
                + "graph.fillColors = \"#ADD981\";\n"
                + "graph.fillAlphas = 1;\n"
                +"graph.cornerRadiusTop = 22;\n"
                //+ "graph.colorField = \"color\";"
                //+ "graph.fontSize = 4;"
                
                + "chart" + nombreDiv + ".addGraph(graph);\n\n"
                
                + "var graph2 = new AmCharts.AmGraph();\n"
                + "graph2.title = \"" + valorCampo2 + "\";\n"
                + "graph2.valueField = \"" + valorCampo2 + "\";"
                + "graph2.type = \"column\";\n"
                + "graph2.balloonText = \"" + valorCampo2 + " en [[category]]:[[value]]\";\n"
                + "graph2.lineAlpha = 0;\n"
                + "graph2.fillColors = \"#81acd9\";\n"
                + "graph2.fillAlphas = 1;"
                //+ "graph2.colorField = \"color\";"
                //+ "graph2.fontSize = 4;"
                + "chart" + nombreDiv + ".addGraph(graph2);\n"
                
                +"var legend = new AmCharts.AmLegend();\n"
                +"chart" + nombreDiv + ".addLegend(legend);\n"
                
                + "chart" + nombreDiv + ".write(\"" + nombreDiv + "\");\n"
                + "});\n"
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
