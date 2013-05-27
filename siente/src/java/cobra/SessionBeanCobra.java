/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra;

import Seguridad.Encrypter;
import atencionHumanitaria.service.AtencionHumanitariaServiceAble;
import chsolicitud.dao.service.SolicitudServiceAble;
import ciudadano.service.CiudadanoServiceAble;
import co.com.interkont.cobra.to.Actividadobra;
import co.com.interkont.cobra.to.Alimentacion;
import co.com.interkont.cobra.to.Documentoobra;
import co.com.interkont.cobra.to.Imagenevolucionobra;
import co.com.interkont.cobra.to.JsfUsuario;
import co.com.interkont.cobra.to.Modulo;
import co.com.interkont.cobra.to.Obra;
import co.com.interkont.cobra.to.Opinionciudadano;
import co.com.interkont.cobra.to.Periodo;
import co.com.interkont.cobra.to.Periodoevento;
import co.com.interkont.cobra.to.Relacionactividadobraperiodo;
import co.com.interkont.cobra.to.RestaurarPassword;
import co.com.interkont.cobra.to.Tipoestadobra;
import co.com.interkont.cobra.vista.VistaObraMapa;
import cobra.Supervisor.FacesUtils;
import cobra.reportes.ReporteUsuariosXls;
import cobra.service.CobraServiceAble;
import cobra.service.IndicadorServiceAble;
import cobra.service.ModificarProyectoServiceAble;
import cobra.service.UsuarioServiceAble;
import cobra.service.UsuarioServiceImpl;

import financiera.service.FinancieraServiceAble;
import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import supervision.service.SupervisionExternaServiceAble;

/**
 * <p>Session scope data bean for your application. Create properties here to
 * represent cached data that should be made available across multiple HTTP
 * requests for an individual user.</p>
 *
 * <p>An instance of this class will be created for you automatically, the first
 * time your application evaluates a value binding expression or method binding
 * expression that references a managed bean using this class.</p>
 *
 * @version SessionBeanCobra.java
 * @version Created on 12/11/2008, 04:01:57 PM
 * @author carlos
 */
public class SessionBeanCobra implements Serializable {
    // <editor-fold defaultstate="collapsed" desc="Managed Component Definition">

    private UsuarioServiceAble usuarioService;
    private CobraServiceAble cobraService;
    private SelectItem[] PeriodoEvento;
    private FinancieraServiceAble financieraService;
    private TipoLogueo tipologueo;
    private AtencionHumanitariaServiceAble atencionhumanitariaService;
    private SolicitudServiceAble solicitudService;
    private SupervisionExternaServiceAble supervisionExternaService;
    private IndicadorServiceAble indicadorService;
    //private EnvioCorreosCobra enviocorreocobra = new EnvioCorreosCobra(cobraService,getUsuarioService().getUsuarioObra());
    private CiudadanoServiceAble ciudadanoservice;
    private ModificarProyectoServiceAble modificarProyectoService;
    private boolean actualcomentario = true;
    private boolean actualdash = true;
    private boolean vermensajeinterventor = true;
//    private JsfUsuario usuarioObra;
    private boolean verregistrarse = false;
    private String usuarioCorreo = "";
    private int msgerrorkeyrespas = 0;
    private String keyrepass = "";
    private boolean ciudadano = true;
    private int obraseguida = 0;

    public int getObraseguida() {
        return obraseguida;
    }

    public void setObraseguida(int obraseguida) {
        this.obraseguida = obraseguida;
    }

    public boolean isCiudadano() {
        return ciudadano;
    }

    public void setCiudadano(boolean ciudadano) {
        this.ciudadano = ciudadano;
    }
    //private Modulo modulo;

    public String getKeyrepass() {
        return keyrepass;
    }

    public void setKeyrepass(String keyrepass) {
        this.keyrepass = keyrepass;
    }

    public int getMsgerrorkeyrespas() {
        return msgerrorkeyrespas;
    }

    public void setMsgerrorkeyrespas(int msgerrorkeyrespas) {
        this.msgerrorkeyrespas = msgerrorkeyrespas;
    }

    public String getUsuarioCorreo() {
        return usuarioCorreo;
    }

    public void setUsuarioCorreo(String usuarioCorreo) {
        this.usuarioCorreo = usuarioCorreo;
    }

    public boolean isVerregistrarse() {

        return verregistrarse;
    }

    public void setVerregistrarse(boolean verregistrarse) {
        this.verregistrarse = verregistrarse;
    }

    public boolean isActualdash() {
        return actualdash;
    }

    public void setActualdash(boolean actualdash) {
        this.actualdash = actualdash;
    }

    public boolean isActualcomentario() {
        return actualcomentario;
    }

    public void setActualcomentario(boolean actualcomentario) {
        this.actualcomentario = actualcomentario;
    }
    Utilidades util = new Utilidades();

    public ModificarProyectoServiceAble getModificarProyectoService() {
        return modificarProyectoService;
    }

    public void setModificarProyectoService(ModificarProyectoServiceAble modificarProyectoService) {
        this.modificarProyectoService = modificarProyectoService;
    }

    public CiudadanoServiceAble getCiudadanoservice() {
        return ciudadanoservice;
    }

    public void setCiudadanoservice(CiudadanoServiceAble ciudadanoservice) {
        this.ciudadanoservice = ciudadanoservice;
    }

    public Utilidades getUtil() {
        return util;
    }

    public void setUtil(Utilidades util) {
        this.util = util;
    }

//    public EnvioCorreosCobra getEnviocorreocobra() {
//        return enviocorreocobra;
//    }
//
//    public void setEnviocorreocobra(EnvioCorreosCobra enviocorreocobra) {
//        this.enviocorreocobra = enviocorreocobra;
//    }
    public IndicadorServiceAble getIndicadorService() {
        return indicadorService;
    }

    public void setIndicadorService(IndicadorServiceAble indicadorService) {
        this.indicadorService = indicadorService;
    }
    private Integer idModulo;
    private ResourceBundle bundle = ResourceBundle.getBundle(ResourceBundle.getBundle("cobra.properties.General").getString("varproperties"));
    private int codinicial = 0;
    private int codfinal = 0;

    public int getCodfinal() {
        return codfinal;
    }

    public void setCodfinal(int codfinal) {
        this.codfinal = codfinal;
    }

    public int getCodinicial() {
        return codinicial;
    }

    public void setCodinicial(int codinicial) {
        this.codinicial = codinicial;
    }

    public Integer getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(Integer idModulo) {
        this.idModulo = idModulo;
    }

    public Map<Integer, String> getActions() {
        return usuarioService.getActions();
    }

    public SupervisionExternaServiceAble getSupervisionExternaService() {
        return supervisionExternaService;
    }

    public void setSupervisionExternaService(SupervisionExternaServiceAble supervisionExternaService) {
        this.supervisionExternaService = supervisionExternaService;
    }

    public SolicitudServiceAble getSolicitudService() {
        return solicitudService;
    }

    public void setSolicitudService(SolicitudServiceAble solicitudService) {
        this.solicitudService = solicitudService;
    }

    public AtencionHumanitariaServiceAble getAtencionhumanitariaService() {
        return atencionhumanitariaService;
    }

    public void setAtencionhumanitariaService(AtencionHumanitariaServiceAble atencionhumanitariaService) {
        this.atencionhumanitariaService = atencionhumanitariaService;
    }

    public FinancieraServiceAble getFinancieraService() {
        return financieraService;
    }

    public void setFinancieraService(FinancieraServiceAble financieraService) {
        this.financieraService = financieraService;
    }

    public TipoLogueo getTipologueo() {
        return tipologueo;
    }

    public void setTipologueo(TipoLogueo tipologueo) {
        this.tipologueo = tipologueo;
    }

    public SelectItem[] getPeriodoEvento() {
        return PeriodoEvento;
    }

    public void setPeriodoEvento(SelectItem[] PeriodoEvento) {
        this.PeriodoEvento = PeriodoEvento;
    }

    public CobraServiceAble getCobraService() {
        return cobraService;
    }

    public void setCobraService(CobraServiceAble cobraService) {
        this.cobraService = cobraService;
    }

    public ResourceBundle getBundle() {

        return bundle;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public UsuarioServiceAble getUsuarioService() {
        return usuarioService;
    }

    public void setUsuarioService(UsuarioServiceAble usuarioService) {
        this.usuarioService = usuarioService;
    }
    private String mensajelogueo = "";

    public String getMensajelogueo() {
        return mensajelogueo;
    }

    public void setMensajelogueo(String mensajelogueo) {
        this.mensajelogueo = mensajelogueo;
    }
    private String UrlAbri = "";

    public String getUrlAbri() {
        return UrlAbri;
    }

    public void setUrlAbri(String UrlAbri) {
        this.UrlAbri = UrlAbri;
    }
    private String valor = "";

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
    private int bandobrasfiltradas = 0;

    public int getBandobrasfiltradas() {
        return bandobrasfiltradas;
    }

    public void setBandobrasfiltradas(int bandobrasfiltradas) {
        this.bandobrasfiltradas = bandobrasfiltradas;
    }
    /**
     * <p>Automatically managed component initialization.
     * <strong>WARNING:</strong> This method is automatically generated, so any
     * user-specified code inserted here is subject to being replaced.</p>
     */
    private java.util.Date utilDate = new java.util.Date(); //fecha actual

    public Date getUtilDate() {
        return utilDate;
    }

    public void setUtilDate(Date utilDate) {
        this.utilDate = utilDate;
    }

    public JsfUsuario getUsuarioObra() {
        return usuarioService.getUsuarioObra();

    }

    public List<Modulo> getModulos() {
        return usuarioService.getModulos();
    }

    public int getCountModulos() {
        return getModulos().size();
    }
    /**
     * Get the value of usuarioObra
     *
     * @return the value of usuarioObra
     */
    private String FechaServer;

    /**
     * Get the value of FechaServer
     *
     * @return the value of FechaServer
     */
    public String getFechaServer() {
        return FechaServer;
    }

    /**
     * Set the value of FechaServer
     *
     * @param FechaServer new value of FechaServer
     */
    public void setFechaServer(String FechaServer) {
        this.FechaServer = FechaServer;
    }

    private void _init() throws Exception {
    }
    // </editor-fold>

    /**
     * <p>Construct a new session data bean instance.</p>
     */
    public SessionBeanCobra() {
        verregistrarse = Boolean.parseBoolean(bundle.getString("varmodalsupervisor"));
    }

    public void llenadodatos() {
        //setNombreUsuario(getUsuarioObra().getStrnombre() + " " + getUsuarioObra().getStrapellidos());

        //System.out.println(utilDate);
        long lnMilisegundos = utilDate.getTime();
        java.sql.Date sqlDate = new java.sql.Date(lnMilisegundos);
        String dat = "" + sqlDate;
        setFechaServer(dat);
    }
    private int codigoobraalimentar = 0;

    public int getCodigoobraalimentar() {
        return codigoobraalimentar;
    }

    public void setCodigoobraalimentar(int codigoobraalimentar) {
        this.codigoobraalimentar = codigoobraalimentar;
    }
    private String fechainicio;
    private String fechafin;

    public String getFechafin() {
        return fechafin;
    }

    public void setFechafin(String fechafin) {
        this.fechafin = fechafin;
    }

    public String getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(String fechainicio) {
        this.fechainicio = fechainicio;
    }

    public String entrarModulo() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest myRequest = (HttpServletRequest) context.getExternalContext().getRequest();
        idModulo = Integer.valueOf(myRequest.getParameter("idModulo"));
        return getActions().get(idModulo);
    }

    public String cerrarSession_action() {
        // TODO: Process the action. Return value is a navigation
        // case name where null will return to the same page.
        getCobraService().getLog().info("cerrarSesion(" + getUsuarioService().getUsuarioObra().getUsuLogin() + ", " + new Date() + ");");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
        //getSessionMap().clear();
        ExternalContext ctx =
                FacesContext.getCurrentInstance().getExternalContext();
        ((HttpSession) ctx.getSession(false)).invalidate();

        return "cerrarSession";
//
//        try {
//            // TODO: Process the action. Return value is a navigation
//            // case name where null will return to the same page.
//            //getLifecycle().render(FacesContext.getCurrentInstance());
//            getExternalContext().getSessionMap().clear();
//            FacesContext.getCurrentInstance().getExternalContext().redirect(getBundle().getString("cerrarsession"));
//        } catch (IOException ex) {
//
//
//            Logger.getLogger(SessionBeanCobra.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        //cobradao.cerrarSession();
//        getExternalContext().getSessionMap().clear();
//        return "cerrarSession";
    }

    public void llenarPeriodoEvento() {
        List<Periodoevento> lista = getCobraService().encontrarPeriodoEventoPorEstado();
        PeriodoEvento = new SelectItem[lista.size()];
        int i = 0;
        for (Periodoevento per : lista) {
            SelectItem opt = new SelectItem(per.getIntidperiodoevento(), per.getEvento().getStrdescripcion() + " " + per.getStrdescripcion());
            PeriodoEvento[i++] = opt;
        }
    }

    public String actualizarPasswords() {

        usuarioService.actualizarContrasenas(codinicial, codfinal);
        return null;
    }

    public void cargarpermisosmodulo(int modulo) {
        Modulorecurso modulorecurso = new Modulorecurso();
        getUsuarioService().getUsuarioObra().setRenderrecurso(modulorecurso.validarrecurso(modulo));

    }

    public void errorCapturado() {
        // TODO: Process the action. Return value is a navigation
        // case name where null will return to the same page.
        getCobraService().getLog().info("cerrarSesionPorError(" + getUsuarioService().getUsuarioObra().getUsuLogin() + ", " + new Date() + ");");
        //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();

    }

    public String mirarImagenes() {

        String path = "";
        ServletContext theApplicationsServletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

        List<Imagenevolucionobra> listaimagen = new ArrayList<Imagenevolucionobra>();

        listaimagen = getCobraService().encontrartodoevolucionobra();

        for (Imagenevolucionobra img : listaimagen) {

            if (img.getStrubicacion() != null) {

                path = theApplicationsServletContext.getRealPath(img.getStrubicacion());
                String pathconespacios = path.replaceAll("%20", " ");

                try {
                    File imagen = new File(pathconespacios);
                    if (imagen.getTotalSpace() == 0) {
                        System.out.println("No Esta = " + img.getIntidimagen());
                        System.out.println("imagen = " + img.getStrubicacion());
                    } else {


                        System.out.println("Si esta = " + img.getIntidimagen());

                    }
                } catch (Exception ex) {
                    //   Logger.getLogger(IngresarNuevaObra.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                System.out.println("imagen sin ruta = " + img.getIntidimagen());


            }
        }

        return null;
    }

    public String mirarImg() {
        List<Imagenevolucionobra> listaimagen = new ArrayList<Imagenevolucionobra>();

        listaimagen = getCobraService().encontrartodoevolucionobra();
        int i = 0;
        for (Imagenevolucionobra img : listaimagen) {

            if (!img.getStrnombre().replaceAll("[^a-zA-Z0-9á-úÁ-Ú\\-/._(%20)]+", "%20").equals(img.getStrnombre())) {
//                System.out.println("img = " + img.getStrnombrearchivo());
                System.out.println(+img.getIntidimagen() + ";" + img.getStrnombrearchivo() + ";" + img.getStrnombrearchivo().replaceAll("[^a-zA-Z0-9á-úÁ-Ú\\-/._(%20)]+", "%20"));
                System.out.println(+img.getIntidimagen() + ";" + img.getStrubicacion() + ";" + img.getStrubicacion().replaceAll("[^a-zA-Z0-9á-úÁ-Ú\\-/._(%20)]+", "%20"));
                img.setStrnombrearchivo(img.getStrnombrearchivo().replaceAll("[^a-zA-Z0-9á-úÁ-Ú\\-/._(%20)]+", "%20"));
                img.setStrubicacion(img.getStrubicacion().replaceAll("[^a-zA-Z0-9á-úÁ-Ú\\-/._(%20)]+", "%20"));
                // System.out.println("strnombre = " + img.getStrubicacion());
                i++;
                getCobraService().guardarImagen(img);

            }
        }
        System.out.println("i = " + i);
        return null;
    }

    public String mirardoc() {
        List<Documentoobra> listadoc = new ArrayList<Documentoobra>();
        listadoc = getCobraService().encontrartododocumentoobra();
        int i = 0;
        for (Documentoobra doc : listadoc) {
            if (!doc.getStrubicacion().replaceAll("[^a-zA-Z0-9á-úÁ-Ú\\-/._(%20),=$~+&\\]\\[ ]+", "_").equals(doc.getStrubicacion())) //if(doc.getStrubicacion().contains("%"))
            //if(doc.getStrubicacion().contains("ó"))
            {
//                if(!doc.getStrubicacion().contains(" "))
//                {

                System.out.println(+doc.getOididdoc() + ";" + doc.getStrubicacion() + ";" + doc.getStrubicacion().replaceAll("[^a-zA-Z0-9á-úÁ-Ú\\-/._(%20)]+", "_"));
                //System.out.println("doc = " + doc.getStrubicacion().replaceAll("[^a-zA-Z0-9á-úÁ-Ú\\-/._(%20)]+", " "));
                //reemplazar %s
                //System.out.println(+doc.getOididdoc()+";" + doc.getStrubicacion()+";"+doc.getStrubicacion().replaceAll("ó", ""));
                i++;
                //}
                //System.out.println("i = " + i);
            }
        }

        System.out.println("i = " + i);
        return null;
    }

    public String mirarDocnosrv() {
        String path = "";
        ServletContext theApplicationsServletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

        List<Documentoobra> listadoc = new ArrayList<Documentoobra>();

        listadoc = getCobraService().encontrartododocumentoobra();

        for (Documentoobra doc : listadoc) {

            if (doc.getStrubicacion() != null) {

                //if (!img.getStrubicacion().replaceAll("[^a-zA-Z0-9á-úÁ-Ú\\-/._(%20)]+", " ").equals(img.getStrubicacion())) {

                path = theApplicationsServletContext.getRealPath(doc.getStrubicacion());
                String pathconespacios = path.replaceAll("%20", " ");

                try {
                    File imagendoc = new File(pathconespacios);
                    if (imagendoc.getTotalSpace() == 0) {
                        System.out.println("No Esta = " + doc.getOididdoc());
                        System.out.println("Documento= " + doc.getStrubicacion());


                    } else {
                        // System.out.println("Si esta = " + img.getIntidimagen());
                    }
                } catch (Exception ex) {
                    //   Logger.getLogger(IngresarNuevaObra.class.getName()).log(Level.SEVERE, null, ex);
                }

                //}
            } else {
                System.out.println("Documento sin ruta = " + doc.getOididdoc());


            }
        }

        return null;
    }

    /*public String borrardoc()
     {
     String path = "";

     ServletContext theApplicationsServletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

     List<Documentoobra> listadoc = new ArrayList<Documentoobra>();


     listadoc = getCobraService().encontrartododocumentoobra();

     for (Documentoobra doc : listadoc) {

     if (doc.getStrubicacion() != null) {

     //if (!img.getStrubicacion().replaceAll("[^a-zA-Z0-9á-úÁ-Ú\\-/._(%20)]+", " ").equals(img.getStrubicacion())) {

     path = theApplicationsServletContext.getRealPath(doc.getStrubicacion());
     String pathconespacios = path.replaceAll("%20", " ");
     try {
     List<Relacionhistoobradocu> listhitdoc = new ArrayList<Relacionhistoobradocu>();

     listhitdoc = getCobraService().encontrarRelahistoobradoc((int)doc.getOididdoc());
     File imagendoc1 = new File(pathconespacios);
     if (imagendoc1.getTotalSpace() == 0) {
     System.out.println("No Esta = " + doc.getOididdoc());
     System.out.println("Documento= " + doc.getStrubicacion());
     System.out.println("idoiddoc = " + doc.getOididdoc());
     getCobraService().encontrarRelahistoobradoc((int)doc.getOididdoc());
     if(!listhitdoc.isEmpty()){
     System.out.println("HOLA = " + listhitdoc.get(0).getIntidrelahistodocu());
     getCobraService().borrarrelacionhisobrdoc((int)doc.getOididdoc());

     }
     getCobraService().borrarDocumento(doc);


     } else {


     // System.out.println("Si esta = " + img.getIntidimagen());

     }
     } catch (Exception ex) {
     //   Logger.getLogger(IngresarNuevaObra.class.getName()).log(Level.SEVERE, null, ex);
     }


     } else {
     System.out.println("Doc sin ruta = " + doc.getOididdoc());


     }
     }
     return  null;
     }*/
    public void LlenarComentarios() {

        getCiudadanoservice().setListaComentarios(new ArrayList<Opinionciudadano>());
        getCiudadanoservice().setListaComentarios(getCiudadanoservice().encontrarUltimosComentarios(4));
        actualcomentario = false;

    }

    public void LlenarComentariosperfil() {
        getCiudadanoservice().setIntcomentario(getCiudadanoservice().obtenerComentariosPerfil(getCiudadanoservice().getUsuariomostrar().getUsuId(), 5));
        getCiudadanoservice().setIntfotos(getCiudadanoservice().obtenerCantFotosPerfil((getCiudadanoservice().getUsuariomostrar().getUsuId())));
        getCiudadanoservice().setIntvideo(getCiudadanoservice().obtenerCantVideosPerfil(getCiudadanoservice().getUsuariomostrar().getUsuId()));
        getCiudadanoservice().setListaComentariosperfil(new ArrayList<Opinionciudadano>());
        getCiudadanoservice().setListaComentariosperfil(getCiudadanoservice().encontrarUltimosComentariosPerfil(getCiudadanoservice().getUsuariomostrar().getUsuId(), 5));
    }

    public String arreglarsumasAlimentaciones() {
        List<Obra> listaobras = getCobraService().encontrarObrasporEstado(1, 0, 5000);
        System.out.println("listaobras = " + listaobras.size());
        int i = 0;
        for (Obra ob : listaobras) {
            List<Alimentacion> listaalimenta = getCobraService().encontrarAlimentacionesObra(ob);
            BigDecimal valorejec = BigDecimal.ZERO;
            System.out.println("Obra = " + ob.getIntcodigoobra());
            for (Alimentacion alim : listaalimenta) {
                System.out.println("Alim = " + alim.getIntidalimenta());
                valorejec = valorejec.add(alim.getNumtotalejec());
                if (valorejec.compareTo(alim.getNumtotalejecacu()) != 0) {
                    System.out.println("Valor = " + valorejec + ";" + alim.getNumtotalejecacu());
                    alim.setNumtotalejecacu(valorejec);
                    getCobraService().guardarArregloAlimentacion(alim, alim.getJsfUsuarioByIntusuAlimenta());
                }

            }
            i++;


        }

        System.out.println("final ");
        return null;
    }

    public String crearRelacionPeriodosEducacion() {
        List<Obra> listaobras = getCobraService().encontrarProyectosEducacion();
        System.out.println("listaobras = " + listaobras.size());
        for (Obra obranueva : listaobras) {

            if (obranueva.getIntplazoobra() > 0) {
                //int indiceperiodo = obranueva.getPeriodomedida().getIntidperiomedida();
                //int division = 0;
                obranueva.setPeriodos(new LinkedHashSet());
//
//            switch (indiceperiodo) {
//                case 1:
//                    division = obranueva.getIntplazoobra() / 7;
//
//                    if (obranueva.getIntplazoobra() % 7 != 0) {
//                        division = division + 1;
//                    }
//                    obranueva.getPeriodomedida().setIntnrodiasperiomedida(7);
//                    break;
//                case 2:
//
//                    division = obranueva.getIntplazoobra() / 14;
//
//                    if (obranueva.getIntplazoobra() % 14 != 0) {
//                        division = division + 1;
//                    }
//                    obranueva.getPeriodomedida().setIntnrodiasperiomedida(14);
//                    break;
//                case 3:
//                    division = obranueva.getIntplazoobra() / 30;
//
//                    if (obranueva.getIntplazoobra() % 30 != 0) {
//                        division = division + 1;
//                    }
//                    obranueva.getPeriodomedida().setIntnrodiasperiomedida(30);
//                    break;
//            }

                List<Periodo> lista = getCobraService().encontrarPeriodosObra(obranueva);
                List<Actividadobra> listaact = getCobraService().obtenerActividadesObra(obranueva.getIntcodigoobra());

                int j = 0;
                while (j < lista.size()) {
                    //for (Periodo pe : lista) {
                    Periodo pe = lista.get(j);

                    int i = 0;

                    pe.setRelacionactividadobraperiodos(new LinkedHashSet());
                    boolean band = false;
                    if (j == lista.size() - 1) {
                        band = true;
                    }
                    while (i < listaact.size()) {
                        Relacionactividadobraperiodo relacion = new Relacionactividadobraperiodo();


                        if (!band) {
                            relacion.setFloatcantplanif(0);
                            relacion.setNumvalplanif(BigDecimal.valueOf(0));
                        } else {
                            relacion.setFloatcantplanif(listaact.get(i).getFloatcantplanifao());
                            relacion.setNumvalplanif(listaact.get(i).getNumvalorplanifao().multiply(BigDecimal.valueOf(listaact.get(i).getFloatcantplanifao())));
                        }
                        //

                        relacion.setActividadobra(listaact.get(i));
                        relacion.setPeriodo(pe);
                        pe.getRelacionactividadobraperiodos().add(relacion);

                        i++;
                    }
                    j++;
                    obranueva.getPeriodos().add(pe);
                }

                getCobraService().guardarObra(obranueva, getUsuarioService().getUsuarioObra(), -1);
            }
        }
        System.out.println("final ");
        return null;
    }

    public String crearPeriodosEducacion() {
        List<Obra> listaobras = getCobraService().encontrarProyectosEducacion();
        System.out.println("listaobras = " + listaobras.size());
        int j = 0;
        for (Obra obranueva : listaobras) {
            System.out.println("Obra = " + obranueva.getIntcodigoobra());
            int division = 0;
            if (obranueva.getIntplazoobra() > 0) {

                obranueva.setPeriodos(new LinkedHashSet());

                int indiceperiodo = obranueva.getPeriodomedida().getIntidperiomedida();
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
                int i = 0;
                System.out.println("division = " + division);
                System.out.println("per = " + obranueva.getPeriodomedida().getIntnrodiasperiomedida());
                Calendar fecha = Calendar.getInstance();
                fecha.setTime(obranueva.getDatefeciniobra());
                while (i < division) {


                    Periodo pe = new Periodo();
                    pe.setDatefeciniperiodo(fecha.getTime());
                    fecha.add(Calendar.DATE, +(obranueva.getPeriodomedida().getIntnrodiasperiomedida() - 1));
                    if (fecha.getTime().compareTo(obranueva.getDatefecfinobra()) > 0) {
                        pe.setDatefecfinperiodo(obranueva.getDatefecfinobra());
                    } else {
                        pe.setDatefecfinperiodo(fecha.getTime());
                    }


                    pe.setNumvaltotplanif(BigDecimal.ZERO);
                    pe.setObra(obranueva);
                    fecha.add(Calendar.DATE, +1);
                    obranueva.getPeriodos().add(pe);
                    i++;
                }
                obranueva.setTipoestadobra(new Tipoestadobra(1));

                getCobraService().guardarObra(obranueva, getUsuarioService().getUsuarioObra(), -1);
                j++;
            }




        }
        System.out.println("final " + j);
        return null;
    }

    public boolean isVermensajeinterventor() {
        return vermensajeinterventor;
    }

    public void setVermensajeinterventor(boolean vermensajeinterventor) {
        this.vermensajeinterventor = vermensajeinterventor;
    }

    public String visualizoModal() {
        vermensajeinterventor = false;
        return null;
    }

    public String visualizarModalImagenCiudadano() {
        ciudadanoservice.setVermodalimagen(true);
        return null;
    }

    public String habilitarRegistrate() {
        verregistrarse = true;
        return null;
    }

    public String deshabilitarRegistrate() {
        verregistrarse = false;
        return null;
    }

    /**
     * Genera un excel con la información de los usuarios asosciados a una obra,
     * incluída la información de autenticación en el sistema
     *
     * @return
     */
    public String generarUsuariosObra() {
        ReporteUsuariosXls excel = new ReporteUsuariosXls();
        List<VistaObraMapa> obras = cobraService.encontrarObrasConSolicitud();
        int count = ReporteUsuariosXls.FILA_UNO;
        int hoja = ReporteUsuariosXls.INICIO_HOJAS_EXCEL;
        excel.crarFila(count);
        for (VistaObraMapa obra : obras) {
            excel.ingresarDatos(count, ReporteUsuariosXls.COL_CODIGO_SIENTE, obra.getIntcodigoobra());
            excel.ingresarDatos(count, ReporteUsuariosXls.COL_NOMBRE_SOLICITUD, obra.getSolicitud_obra().getStrnombresolicitud());
            excel.ingresarDatos(count, ReporteUsuariosXls.COL_CONSECUTIVO, obra.getSolicitud_obra().getStrconsecutivo());
            if (obra.getSolicitud_obra().getEncargofiduciario() != null) {
                excel.ingresarDatos(count, ReporteUsuariosXls.COL_ENCARGO_FIDUCIARIO, obra.getSolicitud_obra().getEncargofiduciario().getIntnumencargofiduciario());
            } else {
                excel.ingresarDatos(count, ReporteUsuariosXls.COL_ENCARGO_FIDUCIARIO, "Sin encargo fiduciario");
            }
            excel.ingresarDatos(count, ReporteUsuariosXls.COL_MUNICIPIO, obra.getSolicitud_obra().getLocalidad().getStrmunicipio());
            excel.ingresarDatos(count, ReporteUsuariosXls.COL_DEPARTAMENTO, obra.getSolicitud_obra().getLocalidad().getStrdepartamento());

            List<JsfUsuario> usuarios = cobraService.encontrarJsfUsuariosObra(obra.getIntcodigoobra());
            usuarios.addAll(cobraService.encontrarJsfUsuariosEntidad(obra.getTercero().getIntcodigo()));
            //System.out.println("local = " + obra.getSolicitud_obra().getLocalidad().getStrcodigolocalidad());
            usuarios.addAll(cobraService.encontrarJsfUsuariosLocalidad(obra.getSolicitud_obra().getLocalidad().getStrcodigolocalidad()));

//            List<JsfUsuario> usuariosTMP;
//            for (Object object : obra.getLocalidads()) {
//                Localidad localidad = (Localidad) object;
//                usuariosTMP = cobraService.encontrarJsfUsuariosLocalidad(localidad.getStrcodigolocalidad());
//                if (usuariosTMP != null && !usuariosTMP.isEmpty()) {
//                    usuarios.addAll(usuariosTMP);
//                }
//            }
            //System.out.println("usuarios = " + usuarios.size());
            if (usuarios.size() > 0) {
                for (JsfUsuario jsfUsuario : usuarios) {
                    String password;
                    try {
                        password = Encrypter.getInstance().decrypt(jsfUsuario.getUsuPasswd());
                        if (jsfUsuario.getTercero() != null) {
                            excel.ingresarDatos(count, ReporteUsuariosXls.COL_NOMBRE, jsfUsuario.getTercero().getStrnombrecompleto());
                        } else {
                            excel.ingresarDatos(count, ReporteUsuariosXls.COL_NOMBRE, "");
                        }
                        if (jsfUsuario.getTercero() != null) {
                            excel.ingresarDatos(count, ReporteUsuariosXls.COL_EMAIL, jsfUsuario.getTercero().getStremail());
                        } else {
                            excel.ingresarDatos(count, ReporteUsuariosXls.COL_EMAIL, "");
                        }
                        if (jsfUsuario != null) {
                            excel.ingresarDatos(count, ReporteUsuariosXls.COL_USUARIO, jsfUsuario.getUsuLogin());
                        } else {
                            excel.ingresarDatos(count, ReporteUsuariosXls.COL_USUARIO, "");
                        }
                        excel.ingresarDatos(count, 9, password);
                        count++;
                        if (count == ReporteUsuariosXls.COL_MAX_FILAS) {
                            hoja++;
                            excel.crearNuevaHoja(hoja);
                            count = ReporteUsuariosXls.INICIO_HOJAS_EXCEL;
                        }
                        excel.crarFila(count);
                    } catch (Exception ex) {
                        Logger.getLogger(UsuarioServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                count++;
                if (count == ReporteUsuariosXls.COL_MAX_FILAS) {
                    hoja++;
                    excel.crearNuevaHoja(hoja);
                    count = ReporteUsuariosXls.INICIO_HOJAS_EXCEL;
                }
                excel.crarFila(count);
            }
        }
        excel.almacenarArchivo();
        return null;
    }

    public void cambiarContrasenaUsuario() {
        List<JsfUsuario> usuarios = usuarioService.encontrarCorreo(getUsuarioCorreo());
        if (usuarios.size() > 0) {
            usuarioService.setUsuarioObra(usuarios.get(0));
            if (usuarioService.getUsuarioObra().getTercero().getStremail() != null) {
                int year = new Date(new Date().getTime()).getYear();
                int month = new Date(new Date().getTime()).getMonth();
                int day = new Date(new Date().getTime()).getDate();
                day++;
                RestaurarPassword restaurar = new RestaurarPassword();
                restaurar.setRestaurarpassfchainicio(new Date());
                restaurar.setRestaurarpassfchavncmnto(new Date(year, month, day));
                restaurar.setBoolestado(true);
                restaurar.setJsfUsuario(getUsuarioObra());
                boolean res = usuarioService.guardarRestaurarPassword(restaurar, getCobraService());
                if (res == true) {

                    FacesUtils.addErrorMessage (bundle.getString("errortresrecordarcontrasenia"));
                    } else {
                     FacesUtils.addErrorMessage(bundle.getString("errorcuatrorecordarcontrasenia"));
                }
            } else {
                FacesUtils.addErrorMessage(bundle.getString("errordosrecordarcontrasenia"));
            }
        } else {
          
             FacesUtils.addErrorMessage(bundle.getString("errorunorecordarcontrasenia"));
        }
        
    }

    public String prueba() {
        //        System.out.println("id view = " + fc.getViewRoot().getViewId());
        //        System.out.println("this = " + fc.getViewRoot().getId());
        //        System.out.println("Num hijos"+fc.getViewRoot().getChildCount());





        Iterator lista = FacesContext.getCurrentInstance().getViewRoot().getFacetsAndChildren();

        recorrerListaComponentes(lista);

        return null;
    }

    public void recorrerListaComponentes(Iterator lista) {
        while (lista.hasNext()) {
            UIComponent objeto = (UIComponent) lista.next();
            System.out.println("objeto = " + objeto.getId());
            System.out.println("Num hijos" + objeto.getId() + " " + objeto.getChildCount());

//            if (objeto.getId().compareTo("btSoporte") == 0) {
//                //System.out.println("objeto rendered inicial = "+objeto.getId());
//                objeto.setRendered(false);
//
//
//            }


            recorrerListaComponentes(objeto.getFacetsAndChildren());
        }
    }

    public String ciudadanosinregistro() {
        if (getUsuarioService().getUsuarioObra().getUsuLogin().equals("ciudadano")) {
            ciudadano = false;
            //System.out.println("this = " + getUsuarioService().getUsuarioObra().getUsuLogin());
        }
        return null;
    }

    public void numeroobraseguidas() {
        obraseguida = getCiudadanoservice().numeroobrasseguidas(getUsuarioService().getUsuarioObra().getUsuId());

    }
    
    public String nombreModulo(Modulo modulo){
        return modulo.getStrmodNmbre();
    }
}
