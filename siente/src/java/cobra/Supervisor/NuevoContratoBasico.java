/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.Supervisor;

import co.com.interkont.cobra.planoperativo.client.dto.ActividadobraDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ContratoDTO;
import co.com.interkont.cobra.planoperativo.exceptionspo.ConvenioException;
import co.com.interkont.cobra.planoperativo.exceptions.ValidacionesConvenio;
import co.com.interkont.cobra.to.Actividadobra;
import co.com.interkont.cobra.to.AreaContratista;
import co.com.interkont.cobra.to.Aseguradora;
import co.com.interkont.cobra.to.Cargo;
import co.com.interkont.cobra.to.Contratista;
import co.com.interkont.cobra.to.Contrato;
import co.com.interkont.cobra.to.Dependencia;
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
import co.com.interkont.cobra.to.Obrafuenterecursosconvenios;
import co.com.interkont.cobra.to.Ordendepago;
import co.com.interkont.cobra.to.Periodoevento;
import co.com.interkont.cobra.to.Planificacionpago;
import co.com.interkont.cobra.to.Polizacontrato;
import co.com.interkont.cobra.to.Relacioncontratojsfusuario;
import co.com.interkont.cobra.to.Relacionobrafuenterecursoscontrato;
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
import cobra.PlanOperativo.ProyectoPlanOperativo;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.richfaces.component.UIDataTable;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import co.com.interkont.cobra.marcologico.to.Contratoestrategia;
import co.com.interkont.cobra.marcologico.to.Estrategia;
import co.com.interkont.cobra.planoperativo.client.dto.DependenciaDTO;
import co.com.interkont.cobra.planoperativo.exceptionspo.ValidacionesPO;
import co.com.interkont.cobra.to.Componente;
import co.com.interkont.cobra.to.Contratocomponente;
import co.com.interkont.cobra.to.Itemflujocaja;
import co.com.interkont.cobra.to.Periodoflujocaja;
import co.com.interkont.cobra.to.Tipoimpactosocial;
import co.com.interkont.cobra.vista.VistaProyectoAvanceFisicoConvenio;
import cobra.MarcoLogico.MarcoLogicoBean;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.interkont.cobra.util.CobraUtil;
import java.security.SecureRandom;
import java.util.HashSet;

/**
 * <p>
 * Page bean that corresponds to a similarly named JSP page. This class contains
 * component definitions (and initialization code) for all components that you
 * have defined on this page, as well as lifecycle methods and event handlers
 * where you may add behavior to respond to incoming events.</p>
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
    /**
     * Listado de pólizas a eliminar del contrato
     */
    private List<Polizacontrato> listaPolizasEliminar = new ArrayList<Polizacontrato>();

    public List<Polizacontrato> getListaPolizasEliminar() {
        return listaPolizasEliminar;
    }

    public void setListaPolizasEliminar(List<Polizacontrato> listaPolizasEliminar) {
        this.listaPolizasEliminar = listaPolizasEliminar;
    }
    private int verEliminar = 0;
    /**
     * Objeto para acceder a los atributos de poliza
     */
    private Polizacontrato polizacontrato = new Polizacontrato();
    /**
     * Variable para mostrar Tipo de pólizas de un contrato
     */
    private List<Tipopoliza> TipoPolizas;
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
    private UIDataTable tablacontrapadrebindin2 = new UIDataTable();
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
    /*
     * Variable contrato seleccionado
     */
    private String contratoselect;
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
     * Binding creado para acceder a los datos de las filas de la tabla creada
     */
    private UIDataTable tablaContratoHijo = new UIDataTable();
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
     * Listado de avance fisido del convenio
     */
    private List<VistaProyectoAvanceFisicoConvenio> listaavancefisico;
    /**
     * Variable para mostrar el valor del convenio en la tabla avance fisico
     * convenio
     */
    private BigDecimal valorconvenio;
    /**
     * Variable para la suma de los proyectos asociados al convenio
     */
    private BigDecimal sumaproyectosconvenio = BigDecimal.ZERO;
    /**
     * Variable para esconder la tabla avance fisico
     */
    private boolean boolavanceproyectoconvenio = false;
    /**
     * Variable para calcular el avance fisico de convenio
     */
    private BigDecimal avancefisicoconvenio = BigDecimal.ZERO;
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
    private String buscarproyecto = " ";
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
    /*
     * Variable para listar los cotratos segun el tipo
     */
    private int varibaleTipo = 1;
    /*
     * Variable habilitar la modificacion del tipo de contrato 
     */
    public boolean modificartipocontrato = true;
    /*
     * Variable deshabilitar la modificacion del tipo de contrato 
     */
    public boolean habiitarmodificartipocontrato = false;
    private String numcontratotemporal;
    private Relacioncontratojsfusuario relacionContratoJSFUsuario = new Relacioncontratojsfusuario();
    private SelectItem[] tipocontratoselectitem;
    List<Actividadobra> lstTemporalActividades = new ArrayList<Actividadobra>();
    List<Actividadobra> lstActividadesEliminar = new ArrayList<Actividadobra>();
    public int mostrarContratoConvenio;
    private List<Tercero> lstgerentes = new ArrayList<Tercero>();
    private Tercero tercero = new Tercero();

    /*Variable para almancenar el tipo de Aporte para el la planificacion de pagos.
     * Esta puede ser en valor o en porcentaje.
     * 
     */
    private int tipoAporte;

    /**
     * Get the value of eliminarPeriodosFueraRango
     *
     * @return the value of eliminarPeriodosFueraRango
     */
    public boolean isEliminarPeriodosFueraRango() {
        return !periodoConveniosFueraRango.isEmpty();
    }
    /**
     * Variable para confirmar el guardado de borrador de convenio.
     */
    private boolean confirmaGuardarBorradorConvenio = false;

    /**
     * Get the value of confirmaGuardarBorradorConvenio
     *
     * @return the value of confirmaGuardarBorradorConvenio
     */
    public boolean isConfirmaGuardarBorradorConvenio() {
        return confirmaGuardarBorradorConvenio;
    }

    /**
     * Set the value of confirmaGuardarBorradorConvenio
     *
     * @param confirmaGuardarBorradorConvenio new value of
     * confirmaGuardarBorradorConvenio
     */
    public void setConfirmaGuardarBorradorConvenio(boolean confirmaGuardarBorradorConvenio) {
        this.confirmaGuardarBorradorConvenio = confirmaGuardarBorradorConvenio;
    }
    /**
     * Variable para confirmar el guardado con plan operativo.
     */
    private boolean confirmacionGuardado;

    /**
     * Get the value of confirmacionGuardado
     *
     * @return the value of confirmacionGuardado
     */
    public boolean isConfirmacionGuardado() {
        return confirmacionGuardado;
    }
    /**
     * Variable para saber a que estrategia ingreso el usuario
     */
    private int estrategia = 0;
    /**
     * Variable para que liste todas las entidades
     */
    private int entidades = 0;
    private int botonGuardado;
    /**
     * Variable para activar el {@link contratoPadreSelec()}
     */
    private int varmostrarseleccionconveniosuperior = 0;
    /*
     * Variable para habilitar el mensaje y Registrar el contratista
     */
    public boolean confirmacioncedula = false;

    private List<Componente> listacomponentes;

    private List<Componente> listasubcomponentes;

    private List<Contratocomponente> listacomponentesimpactados;

    private UIDataTable tablaComponentesImpactados = new UIDataTable();

    private Componente componenteImpactado = new Componente();

    private Componente subcomponenteImpactado = new Componente();

    /**
     * Get the value of botonGuaradado
     *
     * @return the value of botonGuaradado
     */
    public int getBotonGuardado() {
        return botonGuardado;
    }

    /**
     * Set the value of botonGuaradado
     *
     * @param botonGuardado new value of botonGuaradado
     */
    public void setBotonGuardado(int botonGuardado) {
        this.botonGuardado = botonGuardado;
    }

    /**
     * Set the value of confirmacionGuardado
     *
     * @param confirmacionGuardado new value of confirmacionGuardado
     */
    public void setConfirmacionGuardado(boolean confirmacionGuardado) {
        this.confirmacionGuardado = confirmacionGuardado;
    }
    private boolean validarPeriodoConveniosFueraRango;

    /**
     * Get the value of validarPeriodoConveniosFueraRango
     *
     * @return the value of validarPeriodoConveniosFueraRango
     */
    public boolean isValidarPeriodoConveniosFueraRango() {
        return validarPeriodoConveniosFueraRango;
    }

    /**
     * Set the value of validarPeriodoConveniosFueraRango
     *
     * @param validarPeriodoConveniosFueraRango new value of
     * validarPeriodoConveniosFueraRango
     */
    public void setValidarPeriodoConveniosFueraRango(boolean validarPeriodoConveniosFueraRango) {
        this.validarPeriodoConveniosFueraRango = validarPeriodoConveniosFueraRango;
    }
    List<Periodoflujocaja> periodoConveniosFueraRango = new ArrayList<Periodoflujocaja>();

    public List<Periodoflujocaja> getPeriodoConveniosFueraRango() {
        return periodoConveniosFueraRango;
    }

    public void setPeriodoConveniosFueraRango(List<Periodoflujocaja> periodoConveniosFueraRango) {
        this.periodoConveniosFueraRango = periodoConveniosFueraRango;
    }

    /**
     * Indica que el bean ha sido invocado desde la funcionalidad de nuevo
     * convenio
     */
    public List<Tercero> getLstgerentes() {
        return lstgerentes;
    }

    public void setLstgerentes(List<Tercero> lstgerentes) {
        this.lstgerentes = lstgerentes;
    }

    public Tercero getTercero() {
        return tercero;
    }

    public void setTercero(Tercero tercero) {
        this.tercero = tercero;
    }
    private boolean enNuevoConvenio;

    public boolean isEnNuevoConvenio() {
        return enNuevoConvenio;
    }

    public void setEnNuevoConvenio(boolean enNuevoConvenio) {
        this.enNuevoConvenio = enNuevoConvenio;
    }

    public Relacioncontratojsfusuario getRelacionContratoJSFUsuario() {
        return relacionContratoJSFUsuario;
    }

    public void setRelacionContratoJSFUsuario(Relacioncontratojsfusuario relacionContratoJSFUsuario) {
        this.relacionContratoJSFUsuario = relacionContratoJSFUsuario;
    }

    public String getNumcontratotemporal() {
        return numcontratotemporal;
    }

    public void setNumcontratotemporal(String numcontratotemporal) {
        this.numcontratotemporal = numcontratotemporal;
    }

    public SelectItem[] getTipocontratoselectitem() {
        return tipocontratoselectitem;
    }

    public void setTipocontratoselectitem(SelectItem[] tipocontratoselectitem) {
        this.tipocontratoselectitem = tipocontratoselectitem;
    }

    public int getVaribaleTipo() {
        return varibaleTipo;
    }

    public void setVaribaleTipo(int varibaleTipo) {
        this.varibaleTipo = varibaleTipo;
    }
    private Modalidadcontratista modalidadcontratista = new Modalidadcontratista();
    private boolean guardarborradorconvenio = false;
    private UIDataTable tablaDocumentos = new UIDataTable();

    public UIDataTable getTablaDocumentos() {
        return tablaDocumentos;
    }

    public void setTablaDocumentos(UIDataTable tablaDocumentos) {
        this.tablaDocumentos = tablaDocumentos;
    }

    public boolean isGuardarborradorconvenio() {
        return guardarborradorconvenio;
    }

    public void setGuardarborradorconvenio(boolean guardarborradorconvenio) {
        this.guardarborradorconvenio = guardarborradorconvenio;
    }

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
    private RecursosConvenio recursosconvenio = new RecursosConvenio();
    List<Actividadobra> lstTodasActividades = new ArrayList<Actividadobra>();

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

    public boolean getConfirmacioncedula() {
        return confirmacioncedula;
    }

    public void setConfirmacioncedula(boolean confirmacioncedula) {
        this.confirmacioncedula = confirmacioncedula;
    }

    public int getTipoAporte() {
        return tipoAporte;
    }

    public void setTipoAporte(int tipoAporte) {
        this.tipoAporte = tipoAporte;
    }
    private boolean booltipoaporte;

    public boolean isBooltipoaporte() {
        return booltipoaporte;
    }

    public void setBooltipoaporte(boolean booltipoaporte) {
        this.booltipoaporte = booltipoaporte;
    }

    public List<Componente> getListacomponentes() {
        return listacomponentes;
    }

    public void setListacomponentes(List<Componente> listacomponentes) {
        this.listacomponentes = listacomponentes;
    }

    public List<Componente> getListasubcomponentes() {
        return listasubcomponentes;
    }

    public void setListasubcomponentes(List<Componente> listasubcomponentes) {
        this.listasubcomponentes = listasubcomponentes;
    }

    public List<Contratocomponente> getListacomponentesimpactados() {
        return listacomponentesimpactados;
    }

    public void setListacomponentesimpactados(List<Contratocomponente> listacomponentesimpactados) {
        this.listacomponentesimpactados = listacomponentesimpactados;
    }

    public UIDataTable getTablaComponentesImpactados() {
        return tablaComponentesImpactados;
    }

    public void setTablaComponentesImpactados(UIDataTable tablaComponentesImpactados) {
        this.tablaComponentesImpactados = tablaComponentesImpactados;
    }

    public Componente getComponenteImpactado() {
        return componenteImpactado;
    }

    public void setComponenteImpactado(Componente componenteImpactado) {
        this.componenteImpactado = componenteImpactado;
    }

    public Componente getSubcomponenteImpactado() {
        return subcomponenteImpactado;
    }

    public void setSubcomponenteImpactado(Componente subcomponenteImpactado) {
        this.subcomponenteImpactado = subcomponenteImpactado;
    }

    /*
     * variables para realizar la carga del tipo de aporte
     */
    private String variabletitulo;
    private String infogeneralcrearconvenio;
    private int panelPantalla;
    //private List<Obra> listaProyectosConvenio;
    private int reportoption;
    private int subpantalla;
    private boolean puedeEditarValorFuentes = true;
    private List<Tipoimpactosocial> listimpactosocial;
    /**
     * Variable para cargar el tipo de documento ya sea para contrato o convenio
     */
    private int controlvisualizaciondocumento = 0;
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

    public List<Tipoimpactosocial> getListimpactosocial() {
        return listimpactosocial;
    }

    public void setListimpactosocial(List<Tipoimpactosocial> listimpactosocial) {
        this.listimpactosocial = listimpactosocial;
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

    public List<VistaProyectoAvanceFisicoConvenio> getListaavancefisico() {
        return listaavancefisico;
    }

    public void setListaavancefisico(List<VistaProyectoAvanceFisicoConvenio> listaavancefisico) {
        this.listaavancefisico = listaavancefisico;
    }

    public BigDecimal getValorconvenio() {
        return valorconvenio;
    }

    public void setValorconvenio(BigDecimal valorconvenio) {
        this.valorconvenio = valorconvenio;
    }

    public BigDecimal getSumaproyectosconvenio() {
        return sumaproyectosconvenio;
    }

    public void setSumaproyectosconvenio(BigDecimal sumaproyectosconvenio) {
        this.sumaproyectosconvenio = sumaproyectosconvenio;
    }

    public boolean isBoolavanceproyectoconvenio() {
        return boolavanceproyectoconvenio;
    }

    public void setBoolavanceproyectoconvenio(boolean boolavanceproyectoconvenio) {
        this.boolavanceproyectoconvenio = boolavanceproyectoconvenio;
    }

    public BigDecimal getAvancefisicoconvenio() {
        return avancefisicoconvenio;
    }

    public void setAvancefisicoconvenio(BigDecimal avancefisicoconvenio) {
        this.avancefisicoconvenio = avancefisicoconvenio;
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

    public UIDataTable getTablaContratoHijo() {
        return tablaContratoHijo;
    }

    public void setTablaContratoHijo(UIDataTable tablaContratoHijo) {
        this.tablaContratoHijo = tablaContratoHijo;
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

    public String getContratoselect() {
        return contratoselect;
    }

    public void setContratoselect(String contratoselect) {
        this.contratoselect = contratoselect;
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

    public UIDataTable getTablacontrapadrebindin2() {
        return tablacontrapadrebindin2;
    }

    public void setTablacontrapadrebindin2(UIDataTable tablacontrapadrebindin2) {
        this.tablacontrapadrebindin2 = tablacontrapadrebindin2;
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

    public List<Tipopoliza> getTipoPolizas() {
        return TipoPolizas;
    }

    public void setTipoPolizas(List<Tipopoliza> TipoPolizas) {
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

    public int getMostrarContratoConvenio() {
        return mostrarContratoConvenio;
    }

    public void setMostrarContratoConvenio(int mostrarContratoConvenio) {
        this.mostrarContratoConvenio = mostrarContratoConvenio;
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

    public boolean isModificartipocontrato() {
        return modificartipocontrato;
    }

    public void setModificartipocontrato(boolean modificartipocontrato) {
        this.modificartipocontrato = modificartipocontrato;
    }

    public boolean isHabiitarmodificartipocontrato() {
        return habiitarmodificartipocontrato;
    }

    public void setHabiitarmodificartipocontrato(boolean habiitarmodificartipocontrato) {
        this.habiitarmodificartipocontrato = habiitarmodificartipocontrato;
    }

    public int getEstrategia() {
        return estrategia;
    }

    public void setEstrategia(int estrategia) {
        this.estrategia = estrategia;
    }

    public int getVarmostrarseleccionconveniosuperior() {
        return varmostrarseleccionconveniosuperior;
    }

    public void setVarmostrarseleccionconveniosuperior(int varmostrarseleccionconveniosuperior) {
        this.varmostrarseleccionconveniosuperior = varmostrarseleccionconveniosuperior;
    }

    public int getControlvisualizaciondocumento() {
        return controlvisualizaciondocumento;
    }

    public void setControlvisualizaciondocumento(int controlvisualizaciondocumento) {
        this.controlvisualizaciondocumento = controlvisualizaciondocumento;
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
    private List<Tercero> lstentidades = new ArrayList<Tercero>();
    Map<Fuenterecursosconvenio, BigDecimal> mapaReembolsoConvenio = new HashMap<Fuenterecursosconvenio, BigDecimal>();

    @Override
    public void prerender() {
        if (getSessionBeanCobra().isCargarcontrato()) {
            actualizarContratodatosGwt(getSessionBeanCobra().getCobraGwtService().getContratoDto());
            getSessionBeanCobra().setCargarcontrato(false);
            getContrato().setVermensajeguardado(true);
            getContrato().setMensajeguardado("");
            switch (getSessionBeanCobra().getCobraGwtService().getNavegacion()) {
                case 1:
                case 3:
                case 5:
                    panelPantalla = getSessionBeanCobra().getCobraGwtService().getNavegacion();
                    actualizarPanel();
                    break;
                //Plan operativo y guardados
                case 2:
                    if (getSessionBeanCobra().getCobraGwtService().getGuardarconvenio() == 1) {
                        getFlujoCaja().setFlujoCajaIniciado(false);
                        guardarBorradorConvenioPO();
                    } else if (getSessionBeanCobra().getCobraGwtService().getGuardarconvenio() == 2) {
                        guardarFinalizarConvenioPO();
                    }
                    break;

                case 6:
                case 9:
                    panelPantalla = 2;
                    variabletitulo = Propiedad.getValor("segundoplanoperativo");
                    //boolpruea = true;

                    subpantalla = getSessionBeanCobra().getCobraGwtService().getNavegacion() / 3;
                    if (getSubpantalla() == 2) {
                        if (!getContrato().isModolecturaplanop()) {
                            getFlujoCaja().setFlujoCajaIniciado(false);
                            this.guardarBorradorConvenio();
                        }
                        lstTodasActividades.clear();
                        getFlujoCaja().getProyectosPlanOperativo().clear();
                        if (!getContrato().getActividadobras().isEmpty()) {
                            cargarActividadesConsultadas((Actividadobra) getContrato().getActividadobras().iterator().next());
                        }
                        getFlujoCaja().iniciarFlujoCaja();
                    }
                    break;

            }

        }

    }

    public void guardarBorradorConvenioPO() {

        planOperativo();
        try {
            String pagina = "/Supervisor/PlanO.xhtml";
            if (getSessionBeanCobra().getCobraGwtService().isFullScreen()) {
                pagina = "/Supervisor/PlanOFullScreen.xhtml";
            } else {
                pagina = "/Supervisor/PlanO.xhtml";
            }

            FacesContext.getCurrentInstance().getExternalContext().redirect("/" + Propiedad.getValor("versioncobra") + pagina);
        } catch (IOException ex) {
            Logger.getLogger(NuevoContratoBasico.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void guardarFinalizarConvenioPO() {

        finalizarGuardado(); // metodo para finalizar el guardado

        if (!isConfirmacionGuardado()) {// con la variable booleana se sabe si guardo satisfactoriamente o no. 

            try {
                planOperativo();
                FacesContext.getCurrentInstance().getExternalContext().redirect("/zoom/Supervisor/PlanO.xhtml");

            } catch (IOException ex) {
                Logger.getLogger(NuevoContratoBasico.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/zoom/Supervisor/consultarContratoConvenioDetalle.xhtml");

            } catch (IOException ex) {
                Logger.getLogger(NuevoContratoBasico.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * <p>
     * Construct a new Page bean instance.</p>
     */
    /* variables para el funcionamiento del plan operativo*/
    public NuevoContratoBasico() {
        llenarTipodocumentos();
        llenarPolizas();
        llenarEventos();
        llenarPeriodoxEvento();

        //***** Se debe prestar atencion a este if, ya que no estaban funcionando el precargue de algunos listados.  *************//
        // if (!Propiedad.getValor("conplanoperativo").equals("true")) {
        //limpiarContrato();
        llenarTipoContratoconsultoria();
        llenarTipoContrato();

        llenarFormaPago();
        llenarComboContratista();
        iniciarFiltroAvanzado();
        if (getSessionBeanCobra().getBundle().getString("aplicaContralorias").equals("true")) {
            llenarModalidadContratista();
        }
        // } else {
        //setRecursosconvenio(new RecursosConvenio(getContrato(), getSessionBeanCobra().getCobraService()));
        //listaProyectosConvenio = new ArrayList<Obra>();
        getFlujoCaja().setProyectosPlanOperativo(new ArrayList<ProyectoPlanOperativo>());
        variabletitulo = Propiedad.getValor("primerodatosbasicos");
        llenarEstadoConvenio();
        llenarEntidades();
        llenarGerentes();
        this.listaxTipocontratoselect();
        filtrocontrato.setEsadministrador(getSessionBeanCobra().getUsuarioService().validarPerteneceGrupoAdministrador(6));
        // }
        
        encontrarComponentesImpactados();
        
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
        listaPolizasEliminar = new ArrayList<Polizacontrato>();
        listaPolizacontratos = getSessionBeanCobra().getCobraService().encontrarPolizasxContrato(getContrato().getIntidcontrato());
        Poliza = new SelectItem[listaPolizacontratos.size()];
        int i = 0;
        for (Polizacontrato polCont : listaPolizacontratos) {
            SelectItem poli = new SelectItem(polCont.getIntidpolizacontrato(), polCont.getStrdescripcion());
            Poliza[i++] = poli;
        }
        if (!listaPolizacontratos.isEmpty()) {
            listapolizas.addAll(listaPolizacontratos);
        }
        return null;
    }
    /*
     * Según el usuario logueado se llenan las entidades o la entidad
     */

    /* public void llenarEntidadesContratantes() {
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

     }*/
    public void llenarEntidadesContratantes() {
        try {

            List<Tercero> lis = getSessionBeanCobra().getUsuarioService().encontrarEntidadesxtercero(getSessionBeanCobra().getUsuarioObra(), 6, booltipocontratoconvenio);
            //TerceroOption = new SelectItem[lis.size()];

            SelectItem itemTercero = new SelectItem();

            // Esta operación se hace para garantizar que si la lista tiene muchos Items, tenga un espacio mas para el item TODOS. 
            if (lis.size() == 1) {
                TerceroOption = new SelectItem[lis.size()];
            } else {
                TerceroOption = new SelectItem[(lis.size() + 1)];
            }

            int i = 0;

            //----------------------
            //-- Se hace esta codificación para asegurar que el primer item que carga en la vista es con todos los relacionados al usuario. 
            if (lis.size() > 1) {
                String primeraOpcion;
                if (isEnNuevoConvenio()) {
                    primeraOpcion = "Seleccione una Entidad";
                } else {
                    primeraOpcion = "Todos";
                }
                itemTercero = new SelectItem(primeraOpcion);
                TerceroOption[i++] = itemTercero;
                contrato.getTercero().setIntcodigo(-1);
                contrato.getTercero().setStrnombrecompleto(primeraOpcion);
            }
            //----------------------

            for (Tercero ter : lis) {
                itemTercero = new SelectItem(ter.getStrnombrecompleto());
                TerceroEntidadLista terce = new TerceroEntidadLista(ter.getIntcodigo(), itemTercero.getValue().toString());
                temp.add(terce);

                if (lis.size() == 1) {
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

        if (getSessionBeanCobra().getBundle().getString("aplicafonade").equals("true")) {
            filtrocontrato.setAplicaafonade(true);
        } else {
            filtrocontrato.setAplicaafonade(false);
        }

        if (tipoContCon.compareTo("Convenio") == 0) {
            filtrocontrato.setRaiz(true);
        } else {
            filtrocontrato.setRaiz(false);
        }

        if (comboEntidadesContratoguardar()) {

            primeroDetcontrato();

            if (getContrato().getTercero().getIntcodigo() != -1) {
                primeroDetcontratoContratista();
            }
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
            if (getContrato().getTercero().getStrnombrecompleto().toUpperCase().compareTo("TODOS") == 0) {
                getContrato().getTercero().setIntcodigo(-1);
                return true;
            }
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
        List<Tipocontrato> listipcon = getSessionBeanCobra().getCobraService().encontrarTipocontratos();
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
        TipoPolizas = getSessionBeanCobra().getCobraService().encontrarTiposPoliza();
    }

    /**
     * Método que remueve nticipo cuando hay un error
     *
     */
    public void removerAnticipo() {
        if (contrato.getFormapago().getIntidformapago() == 1 && !lisplanifiactapar.isEmpty()) {
            lisplanifiactapar.remove(lisplanifiactapar.size() - 1);
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
                                            removerAnticipo();
                                            lisplanifiactapar.get(i).setDatefechapago(new Date());
                                        } else {
                                            removerAnticipo();
                                            FacesUtils.addErrorMessage("Debe establecer una fecha para el pago del acta parcial.");
                                            return false;
                                        }
                                    }
                                    if (lisplanifiactapar.get(i).getNumvlrporcentage() == null || lisplanifiactapar.get(i).getNumvlrporcentage().compareTo(BigDecimal.valueOf(0.000001)) < 0) {
                                        removerAnticipo();
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
                                    removerAnticipo();
                                    if (bundle.getString("fechaformapago").equals("true")) {
                                        //removerAnticipo();
                                        FacesUtils.addErrorMessage("El valor de la suma de los porcentajes(" + total + "%) de las actas parciales difiere del 100%)");
                                        return false;
                                    }
                                } else {
                                    return true;
                                }

                            } else {
                                if (bundle.getString("fechaformapago").equals("true")) {
                                    removerAnticipo();
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
                            if (lisplanifiactapar.get(i).getNumvlrporcentage() == null || lisplanifiactapar.get(i).getNumvlrporcentage().compareTo(BigDecimal.valueOf(0.000001)) < 0) {

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
        boolean band = true;
        if (getContrato().getIntidcontrato() == 0) {
            if (getSessionBeanCobra().getCobraService().encontrarContratoPorNumero(getContrato().getStrnumcontrato()) != null) {
                band = false;
            }
        } else {
            ///Revisar si cambio el numero de contrato
            if (getNumcontratotemporal().compareTo(getContrato().getStrnumcontrato()) != 0) {
                if (getSessionBeanCobra().getCobraService().encontrarContratoPorNumero(getContrato().getStrnumcontrato()) != null) {
                    band = false;
                }
            }
        }

        if (band) {

            if (comboEntidadesContratoguardar()) {
                if (bundle.getString("plazoconacta").compareTo("true") == 0) {
                    if (contrato.getFechaactaini().compareTo(contrato.getDatefechaini()) >= 0) {
                    } else {
                        removerAnticipo();
                        FacesUtils.addErrorMessage("La Fecha de suscripción debe ser anterior o igual de la fecha de inicio");
                        return null;
                    }
                }
                if (contrato.getIntduraciondias() > 0) {
                    if (contrato.getContratista() == null) {
                        FacesUtils.addErrorMessage("Debe seleccionar ó crear un contratista.");
                        return null;
                    }
                    if (contrato.getNumvlrcontrato().compareTo(BigDecimal.ZERO) <= 0) {
                        removerAnticipo();
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
                                    removerAnticipo();
                                    FacesUtils.addErrorMessage("El valor del " + tipoContCon + " supera el valor del " + contratoselect + " superior");
                                    return null;
                                }
                            } else {
                                removerAnticipo();
                                // FacesUtils.addErrorMessage("las fechas del " + tipoContCon + " a crear deben estar dentro del rango del " + contratoselect + " superior");
                                return null;
                            }
                        } else {
                            removerAnticipo();
                            FacesUtils.addErrorMessage("Debe seleccionar el contrato o convenio padre al que pertenece el contrato a guardar.");
                            return null;
                        }
                    }
                    //getSessionBeanCobra().getCobraService().guardarContrato(contrato);
                    if (!booltipocontratoconvenio) {
                        if (filtrocontrato.getTipocontratoselect() == 0) {
                            removerAnticipo();
                            FacesUtils.addErrorMessage("Debe seleccionar la modalidad del contrato");
                            return null;
                        }
                        if (contrato.getTipocontrato().getInttipocontrato() == 0) {
                            removerAnticipo();
                            FacesUtils.addErrorMessage("Debe seleccionar el tipo de contrato");
                            return null;
                        }
                        if (filtrocontrato.getTipocontratoselect() == 2) {
                            if (contrato.getTipocontrato().getInttipocontrato() == 1 && contrato.getTipocontratoconsultoria().getIntidtipocontratoconsultoria() == 0) {
                                removerAnticipo();
                                FacesUtils.addErrorMessage("Debe seleccionar el tipo de contrato");
                                return null;
                            }
                        }
                    }
                    if (validarDiligenciamientoFormadePago()) {
                        contrato.setDatefechacreacion(new Date());
                        contrato.setDatefechamodificacion(new Date());
                        contrato.setDatefechaultimaprorroga(null);
                        contrato.setJsfUsuarioByIntusumodificacion(getSessionBeanCobra().getUsuarioObra());
                        contrato.setJsfUsuarioByIntusucreacion(getSessionBeanCobra().getUsuarioObra());
                        contrato.setTipoestadobra(new Tipoestadobra(1));
                        if (!boolcontrconsultoria && filtrocontrato.getTipocontratoselect() != 2) {
                            contrato.setTipocontratoconsultoria(new Tipocontratoconsultoria(1));
                        }
                        if (booltipocontratoconvenio) {
                            contrato.setBooleantienehijos(true);
                        }
                        contrato.setBooltipocontratoconvenio(booltipocontratoconvenio);
                        contrato.setNumvlrsumahijos(new BigDecimal(BigInteger.ZERO));
                        contrato.setPolizacontratos(new LinkedHashSet(listapolizas));
                        contrato.setIntcantproyectos(0);
                        contrato.setPeriodoevento(new Periodoevento(1));
                        //guarda el contrato siente en ejecucion
                        contrato.setEstadoconvenio(new Estadoconvenio(2));
                        if (lisplanifiactapar.size() > 0) {//Actas Parciales
                            contrato.setPlanificacionpagos(new LinkedHashSet(lisplanifiactapar));
                        }
                        if (getSessionBeanCobra().getBundle().getString("aplicaContralorias").equals("false")) {
                            contrato.setModalidadcontratista(null);
                        }
                        if (contrato.getTercero().getIntcodigo() == -1) {
                            removerAnticipo();
                            FacesUtils.addErrorMessage(bundle.getString("elegirtercero"));
                            return null;
                        }
                        if (!validacionFechasContrato()) {
                            removerAnticipo();
                            FacesUtils.addErrorMessage(bundle.getString("fechaactaunica"));
                            return null;
                        }
                        if (!validacionFechasContratoActasyAnticipo()) {
                            removerAnticipo();
                            FacesUtils.addErrorMessage(bundle.getString("fechaanticipo"));
                            return null;
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
                                removerAnticipo();
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
                    removerAnticipo();
                    FacesUtils.addErrorMessage("La Fecha de Fin Debe ser mayor o igual a la fecha de inicio");
                }
            } else {
                removerAnticipo();
                FacesUtils.addErrorMessage("Debe diligenciar una entidad contratante válida.");
            }
        } else {
            removerAnticipo();
            validardatosbasicosplano = 1;
            FacesUtils.addErrorMessage(bundle.getString("numerocontratoyaexiste"));
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
        if (bundle.getString("boolencargofidu").equals("false")) {
            if (contrato.getEncargofiduciario().getIntnumencargofiduciario() == 0) {
                contrato.setEncargofiduciario(null);
            }
        }

        getSessionBeanCobra().getCobraService().guardarContrato(contrato, getSessionBeanCobra().getUsuarioObra());
        
        if (!listacomponentesimpactados.isEmpty()) {
            getSessionBeanCobra().getCobraService().guardarComponentesImpactados(listacomponentesimpactados);
        }
        
        if (Boolean.parseBoolean(bundle.getString("aplicamarcologico"))) {
            if (estrategia != 0 && booltipocontratoconvenio) {
                Contratoestrategia contratoestrategia = new Contratoestrategia();
                contratoestrategia.setFkIntidcontrato(contrato.getIntidcontrato());
                contratoestrategia.setEstrategia(new Estrategia(estrategia));
                contratoestrategia.setFkIntusucreacion(getSessionBeanCobra().getUsuarioObra().getUsuId());
                contratoestrategia.setDatefechacreacion(new Date());
                getSessionBeanCobra().getMarcoLogicoService().guardarContratoEstrategia(contratoestrategia);
            }
        }
        estrategia = 0;

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

        if (guardarborradorconvenio == true) {
            limpiarContrato();
        }
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
        if (bundle.getString("plazoconacta").compareTo("false") == 0) {
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
        } else {
            if (contrato.getFechaactaini() != null) {

                if (contrato.getFechaactaini() != null) {
                    fechaCambio();
                } else {
                    contrato.setIntduraciondias(0);
                }
            } else {
                contrato.setIntduraciondias(0);
                contrato.setDatefechafin(null);
            }
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
        if (bundle.getString("plazoconacta").compareTo("false") == 0) {
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
        } else if (contrato.getFechaactaini() != null && contrato.getDatefechafin() != null) {
            if (contrato.getDatefechafin().compareTo(contrato.getFechaactaini()) >= 0) {
                long diferenciaFecha = contrato.getDatefechafin().getTime() - contrato.getFechaactaini().getTime();
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
        enNuevoConvenio = true;
        booltipocontratoconvenio = true;
        tipoContCon = "Convenio";
        boolcontrconsultoria = false;
        getSessionBeanCobra().getCobraService().setAsoContratoCrear(false);
        getSessionBeanCobra().setCargarcontrato(false);
        //if (contrato != null && bundle.getString("conplanoperativo").equals("true")) {
        contrato = new Contrato();
        limpiarContrato();
        getFiltrocontrato().setTipocontratoselect(0);

//        } else {
//            limpiarContrato();
//        }
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
        enNuevoConvenio = true;
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
            if (bundle.getString("conplanoperativo").equals("true")) {
                try {
                    ValidacionesConvenio.validarAgregarPolizasContrato(getContrato().getDatefechaini(), getContrato().getDatefechafin(), polizacontrato.getDatefechavecimiento(), tipoContCon);
                    if (!listaPolizacontratos.isEmpty()) {
                        for (Polizacontrato p : listaPolizacontratos) {
                            if (polizacontrato.getTipopoliza().getInttipopoliza() == p.getTipopoliza().getInttipopoliza()) {
                                FacesUtils.addErrorMessage("El tipo de garantia ya se ha ingresado");
                                return null;
                            }
                        }

                    }
                } catch (Exception e) {
                    FacesUtils.addErrorMessage(e.getMessage());
                    return null;
                }
            }
            polizacontrato.setContrato(contrato);
            polizacontrato.setStrdocpoliza("");
            polizacontrato.setStrdocpoliza("");
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
        if (polizacontrato.getAseguradora().getIntnumnitentidad() != 0 && getTipointpoli() != 0) {
            if (polizacontrato.getStrnumpoliza() != null && polizacontrato.getStrnumpoliza().compareTo("") != 0 && polizacontrato.getDatefechavecimiento() != null) {

                polizacontrato.setAseguradora(getSessionBeanCobra().getCobraService().encontrarAseguradoraPorId(polizacontrato.getAseguradora().getIntnumnitentidad()));
                polizacontrato.setTipopoliza(getSessionBeanCobra().getCobraService().encontrarTipoPolizaPorId(tipointpoli));
                polizacontrato.setContrato(contrato);
                polizacontrato.setStrdocpoliza("");
                //validacion si es plan operativo que valide las fechas dentro del rango de vida del convenio
                if (getContrato().getBooltipocontratoconvenio() == true && getContrato().getBooltipocontratoconvenio() != null) {
                    if (bundle.getString("conplanoperativo").equals("true")) {
                        try {
                            ValidacionesConvenio.validarAgregarPolizas(getContrato().getDatefechaini(), getContrato().getDatefechafin(), polizacontrato.getDatefechavecimiento(), tipoContCon);
                        } catch (Exception e) {
                            FacesUtils.addErrorMessage(e.getMessage());
                            return null;
                        }
                    }
                } else {
                    try {
                        ValidacionesConvenio.validarAgregarPolizasContrato(getContrato().getDatefechaini(), getContrato().getDatefechafin(), polizacontrato.getDatefechavecimiento(), tipoContCon);
                    } catch (Exception e) {
                        FacesUtils.addErrorMessage(e.getMessage());
                        return null;
                    }
                }

                listapolizas.add(polizacontrato);
                listaPolizacontratos.add(polizacontrato);//Se guarda en la lista desde modificar contrato

                polizacontrato = new Polizacontrato();
                polizacontrato.setTipopoliza(new Tipopoliza());
                polizacontrato.setAseguradora(new Aseguradora());
                polizacontrato.setContrato(new Contrato());
            } else {
                FacesUtils.addErrorMessage("Debe diligenciar los datos requeridos para la póliza.");
            }
        } else {
            FacesUtils.addErrorMessage("Debe seleccionar el tipo de garantia y la entidad aseguradora");
        }

        return null;
    }

    /**
     * Elimina la poliza seleccionada de la lista.
     *
     * @param
     * @return
     */
    public String eliminarPoliza() {

        //Polizacontrato poleli = listapolizas.get(filaSeleccionada);
        Polizacontrato poleli = (Polizacontrato) tablaPolizasbin.getRowData();
        listapolizas.remove(poleli);
        listaPolizasEliminar.add(poleli);
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
        if (getContrato().getTercero().getIntcodigo() != -1) {
            primeroDetcontratoContratista();
        }
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
    public String contratoPadreSelec() {
        varmostrarcontrpa = 1;
        contrpadre = (Contrato) tablacontrapadrebindin.getRowData();
        List<Tercero> listaentiddadcontratopadre = new ArrayList<Tercero>();

        if (contrato.getTercero().getIntcodigo() == -1) {
            if (contrpadre.getContratista() != null) {
                listaentiddadcontratopadre = getSessionBeanCobra().getCobraService().obtenerEntidadContratantexContratista(contrpadre.getContratista().getIntcodigocontratista());

                if (!listaentiddadcontratopadre.isEmpty() && listaentiddadcontratopadre.size() == 1) {
                    contrato.setTercero(listaentiddadcontratopadre.get(0));
                    return null;
                } else {
                    FacesUtils.addErrorMessage(Propiedad.getValor("errorconfiguracionterceroconvenio"));
                    limpiarContrato();
                    return null;
                }
            } else {
                FacesUtils.addErrorMessage(Propiedad.getValor("errorconfiguracionterceroconvenio"));
                limpiarContrato();
                return null;
            }
        }
        return null;

    }

    /**
     * Acceder a los datos de la tabla de contratos padres por contratista
     *
     * @return No retorna ningun valor
     */
    public String contratoPadreSelecContratista() {
        varmostrarcontrpa = 1;
        //contrpadre = listacontratoscontratista.get(filaSeleccionada);
        contrpadre = (Contrato) tablacontrapadrebindinContratista.getRowData();
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
        setNumcontratotemporal("");
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
        contrato.setVermensajeguardado(false);
        contrato.setVermensajeerror(false);
        contrato.setMensajeguardado("");
        polizacontrato = new Polizacontrato();
        documentoobra = new Documentoobra();
        encargofiduciario = new Encargofiduciario();
        planificacionpago = new Planificacionpago();
        contrato.setPeriodoevento(new Periodoevento());
        contrato.getPeriodoevento().setEvento(new Evento());
        contrato.setModalidadcontratista(new Modalidadcontratista());
        contrato.setTipoestadobra(new Tipoestadobra(1));//

        contrato.setNumvlrsumaproyectos(BigDecimal.ZERO);

        // contrato.setTercero(new Tercero());
        lisplanifiactapar.clear();
        listaContratosPadre.clear();
        listapolizas.clear();
        listaPolizacontratos.clear();
        //listaProyectosConvenio = new ArrayList<Obra>();
        getFlujoCaja().setProyectosPlanOperativo(new ArrayList<ProyectoPlanOperativo>());
        getFlujoCaja().setFlujoCajaIniciado(false);
        lisplanifiactapar.clear();
        listaEncargofiduciario.clear();
        listadocumentos.clear();
        listadocuContrato.clear();
        numdeactasparciales = 0;
        porcentapagoanticipo = new BigDecimal(BigInteger.ZERO);
        valorpagoanticipo = new BigDecimal(BigInteger.ZERO);
        setFechapagoanticipo(null);
        varmostrarcontrpa = 0;
        contrpadre = null;
        mostrarAgregarPol = 0;
        preguntacontrato = 0;
        varmostrarseleccionconveniosuperior = 0;

        ServletContext theApplicationsServletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        realArchivoPath = theApplicationsServletContext.getRealPath(URL);
        subirDocPol = new CargadorArchivosWeb();

        llenarEntidadesContratantes();
        // getFiltrocontrato().setTipocontratoselect(0);
        instanciarPolizar();
        listaModificarContrato.clear();
        listaContrConvHijo.clear();
        if (contrato.getEstadoconvenio().getIdestadoconvenio() != 2) {
            setRecursosconvenio(new RecursosConvenio(getContrato(), getSessionBeanCobra().getCobraService()));
        }
        panelPantalla = 1;
        getSessionBeanCobra().setConsulteContrato(true);
        actualizarPanel();
        if (Boolean.parseBoolean(bundle.getString("aplicamarcologico"))) {
            if (filtrocontrato.getIdestrategia() != 0) {
                contrato.getTercero().setStrnombrecompleto("TODOS");
            }
        }
        contrato.setTipocontratoconsultoria(new Tipocontratoconsultoria(1));
        contrato.setTipocontrato(new Tipocontrato(1, "Obra", true));

        listacomponentesimpactados = new ArrayList<Contratocomponente>();
    }

    public void iniciarTiposContrato() {
        if (filtrocontrato.getTipocontratoselect() == 1) {

            contrato.setTipocontratoconsultoria(new Tipocontratoconsultoria(1));
            contrato.setTipocontrato(new Tipocontrato());
        } else {
            contrato.setTipocontratoconsultoria(new Tipocontratoconsultoria());
            contrato.setTipocontrato(new Tipocontrato(1, "Obra", true));
        }

    }

    /**
     * Elimina el contrato padre seleccionado.
     *
     * @param
     */
    public void eliminarItemPadreSele() {
        contrpadre = (Contrato) tablacontrapadrebindin2.getRowData();
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

        //Se compara si la el tipo de aporte es en porcentaje.
        if (getTipoAporte() == 1 || getTipoAporte() == 0) {
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
        //Se compara si la el tipo de aporte es en valor.
        if (getTipoAporte() == 2) {

            List<BigDecimal> lsBigporcecuo = new ArrayList<BigDecimal>();

            lisplanifiactapar = new ArrayList<Planificacionpago>();
            BigDecimal numdeactasparcialesB = new BigDecimal(numdeactasparciales);
            BigDecimal cienporciento = new BigDecimal(100);

            if (contrato.getFormapago().getIntidformapago() == 1) {
//              valorpagoanticipo = valorpagoanticipo;
                BigDecimal cien = new BigDecimal(100);
                setPorcentapagoanticipo(valorpagoanticipo.multiply(cien).divide(contrato.getNumvlrcontrato(), RoundingMode.HALF_UP));
                //porcentapagoanticipo = valorpagoanticipo.multiply(cien).divide(contrato.getNumvlrcontrato().setScale(2, RoundingMode.HALF_UP));
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
                    BigDecimal cien = new BigDecimal(100);
                    plp.setValorcuota(contrato.getNumvlrcontrato().multiply(lsBigporcecuo.get(c)).divide(cien));
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
        porcentaje();

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
     * @param
     * @return
     */
    public String eliminardocu() {
        //Documentoobra documento = listadocumentos.get(filaSeleccionada);
        Documentoobra documento = (Documentoobra) tablaDocumentos.getRowData();
        listadocumentos.remove(documento);
        return null;

    }

    /**
     * mostrar que tipo de documento para asociarlo
     *
     * @return
     */
    public String llenarTipodocumentos() {
        List<Tipodocumento> listatipodocumento = getSessionBeanCobra().getCobraService().encontrarTiposDocumentos(controlvisualizaciondocumento);
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
    public String encargoFiduSelec() {
        //encargofiduciario = listaEncargofiduciario.get(filaSeleccionada);
        encargofiduciario = (Encargofiduciario) tablaEncargofiduciario.getRowData();
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
     * Seleccionar el contrato desde la tabla Contratos de Convenio
     *
     * @return
     */
    public String detalleContratoHijo() {

        Contrato contratotabla = (Contrato) tablaContratoHijo.getRowData();
        return detalleContratoGeneric(contratotabla);
    }

    /**
     * Seleccionar el contrato desde la tabla detalle
     *
     * @return
     */
    public String detalleContrato() {

        Contrato contratotabla = (Contrato) tablacontratoconvenio.getRowData();
        return detalleContratoGeneric(contratotabla);

    }

    /**
     * Seleccionar el contrato desde la tabla detalle
     *
     * @param contratotabla
     *
     * @return
     */
    public String detalleContratoGeneric(Contrato contratotabla) {

        //NuevoContratoBasico nuevoContraBasicoSeleccionado = (NuevoContratoBasico) FacesUtils.getManagedBean("Supervisor$Contrato");
        //Contrato contratotabla = nuevoContraBasicoSeleccionado.getListacontratos().get(filaSeleccionada);
        getSessionBeanCobra().setConsulteContrato(true);
        getSessionBeanCobra().setCargarcontrato(false);
        cargarContrato(contratotabla);
        encontrarAvanceFisicoConvenio();
        if (contratotabla.getNumvlrcontrato() != null && contratotabla.getNumValorCuotaGerencia() != null) {
            puedeEditarValorFuentes = false;
        } else {
            puedeEditarValorFuentes = true;
        }
//        setContrato(contratotabla);
//        finentrega = contratotabla.getDatefechafin().toString();

        if (contratotabla.getEstadoconvenio().getIdestadoconvenio() != 2) {

            setRecursosconvenio(new RecursosConvenio(getContrato(), getSessionBeanCobra().getCobraService()));
            recursosconvenio.setLstFuentesRecursos(getSessionBeanCobra().getCobraService().obtenerFuentesRecursosConvenio(contrato.getIntidcontrato()));
            recursosconvenio.sumaFuentesRecursos();
            contrato.setFuenterecursosconvenios(new LinkedHashSet<Fuenterecursosconvenio>());
            cargarActividadesPlanOperativo();
            contrato.setAuxiliarValorContrato(contrato.getNumvlrcontrato());
            contrato.setAuxiliarValorGerencia(contrato.getNumValorCuotaGerencia());

            this.llenarDocumentoContrato();
            listadocumentos = getSessionBeanCobra().getCobraService().getListaDocumentosContrato();
            llenarPolizas();
            getContrato().setModolecturaplanop(false);
            panelPantalla = 1;
            return "nuevoConvenioPo";
        } else {
            return "consultarContrato";
        }
    }

    /**
     * Seleccionar el contrato según contratista, desde la tabla detalle
     *
     * @param
     * @return
     */
    public String detalleContratoContratista() {
//        NuevoContratoBasico nuevoContraBasicoSeleccionado = (NuevoContratoBasico) FacesUtils.getManagedBean("Supervisor$Contrato");
//        Contrato contratotabla = (Contrato) nuevoContraBasicoSeleccionado.getListacontratoscontratista().get(filaSeleccionada);
        encontrarAvanceFisicoConvenio();
        Contrato contratotabla = (Contrato) tablacontratoconveniocontratista.getRowData();
        cargarContrato(contratotabla);
        if (contratotabla.getEstadoconvenio().getIdestadoconvenio() != 2) {
//            setRecursosconvenio(new RecursosConvenio(getContrato(), getSessionBeanCobra().getCobraService()));
            getContrato().setModolecturaplanop(false);
            contrato.setFuenterecursosconvenios(new LinkedHashSet<Fuenterecursosconvenio>(recursosconvenio.getLstFuentesRecursos()));
            llenarPolizas();
            return "nuevoConvenioPo";
        } else {
            return "consultarContrato";
        }
    }

    /**
     * Seleccionar el contrato desde la tabla detalle contratohijo
     *
     * @param
     * @return consultarContrato Refresca la página para mostrar el detalle del
     * contrato hijo.
     */
    public String detalleContrHijo() {
        Contrato contratoHijo = (Contrato) tablacontconvhijo.getRowData();
        cargarContrato(contratoHijo);
        booltipocontratoconvenio = false;
        tipoContCon = "Contrato";
        boolcontrconsultoria = false;
        getSessionBeanCobra().getCobraService().setAsoContratoCrear(false);
        listaavancefisico.clear();
        boolavanceproyectoconvenio = false;
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
    public String detalleContratoPadre() {
//        NuevoContratoBasico nuevoContraBasicoSeleccionado = (NuevoContratoBasico) FacesUtils.getManagedBean("Supervisor$Contrato");
//        Contrato contratotabla = nuevoContraBasicoSeleccionado.getListacontratos().get(filaSeleccionada);
        encontrarAvanceFisicoConvenio();
        Contrato contratotabla = (Contrato) tablacontratoconvenio.getRowData();
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
        if (contratotabla.getEstadoconvenio().getIdestadoconvenio() != 2) {
            contrato.setFuenterecursosconvenios(new LinkedHashSet<Fuenterecursosconvenio>(recursosconvenio.getLstFuentesRecursos()));
//            setRecursosconvenio(new RecursosConvenio(getContrato(), getSessionBeanCobra().getCobraService()));
            recursosconvenio.sumaFuentesRecursos();
            llenarPolizas();
            return "nuevoConvenioPo";
        } else {
            return "consultarContrato";
        }
    }

    /**
     * Seleccionar el contrato desde la tabla detalle contratopadre según el
     * contratista
     *
     * @param
     * @return
     */
    public String detalleContratoPadreContratista() {
//        NuevoContratoBasico nuevoContraBasicoSeleccionado = (NuevoContratoBasico) FacesUtils.getManagedBean("Supervisor$Contrato");
//        Contrato contratotabla = (Contrato) nuevoContraBasicoSeleccionado.getListacontratoscontratista().get(filaSeleccionada);
        encontrarAvanceFisicoConvenio();
        Contrato contratotabla = (Contrato) tablacontratoconveniocontratista.getRowData();
        //contratotabla.getContrato().setFormapago(new Formapago());
        if (contratotabla.getContrato() != null) {
            //setContrato(contratotabla.getContrato());
            cargarContrato(contratotabla);
        } else {
            FacesUtils.addInfoMessage("No pertence a ningún contrato Padre");
            return null;
        }
        if (contratotabla.getEstadoconvenio().getIdestadoconvenio() != 2) {
            contrato.setFuenterecursosconvenios(new LinkedHashSet<Fuenterecursosconvenio>(recursosconvenio.getLstFuentesRecursos()));
            recursosconvenio.sumaFuentesRecursos();
            llenarPolizas();
            return "nuevoConvenioPo";
        } else {
            return "consultarContrato";
        }
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
        if (contrato.getDatefechafin() != null) {
            finentrega = contrato.getDatefechafin().toString();
        }
        listaPolizacontratos.clear();
        listaObraContrato.clear();
        listaGirodirecto.clear();
        listaModificarContrato.clear();
        boolpolizas = false;
        boolproyectos = false;
        boolgiros = false;
        boolmodifca = false;
        if (filtrocontrato.getIdestrategia() != 0) {
//            getMarcoLogicoBean().setBoolcrearobligacion(false);
//            setAvancemedios(getSessionBeanCobra().getMarcoLogicoService().encontrarAvanceConvenioMediosVida(contrato.getIntidcontrato()));
        }

        if (contrato.getBooltipocontratoconvenio()) {
            tipoContCon = "Convenio";
            booltipocontratoconvenio = true;
        } else {
            tipoContCon = "Contrato";
            booltipocontratoconvenio = false;
        }
        if (getContrato().getTipocontratoconsultoria().getIntidtipocontratoconsultoria() == 1) {
            getContrato().getTipocontratoconsultoria().setStrdescripcion("Obra");
        }

        setNumcontratotemporal(cont.getStrnumcontrato());

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

    public String bt_download_documento_action() {
        // TODO: Process the action. Return value is a navigation
        // case name where null will return to the same page.
        Documentoobra doc = (Documentoobra) tabladocuContrato.getRowData();
//        NuevoContratoBasico doc = (NuevoContratoBasico) FacesUtils.getManagedBean("Supervisor$Contrato");
        // doc.getListadocuContrato().get(filaSeleccionada);
        getSessionBeanCobra().setUrlAbri(doc.getStrubicacion());
        //this.getSessionBeanCobra().setUrlAbri(doc.getStrubicacion());
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/" + getSessionBeanCobra().getBundle().getString("versioncobra") + "/" + doc.getStrubicacion());
        } catch (IOException ex) {
            Logger.getLogger(NuevoContratoBasico.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public String bt_download_documento_action_modulo() {
        // TODO: Process the action. Return value is a navigation
        // case name where null will return to the same page.

        //Documentoobra doc = getSessionBeanCobra().getCobraService().getListaDocumentosContrato().get(filaSeleccionada);
        Documentoobra doc = (Documentoobra) tablaDocumentosContrato.getRowData();
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

            listaContratista = getSessionBeanCobra().getCobraService().filtrarContratistas(nombre, 0, 10);
            totalfilas = getSessionBeanCobra().getCobraService().numFiltrarContratistas(nombre);
            // }
        } else {

            listaContratista = getSessionBeanCobra().getCobraService().encontrarContratistas(0, 10);
            totalfilas = getSessionBeanCobra().getCobraService().getNumContratistas();
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
     * Muestra los siguientes 5 contratistas por nombre
     *
     * @return null
     */
    public String siguientesReales() {

        int num = (pagina) * 10;
        if (aplicafiltro) {
            listaContratista = getSessionBeanCobra().getCobraService().filtrarContratistas(nombre, num, 10);
            totalfilas = getSessionBeanCobra().getCobraService().numFiltrarContratistas(nombre);

        } else {
            listaContratista = getSessionBeanCobra().getCobraService().encontrarContratistas(num, 10);
            totalfilas = getSessionBeanCobra().getCobraService().getNumContratistas();
        }
        if (totalfilas <= 10) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 10;
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
        int num = (pagina - 1) * 10;
        if (aplicafiltro) {
            listaContratista = getSessionBeanCobra().getCobraService().filtrarContratistas(nombre, num, 10);
            totalfilas = getSessionBeanCobra().getCobraService().numFiltrarContratistas(nombre);
        } else {
            listaContratista = getSessionBeanCobra().getCobraService().encontrarContratistas(num, 10);
            totalfilas = getSessionBeanCobra().getCobraService().getNumContratistas();
        }
        if (totalfilas <= 10) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 20;
            if (totalfilas % 20 > 0) {
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
        int num = totalfilas % 10;

        if (aplicafiltro) {
            totalfilas = getSessionBeanCobra().getCobraService().numFiltrarContratistas(nombre);
            listaContratista = getSessionBeanCobra().getCobraService().filtrarContratistas(nombre, totalfilas - num, totalfilas);
        } else {
            totalfilas = getSessionBeanCobra().getCobraService().getNumContratistas();
            listaContratista = getSessionBeanCobra().getCobraService().encontrarContratistas(totalfilas - num, totalfilas);
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
    public String seleccionarContratistas() {
//        NuevoContratoBasico nb = (NuevoContratoBasico) FacesUtils.getManagedBean("Supervisor$Contrato");
//        contrato.setContratista(nb.getListaContratista().get(filaSeleccinada));
        contrato.setContratista((Contratista) tablacontratistas.getRowData());
        return null;
    }

    /**
     * Seleecionar el contratista y habilitar los campos para ser editado
     *
     * @return
     */
    public String editarContratistas() {
        boolcrearcontratista = false;
        booleditando = true;
        //contratista = listaContratista.get(filaSeleccionada);
        contratista = (Contratista) tablacontratistas.getRowData();
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
        polizacontrato = (Polizacontrato) tablaPolizasbin.getRowData();

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

//    /**
//     * Instancia del bean AdministrarObraNew
//     *
//     * @return
//     */
//    protected AdministrarObraNew getAdmin() {
//        return (AdministrarObraNew) FacesUtils.getManagedBean("Supervisor$AdministrarObraNew");
//    }
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
            if (getContrato().getTercero().getIntcodigo() != -1) {
                primeroDetcontratoContratista();
            }

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
            listacontratos = getSessionBeanCobra().getCobraService().filtroAvanzadoContratoContratante(getSessionBeanCobra().getUsuarioObra(), getContrato().getTercero().getIntcodigo(), filtrocontrato, 0, 20);
            totalfilas = getSessionBeanCobra().getCobraService().cantidadfFiltroAvanzadoContratoContratante(getSessionBeanCobra().getUsuarioObra(), getContrato().getTercero().getIntcodigo(), filtrocontrato);
            //        } else {
//            listacontratos = getSessionBeanCobra().getCobraService().obtenerContratoxEntidad(getContrato().getTercero().getIntcodigo(), 0, 5, filtro);
//            totalfilas = getSessionBeanCobra().getCobraService().numContratoxEntidad(getContrato().getTercero().getIntcodigo(), filtro);
//        }
            //Condicion para que la loista de contratos salga en tipo obra
            for (Contrato con : listacontratos) {

                if (con.getTipocontratoconsultoria().getIntidtipocontratoconsultoria() == 1) {
                    con.getTipocontratoconsultoria().setStrdescripcion("Obra");
                }
            }

            pagina = 1;
            if (totalfilas <= 20) {
                totalpaginas = 1;
            } else {
                totalpaginas = totalfilas / 20;
                if (totalfilas % 20 > 0) {
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
            listacontratoscontratista = getSessionBeanCobra().getCobraService().filtroAvanzadoContratoContratista(getSessionBeanCobra().getUsuarioObra(), getContrato().getTercero().getIntcodigo(), filtrocontrato, 0, 20);
            totalfilasContratista = getSessionBeanCobra().getCobraService().cantidadfFiltroAvanzadoContratoContratista(getSessionBeanCobra().getUsuarioObra(), getContrato().getTercero().getIntcodigo(), filtrocontrato);
            paginaContratista = 1;
            if (totalfilasContratista <= 20) {
                totalpaginasContratista = 1;
            } else {
                totalpaginasContratista = totalfilasContratista / 20;
                if (totalfilasContratista % 20 > 0) {
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
     * Muestra los siguiente 20 contratos por filtro (nombre, nro de
     * contrato,etc)
     *
     * @return null
     */
    public String siguienteDetcontrato() {

        int num = (pagina) * 20;

//        if (aplicafiltrocontrato) {
        listacontratos = getSessionBeanCobra().getCobraService().filtroAvanzadoContratoContratante(getSessionBeanCobra().getUsuarioObra(), getContrato().getTercero().getIntcodigo(), filtrocontrato, num, 20);
        totalfilas = getSessionBeanCobra().getCobraService().cantidadfFiltroAvanzadoContratoContratante(getSessionBeanCobra().getUsuarioObra(), getContrato().getTercero().getIntcodigo(), filtrocontrato);

//        } else {
//
//            listacontratos = getSessionBeanCobra().getCobraService().obtenerContratoxEntidad(getContrato().getTercero().getIntcodigo(), num, 5, filtro);
//            totalfilas = getSessionBeanCobra().getCobraService().numContratoxEntidad(getContrato().getTercero().getIntcodigo(), filtro);
//        }
        if (totalfilas <= 20) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 20;
            if (totalfilas % 20 > 0) {
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
     * Muestra los siguiente 20 contratos por filtro (nombre, nro de
     * contrato,etc) para la tabla de convenios según el contratista
     *
     * @return null
     */
    public String siguienteDetcontratoContratista() {

        int num = (paginaContratista) * 20;

        listacontratoscontratista = getSessionBeanCobra().getCobraService().filtroAvanzadoContratoContratista(getSessionBeanCobra().getUsuarioObra(), getContrato().getTercero().getIntcodigo(), filtrocontrato, num, 20);
        totalfilasContratista = getSessionBeanCobra().getCobraService().cantidadfFiltroAvanzadoContratoContratista(getSessionBeanCobra().getUsuarioObra(), getContrato().getTercero().getIntcodigo(), filtrocontrato);

        if (totalfilasContratista <= 20) {
            totalpaginasContratista = 1;
        } else {
            totalpaginasContratista = totalfilasContratista / 20;
            if (totalfilasContratista % 20 > 0) {
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
     * Muestra los anterior 20 contratos por filtro (nombre, nro de
     * contrato,etc)
     *
     * @return null
     */
    public String anterioresDetcontrato() {

        pagina = pagina - 1;
        int num = (pagina - 1) * 20;

//        if (aplicafiltrocontrato) {
        listacontratos = getSessionBeanCobra().getCobraService().filtroAvanzadoContratoContratante(getSessionBeanCobra().getUsuarioObra(), getContrato().getTercero().getIntcodigo(), filtrocontrato, num, 20);
        totalfilas = getSessionBeanCobra().getCobraService().cantidadfFiltroAvanzadoContratoContratante(getSessionBeanCobra().getUsuarioObra(), getContrato().getTercero().getIntcodigo(), filtrocontrato);

//        } else {
//
//            listacontratos = getSessionBeanCobra().getCobraService().obtenerContratoxEntidad(getContrato().getTercero().getIntcodigo(), num, 5, filtro);
//            totalfilas = getSessionBeanCobra().getCobraService().numContratoxEntidad(getContrato().getTercero().getIntcodigo(), filtro);
//        }
        if (totalfilas <= 20) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 20;
            if (totalfilas % 20 > 0) {
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
     * Muestra los anterior 20 contratos por filtro (nombre, nro de
     * contrato,etc) para la tabla de convenios según el contratista
     *
     * @return null
     */
    public String anterioresDetcontratoContratista() {

        paginaContratista = paginaContratista - 1;
        int num = (paginaContratista - 1) * 20;
        listacontratoscontratista = getSessionBeanCobra().getCobraService().filtroAvanzadoContratoContratista(getSessionBeanCobra().getUsuarioObra(), getContrato().getTercero().getIntcodigo(), filtrocontrato, num, 20);
        totalfilasContratista = getSessionBeanCobra().getCobraService().cantidadfFiltroAvanzadoContratoContratista(getSessionBeanCobra().getUsuarioObra(), getContrato().getTercero().getIntcodigo(), filtrocontrato);
        if (totalfilasContratista <= 20) {
            totalpaginasContratista = 1;
        } else {
            totalpaginasContratista = totalfilasContratista / 20;
            if (totalfilasContratista % 20 > 0) {
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
     * Muestra los último 20 contratos por filtro (nombre, nro de contrato,etc)
     *
     * @return null
     */
    public String ultimoDetcontrato() {

//        if (aplicafiltrocontrato) {
        totalfilas = getSessionBeanCobra().getCobraService().cantidadfFiltroAvanzadoContratoContratante(getSessionBeanCobra().getUsuarioObra(), getContrato().getTercero().getIntcodigo(), filtrocontrato);
        int num = totalfilas % 20;
        if (num == 0) {
            num = 20;
        }
        listacontratos = getSessionBeanCobra().getCobraService().filtroAvanzadoContratoContratante(getSessionBeanCobra().getUsuarioObra(), getContrato().getTercero().getIntcodigo(), filtrocontrato, totalfilas - num, totalfilas);

//        } else {
//
//            totalfilas = getSessionBeanCobra().getCobraService().numContratoxEntidad(getContrato().getTercero().getIntcodigo(), filtro);
//            listacontratos = getSessionBeanCobra().getCobraService().obtenerContratoxEntidad(getContrato().getTercero().getIntcodigo(), totalfilas - num, totalfilas, filtro);
//
//        }
        if (totalfilas <= 20) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 20;
            if (totalfilas % 20 > 0) {
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
     * Muestra los último 20 contratos por filtro (nombre, nro de contrato,etc)
     * para la tabla de convenios según el contratista
     *
     * @return null
     */
    public String ultimoDetcontratoContratista() {
        int num = totalfilasContratista % 20;
        totalfilasContratista = getSessionBeanCobra().getCobraService().cantidadfFiltroAvanzadoContratoContratista(getSessionBeanCobra().getUsuarioObra(), getContrato().getTercero().getIntcodigo(), filtrocontrato);
        listacontratoscontratista = getSessionBeanCobra().getCobraService().filtroAvanzadoContratoContratista(getSessionBeanCobra().getUsuarioObra(), getContrato().getTercero().getIntcodigo(), filtrocontrato, totalfilasContratista - num, totalfilasContratista);

        if (totalfilasContratista <= 20) {
            totalpaginasContratista = 1;
        } else {
            totalpaginasContratista = totalfilasContratista / 20;
            if (totalfilasContratista % 20 > 0) {
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
        //variable para que pueda buscar correctamente los contratos
        enNuevoConvenio = false;
        if (getSessionBeanCobra().getBundle().getString("aplicafonade").equals("true")) {
            filtrocontrato.setAplicaafonade(true);
        } else {
            filtrocontrato.setAplicaafonade(false);
        }
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
        filtrocontrato.setPalabraClave("");
        filtrocontrato.setRaiz(false);
        controlvisualizaciondocumento = 2;
        limpiarContrato();
        primeroDetcontrato();
        if (getContrato().getTercero().getIntcodigo() != -1) {
            primeroDetcontratoContratista(); // ojo porque anteriormente no se llamaba desde aca.    
        }
        modificartipocontrato = true;
        habiitarmodificartipocontrato = false;
        getSessionBeanCobra().getUsuarioObra().getRenderrecurso().setTipocontrato(false);
        return "consultarContratoConvenio";
    }

    /**
     * Iniciar El detalle del convenio seteando las variables necesariaspara se
     * mostrado
     *
     * @return la pagina detalleconvenio adecuada a este
     */
    public String iniciarDetaConvenio() {
        enNuevoConvenio = false;
        filtrocontrato.setTipocontratoselect(0);
        filtrocontrato.setTipocontrato(0);
        //se deshabilitó para montaje producción 24 de octubre de 2013 (Se puso en false)  
        if (getSessionBeanCobra().getBundle().getString("aplicafonade").equals("true")) {
            filtrocontrato.setAplicaafonade(false);
        } else {
            filtrocontrato.setAplicaafonade(false);
        }
        filtrocontrato.setRaiz(true);
        listacontratos.clear();
        listacontratoscontratista.clear();
        tipoContCon = "Convenio";
        booltipocontratoconvenio = true;
        boolcontrconsultoria = false;
        filtrocontrato.setBoolcontrconsultoria(false);
        filtrocontrato.setBooltienehijo(false);
        filtrocontrato.setBooltipocontconv(true);
        filtrocontrato.setIdestrategia(estrategia);
        filtrocontrato.setTipocontratoselect(0);
        filtrocontrato.setPalabraClave("");
        controlvisualizaciondocumento = 1;
        if (contrato != null && bundle.getString("conplanoperativo").equals("true")) {
            contrato = new Contrato();
            limpiarContrato();
        } else {
            limpiarContrato();
        }
        primeroDetcontrato();
        if (getContrato().getTercero().getIntcodigo() != -1) {
            primeroDetcontratoContratista();
        }
        getSessionBeanCobra().getUsuarioObra().getRenderrecurso().setTipocontrato(true);

        return "consultarContratoConvenio";
    }

    /**
     * Iniciar contrato enviando el idcontrato
     *
     * @return
     */
    public String ReporteConvenio() {
        try {
            getSessionBeanCobra().setUrlAbri(Propiedad.getValor("ipserver") + bundle.getString("reportepdfconvenio") + contrato.getIntidcontrato());
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/Reportes");
        } catch (IOException ex) {
            Logger.getLogger(Contrato.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String ReporteConvenioExcel() {
        try {
            getSessionBeanCobra().setUrlAbri(Propiedad.getValor("ipserver") + bundle.getString("reporteexcelconvenio") + "&convenio=" + contrato.getIntidcontrato());
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/Reportes");
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
        listaContrConvHijo = getSessionBeanCobra().getCobraService().encontrarContratosHijos(getContrato(), false, getSessionBeanCobra().getUsuarioObra());

        return null;
    }

    /**
     * Obtiene los contratos o subconvenios hijos a partir de un contrato.
     *
     * @throws DataAccessLayerException
     */
    public String llenarContrMacroConvHijo() {

        boolean first = listaContrConvHijo.isEmpty();
        List<Contrato> listaContr = new ArrayList<Contrato>();
        llenarContrConvHijo();

        if (first || (listaContrConvHijo.size() > 0 && !listaContrConvHijo.get(0).getContrato().getBooltipocontratoconvenio())) {

            for (Contrato cMacro : listaContrConvHijo) {

                if (cMacro.getContrato().getBooltipocontratoconvenio()) {
                    listaContr.add(cMacro);
                }
            }
        } else {

            for (Contrato cDerivado : listaContrConvHijo) {

                if (!cDerivado.getContrato().getBooltipocontratoconvenio()) {
                    listaContr.add(cDerivado);
                }
            }
        }

        listaContrConvHijo = listaContr;

        return null;
    }

    /**
     * Obtiene los contratos o subconvenios hijos a partir de un contrato
     *
     * @throws DataAccessLayerException
     */
    public String llenarSubconvenios() {

        listaSubconvenios = getSessionBeanCobra().getCobraService().encontrarContratosHijos(getContrato(), true, getSessionBeanCobra().getUsuarioObra());

        return null;
    }

    /**
     * Cambiar de contrato a convenio padre al seleccionarlo
     *
     * @return null
     */
    public String cambiarContrPadre() {
        if (preguntacontrato == 1) {
            setContratoselect("Contrato");
        } else {
            setContratoselect("Convenio");
        }
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
        } else {
            if (contrato.getDatefechaini().before(contrpadre.getDatefechaini()) || contrato.getDatefechaini().after(contrpadre.getDatefechafin())) {
                FacesUtils.addErrorMessage("La Fecha de Inicio del " + tipoContCon + " a crear debe estar dentro del rango del " + contratoselect + "\n Fecha Inicio " + contrpadre.getDatefechaini() + " Fecha Fin " + contrpadre.getDatefechafin());
            } else {
                FacesUtils.addErrorMessage("La Fecha Fin del  " + tipoContCon + " a crear debe estar dentro del rango del " + contratoselect + "\n Fecha Inicio " + contrpadre.getDatefechaini() + " Fecha Fin " + contrpadre.getDatefechafin());
            }
            return false;
        }
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
        } else {
            if (boolproyectos != false) {
                FacesUtils.addInfoMessage("La Busqueda produjo resultados");
            }
        }
        primeroObra();
        return null;
    }

    /**
     * Muestra los primeros 20 proyectos cuando se inicia el simpletooglepanel ,
     * si la búsqueda lleva algún parámetro se trae la obra o proyecto que se
     * esta buscando
     *
     * @return null
     */
    public String primeroObra() {
        listaObraContrato = getSessionBeanCobra().getCobraService().encontrarObraxContratos(getContrato().getIntidcontrato(), buscarproyecto, 0, 20, booltipocontratoconvenio, getSessionBeanCobra().getUsuarioObra(), filtrocontrato.isEsadministrador());
        totalfilas = getSessionBeanCobra().getCobraService().getNumObraxContratos(getContrato().getIntidcontrato(), buscarproyecto, booltipocontratoconvenio, getSessionBeanCobra().getUsuarioObra(), filtrocontrato.isEsadministrador());

        pagina = 1;

        if (totalfilas > 0) {
            if (totalfilas <= 20) {
                totalpaginas = 1;
            } else {
                totalpaginas = totalfilas / 20;
                if (totalfilas % 20 > 0) {
                    totalpaginas++;
                }
            }
            veranteriorobra = false;
            if (totalpaginas > 1) {
                verultimoobra = true;
            } else {
                verultimoobra = false;
            }
            buscarproyecto = "";
            return null;
        } else {
            buscarproyecto = "";
            FacesUtils.addInfoMessage("No se encontrarón proyectos");
        }
        return null;
    }

    /**
     * Muestra los siguiente 20 proyectos cuando se inicia el simpletooglepanel
     * , si la búsqueda lleva algún parámetro se trae la obra o proyecto que se
     * esta buscando
     *
     * @return null
     */
    public String siguienteObra() {
        int num = (pagina) * 20;

        listaObraContrato = getSessionBeanCobra().getCobraService().encontrarObraxContratos(getContrato().getIntidcontrato(), buscarproyecto, num, 20, booltipocontratoconvenio, getSessionBeanCobra().getUsuarioObra(), filtrocontrato.isEsadministrador());
        totalfilas = getSessionBeanCobra().getCobraService().getNumObraxContratos(getContrato().getIntidcontrato(), buscarproyecto, booltipocontratoconvenio, getSessionBeanCobra().getUsuarioObra(), filtrocontrato.isEsadministrador());

        if (totalfilas <= 20) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 20;
            if (totalfilas % 20 > 0) {
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
     * Muestra los anteriores 20 proyectos cuando se inicia el simpletooglepanel
     * , si la búsqueda lleva algún parámetro se trae la obra o proyecto que se
     * esta buscando
     *
     * @return null
     */
    public String anteriorObra() {
        pagina = pagina - 1;
        int num = (pagina - 1) * 20;

        listaObraContrato = getSessionBeanCobra().getCobraService().encontrarObraxContratos(getContrato().getIntidcontrato(), buscarproyecto, num, 20, booltipocontratoconvenio, getSessionBeanCobra().getUsuarioObra(), filtrocontrato.isEsadministrador());
        totalfilas = getSessionBeanCobra().getCobraService().getNumObraxContratos(getContrato().getIntidcontrato(), buscarproyecto, booltipocontratoconvenio, getSessionBeanCobra().getUsuarioObra(), filtrocontrato.isEsadministrador());

        if (totalfilas <= 20) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 20;
            if (totalfilas % 20 > 0) {
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
        int num = totalfilas % 20;

        listaObraContrato = getSessionBeanCobra().getCobraService().encontrarObraxContratos(getContrato().getIntidcontrato(), buscarproyecto, totalfilas - num, 20, booltipocontratoconvenio, getSessionBeanCobra().getUsuarioObra(), filtrocontrato.isEsadministrador());
        totalfilas = getSessionBeanCobra().getCobraService().getNumObraxContratos(getContrato().getIntidcontrato(), buscarproyecto, booltipocontratoconvenio, getSessionBeanCobra().getUsuarioObra(), filtrocontrato.isEsadministrador());

        if (totalfilas <= 20) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 20;
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
        filtrocontrato.setTipocontratoselect(1);
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
        if (filtrocontrato.getIdestrategia() != 0) {
            getIngresarNuevaObra().setProyectoestrategia(true);
            getIngresarNuevaObra().fnEsProyectoPadre();
        }
        List<Tercero> listentcontxcontratistaconvenio = new ArrayList<Tercero>();
        if (contrato.getIntidcontrato() != 0) {
            listimpactosocial = getSessionBeanCobra().getCobraService().encontrarImpactoSocial(contrato.getIntidcontrato());
        }
        getIngresarNuevaObra().tipoImpactoSocial();
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
    public void bt_eliminar_documento_action() {
//        SessionBeanCobra sbc = (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
//        Documentoobra doc = sbc.getCobraService().getListaDocumentosContrato().get(filaSeleccionada);

        Documentoobra doc = (Documentoobra) tablaDocumentosContrato.getRowData();
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
            if (getSessionBeanCobra().getBundle().getString("versioncobra").compareTo("siente") == 0) {

                if (contrato.getContratista().getIntcodigocontratista() != 3120) {

                    if (contrato.getContratista().getIntcodigocontratista() != 7860) {
                        getSessionBeanCobra().setUrlAbri(Propiedad.getValor("ipserver") + bundle.getString("reporteexcelproyectosconvenio") + contrato.getIntidcontrato());
                        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/Reportes");
                    } else {
                        getSessionBeanCobra().setUrlAbri(Propiedad.getValor("ipserver") + bundle.getString("reporteexcelproyectosconvenioambiente") + contrato.getIntidcontrato());
                        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/Reportes");
                    }
                } else {
                    getSessionBeanCobra().setUrlAbri(Propiedad.getValor("ipserver") + bundle.getString("reporteexcelproyectosconvenioeducacion") + contrato.getIntidcontrato());
                    FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/Reportes");
                }
            } else {
                getSessionBeanCobra().setUrlAbri(Propiedad.getValor("ipserver") + bundle.getString("reporteexcelproyectosconvenio") + contrato.getIntidcontrato());
                FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/Reportes");
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
        getSessionBeanCobra().getCobraService().guardarContrato(contrato, getSessionBeanCobra().getUsuarioObra());
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
        getSessionBeanCobra().getCobraService().guardarContrato(getContrato(), getSessionBeanCobra().getUsuarioObra());
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

        boolean band = true;
        if (getContrato().getIntidcontrato() == 0) {
            if (getSessionBeanCobra().getCobraService().encontrarContratoPorNumero(getContrato().getStrnumcontrato()) != null) {
                band = false;
            }
        } else {
            ///Revisar si cambio el numero de contrato
            if (getNumcontratotemporal().compareTo(getContrato().getStrnumcontrato()) != 0) {
                if (getSessionBeanCobra().getCobraService().encontrarContratoPorNumero(getContrato().getStrnumcontrato()) != null) {
                    band = false;
                }
            }
        }

        if (band) {
            getSessionBeanCobra().getCobraService().guardarContrato(getContrato(), getSessionBeanCobra().getUsuarioObra());
            FacesUtils.addInfoMessage("Se actualizo correctamente el " + tipoContCon + ".");
            habilitarModificarNumero = true;
            habilitarGuardarNumeroContrato = false;
        } else {
            FacesUtils.addErrorMessage("El número del " + tipoContCon + " ya existe.");
        }
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
        getSessionBeanCobra().getCobraService().guardarContrato(getContrato(), getSessionBeanCobra().getUsuarioObra());
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
    //private Boolean boolpruea = false;

//    public Boolean getBoolpruea() {
//        return boolpruea;
//    }
//
//    public void setBoolpruea(Boolean boolpruea) {
//        this.boolpruea = boolpruea;
//    }

    /*
     * metodo que se encarga de actualizar el panel actual de la pantalla
     * @param panelPantalla int
     */
    public void actualizarPanel() {
//       variable para esconder los botones que llaman a los reportes
        boolreporte = false;
        //this.panelPantalla = panelPantalla;

        switch (panelPantalla) {
            case 1:
                variabletitulo = Propiedad.getValor("primerodatosbasicos");
                infogeneralcrearconvenio = Propiedad.getValor("infogeneralcrearconveniodb");

                break;
            case 2:
                variabletitulo = Propiedad.getValor("segundoplanoperativo");
                //boolpruea = true;
//                infogeneralcrearconvenio = Propiedad.getValor("infogeneralcrearconveniodb");
                break;
            case 3:
                variabletitulo = Propiedad.getValor("terceropolizas");
                if (!getContrato().isModolecturaplanop()) {
                    this.guardarBorradorConvenio();
                }
//                infogeneralcrearconvenio = Propiedad.getValor("infogeneralcrearconveniodb");
                break;
            case 4:
                variabletitulo = Propiedad.getValor("cuartoformapago");
                infogeneralcrearconvenio = Propiedad.getValor("infogeneralcrearconveniodb");
                break;
            case 5:
                variabletitulo = Propiedad.getValor("quintodocumento");
                if (!getContrato().isModolecturaplanop()) {
                    this.guardarBorradorConvenio();
                }
//                infogeneralcrearconvenio = Propiedad.getValor("infogeneralcrearconveniodb");
                break;

        }

    }

    /**
     * Metodo que llena los estados del convenio
     *
     * @return void
     */
    public void llenarEstadoConvenio() {
        if (Propiedad.getValor("conplanoperativo").equals("true")) {
            listEstadoConvenio = getSessionBeanCobra().getCobraService().encontrarEstadoConvenio();
            estadoConvenioOption = new SelectItem[listEstadoConvenio.size()];
            int i = 0;
            for (Estadoconvenio estado : listEstadoConvenio) {
                SelectItem itEstado = new SelectItem(estado.getIdestadoconvenio(), estado.getStrestadoconv());
                estadoConvenioOption[i++] = itEstado;
            }
        } else {
            filtrocontrato.setEstadoConvenio(2);
        }

    }

    /*
     Método para validar número de contrato
     */
    public boolean validarNumeroContrato() {
        if (getContrato().getIntidcontrato() == 0) {
            if (getSessionBeanCobra().getCobraService().encontrarContratoPorNumero(getContrato().getStrnumcontrato()) != null) {
                throw new ConvenioException("El número del convenio ya existe.");
            }
        } else {
            ///Revisar si cambio el numero de contrato
            if (getNumcontratotemporal().compareTo(getContrato().getStrnumcontrato()) != 0) {
                if (getSessionBeanCobra().getCobraService().encontrarContratoPorNumero(getContrato().getStrnumcontrato()) != null) {
                    throw new ConvenioException("El número del convenio ya existe.");
                }
            }
        }
        return true;
    }

    /**
     *
     */
    public void preValidarFinalizarConvenio() {
        botonGuardado = 3;
        if (contrato.getIntidcontrato() != 0) {
            periodoConveniosFueraRango = getSessionBeanCobra().getCobraService().encontrarPeriodosConvenioPorFueraDeRango(contrato.getDatefechaini(), contrato.getDatefechafin(), contrato.getIntidcontrato());

            if (periodoConveniosFueraRango.isEmpty()) {
                finalizarGuardado();
            }

        } else {
            finalizarGuardado();
        }
    }

    /**
     * *
     *
     */
    public void preValidarGuardarBorrador() {
        botonGuardado = 1;
        if (contrato.getIntidcontrato() != 0) {

            periodoConveniosFueraRango = getSessionBeanCobra().getCobraService().encontrarPeriodosConvenioPorFueraDeRango(contrato.getDatefechaini(), contrato.getDatefechafin(), contrato.getIntidcontrato());
            if (periodoConveniosFueraRango.isEmpty()) {
                guardarBorradorConvenio();
            }

        } else {
            guardarBorradorConvenio();
        }
    }

    /**
     * *
     *
     */
    public String preValidarGuardarBorradorBotonPO() {
        botonGuardado = 2;
        if (contrato.getIntidcontrato() != 0) {
            periodoConveniosFueraRango = getSessionBeanCobra().getCobraService().encontrarPeriodosConvenioPorFueraDeRango(contrato.getDatefechaini(), contrato.getDatefechafin(), contrato.getIntidcontrato());

            if (periodoConveniosFueraRango.isEmpty()) {
                return planOperativo();
            }

        } else {
            return planOperativo();
        }
        return null;
    }

    /**
     * *
     *
     */
    private void eliminarPeriodosFueraRango() {

        /**
         * Si el usuario acepta redimensionar el flujo de caja, se procede a
         * eliminar los periodos fuera de rango.
         */
        getSessionBeanCobra().getCobraService().borrarPeriodosflujocaja(periodoConveniosFueraRango);
        getFlujoCaja().iniciarFlujoCaja();
        getFlujoCaja().guardarFlujoCaja();
    }

    /*
     *Metodo que se encarga de guardar el convenio en estado en estructuración.
     * 
     * @return void
     */
    public void guardarBorradorConvenio() {
        ValidacionesConvenio.validarValorCuotaGerencia(contrato.getNumvlrcontrato(), contrato.getNumValorCuotaGerencia());
        if (!contrato.getActividadobras().isEmpty()) {
            List<Actividadobra> lstActividadObraTodas = new ArrayList<Actividadobra>();
            Actividadobra actiRaiz = (Actividadobra) contrato.getActividadobras().iterator().next();
            encontrarActividadContrato(actiRaiz, lstActividadObraTodas);
            ValidacionesConvenio.validarFechaActaInicioTO(lstActividadObraTodas, contrato);
        }
        if (validacionesBasicasConvenioPO(true)) {
            if (!getContrato().isModolecturaplanop()) {
                configuracionGuardadoPo(1, true);
            }

            if (isEliminarPeriodosFueraRango()) {
                eliminarPeriodosFueraRango();
            }
        }
    }

    /*
     *metodo que se encarga de guardar el convenio
     * en estado en ejecución.
     * 
     */
    public void finalizarGuardado() {

        try {
            ValidacionesConvenio.validarValorCuotaGerencia(contrato.getNumvlrcontrato(), contrato.getNumValorCuotaGerencia());
            if (!contrato.getActividadobras().isEmpty()) {
                List<Actividadobra> lstActividadObraTodas = new ArrayList<Actividadobra>();
                Actividadobra actiRaiz = (Actividadobra) contrato.getActividadobras().iterator().next();
                encontrarActividadContrato(actiRaiz, lstActividadObraTodas);
                ValidacionesConvenio.validarFechaActaInicioTO(lstActividadObraTodas, contrato);
            }
            if (validacionesBasicasConvenioPO(false)) {
                ValidacionesConvenio.validarDistribucionFinalFuenteRecursos(getContrato().getNumvlrcontrato(), recursosconvenio.getSumafuentes());
                ValidacionesConvenio.validarDistribucionFinalCuotaGerencia(getContrato().getNumValorCuotaGerencia(), recursosconvenio.getCuotaGerencia());
                ValidacionesConvenio.validarProyectosPlanOperativo(getFlujoCaja().getProyectosPlanOperativo());
                ValidacionesConvenio.validarDisponibilidadFuentesRecursos(recursosconvenio.getLstFuentesRecursos());
                if (isEliminarPeriodosFueraRango()) {
                    eliminarPeriodosFueraRango();
                }
                if (!getFlujoCaja().isFlujoCajaIniciado()) {
                    //Aca deberiamos guardar borrador convenio antes de iniciar elflujo de caja
                    configuracionGuardadoPo(1, true);
                    getFlujoCaja().iniciarFlujoCaja();
                }
                if (getFlujoCaja().validarFlujoCaja()) {
                    double diferenciaIngresosEgresos = getFlujoCaja().getTotalIngresos() - getFlujoCaja().getTotalEgresos();
                    if (Math.abs(diferenciaIngresosEgresos) < 1) {
                        configuracionGuardadoPo(2, true);
                        contrato.setTercero(new Tercero());
                        setConfirmacionGuardado(true); // Se pone en true si fue un guardado exitoso. 
                    } else {
                        FacesUtils.addErrorMessage("El valor total de los ingresos (" + CobraUtil.getInstance().parserCurrencyLocale(getFlujoCaja().getTotalIngresos()) + ") , debe ser igual al valor total de los egresos ($" + CobraUtil.getInstance().parserCurrencyLocale(getFlujoCaja().getTotalEgresos()) + "), en el flujo de caja.");
                        setMensajePlanOperativo(false, true, "El valor total de los ingresos (" + CobraUtil.getInstance().parserCurrencyLocale(getFlujoCaja().getTotalIngresos()) + ") , debe ser igual al valor total de los egresos ($" + CobraUtil.getInstance().parserCurrencyLocale(getFlujoCaja().getTotalEgresos()) + "), en el flujo de caja.");
                    }
                }
            }
        } catch (ConvenioException e) {
            FacesUtils.addErrorMessage(e.getMessage());
            setMensajePlanOperativo(false, true, e.getMessage());
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
        lstentidades = getSessionBeanCobra().getUsuarioService().encontrarEntidadesxtercero(getSessionBeanCobra().getUsuarioObra(), 6, booltipocontratoconvenio);
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
        lstgerentes = getSessionBeanCobra().getCobraService().encontrarGerente();
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

//    public List<Obra> getListaProyectosConvenio() {
//        return listaProyectosConvenio;
//    }
//
//    public void setListaProyectosCovenio(List<Obra> listaProyectosCovenio) {
//        this.listaProyectosConvenio = listaProyectosCovenio;
//    }
    /**
     * Reportes de plan operativo para el formato en PDF
     *
     * @void
     */
    public void reportesPlanOperativoPDF() {

        switch (tipoReporteVarTmp) {
            case 1:
                /*Reporte Consolidado*/
                try {
                    getSessionBeanCobra().setUrlAbri(Propiedad.getValor("ipserver") + bundle.getString("reporteplanoperativoconsolidadopdf") + contrato.getIntidcontrato());
                    FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/Reportes");

                } catch (IOException ex) {
                    Logger.getLogger(Contrato.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 2:
                /*Reporte Cronograma*/
                try {
                    getSessionBeanCobra().setUrlAbri(Propiedad.getValor("ipserver") + bundle.getString("reporteplanoperativocronogramapdf") + contrato.getIntidcontrato());
                    FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/Reportes");

                } catch (IOException ex) {
                    Logger.getLogger(Contrato.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 3:
                /*Reporte Presupuesto*/
                try {
                    getSessionBeanCobra().setUrlAbri(Propiedad.getValor("ipserver") + bundle.getString("reporteplanoperativopresupuestopdf") + contrato.getIntidcontrato());
                    FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/Reportes");

                } catch (IOException ex) {
                    Logger.getLogger(Contrato.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 4:
                /*Reporte Flujo de caja*/
                try {
                    getSessionBeanCobra().setUrlAbri(Propiedad.getValor("ipserver") + bundle.getString("reporteplanoperativoflujocajapdf") + contrato.getIntidcontrato());
                    FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/Reportes");

                } catch (IOException ex) {
                    Logger.getLogger(Contrato.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 5:
                /*Reporte Plan operativo*/
                try {
                    getSessionBeanCobra().setUrlAbri(Propiedad.getValor("ipserver") + bundle.getString("reporteplanoperativoseccionplanoperativopdf") + contrato.getIntidcontrato());
                    FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/Reportes");

                } catch (IOException ex) {
                    Logger.getLogger(Contrato.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 6:
                /*Reporte Plan de contratación*/
                try {
                    getSessionBeanCobra().setUrlAbri(Propiedad.getValor("ipserver") + bundle.getString("reporteplanoperativoplancontratacionpdf") + contrato.getIntidcontrato());
                    FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/Reportes");

                } catch (IOException ex) {
                    Logger.getLogger(Contrato.class
                            .getName()).log(Level.SEVERE, null, ex);
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
                    getSessionBeanCobra().setUrlAbri(Propiedad.getValor("ipserver") + bundle.getString("reporteplanoperativoconsolidadoxls") + contrato.getIntidcontrato());
                    FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/Reportes");

                } catch (IOException ex) {
                    Logger.getLogger(Contrato.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 2:
                /*Reporte Cronograma*/
                try {
                    getSessionBeanCobra().setUrlAbri(Propiedad.getValor("ipserver") + bundle.getString("reporteplanoperativocronogramaxls") + contrato.getIntidcontrato());
                    FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/Reportes");

                } catch (IOException ex) {
                    Logger.getLogger(Contrato.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 3:
                /*Reporte Presupuesto*/
                try {
                    getSessionBeanCobra().setUrlAbri(Propiedad.getValor("ipserver") + bundle.getString("reporteplanoperativopresupuestoxls") + contrato.getIntidcontrato());
                    FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/Reportes");

                } catch (IOException ex) {
                    Logger.getLogger(Contrato.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 4:
                /*Reporte Flujo de caja*/
                try {
                    getSessionBeanCobra().setUrlAbri(Propiedad.getValor("ipserver") + bundle.getString("reporteplanoperativoflujocajaxls") + contrato.getIntidcontrato());
                    FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/Reportes");

                } catch (IOException ex) {
                    Logger.getLogger(Contrato.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 5:
                /*Reporte Plan operativo*/
                try {
                    getSessionBeanCobra().setUrlAbri(Propiedad.getValor("ipserver") + bundle.getString("reporteplanoperativoseccionplanoperativoxls") + contrato.getIntidcontrato());
                    FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/Reportes");

                } catch (IOException ex) {
                    Logger.getLogger(Contrato.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 6:
                /*Reporte Plan de contratación*/
                try {
                    getSessionBeanCobra().setUrlAbri(Propiedad.getValor("ipserver") + bundle.getString("reporteplanoperativoplancontratacionxls") + contrato.getIntidcontrato());
                    FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/Reportes");

                } catch (IOException ex) {
                    Logger.getLogger(Contrato.class
                            .getName()).log(Level.SEVERE, null, ex);
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
                    getSessionBeanCobra().setUrlAbri(Propiedad.getValor("ipserver") + bundle.getString("reporteplanoperativocronogramampp") + contrato.getIntidcontrato());
                    FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/Reportes");

                } catch (IOException ex) {
                    Logger.getLogger(Contrato.class
                            .getName()).log(Level.SEVERE, null, ex);
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
            validarPuedeEditarValorFuente();
//            Map<Integer, String> mapaValidacionFechasPO = validarFechasConvenioEnRangoPO(contrato);
//            if (!mapaValidacionFechasPO.isEmpty()) {
//                validarModificacionFechasPO(mapaValidacionFechasPO);
//                return null;
//            } else {

            ValidacionesConvenio.validarFechasPlanOperativo(getContrato().getFechaactaini(), getContrato().getDatefechaini(), getContrato().getDatefechafin());
            ValidacionesConvenio.validarValorPositivo(getContrato().getNumvlrcontrato(), "convenio");
            ValidacionesConvenio.validarTamanoLista(recursosconvenio.getLstFuentesRecursos(), "Fuente de Recursos");
            ValidacionesConvenio.validarValorCuotaGerencia(contrato.getNumvlrcontrato(), contrato.getNumValorCuotaGerencia());
            ValidacionesConvenio.validarDistribucionFinalCuotaGerencia(getContrato().getNumValorCuotaGerencia(), recursosconvenio.getCuotaGerencia());

            contrato.setFuenterecursosconvenios(new LinkedHashSet<Fuenterecursosconvenio>(recursosconvenio.getLstFuentesRecursos()));
            //Guardando antes de pasar a plan operativo                
            if (!getContrato().isModolecturaplanop()) {
                guardarBorradorConvenio();
            }

            if (!contrato.getActividadobras().isEmpty()) {
                if (!getSessionBeanCobra().isConsulteContrato()) {
                    Actividadobra actiRaiz = (Actividadobra) contrato.getActividadobras().iterator().next();
                    lstTodasActividades.clear();
                    limpiarPredecesoresActividad(actiRaiz);
                    cargarActividadesConsultadas(actiRaiz);
                    asignarNumeracionActividadesConsultadas(lstTodasActividades);
                    if (!contrato.getDependenciasGenerales().isEmpty()) {
                        asignarPredecesorActividadesConsultadas(contrato.getDependenciasGenerales());
                    }
                }
            }

            ContratoDTO cont = CasteoGWT.castearConvenioToConvenioDTO(contrato);

            if (!cont.getActividadobras().isEmpty()) {
                ActividadobraDTO actiRaiz = (ActividadobraDTO) cont.getActividadobras().iterator().next();
                List<ActividadobraDTO> lstTodasActividadesDTO = new ArrayList<ActividadobraDTO>();
                lstTodasActividadesDTO.add(actiRaiz);
                encontrarActividadContratoDTO(actiRaiz, lstTodasActividadesDTO);
                ValidacionesConvenio.validarFechaActaInicio(lstTodasActividadesDTO, cont);
            }
            getSessionBeanCobra().setConsulteContrato(false);

            getSessionBeanCobra().getCobraGwtService().setContratoDto(cont);
            //} else {
            //  ContratoDTO cont = CasteoGWT.castearContratoSencillo(getSessionBeanCobra().getCobraGwtService().getContratoDto(), contrato);
            // getSessionBeanCobra().getCobraGwtService().setContratoDto(cont);
            //}

            return "PlanOperativo";
            //}
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

//    public void actualizarSoloContratoGWT(ContratoDTO contratodto) {
//        contrato.setDatefechaini(contratodto.getDatefechaini());
//        contrato.setDatefechafin(contratodto.getDatefechafin());
//        contrato.setFechaactaini(contratodto.getDatefechaactaini());
//        contrato.setStrnumcontrato(contratodto.getStrnumcontrato());
//        contrato.setNumvlrcontrato(contratodto.getNumvlrcontrato());
//        contrato.setTextobjeto(contratodto.getTextobjeto());
//        contrato.setIntduraciondias(contratodto.getIntduraciondias());
//        
//    }

    /*
     * metodo que se encarga de actualizar el contrato con los datos provenientes del plan operativo
     * @param ContratoDto Objeto convenio utilizado en GWT.     
     * 
     * @author Carlos Loaiza
     */
    public void actualizarContratodatosGwt(ContratoDTO contratodto) {
        /**
         * Datos Generales
         */
        contrato.setDatefechaini(contratodto.getDatefechaini());
        contrato.setDatefechafin(contratodto.getDatefechafin());
        contrato.setFechaactaini(contratodto.getDatefechaactaini());
        contrato.setStrnumcontrato(contratodto.getStrnumcontrato());
        contrato.setNumvlrcontrato(contratodto.getNumvlrcontrato());
        contrato.setAuxiliarValorContrato(contratodto.getAuxiliarValorContrato());
        contrato.setValorDisponible(contratodto.getValorDisponible());
        contrato.setNumValorCuotaGerencia(contratodto.getNumValorCuotaGerencia());
        contrato.setAuxiliarValorGerencia(contratodto.getAuxiliarValorGerencia());
        contrato.setValorDisponibleCuotaGerencia(contratodto.getValorDisponibleCuotaGerencia());
        contrato.setTextobjeto(contratodto.getTextobjeto());
        contrato.setIntduraciondias(contratodto.getIntduraciondias());
        contrato.setVermensajeguardado(contratodto.isVermensajeguardado());
        contrato.setMensajeguardado(contratodto.getMensajeguardado());
        CasteoGWT.modificarEstaFuenteRecurso(contrato.getFuenterecursosconvenios());

        if (contrato.getNumvlrcontrato() != null && contrato.getNumValorCuotaGerencia() != null) {
            puedeEditarValorFuentes = false;
        } else {
            puedeEditarValorFuentes = true;
        }

        contrato.getActividadobras().clear();
        contrato.getDependenciasGenerales().clear();
        if (!contratodto.getActividadobras().isEmpty()) {
            Iterator it = contratodto.getActividadobras().iterator();
            while (it.hasNext()) {
                ActividadobraDTO act = (ActividadobraDTO) it.next();
                Actividadobra activi = CasteoGWT.castearActividadobraDdoToActividadobra(act, contrato, null, null, getSessionBeanCobra().getUsuarioObra().getUsuId());
                activi.setContrato(contrato);
                //Castear dependencias;

                contrato.getActividadobras().add(activi);
                //Extrae los proyectos de la actividad
                getContrato().setDependenciasGenerales(CasteoGWT.castearSetDependenciasaListaDependenciasDto(getSessionBeanCobra().getCobraGwtService().getContratoDto().getDependenciasGenerales(), activi));
                //listaProyectosConvenio.clear();
                //getFlujoCaja().setProyectosPlanOperativo(new ArrayList<ProyectoPlanOperativo>());
                //extraerProyectosActividad(act);
                if (!getSessionBeanCobra().getCobraGwtService().isElimino()) {
                    mapaReembolsoConvenio.clear();
                }
                lstActividadesEliminar.clear();
                cargarActividadesEliminar(activi);
                lstTemporalActividades.clear();
                encontrarActividadContrato(activi, lstTemporalActividades);

            }

        }

    }

    public void limpiarEntidad() {
        contrato.getTercero().setStrnombrecompleto("");
    }

//    public void extraerProyectosActividad(ActividadobraDTO act) {
//        Iterator it = act.getChildren().iterator();
////        List<Obra> lista= new ArrayList<Obra>();
//        while (it.hasNext()) {
//            ActividadobraDTO actdto = (ActividadobraDTO) it.next();
//            if (actdto.getObra() != null) {
//                //listaProyectosConvenio.add(CasteoGWT.castearObraDdtToObra(actdto.getObra(), contrato, getSessionBeanCobra().getUsuarioObra().getUsuId()));
//                ProyectoPlanOperativo proyplaop= new ProyectoPlanOperativo();
//                proyplaop.setProyecto(CasteoGWT.castearObraDdtToObra(actdto.getObra(), contrato, getSessionBeanCobra().getUsuarioObra().getUsuId()));                
//                getFlujoCaja().getProyectosPlanOperativo().add(proyplaop);
//            }
//            if (actdto.hasChildren()) {
//
//                extraerProyectosActividad(actdto);
//            }
//        }
////        return lista;
//    }
    public String listarPorTipoContrato() {
        if (filtrocontrato.getTipocontratoselect() == 1) {
            switch (filtrocontrato.getTipocontrato()) {
                case 0:
                    iniciarDetaContrato();
                    break;
//                case 1:
//                    tipoContCon = "Obra";
//                    iniciarDetaContrato();
//
//                    break;
                case 2:
                    tipoContCon = "Estudios y Diseños";
                    contrConsultoria();
                    break;
                case 3:
                    tipoContCon = "Prefactibilidad";
                    contrConsultoria();
                    break;
                case 4:
                    tipoContCon = "Factibilidad";
                    contrConsultoria();
                    break;
                case 5:
                    tipoContCon = "Asesoría";
                    contrConsultoria();
                    break;
                case 6:
                    tipoContCon = "Gerencia de Obra";
                    contrConsultoria();

                    break;
                case 7:
                    listacontratos.clear();
                    listacontratoscontratista.clear();
                    tipoContCon = "Contrato consultoría";
                    booltipocontratoconvenio = false;
                    boolcontrconsultoria = true;
                    filtrocontrato.setBooltienehijo(false);
                    filtrocontrato.setBooltipocontconv(false);
                    filtrocontrato.setBoolcontrconsultoria(true);
                    primeroDetcontrato();

                    break;
            }
        } else if (filtrocontrato.getTipocontratoselect() == 0) {
            switch (filtrocontrato.getTipocontrato()) {
                case 0:
                    iniciarDetaContrato();
                    break;
                case 1:
                    tipoContCon = "Obra";
                    iniciarDetaContrato();
                    break;
                case 2:
                    tipoContCon = "Bienes";
                    iniciarDetaContrato();
                    break;
                case 3:
                    tipoContCon = "Servicios";
                    iniciarDetaContrato();
                    break;
                case 4:
                    tipoContCon = "Sociopolítico";
                    iniciarDetaContrato();
                    break;
                case 5:
                    tipoContCon = "Compraventa";
                    iniciarDetaContrato();
                    break;
                case 6:
                    tipoContCon = "Prestación de servicios profesionales";
                    iniciarDetaContrato();
                    break;
                case 7:
                    tipoContCon = "Interadministrativo";
                    iniciarDetaContrato();
                    break;
                case 8:
                    tipoContCon = "Apoyo a la gestión";
                    iniciarDetaContrato();
                    break;
                case 9:
                    tipoContCon = "Suministro";
                    iniciarDetaContrato();
                    break;
                case 10:
                    tipoContCon = "Cooperación";
                    iniciarDetaContrato();
                    break;
                case 11:
                    tipoContCon = "Arrendamiento";
                    iniciarDetaContrato();
                    break;
                case 12:
                    tipoContCon = "Comodato";
                    iniciarDetaContrato();
                    break;
                case 13:
                    tipoContCon = "Aporte";
                    iniciarDetaContrato();
                    break;
                case 14:
                    tipoContCon = "Apoyo financiero";
                    iniciarDetaContrato();
                    break;
                case 15:
                    tipoContCon = "Cesión de derechos de autor";
                    iniciarDetaContrato();
                    break;
            }
        } else if (filtrocontrato.getTipocontratoselect() == 2) {
            iniciarDetaContrato();
        }

        return "consultarContratoConvenio";
    }
    /*
     * Metodo para modificar el tipo de contrato 
     */

    public void modificarTipocontrato() {
        if (filtrocontrato.getTipocontratoselect() == 0) {
            getContrato().setTipocontrato(getSessionBeanCobra().getCobraService().encontrarTipoContratoPorId(filtrocontrato.getTipocontrato()));
        } else if (filtrocontrato.getTipocontratoselect() == 1) {
            getContrato().setTipocontratoconsultoria(getSessionBeanCobra().getCobraService().encontrarTipoContratoConsultoriaxId(filtrocontrato.getTipocontrato()));
        }
        getContrato().setFormapago(new Formapago(1, null, true));
        getSessionBeanCobra().getCobraService().guardarContrato(getContrato(), getSessionBeanCobra().getUsuarioObra());
        FacesUtils.addInfoMessage("Se actualizo correctamente el contrato");
        if (getContrato().getTipocontratoconsultoria().getIntidtipocontratoconsultoria() == 1) {
            getContrato().getTipocontratoconsultoria().setStrdescripcion("Obra");
        }
        modificartipocontrato = true;
        habiitarmodificartipocontrato = false;

    }
    /*
     * Metodo para Habiltiar la modificacion del tipo contrato
     */

    public void habilitarModificacionTipoContrato() {
        modificartipocontrato = false;
        habiitarmodificartipocontrato = true;
    }
    /*
     * Metodo para Cancelar la modificacion  del tipo contrato
     */

    public void cancelarModificacionTipoContrato() {
        modificartipocontrato = true;
        habiitarmodificartipocontrato = false;
    }
    /*
     * Metodo que valida las fechas registradas en el contrato las cuales debe estar dentro del rando de contrato
     */

    public boolean validacionFechasContrato() {
        if (contrato.getFormapago().getIntidformapago() != 0) {
            switch (contrato.getFormapago().getIntidformapago()) {

                case 1:
                case 2:
                    int i = 0;
                    if (contrato.getFormapago().getIntidformapago() == 1) {
                        if (fechapagoanticipo.compareTo(contrato.getDatefechaini()) < 0) {
                            return false;
                        }
                        //Se Deshabilito solicitado 27 diciembre
//                        if (fechapagoanticipo.compareTo(contrato.getDatefechafin()) > 0) {
//                            return false;
//                        }
                    }
                    while (i < lisplanifiactapar.size()) {
                        if (lisplanifiactapar.get(i).getDatefechapago().compareTo(contrato.getDatefechaini()) < 0) {
                            return false;
                        }
                        //Se Deshabilito solicitado 27 diciembre
//                        if (lisplanifiactapar.get(i).getDatefechapago().compareTo(contrato.getDatefechafin()) > 0) {
//                            return false;
//                        }
                        i++;
                    }
                    break;

                case 3:
                    if (planificacionpago.getDatefechapago().compareTo(contrato.getDatefechaini()) < 0) {
                        return false;
                    }
                    //Se Deshabilito solicitado 27 diciembre
//                    if (planificacionpago.getDatefechapago().compareTo(contrato.getDatefechafin()) > 0) {
//                        return false;
//                    }
                    break;
            }
        }
        return true;
    }
    /*
     * Metodo que valida las fechas tanto del anticipo como las de las actas; donde las dechas de las actas deben ser superior 
     * que la del anticipo
     */

    public boolean validacionFechasContratoActasyAnticipo() {
        if (contrato.getFormapago().getIntidformapago() != 0) {
            int i = 0;
            switch (contrato.getFormapago().getIntidformapago()) {
                case 1:
                    while (i < lisplanifiactapar.size()) {
                        if (fechapagoanticipo.compareTo(lisplanifiactapar.get(i).getDatefechapago()) > 0) {
                            return false;
                        }
                        i++;
                    }
            }
        }
        return true;
    }

    /*
     * Metodo que se encarga de validar si el valor del contrato y valor de cuota de gerencia tienen un valor 
     * para activar el campo valor del monto de la fuente de recursos.
     * 
     *
     */
    public void validarPuedeEditarValorFuente() {
        if (recursosconvenio.getLstFuentesRecursos().isEmpty()) {
            boolean boolActivarValor = false;
            if (contrato.getAuxiliarValorContrato() != null) {
                if (contrato.getAuxiliarValorContrato().compareTo(BigDecimal.ZERO) == 0) {
                    contrato.setAuxiliarValorContrato(null);
                    puedeEditarValorFuentes = true;
                    boolActivarValor = true;
                    FacesUtils.addErrorMessage("Ingrese el valor global del convenio");
                } else {
                    String valorContrato = "" + contrato.getAuxiliarValorContrato();
                    contrato.setNumvlrcontrato(new BigDecimal(valorContrato));
                    contrato.setValorDisponible(new BigDecimal(valorContrato));
                }
            } else {
                boolActivarValor = true;
                FacesUtils.addErrorMessage("Ingrese el valor global del convenio");
            }
            if (contrato.getAuxiliarValorGerencia() != null) {
                if (contrato.getAuxiliarValorGerencia().compareTo(BigDecimal.ZERO) == 0) {
                    contrato.setAuxiliarValorGerencia(null);
                    puedeEditarValorFuentes = true;
                    boolActivarValor = true;
                    FacesUtils.addErrorMessage("Ingrese el valor global de la cuota de gerencia");
                } else {
                    String valorGerencia = "" + contrato.getAuxiliarValorGerencia();
                    contrato.setNumValorCuotaGerencia(new BigDecimal(valorGerencia));
                    contrato.setValorDisponibleCuotaGerencia(new BigDecimal(valorGerencia));
                }
            } else {
                boolActivarValor = true;
                FacesUtils.addErrorMessage("Ingrese el valor global de la cuota de gerencia");
            }
            puedeEditarValorFuentes = boolActivarValor;
        } else {
            //if (contrato.getAuxiliarValorContrato().compareTo(contrato.getNumvlrcontrato()) < 0) {
            if (contrato.getAuxiliarValorContrato().compareTo(recursosconvenio.getSumafuentes()) < 0) {
                contrato.setAuxiliarValorContrato(contrato.getNumvlrcontrato());
                FacesUtils.addErrorMessage("No es posible reducir el valor Global del convenio, porque ya posee fuente de recursos por un valor de $" + recursosconvenio.getSumafuentes());
            } else {
                contrato.setNumvlrcontrato(contrato.getAuxiliarValorContrato());
                contrato.setValorDisponible(contrato.getNumvlrcontrato().subtract(recursosconvenio.getSumafuentes()));
            }
            //if (contrato.getAuxiliarValorGerencia().compareTo(contrato.getNumValorCuotaGerencia()) != 0) {
            if (contrato.getAuxiliarValorGerencia().compareTo(recursosconvenio.getCuotaGerencia()) < 0) {
                //String copiaValorGerencia = "" + contrato.getNumValorCuotaGerencia();
                contrato.setAuxiliarValorGerencia(contrato.getNumValorCuotaGerencia());
                FacesUtils.addErrorMessage("No es posible reducir el valor Global de la cuota de gerencia,porque ya distribución de cuota de gerencia en fuente de recursos por un valor de $" + recursosconvenio.getCuotaGerencia());
            } else {
                contrato.setNumValorCuotaGerencia(contrato.getAuxiliarValorGerencia());
                contrato.setValorDisponibleCuotaGerencia(contrato.getNumValorCuotaGerencia().subtract(recursosconvenio.getCuotaGerencia()));
            }
        }
    }

    /**
     * @return the puedeEditarValorFuentes
     */
    public boolean isPuedeEditarValorFuentes() {
        return puedeEditarValorFuentes;
    }

    /**
     * @param puedeEditarValorFuentes the puedeEditarValorFuentes to set
     */
    public void setPuedeEditarValorFuentes(boolean puedeEditarValorFuentes) {
        this.puedeEditarValorFuentes = puedeEditarValorFuentes;
    }

    /*
     *metodo que se encarga de asignar la numeracion a las actividades
     *cuando el convenio se consulta.
     * 
     */
    public void cargarActividadesConsultadas(Actividadobra actividaObraPrincipal) {
        lstTodasActividades.add(actividaObraPrincipal);
        //Extrae las obras para poder instanciar y validar el flujo de caja
        if (actividaObraPrincipal.getObra() != null) {
            //listaProyectosConvenio.add(actividaObraPrincipal.getObra());
            ProyectoPlanOperativo proyplaop = new ProyectoPlanOperativo();
            proyplaop.setProyecto(actividaObraPrincipal.getObra());
            proyplaop.setContratosProyecto(new ArrayList<Contrato>());
            for (Obrafuenterecursosconvenios ofrc : actividaObraPrincipal.getObra().getObrafuenterecursosconvenioses()) {
                //getSessionBeanCobra().getCobraService().buscarRelacionobrafuenterecursoscontrato(ofrc.getIdobrafuenterecursos());
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
            getFlujoCaja().getProyectosPlanOperativo().add(proyplaop);

        }
        if (!actividaObraPrincipal.getActividadobras().isEmpty()) {
            for (Iterator it = actividaObraPrincipal.getActividadobras().iterator(); it.hasNext();) {
                Actividadobra actividadHija = (Actividadobra) it.next();
                cargarActividadesConsultadas(actividadHija);
            }

        }
    }

    public void asignarNumeracionActividadesConsultadas(List<Actividadobra> lstTodasActividades) {
        int numeracion = 1;
        for (Actividadobra acti : lstTodasActividades) {
            acti.setNumeracion(numeracion);
            numeracion++;
        }
    }

    public void asignarPredecesorActividadesConsultadas(Set dependenciasGenerales) {
        for (Iterator it = dependenciasGenerales.iterator(); it.hasNext();) {
            Dependencia dependencia = (Dependencia) it.next();
            if (dependencia.getActividadobraByFkActividadDestino().getLstPredecesores().isEmpty()) {
                dependencia.getActividadobraByFkActividadDestino().setPredecesor("" + dependencia.getActividadobraByFkActividadOrigen().getNumeracion());
                dependencia.getActividadobraByFkActividadDestino().getLstPredecesores().add(dependencia.getActividadobraByFkActividadOrigen().getNumeracion());
            } else {
                dependencia.getActividadobraByFkActividadDestino().setPredecesor(dependencia.getActividadobraByFkActividadDestino().getPredecesor() + "," + dependencia.getActividadobraByFkActividadOrigen().getNumeracion());
                dependencia.getActividadobraByFkActividadDestino().getLstPredecesores().add(dependencia.getActividadobraByFkActividadOrigen().getNumeracion());
            }
        }
    }

//    public Map<Integer, String> validarFechasConvenioEnRangoPO(Contrato contrato) {
//        Map<Integer, String> mapaValidacionRangoPo = new HashMap<Integer, String>();
////
//        //Borramos las paramétricas para que se ecalculen con las nuevas fechas
//        if (!contrato.getActividadobras().isEmpty()) {
//
//
//            /*
//             Solo debo permitir reducir fecha acta de inicio o ampliar fecha de finalización
//             de lo contrario sacar mensaje 
//            
//             Actualmente posee actividades programadas por fuera del rango de fechas especificado, por favor elimine 
//             o modifique las mismas para poder realizar el cambio de las fechas previamente establecidas
//            
//             */
//            Iterator it = contrato.getActividadobras().iterator();
//            Actividadobra actiRaiz = (Actividadobra) it.next();
//
//            if (contrato.getFechaactaini().compareTo(actiRaiz.getFechaInicio()) == 0
//                    && contrato.getDatefechafin().compareTo(actiRaiz.getFechaFin()) == 0) {
//
//            } else {
//                if (contrato.getFechaactaini().compareTo(actiRaiz.getFechaInicio()) < 0
//                        || contrato.getDatefechafin().compareTo(actiRaiz.getFechaFin()) > 0) //Acta de inicio antes yuy fecha fin quieta o superior
//                {
//
//                    Iterator itpadres = actiRaiz.getActividadobras().iterator();
//
//                    actiRaiz.setFechaInicio(contrato.getFechaactaini());
//                    actiRaiz.setFechaFin(contrato.getDatefechafin());
//                    actiRaiz.setDuracion(contrato.getIntduraciondias());
//                    while (itpadres.hasNext()) {
//                        Actividadobra actpad = (Actividadobra) itpadres.next();
//
//                        if (actpad.getStrdescactividad().compareTo("Planeación del Convenio") == 0) {
//                            actpad.setFechaInicio(contrato.getFechaactaini());
//                            actpad.setDuracion(CalendarUtil.getDaysBetween(actpad.getFechaInicio(), actpad.getFechaFin()) + 1);
//                            Iterator ithijos = actpad.getActividadobras().iterator();
//                            while (ithijos.hasNext()) {
//                                Actividadobra actacta = (Actividadobra) ithijos.next();
//                                if (actacta.getStrdescactividad().compareTo("Acta de Inicio del Convenio") == 0) {
//                                    actacta.setFechaInicio(contrato.getFechaactaini());
//                                    actacta.setDuracion(CalendarUtil.getDaysBetween(actacta.getFechaInicio(), actacta.getFechaFin()) + 1);
//                                }
//                            }
//                        }
//                        if (actpad.getStrdescactividad().compareTo("Liquidación del Convenio Marco") == 0) {
//                            actpad.setFechaFin(contrato.getDatefechafin());
//                            actpad.setDuracion(CalendarUtil.getDaysBetween(actpad.getFechaInicio(), actpad.getFechaFin()) + 1);
//                            Iterator ithijos = actpad.getActividadobras().iterator();
//                            while (ithijos.hasNext()) {
//                                Actividadobra actacta = (Actividadobra) ithijos.next();
//                                if (actacta.getStrdescactividad().compareTo("Liquidación Convenio Marco") == 0) {
//                                    actacta.setFechaFin(contrato.getDatefechafin());
//                                    actacta.setDuracion(CalendarUtil.getDaysBetween(actacta.getFechaInicio(), actacta.getFechaFin()) + 1);
//                                }
//                            }
//                        }
//                    }
//
//                } else {
//
//                    Iterator itpadres = actiRaiz.getActividadobras().iterator();
//                    List listaeli= new ArrayList<Actividadobra>();
//                        listaeli.add(actiRaiz);
//                    boolean tieneproyectos = false;
//                    while (itpadres.hasNext()) {
//                        Actividadobra actpad = (Actividadobra) itpadres.next();
//                        listaeli.add(actpad);
//                        
//                        if (actpad.getStrdescactividad().compareTo("Ejecución del Convenio") == 0) {
//                            if (actpad.getActividadobras().size() > 0) {
//                                tieneproyectos = true;
//                                
//                            }
//                        }
//                        Iterator ithijos = actpad.getActividadobras().iterator();
//                            while (ithijos.hasNext()) {
//                                 Actividadobra acthija = (Actividadobra) ithijos.next();
//                                 listaeli.add(acthija);
//                            }
//                    }
//
//                    if (tieneproyectos) {
//                        mapaValidacionRangoPo.put(1, "El Plan Operativo ya posee proyectos que podrían verse afectados con esta modificación.\n"
//                                + "Las fechas deben mantenerse en: Fecha Acta de Inicio: "+ValidacionesPO.obtenerFecha(actiRaiz.getFechaInicio())+"\n"+
//                                   " Fecha Finalización: "+ValidacionesPO.obtenerFecha(actiRaiz.getFechaFin()));
////                        contrato.setFechaactaini(actiRaiz.getFechaInicio());
////                        contrato.setDatefechafin(actiRaiz.getFechaFin());
////                        contrato.setIntduraciondias(actiRaiz.getDuracion());
//                    } else {
//                       
//                        if(getSessionBeanCobra().getCobraGwtService().getContratoDto()!=null)
//                        {
//                            getSessionBeanCobra().getCobraGwtService().getContratoDto().getDependenciasGenerales().clear();
//                            getSessionBeanCobra().getCobraGwtService().getContratoDto().getActividadobras().clear();
//                        }
//                        
//                        
//                        
//                        getSessionBeanCobra().getCobraService().borrarActividadesParametricasPO(listaeli);
//                        
//                        contrato.getActividadobras().clear();
//
//                    }
//
//                }
//            }
//        }
//
////        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
////        if (!contrato.getActividadobras().isEmpty()) {
////
////            Iterator it = contrato.getActividadobras().iterator();
////            Actividadobra actiRaiz = (Actividadobra) it.next();
////
////            Date menorFechaInicio = obtenerMenorFechaInicio(new ArrayList<Actividadobra>(actiRaiz.getActividadobras()));
////            Date mayorFechaFin = obtenerMayorFechaFin(new ArrayList<Actividadobra>(actiRaiz.getActividadobras()));
////            if (contrato.getDatefechaini().compareTo(menorFechaInicio) > 0) {
////                mapaValidacionRangoPo.put(1, "La fecha de inicio no puede ser superior a " + sdf.format(menorFechaInicio));
////            }
////            if (contrato.getDatefechafin().compareTo(mayorFechaFin) < 0) {
////                mapaValidacionRangoPo.put(2, "La fecha de fin no puede ser inferior a " + sdf.format(mayorFechaFin));
////            }
////            if (!mapaValidacionRangoPo.isEmpty()) {
////                //Borramos las paramétricas para que se ecalculen con las nuevas fechas
////                List<ActividadobraDTO> listaactdto = new ArrayList<ActividadobraDTO>();
////                ActividadobraDTO activdadRaiz = (ActividadobraDTO) getSessionBeanCobra().getCobraGwtService().getContratoDto().getActividadobras().iterator().next();
////                encontrarActividadContratoDTO(activdadRaiz, listaactdto);
////                System.out.println("listaac = " + listaactdto.size());
////                
////                if (listaactdto.size() == 7) {
////                    mapaValidacionRangoPo.clear();
////                    contrato.getActividadobras().clear();
////                    getSessionBeanCobra().getCobraGwtService().getContratoDto().getDependenciasGenerales().clear();
////                    getSessionBeanCobra().getCobraGwtService().getContratoDto().getActividadobras().clear();
////                    lstActividadesEliminar.add(actiRaiz);
////                    getSessionBeanCobra().getCobraGwtService().setElimino(true);
////                }
////            }
////        }
////
//        return mapaValidacionRangoPo;
//    }
    public static Date obtenerMenorFechaInicio(List<Actividadobra> listaHijas) {
        if (!listaHijas.isEmpty()) {
            Date menor = listaHijas.get(0).getFechaInicio();
            for (int i = 1; i < listaHijas.size(); i++) {
                if (listaHijas.get(i).getFechaInicio().compareTo(menor) < 0) {
                    menor = listaHijas.get(i).getFechaInicio();
                }
            }
            return menor;
        }
        return null;
    }

    public static Date obtenerMayorFechaFin(List<Actividadobra> listaHijas) {
        if (!listaHijas.isEmpty()) {
            Date mayor = listaHijas.get(0).getFechaFin();
            for (int i = 1; i < listaHijas.size(); i++) {
                if (listaHijas.get(i).getFechaFin().compareTo(mayor) > 0) {
                    mayor = listaHijas.get(i).getFechaFin();
                }
            }
            return mayor;
        }
        return null;
    }

    public void validarModificacionFechasPO(Map<Integer, String> mapaValidacionFechasPO) {

        String msgError = "";
        if (mapaValidacionFechasPO.get(1) != null) {
            msgError = msgError + mapaValidacionFechasPO.get(1) + ",";
        }
        if (mapaValidacionFechasPO.get(2) != null) {
            msgError = msgError + mapaValidacionFechasPO.get(2);
        }
        FacesUtils.addErrorMessage(msgError);
        setMensajePlanOperativo(false, true, msgError);

    }

    public void listaxTipocontratoselect() {
        if (filtrocontrato.getTipocontratoselect() != 2) {
            if (filtrocontrato.getTipocontratoselect() == 0) {
                tipocontratoselectitem = tipocontrato;
                filtrocontrato.setTipocontratoselect(0);
                filtrocontrato.setTipocontrato(0);
                listarPorTipoContrato();
            } else {
                tipocontratoselectitem = tipocontratoconsultoria;
                filtrocontrato.setTipocontratoselect(1);
                filtrocontrato.setTipocontrato(0);
                listarPorTipoContrato();
            }
        } else {
            listarPorTipoContrato();
        }
    }

    public void encontrarActividadContrato(Actividadobra activdadRaiz, List<Actividadobra> listaActividades) {
        for (Iterator it = activdadRaiz.getActividadobras().iterator(); it.hasNext();) {
            Actividadobra actiHija = (Actividadobra) it.next();
            listaActividades.add(actiHija);
            encontrarActividadContrato(actiHija, listaActividades);

        }
    }

    public void encontrarActividadContratoDTO(ActividadobraDTO activdadRaiz, List<ActividadobraDTO> listaActividades) {
        for (ActividadobraDTO actiHija : activdadRaiz.getChildren()) {
            listaActividades.add(actiHija);
            encontrarActividadContratoDTO(actiHija, listaActividades);
        }
    }

    public void cargarActividadesEliminar(Actividadobra activdadRaiz) {
        if (getSessionBeanCobra().getCobraGwtService().isElimino()) {
            if (!lstTemporalActividades.isEmpty()) {
                List<Actividadobra> listaActividades = new ArrayList<Actividadobra>();
                encontrarActividadContrato(activdadRaiz, listaActividades);
                for (Actividadobra ac : lstTemporalActividades) {
                    Actividadobra actiBuscada = buscarActi(ac, listaActividades);
                    if (actiBuscada == null) {
                        if (!lstActividadesEliminar.contains(ac)) {
                            if (ac.getTipotareagantt() == 2) {
                                reembolsarValoresDeObraFuenteRecursos(ac.getObra().getObrafuenterecursosconvenioses());
                            }
                            lstActividadesEliminar.add(ac);
                        }
                    }
                }
            }
        }
    }

    public boolean validacionesBasicasConvenioPO(boolean isBorrador) {
////        Map<Integer, String> mapaValidacionFechasPO = validarFechasConvenioEnRangoPO(contrato);
////        if (!mapaValidacionFechasPO.isEmpty()) {
////            validarModificacionFechasPO(mapaValidacionFechasPO);
////            return false;
////        } else {
        validarNumeroContrato();
        if (isBorrador) {

            if (contrato.getDatefechaini() != null && contrato.getDatefechafin() != null) {
                if (contrato.getIntduraciondias() <= 0) {
                    validardatosbasicosplano = 3;
                    throw new ConvenioException(bundle.getString("validarfechafin"));
                }

                if (contrato.getFechaactaini() != null) {
                    if (contrato.getFechaactaini().compareTo(contrato.getDatefechaini()) < 0 || contrato.getFechaactaini().compareTo(contrato.getDatefechafin()) > 0) {
                        validardatosbasicosplano = 1;
                        throw new ConvenioException(bundle.getString("fechadesuscripcionplano"));
                    }
                }

            }

        } else {
            if (contrato.getDatefechaini() == null) {
                throw new ConvenioException(bundle.getString("fechaInicioNotNull"));
            } else if (contrato.getDatefechafin() == null) {
                throw new ConvenioException(bundle.getString("fechaFinalizacionNotNull"));
            } else if (contrato.getIntduraciondias() <= 0) {
                validardatosbasicosplano = 3;
                throw new ConvenioException(bundle.getString("validarfechafin"));
            } else if (contrato.getFechaactaini() == null) {
                validardatosbasicosplano = 2;
                throw new ConvenioException(bundle.getString("fechadesuscripcionvalida"));
            } else if (contrato.getFechaactaini().compareTo(contrato.getDatefechaini()) < 0 || contrato.getFechaactaini().compareTo(contrato.getDatefechafin()) > 0) {
                validardatosbasicosplano = 1;
                throw new ConvenioException(bundle.getString("fechadesuscripcionplano"));
            }
        }
        //}

        return true;
    }

    public void configuracionGuardadoPo(int estado, boolean vermensaje) {
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
//      Asignandole el valor del contrato en recursos terceros
        contrato.setNumrecursostercero(contrato.getNumvlrcontrato());
        contrato.setNumrecursosch(BigDecimal.ZERO);
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
        contrato.setEstadoconvenio(new Estadoconvenio(estado));
        contrato.setPolizacontratos(new LinkedHashSet(listapolizas));
        contrato.setBoolplanoperativo(false);
        contrato.setEncargofiduciario(null);
        contrato.setModalidadcontratista(null);
        //Guarda gerente de convenio, segun si es administrador o gerente de convenio.
        if (tercero.getIntcodigo() == 0) {
            contrato.setGerenteconvenio(getSessionBeanCobra().getUsuarioObra().getTercero());
        } else {
            contrato.setGerenteconvenio(getTercero());
        }

        //contrato.setNumvlrcontrato(getRecursosconvenio().getSumafuentes());                  
        if (!recursosconvenio.getLstFuentesRecursos().isEmpty()) {
//                                if (!contrato.getFuenterecursosconvenios().isEmpty()) {
            // recursosconvenio.setLstFuentesRecursos(new ArrayList<Fuenterecursosconvenio>(contrato.getFuenterecursosconvenios()));
            contrato.setFuenterecursosconvenios(new LinkedHashSet(recursosconvenio.getLstFuentesRecursos()));
//                                } else {
//                                    contrato.setFuenterecursosconvenios(new LinkedHashSet(recursosconvenio.getLstFuentesRecursos()));
//                                }

        }

        if (!mapaReembolsoConvenio.isEmpty()) {
            devolverValoresConvenio();
        }

        getSessionBeanCobra().getCobraService().guardarContrato(contrato, getSessionBeanCobra().getUsuarioObra());
        for (Polizacontrato poliza : listaPolizasEliminar) {
            getSessionBeanCobra().getCobraService().borrarPolizaContrato(poliza);
        }
        if (!listadocumentos.isEmpty()) {
            for (Documentoobra docContrato : listadocumentos) {
                try {
                    String nuevaRutaWeb = ArchivoWebUtil.copiarArchivo(
                            docContrato.getStrubicacion(),
                            MessageFormat.format(RutasWebArchivos.DOCS_CONTRATO, "" + getContrato().getIntidcontrato()),
                            true, false);
                    docContrato.setStrubicacion(nuevaRutaWeb);
                    getSessionBeanCobra().getCobraService().guardarDocumento(docContrato);

                } catch (FileNotFoundException ex) {
                    Logger.getLogger(NuevoContratoBasico.class
                            .getName()).log(Level.SEVERE, null, ex);
                } catch (ArchivoExistenteException ex) {
                    Logger.getLogger(NuevoContratoBasico.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
//                        } 
        }

        if (!recursosconvenio.getLstFuentesRecursosEliminar().isEmpty()) {
            getSessionBeanCobra().getCobraService().borrarFuentesRecursosConvenios(recursosconvenio.getLstFuentesRecursosEliminar());
            getRecursosconvenio().setLstFuentesRecursosEliminar(new ArrayList<Fuenterecursosconvenio>());
        }

        if (!recursosconvenio.getLstFuentesRecursos().isEmpty()) {
            getSessionBeanCobra().getCobraService().guardarFuentesRecursosConvenios(recursosconvenio.getLstFuentesRecursos());

        }

        if (!getContrato().getActividadobras().isEmpty()) {
            List<Actividadobra> lstactguardar = new ArrayList<Actividadobra>(getContrato().getActividadobras());
            lstactguardar.get(0).setContrato(getContrato());

            getSessionBeanCobra().getCobraService().guardarActividadObra(lstactguardar);

        }

        if (!getSessionBeanCobra().isConsulteContrato()) {
            if (getSessionBeanCobra().getCobraGwtService().isElimino()) {

                if (!lstActividadesEliminar.isEmpty()) {
                    getSessionBeanCobra().getCobraService().borrarActividadesPlanOperativo(lstActividadesEliminar);
                    getSessionBeanCobra().getCobraGwtService().setElimino(false);

                    mapaReembolsoConvenio.clear();
                }

            }
            if (getSessionBeanCobra().getCobraGwtService().getContratoDto() != null) {
                if (!getSessionBeanCobra().getCobraGwtService().getContratoDto().getDependenciasGenerales().isEmpty()) {
                    Actividadobra act = (Actividadobra) getContrato().getActividadobras().iterator().next();
                    getContrato().setDependenciasGenerales(CasteoGWT.castearSetDependenciasaListaDependenciasDto(getSessionBeanCobra().getCobraGwtService().getContratoDto().getDependenciasGenerales(), act));
                    getSessionBeanCobra().getCobraService().guardarDependencias(new ArrayList<Dependencia>(getContrato().getDependenciasGenerales()));
                    getSessionBeanCobra().getCobraGwtService().getContratoDto().setDependenciasGenerales(new HashSet<DependenciaDTO>());
                }
            }
            if (!getSessionBeanCobra().getCobraGwtService().getDependenciasEliminar().isEmpty()) {
                getSessionBeanCobra().getCobraService().borrarDependenciasPlanOperativo(
                        CasteoGWT.castearSetDependenciasaaeliminar(getSessionBeanCobra().getCobraGwtService().getDependenciasEliminar()));
                getSessionBeanCobra().getCobraGwtService().setDependenciasEliminar(new LinkedHashSet());
            }

            if (getFlujoCaja().isFlujoCajaIniciado()) {
                getFlujoCaja().guardarFlujoCaja();
            }
        }
        if (vermensaje) {
            setNumcontratotemporal(getContrato().getStrnumcontrato());
            FacesUtils.addInfoMessage(bundle.getString("losdatossehanguardado"));
            setMensajePlanOperativo(true, false, bundle.getString("losdatossehanguardado"));
        }
    }

    public void reembolsarValoresDeObraFuenteRecursos(Set<Obrafuenterecursosconvenios> setObraFuentesRecursos) {
        for (Obrafuenterecursosconvenios fuenteRecursoObra : setObraFuentesRecursos) {
            valoresReembolsarConvenio(fuenteRecursoObra.getFuenterecursosconvenio(), fuenteRecursoObra.getValor());
        }
    }

    public void valoresReembolsarConvenio(Fuenterecursosconvenio fuente, BigDecimal valor) {
        if (!mapaReembolsoConvenio.isEmpty()) {
            for (Fuenterecursosconvenio f : mapaReembolsoConvenio.keySet()) {
                if (f.getTercero().getStrnombrecompleto().equals(fuente.getTercero().getStrnombrecompleto()) && f.getVigencia() == fuente.getVigencia()) {
                    BigDecimal valorFuente = mapaReembolsoConvenio.get(f);
                    valorFuente = valorFuente.add(valor);
                    mapaReembolsoConvenio.put(f, valorFuente);
                } else {
                    mapaReembolsoConvenio.put(fuente, valor);
                }
            }
        } else {
            mapaReembolsoConvenio.put(fuente, valor);
        }
    }

    /*
     * metodo que se encarga de buscar una fuente recursos del convenio 
     * dependiendo de la entidad y la viencia
     */
    public Fuenterecursosconvenio buscarFuenteRecursos(int vigencia, String entidad) {
        for (Iterator it = contrato.getFuenterecursosconvenios().iterator(); it.hasNext();) {
            Fuenterecursosconvenio fuente = (Fuenterecursosconvenio) it.next();
            if (fuente.getVigencia() == vigencia && fuente.getTercero().getStrnombrecompleto().equals(entidad)) {
                return fuente;
            }
        }
        return null;
    }

    /*
     * metodo que se encarga de devolver el valor disponible
     * a la fuente corresponiente.
     */
    public void devolverValoresConvenio() {
        for (Fuenterecursosconvenio fuenterecursosconvenio : mapaReembolsoConvenio.keySet()) {
            Fuenterecursosconvenio fconvenio = buscarFuenteRecursos(fuenterecursosconvenio.getVigencia(), fuenterecursosconvenio.getTercero().getStrnombrecompleto());
            fconvenio.setValorDisponible(fconvenio.getValorDisponible().add(mapaReembolsoConvenio.get(fuenterecursosconvenio)));
        }
    }

    /*
     * metodo que se encarga de verificar si la actividad se encuentra 
     * dentro de las actividades actuales del plan operativo
     */
    public Actividadobra buscarActi(Actividadobra actiBuscar, List<Actividadobra> listaActividades) {
        for (Actividadobra acti : listaActividades) {
            if (actiBuscar.getOidactiviobra() != 0) {
                if (acti.getOidactiviobra() == actiBuscar.getOidactiviobra()) {
                    return acti;
                }
            }
        }
        return null;

    }

    public void modificarFuentesRecursosConvenioEstaAsociada(List<Fuenterecursosconvenio> lstFuenteRecursos) {
        for (Fuenterecursosconvenio fuente : lstFuenteRecursos) {
            BigDecimal valorActual = fuente.getValorcuotagerencia().add(fuente.getOtrasreservas()).add(fuente.getReservaiva());
            BigDecimal valorFinal = fuente.getValorDisponible().subtract(valorActual);
            if (fuente.getValoraportado().compareTo(valorFinal) != 0) {
                fuente.setEstaEnFuenteRecurso(true);
            } else {
                fuente.setEstaEnFuenteRecurso(false);
            }
        }
    }

    public void setMensajePlanOperativo(boolean ver, boolean error, String mensaje) {
        getContrato().setVermensajeguardado(ver);
        getContrato().setVermensajeerror(error);
        getContrato().setMensajeguardado(mensaje);
    }

    /**
     * Iniciar Fuentes de Recurso Contrato desde el menu superior administrar
     * convenio
     *
     *
     * @return Formulario Fuente Recurso
     */
    public String iraFuenteRecursos() {
        setRecursosconvenio(new RecursosConvenio(getContrato(), getSessionBeanCobra().getCobraService()));
        recursosconvenio.setLstFuentesRecursos(getSessionBeanCobra().getCobraService().obtenerFuentesRecursosConvenio(contrato.getIntidcontrato()));
        recursosconvenio.setVerAgregarRecurso(false);
        recursosconvenio.setVerEliminarRecurso(false);
        recursosconvenio.sumaFuentesRecursos();

        return "fuenteRecursos";
    }

    /**
     * Iniciar Plan Operativo proyecto en ejecución
     *
     * @return String
     */
    public String iraPlanOperativoEjecucion() {
        getSessionBeanCobra().setCargarcontrato(false);
        setRecursosconvenio(new RecursosConvenio(getContrato(), getSessionBeanCobra().getCobraService()));
        recursosconvenio.setLstFuentesRecursos(getSessionBeanCobra().getCobraService().obtenerFuentesRecursosConvenio(getContrato().getIntidcontrato()));
        recursosconvenio.sumaFuentesRecursos();
        recursosconvenio.setLstFuentesRecursos(getSessionBeanCobra().getCobraService().obtenerFuentesRecursosConvenio(contrato.getIntidcontrato()));
        recursosconvenio.setVerAgregarRecurso(false);
        recursosconvenio.setVerEliminarRecurso(false);
        recursosconvenio.sumaFuentesRecursos();
        cargarActividadesPlanOperativo();
        if (getContrato().getActividadobras().isEmpty()) {
            FacesUtils.addInfoMessage("El convenio no posee Plan Operativo");
            return null;
        } else {
            getContrato().setAuxiliarValorContrato(getContrato().getNumvlrcontrato());
            getContrato().setAuxiliarValorGerencia(getContrato().getNumValorCuotaGerencia());
            getContrato().setModolecturaplanop(true);
            return planOperativo();
        }
    }

    public void cargarActividadesPlanOperativo() {

        getContrato().setActividadobras(new LinkedHashSet<Actividadobra>());
        Actividadobra activiprincipal = getSessionBeanCobra().getCobraService().obtenerEstructuraActividadObraPlanOperativo(getContrato().getIntidcontrato());
        if (activiprincipal != null) {
            //listaProyectosConvenio.clear();
            getFlujoCaja().getProyectosPlanOperativo().clear();
            //extraerProyectosActividad(act);
            // modificarFuentesRecursosConvenioEstaAsociada(recursosconvenio.getLstFuentesRecursos());
            lstTodasActividades.clear();
            cargarActividadesConsultadas(activiprincipal);
            asignarNumeracionActividadesConsultadas(lstTodasActividades);
            lstActividadesEliminar.clear();
            lstTemporalActividades.clear();
            mapaReembolsoConvenio.clear();
            getSessionBeanCobra().getCobraGwtService().setElimino(false);
            encontrarActividadContrato(activiprincipal, lstTemporalActividades);
            getContrato().getActividadobras().add(activiprincipal);
            // LLenar dependencias
            getContrato().setDependenciasGenerales(CasteoGWT.encontrarDependenciaActividadObrad(activiprincipal));
            asignarPredecesorActividadesConsultadas(contrato.getDependenciasGenerales());
        }
    }

    public String cargarContratoConvenio() {
        cargarContrato(getSessionBeanCobra().getCobraService().encontrarContratoxId(mostrarContratoConvenio));

        return "consultarContrato";
    }

    public void limpiarPredecesoresActividad(Actividadobra actividadRaiz) {
        actividadRaiz.setPredecesor("");
        actividadRaiz.getLstPredecesores().clear();
        if (!actividadRaiz.getActividadobras().isEmpty()) {
            for (Iterator it = actividadRaiz.getActividadobras().iterator(); it.hasNext();) {
                Actividadobra actiHija = (Actividadobra) it.next();
                limpiarPredecesoresActividad(actiHija);
            }
        }
    }

    /**
     * Metodo para cargar el detalle del convenio que acabo de guardar.
     *
     */
    public void consultarContratoConvenio() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/zoom/Supervisor/consultarContratoConvenioDetalle.xhtml");

        } catch (IOException ex) {
            Logger.getLogger(NuevoContratoBasico.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Comienza medios de vida.
     *
     * @return
     */
    protected MarcoLogicoBean getMarcoLogicoBean() {
        return (MarcoLogicoBean) FacesUtils.getManagedBean("MarcoLogico$MarcoLogico");
    }

    public void confirmarCedulaContratista() {
        if (getContratista().getIntcedula().compareTo(BigInteger.ZERO) != 0) {
            if (getSessionBeanCobra().getCobraService().encontrarContratistaCedula(getContratista().getIntcedula()) != null) {
                setConfirmacioncedula(true);
            } else {
                setConfirmacioncedula(false);
            }
        } else {
            // FacesUtils.addErrorMessage("El Número de Identificación debe ser diferente a 0");
            setBoolcrearcontratista(false);
            setConfirmacioncedula(false);
        }

    }

    public void crearContratoConvenio() {

        Contrato fk_contrato = new Contrato();
        contrpadre = new Contrato();
        fk_contrato = contrato;
        limpiarContrato();
        varmostrarcontrpa = 1;
        varmostrarseleccionconveniosuperior = 1;
        preguntacontrato = 2;
        //limpiarContrato();
        booltipocontratoconvenio = false;
        tipoContCon = "Contrato";
        boolcontrconsultoria = false;
        getSessionBeanCobra().getCobraService().setAsoContratoCrear(false);
        contrpadre = fk_contrato;
        getContrato().setContrato(contrpadre);
        List<Tercero> listaentiddadcontratoconvenio = new ArrayList<Tercero>();

        if (fk_contrato.getContratista() != null) {
            listaentiddadcontratoconvenio = getSessionBeanCobra().getCobraService().obtenerEntidadContratantexContratista(fk_contrato.getContratista().getIntcodigocontratista());

            if (!listaentiddadcontratoconvenio.isEmpty() && listaentiddadcontratoconvenio.size() == 1) {
                contrato.setTercero(listaentiddadcontratoconvenio.get(0));
            } else {
                contrato.setTercero(fk_contrato.getTercero());
            }
        } else {
            FacesUtils.addErrorMessage(Propiedad.getValor("errorconfiguracionterceroconvenio"));
            limpiarContrato();
        }
    }

    public List<VistaProyectoAvanceFisicoConvenio> encontrarAvanceFisicoConvenio() {
        boolavanceproyectoconvenio = false;
        sumaproyectosconvenio = BigDecimal.ZERO;
        avancefisicoconvenio = BigDecimal.ZERO;
        valorconvenio = BigDecimal.ZERO;
        listaavancefisico = getSessionBeanCobra().getCobraService().encontrarAvanceFisicoConvenio(getContrato().getIntidcontrato());
        if (!listaavancefisico.isEmpty()) {
            boolavanceproyectoconvenio = true;
            for (VistaProyectoAvanceFisicoConvenio v : listaavancefisico) {
                v.setAvancefisico(v.getAvancefisico().setScale(2, RoundingMode.HALF_UP));
                v.setAvanceobra(v.getAvanceobra().setScale(2, RoundingMode.HALF_UP));
                setValorconvenio(v.getValorconvenio());
                setSumaproyectosconvenio(sumaproyectosconvenio.add(v.getValortotalobra()));
                setAvancefisicoconvenio(avancefisicoconvenio.add(v.getAvancefisico()));
                setAvancefisicoconvenio(avancefisicoconvenio.setScale(2, RoundingMode.HALF_UP));
            }
        }
        return listaavancefisico;
    }

    public void porcentaje() {
        if (getTipoAporte() == 1) {
            setBooltipoaporte(true);
        }
        if (getTipoAporte() == 2) {
            setBooltipoaporte(false);
        }

    }

    public void listarxTipocontrato() {
        if (filtrocontrato.getTipocontratoselect() != 2) {
            if (filtrocontrato.getTipocontratoselect() == 0) {
                tipocontratoselectitem = tipocontrato;
                filtrocontrato.setTipocontratoselect(0);
                filtrocontrato.setTipocontrato(0);
                habilitarModificacionTipoContrato();
            } else {
                tipocontratoselectitem = tipocontratoconsultoria;
                filtrocontrato.setTipocontratoselect(1);
                filtrocontrato.setTipocontrato(0);
                habilitarModificacionTipoContrato();
            }
        }
    }

    public void llenarListaComponentes() {
        listacomponentes = new ArrayList<Componente>();
        listacomponentes = getSessionBeanCobra().getCobraService().encontrarComponentesPadre();

        quitarComponentesAgregados();

        listasubcomponentes = new ArrayList<Componente>();
    }

    public void encontrarComponentesImpactados() {
        listacomponentesimpactados = new ArrayList<Contratocomponente>();
        
        listacomponentesimpactados = getSessionBeanCobra().getCobraService().encontrarComponentesImpactados(contrato);
    }

    public void encontrarComponentesHijos() {
        listasubcomponentes = new ArrayList<Componente>();
        listasubcomponentes = getSessionBeanCobra().getCobraService().encontrarComponentesHijos(componenteImpactado);

        quitarComponentesAgregados();

        subcomponenteImpactado = new Componente();
    }

    private void quitarComponentesAgregados() {
        for (Contratocomponente contratoComponente : listacomponentesimpactados) {
            listacomponentes.remove(contratoComponente.getComponente());

            if (listasubcomponentes.size() > 0) {
                listasubcomponentes.remove(contratoComponente.getComponente());

                if (listasubcomponentes.isEmpty()) {
                    listacomponentes.remove(componenteImpactado);
                }
            }
        }
    }

    public void agregarComponenteImpactado() {
        Contratocomponente contratocomponente = new Contratocomponente();
        Componente componenteAdicionar = new Componente();

        if (listasubcomponentes.size() > 0) {
            if (subcomponenteImpactado.getIntidcomponente() == 0) {
                for (Componente subcomponenteAdicionar : listasubcomponentes) {
                    contratocomponente = new Contratocomponente();

                    contratocomponente.setContrato(contrato);
                    contratocomponente.setComponente(subcomponenteAdicionar);

                    listacomponentesimpactados.add(contratocomponente);
                }
            } else {
                componenteAdicionar = getSessionBeanCobra().getCobraService().encontrarComponentePorId(subcomponenteImpactado);

                contratocomponente.setContrato(contrato);
                contratocomponente.setComponente(componenteAdicionar);

                listacomponentesimpactados.add(contratocomponente);
            }
        } else {
            componenteAdicionar = getSessionBeanCobra().getCobraService().encontrarComponentePorId(componenteImpactado);
            
            contratocomponente.setContrato(contrato);
            contratocomponente.setComponente(componenteAdicionar);

            listacomponentesimpactados.add(contratocomponente);
        }

        quitarComponentesAgregados();

        componenteImpactado = new Componente();
        subcomponenteImpactado = new Componente();
        listasubcomponentes = new ArrayList<Componente>();
    }

    public void eliminarComponenteImpactado() {
        listacomponentesimpactados.remove(tablaComponentesImpactados.getRowIndex());

        llenarListaComponentes();
    }
   
}
