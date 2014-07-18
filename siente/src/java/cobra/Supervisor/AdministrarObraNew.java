/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.Supervisor;

import co.com.interkont.cobra.marcologico.vista.VistaCalculoIndicadorProyectos;
import co.com.interkont.cobra.to.Alimentacion;
import co.com.interkont.cobra.to.Barrio;
import co.com.interkont.cobra.to.Contratista;
import co.com.interkont.cobra.to.Contrato;
import co.com.interkont.cobra.to.Documentoobra;
import co.com.interkont.cobra.to.Historicoobra;
import co.com.interkont.cobra.to.Imagenevolucionobra;
import co.com.interkont.cobra.to.Localidad;
import co.com.interkont.cobra.to.Novedad;
import co.com.interkont.cobra.to.Obra;
import co.com.interkont.cobra.to.Periodo;
import co.com.interkont.cobra.to.Polizacontrato;
import co.com.interkont.cobra.to.Puntoobra;
import co.com.interkont.cobra.to.Puntoreferencia;
import co.com.interkont.cobra.to.Relacioncontratoobra;
import co.com.interkont.cobra.to.SolicitudValidacionAvance;
import co.com.interkont.cobra.to.Tercero;
import co.com.interkont.cobra.to.Tipodocumento;
import co.com.interkont.cobra.to.Tipoestadobra;
import co.com.interkont.cobra.to.Tipoimagen;
import co.com.interkont.cobra.to.Tipoinforme;
import co.com.interkont.cobra.to.Tiponovedad;
import co.com.interkont.cobra.to.Vereda;
import co.com.interkont.cobra.to.Videoevolucionobra;
import co.com.interkont.cobra.to.utilidades.Propiedad;
import co.com.interkont.giprom.vista.VwIndIndicadorMunicipal;
import co.com.interkont.giprom.vista.VwInmInfoMunicipal;
import cobra.ArchivoWeb;
import cobra.Marcador;
import cobra.RedimensionarImagen;
import cobra.SessionBeanCobra;
import cobra.CargadorArchivosWeb;
import cobra.Utilidades;
import cobra.util.ArchivoWebUtil;
import cobra.util.RutasWebArchivos;
import com.googlecode.gmaps4jsf.services.GMaps4JSFServiceFactory;
import com.googlecode.gmaps4jsf.services.data.PlaceMark;
import com.interkont.cobra.exception.ArchivoExistenteException;
import email.MailFonade;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import org.richfaces.component.UIDataTable;

/**
 * <p>
 * Page bean that corresponds to a similarly named JSP page. This class contains
 * component definitions (and initialization code) for all components that you
 * have defined on this page, as well as lifecycle methods and event handlers
 * where you may add behavior to respond to incoming events.</p>
 *
 * @version AdministrarObraNew.java
 * @version Created on 28-oct-2010, 1:04:30
 * @author carlosalbertoloaizaguerrero
 */
public class AdministrarObraNew implements ILifeCycleAware, Serializable {
    // <editor-fold defaultstate="collapsed" desc="Managed Component Definition">

    //private Obra obra = new Obra();
    private String id = "";
    private String plazo = "";
    //////Documentos obra
    private boolean verMenuModificar = true;
    private boolean verModificar = false;
    private boolean verSuspender = false;
    private boolean verReiniciar = false;
    ///Finalizar    
    private boolean verFin = false;
    private String urlactafin = "";
    private CargadorArchivosWeb subirActaFin = new CargadorArchivosWeb();
    private String mensajefinaliza = "";
    private String urlactarecfin = "";
    private CargadorArchivosWeb subirActaRecFin = new CargadorArchivosWeb();
    //Docs & imágenes
    private List<Documentoobra> listaDocumentosobra;
    private Documentoobra documentoobra = new Documentoobra();
    private UIDataTable tablaDocumentos = new UIDataTable();
    private List<Tipodocumento> listaTipoDocumento;
    private Documentoobra documentoobraEd = new Documentoobra();
    private Documentoobra documentoobraEl = new Documentoobra();
    private Barrio barrio = new Barrio();
    private Vereda vereda = new Vereda();
    /**
     * Objeto utilizado para subir los archivos relacionados a la modificacion
     */
    private CargadorArchivosWeb cargadorDocumentos = new CargadorArchivosWeb();
    private CargadorArchivosWeb cargadorImagenes = new CargadorArchivosWeb();
    private SelectItem[] selectItemTipoDocumento;
    private List<Imagenevolucionobra> listaImagenesevolucionobra;
    private Imagenevolucionobra imagenevolucionobra = new Imagenevolucionobra();
    private UIDataTable tablaImagenesevolucion = new UIDataTable();
    private Imagenevolucionobra imagenevolucionobraEd = new Imagenevolucionobra();
    private Imagenevolucionobra imagenevolucionobraEl = new Imagenevolucionobra();
    private String palabraclave = "";
    private ResourceBundle bundle = getSessionBeanCobra().getBundle();
    private List<Polizacontrato> listaPolizacontratos = new ArrayList<Polizacontrato>();
    private List<Puntoobra> listaPuntoObra = new ArrayList<Puntoobra>();
    private SelectItem[] Poliza;
    private String imgTacometro;
    private String ubicacionmapa = "";
    private List<Relacioncontratoobra> listaContrato = new ArrayList<Relacioncontratoobra>();
    private List<Localidad> listalocalidad = new ArrayList<Localidad>();
    private UIDataTable tablaContrato = new UIDataTable();
    private UIDataTable tablaCosto = new UIDataTable();
    private UIDataTable tablaOtroPunto = new UIDataTable();
    private boolean puntootro = false;
    private boolean polizacontrato = false;
    private String municipio = "";
    private String departamento = "";
    private String region = "";
    private String nacion = "";
    private String contratistanombre = "";
    private String contratistapellido = "";
    private BigDecimal valoren;
    private List<Historicoobra> listahistorico = new ArrayList<Historicoobra>();
    private Boolean historico = false;
    private BigDecimal valoresta;
    //private BigDecimal deberiaestar;
    private Integer intduraciondias;
    public boolean iniima = true;
    public Tercero soli = new Tercero();
    private List<Puntoreferencia> listapuntosreferencia = new ArrayList<Puntoreferencia>();
    private Boolean boolruta = false;
    private List<Videoevolucionobra> listaVideoObra = new ArrayList<Videoevolucionobra>();
    private String urlvideo = "";
    private UIDataTable tablaverVideo = new UIDataTable();
    private Boolean mostrarvideo = false;
    private List<Tipoimagen> listaTipoImagen;
    private SelectItem[] selectItemTipoImagen;
    private String palabradoc = "";
    public boolean activocomentarios = true;
    private Tipoinforme informeavalidar = new Tipoinforme();
    private List<Contrato> lstcontratante = new ArrayList<Contrato>();
    private List<Contratista> lstcontratista = new ArrayList<Contratista>();
    private List<Relacioncontratoobra> lstncontratos = new ArrayList<Relacioncontratoobra>();
    private int numcontratistas = 0;
    private String nomContratistas = "";
    private boolean contratista = false;
    private ArrayList<Relacioncontratoobra> listaContratoObra;
    private ArrayList<Relacioncontratoobra> listaContratoInterventoria;
    private List<Marcador> listamarcadores = new ArrayList<Marcador>();
    private String nombreImpactoSocial;
    private int cantidadColumnas = 3;
    public boolean btn_habilitarModificarObjeto = true;
    public boolean modificarObjetoObra = false;
    private List<Barrio> listaBarrios = new ArrayList<Barrio>();
    private List<Vereda> listaVeredas = new ArrayList<Vereda>();
    public int controltipodocumento = 0;

    //adicion variable para que cuando se termine el proyecto no muestre validacion anterior
    private boolean obraFinalizada = false;

    public boolean isObraFinalizada() {
        return obraFinalizada;
    }

    public void setObraFinalizada(boolean obraFinalizada) {
        this.obraFinalizada = obraFinalizada;
    }

    /**
     * variables para las fechas de informe circular tolima
     */
    private Date fechaInicioCircular;
    private Date fechaFinCircular;

    public Date getFechaInicioCircular() {
        return fechaInicioCircular;
    }

    public void setFechaInicioCircular(Date fechaInicioCircular) {
        this.fechaInicioCircular = fechaInicioCircular;
    }

    public Date getFechaFinCircular() {
        return fechaFinCircular;
    }

    public void setFechaFinCircular(Date fechaFinCircular) {
        this.fechaFinCircular = fechaFinCircular;
    }
    /**
     * Variable para proyectos que pertenecen a Marco
     */
    private boolean proyectoestrategia = false;
    /**
     * Variable para habilitar el boton modificar El impacto
     */
    public boolean habilitarModificarimpacto = true;
    /**
     * Variable para habilitar El boton Guardar la modificacion del impacto
     */
    public boolean habilitarGuardarimpacto = false;
    /**
     * Objetos para almacenar avace fisico medios vida
     *
     */
    private VistaCalculoIndicadorProyectos vproductomedios = new VistaCalculoIndicadorProyectos();
    private VistaCalculoIndicadorProyectos vproductogestion = new VistaCalculoIndicadorProyectos();
    /*
     * Varible que tiene el codigo del contrato para buscar la informacion del contrato
     */
    private int mostrarContratoConvenio = 0;

    public int getMostrarContratoConvenio() {
        return mostrarContratoConvenio;
    }

    public void setMostrarContratoConvenio(int mostrarContratoConvenio) {
        this.mostrarContratoConvenio = mostrarContratoConvenio;
    }

    public VistaCalculoIndicadorProyectos getVproductomedios() {
        return vproductomedios;
    }

    public void setVproductomedios(VistaCalculoIndicadorProyectos vproductomedios) {
        this.vproductomedios = vproductomedios;
    }

    public VistaCalculoIndicadorProyectos getVproductogestion() {
        return vproductogestion;
    }

    public void setVproductogestion(VistaCalculoIndicadorProyectos vproductogestion) {
        this.vproductogestion = vproductogestion;
    }

    public boolean isProyectoestrategia() {
        return proyectoestrategia;
    }

    public void setProyectoestrategia(boolean proyectoestrategia) {
        this.proyectoestrategia = proyectoestrategia;
    }

    public List<Barrio> getListaBarrios() {
        return listaBarrios;
    }

    public void setListaBarrios(List<Barrio> listaBarrios) {
        this.listaBarrios = listaBarrios;
    }

    public List<Vereda> getListaVeredas() {
        return listaVeredas;
    }

    public void setListaVeredas(List<Vereda> listaVeredas) {
        this.listaVeredas = listaVeredas;
    }

    public int getCantidadColumnas() {

        if (listaVideoObra.size() < 3) {
            return listaVideoObra.size();
        } else {

            return cantidadColumnas;
        }
    }

    public void setCantidadColumnas(int cantidadColumnas) {
        this.cantidadColumnas = cantidadColumnas;
    }

    public List<Marcador> getListamarcadores() {
        return listamarcadores;
    }

    public void setListamarcadores(List<Marcador> listamarcadores) {
        this.listamarcadores = listamarcadores;
    }

    public ArrayList<Relacioncontratoobra> getListaContratoInterventoria() {
        return listaContratoInterventoria;
    }

    public void setListaContratoInterventoria(ArrayList<Relacioncontratoobra> listaContratoInterventoria) {
        this.listaContratoInterventoria = listaContratoInterventoria;
    }

    public ArrayList<Relacioncontratoobra> getListaContratoObra() {
        return listaContratoObra;
    }

    public void setListaContratoObra(ArrayList<Relacioncontratoobra> listaContratoObra) {
        this.listaContratoObra = listaContratoObra;
    }

    public boolean isContratista() {
        return contratista;
    }

    public void setContratista(boolean contratista) {
        this.contratista = contratista;
    }

    public String getNomContratistas() {
        return nomContratistas;
    }

    public void setNomContratistas(String nomContratistas) {
        this.nomContratistas = nomContratistas;
    }

    public int getNumcontratistas() {
        return numcontratistas;
    }

    public void setNumcontratistas(int numcontratistas) {
        this.numcontratistas = numcontratistas;
    }

    public List<Contrato> getLstcontratante() {
        return lstcontratante;
    }

    public void setLstcontratante(List<Contrato> lstcontratante) {
        this.lstcontratante = lstcontratante;
    }

    public List<Contratista> getLstcontratista() {
        return lstcontratista;
    }

    public void setLstcontratista(List<Contratista> lstcontratista) {
        this.lstcontratista = lstcontratista;
    }

    public List<Relacioncontratoobra> getLstncontratos() {
        return lstncontratos;
    }

    public void setLstncontratos(List<Relacioncontratoobra> lstncontratos) {
        this.lstncontratos = lstncontratos;
    }

    public CargadorArchivosWeb getCargadorDocumentos() {
        return cargadorDocumentos;
    }

    public void setCargadorDocumentos(CargadorArchivosWeb cargadorDocumentos) {
        this.cargadorDocumentos = cargadorDocumentos;
    }

    public CargadorArchivosWeb getCargadorImagenes() {
        return cargadorImagenes;
    }

    public void setCargadorImagenes(CargadorArchivosWeb cargadorImagenes) {
        this.cargadorImagenes = cargadorImagenes;
    }

    public Tipoinforme getInformeavalidar() {
        return informeavalidar;
    }

    public void setInformeavalidar(Tipoinforme informeavalidar) {
        this.informeavalidar = informeavalidar;
    }

    public boolean isActivocomentarios() {
        return activocomentarios;
    }

    public String actualizar() {
        return null;
    }

    public void setActivocomentarios(boolean activocomentarios) {
        this.activocomentarios = activocomentarios;
    }

    public String getPalabradoc() {
        return palabradoc;
    }

    public void setPalabradoc(String palabradoc) {
        this.palabradoc = palabradoc;
    }

    public SelectItem[] getSelectItemTipoImagen() {
        return selectItemTipoImagen;
    }

    public void setSelectItemTipoImagen(SelectItem[] selectItemTipoImagen) {
        this.selectItemTipoImagen = selectItemTipoImagen;
    }

    public List<Tipoimagen> getListaTipoImagen() {
        return listaTipoImagen;
    }

    public void setListaTipoImagen(List<Tipoimagen> listaTipoImagen) {
        this.listaTipoImagen = listaTipoImagen;
    }

    public Boolean getMostrarvideo() {
        System.out.println("mostrarvideo" + mostrarvideo);
        return mostrarvideo;
    }

    public void setMostrarvideo(Boolean mostrarvideo) {
        this.mostrarvideo = mostrarvideo;
    }

    public UIDataTable getTablaverVideo() {
        return tablaverVideo;
    }

    public void setTablaverVideo(UIDataTable tablaverVideo) {
        this.tablaverVideo = tablaverVideo;
    }

    public String getUrlvideo() {
        return urlvideo;
    }

    public void setUrlvideo(String urlvideo) {
        this.urlvideo = urlvideo;
    }

    public List<Videoevolucionobra> getListaVideoObra() {
        return listaVideoObra;
    }

    public void setListaVideoObra(List<Videoevolucionobra> listaVideoObra) {
        this.listaVideoObra = listaVideoObra;
    }

    public Boolean getBoolruta() {
        return boolruta;
    }

    public void setBoolruta(Boolean boolruta) {
        this.boolruta = boolruta;
    }

    public List<Puntoreferencia> getListapuntosreferencia() {
        return listapuntosreferencia;
    }

    public void setListapuntosreferencia(List<Puntoreferencia> listapuntosreferencia) {
        this.listapuntosreferencia = listapuntosreferencia;
    }

    public Tercero getSoli() {
        return soli;
    }

    public void setSoli(Tercero soli) {
        this.soli = soli;
    }

    public Integer getIntduraciondias() {
        return intduraciondias;
    }

    public void setIntduraciondias(Integer intduraciondias) {
        this.intduraciondias = intduraciondias;
    }

//    public BigDecimal getDeberiaestar() {
//        return deberiaestar;
//    }
//
//    public void setDeberiaestar(BigDecimal deberiaestar) {
//        this.deberiaestar = deberiaestar;
//    }
    public BigDecimal getValoresta() {
        return valoresta;
    }

    public void setValoresta(BigDecimal valoresta) {
        this.valoresta = valoresta;
    }

    public Boolean getHistorico() {
        return historico;
    }

    public void setHistorico(Boolean historico) {
        this.historico = historico;
    }

    public List<Historicoobra> getListahistorico() {
        return listahistorico;
    }

    public void setListahistorico(List<Historicoobra> listahistorico) {
        this.listahistorico = listahistorico;
    }

    public BigDecimal getValoren() {
        return valoren;
    }

    public void setValoren(BigDecimal valoren) {
        this.valoren = valoren;
    }

    public String getContratistapellido() {
        return contratistapellido;
    }

    public void setContratistapellido(String contratistapellido) {
        this.contratistapellido = contratistapellido;
    }

    public String getContratistanombre() {
        return contratistanombre;
    }

    public void setContratistanombre(String contratistanombre) {
        this.contratistanombre = contratistanombre;
    }

    public List<Localidad> getListalocalidad() {
        return listalocalidad;
    }

    public void setListalocalidad(List<Localidad> listalocalidad) {
        this.listalocalidad = listalocalidad;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getNacion() {
        return nacion;
    }

    public void setNacion(String nacion) {
        this.nacion = nacion;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public boolean isPolizacontrato() {
        return polizacontrato;
    }

    public void setPolizacontrato(boolean polizacontrato) {
        this.polizacontrato = polizacontrato;
    }

    public boolean isPuntootro() {
        return puntootro;
    }

    public void setPuntootro(boolean puntootro) {
        this.puntootro = puntootro;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Puntoobra> getListaPuntoObra() {
        return listaPuntoObra;
    }

    public void setListaPuntoObra(List<Puntoobra> listaPuntoObra) {
        this.listaPuntoObra = listaPuntoObra;
    }

    public UIDataTable getTablaOtroPunto() {
        return tablaOtroPunto;
    }

    public void setTablaOtroPunto(UIDataTable tablaOtroPunto) {
        this.tablaOtroPunto = tablaOtroPunto;
    }

    public String getImgTacometro() {
        return imgTacometro;
    }

    public void setImgTacometro(String imgTacometro) {
        this.imgTacometro = imgTacometro;
    }

    public List<Polizacontrato> getListaPolizacontratos() {
        return listaPolizacontratos;
    }

    public void setListaPolizacontratos(List<Polizacontrato> listaPolizacontratos) {
        this.listaPolizacontratos = listaPolizacontratos;
    }
    private int opcion = 0;

    public String getUbicacionmapa() {
        return ubicacionmapa;
    }

    public void setUbicacionmapa(String ubicacionmapa) {
        this.ubicacionmapa = ubicacionmapa;
    }

    public SelectItem[] getPoliza() {
        return Poliza;
    }

    public void setPoliza(SelectItem[] Poliza) {
        this.Poliza = Poliza;
    }

    public int getOpcion() {
        return opcion;
    }

    public void setOpcion(int opcion) {

        this.opcion = opcion;
    }

    public Documentoobra getDocumentoobra() {
        return documentoobra;
    }

    public void setDocumentoobra(Documentoobra documentoobra) {
        this.documentoobra = documentoobra;
    }

    public Documentoobra getDocumentoobraEd() {
        return documentoobraEd;
    }

    public void setDocumentoobraEd(Documentoobra documentoobraEd) {
        this.documentoobraEd = documentoobraEd;
    }

    public Documentoobra getDocumentoobraEl() {
        return documentoobraEl;
    }

    public void setDocumentoobraEl(Documentoobra documentoobraEl) {
        this.documentoobraEl = documentoobraEl;
    }

    public Imagenevolucionobra getImagenevolucionobra() {
        return imagenevolucionobra;
    }

    public void setImagenevolucionobra(Imagenevolucionobra imagenevolucionobra) {
        this.imagenevolucionobra = imagenevolucionobra;
    }

    public Imagenevolucionobra getImagenevolucionobraEd() {
        return imagenevolucionobraEd;
    }

    public void setImagenevolucionobraEd(Imagenevolucionobra imagenevolucionobraEd) {
        this.imagenevolucionobraEd = imagenevolucionobraEd;
    }

    public Imagenevolucionobra getImagenevolucionobraEl() {
        return imagenevolucionobraEl;
    }

    public void setImagenevolucionobraEl(Imagenevolucionobra imagenevolucionobraEl) {
        this.imagenevolucionobraEl = imagenevolucionobraEl;
    }

    public List<Documentoobra> getListaDocumentosobra() {
        return listaDocumentosobra;
    }

    public void setListaDocumentosobra(List<Documentoobra> listaDocumentosobra) {
        this.listaDocumentosobra = listaDocumentosobra;
    }

    public List<Imagenevolucionobra> getListaImagenesevolucionobra() {
        return listaImagenesevolucionobra;
    }

    public void setListaImagenesevolucionobra(List<Imagenevolucionobra> listaImagenesevolucionobra) {
        this.listaImagenesevolucionobra = listaImagenesevolucionobra;
    }

    public List<Tipodocumento> getListaTipoDocumento() {
        return listaTipoDocumento;
    }

    public void setListaTipoDocumento(List<Tipodocumento> listaTipoDocumento) {
        this.listaTipoDocumento = listaTipoDocumento;
    }

    public SelectItem[] getSelectItemTipoDocumento() {
        return selectItemTipoDocumento;
    }

    public void setSelectItemTipoDocumento(SelectItem[] selectItemTipoDocumento) {
        this.selectItemTipoDocumento = selectItemTipoDocumento;
    }

    public UIDataTable getTablaDocumentos() {
        return tablaDocumentos;
    }

    public void setTablaDocumentos(UIDataTable tablaDocumentos) {
        this.tablaDocumentos = tablaDocumentos;
    }

    public UIDataTable getTablaImagenesevolucion() {
        return tablaImagenesevolucion;
    }

    public void setTablaImagenesevolucion(UIDataTable tablaImagenesevolucion) {
        this.tablaImagenesevolucion = tablaImagenesevolucion;
    }

    public String getPalabraclave() {
        return palabraclave;
    }

    public void setPalabraclave(String palabraclave) {
        this.palabraclave = palabraclave;
    }

    public String getMensajefinaliza() {
        return mensajefinaliza;
    }

    public void setMensajefinaliza(String mensajefinaliza) {
        this.mensajefinaliza = mensajefinaliza;
    }

    public CargadorArchivosWeb getSubirActaFin() {
        return subirActaFin;
    }

    public void setSubirActaFin(CargadorArchivosWeb subirActaFin) {
        this.subirActaFin = subirActaFin;
    }

    public CargadorArchivosWeb getSubirActaRecFin() {
        return subirActaRecFin;
    }

    public void setSubirActaRecFin(CargadorArchivosWeb subirActaRecFin) {
        this.subirActaRecFin = subirActaRecFin;
    }

    public String getUrlactafin() {
        return urlactafin;
    }

    public void setUrlactafin(String urlactafin) {
        this.urlactafin = urlactafin;
    }

    public String getUrlactarecfin() {
        return urlactarecfin;
    }

    public void setUrlactarecfin(String urlactarecfin) {
        this.urlactarecfin = urlactarecfin;
    }

    public boolean isVerFin() {
        return verFin;
    }

    public void setVerFin(boolean verFin) {
        this.verFin = verFin;
    }

    public boolean isVerSuspender() {
        return verSuspender;
    }

    public void setVerSuspender(boolean verSuspender) {
        this.verSuspender = verSuspender;
    }

    public boolean isVerReiniciar() {
        return verReiniciar;
    }

    public void setVerReiniciar(boolean verReiniciar) {
        this.verReiniciar = verReiniciar;
    }

    public boolean isVerMenuModificar() {
        return verMenuModificar;
    }

    public void setVerMenuModificar(boolean verMenuModificar) {
        this.verMenuModificar = verMenuModificar;
    }

    public boolean isVerModificar() {
        return verModificar;
    }

    public void setVerModificar(boolean verModificar) {
        this.verModificar = verModificar;
    }

    public String getPlazo() {
        return plazo;
    }

    public void setPlazo(String plazo) {
        this.plazo = plazo;
    }

    public Obra getObra() {
        return getSessionBeanCobra().getCobraService().getObra();
    }

    public void setObra(Obra obra) {
        getSessionBeanCobra().getCobraService().setObra(obra);
    }

    public List<Relacioncontratoobra> getListaContrato() {
        return listaContrato;
    }

    public void setListaContrato(List<Relacioncontratoobra> listaContrato) {
        this.listaContrato = listaContrato;
    }

    public UIDataTable getTablaContrato() {
        return tablaContrato;
    }

    public void setTablaContrato(UIDataTable tablaContrato) {
        this.tablaContrato = tablaContrato;
    }

    public UIDataTable getTablaCosto() {
        return tablaCosto;
    }

    public void setTablaCosto(UIDataTable tablaCosto) {
        this.tablaCosto = tablaCosto;
    }

    public boolean isBtn_habilitarModificarObjeto() {
        return btn_habilitarModificarObjeto;
    }

    public void setBtn_habilitarModificarObjeto(boolean btn_habilitarModificarObjeto) {
        this.btn_habilitarModificarObjeto = btn_habilitarModificarObjeto;
    }

    public boolean isModificarObjetoObra() {
        return modificarObjetoObra;
    }

    public void setModificarObjetoObra(boolean modificarObjetoObra) {
        this.modificarObjetoObra = modificarObjetoObra;
    }

    public void habilitarBotonModificarObjeto() {
        btn_habilitarModificarObjeto = false;
        modificarObjetoObra = true;

    }

    public boolean isHabilitarModificarimpacto() {
        return habilitarModificarimpacto;
    }

    public void setHabilitarModificarimpacto(boolean habilitarModificarimpacto) {
        this.habilitarModificarimpacto = habilitarModificarimpacto;
    }

    public boolean isHabilitarGuardarimpacto() {
        return habilitarGuardarimpacto;
    }

    public void setHabilitarGuardarimpacto(boolean habilitarGuardarimpacto) {
        this.habilitarGuardarimpacto = habilitarGuardarimpacto;
    }

    public void habilitarBtnCancelar() {
        btn_habilitarModificarObjeto = true;
        modificarObjetoObra = false;
    }

    public int getControltipodocumento() {
        return controltipodocumento;
    }

    public void setControltipodocumento(int controltipodocumento) {
        this.controltipodocumento = controltipodocumento;
    }

    public void guardarModificacionObjeto() {
        btn_habilitarModificarObjeto = true;
        modificarObjetoObra = false;
        getSessionBeanCobra().getCobraService().guardarObra(getObra(), null, opcion);
    }

    public String getNombrePanelSuspension() {
        if (getObra() != null) {
            switch (getObra().getTipoestadobra().getIntestadoobra()) {
                case 6:
                    return "MODIFICAR SUSPENSIÓN DE PROYECTO";
                default:
                    return "Modificar SUSPENDER";
            }
        } else {
            return "Modificar SUSPENDER";
        }
    }

    public boolean isIniima() {
        return iniima;
    }

    public void setIniima(boolean iniima) {
        this.iniima = iniima;
    }

    /**
     * <p>
     * Automatically managed component initialization.
     * <strong>WARNING:</strong> This method is automatically generated, so any
     * user-specified code inserted here is subject to being replaced.</p>
     */
    private void _init() throws Exception {
    }

    // </editor-fold>
    //----------------------------------------------------------------------------
    //Metodos para obtebner ManagedBean
    //----------------------------------------------------------------------------
    protected ModificarProyecto getModificarProyecto() {
        return (ModificarProyecto) FacesUtils.getManagedBean("Supervisor$ModificarProyecto");
    }

    /**
     * <p>
     * Construct a new Page bean instance.</p>
     */
    public AdministrarObraNew() {

        if (getSessionBeanCobra().getUsuarioObra() != null && getSessionBeanCobra().getUsuarioObra().getTercero() != null) {
            getSessionBeanCobra().llenadodatos();

            id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
            if (id != null) {
                if (getSessionBeanCobra().getBundle().getString("vistasgiprom").compareTo("true") != 0) {
                    getSessionBeanCobra().getCobraService().guardarContadorVisitas(Integer.parseInt(id), getSessionBeanCobra().getUsuarioObra());
                }
            }
            mostrarGoogle();
            llenarSelectTipoDocumento();
            contratistanombre = "";
            //llenarContrato();
            EncontrarSolicitante();
            iniciarImagenes();
            llenarSelectTipoImagenes();

            //mostrarGoogle();
            //obtenerTacometro();
        } else {

            try {
                //System.out.println(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath());
                FacesContext.getCurrentInstance().getExternalContext().redirect("../Inicio.xhtml");

                //this.getSessionMap().clear();
            } catch (IOException ex) {
                Logger.getLogger(AdministrarObraNew.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void cargarListas() {
        llenarContrato();
        llenarContratoInterventoria();
        llenarContratoObra();

    }

    /**
     * <p>
     * Callback method that is called just before rendering takes place. This
     * method will <strong>only</strong> be called for the page that will
     * actually be rendered (and not, for example, on a page that handled a
     * postback and then navigated to a different page). Customize this method
     * to allocate resources that will be required for rendering this page.</p>
     */
    @Override
    public void prerender() {

        id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        int cual = opcion;
        if (id != null) {

            if (getObra() != null && id != null) {
                if (getSessionBeanCobra().getBundle().getString("vistasgiprom").compareTo("true") == 0) {

                    VwInmInfoMunicipal municipal = getSessionBeanCobra().getGipromService().obtenerInformacionMunicipalGipromxId(BigDecimal.valueOf(Double.valueOf(id)));
                    setObra(getSessionBeanCobra().castearVwInformacionMunicipaltoObra(municipal));
                    gragiprom = new GraficosGiprom();
                    gragiprom.pintarGraficoNBI(getObra().getNumvaltotamorti().toString());

                    List<VwIndIndicadorMunicipal> listindhabanual = getSessionBeanCobra().getGipromService().obtenerIndicadorMunicipalxcodmunicipio(municipal.getLclCodigo(), "040301", BigDecimal.ONE);
                    gragiprom.pintarGraficoNBIanual(listindhabanual);
                    for (VwIndIndicadorMunicipal vwIndIndicadorMunicipal : listindhabanual) {
                        getObra().setStrvereda(vwIndIndicadorMunicipal.getIndFntNombre());
                    }

                    listindhabanual = getSessionBeanCobra().getGipromService().obtenerIndicadorMunicipalxcodmunicipio(municipal.getLclCodigo(), "040104", BigDecimal.valueOf(2));
                    if (!listindhabanual.isEmpty()) {
                        VwIndIndicadorMunicipal den = listindhabanual.get(0);
                        listindhabanual = getSessionBeanCobra().getGipromService().obtenerIndicadorPoblacionMunicipalxcodmunicipio(municipal.getLclCodigo());
                        if (!listindhabanual.isEmpty()) {
                            gragiprom.pintarGraficoNBILocalidad(listindhabanual, den);
                        }

                    }
                    gragiprom.setListadesempeno(getSessionBeanCobra().getGipromService().obtenerIndicadorDesempenoMunicipalxcodmunicipio(municipal.getLclCodigo()));
                    gragiprom.llenarDistribucionEtnica(getSessionBeanCobra().getGipromService().obtenerIndicadorDistribucionEtnica(municipal.getLclCodigo()));
                    listaImagenesevolucionobra = getSessionBeanCobra().getCobraService().obtenerImagenesEvolucionxObra(getObra().getIntcodigoobra());

                } else {
                    if (String.valueOf(getObra().getIntcodigoobra()).compareTo(id) != 0) {
                        getSessionBeanCobra().getCobraService().guardarContadorVisitas(Integer.parseInt(id), getSessionBeanCobra().getUsuarioObra());
                        setObra(getSessionBeanCobra().getCobraService().encontrarObraPorId(Integer.parseInt(id)));

                        //llenarContrato();
                        obtenerTacometro();
                    }
                    getDetalleObra().iniciardetalle();
                    getDetalleObra().setFinentrega(getObra().getDatefecfinobra().toString());
                    iniciarImagenes();
                }

                opcion = cual;
            } else {
                getDetalleObra().iniciardetalle();

            }

        }

    }

    public String button_nuevaobra() {
        // TODO: Replace with your code
        getSessionBeanCobra().setCodigoobraalimentar(0);
        return "ingresar";
    }

    public void cargarObra(Obra obra) {
        setObra(obra);
//        obra.setContrato(getSessionBeanCobra().getCobraService().encontrarContratoxId(obra.getContrato().getIntidcontrato()));
        getDetalleObra().iniciardetalle();
        llenarSelectTipoDocumento();

        int division = 0;
        division = obra.getIntplazoobra() / obra.getPeriodomedida().getIntnrodiasperiomedida();
        if (obra.getIntplazoobra() % obra.getPeriodomedida().getIntnrodiasperiomedida() != 0) {
            division = division + 1;
        }
        switch (obra.getPeriodomedida().getIntidperiomedida()) {
            case 1:
                plazo = String.valueOf(division) + " SEMANAS";
                break;
            case 2:
                plazo = String.valueOf(division) + " QUINCENAS";
                break;
            case 3:
                plazo = String.valueOf(division) + " MESES";
                break;
        }

        obtenerTacometro();

    }

    public void obtenerTacometro() {
        BigDecimal divi = new BigDecimal(100);
        BigDecimal estaen = BigDecimal.valueOf(0.0);
        BigDecimal deberia = BigDecimal.valueOf(0.0);
        if (getObra().getNumvalejecobra() != null && getObra().getNumvalejecobra().compareTo(BigDecimal.valueOf(0)) != 0) {
            estaen = (getObra().getNumvalejecobra().divide(getObra().getNumvaltotobra(), RoundingMode.HALF_UP).multiply(divi));
            //valoren = estaen;
        }
        if (getDetalleObra().getAlimentacionultima() != null && getObra().getNumvaltotobra() != null) {
            if (getDetalleObra().getAlimentacionultima().getNumtotalproyacu() != null) {
                deberia = (getDetalleObra().getAlimentacionultima().getNumtotalproyacu().divide(getObra().getNumvaltotobra(), RoundingMode.HALF_UP).multiply(divi));
            }
        }

        if (estaen.equals(BigDecimal.valueOf(0.0)) && deberia.equals(BigDecimal.valueOf(0.0))) {
            imgTacometro = "http://chart.googleapis.com/chart?cht=gom&chs=100x100&chd=t:,&chl=Esta en :  |Deberia Estar :&chtt=EJECUCIÓN+VS+PROYECCIÓN";
        } else {

            imgTacometro = "http://chart.googleapis.com/chart?cht=gom&chs=120x100&chd=t:" + estaen + "," + deberia + "&chl=Esta en" + estaen + " |Debería Estar" + deberia;
            valoresta = estaen;
            //deberiaestar = deberia;
        }
    }

    public String guardarVideo(String nomarch, String URLorigen, String URLdestino) {
        String ArchivoPath = "";

        ServletContext theApplicationsServletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

        ArchivoPath = theApplicationsServletContext.getRealPath(URLorigen + nomarch);
        try {

            File carpeta = new File(ArchivoPath);
            if (carpeta.exists()) {
                ArchivoPath = theApplicationsServletContext.getRealPath(URLdestino);
                try {

                    File carpetadest = new File(ArchivoPath);
                    if (!carpetadest.exists()) {
                        carpetadest.mkdirs();

                    }

                } catch (Exception ex) {
                    FacesUtils.addErrorMessage("No pudo crear carpeta ");
                }

                String cero = nomarch.substring(0, 1);

                if (cero.compareTo("0") == 0) {
                    nomarch = getObra().getIntcodigoobra() + nomarch.substring(1, nomarch.length());
                }

                carpeta.renameTo(new File(ArchivoPath + "/" + nomarch));
                return URLdestino;
            } else {
                return nomarch;

            }

        } catch (Exception ex) {
            FacesUtils.addErrorMessage(bundle.getString("nosepuedesubir"));
        }
        return null;
    }

    public boolean guardarArchivoObra(String nomarch, String URLorigen, String URLdestino) {
        String ArchivoPath = "";

        ServletContext theApplicationsServletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

        ArchivoPath = theApplicationsServletContext.getRealPath(URLorigen + nomarch);
        try {

            File carpeta = new File(ArchivoPath);
            if (carpeta.exists()) {
                ArchivoPath = theApplicationsServletContext.getRealPath(URLdestino);
                try {

                    File carpetadest = new File(ArchivoPath);
                    if (!carpetadest.exists()) {
                        carpetadest.mkdirs();
                    }

                } catch (Exception ex) {
                    FacesUtils.addErrorMessage(bundle.getString("nopudocrearcarpeta"));//"No pudo crear carpeta ");
                }

                carpeta.renameTo(new File(ArchivoPath + "/" + nomarch));
                return true;
            } else {

                return false;
            }

        } catch (Exception ex) {
            FacesUtils.addErrorMessage(bundle.getString("nosepuedesubir"));//"Cannot upload file ");
        }

        return false;
    }

    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }

    protected DetalleObra getDetalleObra() {
        return (DetalleObra) FacesUtils.getManagedBean("Supervisor$DetalleObra");
    }

    protected IngresarNuevaObra getIngresarNuevaObra() {
        return (IngresarNuevaObra) FacesUtils.getManagedBean("Supervisor$IngresarNuevaObra");
    }

    protected Alimentar getAlimentar() {
        return (Alimentar) FacesUtils.getManagedBean("Supervisor$Alimentar");
    }

    protected ModificarObra getModificar() {
        return (ModificarObra) FacesUtils.getManagedBean("Supervisor$ModificarObra");
    }

    public String mostrarMenuModificar() {
        verMenuModificar = true;
        noMostrarModificar();
        noMostrarSuspender();
        noMostrarReinicio();
        return "menumodificar";
    }

    public void noMostrarMenuModificar() {
        verMenuModificar = false;
    }

    public String mostrarSuspender() {
        verSuspender = true;
        noMostrarMenuModificar();
        noMostrarModificar();
        noMostrarReinicio();
        return "suspenderObra";
    }

    public void noMostrarSuspender() {
        verSuspender = false;
    }

    public String mostrarReinicio() {
        verReiniciar = true;
        noMostrarMenuModificar();
        noMostrarModificar();
        noMostrarSuspender();
        return null;
    }

    public void noMostrarReinicio() {
        verReiniciar = false;
    }

    public String mostrarModificar() {
        verModificar = true;
        noMostrarMenuModificar();
        noMostrarSuspender();
        noMostrarReinicio();
        getModificarProyecto().inicializarPagina();
        return "menumodificarbutton";
    }

    public void noMostrarModificar() {
        verModificar = false;
    }

    //finalizar
    public String registrarReciboFinal() throws ArchivoExistenteException {
        mensajefinaliza = "";
        if (urlactarecfin.compareTo("") != 0) {
            if (getObra().getDatefecrecfin() != null) {
                String carpetaDoc = MessageFormat.format(RutasWebArchivos.DOCS_OBRA, "" + this.getObra().getIntcodigoobra()) + "/Documentos/";
                subirActaRecFin.getArchivoWeb().cambiarNombre(null, true);
                subirActaRecFin.guardarArchivosTemporales(carpetaDoc, false);
                System.out.println(subirActaRecFin.getArchivos().size());
                String rutaWebDoc = subirActaRecFin.getArchivos().get(0).getRutaWeb();
                Tipodocumento tipdoc = new Tipodocumento(10, bundle.getString("actaparcial"), false);
                Documentoobra doc = new Documentoobra(getObra(), tipdoc, rutaWebDoc, bundle.getString("actarecibofinal"), getObra().getDatefecrecfin());
                getSessionBeanCobra().getCobraService().guardarDocumento(doc);

                Novedad nov = new Novedad(0, new Tiponovedad(10, ""), getObra(), new Date());
                getObra().setNovedads(new LinkedHashSet());
                getObra().getNovedads().add(nov);
                getObra().setTipoestadobra(new Tipoestadobra(4));
                getSessionBeanCobra().getCobraService().guardarObra(getObra(), getSessionBeanCobra().getUsuarioObra(), -1);

                urlactarecfin = "";
                subirActaRecFin = new CargadorArchivosWeb();
                urlactarecfin = "";

            } else {
                mensajefinaliza = bundle.getString("debeseleccionarunafecha"); //"Debe seleccionar una fecha para el Acta de Recibo Final";
            }
        } else {
            mensajefinaliza = bundle.getString("debeadjutnarunacta");//"Debe adjuntar un Acta de Recibo Final";
        }
        return null;
    }

    public String registrarUltimaActaParcial() {
        mensajefinaliza = "";
        getObra().setTipoestadobra(new Tipoestadobra(5));
        getSessionBeanCobra().getCobraService().guardarObra(getObra(), getSessionBeanCobra().getUsuarioObra(), -1);

        return null;
    }

    public String validarLiquidacion() {

        mensajefinaliza = "";
        verFin = false;
        if (urlactafin.compareTo("") != 0) {
            if (getObra().getDatefecliqui() != null) {
                verFin = true;
            } else {
                mensajefinaliza = bundle.getString("debeseleccionarunafechaacta");//"Debe seleccionar una fecha para el Acta de Liquidación";
            }

        } else {
            mensajefinaliza = bundle.getString("debeadjuntarunactaliqui");//"Debe adjuntar un Acta de Liquidación";
        }

        return null;
    }

    public String registrarLiquidacion() {
        String realArchivoPath = "";
        String URL = "/resources/Documentos/ObrasVigentes/" + getObra().getIntcodigoobra() + "/Documentos/";

        ServletContext theApplicationsServletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

        realArchivoPath = theApplicationsServletContext.getRealPath(URL);
        try {

            File carpeta = new File(realArchivoPath);
            if (!carpeta.exists()) {
                carpeta.mkdirs();

            }

        } catch (Exception ex) {
            FacesUtils.addErrorMessage(bundle.getString("nosepuedesubir"));
        }
        try {
            subirActaFin.guardarArchivosTemporales(realArchivoPath, false);

        } catch (ArchivoExistenteException ex) {
            Logger.getLogger(AdministrarObraNew.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        Tipodocumento tipdoc = new Tipodocumento(10, bundle.getString("actaparcial"), false);
        Documentoobra doc = new Documentoobra(getObra(), tipdoc, "/resources/Documentos/ObrasVigentes/" + getObra().getIntcodigoobra() + "/Documentos/" + urlactafin, bundle.getString("actadeliquida"), getObra().getDatefecliqui());

        getSessionBeanCobra().getCobraService().guardarDocumento(doc);

        Novedad nov = new Novedad(0, new Tiponovedad(2, ""), getObra(), new Date());
        getObra().setNovedads(new LinkedHashSet());
        getObra().getNovedads().add(nov);
        getObra().setTipoestadobra(new Tipoestadobra(2));
        getSessionBeanCobra().getCobraService().guardarObra(getObra(), getSessionBeanCobra().getUsuarioObra(), 7);
        urlactafin = "";
        subirActaFin = new CargadorArchivosWeb();
        obraFinalizada = true;
        return null;
    }

    public String pathDocumentoRecFin() {
        if (subirActaRecFin.getArchivos().size() > 0) {

            for (ArchivoWeb nombreoriginal : subirActaRecFin.getArchivos()) {
                urlactarecfin = nombreoriginal.getNombre();
            }
        } else {
            urlactarecfin = "";
        }

        return null;
    }

    public String pathDocumentoFin() {
        if (subirActaFin.getArchivos().size() > 0) {
            for (ArchivoWeb nombreoriginal : subirActaFin.getArchivos()) {
                urlactafin = nombreoriginal.getNombre();
            }

        } else {
            urlactafin = "";
        }

        return null;
    }

    public String borrarActaReciboFinal() {
        subirActaRecFin.borrarDatosSubidos();
        urlactarecfin = "";
        return null;
    }

    public String borrarActaFinal() {
        subirActaFin.borrarDatosSubidos();
        urlactafin = "";
        return null;
    }

    public void llenarSelectTipoDocumento() {
        controltipodocumento = 3;
        this.listaTipoDocumento = getSessionBeanCobra().getCobraService().encontrarTiposDocumentos(controltipodocumento);
        this.selectItemTipoDocumento = new SelectItem[this.listaTipoDocumento.size()];
        int i = 0;
        SelectItem selectItem = new SelectItem(0, bundle.getString("seleccioneuntipo"));//"Seleccione un tipo");
        for (Tipodocumento td : this.listaTipoDocumento) {
            selectItem = new SelectItem(td.getInttipodoc(), td.getStrdesctipodoc());
            this.selectItemTipoDocumento[i++] = selectItem;
        }

    }

    public String bt_download_documento_action() {
        // TODO: Process the action. Return value is a navigation
        // case name where null will return to the same page.
        Documentoobra doc = (Documentoobra) tablaDocumentos.getRowData();
        //this.getSessionBeanCobra().setUrlAbri(doc.getStrubicacion());
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/" + getSessionBeanCobra().getBundle().getString("versioncobra") + "/" + doc.getStrubicacion());

        } catch (IOException ex) {
            Logger.getLogger(AdministrarObraNew.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public String bt_editar_documento_action() {
        // TODO: Process the action. Return value is a navigation
        // case name where null will return to the same page.
//        AdministrarObraNew ad = (AdministrarObraNew) FacesUtils.getManagedBean("Supervisor$AdministrarObraNew");
//        this.documentoobraEd = ad.getListaDocumentosobra().get(filaSeleccionada);
        this.documentoobraEd = (Documentoobra) tablaDocumentos.getRowData();
        return null;
    }

    public String bt_aceptar_editar_documento_action() {

        for (Tipodocumento td : this.listaTipoDocumento) {
            if (td.getInttipodoc() == this.documentoobraEd.getTipodocumento().getInttipodoc()) {
                this.documentoobraEd.setTipodocumento(td);
                break;
            }
        }

        //getSessionBeanCobra().getCobradao().cerrarSession();
        //documentoObraDao.guardarOrActualizar(this.documentoobraEd);
        getSessionBeanCobra().getCobraService().guardarDocumento(this.documentoobraEd);
        this.documentoobraEd = new Documentoobra();
        //cargarObra(obra);

        return null;
    }

    public String bt_eliminar_documento_action() {
        // TODO: Process the action. Return value is a navigation
        // case name where null will return to the same page.
//        AdministrarObraNew ad = (AdministrarObraNew) FacesUtils.getManagedBean("Supervisor$AdministrarObraNew");
//        this.documentoobraEl = ad.getListaDocumentosobra().get(filaSeleccionada);
        this.documentoobraEl = (Documentoobra) tablaDocumentos.getRowData();
        bt_aceptar_eliminar_documento_action();
        return null;
    }

    public String bt_aceptar_eliminar_documento_action() {
        // TODO: Process the action. Return value is a navigation
        // case name where null will return to the same page.

        listaDocumentosobra.remove(this.documentoobraEl);
        getSessionBeanCobra().getCobraService().borrarDocumento(this.documentoobraEl);

        this.eliminarDocumento();
        //cargarObra(obra);

        return null;
    }

    public String bt_agregar_documento_action() {
        // TODO: Replace with your code
        for (Tipodocumento td : this.listaTipoDocumento) {
            if (td.getInttipodoc() == this.documentoobra.getTipodocumento().getInttipodoc()) {
                this.documentoobra.setTipodocumento(td);
                break;
            }
        }
        if (getSessionBeanCobra().getBundle().getString("vistasgiprom").compareTo("true") == 0) {

            getSessionBeanCobra().getCobraService().guardarObra(getObra(), getSessionBeanCobra().getUsuarioObra(), -1);

        }
        this.documentoobra.setObra(getObra());
        try {

            subirDocumento();
            getSessionBeanCobra().getCobraService().guardarDocumento(this.documentoobra);
            listaDocumentosobra.add(documentoobra);
            this.documentoobra = new Documentoobra();
            this.documentoobra.setTipodocumento(new Tipodocumento(1, "", true));
            this.documentoobra.setDatefecha(new Date());
            this.cargadorDocumentos = new CargadorArchivosWeb();

        } catch (ArchivoExistenteException ex) {
            Logger.getLogger(ModificarProyecto.class
                    .getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance()
                    .addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    Propiedad.getValor("docexistenteerror"), ""));
        }
        return null;
    }
    
     public String agregarUrlOrfeo() {        
         if (getSessionBeanCobra().getBundle().getString("vistasgiprom").compareTo("true") == 0) {            
                 getSessionBeanCobra().getCobraService().guardarObra(getObra(), getSessionBeanCobra().getUsuarioObra(), -1);            
         }
        this.documentoobra.setObra(getObra());
        try {            
            this.documentoobra.setTipodocumento(new Tipodocumento(Tipodocumento.URL_ORFEO));
            getSessionBeanCobra().getCobraService().guardarDocumento(this.documentoobra);
            listaDocumentosobra.add(documentoobra);
            this.documentoobra = new Documentoobra();
            this.documentoobra.setTipodocumento(new Tipodocumento(1, "", true));
            this.documentoobra.setDatefecha(new Date());
            this.cargadorDocumentos = new CargadorArchivosWeb();
        } catch (Exception ex) {
            Logger.getLogger(ModificarProyecto.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;       
    }

    private void eliminarDocumento() {
        ServletContext theApplicationsServletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String carpetaRealServidor = theApplicationsServletContext.getRealPath(this.documentoobraEl.getStrubicacion());

        try {
            File carpeta = new File(carpetaRealServidor);
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            File file = new File(carpeta.getCanonicalPath());
            file.delete();
        } catch (Exception ex) {
            FacesUtils.addErrorMessage(bundle.getString("nofueposibleeliminarelarch"));//"No fue posible eliminar el archivo");
        }
    }

    public void subirDocumento() throws ArchivoExistenteException {
        String carpetaDoc = MessageFormat.format(RutasWebArchivos.DOCS_OBRA, "" + this.getObra().getIntcodigoobra());
        cargadorDocumentos.getArchivoWeb().cambiarNombre(null, true);
        cargadorDocumentos.guardarArchivosTemporales(carpetaDoc, false);
        String rutaWebDoc = cargadorDocumentos.getArchivos().get(0).getRutaWeb();
        this.documentoobra.setStrubicacion(rutaWebDoc);
    }

    public void llenarTablaImagenevolucionobra() {
        Iterator itrtImagenevolucionobra = this.getObra().getImagenevolucionobras().iterator();
        this.listaImagenesevolucionobra = new ArrayList<Imagenevolucionobra>();
        while (itrtImagenevolucionobra.hasNext()) {
            Imagenevolucionobra img = (Imagenevolucionobra) itrtImagenevolucionobra.next();
            if (img.getAlimentacions().size() == 0) {
                this.listaImagenesevolucionobra.add(img);
            }

        }
    }

    public String bt_download_imagenevolucion_action() {
        // TODO: Process the action. Return value is a navigation
        // case name where null will return to the same page.
//        AdministrarObraNew adminObra = (AdministrarObraNew) FacesUtils.getManagedBean("Supervisor$AdministrarObraNew");
//        Imagenevolucionobra doc = adminObra.getListaImagenesevolucionobra().get(filaSeleccionada);
        // this.getSessionBeanCobra().setUrlAbri(doc.getStrubicacion());
        Imagenevolucionobra doc = (Imagenevolucionobra) tablaImagenesevolucion.getRowData();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/" + getSessionBeanCobra().getBundle().getString("versioncobra") + doc.getStrubicacion());

        } catch (IOException ex) {
            Logger.getLogger(NuevoContratoBasico.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return "Download";
    }

    public String bt_editar_imagenevolucion_action() {
        this.imagenevolucionobraEd = (Imagenevolucionobra) tablaImagenesevolucion.getRowData();
//        AdministrarObraNew adminObra = (AdministrarObraNew) FacesUtils.getManagedBean("Supervisor$AdministrarObraNew");
//        this.imagenevolucionobraEd = adminObra.getListaImagenesevolucionobra().get(filaSeleccionada);
        return null;
    }

    /**
     * Metodo que se ejecuta cuando se selecciona la opción de establecer como
     * valla de algún registro del listado de imágenes. Establece la imagen
     * seleccionada en el atributo imagenevolucionobraEd
     *
     * @param filaSeleccionada Corresponde a la fila de la que proviene la
     * acción en la tabla
     * @return null
     */
    public String btEstablecerVallaAction() {
        this.imagenevolucionobraEd = (Imagenevolucionobra) tablaImagenesevolucion.getRowData();
//        this.imagenevolucionobraEd = listaImagenesevolucionobra.get(filaSeleccionada);
        return null;
    }

    /**
     * Establece la imagen para la valla del proyecto y la ubicación de dicha
     * imagen en la obra a la que pertenece
     *
     * @return null
     */
    public String btConfirmarEstablecerVallaAction() {
        getSessionBeanCobra().getCobraService().funcion_EstablecerValla(imagenevolucionobraEd.getIntidimagen());
        listaImagenesevolucionobra = getSessionBeanCobra().getCobraService().obtenerImagenesEvolucionxObra(getObra().getIntcodigoobra());
        return null;
    }

    /**
     * Metodo que se ejecuta cuando se selecciona la opción de establecer como
     * imagen actual en algún registro del listado de imágenes. Establece la
     * imagen seleccionada en el atributo imagenevolucionobraEd
     *
     * @param filaSeleccionada Corresponde a la fila de la que proviene la
     * acción en la tabla
     * @return null
     */
    public String btEstablecerImagenActualAction() {
        this.imagenevolucionobraEd = (Imagenevolucionobra) tablaImagenesevolucion.getRowData();
        //this.imagenevolucionobraEd = listaImagenesevolucionobra.get(filaSeleccionada);
        return null;
    }

    /**
     * Establece la imagen actual del proyecto
     *
     * @return null
     */
    public String btConfirmarEstablecerImagenActualAction() {
        getSessionBeanCobra().getCobraService().funcion_EstablecerImagenActual(imagenevolucionobraEd.getIntidimagen());
        listaImagenesevolucionobra = getSessionBeanCobra().getCobraService().obtenerImagenesEvolucionxObra(getObra().getIntcodigoobra());
        return null;
    }

    public String bt_aceptar_editar_imagenevolucion_action() {
        imagenevolucionobraEd.setTipoimagen(getSessionBeanCobra().getCobraService().obtenerTipoimagenporId(imagenevolucionobraEd.getTipoimagen().getIntidtipoimagen()));
        getSessionBeanCobra().getCobraService().guardarImagen(imagenevolucionobraEd);

        return null;
    }

    public String bt_eliminar_imagenevolucion_action() {
        // TODO: Process the action. Return value is a navigation
        // case name where null will return to the same page.
//        AdministrarObraNew adminObra = (AdministrarObraNew) FacesUtils.getManagedBean("Supervisor$AdministrarObraNew");
//        this.imagenevolucionobraEl = adminObra.getListaImagenesevolucionobra().get(filaSeleccionada);
        try {
            this.imagenevolucionobraEl = (Imagenevolucionobra) tablaImagenesevolucion.getRowData();
            listaImagenesevolucionobra.remove(this.imagenevolucionobraEl);
            getSessionBeanCobra().getCobraService().borrarImagen(this.imagenevolucionobraEl);

        } catch (Exception e) {
            FacesUtils.addErrorMessage(bundle.getString("erroreliminariamgen"));
        }
        return null;
    }

    public String bt_aceptar_eliminar_imagenevolucion_action() {
        getSessionBeanCobra().getCobraService().borrarImagen(imagenevolucionobraEl);
        this.eliminarImagenevolucion();
        listaImagenesevolucionobra.remove(imagenevolucionobraEl);

        return null;
    }

    public String bt_agregar_imagenevolucion_action() {
        if (getSessionBeanCobra().getBundle().getString("vistasgiprom").compareTo("true") == 0) {

            getSessionBeanCobra().getCobraService().guardarObra(getObra(), getSessionBeanCobra().getUsuarioObra(), -1);

        }

        this.imagenevolucionobra.setObra(getObra());
        try {
            subirImagenevolucion();
            imagenevolucionobra.setTipoimagen(getSessionBeanCobra().getCobraService().obtenerTipoimagenporId(imagenevolucionobra.getTipoimagen().getIntidtipoimagen()));

            getSessionBeanCobra().getCobraService().guardarImagen(this.imagenevolucionobra);
            if (imagenevolucionobra.getTipoimagen().getIntidtipoimagen() == 2) {
                getSessionBeanCobra().getCobraService().funcion_EstablecerImagenActual(imagenevolucionobra.getIntidimagen());
            }
            if (imagenevolucionobra.getTipoimagen().getIntidtipoimagen() == 5) {
                getSessionBeanCobra().getCobraService().funcion_EstablecerValla(imagenevolucionobra.getIntidimagen());
            }
            listaImagenesevolucionobra = getSessionBeanCobra().getCobraService().obtenerImagenesEvolucionxObra(getObra().getIntcodigoobra());
            this.imagenevolucionobra = new Imagenevolucionobra();
            this.imagenevolucionobra.setTipoimagen(new Tipoimagen());
            this.imagenevolucionobra.setDatefecha(new Date());
            this.cargadorImagenes = new CargadorArchivosWeb();

        } catch (ArchivoExistenteException ex) {
            Logger.getLogger(ModificarProyecto.class
                    .getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance()
                    .addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    Propiedad.getValor("docexistenteerror"), ""));
        }
        return null;
    }

    private void eliminarImagenevolucion() {
        ServletContext theApplicationsServletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String carpetaRealServidor = theApplicationsServletContext.getRealPath(this.imagenevolucionobraEl.getStrubicacion());

        try {
            File carpeta = new File(carpetaRealServidor);
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            File file = new File(carpeta.getCanonicalPath());
            file.delete();
        } catch (Exception ex) {
            FacesUtils.addErrorMessage(bundle.getString("nofueposibleeliminarelarch"));//"No fue posible eliminar el archivo");
        }

    }

//    public boolean filtrarImagenesevolucion(Object actual) {
//        Imagenevolucionobra doc = (Imagenevolucionobra) actual;
//        if (this.valorFiltroImagenevolucionobra.length() == 0) {
//            return true;
//        }
//
//        if (doc.getStrnombre().toLowerCase().contains(this.valorFiltroImagenevolucionobra.toLowerCase())) {
//            return true;
//        } else {
//            return false;
//        }
//
//    }
    /**
     * Almacena en el servidor las imágenes relacionados a la obra
     *
     * @throws ArchivoExistenteException
     */
    public void subirImagenevolucion() throws ArchivoExistenteException {
        String carpetaDoc = MessageFormat.format(RutasWebArchivos.IMGS_OBRA, "" + getObra().getIntcodigoobra());
        try {
            cargadorImagenes.getArchivoWeb().cambiarNombre(null, true);
            cargadorImagenes.guardarArchivosTemporales(carpetaDoc, false);
            String rutaWebImg = cargadorImagenes.getArchivos().get(0).getRutaWeb();
            String nombreImg = cargadorImagenes.getArchivos().get(0).getNombre();
            this.imagenevolucionobra.setStrubicacion(rutaWebImg);
            this.imagenevolucionobra.setStrnombrearchivo(nombreImg);
            RedimensionarImagen.scale(ArchivoWebUtil.obtenerRutaAbsoluta(rutaWebImg), 640, 5, ArchivoWebUtil.obtenerRutaAbsoluta(rutaWebImg));

        } catch (IOException ex) {
            Logger.getLogger(AdministrarObraNew.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String iniciarDocumentos() {
        opcion = 3;
        controltipodocumento = 3;
        listaDocumentosobra = getSessionBeanCobra().getCobraService().obtenerDocumentosObra(getObra().getIntcodigoobra());
        int i = 0;
        List<Documentoobra> listdoccontrato = new ArrayList<Documentoobra>();
        List<Documentoobra> listdoccontratointerv = new ArrayList<Documentoobra>();
        if (listaDocumentosobra == null) {
            listaDocumentosobra = new ArrayList<Documentoobra>();
        }

        if (!listaContrato.isEmpty()) {
            i = 0;
            while (i < listaContrato.size()) {
                listdoccontrato = getSessionBeanCobra().getCobraService().obtenerDocumentosxContrato(listaContrato.get(i).getContrato());

                if (!listdoccontrato.isEmpty()) {

                    listaDocumentosobra.addAll(listdoccontrato);
                }

                i++;
            }
        }

        if (listaContratoInterventoria != null && !listaContratoInterventoria.isEmpty()) {
            i = 0;
            while (i < listaContratoInterventoria.size()) {
                listdoccontratointerv = getSessionBeanCobra().getCobraService().obtenerDocumentosxContrato(listaContratoInterventoria.get(i).getContrato());

                if (!listdoccontratointerv.isEmpty()) {

                    listaDocumentosobra.addAll(listdoccontratointerv);
                }

                i++;
            }
        }
        this.documentoobra = new Documentoobra();
        this.documentoobra.setTipodocumento(new Tipodocumento(1, "", true));
        this.documentoobra.setDatefecha(new Date());
        this.cargadorDocumentos = new CargadorArchivosWeb();
        return "admindocumentobra";
    }

    public String iniciarImagenes() {
        opcion = 4;
        iniima = true;

        listaImagenesevolucionobra = getSessionBeanCobra().getCobraService().obtenerImagenesEvolucionxObra(getObra().getIntcodigoobra());
        for (Imagenevolucionobra imagenevolucionobra1 : listaImagenesevolucionobra) {

            Set<Alimentacion> setalimenta = new HashSet<Alimentacion>(getSessionBeanCobra().getCobraService().obtenerAlimentacionXImagen(imagenevolucionobra1.getIntidimagen()));
            imagenevolucionobra1.setAlimentacions(setalimenta);

        }
        if (listaImagenesevolucionobra.isEmpty()) {
            iniima = false;
        }

        llenarSelectTipoImagenes();
        this.imagenevolucionobra.setTipoimagen(new Tipoimagen(1, "", false));

        return "adminimaginobra";
    }

    public String iniciarVideos() {
        opcion = 6;
        listaVideoObra = getSessionBeanCobra().getCobraService().obtenerVideoxObra(getObra().getIntcodigoobra());
        videoEnlace = new Videoevolucionobra();
        videoEnlace.setObra(getObra());
        lstVideosEnlazados = getSessionBeanCobra().getCobraService().obtenerEnlacesVideoxObra(getObra().getIntcodigoobra());
        if (listaVideoObra.size() <= 0) {
            mostrarvideo = true;
        } else {
            mostrarvideo = false;
        }
        return "adminvideoobra";
    }

    public String cantidadColumnasVideos() {
        cantidadColumnas = 3;
        String columnas = "3";
        if (listaVideoObra.size() < 3) {
            columnas = listaVideoObra.size() + "";
            return columnas;
        } else {

            return columnas;
        }

    }

    public String iniciardeta() {
        opcion = 0;
        getDetalleObra().iniciardetalle();
        return "admindetalleObra";
    }

    public String iniciarAliment() {
        try {
            if (!getObra().isBoolobraterminada()) {
                opcion = 1;
                getAlimentar().limpiarAlimentar();
                getAlimentar().cargaListaPeridos();
                return "adminalimentar";
            } else {
                FacesUtils.addErrorMessage(bundle.getString("mensaje2declararobra"));
            }
        } catch (Exception e) {
            getSessionBeanCobra().getCobraService().getLog().info(bundle.getString("mensaje5declararobra") + " " + e.getMessage() + ". Fecha =  " + new Date() + ");");
        }
        return null;
    }

    public String iniciarModif() {
        try {
            if (!getObra().isBoolobraterminada()) {
                opcion = 2;
                return "adminmodificarObra";
            } else {
                FacesUtils.addErrorMessage(bundle.getString("mensaje2declararobra"));
            }
        } catch (Exception e) {
            getSessionBeanCobra().getCobraService().getLog().info(bundle.getString("mensaje6declararobra") + " " + e.getMessage() + ". Fecha =  " + new Date() + ");");
        }
        return null;
    }

    public String iniciarEvolucion() {
        opcion = 3;

        if (getSessionBeanCobra().getCobraService().obtenerUltimaalimentacion(getObra().getIntcodigoobra()) != null) {
            getDetalleObra().llenarPeriodoAliment();
            getDetalleObra().llenarmodalvalidacion();
            this.cargarHistorialSVA();

            return "evolucion";
        } else {
            FacesUtils.addErrorMessage(bundle.getString("esteproyectonosehaalimentado"));
            return "null";
        }
    }

    public String llenarContrato() {
        listaContrato = new ArrayList<Relacioncontratoobra>();
        contratistanombre = "";
        contratistapellido = "";
        listaContrato.addAll(getSessionBeanCobra().getCobraService().encontrarRelacionContratosObra(getObra().getIntcodigoobra(), false));
        if (Boolean.parseBoolean(bundle.getString("iniciaenmapa"))) {
            listaContrato.addAll(getSessionBeanCobra().getCobraService().encontrarRelacionContratosObra(getObra().getIntcodigoobra(), true));
        }
        return null;
    }

    public String llenarContratoObra() {

        listaContratoObra = new ArrayList<Relacioncontratoobra>();
        listaContratoObra.addAll(getSessionBeanCobra().getCobraService().encontrarRelacionContratosObra(getObra().getIntcodigoobra(), false));
        return null;
    }

    public String llenarContratoInterventoria() {

        listaContratoInterventoria = new ArrayList<Relacioncontratoobra>();
        listaContratoInterventoria.addAll(getSessionBeanCobra().getCobraService().encontrarRelacionContratosObra(getObra().getIntcodigoobra(), true));

        return null;
    }

    public String iniciarFinalizar() {
        opcion = 5;
        return "adminfinalizar";
    }

    public String llenarPolizas() {
        listaPolizacontratos = getSessionBeanCobra().getCobraService().encontrarPolizasxContrato(getObra().getContrato().getIntidcontrato());
        Poliza = new SelectItem[listaPolizacontratos.size()];
        int i = 0;
        for (Polizacontrato polCont : listaPolizacontratos) {
            SelectItem poli = new SelectItem(polCont.getIntidpolizacontrato(), polCont.getStrdescripcion());
            Poliza[i++] = poli;
        }
        if (getListaPolizacontratos().size() > 0) {
            polizacontrato = true;
        } else {
            polizacontrato = false;
        }
        return null;
    }
    private boolean sede = false;

    public boolean isSede() {
        return sede;
    }

    public void setSede(boolean sede) {
        this.sede = sede;
    }

    public String mostrarGoogle() {
        sede = false;
        boolruta = false;
        ubicacionmapa = "http://maps.google.com/maps/api/staticmap?center=" + getObra().getFloatlatitud() + "," + getObra().getFloatlongitud() + "&zoom=10&size=450x200&&markers=color:blue|label:.|" + getObra().getFloatlatitud() + "," + getObra().getFloatlongitud() + "&sensor=false";
        listalocalidad = getSessionBeanCobra().getCobraService().encontrarLocalidadPorObra(getObra());
        if (getObra().getSedeeducativa() != null) {
            setSede(true);
        }
//        departamento = listalocalidad.get(0).getStrdepartamento();
//        municipio = listalocalidad.get(0).getStrmunicipio();

        if (getObra().getRuta() != null) {
            boolruta = true;
            listapuntosreferencia = getSessionBeanCobra().getCobraService().encontrarPuntosReferenciaxRuta(getObra().getRuta().getStrcodigotramo());
            int i = 0;
            ubicacionmapa = "http://maps.google.com/maps/api/staticmap?size=450x300&path=color:blue|weight:5|";
            while (i < listapuntosreferencia.size()) {

                ubicacionmapa = ubicacionmapa + listapuntosreferencia.get(i).getFloatlatitud() + "," + listapuntosreferencia.get(i).getFloatlongitud();

                //  "|"
                i++;
                if (i < listapuntosreferencia.size()) {
                    ubicacionmapa = ubicacionmapa + "|";
                }
            }
            ubicacionmapa = ubicacionmapa + "&sensor=false";
        }
        return null;
    }

    /* public void mostrarLocalidad() {
   

     listalocalidad = getSessionBeanCobra().getCobraService().encontrarLocalidadPorObra(obra.getObra());

     departamento = listalocalidad.get(0).getStrdepartamento();
     municipio = listalocalidad.get(0).getStrmunicipio();

     /*int i = 0;
     while (i < listalocalidad.size()) {

     switch (getObra().getTipoorigen().getIntidtipoorigen()) {
     case 1:

     setDepartamento(listalocalidad.get(i).getStrcodigolocalidad());
     setMunicipio(listalocalidad.get(i).getStrcodigolocalidad());
     break;
     case 2:
     setDepartamento(listalocalidad.get(i).getStrcodigolocalidad());
     break;
     case 3:
     setRegion(listalocalidad.get(i).getStrcodigolocalidad());
     break;
     case 4:
     setNacion(listalocalidad.get(i).getStrcodigolocalidad());
     break;
     }
     i++;
     }
     }*/
    public String llenarHistoricoobra() {
        listahistorico = getSessionBeanCobra().getCobraService().encontrarHistoricosObra(getObra().getObra());

        return null;
    }
    String fechaserver = "";

    public String getFechaserver() {
        return fechaserver;
    }

    public void setFechaserver(String fechaserver) {
        this.fechaserver = fechaserver;
    }

    public String actualizarFecha() {
        fechaserver = new Date().toString();

        return fechaserver;
    }

    public void EncontrarSolicitante() {

        boolean solicitud;
        if (getObra().getSolicitud_obra() != null) {
            solicitud = true;
            soli = getSessionBeanCobra().getCobraService().encontrarTerceroSolicitante(getObra().getSolicitud_obra().getIntserial(), solicitud);
        }
        if (getObra().getSolicitudmaestro() != null) {
            solicitud = false;
            soli = getSessionBeanCobra().getCobraService().encontrarTerceroSolicitante((int) getObra().getSolicitudmaestro().getOidcodigosolicitudmaestro(), solicitud);
        }
    }

    public String vervideoevolucion() {

        Videoevolucionobra vervideo = (Videoevolucionobra) tablaverVideo.getRowData();
        setUrlvideo("/" + getSessionBeanCobra().getBundle().getString("versioncobra") + vervideo.getStrubicacion());
//        System.out.println("vervideo = " + getUrlvideo());
//        setVideoHtml("<video id='video1' width='320' height='240' controls='controls'><source src='"+getUrlvideo()+"' type='video/ogv' > </video>");
//        System.out.println("vervideo = " + getVideoHtml());

        /*try {
         FacesContext.getCurrentInstance().getExternalContext().redirect(getUrlvideo()+".ogv");
         } catch (IOException ex) {
         Logger.getLogger(AdministrarObraNew.class.getName()).log(Level.SEVERE, null, ex);
         }*/
        return null;
    }

    public void llenarSelectTipoImagenes() {

        this.listaTipoImagen = getSessionBeanCobra().getCobraService().encontrarTiposImagen();
        this.selectItemTipoImagen = new SelectItem[this.listaTipoImagen.size()];
        int i = 0;
        SelectItem selectItem = new SelectItem(0, bundle.getString("seleccioneuntipo"));//"Seleccione un tipo");
        for (Tipoimagen ti : this.listaTipoImagen) {
            selectItem = new SelectItem(ti.getIntidtipoimagen(), ti.getStrdesctipoimagen());
            this.selectItemTipoImagen[i++] = selectItem;
        }

    }

    public String buscarimg() {
        listaImagenesevolucionobra = getSessionBeanCobra().getCobraService().encontrarImagenesXObra(palabraclave, getObra().getIntcodigoobra());
        return null;
    }

    public String buscarDocs() {

        listaDocumentosobra = getSessionBeanCobra().getCobraService().encontrarDocsXObra(palabradoc, getObra().getIntcodigoobra());
        List<Documentoobra> lisdoc = new ArrayList<Documentoobra>();
        if (!listaContrato.isEmpty()) {
            for (Relacioncontratoobra rel : listaContrato) {

                Contrato contrato = rel.getContrato();

                lisdoc = getSessionBeanCobra().getCobraService().encontrarDocsXContrato(palabradoc, contrato.getIntidcontrato());

                if (!lisdoc.isEmpty()) {

                    listaDocumentosobra.addAll(lisdoc);
                }
            }
        }
        return null;
    }

    /**
     * Se encarga de registrar la solicitud de validación de información para un
     * proyecto
     *
     * @return null Navegacion jsf
     */
    public String solicitarValidacion() {
        getSessionBeanCobra().getCobraService().funcion_RevisionInformacion(getObra(), getSessionBeanCobra().getUsuarioObra());
        setObra(getSessionBeanCobra().getCobraService().encontrarObraPorId(getObra().getIntcodigoobra()));
        return null;
    }

    public String iniciarSolicitarValidacion() {

        if (getSessionBeanCobra().getCobraService().obtenerUltimaalimentacion(getObra().getIntcodigoobra()) != null) {

            if (cargarInformeavalidar()) {
                return "solicitarvalidacion";
            } else {
                FacesUtils.addErrorMessage("El proyecto ya fue aprobado en su último informe 100%");
                return null;
            }
            /*informeavalidar= getSessionBeanCobra().getCobraService().obtenerUltimaValidacionInformacionxObra(getObra().getIntcodigoobra());

             if(informeavalidar == null)
             {
             informeavalidar= getSessionBeanCobra().getCobraService().obtenerTipoinformexId(1);
             return "solicitarvalidacion";
             }
             else
             {

             if(informeavalidar.getIntidtipoinforme()==3)
             {
             FacesUtils.addErrorMessage("El proyecto ya fue aprobado en su último informe");
             return null;
             }
             else
             {

             informeavalidar= getSessionBeanCobra().getCobraService().obtenerTipoinformexId(informeavalidar.getIntidtipoinforme()+1);
             return "solicitarvalidacion";
             }

             }
             */
        } else {
            FacesUtils.addErrorMessage(bundle.getString("esteproyectonosehaalimentado"));
            return null;
        }
    }

    /**
     * iniciarTerminarObra Metodo que llama la página de DeclarTerminarObra
     *
     * @return
     */
    public String iniciarTerminarObra() {
        try {
            if (getObra().getTipoestadobra().getIntestadoobra() == 1) {
                if (!getObra().isBoolobraterminada()) {
                    return "terminarobra";
                } else {
                    FacesUtils.addErrorMessage(bundle.getString("mensaje2declararobra"));
                }
            } else {
                FacesUtils.addErrorMessage(bundle.getString("mensaje4declararobra"));
            }
        } catch (Exception e) {
            getSessionBeanCobra().getCobraService().getLog().info(bundle.getString("mensaje3declararobra") + " " + e.getMessage() + ". Fecha =  " + new Date() + ");");
        }
        return null;
    }

    /**
     * declararTerminarObra Metodo que cambia el estado de Boolobraterminada a
     * true en la base de datos
     *
     * @return
     */
    public String declararTerminarObra() {
        try {
            if (!getObra().isBoolobraterminada()) {
                getObra().setBoolobraterminada(true);
                getSessionBeanCobra().getCobraService().guardarObra(getObra(), getSessionBeanCobra().getUsuarioObra(), -1);
                FacesUtils.addInfoMessage(bundle.getString("mensaje1declararobra"));
                opcion = 0;
                getDetalleObra().iniciardetalle();
                return "admindetalleObra";
            } else {
                FacesUtils.addErrorMessage(bundle.getString("mensaje2declararobra"));
            }
        } catch (Exception e) {
            getSessionBeanCobra().getCobraService().getLog().info(bundle.getString("mensaje3declararobra") + " " + e.getMessage() + ". Fecha =  " + new Date() + ");");
        }
        return null;
    }

    /**
     * iniciarTerminarObra Metodo que llama la página de RegresarObraTerminada
     *
     * @return
     */
    public String iniciarRegresarObra() {
        try {
            if (getObra().getTipoestadobra().getIntestadoobra() == 1) {
                if (getObra().isBoolobraterminada()) {
                    return "regresarobraterminada";
                } else {
                    FacesUtils.addErrorMessage(bundle.getString("mensaje1regresarrobra"));
                }
            } else {
                FacesUtils.addErrorMessage(bundle.getString("mensaje4declararobra"));
            }
        } catch (Exception e) {
            getSessionBeanCobra().getCobraService().getLog().info(bundle.getString("mensaje2regresarrobra") + " " + e.getMessage() + ". Fecha =  " + new Date() + ");");
        }
        return null;
    }

    /**
     * declararRegresarTermindarObra Metodo que cambia el estado de
     * Boolobraterminada a false en la base de datos
     *
     * @return
     */
    public String declararRegresarTermindarObra() {
        try {
            if (getObra().isBoolobraterminada()) {
                getObra().setBoolobraterminada(false);
                getSessionBeanCobra().getCobraService().guardarObra(getObra(), getSessionBeanCobra().getUsuarioObra(), -1);
                FacesUtils.addInfoMessage(bundle.getString("mensaje3regresarrobra"));
                opcion = 0;
                getDetalleObra().iniciardetalle();
                return "admindetalleObra";
            } else {
                FacesUtils.addErrorMessage(bundle.getString("mensaje1regresarrobra"));
            }
        } catch (Exception e) {
            getSessionBeanCobra().getCobraService().getLog().info(bundle.getString("mensaje4regresarrobra") + " " + e.getMessage() + ". Fecha =  " + new Date() + ");");
        }
        return null;
    }

    public boolean cargarInformeavalidar() {

        informeavalidar = getSessionBeanCobra().getCobraService().obtenerUltimaValidacionInformacionxObra(getObra().getIntcodigoobra());

        if (informeavalidar == null) {
            informeavalidar = getSessionBeanCobra().getCobraService().obtenerTipoinformexId(1);
            return true;
        } else {

            if (informeavalidar.getIntidtipoinforme() == 3) {

                return false;
            } else {

                informeavalidar = getSessionBeanCobra().getCobraService().obtenerTipoinformexId(informeavalidar.getIntidtipoinforme() + 1);
                return true;
            }

        }

    }

    public String solicitarCambioEstado() {
        try {
            getSessionBeanCobra().getCobraService().funcion_CambiarEstado(getObra(), getSessionBeanCobra().getUsuarioObra());
            getIngresarNuevaObra().cargarObra(getSessionBeanCobra().getCobraService().encontrarObraPorId(getObra().getIntcodigoobra()));
            //return "datosbasicosnuevoproyecto";

        } catch (Exception ex) {
            Logger.getLogger(AdministrarObraNew.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return "datosbasicosnuevoproyecto";
    }

    public String iniciarCambioEstado() {
        try {
            if (!getObra().isBoolobraterminada()) {
                return "cambiarestado";
            } else {
                FacesUtils.addErrorMessage(bundle.getString("mensaje2declararobra"));
            }
        } catch (Exception e) {
            getSessionBeanCobra().getCobraService().getLog().info(bundle.getString("mensaje5declararobra") + " " + e.getMessage() + ". Fecha =  " + new Date() + ");");
        }
        return null;
    }

    public String iniciarEliminarUltimoAvance() {
        try {
            if (!getObra().isBoolobraterminada()) {
                if (getSessionBeanCobra().getCobraService().obtenerUltimaalimentacion(getObra().getIntcodigoobra()) != null) {

                    return "eliminarultimoavance";
                } else {
                    FacesUtils.addErrorMessage(bundle.getString("esteproyectonosehaalimentado"));
                    return null;
                }
            } else {
                FacesUtils.addErrorMessage(bundle.getString("mensaje2declararobra"));
            }
        } catch (Exception e) {
            getSessionBeanCobra().getCobraService().getLog().info(bundle.getString("mensaje5declararobra") + " " + e.getMessage() + ". Fecha =  " + new Date() + ");");
        }
        return null;
    }

    public String solicitarEliminarUltimoAvance() {
        getSessionBeanCobra().getCobraService().funcion_EliminarAlimentacion(getDetalleObra().getAlimentacionultima().getIntidalimenta(),
                getSessionBeanCobra().getUsuarioObra(), getObra().getIntcodigoobra());
        setObra(getSessionBeanCobra().getCobraService().encontrarObraPorId(getObra().getIntcodigoobra()));
        opcion = 0;
        getDetalleObra().iniciardetalle();

        return "admindetalleObra";
    }

    /**
     *
     * @return
     */
    public int getNumContratosObra() {
        if (listaContratoObra != null) {

            return listaContratoObra.size();
        } else {
            return 0;
        }
    }

    public int getNumContratosInterventoria() {
        if (listaContratoInterventoria != null) {

            return listaContratoInterventoria.size();
        } else {
            return 0;
        }
    }

    public Contrato getPrimerContratoObra() {

        if (!listaContratoObra.isEmpty()) {
            return listaContratoObra.get(0).getContrato();
        } else {
            return null;
        }
    }

    public Contrato getPrimerContratoInterventoria() {
        if (listaContratoInterventoria.isEmpty()) {
            return listaContratoInterventoria.get(0).getContrato();
        } else {
            return null;
        }
    }

    public String calcularPorcentajeEjecutado() {
        BigDecimal porcentajeEjecutado = getObra().getNumvalejecobra().divide(getObra().getNumvaltotobra(), 2, RoundingMode.HALF_UP).multiply(new BigDecimal("" + 100));
        if (porcentajeEjecutado.compareTo(new BigDecimal(100.0)) > 0) {
            porcentajeEjecutado = new BigDecimal(100.0);
        }
        return "" + porcentajeEjecutado + "%";
    }

    /**
     * Metodo para Modificar la ubicacion del
     * proyecto.(Longitud,Latitud,Direccion)
     *
     * @
     */
    public String actualizarUbicacion1() {
        String longitud = getObra().getFloatlongitud().toString();
        String latitud = getObra().getFloatlatitud().toString();
        String address;

        //Se verifica si la longitud y la latitud no estan vacios
        if (!longitud.equals("") || !latitud.equals("")) {
            //Se verifica que la longitud y la latitud sean numeros 
            if (isNumeric(latitud) && isNumeric(longitud)) {
                try {
                    //Se le envia a getPlaceMark la longitud y la latitud para sacar la direccion del proyecto  
                    PlaceMark placeMarkNew = GMaps4JSFServiceFactory.getReverseGeocoderService().getPlaceMark(latitud, longitud);
                    //se le asigna a  address la direccion obtenida
                    address = placeMarkNew.getAddress();
                    //se le asigna la la direccion obtenida en el address a getObra().setStrdireccion(address);
                    getObra().setStrdireccion(address);
                    //se le asigna el valor ingresado de longitud y la latitud a ubicacionmapa  para actualizar la imagen del mapa.
                    ubicacionmapa = "http://maps.google.com/maps/api/staticmap?center=" + getObra().getFloatlatitud() + ","
                            + getObra().getFloatlongitud() + "&zoom=10&size=450x200&&markers=color:blue|label:.|"
                            + getObra().getFloatlatitud() + "," + getObra().getFloatlongitud() + "&sensor=false";
                    //Se llama a el metodo guardarObra, que actualiza o guarda la informacion de la obra. 
                    getSessionBeanCobra().getCobraService().guardarObra(getObra(), null, opcion);
                    FacesUtils.addInfoMessage("Se actualizo con exito la Ubicación del proyecto");
                } catch (Exception e) {
                }
            } else {
                FacesUtils.addErrorMessage("El dato ingresado no es una coordenada validad se espera un numero eje 6.5895478755445");
            }
        } else {
            FacesUtils.addErrorMessage("Debe llenar los campos de latitud y longitud   ");
        }
        return "";

    }

    /**
     * *
     * Metodo para determinar si es una cadena de numeros y convertirlo a
     * Double.
     *
     * @param Cadena *
     */
    private static boolean isNumeric(String cadena) {
        try {
            Double.parseDouble(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;

        }
    }

    /**
     * obtiene el tipo de obra
     *
     * @return nombre del impacto social
     */
    public String getNombreImpactoSocial() {
        if (getObra().getTipoobra().getInttipoobra() == 59) {
            return nombreImpactoSocial = "Impacto Sociopolítico";
        }

        return nombreImpactoSocial = "Impacto social";
    }

    public void setNombreImpactoSocial(String nombreImpactoSocial) {
        this.nombreImpactoSocial = nombreImpactoSocial;
    }

    public Barrio getBarrio() {
        return barrio;
    }

    public void setBarrio(Barrio barrio) {
        this.barrio = barrio;
    }

    public Vereda getVereda() {
        return vereda;
    }

    public void setVereda(Vereda vereda) {
        this.vereda = vereda;
    }

    public void actualizarIndicador() {
        setVproductomedios(getSessionBeanCobra().getMarcoLogicoService().obtenerVistaProyectosMarcoxTipo(2, getObra().getIntcodigoobra()));
        setVproductogestion(getSessionBeanCobra().getMarcoLogicoService().obtenerVistaProyectosMarcoxTipo(3, getObra().getIntcodigoobra()));
    }

    public String llenarLocalidadesBarrioVereda() {

        getListaBarrios().addAll(getSessionBeanCobra().getCobraService().obtenerBarriosObra(getObra()));
        getListaVeredas().addAll(getSessionBeanCobra().getCobraService().obtenerVeredasObra(getObra()));
        return null;
    }

    public void modificarImpactoSocialProyecto() {
        try {
            if (getObra().getObra() == null) {
                getSessionBeanCobra().getCobraService().guardarObra(getObra(), null, opcion);
                FacesUtils.addInfoMessage("Se actualizo correctamente el  proyecto");

            } else {
                Obra obra = getSessionBeanCobra().getCobraService().encontrarObraPorId(getObra().getObra().getIntcodigoobra());
                obra.setNumempdirectos(obra.getNumempdirectos() + getObra().getNumempdirectos());
                obra.setNumempindirectos(obra.getNumempindirectos() + getObra().getNumempindirectos());
                obra.setNumhabbeneficiados(obra.getNumhabbeneficiados() + getObra().getNumhabbeneficiados());
                getSessionBeanCobra().getCobraService().guardarObra(getObra(), null, opcion);
                getSessionBeanCobra().getCobraService().guardarObra(obra, null, opcion);
                FacesUtils.addInfoMessage("Se actualizo correctamente el  proyecto");
            }
        } catch (Exception e) {
            FacesUtils.addErrorMessage("No se pudo actualizar el Proyecto");
        }
        habilitarModificarimpacto = true;
        habilitarGuardarimpacto = false;
    }

    /**
     * Metodo Utilizado para habilitar el boton Modificar El Impacto social del
     * proyecto
     *
     * @return void
     */
    public void habilitarBotonModificarImpacto() {
        habilitarModificarimpacto = false;
        habilitarGuardarimpacto = true;
    }

    /**
     * Metodo Utilizado para cancelar La modificacion del impacto social del
     * proyecto
     *
     * @return void
     */
    public void cancelarModifcacionModificarImpacto() {
        habilitarModificarimpacto = true;
        habilitarGuardarimpacto = false;
    }

    /**
     * Reporte historial de validaciones
     *
     */
    public String reporteHistorialValidaciones() {
        try {
            getSessionBeanCobra().setUrlAbri(Propiedad.getValor("ipserver") + bundle.getString("reportehistorialvalidaciones") + getObra().getIntcodigoobra());
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/Reportes");
        } catch (IOException ex) {
            Logger.getLogger(DetalleObra.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
    private GraficosGiprom gragiprom;

    public GraficosGiprom getGragiprom() {
        return gragiprom;
    }

    public void setGragiprom(GraficosGiprom gragiprom) {
        this.gragiprom = gragiprom;
    }

    /**
     * <p>
     * Return a reference to the scoped data bean.</p>
     *
     * @return reference to the scoped data bean
     */
    protected NuevoContratoBasico getNuevoContratoBasico() {
        return (NuevoContratoBasico) FacesUtils.getManagedBean("Supervisor$Contrato");
    }

    public String detalleContrato() {
//        if(contratoId!=0){
//            mostrarContratoConvenio = contratoId;
//        }    
        Contrato contratotabla = (Contrato) getSessionBeanCobra().getCobraService().encontrarContratoxId(mostrarContratoConvenio);
        getNuevoContratoBasico().cargarContrato(contratotabla);
        return "consultarContrato";
    }

    /**
     * informecircularTolima Envia un correo del contratista a la contraloria
     * del tolima
     *
     * @return
     */
    public String informecircularTolima() {
        try {
            if ((fechaInicioCircular != null) || (fechaFinCircular != null)) {
                if (fechaInicioCircular.before(fechaFinCircular)) {
                    if (getNuevoContratoBasico().comboEntidadesContratoguardar()) {
                        Tercero entidad = getSessionBeanCobra().getCobraService().encontrarTerceroPorId(getNuevoContratoBasico().getContrato().getTercero().getIntcodigo());
                        boolean respuesta = getSessionBeanCobra().getCobraService().enviarcorreoinformecircular(getSessionBeanCobra().getCobraService(), getSessionBeanCobra().getUsuarioObra(), entidad, fechaInicioCircular, fechaFinCircular);
                        if (respuesta) {
                            FacesUtils.addInfoMessage(bundle.getString("mensaje2informecircular"));
                        } else {
                            FacesUtils.addErrorMessage(bundle.getString("mensaje3informecircular"));
                        }
                    } else {
                        FacesUtils.addErrorMessage(bundle.getString("mensaje4informecircular"));
                    }
                } else {
                    FacesUtils.addErrorMessage(bundle.getString("mensaje5informecircular"));
                }
            } else {
                FacesUtils.addErrorMessage(bundle.getString("mensaje6informecircular"));
            }
        } catch (Exception e) {
            getSessionBeanCobra().getCobraService().getLog().info(bundle.getString("mensaje1informecircular") + " " + e.getMessage() + ". Fecha =  " + new Date() + ");");
        }
        return null;
    }

    /*
     Adicionar enlace de video a la obra
     */
    private Videoevolucionobra videoEnlace;
    private List<Videoevolucionobra> lstVideosEnlazados;

    public List<Videoevolucionobra> getLstVideosEnlazados() {
        return lstVideosEnlazados;
    }

    public void setLstVideosEnlazados(List<Videoevolucionobra> lstVideosEnlazados) {
        this.lstVideosEnlazados = lstVideosEnlazados;
    }

    public Videoevolucionobra getVideoEnlace() {
        return videoEnlace;
    }

    public void setVideoEnlace(Videoevolucionobra videoEnlace) {
        this.videoEnlace = videoEnlace;
    }

    public void guardarEnlaceVideo() {
        if (Utilidades.isUrl(videoEnlace.getStrurlvideo())) {
            if (!videoEnlace.getStrurlvideo().contains("http")) {
                videoEnlace.setStrurlvideo("http://" + videoEnlace.getStrurlvideo());
            }

            videoEnlace.setBoolubicacionlocal(false);
            videoEnlace.setDatefecha(new Date());

            getSessionBeanCobra().getCobraService().guardarVideoObra(videoEnlace);
            lstVideosEnlazados.add(videoEnlace);
            limpiarVideo();

            FacesUtils.addInfoMessage("El enlace de video se ha almacenado con éxito.");
        } else {
            FacesUtils.addErrorMessage("Debe ingresar un enlace de video válido.");
        }

    }

    public void limpiarVideo() {
        videoEnlace = new Videoevolucionobra();
        videoEnlace.setObra(getObra());
    }

    public String irContratoPadre() {
        getNuevoContratoBasico().cargarContrato(getObra().getContrato());
        return "consultarContrato";
    }

    //
    // 
    // INICIO LOGICA - SOLICITUD VALIDACION DE AVANCE  - SVA
    //
    //
    private int periodoSolicitudValidacionAvance;
    private String observacionSolicitudValidacionAvance;
    private List<Periodo> periodosalimentacionSolicitudValidacionAvance;
    private List<SolicitudValidacionAvance> listaSVA;
    private int codigoErrorSVA;
    private String errorMessageSVA;

    public int getPeriodoSolicitudValidacionAvance() {
        return periodoSolicitudValidacionAvance;
    }

    public void setPeriodoSolicitudValidacionAvance(int periodoSolicitudValidacionAvance) {
        this.periodoSolicitudValidacionAvance = periodoSolicitudValidacionAvance;
    }

    public String getObservacionSolicitudValidacionAvance() {
        return observacionSolicitudValidacionAvance;
    }

    public void setObservacionSolicitudValidacionAvance(String observacionSolicitudValidacionAvance) {
        this.observacionSolicitudValidacionAvance = observacionSolicitudValidacionAvance;
    }

    public List<SolicitudValidacionAvance> getListaSVA() {
        return this.listaSVA;
    }

    public void setListaSVA(List<SolicitudValidacionAvance> listaSVA) {
        this.listaSVA = listaSVA;
    }

    public List<Periodo> getPeriodosalimentacionSolicitudValidacionAvance() {
        return periodosalimentacionSolicitudValidacionAvance;
    }

    public void setPeriodosalimentacionSolicitudValidacionAvance(List<Periodo> items) {
        this.periodosalimentacionSolicitudValidacionAvance = items;
    }

    public int getCodigoErrorSVA() {
        return codigoErrorSVA;
    }

    public String getErrorMessageSVA() {
        return errorMessageSVA;
    }

    public boolean getRenderSVA() {
        if(this.getObra() == null || this.getObra().getInterventor() == null || getSessionBeanCobra().getUsuarioObra() == null || getSessionBeanCobra().getUsuarioObra().getTercero() == null ){
            return false;
        }
        return this.getObra().getInterventor().getIntcodigo() == getSessionBeanCobra().getUsuarioObra().getTercero().getIntcodigo();
    }

    public String initSolicitudValidacionAvance() {
        if (!getRenderSVA()) {
            return null;
        }
        this.codigoErrorSVA = 0;
        this.errorMessageSVA = null;
        this.setPeriodoSolicitudValidacionAvance(0);
        this.setObservacionSolicitudValidacionAvance(null);
        this.cargarHistorialSVA();
        List<Periodo> temp = getSessionBeanCobra().getCobraService().encontrarPeriodosxAlimentacionesObra(this.getObra().getIntcodigoobra());
        this.setPeriodosalimentacionSolicitudValidacionAvance(temp);
        return "solicitud_validacion_avance";
    }
    
    public void cargarHistorialSVA(){
        this.setListaSVA(getSessionBeanCobra().getCobraService().getSolicitudesValidacionAvance(this.getObra()));
    }

    public void registrarSolicitudValidacionAvance() {
        if (!getRenderSVA()) {
            return;
        }
        Periodo periodo = new Periodo();
        periodo.setIntidperiodo(this.getPeriodoSolicitudValidacionAvance());
        List<Alimentacion> temp = null;
        if (this.getPeriodoSolicitudValidacionAvance() > 0) {
            temp = this.getSessionBeanCobra().getCobraService().encontrarAlimentacionesxPeriodo(periodo.getIntidperiodo(), this.getObra().getIntcodigoobra());
        }
        Alimentacion avance = null;
        if (temp != null && !temp.isEmpty()) {
            avance = temp.get(0);
        }

        SolicitudValidacionAvance sva = new SolicitudValidacionAvance();
        sva.setObra(this.getObra());
        sva.setAlimentacion(avance);
        sva.setPeriodo(periodo);
        sva.setFechaSolicitud(new Date());
        sva.setEstado(SolicitudValidacionAvance.ESTADO_PENDIENTE);
        sva.setJsfUsuarioByUsuarioInterventor(this.getSessionBeanCobra().getUsuarioObra());
        sva.setObservaciones(this.getObservacionSolicitudValidacionAvance());

        String messageSVA;
        int errorCodeSVA = validateSolicitudValidacionAvance(sva);
        switch (errorCodeSVA) {
            case 0:
                this.getSessionBeanCobra().getCobraService().obtenerCobraDao().guardar(sva);
                this.sendNotificationSVA(sva, this.getObra());
                this.initSolicitudValidacionAvance();
                messageSVA = "La solicitud se registró exitosamente";
                break;
            case 1:
                messageSVA = "Debe seleccionar un periodo";
                break;
            case 2:
                messageSVA = "El campo observación no pude ser vacio";
                break;
            case 3:
                messageSVA = "La solicitud se registró exitosamente";
                break;
            case 4:
                messageSVA = "Existe una solicitud  \"APROBADA\" para el periodo seleccionado";
                break;
            case 5:
                messageSVA = "Existe una solicitud \"PENDIENTE\"  para el periodo seleccionado";
                break;
            case 6:
                messageSVA = "Existen periodos previos sin aprobar";
                break;
            default:
                messageSVA = "Error de validación";
                break;
        }
        this.codigoErrorSVA = errorCodeSVA;
        this.errorMessageSVA = messageSVA;

    }
    
    private void sendNotificationSVA(SolicitudValidacionAvance sva, Obra obra){
        if(obra == null || obra.getSupervisor() == null || obra.getSupervisor().getStremail() == null){
            Logger.getLogger(AdministrarObraNew.class.getName()).info("ERROR: No se puede enviar correo. Supervisor no está configurado correctamente");
            return;
        }
        String [] recipients = {obra.getSupervisor().getStremail()};
        String subject = "Solicitud de validación para un Avance";
        StringBuilder body = new StringBuilder();
        body.append("<div><b>Obra:&nbsp;</b>").append(sva.getObra().getStrnombreobra()).append("</div>");
        for (Periodo periodo : periodosalimentacionSolicitudValidacionAvance) {
            if(periodo.getIntidperiodo() == sva.getPeriodo().getIntidperiodo()){
                body.append("<div><b>Periodo:&nbsp;</b>").append(periodo.getDatefeciniperiodo()).append(" - ").append(periodo.getDatefecfinperiodo()).append("</div>");
                break;
            }
        }
        body.append("<div><b>Observaciones:&nbsp;</b>").append(sva.getObservaciones());
        MailFonade mailFonade = new MailFonade();
        mailFonade.sendMail(recipients, subject, body.toString());
    }

    /**
     * Este método valida que los campos de la solicitud no sean nulos y que no
     * exista una solicitud para el mismo periodo en estado aprobado o
     * pendiente. Tambien valida que las solicitudes se hagan en orden, es decir
     * que al hacer una solicitud no se salten los periodos
     *
     * @param sva
     * @return
     */
    private int validateSolicitudValidacionAvance(SolicitudValidacionAvance sva) {

        if (sva.getPeriodo().getIntidperiodo() == 0) {
            return 1;
        } else if (sva.getObservaciones() == null || sva.getObservaciones().isEmpty()) {
            return 2;
        } else if (sva.getAlimentacion() == null || sva.getObra() == null || sva.getJsfUsuarioByUsuarioInterventor() == null) {
            return 3;
        }
        List<SolicitudValidacionAvance> list = this.getListaSVA();
        List<Periodo> periodos = this.getPeriodosalimentacionSolicitudValidacionAvance();
        int j = 0;
        for (SolicitudValidacionAvance s : list) {
            if (s.getPeriodo().getIntidperiodo() == sva.getPeriodo().getIntidperiodo() && s.getEstado() == SolicitudValidacionAvance.ESTADO_APROBADO) {
                return 4;
            } else if (s.getPeriodo().getIntidperiodo() == sva.getPeriodo().getIntidperiodo() && s.getEstado() == SolicitudValidacionAvance.ESTADO_PENDIENTE) {
                return 5;
            }

            //Se analiza que para cada periodo exista una solicitud aprobada (Tener en cuenta que se asume que las listas estan ordenadas)
            if (s.getPeriodo().getIntidperiodo() == periodos.get(j).getIntidperiodo() && s.getEstado() == SolicitudValidacionAvance.ESTADO_APROBADO) {
                j++;
            }
        }

        //La nueva solicitud debe ser para el periodo inmediatamente siguiente
        if (j < periodos.size() && periodos.get(j).getIntidperiodo() != sva.getPeriodo().getIntidperiodo()) {
            return 6;
        }

        return 0;
    }

    //
    // 
    // FIN LOGICA - SOLICITUD VALIDACION DE AVANCE
    //
    //
}
