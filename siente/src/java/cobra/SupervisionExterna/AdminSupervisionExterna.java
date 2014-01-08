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
import co.com.interkont.cobra.to.Typeresultadovalidacion;
import co.com.interkont.cobra.to.Visita;
import co.com.interkont.cobra.to.utilidades.Propiedad;
import cobra.SessionBeanCobra;
import cobra.SolicitudObra.ValidadorSeguimiento;
import cobra.CargadorArchivosWeb;
import cobra.Cobra.Download;
import cobra.Supervisor.FacesUtils;
import cobra.util.ArchivoWebUtil;
import cobra.util.RutasWebArchivos;
import com.interkont.cobra.exception.ArchivoExistenteException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import org.richfaces.component.UIDataTable;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import supervision.service.SupervisionExternaServiceAble;

/**
 *
 * @author desarrollo1
 */
public class AdminSupervisionExterna implements Serializable{

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
    
    /**
     * Referencia a la tabla de visitas fallidas
     */
    private UIDataTable tablavisitasfallidas = new UIDataTable();

    public UIDataTable getTablavisitasfallidas() {
        return tablavisitasfallidas;
    }

    public void setTablavisitasfallidas(UIDataTable tablavisitasfallidas) {
        this.tablavisitasfallidas = tablavisitasfallidas;
    }
    
    /**
     * Listado de visitas fallidas para el usuario actual
     */
    private List<Visita> listavisitasfallidas;

    public List<Visita> getListavisitasfallidas() {
        return listavisitasfallidas;
    }

    public void setListavisitasfallidas(List<Visita> listavisitasfallidas) {
        this.listavisitasfallidas = listavisitasfallidas;
    }
    
    /**
     * Lista para almacenar los resultados de la validación de la matriz de 
     * auditoría
     */
    private List<Typeresultadovalidacion> listaresultadosvalidacion;

    public List<Typeresultadovalidacion> getListaresultadosvalidacion() {
        return listaresultadosvalidacion;
    }

    public void setListaresultadosvalidacion(List<Typeresultadovalidacion> listaresultadosvalidacion) {
        this.listaresultadosvalidacion = listaresultadosvalidacion;
    }

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
        cargarVisitasAuditoriaFallidasUsuario();
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

    public String subirListadoSeg() throws ArchivoExistenteException, FileNotFoundException, IOException, InvalidFormatException {
        String carpetaDoc = MessageFormat.format(RutasWebArchivos.DOC_MATRIZ, "" + +getSessionBeanCobra().getSupervisionExternaService().getVisita().getOidvisita() + "/");
        subirListado.getArchivoWeb().cambiarNombre(null, true);
        subirListado.guardarArchivosTemporales(carpetaDoc, false);
        getSessionBeanCobra().getSupervisionExternaService().getVisita().setStrurlinforme(subirListado.getArchivos().get(0).getRutaWeb());
        getValidadorSeguimiento().validarCronogramaSeguimiento(getSessionBeanCobra().getSupervisionExternaService().getVisita().getStrurlinforme());
        return null;
    }
    
    /**
     * Método encargado de cargar la la matriz de auditoría para la fecha 
     * proporcionada y la ruta de archivo correspondiente, el cual debe esta 
     * previamente cargado
     */
    public void validarMatrizAuditoria() {
        listaresultadosvalidacion = new ArrayList<Typeresultadovalidacion>();
        try {
            subirListado.getArchivoWeb().convertirUTF8();
            LineIterator lineIterator = FileUtils.lineIterator(subirListado.getArchivoWeb().getArchivoTmp());
            int fila = 1;
            int numeroColumnasEsperado = Integer.valueOf(Propiedad.getValor("numerocolumnasmatrizauditoria"));
            while (lineIterator.hasNext()) {
                String linea = lineIterator.nextLine();
                int numeroColumnas = linea.split("\\t").length;
                if (numeroColumnas != numeroColumnasEsperado) {
                    FacesUtils.addErrorMessage(Propiedad.getValor("numerocolumnasmatrizauditoriaerror", fila, numeroColumnas, numeroColumnasEsperado));
                    return;
                }
                fila++;
            }
            subirListado.getArchivoWeb().cambiarNombre(null, true);
            subirListado.guardarArchivosTemporales(RutasWebArchivos.MATRIZ_AUDITORIA, true);
            getSessionBeanCobra().getSupervisionExternaService().getVisita().setDatefecharegistro(new Date());
            getSessionBeanCobra().getSupervisionExternaService().getVisita().setStrurlinforme(ArchivoWebUtil.obtenerRutaAbsoluta(subirListado.getArchivos().get(0).getRutaWeb()));
            getSessionBeanCobra().getSupervisionExternaService().getVisita().setJsfUsuarioVisita(getSessionBeanCobra().getUsuarioObra());
            getSessionBeanCobra().getSupervisionExternaService().getVisita().setStrdescvisita("");
            listaresultadosvalidacion = getSessionBeanCobra().getCobraService().validarMatrizAuditoria(getSessionBeanCobra().getSupervisionExternaService().getVisita());
            if (listaresultadosvalidacion.isEmpty()) {
                FacesUtils.addInfoMessage(Propiedad.getValor("resultadosvalidacionok"));
                limpiarVisita();
            } else {
                FacesUtils.addErrorMessage(Propiedad.getValor("resultadosvalidacionfallo"));
                limpiarArchivoVisita();
            }
            cargarVisitasAuditoriaFallidasUsuario();
        } catch (ArchivoExistenteException ex) {
            Logger.getLogger(AdminSupervisionExterna.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            FacesUtils.addErrorMessage("No fue posible convertir a UTF8");
        }
    }
    
    /**
     * Consulta la visitas de auditoría fallidas, correspondientes al usuario actual del
     * sistema
     */
    public void cargarVisitasAuditoriaFallidasUsuario() {
        listavisitasfallidas = getSessionBeanCobra().getCobraService().encontrarVisitasAuditoriaUsuario(null, Visita.ESTADO_FALLO);
    }
    
    /**
     * Carga la información de la visita fallida seleccionada para que esta sea
     * ingresada nuevamente mediante el archivo de correspondiente
     */
    public void cargarVisitaFallida() {
        Visita visita = (Visita) tablavisitasfallidas.getRowData();
        getSessionBeanCobra().getSupervisionExternaService().setVisita(visita);
        subirListado.borrarDatosSubidos();
    }
    
    /**
     * Descarga el reporte de errores de visita fallida
     */
    public void generarReporteErroresVisita() {
        Visita visita = (Visita) tablavisitasfallidas.getRowData();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(Propiedad.getValor("reporteresultadosvalidacion", Propiedad.getValor("nombrebd") ,visita.getOidvisita()));

        } catch (IOException ex) {
            Logger.getLogger(AdminSupervisionExterna.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Limpia la información de la visita a ser cargada
     */
    public void limpiarVisita() {
        getSessionBeanCobra().getSupervisionExternaService().setVisita(new Visita());
        subirListado.borrarDatosSubidos();
    }
    
    /**
     * Limpia la información del archivo de la visita a ser cargada
     */
    public void limpiarArchivoVisita() {
        subirListado.borrarDatosSubidos();
    }
    
    /**
     * Obtiene una referencia del bean utilizado para descargar arcivos
     * @return 
     */
    public Download getDownload() {
        return (Download) FacesUtils.getManagedBean("Cobra$Download");
    }
    
    /**
     * Método utilizado para descargar la plantilla necesaria para realizar el 
     * cargue del módulo de auditoría
     * @return null
     */
    public void descargarPlantillaAuditoria() {
        getSessionBeanCobra().setUrlAbri(RutasWebArchivos.PLANTILLA_MATRIZ_AUDITORIA);
        getDownload().onDownload();
    }
}
