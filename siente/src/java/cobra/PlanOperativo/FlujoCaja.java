/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.PlanOperativo;

import co.com.interkont.cobra.to.Fuenterecursosconvenio;
import co.com.interkont.cobra.to.Obrafuenterecursosconvenios;
import co.com.interkont.cobra.to.Periodoflujocaja;
import cobra.SessionBeanCobra;
import cobra.Supervisor.FacesUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



/**
 *
 * @author desarrollo6
 */
public class FlujoCaja {
    ExpenseReport expReport;
    List<Fuenterecursosconvenio> fuentesRecursosConvenio;
    List<Obrafuenterecursosconvenios> fuentesRecursosConvenioObras;
    List<Periodoflujocaja> periodosFlujoCaja;
    List<FlujoIngresos> flujoIngresos;
    List<FlujoEgresos> flujoEgresos;

    public List<Periodoflujocaja> getPeriodosFlujoCaja() {
        return periodosFlujoCaja;
    }

    public void setPeriodosFlujoCaja(List<Periodoflujocaja> periodosFlujoCaja) {
        this.periodosFlujoCaja = periodosFlujoCaja;
    }
    
    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }
    
       public ExpenseReport getExpReport() {
        if (expReport == null) {
            expReport = new ExpenseReport();
        }
        return expReport;
    }
 
    public void setExpReport(ExpenseReport expReport) {
        this.expReport = expReport;
    }
   
    public void crearPeriodosFlujoCaja() {
        periodosFlujoCaja = new ArrayList<Periodoflujocaja>();
        int i = 1;
        
        while (i <= 12) {
            Periodoflujocaja periodoFlujoCaja = new Periodoflujocaja();
            
            periodoFlujoCaja.setFechainicio(new Date("1-"+i+"2013"));
            periodoFlujoCaja.setFechafin(new Date("28"+i+"2013"));
            
            periodosFlujoCaja.add(periodoFlujoCaja);
            
            i++;
        }
    }
    
    public void crearEstructuraFlujoIngresos() {
        this.crearPeriodosFlujoCaja();
        
        int codigoConvenio = 61;

        fuentesRecursosConvenio = getSessionBeanCobra().getCobraService().fuentesRecursosConvenio(codigoConvenio);
        
        for (Fuenterecursosconvenio fuenteRecursosConvenio : fuentesRecursosConvenio) {
            FlujoIngresos flujoIngresosEntidad = new FlujoIngresos();
            
            flujoIngresosEntidad.crearEstructuraFlujoIngresosEntidad(fuenteRecursosConvenio, periodosFlujoCaja);
            
            flujoIngresos.add(flujoIngresosEntidad);
        }
    }
    
    public void crearEstructuraFlujoEgresos() {
        this.crearPeriodosFlujoCaja();
        
        int codigoConvenio = 61;
        
        fuentesRecursosConvenioObras = getSessionBeanCobra().getCobraService().fuentesRecursosConvenioObras(codigoConvenio);
        
        for (Obrafuenterecursosconvenios fuenteRecursosConvenioObra : fuentesRecursosConvenioObras) {
            FlujoEgresos flujoEgresosProyecto = new FlujoEgresos();
            
            flujoEgresosProyecto.crearFlujoEgresosProyecto(fuenteRecursosConvenioObra, periodosFlujoCaja);
            
            flujoEgresos.add(flujoEgresosProyecto);
        }
    }
}