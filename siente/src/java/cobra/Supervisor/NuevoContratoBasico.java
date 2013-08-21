/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.Supervisor;

import co.com.interkont.cobra.planoperativo.client.dto.ContratoDTO;
import co.com.interkont.cobra.planoperativo.exceptions.ConvenioException;
import co.com.interkont.cobra.planoperativo.exceptions.ValidacionesConvenio;
import co.com.interkont.cobra.to.Actividadobra;
import co.com.interkont.cobra.to.AreaContratista;
import co.com.interkont.cobra.to.Aseguradora;
import co.com.interkont.cobra.to.Cargo;
import co.com.interkont.cobra.to.Contratista;
import co.com.interkont.cobra.to.Contrato;
import co.com.interkont.cobra.to.Documentoobra;
import co.com.interkont.cobra.to.Encargofiduciario;
import co.com.interkont.cobra.to.EstadoCivil;
import co.com.interkont.cobra.to.Estadoconvenio;
import co.com.interkont.cobra.to.Evento;
import co.com.interkont.cobra.to.Fase;
import co.com.interkont.cobra.to.Formapago;
import co.com.interkont.cobra.to.Fuenterecursosconvenio;
import co.com.interkont.cobra.to.Genero;
import co.com.interkont.cobra.to.Localidad;
import co.com.interkont.cobra.to.Modalidadcontratista;
import co.com.interkont.cobra.to.Modificacioncontrato;
import co.com.interkont.cobra.to.Movimientocontrato;
import co.com.interkont.cobra.to.Obra;
import co.com.interkont.cobra.to.Ordendepago;
import co.com.interkont.cobra.to.Periodoevento;
import co.com.interkont.cobra.to.Planificacionpago;
import co.com.interkont.cobra.to.Polizacontrato;
import co.com.interkont.cobra.to.Tercero;
import co.com.interkont.cobra.to.Tipocontrato;
import co.com.interkont.cobra.to.Tipocontratoconsultoria;
import co.com.interkont.cobra.to.Tipodocumento;
import co.com.interkont.cobra.to.Tipoestadobra;
import co.com.interkont.cobra.to.Tipoidentificacion;
import co.com.interkont.cobra.to.Tipomovimiento;
import co.com.interkont.cobra.to.Tipoobra;
import co.com.interkont.cobra.to.Tipopoliza;
import co.com.interkont.cobra.to.Tipoproyecto;
import co.com.interkont.cobra.to.Tipoorigen;
import co.com.interkont.cobra.to.Tipotercero;
import co.com.interkont.cobra.to.utilidades.Propiedad;
import cobra.FiltroAvanzadoContrato;
import cobra.SessionBeanCobra;
import cobra.CargadorArchivosWeb;
import cobra.PlanOperativo.FlujoCaja;
import cobra.PlanOperativo.RecursosConvenio;
import cobra.util.ArchivoWebUtil;
import cobra.util.CasteoGWT;
import cobra.util.RutasWebArchivos;
import com.interkont.cobra.exception.ArchivoExistenteException;
import coral.dao.DataAccessLayerException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.richfaces.component.UIDataTable;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;

/**
 * <p>Page bean that corresponds to a similarly named JSP page. This class
 * contains component definitions (and initialization code) for all components
 * that you have defined on this page, as well as lifecycle methods and event
 * handlers where you may add behavior to respond to incoming events.</p>
 * Encargada de toda la administración de Contrato y convenio
 * persistencia,consulta
 *
 * @version AdministrarObraNew.java
 * @version Created on 28-oct-2010, 1:04:30
 * @author carlosalbertoloaizaguerrero
 * @author Leonardo Montes
 */
public class NuevoContratoBasico implements ILifeCycleAware, Serializable {
    // <editor-fold defaultstate="collapsed" desc="Managed Component Definition">

    /**
     * Objeto para acceder a los atributos de contrato
     */
    private Contrato contrato = new Contrato();
    /**
     * Objeto para acceder a los atributos de Actividad obra
     */
    private Actividadobra actividadobra = new Actividadobra();
    /**
     * Lista para llenar todas las pólizas de modificar contrato
     */
    private List<Polizacontrato> listaPolizacontratos = new ArrayList<Polizacontrato>();
    /**
     * Variable para mostrar un selectitem de pólizas
     */
    private SelectItem[] Poliza;
    private boolean polizacontratobo = false;
    /**
     * Variable para mostrar las entidades (Tercero)
     */
    private SelectItem[] TerceroOption;
    /**
     * Variable para sumar los valores registrados en datos basicos plan o
     */
    BigDecimal totalfuenteconvenio;
    /**
     * *
     * variable para mostrar el faltante para convenio
     */
    BigDecimal faltafuenteconvenio;
    /**
     * Variable para mostrar las formas de pago de un contrato
     */
    private SelectItem[] formaPago;
    //private Formapago formapago;
    /**
     * Objeto para mostrar los mensaje de la aplicación
     */
    private ResourceBundle bundle = getSessionBeanCobra().getBundle();
    /**
     * Variable para mostrar el tipo de contrato
     */
    private SelectItem[] TipoContrato;
    /**
     * Variable Utilizada para determinar si es contrato o convenio
     */
    private boolean booltipocontratoconvenio = false;
    /**
     * Variable para mostrar Tipo de consultoria de un contrato
     */
    private SelectItem[] tipocontratoconsultoria;
    private SelectItem[] tipocontrato;
    /**
     * Mostrar pólizas cuando ingrese al método {@link agregarPolizasContra()}
     */
    private int mostrarAgregarPol = 0;
    /**
     * Lista para llenar todas las pólizas de contrato
     */
    private List<Polizacontrato> listapolizas = new ArrayList<Polizacontrato>();
    private int verEliminar = 0;
    /**
     * Objeto para acceder a los atributos de poliza
     */
    private Polizacontrato polizacontrato = new Polizacontrato();
    /**
     * Variable para mostrar Tipo de pólizas de un contrato
     */
    private SelectItem[] TipoPolizas;
    /**
     * Variable para mostrar las entidades aseguradoras
     */
    private SelectItem[] Companias;
    private String valorFiltroContratos = "";
    /**
     * Lista que se llena con los contratos o convenios padres
     */
    private List<Contrato> listaContratosPadre = new ArrayList<Contrato>();
    /**
     * Binding creado para acceder a los datos de las filas de la tabla creada
     */
    private UIDataTable tablacontratos = new UIDataTable();
    /*
     * Variable para seleccionar el tipo de mensaje
     */
    private int tipomensajeerror = 0;
    /**
     * Utilizada para llenar con el nombre Contrato o convenio dependiendo de
     * donde este
     */
    private String tipoContCon;
    /**
     * Variable para mostrar el o los eventos de Ch
     */
    private SelectItem[] eventoItem;
    /**
     * Variable para almacenar el Id del evento y enviarselo
     * {@link encontrarPeriodoEventoPorEvento(idevento)}
     */
    private int idevento = 1;
    /**
     * Variable para mostrar periodo de un evento
     */
    private SelectItem[] periodoEventoItem;
    /**
     * Binding creado para acceder a los datos de las filas de la tabla creada
     */
    private UIDataTable tablacontrapadrebindin = new UIDataTable();
    /**
     * Binding asociado a la lista de convenios por contratista en la modal de
     * convenios de nuevo contrato
     */
    private UIDataTable tablacontrapadrebindinContratista = new UIDataTable();
    /**
     * Objeto para acceder a los atributos de contrato
     */
    Contrato contrpadre = new Contrato();
    /**
     * Lista para llenar los contratos padres
     */
    private List<Contrato> listaContraPadreSele = new ArrayList<Contrato>();
    /**
     * Variable para activar el {@link contratoPadreSelec()}
     */
    private int varmostrarcontrpa = 0;
    /**
     * Objeto para acceder a los atributos de planificación pago
     */
    private Planificacionpago planificacionpago = new Planificacionpago();
    /**
     * Cantidad de actas utilizadas dependiendo del valor eegido en la forma de
     * pago
     */
    private int numdeactasparciales = 0;
    /**
     * Lista para llenar la planificación de actas
     */
    private List<Planificacionpago> lisplanifiactapar = new ArrayList<Planificacionpago>();
    /**
     * Variable para almacenar el pago del anticipo en la forma de pago
     */
    private BigDecimal valorpagoanticipo = new BigDecimal(0);
    /**
     * Fecha del pago del anticipo
     */
    private Date fechapagoanticipo;
    /**
     * Binding creado para acceder a los datos de las filas de la tabla creada
     */
    private UIDataTable tablabindinanticiyactasparci = new UIDataTable();
    /**
     * Lista para llenar los contratos encontrados en la búsqueda
     */
    private List<Contrato> listacontratos = new ArrayList<Contrato>();
    /**
     * Lista para llenar los contratos encontrados en la búsqueda según el
     * contratista
     */
    private List<Contrato> listacontratoscontratista = new ArrayList<Contrato>();
    /**
     * Se activa cuando se ingresa al método {@link validacionFormadePago()}
     */
    private int seleccioncmbformadepago = 0;
    /**
     * Objeto para acceder a los atributos de documento
     */
    Documentoobra documentoobra = new Documentoobra();
    /**
     * Lista para llenar los docuemntos que se desean guardar
     */
    private List<Documentoobra> listadocumentos = new ArrayList<Documentoobra>();
    /**
     * Lista para llenar los tipos de docuemntos que se desean guardar
     */
    private List<Tipodocumento> listatipodocumentos = new ArrayList<Tipodocumento>();
    /**
     * Binding creado para acceder a los datos de las filas de la tabla creada
     */
    private UIDataTable tablaDocumentosContrato = new UIDataTable();
    /**
     * Variable para mostrar periodo de un evento
     */
    private SelectItem[] tipodocumento;
    /**
     * Variable para almacenar el Id del evento y enviarselo
     * {@link encontrarTipoDocPorId(tipodoc)}
     */
    private int tipodoc;
    /**
     * Subir archivos a la carpeta donde se almacenan
     */
    private CargadorArchivosWeb subirDocPol = new CargadorArchivosWeb();
    /**
     * ruta donde se almacenarán los documentos
     */
    private String pathActa = "";
    /**
     * Lista para llenar los Encargos fiduciarios asociados a los contratos
     */
    private List<Encargofiduciario> listaEncargofiduciario = new ArrayList<Encargofiduciario>();
    /**
     * Binding creado para acceder a los datos de las filas de la tabla creada
     */
    private UIDataTable tablaEncargofiduciario = new UIDataTable();
    private String valorFiltroEncargo = "";
    /**
     * Variable para almacenar la búsqueda en el filtro
     */
    private String palabraclave = "";
    /**
     * Objeto para acceder a los atributos del encargo fiduciario
     */
    Encargofiduciario encargofiduciario = new Encargofiduciario();
    private String realArchivoPath;
    /**
     * ruta de guardad de los documentos
     */
    private static final String URL = "/resources/Documentos/Contrato/";
    /**
     * Binding creado para acceder a los datos de las filas de la tabla creada
     */
    private UIDataTable tablacontratoconvenio = new UIDataTable();
    /**
     * Fecha del fin del contrato para mostrar el tiempo de terminación del
     * contrato
     */
    private String finentrega = "";
    /**
     * Lista para llenar los documentos del contrato
     */
    private List<Documentoobra> listadocuContrato = new ArrayList<Documentoobra>();
    /**
     * Binding creado para acceder a los datos de las filas de la tabla creada
     */
    private UIDataTable tabladocuContrato = new UIDataTable();
    /**
     * Variable para almacenar el Id del Tipopoliza y enviarselo
     * {@link encontrarTipoPolizaPorId(tipointpoli)}
     */
    private int tipointpoli;
    /**
     * Varible que determina si el criterio de búsqueda viene con algún registro
     * o no
     */
    private boolean aplicafiltro = false;
    /**
     * Variable para habilitar y ver los reportes de plan operativo
     */
    private boolean boolreporte = false;
    /**
     * Lista para llenar los Encargos fiduciarios asociados a los contratos
     */
    private List<Contratista> listaContratista = new ArrayList<Contratista>();
    /**
     * Variable para almacenar la búsqueda del contratista por nombre
     */
    private String nombre = "";
    /**
     * Variable para almacenar la búsqueda del contratista por apellido
     */
    private String apellido;
    /**
     * Cantidad de filas del paginador
     */
    private int totalfilas = 0;
    /**
     * Cantidad de filas del paginador de la tabla de convenios según el
     * contratista
     */
    private int totalfilasContratista = 0;
    /**
     * Cantidad de paginas del paginador
     */
    private int pagina = 0;
    /**
     * Cantidad de páginas del paginador de la tabla de convenios según el
     * contratista
     */
    private int paginaContratista = 0;
    /**
     * total de paginas del paginador
     */
    private int totalpaginas = 0;
    /**
     * total de paginas del paginador de la tabla de convenios según el
     * contratista
     */
    private int totalpaginasContratista = 0;
    /**
     * Volver a la pagina anterior del paginador
     */
    private boolean veranteriorreales;
    /**
     * Ir a la última pagina anterior del paginador
     */
    private boolean verultimosreales;
    /**
     * Binding creado para acceder a los datos de las filas de la tabla creada
     */
    private UIDataTable tablacontratistas = new UIDataTable();
    /**
     * Objeto para acceder a los atributos del contratista
     */
    private Contratista contratista = new Contratista();
    /**
     * Variable para mostrar las entidades del tercero
     */
    private SelectItem[] TipoTerceroOPtion;
    /**
     * Variable para mostrar el tipo de area del contratista
     */
    private SelectItem[] AreaContratistaOption;
    /**
     * Variable para almacenar el codigo del departamento del contratista
     */
    private String codDepartamentoContratista;
    /**
     * Listado de departamentos
     */
    private List<Localidad> listaDeptos;
    /**
     * Variable para mostrar los departamentos
     */
    private SelectItem[] DepartamentoContratista;
    /**
     * Variable para mostrar los municipios
     */
    private SelectItem[] MunicipioContratista;
    /**
     * Variable para mostrar los tipo de identificación
     */
    private SelectItem[] TipoIdentificacionOption;
    /**
     * Variable para almacenar los tipos de cargo
     */
    private SelectItem[] CargoOption;
    /**
     * Variable para almacenar los tipo de genero
     */
    private SelectItem[] GeneroOption;
    /**
     * Variable para almacenar los diferentes tipos de estado civil
     */
    private SelectItem[] EstadoCivilOption;
    /**
     * Variable para saber si el tercero es persona natural o juridica
     */
    private boolean habidescontratista = true;
    /**
     * Variable para almacenar el nombre del contrato
     */
    private String nomcontrato;
    /**
     * Variable para habilitar boton (anterior) del paginador
     */
    private boolean veranteriorcontrato;
    /**
     * Variable para habilitar boton (anterior) del paginador de la tabla de
     * convenios según el contratista
     */
    private boolean veranteriorcontratoContratista;
    /**
     * Variable para habilitar botón (ultimos) del paginador
     */
    private boolean verultimoscontrato;
    /**
     * Variable para habilitar el botón (últimos) del paginador de la tabla
     * según el contratista
     */
    private boolean verultimoscontratoContratista;
    /**
     * Variable para definir si se aplica el filtro
     */
    private boolean aplicafiltrocontrato = false;
    /**
     * Lista de las obras que tiene asociado el contrato
     */
    private List<Obra> listaObraContrato = new ArrayList<Obra>();
    /**
     * Variable para saber si el proyecto esta asociado
     */
    private boolean proyectoasociado;
    /**
     * Variable para mostrar las diferentes fases
     */
    private SelectItem[] fasecombo;
    /**
     * Variable para almacenar el costo inicial del contrato
     */
    private int costoIni = 0;
    /**
     * Variable para almacenar el costo final del contrato
     */
    private int costoFin = 0;
    /**
     * Instancia de el objeto de Fase
     */
    private Fase fase = new Fase();
    /**
     * Variable para almacenar el valor del porcentaje
     */
    private double vlpor = 1;
    //private FiltroAvanzadoObra filtro = new FiltroAvanzadoObra();
    /**
     * Instancia del objeto del filtro avanzado de contrato
     */
    private FiltroAvanzadoContrato filtrocontrato = new FiltroAvanzadoContrato();
    /**
     * Variable para mostrar los tipos de proyecto
     */
    private SelectItem[] tipoproyecto = new SelectItem[0];
    /**
     * Variable para mostrar los subtipos de proyecto
     */
    private SelectItem[] subTiposProyecto = new SelectItem[0];
    /**
     * Listado de las entidades contratantes
     */
    private List<Tercero> listaContrEnti;
    /**
     * Lista que almacena los contratos encontrados en el filtro avanzado
     */
    private List<Contrato> listaavanzada;
    /**
     * Instancia del objeto Localidad, para mostrar el departamento
     */
    private Localidad depto = new Localidad();
    /**
     * Instancia del objeto Localidad, para mostrar el municipio
     */
    private Localidad municipio = new Localidad();
    /**
     * Instancia del objeto Tipo origen
     */
    private Tipoorigen tipoOrigenUsuario = new Tipoorigen();
    /**
     * Variable para mostrar los departamentos en un combo
     */
    private SelectItem[] departamentos = new SelectItem[0];
    /**
     * Variable para mostrar los municipios en un combo
     */
    private SelectItem[] municipios = new SelectItem[0];
    /**
     * Listado de contratos o convenios hijos
     */
    private List<Contrato> listaContrConvHijo = new ArrayList<Contrato>();
    /**
     * Variable para saber si es contrato, convenio, contrato interventoria
     */
    private int preguntacontrato = 0;
    /**
     * Instancia de la clase Contrato, utilizada para manejar los contratos
     * hijos
     */
    Contrato contrhijo = new Contrato();
    /**
     * Variable para habilitar botón anterior del paginador
     */
    private boolean veranteriorencargo;
    /**
     * Variable para saber si se aplica filtro avanzado en contrato
     */
    private boolean aplicafiltroencargo;
    /**
     * Variable para habilitar botón ultimo del paginador
     */
    private boolean verultimosencargo;
    /**
     * Variable para almacenar el codigo del encargo fiduciario
     */
    private int codigoencargo = 0;
    /**
     * Instancia de la clase Planificacionpago
     */
    private Planificacionpago plapagorow = new Planificacionpago();
    /**
     * Variable para almacenar el porcentaje de anticipa
     */
    private BigDecimal porcentapagoanticipo = new BigDecimal(0);
    /**
     * Binding creado para acceder a los datos de las filas de la tabla creada
     */
    private UIDataTable tablacontconvproyecto = new UIDataTable();
    /**
     * Variable para saber si se esta aplicando el filtro de obra
     */
    private boolean aplicafiltroobra;
    /**
     * Variable utilizada para habilitar el botón anterior del paginador
     */
    private boolean veranteriorobra;
    /**
     * Variable utilizada para habilitar el botón ultimo del paginador
     */
    private boolean verultimoobra;
    /**
     * Variable para almacenar el parametro de busqueda de un proyecto
     */
    private String buscarproyecto = "";
    /**
     * Listado de municipios
     */
    private List<Localidad> listaMunicipios;
    /**
     * Variable para almacenar el codigo de departamento de expedicion del
     * documento
     */
    private String codDeptoExpdocumento;
    /**
     * Variable para mostrar los departamentos de Expedicion del documento
     */
    private SelectItem[] DeptoExpdocumento;
    /**
     * Variable para mostrar los municipios de expedicion de documento
     */
    private SelectItem[] MpioExpdocumento;
    /**
     * Variable para almacenar el codigo del departamento
     */
    private String codDeptoNacimiento;
    /**
     * Variable para mostrar los departamentos de nacimiento
     */
    private SelectItem[] DeptoNacimiento;
    /**
     * Variable para mostrar los codigos de municipios de nacimiento
     */
    private SelectItem[] MpioNacimiento;
    /**
     * Binding para acceder a los datos de la tabla de convenios o contratos
     * hijos
     */
    private UIDataTable tablacontconvhijo = new UIDataTable();
    /**
     * Variable para saber si los contratos son de consultoria
     */
    private boolean boolcontrconsultoria = false;
    /**
     * Variable utilizada para obtener la posicion de la poliza a modificar
     */
    private int indicepolizamodificar = 0;
    /**
     * Variable para almacenar el porcentaje de anticipo
     */
    private BigDecimal porcenanticipo = BigDecimal.ZERO;
    /**
     * Variable para almacenar el valor del anticipo
     */
    private BigDecimal vloranticipo = BigDecimal.ZERO;
    /**
     * Variable para almacenar la fecha de pago
     */
    private Date fechapago = new Date();
    /**
     * Variable para almacenar la forma de anticipo
     */
    private int anticiforma = 0;
    /**
     * Variable para saber si se estar creando o consultando un contratista
     */
    private boolean boolcrearcontratista = false;
    /**
     * Variable para saber si se estar creando o consultando una poliza
     */
    private boolean boolcrearpoliza = false;
    /**
     * Instancia de la clase Movimientocontrato
     */
    Movimientocontrato movimientocontrato = new Movimientocontrato();
    /**
     * Variable para mostrar la orden de pago
     */
    private SelectItem[] OrdenPagoitem;
    /**
     * Variable para mostrar los tipos de movimientos
     */
    private SelectItem[] TipoMovimientoitem;
    /**
     * Variable para saber si se inicia con giro directo
     */
    private boolean booliniciargirodirecto = false;
    /**
     * Variable para determinar si se va editar el contratista
     */
    private boolean booleditando = false;
    /**
     * Variable para saber si se esta creando un giro
     */
    private boolean boolcreargiro = false;
    /**
     * Variable para almacenar el parametro de busqueda del giro directo
     */
    private String buscargirodirecto = "";
    /**
     * Binding para acceder a los datos de la tabla de giro directos
     */
    private UIDataTable tablaGirodirecto = new UIDataTable();
    /**
     * Listado de movimientos
     */
    private List<Movimientocontrato> listaGirodirecto = new ArrayList<Movimientocontrato>();
    /**
     * Variable para determinar si se esta editando un giro directo
     */
    private boolean booleditargirodirecto = false;
    /**
     * Listado de las modificacion que ha tenido un contrato
     */
    private List<Modificacioncontrato> listaModificarContrato = new ArrayList<Modificacioncontrato>();
    /**
     * Listado temporal de las entidades que tiene el tercero
     */
    private List<TerceroEntidadLista> temp = new ArrayList<TerceroEntidadLista>();
    /**
     * Variable para desahibilitar la asociacion de un encargo fiduciario si se
     * a crear el contrato desde el proyecto
     *
     */
    private boolean boolnumencargosoli;
    /**
     * Instancia de la clase CargadorArchivosWeb utilizada para subir los
     * documentos
     */
    private CargadorArchivosWeb cargadorDocumento = new CargadorArchivosWeb();
    /**
     * Instancia de la clase CargadorArchivosWeb utilizada para subir los
     * documentos de contrato
     */
    private CargadorArchivosWeb subirDocumentoContrato = new CargadorArchivosWeb();
    /**
     * Variable utilizada para saber si un contrato tiene hijos
     */
    private boolean boolconthijo = false;
    /**
     * Listado de los subconvenios
     */
    private List<Contrato> listaSubconvenios = new ArrayList<Contrato>();
    /**
     * Binding para acceder a los datos de la tabla de subconvenios
     */
    private UIDataTable tablaSubconvenios = new UIDataTable();
    /**
     * Variable para saber si el dato es un subconvenio
     */
    private boolean boolsubconvenios = false;
    /**
     * Variable para saber si el dato es una poliza
     */
    private boolean boolpolizas = false;
    /**
     * Variable para saber si el dato es un proyecto
     */
    private boolean boolproyectos = false;
    /**
     * Variable para saber si el dato es un giro
     */
    private boolean boolgiros = false;
    /**
     * Variable para saber si se esta modificando los datos
     */
    private boolean boolmodifca = false;
    /**
     * Objeto para acceder a los atributos de modalidadcontratista
     */
    private Modalidadcontratista modalidadcontratista = new Modalidadcontratista();

    public Modalidadcontratista getModalidadcontratista() {
        return modalidadcontratista;
    }

    public void setModalidadcontratista(Modalidadcontratista modalidadcontratista) {
        this.modalidadcontratista = modalidadcontratista;
    }
    /**
     * Variable para mostrar la modalidad seleccionada por el contratista
     */
    private SelectItem[] modalidadContratista;

    public SelectItem[] getModalidadContratista() {
        return modalidadContratista;
    }

    public void setModalidadContratista(SelectItem[] modalidadContratista) {
        this.modalidadContratista = modalidadContratista;
    }
    /**
     * Variable para habilitar el boton modificar Objeto contrato
     */
    public boolean habilitarmodificar = true;
    /**
     * Variable para habilitar los botones para modificar el objeto del contrato
     */
    public boolean modificartxt = false;
    /**
     * Variable para habilitar el boton modificar El numero del contrato
     */
    public boolean habilitarModificarNumero = true;
    /**
     * Variable para habilitar El boton Guardar
     */
    public boolean habilitarGuardarNumeroContrato = false;
    public boolean habilitarModificarTxtObjeto = true;
    /**
     * Variable para habilitar el boton modificar Objeto contrato
     */
    private boolean habilitarBtnModificarcontrato = true;
    /**
     * Variable para habilitar los botones para modificar el objeto del contrato
     */
    private boolean habilitarBtnGuardarCancelarContrato = false;
    /*
     * variables para realizar la carga de los gerentes de convenio
     */
    private SelectItem[] gerentesDeConvenio;
    /**
     * Clase para manejar la lógica de fuentes de recursos
     */
    private RecursosConvenio recursosconvenio;

    public RecursosConvenio getRecursosconvenio() {
        return recursosconvenio;
    }

    public void setRecursosconvenio(RecursosConvenio recursosconvenio) {
        this.recursosconvenio = recursosconvenio;
    }
    /**
     * Variable temporal para almacenar el tipo de reporte de plan operativo a
     * ejecutar.
     */
    private int tipoReporteVarTmp;

    public int getTipoReporteVarTmp() {
        return tipoReporteVarTmp;
    }

    public void setTipoReporteVarTmp(int tipoReporteVarTmp) {
        this.tipoReporteVarTmp = tipoReporteVarTmp;
    }

    public List<Tercero> getLstentidades() {
        return lstentidades;
    }

    public void setLstentidades(List<Tercero> lstentidades) {
        this.lstentidades = lstentidades;
    }
    /*
     * variables para realizar la carga del tipo de aporte
     */
    private String variabletitulo;
    private String infogeneralcrearconvenio;
    private int panelPantalla;
    private List<Obra> listaProyectosCovenio;
    private int reportoption;
    private int subpantalla;
    /**
     * variable para saber si el convenio tiene todas las validaciones ok
     */
    private int guardadoconexito = 0;

    /*
     * variable para saber si el convenio tiene los datos basicos
     */
    private int validardatosbasicosplano = 0;

    /**
     * Variable para ver los reportes de plan operativo
     *
     * @return
     */
    public int getReportoption() {
        return reportoption;
    }

    public void setReportoption(int reportoption) {
        this.reportoption = reportoption;
    }

    public int getGuardadoconexito() {
        return guardadoconexito;
    }

    public void setGuardadoconexito(int guardadoconexito) {
        this.guardadoconexito = guardadoconexito;
    }

    public int getValidardatosbasicosplano() {
        return validardatosbasicosplano;
    }

    public void setValidardatosbasicosplano(int validardatosbasicosplano) {
        this.validardatosbasicosplano = validardatosbasicosplano;
    }

    /**
     *
     */
    /**
     * Set y get de todas las variables anteriores
     *
     */
    public boolean isHabilitarBtnModificarcontrato() {
        return habilitarBtnModificarcontrato;
    }

    public void setHabilitarBtnModificarcontrato(boolean habilitarBtnModificarcontrato) {
        this.habilitarBtnModificarcontrato = habilitarBtnModificarcontrato;
    }

    public boolean isHabilitarBtnGuardarCancelarContrato() {
        return habilitarBtnGuardarCancelarContrato;
    }

    public void setHabilitarBtnGuardarCancelarContrato(boolean habilitarBtnGuardarCancelarContrato) {
        this.habilitarBtnGuardarCancelarContrato = habilitarBtnGuardarCancelarContrato;
    }

    public boolean isHabilitarModificarNumero() {
        return habilitarModificarNumero;
    }

    public void setHabilitarModificarNumero(boolean habilitarModificarNumero) {
        this.habilitarModificarNumero = habilitarModificarNumero;
    }

    public boolean isHabilitarGuardarNumeroContrato() {
        return habilitarGuardarNumeroContrato;
    }

    public void setHabilitarGuardarNumeroContrato(boolean habilitarGuardarNumeroContrato) {
        this.habilitarGuardarNumeroContrato = habilitarGuardarNumeroContrato;
    }

    public boolean isHabilitarModificarTxtObjeto() {
        return habilitarModificarTxtObjeto;
    }

    public void setHabilitarModificarTxtObjeto(boolean habilitarModificarTxtObjeto) {
        this.habilitarModificarTxtObjeto = habilitarModificarTxtObjeto;
    }

    public boolean isModificartxt() {
        return modificartxt;
    }

    public void setModificartxt(boolean modificartxt) {
        this.modificartxt = modificartxt;
    }

    public boolean isHabilitarmodificar() {
        return habilitarmodificar;
    }

    public void setHabilitarmodificar(boolean habilitarmodificar) {
        this.habilitarmodificar = habilitarmodificar;
    }

    /**
     *
     */
    /**
     * Set y get de todas las variables anteriores
     *
     */
    public boolean isBoolgiros() {
        return boolgiros;
    }

    public void setBoolgiros(boolean boolgiros) {
        this.boolgiros = boolgiros;
    }

    public boolean isBoolmodifca() {
        return boolmodifca;
    }

    public void setBoolmodifca(boolean boolmodifca) {
        this.boolmodifca = boolmodifca;
    }

    public boolean isBoolpolizas() {
        return boolpolizas;
    }

    public void setBoolpolizas(boolean boolpolizas) {
        this.boolpolizas = boolpolizas;
    }

    public boolean isBoolproyectos() {
        return boolproyectos;
    }

    public void setBoolproyectos(boolean boolproyectos) {
        this.boolproyectos = boolproyectos;
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public List<TerceroEntidadLista> getTemp() {
        return temp;
    }

    public void setTemp(List<TerceroEntidadLista> temp) {
        this.temp = temp;
    }

    public UIDataTable getTablaSubconvenios() {
        return tablaSubconvenios;
    }

    public void setTablaSubconvenios(UIDataTable tablaSubconvenios) {
        this.tablaSubconvenios = tablaSubconvenios;
    }

    public List<Contrato> getListaSubconvenios() {
        return listaSubconvenios;
    }

    public void setListaSubconvenios(List<Contrato> listaSubconvenios) {
        this.listaSubconvenios = listaSubconvenios;
    }

    public boolean isBoolconthijo() {
        return boolconthijo;
    }

    public void setBoolconthijo(boolean boolconthijo) {
        this.boolconthijo = boolconthijo;
    }

    public boolean isBoolnumencargosoli() {
        return boolnumencargosoli;
    }

    public void setBoolnumencargosoli(boolean boolnumencargosoli) {
        this.boolnumencargosoli = boolnumencargosoli;
    }

    public boolean isBoolsubconvenios() {
        return boolsubconvenios;
    }

    public void setBoolsubconvenios(boolean boolsubconvenios) {
        this.boolsubconvenios = boolsubconvenios;
    }

    public String getPalabraclave() {
        return palabraclave;
    }

    public void setPalabraclave(String palabraclave) {
        this.palabraclave = palabraclave;
    }

    public CargadorArchivosWeb getSubirDocumentoContrato() {
        return subirDocumentoContrato;
    }

    public void setSubirDocumentoContrato(CargadorArchivosWeb subirDocumentoContrato) {
        this.subirDocumentoContrato = subirDocumentoContrato;
    }

    public UIDataTable getTablaDocumentosContrato() {
        return tablaDocumentosContrato;
    }

    public void setTablaDocumentosContrato(UIDataTable tablaDocumentosContrato) {
        this.tablaDocumentosContrato = tablaDocumentosContrato;
    }

    public Boolean getBoolnumencargosoli() {
        return boolnumencargosoli;
    }

    public void setBoolnumencargosoli(Boolean boolnumencargosoli) {
        this.boolnumencargosoli = boolnumencargosoli;
    }

    public List<Modificacioncontrato> getListaModificarContrato() {
        return listaModificarContrato;
    }

    public void setListaModificarContrato(List<Modificacioncontrato> listaModificarContrato) {
        this.listaModificarContrato = listaModificarContrato;
    }

    public FiltroAvanzadoContrato getFiltrocontrato() {
        return filtrocontrato;
    }

    public void setFiltrocontrato(FiltroAvanzadoContrato filtrocontrato) {
        this.filtrocontrato = filtrocontrato;
    }

    public boolean isBooleditargirodirecto() {
        return booleditargirodirecto;
    }

    public void setBooleditargirodirecto(boolean booleditargirodirecto) {
        this.booleditargirodirecto = booleditargirodirecto;
    }

    public List<Movimientocontrato> getListaGirodirecto() {
        return listaGirodirecto;
    }

    public void setListaGirodirecto(List<Movimientocontrato> listaGirodirecto) {
        this.listaGirodirecto = listaGirodirecto;
    }

    public UIDataTable getTablaGirodirecto() {
        return tablaGirodirecto;
    }

    public void setTablaGirodirecto(UIDataTable tablaGirodirecto) {
        this.tablaGirodirecto = tablaGirodirecto;
    }

    public String getBuscargirodirecto() {
        return buscargirodirecto;
    }

    public void setBuscargirodirecto(String buscargirodirecto) {
        this.buscargirodirecto = buscargirodirecto;
    }

    public boolean isBoolcreargiro() {
        return boolcreargiro;
    }

    public void setBoolcreargiro(boolean boolcreargiro) {
        this.boolcreargiro = boolcreargiro;
    }

    public boolean isBooliniciargirodirecto() {
        return booliniciargirodirecto;
    }

    public void setBooliniciargirodirecto(boolean booliniciargirodirecto) {
        this.booliniciargirodirecto = booliniciargirodirecto;
    }

    public SelectItem[] getOrdenPagoitem() {
        return OrdenPagoitem;
    }

    public void setOrdenPagoitem(SelectItem[] OrdenPagoitem) {
        this.OrdenPagoitem = OrdenPagoitem;
    }

    public SelectItem[] getTipoMovimientoitem() {
        return TipoMovimientoitem;
    }

    public void setTipoMovimientoitem(SelectItem[] TipoMovimientoitem) {
        this.TipoMovimientoitem = TipoMovimientoitem;
    }

    public Movimientocontrato getMovimientocontrato() {
        return movimientocontrato;
    }

    public void setMovimientocontrato(Movimientocontrato movimientocontrato) {
        this.movimientocontrato = movimientocontrato;
    }

    public boolean isBooleditando() {
        return booleditando;
    }

    public void setBooleditando(boolean booleditando) {
        this.booleditando = booleditando;
    }

    public boolean isBoolcrearcontratista() {
        return boolcrearcontratista;
    }

    public void setBoolcrearcontratista(boolean boolcrearcontratista) {
        this.boolcrearcontratista = boolcrearcontratista;
    }

    public boolean isBoolcrearpoliza() {
        return boolcrearpoliza;
    }

    public void setBoolcrearpoliza(boolean boolcrearpoliza) {
        this.boolcrearpoliza = boolcrearpoliza;
    }

    public int getAnticiforma() {
        return anticiforma;
    }

    public void setAnticiforma(int anticiforma) {
        this.anticiforma = anticiforma;
    }

    public Date getFechapago() {
        return fechapago;
    }

    public void setFechapago(Date fechapago) {
        this.fechapago = fechapago;
    }

    public BigDecimal getPorcenanticipo() {
        return porcenanticipo;
    }

    public void setPorcenanticipo(BigDecimal porcenanticipo) {
        this.porcenanticipo = porcenanticipo;
    }

    public BigDecimal getVloranticipo() {
        return vloranticipo;
    }

    public void setVloranticipo(BigDecimal vloranticipo) {
        this.vloranticipo = vloranticipo;
    }

    public int getIndicepolizamodificar() {
        return indicepolizamodificar;
    }

    public void setIndicepolizamodificar(int indicepolizamodificar) {
        this.indicepolizamodificar = indicepolizamodificar;
    }

    public boolean isBoolcontrconsultoria() {
        return boolcontrconsultoria;
    }

    public void setBoolcontrconsultoria(boolean boolcontrconsultoria) {
        this.boolcontrconsultoria = boolcontrconsultoria;
    }

    public UIDataTable getTablacontconvhijo() {
        return tablacontconvhijo;
    }

    public void setTablacontconvhijo(UIDataTable tablacontconvhijo) {
        this.tablacontconvhijo = tablacontconvhijo;
    }

    public SelectItem[] getMpioNacimiento() {
        return MpioNacimiento;
    }

    public void setMpioNacimiento(SelectItem[] MpioNacimiento) {
        this.MpioNacimiento = MpioNacimiento;
    }

    public SelectItem[] getDeptoNacimiento() {
        return DeptoNacimiento;
    }

    public void setDeptoNacimiento(SelectItem[] DeptoNacimiento) {
        this.DeptoNacimiento = DeptoNacimiento;
    }

    public String getCodDeptoNacimiento() {
        return codDeptoNacimiento;
    }

    public void setCodDeptoNacimiento(String codDeptoNacimiento) {
        this.codDeptoNacimiento = codDeptoNacimiento;
    }

    public SelectItem[] getMpioExpdocumento() {
        return MpioExpdocumento;
    }

    public void setMpioExpdocumento(SelectItem[] MpioExpdocumento) {
        this.MpioExpdocumento = MpioExpdocumento;
    }

    public SelectItem[] getDeptoExpdocumento() {
        return DeptoExpdocumento;
    }

    public void setDeptoExpdocumento(SelectItem[] DeptoExpdocumento) {
        this.DeptoExpdocumento = DeptoExpdocumento;
    }

    public String getCodDeptoExpdocumento() {
        return codDeptoExpdocumento;
    }

    public void setCodDeptoExpdocumento(String codDeptoExpdocumento) {
        this.codDeptoExpdocumento = codDeptoExpdocumento;
    }

    public String getBuscarproyecto() {
        return buscarproyecto;
    }

    public void setBuscarproyecto(String buscarproyecto) {
        this.buscarproyecto = buscarproyecto;
    }

    public boolean isVerultimoobra() {
        return verultimoobra;
    }

    public void setVerultimoobra(boolean verultimoobra) {
        this.verultimoobra = verultimoobra;
    }

    public boolean isVeranteriorobra() {
        return veranteriorobra;
    }

    public void setVeranteriorobra(boolean veranteriorobra) {
        this.veranteriorobra = veranteriorobra;
    }

    public boolean isAplicafiltroobra() {
        return aplicafiltroobra;
    }

    public void setAplicafiltroobra(boolean aplicafiltroobra) {
        this.aplicafiltroobra = aplicafiltroobra;
    }

    public UIDataTable getTablacontconvproyecto() {
        return tablacontconvproyecto;
    }

    public void setTablacontconvproyecto(UIDataTable tablacontconvproyecto) {
        this.tablacontconvproyecto = tablacontconvproyecto;
    }

    public BigDecimal getPorcentapagoanticipo() {
        return porcentapagoanticipo;
    }

    public void setPorcentapagoanticipo(BigDecimal porcentapagoanticipo) {
        this.porcentapagoanticipo = porcentapagoanticipo;
    }

    public Planificacionpago getPlapagorow() {
        return plapagorow;
    }

    public void setPlapagorow(Planificacionpago plapagorow) {
        this.plapagorow = plapagorow;
    }

    public int getCodigoencargo() {
        return codigoencargo;
    }

    public void setCodigoencargo(int codigoencargo) {
        this.codigoencargo = codigoencargo;
    }

    public boolean isVerultimosencargo() {
        return verultimosencargo;
    }

    public void setVerultimosencargo(boolean verultimosencargo) {
        this.verultimosencargo = verultimosencargo;
    }

    public boolean isAplicafiltroencargo() {
        return aplicafiltroencargo;
    }

    public void setAplicafiltroencargo(boolean aplicafiltroencargo) {
        this.aplicafiltroencargo = aplicafiltroencargo;
    }

    public boolean isVeranteriorencargo() {
        return veranteriorencargo;
    }

    public void setVeranteriorencargo(boolean veranteriorencargo) {
        this.veranteriorencargo = veranteriorencargo;
    }

    public Contrato getContrhijo() {
        return contrhijo;
    }

    public void setContrhijo(Contrato contrhijo) {
        this.contrhijo = contrhijo;
    }

    public int getPreguntacontrato() {
        return preguntacontrato;
    }

    public void setPreguntacontrato(int preguntacontrato) {
        this.preguntacontrato = preguntacontrato;
    }

    public List<Contrato> getListaContrConvHijo() {
        return listaContrConvHijo;
    }

    public void setListaContrConvHijo(List<Contrato> listaContrConvHijo) {
        this.listaContrConvHijo = listaContrConvHijo;
    }

    public SelectItem[] getSubTiposProyecto() {
        return subTiposProyecto;
    }

    public void setSubTiposProyecto(SelectItem[] subTiposProyecto) {
        this.subTiposProyecto = subTiposProyecto;
    }

    public SelectItem[] getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(SelectItem[] departamentos) {
        this.departamentos = departamentos;
    }

    public SelectItem[] getMunicipios() {
        return municipios;
    }

    public void setMunicipios(SelectItem[] municipios) {
        this.municipios = municipios;
    }

    public Tipoorigen getTipoOrigenUsuario() {
        return tipoOrigenUsuario;
    }

    public void setTipoOrigenUsuario(Tipoorigen tipoOrigenUsuario) {
        this.tipoOrigenUsuario = tipoOrigenUsuario;
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

    public List<Contrato> getListaavanzada() {
        return listaavanzada;
    }

    public void setListaavanzada(List<Contrato> listaavanzada) {
        this.listaavanzada = listaavanzada;
    }

    public List<Tercero> getListaContrEnti() {
        return listaContrEnti;
    }

    public void setListaContrEnti(List<Tercero> listaContrEnti) {
        this.listaContrEnti = listaContrEnti;
    }

    public SelectItem[] getTipoproyecto() {
        return tipoproyecto;
    }

    public void setTipoproyecto(SelectItem[] tipoproyecto) {
        this.tipoproyecto = tipoproyecto;
    }

    public Fase getFase() {
        return fase;
    }

    public void setFase(Fase fase) {
        this.fase = fase;
    }

    public double getVlpor() {
        return vlpor;
    }

    public void setVlpor(double vlpor) {
        this.vlpor = vlpor;
    }

    public int getCostoFin() {
        return costoFin;
    }

    public void setCostoFin(int costoFin) {
        this.costoFin = costoFin;
    }

    public int getCostoIni() {
        return costoIni;
    }

    public void setCostoIni(int costoIni) {
        this.costoIni = costoIni;
    }

    public SelectItem[] getFasecombo() {
        return fasecombo;
    }

    public void setFasecombo(SelectItem[] fasecombo) {
        this.fasecombo = fasecombo;
    }

    public boolean isProyectoasociado() {
        return proyectoasociado;
    }

    public void setProyectoasociado(boolean proyectoasociado) {
        this.proyectoasociado = proyectoasociado;
    }

    public List<Obra> getListaObraContrato() {
        return listaObraContrato;
    }

    public void setListaObraContrato(List<Obra> listaObraContrato) {
        this.listaObraContrato = listaObraContrato;
    }

    public boolean isAplicafiltrocontrato() {
        return aplicafiltrocontrato;
    }

    public void setAplicafiltrocontrato(boolean aplicafiltrocontrato) {
        this.aplicafiltrocontrato = aplicafiltrocontrato;
    }

    public boolean isVerultimoscontrato() {
        return verultimoscontrato;
    }

    public void setVerultimoscontrato(boolean verultimoscontrato) {
        this.verultimoscontrato = verultimoscontrato;
    }

    public boolean isVeranteriorcontrato() {
        return veranteriorcontrato;
    }

    public void setVeranteriorcontrato(boolean veranteriorcontrato) {
        this.veranteriorcontrato = veranteriorcontrato;
    }

    public String getNomcontrato() {
        return nomcontrato;
    }

    public void setNomcontrato(String nomcontrato) {
        this.nomcontrato = nomcontrato;
    }

    public boolean isBoolreporte() {
        return boolreporte;
    }

    public void setBoolreporte(boolean boolreporte) {
        this.boolreporte = boolreporte;
    }

    public boolean isHabidescontratista() {
        return habidescontratista;
    }

    public void setHabidescontratista(boolean habidescontratista) {
        this.habidescontratista = habidescontratista;
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

    public SelectItem[] getCargoOption() {
        return CargoOption;
    }

    public void setCargoOption(SelectItem[] CargoOption) {
        this.CargoOption = CargoOption;
    }

    public SelectItem[] getTipoIdentificacionOption() {
        return TipoIdentificacionOption;
    }

    public void setTipoIdentificacionOption(SelectItem[] TipoIdentificacionOption) {
        this.TipoIdentificacionOption = TipoIdentificacionOption;
    }

    public SelectItem[] getMunicipioContratista() {
        return MunicipioContratista;
    }

    public void setMunicipioContratista(SelectItem[] MunicipioContratista) {
        this.MunicipioContratista = MunicipioContratista;
    }

    public List<Localidad> getListaMunicipios() {
        return listaMunicipios;
    }

    public void setListaMunicipios(List<Localidad> listaMunicipios) {
        this.listaMunicipios = listaMunicipios;
    }

    public SelectItem[] getDepartamentoContratista() {
        return DepartamentoContratista;
    }

    public void setDepartamentoContratista(SelectItem[] DepartamentoContratista) {
        this.DepartamentoContratista = DepartamentoContratista;
    }

    public List<Localidad> getListaDeptos() {
        return listaDeptos;
    }

    public void setListaDeptos(List<Localidad> listaDeptos) {
        this.listaDeptos = listaDeptos;
    }

    public String getCodDepartamentoContratista() {
        return codDepartamentoContratista;
    }

    public void setCodDepartamentoContratista(String codDepartamentoContratista) {
        this.codDepartamentoContratista = codDepartamentoContratista;
    }

    public SelectItem[] getAreaContratistaOption() {
        return AreaContratistaOption;
    }

    public void setAreaContratistaOption(SelectItem[] AreaContratistaOption) {
        this.AreaContratistaOption = AreaContratistaOption;
    }

    public SelectItem[] getTipoTerceroOPtion() {
        return TipoTerceroOPtion;
    }

    public void setTipoTerceroOPtion(SelectItem[] TipoTerceroOPtion) {
        this.TipoTerceroOPtion = TipoTerceroOPtion;
    }

    public Contratista getContratista() {
        return contratista;
    }

    public void setContratista(Contratista contratista) {
        this.contratista = contratista;
    }

    public UIDataTable getTablacontratistas() {
        return tablacontratistas;
    }

    public void setTablacontratistas(UIDataTable tablacontratistas) {
        this.tablacontratistas = tablacontratistas;
    }

    public boolean isVerultimosreales() {
        return verultimosreales;
    }

    public void setVerultimosreales(boolean verultimosreales) {
        this.verultimosreales = verultimosreales;
    }

    public boolean isVeranteriorreales() {
        return veranteriorreales;
    }

    public void setVeranteriorreales(boolean veranteriorreales) {
        this.veranteriorreales = veranteriorreales;
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Contratista> getListaContratista() {
        return listaContratista;
    }

    public void setListaContratista(List<Contratista> listaContratista) {
        this.listaContratista = listaContratista;
    }

    public boolean isAplicafiltro() {
        return aplicafiltro;
    }

    public void setAplicafiltro(boolean aplicafiltro) {
        this.aplicafiltro = aplicafiltro;
    }

    public int getTipointpoli() {
        return tipointpoli;
    }

    public void setTipointpoli(int tipointpoli) {
        this.tipointpoli = tipointpoli;
    }

    public UIDataTable getTabladocuContrato() {
        return tabladocuContrato;
    }

    public void setTabladocuContrato(UIDataTable tabladocuContrato) {
        this.tabladocuContrato = tabladocuContrato;
    }

    public List<Documentoobra> getListadocuContrato() {
        return listadocuContrato;
    }

    public void setListadocuContrato(List<Documentoobra> listadocuContrato) {
        this.listadocuContrato = listadocuContrato;
    }

    public String getFinentrega() {
        return finentrega;
    }

    public void setFinentrega(String finentrega) {
        this.finentrega = finentrega;
    }

    public UIDataTable getTablacontratoconvenio() {
        return tablacontratoconvenio;
    }

    public void setTablacontratoconvenio(UIDataTable tablacontratoconvenio) {
        this.tablacontratoconvenio = tablacontratoconvenio;
    }

    public String getRealArchivoPath() {
        return realArchivoPath;
    }

    public void setRealArchivoPath(String realArchivoPath) {
        this.realArchivoPath = realArchivoPath;
    }

    public Encargofiduciario getEncargofiduciario() {
        return encargofiduciario;
    }

    public BigDecimal getTotalfuenteconvenio() {
        return totalfuenteconvenio;
    }

    public void setTotalfuenteconvenio(BigDecimal totalfuenteconvenio) {
        this.totalfuenteconvenio = totalfuenteconvenio;
    }

    public BigDecimal getFaltafuenteconvenio() {
        return faltafuenteconvenio;
    }

    public void setFaltafuenteconvenio(BigDecimal faltafuenteconvenio) {
        this.faltafuenteconvenio = faltafuenteconvenio;
    }

    public void setEncargofiduciario(Encargofiduciario encargofiduciario) {
        this.encargofiduciario = encargofiduciario;
    }

    public String getValorFiltroEncargo() {
        return valorFiltroEncargo;
    }

    public void setValorFiltroEncargo(String valorFiltroEncargo) {
        this.valorFiltroEncargo = valorFiltroEncargo;
    }

    public UIDataTable getTablaEncargofiduciario() {
        return tablaEncargofiduciario;
    }

    public void setTablaEncargofiduciario(UIDataTable tablaEncargofiduciario) {
        this.tablaEncargofiduciario = tablaEncargofiduciario;
    }

    public List<Encargofiduciario> getListaEncargofiduciario() {
        return listaEncargofiduciario;
    }

    public void setListaEncargofiduciario(List<Encargofiduciario> listaEncargofiduciario) {
        this.listaEncargofiduciario = listaEncargofiduciario;
    }

    public String getPathActa() {
        return pathActa;
    }

    public void setPathActa(String pathActa) {
        this.pathActa = pathActa;
    }

    public CargadorArchivosWeb getSubirDocPol() {
        return subirDocPol;
    }

    public void setSubirDocPol(CargadorArchivosWeb subirDocPol) {
        this.subirDocPol = subirDocPol;
    }

    public List<Tipodocumento> getListatipodocumentos() {
        return listatipodocumentos;
    }

    public void setListatipodocumentos(List<Tipodocumento> listatipodocumentos) {
        this.listatipodocumentos = listatipodocumentos;
    }

    public int getTipodoc() {
        return tipodoc;
    }

    public void setTipodoc(int tipodoc) {
        this.tipodoc = tipodoc;
    }

    public SelectItem[] getTipodocumento() {
        return tipodocumento;
    }

    public void setTipodocumento(SelectItem[] tipodocumento) {
        this.tipodocumento = tipodocumento;
    }

    public List<Documentoobra> getListadocumentos() {
        return listadocumentos;
    }

    public void setListadocumentos(List<Documentoobra> listadocumentos) {
        this.listadocumentos = listadocumentos;
    }

    public CargadorArchivosWeb getCargadorDocumento() {
        return cargadorDocumento;
    }

    public void setCargadorDocumento(CargadorArchivosWeb cargadorDocumento) {
        this.cargadorDocumento = cargadorDocumento;
    }

    public Documentoobra getDocumentoobra() {
        return documentoobra;
    }

    public void setDocumentoobra(Documentoobra documentoobra) {
        this.documentoobra = documentoobra;
    }

    public int getSeleccioncmbformadepago() {
        return seleccioncmbformadepago;
    }

    public void setSeleccioncmbformadepago(int seleccioncmbformadepago) {
        this.seleccioncmbformadepago = seleccioncmbformadepago;
    }

    public UIDataTable getTablabindinanticiyactasparci() {
        return tablabindinanticiyactasparci;
    }

    public void setTablabindinanticiyactasparci(UIDataTable tablabindinanticiyactasparci) {
        this.tablabindinanticiyactasparci = tablabindinanticiyactasparci;
    }

    public Date getFechapagoanticipo() {
        return fechapagoanticipo;
    }

    public void setFechapagoanticipo(Date fechapagoanticipo) {
        this.fechapagoanticipo = fechapagoanticipo;
    }

    public BigDecimal getValorpagoanticipo() {
        return valorpagoanticipo;
    }

    public void setValorpagoanticipo(BigDecimal valorpagoanticipo) {
        this.valorpagoanticipo = valorpagoanticipo;
    }

    public int getTipomensajeerror() {
        return tipomensajeerror;
    }

    public void setTipomensajeerror(int tipomensajeerror) {
        this.tipomensajeerror = tipomensajeerror;
    }

    public List<Planificacionpago> getLisplanifiactapar() {
        return lisplanifiactapar;
    }

    public void setLisplanifiactapar(List<Planificacionpago> lisplanifiactapar) {
        this.lisplanifiactapar = lisplanifiactapar;
    }

    public int getNumdeactasparciales() {
        return numdeactasparciales;
    }

    public void setNumdeactasparciales(int numdeactasparciales) {
        this.numdeactasparciales = numdeactasparciales;
    }

    public Planificacionpago getPlanificacionpago() {
        return planificacionpago;
    }

    public void setPlanificacionpago(Planificacionpago planificacionpago) {
        this.planificacionpago = planificacionpago;
    }

    public List<Contrato> getListacontratos() {
        return listacontratos;
    }

    public void setListacontratos(List<Contrato> listacontratos) {
        this.listacontratos = listacontratos;
    }

    public List<Contrato> getListacontratoscontratista() {
        return listacontratoscontratista;
    }

    public void setListacontratoscontratista(List<Contrato> listacontratoscontratista) {
        this.listacontratoscontratista = listacontratoscontratista;
    }

    public int getVarmostrarcontrpa() {
        return varmostrarcontrpa;
    }

    public void setVarmostrarcontrpa(int varmostrarcontrpa) {
        this.varmostrarcontrpa = varmostrarcontrpa;
    }

    public List<Contrato> getListaContraPadreSele() {
        return listaContraPadreSele;
    }

    public void setListaContraPadreSele(List<Contrato> listaContraPadreSele) {
        this.listaContraPadreSele = listaContraPadreSele;
    }

    public Contrato getContrpadre() {
        return contrpadre;
    }

    public void setContrpadre(Contrato contrpadre) {
        this.contrpadre = contrpadre;
    }

    public UIDataTable getTablacontrapadrebindin() {
        return tablacontrapadrebindin;
    }

    public void setTablacontrapadrebindin(UIDataTable tablacontrapadrebindin) {
        this.tablacontrapadrebindin = tablacontrapadrebindin;
    }

    public UIDataTable getTablacontrapadrebindinContratista() {
        return tablacontrapadrebindinContratista;
    }

    public void setTablacontrapadrebindinContratista(UIDataTable tablacontrapadrebindinContratista) {
        this.tablacontrapadrebindinContratista = tablacontrapadrebindinContratista;
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

    public UIDataTable getTablacontratos() {
        return tablacontratos;
    }

    public void setTablacontratos(UIDataTable tablacontratos) {
        this.tablacontratos = tablacontratos;
    }

    public List<Contrato> getListaContratosPadre() {
        return listaContratosPadre;
    }

    public void setListaContratosPadre(List<Contrato> listaContratosPadre) {
        this.listaContratosPadre = listaContratosPadre;
    }

    public String getValorFiltroContratos() {
        return valorFiltroContratos;
    }

    public void setValorFiltroContratos(String valorFiltroContratos) {
        this.valorFiltroContratos = valorFiltroContratos;
    }

    public String getTipoContCon() {
        return tipoContCon;
    }

    public void setTipoContCon(String tipoContCon) {
        this.tipoContCon = tipoContCon;
    }

    public boolean isBooltipocontratoconvenio() {
        return booltipocontratoconvenio;
    }

    public void setBooltipocontratoconvenio(boolean booltipocontratoconvenio) {
        this.booltipocontratoconvenio = booltipocontratoconvenio;
    }

    public SelectItem[] getTipoContrato() {
        return TipoContrato;
    }

    public void setTipoContrato(SelectItem[] TipoContrato) {
        this.TipoContrato = TipoContrato;
    }

    public Polizacontrato getPolizacontrato() {
        return polizacontrato;
    }

    public void setPolizacontrato(Polizacontrato polizacontrato) {
        this.polizacontrato = polizacontrato;
    }

    public SelectItem[] getCompanias() {
        return Companias;
    }

    public void setCompanias(SelectItem[] Companias) {
        this.Companias = Companias;
    }

    public SelectItem[] getTipoPolizas() {
        return TipoPolizas;
    }

    public void setTipoPolizas(SelectItem[] TipoPolizas) {
        this.TipoPolizas = TipoPolizas;
    }

    public List<Polizacontrato> getListapolizas() {
        return listapolizas;
    }

    public void setListapolizas(List<Polizacontrato> listapolizas) {
        this.listapolizas = listapolizas;
    }

    public int getVerEliminar() {
        return verEliminar;
    }

    public void setVerEliminar(int verEliminar) {
        this.verEliminar = verEliminar;
    }

    public int getMostrarAgregarPol() {
        return mostrarAgregarPol;
    }

    public void setMostrarAgregarPol(int mostrarAgregarPol) {
        this.mostrarAgregarPol = mostrarAgregarPol;
    }

    public SelectItem[] getTipocontrato() {
        return tipocontrato;
    }

    public void setTipocontrato(SelectItem[] tipocontrato) {
        this.tipocontrato = tipocontrato;
    }

    public SelectItem[] getTipocontratoconsultoria() {
        return tipocontratoconsultoria;
    }

    public void setTipocontratoconsultoria(SelectItem[] tipocontratoconsultoria) {
        this.tipocontratoconsultoria = tipocontratoconsultoria;
    }

    public SelectItem[] getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(SelectItem[] formaPago) {
        this.formaPago = formaPago;
    }

    public SelectItem[] getTerceroOption() {
        return TerceroOption;
    }

    public void setTerceroOption(SelectItem[] TerceroOption) {
        this.TerceroOption = TerceroOption;
    }

    public SelectItem[] getPoliza() {
        return Poliza;
    }

    public void setPoliza(SelectItem[] Poliza) {
        this.Poliza = Poliza;
    }

    public boolean isPolizacontratobo() {
        return polizacontratobo;
    }

    public void setPolizacontratobo(boolean polizacontratobo) {
        this.polizacontratobo = polizacontratobo;
    }

    public List<Polizacontrato> getListaPolizacontratos() {
        return listaPolizacontratos;
    }

    public void setListaPolizacontratos(List<Polizacontrato> listaPolizacontratos) {
        this.listaPolizacontratos = listaPolizacontratos;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public Actividadobra getActividadobra() {
        return actividadobra;
    }

    public void setActividadobra(Actividadobra actividadobra) {
        this.actividadobra = actividadobra;
    }

    public int getPaginaContratista() {
        return paginaContratista;
    }

    public void setPaginaContratista(int paginaContratista) {
        this.paginaContratista = paginaContratista;
    }

    public int getTotalfilasContratista() {
        return totalfilasContratista;
    }

    public void setTotalfilasContratista(int totalfilasContratista) {
        this.totalfilasContratista = totalfilasContratista;
    }

    public int getTotalpaginasContratista() {
        return totalpaginasContratista;
    }

    public void setTotalpaginasContratista(int totalpaginasContratista) {
        this.totalpaginasContratista = totalpaginasContratista;
    }

    public boolean isVeranteriorcontratoContratista() {
        return veranteriorcontratoContratista;
    }

    public void setVeranteriorcontratoContratista(boolean veranteriorcontratoContratista) {
        this.veranteriorcontratoContratista = veranteriorcontratoContratista;
    }

    public boolean isVerultimoscontratoContratista() {
        return verultimoscontratoContratista;
    }

    public void setVerultimoscontratoContratista(boolean verultimoscontratoContratista) {
        this.verultimoscontratoContratista = verultimoscontratoContratista;
    }
    
    
    /**
     * Binding creado para acceder al registro seleccionado en la tabla de
     * contratos según el contratista
     */
    private UIDataTable tablacontratoconveniocontratista = new UIDataTable();

    public UIDataTable getTablacontratoconveniocontratista() {
        return tablacontratoconveniocontratista;
    }

    public void setTablacontratoconveniocontratista(UIDataTable tablacontratoconveniocontratista) {
        this.tablacontratoconveniocontratista = tablacontratoconveniocontratista;
    }
   

    
     /**
     * Binding creado para acceder a los datos de las filas de la tabla creada
     */
    private UIDataTable tablaPolizasbin = new UIDataTable();

    public UIDataTable getTablaPolizasbin() {
        return tablaPolizasbin;
    }

    public void setTablaPolizasbin(UIDataTable tablaPolizasbin) {
        this.tablaPolizasbin = tablaPolizasbin;
    }

  
    
    /**
     * <p>Automatically managed component initialization.
     * <strong>WARNING:</strong> This method is automatically generated, so any
     * user-specified code inserted here is subject to being replaced.</p>
     */
    private void _init() throws Exception {
    }
    // </editor-fold>
    private List<Tercero> lstentidades = new ArrayList<Tercero>();

    @Override
    public void prerender() {
        if (getSessionBeanCobra().isCargarcontrato()) {

            actualizarContratodatosGwt(getSessionBeanCobra().getCobraGwtService().getContratoDto());

            actualizarPanel(2);
            actualizarSubpantallaPlanOperativo(getSessionBeanCobra().getCobraGwtService().getNavegacion());
            if (getSubpantalla() == 2) {
                getFlujoCaja().iniciarFlujoCaja();
            }
            getSessionBeanCobra().setCargarcontrato(false);

        }
    }

    /**
     * <p>Construct a new Page bean instance.</p>
     */
    /* variables para el funcionamiento del plan operativo*/
    public NuevoContratoBasico() {
        llenarTipodocumentos();
        llenarPolizas();
        if (!Propiedad.getValor("conplanoperativo").equals("true")) {
            limpiarContrato();
            llenarTipoContratoconsultoria();
            llenarTipoContrato();
            llenarEventos();
            llenarPeriodoxEvento();
            llenarFormaPago();
            llenarComboContratista();
            iniciarFiltroAvanzado();
            if (getSessionBeanCobra().getBundle().getString("aplicaContralorias").equals("true")) {
                llenarModalidadContratista();
            }
        } else {
            setRecursosconvenio(new RecursosConvenio(getContrato(), getSessionBeanCobra().getCobraService()));
            listaProyectosCovenio = new ArrayList<Obra>();
            variabletitulo = Propiedad.getValor("primerodatosbasicos");
            llenarEstadoConvenio();
            llenarEntidades();
            llenarGerentes();


        }

    }

    public String instanciarPolizar() {
        polizacontrato = new Polizacontrato();
        polizacontrato.setTipopoliza(new Tipopoliza());
        polizacontrato.setAseguradora(new Aseguradora());
        llenarTiposPoliza();
        llenarCompanias();
        boolcrearpoliza = false;
        return null;
    }

    public String inicializarformapago() {
        contrato.setFormapago(new Formapago());
        llenarFormaPago();
        return null;
    }

    /**
     * Se buscan las pólizas asociadas al contrato y se llenan en el consultar
     * del contrato
     *
     * @return
     */
    public String llenarPolizas() {
        listaPolizacontratos = getSessionBeanCobra().getCobraService().encontrarPolizasxContrato(getContrato().getIntidcontrato());
        Poliza = new SelectItem[listaPolizacontratos.size()];
        int i = 0;
        for (Polizacontrato polCont : listaPolizacontratos) {
            SelectItem poli = new SelectItem(polCont.getIntidpolizacontrato(), polCont.getStrdescripcion());
            Poliza[i++] = poli;
        }
        return null;
    }
    /*
     * Según el usuario logueado se llenan las entidades o la entidad
     */

    public void llenarEntidadesContratantes() {
        try {

            List<Tercero> lis = getSessionBeanCobra().getUsuarioService().encontrarEntidadesxtercero(getSessionBeanCobra().getUsuarioObra(), 6, booltipocontratoconvenio);
            TerceroOption = new SelectItem[lis.size()];
            int i = 0;
            SelectItem itemTercero = new SelectItem();
            for (Tercero ter : lis) {

                // itemTercero = new SelectItem(ter.getStrnombrecompleto() + "-" + ter.getLocalidadByStrcodigolocalidad().getStrdepartamento());
//                }else{
//                      itemTercero = new SelectItem(ter.getStrnombrecompleto());
//                }
                itemTercero = new SelectItem(ter.getStrnombrecompleto());
                TerceroEntidadLista terce = new TerceroEntidadLista(ter.getIntcodigo(), itemTercero.getValue().toString());
                temp.add(terce);
                if (i == 0) {
                    contrato.getTercero().setIntcodigo(ter.getIntcodigo());
                    contrato.getTercero().setStrnombrecompleto(ter.getStrnombrecompleto());
                }

                TerceroOption[i++] = itemTercero;
            }
        } catch (Exception e) {
        }

    }

    /**
     * Dependiendo si es contrato o convenio se seta la variable y se inicializa
     * el paginador para el llenado de los contratos por entidades
     */
    public void comboEntidadesContrato() {
        if (tipoContCon.compareTo("Convenio") == 0) {
            filtrocontrato.setRaiz(true);
        } else {
            filtrocontrato.setRaiz(false);
        }

        if (comboEntidadesContratoguardar()) {

            primeroDetcontrato();
            primeroDetcontratoContratista();
        } else {

            getContrato().getTercero().setIntcodigo(0);
            listacontratos = new ArrayList<Contrato>();
            listacontratoscontratista = new ArrayList<Contrato>();
        }
    }

    /**
     * Permite guardar el id de la entidad y no el nombre
     *
     * @return id entidad si la tiene
     */
    public boolean comboEntidadesContratoguardar() {
        if (getContrato().getTercero().getStrnombrecompleto() != null) {
            Iterator ite = temp.iterator();
            int idtercero = 0;
            while (ite.hasNext()) {
                TerceroEntidadLista tempter = (TerceroEntidadLista) ite.next();

                if (getContrato().getTercero().getStrnombrecompleto().equals(tempter.getStrnombre())) {
                    idtercero = tempter.getCodigo();

                }
            }
            if (idtercero != 0) {
                getContrato().getTercero().setIntcodigo(idtercero);
                return true;
            }
        }
        return false;
    }

    /**
     * Listar las formas de pago (ANTICIPO Y ACTAS PARCIALES,ACTAS
     * PARCIALES,ACTA UNICA)
     */
    public void llenarFormaPago() {

        List<Formapago> listaformapago = getSessionBeanCobra().getCobraService().encontrarFormaPagoPorEstado();
        formaPago = new SelectItem[listaformapago.size() + 1];
        int i = 1;
        formaPago[0] = new SelectItem(0, bundle.getString("seleccioneuntipo"));
        for (Formapago forma : listaformapago) {
            formaPago[i++] = new SelectItem(forma.getIntidformapago(), forma.getStrdescformapago());
        }
    }

    /**
     * Si el contrato es de consultoria listar los tipos
     */
    public void llenarTipoContratoconsultoria() {
        List<Tipocontratoconsultoria> liscontrconsul = getSessionBeanCobra().getCobraService().encontrarTipocontratoconsultoria();
        tipocontratoconsultoria = new SelectItem[liscontrconsul.size()];
        int i = 0;
        for (Tipocontratoconsultoria tconcon : liscontrconsul) {
            SelectItem opt = new SelectItem(tconcon.getIntidtipocontratoconsultoria(), tconcon.getStrdescripcion());
            tipocontratoconsultoria[i++] = opt;
        }

    }

    /**
     * Llenar los tipos de contrato (Obra,Bienes y Servicios)
     */
    public void llenarTipoContrato() {
        List<Tipocontrato> listipcon = getSessionBeanCobra().getCobraService().encontrarTiposContrato();
        tipocontrato = new SelectItem[listipcon.size()];
        int i = 0;
        for (Tipocontrato tconcon : listipcon) {
            SelectItem opt = new SelectItem(tconcon.getInttipocontrato(), tconcon.getStrdesctipocontrato());
            tipocontrato[i++] = opt;
        }
    }

    /**
     * Listar los tipos de pólizas
     */
    public void llenarTiposPoliza() {
        List<Tipopoliza> lista = getSessionBeanCobra().getCobraService().encontrarTiposPoliza();
        TipoPolizas = new SelectItem[lista.size()];
        int i = 0;
        for (Tipopoliza tipo : lista) {
            SelectItem tpo = new SelectItem(tipo.getInttipopoliza(), tipo.getStrdesctipopoliza());
            if (i == 0) {
                tipointpoli = tipo.getInttipopoliza();
            }
            TipoPolizas[i++] = tpo;
        }
    }

    /**
     * Validación general de toda la forma de pago
     *
     * @return true si esta correctamente y false sino esta correctamente
     * diligenciado
     */
    public boolean validarDiligenciamientoFormadePago() {

        if (contrato.getFormapago().getIntidformapago() != 0) {
            switch (contrato.getFormapago().getIntidformapago()) {
                case 1://Anticipo y Actas parciales
                    int i = 0;
                    BigDecimal total = BigDecimal.ZERO;
                    if (bundle.getString("fechaformapago").equals("false")) {
                        fechapagoanticipo = new Date();
                    }

                    if (fechapagoanticipo != null) {

                        if (valorpagoanticipo == null && valorpagoanticipo.compareTo(BigDecimal.ONE) <= 0) {
                            FacesUtils.addErrorMessage("Debe establecer un valor para el pago del anticipo.");
                            return false;
                        } else {
                            if (lisplanifiactapar.size() > 0) {
                                Planificacionpago pn = new Planificacionpago();
                                pn.setNumvlrpago(valorpagoanticipo);
                                pn.setNumvlrporcentage(porcentapagoanticipo);
                                pn.setDatefechapago(fechapagoanticipo);
                                lisplanifiactapar.add(pn);

                                while (i < lisplanifiactapar.size()) {
                                    if (lisplanifiactapar.get(i).getDatefechapago() == null) {
                                        if (bundle.getString("fechaformapago").equals("false")) {
                                            lisplanifiactapar.get(i).setDatefechapago(new Date());
                                        } else {
                                            lisplanifiactapar.remove(lisplanifiactapar.size() - 1);
                                            FacesUtils.addErrorMessage("Debe establecer una fecha para el pago del acta parcial.");
                                            return false;
                                        }
                                    }
                                    if (lisplanifiactapar.get(i).getNumvlrporcentage() == null || lisplanifiactapar.get(i).getNumvlrporcentage().compareTo(BigDecimal.ONE) < 0) {
                                        lisplanifiactapar.remove(lisplanifiactapar.size() - 1);
                                        FacesUtils.addErrorMessage("Debe establecer un porcentaje para el pago del acta parcial.");
                                        return false;
                                    } else {
                                        BigDecimal cienporciento = new BigDecimal(100);
                                        lisplanifiactapar.get(i).setNumvlrpago(contrato.getNumvlrcontrato().multiply(lisplanifiactapar.get(i).getNumvlrporcentage().divide(cienporciento)));

                                        total = total.add(lisplanifiactapar.get(i).getNumvlrporcentage());
                                    }
//
//                            if (lisplanifiactapar.get(i).getNumvlrpago() == null && lisplanifiactapar.get(i).getNumvlrpago().compareTo(BigDecimal.ONE) <= 0) {
//                                FacesUtils.addErrorMessage("Debe establecer un valor para el pago del acta parcial.");
//                                return false;
//                            } else {
//                                total = total.add(lisplanifiactapar.get(i).getNumvlrpago());
//                            }

                                    lisplanifiactapar.get(i).setStrdescripcion("");
                                    lisplanifiactapar.get(i).setBoolactivo(true);
                                    lisplanifiactapar.get(i).setBoolrealizado(false);
                                    lisplanifiactapar.get(i).setDateusucreacion(new Date());
                                    lisplanifiactapar.get(i).setIntusucreacion(getSessionBeanCobra().getUsuarioObra().getUsuId());
                                    lisplanifiactapar.get(i).setContrato(contrato);
                                    i++;
                                }

                                if (total.compareTo(BigDecimal.valueOf(100)) != 0) {
                                    lisplanifiactapar.remove(lisplanifiactapar.size() - 1);
                                    if (bundle.getString("fechaformapago").equals("true")) {
                                        FacesUtils.addErrorMessage("El valor de la suma de los porcentajes(" + total + "%) de las actas parciales difiere del 100%)");
                                        return false;
                                    }
                                } else {
                                    return true;
                                }

                            } else {
                                if (bundle.getString("fechaformapago").equals("true")) {
                                    FacesUtils.addErrorMessage("Debe distribuir los pagos en al menos un acta parcial.");
                                    return false;
                                }
                            }
                        }
                    } else {

                        FacesUtils.addErrorMessage("Debe establecer una fecha para el pago del anticipo.");
                        return false;

                    }

                case 2://Actas
                    i = 0;
                    total = BigDecimal.ZERO;

                    if (lisplanifiactapar.size() > 1) {

                        while (i < lisplanifiactapar.size()) {
                            if (lisplanifiactapar.get(i).getDatefechapago() == null) {
                                if (bundle.getString("fechaformapago").equals("true")) {
                                    FacesUtils.addErrorMessage("Debe establecer una fecha para el pago del acta parcial.");
                                    return false;
                                } else {
                                    lisplanifiactapar.get(i).setDatefechapago(new Date());
                                }
                            }
                            if (lisplanifiactapar.get(i).getNumvlrporcentage() == null || lisplanifiactapar.get(i).getNumvlrporcentage().compareTo(BigDecimal.ONE) < 0) {

                                FacesUtils.addErrorMessage("Debe establecer un porcentaje para el pago del acta parcial.");
                                return false;
                            } else {
                                BigDecimal cienporciento = new BigDecimal(100);
                                lisplanifiactapar.get(i).setNumvlrpago(contrato.getNumvlrcontrato().multiply(lisplanifiactapar.get(i).getNumvlrporcentage().divide(cienporciento)));

                                total = total.add(lisplanifiactapar.get(i).getNumvlrporcentage());
                            }
//
//                            if (lisplanifiactapar.get(i).getNumvlrpago() == null && lisplanifiactapar.get(i).getNumvlrpago().compareTo(BigDecimal.ONE) <= 0) {
//                                FacesUtils.addErrorMessage("Debe establecer un valor para el pago del acta parcial.");
//                                return false;
//                            } else {
//                                total = total.add(lisplanifiactapar.get(i).getNumvlrpago());
//                            }

                            lisplanifiactapar.get(i).setStrdescripcion("");
                            lisplanifiactapar.get(i).setBoolactivo(true);
                            lisplanifiactapar.get(i).setBoolrealizado(false);
                            lisplanifiactapar.get(i).setDateusucreacion(new Date());
                            lisplanifiactapar.get(i).setIntusucreacion(getSessionBeanCobra().getUsuarioObra().getUsuId());
                            lisplanifiactapar.get(i).setContrato(contrato);
                            i++;
                        }
                        if (total.compareTo(BigDecimal.valueOf(100)) != 0) {
                            FacesUtils.addErrorMessage("El valor de la suma de los porcentajes(" + total + "%) de las actas parciales difiere del 100%)");
                            return false;
                        } else {
                            return true;
                        }

                    } else {

                        FacesUtils.addErrorMessage("Debe distribuir los pagos en al menos dos actas parciales.");
                        return false;

                    }

                case 3://Acta unica
                    if (bundle.getString("fechaformapago").equals("true")) {
                        if (planificacionpago.getDatefechapago() != null) {
                            planificacionpago.setNumvlrpago(contrato.getNumvlrcontrato());
                            planificacionpago.setStrdescripcion("");
                            planificacionpago.setBoolactivo(true);
                            planificacionpago.setBoolrealizado(false);
                            planificacionpago.setDateusucreacion(new Date());
                            planificacionpago.setIntusucreacion(getSessionBeanCobra().getUsuarioObra().getUsuId());
                            planificacionpago.setContrato(contrato);
                            //setplani.add(planificacionpago);
                            planificacionpago.setNumvlrporcentage(new BigDecimal(100));
                            contrato.setPlanificacionpagos(new LinkedHashSet());
                            contrato.getPlanificacionpagos().add(planificacionpago);

                            return true;
                        } else {
                            if (bundle.getString("fechaformapago").equals("true")) {
                                FacesUtils.addErrorMessage("Debe establecer una fecha para el pago del acta única.");
                                return false;
                            }
                        }
                    } else {
                        planificacionpago.setNumvlrpago(contrato.getNumvlrcontrato());
                        planificacionpago.setStrdescripcion("");
                        planificacionpago.setBoolactivo(true);
                        planificacionpago.setBoolrealizado(false);
                        planificacionpago.setDateusucreacion(new Date());
                        planificacionpago.setIntusucreacion(getSessionBeanCobra().getUsuarioObra().getUsuId());
                        planificacionpago.setContrato(contrato);
                        planificacionpago.setDatefechapago(contrato.getDatefechaini());
                        //setplani.add(planificacionpago);
                        planificacionpago.setNumvlrporcentage(new BigDecimal(100));
                        contrato.setPlanificacionpagos(new LinkedHashSet());
                        contrato.getPlanificacionpagos().add(planificacionpago);
                        return true;
                    }
            }
        } else {
            FacesUtils.addErrorMessage("Debe diligenciar la forma de pago.");
            return false;
        }

        return false;
    }
    /*
     * Guardar contrato, teniendo en cuenta validaciones para el guardado de este objeto
     */

    public String guardarContrato() {
//        if (validarContrato()) {        
        if (comboEntidadesContratoguardar()) {
            if (contrato.getIntduraciondias() > 0) {
                if (contrato.getContratista() == null) {
                    FacesUtils.addErrorMessage("Debe seleccionar ó crear un contratista.");
                    return null;
                }
                if (contrato.getNumvlrcontrato().compareTo(BigDecimal.ZERO) <= 0) {
                    FacesUtils.addErrorMessage("Debe distribuir los recursos económicos del contrato adecuadamente.");
                    return null;
                }
                if (preguntacontrato == 1 || preguntacontrato == 2) {//Contrato convenio Hijo
                    if (contrpadre != null) {
                        if (validarFechaPadre()) {
                            if (validarVlrContratoPadre()) {
                                contrpadre.setNumvlrsumahijos(contrpadre.getNumvlrsumahijos().add(contrato.getNumvlrcontrato()));
                                contrato.setContrato(contrpadre);
                            } else {
                                FacesUtils.addErrorMessage("El valor del " + tipoContCon + " supera el valor del " + tipoContCon + " superior");
                                return null;
                            }
                        } else {
                            FacesUtils.addErrorMessage("las fechas del " + tipoContCon + " a crear deben estar dentro del rango del " + tipoContCon + " superior");
                            return null;
                        }
                    } else {
                        FacesUtils.addErrorMessage("Debe seleccionar el contrato o convenio padre al que pertenece el contrato a guardar.");
                        return null;
                    }
                }
                //getSessionBeanCobra().getCobraService().guardarContrato(contrato);
                if (validarDiligenciamientoFormadePago()) {
                    contrato.setDatefechacreacion(new Date());
                    contrato.setDatefechamodificacion(new Date());
                    contrato.setDatefechaultimaprorroga(null);
                    contrato.setJsfUsuarioByIntusumodificacion(getSessionBeanCobra().getUsuarioObra());
                    contrato.setJsfUsuarioByIntusucreacion(getSessionBeanCobra().getUsuarioObra());
                    contrato.setTipoestadobra(new Tipoestadobra(1));
                    if (!boolcontrconsultoria) {
                        contrato.setTipocontratoconsultoria(new Tipocontratoconsultoria(1));
                    }
                    if (booltipocontratoconvenio) {
                        contrato.setBooleantienehijos(true);
                    }
                    contrato.setBooltipocontratoconvenio(booltipocontratoconvenio);
                    contrato.setNumvlrsumahijos(new BigDecimal(BigInteger.ZERO));
                    contrato.setPolizacontratos(new LinkedHashSet(listapolizas));
                    contrato.setIntcantproyectos(0);
                    //guarda el contrato siente en ejecucion
                    contrato.setEstadoconvenio(new Estadoconvenio(2));
                    if (lisplanifiactapar.size() > 0) {//Actas Parciales
                        contrato.setPlanificacionpagos(new LinkedHashSet(lisplanifiactapar));
                    }
                    if (getSessionBeanCobra().getBundle().getString("aplicaContralorias").equals("false")) {
                        contrato.setModalidadcontratista(null);
                    }
                    //contrato.setContrato(null);
//                        if (contrato.getEncargofiduciario().getIntnumencargofiduciario() == 0) {
//                            contrato.setEncargofiduciario(null);
//                        }

//                         if (tipoContCon.equals("Contrato")) {
//                             setBooltipocontratoconvenio(false);
//                         }else
//                         {
//                            setBooltipocontratoconvenio(true);
//                         }
                    /*se verifica el tipo de proyecto si es contraloria debe realizar la validacion de los documentos*/
                    String contra = bundle.getString("aplicaContralorias");
                    if (contra.equals("true")) {
                        boolean valdocumento = validarDocumentosContralorias();
                        if (valdocumento == false) {
                            FacesUtils.addErrorMessage("Debe diligenciar los tres documentos obligatorios que son: 1. Contrato, 2. Certificado de Disponibilidad Presupuestal (CDP), 3. Registro Presupuestal (RP)");
                        } else {
                            validadcionGuardarContrato();
                        }
                    } else {
                        validadcionGuardarContrato();
                    }
                    FacesUtils.addInfoMessage(bundle.getString("losdatossehanguardado"));

                    limpiarContrato();
                }
            } else {
                FacesUtils.addErrorMessage("La Fecha de Fin Debe ser mayor o igual a la fecha de inicio");
            }
        } else {
            FacesUtils.addErrorMessage("Debe diligenciar una entidad contratante válida.");
        }

        return null;
    }

    /**
     * validadcionGuardarContrato metodo que ejecuta la ultima parte de guardar
     * nuevo contrato, se separo del metodo inicial para no duplicar las lineas
     * de codigo con la nueva logica que se necesita para validar los documetos
     * cuando es una contraloria
     *
     * @return no retorna nada
     */
    private void validadcionGuardarContrato() {
        if (bundle.getString("boolencargofidu").equals("true")) {
            if (contrato.getEncargofiduciario().getIntnumencargofiduciario() == 0) {
                contrato.setEncargofiduciario(null);
            }
        }
        getSessionBeanCobra().getCobraService().guardarContrato(contrato);
        for (Documentoobra docContrato : listadocumentos) {
            try {
                String nuevaRutaWeb = ArchivoWebUtil.copiarArchivo(
                        docContrato.getStrubicacion(),
                        MessageFormat.format(RutasWebArchivos.DOCS_CONTRATO, "" + getContrato().getIntidcontrato()),
                        true, false);
                docContrato.setStrubicacion(nuevaRutaWeb);
                getSessionBeanCobra().getCobraService().guardarDocumento(docContrato);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(NuevoContratoBasico.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ArchivoExistenteException ex) {
                Logger.getLogger(NuevoContratoBasico.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        FacesUtils.addInfoMessage(bundle.getString("losdatossehanguardado"));

        limpiarContrato();
    }

    /**
     * validarDocumentosContralorias metodo que validad en los proyectos de tipo
     * contraloria los tres documentos obligatorios al momento de crear un
     * contrato, 1. Contrato 2. Certificado de Disponibilidad Presupuestal (CDP)
     * 3. Registro Presupuestal (RP)
     *
     * @return true si cumple con los tres documentos
     */
    private boolean validarDocumentosContralorias() {
        boolean tipos = false;
        int count = 0;
        if (listadocumentos.size() > 0) {
            for (int i = 0; i < listadocumentos.size(); i++) {
                if (listadocumentos.get(i).getTipodocumento().getInttipodoc() == 2) {
                    count++;
                } else if (listadocumentos.get(i).getTipodocumento().getInttipodoc() == 3) {
                    count++;
                } else if (listadocumentos.get(i).getTipodocumento().getInttipodoc() == 30) {
                    count++;
                }
            }
        }
        if (count >= 3) {
            tipos = true;
        }
        return tipos;
    }

//    public boolean validarContrato() {
//        if (contrato != null) {
//            if (contrato.getStrnumcontrato() != null && contrato.getStrnumcontrato().compareTo("") != 0 && contrato.getStrnombre() != null
//                    && contrato.getStrnombre().compareTo("") != 0 && contrato.getDatefechaini() != null && contrato.getDatefechafin() != null
//                    && contrato.getNumvlrcontrato() != null && contrato.getTercero() != null && contrato.getTextobjeto() != null
//                    && contrato.getTextobjeto().compareTo("") != 0 && contrato.getContratista() != null
//                    && contrato.getFormapago().getIntidformapago() != 0) {
//                return true;
//            }
//        }
//        return false;
//    }
    /*
     * LLenar la lista siempre y cuando se alla insertado 1 o varios documentos
     */
    public boolean validarDocumento() {
        if (listadocumentos.size() > 0) {
            for (int i = 0; i < listadocumentos.size(); i++) {

                if (listadocumentos.get(i).getTipodocumento().getInttipodoc() == 2) {
                    return true;

                }

            }
        }
        return false;
    }

    /**
     * Inicialización de las fechas
     *
     * @return
     */
    public String feciniCambio() {
//        if (seleccionopadre == 1) {//es un contrato hijo se valida fipadre hijo contra fipadre
//            if (contrato.getDatefechaini().after(contrpadre.getDatefechaini()) || contrato.getDatefechaini().equals(contrpadre.getDatefechaini())) {
        //Calendar hoy = Calendar.getInstance();
        if (contrato.getDatefechaini() != null) {

            if (contrato.getDatefechafin() != null) {
                fechaCambio();
            } else {
                contrato.setIntduraciondias(0);
            }
        } else {
            contrato.setIntduraciondias(0);
            contrato.setDatefechafin(null);
        }

//                if (booltipocontratoconvenio == true) {
//                    FacesUtils.addErrorMessage("La Fecha de Inicio del Contrato Actual es Inferior a la Fecha Inicial de su contrato Padre");
//                } else {
//                    FacesUtils.addErrorMessage("La Fecha de Inicio del Convenio Actual es Inferior a la Fecha Inicial de su convenio Padre");
//                }
//            }
//        } else {
//            Calendar hoy = Calendar.getInstance();
//            fechaCambio();
//            if (contrato.getDatefechaini() != null) {
//                hoy.setTime(contrato.getDatefechaini());
//                hoy.add(Calendar.DATE, +contrato.getIntduraciondias());
//                contrato.setDatefechafin(hoy.getTime());
//            }
//        }
        return null;

    }

    /**
     * Calculo de las fechas en días
     *
     * @return
     */
    public String fechaCambio() {

        //     if (seleccionopadre == 1) {//es un proyecto hijo toes valida fiobra hijo contra fipadre
        // if (contrato.getDatefechafin().before(contrpadre.getDatefechafin()) || contrato.getDatefechafin().equals(contrpadre.getDatefechafin())) {
        Calendar hoy = Calendar.getInstance();
        if (contrato.getDatefechaini() != null && contrato.getDatefechafin() != null) {
            if (contrato.getDatefechafin().compareTo(contrato.getDatefechaini()) >= 0) {
                long diferenciaFecha = contrato.getDatefechafin().getTime() - contrato.getDatefechaini().getTime();
                diferenciaFecha = diferenciaFecha / (36000 * 24 * 100) + 1;
                contrato.setIntduraciondias(Integer.parseInt(String.valueOf(diferenciaFecha)));
                //int plazo = Integer.parseInt(String.valueOf(diferenciaFecha));
            } else {
                contrato.setDatefechafin(null);
                contrato.setIntduraciondias(0);
                //mensaje = bundle.getString("lafechadefinalizaciondebeser");//"La fecha de finalización debe ser mayor o igual a la fecha de inicio";
                FacesUtils.addErrorMessage(bundle.getString("lafechadefinalizaciondebeser"));
            }
        }

//            } else {
//                if (booltipocontratoconvenio == true) {
//                    FacesUtils.addErrorMessage("La Fecha Final del Contrato Actual es Superior a la Fecha de su Contrato Padre");
//                } else {
//                    FacesUtils.addErrorMessage("La Fecha Final del Convenio Actual es Superior a la Fecha de su Convenio Padre");
//                }
//            }
//        } else {
//            Calendar hoy = Calendar.getInstance();
//            if (contrato.getDatefechaini() != null && contrato.getDatefechafin() != null) {
//                if (contrato.getDatefechafin().compareTo(contrato.getDatefechaini()) >= 0) {
//                    long diferenciaFecha = contrato.getDatefechafin().getTime() - contrato.getDatefechaini().getTime();
//                    diferenciaFecha = diferenciaFecha / (36000 * 24 * 100) + 1;
//                    contrato.setIntduraciondias(Integer.parseInt(String.valueOf(diferenciaFecha)));
//                //int plazo = Integer.parseInt(String.valueOf(diferenciaFecha));
//                } else {
//                    contrato.setDatefechafin(null);
//                    contrato.setIntduraciondias(0);
//                    mensaje = bundle.getString("lafechadefinalizaciondebeser");//"La fecha de finalización debe ser mayor o igual a la fecha de inicio";
//                    FacesUtils.addErrorMessage(mensaje);
//                }
//            }
//       }
        return null;
    }

    public String fechaCambioContrato() {
        Calendar hoy = Calendar.getInstance();
        if (contrato.getDatefechaini() != null && contrato.getDatefechafin() != null) {
            if (contrato.getDatefechafin().compareTo(contrato.getDatefechaini()) >= 0) {
                long diferenciaFecha = contrato.getDatefechafin().getTime() - contrato.getDatefechaini().getTime();
                diferenciaFecha = diferenciaFecha / (36000 * 24 * 100) + 1;
                contrato.setIntduraciondias(Integer.parseInt(String.valueOf(diferenciaFecha)));
                int plazo = Integer.parseInt(String.valueOf(diferenciaFecha));
                //llenarPeriodo(plazo);
            } else {
                contrato.setDatefechafin(null);
                contrato.setIntduraciondias(0);
                //mensaje = bundle.getString("lafechadefinalizaciondebeser");//"La fecha de finalización debe ser mayor o igual a la fecha de inicio";
                FacesUtils.addErrorMessage(bundle.getString("lafechadefinalizaciondebeser"));
            }
        }

        return null;
    }

//    public void contratoConvenio(ActionEvent event) {//se invoca desde menu_lateral_gestion
//        mostrarpregcontrcon = 0;
////        booltipocontratoconvenio=false;
//        booltipocontratoconvenio = (Boolean) event.getComponent().getAttributes().get("isContrato");
//        //   mostrartipocon=1;
//        if (booltipocontratoconvenio == true) {//true =contrato
//            tipoContCon = "Contrato";
//            mensaje = bundle.getString("incluyecontratos");
//            mensajeout = bundle.getString("incluyecontratosuperior");
//            mensajeseleccion = bundle.getString("contratosuperior");
//            limpiarContrato();
//        } else {//convenio
//            tipoContCon = "Convenio";
//            mensaje = bundle.getString("incluyeconvenios");
//            mensajeout = bundle.getString("incluyeconveniosuperior");
//            mensajeseleccion = bundle.getString("conveniosuperior");
//        }
//    }
    /**
     * Cuando se inicia desde el botón lateral Nuevo convenio, se inicializan
     * algunas variables para darle el comportamiento de convenio
     *
     * @param event
     */
    public void iniciarConvenio(ActionEvent event) {//se invoca desde menu_lateral_gestion
//       Iniciar los metodos para llenar la tabla de flujo caja si este tiene plan operativo
        booltipocontratoconvenio = true;
        tipoContCon = "Convenio";
        boolcontrconsultoria = false;
        getSessionBeanCobra().getCobraService().setAsoContratoCrear(false);
        limpiarContrato();
//        mensaje = bundle.getString("incluyeconvenios");
//        mensajeout = bundle.getString("incluyeconveniosuperior");
//        mensajeseleccion = bundle.getString("conveniosuperior");
    }

    /**
     * Cuando se inicia desde el botón lateral Nuevo contrato, se inicializan
     * algunas variables para darle el comportamiento de contrato
     *
     * @param event
     */
    public void iniciarContrato(ActionEvent event) {//se invoca desde menu_lateral_gestion
        booltipocontratoconvenio = false;
        tipoContCon = "Contrato";
        boolcontrconsultoria = false;
        getSessionBeanCobra().getCobraService().setAsoContratoCrear(false);
        limpiarContrato();

//        mensaje = bundle.getString("incluyecontratos");
//        mensajeout = bundle.getString("incluyecontratosuperior");
//        mensajeseleccion = bundle.getString("contratosuperior");
    }

    /**
     * Instancia del bean IngresarNuevaObra
     *
     * @return
     */
    protected IngresarNuevaObra getIngresarNuevaObra() {
        return (IngresarNuevaObra) FacesUtils.getManagedBean("Supervisor$IngresarNuevaObra");
    }

    /**
     * Instancia del bean SessionBeanCobra
     *
     * @return
     */
    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }

    protected FlujoCaja getFlujoCaja() {
        return (FlujoCaja) FacesUtils.getManagedBean("PlanOperativo");
    }

    /**
     * Calcula el valor total del contrato, sumandole los recursos propios, los
     * recursos de Colombia Humanitaria y los recursos del tercero
     */
    public void calcularValor() {
        BigDecimal numvlrcontrato = new BigDecimal(BigInteger.ZERO);
        numvlrcontrato = numvlrcontrato.add(contrato.getNumrecursospropios()).add(contrato.getNumrecursosch()).add(contrato.getNumrecursostercero());
        contrato.setNumvlrcontrato(numvlrcontrato);
        lisplanifiactapar = new ArrayList<Planificacionpago>();
        contrato.setFormapago(new Formapago(0, null, true));
    }

    /**
     * Inicialización de variables y llenado de combos utilizados para subir una
     * poliza
     */
    public void agregarPolizasContra() {
        mostrarAgregarPol = 1;
        polizacontrato.setTipopoliza(new Tipopoliza());
        polizacontrato.setAseguradora(new Aseguradora());
        llenarTiposPoliza();
        llenarCompanias();
    }

    /**
     * Metodo para guardar las poliza nueva en el detalle del contrato
     *
     */
    public String insertarPoliza() {

        if (polizacontrato.getStrnumpoliza() != null && polizacontrato.getStrnumpoliza().compareTo("") != 0 && polizacontrato.getDatefechavecimiento() != null) {
            polizacontrato.setContrato(contrato);
            polizacontrato.setStrdocpoliza("");
            polizacontrato.setStrdocpoliza("");
            System.out.println("tipointpoli = " + tipointpoli);
            polizacontrato.setStrdocpoliza("");
            polizacontrato.setPolizacontratos(new LinkedHashSet(listapolizas));
            listapolizas.add(polizacontrato);
            getSessionBeanCobra().getCobraService().guardarNuevaPoliza(polizacontrato);
            boolpolizas = true;
            polizacontrato = new Polizacontrato();
            polizacontrato.setTipopoliza(new Tipopoliza());
            polizacontrato.setAseguradora(new Aseguradora());
            polizacontrato.setContrato(new Contrato());
            llenarPolizas();
            instanciarPolizar();

        } else {
            FacesUtils.addErrorMessage("Debe diligenciar los datos requeridos para la póliza.");

        }
        return null;
    }

    public String boolPoliza() {
        if (boolcrearpoliza == false) {
            System.out.println("entro aqui " + boolcrearpoliza);
            insertarPoliza();
            //instanciarPolizar();
//            polizacontrato = new Polizacontrato();
//            polizacontrato.setTipopoliza(new Tipopoliza());
//            polizacontrato.setAseguradora(new Aseguradora());
//            polizacontrato.setContrato(new Contrato());

        }

        return null;

    }

    /**
     * Método que se utiliza para agregar una poliza a una lista, valida que se
     * hayan subido los datos requeridos e inicializa variables despues de
     * guardar
     *
     * @return No devuelve ningún valor
     */
    public String agregarPoliza() {
        if (polizacontrato.getStrnumpoliza() != null && polizacontrato.getStrnumpoliza().compareTo("") != 0 && polizacontrato.getDatefechavecimiento() != null) {
            polizacontrato.setAseguradora(getSessionBeanCobra().getCobraService().encontrarAseguradoraPorId(polizacontrato.getAseguradora().getIntnumnitentidad()));
            polizacontrato.setTipopoliza(getSessionBeanCobra().getCobraService().encontrarTipoPolizaPorId(tipointpoli));
            polizacontrato.setContrato(contrato);
            polizacontrato.setStrdocpoliza("");
            listapolizas.add(polizacontrato);
            listaPolizacontratos.add(polizacontrato);//Se guarda en la lista desde modificar contrato
            polizacontrato = new Polizacontrato();
            polizacontrato.setTipopoliza(new Tipopoliza());
            polizacontrato.setAseguradora(new Aseguradora());
            polizacontrato.setContrato(new Contrato());
        } else {
            FacesUtils.addErrorMessage("Debe diligenciar los datos requeridos para la póliza.");
        }

        return null;
    }

    /**
     * Elimina la poliza seleccionada de la lista.
     *
     * @param filaSeleccionada Corresponde a la fila de la que proviene la
     * acción en la tabla
     * @return
     */
    public String eliminarPoliza(int filaSeleccionada) {

        Polizacontrato poleli = listapolizas.get(filaSeleccionada);
        listapolizas.remove(poleli);
        //getSessionBeanCobra().getCobraService().borrarPolizaContrato(poleli);
        return null;
    }

    /**
     * Elimina la poliza seleccionada de la lista en el detalle del contrato.
     *
     * @param 
     * 
     * @return
     */
    public String eliminarPolizaNueva() {
        Polizacontrato polizacontratoEliminada = (Polizacontrato) tablaPolizasbin.getRowData();

        listapolizas.remove(polizacontratoEliminada);

        getSessionBeanCobra().getCobraService().borrarPolizaContrato(polizacontratoEliminada);

        llenarPolizas();

        return null;
    }

    /**
     * Llena el combo con las compañias aseguradoras de las polizas
     */
    public void llenarCompanias() {
        List<Aseguradora> lista = getSessionBeanCobra().getCobraService().encontrarCompaniasSeguro();

        Companias = new SelectItem[lista.size()];
        int i = 0;
        for (Aseguradora asegura : lista) {
            SelectItem tpo = new SelectItem(asegura.getIntnumnitentidad(), asegura.getStrnombreentidad());
            if (i == 0) {
                polizacontrato.getAseguradora().setIntnumnitentidad(asegura.getIntnumnitentidad());
            }
            Companias[i++] = tpo;
        }
    }

    /**
     * Filtra los contratos por palabra clave.
     *
     * @param actual: Recibe el parámetro de busqueda
     * @return Retorna true si se encontro un resultado o false si no.
     */
    public boolean filtrarContratos(Object actual) {
        Contrato cont = (Contrato) actual;
        if (this.valorFiltroContratos.length() == 0) {
            return true;
        }

        if (cont.getStrnombre().toLowerCase().contains(this.valorFiltroContratos.toLowerCase())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Inicializa las variables que determinan si es un convenio o un contrato
     * padre. Hace un llamado al método donde se carga la lista.
     */
    public void llenarContratosPadres() {
        listacontratos.clear();
        listacontratoscontratista.clear();

        if (preguntacontrato == 1) {//Contrato=false,convenio=true

            filtrocontrato.setBooltipocontconv(false);
            filtrocontrato.setBooltienehijo(true);
            aplicafiltrocontrato = false;

        }
        if (preguntacontrato == 2) {

            filtrocontrato.setBooltipocontconv(true);
            filtrocontrato.setBooltienehijo(true);
            aplicafiltrocontrato = false;
            //tipoContCon = "Convenio";
        }
        filtrocontrato.setPalabraClave(null);
        filtrocontrato.setBoolcontrconsultoria(false);
        filtrocontrato.setTipocontratointer(1);
        primeroDetcontrato();
        primeroDetcontratoContratista();
    }

    /**
     * Llena el combo de eventos.
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
     * Llena el combo de periodos que esten relacionados con cierto evento.
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
     * Acceder a los datos de la tabla de contratos padres
     *
     * @return No retorna ningun valor
     */
    public String contratoPadreSelec(int filaSeleccionada) {
        varmostrarcontrpa = 1;
        contrpadre = listacontratos.get(filaSeleccionada);
        return null;

    }

    /**
     * Acceder a los datos de la tabla de contratos padres por contratista
     *
     * @return No retorna ningun valor
     */
    public String contratoPadreSelecContratista(int filaSeleccionada) {
        varmostrarcontrpa = 1;
        contrpadre = listacontratoscontratista.get(filaSeleccionada);
        return null;
    }
    /*
     * Inicializa variables, objetos y listas
     */

    public void limpiarContrato() {
        boolnumencargosoli = false;
        if (getSessionBeanCobra().getCobraService().isAsoContratoCrear()) {
            booltipocontratoconvenio = false;
            tipoContCon = "Contrato";
            boolcontrconsultoria = false;
            boolnumencargosoli = true;

        }
        buscarproyecto = "";
        contrato = new Contrato();
        contrato.setTercero(new Tercero());
        contrato.setEncargofiduciario(new Encargofiduciario());
        contrato.getEncargofiduciario().setIntnumencargofiduciario(0);
        contrato.setNumrecursospropios(BigDecimal.ZERO);
        contrato.setNumrecursosch(BigDecimal.ZERO);
        contrato.setNumrecursostercero(BigDecimal.ZERO);
        contrato.setNumvlrcontrato(BigDecimal.ZERO);
        contrato.setFormapago(new Formapago());
        contrato.setEstadoconvenio(new Estadoconvenio(1));
        contrato.setBooleantienehijos(false);
        polizacontrato = new Polizacontrato();
        documentoobra = new Documentoobra();
        encargofiduciario = new Encargofiduciario();
        planificacionpago = new Planificacionpago();
        contrato.setPeriodoevento(new Periodoevento());
        contrato.getPeriodoevento().setEvento(new Evento());
        contrato.setModalidadcontratista(new Modalidadcontratista());
        contrato.setTipoestadobra(new Tipoestadobra(1));//
        contrato.setTipocontratoconsultoria(new Tipocontratoconsultoria());
        contrato.setTipocontrato(new Tipocontrato(1, "Obra", true));
        contrato.setNumvlrsumaproyectos(BigDecimal.ZERO);

        // contrato.setTercero(new Tercero());

        lisplanifiactapar.clear();
        listaContratosPadre.clear();
        listapolizas.clear();
        listaPolizacontratos.clear();
        listaProyectosCovenio = new ArrayList<Obra>();

        lisplanifiactapar.clear();
        listaEncargofiduciario.clear();
        listadocumentos.clear();

        numdeactasparciales = 0;
        porcentapagoanticipo = new BigDecimal(BigInteger.ZERO);
        valorpagoanticipo = new BigDecimal(BigInteger.ZERO);
        setFechapagoanticipo(null);
        varmostrarcontrpa = 0;
        contrpadre = null;
        mostrarAgregarPol = 0;
        preguntacontrato = 0;

        ServletContext theApplicationsServletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        realArchivoPath = theApplicationsServletContext.getRealPath(URL);
        subirDocPol = new CargadorArchivosWeb();

        llenarEntidadesContratantes();
        instanciarPolizar();
        listaModificarContrato.clear();
        listaContrConvHijo.clear();
        setRecursosconvenio(new RecursosConvenio(getContrato(), getSessionBeanCobra().getCobraService()));
        actualizarPanel(1);

    }

    /**
     * Elimina el contrato padre seleccionado.
     *
     * @param filaSeleccionada Corresponde a la fila de la que proviene la
     * acción en la tabla
     */
    public void eliminarItemPadreSele(int filaSeleccionada) {
        //contrpadre = (Contrato) tablacontrapadrebindin.getRowData();
        contrpadre = null;
        varmostrarcontrpa = 0;
        //preguntacontrato = 0;

    }

    /**
     * Se utiliza para hallar que disponibilidad tiene el contrato
     *
     * @param vlrcomparar: Valor con que se va a comparar la disponibilidad
     * @param minuendo: Valor que se va a restar
     * @param sustraendo: Valor del contrato al que se le va a restar
     * @return Retorna el resultado de la operación.
     */
    public int hallarDispo(BigDecimal vlrcomparar, BigDecimal minuendo, BigDecimal sustraendo) {
        int resul = 0;
        resul = vlrcomparar.compareTo(minuendo.subtract(sustraendo));
        return resul;
    }

//    public void validacionFormadePagoGuardar() {
//        if (contrato.getFormapago().getIntidformapago() == 3) {//3 acta unica es un unico pago por el valor del contrato el que se guarda en la tabla planificacion pago
//            planificacionpago.setNumvlrpago(contrato.getNumvlrcontrato());
//            planificacionpago.setStrdescripcion("");
//            planificacionpago.setBoolactivo(true);
//            planificacionpago.setBoolrealizado(false);
//            planificacionpago.setDateusucreacion(new Date());
//            planificacionpago.setIntusucreacion(getSessionBeanCobra().getUsuarioObra().getUsuId());
//            planificacionpago.setContrato(contrato);
//            setplani.add(planificacionpago);
//            contrato.setPlanificacionpagos(setplani);
//        } else if (contrato.getFormapago().getIntidformapago() == 2) { //2;"ACTAS PARCIALES"
//            //Si en forma de pago se selecciona actas parciales se debe preguntar cuantos y se distribuye el valor del contrato en ese numero de pagos o registros
//
//            for (Planificacionpago ppactpar : lisplanifiactapar) {
//                Planificacionpago pn = new Planificacionpago();
//                pn.setNumvlrpago(ppactpar.getNumvlrpago());
//                pn.setDatefechapago(ppactpar.getDatefechapago());
//                pn.setStrdescripcion("");
//                pn.setBoolactivo(true);
//                pn.setBoolrealizado(false);
//                pn.setDateusucreacion(new Date());
//                pn.setIntusucreacion(getSessionBeanCobra().getUsuarioObra().getUsuId());
//                pn.setContrato(contrato);
//                contrato.getPlanificacionpagos().add(pn);//ok mandar set del propio objeto
//            }
//            //  lisplanifiactapar.clear();
//        } else if (contrato.getFormapago().getIntidformapago() == 1) {//// 1;"ANTICIPO Y ACTAS PARCIALES"Si se selecciona anticipo y actas parciales se pregunta cuantas actas y se le agrega una fila siendo el primero el valor del anticipo
//
//            planificacionpago.setNumvlrpago(valorpagoanticipo);
//            planificacionpago.setDatefechapago(fechapagoanticipo);
//            setplani.add(planificacionpago);
//            //este inserta el anticipo q es manual en la lista
//            Planificacionpago ppagoanticipo = new Planificacionpago();
//            ppagoanticipo.setNumvlrpago(valorpagoanticipo);
//            ppagoanticipo.setDatefechapago(fechapagoanticipo);
//            ppagoanticipo.setStrdescripcion("");
//            ppagoanticipo.setBoolactivo(true);
//            ppagoanticipo.setBoolrealizado(false);
//            ppagoanticipo.setDateusucreacion(new Date());
//            ppagoanticipo.setIntusucreacion(getSessionBeanCobra().getUsuarioObra().getUsuId());
//            ppagoanticipo.setContrato(contrato);
//            lisplanifiactaparantici.add(ppagoanticipo);
//            for (Planificacionpago ppantiyantpar : lisplanifiactaparantici) {
//                Planificacionpago pn = new Planificacionpago();
//                pn.setNumvlrpago(ppantiyantpar.getNumvlrpago());
//                pn.setDatefechapago(ppantiyantpar.getDatefechapago());
//                pn.setStrdescripcion("");
//                pn.setBoolactivo(true);
//                pn.setBoolrealizado(false);
//                pn.setDateusucreacion(new Date());
//                pn.setIntusucreacion(getSessionBeanCobra().getUsuarioObra().getUsuId());
//                pn.setContrato(contrato);
//                contrato.getPlanificacionpagos().add(pn);//ok mandar set del propio objeto
//            }
//            //  lisplanifiactaparantici.clear();
//        }
//    }
    /**
     * Valida cual es la forma de pago que seleccionaron e inicializa variables
     */
    public void validacionFormadePago() {
        valorpagoanticipo = new BigDecimal(BigInteger.ZERO);
        porcentapagoanticipo = new BigDecimal(BigInteger.ZERO);
        seleccioncmbformadepago = 1;
        planificacionpago.setNumvlrpago(contrato.getNumvlrcontrato());
        numdeactasparciales = 0;
        lisplanifiactapar = new ArrayList<Planificacionpago>();
    }

    /**
     * Si en forma de pago se selecciona actas parciales se debe preguntar
     * cuantos y se distribuye el valor del contrato en ese numero de pagos o
     * registros
     */
    public void calcuArraPorcenPago() {
        List<BigDecimal> lsBigporcecuo = new ArrayList<BigDecimal>();

        lisplanifiactapar = new ArrayList<Planificacionpago>();
        BigDecimal numdeactasparcialesB = new BigDecimal(numdeactasparciales);
        BigDecimal cienporciento = new BigDecimal(100);

        if (contrato.getFormapago().getIntidformapago() == 1) {
            valorpagoanticipo = contrato.getNumvlrcontrato().multiply(porcentapagoanticipo.divide(cienporciento));
            BigDecimal sma = new BigDecimal(0);
            BigDecimal suma = sma.add(contrato.getNumrecursosch()).add(contrato.getNumrecursospropios()).add(contrato.getNumrecursostercero());
            cienporciento = cienporciento.subtract(porcentapagoanticipo);
        }
        if (numdeactasparciales > 0) {

            BigDecimal sumaarraB = new BigDecimal(BigInteger.ZERO);
            BigDecimal ceroB = new BigDecimal(BigInteger.ZERO);
            BigDecimal porcuoptB = new BigDecimal(BigInteger.ZERO);
            porcuoptB = cienporciento.divide(numdeactasparcialesB, 0, RoundingMode.HALF_UP);
            for (int vi = 0; vi < numdeactasparciales; vi++) {//hallo el valor del porcentaje y guardo en lista
                lsBigporcecuo.add(porcuoptB);
                sumaarraB = sumaarraB.add(porcuoptB);
            }

//            BigDecimal vlcu = new BigDecimal(BigInteger.ONE);
//            vlcu = contrato.getNumvlrcontrato().divide(new BigDecimal(numdeactasparciales), RoundingMode.HALF_EVEN);
//            for (int vxi = 0; vxi < numdeactasparciales; vxi++) {//hallo el total de valor de procentajes
//                sumaarraB = sumaarraB.add(lsBigporcecuo.get(vxi));
//
//                //if (vxi == numdeactasparciales - 1) {
//                    BigDecimal diB = new BigDecimal(BigInteger.ZERO);
//                    if (sumaarraB != cienporciento) {//si hay diferencia tonces sobra y toca sumarla algun campo de la lista
//                        diB = cienporciento.subtract(sumaarraB);//calculo difere
//                        if (diB != ceroB) {//si entra acaes por q toca meterle a algun campo de la lista la diferenc
//                            lsBigporcecuo.remove(vxi);
//                            lsBigporcecuo.add(vxi, lsBigporcecuo.get(vxi - 1).add(diB));
//                        }
//                    }
//                //}
//            }
            //se halla el valor de la cuota y se crea un objeto que sera el q se la pasa a la lista q se muestra
            for (int c = 0; c < lsBigporcecuo.size(); c++) {//hallo el valor dela cuota
                Planificacionpago plp = new Planificacionpago();
                plp.setNumvlrporcentage(lsBigporcecuo.get(c));
                plp.setNumvlrpago(contrato.getNumvlrcontrato().multiply(lsBigporcecuo.get(c).divide(cienporciento, 0, RoundingMode.HALF_UP)));
                lisplanifiactapar.add(plp);
            }

            if (sumaarraB.compareTo(cienporciento) != 0) {

                BigDecimal diferencia = BigDecimal.ZERO;
                if (sumaarraB.compareTo(cienporciento) > 1) {
                    diferencia = cienporciento.subtract(sumaarraB);

                } else {
                    diferencia = sumaarraB.subtract(cienporciento);
                }
                lisplanifiactapar.get(lsBigporcecuo.size() - 1).setNumvlrporcentage(lisplanifiactapar.get(lsBigporcecuo.size() - 1).getNumvlrporcentage().subtract(diferencia));
                if (lisplanifiactapar.get(lsBigporcecuo.size() - 1).getNumvlrporcentage().compareTo(BigDecimal.ZERO) < 0) {

                    lisplanifiactapar.get(lsBigporcecuo.size() - 1).setNumvlrporcentage(BigDecimal.ZERO);
                }

                lisplanifiactapar.get(lsBigporcecuo.size() - 1).setNumvlrpago(contrato.getNumvlrcontrato().multiply(lisplanifiactapar.get(lsBigporcecuo.size() - 1).getNumvlrporcentage().divide(cienporciento, 0, RoundingMode.HALF_UP)));

                lisplanifiactapar.get(lsBigporcecuo.size() - 1).getNumvlrporcentage();

            }

        }
    }

//    public void recalculoporcentageactaanti() {
//        List<BigDecimal> lsBigporcecuo = new ArrayList<BigDecimal>();
//
//        BigDecimal numdeactasparcialesB = new BigDecimal(numdeactasparciales);
//        BigDecimal difereB = new BigDecimal(BigInteger.ZERO);
//        BigDecimal sumaarraBr = new BigDecimal(BigInteger.ZERO);
//        BigDecimal cienporciento = new BigDecimal(100);
//        BigDecimal porcuoptBr = new BigDecimal(BigInteger.ZERO);
//        BigDecimal sumaarraBnewlis = new BigDecimal(BigInteger.ZERO);
//        BigDecimal ceroB = new BigDecimal(BigInteger.ZERO);
//        for (int i = 0; i < lisplanifiactapar.size(); i++) {
//            sumaarraBr = sumaarraBr.add(lisplanifiactapar.get(i).getNumvlrporcentage());
//        }
//        difereB = sumaarraBr.subtract(porcentapagoanticipo);
//        porcuoptBr = difereB.divide(numdeactasparcialesB, 0, RoundingMode.HALF_UP);
//        for (int vi = 0; vi < numdeactasparciales; vi++) {//hallo el valor del porcentaje y guardo en lista
//            lsBigporcecuo.add(porcuoptBr);
//        }
//        //  lsBigporcecuo.add(numdeactasparciales, porcentapagoanticipo);
//        for (int vxi = 0; vxi < numdeactasparciales; vxi++) {//hallo el total de valor de procentajes
//            sumaarraBnewlis = sumaarraBnewlis.add(lsBigporcecuo.get(vxi));
//
//            if (vxi == numdeactasparciales - 1) {
//                sumaarraBnewlis = sumaarraBnewlis.add(porcentapagoanticipo);
//                BigDecimal diB = new BigDecimal(BigInteger.ZERO);
//
//                if (sumaarraBnewlis != cienporciento) {//si hay diferencia tonces sobra y toca sumarla algun campo de la lista
//                    diB = cienporciento.subtract(sumaarraBnewlis);//calculo difere
//                    if (diB != ceroB) {//si entra acaes por q toca meterle a algun campo de la lista la diferenc
//                        lsBigporcecuo.remove(vxi);
//                        lsBigporcecuo.add(vxi, lsBigporcecuo.get(vxi - 1).add(diB));
//                    }
//                }
//            }
//        }
//        lisplanifiactapar.clear();
//        for (int c = 0; c < lsBigporcecuo.size(); c++) {//hallo el valor dela cuota
//            Planificacionpago plp = new Planificacionpago();
//            plp.setNumvlrporcentage(lsBigporcecuo.get(c));
//            plp.setNumvlrpago(contrato.getNumvlrcontrato().multiply(lsBigporcecuo.get(c).divide(cienporciento)));
//            lisplanifiactapar.add(plp);
//        }
//        valorpagoanticipo = contrato.getNumvlrcontrato().multiply(porcentapagoanticipo).divide(cienporciento);
//    }
    public void calculoActasParciales() {// 2;"ACTAS PARCIALES"Si en forma de pago se selecciona actas parciales se debe preguntar cuantos y se distribuye el valor del contrato en ese numero de pagos o registros

        calcuArraPorcenPago();

    }

//    public void calculoActasParcialesAnticipo() {// 1;"ANTICIPO Y ACTAS PARCIALES"Si se selecciona anticipo y actas parciales se pregunta cuantas actas y se le agrega una fila siendo el primero el valor del anticipo
//        BigDecimal nact = new BigDecimal(numdeactasparciales);
//        BigDecimal vlcu = new BigDecimal(BigInteger.ONE);
//        vlcu = contrato.getNumvlrcontrato().divide(nact, RoundingMode.HALF_EVEN);
//        lisplanifiactaparantici.clear();
//        try {
//            valorpagoanticipo = vlcu;
//            for (int i = 0; i < numdeactasparciales; i++) {
//                Planificacionpago ppn = new Planificacionpago();
//                ppn.setNumvlrpago(vlcu);
//                lisplanifiactaparantici.add(i, ppn);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//    public void llenarConsultaContratos() {
//        List<Tercero> lis = getSessionBeanCobra().getUsuarioService().encontrarEntidadesxtercero(getSessionBeanCobra().getUsuarioObra(), 6,true);
//        if (lis.size() > 0) {
//            for (int i = 0; i <= lis.size(); i++) {
//                listaconsultacontratos = getSessionBeanCobra().getCobraService().obtenerContratoxEntidadTodo(valorFiltroContratos, lis.get(i).getIntcodigo());
//            }
//        }
//    }
    /**
     * Subir Documentación de contrato
     *
     * @return null
     */
    public String subirDocContrato() {
        if (!getDocumentoobra().getStrnombre().equals("")) {
            if (!cargadorDocumento.getArchivos().isEmpty()) {
                try {
                    getDocumentoobra().setContrato(contrato);
                    getDocumentoobra().setTipodocumento(getSessionBeanCobra().getCobraService().encontrarTipoDocPorId(tipodoc));
                    getDocumentoobra().setDatefecha(new Date());
                    subirDocumento();
                    listadocumentos.add(getDocumentoobra());
                    setDocumentoobra(new Documentoobra());
                    setCargadorDocumento(new CargadorArchivosWeb());
                } catch (ArchivoExistenteException ex) {
                    Logger.getLogger(NuevoContratoBasico.class.getName()).log(Level.SEVERE, null, ex);
                    FacesUtils.addErrorMessage(Propiedad.getValor("docexistenteerror"));
                }
            } else {
                FacesUtils.addErrorMessage(Propiedad.getValor("nosuarc"));
            }
        } else {
            FacesUtils.addErrorMessage(Propiedad.getValor("debeproporcinarelnombredeldocu"));
        }
        return null;
    }

    /**
     * determinar la ruta de donde se va almacenar el documento
     */
    public void subirDocumento() throws ArchivoExistenteException {
        if (!cargadorDocumento.getArchivos().isEmpty()) {
            cargadorDocumento.getArchivos().get(0).cambiarNombre(null, true);
            cargadorDocumento.guardarArchivosTemporales(MessageFormat.format(RutasWebArchivos.DOCS_CONTRATO, "" + getContrato().getIntidcontrato()), false);
            getDocumentoobra().setStrubicacion(cargadorDocumento.getArchivos().get(0).getRutaWeb());
        }
    }

    /**
     * Eliminar el documento seleccionado
     *
     * @param filaSeleccionada Corresponde a la fila de la que proviene la
     * acción en la tabla
     * @return
     */
    public String eliminardocu(int filaSeleccionada) {
        Documentoobra documento = listadocumentos.get(filaSeleccionada);
        listadocumentos.remove(documento);
        return null;

    }

    /**
     * mostrar que tipo de documento para asociarlo
     *
     * @return
     */
    public String llenarTipodocumentos() {
        List<Tipodocumento> listatipodocumento = getSessionBeanCobra().getCobraService().encontrarTiposDocumentos();
        tipodocumento = new SelectItem[listatipodocumento.size()];
        int i = 0;
        for (Tipodocumento tipodoc : listatipodocumento) {
            SelectItem tipod = new SelectItem(tipodoc.getInttipodoc(), tipodoc.getStrdesctipodoc());
            tipodocumento[i++] = tipod;
        }
        return null;
    }

////    public String subirDocPoliza() {
////        getDocumentoobra().setContrato(contrato);
////        getDocumentoobra().setTipodocumento(getSessionBeanCobra().getCobraService().encontrarTipoDocPorId(15));
////        subirDocPol();
////        listadocumentos.add(getDocumentoobra());
////        setDocumentoobra(new Documentoobra());
////        return null;
////    }
//
//    public void subirDocPol() {
//        String realArchivoPath = "";
//        String URL = "/resources/Documentos/Poliza/";
//
//        ServletContext theApplicationsServletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
//        if (subirDocPol.getSize() > 0) {
//            realArchivoPath = theApplicationsServletContext.getRealPath(URL);
//            try {
//                File carpeta = new File(realArchivoPath);
//                if (!carpeta.exists()) {
//                    carpeta.mkdirs();
//                }
//            } catch (Exception ex) {
//                mensaje = (bundle.getString("nosepuedesubir"));//"Cannot upload file ");
//            }
//        }
//        subirDocPol.guardarArchivosTemporales(realArchivoPath, false);
//        Iterator arch = subirDocPol.getFiles().iterator();
//        while (arch.hasNext()) {
//            Archivo nombreoriginal = (Archivo) arch.next();
//            getDocumentoobra().setStrubicacion(URL + getContrato().getIntidcontrato() + "/" + nombreoriginal.getOnlyName());
//        }
//    }
//
//    public String borrarDocPol() {
//        String ArchivoPath = "";
//        ServletContext contexto = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
//        if (pathActa.compareTo("") != 0) {
//            ArchivoPath = contexto.getRealPath(pathActa);
//            File archi = new File(ArchivoPath);
//            if (archi.exists()) {
//                archi.delete();
//            }
//            pathActa = "/resources/Documentos/Poliza/";
//        }
//        subirDocPol.borrarDatosSubidos();
//        return null;
//    }
    /**
     * invocar el paginador para traer los encargos fiduciarios
     *
     * @return
     */
    public String llenarEncargoFiduciario() {
        codigoencargo = 0;
        primeroEncargo();
        return null;

    }

    /**
     *
     * @param actual
     * @return
     */
    public boolean filtrarEncargo(Object actual) {
        Encargofiduciario enc = (Encargofiduciario) actual;
        if (this.valorFiltroContratos.length() == 0) {
            return true;
        }

        if (enc.getStrdescripcion().toLowerCase().contains(this.valorFiltroEncargo.toLowerCase())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Seleccionar el encargo fiduciario
     *
     * @return
     */
    public String encargoFiduSelec(int filaSeleccionada) {
        encargofiduciario = listaEncargofiduciario.get(filaSeleccionada);
        contrato.setEncargofiduciario(encargofiduciario);
        return null;

    }

    /**
     * cancelar el contrato
     *
     * @return
     */
    public String cancelar_action() {
        // TODO: Process the button click action. Return value is a navigation
        // case name where null will return to the same page.
        limpiarContrato();

        return "gestion";

    }

    /**
     * Seleccionar el contrato desde la tabla detalle
     *
     * @return
     */
    public String detalleContrato(int filaSeleccionada) {
        NuevoContratoBasico nuevoContraBasicoSeleccionado = (NuevoContratoBasico) FacesUtils.getManagedBean("Supervisor$Contrato");
        Contrato contratotabla = nuevoContraBasicoSeleccionado.getListacontratos().get(filaSeleccionada);
        //boolconthijo = false;
        ///Revisar este método se totea pero no siempre
        cargarContrato(contratotabla);
//        setContrato(contratotabla);
//        finentrega = contratotabla.getDatefechafin().toString();

        return "consultarContrato";
    }

    /**
     * Seleccionar el contrato según contratista, desde la tabla detalle
     *
     * @param filaSeleccionada Corresponde a la fila de la que proviene la
     * acción en la tabla
     * @return
     */
    public String detalleContratoContratista(int filaSeleccionada) {
        NuevoContratoBasico nuevoContraBasicoSeleccionado = (NuevoContratoBasico) FacesUtils.getManagedBean("Supervisor$Contrato");
        Contrato contratotabla = (Contrato) nuevoContraBasicoSeleccionado.getListacontratoscontratista().get(filaSeleccionada);
        cargarContrato(contratotabla);
        return "consultarContrato";
    }

    /**
     * Seleccionar el contrato desde la tabla detalle contratohijo
     *
     * @param filaSeleccionada Corresponde a la fila de la que proviene la
     * acción en la tabla
     * @return consultarContrato Refresca la página para mostrar el detalle del
     * contrato hijo.
     */
    public String detalleContrHijo(int filaSeleccionada) {
        Contrato contratoHijo = (Contrato) tablaSubconvenios.getRowData();
        cargarContrato(contratoHijo);

        return "consultarContrato";
    }

    /**
     * Seleccionar el contrato desde la tabla detalle conveniohijo
     *
     * @return consultarContrato Refresca la página para mostrar los detalles de
     * subconvenio.
     */
    public String detalleConvenioHijo() {
        
        Contrato contratoConvenioHijo = (Contrato) tablaSubconvenios.getRowData();

        cargarContrato(contratoConvenioHijo);

        return "consultarContrato";
    }

    /**
     * Seleccionar el contrato desde la tabla detalle contratopadre
     *
     * @return
     */
    public String detalleContratoPadre(int filaSeleccionada) {
        NuevoContratoBasico nuevoContraBasicoSeleccionado = (NuevoContratoBasico) FacesUtils.getManagedBean("Supervisor$Contrato");
        Contrato contratotabla = nuevoContraBasicoSeleccionado.getListacontratos().get(filaSeleccionada);
        //limpiarContrato();
        //contratotabla.getContrato().setFormapago(new Formapago());
        if (contratotabla.getContrato() != null) {
            //setContrato(contratotabla.getContrato());
            cargarContrato(contratotabla);
        } else {
            FacesUtils.addInfoMessage("No pertence a ningún contrato Padre");
            return null;
        }
        //finentrega = contratotabla.getDatefechafin().toString();
        return "consultarContrato";
    }

    /**
     * Seleccionar el contrato desde la tabla detalle contratopadre según el
     * contratista
     *
     * @param filaSeleccionada Corresponde a la fila de la que proviene la
     * acción en la tabla
     * @return
     */
    public String detalleContratoPadreContratista(int filaSeleccionada) {
        NuevoContratoBasico nuevoContraBasicoSeleccionado = (NuevoContratoBasico) FacesUtils.getManagedBean("Supervisor$Contrato");
        Contrato contratotabla = (Contrato) nuevoContraBasicoSeleccionado.getListacontratoscontratista().get(filaSeleccionada);
        //contratotabla.getContrato().setFormapago(new Formapago());
        if (contratotabla.getContrato() != null) {
            //setContrato(contratotabla.getContrato());
            cargarContrato(contratotabla);
        } else {
            FacesUtils.addInfoMessage("No pertence a ningún contrato Padre");
            return null;
        }
        return "consultarContrato";
    }

    /**
     * Iniciar Contrato o convenio
     *
     * @param cont
     */
    public void cargarContrato(Contrato cont) {
        contrato = new Contrato();
        contrato = cont;
        boolconthijo = false;
        boolsubconvenios = false;
        listaContrConvHijo.clear();
        listaSubconvenios.clear();
        finentrega = contrato.getDatefechafin().toString();
        listaPolizacontratos.clear();
        listaObraContrato.clear();
        listaGirodirecto.clear();
        listaModificarContrato.clear();
        boolpolizas = false;
        boolproyectos = false;
        boolgiros = false;
        boolmodifca = false;
        if (contrato.getBooltipocontratoconvenio()) {
            tipoContCon = "Convenio";
            booltipocontratoconvenio = true;
        } else {
            tipoContCon = "Contrato";
            booltipocontratoconvenio = false;
        }

    }

    /**
     * llenar lista de documentos por contrato o convenio
     *
     * @return
     */
    public String llenarDocumentosContrato() {
        listadocuContrato = getSessionBeanCobra().getCobraService().obtenerDocumentosxContrato(getContrato());
        return null;
    }

    public String bt_download_documento_action(int filaSeleccionada) {
        // TODO: Process the action. Return value is a navigation
        // case name where null will return to the same page.

        NuevoContratoBasico doc = (NuevoContratoBasico) FacesUtils.getManagedBean("Supervisor$Contrato");
        doc.getListadocuContrato().get(filaSeleccionada);
        getSessionBeanCobra().setUrlAbri(doc.getListadocuContrato().get(filaSeleccionada).getStrubicacion());
        //this.getSessionBeanCobra().setUrlAbri(doc.getStrubicacion());
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/" + getSessionBeanCobra().getBundle().getString("versioncobra") + "/" + doc.getListadocuContrato().get(filaSeleccionada).getStrubicacion());
        } catch (IOException ex) {
            Logger.getLogger(NuevoContratoBasico.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public String bt_download_documento_action_modulo(int filaSeleccionada) {
        // TODO: Process the action. Return value is a navigation
        // case name where null will return to the same page.
        Documentoobra doc = getSessionBeanCobra().getCobraService().getListaDocumentosContrato().get(filaSeleccionada);
        getSessionBeanCobra().setUrlAbri(doc.getStrubicacion());
        //this.getSessionBeanCobra().setUrlAbri(doc.getStrubicacion());
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/" + getSessionBeanCobra().getBundle().getString("versioncobra") + "/" + doc.getStrubicacion());
        } catch (IOException ex) {
            Logger.getLogger(NuevoContratoBasico.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    /**
     * invocar el paginador para traer los contratistas
     *
     * @return
     */
    public String llenarContratistaContrato() {
        nombre = "";
        aplicafiltro = false;
        primerosreales();
        return null;
    }

    /**
     * Muestra los primeros 5 contratistas por nombre
     *
     * @return null
     */
    public String primerosreales() {
        if (aplicafiltro) {

            listaContratista = getSessionBeanCobra().getCobraService().filtrarContratistas(nombre, 0, 5);
            totalfilas = getSessionBeanCobra().getCobraService().numFiltrarContratistas(nombre);
            // }
        } else {

            listaContratista = getSessionBeanCobra().getCobraService().encontrarContratistas(0, 5);
            totalfilas = getSessionBeanCobra().getCobraService().getNumContratistas();
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
     * Muestra los siguientes 5 contratistas por nombre
     *
     * @return null
     */
    public String siguientesReales() {

        int num = (pagina) * 5;
        if (aplicafiltro) {
            listaContratista = getSessionBeanCobra().getCobraService().filtrarContratistas(nombre, num, 5);
            totalfilas = getSessionBeanCobra().getCobraService().numFiltrarContratistas(nombre);

        } else {
            listaContratista = getSessionBeanCobra().getCobraService().encontrarContratistas(num, 5);
            totalfilas = getSessionBeanCobra().getCobraService().getNumContratistas();
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
     * Muestra los anteriores 5 contratistas por nombre
     *
     * @return null
     */
    public String anterioresReales() {

        pagina = pagina - 1;
        int num = (pagina - 1) * 5;
        if (aplicafiltro) {
            listaContratista = getSessionBeanCobra().getCobraService().filtrarContratistas(nombre, num, 5);
            totalfilas = getSessionBeanCobra().getCobraService().numFiltrarContratistas(nombre);
        } else {
            listaContratista = getSessionBeanCobra().getCobraService().encontrarContratistas(num, 5);
            totalfilas = getSessionBeanCobra().getCobraService().getNumContratistas();
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
     * Muestra los últimos 5 contratistas por nombre
     *
     * @return null
     */
    public String ultimoReales() {
        int num = totalfilas % 5;

        if (aplicafiltro) {
            totalfilas = getSessionBeanCobra().getCobraService().numFiltrarContratistas(nombre);
            listaContratista = getSessionBeanCobra().getCobraService().filtrarContratistas(nombre, totalfilas - num, totalfilas);
        } else {
            totalfilas = getSessionBeanCobra().getCobraService().getNumContratistas();
            listaContratista = getSessionBeanCobra().getCobraService().encontrarContratistas(totalfilas - num, totalfilas);
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
     * Filtra por nombre el buscado del contratista
     *
     * @return
     */
    public String buscarContratista() {
        aplicafiltro = false;
        if (nombre.length() != 0) {
            //   listaContratista.clear();
            aplicafiltro = true;
        }
        primerosreales();
        return null;
    }

    /**
     * Seleccionar y llevar el contratista al contrato
     *
     * @return
     */
    public String seleccionarContratistas(int filaSeleccinada) {
        NuevoContratoBasico nb = (NuevoContratoBasico) FacesUtils.getManagedBean("Supervisor$Contrato");
        contrato.setContratista(nb.getListaContratista().get(filaSeleccinada));
        return null;
    }

    /**
     * Seleecionar el contratista y habilitar los campos para ser editado
     *
     * @return
     */
    public String editarContratistas(int filaSeleccionada) {
        boolcrearcontratista = false;
        booleditando = true;
        contratista = listaContratista.get(filaSeleccionada);
        cambiarPersona();

        return null;
    }

    /**
     * Habilita la información de la poliza a modificar y la lista
     *
     * @param 
     * 
     * @return
     */
    public String editarPoliza() {
        //limpiarContrato();
        Contrato contratotabla = (Contrato) tablacontratoconveniocontratista.getRowData();
      

        boolcrearpoliza = true;

        return null;
    }

    /**
     * Listar en un combobox areas de contratista
     */
    public void llenarAreasContratista() {
        List<AreaContratista> listarc = getSessionBeanCobra().getCobraService().encontrarAreasContratista();

        AreaContratistaOption = new SelectItem[listarc.size()];
        int i = 0;
        for (AreaContratista area : listarc) {
            SelectItem itemArea = new SelectItem(area.getIntcodigoareacon(), area.getStrareacon());
            AreaContratistaOption[i++] = itemArea;
        }

    }

    /**
     * Seleccionar si es persona natural o juridica
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
     * Departamento para ese contratista
     *
     * @return
     */
    public String llenarDepartamentoContratista() {
        listaDeptos = getSessionBeanCobra().getCobraService().encontrarDepartamentos();
        DepartamentoContratista = new SelectItem[listaDeptos.size()];
        int i = 0;

        for (Localidad depto : listaDeptos) {
            SelectItem dep = new SelectItem(depto.getStrcodigolocalidad(), depto.getStrdepartamento());
            if (i == 0) {
                codDepartamentoContratista = depto.getStrcodigolocalidad();
            }
            DepartamentoContratista[i++] = dep;
        }

        DeptoExpdocumento = new SelectItem[listaDeptos.size()];
        int j = 0;
        for (Localidad deptoExpdoc : listaDeptos) {
            SelectItem dep = new SelectItem(deptoExpdoc.getStrcodigolocalidad(), deptoExpdoc.getStrdepartamento());
            if (j == 0) {
                codDeptoExpdocumento = deptoExpdoc.getStrcodigolocalidad();
            }
            DeptoExpdocumento[j++] = dep;
        }

        DeptoNacimiento = new SelectItem[listaDeptos.size()];
        int k = 0;
        for (Localidad deptoNacimiento : listaDeptos) {
            SelectItem dep = new SelectItem(deptoNacimiento.getStrcodigolocalidad(), deptoNacimiento.getStrdepartamento());
            if (k == 0) {
                codDeptoNacimiento = deptoNacimiento.getStrcodigolocalidad();
            }
            DeptoNacimiento[k++] = dep;
        }

        return null;
    }

    /**
     * Municipio de ese depto
     *
     * @return
     */
    public String llenarMunicipioContratista() {
        listaMunicipios = getSessionBeanCobra().getCobraService().encontrarMunicipios(codDepartamentoContratista);
        MunicipioContratista = new SelectItem[listaMunicipios.size()];
        int i = 0;
        for (Localidad muni : listaMunicipios) {
            SelectItem mun = new SelectItem(muni.getStrcodigolocalidad(), muni.getStrmunicipio());
            MunicipioContratista[i++] = mun;
        }
        return null;
    }

    /**
     * Municipio de la cedula del contratista de ese depto
     *
     * @return
     */
    public String llenarMunicipioExpdocumento() {
        listaMunicipios = getSessionBeanCobra().getCobraService().encontrarMunicipios(codDeptoExpdocumento);
        MpioExpdocumento = new SelectItem[listaMunicipios.size()];
        int i = 0;
        for (Localidad muni : listaMunicipios) {
            SelectItem mun = new SelectItem(muni.getStrcodigolocalidad(), muni.getStrmunicipio());
            MpioExpdocumento[i++] = mun;
        }
        return null;
    }

    /**
     * Municipio de Nacimiento para Contratista
     *
     * @return
     */
    public String llenarMunicipioNacimiento() {
        listaMunicipios = getSessionBeanCobra().getCobraService().encontrarMunicipios(codDeptoNacimiento);
        MpioNacimiento = new SelectItem[listaMunicipios.size()];
        int i = 0;
        for (Localidad muni : listaMunicipios) {
            SelectItem mun = new SelectItem(muni.getStrcodigolocalidad(), muni.getStrmunicipio());
            MpioNacimiento[i++] = mun;
        }
        return null;
    }

    /**
     * listar tipo de identificación (Resgistro Civil, Tarjeta de identidad,
     * cédula entre otros)
     */
    public void llenarTiposIdentificacion() {
        List<Tipoidentificacion> listaIden = getSessionBeanCobra().getCobraService().encontrarTiposIdentificacion();

        TipoIdentificacionOption = new SelectItem[listaIden.size()];
        int i = 0;
        for (Tipoidentificacion tipoIde : listaIden) {
            SelectItem tpi = new SelectItem(tipoIde.getIntcodigotipoiden(), tipoIde.getStrnombre());

            TipoIdentificacionOption[i++] = tpi;

        }
    }

    /**
     *
     * Cargo ante el sistema (Gerente,Gobernador,Interventor,Alcalde, etc) )
     */
    public void llenarCargos() {
        List<Cargo> listaCargo = getSessionBeanCobra().getCobraService().encontrarCargos();

        CargoOption = new SelectItem[listaCargo.size()];
        int i = 0;
        for (Cargo car : listaCargo) {
            SelectItem c = new SelectItem(car.getIntcargo(), car.getStrdescripcion());

            CargoOption[i++] = c;
        }
    }

    /**
     * Listar genero sexual (Masculino, Fenemino)
     */
    public void llenarGeneros() {
        List<Genero> listaGen = getSessionBeanCobra().getCobraService().encontrarGeneros();

        GeneroOption = new SelectItem[listaGen.size()];
        int i = 0;
        for (Genero gene : listaGen) {
            SelectItem genItem = new SelectItem(gene.getIntgenero(), gene.getStrnombre());
            GeneroOption[i++] = genItem;
        }
    }

    /**
     * Listar Estado Civil del Contratista
     */
    public void llenarEstadoCivil() {
        List<EstadoCivil> listaec = getSessionBeanCobra().getCobraService().encontrarEstadosCiviles();

        EstadoCivilOption = new SelectItem[listaec.size()];
        int i = 0;
        for (EstadoCivil ec : listaec) {
            SelectItem estCiv = new SelectItem(ec.getIntestadoCivil(), ec.getStrnombre());
            EstadoCivilOption[i++] = estCiv;
        }
    }

    /**
     * iniciar métodos necesarios para mostrar o guardar contratista
     */
    public void llenarComboContratista() {
        llenarEstadoCivil();
        llenarGeneros();
        llenarCargos();
        llenarTiposIdentificacion();
        llenarDepartamentoContratista();
        llenarMunicipioContratista();
        llenarMunicipioExpdocumento();
        llenarMunicipioNacimiento();
        llenarDocumentosContrato();
        llenarTiposTerceros();
        llenarAreasContratista();
    }

    /**
     * adicionar un nuevo contratista depende si es persona natural o juridica
     *
     * @return
     */
    public String guardarContratista() {
        boolcrearcontratista = true;
        if (getContratista().getTipotercero().getIntcodigotipotercero() == 2) {
            contratista.setStrapellido1(null);
            contratista.setStrapellido2(null);
        }
        contratista.setDateusuariocreacion(new Date());
        contratista.setBoolestado(true);
        getSessionBeanCobra().getCobraService().guardarContratista(contratista);
        FacesUtils.addInfoMessage("Los datos de contratista se han guardado.");
        if (!booleditando) {
            contrato.setContratista(contratista);
            booleditando = false;
        }
        return null;
    }

    /**
     * Limpiar listas y setear variables del contratista
     *
     * @return
     */
    public String inicializarContratista() {
        limpiarContratista();
        return null;
    }

    /**
     * Cambiar el estado del guardado de un contratista si es persona natural o
     * juridica
     *
     * @return
     */
    public boolean cambiarPersona() {

        if (getContratista().getTipotercero().getIntcodigotipotercero() == 1) {
            return habidescontratista = true;

        } else {
            return habidescontratista = false;
        }

    }

    /**
     * seteo de variables y listas para el inicio del contratista
     *
     * @return
     */
    public String limpiarContratista() {
        habidescontratista = true;
        boolcrearcontratista = false;
        contratista = new Contratista();
        contratista.setIntcedula(BigInteger.ZERO);
        contratista.setDateusuariocreacion(new Date());
        contratista.setDatefechanacimiento(new Date());
        contratista.setIntdigitoverificacion(0);
        contratista.setTipotercero(new Tipotercero(1));
        contratista.setTipoidentificacion(new Tipoidentificacion());

        return null;
    }

    /**
     * Instancia del bean AdministrarObraNew
     *
     * @return
     */
    protected AdministrarObraNew getAdmin() {
        return (AdministrarObraNew) FacesUtils.getManagedBean("Supervisor$AdministrarObraNew");
    }

//    public String cargarContratista() {
//        boolcrearcontratista = true;
//
//        contratista = getSessionBeanCobra().getCobraService().obtenerContratista(getAdmin().getObra()).get(0);
//        cambiarPersona();
//
//        return null;
//    }
    ///Detalle contrato paginador
    /**
     * Iniciar el buscar de contrato o convenio
     *
     * @return
     */
    public String buscarContrato() {

        try {
            // aplicafiltrocontrato = false;
            listacontratos.clear();
            listacontratoscontratista.clear();
            if (filtrocontrato.getPalabraClave().length() != 0) {
                listacontratos.clear();
                //aplicafiltrocontrato = true;
            }
            primeroDetcontrato();
            primeroDetcontratoContratista();

        } catch (Exception e) {
        }
        return null;
    }

    /**
     * Muestra los primeros 5 contratos por filtro (nombre, nro de contrato,etc)
     *
     * @return null
     */
    public String primeroDetcontrato() {
//        if (aplicafiltrocontrato) {

        if (comboEntidadesContratoguardar()) {
            listacontratos = getSessionBeanCobra().getCobraService().filtroAvanzadoContratoContratante(getContrato().getTercero().getIntcodigo(), filtrocontrato, 0, 5);
            totalfilas = getSessionBeanCobra().getCobraService().cantidadfFiltroAvanzadoContratoContratante(getContrato().getTercero().getIntcodigo(), filtrocontrato);
            //        } else {
//            listacontratos = getSessionBeanCobra().getCobraService().obtenerContratoxEntidad(getContrato().getTercero().getIntcodigo(), 0, 5, filtro);
//            totalfilas = getSessionBeanCobra().getCobraService().numContratoxEntidad(getContrato().getTercero().getIntcodigo(), filtro);
//        }
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

        } else {
            //FacesUtils.addErrorMessage("Debe diligenciar el campo Entidad Contratante");
            getContrato().getTercero().setIntcodigo(0);
        }

        return null;
    }

    /**
     * Acción lanzada al dar clic en el botón primero del paginador de la tabla
     * de convenios según el contratista
     *
     * @return
     */
    public String primeroDetcontratoContratista() {
        if (comboEntidadesContratoguardar()) {
            listacontratoscontratista = getSessionBeanCobra().getCobraService().filtroAvanzadoContratoContratista(getContrato().getTercero().getIntcodigo(), filtrocontrato, 0, 5);
            totalfilasContratista = getSessionBeanCobra().getCobraService().cantidadfFiltroAvanzadoContratoContratista(getContrato().getTercero().getIntcodigo(), filtrocontrato);
            paginaContratista = 1;
            if (totalfilasContratista <= 5) {
                totalpaginasContratista = 1;
            } else {
                totalpaginasContratista = totalfilasContratista / 5;
                if (totalfilasContratista % 5 > 0) {
                    totalpaginasContratista++;
                }
            }
            veranteriorcontratoContratista = false;
            if (totalpaginasContratista > 1) {
                verultimoscontratoContratista = true;
            } else {
                verultimoscontratoContratista = false;
            }
        } else {
            getContrato().getTercero().setIntcodigo(0);
        }
        return null;
    }

    /**
     * Muestra los siguiente 5 contratos por filtro (nombre, nro de
     * contrato,etc)
     *
     * @return null
     */
    public String siguienteDetcontrato() {

        int num = (pagina) * 5;

//        if (aplicafiltrocontrato) {
        listacontratos = getSessionBeanCobra().getCobraService().filtroAvanzadoContratoContratante(getContrato().getTercero().getIntcodigo(), filtrocontrato, num, 5);
        totalfilas = getSessionBeanCobra().getCobraService().cantidadfFiltroAvanzadoContratoContratante(getContrato().getTercero().getIntcodigo(), filtrocontrato);

//        } else {
//
//            listacontratos = getSessionBeanCobra().getCobraService().obtenerContratoxEntidad(getContrato().getTercero().getIntcodigo(), num, 5, filtro);
//            totalfilas = getSessionBeanCobra().getCobraService().numContratoxEntidad(getContrato().getTercero().getIntcodigo(), filtro);
//        }
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
     * Muestra los siguiente 5 contratos por filtro (nombre, nro de
     * contrato,etc) para la tabla de convenios según el contratista
     *
     * @return null
     */
    public String siguienteDetcontratoContratista() {

        int num = (paginaContratista) * 5;

        listacontratoscontratista = getSessionBeanCobra().getCobraService().filtroAvanzadoContratoContratista(getContrato().getTercero().getIntcodigo(), filtrocontrato, num, 5);
        totalfilasContratista = getSessionBeanCobra().getCobraService().cantidadfFiltroAvanzadoContratoContratista(getContrato().getTercero().getIntcodigo(), filtrocontrato);

        if (totalfilasContratista <= 5) {
            totalpaginasContratista = 1;
        } else {
            totalpaginasContratista = totalfilasContratista / 5;
            if (totalfilasContratista % 5 > 0) {
                totalpaginasContratista++;
            }
        }
        paginaContratista = paginaContratista + 1;
        if (paginaContratista < totalpaginasContratista) {
            verultimoscontratoContratista = true;
        } else {
            verultimoscontratoContratista = false;
        }
        veranteriorcontratoContratista = true;
        return null;
    }

    /**
     * Muestra los anterior 5 contratos por filtro (nombre, nro de contrato,etc)
     *
     * @return null
     */
    public String anterioresDetcontrato() {

        pagina = pagina - 1;
        int num = (pagina - 1) * 5;

//        if (aplicafiltrocontrato) {
        listacontratos = getSessionBeanCobra().getCobraService().filtroAvanzadoContratoContratante(getContrato().getTercero().getIntcodigo(), filtrocontrato, num, 5);
        totalfilas = getSessionBeanCobra().getCobraService().cantidadfFiltroAvanzadoContratoContratante(getContrato().getTercero().getIntcodigo(), filtrocontrato);

//        } else {
//
//            listacontratos = getSessionBeanCobra().getCobraService().obtenerContratoxEntidad(getContrato().getTercero().getIntcodigo(), num, 5, filtro);
//            totalfilas = getSessionBeanCobra().getCobraService().numContratoxEntidad(getContrato().getTercero().getIntcodigo(), filtro);
//        }
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
     * Muestra los anterior 5 contratos por filtro (nombre, nro de contrato,etc)
     * para la tabla de convenios según el contratista
     *
     * @return null
     */
    public String anterioresDetcontratoContratista() {

        paginaContratista = paginaContratista - 1;
        int num = (paginaContratista - 1) * 5;
        listacontratoscontratista = getSessionBeanCobra().getCobraService().filtroAvanzadoContratoContratista(getContrato().getTercero().getIntcodigo(), filtrocontrato, num, 5);
        totalfilasContratista = getSessionBeanCobra().getCobraService().cantidadfFiltroAvanzadoContratoContratista(getContrato().getTercero().getIntcodigo(), filtrocontrato);
        if (totalfilasContratista <= 5) {
            totalpaginasContratista = 1;
        } else {
            totalpaginasContratista = totalfilasContratista / 5;
            if (totalfilasContratista % 5 > 0) {
                totalpaginasContratista++;
            }
        }
        if (paginaContratista > 1) {
            veranteriorcontratoContratista = true;
        } else {
            veranteriorcontratoContratista = false;
        }
        verultimoscontratoContratista = true;
        return null;
    }

    /**
     * Muestra los último 5 contratos por filtro (nombre, nro de contrato,etc)
     *
     * @return null
     */
    public String ultimoDetcontrato() {

        int num = totalfilas % 5;

//        if (aplicafiltrocontrato) {
        totalfilas = getSessionBeanCobra().getCobraService().cantidadfFiltroAvanzadoContratoContratante(getContrato().getTercero().getIntcodigo(), filtrocontrato);
        listacontratos = getSessionBeanCobra().getCobraService().filtroAvanzadoContratoContratante(getContrato().getTercero().getIntcodigo(), filtrocontrato, totalfilas - num, totalfilas);

//        } else {
//
//            totalfilas = getSessionBeanCobra().getCobraService().numContratoxEntidad(getContrato().getTercero().getIntcodigo(), filtro);
//            listacontratos = getSessionBeanCobra().getCobraService().obtenerContratoxEntidad(getContrato().getTercero().getIntcodigo(), totalfilas - num, totalfilas, filtro);
//
//        }
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

    /**
     * Muestra los último 5 contratos por filtro (nombre, nro de contrato,etc)
     * para la tabla de convenios según el contratista
     *
     * @return null
     */
    public String ultimoDetcontratoContratista() {
        int num = totalfilasContratista % 5;
        totalfilasContratista = getSessionBeanCobra().getCobraService().cantidadfFiltroAvanzadoContratoContratista(getContrato().getTercero().getIntcodigo(), filtrocontrato);
        listacontratoscontratista = getSessionBeanCobra().getCobraService().filtroAvanzadoContratoContratista(getContrato().getTercero().getIntcodigo(), filtrocontrato, totalfilasContratista - num, totalfilasContratista);

        if (totalfilasContratista <= 5) {
            totalpaginasContratista = 1;
        } else {
            totalpaginasContratista = totalfilasContratista / 5;
            if (totalfilasContratista % 5 > 0) {
                totalpaginasContratista++;
            }
        }
        paginaContratista = totalpaginasContratista;
        if (paginaContratista < totalpaginasContratista) {
            verultimoscontratoContratista = true;
        } else {
            verultimoscontratoContratista = false;
        }
        veranteriorcontratoContratista = true;
        return null;
    }

    /**
     * Iniciar El detalle del contrato seteando las variables necesariaspara se
     * mostrado
     *
     * @return la pagina detallecontrato
     */
    public String iniciarDetaContrato() {
        habilitarBtnModificarcontrato = true;
        habilitarBtnGuardarCancelarContrato = false;
        habilitarmodificar = true;
        modificartxt = false;
        habilitarModificarNumero = true;
        habilitarGuardarNumeroContrato = false;
        listacontratos.clear();
        listacontratoscontratista.clear();
        tipoContCon = "Contrato";
        booltipocontratoconvenio = false;
        boolcontrconsultoria = false;
        filtrocontrato.setBoolcontrconsultoria(false);
        filtrocontrato.setBooltienehijo(false);
        filtrocontrato.setBooltipocontconv(false);
        filtrocontrato.getEstadoConvenio();
        limpiarContrato();
        primeroDetcontrato();
        return "consultarContratoConvenio";
    }

    /**
     * Iniciar El detalle del convenio seteando las variables necesariaspara se
     * mostrado
     *
     * @return la pagina detalleconvenio adecuada a este
     */
    public String iniciarDetaConvenio() {
        listacontratos.clear();
        listacontratoscontratista.clear();
        tipoContCon = "Convenio";
        booltipocontratoconvenio = true;
        boolcontrconsultoria = false;
        filtrocontrato.setBoolcontrconsultoria(false);
        filtrocontrato.setBooltienehijo(false);
        filtrocontrato.setBooltipocontconv(true);
        limpiarContrato();
        primeroDetcontrato();
        primeroDetcontratoContratista();
        return "consultarContratoConvenio";
    }

    /**
     * Iniciar contrato enviando el idcontrato
     *
     * @return
     */
    public String ReporteConvenio() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdfconvenio") + contrato.getIntidcontrato());
        } catch (IOException ex) {
            Logger.getLogger(Contrato.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Mostrar la planificación de los pagos por acta llenando en una lista la
     * planifificacion por contrato y dependiendo del idforma de pago se setean
     * algunos atributos dependiendo del tipo de planificacion
     *
     * @return
     */
    public String llenarplanificacionpagos() {

        setAnticiforma(new Integer(0));
        setVloranticipo(new BigDecimal(0));
        setPorcenanticipo(new BigDecimal(0));
        setFechapago(new Date());
        getSessionBeanCobra().getCobraService().setListaPlanificacionpagos(getSessionBeanCobra().getCobraService().encontrarPlanificacionpagoxContrato(contrato));

        if (getSessionBeanCobra().getCobraService().getListaPlanificacionpagos().size() > 0) {
            switch (contrato.getFormapago().getIntidformapago()) {
                case 1:
                    //"ANTICIPO Y ACTAS PARCIALES"
                    setAnticiforma(1);
                    setVloranticipo(getSessionBeanCobra().getCobraService().getListaPlanificacionpagos().get(0).getNumvlrpago());
                    setPorcenanticipo(getSessionBeanCobra().getCobraService().getListaPlanificacionpagos().get(0).getNumvlrporcentage());
                    setFechapago(getSessionBeanCobra().getCobraService().getListaPlanificacionpagos().get(0).getDatefechapago());
                    getSessionBeanCobra().getCobraService().getListaPlanificacionpagos().remove(0);
                    break;
                case 2:
                    //"ACTAS PARCIALES"
                    setAnticiforma(2);
                    break;
                case 3:
                    //"ACTA UNICA"
                    setAnticiforma(3);
                    setVloranticipo(getSessionBeanCobra().getCobraService().getListaPlanificacionpagos().get(0).getNumvlrpago());
                    setFechapago(getSessionBeanCobra().getCobraService().getListaPlanificacionpagos().get(0).getDatefechapago());
                    break;
            }
        }
        return null;
    }

    /**
     * Listar en combo box las fases (Ah,Rehabilitación)
     */
    public void llenarFase() {
        List<Fase> listaFase = getSessionBeanCobra().getCobraService().encontrarFase();
        fasecombo = new SelectItem[listaFase.size()];
        int i = 0;

        for (Fase faselis : listaFase) {
            SelectItem faset = new SelectItem(faselis.getIntidfase(), faselis.getStrnombre());
            fasecombo[i++] = faset;
        }

    }

    /**
     * Inicializar los atributos necesarios para comenzar el filtro avanzado de
     * búsqueda
     *
     * @return
     */
    public String iniciarFiltroAvanzado() {
        List<Tipoproyecto> listaTiposProyecto = getSessionBeanCobra().getCobraService().encontrarTiposProyecto();
        if (listaTiposProyecto != null) {
            llenarComboTiposProyecto(listaTiposProyecto);
        }
        llenarFase();
        return null;
    }

    /**
     * Iniciar o llenar los combos necesarios para el filtro avanzado de
     * búsqueda
     *
     * @param listTiposProyectos para iterar y mostrar el tipo de proyecto
     */
    public void llenarComboTiposProyecto(List listTiposProyectos) {
        SelectItem[] TempTipoProyecto = new SelectItem[listTiposProyectos.size() + 1];

        TempTipoProyecto[0] = new SelectItem(0, bundle.getString("todos"));

        int j = 1, k = 1;
        for (Iterator i = listTiposProyectos.iterator(); i.hasNext();) {

            Tipoproyecto tipo = (Tipoproyecto) i.next();
            if (verificarTiposProyecto(tipo, j, TempTipoProyecto) == false) {
                SelectItem opt = new SelectItem(tipo.getIntidtipoproyecto(), tipo.getStrnombre());
                TempTipoProyecto[j] = opt;
                j++;

            }
        }
        tipoproyecto = new SelectItem[j];
        tipoproyecto[0] = new SelectItem(0, bundle.getString("todos"));
        while (k < j) {
            tipoproyecto[k] = TempTipoProyecto[k];
            k++;
        }
    }

    /**
     * Verificar el tipo de proyecto
     *
     * @param tipo
     * @param cant
     * @param temporal
     * @return
     */
    public boolean verificarTiposProyecto(Tipoproyecto tipo, int cant, SelectItem[] temporal) {
        int i = 1;
        while (i < cant) {
            if (tipo.getStrnombre().compareTo(temporal[i].getLabel()) == 0) {
                return true;
            }
            i++;
        }
        return false;
    }

    /**
     * Llenar subtipos de proyectos
     *
     * @param listSubTiposProyectos
     */
    public void llenarComboSubTiposProyecto(List listSubTiposProyectos) {
        SelectItem[] TempSubTipoProyecto = new SelectItem[listSubTiposProyectos.size() + 1];

        TempSubTipoProyecto[0] = new SelectItem(0, bundle.getString("todos"));

        int j = 1, k = 1;
        for (Iterator i = listSubTiposProyectos.iterator(); i.hasNext();) {

            Tipoobra tipo = (Tipoobra) i.next();
            if (verificarSubTiposProyecto(tipo, j, TempSubTipoProyecto) == false) {
                SelectItem opt = new SelectItem(tipo.getInttipoobra(), tipo.getStrnombrecorto());
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

    /**
     * Verificar el tipo de proyecto
     *
     * @param tipo
     * @param cant
     * @param temporal
     * @return
     */
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

    /**
     * Obtiene los contratos o subconvenios hijos a partir de un contrato.
     *
     * @throws DataAccessLayerException
     */
    public String llenarContrConvHijo() {
        boolconthijo = true;
        listaContrConvHijo = getSessionBeanCobra().getCobraService().encontrarContratosHijos(getContrato(), false);

        return null;
    }

    /**
     * Obtiene los contratos o subconvenios hijos a partir de un contrato
     *
     * @throws DataAccessLayerException
     */
    public String llenarSubconvenios() {

        listaSubconvenios = getSessionBeanCobra().getCobraService().encontrarContratosHijos(getContrato(), true);

        return null;
    }

    /**
     * Cambiar de contrato a convenio padre al seleccionarlo
     *
     * @return null
     */
    public String cambiarContrPadre() {
        varmostrarcontrpa = 0;
        listacontratos.clear();
        listacontratoscontratista.clear();
        return null;
    }

    /**
     * Validar que las fechas de los contratos a crear esten dentro del rango de
     * las fechas del contrato o convenio padre
     *
     * @return true si cumple false si no cumple con lo anterior mencionado
     */
    public Boolean validarFechaPadre() {
        if ((contrato.getDatefechafin().before(contrpadre.getDatefechafin()) || contrato.getDatefechafin().equals(contrpadre.getDatefechafin()))
                && (contrpadre.getDatefechaini().before(contrato.getDatefechaini()) || contrpadre.getDatefechaini().equals(contrato.getDatefechaini()))) {
            return true;
        }
        return false;
    }

    /**
     * El valor del contrato a crear debe ser menor o igual al valor disponible
     * del contrato padre
     *
     * @return true si cumple false si no cumple con lo anterior mencionado
     */
    public Boolean validarVlrContratoPadre() {
        BigDecimal vldispo = new BigDecimal(BigInteger.ZERO);
        vldispo = contrpadre.getNumvlrcontrato().subtract(contrpadre.getNumvlrsumahijos());
        if (contrato.getNumvlrcontrato().compareTo(vldispo) == -1 || contrato.getNumvlrcontrato().compareTo(vldispo) == 0) {//El valor del contrato a crear debe ser menor o igual al valor disponible
            return true;
        }
        return false;
    }

    /**
     * Desde el filtro de búsqueda de un encargo es invocado este método, con el
     * fin de buscar el numero del encargo fiducaurio
     *
     * @return null
     */
    public String buscarEncargo() {
        aplicafiltroencargo = false;
        if (codigoencargo != 0) {
            listaEncargofiduciario.clear();
            aplicafiltroencargo = true;
        }
        primeroEncargo();
        return null;
    }

    /**
     * Muestra los primeros 5 encargos cuando se inicia el formulario, si se
     * ingreso por el método buscarencargo el filtra y se trae el numero de
     * encargo que se esta buscando
     *
     * @return null
     */
    public String primeroEncargo() {
        if (aplicafiltroencargo) {
            listaEncargofiduciario = getSessionBeanCobra().getCobraService().filtroAvanzadoEncargo(codigoencargo, 0, 5);
            totalfilas = getSessionBeanCobra().getCobraService().numFiltrarEncargo(codigoencargo);
        } else {
            listaEncargofiduciario = getSessionBeanCobra().getCobraService().encontrarEncargo(0, 5);
            totalfilas = getSessionBeanCobra().getCobraService().getNumEncargo();
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
        veranteriorencargo = false;
        if (totalpaginas > 1) {
            verultimosencargo = true;
        } else {
            verultimosencargo = false;
        }

        return null;
    }

    /**
     * Muestra los siguiente 5 encargos cuando se inicia el formulario , si se
     * ingreso por el método buscarencargo el filtra y se trae el numero de
     * encargo que se esta buscando
     *
     * @return null
     */
    public String siguienteEncargo() {

        int num = (pagina) * 5;

        if (aplicafiltroencargo) {
            listaEncargofiduciario = getSessionBeanCobra().getCobraService().filtroAvanzadoEncargo(codigoencargo, num, num + 5);
            totalfilas = getSessionBeanCobra().getCobraService().numFiltrarEncargo(codigoencargo);

        } else {

            listaEncargofiduciario = getSessionBeanCobra().getCobraService().encontrarEncargo(num, num + 5);
            totalfilas = getSessionBeanCobra().getCobraService().getNumEncargo();

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
            verultimosencargo = true;
        } else {
            verultimosencargo = false;
        }
        veranteriorencargo = true;

        return null;
    }

    /**
     * Muestra los anterior 5 encargos cuando se inicia el formulario , si se
     * ingreso por el método buscarencargo el filtra y se trae el numero de
     * encargo que se esta buscando
     *
     * @return null
     */
    public String anteriorEncargo() {

        pagina = pagina - 1;
        int num = (pagina - 1) * 5;

        if (aplicafiltroencargo) {
            listaEncargofiduciario = getSessionBeanCobra().getCobraService().filtroAvanzadoEncargo(codigoencargo, num, num + 5);
            totalfilas = getSessionBeanCobra().getCobraService().numFiltrarEncargo(codigoencargo);

        } else {
            listaEncargofiduciario = getSessionBeanCobra().getCobraService().encontrarEncargo(num, num + 5);
            totalfilas = getSessionBeanCobra().getCobraService().getNumEncargo();
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
            veranteriorencargo = true;
        } else {
            veranteriorencargo = false;
        }
        verultimosencargo = true;
        return null;
    }

    /**
     * Muestra los último 5 encargos cuando se inicia el formulario , si se
     * ingreso por el método buscarencargo el filtra y se trae el numero de
     * encargo que se esta buscando
     *
     * @return null
     */
    public String ultimoEncargo() {

        int num = totalfilas % 5;

        if (aplicafiltrocontrato) {
            totalfilas = getSessionBeanCobra().getCobraService().numFiltrarEncargo(codigoencargo);
            listaEncargofiduciario = getSessionBeanCobra().getCobraService().filtroAvanzadoEncargo(codigoencargo, totalfilas - num, totalfilas);
        } else {
            totalfilas = getSessionBeanCobra().getCobraService().getNumEncargo();
            listaEncargofiduciario = getSessionBeanCobra().getCobraService().encontrarEncargo(totalfilas - num, totalfilas);
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
            verultimosencargo = true;
        } else {
            verultimosencargo = false;
        }
        veranteriorencargo = true;

        return null;
    }

    /**
     * Listar los proyectos que se encuantran asociados a el contrato
     *
     * @return null
     */
    public String llenarObraAsociada() {

        if (buscarproyecto.length() != 0) {
            listaObraContrato.clear();

        }
        primeroObra();
        return null;
    }

    /**
     * Muestra los primeros 5 proyectos cuando se inicia el simpletooglepanel ,
     * si la búsqueda lleva algún parámetro se trae la obra o proyecto que se
     * esta buscando
     *
     * @return null
     */
    public String primeroObra() {
        //if (aplicafiltroobra) {
        listaObraContrato = getSessionBeanCobra().getCobraService().encontrarObraxContratos(getContrato().getIntidcontrato(), buscarproyecto, 0, 5, booltipocontratoconvenio);
        totalfilas = getSessionBeanCobra().getCobraService().getNumObraxContratos(getContrato().getIntidcontrato(), buscarproyecto, booltipocontratoconvenio);
//        } else {
//            listaObraContrato = getSessionBeanCobra().getCobraService().obtenerObraxContratos(getContrato().getIntidcontrato(), 0, 5);
//            totalfilas = getSessionBeanCobra().getCobraService().getNumObraxContratos(getContrato().getIntidcontrato());
//        }

        pagina = 1;
        if (totalfilas <= 5) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 5;
            if (totalfilas % 5 > 0) {
                totalpaginas++;
            }
        }
        veranteriorobra = false;
        if (totalpaginas > 1) {
            verultimoobra = true;
        } else {
            verultimoobra = false;
        }

        return null;
    }

    /**
     * Muestra los siguiente 5 proyectos cuando se inicia el simpletooglepanel ,
     * si la búsqueda lleva algún parámetro se trae la obra o proyecto que se
     * esta buscando
     *
     * @return null
     */
    public String siguienteObra() {
        int num = (pagina) * 5;

        listaObraContrato = getSessionBeanCobra().getCobraService().encontrarObraxContratos(getContrato().getIntidcontrato(), buscarproyecto, num, 5, booltipocontratoconvenio);
        totalfilas = getSessionBeanCobra().getCobraService().getNumObraxContratos(getContrato().getIntidcontrato(), buscarproyecto, booltipocontratoconvenio);

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
            verultimoobra = true;
        } else {
            verultimoobra = false;
        }
        veranteriorobra = true;

        return null;
    }

    /**
     * Muestra los anteriores 5 proyectos cuando se inicia el simpletooglepanel
     * , si la búsqueda lleva algún parámetro se trae la obra o proyecto que se
     * esta buscando
     *
     * @return null
     */
    public String anteriorObra() {
        pagina = pagina - 1;
        int num = (pagina - 1) * 5;

        listaObraContrato = getSessionBeanCobra().getCobraService().encontrarObraxContratos(getContrato().getIntidcontrato(), buscarproyecto, num, 5, booltipocontratoconvenio);
        totalfilas = getSessionBeanCobra().getCobraService().getNumObraxContratos(getContrato().getIntidcontrato(), buscarproyecto, booltipocontratoconvenio);

        if (totalfilas <= 5) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 5;
            if (totalfilas % 5 > 0) {
                totalpaginas++;
            }
        }

        if (pagina > 1) {
            veranteriorobra = true;
        } else {
            veranteriorobra = false;
        }
        verultimoobra = true;
        return null;
    }

    /**
     * Muestra los último 5 proyectos cuando se inicia el simpletooglepanel , si
     * la búsqueda lleva algún parámetro se trae la obra o proyecto que se esta
     * buscando
     *
     * @return null
     */
    public String ultimoObra() {
        int num = totalfilas % 5;

        listaObraContrato = getSessionBeanCobra().getCobraService().encontrarObraxContratos(getContrato().getIntidcontrato(), buscarproyecto, totalfilas - num, 5, booltipocontratoconvenio);
        totalfilas = getSessionBeanCobra().getCobraService().getNumObraxContratos(getContrato().getIntidcontrato(), buscarproyecto, booltipocontratoconvenio);

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
            verultimoobra = true;
        } else {
            verultimoobra = false;
        }
        veranteriorobra = true;

        return null;
    }

    /**
     * Cuando se inicia desde el botón lateral Nuevo contrato consultoría, se
     * inicializan algunas variables para darle el comportamiento de consultoria
     *
     * @param event
     */
    public void iniciarContrConsultoria(ActionEvent event) {
        //contrato=null;
        listacontratos.clear();
        listacontratoscontratista.clear();
        limpiarContrato();
        boolcontrconsultoria = true;
        booltipocontratoconvenio = false;
        getSessionBeanCobra().getCobraService().setAsoContratoCrear(false);
        tipoContCon = "Contrato consultoría";
    }

    /**
     * Cuando se inicia el detalle o consulta de consultoría, se inicializan
     * algunas variables para darle el comportamiento de consultoria
     *
     * @return Formulario consultarContratoConvenioDetalle
     */
    public String contrConsultoria() {
        //contrato = null;
        // limpiarContrato();
        listacontratos.clear();
        listacontratoscontratista.clear();
        tipoContCon = "Contrato consultoría";
        booltipocontratoconvenio = false;
        boolcontrconsultoria = true;
        filtrocontrato.setBooltienehijo(false);
        filtrocontrato.setBooltipocontconv(false);
        filtrocontrato.setBoolcontrconsultoria(true);
        primeroDetcontrato();
        return "consultarContratoConvenio";

//        listacontratos.clear();
//        tipoContCon = "Convenio";
//        booltipocontratoconvenio = true;
//        filtrocontrato.setBoolcontrconsultoria(false);
//        filtrocontrato.setBooltienehijo(false);
//        filtrocontrato.setBooltipocontconv(true);
//        primeroDetcontrato();
    }

    /**
     * LLenar la lista que se modifica desde el modificar contrato
     *
     * @return
     */
    public String modificarPoliza() {
        if (polizacontrato.getStrnumpoliza() != null && polizacontrato.getStrnumpoliza().compareTo("") != 0 && polizacontrato.getDatefechavecimiento() != null) {
            polizacontrato.setAseguradora(getSessionBeanCobra().getCobraService().encontrarAseguradoraPorId(polizacontrato.getAseguradora().getIntnumnitentidad()));
            polizacontrato.setTipopoliza(getSessionBeanCobra().getCobraService().encontrarTipoPolizaPorId(tipointpoli));
            polizacontrato.setContrato(contrato);
            polizacontrato.setStrdocpoliza("");
            listaPolizacontratos.set(indicepolizamodificar, polizacontrato);
            polizacontrato = new Polizacontrato();
            polizacontrato.setTipopoliza(new Tipopoliza());
            polizacontrato.setAseguradora(new Aseguradora());
            polizacontrato.setContrato(new Contrato());
        } else {
            FacesUtils.addErrorMessage("Debe diligenciar los datos requeridos para la póliza.");
        }
        return null;
    }

    /**
     * Mostrar los datos que fueron creados en contratista
     *
     * @return
     */
    public String cambiarboolcrearcontratista() {
        boolcrearcontratista = true;
        limpiarContratista();
        return null;
    }

    public String cambiarboolcrearpoliza() {
        boolcrearpoliza = true;
        //limpiarContratista();
        return null;
    }

    /**
     * Limpiar Giro directo para iniciar este formulario
     *
     * @return
     */
    public String limpiarGirodirecto() {
        movimientocontrato = new Movimientocontrato();
        movimientocontrato.setOrdendepago(new Ordendepago());
        movimientocontrato.setTipomovimiento(new Tipomovimiento());
        llenarOrdenpago();
        llenarTipomovimiento();
        llenarGirodirecto();
        return null;
    }

    /**
     * Listar Ordenes de Pago por encargo fiducuario, enviandole el encargo
     * fiduciario del contrato
     */
    public void llenarOrdenpago() {
        if (getContrato().getEncargofiduciario() != null && getContrato().getEncargofiduciario().getIntnumencargofiduciario() != 0) {
            List<Ordendepago> listaordenpago = getSessionBeanCobra().getCobraService().encontrarOrdenPagoxEncargofiduciario(getContrato().getEncargofiduciario().getIntnumencargofiduciario());
            OrdenPagoitem = new SelectItem[listaordenpago.size()];
            int i = 0;
            for (Ordendepago orden : listaordenpago) {
                SelectItem c = new SelectItem(orden.getOidcodigoordenpago(), "N° OP " + orden.getStrordenpago() + " Fecha " + orden.getDatefechaordenpago() + " Vlr OP " + orden.getNumvlrordenpago());
                OrdenPagoitem[i++] = c;
            }

            if (listaordenpago.size() > 0) {
                booliniciargirodirecto = true;
            } else {
                booliniciargirodirecto = false;
            }
        }
    }

    /**
     * Listar en giro directo el tipo de movimiento
     */
    public void llenarTipomovimiento() {
        List<Tipomovimiento> listatipomovimiento = getSessionBeanCobra().getCobraService().encontrarTipomovimiento();
        TipoMovimientoitem = new SelectItem[listatipomovimiento.size()];
        int i = 0;
        for (Tipomovimiento tipo : listatipomovimiento) {
            SelectItem c = new SelectItem(tipo.getOidtipomovimiento(), tipo.getStrnombretipomovimiento());

            TipoMovimientoitem[i++] = c;
        }
    }

    /**
     * Mostrar los datos que fueron creados en Giro
     *
     * @return
     */
    public String cambiarboolcreargiro() {
        boolcreargiro = false;
        limpiarGirodirecto();
        return null;
    }

    /**
     * Guardar Giro Directo con sus validaciones pertinentes
     *
     * @return
     */
    public String guardarGirodirecto() {

        BigDecimal gastado = valomovimientoop().add(valomovimientocontrato());
        Ordendepago orden = getSessionBeanCobra().getCobraService().encontrarOrdendepagoxId(movimientocontrato.getOrdendepago().getOidcodigoordenpago());
        BigDecimal disponible = orden.getNumvlrordenpago().subtract(gastado);

        if (movimientocontrato.getTipomovimiento().getOidtipomovimiento() == 1) {
            if (movimientocontrato.getNumvlrgiro().compareTo(disponible) > 0) {
                FacesUtils.addErrorMessage("El valor del giro ($" + movimientocontrato.getNumvlrgiro() + ") es superior al valor disponible de la orden de pago ($" + disponible + ")");
                return null;
            }

        } else {
            if (movimientocontrato.getNumvlrgiro().compareTo(gastado) > 0) {
                FacesUtils.addErrorMessage("El valor de la devolución ($" + movimientocontrato.getNumvlrgiro() + ") es superior a lo que se ha girado de la orden de pago ($" + gastado + ")");
                return null;
            }
        }
        boolcreargiro = true;
        movimientocontrato.setContrato(getContrato());
        movimientocontrato.setDatefechacreacion(new Date());
        movimientocontrato.setDatefechamodificacion(new Date());
        movimientocontrato.setJsfUsuarioByIntusucreacion(getSessionBeanCobra().getUsuarioObra());
        movimientocontrato.setJsfUsuarioByIntusumodificacion(getSessionBeanCobra().getUsuarioObra());
        getSessionBeanCobra().getCobraService().guardarGirodirecto(movimientocontrato);
        FacesUtils.addInfoMessage("Los datos de giro directo se han guardado.");

        return null;
    }

    /**
     * LLenar y mostrar Giro directo {@link primeroGirodirec()}
     *
     * @return null
     */
    public String llenarGirodirecto() {
        if (palabraclave.length() != 0) {
            listaGirodirecto.clear();
        }
        primeroGirodirec();
        return null;
    }

    /**
     * Muestra los primeros 5 giros directos cuando se inicia el
     * simpletooglepanel , si la búsqueda lleva algún parámetro se trae el giro
     * directo que se esta buscando
     *
     * @return null
     */
    public String primeroGirodirec() {
        listaGirodirecto = getSessionBeanCobra().getCobraService().encontrarGirodirectoxConvenio(getContrato().getIntidcontrato(), palabraclave, 0, 5);
        totalfilas = getSessionBeanCobra().getCobraService().getNumGirodirectoxConvenio(getContrato().getIntidcontrato(), palabraclave);

        pagina = 1;
        if (totalfilas <= 5) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 5;
            if (totalfilas % 5 > 0) {
                totalpaginas++;
            }
        }
        veranteriorobra = false;
        if (totalpaginas > 1) {
            verultimoobra = true;
        } else {
            verultimoobra = false;
        }

        return null;
    }

    /**
     * Muestra los siguientes 5 giros directos cuando se inicia el
     * simpletooglepanel , si la búsqueda lleva algún parámetro se trae el giro
     * directo que se esta buscando
     *
     * @return null
     */
    public String siguienteGirodirec() {
        int num = (pagina) * 5;

        listaGirodirecto = getSessionBeanCobra().getCobraService().encontrarGirodirectoxConvenio(getContrato().getIntidcontrato(), palabraclave, num, 5);
        totalfilas = getSessionBeanCobra().getCobraService().getNumGirodirectoxConvenio(getContrato().getIntidcontrato(), palabraclave);

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
            verultimoobra = true;
        } else {
            verultimoobra = false;
        }
        veranteriorobra = true;

        return null;
    }

    /**
     * Muestra los anterior 5 giros directos cuando se inicia el
     * simpletooglepanel , si la búsqueda lleva algún parámetro se trae el giro
     * directo que se esta buscando
     *
     * @return null
     */
    public String anteriorGirodirec() {
        pagina = pagina - 1;
        int num = (pagina - 1) * 5;

        listaGirodirecto = getSessionBeanCobra().getCobraService().encontrarGirodirectoxConvenio(getContrato().getIntidcontrato(), palabraclave, num, 5);
        totalfilas = getSessionBeanCobra().getCobraService().getNumGirodirectoxConvenio(getContrato().getIntidcontrato(), palabraclave);

        if (totalfilas <= 5) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 5;
            if (totalfilas % 5 > 0) {
                totalpaginas++;
            }
        }

        if (pagina > 1) {
            veranteriorobra = true;
        } else {
            veranteriorobra = false;
        }
        verultimoobra = true;
        return null;
    }

    /**
     * Muestra los último 5 giros directos cuando se inicia el simpletooglepanel
     * , si la búsqueda lleva algún parámetro se trae el giro directo que se
     * esta buscando
     *
     * @return null
     */
    public String ultimoGirodirec() {
        int num = totalfilas % 5;

        listaGirodirecto = getSessionBeanCobra().getCobraService().encontrarGirodirectoxConvenio(getContrato().getIntidcontrato(), palabraclave, totalfilas - num, 5);
        totalfilas = getSessionBeanCobra().getCobraService().getNumGirodirectoxConvenio(getContrato().getIntidcontrato(), palabraclave);

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
            verultimoobra = true;
        } else {
            verultimoobra = false;
        }
        veranteriorobra = true;

        return null;
    }

    /**
     * Se elige el giro directo que se quiere editar
     *
     *
     * @param 
     * @return null
     */
    public String editarGirodirecto() {
        booleditargirodirecto = true;

        movimientocontrato = (Movimientocontrato) tablaGirodirecto.getRowData();

        return null;
    }

    /**
     * Eliminar el giro directo previamente seleccionado
     *
     * @param
     * @return
     */
    public String eliminarGirodirecto() {
        movimientocontrato = (Movimientocontrato) tablaGirodirecto.getRowData();
        listaGirodirecto.remove(movimientocontrato);
        if (movimientocontrato.getIntidnumero() != 0) {
            getSessionBeanCobra().getCobraService().borrarMovimientocontrato(movimientocontrato);
        }
        return null;
    }

    /**
     * Obtener el valor del movimiento por orden de pago, enviando como
     * parámetro el codigo de orden pago
     *
     * @return valorMovimientoxOrdenpago
     */
    public BigDecimal valomovimientoop() {
        return getSessionBeanCobra().getCobraService().valorMovimientoxOrdenpago(movimientocontrato.getOrdendepago().getOidcodigoordenpago());

    }

    /**
     * Obtener el valor del movimiento por contrato, enviando como parámetro, el
     * codigo del contrato y el codigo de orden pago
     *
     * @return valorMovimientoxOrdenpago
     */
    public BigDecimal valomovimientocontrato() {
        return getSessionBeanCobra().getCobraService().valorMovimientoContratoxContrato(getContrato().getIntidcontrato(), movimientocontrato.getOrdendepago().getOidcodigoordenpago());

    }

    /**
     * Iniciar Evolución Contrato desde el botón lateral del detalle del
     * contrato
     *
     * @return Formulario EvolucionContrato
     */
    public String iniciarEvolucionContrato() {
        llenarplanificacionpagos();
        return "evoluvionContrato";

    }

    /**
     * Mostrar las modificaciones del contrato si este lo tiene
     *
     * @return
     */
    public String llenarModifacionesContrato() {
        listaModificarContrato = getSessionBeanCobra().getCobraService().encontrarModificacionContratoxContrato(getContrato().getIntidcontrato());
        int i = 0;
        while (i < listaModificarContrato.size()) {
            listaModificarContrato.get(i).setListatiposmodificacion(
                    getSessionBeanCobra().getCobraService().encontrarTipomodixModificacionContrato(listaModificarContrato.get(i).getOidmodificacion()));
            i++;
        }

        return null;
    }

    /**
     * Si la entidad pertenece a MINISTERIO DE EDUCACION NACIONAL o INSTITUTO
     * NACIONAL DE VIAS se setean setIntentidadconvenio
     *
     * @return
     */
    public String crearProyecto() {
        String ret = getIngresarNuevaObra().limpiarobra();
        List<Tercero> listentcontxcontratistaconvenio = new ArrayList<Tercero>();

        if (contrato.getContratista() != null) {

            listentcontxcontratistaconvenio = getSessionBeanCobra().getCobraService().obtenerEntidadContratantexContratista(contrato.getContratista().getIntcodigocontratista());

            if (!listentcontxcontratistaconvenio.isEmpty() && listentcontxcontratistaconvenio.size() == 1) {
                getIngresarNuevaObra().getObranueva().setContrato(contrato);
                getIngresarNuevaObra().getObranueva().setTercero(listentcontxcontratistaconvenio.get(0));
                getIngresarNuevaObra().setDesdeconvenio(true);
                if (listentcontxcontratistaconvenio.get(0).getIntcodigo() == 5212) {
                    getIngresarNuevaObra().getEntidadConvenio().setIntentidadconvenio(1);

                }
                if (listentcontxcontratistaconvenio.get(0).getIntcodigo() == 5229) {
                    getIngresarNuevaObra().getEntidadConvenio().setIntentidadconvenio(2);
                }
                return ret;
            } else {
                FacesUtils.addErrorMessage(Propiedad.getValor("errorconfiguracionterceroconvenio"));

                return ret;
            }
        } else {
            FacesUtils.addErrorMessage(Propiedad.getValor("errorconfiguracionterceroconvenio"));
            return ret;
        }
    }

    /**
     * Mostrar los docuementos del contrato seleccionado
     *
     * @return Formulario DocumentoContrato
     */
    public String documentocontrato() {
        llenarDocumentoContrato();
        llenarTipodocumentos();
        iniciardocuContrato();
        return "documentocontrato";
    }

    /**
     * Variables necesarias para mostrar los documentos de un contrato
     */
    public void inicializarVariablesdocu() {
        this.documentoobra = new Documentoobra();
        this.documentoobra.setTipodocumento(new Tipodocumento(1, "", true));
        this.documentoobra.setDatefecha(new Date());
        this.cargadorDocumento = new CargadorArchivosWeb();
        llenarDocumentoContrato();
    }

    /**
     * Llenar lista con los documento del contrato seleccionado
     */
    public void llenarDocumentoContrato() {
        getSessionBeanCobra().getCobraService().setListaDocumentosContrato(getSessionBeanCobra().getCobraService().encontrarDocumentosContrato(contrato.getIntidcontrato()));
    }

    /**
     * Variables necesarias para mostrar los documentos de un contrato
     */
    public void iniciardocuContrato() {
        this.documentoobra = new Documentoobra();
        this.documentoobra.setTipodocumento(new Tipodocumento(1, "", true));
        this.documentoobra.setDatefecha(new Date());
        this.cargadorDocumento = new CargadorArchivosWeb();
    }

    /**
     * eliminar el documento seleccionado
     */
    public void bt_eliminar_documento_action(int filaSeleccionada) {
        SessionBeanCobra sbc = (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
        Documentoobra doc = sbc.getCobraService().getListaDocumentosContrato().get(filaSeleccionada);
        getSessionBeanCobra().getCobraService().borrarDocumento(doc);
        getSessionBeanCobra().getCobraService().getListaDocumentosContrato().remove(doc);
    }

    /**
     * Agregar el documento y guardarlo
     *
     * @return
     */
    public String bt_agregar_documento_action() {
        try {
            for (Tipodocumento td : this.listatipodocumentos) {
                if (td.getInttipodoc() == this.documentoobra.getTipodocumento().getInttipodoc()) {
                    this.documentoobra.setTipodocumento(td);
                    break;
                }
            }
            this.documentoobra.setContrato(contrato);

            subirDocumento();
            getSessionBeanCobra().getCobraService().guardarDocumento(this.documentoobra);
            getSessionBeanCobra().getCobraService().getListaDocumentosContrato().add(documentoobra);
            inicializarVariablesdocu();
        } catch (ArchivoExistenteException ex) {
            Logger.getLogger(NuevoContratoBasico.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    Propiedad.getValor("docexistenteerror"), ""));
        }
        return null;
    }

    /**
     * invocar el reporte de convenio enviando el id del mismo
     *
     * @return
     */
    public String reporteProyectosConvenio() {
        try {
            if (contrato.getContratista().getIntcodigocontratista() != 3120) {

                if (contrato.getContratista().getIntcodigocontratista() != 7860) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteexcelproyectosconvenio") + contrato.getIntidcontrato());
                } else {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteexcelproyectosconvenioambiente") + contrato.getIntidcontrato());
                }
            } else {
                FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteexcelproyectosconvenioeducacion") + contrato.getIntidcontrato());
            }

        } catch (IOException ex) {
            Logger.getLogger(Contrato.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * Permite actualizar la entidad de un contrato
     *
     * @return void
     */
    public void actualizarEntidadContrato() {
        comboEntidadesContratoguardar();
        getSessionBeanCobra().getCobraService().guardarContrato(contrato);
        FacesUtils.addInfoMessage("Se actualizo la entidad del contrato");
    }

    /**
     * Metodo Utilizado para habilitar los botones de Guardar y cancelar el
     * objeto del contrato
     *
     * @return void
     */
    public void habilitarBotonModificar() {
        habilitarmodificar = false;
        modificartxt = true;
    }

    /**
     * Método utilizado Guardar la Modificacion el objeto del contrato.
     *
     * @return NVoid
     */
    public void guardarModficiacionObjetoContrato() {
        habilitarmodificar = true;
        modificartxt = false;
        getSessionBeanCobra().getCobraService().guardarContrato(getContrato());
        FacesUtils.addInfoMessage("Se actualizo correctamente el contrato");
    }

    /**
     * Metodo Utilizado para habilitar el boton Modificar
     *
     * @return void
     */
    public void cancelarModificacionObjeto() {
        habilitarmodificar = true;
        modificartxt = false;
    }

    /**
     * Metodo Utilizado para cancelar el numero del contrato
     *
     * @return void
     */
    public void cancelarModifcacionNumeroContrato() {
        habilitarModificarNumero = true;
        habilitarGuardarNumeroContrato = false;
    }

    /**
     * Metodo Utilizado para habilitar el boton Modificar el numero del contrato
     *
     * @return void
     */
    public void habilitarBotonModificarNumero() {

        habilitarModificarNumero = false;
        habilitarGuardarNumeroContrato = true;

    }

    /**
     * Metodo Utilizado actualizar el numero del contrato
     *
     * @return void
     */
    public void actualizarContato() {
        getSessionBeanCobra().getCobraService().guardarContrato(getContrato());
        FacesUtils.addInfoMessage("Se actualizo correctamente el contrato");
        habilitarModificarNumero = true;
        habilitarGuardarNumeroContrato = false;
    }

    /**
     * Metodo Utilizado para habilitar los botones de Guardar y cancelar el
     * objeto del contrato
     *
     * @return void
     */
    public void habilitarBotonModificarContrato() {
        habilitarBtnModificarcontrato = false;
        habilitarBtnGuardarCancelarContrato = true;
    }

    /**
     * Método utilizado Guardar la Modificacion el objeto del contrato.
     *
     * @return NVoid
     */
    public void guardarModficiacionContrato() {
        comboEntidadesContratoguardar();
        habilitarBtnModificarcontrato = true;
        habilitarBtnGuardarCancelarContrato = false;
        getSessionBeanCobra().getCobraService().guardarContrato(getContrato());
        FacesUtils.addInfoMessage("Se actualizo correctamente el contrato");
    }

    /**
     * Metodo Utilizado para habilitar el boton Modificar
     *
     * @return void
     */
    public void cancelarModificacionContrato() {
        habilitarBtnModificarcontrato = true;
        habilitarBtnGuardarCancelarContrato = false;
    }
    /**
     * Llena el combo de modalidad de contratista.
     */
    private List<Modalidadcontratista> listModalidadContratista;
    /**
     * Llena el Estado del convenio
     */
    private List<Estadoconvenio> listEstadoConvenio;
    /**
     * Select item listaestadoconvenio
     *
     * @return
     */
    private SelectItem[] estadoConvenioOption;

    public SelectItem[] getEstadoConvenioOption() {
        return estadoConvenioOption;
    }

    public void setEstadoConvenioOption(SelectItem[] estadoConvenioOption) {
        this.estadoConvenioOption = estadoConvenioOption;
    }

    public List<Estadoconvenio> getListEstadoConvenio() {
        return listEstadoConvenio;
    }

    public void setListEstadoConvenio(List<Estadoconvenio> listEstadoConvenio) {
        this.listEstadoConvenio = listEstadoConvenio;
    }

    public List<Modalidadcontratista> getListModalidadContratista() {
        return listModalidadContratista;
    }

    public void setListModalidadContratista(List<Modalidadcontratista> listModalidadContratista) {
        this.listModalidadContratista = listModalidadContratista;
    }

    public void llenarModalidadContratista() {
        listModalidadContratista = getSessionBeanCobra().getCobraService().encontrarModalidadContratista();
        modalidadContratista = new SelectItem[listModalidadContratista.size()];
        int i = 0;
        for (Modalidadcontratista modalidad : listModalidadContratista) {
            SelectItem itModalidad = new SelectItem(modalidad.getIntidmodalidadcontratista(), modalidad.getStrdescripcionmodalidad());
            if (i == 0) {
                contrato.getModalidadcontratista().setIntidmodalidadcontratista(modalidad.getIntidmodalidadcontratista());
            }
            modalidadContratista[i++] = itModalidad;
        }
    }

    public String irApaginaconvenio() {
        if (Propiedad.getValor("conplanoperativo").equals("true")) {
            panelPantalla = 1;
            return "nuevoConvenioPo";
        }
        return "nuevoContrato";
    }

    /**
     * @return the panelPantalla
     */
    public int getPanelPantalla() {
        return panelPantalla;
    }

    /**
     * @param panelPantalla the panelPantalla to set
     */
    public void setPanelPantalla(int panelPantalla) {
        this.panelPantalla = panelPantalla;
    }
    private Boolean boolpruea = false;

    public Boolean getBoolpruea() {
        return boolpruea;
    }

    public void setBoolpruea(Boolean boolpruea) {
        this.boolpruea = boolpruea;
    }

    /*
     * metodo que se encarga de actualizar el panel actual de la pantalla
     * @param panelPantalla int
     */
    public void actualizarPanel(int panelPantalla) {
//       variable para esconder los botones que llaman a los reportes
        boolreporte = false;
        this.panelPantalla = panelPantalla;
        switch (panelPantalla) {
            case 1:
                variabletitulo = Propiedad.getValor("primerodatosbasicos");
                infogeneralcrearconvenio = Propiedad.getValor("infogeneralcrearconveniodb");

                break;
            case 2:
                variabletitulo = Propiedad.getValor("segundoplanoperativo");
                boolpruea = true;
//                infogeneralcrearconvenio = Propiedad.getValor("infogeneralcrearconveniodb");
                break;
            case 3:
                variabletitulo = Propiedad.getValor("terceropolizas");
//                infogeneralcrearconvenio = Propiedad.getValor("infogeneralcrearconveniodb");
                break;
            case 4:
                variabletitulo = Propiedad.getValor("cuartoformapago");
                infogeneralcrearconvenio = Propiedad.getValor("infogeneralcrearconveniodb");
                break;
            case 5:
                variabletitulo = Propiedad.getValor("quintodocumento");
//                infogeneralcrearconvenio = Propiedad.getValor("infogeneralcrearconveniodb");
                break;

        }

    }
    /*
     * Metodo que muestra la subpantalla del plan operativo de acuerdo a la opción seleccionada
     * @void
     */

    public void actualizarSubpantallaPlanOperativo(int subPantalla) {
        this.setSubpantalla(subPantalla);
    }

    /**
     * Metodo que llena los estados del convenio
     *
     * @return void
     */
    public void llenarEstadoConvenio() {
        listEstadoConvenio = getSessionBeanCobra().getCobraService().encontrarEstadoConvenio();
        estadoConvenioOption = new SelectItem[listEstadoConvenio.size()];
        int i = 0;
        for (Estadoconvenio estado : listEstadoConvenio) {
            SelectItem itEstado = new SelectItem(estado.getIdestadoconvenio(), estado.getStrestadoconv());
            estadoConvenioOption[i++] = itEstado;
        }

    }

    /*
     *Metodo que se encarga de guardar el convenio en estado en estructuración.
     * 
     * @return void
     */
    public void guardarBorradorConvenio(Boolean formaguradarconvpo) {
        validardatosbasicosplano = 0;
//      Adicionando una forma de pago por defecto para que no saque error el sistema al intentar validarlo
        contrato.setFormapago(new Formapago(1, null, true));
        contrato.setTercero(null);
//      Si el contrato no se ha creado que guarde en fecha de creacion si no en fechamodificacion
        if (contrato.getDatefechacreacion() != null) {
            contrato.setDatefechamodificacion(new Date());
        } else {
            contrato.setDatefechacreacion(new Date());
        }

//      Asignandole el valor del conatrato en recursos terceros
        contrato.setNumrecursostercero(contrato.getNumvlrcontrato());
        contrato.setNumrecursosch(BigDecimal.ZERO);
        if (contrato.getIntduraciondias() > 0) {
            if (contrato.getFechaactaini() != null) {
                if (contrato.getFechaactaini().compareTo(contrato.getDatefechaini()) >= 0 && contrato.getFechaactaini().compareTo(contrato.getDatefechafin()) <= 0) {

                    //          Asisganicion de estado de obra y de tipo contrato quemados
                    contrato.setTipoestadobra(new Tipoestadobra(1));
                    contrato.setTipocontrato(new Tipocontrato(1, null));

//          Condicion si el cotrato no esta creado se le pone el id del usuario de la creacion en caso que este ya creado
//          entonces poner el id del usuario quien modifico. 
                    if (contrato.getJsfUsuarioByIntusucreacion() != null) {
                        contrato.setJsfUsuarioByIntusumodificacion(getSessionBeanCobra().getUsuarioObra());
                    } else {
                        contrato.setJsfUsuarioByIntusucreacion(getSessionBeanCobra().getUsuarioObra());
                    }
                    contrato.setTipocontratoconsultoria(new Tipocontratoconsultoria(1));
                    contrato.setNumvlrsumahijos(BigDecimal.ZERO);
                    contrato.setBooltipocontratoconvenio(true);
                    contrato.setBooleantienehijos(true);
                    contrato.setPeriodoevento(new Periodoevento(1));
                    contrato.setIntcantproyectos(0);
                    contrato.setNumvlrsumaproyectos(BigDecimal.ZERO);
                    contrato.setEstadoconvenio(new Estadoconvenio(1));
                    contrato.setPolizacontratos(new LinkedHashSet(listapolizas));
                    contrato.setBoolplanoperativo(true);
                    contrato.setEncargofiduciario(null);
                    contrato.setModalidadcontratista(null);
                    if (formaguradarconvpo != true) {
                        if (!listadocumentos.isEmpty()) {
                            validadcionGuardarContrato();
                        } else {
                            getSessionBeanCobra().getCobraService().guardarContrato(contrato);
                            FacesUtils.addInfoMessage("losdatossehanguardado");
                        }
                        contrato = new Contrato();
                    }
                } else {
                    validardatosbasicosplano = 1;
                    FacesUtils.addErrorMessage("fechadesuscripcionplano");
                }
            } else {
                validardatosbasicosplano = 2;
                FacesUtils.addErrorMessage("fechadesuscripcionvalida");
            }
        } else {
            validardatosbasicosplano = 3;
            FacesUtils.addErrorMessage("validarfechafin");
        }
    }

    /*
     *metodo que se encarga de guardar el convenio
     * en estado en ejecución.
     * 
     */
    public void finalizarGuardado() {
        guardadoconexito = 0;
        tipomensajeerror = 0;
        totalfuenteconvenio = new BigDecimal(BigInteger.ZERO);
        faltafuenteconvenio = new BigDecimal(BigInteger.ZERO);
//        Se llama el metodo de guardar borrador para que valide la información inicial
        this.guardarBorradorConvenio(true);
        if (!recursosconvenio.getLstFuentesRecursos().isEmpty()) {
            guardadoconexito = 1;
            getSessionBeanCobra().getCobraService().guardarContrato(contrato);
//            Recorriendo la lista de fuentes de recursos para sumar los valores ingresados
            for (Fuenterecursosconvenio fuenterecurso : recursosconvenio.getLstFuentesRecursos()) {
                totalfuenteconvenio = totalfuenteconvenio.add(fuenterecurso.getOtrasreservas()).add(fuenterecurso.getValorcuotagerencia()).add(fuenterecurso.getReservaiva());
            }
            if (totalfuenteconvenio.compareTo(contrato.getNumvlrcontrato()) == 0) {

                guardadoconexito = 1;
                getSessionBeanCobra().getCobraService().guardarContrato(contrato);
                contrato = new Contrato();

            } else {
                guardadoconexito = 0;
                tipomensajeerror = 2;
                faltafuenteconvenio = contrato.getNumvlrcontrato().subtract(totalfuenteconvenio);
                FacesUtils.addErrorMessage("fuenterecursovlrconvenio"
                        + contrato.getNumvlrcontrato().subtract(totalfuenteconvenio));
            }
        } else {
            guardadoconexito = 0;
            tipomensajeerror = 1;
            FacesUtils.addErrorMessage("numfuenterecurso");
        }

    }

    public Tercero obtenerTerceroXcodigo(int codigo) {
        for (Tercero ter : lstentidades) {
            if (ter.getIntcodigo() == codigo) {
                return ter;
            }
        }
        return null;
    }

    /*
     *metodo que  carga las entidades de fonade en la lista de seleccion
     *      
     */
    public void llenarEntidades() {
        lstentidades = getSessionBeanCobra().getCobraService().encontrarTercerosxTiposolicitante(2);
//        setEntidades(new SelectItem[lstentidades.size()]);
//        int i = 0;
//        for (Tercero tercero : lstentidades) {
//            SelectItem itTercero = new SelectItem(tercero.getIntcodigo(), tercero.getStrnombrecompleto());
//            getEntidades()[i++] = itTercero;
//        }

    }

    /*
     *metodo que  carga los gerentes en la lista de seleccion
     *      
     */
    public void llenarGerentes() {
        List<Tercero> lstgerentesConvenio = new ArrayList<Tercero>();
        lstgerentesConvenio = getSessionBeanCobra().getCobraService().encontrarGerentesConvenio();
        setGerentesDeConvenio(new SelectItem[lstgerentesConvenio.size()]);
        int i = 0;
        for (Tercero gerenteConvenio : lstgerentesConvenio) {
            SelectItem itGerenteConvenio = new SelectItem(gerenteConvenio.getIntcodigo(), gerenteConvenio.getStrnombrecompleto());
            getGerentesDeConvenio()[i++] = itGerenteConvenio;
        }

    }

    /**
     * @return the gerentesDeConvenio
     */
    public SelectItem[] getGerentesDeConvenio() {
        return gerentesDeConvenio;
    }

    /**
     * @param gerentesDeConvenio the gerentesDeConvenio to set
     */
    public void setGerentesDeConvenio(SelectItem[] gerentesDeConvenio) {
        this.gerentesDeConvenio = gerentesDeConvenio;
    }

    /**
     * @return the variabletitulo
     */
    public String getVariabletitulo() {
        return variabletitulo;
    }

    /**
     * @param variabletitulo the variabletitulo to set
     */
    public void setVariabletitulo(String variabletitulo) {
        this.variabletitulo = variabletitulo;
    }

    /**
     * @return the infogeneralcrearconvenio
     */
    public String getInfogeneralcrearconvenio() {
        return infogeneralcrearconvenio;
    }

    /**
     * @param infogeneralcrearconvenio the infogeneralcrearconvenio to set
     */
    public void setInfogeneralcrearconvenio(String infogeneralcrearconvenio) {
        this.infogeneralcrearconvenio = infogeneralcrearconvenio;
    }

    public List<Obra> getListaProyectosCovenio() {
        return listaProyectosCovenio;
    }

    public void setListaProyectosCovenio(List<Obra> listaProyectosCovenio) {
        this.listaProyectosCovenio = listaProyectosCovenio;
    }

    /**
     * Obtener variable para identificar tipo de reporte.
     *
     * @void
     */
    public void obtenerVariableReportePlanOperativo() {
    }

    /**
     * Reportes de plan operativo para el formato en PDF
     *
     * @void
     */
    public void reportesPlanOperativoPDF() {

        System.out.println("Debug - reportesPlanOperativoPDF");
        switch (tipoReporteVarTmp) {
            case 1:
                /*Reporte Consolidado*/
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteplanoperativoconsolidadopdf") + contrato.getIntidcontrato());
                } catch (IOException ex) {
                    Logger.getLogger(Contrato.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 2:
                /*Reporte Cronograma*/
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteplanoperativocronogramapdf") + contrato.getIntidcontrato());
                } catch (IOException ex) {
                    Logger.getLogger(Contrato.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 3:
                /*Reporte Presupuesto*/
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteplanoperativopresupuestopdf") + contrato.getIntidcontrato());
                } catch (IOException ex) {
                    Logger.getLogger(Contrato.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 4:
                /*Reporte Flujo de caja*/
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteplanoperativoflujocajapdf") + contrato.getIntidcontrato());
                } catch (IOException ex) {
                    Logger.getLogger(Contrato.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 5:
                /*Reporte Plan operativo*/
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteplanoperativoseccionplanoperativopdf") + contrato.getIntidcontrato());
                } catch (IOException ex) {
                    Logger.getLogger(Contrato.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 6:
                /*Reporte Plan de contratación*/
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteplanoperativoplancontratacionpdf") + contrato.getIntidcontrato());
                } catch (IOException ex) {
                    Logger.getLogger(Contrato.class.getName()).log(Level.SEVERE, null, ex);
                }
        }

    }

    /**
     * Reportes de plan operativo para el formato en PDF
     *
     * @void
     */
    public void reportesPlanOperativoXLS() {

        switch (tipoReporteVarTmp) {
            case 1:
                /*Reporte Consolidado*/
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteplanoperativoconsolidadoxls") + contrato.getIntidcontrato());
                } catch (IOException ex) {
                    Logger.getLogger(Contrato.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 2:
                /*Reporte Cronograma*/
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteplanoperativocronogramaxls") + contrato.getIntidcontrato());
                } catch (IOException ex) {
                    Logger.getLogger(Contrato.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 3:
                /*Reporte Presupuesto*/
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteplanoperativopresupuestoxls") + contrato.getIntidcontrato());
                } catch (IOException ex) {
                    Logger.getLogger(Contrato.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 4:
                /*Reporte Flujo de caja*/
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteplanoperativoflujocajaxls") + contrato.getIntidcontrato());
                } catch (IOException ex) {
                    Logger.getLogger(Contrato.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 5:
                /*Reporte Plan operativo*/
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteplanoperativoseccionplanoperativoxls") + contrato.getIntidcontrato());
                } catch (IOException ex) {
                    Logger.getLogger(Contrato.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 6:
                /*Reporte Plan de contratación*/
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteplanoperativoplancontratacionxls") + contrato.getIntidcontrato());
                } catch (IOException ex) {
                    Logger.getLogger(Contrato.class.getName()).log(Level.SEVERE, null, ex);
                }
        }

    }

    /**
     * Reportes de plan operativo para el formato en MPP
     *
     * @void
     */
    public void reportesPlanOperativoMPP() {

        switch (tipoReporteVarTmp) {
       
            case 2:
                /*Reporte Cronograma*/
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reporteplanoperativocronogramampp") + contrato.getIntidcontrato());
                } catch (IOException ex) {
                    Logger.getLogger(Contrato.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;            
        }

    }

    public String flujoCaja() {
        return "FlujoCaja";

    }

    public String verReportes() {
        boolreporte = true;
        return null;
    }

    /**
     * @return the subpantalla
     */
    public int getSubpantalla() {
        return subpantalla;
    }

    /**
     * @param subpantalla the subpantalla to set
     */
    public void setSubpantalla(int subpantalla) {
        this.subpantalla = subpantalla;
    }

    public String planOperativo() {
        try {
            ValidacionesConvenio.validarFechasPlanOperativo(getContrato().getFechaactaini(), getContrato().getDatefechaini(), getContrato().getDatefechafin());
            ValidacionesConvenio.validarValorPositivo(getContrato().getNumvlrcontrato(), "convenio");
            ValidacionesConvenio.validarTamanoLista(recursosconvenio.getLstFuentesRecursos(), "Fuente de Recursos");
            contrato.setFuenterecursosconvenios(new LinkedHashSet<Fuenterecursosconvenio>(recursosconvenio.getLstFuentesRecursos()));
            getSessionBeanCobra().getCobraGwtService().setContratoDto(CasteoGWT.castearContratoToContratoDTO(contrato));

            return "PlanOperativo";
        } catch (ConvenioException e) {
            FacesUtils.addErrorMessage(e.getMessage());
        }
        return null;
    }

    public String irApaginaconvenioplanoperativo() {
        panelPantalla = 2;
        subpantalla = 2;
        variabletitulo = Propiedad.getValor("segundoplanoperativo");
        return "nuevoConvenioPo";
    }

    /*
     * metodo que se encarga de actualizar el contrato con los datos provenientes del plan operativo
     * @param ContratoDto Objeto convenio utilizado en GWT.     
     * 
     * @author Carlos Loaiza
     */
    public void actualizarContratodatosGwt(ContratoDTO contratodto) {
        contrato.setDatefechaini(contratodto.getDatefechaini());
        contrato.setDatefechafin(contratodto.getDatefechafin());
        contrato.setFechaactaini(contratodto.getDatefechaactaini());
        contrato.setStrnumcontrato(contratodto.getStrnumcontrato());
        contrato.setNumvlrcontrato(contratodto.getNumvlrcontrato());
        contrato.setDatefechacreacion(contratodto.getDatefechacreacion());
        contrato.setTextobjeto(contratodto.getTextobjeto());
        contrato.setIntduraciondias(contratodto.getIntduraciondias());

    }
}
