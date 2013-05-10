/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.reportes;

import chsolicitud.dao.service.SolicitudServiceAble;
import co.com.interkont.cobra.to.Cargo;
import co.com.interkont.cobra.to.Contrato;
import co.com.interkont.cobra.to.Estadodocumentacion;
import co.com.interkont.cobra.to.Estadosolicitudch;
import co.com.interkont.cobra.to.Grupo;
import co.com.interkont.cobra.to.JsfUsuario;
import co.com.interkont.cobra.to.Localidad;
import co.com.interkont.cobra.to.LogConsolidado;
import co.com.interkont.cobra.to.Region;
import co.com.interkont.cobra.to.Tercero;
import co.com.interkont.cobra.to.Tipoah;
import co.com.interkont.cobra.to.Tipoestadobra;
import co.com.interkont.cobra.to.Tipoobra;
import co.com.interkont.cobra.to.Tipoorigen;
import co.com.interkont.cobra.to.Tipoproyecto;
import co.com.interkont.cobra.to.Tiposolicitudobra;
import co.com.interkont.cobra.to.Zonaespecifica;
import cobra.SessionBeanCobra;
import cobra.Supervisor.FacesUtils;
import cobra.Supervisor.ILifeCycleAware;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

/**
 *
 * @author David Andrés Betancourth Botero
 */
public class Reporte  implements ILifeCycleAware {
    // <editor-fold defaultstate="collapsed" desc="Managed Component Definition">

    private String tiposolitxregion;
    private String tiposoliresumen;
    private String tiposolitodossolivsapro;
    private String deptosah;
    private String muniah;
    private String deptoselec = "";
    private String loc;
    private String regionint = "";
    private String nombreTerritorio = "";
    private SolicitudServiceAble solicitudService;
    private JsfUsuario usuariolog = new JsfUsuario();
    private Tipoorigen tipoOrigenUsuario;
    private ResourceBundle bundle = getSessionBeanCobra().getBundle();
    private Date fecinisolaprotipo;
    private Date fecfinsolaprotipo;
    private Date fecinireporte;
    private Date fecinitodossolivsapro;
    private Date fecfintodossolivsapro;
    private Date fecinisolapro;
    private Date fecfinsolapro;
    private Date fecinisolregi;
    private Date fecfinsolregi;
    private Date fecinisolresumen;
    private Date fecfinsolresumen;
    private Date fecinitotaprotipo;
    private Date fecfintotaprotipo;
    private Date feciniaproxtipodeobra;
    private Date fecfinaproxtipodeobra;
    private Date fecinitotsolaprotipdepvlr;
    private Date fecfinatotsolaprotipdepvlr;
    private Date fechainiconso;
    private Date fechafinconso;
    private Date fechainiconpro;
    private Date fechafinconpro;
    private Date feciniah;
    private Date fecfinah;
    private Date feciniob;
    private Date fecfinob;
    private List<Grupo> listagrupo = new ArrayList<Grupo>();
    private SelectItem[] itemsEstadoSolicitudCh;
    private SelectItem[] itemsEstadoDocumentacion;
    private SelectItem[] itemsTipoObra;
    private SelectItem[] itemsCargo;
    private SelectItem[] itemsDeptos;
    private SelectItem[] sedeptosah;
    private SelectItem[] semuniah;
    private SelectItem[] tipoestadoobra;
    private SelectItem[] entidades;
    private SelectItem[] zonaespecifica;
    private SelectItem[] tipoahintselect;
    private SelectItem[] tipoobraintselect;
    private SelectItem[] tipoproyectointselect;
    private SelectItem[] tercerointselect;
    private SelectItem[] tiposolicitudintselect;
    private SelectItem[] tipoorigenselect;
    private SelectItem[] regionselect;
    private SelectItem[] itemsMunicipios;
    private SelectItem[] itemsCortes;
    private long inttiposolicitud = 0;
    private int intentidad = 0;
    private int intestadoobra = 0;
    private int intzona = 0;
    private int inttipoobra = -1;
    private int inttipoproyecto = -1;
    private int inttercer = 0;
    private int perfil = 0;
    private int grupo = 0;
    private int vista = 0;
    private int vistaicom;
    private int reporteselec;
    private int reporteselectaten = 0;
    private int reporteselectcontrol = 0;
    private int reporteselectlinea = 0;
    private int reporteselectregionali = 0;
    private int reporteFormato = 0;
    private int tipoahint = 0;
    private int tipoobint = 0;
    private int tipoorigenint = 0;
    private int tipoorigenintob = 0;
    private int idconsolidadoselec = 0;
    private int consolida = 0;
    //Formatos disponibles de reportes.
    private static int PDF_FORMATO = 1;
    private static int XLS_FORMATO = 2;
    private static int DOC_FORMATO = 3;
    //Reportes por convenio
    private static int REPORTE_CONVENIO = 1;
    private static int REPORTE_PROYECTO_X_CONVENIO = 2;
    //Reportes ayuda humanitaria.
    private static int REPORTE_RESUMEN_SOLICITUDES_ASISTENCIA_HUMANITARIA = 1;
    private static int REPORTE_SEGUIMIENTO_ATENCION_HUMANITARIA_MUNICIPAL = 2;
    private static int REPORTE_SEGUIMIENTOS_ARRIENDOS_MUNICIPAL = 3;
    private static int REPORTE_SEGUIMIENTOS_ATENCION_HUMANITARIA_GLOBAL = 4;
    private static int REPORTE_SOLICITUDES_APROBADAS_POR_DEPARTAMENTO = 5;
    private static int REPORTE_SOLICITUDES_APROBADAS_POR_REGION = 6;
    private static int REPORTE_SOLICITUDES_PRESENTADAS_VS_APROBADAS = 7;
    private static int REPORTE_TOTAL_APROBADO = 8;
    private static int REPORTE_TOTAL_APROBADO_ATENCION_HUMANITARIA_ALOJAMIENTOS_TEMPORALES = 9;
    private static int REPORTE_TOTAL_APROBADO_ATENCION_HUMANITARIA_ALOJAMIENTOS_TEMPORALES_POR_REGION_O_LOCALIDAD = 10;
    //Regionalizacion.
    private static int REGIONALIZACION_POR_DEPARTAMENTO = 1;
    private static int REGIONALIZACION_POR_ZONA = 2;
    private static int REGIONALIZACION_POR_REGION = 3;
    private String cadenaPeticionReporte;
    private int reporteSelectConveniosEntidadesNacionales;
    private SelectItem[] _contratoConvenios;
    private int _idConvenio;

    public int getIdConvenio() {
        return _idConvenio;
    }

    public void setIdConvenio(int _idConvenio) {
        this._idConvenio = _idConvenio;
    }

    public SelectItem[] getContratoConvenios() {
        return _contratoConvenios;
    }

    public void setContratoConvenios(SelectItem[] _contratoConvenios) {
        this._contratoConvenios = _contratoConvenios;
    }

    public int getReporteSelectConveniosEntidadesNacionales() {
        return reporteSelectConveniosEntidadesNacionales;
    }

    public void setReporteSelectConveniosEntidadesNacionales(int reporteSelectConveniosEntidadesNacionales) {
        this.reporteSelectConveniosEntidadesNacionales = reporteSelectConveniosEntidadesNacionales;
    }

    public String getCadenaPeticionReporte() {
        return cadenaPeticionReporte;
    }

    public void setCadenaPeticionReporte(String cadenaPeticionReporte) {
        this.cadenaPeticionReporte = cadenaPeticionReporte;
    }

    public int getInttipoobra() {
        return inttipoobra;
    }

    public void setInttipoobra(int inttipoobra) {
        this.inttipoobra = inttipoobra;
    }

    public int getInttipoproyecto() {
        return inttipoproyecto;
    }

    public void setInttipoproyecto(int inttipoproyecto) {
        this.inttipoproyecto = inttipoproyecto;
    }

    public SelectItem[] getTipoproyectointselect() {
        return tipoproyectointselect;
    }

    public void setTipoproyectointselect(SelectItem[] tipoproyectointselect) {
        this.tipoproyectointselect = tipoproyectointselect;
    }

    public String getNombreTerritorio() {
        return nombreTerritorio;
    }

    public void setNombreTerritorio(String nombreTerritorio) {
        this.nombreTerritorio = nombreTerritorio;
    }

    public int getInttercer() {
        return inttercer;
    }

    public void setInttercer(int inttercer) {
        this.inttercer = inttercer;
    }

    public SelectItem[] getTercerointselect() {
        return tercerointselect;
    }

    public void setTercerointselect(SelectItem[] tercerointselect) {
        this.tercerointselect = tercerointselect;
    }

    public SelectItem[] getTipoobraintselect() {
        return tipoobraintselect;
    }

    public void setTipoobraintselect(SelectItem[] tipoobraintselect) {
        this.tipoobraintselect = tipoobraintselect;
    }

    public int getReporteselectregionali() {
        return reporteselectregionali;
    }

    public void setReporteselectregionali(int reporteselectregionali) {
        this.reporteselectregionali = reporteselectregionali;
    }

    public int getReporteselectlinea() {
        return reporteselectlinea;
    }

    public void setReporteselectlinea(int reporteselectlinea) {
        this.reporteselectlinea = reporteselectlinea;
    }

    public int getReporteselectcontrol() {
        return reporteselectcontrol;
    }

    public void setReporteselectcontrol(int reporteselectcontrol) {
        this.reporteselectcontrol = reporteselectcontrol;
    }

    public int getIdconsolidadoselec() {
        return idconsolidadoselec;
    }

    public void setIdconsolidadoselec(int idconsolidadoselec) {
        this.idconsolidadoselec = idconsolidadoselec;
    }

    public SelectItem[] getItemsCortes() {
        return itemsCortes;
    }

    public void setItemsCortes(SelectItem[] itemsCortes) {
        this.itemsCortes = itemsCortes;
    }

    public int getTipoorigenintob() {
        return tipoorigenintob;
    }

    public void setTipoorigenintob(int tipoorigenintob) {
        this.tipoorigenintob = tipoorigenintob;
    }

    public int getTipoobint() {
        return tipoobint;
    }

    public void setTipoobint(int tipoobint) {
        this.tipoobint = tipoobint;
    }

    public SelectItem[] getTiposolicitudintselect() {
        return tiposolicitudintselect;
    }

    public void setTiposolicitudintselect(SelectItem[] tiposolicitudintselect) {
        this.tiposolicitudintselect = tiposolicitudintselect;
    }

    public Date getFecfinob() {
        return fecfinob;
    }

    public void setFecfinob(Date fecfinob) {
        this.fecfinob = fecfinob;
    }

    public Date getFeciniob() {
        return feciniob;
    }

    public void setFeciniob(Date feciniob) {
        this.feciniob = feciniob;
    }

    public String getRegionint() {
        return regionint;
    }

    public void setRegionint(String regionint) {
        this.regionint = regionint;
    }

    public Date getFecfinaproxtipodeobra() {
        return fecfinaproxtipodeobra;
    }

    public void setFecfinaproxtipodeobra(Date fecfinaproxtipodeobra) {
        this.fecfinaproxtipodeobra = fecfinaproxtipodeobra;
    }

    public Date getFecfinatotsolaprotipdepvlr() {
        return fecfinatotsolaprotipdepvlr;
    }

    public void setFecfinatotsolaprotipdepvlr(Date fecfinatotsolaprotipdepvlr) {
        this.fecfinatotsolaprotipdepvlr = fecfinatotsolaprotipdepvlr;
    }

    public Date getFecfinsolapro() {
        return fecfinsolapro;
    }

    public void setFecfinsolapro(Date fecfinsolapro) {
        this.fecfinsolapro = fecfinsolapro;
    }

    public Date getFecfinsolaprotipo() {
        return fecfinsolaprotipo;
    }

    public void setFecfinsolaprotipo(Date fecfinsolaprotipo) {
        this.fecfinsolaprotipo = fecfinsolaprotipo;
    }

    public Date getFecfinsolregi() {
        return fecfinsolregi;
    }

    public void setFecfinsolregi(Date fecfinsolregi) {
        this.fecfinsolregi = fecfinsolregi;
    }

    public Date getFecfinsolresumen() {
        return fecfinsolresumen;
    }

    public void setFecfinsolresumen(Date fecfinsolresumen) {
        this.fecfinsolresumen = fecfinsolresumen;
    }

    public Date getFecfintodossolivsapro() {
        return fecfintodossolivsapro;
    }

    public void setFecfintodossolivsapro(Date fecfintodossolivsapro) {
        this.fecfintodossolivsapro = fecfintodossolivsapro;
    }

    public Date getFecfintotaprotipo() {
        return fecfintotaprotipo;
    }

    public void setFecfintotaprotipo(Date fecfintotaprotipo) {
        this.fecfintotaprotipo = fecfintotaprotipo;
    }

    public Date getFeciniaproxtipodeobra() {
        return feciniaproxtipodeobra;
    }

    public void setFeciniaproxtipodeobra(Date feciniaproxtipodeobra) {
        this.feciniaproxtipodeobra = feciniaproxtipodeobra;
    }

    public Date getFecinireporte() {
        return fecinireporte;
    }

    public void setFecinireporte(Date fecinireporte) {
        this.fecinireporte = fecinireporte;
    }

    public Date getFecinisolapro() {
        return fecinisolapro;
    }

    public void setFecinisolapro(Date fecinisolapro) {
        this.fecinisolapro = fecinisolapro;
    }

    public Date getFecinisolaprotipo() {
        return fecinisolaprotipo;
    }

    public void setFecinisolaprotipo(Date fecinisolaprotipo) {
        this.fecinisolaprotipo = fecinisolaprotipo;
    }

    public Date getFecinisolregi() {
        return fecinisolregi;
    }

    public void setFecinisolregi(Date fecinisolregi) {
        this.fecinisolregi = fecinisolregi;
    }

    public Date getFecinisolresumen() {
        return fecinisolresumen;
    }

    public void setFecinisolresumen(Date fecinisolresumen) {
        this.fecinisolresumen = fecinisolresumen;
    }

    public Date getFecinitodossolivsapro() {
        return fecinitodossolivsapro;
    }

    public void setFecinitodossolivsapro(Date fecinitodossolivsapro) {
        this.fecinitodossolivsapro = fecinitodossolivsapro;
    }

    public Date getFecinitotaprotipo() {
        return fecinitotaprotipo;
    }

    public void setFecinitotaprotipo(Date fecinitotaprotipo) {
        this.fecinitotaprotipo = fecinitotaprotipo;
    }

    public Date getFecinitotsolaprotipdepvlr() {
        return fecinitotsolaprotipdepvlr;
    }

    public void setFecinitotsolaprotipdepvlr(Date fecinitotsolaprotipdepvlr) {
        this.fecinitotsolaprotipdepvlr = fecinitotsolaprotipdepvlr;
    }

    public int getGrupo() {
        return grupo;
    }

    public void setGrupo(int grupo) {
        this.grupo = grupo;
    }

    public List<Grupo> getListagrupo() {
        return listagrupo;
    }

    public void setListagrupo(List<Grupo> listagrupo) {
        this.listagrupo = listagrupo;
    }

    public int getPerfil() {
        return perfil;
    }

    public void setPerfil(int perfil) {
        this.perfil = perfil;
    }

    public SolicitudServiceAble getSolicitudService() {
        return solicitudService;
    }

    public void setSolicitudService(SolicitudServiceAble solicitudService) {
        this.solicitudService = solicitudService;
    }

    public String getTiposoliresumen() {
        return tiposoliresumen;
    }

    public void setTiposoliresumen(String tiposoliresumen) {
        this.tiposoliresumen = tiposoliresumen;
    }

    public String getTiposolitodossolivsapro() {
        return tiposolitodossolivsapro;
    }

    public void setTiposolitodossolivsapro(String tiposolitodossolivsapro) {
        this.tiposolitodossolivsapro = tiposolitodossolivsapro;
    }

    public String getTiposolitxregion() {
        return tiposolitxregion;
    }

    public void setTiposolitxregion(String tiposolitxregion) {
        this.tiposolitxregion = tiposolitxregion;
    }

    public JsfUsuario getUsuariolog() {
        return usuariolog;
    }

    public void setUsuariolog(JsfUsuario usuariolog) {
        this.usuariolog = usuariolog;
    }

    public String getDeptoselec() {
        return deptoselec;
    }

    public void setDeptoselec(String deptoselec) {
        this.deptoselec = deptoselec;
    }

    public SelectItem[] getItemsCargo() {
        return itemsCargo;
    }

    public void setItemsCargo(SelectItem[] itemsCargo) {
        this.itemsCargo = itemsCargo;
    }

    public SelectItem[] getItemsDeptos() {
        return itemsDeptos;
    }

    public void setItemsDeptos(SelectItem[] itemsDeptos) {
        this.itemsDeptos = itemsDeptos;
    }

    public SelectItem[] getItemsEstadoDocumentacion() {
        return itemsEstadoDocumentacion;
    }

    public void setItemsEstadoDocumentacion(SelectItem[] itemsEstadoDocumentacion) {
        this.itemsEstadoDocumentacion = itemsEstadoDocumentacion;
    }

    public SelectItem[] getItemsEstadoSolicitudCh() {
        return itemsEstadoSolicitudCh;
    }

    public void setItemsEstadoSolicitudCh(SelectItem[] itemsEstadoSolicitudCh) {
        this.itemsEstadoSolicitudCh = itemsEstadoSolicitudCh;
    }

    public SelectItem[] getItemsMunicipios() {
        return itemsMunicipios;
    }

    public void setItemsMunicipios(SelectItem[] itemsMunicipios) {
        this.itemsMunicipios = itemsMunicipios;
    }

    public SelectItem[] getItemsTipoObra() {
        return itemsTipoObra;
    }

    public void setItemsTipoObra(SelectItem[] itemsTipoObra) {
        this.itemsTipoObra = itemsTipoObra;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public Tipoorigen getTipoOrigenUsuario() {
        return tipoOrigenUsuario;
    }

    public void setTipoOrigenUsuario(Tipoorigen tipoOrigenUsuario) {
        this.tipoOrigenUsuario = tipoOrigenUsuario;
    }

    public SelectItem[] getSedeptosah() {
        return sedeptosah;
    }

    public void setSedeptosah(SelectItem[] sedeptosah) {
        this.sedeptosah = sedeptosah;
    }

    public SelectItem[] getSemuniah() {
        return semuniah;
    }

    public void setSemuniah(SelectItem[] semuniah) {
        this.semuniah = semuniah;
    }

    public String getDeptosah() {
        return deptosah;
    }

    public void setDeptosah(String deptosah) {
        this.deptosah = deptosah;
    }

    public String getMuniah() {
        return muniah;
    }

    public void setMuniah(String muniah) {
        this.muniah = muniah;
    }

    public SelectItem[] getRegionselect() {
        return regionselect;
    }

    public void setRegionselect(SelectItem[] regionselect) {
        this.regionselect = regionselect;
    }

    public int getTipoorigenint() {
        return tipoorigenint;
    }

    public void setTipoorigenint(int tipoorigenint) {
        this.tipoorigenint = tipoorigenint;
    }

    public Date getFecfinah() {
        return fecfinah;
    }

    public void setFecfinah(Date fecfinah) {
        this.fecfinah = fecfinah;
    }

    public Date getFeciniah() {
        return feciniah;
    }

    public void setFeciniah(Date feciniah) {
        this.feciniah = feciniah;
    }

    public int getTipoahint() {
        return tipoahint;
    }

    public void setTipoahint(int tipoahint) {
        this.tipoahint = tipoahint;
    }

    public int getReporteFormato() {
        return reporteFormato;
    }

    public void setReporteFormato(int reporteFormato) {
        this.reporteFormato = reporteFormato;
    }

    public int getReporteselectaten() {
        return reporteselectaten;
    }

    public SelectItem[] getTipoorigenselect() {
        return tipoorigenselect;
    }

    public void setTipoorigenselect(SelectItem[] tipoorigenselect) {
        this.tipoorigenselect = tipoorigenselect;
    }

    public void setReporteselectaten(int reporteselectaten) {
        this.reporteselectaten = reporteselectaten;
    }

    public SelectItem[] getTipoahintselect() {
        return tipoahintselect;
    }

    public void setTipoahintselect(SelectItem[] tipoahintselect) {
        this.tipoahintselect = tipoahintselect;
    }

    public Date getFechafinconpro() {
        return fechafinconpro;
    }

    public void setFechafinconpro(Date fechafinconpro) {
        this.fechafinconpro = fechafinconpro;
    }

    public Date getFechainiconpro() {
        return fechainiconpro;
    }

    public void setFechainiconpro(Date fechainiconpro) {
        this.fechainiconpro = fechainiconpro;
    }

    public int getVistaicom() {
        return vistaicom;
    }

    public void setVistaicom(int vistaicom) {
        this.vistaicom = vistaicom;
    }

    public int getReporteselec() {
        return reporteselec;
    }

    public void setReporteselec(int reporteselec) {
        this.reporteselec = reporteselec;
    }

    public int getIntzona() {
        return intzona;
    }

    public void setIntzona(int intzona) {
        this.intzona = intzona;
    }

    public SelectItem[] getZonaespecifica() {
        return zonaespecifica;
    }

    public void setZonaespecifica(SelectItem[] zonaespecifica) {
        this.zonaespecifica = zonaespecifica;
    }

    public int getVista() {
        return vista;
    }

    public void setVista(int vista) {
        this.vista = vista;
    }

    public Date getFechafinconso() {
        return fechafinconso;
    }

    public void setFechafinconso(Date fechafinconso) {
        this.fechafinconso = fechafinconso;
    }

    public Date getFechainiconso() {
        return fechainiconso;
    }

    public void setFechainiconso(Date fechainiconso) {
        this.fechainiconso = fechainiconso;
    }

    public long getInttiposolicitud() {
        return inttiposolicitud;
    }

    public void setInttiposolicitud(long inttiposolicitud) {
        this.inttiposolicitud = inttiposolicitud;
    }

    public int getIntentidad() {
        return intentidad;
    }

    public void setIntentidad(int intentidad) {
        this.intentidad = intentidad;
    }

    public int getIntestadoobra() {
        return intestadoobra;
    }

    public void setIntestadoobra(int intestadoobra) {
        this.intestadoobra = intestadoobra;
    }

    public SelectItem[] getEntidades() {
        return entidades;
    }

    public void setEntidades(SelectItem[] entidades) {
        this.entidades = entidades;
    }

    public SelectItem[] getTipoestadoobra() {
        return tipoestadoobra;
    }

    public void setTipoestadoobra(SelectItem[] tipoestadoobra) {
        this.tipoestadoobra = tipoestadoobra;
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public void inicializarAh() {
        llenarTipoah();
        llenarTipoOrigen();
        setTipoahint(0);
        setTipoorigenint(4);
    }

    public void inicializarConvenio() {
        llenarCortes();
    }

    public void llenarxTipoOrien() {
        switch (getTipoorigenint()) {
            case 3:
                llenarRegiones();
                break;
        }
    }

//    public void llenarxTipoOrienOb() {
//        switch (getTipoorigenintob()) {
//            case 3:
//                llenarRegiones();
//                break;
//        }
//    }
    public String reporteatencion() {
        String nombrereport = "";
        switch (getReporteselectaten()) {
            case 4:
                nombrereport = bundle.getString("reporteah") + "ah_ressoltipobr." + bundle.getString("reportformatpdf");
                break;
            case 5:
                nombrereport = bundle.getString("reporteah") + "ah_solprevsaprtipobr." + bundle.getString("reportformatpdf");
                break;
            case 6:
                nombrereport = bundle.getString("reporteah") + "ah_totaprcon." + bundle.getString("reportformatpdf");
                break;
            case 7:
                nombrereport = bundle.getString("reporteah") + "ah_totaprreg." + bundle.getString("reportformatpdf");
                break;
            case 8:
                nombrereport = bundle.getString("reporteah") + "ah_totaprtipobr." + bundle.getString("reportformatpdf");
                break;
            case 10:
                nombrereport = bundle.getString("reporteah") + "ah_totaprtipobrreg." + bundle.getString("reportformatpdf");
                break;
            case 11:
                nombrereport = bundle.getString("reporteah") + "ah_totaprtipsol." + bundle.getString("reportformatpdf");
                break;
        }
        if (getReporteselectaten() == 4 || getReporteselectaten() == 5 || getReporteselectaten() == 8 || getReporteselectaten() == 10) {
            try {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                if (getTipoahint() == -1) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(nombrereport
                            + "&fechaini=" + URLEncoder.encode(df.format(feciniah)) + "&fechafin=" + URLEncoder.encode(df.format(fecfinah)));
                } else {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(nombrereport + "&TipoAtencion=" + getTipoahint()
                            + "&fechaini=" + URLEncoder.encode(df.format(feciniah)) + "&fechafin=" + URLEncoder.encode(df.format(fecfinah)));
                }
            } catch (IOException ex) {
                Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (getReporteselectaten() == 7 || getReporteselectaten() == 11) {
            try {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                switch (getTipoorigenint()) {
                    case 1:
                        FacesContext.getCurrentInstance().getExternalContext().redirect(nombrereport + "&Localidad=" + getMuniah()
                                + "&fechaini=" + URLEncoder.encode(df.format(feciniah)) + "&fechafin=" + URLEncoder.encode(df.format(fecfinah)));
                        break;
                    case 2:
                        FacesContext.getCurrentInstance().getExternalContext().redirect(nombrereport + "&Localidad=" + getDeptosah()
                                + "&fechaini=" + URLEncoder.encode(df.format(feciniah)) + "&fechafin=" + URLEncoder.encode(df.format(fecfinah)));
                        break;
                    case 3:
                        FacesContext.getCurrentInstance().getExternalContext().redirect(nombrereport + "&Region=" + getRegionint()
                                + "&fechaini=" + URLEncoder.encode(df.format(feciniah)) + "&fechafin=" + URLEncoder.encode(df.format(fecfinah)));
                        break;
                    case 4:
                        FacesContext.getCurrentInstance().getExternalContext().redirect(nombrereport + "&Localidad=169"
                                + "&fechaini=" + URLEncoder.encode(df.format(feciniah)) + "&fechafin=" + URLEncoder.encode(df.format(fecfinah)));
                        break;
                }
            } catch (IOException ex) {
                Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (getReporteselectaten() == 6) {
            try {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                FacesContext.getCurrentInstance().getExternalContext().redirect(nombrereport
                        + "&fechaini=" + URLEncoder.encode(df.format(feciniah)) + "&fechafin=" + URLEncoder.encode(df.format(fecfinah)));
            } catch (IOException ex) {
                Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public String llenarTipoah() {
        getSessionBeanCobra().getCobraService().setListaTipoah(getSessionBeanCobra().getCobraService().encontrarTipoah());
        tipoahintselect = new SelectItem[getSessionBeanCobra().getCobraService().getListaTipoah().size()];
        int i = 0;
        for (Tipoah clase : getSessionBeanCobra().getCobraService().getListaTipoah()) {
            SelectItem clas = new SelectItem(clase.getOidcodigotipoah(), clase.getStrdescripcion());
            tipoahintselect[i++] = clas;
        }
        return null;
    }

    public String llenarTipoSolicitud() {
        getSessionBeanCobra().getCobraService().setListaTiposolicitud(getSessionBeanCobra().getCobraService().encontrarTiposolicitud());
        tiposolicitudintselect = new SelectItem[getSessionBeanCobra().getCobraService().getListaTiposolicitud().size()];
        int i = 0;
        for (Tiposolicitudobra clase : getSessionBeanCobra().getCobraService().getListaTiposolicitud()) {
            SelectItem clas = new SelectItem(clase.getInttiposolicitud(), clase.getStrdescripcion());
            if (i == 0) {
                tipoobint = -1;
            }
            tiposolicitudintselect[i++] = clas;
        }
        return null;
    }

    public String llenarTipoOrigen() {
        getSessionBeanCobra().getCobraService().setListaTipoOrigen(getSessionBeanCobra().getCobraService().encontrarTiposOrigenes());
        tipoorigenselect = new SelectItem[getSessionBeanCobra().getCobraService().getListaTipoOrigen().size()];
        int i = 0;
        for (Tipoorigen clase : getSessionBeanCobra().getCobraService().getListaTipoOrigen()) {
            SelectItem clas = new SelectItem(clase.getIntidtipoorigen(), clase.getStrnombre());
            tipoorigenselect[i++] = clas;
        }
        return null;
    }

    public String llenarRegiones() {
        getSessionBeanCobra().getCobraService().setListaregion(getSessionBeanCobra().getCobraService().encontrarRegion());
        regionselect = new SelectItem[getSessionBeanCobra().getCobraService().getListaregion().size()];
        int i = 0;
        for (Region clase : getSessionBeanCobra().getCobraService().getListaregion()) {
            SelectItem clas = new SelectItem(clase.getStrnombre(), clase.getStrnombre());
            if (i == 0) {
                regionint = clase.getStrnombre();
            }
            regionselect[i++] = clas;
        }
        return null;
    }

    public String llenarDeptosAh() {
        getSessionBeanCobra().getCobraService().setListadeptosah(getSessionBeanCobra().getCobraService().encontrarDepartamentos());
        sedeptosah = new SelectItem[getSessionBeanCobra().getCobraService().getListadeptosah().size()];
        int i = 0;
        for (Localidad dept : getSessionBeanCobra().getCobraService().getListadeptosah()) {
            SelectItem dep = new SelectItem(dept.getStrcodigolocalidad(), dept.getStrdepartamento());
            if (i == 0) {
                deptosah = "-1";
            }
            sedeptosah[i++] = dep;
        }
        return null;
    }

    public String llenarMuniciAh() {
        getSessionBeanCobra().getCobraService().setListamuniciah(getSessionBeanCobra().getCobraService().encontrarMunicipios(deptosah));
        semuniah = new SelectItem[getSessionBeanCobra().getCobraService().getListamuniciah().size()];
        int i = 0;
        for (Localidad munic : getSessionBeanCobra().getCobraService().getListamuniciah()) {
            SelectItem mun = new SelectItem(munic.getStrcodigolocalidad(), munic.getStrmunicipio());
            if (i == 0) {
                muniah = "-1";
            }
            semuniah[i++] = mun;
        }
        return null;
    }

    public Reporte() {

        vista = 0;
        vistaicom = 0;
        consolida = 1;
        inicializarVariables();
        reporteSelectRegionalizacion();
    }

    @Override
    public void prender() {
        vista = 0;
        vistaicom = 0;
        consolida = 1;
        reporteselec = 1;
    }

    public void llenadodeCombosReport() {
        llenarTipoEstadoObra();
        llenarZonaEspecifica();
    }

    public String reportePdf() {
        try {
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

            if (getFechainiconpro() != null || getFechafinconpro() != null) {
                if (getFechafinconpro() != null) {
                    if (getFechainiconpro() != null) {
                        if (getIntestadoobra() != -1 || getIntentidad() != -1) {
                            if (getIntentidad() != -1) {
                                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdftotal")
                                        + "&entidad=" + intentidad + "&localidad=169&fecha_inicio=" + URLEncoder.encode(df.format(fechainiconpro)) + "&fecha_fin=" + URLEncoder.encode(df.format(fechafinconpro)));
                            } else {
                                if (getIntestadoobra() != -1) {
                                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdftotal")
                                            + "&estado_obra=" + intestadoobra + "&localidad=169&fecha_inicio=" + URLEncoder.encode(df.format(fechainiconpro)) + "&fecha_fin=" + URLEncoder.encode(df.format(fechafinconpro)));
                                } else {
                                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdftotal")
                                            + "&estado_obra=" + intestadoobra + "&entidad=" + intentidad + "&localidad=169&fecha_inicio="
                                            + URLEncoder.encode(df.format(fechainiconpro)) + "&fecha_fin=" + URLEncoder.encode(df.format(fechafinconpro)));
                                }
                            }
                        } else {
                            FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdftotal")
                                    + "&localidad=169&fecha_inicio=" + URLEncoder.encode(df.format(fechainiconpro)) + "&fecha_fin=" + URLEncoder.encode(df.format(fechafinconpro)));
                        }
                    } else {
                        FacesUtils.addErrorMessage(bundle.getString("lafechainicialesuncampoobli"));
                    }
                } else {
                    FacesUtils.addErrorMessage(bundle.getString("lafechafinalesuncampoobli"));
                }
            } else {
                if (getIntestadoobra() != -1 || getIntentidad() != -1) {
                    if (getIntentidad() != -1) {
                        FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdftotal")
                                + "&entidad=" + intentidad + "&localidad=169&fecha_inicio=1996-01-01&fecha_fin=3000-01-01");
                    } else {
                        if (getIntestadoobra() != -1) {
                            FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdftotal")
                                    + "&estado_obra=" + intestadoobra + "&localidad=169&fecha_inicio=1996-01-01&fecha_fin=3000-01-01");
                        } else {
                            FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdftotal")
                                    + "&estado_obra=" + intestadoobra + "&entidad=" + intentidad + "&localidad=169&fecha_inicio=1996-01-01&fecha_fin=3000-01-01");
                        }
                    }
                } else {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdftotal")
                            + "&localidad=169&fecha_inicio=1996-01-01&fecha_fin=3000-01-01");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String reporteExcel() {
        try {
            if (getFechainiconpro() != null || getFechafinconpro() != null) {
                if (getFechafinconpro() != null) {
                    if (getFechainiconpro() != null) {
                        if (getIntestadoobra() != -1 || getIntentidad() != -1) {
                            if (getIntentidad() != -1) {
                                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteexceltotal")
                                        + "&entidad=" + intentidad + "&localidad=169&fecha_inicio=" + fechainiconpro + "&fecha_fin=" + fechafinconpro);
                            } else {
                                if (getIntestadoobra() != -1) {
                                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteexceltotal")
                                            + "&estado_obra=" + intestadoobra + "&localidad=169&fecha_inicio=" + fechainiconpro + "&fecha_fin=" + fechafinconpro);
                                } else {
                                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteexceltotal")
                                            + "&estado_obra=" + intestadoobra + "&entidad=" + intentidad + "&localidad=169&fecha_inicio=" + fechainiconpro + "&fecha_fin=" + fechafinconpro);
                                }
                            }
                        } else {
                            FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteexceltotal")
                                    + "&localidad=169&fecha_inicio=" + fechainiconpro + "&fecha_fin=" + fechafinconpro);
                        }
                    } else {
                        FacesUtils.addErrorMessage(bundle.getString("lafechainicialesuncampoobli"));
                    }
                } else {
                    FacesUtils.addErrorMessage(bundle.getString("lafechafinalesuncampoobli"));
                }
            } else {
                if (getIntestadoobra() != -1 || getIntentidad() != -1) {
                    if (getIntentidad() != -1) {
                        FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteexceltotal")
                                + "&entidad=" + intentidad + "&localidad=169&fecha_inicio=1996-01-01&fecha_fin=3000-01-01");
                    } else {
                        if (getIntestadoobra() != -1) {
                            FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteexceltotal")
                                    + "&estado_obra=" + intestadoobra + "&localidad=169&fecha_inicio=1996-01-01&fecha_fin=3000-01-01");
                        } else {
                            FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteexceltotal")
                                    + "&estado_obra=" + intestadoobra + "&entidad=" + intentidad + "&localidad=169&fecha_inicio=1996-01-01&fecha_fin=3000-01-01");
                        }
                    }
                } else {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteexceltotal")
                            + "&localidad=169&fecha_inicio=1996-01-01&fecha_fin=3000-01-01");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String reporteWord() {
        try {
            if (getFechainiconpro() != null || getFechafinconpro() != null) {
                if (getFechafinconpro() != null) {
                    if (getFechainiconpro() != null) {
                        if (getIntestadoobra() != -1 || getIntentidad() != -1) {
                            if (getIntentidad() != -1) {
                                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportewordtotal")
                                        + "&entidad=" + intentidad + "&localidad=169&fecha_inicio=" + fechainiconpro + "&fecha_fin=" + fechafinconpro);
                            } else {
                                if (getIntestadoobra() != -1) {
                                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportewordtotal")
                                            + "&estado_obra=" + intestadoobra + "&localidad=169&fecha_inicio=" + fechainiconpro + "&fecha_fin=" + fechafinconpro);
                                } else {
                                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportewordtotal")
                                            + "&estado_obra=" + intestadoobra + "&entidad=" + intentidad + "&localidad=169&fecha_inicio=" + fechainiconpro + "&fecha_fin=" + fechafinconpro);
                                }
                            }
                        } else {
                            FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportewordtotal")
                                    + "&localidad=169&fecha_inicio=" + fechainiconpro + "&fecha_fin=" + fechafinconpro);
                        }
                    } else {
                        FacesUtils.addErrorMessage(bundle.getString("lafechainicialesuncampoobli"));
                    }
                } else {
                    FacesUtils.addErrorMessage(bundle.getString("lafechafinalesuncampoobli"));
                }
            } else {
                if (getIntestadoobra() != -1 || getIntentidad() != -1) {
                    if (getIntentidad() != -1) {
                        FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportewordtotal")
                                + "&entidad=" + intentidad + "&localidad=169&fecha_inicio=1996-01-01&fecha_fin=3000-01-01");
                    } else {
                        if (getIntestadoobra() != -1) {
                            FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportewordtotal")
                                    + "&estado_obra=" + intestadoobra + "&localidad=169&fecha_inicio=1996-01-01&fecha_fin=3000-01-01");
                        } else {
                            FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportewordtotal")
                                    + "&estado_obra=" + intestadoobra + "&entidad=" + intentidad + "&localidad=169&fecha_inicio=1996-01-01&fecha_fin=3000-01-01");
                        }
                    }
                } else {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportewordtotal")
                            + "&localidad=169&fecha_inicio=1996-01-01&fecha_fin=3000-01-01");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String reportePdfConsoSoli() {
        try {
            if (getFechafinconso() != null || getFechainiconso() != null) {
                if (getFechafinconso() != null) {
                    if (getFechainiconso() != null) {
                        FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdftotalSolicitud") + "&fechaini=" + fechainiconso + "&fechafin=" + fechafinconso);
                    } else {
                        FacesUtils.addErrorMessage(bundle.getString("lafechainicialesuncampoobli"));
                    }
                } else {
                    FacesUtils.addErrorMessage(bundle.getString("lafechafinalesuncampoobli"));
                }
            } else {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdftotalSolicitud") + "&fechaini=1996-01-01&fechafin=3000-01-01");
            }
        } catch (IOException ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String reporteExcelConsoSoli() {
        try {
            if (getFechafinconso() != null || getFechainiconso() != null) {
                if (getFechafinconso() != null) {
                    if (getFechainiconso() != null) {
                        FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteexceltotalSolicitud") + "&fechaini=" + fechainiconso + "&fechafin=" + fechafinconso);
                    } else {
                        FacesUtils.addErrorMessage(bundle.getString("lafechainicialesuncampoobli"));
                    }
                } else {
                    FacesUtils.addErrorMessage(bundle.getString("lafechafinalesuncampoobli"));
                }
            } else {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteexceltotalSolicitud") + "&fechaini=1996-01-01&fechafin=3000-01-01");
            }
        } catch (IOException ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String reporteWordConsoSoli() {
        try {
            if (getFechafinconso() != null || getFechainiconso() != null) {
                if (getFechafinconso() != null) {
                    if (getFechainiconso() != null) {
                        FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportewordtotalSolicitud") + "&fechaini=" + fechainiconso + "&fechafin=" + fechafinconso);
                    } else {
                        FacesUtils.addErrorMessage(bundle.getString("lafechainicialesuncampoobli"));
                    }
                } else {
                    FacesUtils.addErrorMessage(bundle.getString("lafechafinalesuncampoobli"));
                }
            } else {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportewordtotalSolicitud") + "&fechaini=1996-01-01&fechafin=3000-01-01");
            }
        } catch (IOException ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String reportepdfsolicitud() {
        try {
            String cadena = "";

            switch (getReporteselectregionali()) {
                case 1:
                    if (getDeptosah().equals("-1")) {
                        cadena = "&localidad=169";
                    } else {
                        if (getMuniah().equals("-1")) {
                            cadena = "&localidad=" + getDeptosah();
                        } else {
                            cadena = "&localidad=" + getMuniah();
                        }
                    }
                    break;
                case 2:
                    cadena = "&zona=" + getIntzona();
                    break;
            }


            if (tipoobint != -1) {
                cadena = cadena + "&claseobra=" + tipoobint;
            }


            if (getReporteselectlinea() == 2) {

                if (inttipoobra != -1) {
                    cadena = cadena + "&tipoobra=" + inttipoobra;
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdfsolicitud") + cadena);
                } else {
                    FacesUtils.addErrorMessage(bundle.getString("seleccionar") + " " + bundle.getString("tipoobraob"));
                }
            }

            FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdfsolicitud") + cadena);

        } catch (IOException ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public String reportePdfFondo() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdftotalSolicitud") + "&fechaini=1996-01-01&fechafin=3000-01-01");

        } catch (IOException ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String reporteExcelFondo() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteexceltotalSolicitud") + "&fechaini=1996-01-01&fechafin=3000-01-01");

        } catch (IOException ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String reporteWordFondo() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportewordtotalSolicitud") + "&fechaini=1996-01-01&fechafin=3000-01-01");

        } catch (IOException ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String reportePdfFicha() {
        try {
            if (vista == 0) {
                if (deptosah.equals("-1")) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdftotalfichasinobras") + "&munici=169");
                } else {
                    if (muniah.equals("-1")) {
                        FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdftotalfichasinobras") + "&munici=" + deptosah);
                    } else {
                        FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdftotalfichasinobras") + "&munici=" + muniah);
                    }
                }
            } else {

                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdftotalfichasinobras") + "&zona=" + intzona);

            }
        } catch (IOException ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String getHeadercifras() {
        String tempo = "";
        switch (reporteselec) {
            case 1:
                tempo = bundle.getString("cifras1");
                break;
            case 2:
                tempo = bundle.getString("cifras2");
                break;
            case 3:
                tempo = bundle.getString("cifras3");
                break;
        }
        return tempo;
    }

    public String getHeaderReportes() {
        String tempo = "";
        switch (consolida) {
            case 0:
                tempo = bundle.getString("reportes");
                break;
            case 1:
                tempo = bundle.getString("header1");
                break;
            case 2:
                tempo = bundle.getString("header2");
                break;
            case 3:
                tempo = bundle.getString("header3");
                break;
            case 4:
                tempo = bundle.getString("header4");
                break;
            case 5:
                tempo = bundle.getString("header5");
                break;
        }
        return tempo;
    }

    public int getConsolida() {
        return consolida;
    }

    public void setConsolida(int consolida) {
        this.consolida = consolida;
    }

    public void inicializarVariables() {
        reporteselec = 1;
        reporteselectaten = 0;
        reporteselectcontrol = 0;
        reporteselectregionali = 1;
        reporteselectlinea = 1;
        reporteSelectConveniosEntidadesNacionales = 1;
    }

    public String reporteSelectAh() {
        inicializarVariables();
        consolida = 2;
        return null;
    }

    public String reporteSelectEt() {
        inicializarVariables();
        consolida = 3;
        return null;
    }

    public String reporteSelectEn() {
        inicializarVariables();
        consolida = 4;
        return null;
    }

    public String llenarTipoObraxTipoProyecto() {

        getSessionBeanCobra().getCobraService().setListaTipoObra(getSessionBeanCobra().getCobraService().encontrarSubTiposProyectoxtipoproyecto(inttipoproyecto));
        tipoobraintselect = new SelectItem[getSessionBeanCobra().getCobraService().getListaTipoObra().size()];
        int i = 0;
        for (Tipoobra tipoOb : getSessionBeanCobra().getCobraService().getListaTipoObra()) {
            SelectItem tipo = new SelectItem(tipoOb.getInttipoobra(), tipoOb.getStrdesctipoobra());
            if (i == 0) {
                inttipoobra = -1;
            }
            tipoobraintselect[i++] = tipo;
        }
        return null;
    }

    public String llenarTipoProyecto() {
        getSessionBeanCobra().getCobraService().setListatipoproyecto(getSessionBeanCobra().getCobraService().encontrarTiposProyectoAH());
        tipoproyectointselect = new SelectItem[getSessionBeanCobra().getCobraService().getListatipoproyecto().size()];
        int i = 0;
        for (Tipoproyecto tipoOb : getSessionBeanCobra().getCobraService().getListatipoproyecto()) {
            SelectItem tipo = new SelectItem(tipoOb.getIntidtipoproyecto(), tipoOb.getStrnombre());
            if (i == 0) {
                inttipoproyecto = -1;
            }
            tipoproyectointselect[i++] = tipo;
        }
        return null;
    }

    public String llenarEntidades(int inttiposolicitante) {
        getSessionBeanCobra().getCobraService().setListaEntidades(getSessionBeanCobra().getCobraService().encontrarTercerosxTiposolicitante(inttiposolicitante));
        tercerointselect = new SelectItem[getSessionBeanCobra().getCobraService().getListaEntidades().size()];
        int i = 0;
        for (Tercero tercer : getSessionBeanCobra().getCobraService().getListaEntidades()) {
            SelectItem ter = new SelectItem(tercer.getIntcodigo(), tercer.getStrnombre());
            if (i == 0) {
                inttercer = tercer.getIntcodigo();
            }
            tercerointselect[i++] = ter;
        }
        return null;
    }

//    public String llenarEntidades() {
//        getSessionBeanCobra().getCobraService().setEntidades(getSessionBeanCobra().getCobraService().encontrarEntidades());
//        entidades = new SelectItem[getSessionBeanCobra().getCobraService().getEntidades().size()];
//        int i = 0;
//        for (Tercero entidad : getSessionBeanCobra().getCobraService().getEntidades()) {
//            SelectItem entid = new SelectItem(entidad.getIntcodigo(), entidad.getStrnombre().toUpperCase());
//            entidades[i++] = entid;
//        }
//        return null;
//    }
    public String reporteSelectDi() {

        inicializarVariables();
        reporteSelectLinea();
        consolida = 5;
        return null;
    }

    public String reporteSelect() {
        inicializarVariables();
        consolida = 1;
        return null;
    }

    public void reporteSelectLinea() {
        switch (reporteselectlinea) {
            case 1:
                inicializarAh();
                reporteselectaten = 1; //para que inicie en la primera por defecto
                reporteselectcontrol = 0;
                break;
            case 2:
                reporteselectaten = 0;
                reporteselectcontrol = 0;
                llenarTipoSolicitud();
                llenarTipoProyecto();
                llenarTipoObraxTipoProyecto();
                break;
            case 3:
                llenarEntidades(2);
                llenarConveniosXEntidad();
                reporteselectaten = 0;
                reporteselectcontrol = 0;
                break;
        }
    }

    public void reporteSelectRegionalizacion() {
        switch (reporteselectregionali) {
            case 1:
                llenarDeptosAh();
                llenarMuniciAh();
                break;
            case 2:
                llenarZonaEspecifica();
                break;
            case 3:
                llenarRegiones();
                break;
        }
    }

    public String reportePdfFichaCorto() {
        try {
            if (reporteselectregionali == 1) {
                if (deptosah.equals("-1")) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdftotalfichacorto") + "&munici=169");
                } else {


                    if (muniah.equals("-1")) {
                        FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdftotalfichacorto") + "&munici=" + deptosah);
                    } else {
                        FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdftotalfichacorto") + "&munici=" + muniah);
                    }
                }
            } else {
                if (intzona == -1) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdftotalfichacorto") + "&munici=169");
                } else {

                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdftotalfichacortozona") + "&zona=" + intzona);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String reportePdfFichaObras() {
        try {

            if (reporteselectregionali == 1) {
                if (deptosah.equals("-1")) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdftotalfichaobras") + "&localidad=169");//select
                } else {
                    if (muniah.equals("-1")) {
                        FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdftotalfichaobras") + "&localidad=" + deptosah);//select
                    } else {
                        FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdftotalfichaobras") + "&localidad=" + muniah);//select
                    }
                }
            } else {
                if (intzona == -1) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdftotalfichaobras") + "&localidad=169");
                } else {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdftotalfichaobras") + "&zona=" + intzona);//select
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String reportePdfFichaConvenio() {
        try {
            if (reporteselectregionali == 1) {
                if (deptosah.equals("-1")) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdftotalfichaconvenio") + "&localidad=169");
                } else {
                    if (muniah.equals("-1")) {
                        FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdftotalfichaconvenio") + "&localidad=" + deptosah);
                    } else {
                        FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdftotalfichaconvenio") + "&localidad=" + muniah);
                    }
                }
            } else {
                if (intzona == -1) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdftotalfichaconvenio") + "&localidad=169");
                } else {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdftotalfichaconvenio") + "&zona=" + intzona);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String reporteExcelFicha() {
        try {
            if (vista == 0) {
                if (deptosah.equals("-1")) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteexceltotalfichasinobras") + "&munici=169");
                } else {
                    if (muniah.equals("-1")) {
                        FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteexceltotalfichasinobras") + "&munici=" + deptosah);
                    } else {
                        FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteexceltotalfichasinobras") + "&munici=" + muniah);
                    }
                }
            } else {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteexceltotalfichasinobras") + "&zona=" + intzona);
            }
        } catch (IOException ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String reporteWordFicha() {
        try {
            if (vista == 0) {
                if (deptosah.equals("-1")) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportewordtotalfichasinobras") + "&munici=169");
                } else {
                    if (muniah.equals("-1")) {
                        FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportewordtotalfichasinobras") + "&munici=" + deptosah);
                    } else {
                        FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportewordtotalfichasinobras") + "&munici=" + muniah);
                    }
                }
            } else {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportewordtotalfichasinobras") + "&zona=" + intzona);
            }
        } catch (IOException ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String reportePdfImcompletos() {
        try {
            if (vistaicom == 0) {
                if (deptosah.equals("-1")) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdfimcompletos") + "&munici=169");
                } else {
                    if (muniah.equals("-1")) {
                        FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdfimcompletos") + "&munici=" + deptosah);
                    } else {
                        FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdfimcompletos") + "&munici=" + muniah);
                    }
                }
            } else {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdfimcompletos") + "&zona=" + intzona);
            }
        } catch (IOException ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String reporteExcelImcompletos() {
        try {
            if (vistaicom == 0) {
                if (deptosah.equals("-1")) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteexcelimcompletos") + "&munici=169");
                } else {
                    if (muniah.equals("-1")) {
                        FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteexcelimcompletos") + "&munici=" + deptosah);
                    } else {
                        FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteexcelimcompletos") + "&munici=" + muniah);
                    }
                }
            } else {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteexcelimcompletos") + "&zona=" + intzona);
            }
        } catch (IOException ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String reporteWordImcompletos() {
        try {
            if (vistaicom == 0) {
                if (deptosah.equals("-1")) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportewordimcompletos") + "&munici=169");
                } else {
                    if (muniah.equals("-1")) {
                        FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportewordimcompletos") + "&munici=" + deptosah);
                    } else {
                        FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportewordimcompletos") + "&munici=" + muniah);
                    }
                }
            } else {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportewordimcompletos") + "&zona=" + intzona);
            }
        } catch (IOException ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String llenarTipoEstadoObra() {
        getSessionBeanCobra().getCobraService().setTipoestadobras(getSessionBeanCobra().getCobraService().encontrarEstadosObras());
        tipoestadoobra = new SelectItem[getSessionBeanCobra().getCobraService().getTipoestadobras().size()];
        int i = 0;
        for (Tipoestadobra tipoesta : getSessionBeanCobra().getCobraService().getTipoestadobras()) {
            SelectItem tipoes = new SelectItem(tipoesta.getIntestadoobra(), tipoesta.getStrdesctipoestado());
            tipoestadoobra[i++] = tipoes;
        }
        return null;
    }

    public String llenarZonaEspecifica() {
        getSessionBeanCobra().getCobraService().setListazonaespecificas(getSessionBeanCobra().getCobraService().encontrarZonaespecifica());
        zonaespecifica = new SelectItem[getSessionBeanCobra().getCobraService().getListazonaespecificas().size()];
        int i = 0;
        for (Zonaespecifica zona : getSessionBeanCobra().getCobraService().getListazonaespecificas()) {
            SelectItem zon = new SelectItem(zona.getIntidzonaespecifica(), zona.getStrdescripcionzona().toUpperCase());
            if (i == 0) {
                intzona = -1;
            }
            zonaespecifica[i++] = zon;
        }
        return null;
    }

    public void llenarListaEstadoSolicitudCh() {
        List<Estadosolicitudch> lista = solicitudService.getLista(Estadosolicitudch.class, "oidestadosolicitud");
        itemsEstadoSolicitudCh = new SelectItem[lista.size()];
        int i = 0;
        while (i < lista.size()) {
            SelectItem opt = new SelectItem(lista.get(i).getOidestadosolicitud(), lista.get(i).getStrnombreestadosolicitud());
            itemsEstadoSolicitudCh[i] = opt;

            i++;
        }
    }

    public void llenarListaEstadoDocumentacion() {
        List<Estadodocumentacion> lista = solicitudService.getLista(Estadodocumentacion.class, "intestadodocumentacion");// Tipodocumentacion.class
        itemsEstadoDocumentacion = new SelectItem[lista.size()];
        int i = 0;
        while (i < lista.size()) {
            SelectItem opt = new SelectItem(lista.get(i).getIntestadodocumentacion(), lista.get(i).getStrdescripcion());
            itemsEstadoDocumentacion[i] = opt;

            i++;
        }

    }

    public void llenarListaCargos() {
        List<Cargo> lista = solicitudService.getLista(Cargo.class, "strdescripcion");
        itemsCargo = new SelectItem[lista.size()];
        int i = 0;
        while (i < lista.size()) {
            SelectItem opt = new SelectItem(lista.get(i).getIntcargo(), lista.get(i).getStrdescripcion());
            itemsCargo[i] = opt;
            i++;
        }
    }

    public void llenarDeptos() {
        List<Localidad> lista = solicitudService.filtrolocalidad(2);
        itemsDeptos = new SelectItem[lista.size()];
        int i = 0;
        while (i < lista.size()) {
            SelectItem opt = new SelectItem(lista.get(i).getStrcodigolocalidad(), lista.get(i).getStrdepartamento());
            itemsDeptos[i] = opt;
            if (i == 0) {
                deptoselec = lista.get(i).getStrcodigolocalidad();
            }
            i++;
        }
    }

    public String llenarMunicipios() {
        List<Localidad> lista = solicitudService.filtroBuscarMunicipios(deptoselec);

        itemsMunicipios = new SelectItem[lista.size()];
        int i = 0;
        while (i < lista.size()) {
            SelectItem opt = new SelectItem(lista.get(i).getStrcodigolocalidad(), lista.get(i).getStrmunicipio());
            itemsMunicipios[i] = opt;

            i++;
        }
        return null;
    }

    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }

    public String habilitarPerfil() {
        perfil = 0;
        listagrupo = solicitudService.encontrarGrupoUsuario(getSessionBeanCobra().getUsuarioObra().getUsuId());
        Iterator iter = listagrupo.iterator();
        while (iter.hasNext()) {
            Grupo grupo = (Grupo) iter.next();
            switch (grupo.getGruGid()) {
                case 1:
                    perfil = 1;
                    break;
                case 2:
                    perfil = 2;
                case 12:
                    perfil = 12;
                    break;
                case 13:
                    perfil = 13;
                    break;
            }
        }
        return null;
    }

    public void llenarCortes() {
        List<LogConsolidado> listacortes = getSessionBeanCobra().getIndicadorService().obtenerLogConsolidados();
        int i = 0;
        itemsCortes = new SelectItem[listacortes.size()];
        while (i < listacortes.size()) {
            SelectItem opt = new SelectItem(listacortes.get(i).getIdconsolidado(), "Consolidado " + listacortes.get(i).getIdconsolidado() + " - " + listacortes.get(i).getFechaCorte());
            if (i == 0) {
                idconsolidadoselec = listacortes.get(i).getIdconsolidado();
            }
            itemsCortes[i] = opt;
            i++;
        }

    }

    public String reportepdfcontrol1() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdfcontrol1") + idconsolidadoselec);
        } catch (IOException ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String reportepdfcontrol2() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdfcontrol2") + idconsolidadoselec);
        } catch (IOException ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String reportepdfcontrol3() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdfcontrol3") + idconsolidadoselec);
        } catch (IOException ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String reportepdfconvenio() {
        try {
            switch (reporteselectcontrol) {
                case 4:
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdfcontrol4") + idconsolidadoselec);
                    break;
                case 5:
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdfcontrol5") + idconsolidadoselec);
                    break;
                case 6:
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdfcontrol6") + idconsolidadoselec);
                    break;
                case 7:
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdfcontrol7") + idconsolidadoselec);
                    break;
            }
        } catch (IOException ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String reportepdfcontrol() {
        try {
            switch (reporteselec) {
                case 4:
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdfcontrol1") + idconsolidadoselec);
                    break;
                case 5:
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdfcontrol2") + idconsolidadoselec);
                    break;
                case 6:
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdfcontrol3") + idconsolidadoselec);
                    break;
                case 7:
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdfcontrol8") + idconsolidadoselec);
                    break;
                case 8:
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdfcontrol9") + idconsolidadoselec);
                    break;
                case 9:
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdfcontrol10") + idconsolidadoselec);
                    break;
                case 10:
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdfcontrol11") + idconsolidadoselec);
                    break;
            }
        } catch (IOException ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //************---------------------------------------*********************\\  
    // Reportes dinamicos, metodos nuevos 11-04-2013
    public void reportePdfDinamico() {
        /**
         * Proceso de los reportes dinamicos en formato PDF, identificando a
         * cual pertenece y por consiguiente que reporte debe cargar
         *
         */
        try {
            setReporteFormato(PDF_FORMATO);

            switch (getReporteselectregionali()) {
                case 1:
                    //Regionalizacion 1. Departamentos
                    switch (getReporteselectlinea()) {
                        // Manejo de las tres opciones de reportes dinamicos 1.Ayuda humanitaria, 2. Obras entidades territoriales, 3. Convenios entidades nacionales
                        case 1:
                            //1. Ayuda humanitaria
                            configuracionRegionalXDepartamento();
                            reporteDinamicoAyudaHumanitaria();
                            break;
                        case 2:
                            //2. Obras entidades territoriales
                            configuracionRegionalXDepartamento();
                            reporteDinamicoObrasEntidadesTerritoriales();
                            break;

                        case 3:
                            //3. Convenios entidades nacionales
                            configuracionRegionalXDepartamento();
                            reporteDinamicoConveniosEntidadesNacionales();
                            break;
                    }
                    break;
                case 2:
                    //Regionalizacion 2. Region (Zona especifica)
                    switch (getReporteselectlinea()) {
                        // Manejo de las tres opciones de reportes dinamicos 1.Ayuda humanitaria, 2. Obras entidades territoriales, 3. Convenios entidades nacionales
                        case 1:
                            //1. Ayuda humanitaria
                            configuracionRegionalXZona();
                            reporteDinamicoAyudaHumanitaria();
                            break;
                        case 2:
                            //2. Obras entidades territoriales
                            configuracionRegionalXZona();
                            reporteDinamicoObrasEntidadesTerritoriales();
                            break;
                        case 3:
                            //3. Convenios entidades nacionales
                            configuracionRegionalXZona();
                            reporteDinamicoConveniosEntidadesNacionales();
                            break;
                    }
                    break;

                case 3:
                    switch (getReporteselectlinea()) {
                        // Manejo de las tres opciones de reportes dinamicos 1.Ayuda humanitaria, 2. Obras entidades territoriales, 3. Convenios entidades nacionales
                        case 1:
                            //1. Ayuda humanitaria
                            configuracionRegionalXRegion();
                            reporteDinamicoAyudaHumanitaria();
                            break;
                        case 2:
                            //2. Obras entidades territoriales
                            configuracionRegionalXRegion();
                            reporteDinamicoObrasEntidadesTerritoriales();
                            break;
                        case 3:
                            //3. Convenios entidades nacionales
                            configuracionRegionalXRegion();
                            reporteDinamicoConveniosEntidadesNacionales();
                            break;
                    }
                    break;

            }

        } catch (IOException ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void reporteExcelDinamico() {
        /**
         * Proceso de los reportes dinamicos en formato Excel, identificando a
         * cual pertenece y por consiguiente que reporte debe cargar
         *
         */
        try {
            setReporteFormato(XLS_FORMATO);

            switch (getReporteselectregionali()) {
                case 1:
                    //Regionalizacion 1. Departamentos
                    switch (getReporteselectlinea()) {
                        // Manejo de las tres opciones de reportes dinamicos 1.Ayuda humanitaria, 2. Obras entidades territoriales, 3. Convenios entidades nacionales
                        case 1:
                            //1. Ayuda humanitaria
                            configuracionRegionalXZona();
                            reporteDinamicoAyudaHumanitaria();
                            break;
                        case 2:
                            //2. Obras entidades territoriales
                            configuracionRegionalXDepartamento();
                            reporteDinamicoObrasEntidadesTerritoriales();

                            break;

                        case 3:
                            //3. Convenios entidades nacionales
                            configuracionRegionalXDepartamento();
                            reporteDinamicoConveniosEntidadesNacionales();
                            break;
                    }
                    break;
                case 2:
                    //Regionalizacion 2. Region (Zona especifica)
                    switch (getReporteselectlinea()) {
                        // Manejo de las tres opciones de reportes dinamicos 1.Ayuda humanitaria, 2. Obras entidades territoriales, 3. Convenios entidades nacionales
                        case 1:
                            //1. Ayuda humanitaria
                            configuracionRegionalXZona();
                            reporteDinamicoAyudaHumanitaria();
                            break;
                        case 2:
                            //2. Obras entidades territoriales
                            configuracionRegionalXZona();
                            reporteDinamicoObrasEntidadesTerritoriales();
                            break;
                        case 3:
                            //3. Convenios entidades nacionales
                            configuracionRegionalXZona();
                            reporteDinamicoConveniosEntidadesNacionales();
                            break;
                    }
                    break;

                case 3:
                    switch (getReporteselectlinea()) {
                        // Manejo de las tres opciones de reportes dinamicos 1.Ayuda humanitaria, 2. Obras entidades territoriales, 3. Convenios entidades nacionales
                        case 1:
                            //1. Ayuda humanitaria
                            configuracionRegionalXRegion();
                            reporteDinamicoAyudaHumanitaria();
                            break;
                        case 2:
                            //2. Obras entidades territoriales
                            configuracionRegionalXRegion();
                            reporteDinamicoObrasEntidadesTerritoriales();
                            break;
                        case 3:
                            //3. Convenios entidades nacionales
                            configuracionRegionalXRegion();
                            reporteDinamicoConveniosEntidadesNacionales();
                            break;
                    }
            }

        } catch (IOException ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void reporteWordDinamico() {
        /**
         * Proceso de los reportes dinamicos en formato Word, identificando a
         * cual pertenece y por consiguiente que reporte debe cargar
         *
         */
        try {
            setReporteFormato(DOC_FORMATO);

            switch (getReporteselectregionali()) {
                case 1:
                    //Regionalizacion 1. Departamentos
                    switch (getReporteselectlinea()) {
                        // Manejo de las tres opciones de reportes dinamicos 1.Ayuda humanitaria, 2. Obras entidades territoriales, 3. Convenios entidades nacionales
                        case 1:
                            //1. Ayuda humanitaria
                            configuracionRegionalXDepartamento();
                            reporteDinamicoAyudaHumanitaria();
                            break;
                        case 2:
                            //2. Obras entidades territoriales
                            configuracionRegionalXDepartamento();
                            reporteDinamicoObrasEntidadesTerritoriales();
                            break;

                        case 3:
                            //3. Convenios entidades nacionales
                            configuracionRegionalXDepartamento();
                            reporteDinamicoConveniosEntidadesNacionales();
                            break;
                    }
                    break;
                case 2:
                    //Regionalizacion 2. Region (Zona especifica)
                    switch (getReporteselectlinea()) {
                        // Manejo de las tres opciones de reportes dinamicos 1.Ayuda humanitaria, 2. Obras entidades territoriales, 3. Convenios entidades nacionales
                        case 1:
                            //1. Ayuda humanitaria
                            configuracionRegionalXZona();
                            reporteDinamicoAyudaHumanitaria();
                            break;
                        case 2:
                            //2. Obras entidades territoriales
                            configuracionRegionalXZona();
                            reporteDinamicoObrasEntidadesTerritoriales();
                            break;
                        case 3:
                            //3. Convenios entidades nacionales
                            configuracionRegionalXZona();
                            reporteDinamicoConveniosEntidadesNacionales();
                            break;
                    }
                    break;

                case 3:
                    switch (getReporteselectlinea()) {
                        // Manejo de las tres opciones de reportes dinamicos 1.Ayuda humanitaria, 2. Obras entidades territoriales, 3. Convenios entidades nacionales
                        case 1:
                            //1. Ayuda humanitaria
                            configuracionRegionalXRegion();
                            reporteDinamicoAyudaHumanitaria();
                            break;
                        case 2:
                            //2. Obras entidades territoriales
                            configuracionRegionalXRegion();
                            reporteDinamicoObrasEntidadesTerritoriales();
                            break;
                        case 3:
                            //3. Convenios entidades nacionales
                            configuracionRegionalXRegion();
                            reporteDinamicoConveniosEntidadesNacionales();
                            break;
                    }
            }

        } catch (IOException ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    private void reporteDinamicoObrasEntidadesTerritoriales() throws IOException {
        /*
         * En este método se maneja la logica de los reportes de entidades territoriales.
         * y se hace el llamado al reporte como tal. 
         */
        // parametros categoria = todos, tipo Proyecto = todos, Tipo Obra = todos
        if (getTipoobint() == (-1) && getInttipoproyecto() == (-1) && getInttipoobra() == (-1)) {
            if (getReporteFormato() == PDF_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdfsolicitud") + cadenaPeticionReporte);
            }
            if (getReporteFormato() == XLS_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteexcelsolicitud") + cadenaPeticionReporte);
            }
            if (getReporteFormato() == DOC_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportewordsolicitud") + cadenaPeticionReporte);
            }
        }
        // parametros categoria = todos, tipo Proyecto = todos, Tipo Obra != todos 
        if (getTipoobint() == (-1) && getInttipoproyecto() == (-1) && getInttipoobra() != (-1)) {
            //Funciona igual que el primero. 
            if (getReporteFormato() == PDF_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdfsolicitud") + cadenaPeticionReporte);
            }

            if (getReporteFormato() == XLS_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteexcelsolicitud") + cadenaPeticionReporte);
            }
            if (getReporteFormato() == DOC_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportewordsolicitud") + cadenaPeticionReporte);
            }
        }
        // parametros categoria = todos, tipo Proyecto != todos, Tipo Obra = todos
        if (getTipoobint() == (-1) && getInttipoproyecto() != (-1) && getInttipoobra() == (-1)) {
            cadenaPeticionReporte = cadenaPeticionReporte + "&tipoproyecto=" + inttipoproyecto;
            if (getReporteFormato() == PDF_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdfsolicitud") + cadenaPeticionReporte);
            }

            if (getReporteFormato() == XLS_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteexcelsolicitud") + cadenaPeticionReporte);
            }
            if (getReporteFormato() == DOC_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportewordsolicitud") + cadenaPeticionReporte);
            }

        }
        // parametros categoria != todos, tipo Proyecto = todos, Tipo Obra = todos
        if (getTipoobint() != (-1) && getInttipoproyecto() == (-1) && getInttipoobra() == (-1)) {
            cadenaPeticionReporte = cadenaPeticionReporte + "&claseobra=" + tipoobint;
            if (getReporteFormato() == PDF_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdfsolicitud") + cadenaPeticionReporte);
            }
            if (getReporteFormato() == XLS_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteexcelsolicitud") + cadenaPeticionReporte);
            }
            if (getReporteFormato() == DOC_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportewordsolicitud") + cadenaPeticionReporte);
            }
        }
        // parametros categoria = todos, tipo Proyecto != todos, Tipo Obra != todos
        if (getTipoobint() == (-1) && getInttipoproyecto() != (-1) && getInttipoobra() != (-1)) {
            cadenaPeticionReporte = cadenaPeticionReporte + "&tipoobra=" + inttipoobra;
            if (getReporteFormato() == PDF_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdfsolicitud") + cadenaPeticionReporte);
            }
            if (getReporteFormato() == XLS_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteexcelsolicitud") + cadenaPeticionReporte);
            }
            if (getReporteFormato() == DOC_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportewordsolicitud") + cadenaPeticionReporte);
            }
        }
        // parametros categoria != todos, tipo Proyecto != todos, Tipo Obra = todos
        if (getTipoobint() != (-1) && getInttipoproyecto() != (-1) && getInttipoobra() == (-1)) {
            cadenaPeticionReporte = cadenaPeticionReporte + "&claseobra=" + tipoobint + "&tipoproyecto=" + inttipoproyecto;
            if (getReporteFormato() == PDF_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdfsolicitud") + cadenaPeticionReporte);
            }
            if (getReporteFormato() == XLS_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteexcelsolicitud") + cadenaPeticionReporte);
            }
            if (getReporteFormato() == DOC_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportewordsolicitud") + cadenaPeticionReporte);
            }
        }
        // parametros categoria != todos, tipo Proyecto = todos, Tipo Obra != todos
        if (getTipoobint() != (-1) && getInttipoproyecto() == (-1) && getInttipoobra() != (-1)) {
            cadenaPeticionReporte = cadenaPeticionReporte + "&claseobra=" + tipoobint + "&tipoobra" + inttipoobra;
            if (getReporteFormato() == PDF_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdfsolicitud") + cadenaPeticionReporte);
            }
            if (getReporteFormato() == XLS_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteexcelsolicitud") + cadenaPeticionReporte);
            }
            if (getReporteFormato() == DOC_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportewordsolicitud") + cadenaPeticionReporte);
            }
        }
        // parametros categoria != todos, tipo Proyecto != todos, Tipo Obra != todos
        if (getTipoobint() != (-1) && getInttipoproyecto() != (-1) && getInttipoobra() != (-1)) {
            cadenaPeticionReporte = cadenaPeticionReporte + "&claseobra=" + tipoobint + "&tipoobra" + inttipoobra;
            if (getReporteFormato() == PDF_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdfsolicitud") + cadenaPeticionReporte);
            }
            if (getReporteFormato() == XLS_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteexcelsolicitud") + cadenaPeticionReporte);
            }
            if (getReporteFormato() == DOC_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportewordsolicitud") + cadenaPeticionReporte);
            }
        }

    }

    private void reporteDinamicoConveniosEntidadesNacionales() throws IOException {
        /*
         * Metodo encargado de 
         */


        if (getReporteSelectConveniosEntidadesNacionales() == -1) {
            if (getReporteFormato() == PDF_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdfconvenio") + cadenaPeticionReporte);
            }
            if (getReporteFormato() == XLS_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteexcelconvenio") + cadenaPeticionReporte);
            }
            if (getReporteFormato() == DOC_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportewordconvenio") + cadenaPeticionReporte);
            }
        }

        if (getReporteSelectConveniosEntidadesNacionales() == REPORTE_CONVENIO) {
            cadenaPeticionReporte = cadenaPeticionReporte + "&convenio=" + _idConvenio;
            if (getReporteFormato() == PDF_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdfconvenio") + cadenaPeticionReporte);
            }
            if (getReporteFormato() == XLS_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteexcelconvenio") + cadenaPeticionReporte);
            }
            if (getReporteFormato() == DOC_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportewordconvenio") + cadenaPeticionReporte);
            }
        }

        if (getReporteSelectConveniosEntidadesNacionales() == REPORTE_PROYECTO_X_CONVENIO) {
            cadenaPeticionReporte = cadenaPeticionReporte + "&convenio=" + _idConvenio;
            if (getReporteFormato() == PDF_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdfproyectosconvenio") + cadenaPeticionReporte);
            }
            if (getReporteFormato() == XLS_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteexcelproyectosconvenio") + cadenaPeticionReporte);
            }
            if (getReporteFormato() == DOC_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportewordproyectosconvenio") + cadenaPeticionReporte);
            }
        }

    }

    private void reporteDinamicoAyudaHumanitaria() throws IOException {

        /*
         * 
         */

        SimpleDateFormat _fechaFormateada = new SimpleDateFormat("yyyy-MM-dd");

        if (getReporteselectaten() == REPORTE_RESUMEN_SOLICITUDES_ASISTENCIA_HUMANITARIA) {
            cadenaPeticionReporte = cadenaPeticionReporte + "&TipoAtencion=" + tipoahint + "&fechaini=" + _fechaFormateada.format(feciniah) + "&fechafin=" + _fechaFormateada.format(fecfinah);
            if (getReporteFormato() == PDF_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdf_resumen_solicitudes_asistencia_humanitaria") + cadenaPeticionReporte);
            }
            if (getReporteFormato() == XLS_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteexcel_resumen_solicitudes_asistencia_humanitaria") + cadenaPeticionReporte);
            }
            if (getReporteFormato() == DOC_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteword_resumen_solicitudes_asistencia_humanitaria") + cadenaPeticionReporte);
            }
        }

        if (getReporteselectaten() == REPORTE_SEGUIMIENTO_ATENCION_HUMANITARIA_MUNICIPAL) {
            if (getReporteFormato() == PDF_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdf_seguimiento_atencion_humanitaria_municipal"));
            }
            if (getReporteFormato() == XLS_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteexcel_seguimiento_atencion_humanitaria_municipal"));
            }
            if (getReporteFormato() == DOC_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteword_seguimiento_atencion_humanitaria_municipal"));
            }
        }

        if (getReporteselectaten() == REPORTE_SEGUIMIENTOS_ARRIENDOS_MUNICIPAL) {
            if (getReporteFormato() == PDF_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdf_seguimientos_arriendos_municipal"));
            }
            if (getReporteFormato() == XLS_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteexcel_seguimientos_arriendos_municipal"));
            }
            if (getReporteFormato() == DOC_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteword_seguimientos_arriendos_municipal"));
            }
        }

        if (getReporteselectaten() == REPORTE_SEGUIMIENTOS_ATENCION_HUMANITARIA_GLOBAL) {
            if (getReporteFormato() == PDF_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdf_seguimientos_atencion_humanitaria_global"));
            }
            if (getReporteFormato() == XLS_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteexcel_seguimientos_atencion_humanitaria_global"));
            }
            if (getReporteFormato() == DOC_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteword_seguimientos_atencion_humanitaria_global"));
            }
        }

        if (getReporteselectaten() == REPORTE_SOLICITUDES_APROBADAS_POR_DEPARTAMENTO) {
            cadenaPeticionReporte = cadenaPeticionReporte + "&TipoAtencion=" + tipoahint + "&fechaini=" + _fechaFormateada.format(feciniah) + "&fechafin=" + _fechaFormateada.format(fecfinah);
            if (getReporteFormato() == PDF_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdf_solicitudes_aprobadas_por_departamento") + cadenaPeticionReporte);
            }
            if (getReporteFormato() == XLS_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteexcel_solicitudes_aprobadas_por_departamento") + cadenaPeticionReporte);
            }
            if (getReporteFormato() == DOC_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteword_solicitudes_aprobadas_por_departamento") + cadenaPeticionReporte);
            }
        }

        if (getReporteselectaten() == REPORTE_SOLICITUDES_APROBADAS_POR_REGION) {

            cadenaPeticionReporte = cadenaPeticionReporte + "&fechaini=" + _fechaFormateada.format(feciniah) + "&fechafin=" + _fechaFormateada.format(fecfinah);

            if (getReporteFormato() == PDF_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdf_solicitudes_aprobadas_por_region") + cadenaPeticionReporte);
            }
            if (getReporteFormato() == XLS_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteexcel_solicitudes_aprobadas_por_region") + cadenaPeticionReporte);
            }
            if (getReporteFormato() == DOC_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteword_solicitudes_aprobadas_por_region") + cadenaPeticionReporte);
            }
        }


        if (getReporteselectaten() == REPORTE_SOLICITUDES_PRESENTADAS_VS_APROBADAS) {
            cadenaPeticionReporte = cadenaPeticionReporte + "&TipoAtencion=" + tipoahint + "&fechaini=" + _fechaFormateada.format(feciniah) + "&fechafin=" + _fechaFormateada.format(fecfinah);
            if (getReporteFormato() == PDF_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdf_solicitudes_presentadas_vs_aprobadas") + cadenaPeticionReporte);
            }
            if (getReporteFormato() == XLS_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteexcel_solicitudes_presentadas_vs_aprobadas") + cadenaPeticionReporte);
            }
            if (getReporteFormato() == DOC_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteword_solicitudes_presentadas_vs_aprobadas") + cadenaPeticionReporte);
            }
        }


        if (getReporteselectaten() == REPORTE_TOTAL_APROBADO) {
            cadenaPeticionReporte = cadenaPeticionReporte + "&TipoAtencion=" + tipoahint + "&fechaini=" + _fechaFormateada.format(feciniah) + "&fechafin=" + _fechaFormateada.format(fecfinah);
            if (getReporteFormato() == PDF_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdf_total_aprobado") + cadenaPeticionReporte);
            }
            if (getReporteFormato() == XLS_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteexcel_total_aprobado") + cadenaPeticionReporte);
            }
            if (getReporteFormato() == DOC_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteword_total_aprobado") + cadenaPeticionReporte);
            }
        }

        if (getReporteselectaten() == REPORTE_TOTAL_APROBADO_ATENCION_HUMANITARIA_ALOJAMIENTOS_TEMPORALES) {
            cadenaPeticionReporte = cadenaPeticionReporte + "&fechaini=" + _fechaFormateada.format(feciniah) + "&fechafin=" + _fechaFormateada.format(fecfinah);
            if (getReporteFormato() == PDF_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdf_total_aprobado_atencion_humanitaria_alojamientos_temporales") + cadenaPeticionReporte);
            }
            if (getReporteFormato() == XLS_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteexcel_total_aprobado_atencion_humanitaria_alojamientos_temporales") + cadenaPeticionReporte);
            }
            if (getReporteFormato() == DOC_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteword_total_aprobado_atencion_humanitaria_alojamientos_temporales") + cadenaPeticionReporte);
            }
        }


        if (getReporteselectaten() == REPORTE_TOTAL_APROBADO_ATENCION_HUMANITARIA_ALOJAMIENTOS_TEMPORALES_POR_REGION_O_LOCALIDAD) {

            cadenaPeticionReporte = cadenaPeticionReporte + "&fechaini=" + _fechaFormateada.format(feciniah) + "&fechafin=" + _fechaFormateada.format(fecfinah);

            if (getReporteFormato() == PDF_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdf_total_aprobado_atencion_humanitaria_alojamientos_temporales") + cadenaPeticionReporte);
            }
            if (getReporteFormato() == XLS_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteexcel_total_aprobado_atencion_humanitaria_alojamientos_temporales") + cadenaPeticionReporte);
            }
            if (getReporteFormato() == DOC_FORMATO) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteword_total_aprobado_atencion_humanitaria_alojamientos_temporales") + cadenaPeticionReporte);
            }
        }

    }

    public void configuracionRegionalXDepartamento() {
        /*
         * Metodo encargado de dar el formato a la cadena del reporte de acuerdo al departamento y municipios 
         * seleccionados, teniendo en cuenta cuando estos estan vacios. 
         */
        cadenaPeticionReporte = null;

        if (deptosah.equals("-1")) {
            cadenaPeticionReporte = "&munici=169";
        } else {

            if (muniah.equals("-1")) {
                cadenaPeticionReporte = "&munici=" + deptosah;
            } else {
                cadenaPeticionReporte = "&munici=" + muniah;
            }
        }
    }

    public void configuracionRegionalXZona() {
        /*
         * Metodo encargado de dar el formato a la cadena del reporte de acuerdo a una zona  
         * seleccionada, teniendo en cuenta cuando estos estan vacios. 
         */

        cadenaPeticionReporte = null;
        if (intzona == -1) {
            cadenaPeticionReporte = "&munici=169";
        } else {

            cadenaPeticionReporte = "&zona=" + intzona;
        }
    }

    public void configuracionRegionalXRegion() {
        /*
         * Metodo encargado de dar el formato a la cadena del reporte de acuerdo a una Región  
         * seleccionada, teniendo en cuenta cuando estos estan vacios. 
         */

        cadenaPeticionReporte = null;
        cadenaPeticionReporte = "&Region=" + regionint;

    }

    public void llenarConveniosXEntidad() {
        /*
         * Metodo encargado de llenar la lista de los convenios 
         * generados por una entidad(contrato) seleccionada.
         */

        List<Contrato> lista = getSessionBeanCobra().getCobraService().getContratosXCodigoTercero(inttercer);;
        _contratoConvenios = new SelectItem[lista.size()];

        if (lista.size() <= 0) {
            _idConvenio = -1;
        } else {
            int i = 0;
            while (i < lista.size()) {
                SelectItem opt = new SelectItem(lista.get(i).getIntidcontrato(), lista.get(i).getStrnombre());
                _contratoConvenios[i] = opt;
                if (i == 0) {
                    _idConvenio = lista.get(i).getIntidcontrato();
                }

                i++;
            }

        }

    }
}
