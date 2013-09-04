/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.Ciudadano;

import Seguridad.Encrypter;
import co.com.interkont.cobra.to.Cargo;
import co.com.interkont.cobra.to.Comentarioobra;
import co.com.interkont.cobra.to.Contratista;
import co.com.interkont.cobra.to.Imagenevolucionobra;
import co.com.interkont.cobra.to.JsfUsuario;
import co.com.interkont.cobra.to.JsfUsuarioGrupoId;
import co.com.interkont.cobra.to.Localidad;
import co.com.interkont.cobra.to.Opinionciudadano;
import co.com.interkont.cobra.to.Tercero;
import co.com.interkont.cobra.to.Tipoidentificacion;
import co.com.interkont.cobra.to.Tipoopinion;
import co.com.interkont.cobra.to.Tipoorigen;
import co.com.interkont.cobra.to.Tiposolicitante;
import co.com.interkont.cobra.to.Tipotercero;
import co.com.interkont.cobra.to.Tipousuario;
import co.com.interkont.cobra.to.utilidades.Propiedad;
import co.com.interkont.cobra.vista.VistaObraMapa;
import cobra.ArchivoWeb;
import cobra.CargadorArchivosWeb;
import cobra.ControlGerencial.ControlGerencial;
import cobra.EnvioCorreosCobra;
import cobra.RedimensionarImagen;
import cobra.SessionBeanCobra;
import cobra.Supervisor.AdministrarObraNew;
import cobra.Supervisor.FacesUtils;

import cobra.Supervisor.ILifeCycleAware;
import cobra.Utilidades;
import cobra.perfil.Usuario;
import cobra.util.ArchivoWebUtil;
import cobra.util.RutasWebArchivos;
import com.interkont.cobra.exception.ArchivoExistenteException;
import com.interkont.cobra.util.DatoPie;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import org.richfaces.component.UIRepeat;

/**
 * Clase que permite toda la interacción con el perfil
 * ciudadano,persistencia,consulta
 *
 * @author Leonardo Montes
 * @author Diana Taborda
 */
public class PerfilCiudadano  implements ILifeCycleAware, Serializable {

    private String contrasena = "";
    private String codDepartamentoCiudadano = "";
    private String nomImaCiu = "";
    /**
     * Utilizada para anexar imagen en el perfil de comentarios
     */
    private SelectItem[] ProfesionOption;
    private SelectItem[] DepartamentoCiudadano;
    private SelectItem[] MunicipioCiudadano;
    private ResourceBundle bundle = getSessionBeanCobra().getBundle();
    /**
     * Variable utilizada para mostrar los comentarios asociados a ese usuario
     */
    // private boolean boolperfilusuario = false;
    /**
     * Contar la cantidad de caracteres de manera descendente
     */
    private int contarmensaje = 400;
    /**
     * Objeto para acceder a la validación del mail
     */
    private Utilidades utilidad = new Utilidades();
    /**
     * Almacenar la imagen que muestra por defecto
     */
    // private String pathImagen = "/resources/imgs/bt_user.png";
    /**
     * Iniciar el bean de subir archivo para este atributo una imagen
     */
    private CargadorArchivosWeb cargadorFoto = new CargadorArchivosWeb();
    /**
     * Asignar Nombre de la imagen a guardar
     */
    private String nomImagen = "";
    /**
     * Estas variables se utilizan para mostrar los datos del dashboard
     */
    private DatoPie persoafect = new DatoPie();
    private DatoPie persodamni = new DatoPie();
    private DatoPie hogaafect = new DatoPie();
    private DatoPie hogadam = new DatoPie();
    private String nombretitulo;
    private String pathImagen_par = "";
    private CargadorArchivosWeb cargadorDocumento = new CargadorArchivosWeb();
    private CargadorArchivosWeb cargadorImagen = new CargadorArchivosWeb();
    /**
     * Camputar tabla para ir al usuario general
     */
    private UIRepeat repeatusuario = new UIRepeat();
    private UIRepeat repeatopinion = new UIRepeat();
    private String obraAsiVa = new String();
    private String obraDeberiaIr = new String();
    private boolean cambioFotoCiudadano;

    public boolean isCambioFotoCiudadano() {
        return cambioFotoCiudadano;
    }
    public void setCambioFotoCiudadano(boolean cambioFotoCiudadano) {
        this.cambioFotoCiudadano = cambioFotoCiudadano;
    }
    
    public UIRepeat getRepeatopinion() {
        return repeatopinion;
    }

    public void setRepeatopinion(UIRepeat repeatopinion) {
        this.repeatopinion = repeatopinion;
    }
    /**
     * Computar tabla para ir al usuario perfil
     */
    private UIRepeat repeatusuarioperfil = new UIRepeat();
    /**
     * Activar perfil de otro ciudadano
     */
    private boolean boolotrousuario = true;
    /**
     * Lista para almacenar las opiniones según la tipificación.
     */
    private List<Opinionciudadano> listopinion = new ArrayList<Opinionciudadano>();
    /*
     Mostrar el nombre Segun la opinion
     */
    private String opinion;

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }
        
    public UIRepeat getRepeatusuarioperfil() {
        return repeatusuarioperfil;
    }

    public void setRepeatusuarioperfil(UIRepeat repeatusuarioperfil) {
        this.repeatusuarioperfil = repeatusuarioperfil;
    }

    public List<Opinionciudadano> getListopinion() {
        return listopinion;
    }

    public void setListopinion(List<Opinionciudadano> listopinion) {
        this.listopinion = listopinion;
    }

    public boolean isBoolotrousuario() {
        return boolotrousuario;
    }

    public void setBoolotrousuario(boolean boolotrousuario) {
        this.boolotrousuario = boolotrousuario;
    }

    public UIRepeat getRepeatusuario() {
        return repeatusuario;
    }

    public void setRepeatusuario(UIRepeat repeatusuario) {
        this.repeatusuario = repeatusuario;
    }

    public String getPathImagen_par() {
        return pathImagen_par;
    }

    public void setPathImagen_par(String pathImagen_par) {
        this.pathImagen_par = pathImagen_par;
    }

    public CargadorArchivosWeb getCargadorDocumento() {
        return cargadorDocumento;
    }

    public void setCargadorDocumento(CargadorArchivosWeb cargadorDocumento) {
        this.cargadorDocumento = cargadorDocumento;
    }

    public CargadorArchivosWeb getCargadorImagen() {
        return cargadorImagen;
    }

    public void setCargadorImagen(CargadorArchivosWeb cargadorImagen) {
        this.cargadorImagen = cargadorImagen;
    }

    public String getNombretitulo() {
        return nombretitulo;
    }

    public void setNombretitulo(String nombretitulo) {
        this.nombretitulo = nombretitulo;
    }

    public int getContarmensaje() {
        return contarmensaje;
    }

    public void setContarmensaje(int contarmensaje) {
        this.contarmensaje = contarmensaje;
    }

    public String getNomImaCiu() {
        return nomImaCiu;
    }

    public void setNomImaCiu(String nomImaCiu) {
        this.nomImaCiu = nomImaCiu;
    }

//    public boolean isBoolperfilusuario() {
//        return boolperfilusuario;
//    }
//
//    public void setBoolperfilusuario(boolean boolperfilusuario) {
//        this.boolperfilusuario = boolperfilusuario;
//    }
    public DatoPie getHogaafect() {
        return hogaafect;
    }

    public void setHogaafect(DatoPie hogaafect) {
        this.hogaafect = hogaafect;
    }

    public DatoPie getHogadam() {
        return hogadam;
    }

    public void setHogadam(DatoPie hogadam) {
        this.hogadam = hogadam;
    }

    public DatoPie getPersoafect() {
        return persoafect;
    }

    public void setPersoafect(DatoPie persoafect) {
        this.persoafect = persoafect;
    }

    public DatoPie getPersodamni() {
        return persodamni;
    }

    public void setPersodamni(DatoPie persodamni) {
        this.persodamni = persodamni;
    }

    public String getNomImagen() {
        return nomImagen;
    }

    public void setNomImagen(String nomImagen) {
        this.nomImagen = nomImagen;
    }

    public CargadorArchivosWeb getCargadorFoto() {
        return cargadorFoto;
    }

    public void setCargadorFoto(CargadorArchivosWeb cargadorFoto) {
        this.cargadorFoto = cargadorFoto;
    }

//    public String getPathImagen() {
//        return pathImagen;
//    }
//
//    public void setPathImagen(String pathImagen) {
//        this.pathImagen = pathImagen;
//    }
    public Utilidades getUtilidad() {
        return utilidad;
    }

    public void setUtilidad(Utilidades utilidad) {
        this.utilidad = utilidad;
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public SelectItem[] getProfesionOption() {
        return ProfesionOption;
    }

    public void setProfesionOption(SelectItem[] ProfesionOption) {
        this.ProfesionOption = ProfesionOption;
    }

    public String getCodDepartamentoCiudadano() {
        return codDepartamentoCiudadano;
    }

    public void setCodDepartamentoCiudadano(String codDepartamentoCiudadano) {
        this.codDepartamentoCiudadano = codDepartamentoCiudadano;
    }

    public SelectItem[] getDepartamentoCiudadano() {
        return DepartamentoCiudadano;
    }

    public void setDepartamentoCiudadano(SelectItem[] DepartamentoCiudadano) {
        this.DepartamentoCiudadano = DepartamentoCiudadano;
    }

    public SelectItem[] getMunicipioCiudadano() {
        return MunicipioCiudadano;
    }

    public void setMunicipioCiudadano(SelectItem[] MunicipioCiudadano) {
        this.MunicipioCiudadano = MunicipioCiudadano;
    }
    private UIRepeat repeatslider = new UIRepeat();

    public UIRepeat getRepeatslider() {
        return repeatslider;
    }

    public void setRepeatslider(UIRepeat repeatslider) {
        this.repeatslider = repeatslider;
    }

    public String getObraAsiVa() {
        return obraAsiVa;
    }

    public void setObraAsiVa(String obraAsiVa) {
        this.obraAsiVa = obraAsiVa;
    }

    public String getObraDeberiaIr() {
        return obraDeberiaIr;
    }

    public void setObraDeberiaIr(String obraDeberiaIr) {
        this.obraDeberiaIr = obraDeberiaIr;
    }
    private VistaObraMapa vistaobraciudadano;

    public VistaObraMapa getVistaobraciudadano() {
        return vistaobraciudadano;
    }

    public void setVistaobraciudadano(VistaObraMapa vistaobraciudadano) {
        this.vistaobraciudadano = vistaobraciudadano;
    }

    /**
     * Constructor del perfilCiudadano el cual invoca los métodos necesarios
     * para cargar combos
     */
    public PerfilCiudadano() {
        
    }

    @Override
    public void prerender() {
        if (getAdministrarObraNew().activocomentarios) {
            getAdministrarObraNew().prerender();
        }
    }

    /**
     * Este método se utiliza para inicializar y llenar todos los combos
     */
    public void iniciarCombos() {
        llenarProfesion();
        llenarDepartamentoCiudadano();
        llenarMunicipioCiudadanoModi();

        
    }

    /**
     * Este método se utiliza para inicializar ciudadano
     * @return 
     */
    public String inicializarCiudadano() {
        iniciarCombos();
        limpiarCiudadano();
        // 

        return "ingresarciudadano";
    }

    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }

    protected AdministrarObraNew getAdministrarObraNew() {
        return (AdministrarObraNew) FacesUtils.getManagedBean("Supervisor$AdministrarObraNew");
    }

    protected ControlGerencial getControlGerencial() {
        return (ControlGerencial) FacesUtils.getManagedBean("ControlGerencial");
    }

    /**
     * Llena un selectitem con una lista de tipo cargo.
     */
    public void llenarProfesion() {
        List<Cargo> listaCargo = getSessionBeanCobra().getCobraService().encontrarCargos();

        ProfesionOption = new SelectItem[listaCargo.size()];
        int i = 0;
        for (Cargo car : listaCargo) {
            SelectItem c = new SelectItem(car.getIntcargo(), car.getStrdescripcion());

            ProfesionOption[i++] = c;
        }
    }

    /**
     * Este metodo se utiliza para llenar un selectitem con un listado de
     * departamentos.
     *
     * @return null
     */
    public String llenarDepartamentoCiudadano() {
        getSessionBeanCobra().getCiudadanoservice().setListaDeptos(getSessionBeanCobra().getCobraService().encontrarDepartamentos());

        DepartamentoCiudadano = new SelectItem[getSessionBeanCobra().getCiudadanoservice().getListaDeptos().size()];
        int i = 0;

        for (Localidad depto : getSessionBeanCobra().getCiudadanoservice().getListaDeptos()) {
            SelectItem dep = new SelectItem(depto.getStrcodigolocalidad(), depto.getStrdepartamento());
            if (i == 0) {
                codDepartamentoCiudadano = depto.getStrcodigolocalidad();
            }
            DepartamentoCiudadano[i++] = dep;
        }
        return null;
    }

    public String llenarMunicipioCiudadano() {

        getSessionBeanCobra().getCiudadanoservice().setListaMunicipios(getSessionBeanCobra().getCobraService().encontrarMunicipios(codDepartamentoCiudadano));
        MunicipioCiudadano = new SelectItem[getSessionBeanCobra().getCiudadanoservice().getListaMunicipios().size()];
        int i = 0;
        for (Localidad muni : getSessionBeanCobra().getCiudadanoservice().getListaMunicipios()) {
            SelectItem mun = new SelectItem(muni.getStrcodigolocalidad(), muni.getStrmunicipio());
            MunicipioCiudadano[i++] = mun;
        }
        return null;
    }

    public String llenarMunicipioCiudadanoModi() {

        getSessionBeanCobra().getCiudadanoservice().setListaMunicipios(getSessionBeanCobra().getCobraService().encontrarMunicipios(codDepartamentoCiudadano));
        MunicipioCiudadano = new SelectItem[getSessionBeanCobra().getCiudadanoservice().getListaMunicipios().size()];
        int i = 0;
        for (Localidad muni : getSessionBeanCobra().getCiudadanoservice().getListaMunicipios()) {
            SelectItem mun = new SelectItem(muni.getStrcodigolocalidad(), muni.getStrmunicipio());
            if (i == 0) {
                mun = new SelectItem(muni.getStrcodigolocalidad(), muni.getStrmunicipio());
            }
            MunicipioCiudadano[i++] = mun;
        }
        return null;
    }

    public String finalizarGuardar_action() {
        if (getSessionBeanCobra().getCiudadanoservice().isBoolmensajeguardar()) {
            try {
                guardarCiudadano();
                
            } catch (Exception ex) {
                Logger.getLogger(PerfilCiudadano.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "volver";
    }

    /**
     * Guardar el ingreso de un nuevo ciudadano
     *
     * @return No devuelve ningún valor
     * @author Leonardo Montes
     *
     */
    public void guardarCiudadano()  {
        if (validarusuario()) {
            //inicializar atributos del objeto tercero
            getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero().setStrnombrecompleto(getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero().getStrnombre());
            getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero().setLocalidadByStrcodigolocalidad(new Localidad("169"));
            getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero().setLocalidadByStrlocalidadnacimiento(new Localidad("169"));
            getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero().setTipoidentificacion(new Tipoidentificacion(6, "NIT"));
            getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero().setTipotercero(new Tipotercero(1));
            getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero().setTipoOrigen(new Tipoorigen(4, "Nacional"));
            getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero().setIntcedula("faltante");
            getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero().setDateusuariocreacion(new Date());
            getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero().setStrdireccionprincipal("faltante");
            getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero().setTiposolicitante(new Tiposolicitante(5));
            getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero().setBoolestado(true);
            getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero().setBoolobraslocalidad(true);
            getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero().setStrtelefono1("Faltante");
            getSessionBeanCobra().getCiudadanoservice().getCiudadano().setUsuFchaCrcion(new Date());
            getSessionBeanCobra().getCiudadanoservice().getCiudadano().setUsuFchaVncmnto(new Date(2012, 11, 31));
            getSessionBeanCobra().getCiudadanoservice().getCiudadano().setUsuEstado(Boolean.FALSE);
            getSessionBeanCobra().getCiudadanoservice().getCiudadano().setTipousuario(new  Tipousuario(1));
            getSessionBeanCobra().getCiudadanoservice().guardarTercero(getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero());
            getSessionBeanCobra().getCiudadanoservice().guardarCiudadano(getSessionBeanCobra().getCiudadanoservice().getCiudadano());
            String rutaFoto = null;
            try {
                rutaFoto = ArchivoWebUtil.copiarArchivo(
                        RutasWebArchivos.TMP+nomImagen, 
                        MessageFormat.format(RutasWebArchivos.IMAGENES_CIUDADANO, ""+getSessionBeanCobra().getCiudadanoservice().getCiudadano().getUsuId()), 
                        true, 
                        false
                    );
            } catch (FileNotFoundException ex) {
                Logger.getLogger(PerfilCiudadano.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ArchivoExistenteException ex) {
                Logger.getLogger(PerfilCiudadano.class.getName()).log(Level.SEVERE, null, ex);
            }
            getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero().setStrfoto(rutaFoto);
            getSessionBeanCobra().getCiudadanoservice().guardarTercero(getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero());
            Comentarioobra comen = new Comentarioobra();
            comen.setDatefecha(new Date());
            comen.setJsfUsuario(getSessionBeanCobra().getCiudadanoservice().getCiudadano());
            comen.setStrdesccoment(getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero().getStrnombrecompleto() + " ya es parte de " + bundle.getString("cobra"));

            getSessionBeanCobra().getCiudadanoservice().guardarComentarioObra(comen);
            getSessionBeanCobra().getCiudadanoservice().getJsfusuariogrupo().setId(new JsfUsuarioGrupoId(getSessionBeanCobra().getCiudadanoservice().getCiudadano().getUsuId(), 6, 21));
            getSessionBeanCobra().getCiudadanoservice().guardarJsfUsuarioGrupo(getSessionBeanCobra().getCiudadanoservice().getJsfusuariogrupo());
            getSessionBeanCobra().getCiudadanoservice().setMensaje(bundle.getString("mensajecorreo"));
            getSessionBeanCobra().getCiudadanoservice().setBoolmensajeguardar(true);
            try {
                enviarCorreoConfirmar();
            } catch (Exception ex) {
                Logger.getLogger(PerfilCiudadano.class.getName()).log(Level.SEVERE, null, ex);
            }

        }


    }

    public String MostrarMensaje() {
        getSessionBeanCobra().getCiudadanoservice().setBoolmensajeguardar(false);
        guardarCiudadano();        
        
        return null;
    }

    public String ModificarCiudadano() {
        if (validarDatosCiudadano()) {

            getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero().setStrnombrecompleto(getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero().getStrnombrecompleto());
            getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero().setLocalidadByStrcodigolocalidad(new Localidad("169"));
            getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero().setStrtelefono1(getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero().getStrtelefono1());
            getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero().setTipoidentificacion(new Tipoidentificacion(3, "Cédula de ciudadanía"));
            getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero().setTipotercero(new Tipotercero(1));
            getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero().setTipoOrigen(new Tipoorigen(4, "Nacional"));
            getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero().setDateusuariocreacion(new Date());
            getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero().setStrdireccionprincipal("faltante");
            getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero().setTiposolicitante(new Tiposolicitante(5));
            getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero().setBoolestado(true);
            getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero().setBoolobraslocalidad(true);
            if(cambioFotoCiudadano) {
                String nuevaRutaWeb = MessageFormat.format(RutasWebArchivos.IMAGENES_CIUDADANO, ""+getSessionBeanCobra().getUsuarioObra().getUsuId())+nomImagen;
                try {
                    ArchivoWebUtil.copiarArchivo(
                            RutasWebArchivos.TMP+nomImagen, 
                            nuevaRutaWeb, 
                            true, 
                            false
                        );
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(PerfilCiudadano.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ArchivoExistenteException ex) {
                    Logger.getLogger(PerfilCiudadano.class.getName()).log(Level.SEVERE, null, ex);
                }
                    getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero().setStrfoto(nuevaRutaWeb);
            }

            getSessionBeanCobra().getCiudadanoservice().setLocalidad(getSessionBeanCobra().getCiudadanoservice().encontrarLocalidadxPerfil(getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero().getLocalidadByStrlocalidadnacimiento().getStrcodigolocalidad()));
            getSessionBeanCobra().getCiudadanoservice().setDepartamento(getSessionBeanCobra().getCiudadanoservice().getLocalidad().getStrdepartamento());
            getSessionBeanCobra().getCiudadanoservice().setMunicipio(getSessionBeanCobra().getCiudadanoservice().getLocalidad().getStrmunicipio());
            getSessionBeanCobra().getCiudadanoservice().guardarTercero(getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero());
            return "miperfil";
        }


        return null;
    }

    public boolean validarDatosCiudadano() {

        if (getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero().getStrnombrecompleto().compareTo("") != 0) {
            if (getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero().getIntcedula().compareTo("") != 0) {
                if (utilidad.isEmail(getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero().getStremail())) {
                    FacesUtils.addInfoMessage(bundle.getString("losdatossehanguardado"));
                    return true;
                } else {
                    FacesUtils.addInfoMessage(bundle.getString("correoinvalido"));
                    return false;
                }
            } else {
                FacesUtils.addInfoMessage(bundle.getString("debediligenciarcedula"));
                return false;
            }
        } else {
            FacesUtils.addInfoMessage(bundle.getString("debediligenciarelnombre"));
            return false;
        }

    }

    /**
     * Valida que las contraseñas no sean repetidas y que no digiten caractéres
     * especiales por contraseña
     *
     * @param void
     * @return False si digitan erroneamente las contraseñas o caractéres
     * especiales y true si todo se encuentra correctamente.
     * @throws
     */
    private boolean validarusuario() {
        Pattern p;
        Matcher m;
        boolean resultado = false;


        try {

            String contrasena = "";
            //  if (subirImagen.getSize() == 1 && pathImagen.compareTo("") != 0 && pathImagen.compareTo("/resources/imgs/bt_user.png") != 0) {
            if (getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero().getStrnombre() != null && getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero().getStrnombre().compareTo("") != 0) {
                JsfUsuario usulogin = getSessionBeanCobra().getUsuarioService().encontrarPorNombreusuario(getSessionBeanCobra().getCiudadanoservice().getCiudadano().getUsuLogin());
                if (utilidad.isEmail(getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero().getStremail())) {
                    if (usulogin == null) {
                        if (getSessionBeanCobra().getCiudadanoservice().getPassword().length() >= 8 && getSessionBeanCobra().getCiudadanoservice().getRepassword().length() >= 8) {
                            if (getSessionBeanCobra().getCiudadanoservice().getPassword().equals(getSessionBeanCobra().getCiudadanoservice().getRepassword())) {

                                p = Pattern.compile("[^A-Za-z0-9]+");
                                m = p.matcher(getSessionBeanCobra().getCiudadanoservice().getPassword());
                                resultado = m.find();
                                boolean caracteresIlegales = false;

                                while (resultado) {
                                    caracteresIlegales = true;
                                    resultado = m.find();
                                }

                                if (caracteresIlegales) {
                                    getSessionBeanCobra().getCiudadanoservice().setMensaje(bundle.getString("caracterespermitidos"));
                                    return false;
                                } else {
                                    contrasena = Encrypter.getInstance().encrypt(getSessionBeanCobra().getCiudadanoservice().getPassword());
                                    getSessionBeanCobra().getCiudadanoservice().getCiudadano().setUsuPasswd(contrasena);
                                    
                                    return true;
                                }

                            } else {
                                getSessionBeanCobra().getCiudadanoservice().setMensaje(bundle.getString("nocoinciden"));
                                return false;

                            }
                        } else {
                            getSessionBeanCobra().getCiudadanoservice().setMensaje(bundle.getString("lacontrasenadebe"));
                            return false;
                        }
                    } else {
                        getSessionBeanCobra().getCiudadanoservice().setMensaje(bundle.getString("usuariorepetido"));
                        return false;
                    }

                } else {
                    getSessionBeanCobra().getCiudadanoservice().setMensaje(bundle.getString("correoinvalido"));
                    return false;
                }
            } else {
                getSessionBeanCobra().getCiudadanoservice().setMensaje(bundle.getString("debedardiligenciarlosdatos"));
                return false;
            }

        } catch (Exception ex) {
        }
        return false;

    }

    /**
     * Enviar correo electrónico al ciudadano para validarse ante el sistema
     *
     * @return No retorna nada
     */
    public void enviarCorreoConfirmar() throws Exception {
        EnvioCorreosCobra envio = new EnvioCorreosCobra(getSessionBeanCobra().getCobraService(), getSessionBeanCobra().getUsuarioObra());
        envio.enviarCorreoCiudadano(getSessionBeanCobra().getCiudadanoservice().getCiudadano());

    }

    /**
     * Inicializar o limpiar variables,listas de la clase PerfilCiudadano
     *
     * @return No retorna
     */
    public void limpiarCiudadano() {
        getSessionBeanCobra().getCiudadanoservice().setCiudadano(new JsfUsuario());
        getSessionBeanCobra().getCiudadanoservice().getCiudadano().setTercero(new Tercero());
        getSessionBeanCobra().getCiudadanoservice().limpiarOpinionCiudadano();

        getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero().setLocalidadByStrlocalidadnacimiento(new Localidad());
        getSessionBeanCobra().getCiudadanoservice().setPassword("");
        getSessionBeanCobra().getCiudadanoservice().setRepassword("");
        // pathImagen = "/resources/imgs/bt_user.png";
        cargadorFoto = new CargadorArchivosWeb();
        getSessionBeanCobra().getCiudadanoservice().setPathImaCiu(RutasWebArchivos.FOTO_NO_DISPONIBLE);
        getSessionBeanCobra().getCiudadanoservice().setBoolmensajeguardar(false);
        // boolperfilusuario = false;
        //getSessionBeanCobra().getCiudadanoservice().setImagenComentario("/resources/imgs/bt_user.png");
        getSessionBeanCobra().setVerregistrarse(false);
        
        
    }

    /**
     * Permite Eliminar de la modal la imagen ya adjunta
     *
     * @return
     */
    public void borrarImagenCorreo() {
        if (getSessionBeanCobra().getCiudadanoservice().getPathImaCiu().compareTo("") != 0) {
            ArchivoWebUtil.eliminarArchivo(getSessionBeanCobra().getCiudadanoservice().getPathImaCiu());
            getSessionBeanCobra().getCiudadanoservice().setPathImaCiu(RutasWebArchivos.FOTO_NO_DISPONIBLE);
        }
        cargadorFoto.borrarDatosSubidos();
    }

    /**
     * Guarda la imagen temporal
     *
     * @return
     */
    public String guardarImagenTemporal() {
        if (cargadorFoto.getNumArchivos() > 0) {
            try {
                cargadorFoto.getArchivos().get(0).cambiarNombre(null, true);
                nomImagen = cargadorFoto.getArchivos().get(0).getNombre();
                cargadorFoto.guardarArchivosTemporales(RutasWebArchivos.TMP, true);
                String rutaWeb = cargadorFoto.getArchivos().get(0).getRutaWeb();
                getSessionBeanCobra().getCiudadanoservice().setPathImaCiu(rutaWeb);

                    try {

                        RedimensionarImagen.scale(
                                ArchivoWebUtil.obtenerRutaAbsoluta(rutaWeb), 
                                640, 5, 
                                ArchivoWebUtil.obtenerRutaAbsoluta(rutaWeb));
                    } catch (IOException ex) {
                        Logger.getLogger(JsfUsuario.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (ArchivoExistenteException ex) {
                    Logger.getLogger(PerfilCiudadano.class.getName()).log(Level.SEVERE, null, ex);
                }
            cambioFotoCiudadano=true;
            }
        return null;
    }

    /**
     * Volver a inicio e invocando el limpiar el formulario y variables
     * creadas{@link limpiarCiudadano()}
     *
     * @return Inicio de la apliación
     *
     */
    public String regresar() {
        limpiarCiudadano();
        
        return null;
    }

    public void guardarcambioimagen() {

        cargadorFoto = new CargadorArchivosWeb();
        if (getSessionBeanCobra().getUsuarioObra() != null) {
            if (getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero().getStrfoto() != null) {
                 getSessionBeanCobra().getCiudadanoservice().setPathImaCiu(getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero().getStrfoto());
            } else {
                getSessionBeanCobra().getCiudadanoservice().setPathImaCiu(RutasWebArchivos.FOTO_NO_DISPONIBLE);
            }
        }
    }

    public String borrarImaCiu() {
        String ArchivoPath = "";
        ServletContext contexto = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        if (getSessionBeanCobra().getCiudadanoservice().getPathImaCiu().compareTo("") != 0) {
            ArchivoPath = contexto.getRealPath(getSessionBeanCobra().getCiudadanoservice().getPathImaCiu());
            File archi = new File(ArchivoPath);
            if (archi.exists()) {
                archi.delete();
            }
            getSessionBeanCobra().getCiudadanoservice().setPathImaCiu(RutasWebArchivos.FOTO_NO_DISPONIBLE);
        }
        return null;
    }

    public String DatosPanelPersonas() {
        

        getControlGerencial().iniciargerencial();
        for (DatoPie dato : getControlGerencial().getListaPersonas()) {


            if (dato.getEtiqueta().compareTo("Damnificadas") == 0) {

                persodamni.setValor(dato.getValor());
            }

            if (dato.getEtiqueta().compareTo("Afectadas") == 0) {

                persoafect.setValor(dato.getValor());
            }
        }
        for (DatoPie datos : getControlGerencial().getListaHogares()) {
            if (datos.getEtiqueta().compareTo("Damnificados") == 0) {
                hogadam.setValor(datos.getValor());
            }
            if (datos.getEtiqueta().compareTo("Afectados") == 0) {
                hogaafect.setValor(datos.getValor());
            }
        }
        getSessionBeanCobra().setActualdash(false);

        return null;
    }

    /**
     * suministrar comentario al usuario
     *
     * @return errorcomentario
     */
    public String AgregarComentario() {
        if (getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().getStropinion().compareTo("") != 0
                && !getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().getStropinion().equals("Escriba Su Comentario")) {

            if (getSessionBeanCobra().getUsuarioObra().getUsuId() != getSessionBeanCobra().getCiudadanoservice().getUsuariomostrar().getUsuId()) {
                getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().setJsfUsuario(getSessionBeanCobra().getCiudadanoservice().getUsuariomostrar());
                getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().setJsfUsuariocomento(getSessionBeanCobra().getUsuarioObra());
            } else {
                getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().setJsfUsuario(getSessionBeanCobra().getUsuarioObra());
                getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().setJsfUsuariocomento(getSessionBeanCobra().getUsuarioObra());
            }

            getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().setTipoopinion(new Tipoopinion(5, "Perfil"));
            getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().setDatefechaopinion(new Date());
            getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().setStrnombreciudadano(getSessionBeanCobra().getUsuarioObra().getTercero().getStrnombre());
            getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().setStrapellidociudadano(getSessionBeanCobra().getUsuarioObra().getTercero().getStrapellido1());
            getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().setStremailciudadano(getSessionBeanCobra().getUsuarioObra().getTercero().getStremail());

            getSessionBeanCobra().getCiudadanoservice().guardarComentarios(getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano());

            Comentarioobra comen = new Comentarioobra();
            comen.setDatefecha(new Date());
            comen.setJsfUsuario(getSessionBeanCobra().getUsuarioObra());
            comen.setStrdesccoment(getSessionBeanCobra().getUsuarioObra().getTercero().getStrnombrecompleto() + " ha comentado");
            getSessionBeanCobra().getCiudadanoservice().guardarComentarioObra(comen);

            getSessionBeanCobra().getCiudadanoservice().limpiarOpinionCiudadano();
            getSessionBeanCobra().LlenarComentariosperfil();

        } else {
            FacesUtils.addErrorMessage(bundle.getString("errorcomentario"));
        }
        return null;
    }

    /**
     * Contar la cantidad de caracteres en el campo opinion
     *
     * @return null
     */
    public String contarcomentario() {
        if (getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().getStropinion().length() <= 400) {
            contarmensaje = 400 - getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().getStropinion().length();
        }
        return null;
    }

    /**
     * Inicializar el formulario de Mi perfil Ciudadano
     *
     * @return MiperfilCiudadano.xhtml
     */
    public String iniperfiUsu() {
        boolotrousuario = false;
        iniciarCombos();
        getSessionBeanCobra().getCiudadanoservice().setUsuariomostrar(getSessionBeanCobra().getUsuarioObra());
        iniciarDatosPerfil(getSessionBeanCobra().getCiudadanoservice().getUsuariomostrar());
        getSessionBeanCobra().LlenarComentariosperfil();
        getSessionBeanCobra().numeroobraseguidas();
        return "miperfil";
    }

    public void iniciarDatosPerfil(JsfUsuario usuariomostrar) {
        getSessionBeanCobra().getCiudadanoservice().setLocalidad(getSessionBeanCobra().getCiudadanoservice().encontrarLocalidadxPerfil(getSessionBeanCobra().getCiudadanoservice().getUsuariomostrar().getTercero().getLocalidadByStrlocalidadnacimiento().getStrcodigolocalidad()));
        getSessionBeanCobra().getCiudadanoservice().setDepartamento(getSessionBeanCobra().getCiudadanoservice().getLocalidad().getStrdepartamento());
        getSessionBeanCobra().getCiudadanoservice().setMunicipio(getSessionBeanCobra().getCiudadanoservice().getLocalidad().getStrmunicipio());

    }

    protected Usuario getUsuario() {
        return (Usuario) FacesUtils.getManagedBean("Perfil$Usuario");
    }

    /**
     * Iniciar la modificación del usuario ciudadano
     *
     * @return MofificarUsuario.xhtml
     */
    public String modificarUsuario() {
        getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero().setCargo(new Cargo(1, ""));
        getSessionBeanCobra().getCiudadanoservice().getCiudadano().setTercero(getSessionBeanCobra().getUsuarioObra().getTercero());
        if(getSessionBeanCobra().getUsuarioObra().getTercero().getLocalidadByStrlocalidadnacimiento().getStrcodigolocalidad().length()>=5) {
            codDepartamentoCiudadano = getSessionBeanCobra().getUsuarioObra().getTercero().getLocalidadByStrlocalidadnacimiento().getStrcodigolocalidad().substring(0, 5);
        }
        llenarMunicipioCiudadanoModi();
        getUsuario().initusu();
        guardarcambioimagen();
        //iniciarCombos();
        //limpiarCiudadano();
        return "modificarperfil";
    }

    public String llenarcontrasena() {
        contrasena = getSessionBeanCobra().getCiudadanoservice().getCiudadano().getUsuPasswd();
        getSessionBeanCobra().getCiudadanoservice().getCiudadano().setUsuLogin(getSessionBeanCobra().getUsuarioObra().getUsuLogin());
        return null;
    }

    /*
     * En la vista de ParticipacionCiudadano.xhtml selecciona el tipo de opinion, imagen y titulo de la imagen para la opinion 
     */
    public void seleccionDenuncie() {
        //getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().getTipoopinion().setIntidtipoopinion(4);
        nombretitulo = "Denunciar";

    }

    public void seleccionFelicite() {
        getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().getTipoopinion().setIntidtipoopinion(3);
        nombretitulo = "Felicitar";
    }

    public void seleccionopine() {
        getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().getTipoopinion().setIntidtipoopinion(2);
        nombretitulo = "Opinar";
    }

    public void seleccionQueja() {
        getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().getTipoopinion().setIntidtipoopinion(1);
        nombretitulo = "Quejarse";
    }

    /*
     * Guarda la opinion realizada por el ciudadano para el proyecto especifico
     */
    public String guardarComentarioOpinion() {
        if (getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().getStropinion() != null
                && getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().getStropinion().compareTo("") != 0
                && getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().getStropinion().compareTo("Escriba Su Comentario") != 0) {

            getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().setDatefechaopinion(new Date());
            getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().setJsfUsuario(getSessionBeanCobra().getUsuarioObra());
            getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().setObra(getAdministrarObraNew().getObra());
            getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().setStrnombreciudadano(getSessionBeanCobra().getUsuarioObra().getTercero().getStrnombrecompleto());
            getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().setStremailciudadano(getSessionBeanCobra().getUsuarioObra().getTercero().getStremail());

            if (getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().getStrfotoopinion().compareTo("Imagen") == 0) {
                getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().setStrfotoopinion(null);
            } else {
                //Guardo imagen en carpeta y actualizo el url
                getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().setStrfotoopinion(guardarArchivoOpinion(0, getAdministrarObraNew().getObra().getIntcodigoobra()));
                cargadorImagen.borrarDatosSubidos();
            }
            if (getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().getStrdoc().compareTo("Documento") == 0) {
                getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().setStrdoc(null);
            } else {
                //Guardo documento en carpeta y actualizo el url  
                getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().setStrdoc(guardarArchivoOpinion(1, getAdministrarObraNew().getObra().getIntcodigoobra()));
                cargadorDocumento.borrarDatosSubidos();
            }
            if (getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().getStrvideo().compareTo("Video") == 0) {
                getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().setStrvideo(null);
            }
            getSessionBeanCobra().getCiudadanoservice().guardarComentarios(getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano());
            iniciarOpiniones();
            FacesUtils.addInfoMessage("La opinión se ha guardado exitosamente");
        } else {
            FacesUtils.addErrorMessage("Debe escribir un comentario.");
        }
        return null;
    }
    
    /**
     * Cancela el registro del comentario
     * @return null
     */
    public String cancelarComentario() {
        iniciarOpiniones();
        return null;
    }

    /**
     * Mostrar el usuario el cual fué elegido
     *
     * @return MiperfilCiudadano.xhtml
     */
    public String cargarUsuario() {

        getSessionBeanCobra().getCiudadanoservice().setUsuariomostrar(new JsfUsuario());
        Opinionciudadano ciudadano = (Opinionciudadano) repeatusuario.getRowData();
        getSessionBeanCobra().getCiudadanoservice().setUsuariomostrar(ciudadano.getJsfUsuariocomento());

        if (getSessionBeanCobra().getUsuarioObra().getUsuId() != getSessionBeanCobra().getCiudadanoservice().getUsuariomostrar().getUsuId()) {
            iniciarDatosPerfil(getSessionBeanCobra().getCiudadanoservice().getUsuariomostrar());
            boolotrousuario = true;
        } else {
            iniciarDatosPerfil(getSessionBeanCobra().getUsuarioObra());
            boolotrousuario = false;
        }
        iniciarCombos();
        getSessionBeanCobra().LlenarComentariosperfil();
        return "miperfil";
    }

    /**
     * Mostrar el usuario el cual fué elegido
     *
     * @return MiperfilCiudadano.xhtml
     */
    public String cargarUsuarioPerfil() {

        getSessionBeanCobra().getCiudadanoservice().setUsuariomostrar(new JsfUsuario());
        Opinionciudadano ciudadano = (Opinionciudadano) repeatusuarioperfil.getRowData();


        if (getSessionBeanCobra().getUsuarioObra().getUsuId() != ciudadano.getJsfUsuariocomento().getUsuId()) {
            getSessionBeanCobra().getCiudadanoservice().setUsuariomostrar(ciudadano.getJsfUsuariocomento());

            iniciarDatosPerfil(getSessionBeanCobra().getCiudadanoservice().getUsuariomostrar());
            boolotrousuario = true;
        } else {
            getSessionBeanCobra().getCiudadanoservice().setUsuariomostrar(getSessionBeanCobra().getUsuarioObra());

            iniciarDatosPerfil(getSessionBeanCobra().getUsuarioObra());
            boolotrousuario = false;
        }
        iniciarCombos();


        getSessionBeanCobra().LlenarComentariosperfil();
        return "miperfil";
    }

    public int getFelicitar() {
        return getSessionBeanCobra().getCiudadanoservice().encontrarOpinionesxObrayTipoOpinion(getAdministrarObraNew().getObra().getIntcodigoobra(), 3).size();
    }

    public int getOpinar() {
        return getSessionBeanCobra().getCiudadanoservice().encontrarOpinionesxObrayTipoOpinion(getAdministrarObraNew().getObra().getIntcodigoobra(), 2).size();
    }

    public int getQuejarse() {
        return getSessionBeanCobra().getCiudadanoservice().encontrarOpinionesxObrayTipoOpinion(getAdministrarObraNew().getObra().getIntcodigoobra(), 1).size();
    }

    public void llenarlistaFelicitacion() {
        opinion= Propiedad.getValor("felicitacion");
        listopinion = getSessionBeanCobra().getCiudadanoservice().encontrarOpinionesxObrayTipoOpinionPublicos(getAdministrarObraNew().getObra().getIntcodigoobra(), 3, getSessionBeanCobra().getUsuarioObra());
    }

    public void llenarlistaOpinion() {
        opinion= Propiedad.getValor("opiniondelciudadano");
        listopinion = getSessionBeanCobra().getCiudadanoservice().encontrarOpinionesxObrayTipoOpinionPublicos(getAdministrarObraNew().getObra().getIntcodigoobra(), 2, getSessionBeanCobra().getUsuarioObra());
    }

    public void llenarlistaQuejas() {
        opinion= Propiedad.getValor("quejas");
        listopinion = getSessionBeanCobra().getCiudadanoservice().encontrarOpinionesxObrayTipoOpinionPublicos(getAdministrarObraNew().getObra().getIntcodigoobra(), 1, getSessionBeanCobra().getUsuarioObra());
    }

    public void llenarlistaTodos() {
        opinion= Propiedad.getValor("todos");
        listopinion = getSessionBeanCobra().getCiudadanoservice().encontrarOpinionesxObrayTipoOpinionPublicos(getAdministrarObraNew().getObra().getIntcodigoobra(), 0, getSessionBeanCobra().getUsuarioObra());
    }

    public int getTodos() {
        return getQuejarse() + getOpinar() + getFelicitar();
    }

    /**
     * suministrar comentario al usuario del home
     *
     * @return errorcomentario
     */
    public String AgregarComentarioGeneral() {
        if (getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().getStropinion().compareTo("") != 0
                && !getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().getStropinion().equals("Escriba Su Comentario")) {

            getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().setJsfUsuario(getSessionBeanCobra().getUsuarioObra());
            getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().setJsfUsuariocomento(getSessionBeanCobra().getUsuarioObra());
            getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().setTipoopinion(new Tipoopinion(4, "General"));
            getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().setDatefechaopinion(new Date());
            getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().setStrnombreciudadano(getSessionBeanCobra().getUsuarioObra().getTercero().getStrnombre());
            getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().setStrapellidociudadano(getSessionBeanCobra().getUsuarioObra().getTercero().getStrapellido1());
            getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().setStremailciudadano(getSessionBeanCobra().getUsuarioObra().getTercero().getStremail());

            getSessionBeanCobra().getCiudadanoservice().guardarComentarios(getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano());

            Comentarioobra comen = new Comentarioobra();
            comen.setDatefecha(new Date());
            comen.setJsfUsuario(getSessionBeanCobra().getUsuarioObra());
            comen.setStrdesccoment(getSessionBeanCobra().getUsuarioObra().getTercero().getStrnombrecompleto() + " ha comentado");
            getSessionBeanCobra().getCiudadanoservice().guardarComentarioObra(comen);

            getSessionBeanCobra().getCiudadanoservice().limpiarOpinionCiudadano();

            getSessionBeanCobra().LlenarComentarios();


        } else {
            FacesUtils.addErrorMessage(bundle.getString("errorcomentario"));
        }
        return null;
    }
    /*
     * Permite subir un documento 
     */

    public String pathDocumento() {
        if (cargadorDocumento.getNumArchivos() > 0) {
            for (ArchivoWeb nombreoriginal : cargadorDocumento.getArchivos()) {
                getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().setStrdoc(nombreoriginal.getNombre());
            }
        } else {
            getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().setStrdoc("Documento");
        }
        return null;
    }
    /*
     * borra el documento subida y recarga el titulo imagen en el inputtext
     */

    public String borrarDoc() {

        cargadorDocumento.borrarDatosSubidos();
        if (cargadorDocumento.getNumArchivos() == 0) {
            getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().setStrdoc("Documento");
        }
        return null;
    }

    /*
     * Borra la imagen subida y recarga el titulo imagen en el inputtext
     */
    public String borrarImagenCiuCargado() {
        cargadorImagen.borrarDatosSubidos();
        if (cargadorImagen.getNumArchivos() == 0) {
            getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().setStrfotoopinion("Imagen");
        }
        return null;
    }

    /*
     * Carga la imagen con el titulo de felicitar en la opcion de opinar ciudadano y llena la lista de las opiniones en todos
     */
    public String iniciarOpiniones() {
        getAdministrarObraNew().setOpcion(7);
        iniciarCombos();
        limpiarCiudadano();
        seleccionFelicite();
        llenarlistaTodos();
        getAdministrarObraNew().setActivocomentarios(false);

        return "participacionciu";
    }

    /*
     * Permite cargar una foto para la obra por parte del ciudadano
     */
    public String aceptarImagen() {
        if (cargadorImagen.getNumArchivos() > 0) {
            for (ArchivoWeb nombreoriginal : cargadorImagen.getArchivos()) {
                getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().setStrfotoopinion(nombreoriginal.getNombre());
            }
        } else {
            getSessionBeanCobra().getCiudadanoservice().getOpinionciudadano().setStrfotoopinion("Imagen");
        }
        return null;
    }
    /*
     * Persistencia y cambio de imagen 
     */
    /*
     * Permite  crear la ruta en el servidor  y guardar los documentos subidos por parte del usuario ciudadano en opinion.
     */

    public String guardarArchivoOpinion(int tipo, int codobra) {
        String rutaArchivo = null;
        try {
            switch (tipo) {
                case 0:
                    if (cargadorImagen.getNumArchivos() > 0) {
                        cargadorImagen.getArchivos().get(0).cambiarNombre(null, true);
                        cargadorImagen.guardarArchivosTemporales(MessageFormat.format(RutasWebArchivos.OPINIONES_CIUDADANO, "" + codobra), false);
                        rutaArchivo = cargadorImagen.getArchivos().get(0).getRutaWeb();
                    }
                    break;
                case 1:
                    if (cargadorDocumento.getNumArchivos() > 0) {
                        cargadorDocumento.getArchivos().get(0).cambiarNombre(null, true);
                        cargadorDocumento.guardarArchivosTemporales(MessageFormat.format(RutasWebArchivos.OPINIONES_CIUDADANO, "" + codobra), false);
                        rutaArchivo = cargadorDocumento.getArchivos().get(0).getRutaWeb();
                    }
                    break;
            }
        } catch (Exception ex) {
            FacesUtils.addErrorMessage(bundle.getString("nosepuedesubir"));
        }
        return rutaArchivo;
    }

    /**
     * 
     * @return Volver al Inicio.xhtml
     */
    public String volverinicio() {
        return "volver";
    }

    public String abrirImagen() {
        Opinionciudadano op = (Opinionciudadano) repeatopinion.getRowData();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/"+getSessionBeanCobra().getBundle().getString("versioncobra")+ op.getStrfotoopinion());
        } catch (IOException ex) {
            Logger.getLogger(PerfilCiudadano.class.getName()).log(Level.SEVERE, null, ex);
        }



        return "Download";
    }

    public String abrirDoc() {
        Opinionciudadano op = (Opinionciudadano) repeatopinion.getRowData();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/" +getSessionBeanCobra().getBundle().getString("versioncobra")+ op.getStrdoc());
        } catch (IOException ex) {
            Logger.getLogger(PerfilCiudadano.class.getName()).log(Level.SEVERE, null, ex);
        }



        return "Download";
    }
    
    public String reiniciarMensaje()
    {
        getSessionBeanCobra().getCiudadanoservice().setMensaje("Debe diligenciar los campos obligatorios.");
        return null;
    }        

    private int asi_va = 0;

    public int getAsi_va() {
        return asi_va;
    }

    public void setAsi_va(int asi_va) {
        this.asi_va = asi_va;
    }
    private int deberia_ir = 0;

    public int getDeberia_ir() {
        return deberia_ir;
    }

    public void setDeberia_ir(int deberia_ir) {
        this.deberia_ir = deberia_ir;
    }
    private String valorobra = "";

    public String getValorobra() {
        return valorobra;
    }

    public void setValorobra(String valorobra) {
        this.valorobra = valorobra;
    }
    NumberFormat money = NumberFormat.getCurrencyInstance(new Locale("es", "CO", "Traditional_WIN"));
    private List<Contratista> contratistas_slider = new ArrayList<Contratista>();
    private List<String>list_contratistas_slider = new ArrayList<String>();
    private int numeroContratistas = 0;
    private String unoContratista = "";

    public String getUnoContratista() {
        return unoContratista;
    }

    public void setUnoContratista(String unoContratista) {
        this.unoContratista = unoContratista;
    }

    public int getNumeroContratistas() {
        return numeroContratistas;
    }

    public void setNumeroContratistas(int numeroContratistas) {
        this.numeroContratistas = numeroContratistas;
    }

    public List<String> getList_contratistas_slider() {
        return list_contratistas_slider;
    }

    public void setList_contratistas_slider(List<String> list_contratistas_slider) {
        this.list_contratistas_slider = list_contratistas_slider;
    }

    public String cargarObraSliderImagenes() {    
        list_contratistas_slider.clear();
        numeroContratistas = 0;
        unoContratista = "";
        Imagenevolucionobra im = (Imagenevolucionobra) repeatslider.getRowData();
        vistaobraciudadano = getSessionBeanCobra().getCobraService().obtenerVistaObraMapaxid(im.getObra().getIntcodigoobra());
        contratistas_slider = (getSessionBeanCobra().getCobraService().obtenerContratistaporobra(im.getObra().getIntcodigoobra()));
        BigDecimal porcentaje = BigDecimal.valueOf(0);
        if (vistaobraciudadano.getNumvalejecobra() != null && vistaobraciudadano.getTipoestadobra().getIntestadoobra() > 0) {
            porcentaje = vistaobraciudadano.getNumvalejecobra();
            porcentaje = porcentaje.multiply(BigDecimal.valueOf(100));
            porcentaje = porcentaje.divide(vistaobraciudadano.getNumvaltotobra(), 2, RoundingMode.HALF_UP);

        }
        Double asi_va_int = Double.parseDouble(porcentaje.setScale(2, RoundingMode.HALF_UP).toString());
        asi_va = (int) Math.floor(asi_va_int);
        deberia_ir = 0;
        valorobra = money.format(vistaobraciudadano.getNumvaltotobra());

        if (vistaobraciudadano.getDeberiaestar() != null) {
            BigDecimal db_ir = vistaobraciudadano.getDeberiaestar().setScale(0, RoundingMode.DOWN);
            deberia_ir = db_ir.intValueExact();
        }
        if (contratistas_slider != null) {
            if (!contratistas_slider.isEmpty()) {
                if (contratistas_slider.size() == 1) {
                    unoContratista = contratistas_slider.get(0).getStrnombre();                    
                    numeroContratistas = 1;
                } else {
                    for (Contratista cont : contratistas_slider) {
                        if (cont.getStrnombre() != null) {
                            numeroContratistas ++;
                            list_contratistas_slider.add(cont.getStrnombre());
                        }
                    }
                }
            }
        }
        getSessionBeanCobra().ciudadanosinregistro();
        return null;
    }
}
