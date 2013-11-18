/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.perfil;

import Seguridad.Encrypter;
import co.com.interkont.cobra.to.Cargo;
import co.com.interkont.cobra.to.EstadoCivil;
import co.com.interkont.cobra.to.Genero;
import co.com.interkont.cobra.to.JsfUsuario;
import co.com.interkont.cobra.to.Localidad;
import co.com.interkont.cobra.to.Tercero;
import co.com.interkont.cobra.to.Tipoidentificacion;
import co.com.interkont.cobra.to.Tipoorigen;
import co.com.interkont.cobra.to.Tipotercero;
import cobra.SessionBeanCobra;
import cobra.Supervisor.FacesUtils;
import cobra.Utilidades;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.faces.component.UIData;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import org.richfaces.component.UIDataTable;
import co.com.interkont.cobra.to.Grupo;
import cobra.Ciudadano.PerfilCiudadano;
import co.com.interkont.cobra.to.Modulo;
import co.com.interkont.cobra.to.Obra;
import co.com.interkont.cobra.to.Relacioncontratojsfusuario;
import co.com.interkont.cobra.to.Tipousuario;
import co.com.interkont.cobra.vista.VistaObraMapa;
import cobra.FiltroObra;
import cobra.gestion.HomeGestion;

/**
 *
 * @author David Andres Betancourth Botero
 * @author Cristian Daniel Gutierrez S.
 */
public class Usuario implements Serializable {

    public String username = "";
    public String contrasenaact = "";
    public String contrasenanuev = "";
    public String contrasenanuevcomf = "";
    private UIDataTable tablabuscarusuarios = new UIDataTable();
    private String usuLogin;
    private List<JsfUsuario> listausuarios = new ArrayList<JsfUsuario>();
    private JsfUsuario usuariomod = new JsfUsuario();
    /**
     * Variable para mostrar un selectitem del Tipo origen
     */
    private SelectItem[] tipoorigensoption;
    /**
     * Variable para mostrar un selectitem del departamento al que esta
     * registrado
     */
    private SelectItem[] deptosusuariooption;
    /* Variable para  listar los departamentos
     */
    private SelectItem[] localidadusuario;
    /**
     * /**
     * Variable para mostrar un selectitem con los municipios
     */
    private SelectItem[] municipiosusuariooption;
    /**
     * Variable que se le asigna la Localidad del Tercero
     */
    private String coddeptoselec = "";
    /**
     * Variable para mostrar un selectitem del tipo Tercero
     */
    private SelectItem[] tipotercerosoption;
    /**
     * Variable para mostrar un selectitem con los cargos
     */
    private SelectItem[] cargosoption;
    /**
     * Variable para mostrar un selectitem con los Generos
     */
    private SelectItem[] generosoption;
    /**
     * Variable para mostrar un selectitem con el Estado Civil
     */
    private SelectItem[] estadocivilsoption;
    /**
     * Variable para mostrar un selectitem con el Tipo de identificación
     */
    private SelectItem[] tipoidentificacionsoption;
    /**
     * Variable que se le asigna el departamento de expedición del documento
     */
    private String coddeptoexpdocumento;
    /**
     * Variable para mostrar un selectitem con los municipos que pertenecen al
     * departamento de expedición
     */
    private SelectItem[] municipiosexpdocumentooption;
    /**
     * Variable que se le asigna el departamento de nacimiento
     */
    private String coddeptonacimiento;
    /**
     * Variable para mostrar un selectitem con los municipos que pertenecen al
     * departamento de nacimiento
     */
    private SelectItem[] municipiosnacimientooption;
    /**
     * Variable para habilitar los campos de modificar
     */
    private boolean modificarUsuario = true;
    /**
     * Variable para habilitar el boton de Guardar o Cancelar
     */
    private boolean habilitarBotonGuardar = false;
    /**
     * Variable para habilitar el boton de Modificar Usuario
     */
    private boolean deshabilitarBotonModificar = true;
    /**
     * Variable para almacenar el estado del usuario (Activo,Inhabilitado)
     */
    private String estadousuario = "";
    /**
     * Variable para almacenar todas las localidades
     */
    private String todasLocalidad = "169";
    /**
     * Variable para almacenar el objeto de Utilidades
     */
    private Utilidades utilidad = new Utilidades();
    /**
     * Variable para almacenar el objeto ResourceBundle
     */
    private ResourceBundle bundle = getSessionBeanCobra().getBundle();
    /**
     * Variable para guardar la lista de las entidades Contratantes
     */
    private List<Tercero> listaentidades = new ArrayList<Tercero>();
    /**
     * Variable Utilizada para almacenar el selectBooleanCheckbox
     */
    private boolean seleccionEntidad;
    /**
     * Lista utilizada para mostrar las entidades seleccionadas
     */
    private Map<Tercero, Boolean> listaentidadbooleana = new HashMap<Tercero, Boolean>();
    /**
     * Tabla utilizada para mostrar la lista de las entidades
     */
    private UIDataTable datatablelistaentidades;
    /**
     * Tabla utilizada para mostrar las entidades seleccionadas
     */
    private UIDataTable datatablelistaentidadseleccionada;
    /**
     * Lista para almacenar las entidades seleccionadas
     */
    private ArrayList<Tercero> listaentidad = new ArrayList<Tercero>();
    /**
     * Variable para inhabilitar la tabla datatablelistaentidades
     */
    private boolean inhabilitarseleccionentidades = true;
    /**
     * Variable para mostrar un selectitem con los Grupo
     */
    private SelectItem[] gruposoption;
    /**
     * Variable para mostrar el id del grupo
     */
    private int intidgrupo = 0;
    /**
     * Variable Utilizada para guardar la entidad a buscar
     */
    public String strbuscarentidad = "";
    /**
     * Variable para determinar si se encontro algo en la busqueda
     */
    public boolean strnoencontrabusqueda = true;
    /**
     * Variable para mostrar un selectitem con lis tipos de usuario
     */
    private SelectItem[] tipousuariooption;
    /**
     * Variable utilizada para almacenar la lista de obras desde vistamapa
     * asociadas al usuario
     */
    private List<VistaObraMapa> listaobrasasociadas = new ArrayList<VistaObraMapa>();
    /**
     * Variable Utilizada para almacenar la lista de Las obras segun la entidad
     */
    private List<VistaObraMapa> listaobrasentidad = new ArrayList<VistaObraMapa>();
    /**
     * Variable utilizada para almacenar la lista las entidades asociadas al
     * Usuario
     */
    private List<Tercero> listaEntidadUsuario = new ArrayList<Tercero>();
    /**
     * Variable utilizada para almacenar la lista de contratos (convenios)
     * asociados al usuario
     */
    private List<Relacioncontratojsfusuario> listarelacioncontratousuario = new ArrayList<Relacioncontratojsfusuario>();
    /**
     * Variable Utilizada para almacenar el objecto Tercero
     */
    private Tercero entidad = new Tercero();
    /**
     * Variabe utilizada para guardar la entidad y la lista de obras
     */
    private Map<String, List> maplistaobras = new HashMap<String, List>();
    /**
     * Variable utilizada para deshabilitar la lista de proyectos asociados al
     * usuario si es por entidad
     */
    private boolean boolpanellistaproyecto = true;
    /**
     * Variable utilizada para deshabilitar la lista de las entidades asociadas
     * al usuario solo si es por (Localidad)
     */
    private boolean boolseleccionEntidad = true;
    /**
     * Variable que habilita informacion adicional solo al usuario si es por
     * Localidad
     */
    private boolean boolLocalidad = false;
    /**
     * Variable que almacena el objeto Relacioncontratojsfusuario
     */
    private Relacioncontratojsfusuario relacioncontratojsfusuario = new Relacioncontratojsfusuario();
    
    
    /* Variable para  listar los departamentos
     */
    private SelectItem[] entidadcreacionusuario;
    /* Variable para  listar los departamentos
     */
    private Tercero tercero;
    /**
     * /**
     * Inicio de los Get y Set de las variables anteriores
     */
    public Utilidades getUtilidad() {
        return utilidad;
    }

    public void setUtilidad(Utilidades utilidad) {
        this.utilidad = utilidad;
    }

    public String getTodasLocalidad() {
        return todasLocalidad;
    }

    public void setTodasLocalidad(String todasLocalidad) {
        this.todasLocalidad = todasLocalidad;
    }

    public String getEstadousuario() {
        return estadousuario;
    }

    public void setEstadousuario(String estadousuario) {
        this.estadousuario = estadousuario;
    }

    public boolean isModificarUsuario() {
        return modificarUsuario;
    }

    public void setModificarUsuario(boolean modificarUsuario) {
        this.modificarUsuario = modificarUsuario;
    }

    public boolean isHabilitarBotonGuardar() {
        return habilitarBotonGuardar;
    }

    public void setHabilitarBotonGuardar(boolean habilitarBotonGuardar) {
        this.habilitarBotonGuardar = habilitarBotonGuardar;
    }

    public boolean isDeshabilitarBotonModificar() {
        return deshabilitarBotonModificar;
    }

    public void setDeshabilitarBotonModificar(boolean deshabilitarBotonModificar) {
        this.deshabilitarBotonModificar = deshabilitarBotonModificar;
    }

    public SelectItem[] getTipoidentificacionsoption() {
        return tipoidentificacionsoption;
    }

    public void setTipoidentificacionsoption(SelectItem[] tipoidentificacionsoption) {
        this.tipoidentificacionsoption = tipoidentificacionsoption;
    }

    public String getCoddeptonacimiento() {
        return coddeptonacimiento;
    }

    public void setCoddeptonacimiento(String coddeptonacimiento) {
        this.coddeptonacimiento = coddeptonacimiento;
    }

    public SelectItem[] getMunicipiosnacimientooption() {
        return municipiosnacimientooption;
    }

    public void setMunicipiosnacimientooption(SelectItem[] municipiosnacimientooption) {
        this.municipiosnacimientooption = municipiosnacimientooption;
    }

    public String getCoddeptoexpdocumento() {
        return coddeptoexpdocumento;
    }

    public void setCoddeptoexpdocumento(String coddeptoexpdocumento) {
        this.coddeptoexpdocumento = coddeptoexpdocumento;
    }

    public String getCoddeptoselec() {
        return coddeptoselec;
    }

    public void setCoddeptoselec(String coddeptoselec) {
        this.coddeptoselec = coddeptoselec;
    }

    public JsfUsuario getUsuariomod() {
        return usuariomod;
    }

    public void setUsuariomod(JsfUsuario usuariomod) {
        this.usuariomod = usuariomod;
    }

    public String getUsuLogin() {
        return usuLogin;
    }

    public void setUsuLogin(String usuLogin) {
        this.usuLogin = usuLogin;
    }

    public String getContrasenaact() {
        return contrasenaact;
    }

    public void setContrasenaact(String contrasenaact) {
        this.contrasenaact = contrasenaact;
    }

    public String getContrasenanuev() {
        return contrasenanuev;
    }

    public void setContrasenanuev(String contrasenanuev) {
        this.contrasenanuev = contrasenanuev;
    }

    public String getContrasenanuevcomf() {
        return contrasenanuevcomf;
    }

    public void setContrasenanuevcomf(String contrasenanuevcomf) {
        this.contrasenanuevcomf = contrasenanuevcomf;
    }

    public SelectItem[] getCargosoption() {
        return cargosoption;
    }

    public void setCargosoption(SelectItem[] cargosoption) {
        this.cargosoption = cargosoption;
    }

    public SelectItem[] getDeptosusuariooption() {
        return deptosusuariooption;
    }

    public void setDeptosusuariooption(SelectItem[] deptosusuariooption) {
        this.deptosusuariooption = deptosusuariooption;
    }

    public SelectItem[] getLocalidadusuario() {
        return localidadusuario;
    }

    public void setLocalidadusuario(SelectItem[] localidadusuario) {
        this.localidadusuario = localidadusuario;
    }

    public SelectItem[] getEstadocivilsoption() {
        return estadocivilsoption;
    }

    public void setEstadocivilsoption(SelectItem[] estadocivilsoption) {
        this.estadocivilsoption = estadocivilsoption;
    }

    public SelectItem[] getGenerosoption() {
        return generosoption;
    }

    public void setGenerosoption(SelectItem[] generosoption) {
        this.generosoption = generosoption;
    }

    public List<JsfUsuario> getListausuarios() {
        return listausuarios;
    }

    public void setListausuarios(List<JsfUsuario> listausuarios) {
        this.listausuarios = listausuarios;
    }

    public SelectItem[] getMunicipiosexpdocumentooption() {
        return municipiosexpdocumentooption;
    }

    public void setMunicipiosexpdocumentooption(SelectItem[] municipiosexpdocumentooption) {
        this.municipiosexpdocumentooption = municipiosexpdocumentooption;
    }

    public SelectItem[] getMunicipiosusuariooption() {
        return municipiosusuariooption;
    }

    public void setMunicipiosusuariooption(SelectItem[] municipiosusuariooption) {
        this.municipiosusuariooption = municipiosusuariooption;
    }

    public UIDataTable getTablabuscarusuarios() {
        return tablabuscarusuarios;
    }

    public void setTablabuscarusuarios(UIDataTable tablabuscarusuarios) {
        this.tablabuscarusuarios = tablabuscarusuarios;
    }

    public SelectItem[] getTipoorigensoption() {
        return tipoorigensoption;
    }

    public void setTipoorigensoption(SelectItem[] tipoorigensoption) {
        this.tipoorigensoption = tipoorigensoption;
    }

    public SelectItem[] getTipotercerosoption() {
        return tipotercerosoption;
    }

    public void setTipotercerosoption(SelectItem[] tipotercerosoption) {
        this.tipotercerosoption = tipotercerosoption;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Tercero> getListaentidades() {
        return listaentidades;
    }

    public void setListaentidades(List<Tercero> listaentidades) {
        this.listaentidades = listaentidades;
    }

    public boolean isSeleccionEntidad() {
        return seleccionEntidad;
    }

    public void setSeleccionEntidad(boolean seleccionEntidad) {
        this.seleccionEntidad = seleccionEntidad;
    }

    public Map<Tercero, Boolean> getListaentidadbooleana() {
        return listaentidadbooleana;
    }

    public void setListaentidadbooleana(Map<Tercero, Boolean> listaentidadbooleana) {
        this.listaentidadbooleana = listaentidadbooleana;
    }

    public UIDataTable getDatatablelistaentidades() {
        return datatablelistaentidades;
    }

    public void setDatatablelistaentidades(UIDataTable datatablelistaentidades) {
        this.datatablelistaentidades = datatablelistaentidades;
    }

    public UIDataTable getDatatablelistaentidadseleccionada() {
        return datatablelistaentidadseleccionada;
    }

    public void setDatatablelistaentidadseleccionada(UIDataTable datatablelistaentidadseleccionada) {
        this.datatablelistaentidadseleccionada = datatablelistaentidadseleccionada;
    }

    public ArrayList<Tercero> getListaentidad() {
        return listaentidad;
    }

    public void setListaentidad(ArrayList<Tercero> listaentidad) {
        this.listaentidad = listaentidad;
    }

    public boolean isInhabilitarseleccionentidades() {
        return inhabilitarseleccionentidades;
    }

    public void setInhabilitarseleccionentidades(boolean inhabilitarseleccionentidades) {
        this.inhabilitarseleccionentidades = inhabilitarseleccionentidades;
    }

    public SelectItem[] getGruposoption() {
        return gruposoption;
    }

    public void setGruposoption(SelectItem[] gruposoption) {
        this.gruposoption = gruposoption;
    }

    public int getIntidgrupo() {
        return intidgrupo;
    }

    public void setIntidgrupo(int intidgrupo) {
        this.intidgrupo = intidgrupo;
    }

    public String getStrbuscarentidad() {
        return strbuscarentidad;
    }

    public void setStrbuscarentidad(String strbuscarentidad) {
        this.strbuscarentidad = strbuscarentidad;
    }

    public boolean isStrnoencontrabusqueda() {
        return strnoencontrabusqueda;
    }

    public void setStrnoencontrabusqueda(boolean strnoencontrabusqueda) {
        this.strnoencontrabusqueda = strnoencontrabusqueda;
    }

    public SelectItem[] getTipousuariooption() {
        return tipousuariooption;
    }

    public void setTipousuariooption(SelectItem[] tipousuariooption) {
        this.tipousuariooption = tipousuariooption;
    }   

    public Tercero getTercero() {
        System.out.println("tercero   " + getTercero().getIntcodigo());
        return tercero;
    }

    public void setTercero(Tercero tercero) {
        this.tercero = tercero;
    }

    public SelectItem[] getEntidadcreacionusuario() {
        return entidadcreacionusuario;
    }

    public void setEntidadcreacionusuario(SelectItem[] entidadcreacionusuario) {
        this.entidadcreacionusuario = entidadcreacionusuario;
    }

    public List<VistaObraMapa> getListaobrasasociadas() {
        return listaobrasasociadas;
    }

    public void setListaobrasasociadas(List<VistaObraMapa> listaobrasasociadas) {
        this.listaobrasasociadas = listaobrasasociadas;
    }

    public List<VistaObraMapa> getListaobrasentidad() {
        return listaobrasentidad;
    }

    public void setListaobrasentidad(List<VistaObraMapa> listaobrasentidad) {
        this.listaobrasentidad = listaobrasentidad;
    }

    public List<Tercero> getListaEntidadUsuario() {
        return listaEntidadUsuario;
    }

    public void setListaEntidadUsuario(List<Tercero> listaEntidadUsuario) {
        this.listaEntidadUsuario = listaEntidadUsuario;
    }

    public List<Relacioncontratojsfusuario> getListarelacioncontratousuario() {
        return listarelacioncontratousuario;
    }

    public void setListarelacioncontratousuario(List<Relacioncontratojsfusuario> listarelacioncontratousuario) {
        this.listarelacioncontratousuario = listarelacioncontratousuario;
    }

    public Tercero getEntidad() {
        return entidad;
    }

    public void setEntidad(Tercero entidad) {
        this.entidad = entidad;
    }

    public Map<String, List> getMaplistaobras() {
        return maplistaobras;
    }

    public void setMaplistaobras(Map<String, List> maplistaobras) {
        this.maplistaobras = maplistaobras;
    }

    public boolean isBoolpanellistaproyecto() {
        return boolpanellistaproyecto;
    }

    public void setBoolpanellistaproyecto(boolean boolpanellistaproyecto) {
        this.boolpanellistaproyecto = boolpanellistaproyecto;
    }

    public boolean isBoolseleccionEntidad() {
        return boolseleccionEntidad;
    }

    public void setBoolseleccionEntidad(boolean boolseleccionEntidad) {
        this.boolseleccionEntidad = boolseleccionEntidad;
    }

    public boolean isBoolLocalidad() {
        return boolLocalidad;
    }

    public void setBoolLocalidad(boolean boolLocalidad) {
        this.boolLocalidad = boolLocalidad;
    }

    public Relacioncontratojsfusuario getRelacioncontratojsfusuario() {
        return relacioncontratojsfusuario;
    }

    public void setRelacioncontratojsfusuario(Relacioncontratojsfusuario relacioncontratojsfusuario) {
        this.relacioncontratojsfusuario = relacioncontratojsfusuario;
    }
    
    /**
     * Constructor de la pagina, Se estan inicializando algunas variables.
     */
    public Usuario() {

        llenarTipoorigenUsuario();
        llenarTipoTercero();
        llenarCargos();
        llenarGeneros();
        llenarEstadoCivil();
        llenarTiposIdentificacion();
        llenarlocalidadusuario();
        cargarEntidades();
        llenarGrupos();
        llenarentidadcreacionusuario();
        llenarTipoUsuario();
    }

    public String initusu() {

        username = getSessionBeanCobra().getUsuarioObra().getUsuLogin();
        contrasenaact = "";
        contrasenanuev = "";
        contrasenanuevcomf = "";
        return "cambiocontra";

    }

    public String enviodatos() {

        return validarusu();
    }

    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }

    private String validarusu() {
        Pattern p;
        Matcher m;
        boolean resultado = false;

        try {

            String contra = Encrypter.getInstance().encrypt(contrasenaact);
            String contranuev = "";
            if (contra.equals(getSessionBeanCobra().getUsuarioObra().getUsuPasswd())) {

                if (contrasenanuev.equals(contrasenanuevcomf)) {

                    p = Pattern.compile("[^A-Za-z0-9]+");
                    m = p.matcher(contrasenanuev);
                    resultado = m.find();
                    boolean caracteresIlegales = false;

                    while (resultado) {
                        caracteresIlegales = true;
                        resultado = m.find();
                    }

                    if (caracteresIlegales) {
                        FacesUtils.addErrorMessage(getSessionBeanCobra().getBundle().getString("caracterespermitidos"));
                    } else {
                        contranuev = Encrypter.getInstance().encrypt(contrasenanuev);
                        getSessionBeanCobra().getUsuarioObra().setUsuPasswd(contranuev);
                        getSessionBeanCobra().getCobraService().guardarOrActualizarusernamecontrasenausu(getSessionBeanCobra().getUsuarioObra());

                        FacesUtils.addInfoMessage(getSessionBeanCobra().getBundle().getString("ingresocorrecto"));
                    }

                } else {
                    FacesUtils.addErrorMessage(getSessionBeanCobra().getBundle().getString("noconincidenlascontra"));
                }
            } else {
                FacesUtils.addErrorMessage(getSessionBeanCobra().getBundle().getString("ingresecorectamentesucontra"));
            }


        } catch (Exception ex) {
        }
        return null;
    }

    /**
     * Este metodo es utilizado para buscar el usuario por
     * (nombre,usuario,cedula)
     *
     * @return Retorna los usuario encontrados con estos criterios
     */
    public void buscarUsuarioporCriterios() {
        listausuarios = new ArrayList<JsfUsuario>();
        listausuarios = getSessionBeanCobra().getUsuarioService().buscarUsuario(usuLogin);
        
    }

    /**
     * Este metodo es utilizado mostrar el Perfil del usuario con la informacion
     *
     * @return Retorna la pagina gestionusuario
     */
    public String iniciarperfilusuario() {
        return "gestionusuario";
    }

    /**
     * Este metodo es utilizado para cargar toda la informacion del usuario.
     *
     * @return Retorna La informacion del usuario.
     */
    public String irperfilUsuario() {
        modificarUsuario = true;
        deshabilitarBotonModificar = true;
        habilitarBotonGuardar = false;
        usuariomod = (JsfUsuario) tablabuscarusuarios.getRowData();
        if (usuariomod.getTercero().getLocalidadByStrcodigolocalidad().getStrcodigolocalidad().length() > 3) {
            coddeptoselec = usuariomod.getTercero().getLocalidadByStrcodigolocalidad().getStrcodigolocalidad().substring(0, 5);
        } else {
            coddeptoselec = "169";
        }
        if (usuariomod.getTercero().getLocalidadByStrlocalidadexpdocumento() != null) {
            if (usuariomod.getTercero().getLocalidadByStrlocalidadexpdocumento().getStrcodigolocalidad().length() > 3) {
                coddeptoexpdocumento = usuariomod.getTercero().getLocalidadByStrlocalidadexpdocumento().getStrcodigolocalidad().substring(0, 5);
            } else {
                coddeptoexpdocumento = "16911";
            }
        } else {
            coddeptoexpdocumento = "16911";
            usuariomod.getTercero().setLocalidadByStrlocalidadexpdocumento(new Localidad(coddeptoexpdocumento));
        }

        if (usuariomod.getTercero().getLocalidadByStrlocalidadnacimiento() != null) {
            if (usuariomod.getTercero().getLocalidadByStrlocalidadnacimiento().getStrcodigolocalidad().length() > 3) {
                coddeptonacimiento = usuariomod.getTercero().getLocalidadByStrlocalidadnacimiento().getStrcodigolocalidad().substring(0, 5);
            } else {
                coddeptonacimiento = "16911";
            }
        } else {
            coddeptonacimiento = "16911";
            usuariomod.getTercero().setLocalidadByStrlocalidadnacimiento(new Localidad(coddeptonacimiento));

        }
        if (usuariomod.getTercero().getGenero() == null) {
            System.out.println("ingreso al if de genero");
            usuariomod.getTercero().setGenero(new Genero(3));
        }
        if (usuariomod.getTercero().getCargo() == null) {
            usuariomod.getTercero().setCargo(new Cargo(17, "No Aplica"));
        }
        if (usuariomod.getTercero().getTipoidentificacion() == null) {
            usuariomod.getTercero().setTipoidentificacion(new Tipoidentificacion(10, ""));
        }
        if (usuariomod.getTercero().getEstadoCivil() == null) {
            usuariomod.getTercero().setEstadoCivil(new EstadoCivil(3));
        }
        if (usuariomod.getUsuEstado().equals(false)) {
            estadousuario = "Inhabilitado";
        } else {
            estadousuario = "Activo";
        }
        usuariomod.getTercero().setTipotercero(getSessionBeanCobra().getCobraService().encontrarTipoTerceroPorId(usuariomod.getTercero().getTipotercero().getIntcodigotipotercero()));
        usuariomod.getTercero().setGenero(getSessionBeanCobra().getCobraService().encontrarGeneroPorId(usuariomod.getTercero().getGenero().getIntgenero()));
        usuariomod.getTercero().setTipoidentificacion(getSessionBeanCobra().getCobraService().encontrarTipoIdentificacionPorId(usuariomod.getTercero().getTipoidentificacion().getIntcodigotipoiden()));
        usuariomod.getTercero().setEstadoCivil(getSessionBeanCobra().getCobraService().encontrarEstadoCivilPorId(usuariomod.getTercero().getEstadoCivil().getIntestadoCivil()));
        usuariomod.setTipousuario(getSessionBeanCobra().getCobraService().encontrarTipoUsuarioPorId(getUsuariomod().getTipousuario().getIntcodigotipousuario()));
        llenarDeptosUsuario();
        llenarMunicipiosUsuario();
        llenarMunicipiosExpedicion();
        llenarMunicipiosNacimiento();
        return "perfilusuario";
    }

//    public void actualizarUsuario() {
//        getSessionBeanCobra().getUsuarioService().guardarOrActualizarUsuario(usuariomod);
//
//    }
    /**
     * Este metodo es utilizado para cargar el selectitem tipo de Origen
     *
     * @return No retorna ningun Valor.
     */
    public String llenarTipoorigenUsuario() {
        List<Tipoorigen> querycargartipoorigenusuario = getSessionBeanCobra().getCobraService().encontrarTiposOrigenes();
        tipoorigensoption = new SelectItem[querycargartipoorigenusuario.size()];
        int i = 0;
        for (Tipoorigen tipoorigenusuario : querycargartipoorigenusuario) {
            SelectItem usuario = new SelectItem(tipoorigenusuario.getIntidtipoorigen(), tipoorigenusuario.getStrnombre());
            tipoorigensoption[i++] = usuario;
        }
        return null;
    }

    /**
     * Este metodo es utilizado para cargar el selectitem de los departamentos
     * del usuario
     *
     * @return No retorna ningun Valor.
     */
    public void llenarDeptosUsuario() {
        List<Localidad> listaDeptos = getSessionBeanCobra().getCobraService().encontrarDepartamentos();
        deptosusuariooption = new SelectItem[listaDeptos.size()];

        int i = 0;

        for (Localidad depto : listaDeptos) {
            SelectItem dep = new SelectItem(depto.getStrcodigolocalidad(), depto.getStrdepartamento());
            deptosusuariooption[i++] = dep;
        }
    }

    public void llenarlocalidadusuario() {
        List<Localidad> listaDeptos = getSessionBeanCobra().getCobraService().encontrarDepartamentos();
        localidadusuario = new SelectItem[listaDeptos.size()];

        int i = 0;

        for (Localidad depto : listaDeptos) {
            SelectItem dep = new SelectItem(depto.getStrcodigolocalidad(), depto.getStrdepartamento());
            localidadusuario[i++] = dep;
        }
    }

    /**
     * Este metodo es utilizado obtener los municipios pertenecientes al depto
     *
     * @param coddepto: codigo de la Localidad
     * @param todos : boolean de si tiene todos los departamentos
     * @return Retorna la lista de los municipios.
     */
    public SelectItem[] obtenerMunicipiosporDepto(String coddepto, boolean todos) {
        List<Localidad> listaMunicipios = new ArrayList<Localidad>();
        listaMunicipios = getSessionBeanCobra().getCobraService().encontrarMunicipios(coddepto);
        int tam = listaMunicipios.size();
        if (todos) {
            tam = tam + 1;
        }
        SelectItem[] sel = new SelectItem[tam];
        int i = 0;

        for (Localidad muni : listaMunicipios) {
            SelectItem mun = new SelectItem(muni.getStrcodigolocalidad(), muni.getStrmunicipio());
            sel[i++] = mun;
        }
        if (todos) {
            sel[i] = new SelectItem("169", "Todos");
        }
        return sel;
    }

    /**
     * Este metodo es utilizado para cargar el selectitem de los municipios
     * pertenecientes al depto
     *
     * @return No retorna ningun Valor.
     */
    public String llenarMunicipiosUsuario() {

        if (coddeptoselec.compareTo("169") == 0) {
            municipiosusuariooption = new SelectItem[1];
            municipiosusuariooption[0] = new SelectItem("169", "Todos");
        } else {
            municipiosusuariooption = obtenerMunicipiosporDepto(coddeptoselec, true);
        }
        usuariomod.getTercero().getLocalidadByStrcodigolocalidad().setStrcodigolocalidad("169");
        return null;
    }

    /**
     * Este metodo es utilizado para cargar el selectitem de los municipios de
     * expedición
     *
     * @return No retorna ningun Valor.
     */
    public String llenarMunicipiosExpedicion() {
        municipiosexpdocumentooption = obtenerMunicipiosporDepto(coddeptoexpdocumento, false);
        //usuariomod.getTercero().getLocalidadByStrlocalidadexpdocumento().setStrcodigolocalidad(municipiosexpdocumentooption[0].getValue().toString());        
        return null;
    }

    /**
     * Este metodo es utilizado para cargar el selectitem de los municipios de
     * nacimiento
     *
     * @return No retorna ningun Valor.
     */
    public String llenarMunicipiosNacimiento() {

        municipiosnacimientooption = obtenerMunicipiosporDepto(coddeptonacimiento, false);
        // usuariomod.getTercero().getLocalidadByStrlocalidadnacimiento().setStrcodigolocalidad(municipiosnacimientooption[0].getValue().toString());      
        return null;
    }

    /**
     * Este metodo es utilizado para cargar el selectitem del Tipo tercero
     *
     * @return No retorna ningun Valor.
     */
    public void llenarTipoTercero() {
        List<Tipotercero> querycargartipotercero = getSessionBeanCobra().getCobraService().encontrarTiposTercero();

        tipotercerosoption = new SelectItem[querycargartipotercero.size()];
        int i = 0;
        for (Tipotercero tipotercerousuario : querycargartipotercero) {
            SelectItem usuario = new SelectItem(tipotercerousuario.getIntcodigotipotercero(), tipotercerousuario.getStrnombretipotercero());
            tipotercerosoption[i++] = usuario;
        }

    }

    /**
     * Este metodo es utilizado para cargar el selectitem de los cargos
     *
     * @return No retorna ningun Valor.
     */
    public void llenarCargos() {
        List<Cargo> listaCargo = getSessionBeanCobra().getCobraService().encontrarCargos();

        cargosoption = new SelectItem[listaCargo.size()];
        int i = 0;
        for (Cargo car : listaCargo) {
            SelectItem c = new SelectItem(car.getIntcargo(), car.getStrdescripcion());
            cargosoption[i++] = c;
        }
    }

    /**
     * Este metodo es utilizado para cargar el selectitem de Genero
     *
     * @return No retorna ningun Valor.
     */
    public void llenarGeneros() {
        List<Genero> listaGen = getSessionBeanCobra().getCobraService().encontrarGeneros();

        generosoption = new SelectItem[listaGen.size()];
        int i = 0;
        for (Genero gene : listaGen) {
            SelectItem genItem = new SelectItem(gene.getIntgenero(), gene.getStrnombre());
            generosoption[i++] = genItem;
        }
    }

    /**
     * Este metodo es utilizado para cargar el selectitem del Estado Civil
     *
     * @return No retorna ningun Valor.
     */
    public void llenarEstadoCivil() {
        List<EstadoCivil> listaec = getSessionBeanCobra().getCobraService().encontrarEstadosCiviles();

        estadocivilsoption = new SelectItem[listaec.size()];
        int i = 0;
        for (EstadoCivil ec : listaec) {
            SelectItem estCiv = new SelectItem(ec.getIntestadoCivil(), ec.getStrnombre());
            estadocivilsoption[i++] = estCiv;
        }
    }

    /**
     * Este metodo es utilizado para cargar el selectitem de los Tipos de
     * Identificación
     *
     * @return No retorna ningun Valor.
     */
    public void llenarTiposIdentificacion() {
        List<Tipoidentificacion> listaec = getSessionBeanCobra().getCobraService().encontrarTiposIdentificacion();

        tipoidentificacionsoption = new SelectItem[listaec.size()];
        int i = 0;
        for (Tipoidentificacion ec : listaec) {
            SelectItem estCiv = new SelectItem(ec.getIntcodigotipoiden(), ec.getStrnombre());
            tipoidentificacionsoption[i++] = estCiv;
        }
    }

    /**
     * Este metodo es utilizado habilitar los botones de Guardar y Cancelar y
     * deshabilitar el boton de Modificar
     *
     * @return No retorna ningun Valor.
     */
    public void habilitarModificarUsuario() {
        modificarUsuario = false;
        habilitarBotonGuardar = true;
        deshabilitarBotonModificar = false;
    }

    /**
     * Este metodo es utilizado habilitar el boton de Modificar y deshabilitar
     * los botones de Guardar y Cancelar
     *
     * @return Retorna el listado de los usuarios.
     */
    public String btnCancelar() {
        modificarUsuario = true;
        habilitarBotonGuardar = false;
        deshabilitarBotonModificar = true;
        return "gestionusuario";
    }

    /**
     * Método utilizado Guardar la Modificacion del usuario.
     *
     * @return No devuelve ningun valor
     */
    public String guardarModificacion() {

        if (validarInformacion()) {
            if (coddeptoselec.equals("169")) {
                usuariomod.getTercero().getLocalidadByStrcodigolocalidad().setStrcodigolocalidad(todasLocalidad);
            }
            usuariomod.getTercero().setStrnombrecompleto(usuariomod.getTercero().getStrnombre() + " " + usuariomod.getTercero().getStrapellido1() + " " + usuariomod.getTercero().getStrapellido2());
            getSessionBeanCobra().getUsuarioService().guardarOrActualizarUsuario(usuariomod);
            modificarUsuario = true;
            habilitarBotonGuardar = false;
            deshabilitarBotonModificar = true;
        }
        return null;
    }

    /**
     * Método utilizado para Validar el correo del usuario
     *
     * @return retorna si el correo es valido o invalido
     */
    public boolean validarInformacion() {

        if (utilidad.isEmail(usuariomod.getTercero().getStremail())) {
            FacesUtils.addInfoMessage(bundle.getString("losdatossehanguardado"));
            return true;
        } else {
            FacesUtils.addErrorMessage(bundle.getString("correoinvalido"));
            return false;
        }
    }

    /**
     * Método utilizado para Cargar las entidades correspondientes al tipo 2
     * (Entidades Nacionales)
     *
     * @return no retorna Nada
     */
    public void cargarEntidades() {
        listaentidades = getSessionBeanCobra().getCobraService().encontrarTercerosxTiposolicitante(2);
    }

    /**
     * Metodo para limpiar La seleccion de la entidad
     *
     * @return null
     */
    public void limpiarSeleccionEntidad() {
        cargarEntidades();
        seleccionEntidad = false;
        listaentidadbooleana.clear();
        listaentidad.clear();
        inhabilitarseleccionentidades = false;
        strbuscarentidad = "";
    }

    /**
     * Metodo Utilizado para seleccionar todo el selectBooleanCheckbox
     *
     * @return null
     * @param event
     */
    public void seleccionarTodo(ValueChangeEvent event) {
        seleccionEntidad = ((Boolean) event.getNewValue()).booleanValue();
        System.out.println("((Boolean) event.getNewValue()).booleanValue() = " + ((Boolean) event.getNewValue()).booleanValue());
        if (!seleccionEntidad) {
            limpiarSeleccionEntidad();
            System.out.println("event = " + event);
        }
    }

    /**
     * Metodo Utilizado para guardar en la lista una sola entidad seleccionada
     * en selectBooleanCheckbox
     *
     * @param event
     * @return null
     */
    public void seleccionarUnaEntidad(ValueChangeEvent event) {
        listaentidadbooleana.put((Tercero) datatablelistaentidades.getRowData(), (Boolean) event.getNewValue());

    }

    /**
     * Metodo Utilizado para Listar las entidades seleccionadas
     *
     * @return Lista tipo Tercero
     */
    public List<Tercero> listarEntidadesSeleccionadas() {
        if (isSeleccionEntidad()) {
            return getListaentidades();
        }
        List<Tercero> result = new ArrayList<Tercero>();
        Iterator<?> iterator = listaentidadbooleana.keySet().iterator();
        listaentidad = new ArrayList<Tercero>();
        while (iterator.hasNext()) {

            Tercero key = (Tercero) iterator.next();
            if (listaentidadbooleana.get(key)) {
                result.add(key);
                listaentidad.add(key);
            }
        }
        inhabilitarseleccionproyecto = true;
        if (listaentidad.size() < 1) {
            FacesUtils.addErrorMessage(bundle.getString("debeeligirunentidad"));
        }
        return result;

    }

    /**
     * Metodo Utilizado para Buscar la entidad y lista las entidades encontradas
     *
     * @param String getStrbuscarentidad
     *
     */
    public void buscarEntidad() {
        listaentidades = getSessionBeanCobra().getCobraService().buscarEntidadxNombre(getStrbuscarentidad());
        if (listaentidades.size() > 0) {
            inhabilitarseleccionentidades = true;
            strnoencontrabusqueda = true;

        } else {
            inhabilitarseleccionentidades = false;
            strnoencontrabusqueda = false;
            FacesUtils.addErrorMessage(bundle.getString("noseencontrolaentidad"));
        }
    }

    /**
     * Metodo Utilizado para Carhar en un Selectitem la lista de los grupos
     *
     * @return No retorna ningun valor
     */
    public void llenarGrupos() {
        List<Grupo> listaGrupo = getSessionBeanCobra().getCobraService().encontrarGrupos();

        gruposoption = new SelectItem[listaGrupo.size()];
        int i = 0;
        for (Grupo gene : listaGrupo) {
            SelectItem genItem = new SelectItem(gene.getGruGid(), gene.getGruNombre());
            gruposoption[i++] = genItem;
        }
    }

    /**
     * Metodo utilizado para cargar en un Selectitem el tipo de usuario
     *
     * @return No retorna ningun valor
     */
    public void llenarTipoUsuario() {
        List<Tipousuario> listaGrupo = getSessionBeanCobra().getCobraService().encontrarTipousuario();

        tipousuariooption = new SelectItem[listaGrupo.size()];
        int i = 0;
        for (Tipousuario gene : listaGrupo) {
            SelectItem genItem = new SelectItem(gene.getIntcodigotipousuario(), gene.getStrtipousuario());
            tipousuariooption[i++] = genItem;
        }
    }

    /**
     * Metodo Utilizado para cargar los proyectos segun la tipificacion del
     * usuario (Tipo usuario).
     */
    public void cargarEntidadesUsuario() {
        listaEntidadUsuario = new ArrayList<Tercero>();
        listaobrasasociadas = new ArrayList<VistaObraMapa>();
        listaobrasentidad = new ArrayList<VistaObraMapa>();
        listarelacioncontratousuario = new ArrayList<Relacioncontratojsfusuario>();
        switch (usuariomod.getTipousuario().getIntcodigotipousuario()) {
            //Ciudadano
            case 1:
                System.out.println("El usuario es de tipo ciudadano (No aplica)");
                break;
            //Localidad
            case 2:
                FiltroObra filtro = new FiltroObra();
                filtro.setStrcoddepto(usuariomod.getTercero().getLocalidadByStrcodigolocalidad().getStrcodigolocalidad());
                filtro.setStrmunicipio(usuariomod.getTercero().getLocalidadByStrcodigolocalidad().getStrcodigolocalidad());
                listaobrasasociadas = getSessionBeanCobra().getCobraService().encontrarVistaObrasJsfUsuario(usuariomod, filtro);
                listaobrasentidad.addAll(listaobrasasociadas);
                boolseleccionEntidad = false;
                boolpanellistaproyecto = true;
                boolLocalidad = true;
                System.out.println("El usuario es de tipo Localidad");
                break;
            //Relacion obra jsfusuario
            case 3:
                System.out.println("El usuario es de tipo relacionobrajsfusuario");
                listaEntidadUsuario = getSessionBeanCobra().getUsuarioService().encontrarEntidadUsuario(usuariomod);
                listaobrasasociadas = getSessionBeanCobra().getCobraService().encontrarRelacionObrajsfUsuario(usuariomod);
                cargarMaplistaEntidad();
                System.out.println("usuariomod.isRelacioncontrato()" + usuariomod.isRelacioncontrato());
                boolpanellistaproyecto = true;


                if (usuariomod.isRelacioncontrato()) {
                    listarelacioncontratousuario = getSessionBeanCobra().getCobraService().buscarRelacionContratoUsuario(usuariomod);

                    for (Relacioncontratojsfusuario jsfUsuario : listarelacioncontratousuario) {
                        jsfUsuario.setContrato(getSessionBeanCobra().getCobraService().encontrarContratoxId(jsfUsuario.getContrato().getIntidcontrato()));
                        System.out.println("jsfUsuario" + jsfUsuario.getContrato().getStrnombre());
                    }

                } else {
                }
                listaobrasentidad.addAll(listaobrasasociadas);
                boolseleccionEntidad = true;
                boolpanellistaproyecto = true;
                boolLocalidad = false;
                break;
            //Entidad
            case 4:
                System.out.println(" El Usuario es de tipo Entidad");
                boolseleccionEntidad = true;
                boolLocalidad = false;
                listaEntidadUsuario = getSessionBeanCobra().getUsuarioService().encontrarEntidadUsuario(usuariomod);
                if (usuariomod.isRelacioncontrato()) {

                    listarelacioncontratousuario = getSessionBeanCobra().getCobraService().buscarRelacionContratoUsuario(usuariomod);

                    for (Relacioncontratojsfusuario jsfUsuario : listarelacioncontratousuario) {
                        jsfUsuario.setContrato(getSessionBeanCobra().getCobraService().encontrarContratoxId(jsfUsuario.getContrato().getIntidcontrato()));
                        System.out.println("jsfUsuario" + jsfUsuario.getContrato().getStrnombre());
                    }
                    System.out.println("El usuario esta por relacioncontratoobra");
                    boolpanellistaproyecto = true;
                } else {
                    System.out.println("El usuario es supervisor de entidad");
                    boolpanellistaproyecto = false;
                }
                break;
        }
    }

    /**
     * Metodo Utilizado para Cargar la lista listaobrasasociadas segun el
     * usuario y la entidad (Asociadas a el).
     */
    public void cargarMaplistaEntidad() {
        System.out.println("ingresa la entidad seleccionada ");
        for (VistaObraMapa obrasasociada : listaobrasasociadas) {
            if (maplistaobras.get(obrasasociada.getTercero().getStrnombrecompleto()) == null) {
                List<VistaObraMapa> listaobra = new ArrayList<VistaObraMapa>();
                listaobra.add(obrasasociada);
                maplistaobras.put(obrasasociada.getTercero().getStrnombrecompleto(), listaobra);
            } else {
                List<VistaObraMapa> listaobra = maplistaobras.get(obrasasociada.getTercero().getStrnombrecompleto());
                listaobra.add(obrasasociada);
                maplistaobras.put(obrasasociada.getTercero().getStrnombrecompleto(), listaobra);
            }
        }

    }

    /**
     * Metodo Utilizado para asignarle a listaobrasentidad segun la lista
     * maplistaobras.
     */
    public void CargarObraEntidad() {
        if (maplistaobras.get(entidad.getStrnombrecompleto()) != null) {
            listaobrasentidad = maplistaobras.get(entidad.getStrnombrecompleto());
        } else {
            FacesUtils.addErrorMessage(getSessionBeanCobra().getBundle().getString("mensajedecreacionadm"));
            listaobrasentidad.clear();
            System.out.println("Entro al else");
        }
    }
    public void llenarentidadcreacionusuario() {
        List<Tercero> listaEntidades = getSessionBeanCobra().getUsuarioService().encontrarTotalEntidades();
        entidadcreacionusuario = new SelectItem[listaEntidades.size()];
        int i = 0;

        for (Tercero terce : listaEntidades) {
            SelectItem dep = new SelectItem(terce.getIntcodigo(), terce.getStrnombrecompleto());
            entidadcreacionusuario[i++] = dep;
        }
    }
    
    //Parte de ingresar usuario
    private UIDataTable datatablelistaobras;
    private boolean seleccionProyecto;
    private boolean  inhabilitarseleccionproyecto = false;

    public boolean isSeleccionProyecto() {
        return seleccionProyecto;
    }

    public void setSeleccionProyecto(boolean seleccionProyecto) {
        this.seleccionProyecto = seleccionProyecto;
    }

    public boolean isInhabilitarseleccionproyecto() {
        return inhabilitarseleccionproyecto;
    }

    public void setInhabilitarseleccionproyecto(boolean inhabilitarseleccionproyecto) {
        this.inhabilitarseleccionproyecto = inhabilitarseleccionproyecto;
    }
    
    public UIDataTable getDatatablelistaobras() {
        return datatablelistaobras;
    }
    

    public void setDatatablelistaobras(UIDataTable datatablelistaobras) {
        this.datatablelistaobras = datatablelistaobras;
    }
    private Map<VistaObraMapa, Boolean> listaproyectosbooleana = new HashMap<VistaObraMapa, Boolean>();

    public Map<VistaObraMapa, Boolean> getListaproyectosbooleana() {
        return listaproyectosbooleana;
    }

    public void setListaproyectosbooleana(Map<VistaObraMapa, Boolean> listaproyectosbooleana) {
        this.listaproyectosbooleana = listaproyectosbooleana;
    }
    private List<VistaObraMapa> listaobrasasociar = new ArrayList<VistaObraMapa>();

    public List<VistaObraMapa> getListaobrasasociar() {
        return listaobrasasociar;
    }

    public void setListaobrasasociar(List<VistaObraMapa> listaobrasasociar) {
        this.listaobrasasociar = listaobrasasociar;
    }
    Tercero entidadproyecto = new Tercero();

    public Tercero getEntidadproyecto() {
        return entidadproyecto;
    }

    public void setEntidadproyecto(Tercero entidadproyecto) {
        this.entidadproyecto = entidadproyecto;
    }

   public void cargarObras() {
     listaobrasasociar = getSessionBeanCobra().getCobraService().encontrarObrasPorTerceroVista(getEntidadproyecto());
     }

     public void seleccionarUnProyecto(ValueChangeEvent event) {
        listaproyectosbooleana.put((VistaObraMapa) datatablelistaobras.getRowData(), (Boolean) event.getNewValue());

    }
      private ArrayList<VistaObraMapa> listaproyecto = new ArrayList<VistaObraMapa>();

    public ArrayList<VistaObraMapa> getListaproyecto() {
        return listaproyecto;
    }

    public void setListaproyecto(ArrayList<VistaObraMapa> listaproyecto) {
        this.listaproyecto = listaproyecto;
    }


     public List<VistaObraMapa> listarProyectosSeleccionadas() {
        if (isSeleccionProyecto()) {
            
            return getListaobrasasociar();
        }
        List<VistaObraMapa> result = new ArrayList<VistaObraMapa>();
        Iterator<?> iterator = listaproyectosbooleana.keySet().iterator();
        listaproyecto = new ArrayList<VistaObraMapa>();
        while (iterator.hasNext()) {

            VistaObraMapa key = (VistaObraMapa) iterator.next();
            if (listaproyectosbooleana.get(key)) {
                result.add(key);
                listaproyecto.add(key);
            }
        }
        inhabilitarseleccionproyecto = true;
        if (listaproyecto.size() < 1) {
            FacesUtils.addErrorMessage("debe elegir un proyecto");
        }
        return result;
    }
     
     public void limpiarSeleccionproyecto() {
         inhabilitarseleccionproyecto = false;
         cargarObras();
        seleccionProyecto = false;
        listaproyectosbooleana.clear();
        listaproyecto.clear();
    }
}

