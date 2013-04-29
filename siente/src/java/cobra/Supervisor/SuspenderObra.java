/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.Supervisor;

import co.com.interkont.cobra.to.Actividadobra;
import co.com.interkont.cobra.to.Actividadobrahist;
import co.com.interkont.cobra.to.Alimentacion;
import co.com.interkont.cobra.to.Documentoobra;
import co.com.interkont.cobra.to.Historicoobra;
import co.com.interkont.cobra.to.Novedad;
import co.com.interkont.cobra.to.Periodo;
import co.com.interkont.cobra.to.Periodohisto;
import co.com.interkont.cobra.to.Relacionactividadobraperiodo;
import co.com.interkont.cobra.to.Relacionactividadobraperiodohisto;
import co.com.interkont.cobra.to.Tipodocumento;
import co.com.interkont.cobra.to.Tipoestadobra;

import co.com.interkont.cobra.to.Tipomodificacion;
import co.com.interkont.cobra.to.Tiponovedad;
import cobra.CargadorArchivosWeb;
import cobra.SessionBeanCobra;
import cobra.util.RutasWebArchivos;
import com.interkont.cobra.exception.ArchivoExistenteException;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 * <p>Fragment bean that corresponds to a similarly named JSP page
 * fragment.  This class contains component definitions (and initialization
 * code) for all components that you have defined on this fragment, as well as
 * lifecycle methods and event handlers where you may add behavior
 * to respond to incoming events.</p>
 *
 * @version SuspenderObra.java
 * @version Created on 4/02/2010, 11:40:48 PM
 * @author carlos
 */
public class SuspenderObra  {
    // <editor-fold defaultstate="collapsed" desc="Managed Component Definition">

    private String mensajeSuspension = "";
    private String mensajeReinicio = "";
    //Objeto para registrar las suspensiones de obra ya que estàs son un tipo de modificaciòn
    //La fecha de inicio de la suspensión en el campo datefecobrahist
    //La fecha de finalización en el campo datefecfinhist
    //Además de diferenciarse de las modificaciones de obra por poseer la relación tipomodificacionobra 5 Suspender obra
    private Historicoobra suspension = new Historicoobra();
    private CargadorArchivosWeb cargadorActaSuspension = new CargadorArchivosWeb();
    private Documentoobra actaSuspension = new Documentoobra();
    private Historicoobra reinicio = new Historicoobra();
    private CargadorArchivosWeb cargadorActaReinicio = new CargadorArchivosWeb();
    private Documentoobra actaReinicio = new Documentoobra();
    ResourceBundle bundle = getSessionBeanCobra().getBundle();
    private int datoInvalido = 0;

    public int getDatoInvalido() {
        return datoInvalido;
    }

    public void setDatoInvalido(int datoInvalido) {
        this.datoInvalido = datoInvalido;
    }

    public String getMensajeSuspension() {
        return mensajeSuspension;
    }

    public void setMensajeSuspension(String mensajeSuspension) {
        this.mensajeSuspension = mensajeSuspension;
    }

    public String getMensajeReinicio() {
        return mensajeReinicio;
    }

    public void setMensajeReinicio(String mensajeReinicio) {
        this.mensajeReinicio = mensajeReinicio;
    }

    public Historicoobra getSuspension() {
        return suspension;
    }

    public void setSuspension(Historicoobra suspension) {
        this.suspension = suspension;
    }

    public CargadorArchivosWeb getCargadorActaSuspension() {
        return cargadorActaSuspension;
    }

    public void setCargadorActaSuspension(CargadorArchivosWeb cargadorActaSuspension) {
        this.cargadorActaSuspension = cargadorActaSuspension;
    }

    public Historicoobra getReinicio() {
        return reinicio;
    }

    public void setReinicio(Historicoobra reinicio) {
        this.reinicio = reinicio;
    }

    public CargadorArchivosWeb getCargadorActaReinicio() {
        return cargadorActaReinicio;
    }

    public void setCargadorActaReinicio(CargadorArchivosWeb cargadorActaReinicio) {
        this.cargadorActaReinicio = cargadorActaReinicio;
    }

    /**
     * <p>Automatically managed component initialization. <strong>WARNING:</strong>
     * This method is automatically generated, so any user-specified code inserted
     * here is subject to being replaced.</p>
     */
    private void _init() throws Exception {
    }
    // </editor-fold>

    public SuspenderObra() {
    }

    protected AdministrarObraNew getAdministrarObraNew() {
        return (AdministrarObraNew) FacesUtils.getManagedBean("Supervisor$AdministrarObraNew");
    }

    protected DetalleObra getDetalleObra() {
        return (DetalleObra) FacesUtils.getManagedBean("Supervisor$DetalleObra");
    }
    ////Registrando Suspensíon de obra
    private Alimentacion alimult = new Alimentacion();

    public String registrarSuspension() {

        mensajeSuspension = "";
        datoInvalido = 0;

        if (getAdministrarObraNew().getObra().getTipoestadobra().getIntestadoobra() == 1) {
            if (suspension.getDatefecobrahist() != null) {

                if (suspension.getDatefecobrahist().after(getAdministrarObraNew().getObra().getDatefeciniobra())) {

                        alimult = getSessionBeanCobra().getCobraService().obtenerUltimaalimentacion(getAdministrarObraNew().getObra().getIntcodigoobra());
                        boolean band = false;
                        if (alimult == null) {
                            band = true;
                        } else {
                            if (alimult.getDatefecha().compareTo(suspension.getDatefecobrahist()) <= 0) {
                                band = true;
                            } else {
                                FacesUtils.addErrorMessage("La fecha de suspension debe ser mayor a la ultima alimentacion");
                            }
                        }

                        if (band) {
                            if (suspension.getStrrazonmodif() != null && suspension.getStrrazonmodif().compareTo("") != 0) {
                                if (!cargadorActaSuspension.getArchivos().isEmpty()) {
                                    suspension.setDatefechist(new Date());
                                    suspension.setJsfUsuario(getSessionBeanCobra().getUsuarioObra());
                                    suspension.setObra(getAdministrarObraNew().getObra());

                                    if (getAdministrarObraNew().getObra().getTipoestadobra().getIntestadoobra() == 6) {
                                        suspension.getTipomodificacions().add(new Tipomodificacion(9, ""));
                                    } else {
                                        suspension.getTipomodificacions().add(new Tipomodificacion(5, ""));
                                    }
                                    suspension.setNumvalorhist(getAdministrarObraNew().getObra().getNumvaltotobra());
                                    suspension.setStrnuevointervhist(getAdministrarObraNew().getObra().getStrinterventor());
                                    getAdministrarObraNew().getObra().setTipoestadobra(new Tipoestadobra(6));
                                    bt_agregar_actaSuspension_action();
                                    getSessionBeanCobra().getCobraService().guardarDocumento(actaSuspension);
                                    getSessionBeanCobra().getCobraService().guardarHistorico(suspension, getSessionBeanCobra().getUsuarioObra());
                                    getSessionBeanCobra().getCobraService().guardarObra(getAdministrarObraNew().getObra(), getAdministrarObraNew().getObra().getJsfUsuario(),3);
                                    FacesUtils.addErrorMessage(bundle.getString("elproyectohasidosusp"));

                                } else {

                                    FacesUtils.addErrorMessage(bundle.getString("debeadjuntarelact"));
                                }

                            } else {

                                FacesUtils.addErrorMessage(bundle.getString("debeingresarlajusti"));

                            }

                        }


                } else {

                    FacesUtils.addErrorMessage(bundle.getString("lafechafinaldesus"));
                }
            } else {

                FacesUtils.addErrorMessage(bundle.getString("debeingresarlasfechasdeini"));
            }
        } else {

            FacesUtils.addErrorMessage(bundle.getString("laobradebeestarvigente"));

        }

////                            getAdministrarObraNew().getObra().setNovedads(new LinkedHashSet());
////
////                            if (getAdministrarObraNew().getObra().getTipoestadobra().getIntestadoobra() == 6) {
////                                getAdministrarObraNew().getObra().getNovedads().add(new Novedad(0, new Tiponovedad(12,""), getAdministrarObraNew().getObra(), new Date()));
////                            } else {
////                                getAdministrarObraNew().getObra().getNovedads().add(new Novedad(0, new Tiponovedad(11, ""), getAdministrarObraNew().getObra(), new Date()));
////                            }
////                            getSessionBeanCobra().getCobraService().guardarHistorico(suspension, getSessionBeanCobra().getUsuarioObra());
////                            getSessionBeanCobra().getCobraService().guardarObra(getAdministrarObraNew().getObra(), getSessionBeanCobra().getUsuarioObra());
////                            subirActaSuspension.borrarDatosSubidos();
////
//                        datoInvalido = 0; //"El proyecto se ha suspendido";
////                            FacesUtils.addErrorMessage(bundle.getString("laobrasehasuspendido"));
////                            return null;



        suspension = new Historicoobra();
        actaSuspension= new Documentoobra();
        cargadorActaSuspension= new CargadorArchivosWeb();
        return null;

    }

    ///Cancelando la Suspensión de Obra
    public String cancelarSuspension() {
        suspension = new Historicoobra();
        cargadorActaSuspension.borrarDatosSubidos();
        getAdministrarObraNew().mostrarMenuModificar();
        getAdministrarObraNew().noMostrarSuspender();

        return "menumodificar";
    }

    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }

    public String cargarSuspension() {
        mensajeSuspension = "";
        suspension = new Historicoobra();
        if (getAdministrarObraNew().getObra().getTipoestadobra().getIntestadoobra() == 6) {
//            suspension.setDatefecobrahist(getAdministrarObraNew().getObra().getUltimoHistorico().getDatefecobrahist());
//            suspension.setDatefecfinhist(getAdministrarObraNew().getObra().getUltimoHistorico().getDatefecfinhist());
//            suspension.setStrrazonmodif(getAdministrarObraNew().getObra().getUltimoHistorico().getStrrazonmodif());
            suspension = getSessionBeanCobra().getCobraService().obtenerUltimoHistorico(getAdministrarObraNew().getObra().getIntcodigoobra());
            suspension.setJsfUsuario(getSessionBeanCobra().getUsuarioObra());
            suspension.getTipomodificacions().add(new Tipomodificacion(9, ""));
        }
        getAdministrarObraNew().mostrarSuspender();
        return null;
    }

    public String bt_agregar_actaSuspension_action() {
        try {
            Tipodocumento td = new Tipodocumento();
            td.setInttipodoc(18);
            actaSuspension = new Documentoobra();
            actaSuspension.setDatefecha(new Date());
            actaSuspension.setObra(getAdministrarObraNew().getObra());
            actaSuspension.setStrnombre("Acta de suspension");
            actaSuspension.setTipodocumento(td);
            subirActaSuspension();
        } catch (ArchivoExistenteException ex) {
            Logger.getLogger(SuspenderObra.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void subirActaSuspension() throws ArchivoExistenteException {
        if (cargadorActaSuspension.getNumArchivos() > 0) {
            cargadorActaSuspension.guardarArchivosTemporales(MessageFormat.format(RutasWebArchivos.DOCS_SUSPENSION_OBRA, ""+getAdministrarObraNew().getObra().getIntcodigoobra()), false);
            this.actaSuspension.setStrubicacion(cargadorActaSuspension.getArchivos().get(0).getRutaWeb());
        }
    }

    ////Reinicio de Obra
    public String cargarReinicio() {
        mensajeReinicio = "";
        reinicio = new Historicoobra();
        reinicio.getTipomodificacions().add(new Tipomodificacion(10, ""));

        suspension = new Historicoobra();
//        suspension.setDatefecobrahist(getAdministrarObraNew().getObra().getUltimoHistorico().getDatefecobrahist());
//        suspension.setDatefecfinhist(getAdministrarObraNew().getObra().getUltimoHistorico().getDatefecfinhist());
        suspension = getSessionBeanCobra().getCobraService().obtenerUltimoHistorico(getAdministrarObraNew().getObra().getIntcodigoobra());
        getAdministrarObraNew().mostrarReinicio();
        return "reiniciarObra";
    }

    public String bt_agregar_actaReinicio_action() {
        Tipodocumento td = new Tipodocumento();
        td.setInttipodoc(19);
        actaReinicio = new Documentoobra();
        actaReinicio.setDatefecha(new Date());
        actaReinicio.setObra(getAdministrarObraNew().getObra());
        actaReinicio.setStrnombre("Acta de Reinicio");
        actaReinicio.setTipodocumento(td);
        try {
            subirActaReinicio();
        } catch (ArchivoExistenteException ex) {
            Logger.getLogger(SuspenderObra.class.getName()).log(Level.SEVERE, null, ex);
        }

        //getAdministrarObraNew().getObra().getDocumentoobras().add(actaReinicio);
        return null;
    }

    public void subirActaReinicio() throws ArchivoExistenteException {
        if(!cargadorActaReinicio.getArchivos().isEmpty()) {
            cargadorActaReinicio.guardarArchivosTemporales(MessageFormat.format(RutasWebArchivos.DOCS_OBRA, ""+getAdministrarObraNew().getObra().getIntcodigoobra()), false);
            this.actaReinicio.setStrubicacion(cargadorActaReinicio.getArchivos().get(0).getRutaWeb());
        }
    }

    public String reiniciarObra() {
        ServletContext contexto = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

        //suspension = getAdministrarObraNew().getObra().getUltimaSuspension();
        suspension = getSessionBeanCobra().getCobraService().obtenerUltimoHistorico(getAdministrarObraNew().getObra().getIntcodigoobra());
        mensajeReinicio = "";

        if (reinicio.getDatefecobrahist() != null) {

            if (reinicio.getDatefecobrahist().compareTo(suspension.getDatefecobrahist()) >= 0) {
//                if (reinicio.getDatefecobrahist().compareTo(getAdministrarObraNew().getObra().getDatefecfinobra()) < 0) {
                    if (!cargadorActaReinicio.getArchivos().isEmpty()) {
                        // Persistir Suspensión, cambiar estado de obra a 6 - Estado suspendida, registrar novedad
                        reinicio.setDatefecfinhist(suspension.getDatefecfinhist());
                        reinicio.setJsfUsuario(getSessionBeanCobra().getUsuarioObra());
                        reinicio.setObra(getAdministrarObraNew().getObra());

                        reinicio.getTipomodificacions().add(new Tipomodificacion(10, ""));

                        reinicio.setNumvalorhist(getAdministrarObraNew().getObra().getNumvaltotobra());
                        reinicio.setStrnuevointervhist(getAdministrarObraNew().getObra().getStrinterventor());
                        getAdministrarObraNew().getObra().setTipoestadobra(new Tipoestadobra(1));

                        reinicio.setDatefechist(new Date());
                        reinicio.setStrrazonmodif(bundle.getString("laobrasereinicio"));//"La obra se reinició después de estar suspendida."

                        bt_agregar_actaReinicio_action();
                         getSessionBeanCobra().getCobraService().guardarDocumento(actaReinicio);

//                        if (actaReinicio != null && actaReinicio.getStrubicacion() != null && !actaReinicio.getStrubicacion().equals("")) {
//                            persistirDocumento(actaReinicio);
//                            ////Volviendo a cargar el actaconvenio en el file upload
//                            String path = actaReinicio.getStrubicacion();
//
//                            path = contexto.getRealPath(path);
//                            String nombre = "";
//
//                            if (path.contains("%20")) {
//                                int i = 0;
//                                while (i < path.length()) {
//                                    if (String.valueOf(path.charAt(i)).compareTo("%") == 0) {
//                                        nombre = nombre + " ";
//                                        i = i + 2;
//                                    } else {
//                                        nombre = nombre + String.valueOf(path.charAt(i));
//                                    }
//                                    i++;
//                                }
//                            } else {
//                                nombre = path;
//                            }
//
//                            try {
//                                File imagen = new File(nombre);
//                                if (imagen.getTotalSpace() == 0) {
//                                } else {
//                                    subirActaReinicio.adicionarArchivo(imagen);
//                                }
//                            } catch (Exception ex) {
//                                Logger.getLogger(AdministrarObraNew.class.getName()).log(Level.SEVERE, null, ex);
//                            }
//                        }

                        getAdministrarObraNew().getObra().setNovedads(new LinkedHashSet());
                        getAdministrarObraNew().getObra().getNovedads().add(new Novedad(0, new Tiponovedad(13, ""), getAdministrarObraNew().getObra(), new Date()));
                        getSessionBeanCobra().getCobraService().guardarHistorico(reinicio, getSessionBeanCobra().getUsuarioObra());
                        getSessionBeanCobra().getCobraService().guardarObra(getAdministrarObraNew().getObra(), getSessionBeanCobra().getUsuarioObra(), 3);
                        cargadorActaReinicio.borrarDatosSubidos();
                        reinicio = new Historicoobra();
                        suspension = new Historicoobra();
                        datoInvalido = 0; //"El proyecto se ha reiniciado";
                        return "reiniciarproyecto";

                    } else {
                        datoInvalido = 1; //"Debe adjuntar el acta de reinicio de proyecto";
                    }
//                } else {
//                    datoInvalido = 2; //"La fecha de reinicio de proyecto debe ser menor a la fecha de finalización del proyecto";
//                }
            } else {
                datoInvalido = 3; //"La fecha de reinicio de proyecto debe ser menor o igual a la fecha final de la suspensión del proyecto";
            }
        } else {
            datoInvalido = 4; //"Debe ingresar la fecha de reinicio del proyecto";
        }
        return null;
    }

    ///Cancelando el reinicio de la Obra
    public String cancelarReinicio() {
        reinicio = new Historicoobra();
        cargadorActaReinicio.borrarDatosSubidos();
        getAdministrarObraNew().mostrarMenuModificar();
        getAdministrarObraNew().mostrarReinicio();

        return "menumodificar";
    }

    private void persistirDocumento(Documentoobra doc) {
        getSessionBeanCobra().getCobraService().guardarDocumento(doc);
    }

    public String backupPlaneacion() {
        ///Backup Actividades

        Iterator itperiodo = getAdministrarObraNew().getObra().getPeriodos().iterator();
        while (itperiodo.hasNext()) {
            Periodo per = (Periodo) itperiodo.next();
            Periodohisto periohisto = new Periodohisto();
            periohisto.setDatefecfinperiodo(per.getDatefecfinperiodo());
            periohisto.setDatefeciniperiodo(per.getDatefeciniperiodo());
            periohisto.setHistoricoobra(suspension);
            periohisto.setNumvaltotplanif(per.getNumvaltotplanif());

            Iterator itrelper = per.getRelacionactividadobraperiodos().iterator();
            while (itrelper.hasNext()) {
                Relacionactividadobraperiodo rela = (Relacionactividadobraperiodo) itrelper.next();
                Relacionactividadobraperiodohisto relahist = new Relacionactividadobraperiodohisto();
                Actividadobrahist acthist = new Actividadobrahist();
                acthist.setFloatcantidadejecutao(rela.getActividadobra().getFloatcantidadejecutao());
                acthist.setFloatcantplanifao(rela.getActividadobra().getFloatcantplanifao());
                acthist.setHistoricoobra(suspension);
                acthist.setNumvalorejecutao(rela.getActividadobra().getNumvalorejecutao());
                acthist.setNumvalorplanifao(rela.getActividadobra().getNumvalorplanifao());
                acthist.setStrcodcubs(rela.getActividadobra().getStrcodcubs());
                acthist.setStrdescactividad(rela.getActividadobra().getStrdescactividad());
                acthist.setStrtipounidadmed(rela.getActividadobra().getStrtipounidadmed());
                acthist.setJsfUsuario(rela.getActividadobra().getJsfUsuario());
                relahist.setActividadobrahist(acthist);
                relahist.setFloatcantplanif(rela.getFloatcantplanif());
                relahist.setNumvalplanif(rela.getNumvalplanif());
                relahist.setPeriodohisto(periohisto);
                periohisto.getRelacionactividadobraperiodohistos().add(relahist);
            }
            suspension.getPeriodohistos().add(periohisto);
        }

        Iterator itActi = getAdministrarObraNew().getObra().getActividadobras().iterator();
        List<Periodohisto> listaperhistos = new ArrayList<Periodohisto>(reinicio.getPeriodohistos());
        while (itActi.hasNext()) {
            Actividadobrahist acthist = new Actividadobrahist();
            Actividadobra actobra = (Actividadobra) itActi.next();
            acthist.setFloatcantidadejecutao(actobra.getFloatcantidadejecutao());
            acthist.setFloatcantplanifao(actobra.getFloatcantplanifao());

            Historicoobra h = new Historicoobra();
            h.setOididhistoricoobra(reinicio.getOididhistoricoobra());
            acthist.setHistoricoobra(h);
            acthist.setNumvalorejecutao(actobra.getNumvalorejecutao());
            acthist.setNumvalorplanifao(actobra.getNumvalorplanifao());
            acthist.setStrcodcubs(actobra.getStrcodcubs());
            acthist.setStrdescactividad(actobra.getStrdescactividad());
            acthist.setStrtipounidadmed(actobra.getStrtipounidadmed());
            acthist.setJsfUsuario(actobra.getJsfUsuario());

            //periodos
            List<Relacionactividadobraperiodohisto> listarelahisto = new ArrayList<Relacionactividadobraperiodohisto>();

            int i = 0;

            while (i < listaperhistos.size()) {
                Iterator itrelaper = listaperhistos.get(i).getRelacionactividadobraperiodohistos().iterator();
                while (itrelaper.hasNext()) {
                    Relacionactividadobraperiodohisto rela = (Relacionactividadobraperiodohisto) itrelaper.next();
                    if (rela.getActividadobrahist().getStrdescactividad().compareTo(acthist.getStrdescactividad()) == 0
                            && rela.getActividadobrahist().getFloatcantidadejecutao().equals(acthist.getFloatcantidadejecutao())
                            && rela.getActividadobrahist().getFloatcantplanifao() == acthist.getFloatcantplanifao()
                            && rela.getActividadobrahist().getNumvalorejecutao().equals(acthist.getNumvalorejecutao())
                            && rela.getActividadobrahist().getNumvalorplanifao().equals(acthist.getNumvalorplanifao())
                            && rela.getActividadobrahist().getStrtipounidadmed().compareTo(acthist.getStrtipounidadmed()) == 0) {
                        rela.setActividadobrahist(acthist);
                        listarelahisto.add(rela);
                    }
                }
                i++;
            }
            acthist.setRelacionactividadobraperiodohistos(new HashSet(listarelahisto));
            reinicio.getActividadobrahists().add(acthist);
        }

        List<Historicoobra> ithistos = new ArrayList<Historicoobra>(getAdministrarObraNew().getObra().getHistoricoobras());
        int i = 0;
        getAdministrarObraNew().getObra().setHistoricoobras(new HashSet());
        while (i < ithistos.size()) {
            if (ithistos.get(i).getOididhistoricoobra() == reinicio.getOididhistoricoobra()) {

                ithistos.set(i, reinicio);
            }
            i++;
        }
        getAdministrarObraNew().getObra().setHistoricoobras(new HashSet(ithistos));
        return null;





    }
// Se hace backup de los periodos y actividades actuales de la obra antes de
    // reiniciar la obra. Después de hacer el backup se calculan las cantidades
    // que están atrasadas para cada periodo que cubre el periodo de suspensión
    // y se acumulan para el periodo siguiente o activo que contiene la fecha de
    // reinicio de obra. Conjunto con el backup realizado se almacena un histórico
    // de la obra indicando que esta fue reiniciada después de un periodo de
    // suspensión.
 /*   public void backupObraYCalcularReinicio() {
    Historicoobra histoguardar = new Historicoobra();

    histoguardar.setObra(getAdministrarObraNew().getObra());
    histoguardar.setUsuario(getAdministrarObraNew().getObra().getUsuario());

    histoguardar.setDatefechist(new Date());
    histoguardar.setDatefecobrahist(getAdministrarObraNew().getObra().getDatefeciniobra());
    histoguardar.setDatefecfinhist(getAdministrarObraNew().getObra().getDatefecfinobra());

    histoguardar.setNumvalorhist(getAdministrarObraNew().getObra().getNumvaltotobra());
    histoguardar.setStrrazonmodif("Reinicio de la obra después de suspensión entre " + new SimpleDateFormat("yyyy-MM-dd").format(suspension.getDatefecobrahist()) + " y " + new SimpleDateFormat("yyyy-MM-dd").format(reinicio.getDatefecobrahist()) + ".");

    getAdministrarObraNew().getObra().getHistoricoobras().add(histoguardar);

    // Se almacena el historico
    getSessionBeanCobra().setCobradao(new CobraDao());
    getSessionBeanCobra().getCobradao().guardarOrActualizarObra(getAdministrarObraNew().castearObra(getAdministrarObraNew().getObra()));

    histoguardar = getAdministrarObraNew().getObra().getUltimoHistorico();

    Iterator itperiodo = getAdministrarObraNew().getObra().getPeriodos().iterator();
    while (itperiodo.hasNext()) {
    Periodo per = (Periodo) itperiodo.next();
    Periodohisto periohisto = new Periodohisto();
    periohisto.setDatefecfinperiodo(per.getDatefecfinperiodo());
    periohisto.setDatefeciniperiodo(per.getDatefeciniperiodo());
    periohisto.setHistoricoobra(histoguardar);
    periohisto.setNumvaltotplanif(per.getNumvaltotplanif());

    Iterator itrelper = per.getRelacionactividadobraperiodos().iterator();
    while (itrelper.hasNext()) {
    Relacionactividadobraperiodo rela = (Relacionactividadobraperiodo) itrelper.next();
    Relacionactividadobraperiodohisto relahist = new Relacionactividadobraperiodohisto();
    Actividadobrahist acthist = new Actividadobrahist();
    acthist.setFloatcantidadejecutao(rela.getActividadobra().getFloatcantidadejecutao());
    acthist.setFloatcantplanifao(rela.getActividadobra().getFloatcantplanifao());
    acthist.setHistoricoobra(histoguardar);
    acthist.setNumvalorejecutao(rela.getActividadobra().getNumvalorejecutao());
    acthist.setNumvalorplanifao(rela.getActividadobra().getNumvalorplanifao());
    acthist.setStrcodcubs(rela.getActividadobra().getStrcodcubs());
    acthist.setStrdescactividad(rela.getActividadobra().getStrdescactividad());
    acthist.setStrtipounidadmed(rela.getActividadobra().getStrtipounidadmed());
    acthist.setUsuario(rela.getActividadobra().getUsuario());
    relahist.setActividadobrahist(acthist);
    relahist.setFloatcantplanif(rela.getFloatcantplanif());
    relahist.setNumvalplanif(rela.getNumvalplanif());
    relahist.setPeriodohisto(periohisto);
    periohisto.getRelacionactividadobraperiodohistos().add(relahist);
    }
    histoguardar.getPeriodohistos().add(periohisto);
    }

    Iterator itActi = getAdministrarObraNew().getObra().getActividadobras().iterator();
    List<Periodohisto> listaperhistos = new ArrayList<Periodohisto>(histoguardar.getPeriodohistos());
    while (itActi.hasNext()) {
    Actividadobrahist acthist = new Actividadobrahist();
    Actividadobra actobra = (Actividadobra) itActi.next();
    acthist.setFloatcantidadejecutao(actobra.getFloatcantidadejecutao());
    acthist.setFloatcantplanifao(actobra.getFloatcantplanifao());

    Historicoobra h = new Historicoobra();
    h.setOididhistoricoobra(histoguardar.getOididhistoricoobra());
    acthist.setHistoricoobra(h);
    acthist.setNumvalorejecutao(actobra.getNumvalorejecutao());
    acthist.setNumvalorplanifao(actobra.getNumvalorplanifao());
    acthist.setStrcodcubs(actobra.getStrcodcubs());
    acthist.setStrdescactividad(actobra.getStrdescactividad());
    acthist.setStrtipounidadmed(actobra.getStrtipounidadmed());
    acthist.setUsuario(actobra.getUsuario());

    // Periodos
    List<Relacionactividadobraperiodohisto> listarelahisto= new ArrayList<Relacionactividadobraperiodohisto>();

    int i = 0;

    while (i < listaperhistos.size()) {
    Iterator itrelaper = listaperhistos.get(i).getRelacionactividadobraperiodohistos().iterator();
    while (itrelaper.hasNext()) {
    Relacionactividadobraperiodohisto rela = (Relacionactividadobraperiodohisto) itrelaper.next();
    if(rela.getActividadobrahist().getStrdescactividad().compareTo(acthist.getStrdescactividad())==0 &&
    rela.getActividadobrahist().getFloatcantidadejecutao().equals(acthist.getFloatcantidadejecutao()) &&
    rela.getActividadobrahist().getFloatcantplanifao()== acthist.getFloatcantplanifao() &&
    rela.getActividadobrahist().getNumvalorejecutao().equals(acthist.getNumvalorejecutao()) &&
    rela.getActividadobrahist().getNumvalorplanifao().equals(acthist.getNumvalorplanifao()) &&
    rela.getActividadobrahist().getStrtipounidadmed().compareTo(acthist.getStrtipounidadmed())==0) {
    rela.setActividadobrahist(acthist);
    listarelahisto.add(rela);
    }
    }
    i++;
    }
    acthist.setRelacionactividadobraperiodohistos(new HashSet(listarelahisto));
    histoguardar.getActividadobrahists().add(acthist);
    }

    List<Historicoobra> ithistos = new ArrayList<Historicoobra>(getAdministrarObraNew().getObra().getHistoricoobras());
    int i = 0;
    getAdministrarObraNew().getObra().setHistoricoobras(new HashSet());
    while (i < ithistos.size()) {
    if (ithistos.get(i).getOididhistoricoobra() == histoguardar.getOididhistoricoobra()) {
    ithistos.set(i, histoguardar);
    }
    i++;
    }
    getAdministrarObraNew().getObra().setHistoricoobras(new HashSet(ithistos));

    // Prorrateo para valores acumulados después del reinicio

    // Generación de objetos fecha necesarios
    DateTime fechaInicioSuspension = new DateTime(suspension.getDatefecobrahist());
    DateTime fechaReinicioObra = new DateTime(reinicio.getDatefecobrahist());
    DateTime fechaFinalObra = new DateTime(getAdministrarObraNew().getObra().getDatefecfinobra());

    // Calculo del intervalo donde la obra estuvo suspendida.
    Interval intervaloSuspension = new Interval(fechaInicioSuspension, fechaReinicioObra);

    // Mapa que contiene los periodos de la obra
    Map<Integer, Periodo> periodosVigentes = new TreeMap<Integer, Periodo>();

    // Datos de atraso por periodo
    Map<Integer, Interval> intervalosPeriodos = new TreeMap<Integer, Interval>();
    Map<Integer, Integer> longitudesOverlapsPeriodos = new TreeMap<Integer, Integer>();
    Map<Integer, BigDecimal> prorrateosPeriodos = new TreeMap<Integer, BigDecimal>();
    Map<Integer, BigDecimal> atrasosPeriodos = new TreeMap<Integer, BigDecimal>();

    // Datos de atraso por actividad. Se tienen en cuenta el valor de la actividad atrasada y la cantidad
    Map<Integer, Map<Integer, BigDecimal>> prorrateosValoresActividades = new TreeMap<Integer, Map<Integer, BigDecimal>>();
    Map<Integer, Map<Integer, BigDecimal>> prorrateosCantidadesActividades = new TreeMap<Integer, Map<Integer, BigDecimal>>();
    Map<Integer, Map<Integer, BigDecimal>> atrasosValoresActividades = new TreeMap<Integer, Map<Integer, BigDecimal>>();
    Map<Integer, Map<Integer, BigDecimal>> atrasosCantidadesActividades = new TreeMap<Integer, Map<Integer, BigDecimal>>();

    // Se itera sobre todos los periodos vigentes de la obra
    itperiodo = getAdministrarObraNew().getObra().getPeriodos().iterator();
    while (itperiodo.hasNext()) {
    Periodo per = (Periodo) itperiodo.next();

    int idPeriodo = per.getIntidperiodo();

    periodosVigentes.put(idPeriodo, per);

    DateTime inicioPeriodo = new DateTime(per.getDatefeciniperiodo());
    DateTime finalPeriodo = new DateTime(per.getDatefecfinperiodo());

    Interval intervaloPeriodo = null;

    // Calculo del intervalo donde la obra estuvo suspendida.
    if(finalPeriodo.isAfter(fechaFinalObra)) {
    intervaloPeriodo = new Interval(inicioPeriodo, fechaFinalObra);
    } else {
    intervaloPeriodo = new Interval(inicioPeriodo, finalPeriodo);
    }

    intervalosPeriodos.put(idPeriodo, intervaloPeriodo);

    // Se verifica que el periodo de la iteración se intersecte con el
    // periodo de suspensión.
    // Si el periodo iterado se intersecta con el periodo de suspensión,
    // se lleva la cuenta de cuántos días se intersectan para acumularlos

    Interval interseccion = intervaloPeriodo.overlap(intervaloSuspension);

    int diasPeriodo = Days.daysIn(intervaloPeriodo).getDays() + 1;
    int diasInterseccion = 0;

    if(interseccion != null) {
    diasInterseccion = Days.daysIn(interseccion).getDays() + 1;
    prorrateosPeriodos.put(idPeriodo, per.getNumvaltotplanif().divide(new BigDecimal(diasPeriodo), 2, RoundingMode.CEILING));
    } else {
    prorrateosPeriodos.put(idPeriodo, new BigDecimal("0.0"));
    }

    longitudesOverlapsPeriodos.put(idPeriodo, diasInterseccion);

    Iterator itrelper = per.getRelacionactividadobraperiodos().iterator();

    Map<Integer, BigDecimal> prorrateoValorActividad = new TreeMap<Integer, BigDecimal>();
    Map<Integer, BigDecimal> prorrateoCantidadActividad = new TreeMap<Integer, BigDecimal>();

    while (itrelper.hasNext()) {
    Relacionactividadobraperiodo rela = (Relacionactividadobraperiodo) itrelper.next();
    Actividadobra actividad = rela.getActividadobra();
    prorrateoValorActividad.put(actividad.getOidactiviobra(),actividad.getNumvalorplanifao().divide(new BigDecimal(diasPeriodo), 2, RoundingMode.CEILING));
    prorrateoCantidadActividad.put(actividad.getOidactiviobra(), new BigDecimal(actividad.getFloatcantplanifao()).divide(new BigDecimal(diasPeriodo), 2, RoundingMode.CEILING));
    }

    prorrateosValoresActividades.put(idPeriodo, prorrateoValorActividad);
    prorrateosCantidadesActividades.put(idPeriodo, prorrateoCantidadActividad);
    }

    // Se encuentra el periodo donde se reinició la obra.
    Iterator intervalos = intervalosPeriodos.entrySet().iterator();
    int periodoReinicio = 0;

    while(intervalos.hasNext()) {
    Entry entradaMapa = (Entry) intervalos.next();
    Interval intervalo = (Interval)entradaMapa.getValue();

    if(intervalo.contains(fechaReinicioObra) || intervalo.getStart().isEqual(fechaReinicioObra) || intervalo.getEnd().isEqual(fechaReinicioObra)) {
    if(intervalo.getEnd().isEqual(fechaReinicioObra)) {
    // Si el día del periodo donde se reinició la obra es el último
    // día del periodo el periodo se toma como el siguiente
    // periodo si hay.
    entradaMapa = (Entry) intervalos.next();
    periodoReinicio = (Integer) entradaMapa.getKey();
    } else {
    periodoReinicio = (Integer) entradaMapa.getKey();
    }
    break;
    }
    }

    Periodo periodoAcumular = periodosVigentes.get(periodoReinicio);

    // Se calculan las cantidades de atraso y se acumula el valor total bruto
    // atrasado.

    itperiodo = getAdministrarObraNew().getObra().getPeriodos().iterator();

    Double atrasadoTotalBruto = 0.0;
    Map<Integer, Map<Integer, BigDecimal>> atrasoTotalBrutoValorActividad = new TreeMap<Integer, Map<Integer, BigDecimal>>();
    Map<Integer, Map<Integer, BigDecimal>> atrasoTotalBrutoCantidadActividad = new TreeMap<Integer, Map<Integer, BigDecimal>>();

    Integer idPeriodo = null;
    BigDecimal atraso = null;

    while (itperiodo.hasNext()) {
    Periodo per = (Periodo) itperiodo.next();
    idPeriodo = per.getIntidperiodo();

    atraso = new BigDecimal(longitudesOverlapsPeriodos.get(idPeriodo)).multiply(prorrateosPeriodos.get(idPeriodo));
    atrasosPeriodos.put(idPeriodo, atraso);
    atrasadoTotalBruto = atraso.doubleValue() + atrasadoTotalBruto;

    Iterator itrelper = per.getRelacionactividadobraperiodos().iterator();

    Map<Integer, BigDecimal> prorrateoValorActividad = prorrateosValoresActividades.get(idPeriodo);
    Map<Integer, BigDecimal> prorrateoCantidadActividad = prorrateosCantidadesActividades.get(idPeriodo);

    Map<Integer, BigDecimal> atrasoValorActividad = new TreeMap<Integer, BigDecimal>();
    Map<Integer, BigDecimal> atrasoCantidadActividad = new TreeMap<Integer, BigDecimal>();

    Map<Integer, BigDecimal> atrasoTotalValorActividad = new TreeMap<Integer, BigDecimal>();
    Map<Integer, BigDecimal> atrasoTotalCantidadActividad = new TreeMap<Integer, BigDecimal>();

    Integer idActividad = null;
    BigDecimal atrasoValor = null;
    Double atrasoTotalBrutoValor = 0.0;
    BigDecimal atrasoCantidad = null;
    Double atrasoTotalBrutoCantidad = 0.0;

    while (itrelper.hasNext()) {
    Relacionactividadobraperiodo rela = (Relacionactividadobraperiodo) itrelper.next();
    Actividadobra actividad = rela.getActividadobra();

    idActividad = actividad.getOidactiviobra();

    atrasoValor = new BigDecimal(longitudesOverlapsPeriodos.get(idPeriodo)).multiply(prorrateoValorActividad.get(idActividad));
    atrasoCantidad = new BigDecimal(longitudesOverlapsPeriodos.get(idPeriodo)).multiply(prorrateoCantidadActividad.get(idActividad));

    atrasoValorActividad.put(idActividad, atrasoValor);
    atrasoCantidadActividad.put(idActividad, atrasoCantidad);

    if(atrasoTotalValorActividad.get(idActividad) != null)
    atrasoTotalBrutoValor = atrasoTotalValorActividad.get(idActividad).doubleValue();

    if(atrasoTotalCantidadActividad.get(idActividad) != null)
    atrasoTotalBrutoCantidad = atrasoTotalCantidadActividad.get(idActividad).doubleValue();

    atrasoTotalBrutoValor += atrasoValor.doubleValue();
    atrasoTotalBrutoCantidad += atrasoCantidad.doubleValue();

    atrasoTotalValorActividad.put(idActividad, new BigDecimal(atrasoTotalBrutoValor));
    atrasoTotalCantidadActividad.put(idActividad, new BigDecimal(atrasoTotalBrutoCantidad));
    }

    atrasosValoresActividades.put(idPeriodo, atrasoValorActividad);
    atrasosCantidadesActividades.put(idPeriodo, atrasoCantidadActividad);

    atrasoTotalBrutoValorActividad.put(idPeriodo, atrasoTotalValorActividad);
    atrasoTotalBrutoCantidadActividad.put(idPeriodo, atrasoTotalCantidadActividad);
    }

    BigDecimal atrasadoTotalNeto = new BigDecimal(atrasadoTotalBruto).subtract(atrasosPeriodos.get(periodoReinicio));

    Map<Integer, BigDecimal> atrasoTotalBrutoValor = atrasoTotalBrutoValorActividad.get(periodoReinicio);
    Map<Integer, BigDecimal> atrasoTotalBrutoCantidad = atrasoTotalBrutoCantidadActividad.get(periodoReinicio);

    Map<Integer, BigDecimal> atrasosValores = atrasosValoresActividades.get(periodoReinicio);
    Map<Integer, BigDecimal> atrasosCantidades = atrasosCantidadesActividades.get(periodoReinicio);

    Map<Integer, BigDecimal> atrasoTotalNetoValor = new TreeMap<Integer, BigDecimal>();
    Map<Integer, BigDecimal> atrasoTotalNetoCantidad = new TreeMap<Integer, BigDecimal>();

    Iterator itrelper = periodoAcumular.getRelacionactividadobraperiodos().iterator();

    Integer idActividad = null;

    while (itrelper.hasNext()) {
    Relacionactividadobraperiodo rela = (Relacionactividadobraperiodo) itrelper.next();
    Actividadobra actividad = rela.getActividadobra();

    idActividad = actividad.getOidactiviobra();

    atrasoTotalNetoValor.put(idActividad, atrasoTotalBrutoValor.get(idActividad).subtract(atrasosValores.get(idActividad)));
    atrasoTotalNetoCantidad.put(idActividad, atrasoTotalBrutoCantidad.get(idActividad).subtract(atrasosCantidades.get(idActividad)));
    }

    // Hasta aqui se han calculado los valores para acumular tanto para los periodos como para las actividades en sus cantidades y valores.
    // También se indentifió en qué periodo se debe acumular. También se han restado los valores de atraso a los periodos durante los cuales
    // la obra estuvo suspendida. Lo que falta es guardar estos datos en BD para todas las entidades.

    itperiodo = getAdministrarObraNew().getObra().getPeriodos().iterator();
    while (itperiodo.hasNext()) {
    Periodo per = (Periodo) itperiodo.next();
    idPeriodo = per.getIntidperiodo();

    atrasosValores = atrasosValoresActividades.get(idPeriodo);
    atrasosCantidades = atrasosCantidadesActividades.get(idPeriodo);

    itrelper = per.getRelacionactividadobraperiodos().iterator();

    if(idPeriodo == periodoReinicio) {
    per.setNumvaltotplanif(new BigDecimal(per.getNumvaltotplanif().doubleValue() + atrasadoTotalNeto.doubleValue()));
    while (itrelper.hasNext()) {
    Relacionactividadobraperiodo rela = (Relacionactividadobraperiodo) itrelper.next();
    idActividad = rela.getActividadobra().getOidactiviobra();

    rela.setFloatcantplanif(new BigDecimal(rela.getFloatcantplanif() + atrasoTotalNetoValor.get(idActividad).doubleValue()).doubleValue());
    rela.setNumvalplanif(new BigDecimal(rela.getNumvalplanif().doubleValue() + atrasoTotalNetoCantidad.get(idActividad).doubleValue()));
    }
    } else {
    per.setNumvaltotplanif(per.getNumvaltotplanif().subtract(atrasosPeriodos.get(idPeriodo)));
    while (itrelper.hasNext()) {
    Relacionactividadobraperiodo rela = (Relacionactividadobraperiodo) itrelper.next();
    idActividad = rela.getActividadobra().getOidactiviobra();

    rela.setFloatcantplanif(new BigDecimal(rela.getFloatcantplanif()).subtract(atrasosValores.get(idActividad)).doubleValue());
    rela.setNumvalplanif(rela.getNumvalplanif().subtract(atrasosCantidades.get(idActividad)));
    }
    }
    }
    }
     */
}
