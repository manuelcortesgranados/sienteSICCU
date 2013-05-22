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
import co.com.interkont.cobra.to.Tipoidentificacion;
import co.com.interkont.cobra.to.Tipoorigen;
import co.com.interkont.cobra.to.Tipotercero;
import cobra.SessionBeanCobra;
import cobra.Supervisor.FacesUtils;
import cobra.Utilidades;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.model.SelectItem;
import org.richfaces.component.UIDataTable;

/**
 *
 * @author David Andres Betancourth Botero
 * @author Cristian Daniel Gutierrez S.
 */
public class Usuario  {

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
    /**
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
        System.out.println("usulogin = "+ usuLogin);
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
     * Este metodo es utilizado para buscar el usuario por (nombre,usuario,cedula)
     * @return Retorna los usuario encontrados con estos criterios
     */
    public void buscarUsuarioporCriterios() {
        System.out.println("usulogin del bena = " + usuLogin);
        System.out.println("entro aqui ejej = ");
        
        listausuarios = new ArrayList<JsfUsuario>();
        listausuarios = getSessionBeanCobra().getUsuarioService().buscarUsuario(usuLogin);
        System.out.println("lista = " + listausuarios);
    }
    /**
     * Este metodo es utilizado mostrar el Perfil del usuario con la informacion 
     * @return Retorna la pagina gestionusuario
     */ 
    public String iniciarperfilusuario() {
        return "gestionusuario";
    }
    /**
     * Este metodo es utilizado para cargar toda la informacion del usuario.
     * @return Retorna La informacion del usuario.
     */
    public String irperfilUsuario(int filaSeleccionada) {
        modificarUsuario = true;
        deshabilitarBotonModificar = true;
        habilitarBotonGuardar = false;
        Usuario usuario =(Usuario) FacesUtils.getManagedBean("Perfil$Usuario");
        usuario.getListausuarios().get(filaSeleccionada);       
        usuariomod = usuario.getListausuarios().get(filaSeleccionada);
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
     * Este metodo es utilizado para cargar el selectitem de los departamentos del usuario 
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
    /**
     * Este metodo es utilizado obtener los  municipios pertenecientes al depto
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
     * Este metodo es utilizado para cargar el selectitem de los municipios pertenecientes al depto
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
     * Este metodo es utilizado para cargar el selectitem de los municipios de expedición
     * @return No retorna ningun Valor.
     */
    public String llenarMunicipiosExpedicion() {
        municipiosexpdocumentooption = obtenerMunicipiosporDepto(coddeptoexpdocumento, false);
        //usuariomod.getTercero().getLocalidadByStrlocalidadexpdocumento().setStrcodigolocalidad(municipiosexpdocumentooption[0].getValue().toString());        
        return null;
    }
     /**
     * Este metodo es utilizado para cargar el selectitem de los municipios de nacimiento
     * @return No retorna ningun Valor.
     */
    public String llenarMunicipiosNacimiento() {

        municipiosnacimientooption = obtenerMunicipiosporDepto(coddeptonacimiento, false);
        // usuariomod.getTercero().getLocalidadByStrlocalidadnacimiento().setStrcodigolocalidad(municipiosnacimientooption[0].getValue().toString());      
        return null;
    }
     /**
     * Este metodo es utilizado para cargar el selectitem del Tipo tercero
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
     * Este metodo es utilizado para cargar el selectitem de los Tipos de Identificación
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
     * Este metodo es utilizado habilitar los botones de Guardar y Cancelar
     * y deshabilitar el boton de Modificar
     * @return No retorna ningun Valor.
     */
    public String habilitarModificarUsuario() {
        modificarUsuario = false;
        habilitarBotonGuardar = true;
        deshabilitarBotonModificar = false;

        return null;
    }
     /**
     * Este metodo es utilizado habilitar el boton de Modificar
     * y deshabilitar los botones de Guardar y Cancelar
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
}
