/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.MarcoLogico;

import co.com.interkont.cobra.marcologico.to.Avanceplanificacionrelacionmarcologicoindicador;
import co.com.interkont.cobra.marcologico.to.Cronogramaobligacionesejecutada;
import co.com.interkont.cobra.marcologico.to.Indicador;
import co.com.interkont.cobra.marcologico.to.Marcologico;
import co.com.interkont.cobra.marcologico.to.Obligacion;
import co.com.interkont.cobra.marcologico.to.Planificacionrelacionmarcologicoindicador;
import co.com.interkont.cobra.marcologico.to.Relacionmarcologicoindicador;
import co.com.interkont.cobra.to.Obra;
import co.com.interkont.cobra.to.utilidades.Propiedad;
import cobra.SessionBeanCobra;
import cobra.Supervisor.AdministrarObraNew;
import cobra.Supervisor.FacesUtils;
import cobra.Supervisor.NuevoContratoBasico;
//import com.sun.rave.web.ui.appbase.AbstractPageBean;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import org.richfaces.component.UIDataTable;


/**
 *
 * @author desarrollo9
 */
public class MarcoLogicoBean implements Serializable{

    public MarcoLogicoBean() {
        
    }

    
    //Instancia de la clase Obligacion
    Obligacion obligacion = new Obligacion();
    Cronogramaobligacionesejecutada cronogramaobligacionesejecutada = new Cronogramaobligacionesejecutada();
    Relacionmarcologicoindicador relacionmarcologicoindicador = new Relacionmarcologicoindicador();
    Marcologico marcologico = new Marcologico();
    Indicador indicador = new Indicador();
    Planificacionrelacionmarcologicoindicador planificacionrelacionmarcologicoindicador = new Planificacionrelacionmarcologicoindicador();
    Avanceplanificacionrelacionmarcologicoindicador avanceplanificacionrelacionmarcologicoindicador = new Avanceplanificacionrelacionmarcologicoindicador();
    //Listado de obligaciones convenio
    private List<Obligacion> listaObligaciones = new ArrayList<Obligacion>();
    private List<Cronogramaobligacionesejecutada> listaObligacionavance = new ArrayList<Cronogramaobligacionesejecutada>();
    private List<Marcologico> listaMarcoLogico = new ArrayList<Marcologico>();
    private List<Marcologico> listaMarcoLogicoAsociado = new ArrayList<Marcologico>();
    private List<Indicador> listaIndicador = new ArrayList<Indicador>();
    //private List<Indicador> listaIndicadorAsociado = new ArrayList<Indicador>();
    private List<Relacionmarcologicoindicador> listarelacionMarcolIndicador = new ArrayList<Relacionmarcologicoindicador>();
    private List<Relacionmarcologicoindicador> listMarcolInidicadorRelacionados = new ArrayList<Relacionmarcologicoindicador>();
    private List<Relacionmarcologicoindicador> listaMarcolIndicadorcomponente = new ArrayList<Relacionmarcologicoindicador>();
    private List<Relacionmarcologicoindicador> listaMarcolIndicadoractividad = new ArrayList<Relacionmarcologicoindicador>();
    private List<Avanceplanificacionrelacionmarcologicoindicador> listaavanceplanificacionRelacionMarcolIndicador = new ArrayList<Avanceplanificacionrelacionmarcologicoindicador>();
    private List<Planificacionrelacionmarcologicoindicador> listaplanificacionRelacionMarcolIndicador = new ArrayList<Planificacionrelacionmarcologicoindicador>();
    private List<Object> listarelacionobjeto = new ArrayList<Object>();
    private UIDataTable tablaObligacionbin = new UIDataTable();
    private UIDataTable tablaCronogAvancebin = new UIDataTable();
    private UIDataTable tablaIndicadorbin = new UIDataTable();
    private UIDataTable tablaMarcoLogicoPropositobin = new UIDataTable();
    private UIDataTable tablaMarcoLogicobin = new UIDataTable();
    private UIDataTable tablaRelacionMarcolIndicadorbin = new UIDataTable();
    private UIDataTable tablaRelacionMarcolIndicadorComponentebin = new UIDataTable();
    private UIDataTable tablaRelacionMarcolIndicadorActividadbin = new UIDataTable();
    private UIDataTable tablaPlanificacionRelacionMarcolIndicadorbin = new UIDataTable();
    private UIDataTable tablaAvancePlanificacionRelacionMarcolIndicadorbin = new UIDataTable();
    private boolean boolcrearobligacion = false;
    private int intmarco;
    private int intIndicador;
    private int fechareal;
    private int fechaanterior;
    private int indextable;
    private double porcentajePlanificacion;
    private double porcentajeAvancePlanificacion;
    private boolean vermodal;
    private int totalcronogobligacion = 0;
    private boolean guardoavance = false;
    private boolean habilitarguardaravance = false;
    private boolean habilitarguardaravancePlanificacion = false;
    private boolean habilitarfechainicialestimada = false;
    private boolean habilitarfechafinestimada = false;
    private boolean habilitarfechainicioreal = false;
    private boolean habilitarfechafinreal = false;
    private Indicador indicadorAsociado = new Indicador();
    private Date fechafinobligaciontem;
    private boolean estadoObligacion;

    public Indicador getIndicadorAsociado() {
        return indicadorAsociado;
    }

    public void setIndicadorAsociado(Indicador indicadorAsociado) {
        this.indicadorAsociado = indicadorAsociado;
    }

    public boolean isHabilitarfechainicialestimada() {
        return habilitarfechainicialestimada;
    }

    public void setHabilitarfechainicialestimada(boolean habilitarfechainicialestimada) {
        this.habilitarfechainicialestimada = habilitarfechainicialestimada;
    }

    public boolean isHabilitarfechafinestimada() {
        return habilitarfechafinestimada;
    }

    public void setHabilitarfechafinestimada(boolean habilitarfechafinestimada) {
        this.habilitarfechafinestimada = habilitarfechafinestimada;
    }

    public boolean isHabilitarfechainicioreal() {
        return habilitarfechainicioreal;
    }

    public void setHabilitarfechainicioreal(boolean habilitarfechainicioreal) {
        this.habilitarfechainicioreal = habilitarfechainicioreal;
    }

    public boolean isHabilitarfechafinreal() {
        return habilitarfechafinreal;
    }

    public void setHabilitarfechafinreal(boolean habilitarfechafinreal) {
        this.habilitarfechafinreal = habilitarfechafinreal;
    }

    public boolean isHabilitarguardaravance() {
        return habilitarguardaravance;
    }

    public void setHabilitarguardaravance(boolean habilitarguardaravance) {
        this.habilitarguardaravance = habilitarguardaravance;
    }

    public boolean isHabilitarguardaravancePlanificacion() {
        return habilitarguardaravancePlanificacion;
    }

    public void setHabilitarguardaravancePlanificacion(boolean habilitarguardaravancePlanificacion) {
        this.habilitarguardaravancePlanificacion = habilitarguardaravancePlanificacion;
    }

    public boolean isGuardoavance() {
        return guardoavance;
    }

    public void setGuardoavance(boolean guardoavance) {
        this.guardoavance = guardoavance;
    }

    public int getTotalcronogobligacion() {
        return totalcronogobligacion;
    }

    public void setTotalcronogobligacion(int totalcronogobligacion) {
        this.totalcronogobligacion = totalcronogobligacion;
    }

    /**
     * Instancia del bean SessionBeanCobra
     *
     * @return
     */
    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }

    /**
     * Instancia del bean NuevoContratoBasico
     *
     * @return
     */
    protected NuevoContratoBasico getNuevoContratoBasico() {
        return (NuevoContratoBasico)FacesUtils.getManagedBean("Supervisor$Contrato");
    }

    protected AdministrarObraNew getAdministrarObraNew() {
        return (AdministrarObraNew) FacesUtils.getManagedBean("Supervisor$AdministrarObraNew");
    }
    private boolean boolModuloSeguimientoConvenio = false;
    private boolean boolModuloRelacionmarcolIndicador = false;

    public Obligacion getObligacion() {
        return obligacion;
    }

    public void setObligacion(Obligacion obligacion) {
        this.obligacion = obligacion;
    }

    public Cronogramaobligacionesejecutada getCronogramaobligacionesejecutada() {
        return cronogramaobligacionesejecutada;
    }

    public void setCronogramaobligacionesejecutada(Cronogramaobligacionesejecutada cronogramaobligacionesejecutada) {
        this.cronogramaobligacionesejecutada = cronogramaobligacionesejecutada;
    }

    public List<Obligacion> getListaObligaciones() {
        return listaObligaciones;
    }

    public void setListaObligaciones(List<Obligacion> listaObligaciones) {
        this.listaObligaciones = listaObligaciones;
    }

    public UIDataTable getTablaObligacionbin() {
        return tablaObligacionbin;
    }

    public void setTablaObligacionbin(UIDataTable tablaObligacionbin) {
        this.tablaObligacionbin = tablaObligacionbin;
    }

    public UIDataTable getTablaIndicadorbin() {
        return tablaIndicadorbin;
    }

    public void setTablaIndicadorbin(UIDataTable tablaIndicadorbin) {
        this.tablaIndicadorbin = tablaIndicadorbin;
    }

    public UIDataTable getTablaMarcoLogicoPropositobin() {
        return tablaMarcoLogicoPropositobin;
    }

    public void setTablaMarcoLogicoPropositobin(UIDataTable tablaMarcoLogicoPropositobin) {
        this.tablaMarcoLogicoPropositobin = tablaMarcoLogicoPropositobin;
    }

    public UIDataTable getTablaMarcoLogicobin() {
        return tablaMarcoLogicobin;
    }

    public void setTablaMarcoLogicobin(UIDataTable tablaMarcoLogicobin) {
        this.tablaMarcoLogicobin = tablaMarcoLogicobin;
    }

    public UIDataTable getTablaRelacionMarcolIndicadorbin() {
        return tablaRelacionMarcolIndicadorbin;
    }

    public List<Relacionmarcologicoindicador> getListaMarcolIndicadoractividad() {
        return listaMarcolIndicadoractividad;
    }

    public void setListaMarcolIndicadoractividad(List<Relacionmarcologicoindicador> listaMarcolIndicadoractividad) {
        this.listaMarcolIndicadoractividad = listaMarcolIndicadoractividad;
    }

    public UIDataTable getTablaRelacionMarcolIndicadorActividadbin() {
        return tablaRelacionMarcolIndicadorActividadbin;
    }

    public void setTablaRelacionMarcolIndicadorActividadbin(UIDataTable tablaRelacionMarcolIndicadorActividadbin) {
        this.tablaRelacionMarcolIndicadorActividadbin = tablaRelacionMarcolIndicadorActividadbin;
    }

    public void setTablaRelacionMarcolIndicadorbin(UIDataTable tablaRelacionMarcolIndicadorbin) {
        this.tablaRelacionMarcolIndicadorbin = tablaRelacionMarcolIndicadorbin;
    }

    public UIDataTable getTablaRelacionMarcolIndicadorComponentebin() {
        return tablaRelacionMarcolIndicadorComponentebin;
    }

    public void setTablaRelacionMarcolIndicadorComponentebin(UIDataTable tablaRelacionMarcolIndicadorComponentebin) {
        this.tablaRelacionMarcolIndicadorComponentebin = tablaRelacionMarcolIndicadorComponentebin;
    }

    public UIDataTable getTablaPlanificacionRelacionMarcolIndicadorbin() {
        return tablaPlanificacionRelacionMarcolIndicadorbin;
    }

    public void setTablaPlanificacionRelacionMarcolIndicadorbin(UIDataTable tablaPlanificacionRelacionMarcolIndicadorbin) {
        this.tablaPlanificacionRelacionMarcolIndicadorbin = tablaPlanificacionRelacionMarcolIndicadorbin;
    }

    public UIDataTable getTablaAvancePlanificacionRelacionMarcolIndicadorbin() {
        return tablaAvancePlanificacionRelacionMarcolIndicadorbin;
    }

    public void setTablaAvancePlanificacionRelacionMarcolIndicadorbin(UIDataTable tablaAvancePlanificacionRelacionMarcolIndicadorbin) {
        this.tablaAvancePlanificacionRelacionMarcolIndicadorbin = tablaAvancePlanificacionRelacionMarcolIndicadorbin;
    }

    public UIDataTable getTablaCronogAvancebin() {
        return tablaCronogAvancebin;
    }

    public void setTablaCronogAvancebin(UIDataTable tablaCronogAvancebin) {
        this.tablaCronogAvancebin = tablaCronogAvancebin;
    }

    public boolean isBoolcrearobligacion() {
        return boolcrearobligacion;
    }

    public void setBoolcrearobligacion(boolean boolcrearobligacion) {
        this.boolcrearobligacion = boolcrearobligacion;
    }

    public int getFechareal() {
        return fechareal;
    }

    public void setFechareal(int fechareal) {
        this.fechareal = fechareal;
    }

    public int getFechaanterior() {
        return fechaanterior;
    }

    public void setFechaanterior(int fechaanterior) {
        this.fechaanterior = fechaanterior;
    }

    public Date getFechafinobligaciontem() {
        return fechafinobligaciontem;
    }

    public void setFechafinobligaciontem(Date fechafinobligaciontem) {
        this.fechafinobligaciontem = fechafinobligaciontem;
    }

    public boolean isEstadoObligacion() {
        return estadoObligacion;
    }

    public void setEstadoObligacion(boolean estadoObligacion) {
        this.estadoObligacion = estadoObligacion;
    }

    public int getIndextable() {
        return indextable;
    }

    public void setIndextable(int indextable) {
        this.indextable = indextable;
    }

    public boolean isVermodal() {
        return vermodal;
    }

    public void setVermodal(boolean vermodal) {
        this.vermodal = vermodal;
    }

    public boolean isBoolModuloSeguimientoConvenio() {
        return boolModuloSeguimientoConvenio;
    }

    public void setBoolModuloSeguimientoConvenio(boolean boolModuloSeguimientoConvenio) {
        this.boolModuloSeguimientoConvenio = boolModuloSeguimientoConvenio;
    }

    public boolean isBoolModuloRelacionmarcolIndicador() {
        return boolModuloRelacionmarcolIndicador;
    }

    public void setBoolModuloRelacionmarcolIndicador(boolean boolModuloRelacionmarcolIndicador) {
        this.boolModuloRelacionmarcolIndicador = boolModuloRelacionmarcolIndicador;
    }

    public List<Cronogramaobligacionesejecutada> getListaObligacionavance() {
        return listaObligacionavance;
    }

    public List<Marcologico> getListaMarcoLogico() {
        return listaMarcoLogico;
    }

    public void setListaMarcoLogico(List<Marcologico> listaMarcoLogico) {
        this.listaMarcoLogico = listaMarcoLogico;
    }

    public List<Marcologico> getListaMarcoLogicoAsociado() {
        return listaMarcoLogicoAsociado;
    }

    public void setListaMarcoLogicoAsociado(List<Marcologico> listaMarcoLogicoAsociado) {
        this.listaMarcoLogicoAsociado = listaMarcoLogicoAsociado;
    }

    public List<Indicador> getListaIndicador() {
        return listaIndicador;
    }

    public void setListaIndicador(List<Indicador> listaIndicador) {
        this.listaIndicador = listaIndicador;
    }

    public List<Relacionmarcologicoindicador> getListarelacionMarcolIndicador() {
        return listarelacionMarcolIndicador;
    }

    public void setListarelacionMarcolIndicador(List<Relacionmarcologicoindicador> listarelacionMarcolIndicador) {
        this.listarelacionMarcolIndicador = listarelacionMarcolIndicador;
    }

    public List<Relacionmarcologicoindicador> getListMarcolInidicadorRelacionados() {
        return listMarcolInidicadorRelacionados;
    }

    public void setListMarcolInidicadorRelacionados(List<Relacionmarcologicoindicador> listMarcolInidicadorRelacionados) {
        this.listMarcolInidicadorRelacionados = listMarcolInidicadorRelacionados;
    }

    public List<Relacionmarcologicoindicador> getListaMarcolIndicadorcomponente() {
        return listaMarcolIndicadorcomponente;
    }

    public void setListaMarcolIndicadorcomponente(List<Relacionmarcologicoindicador> listaMarcolIndicadorcomponente) {
        this.listaMarcolIndicadorcomponente = listaMarcolIndicadorcomponente;
    }

    public List<Avanceplanificacionrelacionmarcologicoindicador> getListaavanceplanificacionRelacionMarcolIndicador() {
        return listaavanceplanificacionRelacionMarcolIndicador;
    }

    public void setListaavanceplanificacionRelacionMarcolIndicador(List<Avanceplanificacionrelacionmarcologicoindicador> listaavanceplanificacionRelacionMarcolIndicador) {
        this.listaavanceplanificacionRelacionMarcolIndicador = listaavanceplanificacionRelacionMarcolIndicador;
    }

    public List<Planificacionrelacionmarcologicoindicador> getListaplanificacionRelacionMarcolIndicador() {
        return listaplanificacionRelacionMarcolIndicador;
    }

    public void setListaplanificacionRelacionMarcolIndicador(List<Planificacionrelacionmarcologicoindicador> listaplanificacionRelacionMarcolIndicador) {
        this.listaplanificacionRelacionMarcolIndicador = listaplanificacionRelacionMarcolIndicador;
    }

    public List<Object> getListarelacionobjeto() {
        return listarelacionobjeto;
    }

    public void setListarelacionobjeto(List<Object> listarelacionobjeto) {
        this.listarelacionobjeto = listarelacionobjeto;
    }

    public void setListaObligacionavance(List<Cronogramaobligacionesejecutada> listaObligacionavance) {
        this.listaObligacionavance = listaObligacionavance;
    }

    public int getIntIndicador() {
        return intIndicador;
    }

    public void setIntIndicador(int intIndicador) {
        this.intIndicador = intIndicador;
    }

    public int getIntmarco() {
        return intmarco;
    }

    public void setIntmarco(int intmarco) {
        this.intmarco = intmarco;
    }

    public Relacionmarcologicoindicador getRelacionmarcologicoindicador() {
        return relacionmarcologicoindicador;
    }

    public void setRelacionmarcologicoindicador(Relacionmarcologicoindicador relacionmarcologicoindicador) {
        this.relacionmarcologicoindicador = relacionmarcologicoindicador;
    }

    public Marcologico getMarcologico() {
        return marcologico;
    }

    public void setMarcologico(Marcologico marcologico) {
        this.marcologico = marcologico;
    }

    public Planificacionrelacionmarcologicoindicador getPlanificacionrelacionmarcologicoindicador() {
        return planificacionrelacionmarcologicoindicador;
    }

    public void setPlanificacionrelacionmarcologicoindicador(Planificacionrelacionmarcologicoindicador planificacionrelacionmarcologicoindicador) {
        this.planificacionrelacionmarcologicoindicador = planificacionrelacionmarcologicoindicador;
    }

    public Avanceplanificacionrelacionmarcologicoindicador getAvanceplanificacionrelacionmarcologicoindicador() {
        return avanceplanificacionrelacionmarcologicoindicador;
    }

    public void setAvanceplanificacionrelacionmarcologicoindicador(Avanceplanificacionrelacionmarcologicoindicador avanceplanificacionrelacionmarcologicoindicador) {
        this.avanceplanificacionrelacionmarcologicoindicador = avanceplanificacionrelacionmarcologicoindicador;
    }

    public Indicador getIndicador() {
        return indicador;
    }

    public void setIndicador(Indicador indicador) {
        this.indicador = indicador;
    }

    public double getPorcentajePlanificacion() {
        return porcentajePlanificacion;
    }

    public void setPorcentajePlanificacion(double porcentajePlanificacion) {
        this.porcentajePlanificacion = porcentajePlanificacion;
    }

    public double getPorcentajeAvancePlanificacion() {
        return porcentajeAvancePlanificacion;
    }

    public void setPorcentajeAvancePlanificacion(double porcentajeAvancePlanificacion) {
        this.porcentajeAvancePlanificacion = porcentajeAvancePlanificacion;
    }

    // metodo encargado de mostrar las obligaciones que tiene un convenio
    public void llenarObligacionesContrato() {
        listaObligaciones = getSessionBeanCobra().getMarcoLogicoService().encontrarObligacionesxContrato(getNuevoContratoBasico().getContrato().getIntidcontrato());
        //obligacion = new Obligacion();
    }

    public void llenarCronogramaObligacionesAvance() {
        obligacion = (Obligacion) tablaObligacionbin.getRowData();
        listaObligacionavance = getSessionBeanCobra().getMarcoLogicoService().encontrarCronogramaObligacionEjecutadaxObligacion(obligacion);//      
    }

    public String guardarPlanificacion() {

        if (planificacionrelacionmarcologicoindicador.getDatefechaplanificada() != null) {
            if (planificacionrelacionmarcologicoindicador.getDoublevariable1metaesperada() != null || planificacionrelacionmarcologicoindicador.getDoublevariable1metaesperada() != 0) {

                if (getAdministrarObraNew().getObra().getDatefeciniobra().compareTo(planificacionrelacionmarcologicoindicador.getDatefechaplanificada()) > 0) {
                    FacesUtils.addErrorMessage("La fecha que desea planificar es menor que la fecha inicial del proyecto.");
                    return null;
                }

                if (getAdministrarObraNew().getObra().getDatefecfinobra().compareTo(planificacionrelacionmarcologicoindicador.getDatefechaplanificada()) < 0) {
                    FacesUtils.addErrorMessage("La fecha que desea planificar es mayor que la fecha final del proyecto.");
                    return null;
                }

                if (planificacionrelacionmarcologicoindicador.getDoublevariable1metaesperada().compareTo(relacionmarcologicoindicador.getDoublevalor1meta()) > 0) {
                    FacesUtils.addErrorMessage("La meta intermedia debe ser menor a la fecha final (Meta final Esperada: " + relacionmarcologicoindicador.getDoublevalor1meta() + ")");
                    return null;
                }

                if (relacionmarcologicoindicador.getIndicador().getNumcantidadvariables() == 2) {
//                    if (planificacionrelacionmarcologicoindicador.getDoublevariable1metaesperada() == null || planificacionrelacionmarcologicoindicador.getDoublevariable1metaesperada() == 0) {
//                        FacesUtils.addErrorMessage("Debe ingresar la segunda variable.");
//                        return null;
//                    } else {
                    setPorcentajePlanificacion(planificacionrelacionmarcologicoindicador.getDoublevariable1metaesperada());

                    planificacionrelacionmarcologicoindicador.setDoubleporcentajeavanceestimado(getPorcentajePlanificacion());
                    planificacionrelacionmarcologicoindicador.setFkUsureporta(getSessionBeanCobra().getUsuarioObra().getUsuId());
                    planificacionrelacionmarcologicoindicador.setRelacionmarcologicoindicador(relacionmarcologicoindicador);
                    getSessionBeanCobra().getMarcoLogicoService().guardarPlanificacionRelacionMarcolIndicador(planificacionrelacionmarcologicoindicador, getSessionBeanCobra().getUsuarioObra().getUsuLogin());

                    planificacionrelacionmarcologicoindicador = new Planificacionrelacionmarcologicoindicador();
                    relacionmarcologicoindicador = new Relacionmarcologicoindicador();

                    FacesUtils.addInfoMessage("La planificación se ha guardado.");
                    //}

                } else {
                    planificacionrelacionmarcologicoindicador.setFkUsureporta(getSessionBeanCobra().getUsuarioObra().getUsuId());
                    planificacionrelacionmarcologicoindicador.setRelacionmarcologicoindicador(relacionmarcologicoindicador);
                    getSessionBeanCobra().getMarcoLogicoService().guardarPlanificacionRelacionMarcolIndicador(planificacionrelacionmarcologicoindicador, getSessionBeanCobra().getUsuarioObra().getUsuLogin());

                    planificacionrelacionmarcologicoindicador = new Planificacionrelacionmarcologicoindicador();
                    relacionmarcologicoindicador = new Relacionmarcologicoindicador();

                    FacesUtils.addInfoMessage("La planificación se ha guardado.");
                }

                return null;
            } else {
                FacesUtils.addErrorMessage("Debe ingresar la primera variable.");
                return null;
            }

        } else {
            FacesUtils.addErrorMessage("Debe seleccionar una fecha.");
            return null;
        }

    }

    public void limpiarModalAvancePlanificacion() {
        avanceplanificacionrelacionmarcologicoindicador = new Avanceplanificacionrelacionmarcologicoindicador();
        listaavanceplanificacionRelacionMarcolIndicador = getSessionBeanCobra().getMarcoLogicoService().encontrarAvancePlanificacionRelacionMarcoLindicador(relacionmarcologicoindicador);
        ajustarPermitirRegistrarAvanceIndicador();
    }

    public void limpiarModalRegistrarPlanificacion() {
        relacionmarcologicoindicador = new Relacionmarcologicoindicador();
        planificacionrelacionmarcologicoindicador = new Planificacionrelacionmarcologicoindicador();
    }

    public void limpiarModalAsociacion() {
        indicador = new Indicador();
        marcologico = new Marcologico();
        relacionmarcologicoindicador = new Relacionmarcologicoindicador();

    }

    public void limpiarModalCrearLineaBaseMeta() {
        relacionmarcologicoindicador = new Relacionmarcologicoindicador();

    }

    public void crearLineaBaseMetaComponente() {
        relacionmarcologicoindicador = new Relacionmarcologicoindicador();
        relacionmarcologicoindicador = (Relacionmarcologicoindicador) tablaRelacionMarcolIndicadorComponentebin.getRowData();

    }

    public void crearLineaBaseMetaProposito() {
        relacionmarcologicoindicador = new Relacionmarcologicoindicador();
        relacionmarcologicoindicador = (Relacionmarcologicoindicador) tablaRelacionMarcolIndicadorbin.getRowData();

    }

    public void crearLineaBaseMetaActividad() {
        relacionmarcologicoindicador = new Relacionmarcologicoindicador();
        relacionmarcologicoindicador = (Relacionmarcologicoindicador) tablaRelacionMarcolIndicadorActividadbin.getRowData();

    }

    public void limpiarModalPlanificarNuevafechaAvanceObligacion() {
        cronogramaobligacionesejecutada = new Cronogramaobligacionesejecutada();

    }

    public boolean analizarStringSoloespacios(String x) {
        for (int i = 0; i < x.length(); i++) {
            if (x.charAt(i) != ' ') {
                return true;
            }
        }
        return false;
    }
    
    public String eliminarAvancePlanificacion() {
        
        avanceplanificacionrelacionmarcologicoindicador.setDoubleavancevariablesperada1(null);
        avanceplanificacionrelacionmarcologicoindicador.setDoubleavancevariablesperada2(null);
        avanceplanificacionrelacionmarcologicoindicador.setStrobservacion(null);
        avanceplanificacionrelacionmarcologicoindicador.setDatefechacreacion(null);
        avanceplanificacionrelacionmarcologicoindicador.setDoubleporcentajeavance(null);
        avanceplanificacionrelacionmarcologicoindicador.setDatefecharealizacion(null);
        avanceplanificacionrelacionmarcologicoindicador.setFkUsureporta(null);
        
        getSessionBeanCobra().getMarcoLogicoService().guardarAvancePlanificacionRelacionMarcolIndicador(avanceplanificacionrelacionmarcologicoindicador, getSessionBeanCobra().getUsuarioObra().getUsuLogin());
        //avanceplanificacionrelacionmarcologicoindicador = new Avanceplanificacionrelacionmarcologicoindicador();
        actualizarIndicador();
        FacesUtils.addInfoMessage("El avance se ha eliminado.");

        return null;
    }

    public String guardarAvancePlanificacion() {
        if (avanceplanificacionrelacionmarcologicoindicador.getDatefecharealizacion() != null) {
            if (avanceplanificacionrelacionmarcologicoindicador.getDoubleavancevariablesperada1() != null) {

                if ((avanceplanificacionrelacionmarcologicoindicador.getStrobservacion() != null
                        && avanceplanificacionrelacionmarcologicoindicador.getStrobservacion().compareTo("") != 0)
                        && analizarStringSoloespacios(avanceplanificacionrelacionmarcologicoindicador.getStrobservacion())) {

                    if (avanceplanificacionrelacionmarcologicoindicador.getDatefecharealizacion().compareTo(listaavanceplanificacionRelacionMarcolIndicador.get(indextable).getDatefechaplanificada()) >= 0) {

                        if (indextable + 1 == listaavanceplanificacionRelacionMarcolIndicador.size()) {
                            if (relacionmarcologicoindicador.getIndicador().getNumcantidadvariables() == 2) {
                                if (avanceplanificacionrelacionmarcologicoindicador.getDoubleavancevariablesperada2() == null || avanceplanificacionrelacionmarcologicoindicador.getDoubleavancevariablesperada2() == 0) {
                                    FacesUtils.addErrorMessage("Debe ingresar la segunda variable.");
                                    return null;
                                } else {
                                    setPorcentajeAvancePlanificacion((avanceplanificacionrelacionmarcologicoindicador.getDoubleavancevariablesperada1()) / (avanceplanificacionrelacionmarcologicoindicador.getDoubleavancevariablesperada2()) * 100);

                                    avanceplanificacionrelacionmarcologicoindicador.setDoubleporcentajeavance(getPorcentajeAvancePlanificacion());
                                    avanceplanificacionrelacionmarcologicoindicador.setFkUsureporta(getSessionBeanCobra().getUsuarioObra().getUsuId());
                                    avanceplanificacionrelacionmarcologicoindicador.setDatefechacreacion(new Date());
                                    getSessionBeanCobra().getMarcoLogicoService().guardarAvancePlanificacionRelacionMarcolIndicador(avanceplanificacionrelacionmarcologicoindicador, getSessionBeanCobra().getUsuarioObra().getUsuLogin());
                                    avanceplanificacionrelacionmarcologicoindicador = new Avanceplanificacionrelacionmarcologicoindicador();
                                    actualizarIndicador();
                                    FacesUtils.addInfoMessage("El avance se ha guardado.");

                                    return null;
                                }

                            } else {

                                getSessionBeanCobra().getMarcoLogicoService().guardarAvancePlanificacionRelacionMarcolIndicador(avanceplanificacionrelacionmarcologicoindicador, getSessionBeanCobra().getUsuarioObra().getUsuLogin());
                                avanceplanificacionrelacionmarcologicoindicador.setDatefechacreacion(new Date());
                                avanceplanificacionrelacionmarcologicoindicador.setFkUsureporta(getSessionBeanCobra().getUsuarioObra().getUsuId());
                                avanceplanificacionrelacionmarcologicoindicador = new Avanceplanificacionrelacionmarcologicoindicador();
                                avanceplanificacionrelacionmarcologicoindicador.setDoubleporcentajeavance(avanceplanificacionrelacionmarcologicoindicador.getDoubleavancevariablesperada1());
                                actualizarIndicador();
                                FacesUtils.addInfoMessage("El avance se ha guardado.");
                                return null;
                            }

                        } else if (avanceplanificacionrelacionmarcologicoindicador.getDatefecharealizacion().compareTo(listaavanceplanificacionRelacionMarcolIndicador.get(indextable + 1).getDatefechaplanificada()) <= 0) {

                            if (relacionmarcologicoindicador.getIndicador().getNumcantidadvariables() == 2) {
                                if (avanceplanificacionrelacionmarcologicoindicador.getDoubleavancevariablesperada2() == null || avanceplanificacionrelacionmarcologicoindicador.getDoubleavancevariablesperada2() == 0) {
                                    FacesUtils.addErrorMessage("Debe ingresar la segunda variable.");
                                    return null;
                                } else {
                                    setPorcentajeAvancePlanificacion((avanceplanificacionrelacionmarcologicoindicador.getDoubleavancevariablesperada1()) / (avanceplanificacionrelacionmarcologicoindicador.getDoubleavancevariablesperada2()) * 100);

                                    avanceplanificacionrelacionmarcologicoindicador.setDoubleporcentajeavance(getPorcentajeAvancePlanificacion());
                                    avanceplanificacionrelacionmarcologicoindicador.setDatefechacreacion(new Date());
                                    avanceplanificacionrelacionmarcologicoindicador.setFkUsureporta(getSessionBeanCobra().getUsuarioObra().getUsuId());
                                    getSessionBeanCobra().getMarcoLogicoService().guardarAvancePlanificacionRelacionMarcolIndicador(avanceplanificacionrelacionmarcologicoindicador, getSessionBeanCobra().getUsuarioObra().getUsuLogin());
                                    avanceplanificacionrelacionmarcologicoindicador = new Avanceplanificacionrelacionmarcologicoindicador();
                                    actualizarIndicador();
                                    FacesUtils.addInfoMessage("El avance se ha guardado.");
                                    return null;
                                }

                            } else {
                                avanceplanificacionrelacionmarcologicoindicador.setDatefechacreacion(new Date());
                                avanceplanificacionrelacionmarcologicoindicador.setDoubleporcentajeavance(avanceplanificacionrelacionmarcologicoindicador.getDoubleavancevariablesperada1());
                                avanceplanificacionrelacionmarcologicoindicador.setFkUsureporta(getSessionBeanCobra().getUsuarioObra().getUsuId());
                                getSessionBeanCobra().getMarcoLogicoService().guardarAvancePlanificacionRelacionMarcolIndicador(avanceplanificacionrelacionmarcologicoindicador, getSessionBeanCobra().getUsuarioObra().getUsuLogin());
                                avanceplanificacionrelacionmarcologicoindicador = new Avanceplanificacionrelacionmarcologicoindicador();
                                actualizarIndicador();
                                FacesUtils.addInfoMessage("El avance se ha guardado.");
                                return null;
                            }
                        } else {
                            FacesUtils.addErrorMessage("La fecha avance debe ser menor que " + listaavanceplanificacionRelacionMarcolIndicador.get(indextable + 1).getDatefechaplanificada());
                            return null;
                        }

                    } else {
                        FacesUtils.addErrorMessage("La fecha avance debe ser mayor o igual que " + listaavanceplanificacionRelacionMarcolIndicador.get(indextable).getDatefechaplanificada());
                        return null;
                    }

                } else {
                    FacesUtils.addErrorMessage("Debe ingresar una observación.");
                    return null;
                }

            } else {
                FacesUtils.addErrorMessage("Debe ingresar el avance.");
                return null;
            }

        } else {
            FacesUtils.addErrorMessage("Debe seleccionar una fecha.");
            return null;
        }

    }

    public void limpiarModalAgredarObligacion() {

        obligacion = new Obligacion();
        cronogramaobligacionesejecutada = new Cronogramaobligacionesejecutada();
        llenarObligacionesContrato();
    }

    public void limpiarModalEditarAvance() {
        cronogramaobligacionesejecutada = new Cronogramaobligacionesejecutada();
        llenarObligacionesContrato();
    }

    //Metodo que guarda la obligacion de un convenio
    public String guardarObligacion() {
        if (obligacion.getDatefechainiestimada() != null) {
            if (obligacion.getDatefechafinestimada() != null) {
                if ((obligacion.getStrdescripcion() != null && obligacion.getStrdescripcion().compareTo("") != 0) && analizarStringSoloespacios(obligacion.getStrdescripcion())) {
                    if (getFechafinobligaciontem() != null && obligacion.getDatefechafinreal() != null) {
                        if (getFechafinobligaciontem().compareTo(obligacion.getDatefechafinreal()) > 0) {
                            FacesUtils.addErrorMessage("La nueva fecha finalización no debe ser menor a la que esta ingresada");
                            return null;
                        }
                    }

                    if (getNuevoContratoBasico().getContrato().getDatefechaini().compareTo(obligacion.getDatefechainiestimada()) > 0) {
                        FacesUtils.addErrorMessage("La fecha inicial que desea ingresar no debe ser menor a la fecha inicial del convenio.");

                        return null;
                    }
                    //valida uqe la fecha final no sea menor que la inicial de el convenio
                    if (getNuevoContratoBasico().getContrato().getDatefechaini().compareTo(obligacion.getDatefechafinestimada()) > 0) {
                        FacesUtils.addErrorMessage("La fecha de finalización que desea ingresar no debe ser menor a la fecha inicial del convenio.");
                        return null;
                    }
                    if (getNuevoContratoBasico().getContrato().getDatefechafin().compareTo(obligacion.getDatefechafinestimada()) < 0) {

                        FacesUtils.addErrorMessage("La fecha de finalización que desea ingresar no debe ser mayor a la fecha final del convenio.");
                        return null;
                    }
                    //valida que la fecha
                    if (getNuevoContratoBasico().getContrato().getDatefechafin().compareTo(obligacion.getDatefechainiestimada()) < 0) {
                        FacesUtils.addErrorMessage("La fecha inicial que desea ingresar no debe ser mayor a la fecha final del convenio.");
                        return null;
                    }

                    obligacion.setFkIntidcontrato(getNuevoContratoBasico().getContrato().getIntidcontrato());

                    obligacion.setStrresponsable(getNuevoContratoBasico().getContrato().getContratista().getTercero().getStrnombre());
                    obligacion.setFkIntusucreacion(getSessionBeanCobra().getUsuarioObra().getUsuId());
                    obligacion.setDatefechacreacion(new Date());

                    if (obligacion.getDatefechainireal() != null) {
                        if (obligacion.getDatefechafinreal() != null) {
                            Date fechafintemp = obligacion.getDatefechafinreal();
                            if (fechafintemp.compareTo(obligacion.getDatefechafinreal()) < 0) {
                                FacesUtils.addErrorMessage("La fecha ingresada no puede ser menor ala fecha final planificada");
                                return null;
                            }

                            if (obligacion.getDatefechainireal().compareTo(obligacion.getDatefechafinreal()) > 0) {
                                FacesUtils.addErrorMessage("La fecha de finalización estimada no debe ser menor a la fecha inicial estimada.");
                                return null;
                            }

                            if (getNuevoContratoBasico().getContrato().getDatefechaini().compareTo(obligacion.getDatefechainireal()) > 0) {
                                FacesUtils.addErrorMessage("La fecha inicial que desea ingresar no debe ser menor a la fecha inicial del convenio.");

                                return null;
                            }
                            //valida uqe la fecha final no sea menor que la inicial de el convenio
                            if (getNuevoContratoBasico().getContrato().getDatefechaini().compareTo(obligacion.getDatefechafinreal()) > 0) {
                                FacesUtils.addErrorMessage("La fecha de finalización que desea ingresar no debe ser menor a la fecha inicial del convenio.");
                                return null;
                            }
                            if (getNuevoContratoBasico().getContrato().getDatefechafin().compareTo(obligacion.getDatefechafinreal()) < 0) {

                                FacesUtils.addErrorMessage("La fecha de finalización que desea ingresar no debe ser mayor a la fecha final del convenio.");
                                return null;
                            }
                            //valida que la fecha
                            if (getNuevoContratoBasico().getContrato().getDatefechafin().compareTo(obligacion.getDatefechainireal()) < 0) {
                                FacesUtils.addErrorMessage("La fecha inicial que desea ingresar no debe ser mayor a la fecha final del convenio.");
                                return null;
                            }

                            listaObligacionavance = getSessionBeanCobra().getMarcoLogicoService().encontrarCronogramaObligacionEjecutadaxObligacion(obligacion);

                            for (Cronogramaobligacionesejecutada cronog : listaObligacionavance) {

                                if (cronog.getStrdescripcion() != null && !cronog.getStrdescripcion().isEmpty()) {

                                    List<Cronogramaobligacionesejecutada> lstcronog = new ArrayList<Cronogramaobligacionesejecutada>();

                                    Calendar fecha = Calendar.getInstance();
                                    fecha.setTime(listaObligacionavance.get(listaObligacionavance.size() - 1).getDatefechaavance());
                                    fecha.add(Calendar.DATE, +(30));

                                    Cronogramaobligacionesejecutada crog = new Cronogramaobligacionesejecutada();
                                    crog.setBooleanavanceobligatorio(true);
                                    crog.setDatefechaavance(fecha.getTime());
                                    crog.setFkIntusucreacion(getSessionBeanCobra().getUsuarioObra().getUsuId());
                                    crog.setObligacion(obligacion);

                                    if (fecha.getTime().compareTo(obligacion.getDatefechafinreal()) > 0) {
                                        crog.setDatefechaavance(obligacion.getDatefechafinreal());
                                    }
                                    lstcronog.add(crog);

                                    obligacion.setCronogramaobligacionesejecutadas(new LinkedHashSet(lstcronog));
                                    getSessionBeanCobra().getMarcoLogicoService().guardarObligacion(obligacion, getSessionBeanCobra().getUsuarioObra().getUsuLogin());

                                    limpiarModalAgredarObligacion();
                                    llenarObligacionesContrato();
                                    FacesUtils.addInfoMessage("La obligación se ha guardado.");

                                    return null;
                                }
                            }

                            getSessionBeanCobra().getMarcoLogicoService().eliminarCronogramaObligacion(obligacion.getIntidobligacion());

                            List<Cronogramaobligacionesejecutada> lstcronog = new ArrayList<Cronogramaobligacionesejecutada>();
                            Calendar fecha = Calendar.getInstance();
                            fecha.setTime(obligacion.getDatefechainireal());
                            do {
                                fecha.add(Calendar.DATE, +(30));

                                Cronogramaobligacionesejecutada crog = new Cronogramaobligacionesejecutada();
                                crog.setBooleanavanceobligatorio(true);
                                crog.setDatefechaavance(fecha.getTime());
                                crog.setFkIntusucreacion(getSessionBeanCobra().getUsuarioObra().getUsuId());
                                crog.setObligacion(obligacion);

                                if (fecha.getTime().compareTo(obligacion.getDatefechafinreal()) > 0) {

                                    crog.setDatefechaavance(obligacion.getDatefechafinreal());
                                }
                                lstcronog.add(crog);
                            } while (fecha.getTime().compareTo(obligacion.getDatefechafinreal()) < 0);

                            obligacion.setCronogramaobligacionesejecutadas(new LinkedHashSet(lstcronog));

                        }

                    }

                    getSessionBeanCobra().getMarcoLogicoService().guardarObligacion(obligacion, getSessionBeanCobra().getUsuarioObra().getUsuLogin());

                    limpiarModalAgredarObligacion();
                    llenarObligacionesContrato();
                    FacesUtils.addInfoMessage("La obligación se ha guardado.");
                    return null;
                } else {
                    FacesUtils.addErrorMessage("Debe ingresar la obligación.");
                    return null;
                }

            } else {
                FacesUtils.addErrorMessage("Debe seleccionar una fecha de finalización estimada.");
                return null;
            }

        } else {
            FacesUtils.addErrorMessage("Debe seleccionar una fecha inicial estimada");
            return null;
        }
    }

    public String cancelarGuardado() {
        obligacion = new Obligacion();
        habilitarfechainicialestimada = false;
        habilitarfechafinestimada = false;
        return null;
    }

    private boolean tieneCronograma() {
        listaObligacionavance = getSessionBeanCobra().getMarcoLogicoService().encontrarCronogramaObligacionEjecutadaxObligacion(obligacion);
        if (listaObligacionavance.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public String guardarFechaAvanceAdicionalObligacion() {
        
        if (cronogramaobligacionesejecutada.getDatefechaavance() != null) {
            cronogramaobligacionesejecutada.setObligacion(obligacion);
            cronogramaobligacionesejecutada.setBooleanavanceobligatorio(false);
            cronogramaobligacionesejecutada.setFkIntusucreacion(getSessionBeanCobra().getUsuarioObra().getUsuId());
            getSessionBeanCobra().getMarcoLogicoService().guardarAvanceObligacion(cronogramaobligacionesejecutada, getSessionBeanCobra().getUsuarioObra().getUsuLogin());
            cronogramaobligacionesejecutada = new Cronogramaobligacionesejecutada();

            //listar cronograma obligaciones
            listaObligacionavance = getSessionBeanCobra().getMarcoLogicoService().encontrarCronogramaObligacionEjecutadaxObligacion(obligacion);

            FacesUtils.addInfoMessage("El avance de la obligación se ha guardado.");
        } else {
            FacesUtils.addErrorMessage("Debe ingresar una fecha.");
            return null;
        }

        return null;
    }

    //Metodo que valida si una cadena tiene espacios
    //Devuelve true si una cadena tiene espacios, false si es lo contrario
    public boolean validarEspacios(String cad) {
        for (int i = 0; i < cad.length(); i++) {
            if (cad.charAt(i) != ' ') {
                return false;
            }
        }
        return true;
    }

    public String guardarAvanceObligacion() {
        guardoavance = false;
//        if (fechaanterior == 0 || fechareal == 0) {
//            cronogramaobligacionesejecutada.getObligacion().setFkIntusumodificacion(getSessionBeanCobra().getUsuarioObra().getUsuId());
//            cronogramaobligacionesejecutada.getObligacion().setDatefechamodificacion(new Date());
//            getSessionBeanCobra().getMarcoLogicoService().guardarObligacion(cronogramaobligacionesejecutada.getObligacion(), getSessionBeanCobra().getUsuarioObra().getUsuLogin());
//        }

        if (cronogramaobligacionesejecutada.getDatefechacreacion().compareTo(listaObligacionavance.get(indextable).getDatefechaavance()) >= 0) {
            if (indextable + 1 == listaObligacionavance.size()) {
                if (cronogramaobligacionesejecutada.getDatefechacreacion().compareTo(listaObligacionavance.get(indextable).getDatefechaavance()) <= 0) {
                    if (cronogramaobligacionesejecutada.getStrdescripcion().length() > 0 || cronogramaobligacionesejecutada.getStrdescripcion().isEmpty()) {
                        if (validarEspacios(cronogramaobligacionesejecutada.getStrdescripcion()) == false) {
                            cronogramaobligacionesejecutada.setFkIntusucreacion(getSessionBeanCobra().getUsuarioObra().getUsuId());
                            cronogramaobligacionesejecutada.setDatefechacreacion(new Date());
                            getSessionBeanCobra().getMarcoLogicoService().guardarAvanceObligacion(cronogramaobligacionesejecutada, getSessionBeanCobra().getUsuarioObra().getUsuLogin());
                            FacesUtils.addInfoMessage("El avance de la obligación se ha guardado.");
                            limpiarModalEditarAvance();
                            guardoavance = true;
                            //cronogramaobligacionesejecutada = null;
                            this.limpiarObligaciones();
                            return null;

                        } else {
                            FacesUtils.addErrorMessage("No Puede digitar solo espacios");
                        }

                    } else {
                        FacesUtils.addErrorMessage("Debe ingresar la observación ");
                    }

                } else {
                    FacesUtils.addErrorMessage("La fecha avance debe ser menor o igual que " + listaObligacionavance.get(indextable).getDatefechaavance());
                }

            } else if (cronogramaobligacionesejecutada.getDatefechacreacion().compareTo(listaObligacionavance.get(indextable + 1).getDatefechaavance()) < 0) {
                cronogramaobligacionesejecutada.setFkIntusucreacion(getSessionBeanCobra().getUsuarioObra().getUsuId());
                cronogramaobligacionesejecutada.setDatefechacreacion(new Date());
                getSessionBeanCobra().getMarcoLogicoService().guardarAvanceObligacion(cronogramaobligacionesejecutada, getSessionBeanCobra().getUsuarioObra().getUsuLogin());
                FacesUtils.addInfoMessage("El avance de la obligación se ha guardado.");
                limpiarModalEditarAvance();
                //cronogramaobligacionesejecutada = null;
                llenarObligacionesContrato();
                guardoavance = true;
                this.limpiarObligaciones();
                return null;
            } else {
                FacesUtils.addErrorMessage("La fecha avance debe ser menor que " + listaObligacionavance.get(indextable + 1).getDatefechaavance());
            }
        } else {
            FacesUtils.addErrorMessage("La fecha avance debe ser mayor o igual que " + listaObligacionavance.get(indextable).getDatefechaavance());

            return null;
        }

        return null;
    }

    // Edita la obligacion seleccionada de la lista en el detalle del convenio.
    public String editarObligacion() {
        obligacion = new Obligacion();
        habilitarfechainicialestimada = false;
        habilitarfechafinestimada = false;
        habilitarfechainicioreal = false;
        habilitarfechafinreal = false;
        obligacion = (Obligacion) tablaObligacionbin.getRowData();
        obligacion.setCronogramaobligacionesejecutadas(new LinkedHashSet<Cronogramaobligacionesejecutada>(getSessionBeanCobra().getMarcoLogicoService().encontrarCronogramaObligacionEjecutadaxObligacion(obligacion)));
        setFechafinobligaciontem(obligacion.getDatefechafinreal());
        if (obligacion.getDatefechainiestimada() != null) {
            habilitarfechainicialestimada = true;
        }
        if (obligacion.getDatefechafinestimada() != null) {
            habilitarfechafinestimada = true;
        }
        if (obligacion.getDatefechainireal() != null) {
            habilitarfechainicioreal = true;
        }
        if (obligacion.getDatefechafinestimada() != null) {
            habilitarfechafinreal = true;
        }

        // boolcrearobligacion = true;
        return null;
    }

    public void editarAvance() {
        guardoavance = false;
        habilitarguardaravance = true;
//        fechareal = 0;
//        fechaanterior = 0;
        indextable = tablaCronogAvancebin.getRowIndex();
        cronogramaobligacionesejecutada = (Cronogramaobligacionesejecutada) tablaCronogAvancebin.getRowData();
        totalcronogobligacion = tablaCronogAvancebin.getRowCount();

//        if (cronogramaobligacionesejecutada.getObligacion().getDatefechainireal() != null) {
//            fechaanterior = 1;
//        }
//        if (cronogramaobligacionesejecutada.getObligacion().getDatefechafinreal() != null) {
//            fechareal = 1;
//        }
//        if (cronogramaobligacionesejecutada.getStrdescripcion() != null) {
//            habilitarguardaravance = false;
//        }
//
//        if (tablaCronogAvancebin.getRowIndex() == 0) {
//        } else if (listaObligacionavance.get(tablaCronogAvancebin.getRowIndex() - 1).getDatefechacreacion() != null) {
//        } else {
//            FacesUtils.addErrorMessage("El avance anterior no se ha ingresado");
//            habilitarguardaravance = false;
//        }
    }

    public String cancelarEliminarObligacion() {
        obligacion = new Obligacion();
        return null;
    }

    public String preEliminarObligacion() {
        obligacion = (Obligacion) tablaObligacionbin.getRowData();
        return null;
    }

    //Elimina la obligacion seleccionada de la lista en el detalle del convenio.
    public String eliminarObligacion() {

        listaObligacionavance = getSessionBeanCobra().getMarcoLogicoService().encontrarCronogramaObligacionEjecutadaxObligacion(obligacion);

        for (Cronogramaobligacionesejecutada cronog : listaObligacionavance) {

            if (cronog.getStrdescripcion() != null && !cronog.getStrdescripcion().isEmpty()) {

                FacesUtils.addErrorMessage("No puede eliminarse la obligación, tiene avances asociados.");

                return null;
            }
        }

        getSessionBeanCobra().getMarcoLogicoService().borrarObligacion(obligacion);
        FacesUtils.addInfoMessage("La obligación se eliminó exitosamente.");
        llenarObligacionesContrato();
        obligacion = new Obligacion();
        return null;
    }

    public void llenarMarcoLogico() {

        listaMarcoLogico = getSessionBeanCobra().getMarcoLogicoService().encontrarMarcoLogicoxProyecto(getAdministrarObraNew().getObra().getIntcodigoobra());

    }

    public void llenarMarcoLogicoProposito() {

        listaMarcoLogico = getSessionBeanCobra().getMarcoLogicoService().encontrarMarcoLogicoPropositoXCodigoProyecto(getAdministrarObraNew().getObra().getIntcodigoobra(), 1);

    }

    public void llenarIndicador() {
        listaIndicador = getSessionBeanCobra().getMarcoLogicoService().encontrarIndicador(getAdministrarObraNew().getObra().getIntcodigoobra());

    }

    public String seleccionaIndicador() {
        indicador = (Indicador) tablaIndicadorbin.getRowData();
        return null;
    }

    public String seleccionaMarcoLogico() {
        marcologico = (Marcologico) tablaMarcoLogicobin.getRowData();
        return null;
    }

    //llena la planificacion del avance tipo proposito
    public String llenarPlanificacionRelacionMarcoLogicoIndicador() {
        habilitarguardaravancePlanificacion = true;
        indextable = tablaAvancePlanificacionRelacionMarcolIndicadorbin.getRowIndex();
        avanceplanificacionrelacionmarcologicoindicador = (Avanceplanificacionrelacionmarcologicoindicador) tablaAvancePlanificacionRelacionMarcolIndicadorbin.getRowData();

//        if (tablaAvancePlanificacionRelacionMarcolIndicadorbin.getRowIndex() == 0) {
//        } else if (listaavanceplanificacionRelacionMarcolIndicador.get(tablaAvancePlanificacionRelacionMarcolIndicadorbin.getRowIndex() - 1).getStrobservacion() != null || listaavanceplanificacionRelacionMarcolIndicador.get(tablaAvancePlanificacionRelacionMarcolIndicadorbin.getRowIndex() - 1).getStrobservacion().isEmpty()) {
//            habilitarguardaravancePlanificacion = false;
//            FacesUtils.addErrorMessage("No es posible registrar este avance por que el posterior ya fue reportado");
//        }
        return null;
    }

    public String llenarPlanificacionRelacionMarcoLogicoIndicadorComponente() {
        habilitarguardaravancePlanificacion = true;
        indextable = tablaAvancePlanificacionRelacionMarcolIndicadorbin.getRowIndex();
        avanceplanificacionrelacionmarcologicoindicador = (Avanceplanificacionrelacionmarcologicoindicador) tablaAvancePlanificacionRelacionMarcolIndicadorbin.getRowData();

//        if (tablaAvancePlanificacionRelacionMarcolIndicadorbin.getRowIndex() == 0) {
//        } else if (listaavanceplanificacionRelacionMarcolIndicador.get(tablaAvancePlanificacionRelacionMarcolIndicadorbin.getRowIndex() - 1).getStrobservacion() == null || listaavanceplanificacionRelacionMarcolIndicador.get(tablaAvancePlanificacionRelacionMarcolIndicadorbin.getRowIndex() - 1).getStrobservacion().isEmpty()) {
//            habilitarguardaravancePlanificacion = false;
//            FacesUtils.addErrorMessage("El avance anterior no se ha ingresado");
//        }
        return null;
    }

    public void llenarRelacionmarcologicoindicador() {
        llenarRelacionmarcologicoindicadorByProposito();
        llenarRelacionmarcologicoindicadorByComponente();
        llenarRelacionmarcologicoindicadorByActividad();
    }

    public void llenarRelacionmarcologicoindicadorByProposito() {
        try {
            listarelacionMarcolIndicador = getSessionBeanCobra().getMarcoLogicoService().encontrarRelacionmarcologicoindicadorXcodigoObra(getAdministrarObraNew().getObra().getIntcodigoobra());
        } catch (Exception e) {
        }

    }

    public void llenarRelacionmarcologicoindicadorByComponente() {
        try {
            listaMarcolIndicadorcomponente = getSessionBeanCobra().getMarcoLogicoService().encontrarRelacionmarcologicoindicadorXcodigoObraByComponente(getAdministrarObraNew().getObra().getIntcodigoobra());
        } catch (Exception e) {
        }

    }

    public void llenarRelacionmarcologicoindicadorByActividad() {
        try {
            listaMarcolIndicadoractividad = getSessionBeanCobra().getMarcoLogicoService().encontrarRelacionmarcologicoindicadorXcodigoObraByActividad(getAdministrarObraNew().getObra().getIntcodigoobra());
        } catch (Exception e) {
        }

    }

    // ---------------------- tabla proposito ----------------------------
    public void llenarAvancePlanificacionRelacionMarcolIndicador() {
        relacionmarcologicoindicador = new Relacionmarcologicoindicador();
        relacionmarcologicoindicador = (Relacionmarcologicoindicador) tablaRelacionMarcolIndicadorbin.getRowData();
        listaavanceplanificacionRelacionMarcolIndicador = getSessionBeanCobra().getMarcoLogicoService().encontrarAvancePlanificacionRelacionMarcoLindicador(relacionmarcologicoindicador);
        ajustarPermitirRegistrarAvanceIndicador();
    }

    public void llenarPlanificacionRelacionMarcolIndicador() {
        relacionmarcologicoindicador = (Relacionmarcologicoindicador) tablaRelacionMarcolIndicadorbin.getRowData();
        listaplanificacionRelacionMarcolIndicador = getSessionBeanCobra().getMarcoLogicoService().encontrarPlanificacionRelacionMarcoLindicador(relacionmarcologicoindicador);
    }

    //Carga marco logico asociado de tipo proposito
    public void llenarMarcoLogicoByAsociado() {
        relacionmarcologicoindicador = (Relacionmarcologicoindicador) tablaRelacionMarcolIndicadorbin.getRowData();
        listaMarcoLogicoAsociado = getSessionBeanCobra().getMarcoLogicoService().encontrarMarcoLogicoAsociado(relacionmarcologicoindicador.getMarcologico().getIntidmarcologico());
    }

    //Carga Id Relacion Marcologico Indicador Asociada de tipo Proposito
    public void cargarIdRelacionMarcologicoIndicadorAsociada() {
        planificacionrelacionmarcologicoindicador = new Planificacionrelacionmarcologicoindicador();
        relacionmarcologicoindicador = (Relacionmarcologicoindicador) tablaRelacionMarcolIndicadorbin.getRowData();
    }

    //Carga inidcador asociado de tipo proposito
    public void llenarIndicadorByAsociado() {
        
        relacionmarcologicoindicador = (Relacionmarcologicoindicador) tablaRelacionMarcolIndicadorbin.getRowData();
        indicadorAsociado = getSessionBeanCobra().getMarcoLogicoService().encontrarIndicadorXId(relacionmarcologicoindicador.getIndicador().getIntidindicador());        
    }

    //=========================================================
    //------------------------ tabla componente ---------------------------
    //Carga inidcador asociado de tipo Componente
    public void llenarAvancePlanificacionRelacionMarcolIndicadorComponente() {
        relacionmarcologicoindicador = new Relacionmarcologicoindicador();
        relacionmarcologicoindicador = (Relacionmarcologicoindicador) tablaRelacionMarcolIndicadorComponentebin.getRowData();
        listaavanceplanificacionRelacionMarcolIndicador = getSessionBeanCobra().getMarcoLogicoService().encontrarAvancePlanificacionRelacionMarcoLindicador(relacionmarcologicoindicador);
        ajustarPermitirRegistrarAvanceIndicador();
    }

    public void llenarIndicadorByAsociadoByComponente() {
        relacionmarcologicoindicador = (Relacionmarcologicoindicador) tablaRelacionMarcolIndicadorComponentebin.getRowData();
        indicadorAsociado = getSessionBeanCobra().getMarcoLogicoService().encontrarIndicadorXId(relacionmarcologicoindicador.getIndicador().getIntidindicador());

    }

    // llena la Planificacion Relacionada entre Marcol Indicador Componente
    public void llenarPlanificacionRelacionMarcolIndicadorByComponente() {
        relacionmarcologicoindicador = (Relacionmarcologicoindicador) tablaRelacionMarcolIndicadorComponentebin.getRowData();
        listaplanificacionRelacionMarcolIndicador = getSessionBeanCobra().getMarcoLogicoService().encontrarPlanificacionRelacionMarcoLindicador(relacionmarcologicoindicador);
    }
    //Carga marco logico asociado de tipo componente

    public void llenarMarcoLogicoByAsociadoByComponente() {
        relacionmarcologicoindicador = (Relacionmarcologicoindicador) tablaRelacionMarcolIndicadorComponentebin.getRowData();
        listaMarcoLogicoAsociado = getSessionBeanCobra().getMarcoLogicoService().encontrarMarcoLogicoAsociado(relacionmarcologicoindicador.getMarcologico().getIntidmarcologico());

    }
    //Carga Id Relacion Marcologico Indicador Asociada de tipo Componente

    public void cargarIdRelacionMarcologicoIndicadorAsociadaByComponente() {
        planificacionrelacionmarcologicoindicador = new Planificacionrelacionmarcologicoindicador();
        relacionmarcologicoindicador = (Relacionmarcologicoindicador) tablaRelacionMarcolIndicadorComponentebin.getRowData();
    }

    //=======================================================================
    //--------------------- tabla actividad -----------------------------------
    public void llenarAvancePlanificacionRelacionMarcolIndicadorActividad() {
        relacionmarcologicoindicador = new Relacionmarcologicoindicador();
        relacionmarcologicoindicador = (Relacionmarcologicoindicador) tablaRelacionMarcolIndicadorActividadbin.getRowData();
        listaavanceplanificacionRelacionMarcolIndicador = getSessionBeanCobra().getMarcoLogicoService().encontrarAvancePlanificacionRelacionMarcoLindicador(relacionmarcologicoindicador);
        ajustarPermitirRegistrarAvanceIndicador();
    }

    public void ajustarPermitirRegistrarAvanceIndicador() {
        int i = 0;

//        while (i < listaavanceplanificacionRelacionMarcolIndicador.size()) {
//            if (listaavanceplanificacionRelacionMarcolIndicador.get(i).getDatefecharealizacion() == null) {
//                listaavanceplanificacionRelacionMarcolIndicador.get(i).setPermitirAvance(true);
//                i = listaavanceplanificacionRelacionMarcolIndicador.size();
//            }
//            i++;
//        }
        int ind = encontrarIndiceMaximoAvanceReportado();

        while (i < listaavanceplanificacionRelacionMarcolIndicador.size()) {
            if (listaavanceplanificacionRelacionMarcolIndicador.get(i).getDatefecharealizacion() == null) {
                if (i < ind) {
                    listaavanceplanificacionRelacionMarcolIndicador.get(i).setPermitirAvance(false);
                } else {
                    listaavanceplanificacionRelacionMarcolIndicador.get(i).setPermitirAvance(true);
                    //i = listaavanceplanificacionRelacionMarcolIndicador.size();
                }
            }
            i++;
        }

    }

    public int encontrarIndiceMaximoAvanceReportado() {
        int indice = -1, i = 0;

        while (i < listaavanceplanificacionRelacionMarcolIndicador.size()) {
            if (listaavanceplanificacionRelacionMarcolIndicador.get(i).getDatefecharealizacion() != null) {
                indice = i;
            }
            i++;
        }
        return indice;
    }

    //Carga marco logico asociado de tipo Actividad
    public void llenarMarcoLogicoByAsociadoByActividad() {
        relacionmarcologicoindicador = (Relacionmarcologicoindicador) tablaRelacionMarcolIndicadorActividadbin.getRowData();
        listaMarcoLogicoAsociado = getSessionBeanCobra().getMarcoLogicoService().encontrarMarcoLogicoAsociado(relacionmarcologicoindicador.getMarcologico().getIntidmarcologico());
    }

    //Carga indicador asociado de tipo Actividad
    public void llenarIndicadorByAsociadoByActividad() {
        relacionmarcologicoindicador = (Relacionmarcologicoindicador) tablaRelacionMarcolIndicadorActividadbin.getRowData();
        indicadorAsociado = getSessionBeanCobra().getMarcoLogicoService().encontrarIndicadorXId(relacionmarcologicoindicador.getIndicador().getIntidindicador());
    }

    //Carga Id Relacion Marcologico Indicador Asociada de tipo Actividad
    public void cargarIdRelacionMarcologicoIndicadorAsociadoByActividad() {
        planificacionrelacionmarcologicoindicador = new Planificacionrelacionmarcologicoindicador();
        relacionmarcologicoindicador = (Relacionmarcologicoindicador) tablaRelacionMarcolIndicadorActividadbin.getRowData();
    }

    public void llenarPlanificacionRelacionMarcolIndicadorByActividad() {
        relacionmarcologicoindicador = (Relacionmarcologicoindicador) tablaRelacionMarcolIndicadorActividadbin.getRowData();
        listaplanificacionRelacionMarcolIndicador = getSessionBeanCobra().getMarcoLogicoService().encontrarPlanificacionRelacionMarcoLindicador(relacionmarcologicoindicador);
    }

//    public String guardarAsociacionMarcoLogicoIndicador() {
//        if (marcologico.getIntidmarcologico() != 0) {
//            if (indicador.getIntidindicador() != 0) {
//                if (relacionmarcologicoindicador.getDoublevalor1lineabase() != 0) {
//
//                    relacionmarcologicoindicador.setMarcologico(marcologico);
//                    relacionmarcologicoindicador.setIndicador(indicador);
//                    relacionmarcologicoindicador.setDatefechacreacion(new Date());
//                    relacionmarcologicoindicador.setFkIntusureporta(getSessionBeanCobra().getUsuarioObra().getUsuId());
//
//                    List<Planificacionrelacionmarcologicoindicador> PmlInd = new ArrayList<Planificacionrelacionmarcologicoindicador>();
//
//                    if (indicador.getNumcantidadvariables() == 2) {
//
//                        planificacionrelacionmarcologicoindicador.setDoublevariable1metaesperada(relacionmarcologicoindicador.getDoublevalor1meta());
//                        planificacionrelacionmarcologicoindicador.setDoublevariable2metaesperada(relacionmarcologicoindicador.getDoublevalor2meta());
//                        planificacionrelacionmarcologicoindicador.setFkUsureporta(1);
//                        planificacionrelacionmarcologicoindicador.setRelacionmarcologicoindicador(relacionmarcologicoindicador);
//                        planificacionrelacionmarcologicoindicador.setDatefechaplanificada(getAdministrarObraNew().getObra().getDatefecfinobra());
//                        PmlInd.add(planificacionrelacionmarcologicoindicador);
//
//                    } else if (indicador.getNumcantidadvariables() == 1) {
//
//                        planificacionrelacionmarcologicoindicador.setDoublevariable1metaesperada(relacionmarcologicoindicador.getDoublevalor1meta());
//                        planificacionrelacionmarcologicoindicador.setFkUsureporta(1);
//                        planificacionrelacionmarcologicoindicador.setRelacionmarcologicoindicador(relacionmarcologicoindicador);
//                        planificacionrelacionmarcologicoindicador.setDatefechaplanificada(getAdministrarObraNew().getObra().getDatefecfinobra());
//                        PmlInd.add(planificacionrelacionmarcologicoindicador);
//
//                    }
//
//                    if (indicador.getFrecuenciaperiocidad().getIntidfrecuenciaperiocidad() == 4) {
//                        List<Avanceplanificacionrelacionmarcologicoindicador> listAvancPlanRelMarlInd = new ArrayList<Avanceplanificacionrelacionmarcologicoindicador>();
//
//                        Calendar fecha = Calendar.getInstance();
//                        fecha.setTime(getAdministrarObraNew().getObra().getDatefeciniobra());
//                        while (fecha.getTime().compareTo(getAdministrarObraNew().getObra().getDatefecfinobra()) <= 0) {
//                            fecha.add(Calendar.DATE, +(30));
//
//                            Avanceplanificacionrelacionmarcologicoindicador avanceplan = new Avanceplanificacionrelacionmarcologicoindicador();
//
//                            avanceplan.setDatefechaplanificada(fecha.getTime());
//                            avanceplan.setFkUsureporta(getSessionBeanCobra().getUsuarioObra().getUsuId());
//                            avanceplan.setRelacionmarcologicoindicador(relacionmarcologicoindicador);
//                            if (fecha.getTime().compareTo(getAdministrarObraNew().getObra().getDatefecfinobra()) > 0) {
//                                avanceplan.setDatefechaplanificada(getAdministrarObraNew().getObra().getDatefecfinobra());
//                            }
//                            listAvancPlanRelMarlInd.add(avanceplan);
//
//                        }
//                        relacionmarcologicoindicador.setAvanceplanificacionrelacionmarcologicoindicadors(new LinkedHashSet(listAvancPlanRelMarlInd));
//
//                    } else if (indicador.getFrecuenciaperiocidad().getIntidfrecuenciaperiocidad() == 6) {
//                        List<Avanceplanificacionrelacionmarcologicoindicador> listAvancPlanRelMarlInd = new ArrayList<Avanceplanificacionrelacionmarcologicoindicador>();
//
//                        Calendar fecha = Calendar.getInstance();
//                        fecha.setTime(getAdministrarObraNew().getObra().getDatefeciniobra());
//                        while (fecha.getTime().compareTo(getAdministrarObraNew().getObra().getDatefecfinobra()) <= 0) {
//                            fecha.add(Calendar.DATE, +(90));
//
//                            Avanceplanificacionrelacionmarcologicoindicador avanceplan = new Avanceplanificacionrelacionmarcologicoindicador();
//
//                            avanceplan.setDatefechaplanificada(fecha.getTime());
//                            avanceplan.setFkUsureporta(getSessionBeanCobra().getUsuarioObra().getUsuId());
//                            avanceplan.setRelacionmarcologicoindicador(relacionmarcologicoindicador);
//                            if (fecha.getTime().compareTo(getAdministrarObraNew().getObra().getDatefecfinobra()) > 0) {
//                                avanceplan.setDatefechaplanificada(getAdministrarObraNew().getObra().getDatefecfinobra());
//                            }
//                            listAvancPlanRelMarlInd.add(avanceplan);
//
//                        }
//                        relacionmarcologicoindicador.setAvanceplanificacionrelacionmarcologicoindicadors(new LinkedHashSet(listAvancPlanRelMarlInd));
//
//                    }
//
//                    relacionmarcologicoindicador.setPlanificacionrelacionmarcologicoindicadors(new LinkedHashSet(PmlInd));
//
//                    getSessionBeanCobra().getMarcoLogicoService().guardarAsociacionMarcoLogicoIndicador(relacionmarcologicoindicador);
//                    relacionmarcologicoindicador = new Relacionmarcologicoindicador();
//                    marcologico = new Marcologico();
//                    indicador = new Indicador();
//
//                    llenarRelacionmarcologicoindicador();
//
//                    FacesUtils.addInfoMessage("La Asociación se guardo");
//
//                    return null;
//                } else {
//                    FacesUtils.addErrorMessage("Debe ingresar la linea base");
//                    return null;
//                }
//            } else {
//                FacesUtils.addErrorMessage("Debe seleccionar un indicador");
//                return null;
//            }
//        } else {
//            FacesUtils.addErrorMessage("Debe seleccionar un marco logico");
//            return null;
//        }
//
//    }
    
    
     public String eliminarMetaLinesBase() {

        relacionmarcologicoindicador.setDoublevalor1lineabase(null);
        relacionmarcologicoindicador.setDoublevalor2lineabase(null);
        relacionmarcologicoindicador.setDoubleporcentajelinebase(null);
        relacionmarcologicoindicador.setDoublevalor1meta(null);
        relacionmarcologicoindicador.setStrobservacionmeta(null);
        relacionmarcologicoindicador.setDatefechalineabase(null);
        
      
        getSessionBeanCobra().getMarcoLogicoService().guardarAsociacionMarcoLogicoIndicador(relacionmarcologicoindicador);
        //relacionmarcologicoindicador = new Relacionmarcologicoindicador();

        llenarRelacionmarcologicoindicador();

        FacesUtils.addInfoMessage("Se ha eliminado la linea base");

        return null;
    }
     
     
    public String guardarMetaLineabaseAsociacionMarcoLogicoIndicador() {
        if (relacionmarcologicoindicador.getDoublevalor1lineabase() != null) {
            if (relacionmarcologicoindicador.getStrobservacionmeta() != null) {

                relacionmarcologicoindicador.setDatefechacreacion(new Date());
                relacionmarcologicoindicador.setFkIntusureporta(getSessionBeanCobra().getUsuarioObra().getUsuId());

                if (relacionmarcologicoindicador.getIndicador().getNumcantidadvariables() == 2) {

                    planificacionrelacionmarcologicoindicador.setDoublevariable1metaesperada(relacionmarcologicoindicador.getDoublevalor1meta());
                    planificacionrelacionmarcologicoindicador.setFkUsureporta(getSessionBeanCobra().getUsuarioObra().getUsuId());
                    planificacionrelacionmarcologicoindicador.setRelacionmarcologicoindicador(relacionmarcologicoindicador);
                    planificacionrelacionmarcologicoindicador.setDatefechaplanificada(getAdministrarObraNew().getObra().getDatefecfinobra());

                } else if (relacionmarcologicoindicador.getIndicador().getNumcantidadvariables() == 1) {

                    planificacionrelacionmarcologicoindicador.setDoublevariable1metaesperada(relacionmarcologicoindicador.getDoublevalor1meta());
                    planificacionrelacionmarcologicoindicador.setFkUsureporta(getSessionBeanCobra().getUsuarioObra().getUsuId());
                    planificacionrelacionmarcologicoindicador.setRelacionmarcologicoindicador(relacionmarcologicoindicador);
                    planificacionrelacionmarcologicoindicador.setDatefechaplanificada(getAdministrarObraNew().getObra().getDatefecfinobra());

                }

                relacionmarcologicoindicador.setPlanificacionrelacionmarcologicoindicadors(new LinkedHashSet());
                relacionmarcologicoindicador.getPlanificacionrelacionmarcologicoindicadors().add(planificacionrelacionmarcologicoindicador);

                getSessionBeanCobra().getMarcoLogicoService().guardarAsociacionMarcoLogicoIndicador(relacionmarcologicoindicador);
                relacionmarcologicoindicador = new Relacionmarcologicoindicador();
                marcologico = new Marcologico();
                indicador = new Indicador();

                llenarRelacionmarcologicoindicador();

                FacesUtils.addInfoMessage("La línea base y la meta final esperada se han guardado");

                return null;

            } else {
                FacesUtils.addErrorMessage("Debe ingresar la observación de la meta");
                return null;
            }
        } else {
            FacesUtils.addErrorMessage("Debe ingresar la línea base");
            return null;
        }

    }

    public String guardarAsociacionMarcoLogicoIndicador() {
        if (relacionmarcologicoindicador.getDoublevalor1lineabase() != null) {

            relacionmarcologicoindicador.setDatefechacreacion(new Date());
            relacionmarcologicoindicador.setFkIntusureporta(getSessionBeanCobra().getUsuarioObra().getUsuId());

            List<Planificacionrelacionmarcologicoindicador> PmlInd = new ArrayList<Planificacionrelacionmarcologicoindicador>();

            if (relacionmarcologicoindicador.getIndicador().getNumcantidadvariables() == 2) {

                planificacionrelacionmarcologicoindicador.setDoublevariable1metaesperada(relacionmarcologicoindicador.getDoublevalor1meta());
                planificacionrelacionmarcologicoindicador.setFkUsureporta(getSessionBeanCobra().getUsuarioObra().getUsuId());
                planificacionrelacionmarcologicoindicador.setRelacionmarcologicoindicador(relacionmarcologicoindicador);
                planificacionrelacionmarcologicoindicador.setDatefechaplanificada(getAdministrarObraNew().getObra().getDatefecfinobra());
                PmlInd.add(planificacionrelacionmarcologicoindicador);

            } else if (relacionmarcologicoindicador.getIndicador().getNumcantidadvariables() == 1) {

                planificacionrelacionmarcologicoindicador.setDoublevariable1metaesperada(relacionmarcologicoindicador.getDoublevalor1meta());
                planificacionrelacionmarcologicoindicador.setFkUsureporta(getSessionBeanCobra().getUsuarioObra().getUsuId());
                planificacionrelacionmarcologicoindicador.setRelacionmarcologicoindicador(relacionmarcologicoindicador);
                planificacionrelacionmarcologicoindicador.setDatefechaplanificada(getAdministrarObraNew().getObra().getDatefecfinobra());
                PmlInd.add(planificacionrelacionmarcologicoindicador);

            }

            if (relacionmarcologicoindicador.getIndicador().getFrecuenciaperiocidad().getIntidfrecuenciaperiocidad() == 4) {
                List<Avanceplanificacionrelacionmarcologicoindicador> listAvancPlanRelMarlInd = new ArrayList<Avanceplanificacionrelacionmarcologicoindicador>();

                Calendar fecha = Calendar.getInstance();
                fecha.setTime(getAdministrarObraNew().getObra().getDatefeciniobra());
                while (fecha.getTime().compareTo(getAdministrarObraNew().getObra().getDatefecfinobra()) <= 0) {
                    fecha.add(Calendar.DATE, +(30));

                    Avanceplanificacionrelacionmarcologicoindicador avanceplan = new Avanceplanificacionrelacionmarcologicoindicador();

                    avanceplan.setDatefechaplanificada(fecha.getTime());
                    avanceplan.setFkUsureporta(getSessionBeanCobra().getUsuarioObra().getUsuId());
                    avanceplan.setRelacionmarcologicoindicador(relacionmarcologicoindicador);
                    if (fecha.getTime().compareTo(getAdministrarObraNew().getObra().getDatefecfinobra()) > 0) {
                        avanceplan.setDatefechaplanificada(getAdministrarObraNew().getObra().getDatefecfinobra());
                    }
                    listAvancPlanRelMarlInd.add(avanceplan);

                }
                relacionmarcologicoindicador.setAvanceplanificacionrelacionmarcologicoindicadors(new LinkedHashSet(listAvancPlanRelMarlInd));

            } else if (relacionmarcologicoindicador.getIndicador().getFrecuenciaperiocidad().getIntidfrecuenciaperiocidad() == 6) {
                List<Avanceplanificacionrelacionmarcologicoindicador> listAvancPlanRelMarlInd = new ArrayList<Avanceplanificacionrelacionmarcologicoindicador>();

                Calendar fecha = Calendar.getInstance();
                fecha.setTime(getAdministrarObraNew().getObra().getDatefeciniobra());
                while (fecha.getTime().compareTo(getAdministrarObraNew().getObra().getDatefecfinobra()) <= 0) {
                    fecha.add(Calendar.DATE, +(90));

                    Avanceplanificacionrelacionmarcologicoindicador avanceplan = new Avanceplanificacionrelacionmarcologicoindicador();

                    avanceplan.setDatefechaplanificada(fecha.getTime());
                    avanceplan.setFkUsureporta(getSessionBeanCobra().getUsuarioObra().getUsuId());
                    avanceplan.setRelacionmarcologicoindicador(relacionmarcologicoindicador);
                    if (fecha.getTime().compareTo(getAdministrarObraNew().getObra().getDatefecfinobra()) > 0) {
                        avanceplan.setDatefechaplanificada(getAdministrarObraNew().getObra().getDatefecfinobra());
                    }
                    listAvancPlanRelMarlInd.add(avanceplan);

                }
                relacionmarcologicoindicador.setAvanceplanificacionrelacionmarcologicoindicadors(new LinkedHashSet(listAvancPlanRelMarlInd));

            }

            relacionmarcologicoindicador.setPlanificacionrelacionmarcologicoindicadors(new LinkedHashSet(PmlInd));

            getSessionBeanCobra().getMarcoLogicoService().guardarAsociacionMarcoLogicoIndicador(relacionmarcologicoindicador);
            relacionmarcologicoindicador = new Relacionmarcologicoindicador();
            marcologico = new Marcologico();
            indicador = new Indicador();

            llenarRelacionmarcologicoindicador();

            FacesUtils.addInfoMessage("La linea base y meta se han guardado");

            return null;
        } else {
            FacesUtils.addErrorMessage("Debe ingresar la linea base");
            return null;
        }

    }

    public void limpiarObligaciones() {
    }

    public String reportePdf() {
        try {
            getSessionBeanCobra().setUrlAbri(Propiedad.getValor("ipserver")+ResourceBundle.getBundle("cobra.properties.BundleMarcoLogico").getString("birtpdfobligacion") + getNuevoContratoBasico().getContrato().getIntidcontrato());
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/Reportes");
        } catch (IOException ex) {
            Logger.getLogger(MarcoLogicoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String reporteExcel() {
        try {
            getSessionBeanCobra().setUrlAbri(Propiedad.getValor("ipserver")+ResourceBundle.getBundle("cobra.properties.BundleMarcoLogico").getString("birtexcelobligacion") + getNuevoContratoBasico().getContrato().getIntidcontrato());
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/Reportes");
        } catch (IOException ex) {
            Logger.getLogger(MarcoLogicoBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public String reporteMarcoLogicoPdf() {
        try {
            getSessionBeanCobra().setUrlAbri(Propiedad.getValor("ipserver")+ResourceBundle.getBundle("cobra.properties.BundleMarcoLogico").getString("birtpdfmarcologico") + getAdministrarObraNew().getObra().getIntcodigoobra());
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/Reportes");
        } catch (IOException ex) {
            Logger.getLogger(MarcoLogicoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String reporteMarcoLogicoExcel() {
        try {
            getSessionBeanCobra().setUrlAbri(Propiedad.getValor("ipserver")+ResourceBundle.getBundle("cobra.properties.BundleMarcoLogico").getString("birtexcelmarcologico") + getAdministrarObraNew().getObra().getIntcodigoobra());
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/Reportes");
        } catch (IOException ex) {
            Logger.getLogger(MarcoLogicoBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public String reporteConsolidadoMarcoLogicoExcel() {
        try {
            getSessionBeanCobra().setUrlAbri(Propiedad.getValor("ipserver")+ResourceBundle.getBundle("cobra.properties.BundleMarcoLogico").getString("birtexcelmarcologicoconsolidado") + getAdministrarObraNew().getObra().getIntcodigoobra());
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/Reportes");
        } catch (IOException ex) {
            Logger.getLogger(MarcoLogicoBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    private boolean tieneAvancesPlanificados() {
        List<Avanceplanificacionrelacionmarcologicoindicador> listAvancPlani = getSessionBeanCobra().getMarcoLogicoService().encontrarAvancePlanificadosByObra(getAdministrarObraNew().getObra().getIntcodigoobra());

        if (listAvancPlani.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    private boolean tieneAvancesPlanificados(int intIdRelacionMarcolIndicador) {
        List<Avanceplanificacionrelacionmarcologicoindicador> listAvancPlani = getSessionBeanCobra().getMarcoLogicoService().encontrarAvancePlanificadosXIdAvance(intIdRelacionMarcolIndicador);

        if (listAvancPlani.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public void calcularAvanceRelacionMarcoLogicoIndicadorByPeridicidad() {

        listMarcolInidicadorRelacionados = getSessionBeanCobra().getMarcoLogicoService().encontrarRelacionmarcologicoindicador();

//        List<Relacionmarcologicoindicador> lisrel = getSessionBeanCobra().getMarcoLogicoService().encontrarRelMarcoIndXcodigoObra(getAdministrarObraNew().getObra().getIntcodigoobra());
        for (Relacionmarcologicoindicador relac : listMarcolInidicadorRelacionados) {

            if (tieneAvancesPlanificados(relac.getIntidrelacionmarcologicoindicador()) != true) {

                int per = 1;
                switch (relac.getIndicador().getFrecuenciaperiocidad().getIntidfrecuenciaperiocidad()) {
                    case 4:
                        per = 30;
                        break;
                    case 5:
                        per = 60;
                        break;
                    case 6:
                        per = 90;
                        break;

                }

                Obra ob = getSessionBeanCobra().getCobraService().encontrarObraPorId(relac.getMarcologico().getFkIntcodigoobra());

                if (ob.getTipoestadobra().getIntestadoobra() != 0) {
                    if (ob.getDatefecfinobra() != null) {

                        Calendar fecha = Calendar.getInstance();
                        fecha.setTime(ob.getDatefeciniobra());
                        while (fecha.getTime().compareTo(ob.getDatefecfinobra()) <= 0) {
                            fecha.add(Calendar.DATE, +(per));
                            Avanceplanificacionrelacionmarcologicoindicador avanceplan = new Avanceplanificacionrelacionmarcologicoindicador();
                            avanceplan.setDatefechaplanificada(fecha.getTime());
                            avanceplan.setFkUsureporta(getSessionBeanCobra().getUsuarioObra().getUsuId());
                            avanceplan.setRelacionmarcologicoindicador(relac);
                            if (fecha.getTime().compareTo(ob.getDatefecfinobra()) > 0) {
                                avanceplan.setDatefechaplanificada(ob.getDatefecfinobra());
                            }
                            getSessionBeanCobra().getMarcoLogicoService().guardarAvancesByAsociaciones(avanceplan);
                            System.out.println("fecha planificada = " + avanceplan.getDatefechaplanificada());

                        }
                    } else {
                        System.out.println(" .: El proyecto no tiene fecha de finalización :. = ");
                    }

                } else {
                    System.out.println(" .: El proyecto no esta en ejecucción :. = ");
                }
            } else {
                System.out.println(" .: La Relacion ya tiene avances planificados :. = ");
            }
        }

    }

    public void actualizarIndicador() {
//        getAdministrarObraNew().actualizarIndicador();
    }

    public void obligacionCumplida() {
         System.out.println("obligacion.isBoolestado()1 = " + obligacion.isBoolestado());
        if (obligacion.isBoolestado() == true) {
            System.out.println("obligacion.isBoolestado()2 = " + obligacion.isBoolestado());
            obligacion.setDatefechacreacion(new Date());
            obligacion.setFkIntusumodificacion(getSessionBeanCobra().getUsuarioObra().getUsuId());
            getSessionBeanCobra().getMarcoLogicoService().guardarObligacion(obligacion, getSessionBeanCobra().getUsuarioObra().getUsuLogin());
            FacesUtils.addInfoMessage("La obligacion esta cumplida");
        } else {
            FacesUtils.addErrorMessage("Si Desea dar la obligacion por cumplida seleccione el campo");
        }

    }
     

}
