/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.gestion;

import co.com.interkont.cobra.to.ActividadReciente;
import co.com.interkont.cobra.to.Comentarioobra;
import co.com.interkont.cobra.to.Contratista;
import co.com.interkont.cobra.to.ControlPanel;
import co.com.interkont.cobra.to.Evento;
import co.com.interkont.cobra.to.Fase;
import co.com.interkont.cobra.to.JsfUsuarioGrupo;
import co.com.interkont.cobra.to.Localidad;
import co.com.interkont.cobra.to.Lugarobra;
import co.com.interkont.cobra.to.Mensaje;
import co.com.interkont.cobra.to.Novedad;
import co.com.interkont.cobra.to.Obra;
import co.com.interkont.cobra.to.Opinionciudadano;
import co.com.interkont.cobra.to.Region;
import co.com.interkont.cobra.to.Tercero;
import co.com.interkont.cobra.to.Tipoestadobra;
import co.com.interkont.cobra.to.Tipoobra;
import co.com.interkont.cobra.to.Tipoorigen;
import co.com.interkont.cobra.to.Tipoproyecto;
import co.com.interkont.cobra.to.Zonaespecifica;
import co.com.interkont.cobra.vista.VistaObraMapa;
import co.com.interkont.cobra.vista.VistaSeguidoresObra;
import cobra.DatosGeneralesPerfilControl;
import cobra.FiltroGerencial;
import cobra.FiltroObra;
import cobra.Marcador;
import cobra.SessionBeanCobra;
import cobra.Supervisor.AdministrarObraNew;
import cobra.Supervisor.DetalleObra;
import cobra.Supervisor.EntidadConvenio;
import cobra.Supervisor.FacesUtils;
import cobra.Supervisor.IngresarNuevaObra;
import cobra.Supervisor.TerceroEntidadLista;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import org.richfaces.component.UIDataTable;

/**
 *
 * @author desarrollo5
 */
public class HomeGestion implements Serializable {
    // <editor-fold defaultstate="collapsed" desc="Managed Component Definition">

    /**
     * <p>Automatically managed component initialization.
     * <strong>WARNING:</strong>
     * This method is automatically generated, so any user-specified code
     * inserted here is subject to being replaced.</p>
     */
    private Integer ubicaciondetalle = 0;
    //private Alimentacion alimentacionultima = new Alimentacion();

    public Integer getUbicaciondetalle() {
        return ubicaciondetalle;
    }

    public void setUbicaciondetalle(Integer ubicaciondetalle) {
        this.ubicaciondetalle = ubicaciondetalle;
    }
    private SelectItem[] tercerosUsuario;
    private SelectItem[] UbicacionObra;
    private SelectItem[] TipoObraUsuario;
    private HashMap<Integer, Double> porcentajes = new HashMap<Integer, Double>();
    private List<Localidad> lslocalidad = new ArrayList<Localidad>();
    /**
     * Listados de grupos al que pertenece el usuario
     */
    private List<JsfUsuarioGrupo> jsfUsuarioGrupos = new ArrayList<JsfUsuarioGrupo>();
    /**
     * Verdadero si el usuario ha ingresado al sistema sin registrarse
     */
    private boolean usuarioSinRegistro = true;

    /**
     * Get the value of datos_mapa
     *
     * @return the value of datos_mapa
     */
    public List<Localidad> getLslocalidad() {
        return lslocalidad;
    }

    public List<JsfUsuarioGrupo> getJsfUsuarioGrupos() {
        return jsfUsuarioGrupos;
    }

    public void setJsfUsuarioGrupos(List<JsfUsuarioGrupo> jsfUsuarioGrupos) {
        this.jsfUsuarioGrupos = jsfUsuarioGrupos;
    }

    public void setLslocalidad(List<Localidad> lslocalidad) {
        this.lslocalidad = lslocalidad;
    }

    /**
     * Set the value of datos_mapa
     *
     * @param datos_mapa new value of datos_mapa
     */
    public SelectItem[] getTipoObraUsuario() {

        return TipoObraUsuario;
    }

    public void setTipoObraUsuario(SelectItem[] TipoObraUsuario) {
        this.TipoObraUsuario = TipoObraUsuario;
    }

    /**
     * Get the value of ObrasUsuario
     *
     * @return the value of ObrasUsuario
     */
    /**
     * Set the value of ObrasUsuario
     *
     * @param ObrasUsuario new value of ObrasUsuario
     */
    public SelectItem[] getUbicacionObra() {
        return UbicacionObra;
    }

    public boolean isUsuarioSinRegistro() {
        return usuarioSinRegistro;
    }

    public void setUsuarioSinRegistro(boolean usuarioSinRegistro) {
        this.usuarioSinRegistro = usuarioSinRegistro;
    }

    public void setUbicacionObra(SelectItem[] UbicacionObra) {
        this.UbicacionObra = UbicacionObra;
    }

    public SelectItem[] getTercerosUsuario() {
        return tercerosUsuario;
    }

    public void setTercerosUsuario(SelectItem[] tercerosUsuario) {
        this.tercerosUsuario = tercerosUsuario;
    }
    private String txtPor1 = "";

    public String getTxtPor1() {
        return txtPor1;
    }

    public void setTxtPor1(String tf) {
        this.txtPor1 = tf;
    }
    private String txtPor2 = "";

    public String getTxtPor2() {
        return txtPor2;
    }

    public void setTxtPor2(String tf) {
        this.txtPor2 = tf;
    }
    private String txtPor11 = "";
    private String txtPor22 = "";

    public String getTxtPor11() {
        return txtPor11;
    }

    public void setTxtPor11(String txtPor11) {
        this.txtPor11 = txtPor11;
    }

    public String getTxtPor22() {
        return txtPor22;
    }

    public void setTxtPor22(String txtPor22) {
        this.txtPor22 = txtPor22;
    }
    // </editor-fold>
    //Novedades
    private String valorFiltroMensajeRecibido = "";
    private UIDataTable tablaMensajesRecibidos = new UIDataTable();
    private boolean verMapa = true;
    private UIDataTable tablaNovedadesTipo = new UIDataTable();
    private ResourceBundle bundle = getSessionBeanCobra().getBundle();
    private Marcador marcador = new Marcador();
    private List<Tercero> listTercerosUsuario = new ArrayList<Tercero>();
    private Localidad depto = new Localidad();
    private Localidad municipio = new Localidad();
    private Contratista contratista = new Contratista();
    private SelectItem[] tiposEstadoObra = new SelectItem[0];
    private SelectItem[] tiposProyectos = new SelectItem[0];
    private SelectItem[] fases = new SelectItem[0];
    private SelectItem[] departamentos = new SelectItem[0];
    private SelectItem[] zonaespe;
    private SelectItem[] municipios = new SelectItem[0];
    private SelectItem[] subTiposProyecto = new SelectItem[0];
    private Tipoproyecto tipoProyecto = new Tipoproyecto();
    private Tipoorigen tipoOrigenUsuario = new Tipoorigen();
    private Fase fase = new Fase();
    private boolean mostrarFiltroAvanzado;
    private FiltroObra filtro = new FiltroObra();
    private UIDataTable tablaobras = new UIDataTable();
    //private List<Obra> listaobrasusu = new ArrayList();
    private List<VistaObraMapa> listaobrasusu = new ArrayList();
    private int totalfilas = 0;
    private int pagina = 0;
    private int totalpaginas = 0;
    private boolean verultimosobra;
    private boolean veranteriorobra;
    private boolean aplicafiltroobra = false;
    private SelectItem[] eventos = new SelectItem[0];
    private SelectItem[] zonasespecificas = new SelectItem[0];
    private int opcionov = 0;
    private int inttipoorigen = 0;
    public boolean onNovedades = false;
    private List<Novedad> listaNovedades = new ArrayList<Novedad>();
    private String datosmapa = "";
    private String scriptmapa = "";
    private String mensaje = "";
    //CONTRATISTAS
    private List<Contratista> contratistas = new ArrayList<Contratista>();

    public List<Contratista> getContratistas() {
        return contratistas;
    }

    public void setContratistas(List<Contratista> contratistas) {
        this.contratistas = contratistas;
    }

//    public Alimentacion getAlimentacionultima() {
//        return alimentacionultima;
//    }
//
//    public void setAlimentacionultima(Alimentacion alimentacionultima) {
//        this.alimentacionultima = alimentacionultima;
//    }
    protected DetalleObra getDetalleObra() {
        return (DetalleObra) FacesUtils.getManagedBean("Supervisor$DetalleObra");
    }
    /**
     * Lista donde se almacenan los comentarios hechos por los ciudadanos
     */
    private List<Comentarioobra> listComentarioObra = new ArrayList<Comentarioobra>();
    /**
     * Variable utilizada para guardar la ruta de la imagen.
     */
    private String imgNovedad = "";
    /**
     * Lista para llenar las novedades y comentarios
     */
    private List<ActividadReciente> listarecientes = new ArrayList<ActividadReciente>();
    private List<Opinionciudadano> listaopinion = new ArrayList<Opinionciudadano>();

    public List<Opinionciudadano> getListaopinion() {
        return listaopinion;
    }

    public void setListaopinion(List<Opinionciudadano> listaopinion) {
        this.listaopinion = listaopinion;
    }

    public List<ActividadReciente> getListarecientes() {
        return listarecientes;
    }

    public void setListarecientes(List<ActividadReciente> listarecientes) {
        this.listarecientes = listarecientes;
    }

    public List<Comentarioobra> getListComentarioObra() {
        return listComentarioObra;
    }

    public void setListComentarioObra(List<Comentarioobra> listComentarioObra) {
        this.listComentarioObra = listComentarioObra;
    }

    public String getImgNovedad() {
        return imgNovedad;
    }

    public void setImgNovedad(String imgNovedad) {
        this.imgNovedad = imgNovedad;
    }

    public int getInttipoorigen() {
        return inttipoorigen;
    }

    public void setInttipoorigen(int inttipoorigen) {
        this.inttipoorigen = inttipoorigen;
    }

    public SelectItem[] getZonaespe() {
        return zonaespe;
    }

    public void setZonaespe(SelectItem[] zonaespe) {
        this.zonaespe = zonaespe;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getScriptmapa() {
        return scriptmapa;
    }

    public void setScriptmapa(String scriptmapa) {
        this.scriptmapa = scriptmapa;
    }

    public String getDatosmapa() {
        return datosmapa;
    }

    public void setDatosmapa(String datosmapa) {
        this.datosmapa = datosmapa;
    }

    public FiltroObra getFiltro() {
        return filtro;
    }

    public void setFiltro(FiltroObra filtro) {
        this.filtro = filtro;
    }

    public List<Novedad> getListaNovedades() {
        return listaNovedades;
    }

    public void setListaNovedades(List<Novedad> listaNovedades) {
        this.listaNovedades = listaNovedades;
    }

    public boolean isOnNovedades() {
        return onNovedades;
    }

    public void setOnNovedades(boolean onNovedades) {
        this.onNovedades = onNovedades;
    }

    public int getOpcionov() {
        return opcionov;
    }

    public void setOpcionov(int opcionov) {
        this.opcionov = opcionov;
    }

    public SelectItem[] getZonasespecificas() {
        return zonasespecificas;
    }

    public void setZonasespecificas(SelectItem[] zonasespecificas) {
        this.zonasespecificas = zonasespecificas;
    }

    public SelectItem[] getEventos() {
        return eventos;
    }

    public void setEventos(SelectItem[] eventos) {
        this.eventos = eventos;
    }

    public boolean isAplicafiltroobra() {
        return aplicafiltroobra;
    }

    public void setAplicafiltroobra(boolean aplicafiltroobra) {
        this.aplicafiltroobra = aplicafiltroobra;
    }

    public boolean isVeranteriorobra() {
        return veranteriorobra;
    }

    public void setVeranteriorobra(boolean veranteriorobra) {
        this.veranteriorobra = veranteriorobra;
    }

    public boolean isVerultimosobra() {
        return verultimosobra;
    }

    public void setVerultimosobra(boolean verultimosobra) {
        this.verultimosobra = verultimosobra;
    }

//    public List<Obra> getListaobrasusu() {
//        return listaobrasusu;
//    }
//
//    public void setListaobrasusu(List<Obra> listaobrasusu) {
//        this.listaobrasusu = listaobrasusu;
//    }
    public List<VistaObraMapa> getListaobrasusu() {
        return listaobrasusu;
    }

    public void setListaobrasusu(List<VistaObraMapa> listaobrasusu) {
        this.listaobrasusu = listaobrasusu;
    }

    public int getPagina() {
        return pagina;
    }

    public void setPagina(int pagina) {
        this.pagina = pagina;
    }

    public int getTotalfilas() {
        return totalfilas;
    }

    public void setTotalfilas(int totalfilas) {
        this.totalfilas = totalfilas;
    }

    public int getTotalpaginas() {
        return totalpaginas;
    }

    public void setTotalpaginas(int totalpaginas) {
        this.totalpaginas = totalpaginas;
    }

    public UIDataTable getTablaobras() {
        return tablaobras;
    }

    public void setTablaobras(UIDataTable tablaobras) {
        this.tablaobras = tablaobras;
    }

    public Contratista getContratista() {
        return contratista;
    }

    public void setContratista(Contratista contratista) {
        this.contratista = contratista;
    }
    private Integer idObra;

    public Integer getIdObra() {
        return idObra;
    }

    public void setIdObra(Integer idObra) {
        this.idObra = idObra;
    }

    public List<Tercero> getListTercerosUsuario() {
        return listTercerosUsuario;
    }

    public void setListTercerosUsuario(List<Tercero> listTercerosUsuario) {
        this.listTercerosUsuario = listTercerosUsuario;
    }
    private List<Tipoobra> listTiposObra = new ArrayList<Tipoobra>();

    public List<Tipoobra> getListTiposObra() {
        return listTiposObra;
    }

    public void setListTiposObra(List<Tipoobra> listTiposObra) {
        this.listTiposObra = listTiposObra;
    }
    private Integer obrasEncontradas;

    public Integer getObrasEncontradas() {
        return obrasEncontradas;
    }

    public void setObrasEncontradas(Integer obrasEncontradas) {
        this.obrasEncontradas = obrasEncontradas;
    }
    private List<Marcador> markers;

    public List<Marcador> getMarkers() {
        return markers;
    }

    public void setMarkers(List<Marcador> markers) {
        this.markers = markers;
    }

   

    public Marcador getMarcador() {
        return marcador;
    }

    public void setMarcador(Marcador marcador) {
        this.marcador = marcador;
    }

    public UIDataTable getTablaNovedadesTipo() {
        return tablaNovedadesTipo;
    }

    public void setTablaNovedadesTipo(UIDataTable tablaNovedadesTipo) {
        this.tablaNovedadesTipo = tablaNovedadesTipo;
    }
    //cambios2

    public boolean isVerMapa() {
        return verMapa;
    }

    public void setVerMapa(boolean verMapa) {
        this.verMapa = verMapa;
    }

    public UIDataTable getTablaMensajesRecibidos() {
        return tablaMensajesRecibidos;
    }

    public void setTablaMensajesRecibidos(UIDataTable tablaMensajesRecibidos) {
        this.tablaMensajesRecibidos = tablaMensajesRecibidos;
    }

    public String getValorFiltroMensajeRecibido() {
        return valorFiltroMensajeRecibido;
    }

    public void setValorFiltroMensajeRecibido(String valorFiltroMensajeRecibido) {
        this.valorFiltroMensajeRecibido = valorFiltroMensajeRecibido;
    }

    public Tipoorigen getTipoOrigenUsuario() {
        return tipoOrigenUsuario;
    }

    public void setTipoOrigenUsuario(Tipoorigen tipoOrigenUsuario) {
        this.tipoOrigenUsuario = tipoOrigenUsuario;
    }

    public Fase getFase() {
        return fase;
    }

    public void setFase(Fase fase) {
        this.fase = fase;
    }

    public boolean isMostrarFiltroAvanzado() {
        return mostrarFiltroAvanzado;
    }

    public void setMostrarFiltroAvanzado(boolean mostrarFiltroAvanzado) {
        this.mostrarFiltroAvanzado = mostrarFiltroAvanzado;
    }

    protected EntidadConvenio getEntidadConvenio() {
        return (EntidadConvenio) FacesUtils.getManagedBean("Supervisor$EntidadConvenio");
    }

    protected AdministrarObraNew getAdministrarObraNew() {
        return (AdministrarObraNew) FacesUtils.getManagedBean("Supervisor$AdministrarObraNew");
    }

    public void iniciarfiltro() {

        /**
         * igualando la variable Versioncobra para el conteo de los proyectos en
         * cobrafonade
         *
         * @param FiltroObra Version cobra
         */
        //Version cobra para saber que bundle esta apuntando para el conteo de los proyectos en cobrafonade
        filtro.setCuantiaini(null);
        filtro.setCuantiafin(null);
        filtro.setFactorpagina(100);
        filtro.setIntcodespecifico(0);
        filtro.setIntcodevento(1);
        filtro.setIntpagini(0);
        filtro.setIntregion(0);
        filtro.setIntregionespecifica(0);
        filtro.setIntsubtipoproyecto(0);
        filtro.setInttipoproyecto(0);
        filtro.setPalabraclave("");
        filtro.setStrcoddepto("0");
        filtro.setStrcontratista("");
        filtro.setStrmunicipio("0");
        filtro.setEncargofidu(0);
        filtro.setVercodigosedeeducativa(false);
        //filtro.setIntcodigoentidad(0);
        filtro.setCoddanesedeeducativa("");

        if (listTercerosUsuario.size() == 1) {
            filtro.setIntcodigoentidad(listTercerosUsuario.get(0).getIntcodigo());
            if (filtro.getIntcodigoentidad() == 5212) {

                filtro.setVercodigosedeeducativa(true);
            }
        } else {
            filtro.setIntcodigoentidad(0);
            filtro.setStrentidad("");
        }

    }

    public void iniciarHome() {
        llenarComboEntidades();
        ubicaciondetalle = 1;
        getSessionBeanCobra().llenadodatos();
        filtro.setIntvista(1);
        filtro.setIntestadoobra(-1);
        filtro.setIntcodfase(-1);
        filtro.setTipointervencion(-1);
        filtro.setBoolobraterminada(false);
        filtro.setIntregionespecifica(0);
        iniciarfiltro();
        primeroListProyectos();
        iniciarFiltroAvanzado();
        llenarTablaNovedades();
        llenarZonaEspecifica();
        //getPerfilControl().encontrarEtiqueta();
        //Debería utilizarse solo en ciudadano
        if (getSessionBeanCobra().getUsuarioObra().getRenderrecurso().btnslider_imagenes_ciudadano) {
            iniciarSlider();
        }
        ////******************
        jsfUsuarioGrupos = getSessionBeanCobra().getUsuarioService().encontrarGruposxUsuarioxenModulo(getSessionBeanCobra().getUsuarioObra().getUsuId(), 6);
        Iterator<JsfUsuarioGrupo> itJsfUsuarioGrupos = jsfUsuarioGrupos.iterator();

        while (itJsfUsuarioGrupos.hasNext()) {
            JsfUsuarioGrupo jsfUsuarioGrupo = itJsfUsuarioGrupos.next();
            if (jsfUsuarioGrupo.getGrupo().getGruGid() == 22) {
                usuarioSinRegistro = true;
            }
        }

    }

    public void configurarzonalocali() {
        llenarZonaEspecifica();
        filtro.setIntregionespecifica(-1);
        filtro.setStrmunicipio("-1");
        filtro.setStrcoddepto("-1");
    }

    protected DatosGeneralesPerfilControl getPerfilControl() {
        return (DatosGeneralesPerfilControl) FacesUtils.getManagedBean("perfilcontrol");
    }

    public HomeGestion() {

        if (getSessionBeanCobra().getUsuarioObra().getTercero().getStrnombre() != null) {
            iniciarHome();
        } else {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("../Inicio.xhtml");

            } catch (IOException ex) {
                Logger.getLogger(HomeGestion.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public String iniciarNuevaObra() {
//        Obra obra = getSessionBeanCobra().getCobraService().encontrarObraPorId(codigoObra);
//

        Obra obra = (Obra) tablaobras.getRowData();
        try {
            getIngresarNuevaObra().cargarObra(obra);
        } catch (Exception ex) {
            Logger.getLogger(HomeGestion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "nuevaobra";
    }

    public void iniciarFiltroAvanzado() {
        //filtro.setPalabraClave(new String());
        List<Tipoestadobra> listaEstadosObras = getSessionBeanCobra().getCobraService().encontrarEstadosObras();

        if (listaEstadosObras != null) {
            llenarComboEstadosObra(listaEstadosObras);
        }

        if (getSessionBeanCobra().getUsuarioObra().getTercero().getTipoOrigen().getIntidtipoorigen() == 4) {
            tipoOrigenUsuario = getSessionBeanCobra().getUsuarioObra().getTercero().getTipoOrigen();
            List<Localidad> deptos = getSessionBeanCobra().getCobraService().encontrarDepartamentos();
            llenarComboDeptos(deptos);

        } else if (getSessionBeanCobra().getUsuarioObra().getTercero().getTipoOrigen().getIntidtipoorigen() == 1) {
            tipoOrigenUsuario = getSessionBeanCobra().getUsuarioObra().getTercero().getTipoOrigen();
            String codigoLocalidad = getSessionBeanCobra().getUsuarioObra().getTercero().getLocalidadByStrcodigolocalidad().getStrcodigolocalidad();
            municipio = getSessionBeanCobra().getCobraService().encontrarLocalidadPorId(codigoLocalidad);
            filtro.setStrmunicipio(depto.getStrcodigolocalidad());

        } else if (getSessionBeanCobra().getUsuarioObra().getTercero().getTipoOrigen().getIntidtipoorigen() == 2) {
            tipoOrigenUsuario = getSessionBeanCobra().getUsuarioObra().getTercero().getTipoOrigen();
            String codigoLocalidad = getSessionBeanCobra().getUsuarioObra().getTercero().getLocalidadByStrcodigolocalidad().getStrcodigolocalidad();
            depto = getSessionBeanCobra().getCobraService().encontrarLocalidadPorId(codigoLocalidad);
            filtro.setStrcoddepto(depto.getStrcodigolocalidad());
            List<Localidad> listaMunicipios = getSessionBeanCobra().getCobraService().encontrarMunicipios(depto.getStrcodigolocalidad());
            llenarComboMunicipio(listaMunicipios);
        }

        List<Tipoproyecto> listaTiposProyecto = getSessionBeanCobra().getCobraService().encontrarTiposProyecto();

        if (listaTiposProyecto != null) {
            llenarComboTiposProyecto(listaTiposProyecto);
        }

        List<Fase> listaFases = getSessionBeanCobra().getCobraService().encontrarFase();
        List<Evento> listaEventos = getSessionBeanCobra().getCobraService().encontrarEventos();
        List<Zonaespecifica> listaZonaespecificas = getSessionBeanCobra().getCobraService().encontrarZonasEspecificas();
        List<Region> listaRegiones = getSessionBeanCobra().getCobraService().encontrarRegion();


        if (listaFases != null) {
            llenarComboFases(listaFases);
        }
        if (listaEventos != null) {
            llenarComboEventos(listaEventos);
        }
        if (listaZonaespecificas != null) {
            llenarComboZonasEspecificas(listaZonaespecificas);
        }
        getSessionBeanCobra().llenarPeriodoEvento();
        cargarSubtiposProyecto();


    }

    public void cargarMunicipios() {
        List<Localidad> listaMunicipios = getSessionBeanCobra().getCobraService().encontrarMunicipios(filtro.getStrcoddepto());
        llenarComboMunicipio(listaMunicipios);
    }

    public void cargarSubtiposProyecto() {
        int idTipoProyecto = filtro.getInttipoproyecto();
        List<Tipoobra> subtiposProyecto = getSessionBeanCobra().getCobraService().encontrarSubTiposProyectoxtipoproyecto(idTipoProyecto);
        llenarComboSubTiposProyecto(subtiposProyecto);
    }

    /**
     * <p>Callback method that is called after the component tree has been
     * restored, but before any event processing takes place. This method will
     * <strong>only</strong> be called on a postback request that is processing
     * a form submit. Customize this method to allocate resources that will be
     * required in your event handlers.</p>
     */
    /**
     * <p>This method is called when the session containing it is about to be
     * passivated. Typically, this occurs in a distributed servlet container
     * when the session is about to be transferred to a different container
     * instance, after which the
     * <code>activate()</code> method will be called to indicate that the
     * transfer is complete.</p>
     *
     * <p>You may customize this method to release references to session data or
     * resources that can not be serialized with the session itself.</p>
     */
    public void passivate() {
    }

    /**
     * <p>This method is called when the session containing it was
     * reactivated.</p>
     *
     * <p>You may customize this method to reacquire references to session data
     * or resources that could not be serialized with the session itself.</p>
     */
    public void activate() {
    }

    public void cargarMapa() {

        if (listaobrasusu != null) {
            //mapa.getChildren().clear();
            markers = new ArrayList();

            int i = 0;
            int contador = 0;

            //for (Iterator i = ObrasUsuario.iterator(); i.hasNext();) {
            while (i < listaobrasusu.size()) {


                //ObraMapa obra = (ObraMapa) i.next();
                //Obra obra = listaobrasusu.get(i);
                VistaObraMapa obra = listaobrasusu.get(i);
                Marcador marker = new Marcador();

                //marker.setObra(obra);
                //marker.setVistaobra(obra);
                marker.setLatitude(obra.getFloatlatitud().doubleValue() + "");
                marker.setLongitude(obra.getFloatlongitud().doubleValue() + "");
                marker.setJsVariable("marker_" + (contador++));

                marker.setIcon("/" + getSessionBeanCobra().getBundle().getString("versioncobra") + obra.obtenerPin());
                NumberFormat money = NumberFormat.getCurrencyInstance(new Locale("es", "CO", "Traditional_WIN"));

                String url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/faces/Supervisor/DetalleObra.xhtml";
                String urlSeguimiento = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/faces/SupervisionExterna/ListadoSeguimientos.xhtml";

                String urlnuevo = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/faces/NuevoProyecto/datosBasicos.xhtml";

                BigDecimal porcentaje = BigDecimal.valueOf(0);
                if (obra.getNumvalejecobra() != null && obra.getTipoestadobra().getIntestadoobra() > 0) {
                    porcentaje = obra.getNumvalejecobra();
                    porcentaje = porcentaje.multiply(BigDecimal.valueOf(100));
                    porcentaje = porcentaje.divide(obra.getNumvaltotobra(), 2, RoundingMode.HALF_UP);

                    if (porcentaje.compareTo(new BigDecimal(100.0)) > 0) {
                        porcentaje = new BigDecimal(100.0);
                    }

                }

                //            String desc="<tr><td>"+obra.getStrnombreobra()+"<br>" +
                //                    "</td><td><img height=30 width=50 src='http://www.appliedlanguage.com/flags_of_the_world/large_flag_of_egypt.gif'>"+
                //                    //"<img src=\"/Cobra" + obra.getStrimagenobra() + "\" width=\"160\" height=\"130\" align=\"middle\">"+
                //                    "</td></tr><tr><td><br></td><td><br>" +
                //                    "</td></tr>";

                StringBuilder descripcion = new StringBuilder();
                String version = bundle.getString("versioncobra");
                String host = bundle.getString("ipserver");//"74.207.234.207";
                descripcion.append("<div class=\"info_window\"><center><font color=\"#266085\"><b class=\"title_info_window\">");
                String nombre = obra.getStrnombreobra().replaceAll("[^a-zA-Z0-9á-úÁ-Ú\\-,.;$%:]+", " ");

                if (obra.getStrnombreobra().length() > 255) {
                    descripcion.append(nombre);
                } else {
                    descripcion.append(nombre);
                }

                descripcion.append("</b>");
                descripcion.append("<br>");

                if (obra.getStrimagenobra() != null && !obra.getStrimagenobra().equalsIgnoreCase("") && obra.getStrimagenobra().indexOf(".") != -1) {
                    descripcion.append("<img style=\"box-shadow: 2px 2px 5px #999;\" height=\"80\" width=\"120\" src=\"" + host + version);
                    descripcion.append(obra.getStrimagenobra());
                    descripcion.append("\">");
                } else {
                    descripcion.append("<img style=\"box-shadow: 2px 2px 5px #999;\" height=\"90\" width=\"120\" src=\"" + host + version + "/resources/imgs/noimagen_mapa.png\">");
                }

                descripcion.append("</center><br><b>" + bundle.getString("costodelaobra") + " </b>");
                descripcion.append(money.format(obra.getNumvaltotobra()));
                descripcion.append("<br>");
                descripcion.append("<b>" + bundle.getString("duracionobra") + "</b>");
                descripcion.append(obra.getIntplazoobra());
                descripcion.append(bundle.getString("dias"));
                descripcion.append("<br>");
                descripcion.append("<b>" + bundle.getString("houtubicacion") + "</b>");
                descripcion.append(obra.getStrdireccion());
                descripcion.append("<br>");
                descripcion.append("<b>" + bundle.getString("porcentajedeavance") + " </b>");
                descripcion.append(porcentaje.toString());
                descripcion.append("%");
                descripcion.append("<br>");
                descripcion.append("<b>" + bundle.getString("houtcontratante") + " </b>");
                descripcion.append(obra.getTercero().getStrnombrecompleto());
                descripcion.append("<br>");
                /*
                 descripcion.append("<b>" + bundle.getString("houtcontratis") + " </b>");
                 descripcion.append(obra.getStrcontratista());
                 descripcion.append("<br>");
                 *
                 */
                if (getSessionBeanCobra().getUsuarioObra().getRenderrecurso().isBtndetalleproyecto()) {

                    descripcion.append("<br><center><a id=\"link1_");
                    descripcion.append(obra.getIntcodigoobra());
                    descripcion.append("\" ");
                    descripcion.append("class=\"buttontxt2\" href=\"");
                    if (obra.getTipoestadobra().getIntestadoobra() == 0) {
                        descripcion.append(urlnuevo);
                    } else {
                        descripcion.append(url);
                    }
                    descripcion.append("?id=");
                    descripcion.append(obra.getIntcodigoobra());

                    descripcion.append("\">" + bundle.getString("accederalaobra") + "</a>");
                    if (obra.getSolicitud_obra() != null) {
                        descripcion.append("<a style=\"margin-left:1em\" id=\"link2_");
                        descripcion.append(obra.getIntcodigoobra());
                        descripcion.append("\" ");
                        descripcion.append("class=\"buttontxt2\" href=\"");
                        descripcion.append(urlSeguimiento);
                        descripcion.append("?id=");
                        descripcion.append(obra.getIntcodigoobra());
                        descripcion.append("\">&nbsp;" + bundle.getString("verseguimientos") + "</a>");
                    }
                    descripcion.append("</center></div>");

                } else {
                    if (obra.getTipoestadobra().getIntestadoobra() != 0) {
                        descripcion.append("<br><center><a id=\"link_");
                        descripcion.append(obra.getIntcodigoobra());
                        descripcion.append("\" ");
                        descripcion.append("class=\"buttontxt2\" href=\"");
                        if (obra.getTipoestadobra().getIntestadoobra() == 0) {
                            descripcion.append(urlnuevo);
                        } else {
                            descripcion.append(url);
                        }
                        descripcion.append("?id=");
                        descripcion.append(obra.getIntcodigoobra());

                        descripcion.append("\">" + bundle.getString("accederalaobra") + "</center></a></div>");
                    }
                }

                marker.setInformationWindow(descripcion.toString().replaceAll("\"", ""));
                /*
                 * LLenado de rutas
                 */
                if (obra.getRuta() != null) {

                    marker.setListapuntosruta(getSessionBeanCobra().getCobraService().encontrarPuntosReferenciaxRuta(obra.getRuta().getStrcodigotramo()));
                    marker.setVerlinea(true);
//
                }

                markers.add(marker);
                i++;

            }
            //mapa.getChildren().addAll(markers);
            //mapa.getChildren().add(cluster);
        }
    }

    public void llenarComboMunicipio(List listaMunicipios) {
        SelectItem[] TempMunicipios = new SelectItem[listaMunicipios.size() + 1];

        TempMunicipios[0] = new SelectItem(0, bundle.getString("todas"));

        int j = 1, k = 1;
        for (Iterator i = listaMunicipios.iterator(); i.hasNext();) {

            Localidad local = (Localidad) i.next();
            if (verificarMunicipio(local, j, TempMunicipios) == false) {
                SelectItem opt = new SelectItem(local.getStrcodigolocalidad(), local.getStrmunicipio());
                TempMunicipios[j] = opt;
                j++;

            }
        }
        municipios = new SelectItem[j];
        municipios[0] = new SelectItem(0, bundle.getString("todas"));
        while (k < j) {
            municipios[k] = TempMunicipios[k];
            k++;
        }
    }

    public boolean verificarMunicipio(Localidad local, int cant, SelectItem[] temporal) {
        int i = 1;
        while (i < cant) {
            if (local.getStrmunicipio().compareTo(temporal[i].getLabel()) == 0) {
                return true;
            }
            i++;
        }
        return false;
    }

    public void llenarZonaEspecifica() {
        getSessionBeanCobra().getCobraService().setListazonaespecificas(getSessionBeanCobra().getCobraService().encontrarZonaespecifica());
        zonaespe = new SelectItem[getSessionBeanCobra().getCobraService().getListazonaespecificas().size()];
        int i = 0;
        for (Zonaespecifica zona : getSessionBeanCobra().getCobraService().getListazonaespecificas()) {
            SelectItem zon = new SelectItem(zona.getIntidzonaespecifica(), zona.getStrdescripcionzona().toUpperCase());

            zonaespe[i++] = zon;
        }

    }

    public void llenarComboDeptos(List deptos) {
        SelectItem[] TempDeptos = new SelectItem[deptos.size() + 1];

        TempDeptos[0] = new SelectItem(0, bundle.getString("todas"));

        int j = 1, k = 1;
        for (Iterator i = deptos.iterator(); i.hasNext();) {

            Localidad local = (Localidad) i.next();
            if (verificarDepto(local, j, TempDeptos) == false) {
                SelectItem opt = new SelectItem(local.getStrcodigolocalidad(), local.getStrdepartamento());
                TempDeptos[j] = opt;
                j++;

            }
        }
        departamentos = new SelectItem[j];
        departamentos[0] = new SelectItem(0, bundle.getString("todas"));
        while (k < j) {
            departamentos[k] = TempDeptos[k];
            k++;
        }
    }

    public boolean verificarDepto(Localidad local, int cant, SelectItem[] temporal) {
        int i = 1;
        while (i < cant) {
            if (local.getStrdepartamento().compareTo(temporal[i].getLabel()) == 0) {
                return true;
            }
            i++;
        }
        return false;
    }

    public void llenarComboUbicaciones(List lugares) {
        SelectItem[] TempUbicacion = new SelectItem[lugares.size() + 1];

        TempUbicacion[0] = new SelectItem(0, bundle.getString("todas"));

        int j = 1, k = 1;
        for (Iterator i = lugares.iterator(); i.hasNext();) {

            Lugarobra lug = (Lugarobra) i.next();
            if (verificarLugar(lug, j, TempUbicacion) == false) {
                SelectItem opt = new SelectItem(lug.getIntidlugarobra(), lug.getStrdesclugarobra());
                TempUbicacion[j] = opt;
                j++;

            }
        }
        UbicacionObra = new SelectItem[j];
        UbicacionObra[0] = new SelectItem(0, bundle.getString("todas"));
        while (k < j) {
            UbicacionObra[k] = TempUbicacion[k];
            k++;
        }
    }

    public boolean verificarLugar(Lugarobra lugar, int cant, SelectItem[] temporal) {
        int i = 1;
        while (i < cant) {
            if (lugar.getStrdesclugarobra().compareTo(temporal[i].getLabel()) == 0) {
                return true;
            }
            i++;
        }
        return false;
    }

    public void llenarComboFases(List listFases) {
        fases = new SelectItem[listFases.size()];

        int j = 0;
        for (Iterator i = listFases.iterator(); i.hasNext();) {

            Fase fas = (Fase) i.next();

            SelectItem opt = new SelectItem(fas.getIntidfase(), fas.getStrnombre());
            fases[j] = opt;
            j++;
        }

    }

    /*------------------*/
    public void llenarComboEventos(List listaEventos) {

        eventos = new SelectItem[listaEventos.size()];
        int j = 0;
        for (Iterator i = listaEventos.iterator(); i.hasNext();) {

            Evento eve = (Evento) i.next();

            SelectItem opt = new SelectItem(eve.getIntidevento(), eve.getStrnombre());
            eventos[j] = opt;
            j++;

        }

    }

    public void llenarComboZonasEspecificas(List listaZonasEspecificas) {
        SelectItem[] TempZonaE = new SelectItem[listaZonasEspecificas.size() + 1];

        TempZonaE[0] = new SelectItem(0, bundle.getString("todos"));

        int j = 1, k = 1;
        for (Iterator i = listaZonasEspecificas.iterator(); i.hasNext();) {

            Zonaespecifica ze = (Zonaespecifica) i.next();
            if (verificarZonaE(ze, j, TempZonaE) == false) {
                SelectItem opt = new SelectItem(ze.getIntidzonaespecifica(), ze.getStrnombrezona());
                TempZonaE[j] = opt;
                j++;

            }
        }
        zonasespecificas = new SelectItem[j];
        zonasespecificas[0] = new SelectItem(0, bundle.getString("todos"));
        while (k < j) {
            zonasespecificas[k] = TempZonaE[k];
            k++;
        }
    }

    public boolean verificarFase(Fase fase, int cant, SelectItem[] temporal) {
        int i = 1;
        while (i < cant) {
            if (fase.getStrnombre().compareTo(temporal[i].getLabel()) == 0) {
                return true;
            }
            i++;
        }
        return false;
    }

    public boolean verificarZonaE(Zonaespecifica zonaespecifica, int cant, SelectItem[] temporal) {
        int i = 1;
        while (i < cant) {
            if (zonaespecifica.getStrnombrezona().compareTo(temporal[i].getLabel()) == 0) {
                return true;
            }
            i++;
        }
        return false;
    }

    public boolean verificarRegion(Region region, int cant, SelectItem[] temporal) {
        int i = 1;
        while (i < cant) {
            if (region.getStrnombre().compareTo(temporal[i].getLabel()) == 0) {
                return true;
            }
            i++;
        }
        return false;
    }

    public void llenarComboTiposProyecto(List listTiposProyectos) {

        tiposProyectos = new SelectItem[listTiposProyectos.size() + 1];

        int j = 0;
        SelectItem opt = new SelectItem(0, "Todos");
        tiposProyectos[j] = opt;
        j++;
        for (Iterator i = listTiposProyectos.iterator(); i.hasNext();) {

            Tipoproyecto tipo = (Tipoproyecto) i.next();

            opt = new SelectItem(tipo.getIntidtipoproyecto(), tipo.getStrnombre());
            tiposProyectos[j] = opt;
            j++;
        }

    }

    public void llenarComboSubTiposProyecto(List listSubTiposProyectos) {
        SelectItem[] TempSubTipoProyecto = new SelectItem[listSubTiposProyectos.size() + 1];

        TempSubTipoProyecto[0] = new SelectItem(0, bundle.getString("todos"));

        int j = 1, k = 1;
        for (Iterator i = listSubTiposProyectos.iterator(); i.hasNext();) {

            Tipoobra tipo = (Tipoobra) i.next();
            if (verificarSubTiposProyecto(tipo, j, TempSubTipoProyecto) == false) {
                SelectItem opt = new SelectItem(tipo.getInttipoobra(), tipo.getStrnombremedio());
                TempSubTipoProyecto[j] = opt;
                j++;

            }
        }
        subTiposProyecto = new SelectItem[j];
        subTiposProyecto[0] = new SelectItem(0, bundle.getString("todos"));
        while (k < j) {
            subTiposProyecto[k] = TempSubTipoProyecto[k];
            k++;
        }
    }

    public boolean verificarSubTiposProyecto(Tipoobra tipo, int cant, SelectItem[] temporal) {
        int i = 1;
        while (i < cant) {
            if (tipo.getStrnombrecorto().compareTo(temporal[i].getLabel()) == 0) {
                return true;
            }
            i++;
        }
        return false;
    }

    public void llenarComboTipoObra(List tipos) {

        SelectItem[] TempTipos = new SelectItem[tipos.size() + 1];

        TempTipos[0] = new SelectItem(0, bundle.getString("todos"));

        int j = 1, k = 1;
        for (Iterator i = tipos.iterator(); i.hasNext();) {

            Tipoobra tip = (Tipoobra) i.next();
            if (verificarTipo(tip, j, TempTipos) == false) {
                SelectItem opt = new SelectItem(tip.getInttipoobra(), tip.getStrdesctipoobra());
                TempTipos[j] = opt;
                j++;

            }
        }
        TipoObraUsuario = new SelectItem[j];
        TipoObraUsuario[0] = new SelectItem(0, bundle.getString("todos"));
        while (k < j) {
            TipoObraUsuario[k] = TempTipos[k];
            k++;
        }
    }

    public void llenarComboEstadosObra(List estados) {

        if (!filtro.isIsciu()) {
            tiposEstadoObra = new SelectItem[estados.size()];
        } else {
            tiposEstadoObra = new SelectItem[estados.size() - 1];
        }

        int j = 0;
        for (Iterator i = estados.iterator(); i.hasNext();) {

            Tipoestadobra tip = (Tipoestadobra) i.next();
            SelectItem opt = new SelectItem(tip.getIntestadoobra(), tip.getStrdesctipoestado());


            if (!filtro.isIsciu()) {

                tiposEstadoObra[j] = opt;
                j++;
            } else {
                if (tip.getIntestadoobra() != 0) {
                    tiposEstadoObra[j] = opt;
                    j++;
                }

            }


        }

    }

    public boolean verificarTipo(Tipoobra tipo, int cant, SelectItem[] temporal) {
        int i = 1;
        while (i < cant) {
            if (tipo.getStrdesctipoobra().compareTo(temporal[i].getLabel()) == 0) {
                return true;
            }
            i++;
        }
        return false;
    }

    public boolean verificarEstado(Tipoestadobra tipo, int cant, SelectItem[] temporal) {
        int i = 1;
        while (i < cant) {
            if (tipo.getStrdesctipoestado().compareTo(temporal[i].getLabel()) == 0) {
                return true;
            }
            i++;
        }
        return false;
    }
    private List<TerceroEntidadLista> temp = new ArrayList<TerceroEntidadLista>();

    public List<TerceroEntidadLista> getTemp() {
        return temp;
    }

    public void setTemp(List<TerceroEntidadLista> temp) {
        this.temp = temp;
    }

    public void llenarComboEntidades() {
        //getSessionBeanCobra().getUsuarioObra().getTercero().getTercerosForIntcodigoentidad();
        this.listTercerosUsuario = getSessionBeanCobra().getUsuarioService().encontrarEntidadesxtercero(getSessionBeanCobra().getUsuarioObra(), 6, false);
        //this.listTercerosUsuario = getSessionBeanCobra().getUsuarioService().encontrarTercerosUsuario(getSessionBeanCobra().getUsuarioObra());
        int j = 0;
        if (listTercerosUsuario.size() == 1) {
            tercerosUsuario = new SelectItem[listTercerosUsuario.size()];
            j = 0;
        } else {
            tercerosUsuario = new SelectItem[listTercerosUsuario.size() + 1];
            tercerosUsuario[0] = new SelectItem(bundle.getString("todos"), bundle.getString("todos"));
            filtro.setIntcodigoentidad(0);
            j = 1;
        }


        for (Iterator i = listTercerosUsuario.iterator(); i.hasNext();) {
            Tercero relent = (Tercero) i.next();
            SelectItem opt = new SelectItem(relent.getStrnombrecompleto());
            TerceroEntidadLista terce = new TerceroEntidadLista(relent.getIntcodigo(), relent.getStrnombrecompleto());
            temp.add(terce);
            if (j == 0) {
                filtro.setIntcodigoentidad(relent.getIntcodigo());
                filtro.setStrentidad(relent.getStrnombrecompleto());
            }
            tercerosUsuario[j++] = opt;

        }
        //dropContratante.setSelected(EntidadesUsuario[0]);
    }

    public void comboEntidadesContratoguardar() {
        filtro.setVercodigosedeeducativa(false);
        if (filtro.getStrentidad() != null) {
            Iterator ite = temp.iterator();
            int idtercero = 0;
            while (ite.hasNext()) {
                TerceroEntidadLista tempter = (TerceroEntidadLista) ite.next();
                if (filtro.getStrentidad().equals(tempter.getStrnombre())) {
                    idtercero = tempter.getCodigo();

                }
            }

            if (idtercero != 0) {

                filtro.setIntcodigoentidad(idtercero);
            } else {
                if (listTercerosUsuario.size() == 1) {
                    filtro.setIntcodigoentidad(listTercerosUsuario.get(0).getIntcodigo());
                } else {
                    filtro.setIntcodigoentidad(0);
                }
            }
            if (filtro.getIntcodigoentidad() == 5212) {

                filtro.setVercodigosedeeducativa(true);
            }
        } else {
            if (listTercerosUsuario.size() == 1) {
                filtro.setIntcodigoentidad(listTercerosUsuario.get(0).getIntcodigo());
            } else {
                filtro.setIntcodigoentidad(0);
            }
        }

    }

    public String activarvistamapa() {
        iniciarfiltro();
        filtro.setIntvista(1);
        getSessionBeanCobra().setActualcomentario(true);
        getSessionBeanCobra().setActualdash(true);
        primeroListProyectos();

        return "gestion";
    }

    public String filtrarObrasPorEstado() {
        //iniciarfiltro();
        String u = primeroListProyectos();
        return null;
    }

    public String limpiar_action() {
        // TODO: Process the button click action. Return value is a navigation
        // case name where null will return to the same page.

        cargarMapa();
        filtro = new FiltroObra();


        return null;
    }

    public String formatoFecha(String fechaini) {
        String fechafin = "";
        int i = 0;
        while (i < fechaini.length()) {
            if (String.valueOf(fechaini.charAt(i)).compareTo(" ") != 0) {
                fechafin = fechafin + String.valueOf(fechaini.charAt(i));
            } else {
                i = fechaini.length();
            }
            i++;
        }

        return fechafin;
    }

    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }

    protected IngresarNuevaObra getIngresarNuevaObra() {
        return (IngresarNuevaObra) FacesUtils.getManagedBean("Supervisor$IngresarNuevaObra");
    }
    private String comentario = "";

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    private String ListadoComentarios = "";

    public String getListadoComentarios() {
        return ListadoComentarios;
    }

    public void setListadoComentarios(String ListadoComentarios) {
        this.ListadoComentarios = ListadoComentarios;
    }

    public boolean filtrarMensajesRecibidos(Object actual) {
        Mensaje msj = (Mensaje) actual;
        if (this.valorFiltroMensajeRecibido.length() == 0) {
            return true;
        }
        if (msj.getStrasuntomensaje().toLowerCase().contains(this.valorFiltroMensajeRecibido.toLowerCase())) {
            return true;
        } else {
            return false;
        }
    }

    public String mostrarMapa() {
        this.verMapa = true;
        return null;
    }

    public String ocultarMapa() {
        this.verMapa = false;
        return null;
    }

    public HashMap<Integer, Double> getPorcentajes() {
        return porcentajes;
    }

    public void setPorcentajes(HashMap<Integer, Double> porcentajes) {
        this.porcentajes = porcentajes;
    }

    public String redirectDetalle() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.setAttribute("idObra", getIdObra());

        return "admindetalleObra";
    }

    public SelectItem[] getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(SelectItem[] departamentos) {
        this.departamentos = departamentos;
    }

    public SelectItem[] getFases() {
        return fases;
    }

    public void setFases(SelectItem[] fases) {
        this.fases = fases;
    }

    public SelectItem[] getMunicipios() {
        return municipios;
    }

    public void setMunicipios(SelectItem[] municipios) {
        this.municipios = municipios;
    }

    public SelectItem[] getTiposEstadoObra() {
        return tiposEstadoObra;
    }

    public void setTiposEstadoObra(SelectItem[] tiposEstadoObra) {
        this.tiposEstadoObra = tiposEstadoObra;
    }

    public SelectItem[] getTiposProyectos() {
        return tiposProyectos;
    }

    public void setTiposProyectos(SelectItem[] tiposProyectos) {
        this.tiposProyectos = tiposProyectos;
    }

    public Localidad getDepto() {
        return depto;
    }

    public void setDepto(Localidad depto) {
        this.depto = depto;
    }

    public Localidad getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Localidad municipio) {
        this.municipio = municipio;
    }

    public Tipoproyecto getTipoProyecto() {
        return tipoProyecto;
    }

    public void setTipoProyecto(Tipoproyecto tipoProyecto) {
        this.tipoProyecto = tipoProyecto;
    }

    public SelectItem[] getSubTiposProyecto() {
        return subTiposProyecto;
    }

    public void setSubTiposProyecto(SelectItem[] subTiposProyecto) {
        this.subTiposProyecto = subTiposProyecto;
    }

    public String filtroObrasActionMapaAvanModal() {
        System.out.println("ingresoo al sistema");
        if (filtro.getStrentidad() == null || filtro.getStrentidad().compareTo("") == 0) {
            if (listTercerosUsuario.size() == 1) {
                filtro.setIntcodigoentidad(listTercerosUsuario.get(0).getIntcodigo());
            } else {
                filtro.setIntcodigoentidad(0);
            }
        }
        //filtro.setBoolconvenio(false);
        primeroListProyectos();

        return null;

    }

    //public String  primeroObrasUsuario() {
    //ObrasUsuario = new ArrayList<Obra>(getSessionBeanCobra().getCobraService().encontrarObrasJsfUsuario(getSessionBeanCobra().getUsuarioObra(), null));
//
//        if (obrasusu.size() == 0) {
//            obrasIntersection = new ArrayList<Obra>();
//            if (!control.getTercero().isBoolobraslocalidad()) {
//                for (Iterator i = this.listTercerosUsuario.iterator(); i.hasNext();) {
//                    Tercero relent = (Tercero) i.next();
//                    obrasusu = getSessionBeanCobra().getCobraService().encontrarObrasPorTercero(relent);
//                    obrasIntersection.addAll(obrasusu);
//                    for (Iterator j = obrasusu.iterator(); j.hasNext();) {
//                        Obra obra = (Obra) j.next();
//                        if (obra.getTipoestadobra().getIntestadoobra() != 0) {
//                            ObrasUsuario.add(obra);
//                            lugares.add(obra.getLugarobra());
//                            tipos.add(obra.getTipoobra());
//                        }
//                    }
//                }
//            } else {
//
//                obrasusu = getSessionBeanCobra().getCobraService().encontrarObrasPorLocalidad(control.getTercero().getLocalidadByStrcodigolocalidad().getStrcodigolocalidad(),
//                        control.getTercero().getTipoOrigen().getIntidtipoorigen());
//                obrasIntersection = new ArrayList<Obra>();
//                obrasIntersection.addAll(obrasusu);
//
//                for (Iterator j = obrasusu.iterator(); j.hasNext();) {
//                    Obra obra = (Obra) j.next();
//                    if (obra.getTipoestadobra().getIntestadoobra() != 0) {
//                        ObrasUsuario.add(obra);
//                        lugares.add(obra.getLugarobra());
//                        tipos.add(obra.getTipoobra());
//                    }
//                }
//            }
//
//        } else {
//            obrasIntersection = new ArrayList<Obra>();
//            obrasIntersection.addAll(obrasusu);
//
//            for (Iterator j = obrasusu.iterator(); j.hasNext();) {
//                Obra obra = (Obra) j.next();
//                if (obra.getTipoestadobra().getIntestadoobra() != 0) {
//                    ObrasUsuario.add(obra);
//                    lugares.add(obra.getLugarobra());
//                    tipos.add(obra.getTipoobra());
//                }
//            }
//        }
    //llenarComboUbicaciones(lugares);
    //llenarComboTipoObra(tipos);
    //  return null;
    //}
    public boolean validarGrupo() {
        List<JsfUsuarioGrupo> usu = new ArrayList<JsfUsuarioGrupo>();
        boolean bol = false;
        int i = 0;
        usu = getSessionBeanCobra().getUsuarioService().encontrarGruposxUsuarioxenModulo(getSessionBeanCobra().getUsuarioObra().getUsuId(), 6);
        while (i < usu.size()) {
            if (usu.get(i).getGrupo().getGruGid() == 21) {
                i++;
                return true;
            } else {
                i++;
                return false;
            }
        }
        return bol;
    }

    public String primeroListProyectos() {
        obrasEncontradas = 0;

        //listaobrasusu = new ArrayList<Obra>();
        listaobrasusu = new ArrayList<VistaObraMapa>();
        filtro.setIntpagini(0);
        filtro.setIsciu(getSessionBeanCobra().getCobraService().isCiu());
        switch (filtro.getIntvista()) {
            case 1:
                if (getSessionBeanCobra().getCobraService().isCiu()) {
                    filtro.setFactorpagina(100);
                    //filtro.setIntestadoobra(1);
                    //listaobrasusu = new ArrayList<Obra>(getSessionBeanCobra().getCobraService().encontrarObrasJsfUsuario(getSessionBeanCobra().getUsuarioObra(), filtro));
                    listaobrasusu = new ArrayList<VistaObraMapa>(getSessionBeanCobra().getCobraService().encontrarVistaObrasJsfUsuario(getSessionBeanCobra().getUsuarioObra(), filtro));
                    cargarMapaCiudadano();
                } else {
                    filtro.setFactorpagina(100);
                    //listaobrasusu = new ArrayList<Obra>(getSessionBeanCobra().getCobraService().encontrarObrasJsfUsuario(getSessionBeanCobra().getUsuarioObra(), filtro));
                    listaobrasusu = new ArrayList<VistaObraMapa>(getSessionBeanCobra().getCobraService().encontrarVistaObrasJsfUsuario(getSessionBeanCobra().getUsuarioObra(), filtro));
                    cargarMapa();
                }
                break;
            case 2:
                filtro.setFactorpagina(9);
                //listaobrasusu = new ArrayList<Obra>(getSessionBeanCobra().getCobraService().encontrarObrasJsfUsuario(getSessionBeanCobra().getUsuarioObra(), filtro));
                listaobrasusu = new ArrayList<VistaObraMapa>(getSessionBeanCobra().getCobraService().encontrarVistaObrasJsfUsuario(getSessionBeanCobra().getUsuarioObra(), filtro));
                break;
            case 3:
                filtro.setFactorpagina(10);
                //listaobrasusu = new ArrayList<Obra>(getSessionBeanCobra().getCobraService().encontrarObrasJsfUsuario(getSessionBeanCobra().getUsuarioObra(), filtro));
                listaobrasusu = new ArrayList<VistaObraMapa>(getSessionBeanCobra().getCobraService().encontrarVistaObrasJsfUsuario(getSessionBeanCobra().getUsuarioObra(), filtro));
                break;
        }


        totalfilas = getSessionBeanCobra().getCobraService().encontrarNumeroObrasJsfUsuario(getSessionBeanCobra().getUsuarioObra(), filtro);

        pagina = 1;
        totalpaginas = 0;
        if (totalfilas <= filtro.getFactorpagina()) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / filtro.getFactorpagina();
            if (totalfilas % filtro.getFactorpagina() > 0) {
                totalpaginas++;
            }
        }
        veranteriorobra = false;
        if (totalpaginas > 1) {
            verultimosobra = true;
        } else {
            verultimosobra = false;
        }
        obrasEncontradas = listaobrasusu.size();

        return null;
    }

    public String siguienteProyectos() {

        obrasEncontradas = 0;
        int num = (pagina) * filtro.getFactorpagina();
        filtro.setIntpagini(num);

        switch (filtro.getIntvista()) {
            case 1:
                if (getSessionBeanCobra().getCobraService().isCiu()) {
                    // filtro.setIntestadoobra(1);
                    //listaobrasusu = new ArrayList<Obra>(getSessionBeanCobra().getCobraService().encontrarObrasJsfUsuario(getSessionBeanCobra().getUsuarioObra(), filtro));
                    listaobrasusu = new ArrayList<VistaObraMapa>(getSessionBeanCobra().getCobraService().encontrarVistaObrasJsfUsuario(getSessionBeanCobra().getUsuarioObra(), filtro));
                    cargarMapaCiudadano();
                } else {
                    //listaobrasusu = new ArrayList<Obra>(getSessionBeanCobra().getCobraService().encontrarObrasJsfUsuario(getSessionBeanCobra().getUsuarioObra(), filtro));
                    listaobrasusu = new ArrayList<VistaObraMapa>(getSessionBeanCobra().getCobraService().encontrarVistaObrasJsfUsuario(getSessionBeanCobra().getUsuarioObra(), filtro));
                    cargarMapa();
                }
                break;
            case 2:
            case 3:
                //listaobrasusu = new ArrayList<Obra>(getSessionBeanCobra().getCobraService().encontrarObrasJsfUsuario(getSessionBeanCobra().getUsuarioObra(), filtro));
                listaobrasusu = new ArrayList<VistaObraMapa>(getSessionBeanCobra().getCobraService().encontrarVistaObrasJsfUsuario(getSessionBeanCobra().getUsuarioObra(), filtro));
                break;
        }


        totalfilas = getSessionBeanCobra().getCobraService().encontrarNumeroObrasJsfUsuario(getSessionBeanCobra().getUsuarioObra(), filtro);


        if (totalfilas <= filtro.getFactorpagina()) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / filtro.getFactorpagina();
            if (totalfilas % filtro.getFactorpagina() > 0) {
                totalpaginas++;
            }
        }
        pagina = pagina + 1;
        if (pagina < totalpaginas) {
            verultimosobra = true;
        } else {
            verultimosobra = false;
        }
        veranteriorobra = true;

        obrasEncontradas = listaobrasusu.size();

        return null;
    }

    public String anterioresProyectos() {
        obrasEncontradas = 0;
        pagina = pagina - 1;
        int num = (pagina - 1) * filtro.getFactorpagina();

        filtro.setIntpagini(num);

        switch (filtro.getIntvista()) {
            case 1:
                if (getSessionBeanCobra().getCobraService().isCiu()) {
                    //filtro.setIntestadoobra(1);
                    //listaobrasusu = new ArrayList<Obra>(getSessionBeanCobra().getCobraService().encontrarObrasJsfUsuario(getSessionBeanCobra().getUsuarioObra(), filtro));
                    listaobrasusu = new ArrayList<VistaObraMapa>(getSessionBeanCobra().getCobraService().encontrarVistaObrasJsfUsuario(getSessionBeanCobra().getUsuarioObra(), filtro));
                    cargarMapaCiudadano();
                } else {
                    //filtro.setIntestadoobra(1);
                    //listaobrasusu = new ArrayList<Obra>(getSessionBeanCobra().getCobraService().encontrarObrasJsfUsuario(getSessionBeanCobra().getUsuarioObra(), filtro));
                    listaobrasusu = new ArrayList<VistaObraMapa>(getSessionBeanCobra().getCobraService().encontrarVistaObrasJsfUsuario(getSessionBeanCobra().getUsuarioObra(), filtro));
                    cargarMapa();
                }
                break;
            case 2:
            case 3:
                //listaobrasusu = new ArrayList<Obra>(getSessionBeanCobra().getCobraService().encontrarObrasJsfUsuario(getSessionBeanCobra().getUsuarioObra(), filtro));
                listaobrasusu = new ArrayList<VistaObraMapa>(getSessionBeanCobra().getCobraService().encontrarVistaObrasJsfUsuario(getSessionBeanCobra().getUsuarioObra(), filtro));
                break;
        }


        if (totalfilas <= filtro.getFactorpagina()) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / filtro.getFactorpagina();
            if (totalfilas % filtro.getFactorpagina() > 0) {
                totalpaginas++;
            }
        }

        if (pagina > 1) {
            veranteriorobra = true;
        } else {
            veranteriorobra = false;
        }
        verultimosobra = true;
        obrasEncontradas = listaobrasusu.size();
        return null;
    }

    public String ultimoProyectos() {
        obrasEncontradas = 0;
        int num = totalfilas - filtro.getFactorpagina();

        filtro.setIntpagini(num);

        switch (filtro.getIntvista()) {
            case 1:
                if (getSessionBeanCobra().getCobraService().isCiu()) {
                    // filtro.setIntestadoobra(1);
                    //listaobrasusu = new ArrayList<Obra>(getSessionBeanCobra().getCobraService().encontrarObrasJsfUsuario(getSessionBeanCobra().getUsuarioObra(), filtro));
                    listaobrasusu = new ArrayList<VistaObraMapa>(getSessionBeanCobra().getCobraService().encontrarVistaObrasJsfUsuario(getSessionBeanCobra().getUsuarioObra(), filtro));
                    cargarMapaCiudadano();
                } else {
                    //listaobrasusu = new ArrayList<Obra>(getSessionBeanCobra().getCobraService().encontrarObrasJsfUsuario(getSessionBeanCobra().getUsuarioObra(), filtro));
                    listaobrasusu = new ArrayList<VistaObraMapa>(getSessionBeanCobra().getCobraService().encontrarVistaObrasJsfUsuario(getSessionBeanCobra().getUsuarioObra(), filtro));
                    cargarMapa();
                }
                break;
            case 2:
            case 3:
                //listaobrasusu = new ArrayList<Obra>(getSessionBeanCobra().getCobraService().encontrarObrasJsfUsuario(getSessionBeanCobra().getUsuarioObra(), filtro));
                listaobrasusu = new ArrayList<VistaObraMapa>(getSessionBeanCobra().getCobraService().encontrarVistaObrasJsfUsuario(getSessionBeanCobra().getUsuarioObra(), filtro));
                break;
        }

        if (totalfilas <= filtro.getFactorpagina()) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / filtro.getFactorpagina();
            if (totalfilas % filtro.getFactorpagina() > 0) {
                totalpaginas++;
            }
        }
        pagina = totalpaginas;
        if (pagina < totalpaginas) {
            verultimosobra = true;
        } else {
            verultimosobra = false;
        }
        veranteriorobra = true;
        obrasEncontradas = listaobrasusu.size();
        return null;
    }

    public boolean getResulbusqueda() {
        if (listaobrasusu.size() <= 0) {
            if (totalfilas > 0) {
                setMensaje(bundle.getString("nogeorefernciado"));
            } else {
                setMensaje(bundle.getString("notieneproyectos"));
            }
            return true;
        }
        return false;
    }

    public String llenarTablaNovedades() {
        llenarComentarios();
        opcionov = 1;
        listarecientes = new ArrayList<ActividadReciente>();
        int cantidad;
        if (listComentarioObra.size() == 0) {
            cantidad = 5;
        } else {
            cantidad = 5 - listComentarioObra.size();
        }
        listaNovedades = getSessionBeanCobra().getUsuarioService().encontrarUltimasNovedadesUsuario(getSessionBeanCobra().getUsuarioObra().getTercero().getIntcodigo(), cantidad);
        if (listaNovedades.isEmpty()) {

            if (getSessionBeanCobra().getUsuarioObra().getTercero().getTipoOrigen().getIntidtipoorigen() == 4) {
                int totalnov = getSessionBeanCobra().getCobraService().NumtotalNovedades();
                listaNovedades = getSessionBeanCobra().getCobraService().obtenerUltimasNovedades(totalnov - cantidad, totalnov);


            }
            //onNovedades = true;
        }

        //Castea Novedades y Comentario a tipo Actividad Reciente
        for (Novedad nov : listaNovedades) {
            ActividadReciente act = new ActividadReciente();
            act.setCodObra(nov.getObra().getIntcodigoobra());
            act.setNombreobra(nov.getObra().getStrnombrecrot());
            act.setStrNovedad_comentario(nov.getTiponovedad().getStrtiponovedad());
            act.setStrrutaimg(nov.getTiponovedad().getStrimgnovedad());
            act.setIntestadoobra(nov.getObra().getTipoestadobra().getIntestadoobra());
            act.setTextoinicial(getSessionBeanCobra().getBundle().getString("proyectonovedades"));
            act.setTextofinal(act.getStrNovedad_comentario());
            listarecientes.add(act);
        }
        for (Comentarioobra comen : listComentarioObra) {
            ActividadReciente acti = new ActividadReciente();
            if (comen.getObra() != null) {
                acti.setCodObra(comen.getObra().getIntcodigoobra());
                acti.setIntestadoobra(comen.getObra().getTipoestadobra().getIntestadoobra());
                acti.setNombreobra(comen.getObra().getStrnombrecrot());
                acti.setNombreciudadano(comen.getJsfUsuario().getTercero().getStrnombrecompleto());
                acti.setStrNovedad_comentario("Ha Comentado");
                acti.setStrrutaimg("/resources/imgs/nov_usuario.png");
                acti.setTextoinicial(comen.getStrdesccoment());
            } else {
                acti.setNombreciudadano(comen.getJsfUsuario().getTercero().getStrnombrecompleto());
                acti.setStrNovedad_comentario(comen.getStrdesccoment());
                acti.setStrrutaimg("/resources/imgs/nov_usuario.png");
                acti.setTextoinicial(comen.getStrdesccoment());
            }

            acti.setTextofinal("");
            listarecientes.add(acti);

        }


        return null;
    }

    public String llenarComentarios() {
        this.listComentarioObra.clear();
        listComentarioObra = getSessionBeanCobra().getCiudadanoservice().encontrarUltimosComentariosCiudadano();

        return null;
    }
    private ControlPanel controlp = new ControlPanel();

    public ControlPanel getControlp() {
        return controlp;
    }

    public void setControlp(ControlPanel controlp) {
        this.controlp = controlp;
    }
    private int num = 1000;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String reportePdfFichaCorto() {
        try {
            if (filtro.getStrcoddepto().equals("0")) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdftotalfichacorto") + "&munici=169");
            } else {
                if (filtro.getStrmunicipio().equals("-1")) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdftotalfichacorto") + "&munici=" + filtro.getStrcoddepto());
                } else {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdftotalfichacorto") + "&munici=" + filtro.getStrmunicipio());
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(HomeGestion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String reportePdfFichaObras() {
        try {
            if (filtro.getStrcoddepto().equals("0")) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdftotalfichaobras") + "&munici=169");
            } else {
                if (filtro.getStrmunicipio().equals("-1")) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdftotalfichaobras") + "&munici=" + filtro.getStrcoddepto());
                } else {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdftotalfichaobras") + "&munici=" + filtro.getStrmunicipio());
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(HomeGestion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String reportePdfFichaConvenio() {
        try {
            if (filtro.getStrcoddepto().equals("0")) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdftotalfichaconvenio") + "&munici=169");
            } else {
                if (filtro.getStrmunicipio().equals("-1")) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdftotalfichaconvenio") + "&munici=" + filtro.getStrcoddepto());
                } else {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdftotalfichaconvenio") + "&munici=" + filtro.getStrmunicipio());
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(HomeGestion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void cargarMapaCiudadano() {

        if (listaobrasusu != null) {
            //mapa.getChildren().clear();
            markers = new ArrayList();

            int i = 0;
            int contador = 0;

            //for (Iterator i = ObrasUsuario.iterator(); i.hasNext();) {
            while (i < listaobrasusu.size()) {
                //Obra obra = listaobrasusu.get(i);
                VistaObraMapa obra = listaobrasusu.get(i);
                Marcador marker = new Marcador();
                //setAlimentacionultima(new Alimentacion());
                //setAlimentacionultima(getSessionBeanCobra().getCobraService().obtenerUltimaalimentacion(obra.getIntcodigoobra()));

                //marker.setObra(obra);
                //marker.setVistaobra(obra);
                marker.setLatitude(obra.getFloatlatitud().doubleValue() + "");
                marker.setLongitude(obra.getFloatlongitud().doubleValue() + "");
                marker.setJsVariable("marker_" + (contador++));


                marker.setIcon("/" + getSessionBeanCobra().getBundle().getString("versioncobra") + obra.obtenerPin());

                NumberFormat money = NumberFormat.getCurrencyInstance(new Locale("es", "CO", "Traditional_WIN"));

                String url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/faces/Supervisor/DetalleObra.xhtml";
                String urlDocumento = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/faces/Supervisor/DocumentoObra.xhtml";
                String urlImages = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/faces/Supervisor/ImagenObra.xhtml";
                String urlComentario = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/faces/Ciudadano/ParticipacionCiudadano.xhtml";
                BigDecimal porcentaje = BigDecimal.valueOf(0);
                if (obra.getNumvalejecobra() != null && obra.getTipoestadobra().getIntestadoobra() > 0) {
                    porcentaje = obra.getNumvalejecobra();
                    porcentaje = porcentaje.multiply(BigDecimal.valueOf(100));
                    porcentaje = porcentaje.divide(obra.getNumvaltotobra(), 2, RoundingMode.HALF_UP);

                }

                StringBuilder descripcion = new StringBuilder();
                String version = bundle.getString("versioncobra");
                String host = bundle.getString("ipserver");

                Double asi_va = Double.parseDouble(porcentaje.setScale(2, RoundingMode.HALF_UP).toString());
                //asi_va = (asi_va * 70) / 100;
                Double deberia_ir = 0.0;



                int asi_va_text = (int) Math.floor(asi_va);
                int deberia_ir_text = 0;
                if (obra.getDeberiaestar() != null) {
                    deberia_ir = obra.getDeberiaestar().setScale(2, RoundingMode.HALF_UP).doubleValue();
                    deberia_ir_text = (int) Math.floor(deberia_ir);
                }
                List<VistaSeguidoresObra> list_seguidores = new ArrayList<VistaSeguidoresObra>();
                list_seguidores = getSessionBeanCobra().getCobraService().encontrarSeguidoresxObraxLimit(obra.getIntcodigoobra(), 6);
                String table_seguidores = "";
                int num_seguidores = list_seguidores.size();
                int _count = 0;
                if (num_seguidores > 0) {
                    table_seguidores += "<table class=\"table-contact\">";
                    for (int seg = 0; seg < num_seguidores; seg++) {
                        String _url_img = list_seguidores.get(seg).getStrfoto();
                        if (_url_img == null) {
                            _url_img = "/resources/imgs/valla/usu_photo.png";
                        }
                        _count++;
                        if (_count == 1) {
                            table_seguidores += "<tr>";
                            table_seguidores += "<td><img src=\"" + host + version + _url_img + "\" /></td>";
                        } else if (_count == 3) {
                            table_seguidores += "<td><img src=\"" + host + version + _url_img + "\" /></td>";
                            table_seguidores += "</tr>";
                            _count = 0;
                        } else {
                            table_seguidores += "<td><img src=\"" + host + version + _url_img + "\" /></td>";
                        }
                    }
                    table_seguidores += "</table>";
                }


                contratistas = (getSessionBeanCobra().getCobraService().obtenerContratistaporobra(obra.getIntcodigoobra()));
                String list_contratistas = "";
                if (contratistas != null) {
                    if (!contratistas.isEmpty()) {
                        if (contratistas.size() == 1) {
                            list_contratistas += "<label>";
                            list_contratistas += contratistas.get(0).getStrnombre();
                            list_contratistas += "</label>";
                        } else {
                            list_contratistas += "<label class=\"tool-valla\">";
                            list_contratistas += "Ver contratistas";
                            list_contratistas += "<div>";
                            for (Contratista cont : contratistas) {
                                if (cont.getStrnombre() != null) {
                                    list_contratistas += "<p>";
                                    list_contratistas += cont.getStrnombre();
                                    list_contratistas += "</p>";
                                }
                            }
                            list_contratistas += "</div>";
                            list_contratistas += "</label>";
                        }
                    }
                }

                descripcion.append("<html>");
                descripcion.append("<body>");
                descripcion.append("<div class=\"modal-content\">");
                descripcion.append("<div class=\"modal-header\">");
                descripcion.append("<table>");
                descripcion.append("<tr>");
                descripcion.append("<td >");
                descripcion.append("<img src=\"/" + version + "/resources/imgs/valla/imagotipo_siente.png\" width=\"45\" height=\"45\" style=\"margin-left:\"\"20px;\"/>");
                descripcion.append("</td>");
                descripcion.append("<td >");
                descripcion.append("<div class=\"modal-title\">");
                descripcion.append("<label class=\"lable-title\">");
                descripcion.append(obra.getStrnombrecrot());
                descripcion.append("</label>");
                descripcion.append("</div>");
                descripcion.append("<div class=\"modal-title\">");
                descripcion.append("<label class=\"lable-direccion\">");
                descripcion.append(obra.getStrdireccion());
                descripcion.append("</label>");
                descripcion.append("</div>");
                descripcion.append("</td>");
                descripcion.append("<td >");
                descripcion.append("</td>");
                descripcion.append("</tr>");
                descripcion.append("</table>");
                descripcion.append("</div>");
                descripcion.append("<div class=\"modal-body\">");
                descripcion.append("<table class=\"table-content\">");
                descripcion.append("<tr>");
                descripcion.append("<td>");
                descripcion.append("<table class=\"table-state\">");
                descripcion.append("<tr>");
                descripcion.append("<td>");
                descripcion.append("<table class=\"table-state-img\">");
                descripcion.append("<tr>");
                descripcion.append("<td>");
                if (obra.getSemaforo() != null && obra.getSemaforo().compareTo("") != 0) {
                    descripcion.append("<img width=\"60\" height=\"60\" src=\"" + host + version + obra.getSemaforo() + "\" />");
                }
                descripcion.append("</td>");
                descripcion.append("<td>");
                descripcion.append("<div class=\"table-avance\">");
                descripcion.append("<div >");
                descripcion.append("<div>");
                descripcion.append("<center>");
                descripcion.append("<label>Asi va</label>");
                descripcion.append("<br/>");
                descripcion.append("<label class=\"asi-va-value\">");
                descripcion.append(asi_va_text).append("%");
                descripcion.append("</label>");
                descripcion.append("</center>");
                descripcion.append("</div>");
                descripcion.append("<img src=\"/").append(version).append("/resources/imgs/valla/avance_fisico.png\" class=\"line-avance\"/>");
                descripcion.append("<img src=\"/").append(version).append("/resources/imgs/valla/circulo.png\" class=\"circle-avance\" style=\"margin-left:\"").append((asi_va * 85) / 100).append("px;\"/>");
                descripcion.append("</div>");
                descripcion.append("<div class=\"position-value-text\" style=\"position: relative; top: -13px\">");
                descripcion.append("<center >");
                descripcion.append("<label>Deberia ir</label>");
                descripcion.append("<br/>");
                descripcion.append("<label class=\"asi-va-value\">");
                descripcion.append(deberia_ir_text).append("%");
                descripcion.append("</label>");
                descripcion.append("</center>");
                descripcion.append("</div>");
                descripcion.append("<div style=\"position: relative; top: -13px\">");
                descripcion.append("<img src=\"/").append(version).append("/resources/imgs/valla/avance_fisico.png\" class=\"line-avance\"/>");
                descripcion.append("<img src=\"/").append(version).append("/resources/imgs/valla/circulo.png\" class=\"circle-avance\" style=\"margin-left:\"").append((deberia_ir * 85) / 100).append("px;\"/>");
                descripcion.append("</div>");
                descripcion.append("</div>");
                descripcion.append("</td>");
                descripcion.append("</tr>");
                descripcion.append("</table>");
                descripcion.append("</td>");
                descripcion.append("</tr>");
                descripcion.append("<tr>");
                descripcion.append("<td>");
                descripcion.append("<div class=\"info-title-valla\">");
                descripcion.append("<line class=\"info-title-valla-line\">SEGUIDORES</line>");
                descripcion.append("</div>");
                descripcion.append("</td>");
                descripcion.append("</tr>");
                descripcion.append("<tr>");
                descripcion.append("<td>");
                descripcion.append(table_seguidores);
                descripcion.append("</td>");
                descripcion.append("</tr>");
                descripcion.append("</table>");
                descripcion.append("</td>");
                descripcion.append("<td>");
                descripcion.append("<table class=\"table-info\">");
                descripcion.append("<tr>");
                descripcion.append("<td>");
                descripcion.append("<div class=\"info-title-valla\">");
                descripcion.append("<line class=\"info-title-valla-line\">CUANTO CUESTA EL PROYECTO?</line>");
                descripcion.append("</div>");
                descripcion.append("</td>");
                descripcion.append("</tr>");
                descripcion.append("<tr>");
                descripcion.append("<td>");
                descripcion.append("<div class=\"info-value-valla\">");
                descripcion.append("<lavel>");
                descripcion.append(money.format(obra.getNumvaltotobra()));
                descripcion.append("</label>");
                descripcion.append("</div>");
                descripcion.append("</td>");
                descripcion.append("</tr>");
                descripcion.append("<tr>");
                descripcion.append("<td>");
                descripcion.append("<table>");
                descripcion.append("<tr>");
                descripcion.append("<td>");
                descripcion.append("<div class=\"info-contratante-valla\">");
                descripcion.append("<line class=\"info-title-valla-line\">CONTRATANTE</line>");
                descripcion.append("</div>");
                descripcion.append("</td>");
                descripcion.append("<td>");
                descripcion.append("<div class=\"info-contratista-valla\">");
                descripcion.append("<line class=\"info-title-valla-line\">CONTRATISTA</line>");
                descripcion.append("</div>");
                descripcion.append("</td>");
                descripcion.append("</tr>");
                descripcion.append("<tr>");
                descripcion.append("<td class=\"stile-text-contrato-valla\">");
                descripcion.append("<label >");
                descripcion.append(obra.getTercero().getStrnombrecompleto());
                descripcion.append("</label>");
                descripcion.append("</td>");
                descripcion.append("<td class=\"stile-text-contrato-valla\">");
                descripcion.append(list_contratistas);
                descripcion.append("</td>");
                descripcion.append("</tr>");
                descripcion.append("</table>");
                descripcion.append("</td>");
                descripcion.append("</tr>");
                descripcion.append("<tr>");
                descripcion.append("<td>");
                descripcion.append("<div class=\"info-objeto-valla\">");
                descripcion.append("<line class=\"info-title-valla-line\">OBJETO</line>");
                descripcion.append("</div>");
                descripcion.append("</td>");
                descripcion.append("</tr>");
                descripcion.append("<tr>");
                descripcion.append("<td class=\"stile-text-objeto-valla\">");
                descripcion.append("<div class=\"scroll\">");
                descripcion.append("<p>");
                descripcion.append(obra.getStrobjetoobra().replaceAll("[^a-zA-Z0-9á-úÁ-Ú\\-,.;$%:]+", " "));
                descripcion.append("</p>");
                descripcion.append("</div>");
                descripcion.append("</td>");
                descripcion.append("</tr>");
                descripcion.append("</table>");
                descripcion.append("</td>");
                descripcion.append("<td>");
                descripcion.append("<table class=\"table-img\">");
                descripcion.append("<tr>");
                descripcion.append("<td>");
                descripcion.append("<table>");
                descripcion.append("<tr>");
                descripcion.append("<td>");
                if (obra.getStrimagenobra() != null && !obra.getStrimagenobra().equalsIgnoreCase("") && obra.getStrimagenobra().indexOf(".") != -1) {
                    descripcion.append("<img class=\"table-img-content\" src=\"").append(host).append(version);
                    descripcion.append(obra.getStrimagenobra());
                    descripcion.append("\">");
                } else {
                    descripcion.append("<img class=\"table-img-content\" src=\"").append(host).append(version).append("/resources/imgs/noimagen_mapa.png\">");
                }
                descripcion.append("</td>");
                descripcion.append("</tr>");
                descripcion.append("<tr>");
                descripcion.append("<td>");
                descripcion.append("<table class=\"table-img-actions-valla\">");
                descripcion.append("<tr >");
                if (obra.getTipoestadobra().getIntestadoobra() != 0) {
                    descripcion.append("<td class=\"title-tool\">");
                    descripcion.append("<a href=\"").append(url).append("?id=").append(obra.getIntcodigoobra()).append("\" value=\"Información\" >");
                    descripcion.append("<img src=\"/").append(version).append("/resources/imgs/valla/btn_info.png\" />");
                    descripcion.append("</a>");
                    descripcion.append("</td>");
                    if (!getSessionBeanCobra().getUsuarioObra().getUsuLogin().equals(bundle.getString("ciudadanosinregistro"))) {
                        descripcion.append("<td class=\"title-tool\">");
                        descripcion.append("<a href=\"").append(urlComentario).append("?id=").append(obra.getIntcodigoobra()).append("\" value=\"Comentarios\" >");
                        descripcion.append("<img src=\"/").append(version).append("/resources/imgs/valla/btn_comentarios.png\" />");
                        descripcion.append("</a>");
                        descripcion.append("</td>");
                    }
                    descripcion.append("<td class=\"title-tool\">");
                    descripcion.append("<a href=\"").append(urlImages).append("?id=").append(obra.getIntcodigoobra()).append("\" value=\"Fotos\" >");
                    descripcion.append("<img src=\"/").append(version).append("/resources/imgs/valla/btn_fotos.png\" />");
                    descripcion.append("</a>");
                    descripcion.append("</td>");
                    descripcion.append("<td>");
                    descripcion.append("<a href=\"").append(url).append("?id=").append(obra.getIntcodigoobra()).append("\" class=\"button-valla\" >");
                    descripcion.append("Ver proyecto");
                    descripcion.append("</a>");
                    descripcion.append("</td>");
                }
                descripcion.append("</tr>");
                descripcion.append("</table>");
                descripcion.append("</td>");
                descripcion.append("</tr>");
                descripcion.append("</table>");
                descripcion.append("</td>");
                descripcion.append("</tr>");
                descripcion.append("</table>");
                descripcion.append("</td>");
                descripcion.append("</tr>");
                descripcion.append("</table>");
                descripcion.append("</div>");
                descripcion.append("</div>");
                descripcion.append("</body>");
                descripcion.append("</html>");
                
                //marker.setInformationWindow(descripcion.toString().replaceAll("\"", ""));
                marker.setInformationWindow(descripcion.toString());
               System.out.println(descripcion.toString());
                if (obra.getRuta() != null) {

                    marker.setListapuntosruta(getSessionBeanCobra().getCobraService().encontrarPuntosReferenciaxRuta(obra.getRuta().getStrcodigotramo()));
                    marker.setVerlinea(true);
                }
                markers.add(marker);
                i++;
            }

        }
    }

    /**
     * Método para abrir el slider de Ciudadano, invocando un método que trae
     * las últimas imágenes alimentadas
     */
    public void iniciarSlider() {
        FiltroGerencial filtrog = new FiltroGerencial();
        filtrog.setLocalidad("0");
        filtrog.setZona(0);
        filtrog.setLog(1);
        getSessionBeanCobra().getCiudadanoservice().setListaSliderGeneral(getSessionBeanCobra().getCobraService().obtenerUltimasImagenesObrasAlimentadas(filtrog));

    }
}
