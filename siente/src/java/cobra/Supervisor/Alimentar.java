/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.Supervisor;

import co.com.interkont.cobra.to.Actividadobra;
import co.com.interkont.cobra.to.Alimentacion;
import co.com.interkont.cobra.to.Alimentacioncualificacion;
import co.com.interkont.cobra.to.Documentoobra;
import co.com.interkont.cobra.to.Factoratraso;
import co.com.interkont.cobra.to.Historicoobra;
import co.com.interkont.cobra.to.Imagenevolucionobra;
import co.com.interkont.cobra.to.Novedad;
import co.com.interkont.cobra.to.Periodo;
import co.com.interkont.cobra.to.Relacionactividadobraperiodo;
import co.com.interkont.cobra.to.Relacionalimentacionactividad;
import co.com.interkont.cobra.to.Relacionhistoobradocu;
import co.com.interkont.cobra.to.Semaforo;
import co.com.interkont.cobra.to.Tipocosto;
import co.com.interkont.cobra.to.Tipocualitativa;
import co.com.interkont.cobra.to.Tipodocumento;
import co.com.interkont.cobra.to.Tipofactoratraso;
import co.com.interkont.cobra.to.Tipoimagen;
import co.com.interkont.cobra.to.Tipomodificacion;
import co.com.interkont.cobra.to.Tiponovedad;
import co.com.interkont.cobra.to.Videoevolucionobra;
import co.com.interkont.cobra.to.utilidades.Propiedad;
import cobra.ArchivoWeb;
import cobra.CargadorArchivosWeb;
import cobra.RedimensionarImagen;
import cobra.SessionBeanCobra;
import cobra.util.ArchivoWebUtil;
import cobra.util.RutasWebArchivos;
import com.interkont.cobra.exception.ArchivoExistenteException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import org.richfaces.component.UIDataTable;

/**
 * <p>Fragment bean that corresponds to a similarly named JSP page
 * fragment.  This class contains component definitions (and initialization
 * code) for all components that you have defined on this fragment, as well as
 * lifecycle methods and event handlers where you may add behavior
 * to respond to incoming events.</p>
 *
 * @version Alimentar.java
 * @version Created on 13-oct-2010, 22:27:32
 * @author carlosalbertoloaizaguerrero
 * @author David Andrés Betancourth Botero
 */
public class Alimentar {
    // <editor-fold defaultstate="collapsed" desc="Managed Component Definition">

    ResourceBundle bundle = getSessionBeanCobra().getBundle();
    private List<Factoratraso> listaFactores = new ArrayList<Factoratraso>();
    private String mensajefecha = bundle.getString("debeingresarunafecha");//"Debe Ingresar una fecha válida";
    private UIDataTable tablaFactores = new UIDataTable();
    private Alimentacion alimentacion = new Alimentacion();
    private float desviacion = 0;
    private int avisocambio = 0;
    private SelectItem[] TipoFactorOption;
    private SelectItem[] FactoresOption;
    private int tipofactorselec;
    private int factorselec;
    private boolean fechavalida;
    private String urlacta = "";
    private String nomImagen = "";
    private String nomActas = "";
    private CargadorArchivosWeb cargadorActa = new CargadorArchivosWeb();
    private CargadorArchivosWeb cargadorImagen = new CargadorArchivosWeb();
    private CargadorArchivosWeb cargadorVideo = new CargadorArchivosWeb();
    private boolean validezalimentacion = false;
    private String mensajeMatrix = "";
    private String mensaje = "";
    private String urlmemoria = "";
    private int estadoalimentar = 0;
    private String mensajefin = "";
    private BigDecimal[] totales;
    private List<Relacionalimentacionactividad> registrosmatrix = new ArrayList<Relacionalimentacionactividad>();
    //Matrix
    private UIDataTable listaActividades = new UIDataTable();
    private SelectItem[] itemstipoculitativa;
    private SelectItem[] periodos;
    List<Tipocualitativa> listatipocualitativo = new ArrayList<Tipocualitativa>();
    public Alimentacioncualificacion ali = new Alimentacioncualificacion();
    List<Alimentacioncualificacion> listitemculi = new ArrayList<Alimentacioncualificacion>();
    private UIDataTable tablaEliminarItemCualita = new UIDataTable();
    private boolean editarActividades = false;
    private boolean subiracta = false;
    private int fechaescogida;
    private String pathActaPrecios = "";
    private BigDecimal valorA = BigDecimal.ZERO;
    private BigDecimal valorU = BigDecimal.ZERO;
    private BigDecimal valorIvaU = BigDecimal.ZERO;
    private List<Actividadobra> listactividadobra = new ArrayList<Actividadobra>();
    private SelectItem[] TipoCostoOption;
    private UIDataTable tablaitemnoprevistos = new UIDataTable();
    private Historicoobra historicoobra = new Historicoobra();
    private Documentoobra documentoobra = new Documentoobra();
    private Actividadobra actividadobra = new Actividadobra();
    private Relacionhistoobradocu relacionhistoobradocu = new Relacionhistoobradocu();
    private Novedad novedad = new Novedad();
    private CargadorArchivosWeb cargadorActaPrecios = new CargadorArchivosWeb();
    private boolean vertodoalimentar = true;
    private Date fechaAlimentacion;
   private  List<Periodo> lstAlimentacionxfecha;

    public List<Periodo> getLstAlimentacionxfecha() {
        return lstAlimentacionxfecha;
    }

    public void setLstAlimentacionxfecha(List<Periodo> lstAlimentacionxfecha) {
        this.lstAlimentacionxfecha = lstAlimentacionxfecha;
    }


    public Date getFechaAlimentacion() {
        return fechaAlimentacion;
    }

    public void setFechaAlimentacion(Date fechaAlimentacion) {
        this.fechaAlimentacion = fechaAlimentacion;
    }

    

    public boolean isVertodoalimentar() {
        return vertodoalimentar;
    }

    public void setVertodoalimentar(boolean vertodoalimentar) {
        this.vertodoalimentar = vertodoalimentar;
    }

    public Relacionhistoobradocu getRelacionhistoobradocu() {
        return relacionhistoobradocu;
    }

    public void setRelacionhistoobradocu(Relacionhistoobradocu relacionhistoobradocu) {
        this.relacionhistoobradocu = relacionhistoobradocu;
    }

    public Documentoobra getDocumentoobra() {
        return documentoobra;
    }

    public void setDocumentoobra(Documentoobra documentoobra) {
        this.documentoobra = documentoobra;
    }

    public String getNomActas() {
        return nomActas;
    }

    public void setNomActas(String nomActas) {
        this.nomActas = nomActas;
    }

    public String getPathActaPrecios() {
        return pathActaPrecios;
    }

    public void setPathActaPrecios(String pathActaPrecios) {
        this.pathActaPrecios = pathActaPrecios;
    }

    public CargadorArchivosWeb getCargadorActaPrecios() {
        return cargadorActaPrecios;
    }

    public void setCargadorActaPrecios(CargadorArchivosWeb cargadorActaPrecios) {
        this.cargadorActaPrecios = cargadorActaPrecios;
    }

    public Novedad getNovedad() {
        return novedad;
    }

    public void setNovedad(Novedad novedad) {
        this.novedad = novedad;
    }

    public Historicoobra getHistoricoobra() {
        return historicoobra;
    }

    public void setHistoricoobra(Historicoobra historicoobra) {
        this.historicoobra = historicoobra;
    }

    public BigDecimal getValorIvaU() {
        return valorIvaU;
    }

    public void setValorIvaU(BigDecimal valorIvaU) {
        this.valorIvaU = valorIvaU;
    }

    public BigDecimal getValorA() {
        return valorA;
    }

    public void setValorA(BigDecimal valorA) {
        this.valorA = valorA;
    }

    public BigDecimal getValorU() {
        return valorU;
    }

    public void setValorU(BigDecimal valorU) {
        this.valorU = valorU;
    }

    public List<Actividadobra> getListactividadobra() {
        return listactividadobra;
    }

    public void setListactividadobra(List<Actividadobra> listactividadobra) {
        this.listactividadobra = listactividadobra;
    }

    public Actividadobra getActividadobra() {
        return actividadobra;
    }

    public void setActividadobra(Actividadobra actividadobra) {
        this.actividadobra = actividadobra;
    }

    public UIDataTable getTablaitemnoprevistos() {
        return tablaitemnoprevistos;
    }

    public void setTablaitemnoprevistos(UIDataTable tablaitemnoprevistos) {
        this.tablaitemnoprevistos = tablaitemnoprevistos;
    }

    public SelectItem[] getTipoCostoOption() {
        return TipoCostoOption;
    }

    public void setTipoCostoOption(SelectItem[] TipoCostoOption) {
        this.TipoCostoOption = TipoCostoOption;
    }

    public int getFechaescogida() {
        return fechaescogida;
    }

    public void setFechaescogida(int fechaescogida) {
        this.fechaescogida = fechaescogida;
    }

    public SelectItem[] getPeriodos() {
        return periodos;
    }

    public void setPeriodos(SelectItem[] periodos) {
        this.periodos = periodos;
    }

    public boolean isSubiracta() {
        return subiracta;
    }

    public void setSubiracta(boolean subiracta) {
        this.subiracta = subiracta;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isEditarActividades() {
        return editarActividades;
    }

    public void setEditarActividades(boolean editarActividades) {
        this.editarActividades = editarActividades;
    }

    public UIDataTable getTablaEliminarItemCualita() {
        return tablaEliminarItemCualita;
    }

    public void setTablaEliminarItemCualita(UIDataTable tablaEliminarItemCualita) {
        this.tablaEliminarItemCualita = tablaEliminarItemCualita;
    }

    public List<Alimentacioncualificacion> getListitemculi() {
        return listitemculi;
    }

    public void setListitemculi(List<Alimentacioncualificacion> listitemculi) {
        this.listitemculi = listitemculi;
    }

    public Alimentacioncualificacion getAli() {
        return ali;
    }

    public void setAli(Alimentacioncualificacion ali) {
        this.ali = ali;
    }

    public List<Tipocualitativa> getListatipocualitativo() {
        return listatipocualitativo;
    }

    public void setListatipocualitativo(List<Tipocualitativa> listatipocualitativo) {
        this.listatipocualitativo = listatipocualitativo;
    }

    public SelectItem[] getItemstipoculitativa() {
        return itemstipoculitativa;
    }

    public void setItemstipoculitativa(SelectItem[] itemstipoculitativa) {
        this.itemstipoculitativa = itemstipoculitativa;
    }
    //Video
    private boolean hayvideo = false;

    public boolean isHayvideo() {
        return hayvideo;
    }

    public void setHayvideo(boolean hayvideo) {
        this.hayvideo = hayvideo;
    }

    public UIDataTable getListaActividades() {
        return listaActividades;
    }

    public void setListaActividades(UIDataTable listaActividades) {
        this.listaActividades = listaActividades;
    }

    public List<Relacionalimentacionactividad> getRegistrosmatrix() {
        return registrosmatrix;
    }

    public void setRegistrosmatrix(List<Relacionalimentacionactividad> registrosmatrix) {
        this.registrosmatrix = registrosmatrix;
    }

    public BigDecimal[] getTotales() {
        return totales;
    }

    public void setTotales(BigDecimal[] totales) {
        this.totales = totales;
    }

    public int getEstadoalimentar() {
        return estadoalimentar;
    }

    public void setEstadoalimentar(int estadoalimentar) {
        this.estadoalimentar = estadoalimentar;
    }

    public String getMensajefin() {
        return mensajefin;
    }

    public void setMensajefin(String mensajefin) {
        this.mensajefin = mensajefin;
    }

    public String getUrlmemoria() {
        return urlmemoria;
    }

    public void setUrlmemoria(String urlmemoria) {
        this.urlmemoria = urlmemoria;
    }

    public String getMensajeMatrix() {
        return mensajeMatrix;
    }

    public void setMensajeMatrix(String mensajeMatrix) {
        this.mensajeMatrix = mensajeMatrix;
    }

    public boolean isValidezalimentacion() {
        return validezalimentacion;
    }

    public void setValidezalimentacion(boolean validezalimentacion) {
        this.validezalimentacion = validezalimentacion;
    }

    public CargadorArchivosWeb getCargadorActa() {
        return cargadorActa;
    }

    public void setCargadorActa(CargadorArchivosWeb cargadorActa) {
        this.cargadorActa = cargadorActa;
    }

    public CargadorArchivosWeb getCargadorImagen() {
        return cargadorImagen;
    }

    public void setCargadorImagen(CargadorArchivosWeb cargadorImagen) {
        this.cargadorImagen = cargadorImagen;
    }

    public CargadorArchivosWeb getCargadorVideo() {
        return cargadorVideo;
    }

    public void setCargadorVideo(CargadorArchivosWeb cargadorVideo) {
        this.cargadorVideo = cargadorVideo;
    }

    public String getNomImagen() {
        return nomImagen;
    }

    public void setNomImagen(String nomImagen) {
        this.nomImagen = nomImagen;
    }

    public String getPathImagen() {
        if(cargadorImagen.getArchivoWeb()!=null) {
            return cargadorImagen.getArchivoWeb().getRutaWeb();
        } else {
            return RutasWebArchivos.IMG_NO_DISPONIBLE;
        }
    }

    public String getUrlacta() {
        return urlacta;
    }

    public void setUrlacta(String urlacta) {
        this.urlacta = urlacta;
    }

    public boolean isFechavalida() {
        return fechavalida;
    }

    public void setFechavalida(boolean fechavalida) {
        this.fechavalida = fechavalida;
    }
    private boolean desviado = false;

    public boolean isDesviado() {
        return desviado;
    }

    public void setDesviado(boolean desviado) {
        this.desviado = desviado;
    }

    public int getAvisocambio() {
        return avisocambio;
    }

    public void setAvisocambio(int avisocambio) {
        this.avisocambio = avisocambio;
    }

    public Alimentacion getAlimentacion() {
        return alimentacion;
    }

    public void setAlimentacion(Alimentacion alimentacion) {
        this.alimentacion = alimentacion;
    }

    public UIDataTable getTablaFactores() {
        return tablaFactores;
    }

    public void setTablaFactores(UIDataTable tablaFactores) {
        this.tablaFactores = tablaFactores;
    }

    public List<Factoratraso> getListaFactores() {
        return listaFactores;
    }

    public void setListaFactores(List<Factoratraso> listaFafctores) {
        this.listaFactores = listaFactores;
    }

    public SelectItem[] getTipoFactorOption() {
        return TipoFactorOption;
    }

    public void setTipoFactorOption(SelectItem[] TipoFactorOption) {
        this.TipoFactorOption = TipoFactorOption;
    }

    public SelectItem[] getFactoresOption() {
        return FactoresOption;
    }

    public void setFactoresOption(SelectItem[] FactoresOption) {
        this.FactoresOption = FactoresOption;
    }

    public int getTipofactorselec() {
        return tipofactorselec;
    }

    public void setTipofactorselec(int tipofactorselec) {
        this.tipofactorselec = tipofactorselec;
    }

    public int getFactorselec() {

        return factorselec;
    }

    public void setFactorselec(int factorselec) {
        this.factorselec = factorselec;
    }

    public float getDesviacion() {
        return desviacion;
    }

    /**
     * Set the value of desviacion
     *
     * @param desviacion new value of desviacion
     */
    public void setDesviacion(float desviacion) {
        this.desviacion = desviacion;
    }

    public String getMensajefecha() {
        if (fechaescogida == -1) {
            return mensajefecha;
        } else {
            return null;
        }

    }

    public void setMensajefecha(String mensajefecha) {
        this.mensajefecha = mensajefecha;
    }

    /**
     * <p>Automatically managed component initialization. <strong>WARNING:</strong>
     * This method is automatically generated, so any user-specified code inserted
     * here is subject to being replaced.</p>
     */
    private void _init() throws Exception {
    }
    // </editor-fold>

    public Alimentar() {
        limpiarAlimentar();       
    }

    
    /**
     * Asigna a factorselec con el nuevo valor del factor de atraso
     * @param evt
     * @return null
     */
    public String combofactorCambio_action(ValueChangeEvent evt) {
        if (avisocambio == 0) {
            factorselec = Integer.parseInt(String.valueOf(evt.getNewValue()));
        } else {
            avisocambio = 0;
        }

        return null;
    }

    /**
     * Llenado  LlenarFactores (Tipos de Factores) y asignacion a la variable  avisocambio
     * @return null
     */
    public String combotipoCambio_action() {
        //tipoatraso = new Tipofactoratraso(Integer.parseInt(String.valueOf(evt.getNewValue())), "");
        avisocambio = 1;
        LlenarFactores();
        return null;
    }

    /**
     * Obtiene el codigo del tipo de atraso y se asigna a la variable tipofactorselec
     */
    public void LlenarTipoFactoratraso() {
        //TipofactoratrasoDaoInterface o = new TipofactoratrasoDao();
        List<Tipofactoratraso> tipofactor = getSessionBeanCobra().getCobraService().encontrarTiposFactorAtraso();


        TipoFactorOption = new SelectItem[tipofactor.size()];
        int i = 0;


        for (Tipofactoratraso factora : tipofactor) {
            SelectItem opt = new SelectItem(factora.getInttipoatraso(), factora.getStrdesctipoatraso());
            if (i == 0) {
                tipofactorselec = factora.getInttipoatraso();
            }

            TipoFactorOption[i++] = opt;
        }


    }

    /**
     * A la lista factores le adiciona el id del factor seleccionado
     * @return null
     */
    public String agregar_action() {
        if (!verificarfactor(factorselec)) {
            //FactoratrasoDaoInterface fact = new FactoratrasoDao();
            Factoratraso factoratra = getSessionBeanCobra().getCobraService().encontrarFactorAtrasoPorId(factorselec);
            listaFactores.add(factoratra);
        }
//fact.cerrarSession();

        return null;
    }

    /**
     * Verifica el id de atraso seleccionado 
     * @param id factor de atraso
     * @return
     */
    public boolean verificarfactor(int id) {
        for (Factoratraso tab : listaFactores) {
            if (tab.getIntidfactor() == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * Elimina el factor atraso seleccionado
     * @return null
     */
    public String removerFactor() {

        Factoratraso fact = (Factoratraso) tablaFactores.getRowData();
        listaFactores.remove(fact);

        return null;
    }

    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }

    /*
     * limpia las variables de alimentar  y llama los metodos llenarTipoCosto,llenarCualitativo y llenarPeriodos
     */
    public void limpiarAlimentar() {
        llenarTiposCosto();
        nomImagen = "";
        cargadorImagen = new CargadorArchivosWeb();
        cargadorVideo = new CargadorArchivosWeb();
        cargadorActaPrecios = new CargadorArchivosWeb();
        setAlimentacion(new Alimentacion());
        getAlimentacion().setDatefechaalimen(new Date());
        getAlimentacion().setObra(getAdministrarObraNew().getObra());
        validezalimentacion = false;
        mensajeMatrix = "";
        setDesviado(false);
        setDesviacion(0);
        urlacta = "";
        cargadorActa = new CargadorArchivosWeb();
        urlmemoria = "";
        setListaFactores(new ArrayList<Factoratraso>());
        mensajefin = "";
        llenarActividadesMatrix();
        estadoalimentar = 0;
        getDetalleObra().setNumeroalimentacion(1);
        fechavalida = false;
        ali = new Alimentacioncualificacion();
        ali.setTipocualitativa(new Tipocualitativa());
        listitemculi = new ArrayList<Alimentacioncualificacion>();
        mensajefecha = "";
        llenarCualitativo();
        llenarPeriodos();
        editarActividades = false;
        documentoobra = new Documentoobra();
        listaFactores = new ArrayList<Factoratraso>();
        vertodoalimentar = true;
        if (getAdministrarObraNew().getObra().getTipoestadobra().getIntestadoobra() != 1 && getAdministrarObraNew().getObra().getTipoestadobra().getIntestadoobra() != 4) {
            fechavalida = false;
            setMensajefecha(bundle.getString("laobraestasiendofinalizadamodi"));//"La obra esta siendo finalizada, modificada o está suspendida, termine el proceso para poder alimentar.");
            FacesUtils.addErrorMessage(bundle.getString("laobraestasiendofinalizadamodi"));
            vertodoalimentar = false;
        }
        alimentacion.setNumempdirectos(0);
        alimentacion.setNumempindirectos(0);
        alimentacion.setNumhabafectados(0);
        
    }

    /*
     * Llena una lista con las relaciones entre actividades y periodos, allí se almacenará los datos del avance.
     */
    public void llenarActividadesMatrix() {
        Iterator act = getSessionBeanCobra().getCobraService().obtenerActividadesObra(getAdministrarObraNew().getObra().getIntcodigoobra()).iterator();

        registrosmatrix = new ArrayList<Relacionalimentacionactividad>();
        totales = new BigDecimal[4];
        while (act.hasNext()) {
            Relacionalimentacionactividad rel = new Relacionalimentacionactividad();
            rel.setActividadobra((Actividadobra) act.next());
            rel.setAlimentacion(getAlimentacion());
            registrosmatrix.add(rel);
        }

        totales[0] = getAdministrarObraNew().getObra().getNumvaltotobra();
        totales[1] = BigDecimal.valueOf(0);
        if (getAdministrarObraNew().getObra().getNumvalejecobra() == null) {
            getAdministrarObraNew().getObra().setNumvalejecobra(BigDecimal.valueOf(0));
        }
        totales[2] = getAdministrarObraNew().getObra().getNumvaltotobra().subtract(getAdministrarObraNew().getObra().getNumvalejecobra());
        totales[3] = BigDecimal.valueOf(0);
    }

    public void cargaListaPeridos()
    {
        lstAlimentacionxfecha=new ArrayList<Periodo>();
        fechaAlimentacion=null;
        getSessionBeanCobra().getCobraService().setPeriodos(getSessionBeanCobra().getCobraService().encontrarPeriodosObra(getAdministrarObraNew().getObra()));
        lstAlimentacionxfecha = getSessionBeanCobra().getCobraService().getPeriodos();
        fechaAlimentacion=lstAlimentacionxfecha.get(0).getDatefeciniperiodo();
    }


    /*
     * Método llamado cuando se selecciona el periodo al cual se le va a reportar avance, se valida  el periodo de alimentación
     */
    public String fechaCambio() {
        mensajefecha = "";
        fechavalida = false;
        registrosmatrix = new ArrayList<Relacionalimentacionactividad>();
         setDesviado(false);
         setDesviacion(0);
        if( bundle.getString("varalimentacionxfecha").equals("true")){
         fechaescogida =-1;
         
         for(int i=0;i<lstAlimentacionxfecha.size();i++)
         {
            
             if(fechaAlimentacion.compareTo(lstAlimentacionxfecha.get(i).getDatefeciniperiodo()) >=0 && fechaAlimentacion.compareTo(lstAlimentacionxfecha.get(i).getDatefecfinperiodo())<=0 )
             {
                 
                 fechaescogida=lstAlimentacionxfecha.get(i).getIntidperiodo();
                 
             }
         }
         if(fechaescogida==-1){
            FacesUtils.addErrorMessage(bundle.getString("noestaperiodofecha"));
         }
        }
        if (fechaescogida != -1) {
            
            getSessionBeanCobra().getCobraService().setPeriodo(getSessionBeanCobra().getCobraService().encontrarPeriodoxid(fechaescogida));
            if( bundle.getString("varalimentacionxfecha").equals("true")){
                getAlimentacion().setDatefecha(fechaAlimentacion);
            }else
            {
                getAlimentacion().setDatefecha(getSessionBeanCobra().getCobraService().getPeriodo().getDatefecfinperiodo());
            }
            LlenarTipoFactoratraso();
            combotipoCambio_action();
            //getAlimentacion().setTextcomentario(""); //Supervisor$Alimentar.alimentacion.textcomentario
            getAlimentacion().setDatefechaalimen(new Date());
            getAdministrarObraNew().getObra().setPeriodos(new LinkedHashSet(getSessionBeanCobra().getCobraService().obtenerPeriodosxObra(getAdministrarObraNew().getObra())));
            //Verifica si la obra se encuentra en un estado que se pueda alimentar.
            if (getAdministrarObraNew().getObra().getTipoestadobra().getIntestadoobra() == 1 || getAdministrarObraNew().getObra().getTipoestadobra().getIntestadoobra() == 4) {
                if (getAlimentacion().getDatefecha() != null) {
                    if (getAdministrarObraNew().getObra().getTipoestadobra().getIntestadoobra() == 6) {
                        if (getModificarObra().getHistoricoobra().getDatefecobrahist() != null
                                && getAlimentacion().getDatefecha().compareTo(getModificarObra().getHistoricoobra().getDatefecobrahist()) >= 0) {
                            fechavalida = false;

                            setMensajefecha(bundle.getString("lafechadealimantaciondebeser")
                                    + (getModificarObra().getHistoricoobra().getDatefecobrahist().getYear() + 1900) + "-"
                                    + (getModificarObra().getHistoricoobra().getDatefecobrahist().getMonth() + 1) + "-"
                                    + getModificarObra().getHistoricoobra().getDatefecobrahist().getDate());
                            FacesUtils.addErrorMessage(bundle.getString("lafechadealimantaciondebeser"));
                            return null;
                        }
                    }
                    //Verifica que no se alimente el futuro
                    if (getAlimentacion().getDatefecha().compareTo(getAlimentacion().getDatefechaalimen()) <= 0) {
                        //Verifica si la fecha del periodo se encuentra en el rango de inicio y finalización de la obra
                        if (getAlimentacion().getDatefecha().compareTo(getAdministrarObraNew().getObra().getDatefeciniobra()) >= 0
                                && (getAlimentacion().getDatefecha().compareTo(getAdministrarObraNew().getObra().getDatefecfinobra()) == -1
                                || getAlimentacion().getDatefecha().compareTo(getAdministrarObraNew().getObra().getDatefecfinobra()) == 0)) {
                            //Se obtiene la última alimentación de la obra
                            Date ultfec = null;
                            if (getDetalleObra().getAlimentacionultima() == null || getDetalleObra().getAlimentacionultima().getDatefecha() == null) {
                                //Setea la variable con la fecha de inicio
                                ultfec = getAdministrarObraNew().getObra().getDatefeciniobra();
                            } else {
                                // Setea la variable con la fecha de la última alimentación
                                ultfec = getDetalleObra().getAlimentacionultima().getDatefecha();
                            }
                            //Verifica que no alimente fechas anteriores a la ult alimentacion --ni iguales
                            String permitirvarias = bundle.getString("permitirvariasalimentacionesxperiodo");
                            if (getAlimentacion().getDatefecha().compareTo(ultfec) > 0
                                    || (permitirvarias.compareTo("true") == 0 && getAlimentacion().getDatefecha().compareTo(ultfec) >= 0)) {
                                fechavalida = true;
                                //Calcula la proyección de ejecución
                                getAlimentacion().setNumtotalproyacu(getAdministrarObraNew().getObra().getValorProyectadoenFecha(getAlimentacion().getDatefecha()));
                                //getAlimentacion().setNumtotalproyacu(getSessionBeanCobra().getCobraService().getPeriodo().getNumvaltotplanif());
                                llenarActividadesMatrix();
                                //Valida la desviación y obtiene el semáforo
                                if (getAlimentacion().getNumtotalproyacu().compareTo(BigDecimal.valueOf(0)) != 0) {
                                    //getAlimentacion().setSemaforo(getAdministrarObraNew().getObra().getSemaforoAlimentacion(getAdministrarObraNew().getObra().getNumvalejecobra().add(totales[1]), getAlimentacion().getNumtotalproyacu()));

                                    getAlimentacion().setSemaforo(getSessionBeanCobra().getCobraService().getSemaforoAlimentacion(getAdministrarObraNew().getObra().getNumvalejecobra().add(totales[1]),
                                            getAlimentacion().getNumtotalproyacu(), getAdministrarObraNew().getObra().getTipoobra(), getAdministrarObraNew().getObra().getNumvaltotobra()));
                                    setDesviacion(getAdministrarObraNew().getObra().getCalcularPorcentajeDesviacion(getAdministrarObraNew().getObra().getNumvalejecobra().add(totales[1]), getAlimentacion().getNumtotalproyacu()));
                                    setDesviado(getAlimentacion().getSemaforo().isBooleanatraso());
                                } else {
                                    getAlimentacion().setSemaforo(new Semaforo(1, null, 0, 0, "/resources/botones/verde.png", false));
                                    setDesviado(false);
                                    setDesviacion(0);
                                }

                            } else {

                                fechavalida = false;
                                //
                                if (getAlimentacion().getDatefecha().compareTo(ultfec) == 0) {
                                    FacesUtils.addErrorMessage(bundle.getString("periodoyaalimentado"));

                                } else {
                                    FacesUtils.addErrorMessage(bundle.getString("lafechaesmenoralaanterior"));
                                    setMensajefecha(bundle.getString("lafechaesmenoralaanterior"));//"La Fecha es menor a la anterior alimentación");
                                }


                            }

///Cálculo de atraso y alarma
                        } else {

                            fechavalida = false;
                            setMensajefecha(bundle.getString("fechaporfueradelrango"));//"Fecha por fuera del rango de ejecución de la Obra");
                            FacesUtils.addErrorMessage(bundle.getString("fechaporfueradelrango"));
                        }

                    } else {

                        fechavalida = false;
                        setMensajefecha(bundle.getString("fehcadelactaparcial"));//"Fecha del Acta Parcial superior a la fecha actual");
                        FacesUtils.addErrorMessage(bundle.getString("fehcadelactaparcial"));//"Fecha del Acta Parcial superior a la fecha actual");
                    }


                } else {

                    fechavalida = false;
                }
            } else {

                fechavalida = false;
                setMensajefecha(bundle.getString("laobraestasiendofinalizadamodi"));//"La obra esta siendo finalizada, modificada o está suspendida, termine el proceso para poder alimentar.");
                FacesUtils.addErrorMessage(bundle.getString("laobraestasiendofinalizadamodi"));
            }
        }
        return null;
        
    }

    /*
     * Metodo  trae los datos del sin avance.
     */
    public String cambioMatrix() {
        String cual=calcularTodaMatrix();
        
        if(!validezalimentacion)
        {    
        if (fechavalida) {
            //getAlimentacion().setNumtotalproyacu(getAdministrarObraNew().getObra().getValorProyectadoenFecha(getAlimentacion().getDatefecha()));
            //desviado=validar
            if (getAlimentacion().getNumtotalproyacu().compareTo(BigDecimal.valueOf(0)) != 0) {
                //getAlimentacion().setSemaforo(getAdministrarObraNew().getObra().getSemaforoAlimentacion(getAdministrarObraNew().getObra().getNumvalejecobra().add(totales[1]), getAlimentacion().getNumtotalproyacu()));
                getAlimentacion().setSemaforo(getSessionBeanCobra().getCobraService().getSemaforoAlimentacion(getAdministrarObraNew().getObra().getNumvalejecobra().add(totales[1]),
                        getAlimentacion().getNumtotalproyacu(), getAdministrarObraNew().getObra().getTipoobra(), getAdministrarObraNew().getObra().getNumvaltotobra()));

                setDesviacion(getAdministrarObraNew().getObra().getCalcularPorcentajeDesviacion(getAdministrarObraNew().getObra().getNumvalejecobra().add(totales[1]), getAlimentacion().getNumtotalproyacu()));
                setDesviado(getAlimentacion().getSemaforo().isBooleanatraso());
            } else {
                getAlimentacion().setSemaforo(new Semaforo(1, null, 0, 0, "/resources/botones/verde.png", false));
                setDesviado(false);
                setDesviacion(0);
            }
            editarActividades = false;
        }
}
        return null;
    }

    /*
     * carga los datos de la obra y llama los metodos para iniciar la alimentacion de la obra especifica.
     */
    public void iniciarAlimentar() {

        setMensajefecha("");
        setAlimentacion(new Alimentacion());
        getAlimentacion().setDatefechaalimen(new Date());
        getAlimentacion().setObra(getAdministrarObraNew().getObra());
        getAdministrarObraNew().setOpcion(1);
        getAdministrarObraNew().getObra().setPeriodos(new LinkedHashSet(getSessionBeanCobra().getCobraService().obtenerPeriodosObra(getAdministrarObraNew().getObra().getIntcodigoobra())));
//        getDetalleObra().setAlimentacionultima(new Alimentacion());
//

        //getDetalleObra().setAlimentacionultima(getAdministrarObraNew().getObra().getUltimaalimentacion());
        //getDetalleObra().setListaAlimenta(new ArrayList<Alimentacion>());
        llenarActividadesMatrix();
        LlenarTipoFactoratraso();

        LlenarFactores();
        fechavalida = false;
//       alimentacionCualificacion =new Alimentacioncualificacion();
        ali = new Alimentacioncualificacion();
        editarActividades = false;
        llenarCualitativo();
    }

    /*
     * actualiza los valores de la matrix de avance, calcula el valor ejecutado
     */
    public String actualizarMatrix() {
        Relacionalimentacionactividad reg = registrosmatrix.get(listaActividades.getRowIndex());
        reg.calcularNumvalEjec();


        registrosmatrix.set(listaActividades.getRowIndex(), reg);

        CalculoTotales();

        return null;
    }
    
    
    public String calcularTodaMatrix()
    {
        
        int i=0;
        while(i<registrosmatrix.size())
        {
          registrosmatrix.get(i).calcularNumvalEjec();
            i++;  
        }
        CalculoTotales();
        return null;
    }       
    
    

    /*
     * Suma todos los totales de la alimentación.
     */
    public void CalculoTotales() {
        validezalimentacion = false;

        Iterator iter = registrosmatrix.iterator();

        totales[1] = BigDecimal.valueOf(0);
        while (iter.hasNext()) {
            Relacionalimentacionactividad reg = (Relacionalimentacionactividad) iter.next();
            if (reg.getNumvalejec() == null) {
                reg.setNumvalejec(BigDecimal.valueOf(0));

            }

            totales[1] = totales[1].add(reg.getNumvalejec());

        }

        totales[2] = totales[0].subtract(totales[1]).subtract(getAdministrarObraNew().getObra().getNumvalejecobra());
        totales[2] = totales[2].setScale(6, RoundingMode.DOWN);//Se redondea totales[2] para que no incurra en errores en la alimentación
        if (totales[2].compareTo(BigDecimal.valueOf(0)) == -1) {
            validezalimentacion = true;
            mensajeMatrix = bundle.getString("sesuperaelvalor");//"SE SUPERA EL VALOR TOTAL PROGRAMADO";
            FacesUtils.addErrorMessage(mensajeMatrix);
        }

        totales[3] = BigDecimal.valueOf(0);
        for (Relacionalimentacionactividad reg : registrosmatrix) {
            if (reg.getActividadobra().getFloatcantidadejecutao() == null && reg.getFloatcantejec() == null) {
                // reg.setNumvalejec(BigDecimal.valueOf(0));
            }


            Double sum = reg.getActividadobra().getFloatcantidadejecutao() + reg.getFloatcantejec();

            totales[3] = totales[3].add(BigDecimal.valueOf(sum));
        }

    }

    /*
     * borra el video de la alimentación
     */
    public String borrarVideoAlimenta() {
        if (!cargadorVideo.getArchivos().isEmpty()) {
            ArchivoWebUtil.eliminarArchivo(cargadorVideo.getArchivoWeb().getRutaWeb());
        }
        cargadorVideo.borrarDatosSubidos();
        return null;
    }

    /*
     * borra la imagen de la alimentación y asigna la de no disponible
     */
    public String borrarImagenAlimenta() {
        if (cargadorImagen.getArchivoWeb() != null) {
            ArchivoWebUtil.eliminarArchivo(cargadorImagen.getArchivoWeb().getRutaWeb());
        }
        cargadorImagen.borrarDatosSubidos();
        return null;
    }

    /*
     * Guarda imagen en la carpeta temporal mientras esta alimentado el proyecto.
     */
    public String guardarImagenTemporal() {

        if (cargadorImagen.getNumArchivos() > 0) {
            try {
                cargadorImagen.getArchivoWeb().cambiarNombre(null, true);
                cargadorImagen.guardarArchivosTemporales(RutasWebArchivos.TMP, false);
                    nomImagen = cargadorImagen.getArchivoWeb().getNombre();
                    String rutaAbsolutaImg = ArchivoWebUtil.obtenerRutaAbsoluta(cargadorImagen.getArchivoWeb().getRutaWeb()) ;
                    try {
                        RedimensionarImagen.scale(rutaAbsolutaImg, 640, 5, rutaAbsolutaImg);
                    } catch (IOException ex) {
                        Logger.getLogger(AdministrarObraNew.class.getName()).log(Level.SEVERE, null, ex);
                    }
            } catch (ArchivoExistenteException ex) {
                FacesContext.getCurrentInstance().addMessage(
                                        null,
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        Propiedad.getValor("docexistenteerror"), ""));
                Logger.getLogger(Alimentar.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return null;
    }

    /*
     * Recorre y guarda el nombre de las actas
     */
    public String pathDocumento() {
        if (cargadorActa.getNumArchivos() > 0) {
            for (ArchivoWeb nombreoriginal : cargadorActa.getArchivos()) {
                urlacta = nombreoriginal.getNombre();
            }
        } else {
            urlacta = "";
        }
        return null;
    }

    /*
     * Guarda el video en una carpeta temporal mientras se confirma la alimentación.
     */
    public String guardarVideoTemporal() throws IOException, InterruptedException {
        String carpetaDoc = RutasWebArchivos.TMP;
        if(!cargadorVideo.getArchivos().isEmpty()){
            try {
                cargadorVideo.getArchivoWeb().cambiarNombre(null, true);
                cargadorVideo.guardarArchivosTemporales(carpetaDoc, false);
            } catch (ArchivoExistenteException ex) {
                FacesContext.getCurrentInstance().addMessage(
                                        null,
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        Propiedad.getValor("docexistenteerror"), ""));
                Logger.getLogger(Alimentar.class.getName()).log(Level.SEVERE, null, ex);
            }
            hayvideo = true;
        }
        return null;
    }

    /*
     * Borra el acta de la alimentación
     */
    public String borrarActa() {
        if (urlacta.compareTo("") != 0) {
            ArchivoWebUtil.eliminarArchivo(urlacta);
            urlacta = "";
        }
        cargadorActa.borrarDatosSubidos();
        return null;
    }

    /*
     * Valida que los datos alimentados sean correctos.
     */
    public String validarAlimentar() {

        estadoalimentar = 0;
        mensajefin = "";

        if (alimentacion.getDatefecha() != null) {
            if (cargadorImagen.getNumArchivos() == 1) {

                if (alimentacion.getNumhabafectados() >= 0 && alimentacion.getNumempdirectos() >= 0 && alimentacion.getNumempindirectos() >= 0) {
                    if (totales[1].compareTo(BigDecimal.valueOf(0)) != 0) {
                        if (totales[2].compareTo(BigDecimal.valueOf(0)) != -1) {
                            if (subiracta) {
                                if (cargadorActa.getNumArchivos() == 1 && urlacta != null && urlacta.compareTo("") != 0) {

                                    estadoalimentar = 1;
                                } else {
                                    //FacesUtils.addErrorMessage(bundle.getString("debeagregarunacta"));
                                    mensajefin = bundle.getString("debeagregarunacta");//"Debe agregar un acta a la alimentación";
                                }
                            } else {
                                estadoalimentar = 1;
                            }
                            
                            if(isDesviado())
                            {
                               
                                if(listaFactores.isEmpty())
                                {
                                    estadoalimentar=0;
                                    mensajefin = bundle.getString("debejustificar");//"Debe justificar los factores de atraso";
                                }
                                else
                                {
                                    estadoalimentar=1;
                                }    
                            }    
                            
//                            if ((isDesviado() && !getListaFactores().isEmpty()) || !isDesviado()) {
//                                estadoalimentar = 1;
//                            } else {
//                                //  FacesUtils.addErrorMessage(bundle.getString("debejustificar"));
//                                mensajefin = bundle.getString("debejustificar");//"Debe justificar los factores de atraso";
//                            }
                        } else {
                            // FacesUtils.addErrorMessage(bundle.getString("elvalorejecutadoexcede"));
                            mensajefin = bundle.getString("elvalorejecutadoexcede");//"El Valor ejecutado acumulado excede lo planificado";
                        }

                    } else {
                        // FacesUtils.addErrorMessage(bundle.getString("debeingresarvalores"));
                        mensajefin = bundle.getString("debeingresarvalores");//"Debe ingresar valores a las cantidades de las actividades";
                    }
                } else {
                    // FacesUtils.addErrorMessage(bundle.getString("debeingresarvalores"));
                    mensajefin = bundle.getString("impactosocialreportar");//"Debe diligenciar los campos de Impacto Social.";
                }

            } else {
                //FacesUtils.addErrorMessage(bundle.getString("debeingresarunaimagen"));
                mensajefin = bundle.getString("debeingresarunaimagen");//"Debe Ingresar una imagen correspondiente a la alimentación";
            }
        } else {
            //FacesUtils.addErrorMessage(bundle.getString("debeseleccionarunfechapa"));
            mensajefin = bundle.getString("debeseleccionarunfechapa");//"Debe Seleccionar una fecha para el acta de alimentación";
        }

        return null;
    }

    /**
     * Si estadoalimentar es igual a 1 permite guardar alimentación
     */
    public String finalizarModal_action() {

        if (estadoalimentar == 1) {
            try {
                guardarAlimentar();
            } catch (ArchivoExistenteException ex) {
                FacesContext.getCurrentInstance().addMessage(
                                        null,
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        Propiedad.getValor("docexistenteerror"), ""));
                Logger.getLogger(Alimentar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return null;

    }

    public String iraDetalle() {
        getAdministrarObraNew().setObra(getSessionBeanCobra().getCobraService().encontrarObraPorId(
                getAdministrarObraNew().getObra().getIntcodigoobra()));
        getAdministrarObraNew().setOpcion(0);
        getDetalleObra().iniciardetalle();
        return "admindetalleObra";
    }
    private String nuevonombre = "";

    public String getNuevonombre() {
        return nuevonombre;
    }

    public void setNuevonombre(String nuevonombre) {
        this.nuevonombre = nuevonombre;
    }

    /*
     * Guarda la alimentación, asigna los diferentes rutas para la base de datos
     */
    public void guardarAlimentar() throws ArchivoExistenteException {
        String rutaActa = null;
        String rutaWebVideo=null;
        String rutaWebImg = null;

        getAlimentacion().setIntidalimenta(0);
        getAlimentacion().setDatefechaalimen(new Date());
        getAlimentacion().setObra(getAdministrarObraNew().getObra());

//        ServletContext theApplicationsServletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

        if (urlacta != null && urlacta.compareTo("") != 0) {
            cargadorActa.getArchivoWeb().cambiarNombre(null, true);
            cargadorActa.guardarArchivosTemporales(MessageFormat.format(RutasWebArchivos.DOCS_OBRA, ""+getAdministrarObraNew().getObra().getIntcodigoobra()), false);
            rutaActa = cargadorActa.getArchivoWeb().getRutaWeb();

            Tipodocumento tipdoc = new Tipodocumento(10, "ACTA PARCIAL", false);
            Documentoobra doc = new Documentoobra(getAdministrarObraNew().getObra(), tipdoc, rutaActa, bundle.getString("actadealimentacion")
                    + getAlimentacion().getDatefecha(), getAlimentacion().getDatefecha());

            //doc= getAdministrarObraNew().castearDocumentoObra(doc);
            //getSessionBeanCobra().getCobradao().guardarDocumento(doc);
            getAlimentacion().getDocumentoobras().add(doc);
            //getAdministrarObraNew().getObra().getDocumentoobras().add(doc);
        }
        //getAdministrarObraNew().getListaDocumentosobra().add(doc);
//        tipdoc = new Tipodocumento(15, "MEMORIA CANTIDAD OBRA", false);
//        doc = new Documentoobra(obra, tipdoc, "/resources/Documentos/ObrasVigentes/" + obra.getIntcodigoobra() + "/Documentos/" + urlmemoria, "Memoria de Cantidad de Obra " + alimentacion.getDatefecha(), alimentacion.getDatefecha());
//        alimentacion.getDocumentoobras().add(doc);
//        this.listaDocumentosobra.add(doc);

        //guardarfactores
        if (isDesviado() == true) {
            Iterator facto = getListaFactores().iterator();
            getAlimentacion().getFactoratrasos().clear();
            while (facto.hasNext()) {
                Factoratraso fact = (Factoratraso) facto.next();
                getAlimentacion().getFactoratrasos().add(new Factoratraso(fact.getIntidfactor(), new Tipofactoratraso(fact.getTipofactoratraso().getInttipoatraso(), ""), ""));
            }

        }
        try {
            //agrego imagen
            rutaWebImg = ArchivoWebUtil.copiarArchivo(
                    cargadorImagen.getArchivoWeb().getRutaWeb(), 
                    MessageFormat.format(RutasWebArchivos.IMGS_OBRA_ALIMENTACION, ""+getAdministrarObraNew().getObra().getIntcodigoobra()),
                    true, true);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Alimentar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ArchivoExistenteException ex) {
            FacesContext.getCurrentInstance().addMessage(
                                        null,
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        Propiedad.getValor("docexistenteerror"), ""));
            Logger.getLogger(Alimentar.class.getName()).log(Level.SEVERE, null, ex);
        }

        //ImagenevolucionobraDaoInterface daoimg = new ImagenevolucionobraDao();

        Imagenevolucionobra im = new Imagenevolucionobra();
        im.setTipoimagen(new Tipoimagen(2, "", true));
        im.setObra(getAdministrarObraNew().getObra());
        im.setStrnombre("Imagen alimentacion " + getAlimentacion().getDatefechaalimen());
        im.setDatefecha(getAlimentacion().getDatefecha());
        im.setStrubicacion(rutaWebImg);
        im.setStrnombrearchivo(nomImagen);
        im.getAlimentacions().clear();
        im.getAlimentacions().add(getAlimentacion());
        //getAdministrarObraNew().getListaImagenesevolucionobra().add(im);
        //getSessionBeanCobra().getCobradao().cerrarSession();
        //getSessionBeanCobra().getCobradao().guardarOrActualizarImagen(im);
        //daoimg.guardarOrActualizar(im);
        //System.out.println("guardo la imagen = ");
        getAlimentacion().setImagenevolucionobra(im);
        getAlimentacion().setNumtotalejecacu(getAdministrarObraNew().getObra().getNumvalejecobra().add(totales[1]));
        //getAdministrarObraNew().getObra().getImagenevolucionobras().add(im);
        //agregar video


        if (hayvideo == true) {
                String command = "ffmpeg2theora " + ArchivoWebUtil.obtenerRutaAbsoluta(cargadorVideo.getArchivoWeb().getRutaWeb()) + " -o " + ArchivoWebUtil.obtenerRutaAbsoluta(cargadorVideo.getArchivoWeb().getRutaWeb().substring(0, cargadorVideo.getArchivoWeb().getRutaWeb().lastIndexOf(".")) + "_Convertido.ogv");
                try {
                    final Process process = Runtime.getRuntime().exec(command);
                    new Thread() {

                        @Override
                        public void run() {
                            try {
                                InputStream is = process.getInputStream();
                                byte[] buffer = new byte[1024];
                                for (int count = 0; (count = is.read(buffer)) >= 0;) {
                                    System.out.write(buffer, 0, count);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                    new Thread() {

                        @Override
                        public void run() {
                            try {
                                InputStream is = process.getErrorStream();
                                byte[] buffer = new byte[1024];
                                for (int count = 0; (count = is.read(buffer)) >= 0;) {
                                    System.err.write(buffer, 0, count);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();

                    int returnCode = process.waitFor();

                    command = "ffmpeg -i " + ArchivoWebUtil.obtenerRutaAbsoluta(cargadorVideo.getArchivoWeb().getRutaWeb()) + " " + ArchivoWebUtil.obtenerRutaAbsoluta(cargadorVideo.getArchivoWeb().getRutaWeb().substring(0, cargadorVideo.getArchivoWeb().getRutaWeb().lastIndexOf(".")) + "_Convertido.mp4");
                    final Process process1 = Runtime.getRuntime().exec(command);
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                InputStream is = process1.getInputStream();
                                byte[] buffer = new byte[1024];
                                for (int count = 0; (count = is.read(buffer)) >= 0;) {
                                    System.out.write(buffer, 0, count);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                    new Thread() {

                        @Override
                        public void run() {
                            try {
                                InputStream is = process1.getErrorStream();
                                byte[] buffer = new byte[1024];
                                for (int count = 0; (count = is.read(buffer)) >= 0;) {
                                    System.err.write(buffer, 0, count);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();

                    int returnCode1 = process1.waitFor();
                    
                    ArchivoWebUtil.eliminarArchivo(cargadorVideo.getArchivoWeb().getRutaWeb());

                    rutaWebVideo = ArchivoWebUtil.copiarArchivo(
                            cargadorVideo.getArchivoWeb().getRutaWeb().substring(0, cargadorVideo.getArchivoWeb().getRutaWeb().lastIndexOf(".")) + "_Convertido.mp4", 
                            MessageFormat.format(RutasWebArchivos.VIDEOS_ALIMENTACION_OBRA, ""+getAdministrarObraNew().getObra().getIntcodigoobra()),
                            true, true
                    );
                    
                    ArchivoWebUtil.copiarArchivo(
                            cargadorVideo.getArchivoWeb().getRutaWeb().substring(0, cargadorVideo.getArchivoWeb().getRutaWeb().lastIndexOf(".")) + "_Convertido.ogv", 
                            MessageFormat.format(RutasWebArchivos.VIDEOS_ALIMENTACION_OBRA, ""+getAdministrarObraNew().getObra().getIntcodigoobra()),
                            true, true
                    );
                    
                    /*pathVideo = URL + nuevonombre + ".mp4";
                    nombreVideo = URL + nuevonombre;
                    command = "ffmpeg -i " + URLFINAL + URL + nuevonombre + ".flv " getIn URLFINAL + URL + nuevonombre + ".mp4";
                    System.out.println("input = "+URLFINAL + URL + nuevonombre + ".flv");
                    System.out.println("output = " +URLFINAL + URL + nuevonombre + ".mp4");
                    final Process process1 = Runtime.getRuntime().exec(command);
                    new Thread() {
                    
                    @Override
                    public void run() {
                    try {
                    InputStream is = process1.getInputStream();
                    byte[] buffer = new byte[1024];
                    for (int count = 0; (count = is.read(buffer)) >= 0;) {
                    System.out.write(buffer, 0, count);
                    }
                    } catch (Exception e) {
                    e.printStackTrace();
                    }
                    }
                    }.start();
                    new Thread() {
                    
                    @Override
                    public void run() {
                    try {
                    InputStream is = process1.getErrorStream();
                    byte[] buffer = new byte[1024];
                    for (int count = 0; (count = is.read(buffer)) >= 0;) {
                    System.err.write(buffer, 0, count);
                    }
                    } catch (Exception e) {
                    e.printStackTrace();
                    }
                    }
                    }.start();
                    
                    int returnCode1 = process1.waitFor();
                    //System.out.println("termina = " );*/
                    if (!cargadorVideo.getArchivos().isEmpty()) {
                        ArchivoWebUtil.eliminarArchivo(cargadorVideo.getArchivoWeb().getRutaWeb());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }



//            String nombredelvideo = "";
//            if (nombreVideo.contains(" ")) {
//
//                int i = 0;
//                while (i < nombreVideo.length()) {
//                    if (String.valueOf(nombreVideo.charAt(i)).compareTo(" ") == 0) {
//                        nombredelvideo = nombredelvideo + "%20";
//                    } else {
//                        nombredelvideo = nombredelvideo + String.valueOf(nombreVideo.charAt(i));
//                    }
//
//                    i++;
//                }
//
//            } else {
//                nombredelvideo = nombreVideo;
//                System.out.println("nombredelvideo = " + nombreVideo);
//            }


            Videoevolucionobra vm = new Videoevolucionobra();
            //VideoevolucionobraDaoInterface daovideo = new VideoevolucionobraDao();
            vm.setObra(getAdministrarObraNew().getObra());
            vm.setStrnombre("Video alimentacion" + getAlimentacion().getDatefechaalimen());
            vm.setDatefecha(getAlimentacion().getDatefecha());
            //  vm.setStrubicacion(pathVideo);
            vm.setStrubicacion(rutaWebVideo.substring(0, rutaWebVideo.lastIndexOf(".")));

            vm.getAlimentacions().clear();
            vm.getAlimentacions().add(getAlimentacion());
            vm.setStrurlvideo("");
            vm.setBoolubicacionlocal(true);
            //daovideo.guardarOrActualizar(vm);
            //getSessionBeanCobra().getCobradao().guardarOrActualizarVideo(vm);

            //System.out.println("VIDEO = " + vm.getStrubicacion());
            getAlimentacion().setVideoevolucionobra(vm);
            //getAdministrarObraNew().getObra().getVideoevolucionobras().add(vm);

        }

        //agregar Relaciones actividades
        int i = 0;
        while (i < registrosmatrix.size()) {
            Relacionalimentacionactividad reg = registrosmatrix.get(i);
            reg.getActividadobra().setFloatcantidadejecutao(reg.getActividadobra().getFloatcantidadejecutao() + reg.getFloatcantejec());
            reg.getActividadobra().setNumvalorejecutao(reg.getActividadobra().getNumvalorejecutao().add(reg.getNumvalejec()));
            reg.setStrurlfoto(rutaWebImg);//new***
            reg.setAlimentacion(getAlimentacion());
            registrosmatrix.set(i, reg);
            i++;

        }
        Set<Relacionalimentacionactividad> relas = new HashSet<Relacionalimentacionactividad>(registrosmatrix);

        getAlimentacion().getRelacionalimentacionactividads().clear();

        getAlimentacion().setRelacionalimentacionactividads(relas);
        //System.out.println("totales[1] = " + totales[1]);
        getAlimentacion().setNumtotalejec(totales[1]);
        //getAlimentacion().setDatefechaautorizacion(new Date());//new***


        //Novedades

        if (getAlimentacion().getSemaforo().isBooleanatraso()) {

            getAdministrarObraNew().getObra().setNovedads(new HashSet());
            getAdministrarObraNew().getObra().getNovedads().add(new Novedad(0, new Tiponovedad(4, ""), getAdministrarObraNew().getObra(), new Date()));
            if (getAlimentacion().getSemaforo().getIntporfin() == 100) {


                getAdministrarObraNew().getObra().getNovedads().add(new Novedad(0, new Tiponovedad(3, ""), getAdministrarObraNew().getObra(), new Date()));
            }
        }

        if (listitemculi.size() > 0) {
            alimentacion.setAlimentacioncualificacions(new LinkedHashSet(listitemculi));
        }



        getAdministrarObraNew().getObra().setActividadobras(new HashSet());
        alimentacion.setJsfUsuarioByIntusuAlimenta(getSessionBeanCobra().getUsuarioObra());
        ///Manejo de impacto de la alimentación

        alimentacion.setBoolaplicado(true);
        //getAdministrarObraNew().getObra().setEnalimentacion(false);

        //Habilitar obra para validación de información a validación de información
//        if (getAdministrarObraNew().getObra().getSolicitud_obra() != null
//                && !getSessionBeanCobra().getUsuarioService().validarColombiaHumanitaria(getSessionBeanCobra().getUsuarioObra().getUsuId(), 6)) {
//            //alimentacion.setBoolaplicado(false);
//
//            getAdministrarObraNew().getObra().setEnalimentacion(true);
//
//        } 

        getAdministrarObraNew().getObra().setNumvalejecobra(alimentacion.getNumtotalejecacu());

        getSessionBeanCobra().getCobraService().guardarAlimentacion(alimentacion, getSessionBeanCobra().getUsuarioObra());

        getAdministrarObraNew().getObra().setStrimagenobra(alimentacion.getImagenevolucionobra().getStrubicacion());
        getSessionBeanCobra().getCobraService().guardarObra(getAdministrarObraNew().getObra(), getSessionBeanCobra().getUsuarioObra(), -1);
        getSessionBeanCobra().getCobraService().funcion_EstablecerImagenActual(im.getIntidimagen());

        getDetalleObra().setAlimentacionultima(getSessionBeanCobra().getCobraService().obtenerUltimaalimentacion(getAdministrarObraNew().getObra().getIntcodigoobra()));
        limpiarAlimentar();

    }

    protected DetalleObra getDetalleObra() {
        return (DetalleObra) FacesUtils.getManagedBean("Supervisor$DetalleObra");
    }

    protected AdministrarObraNew getAdministrarObraNew() {
        return (AdministrarObraNew) FacesUtils.getManagedBean("Supervisor$AdministrarObraNew");
    }

    protected ModificarObra getModificarObra() {
        return (ModificarObra) FacesUtils.getManagedBean("Supervisor$ModificarObra");
    }

    /*
     * Crea una lista de tipo factor de atraso y obtiene el factor de atraso con la descripción seleccionado
     */
    public void LlenarFactores() {


        int i = 0;
        List<Factoratraso> listatemp = new ArrayList<Factoratraso>(getSessionBeanCobra().getCobraService().encontrarFactoresAtrasoxtipo(tipofactorselec));

        FactoresOption = new SelectItem[listatemp.size()];
        i = 0;
        while (i < listatemp.size()) {
            Factoratraso factoratra = listatemp.get(i);
            SelectItem opt = new SelectItem(factoratra.getIntidfactor(), factoratra.getStrdescfactor());
            if (i == 0) {
                factorselec = factoratra.getIntidfactor();
            }

            FactoresOption[i++] = opt;

        }

    }

    /*
     * llena una lista con los tipos de cualitativa  
     */
    public void llenarCualitativo() {
        listatipocualitativo = getSessionBeanCobra().getCobraService().encontrarTodoCualitativa();
        itemstipoculitativa = new SelectItem[listatipocualitativo.size()];
        int i = 0;
        while (i < listatipocualitativo.size()) {
            SelectItem opt = new SelectItem(listatipocualitativo.get(i).getIntidtipocualificacion(), listatipocualitativo.get(i).getStrnombre());
            if (i == 0) {
                //tipoCualitativoSele = listatipocualitativo.get(i).getIntidtipocualificacion();
                ali.setTipocualitativa(new Tipocualitativa(listatipocualitativo.get(i).getIntidtipocualificacion(), ""));
            }
            itemstipoculitativa[i++] = opt;
        }

    }

    /*
     * Lista los periodos alimentados fecha inicio - fecha final
     */
    public void llenarPeriodos() {
        getSessionBeanCobra().getCobraService().setPeriodos(getSessionBeanCobra().getCobraService().encontrarPeriodosObra(getAdministrarObraNew().getObra()));
        periodos = new SelectItem[getSessionBeanCobra().getCobraService().getPeriodos().size()];
        int i = 0;
        for (Periodo period : getSessionBeanCobra().getCobraService().getPeriodos()) {
            SelectItem peri = new SelectItem(period.getIntidperiodo(), period.getDatefeciniperiodo() + " - " + period.getDatefecfinperiodo());
            if (i == 0) {
                fechaescogida = -1;
            }
            periodos[i++] = peri;
        }
    }

    /*
     * Adiciona la alimentacion cualitativa si dificultad o logro no son diligenciados
     */
    public String agregarAlimentacionCualitativa() {
        if (!ali.getStrdificultad().equals("") || !ali.getStrlogro().equals("")) {
            ali.setTipocualitativa(getSessionBeanCobra().getCobraService().encontrarTipoCualitativa(ali.getTipocualitativa().getIntidtipocualificacion()));
            ali.setAlimentacion(alimentacion);
            listitemculi.add(ali);
//            ali = new Alimentacioncualificacion();
//            ali.setTipocualitativa(new Tipocualitativa());
            mensaje = bundle.getString("agregadoCualitativa");
        } else {
            mensaje = bundle.getString("diligenciarCualitativa");
        }
        return null;
    }

    /*
     * Elimina la alimentacion cualtativa
     */
    public void removerAlimentacionCualitativa() {
        Alimentacioncualificacion lialieli = (Alimentacioncualificacion) tablaEliminarItemCualita.getRowData();
        listitemculi.remove(lialieli);
    }

    public String habilitarEdicionActividades() {
        editarActividades = true;
        return null;
    }

    /*
     * limpia el contenido de cualitativa
     */
    public String limpiarCualitativa() {
        ali = new Alimentacioncualificacion();
        ali.setTipocualitativa(new Tipocualitativa());
        return null;
    }

    /**
     * Obtener la lista de seguidores, con el parametro obra y limitante
     */
    public void eliminaractividad(int filaSeleccionada) {
       // Supervisor$Alimentar.listactividadobra
        listactividadobra.remove(filaSeleccionada);
    }

    /**
     * Inicializar el Objeto de Actividad
     */
    public void inicializarActividad() {
        actividadobra = new Actividadobra();
    }

    /**
     * Borrar la lista actividades no previstas.
     */
    public void limpiarlistaActividades() {
        inicializarActividad();
        listactividadobra = new ArrayList<Actividadobra>();
    }

    /**
     * Inicializar el diligenciamiento de actividades no previstas.
     */
    public void inicializarAdicionActividades() {
        pathActaPrecios="";
        llenarTiposCosto();
        inicializarActividad();
        cargadorActaPrecios = new CargadorArchivosWeb();
        nomActas = "";
        borrarActadePrecios();
        listactividadobra = new ArrayList<Actividadobra>();
    }

    /**
     * Agregar Actiividades No Previstas A la Lista que va hacer persistida.
     */
    public void agregaractividad() {
        if (!actividadobra.getStrdescactividad().equals("")
                && !actividadobra.getStrtipounidadmed().equals("")
                && !actividadobra.getCalcularValorUnitario().equals(0)) {

            actividadobra=modificarCadenaLarga(actividadobra);            
            carcularValortotal();
            actividadobra.setFloatcantidadejecutao(Double.valueOf(0));
            actividadobra.setFloatcantplanifao(Double.valueOf(0));
            actividadobra.setStrcodcubs("");
            actividadobra.setNumvalorejecutao(BigDecimal.ZERO);
            actividadobra.setObra(getAdministrarObraNew().getObra());
            actividadobra.setJsfUsuario(getSessionBeanCobra().getUsuarioObra());
            actividadobra.setBoolimprevisto(true);
            if (!getAdministrarObraNew().getObra().isAplicaaiu()) {
                actividadobra.setBoolaiu(false);
            } else {
                actividadobra.setBoolaiu(true);
            }
            Iterator<Periodo> itPeriodos = getAdministrarObraNew().getObra().getPeriodos().iterator();
            while (itPeriodos.hasNext()) {
                Periodo periodo = itPeriodos.next();
                Relacionactividadobraperiodo relacionactividadobraperiodo = new Relacionactividadobraperiodo();
                relacionactividadobraperiodo.setPeriodo(periodo);
                relacionactividadobraperiodo.setActividadobra(actividadobra);
                relacionactividadobraperiodo.setNumvalplanif(BigDecimal.ZERO);
                relacionactividadobraperiodo.setFloatcantplanif(0.0);
                actividadobra.getRelacionactividadobraperiodos().add(relacionactividadobraperiodo);
            }
            listactividadobra.add(actividadobra);
            inicializarActividad();
        }
    }
    
    public Actividadobra modificarCadenaLarga(Actividadobra actividadobra){
        if(actividadobra.getStrdescactividad().length()>40){
            int cantidad=actividadobra.getStrdescactividad().length()/20;
            StringTokenizer espaciosCadena=new StringTokenizer(actividadobra.getStrdescactividad());
            if(espaciosCadena.countTokens()==1){
               StringBuilder palabraEspacios=new StringBuilder();
               int inicio=0;
               int fin=20;
               while(cantidad>0){
                   palabraEspacios.append(actividadobra.getStrdescactividad().substring(inicio, fin)+'\n');
                   inicio=fin+1;
                   fin+=20;
                   if(fin>actividadobra.getStrdescactividad().length()){
                    fin=actividadobra.getStrdescactividad().length();
                   }                   
                   cantidad--;
               }                   
               actividadobra.setStrdescactividad(palabraEspacios.toString());
            }
         }
        return  actividadobra;
    }

    /**
     * Guardar y Persistir, las Actividades no previstas par ala alimentacion.
     */
    public void guardarActividades() {
        setNovedad(new Novedad(0, new Tiponovedad(9, ""), getAdministrarObraNew().getObra(), new Date()));
        getSessionBeanCobra().getCobraService().guardarHistoxActivixNovedadxDocuxRelacion(
                guardarHistoricoObra(), listactividadobra, getNovedad(), getSessionBeanCobra().getUsuarioObra(), guardarDocumentoObra(), guardarRelacionhistoobradocu());
        guardarArchiActaPrecios();
        llenarActividadesMatrix();
    }

    /**
     * Guardar el Documento.
     * Trasladar el documento de la carpeta temporal, a la vigente.
     * Encontrar si el nombre el documento contiene espacios en blanco para remplazarlos %20.
     */
    public void guardarArchiActaPrecios() {
        try {
            ArchivoWebUtil.copiarArchivo(cargadorActaPrecios.getArchivoWeb().getRutaWeb(),
                                MessageFormat.format(RutasWebArchivos.DOCS_OBRA, ""+getAdministrarObraNew().getObra().getIntcodigoobra()),
                                true, true
                        );
        } catch (FileNotFoundException ex) {             
            Logger.getLogger(Alimentar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ArchivoExistenteException ex) {
            FacesContext.getCurrentInstance().addMessage(
                                        null,
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        Propiedad.getValor("docexistenteerror"), ""));
            Logger.getLogger(Alimentar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Obtener el Objeto de Historico Obra.
     * @return Objeto de Historico Obra.
     */
    public Historicoobra guardarHistoricoObra() {
        historicoobra = new Historicoobra();
        historicoobra.setDatefechist(new Date());
        historicoobra.setDatefecobrahist(getAdministrarObraNew().getObra().getDatefecfinobra());
        historicoobra.setDatefecfinhist(getAdministrarObraNew().getObra().getDatefecfinobra());
        historicoobra.setStrrazonmodif(bundle.getString("razondemodificacion9"));
        historicoobra.setNumvalorhist(getAdministrarObraNew().getObra().getNumvaltotobra());
        historicoobra.setStrurlcronogramahist(getAdministrarObraNew().getObra().getStrurlcronograma());
        historicoobra.setObra(getAdministrarObraNew().getObra());
        historicoobra.setJsfUsuario(getSessionBeanCobra().getUsuarioObra());
        historicoobra.getTipomodificacions().add(new Tipomodificacion(9, ""));
        return historicoobra;
    }

    /**
     * Obtener el Objeto de Documento Obra.
     * @return Objeto de Documento Obra.
     */
    public Documentoobra guardarDocumentoObra() {
        documentoobra = new Documentoobra();
        documentoobra.setStrubicacion("/resources/Documentos/ObrasVigentes/" + getAdministrarObraNew().getObra().getIntcodigoobra() + "/Modificacion/" + nomActas);
        documentoobra.setStrnombre(nomActas);
        documentoobra.setDatefecha(new Date());
        documentoobra.setObra(getAdministrarObraNew().getObra());
        documentoobra.setTipodocumento(new Tipodocumento(17, "", true));
        return documentoobra;
    }

    /**
     * Obtener la Relacion Historico Obra x Documento.
     * @return Objeto Relacion Historico Obra x Documento.
     */
    public Relacionhistoobradocu guardarRelacionhistoobradocu() {
        relacionhistoobradocu = new Relacionhistoobradocu();
        relacionhistoobradocu.setHistoricoobra(historicoobra);
        relacionhistoobradocu.setDocumentoobra(documentoobra);
        return relacionhistoobradocu;
    }

    /**
     * Llenado de combo Tipo Costo.
     */
    public void llenarTiposCosto() {
        List<Tipocosto> lista = getSessionBeanCobra().getCobraService().encontrarTiposCosto();
        TipoCostoOption = new SelectItem[lista.size()];
        int i = 0;
        for (Tipocosto tipo : lista) {
            SelectItem opt = new SelectItem(tipo.getInttipocosto(), tipo.getStrdesctipocosto());
            TipoCostoOption[i++] = opt;
        }
    }

    /**
     * Calcular el Valor de la Actvidad cuando no contenga AIU
     */
    public void carcularValortotal() {
        if (!getAdministrarObraNew().getObra().isAplicaaiu()) {
            valorA = BigDecimal.ZERO;
            valorU = BigDecimal.ZERO;
            valorIvaU = BigDecimal.ZERO;

            valorA = valorA.add(actividadobra.getCalcularValorUnitario().multiply(BigDecimal.valueOf(getAdministrarObraNew().getObra().getFloatporadmon())));
            valorA = valorA.add(actividadobra.getCalcularValorUnitario().multiply(BigDecimal.valueOf(getAdministrarObraNew().getObra().getFloatporimprevi())));
            valorA = valorA.add(actividadobra.getCalcularValorUnitario().multiply(BigDecimal.valueOf(getAdministrarObraNew().getObra().getFloatporotros())));
            valorU = actividadobra.getCalcularValorUnitario().multiply(BigDecimal.valueOf(getAdministrarObraNew().getObra().getFloatporutilidad()));
            if (!valorA.equals(0)) {
                valorA = valorA.divide(BigDecimal.valueOf(100));
            }
            if (!valorU.equals(0)) {
                valorU = valorU.divide(BigDecimal.valueOf(100));
            }
            valorIvaU = valorU.multiply(BigDecimal.valueOf(getAdministrarObraNew().getObra().getFloatporivasobreutil()));
            actividadobra.setNumvalorplanifao(valorA.add(valorU).add(valorIvaU).add(actividadobra.getCalcularValorUnitario()));
        } else {
            actividadobra.setNumvalorplanifao(actividadobra.getCalcularValorUnitario());
        }
    }

    /**
     * Borrar Documento de Acta de Precios de la Carpeta donde fue almacenado con anterioridad.
     */
    public String borrarActadePrecios() {
        if (!cargadorActaPrecios.getArchivos().isEmpty()) {
            ArchivoWebUtil.eliminarArchivo(cargadorActaPrecios.getArchivoWeb().getRutaWeb());
            pathActaPrecios = "";
        }
        cargadorActaPrecios.borrarDatosSubidos();
        return null;
    }

    /**
     * Guardar el documento de acta de precios en la carpeta temporal.
     */
    public String guardarActadePrecios() {
        if (cargadorActaPrecios.getNumArchivos() > 0) {
            try {
                cargadorActaPrecios.getArchivoWeb().cambiarNombre(null, true);
                cargadorActaPrecios.guardarArchivosTemporales(RutasWebArchivos.TMP, false);
                for (ArchivoWeb nombreoriginal : cargadorActaPrecios.getArchivos()) {
                    nomActas = nombreoriginal.getNombre();
                    pathActaPrecios = RutasWebArchivos.TMP + nombreoriginal.getNombre();
                }
            } catch (ArchivoExistenteException ex) {
                FacesContext.getCurrentInstance().addMessage(
                                        null,
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        Propiedad.getValor("docexistenteerror"), ""));
                Logger.getLogger(Alimentar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public boolean getValidarGuardarItemNoPrevis() {
        if (listactividadobra.size() > 0) {
            if (!pathActaPrecios.equals("")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
