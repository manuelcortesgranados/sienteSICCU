/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cobra.Supervisor;

import co.com.interkont.cobra.to.Actividadobra;
import co.com.interkont.cobra.to.Contrato;
import co.com.interkont.cobra.to.Desembolso;
import co.com.interkont.cobra.to.Fuenterecursosconvenio;
import co.com.interkont.cobra.to.Itemflujocaja;
import co.com.interkont.cobra.to.Obrafuenterecursosconvenios;
import co.com.interkont.cobra.to.Periodoflujocaja;
import co.com.interkont.cobra.to.Planificacionmovconvenio;
import co.com.interkont.cobra.to.Planificacionmovcuotagerencia;
import co.com.interkont.cobra.to.Planificacionmovimientocontrato;
import co.com.interkont.cobra.to.Planificacionmovimientoproyecto;
import co.com.interkont.cobra.to.Planificacionmovimientoprydirecto;
import co.com.interkont.cobra.to.Planificacionmovimientopryotros;
import co.com.interkont.cobra.to.Relacioncontratoperiodoflujocaja;
import co.com.interkont.cobra.to.Relacionobrafuenterecursoscontrato;
import co.com.interkont.cobra.to.Tercero;
import co.com.interkont.cobra.to.utilidades.Propiedad;
import cobra.PlanOperativo.FlujoEgresos;
import cobra.PlanOperativo.ProyectoPlanOperativo;
import cobra.SessionBeanCobra;
import cobra.graficos.ConjuntoDatosGrafico;
import cobra.graficos.DatoGrafico;
import cobra.graficos.EstiloGrafico;
import cobra.graficos.Grafico;
import cobra.graficos.GraficoSeries;
import cobra.graficos.GraficoSeriesAmCharts;
import com.google.common.collect.HashBiMap;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author maritzabell1
 */
public class GraficoEvolucionFinancieraConvenio implements Serializable {
    
    private GraficoSeries grafico = new GraficoSeriesAmCharts("graficoEvolucionFinancieraConvenio");

    private List<Relacioncontratoperiodoflujocaja> periodosConvenio;
    private Calendar fechaInicioConvenio;
    private Calendar fechaFinConvenio;
    private Contrato convenio;
    
    private List<FlujoEgresos> flujoEgresos;
    private double totalEgresos;
    private List<Planificacionmovimientoproyecto> planifmovimientoconvenioproyecto;
    private List<Planificacionmovconvenio> planifmovimientoconvenio;
    private List<Itemflujocaja> itemsFlujoEgresos;
    private List<ProyectoPlanOperativo> proyectosPlanOperativo = new ArrayList<ProyectoPlanOperativo>();
    private List<Planificacionmovcuotagerencia> planificacionmovcuotagerencia;
    private double totalEgresosPeriodo[];
    private double totalEgresosPeriodoAcumulativo[];
    private double acumuladoGMF[];
    private ResourceBundle bundle = getSessionBeanCobra().getBundle();
    private List<Actividadobra> actividadesObra;
    private TreeMap<Date, BigDecimal> fechasDesembolsos;
    private TreeMap<Date, Double> mapPlani = new TreeMap<Date, Double>();
    private List<Entry<Date,Double>> planificado;
    public GraficoSeries getGraficoEvolucionFinancieraConvenio() {
        return grafico;
    }

    public void setGraficoEvolucionFinancieraConvenio(GraficoSeries graficoEvolucionFinancieraConvenio) {
        this.grafico = graficoEvolucionFinancieraConvenio;
    }

    public GraficoEvolucionFinancieraConvenio() {
        graficar();
    }

    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }

    protected NuevoContratoBasico getNuevoContratoBasico() {
        return (NuevoContratoBasico) FacesUtils.getManagedBean("Supervisor$Contrato");
    }

    /**
     * Genera el grácico de evolución del proyecto estableciendo el código de
     * la gráfica en atributo codigoGrafico del objeto graficoEvolucionproyecto
     */
    private void graficar() {
        BigDecimal divisor = new BigDecimal(1000000);
        BigDecimal acumuladoVlrEjecutado = new BigDecimal(0);

        convenio = getNuevoContratoBasico().getContrato();
        cargarActividadesPlanOperativo();
        generarPeriodosFlujoCaja();
        crearEstructuraFlujoEgresosConContratos();
        iniciarTotalesEgresosPeriodo();
        calcularTotalesFlujoEgreso();
        
        
        ConjuntoDatosGrafico conjuntoDatosPlani = new ConjuntoDatosGrafico();
        conjuntoDatosPlani.setCodigo("plan");
        conjuntoDatosPlani.setEtiqueta(Propiedad.getValor("graEvuContratoPlani"));
        EstiloGrafico estiloDatosPlani = new EstiloGrafico();
        estiloDatosPlani.setColorSerie("#336699");
        conjuntoDatosPlani.setEstilo(estiloDatosPlani);

        
        if(totalEgresosPeriodoAcumulativo!= null){
            BigDecimal plan ;
            String valStr;
                    
            for(int i=0 ; i< totalEgresosPeriodoAcumulativo.length; i++){
                DatoGrafico datoPlani = new DatoGrafico();
                datoPlani.setValorX("" + periodosConvenio.get(i).getPeriodoflujocaja().getFechainicio().getTime());
                plan = new BigDecimal(totalEgresosPeriodoAcumulativo[i]);
                valStr = plan.divide(divisor).setScale(3, RoundingMode.HALF_UP).toPlainString();
                datoPlani.setValorY(valStr);
                StringBuffer stringEtiDatoPlaniActual = new StringBuffer();
                stringEtiDatoPlaniActual.append(Propiedad.getValor("graEvuContratoEtiDato")).append(" ").append(periodosConvenio.get(i).getPeriodoflujocaja().getFechainicio()).append(" ").append(Propiedad.getValor("graEvuContratoEtiDatoPlan")).append(valStr);
                datoPlani.setEtiqueta(stringEtiDatoPlaniActual.toString());
                conjuntoDatosPlani.getListaDatos().add(datoPlani);
            }
            getSessionBeanCobra().getCobraService().setPlanificadoConvenio(new ArrayList<Entry<Date, Double>>(mapPlani.entrySet()));
        }
        grafico.getConjuntosDatosGrafico().add(conjuntoDatosPlani);
        
        List<Desembolso> desembolsos = getSessionBeanCobra().getCobraService().encontrarDesembolsoxConvenio(getNuevoContratoBasico().getContrato());
        
        fechasDesembolsos = new TreeMap<Date, BigDecimal>();
        BigDecimal valoracum;
        for(Desembolso des : desembolsos){
            if(!fechasDesembolsos.containsKey(des.getFecha())){
                fechasDesembolsos.put(des.getFecha(), des.getValor());
            }else{
                valoracum = fechasDesembolsos.get(des.getFecha());
                fechasDesembolsos.remove(des.getFecha());
                valoracum.add(des.getValor());
                fechasDesembolsos.put(des.getFecha(), valoracum);
                
            }
        }
        
        
        
        ConjuntoDatosGrafico conjuntoDatosEje = new ConjuntoDatosGrafico();
        conjuntoDatosEje.setCodigo("eje");
        conjuntoDatosEje.setEtiqueta(Propiedad.getValor("graEvuContratoEjec"));
        EstiloGrafico estiloDatosEje = new EstiloGrafico();
        estiloDatosEje.setColorSerie("#FF0000");
        conjuntoDatosEje.setEstilo(estiloDatosEje);

        if (desembolsos != null) {
            Set<Map.Entry<Date, BigDecimal>> entrySet = fechasDesembolsos.entrySet();
            for (Iterator it = entrySet.iterator(); it.hasNext();) {
                Map.Entry<Date, BigDecimal> desembolso = (Map.Entry<Date, BigDecimal>) it.next();
                DatoGrafico datoEje = new DatoGrafico();
                datoEje.setValorX("" + desembolso.getKey().getTime());
                acumuladoVlrEjecutado = acumuladoVlrEjecutado.add(desembolso.getValue().divide(divisor));
                datoEje.setValorY(acumuladoVlrEjecutado.setScale(3, RoundingMode.HALF_UP).toPlainString());

                StringBuffer stringEtiDatoEjecutado = new StringBuffer();
                stringEtiDatoEjecutado.append(Propiedad.getValor("graEvuContratoEtiDato")).append(" ").append(desembolso.getKey()).append(" ").append(Propiedad.getValor("graEvuContratoEtiDatoPlan")).append(desembolso.getValue().divide(divisor).setScale(3, RoundingMode.HALF_UP).toPlainString());
                datoEje.setEtiqueta(stringEtiDatoEjecutado.toString());
                conjuntoDatosEje.getListaDatos().add(datoEje);
            }
        }
        grafico.getConjuntosDatosGrafico().add(conjuntoDatosEje);

        
        
        grafico.setTipoGrafico(GraficoSeries.TIPO_LINEAS);
        grafico.getEstilo().setVerLeyenda(true);
        grafico.getEstilo().setVerCursor(true);
        grafico.setTipoDatoEjeX(Grafico.FECHA);
        grafico.getEstilo().setUselinealegenda(true);
        grafico.getEstilo().setNumcolumnaslegenda("2");
        grafico.getEstilo().setZoomcursor(true);
        grafico.getEstilo().setColortextocursor(Propiedad.getValor("graevuproyetextocursor"));
        grafico.getEstilo().setColorfondoocursor(Propiedad.getValor("graevuproyecolorcursor"));
        grafico.getEstilo().setAnimado(true);
        grafico.getEstilo().setEvolucionproyectociudadano(true);
        grafico.setTituloEjeY(Propiedad.getValor("graEvuContratoTituloEjeYMillones"));
        
        
        grafico.generarGrafico();
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
        setPeriodosConvenio(new ArrayList<Relacioncontratoperiodoflujocaja>());
        Relacioncontratoperiodoflujocaja periodoConvenio;
        Periodoflujocaja periodo;
        Calendar fechaPeriodos = GregorianCalendar.getInstance();
        setFechaInicioConvenio(Calendar.getInstance());
        setFechaFinConvenio(Calendar.getInstance());

        getFechaInicioConvenio().setTime(getConvenio().getDatefechaini());
        getFechaFinConvenio().setTime(getConvenio().getDatefechafin());

        setPeriodosConvenio(getSessionBeanCobra().getCobraService().encontrarPeriodosConvenio(getConvenio().getIntidcontrato()));

        if (getPeriodosConvenio().isEmpty()) {
            fechaPeriodos.setTime(getConvenio().getDatefechaini());

            while (fechaPeriodos.compareTo(getFechaFinConvenio()) < 0) {
                periodoConvenio = new Relacioncontratoperiodoflujocaja();
                periodo = new Periodoflujocaja();

                periodoConvenio.setPeriodoflujocaja(periodo);
                periodoConvenio.setContrato(getConvenio());
                getPeriodosConvenio().add(definirPeriodoConvenio(periodoConvenio, fechaPeriodos));

                fechaPeriodos.add(Calendar.MONTH, 1);
                fechaPeriodos = obtenerFechaDiaDelMes(fechaPeriodos, true);
            }
        } else if (getPeriodosConvenio().size() < mesesEntreFechas(getFechaInicioConvenio(), getFechaFinConvenio())) {
            fechaPeriodos.setTime(getConvenio().getDatefechaini());
            fechaPeriodos = actualizarPeriodosConvenio(fechaPeriodos);

            while (fechaPeriodos.compareTo(getFechaFinConvenio()) <= 0) {
                periodoConvenio = new Relacioncontratoperiodoflujocaja();
                periodo = new Periodoflujocaja();

                periodoConvenio.setPeriodoflujocaja(periodo);
                periodoConvenio.setContrato(getConvenio());
                getPeriodosConvenio().add(definirPeriodoConvenio(periodoConvenio, fechaPeriodos));

                fechaPeriodos.add(Calendar.MONTH, 1);
                fechaPeriodos = obtenerFechaDiaDelMes(fechaPeriodos, true);
            }
        } else if (getPeriodosConvenio().size() > mesesEntreFechas(getFechaInicioConvenio(), getFechaFinConvenio())) {
            int meses = mesesEntreFechas(getFechaInicioConvenio(), getFechaFinConvenio());
            fechaPeriodos.setTime(getConvenio().getDatefechaini());
            actualizarPeriodosConvenio(fechaPeriodos);
        } else {
            fechaPeriodos.setTime(getConvenio().getDatefechaini());

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
        if (fechaPeriodos.compareTo(getFechaInicioConvenio()) <= 0) {
            periodoConvenio.getPeriodoflujocaja().setFechainicio(getConvenio().getDatefechaini());
        } else {
            periodoConvenio.getPeriodoflujocaja().setFechainicio(fechaPeriodos.getTime());
        }

        fechaPeriodos = obtenerFechaDiaDelMes(fechaPeriodos, false);

        if (fechaPeriodos.compareTo(getFechaFinConvenio()) < 0) {
            periodoConvenio.getPeriodoflujocaja().setFechafin(fechaPeriodos.getTime());
        } else {
            periodoConvenio.getPeriodoflujocaja().setFechafin(getConvenio().getDatefechafin());
        }

        periodoConvenio.getPeriodoflujocaja().setStrdescripcion(etiquetarPeriodo(fechaPeriodos));

        return periodoConvenio;
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
     * Actualizar los periodos establecidos del flujo de caja. Recorre los
     * periodos del flujo de caja del convenio establecidos y los actualiza a
     * partir de una fecha de inicio recibida.
     *
     * @param fechaPeriodos fecha de inicio de la actualización.
     * @return Fecha tipo Calendar del último periodo actualizado.
     */
    private Calendar actualizarPeriodosConvenio(Calendar fechaPeriodos) {
        for (Relacioncontratoperiodoflujocaja periodoConvenioActualizar : getPeriodosConvenio()) {
            definirPeriodoConvenio(periodoConvenioActualizar, fechaPeriodos);

            fechaPeriodos.add(Calendar.MONTH, 1);
            fechaPeriodos = obtenerFechaDiaDelMes(fechaPeriodos, true);
        }

        return fechaPeriodos;
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
     * Crear la estructura del flujo de egresos.
     *
     * De acuerdo con los proyectos definidos para el convenio y los items de
     * naturaleza egreso ('E'), se crea la estructura del flujo de egresos a
     * través de una lista FlujoEgresos.
     */
    public void crearEstructuraFlujoEgresosConContratos() {
        List<FlujoEgresos> flujoEgresosContratos;
        this.setFlujoEgresos(new ArrayList<FlujoEgresos>());
        this.setTotalEgresos(0);
        boolean guardarPlanificacionInicial = false;
        this.setPlanifmovimientoconvenioproyecto(new ArrayList<Planificacionmovimientoproyecto>());
        this.setPlanifmovimientoconvenio(new ArrayList<Planificacionmovconvenio>());

        setItemsFlujoEgresos(getSessionBeanCobra().getCobraService().itemsFlujoCajaPorNaturaleza("E"));

        for (ProyectoPlanOperativo proyectoPlanOperativo : getProyectosPlanOperativo()) {
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

            setPlanifmovimientoconvenioproyecto(getSessionBeanCobra().getCobraService().buscarPlanificacionConvenioProyecto(proyectoPlanOperativo.getProyecto().getIntcodigoobra()));
            //Si no se han cargado aún las planificaciones de proyecto 
            if (getPlanifmovimientoconvenioproyecto().isEmpty()) {
                guardarPlanificacionInicial = true;
                itemFlujoEgresosProyecto.crearEstructuraFlujoEgresosProyecto(proyectoPlanOperativo.getProyecto(), getPeriodosConvenio());
                getSessionBeanCobra().getCobraService().guardarFlujoEgresosProyecto(getPlanifmovimientoconvenioproyecto());
//                itemFlujoEgresosProyecto.calcularTotalEgresosFuente(periodosConvenio.size());
                //Se cargan los registros correspondientes a los contratos del proyecto
                for (Contrato contratoProyecto : proyectoPlanOperativo.getContratosProyecto()) {
                    FlujoEgresos itemFlujoEgresosContrato = new FlujoEgresos();
                    itemFlujoEgresosContrato.setEgresoContrato(true);
                    itemFlujoEgresosContrato.getItemFlujoEgresos().setStrdescripcion(contratoProyecto.getStrnombre());
                    itemFlujoEgresosContrato.setPlanMovimientosProyecto(itemFlujoEgresosProyecto.getPlanMovimientosProyecto());
                    itemFlujoEgresosContrato.crearEstructuraFlujoEgresosContrato(contratoProyecto, getPeriodosConvenio());
                    flujoEgresosContratos.add(itemFlujoEgresosContrato);
                }
                //Se carga el registro correspondiente a los pagos directos del proyecto
                itemFlujoEgresosPryDirecto.setPlanMovimientosProyecto(itemFlujoEgresosProyecto.getPlanMovimientosProyecto());
                itemFlujoEgresosPryDirecto.crearEstructuraFlujoEgresosPryDirecto(getPeriodosConvenio());

                //Se carga el registro correspondiente a los otros pagos del proyecto
                itemFlujoEgresosPryOtros.setPlanMovimientosProyecto(itemFlujoEgresosProyecto.getPlanMovimientosProyecto());
                itemFlujoEgresosPryOtros.crearEstructuraFlujoEgresosPryOtros(getPeriodosConvenio());
            } else {//Si ya se han establecido las planificaciones de movimientos del proyecto
                //Se cargan las  planificaciones de contrato, pagos directos y otros pagos para el proyecto
                for (Planificacionmovimientoproyecto planificacionmovimientoproyecto : getPlanifmovimientoconvenioproyecto()) {
                    planificacionmovimientoproyecto.setPlanificacionmovimientocontratos(new LinkedHashSet(getSessionBeanCobra().getCobraService().buscarPlanificacionConvenioContrato(planificacionmovimientoproyecto.getIdplanificacionmovpry())));
                    planificacionmovimientoproyecto.setPlanificacionmovimientoprydirectos(new LinkedHashSet(getSessionBeanCobra().getCobraService().buscarPlanificacionConvenioPrydirecto(planificacionmovimientoproyecto.getIdplanificacionmovpry())));
                    planificacionmovimientoproyecto.setPlanificacionmovimientopryotroses(new LinkedHashSet(getSessionBeanCobra().getCobraService().buscarPlanificacionConvenioPryotros(planificacionmovimientoproyecto.getIdplanificacionmovpry())));
                }
                for (Planificacionmovimientoproyecto planificacionmovimientoproyecto : getPlanifmovimientoconvenioproyecto()) {
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
                    } else {
                        for (Contrato contratoProyecto : proyectoPlanOperativo.getContratosProyecto()) {
                            boolean estaPlanificado = false;
                            for (Object object : planificacionmovimientoproyecto.getPlanificacionmovimientocontratos()) {
                                Planificacionmovimientocontrato planificacionmovimientocontrato = (Planificacionmovimientocontrato) object;
                                if(planificacionmovimientocontrato.getContrato().getIntidcontrato() == contratoProyecto.getIntidcontrato()) {
                                    estaPlanificado = true;
                                    break;
                                }
                            }
                            if(!estaPlanificado) {
                                Planificacionmovimientocontrato planMovimientoContrato = new Planificacionmovimientocontrato();
                                planMovimientoContrato.setContrato(contratoProyecto);
                                planMovimientoContrato.setPlanificacionmovimientoproyecto(planificacionmovimientoproyecto);
                                planMovimientoContrato.setValor(BigDecimal.ZERO);
                                planificacionmovimientoproyecto.getPlanificacionmovimientocontratos().add(planMovimientoContrato);
                            }
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
                for (Planificacionmovimientoproyecto planificacionmovimientoproyecto : getPlanifmovimientoconvenioproyecto()) {
                    //Se cargan los registros de planificaciones de contrato
                    for (Object object : planificacionmovimientoproyecto.getPlanificacionmovimientocontratos()) {
                        Planificacionmovimientocontrato planMovimientoContrato = (Planificacionmovimientocontrato) object;
                        FlujoEgresos itemFlujoEgresosContrato = null;
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
                itemFlujoEgresosProyecto.setPlanMovimientosProyecto(getPlanifmovimientoconvenioproyecto());
                itemFlujoEgresosProyecto.setProyecto(proyectoPlanOperativo.getProyecto());
                itemFlujoEgresosProyecto.actualizarPlanMovimientosProyecto(getPeriodosConvenio());
                itemFlujoEgresosProyecto.calcularTotalEgresosFuente(getPeriodosConvenio().size());
            }
            itemFlujoEgresosProyecto.actualizarPlanMovimientosProyecto(getPeriodosConvenio());
            for (FlujoEgresos fec : flujoEgresosContratos) {
                fec.actualizarPlanMovimientosContrato(getPeriodosConvenio(), fec.getContrato(), itemFlujoEgresosProyecto.getPlanMovimientosProyecto());
            }
            itemFlujoEgresosPryDirecto.actualizarPlanMovimientosPryDirecto(getPeriodosConvenio(), itemFlujoEgresosProyecto.getPlanMovimientosProyecto());
            itemFlujoEgresosPryOtros.actualizarPlanMovimientosPryOtros(getPeriodosConvenio(), itemFlujoEgresosProyecto.getPlanMovimientosProyecto());
            getFlujoEgresos().add(itemFlujoEgresosProyecto);
            getFlujoEgresos().addAll(flujoEgresosContratos);
            getFlujoEgresos().add(itemFlujoEgresosPryDirecto);
            getFlujoEgresos().add(itemFlujoEgresosPryOtros);
        }
        for (Itemflujocaja itemFlujoEgresos : getItemsFlujoEgresos()) {
            FlujoEgresos flujoEgresosEntidad = new FlujoEgresos();
            flujoEgresosEntidad.setEgresoOtros(true);

            setPlanifmovimientoconvenio(getSessionBeanCobra().getCobraService().buscarPlanificacionConvenio(itemFlujoEgresos.getIditemflujocaja(), getConvenio().getIntidcontrato()));

            if (getPlanifmovimientoconvenio().isEmpty()) {
                flujoEgresosEntidad.crearEstructuraFlujoEgresosOtrosItems(itemFlujoEgresos, getPeriodosConvenio(), getConvenio());
                flujoEgresosEntidad.calcularTotalEgresosFuente(getPeriodosConvenio().size());

            } else {
                flujoEgresosEntidad.setPlanMovimientosEgresosConvenio(getPlanifmovimientoconvenio());
                flujoEgresosEntidad.setItemFlujoEgresos(itemFlujoEgresos);
                flujoEgresosEntidad.actualizarPlanMovimientosEgresosConvenio(getPeriodosConvenio(), getConvenio());
                flujoEgresosEntidad.calcularTotalEgresosFuente(getPeriodosConvenio().size());

            }

            getFlujoEgresos().add(flujoEgresosEntidad);
            if (itemFlujoEgresos.getIditemflujocaja() == Itemflujocaja.ID_GERENCIA_CONVENIO) {
                for (Fuenterecursosconvenio fuenteRecursosConvenio : getNuevoContratoBasico().getRecursosconvenio().getLstFuentesRecursos()) {
                    FlujoEgresos flujoEgresosCuotaGerencia = new FlujoEgresos();
                    flujoEgresosCuotaGerencia.setEgresoCuotaGerencia(true);
                    flujoEgresosCuotaGerencia.setTotalEsperadoEgresosFuente(fuenteRecursosConvenio.getValorcuotagerencia());
                    Tercero entidadAportante = getSessionBeanCobra().getCobraService().encontrarTerceroPorId(fuenteRecursosConvenio.getTercero().getIntcodigo());
                    flujoEgresosCuotaGerencia.setItemFlujoEgresos(new Itemflujocaja());
                    flujoEgresosCuotaGerencia.getItemFlujoEgresos().setStrdescripcion(entidadAportante.getStrnombrecompleto());

                    setPlanificacionmovcuotagerencia(getSessionBeanCobra().getCobraService().buscarPlanificacionCuotaGerencia(fuenteRecursosConvenio.getIdfuenterecursosconvenio()));

                    if (getPlanificacionmovcuotagerencia().isEmpty()) {
                        flujoEgresosCuotaGerencia.crearEstructuraFlujoEgresosEntidad(fuenteRecursosConvenio, entidadAportante, getPeriodosConvenio());
                        flujoEgresosCuotaGerencia.calcularTotalEgresosFuente(getPeriodosConvenio().size());

                    } else {
                        flujoEgresosCuotaGerencia.setPlanificacionesmovcuotagerencia(getPlanificacionmovcuotagerencia());
                        flujoEgresosCuotaGerencia.setEntidadAportante(entidadAportante);
                        flujoEgresosCuotaGerencia.actualizarPlanMovimientosCuotaGerencia(getPeriodosConvenio(), fuenteRecursosConvenio);
                        flujoEgresosCuotaGerencia.calcularTotalEgresosFuente(getPeriodosConvenio().size());
                    }

                    getFlujoEgresos().add(flujoEgresosCuotaGerencia);
                }
            }
        }
    }
    
    /**
     * Inicializar los totales de egresos para los periodos.
     *
     * Inicializa los totales de egresos por periodo.
     */
    public void iniciarTotalesEgresosPeriodo() {
        setTotalEgresosPeriodo(new double[getPeriodosConvenio().size()]);
        setTotalEgresosPeriodoAcumulativo(new double[getPeriodosConvenio().size()]);

        int iterador = 0;

        while (iterador < getPeriodosConvenio().size()) {
            getTotalEgresosPeriodo()[iterador] = 0;

            iterador++;
        }
        
        //Se inicializa el consolidado de las cuotas de gerencia
        for (FlujoEgresos flujoEgresosCuotaGerencia : getFlujoEgresos()) {
            if (
                    flujoEgresosCuotaGerencia.isEgresoOtros() && ( 
                        flujoEgresosCuotaGerencia.getItemFlujoEgresos().getIditemflujocaja() == Itemflujocaja.ID_GERENCIA_CONVENIO
                        || flujoEgresosCuotaGerencia.getItemFlujoEgresos().getIditemflujocaja() == Itemflujocaja.ID_GMF
                    )
                    ){
                for (Planificacionmovconvenio planificacionmovconvenio : flujoEgresosCuotaGerencia.getPlanMovimientosEgresosConvenio()) {
                    planificacionmovconvenio.setValor(BigDecimal.ZERO);
                }
            }
        }
        
        iniciarAcumuladoGMF();
    }

    /**
     * Iniciar acumulado GMF. Inicializa los valores del arreglo que almacena
     * los valores acumulados por periodo para el cálculo del GMF.
     */
    private void iniciarAcumuladoGMF() {
        setAcumuladoGMF(new double[getPeriodosConvenio().size()]);

        int iterador = 0;

        while (iterador < getPeriodosConvenio().size()) {
            getAcumuladoGMF()[iterador] = 0;

            iterador++;
        }
    }
    /**
     * Metodo ejecutado cada vez que se modifica el valor de la planificación de
     * un contrato del flujo de caja
     */
    public void calcularTotalesFlujoEgreso() {
        int iterador;
        iniciarTotalesEgresosPeriodo();
        setTotalEgresos(0);

        for (FlujoEgresos flujoEgresosProyecto : getFlujoEgresos()) {
            if (flujoEgresosProyecto.isEgresoProyecto()) {
                for (Planificacionmovimientoproyecto planificacionmovimientoproyecto : flujoEgresosProyecto.getPlanMovimientosProyecto()) {
                    planificacionmovimientoproyecto.setValor(BigDecimal.ZERO);
                }
            }
        }

        for (FlujoEgresos flujoEgresosRefrescar : getFlujoEgresos()) {
            flujoEgresosRefrescar.calcularTotalEgresosFuente(getPeriodosConvenio().size());
            setTotalEgresos(getTotalEgresos() + flujoEgresosRefrescar.getTotalEgresosFuente().doubleValue());

            iterador = 0;
            while (iterador < getPeriodosConvenio().size()) {
                if (flujoEgresosRefrescar.isEgresoProyecto()) {
//                    totalEgresosPeriodo[iterador] += flujoEgresosRefrescar.getPlanMovimientosProyecto().get(iterador).getValor().doubleValue();
                    /**
                     * Se adiciona al total del periodo el valor de las planificaciones de: 
                     * "Pagos estimados con cargo directo al convenio" (3)
                     * "Otros egresos" (5)
                     */
                } else if (flujoEgresosRefrescar.isEgresoOtros() && (flujoEgresosRefrescar.getItemFlujoEgresos().getIditemflujocaja() == Itemflujocaja.ID_PAGOS_CARGO_DIRECTO_CONVENIO || flujoEgresosRefrescar.getItemFlujoEgresos().getIditemflujocaja() == Itemflujocaja.ID_OTROS_EGRESOS)) {
                    getTotalEgresosPeriodo()[iterador] += flujoEgresosRefrescar.getPlanMovimientosEgresosConvenio().get(iterador).getValor().doubleValue();
                } else if (flujoEgresosRefrescar.isEgresoContrato()) {
                    getTotalEgresosPeriodo()[iterador] += flujoEgresosRefrescar.getPlanMovimientosContrato().get(iterador).getValor().doubleValue();
                    Planificacionmovimientocontrato planificacionmovimientocontrato = flujoEgresosRefrescar.getPlanMovimientosContrato().get(iterador);
                    for (FlujoEgresos flujoEgresosProyecto : getFlujoEgresos()) {
                        if (flujoEgresosProyecto.isEgresoProyecto()) {
                            if (flujoEgresosProyecto.getPlanMovimientosProyecto().get(iterador).getIdplanificacionmovpry() == planificacionmovimientocontrato.getPlanificacionmovimientoproyecto().getIdplanificacionmovpry()) {
                                BigDecimal valorProyecto = flujoEgresosProyecto.getPlanMovimientosProyecto().get(iterador).getValor();
                                flujoEgresosProyecto.getPlanMovimientosProyecto().get(iterador).setValor(valorProyecto.add(planificacionmovimientocontrato.getValor()));
                            }
                        }
                    }
                } else if (flujoEgresosRefrescar.isEgresoPryDirecto()) {
                    getTotalEgresosPeriodo()[iterador] += flujoEgresosRefrescar.getPlanMovimientosPryDirecto().get(iterador).getValor().doubleValue();
                    Planificacionmovimientoprydirecto planificacionmovimientoprydirecto = flujoEgresosRefrescar.getPlanMovimientosPryDirecto().get(iterador);
                    for (FlujoEgresos flujoEgresosProyecto : getFlujoEgresos()) {
                        if (flujoEgresosProyecto.isEgresoProyecto()) {
                            if (flujoEgresosProyecto.getPlanMovimientosProyecto().get(iterador).getIdplanificacionmovpry() == planificacionmovimientoprydirecto.getPlanificacionmovimientoproyecto().getIdplanificacionmovpry()) {
                                BigDecimal valorProyecto = flujoEgresosProyecto.getPlanMovimientosProyecto().get(iterador).getValor();
                                flujoEgresosProyecto.getPlanMovimientosProyecto().get(iterador).setValor(valorProyecto.add(planificacionmovimientoprydirecto.getValor()));
                            }
                        }
                    }
                } else if (flujoEgresosRefrescar.isEgresoPryOtros()) {
                    getTotalEgresosPeriodo()[iterador] += flujoEgresosRefrescar.getPlanMovimientosPryOtros().get(iterador).getValor().doubleValue();
                    Planificacionmovimientopryotros planificacionmovimientopryotros = flujoEgresosRefrescar.getPlanMovimientosPryOtros().get(iterador);
                    for (FlujoEgresos flujoEgresosProyecto : getFlujoEgresos()) {
                        if (flujoEgresosProyecto.isEgresoProyecto()) {
                            if (flujoEgresosProyecto.getPlanMovimientosProyecto().get(iterador).getIdplanificacionmovpry() == planificacionmovimientopryotros.getPlanificacionmovimientoproyecto().getIdplanificacionmovpry()) {
                                BigDecimal valorProyecto = flujoEgresosProyecto.getPlanMovimientosProyecto().get(iterador).getValor();
                                flujoEgresosProyecto.getPlanMovimientosProyecto().get(iterador).setValor(valorProyecto.add(planificacionmovimientopryotros.getValor()));
                            }
                        }
                    }
                } else if (flujoEgresosRefrescar.isEgresoCuotaGerencia()) {
                    getTotalEgresosPeriodo()[iterador] += flujoEgresosRefrescar.getPlanificacionesmovcuotagerencia().get(iterador).getValor().doubleValue();
                    Planificacionmovcuotagerencia planificacionmovcuotagerencia = flujoEgresosRefrescar.getPlanificacionesmovcuotagerencia().get(iterador);
                    for (FlujoEgresos flujoEgresosCuotaGerencia : getFlujoEgresos()) {
                        if (flujoEgresosCuotaGerencia.isEgresoOtros() && flujoEgresosCuotaGerencia.getItemFlujoEgresos().getIditemflujocaja() == Itemflujocaja.ID_GERENCIA_CONVENIO) {
                            BigDecimal valorCuotaGerencia = flujoEgresosCuotaGerencia.getPlanMovimientosEgresosConvenio().get(iterador).getValor();
                            flujoEgresosCuotaGerencia.getPlanMovimientosEgresosConvenio().get(iterador).setValor(valorCuotaGerencia.add(planificacionmovcuotagerencia.getValor()));
                        }
                    }
                }
                iterador++;
            }
        }

        calcularGMF();

        getTotalEgresosPeriodoAcumulativo()[0] += getTotalEgresosPeriodo()[0];
        getMapPlani().put(periodosConvenio.get(0).getPeriodoflujocaja().getFechainicio(), getTotalEgresosPeriodo()[0]);
        iterador = 1;

        while (iterador < getPeriodosConvenio().size()) {
            getTotalEgresosPeriodoAcumulativo()[iterador] += (getTotalEgresosPeriodoAcumulativo()[iterador - 1] + getTotalEgresosPeriodo()[iterador]);
            getMapPlani().put(periodosConvenio.get(iterador).getPeriodoflujocaja().getFechainicio(), getTotalEgresosPeriodo()[iterador]);
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
        double divisorGMF = Double.valueOf(getBundle().getString("divisorGMF"));
        double multiplicadorGMF = Double.valueOf(getBundle().getString("multiplicadorGMF"));
        int iterador;

        iniciarAcumuladoGMF();

        iterador = 0;

        while (iterador < getPeriodosConvenio().size()) {
            for (FlujoEgresos flujoEgresosRecorrer : getFlujoEgresos()) {
                if (flujoEgresosRecorrer.isEgresoProyecto()) {
                    getAcumuladoGMF()[iterador] += flujoEgresosRecorrer.getPlanMovimientosProyecto().get(iterador).getValor().doubleValue();
                } else if (flujoEgresosRecorrer.isEgresoOtros()) {
                    if (flujoEgresosRecorrer.getItemFlujoEgresos().getBooltienegmf().booleanValue()) {
                        getAcumuladoGMF()[iterador] += flujoEgresosRecorrer.getPlanMovimientosEgresosConvenio().get(iterador).getValor().doubleValue();
                    }
                }
            }
            iterador++;
        }

        for (FlujoEgresos flujoEgresosRecorrer : getFlujoEgresos()) {
            if (flujoEgresosRecorrer.isEgresoOtros()) {
                if (flujoEgresosRecorrer.getItemFlujoEgresos().getStrdescripcion().contains("GMF")) {
                    iterador = 0;

                    while (iterador < getPeriodosConvenio().size()) {
                        double valorGMFPeriodo = getAcumuladoGMF()[iterador] / divisorGMF * multiplicadorGMF;

                        flujoEgresosRecorrer.getPlanMovimientosEgresosConvenio().get(iterador).setValor(BigDecimal.valueOf(valorGMFPeriodo));
                        getTotalEgresosPeriodo()[iterador] += valorGMFPeriodo;
                        setTotalEgresos(getTotalEgresos() + valorGMFPeriodo);

                        iterador++;
                    }
                }

                flujoEgresosRecorrer.calcularTotalEgresosFuente(getPeriodosConvenio().size());
            }
        }

    }
    
    public void cargarActividadesPlanOperativo() {

        actividadesObra = new ArrayList<Actividadobra>();
        Actividadobra activiprincipal = getSessionBeanCobra().getCobraService().obtenerEstructuraActividadObraPlanOperativo(convenio.getIntidcontrato());
        if (activiprincipal != null) {
            cargarActividadesConsultadas(activiprincipal);
        }
    }
    
    /*
     *metodo que se encarga de asignar la numeracion a las actividades
     *cuando el convenio se consulta.
     * 
     */
    public void cargarActividadesConsultadas(Actividadobra actividaObraPrincipal) {
        actividadesObra.add(actividaObraPrincipal);
        //Extrae las obras para poder instanciar y validar el flujo de caja
        if (actividaObraPrincipal.getObra() != null) {
            ProyectoPlanOperativo proyplaop = new ProyectoPlanOperativo();
            proyplaop.setProyecto(actividaObraPrincipal.getObra());
            proyplaop.setContratosProyecto(new ArrayList<Contrato>());
            for (Obrafuenterecursosconvenios ofrc : actividaObraPrincipal.getObra().getObrafuenterecursosconvenioses()) {
                for (Relacionobrafuenterecursoscontrato robrc : getSessionBeanCobra().getCobraService().buscarRelacionobrafuenterecursoscontrato(ofrc.getIdobrafuenterecursos())) {
                    boolean estaEnContratosProyecto = false;
                    for (Contrato cont : proyplaop.getContratosProyecto()) {
                        if (cont.getIntidcontrato() == robrc.getContrato().getIntidcontrato()) {
                            estaEnContratosProyecto = true;
                        }
                    }
                    if (!estaEnContratosProyecto) {
                        proyplaop.getContratosProyecto().add(robrc.getContrato());
                    }
                }
            }
            getProyectosPlanOperativo().add(proyplaop);

        }
        if (!actividaObraPrincipal.getActividadobras().isEmpty()) {
            for (Iterator it = actividaObraPrincipal.getActividadobras().iterator(); it.hasNext();) {
                Actividadobra actividadHija = (Actividadobra) it.next();
                cargarActividadesConsultadas(actividadHija);
            }

        }
    }

    /**
     * @return the periodosConvenio
     */
    public List<Relacioncontratoperiodoflujocaja> getPeriodosConvenio() {
        return periodosConvenio;
    }

    /**
     * @param periodosConvenio the periodosConvenio to set
     */
    public void setPeriodosConvenio(List<Relacioncontratoperiodoflujocaja> periodosConvenio) {
        this.periodosConvenio = periodosConvenio;
    }

    /**
     * @return the fechaInicioConvenio
     */
    public Calendar getFechaInicioConvenio() {
        return fechaInicioConvenio;
    }

    /**
     * @param fechaInicioConvenio the fechaInicioConvenio to set
     */
    public void setFechaInicioConvenio(Calendar fechaInicioConvenio) {
        this.fechaInicioConvenio = fechaInicioConvenio;
    }

    /**
     * @return the fechaFinConvenio
     */
    public Calendar getFechaFinConvenio() {
        return fechaFinConvenio;
    }

    /**
     * @param fechaFinConvenio the fechaFinConvenio to set
     */
    public void setFechaFinConvenio(Calendar fechaFinConvenio) {
        this.fechaFinConvenio = fechaFinConvenio;
    }

    /**
     * @return the convenio
     */
    public Contrato getConvenio() {
        return convenio;
    }

    /**
     * @param convenio the convenio to set
     */
    public void setConvenio(Contrato convenio) {
        this.convenio = convenio;
    }

    /**
     * @return the flujoEgresos
     */
    public List<FlujoEgresos> getFlujoEgresos() {
        return flujoEgresos;
    }

    /**
     * @param flujoEgresos the flujoEgresos to set
     */
    public void setFlujoEgresos(List<FlujoEgresos> flujoEgresos) {
        this.flujoEgresos = flujoEgresos;
    }

    /**
     * @return the totalEgresos
     */
    public double getTotalEgresos() {
        return totalEgresos;
    }

    /**
     * @param totalEgresos the totalEgresos to set
     */
    public void setTotalEgresos(double totalEgresos) {
        this.totalEgresos = totalEgresos;
    }

    /**
     * @return the planifmovimientoconvenioproyecto
     */
    public List<Planificacionmovimientoproyecto> getPlanifmovimientoconvenioproyecto() {
        return planifmovimientoconvenioproyecto;
    }

    /**
     * @param planifmovimientoconvenioproyecto the planifmovimientoconvenioproyecto to set
     */
    public void setPlanifmovimientoconvenioproyecto(List<Planificacionmovimientoproyecto> planifmovimientoconvenioproyecto) {
        this.planifmovimientoconvenioproyecto = planifmovimientoconvenioproyecto;
    }

    /**
     * @return the planifmovimientoconvenio
     */
    public List<Planificacionmovconvenio> getPlanifmovimientoconvenio() {
        return planifmovimientoconvenio;
    }

    /**
     * @param planifmovimientoconvenio the planifmovimientoconvenio to set
     */
    public void setPlanifmovimientoconvenio(List<Planificacionmovconvenio> planifmovimientoconvenio) {
        this.planifmovimientoconvenio = planifmovimientoconvenio;
    }

    /**
     * @return the itemsFlujoEgresos
     */
    public List<Itemflujocaja> getItemsFlujoEgresos() {
        return itemsFlujoEgresos;
    }

    /**
     * @param itemsFlujoEgresos the itemsFlujoEgresos to set
     */
    public void setItemsFlujoEgresos(List<Itemflujocaja> itemsFlujoEgresos) {
        this.itemsFlujoEgresos = itemsFlujoEgresos;
    }

    /**
     * @return the proyectosPlanOperativo
     */
    public List<ProyectoPlanOperativo> getProyectosPlanOperativo() {
        return proyectosPlanOperativo;
    }

    /**
     * @param proyectosPlanOperativo the proyectosPlanOperativo to set
     */
    public void setProyectosPlanOperativo(List<ProyectoPlanOperativo> proyectosPlanOperativo) {
        this.proyectosPlanOperativo = proyectosPlanOperativo;
    }

    /**
     * @return the planificacionmovcuotagerencia
     */
    public List<Planificacionmovcuotagerencia> getPlanificacionmovcuotagerencia() {
        return planificacionmovcuotagerencia;
    }

    /**
     * @param planificacionmovcuotagerencia the planificacionmovcuotagerencia to set
     */
    public void setPlanificacionmovcuotagerencia(List<Planificacionmovcuotagerencia> planificacionmovcuotagerencia) {
        this.planificacionmovcuotagerencia = planificacionmovcuotagerencia;
    }

    /**
     * @return the totalEgresosPeriodo
     */
    public double[] getTotalEgresosPeriodo() {
        return totalEgresosPeriodo;
    }

    /**
     * @param totalEgresosPeriodo the totalEgresosPeriodo to set
     */
    public void setTotalEgresosPeriodo(double[] totalEgresosPeriodo) {
        this.totalEgresosPeriodo = totalEgresosPeriodo;
    }

    /**
     * @return the totalEgresosPeriodoAcumulativo
     */
    public double[] getTotalEgresosPeriodoAcumulativo() {
        return totalEgresosPeriodoAcumulativo;
    }

    /**
     * @param totalEgresosPeriodoAcumulativo the totalEgresosPeriodoAcumulativo to set
     */
    public void setTotalEgresosPeriodoAcumulativo(double[] totalEgresosPeriodoAcumulativo) {
        this.totalEgresosPeriodoAcumulativo = totalEgresosPeriodoAcumulativo;
    }

    /**
     * @return the acumuladoGMF
     */
    public double[] getAcumuladoGMF() {
        return acumuladoGMF;
    }

    /**
     * @param acumuladoGMF the acumuladoGMF to set
     */
    public void setAcumuladoGMF(double[] acumuladoGMF) {
        this.acumuladoGMF = acumuladoGMF;
    }

    /**
     * @return the bundle
     */
    public ResourceBundle getBundle() {
        return bundle;
    }

    /**
     * @param bundle the bundle to set
     */
    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    /**
     * @return the mapPlani
     */
    public TreeMap<Date, Double> getMapPlani() {
        return mapPlani;
    }

    /**
     * @param mapPlani the mapPlani to set
     */
    public void setMapPlani(TreeMap<Date, Double> mapPlani) {
        this.mapPlani = mapPlani;
    }

    /**
     * @return the planificado
     */
    public List<Entry<Date,Double>> getPlanificado() {
        return planificado;
    }

    /**
     * @param planificado the planificado to set
     */
    public void setPlanificado(List<Entry<Date,Double>> planificado) {
        this.planificado = planificado;
    }
}
