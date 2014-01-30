/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.AtencionHumanitaria;

import atencionHumanitaria.service.AtencionHumanitariaServiceAble;
import co.com.interkont.cobra.to.Claseobra;
import co.com.interkont.cobra.to.Documentosolicitud;
import co.com.interkont.cobra.to.Estadodocumentacion;
import co.com.interkont.cobra.to.Estadosolicitudch;
import co.com.interkont.cobra.to.Evento;
import co.com.interkont.cobra.to.Fase;
import co.com.interkont.cobra.to.Imagensolicitud;
import co.com.interkont.cobra.to.Localidad;
import co.com.interkont.cobra.to.Obra;
import co.com.interkont.cobra.to.Periodoevento;
import co.com.interkont.cobra.to.Periodomedida;
import co.com.interkont.cobra.to.Productoah;
import co.com.interkont.cobra.to.Solicitudmaestro;
import co.com.interkont.cobra.to.Solicituddetalle;
import co.com.interkont.cobra.to.Subestadosolicitudch;
import co.com.interkont.cobra.to.Tercero;
import co.com.interkont.cobra.to.Tipoah;
import co.com.interkont.cobra.to.Tipodocumentosolicitud;
import co.com.interkont.cobra.to.Tipoevento;
import co.com.interkont.cobra.to.Tipoobra;
import co.com.interkont.cobra.to.Tipoorigen;
import co.com.interkont.cobra.to.Tipoproyecto;
import co.com.interkont.cobra.to.Urgencia;
import co.com.interkont.cobra.to.Zonaespecifica;
import cobra.Archivo;
import cobra.CargadorArchivosWeb;
import cobra.FiltroObra;
import cobra.Financiera.FiduFinanciera;
import cobra.SessionBeanCobra;
import cobra.Supervisor.AdministrarObraNew;
import cobra.Supervisor.FacesUtils;
import cobra.Supervisor.IngresarNuevaObra;import com.interkont.cobra.exception.ArchivoExistenteException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import org.richfaces.component.UIDataTable;
/**
 * Clase que permite toda la interacción con el modulo de antecion humanitaria, persistir, consultar, entre otros.
 * @author Diana Taborda
 */
public class AdminSolicitudAtencion  implements Serializable{

    /**
     * Variable para mostrar un selectitem con los departamentos
     */
    private SelectItem[] departamento;
    /**
     * Variable para mostrar un selectitem con los departamentos
     * en el filtro avanzado
     */
    private SelectItem[] departamentofiltro;
    /**
     * Variable para mostrar un selectitem de municipios
     */
    private SelectItem[] municipio;
    /**
     * Variable para mostrar un selectitem de municipios
     * que se va a utilizar en el filtro avanzado
     */
    private SelectItem[] municipiofiltro;
    /**
     * Variable para mostrar un selectitem de tipos de atención
     */
    private SelectItem[] tipoah;
    /**
     * Variable para mostrar un selectitem de tipos de familia
     */
    private SelectItem[] tipofamilia;
    /**
     * Variable para mostrar un selectitem de los tipos de documento
     */
    private SelectItem[] tipoDocumento;
    /**
     * Variable para mostrar un selectitem con los diferentes
     * eventos
     */
    private SelectItem[] evento;
    /**
     * Variable para mostrar un selectitem de los eventos y el
     * periodo a que pertenecen
     */
    private SelectItem[] periodoevento;
    /**
     * Variable para mostrar un selectitem de los productos
     */
    private SelectItem[] producto;
    /**
     * Variable para mostrar un selectitem de los tipos de eventos
     */
    private SelectItem[] problemaSelec;
    /**
     * Variable para mostrar un selectitem con los subestados
     */
    private SelectItem[] subestado;
    /**
     * Variable para mostrar un selectitem con los diferentes estados
     * que puede tener una solicitud
     */
    private SelectItem[] estadosolicitud;
    /**
     * Variable para mostrar un selectitem con los diferentes estados
     * que puede tener un documentos
     */
    private SelectItem[] estadodocumentacion;
    /**
     * Variable para mostrar un selectitem con los numeros
     * de uno a cinco para seleccionar la urgencia de la solicitud
     */
    private SelectItem[] selectUrgencia;
    /**
     * Variable para mostrar un selectitem con los solicitantes
     */
    private SelectItem[] solicitante;
    /**
     * Variable para mostrar un selectitem en zona específica
     */
    private SelectItem[] zonaespe;
    /**
     * Variable para hacer binding con la tabla que muestra los datos de
     * solicitud maestro
     */
    private UIDataTable tablaSolicitudMaestro = new UIDataTable();
    /**
     * Variable para hacer binding con la tabla que muestra los datos de
     * productos
     */
    private UIDataTable tablaProductos = new UIDataTable();
    /**
     * Variable para hacer binding con la tabla que muestra los datos de
     * los documentos
     */
    private UIDataTable tablaDocumentos = new UIDataTable();
    /**
     * Variable para hacer binding con la tabla que muestra las imagenes de una solicitud
     */
    private UIDataTable tablaImagenesatencion = new UIDataTable();
    /**
     * Variable para hacer binding con la tabla que muestra los movimientos
     * financieros que ha tenido una solicitud
     */
    private UIDataTable tablamovimientos = new UIDataTable();
    /**
     * Variable para hacer binding con la tabla que se muestra en el home
     * con el listado de soliciutdes
     */
    private UIDataTable tablaproyectosah = new UIDataTable();
    /**
     * Variable para almacenar el codigo del departamento seleccionado en el selectitem
     */
    private String codDepartamento = "";
    /**
     * Variable para almacenar el nombre que el usuario da al video
     */
    private String nombreVideo = "";
    /**
     * Variable para almacenar la ruta donde queda el video
     */
    private String pathVideo = "";
    /**
     * Variable para almacenar la ruta donde queda el acta de la solicitud
     */
    private String pathActa = "";
    /**
     * Variable para almacenar la ruta donde queda la imagen
     */
    private String pathImagen = "";
    /**
     * Variable para almacenar la ruta donde queda un documento de la solicitud
     */
    private String pathCarta = "";
    /**
     * Variable utlizada para mostrar mensajes de validación.
     */
    private String mensaje;
    /**
     * Variable utlizada para mandar como parametro
     * para filtrar por palabra clave
     */
    private String txtPalabraClave = "";
    /**
     * Variable utlizada para contar las solicitudes
     * que se encuentran registradas en el sistema
     */
    private String cantidadSolicitides = "";
    /**
     * Variable utlizada para contar las solicitudes
     * que se encuentran registradas en el sistema y en estado aprobadas
     */
    private String solicitudesAprobadas = "";
    /**
     * Variable utlizada para almacenar el municipio
     * seleccionado en el selectitem
     */
    private String strmunicipio = "";
    /**
     * Variable utlizada para comparar los diferentes estados de los documentos
     */
    private int estadodoc;
    /**
     * Variable utlizada para saber a que grupo pertenece el usuario logueado
     */
    private int grupousu;
    /**
     * Variable utlizada para contar las filas de la tabla del home
     * Es utilizada para el paginador
     */
    private int totalfilas = 0;
    /**
     * Variable utlizada para contar las paginas que tiene la tabla del home
     * Es utilizada para el paginador
     */
    private int totalpaginas = 0;
    /**
     * Variable utlizada para mostrar en que pagina se encuentra de la tabla
     * Es utilizada para el paginador
     */
    private int pagina = 0;
    /**
     * Utilizada para mostrar si es zona especifica o por departamentos
     */
    private int vistazonas=0;
    /**
     * Variable utlizada para almacenar el tipo de familia que se selecciono en el selecitem
     */
    private long tipofamiliavalue;
    /**
     * Variable utlizada para almacenar el estado en que se encuentra la solicitud
     */
    private long estado;
    /**
     * Variable utlizada para saber si ya se subio el video
     */
    private boolean hayvideo = false;
    /**
     * Variable utlizada para mostrar o no, el listado de municipios
     */
    private boolean vermunicipio = false;
    /**
     * Variable utlizada para saber si se esta guardando o modificando la solicitud
     */
    private boolean guardaromodifica = false;
    /**
     * Variable utlizada para saber si se esta aplicando el filtro
     */
    private boolean aplicafiltro = false;
    /**
     * Variable utlizada para habilitar boton "ultimo" del paginador
     */
    private boolean verultimosreales;
    /**
     * Variable utlizada para habilitar boton "anterior" del paginador
     */
    private boolean veranteriorreales;
    /**
     * Variable utlizada para deshabilitar campos del formulario de crear solicitud
     */
    private boolean deshformulario = false;
    /**
     * Variable utlizada para deshabilitar el campo de categoria
     */
    private boolean deshcategoria = false;
    /**
     * Variable utlizada para saber si se guardo la solicitud correctamente.
     */
    private boolean guardar = false;
    /**
     * Variable utlizada para saber si se sugiere el precio del producto en la caja de texto
     */
    private boolean sugerir = false;
    /**
     * Variable utlizada para mostrar si ya la solicitud fue aprobada
     * por el alto funcionario.
     */
    private boolean aprobacionAltofun = false;
    /**
     * Variable utlizada para saber si el usuario puede
     * registrar solicitudes
     */
    private boolean regisolicitud;
    /**
     * Variable utlizada para saber si se muestran los movimientos financieros
     * que ha tenido la solicitud
     */
    private boolean mostrarmov = false;
    /**
     * Variable utlizada para saber si se esta editando la solicitud
     */
    private boolean editarsolicitud = false;
    /**
     * Variable utlizada para saber si se muestran las solicitudes
     */
    private boolean versolicitudes = false;
    /**
     * Variable utlizada para almacenar el valor total del item
     */
    private BigDecimal numvalortotalitem;
    /**
     * Variable utlizada para almacenar el valor de la solicitud
     */
    private BigDecimal valorSolicitud;
    /**
     * Variable utlizada para almacenar el valor del porcentaje de interventoria
     */
    private BigDecimal valorporcentaje = BigDecimal.ZERO;
    /**
     * Variable para calcular el total de las familias que se van atender
     */
    private BigDecimal totalfamilias = BigDecimal.ZERO;
    /**
     * Inicializacion de Listado de productos
     */
    private List<Productoah> listadoProductos = new ArrayList<Productoah>();
    /**
     * Inicializacion de Listado de productos reales
     */
    private List<Productoah> listProdReal = new ArrayList<Productoah>();
    /**
     * Inicializacion de Listado de solicitantes
     */
    private List<Tercero> listaSolicitante = new ArrayList<Tercero>();
    /**
     * Inicializacion de el bean de subir archivos para las imagenes
     */
    private CargadorArchivosWeb subirImagenAtencion = new CargadorArchivosWeb();
    /**
     * Inicializacion de el bean de subir archivos para los videos
     */
    private CargadorArchivosWeb subirVideoAtencion = new CargadorArchivosWeb();
    /**
     * Inicializacion de el bean de subir archivos para los documentos
     */
    private CargadorArchivosWeb subirDocumento = new CargadorArchivosWeb();
    /**
     * Inicializacion de el bean de subir archivos para el acta
     */
    private CargadorArchivosWeb subirActa = new CargadorArchivosWeb();
    /**
     * Inicializacion de el bean de subir archivos para la carta financiera
     */
    private CargadorArchivosWeb subirCartaFinanciera = new CargadorArchivosWeb();
    /**
     * Inicializacion de el bean bundle
     */
    private ResourceBundle bundle = getSessionBeanCobra().getBundle();
    /**
     * Inicializacion de el objeto Solicitud Maestro
     */
    private Solicitudmaestro solitemp = new Solicitudmaestro();
    /**
     * Inicializacion de el objeto Solicitud Maestro que se va a borrar
     */
    private Solicitudmaestro solicitudaborrar;
    /**
     * Inicializacion de el filtro avanzado de atencion
     */
    private FiltroObra filtroAh = new FiltroObra();

    /*
     * Variable que identifica que no se puedo guardar la solicitud por que no tiene contacto
     */
    private boolean noguardo=false;

    /**
     *Inicio de los Set y Get de las variables anteriores
     */
    public BigDecimal getTotalfamilias() {
        return totalfamilias;
    }

    public void setTotalfamilias(BigDecimal totalfamilias) {
        this.totalfamilias = totalfamilias;
    }

    public BigDecimal getValorporcentaje() {
        return valorporcentaje;
    }

    public void setValorporcentaje(BigDecimal valorporcentaje) {
        this.valorporcentaje = valorporcentaje;
    }

    public Solicitudmaestro getSolicitudaborrar() {
        return solicitudaborrar;
    }

    public void setSolicitudaborrar(Solicitudmaestro solicitudaborrar) {
        this.solicitudaborrar = solicitudaborrar;
    }

    public SelectItem[] getMunicipiofiltro() {
        return municipiofiltro;
    }

    public void setMunicipiofiltro(SelectItem[] municipiofiltro) {
        this.municipiofiltro = municipiofiltro;
    }

    public SelectItem[] getDepartamentofiltro() {
        return departamentofiltro;
    }

    public void setDepartamentofiltro(SelectItem[] departamentofiltro) {
        this.departamentofiltro = departamentofiltro;
    }

    public FiltroObra getFiltroAh() {
        return filtroAh;
    }

    public void setFiltroAh(FiltroObra filtroAh) {
        this.filtroAh = filtroAh;
    }

    public boolean isVersolicitudes() {
        return versolicitudes;
    }

    public void setVersolicitudes(boolean versolicitudes) {
        this.versolicitudes = versolicitudes;
    }

    public String getStrmunicipio() {
        return strmunicipio;
    }

    public void setStrmunicipio(String strmunicipio) {
        this.strmunicipio = strmunicipio;
    }

    public SelectItem[] getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(SelectItem[] solicitante) {
        this.solicitante = solicitante;
    }

    public List<Tercero> getListaSolicitante() {
        return listaSolicitante;
    }

    public void setListaSolicitante(List<Tercero> listaSolicitante) {
        this.listaSolicitante = listaSolicitante;
    }

    public boolean isEditarsolicitud() {
        return editarsolicitud;
    }

    public void setEditarsolicitud(boolean editarsolicitud) {
        this.editarsolicitud = editarsolicitud;
    }

    public Solicitudmaestro getSolitemp() {
        return solitemp;
    }

    public void setSolitemp(Solicitudmaestro solitemp) {
        this.solitemp = solitemp;
    }

    public UIDataTable getTablaproyectosah() {
        return tablaproyectosah;
    }

    public void setTablaproyectosah(UIDataTable tablaproyectosah) {
        this.tablaproyectosah = tablaproyectosah;
    }

    public boolean isMostrarmov() {
        return mostrarmov;
    }

    public void setMostrarmov(boolean mostrarmov) {
        this.mostrarmov = mostrarmov;
    }

    public UIDataTable getTablamovimientos() {
        return tablamovimientos;
    }

    public void setTablamovimientos(UIDataTable tablamovimientos) {
        this.tablamovimientos = tablamovimientos;
    }

    public long getEstado() {
        return estado;
    }

    public void setEstado(long estado) {
        this.estado = estado;
    }

    public boolean isRegisolicitud() {
        return regisolicitud;
    }

    public void setRegisolicitud(boolean regisolicitud) {
        this.regisolicitud = regisolicitud;
    }
    public Tercero entidadUsuario = new Tercero();

    public int getEstadodoc() {
        return estadodoc;
    }

    public void setEstadodoc(int estadodoc) {
        this.estadodoc = estadodoc;
    }

    public boolean isGuardar() {
        return guardar;
    }

    public void setGuardar(boolean guardar) {
        this.guardar = guardar;
    }

    public SelectItem[] getZonaespe() {
        return zonaespe;
    }

    public void setZonaespe(SelectItem[] zonaespe) {
        this.zonaespe = zonaespe;
    }

    public int getVistazonas() {
        return vistazonas;
    }

    public void setVistazonas(int vistazonas) {
        this.vistazonas = vistazonas;
    }
    public boolean isNoguardo() {
        return noguardo;
    }

    public void setNoguardo(boolean noguardo) {
        this.noguardo = noguardo;
    }

    /**
     * Este metodo es utilizado para cargar las entidades que tiene el usuario que se loguea
     * @return Retorna las entidades del usuario
     */
    public Tercero getEntidadUsuario() {
        List<Tercero> terceroentidad = new ArrayList<Tercero>();
        try {
            if (grupousu != 10) {
                //  terceroentidad = getSessionBeanCobra().getCobraService().encontrarEntidadesxtercero(getSessionBeanCobra().getAtencionhumanitariaService().getSolicitudmaestro().getTercero().getIntcodigo());
            } else {
                terceroentidad = getSessionBeanCobra().getUsuarioService().encontrarEntidadesxtercero(getSessionBeanCobra().getUsuarioObra(), 1, false);
            }
            entidadUsuario = getSessionBeanCobra().getAtencionhumanitariaService().enconcontrarTerceroPorId(terceroentidad.get(0).getIntcodigo());
        } catch (Exception e) {
        }


        return entidadUsuario;
    }

    public void setEntidadUsuario(Tercero entidadUsuario) {
        this.entidadUsuario = entidadUsuario;
    }

    public boolean isDeshcategoria() {
        return deshcategoria;
    }

    public void setDeshcategoria(boolean deshcategoria) {
        this.deshcategoria = deshcategoria;
    }

    public boolean isDeshformulario() {
        return deshformulario;
    }

    public void setDeshformulario(boolean deshformulario) {
        this.deshformulario = deshformulario;
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

    public boolean isAprobacionAltofun() {
        return aprobacionAltofun;
    }

    public void setAprobacionAltofun(boolean aprobacionAltofun) {
        this.aprobacionAltofun = aprobacionAltofun;
    }

    public BigDecimal getValorSolicitud() {
        return valorSolicitud;
    }

    public void setValorSolicitud(BigDecimal valorSolicitud) {
        this.valorSolicitud = valorSolicitud;
    }

    public boolean isAplicafiltro() {
        return aplicafiltro;
    }

    public void setAplicafiltro(boolean aplicafiltro) {
        this.aplicafiltro = aplicafiltro;
    }

    public boolean isGuardaromodifica() {
        return guardaromodifica;
    }

    public void setGuardaromodifica(boolean guardaromodifica) {
        this.guardaromodifica = guardaromodifica;
    }

    public String getSolicitudesAprobadas() {
        return solicitudesAprobadas;
    }

    public void setSolicitudesAprobadas(String solicitudesAprobadas) {
        this.solicitudesAprobadas = solicitudesAprobadas;
    }

    public String getCantidadSolicitides() {
        return cantidadSolicitides;
    }

    public void setCantidadSolicitides(String cantidadSolicitides) {
        this.cantidadSolicitides = cantidadSolicitides;
    }

    public long getTipofamiliavalue() {
        return tipofamiliavalue;
    }

    public void setTipofamiliavalue(long tipofamiliavalue) {
        this.tipofamiliavalue = tipofamiliavalue;
    }

    public boolean isVermunicipio() {
        return vermunicipio;
    }

    public void setVermunicipio(boolean vermunicipio) {
        this.vermunicipio = vermunicipio;
    }

    public int getGrupousu() {
        return grupousu;
    }

    public void setGrupousu(int grupousu) {
        this.grupousu = grupousu;
    }

    public List<Productoah> getListProdReal() {
        return listProdReal;
    }

    public void setListProdReal(List<Productoah> listProdReal) {
        this.listProdReal = listProdReal;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getPathCarta() {
        return pathCarta;
    }

    public void setPathCarta(String pathCarta) {
        this.pathCarta = pathCarta;
    }

    public String getPathImagen() {
        return pathImagen;
    }

    public void setPathImagen(String pathImagen) {
        this.pathImagen = pathImagen;
    }

    public SelectItem[] getSelectUrgencia() {
        return selectUrgencia;
    }

    public void setSelectUrgencia(SelectItem[] selectUrgencia) {
        this.selectUrgencia = selectUrgencia;
    }

    public BigDecimal getNumvalortotalitem() {
        return numvalortotalitem;
    }

    public void setNumvalortotalitem(BigDecimal numvalortotalitem) {
        this.numvalortotalitem = numvalortotalitem;
    }

    public boolean isSugerir() {
        return sugerir;
    }

    public void setSugerir(boolean sugerir) {
        this.sugerir = sugerir;
    }

    public String getPathActa() {
        return pathActa;
    }

    public void setPathActa(String pathActa) {
        this.pathActa = pathActa;
    }

    public String getPathVideo() {
        return pathVideo;
    }

    public void setPathVideo(String pathVideo) {
        this.pathVideo = pathVideo;
    }

    public String getNombreVideo() {
        return nombreVideo;
    }

    public void setNombreVideo(String nombreVideo) {
        this.nombreVideo = nombreVideo;
    }

    public boolean isHayvideo() {
        return hayvideo;
    }

    public void setHayvideo(boolean hayvideo) {
        this.hayvideo = hayvideo;
    }

    public CargadorArchivosWeb getSubirActa() {
        return subirActa;
    }

    public void setSubirActa(CargadorArchivosWeb subirActa) {
        this.subirActa = subirActa;
    }

    public CargadorArchivosWeb getSubirCartaFinanciera() {
        return subirCartaFinanciera;
    }

    public void setSubirCartaFinanciera(CargadorArchivosWeb subirCartaFinanciera) {
        this.subirCartaFinanciera = subirCartaFinanciera;
    }

    public CargadorArchivosWeb getSubirVideoAtencion() {
        return subirVideoAtencion;
    }

    public void setSubirVideoAtencion(CargadorArchivosWeb subirVideoAtencion) {
        this.subirVideoAtencion = subirVideoAtencion;
    }

    public UIDataTable getTablaImagenesatencion() {
        return tablaImagenesatencion;
    }

    public void setTablaImagenesatencion(UIDataTable tablaImagenesatencion) {
        this.tablaImagenesatencion = tablaImagenesatencion;
    }

    public CargadorArchivosWeb getSubirImagenAtencion() {
        return subirImagenAtencion;
    }

    public void setSubirImagenAtencion(CargadorArchivosWeb subirImagenAtencion) {
        this.subirImagenAtencion = subirImagenAtencion;
    }

    public UIDataTable getTablaDocumentos() {
        return tablaDocumentos;
    }

    public void setTablaDocumentos(UIDataTable tablaDocumentos) {
        this.tablaDocumentos = tablaDocumentos;
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public CargadorArchivosWeb getSubirDocumento() {
        return subirDocumento;
    }

    public void setSubirDocumento(CargadorArchivosWeb subirDocumento) {
        this.subirDocumento = subirDocumento;
    }

    public List<Productoah> getListadoProductos() {
        return listadoProductos;
    }

    public void setListadoProductos(List<Productoah> listadoProductos) {
        this.listadoProductos = listadoProductos;
    }

    public UIDataTable getTablaProductos() {
        return tablaProductos;
    }

    public void setTablaProductos(UIDataTable tablaProductos) {
        this.tablaProductos = tablaProductos;
    }

    public SelectItem[] getSubestado() {
        return subestado;
    }

    public void setSubestado(SelectItem[] subestado) {
        this.subestado = subestado;
    }

    public SelectItem[] getEstadodocumentacion() {
        return estadodocumentacion;
    }

    public void setEstadodocumentacion(SelectItem[] estadodocumentacion) {
        this.estadodocumentacion = estadodocumentacion;
    }

    public SelectItem[] getEstadosolicitud() {
        return estadosolicitud;
    }

    public void setEstadosolicitud(SelectItem[] estadosolicitud) {
        this.estadosolicitud = estadosolicitud;
    }

    public SelectItem[] getProblemaSelec() {
        return problemaSelec;
    }

    public void setProblemaSelec(SelectItem[] problemaSelec) {
        this.problemaSelec = problemaSelec;
    }

    public SelectItem[] getProducto() {
        return producto;
    }

    public void setProducto(SelectItem[] producto) {
        this.producto = producto;
    }

    public SelectItem[] getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(SelectItem[] tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public SelectItem[] getPeriodoevento() {
        return periodoevento;
    }

    public void setPeriodoevento(SelectItem[] periodoevento) {
        this.periodoevento = periodoevento;
    }

    public SelectItem[] getEvento() {
        return evento;
    }

    public void setEvento(SelectItem[] evento) {
        this.evento = evento;
    }

    public SelectItem[] getTipofamilia() {
        return tipofamilia;
    }

    public void setTipofamilia(SelectItem[] tipofamilia) {
        this.tipofamilia = tipofamilia;
    }

    public UIDataTable getTablaSolicitudMaestro() {
        return tablaSolicitudMaestro;
    }

    public void setTablaSolicitudMaestro(UIDataTable tablaSolicitudMaestro) {
        this.tablaSolicitudMaestro = tablaSolicitudMaestro;
    }

    public SelectItem[] getTipoah() {
        return tipoah;
    }

    public void setTipoah(SelectItem[] tipoah) {
        this.tipoah = tipoah;
    }

    public String getCodDepartamento() {
        return codDepartamento;
    }

    public void setCodDepartamento(String codDepartamento) {
        this.codDepartamento = codDepartamento;
    }

    public SelectItem[] getMunicipio() {
        return municipio;
    }

    public void setMunicipio(SelectItem[] municipio) {
        this.municipio = municipio;
    }

    public SelectItem[] getDepartamento() {
        return departamento;
    }

    public void setDepartamento(SelectItem[] departamento) {
        this.departamento = departamento;
    }

    public String getTxtPalabraClave() {
        return txtPalabraClave;
    }

    public void setTxtPalabraClave(String txtPalabraClave) {
        this.txtPalabraClave = txtPalabraClave;
    }

    /**
     * Llamado al service de Atencion Humanitaria
     * @return
     */
    public AtencionHumanitariaServiceAble getAtencionHumanitaria() {

        return getSessionBeanCobra().getAtencionhumanitariaService();
    }

    /**
     * Llamado al SessionBeanCobra
     * @return
     */
    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }

    /**
     * Llamado al bean FiduFinanciera
     * @return
     */
    protected FiduFinanciera getFiduFinanciera() {
        return (FiduFinanciera) FacesUtils.getManagedBean("FiduFinanciera");
    }

    /**
     * Llamado al bean IngresarNuevaObra
     * @return
     */
    protected IngresarNuevaObra getIngresarNuevaObra() {
        return (IngresarNuevaObra) FacesUtils.getManagedBean("Supervisor$IngresarNuevaObra");
    }
    
    protected AdministrarObraNew getAdministrarObraNew() {
        return (AdministrarObraNew) FacesUtils.getManagedBean("Supervisor$AdministrarObraNew");
    }

    /*
     * Metodo utilizado para llenar todos los combos
     */
    public void llenadodecombos() {
        if (getSessionBeanCobra().getUsuarioObra().getTercero().getTipoOrigen().getIntidtipoorigen() == 2) { //Sí es Nacional
            llenarComboDeptos();

        }
        llenarDepartamentoFiltro();
        llenarMunicipiosFiltro();
        llenarProductos1();
        llenarTipoah();
        llenarSolicitudMaestro();
        llenarTipoDocumento();
        llenarEvento();
        llenarPeriodoEvento();
        llenarProductos();
        llenarTipoFamilia();
        llenarEstadoSolicitud();
//        llenarSubestado();
        llenarTiposdeEvento();
        llenarEstadoDocumentacion();
        llenarUrgencia();
        llenarComboDeptos();
        llenarComboMunicipio();
        llenarSolicitantes();
        llenarZonaEspecifica();
        filtroAh.setIntregionespecifica(0);

    }

    /**
     * Constructor de la pagina, aqui se estan inicializando variables, ademas se esta validando el grupo del
     * usuario que ingreso a la aplicación para darle ciertos permisos.
     * @throws IOException
     */
    public AdminSolicitudAtencion() throws IOException {
        consultarDatosAtencion();
        limpiarSolicitud();
        llenadodecombos();
        txtPalabraClave = "";
        getSessionBeanCobra().getSolicitudService().setJsfusuariogrupo(getSessionBeanCobra().getUsuarioService().
                encontrarGrupoxUsuarioxModulo(getSessionBeanCobra().getUsuarioObra().getUsuId(), 1));
        switch (getSessionBeanCobra().getSolicitudService().getJsfusuariogrupo().getGrupo().getGruGid()) {
            case 10:
                grupousu = 10;
                llenarSolicitudMaestro();
                //  verificarGoberAlcalde();
                break;
            case 11:
                grupousu = 11;

                break;
            case 12:
                grupousu = 12;

                break;
            case 13:
                grupousu = 13;

                break;
            case 14:
                grupousu = 14;

                break;
        }

    }

    /**
     * Método utilizado para sumar las solicitudes que tiene el usuario y las que han sido aprobadas.
     */
    public void consultarDatosAtencion() {
        cantidadSolicitides = String.valueOf(getSessionBeanCobra().getAtencionhumanitariaService().encontrarSolicitudMaestro().size());
        solicitudesAprobadas = String.valueOf(getSessionBeanCobra().getAtencionhumanitariaService().encontrarSolicitudesMaestroAprobadas().size());

    }

    /**
     * Método utilizado para llenar el selectitem de estado de documentación.
     * @return No devuelve ningun valor
     */
    public String llenarEstadoDocumentacion() {
        getSessionBeanCobra().getAtencionhumanitariaService().setEstadodoc(getSessionBeanCobra().getAtencionhumanitariaService().encontrarEstadoDocumentacion());

        estadodocumentacion = new SelectItem[getSessionBeanCobra().getAtencionhumanitariaService().getEstadodoc().size()];
        int i = 0;
        for (Estadodocumentacion estadosdoc : getSessionBeanCobra().getAtencionhumanitariaService().getEstadodoc()) {
            SelectItem estdocu = new SelectItem(estadosdoc.getIntestadodocumentacion(), estadosdoc.getStrdescripcion());
            if (i == 0) {
                estadodoc = estadosdoc.getIntestadodocumentacion();
            }
            if (estadodoc == 1) {
                getAtencionHumanitaria().getSolicitudmaestro().setBoolestadodocumentacion(true);
            } else {
                getAtencionHumanitaria().getSolicitudmaestro().setBoolestadodocumentacion(false);
            }
            estadodocumentacion[i++] = estdocu;
        }

        return null;
    }

    /**
     * Método utilizado para llenar el selectitem de los departamentos.
     * Este método se utiliza para el filtro avanzado de atención.
     * @return No devuelve ningun valor
     */
    public String llenarDepartamentoFiltro() {
        getSessionBeanCobra().getSolicitudService().setDepartamentosfl(getSessionBeanCobra().getAtencionhumanitariaService().encontrarDepartamentos());
        departamentofiltro = new SelectItem[getSessionBeanCobra().getSolicitudService().getDepartamentosfl().size() + 1];
        int i = 1;
        departamentofiltro[0] = new SelectItem("0", "Todos");
        filtroAh.setStrcoddepto("0");
        for (Localidad depto : getSessionBeanCobra().getSolicitudService().getDepartamentosfl()) {
            SelectItem dep = new SelectItem(depto.getStrcodigolocalidad(), depto.getStrdepartamento());
            departamentofiltro[i++] = dep;
        }
        return null;
    }

    /**
     * Método utilizado para llenar el selctitem de municipios.
     * Se utiliza en el filtro avanzado de atención.
     * @return No devuelve ningun valor
     */
    public String llenarMunicipiosFiltro() {
        getSessionBeanCobra().getSolicitudService().setMunicipiosfl(getSessionBeanCobra().getAtencionhumanitariaService().encontrarMunicipios(filtroAh.getStrcoddepto()));
        municipiofiltro = new SelectItem[getSessionBeanCobra().getSolicitudService().getMunicipiosfl().size()];
        int i = 0;
        for (Localidad muni : getSessionBeanCobra().getSolicitudService().getMunicipiosfl()) {
            SelectItem mun = new SelectItem(muni.getStrcodigolocalidad(), muni.getStrmunicipio());
            municipiofiltro[i++] = mun;
        }
        return null;
    }

    /**
     * Método para llenar el selectitem de departamentos.
     */
    public void llenarComboDeptos() {

        getSessionBeanCobra().getAtencionhumanitariaService().setDepartamentos(getSessionBeanCobra().getAtencionhumanitariaService().encontrarDepartamentos());

        SelectItem[] TempDeptos = new SelectItem[getSessionBeanCobra().getAtencionhumanitariaService().getDepartamentos().size() + 1];

        TempDeptos[0] = new SelectItem(0, bundle.getString("todas"));

        int j = 1, k = 1;
        for (Iterator i = getSessionBeanCobra().getAtencionhumanitariaService().getDepartamentos().iterator(); i.hasNext();) {

            Localidad local = (Localidad) i.next();
            if (verificarDepto(local, j, TempDeptos) == false) {
                SelectItem opt = new SelectItem(local.getStrcodigolocalidad(), local.getStrdepartamento());
                TempDeptos[j] = opt;
                j++;

            }
        }
        departamento = new SelectItem[j];
        departamento[0] = new SelectItem(0, bundle.getString("todas"));
        while (k < j) {
            departamento[k] = TempDeptos[k];
            k++;
        }
    }

    /**
     * Método utilizado para verificar si el codigo de una localidad es departamento
     * @param local: Codigo de la Localidad
     * @param cant: Cantidad de departamentos
     * @param temporal : Selectitem temporal
     * @return true si es departamento o false si no es.
     */
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

    /**
     * Método utilizado para llenar el seletitem de municipios
     */
    public void llenarComboMunicipio() {

        getAtencionHumanitaria().setMunicipios(getAtencionHumanitaria().encontrarMunicipios(codDepartamento));

        SelectItem[] TempMunicipios = new SelectItem[getAtencionHumanitaria().getMunicipios().size() + 1];

        TempMunicipios[0] = new SelectItem(0, "N/A");

        int j = 1, k = 1;
        for (Iterator i = getAtencionHumanitaria().getMunicipios().iterator(); i.hasNext();) {

            Localidad local = (Localidad) i.next();
            if (verificarMunicipio(local, j, TempMunicipios) == false) {
                SelectItem opt = new SelectItem(local.getStrcodigolocalidad(), local.getStrmunicipio());
                TempMunicipios[j] = opt;
                j++;

            }
        }
        municipio = new SelectItem[j];
        municipio[0] = new SelectItem(0, "N/A");
        while (k < j) {
            municipio[k] = TempMunicipios[k];
            k++;
        }
        llenarSolicitantes();
    }

    /**
     * Método utilizado para verificar si el codigo de una localidad es municipio
     * @param local: Codigo de la Localidad
     * @param cant: Cantidad de departamentos
     * @param temporal : Selectitem temporal
     * @return true si es municipio o false si no es.
     */
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

    /**
     * Método utilizado para llenar el selectitem de los tipo de documentos
     * @return No devuelve ningun valor
     */
    public String llenarTipoDocumento() {

        getSessionBeanCobra().getAtencionhumanitariaService().setTipodocumento(getSessionBeanCobra().getAtencionhumanitariaService().encontrarTiposDocumentoSolicitud());

        tipoDocumento = new SelectItem[getSessionBeanCobra().getAtencionhumanitariaService().getTipodocumento().size()];
        int i = 0;
        for (Tipodocumentosolicitud tipo : getSessionBeanCobra().getAtencionhumanitariaService().getTipodocumento()) {
            SelectItem tipodocu = new SelectItem(tipo.getInttipodocsol(), tipo.getStrdesctipodocsol());
            if (i == 0) {
                getAtencionHumanitaria().getObjTipoDocumento().setInttipodocsol(tipo.getInttipodocsol());
            }
            tipoDocumento[i++] = tipodocu;
        }
        return null;
    }

    /**
     * Método utilizado para llenar el selectitem de tipos de atención humanitaria
     * @return No devuelve ningún valor
     */
    public String llenarTipoah() {
        getSessionBeanCobra().getAtencionhumanitariaService().setTipoah(getSessionBeanCobra().getAtencionhumanitariaService().encontrarTipoah());
        tipoah = new SelectItem[getSessionBeanCobra().getAtencionhumanitariaService().getTipoah().size()];
        int i = 0;
        for (Tipoah tipoa : getSessionBeanCobra().getAtencionhumanitariaService().getTipoah()) {
            SelectItem tipo = new SelectItem(tipoa.getOidcodigotipoah(), tipoa.getStrdescripcion());
            if (i == 0) {
                getSessionBeanCobra().getAtencionhumanitariaService().getSolicitudmaestro().getTipoah().setOidcodigotipoah(tipoa.getOidcodigotipoah());
            }
            tipoah[i++] = tipo;
        }
        return null;
    }

    /**
     * Método utilizado para llenar el selectitem de eventos
     * @return No devuelve ningun valor
     */
    public String llenarEvento() {
        getSessionBeanCobra().getAtencionhumanitariaService().setEvento(getSessionBeanCobra().getAtencionhumanitariaService().encontrarEvento());
        evento = new SelectItem[getSessionBeanCobra().getAtencionhumanitariaService().getEvento().size()];
        int i = 0;
        for (Evento event : getSessionBeanCobra().getAtencionhumanitariaService().getEvento()) {
            SelectItem even = new SelectItem(event.getIntidevento(), event.getStrdescripcion());
            if (i == 0) {
                getSessionBeanCobra().getAtencionhumanitariaService().getEventoSolicitud().setIntidevento(event.getIntidevento());
            }
            evento[i++] = even;
        }
        return null;
    }

    /**
     * Método utilizado para llenar el selectitem de estados de una solicitud
     * @return No devuelve ningún valor
     */
    public String llenarEstadoSolicitud() {
        getSessionBeanCobra().getAtencionhumanitariaService().setEstadosol(getSessionBeanCobra().getAtencionhumanitariaService().encontrarEstadoSolicitud());

        estadosolicitud = new SelectItem[getSessionBeanCobra().getAtencionhumanitariaService().getEstadosol().size() + 1];
        int i = 1;
        estadosolicitud[0] = new SelectItem("0", "Todos");
        filtroAh.setIntestadoSolicitud(0);
        for (Estadosolicitudch estadosoli : getSessionBeanCobra().getAtencionhumanitariaService().getEstadosol()) {
            SelectItem estsoli = new SelectItem(estadosoli.getOidestadosolicitud(), estadosoli.getStrnombreestadosolicitud());
//            if (i == 0) {
//                getAtencionHumanitaria().getEstadoSolicitudch().setOidestadosolicitud(estadosoli.getOidestadosolicitud());
//            }
            estadosolicitud[i++] = estsoli;
        }
        return null;
    }

    /**
     * Este método se utiliza para sugerir el precio de un producto en una caja de texto, según
     * el producto que hayan escogido en el combo.
     * @return No retorna ningún valor
     */
    public String sugerirPrecio() {
        getAtencionHumanitaria().getSolicituddetalle().setProductoah(getSessionBeanCobra().getAtencionhumanitariaService().encontrarProductoPorId(tipofamiliavalue));
        getAtencionHumanitaria().getSolicituddetalle().setNumvlrunitario(getAtencionHumanitaria().getSolicituddetalle().getProductoah().getNumvlrproductoah());

        return null;
    }

    /**
     * Método utilizado para llenar el listado de solicitudes en el home, inicializa la variable de ver solicitudes
     * y llama al paginador.
     * @return la regla de navegación que lleva al homeatencion.
     */
    public String llenarSolicitudMaestro() {

        versolicitudes = false;
        primerosreales();
        return "homesolicitud";
    }

    /**
     * Este método es utilizado para llenar las solicitudes que no han sido aprobadas.
     * se le asigna el valor a la variable estado para saber que solo se cargan las de estado 2 e invoca al paginador
     * @return la regla de navegación que lleva al homeatencion.
     */
    public String MostrarPendientes() {
        versolicitudes = true;
        estado = 2;
        primerosreales();

        return "homesolicitud";
    }

    /**
     * Este método es utilizado para llenar las solicitudes que  han sido aprobadas.
     * se le asigna el valor a la variable estado para saber que solo se cargan las de estado 1 e invoca al paginador
     * @return la regla de navegación que lleva al homeatencion.
     */
    public String MostrarAprobados() {
        versolicitudes = true;
        estado = 1;
        primerosreales();

        return "homesolicitud";
    }

    /**
     * Método utilizado para llenar el combo de tipos de familia, agrupando el producto con el tipo de familia
     * en un mismo selectitem
     * @return No retorna ningun valor
     */
    public String llenarTipoFamilia() {

        int i = 0;
        Productoah prod = new Productoah();
        while (i < listProdReal.size()) {
            if (getSessionBeanCobra().getAtencionhumanitariaService().getProductoNuevo().getOidcodigoproductoah() == listProdReal.get(i).getOidcodigoproductoah()) {
                prod = listProdReal.get(i);
            }
            i++;
        }
        i = 0;
        List<Productoah> listatempo = new ArrayList();
        while (i < listadoProductos.size()) {
            if (prod.getStrdescripcionproductoah().compareTo(listadoProductos.get(i).getStrdescripcionproductoah()) == 0) {
                listatempo.add(listadoProductos.get(i));
            }
            i++;
        }
        i = 0;
        tipofamilia = new SelectItem[listatempo.size()];
        while (i < listatempo.size()) {
            Productoah produfa = listatempo.get(i);

            SelectItem tipofami = new SelectItem(produfa.getOidcodigoproductoah(), produfa.getTipodefamilia().getStrdescripcionfamilia());
            if (i == 0) {
                tipofamiliavalue = produfa.getOidcodigoproductoah();
            }
            tipofamilia[i++] = tipofami;
        }

        getAtencionHumanitaria().getSolicituddetalle().setNumvlrunitario(getAtencionHumanitaria().getProductoNuevo().getNumvlrproductoah());
        sugerirPrecio();
        return null;
    }

    /**
     * Método utilizado para llenar el combo de periodo evento.
     * @return No devuelve ningun valor
     */
    public String llenarPeriodoEvento() {
        getSessionBeanCobra().getAtencionhumanitariaService().setPeriodoevento(getSessionBeanCobra().getAtencionhumanitariaService().encontrarPeriodoEvento(getSessionBeanCobra().getAtencionhumanitariaService().getEventoSolicitud().getIntidevento()));
        periodoevento = new SelectItem[getSessionBeanCobra().getAtencionhumanitariaService().getPeriodoevento().size()];
        int i = 0;
        for (Periodoevento perieven : getSessionBeanCobra().getAtencionhumanitariaService().getPeriodoevento()) {
            SelectItem perev = new SelectItem(perieven.getIntidperiodoevento(), perieven.getStrdescripcion());
            if (i == 0) {
                getSessionBeanCobra().getAtencionhumanitariaService().getPeriodoEvento().setIntidperiodoevento(perieven.getIntidperiodoevento());
            }
            periodoevento[i++] = perev;
        }
        return null;
    }

    /**
     * Este método es utilizado para llenar el combo de productos.
     * Tambien desde aqui se invoca el metodo de llenar tipo de familia
     * @return
     */
    public String llenarProductos() {

        listadoProductos = getSessionBeanCobra().getAtencionhumanitariaService().encontrarProductosxTipoah(getSessionBeanCobra().getAtencionhumanitariaService().getSolicitudmaestro().getTipoah().getOidcodigotipoah());
        int a = 0;
        listProdReal = new ArrayList<Productoah>();
        while (a < listadoProductos.size()) {

            if (!verificarExistencia(listadoProductos.get(a).getStrdescripcionproductoah())) {

                listProdReal.add(listadoProductos.get(a));

            }
            a++;
        }


        producto = new SelectItem[listProdReal.size()];
        int i = 0;
        for (Productoah pdo : listProdReal) {
            SelectItem pdoItem = new SelectItem(pdo.getOidcodigoproductoah(), pdo.getStrdescripcionproductoah());
            if (i == 0) {
                getSessionBeanCobra().getAtencionhumanitariaService().getProductoNuevo().setOidcodigoproductoah(pdo.getOidcodigoproductoah());
            }
            producto[i++] = pdoItem;

        }

        llenarTipoFamilia();
        return null;
    }

      public String llenarProductos1() {
      if(getFiltroAh().getInttipoayuda()!= -1){
        listadoProductos = getSessionBeanCobra().getAtencionhumanitariaService().encontrarProductosxTipoah(getFiltroAh().getInttipoayuda());
      }
      else{
         listadoProductos = getSessionBeanCobra().getAtencionhumanitariaService().encontrarProductosxTipoah(getSessionBeanCobra().getAtencionhumanitariaService().getSolicitudmaestro().getTipoah().getOidcodigotipoah());
      }
        
        int a = 0;
        listProdReal = new ArrayList<Productoah>();
        while (a < listadoProductos.size()) {

            if (!verificarExistencia(listadoProductos.get(a).getStrdescripcionproductoah())) {

                listProdReal.add(listadoProductos.get(a));

            }
            a++;
        }


        producto = new SelectItem[listProdReal.size()];
        int i = 0;
        for (Productoah pdo : listProdReal) {
            SelectItem pdoItem = new SelectItem(pdo.getOidcodigoproductoah(), pdo.getStrdescripcionproductoah());
            if (i == 0) {
                getSessionBeanCobra().getAtencionhumanitariaService().getProductoNuevo().setOidcodigoproductoah(pdo.getOidcodigoproductoah());
            }
            producto[i++] = pdoItem;

        }

        return null;
    }

    /**
     * Este método es utilizado para comparar si un producto ya existe
     * @param se recibe un string que se va a comparar con la descripcion del producto
     * @return Devuelve true si el producto existe o false si no existe.
     */
    public boolean verificarExistencia(String descripcion) {

        int i = 0;

        while (i < getListProdReal().size()) {

            if (descripcion.compareTo(getListProdReal().get(i).getStrdescripcionproductoah()) == 0) {

                return true;


            }
            i++;
        }


        return false;
    }

    /**
     * Método utiliza para llenar los combos de tipo evento
     * @return No devuelve ningun valor
     */
    public String llenarTiposdeEvento() {
        getSessionBeanCobra().getAtencionhumanitariaService().setTipoevento(getSessionBeanCobra().getAtencionhumanitariaService().encontrarTiposEvento());
        problemaSelec = new SelectItem[getSessionBeanCobra().getAtencionhumanitariaService().getTipoevento().size()];
        int i = 0;
        for (Tipoevento tipoev : getSessionBeanCobra().getAtencionhumanitariaService().getTipoevento()) {
            SelectItem tipoSelec = new SelectItem(tipoev.getIntidtipoevento(), tipoev.getStrnombretipoevento());
            if (i == 0) {
                getSessionBeanCobra().getAtencionhumanitariaService().getTipoEventoSolcitud().setIntidtipoevento(tipoev.getIntidtipoevento());
            }
            problemaSelec[i++] = tipoSelec;
        }
        return null;
    }

    /**
     * Método utilizado para llenar un selectitem de urgencia
     * @return No retorne ningún valor
     */
    public String llenarUrgencia() {

        getSessionBeanCobra().getAtencionhumanitariaService().setListadourgencia(getSessionBeanCobra().getAtencionhumanitariaService().encontrarUrgencia());
        selectUrgencia = new SelectItem[getSessionBeanCobra().getAtencionhumanitariaService().getListadourgencia().size()];
        int i = 0;
        for (Urgencia urgen : getSessionBeanCobra().getAtencionhumanitariaService().getListadourgencia()) {
            SelectItem urgenSelect = new SelectItem(urgen.getIntidurgencia(), urgen.getStrdescripcion());
            if (i == 0) {
                getAtencionHumanitaria().getTipoUrgencia().setIntidurgencia(urgen.getIntidurgencia());
            }
            selectUrgencia[i++] = urgenSelect;

        }

        return null;
    }

//    public void llenarTablaImagenAtencion() {
//        Iterator itrtImagenatencion = this.soliMaestro.getImagensolicitud().iterator();
//        this.listaImagenesSolicitud = new ArrayList<Imagensolicitud>();
//        while (itrtImagenatencion.hasNext()) {
//            Imagensolicitud img = (Imagensolicitud) itrtImagenatencion.next();
//            this.listaImagenesSolicitud.add(img);
//        }
//    }
    /**
     * Este método es utilizado para inicializar los objetos y las variables que se necesitan para crear
     * una nueva solicitud
     * @return Devuelve la regla de navegación que lleva a la pagina con el formulario de nueva solicitud
     */
    public String CrearSoliicitud() {
        getSessionBeanCobra().getAtencionhumanitariaService().getSolicitudmaestro().setPeriodoevento(new Periodoevento());
        getSessionBeanCobra().getAtencionhumanitariaService().getSolicitudmaestro().setTipoah(new Tipoah(2, "", ""));

        getSessionBeanCobra().getAtencionhumanitariaService().setActualizar(false);
        getSessionBeanCobra().getAtencionhumanitariaService().setNuevoProducto(false);
        getSessionBeanCobra().getAtencionhumanitariaService().setVerDatos(false);
        getSessionBeanCobra().getAtencionhumanitariaService().setVerboton(true);
        getSessionBeanCobra().getAtencionhumanitariaService().setGuardar(true);
        return "crear";
    }

    /**
     * Este método es utilizado para asignarle el valor al objeto de solicitud que se selecciono de la tabla
     * Inicializa las variables y objetos, ademas llena la lista de solicitudes detalle
     * @return Devuelve la regla de navegacion, que lleva al formulario de crear solicitud, pero cargando los datos
     */
    public String versolicitud() {
        Solicitudmaestro solicitud = (Solicitudmaestro) tablaSolicitudMaestro.getRowData();

        getSessionBeanCobra().getAtencionhumanitariaService().setSolicitudmaestro(solicitud);
        getSessionBeanCobra().getAtencionhumanitariaService().setListadosolicitudDetalle(getSessionBeanCobra().getAtencionhumanitariaService().encontrarSolicitudDetalle(solicitud));

        getSessionBeanCobra().getAtencionhumanitariaService().setActualizar(false);
        getSessionBeanCobra().getAtencionhumanitariaService().setNuevoProducto(false);
        getSessionBeanCobra().getAtencionhumanitariaService().setVerDatos(true);
        getSessionBeanCobra().getAtencionhumanitariaService().setVerboton(false);
        getSessionBeanCobra().getAtencionhumanitariaService().setGuardar(false);
        return "crear";
    }

    /**
     * En este método se le asigna el valor de la fila seleccionada a el objeto de solicitud maestro
 que va ser modificada, se valida si el usuario puede
     * modificar
     *
     * @param filaSeleccionada Corresponde a la fila de la que proviene la
     * acción en la tabla
     * @return devuelve la regla de navegacion de crear solicitud si puede modificar, si no retorna la regla de navegación de
     * el home.
     */
    public String identificados_Modif() {

        limpiarSolicitud();

        //SessionBeanCobra sessionBeanCobra = (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
        Solicitudmaestro solicitud = (Solicitudmaestro) tablaSolicitudMaestro.getRowData();

        getAtencionHumanitaria().setSolicitudmaestro(solicitud);
        deshformulario = false;
        editarsolicitud = true;
        boolean grupoeditar = false;
        sugerirPrecio();
        llenarProductos();

        grupoeditar = getSessionBeanCobra().getUsuarioService().verificarGruposUsuario(getSessionBeanCobra().getUsuarioObra().getUsuId(), 1, 12);
        if (grupoeditar || solicitud.getSubestadosolicitudch().getIntoidsubestadosolicitud() == 4) {
            getAtencionHumanitaria().getSolicitudmaestro().setTercero(solicitud.getTercero());


            if (solicitud.getLocalidad().getStrcodigolocalidad().length() == 5) {
                codDepartamento = solicitud.getLocalidad().getStrcodigolocalidad();
                llenarComboMunicipio();
            }
            if (solicitud.getLocalidad().getStrcodigolocalidad().length() > 5) {
                codDepartamento = solicitud.getLocalidad().getStrcodigolocalidad().substring(0, 5);
                llenarComboMunicipio();
                strmunicipio = solicitud.getLocalidad().getStrcodigolocalidad();
            }
            getAtencionHumanitaria().setListadosolicitudDetalle(getSessionBeanCobra().getAtencionhumanitariaService().encontrarSolicitudDetalle(solicitud));
            getAtencionHumanitaria().setListadocumentosolicitud(getAtencionHumanitaria().encontrarDocumentosSolicitud(getAtencionHumanitaria().getSolicitudmaestro().getOidcodigosolicitudmaestro()));
            getAtencionHumanitaria().setListadoimagensolicitud(getAtencionHumanitaria().encontrarImagenSolicitud(getAtencionHumanitaria().getSolicitudmaestro().getOidcodigosolicitudmaestro()));
            getAtencionHumanitaria().setCodigosol(true);
            
            calcularValorSolicitudDetalle();
            return "crearatencion";
        } else {
            FacesUtils.addErrorMessage("No puede modificar la solicitud ya que se encuentra  " + getAtencionHumanitaria().getSolicitudmaestro().getSubestadosolicitudch().getStrdescripcion());
            return "homesolicitud";
        }
    }

    /**
     * En este método se adicionan productos a la lista de solicitud detalle con el codigo de
     * la localidad a la que pertenece el solicitante.
     * @return No devuelve ningun valor
     */
    public String anadirItem() {

        getAtencionHumanitaria().getSolicituddetalle().setProductoah(getSessionBeanCobra().getAtencionhumanitariaService().encontrarProductoPorId(tipofamiliavalue));
        if (strmunicipio.compareTo("0") != 0) {
            getAtencionHumanitaria().getSolicituddetalle().setLocalidad(getSessionBeanCobra().getAtencionhumanitariaService().encontrarLocalidadPorId(strmunicipio));
        }
        if (strmunicipio.compareTo("0") == 0) {
            getAtencionHumanitaria().getSolicituddetalle().setLocalidad(getSessionBeanCobra().getAtencionhumanitariaService().encontrarLocalidadPorId("169"));
            getAtencionHumanitaria().getSolicituddetalle().getLocalidad().setStrmunicipio("N/A");
        }

        getAtencionHumanitaria().getSolicituddetalle().setNumvlrunitario(getAtencionHumanitaria().getSolicituddetalle().getNumvlrunitario());
        getAtencionHumanitaria().getSolicituddetalle().setNumvlrtotalitem(getAtencionHumanitaria().getSolicituddetalle().getNumvlrunitario().multiply(getAtencionHumanitaria().getSolicituddetalle().getNumcantidadsolicitadaitem()));
        getAtencionHumanitaria().getSolicituddetalle().setSolicitudmaestro(getAtencionHumanitaria().getSolicitudmaestro());
        getAtencionHumanitaria().getSolicituddetalle().setJsfUsuarioByIntusucreacion(getSessionBeanCobra().getUsuarioObra());
        getAtencionHumanitaria().getListadosolicitudDetalle().add(getAtencionHumanitaria().getSolicituddetalle());


        getAtencionHumanitaria().setSolicituddetalle(new Solicituddetalle());

        if (getAtencionHumanitaria().getListadosolicitudDetalle().size() > 0) {
            deshcategoria = true;
        }
        calcularValorSolicitudDetalle();
        return null;
    }

    /**
     * En este método se elimina de la lista el producto de la fila que el
     * usuario selecciono.
     *
     * @param filaSeleccionada Corresponde a la fila de la que proviene la
     * acción en la tabla
     * @return No devuelve ningún valor
     */
    public String eliminarItem() {
        Solicituddetalle solicituddetalle = (Solicituddetalle) tablaProductos.getRowData();

        //Solicituddetalle solicituddetalle = getAtencionHumanitaria().getListadosolicitudDetalle().get(filaSeleccionada);

        getAtencionHumanitaria().getListadosolicitudDetalle().remove(solicituddetalle);
        getAtencionHumanitaria().borrarItem(solicituddetalle);
        calcularValorSolicitudDetalle();
        if (!getAtencionHumanitaria().getListadosolicitudDetalle().isEmpty()) {
        } else {
            deshcategoria = false;
        }
        return null;
    }

    /**
     * En este método se calcula el valor de la solicitud, sumando todos los items y adicionandole el valor del
     * porcentaje de interventoria en caso de que tenga.
     */
    public void calcularValorSolicitudDetalle() {

        int i = 0;
        totalfamilias= BigDecimal.ZERO;
        getAtencionHumanitaria().getSolicitudmaestro().setNumvlrtotalparcial(BigDecimal.ZERO);
        while (i < getAtencionHumanitaria().getListadosolicitudDetalle().size()) {
            
            getAtencionHumanitaria().getSolicitudmaestro().setNumvlrtotalparcial(getAtencionHumanitaria().getSolicitudmaestro().getNumvlrtotalparcial().add(getAtencionHumanitaria().getListadosolicitudDetalle().get(i).getNumvlrtotalitem()));

             totalfamilias = totalfamilias.add(getAtencionHumanitaria().getListadosolicitudDetalle().get(i).getNumcantidadsolicitadaitem());
            i++;
        }
        if (getAtencionHumanitaria().getSolicitudmaestro().isBoolincluyeinterventoria() && sugerir) {

            getAtencionHumanitaria().getSolicitudmaestro().setFloatporcentaje(5);
            sugerir = false;


        }
        if (!getAtencionHumanitaria().getSolicitudmaestro().isBoolincluyeinterventoria()) {
            getAtencionHumanitaria().getSolicitudmaestro().setFloatporcentaje(0);
            sugerir = true;
        }
       

        valorporcentaje = getAtencionHumanitaria().getSolicitudmaestro().getNumvlrtotalparcial().multiply(
                BigDecimal.valueOf(getAtencionHumanitaria().getSolicitudmaestro().getFloatporcentaje()).setScale(6, RoundingMode.HALF_UP)).divide(BigDecimal.valueOf(100));

        getAtencionHumanitaria().getSolicitudmaestro().setNumvlrsolicitado(getAtencionHumanitaria().getSolicitudmaestro().getNumvlrtotalparcial().add(getAtencionHumanitaria().getSolicitudmaestro().getNumvlrtotalparcial().multiply(
                BigDecimal.valueOf(getAtencionHumanitaria().getSolicitudmaestro().getFloatporcentaje()).setScale(6, RoundingMode.HALF_UP)).divide(BigDecimal.valueOf(100))));

    }

    /**
     * Sube la imagen a una carpeta temporal y le cambia el nombre al archivo.
     */
    public void subirImageneAtencion() {
        String pathDoc = "";
        String realArchivoPath = "";
        String URL = "/resources/Documentos/temporal/";


        ServletContext theApplicationsServletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

        if (subirImagenAtencion.getArchivos().size() > 0) {
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
                subirImagenAtencion.guardarArchivosTemporales(realArchivoPath, false);
            } catch (ArchivoExistenteException ex) {
                Logger.getLogger(AdminSolicitudAtencion.class.getName()).log(Level.SEVERE, null, ex);
            }

            Iterator arch = subirImagenAtencion.getArchivos().iterator();
            while (arch.hasNext()) {

                Archivo nombreoriginal = (Archivo) arch.next();
                pathDoc = nombreoriginal.getOnlyName();
                String URLCONTEXTO = theApplicationsServletContext.getRealPath(URL);
                URLCONTEXTO = URLCONTEXTO + "/";

                int pos;
                String extencion = "";
                pos = pathDoc.lastIndexOf('.');
                extencion = pathDoc.substring(pos);

                File imgpp = new File(URLCONTEXTO + pathDoc);
                pathImagen = getAtencionHumanitaria().getSolicitudmaestro().getOidcodigosolicitudmaestro() + "_SOLICITUD" + extencion;
                imgpp.renameTo(new File(URLCONTEXTO + pathImagen));

                getSessionBeanCobra().getCobraService().getImagen().setStrubicacion(URLCONTEXTO + pathImagen);
                getSessionBeanCobra().getCobraService().getImagen().setStrnombrearchivo(pathImagen);


            }
        } else {
            if (pathDoc.compareTo("") != 0) {
                realArchivoPath = theApplicationsServletContext.getRealPath(pathDoc);
                File archi = new File(realArchivoPath);

                if (archi.exists()) {
                    archi.delete();

                }
                pathDoc = "";
            }
        }
    }

    /**
     * Sube el documento a una carpeta temporal
     */
    public void subirDocumento() {
        String realArchivoPath = "";
        String URL = "/resources/Documentos/Solicitud/Temporal/";

        ServletContext theApplicationsServletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        if (subirDocumento.getArchivos().size() > 0) {
            realArchivoPath = theApplicationsServletContext.getRealPath(URL);
            try {
                File carpeta = new File(realArchivoPath);
                if (!carpeta.exists()) {
                    carpeta.mkdirs();
                }
            } catch (Exception ex) {
                mensaje = (bundle.getString("nosepuedesubir"));//"Cannot upload file ");
            }
        }
        try {
            subirDocumento.guardarArchivosTemporales(realArchivoPath, false);
        } catch (ArchivoExistenteException ex) {
            Logger.getLogger(AdminSolicitudAtencion.class.getName()).log(Level.SEVERE, null, ex);
        }
        Iterator arch = subirDocumento.getArchivos().iterator();
        while (arch.hasNext()) {
            Archivo nombreoriginal = (Archivo) arch.next();
            getAtencionHumanitaria().getDocumento().setStrurldocumento(URL + nombreoriginal.getOnlyName());
        }
    }

    /**
     * Sube el acta a una carpeta temporal
     */
    public void subirActa() {
        String realArchivoPath = "";
        String URL = "/resources/Documentos/Solicitud/Temporal/";

        ServletContext theApplicationsServletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        if (subirActa.getArchivos().size() > 0) {
            realArchivoPath = theApplicationsServletContext.getRealPath(URL);
            try {
                File carpeta = new File(realArchivoPath);
                if (!carpeta.exists()) {
                    carpeta.mkdirs();
                }
            } catch (Exception ex) {
                mensaje = (bundle.getString("nosepuedesubir"));//"Cannot upload file ");
            }
        }
        try {
            subirActa.guardarArchivosTemporales(realArchivoPath, false);
        } catch (ArchivoExistenteException ex) {
            Logger.getLogger(AdminSolicitudAtencion.class.getName()).log(Level.SEVERE, null, ex);
        }
        Iterator arch = subirActa.getArchivos().iterator();
        while (arch.hasNext()) {
            Archivo nombreoriginal = (Archivo) arch.next();
            getAtencionHumanitaria().getSolicitudmaestro().setStrurlactajunta(URL + "/" + nombreoriginal.getOnlyName());
        }
    }

    /**
     * Sube el acta a una carpeta temporal
     */
    public void subirCartaFinanciera() {
        String realArchivoPath = "";
        String URL = "/resources/Documentos/Solicitud/Temporal/";

        ServletContext theApplicationsServletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        if (subirCartaFinanciera.getArchivos().size() > 0) {
            realArchivoPath = theApplicationsServletContext.getRealPath(URL);
            try {
                File carpeta = new File(realArchivoPath);
                if (!carpeta.exists()) {
                    carpeta.mkdirs();
                }
            } catch (Exception ex) {
                mensaje = (bundle.getString("nosepuedesubir"));//"Cannot upload file ");
            }
        }
        try {
            subirCartaFinanciera.guardarArchivosTemporales(realArchivoPath, false);
        } catch (ArchivoExistenteException ex) {
            Logger.getLogger(AdminSolicitudAtencion.class.getName()).log(Level.SEVERE, null, ex);
        }
        Iterator arch = subirCartaFinanciera.getArchivos().iterator();
        while (arch.hasNext()) {
            Archivo nombreoriginal = (Archivo) arch.next();
            getAtencionHumanitaria().getSolicitudmaestro().setStrurlcartaaprobacion(URL + "/" + nombreoriginal.getOnlyName());
        }
    }

    /**
     * Cambia el archivo de la carpeta temporal a la carpeta donde va a quedar.
     * @param nomarch : es el nombre del archivo a guardar
     * @param URLorigen : Ruta de la carpeta temporal donde esta el archivo
     * @param URLdestino : Ruta donde va a quedar el archivo
     * @return Retorna true si se pudo crear la carpeta de destino
     */
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
                    FacesUtils.addErrorMessage("No pudo crear carpeta ");
                }
                carpeta.renameTo(new File(ArchivoPath + "/" + nomarch));
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
//            FacesUtils.addErrorMessage(bundle.getString("nosepuedesubir"));
        }
        return false;
    }

    /**
     * Adiciona el documento a la lista e invoca el metodo que sube el documento a la carpeta temporal
     * @return No retorna ningún valor
     */
    public String subirDocumentoaLista() {
        getAtencionHumanitaria().getDocumento().setOidcodigosolicitudmaestro(getAtencionHumanitaria().getSolicitudmaestro());
        getAtencionHumanitaria().setObjTipoDocumento(getSessionBeanCobra().getAtencionhumanitariaService().encontrarTipoDocPorId(getAtencionHumanitaria().getObjTipoDocumento().getInttipodocsol()));
        subirDocumento();
        getAtencionHumanitaria().getDocumento().setTipodocumentosolicitud(getAtencionHumanitaria().getObjTipoDocumento());
        getAtencionHumanitaria().getListadocumentosolicitud().add(getAtencionHumanitaria().getDocumento());
        getAtencionHumanitaria().setDocumento(new Documentosolicitud());
        setSubirDocumento(new CargadorArchivosWeb());
        return null;
    }

    /**
     * Invoca al metodo que sube el acta a la carpeta temporal
     * @return No devuelve ningun valor.
     */
    public String subirActaaLista() {
        subirActa();
        return null;
    }

    /**
     * Invoca al metodo que sube la carta de aprobacion financiera a la carpeta temporal
     * @return No devuelve ningun valor.
     */
    public String subirAprobacionaDetalle() {
        subirCartaFinanciera();
        return null;
    }

    /**
     * Sube la imagen a la lista, valida que se haya dado nombre y que se haya adjuntado algun archivo
     * @return No retorna ningun valor
     */
    public String subirImagenaLista() {

        if (subirImagenAtencion.getArchivos().size() > 0) {

            if (getAtencionHumanitaria().getImagen().getStrdescripcion().compareTo("") != 0) {
                getAtencionHumanitaria().getImagen().setOidcodigosolicitudmaestro(getAtencionHumanitaria().getSolicitudmaestro());
                subirImageneAtencion();

                getAtencionHumanitaria().getImagen().setStrurlimagen("/resources/Documentos/temporal/" + pathImagen);

                getAtencionHumanitaria().getListadoimagensolicitud().add(getAtencionHumanitaria().getImagen());
                getAtencionHumanitaria().setImagen(new Imagensolicitud());
                setSubirImagenAtencion(new CargadorArchivosWeb());

            } else {
                FacesUtils.addErrorMessage(bundle.getString("debedarunnombre"));
            }

        } else {
            FacesUtils.addErrorMessage(bundle.getString("debeadjuntarimagen"));
        }
        return null;
    }

    /**
     * Se le asigna la ruta al video.
     * @return No devuelve ningun valor
     */
    public String subirVideoaSolicitud() {
        getAtencionHumanitaria().setRutaVideo(pathVideo);
        return null;
    }

    /**
     * Inicializa las variables y el objeto de solicitud maestro, valida si el usuario
     * puede crear la solicitud, llama metodos de sugerir precio y limpiar solicitud.
     * @return retorna la regla de navegacion al formulario de crear solicitud.
     */
    public String crearsolicitudatencion() {

        Date fecha = new Date();
        Periodoevento periodoeven = getAtencionHumanitaria().encontrarperiodoevento();

        boolean grupoif = false;

        grupoif = getSessionBeanCobra().getUsuarioService().verificarGruposUsuario(getSessionBeanCobra().getUsuarioObra().getUsuId(), 1, 12);

        if (grupoif || fecha.after(periodoeven.getDatefechainisol()) && fecha.before(periodoeven.getDatefechafinsol())) {

            regisolicitud = true;

            limpiarSolicitud();
            sugerirPrecio();
            getAtencionHumanitaria().setCodigosol(false);
            getAtencionHumanitaria().setListadoimagensolicitud(new ArrayList<Imagensolicitud>());
            setGuardaromodifica(true);
            sugerir = true;
            return "crearatencion";
        } else {

            regisolicitud = false;
            FacesUtils.addErrorMessage("Se encuentra por fuera de las fechas para solicitar");

        }

        return null;

    }

    /**
     * Le asigna el valor al objeto seleccionado de la solicitud que se va a
     * eliminar
     *
     * @param filaSeleccionada Corresponde a la fila de la que proviene la
     * acción en la tabla
     * @return No retorna ningun valor
     */
    public String SolicitudAeliminar() {
        //SessionBeanCobra sessionBeanCobra = (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
        solicitudaborrar = (Solicitudmaestro) tablaSolicitudMaestro.getRowData();
        return null;
    }

    /**
     * Elimina la solicitud de la lista y de la base de datos.
     * @return No devuelve ningun valor
     */
    public String eliminarSolicitud() {

        getAtencionHumanitaria().getSolicitudMaestro().remove(solicitudaborrar);
        getAtencionHumanitaria().borrarSolicitud(solicitudaborrar);

        primerosreales();

        return null;
    }

    /**
     * Invoca al metodo del paginar que carga las solicitudes en el home.
     * @return Retorna la regla de navegación que lleva al home atencion.
     */
    public String homeSolicitudes() {
        getAtencionHumanitaria().setCodigosol(false);
        primerosreales();

        return "homesolicitud";
    }

    /**
     * Elimina La imagen seleccionada de la lista.
     * @param filaSeleccionada Corresponde a la fila de la que proviene la
     * acción en la tabla
     * @return No devuelve ningun valor
     */
    public String eliminarImagenLista() {
        getAtencionHumanitaria().setImagen((Imagensolicitud) tablaImagenesatencion.getRowData());
//      SessionBeanCobra sessionBeanCobra = (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
//      Imagensolicitud imagensolicitud = sessionBeanCobra.getAtencionhumanitariaService().getListadoimagensolicitud().get(filaSeleccionada);
//        getAtencionHumanitaria().setImagen(imagensolicitud);
        getAtencionHumanitaria().getListadoimagensolicitud().remove(getSessionBeanCobra().getAtencionhumanitariaService().getImagen());
        return null;
    }

    /**
     * Elimina el documento seleccionado de la lista
     * @param filaSeleccionada Corresponde a la fila de la que proviene la
     * acción en la tabla
     * @return No retorna ningun valor
     */
    public String eliminarDocumentoLista() {
         getAtencionHumanitaria().setDocumento((Documentosolicitud) tablaDocumentos.getRowData());
//        SessionBeanCobra sessionBeanCobra = (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
//        Documentosolicitud documentosolicitud = sessionBeanCobra.getAtencionhumanitariaService().getListadocumentosolicitud().get(filaSeleccionada);
        //getAtencionHumanitaria().setDocumento(documentosolicitud);
        getAtencionHumanitaria().getListadocumentosolicitud().remove(getSessionBeanCobra().getAtencionhumanitariaService().getDocumento());
        return null;
    }

    /**
     * Cancela la solicitud que han digitado y limpia el formulario
     * @return la regla de navegacion que lleva al home
     */
    public String cancelarSolicitud() {
        limpiarSolicitud();
        return "cancelarSolicitudAtencion";
    }

    /**
     * Guarda la solicitud maestro, la solicitud detalle, documentos e imagenes que hayan adjuntado.
     * Valida que se digiten todos los campos obligatorios y al guardar invoca al metodo que limpia el formulario
     * @return No retorna ningun valor.
     */
    public String guardarSolicitud() {
        System.out.println("Ingresa al metodo");
        
        if(getAtencionHumanitaria().getSolicitudmaestro().getTercero().getIntcodigo()==0){
            getAtencionHumanitaria().setMensaje(bundle.getString("solicitanteat"));
            noguardo=true;
            return null;
        }       
        guardar = false;

        if (getAtencionHumanitaria().validarDatosSolicitud()) {
            if (getAtencionHumanitaria().isGuardadosol()) {
                if (codDepartamento.compareTo("0") != 0) {
                    if (grupousu == 12) {
                        if (strmunicipio.compareTo("0") == 0) {
                            getAtencionHumanitaria().getSolicitudmaestro().setLocalidad(new Localidad(codDepartamento));
                        } else if (strmunicipio.compareTo("0") != 0) {
                            getAtencionHumanitaria().getSolicitudmaestro().setLocalidad(new Localidad(strmunicipio));
                        }
                    } else {
                        getAtencionHumanitaria().getSolicitudmaestro().setLocalidad(new Localidad(getSessionBeanCobra().getUsuarioObra().getTercero().getLocalidadByStrcodigolocalidad().getStrcodigolocalidad()));
                        getAtencionHumanitaria().getSolicitudmaestro().setTercero(getSessionBeanCobra().getUsuarioObra().getTercero());
                    }
                    getAtencionHumanitaria().getSolicitudmaestro().setDatefechasolicitud(new Date());
                    getAtencionHumanitaria().getSolicitudmaestro().setDatefechacreacion(new Date());
                    getAtencionHumanitaria().getSolicitudmaestro().setStrdescripcion("");
                    getAtencionHumanitaria().getSolicitudmaestro().setSubestadosolicitudch(new Subestadosolicitudch(4, null, null));
                    getAtencionHumanitaria().getSolicitudmaestro().setTipoah(getAtencionHumanitaria().obtenerTipoatencionHumXId(getAtencionHumanitaria().getSolicitudmaestro().getTipoah().getOidcodigotipoah()));
                    getAtencionHumanitaria().getSolicitudmaestro().setPeriodoevento(new Periodoevento(getSessionBeanCobra().getAtencionhumanitariaService().getPeriodoEvento().getIntidperiodoevento(), null, null, null, null, null, true));
                    getAtencionHumanitaria().getSolicitudmaestro().setStrcodigosolicitud("0");
                    getAtencionHumanitaria().getSolicitudmaestro().setNumvlrtotalparcial(numvalortotalitem);
                    getAtencionHumanitaria().getSolicitudmaestro().setUrgencia(new Urgencia(getAtencionHumanitaria().getTipoUrgencia().getIntidurgencia()));
                    getAtencionHumanitaria().getSolicitudmaestro().setTipoevento(new Tipoevento(getSessionBeanCobra().getAtencionhumanitariaService().getTipoEventoSolcitud().getIntidtipoevento()));
                    getAtencionHumanitaria().getSolicitudmaestro().setJsfUsuarioByIntusucreacion(getSessionBeanCobra().getUsuarioObra());

                    if (getAtencionHumanitaria().getListadosolicitudDetalle().size() > 0) {
                        getAtencionHumanitaria().getSolicitudmaestro().setSolicituddetalles(new HashSet(getAtencionHumanitaria().getListadosolicitudDetalle()));
                    }

                    if (!getAtencionHumanitaria().getSolicitudmaestro().isBoolincluyeinterventoria()) {

                        getAtencionHumanitaria().getSolicitudmaestro().setFloatporcentaje(0);
                    }

                        getAtencionHumanitaria().guardarSolitudMaestro(getAtencionHumanitaria().getSolicitudmaestro());

                    getAtencionHumanitaria().setGuardadosol(true);
                    guardar = true;
                    consultarDatosAtencion();
                    //String prueba=limpiarSolicitud();


                } else {
                    getAtencionHumanitaria().setMensaje("Debe seleccionar una localidad");
                    guardar = false;
                    getAtencionHumanitaria().setGuardadosol(false);

                }
            } else {
                guardar = false;
                
                getAtencionHumanitaria().getMensaje();
            }

        } else {
            return null;
        }
//        } else if (grupousu == 11 || grupousu == 12) { //Funcionario CH
//
//
//            return null;
//
//        } else if (grupousu == 12) { //Alto funcionario Colombia Humanitaria (Director)
//            getAtencionHumanitaria().getSolicitudmaestro().setDatefechamodifica(new Date());
//            getAtencionHumanitaria().getSolicitudmaestro().setJsfUsuarioByIntusumodifica(getSessionBeanCobra().getUsuarioObra());
//            getAtencionHumanitaria().getSolicitudmaestro().setTextobservaciones(getSessionBeanCobra().getAtencionhumanitariaService().getSolicitudmaestro().getTextobservaciondirec());
//            getAtencionHumanitaria().getSolicitudmaestro().setFloatporcentaje((float) getAtencionHumanitaria().getValorPagar());
//            getAtencionHumanitaria().getSolicitudmaestro().setNumvlrpagar(getValoraPagar());
//            getAtencionHumanitaria().guardarSolitudMaestro(getAtencionHumanitaria().getSolicitudmaestro());
//            getAtencionHumanitaria().setSolicitudmaestro(new Solicitudmaestro());
//
//            return "homesolicitud";
//
//        } else if (grupousu == 13) { //Financiera
//            getAtencionHumanitaria().getSolicitudmaestro().setDatefechamodifica(new Date());
//            getAtencionHumanitaria().getSolicitudmaestro().setJsfUsuarioByIntusumodifica(getSessionBeanCobra().getUsuarioObra());
//            getAtencionHumanitaria().getSolicitudmaestro().setTextobservacionfinanciera(getSessionBeanCobra().getAtencionhumanitariaService().getSolicitudmaestro().getTextobservacionfinanciera());
//            getAtencionHumanitaria().getSolicitudmaestro().setStrurlcartaaprobacion(getAtencionHumanitaria().getSolicitudmaestro().getStrurlcartaaprobacion());
//
//            if (getSessionBeanCobra().getAtencionhumanitariaService().encontrarOrdenPagoPorId(getAtencionHumanitaria().getOrdendepago().getStrordenpago()) == null) {
//                getSessionBeanCobra().getAtencionhumanitariaService().getOrdendepago().setDatefechaordenpago(new Date());
//                getSessionBeanCobra().getAtencionhumanitariaService().getOrdendepago().setBoolestadoordenpago(true);
//                getSessionBeanCobra().getAtencionhumanitariaService().getOrdendepago().setStrdescripcionordenpago(getSessionBeanCobra().getAtencionhumanitariaService().getSolicitudmaestro().getTextobservacionfinanciera());
//                getSessionBeanCobra().getAtencionhumanitariaService().getOrdendepago().setNumvlrordenpago(getSessionBeanCobra().getAtencionhumanitariaService().getSolicitudmaestro().getNumvlraprobado());
//                getSessionBeanCobra().getAtencionhumanitariaService().getOrdendepago().setStrordenpago(getAtencionHumanitaria().getOrdendepago().getStrordenpago());
//                getSessionBeanCobra().getAtencionhumanitariaService().getOrdendepago().setJsfUsuario(new JsfUsuario(getSessionBeanCobra().getUsuarioObra().getUsuId(), "", ""));
//                getSessionBeanCobra().getAtencionhumanitariaService().getOrdendepago().setTercero(new Tercero(getSessionBeanCobra().getUsuarioObra().getTercero().getIntcodigo(), null, null, null, "", "", "", "", "", false, null, ""));
//                getSessionBeanCobra().getAtencionhumanitariaService().getOrdendepago().setSolicitudmaestro(new Solicitudmaestro(getSessionBeanCobra().getAtencionhumanitariaService().getSolicitudmaestro().getOidcodigosolicitudmaestro(), null, null, null, null, "", null, BigDecimal.ZERO, BigDecimal.ZERO, "", null, null, "", null, null, null, null));
//                getSessionBeanCobra().getAtencionhumanitariaService().getOrdendepago().setTipoordendepago(new Tipoordendepago(1));
//                getSessionBeanCobra().getAtencionhumanitariaService().guardarOrdenPago(getSessionBeanCobra().getAtencionhumanitariaService().getOrdendepago());
//            }
//            getAtencionHumanitaria().guardarSolitudMaestro(getAtencionHumanitaria().getSolicitudmaestro());
//            getAtencionHumanitaria().setSolicitudmaestro(new Solicitudmaestro());
//            return "homesolicitud";
//
//        } else if (grupousu == 14) { // Fiduprevisora
//            getAtencionHumanitaria().getSolicitudmaestro().setDatefechamodifica(new Date());
//            getAtencionHumanitaria().getSolicitudmaestro().setJsfUsuarioByIntusumodifica(getSessionBeanCobra().getUsuarioObra());
//            getAtencionHumanitaria().getSolicitudmaestro().setEncargofiduciario(new Encargofiduciario(getSessionBeanCobra().getAtencionhumanitariaService().getEncargofiduciario().getIntnumencargofiduciario(), null, null, "", null, null, BigDecimal.ZERO, BigDecimal.ZERO));
//            // if (getSessionBeanCobra().getAtencionhumanitariaService().encontrarEncargoFiduciarioPorId(getSessionBeanCobra().getAtencionhumanitariaService().getEncargofiduciario().getIntnumencargofiduciario()) == null) {
//            getSessionBeanCobra().getAtencionhumanitariaService().getEncargofiduciario().setDatefechacreacion(new Date());
//            getSessionBeanCobra().getAtencionhumanitariaService().getEncargofiduciario().setStrdescripcion(getAtencionHumanitaria().getSolicitudmaestro().getTextobservacionesfidu());
//            getSessionBeanCobra().getAtencionhumanitariaService().getEncargofiduciario().setNumsaldoactual(getSessionBeanCobra().getAtencionhumanitariaService().getSolicitudmaestro().getNumvlraprobado());
//            getSessionBeanCobra().getAtencionhumanitariaService().getEncargofiduciario().setNumsaldoinicial(getSessionBeanCobra().getAtencionhumanitariaService().getSolicitudmaestro().getNumvlraprobado());
//            getSessionBeanCobra().getAtencionhumanitariaService().getEncargofiduciario().setJsfUsuarioByIntusucreacion(new JsfUsuario(getSessionBeanCobra().getUsuarioObra().getUsuId(), "", ""));
//            getSessionBeanCobra().getAtencionhumanitariaService().getEncargofiduciario().setTercero(getSessionBeanCobra().getUsuarioObra().getTercero());
//            getSessionBeanCobra().getAtencionhumanitariaService().getEncargofiduciario().setLocalidad(new Localidad(getSessionBeanCobra().getAtencionhumanitariaService().getSolicitudmaestro().getLocalidad().getStrcodigolocalidad()));
//            getSessionBeanCobra().getAtencionhumanitariaService().guardarEncargoFiduciario(getSessionBeanCobra().getAtencionhumanitariaService().getEncargofiduciario());
//
//            // }
//            getAtencionHumanitaria().guardarSolitudMaestro(getAtencionHumanitaria().getSolicitudmaestro());
//            getAtencionHumanitaria().setSolicitudmaestro(new Solicitudmaestro());
//            return "homesolicitud";

        return null;
    }

    /**
     * Guarda la aprobacion de la solicitud, cambia el estado de la solicitud
     * @return No Retorna ningún valor
     */
    public String aprobarSolicitud() {

        getAtencionHumanitaria().getSolicitudmaestro().setDatefechamodifica(new Date());
        getAtencionHumanitaria().getSolicitudmaestro().setStrurlactajunta(getAtencionHumanitaria().getSolicitudmaestro().getStrurlactajunta());
        getAtencionHumanitaria().getSolicitudmaestro().setSubestadosolicitudch(getAtencionHumanitaria().encontrarSubestadoPorId(getAtencionHumanitaria().getSolicitudmaestro().getSubestadosolicitudch().getIntoidsubestadosolicitud()));
        getAtencionHumanitaria().getSolicitudmaestro().setJsfUsuarioByIntusumodifica(getSessionBeanCobra().getUsuarioObra());
        getAtencionHumanitaria().guardarSolitudMaestro(getAtencionHumanitaria().getSolicitudmaestro());
        getAtencionHumanitaria().setSolicitudmaestro(new Solicitudmaestro());


        return null;
    }

    /**
     * Guarda el video en una carpeta temporal, comprime el video.
     * @return No retorna ningun valor
     * @throws IOException
     * @throws InterruptedException
     */
    public String guardarVideoTemporal() throws IOException, InterruptedException {


        String realArchivoPath = "";
        String URL = "/resources/Documentos/Solicitud/Temporal/";
        ServletContext theApplicationsServletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String URLFINAL = theApplicationsServletContext.getRealPath("");

        if (subirVideoAtencion.getArchivos().size() > 0) {
            hayvideo = true;

            realArchivoPath = theApplicationsServletContext.getRealPath(URL);
            try {
                File carpeta = new File(realArchivoPath);
                if (!carpeta.exists()) {
                    carpeta.mkdirs();
                }

            } catch (Exception ex) {
                FacesUtils.addErrorMessage(bundle.getString("nosepuedesubir"));
                return null;
            }
            try {
                subirVideoAtencion.guardarArchivosTemporales(realArchivoPath, false);
            } catch (ArchivoExistenteException ex) {
                Logger.getLogger(AdminSolicitudAtencion.class.getName()).log(Level.SEVERE, null, ex);
            }
            Iterator arch = subirVideoAtencion.getArchivos().iterator();
            String nombre2 = "";

            while (arch.hasNext()) {
                Archivo elvideo = (Archivo) arch.next();
                nombre2 = "" + elvideo.getLength();
                nombreVideo = elvideo.getName();
                File f = new File(URLFINAL + URL + nombreVideo);

                String nuevonombre = "video" + nombre2;
                f.renameTo(new File(URLFINAL + URL + "video" + nombre2));

                pathVideo = URL + nuevonombre + ".flv";
                nombreVideo = URL + nuevonombre;
                String command = "ffmpeg -i " + URLFINAL + URL + nuevonombre + " -s 320x240 -ar 11025 " + URLFINAL + URL + nuevonombre + ".flv";
                try {
                    final Process process = Runtime.getRuntime().exec(command);
                    new Thread() {

                        @Override
                        public void run() {
                            try {
                                InputStream is = process.getInputStream();
                                byte[] buffer = new byte[1024];
                                for (int count = 0;
                                        (count = is.read(buffer)) >= 0;) {
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
                                for (int count = 0;
                                        (count = is.read(buffer)) >= 0;) {
                                    System.err.write(buffer, 0, count);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                    int returnCode = process.waitFor();
                    String ArchivoPath = "";
                    ServletContext contexto = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
                    if (nombreVideo.compareTo("") != 0) {
                        ArchivoPath = contexto.getRealPath(nombreVideo);
                        File archi = new File(ArchivoPath);
                        if (archi.exists()) {
                            archi.delete();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

        } else {
            if (pathVideo.compareTo("") != 0) {
                realArchivoPath = theApplicationsServletContext.getRealPath(pathVideo);
                File archi = new File(realArchivoPath);
                if (archi.exists()) {
                    archi.delete();
                }
                pathVideo = bundle.getString("noaplica");//"No Aplica";
            }

        }
        return null;
    }

    /**
     * Borra el video que han subido de la carpeta del tomcat.
     * @return
     */
    public String borrarVideoAtencion() {
        String ArchivoPath = "";

        ServletContext contexto = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        if (pathVideo.compareTo("") != 0) {
            ArchivoPath = contexto.getRealPath(pathVideo);
            File archi = new File(ArchivoPath);
            if (archi.exists()) {
                archi.delete();
            }
            pathVideo = "";
            nombreVideo = "";
        }
        subirVideoAtencion.borrarDatosSubidos();
        return null;
    }

    /**
     * Borra la imagen del tomcat
     * @return No retorna nigun valor
     */
    public String borrarImagenAtencion() {
        String ArchivoPath = "";
        ServletContext contexto = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        if (pathImagen.compareTo("") != 0) {
            ArchivoPath = contexto.getRealPath(pathImagen);
            File archi = new File(ArchivoPath);
            if (archi.exists()) {
                archi.delete();
            }
            pathImagen = "/resources/images/noimagen.png";
        }
        subirImagenAtencion.borrarDatosSubidos();
        return null;
    }

    /**
     * Borra el acta que esta subida en el tomcat
     * @return
     */
    public String borrarActaAtencion() {
        String ArchivoPath = "";
        ServletContext contexto = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        if (pathActa.compareTo("") != 0) {
            ArchivoPath = contexto.getRealPath(pathActa);
            File archi = new File(ArchivoPath);
            if (archi.exists()) {
                archi.delete();
            }
            pathActa = "/resources/images/noimagen.png";
        }
        subirActa.borrarDatosSubidos();
        return null;
    }

    /**
     * Elimina la carta de aprobacion que han subido
     * @return
     */
    public String borrarCartaAprobacion() {
        String ArchivoPath = "";
        ServletContext contexto = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        if (pathCarta.compareTo("") != 0) {
            ArchivoPath = contexto.getRealPath(pathCarta);
            File archi = new File(ArchivoPath);
            if (archi.exists()) {
                archi.delete();
            }
            pathCarta = "/resources/images/noimagen.png";
        }
        subirCartaFinanciera.borrarDatosSubidos();
        return null;
    }

    /**
     * Persisite la orden de pago de la solicitud
     * @return
     */
    public String guardarOrden() {

        getFiduFinanciera().guardarOrdenPago();
        return null;
    }

    /**
     * Metodo para filtrar las solicitudes
     * @return No Retorna Ningun Valor
     */
    public String filtrar_action() {
//        filtrarSolicitudesAh();
        return null;
    }

    /**
     * Inicializa variables, objetos, se utiliza para limpiar el formulario
     * de solicitud maestro.
     * @return
     */
    public String limpiarSolicitud() {

        subirImagenAtencion = new CargadorArchivosWeb();
        subirDocumento = new CargadorArchivosWeb();
        getAtencionHumanitaria().getSolicitudmaestro().setTipoah(new Tipoah(2, "", ""));
        getAtencionHumanitaria().setListadocumentosolicitud(new ArrayList<Documentosolicitud>());
        getAtencionHumanitaria().setListadoimagensolicitud(new ArrayList<Imagensolicitud>());
        getAtencionHumanitaria().setListadosolicitudDetalle(new ArrayList<Solicituddetalle>());
        getAtencionHumanitaria().setImagen(new Imagensolicitud());
        getAtencionHumanitaria().setSolicituddetalle(new Solicituddetalle());
        getAtencionHumanitaria().setSolicitudmaestro(new Solicitudmaestro());
        getAtencionHumanitaria().getSolicitudmaestro().setTipoah(new Tipoah(2, "", ""));
        getAtencionHumanitaria().getSolicitudmaestro().setTercero(new Tercero());
        llenarSolicitudMaestro();
        codDepartamento = "0";
        strmunicipio = "0";
        vermunicipio = false;
        // llenadodecombos();
//        if (getSessionBeanCobra().getUsuarioObra().getTercero().getCargo() != null) {
////            if (getSessionBeanCobra().getUsuarioObra().getTercero().getCargo().getIntcargo() == 1) {
////                vermunicipio = true;
////
////            }
////            if (getSessionBeanCobra().getUsuarioObra().getTercero().getCargo().getIntcargo() == 9) {
////                vermunicipio = false;
////            }
//        }

        deshcategoria = false;
        editarsolicitud = false;
        mostrar = false;
        numvalortotalitem = BigDecimal.ZERO;
        valorporcentaje = BigDecimal.ZERO;
        totalfamilias=BigDecimal.ZERO;
        getAtencionHumanitaria().getSolicituddetalle().setNumvlrunitario(getAtencionHumanitaria().getProductoNuevo().getNumvlrproductoah());
        llenarSolicitantes();
        return "homesolicitud";

    }

    /**
     * Calcular el valor que le aprobaron de la solicitud.
     * @return el valor que dio como resultado del calculo
     */
    public BigDecimal getValoraPagar() {
        BigDecimal temp = new BigDecimal(BigInteger.ZERO);
        if (getSessionBeanCobra().getAtencionhumanitariaService().getSolicitudmaestro().getNumvlraprobado() != null) {
            temp = getSessionBeanCobra().getAtencionhumanitariaService().getSolicitudmaestro().getNumvlraprobado().multiply(new BigDecimal(getSessionBeanCobra().getAtencionhumanitariaService().getValorPagar())).divide(new BigDecimal(100));
        }
        return temp;
    }

    /**
     * Paginador de la tabla del home, filtra las solicitudes por usuario que se loguio
     * y por los parametros que se hayan configurado.
     * @return No retorna ningun valor
     */
    public String primerosreales() {


        if (aplicafiltro) {
            getAtencionHumanitaria().setSolicitudMaestro(getAtencionHumanitaria().filtrarSolicitudAtencion(filtroAh, 0, 10));
            totalfilas = getAtencionHumanitaria().getNumSolicitudesAtencion(filtroAh);

        } else {
            if (versolicitudes) {
                getAtencionHumanitaria().setSolicitudMaestro(getAtencionHumanitaria().encontrarTotalSolicitudesAhXEstado(estado, 0, 10));
                totalfilas = getAtencionHumanitaria().getNumSolicitudesXEstado(estado);

            }
            if (grupousu == 10) {

                getAtencionHumanitaria().setSolicitudMaestro(getAtencionHumanitaria().SolicitudesmaestroxUsuid(getSessionBeanCobra().getUsuarioObra().getUsuId(), 0, 10));
                totalfilas = getAtencionHumanitaria().getNumSolicitudesAtencionTempxUsuid(getSessionBeanCobra().getUsuarioObra().getUsuId());
            } else if (!versolicitudes) {

                getAtencionHumanitaria().setSolicitudMaestro(getAtencionHumanitaria().encontrarTotalSolicitudesAtencionTemp(0, 10));
                totalfilas = getAtencionHumanitaria().getNumSolicitudes();
            }

        }
        pagina = 1;
        if (totalfilas <= 10) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 10;
            if (totalfilas % 10 > 0) {
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
     * Paginador de la tabla del home, filtra las solicitudes por usuario que se loguio
     * y por los parametros que se hayan configurado.
     * @return No retorna ningun valor
     */
    public String siguientesReales() {

        int num = (pagina) * 10;
        if (aplicafiltro) {
            getAtencionHumanitaria().setSolicitudMaestro(getAtencionHumanitaria().filtrarSolicitudAtencion(filtroAh, num, 10));
            totalfilas = getAtencionHumanitaria().getNumSolicitudesAtencion(filtroAh);

        } else {
            if (grupousu == 10) {

                getAtencionHumanitaria().setSolicitudMaestro(getAtencionHumanitaria().SolicitudesmaestroxUsuid(getSessionBeanCobra().getUsuarioObra().getUsuId(), num, 10));
                totalfilas = getAtencionHumanitaria().getNumSolicitudesAtencionTempxUsuid(getSessionBeanCobra().getUsuarioObra().getUsuId());
            } else {
                getAtencionHumanitaria().setSolicitudMaestro(getAtencionHumanitaria().encontrarTotalSolicitudesAtencionTemp(num, 10));
                totalfilas = getAtencionHumanitaria().getNumSolicitudes();
            }

//                getAtencionHumanitaria().setSolicitudMaestro(getAtencionHumanitaria().encontrarTotalSolicitudesAhXEstado(estado, num, 10));
//                totalfilas = getAtencionHumanitaria().getNumSolicitudesXEstado(estado);

        }
        if (totalfilas <= 10) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 10;
            if (totalfilas % 10 > 0) {
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
     * Paginador de la tabla del home, filtra las solicitudes por usuario que se loguio
     * y por los parametros que se hayan configurado.
     * @return No retorna ningun valor
     */
    public String anterioresReales() {
        pagina = pagina - 1;
        int num = (pagina - 1) * 10;
        if (aplicafiltro) {
            getAtencionHumanitaria().setSolicitudMaestro(getSessionBeanCobra().getAtencionhumanitariaService().filtrarSolicitudAtencion(filtroAh, num, 10));
            totalfilas = getAtencionHumanitaria().getNumSolicitudesAtencion(filtroAh);
        } else {
            if (getSessionBeanCobra().getSolicitudService().getJsfusuariogrupo().getGrupo().getGruGid() == 10) {
                getAtencionHumanitaria().setSolicitudMaestro(getAtencionHumanitaria().SolicitudesmaestroxUsuid(getSessionBeanCobra().getUsuarioObra().getUsuId(), num, 10));
                totalfilas = getAtencionHumanitaria().getNumSolicitudesAtencionTempxUsuid(getSessionBeanCobra().getUsuarioObra().getUsuId());
            } else {
                getAtencionHumanitaria().setSolicitudMaestro(getAtencionHumanitaria().encontrarTotalSolicitudesAtencionTemp(num, 10));
                totalfilas = getAtencionHumanitaria().getNumSolicitudes();
            }

//                getAtencionHumanitaria().setSolicitudMaestro(getAtencionHumanitaria().encontrarTotalSolicitudesAhXEstado(estado, num, 10));
//                totalfilas = getAtencionHumanitaria().getNumSolicitudesXEstado(estado);

        }

        if (totalfilas <= 10) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 10;
            if (totalfilas % 10 > 0) {
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
     * Paginador de la tabla del home, filtra las solicitudes por usuario que se loguio
     * y por los parametros que se hayan configurado.
     * @return No retorna ningun valor
     */
    public String ultimoReales() {
        //totalfilas = getAtencionHumanitaria().getNumSolicitudes();
        int num = totalfilas % 10;


        if (aplicafiltro) {
            totalfilas = getAtencionHumanitaria().getNumSolicitudesAtencion(filtroAh);
            getAtencionHumanitaria().setSolicitudMaestro(getAtencionHumanitaria().filtrarSolicitudAtencion(filtroAh, totalfilas - num, totalfilas));
        } else {
            if (getSessionBeanCobra().getSolicitudService().getJsfusuariogrupo().getGrupo().getGruGid() == 10) {
                totalfilas = getAtencionHumanitaria().getNumSolicitudesAtencionTempxUsuid(getSessionBeanCobra().getUsuarioObra().getUsuId());
                getAtencionHumanitaria().setSolicitudMaestro(getAtencionHumanitaria().SolicitudesmaestroxUsuid(getSessionBeanCobra().getUsuarioObra().getUsuId(), totalfilas - num, totalfilas));
            } else {
                totalfilas = getAtencionHumanitaria().getNumSolicitudes();
                getAtencionHumanitaria().setSolicitudMaestro(getAtencionHumanitaria().encontrarTotalSolicitudesAtencionTemp(totalfilas - num, totalfilas));
            }

//                getAtencionHumanitaria().setSolicitudMaestro(getAtencionHumanitaria().encontrarTotalSolicitudesAhXEstado(estado, totalfilas - num, totalfilas));
//                totalfilas = getAtencionHumanitaria().getNumSolicitudesXEstado(estado);

        }

        if (totalfilas <= 10) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 10;
            if (totalfilas % 10 > 0) {
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
     * Asigna el valor a el objeto de Solicitud que han seleccionado en el home
     * para cosnultar
     * @param filaSeleccionada Corresponde a la fila de la que proviene la
     * acción en la tabla
     * @return la regla de navegacion que direcciona a la pagina de consultar atencion
     */
    public String consultarSolicitud() {
        deshformulario = true;
        mostrar = true;
        //SessionBeanCobra sessionBeanCobra = (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
        Solicitudmaestro solicitud = (Solicitudmaestro) tablaSolicitudMaestro.getRowData();

        getAtencionHumanitaria().setSolicitudmaestro(solicitud);

        if (solicitud.getSubestadosolicitudch().getEstadosolicitudch().getOidestadosolicitud() == 1) {
            mostrarmov = true;
        }

        getAtencionHumanitaria().setListadosolicitudDetalle(getSessionBeanCobra().getAtencionhumanitariaService().encontrarSolicitudDetalle(solicitud));
        getAtencionHumanitaria().setCodigosol(true);
        getAtencionHumanitaria().setLocalidadSolicitud(solicitud.getLocalidad());
        if (solicitud.getLocalidad().getStrcodigolocalidad().length() == 5) {
            vermunicipio = false;
        }
        if (solicitud.getLocalidad().getStrcodigolocalidad().length() > 5) {
            vermunicipio = true;

        }
        getAtencionHumanitaria().setListadosolicitudDetalle(getAtencionHumanitaria().encontrarSolicitudDetalle(solicitud));
        getSessionBeanCobra().getAtencionhumanitariaService().setTipoEventoSolcitud(solicitud.getTipoevento());
        getAtencionHumanitaria().getEstadoSolicitudch().setOidestadosolicitud(solicitud.getSubestadosolicitudch().getEstadosolicitudch().getOidestadosolicitud());
        getAtencionHumanitaria().getSubestadoSolicitudch().setIntoidsubestadosolicitud(solicitud.getSubestadosolicitudch().getIntoidsubestadosolicitud());
        if (solicitud.isBoolestadodocumentacion() == false) {
            estadodoc = 2;
        } else {
            estadodoc = 1;
        }
        calcularValorSolicitudDetalle();
        return "consultar_atencion";
    }

    /**
     * Desactiva el formulario de la solicitud
     * @return la regla de navegacion que lleva al formulario
     */
    public String ConsultarDatosBasicos() {
        deshformulario = true;

        return "consultar_atencion";
    }

    /**
     * Carga las imagenes que tiene la solicitud
     * @return regla de navegacion que lleva a la pagina de imagenes de atencion
     */
    public String ConsultarImagenes() {

        getAtencionHumanitaria().setListadoimagensolicitud(getAtencionHumanitaria().encontrarImagenSolicitud(getAtencionHumanitaria().getSolicitudmaestro().getOidcodigosolicitudmaestro()));
        return "img_atencion";
    }

    /**
     * Llena la lista de documentos de la solicitud
     * @return la regla de navegacion que lleva a la pagina de documentos
     */
    public String ConsultarDocumentos() {

        getAtencionHumanitaria().setListadocumentosolicitud(getAtencionHumanitaria().encontrarDocumentosSolicitud(getAtencionHumanitaria().getSolicitudmaestro().getOidcodigosolicitudmaestro()));
        return "doc_atencion";
    }

    /**
     * Configura el filtro e invoca al paginador
     * @return No retorna valor
     */
    public String buscar() {
        aplicafiltro = true;
        filtroAh.setInttipoayuda(0);
        primerosreales();
        return null;
    }

    /**
     * Configura el filtro e invoca el paginador
     * @return No retorna ningun valor
     */
    public String buscarFiltro() {
        aplicafiltro = true;
        primerosreales();
        return null;
    }

    /**
     * Llena el listado de los movimientos de la soliciutd.
     * @param codsolicitud: El codigo de la solicitud de la cual se necesitan los movimientos
     */
    public void movimientosSolicitud(long codsolicitud) {

        getAtencionHumanitaria().setListadoMovimientos(getAtencionHumanitaria().encontrarmovimientossolicitud(codsolicitud));

    }

    /**
     * Se le envian los varoles a un nuevo proyecto que sera creado apartir de una solicitud
     * @return Regla de navegacion que lleva a el formulario de nuevo proyecto
     * @throws Exception
     */
    public String crearProyecto() throws Exception {

        getIngresarNuevaObra().limpiarobra();

        if (getSolitemp().getSubestadosolicitudch().getEstadosolicitudch().getOidestadosolicitud() == 1) {
            getIngresarNuevaObra().getObranueva().setStrnombreobra(getSolitemp().getStrnombresolicitud());
            getIngresarNuevaObra().getObranueva().setStrobjetoobra(getSolitemp().getStrdescripcion());
            getIngresarNuevaObra().getObranueva().setFloatlatitud(getSolitemp().getLocalidad().getFloatlatitud());
            getIngresarNuevaObra().getObranueva().setFloatlongitud(getSolitemp().getLocalidad().getFloatlongitud());
            getIngresarNuevaObra().getObranueva().setNumvaltotobra(getSolitemp().getNumvlraprobado());
            getIngresarNuevaObra().getObranueva().setSolicitudmaestro(getSolitemp());

            getIngresarNuevaObra().setTiahselect(1);
            getIngresarNuevaObra().setTiproyectoselec(2);
            getIngresarNuevaObra().setSubtiposelec(18);

            if (getSolitemp().getTipoah().getOidcodigotipoah() == 1) {
                getIngresarNuevaObra().getObranueva().setClaseobra(new Claseobra(4, null, "", true));
                getIngresarNuevaObra().getObranueva().setTipoobra(new Tipoobra(17, null, ""));

            }
            if (getSolitemp().getTipoah().getOidcodigotipoah() == 2) {
                getIngresarNuevaObra().getObranueva().setClaseobra(new Claseobra(5, null, "", true));
                getIngresarNuevaObra().getObranueva().setTipoobra(new Tipoobra(18, null, ""));
            }

            getIngresarNuevaObra().getObranueva().getTipoobra().setTipoproyecto(new Tipoproyecto(2, ""));
            getIngresarNuevaObra().getObranueva().getClaseobra().setFase(new Fase(1, ""));
            getIngresarNuevaObra().getObranueva().setTercero(getSessionBeanCobra().getUsuarioObra().getTercero());
            getIngresarNuevaObra().getObranueva().setTipoorigen(new Tipoorigen(getSessionBeanCobra().getUsuarioObra().getTercero().getTipoOrigen().getIntidtipoorigen(), ""));
            getIngresarNuevaObra().getObranueva().setPeriodomedida(new Periodomedida());
            getIngresarNuevaObra().getObranueva().setPeriodoevento(getSolitemp().getPeriodoevento());
            getSessionBeanCobra().getCobraService().setSolibol(true);

            return "asociarproyectoaatenci";

        } else {
            FacesUtils.addErrorMessage("No puede crear un proyecto a la solicitud ya que no ha sido aprobada ");
        }

        return null;
    }

    /**
     * Llena la lista de proyectos que tiene una solicitud
     *
     * @param filaSeleccionada Corresponde a la fila de la que proviene la
     * acción en la tabla
     * @return No retorna valor alguno
     */
    public String llenarProyectosxSolicitud() {

        //SessionBeanCobra sessionBeanCobra = (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
        Solicitudmaestro solicitud_mestro = (Solicitudmaestro) tablaSolicitudMaestro.getRowData();

        getSessionBeanCobra().getSolicitudService().setListaproyectoasoci(getSessionBeanCobra().getCobraService().encontrarSoliProyecto(solicitud_mestro.getOidcodigosolicitudmaestro()));
        if (getSessionBeanCobra().getSolicitudService().getListaproyectoasoci().size() > 0) {

            getSessionBeanCobra().getCobraService().setVerproy(true);
        } else {
            getSessionBeanCobra().getCobraService().setVerproy(false);

        }
        setSolitemp(solicitud_mestro);
        return null;
    }



     public String verProyecto() {

        Obra obra = (Obra) tablaproyectosah.getRowData();
        //getIngresarNuevaObra().setObranueva(obra);   
        if (obra.getObra().getTipoestadobra().getIntestadoobra() == 1) {
            getAdministrarObraNew().setObra(obra);

         return "verproyecto";
        }
        if (obra.getObra().getTipoestadobra().getIntestadoobra() == 0) {
            getSessionBeanCobra().getCobraService().setSolibol(true);
            getIngresarNuevaObra().setObjeto(true);           
            return "asociarproyectoaatenci";
        }
        return null;
    }

    /**
     * Descargar documentos de la solicitud.
     * @return
     */
    public String bt_download_documento_solicitud() {
        // TODO: Process the action. Return value is a navigation
        // case name where null will return to the same page.
        getAtencionHumanitaria().setDocumento((Documentosolicitud) tablaDocumentos.getRowData());
        getSessionBeanCobra().setUrlAbri(getAtencionHumanitaria().getDocumento().getStrurldocumento());
        //this.getSessionBeanCobra().setUrlAbri(doc.getStrubicacion());
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/"+getSessionBeanCobra().getBundle().getString("versioncobra") +"/" + getAtencionHumanitaria().getDocumento().getStrurldocumento());
        } catch (IOException ex) {
            Logger.getLogger(AdminSolicitudAtencion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    /**
     * Variable utilizada para saber si se muestra el solicitante
     */
    private boolean mostrar = false;

    public boolean isMostrar() {
        return mostrar;
    }

    public void setMostrar(boolean mostrar) {
        this.mostrar = mostrar;
    }

    /**
     * Llena el combo con los solicitantes del departamento o del municipio
     * @return No retorna ningun valor
     */
    public String llenarSolicitantes() {
        mostrar = true;
        listaSolicitante = new ArrayList<Tercero>();

        if (strmunicipio.compareTo("0") == 0) {

            listaSolicitante = getSessionBeanCobra().getAtencionhumanitariaService().enconcontrarTerceroSolicitante(codDepartamento, 9);
            
        } else if (strmunicipio.compareTo("0") != 0) {

            listaSolicitante = getSessionBeanCobra().getAtencionhumanitariaService().enconcontrarTerceroSolicitante(strmunicipio, 1);
        }
        
        
        solicitante = new SelectItem[listaSolicitante.size()];
        int i = 0;
        for (Tercero tersolicitante : listaSolicitante) {
            SelectItem itemsolicitante = new SelectItem(tersolicitante.getIntcodigo(), tersolicitante.getStrnombrecompleto());
            if (i == 0) {
                getAtencionHumanitaria().getSolicitudmaestro().getTercero().setIntcodigo(tersolicitante.getIntcodigo());
            }
            solicitante[i++] = itemsolicitante;
        }
        return null;
    }

    /**
     * Carga el tercero solicitante
     * @return
     */
    public String cargarSolicitante() {
        getAtencionHumanitaria().getSolicitudmaestro().setTercero(getAtencionHumanitaria().enconcontrarTerceroPorId(getAtencionHumanitaria().getSolicitudmaestro().getTercero().getIntcodigo()));
        return null;
    }
    /**
     * Suministrarle al combo las zonas específicas
     */
    public void llenarZonaEspecifica() {
        getSessionBeanCobra().getCobraService().setListazonaespecificas(getSessionBeanCobra().getCobraService().encontrarZonaespecifica());
        zonaespe = new SelectItem[getSessionBeanCobra().getCobraService().getListazonaespecificas().size()];
        int i = 0;
        for (Zonaespecifica zona : getSessionBeanCobra().getCobraService().getListazonaespecificas()) {
            SelectItem zon = new SelectItem(zona.getIntidzonaespecifica(), zona.getStrdescripcionzona().toUpperCase());
            zonaespe[i++] = zon;
        }
    }

}
