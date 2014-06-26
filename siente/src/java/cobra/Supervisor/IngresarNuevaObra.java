/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.Supervisor;

import co.com.interkont.cobra.marcologico.to.Avanceplanificacionrelacionmarcologicoindicador;
import co.com.interkont.cobra.marcologico.to.Relacionmarcologicoindicador;
import co.com.interkont.cobra.to.Actividadobra;
import co.com.interkont.cobra.to.Aseguradora;
import co.com.interkont.cobra.to.Barrio;
import co.com.interkont.cobra.to.Bdpu;
import co.com.interkont.cobra.to.Claseobra;
import co.com.interkont.cobra.to.Comuna;
import co.com.interkont.cobra.to.Contratista;
import co.com.interkont.cobra.to.Contrato;
import co.com.interkont.cobra.to.Corregimiento;
import co.com.interkont.cobra.to.Documentoobra;
import co.com.interkont.cobra.to.Evento;
import co.com.interkont.cobra.to.Fase;
import co.com.interkont.cobra.to.Formapago;
import co.com.interkont.cobra.to.Imagenevolucionobra;
import co.com.interkont.cobra.to.Indicador;
import co.com.interkont.cobra.to.Indicadorobra;
import co.com.interkont.cobra.to.Localidad;
import co.com.interkont.cobra.to.Lugarobra;
import co.com.interkont.cobra.to.Novedad;
import co.com.interkont.cobra.to.Objetoindicador;
import co.com.interkont.cobra.to.Obra;
import co.com.interkont.cobra.to.Periodo;
import co.com.interkont.cobra.to.Periodoevento;
import co.com.interkont.cobra.to.Periodomedida;
import co.com.interkont.cobra.to.Polizacontrato;
import co.com.interkont.cobra.to.Region;
import co.com.interkont.cobra.to.Relacionactividadobraperiodo;
import co.com.interkont.cobra.to.Tercero;
import co.com.interkont.cobra.to.Tipocosto;
import co.com.interkont.cobra.to.Tipoestadobra;
import co.com.interkont.cobra.to.Tiponovedad;
import co.com.interkont.cobra.to.Tipoobra;
import co.com.interkont.cobra.to.Tipoorigen;
import co.com.interkont.cobra.to.Tipopoliza;
import co.com.interkont.cobra.to.Tipotercero;
import co.com.interkont.cobra.to.Puntoobra;
import co.com.interkont.cobra.to.Puntoreferencia;
import co.com.interkont.cobra.to.Relacioncontratoobra;
import co.com.interkont.cobra.to.Ruta;
import co.com.interkont.cobra.to.Sedeeducativa;
import co.com.interkont.cobra.to.Tipoimagen;
import co.com.interkont.cobra.to.Tipoimpactosocial;
import co.com.interkont.cobra.to.Tipolocalidad;
import co.com.interkont.cobra.to.Tipoproyecto;
import co.com.interkont.cobra.to.ValidacionNuevoProyecto;
import co.com.interkont.cobra.to.Vereda;
import co.com.interkont.cobra.to.utilidades.Propiedad;
import com.googlecode.gmaps4jsf.services.data.PlaceMark;
import com.interkont.cobra.exception.ArchivoExistenteException;
import java.io.FileOutputStream;
import javax.faces.model.SelectItem;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import cobra.CargadorArchivosWeb;
import cobra.Cobra.Download;
import cobra.FiltroAvanzadoContrato;
import cobra.FiltroAvanzadoObra;
import cobra.Marcador;
import cobra.SessionBeanCobra;
import cobra.gestion.HomeGestion;
import cobra.util.ArchivoWebUtil;
import cobra.util.RutasWebArchivos;
import com.googlecode.gmaps4jsf.component.common.Position;
import com.googlecode.gmaps4jsf.services.GMaps4JSFServiceFactory;
import com.googlecode.gmaps4jsf.services.ReverseGeocoderServiceImpl.*;
import com.interkont.cobra.exception.ArchivoNoExistenteException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.faces.event.ValueChangeEvent;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.util.CellRangeAddressList;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.hssf.util.HSSFColor;
import org.richfaces.component.UIDataTable;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import com.googlecode.mashups.services.factory.GenericServicesFactory;
import com.googlecode.mashups.services.generic.api.Location;
import java.util.AbstractList;
import java.util.Collections;
import java.util.Map;

/**
 * <p>
 * Page bean that corresponds to a similarly named JSP page. This class contains
 * component definitions (and initialization code) for all components that you
 * have defined on this page, as well as lifecycle methods and event handlers
 * where you may add behavior to respond to incoming events.</p>
 *
 * @version IngresarNuevaObra.java
 * @version Created on 16/11/2008, 04:41:01 PM
 * @author Carlos Alberto Loaiza Guerrerro
 * @author David Andres Betancourth Botero
 */
public class IngresarNuevaObra implements ILifeCycleAware, Serializable {

    /**
     * Variables BigDecimal.
     */
    // <editor-fold defaultstate="collapsed" desc="Managed Component Definition">
    BigDecimal valortotalobra = BigDecimal.ZERO;
    // </editor-fold>
    /**
     * Variables Boolean.
     */
    private int datosValidos;
    // <editor-fold defaultstate="collapsed" desc="Managed Component Definition">
    private boolean decision;
    private boolean verContrato = false;
    private boolean verPrimario = true;
    private boolean vertipocosto = false;
    private boolean verNuevo = false;
    private boolean verConfirmar = false;
    private boolean verObrasPadres = false;
    private boolean verObrasHijo = false;
    private boolean editarContrato = true;
    private boolean datosbas = false;
    private boolean aplicafiltro = false;
    private boolean verultimosreales;
    private boolean veranteriorreales;
    private boolean verultimoscontrato;
    private boolean veranteriorcontrato;
    private boolean booltipocontratoconvenio;
    private boolean band = false;
    private boolean objeto = false;
    private boolean desdeconvenio = false;
    private boolean formatoxlsx = false;
    private boolean fechasvalidas = false;
    // </editor-fold>
    /**
     * Variables String.
     */
    // <editor-fold defaultstate="collapsed" desc="Managed Component Definition">
    private String codMunicipio;
    private String codDepartamento;
    private String codDepartamentoContratista;
    private String nomcontraro;
    private String mensaje = "";
    private String zoom = getSessionBeanCobra().getBundle().getString("zoommapa");
    private String address = "Bogotá";
    private String latNewmanu;
    private String longNewmanu;
    private String valorFiltroContratos = "";
    private String nombreobra;
    private String realArchivoPath;
    private String nomImagen = "";
    private String pathImagen = "";
    private String pathImagenPpal = "";
    private String valorFiltroImagenevolucionobra = "";
    private String id = "";
    private String jsVariable = "";
    private String jsZoon = "";
    private static final String URL = "/resources/Documentos/ObrasenProceso/";
    // </editor-fold>
    /**
     * Variables Listas y Sets.
     */
    // <editor-fold defaultstate="collapsed" desc="Managed Component Definition">
    private List<Marcador> listamarcadores = new ArrayList<Marcador>();
    private List<Relacioncontratoobra> listacontratosobra = new ArrayList<Relacioncontratoobra>();
    private List<Contrato> listacontratos = new ArrayList<Contrato>();
    private List<Marcador> marli = new ArrayList<Marcador>();
    private List<Region> queryRegiones;
    private List<Localidad> queryMunicipios;
    private List<Localidad> queryDeptos;
    private List<Localidad> listaLocalidades = new ArrayList<Localidad>();
    private List<Region> listaRegiones = new ArrayList<Region>();
    private List<Tercero> ternew;
    private List<Tercero> listerentidad = new ArrayList<Tercero>();
    private List<Imagenevolucionobra> listaImagenesevolucionobra = new ArrayList<Imagenevolucionobra>();
    private List<TerceroEntidadLista> temp = new ArrayList<TerceroEntidadLista>();
    private List<Obra> listaProyectosPadre = new ArrayList<Obra>();
    private Set<Actividadobra> actividadesobra = new LinkedHashSet<Actividadobra>();
    private Barrio barrio = new Barrio();
    private Vereda vereda = new Vereda();
    private List<Barrio> listaBarrios = new ArrayList<Barrio>();
    private List<Vereda> listaVeredas = new ArrayList<Vereda>();
    private List<Barrio> listadobarrio;
    private List<Vereda> listadovereda;
    // </editor-fold>
    /**
     * Objetos UIDataTable.
     */
    // <editor-fold defaultstate="collapsed" desc="Managed Component Definition">
    private UIDataTable tablaMarkereli = new UIDataTable();
    private UIDataTable tablacontratos = new UIDataTable();
    private UIDataTable tablaregiones = new UIDataTable();
    private UIDataTable tablatipoproyecto = new UIDataTable();
    private UIDataTable tablatiposolicitudobra = new UIDataTable();
    private UIDataTable tablaProyectosHijos = new UIDataTable();
    private UIDataTable tablalocalidades = new UIDataTable();
    private UIDataTable tableLocalidadVereda = new UIDataTable();
    private UIDataTable tableLocalidadbarrio = new UIDataTable();
    private UIDataTable tablatipoobra = new UIDataTable();
    private UIDataTable tablaImagenesevolucion = new UIDataTable();
    private UIDataTable tablalistacontratos = new UIDataTable();
    private UIDataTable tablaObrasPadres = new UIDataTable();
    private UIDataTable tablainterventores = new UIDataTable();
    private UIDataTable tablasupervisores = new UIDataTable();
    // </editor-fold>
    /**
     * Variables Int.
     */
    // <editor-fold defaultstate="collapsed" desc="Managed Component Definition">
    private int formaseleccionada = 0;
    private int tiposelec = 0;
    private int valido = 0;
    private int estado = 0;
    private int idConvenio;
    private int documentoseleccionado = 0;
    private int tiproyectoselec = 0;
    private int tiahselect = 0;
    private int idcomuna = 1;
    private int idcorregimiento = 1;
    private int idevento = 1;
    private int preguntaSubProyecto = 0;
    private int preguntaProyectoPadre = 0;
    private int mostrarpgconveniocontrato = 0;
    private int mostrarbtguardado = 0;
    private int totalfilas = 0;
    private int pagina = 0;
    private int totalpaginas = 0;
    private int codRegion;
    private int verEliminar = 0;
    private int subtiposelec = 0;
    private int disableCronograma = 0;//no muestra cronograma por q es padre
    private int disableAiu = 0;//muestra aiu cuando es hijo y
    private int listadiligenciada = 0;
    private int estadoguardado = 0;
    private int navegacion = 0;
    // </editor-fold>
    /**
     * Variables SelectItem[].
     */
    // <editor-fold defaultstate="collapsed" desc="Managed Component Definition">
    private SelectItem[] TipoCostoOption;
    private SelectItem[] FormaPago;
    private SelectItem[] PeriodoMedida;
    private SelectItem[] yesOrNo;
    private SelectItem[] LugarObra;
    private SelectItem[] ClaseObra;
    private SelectItem[] TipoOrigenes;
    private SelectItem[] Region;
    private SelectItem[] Departamento;
    private SelectItem[] Municipio;
    private SelectItem[] GeneroOption;
    private SelectItem[] EstadoCivilOption;
    private SelectItem[] TerceroOption;
    private SelectItem[] TipoTerceroOPtion;
    private SelectItem[] comunas;
    private SelectItem[] corregimientos;
    private SelectItem[] barrios;
    private SelectItem[] veredas;
    private SelectItem[] eventoItem;
    private SelectItem[] periodoEventoItem;
    private Periodo[] listadoperiodos = new Periodo[100];
    // </editor-fold>
    /**
     * Objetos de SubirArchivoBean.
     */
    // <editor-fold defaultstate="collapsed" desc="Managed Component Definition">
    /**
     * Objeto utilizado para subir el archivo del cronograma
     */
    private CargadorArchivosWeb cargadorCronograma = new CargadorArchivosWeb();
    /**
     * Objeto utilizado para subir la imágen principal del proyecto
     */
    private CargadorArchivosWeb cargadorImagenPrincipal = new CargadorArchivosWeb();
    /**
     * Objeto utilizado para subir la imágen anterior del proyecto
     */
    private CargadorArchivosWeb cargadorImagenAnterior = new CargadorArchivosWeb();
    // </editor-fold>
    /**
     * Objetos data transfer object
     */
    // <editor-fold defaultstate="collapsed" desc="Managed Component Definition">
    private Obra obranueva = new Obra();
    private Obra obrapadre = new Obra();
    private Documentoobra documentoobra = new Documentoobra();
    private Documentoobra cdp = new Documentoobra();
    private Polizacontrato polizacontrato = new Polizacontrato();
    private Imagenevolucionobra imagenevolucionobraEd = new Imagenevolucionobra();
    private Imagenevolucionobra imagenevolucionobraEl = new Imagenevolucionobra();
    private Imagenevolucionobra imagenevolucionobra = new Imagenevolucionobra();
    private Relacioncontratoobra relacioncontrato = new Relacioncontratoobra();
    private Fase faseelegida = new Fase();
    private BigDecimal sumValorContrato;
    private FiltroAvanzadoObra filtro = new FiltroAvanzadoObra();
    private FiltroAvanzadoContrato filtrocontrato = new FiltroAvanzadoContrato();
    private Tercero solicitante = new Tercero();
    private ResourceBundle bundle = getSessionBeanCobra().getBundle();
    private String latitudmapa = getSessionBeanCobra().getBundle().getString("latitudmapa");
    private String longitudmapa = getSessionBeanCobra().getBundle().getString("longitudmapa");
    private boolean redibujarmapa = false;
    private boolean tipoOrigenSoloLectura;
    private  BigDecimal valorfaltanteasociarcontrato = BigDecimal.ZERO;
    private String strempleosdirectos = bundle.getString("numempleosdirectos");
    private String strempleosindirectos = bundle.getString("numempleosindirectos");
    private String strbeneficiarios = bundle.getString("numbeneficiarios");
    private boolean primeraPagina;
    private boolean anteriorPagina;
    private boolean siguientePagina;
    private boolean ultimoPagina;
    List<Tercero> listaTotalTerceros;
    private boolean isSupervisor; //true es supervisor, false es interventor
    
    
    public boolean isTipoOrigenSoloLectura() {
        return tipoOrigenSoloLectura;
    }

    public void setTipoOrigenSoloLectura(boolean tipoOrigenSoloLectura) {
        this.tipoOrigenSoloLectura = tipoOrigenSoloLectura;
    }

    public boolean isRedibujarmapa() {
        return redibujarmapa;
    }

    public void setRedibujarmapa(boolean redibujarmapa) {
        this.redibujarmapa = redibujarmapa;
    }

    public String getLatitudmapa() {
        return latitudmapa;
    }

    public void setLatitudmapa(String latitudmapa) {
        this.latitudmapa = latitudmapa;
    }

    public String getLongitudmapa() {
        return longitudmapa;
    }

    public void setLongitudmapa(String longitudmapa) {
        this.longitudmapa = longitudmapa;
    }
    // </editor-fold>
    
    private String nombre = "";
    private boolean aplicaFiltroInterventor;
    private List<Tercero> listaInterventores;
    private String nombreSupervisor = "";
    private boolean aplicaFiltroSupervisor;
    private List<Tercero> listaSupervisores;
    /**
     * Medios de vida
     */
    private boolean proyectoestrategia = false;

    public boolean isProyectoestrategia() {
        return proyectoestrategia;
    }

    public void setProyectoestrategia(boolean proyectoestrategia) {
        this.proyectoestrategia = proyectoestrategia;
    }

    /**
     * Encapsulamiento
     */
    // <editor-fold defaultstate="collapsed" desc="Managed Component Definition">
    public CargadorArchivosWeb getCargadorCronograma() {
        return cargadorCronograma;
    }

    public void setCargadorCronograma(CargadorArchivosWeb cargadorCronograma) {
        this.cargadorCronograma = cargadorCronograma;
    }

    public BigDecimal getValortotalobra() {
        return valortotalobra;
    }

    public void setValortotalobra(BigDecimal valortotalobra) {
        this.valortotalobra = valortotalobra;
    }

    public boolean isVertipocosto() {
        return vertipocosto;
    }

    public void setVertipocosto(boolean vertipocosto) {
        this.vertipocosto = vertipocosto;
    }

    public boolean isVerPrimario() {
        return verPrimario;
    }

    public void setVerPrimario(boolean verPrimario) {
        this.verPrimario = verPrimario;
    }

    public List<Marcador> getListamarcadores() {
        return listamarcadores;
    }

    public void setListamarcadores(List<Marcador> listamarcadores) {
        this.listamarcadores = listamarcadores;
    }

    public boolean isVerConfirmar() {
        return verConfirmar;
    }

    public void setVerConfirmar(boolean verConfirmar) {
        this.verConfirmar = verConfirmar;
    }

    public int getFormaseleccionada() {
        return formaseleccionada;
    }

    public void setFormaseleccionada(int formaseleccionada) {
        this.formaseleccionada = formaseleccionada;
    }

    public boolean isVerNuevo() {
        return verNuevo;
    }

    public void setVerNuevo(boolean verNuevo) {
        this.verNuevo = verNuevo;
    }

    public int getTiposelec() {
        return tiposelec;
    }

    public void setTiposelec(int tiposelec) {
        this.tiposelec = tiposelec;
    }

    public int getNavegacion() {
        return navegacion;
    }

    public void setNavegacion(int navegacion) {
        this.navegacion = navegacion;
    }

    public UIDataTable getTablalistacontratos() {
        return tablalistacontratos;
    }

    public void setTablalistacontratos(UIDataTable tablalistacontratos) {
        this.tablalistacontratos = tablalistacontratos;
    }

    public Relacioncontratoobra getRelacioncontrato() {
        return relacioncontrato;
    }

    public void setRelacioncontrato(Relacioncontratoobra relacioncontrato) {
        this.relacioncontrato = relacioncontrato;
    }

    public boolean isDesdeconvenio() {
        return desdeconvenio;
    }

    public void setDesdeconvenio(boolean desdeconvenio) {
        this.desdeconvenio = desdeconvenio;
    }

    public boolean isFormatoxlsx() {
        return formatoxlsx;
    }

    public void setFormatoxlsx(boolean formatoxlsx) {
        this.formatoxlsx = formatoxlsx;
    }

    public FiltroAvanzadoContrato getFiltrocontrato() {
        return filtrocontrato;
    }

    public void setFiltrocontrato(FiltroAvanzadoContrato filtrocontrato) {
        this.filtrocontrato = filtrocontrato;
    }

    public boolean isObjeto() {
        return objeto;
    }

    public void setObjeto(boolean objeto) {
        this.objeto = objeto;
    }

    public Tercero getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(Tercero solicitante) {
        this.solicitante = solicitante;
    }
    private ValidacionNuevoProyecto validacion = new ValidacionNuevoProyecto();

    public ValidacionNuevoProyecto getValidacion() {
        return validacion;
    }

    public void setValidacion(ValidacionNuevoProyecto validacion) {
        this.validacion = validacion;
    }

    public boolean isBand() {
        return band;
    }

    public void setBand(boolean band) {
        this.band = band;
    }

    public String getPathImagenPpal() {
        return pathImagenPpal;
    }

    public void setPathImagenPpal(String pathImagenPpal) {
        this.pathImagenPpal = pathImagenPpal;
    }

    public FiltroAvanzadoObra getFiltro() {
        return filtro;
    }

    public void setFiltro(FiltroAvanzadoObra filtro) {
        this.filtro = filtro;
    }

    public String getJsZoon() {
        return jsZoon;
    }

    public void setJsZoon(String jsZoon) {
        this.jsZoon = jsZoon;
    }

    public String getJsVariable() {
        return jsVariable;
    }

    public void setJsVariable(String jsVariable) {
        this.jsVariable = jsVariable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isBooltipocontratoconvenio() {
        return booltipocontratoconvenio;
    }

    public void setBooltipocontratoconvenio(boolean booltipocontratoconvenio) {
        this.booltipocontratoconvenio = booltipocontratoconvenio;
    }

    public String getNomcontraro() {
        return nomcontraro;
    }

    public void setNomcontraro(String nomcontraro) {
        this.nomcontraro = nomcontraro;
    }

    public List<Contrato> getListacontratos() {
        return listacontratos;
    }

    public void setListacontratos(List<Contrato> listacontratos) {
        this.listacontratos = listacontratos;
    }

    public boolean isVeranteriorcontrato() {
        return veranteriorcontrato;
    }

    public void setVeranteriorcontrato(boolean veranteriorcontrato) {
        this.veranteriorcontrato = veranteriorcontrato;
    }

    public boolean isVerultimoscontrato() {
        return verultimoscontrato;
    }

    public void setVerultimoscontrato(boolean verultimoscontrato) {
        this.verultimoscontrato = verultimoscontrato;
    }

    public BigDecimal getSumValorContrato() {
        return sumValorContrato;
    }

    public void setSumValorContrato(BigDecimal sumValorContrato) {
        this.sumValorContrato = sumValorContrato;
    }

    public Fase getFaseelegida() {
        return faseelegida;
    }

    public void setFaseelegida(Fase faseelegida) {
        this.faseelegida = faseelegida;
    }

    public String getNombreobra() {
        return nombreobra;
    }

    public void setNombreobra(String nombreobra) {
        this.nombreobra = nombreobra;
    }

    public boolean isAplicafiltro() {
        return aplicafiltro;
    }

    public void setAplicafiltro(boolean aplicafiltro) {
        this.aplicafiltro = aplicafiltro;
    }

    public boolean isVeranteriorreales() {
        return veranteriorreales;
    }

    public void setVeranteriorreales(boolean veranteriorreales) {
        this.veranteriorreales = veranteriorreales;
    }

    public boolean isVerultimosreales() {
        return verultimosreales;
    }

    public void setVerultimosreales(boolean verultimosreales) {
        this.verultimosreales = verultimosreales;
    }

    public int getTotalpaginas() {
        return totalpaginas;
    }

    public void setTotalpaginas(int totalpaginas) {
        this.totalpaginas = totalpaginas;
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

    public int getEstadoguardado() {
        return estadoguardado;
    }

    public void setEstadoguardado(int estadoguardado) {
        this.estadoguardado = estadoguardado;
    }

    public UIDataTable getTablatipoobra() {
        return tablatipoobra;
    }

    public void setTablatipoobra(UIDataTable tablatipoobra) {
        this.tablatipoobra = tablatipoobra;
    }
    private boolean img = false;
    private boolean imgppal = false;
    private String nombreimg = "";

    public boolean isImgppal() {
        return imgppal;
    }

    public void setImgppal(boolean imgppal) {
        this.imgppal = imgppal;
    }

    public String getNombreimg() {
        return nombreimg;
    }

    public void setNombreimg(String nombreimg) {
        this.nombreimg = nombreimg;
    }

    public boolean isImg() {
        return img;
    }

    public void setImg(boolean img) {
        this.img = img;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Obra getObrapadre() {
        return obrapadre;
    }

    public void setObrapadre(Obra obrapadre) {
        this.obrapadre = obrapadre;
    }

    public int getVerEliminar() {
        return verEliminar;
    }

    public void setVerEliminar(int verEliminar) {
        this.verEliminar = verEliminar;
    }

    public List<Tercero> getListerentidad() {
        return listerentidad;
    }

    public void setListerentidad(List<Tercero> listerentidad) {
        this.listerentidad = listerentidad;
    }

    public int getDisableAiu() {
        return disableAiu;
    }

    public void setDisableAiu(int disableAiu) {
        this.disableAiu = disableAiu;
    }

    public int getDisableCronograma() {
        return disableCronograma;
    }

    public void setDisableCronograma(int disableCronograma) {
        this.disableCronograma = disableCronograma;
    }

    public int getSubtiposelec() {
        return subtiposelec;
    }

    public void setSubtiposelec(int subtiposelec) {
        this.subtiposelec = subtiposelec;
    }

    public CargadorArchivosWeb getCargadorImagenAnterior() {
        return cargadorImagenAnterior;
    }

    public void setCargadorImagenAnterior(CargadorArchivosWeb cargadorImagenAnterior) {
        this.cargadorImagenAnterior = cargadorImagenAnterior;
    }

    public CargadorArchivosWeb getCargadorImagenPrincipal() {
        return cargadorImagenPrincipal;
    }

    public void setCargadorImagenPrincipal(CargadorArchivosWeb cargadorImagenPrincipal) {
        this.cargadorImagenPrincipal = cargadorImagenPrincipal;
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

    public String getValorFiltroImagenevolucionobra() {
        return valorFiltroImagenevolucionobra;
    }

    public void setValorFiltroImagenevolucionobra(String valorFiltroImagenevolucionobra) {
        this.valorFiltroImagenevolucionobra = valorFiltroImagenevolucionobra;
    }

    public UIDataTable getTablaImagenesevolucion() {
        return tablaImagenesevolucion;
    }

    public void setTablaImagenesevolucion(UIDataTable tablaImagenesevolucion) {
        this.tablaImagenesevolucion = tablaImagenesevolucion;
    }

    public List<Imagenevolucionobra> getListaImagenesevolucionobra() {
        return listaImagenesevolucionobra;
    }

    public void setListaImagenesevolucionobra(List<Imagenevolucionobra> listaImagenesevolucionobra) {
        this.listaImagenesevolucionobra = listaImagenesevolucionobra;
    }

    public List<Tercero> getTernew() {
        return ternew;
    }

    public void setTernew(List<Tercero> ternew) {
        this.ternew = ternew;
    }

    public int getTiahselect() {
        return tiahselect;
    }

    public void setTiahselect(int tiahselect) {
        this.tiahselect = tiahselect;
    }

    public boolean isVerObrasHijo() {
        return verObrasHijo;
    }

    public void setVerObrasHijo(boolean verObrasHijo) {
        this.verObrasHijo = verObrasHijo;
    }

    public UIDataTable getTablaProyectosHijos() {
        return tablaProyectosHijos;
    }

    public void setTablaProyectosHijos(UIDataTable tablaProyectosHijos) {
        this.tablaProyectosHijos = tablaProyectosHijos;
    }

    public UIDataTable getTablatipoproyecto() {
        return tablatipoproyecto;
    }

    public void setTablatipoproyecto(UIDataTable tablatipoproyecto) {
        this.tablatipoproyecto = tablatipoproyecto;
    }

    public UIDataTable getTablatiposolicitudobra() {
        return tablatiposolicitudobra;
    }

    public void setTablatiposolicitudobra(UIDataTable tablatiposolicitudobra) {
        this.tablatiposolicitudobra = tablatiposolicitudobra;
    }

    public int getMostrarbtguardado() {
        return mostrarbtguardado;
    }

    public void setMostrarbtguardado(int mostrarbtguardado) {
        this.mostrarbtguardado = mostrarbtguardado;
    }

    public int getMostrarpgconveniocontrato() {
        return mostrarpgconveniocontrato;
    }

    public void setMostrarpgconveniocontrato(int mostrarpgconveniocontrato) {
        this.mostrarpgconveniocontrato = mostrarpgconveniocontrato;
    }

    public int getPreguntaProyectoPadre() {
        return preguntaProyectoPadre;
    }

    public void setPreguntaProyectoPadre(int preguntaProyectoPadre) {
        this.preguntaProyectoPadre = preguntaProyectoPadre;
    }

    public int getPreguntaSubProyecto() {
        return preguntaSubProyecto;
    }

    public void setPreguntaSubProyecto(int preguntaSubProyecto) {
        this.preguntaSubProyecto = preguntaSubProyecto;
    }

    public SelectItem[] getPeriodoEventoItem() {
        return periodoEventoItem;
    }

    public void setPeriodoEventoItem(SelectItem[] periodoEventoItem) {
        this.periodoEventoItem = periodoEventoItem;
    }

    public int getIdevento() {
        return idevento;
    }

    public void setIdevento(int idevento) {
        this.idevento = idevento;
    }

    public SelectItem[] getEventoItem() {
        return eventoItem;
    }

    public void setEventoItem(SelectItem[] eventoItem) {
        this.eventoItem = eventoItem;
    }

    public SelectItem[] getBarrios() {
        return barrios;
    }

    public void setBarrios(SelectItem[] barrios) {
        this.barrios = barrios;
    }

    public SelectItem[] getVeredas() {
        return veredas;
    }

    public void setVeredas(SelectItem[] veredas) {
        this.veredas = veredas;
    }

    public int getIdcomuna() {
        return idcomuna;
    }

    public void setIdcomuna(int idcomuna) {
        this.idcomuna = idcomuna;
    }

    public int getIdcorregimiento() {
        return idcorregimiento;
    }

    public void setIdcorregimiento(int idcorregimiento) {
        this.idcorregimiento = idcorregimiento;
    }

    public SelectItem[] getComunas() {
        return comunas;
    }

    public void setComunas(SelectItem[] comunas) {
        this.comunas = comunas;
    }

    public SelectItem[] getCorregimientos() {
        return corregimientos;
    }

    public void setCorregimientos(SelectItem[] corregimientos) {
        this.corregimientos = corregimientos;
    }

    public boolean isVerObrasPadres() {
        return verObrasPadres;
    }

    public void setVerObrasPadres(boolean verObrasPadres) {
        this.verObrasPadres = verObrasPadres;
    }

    public UIDataTable getTablaMarkereli() {
        return tablaMarkereli;
    }

    public void setTablaMarkereli(UIDataTable tablaMarkereli) {
        this.tablaMarkereli = tablaMarkereli;
    }

    public List<Marcador> getMarli() {
        return marli;
    }

    public void setMarli(List<Marcador> marli) {
        this.marli = marli;
    }

    public boolean isEditarContrato() {
        return editarContrato;
    }

    public void setEditarContrato(boolean editarContrato) {
        this.editarContrato = editarContrato;
    }

    public List<Relacioncontratoobra> getListacontratosobra() {
        return listacontratosobra;
    }

    public void setListacontratosobra(List<Relacioncontratoobra> listacontratosobra) {
        this.listacontratosobra = listacontratosobra;
    }

    public String getValorFiltroContratos() {
        return valorFiltroContratos;
    }

    public void setValorFiltroContratos(String valorFiltroContratos) {
        this.valorFiltroContratos = valorFiltroContratos;
    }

    public String getLatNewmanu() {
        return latNewmanu;
    }

    public void setLatNewmanu(String latNewmanu) {
        this.latNewmanu = latNewmanu;
    }

    public String getLongNewmanu() {
        return longNewmanu;
    }

    public void setLongNewmanu(String longNewmanu) {
        this.longNewmanu = longNewmanu;
    }

    public String getZoom() {
        return zoom;
    }

    public void setZoom(String zoom) {
        this.zoom = zoom;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UIDataTable getTablacontratos() {
        return tablacontratos;
    }

    public void setTablacontratos(UIDataTable tablacontratos) {
        this.tablacontratos = tablacontratos;
    }

    public int getIdConvenio() {
        return idConvenio;
    }

    public void setIdConvenio(int idConvenio) {
        this.idConvenio = idConvenio;
    }

    public void setDecision(boolean decision) {
        this.decision = decision;
    }

    public boolean isDecision() {
        return decision;
    }

    public void setYesOrNo(SelectItem[] yesOrNo) {
        this.yesOrNo = yesOrNo;
    }

    public SelectItem[] getYesOrNo() {
        return yesOrNo;
    }

    public boolean isVerContrato() {
        return verContrato;
    }

    public void setVerContrato(boolean verContrato) {
        this.verContrato = verContrato;
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public String getRealArchivoPath() {
        return realArchivoPath;
    }

    public void setRealArchivoPath(String realArchivoPath) {
        this.realArchivoPath = realArchivoPath;
    }

    public SelectItem[] getTipoTerceroOPtion() {
        return TipoTerceroOPtion;
    }

    public void setTipoTerceroOPtion(SelectItem[] TipoTerceroOPtion) {
        this.TipoTerceroOPtion = TipoTerceroOPtion;
    }

    public SelectItem[] getTerceroOption() {
        return TerceroOption;
    }

    public void setTerceroOption(SelectItem[] TerceroOption) {
        this.TerceroOption = TerceroOption;
    }

    public SelectItem[] getEstadoCivilOption() {
        return EstadoCivilOption;
    }

    public void setEstadoCivilOption(SelectItem[] EstadoCivilOption) {
        this.EstadoCivilOption = EstadoCivilOption;
    }

    public SelectItem[] getGeneroOption() {
        return GeneroOption;
    }

    public void setGeneroOption(SelectItem[] GeneroOption) {
        this.GeneroOption = GeneroOption;
    }

    public SelectItem[] getDepartamento() {
        return Departamento;
    }

    public void setDepartamento(SelectItem[] Departamento) {
        this.Departamento = Departamento;
    }

    public SelectItem[] getMunicipio() {
        return Municipio;
    }

    public void setMunicipio(SelectItem[] Municipio) {
        this.Municipio = Municipio;
    }

    public SelectItem[] getRegion() {
        return Region;
    }

    public void setRegion(SelectItem[] Region) {
        this.Region = Region;
    }

    public SelectItem[] getTipoOrigenes() {
        return TipoOrigenes;
    }

    public void setTipoOrigenes(SelectItem[] TipoOrigenes) {
        this.TipoOrigenes = TipoOrigenes;
    }

    public String getCodDepartamento() {
        return codDepartamento;
    }

    public void setCodDepartamento(String codDepartamento) {
        this.codDepartamento = codDepartamento;
    }

    public String getCodDepartamentoContratista() {
        return codDepartamentoContratista;
    }

    public void setCodDepartamentoContratista(String codDepartamentoContratista) {
        this.codDepartamentoContratista = codDepartamentoContratista;
    }

    public String getCodMunicipio() {
        return codMunicipio;
    }

    public void setCodMunicipio(String codMunicipio) {
        this.codMunicipio = codMunicipio;
    }

    public int getCodRegion() {
        return codRegion;
    }

    public void setCodRegion(int codRegion) {
        this.codRegion = codRegion;
    }

    public List<Obra> getListaProyectosPadre() {
        return listaProyectosPadre;
    }

    public void setListaProyectosPadre(List<Obra> listaProyectosPadre) {
        this.listaProyectosPadre = listaProyectosPadre;
    }

    public List<Localidad> getListaLocalidades() {
        return listaLocalidades;
    }

    public void setListaLocalidades(List<Localidad> listaLocalidades) {
        this.listaLocalidades = listaLocalidades;
    }

    public List<Region> getListaRegiones() {
        return listaRegiones;
    }

    public void setListaRegiones(List<Region> listaRegiones) {
        this.listaRegiones = listaRegiones;
    }

    public int getListadiligenciada() {
        return listadiligenciada;
    }

    public void setListadiligenciada(int listadiligenciada) {
        this.listadiligenciada = listadiligenciada;
    }

    public List<Localidad> getQueryDeptos() {
        return queryDeptos;
    }

    public void setQueryDeptos(List<Localidad> queryDeptos) {
        this.queryDeptos = queryDeptos;
    }

    public List<Barrio> getListadobarrio() {
        return listadobarrio;
    }

    public void setListadobarrio(List<Barrio> listadobarrio) {
        this.listadobarrio = listadobarrio;
    }

    public List<Vereda> getListadovereda() {
        return listadovereda;
    }

    public void setListadovereda(List<Vereda> listadovereda) {
        this.listadovereda = listadovereda;
    }

    public List<Localidad> getQueryMunicipios() {
        return queryMunicipios;
    }

    public void setQueryMunicipios(List<Localidad> queryMunicipios) {
        this.queryMunicipios = queryMunicipios;
    }

    public List<Region> getQueryRegiones() {
        return queryRegiones;
    }

    public void setQueryRegiones(List<Region> queryRegiones) {
        this.queryRegiones = queryRegiones;
    }

    public UIDataTable getTablalocalidades() {
        return tablalocalidades;
    }

    public void setTablalocalidades(UIDataTable tablalocalidades) {
        this.tablalocalidades = tablalocalidades;
    }

    public UIDataTable getTableLocalidadVereda() {
        return tableLocalidadVereda;
    }

    public void setTableLocalidadVereda(UIDataTable tableLocalidadVereda) {
        this.tableLocalidadVereda = tableLocalidadVereda;
    }

    public UIDataTable getTableLocalidadbarrio() {
        return tableLocalidadbarrio;
    }

    public void setTableLocalidadbarrio(UIDataTable tableLocalidadbarrio) {
        this.tableLocalidadbarrio = tableLocalidadbarrio;
    }

    public UIDataTable getTablaregiones() {
        return tablaregiones;
    }

    public void setTablaregiones(UIDataTable tablaregiones) {
        this.tablaregiones = tablaregiones;
    }

    public Polizacontrato getPolizacontrato() {
        return polizacontrato;
    }

    public void setPolizacontrato(Polizacontrato polizacontrato) {
        this.polizacontrato = polizacontrato;
    }

    public int getTiproyectoselec() {
        return tiproyectoselec;
    }

    public void setTiproyectoselec(int tiproyectoselec) {
        this.tiproyectoselec = tiproyectoselec;
    }

    public Documentoobra getCdp() {
        return cdp;
    }

    public void setCdp(Documentoobra cdp) {
        this.cdp = cdp;
    }

    public Documentoobra getDocumentoobra() {
        return documentoobra;
    }

    public void setDocumentoobra(Documentoobra documentoobra) {
        this.documentoobra = documentoobra;
    }

    public boolean isDatosbas() {
        return datosbas;
    }

    public void setDatosbas(boolean datosbas) {
        this.datosbas = datosbas;
    }

    public int getValido() {
        return valido;
    }

    public void setValido(int valido) {
        this.valido = valido;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public SelectItem[] getTipoCostoOption() {
        return TipoCostoOption;
    }

    public void setTipoCostoOption(SelectItem[] TipoCostoOption) {
        this.TipoCostoOption = TipoCostoOption;
    }

    public SelectItem[] getFormaPago() {
        return FormaPago;
    }

    public void setFormaPago(SelectItem[] Formapago) {
        this.FormaPago = Formapago;
    }

    public SelectItem[] getPeriodoMedida() {
        return PeriodoMedida;
    }

    public void setPeriodoMedida(SelectItem[] PeriodoMedida) {
        this.PeriodoMedida = PeriodoMedida;
    }

    public Periodo[] getListadoperiodos() {
        return listadoperiodos;
    }

    public void setListadoperiodos(Periodo[] listadoperiodos) {
        this.listadoperiodos = listadoperiodos;
    }

    public String getNomImagen() {
        return nomImagen;
    }

    public void setNomImagen(String nomImagen) {
        this.nomImagen = nomImagen;
    }

    public String getPathImagen() {
        return pathImagen;
    }

    public void setPathImagen(String pathImagen) {
        this.pathImagen = pathImagen;
    }

    public Obra getObranueva() {
        return obranueva;
    }

    public void setObranueva(Obra obranueva) {
        this.obranueva = obranueva;
    }

    public int getDocumentoseleccionado() {
        return documentoseleccionado;
    }

    public void setDocumentoseleccionado(int documentoseleccionado) {
        this.documentoseleccionado = documentoseleccionado;
    }

    public String seleccionDcto() {
        documentoseleccionado = 1;

        return null;
    }

    public String seleccionDctoCDP() {
        documentoseleccionado = 2;

        return null;
    }

    public Set<Actividadobra> getActividadesobra() {
        return actividadesobra;
    }

    public void setActividadesobra(Set<Actividadobra> actividadesobra) {
        this.actividadesobra = actividadesobra;
    }

    public SelectItem[] getClaseObra() {
        return ClaseObra;
    }

    public void setClaseObra(SelectItem[] ClaseObra) {
        this.ClaseObra = ClaseObra;
    }

    public SelectItem[] getLugarObra() {
        return LugarObra;
    }

    public void setLugarObra(SelectItem[] LugarObra) {
        this.LugarObra = LugarObra;
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

    public List<Barrio> getListaBarrios() {
        return listaBarrios;
    }

    public void setListaBarrios(List<Barrio> listaBarrios) {
        this.listaBarrios = listaBarrios;
    }

    public List<Vereda> getListaVeredas() {
        return listaVeredas;
    }
    // </editor-fold>    

    public List<TerceroEntidadLista> getTemp() {
        return temp;
    }

    public void setTemp(List<TerceroEntidadLista> temp) {
        this.temp = temp;
    }

    public UIDataTable getTablaObrasPadres() {
        return tablaObrasPadres;
    }

    public void setTablaObrasPadres(UIDataTable tablaObrasPadres) {
        this.tablaObrasPadres = tablaObrasPadres;
    }

    public BigDecimal getValorfaltanteasociarcontrato() {
        return valorfaltanteasociarcontrato;
    }

    public void setValorfaltanteasociarcontrato(BigDecimal valorfaltanteasociarcontrato) {
        this.valorfaltanteasociarcontrato = valorfaltanteasociarcontrato;
    }

    public String getStrempleosdirectos() {
        return strempleosdirectos;
    }

    public void setStrempleosdirectos(String strempleosdirectos) {
        this.strempleosdirectos = strempleosdirectos;
    }

    public String getStrempleosindirectos() {
        return strempleosindirectos;
    }

    public void setStrempleosindirectos(String strempleosindirectos) {
        this.strempleosindirectos = strempleosindirectos;
    }

    public String getStrbeneficiarios() {
        return strbeneficiarios;
    }

    public void setStrbeneficiarios(String strbeneficiarios) {
        this.strbeneficiarios = strbeneficiarios;
    }
    
    public String habilitarNuevo() {
        verNuevo = true;
        address = "";
        latNewmanu = "";
        longNewmanu = "";
        return null;
    }

    public PlaceMark doSearch() {
        PlaceMark placeMarkNew = new PlaceMark();
        try {
            //zoom = "14";
            //String direcciondigi = getAddress().split(" ");
            String[] pa = getAddress().split(" ");
            if (pa.length > 1) {
                String pala = pa[0].toString();
                String palad = pa[1].toString();
                List<Localidad> lo = getSessionBeanCobra().getCobraService().encontrarMunicipiosDepartamentos(palad, pala);
                if (lo.size() == 0) {
                    List<Localidad> li = getSessionBeanCobra().getCobraService().encontrarMunicipiosDepartamentos(pala, palad);
                    lo = li;
                }
                latNewmanu = lo.get(0).getFloatlatitud().toString();
                longNewmanu = lo.get(0).getFloatlongitud().toString();
            } else {
                List<Localidad> lo = getSessionBeanCobra().getCobraService().encontrarMunicipiosDepartamentos(getAddress(), getAddress());
                latNewmanu = lo.get(0).getFloatlatitud().toString();
                longNewmanu = lo.get(0).getFloatlongitud().toString();
            }

            placeMarkNew = GMaps4JSFServiceFactory.getReverseGeocoderService().getPlaceMark(latNewmanu, longNewmanu);
            placeMarkNew.setAddress(getAddress());

            address = placeMarkNew.getAddress();

        } catch (Exception e) {
            address = "Faltante";
            placeMarkNew.setAddress(address);
        }

        obranueva.setStrdireccion(address);
        obranueva.setFloatlatitud(new BigDecimal(latNewmanu));
        obranueva.setFloatlongitud(new BigDecimal(longNewmanu));

        return placeMarkNew;

    }

    public String lonlatmanual() {
        zoom = "14";
        try {
            PlaceMark placeMarkNew = GMaps4JSFServiceFactory.getReverseGeocoderService().getPlaceMark(latNewmanu, longNewmanu);
            address = placeMarkNew.getAddress();
            //confirmarDireccion();
        } catch (Exception e) {
            address = "Faltante";
        }
        return null;
    }

    /**
     * Llenado de SelectItems.
     */
    public void llenadoSelectItems() {
        llenarLugarObra();
        llenarPeriodo(0);
        llenarTiposorigenes();
        llenarDepartamento();
        llenarMunicipio();
        llenarRegiones();
        getSessionBeanCobra().llenarPeriodoEvento();
        llenarEventos();
        llenarPeriodoxEvento();
        llenarFase();
        llenarTipoProyecto();
        llenarBarrios();
        llenarVeredas();
        llenarComunas();
        llenarCorregimientos();
//        buscarInterventor();
//        buscarSupervisor();
        
    }

    /**
     * Contructor de Clase IngresarNuevaObra
     *
     * @throws Exception
     */
    public IngresarNuevaObra() throws Exception {
        if (getSessionBeanCobra().getUsuarioObra() != null) {
            getSessionBeanCobra().llenadodatos();
            llenadoSelectItems();
            limpiarobra();

            if (getSessionBeanCobra().getCobraService().isSolibol()) {

                cargarObra(getSessionBeanCobra().getCobraService().getProyectoSoli());
            }

            id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
            if (id != null) {
                Obra obra = getSessionBeanCobra().getCobraService().encontrarObraPorId(Integer.parseInt(id));
                cargarObra(obra);
            }
        } else {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("../Inicio.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(IngresarNuevaObra.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        nombre = "";
    }

    @Override
    public void prerender() {
        try {
            id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
            if (id != null) {
                if (obranueva != null && String.valueOf(obranueva.getIntcodigoobra()).compareTo(id) != 0) {
                    limpiarobra();
                    Obra obra = getSessionBeanCobra().getCobraService().encontrarObraPorId(Integer.parseInt(id));
                    cargarObra(obra);
                }
            }
        } catch (Exception e) {
            System.out.println("excepcion = " + e);
        }
    }

    /**
     * <p>
     * Return a reference to the scoped data bean.</p>
     *
     * @return reference to the scoped data bean
     */
    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }

    /**
     * Guarda y Traslada el Archivo a una nueva carpeta.
     *
     * @param nomarch Nombre del Archivo.
     * @param URLorigen El Path del archivo donde se encuentra ubicado
     * Actualmente.
     * @param URLdestino El Path donde se va a trasladar el archivo.
     * @return Path del archivo, donde que quedo ubicado.
     */
    public String guardarArchivoObra(String nomarch, String URLorigen, String URLdestino) {
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
                    nomarch = getObranueva().getIntcodigoobra() + nomarch.substring(1, nomarch.length());
                }

                carpeta.renameTo(new File(ArchivoPath + "/" + nomarch));
                return nomarch;
            } else {

                return null;
            }

        } catch (Exception ex) {
            FacesUtils.addErrorMessage(bundle.getString("nosepuedesubir"));
        }
        return null;
    }

    /**
     * <p>
     * Automatically managed component initialization.
     * <strong>WARNING:</strong> This method is automatically generated, so any
     * user-specified code inserted here is subject to being replaced.</p>
     */
    private void _init() {
    }

    public void llenarPeriodo(int plazo) {

        List<Periodomedida> periodoList = getSessionBeanCobra().getCobraService().encontrarPeriodosMedida();
        PeriodoMedida = null;

        if (plazo >= 0 && plazo < 805) {
            PeriodoMedida = new SelectItem[periodoList.size()];
            int i = 0;

            for (Periodomedida perio : periodoList) {
                SelectItem opt = new SelectItem(perio.getIntidperiomedida(), perio.getStrdescperiomedida());
                PeriodoMedida[i++] = opt;
            }

        } else {
            if (plazo > 804 && plazo < 3450) {
                PeriodoMedida = new SelectItem[periodoList.size() - 1];
                int i = 0;

                for (Periodomedida perio : periodoList) {
                    if (perio.getIntidperiomedida() != 1) {
                        SelectItem opt = new SelectItem(perio.getIntidperiomedida(), perio.getStrdescperiomedida());
                        PeriodoMedida[i++] = opt;
                    }
                }
            }
        }

    }

    /**
     * El Metodo feciniCambio, se utiliza para calacular la diferencia de dias
     * que hay entre la Fecha inicio y la Fecha fin de una Obra.
     *
     * @return null
     */
    public String feciniCambio() {
        if (obranueva.getDatefeciniobra() != null) {
            if (obranueva.getDatefecfinobra() != null) {
                fechaCambio();
            } else {
                obranueva.setIntplazoobra(0);
            }
        } else {
            obranueva.setIntplazoobra(0);
            obranueva.setDatefecfinobra(null);
        }
        return null;
    }

    /**
     * El Metdodo fechaCambio, se utiliza para capturar el momento donde se
     * cambie cualquiera de las dos fechas (Fecha Inicio o Fecha Fin) recalcular
     * el plazo en dias de la Obra.
     *
     * @return null
     */
    public String fechaCambio() {

        Calendar hoy = Calendar.getInstance();
        if (obranueva.getDatefeciniobra() != null && obranueva.getDatefecfinobra() != null) {
            if (obranueva.getDatefecfinobra().compareTo(obranueva.getDatefeciniobra()) >= 0) {
                long diferenciaFecha = obranueva.getDatefecfinobra().getTime() - obranueva.getDatefeciniobra().getTime();
                diferenciaFecha = diferenciaFecha / (36000 * 24 * 100) + 1;
                obranueva.setIntplazoobra(Integer.parseInt(String.valueOf(diferenciaFecha)));
                if (desdeconvenio) {
                    Calendar fechaInicioConvenio = new GregorianCalendar();
                    Calendar fechaFinConvenio = new GregorianCalendar();
                    Calendar fechaInicioProyecto = new GregorianCalendar();
                    Calendar fechaFinProyecto = new GregorianCalendar();

                    fechaInicioConvenio.setTime(obranueva.getContrato().getDatefechaini());
                    fechaFinConvenio.setTime(obranueva.getContrato().getDatefechafin());
                    fechaInicioProyecto.setTime(obranueva.getDatefeciniobra());
                    fechaFinProyecto.setTime(obranueva.getDatefecfinobra());

                    if (fechaInicioProyecto.before(fechaInicioConvenio)) {
                        FacesUtils.addErrorMessage(bundle.getString("lafechainicioproyectomenorconvenio"));
                    }

                    if (fechaInicioProyecto.after(fechaFinConvenio)) {
                        FacesUtils.addErrorMessage(bundle.getString("lafechainicioproyectomayorconvenio"));
                    }
                    if (fechaFinProyecto.after(fechaFinConvenio)) {
                        FacesUtils.addErrorMessage(bundle.getString("lafechafinproyectomenorconvenio"));
                    }
                    if (fechaFinProyecto.before(fechaInicioConvenio)) {
                        FacesUtils.addErrorMessage(bundle.getString("lafechafinproyectomenorconvenio"));
                    }

                    if ((!fechaInicioProyecto.before(fechaInicioConvenio)) && (!fechaInicioProyecto.after(fechaFinConvenio)) && (!fechaFinProyecto.after(fechaFinConvenio)) && (!fechaFinProyecto.before(fechaInicioConvenio))) {
                        fechasvalidas = true;
                    }
                }
            } else {
                obranueva.setDatefecfinobra(null);
                obranueva.setIntplazoobra(0);
                FacesUtils.addErrorMessage(bundle.getString("lafechadefinalizaciondebeser"));
            }
        }
        return null;
    }

    /**
     * El Metdodo fechaCambioContrato, se utiliza para capturar el momento donde
     * se cambie cualquiera de las dos fechas (Fecha Inicio o Fecha Fin)
     * recalcular el plazo en dias del Contrato.
     *
     * @return null
     */
    public String fechaCambioContrato() {
        Calendar hoy = Calendar.getInstance();
        if (obranueva.getContrato().getDatefechaini() != null && obranueva.getContrato().getDatefechafin() != null) {
            if (obranueva.getContrato().getDatefechafin().compareTo(obranueva.getContrato().getDatefechaini()) >= 0) {
                long diferenciaFechaConvenio = obranueva.getContrato().getDatefechafin().getTime() - obranueva.getContrato().getDatefechaini().getTime();
                diferenciaFechaConvenio = diferenciaFechaConvenio / (36000 * 24 * 100) + 1;
                obranueva.getContrato().setIntduraciondias(Integer.parseInt(String.valueOf(diferenciaFechaConvenio)));
            } else {
                obranueva.getContrato().setDatefechafin(null);
                obranueva.getContrato().setIntduraciondias(0);
                //"La fecha de finalización debe ser mayor o igual a la fecha de inicio";
                FacesUtils.addErrorMessage(bundle.getString("lafechadefinalizaciondebeser"));
            }
        }
        return null;
    }

    //Paso 2 Ingresar Nueva Obra
    /**
     * Llenado de selectitem FormaPago.
     */
    public void llenarFormaPago() {
        List<Formapago> lista = getSessionBeanCobra().getCobraService().encontrarFormaPagoPorEstado();
        FormaPago = new SelectItem[lista.size()];
        int i = 0;
        for (Formapago forma : lista) {
            SelectItem opt = new SelectItem(forma.getIntidformapago(), forma.getStrdescformapago());
            FormaPago[i++] = opt;
        }
    }

    /**
     * EL metodo finalizarModal_action se utiliza para finalizar la obra en
     * proceso imcompleto, para pasar a un proyecto en vigencia
     *
     * @return Regla de Navegación "cancelar".
     * @throws Throwable
     */
    public String finalizarModal_action() throws Throwable {

//        getHomeGestion().iniciarfiltro();
//        getHomeGestion().getFiltro().setIntvista(1);
//        getHomeGestion().getFiltro().setIntcodfase(2);
//        getHomeGestion().getFiltro().setTipointervencion(-1);
//        if (obranueva.getContrato() != null) {
//            getHomeGestion().getFiltro().setTipointervencion(3);
//        }
//
//        getHomeGestion().getFiltro().setIntestadoobra(1);
//        getHomeGestion().getFiltro().setPalabraclave(obranueva.getStrnombreobra());
//
//        String cual=getHomeGestion().primeroListProyectos();
//
//        return "cancelar";
        getSessionBeanCobra().getCobraService().guardarContadorVisitas(obranueva.getIntcodigoobra(), getSessionBeanCobra().getUsuarioObra());
        getAdministrarObraNew().setObra(getSessionBeanCobra().getCobraService().encontrarObraPorId(obranueva.getIntcodigoobra()));
        getAdministrarObraNew().setOpcion(0);
        getAdministrarObraNew().getDetalleObra().iniciardetalle();
        return "admindetalleObra";
    }

    /**
     * El metodo limpiarobra se utiliza para reinicializar todo el contexto de
     * nueva obra.
     *
     * @return null
     */
    public String limpiarobra() {
        latNewmanu = "";
        longNewmanu = "";
        cargadorImagenPrincipal = new CargadorArchivosWeb();
        cargadorImagenAnterior = new CargadorArchivosWeb();
        objeto = false;
        datosbas = false;
        cargadorCronograma = new CargadorArchivosWeb();
        sumValorContrato = BigDecimal.ZERO;

        documentoseleccionado = 0;
        obranueva = new Obra();
        obranueva.setClaseobra(new Claseobra());
        obranueva.getClaseobra().setIntidclaseobra(0);
        obranueva.getClaseobra().setFase(new Fase());
        obranueva.setLugarobra(new Lugarobra(1, ""));
        obranueva.setTipoobra(new Tipoobra(0, null, ""));
        obranueva.setTipocosto(new Tipocosto(1, ""));
        obranueva.setPeriodomedida(new Periodomedida(1, "", 7, 0, 0));
        obranueva.setTipoestadobra(new Tipoestadobra(0));
        obranueva.getTipoobra().setTipoproyecto(new Tipoproyecto(1, ""));
        obranueva.setEnalimentacion(false);
        obranueva.setBoolincluyeaiu(false);

        polizacontrato = new Polizacontrato();
        polizacontrato.setTipopoliza(new Tipopoliza());
        polizacontrato.setAseguradora(new Aseguradora());
        obranueva.setPeriodoevento(new Periodoevento());
        obranueva.getPeriodoevento().setEvento(new Evento());
        obranueva.getPeriodoevento().setIntidperiodoevento(1);
        obranueva.setTipoorigen(new Tipoorigen(4, ""));
        obranueva.setTercero(new Tercero());
        obranueva.setNumvaltotobra(BigDecimal.ZERO);
        obranueva.setNumvalejecobra(BigDecimal.ZERO);
        obranueva.setNumvaldeclarado(BigDecimal.ZERO);
        obranueva.setNumvalavanfinanciaerodeclarado(BigDecimal.ZERO);
        obranueva.setNumvalavanfisicodeclarado(BigDecimal.ZERO);
        obranueva.setNumvalprogramejec(BigDecimal.ZERO);
        pathImagen = "/resources/imgs/bt_nodisponible.png";
        nomImagen = "";
        valido = 0;
        estado = 0;
        verPrimario = true;
        verNuevo = false;
        //cargar = 0;
        listaLocalidades = new ArrayList<Localidad>();
        listaBarrios = new ArrayList<Barrio>();
        listaVeredas = new ArrayList<Vereda>();
        listaProyectosPadre = new ArrayList<Obra>();
        listaRegiones = new ArrayList<Region>();
        preguntaSubProyecto = 0;
        preguntaProyectoPadre = 0;
        verContrato = false;
        verObrasPadres = false;

        address = "Bogotá";
        tiproyectoselec = 0;
        subtiposelec = 0;
        tiahselect = 0;
        img = false;
        imgppal = false;
        marli = new ArrayList<Marcador>();
        listacontratosobra = new ArrayList<Relacioncontratoobra>();
        listamarcadores = new ArrayList<Marcador>();
        listaImagenesevolucionobra = new ArrayList<Imagenevolucionobra>();
        ServletContext theApplicationsServletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        this.realArchivoPath = theApplicationsServletContext.getRealPath(URL);
        desdeconvenio = false;
        verConfirmar = false;
        obranueva.setPuntoobras(new LinkedHashSet());
        llenarEntidadesContratantes();
        EncontrarSolicitante();
        estadoguardado = 0;
        nombreimg = "";
        navegacion = 0;
        valortotalobra = BigDecimal.ZERO;
        tiposelec = 0;
        chequiarFase(0);
        if (getSessionBeanCobra().getBundle().getString("aplicafonade").equals("true")
                || getSessionBeanCobra().getBundle().getString("aplicafaseenproyecto").equals("false")) {
            tiahselect = 2;
            obtenerFaseSeleccionada(1);

            llenarTipoProyecto();
        }

        return "nuevoProyecto";

    }

    /// Manejo de Opciones Tipo de Obra
    /**
     * Llenado de selectitem ClaseObra.
     *
     * @return null
     */
    public void llenarClaseObra(int codfase) {

        List<Claseobra> lista = getSessionBeanCobra().getCobraService().encontrarClaseObraPorEstadoPorFase(codfase);
        ClaseObra = new SelectItem[lista.size()];
        int i = 0;
        for (Claseobra clase : lista) {
            SelectItem opt = new SelectItem(clase.getIntidclaseobra(), clase.getStrdescclaseobra());
                ClaseObra[i++] = opt;
            }
        faseelegida.setIntidfase(tiahselect);
        tiproyectoselec = 0;

    }

    public void obtenerFaseSeleccionada(int fila) {
        chequiarFase(getSessionBeanCobra().getCobraService().getFase().get(fila).getIntidfase());
        setTiahselect(getSessionBeanCobra().getCobraService().getFase().get(fila).getIntidfase());
        llenarClaseObra(getSessionBeanCobra().getCobraService().getFase().get(fila).getIntidfase());
    }

    /**
     * Llenado de selectitem LugarObra.
     */
    private void llenarLugarObra() {
        List<Lugarobra> lista = getSessionBeanCobra().getCobraService().encontrarLugaresObra();
        LugarObra = new SelectItem[lista.size()];
        int i = 0;
        for (Lugarobra lugar : lista) {
            SelectItem opt = new SelectItem(lugar.getIntidlugarobra(), lugar.getStrdesclugarobra());
            LugarObra[i++] = opt;
        }
    }

    // Tipo Costos
    /**
     * Llenado de selectitem TipoCostoOption.
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

    //Proyectos Padres
    /**
     * llenado de proyectos padres.
     *
     * @return null
     */
    public String llenarproyectospadres() {
        primerosreales();
        return null;
    }

    /**
     * El getNombreCorto() se utiliza para recorrer la ListaProyectosPadre, y
     * por cada una las Obras o Proyectos, abreviar el nombre.
     */
    public void getNombreCorto() {
        int i = 0;
        while (i < listaProyectosPadre.size()) {
            Obra b = listaProyectosPadre.get(i);
            if (b.getStrnombreobra().length() > 20) {
                String nombcorto = b.getStrnombreobra().substring(0, 20) + "...";
                listaProyectosPadre.get(i).setStrnombrecrot(nombcorto.toLowerCase());
            } else {
                listaProyectosPadre.get(i).setStrnombrecrot(listaProyectosPadre.get(i).getStrnombreobra().toLowerCase());
            }
            i++;
        }
    }

    /**
     * Metodo que se utiliza para paginar, Trae las primeros proyectos 0 a 5
     *
     * @return null
     */
    public String primerosreales() {
        Tercero terbuscarobras = new Tercero();
        terbuscarobras = listerentidad.get(0);
        if (aplicafiltro) {
            listaProyectosPadre = getSessionBeanCobra().getCobraService().filtrarObrasPadres(terbuscarobras, nombreobra, 0, 5);
            totalfilas = getSessionBeanCobra().getCobraService().numFiltrarObrasPadres(terbuscarobras, nombreobra);
            getNombreCorto();
        } else {

            listaProyectosPadre = getSessionBeanCobra().getCobraService().encontrarObrasPadres(terbuscarobras, 0, 5);
            totalfilas = getSessionBeanCobra().getCobraService().getNumObrasPadre(terbuscarobras);
            getNombreCorto();
        }
        pagina = 1;
        if (totalfilas <= 5) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 5;
            if (totalfilas % 5 > 0) {
                totalpaginas++;
            }
        }
        veranteriorreales = false;
        if (totalpaginas > 1) {
            verultimosreales = true;
        } else {
            verultimosreales = false;
        }
        return null;
    }

    /**
     * Metodo que se utiliza para paginar, Trae los siguientes 5 proyectos
     *
     * @return null
     */
    public String siguientesReales() {
        Tercero terbuscarobras = new Tercero();
        terbuscarobras.setIntcodigo(listerentidad.get(0).getIntcodigo());
        int num = (pagina) * 5;
        if (aplicafiltro) {
            listaProyectosPadre = getSessionBeanCobra().getCobraService().filtrarObrasPadres(terbuscarobras, nombreobra, num, num + 5);
            totalfilas = getSessionBeanCobra().getCobraService().numFiltrarObrasPadres(terbuscarobras, nombreobra);
            getNombreCorto();
        } else {
            listaProyectosPadre = getSessionBeanCobra().getCobraService().encontrarObrasPadres(terbuscarobras, num, num + 5);
            totalfilas = getSessionBeanCobra().getCobraService().getNumObrasPadre(terbuscarobras);
            getNombreCorto();
        }
        if (totalfilas <= 5) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 5;
            if (totalfilas % 5 > 0) {
                totalpaginas++;
            }
        }
        pagina = pagina + 1;
        if (pagina < totalpaginas) {
            verultimosreales = true;
        } else {
            verultimosreales = false;
        }
        veranteriorreales = true;
        return null;
    }

    /**
     * Metodo que se utiliza para paginar, Trae los 5 anteriores proyectos
     *
     * @return null
     */
    public String anterioresReales() {
        Tercero terbuscarobras = new Tercero();
        terbuscarobras.setIntcodigo(listerentidad.get(0).getIntcodigo());
        pagina = pagina - 1;
        int num = (pagina - 1) * 5;
        if (aplicafiltro) {
            listaProyectosPadre = getSessionBeanCobra().getCobraService().filtrarObrasPadres(terbuscarobras, nombreobra, num, num + 5);
            totalfilas = getSessionBeanCobra().getCobraService().numFiltrarObrasPadres(terbuscarobras, nombreobra);
            getNombreCorto();
        } else {
            listaProyectosPadre = getSessionBeanCobra().getCobraService().encontrarObrasPadres(terbuscarobras, num, num + 5);
            totalfilas = getSessionBeanCobra().getCobraService().getNumObrasPadre(terbuscarobras);
            getNombreCorto();
        }
        if (totalfilas <= 5) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 5;
            if (totalfilas % 5 > 0) {
                totalpaginas++;
            }
        }

        if (pagina > 1) {
            veranteriorreales = true;
        } else {
            veranteriorreales = false;
        }
        verultimosreales = true;
        return null;
    }

    /**
     * Metodo que se utiliza para paginar, Trae los ultimos 5 proyectos
     *
     * @return null
     */
    public String ultimoReales() {
        Tercero terbuscarobras = new Tercero();
        terbuscarobras.setIntcodigo(listerentidad.get(0).getIntcodigo());
        int num = totalfilas % 5;
        if (aplicafiltro) {
            totalfilas = getSessionBeanCobra().getCobraService().numFiltrarObrasPadres(terbuscarobras, nombreobra);
            listaProyectosPadre = getSessionBeanCobra().getCobraService().filtrarObrasPadres(terbuscarobras, nombreobra, totalfilas - num, totalfilas);
            getNombreCorto();
        } else {
            totalfilas = getSessionBeanCobra().getCobraService().getNumObrasPadre(terbuscarobras);
            listaProyectosPadre = getSessionBeanCobra().getCobraService().encontrarObrasPadres(terbuscarobras, totalfilas - num, totalfilas);
            getNombreCorto();
        }
        if (totalfilas <= 5) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 5;
            if (totalfilas % 5 > 0) {
                totalpaginas++;
            }
        }
        pagina = totalpaginas;
        if (pagina < totalpaginas) {
            verultimosreales = true;
        } else {
            verultimosreales = false;
        }
        veranteriorreales = true;
        return null;
    }

    /**
     * El Metodo generarXLSPOI se utiliza generar el cronograma (.xls) de
     * acuerdo a la obra en proceso de creación
     *
     * @return null
     */
    public String generarXLSPOI() {
        validarDatosBasicos();
        if (datosbas) {
            guardarObraTemporal();
            String ArchivoPath = "";
            ServletContext theApplicationsServletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

            if (!isFormatoxlsx()) {
                ArchivoPath = theApplicationsServletContext.getRealPath("/resources/Plantilla/" + bundle.getString("plantillaobra"));
            } else {
                ArchivoPath = theApplicationsServletContext.getRealPath("/resources/Plantilla/" + bundle.getString("plantillaobraxlsx"));
            }

            File archivo = new File(ArchivoPath);

            if (archivo.exists()) {
                InputStream inp = null;
                try {
                    inp = new FileInputStream(archivo);
                    Workbook wb = WorkbookFactory.create(inp);
                    Sheet sheet = wb.getSheetAt(0);
                    Row row = sheet.getRow(2);

//                 //    sheet.protectSheet("hola");
//                      HSSFCellStyle stylex = (HSSFCellStyle) wb.createCellStyle();
//                        stylex.setLocked(false);
//                        HSSFCellStyle stylexc = (HSSFCellStyle) wb.createCellStyle();
//                        stylexc.setLocked(true);
                    Cell cell = row.getCell(2);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    cell.setCellValue(obranueva.getStrnombreobra());
                    row = sheet.getRow(3);
                    cell = row.getCell(2);
                    //   row.setRowStyle(stylex);
                    // cell.setCellStyle(stylex);
                    cell.setCellValue(obranueva.getIntplazoobra());
                    row = sheet.getRow(4);
                    cell = row.getCell(2);
                    cell.setCellValue(obranueva.getDatefeciniobra());
                    row = sheet.getRow(5);
                    cell = row.getCell(2);
                    cell.setCellValue(obranueva.getDatefecfinobra());
                    row = sheet.getRow(8);

//**lista actividades escabacion...etc
                    //subtiposelec = obranueva.getTipoobra().getInttipoobra();
                    List<Bdpu> lstipoobraBdpuexce = getSessionBeanCobra().getCobraService().encontrarRelacionTipoObraBdpuxSubtipo(subtiposelec);
                    if (lstipoobraBdpuexce.size() > 0) {//el tipoobra si tiene bdpus
                        CellRangeAddressList addressListxc = new CellRangeAddressList();
                        addressListxc.addCellRangeAddress(9, 1, 999, 1);
                        String[] excelListValues = new String[lstipoobraBdpuexce.size()];
                        for (int i = 0; i < lstipoobraBdpuexce.size(); i++) {
                            excelListValues[i] = lstipoobraBdpuexce.get(i).getStrdescripcion();
                        }
                        DVConstraint dvConstraintsxxact = DVConstraint.createExplicitListConstraint(excelListValues);//DVConstraint dvConstraints = DVConstraint.createFormulaListConstraint("$B$1:$D$1");
                        HSSFDataValidation dataValidationxxact = new HSSFDataValidation(addressListxc, dvConstraintsxxact);
                        dataValidationxxact.setSuppressDropDownArrow(false);
                        dataValidationxxact.createPromptBox("Seleccione ", " Categoria");
                        dataValidationxxact.setShowPromptBox(true);
                        dataValidationxxact.createErrorBox("    Dato inválido     !!", " Seleccione de la lista");
                        dataValidationxxact.setErrorStyle(HSSFDataValidation.ErrorStyle.STOP);
                        sheet.addValidationData(dataValidationxxact);
                    } else {
                        if (isFormatoxlsx()) {
                            XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
                            style.setFillBackgroundColor(HSSFColor.WHITE.index);
                            style.setFillForegroundColor(HSSFColor.WHITE.index);
                            style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                            for (int i = 8; i < 1000; i++) {
                                Row row1 = sheet.getRow(i);
                                Cell cell1 = row1.getCell(1);
                                cell1.setCellStyle(style);
                                cell1.setCellValue("");
                            }

                        } else {
                            HSSFCellStyle style = (HSSFCellStyle) wb.createCellStyle();
                            style.setFillBackgroundColor(HSSFColor.WHITE.index);
                            style.setFillForegroundColor(HSSFColor.WHITE.index);
                            style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                            for (int i = 8; i < 1000; i++) {
                                Row row1 = sheet.getRow(i);
                                Cell cell1 = row1.getCell(1);
                                cell1.setCellStyle(style);
                                cell1.setCellValue("");
                            }
                        }

                    }

                    //-----LISTA UNIDAD DE MEDIDA CRONOGRAMA CON LAS LIB 3.8 14nov2011 ok
                    //List<Unidadmedida> listaunidadmedida = getSessionBeanCobra().getCobraService().encontrarUnidadMedidaporSubtipo(subtiposelec);
//////                    List<Unidadmedida> listaunidadmedida = getSessionBeanCobra().getCobraService().encontrarUnidadMedida();
//////                    String[] valoreslistaexcel = new String[listaunidadmedida.size()];
//////                    CellRangeAddressList posicionexcellista = new CellRangeAddressList();
//////                    posicionexcellista.addCellRangeAddress(9, 3, 999, 3);
//////                    for (int i = 0; i < listaunidadmedida.size(); i++) {
//////                        valoreslistaexcel[i] = listaunidadmedida.get(i).getStrnombre();
//////                    }
//////                    DVConstraint dvConstraintsxx = DVConstraint.createExplicitListConstraint(valoreslistaexcel);//DVConstraint dvConstraints = DVConstraint.createFormulaListConstraint("$B$1:$D$1");
//////                    HSSFDataValidation dataValidationxx = new HSSFDataValidation(posicionexcellista, dvConstraintsxx);
//////                    dataValidationxx.setEmptyCellAllowed(true);
//////                    dataValidationxx.setShowPromptBox(true);
//////                    dataValidationxx.createPromptBox("Seleccione ", " Unidad de Medida");
//////                    dataValidationxx.setSuppressDropDownArrow(false);
//////                    dataValidationxx.createErrorBox("    Dato inválido     !!", " Seleccione de la lista");
//////                    dataValidationxx.setErrorStyle(HSSFDataValidation.ErrorStyle.STOP);
//////                    sheet.addValidationData(dataValidationxx);
//////
                    //Generando Periodos de Medida
                    int indiceperiodo = obranueva.getPeriodomedida().getIntidperiomedida();

                    int division = 0;
                    String perio = "";
                    switch (indiceperiodo) {
                        case 1:
                            perio = "SEMANA ";
                            division = obranueva.getIntplazoobra() / 7;

                            if (obranueva.getIntplazoobra() % 7 != 0) {
                                division = division + 1;
                            }
                            break;
                        case 2:
                            perio = "QUINCENA ";
                            division = obranueva.getIntplazoobra() / 14;

                            if (obranueva.getIntplazoobra() % 14 != 0) {
                                division = division + 1;
                            }
                            break;
                        case 3:
                            perio = "MES ";
                            division = obranueva.getIntplazoobra() / 30;

                            if (obranueva.getIntplazoobra() % 30 != 0) {
                                division = division + 1;
                            }
                            break;
                    }

                    int i = 1;
                    int j = 8;

                    while (i <= division) {

                        cell = row.createCell(j);
                        cell.setCellValue("Q");
//                        cell = row.createCell(j + 1);
//                        cell.setCellValue("VALOR");
                        i++;
                        j = j + 1;
                        sheet.autoSizeColumn(j);
//                        sheet.autoSizeColumn(j + 1);
                    }
                    cell = row.createCell(j);
                    cell.setCellValue("VALOR FALTANTE");
                    sheet.autoSizeColumn(j);
                    j = 8;
                    i = 1;
                    row = sheet.getRow(7);
                    cell = row.getCell(7);
                    if (cell == (null)) {
                        cell = row.createCell(7);
                    }
                    cell.setCellValue("FALTANTE");
                    while (i <= division) {
//semanas
                        cell = row.createCell(j);
                        cell.setCellValue(perio + " " + i);
//                        sheet.addMergedRegion(new CellRangeAddress(7, 7, j, j + 1));
                        i++;
                        j = j + 1;

                    }
                    row = sheet.getRow(5);
                    int k = 65535;
                    j = 8;
                    i = 1;
                    String formula = "";
//                    while (i <= division) {
//
//                        cell = row.createCell(j + 1);
//                        CellReference celdaini = new CellReference(9, j + 1);
//                        CellReference celdafin = new CellReference(k - 1, j + 1);
//                        formula = "SUM(" + celdaini.formatAsString() + ":" + celdafin.formatAsString() + ")";
//                        cell.setCellFormula(formula);
//                        sheet.setColumnWidth(j + 1, 4000);
//                        i++;
//                        j = j + 1;
//
//                    }
                    k = 9;
                    while (k < 1000) {
                        row = sheet.getRow(k);
                        j = 8;
                        i = 1;
//                        formula = "";
                        String formulafalt = "E" + (k + 1) + "-SUM(";
                        CellReference celda = new CellReference(k, j);
                        formulafalt = formulafalt + celda.formatAsString() + ":";
                        while (i <= division) {

                            cell = row.createCell(j + 1);
                            celda = new CellReference(k, j);
//                            formula = "F" + (k + 1) + "*" + celda.formatAsString();
                            Cell cellda = row.createCell(j);
                            cellda.setCellValue(0);
                            cellda.setCellType(0);
                            sheet.setColumnWidth(j + 1, 5000);
                            i++;
                            j = j + 1;
                        }
                        cell = row.createCell(7);
//                        cell.setCellFormula(formulafalt + "(E" + (k + 1) + "*-1))*-1");

                        cell.setCellFormula(formulafalt + celda.formatAsString() + ")");

                        CellReference celdafalt = new CellReference(k, 7);
                        cell = row.createCell(j);
                        cell.setCellFormula("F" + (k + 1) + "*" + celdafalt.formatAsString());
                        cell.getCellStyle().setLocked(true);

                        k++;
                    }
                    
                    row = sheet.getRow(3);
                    cell = row.getCell(6);
                    CellReference celdafinal = new CellReference(65535, j);
                    CellReference celdainicial = new CellReference(9, j);
                    formula = "SUM(" + celdainicial.formatAsString() + ":" + celdafinal.formatAsString() + ")";
                    cell.setCellFormula(formula);
                    
                    Row focusRow =  sheet.getRow(9);
                    Cell focusCell = focusRow.getCell(2);
                    focusCell.setAsActiveCell();
                    File carpeta = new File(realArchivoPath + "/" + String.valueOf(obranueva.getIntcodigoobra()));
                    if (!carpeta.exists()) {
                        carpeta.mkdirs();

                    }
                    FileOutputStream fileOut;
                    // Write the output to a file
                    if (!isFormatoxlsx()) {
                        fileOut = new FileOutputStream(realArchivoPath + "/" + String.valueOf(obranueva.getIntcodigoobra()) + "/" + bundle.getString("plantillaobra"));
                    } else {
                        fileOut = new FileOutputStream(realArchivoPath + "/" + String.valueOf(obranueva.getIntcodigoobra()) + "/" + bundle.getString("plantillaobraxlsx"));
                    }

                    //FileOutputStream fileOut = new FileOutputStream("workbook.xls");
                    wb.write(fileOut);
                    fileOut.close();
                    if (!isFormatoxlsx()) {
                        this.getSessionBeanCobra().setUrlAbri(URL + String.valueOf(obranueva.getIntcodigoobra()) + "/" + bundle.getString("plantillaobra"));
                    } else {
                        this.getSessionBeanCobra().setUrlAbri(URL + String.valueOf(obranueva.getIntcodigoobra()) + "/" + bundle.getString("plantillaobraxlsx"));
                    }
                    //getHomeInterventor().cargarObrasUsuario();
                    FacesUtils.addInfoMessage(getSessionBeanCobra().getBundle().getString("laobrasehaguardadoysehaclasificado"));
                    //"La Obra se ha guardado y se ha clasificado como Obra en Proceso";

                } catch (Exception ex) {
                    Logger.getLogger(IngresarNuevaObra.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        inp.close();
                    } catch (IOException ex) {
                        Logger.getLogger(IngresarNuevaObra.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                System.out.println("La plantilla no existe");
            }
        }
        return "null";
    }

    /**
     * Se utiliza para descargar documentos de cualquier tipo
     *
     * @return null
     */
    public String bt_download_action() {
        guardadoIntermedio();
        getDownload().onDownload();
        return null;
    }

    /**
     * <p>
     * Return a reference to the scoped data bean.</p>
     *
     * @return reference to the scoped data bean
     */
    public Download getDownload() {
        return (Download) FacesUtils.getManagedBean("Cobra$Download");
    }

    /**
     * Guarda las imagenes de la obra temporalmente
     *
     * @param rutaWebCarpetaDestino Carpeta en la que se guardarán las imágenes
     */
    private void guardarImagenesaobra(String rutaWebCarpetaDestino) {
        int i = 0;
        obranueva.setImagenevolucionobras(new LinkedHashSet());
        String carpetaDestino = MessageFormat.format(rutaWebCarpetaDestino, "" + obranueva.getIntcodigoobra());
        while (i < listaImagenesevolucionobra.size()) {
            Imagenevolucionobra imagen = listaImagenesevolucionobra.get(i);
            String rutaDestino = carpetaDestino + imagen.getStrubicacion().substring(imagen.getStrubicacion().lastIndexOf("/") + 1);
            if (!imagen.getStrubicacion().equals(rutaDestino)) {
                try {
                    ArchivoWebUtil.copiarArchivo(imagen.getStrubicacion(), rutaDestino, true, true);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(IngresarNuevaObra.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ArchivoExistenteException ex) {
                    Logger.getLogger(IngresarNuevaObra.class.getName()).log(Level.SEVERE, null, ex);
                }
                imagen.setStrubicacion(rutaDestino);
            }
            imagen.setObra(obranueva);
            if (imagen.getTipoimagen().getIntidtipoimagen() == 5) {
                obranueva.setStrimagenobra(imagen.getStrubicacion());
            }
            i++;
        }
        obranueva.setImagenevolucionobras(new LinkedHashSet(listaImagenesevolucionobra));
    }

    /**
     * El metodo se utiliza para para cargar las latitudes y longitudes de un
     * proyecto donde se va a ejecutar
     */
    private void cargarPuntosaobra() {
        int i = 0;
        obranueva.setPuntoobras(new LinkedHashSet());
        obranueva.getPuntoobras().clear();
        if (marli.size() > 0) {
            i = 0;
            while (i < marli.size()) {
                if (i == 0) {
                    obranueva.setFloatlatitud(new BigDecimal(marli.get(i).getLatitude().replaceAll(" ", "")));
                    obranueva.setFloatlongitud(new BigDecimal(marli.get(i).getLongitude().replaceAll(" ", "")));
                    obranueva.setStrdireccion(marli.get(i).getAddress());
                } else {
                    Puntoobra puntonew = new Puntoobra();
                    puntonew.setObra(obranueva);
                    puntonew.setStrlatitud(marli.get(i).getLatitude().replaceAll(" ", ""));
                    puntonew.setStrlongitud(marli.get(i).getLongitude().replaceAll(" ", ""));
                    puntonew.setStrdireccion(marli.get(i).getAddress());
                    obranueva.getPuntoobras().add(puntonew);
                }
                i++;
            }
        }
    }

    /**
     * El Finalizar Action es el que valida que todos pasos del proyectos han
     * sido diligenciados correctamente, para asi pasar al metodo
     * {@link finalizarModal_action()}
     *
     * @return null
     */
    public String finalizar_action() {
        if (obranueva.getIntcodigoobra() == 0) {
            obranueva.setJsfUsuario(getSessionBeanCobra().getUsuarioObra());
        }
        if (obranueva.getStrnombreobra() != null && obranueva.getStrdireccion() != null) {
            if (obranueva.getStrdireccion().compareTo("") != 0 && String.valueOf(obranueva.getFloatlatitud()).compareTo("0.0") != 0 && String.valueOf(obranueva.getFloatlongitud()).compareTo("0.0") != 0 && obranueva.getStrobjetoobra().compareTo("") != 0 && obranueva.getDatefeciniobra() != null) {
                if (valido == 1) {
                    if (obranueva.getStrimagenobra() == null) {
                        obranueva.setStrimagenobra("/resources/imgs/bt_nodisponible.png");
                    }
                    obranueva.setActividadobras(actividadesobra);
                    int i = 0;
                    int division = 0;
                    switch (obranueva.getPeriodomedida().getIntidperiomedida()) {
                        case 1:
                            division = obranueva.getIntplazoobra() / 7;
                            if (obranueva.getIntplazoobra() % 7 != 0) {
                                division = division + 1;
                            }
                            break;
                        case 2:
                            division = obranueva.getIntplazoobra() / 14;
                            if (obranueva.getIntplazoobra() % 14 != 0) {
                                division = division + 1;
                            }
                            break;
                        case 3:
                            division = obranueva.getIntplazoobra() / 30;
                            if (obranueva.getIntplazoobra() % 30 != 0) {
                                division = division + 1;
                            }
                            break;
                    }

                    if (!obranueva.isBooleantienehijos() && !isProyectoestrategia()) {
                        Set<Periodo> perios = new LinkedHashSet<Periodo>();
                        while (i < division) {
                            listadoperiodos[i].setObra(obranueva);
                            perios.add(listadoperiodos[i]);
                            i++;
                        }
                        obranueva.setPeriodos(perios);
                    }
                    Set<Novedad> novedades = new HashSet<Novedad>();
                    novedades.add(new Novedad(0, new Tiponovedad(1, ""), obranueva, new Date()));
                    obranueva.setNovedads(novedades);

                    //para aplicar el aiu al valor de la obra
                    valortotalobra = obranueva.getNumvaltotobra();
                    BigDecimal valortotalsuma = BigDecimal.valueOf(0);
                    BigDecimal operacionutilidad = new BigDecimal(obranueva.getFloatporutilidad());
                    BigDecimal valorutilidad = operacionutilidad.divide(BigDecimal.valueOf(100));
                    BigDecimal operacionadministracion = new BigDecimal(obranueva.getFloatporadmon());
                    BigDecimal valoradministracion = operacionadministracion.divide(BigDecimal.valueOf(100));
                    BigDecimal operacionivasobreutilidad = new BigDecimal(obranueva.getFloatporivasobreutil());
                    operacionivasobreutilidad = operacionivasobreutilidad.divide(BigDecimal.valueOf(100));
                    switch (obranueva.getTipocosto().getInttipocosto()) {
                        case 1:
                            operacionivasobreutilidad = operacionivasobreutilidad.multiply(valorutilidad);
                            break;
                        case 2:
                            operacionivasobreutilidad = operacionivasobreutilidad.multiply(valoradministracion);
                            break;
                    }
                    BigDecimal operacionimprevistos = new BigDecimal(obranueva.getFloatporimprevi());
                    BigDecimal valorimprevistos = operacionimprevistos.divide(BigDecimal.valueOf(100));
                    BigDecimal operacionvalorotros = new BigDecimal(obranueva.getFloatporotros());
                    BigDecimal valorotros = operacionvalorotros.divide(BigDecimal.valueOf(100));
                    if (obranueva.getBoolincluyeaiu() == false) {
                        valortotalsuma = valortotalsuma.add(valortotalobra.multiply(operacionivasobreutilidad));
                        valortotalsuma = valortotalsuma.add(valortotalobra.multiply(valorutilidad));
                        valortotalsuma = valortotalsuma.add(valortotalobra.multiply(valorimprevistos));
                        valortotalsuma = valortotalsuma.add(valortotalobra.multiply(valoradministracion));
                        valortotalsuma = valortotalsuma.add(valortotalobra.multiply(valorotros));
                    }
                    valortotalobra = valortotalobra.add(valortotalsuma);
                    obranueva.setNumvaltotobra(valortotalobra);
                    obranueva.setNumvlrsumahijos(BigDecimal.ZERO);
                    //para mineria valor del proyecto se va restando cada vez q se le asigne un hijo al proyecto
                    obranueva.setRelacioncontratoobras(new LinkedHashSet());
                    if (listacontratosobra.size() > 0 || isProyectoestrategia()) {
                        // obranueva.setContratos(new LinkedHashSet(listacontratosobra));
                        for (Relacioncontratoobra cont : listacontratosobra) {
                            obranueva.getRelacioncontratoobras().add(cont);
                        }
                        if (isProyectoestrategia()) {
                            BigDecimal dispo = obranueva.getContrato().getNumvlrcontrato().subtract(obranueva.getContrato().getNumvlrsumahijos().add(obranueva.getContrato().getNumvlrsumaproyectos()));

                            if (dispo.compareTo(obranueva.getNumvaltotobra()) >= 0) {

                                Relacioncontratoobra cont = new Relacioncontratoobra();
                                cont.setContrato(obranueva.getContrato());
                                cont.setObra(obranueva);
                                cont.setNumvalorrelacion(obranueva.getNumvaltotobra());
                                obranueva.getRelacioncontratoobras().add(cont);
                            } else {
                                FacesUtils.addErrorMessage("El convenio solo posee disponibilidad de recursos de $" + dispo);
                                setMensaje("El convenio solo posee disponibilidad de recursos de $" + dispo);
                                return null;
                            }

                        }
                    } else {

                        FacesUtils.addErrorMessage("Debe asociar al menos un contrato al proyecto.");
                        return null;
                    }

                    /// revisar esto                    
                    if (!obranueva.isBooleantienehijos() && !isProyectoestrategia()) {
                        //para aplicar el aiu al valor de la obra
                        Iterator actividadesIterador = obranueva.getActividadobras().iterator();
                        while (actividadesIterador.hasNext()) {
                            Actividadobra actividad = new Actividadobra();
                            actividad = (Actividadobra) actividadesIterador.next();
                            BigDecimal valoractividad = actividad.getNumvalorplanifao();
                            BigDecimal valoractividadsuma = BigDecimal.valueOf(0);
                            if (obranueva.getBoolincluyeaiu() == false) {
                                valoractividadsuma = valoractividadsuma.add(valoractividad.multiply(operacionivasobreutilidad));
                                valoractividadsuma = valoractividadsuma.add(valoractividad.multiply(valorutilidad));
                                valoractividadsuma = valoractividadsuma.add(valoractividad.multiply(valorimprevistos));
                                valoractividadsuma = valoractividadsuma.add(valoractividad.multiply(valoradministracion));
                                valoractividadsuma = valoractividadsuma.add(valoractividad.multiply(valorotros));
                                valoractividad = valoractividad.add(valoractividadsuma);
                                Iterator relacionesIterator = actividad.getRelacionactividadobraperiodos().iterator();
                                while (relacionesIterator.hasNext()) {
                                    Relacionactividadobraperiodo rel = (Relacionactividadobraperiodo) relacionesIterator.next();
                                    BigDecimal valoractividadsumarelacion = BigDecimal.valueOf(0);
                                    valoractividadsumarelacion = valoractividadsumarelacion.add(rel.getNumvalplanif().multiply(operacionivasobreutilidad));
                                    valoractividadsumarelacion = valoractividadsumarelacion.add(rel.getNumvalplanif().multiply(valorutilidad));
                                    valoractividadsumarelacion = valoractividadsumarelacion.add(rel.getNumvalplanif().multiply(valorimprevistos));
                                    valoractividadsumarelacion = valoractividadsumarelacion.add(rel.getNumvalplanif().multiply(valoradministracion));
                                    valoractividadsumarelacion = valoractividadsumarelacion.add(rel.getNumvalplanif().multiply(valorotros));
                                    rel.setNumvalplanif(rel.getNumvalplanif().add(valoractividadsumarelacion));
                                }
                            }
                            actividad.setNumvalorplanifao(valoractividad);
                        }
                        Iterator periodosIterador = obranueva.getPeriodos().iterator();
                        while (periodosIterador.hasNext()) {
                            Periodo periodo = new Periodo();
                            periodo = (Periodo) periodosIterador.next();
                            BigDecimal periodoaiu = periodo.getNumvaltotplanif();
                            BigDecimal sumaperiodoaiu = BigDecimal.valueOf(0);
                            if (obranueva.getBoolincluyeaiu() == false) {
                                sumaperiodoaiu = sumaperiodoaiu.add(periodoaiu.multiply(operacionivasobreutilidad));
                                sumaperiodoaiu = sumaperiodoaiu.add(periodoaiu.multiply(valorutilidad));
                                sumaperiodoaiu = sumaperiodoaiu.add(periodoaiu.multiply(valorimprevistos));
                                sumaperiodoaiu = sumaperiodoaiu.add(periodoaiu.multiply(valoradministracion));
                                sumaperiodoaiu = sumaperiodoaiu.add(periodoaiu.multiply(valorotros));
                                periodoaiu = periodoaiu.add(sumaperiodoaiu);
                                Iterator relacionesIterator = periodo.getRelacionactividadobraperiodos().iterator();
                                while (relacionesIterator.hasNext()) {
                                    Relacionactividadobraperiodo rel = (Relacionactividadobraperiodo) relacionesIterator.next();
                                    BigDecimal valoractividadsumarelacion = BigDecimal.valueOf(0);
                                    valoractividadsumarelacion = valoractividadsumarelacion.add(rel.getNumvalplanif().multiply(operacionivasobreutilidad));
                                    valoractividadsumarelacion = valoractividadsumarelacion.add(rel.getNumvalplanif().multiply(valorutilidad));
                                    valoractividadsumarelacion = valoractividadsumarelacion.add(rel.getNumvalplanif().multiply(valorimprevistos));
                                    valoractividadsumarelacion = valoractividadsumarelacion.add(rel.getNumvalplanif().multiply(valoradministracion));
                                    valoractividadsumarelacion = valoractividadsumarelacion.add(rel.getNumvalplanif().multiply(valorotros));
                                    rel.setNumvalplanif(rel.getNumvalplanif().add(valoractividadsumarelacion));
                                }
                            }
                            periodo.setNumvaltotplanif(periodoaiu);
                        }
                        Iterator itact = actividadesobra.iterator();
                        Set<Actividadobra> actsguar = new LinkedHashSet<Actividadobra>();
                        while (itact.hasNext()) {
                            Actividadobra act = (Actividadobra) itact.next();
                            act.setObra(obranueva);
                            actsguar.add(act);
                        }
                        obranueva.setActividadobras(actsguar);
                    }
                    if (Boolean.parseBoolean(getSessionBeanCobra().getBundle().getString("habilitacionLocalidadesBarrioVereda"))) {
                        obranueva.setBarrios(new LinkedHashSet(listaBarrios));
                        obranueva.setVeredas(new LinkedHashSet(listaVeredas));
                    }
                    obranueva.setLocalidads(new LinkedHashSet());
                    obranueva.setRegions(new LinkedHashSet());
                    switch (obranueva.getTipoorigen().getIntidtipoorigen()) {
                        case 1:
                        case 2:
                            obranueva.setLocalidads(new LinkedHashSet(listaLocalidades));
                            break;
                        case 3:
                            obranueva.setRegions(new LinkedHashSet(listaRegiones));
                            break;
                    }
                    obranueva.setBarrios(new LinkedHashSet());
                    obranueva.setVeredas(new LinkedHashSet());
                    switch (obranueva.getLugarobra().getIntidlugarobra()) {
                        case 1:
                            obranueva.setBarrios(new LinkedHashSet(listaBarrios));
                        case 2:
                            obranueva.setVeredas(new LinkedHashSet(listaVeredas));
                            break;
                    }

                    obranueva.setTipoestadobra(new Tipoestadobra(1));
                    cargarPuntosaobra();

                    getSessionBeanCobra().getCobraService().guardarObra(obranueva, getSessionBeanCobra().getUsuarioObra(), -1);
                    if (!obranueva.isBooleantienehijos() && !isProyectoestrategia()) {
                        guardarCronograma();
                    }
                    guardarImagenesaobra(RutasWebArchivos.IMGS_OBRA);
                    getSessionBeanCobra().getCobraService().guardarObra(obranueva, getSessionBeanCobra().getUsuarioObra(), 1);
                    //Validamos si el proyecto es de marcológico para insertar los avances
                    if (isProyectoestrategia()) {
                        crearPeriodosAvanceMarcoLogico();
                    }
                    FacesUtils.addInfoMessage(bundle.getString("hasidoanadidaconexito"));
                    estadoguardado = 1;
                } else {
                    //"DEBE DILIGENCIAR EL PRESUPUESTO DE OBRA";
                    FacesUtils.addErrorMessage(bundle.getString("presunpuestodeobra"));
                }
            } else {
                //"DEBE DILIGENCIAR TODOS LOS DATOS REQUERIDOS EN EL PASO 1";
                FacesUtils.addErrorMessage(bundle.getString("debediligenciartodoslosdatos"));
            }
        } else {
            //"DEBE LLENAR LOS DATOS DE LA OBRA";
            FacesUtils.addErrorMessage(bundle.getString("debellenarlosdatoso"));
        }
        return null;
    }

    /**
     * El metodo se utiliza para desplegar una obra en proceso imcompleto, para
     * seguir diligenciando el proyecto
     *
     * @param obra Objeto de obra
     * @throws Exception
     */
    public void cargarObra(Obra obra) throws Exception {
        setProyectoestrategia(false);
        obranueva = obra;
        disableCronograma = 0;
        /**
         * Verificando si pertenece a estrategia
         */
        if (Boolean.parseBoolean(getSessionBeanCobra().getBundle().getString("aplicamarcologico"))) {

            if (obranueva.getContrato() != null) {
                if (getSessionBeanCobra().getMarcoLogicoService().encontrarEstrategiaProyectoMarcoLogico(obranueva.getIntcodigoobra()) != null) {
                    setProyectoestrategia(true);
                    disableCronograma = 1;
                    llenarTiposCosto();
                }
            }
        }

        setObranueva(obra);
        getObranueva().setNumvaldeclarado(BigDecimal.ZERO);
        getObranueva().setNumvalavanfinanciaerodeclarado(BigDecimal.ZERO);
        getObranueva().setNumvalavanfisicodeclarado(BigDecimal.ZERO);
        getObranueva().setNumvalprogramejec(BigDecimal.ZERO);
        if (getObranueva().getSolicitud_obra() != null || getObranueva().getSolicitudmaestro() != null) {
            objeto = true;
        }
        preguntaProyectoPadre = 0;
        preguntaSubProyecto = 0;
        verContrato = false;
        tiproyectoselec = obranueva.getTipoobra().getTipoproyecto().getIntidtipoproyecto();
        listaLocalidades = new ArrayList<Localidad>();
        listaRegiones = new ArrayList<Region>();
        listacontratosobra = new ArrayList<Relacioncontratoobra>();
        marli = new ArrayList<Marcador>();
        listamarcadores = new ArrayList<Marcador>();
        verConfirmar = false;
        estadoguardado = 0;
        getObranueva().setEnalimentacion(false);
        getObranueva().setBoolincluyeaiu(false);
        listaBarrios = new ArrayList<Barrio>();
        listaVeredas = new ArrayList<Vereda>();
        tiahselect = getObranueva().getClaseobra().getFase().getIntidfase();
        cambioClaseObra(); // Llenar la lista de proyectos si filtra por clase obra, pues estaría vacía.
        tiproyectoselec = getObranueva().getTipoobra().getTipoproyecto().getIntidtipoproyecto();
        seleccionarsubtipo();
        subtiposelec = getObranueva().getTipoobra().getInttipoobra();
        sumValorContrato = BigDecimal.ZERO;
        navegacion = 0;
        llenarClaseObra(getObranueva().getClaseobra().getFase().getIntidfase());
        EncontrarSolicitante();
        ServletContext theApplicationsServletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        this.realArchivoPath = theApplicationsServletContext.getRealPath(URL);
        switch (getObranueva().getTipoorigen().getIntidtipoorigen()) {
            case 1:
            case 2:
                getListaLocalidades().addAll(getSessionBeanCobra().getCobraService().encontrarLocalidadPorObra(getObranueva()));
                listaRegiones = new ArrayList<Region>();
                break;
            case 3:
                getListaRegiones().addAll(getSessionBeanCobra().getCobraService().encontrarRegionesPorObra(getObranueva()));
                listaLocalidades = new ArrayList<Localidad>();
                break;
        }
        getListaBarrios().addAll(getSessionBeanCobra().getCobraService().obtenerBarriosObra(obranueva));
        getListaVeredas().addAll(getSessionBeanCobra().getCobraService().obtenerVeredasObra(obranueva));
        List<Puntoobra> lispuntoobrapropues = getSessionBeanCobra().getCobraService().encontrarPuntosObraxObra(getObranueva().getIntcodigoobra());
        getObranueva().setPuntoobras(new LinkedHashSet(lispuntoobrapropues));
        if (getObranueva() != null && getObranueva().getFloatlatitud() != null && getObranueva().getFloatlongitud() != null && getObranueva().getFloatlatitud().compareTo(BigDecimal.valueOf(0.000000)) != 0 && getObranueva().getFloatlongitud().compareTo(BigDecimal.valueOf(0.000000)) != 0) {
            if (getObranueva().getTipoestadobra().getIntestadoobra() == 0) {//si es obra propuesta
                PlaceMark placeMarkNewcargar = new PlaceMark();

                try {
                    placeMarkNewcargar = GMaps4JSFServiceFactory.getReverseGeocoderService().getPlaceMark(getObranueva().getFloatlatitud().toString(), getObranueva().getFloatlongitud().toString());
                    address = placeMarkNewcargar.getAddress();
                } catch (Exception e) {
                    address = "Faltante";
                }

                Marcador marca = new Marcador();
                marca.setAddress(address);

                //marca.setAddress(obranueva.getStrdireccion());
                marca.setLatitude(obranueva.getFloatlatitud().toString());
                marca.setLongitude(obranueva.getFloatlongitud().toString());
                marca.setAddress(obranueva.getStrdireccion().toString());
                marca.setDraggable("false");
                marca.setConverterMessage("/" + getSessionBeanCobra().getBundle().getString("versioncobra") + "/resources/images/marker.png");
                marca.setTipo(0);
                //marca.setObra(obranueva);
                listamarcadores.add(marca);
                marli.add(marca);
                verPrimario = false;
                tiposelec = 1;
                if (lispuntoobrapropues.size() > 0) {
                    for (int i = 0; i < lispuntoobrapropues.size(); i++) {
                        PlaceMark placeMarkNewcargarPunto = new PlaceMark();
                        try {
                            placeMarkNewcargarPunto = GMaps4JSFServiceFactory.getReverseGeocoderService().getPlaceMark(lispuntoobrapropues.get(i).getStrlatitud(), lispuntoobrapropues.get(i).getStrlongitud());
                            address = placeMarkNewcargarPunto.getAddress();
                        } catch (Exception e) {
                            address = "Faltante";
                        }
                        marca = new Marcador();
                        marca.setAddress(lispuntoobrapropues.get(i).getStrdireccion());
                        marca.setLatitude(lispuntoobrapropues.get(i).getStrlatitud());
                        marca.setLongitude(lispuntoobrapropues.get(i).getStrlongitud());
                        marca.setDraggable("false");
                        marca.setConverterMessage("/" + getSessionBeanCobra().getBundle().getString("versioncobra") + "/resources/images/marker.png");
                        marca.setTipo(1);
                        //marca.setObra(obranueva);
                        marli.add(marca);
                        listamarcadores.add(marca);
                    }
                }
                if (obra.getRuta() != null) {
                    getEntidadConvenio().setListapuntosruta(getSessionBeanCobra().getCobraService().encontrarPuntosReferenciaxRuta(obranueva.getRuta().getStrcodigotramo()));
                    verPrimario = false;
                }
            }
        }
        listacontratosobra = getSessionBeanCobra().getCobraService().obtenerRelacionesContratoxObra(obranueva.getIntcodigoobra());
        valido = 0;
        cargadorCronograma = new CargadorArchivosWeb();
        documentoseleccionado = 0;
        String path = "";
        if (obranueva.getStrimagenobra() != null) {
            pathImagen = obranueva.getStrimagenobra();
            datosbas = true;
            path = theApplicationsServletContext.getRealPath(pathImagen);
            String nombre = "";
            if (path.contains("%20")) {
                int i = 0;
                while (i < path.length()) {
                    if (String.valueOf(path.charAt(i)).compareTo("%") == 0) {
                        nombre = nombre + " ";
                        i = i + 2;
                    } else {
                        nombre = nombre + String.valueOf(path.charAt(i));
                    }
                    i++;
                }
            } else {
                nombre = path;
            }
            try {
                File imagen = new File(nombre);
                if (imagen.getTotalSpace() == 0) {
                    pathImagen = "/resources/imgs/bt_nodisponible.png";
                } else {
                }
            } catch (Exception ex) {
                Logger.getLogger(IngresarNuevaObra.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            pathImagen = "/resources/imgs/bt_nodisponible.png";
        }
        if (obranueva.getStrurlcronograma() != null) {
            try {
                cargadorCronograma.cargarArchivo(obranueva.getStrurlcronograma());
            } catch (ArchivoNoExistenteException ex) {
                FacesUtils.addErrorMessage(bundle.getString("archivonoexixtenteerror"));
                Logger.getLogger(IngresarNuevaObra.class.getName()).log(Level.SEVERE, null, ex);
            }
            llenarTiposCosto();
            validarCronograma();
        }
        img = false;
        imgppal = false;
        listaImagenesevolucionobra = getSessionBeanCobra().getCobraService().encontrarImagenesEvolucionObra(obranueva);
        if (listaImagenesevolucionobra.size() > 0) {
            int i = 0;
            while (i < listaImagenesevolucionobra.size()) {
                if (listaImagenesevolucionobra.get(i).getTipoimagen().getIntidtipoimagen() == 1) {
                    img = true;
                } else if (listaImagenesevolucionobra.get(i).getTipoimagen().getIntidtipoimagen() == 5) {
                    imgppal = true;
                }
                i++;
            }
        }
        llenarEntidadesContratantes();
        if (getObranueva().getContrato() != null) {
            setDesdeconvenio(true);
            if (getObranueva().getTercero().getIntcodigo() == 5212) {
                getEntidadConvenio().setIntentidadconvenio(1);
            }
            if (getObranueva().getTercero().getIntcodigo() == 5229) {
                getEntidadConvenio().setIntentidadconvenio(2);
            }
        }
        fechaCambio();
        chequiarFase(getTiahselect());
        chequiarTipoProyecto(getObranueva().getTipoobra().getTipoproyecto().getIntidtipoproyecto());
        chequiarTipoObra(getObranueva().getTipoobra().getInttipoobra());
        volverTipificacion();
    }

    /**
     * Se utiliza para validar el cronograma diligenciado por el usuario,
     * recorre todas las actividades suministradas por el usuario si estan
     * correctamente diligenciadas con el metodo {@link verificarActividad()}
     *
     * @return null
     */
    public String validarCronograma() {

        disableAiu = 1;
        valido = 2;
        actividadesobra = new LinkedHashSet<Actividadobra>();

        InputStream inp = null;
        File crono = null;
        try {
            if (cargadorCronograma.getNumArchivos() > 0 && cargadorCronograma.getArchivoWeb().getArchivoTmp() != null) {
                crono = cargadorCronograma.getArchivos().get(0).getArchivoTmp();
            } else {
                if (obranueva.getStrurlcronograma() != null) {
                    crono = new File(ArchivoWebUtil.obtenerRutaAbsoluta(obranueva.getStrurlcronograma()));
                }
            }

            if (crono != null) {
                inp = new FileInputStream(crono);

                Workbook wb;
                Row row = null;
                try {
                    wb = WorkbookFactory.create(inp);
                    Sheet sheet = wb.getSheetAt(0);
                    Cell celda = null;

                    ///Se cuentan las filas de la hoja para saber el numero de actividades
                    int numFilasDeActividadLlenas = 0;
                    int numFilasDeActividad = 0;

                    for (Iterator rit = sheet.rowIterator(); rit.hasNext();) {
                        row = (Row) rit.next();
                        if (row.getRowNum() >= 9 && row.getCell(2) != null && !row.getCell(2).getStringCellValue().equals("")) {
                            numFilasDeActividadLlenas++;
                        }
                        numFilasDeActividad++;
                    }

                    int i = numFilasDeActividadLlenas;
                    if (i > 0) {

                        //Verificar Datos de la Obra
                        row = sheet.getRow(2);
                        celda = row.getCell(2);
                        if (celda.getStringCellValue().compareTo(obranueva.getStrnombreobra()) != 0) {
                            valido = 2;
                            //"El nombre de la obra del archivo no corresponde a esta obra";
                            FacesUtils.addErrorMessage(bundle.getString("elnombredelaobra"));
                            return null;
                        }
                        row = sheet.getRow(3);
                        celda = row.getCell(2);
                        if (celda.getNumericCellValue() != obranueva.getIntplazoobra()) {
                            valido = 2;
                            //"El plazo en días del archivo no corresponde a esta obra";
                            FacesUtils.addErrorMessage(bundle.getString("elplazoendiasdelarchivonocorres"));
                            return null;
                        }
                        row = sheet.getRow(4);
                        celda = row.getCell(2);
                        if (celda.getDateCellValue().compareTo(obranueva.getDatefeciniobra()) != 0) {
                            valido = 2;
                            //"La fecha de inicio del archivo no corresponde a la consignada en la obra";
                            FacesUtils.addErrorMessage(bundle.getString("lafechadeiniciodelarchivo"));
                            return null;
                        }
                        row = sheet.getRow(5);
                        celda = row.getCell(2);
                        if (celda.getDateCellValue().compareTo(obranueva.getDatefecfinobra()) != 0) {
                            valido = 2;
                            //"La fecha de finalización del archivo no corresponde a la consignada en la obra";
                            FacesUtils.addErrorMessage(bundle.getString("lafechadefinalizaciondelarchivo"));
                            return null;
                        }
                        //Verificar Total
                        row = sheet.getRow(2);
                        celda = row.getCell(6);

                        if (celda.getCellType() != 2) {
                            valido = 2;
                            //"Ha sido modificada la formula de valor total";
                            FacesUtils.addErrorMessage(bundle.getString("hasidomodificada"));
                            return null;
                        } else {

                            try {

                                if (celda.getNumericCellValue() == 0) {
                                    valido = 2;
                                    //"El valor total de la obra no puede ser $0";
                                    FacesUtils.addErrorMessage(bundle.getString("elvalortotaldela"));
                                    return null;
                                } else {
                                    //verificar faltante
                                    row = sheet.getRow(3);
                                    celda = row.getCell(6);

                                    if (celda.getCellType() != 2) {
                                        valido = 2;
                                        //"Ha sido modificada la formula de valor faltante";
                                        FacesUtils.addErrorMessage(bundle.getString("hasidomodificadadevalorfaltante"));
                                        return null;
                                    } else {
                                        if (celda.getNumericCellValue() != 0) {
                                            valido = 2;
                                            //"El valor faltante por programar debe ser $0";FacesUtils.addErrorMessage(mensaje);
                                            FacesUtils.addErrorMessage(bundle.getString("elvalorfaltanteporprogra"));
                                            return null;
                                        } else {
                                            //Verificar Actividades diligenciadas
                                            //actividadesobra.clear();
                                            int j = 9, total = j + i;
                                            row = null;
                                            while (j < total) {
                                                row = sheet.getRow(j);
                                                celda = row.getCell(2);
                                                Cell celdauni = row.getCell(3);
                                                Cell celdacant = row.getCell(4);
                                                Cell celdavalor = row.getCell(5);

                                                if (!verificarActividad(celda.getStringCellValue(), celdauni.getStringCellValue(), celdacant.getNumericCellValue(), celdavalor.getNumericCellValue(), j + 1)) {
                                                    //actividadesobra.clear();
                                                    valido = 2;
                                                    return null;
                                                } else {

                                                    Cell celdafin = row.getCell(6);
                                                    if (celdafin.getCellType() != 2) {
                                                        valido = 2;
                                                        FacesUtils.addErrorMessage(bundle.getString("laformuladevalorfinal") + (j + 1));
                                                        return null;
                                                    } else {
                                                        if (celdavalor.getNumericCellValue() * celdacant.getNumericCellValue() != celdafin.getNumericCellValue()) {
                                                            valido = 2;
                                                            //"La fórmula de valor final ha sido modifcada en la fila " + (j + 1);
                                                            FacesUtils.addErrorMessage(bundle.getString("laformuladevalorfinalhasidomodi") + (j + 1));
                                                            return null;
                                                        }
                                                    }
                                                }
                                                celdavalor = row.getCell(7);
                                                if (celdavalor.getCellType() != 2) {
                                                    valido = 2;
                                                    //"La fórmula de valor faltante ha sido modificada en la fila " + (j + 1);
                                                    FacesUtils.addErrorMessage(bundle.getString("lafomruladevalorfaltante") + (j + 1));
                                                    return null;
                                                } else {
                                                    if (celdavalor.getNumericCellValue() != 0) {
                                                        valido = 2;
                                                        FacesUtils.addErrorMessage(bundle.getString("elvalorfaltantepoprogr") + (j + 1) + bundle.getString("debesercero"));
                                                        //mensaje = "El valor faltante por programar en la fila " + (j + 1) + " debe ser cero";
                                                        return null;
                                                    }
                                                }
                                                j++;
                                            }
                                            int indiceperiodo = obranueva.getPeriodomedida().getIntidperiomedida();
                                            int division = 0;

                                            switch (indiceperiodo) {
                                                case 1:
                                                    division = obranueva.getIntplazoobra() / 7;

                                                    if (obranueva.getIntplazoobra() % 7 != 0) {
                                                        division = division + 1;
                                                    }
                                                    obranueva.getPeriodomedida().setIntnrodiasperiomedida(7);
                                                    break;
                                                case 2:

                                                    division = obranueva.getIntplazoobra() / 14;

                                                    if (obranueva.getIntplazoobra() % 14 != 0) {
                                                        division = division + 1;
                                                    }
                                                    obranueva.getPeriodomedida().setIntnrodiasperiomedida(14);
                                                    break;
                                                case 3:

                                                    division = obranueva.getIntplazoobra() / 30;

                                                    if (obranueva.getIntplazoobra() % 30 != 0) {
                                                        division = division + 1;
                                                    }
                                                    obranueva.getPeriodomedida().setIntnrodiasperiomedida(30);
                                                    break;
                                            }

                                            ///Conteo de periodos
                                            i = 0;
                                            row = sheet.getRow(8);
                                            for (Iterator cit = row.cellIterator(); cit.hasNext();) {
                                                cit.next();
                                                i++;
                                            }

                                            int totalcol = (i - 8);
                                            if (totalcol != division) {
                                                //"Error en número de periodos";
                                                FacesUtils.addErrorMessage(bundle.getString("errorennumerodeperiodos"));
                                                valido = 2;
                                                return null;
                                            } else {
                                                //verificar la suma de totales

                                                int filas = 9;
                                                int u = 1;
                                                int h = 5;
                                                BigDecimal suma = BigDecimal.valueOf(0);
                                                while (u <= numFilasDeActividadLlenas) {
                                                    row = sheet.getRow(filas);
                                                    j = 8;

                                                    while (j <= (i - 1)) {
                                                        Cell celdatotal = row.getCell(j);
                                                        Cell celdaMultipli = row.getCell(h);
                                                        //if (celdatotal.getCellType() != 0) {

                                                        if (celdatotal != null) {

                                                            if (celdatotal.getNumericCellValue() == 0) {
                                                                //suma = suma.add(BigDecimal.valueOf(celdaMultipli.getNumericCellValue() * 0).setScale(2, BigDecimal.ROUND_HALF_UP));
                                                            } else {
                                                                suma = suma.add(BigDecimal.valueOf(celdaMultipli.getNumericCellValue() * celdatotal.getNumericCellValue()).setScale(2, BigDecimal.ROUND_HALF_UP));
                                                            }
                                                        }

//                                                } else {
//                                                    FacesUtils.addErrorMessage(bundle.getString("errorcorno") + " " + celdatotal.getRowIndex());
//                                                    valido = 2;
//                                                    return null;
//                                                }
                                                        j++;
                                                    }

//                                                obranueva.getActividadobras().add();
                                                    filas++;
                                                    u++;
                                                }

                                                if (suma.compareTo(BigDecimal.valueOf(0)) == 0) {
                                                    //"ERROR LA SUMA DE LOS TOTALES ES O";
                                                    FacesUtils.addErrorMessage(bundle.getString("errorlasumadelostotaleseso"));
                                                    valido = 2;
                                                    return null;
                                                }
                                                ///comparar suma con total del archivo que sean iguales
//                                            row = sheet.getRow(2);
//                                            celda = row.getCell(6);
//
//                                            NumberFormat formatter = new DecimalFormat("000000");
//                                            formatter = new DecimalFormat(".");
//
//                                            if (formatter.format(suma).compareTo(formatter.format(BigDecimal.valueOf(celda.getNumericCellValue()).setScale(2, BigDecimal.ROUND_HALF_UP))) != 0) {
//                                                //"ERROR LA SUMA DE LOS TOTALES DE LOS PERIODOS NO COINCIDE CON EL VALOR TOTAL DE LA OBRA";
//                                                FacesUtils.addErrorMessage(bundle.getString("errorlasumadelostotales"));
//                                                valido = 2;
//                                                return null;
//                                            }
                                                obranueva.setNumvaltotobra(suma);
                                                ///LLenar Matrix con los datos provenientes del excell
                                                //llenar periodos

                                                Calendar fecha = Calendar.getInstance();
                                                fecha.setTime(obranueva.getDatefeciniobra());

                                                h = 5;
                                                int k = 0;
                                                j = 8;
                                                //row = sheet.getRow(9);
                                                filas = 9;
                                                while (j <= (i - 1)) {
                                                    filas = 9;
                                                    row = sheet.getRow(filas);
                                                    //Cell celdatotal = row.getCell(j);

                                                    //if (celdatotal != null) {
                                                    Periodo pe = new Periodo();
                                                    pe.setDatefeciniperiodo(fecha.getTime());
                                                    fecha.add(Calendar.DATE, +(obranueva.getPeriodomedida().getIntnrodiasperiomedida() - 1));
                                                    if (fecha.getTime().compareTo(obranueva.getDatefecfinobra()) > 0) {
                                                        pe.setDatefecfinperiodo(obranueva.getDatefecfinobra());
                                                    } else {
                                                        pe.setDatefecfinperiodo(fecha.getTime());
                                                    }
                                                    BigDecimal sumaperiodos = BigDecimal.ZERO;
                                                    u = 1;
                                                    while (u <= numFilasDeActividadLlenas) {
                                                        row = sheet.getRow(filas);
                                                        Cell celdavalor = row.getCell(j);
                                                        Cell celdaMultipli = row.getCell(h);
                                                        if (celdavalor != null) {
                                                            BigDecimal periodoaiu = BigDecimal.valueOf(celdaMultipli.getNumericCellValue() * celdavalor.getNumericCellValue());
                                                            sumaperiodos = sumaperiodos.add(periodoaiu);
                                                        }
                                                        filas++;
                                                        u++;
                                                    }
                                                    pe.setNumvaltotplanif(sumaperiodos);
                                                    pe.setObra(obranueva);
                                                    listadoperiodos[k] = pe;
                                                    fecha.add(Calendar.DATE, +1);
                                                    k++;
                                                    j++;
                                                }
                                                ///LLenar actividades obra
                                                j = 9;

                                                row = null;
                                                actividadesobra.clear();

                                                while (j < total) {
                                                    row = sheet.getRow(j);
                                                    celda = row.getCell(2);
                                                    Cell celdaacttiobra = row.getCell(1);
                                                    Cell celdauni = row.getCell(3);
                                                    Cell celdacant = row.getCell(4);
                                                    Cell celdavalor = row.getCell(5);
                                                    Actividadobra act = new Actividadobra();
                                                    act.setStrdescactividad(celda.getStringCellValue());
                                                    act.setNumvalorplanifao(BigDecimal.valueOf(celdavalor.getNumericCellValue()));
                                                    act.setFloatcantplanifao(celdacant.getNumericCellValue());
                                                    act.setObra(obranueva);
                                                    act.setStrtipounidadmed(celdauni.getStringCellValue());
                                                    act.setJsfUsuario(getSessionBeanCobra().getUsuarioObra());
                                                    act.setFloatcantidadejecutao(Double.parseDouble("0"));
                                                    act.setNumvalorejecutao(BigDecimal.valueOf(0));
                                                    act.setNumvalorejecutao(BigDecimal.valueOf(0));
                                                    if (celdaacttiobra.getCellType() == 1) {
                                                        celdaacttiobra.getStringCellValue();
                                                        List<Bdpu> lsbdBdpus = getSessionBeanCobra().getCobraService().encontrarBdpuxNombre(celdaacttiobra.getStringCellValue());
                                                        if (lsbdBdpus.size() > 0) {
                                                            act.setBdpu(lsbdBdpus.get(0));
                                                            act.setStrcodcubs(lsbdBdpus.get(0).getStrdescripcion());
                                                            // --aca leyende el excel
                                                        }
                                                    }
                                                    act.setBoolimprevisto(false);
                                                    if (act.getFloatcantplanifao() == 0) {
                                                        act.setBoolimprevisto(true);
                                                    }

                                                    actividadesobra.add(act);

                                                    k = 8;
                                                    i = 0;

                                                    while (i < division) {

                                                        celda = row.getCell(k);
                                                        Relacionactividadobraperiodo relacion = new Relacionactividadobraperiodo();
                                                        if (celda == null) {
                                                            relacion.setFloatcantplanif(0);
                                                            relacion.setNumvalplanif(BigDecimal.valueOf(0));
                                                        } else {
                                                            relacion.setFloatcantplanif(celda.getNumericCellValue());
                                                            relacion.setNumvalplanif(BigDecimal.valueOf(celda.getNumericCellValue()).multiply(act.getNumvalorplanifao()));
                                                        }
//                                                    //celda = row.getCell(k + 1);
//                                                    if (celda == null) {
//                                                        relacion.setNumvalplanif(BigDecimal.valueOf(0));
//
//                                                    } else {
//                                                        relacion.setNumvalplanif(BigDecimal.valueOf(celda.getNumericCellValue()));
//                                                    }

                                                        relacion.setActividadobra(act);
                                                        relacion.setPeriodo(listadoperiodos[i]);
                                                        listadoperiodos[i].getRelacionactividadobraperiodos().add(relacion);

                                                        i++;

                                                        k++;
                                                    }

                                                    j++;
                                                }

                                            }
                                            valido = 1;
                                            estado = 1;

                                            llenarTiposCosto();
                                            valortotalobra = obranueva.getNumvaltotobra();
                                            FacesUtils.addInfoMessage(bundle.getString("archivovalido"));
                                            setDatosValidos(1);
                                            validarCostos();
                                        }
                                    }
                                }
                            } catch (java.lang.IllegalStateException e) {
                                //"Fórmula con error";

                                FacesUtils.addErrorMessage(bundle.getString("formulaconerror"));
                            }
                        }

                    } else {
                        //+"EL ARCHIVO NO TIENE ACTIVIDADES";
                        FacesUtils.addErrorMessage(bundle.getString("elarchivonotieneactivi"));
                        valido = 2;
                        return null;
                    }

                } catch (Exception ex) {

                    Logger.getLogger(IngresarNuevaObra.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } catch (FileNotFoundException ex) {
            FacesUtils.addErrorMessage(bundle.getString("archivonoexixtenteerror"));
            Logger.getLogger(IngresarNuevaObra.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (inp != null) {
                    inp.close();
                }
            } catch (IOException ex) {
                System.out.println("ex = " + ex);
                Logger.getLogger(IngresarNuevaObra.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        setDatosValidos(1);
        System.out.println(getDatosValidos());
        return null;
    }

    /**
     * Se utiliza para verificar cada una de las actividades diligenciadas en el
     * cronograma (.xls) suministrado por el usuario.
     *
     * @param desc El contenido de la celda a verificar.
     * @param unidad Unidad de medidad.
     * @param cantidad Cantidad de la actividad.
     * @param valoruni Valor de la actividad.
     * @param fila Número de la fila.
     * @return si la actividad esta bien diligenciada retorna True de lo
     * contrario False
     */
    public boolean verificarActividad(String desc, String unidad, Double cantidad, Double valoruni, int fila) {
        if (desc.compareTo("") == 0 || unidad.compareTo("") == 0) {
            FacesUtils.addErrorMessage("Debe llenar los datos de la actividad en la fila " + fila);
            return false;
        }
        if (desc.length() > 255) {
            FacesUtils.addErrorMessage("La actividad en la fila " + fila + " posee más de 255 caracteres");
            return false;
        }
        if (unidad.length() > 10) {
            mensaje = "La Unidad de medida en la fila " + fila + " posee más de 10 caracteres";
            FacesUtils.addErrorMessage(mensaje);
            return false;
        }
        if (valoruni <= 0) {
            //FacesUtils.addErrorMessage("Los valores en la fila " + fila + " no pueden ser menores o iguales a cero");
            FacesUtils.addErrorMessage("Los valores en la fila " + fila + " no pueden ser menores o iguales a cero");
            return false;
        }
        if (cantidad < 0) {
            FacesUtils.addErrorMessage("Los valores en la fila " + fila + " no pueden ser menores a cero");
            return false;
        }
        return true;
    }

    /**
     * El metodo se utiliza para aplicarle el AIU a las actividades del
     * cronograma
     *
     * @return null
     */
    public String validarCostos() {
        if (obranueva.getTipocosto().getInttipocosto() == 2) {
            obranueva.setFloatporimprevi(0);
            obranueva.setFloatporutilidad(0);
        }
        valortotalobra = obranueva.getNumvaltotobra();
        BigDecimal valortotalsuma = BigDecimal.valueOf(0);
        BigDecimal operacionutilidad = new BigDecimal(obranueva.getFloatporutilidad());
        BigDecimal valorutilidad = operacionutilidad.divide(BigDecimal.valueOf(100));
        BigDecimal operacionadministracion = new BigDecimal(obranueva.getFloatporadmon());
        BigDecimal valoradministracion = operacionadministracion.divide(BigDecimal.valueOf(100));
        BigDecimal operacionivasobreutilidad = new BigDecimal(obranueva.getFloatporivasobreutil());
        operacionivasobreutilidad = operacionivasobreutilidad.divide(BigDecimal.valueOf(100));
        switch (obranueva.getTipocosto().getInttipocosto()) {
            case 1:
                operacionivasobreutilidad = operacionivasobreutilidad.multiply(valorutilidad);
                break;
            case 2:
                operacionivasobreutilidad = operacionivasobreutilidad.multiply(valoradministracion);
                break;
        }
        BigDecimal operacionimprevistos = new BigDecimal(obranueva.getFloatporimprevi());
        BigDecimal valorimprevistos = operacionimprevistos.divide(BigDecimal.valueOf(100));
        BigDecimal operacionvalorotros = new BigDecimal(obranueva.getFloatporotros());
        BigDecimal valorotros = operacionvalorotros.divide(BigDecimal.valueOf(100));
        if (obranueva.getBoolincluyeaiu() == false) {
            valortotalsuma = valortotalsuma.add(valortotalobra.multiply(operacionivasobreutilidad));
            valortotalsuma = valortotalsuma.add(valortotalobra.multiply(valorutilidad));
            valortotalsuma = valortotalsuma.add(valortotalobra.multiply(valorimprevistos));
            valortotalsuma = valortotalsuma.add(valortotalobra.multiply(valoradministracion));
            valortotalsuma = valortotalsuma.add(valortotalobra.multiply(valorotros));
        }
        valortotalobra = valortotalobra.add(valortotalsuma);
        if (obranueva.getObra() != null) {
            if (valortotalobra.compareTo(obranueva.getObra().getNumvaltotobra().subtract(obranueva.getObra().getNumvlrsumahijos())) > 0) {
                FacesUtils.addErrorMessage(bundle.getString("valorobradesbordadoerror"));
            }
            if (valortotalobra.compareTo(BigDecimal.ZERO) == 0) {
                FacesUtils.addErrorMessage(bundle.getString("elvalortotaldela"));
            }
        }
        return null;
    }

    /**
     * El metodo validarDatosBasicos se utiliza para validar el Primer paso del
     * diligenciamiento de un Proyecto
     *
     * @return null
     */
    public String validarDatosBasicos() {
        datosbas = false;
        if (obranueva.getStrnombreobra() != null && obranueva.getStrnombreobra().compareTo("") != 0 && obranueva.getStrobjetoobra() != null && obranueva.getStrobjetoobra().compareTo("") != 0 && obranueva.getDatefeciniobra() != null && obranueva.getDatefecfinobra() != null) {

            if (obranueva.getDatefeciniobra() != null && obranueva.getDatefecfinobra() != null) {

                if (obranueva.getDatefeciniobra().compareTo(obranueva.getDatefecfinobra()) <= 0) {

                    if (listaLocalidades.size() > 0 || listaRegiones.size() > 0 || obranueva.getTipoorigen().getIntidtipoorigen() == 4) {

                        datosbas = true;
                    } else {
                        //"DEBE DILIGENCIAR TODOS LOS DATOS REQUERIDOS EN EL PASO 1";                        

                        FacesUtils.addErrorMessage(bundle.getString("debediligenciarlocalregion"));
                        datosbas = false;
                    }
                } else {
                    //"DEBE DILIGENCIAR TODOS LOS DATOS REQUERIDOS EN EL PASO 1";
                    FacesUtils.addErrorMessage(bundle.getString("lafechadefinalizaciondebeser"));
                    datosbas = false;
                }
            } else {
                //"DEBE DILIGENCIAR TODOS LOS DATOS REQUERIDOS EN EL PASO 1";
                FacesUtils.addErrorMessage(bundle.getString("debellenarrangodefechas"));
                datosbas = false;
            }
        } else {
            //"DEBE LLENAR LOS DATOS DE LA OBRA";
            FacesUtils.addErrorMessage(bundle.getString("debediligenciartodoslosdatos"));
            datosbas = false;
        }
        return null;
    }

    /**
     * Como su nombre lo indica, se utiliza para guardar un proyecto
     * temporalmente o en estado incompleto
     *
     * @return null
     */
    public String guardarObraTemporal() {
        if (obranueva.getIntcodigoobra() == 0) {
            obranueva.setJsfUsuario(getSessionBeanCobra().getUsuarioObra());
        }
        if (obranueva.getStrnombreobra() != null && obranueva.getStrnombreobra().compareTo("") != 0) {
            if (obranueva.getPeriodomedida() == null || obranueva.getPeriodomedida().getIntidperiomedida() == 0) {
                obranueva.setPeriodomedida(new Periodomedida(1, "", 0, 0, 0));
            }
            obranueva.setStrimagenobra(RutasWebArchivos.IMG_NO_DISPONIBLE);
            obranueva.setLocalidads(new LinkedHashSet());
            obranueva.setRegions(new LinkedHashSet());
            obranueva.setTipoestadobra(new Tipoestadobra(0));
            switch (obranueva.getTipoorigen().getIntidtipoorigen()) {
                case 1:
                case 2:
                    obranueva.setLocalidads(new LinkedHashSet(listaLocalidades));
                    break;
                case 3:
                    obranueva.setRegions(new LinkedHashSet(listaRegiones));
                    break;
            }
            if (Boolean.parseBoolean(getSessionBeanCobra().getBundle().getString("habilitacionLocalidadesBarrioVereda"))) {
                obranueva.setBarrios(new LinkedHashSet(listaBarrios));
                obranueva.setVeredas(new LinkedHashSet(listaVeredas));
            } else {
                obranueva.setBarrios(new LinkedHashSet());
                obranueva.setVeredas(new LinkedHashSet());
            }

            obranueva.setRelacioncontratoobras(new LinkedHashSet());
            obranueva.getRelacioncontratoobras().clear();
            if (listacontratosobra.size() > 0) {
                for (Relacioncontratoobra cont : listacontratosobra) {
                    obranueva.getRelacioncontratoobras().add(cont);
                }
            }
            cargarPuntosaobra();
            guardarCronogramaTemporal();
            if (obranueva.getIntcodigoobra() == 0) {

                // Asociar un usuario a la tabla relacionobrajsfusuario.
                if (getSessionBeanCobra().getUsuarioObra().getTipousuario().getIntcodigotipousuario() == 3) {
                    getObranueva().setJsfUsuarios(new LinkedHashSet());
                    getObranueva().getJsfUsuarios().add(getSessionBeanCobra().getUsuarioObra());
                }

                getSessionBeanCobra().getCobraService().guardarObra(obranueva, getSessionBeanCobra().getUsuarioObra(), -1);
            }
            guardarImagenesaobra(RutasWebArchivos.IMGS_OBRA_PROCESO);
            getSessionBeanCobra().getCobraService().guardarObra(obranueva, getSessionBeanCobra().getUsuarioObra(), -1);
            obranueva.getBarrios().clear();
            obranueva.getVeredas().clear();
            FacesUtils.addInfoMessage(bundle.getString("losdatosdelaobrasehanguardao"));
        } else {
            //"Debe dar un nombre a la obra";
            FacesUtils.addErrorMessage(bundle.getString("debedarunnombraalaobra"));
            datosbas = false;
        }
        return null;
    }

    /**
     * Se utiliza despues de haber validado el documento
     * ({@link validarCronograma()}) se guarda en un carpeta temporalmente.
     */
    public void guardarCronogramaTemporal() {
        String ubicacionArchivoDestino = MessageFormat.format(RutasWebArchivos.DOCS_OBRA_PROCESO, "" + obranueva.getIntcodigoobra());
        if (cargadorCronograma.getNumArchivos() > 0 && valido == 1) {
            try {
                cargadorCronograma.guardarArchivosTemporales(ubicacionArchivoDestino, true);
            } catch (ArchivoExistenteException ex) {
                Logger.getLogger(IngresarNuevaObra.class.getName()).log(Level.SEVERE, null, ex);
            }
            obranueva.setStrurlcronograma(cargadorCronograma.getArchivos().get(0).getRutaWeb());
            if (!cargadorCronograma.getArchivoWeb().getRutaWeb().substring(0, cargadorCronograma.getArchivoWeb().getRutaWeb().lastIndexOf("/") + 1).equals(ubicacionArchivoDestino)) {
                cargadorCronograma.borrarDatosSubidos();
            }
        }
    }

    /**
     * Guarda en cronograma de la obra
     */
    public void guardarCronograma() {
        String carpetaDestino = MessageFormat.format(RutasWebArchivos.DOCS_OBRA, "" + obranueva.getIntcodigoobra());
        if (cargadorCronograma.getNumArchivos() > 0 && cargadorCronograma.getArchivoWeb().getArchivoTmp() != null && valido == 1) {
            try {
                cargadorCronograma.guardarArchivosTemporales(carpetaDestino, true);
            } catch (ArchivoExistenteException ex) {
                Logger.getLogger(IngresarNuevaObra.class.getName()).log(Level.SEVERE, null, ex);
            }
            obranueva.setStrurlcronograma(cargadorCronograma.getArchivos().get(0).getRutaWeb());
        } else {
            String rutaDestino = carpetaDestino + obranueva.getStrurlcronograma().substring(obranueva.getStrurlcronograma().lastIndexOf("/") + 1);
            try {
                ArchivoWebUtil.copiarArchivo(obranueva.getStrurlcronograma(), rutaDestino, true, true);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(IngresarNuevaObra.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ArchivoExistenteException ex) {
                Logger.getLogger(IngresarNuevaObra.class.getName()).log(Level.SEVERE, null, ex);
            }
            obranueva.setStrurlcronograma(rutaDestino);
        }
    }

    /**
     * Se utiliza para validar algunos requerimientos que se necesita para
     * guardar el proyecto en estado incompleto se invoca el metodo
     * ({@link guardarObraTemporal()})
     *
     * @return null
     */
    public String guardadoIntermedio() {
        subtiposelec = obranueva.getTipoobra().getInttipoobra();
        tiahselect = faseelegida.getIntidfase();
        tiproyectoselec = obranueva.getTipoobra().getTipoproyecto().getIntidtipoproyecto();
        if (comboEntidadesObraguardar()) {
            if (validarTipificacion()) {
                if(obranueva.getDatefeciniobra()==null && obranueva.getDatefecfinobra()==null)
                {
//                    fechas
//                }    
//                if (fechasvalidas) {
                    obranueva.setTipoestadobra(new Tipoestadobra(0));
                    guardarObraTemporal();
                } else {
                    if(obranueva.getDatefeciniobra()==null || obranueva.getDatefecfinobra()==null)
                    {
                        obranueva.setTipoestadobra(new Tipoestadobra(0));
                        guardarObraTemporal();
                    }
                    else
                    {    
                        fechaCambio();
                        if(fechasvalidas)
                        {
                            obranueva.setTipoestadobra(new Tipoestadobra(0));
                            guardarObraTemporal();
                        }
                    }
                    //return "nu";
                }
            } else {
                //"Debe dar un nombre a la obra";
                FacesUtils.addErrorMessage("Debe seleccionar un tipo de obra.");

                return volverTipificacion();
            }
        } else {
            FacesUtils.addErrorMessage("Debe seleccionar una entidad contratante válida.");
            return "datosbasicosnuevoproyecto";

        }
        return null;

    }

    /**
     * Se utiliza en el momento de guardar un nuevo punto de referenciacion para
     * el proyecto
     */
    public void confirmarDireccion() {
        if (latNewmanu != null && longNewmanu != null) {
            obranueva.setStrdireccion(address);
            obranueva.setFloatlatitud(new BigDecimal(latNewmanu));
            obranueva.setFloatlongitud(new BigDecimal(longNewmanu));
        }
        zoom = "6";
    }

    /**
     * Se cargan los contratos de un Proyecto siempre y cuando se cumpla la
     * condicion ({@link comboEntidadesObraguardar()})
     *
     * @return null
     */
    public String llenarContratos() {
        if (comboEntidadesObraguardar()) {
            filtrocontrato.setBooltienehijo(false);
            filtrocontrato.setBooltipocontconv(false);
            filtrocontrato.setBoolcontrconsultoria(false);
            if (obranueva.getContrato() != null) {
                filtrocontrato.setInconvenio(obranueva.getContrato().getIntidcontrato());
            }
            primeroContratos();
        } else {
            obranueva.getTercero().setIntcodigo(0);
        }
        return null;
    }

    /**
     * Nos envia a crear un contrato para el Proyecto en creación
     *
     * @return Regla de navegacion "nuevoContrato", siempre y cuando el nombre
     * del proyecto no sea null
     */
    public String crearAsociarContrato() {
        getSessionBeanCobra().getCobraService().setAsoContratoCrear(true);
        getNuevoContratoBasico().limpiarContrato();
        getNuevoContratoBasico().getContrato().setTercero(obranueva.getTercero());
        if (obranueva.getSolicitud_obra() != null) {
            getNuevoContratoBasico().setEncargofiduciario(obranueva.getSolicitud_obra().getEncargofiduciario());
        }
        if (obranueva.getSolicitudmaestro() != null) {
            getNuevoContratoBasico().setEncargofiduciario(obranueva.getSolicitudmaestro().getEncargofiduciario());
        }
        if (obranueva.getStrnombreobra() != null && obranueva.getStrnombreobra().compareTo("") != 0) {
            guardarObraTemporal();
            getSessionBeanCobra().getCobraService().setProyectoContrato(obranueva);
            return "nuevoContrato";
        } else {
            return null;
        }
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

    public void validarYesOrNo() {
//        if (decision) {
//            llenarConvenios();
//        } else {
//            this.obranueva.setConvenio(null);
//        }
    }

    public void validarConvenio() {
//        if (decisionConvenio == 1) {
//            llenarConvenios();
//        } else {
//            this.obranueva.setConvenio(null);
//        }
    }

    public void seleccionarConvenio() {
//
//        for (Convenio con : convenios) {
//
//            if (con.getIntidconvenio() == this.idConvenio) {
//                this.obranueva.setConvenio(con);
//                break;
//            }
//        }
    }

    public String limpiarMensaje() {
        return null;
    }

    /**
     * Se utiliza para indicar que el proyecto en creación pertenece a un
     * proyecto Padre o Mayor
     *
     * @return null
     */
    public String seleccionarProyectoPadre() {
        obrapadre = (Obra) tablaObrasPadres.getRowData();
        obranueva.setObra(obrapadre);
        verObrasPadres = true;
        return null;
    }

    /**
     * Se utiliza para comprobar si el el costo de el proyecto no sobrapasa el
     * costo de el Contrato y cuanto quedaria disponible
     *
     * @param vlrobra Valor del Proyecto
     * @param valorcontrato Valor del Contrto
     * @param valorhijos Valor de la Suma de los Proyectos Hijos
     * @return Si el valor esta del proyecto esta en el rango de contrato
     * retorna True o de lo contrario False
     */
    public boolean hallarDispo(BigDecimal vlrobra, BigDecimal valorcontrato, BigDecimal valorhijos) {
        BigDecimal disponible = valorcontrato.subtract(valorhijos);
        if (vlrobra.compareTo(disponible) <= 0) {
            return true;
        }
        return false;
    }

    /**
     * Se utiliza para encontrar el numero de contrato en la lista de contratos
     * asociados al proyecto actual.
     *
     * @param numcont Numero del contrato
     * @return si el contrato es encontrado retorna False de lo contrario True
     */
    public boolean verificarContrato(String numcont) {
        if (listacontratosobra.size() > 0) {
            for (int i = 0; i < listacontratosobra.size(); i++) {
                if (listacontratosobra.get(i).getContrato().getStrnumcontrato().equals(numcont)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Como su nombre lo indica es agregar un contrato al proyecto que se esta
     * diligenciando actualmente, si solo si ya no esta asociado
     *
     * @return null
     */
    public String agregarContrato() {
        relacioncontrato.setNumvalordisponible(BigDecimal.ZERO);
        BigDecimal valorlistacontratoobra = BigDecimal.ZERO;
        listacontratosobra.addAll(getSessionBeanCobra().getCobraService().encontrarRelacionContratosObra(getAdministrarObraNew().getObra().getIntcodigoobra(), false));
        for (Relacioncontratoobra contratoobta : listacontratosobra) {
            valorlistacontratoobra = valorlistacontratoobra.add(contratoobta.getNumvalorrelacion());
        }
        valorfaltanteasociarcontrato = obranueva.getNumvaltotobra().subtract(valorlistacontratoobra);
        Contrato contselec = (Contrato) tablacontratos.getRowData();
//        Contrato contselec = listacontratos.get(filaSeleccionada);
        relacioncontrato = new Relacioncontratoobra();
        relacioncontrato.setContrato(contselec);
        relacioncontrato.setObra(obranueva);
        relacioncontrato.setNumvalordisponible(contselec.getNumvlrcontrato().subtract(contselec.getNumvlrsumahijos().add(contselec.getNumvlrsumaproyectos())));
        if (valortotalobra.compareTo(BigDecimal.ZERO) == 0) {
            relacioncontrato.setMensaje(bundle.getString("proyectonotienecronograma"));
            FacesUtils.addErrorMessage(bundle.getString("proyectonotienecronograma"));
            return null;
        }
        if (relacioncontrato.getNumvalordisponible().compareTo(BigDecimal.ZERO) <= 0) {
            FacesUtils.addErrorMessage(bundle.getString("contratonotienedisponibilidad"));
            relacioncontrato.setMensaje(bundle.getString("contratonotienedisponibilidad"));
            return null;
        }
        if (verificarContrato(contselec.getStrnumcontrato())) {
            BigDecimal faltanteasociar = valortotalobra.subtract(sumValorContrato);
            if (faltanteasociar.compareTo(relacioncontrato.getNumvalordisponible()) <= 0) {
                relacioncontrato.setNumvalorrelacion(faltanteasociar);
                relacioncontrato.setNumvalormaximo(faltanteasociar);
            } else {
                relacioncontrato.setNumvalorrelacion(relacioncontrato.getNumvalordisponible());
                relacioncontrato.setNumvalormaximo(relacioncontrato.getNumvalordisponible());
            }
        } else {
            relacioncontrato.setMensaje(bundle.getString("contratoestarelacionado"));
            FacesUtils.addErrorMessage(bundle.getString("contratoestarelacionado"));
            relacioncontrato.setNumvalormaximo(BigDecimal.ZERO);
        }
        return null;
    }

    /**
     * Agregar a la listacontratosobra el contrato asociado y realizar la suma
     * al contrato y luego ({@link guardarObraTemporal()})
     *
     * @return null
     */
    public String asociarContrato() {
        listacontratosobra.add(relacioncontrato);
        sumValorContrato = sumValorContrato.add(relacioncontrato.getNumvalorrelacion());
        guardarObraTemporal();
        return null;
    }

    /**
     * Remover el objeto de contrato de la lista de contratos asociados a actual
     * proyecto y luego ({@link guardarObraTemporal()})
     *
     * @return null
     */
    public String desasociarContrato() {
        Relacioncontratoobra contselec = (Relacioncontratoobra) tablalistacontratos.getRowData();
//        IngresarNuevaObra ign = (IngresarNuevaObra) FacesUtils.getManagedBean("Supervisor$IngresarNuevaObra");
//        Relacioncontratoobra contselec = ign.getListacontratosobra().get(filaSeleccionada);
        listacontratosobra.remove(contselec);
        if (listacontratosobra.size() == 0) {
            validacion.setContratos(false);
        }
        sumValorContrato = sumValorContrato.subtract(contselec.getNumvalorrelacion());
        getSessionBeanCobra().getCobraService().borrarRelacionContrato(contselec);
        guardarObraTemporal();
        return null;
    }

    /**
     * Llenado de selectitem TipoOrigenes.
     */
    public void llenarTiposorigenes() {
        List<Tipoorigen> lista = getSessionBeanCobra().getCobraService().encontrarTiposOrigenes();
        TipoOrigenes = new SelectItem[lista.size() - 1];
        int i = 0;
        for (Tipoorigen tipo : lista) {
            SelectItem tpo = new SelectItem(tipo.getIntidtipoorigen(), tipo.getStrnombre());
            if (tipo.getIntidtipoorigen() != 3) {
                if (i == 0) {
                    obranueva.setTipoorigen(new Tipoorigen(tipo.getIntidtipoorigen(), ""));
                }
                TipoOrigenes[i++] = tpo;

            }
        }
        if (lista.size() == 1) {

            ingresaraModalLocalidad();
        }
    }

    /**
     * Llenado de suggestion box de acuerdo a la entidad correspondiente al
     * Proyecto
     */
    public void llenarEntidadesContratantes() {
        getEntidadConvenio().setIntentidadconvenio(0);
        listerentidad = getSessionBeanCobra().getUsuarioService().encontrarEntidadesxtercero(getSessionBeanCobra().getUsuarioObra(), 6, false);
        TerceroOption = new SelectItem[listerentidad.size()];
        int i = 0;
        SelectItem itemTercero = new SelectItem();
        for (Tercero ter : listerentidad) {

//              if ((ter.getLocalidadByStrcodigolocalidad().getStrcodigolocalidad().length() > 5)) {
            // itemTercero = new SelectItem(ter.getStrnombrecompleto() + "-" + ter.getLocalidadByStrcodigolocalidad().getStrdepartamento());
//                }else{
//                      itemTercero = new SelectItem(ter.getStrnombrecompleto());
//                }
            itemTercero = new SelectItem(ter.getStrnombrecompleto());

            //Lineas que capturar el id entidad para suggestion box
            TerceroEntidadLista terce = new TerceroEntidadLista(ter.getIntcodigo(), itemTercero.getValue().toString());
            temp.add(terce);
            TerceroOption[i++] = itemTercero;
        }

        if (listerentidad.size() == 1) {
            obranueva.setTercero(listerentidad.get(0));
            setDesdeconvenio(false);
        }

    }

    /**
     * Llenado de selectitem TipoTerceroOPtion.
     */
    public void llenarTiposTerceros() {
        List<Tipotercero> listatt = getSessionBeanCobra().getCobraService().encontrarTiposTercero();
        TipoTerceroOPtion = new SelectItem[listatt.size()];
        int i = 0;
        for (Tipotercero tipoter : listatt) {
            SelectItem itemTipot = new SelectItem(tipoter.getIntcodigotipotercero(), tipoter.getStrnombretipotercero());
            TipoTerceroOPtion[i++] = itemTipot;
        }
    }

    /**
     * Llenado de selectitem Departamento.
     *
     * @return
     */
    public String llenarDepartamento() {
        queryDeptos = getSessionBeanCobra().getCobraService().encontrarDepartamentos();
        Departamento = new SelectItem[queryDeptos.size()];
        int i = 0;
        for (Localidad depto : queryDeptos) {
            SelectItem dep = new SelectItem(depto.getStrcodigolocalidad(), depto.getStrdepartamento());
//            if (i == 0) {
//                codDepartamento = depto.getStrcodigolocalidad();
//            }
            Departamento[i++] = dep;
        }
        codDepartamento = "0";
        return null;
    }

    /**
     * Llenado de selectitem Municipio.
     *
     * @return
     */
    public String llenarMunicipio() {
        queryMunicipios = getSessionBeanCobra().getCobraService().encontrarMunicipios(codDepartamento);
        Municipio = new SelectItem[queryMunicipios.size()];
        int i = 0;
        for (Localidad muni : queryMunicipios) {
            SelectItem mun = new SelectItem(muni.getStrcodigolocalidad(), muni.getStrmunicipio());
            Municipio[i++] = mun;
        }
        return null;
    }
    
    /**
     * Llenado de selectitem Region.
     */
    public void llenarRegiones() {
        queryRegiones = getSessionBeanCobra().getCobraService().encontrarRegion();

        Region = new SelectItem[queryRegiones.size()];
        int i = 0;
        for (Region regi : queryRegiones) {
            SelectItem reg = new SelectItem(regi.getIntidregion(), regi.getStrnombre());

            Region[i++] = reg;
        }
    }

    /**
     * Agregar la localidad, dependiendo del tipo de origen
     */
    public void agregarLocalidadEspecifica() {
        if (!verificarLocalidad(codMunicipio)) {
            listaLocalidades.add(getSessionBeanCobra().getCobraService().encontrarLocalidadPorId(codMunicipio));
        }
        validarDatosBasicos();
    }
    
    public void agregarLocalidad() {
        switch (obranueva.getTipoorigen().getIntidtipoorigen()) {
            case 1:
                if (!verificarLocalidad(codMunicipio)) {
                    for (Localidad local : queryMunicipios) {
                        if (local.getStrcodigolocalidad().compareTo(codMunicipio) == 0) {
                            listaLocalidades.add(local);
                        }
                    }
                    listadiligenciada = 1;
                }
                break;
            case 2:
                if (!verificarLocalidad(codDepartamento)) {
                    for (Localidad local : queryDeptos) {
                        if (local.getStrcodigolocalidad().compareTo(codDepartamento) == 0) {
                            listaLocalidades.add(local);
                        }
                    }
                    listadiligenciada = 2;
                }
                break;
            case 3:
                if (!verificarRegion(codRegion)) {
                    for (Region local : queryRegiones) {
                        if (local.getIntidregion() == codRegion) {
                            listaRegiones.add(local);
                        }
                    }
                    listadiligenciada = 3;
                }
                break;
        }
        validarDatosBasicos();
    }

    /**
     * Verifica que en la lista de la localidad, no este presente la nueva
     * localidad a agregar
     *
     * @param codloc Codigo localidad
     * @return Retorna True siempre y cuando este en la lista, de lo contrario
     * False
     */
    public boolean verificarLocalidad(String codloc) {
        for (Localidad local : listaLocalidades) {
            if (local.getStrcodigolocalidad().compareTo(codloc) == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica que en la lista de la localidad, no este presente la nueva
     * localidad a agregar
     *
     * @param codRegion Codigo de La Region
     * @return Retorna True siempre y cuando este en la lista, de lo contrario
     * False
     */
    public boolean verificarRegion(int codRegion) {
        for (Region local : listaRegiones) {
            if (local.getIntidregion() == codRegion) {
                return true;
            }
        }
        return false;
    }

    /**
     * Remueve el objeto (localidad) de la tablalocalidades
     *
     * @return null
     */
    public String eliminarLocalidad() {
        Localidad local = (Localidad) tablalocalidades.getRowData();
        if(local.getTipolocalidad().getOidcodigotipolocalidad() == Tipolocalidad.ID_PROVINCIA) {
            provincias.add(local);
//            cargarSelectItemLocalidad(provinciasSelectItem, provincias);
        }
        if(local.getTipolocalidad().getOidcodigotipolocalidad() == Tipolocalidad.ID_CORREGIMIENTO) {
            corregimients.add(local);
//            cargarSelectItemLocalidad(corregimientsSelectItem, corregimients);
        }
        if(local.getTipolocalidad().getOidcodigotipolocalidad() == Tipolocalidad.ID_CAR) {
            cars.add(local);
//            cargarSelectItemLocalidad(carsSelectItem, cars);
        }
        if(local.getTipolocalidad().getOidcodigotipolocalidad() == Tipolocalidad.ID_CUENCA) {
            cuencas.add(local);
//            cargarSelectItemLocalidad(carsSelectItem, cars);
        }
        listaLocalidades.remove(local);
        return null;
    }

    /**
     * Inicializar listalocalidades y listaregiones, dependiendo del tipoorigen
     *
     * @return
     */
    public String ingresaraModalLocalidad() {
        if (listadiligenciada != 0) {
            if (listadiligenciada != obranueva.getTipoorigen().getIntidtipoorigen()) {
                listaLocalidades = new ArrayList<Localidad>();
                listaRegiones = new ArrayList<Region>();
            }
        }
        return null;
    }

    /**
     * Remueve el objeto (localidad) de la tablalocalidades
     *
     * @return
     */
    public String eliminarRegion() {
        Region local = (Region) tablaregiones.getRowData();
        listaRegiones.remove(local);
        return null;
    }

    /**
     * Llenado de selectitem comunas.
     */
    public String llenarComunas() {
        List<Comuna> lista = getSessionBeanCobra().getCobraService().encontrarComunas();
        comunas = new SelectItem[lista.size()];
        int i = 0;
        for (Comuna com : lista) {
            SelectItem item = new SelectItem(com.getIntcodigocomuna(), com.getStrnombrecomuna());
            if (i == 0) {
                idcomuna = com.getIntcodigocomuna();
            }
            comunas[i++] = item;
        }
        return null;
    }

    /**
     * Llenado de selectitem corregimientos. public String llenarMunicipio() {
     * queryMunicipios =
     * getSessionBeanCobra().getCobraService().encontrarMunicipios(codDepartamento);
     * Municipio = new SelectItem[queryMunicipios.size()]; int i = 0; for
     * (Localidad muni : queryMunicipios) { SelectItem mun = new
     * SelectItem(muni.getStrcodigolocalidad(), muni.getStrmunicipio());
     * Municipio[i++] = mun; } return null; }
     */
    public String llenarCorregimientos() {
        List<Corregimiento> lista = getSessionBeanCobra().getCobraService().encontrarCorregimientos();

        corregimientos = new SelectItem[lista.size()];
        int i = 0;
        for (Corregimiento com : lista) {
            SelectItem item = new SelectItem(com.getIntcodigocorregimiento(), com.getStrnombrecorregimiento());
            if (i == 0) {
                idcorregimiento = com.getIntcodigocorregimiento();
            }
            corregimientos[i++] = item;

        }
        return null;
    }

    /**
     * Llenado de selectitem barrios.
     *
     */
    public String llenarBarrios() {
        listadobarrio = getSessionBeanCobra().getCobraService().encontrarBarrios(idcomuna);
        barrios = new SelectItem[listadobarrio.size()];
        int i = 0;
        for (Barrio com : listadobarrio) {
            SelectItem item = new SelectItem(com.getIntcodigobarrio(), com.getStrnombrebarrio());
            barrios[i++] = item;
        }
        return null;
    }

    /**
     * Llenado de selectitem veredas.
     */
    public String llenarVeredas() {
        listadovereda = getSessionBeanCobra().getCobraService().encontrarVeredas(idcorregimiento);
        veredas = new SelectItem[listadovereda.size()];
        int i = 0;
        for (Vereda com : listadovereda) {
            SelectItem item = new SelectItem(com.getIntcodigovereda(), com.getStrnombrevereda());
            veredas[i++] = item;
        }
        return null;
    }

    /**
     * Llenado de selectitem eventoItem.
     */
    public void llenarEventos() {
        List<Evento> listEvento = getSessionBeanCobra().getCobraService().encontrarEventos();
        eventoItem = new SelectItem[listEvento.size()];
        int i = 0;
        for (Evento even : listEvento) {
            SelectItem itEvento = new SelectItem(even.getIntidevento(), even.getStrnombre());
            if (i == 0) {
                idevento = even.getIntidevento();
            }
            eventoItem[i++] = itEvento;
        }
    }

    /**
     * Llenado de selectitem periodoEventoItem.
     */
    public void llenarPeriodoxEvento() {
        List<Periodoevento> listPeriodo = getSessionBeanCobra().getCobraService().encontrarPeriodoEventoPorEvento(idevento);
        periodoEventoItem = new SelectItem[listPeriodo.size()];
        int i = 0;
        for (Periodoevento peven : listPeriodo) {
            SelectItem itPeven = new SelectItem(peven.getIntidperiodoevento(), peven.getStrdescripcion());
            periodoEventoItem[i++] = itPeven;
        }
    }

    /**
     * Se utiliza para llenar la fase.
     *
     * @return null
     */
    public String llenarFase() {
        getSessionBeanCobra().getCobraService().setFase(getSessionBeanCobra().getCobraService().encontrarFase());

        return null;
    }

    /**
     * Llenado de Listatipoproyecto.
     *
     * @return
     */
    public String llenarTipoProyecto() {
        setTiproyectoselec(0);

        if (bundle.getString("tipoproyectoporsector").equals("true")) {
            getSessionBeanCobra().getCobraService().setListatipoproyecto(getSessionBeanCobra().getCobraService().encontrarTiposProyectoPorClaseObra(0));
        } else {
            getSessionBeanCobra().getCobraService().setListatipoproyecto(getSessionBeanCobra().getCobraService().encontrarTiposProyecto());
        }

        return null;
    }

    /**
     * Obtener el tipo de proyecto seleccionado y cargar los subtipos
     *
     * @param fila
     */
    public void obtenerFilaTipoProyecto(int fila) {
        setTiproyectoselec(getSessionBeanCobra().getCobraService().getListatipoproyecto().get(fila).getIntidtipoproyecto());
        chequiarTipoProyecto(getSessionBeanCobra().getCobraService().getListatipoproyecto().get(fila).getIntidtipoproyecto());
        seleccionarsubtipo();
    }

    /**
     * LLenar la listatipodeobra dependiendo el tipoproyecto
     *
     */
    public void seleccionarsubtipo() {
        getSessionBeanCobra().getCobraService().setListatipoobra(getSessionBeanCobra().getCobraService().encontrarSubTiposProyectoxtipoproyecto(getTiproyectoselec()));
    }

    /**
     * Si aplica proyecto por sector en el cambio de clase de obra (sector) se
     * consultan los tipos de proyecto asociados a esa clase de obra (sector).
     */
    public void cambioClaseObra() {
        if (bundle.getString("tipoproyectoporsector").equals("true")) {
            setTiproyectoselec(0);
            getSessionBeanCobra().getCobraService().setListatipoproyecto(getSessionBeanCobra().getCobraService().encontrarTiposProyectoPorClaseObra(obranueva.getClaseobra().getIntidclaseobra()));
        }
    }

    /**
     * Encontrar la imagen evolucion por nombre
     *
     * @param actual Imegen a filtrar
     * @return Retorna True si ha sido encontrada la imagen
     */
    public boolean filtrarImagenesevolucion(Object actual) {
        Imagenevolucionobra doc = (Imagenevolucionobra) actual;
        if (this.valorFiltroImagenevolucionobra.length() == 0) {
            return true;
        }
        if (doc.getStrnombre().toLowerCase().contains(this.valorFiltroImagenevolucionobra.toLowerCase())) {
            return true;
        } else {
            return false;
        }
    }

    public void llenarTablaImagenevolucionobra() {
    }

    /**
     * Se utiliza para descargar una imagenevolucionobra
     *
     * @return null
     */
    public String bt_download_imagenevolucion_action() {
        Imagenevolucionobra doc = (Imagenevolucionobra) tablaImagenesevolucion.getRowData();
        this.getSessionBeanCobra().setUrlAbri(doc.getStrubicacion());
        return "Download";
    }

    /**
     * Se utiliza para capturar el objeto de imagenevolucionobra.
     *
     * @return null
     */
    public String bt_editar_imagenevolucion_action() {
        this.imagenevolucionobraEd = (Imagenevolucionobra) tablaImagenesevolucion.getRowData();
        return null;
    }

    /**
     * Se utiliza para capturar el objeto de imagenevolucionobra.
     *
     * @return null
     */
    public String bt_eliminar_imagenevolucion_action() {
        this.imagenevolucionobraEl = (Imagenevolucionobra) tablaImagenesevolucion.getRowData();
        return null;
    }

    /**
     * Se utiliza para actualizar el tipodeobra, del proyecto
     *
     * @return null
     */
    public void cambioSubtipo(int subtipo) {
        obranueva.setTipoobra(new Tipoobra());
        obranueva.getTipoobra().setInttipoobra(subtipo);
        obranueva.getTipoobra().setTipoproyecto(new Tipoproyecto());
        obranueva.getTipoobra().getTipoproyecto().setIntidtipoproyecto(tiproyectoselec);
        chequiarTipoObra(subtipo);

    }

    /**
     * Se utiliza para indicarle a la aplicacion si es un proyecto padre para no
     * dejarle descargar el cronograma
     */
    public void fnEsProyectoPadre() {
        // si es padre deshabilitrar cronograma
        disableCronograma = 0;
        disableAiu = 0;
        obranueva.setNumvaltotobra(BigDecimal.ZERO);
            valortotalobra= BigDecimal.ZERO;
        if (obranueva.isBooleantienehijos() || isProyectoestrategia()) {
            disableCronograma = 1;
            disableAiu = 1;

            llenarTiposCosto();
        }
    }

    /**
     * Se utiliza para asignarle a la Obra en Proceso la imagen que se va a
     * mostrar en el mapa, como imagen principal.
     *
     * @return null
     */
    public String subirImagenPrincipal() {
        if (cargadorImagenPrincipal.getNumArchivos() > 0) {
            if (nomImagen.compareTo("") != 0) {
                try {
                    getSessionBeanCobra().getCobraService().getImagen().setObra(obranueva);
                    getSessionBeanCobra().getCobraService().getImagen().setStrnombre(nomImagen);
                    String carpetaDoc = MessageFormat.format(RutasWebArchivos.TMP, "" + obranueva.getIntcodigoobra());
                    cargadorImagenPrincipal.guardarArchivosTemporales(carpetaDoc, true);
                    String rutaWebImg = cargadorImagenPrincipal.getArchivos().get(0).getRutaWeb();
                    getSessionBeanCobra().getCobraService().getImagen().setStrubicacion(rutaWebImg);
                    getSessionBeanCobra().getCobraService().getImagen().setTipoimagen(new Tipoimagen(5, "Principal", false));
                    getSessionBeanCobra().getCobraService().getImagen().setDatefecha(new Date());
                    getSessionBeanCobra().getCobraService().getImagen().setObra(obranueva);
                    listaImagenesevolucionobra.add(getSessionBeanCobra().getCobraService().getImagen());
                    getSessionBeanCobra().getCobraService().setImagen(new Imagenevolucionobra());
                    cargadorImagenPrincipal = new CargadorArchivosWeb();
                    imgppal = true;
                } catch (ArchivoExistenteException ex) {
                    Logger.getLogger(IngresarNuevaObra.class.getName()).log(Level.SEVERE, null, ex);
                    FacesUtils.addErrorMessage(bundle.getString("docexistenteerror"));
                }
            } else {
                FacesUtils.addErrorMessage(bundle.getString("debedarunnombre"));
            }
        } else {
            FacesUtils.addErrorMessage(bundle.getString("debeadjuntarimagen"));
        }
        nomImagen = "";
        return null;
    }

    /**
     * Se utiliza para asignarle a la Obra en Proceso la imagen que se va a
     * mostrar como imagen anterior.
     *
     * @return null
     */
    public String subirImagenInicial() {

        if (nombreimg.compareTo("") != 0) {
            if (cargadorImagenAnterior.getNumArchivos() > 0) {
                try {
                    getSessionBeanCobra().getCobraService().getImagen().setObra(obranueva);
                    String carpetaDoc = MessageFormat.format(RutasWebArchivos.TMP, "" + obranueva.getIntcodigoobra());
                    cargadorImagenAnterior.guardarArchivosTemporales(carpetaDoc, true);
                    getSessionBeanCobra().getCobraService().getImagen().setStrnombre(nombreimg);
                    getSessionBeanCobra().getCobraService().getImagen().setTipoimagen(new Tipoimagen(1, "Anterior", true));
                    getSessionBeanCobra().getCobraService().getImagen().setDatefecha(obranueva.getDatefeciniobra());
                    String rutaWebImg = cargadorImagenAnterior.getArchivos().get(0).getRutaWeb();
                    getSessionBeanCobra().getCobraService().getImagen().setStrubicacion(rutaWebImg);
                    getSessionBeanCobra().getCobraService().getImagen().setObra(obranueva);
                    listaImagenesevolucionobra.add(getSessionBeanCobra().getCobraService().getImagen());
                    getSessionBeanCobra().getCobraService().setImagen(new Imagenevolucionobra());
                    cargadorImagenAnterior = new CargadorArchivosWeb();
                    nombreimg = "";
                    img = true;
                } catch (ArchivoExistenteException ex) {
                    Logger.getLogger(IngresarNuevaObra.class.getName()).log(Level.SEVERE, null, ex);
                    FacesUtils.addErrorMessage(bundle.getString("docexistenteerror"));
                }

            } else {

                FacesUtils.addErrorMessage(bundle.getString("debeadjuntarimagen"));

            }
        } else {

            FacesUtils.addErrorMessage(bundle.getString("debedarunnombre"));
        }

        return null;
    }

    /**
     * Eliminar el objeto imagen de la tablaImagenesevolucion y eliminarla la
     * carpeta donde esta almacenada.
     *
     * @return null
     */
    public String eliminarImagenLista() {
        Imagenevolucionobra imgborrar = (Imagenevolucionobra) tablaImagenesevolucion.getRowData();
//        IngresarNuevaObra ign = (IngresarNuevaObra) FacesUtils.getManagedBean("Supervisor$IngresarNuevaObra");
//        Imagenevolucionobra imgborrar = ign.getListaImagenesevolucionobra().get(filaSeleccionada);
        if (imgborrar.getTipoimagen().getIntidtipoimagen() == 1) {
            img = false;
        } else if (imgborrar.getTipoimagen().getIntidtipoimagen() == 5) {
            imgppal = false;
        }
        ArchivoWebUtil.eliminarArchivo(imgborrar.getStrubicacion());
        if (imgborrar.getIntidimagen() != 0) {
            getSessionBeanCobra().getCobraService().borrarImagen(imgborrar);
        }
        listaImagenesevolucionobra.remove(imgborrar);
        return null;
    }

    /**
     * Eliminar el objeto imagen de la tablaImagenesevolucion y eliminarla la
     * carpeta donde esta almacenada.
     *
     * @return null
     */
    public String borrarImagenEvolucion() {
        cargadorImagenAnterior.borrarDatosSubidos();
        pathImagen = RutasWebArchivos.IMG_NO_DISPONIBLE;
        return null;
    }

    /**
     * Eliminar el objeto imagen de la tablaImagenesevolucion y eliminarla la
     * carpeta donde esta almacenada.
     *
     * @return null
     */
    public String borrarImagenPrincipal() {
        cargadorImagenPrincipal.borrarDatosSubidos();
        pathImagen = RutasWebArchivos.IMG_NO_DISPONIBLE;
        return null;
    }

    /**
     * Elimina el cronograma del cargador de archivos
     */
    public String borrarCronograma() {
        cargadorCronograma.borrarDatosSubidos();
        if (obranueva.getStrurlcronograma() != null) {
            valido = 1;
        }
        return null;
    }

    /**
     * El metodo es para adicionar en el mapa un markador para que el usuario
     * pueda ubicar donde se va a ejecutar el proyecto en el pais
     *
     * @throws Exception
     */
    public String nuevoPunto() throws Exception {
        verConfirmar = true;
        Marcador marke = new Marcador();
        switch (formaseleccionada) {
            case 0:
                try {
                    marke.setLatitude(getSessionBeanCobra().getBundle().getString("latitudmarkadornuevaobra"));
                    marke.setLongitude(getSessionBeanCobra().getBundle().getString("longitudmarkadornuevaobra"));
                    marke.setDraggable("true");
                    marke.setTipo(tiposelec);
                    marke.setConverterMessage("/" + getSessionBeanCobra().getBundle().getString("versioncobra") + "/resources/images/marker.png");
                    listamarcadores.add(marke);
                    redibujarmapa = false;
                } catch (Exception ex) {
                    System.out.println("ex = " + ex);
                }
                break;
//                Esta parte se deshabilito ya hay un metodo (obtenerPuntopordireccion) que hace la busqueda por direccion
//            case 1:
//                System.out.println("latitidu = " + latNewmanu);
//                System.out.println("longitud = " + longNewmanu);
//                marke.setLatitude(latNewmanu);
//                marke.setLongitude(longNewmanu);                
//                marke.setDraggable("true");
//                marke.setTipo(tiposelec);
//                marke.setConverterMessage("/" + getSessionBeanCobra().getBundle().getString("versioncobra") + "/resources/images/marker.png");
//                listamarcadores.add(marke);
//                redibujarmapa = false;
//                break;
            case 2:
                if (!latNewmanu.equals("") && !longNewmanu.equals("")) {//address.compareTo("") != 0
                    if (isNumeric(latNewmanu)) {
                        try {

                            PlaceMark placeMarkNew = GMaps4JSFServiceFactory.getReverseGeocoderService().getPlaceMark(latNewmanu, longNewmanu);
                            address = placeMarkNew.getAddress();

                        } catch (Exception e) {
                            address = "Faltante";
                        }

                        marke.setAddress(address);
                        marke.setLatitude(latNewmanu);
                        marke.setLongitude(longNewmanu);
                        marke.setConverterMessage("/" + getSessionBeanCobra().getBundle().getString("versioncobra") + "/resources/images/marker.png");
                        marke.setTipo(tiposelec);
                        //zoom="14";
                        listamarcadores.add(marke);
                    } else {
                        verConfirmar = false;
                        FacesUtils.addErrorMessage("El dato ingresado no es una coordenada validad se espera un numero eje 6.5895478755445");
                    }
                } else {
                    verConfirmar = false;
                    FacesUtils.addErrorMessage("Debe llenar los campos de latitud y longitud   ");
                }
                break;
        }
        return null;
    }

    /**
     * Validar si la cadena ingresada es numeric o no
     *
     * @param cadena Numero a Validar
     * @return si la cadena es numeric retorna True o por el contrario retorna
     * False
     */
    private static boolean isNumeric(String cadena) {
        try {
            BigDecimal bd = new BigDecimal(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;

        }
    }

    /**
     * Agregar a la listamarcadores el nuevo marcador
     *
     * @return null
     */
    public String confirmarPunto() {
        verConfirmar = false;
        verNuevo = false;
        int i = listamarcadores.size();
        try {
            String lat = listamarcadores.get(i - 1).getLatitude();
            String longi = listamarcadores.get(i - 1).getLongitude();
            PlaceMark placeMarkNew = GMaps4JSFServiceFactory.getReverseGeocoderService().getPlaceMark(lat, longi);
            listamarcadores.get(i - 1).setAddress(placeMarkNew.getAddress().toString());
            listamarcadores.get(i - 1).setConverterMessage("/" + getSessionBeanCobra().getBundle().getString("versioncobra") + "/resources/images/pin.png");
        } catch (Exception ex) {
            //Logger.getLogger(IngresarNuevaObra.class.getName()).log(Level.SEVERE, null, ex);
            listamarcadores.get(i - 1).setAddress("Faltante");
            listamarcadores.get(i - 1).setConverterMessage("/" + getSessionBeanCobra().getBundle().getString("versioncobra") + "/resources/images/pin.png");
        }
//        listamarcadores.get(i - 1).setAddress(getGeocode().obtenerDireccionxLatyLong(listamarcadores.get(i - 1).getLatitude(), listamarcadores.get(i - 1).getLongitude()));
//        listamarcadores.get(i - 1).setConverterMessage("/" + getSessionBeanCobra().getBundle().getString("versioncobra") + "/resources/images/pin.png");

        listamarcadores.get(i - 1).setDraggable("false");
        marli.add(listamarcadores.get(i - 1));
        //mostrarPanelmarker = 1;
        if (listamarcadores.get(i - 1).getTipo() == 0) {
            verPrimario = false;
            tiposelec = 1;
            obranueva.setFloatlatitud(new BigDecimal(listamarcadores.get(i - 1).getLatitude().replaceAll(" ", "")));
            obranueva.setFloatlongitud(new BigDecimal(listamarcadores.get(i - 1).getLongitude().replaceAll(" ", "")));
            obranueva.setStrdireccion(listamarcadores.get(i - 1).getAddress());
        } else {
            Puntoobra puntonew = new Puntoobra();
            puntonew.setObra(obranueva);
            puntonew.setStrlatitud(listamarcadores.get(i - 1).getLatitude().replaceAll(" ", ""));
            puntonew.setStrlongitud(listamarcadores.get(i - 1).getLongitude().replaceAll(" ", ""));
            puntonew.setStrdireccion(marli.get(i - 1).getAddress().toString());
            obranueva.getPuntoobras().add(puntonew);
        }
//         zoom="6";
//         zoom=jsZoon;
        redibujarmapa = true;
        return null;
    }

    /**
     * captura el cambio de posicion del marcador y borra la anterior
     *
     * @param event Evento que captura el cambio de posicion del marcador
     * @return null
     */
    public String cambio(ValueChangeEvent event) {
        //Marker nuevo = (Marker) event.getNewValue();
        Position posmarker = (Position) event.getNewValue();

//        latNewmanu = nuevo.getLatitude();
//        longNewmanu = nuevo.getLongitude().replaceAll(" ", "");
        latNewmanu = posmarker.getLatitude();
        longNewmanu = posmarker.getLongitude();
        try {
            PlaceMark placeMarkNew = GMaps4JSFServiceFactory.getReverseGeocoderService().getPlaceMark(latNewmanu, longNewmanu);
            address = placeMarkNew.getAddress();

        } catch (Exception ex) {
            //Logger.getLogger(IngresarNuevaObra.class.getName()).log(Level.SEVERE, null, ex);
            address = "Faltante";
        }
////        setAddress(getGeocode().obtenerDireccionxLatyLong(latNewmanu, longNewmanu));
        listamarcadores.get(listamarcadores.size() - 1).setLatitude(latNewmanu);
        listamarcadores.get(listamarcadores.size() - 1).setLongitude(longNewmanu);
        return null;
    }

    /**
     * Remover de la listamarcadores la direccion almacenada
     *
     * @return null
     */
    public String cancelarDireccion() {
        listamarcadores.remove(listamarcadores.size() - 1);
        verConfirmar = false;
        verNuevo = false;
        formaseleccionada = 0;
        redibujarmapa = true;
        //  zoom = "6";      
        return null;
    }

    /**
     * Remover de la listamarcadores el marcador que ha sido almacenado y
     * actualizar El proyecto
     *
     * @return null
     */
    public String eliminarMarker() {
        Marcador marEli = (Marcador) tablaMarkereli.getRowData();
        //IngresarNuevaObra ign = (IngresarNuevaObra) FacesUtils.getManagedBean("Supervisor$IngresarNuevaObra");
//        Marcador marEli = ign.getMarli().get(filaSeleccionada);
        marli.remove(marEli);
        listamarcadores.remove(marEli);
        if (listamarcadores.isEmpty()) {
            //mostrarPanelmarker = 0;
            listamarcadores = new ArrayList<Marcador>();
        }
        if (marEli.getTipo() == 0) {
            verPrimario = true;
            tiposelec = 0;
            obranueva.setFloatlatitud(null);
            obranueva.setFloatlongitud(null);
            obranueva.setStrdireccion(null);
        } else {
            int i = 0;
            List<Puntoobra> lista = new ArrayList<Puntoobra>(obranueva.getPuntoobras());
            while (i < lista.size()) {
                if (lista.get(i).getStrlatitud().compareTo(marEli.getLatitude()) == 0
                        && lista.get(i).getStrlongitud().compareTo(marEli.getLongitude()) == 0) {
                    obranueva.getPuntoobras().remove(lista.get(i));
                    getSessionBeanCobra().getCobraService().borrarPunto(lista.get(i));
                }
                i++;
            }
        }
        if (obranueva.getTercero().getIntcodigo() == 5212 && obranueva.getSedeeducativa() != null) {
            obranueva.setSedeeducativa(null);
        }
        redibujarmapa = false;
        return null;
    }

    /**
     * Validar si el primer paso ({@link validarPasoDatosBasicos()}) esta bien
     * diligenciado para proseguir a la ubicacion del proyecto
     *
     * @return Regla de navegacion "datosubicaciondelaObra"
     */
    public String validabasicos() {
        if (getSessionBeanCobra().getBundle().getString("aplicafonade").equals("true")) {
            if (validarPasoDatosBasicos() && validarFechasProyectoInicio() && validarFechasProyectoFin()) {
                return pasaraUbicacion();
            }
        } else {
            if (validarPasoDatosBasicos()) {
                return pasaraUbicacion();
            } else {
                return null;
            }
        }
        return null;

    }

    /**
     *
     * @return Regla de Navegación "datosgenerarCrono"
     */
    public String pasoGenerarCronograma() {
        validarFinalizar();
        if (obranueva.isBooleantienehijos() || isProyectoestrategia()) {
            validarCostos();
        }
        navegacion = 3;
        subtiposelec = obranueva.getTipoobra().getInttipoobra();
        tiahselect = faseelegida.getIntidfase();
        tiproyectoselec = obranueva.getTipoobra().getTipoproyecto().getIntidtipoproyecto();
        return "datosgenerarCrono";
    }

    /**
     * Validar si el Paso de datos basicos esta bien diligenciado
     *
     * @return Retorna False si algun campo no esta bien diliganciado de lo
     * contrario True
     */
    public boolean validarPasoDatosBasicos() {
        if (obranueva.getStrnombreobra() == null || obranueva.getStrnombreobra().equals("")) {
            FacesUtils.addErrorMessage(bundle.getString("debedarunnombraalaobra"));
            datosbas = false;
            return false;
        } else {
            if (obranueva.getDatefeciniobra() != null && obranueva.getDatefecfinobra() != null) {
                if (obranueva.getDatefeciniobra().compareTo(obranueva.getDatefecfinobra()) > 0) {
                    FacesUtils.addErrorMessage(bundle.getString("lafechadefinalizaciondebeser"));
                    datosbas = false;
                    return false;
                } else {
                    if (obranueva.getStrobjetoobra() == null || obranueva.getStrobjetoobra().equals("")) {
                        FacesUtils.addErrorMessage(bundle.getString("debedarobjetoobra"));
                        datosbas = false;
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * valida si las fechas de inicio del proyecto esta dentro del rango de
     * convenio
     *
     * @return Retorna false si las fecha inicial es menor que la fecha inicial
     * del convenio
     */
    public boolean validarFechasProyectoInicio() {
        if (obranueva.getContrato() != null) {
            if (obranueva.getDatefeciniobra() != null) {
                if (obranueva.getDatefeciniobra().compareTo(obranueva.getContrato().getDatefechaini()) < 0) {
                    FacesUtils.addErrorMessage(bundle.getString("fechaerrorinicio"));
                    datosbas = false;
                    return false;
                }
            } else {
                FacesUtils.addErrorMessage("Debe ingresar la fecha de inicio y fin del proyecto");
                datosbas = false;
                return false;
            }

            return true;
        }
        return true;
    }

    /**
     * valida si las fechas de fin del proyecto esta dentro del rango de
     * convenio
     *
     * @return Retorna false si las fecha fin es menor que la fecha fin del
     * convenio
     */
    public boolean validarFechasProyectoFin() {
        if (obranueva.getContrato() != null) {
            if (obranueva.getDatefecfinobra().compareTo(obranueva.getContrato().getDatefechafin()) > 0) {
                FacesUtils.addErrorMessage(bundle.getString("fechaerrorfin"));
                datosbas = false;
                return false;
            }
            return true;
        }
        return true;
    }

    /**
     * Validar si el Paso de Ubicación del Proyecto esta bien diligenciado
     *
     * @return Retorna False si algun campo no esta bien diliganciado de lo
     * contrario True
     */
    public boolean validarPasoUbicacion() {
        if (marli.isEmpty()) {
            FacesUtils.addErrorMessage(bundle.getString("debeingresaralmenosunpunto"));
            return false;
        } else if (verPrimario) {
            FacesUtils.addErrorMessage(bundle.getString("debeingresarunpuntoprimario"));
            return false;
        }
        if (listaLocalidades.size() > 0 || listaRegiones.size() > 0 || obranueva.getTipoorigen().getIntidtipoorigen() == 4) {
            return true;
        } else {
            //"DEBE DILIGENCIAR TODOS LOS DATOS REQUERIDOS EN EL PASO 1";
            FacesUtils.addErrorMessage(bundle.getString("debediligenciarlocalregion"));
            return false;
        }
    }

    /**
     * Realiza la redirección al paso de asociar contrato
     * @return 
     */
    public String pasoAsociarContrato() {
        return "datosAsociarContrato";
    }

    /**
     * <p>
     * Return a reference to the scoped data bean.</p>
     *
     * @return reference to the scoped data bean
     */
    protected HomeGestion getHomeGestion() {
        return (HomeGestion) FacesUtils.getManagedBean("HomeGestion");
    }

    /**
     * Reinicializa todos los objetos del modulo de nuevo proyecto, para despues
     * volver al mapa
     *
     * @return Regla de navegación "cancelar"
     */
    public String cancelar_action() {
        limpiarobra();
        return "cancelar";
    }

    /**
     * El metodo buscar es aplicar el filtro de la modal busqueda avanzada y
     * luego llena ({@link primerosreales()})
     *
     * @return null
     */
    public String buscar() {
        aplicafiltro = true;
        primerosreales();
        return null;
    }

    public boolean validarTipificacion() {
        boolean valido = true;
        if (obranueva.getClaseobra().getIntidclaseobra()==0) {
            FacesUtils.addErrorMessage("Debe seleccionar un sector para la obra.");
            valido= false;
        }
        subtiposelec = obranueva.getTipoobra().getInttipoobra();
        tiahselect = faseelegida.getIntidfase();
        if (tiahselect == 0 || subtiposelec == 0 || tiproyectoselec == 0) {
             FacesUtils.addErrorMessage("Debe seleccionar un tipo de obra.");
            valido= false;
        } else {
            valido= true;
        }
        return valido;
    }

    /**
     * Validar si el Paso de datos basicos esta bien diligenciado
     *
     * @return Retorna Regla de navegacion "datosbasicosnuevoproyecto" de lo
     * contrario null
     */
    public String pasarDatosBasicos() {
        if (validarTipificacion()) {
            return "datosbasicosnuevoproyecto";
        } else {
            FacesUtils.addErrorMessage("Debe seleccionar un tipo de obra.");
            return null;
        }
    }

    /**
     * Regla de Navegacion que me lleva a la tipificacion
     *
     * @return
     */
    public String volverTipificacion() {
        subtiposelec = obranueva.getTipoobra().getInttipoobra();
        tiahselect = faseelegida.getIntidfase();
        tiproyectoselec = obranueva.getTipoobra().getTipoproyecto().getIntidtipoproyecto();
        return "volvertipificacion";
    }

    /**
     * Metodo que se utiliza para paginar,
     *
     * Trae las primeros Contratos 0 a 5
     *
     * @return null
     */
    public String primeroContratos() {
        listacontratos = getSessionBeanCobra().getCobraService().obtenerContratoxEntidad(obranueva.getTercero().getIntcodigo(), 0, 5, filtrocontrato, getSessionBeanCobra().getUsuarioObra());
        totalfilas = getSessionBeanCobra().getCobraService().numContratoxEntidad(obranueva.getTercero().getIntcodigo(), filtrocontrato, getSessionBeanCobra().getUsuarioObra());
        pagina = 1;
        if (totalfilas <= 5) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 5;
            if (totalfilas % 5 > 0) {
                totalpaginas++;
            }
        }
        veranteriorcontrato = false;
        if (totalpaginas > 1) {
            verultimoscontrato = true;
        } else {
            verultimoscontrato = false;
        }
        return null;
    }

    /**
     * Metodo que se utiliza para paginar, Trae los siguientes 5 Contratos
     *
     * @return null
     */
    public String siguienteContratos() {
        int num = (pagina) * 5;
        listacontratos = getSessionBeanCobra().getCobraService().obtenerContratoxEntidad(obranueva.getTercero().getIntcodigo(), num, num + 5, filtrocontrato, getSessionBeanCobra().getUsuarioObra());
        totalfilas = getSessionBeanCobra().getCobraService().numContratoxEntidad(obranueva.getTercero().getIntcodigo(), filtrocontrato, getSessionBeanCobra().getUsuarioObra());
        if (totalfilas <= 5) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 5;
            if (totalfilas % 5 > 0) {
                totalpaginas++;
            }
        }
        pagina = pagina + 1;
        if (pagina < totalpaginas) {
            verultimoscontrato = true;
        } else {
            verultimoscontrato = false;
        }
        veranteriorcontrato = true;
        return null;
    }

    /**
     * Metodo que se utiliza para paginar, Trae los 5 anteriores Contratos
     *
     * @return null
     */
    public String anterioresContratos() {
        pagina = pagina - 1;
        int num = (pagina - 1) * 5;
        listacontratos = getSessionBeanCobra().getCobraService().obtenerContratoxEntidad(obranueva.getTercero().getIntcodigo(), num, num + 5, filtrocontrato, getSessionBeanCobra().getUsuarioObra());
        totalfilas = getSessionBeanCobra().getCobraService().numContratoxEntidad(obranueva.getTercero().getIntcodigo(), filtrocontrato, getSessionBeanCobra().getUsuarioObra());
        if (totalfilas <= 5) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 5;
            if (totalfilas % 5 > 0) {
                totalpaginas++;
            }
        }
        if (pagina > 1) {
            veranteriorcontrato = true;
        } else {
            veranteriorcontrato = false;
        }
        verultimoscontrato = true;
        return null;
    }

    /**
     * Metodo que se utiliza para paginar, Trae los ultimos 5 proyectos
     *
     * @return null
     */
    public String ultimoContratos() {
        int num = totalfilas % 5;
        totalfilas = getSessionBeanCobra().getCobraService().numContratoxEntidad(obranueva.getTercero().getIntcodigo(), filtrocontrato, getSessionBeanCobra().getUsuarioObra());
        listacontratos = getSessionBeanCobra().getCobraService().obtenerContratoxEntidad(obranueva.getTercero().getIntcodigo(), totalfilas - num, totalfilas, filtrocontrato, getSessionBeanCobra().getUsuarioObra());
        if (totalfilas <= 5) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 5;
            if (totalfilas % 5 > 0) {
                totalpaginas++;
            }
        }
        pagina = totalpaginas;
        if (pagina < totalpaginas) {
            verultimoscontrato = true;
        } else {
            verultimoscontrato = false;
        }
        veranteriorcontrato = true;
        return null;
    }

    public String buscarContrato() {
        // aplicafiltro = true;
        filtrocontrato.setBooltipocontconv(false);
        filtrocontrato.setBoolcontrconsultoria(false);
        primeroContratos();
        return null;
    }

    public String imprimirzoom() {
        return null;
    }

    public void capturaZoom() {
        String[] u = jsVariable.split(",");
    }

    /**
     * Se utiliza cuando las actividades si incluyen el AIU setiar las variables
     * que almacenan el calculo
     *
     * @return null
     */
    public String cambioAPU() {
        if (obranueva.getBoolincluyeaiu()) {
            obranueva.setFloatporadmon(0);
            obranueva.setFloatporimprevi(0);
            obranueva.setFloatporutilidad(0);
        }
        return null;
    }

    /**
     * Valida si un Proyecto ya es acto para pasar de <b>Imcompleto</b> a pasar
     * en <b>Ejecución</b>
     *
     * @return null
     */
    public String validarFinalizar() {
        subtiposelec = obranueva.getTipoobra().getInttipoobra();
        tiahselect = faseelegida.getIntidfase();
        tiproyectoselec = obranueva.getTipoobra().getTipoproyecto().getIntidtipoproyecto();
        validacion.setPresupuesto(false);
        validacion.setTipificacion(false);
        validacion.setDatosbasicos(false);
        validacion.setUbicacion(false);
        validacion.setCronograma(false);
        validacion.setContratos(false);
        validacion.setImagenes(false);
        if (obranueva.getObra() != null) {
            if (valortotalobra.compareTo(BigDecimal.ZERO) == 0
                    || valortotalobra.compareTo(obranueva.getObra().getNumvaltotobra().subtract(obranueva.getObra().getNumvlrsumahijos())) > 0) {
                validacion.setPresupuesto(false);
            } else {
                validacion.setPresupuesto(true);
            }
        } else {
            if (isProyectoestrategia()) {
                obranueva.setNumvlrsumahijos(BigDecimal.ZERO);
                validarCostos();
                if (obranueva.getNumvaltotobra().compareTo(BigDecimal.ZERO) > 0) {
                    validacion.setPresupuesto(true);
                } else {
                    validacion.setPresupuesto(false);
                }
            } else {
                System.out.println("valortotalobra = " + valortotalobra);
                if (obranueva.getNumvaltotobra().compareTo(BigDecimal.ZERO) > 0) {
                    validacion.setPresupuesto(true);
                } else {
                    validacion.setPresupuesto(false);
                }
            }
        }
        if (validarTipificacion()) {
            validacion.setTipificacion(true);
        } else {
            validacion.setTipificacion(false);
        }
        if (obranueva.getStrnombreobra() == null || obranueva.getStrnombreobra().equals("")
                && (obranueva.getNumhabbeneficiados() >= 0 && obranueva.getNumempdirectos() >= 0 && obranueva.getNumempindirectos() >= 0)) {
            validacion.setDatosbasicos(false);
        } else {
            if (!fechasvalidas) {
                validacion.setDatosbasicos(false);
                fechaCambio();
            }
            if (obranueva.getDatefeciniobra() != null && obranueva.getDatefecfinobra() != null) {
                if (obranueva.getDatefeciniobra().compareTo(obranueva.getDatefecfinobra()) > 0) {
                    validacion.setDatosbasicos(false);
                } else {
                    if (obranueva.getStrobjetoobra() == null || obranueva.getStrobjetoobra().equals("")) {
                        validacion.setDatosbasicos(false);
                    } else {
                        validacion.setDatosbasicos(true);
                    }
                }
                if (validarFechasProyectoInicio() && validarFechasProyectoFin()) {
                    validacion.setDatosbasicos(true);

                } else {
                    validacion.setDatosbasicos(false);
                }
            } else {
                validacion.setDatosbasicos(false);
            }
        }
        if (marli.isEmpty()) {
            validacion.setUbicacion(false);
        } else if (verPrimario) {
            validacion.setUbicacion(false);
        } else if (listaLocalidades.size() > 0 || listaRegiones.size() > 0 || obranueva.getTipoorigen().getIntidtipoorigen() == 4) {
            validacion.setUbicacion(true);
        } else {
            validacion.setUbicacion(false);
        }
        if (Boolean.parseBoolean(getSessionBeanCobra().getBundle().getString("habilitacionLocalidadesBarrioVereda"))) {
            if (listaBarrios.size() > 0 || listaVeredas.size() > 0) {
                validacion.setUbicacion(true);
            } else {
                validacion.setUbicacion(false);
            }
        }
        if (!obranueva.isBooleantienehijos() && !isProyectoestrategia()) {
            validarCronograma();
        } else {
            valido = 1;
        }
        if (valido == 1) {
            validacion.setCronograma(true);
        }
        if (listacontratosobra.size() > 0 || isProyectoestrategia()) {
            validacion.setContratos(true);
        }
        int i = 0;
        while (i < listaImagenesevolucionobra.size()) {
            if (listaImagenesevolucionobra.get(i).getTipoimagen().getIntidtipoimagen() == 1) {
                validacion.setImagenes(true);
                i = listaImagenesevolucionobra.size();
            }
            i++;
        }

//        if (!obranueva.isBooleantienehijos()) {
//            validacion.setPresupuesto(true);
//        }

        //if (validacion.isTipificacion() && validacion.isDatosbasicos()) {
        //guardarObraTemporal();
        //}
        validacion.validarTotal();
        return "datosAsociarFinalizar";
    }

    /**
     * Encuentra el solicitante de un Proyecto de <b>Infraestructura</b>.
     */
    public void EncontrarSolicitante() {
        boolean solicitud;
        if (obranueva.getSolicitud_obra() != null) {
            solicitud = true;
            solicitante = getSessionBeanCobra().getCobraService().encontrarTerceroSolicitante(obranueva.getSolicitud_obra().getIntserial(), solicitud);
        }
        if (obranueva.getSolicitudmaestro() != null) {
            solicitud = false;
            solicitante = getSessionBeanCobra().getCobraService().encontrarTerceroSolicitante((int) obranueva.getSolicitudmaestro().getOidcodigosolicitudmaestro(), solicitud);
        }
    }

    public boolean comboEntidadesObraguardar() {
        if (getObranueva().getTercero().getStrnombrecompleto() != null) {
            Iterator ite = temp.iterator();
            int idtercero = 0;
            while (ite.hasNext()) {
                TerceroEntidadLista tempter = (TerceroEntidadLista) ite.next();
                if (getObranueva().getTercero().getStrnombrecompleto().equals(tempter.getStrnombre())) {
                    idtercero = tempter.getCodigo();
                }
            }
            if (idtercero != 0) {
                if (idtercero == 5212) {
                    getEntidadConvenio().setIntentidadconvenio(1);
                }
                if (idtercero == 5229) {
                    getEntidadConvenio().setIntentidadconvenio(2);
                }
                getObranueva().getTercero().setIntcodigo(idtercero);
                return true;
            }
        }
        return false;
    }

    /**
     * <p>
     * Return a reference to the scoped data bean.</p>
     *
     * @return reference to the scoped data bean
     */
    protected EntidadConvenio getEntidadConvenio() {
        return (EntidadConvenio) FacesUtils.getManagedBean("Supervisor$EntidadConvenio");
    }

    /**
     * El se utiliza para seleccionar un tramo previamente ya demarcado por unas
     * rutas enviadas por inivias (Solo aplica para Proyectos de Invias)
     *
     * @return null
     */
    public String seleccionruta() {
        try {
            marli = new ArrayList<Marcador>();
            //EntidadConvenio rutaseleccion = (EntidadConvenio) FacesUtils.getManagedBean("Supervisor$EntidadConvenio");
            //Ruta ruta = rutaseleccion.getListaruta().get(filaSeleccionada);
            Ruta rutaseleccion = (Ruta) getEntidadConvenio().getTablarutas().getRowData();
            getObranueva().setRuta(rutaseleccion);
            getEntidadConvenio().setListapuntosruta(getSessionBeanCobra().getCobraService().encontrarPuntosReferenciaxRuta(rutaseleccion.getStrcodigotramo()));

            Marcador marc = new Marcador();
            int i = 0;
            while (i < getEntidadConvenio().getListapuntosruta().size()) {
                marc.setTipo(0);
                marc.setLatitude(getEntidadConvenio().getListapuntosruta().get(i).getFloatlatitud().toString());
                marc.setLongitude(getEntidadConvenio().getListapuntosruta().get(i).getFloatlongitud().toString());
                PlaceMark placeMarkNew = new PlaceMark();
                try {
                    placeMarkNew = GMaps4JSFServiceFactory.getReverseGeocoderService().getPlaceMark(marc.getLatitude(), marc.getLongitude());
                    marc.setAddress(placeMarkNew.getAddress());
                } catch (Exception e) {
                    marc.setAddress("Faltante");
                }

                i++;
            }
            marli.add(marc);
            verPrimario = false;

        } catch (Exception ex) {
            Logger.getLogger(IngresarNuevaObra.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Remueve la ruta previamente seleccionada por el usuario (Solo aplica para
     * Proyectos de Invias)
     *
     * @return null
     */
    public String desasociarRuta() {
        getObranueva().setRuta(new Ruta());
        getEntidadConvenio().setListapuntosruta(new ArrayList<Puntoreferencia>());
        listamarcadores = new ArrayList<Marcador>();
        marli = new ArrayList<Marcador>();
        return null;
    }

    /**
     * El metodo aplica siempre y cuando sea un proyecto para el ministerio de
     * educacion debido a que se selecciona la sede donde se va a ejecutar el
     * Proyecto.
     *
     * @return null
     */
    public String seleccionarSede() {
        //EntidadConvenio entidadConvenio = (EntidadConvenio) FacesUtils.getManagedBean("Supervisor$EntidadConvenio");
        //Sedeeducativa sede = entidadConvenio.getListasedeseducativas().get(filaSeleccionada);

        listamarcadores = new ArrayList<Marcador>();
        marli = new ArrayList<Marcador>();
        Sedeeducativa sede = (Sedeeducativa) getEntidadConvenio().getTablasedes().getRowData();
        obranueva.setSedeeducativa(sede);
        Marcador marc = new Marcador();
        marc.setTipo(0);
        marc.setLatitude(sede.getNumlatitud().toString());
        marc.setLongitude(sede.getNumlongitud().toString());
        try {
            PlaceMark placeMarkNew = GMaps4JSFServiceFactory.getReverseGeocoderService().getPlaceMark(marc.getLatitude(), marc.getLongitude());
            marc.setAddress(placeMarkNew.getAddress());
        } catch (Exception e) {
            marc.setAddress("Faltante");
        }
        listamarcadores.add(marc);
        marli.add(marc);
        verPrimario = false;

        return null;
    }

    /**
     * Remueve la sede a la cual fue asociada al proyecto
     *
     * @return null
     */
    public String desasociarSede() {
        obranueva.setSedeeducativa(new Sedeeducativa());
        listamarcadores = new ArrayList<Marcador>();
        marli = new ArrayList<Marcador>();
        return null;
    }

    /**
     * El metodo se utiliza para la navegacion entre paginas del modulo de nueva
     * obra
     *
     * @return
     */
     public String navegarNuevaObra() {
        String regla = null;
        subtiposelec = obranueva.getTipoobra().getInttipoobra();

        tiproyectoselec = obranueva.getTipoobra().getTipoproyecto().getIntidtipoproyecto();
        
        switch (navegacion) {
            case 0:
                regla = volverTipificacion();
                break;
            case 1:
                regla = pasarDatosBasicos();
                break;
            case 2:
                regla = pasarUbicacionObra();
                break;
            case 3:
                regla = pasoGenerarCronograma();
                break;
            case 4:
                regla = "datosGenerarImagenes";
                break;
            case 5:
                regla = "datosAsociarContrato";
                break;
            case 6:
                regla = pasoIngresarIndicadores();
                break;
        }
        return regla;
    }

    public String pasaraUbicacion() {
        navegacion = 2;
        String regla = navegarNuevaObra();
        subtiposelec = obranueva.getTipoobra().getInttipoobra();
        tiahselect = faseelegida.getIntidfase();
        tiproyectoselec = obranueva.getTipoobra().getTipoproyecto().getIntidtipoproyecto();
        codDepartamento = "0";
        llenarMunicipio();
        if (Propiedad.getValor("versioncobra").equals("cobracali")
                || Propiedad.getValor("versioncobra").equals("cobramanizales")) {

            codMunicipio = Propiedad.getValor("codigoCiudadCobra");
            agregarLocalidadEspecifica();
            Tipoorigen tipoorigen = new Tipoorigen();
            tipoorigen.setIntidtipoorigen(1);
            obranueva.setTipoorigen(tipoorigen);
            tipoOrigenSoloLectura = true;
        }
        if (Propiedad.getValor("versioncobra").equals("cobratolima")) {
            codDepartamento = Propiedad.getValor("codigoDepartamentoCobra");
            llenarMunicipio();
            Tipoorigen tipoorigen = new Tipoorigen();
            tipoorigen.setIntidtipoorigen(2);
            obranueva.setTipoorigen(tipoorigen);

        }
        return regla;
    }

    protected AdministrarObraNew getAdministrarObraNew() {
        return (AdministrarObraNew) FacesUtils.getManagedBean("Supervisor$AdministrarObraNew");
    }

    public String reporteHistorialValidaciones() {

        try {
            getSessionBeanCobra().setUrlAbri(Propiedad.getValor("ipserver")+bundle.getString("reportehistorialvalidaciones") + obranueva.getIntcodigoobra());
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/Reportes");
        } catch (IOException ex) {
            Logger.getLogger(Obra.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * retorna el nombre del archivo del cronograma
     *
     * @return
     */
    public String getNombreArchivoCronograma() {
        String nobreArchivo = null;
        if (obranueva.getStrurlcronograma() != null) {
            nobreArchivo = obranueva.getStrurlcronograma().substring(obranueva.getStrurlcronograma().lastIndexOf("/") + 1);
        }
        return nobreArchivo;
    }

    /**
     * Establece como sin validar el cronograma
     *
     * @return null
     */
    public String uploadCronoAction() {
        valido = 0;
        return null;
    }

    /**
     * Parametriza el objeto obra para crear un proyecto desde cero sin estar
     * asociado nia solicitud de obra, ni a convenio , ni a atención humanitaria
     *
     * @return null
     */
    public String crearProyecto() {
        String ret = limpiarobra();
        llenarTiposorigenes();
        return ret;
    }

    /**
     * Direcciona a la página para crear un subproyecto a partir del proyecto
     * actual
     *
     * @return
     */
    public String crearSubProyecto() {
        String ret = limpiarobra();
        obranueva.setObra(getAdministrarObraNew().getObra());
        return ret;
    }
    /*
     * Método para chuliar la fase seleccionada
     * 
     */

    public void chequiarFase(int idfase) {
        for (Fase fas : getSessionBeanCobra().getCobraService().getFase()) {
            if (fas.getIntidfase() == idfase) {
                fas.setCheck(true);
            } else {
                fas.setCheck(false);
            }
        }
    }

    /*
     * Método para chuliar el tipo de proyecto seleccionado
     * 
     */
    public void chequiarTipoProyecto(int idtipo) {
        for (Tipoproyecto tip : getSessionBeanCobra().getCobraService().getListatipoproyecto()) {
            if (tip.getIntidtipoproyecto() == idtipo) {
                tip.setCheck(true);
            } else {
                tip.setCheck(false);
            }
        }
    }
    /*
     * Método para chuliar el tipo de obra seleccionado
     * 
     */

    public void chequiarTipoObra(int idtipo) {

        for (Tipoobra tip : getSessionBeanCobra().getCobraService().getListatipoobra()) {

            if (tip.getInttipoobra() == idtipo) {
                tip.setCheck(true);

            } else {
                tip.setCheck(false);
            }
        }
    }

    /**
     * @param datosValidos the datosValidos to set
     */
    public void setDatosValidos(int datosValidos) {
        this.datosValidos = datosValidos;
    }

    /**
     * @return the datosValidos
     */
    public int getDatosValidos() {
        return datosValidos;
    }

    /**
     * Elimina un barrio de la lista de barrios
     *
     * @return
     *
     */
    public String eliminarBarrio() {
        listaBarrios.remove((Barrio) tableLocalidadbarrio.getRowData());
        return null;
    }

    /**
     * Elimina una vereda de la lista de veredas
     *
     * @return
     */
    public String eliminarVereda() {
        listaVeredas.remove((Vereda) tableLocalidadVereda.getRowData());
        return null;
    }

    public boolean verificarVereda(int intcodigovereda) {
        for (Vereda v : listaVeredas) {
            if (v.getIntcodigovereda() == intcodigovereda) {
                return true;
            }
        }
        return false;
    }

    public boolean verificarBarrio(int intcodigobarrio) {
        for (Barrio b : listaBarrios) {
            if (b.getIntcodigobarrio() == intcodigobarrio) {
                return true;
            }
        }
        return false;
    }

    public void modalModificarSelectUbicacion() {

        listaBarrios.clear();
        listaVeredas.clear();

    }

    public void agregarUbicacioncomunalocalidad() {
        switch (obranueva.getLugarobra().getIntidlugarobra()) {
            case 1:
                if (!verificarBarrio(getBarrio().getIntcodigobarrio())) {
                    for (Barrio bar : listadobarrio) {
                        if (bar.getIntcodigobarrio() == getBarrio().getIntcodigobarrio()) {
                            listaBarrios.add(bar);

                        }
                    }
                }
                break;
            case 2:

                if (!verificarVereda(getVereda().getIntcodigovereda())) {
                    for (Vereda ver : listadovereda) {
                        if (ver.getIntcodigovereda() == getVereda().getIntcodigovereda()) {
                            listaVeredas.add(ver);

                        }
                    }
                    break;
                }
        }
    }

    public void obtenerPuntopordireccion() {
        //Marcador marke = geocode.obtenerMarcadorporDireccion(address);

        try {
            Location loc = GenericServicesFactory.getLocationService().getLocationFromAddress(address);
//            System.out.println("loc = " + loc.getLatitude());
//            System.out.println("loc long = " + loc.getLongitude());
            if (loc != null) {
                Marcador marke = new Marcador();
                marke.setDraggable("true");
                marke.setTipo(tiposelec);
                marke.setConverterMessage("/" + getSessionBeanCobra().getBundle().getString("versioncobra") + "/resources/images/marker.png");
                marke.setLongitude(loc.getLongitude().toString());
                marke.setLatitude(loc.getLatitude().toString());
                listamarcadores.add(marke);
                redibujarmapa = false;
                verConfirmar = true;
            } else {
                //mensaje no encontro con la direccion suministrada
                address = "";
                verConfirmar = false;
                FacesUtils.addInfoMessage("La búsqueda no arrojó ningun resultado");
            }
//            if (marke != null) {
//                marke.setDraggable("true");
//                marke.setTipo(tiposelec);
//                marke.setConverterMessage("/" + getSessionBeanCobra().getBundle().getString("versioncobra") + "/resources/images/marker.png");
//                listamarcadores.add(marke);
//                redibujarmapa = false;
//                verConfirmar = true;
//            } else {
//                //mensaje no encontro con la direccion suministrada
//                address = "";
//                verConfirmar = false;
//            }
        } catch (Exception ex) {
            Logger.getLogger(IngresarNuevaObra.class.getName()).log(Level.SEVERE, null, ex);
            address = "";
            verConfirmar = false;
            FacesUtils.addInfoMessage("La búsqueda no arrojó ningun resultado");
        }

    }

    /**
     * Método para verificar si el proyecto pertenece a una estrategia, y
     * verficar si posee marcos logicos asociados y verificar si posee
     * indicadores asociados a los marcos logicos crear los avances
     * planificacion
     */
    private boolean tieneAvancesPlanificados() {
        List<Avanceplanificacionrelacionmarcologicoindicador> listAvancPlani = getSessionBeanCobra().getMarcoLogicoService().encontrarAvancePlanificadosByObra(getObranueva().getIntcodigoobra());

        if (listAvancPlani.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public void crearPeriodosAvanceMarcoLogico() {
        List<Relacionmarcologicoindicador> lisrel = getSessionBeanCobra().getMarcoLogicoService().encontrarRelMarcoIndXcodigoObra(getObranueva().getIntcodigoobra());

        if (tieneAvancesPlanificados() != true) {

            for (Relacionmarcologicoindicador relac : lisrel) {

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

                Calendar fecha = Calendar.getInstance();
                fecha.setTime(getObranueva().getDatefeciniobra());
                while (fecha.getTime().compareTo(getObranueva().getDatefecfinobra()) <= 0) {
                    fecha.add(Calendar.DATE, +(per));
                    Avanceplanificacionrelacionmarcologicoindicador avanceplan = new Avanceplanificacionrelacionmarcologicoindicador();
                    avanceplan.setDatefechaplanificada(fecha.getTime());
                    //avanceplan.setFkUsureporta(getSessionBeanCobra().getUsuarioObra().getUsuId());
                    avanceplan.setRelacionmarcologicoindicador(relac);
                    if (fecha.getTime().compareTo(getObranueva().getDatefecfinobra()) > 0) {
                        avanceplan.setDatefechaplanificada(getObranueva().getDatefecfinobra());
                    }
                    getSessionBeanCobra().getMarcoLogicoService().guardarAvancesByAsociaciones(avanceplan);

                }

            }

        }

    }
    public void tipoImpactoSocial (){      
       
        if (!getNuevoContratoBasico().getListimpactosocial().isEmpty()){
            for (Tipoimpactosocial imp : getNuevoContratoBasico().getListimpactosocial()){
                if (imp.getStrnombrecolumna().equals("empleos directos")){                    
                    setStrempleosdirectos(imp.getStrdescripcionimpacto());
            }else if (imp.getStrnombrecolumna().equals("empleos indirectos")){
                setStrempleosindirectos(imp.getStrdescripcionimpacto());
            }else if (imp.getStrnombrecolumna().equals("habitantes beneficiados")){
                setStrbeneficiarios(imp.getStrdescripcionimpacto());
            }
                
            }
        } else {
            setStrbeneficiarios(bundle.getString("numbeneficiarios"));
            setStrempleosdirectos(bundle.getString("numempleosdirectos"));
            setStrempleosindirectos(bundle.getString("numempleosindirectos"));
        }
        
    }
    
    /**
     * Listado de indicadores parametrizados en el sistema
     */
    private List<Indicador> listaIndicadores = new ArrayList<Indicador>();

    public List<Indicador> getListaIndicadores() {
        return listaIndicadores;
    }

    public void setListaIndicadores(List<Indicador> listaIndicadores) {
        this.listaIndicadores = listaIndicadores;
    }
    
    /**
     * Listado de indicadores asociados a la obra
     */
    private List<Indicadorobra> listaIndicadoresObra = new ArrayList<Indicadorobra>();

    public List<Indicadorobra> getListaIndicadoresObra() {
        return listaIndicadoresObra;
    }

    public void setListaIndicadoresObra(List<Indicadorobra> listaIndicadoresObra) {
        this.listaIndicadoresObra = listaIndicadoresObra;
    }
    
    /**
     * Variable temporal para gestionar el indicador
     */
    private Indicador indicador = new Indicador();

    public Indicador getIndicador() {
        return indicador;
    }

    public void setIndicador(Indicador indicador) {
        this.indicador = indicador;
    }
    
    /**
     * Indicador de obra para el objeto urbano
     */
    private Indicadorobra indicadorobraobjetourbano = new Indicadorobra();

    public Indicadorobra getIndicadorobraobjetourbano() {
        return indicadorobraobjetourbano;
    }

    public void setIndicadorobraobjetourbano(Indicadorobra indicadorobraobjetourbano) {
        this.indicadorobraobjetourbano = indicadorobraobjetourbano;
    }

    /**
     * Indicador de obra para el objeto rural
     */
    private Indicadorobra indicadorobraobjetorural = new Indicadorobra();
    
    public Indicadorobra getIndicadorobraobjetorural() {
        return indicadorobraobjetorural;
    }

    public void setIndicadorobraobjetorural(Indicadorobra indicadorobraobjetorural) {
        this.indicadorobraobjetorural = indicadorobraobjetorural;
    }
    
    /**
     * Objeto temporal para gestionar el indicador asociado a una obra
     */
    private Indicadorobra indicadorobra;

    public Indicadorobra getIndicadorobra() {
        return indicadorobra;
    }

    public void setIndicadorobra(Indicadorobra indicadorobra) {
        this.indicadorobra = indicadorobra;
    }
    
    /**
     * De acuerdo a esta variable se visualiza o no la sección urbano en la 
     * ventana de editar indicador
     */
    private boolean verSeccionUrbano;

    public boolean isVerSeccionUrbano() {
        return verSeccionUrbano;
    }

    public void setVerSeccionUrbano(boolean verSeccionUrbano) {
        this.verSeccionUrbano = verSeccionUrbano;
    }

    /**
     * De acuerdo a esta variable se visualiza o no la sección rural en la 
     * ventana de editar indicador
     */
    private boolean verSeccionRural;
    
    public boolean isVerSeccionRural() {
        return verSeccionRural;
    }

    public void setVerSeccionRural(boolean verSeccionRural) {
        this.verSeccionRural = verSeccionRural;
    }
    
    /**
     * Variable asociada al filtro de la columna correspondiente al nombre del 
     * indicador de la tabla de indicadores disponibles
     */
    private String indicadorFilter;

    public String getIndicadorFilter() {
        return indicadorFilter;
    }

    public void setIndicadorFilter(String indicadorFilter) {
        this.indicadorFilter = indicadorFilter;
    }
    
    /**
     * Variable asociada al filtro de la columna correspondiente a la primera 
     * clasificación del indicador en el sentido hoja -> tallo de la jerarquía
     * de la tabla de indicadores disponibles
     */
    private String clasificacion1Filter;

    public String getClasificacion1Filter() {
        return clasificacion1Filter;
    }

    public void setClasificacion1Filter(String clasificacion1Filter) {
        this.clasificacion1Filter = clasificacion1Filter;
    }
    
    /**
     * Variable asociada al filtro de la columna correspondiente a la segunda 
     * clasificación del indicador en el sentido hoja -> tallo de la jerarquía
     * de la tabla de indicadores disponibles
     */
    private String clasificacion2Filter;

    public String getClasificacion2Filter() {
        return clasificacion2Filter;
    }

    public void setClasificacion2Filter(String clasificacion2Filter) {
        this.clasificacion2Filter = clasificacion2Filter;
    }
    
    /**
     * Variable asociada al filtro de la columna correspondiente al nombre del 
     * indicador de la tabla de indicadores asociados
     */
    private String indicadorAsociadoFilter;

    public String getIndicadorAsociadoFilter() {
        return indicadorAsociadoFilter;
    }

    public void setIndicadorAsociadoFilter(String indicadorAsociadoFilter) {
        this.indicadorAsociadoFilter = indicadorAsociadoFilter;
    }

    /**
     * Variable asociada al filtro de la columna correspondiente a la primera 
     * clasificación del indicador en el sentido hoja -> tallo de la jerarquía
     * de la tabla de indicadores asociados
     */
    private String clasificacion1AsociadoFilter;

    public String getClasificacion1AsociadoFilter() {
        return clasificacion1AsociadoFilter;
    }

    public void setClasificacion1AsociadoFilter(String clasificacion1AsociadoFilter) {
        this.clasificacion1AsociadoFilter = clasificacion1AsociadoFilter;
    }

    /**
     * Variable asociada al filtro de la columna correspondiente a la segunda 
     * clasificación del indicador en el sentido hoja -> tallo de la jerarquía
     * de la tabla de indicadores asociados
     */
    private String clasificacion2AsociadoFilter;

    public String getClasificacion2AsociadoFilter() {
        return clasificacion2AsociadoFilter;
    }

    public void setClasificacion2AsociadoFilter(String clasificacion2AsociadoFilter) {
        this.clasificacion2AsociadoFilter = clasificacion2AsociadoFilter;
    }
    
    /**
     * Constante que representa un objeto urbano
     */
    private final Objetoindicador objetoUrbano = new Objetoindicador(1, "Urbano");
    /**
     * Constante que representa un objeto rural
     */
    private final Objetoindicador objetoRural = new Objetoindicador(2, "Rural");
    
    /**
     * Ordena las listas de indicadores disponibles e indicadores asociados
     * presentada en las tablas de la vista
     */
    public void ordenarListas() {
        Collections.sort(listaIndicadores);
        Collections.sort(listaIndicadoresObra);
    }
    
    /**
     * Método ejecutado cuando se selecciona el paso Ingresar Indicadores
     * @return 
     */
    public String pasoIngresarIndicadores() {
        listaIndicadores = getSessionBeanCobra().getCobraService().encontrarIndicadores();
        listaIndicadoresObra = getSessionBeanCobra().getCobraService().encontrarIndicadoresObra(obranueva.getIntcodigoobra());
        for(Indicadorobra ind : listaIndicadoresObra) {
            listaIndicadores.remove(ind.getIndicador());
            ind.setObra(obranueva);
        }
        ordenarListas();
        return "IndicadoresProyecto";
    }
    
    /**
     * Validar si el Paso de Asociar Contrato esta bien diligenciado
     *
     * @return null
     */
    public String pasoIndicadores() {
        int i = 0;
        while (i < listaImagenesevolucionobra.size()) {
            if (listaImagenesevolucionobra.get(i).getTipoimagen().getIntidtipoimagen() == 1) {
                if(Boolean.valueOf(Propiedad.getValor("vermoduloindicadores"))) {
                    return pasoIngresarIndicadores();
                } else {
                    return "datosAsociarContrato";
                }
            }
            i++;
        }
        FacesUtils.addErrorMessage(bundle.getString("debeadjuntarimagenppal"));
        return null;
    }
    
    /**
     * Metodo que devuelve la regla de navegación para regresar al módulo de 
     * indicadores
     * @return Regla de navegación evaluada por el faces-config
     */
    public String pasoVolverAIndicadores() {
        if(Boolean.valueOf(Propiedad.getValor("vermoduloindicadores"))) {
            return pasoIngresarIndicadores();
        } else {
            return "datosGenerarImagenes";
        }
    }
    
    /**
     * Acción ejecutada cuando se selecciona un registro de la tabla de 
     * indicadores
     * @param indicador Indicador seleccionado de la tabla
     */
    public void seleccionarIndicadorAction(Indicador indicador) {
        this.indicador = indicador;
        indicadorobraobjetourbano = new Indicadorobra();
        indicadorobraobjetourbano.setObjetoindicador(objetoUrbano);
        indicadorobraobjetourbano.setIndicador(indicador);
        indicadorobraobjetourbano.setObra(obranueva);
        
        indicadorobraobjetorural = new Indicadorobra();
        indicadorobraobjetorural.setObjetoindicador(objetoRural);
        indicadorobraobjetorural.setIndicador(indicador);
        indicadorobraobjetorural.setObra(obranueva);
        
        establecerVerSeccionUrbano();
        establecerVerSeccionRural();
    }
    
    /**
     * Establece si el indicador de la obra de objeto urbano ya se encuentra 
     * asociado al proyecto
     */
    public void establecerVerSeccionUrbano() {
        verSeccionUrbano = true;
        for (Indicadorobra indobra : listaIndicadoresObra) {
            if(indobra.getIndicador().equals(indicador) && indobra.getObjetoindicador().getIntidobjetoindicador() == 1) {
                verSeccionUrbano = false;
            }
        }
    }
    
    /**
     * Estblece si el indicador de la obra de objeto urbano ya se encuentra 
     * asociado al proyecto
     */
    public void establecerVerSeccionRural() {
        verSeccionRural = true;
        for (Indicadorobra indobra : listaIndicadoresObra) {
            if(indobra.getIndicador().equals(indicador) && indobra.getObjetoindicador().getIntidobjetoindicador() == 2) {
                verSeccionRural = false;
            }
        }
    }
    
    /**
     * Ingresa el indicador a la lista de indicadores del proyecto
     */
    public void ingresarIndicadorAction() {
        if(verSeccionUrbano) {
            ingresarIndicador(indicadorobraobjetourbano);
        }
        if(verSeccionRural) {
            ingresarIndicador(indicadorobraobjetorural);
        }
        ordenarListas();
    }
    public void ingresarIndicador(Indicadorobra indicadorobra) {
        listaIndicadoresObra.add(indicadorobra);
        listaIndicadores.remove(indicadorobra.getIndicador());
        getSessionBeanCobra().getCobraService().guardarIndicadorObra(indicadorobra);
    }
    
    /**
     * Establece la referencia del indicador seleccionado para eliminar
     * @param indicadorobra Indicador seleccionado de la tabla
     */
    public void eliminarIndicadorObraAction(Indicadorobra indicadorobra) {
        this.indicadorobra = indicadorobra;
    }
    
    /**
     * Realiza la eliminación del indicador
     */
    public void aceptarEliminarIndicadorObraAction() {
        if(!listaIndicadores.contains(indicadorobra.getIndicador())) {
            listaIndicadores.add(indicadorobra.getIndicador());
        }
        listaIndicadoresObra.remove(indicadorobra);
        getSessionBeanCobra().getCobraService().eliminarIndicadorObra(indicadorobra);
        Collections.sort(listaIndicadores);
        Collections.sort(listaIndicadoresObra);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * Selector de provincias
     */
    private SelectItem[] provinciasSelectItem;

    public SelectItem[] getProvinciasSelectItem() {
        return provinciasSelectItem;
    }

    public void setProvinciasSelectItem(SelectItem[] provinciasSelectItem) {
        this.provinciasSelectItem = provinciasSelectItem;
    }

    /**
     * Código de la provincia
     */
    private String codProvincia;

    public String getCodProvincia() {
        return codProvincia;
    }

    public void setCodProvincia(String codProvincia) {
        this.codProvincia = codProvincia;
    }

    /**
     * Listado de provincias parametrizadas en el sistema
     */
    private List<Localidad> provincias;

    public List<Localidad> getProvincias() {
        return provincias;
    }

    public void setProvincias(List<Localidad> provincias) {
        this.provincias = provincias;
    }

    
    /**
     * Metodo encargado de llenar los datos para el selector de provincias
     * @return 
     */
    public String llenarProvincias() {
        provincias = getSessionBeanCobra().getCobraService().encontrarLocalidadesPorTipolocalidad(Tipolocalidad.ID_PROVINCIA);
        provinciasSelectItem = cargarSelectItemLocalidad(provinciasSelectItem, provincias);
        return null;
    }
    
    /**
     * Agrega la provincia a la lista de localidades a adicionar
     */
    public void agregarProvincia() {
        for (Localidad localidad : provincias) {
            if (localidad.getStrcodigolocalidad().compareTo(codProvincia) == 0) {
                for (Localidad localidadAsociada : listaLocalidades) {
                    if(localidadAsociada.getStrcodigolocalidad().equals(localidad.getStrcodigolocalidad())) {
                        FacesUtils.addErrorMessage(Propiedad.getValor("localidadyaasociadaerror"));
                        return;
                    }
                }
                listaLocalidades.add(localidad);
            }
        }
//        provinciasSelectItem = cargarSelectItemLocalidad(provinciasSelectItem,provincias);
    }
    
    /**
     * Selector de corregimients
     */
    private SelectItem[] corregimientsSelectItem;

    public SelectItem[] getCorregimientsSelectItem() {
        return corregimientsSelectItem;
    }

    public void setCorregimientsSelectItem(SelectItem[] corregimientsSelectItem) {
        this.corregimientsSelectItem = corregimientsSelectItem;
    }

    /**
     * Código de la corregimient
     */
    private String codCorregimient;

    public String getCodCorregimient() {
        return codCorregimient;
    }

    public void setCodCorregimient(String codCorregimient) {
        this.codCorregimient = codCorregimient;
    }

    /**
     * Listado de corregimients parametrizadas en el sistema
     */
    private List<Localidad> corregimients;

    public List<Localidad> getCorregimients() {
        return corregimients;
    }

    public void setCorregimients(List<Localidad> corregimients) {
        this.corregimients = corregimients;
    }

    
    /**
     * Metodo encargado de llenar los datos para el selector de corregimients
     * @return 
     */
    public String llenarCorregimients() {
        corregimients = getSessionBeanCobra().getCobraService().encontrarLocalidadesPorTipolocalidad(Tipolocalidad.ID_CORREGIMIENTO);
        corregimientsSelectItem = cargarSelectItemLocalidad(corregimientsSelectItem, corregimients);
        return null;
    }
    
    /**
     * Agrega la corregimient a la lista de localidades a adicionar
     */
    public void agregarCorregimient() {
        for (Localidad localidad : corregimients) {
            if (localidad.getStrcodigolocalidad().compareTo(codCorregimient) == 0) {
                for (Localidad localidadAsociada : listaLocalidades) {
                    if(localidadAsociada.getStrcodigolocalidad().equals(localidad.getStrcodigolocalidad())) {
                        FacesUtils.addErrorMessage(Propiedad.getValor("localidadyaasociadaerror"));
                        return;
                    }
                }
                listaLocalidades.add(localidad);
            }
        }
//        corregimientsSelectItem = cargarSelectItemLocalidad(corregimientsSelectItem,corregimients);
    }
    
    /**
     * Selector de cars
     */
    private SelectItem[] carsSelectItem;

    public SelectItem[] getCarsSelectItem() {
        return carsSelectItem;
    }

    public void setCarsSelectItem(SelectItem[] carsSelectItem) {
        this.carsSelectItem = carsSelectItem;
    }

    /**
     * Código de la car
     */
    private String codCar;

    public String getCodCar() {
        return codCar;
    }

    public void setCodCar(String codCar) {
        this.codCar = codCar;
    }

    /**
     * Listado de cars parametrizadas en el sistema
     */
    private List<Localidad> cars;

    public List<Localidad> getCars() {
        return cars;
    }

    public void setCars(List<Localidad> cars) {
        this.cars = cars;
    }

    
    /**
     * Metodo encargado de llenar los datos para el selector de cars
     * @return 
     */
    public String llenarCars() {
        cars = getSessionBeanCobra().getCobraService().encontrarLocalidadesPorTipolocalidad(Tipolocalidad.ID_CAR);
        carsSelectItem = cargarSelectItemLocalidad(carsSelectItem, cars);
        return null;
    }
    
    /**
     * Agrega la car a la lista de localidades a adicionar
     */
    public void agregarCar() {
        for (Localidad localidad : cars) {
            if (localidad.getStrcodigolocalidad().compareTo(codCar) == 0) {
                for (Localidad localidadAsociada : listaLocalidades) {
                    if(localidadAsociada.getStrcodigolocalidad().equals(localidad.getStrcodigolocalidad())) {
                        FacesUtils.addErrorMessage(Propiedad.getValor("localidadyaasociadaerror"));
                        return;
                    }
                }
                listaLocalidades.add(localidad);
            }
        }
//        carsSelectItem = cargarSelectItemLocalidad(carsSelectItem,cars);
    }
    
    public void buscarInterventor(){
        aplicaFiltroInterventor = false;
        if (nombre.length() != 0) {
            //   listaContratista.clear();
            aplicaFiltroInterventor = true;
        }
        primerosInterventores();
    }
    
    public void primerosTerceros() {
        int fin = listaTotalTerceros.size()>=10 ? 9 : listaTotalTerceros.size()-1;
            
        
        if(isIsSupervisor()){
            listaSupervisores = rangoTerceros(listaTotalTerceros, 0, fin);
        }else{
            listaInterventores = rangoTerceros(listaTotalTerceros, 0, fin);
        }
        
        setPrimeraPagina(false);
        setAnteriorPagina(false);
        pagina = 1;
        int round = Math.round(listaTotalTerceros.size()/10);
        if(round*10<listaTotalTerceros.size()){
            round++;
        }
        totalpaginas = round;
        if(listaTotalTerceros.size()<=10){
            setSiguientePagina(false);
            setUltimoPagina(false);
        }else{
            setSiguientePagina(true);
            setUltimoPagina(true);
        }
    }
    
    public void anterioresTerceros() {
        pagina--;
        int fin = pagina*10-1;
        int ini = (pagina-1)*10;
        
        if(isIsSupervisor()){
            listaSupervisores = rangoTerceros(listaTotalTerceros, ini, fin);
        }else{
            listaInterventores = rangoTerceros(listaTotalTerceros, ini, fin);
        }
        
        
        if(pagina == 1){
            setPrimeraPagina(false);
            setAnteriorPagina(false);
        }else{
            setPrimeraPagina(true);
            setAnteriorPagina(true);
        }
        
        if(pagina<totalpaginas){
            setSiguientePagina(true);
            setUltimoPagina(true);
        }else{
            setSiguientePagina(false);
            setUltimoPagina(false);
        }
    }
    public void siguienteTerceros() {
        pagina++;
        int fin = pagina*10 > listaTotalTerceros.size() ? listaTotalTerceros.size()-1 : pagina*10-1;
        int ini = (pagina-1)*10;
        
        if(isIsSupervisor()){
            listaSupervisores = rangoTerceros(listaTotalTerceros, ini, fin);
        }else{
            listaInterventores = rangoTerceros(listaTotalTerceros, ini, fin);
        }
        if(pagina == 1){
            setPrimeraPagina(false);
            setAnteriorPagina(false);
        }else{
            setPrimeraPagina(true);
            setAnteriorPagina(true);
        }
        
        if(pagina<totalpaginas){
            setSiguientePagina(true);
            setUltimoPagina(true);
        }else{
            setSiguientePagina(false);
            setUltimoPagina(false);
        }
    }
    
    public void ultimosTerceros() {
        pagina = totalpaginas;
        int fin = listaTotalTerceros.size()-1;
        
        if(isIsSupervisor()){
            listaSupervisores = rangoTerceros(listaTotalTerceros, fin-20 < 0 ? 0 : fin-20, fin);
        }else{
            listaInterventores = rangoTerceros(listaTotalTerceros, fin-20 < 0 ? 0 : fin-20, fin);
        }
        if(pagina == 1){
            setPrimeraPagina(false);
            setAnteriorPagina(false);
        }else{
            setPrimeraPagina(true);
            setAnteriorPagina(true);
        }
        
        if(pagina<totalpaginas){
            setSiguientePagina(true);
            setUltimoPagina(true);
        }else{
            setSiguientePagina(false);
            setUltimoPagina(false);
        }
    }
    
    public void primerosInterventores() {
        if (aplicaFiltroInterventor) {
            listaTotalTerceros = getSessionBeanCobra().getCobraService().getTerceroXGrupo(nombre, 30);
            totalfilas = listaTotalTerceros.size();
        } else {
            listaTotalTerceros = getSessionBeanCobra().getCobraService().getTerceroXGrupo(null, 30);
            totalfilas = listaTotalTerceros.size();
        }
        primerosTerceros();
    }
    public String seleccionarInterventor() {
        obranueva.setInterventor((Tercero) tablainterventores.getRowData());
        return null;
    }
    
    public void buscarSupervisor(){
        aplicaFiltroSupervisor = false;
        if (nombreSupervisor.length() != 0) {
            //   listaContratista.clear();
            aplicaFiltroSupervisor = true;
        }
        primerosSupervisores();
    }
    public void primerosSupervisores() {
        if (aplicaFiltroSupervisor) {
            listaTotalTerceros = getSessionBeanCobra().getCobraService().getTerceroXGrupo(nombreSupervisor, 28);
            totalfilas = listaTotalTerceros.size();
        } else {
            listaTotalTerceros = getSessionBeanCobra().getCobraService().getTerceroXGrupo(null, 28);
            totalfilas = listaTotalTerceros.size();
        }
        
        primerosTerceros();
        
    }
    public String seleccionarSupervisor() {
        obranueva.setSupervisor((Tercero) tablasupervisores.getRowData());
        return null;
    }
            
    /**
     * Selector de cuencas
     */
    private SelectItem[] cuencasSelectItem;

    public SelectItem[] getCuencasSelectItem() {
        return cuencasSelectItem;
    }

    public void setCuencasSelectItem(SelectItem[] cuencasSelectItem) {
        this.cuencasSelectItem = cuencasSelectItem;
    }

    /**
     * Código de la cuenca
     */
    private String codCuenca;

    public String getCodCuenca() {
        return codCuenca;
    }

    public void setCodCuenca(String codCuenca) {
        this.codCuenca = codCuenca;
    }

    /**
     * Listado de cuencas parametrizadas en el sistema
     */
    private List<Localidad> cuencas;

    public List<Localidad> getCuencas() {
        return cuencas;
    }

    public void setCuencas(List<Localidad> cuencas) {
        this.cuencas = cuencas;
    }

    
    /**
     * Metodo encargado de llenar los datos para el selector de cuencas
     * @return 
     */
    public String llenarCuencas() {
        cuencas = getSessionBeanCobra().getCobraService().encontrarLocalidadesPorTipolocalidad(Tipolocalidad.ID_CUENCA);
        cuencasSelectItem = cargarSelectItemLocalidad(cuencasSelectItem, cuencas);
        return null;
    }
    
    /**
     * Agrega la cuenca a la lista de localidades a adicionar
     */
    public void agregarCuenca() {
        for (Localidad localidad : cuencas) {
            if (localidad.getStrcodigolocalidad().compareTo(codCuenca) == 0) {
                for (Localidad localidadAsociada : listaLocalidades) {
                    if(localidadAsociada.getStrcodigolocalidad().equals(localidad.getStrcodigolocalidad())) {
                        FacesUtils.addErrorMessage(Propiedad.getValor("localidadyaasociadaerror"));
                        return;
                    }
                }
                listaLocalidades.add(localidad);
            }
        }
//        cuencasSelectItem = cargarSelectItemLocalidad(cuencasSelectItem,cuencas);
    }
    
    /**
     * Actualiza la lista de localidades específicas disponibles teniendo en 
     * cuenta aquellas que ya han sido asociadas al proyecto
     * @param localidadesEsp Lista de localidades específicas
     */
    public void actualizarLocalidadesEspDisponibles(List<Localidad> localidadesEsp) {
        List<Localidad> localidadesAEliminar = new ArrayList<Localidad>();
        for (Localidad localidad : listaLocalidades) {
            for (Localidad localidadEsp : localidadesEsp) {
                if(localidadEsp.getStrcodigolocalidad().equals(localidad.getStrcodigolocalidad())) {
                    localidadesAEliminar.add(localidadEsp);
                }
            }
        }
        localidadesEsp.removeAll(localidadesAEliminar);
    }
    
    /**
     * Carga la lista de selección proporcionada con la lista de localidades
     * proporcionada
     * @param localidadesSelectItem Lista de selección
     * @param localidadesEsp Localidades a cargar en la lista
     * @return Lista de selección cargada
     */
    public SelectItem[] cargarSelectItemLocalidad(SelectItem[] localidadesSelectItem, List<Localidad> localidadesEsp) {
        actualizarLocalidadesEspDisponibles(localidadesEsp);
        localidadesSelectItem = new SelectItem[localidadesEsp.size()];
        int i = 0;
        for (Localidad localidad : localidadesEsp) {
            SelectItem selectItem = new SelectItem(localidad.getStrcodigolocalidad(), localidad.getStrnombrelocalidad());
            localidadesSelectItem[i++] = selectItem;
        }
        return localidadesSelectItem;
    }
    
    /**
     * Metodo ejecutado cuando se selecciona la opción de pasar a la pestaña de 
     * ubicación de la obra;
     * @return 
     */
    public String pasarUbicacionObra() {
        if(Boolean.valueOf(Propiedad.getValor("verotrostiposdelocalidad"))) {
            llenarProvincias();
            llenarCorregimients();
            llenarCars();
            llenarCuencas();
        }
        return "datosubicaciondelaObra";
    }
    
    /**
     * Variable para la gestión de una localidad
     */
    public Localidad localidad = new Localidad();

    public Localidad getLocalidad() {
        return localidad;
    }

    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
    }
    
    public long oidcodigotipolocalidad;

    public long getOidcodigotipolocalidad() {
        return oidcodigotipolocalidad;
    }

    public void setOidcodigotipolocalidad(long oidcodigotipolocalidad) {
        this.oidcodigotipolocalidad = oidcodigotipolocalidad;
    }
    
    /**
     * Método para crear una nueva localidad Específica
     */
    public void crearNuevaLocalidad() {
        localidad.setTipolocalidad(new Tipolocalidad(oidcodigotipolocalidad));
        getSessionBeanCobra().getCobraService().guardarLocalidad(localidad);
        switch ((int)localidad.getTipolocalidad().getOidcodigotipolocalidad()) {
            case (int)Tipolocalidad.ID_CAR :    
                codCar = localidad.getStrcodigolocalidad();
                cars.add(localidad);
                carsSelectItem = cargarSelectItemLocalidad(carsSelectItem, cars);
                break;
            case (int)Tipolocalidad.ID_CORREGIMIENTO :    
                codCorregimient = localidad.getStrcodigolocalidad();
                corregimients.add(localidad);
                corregimientsSelectItem = cargarSelectItemLocalidad(corregimientsSelectItem, corregimients);
                break;
            case (int)Tipolocalidad.ID_CUENCA :    
                codCuenca = localidad.getStrcodigolocalidad();
                cuencas.add(localidad);
                cuencasSelectItem = cargarSelectItemLocalidad(cuencasSelectItem, cuencas);
                break;
            case (int)Tipolocalidad.ID_PROVINCIA :    
                codProvincia = localidad.getStrcodigolocalidad();
                provincias.add(localidad);
                provinciasSelectItem = cargarSelectItemLocalidad(provinciasSelectItem, provincias);
                break;
        }
        localidad = new Localidad();
    }
    
    /**
     * Lista de tipos de localidad
     */
    public List<Tipolocalidad> tiposLocalidad = new ArrayList<Tipolocalidad>();

    public List<Tipolocalidad> getTiposLocalidad() {
        return tiposLocalidad;
    }

    public void setTiposLocalidad(List<Tipolocalidad> tiposLocalidad) {
        this.tiposLocalidad = tiposLocalidad;
    }
    
    /**
     * Selector de tipos de localidad
     */
    private SelectItem[] tiposLocalidadSelectItem;

    public SelectItem[] getTiposLocalidadSelectItem() {
        return tiposLocalidadSelectItem;
    }

    public void setTiposLocalidadSelectItem(SelectItem[] tiposLocalidadSelectItem) {
        this.tiposLocalidadSelectItem = tiposLocalidadSelectItem;
    }
    
    /**
     * Método ejecutado cuando se activa la sección de ingresar nueva localidad
     * en ubicación de nuevo proyecto
     */
    public void mostrarNuevalocalidadAction() {
        localidad = new Localidad();
        llenarTiposLocalidad();
    }
    
    /**
     * Carga la lista de selección de os codigos de localidad
     */
    public void llenarTiposLocalidad() {
        tiposLocalidad = getSessionBeanCobra().getCobraService().encontrarTiposLocalidad();
        tiposLocalidadSelectItem = new SelectItem[tiposLocalidad.size()];
        int i = 0;
        for (Tipolocalidad tipolocalidad : tiposLocalidad) {
            SelectItem selectItem = new SelectItem(tipolocalidad.getOidcodigotipolocalidad(), tipolocalidad.getStrnombretipolocalidad());
            tiposLocalidadSelectItem[i++] = selectItem;
        }
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the listaInterventores
     */
    public List<Tercero> getListaInterventores() {
        return listaInterventores;
    }

    /**
     * @param listaInterventores the listaInterventores to set
     */
    public void setListaInterventores(List<Tercero> listaInterventores) {
        this.listaInterventores = listaInterventores;
    }

    /**
     * @return the tablainterventores
     */
    public UIDataTable getTablainterventores() {
        return tablainterventores;
    }

    /**
     * @param tablainterventores the tablainterventores to set
     */
    public void setTablainterventores(UIDataTable tablainterventores) {
        this.tablainterventores = tablainterventores;
    }

    /**
     * @return the tablasupervisores
     */
    public UIDataTable getTablasupervisores() {
        return tablasupervisores;
    }

    /**
     * @param tablasupervisores the tablasupervisores to set
     */
    public void setTablasupervisores(UIDataTable tablasupervisores) {
        this.tablasupervisores = tablasupervisores;
    }

    /**
     * @return the nombreSupervisor
     */
    public String getNombreSupervisor() {
        return nombreSupervisor;
    }

    /**
     * @param nombreSupervisor the nombreSupervisor to set
     */
    public void setNombreSupervisor(String nombreSupervisor) {
        this.nombreSupervisor = nombreSupervisor;
    }

    /**
     * @return the aplicaFiltroSupervisor
     */
    public boolean isAplicaFiltroSupervisor() {
        return aplicaFiltroSupervisor;
    }

    /**
     * @param aplicaFiltroSupervisor the aplicaFiltroSupervisor to set
     */
    public void setAplicaFiltroSupervisor(boolean aplicaFiltroSupervisor) {
        this.aplicaFiltroSupervisor = aplicaFiltroSupervisor;
    }

    /**
     * @return the listaSupervisores
     */
    public List<Tercero> getListaSupervisores() {
        return listaSupervisores;
    }

    /**
     * @param listaSupervisores the listaSupervisores to set
     */
    public void setListaSupervisores(List<Tercero> listaSupervisores) {
        this.listaSupervisores = listaSupervisores;
    }

    private List<Tercero> detachTerceros(List<Object[]> terceros) {
        List<Tercero> detTerceros = new ArrayList<Tercero>();
        for(Object[] obj : terceros){
            detTerceros.add(detachTercero(obj));
        }
        return detTerceros;
    }

    private Tercero detachTercero(Object[] obj) {
        Tercero ter = new Tercero();
        ter.setIntcodigo((Integer) obj[0]);
        ter.setIntcedula(obj[1].toString());
        ter.setStrnombre(obj[2].toString());
        return ter;
    }
    
    private List<Tercero> rangoTerceros(List<Tercero> lista, int inicio, int fin) {
        List<Tercero> primeros = new ArrayList<Tercero>();
        for(int i = inicio; i<=fin; i++){
            primeros.add(lista.get(i));
        }
        return primeros;
    }

    /**
     * @return the primeraPagina
     */
    public boolean isPrimeraPagina() {
        return primeraPagina;
    }

    /**
     * @param primeraPagina the primeraPagina to set
     */
    public void setPrimeraPagina(boolean primeraPagina) {
        this.primeraPagina = primeraPagina;
    }

    /**
     * @return the anteriorPagina
     */
    public boolean isAnteriorPagina() {
        return anteriorPagina;
    }

    /**
     * @param anteriorPagina the anteriorPagina to set
     */
    public void setAnteriorPagina(boolean anteriorPagina) {
        this.anteriorPagina = anteriorPagina;
    }

    /**
     * @return the siguientePagina
     */
    public boolean isSiguientePagina() {
        return siguientePagina;
    }

    /**
     * @param siguientePagina the siguientePagina to set
     */
    public void setSiguientePagina(boolean siguientePagina) {
        this.siguientePagina = siguientePagina;
    }

    /**
     * @return the ultimoPagina
     */
    public boolean isUltimoPagina() {
        return ultimoPagina;
    }

    /**
     * @param ultimoPagina the ultimoPagina to set
     */
    public void setUltimoPagina(boolean ultimoPagina) {
        this.ultimoPagina = ultimoPagina;
    }

    /**
     * @return the isSupervisor
     */
    public boolean isIsSupervisor() {
        return isSupervisor;
    }

    /**
     * @param isSupervisor the isSupervisor to set
     */
    public void setIsSupervisor(boolean isSupervisor) {
        this.isSupervisor = isSupervisor;
    }
    
}
