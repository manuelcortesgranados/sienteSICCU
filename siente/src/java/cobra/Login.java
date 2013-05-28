/*
 * login.java
 *
 * Created on 15/09/2008, 09:33:20 AM
 */
package cobra;

import co.com.interkont.cobra.to.Grupo;
import co.com.interkont.cobra.to.JsfUsuario;

import java.util.logging.Level;
import java.util.logging.Logger;
import cobra.Supervisor.FacesUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * <p>Page bean that corresponds to a similarly named JSP page.  This
 * class contains component definitions (and initialization code) for
 * all components that you have defined on this page, as well as
 * lifecycle methods and event handlers where you may add behavior
 * to respond to incoming events.</p>
 *
 * @author Christian
 */
public class Login implements Serializable{
    // <editor-fold defaultstate="collapsed" desc="Managed Component Definition">

    /**
     * <p>Automatically managed component initialization.  <strong>WARNING:</strong>
     * This method is automatically generated, so any user-specified code inserted
     * here is subject to being replaced.</p>
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
        try {
            getSessionBeanCobra().setMensajelogueo("");
            

           
            getSessionBeanCobra().setTipologueo(getSessionBeanCobra().getUsuarioService().
                    encontrarUsuario(getSessionBeanCobra().getUsuarioObra()));


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
                    }

                    if (verificarGrupo(getSessionBeanCobra().getUsuarioObra())) {
                        getSessionBeanCobra().getUsuarioService().getLog().info("Auntentico_en_siente_Ciudadano(" + getSessionBeanCobra().getUsuarioObra().getUsuLogin()
                                + ", " + new Date() + ", " + respuesta + ")");
                        getSessionBeanCobra().getCobraService().setAltomapa("350px");
                        getSessionBeanCobra().getCobraService().setHeaderNombre("Herramientas");
                        getSessionBeanCobra().getCobraService().setHeaderStyle("titletool");
                    } else {
                        getSessionBeanCobra().getUsuarioService().getLog().info("Auntentico_en_siente(" + getSessionBeanCobra().getUsuarioObra().getUsuLogin()
                                + ", " + new Date() + ", " + respuesta + ")");
                        getSessionBeanCobra().getCobraService().setAltomapa("450px");
                        getSessionBeanCobra().getCobraService().setHeaderNombre("Inicio");
                        getSessionBeanCobra().getCobraService().setHeaderStyle("headertool");
                        getSessionBeanCobra().getCobraService().setCiu(false);
                    }

                    if (getSessionBeanCobra().getTipologueo().getTipoerror() == 2) {
                        getSessionBeanCobra().getUsuarioObra().setUsuEstado(Boolean.TRUE);
                        getSessionBeanCobra().getUsuarioService().guardarOrActualizarUsuario(getSessionBeanCobra().getUsuarioObra());
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

