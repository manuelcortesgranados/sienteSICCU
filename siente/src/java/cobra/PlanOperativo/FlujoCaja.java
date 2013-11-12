package cobra.PlanOperativo;

import co.com.interkont.cobra.to.Contrato;
import co.com.interkont.cobra.to.Fuenterecursosconvenio;
import co.com.interkont.cobra.to.Itemflujocaja;
import co.com.interkont.cobra.to.Obra;
import co.com.interkont.cobra.to.Periodoflujocaja;
import co.com.interkont.cobra.to.Planificacionmovconvenio;
import co.com.interkont.cobra.to.Planificacionmovconvenioentidad;
import co.com.interkont.cobra.to.Planificacionmovcuotagerencia;
import co.com.interkont.cobra.to.Planificacionmovimientocontrato;
import co.com.interkont.cobra.to.Planificacionmovimientoproyecto;
import co.com.interkont.cobra.to.Planificacionmovimientoprydirecto;
import co.com.interkont.cobra.to.Planificacionmovimientopryotros;
import co.com.interkont.cobra.to.Relacioncontratoperiodoflujocaja;
import co.com.interkont.cobra.to.Tercero;
import cobra.SessionBeanCobra;
import cobra.Supervisor.FacesUtils;
import cobra.Supervisor.NuevoContratoBasico;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import org.richfaces.component.UIExtendedDataTable;

/**
 * Flujo de caja del plan operativo.
 *
 * Controla el comportamiento y presentación del flujo de caja del convenio
 * marco para los elementos de flujo de ingreso y el flujo de egresos.
 *
 * @author Cristian Gutiérrez
 * @author Yeison Osorio
 */
public class FlujoCaja implements Serializable {

    final long MILISEGUNDOS_POR_DIA = 24 * 60 * 60 * 1000;
    Contrato convenio;
    boolean flujoCajaIniciado = false;
    List<Fuenterecursosconvenio> fuentesRecursosConvenio;
    List<Obra> proyectosConvenio;
    List<Itemflujocaja> itemsFlujoIngresos;
    List<Itemflujocaja> itemsFlujoEgresos;
    List<Relacioncontratoperiodoflujocaja> periodosConvenio;
    List<Relacioncontratoperiodoflujocaja> periodosConvenioEliminados;
    Calendar fechaInicioConvenio;
    Calendar fechaFinConvenio;
    List<FlujoIngresos> flujoIngresos;
    List<FlujoEgresos> flujoEgresos;
    List<Planificacionmovconvenioentidad> planifmovimientoconvenioentidad;
    List<Planificacionmovcuotagerencia> planificacionmovcuotagerencia;
    List<Planificacionmovimientoproyecto> planifmovimientoconvenioproyecto;
    List<Planificacionmovconvenio> planifmovimientoconvenio;
    double totalIngresosPeriodo[];
    double totalIngresosPeriodoAcumulativo[];
    double totalEgresosPeriodo[];
    double totalEgresosPeriodoAcumulativo[];
    double acumuladoGMF[];
    double totalIngresos;
    double totalEgresos;
    ResourceBundle bundle = getSessionBeanCobra().getBundle();
    NuevoContratoBasico nuevoContratoBasico;
    UIExtendedDataTable tablaFuentesIngresos;
    /**
     * Referencia a la tabla de egresos del flujo de caja
     */
    UIExtendedDataTable extendedDataTableEgresos;
    int columnaEvento;

    public List<Planificacionmovcuotagerencia> getPlanificacionmovcuotagerencia() {
        return planificacionmovcuotagerencia;
    }

    public void setPlanificacionmovcuotagerencia(List<Planificacionmovcuotagerencia> planificacionmovcuotagerencia) {
        this.planificacionmovcuotagerencia = planificacionmovcuotagerencia;
    }

    public UIExtendedDataTable getExtendedDataTableEgresos() {
        return extendedDataTableEgresos;
    }

    public void setExtendedDataTableEgresos(UIExtendedDataTable extendedDataTableEgresos) {
        this.extendedDataTableEgresos = extendedDataTableEgresos;
    }
    /**
     * Listado de proyectos del plan operativo con sus contratos asociados. Este
     * es un insumo necesario para el flujo de caja
     */
    private List<ProyectoPlanOperativo> proyectosPlanOperativo = new ArrayList<ProyectoPlanOperativo>();

    public List<ProyectoPlanOperativo> getProyectosPlanOperativo() {
        return proyectosPlanOperativo;
    }

    public void setProyectosPlanOperativo(List<ProyectoPlanOperativo> proyectosPlanOperativo) {
        this.proyectosPlanOperativo = proyectosPlanOperativo;
    }

    public Contrato getConvenio() {
        return convenio;
    }

    public void setConvenio(Contrato convenio) {
        this.convenio = convenio;
    }

    public boolean isFlujoCajaIniciado() {
        return flujoCajaIniciado;
    }

    public void setFlujoCajaIniciado(boolean flujoCajaIniciado) {
        this.flujoCajaIniciado = flujoCajaIniciado;
    }

    public List<Fuenterecursosconvenio> getFuentesRecursosConvenio() {
        return fuentesRecursosConvenio;
    }

    public void setFuentesRecursosConvenio(List<Fuenterecursosconvenio> fuentesRecursosConvenio) {
        this.fuentesRecursosConvenio = fuentesRecursosConvenio;
    }

    public List<Obra> getProyectosConvenio() {
        return proyectosConvenio;
    }

    public void setProyectosConvenio(List<Obra> proyectosConvenio) {
        this.proyectosConvenio = proyectosConvenio;
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

    public List<Relacioncontratoperiodoflujocaja> getPeriodosConvenio() {
        return periodosConvenio;
    }

    public void setPeriodosConvenio(List<Relacioncontratoperiodoflujocaja> periodosConvenio) {
        this.periodosConvenio = periodosConvenio;
    }

    public List<Relacioncontratoperiodoflujocaja> getPeriodosConvenioEliminados() {
        return periodosConvenioEliminados;
    }

    public void setPeriodosConvenioEliminados(List<Relacioncontratoperiodoflujocaja> periodosConvenioEliminados) {
        this.periodosConvenioEliminados = periodosConvenioEliminados;
    }

    public Calendar getFechaInicioConvenio() {
        return fechaInicioConvenio;
    }

    public void setFechaInicioConvenio(Calendar fechaInicioConvenio) {
        this.fechaInicioConvenio = fechaInicioConvenio;
    }

    public Calendar getFechaFinConvenio() {
        return fechaFinConvenio;
    }

    public void setFechaFinConvenio(Calendar fechaFinConvenio) {
        this.fechaFinConvenio = fechaFinConvenio;
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

    public UIExtendedDataTable getTablaFuentesIngresos() {
        return tablaFuentesIngresos;
    }

    public void setTablaFuentesIngresos(UIExtendedDataTable tablaFuentesIngresos) {
        this.tablaFuentesIngresos = tablaFuentesIngresos;
    }

    public int getColumnaEvento() {
        return columnaEvento;
    }

    public void setColumnaEvento(int columnaEvento) {
        this.columnaEvento = columnaEvento;
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }

    public double getValorTotalContrato() {
        return convenio.getNumvlrcontrato().doubleValue();
    }

    /**
     * Columnas para periodos del flujo de caja.
     *
     * De acuerdo con el tamaño de la lista de periodos de flujo de caja se
     * contruye una lista de enteros (desde la posición 0) que representarán los
     * índices de los elementos de los arreglos.
     *
     * @return Lista de enteros que representan las columnas de los periodos.
     */
    public List<Integer> getColumnasPeriodos() {
        List<Integer> items = new ArrayList<Integer>();
        int iterador = 0;

        if (periodosConvenio == null) {
            Integer item = iterador;
            items.add(item);
        } else {
            while (iterador < periodosConvenio.size()) {
                Integer item = iterador;
                items.add(item);

                iterador++;
            }
        }

        return items;
    }

    /**
     * Inicia los objetos objetos para mostrar el flujo de caja.
     *
     * 1. Establece el convenio marco. 2. Crear los periodos del flujo de caja
     * con base en las fechas del convenio marco. 3. Crea la estructura para el
     * flujo de ingresos, inicia los totales 4. Crea la estructura para el flujo
     * de egresos, inicia los totales 5. Calcula los totales del flujo.
     *
     */
    public void iniciarFlujoCaja() {
        nuevoContratoBasico = (NuevoContratoBasico) FacesUtils.getManagedBean("Supervisor$Contrato");
        convenio = nuevoContratoBasico.getContrato();

        if (!flujoCajaIniciado) {
            flujoIngresos = new ArrayList<FlujoIngresos>();
            flujoEgresos = new ArrayList<FlujoEgresos>();
        }
        generarPeriodosFlujoCaja();
        crearEstructuraFlujoIngresos();
        iniciarTotalesIngresosPeriodo();
        crearEstructuraFlujoEgresosConContratos();
        iniciarTotalesEgresosPeriodo();
        refrescarTotalesIngresos();
        calcularTotalesFlujoEgreso();
        generarPeriodosFlujoCaja();

        flujoCajaIniciado = true;
    }

    /**
     * Crear estructura del flujo de ingresos.
     *
     * De acuerdo con las fuentes de recursos definidas y los items de
     * naturaleza ingreso ('I'), se crea la estructura de ingresos a través de
     * una lista FlujoIngresos.
     */
    public void crearEstructuraFlujoIngresos() {
        this.flujoIngresos = new ArrayList<FlujoIngresos>();
        this.totalIngresos = 0;
        planifmovimientoconvenioentidad = new ArrayList<Planificacionmovconvenioentidad>();
        planifmovimientoconvenio = new ArrayList<Planificacionmovconvenio>();

        itemsFlujoIngresos = getSessionBeanCobra().getCobraService().itemsFlujoCajaPorNaturaleza("I");

        for (Fuenterecursosconvenio fuenteRecursosConvenio : nuevoContratoBasico.getRecursosconvenio().getLstFuentesRecursos()) {
            FlujoIngresos flujoIngresosEntidad = new FlujoIngresos();

            Tercero entidadAportante = getSessionBeanCobra().getCobraService().encontrarTerceroPorId(fuenteRecursosConvenio.getTercero().getIntcodigo());

            planifmovimientoconvenioentidad = getSessionBeanCobra().getCobraService().buscarPlanificacionConvenioEntidad(fuenteRecursosConvenio.getIdfuenterecursosconvenio());

            if (planifmovimientoconvenioentidad.isEmpty()) {
                flujoIngresosEntidad.crearEstructuraFlujoIngresosEntidad(fuenteRecursosConvenio, entidadAportante, periodosConvenio);
                flujoIngresosEntidad.calcularTotalIngresosFuente(periodosConvenio.size());

            } else {
                flujoIngresosEntidad.setPlanMovimientosConvenioEntidad(planifmovimientoconvenioentidad);
                flujoIngresosEntidad.setEntidadAportante(entidadAportante);
                flujoIngresosEntidad.actualizarPlanMovimientosEntidad(periodosConvenio, fuenteRecursosConvenio);
                flujoIngresosEntidad.calcularTotalIngresosFuente(periodosConvenio.size());
            }

            flujoIngresos.add(flujoIngresosEntidad);
        }

        for (Itemflujocaja itemFlujoIngresos : itemsFlujoIngresos) {
            FlujoIngresos flujoIngresosEntidad = new FlujoIngresos();

            planifmovimientoconvenio = getSessionBeanCobra().getCobraService().buscarPlanificacionConvenio(itemFlujoIngresos.getIditemflujocaja(), convenio.getIntidcontrato());

            if (planifmovimientoconvenio.isEmpty()) {
                flujoIngresosEntidad.crearEstructuraFlujoIngresosOtrosItems(itemFlujoIngresos, periodosConvenio, convenio);
                flujoIngresosEntidad.calcularTotalIngresosFuente(periodosConvenio.size());
            } else {
                flujoIngresosEntidad.setPlanMovimientosIngresosConvenio(planifmovimientoconvenio);
                flujoIngresosEntidad.setItemFlujoIngresos(itemFlujoIngresos);
                flujoIngresosEntidad.actualizarPlanMovimientosIngresosConvenio(periodosConvenio, convenio);
                flujoIngresosEntidad.calcularTotalIngresosFuente(periodosConvenio.size());
            }

            flujoIngresos.add(flujoIngresosEntidad);
        }
    }

    /**
     * Inicializar los totales de ingresos para los periodos.
     *
     * Inicializa los totales de ingresos por periodo.
     */
    public void iniciarTotalesIngresosPeriodo() {
        this.totalIngresosPeriodo = new double[periodosConvenio.size()];
        this.totalIngresosPeriodoAcumulativo = new double[periodosConvenio.size()];
        int iterador = 0;

        while (iterador < periodosConvenio.size()) {
            this.totalIngresosPeriodo[iterador] = 0;
            this.totalIngresosPeriodoAcumulativo[iterador] = 0;

            iterador++;
        }
    }

    /**
     * Cambió valor de un ingreso por entidad. Valida que el nuevo ingreso
     * diligenciado no exceda el valor disponible por distribuir de la fuente de
     * ingresos. En caso de superarlo se ajusta el valor diligenciado al total
     * disponible y se informa del error.
     *
     */
    public void cambioValorIngreso() {
        FlujoIngresos fuenteIngresosEvento = (FlujoIngresos) tablaFuentesIngresos.getRowData();
        BigDecimal disponibleInicial = BigDecimal.ZERO;
        BigDecimal disponible = BigDecimal.ZERO;

        if (fuenteIngresosEvento.isIngresoEntidad()) {
            Map<String, String> parametros = FacesUtils.getExternalContext().getRequestParameterMap();
            columnaEvento = Integer.valueOf(parametros.get("columnaEvento"));
            disponibleInicial = fuenteIngresosEvento.getFuenteRecursosConvenio().getValoraportado().subtract(fuenteIngresosEvento.getTotalIngresosFuente());
            fuenteIngresosEvento.calcularTotalIngresosFuente(periodosConvenio.size());
            disponible = fuenteIngresosEvento.fuenteRecursosConvenio.getValoraportado().subtract(fuenteIngresosEvento.getTotalIngresosFuente());

            if (disponible.compareTo(BigDecimal.ZERO) < 0) {
                FacesUtils.addErrorMessage("El valor ingresado es superior al valor disponible. Se ajusta el valor al disponible.");
                FacesUtils.addInfoMessage("Se ajusta el valor al disponible.");
                fuenteIngresosEvento.planMovimientosConvenioEntidad.get(columnaEvento).setValor(disponibleInicial);
                fuenteIngresosEvento.calcularTotalIngresosFuente(periodosConvenio.size());
                disponible = fuenteIngresosEvento.fuenteRecursosConvenio.getValoraportado().subtract(fuenteIngresosEvento.getTotalIngresosFuente());
                fuenteIngresosEvento.planMovimientosConvenioEntidad.get(columnaEvento).setValor(disponibleInicial.add(disponible));
            }
        }

        refrescarTotalesIngresos();
    }

    /**
     * Cambió valor planificado de un egreso de contrato. Valida que el nuevo
     * valor diligenciado no exceda el valor disponible por distribuir del
     * contrato. En caso de superarlo se ajusta el valor diligenciado al total
     * disponible y se informa del error.
     */
    public void cambioValorEgresoContrato() {
        FlujoEgresos flujoEgresosDetallePry = (FlujoEgresos) extendedDataTableEgresos.getRowData();
        BigDecimal disponibleInicial = BigDecimal.ZERO;
        BigDecimal disponible = BigDecimal.ZERO;

        Map<String, String> parametros = FacesUtils.getExternalContext().getRequestParameterMap();
        columnaEvento = Integer.valueOf(parametros.get("columnaEvento"));
        if (flujoEgresosDetallePry.isEgresoContrato()) {
            disponibleInicial = flujoEgresosDetallePry.getContrato().getNumvlrcontrato().subtract(flujoEgresosDetallePry.getTotalEgresosFuente());
            flujoEgresosDetallePry.calcularTotalEgresosFuente(periodosConvenio.size());
            disponible = flujoEgresosDetallePry.getContrato().getNumvlrcontrato().subtract(flujoEgresosDetallePry.getTotalEgresosFuente());

            if (disponible.compareTo(BigDecimal.ZERO) < 0) {
                FacesUtils.addErrorMessage("El valor ingresado es superior al valor disponible. Se ajusta el valor al disponible.");
                FacesUtils.addInfoMessage("Se ajusta el valor al disponible.");
                flujoEgresosDetallePry.planMovimientosContrato.get(columnaEvento).setValor(disponibleInicial);
                flujoEgresosDetallePry.calcularTotalEgresosFuente(periodosConvenio.size());
                disponible = flujoEgresosDetallePry.getContrato().getNumvlrcontrato().subtract(flujoEgresosDetallePry.getTotalEgresosFuente());
                flujoEgresosDetallePry.planMovimientosContrato.get(columnaEvento).setValor(disponibleInicial.add(disponible));
            }
        }
        if (flujoEgresosDetallePry.isEgresoPryDirecto()) {
            disponibleInicial = flujoEgresosDetallePry.getTotalEsperadoEgresosFuente().subtract(flujoEgresosDetallePry.getTotalEgresosFuente());
            flujoEgresosDetallePry.calcularTotalEgresosFuente(periodosConvenio.size());
            disponible = flujoEgresosDetallePry.getTotalEsperadoEgresosFuente().subtract(flujoEgresosDetallePry.getTotalEgresosFuente());

            if (disponible.compareTo(BigDecimal.ZERO) < 0) {
                FacesUtils.addErrorMessage("El valor ingresado es superior al valor disponible. Se ajusta el valor al disponible.");
                FacesUtils.addInfoMessage("Se ajusta el valor al disponible.");
                flujoEgresosDetallePry.planMovimientosPryDirecto.get(columnaEvento).setValor(disponibleInicial);
                flujoEgresosDetallePry.calcularTotalEgresosFuente(periodosConvenio.size());
                disponible = flujoEgresosDetallePry.getTotalEsperadoEgresosFuente().subtract(flujoEgresosDetallePry.getTotalEgresosFuente());
                flujoEgresosDetallePry.planMovimientosPryDirecto.get(columnaEvento).setValor(disponibleInicial.add(disponible));
            }
        }
        if (flujoEgresosDetallePry.isEgresoPryOtros()) {
            disponibleInicial = flujoEgresosDetallePry.getTotalEsperadoEgresosFuente().subtract(flujoEgresosDetallePry.getTotalEgresosFuente());
            flujoEgresosDetallePry.calcularTotalEgresosFuente(periodosConvenio.size());
            disponible = flujoEgresosDetallePry.getTotalEsperadoEgresosFuente().subtract(flujoEgresosDetallePry.getTotalEgresosFuente());

            if (disponible.compareTo(BigDecimal.ZERO) < 0) {
                FacesUtils.addErrorMessage("El valor ingresado es superior al valor disponible. Se ajusta el valor al disponible.");
                FacesUtils.addInfoMessage("Se ajusta el valor al disponible.");
                flujoEgresosDetallePry.planMovimientosPryOtros.get(columnaEvento).setValor(disponibleInicial);
                flujoEgresosDetallePry.calcularTotalEgresosFuente(periodosConvenio.size());
                disponible = flujoEgresosDetallePry.getTotalEsperadoEgresosFuente().subtract(flujoEgresosDetallePry.getTotalEgresosFuente());
                flujoEgresosDetallePry.planMovimientosPryOtros.get(columnaEvento).setValor(disponibleInicial.add(disponible));
            }
        }

        calcularTotalesFlujoEgreso();
    }

    /**
     * Refrescar totales del flujo de ingresos.
     *
     * Después del evento de cambiar el valor de una celda, se refrescan los
     * totales del flujo de ingresos y sus fuentes.
     */
    public void refrescarTotalesIngresos() {
        int iterador;

        iniciarTotalesIngresosPeriodo();
        totalIngresos = 0;

        for (FlujoIngresos flujoIngresosRefrescar : flujoIngresos) {
            flujoIngresosRefrescar.calcularTotalIngresosFuente(periodosConvenio.size());
            totalIngresos += flujoIngresosRefrescar.getTotalIngresosFuente().doubleValue();

            iterador = 0;
            while (iterador < periodosConvenio.size()) {
                if (flujoIngresosRefrescar.isIngresoEntidad()) {
                    totalIngresosPeriodo[iterador] += flujoIngresosRefrescar.getPlanMovimientosConvenioEntidad().get(iterador).getValor().doubleValue();
                } else {
                    totalIngresosPeriodo[iterador] += flujoIngresosRefrescar.getPlanMovimientosIngresosConvenio().get(iterador).getValor().doubleValue();
                }
                iterador++;
            }
        }
        totalIngresosPeriodoAcumulativo[0] += totalIngresosPeriodo[0];
        iterador = 1;

        while (iterador < periodosConvenio.size()) {
            totalIngresosPeriodoAcumulativo[iterador] += (totalIngresosPeriodoAcumulativo[iterador - 1] + totalIngresosPeriodo[iterador]);

            iterador++;
        }
    }

    /**
     * Crear la estructura del flujo de egresos.
     *
     * De acuerdo con los proyectos definidos para el convenio y los items de
     * naturaleza egreso ('E'), se crea la estructura del flujo de egresos a
     * través de una lista FlujoEgresos.
     */
    public void crearEstructuraFlujoEgresos() {
        this.flujoEgresos = new ArrayList<FlujoEgresos>();
        this.totalEgresos = 0;
        this.planifmovimientoconvenioproyecto = new ArrayList<Planificacionmovimientoproyecto>();
        this.planifmovimientoconvenio = new ArrayList<Planificacionmovconvenio>();

        itemsFlujoEgresos = getSessionBeanCobra().getCobraService().itemsFlujoCajaPorNaturaleza("E");

        for (ProyectoPlanOperativo proyectoPlanOperativo : proyectosPlanOperativo) {
            FlujoEgresos itemFlujoEgresos = new FlujoEgresos();
            itemFlujoEgresos.getItemFlujoEgresos().setStrdescripcion(proyectoPlanOperativo.getProyecto().getStrnombrecrot());
            planifmovimientoconvenioproyecto = getSessionBeanCobra().getCobraService().buscarPlanificacionConvenioProyecto(proyectoPlanOperativo.getProyecto().getIntcodigoobra());

            if (planifmovimientoconvenioproyecto.isEmpty()) {
                itemFlujoEgresos.crearEstructuraFlujoEgresosProyecto(proyectoPlanOperativo.getProyecto(), periodosConvenio);
                itemFlujoEgresos.calcularTotalEgresosFuente(periodosConvenio.size());
            } else {
                itemFlujoEgresos.setPlanMovimientosProyecto(planifmovimientoconvenioproyecto);
                itemFlujoEgresos.setProyecto(proyectoPlanOperativo.getProyecto());
                itemFlujoEgresos.actualizarPlanMovimientosProyecto(periodosConvenio);
                itemFlujoEgresos.calcularTotalEgresosFuente(periodosConvenio.size());
            }

            flujoEgresos.add(itemFlujoEgresos);
        }

        for (Itemflujocaja itemFlujoEgresos : itemsFlujoEgresos) {
            FlujoEgresos flujoEgresosEntidad = new FlujoEgresos();

            planifmovimientoconvenio = getSessionBeanCobra().getCobraService().buscarPlanificacionConvenio(itemFlujoEgresos.getIditemflujocaja(), convenio.getIntidcontrato());

            if (planifmovimientoconvenio.isEmpty()) {
                flujoEgresosEntidad.crearEstructuraFlujoEgresosOtrosItems(itemFlujoEgresos, periodosConvenio, convenio);
                flujoEgresosEntidad.calcularTotalEgresosFuente(periodosConvenio.size());

            } else {
                flujoEgresosEntidad.setPlanMovimientosEgresosConvenio(planifmovimientoconvenio);
                flujoEgresosEntidad.setItemFlujoEgresos(itemFlujoEgresos);
                flujoEgresosEntidad.actualizarPlanMovimientosEgresosConvenio(periodosConvenio, convenio);
                flujoEgresosEntidad.calcularTotalEgresosFuente(periodosConvenio.size());

            }

            flujoEgresos.add(flujoEgresosEntidad);
        }
    }

    /**
     * Crear la estructura del flujo de egresos.
     *
     * De acuerdo con los proyectos definidos para el convenio y los items de
     * naturaleza egreso ('E'), se crea la estructura del flujo de egresos a
     * través de una lista FlujoEgresos.
     */
    public void crearEstructuraFlujoEgresosConContratos() {
        List<FlujoEgresos> flujoEgresosContratos;
        this.flujoEgresos = new ArrayList<FlujoEgresos>();
        this.totalEgresos = 0;
        boolean guardarPlanificacionInicial = false;
        this.planifmovimientoconvenioproyecto = new ArrayList<Planificacionmovimientoproyecto>();
        this.planifmovimientoconvenio = new ArrayList<Planificacionmovconvenio>();

        itemsFlujoEgresos = getSessionBeanCobra().getCobraService().itemsFlujoCajaPorNaturaleza("E");

        for (ProyectoPlanOperativo proyectoPlanOperativo : proyectosPlanOperativo) {
            flujoEgresosContratos = new ArrayList<FlujoEgresos>();
            FlujoEgresos itemFlujoEgresosProyecto = new FlujoEgresos();
            itemFlujoEgresosProyecto.getItemFlujoEgresos().setStrdescripcion(proyectoPlanOperativo.getProyecto().getStrnombrecrot());

            FlujoEgresos itemFlujoEgresosPryDirecto = new FlujoEgresos();
            itemFlujoEgresosPryDirecto.setEgresoPryDirecto(true);
            itemFlujoEgresosPryDirecto.getItemFlujoEgresos().setStrdescripcion("Pagos Directos");
            itemFlujoEgresosPryDirecto.setPlanMovimientosProyecto(itemFlujoEgresosProyecto.getPlanMovimientosProyecto());
            itemFlujoEgresosPryDirecto.setTotalEsperadoEgresosFuente(proyectoPlanOperativo.getProyecto().getPagodirecto());

            FlujoEgresos itemFlujoEgresosPryOtros = new FlujoEgresos();
            itemFlujoEgresosPryOtros.setEgresoPryOtros(true);
            itemFlujoEgresosPryOtros.getItemFlujoEgresos().setStrdescripcion("Otros Pagos");
            itemFlujoEgresosPryOtros.setPlanMovimientosProyecto(itemFlujoEgresosProyecto.getPlanMovimientosProyecto());
            itemFlujoEgresosPryOtros.setTotalEsperadoEgresosFuente(proyectoPlanOperativo.getProyecto().getOtrospagos());

            planifmovimientoconvenioproyecto = getSessionBeanCobra().getCobraService().buscarPlanificacionConvenioProyecto(proyectoPlanOperativo.getProyecto().getIntcodigoobra());
            //Si no se han cargado aún las planificaciones de proyecto 
            if (planifmovimientoconvenioproyecto.isEmpty()) {
                guardarPlanificacionInicial = true;
                itemFlujoEgresosProyecto.crearEstructuraFlujoEgresosProyecto(proyectoPlanOperativo.getProyecto(), periodosConvenio);
                getSessionBeanCobra().getCobraService().guardarFlujoEgresosProyecto(planifmovimientoconvenioproyecto);
//                itemFlujoEgresosProyecto.calcularTotalEgresosFuente(periodosConvenio.size());
                //Se cargan los registros correspondientes a los contratos del proyecto
                for (Contrato contratoProyecto : proyectoPlanOperativo.getContratosProyecto()) {
                    FlujoEgresos itemFlujoEgresosContrato = new FlujoEgresos();
                    itemFlujoEgresosContrato.setEgresoContrato(true);
                    itemFlujoEgresosContrato.getItemFlujoEgresos().setStrdescripcion(contratoProyecto.getStrnombre());
                    itemFlujoEgresosContrato.setPlanMovimientosProyecto(itemFlujoEgresosProyecto.getPlanMovimientosProyecto());
                    itemFlujoEgresosContrato.crearEstructuraFlujoEgresosContrato(contratoProyecto, periodosConvenio);
                    flujoEgresosContratos.add(itemFlujoEgresosContrato);
                }
                //Se carga el registro correspondiente a los pagos directos del proyecto
                itemFlujoEgresosPryDirecto.setPlanMovimientosProyecto(itemFlujoEgresosProyecto.getPlanMovimientosProyecto());
                itemFlujoEgresosPryDirecto.crearEstructuraFlujoEgresosPryDirecto(periodosConvenio);

                //Se carga el registro correspondiente a los otros pagos del proyecto
                itemFlujoEgresosPryOtros.setPlanMovimientosProyecto(itemFlujoEgresosProyecto.getPlanMovimientosProyecto());
                itemFlujoEgresosPryOtros.crearEstructuraFlujoEgresosPryOtros(periodosConvenio);
            } else {//Si ya se han establecido las planificaciones de movimientos del proyecto
                //Se cargan las  planificaciones de contrato, pagos directos y otros pagos para el proyecto
                for (Planificacionmovimientoproyecto planificacionmovimientoproyecto : planifmovimientoconvenioproyecto) {
                    planificacionmovimientoproyecto.setPlanificacionmovimientocontratos(new LinkedHashSet(getSessionBeanCobra().getCobraService().buscarPlanificacionConvenioContrato(planificacionmovimientoproyecto.getIdplanificacionmovpry())));
                    planificacionmovimientoproyecto.setPlanificacionmovimientoprydirectos(new LinkedHashSet(getSessionBeanCobra().getCobraService().buscarPlanificacionConvenioPrydirecto(planificacionmovimientoproyecto.getIdplanificacionmovpry())));
                    planificacionmovimientoproyecto.setPlanificacionmovimientopryotroses(new LinkedHashSet(getSessionBeanCobra().getCobraService().buscarPlanificacionConvenioPryotros(planificacionmovimientoproyecto.getIdplanificacionmovpry())));
                }
                for (Planificacionmovimientoproyecto planificacionmovimientoproyecto : planifmovimientoconvenioproyecto) {
                    //Se crean nuevas planificaciones de contrato para aquellos sin planificación
                    if (planificacionmovimientoproyecto.getPlanificacionmovimientocontratos().isEmpty()) {
                        guardarPlanificacionInicial = true;
                        for (Contrato contratoProyecto : proyectoPlanOperativo.getContratosProyecto()) {
                            Planificacionmovimientocontrato planMovimientoContrato = new Planificacionmovimientocontrato();
                            planMovimientoContrato.setContrato(contratoProyecto);
                            planMovimientoContrato.setPlanificacionmovimientoproyecto(planificacionmovimientoproyecto);
                            planMovimientoContrato.setValor(BigDecimal.ZERO);
                            planificacionmovimientoproyecto.getPlanificacionmovimientocontratos().add(planMovimientoContrato);
                        }
                    }
                    //Se crean nuevas planificaciones de pago directo para aquellos sin planificación
                    if (planificacionmovimientoproyecto.getPlanificacionmovimientoprydirectos().isEmpty()) {
                        guardarPlanificacionInicial = true;
                        Planificacionmovimientoprydirecto planMovimientoPryDirecto = new Planificacionmovimientoprydirecto();
                        planMovimientoPryDirecto.setPlanificacionmovimientoproyecto(planificacionmovimientoproyecto);
                        planMovimientoPryDirecto.setValor(BigDecimal.ZERO);
                        planificacionmovimientoproyecto.getPlanificacionmovimientoprydirectos().add(planMovimientoPryDirecto);
                    }
                    //Se crean nuevas planificaciones de otros pagos para aquellos sin planificación
                    if (planificacionmovimientoproyecto.getPlanificacionmovimientopryotroses().isEmpty()) {
                        guardarPlanificacionInicial = true;
                        Planificacionmovimientopryotros planMovimientoPryOtros = new Planificacionmovimientopryotros();
                        planMovimientoPryOtros.setPlanificacionmovimientoproyecto(planificacionmovimientoproyecto);
                        planMovimientoPryOtros.setValor(BigDecimal.ZERO);
                        planificacionmovimientoproyecto.getPlanificacionmovimientopryotroses().add(planMovimientoPryOtros);
                    }
                }
                FlujoEgresos itemFlujoEgresosContrato = null;
                for (Planificacionmovimientoproyecto planificacionmovimientoproyecto : planifmovimientoconvenioproyecto) {
                    //Se cargan los registros de planificaciones de contrato
                    for (Object object : planificacionmovimientoproyecto.getPlanificacionmovimientocontratos()) {
                        Planificacionmovimientocontrato planMovimientoContrato = (Planificacionmovimientocontrato) object;
                        for (FlujoEgresos fe : flujoEgresosContratos) {
                            if (fe.getContrato().getIntidcontrato() == planMovimientoContrato.getContrato().getIntidcontrato()) {
                                itemFlujoEgresosContrato = fe;
                            }
                        }
                        if (itemFlujoEgresosContrato == null) {
                            itemFlujoEgresosContrato = new FlujoEgresos();
                            itemFlujoEgresosContrato.setContrato(planMovimientoContrato.getContrato());
                            itemFlujoEgresosContrato.setEgresoContrato(true);
                            itemFlujoEgresosContrato.getItemFlujoEgresos().setStrdescripcion(planMovimientoContrato.getContrato().getStrnombre());
                            flujoEgresosContratos.add(itemFlujoEgresosContrato);
                        }
                        itemFlujoEgresosContrato.getPlanMovimientosContrato().add(planMovimientoContrato);
                    }

                    //Se cargan todas las planificaciones de pagos directos en el registro pagos directos
                    for (Object object : planificacionmovimientoproyecto.getPlanificacionmovimientoprydirectos()) {
                        Planificacionmovimientoprydirecto planMovimientoPryDirecto = (Planificacionmovimientoprydirecto) object;
                        itemFlujoEgresosPryDirecto.getPlanMovimientosPryDirecto().add(planMovimientoPryDirecto);
                    }

                    //Se cargan todas las planificaciones de otros pagos en el registro de otros pagos
                    for (Object object : planificacionmovimientoproyecto.getPlanificacionmovimientopryotroses()) {
                        Planificacionmovimientopryotros planMovimientoPryOtros = (Planificacionmovimientopryotros) object;
                        itemFlujoEgresosPryOtros.getPlanMovimientosPryOtros().add(planMovimientoPryOtros);
                    }
                }
                itemFlujoEgresosProyecto.setPlanMovimientosProyecto(planifmovimientoconvenioproyecto);
                itemFlujoEgresosProyecto.setProyecto(proyectoPlanOperativo.getProyecto());
                itemFlujoEgresosProyecto.actualizarPlanMovimientosProyecto(periodosConvenio);
                itemFlujoEgresosProyecto.calcularTotalEgresosFuente(periodosConvenio.size());
            }
            flujoEgresos.add(itemFlujoEgresosProyecto);
            flujoEgresos.addAll(flujoEgresosContratos);
            flujoEgresos.add(itemFlujoEgresosPryDirecto);
            flujoEgresos.add(itemFlujoEgresosPryOtros);
        }
        for (Itemflujocaja itemFlujoEgresos : itemsFlujoEgresos) {
            FlujoEgresos flujoEgresosEntidad = new FlujoEgresos();
            flujoEgresosEntidad.setEgresoOtros(true);

            planifmovimientoconvenio = getSessionBeanCobra().getCobraService().buscarPlanificacionConvenio(itemFlujoEgresos.getIditemflujocaja(), convenio.getIntidcontrato());

            if (planifmovimientoconvenio.isEmpty()) {
                flujoEgresosEntidad.crearEstructuraFlujoEgresosOtrosItems(itemFlujoEgresos, periodosConvenio, convenio);
                flujoEgresosEntidad.calcularTotalEgresosFuente(periodosConvenio.size());

            } else {
                flujoEgresosEntidad.setPlanMovimientosEgresosConvenio(planifmovimientoconvenio);
                flujoEgresosEntidad.setItemFlujoEgresos(itemFlujoEgresos);
                flujoEgresosEntidad.actualizarPlanMovimientosEgresosConvenio(periodosConvenio, convenio);
                flujoEgresosEntidad.calcularTotalEgresosFuente(periodosConvenio.size());

            }

            flujoEgresos.add(flujoEgresosEntidad);
            if (itemFlujoEgresos.getIditemflujocaja() == 6) {
                for (Fuenterecursosconvenio fuenteRecursosConvenio : nuevoContratoBasico.getRecursosconvenio().getLstFuentesRecursos()) {
                    FlujoEgresos flujoEgresosCuotaGerencia = new FlujoEgresos();
                    flujoEgresosCuotaGerencia.setEgresoCuotaGerencia(true);
                    Tercero entidadAportante = getSessionBeanCobra().getCobraService().encontrarTerceroPorId(fuenteRecursosConvenio.getTercero().getIntcodigo());
                    flujoEgresosCuotaGerencia.setItemFlujoEgresos(new Itemflujocaja());
                    flujoEgresosCuotaGerencia.getItemFlujoEgresos().setStrdescripcion(entidadAportante.getStrnombrecompleto());

                    planificacionmovcuotagerencia = getSessionBeanCobra().getCobraService().buscarPlanificacionCuotaGerencia(fuenteRecursosConvenio.getIdfuenterecursosconvenio());

                    if (planificacionmovcuotagerencia.isEmpty()) {
                        flujoEgresosCuotaGerencia.crearEstructuraFlujoEgresosEntidad(fuenteRecursosConvenio, entidadAportante, periodosConvenio);
                        flujoEgresosCuotaGerencia.calcularTotalEgresosFuente(periodosConvenio.size());

                    } else {
                        flujoEgresosCuotaGerencia.setPlanificacionesmovcuotagerencia(planificacionmovcuotagerencia);
                        flujoEgresosCuotaGerencia.setEntidadAportante(entidadAportante);
                        flujoEgresosCuotaGerencia.actualizarPlanMovimientosEntidad(periodosConvenio, fuenteRecursosConvenio);
                        flujoEgresosCuotaGerencia.calcularTotalEgresosFuente(periodosConvenio.size());
                    }

                    flujoEgresos.add(flujoEgresosCuotaGerencia);
                }

                for (Itemflujocaja itemFlujoIngresos : itemsFlujoIngresos) {
                    FlujoIngresos flujoIngresosEntidad = new FlujoIngresos();

                    planifmovimientoconvenio = getSessionBeanCobra().getCobraService().buscarPlanificacionConvenio(itemFlujoIngresos.getIditemflujocaja(), convenio.getIntidcontrato());

                    if (planifmovimientoconvenio.isEmpty()) {
                        flujoIngresosEntidad.crearEstructuraFlujoIngresosOtrosItems(itemFlujoIngresos, periodosConvenio, convenio);
                        flujoIngresosEntidad.calcularTotalIngresosFuente(periodosConvenio.size());
                    } else {
                        flujoIngresosEntidad.setPlanMovimientosIngresosConvenio(planifmovimientoconvenio);
                        flujoIngresosEntidad.setItemFlujoIngresos(itemFlujoIngresos);
                        flujoIngresosEntidad.actualizarPlanMovimientosIngresosConvenio(periodosConvenio, convenio);
                        flujoIngresosEntidad.calcularTotalIngresosFuente(periodosConvenio.size());
                    }

                    flujoIngresos.add(flujoIngresosEntidad);
                }
            }
        }

        if (guardarPlanificacionInicial) {
            System.out.println("guardarFlujoCaja");
            guardarFlujoCaja();
        }
    }

    /**
     * Inicializar los totales de egresos para los periodos.
     *
     * Inicializa los totales de egresos por periodo.
     */
    public void iniciarTotalesEgresosPeriodo() {
        totalEgresosPeriodo = new double[periodosConvenio.size()];
        totalEgresosPeriodoAcumulativo = new double[periodosConvenio.size()];

        int iterador = 0;

        while (iterador < periodosConvenio.size()) {
            totalEgresosPeriodo[iterador] = 0;

            iterador++;
        }

        iniciarAcumuladoGMF();
    }

    /**
     * Iniciar acumulado GMF. Inicializa los valores del arreglo que almacena
     * los valores acumulados por periodo para el cálculo del GMF.
     */
    private void iniciarAcumuladoGMF() {
        acumuladoGMF = new double[periodosConvenio.size()];

        int iterador = 0;

        while (iterador < periodosConvenio.size()) {
            acumuladoGMF[iterador] = 0;

            iterador++;
        }
    }

    /**
     * Refrescar totales del flujo de egresos.
     *
     * Después del evento de cambiar el valor de una celda, se refresca el flujo
     * de egresos.
     */
    public void refrescarTotalesEgresos() {
        int iterador;
        iniciarTotalesEgresosPeriodo();
        totalEgresos = 0;

        for (FlujoEgresos flujoEgresosRefrescar : flujoEgresos) {
            flujoEgresosRefrescar.calcularTotalEgresosFuente(periodosConvenio.size());
            totalEgresos += flujoEgresosRefrescar.getTotalEgresosFuente().doubleValue();

            iterador = 0;
            while (iterador < periodosConvenio.size()) {
                if (flujoEgresosRefrescar.isEgresoProyecto()) {
                    totalEgresosPeriodo[iterador] += flujoEgresosRefrescar.getPlanMovimientosProyecto().get(iterador).getValor().doubleValue();
                    acumuladoGMF[iterador] += flujoEgresosRefrescar.getPlanMovimientosProyecto().get(iterador).getValor().doubleValue();
                } else if (flujoEgresosRefrescar.isEgresoOtros()) {
                    totalEgresosPeriodo[iterador] += flujoEgresosRefrescar.getPlanMovimientosEgresosConvenio().get(iterador).getValor().doubleValue();

                    if (flujoEgresosRefrescar.getItemFlujoEgresos().getBooltienegmf()) {
                        acumuladoGMF[iterador] += flujoEgresosRefrescar.getPlanMovimientosEgresosConvenio().get(iterador).getValor().doubleValue();
                    }
                }
                iterador++;
            }
        }

        calcularGMF();

        totalEgresosPeriodoAcumulativo[0] += totalEgresosPeriodo[0];
        iterador = 1;

        while (iterador < periodosConvenio.size()) {
            totalEgresosPeriodoAcumulativo[iterador] += (totalEgresosPeriodoAcumulativo[iterador - 1] + totalEgresosPeriodo[iterador]);

            iterador++;
        }

    }

    /**
     * Calcula el valor para el GMF -Gravamen por Movimiento Financiero-.
     *
     * La operación se realiza sobre los valores de egresos de proyectos y otros
     * items definidos en un periodo dado.
     */
    public void calcularGMF() {
        double divisorGMF = Double.valueOf(bundle.getString("divisorGMF"));
        double multiplicadorGMF = Double.valueOf(bundle.getString("multiplicadorGMF"));
        int iterador;

        iniciarAcumuladoGMF();

        iterador = 0;

        while (iterador < periodosConvenio.size()) {
            for (FlujoEgresos flujoEgresosRecorrer : flujoEgresos) {
                if (flujoEgresosRecorrer.isEgresoProyecto()) {
                    acumuladoGMF[iterador] += flujoEgresosRecorrer.getPlanMovimientosProyecto().get(iterador).getValor().doubleValue();
                } else if (flujoEgresosRecorrer.isEgresoOtros()) {
                    if (flujoEgresosRecorrer.itemFlujoEgresos.getBooltienegmf().booleanValue()) {
                        acumuladoGMF[iterador] += flujoEgresosRecorrer.planMovimientosEgresosConvenio.get(iterador).getValor().doubleValue();
                    }
                }
            }
            iterador++;
        }

        for (FlujoEgresos flujoEgresosRecorrer : flujoEgresos) {
            if (flujoEgresosRecorrer.isEgresoOtros()) {
                if (flujoEgresosRecorrer.itemFlujoEgresos.getStrdescripcion().contains("GMF")) {
                    iterador = 0;

                    while (iterador < periodosConvenio.size()) {
                        double valorGMFPeriodo = acumuladoGMF[iterador] / divisorGMF * multiplicadorGMF;

                        flujoEgresosRecorrer.planMovimientosEgresosConvenio.get(iterador).setValor(BigDecimal.valueOf(valorGMFPeriodo));
                        totalEgresosPeriodo[iterador] += valorGMFPeriodo;
                        totalEgresos += valorGMFPeriodo;

                        iterador++;
                    }
                }

                flujoEgresosRecorrer.calcularTotalEgresosFuente(periodosConvenio.size());
            }
        }

    }

    /**
     * Validar flujo de caja.
     *
     * Valida que: 1. El total de ingresos distribuídos para una entidad no sea
     * mayor al valor aportado por la entidad. 2. El total de egresos
     * distribuídos para un proyecto no sea mayor al valor total del proyecto.
     * 3. El valor total egresos del flujo no sea mayor al valor total de
     * ingresos definidos.
     *
     * @return true si cumple con los requisitos, false si no los cumple.
     */
    public boolean validarFlujoCaja() {
        boolean cumpleRequisitos = true;

        for (FlujoIngresos flujoIngresosValidar : flujoIngresos) {
            if (flujoIngresosValidar.ingresoEntidad) {
                if (flujoIngresosValidar.totalIngresosFuente.doubleValue() > flujoIngresosValidar.fuenteRecursosConvenio.getValoraportado().doubleValue()) {
                    cumpleRequisitos = false;
                    FacesUtils.addErrorMessage(bundle.getString("errorIngresosMayorAAportes"));
                    nuevoContratoBasico.setMensajePlanOperativo(false, true, bundle.getString("errorIngresosMayorAAportes"));
                }
            }
        }

        for (FlujoEgresos flujoEgresosValidar : flujoEgresos) {
            if (flujoEgresosValidar.egresoProyecto) {
                if (flujoEgresosValidar.totalEgresosFuente.doubleValue() > flujoEgresosValidar.getProyecto().getNumvaltotobra().doubleValue()) {
                    cumpleRequisitos = false;

                    FacesUtils.addErrorMessage(bundle.getString("errorEgresosMayorAValorProyecto"));
                    nuevoContratoBasico.setMensajePlanOperativo(false, true, bundle.getString("errorEgresosMayorAValorProyecto"));
                }
            }
        }

        if (this.totalEgresos > this.totalIngresos) {
            cumpleRequisitos = false;

            FacesUtils.addErrorMessage(bundle.getString("errorTotalEgresosMayorATotalIngresos"));
            nuevoContratoBasico.setMensajePlanOperativo(false, true, bundle.getString("errorTotalEgresosMayorATotalIngresos"));
        }

        return cumpleRequisitos;
    }

    /**
     * Guardar el flujo de caja.
     *
     * Si el la condiciones la validación se cumple (si es true), se guardan los
     * objetos del flujo de caja.
     */
    public void guardarFlujoCaja() {
        guardarPeriodosConvenio();

        //if (validarFlujoCaja()) {
        for (FlujoIngresos flujoIngresosGuardar : flujoIngresos) {
            flujoIngresosGuardar.refrescarPeriodos(periodosConvenio);

            if (flujoIngresosGuardar.ingresoEntidad) {
                getSessionBeanCobra().getCobraService().guardarFlujoIngresosConvenioEntidad(flujoIngresosGuardar.planMovimientosConvenioEntidad);

                if (flujoIngresosGuardar.movimientosConvenioEntidadEliminados.size() > 0) {
                    getSessionBeanCobra().getCobraService().borrarIngresosConvenioEntidad(flujoIngresosGuardar.movimientosConvenioEntidadEliminados);
                }
            } else {
                getSessionBeanCobra().getCobraService().guardarFlujoConvenioOtrosConceptos(flujoIngresosGuardar.planMovimientosIngresosConvenio);

                if (flujoIngresosGuardar.movimientosIngresosConvenioEliminados.size() > 0) {
                    getSessionBeanCobra().getCobraService().borrarMovimientosConvenio(flujoIngresosGuardar.movimientosIngresosConvenioEliminados);
                }
            }
        }

        for (FlujoEgresos flujoEgresosGuardar : flujoEgresos) {
            flujoEgresosGuardar.refrescarPeriodos(periodosConvenio);

            if (flujoEgresosGuardar.egresoProyecto) {
                getSessionBeanCobra().getCobraService().guardarFlujoEgresosProyecto(flujoEgresosGuardar.planMovimientosProyecto);

                if (flujoEgresosGuardar.movimientosProyectoEliminados.size() > 0) {
                    getSessionBeanCobra().getCobraService().borrarEgresosProyecto(flujoEgresosGuardar.movimientosProyectoEliminados);
                }
            } else if (flujoEgresosGuardar.egresoOtros) {
                getSessionBeanCobra().getCobraService().guardarFlujoConvenioOtrosConceptos(flujoEgresosGuardar.planMovimientosEgresosConvenio);

                if (flujoEgresosGuardar.movimientosEgresosConvenioEliminados.size() > 0) {
                    getSessionBeanCobra().getCobraService().borrarMovimientosConvenio(flujoEgresosGuardar.movimientosEgresosConvenioEliminados);
                }
            } else if (flujoEgresosGuardar.egresoContrato) {
                getSessionBeanCobra().getCobraService().guardarFlujoEgresosContrato(flujoEgresosGuardar.planMovimientosContrato);

                if (flujoEgresosGuardar.planMovimientosContratoEliminados.size() > 0) {
                    getSessionBeanCobra().getCobraService().borrarEgresosContrato(flujoEgresosGuardar.planMovimientosContratoEliminados);
                }
            } else if (flujoEgresosGuardar.egresoPryDirecto) {
                getSessionBeanCobra().getCobraService().guardarFlujoEgresosPrydirecto(flujoEgresosGuardar.planMovimientosPryDirecto);

                if (flujoEgresosGuardar.planMovimientosPryDirectoEliminados.size() > 0) {
                    getSessionBeanCobra().getCobraService().borrarEgresosPrydirecto(flujoEgresosGuardar.planMovimientosPryDirectoEliminados);
                }
            } else if (flujoEgresosGuardar.egresoPryOtros) {
                getSessionBeanCobra().getCobraService().guardarFlujoEgresosPryotros(flujoEgresosGuardar.planMovimientosPryOtros);

                if (flujoEgresosGuardar.planMovimientosPryOtrosEliminados.size() > 0) {
                    getSessionBeanCobra().getCobraService().borrarEgresosPryotros(flujoEgresosGuardar.planMovimientosPryOtrosEliminados);
                }
            } else if (flujoEgresosGuardar.egresoCuotaGerencia) {
                getSessionBeanCobra().getCobraService().guardarFlujoEgresosCuotaGerencia(flujoEgresosGuardar.planificacionesmovcuotagerencia);

                if (flujoEgresosGuardar.planificacionesmovcuotagerenciaEliminados.size() > 0) {
                    getSessionBeanCobra().getCobraService().borrarEgresosCuotaGerencia(flujoEgresosGuardar.planificacionesmovcuotagerenciaEliminados);
                }
            }
        }
        //}
    }

    /**
     * Guardar periodos del flujo de caja.
     *
     * Se guardan los periodos definidos para el flujo de caja según la fecha de
     * inicio y finalización del convenio marco.
     */
    public void guardarPeriodosConvenio() {
        getSessionBeanCobra().getCobraService().guardarPeriodosConvenio(periodosConvenio);

        if (!periodosConvenioEliminados.isEmpty()) {
            getSessionBeanCobra().getCobraService().borrarPeriodosConvenio(periodosConvenioEliminados);
        }
    }

    /**
     * Genera los periodos del flujo de caja de acuerdo con la fecha de inicio y
     * finalización del convenio marco.
     *
     * Llena la lista periodosConvenio estableciendo como fecha de inicio del
     * primer periodo la fecha de inicio del convenio marco, se incrementa
     * mensualmente hasta generar el último periodo y establece como fecha de
     * finalización la fecha final del convenio marco.
     */
    public void generarPeriodosFlujoCaja() {
        periodosConvenio = new ArrayList<Relacioncontratoperiodoflujocaja>();
        periodosConvenioEliminados = new ArrayList<Relacioncontratoperiodoflujocaja>();
        Relacioncontratoperiodoflujocaja periodoConvenio;
        Periodoflujocaja periodo;
        Calendar fechaPeriodos = GregorianCalendar.getInstance();
        fechaInicioConvenio = Calendar.getInstance();
        fechaFinConvenio = Calendar.getInstance();

        fechaInicioConvenio.setTime(convenio.getDatefechaini());
        fechaFinConvenio.setTime(convenio.getDatefechafin());

        periodosConvenio = getSessionBeanCobra().getCobraService().encontrarPeriodosConvenio(convenio.getIntidcontrato());

        if (periodosConvenio.isEmpty()) {
            fechaPeriodos.setTime(convenio.getDatefechaini());

            while (fechaPeriodos.compareTo(fechaFinConvenio) < 0) {
                periodoConvenio = new Relacioncontratoperiodoflujocaja();
                periodo = new Periodoflujocaja();

                periodoConvenio.setPeriodoflujocaja(periodo);
                periodoConvenio.setContrato(convenio);
                periodosConvenio.add(definirPeriodoConvenio(periodoConvenio, fechaPeriodos));

                fechaPeriodos.add(Calendar.MONTH, 1);
                fechaPeriodos = obtenerFechaDiaDelMes(fechaPeriodos, true);
            }
        } else if (periodosConvenio.size() < mesesEntreFechas(fechaInicioConvenio, fechaFinConvenio)) {
            fechaPeriodos.setTime(convenio.getDatefechaini());
            fechaPeriodos = actualizarPeriodosConvenio(fechaPeriodos);

            while (fechaPeriodos.compareTo(fechaFinConvenio) <= 0) {
                periodoConvenio = new Relacioncontratoperiodoflujocaja();
                periodo = new Periodoflujocaja();

                periodoConvenio.setPeriodoflujocaja(periodo);
                periodoConvenio.setContrato(convenio);
                periodosConvenio.add(definirPeriodoConvenio(periodoConvenio, fechaPeriodos));

                fechaPeriodos.add(Calendar.MONTH, 1);
                fechaPeriodos = obtenerFechaDiaDelMes(fechaPeriodos, true);
            }
        } else if (periodosConvenio.size() > mesesEntreFechas(fechaInicioConvenio, fechaFinConvenio)) {
            int meses = mesesEntreFechas(fechaInicioConvenio, fechaFinConvenio);
            fechaPeriodos.setTime(convenio.getDatefechaini());

            while (periodosConvenio.size() > meses) {
                periodoConvenio = periodosConvenio.remove(periodosConvenio.size() - 1);
                periodosConvenioEliminados.add(periodoConvenio);
            }

            actualizarPeriodosConvenio(fechaPeriodos);
        } else {
            fechaPeriodos.setTime(convenio.getDatefechaini());

            actualizarPeriodosConvenio(fechaPeriodos);
        }
    }

    /**
     * Definir periodo del flujo de caja. El método recibe un periodo genérico y
     * una fecha de inicio de periodo a aplicar. Si la fecha de inicio
     * referenciada es menor a la fecha de inicio del convenio, define la fecha
     * de inicio del periodo como la fecha de inicio del convenio. Si la fecha
     * de finalización del convenio es menor al último día del mes de la fecha
     * referenciada, define como la fecha de finalización del periodo la fecha
     * de finalización del contrato. Al final etiqueta el periodo de acuerdo con
     * el mes al que pertenece.
     *
     * @param periodoConvenio Periodo convenio a definir.
     * @param fechaPeriodos Fecha de inicio para aplicar al periodo.
     *
     * @return Periodo definido.
     */
    private Relacioncontratoperiodoflujocaja definirPeriodoConvenio(Relacioncontratoperiodoflujocaja periodoConvenio, Calendar fechaPeriodos) {
        if (fechaPeriodos.compareTo(fechaInicioConvenio) <= 0) {
            periodoConvenio.getPeriodoflujocaja().setFechainicio(convenio.getDatefechaini());
        } else {
            periodoConvenio.getPeriodoflujocaja().setFechainicio(fechaPeriodos.getTime());
        }

        fechaPeriodos = obtenerFechaDiaDelMes(fechaPeriodos, false);

        if (fechaPeriodos.compareTo(fechaFinConvenio) < 0) {
            periodoConvenio.getPeriodoflujocaja().setFechafin(fechaPeriodos.getTime());
        } else {
            periodoConvenio.getPeriodoflujocaja().setFechafin(convenio.getDatefechafin());
        }

        periodoConvenio.getPeriodoflujocaja().setStrdescripcion(etiquetarPeriodo(fechaPeriodos));

        return periodoConvenio;
    }

    /**
     * Actualizar los periodos establecidos del flujo de caja. Recorre los
     * periodos del flujo de caja del convenio establecidos y los actualiza a
     * partir de una fecha de inicio recibida.
     *
     * @param fechaPeriodos fecha de inicio de la actualización.
     * @return Fecha tipo Calendar del último periodo actualizado.
     */
    private Calendar actualizarPeriodosConvenio(Calendar fechaPeriodos) {
        for (Relacioncontratoperiodoflujocaja periodoConvenioActualizar : periodosConvenio) {
            definirPeriodoConvenio(periodoConvenioActualizar, fechaPeriodos);

            fechaPeriodos.add(Calendar.MONTH, 1);
            fechaPeriodos = obtenerFechaDiaDelMes(fechaPeriodos, true);
        }

        return fechaPeriodos;
    }

    /**
     * Obtener primer o último día del mes. De acuerdo con una fecha de
     * referencia.
     *
     * @param fechaReferencia Fecha de referencia para calcular el día.
     * @param primerDia Si es true devuelve el primer día del mes de la fecha de
     * referencia. Si es false devuelve el último día del mes.
     * @return Fecha del día del mes de acuerdo con lo solicitado.
     */
    private Calendar obtenerFechaDiaDelMes(Calendar fechaReferencia, boolean primerDia) {
        Calendar fecha = Calendar.getInstance();
        int dia;

        if (primerDia) {
            dia = fechaReferencia.getActualMinimum(Calendar.DAY_OF_MONTH);
        } else {
            dia = fechaReferencia.getActualMaximum(Calendar.DAY_OF_MONTH);
        }

        fecha.set(fechaReferencia.get(Calendar.YEAR),
                fechaReferencia.get(Calendar.MONTH),
                dia);

        return fecha;
    }

    /**
     * Etiquetar periodo del flujo de caja del convenio. Este método devuelve
     * una cadena que representa la descripción del periodo, de acuerdo con la
     * fecha a la que pertenece.
     *
     * @param fechaReferencia Fecha a la que pertenece el periodo.
     * @return Etiqueta con descripción del periodo de flujo de caja.
     */
    private String etiquetarPeriodo(Calendar fechaReferencia) {
        String etiquetaPeriodo;

        etiquetaPeriodo = (new SimpleDateFormat("MMM").format(fechaReferencia.getTime())) + "-" + fechaReferencia.get(Calendar.YEAR);

        return etiquetaPeriodo;
    }

    /**
     * Calcular el total de meses para periodos de flujo que hay entre dos
     * fechas. Devuelve la diferencias (mas uno) que hay entre las dos fechas.
     * Por ejemplo, si las fechas están dentro del mismo mes devuelve 1, si está
     * en dos mese consecutivos devuelve 2 y así sucesivamente para saber
     * cuántos periodos se deben generar, recorrer o actualizar.
     *
     * @param fechaInicial Fecha inicial para la diferencia.
     * @param fechaFinal Fecha final para la diferencia.
     * @return Entero con la cantidad de meses para generar periodos.
     */
    private int mesesEntreFechas(Calendar fechaInicial, Calendar fechaFinal) {
        int meses;

        meses = ((fechaFinal.get(Calendar.YEAR) * 12) + (fechaFinal.get(Calendar.MONTH) + 1))
                - ((fechaInicial.get(Calendar.YEAR) * 12) + (fechaInicial.get(Calendar.MONTH) + 1));

        return (meses + 1);
    }

    /**
     * Metodo ejecutado cada vez que se modifica el valor de la planificación de
     * un contrato del flujo de caja
     */
    public void calcularTotalesFlujoEgreso() {
        int iterador;
        iniciarTotalesEgresosPeriodo();
        totalEgresos = 0;

        for (FlujoEgresos flujoEgresosProyecto : flujoEgresos) {
            if (flujoEgresosProyecto.isEgresoProyecto()) {
                for (Planificacionmovimientoproyecto planificacionmovimientoproyecto : flujoEgresosProyecto.planMovimientosProyecto) {
                    planificacionmovimientoproyecto.setValor(BigDecimal.ZERO);
                }
            }
        }

        for (FlujoEgresos flujoEgresosRefrescar : flujoEgresos) {
            flujoEgresosRefrescar.calcularTotalEgresosFuente(periodosConvenio.size());
            totalEgresos += flujoEgresosRefrescar.getTotalEgresosFuente().doubleValue();

            iterador = 0;
            while (iterador < periodosConvenio.size()) {
                if (flujoEgresosRefrescar.isEgresoProyecto()) {
//                    totalEgresosPeriodo[iterador] += flujoEgresosRefrescar.getPlanMovimientosProyecto().get(iterador).getValor().doubleValue();
                } else if (flujoEgresosRefrescar.isEgresoOtros()) {
                    totalEgresosPeriodo[iterador] += flujoEgresosRefrescar.getPlanMovimientosEgresosConvenio().get(iterador).getValor().doubleValue();
                    System.out.println("totalEgresosPeriodo[" + iterador + "] = " + totalEgresosPeriodo[iterador]);
                } else if (flujoEgresosRefrescar.isEgresoContrato()) {
                    totalEgresosPeriodo[iterador] += flujoEgresosRefrescar.getPlanMovimientosContrato().get(iterador).getValor().doubleValue();
                    System.out.println("totalEgresosPeriodo[" + iterador + "] = " + totalEgresosPeriodo[iterador]);
                    Planificacionmovimientocontrato planificacionmovimientocontrato = flujoEgresosRefrescar.getPlanMovimientosContrato().get(iterador);
                    for (FlujoEgresos flujoEgresosProyecto : flujoEgresos) {
                        if (flujoEgresosProyecto.isEgresoProyecto()) {
                            if (flujoEgresosProyecto.planMovimientosProyecto.get(iterador).getIdplanificacionmovpry() == planificacionmovimientocontrato.getPlanificacionmovimientoproyecto().getIdplanificacionmovpry()) {
                                BigDecimal valorProyecto = flujoEgresosProyecto.planMovimientosProyecto.get(iterador).getValor();
                                flujoEgresosProyecto.planMovimientosProyecto.get(iterador).setValor(valorProyecto.add(planificacionmovimientocontrato.getValor()));
                            }
                        }
                    }
                } else if (flujoEgresosRefrescar.isEgresoPryDirecto()) {
                    totalEgresosPeriodo[iterador] += flujoEgresosRefrescar.getPlanMovimientosPryDirecto().get(iterador).getValor().doubleValue();
                    System.out.println("totalEgresosPeriodo[" + iterador + "] = " + totalEgresosPeriodo[iterador]);
                    Planificacionmovimientoprydirecto planificacionmovimientoprydirecto = flujoEgresosRefrescar.getPlanMovimientosPryDirecto().get(iterador);
                    for (FlujoEgresos flujoEgresosProyecto : flujoEgresos) {
                        if (flujoEgresosProyecto.isEgresoProyecto()) {
                            if (flujoEgresosProyecto.planMovimientosProyecto.get(iterador).getIdplanificacionmovpry() == planificacionmovimientoprydirecto.getPlanificacionmovimientoproyecto().getIdplanificacionmovpry()) {
                                BigDecimal valorProyecto = flujoEgresosProyecto.planMovimientosProyecto.get(iterador).getValor();
                                flujoEgresosProyecto.planMovimientosProyecto.get(iterador).setValor(valorProyecto.add(planificacionmovimientoprydirecto.getValor()));
                            }
                        }
                    }
                } else if (flujoEgresosRefrescar.isEgresoPryOtros()) {
                    totalEgresosPeriodo[iterador] += flujoEgresosRefrescar.getPlanMovimientosPryOtros().get(iterador).getValor().doubleValue();
                    Planificacionmovimientopryotros planificacionmovimientopryotros = flujoEgresosRefrescar.getPlanMovimientosPryOtros().get(iterador);
                    for (FlujoEgresos flujoEgresosProyecto : flujoEgresos) {
                        if (flujoEgresosProyecto.isEgresoProyecto()) {
                            if (flujoEgresosProyecto.planMovimientosProyecto.get(iterador).getIdplanificacionmovpry() == planificacionmovimientopryotros.getPlanificacionmovimientoproyecto().getIdplanificacionmovpry()) {
                                BigDecimal valorProyecto = flujoEgresosProyecto.planMovimientosProyecto.get(iterador).getValor();
                                flujoEgresosProyecto.planMovimientosProyecto.get(iterador).setValor(valorProyecto.add(planificacionmovimientopryotros.getValor()));
                            }
                        }
                    }
                } else if (flujoEgresosRefrescar.isEgresoCuotaGerencia()) {
                    totalEgresosPeriodo[iterador] += flujoEgresosRefrescar.getPlanificacionesmovcuotagerencia().get(iterador).getValor().doubleValue();
                    System.out.println("totalEgresosPeriodo[" + iterador + "] = " + totalEgresosPeriodo[iterador]);
                    Planificacionmovcuotagerencia planificacionmovcuotagerencia = flujoEgresosRefrescar.getPlanificacionesmovcuotagerencia().get(iterador);
                    for (FlujoEgresos flujoEgresosCuotaGerencia : flujoEgresos) {
                        if (flujoEgresosCuotaGerencia.isEgresoOtros() && flujoEgresosCuotaGerencia.getItemFlujoEgresos().getIditemflujocaja() == 6) {
                            BigDecimal valorCuotaGerencia = flujoEgresosCuotaGerencia.planMovimientosEgresosConvenio.get(iterador).getValor();
                            flujoEgresosCuotaGerencia.planMovimientosEgresosConvenio.get(iterador).setValor(valorCuotaGerencia.add(planificacionmovcuotagerencia.getValor()));
                        }
                    }
                }
                iterador++;
            }
        }

        calcularGMF();

        totalEgresosPeriodoAcumulativo[0] += totalEgresosPeriodo[0];
        iterador = 1;

        while (iterador < periodosConvenio.size()) {
            totalEgresosPeriodoAcumulativo[iterador] += (totalEgresosPeriodoAcumulativo[iterador - 1] + totalEgresosPeriodo[iterador]);

            iterador++;
        }
    }
}