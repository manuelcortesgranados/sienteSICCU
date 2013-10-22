/*
 * login.java
 *
 * Created on 15/09/2008, 09:33:20 AM
 */
package cobra;

import co.com.interkont.cobra.to.Comentarioobra;
import co.com.interkont.cobra.to.Grupo;
import co.com.interkont.cobra.to.JsfUsuario;
import co.com.interkont.cobra.to.JsfUsuarioGrupoId;
import co.com.interkont.cobra.to.Localidad;
import co.com.interkont.cobra.to.Tercero;
import co.com.interkont.cobra.to.Tipoidentificacion;
import co.com.interkont.cobra.to.Tipoorigen;
import co.com.interkont.cobra.to.Tiposolicitante;
import co.com.interkont.cobra.to.Tipotercero;
import co.com.interkont.cobra.to.Tipousuario;
import co.com.interkont.cobra.to.utilidades.Propiedad;
import co.interkont.zoomfonadews.autenticacion.to.Usuario;
import cobra.Ciudadano.PerfilCiudadano;
import cobra.Supervisor.FacesUtils;
import cobra.service.UsuarioServiceImpl;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>Page bean that corresponds to a similarly named JSP page. This class
 * contains component definitions (and initialization code) for all components
 * that you have defined on this page, as well as lifecycle methods and event
 * handlers where you may add behavior to respond to incoming events.</p>
 *
 * @author Christian
 */
public class Login implements Serializable {
    // <editor-fold defaultstate="collapsed" desc="Managed Component Definition">

    /**
     * <p>Automatically managed component initialization.
     * <strong>WARNING:</strong>
     * This method is automatically generated, so any user-specified code
     * inserted here is subject to being replaced.</p>
     */
    private void _init() throws Exception {
    }

    // </editor-fold>
    /**
     * <p>Construct a new Page bean instance.</p>
     */
    public Login() {
    }

    /**
     * <p>Return a reference to the scoped data bean.</p>
     *
     * @return reference to the scoped data bean
     */
    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }

    public boolean verificarGrupo(JsfUsuario usu) {
        int i = 0;
        List<Grupo> grupusu = getSessionBeanCobra().getSolicitudService().encontrarGrupoUsuario(usu.getUsuId());
        while (i < grupusu.size()) {
            if (grupusu.get(i).getGruGid() == 21 || grupusu.get(i).getGruGid() == 22) {
                getSessionBeanCobra().getCobraService().setCiu(true);
            } else {
                getSessionBeanCobra().getCobraService().setCiu(false);
            }
            i++;
        }
        return getSessionBeanCobra().getCobraService().isCiu();
    }

    public String enviar_action() {
        // TODO: Process the action. Return value is a navigation
        // case name where null will return to the same page.


        return verificarUsuario();

    }

    public String verificarUsuario() {
        Usuario usuarioExterno = null;
        try {
            getSessionBeanCobra().setMensajelogueo("");



            getSessionBeanCobra().setTipologueo(getSessionBeanCobra().getUsuarioService().
                    encontrarUsuario(getSessionBeanCobra().getUsuarioObra()));

            //Si el sistema está paramatrizado para autenticarse mediante LDAP
            if (Propiedad.getValor("autenticacionLDAP").equals("true") && !getSessionBeanCobra().getTipologueo().isUsuarioEncontrado()) {
                if (getSessionBeanCobra().getTipologueo().getTipoerror() == 0) {
                    try {
                        usuarioExterno = getSessionBeanCobra().getUsuarioService().solicitarAccesoLDAP(getSessionBeanCobra().getUsuarioObra().getUsuLogin(), getSessionBeanCobra().getUsuarioObra().getUsuPasswd());
                    } catch (Exception ex) {
                        getSessionBeanCobra().getTipologueo().setMensajeerror(Propiedad.getValor("errorConexionAutenticacionLDAP"));
                        Logger.getLogger(UsuarioServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (usuarioExterno != null && usuarioExterno.getAcceso() == 1) {
                        getSessionBeanCobra().getCiudadanoservice().setCiudadano(new JsfUsuario());
                        getSessionBeanCobra().getCiudadanoservice().getCiudadano().setTercero(new Tercero());
                        getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero().setStrnombrecompleto(usuarioExterno.getNombre());
                        getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero().setStrnombre(usuarioExterno.getNombre());
                        getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero().setStremail(usuarioExterno.getEmail());
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
                        getSessionBeanCobra().getCiudadanoservice().getCiudadano().setUsuLogin(usuarioExterno.getUsuario());
                        getSessionBeanCobra().getCiudadanoservice().getCiudadano().setLdap(true);
                        getSessionBeanCobra().getCiudadanoservice().getCiudadano().setUsuFchaCrcion(new Date());
                        Calendar fechaVencimiento = Calendar.getInstance();
                        fechaVencimiento.add(Calendar.YEAR, 1);
                        getSessionBeanCobra().getCiudadanoservice().getCiudadano().setUsuFchaVncmnto(fechaVencimiento.getTime());
                        getSessionBeanCobra().getCiudadanoservice().getCiudadano().setUsuEstado(Boolean.FALSE);
                        getSessionBeanCobra().getCiudadanoservice().getCiudadano().setTipousuario(new Tipousuario(1));
                        getSessionBeanCobra().getCiudadanoservice().guardarTercero(getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero());
                        getSessionBeanCobra().getCiudadanoservice().guardarCiudadano(getSessionBeanCobra().getCiudadanoservice().getCiudadano());
                        getSessionBeanCobra().getTipologueo().setTipoerror(1);
                        Comentarioobra comen = new Comentarioobra();
                        comen.setDatefecha(new Date());
                        comen.setJsfUsuario(getSessionBeanCobra().getCiudadanoservice().getCiudadano());
                        comen.setStrdesccoment(getSessionBeanCobra().getCiudadanoservice().getCiudadano().getTercero().getStrnombrecompleto() + " ya es parte de " + Propiedad.getValor("cobra"));
                        getSessionBeanCobra().getCiudadanoservice().guardarComentarioObra(comen);
                        getSessionBeanCobra().getCiudadanoservice().getJsfusuariogrupo().setId(new JsfUsuarioGrupoId(getSessionBeanCobra().getCiudadanoservice().getCiudadano().getUsuId(), 6, 21));
                        getSessionBeanCobra().getCiudadanoservice().guardarJsfUsuarioGrupo(getSessionBeanCobra().getCiudadanoservice().getJsfusuariogrupo());
                        getSessionBeanCobra().getCiudadanoservice().setMensaje(Propiedad.getValor("mensajecorreo"));
                        getSessionBeanCobra().getCiudadanoservice().setBoolmensajeguardar(true);
                        try {
                            EnvioCorreosCobra envio = new EnvioCorreosCobra(getSessionBeanCobra().getCobraService(), getSessionBeanCobra().getUsuarioObra());
                            envio.enviarCorreoCiudadano(getSessionBeanCobra().getCiudadanoservice().getCiudadano());
                        } catch (Exception ex) {
                            Logger.getLogger(PerfilCiudadano.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }

            switch (getSessionBeanCobra().getTipologueo().getTipoerror()) {
                case 0:
                    getSessionBeanCobra().setMensajelogueo(getSessionBeanCobra().getTipologueo().getMensajeerror());
                    FacesUtils.addErrorMessage(getSessionBeanCobra().getTipologueo().getMensajeerror());
                    return "fallo";

                case 1:
                case 2:

                    getSessionBeanCobra().llenadodatos();
                    String respuesta = "";
                    if (getSessionBeanCobra().getModulos().size() == 1) {
                        respuesta = getSessionBeanCobra().getModulos().get(0).getStraction();
                    } else {
                        respuesta = "home";
                        getSessionBeanCobra().getHomeGestion().iniciarHome();
                    }

                    if (verificarGrupo(getSessionBeanCobra().getUsuarioObra())) {
                        getSessionBeanCobra().getUsuarioService().getLog().info("Auntentico_en_"
                                + getSessionBeanCobra().getBundle().getString("versioncobra") + "_Ciudadano(" + getSessionBeanCobra().getUsuarioObra().getUsuLogin()
                                + ", " + new Date() + ", " + respuesta + ")");
                        getSessionBeanCobra().getCobraService().setAltomapa(getSessionBeanCobra().getBundle().getString("altomapaciudadano"));
                        getSessionBeanCobra().getCobraService().setHeaderNombre("Herramientas");
                        getSessionBeanCobra().getCobraService().setHeaderStyle("titletool");
                        getSessionBeanCobra().setLogueado(true);
                        getSessionBeanCobra().getHomeGestion().iniciarHome();
                    } else {
                        getSessionBeanCobra().getUsuarioService().getLog().info("Auntentico_en_" + getSessionBeanCobra().getBundle().getString("versioncobra") + "(" + getSessionBeanCobra().getUsuarioObra().getUsuLogin()
                                + ", " + new Date() + ", " + respuesta + ")");
                        getSessionBeanCobra().getCobraService().setAltomapa(getSessionBeanCobra().getBundle().getString("altomapainterventor"));
                        getSessionBeanCobra().getCobraService().setHeaderNombre("Inicio");
                        getSessionBeanCobra().getCobraService().setHeaderStyle("headertool");
                        getSessionBeanCobra().getCobraService().setCiu(false);
                        getSessionBeanCobra().setLogueado(true);
                        getSessionBeanCobra().getHomeGestion().iniciarHome();
                    }

                    if (getSessionBeanCobra().getTipologueo().getTipoerror() == 2) {
                        if(!getSessionBeanCobra().getCiudadanoservice().getCiudadano().isLdap()) {
                        getSessionBeanCobra().getUsuarioObra().setUsuEstado(Boolean.TRUE);
                        getSessionBeanCobra().getUsuarioService().guardarOrActualizarUsuario(getSessionBeanCobra().getUsuarioObra());
                    }
                    }
                    return respuesta;

            }


        } catch (Exception ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String usuarioSinRegistro() {
        getSessionBeanCobra().getUsuarioService().setUsuarioObra(new JsfUsuario());

        getSessionBeanCobra().getUsuarioObra().setUsuLogin("ciudadano");

        getSessionBeanCobra().getUsuarioObra().setUsuPasswd(ResourceBundle.getBundle("key").getString("key2"));


        return enviar_action();
    }
}
