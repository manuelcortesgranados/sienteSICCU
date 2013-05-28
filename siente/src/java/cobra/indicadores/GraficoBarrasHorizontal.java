
package cobra.indicadores;

import com.interkont.cobra.util.DatoPie;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author 
 */
public class GraficoBarrasHorizontal implements Serializable{


    private String scriptGrafico;
    private String nombreDiv;
    private String colorBarra;
    private String colorLabel;
    private List<DatoPie> listadatos;

    public String getColorLabel() {
        return colorLabel;
    }

    public void setColorLabel(String colorLabel) {
        this.colorLabel = colorLabel;
    }

    public String getColorBarra() {
        return colorBarra;
    }

    public void setColorBarra(String colorBarra) {
        this.colorBarra = colorBarra;
    }

    public String getNombreDiv() {
        return nombreDiv;
    }

    public List<DatoPie> getListadatos() {
        return listadatos;
    }

    public void setListadatos(List<DatoPie> listadatos) {
        this.listadatos = listadatos;
    }

    public void setNombreDiv(String nombreDiv) {
        this.nombreDiv = nombreDiv;
    }

    public String getScriptGrafico() {
        return scriptGrafico;
    }

    public void setScriptGrafico(String scriptGrafico) {
        this.scriptGrafico = scriptGrafico;
    }
 

    public GraficoBarrasHorizontal(String nombreDiv, String colorBarra, String colorLabel,List<DatoPie> listadatos) {
        
        
        StringBuilder scriptGraf = new StringBuilder();
        scriptGraf.append("<script type=\"text/javascript\">\n");
       
        //Código que configura el datasource del gráfico
        scriptGraf.append("var ").append(nombreDiv).append("Data = [\n");
        for (Iterator<DatoPie> it = listadatos.iterator(); it.hasNext();) {
            DatoPie elemento = it.next();
            scriptGraf.append("{etiqueta: \"").append(elemento.getEtiqueta()).append("\",\n");
            scriptGraf.append("valor:").append(elemento.getValor()).append("\n");
            scriptGraf.append("}\n");
            if (it.hasNext()) {
                scriptGraf.append(",\n");
            }
        }
        scriptGraf.append("];\n");
        //Fin del código del datasource
        scriptGraf.append("AmCharts.ready(function () {\n");
        // SERIAL CHART
        scriptGraf.append("var ").append(nombreDiv).append(" = new AmCharts.AmSerialChart();\n");
        scriptGraf.append(nombreDiv).append(".dataProvider = ").append(nombreDiv).append("Data;\n");
        scriptGraf.append(nombreDiv).append(".categoryField = \"etiqueta\";\n");
        scriptGraf.append(nombreDiv).append(".rotate = true;\n");
        scriptGraf.append(nombreDiv).append(".color = \"").append(colorLabel).append("\";\n");
        // this line makes the chart to show image in the background
        //chart.backgroundImage = \"bg.jpg\";
        // sometimes we need to set margins manually
        // autoMargins should be set to false in order chart to use custom margin values 
        scriptGraf.append(nombreDiv).append(".autoMargins = true;\n");
//        scriptGraf.append(nombreDiv).append(".marginTop = 0;\n");
//        scriptGraf.append(nombreDiv).append(".marginLeft = 0;\n");
//        scriptGraf.append(nombreDiv).append(".marginRight = 0;\n");
        scriptGraf.append(nombreDiv).append(".startDuration = 2;\n");
        scriptGraf.append(nombreDiv).append(".depth3D = 20;\n");
        scriptGraf.append(nombreDiv).append(".angle = 15;\n");
        scriptGraf.append(nombreDiv).append(".columnWidth = 0.6;\n");
        // AXES
        // category
        scriptGraf.append("var categoryAxis = ").append(nombreDiv).append(".categoryAxis;\n");
        scriptGraf.append("categoryAxis.gridAlpha = 0;\n");
        scriptGraf.append("categoryAxis.axisAlpha = 0;\n");
        scriptGraf.append("categoryAxis.labelsEnabled = true;\n");
         scriptGraf.append("categoryAxis.axisColor = \"#AA975E\";\n");
        // value
        scriptGraf.append("var valueAxis = new AmCharts.ValueAxis();\n");
        scriptGraf.append("valueAxis.gridAlpha = 0;\n");
        scriptGraf.append("valueAxis.axisAlpha = 0;\n");
        scriptGraf.append("valueAxis.labelsEnabled = false;\n");
        scriptGraf.append("valueAxis.minimum = 0;\n");
        scriptGraf.append(nombreDiv).append(".addValueAxis(valueAxis);\n");
        // GRAPH
        scriptGraf.append("var graph = new AmCharts.AmGraph();\n");
        scriptGraf.append("graph.balloonText = \"[[etiqueta]]: [[valor]]\";\n");
        scriptGraf.append("graph.valueField = \"valor\"\n");
        scriptGraf.append("graph.type = \"column\";\n");
        scriptGraf.append("graph.lineAlpha = 0;\n");
        scriptGraf.append("graph.fillAlphas = 0.85;\n");
        // you can pass any number of colors in array to create more fancy gradients
        scriptGraf.append("graph.fillColors = [\"").append(colorBarra).append("\"];\n");
        scriptGraf.append("graph.gradientOrientation = \"horizontal\";\n");
        scriptGraf.append("graph.labelPosition = \"top\";\n");
        scriptGraf.append("graph.labelText = \"[[valor]]\";\n");
        scriptGraf.append("graph.balloonText = \"[[etiqueta]]: [[valor]] \";\n");
        scriptGraf.append(nombreDiv).append(".addGraph(graph);\n");
        // LABEL
        //scriptGraf.append(nombreDiv).append(".addLabel(50, 40, \"ASISTENCIA HUMANITARIA\", \"left\", 15, \"#000000\", 0, 1, true);\n");
        // WRITE
        scriptGraf.append(nombreDiv).append(".write(\"").append(nombreDiv).append("\");\n");
        scriptGraf.append("});\n");
        scriptGraf.append("</script>\n");
        scriptGrafico = scriptGraf.toString();

    }  
}