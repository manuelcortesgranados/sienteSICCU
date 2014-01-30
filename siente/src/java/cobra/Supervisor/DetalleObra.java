/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.Supervisor;

import co.com.interkont.cobra.to.Actividadobra;
import co.com.interkont.cobra.to.Alimentacion;
import co.com.interkont.cobra.to.Alimentacioncualificacion;
import co.com.interkont.cobra.to.Beneficiario;
import co.com.interkont.cobra.to.Calificacionauditoriaobra;
import co.com.interkont.cobra.to.Contratista;
import co.com.interkont.cobra.to.Documentoobra;
import co.com.interkont.cobra.to.Factoratraso;
import co.com.interkont.cobra.to.Historicoobra;
import co.com.interkont.cobra.to.Imagenevolucionobra;
import co.com.interkont.cobra.to.Listaverificacion;
import co.com.interkont.cobra.to.Movimiento;
import co.com.interkont.cobra.to.Obra;
import co.com.interkont.cobra.to.Periodo;
import co.com.interkont.cobra.to.Periodohisto;
import cobra.GestionarXML;
import cobra.SessionBeanCobra;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import org.richfaces.component.UIDataTable;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import co.com.interkont.cobra.to.Relacionalimentacionactividad;
import co.com.interkont.cobra.to.Relacioncontratoobra;
import co.com.interkont.cobra.to.Relacionobraseguidor;
import co.com.interkont.cobra.to.Seguimiento;
import co.com.interkont.cobra.to.Tipoimpactosocial;
import co.com.interkont.cobra.to.Tipoinforme;
import co.com.interkont.cobra.to.Validacionalimentacion;
import co.com.interkont.cobra.to.Visita;
import co.com.interkont.cobra.to.utilidades.Propiedad;
import co.com.interkont.cobra.vista.VistaObraMapa;
import cobra.SupervisionExterna.AdminSupervisionExterna;
import java.io.Serializable;
import java.math.RoundingMode;
import javax.faces.context.FacesContext;

/**
 * <p>Fragment bean that corresponds to a similarly named JSP page fragment.
 * This class contains component definitions (and initialization code) for all
 * components that you have defined on this fragment, as well as lifecycle
 * methods and event handlers where you may add behavior to respond to incoming
 * events.</p>
 *
 * @version DetalleObra.java
 * @version Created on 13-oct-2010, 13:53:10
 * @author carlosalbertoloaizaguerrero
 */
public class DetalleObra implements Serializable {
    // <editor-fold defaultstate="collapsed" desc="Managed Component Definition">

    private String strimagenobraproyectada = "";
    private boolean verPlazo = false;
    private boolean verPresu = false;
    private Date periodoultimo = new Date();
    private Historicoobra primerhisto = new Historicoobra();
    private int plazoinicial = 0;
    private Alimentacion alimentacionultima = new Alimentacion();
    private BigDecimal promedioempleos;
    private int numeroalimentacion = 1;
    private List<Alimentacion> listaAlimenta = new ArrayList<Alimentacion>();
    private Alimentacion alimentacionmostrar = new Alimentacion();
    private List<Factoratraso> listaFactoresAlimenta = new ArrayList<Factoratraso>();
    private List<Documentoobra> listaDocsAlimentacion;
    private UIDataTable tablaDocsAlimentacion = new UIDataTable();
    private UIDataTable tablaSeguimientos = new UIDataTable();
    private UIDataTable tablaContatoInter = new UIDataTable();
    private UIDataTable tablalogrosalimentacion = new UIDataTable();
    private UIDataTable tabladificultadalimentacion = new UIDataTable();
    private UIDataTable tablafactoratraso = new UIDataTable();
    private UIDataTable tablamovimientos = new UIDataTable();
    private GestionarXML datosgrafico = new GestionarXML();
    private BigDecimal[] totales;
    private String datos_grafico;
    private static final String URL = "/amline/";
    private int totalalim = 0;
    private ResourceBundle bundle = getSessionBeanCobra().getBundle();
    private List<Contratista> contrat = new ArrayList<Contratista>();
    private List<Alimentacioncualificacion> listaAvance = new ArrayList<Alimentacioncualificacion>();
    private boolean stpestado = false;
    //private Obra obra = new Obra();
    //private Alimentacion alimentacion = new Alimentacion();
    private String strimagenAnterior = "";
    private String strimagenActual = "";
    private String strimagenFutura = "";
    private SelectItem[] selectItemPeriodoCorte;
    private SelectItem[] periodoaliment;
    private SelectItem[] tipoinforme;
    private List<Periodo> listaperiodo;
    private Periodo periodoevo = new Periodo();
    public BigDecimal bdproyectadoPeriodo;
    public BigDecimal bdejecutadoAlimentacion;
    public String mensageFechaSeleccio = "";
    public int mostrarPeriodo = 0;
    private String logro = "";
    private String dificulta = "";
    private List<Relacionalimentacionactividad> lisrelalimacti = new ArrayList<Relacionalimentacionactividad>();
    private String finentrega;
    private boolean empleosgenerados = false;
    private String semaforo = "";
    private int totalfilas = 0;
    private int pagina = 0;
    private int totalpaginas = 0;
    private boolean verultimos;
    private boolean veranterior;
    private String contratistanombrecomple = "";
    private String idcontratos = "";
    private boolean boolimg = true;
    public List<Imagenevolucionobra> listaImagenesevolucionobra;
    public List<Movimiento> listadomovimientos;
    public BigDecimal valorinterventoria = BigDecimal.ZERO;
    private List<Listaverificacion> listaverificacion = new ArrayList<Listaverificacion>();
    private boolean veralimenta = false;
    private int listasegudires;
    private List<Beneficiario> lstbeneficiario;
    private int totalben = 0;
    private List<Actividadobra> listactividades = new ArrayList<Actividadobra>();
    private BigDecimal totalprogramado;
    private BigDecimal totalejecutado;
    private boolean permitirvalidar = true;
    private List<Tipoimpactosocial> listimpactosocialdetalle;
    private String strbeneficiariosdetalle;
    private String strempleosdirectosdetalle;
    private String strempleosindirectosdetalle;
    
    /**
     * Lista de proyectos hijos de este proyecto cuando se trata de un proyecto
     * padre
     */
    private List<Obra> listaSubProyectos = new ArrayList<Obra>();
    /**
     * Objeto que contiene los datos básicos de una obra
     */
    private VistaObraMapa obraMapa;
    /**
     * Si true se renderiza la modal modalImagenvermas de lo contrario no
     */
    private boolean verModalImagenvermas;
    private String strlocalidadlogitud;
    private String strlocalidadlatitud;

    public String getStrlocalidadlogitud() {
        return strlocalidadlogitud;
    }

    public void setStrlocalidadlogitud(String strlocalidadlogitud) {
        this.strlocalidadlogitud = strlocalidadlogitud;
    }

    public String getStrlocalidadlatitud() {
        return strlocalidadlatitud;
    }

    public void setStrlocalidadlatitud(String strlocalidadlatitud) {
        this.strlocalidadlatitud = strlocalidadlatitud;
    }

    public boolean isVerModalImagenvermas() {
        return verModalImagenvermas;
    }

    public void setVerModalImagenvermas(boolean verModalImagenvermas) {
        this.verModalImagenvermas = verModalImagenvermas;
    }

    public VistaObraMapa getObraMapa() {
        return obraMapa;
    }

    public void setObraMapa(VistaObraMapa obraMapa) {
        this.obraMapa = obraMapa;
    }

    public List<Obra> getListaSubProyectos() {
        return listaSubProyectos;
    }

    public void setListaSubProyectos(List<Obra> listaProyectosHijos) {
        this.listaSubProyectos = listaProyectosHijos;
    }

    /**
     * Obtiene el número de proyectos hijos de este proyecto
     *
     * @return
     */
    public int getNumSubproyectos() {
        if (!listaSubProyectos.isEmpty()) {
            return listaSubProyectos.size();
        } else {
            return 0;
        }
    }

    public boolean isPermitirvalidar() {
        return permitirvalidar;
    }

    public List<Tipoimpactosocial> getListimpactosocialdetalle() {
        return listimpactosocialdetalle;
    }

    public void setListimpactosocialdetalle(List<Tipoimpactosocial> listimpactosocialdetalle) {
        this.listimpactosocialdetalle = listimpactosocialdetalle;
    }

    public String getStrbeneficiariosdetalle() {
        return strbeneficiariosdetalle;
    }

    public void setStrbeneficiariosdetalle(String strbeneficiariosdetalle) {
        this.strbeneficiariosdetalle = strbeneficiariosdetalle;
    }

    public String getStrempleosdirectosdetalle() {
        return strempleosdirectosdetalle;
    }

    public void setStrempleosdirectosdetalle(String strempleosdirectosdetalle) {
        this.strempleosdirectosdetalle = strempleosdirectosdetalle;
    }

    public String getStrempleosindirectosdetalle() {
        return strempleosindirectosdetalle;
    }

    public void setStrempleosindirectosdetalle(String strempleosindirectosdetalle) {
        this.strempleosindirectosdetalle = strempleosindirectosdetalle;
    }   
   
    public void setPermitirvalidar(boolean permitirvalidar) {
        this.permitirvalidar = permitirvalidar;
    }
    private boolean seguidorporobra = false;
    private Relacionobraseguidor seguirobra;

    public Relacionobraseguidor getSeguirobra() {
        return seguirobra;
    }

    public void setSeguirobra(Relacionobraseguidor seguirobra) {
        this.seguirobra = seguirobra;
    }

    public boolean isSeguidorporobra() {
        return seguidorporobra;
    }

    public void setSeguidorporobra(boolean seguidorporobra) {
        this.seguidorporobra = seguidorporobra;
    }

    public BigDecimal getTotalejecutado() {
        return totalejecutado;
    }

    public void setTotalejecutado(BigDecimal totalejecutado) {
        this.totalejecutado = totalejecutado;
    }

    public BigDecimal getTotalprogramado() {
        return totalprogramado;
    }

    public void setTotalprogramado(BigDecimal totalprogramado) {
        this.totalprogramado = totalprogramado;
    }

    public List<Actividadobra> getListactividades() {
        return listactividades;
    }

    public void setListactividades(List<Actividadobra> listactividades) {
        this.listactividades = listactividades;
    }

    public int getTotalben() {
        return totalben;
    }

    public void setTotalben(int totalben) {
        this.totalben = totalben;
    }

    public int getListasegudires() {
        return listasegudires;
    }

    public void setListasegudires(int listasegudires) {
        this.listasegudires = listasegudires;
    }

    public boolean isVeralimenta() {
        return veralimenta;
    }

    public void setVeralimenta(boolean veralimenta) {
        this.veralimenta = veralimenta;
    }

    public void setListaverificacion(List<Listaverificacion> listaverificacion) {
        this.listaverificacion = listaverificacion;
    }

    public List<Listaverificacion> getListaverificacion() {
        return listaverificacion;
    }
    public Validacionalimentacion valialiment = new Validacionalimentacion();

    public Date getPeriodoultimo() {
        return periodoultimo;
    }

    public void setPeriodoultimo(Date periodoultimo) {
        this.periodoultimo = periodoultimo;
    }

    public Validacionalimentacion getValialiment() {
        return valialiment;
    }

    public void setValialiment(Validacionalimentacion valialiment) {
        this.valialiment = valialiment;
    }

    public SelectItem[] getTipoinforme() {
        return tipoinforme;
    }

    public void setTipoinforme(SelectItem[] tipoinforme) {
        this.tipoinforme = tipoinforme;
    }

    public BigDecimal[] getTotales() {
        return totales;
    }

    public void setTotales(BigDecimal[] totales) {
        this.totales = totales;
    }

    public BigDecimal getValorinterventoria() {
        return valorinterventoria;
    }

    public void setValorinterventoria(BigDecimal valorinterventoria) {
        this.valorinterventoria = valorinterventoria;
    }

    public UIDataTable getTablaContatoInter() {
        return tablaContatoInter;
    }

    public void setTablaContatoInter(UIDataTable tablaContatoInter) {
        this.tablaContatoInter = tablaContatoInter;
    }

    public UIDataTable getTablaSeguimientos() {
        return tablaSeguimientos;
    }

    public void setTablaSeguimientos(UIDataTable tablaSeguimientos) {
        this.tablaSeguimientos = tablaSeguimientos;
    }

    public List<Movimiento> getListadomovimientos() {
        return listadomovimientos;
    }

    public void setListadomovimientos(List<Movimiento> listadomovimientos) {
        this.listadomovimientos = listadomovimientos;
    }

    public UIDataTable getTablamovimientos() {
        return tablamovimientos;
    }

    public void setTablamovimientos(UIDataTable tablamovimientos) {
        this.tablamovimientos = tablamovimientos;
    }

    public String getIdcontratos() {
        return idcontratos;
    }

    public void setIdcontratos(String idcontratos) {
        this.idcontratos = idcontratos;
    }

    public String getContratistanombrecomple() {
        return contratistanombrecomple;
    }

    public void setContratistanombrecomple(String contratistanombrecomple) {
        this.contratistanombrecomple = contratistanombrecomple;
    }

    public List<Contratista> getContrat() {
        return contrat;
    }

    public void setContrat(List<Contratista> contrat) {
        this.contrat = contrat;
    }

    public int getPagina() {
        return pagina;
    }

    public void setPagina(int pagina) {
        this.pagina = pagina;
    }

    public int getTotalfilas() {
        return totalfilas;
    }

    public void setTotalfilas(int totalfilas) {
        this.totalfilas = totalfilas;
    }

    public int getTotalpaginas() {
        return totalpaginas;
    }

    public void setTotalpaginas(int totalpaginas) {
        this.totalpaginas = totalpaginas;
    }

    public boolean isVeranterior() {
        return veranterior;
    }

    public void setVeranterior(boolean veranterior) {
        this.veranterior = veranterior;
    }

    public boolean isVerultimos() {
        return verultimos;
    }

    public void setVerultimos(boolean verultimos) {
        this.verultimos = verultimos;
    }

    public UIDataTable getTablafactoratraso() {
        return tablafactoratraso;
    }

    public void setTablafactoratraso(UIDataTable tablafactoratraso) {
        this.tablafactoratraso = tablafactoratraso;
    }

    public UIDataTable getTabladificultadalimentacion() {




        return tabladificultadalimentacion;
    }

    public void setTabladificultadalimentacion(UIDataTable tabladificultadalimentacion) {
        this.tabladificultadalimentacion = tabladificultadalimentacion;
    }

    public UIDataTable getTablalogrosalimentacion() {
        return tablalogrosalimentacion;
    }

    public void setTablalogrosalimentacion(UIDataTable tablalogrosalimentacion) {
        this.tablalogrosalimentacion = tablalogrosalimentacion;
    }

    public SelectItem[] getPeriodoaliment() {
        return periodoaliment;
    }

    public void setPeriodoaliment(SelectItem[] periodoaliment) {
        this.periodoaliment = periodoaliment;
    }

    public String getSemaforo() {
        return semaforo;
    }

    public void setSemaforo(String semaforo) {
        this.semaforo = semaforo;
    }

    public boolean isEmpleosgenerados() {
        return empleosgenerados;
    }

    public void setEmpleosgenerados(boolean empleosgenerados) {
        this.empleosgenerados = empleosgenerados;
    }

    public String getFinentrega() {
        return finentrega;
    }

    public void setFinentrega(String finentrega) {
        this.finentrega = finentrega;
    }

    public String getStrimagenActual() {
        return strimagenActual;
    }

    public void setStrimagenActual(String strimagenActual) {
        this.strimagenActual = strimagenActual;
    }

    public String getStrimagenAnterior() {
        return strimagenAnterior;
    }

    public void setStrimagenAnterior(String strimagenAnterior) {
        this.strimagenAnterior = strimagenAnterior;
    }

    public String getStrimagenFutura() {
        return strimagenFutura;
    }

    public void setStrimagenFutura(String strimagenFutura) {
        this.strimagenFutura = strimagenFutura;
    }

    public List<Relacionalimentacionactividad> getLisrelalimacti() {
        return lisrelalimacti;
    }

    public void setLisrelalimacti(List<Relacionalimentacionactividad> lisrelalimacti) {
        this.lisrelalimacti = lisrelalimacti;
    }

    public String getDificulta() {
        return dificulta;
    }

    public void setDificulta(String dificulta) {
        this.dificulta = dificulta;
    }

    public String getLogro() {
        return logro;
    }

    public void setLogro(String logro) {
        this.logro = logro;
    }

    public int getMostrarPeriodo() {
        return mostrarPeriodo;
    }

    public void setMostrarPeriodo(int mostrarPeriodo) {
        this.mostrarPeriodo = mostrarPeriodo;
    }

    public String getMensageFechaSeleccio() {
        return mensageFechaSeleccio;
    }

    public void setMensageFechaSeleccio(String mensageFechaSeleccio) {
        this.mensageFechaSeleccio = mensageFechaSeleccio;
    }
    public String observacionG;

    public String getObservacionG() {
        return observacionG;
    }

    public void setObservacionG(String observacionG) {
        this.observacionG = observacionG;
    }

    public BigDecimal getBdejecutadoAlimentacion() {
        return bdejecutadoAlimentacion;
    }

    public void setBdejecutadoAlimentacion(BigDecimal bdejecutadoAlimentacion) {
        this.bdejecutadoAlimentacion = bdejecutadoAlimentacion;
    }

    public BigDecimal getBdproyectadoPeriodo() {
        return bdproyectadoPeriodo;
    }

    public void setBdproyectadoPeriodo(BigDecimal bdproyectadoPeriodo) {
        this.bdproyectadoPeriodo = bdproyectadoPeriodo;
    }

    public Periodo getPeriodoevo() {
        return periodoevo;
    }

    public void setPeriodoevo(Periodo periodoevo) {
        this.periodoevo = periodoevo;
    }

    public List<Periodo> getListaperiodo() {
        return listaperiodo;
    }

    public void setListaperiodo(List<Periodo> listaperiodo) {
        this.listaperiodo = listaperiodo;
    }

    public SelectItem[] getSelectItemPeriodoCorte() {
        return selectItemPeriodoCorte;
    }

    public void setSelectItemPeriodoCorte(SelectItem[] selectItemPeriodoCorte) {
        this.selectItemPeriodoCorte = selectItemPeriodoCorte;
    }

    public boolean isStpestado() {
        return stpestado;
    }

    public void setStpestado(boolean stpestado) {
        this.stpestado = stpestado;
    }
    private List<Relacionalimentacionactividad> listaActividadesAlimentacion = new ArrayList<Relacionalimentacionactividad>();

    public List<Relacionalimentacionactividad> getListaActividadesAlimentacion() {
        return listaActividadesAlimentacion;
    }

    public void setListaActividadesAlimentacion(List<Relacionalimentacionactividad> listaActividadesAlimentacion) {
        this.listaActividadesAlimentacion = listaActividadesAlimentacion;
    }

//    public Obra getObra() {
//        return obra;
//    }
//
//    public void setObra(Obra obra) {
//        this.obra = obra;
//    }
    public List<Alimentacioncualificacion> getListaAvance() {
        return listaAvance;
    }

    public void setListaAvance(List<Alimentacioncualificacion> listaAvance) {
        this.listaAvance = listaAvance;
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public int getTotalalim() {
        return totalalim;
    }

    public void setTotalalim(int totalalim) {
        this.totalalim = totalalim;
    }

    public String getDatos_grafico() {
        return datos_grafico;
    }

    public void setDatos_grafico(String datos_grafico) {
        this.datos_grafico = datos_grafico;
    }

    public GestionarXML getDatosgrafico() {
        return datosgrafico;
    }

    public void setDatosgrafico(GestionarXML datosgrafico) {
        this.datosgrafico = datosgrafico;
    }

    public List<Documentoobra> getListaDocsAlimentacion() {
        return listaDocsAlimentacion;
    }

    public void setListaDocsAlimentacion(List<Documentoobra> listaDocsAlimentacion) {
        this.listaDocsAlimentacion = listaDocsAlimentacion;
    }

    public UIDataTable getTablaDocsAlimentacion() {
        return tablaDocsAlimentacion;
    }

    public void setTablaDocsAlimentacion(UIDataTable tablaDocsAlimentacion) {
        this.tablaDocsAlimentacion = tablaDocsAlimentacion;
    }

    public List<Factoratraso> getListaFactoresAlimenta() {
        return listaFactoresAlimenta;
    }

    public void setListaFactoresAlimenta(List<Factoratraso> listaFactoresAlimenta) {
        this.listaFactoresAlimenta = listaFactoresAlimenta;
    }

    public Alimentacion getAlimentacionmostrar() {
        return alimentacionmostrar;
    }

    public void setAlimentacionmostrar(Alimentacion alimentacionmostrar) {
        this.alimentacionmostrar = alimentacionmostrar;
    }

    public List<Alimentacion> getListaAlimenta() {
        return listaAlimenta;
    }

    public void setListaAlimenta(List<Alimentacion> listaAlimenta) {
        this.listaAlimenta = listaAlimenta;
    }

    public int getNumeroalimentacion() {
        return numeroalimentacion;
    }

    public void setNumeroalimentacion(int numeroalimentacion) {
        this.numeroalimentacion = numeroalimentacion;
    }

    public BigDecimal getPromedioempleos() {
        return promedioempleos;
    }

    public void setPromedioempleos(BigDecimal promedioempleos) {
        this.promedioempleos = promedioempleos;
    }

    public Alimentacion getAlimentacionultima() {
        return alimentacionultima;
    }

    public void setAlimentacionultima(Alimentacion alimentacionultima) {
        this.alimentacionultima = alimentacionultima;
    }

    public int getPlazoinicial() {
        return plazoinicial;
    }

    public void setPlazoinicial(int plazoinicial) {
        this.plazoinicial = plazoinicial;
    }

    public Historicoobra getPrimerhisto() {
        return primerhisto;
    }

    public void setPrimerhisto(Historicoobra primerhisto) {
        this.primerhisto = primerhisto;
    }

    public boolean isVerPresu() {
        return verPresu;
    }

    public void setVerPresu(boolean verPresu) {
        this.verPresu = verPresu;
    }

    public boolean isVerPlazo() {
        return verPlazo;
    }

    public void setVerPlazo(boolean verPlazo) {
        this.verPlazo = verPlazo;
    }

    public String getStrimagenobraproyectada() {
        return strimagenobraproyectada;
    }

    public void setStrimagenobraproyectada(String strimagenobraproyectada) {
        this.strimagenobraproyectada = strimagenobraproyectada;
    }

    public boolean isBoolimg() {
        return boolimg;
    }

    public void setBoolimg(boolean boolimg) {
        this.boolimg = boolimg;
    }

    public List<Imagenevolucionobra> getListaImagenesevolucionobra() {
        return listaImagenesevolucionobra;
    }

    public void setListaImagenesevolucionobra(List<Imagenevolucionobra> listaImagenesevolucionobra) {
        this.listaImagenesevolucionobra = listaImagenesevolucionobra;
    }

    public List<Beneficiario> getLstbeneficiario() {
        return lstbeneficiario;
    }

    public void setLstbeneficiario(List<Beneficiario> lstbeneficiario) {
        this.lstbeneficiario = lstbeneficiario;
    }
    /**
     * Listado de calificaciones de auditoría para la obra
     */
    private List<Calificacionauditoriaobra> listacalificacionesauditoriaobra;

    public List<Calificacionauditoriaobra> getListacalificacionesauditoriaobra() {
        return listacalificacionesauditoriaobra;
    }

    public void setListacalificacionesauditoriaobra(List<Calificacionauditoriaobra> listacalificacionesauditoriaobra) {
        this.listacalificacionesauditoriaobra = listacalificacionesauditoriaobra;
    }
    /**
     * Referencia a la tabla de visitas de auditoría
     */
    private UIDataTable tablacalificacionesauditoriaobra = new UIDataTable();

    public UIDataTable getTablacalificacionesauditoriaobra() {
        return tablacalificacionesauditoriaobra;
    }

    public void setTablacalificacionesauditoriaobra(UIDataTable tablacalificacionesauditoriaobra) {
        this.tablacalificacionesauditoriaobra = tablacalificacionesauditoriaobra;
    }

    /**
     * <p>Automatically managed component initialization.
     * <strong>WARNING:</strong> This method is automatically generated, so any
     * user-specified code inserted here is subject to being replaced.</p>
     */
    private void _init() throws Exception {
    }
    // </editor-fold>
    private UIDataTable tablalistaavances = new UIDataTable();

    public UIDataTable getTablalistaavances() {
        return tablalistaavances;
    }

    public void setTablalistaavances(UIDataTable tablalistaavances) {
        this.tablalistaavances = tablalistaavances;
    }
    private int filaSeleccionada;

    public DetalleObra() {
//        if (getSessionBeanCobra().getCobraService().isVerproy()) {
//            getAdministrarObraNew().setObra(getSessionBeanCobra().getCobraService().getProyectoSoli());
//            iniciardetalle();
        llenarSelectPeriodoCorto();
        if (Boolean.parseBoolean(getBundle().getString("vistasgiprom"))) {
            calcularUbicacionGradosMinutosSegundo();
        }
    }

    public void setimagenObra(String imagen) {

        if (imagen == null && imagen.compareTo("/resources/Documentos/ObrasVigentes/") == 0) {
            strimagenobraproyectada = "/resources/imgs/bt_nodisponible.jpg";
        } else {
            strimagenobraproyectada = imagen;

        }
    }

    public String obtenerAlimentacionMostrar() {
        veralimenta = false;
        if (periodoevo.getIntidperiodo() == -1) {
            listaAlimenta = new ArrayList<Alimentacion>();
            alimentacionmostrar = new Alimentacion();
        } else {
            if (bundle.getString("varalimentacionxfecha").equals("true")) {
                listaAlimenta = getSessionBeanCobra().getCobraService().encontrarAlimentacionesxPeriodoFecha(periodoevo.getIntidperiodo(), getAdministrarObraNew().getObra().getIntcodigoobra());
                totalalim = listaAlimenta.size();
            } else {
                listaAlimenta = getSessionBeanCobra().getCobraService().encontrarAlimentacionesxPeriodo(periodoevo.getIntidperiodo(), getAdministrarObraNew().getObra().getIntcodigoobra());
                totalalim = listaAlimenta.size();
            }
        }

        return null;
    }

    /**
     * Consulta las alimentaciones
     *
     * @param filaSeleccionada Corresponde a la fila de la que proviene la
     * acción en la tabla
     * @return
     */
    public String encontraralimentacion() {

        if (periodoevo.getIntidperiodo() == -1) {
            alimentacionmostrar = new Alimentacion();
        } else {

            // alimentacionmostrar = getSessionBeanCobra().getCobraService().encontrarAlimentacionxPeriodo(periodoevo.getIntidperiodo(), getAdministrarObraNew().getObra().getIntcodigoobra());

            alimentacionmostrar = (Alimentacion) tablalistaavances.getRowData();

            if (getAlimentacionmostrar() != null && getAlimentacionmostrar().getSemaforo().getStrimagen().equals(bundle.getString("semafo_verde"))) {
                semaforo = "VERDE";
            }
            if (getAlimentacionmostrar() != null && getAlimentacionmostrar().getSemaforo().getStrimagen().equals(bundle.getString("semafo_amarillo"))) {
                semaforo = "AMARILLO";
            }
            if (getAlimentacionmostrar() != null && getAlimentacionmostrar().getSemaforo().getStrimagen().equals(bundle.getString("semafo_rojo"))) {
                semaforo = "ROJO";
            }
            veralimenta = true;
        }
        return null;
    }

//    public void elegirAlimentacionMostrar() {
//        Iterator al = .iterator();
//        int i = 1;
//        while (al.hasNext()) {
//            Alimentacion aliment = (Alimentacion) al.next();
//            if (i == getNumeroalimentacion()) {
//                alimentacionmostrar = aliment;
//                listaFactoresAlimenta = new ArrayList<Factoratraso>();
//                Iterator facto = alimentacionmostrar.getFactoratrasos().iterator();
//                while (facto.hasNext()) {
//
//                    listaFactoresAlimenta.add((Factoratraso) facto.next());
//                }
//                Iterator itrtDocumentoobra = this.alimentacionmostrar.getDocumentoobras().iterator();
//                this.listaDocsAlimentacion = new ArrayList<Documentoobra>();
//                while (itrtDocumentoobra.hasNext()) {
//
//                    this.listaDocsAlimentacion.add((Documentoobra) itrtDocumentoobra.next());
//                }
//            }
//            i++;
//        }
//        llenarAvance();
//
//    }
//    public String siguienteAlimentacion() {
//
//
//        setNumeroalimentacion(getNumeroalimentacion() + 1);
//        if (getNumeroalimentacion() <= totalalim) {
//            elegirAlimentacionMostrar();
//            stpestado = false;
//        } else {
//            setNumeroalimentacion(getNumeroalimentacion() - 1);
//        }
//
//        return null;
//    }
//    public String anteriorAlimentacion() {
//        setNumeroalimentacion(getNumeroalimentacion() - 1);
//        if (getNumeroalimentacion() >= 1) {
//            elegirAlimentacionMostrar();
//            stpestado = false;
//        } else {
//            setNumeroalimentacion(getNumeroalimentacion() + 1);
//        }
//
//        return null;
//    }
    public String bt_downloadAlimen_action() {
        // TODO: Process the action. Return value is a navigation
        // case name where null will return to the same page.
        Documentoobra doc = (Documentoobra) tablaDocsAlimentacion.getRowData();

        try {
            getSessionBeanCobra().setUrlAbri(Propiedad.getValor("ipserver") + "/" + getSessionBeanCobra().getBundle().getString("versioncobra") + "/" + doc.getStrubicacion());
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/Reportes");
        } catch (IOException ex) {
            Logger.getLogger(NuevoContratoBasico.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Download";
    }

    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }

    public String cargarGrafico() {

        String rptDate;

        BigDecimal divisor = BigDecimal.valueOf(1);
        BigDecimal planificadomostrar = BigDecimal.valueOf(0);
        BigDecimal ejecutadomostrar = BigDecimal.valueOf(0);

        ServletContext theApplicationsServletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

        String path = theApplicationsServletContext.getRealPath(URL);

        getDatosgrafico().cargarDocumento(path + "/amline_data.xml");


        Document docNuevo = new Document();

        // Vamos a generar la etiqueta raiz
        Element raiz = (Element) getDatosgrafico().getRaiz().clone();

        // y la asociamos al document
        Element etiquetaseries = raiz.getChild("series");

        etiquetaseries.removeChildren("value");

        int xid = 0;
        Date inicio;
        Date fin;
        Date ciclo;
        Calendar cal = Calendar.getInstance();

        inicio = getAdministrarObraNew().getObra().getDatefeciniobra();
        cal.setTime(inicio);

        fin = getAdministrarObraNew().getObra().getDatefecfinobra();

        Long dias = fin.getTime() - inicio.getTime();
        dias = dias / (3600 * 24 * 1000);


        for (int i = 0; i <= dias; i++) {

            Element xmlValues = new Element("value");
            xmlValues.setAttribute("xid", String.valueOf(xid));
            ciclo = cal.getTime();
            SimpleDateFormat fechilla = new SimpleDateFormat("dd MMM yyyy");
            rptDate = fechilla.format(ciclo);
            xmlValues.setText(rptDate);
            etiquetaseries.addContent(xmlValues);
            xid++;
            cal.add(Calendar.DATE, 1);
        }
        raiz.removeChild("graphs");
        Element xmlGraph = new Element("graphs");
        raiz.addContent(xmlGraph);
        Element xmlGraph1 = new Element("graph");
        xmlGraph1.setAttribute("gid", "1");
        xmlGraph.addContent(xmlGraph1);
        BigDecimal planificado = BigDecimal.valueOf(0);
        //for (Iterator obraIterador = ObrasGrafico.getPeriodos().iterator(); obraIterador.hasNext();) {
        for (Iterator obraIterador = getSessionBeanCobra().getCobraService().obtenerPeriodosObra(getAdministrarObraNew().getObra().getIntcodigoobra()).iterator(); obraIterador.hasNext();) {
            Periodo obra = (Periodo) obraIterador.next();
            Element xmlValues = new Element("value");
            planificado = planificado.add(obra.getNumvaltotplanif());
            Date fecperiodo = obra.getDatefecfinperiodo();
            Long dias_per = fecperiodo.getTime() - inicio.getTime();
            dias_per = dias_per / (3600 * 24 * 1000);
            xmlValues.setAttribute("xid", String.valueOf(dias_per));
            planificadomostrar = planificado;
            xmlValues.setText(String.valueOf(planificadomostrar.divide(divisor)));
            xmlGraph1.addContent(xmlValues);
        }
        Element xmlGraph2 = new Element("graph");
        xmlGraph2.setAttribute("gid", "2");
        xmlGraph.addContent(xmlGraph2);
        BigDecimal ejecutado = BigDecimal.valueOf(0);
        for (Iterator obraIterador = getSessionBeanCobra().getCobraService().obtenerAlimentacionesObra(getAdministrarObraNew().getObra().getIntcodigoobra()).iterator(); obraIterador.hasNext();) {
            Alimentacion obra = (Alimentacion) obraIterador.next();
            Element xmlValues1 = new Element("value");
            Date fecalimentacion = obra.getDatefecha();
            Long dias_per = fecalimentacion.getTime() - inicio.getTime();
            dias_per = dias_per / (3600 * 24 * 1000);
            ejecutadomostrar = obra.getNumtotalejecacu();
            xmlValues1.setAttribute("xid", String.valueOf(dias_per));

            String descripcion = "\n proyectado: $" + obra.getNumtotalproyacu().divide(divisor).toString();

            xmlValues1.setText(ejecutadomostrar.divide(divisor).toString());
            xmlValues1.setAttribute("description", descripcion);
            //xmlValues1.setText(String.valueOf("ejecutado: "+ejecutadomostrar.divide(divisor))+" proyectado: "+String.valueOf(obra.getNumtotalproyacu()));
            xmlGraph2.addContent(xmlValues1);

        }

        if (getPrimerhisto() != null) {


            Element xmlGraph3 = new Element("graph");
            xmlGraph3.setAttribute("gid", "3");
            xmlGraph.addContent(xmlGraph3);


            BigDecimal historico = BigDecimal.valueOf(0);



            Iterator periodositer = getSessionBeanCobra().getCobraService().obtenerPeriodosporHistorico(getPrimerhisto().getOididhistoricoobra()).iterator();

            while (periodositer.hasNext()) {

                Periodohisto periodohisto = (Periodohisto) periodositer.next();
                Element xmlValues2 = new Element("value");

                historico = historico.add(periodohisto.getNumvaltotplanif());

                Date fecperiodo = periodohisto.getDatefecfinperiodo();
                Long dias_per = fecperiodo.getTime() - inicio.getTime();
                dias_per = dias_per / (3600 * 24 * 1000);

                xmlValues2.setAttribute("xid", String.valueOf(dias_per));

                xmlValues2.setText(String.valueOf(historico.divide(divisor)));
                xmlGraph3.addContent(xmlValues2);
            }
        }



        docNuevo.addContent(raiz);


        getDatosgrafico().guardarDocumento(docNuevo, path + "/amline_data.xml");

        Format format = Format.getPrettyFormat();
        // Creamos el serializador con el formato deseado
        XMLOutputter xmloutputter = new XMLOutputter(format);
        // Serializamos el document parseado
        datos_grafico = xmloutputter.outputString(docNuevo);
        datos_grafico = datos_grafico.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");

        return "null";
    }

    public void ConozcaProyecto() {
        getSessionBeanCobra().getCobraService().conozcaProyecto(getAdministrarObraNew().getObra(), getSessionBeanCobra().getUsuarioObra());
    }

    public void iniciardetalle() {
        getAdministrarObraNew().setProyectoestrategia(false);
        imagenEvolucion();
        getAdministrarObraNew().cargarListas();
        llenarContratosInterventoria();
        getAdministrarObraNew().modificarObjetoObra = false;
        getAdministrarObraNew().btn_habilitarModificarObjeto = true;
        getAdministrarObraNew().habilitarModificarimpacto = true;
        getAdministrarObraNew().habilitarGuardarimpacto = false;
        getAdministrarObraNew().controltipodocumento = 3;
        obraMapa = getSessionBeanCobra().getCobraService().obtenerVistaObraMapaxid(getAdministrarObraNew().getObra().getIntcodigoobra());
        if (getSessionBeanCobra().getBundle().getString("versioncobra").compareTo("siente") == 0 && obraMapa.getContrato() != null) {
            if (getSessionBeanCobra().getMarcoLogicoService().encontrarEstrategiaProyectoMarcoLogico(obraMapa.getIntcodigoobra()) != null) {
                getAdministrarObraNew().setProyectoestrategia(true);
                //Llenamos objetos de avance                
                getAdministrarObraNew().setVproductomedios(getSessionBeanCobra().getMarcoLogicoService().obtenerVistaProyectosMarcoxTipo(2, obraMapa.getIntcodigoobra()));
                getAdministrarObraNew().setVproductogestion(getSessionBeanCobra().getMarcoLogicoService().obtenerVistaProyectosMarcoxTipo(3, obraMapa.getIntcodigoobra()));                

            }
        }
        System.out.println("obramapa");
        if (obraMapa.getContrato() != null){
            listimpactosocialdetalle = getSessionBeanCobra().getCobraService().encontrarImpactoSocial(obraMapa.getContrato().getIntidcontrato());
            System.out.println("lista obra mapa");
        }
        tipoImpactoSocialDetalle();
        limpiardetalle();
        if (getSessionBeanCobra().getCobraService().isCiu()) {
            llenarseguidores();
            ConozcaProyecto();
            obtenerSeguidorporObra();
        }

        semaforo = "";
        //llenarContratosInterventoria();
        idcontratos = "";
//        if (getSessionBeanCobra().getCobraService().isVerproy()) {
//
//            getAdministrarObraNew().setObra(getSessionBeanCobra().getCobraService().getProyectoSoli());
//        }
        contratistanombrecomple = "";
        setContrat(getSessionBeanCobra().getCobraService().obtenerContratista(getAdministrarObraNew().getObra()));

        // getAdministrarObraNew().llenarContratoInterventoria();
        //getAdministrarObraNew().llenarContratoObra();
        if (getAdministrarObraNew().getListaContrato() != null) {
            int i = 0;
            while (i < getAdministrarObraNew().getListaContrato().size()) {
                if (idcontratos.equals("")) {
                    idcontratos = getAdministrarObraNew().getListaContrato().get(i).getContrato().getStrnumcontrato() + "";
                } else {
                    idcontratos = idcontratos + " - " + getAdministrarObraNew().getListaContrato().get(i).getContrato().getStrnumcontrato();
                }
                i++;
            }
        }
        if (getContrat() != null) {
            int i = 0;
            while (i < getContrat().size()) {

                if (getContrat().get(i) != null) {
                    //validar que haya mas de un contratista
                    if (contratistanombrecomple.equals("")) {
                        contratistanombrecomple = getContrat().get(i).getStrnombre() + " ";
                        if (getContrat().get(i).getStrapellido1() != null) {
                            contratistanombrecomple = contratistanombrecomple + getContrat().get(i).getStrapellido1();
                        }
                    } else {
                        contratistanombrecomple = contratistanombrecomple + " - "
                                + getContrat().get(i).getStrnombre() + " ";
                        if (getContrat().get(i).getStrapellido1() != null) {
                            contratistanombrecomple = contratistanombrecomple + getContrat().get(i).getStrapellido1();
                        }
                    }
                }
                i++;
            }
        }
        setimagenObra(getAdministrarObraNew().getObra().getStrimagenobra());
        setAlimentacionultima(new Alimentacion());
        setAlimentacionultima(getSessionBeanCobra().getCobraService().obtenerUltimaalimentacion(getAdministrarObraNew().getObra().getIntcodigoobra()));

        if (obraMapa != null && obraMapa.getSemaforo() != null && obraMapa.getSemaforo().equals(bundle.getString("semafo_verde"))) {
            semaforo = "VERDE";
        }
        if (obraMapa != null && obraMapa.getSemaforo() != null && obraMapa.getSemaforo().equals(bundle.getString("semafo_amarillo"))) {
            semaforo = "AMARILLO";
        }
        if (obraMapa != null && obraMapa.getSemaforo() != null && obraMapa.getSemaforo().equals(bundle.getString("semafo_rojo"))) {
            semaforo = "ROJO";
        }

        totalalim = 0;
        primerhisto = getSessionBeanCobra().getCobraService().obtenerPrimerHistorico(getAdministrarObraNew().getObra().getIntcodigoobra());

        if ((primerhisto != null) && getAdministrarObraNew().getObra().getTipoestadobra().getIntestadoobra() == 1) {
            long diferenciaFecha = primerhisto.getDatefechist().getTime() - getAdministrarObraNew().getObra().getDatefeciniobra().getTime();
            diferenciaFecha = diferenciaFecha / (36000 * 24 * 100) + 1;
            setPlazoinicial(Integer.parseInt(String.valueOf(diferenciaFecha)));

            if (getPlazoinicial() == getAdministrarObraNew().getObra().getIntplazoobra()) {
                setVerPlazo(false);
            } else {
                setVerPlazo(true);
            }

            if (getPrimerhisto().getNumvalorhist().compareTo(getAdministrarObraNew().getObra().getNumvaltotobra()) == 0) {
                setVerPresu(false);
            } else {
                setVerPresu(true);
            }
        }
        //cargarGrafico();
        llenarSelectPeriodoCorto();
        calcularUbicacionGradosMinutosSegundo();


//        } catch (Exception e) {
//        }

        getAdministrarObraNew().iniciarDocumentos();
        if (getSessionBeanCobra().getCobraService().isCiu()) {
            getAdministrarObraNew().mostrarGoogle();
        }
        getAdministrarObraNew().setOpcion(0);
        if (getAdministrarObraNew().getObra().getDatefecfinobra() != null) {
            finentrega = getAdministrarObraNew().getObra().getDatefecfinobra().toString();
        }
    }

    protected AdministrarObraNew getAdministrarObraNew() {
        return (AdministrarObraNew) FacesUtils.getManagedBean("Supervisor$AdministrarObraNew");
    }

    public String reportePdf() {
        try {
            getSessionBeanCobra().setUrlAbri(Propiedad.getValor("ipserver") + bundle.getString("birtpdfdetalle") + getAdministrarObraNew().getObra().getIntcodigoobra());
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/Reportes");
        } catch (IOException ex) {
            Logger.getLogger(DetalleObra.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String reportePdfSeguimiento() {
        try {
            getSessionBeanCobra().setUrlAbri(Propiedad.getValor("ipserver") + bundle.getString("birtpdfseguimiento") + getAdministrarObraNew().getObra().getIntcodigoobra());
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/Reportes");
        } catch (IOException ex) {
            Logger.getLogger(DetalleObra.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String reporteExcel() {
        try {
            getSessionBeanCobra().setUrlAbri(Propiedad.getValor("ipserver") + bundle.getString("birtexceldetalle") + getAdministrarObraNew().getObra().getIntcodigoobra());
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/Reportes");
        } catch (IOException ex) {
            Logger.getLogger(DetalleObra.class.getName()).log(Level.SEVERE, null, ex);
        }


        return null;
    }

    public String reporteWord() {
        try {
            getSessionBeanCobra().setUrlAbri(Propiedad.getValor("ipserver") + bundle.getString("birtworddetalle") + getAdministrarObraNew().getObra().getIntcodigoobra());
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/Reportes");
        } catch (IOException ex) {
            Logger.getLogger(DetalleObra.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public void llenarAvance() {

        listaAvance = getSessionBeanCobra().getCobraService().encontrarAlimentacionesCualitativa(alimentacionmostrar.getIntidalimenta());

    }

    public String llenaractividadesAlimentacion() {

        primeroActividades();
        //   listaActividadesAlimentacion = getSessionBeanCobra().getCobraService().obtenerRelacionesAlimentacionActividadxAlimentacion(alimentacionmostrar.getIntidalimenta());
        stpestado = true;
        return null;
    }
    private Map<Integer, Object> index = new LinkedHashMap<Integer, Object>();
    private ArrayList<String> names = new ArrayList<String>();
    private ArrayList<String> colors = new ArrayList<String>();

    public String imagenEvolucion() {
        //Encontrar imagen dependiendo del tipo 1.Anterior, 2.Actual, 3.futura

        getSessionBeanCobra().getCobraService().setListaimagen(getSessionBeanCobra().getCobraService().encontrarImagenxObra(getAdministrarObraNew().getObra().getIntcodigoobra()));

        setStrimagenAnterior("");
        setStrimagenActual("");
        setStrimagenFutura("");
        int i = 0;
        setListaImagenesevolucionobra(getSessionBeanCobra().getCobraService().getListaimagen());
        while (i < getSessionBeanCobra().getCobraService().getListaimagen().size()) {
            if (getSessionBeanCobra().getCobraService().getListaimagen().get(i).getBooleanactual() != null && getSessionBeanCobra().getCobraService().getListaimagen().get(i).getBooleanactual()) {
                setStrimagenActual(getSessionBeanCobra().getCobraService().getListaimagen().get(i).getStrubicacion());
                getSessionBeanCobra().getCobraService().getListactual().add(getSessionBeanCobra().getCobraService().getListaimagen().get(i));
            }
            switch (getSessionBeanCobra().getCobraService().getListaimagen().get(i).getTipoimagen().getIntidtipoimagen()) {
                case 1:
                    setStrimagenAnterior(getSessionBeanCobra().getCobraService().getListaimagen().get(i).getStrubicacion());
                    getSessionBeanCobra().getCobraService().getListanterior().add(getSessionBeanCobra().getCobraService().getListaimagen().get(i));
                    break;
                case 3:
                    setStrimagenFutura(getSessionBeanCobra().getCobraService().getListaimagen().get(i).getStrubicacion());
                    getSessionBeanCobra().getCobraService().getListfutura().add(getSessionBeanCobra().getCobraService().getListaimagen().get(i));
                    break;
            }
            i++;
        }
        if (getStrimagenAnterior().compareTo("") != 0 && getStrimagenAnterior() != null) {
            getStrimagenAnterior();
        } else {
            if (getAdministrarObraNew().getObra().getStrimagenobra().compareTo("") != 0 && getAdministrarObraNew().getObra().getStrimagenobra() != null) {
                setStrimagenAnterior(getAdministrarObraNew().getObra().getStrimagenobra());
            } else {
                setStrimagenAnterior("/resources/imgs/bt_nodisponible.png");
            }
        }
        if (getStrimagenActual().compareTo("") != 0 && getStrimagenActual() != null) {
            getStrimagenActual();
            boolimg = true;
        } else {
            setStrimagenActual(bundle.getString("noimagen"));
            boolimg = false;
        }
        if (getStrimagenFutura().compareTo("") != 0 && getStrimagenFutura() != null) {
            getStrimagenFutura();
        } else {
            setStrimagenFutura(bundle.getString("noimagen"));
        }
        verModalImagenvermas = true;
        return null;
    }

    public Map<Integer, Object> getData() {
        index.put(0, 360);
        index.put(3, 400);
        index.put(6, 340);
        index.put(9, 370);
        index.put(12, 390);
        index.put(15, 400);
        index.put(18, 420);
        index.put(21, 450);
        return index;
    }

    public ArrayList<String> getNames() {
        names.add("Alimentaciones");
        return names;
    }

    public ArrayList<String> getColors() {
        colors.add("#5db2c2");
        return colors;
    }

    public void llenarSelectPeriodoCorto() {
        listaperiodo = getSessionBeanCobra().getCobraService().encontrarPeriodosObra(getAdministrarObraNew().getObra());
        selectItemPeriodoCorte = new SelectItem[listaperiodo.size()];//asigno tamaño al selecitem
        int i = 0;
        SelectItem selectItem = new SelectItem(0, "Seleccione Periodo");
        for (Periodo pe : listaperiodo) {
            selectItem = new SelectItem(pe.getIntidperiodo(), "Del     " + pe.getDatefeciniperiodo() + "  al     " + pe.getDatefecfinperiodo());
            selectItemPeriodoCorte[i++] = selectItem;
        }
    }

//    public void buscarAlimentacionPeriodo() {//trae las alimentaciones por periodo
//        llenarSelectPeriodoCorto();
//        mostrarPeriodo = 1;
//        Periodo pesele = getSessionBeanCobra().getCobraService().encontrarPeriodoxid(getPeriodoevo().getIntidperiodo());
//
//        pesele = listaperiodo.get(periodoevo.getIntidperiodo() - 1);//busco el periodo seleccionado de la lista y la asigno al objpor el indice de la lista toca restar
//        mensageFechaSeleccio = "PERIODO " + periodoevo.getIntidperiodo() + " - de " + pesele.getDatefeciniperiodo() + " a " + pesele.getDatefecfinperiodo();
//        bdproyectadoPeriodo = new BigDecimal(0.0);
//        bdejecutadoAlimentacion = new BigDecimal(0.0);
//        BigDecimal sumatoriaejecutaBd = new BigDecimal(0.0);
//        bdproyectadoPeriodo = pesele.getNumvaltotplanif();
//        listaAlimenta = getSessionBeanCobra().getCobraService().obtenerAlimentacionxPeriodo(pesele.getDatefecfinperiodo(), pesele.getDatefecfinperiodo(), getAdministrarObraNew().getObra().getIntcodigoobra());
//        //  alimentacionmostrar=getSessionBeanCobra().getCobraService().obtenerAlimentacionxPeriodo( pesele.getDatefecfinperiodo(), pesele.getDatefecfinperiodo(),  obr.getIntcodigoobra());
//
//        for (Alimentacion allis : listaAlimenta) {
//            sumatoriaejecutaBd = sumatoriaejecutaBd.add(allis.getNumtotalejec());
//            observacionG = allis.getTextcomentario();
//            listaAvance = getSessionBeanCobra().getCobraService().encontrarAlimentacionesCualitativa(allis.getIntidalimenta());//alimentacion culitativa
//            logro = listaAvance.get(0).getStrlogro();
//            dificulta = listaAvance.get(0).getStrdificultad();
//            bdejecutadoAlimentacion = sumatoriaejecutaBd;
//            lisrelalimacti = getSessionBeanCobra().getCobraService().obtenerRelacionesAlimentacionActividadxAlimentacion(allis.getIntidalimenta());
//            lisrelalimacti.get(0).setNumvalejec(sumatoriaejecutaBd);
//            listaActividadesAlimentacion = getSessionBeanCobra().getCobraService().obtenerRelacionesAlimentacionActividadxAlimentacion(allis.getIntidalimenta());
//        }
//
//        elegirAlimentacionMostrar();
//    }
    public String llenarPeriodoAliment() {
        if (bundle.getString("varalimentacionxfecha").equals("true")) {
            getSessionBeanCobra().getCobraService().setLisperiodosaliment(
                    getSessionBeanCobra().getCobraService().encontrarPeriodosxFechaAlimentacionesObra(getAdministrarObraNew().getObra().getIntcodigoobra()));
        } else {
            getSessionBeanCobra().getCobraService().setLisperiodosaliment(
                    getSessionBeanCobra().getCobraService().encontrarPeriodosxAlimentacionesObra(getAdministrarObraNew().getObra().getIntcodigoobra()));
        }
        periodoaliment = new SelectItem[getSessionBeanCobra().getCobraService().getLisperiodosaliment().size()];
        int i = 0;
        for (Periodo peri : getSessionBeanCobra().getCobraService().getLisperiodosaliment()) {
            SelectItem per = new SelectItem(peri.getIntidperiodo(), peri.getDatefeciniperiodo() + " - " + peri.getDatefecfinperiodo());
            if (i == 0) {
                periodoevo.setIntidperiodo(-1);
            }
            periodoultimo = peri.getDatefecfinperiodo();
            periodoaliment[i++] = per;
        }
        veralimenta = false;
        return null;
    }

    public String mostrarAlimentacionCualitativa() {
        getSessionBeanCobra().getCobraService().setListAlimetCualitativa(getSessionBeanCobra().getCobraService().encontrarAlimentacionesCualitativa(alimentacionmostrar.getIntidalimenta()));

        return null;
    }

    public String mostrarUltimasAlimentacionCualitativa() {
        getSessionBeanCobra().getCobraService().setListAlimetCualitativa(getSessionBeanCobra().getCobraService().encontrarUltimasAlimentacionesCualitativa(getAdministrarObraNew().getObra().getIntcodigoobra(), 0, 10));
        System.out.println("entre aca = ");
        return null;
    }

    public String llenarFactoratraso() {
        getSessionBeanCobra().getCobraService().setFactoratrasos(getSessionBeanCobra().getCobraService().encontrarFactRetrasoAlimentacion(alimentacionmostrar));
        return null;
    }

    public String LlenarDocumentos() {
        listaDocsAlimentacion = getSessionBeanCobra().getCobraService().obtenerDocumentosxObra(alimentacionmostrar.getObra());
        return null;
    }

    public String primeroActividades() {

        listaActividadesAlimentacion = getSessionBeanCobra().getCobraService().obtenerActividadxAlimentacion(alimentacionmostrar.getIntidalimenta(), 0, 5);
        totalfilas = getSessionBeanCobra().getCobraService().numAlimentacionActividadxAlimentacion(alimentacionmostrar.getIntidalimenta());

        pagina = 1;
        if (totalfilas <= 5) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 5;
            if (totalfilas % 5 > 0) {
                totalpaginas++;
            }
        }
        veranterior = false;
        if (totalpaginas > 1) {
            verultimos = true;
        } else {
            verultimos = false;
        }
        return null;
    }

    public String siguienteActividades() {

        int num = (pagina) * 5;

        listaActividadesAlimentacion = getSessionBeanCobra().getCobraService().obtenerActividadxAlimentacion(alimentacionmostrar.getIntidalimenta(), num, num + 5);
        totalfilas = getSessionBeanCobra().getCobraService().numAlimentacionActividadxAlimentacion(alimentacionmostrar.getIntidalimenta());

        if (totalfilas <= 5) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 5;
            if (totalfilas % 5 > 0) {
                totalpaginas++;
            }
        }
        pagina = pagina + 1;
        if (pagina < totalpaginas) {
            verultimos = true;
        } else {
            verultimos = false;
        }
        veranterior = true;

        return null;
    }

    public String anterioresActividades() {

        pagina = pagina - 1;
        int num = (pagina - 1) * 5;

        listaActividadesAlimentacion = getSessionBeanCobra().getCobraService().obtenerActividadxAlimentacion(alimentacionmostrar.getIntidalimenta(), num, num + 5);
        totalfilas = getSessionBeanCobra().getCobraService().numAlimentacionActividadxAlimentacion(alimentacionmostrar.getIntidalimenta());


        if (totalfilas <= 5) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 5;
            if (totalfilas % 5 > 0) {
                totalpaginas++;
            }
        }

        if (pagina > 1) {
            veranterior = true;
        } else {
            veranterior = false;
        }
        verultimos = true;
        return null;
    }

    public String ultimoActividades() {

        int num = totalfilas % 5;

        totalfilas = getSessionBeanCobra().getCobraService().numAlimentacionActividadxAlimentacion(alimentacionmostrar.getIntidalimenta());
        listaActividadesAlimentacion = getSessionBeanCobra().getCobraService().obtenerActividadxAlimentacion(alimentacionmostrar.getIntidalimenta(), totalfilas - num, totalfilas);

        if (totalfilas <= 5) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 5;
            if (totalfilas % 5 > 0) {
                totalpaginas++;
            }
        }
        pagina = totalpaginas;
        if (pagina < totalpaginas) {
            verultimos = true;
        } else {
            verultimos = false;
        }
        veranterior = true;

        return null;
    }

    public String llenarSeguimientosxProyectos() {
        if (getAdministrarObraNew().getObra().getSolicitud_obra() != null) {
            getSessionBeanCobra().getCobraService().setListaseguimientos(
                    getSessionBeanCobra().getCobraService().encontrarSeguimientosxProyecto(getAdministrarObraNew().getObra()));
        }

        return null;
    }

    public String seguimientoconsul() {
        Seguimiento segui = (Seguimiento) tablaSeguimientos.getRowData();
        getAdminSupervisionExterna().setObjetoSeguimiento(segui);

        return "consuldetaseguimi";
    }

    public String seguimientoreport() {
        Seguimiento segui = (Seguimiento) tablaSeguimientos.getRowData();
        try {
            getSessionBeanCobra().setUrlAbri(Propiedad.getValor("ipserver") + bundle.getString("reportepdfseguimiento") + segui.getIntidseguimiento());
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/Reportes");
        } catch (IOException ex) {
            Logger.getLogger(Obra.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    protected AdminSupervisionExterna getAdminSupervisionExterna() {
        return (AdminSupervisionExterna) FacesUtils.getManagedBean("Supervision$Externa");
    }

    public String llenarMovimientos() {
        int codsoli = 0;


        if (getAdministrarObraNew().getObra().getSolicitud_obra() != null) {
            codsoli = getAdministrarObraNew().getObra().getSolicitud_obra().getIntserial();
            listadomovimientos = getSessionBeanCobra().getSolicitudService().encontrarmovimientossolicitudobra(codsoli);
            Carcularvalores();
        }
        if (getAdministrarObraNew().getObra().getSolicitudmaestro() != null) {

            codsoli = (int) getAdministrarObraNew().getObra().getSolicitudmaestro().getOidcodigosolicitudmaestro();
            listadomovimientos = getSessionBeanCobra().getAtencionhumanitariaService().encontrarmovimientossolicitud(codsoli);
            Carcularvalores();
        }


        return null;
    }

    public String Carcularvalores() {

        Iterator iter = listadomovimientos.iterator();

        totales = new BigDecimal[4];
        totales[1] = BigDecimal.valueOf(0);
        totales[2] = BigDecimal.valueOf(0);

        while (iter.hasNext()) {
            Movimiento mov = (Movimiento) iter.next();
            if (mov.getNumvlrmovimiento() == null) {
                mov.setNumvlrmovimiento(BigDecimal.valueOf(0));
            }
            totales[1] = totales[1].add(mov.getNumvlrmovimiento());

            if (mov.getNumvlrejecutado() == null) {
                mov.setNumvlrejecutado(BigDecimal.valueOf(0));
            }
            totales[2] = totales[2].add(mov.getNumvlrejecutado());

        }
        return null;
    }

    public String llenarContratosInterventoria() {
        valorinterventoria = BigDecimal.ZERO;

        //List<Relacioncontratoobra> listrelacion = new ArrayList<Relacioncontratoobra>();

        getSessionBeanCobra().getCobraService().setListaContratoInterventoria(new ArrayList<Relacioncontratoobra>());
        //listrelacion = getSessionBeanCobra().getCobraService().encontrarRelacionContratosObra(getAdministrarObraNew().getObra().getIntcodigoobra(),true);
        getSessionBeanCobra().getCobraService().setListaContratoInterventoria(getSessionBeanCobra().getCobraService().encontrarRelacionContratosObra(getAdministrarObraNew().getObra().getIntcodigoobra(), true));
        for (Relacioncontratoobra relacion : getSessionBeanCobra().getCobraService().getListaContratoInterventoria()) {
            if (relacion.getContrato().getTipocontratoconsultoria().getIntidtipocontratoconsultoria() > 1) {
                valorinterventoria = valorinterventoria.add(relacion.getNumvalorrelacion());
                //getSessionBeanCobra().getCobraService().getListaContratoInterventoria().add(relacion.getContrato());

            }
        }

        return null;
    }

    public String reporteInterventoriaSeguimiento() {
        try {
            getSessionBeanCobra().setUrlAbri(Propiedad.getValor("ipserver") + bundle.getString("reportepdfseguimientointerventoria") + getAdministrarObraNew().getObra().getIntcodigoobra());
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/Reportes");
        } catch (IOException ex) {
            Logger.getLogger(Obra.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public void llenarmodalvalidacion() {
        inicializarValidacionAlimentacion();

        permitirvalidar = getAdministrarObraNew().cargarInformeavalidar();
        llenarTipoInforme();
    }

    public void inicializarValidacionAlimentacion() {
        valialiment = new Validacionalimentacion();
        valialiment.setDatefecharevision(new Date());
        valialiment.setTipoinforme(new Tipoinforme(1));
        valialiment.setBoolestado(false);

    }

    public String guardarAprobacionAlimentacionSi() {

        valialiment.setIntidalimenta(Integer.parseInt(String.valueOf(getSessionBeanCobra().getCobraService().obtenerUltimaalimentacion(getAdministrarObraNew().getObra().getIntcodigoobra()).getIntidalimenta())));
        valialiment.setDatefecharevision(new Date());
        valialiment.setIntusuAutoriza(getSessionBeanCobra().getUsuarioObra().getUsuId());
        valialiment.setIntcodigoobra(getAdministrarObraNew().getObra().getIntcodigoobra());
        valialiment.setDatefechaautorizacion(new Date());
        alimentacionmostrar.setBoolaplicado(true);
        alimentacionmostrar.setJsfUsuarioByIntusuAutoriza(getSessionBeanCobra().getUsuarioObra());

        getSessionBeanCobra().getCobraService().guardarValidacionAlimentacion(valialiment, getSessionBeanCobra().getUsuarioObra(), alimentacionmostrar);

        getAdministrarObraNew().getObra().setEnalimentacion(false);
        getSessionBeanCobra().getCobraService().guardarObra(getAdministrarObraNew().getObra(), getSessionBeanCobra().getUsuarioObra(), -1);
        inicializarValidacionAlimentacion();
        return null;
    }

    public String guardarListaVerificacion() {
        getSessionBeanCobra().getCobraService().guardarListaVerificacion(listaverificacion, getAdministrarObraNew().getObra(), getSessionBeanCobra().getUsuarioObra());

        return null;
    }

//    public String eliminarAlimentacion() {
//        if (getSessionBeanCobra().getCobraService().fEliminarAlimentacion(alimentacionmostrar, getSessionBeanCobra().getUsuarioObra())) {
//            FacesUtils.addInfoMessage("Se ha eliminado la alimentacion");
//            return null;
//        }
//        return null;
//    }
    public String llenarTipoInforme() {
        getSessionBeanCobra().getCobraService().setListatipoinforme(getSessionBeanCobra().getCobraService().obtenerTipoInforme());
        int cual = getAdministrarObraNew().getInformeavalidar().getIntidtipoinforme() - 1;

        tipoinforme = new SelectItem[getSessionBeanCobra().getCobraService().getListatipoinforme().size() - cual];
        int i = 0;
        for (Tipoinforme tipodoc : getSessionBeanCobra().getCobraService().getListatipoinforme()) {
            if (tipodoc.getIntidtipoinforme() > cual) {
                SelectItem tipod = new SelectItem(tipodoc.getIntidtipoinforme(), tipodoc.getStrdescripcion());
                tipoinforme[i++] = tipod;
            }
        }
        return null;
    }

    public String llenarListaverificacion() {
        listaverificacion = getSessionBeanCobra().getCobraService().obtenerlistarVerificacion(getAdministrarObraNew().getObra());


        return null;

    }

    public String reporteHistorialValidaciones() {

        try {
            getSessionBeanCobra().setUrlAbri(Propiedad.getValor("ipserver") + bundle.getString("reportehistorialvalidaciones") + getAdministrarObraNew().getObra().getIntcodigoobra());
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/Reportes");
        } catch (IOException ex) {
            Logger.getLogger(Obra.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void limpiardetalle() {
        alimentacionultima = new Alimentacion();
        listaAlimenta = new ArrayList<Alimentacion>();
        alimentacionmostrar = new Alimentacion();
        listaFactoresAlimenta = new ArrayList<Factoratraso>();
        listaDocsAlimentacion = new ArrayList<Documentoobra>();
        listaAvance = new ArrayList<Alimentacioncualificacion>();
        List<Relacionalimentacionactividad> lisrelalimacti = new ArrayList<Relacionalimentacionactividad>();
        List<Imagenevolucionobra> listaImagenesevolucionobra = new ArrayList<Imagenevolucionobra>();

    }

    public int llenarseguidores() {
        listasegudires = getSessionBeanCobra().getCobraService().encontrarSeguidoresxObraxLimit(getAdministrarObraNew().getObra().getIntcodigoobra(), 0).size();
        return listasegudires;
    }

    public void llenarBeneficiariosxavance() {
        lstbeneficiario = new ArrayList<Beneficiario>();
        lstbeneficiario = getSessionBeanCobra().getCobraService().obtenerlistaBeneficiariosxavance(alimentacionmostrar.getIntidalimenta());
    }

    public String llenarBeneficiariosxproyecto() {
        totalben = 0;
        lstbeneficiario = new ArrayList<Beneficiario>();
        lstbeneficiario = getSessionBeanCobra().getCobraService().obtenerlistaBeneficiariosxsolicitud(getAdministrarObraNew().getObra().getSolicitudmaestro().getOidcodigosolicitudmaestro());
        totalben = lstbeneficiario.size();
        return null;

    }

    public String llenarActividadesxproyecto() {
        totalprogramado = BigDecimal.ZERO;
        totalejecutado = BigDecimal.ZERO;
        listactividades = new ArrayList<Actividadobra>();
        listactividades = getSessionBeanCobra().getCobraService().obtenerActividadesObra(getAdministrarObraNew().getObra().getIntcodigoobra());
        for (Actividadobra act : listactividades) {
            totalprogramado = totalprogramado.add(act.getNumvalorplanifao().multiply(BigDecimal.valueOf(act.getFloatcantplanifao())));
            totalejecutado = totalejecutado.add(act.getNumvalorejecutao());
        }
        return null;
    }

    public String obtenerSeguidorporObra() {
        seguirobra = getSessionBeanCobra().getCobraService().obtenerSeguidorporObra(getAdministrarObraNew().getObra().getIntcodigoobra(), getSessionBeanCobra().getUsuarioObra().getUsuId());
        if (seguirobra != null) {
            seguidorporobra = true;
        } else {
            seguidorporobra = false;
        }
        return null;
    }

    public String registrarSeguirobra() {
        seguirobra = new Relacionobraseguidor();
        seguirobra.setJsfUsuario(getSessionBeanCobra().getUsuarioObra());
        seguirobra.setObra(getAdministrarObraNew().getObra());
        getSessionBeanCobra().getCobraService().guardarSeguimientoporobra(seguirobra);
        seguidorporobra = true;
        return null;
    }

    public String eliminarSeguirobra() {
        getSessionBeanCobra().getCobraService().borrarSeguimientoporobra(seguirobra);
        seguidorporobra = false;
        return null;
    }

    /**
     * Carga el listado de proyectos hijos del proyecto actual
     *
     * @return
     */
    public String cargarSubProyectos() {
        listaSubProyectos = getSessionBeanCobra().getCobraService().obtenerSubProyectos(getAdministrarObraNew().getObra().getIntcodigoobra());
        return null;
    }

    public String cargaImagenActual() {
        boolimg = true;
        return null;
    }

    /**
     * @return the filaSeleccionada
     */
    public int getFilaSeleccionada() {
        return filaSeleccionada;
    }

    /**
     * @param filaSeleccionada the filaSeleccionada to set
     */
    public void setFilaSeleccionada(int filaSeleccionada) {
        this.filaSeleccionada = filaSeleccionada;
    }

    /**
     * Consulta la visitas de auditoría fallidas, correspondientes al usuario
     * actual del sistema
     */
    public void cargarVisitasAuditoriaObra() {
        listacalificacionesauditoriaobra = getSessionBeanCobra().getCobraService().encontrarCalificacionesAuditoriaObra(getAdministrarObraNew().getObra().getIntcodigoobra(), Visita.ESTADO_CORRECTO);
    }

    /**
     * Descarga el reporte de errores de visita fallida
     */
    public void generarReporteVisitaAuditoria() {
        Calificacionauditoriaobra calificacionauditoriaobra = (Calificacionauditoriaobra) tablacalificacionesauditoriaobra.getRowData();
        try {
            getSessionBeanCobra().setUrlAbri(Propiedad.getValor("ipserver") + Propiedad.getValor("reportematrizauditoria", Propiedad.getValor("nombrebd"), calificacionauditoriaobra.getVisita().getOidvisita(), getAdministrarObraNew().getObra().getIntcodigoobra()));
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/Reportes");

        } catch (IOException ex) {
            Logger.getLogger(AdminSupervisionExterna.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
/**
 * Metodo utilizado para calcular La localidad por Grados, Minutos y Segundos.
 */
    public void calcularUbicacionGradosMinutosSegundo() {
        BigDecimal minseg = new BigDecimal(60);
        BigDecimal longitud = getAdministrarObraNew().getObra().getFloatlongitud();
        BigDecimal latitud = getAdministrarObraNew().getObra().getFloatlatitud();
        BigDecimal grados_longitud, grados_latitud;
        BigDecimal minutos_longitud, minutos_latitud;
        BigDecimal seg_longitud, seg_latitud;

        grados_longitud = longitud.setScale(0, RoundingMode.DOWN);
        minutos_longitud = ((longitud.subtract(grados_longitud)).multiply(minseg)).setScale(0, RoundingMode.DOWN);
        seg_longitud = minseg.multiply((longitud.subtract(grados_longitud).subtract(((latitud.subtract(grados_longitud)).multiply(minseg)).divide(minseg)))).multiply(minseg).setScale(0, RoundingMode.DOWN);
        grados_latitud = latitud.setScale(0, RoundingMode.DOWN);
        minutos_latitud = ((latitud.subtract(grados_latitud)).multiply(minseg)).setScale(0, RoundingMode.DOWN);
        seg_latitud = minseg.multiply((latitud.subtract(grados_latitud).subtract(((latitud.subtract(grados_latitud)).multiply(minseg)).divide(minseg)))).multiply(minseg).setScale(0, RoundingMode.DOWN);

        if (grados_longitud.compareTo(BigDecimal.ONE) < 0) {
            strlocalidadlogitud = "W  " + grados_longitud.multiply(new BigDecimal(-1)) + "°  " + minutos_longitud.multiply(new BigDecimal(-1)) + "'  " + seg_longitud.multiply(new BigDecimal(-1)) + "''";

        } else {
            strlocalidadlogitud = "E  " + grados_longitud + "° " + minutos_longitud + "'  " + seg_longitud + "'' ";
        }

        if (grados_latitud.compareTo(BigDecimal.ONE) < 0) {
            strlocalidadlatitud = "S " + grados_latitud.multiply(new BigDecimal(-1)) + "°  " + minutos_longitud.multiply(new BigDecimal(-1)) + "'  " + seg_latitud.multiply(new BigDecimal(-1)) + " ''";

        } else {
            strlocalidadlatitud = "N " + grados_latitud + "° " + minutos_latitud + "'  " + seg_latitud + "''";
        }

    }
    
    public void tipoImpactoSocialDetalle (){       
        
        if (!listimpactosocialdetalle.isEmpty()){
            for (Tipoimpactosocial imp : listimpactosocialdetalle){
                if (imp.getStrnombrecolumna().equals("empleos directos")){                    
                    setStrempleosdirectosdetalle(imp.getStrdescripcionimpacto());
            }else if (imp.getStrnombrecolumna().equals("empleos indirectos")){
                setStrempleosindirectosdetalle(imp.getStrdescripcionimpacto());
            }else if (imp.getStrnombrecolumna().equals("habitantes beneficiados")){
                setStrbeneficiariosdetalle(imp.getStrdescripcionimpacto());
            }
                
            }
        } else {
            setStrbeneficiariosdetalle(bundle.getString("personasbeneficiadas"));
            setStrempleosdirectosdetalle(bundle.getString("empleosdirectos"));
            setStrempleosindirectosdetalle(bundle.getString("empleosindirectos"));
        }
        
    }
}
