/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.SupervisionExterna;

import co.com.interkont.cobra.to.Actividadseguimiento;
import co.com.interkont.cobra.to.Polizaseguimiento;
import co.com.interkont.cobra.to.Seguimiento;
import co.com.interkont.cobra.to.Tipodesarrollo;
import co.com.interkont.cobra.to.Tipoobra;
import co.com.interkont.cobra.to.Visita;
import cobra.Archivo;
import cobra.ArchivoWeb;

import cobra.SessionBeanCobra;
import cobra.SolicitudObra.ValidadorSeguimiento;
import cobra.CargadorArchivosWeb;
import cobra.Supervisor.FacesUtils;
import com.interkont.cobra.exception.ArchivoExistenteException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import org.richfaces.component.UIDataTable;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import supervision.service.SupervisionExternaServiceAble;

/**
 *
 * @author desarrollo1
 */
public class AdminSupervisionExterna {

    private SelectItem[] visitaSelec;
    private UIDataTable tablaSeguimientos = new UIDataTable();
    private List<Seguimiento> listadoSeguimientos = new ArrayList<Seguimiento>();
    private List<Visita> listadoVisitas = new ArrayList<Visita>();
    private List<Actividadseguimiento> listadoActividadesSeguimientos = new ArrayList<Actividadseguimiento>();
    private Seguimiento objetoSeguimiento = new Seguimiento();
    private Polizaseguimiento objetoPolizaSeguimiento = new Polizaseguimiento();
    private Actividadseguimiento objetoActividadSeguimiento = new Actividadseguimiento();
    private Tipodesarrollo objetoTipoDesarrollo = new Tipodesarrollo();
    private Tipoobra objetoTipoObra = new Tipoobra();
    private double sumavlrtotalact = 0.0;
    private int cantidadRegistros = 0;
    private int totalSolicituesApro = 0;
    private double valorTotalAct = 0.0;
    private BigDecimal sumacantejecutada = BigDecimal.ZERO;
    private long codigoVisita = 0;
    private String tieneReuniones = "";
    private String remiteInformes = "";
    private String cumpleAmbiental = "";
    private String permisosLicencias = "";
    private String permisoAmbiental = "";
    private String relacionInterventoria = "";
    private String porcentajeMenorIgual = "";
    private String relacioncasualInforme = "";
    private String planosobra = "";
    private String disenios = "";
    private String especifTecnica = "";
    private String obradearte = "";
    private String mensaje;
    private ResourceBundle bundle = getSessionBeanCobra().getBundle();
    private CargadorArchivosWeb subirListado = new CargadorArchivosWeb();
    private String urlCronograma = "";
    private UIDataTable tablaItemSeguimientoSeleccionado = new UIDataTable();
    public Seguimiento seguimientoSelec = new Seguimiento();
    public int varmostrarBotonexcelMatris = 0;

    public int getVarmostrarBotonexcelMatris() {
        return varmostrarBotonexcelMatris;
    }

    public void setVarmostrarBotonexcelMatris(int varmostrarBotonexcelMatris) {
        this.varmostrarBotonexcelMatris = varmostrarBotonexcelMatris;
    }

    public double getValorTotalAct() {
        return valorTotalAct;
    }

    public void setValorTotalAct(double valorTotalAct) {
        this.valorTotalAct = valorTotalAct;
    }

    public String getObradearte() {
        return obradearte;
    }

    public void setObradearte(String obradearte) {
        this.obradearte = obradearte;
    }

    public Tipoobra getObjetoTipoObra() {
        return objetoTipoObra;
    }

    public void setObjetoTipoObra(Tipoobra objetoTipoObra) {
        this.objetoTipoObra = objetoTipoObra;
    }

    public String getEspecifTecnica() {
        return especifTecnica;
    }

    public void setEspecifTecnica(String especifTecnica) {
        this.especifTecnica = especifTecnica;
    }

    public String getPlanosobra() {
        return planosobra;
    }

    public void setPlanosobra(String planosobra) {
        this.planosobra = planosobra;
    }

    public String getDisenios() {
        return disenios;
    }

    public void setDisenios(String disenios) {
        this.disenios = disenios;
    }

    public String getRelacioncasualInforme() {
        return relacioncasualInforme;
    }

    public void setRelacioncasualInforme(String relacioncasualInforme) {
        this.relacioncasualInforme = relacioncasualInforme;
    }

    public int getTotalSolicituesApro() {
        return totalSolicituesApro;
    }

    public void setTotalSolicituesApro(int totalSolicituesApro) {
        this.totalSolicituesApro = totalSolicituesApro;
    }

    public Seguimiento getSeguimientoSelec() {
        return seguimientoSelec;
    }

    public void setSeguimientoSelec(Seguimiento seguimientoSelec) {
        this.seguimientoSelec = seguimientoSelec;
    }

    public UIDataTable getTablaItemSeguimientoSeleccionado() {
        return tablaItemSeguimientoSeleccionado;
    }

    public void setTablaItemSeguimientoSeleccionado(UIDataTable tablaItemSeguimientoSeleccionado) {
        this.tablaItemSeguimientoSeleccionado = tablaItemSeguimientoSeleccionado;
    }

    public String getUrlCronograma() {
        return urlCronograma;
    }

    public void setUrlCronograma(String urlCronograma) {
        this.urlCronograma = urlCronograma;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public CargadorArchivosWeb getSubirListado() {
        return subirListado;
    }

    public void setSubirListado(CargadorArchivosWeb subirListado) {
        this.subirListado = subirListado;
    }

    public String getPorcentajeMenorIgual() {
        return porcentajeMenorIgual;
    }

    public void setPorcentajeMenorIgual(String porcentajeMenorIgual) {
        this.porcentajeMenorIgual = porcentajeMenorIgual;
    }

    public String getRelacionInterventoria() {
        return relacionInterventoria;
    }

    public void setRelacionInterventoria(String relacionInterventoria) {
        this.relacionInterventoria = relacionInterventoria;
    }

    public String getPermisoAmbiental() {
        return permisoAmbiental;
    }

    public void setPermisoAmbiental(String permisoAmbiental) {
        this.permisoAmbiental = permisoAmbiental;
    }

    public String getPermisosLicencias() {
        return permisosLicencias;
    }

    public void setPermisosLicencias(String permisosLicencias) {
        this.permisosLicencias = permisosLicencias;
    }

    public String getCumpleAmbiental() {
        return cumpleAmbiental;
    }

    public void setCumpleAmbiental(String cumpleAmbiental) {
        this.cumpleAmbiental = cumpleAmbiental;
    }

    public String getRemiteInformes() {
        return remiteInformes;
    }

    public void setRemiteInformes(String remiteInformes) {
        this.remiteInformes = remiteInformes;
    }

    public String getTieneReuniones() {
        return tieneReuniones;
    }

    public void setTieneReuniones(String tieneReuniones) {
        this.tieneReuniones = tieneReuniones;
    }

    public long getCodigoVisita() {
        return codigoVisita;
    }

    public void setCodigoVisita(long codigoVisita) {
        this.codigoVisita = codigoVisita;
    }

    public SelectItem[] getVisitaSelec() {
        return visitaSelec;
    }

    public void setVisitaSelec(SelectItem[] visitaSelec) {
        this.visitaSelec = visitaSelec;
    }

    public BigDecimal getSumacantejecutada() {
        return sumacantejecutada;
    }

    public void setSumacantejecutada(BigDecimal sumacantejecutada) {
        this.sumacantejecutada = sumacantejecutada;
    }

    public double getSumavlrtotalact() {
        return sumavlrtotalact;
    }

    public void setSumavlrtotalact(double sumavlrtotalact) {
        this.sumavlrtotalact = sumavlrtotalact;
    }

    public List<Actividadseguimiento> getListadoActividadesSeguimientos() {
        return listadoActividadesSeguimientos;
    }

    public void setListadoActividadesSeguimientos(List<Actividadseguimiento> listadoActividadesSeguimientos) {
        this.listadoActividadesSeguimientos = listadoActividadesSeguimientos;
    }

    public Actividadseguimiento getObjetoActividadSeguimiento() {
        return objetoActividadSeguimiento;
    }

    public void setObjetoActividadSeguimiento(Actividadseguimiento objetoActividadSeguimiento) {
        this.objetoActividadSeguimiento = objetoActividadSeguimiento;
    }

    public Polizaseguimiento getObjetoPolizaSeguimiento() {
        return objetoPolizaSeguimiento;
    }

    public void setObjetoPolizaSeguimiento(Polizaseguimiento objetoPolizaSeguimiento) {
        this.objetoPolizaSeguimiento = objetoPolizaSeguimiento;
    }

    public Seguimiento getObjetoSeguimiento() {
        return objetoSeguimiento;
    }

    public void setObjetoSeguimiento(Seguimiento objetoSeguimiento) {
        this.objetoSeguimiento = objetoSeguimiento;
    }

    public Tipodesarrollo getObjetoTipoDesarrollo() {
        return objetoTipoDesarrollo;
    }

    public void setObjetoTipoDesarrollo(Tipodesarrollo objetoTipoDesarrollo) {
        this.objetoTipoDesarrollo = objetoTipoDesarrollo;
    }

    public List<Visita> getListadoVisitas() {
        return listadoVisitas;
    }

    public void setListadoVisitas(List<Visita> listadoVisitas) {
        this.listadoVisitas = listadoVisitas;
    }

    public List<Seguimiento> getListadoSeguimientos() {
        return listadoSeguimientos;
    }

    public void setListadoSeguimientos(List<Seguimiento> listadoSeguimientos) {
        this.listadoSeguimientos = listadoSeguimientos;
    }

    public int getCantidadRegistros() {
        return cantidadRegistros;
    }

    public void setCantidadRegistros(int cantidadRegistros) {
        this.cantidadRegistros = cantidadRegistros;
    }

    public UIDataTable getTablaSeguimientos() {
        return tablaSeguimientos;
    }

    public void setTablaSeguimientos(UIDataTable tablaSeguimientos) {
        this.tablaSeguimientos = tablaSeguimientos;
    }

    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }

    protected ValidadorSeguimiento getValidadorSeguimiento() {
        return (ValidadorSeguimiento) FacesUtils.getManagedBean("SolicitudObra$Validador");
    }

    public SupervisionExternaServiceAble getSupervisionExterna() {
        return getSessionBeanCobra().getSupervisionExternaService();
    }

    /**
     * Creates a new instance of AdminSupervisionExterna
     */
    public AdminSupervisionExterna() throws IOException {
        registrarMatriz();
//        llenarVisitas();
//        llenarlistadoSeguimientos();
    }

    public String volverDetalle() {
        return "volverdetalle";
    }

//    public String llenarVisitas() {
//        try {
//            getSessionBeanCobra().getSupervisionExternaService().setVisitas(getSessionBeanCobra().getSupervisionExternaService().encontrarVisitas());
//            visitaSelec = new SelectItem[getSessionBeanCobra().getSupervisionExternaService().getVisitas().size()];
//            int i = 0;
//            visitaSelec[0] = new SelectItem(0, bundle.getString("dobleraya"));
//            for (Visita visita : getSessionBeanCobra().getSupervisionExternaService().getVisitas()) {
//                SelectItem vis = new SelectItem(visita.getOidvisita(), visita.getStrdescvisita());
//                if (i == 0) {
//                    codigoVisita = visita.getOidvisita();
//                    //getSessionBeanCobra().getSupervisionExternaService().getVisita().setOidvisita(visita.getOidvisita());
//                }
//                visitaSelec[i++] = vis;
//            }
//        } catch (Exception e) {
//        }
//        return null;
//    }
//
//    public void cambioVisita() {
//        llenarVisitas();
//        mostrarInformacionGeneral();
//    }
//
//    public void llenarlistadoSeguimientos() {
//        setListadoSeguimientos(getSessionBeanCobra().getSupervisionExternaService().obtenerTotalSeguimientos());
//        cantidadRegistros = getListadoSeguimientos().size();
//        totalSolicituesApro = getSessionBeanCobra().getSolicitudService().totalaprobadas();
//    }
//
    public String mostrarInformacionGeneral() {
        setObjetoActividadSeguimiento(getSessionBeanCobra().getSupervisionExternaService().obtenerActividadSeguimientoPorId(getObjetoSeguimiento().getIntidseguimiento()));
        setObjetoTipoDesarrollo(getSessionBeanCobra().getSupervisionExternaService().obtenerTipoDesarrolloPorId(getObjetoSeguimiento().getIntidseguimiento()));

        return null;
    }

    public String mostrarDatosGenerales() {
        mostrarBooleanos();
        return null;
    }

    public String mostrarPolizaSeguimiento() {
        setObjetoPolizaSeguimiento(getSessionBeanCobra().getSupervisionExternaService().obtenerPolizaSeguimientoPorId(getObjetoSeguimiento().getIntidseguimiento()));
        return null;
    }

    public String llenarlistadoActividadesSeguimientos() {
        setListadoActividadesSeguimientos(getSessionBeanCobra().getSupervisionExternaService().obtenerListadoActividadesPorSeguimiento(getObjetoSeguimiento().getIntidseguimiento()));
        for (Iterator actSeguimiento = getListadoActividadesSeguimientos().listIterator(); actSeguimiento.hasNext();) {
            Actividadseguimiento act = (Actividadseguimiento) actSeguimiento.next();
            sumavlrtotalact = sumavlrtotalact + act.getValortotalactividad();
            sumacantejecutada = sumacantejecutada.add(act.getNumvlrtotalejec());
        }
        return null;
    }

    public double getPorcentajeActividad() throws ArithmeticException {
        return (sumacantejecutada.doubleValue() * 100) / sumavlrtotalact;
    }

    public double getPorcentajeInterventoria() throws ArithmeticException {
        return 1;
    }

    public void mostrarBooleanos() {
        if (getObjetoSeguimiento().getBoolreuniones().equals(true)) {
            tieneReuniones = "SI";
        } else {
            tieneReuniones = "NO";
        }
        if (getObjetoSeguimiento().getBoolremiteinformes().equals(true)) {
            remiteInformes = "SI";
        } else {
            remiteInformes = "NO";
        }
        if (getObjetoSeguimiento().getBoolcumpleambiental().equals(true)) {
            cumpleAmbiental = "SI";
        } else {
            cumpleAmbiental = "NO";
        }
        if (getObjetoSeguimiento().getBoolpermisosylicencias().equals(true)) {
            permisosLicencias = "SI";
        } else {
            permisosLicencias = "NO";
        }
        if (getObjetoSeguimiento().getBoolpermisoambiental().equals(true)) {
            permisoAmbiental = "SI";
        } else {
            permisoAmbiental = "NO";
        }
        if (getObjetoSeguimiento().getBoolrelacionnina().equals(true)) {
            relacionInterventoria = "SI";
        } else {
            relacionInterventoria = "NO";
        }
        if (getObjetoSeguimiento().getBoolcumpleporcenvalrmenoroigual().equals(true)) {
            porcentajeMenorIgual = "SI";
        } else {
            porcentajeMenorIgual = "NO";
        }
        if (getObjetoSeguimiento().getBoolrelacioncasualifenome().equals(true)) {
            relacioncasualInforme = "SI";
        } else {
            relacioncasualInforme = "NO";
        }
        if (getObjetoSeguimiento().getBooldisenos().equals(true)) {
            disenios = "SI";
        } else {
            disenios = "NO";
        }
        if (getObjetoSeguimiento().getBoolplanosobra().equals(true)) {
            planosobra = "SI";
        } else {
            planosobra = "NO";
        }
        if (getObjetoSeguimiento().getBoolespecificacionestecni().equals(true)) {
            especifTecnica = "SI";
        } else {
            especifTecnica = "NO";
        }
        if (getObjetoSeguimiento().getTipoobra().getInttipoobra() == 7) {
            obradearte = "SI";
        } else {
            obradearte = "NO";
        }

    }

    public String registrarMatriz() {

        return "registrarMatriz";
    }

    public String verRegistros() {

        return "proyectosSupervision";
    }

    public String cambioListado() {
        this.subirListado.borrarDatosSubidos();
        ServletContext contextoCobra = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        getValidadorSeguimiento().limpiarValidador();
        varmostrarBotonexcelMatris = 0;

        if (getSessionBeanCobra().getSupervisionExternaService().getVisita().getStrurlinforme() != null) {
            File archi = new File(contextoCobra.getRealPath(getSessionBeanCobra().getSupervisionExternaService().getVisita().getStrurlinforme()));
            if (archi.exists()) {
                archi.delete();
            }
        }
        return null;
    }

    public String guardarVisita() throws FileNotFoundException, IOException, InvalidFormatException {
        // subirListado();

        if (getSessionBeanCobra().getSupervisionExternaService().getVisita().getStrnombreinforme() != null && getSessionBeanCobra().getSupervisionExternaService().getVisita().getDatefechainforme() != null) {
            // getSessionBeanCobra().getSupervisionExternaService().getVisita().setDatefecharegistro(new Date());
            // getSessionBeanCobra().getSupervisionExternaService().getVisita().setJsfUsuarioVisita(getSessionBeanCobra().getUsuarioObra());
            // getSessionBeanCobra().getSupervisionExternaService().guardarRegMatrizVisitas(getSessionBeanCobra().getSupervisionExternaService().getVisita());
            varmostrarBotonexcelMatris = 1;
        } else {
            FacesUtils.addErrorMessage("Campos Requeridos");
        }
        return null;
    }

    public String subirListadoSeg() throws FileNotFoundException, IOException, InvalidFormatException {
        String pathDoc = "";
        String realArchivoPath = "";
        String URL = "/resources/Documentos/ValidadorE/" + getSessionBeanCobra().getSupervisionExternaService().getVisita().getOidvisita() + "/";
        ServletContext theApplicationsServletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        if (subirListado.getArchivos().size() > 0) {
            realArchivoPath = theApplicationsServletContext.getRealPath(URL);
            try {
                File carpeta = new File(realArchivoPath);
                if (!carpeta.exists()) {
                    carpeta.mkdirs();
                }
            } catch (Exception ex) {
                mensaje = (bundle.getString("nosepuedesubir"));//"Cannot upload file ");
            }
            try {
                subirListado.guardarArchivosTemporales(realArchivoPath, false);
            } catch (ArchivoExistenteException ex) {
                Logger.getLogger(AdminSupervisionExterna.class.getName()).log(Level.SEVERE, null, ex);
            }
            Iterator arch = subirListado.getArchivos().iterator();
            while (arch.hasNext()) {
                ArchivoWeb nombreoriginal = (ArchivoWeb) arch.next();
                pathDoc = nombreoriginal.getNombre();
            }
        } else {
            if (pathDoc.compareTo("") != 0) {
                realArchivoPath = theApplicationsServletContext.getRealPath(pathDoc);
                File archi = new File(realArchivoPath);

                if (archi.exists()) {
                    archi.delete();

                }
                pathDoc = "";
            }

        }
        urlCronograma = pathDoc;
        // String url = getSessionBeanCobra().getSupervisionExternaService().getVisita().getStrurlinforme();
        getSessionBeanCobra().getSupervisionExternaService().getVisita().setStrurlinforme(URL + urlCronograma);
        //   guardarVisita();
        getValidadorSeguimiento().validarCronogramaSeguimiento(getSessionBeanCobra().getSupervisionExternaService().getVisita().getStrurlinforme());

        return null;
    }
}
