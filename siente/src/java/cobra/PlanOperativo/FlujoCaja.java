package cobra.PlanOperativo;

import co.com.interkont.cobra.to.Contrato;
import co.com.interkont.cobra.to.Fuenterecursosconvenio;
import co.com.interkont.cobra.to.Itemflujocaja;
import co.com.interkont.cobra.to.Obra;
import co.com.interkont.cobra.to.Periodoflujocaja;
import co.com.interkont.cobra.to.Planificacionmovconvenio;
import co.com.interkont.cobra.to.Planificacionmovconvenioentidad;
import co.com.interkont.cobra.to.Planificacionmovimientoproyecto;
import co.com.interkont.cobra.to.Relacioncontratoperiodoflujocaja;
import co.com.interkont.cobra.to.Tercero;
import cobra.SessionBeanCobra;
import cobra.Supervisor.FacesUtils;
import cobra.Supervisor.NuevoContratoBasico;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;

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
     * @return Cadena con el nombre de la página del flujo.
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
        crearEstructuraFlujoEgresos();
        iniciarTotalesEgresosPeriodo();
        refrescarTotalesIngresos();
        refrescarTotalesEgresos();
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

        for (Obra proyectoConvenio : nuevoContratoBasico.getListaProyectosConvenio()) {
            FlujoEgresos itemFlujoEgresos = new FlujoEgresos();


            if (planifmovimientoconvenioproyecto.isEmpty()) {
                itemFlujoEgresos.crearEstructuraFlujoEgresosProyecto(proyectoConvenio, periodosConvenio);
                itemFlujoEgresos.calcularTotalEgresosFuente(periodosConvenio.size());
            } else {
                itemFlujoEgresos.setPlanMovimientosProyecto(planifmovimientoconvenioproyecto);
                itemFlujoEgresos.setProyecto(proyectoConvenio);
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
     * Inicializar los totales de egresos para los periodos.
     *
     * Inicializa los totales de egresos por periodo.
     */
    public void iniciarTotalesEgresosPeriodo() {
        totalEgresosPeriodo = new double[periodosConvenio.size()];
        totalEgresosPeriodoAcumulativo = new double[periodosConvenio.size()];
        acumuladoGMF = new double[periodosConvenio.size()];

        int iterador = 0;

        while (iterador < periodosConvenio.size()) {
            totalEgresosPeriodo[iterador] = 0;
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
                } else {
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

        for (FlujoEgresos flujoEgresosRecorrer : flujoEgresos) {
            if (!flujoEgresosRecorrer.isEgresoProyecto()) {
                if (flujoEgresosRecorrer.itemFlujoEgresos.getStrdescripcion().contains("GMF")) {
                    iterador = 0;

                    while (iterador < periodosConvenio.size()) {
                        double valorGMFPeriodo = acumuladoGMF[iterador] / divisorGMF * multiplicadorGMF;

                        totalEgresosPeriodo[iterador] -= flujoEgresosRecorrer.planMovimientosEgresosConvenio.get(iterador).getValor().doubleValue();
                        totalEgresos -= flujoEgresosRecorrer.planMovimientosEgresosConvenio.get(iterador).getValor().doubleValue();

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
                }
            }
        }

        for (FlujoEgresos flujoEgresosValidar : flujoEgresos) {
            if (flujoEgresosValidar.egresoProyecto) {
                if (flujoEgresosValidar.totalEgresosFuente.doubleValue() > flujoEgresosValidar.getProyecto().getNumvaltotobra().doubleValue()) {
                    cumpleRequisitos = false;

                    FacesUtils.addErrorMessage(bundle.getString("errorEgresosMayorAValorProyecto"));
                }
            }
        }

        if (this.totalEgresos > this.totalIngresos) {
            cumpleRequisitos = false;

            FacesUtils.addErrorMessage(bundle.getString("errorTotalEgresosMayorATotalIngresos"));
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

        if (validarFlujoCaja()) {
            for (FlujoIngresos flujoIngresosGuardar : flujoIngresos) {
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
                if (flujoEgresosGuardar.egresoProyecto) {
                    getSessionBeanCobra().getCobraService().guardarFlujoEgresosProyecto(flujoEgresosGuardar.planMovimientosProyecto);

                    if (flujoEgresosGuardar.movimientosProyectoEliminados.size() > 0) {
                        getSessionBeanCobra().getCobraService().borrarEgresosProyecto(flujoEgresosGuardar.movimientosProyectoEliminados);
                    }
                } else {
                    getSessionBeanCobra().getCobraService().guardarFlujoConvenioOtrosConceptos(flujoEgresosGuardar.planMovimientosEgresosConvenio);

                    if (flujoEgresosGuardar.movimientosEgresosConvenioEliminados.size() > 0) {
                        getSessionBeanCobra().getCobraService().borrarMovimientosConvenio(flujoEgresosGuardar.movimientosEgresosConvenioEliminados);
                    }
                }
            }
        }
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

        etiquetaPeriodo = (fechaReferencia.get(Calendar.MONTH) + 1) + " / " + fechaReferencia.get(Calendar.YEAR);

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
}