/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.Supervisor;

import co.com.interkont.cobra.to.seguimientosah.AhGlobal;
import co.com.interkont.cobra.to.seguimientosah.AlberguesMunicipal;
import co.com.interkont.cobra.to.seguimientosah.ArriendoMuni;
import co.com.interkont.cobra.to.seguimientosah.AsishumaMunicipal;
import cobra.SessionBeanCobra;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Fragment bean that corresponds to a similarly named JSP page fragment.
 * This class contains component definitions (and initialization code) for all
 * components that you have defined on this fragment, as well as lifecycle
 * methods and event handlers where you may add behavior to respond to incoming
 * events.</p>
 *
 * @version SeguimientosAh.java
 * @version Created on 4-sep-2010, 10:21:32
 * @author carlosalbertoloaizaguerrero
 */
public class SeguimientosAh implements Serializable {
    // <editor-fold defaultstate="collapsed" desc="Managed Component Definition">

    private List<AhGlobal> listaglobalah = new ArrayList<AhGlobal>();
    private List<AsishumaMunicipal> listaasishummuni = new ArrayList<AsishumaMunicipal>();
    private List<ArriendoMuni> listaarriendomuni = new ArrayList<ArriendoMuni>();
    private List<AlberguesMunicipal> listaalberguesmuni = new ArrayList<AlberguesMunicipal>();

    public List<AlberguesMunicipal> getListaalberguesmuni() {
        return listaalberguesmuni;
    }

    public void setListaalberguesmuni(List<AlberguesMunicipal> listaalberguesmuni) {
        this.listaalberguesmuni = listaalberguesmuni;
    }

    public List<ArriendoMuni> getListaarriendomuni() {
        return listaarriendomuni;
    }

    public void setListaarriendomuni(List<ArriendoMuni> listaarriendomuni) {
        this.listaarriendomuni = listaarriendomuni;
    }

    public List<AsishumaMunicipal> getListaasishummuni() {
        return listaasishummuni;
    }

    public void setListaasishummuni(List<AsishumaMunicipal> listaasishummuni) {
        this.listaasishummuni = listaasishummuni;
    }

    public List<AhGlobal> getListaglobalah() {
        return listaglobalah;
    }

    public void setListaglobalah(List<AhGlobal> listaglobalah) {
        this.listaglobalah = listaglobalah;
    }

    /**
     * <p>Automatically managed component initialization.
     * <strong>WARNING:</strong>
     * This method is automatically generated, so any user-specified code
     * inserted here is subject to being replaced.</p>
     */
    private void _init() throws Exception {
    }
    // </editor-fold>

    public SeguimientosAh() {
    }

    protected AdministrarObraNew getAdministrarObraNew() {
        return (AdministrarObraNew) FacesUtils.getManagedBean("Supervisor$AdministrarObraNew");
    }

    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }

    public String iniciarSeguimientosAh() {
        listaglobalah = getSessionBeanCobra().getCobraService().obtenerUltimosSeguimientosGlobalAhxSolicitud(getAdministrarObraNew().getObra().getSolicitudmaestro().getOidcodigosolicitudmaestro());
        listaasishummuni = getSessionBeanCobra().getCobraService().obtenerUltimosSeguimientosAsistenciaHumanMpalxSolicitud(getAdministrarObraNew().getObra().getSolicitudmaestro().getOidcodigosolicitudmaestro());
        listaarriendomuni = getSessionBeanCobra().getCobraService().obtenerUltimosSeguimientosArriendoHumanMpalxSolicitud(getAdministrarObraNew().getObra().getSolicitudmaestro().getOidcodigosolicitudmaestro());
        listaalberguesmuni = getSessionBeanCobra().getCobraService().obtenerUltimosSeguimientosAlberguesHumanMpalxSolicitud(getAdministrarObraNew().getObra().getSolicitudmaestro().getOidcodigosolicitudmaestro());
        return "seguimientosah";
    }
}
