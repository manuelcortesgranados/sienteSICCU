/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.SolicitudObra;

import chsolicitud.dao.service.SolicitudServiceAble;
import co.com.interkont.cobra.to.*;


import java.io.IOException;
import java.io.Serializable;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>Session scope data bean for your application.  Create properties
 *  here to represent cached data that should be made available across
 *  multiple HTTP requests for an individual user.</p>
 *
 * <p>An instance of this class will be created for you automatically,
 * the first time your application evaluates a value binding expression
 * or method binding expression that references a managed bean using
 * this class.</p>
 *
 * @version SessionBean1.java
 * @version Created on 4/10/2010, 05:27:59 PM
 * @author carlos
 */
public class SessionChsolicitud implements Serializable{
    // <editor-fold defaultstate="collapsed" desc="Managed Component Definition">

    ResourceBundle bundle = ResourceBundle.getBundle("cobra.properties.Bundle");
    private String mensajeError = "";
    private String usuariologin = "";
    private String passwordlogin = "";
    private JsfUsuario usuariolog = new JsfUsuario();
    private Locale local = new Locale("es");
    private Log logger = LogFactory.getLog(this.getClass());
    private SolicitudServiceAble solicitudService;
    private SelectItem[] itemsEstadoSolicitudCh;
    private SelectItem[] itemsEstadoDocumentacion;
    private SelectItem[] itemsTipoObra;
    private SelectItem[] itemsTipoSolicitud;
    private SelectItem[] itemsCargo;
    private SelectItem[] itemsDeptos;
    private String deptoselec = "";
    private SelectItem[] itemsMunicipios;
    private boolean puedeinsertar = false;
    private boolean puedeeditar = false;
    private boolean puedeconsultar = false;
    private boolean puedeeliminar = false;
    private boolean puedeverreportes = false;
    private boolean puedeaprobar = false;
    private boolean todossolivsapro;
    private Date fecinitodossolivsapro;
    private Date fecfintodossolivsapro;
    private String tiposolitodossolivsapro;
    private Date fecinisolapro;
    private Date fecfinsolapro;
    private Date fecinisolregi;
    private Date fecfinsolregi;
    private Date fecinisolaprotipo;
    private Date fecfinsolaprotipo;
    private String tipoSolicitudxtipo;
    private String localidadseleccionada = "";
    private SelectItem[] itemsLocalidades;
    private String tiposolitxregion;
    private String tiposoliresumen;
    private Date fecinisolresumen;
    private Date fecfinsolresumen;
    private Date fecinitotaprotipo;
    private Date fecfintotaprotipo;
    private Date feciniaproxtipodeobra;
    private Date fecfinaproxtipodeobra;
    private Date fecinitotsolaprotipdepvlr;
    private Date fecfinatotsolaprotipdepvlr;
    private String tipototsolaprotipdepvlrsele;
    private Date fecinireporte;
    private Date fecfinreporte;
    private SelectItem[] itemReporte;
    private int reporteSeleccionado;
    private int menu = 0;
    private String origen;
    private String reg;
    private String loc;
    private int regionesta = 0;
    private int locaesta = 0;
    private SelectItem[] itemsMunipfiltro;
    private String munifiltroseleccioando = "";
    private int municiesta = 0;
    private SelectItem[] itemsSubEstadoSolicitudCh;
    private int mostrarFecha = 0;
    private int perfil = 0;
    private int grupo = 0;
    private List<Grupo> listagrupo = new ArrayList<Grupo>();
    private String urlbirt;

    public String getUrlbirt() {
        return urlbirt;
    }

    public void setUrlbirt(String urlbirt) {
        this.urlbirt = urlbirt;
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

    public int getMostrarFecha() {
        return mostrarFecha;
    }

    public void setMostrarFecha(int mostrarFecha) {
        this.mostrarFecha = mostrarFecha;
    }

    public Date getFecfinreporte() {
        return fecfinreporte;
    }

    public void setFecfinreporte(Date fecfinreporte) {
        this.fecfinreporte = fecfinreporte;
    }

    public Date getFecinireporte() {
        return fecinireporte;
    }

    public void setFecinireporte(Date fecinireporte) {
        this.fecinireporte = fecinireporte;
    }

    public SelectItem[] getItemReporte() {
        return itemReporte;
    }

    public void setItemReporte(SelectItem[] itemReporte) {
        this.itemReporte = itemReporte;
    }

    public SelectItem[] getItemsMunipfiltro() {
        return itemsMunipfiltro;
    }

    public void setItemsMunipfiltro(SelectItem[] itemsMunipfiltro) {
        this.itemsMunipfiltro = itemsMunipfiltro;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public int getLocaesta() {
        return locaesta;
    }

    public void setLocaesta(int locaesta) {
        this.locaesta = locaesta;
    }

    public int getMenu() {
        return menu;
    }

    public void setMenu(int menu) {
        this.menu = menu;
    }

    public int getMuniciesta() {
        return municiesta;
    }

    public void setMuniciesta(int municiesta) {
        this.municiesta = municiesta;
    }

    public String getMunifiltroseleccioando() {
        return munifiltroseleccioando;
    }

    public void setMunifiltroseleccioando(String munifiltroseleccioando) {
        this.munifiltroseleccioando = munifiltroseleccioando;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getReg() {
        return reg;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }

    public int getRegionesta() {
        return regionesta;
    }

    public void setRegionesta(int regionesta) {
        this.regionesta = regionesta;
    }

    public int getReporteSeleccionado() {
        return reporteSeleccionado;
    }

    public void setReporteSeleccionado(int reporteSeleccionado) {
        this.reporteSeleccionado = reporteSeleccionado;
    }

    public SelectItem[] getItemsSubEstadoSolicitudCh() {
        return itemsSubEstadoSolicitudCh;
    }

    public void setItemsSubEstadoSolicitudCh(SelectItem[] itemsSubEstadoSolicitudCh) {
        this.itemsSubEstadoSolicitudCh = itemsSubEstadoSolicitudCh;
    }

    public String getTipototsolaprotipdepvlrsele() {
        return tipototsolaprotipdepvlrsele;
    }

    public void setTipototsolaprotipdepvlrsele(String tipototsolaprotipdepvlrsele) {
        this.tipototsolaprotipdepvlrsele = tipototsolaprotipdepvlrsele;
    }

    public Date getFecfinatotsolaprotipdepvlr() {
        return fecfinatotsolaprotipdepvlr;
    }

    public void setFecfinatotsolaprotipdepvlr(Date fecfinatotsolaprotipdepvlr) {
        this.fecfinatotsolaprotipdepvlr = fecfinatotsolaprotipdepvlr;
    }

    public Date getFecinitotsolaprotipdepvlr() {
        return fecinitotsolaprotipdepvlr;
    }

    public void setFecinitotsolaprotipdepvlr(Date fecinitotsolaprotipdepvlr) {
        this.fecinitotsolaprotipdepvlr = fecinitotsolaprotipdepvlr;
    }

    public Date getFecfinaproxtipodeobra() {
        return fecfinaproxtipodeobra;
    }

    public void setFecfinaproxtipodeobra(Date fecfinaproxtipodeobra) {
        this.fecfinaproxtipodeobra = fecfinaproxtipodeobra;
    }

    public Date getFeciniaproxtipodeobra() {
        return feciniaproxtipodeobra;
    }

    public void setFeciniaproxtipodeobra(Date feciniaproxtipodeobra) {
        this.feciniaproxtipodeobra = feciniaproxtipodeobra;
    }

    public Date getFecfintotaprotipo() {
        return fecfintotaprotipo;
    }

    public void setFecfintotaprotipo(Date fecfintotaprotipo) {
        this.fecfintotaprotipo = fecfintotaprotipo;
    }

    public Date getFecinitotaprotipo() {
        return fecinitotaprotipo;
    }

    public void setFecinitotaprotipo(Date fecinitotaprotipo) {
        this.fecinitotaprotipo = fecinitotaprotipo;
    }

    public Date getFecfinsolresumen() {
        return fecfinsolresumen;
    }

    public void setFecfinsolresumen(Date fecfinsolresumen) {
        this.fecfinsolresumen = fecfinsolresumen;
    }

    public Date getFecinisolresumen() {
        return fecinisolresumen;
    }

    public void setFecinisolresumen(Date fecinisolresumen) {
        this.fecinisolresumen = fecinisolresumen;
    }

    public String getTiposoliresumen() {
        return tiposoliresumen;
    }

    public void setTiposoliresumen(String tiposoliresumen) {
        this.tiposoliresumen = tiposoliresumen;
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

    public Date getFecfintodossolivsapro() {
        return fecfintodossolivsapro;
    }

    public void setFecfintodossolivsapro(Date fecfintodossolivsapro) {
        this.fecfintodossolivsapro = fecfintodossolivsapro;
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

    public Date getFecinitodossolivsapro() {
        return fecinitodossolivsapro;
    }

    public void setFecinitodossolivsapro(Date fecinitodossolivsapro) {
        this.fecinitodossolivsapro = fecinitodossolivsapro;
    }

    public SelectItem[] getItemsLocalidades() {
        return itemsLocalidades;
    }

    public void setItemsLocalidades(SelectItem[] itemsLocalidades) {
        this.itemsLocalidades = itemsLocalidades;
    }

    public String getLocalidadseleccionada() {
        return localidadseleccionada;
    }

    public void setLocalidadseleccionada(String localidadseleccionada) {
        this.localidadseleccionada = localidadseleccionada;
    }

    public String getTipoSolicitudxtipo() {
        return tipoSolicitudxtipo;
    }

    public void setTipoSolicitudxtipo(String tipoSolicitudxtipo) {
        this.tipoSolicitudxtipo = tipoSolicitudxtipo;
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

    public boolean isTodossolivsapro() {
        return todossolivsapro;
    }

    public void setTodossolivsapro(boolean todossolivsapro) {
        this.todossolivsapro = todossolivsapro;
    }

    public boolean isPuedeaprobar() {
        return puedeaprobar;
    }

    public void setPuedeaprobar(boolean puedeaprobar) {
        this.puedeaprobar = puedeaprobar;
    }

    public boolean isPuedeconsultar() {
        return puedeconsultar;
    }

    public void setPuedeconsultar(boolean puedeconsultar) {
        this.puedeconsultar = puedeconsultar;
    }

    public boolean isPuedeeditar() {
        return puedeeditar;
    }

    public void setPuedeeditar(boolean puedeeditar) {
        this.puedeeditar = puedeeditar;
    }

    public boolean isPuedeeliminar() {
        return puedeeliminar;
    }

    public void setPuedeeliminar(boolean puedeeliminar) {
        this.puedeeliminar = puedeeliminar;
    }

    public boolean isPuedeinsertar() {
        return puedeinsertar;
    }

    public void setPuedeinsertar(boolean puedeinsertar) {
        this.puedeinsertar = puedeinsertar;
    }

    public boolean isPuedeverreportes() {
        return puedeverreportes;
    }

    public void setPuedeverreportes(boolean puedeverreportes) {
        this.puedeverreportes = puedeverreportes;
    }

    public SelectItem[] getItemsMunicipios() {
        return itemsMunicipios;
    }

    public void setItemsMunicipios(SelectItem[] itemsMunicipios) {
        this.itemsMunicipios = itemsMunicipios;
    }

    public String getDeptoselec() {
        return deptoselec;
    }

    public void setDeptoselec(String deptoselec) {
        this.deptoselec = deptoselec;
    }

    public SelectItem[] getItemsDeptos() {
        return itemsDeptos;
    }

    public void setItemsDeptos(SelectItem[] itemsDeptos) {
        this.itemsDeptos = itemsDeptos;
    }

    public SelectItem[] getItemsCargo() {
        return itemsCargo;
    }

    public void setItemsCargo(SelectItem[] itemsCargo) {
        this.itemsCargo = itemsCargo;
    }

    public SelectItem[] getItemsTipoSolicitud() {
        return itemsTipoSolicitud;
    }

    public void setItemsTipoSolicitud(SelectItem[] itemsTipoSolicitud) {
        this.itemsTipoSolicitud = itemsTipoSolicitud;
    }

    public SelectItem[] getItemsTipoObra() {
        return itemsTipoObra;
    }

    public void setItemsTipoObra(SelectItem[] itemsTipoObra) {
        this.itemsTipoObra = itemsTipoObra;
    }

   
//    public Solicituddetalle solicituddetalle = new Solicituddetalle();
    public SelectItem[] getItemsEstadoDocumentacion() {
        return itemsEstadoDocumentacion;
    }

    public void setItemsEstadoDocumentacion(SelectItem[] itemsEstadoDocumentacion) {
        this.itemsEstadoDocumentacion = itemsEstadoDocumentacion;
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public SelectItem[] getItemsEstadoSolicitudCh() {
        return itemsEstadoSolicitudCh;
    }

    public void setItemsEstadoSolicitudCh(SelectItem[] itemsEstadoSolicitudCh) {
        this.itemsEstadoSolicitudCh = itemsEstadoSolicitudCh;
    }

    public SolicitudServiceAble getSolicitudService() {
        return solicitudService;
    }

    public void setSolicitudService(SolicitudServiceAble solicitudService) {
        this.solicitudService = solicitudService;
    }

    public Locale getLocal() {
        return local;
    }

    public void setLocal(Locale local) {
        this.local = local;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public String getPasswordlogin() {
        return passwordlogin;
    }

    public void setPasswordlogin(String passwordlogin) {
        this.passwordlogin = passwordlogin;
    }

    public JsfUsuario getUsuariolog() {
        return usuariolog;
    }

    public void setUsuariolog(JsfUsuario usuariolog) {
        this.usuariolog = usuariolog;
    }

    public String getUsuariologin() {
        return usuariologin;
    }

    public void setUsuariologin(String usuariologin) {
        this.usuariologin = usuariologin;
    }

    public Log getLogger() {
        return logger;
    }

    public void setLogger(Log logger) {
        this.logger = logger;
    }

    /**
     * <p>Automatically managed component initialization.  <strong>WARNING:</strong>
     * This method is automatically generated, so any user-specified code inserted
     * here is subject to being replaced.</p>
     */
    private void _init() throws Exception {
    }
    // </editor-fold>

    /**
     * <p>Construct a new session data bean instance.</p>
     */
    public SessionChsolicitud() {
        urlbirt= getBundle().getString("ipserver");
       
    }

    public String cerrarSession_action() {
        this.logger.info("cerrarSesion(" + usuariologin + ", " + new Date() + ");");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        ((HttpSession) ctx.getSession(false)).invalidate();
        return "cerrarSession";
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

        //   itemsEstadoSolicitudCh[i] = new SelectItem(-1,"Todos");
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

    public void llenarListaTipoObra() {
        List<Tipoobra> lista = solicitudService.getLista(Tipoobra.class, "strdesctipoobra");//Tipoobrach.class
        itemsTipoObra = new SelectItem[lista.size()];
        int i = 0;
        while (i < lista.size()) {
            SelectItem opt = new SelectItem(lista.get(i).getInttipoobra(), lista.get(i).getStrdesctipoobra());
            itemsTipoObra[i] = opt;

            i++;
        }
//        itemsTipoObra[i] = new SelectItem(-1, "Todos");
    }

    public void llenarListaTipoSolicitud() {
        List<Tiposolicitudobra> lista = solicitudService.getLista(Tiposolicitudobra.class, "strdescripcion");
        itemsTipoSolicitud = new SelectItem[lista.size()];
        int i = 0;
        while (i < lista.size()) {
            SelectItem opt = new SelectItem(lista.get(i).getInttiposolicitud(), lista.get(i).getStrdescripcion());
            itemsTipoSolicitud[i] = opt;
            i++;
        }
//       itemsTipoSolicitud[i] = new SelectItem(-1, "Todos");
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

    public String reporteGloblalOrigen() {

        if (origen.equals("1")) {//1=nal
            loc = null;
            reg = null;
            regionesta = 0;
            locaesta = 0;
            municiesta = 0;
        } else if (origen.equals("2")) {//2=regiona
            regionesta = 1;
            municiesta = 0;
            locaesta = 0;
        } else if (origen.equals("3")) {//3=depto
            regionesta = 0;
            locaesta = 1;
            llenarDeptos();
            municiesta = 0;
        } else if (origen.equals("4")) {//4=municipal
            municiesta = 1;
            regionesta = 0;
            locaesta = 0;
            llenarMuniciposxFiltro();
        }
        return null;
    }

    public String reporteGloblal() {
        origen = "0";
        reg = "0";
        llenarLocalidad();
        llenarListaTipoSolicitud();
        if (reporteSeleccionado == 1 || reporteSeleccionado == 2 || reporteSeleccionado == 3 || reporteSeleccionado == 5) {//1
            menu = 1;//menu 1 =pide tipo obra
            regionesta = 0;
            locaesta = 0;
            municiesta = 0;
            mostrarFecha = 0;
        } else if (reporteSeleccionado == 0 || reporteSeleccionado == 4) {
            menu = 0;
            municiesta = 0;
            mostrarFecha = 0;
        } else if (reporteSeleccionado == 6) {
            menu = 2;//menu=3 pide origen
            regionesta = 0;
            mostrarFecha = 0;
        } else if (reporteSeleccionado == 7) {
            menu = 2;//menu=3 pide origen
            mostrarFecha = 0;
        } else if (reporteSeleccionado == 8) {
            menu = 2;//menu=3 pide origen
            mostrarFecha = 0;
        } else if (reporteSeleccionado == 9 || reporteSeleccionado == 10) {
            menu = 1;//menu=3 pide origen
            mostrarFecha = 1;
            regionesta = 0;
        }
        return null;
    }

    public String reporteGloblalGenerar() {
        String namerepro = "";

        switch (reporteSeleccionado) {
            case 1:
                namerepro = "ch_totaprtipobr";
                break;
            case 2:
                namerepro = "ch_ressoltipobr";
                break;
            case 3:
                namerepro = "ch_solprevsaprtipobr";
                break;
            case 4:
                namerepro = "ch_totaprcon";
                break;
            case 5:
                namerepro = "ch_totaprtipobrreg";
                break;
            case 6:
                namerepro = "ch_totaprreg";
                break;
            case 7:
                namerepro = "ch_totaprtipsol";
                break;
            case 8:
                namerepro = "ch_totaprtipobrpastab";
                break;
            case 9:
                namerepro = "AprobacionesMes";
                break;
            case 10:
                namerepro = "AprobacionesMesEncargoFidu";
                break;


        }
        if (reporteSeleccionado == 1 || reporteSeleccionado == 2 || reporteSeleccionado == 3 || reporteSeleccionado == 4 || reporteSeleccionado == 5) {
            try {
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                FacesContext.getCurrentInstance().getExternalContext().redirect((urlbirt+"/birt/run?__report=report/ReportesCh/" + namerepro + ".rptdesign&__format=pdf&Tipo%20Solicitud=") + "&TipoSolicitud=" + tipototsolaprotipdepvlrsele + "&fechaini=" + URLEncoder.encode(df.format(fecinireporte), "UTF-8") + "&fechafin=" + URLEncoder.encode(df.format(fecfinreporte), "UTF-8"));
            } catch (IOException ex) {
                Logger.getLogger(SessionChsolicitud.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (reporteSeleccionado == 6 && origen.equals("1")) {//total aprobado por regiones+origen nacional
            //else if(reporteSeleccionado==6 && regionesta==0 && localidadseleccionada.equals("")){
            try {
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                FacesContext.getCurrentInstance().getExternalContext().redirect((urlbirt+"/birt/run?__report=report/ReportesCh/" + namerepro + ".rptdesign&__format=pdf&Tipo%20Solicitud=") + "&fechaini=" + URLEncoder.encode(df.format(fecinireporte), "UTF-8") + "&fechafin=" + URLEncoder.encode(df.format(fecfinreporte), "UTF-8"));//+"&Localidad="+loc+"&Region="+reg
                //FacesContext.getCurrentInstance().getExternalContext().redirect(("/birt/run?__report=report/ReportesCh/ch_totaprtipobr.rptdesign&__format=pdf&Tipo%20Solicitud=") + "&TipoSolicitud=" + tipototsolaprotipdepvlrsele + "&fechaini=" + URLEncoder.encode(df.format(fecinireporte), "UTF-8") + "&fechafin=" + URLEncoder.encode(df.format(fecfinreporte), "UTF-8"));
            } catch (IOException ex) {
                Logger.getLogger(SessionChsolicitud.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (reporteSeleccionado == 6 && origen.equals("2")) {//total aprobado por regiones+origen regiona√±
            //else if(reporteSeleccionado==6 && regionesta==1 && localidadseleccionada==""){//total aprobado por regiones+origen regional
            try {
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                FacesContext.getCurrentInstance().getExternalContext().redirect((urlbirt+"/birt/run?__report=report/ReportesCh/" + namerepro + ".rptdesign&__format=pdf&Tipo%20Solicitud=") + "&fechaini=" + URLEncoder.encode(df.format(fecinireporte), "UTF-8") + "&fechafin=" + URLEncoder.encode(df.format(fecfinreporte), "UTF-8") + "&Region=" + reg);//+"&Localidad="+loc+"&Region="+reg
                //FacesContext.getCurrentInstance().getExternalContext().redirect(("/birt/run?__report=report/ReportesCh/ch_totaprtipobr.rptdesign&__format=pdf&Tipo%20Solicitud=") + "&TipoSolicitud=" + tipototsolaprotipdepvlrsele + "&fechaini=" + URLEncoder.encode(df.format(fecinireporte), "UTF-8") + "&fechafin=" + URLEncoder.encode(df.format(fecfinreporte), "UTF-8"));
            } catch (IOException ex) {
                Logger.getLogger(SessionChsolicitud.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (reporteSeleccionado == 6 && origen.equals("3")) {//total aprobado por regiones+origen depto
            //else if(reporteSeleccionado==6 && regionesta==0 && localidadseleccionada!=""){
            try {
                List<Localidad> listadepto = solicitudService.filtroBuscarDepto(localidadseleccionada);
                // namerepro="ch_totaprtipsol";
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                FacesContext.getCurrentInstance().getExternalContext().redirect((urlbirt+"/birt/run?__report=report/ReportesCh/" + namerepro + ".rptdesign&__format=pdf&Tipo%20Solicitud=") + "&fechaini=" + URLEncoder.encode(df.format(fecinireporte), "UTF-8") + "&fechafin=" + URLEncoder.encode(df.format(fecfinreporte), "UTF-8") + "&Localidad=" + listadepto.get(0).getStrcodigolocalidad());//+"&Localidad="+loc+"&Region="+reg
                //FacesContext.getCurrentInstance().getExternalContext().redirect(("/birt/run?__report=report/ReportesCh/ch_totaprtipobr.rptdesign&__format=pdf&Tipo%20Solicitud=") + "&TipoSolicitud=" + tipototsolaprotipdepvlrsele + "&fechaini=" + URLEncoder.encode(df.format(fecinireporte), "UTF-8") + "&fechafin=" + URLEncoder.encode(df.format(fecfinreporte), "UTF-8"));
            } catch (IOException ex) {
                Logger.getLogger(SessionChsolicitud.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (reporteSeleccionado == 6 && origen.equals("4") && municiesta == 1) {//total aprobado por regiones+origen muni
            //else if(reporteSeleccionado==6 && regionesta==0 && localidadseleccionada=="" && municiesta==1){//total aprobado por regiones+origen depto
            try {
//                if(munifiltroseleccioando==""){

                List<Localidad> listamuni = solicitudService.filtroxMunicipio(munifiltroseleccioando);
                // namerepro="ch_totaprtipsol";
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                FacesContext.getCurrentInstance().getExternalContext().redirect((urlbirt+"/birt/run?__report=report/ReportesCh/" + namerepro + ".rptdesign&__format=pdf&Tipo%20Solicitud=") + "&fechaini=" + URLEncoder.encode(df.format(fecinireporte), "UTF-8") + "&fechafin=" + URLEncoder.encode(df.format(fecfinreporte), "UTF-8") + "&Localidad=" + listamuni.get(0).getStrcodigolocalidad());//+"&Localidad="+loc+"&Region="+reg
//                }else {//r007
//                    List<Localidad> listadepto=solicitudService.filtroxMunicipio(munifiltroseleccioando);
//                    namerepro="ch_totaprtipsol";
//                    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
//                    FacesContext.getCurrentInstance().getExternalContext().redirect(("/birt/run?__report=report/ReportesCh/"+namerepro+".rptdesign&__format=pdf&Tipo%20Solicitud=") + "&fechaini=" + URLEncoder.encode(df.format(fecinireporte), "UTF-8") + "&fechafin=" + URLEncoder.encode(df.format(fecfinreporte), "UTF-8")+"&Localidad="+listadepto.get(0).getStrcodigolocalidad());//+"&Localidad="+loc+"&Region="+reg
//                }
                //FacesContext.getCurrentInstance().getExternalContext().redirect(("/birt/run?__report=report/ReportesCh/ch_totaprtipobr.rptdesign&__format=pdf&Tipo%20Solicitud=") + "&TipoSolicitud=" + tipototsolaprotipdepvlrsele + "&fechaini=" + URLEncoder.encode(df.format(fecinireporte), "UTF-8") + "&fechafin=" + URLEncoder.encode(df.format(fecfinreporte), "UTF-8"));
            } catch (IOException ex) {
                Logger.getLogger(SessionChsolicitud.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (reporteSeleccionado == 7 && origen.equals("1")) {//total aprobado por tipo obra grafica nacional
            namerepro = "ch_totaprtipsol";
            try {
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                FacesContext.getCurrentInstance().getExternalContext().redirect((urlbirt+"/birt/run?__report=report/ReportesCh/" + namerepro + ".rptdesign&__format=pdf&Tipo%20Solicitud=") + "&fechaini=" + URLEncoder.encode(df.format(fecinireporte), "UTF-8") + "&fechafin=" + URLEncoder.encode(df.format(fecfinreporte), "UTF-8"));//+"&Localidad="+loc+"&Region="+reg
            } catch (IOException ex) {
                Logger.getLogger(SessionChsolicitud.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (reporteSeleccionado == 7 && origen.equals("2")) {//regio
            try {
                namerepro = "ch_totaprtipsol";
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                FacesContext.getCurrentInstance().getExternalContext().redirect((urlbirt+"/birt/run?__report=report/ReportesCh/" + namerepro + ".rptdesign&__format=pdf&Tipo%20Solicitud=") + "&fechaini=" + URLEncoder.encode(df.format(fecinireporte), "UTF-8") + "&fechafin=" + URLEncoder.encode(df.format(fecfinreporte), "UTF-8") + "&Region=" + reg);//+"&Localidad="+loc+"&Region="+reg
            } catch (IOException ex) {
                Logger.getLogger(SessionChsolicitud.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (reporteSeleccionado == 7 && origen.equals("3")) {//depto
            try {
                namerepro = "ch_totaprtipsol";
                List<Localidad> listadepto = solicitudService.filtroBuscarDepto(localidadseleccionada);
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                FacesContext.getCurrentInstance().getExternalContext().redirect((urlbirt+"/birt/run?__report=report/ReportesCh/" + namerepro + ".rptdesign&__format=pdf&Tipo%20Solicitud=") + "&fechaini=" + URLEncoder.encode(df.format(fecinireporte), "UTF-8") + "&fechafin=" + URLEncoder.encode(df.format(fecfinreporte), "UTF-8") + "&Localidad=" + listadepto.get(0).getStrcodigolocalidad());//+"&Localidad="+loc+"&Region="+reg
                //FacesContext.getCurrentInstance().getExternalContext().redirect(("/birt/run?__report=report/ReportesCh/"+namerepro+".rptdesign&__format=pdf&Tipo%20Solicitud=") + "&fechaini=" + URLEncoder.encode(df.format(fecinireporte), "UTF-8") + "&fechafin=" + URLEncoder.encode(df.format(fecfinreporte), "UTF-8")+"&Localidad="+listadepto.get(0).getStrcodigolocalidad());//+"&Localidad="+loc+"&Region="+reg
                //FacesContext.getCurrentInstance().getExternalContext().redirect(("/birt/run?__report=report/ReportesCh/ch_totaprtipobr.rptdesign&__format=pdf&Tipo%20Solicitud=") + "&TipoSolicitud=" + tipototsolaprotipdepvlrsele + "&fechaini=" + URLEncoder.encode(df.format(fecinireporte), "UTF-8") + "&fechafin=" + URLEncoder.encode(df.format(fecfinreporte), "UTF-8"));
            } catch (IOException ex) {
                Logger.getLogger(SessionChsolicitud.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (reporteSeleccionado == 7 && origen.equals("4")) {//muni
            try {
                namerepro = "ch_totaprtipsol";
                List<Localidad> listadepto = solicitudService.filtroxMunicipio(munifiltroseleccioando);
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                FacesContext.getCurrentInstance().getExternalContext().redirect((urlbirt+"/birt/run?__report=report/ReportesCh/" + namerepro + ".rptdesign&__format=pdf&Tipo%20Solicitud=") + "&fechaini=" + URLEncoder.encode(df.format(fecinireporte), "UTF-8") + "&fechafin=" + URLEncoder.encode(df.format(fecfinreporte), "UTF-8") + "&Localidad=" + listadepto.get(0).getStrcodigolocalidad());//+"&Localidad="+loc+"&Region="+reg
            } catch (IOException ex) {
                Logger.getLogger(SessionChsolicitud.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (reporteSeleccionado == 8 && origen.equals("1")) {//muni
            try {
                List<Localidad> listadepto = solicitudService.filtroxMunicipio(munifiltroseleccioando);
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                FacesContext.getCurrentInstance().getExternalContext().redirect((urlbirt+"/birt/run?__report=report/ReportesCh/" + namerepro + ".rptdesign&__format=pdf&Tipo%20Solicitud=") + "&fechaini=" + URLEncoder.encode(df.format(fecinireporte), "UTF-8") + "&fechafin=" + URLEncoder.encode(df.format(fecfinreporte), "UTF-8"));//+"&Localidad="+loc+"&Region="+reg
            } catch (IOException ex) {
                Logger.getLogger(SessionChsolicitud.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (reporteSeleccionado == 8 && origen.equals("2")) {//regio
            try {
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                FacesContext.getCurrentInstance().getExternalContext().redirect((urlbirt+"/birt/run?__report=report/ReportesCh/" + namerepro + ".rptdesign&__format=pdf&Tipo%20Solicitud=") + "&fechaini=" + URLEncoder.encode(df.format(fecinireporte), "UTF-8") + "&fechafin=" + URLEncoder.encode(df.format(fecfinreporte), "UTF-8") + "&Region=" + reg);//+"&Localidad="+loc+"&Region="+reg
            } catch (IOException ex) {
                Logger.getLogger(SessionChsolicitud.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (reporteSeleccionado == 8 && origen.equals("3")) {//depto
            try {
                List<Localidad> listadepto = solicitudService.filtroBuscarDepto(localidadseleccionada);
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                FacesContext.getCurrentInstance().getExternalContext().redirect((urlbirt+"/birt/run?__report=report/ReportesCh/" + namerepro + ".rptdesign&__format=pdf&Tipo%20Solicitud=") + "&fechaini=" + URLEncoder.encode(df.format(fecinireporte), "UTF-8") + "&fechafin=" + URLEncoder.encode(df.format(fecfinreporte), "UTF-8") + "&Localidad=" + listadepto.get(0).getStrcodigolocalidad());//+"&Localidad="+loc+"&Region="+reg
                //FacesContext.getCurrentInstance().getExternalContext().redirect(("/birt/run?__report=report/ReportesCh/"+namerepro+".rptdesign&__format=pdf&Tipo%20Solicitud=") + "&fechaini=" + URLEncoder.encode(df.format(fecinireporte), "UTF-8") + "&fechafin=" + URLEncoder.encode(df.format(fecfinreporte), "UTF-8")+"&Localidad="+listadepto.get(0).getStrcodigolocalidad());//+"&Localidad="+loc+"&Region="+reg
                //FacesContext.getCurrentInstance().getExternalContext().redirect(("/birt/run?__report=report/ReportesCh/ch_totaprtipobr.rptdesign&__format=pdf&Tipo%20Solicitud=") + "&TipoSolicitud=" + tipototsolaprotipdepvlrsele + "&fechaini=" + URLEncoder.encode(df.format(fecinireporte), "UTF-8") + "&fechafin=" + URLEncoder.encode(df.format(fecfinreporte), "UTF-8"));
            } catch (IOException ex) {
                Logger.getLogger(SessionChsolicitud.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (reporteSeleccionado == 8 && origen.equals("4")) {//muni
            try {
                List<Localidad> listadepto = solicitudService.filtroxMunicipio(munifiltroseleccioando);
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                FacesContext.getCurrentInstance().getExternalContext().redirect((urlbirt+"/birt/run?__report=report/ReportesCh/" + namerepro + ".rptdesign&__format=pdf&Tipo%20Solicitud=") + "&fechaini=" + URLEncoder.encode(df.format(fecinireporte), "UTF-8") + "&fechafin=" + URLEncoder.encode(df.format(fecfinreporte), "UTF-8") + "&Localidad=" + listadepto.get(0).getStrcodigolocalidad());//+"&Localidad="+loc+"&Region="+reg
            } catch (IOException ex) {
                Logger.getLogger(SessionChsolicitud.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (reporteSeleccionado == 9) {//muni
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect((urlbirt+"/birt/run?__report=report/ReportesCh/" + namerepro + ".rptdesign&__format=pdf&inttiposolicitud=") + tipototsolaprotipdepvlrsele);//+"&Localidad="+loc+"&Region="+reg
            } catch (IOException ex) {
                Logger.getLogger(SessionChsolicitud.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (reporteSeleccionado == 10) {//muni
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect((urlbirt+"/birt/run?__report=report/ReportesCh/" + namerepro + ".rptdesign&__format=pdf&inttiposolicitud=") + tipototsolaprotipdepvlrsele);//+"&Localidad="+loc+"&Region="+reg
            } catch (IOException ex) {
                Logger.getLogger(SessionChsolicitud.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public String reportePdfSolpreVSApro() {
        try {
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            FacesContext.getCurrentInstance().getExternalContext().redirect((urlbirt+"/birt/run?__report=report/obraschreport/ReporteCh_SolicitudesPreVsApro.rptdesign&__format=pdf&Tipo%20Solicitud=") + "&TipoSolicitud=" + tiposolitodossolivsapro + "&fechaini=" + URLEncoder.encode(df.format(fecinitodossolivsapro), "UTF-8") + "&fechafin=" + URLEncoder.encode(df.format(fecfintodossolivsapro), "UTF-8"));
        } catch (IOException ex) {
            Logger.getLogger(SessionChsolicitud.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public String reportePdfsolapro() {
        try {
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            FacesContext.getCurrentInstance().getExternalContext().redirect((urlbirt+"/birt/run?__report=report/obraschreport/ReporteCh_TotalAproObrasMayoMeno.rptdesign&__format=pdf&Tipo%20Solicitud=") + "&fechaini=" + URLEncoder.encode(df.format(fecinisolapro), "UTF-8") + "&fechafin=" + URLEncoder.encode(df.format(fecfinsolapro), "UTF-8"));
        } catch (IOException ex) {
            Logger.getLogger(SessionChsolicitud.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String reportePdfregio() {
        try {
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            FacesContext.getCurrentInstance().getExternalContext().redirect((urlbirt+"/birt/run?__report=report/obraschreport/ReporteCh_TotalAproTipoSoliPorRegion.rptdesign&__format=pdf&Tipo%20Solicitud=") + "&TipoSolicitud=" + tiposolitxregion + "&fechaini=" + URLEncoder.encode(df.format(fecinisolregi), "UTF-8") + "&fechafin=" + URLEncoder.encode(df.format(fecfinsolregi), "UTF-8"));
        } catch (IOException ex) {
            Logger.getLogger(SessionChsolicitud.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String reportePdfaproxtipo() {
        try {
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            FacesContext.getCurrentInstance().getExternalContext().redirect((urlbirt+"/birt/run?__report=report/obraschreport/ReporteCh_TotalAproPorTipoObra.rptdesign&__format=pdf&Tipo%20Solicitud=") + "&fechaini=" + URLEncoder.encode(df.format(fecinisolaprotipo), "UTF-8") + "&fechafin=" + URLEncoder.encode(df.format(fecfinsolaprotipo), "UTF-8"));
        } catch (IOException ex) {
            Logger.getLogger(SessionChsolicitud.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String reportePdfSolresumen() {
        try {
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            FacesContext.getCurrentInstance().getExternalContext().redirect((urlbirt+"/birt/run?__report=report/obraschreport/ReporteCh_ResumenPorTipoSolicitud.rptdesign&__format=pdf&Tipo%20Solicitud=") + "&TipoSolicitud=" + tiposoliresumen + "&fechaini=" + URLEncoder.encode(df.format(fecinisolresumen), "UTF-8") + "&fechafin=" + URLEncoder.encode(df.format(fecfinsolresumen), "UTF-8"));
        } catch (IOException ex) {
            Logger.getLogger(SessionChsolicitud.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public String reportePdfSoltotaprotipo() {
        try {
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            FacesContext.getCurrentInstance().getExternalContext().redirect((urlbirt+"/birt/run?__report=report/obraschreport/ReporteCh_TotalAproTipoSolicitud.rptdesign&__format=pdf&Tipo%20Solicitud=") + "&fechaini=" + URLEncoder.encode(df.format(fecinitotaprotipo), "UTF-8") + "&fechafin=" + URLEncoder.encode(df.format(fecfintotaprotipo), "UTF-8"));
        } catch (IOException ex) {
            Logger.getLogger(SessionChsolicitud.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public String reportePdfaproxtipodeobra() {
        try {
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            FacesContext.getCurrentInstance().getExternalContext().redirect((urlbirt+"/birt/run?__report=report/obraschreport/ReporteCh_AproObrasMayoMeno.rptdesign&__format=pdf&Tipo%20Solicitud=") + "&fechaini=" + URLEncoder.encode(df.format(feciniaproxtipodeobra), "UTF-8") + "&fechafin=" + URLEncoder.encode(df.format(fecfinaproxtipodeobra), "UTF-8"));
        } catch (IOException ex) {
            Logger.getLogger(SessionChsolicitud.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public String reportePdftotsolaprotipdepvlr() {
        try {
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            FacesContext.getCurrentInstance().getExternalContext().redirect((urlbirt+"/birt/run?__report=report/obraschreport/ReporteCh_TotalApro.rptdesign&__format=pdf&Tipo%20Solicitud=") + "&TipoSolicitud=" + tipototsolaprotipdepvlrsele + "&fechaini=" + URLEncoder.encode(df.format(fecinitotsolaprotipdepvlr), "UTF-8") + "&fechafin=" + URLEncoder.encode(df.format(fecfinatotsolaprotipdepvlr), "UTF-8"));
        } catch (IOException ex) {
            Logger.getLogger(SessionChsolicitud.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public void llenarMuniciposxFiltro() {
        List<Localidad> lista = solicitudService.listaMunicipios();
        itemsMunipfiltro = new SelectItem[lista.size()];
        int i = 0;
        while (i < lista.size()) {
            SelectItem opt = new SelectItem(lista.get(i).getStrmunicipio(), lista.get(i).getStrcodigolocalidad());
            itemsMunipfiltro[i] = opt;
//            if(i==1)
//            {
//                munifiltroseleccioando=lista.get(i).getStrcodigolocalidad();
//            }
            i++;
        }
    }

    public void llenarLocalidad() {
        List<Localidad> lista = solicitudService.filtroDepto();
        itemsLocalidades = new SelectItem[lista.size()];
        int i = 0;
        for (Localidad tipLoc : lista) {
            {
                itemsLocalidades[i] = new SelectItem(tipLoc.getStrdepartamento(), tipLoc.getStrcodigolocalidad());
                i++;
            }
        }
    }

    public String habilitarPerfil() {
        perfil = 0;
        listagrupo = getSolicitudService().encontrarGrupoUsuario(getUsuariolog().getUsuId());
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
}
