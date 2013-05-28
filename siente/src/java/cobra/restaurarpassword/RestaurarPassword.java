/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.restaurarpassword;

import Seguridad.Encrypter;
import cobra.SessionBeanCobra;
import cobra.Supervisor.FacesUtils;
import java.io.Serializable;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author desarrollo3
 */
public class RestaurarPassword implements Serializable {

    private ResourceBundle bundle = getSessionBeanCobra().getBundle();
    private String contrasenanuev;
    private String contrasenanuevcomf;
    private boolean  buttonRegresar = false;
    private boolean  buttonEnviar = true;

    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public boolean isButtonEnviar() {
        return buttonEnviar;
    }

    public void setButtonEnviar(boolean buttonEnviar) {
        this.buttonEnviar = buttonEnviar;
    }

    public boolean getButtonRegresar() {
        return buttonRegresar;
    }

    public void setButtonRegresar(boolean buttonRegresar) {
        this.buttonRegresar = buttonRegresar;
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

    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }
    
    public String enviarDatos(){
        return validarpass();
    }

    private String validarpass() {
        Pattern p;
        Matcher m;
        boolean resultado = false;
        try {            
            String contranuev = "";
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
                    FacesUtils.addErrorMessage(bundle.getString("caracterespermitidos"));
                } else {
                    contranuev = Encrypter.getInstance().encrypt(contrasenanuev);
                    getSessionBeanCobra().getUsuarioService().getUsuarioObra().setUsuPasswd(contranuev);
                    getSessionBeanCobra().getCobraService().guardarOrActualizarusernamecontrasenausu(getSessionBeanCobra().getUsuarioService().getUsuarioObra());
                    getSessionBeanCobra().getUsuarioService().cancelarkeysolicitud(getSessionBeanCobra().getUsuarioService().getUsuarioObra());
                    FacesUtils.addInfoMessage(bundle.getString("ingresocorrecto"));
                    buttonRegresar =  true;
                    buttonEnviar = false;
                }
            } else {
                FacesUtils.addErrorMessage(bundle.getString("noconincidenlascontra"));
            }
        } catch (Exception ex) {
        }
        return null;
    }
    
    public String regresarInicio(){
        return "cerrarSession";
    }
}
