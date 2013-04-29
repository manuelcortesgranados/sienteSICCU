/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cobra.Supervisor;


import co.com.interkont.cobra.to.Mensaje;
import co.com.interkont.cobra.to.JsfUsuario;
import co.com.interkont.cobra.to.Tercero;
import cobra.SessionBeanCobra;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.richfaces.component.UIDataTable;

/**
 * <p>Page bean that corresponds to a similarly named JSP page.  This
 * class contains component definitions (and initialization code) for
 * all components that you have defined on this page, as well as
 * lifecycle methods and event handlers where you may add behavior
 * to respond to incoming events.</p>
 *
 * @version Mensajes.java
 * @version Created on Feb 11, 2009, 1:49:53 PM
 * @author jhon
 */

public class Mensajes  {
    // <editor-fold defaultstate="collapsed" desc="Managed Component Definition">

    /**
     * <p>Automatically managed component initialization.  <strong>WARNING:</strong>
     * This method is automatically generated, so any user-specified code inserted
     * here is subject to being replaced.</p>
     */
    private void _init() throws Exception {
    }

    // </editor-fold>
    private List<Mensaje> listaMensajesRecibidos;
    private List<Mensaje> listaMensajesEnviados;
    private Mensaje mensajeEnviadoVer = new Mensaje();
    private Mensaje mensajeRecibidoVer = new Mensaje();
    private Mensaje mensajeRecibidoResponder = new Mensaje();
    private UIDataTable tablaMensajesEnviados = new UIDataTable();
    private UIDataTable tablaMensajesRecibidos = new UIDataTable();
    private String valorFiltroMensajeEnviado = "";
    private String valorFiltroMensajeRecibido = "";
    private JsfUsuario usuarioObra;    
    private List<JsfUsuario> listaUsuarios;
    private SelectItem[] selectItemUsuarios;
    private Mensaje mensajeNuevo = new Mensaje();
    ResourceBundle bundle= getSessionBeanCobra().getBundle();
    
    public List<JsfUsuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<JsfUsuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public Mensaje getMensajeNuevo() {
        return mensajeNuevo;
    }

    public void setMensajeNuevo(Mensaje mensajeNuevo) {
        this.mensajeNuevo = mensajeNuevo;
    }

    public SelectItem[] getSelectItemUsuarios() {
        return selectItemUsuarios;
    }

    public void setSelectItemUsuarios(SelectItem[] selectItemUsuarios) {
        this.selectItemUsuarios = selectItemUsuarios;
    }

    public JsfUsuario getUsuarioObra() {
        return usuarioObra;
    }

    public void setUsuarioObra(JsfUsuario usuarioObra) {
        this.usuarioObra = usuarioObra;
    }

    public List<Mensaje> getListaMensajesEnviados() {
        return listaMensajesEnviados;
    }

    public void setListaMensajesEnviados(List<Mensaje> listaMensajesEnviados) {
        this.listaMensajesEnviados = listaMensajesEnviados;
    }

    public List<Mensaje> getListaMensajesRecibidos() {
        return listaMensajesRecibidos;
    }

    public void setListaMensajesRecibidos(List<Mensaje> listaMensajesRecibidos) {
        this.listaMensajesRecibidos = listaMensajesRecibidos;
    }

    public Mensaje getMensajeEnviadoVer() {
        return mensajeEnviadoVer;
    }

    public void setMensajeEnviadoVer(Mensaje mensajeEnviadoVer) {
        this.mensajeEnviadoVer = mensajeEnviadoVer;
    }

    public Mensaje getMensajeRecibidoResponder() {
        return mensajeRecibidoResponder;
    }

    public void setMensajeRecibidoResponder(Mensaje mensajeRecibidoResponder) {
        this.mensajeRecibidoResponder = mensajeRecibidoResponder;
    }

    public Mensaje getMensajeRecibidoVer() {
        return mensajeRecibidoVer;
    }

    public void setMensajeRecibidoVer(Mensaje mensajeRecibidoVer) {
        this.mensajeRecibidoVer = mensajeRecibidoVer;
    }

    public UIDataTable getTablaMensajesEnviados() {
        return tablaMensajesEnviados;
    }

    public void setTablaMensajesEnviados(UIDataTable tablaMensajesEnviados) {
        this.tablaMensajesEnviados = tablaMensajesEnviados;
    }

    public UIDataTable getTablaMensajesRecibidos() {
        return tablaMensajesRecibidos;
    }

    public void setTablaMensajesRecibidos(UIDataTable tablaMensajesRecibidos) {
        this.tablaMensajesRecibidos = tablaMensajesRecibidos;
    }

    public String getValorFiltroMensajeEnviado() {
        return valorFiltroMensajeEnviado;
    }

    public void setValorFiltroMensajeEnviado(String valorFiltroMensajeEnviado) {
        this.valorFiltroMensajeEnviado = valorFiltroMensajeEnviado;
    }

    public String getValorFiltroMensajeRecibido() {
        return valorFiltroMensajeRecibido;
    }

    public void setValorFiltroMensajeRecibido(String valorFiltroMensajeRecibido) {
        this.valorFiltroMensajeRecibido = valorFiltroMensajeRecibido;
    }


    /**
     * <p>Construct a new Page bean instance.</p>
     */
    public Mensajes() {
        if(getSessionBeanCobra().getUsuarioObra().getTercero().getStrnombre()!=null)
        {
            this.usuarioObra=getSessionBeanCobra().getUsuarioService().encontrarUsuarioPorId(getSessionBeanCobra().getUsuarioObra().getTercero().getIntcodigo());
//            usuarioObra=getSessionBeanCobra().getUsuarioService().encontrarUsuarioporCedula(Integer.parseInt(getSessionBeanCobra().getUsuarioObra().getTercero().getIntcedula()));
             this.mensajeNuevo.setJsfUsuarioByIntusurecibe(new JsfUsuario());
            this.mensajeNuevo.setDatefecmensaje(new Date());
            this.mensajeNuevo.setJsfUsuarioByIntusurecibe(usuarioObra);
            this.mensajeNuevo.setJsfUsuarioByIntusuenvia(usuarioObra);

            this.mensajeRecibidoResponder.setJsfUsuarioByIntusurecibe(new JsfUsuario());
            this.mensajeRecibidoResponder.setDatefecmensaje(new Date());
            llenarSelectUsuarios();
            llenarTablaMensaejesEnviados();
            llenarTablaMensaejesRecibidos();
        }
        else
        {

            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("../Inicio.jspx");

                //this.getSessionMap().clear();
            } catch (IOException ex) {
                Logger.getLogger(Mensajes.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }

    public void llenarTablaMensaejesRecibidos() {
          List<Mensaje> mensajesRecibe = getSessionBeanCobra().getUsuarioService().encontrarMensajesRecibidosUsuario(this.usuarioObra.getUsuId());
        this.listaMensajesRecibidos = new ArrayList<Mensaje>();
        
        if(mensajesRecibe != null && mensajesRecibe.size() > 0){
             this.listaMensajesRecibidos.addAll(mensajesRecibe);
        }
//        Iterator itrtMensaje = this.usuarioObra.getMensajesForIntusurecibe().iterator();
//        this.listaMensajesRecibidos = new ArrayList<Mensaje>();
//        while (itrtMensaje.hasNext()) {
//            this.listaMensajesRecibidos.add((Mensaje) itrtMensaje.next());
//        }
    }

    public void llenarTablaMensaejesEnviados() {
//        Iterator itrtMensaje = this.usuarioObra.getMensajesForIntusuenvia().iterator();
//        this.listaMensajesEnviados = new ArrayList<Mensaje>();
//        while (itrtMensaje.hasNext()) {
//            this.listaMensajesEnviados.add((Mensaje) itrtMensaje.next());
//        }
        List<Mensaje> mensajesEnvia = getSessionBeanCobra().getUsuarioService().encontrarMensajesEnviadosUsuario(this.usuarioObra.getUsuId());
        this.listaMensajesEnviados = new ArrayList<Mensaje>();

        if(mensajesEnvia != null && mensajesEnvia.size() > 0){
             this.listaMensajesEnviados.addAll(mensajesEnvia);
        }
    }

    public boolean filtrarMensajesRecibidos(Object actual) {
        Mensaje msj = (Mensaje) actual;
        if (this.valorFiltroMensajeRecibido.length() == 0) {
            return true;
        }
        if (msj.getStrasuntomensaje().toLowerCase().contains(this.valorFiltroMensajeRecibido.toLowerCase())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean filtrarMensajesEnviados(Object actual) {
        Mensaje msj = (Mensaje) actual;
        if (this.valorFiltroMensajeEnviado.length() == 0) {
            return true;
        }
        if (msj.getStrasuntomensaje().toLowerCase().contains(this.valorFiltroMensajeEnviado.toLowerCase())) {
            return true;
        } else {
            return false;
        }
    }

    public String bt_verrecibidos_action() {
        // TODO: Process the action. Return value is a navigation
        // case name where null will return to the same page.
        this.mensajeRecibidoVer = (Mensaje) tablaMensajesRecibidos.getRowData();
        return null;
    }

    public String bt_verenviados_action() {
        // TODO: Process the action. Return value is a navigation
        // case name where null will return to the same page.
        this.mensajeEnviadoVer = (Mensaje) tablaMensajesEnviados.getRowData();
        return null;
    }
    
    public String bt_responder_action() {
        // TODO: Process the action. Return value is a navigation
        // case name where null will return to the same page.
        this.mensajeRecibidoResponder.setJsfUsuarioByIntusurecibe(this.mensajeRecibidoVer.getJsfUsuarioByIntusuenvia());
        this.mensajeRecibidoResponder.setJsfUsuarioByIntusuenvia(this.mensajeRecibidoVer.getJsfUsuarioByIntusurecibe());
        this.mensajeRecibidoResponder.setStrasuntomensaje(bundle.getString("respuestad")+ this.mensajeRecibidoVer.getStrasuntomensaje());
        return null;
    }

    public String aceptar_responder_action() {

        this.mensajeRecibidoResponder.setDatefecmensaje(new Date(System.currentTimeMillis()));
        getSessionBeanCobra().getUsuarioService().guardarOrActualizarMensaje(mensajeRecibidoResponder);
        this.listaMensajesEnviados.add(this.mensajeRecibidoResponder);
        this.mensajeRecibidoResponder=new Mensaje();
        this.mensajeRecibidoResponder.setDatefecmensaje(new Date());
        this.mensajeRecibidoResponder.setJsfUsuarioByIntusuenvia(new JsfUsuario());
        this.mensajeRecibidoResponder.setJsfUsuarioByIntusurecibe(new JsfUsuario());
        return null;
    }

    public String aceptar_nuevo_action() {
        this.mensajeNuevo.setDatefecmensaje(new Date(System.currentTimeMillis()));
        this.mensajeNuevo.setJsfUsuarioByIntusuenvia(usuarioObra);
        for (JsfUsuario usr : this.listaUsuarios) {
            if (usr.getUsuId() == this.mensajeNuevo.getJsfUsuarioByIntusurecibe().getUsuId()) {
                this.mensajeNuevo.setJsfUsuarioByIntusurecibe(usr);
                break;
            }
        }
        getSessionBeanCobra().getUsuarioService().guardarOrActualizarMensaje(mensajeNuevo);
        this.listaMensajesEnviados.add(this.mensajeNuevo);
        this.mensajeNuevo=new Mensaje();
  
        this.mensajeNuevo.setDatefecmensaje(new Date());
//        this.mensajeNuevo.setJsfUsuarioByIntusuenvia(new JsfUsuario());
//        this.mensajeNuevo.setJsfUsuarioByIntusurecibe(new JsfUsuario());
        this.mensajeNuevo.setJsfUsuarioByIntusurecibe(usuarioObra);
        this.mensajeNuevo.setJsfUsuarioByIntusuenvia(usuarioObra);
        return null;
    }

    public void llenarSelectUsuarios() {
        this.listaUsuarios = new ArrayList<JsfUsuario>();
        Iterator itrListaUsuarios = getSessionBeanCobra().getUsuarioService().encontrarTodoUsuarios().iterator();
        while (itrListaUsuarios.hasNext()) {
            JsfUsuario usr = (JsfUsuario) itrListaUsuarios.next();
            if (usr.getUsuId() != this.usuarioObra.getUsuId() && usr.getUsuEstado()) {
                this.listaUsuarios.add(usr);
            }
        }
        this.selectItemUsuarios = new SelectItem[this.listaUsuarios.size()];
        int i = 0;
        SelectItem selectItem = new SelectItem(0, bundle.getString("seleccioneusuario"));
        itrListaUsuarios = listaUsuarios.iterator();
        while (itrListaUsuarios.hasNext()) {
            JsfUsuario usr = (JsfUsuario) itrListaUsuarios.next();
            selectItem = new SelectItem(usr.getTercero().getIntcedula(), usr.getTercero().getStrnombre() + " " + usr.getTercero().getStrapellido1());
            this.selectItemUsuarios[i++] = selectItem;
        }
    }
}

