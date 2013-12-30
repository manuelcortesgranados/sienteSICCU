/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.Supervisor;

import co.com.interkont.giprom.vista.VwIndIndicadorMunicipal;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author desarrollo3
 */
public class GraficosGiprom implements Serializable{
    
    private StringBuilder graficoNBI = new StringBuilder();
    private StringBuilder graficoNBIanual = new StringBuilder();
    private StringBuilder graficoNBILocalizacion = new StringBuilder(); 
    private List<VwIndIndicadorMunicipal> listadesempeno= new ArrayList<VwIndIndicadorMunicipal>();
    private String distribucionetnica="";

    public String getDistribucionetnica() {
        return distribucionetnica;
    }

    public void setDistribucionetnica(String distribucionetnica) {
        this.distribucionetnica = distribucionetnica;
    }
    
    

    public List<VwIndIndicadorMunicipal> getListadesempeno() {
        return listadesempeno;
    }

    public void setListadesempeno(List<VwIndIndicadorMunicipal> listadesempeno) {
        this.listadesempeno = listadesempeno;
    }   
    

    public StringBuilder getGraficoNBI() {
        
        return graficoNBI;
    }

    public void setGraficoNBI(StringBuilder graficoNBI) {
        this.graficoNBI = graficoNBI;
    }

    public StringBuilder getGraficoNBIanual() {
        
        return graficoNBIanual;
    }

    public void setGraficoNBIanual(StringBuilder graficoNBIanual) {
        this.graficoNBIanual = graficoNBIanual;
    }

    public StringBuilder getGraficoNBILocalizacion() {
        
        return graficoNBILocalizacion;
    }

    public void setGraficoNBILocalizacion(StringBuilder graficoNBILocalizacion) {
        this.graficoNBILocalizacion = graficoNBILocalizacion;
    }
    
    
    public void pintarGraficoNBI(String valor) {
        
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

        this.graficoNBI.append("arrow.setValue(").append(valor).append(");\n");
        this.graficoNBI.append("axis.setBottomText(").append(valor).append(" + \" %\");\n");
        this.graficoNBI.append("});\n");
        this.graficoNBI.append("</script>\n");

        
    }

    public void pintarGraficoNBIanual(List<VwIndIndicadorMunicipal> listindhabanual) {
        StringBuilder datosNBIanual = new StringBuilder();
        datosNBIanual.append("<script type=\"text/javascript\">\n");
        datosNBIanual.append("var chart;\n");
        datosNBIanual.append("var chartData = [");
        int i=0;
        for(VwIndIndicadorMunicipal ind:listindhabanual)
        {
            if(i>0)
            {
               datosNBIanual.append(","); 
            }    
            datosNBIanual.append("{\n");
            
            datosNBIanual.append("'year': '").append(ind.getIndDtmAnio().toString()).append("',\n");
        datosNBIanual.append("    'nbi': ").append(ind.getIndDtmValor().toString()).append("\n");
            datosNBIanual.append("}");
            i++;
        }  
        datosNBIanual.append("];\n");        
        datosNBIanual.append("AmCharts.ready(function () {\n");

        datosNBIanual.append("chart = new AmCharts.AmSerialChart();\n");
        datosNBIanual.append("chart.dataProvider = chartData;\n");
        datosNBIanual.append("chart.categoryField = 'year';\n");
        datosNBIanual.append("chart.rotate = true;\n");
        datosNBIanual.append("chart.depth3D = 10;\n");
        datosNBIanual.append("chart.angle = 30;\n");
        datosNBIanual.append("chart.startDuration = 4;\n");
        datosNBIanual.append("chart.color = '#58595B';\n");

        datosNBIanual.append("var categoryAxis = chart.categoryAxis;\n");
        datosNBIanual.append("categoryAxis.gridPosition = 'start';\n");
        datosNBIanual.append("categoryAxis.gridAlpha = 0.15;\n");
        datosNBIanual.append("categoryAxis.dashLength = 2  ;\n");
        datosNBIanual.append("categoryAxis.axisColor = '#CCC';\n");

        datosNBIanual.append("var valueAxis = new AmCharts.ValueAxis();\n");
        datosNBIanual.append("valueAxis.axisColor = '#CCC';\n");
        datosNBIanual.append("valueAxis.position = 'top';\n");
        datosNBIanual.append("valueAxis.minorGridEnabled = true;\n");
        datosNBIanual.append("valueAxis.maximum = '100';\n");
        datosNBIanual.append("valueAxis.minimum = '0';\n");
        datosNBIanual.append("valueAxis.dashLength = 2;\n");
        datosNBIanual.append("valueAxis.axisThickness = 2;\n");
        datosNBIanual.append("valueAxis.gridAlpha = 0.15;\n");
        datosNBIanual.append("chart.addValueAxis(valueAxis);\n");

        datosNBIanual.append("var graph = new AmCharts.AmGraph();\n");
        datosNBIanual.append("graph.title = 'NBI AL AÑO';\n");
        datosNBIanual.append("graph.valueField = 'nbi';\n");
        datosNBIanual.append("graph.type = 'column';\n");
        datosNBIanual.append("graph.balloonText = '% NBI AL [[category]]:[[value]]';\n");
        datosNBIanual.append("graph.lineAlpha = 0;\n");
        datosNBIanual.append("graph.fillColors = '#00B3DF';\n");
        datosNBIanual.append("graph.fillAlphas = 0.8;\n");
        datosNBIanual.append("chart.addGraph(graph);\n");

        datosNBIanual.append("chart.write('graficoNBIanual');\n");
        datosNBIanual.append("});\n");

        datosNBIanual.append("</script>\n");
        
        this.graficoNBIanual.append(datosNBIanual);
    }

    public void pintarGraficoNBILocalidad(List<VwIndIndicadorMunicipal> listindhabanual, VwIndIndicadorMunicipal denominador ) {
        StringBuilder datosNBILocalidad = new StringBuilder();
        
        datosNBILocalidad.append("<script type=\"text/javascript\">\n");
        
        datosNBILocalidad.append("var chartDataLocal = [");
        int i=0;
        for(VwIndIndicadorMunicipal ind:listindhabanual)
        {
            if(i>0)
            {
               datosNBILocalidad.append(","); 
            }    
            datosNBILocalidad.append("{\n");
            Double val=(ind.getIndDtmValor()/denominador.getIndDtmValor())*100;
            BigDecimal val2=BigDecimal.valueOf(val).setScale(2, RoundingMode.HALF_UP);
            datosNBILocalidad.append("'area': '").append(ind.getIndMtdNombre()).append("',\n");
        datosNBILocalidad.append("    'nbi': ").append(val2.toString()).append("\n");
            datosNBILocalidad.append("}");
            i++;
        }  
        datosNBILocalidad.append("];\n");      
        
        

        datosNBILocalidad.append("AmCharts.ready(function () {\n");

        datosNBILocalidad.append("chartLocal = new AmCharts.AmSerialChart();\n");
        datosNBILocalidad.append("chartLocal.dataProvider = chartDataLocal;\n");
        datosNBILocalidad.append("chartLocal.categoryField = 'area';\n");
        datosNBILocalidad.append("chartLocal.rotate = true;\n");
        datosNBILocalidad.append("chartLocal.depth3D = 10;\n");
        datosNBILocalidad.append("chartLocal.angle = 30;\n");
        datosNBILocalidad.append("chartLocal.startDuration = 4;\n");
        datosNBILocalidad.append("chartLocal.color = '#58595B';\n");

        datosNBILocalidad.append("var categoryAxisLocal = chartLocal.categoryAxis;\n");
        datosNBILocalidad.append("categoryAxisLocal.gridPosition = 'start';\n");
        datosNBILocalidad.append("categoryAxisLocal.gridAlpha = 0.15;\n");
        datosNBILocalidad.append("categoryAxisLocal.dashLength = 2  ;\n");
        datosNBILocalidad.append("categoryAxisLocal.axisColor = '#CCC';\n");

        datosNBILocalidad.append("var valueAxisLocal = new AmCharts.ValueAxis();\n");
        datosNBILocalidad.append("valueAxisLocal.axisColor = '#CCC';\n");
        datosNBILocalidad.append("valueAxisLocal.position = 'top';\n");
        datosNBILocalidad.append("valueAxisLocal.minorGridEnabled = true;\n");
        datosNBILocalidad.append("valueAxisLocal.maximum = '100';\n");
        datosNBILocalidad.append("valueAxisLocal.minimum = '0';\n");
        datosNBILocalidad.append("valueAxisLocal.dashLength = 2;\n");
        datosNBILocalidad.append("valueAxisLocal.axisThickness = 2;\n");
        datosNBILocalidad.append("valueAxisLocal.gridAlpha = 0.15;\n");
        datosNBILocalidad.append("chartLocal.addValueAxis(valueAxisLocal);\n");

        datosNBILocalidad.append("var graphLocal = new AmCharts.AmGraph();\n");
        datosNBILocalidad.append("graphLocal.title = 'NBI AL AÑO';\n");
        datosNBILocalidad.append("graphLocal.valueField = 'nbi';\n");
        datosNBILocalidad.append("graphLocal.type = 'column';\n");
        datosNBILocalidad.append("graphLocal.balloonText = '% NBI POR LOCALIZACION [[category]]:[[value]]';\n");
        datosNBILocalidad.append("graphLocal.lineAlpha = 0;\n");
        datosNBILocalidad.append("graphLocal.fillColors = '#00B3DF';\n");
        datosNBILocalidad.append("graphLocal.fillAlphas = 0.8;\n");
        datosNBILocalidad.append("chartLocal.addGraph(graphLocal);\n");

        datosNBILocalidad.append("chartLocal.write('graficoNBILocalizacion');\n");
        datosNBILocalidad.append("});\n");

        datosNBILocalidad.append("</script>\n");
        
        this.graficoNBILocalizacion.append(datosNBILocalidad);
    }
    
    private StringBuilder pintarGraficoBarras(StringBuilder datos, String ejex, String ejey, String id, String tool) {
        StringBuilder grafico = new StringBuilder();
        

        return grafico;
    }
    
    public void llenarDistribucionEtnica(List<VwIndIndicadorMunicipal> listind)
    {
        List<String> listadatos= new ArrayList<String>();
        
        
        
        for(VwIndIndicadorMunicipal ind:listind)        {
           
            if(ind.getIndMtdCodigo().compareTo("040104")==0)
            {
                    listadatos.add(ind.getIndDtmAnio().toString());
                listadatos.add(ind.getIndDtmValor().toString());
            }
            if(ind.getIndMtdCodigo().compareTo("040201")==0)
            {
                listadatos.add(ind.getIndDtmAnio().toString());
                listadatos.add(ind.getIndDtmValor().toString());
            }
            if(ind.getIndMtdCodigo().compareTo("040162")==0)
            {
                Double val=(ind.getIndDtmValor()/Double.valueOf(listadatos.get(1)))*100;
                BigDecimal val2=BigDecimal.valueOf(val).setScale(2, RoundingMode.HALF_UP);
                listadatos.add(val2.toString()+" %");
            }
            if(ind.getIndMtdCodigo().compareTo("040161")==0)
            {
                Double val=(ind.getIndDtmValor()/Double.valueOf(listadatos.get(1)))*100;
                BigDecimal val2=BigDecimal.valueOf(val).setScale(2, RoundingMode.HALF_UP);
                listadatos.add(val2.toString()+" %");
            }
            if(ind.getIndMtdCodigo().compareTo("030001")==0)
            {
                Double val=(ind.getIndDtmValor()/Double.valueOf(listadatos.get(1)))*100;
                BigDecimal val2=BigDecimal.valueOf(val).setScale(2, RoundingMode.HALF_UP);
                listadatos.add(val2.toString());
            }
            if(ind.getIndMtdCodigo().compareTo("040165")==0)
            {
                Double val=(ind.getIndDtmValor()/Double.valueOf(listadatos.get(1)))*100;
                BigDecimal val2=BigDecimal.valueOf(val).setScale(2, RoundingMode.HALF_UP);
                listadatos.add(val2.toString());
            }
            if(ind.getIndMtdCodigo().compareTo("040166")==0)
            {
                Double val=(ind.getIndDtmValor()/Double.valueOf(listadatos.get(1)))*100;
                BigDecimal val2=BigDecimal.valueOf(val).setScale(2, RoundingMode.HALF_UP);
                listadatos.add(val2.toString());
            }           
            
        }
        this.distribucionetnica="Según el censo "+listadatos.get(1)+
                " la población del municipio es de "+listadatos.get(2)+
                " habitantes, la población proyectada para "+listadatos.get(3)+
                " es de "+listadatos.get(4)+
                " habitantes. El "+listadatos.get(5)+
                " de la población vive en área rural y el "+listadatos.get(6)+
                " en el área urbana, en el municipio habitan en promedio "+listadatos.get(7)+
                " habitantes por Km cuadrado. El "+listadatos.get(8)+
                "  corresponde a la población afrocolombiana, y el "+listadatos.get(9)+
                "  a la población indígena.";
        
    }        
    
}
