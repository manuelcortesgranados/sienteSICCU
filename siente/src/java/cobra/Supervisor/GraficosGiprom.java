/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.Supervisor;

import java.io.Serializable;

/**
 *
 * @author desarrollo3
 */
public class GraficosGiprom implements Serializable{
    
    private StringBuilder graficoNBI = new StringBuilder();
    private StringBuilder graficoNBIanual = new StringBuilder();
    private StringBuilder graficoNBILocalizacion = new StringBuilder();  

    public StringBuilder getGraficoNBI() {
        pintarGraficoNBI();
        return graficoNBI;
    }

    public void setGraficoNBI(StringBuilder graficoNBI) {
        this.graficoNBI = graficoNBI;
    }

    public StringBuilder getGraficoNBIanual() {
        pintarGraficoNBIanual();
        return graficoNBIanual;
    }

    public void setGraficoNBIanual(StringBuilder graficoNBIanual) {
        this.graficoNBIanual = graficoNBIanual;
    }

    public StringBuilder getGraficoNBILocalizacion() {
        pintarGraficoNBILocalidad();
        return graficoNBILocalizacion;
    }

    public void setGraficoNBILocalizacion(StringBuilder graficoNBILocalizacion) {
        this.graficoNBILocalizacion = graficoNBILocalizacion;
    }
    
    
    private void pintarGraficoNBI() {
        
        this.graficoNBI.append("<script type=\"text/javascript\">\n");
        this.graficoNBI.append("var chart;\n");
        this.graficoNBI.append("var arrow;\n");
        this.graficoNBI.append("var axis;\n");

        this.graficoNBI.append("AmCharts.ready(function() {\n");
        this.graficoNBI.append("chart = new AmCharts.AmAngularGauge();\n");
        this.graficoNBI.append("chart.color = \"#00B3DF\";\n");
        this.graficoNBI.append("chart.startDuration = 4;\n");

        this.graficoNBI.append("axis = new AmCharts.GaugeAxis();\n");
        this.graficoNBI.append("axis.startValue = 0;\n");
        this.graficoNBI.append("axis.endValue = 100;\n");
        this.graficoNBI.append("axis.axisColor = \"#00B3DF\";\n");
        this.graficoNBI.append("axis.tickColor = \"#00B3DF\";\n");
        this.graficoNBI.append("axis.axisThickness = 2;\n");
        this.graficoNBI.append("chart.addAxis(axis);\n");

        this.graficoNBI.append("arrow = new AmCharts.GaugeArrow();\n");
        this.graficoNBI.append("arrow.color = \"#00B3DF\";\n");
        this.graficoNBI.append("chart.addArrow(arrow);\n");
        this.graficoNBI.append("chart.write(\"graficoNBI\");\n");

        this.graficoNBI.append("arrow.setValue(60);\n");
        this.graficoNBI.append("axis.setBottomText(60 + \" %\");\n");
        this.graficoNBI.append("});\n");
        this.graficoNBI.append("</script>\n");

        
    }

    private void pintarGraficoNBIanual() {
        StringBuilder datosNBIanual = new StringBuilder();
        
        datosNBIanual.append("var chartData = [{\n");
        datosNBIanual.append("'anio': 2005,\n");
        datosNBIanual.append("    'nbi': 23.5\n");
        datosNBIanual.append("}, {\n");
        datosNBIanual.append("'anio': 2006,\n");
        datosNBIanual.append("'nbi': 26.2\n");
        datosNBIanual.append("}];\n");
        
        this.graficoNBIanual.append(pintarGraficoBarras(datosNBIanual, "anio", "nbi", "graficoNBIanual", "% NBI AL AÑO"));
    }

    private void pintarGraficoNBILocalidad() {
        StringBuilder datosNBIanual = new StringBuilder();
        
        datosNBIanual.append("var chartData = [{\n");
        datosNBIanual.append("'area': 'Urbano',\n");
        datosNBIanual.append("    'nbi': 23.5\n");
        datosNBIanual.append("}, {\n");
        datosNBIanual.append("'area': 'Rural',\n");
        datosNBIanual.append("'nbi': 26.2\n");
        datosNBIanual.append("}];\n");
        
        this.graficoNBILocalizacion.append(pintarGraficoBarras(datosNBIanual, "area", "nbi", "graficoNBILocalizacion", "% NBI POR LOCALIZACIÓN"));
    }
    
    private StringBuilder pintarGraficoBarras(StringBuilder datos, String ejex, String ejey, String id, String tool) {
        StringBuilder grafico = new StringBuilder();
        grafico.append("<script type=\"text/javascript\">\n");
        grafico.append("var chart;\n");

        grafico.append(datos);

        grafico.append("AmCharts.ready(function () {\n");

        grafico.append("chart = new AmCharts.AmSerialChart();\n");
        grafico.append("chart.dataProvider = chartData;\n");
        grafico.append("chart.categoryField = '");
        grafico.append(ejex);
        grafico.append("';\n");
        grafico.append("chart.rotate = true;\n");
        grafico.append("chart.depth3D = 10;\n");
        grafico.append("chart.angle = 30;\n");
        grafico.append("chart.startDuration = 4;\n");
        grafico.append("chart.color = '#58595B';\n");

        grafico.append("var categoryAxis = chart.categoryAxis;\n");
        grafico.append("categoryAxis.gridPosition = 'start';\n");
        grafico.append("categoryAxis.gridAlpha = 0.15;\n");
        grafico.append("categoryAxis.dashLength = 2  ;\n");
        grafico.append("categoryAxis.axisColor = '#CCC';\n");

        grafico.append("var valueAxis = new AmCharts.ValueAxis();\n");
        grafico.append("valueAxis.axisColor = '#CCC';\n");
        grafico.append("valueAxis.position = 'top';\n");
        grafico.append("valueAxis.minorGridEnabled = true;\n");
        grafico.append("valueAxis.maximum = '100';\n");
        grafico.append("valueAxis.minimum = '0';\n");
        grafico.append("valueAxis.dashLength = 2;\n");
        grafico.append("valueAxis.axisThickness = 2;\n");
        grafico.append("valueAxis.gridAlpha = 0.15;\n");
        grafico.append("chart.addValueAxis(valueAxis);\n");

        grafico.append("var graph = new AmCharts.AmGraph();\n");
        grafico.append("graph.title = 'NBI AL AÑO';\n");
        grafico.append("graph.valueField = '");
        grafico.append(ejey);
        grafico.append("';\n");
        grafico.append("graph.type = 'column';\n");
        grafico.append("graph.balloonText = '");
        grafico.append(tool);
        grafico.append(" [[category]]:[[value]]';\n");
        grafico.append("graph.lineAlpha = 0;\n");
        grafico.append("graph.fillColors = '#00B3DF';\n");
        grafico.append("graph.fillAlphas = 0.8;\n");
        grafico.append("chart.addGraph(graph);\n");

        grafico.append("chart.write('");
        grafico.append(id);
        grafico.append("');\n");
        grafico.append("});\n");

        grafico.append("</script>\n");

        return grafico;
    }
    
}
