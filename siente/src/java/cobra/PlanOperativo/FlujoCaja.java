/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.PlanOperativo;

import co.com.interkont.cobra.to.Contrato;
import co.com.interkont.cobra.to.Fuenterecursosconvenio;
import co.com.interkont.cobra.to.Obra;
import co.com.interkont.cobra.to.Obrafuenterecursosconvenios;
import co.com.interkont.cobra.to.Periodoflujocaja;
import co.com.interkont.cobra.to.Tercero;
import cobra.SessionBeanCobra;
import cobra.Supervisor.FacesUtils;
import java.util.ArrayList;
import java.util.Calendar;
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
    double totalIngresosPeriodo[];
    double totalIngresos;

    public List<Periodoflujocaja> getPeriodosFlujoCaja() {
        return periodosFlujoCaja;
    }

    public void setPeriodosFlujoCaja(List<Periodoflujocaja> periodosFlujoCaja) {
        this.periodosFlujoCaja = periodosFlujoCaja;
    }

    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }

    public List<Fuenterecursosconvenio> getFuentesRecursosConvenio() {
        return fuentesRecursosConvenio;
    }

    public void setFuentesRecursosConvenio(List<Fuenterecursosconvenio> fuentesRecursosConvenio) {
        this.fuentesRecursosConvenio = fuentesRecursosConvenio;
    }

    public List<FlujoIngresos> getFlujoIngresos() {
        return flujoIngresos;
    }

    public void setFlujoIngresos(List<FlujoIngresos> flujoIngresos) {
        this.flujoIngresos = flujoIngresos;
    }

    public double[] getTotalIngresosPeriodo() {
        return totalIngresosPeriodo;
    }

    public void setTotalIngresosPeriodo(double[] totalIngresosPeriodo) {
        this.totalIngresosPeriodo = totalIngresosPeriodo;
    }

    public double getTotalIngresos() {
        return totalIngresos;
    }

    public void setTotalIngresos(double totalIngresos) {
        this.totalIngresos = totalIngresos;
    }

    public List<Integer> getColumnasPeriodos() {
        List<Integer> items = new ArrayList<Integer>();
        int i = 0;

        while (i < periodosFlujoCaja.size()) {
            Integer item = i;

            items.add(item);

            i++;
        }

        return items;
    }

    public String iniciarFlujoCaja() {
        this.crearPeriodosFlujoCaja();
        this.crearEstructuraFlujoIngresos();
        this.iniciarTotalesIngresosPeriodo();

        return "FlujoCaja";
    }

    public void crearPeriodosFlujoCaja() {
        final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;
        Calendar fechaInicioConvenio = Calendar.getInstance();
        Calendar fechaFinConvenio = Calendar.getInstance();
        int diasEntreFechas = 0;

        Contrato contrato = getSessionBeanCobra().getCobraService().encontrarContratoxId(53);

        fechaInicioConvenio.setTime(contrato.getDatefechaini());
        fechaFinConvenio.setTime(contrato.getDatefechafin());

        diasEntreFechas = fechaInicioConvenio.compareTo(fechaFinConvenio);

        long diferencia = (fechaInicioConvenio.getTime().getTime() - fechaFinConvenio.getTime().getTime()) / MILLSECS_PER_DAY;

        System.out.println("diasEntreFechas = " + diasEntreFechas);
        System.out.println("diferencia = " + diferencia);

        periodosFlujoCaja = new ArrayList<Periodoflujocaja>();
        int i = 1;

        while (i <= 12) {
            Periodoflujocaja periodoFlujoCaja = new Periodoflujocaja();
            Calendar calendario = Calendar.getInstance();


            calendario.set(2013, 1, i);
            periodoFlujoCaja.setFechainicio(calendario.getTime());

            calendario.set(2013, 28, i);
            periodoFlujoCaja.setFechafin(calendario.getTime());
            periodoFlujoCaja.setStrdescripcion("Mes " + i);

            periodosFlujoCaja.add(periodoFlujoCaja);

            i++;
        }
    }

    public void crearEstructuraFlujoIngresos() {
        this.crearPeriodosFlujoCaja();
        this.flujoIngresos = new ArrayList<FlujoIngresos>();
        this.totalIngresos = 0;

        int codigoConvenio = 53;

        fuentesRecursosConvenio = getSessionBeanCobra().getCobraService().fuentesRecursosConvenio(codigoConvenio);

        for (Fuenterecursosconvenio fuenteRecursosConvenio : fuentesRecursosConvenio) {
            FlujoIngresos flujoIngresosEntidad = new FlujoIngresos();
            Tercero entidadAportante = getSessionBeanCobra().getCobraService().encontrarTerceroPorId(fuenteRecursosConvenio.getTercero().getIntcodigo());

            flujoIngresosEntidad.crearEstructuraFlujoIngresosEntidad(fuenteRecursosConvenio, entidadAportante, periodosFlujoCaja);
            flujoIngresosEntidad.calcularTotalIngresosEntidad();

            flujoIngresos.add(flujoIngresosEntidad);
        }
    }
    
    public void iniciarTotalesIngresosPeriodo() {
        this.totalIngresosPeriodo = new double[this.periodosFlujoCaja.size()];
        int i = 0;
        
        while (i < periodosFlujoCaja.size()) {
            this.totalIngresosPeriodo[i] = 0;
            
            i++;
        }
    }
    
    public void refrescarTotalesIngresos(FlujoIngresos fuenteIngresos, int columna) {
        fuenteIngresos.calcularTotalIngresosEntidad();
        this.totalIngresosPeriodo[columna] = 0;
        this.totalIngresos = 0;
        
        for (FlujoIngresos fuenteIngresosRecorrer : flujoIngresos) {
            this.totalIngresosPeriodo[columna] += fuenteIngresosRecorrer.ingresos[columna].doubleValue();
            this.totalIngresos += fuenteIngresosRecorrer.totalIngresosFuente.doubleValue();
        }
    }
    
    public void crearEstructuraFlujoEgresos() {
        this.crearPeriodosFlujoCaja();
        this.flujoEgresos = new ArrayList<FlujoEgresos>();

        int codigoConvenio = 53;

        fuentesRecursosConvenioObras = getSessionBeanCobra().getCobraService().fuentesRecursosConvenioObras(codigoConvenio);
        

        for (Obrafuenterecursosconvenios fuenteRecursosConvenioObra : fuentesRecursosConvenioObras) {
            FlujoEgresos flujoEgresosProyecto = new FlujoEgresos();
            Obra proyecto = getSessionBeanCobra().getCobraService().encontrarObraPorId(fuenteRecursosConvenioObra.getObra().getIntcodigoobra());
            flujoEgresosProyecto.crearEstructuraFlujoEgresosProyecto(fuenteRecursosConvenioObra, proyecto, periodosFlujoCaja);

            flujoEgresos.add(flujoEgresosProyecto);
        }
    }    
}