/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.PlanOperativo;

import co.com.interkont.cobra.to.Contrato;
import co.com.interkont.cobra.to.Fuenterecursosconvenio;
import co.com.interkont.cobra.to.Itemflujocaja;
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

    final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;
    List<Fuenterecursosconvenio> fuentesRecursosConvenio;
    List<Obrafuenterecursosconvenios> fuentesRecursosConvenioObras;
    List<Itemflujocaja> itemsFlujoIngresos;
    List<Itemflujocaja> itemsFlujoEgresos;
    List<Periodoflujocaja> periodosFlujoCaja;
    List<FlujoIngresos> flujoIngresos;
    List<FlujoEgresos> flujoEgresos;
    double totalIngresosPeriodo[];
    double totalIngresosPeriodoAcumulativo[];
    double totalEgresosPeriodo[];
    double totalEgresosPeriodoAcumulativo[];
    double totalIngresos;
    double totalEgresos;

    public List<Fuenterecursosconvenio> getFuentesRecursosConvenio() {
        return fuentesRecursosConvenio;
    }

    public void setFuentesRecursosConvenio(List<Fuenterecursosconvenio> fuentesRecursosConvenio) {
        this.fuentesRecursosConvenio = fuentesRecursosConvenio;
    }

    public List<Obrafuenterecursosconvenios> getFuentesRecursosConvenioObras() {
        return fuentesRecursosConvenioObras;
    }

    public void setFuentesRecursosConvenioObras(List<Obrafuenterecursosconvenios> fuentesRecursosConvenioObras) {
        this.fuentesRecursosConvenioObras = fuentesRecursosConvenioObras;
    }

    public List<Itemflujocaja> getItemsFlujoIngresos() {
        return itemsFlujoIngresos;
    }

    public void setItemsFlujoIngresos(List<Itemflujocaja> itemsFlujoIngresos) {
        this.itemsFlujoIngresos = itemsFlujoIngresos;
    }

    public List<Itemflujocaja> getItemsFlujoEgresos() {
        return itemsFlujoEgresos;
    }

    public void setItemsFlujoEgresos(List<Itemflujocaja> itemsFlujoEgresos) {
        this.itemsFlujoEgresos = itemsFlujoEgresos;
    }

    public List<Periodoflujocaja> getPeriodosFlujoCaja() {
        return periodosFlujoCaja;
    }

    public void setPeriodosFlujoCaja(List<Periodoflujocaja> periodosFlujoCaja) {
        this.periodosFlujoCaja = periodosFlujoCaja;
    }

    public List<FlujoIngresos> getFlujoIngresos() {
        return flujoIngresos;
    }

    public void setFlujoIngresos(List<FlujoIngresos> flujoIngresos) {
        this.flujoIngresos = flujoIngresos;
    }

    public List<FlujoEgresos> getFlujoEgresos() {
        return flujoEgresos;
    }

    public void setFlujoEgresos(List<FlujoEgresos> flujoEgresos) {
        this.flujoEgresos = flujoEgresos;
    }

    public double[] getTotalIngresosPeriodo() {
        return totalIngresosPeriodo;
    }

    public void setTotalIngresosPeriodo(double[] totalIngresosPeriodo) {
        this.totalIngresosPeriodo = totalIngresosPeriodo;
    }

    public double[] getTotalIngresosPeriodoAcumulativo() {
        return totalIngresosPeriodoAcumulativo;
    }

    public void setTotalIngresosPeriodoAcumulativo(double[] totalIngresosPeriodoAcumulativo) {
        this.totalIngresosPeriodoAcumulativo = totalIngresosPeriodoAcumulativo;
    }

    public double[] getTotalEgresosPeriodo() {
        return totalEgresosPeriodo;
    }

    public void setTotalEgresosPeriodo(double[] totalEgresosPeriodo) {
        this.totalEgresosPeriodo = totalEgresosPeriodo;
    }

    public double[] getTotalEgresosPeriodoAcumulativo() {
        return totalEgresosPeriodoAcumulativo;
    }

    public void setTotalEgresosPeriodoAcumulativo(double[] totalEgresosPeriodoAcumulativo) {
        this.totalEgresosPeriodoAcumulativo = totalEgresosPeriodoAcumulativo;
    }

    public double getTotalIngresos() {
        return totalIngresos;
    }

    public void setTotalIngresos(double totalIngresos) {
        this.totalIngresos = totalIngresos;
    }

    public double getTotalEgresos() {
        return totalEgresos;
    }

    public void setTotalEgresos(double totalEgresos) {
        this.totalEgresos = totalEgresos;
    }

    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
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
        this.crearEstructuraFlujoEgresos();
        this.iniciarTotalesEgresosPeriodo();

        return "FlujoCaja";
    }

    /**
     * Crea los periodos del flujo de caja de acuerdo con la fecha de inicio y
     * finalización del convenio marco.
     *
     * Llena la lista periodosFlujoCaja estableciendo como fecha de inicio del
     * primer periodo la fecha de inicio del convenio marco, se incrementa
     * mensualmente hasta generar el último periodo y establece como fecha de
     * finalización la fecha final del convenio marco.
     */
    public void crearPeriodosFlujoCaja() {
        periodosFlujoCaja = new ArrayList<Periodoflujocaja>();
        Periodoflujocaja periodoFlujoCaja = new Periodoflujocaja();
        Calendar fechaInicioConvenio = Calendar.getInstance();
        Calendar fechaFinConvenio = Calendar.getInstance();
        Calendar fechaPeriodo = Calendar.getInstance();
        double cantidadPeriodos = 0;
        int iterador = 1;

        Contrato contrato = getSessionBeanCobra().getCobraService().encontrarContratoxId(53);

        fechaInicioConvenio.setTime(contrato.getDatefechaini());
        fechaFinConvenio.setTime(contrato.getDatefechafin());

        cantidadPeriodos = (fechaFinConvenio.getTime().getTime() - fechaInicioConvenio.getTime().getTime()) / MILLSECS_PER_DAY / 30d;

        /* Primer elemento de la lista de periodos */
        System.out.println("Cantidad de periodos = " + Math.ceil(cantidadPeriodos));
        periodoFlujoCaja.setFechainicio(fechaInicioConvenio.getTime());
        fechaPeriodo = fechaInicioConvenio;

        fechaPeriodo.add(Calendar.MONTH, 1);
        fechaPeriodo.add(Calendar.DATE, -1);
        periodoFlujoCaja.setFechafin(fechaPeriodo.getTime());

        periodoFlujoCaja.setStrdescripcion("Mes " + iterador);
        periodosFlujoCaja.add(periodoFlujoCaja);

        iterador++;

        System.out.println("Fecha inicio del primer elemento = " + periodosFlujoCaja.get(0).getFechainicio());

        while (iterador <= Math.ceil(cantidadPeriodos)) {
            periodoFlujoCaja = new Periodoflujocaja();

            fechaPeriodo.add(Calendar.DATE, 1);
            periodoFlujoCaja.setFechainicio(fechaPeriodo.getTime());

            if (iterador < Math.ceil(cantidadPeriodos)) {
                fechaPeriodo.add(Calendar.MONTH, 1);
                fechaPeriodo.add(Calendar.DATE, -1);
                periodoFlujoCaja.setFechafin(fechaPeriodo.getTime());
            } else {
                fechaPeriodo = fechaFinConvenio;
                periodoFlujoCaja.setFechafin(fechaPeriodo.getTime());
            }

            periodoFlujoCaja.setStrdescripcion("Mes " + iterador);
            periodosFlujoCaja.add(periodoFlujoCaja);

            System.out.println("Fecha inicio del elemento " + iterador + ": " + periodosFlujoCaja.get(iterador - 1).getFechainicio());
            System.out.println("Fecha fin del elemento " + iterador + ": " + periodosFlujoCaja.get(iterador - 1).getFechafin());

            iterador++;
        }
    }

    public void crearEstructuraFlujoIngresos() {
        this.flujoIngresos = new ArrayList<FlujoIngresos>();
        this.totalIngresos = 0;

        int codigoConvenio = 53;

        fuentesRecursosConvenio = getSessionBeanCobra().getCobraService().fuentesRecursosConvenio(codigoConvenio);
        itemsFlujoIngresos = getSessionBeanCobra().getCobraService().itemsFlujoCajaPorNaturaleza("I");

        for (Fuenterecursosconvenio fuenteRecursosConvenio : fuentesRecursosConvenio) {
            FlujoIngresos flujoIngresosEntidad = new FlujoIngresos();
            Tercero entidadAportante = getSessionBeanCobra().getCobraService().encontrarTerceroPorId(fuenteRecursosConvenio.getTercero().getIntcodigo());

            flujoIngresosEntidad.crearEstructuraFlujoIngresosEntidad(fuenteRecursosConvenio, entidadAportante, periodosFlujoCaja);
            flujoIngresosEntidad.calcularTotalIngresosFuente(periodosFlujoCaja.size());

            flujoIngresos.add(flujoIngresosEntidad);
        }

        for (Itemflujocaja itemFlujoIngresos : itemsFlujoIngresos) {
            FlujoIngresos flujoIngresosEntidad = new FlujoIngresos();

            flujoIngresosEntidad.crearEstructuraFlujoIngresosOtrosItems(itemFlujoIngresos, periodosFlujoCaja);
            flujoIngresosEntidad.calcularTotalIngresosFuente(periodosFlujoCaja.size());

            flujoIngresos.add(flujoIngresosEntidad);
        }
    }

    public void iniciarTotalesIngresosPeriodo() {
        this.totalIngresosPeriodo = new double[this.periodosFlujoCaja.size()];
        this.totalIngresosPeriodoAcumulativo = new double[this.periodosFlujoCaja.size()];
        int i = 0;

        while (i < periodosFlujoCaja.size()) {
            this.totalIngresosPeriodo[i] = 0;
            this.totalIngresosPeriodoAcumulativo[i] = 0;

            i++;
        }
    }

    public void refrescarTotalesIngresos(FlujoIngresos fuenteIngresos, int columna) {
        fuenteIngresos.calcularTotalIngresosFuente(periodosFlujoCaja.size());
        this.totalIngresosPeriodo[columna] = 0;
        this.totalIngresos = 0;

        for (FlujoIngresos fuenteIngresosRecorrer : flujoIngresos) {
            if (fuenteIngresosRecorrer.isIngresoEntidad()) {
                this.totalIngresosPeriodo[columna] += fuenteIngresosRecorrer.planMovimientosConvenioEntidad.get(columna).getValor().doubleValue();
            } else {
                this.totalIngresosPeriodo[columna] += fuenteIngresosRecorrer.planMovimientosIngresosConvenio.get(columna).getValor().doubleValue();
            }
            
            this.totalIngresos += fuenteIngresosRecorrer.totalIngresosFuente.doubleValue();

        }

        int i = 1;
        this.totalIngresosPeriodoAcumulativo[0] = this.totalIngresosPeriodo[0];

        while (i < this.totalIngresosPeriodo.length) {
            this.totalIngresosPeriodoAcumulativo[i] = this.totalIngresosPeriodoAcumulativo[i - 1] + this.totalIngresosPeriodo[i];

            i++;
        }
    }

    public void crearEstructuraFlujoEgresos() {
        this.flujoEgresos = new ArrayList<FlujoEgresos>();
        this.totalEgresos = 0;

        int codigoConvenio = 53;

        fuentesRecursosConvenioObras = getSessionBeanCobra().getCobraService().fuentesRecursosConvenioObras(codigoConvenio);
        itemsFlujoEgresos = getSessionBeanCobra().getCobraService().itemsFlujoCajaPorNaturaleza("E");

        for (Obrafuenterecursosconvenios fuenteRecursosConvenioObra : fuentesRecursosConvenioObras) {
            FlujoEgresos flujoEgresosProyecto = new FlujoEgresos();
            Obra proyecto = getSessionBeanCobra().getCobraService().encontrarObraPorId(fuenteRecursosConvenioObra.getObra().getIntcodigoobra());

            flujoEgresosProyecto.crearEstructuraFlujoEgresosProyecto(fuenteRecursosConvenioObra, proyecto, periodosFlujoCaja);
            flujoEgresosProyecto.calcularTotalEgresosFuente(periodosFlujoCaja.size());

            flujoEgresos.add(flujoEgresosProyecto);
        }

        for (Itemflujocaja itemFlujoEgresos : itemsFlujoEgresos) {
            FlujoEgresos flujoEgresosEntidad = new FlujoEgresos();

            flujoEgresosEntidad.crearEstructuraFlujoEgresosOtrosItems(itemFlujoEgresos, periodosFlujoCaja);
            flujoEgresosEntidad.calcularTotalEgresosFuente(periodosFlujoCaja.size());

            flujoEgresos.add(flujoEgresosEntidad);
        }
    }

    public void iniciarTotalesEgresosPeriodo() {
        this.totalEgresosPeriodo = new double[this.periodosFlujoCaja.size()];
        this.totalEgresosPeriodoAcumulativo = new double[this.periodosFlujoCaja.size()];
        int i = 0;

        while (i < periodosFlujoCaja.size()) {
            this.totalEgresosPeriodo[i] = 0;

            i++;
        }
    }

    public void refrescarTotalesEgresos(FlujoEgresos fuenteEgresos, int columna) {
        fuenteEgresos.calcularTotalEgresosFuente(periodosFlujoCaja.size());
        this.totalEgresosPeriodo[columna] = 0;
        this.totalEgresos = 0;

        for (FlujoEgresos fuenteEgresosRecorrer : flujoEgresos) {
            if (fuenteEgresosRecorrer.isEgresoProyecto()) {
                this.totalEgresosPeriodo[columna] += fuenteEgresosRecorrer.planMovimientosProyecto.get(columna).getValor().doubleValue();
            } else {
                this.totalEgresosPeriodo[columna] += fuenteEgresosRecorrer.planMovimientosEgresosConvenio.get(columna).getValor().doubleValue();
            }
            
            this.totalEgresos += fuenteEgresosRecorrer.totalEgresosFuente.doubleValue();
        }

        int i = 1;
        this.totalEgresosPeriodoAcumulativo[0] = this.totalEgresosPeriodo[0];

        while (i < this.totalEgresosPeriodo.length) {
            this.totalEgresosPeriodoAcumulativo[i] = this.totalEgresosPeriodoAcumulativo[i - 1] + this.totalEgresosPeriodo[i];

            i++;
        }
    }
}