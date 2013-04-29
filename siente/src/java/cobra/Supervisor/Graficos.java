/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cobra.Supervisor;

import co.com.interkont.cobra.to.Alimentacion;
import co.com.interkont.cobra.to.Obra;
import co.com.interkont.cobra.to.Periodo;
import cobra.SessionBeanCobra;
import cobra.graficos.Data;
import cobra.graficos.Datasets;
import cobra.graficos.Estructuragraphic;
import cobra.graficos.JSChart;
import cobra.graficos.Optionset;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.google.gson.Gson;
import java.math.RoundingMode;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
/**
 *
 * @author felipe
 */
public class Graficos  {

    public String rutaGrafico="";

    public Graficos() {
        GenerarJson();
    }

    private void GenerarJson() {

        List<Datasets> linea= new ArrayList();
       
        List<Data> dato1= new ArrayList();
        List<Data> dato2= new ArrayList();
        List<Data> dato3= new ArrayList();

        List<Optionset> optionset=new ArrayList();
        optionset.add(new Optionset("setSize", "800, 350"));
        optionset.add(new Optionset("setTitle", "'Grafico Evolucion Proyecto'"));
        optionset.add(new Optionset("setAxisNameFontSize", "10"));
        optionset.add(new Optionset("setAxisNameX", "'Periodo Proyecto'"));
        optionset.add(new Optionset("setAxisNameY", "'MILLONES $'"));
        optionset.add(new Optionset("setShowXValues", "false"));
        optionset.add(new Optionset("setTitleColor", "'#666666'"));
        optionset.add(new Optionset("setAxisValuesColor", "'#666666'"));
        optionset.add(new Optionset("setLineColor", "'#FF0000', 'green'"));
        optionset.add(new Optionset("setLineColor", "'#336699', 'gray'"));
        optionset.add(new Optionset("setFlagColor","'#666666'"));
        optionset.add(new Optionset("setFlagRadius","6"));
        optionset.add(new Optionset("setAxisPaddingLeft","80"));
        optionset.add(new Optionset("setLegendShow","true"));
        optionset.add(new Optionset("setLegendPosition","200, 80"));
        optionset.add(new Optionset("setLabelFontSize","7"));
        optionset.add(new Optionset("setLegendForLine","'blue', 'Planificado'"));
//        optionset.add(new Optionset("setBackgroundColor","'#BCD0FF'"));
        //optionset.add(new Optionset("setLegendForLine","'green', 'Otro'"));
        optionset.add(new Optionset("setLegendForLine","'green', 'Ejecutado'"));
        int idobra=getAdministrarObraNew().getObra().getIntcodigoobra();
        List<Periodo> obrasperiodos=getSessionBeanCobra().getCobraService().obtenerPeriodosObra(idobra);
        optionset.add(new Optionset("setIntervalStartX", "0"));
        if(obrasperiodos.size()==1){
            int tam=obrasperiodos.size()+1;
            optionset.add(new Optionset("setIntervalEndX", ""+tam));
        }
         else{
             optionset.add(new Optionset("setIntervalEndX", ""+obrasperiodos.size()));
        }

        optionset.add(new Optionset("setAxisValuesNumberX", ""+obrasperiodos.size()));

        int i=0;
        BigDecimal divisor=new BigDecimal(1000000);
        String periodo;
        String valorplani;
        BigDecimal valorplanificado=new BigDecimal(0);
        BigDecimal valoracumulado=new BigDecimal(0);
        getSessionBeanCobra().getCobraService().guardarContadorVisitas(idobra, getSessionBeanCobra().getUsuarioObra());
        Obra obra=getSessionBeanCobra().getCobraService().encontrarObraPorId(idobra);
        BigDecimal totalplanificado=obra.getNumvaltotobra().divide(divisor);
        optionset.add(new Optionset("setIntervalStartY", "0"));
        optionset.add(new Optionset("setIntervalEndY", ""+totalplanificado.setScale(4,RoundingMode.HALF_UP)));
        optionset.add(new Optionset("setAxisValuesNumberY", ""+obrasperiodos.size()));

        BigDecimal sumaacualim=new BigDecimal(0);
        BigDecimal totalacualimen=new BigDecimal(0);
        BigDecimal totalvaloralim=new BigDecimal(0);

        String valoreje;
        dato1.add(new Data(String.valueOf(i), String.valueOf(valoracumulado)));
        dato2.add(new Data(String.valueOf(i), String.valueOf(valoracumulado)));
        i+=1;
        for (Iterator obraIterador =obrasperiodos.iterator(); obraIterador.hasNext();) {

            //optionset.add(new Optionset("setTooltip","["+i+",'']"));
            optionset.add(new Optionset("setLabelX", "["+i+", xxp"+i+"xx ]"));
           
            periodo=String.valueOf(i);
            Periodo periodoObra = (Periodo) obraIterador.next();
            valorplanificado = valorplanificado.add(periodoObra.getNumvaltotplanif());
            valoracumulado=valorplanificado.divide(divisor);
            
            valorplani=String.valueOf(valoracumulado.setScale(3,RoundingMode.HALF_UP));

            dato1.add(new Data(periodo, valorplani));

            Iterator alimentacionIterador =getSessionBeanCobra().getCobraService().obtenerAlimentacionxPeriodo(periodoObra.getDatefeciniperiodo(), periodoObra.getDatefecfinperiodo(), idobra).iterator();

            while (alimentacionIterador.hasNext()) {
                Alimentacion alimenta=(Alimentacion)alimentacionIterador.next();
                sumaacualim = sumaacualim.add(alimenta.getNumtotalejec());
            }

            totalacualimen=totalacualimen.add(sumaacualim).divide(divisor);
            totalvaloralim=totalvaloralim.add(totalacualimen);

            valoreje=String.valueOf(totalacualimen.setScale(3,RoundingMode.HALF_UP));
            dato2.add(new Data(periodo, valoreje));

            optionset.add(new Optionset("setTooltip","["+i+", xxFecha Inicial:"+periodoObra.getDatefeciniperiodo()+" Fecha Final:"+periodoObra.getDatefecfinperiodo()+" Valor planificado en millones $"+valorplani+"xx , xxbluexx ]"));
            optionset.add(new Optionset("setTooltip","["+i+", xxFecha Inicial:"+periodoObra.getDatefeciniperiodo()+" Fecha Final:"+periodoObra.getDatefecfinperiodo()+" Valor ejecutado en millones $"+valoreje+"xx , xxgreenxx ]"));

            i+=1;

        }
        linea.add(new Datasets("line", "blue", dato1));
        linea.add(new Datasets("line", "green", dato2));
        
        Estructuragraphic estruc=new Estructuragraphic();
        estruc.setJSChart(new JSChart(linea,optionset));
       
        Gson gson = new Gson();
        String jsonOutput = gson.toJson(estruc);
        String temp=jsonOutput.replace("xx", "\'");
        
        ServletContext theApplicationsServletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

        String realArchivoPath = theApplicationsServletContext.getRealPath("/resources/json/data.json");

        FileManager filejson=new FileManager();
        filejson.writeInFile(temp, realArchivoPath);
        this.rutaGrafico="/"+getSessionBeanCobra().getBundle().getString("versioncobra")+"/resources/json/data.json";

    }


    public String getRutaGrafico() {
        return rutaGrafico;
    }

    public void setRutaGrafico(String rutaGrafico) {
        this.rutaGrafico = rutaGrafico;
    }

    
    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }

    protected AdministrarObraNew getAdministrarObraNew() {
        return (AdministrarObraNew) FacesUtils.getManagedBean("Supervisor$AdministrarObraNew");
    }



}
