/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.SolicitudObra;

import atencionHumanitaria.service.AtencionHumanitariaServiceAble;
import co.com.interkont.cobra.to.Claseobra;
import co.com.interkont.cobra.to.Documentosolicitud;
import co.com.interkont.cobra.to.Estadodocumentacion;
import co.com.interkont.cobra.to.Estadosolicitudch;
import co.com.interkont.cobra.to.Evento;
import co.com.interkont.cobra.to.Fase;
import co.com.interkont.cobra.to.Imagensolicitud;
import co.com.interkont.cobra.to.JsfUsuario;
import co.com.interkont.cobra.to.Localidad;
import co.com.interkont.cobra.to.Lugarobra;
import co.com.interkont.cobra.to.Obra;
import co.com.interkont.cobra.to.Periodoevento;
import co.com.interkont.cobra.to.Periodomedida;
import co.com.interkont.cobra.to.SolicitudObrach;
import co.com.interkont.cobra.to.SolicitudObrachTemp;
import co.com.interkont.cobra.to.Subestadosolicitudch;
import co.com.interkont.cobra.to.Tercero;
import co.com.interkont.cobra.to.Tipocosto;
import co.com.interkont.cobra.to.Tipodocumentosolicitud;
import co.com.interkont.cobra.to.Tipoestadobra;
import co.com.interkont.cobra.to.Tipoobra;
import co.com.interkont.cobra.to.Tipoordendepago;
import co.com.interkont.cobra.to.Tipoorigen;
import co.com.interkont.cobra.to.Tipoproyecto;
import co.com.interkont.cobra.to.Tiposolicitudobra;
import co.com.interkont.cobra.to.Urgencia;
import cobra.Archivo;
import cobra.FiltroObra;

import cobra.SessionBeanCobra;
import cobra.SubirArchivoBean;
import cobra.Supervisor.AdministrarObraNew;
import cobra.Supervisor.FacesUtils;
import cobra.Supervisor.IngresarNuevaObra;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ResourceBundle;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import org.richfaces.component.UIDataTable;

/**
 *
 * @author David Andrés Betancourth Botero
 */
public class GestionarSolicitudObra  {

    private int eventovalue;
    private int valuegrupo;
    private double porpago1 = 0;
    private double porpago2 = 0;
    private double porpago3 = 0;
    private BigDecimal value1;
    private BigDecimal value2;
    private BigDecimal value3;
    private long valueestadosolich;
    private String codDepartamento;
    private String codDepartamentoFiltro;
    private String codMunicipio;
    private String pathActa;
    private boolean deshabilitarmunicipios = false;
    private boolean deshabilitardepartamentos = false;
    private boolean verdepartamento = false;
    private boolean guarda = false;
    private boolean consul = false;
    private boolean mostrarmov = false;
    private SelectItem[] departamento;
    private SelectItem[] municipio;
    private SelectItem[] departamentofiltro;
    private SelectItem[] municipiofiltro;
    private SelectItem[] evento;
    private SelectItem[] periodoevento;
    private SelectItem[] tiposolicitudobra;
    private SelectItem[] tipoobra;
    private SelectItem[] tipodocumento;
    private SelectItem[] estadodocumentacion;
    private SelectItem[] estadosolicitudch;
    private SelectItem[] subestadosolicitudch;
    private SelectItem[] urgencia;
    private SelectItem[] prioridad;
    private SelectItem[] tipoordenpago;
    private boolean verultimosreales;
    private boolean veranteriorreales;
    private int totalfilas = 0;
    private int totalpaginas = 0;
    private int pagina = 0;
    private boolean aplicafiltro;
    private boolean verultimosrealesobra;
    private boolean veranteriorrealesobra;
    private int totalfilasobra = 0;
    private int totalpaginasobra = 0;
    private int paginaobra = 0;
    private boolean aplicafiltroobra;
    private int totalSolApro;
    private int totalSolUsu;
    private SubirArchivoBean subirImagenSolicitud = new SubirArchivoBean(1, true, false);
    private SubirArchivoBean subirDocumentoSolicitud = new SubirArchivoBean(1, true, false);
    private SubirArchivoBean subirActa = new SubirArchivoBean(1, true, false);
    private ResourceBundle bundle = getSessionBeanCobra().getBundle();
    private UIDataTable tablaSolicitudes = new UIDataTable();
    private UIDataTable tablaSolicitudesObra = new UIDataTable();
    private UIDataTable tablaImagenessolicitud = new UIDataTable();
    private UIDataTable tablaProyectosAso = new UIDataTable();
    private UIDataTable tablaMovimientos = new UIDataTable();
    private String pathImagen = "";
    private String mensaje;
    private int tipodoc;
    private boolean regsolobra;
    private FiltroObra filtro = new FiltroObra();

    public FiltroObra getFiltro() {
        return filtro;
    }

    public void setFiltro(FiltroObra filtro) {
        this.filtro = filtro;
    }

    public SelectItem[] getDepartamentofiltro() {
        return departamentofiltro;
    }

    public void setDepartamentofiltro(SelectItem[] departamentofiltro) {
        this.departamentofiltro = departamentofiltro;
    }

    public SelectItem[] getMunicipiofiltro() {
        return municipiofiltro;
    }

    public void setMunicipiofiltro(SelectItem[] municipiofiltro) {
        this.municipiofiltro = municipiofiltro;
    }

    public UIDataTable getTablaMovimientos() {
        return tablaMovimientos;
    }

    public void setTablaMovimientos(UIDataTable tablaMovimientos) {
        this.tablaMovimientos = tablaMovimientos;
    }

    public boolean isMostrarmov() {
        return mostrarmov;
    }

    public void setMostrarmov(boolean mostrarmov) {
        this.mostrarmov = mostrarmov;
    }

    public boolean isRegsolobra() {
        return regsolobra;
    }

    public void setRegsolobra(boolean regsolobra) {
        this.regsolobra = regsolobra;
    }

    public UIDataTable getTablaProyectosAso() {
        return tablaProyectosAso;
    }

    public void setTablaProyectosAso(UIDataTable tablaProyectosAso) {
        this.tablaProyectosAso = tablaProyectosAso;
    }

    public boolean isConsul() {
        return consul;
    }

    public void setConsul(boolean consul) {
        this.consul = consul;
    }

    public SubirArchivoBean getSubirActa() {
        return subirActa;
    }

    public void setSubirActa(SubirArchivoBean subirActa) {
        this.subirActa = subirActa;
    }

    public SelectItem[] getTipoordenpago() {
        return tipoordenpago;
    }

    public void setTipoordenpago(SelectItem[] tipoordenpago) {
        this.tipoordenpago = tipoordenpago;
    }

    public String getCodDepartamentoFiltro() {
        return codDepartamentoFiltro;
    }

    public void setCodDepartamentoFiltro(String codDepartamentoFiltro) {
        this.codDepartamentoFiltro = codDepartamentoFiltro;
    }

    public int getTipodoc() {
        return tipodoc;
    }

    public void setTipodoc(int tipodoc) {
        this.tipodoc = tipodoc;
    }

    public SubirArchivoBean getSubirDocumentoSolicitud() {
        return subirDocumentoSolicitud;
    }

    public void setSubirDocumentoSolicitud(SubirArchivoBean subirDocumentoSolicitud) {
        this.subirDocumentoSolicitud = subirDocumentoSolicitud;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getPathImagen() {
        return pathImagen;
    }

    public void setPathImagen(String pathImagen) {
        this.pathImagen = pathImagen;
    }

    public UIDataTable getTablaImagenessolicitud() {
        return tablaImagenessolicitud;
    }

    public void setTablaImagenessolicitud(UIDataTable tablaImagenessolicitud) {
        this.tablaImagenessolicitud = tablaImagenessolicitud;
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public SubirArchivoBean getSubirImagenSolicitud() {
        return subirImagenSolicitud;
    }

    public void setSubirImagenSolicitud(SubirArchivoBean subirImagenSolicitud) {
        this.subirImagenSolicitud = subirImagenSolicitud;
    }

    public int getTotalSolApro() {
        return totalSolApro;
    }

    public void setTotalSolApro(int totalSolApro) {
        this.totalSolApro = totalSolApro;
    }

    public int getTotalSolUsu() {
        return totalSolUsu;
    }

    public void setTotalSolUsu(int totalSolUsu) {
        this.totalSolUsu = totalSolUsu;
    }

    public boolean isDeshabilitardepartamentos() {
        return deshabilitardepartamentos;
    }

    public void setDeshabilitardepartamentos(boolean deshabilitardepartamentos) {
        this.deshabilitardepartamentos = deshabilitardepartamentos;
    }

    public boolean isAplicafiltroobra() {
        return aplicafiltroobra;
    }

    public void setAplicafiltroobra(boolean aplicafiltroobra) {
        this.aplicafiltroobra = aplicafiltroobra;
    }

    public int getPaginaobra() {
        return paginaobra;
    }

    public void setPaginaobra(int paginaobra) {
        this.paginaobra = paginaobra;
    }

    public int getTotalfilasobra() {
        return totalfilasobra;
    }

    public void setTotalfilasobra(int totalfilasobra) {
        this.totalfilasobra = totalfilasobra;
    }

    public int getTotalpaginasobra() {
        return totalpaginasobra;
    }

    public void setTotalpaginasobra(int totalpaginasobra) {
        this.totalpaginasobra = totalpaginasobra;
    }

    public boolean isVeranteriorrealesobra() {
        return veranteriorrealesobra;
    }

    public void setVeranteriorrealesobra(boolean veranteriorrealesobra) {
        this.veranteriorrealesobra = veranteriorrealesobra;
    }

    public boolean isVerultimosrealesobra() {
        return verultimosrealesobra;
    }

    public void setVerultimosrealesobra(boolean verultimosrealesobra) {
        this.verultimosrealesobra = verultimosrealesobra;
    }

    public boolean isAplicafiltro() {
        return aplicafiltro;
    }

    public void setAplicafiltro(boolean aplicafiltro) {
        this.aplicafiltro = aplicafiltro;
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

    public boolean isVeranteriorreales() {
        return veranteriorreales;
    }

    public void setVeranteriorreales(boolean veranteriorreales) {
        this.veranteriorreales = veranteriorreales;
    }

    public boolean isVerultimosreales() {
        return verultimosreales;
    }

    public void setVerultimosreales(boolean verultimosreales) {
        this.verultimosreales = verultimosreales;
    }

    public BigDecimal getValue1() {
        return value1;
    }

    public void setValue1(BigDecimal value1) {
        this.value1 = value1;
    }

    public BigDecimal getValue2() {
        return value2;
    }

    public void setValue2(BigDecimal value2) {
        this.value2 = value2;
    }

    public BigDecimal getValue3() {
        return value3;
    }

    public void setValue3(BigDecimal value3) {
        this.value3 = value3;
    }

    public double getPorpago1() {
        return porpago1;
    }

    public void setPorpago1(double porpago1) {
        this.porpago1 = porpago1;
    }

    public double getPorpago2() {
        return porpago2;
    }

    public void setPorpago2(double porpago2) {
        this.porpago2 = porpago2;
    }

    public double getPorpago3() {
        return porpago3;
    }

    public void setPorpago3(double porpago3) {
        this.porpago3 = porpago3;
    }

    public boolean isGuarda() {
        return guarda;
    }

    public void setGuarda(boolean guarda) {
        this.guarda = guarda;
    }

    public UIDataTable getTablaSolicitudesObra() {
        return tablaSolicitudesObra;
    }

    public void setTablaSolicitudesObra(UIDataTable tablaSolicitudesObra) {
        this.tablaSolicitudesObra = tablaSolicitudesObra;
    }

    public int getValuegrupo() {
        return valuegrupo;
    }

    public void setValuegrupo(int valuegrupo) {
        this.valuegrupo = valuegrupo;
    }

    public UIDataTable getTablaSolicitudes() {
        return tablaSolicitudes;
    }

    public void setTablaSolicitudes(UIDataTable tablaSolicitudes) {
        this.tablaSolicitudes = tablaSolicitudes;
    }

    public String getCodMunicipio() {
        return codMunicipio;
    }

    public void setCodMunicipio(String codMunicipio) {
        this.codMunicipio = codMunicipio;
    }

    public boolean isVerdepartamento() {
        return verdepartamento;
    }

    public void setVerdepartamento(boolean verdepartamento) {
        this.verdepartamento = verdepartamento;
    }

    public boolean isDeshabilitarmunicipios() {
        return deshabilitarmunicipios;
    }

    public void setDeshabilitarmunicipios(boolean deshabilitarmunicipios) {
        this.deshabilitarmunicipios = deshabilitarmunicipios;
    }

    public SelectItem[] getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(SelectItem[] prioridad) {
        this.prioridad = prioridad;
    }

    public SelectItem[] getUrgencia() {
        return urgencia;
    }

    public void setUrgencia(SelectItem[] urgencia) {
        this.urgencia = urgencia;
    }

    public long getValueestadosolich() {
        return valueestadosolich;
    }

    public void setValueestadosolich(long valueestadosolich) {
        this.valueestadosolich = valueestadosolich;
    }

    public SelectItem[] getEstadosolicitudch() {
        return estadosolicitudch;
    }

    public void setEstadosolicitudch(SelectItem[] estadosolicitudch) {
        this.estadosolicitudch = estadosolicitudch;
    }

    public SelectItem[] getSubestadosolicitudch() {
        return subestadosolicitudch;
    }

    public void setSubestadosolicitudch(SelectItem[] subestadosolicitudch) {
        this.subestadosolicitudch = subestadosolicitudch;
    }

    public SelectItem[] getEstadodocumentacion() {
        return estadodocumentacion;
    }

    public void setEstadodocumentacion(SelectItem[] estadodocumentacion) {
        this.estadodocumentacion = estadodocumentacion;
    }

    public SelectItem[] getTipodocumento() {
        return tipodocumento;
    }

    public void setTipodocumento(SelectItem[] tipodocumento) {
        this.tipodocumento = tipodocumento;
    }

    public SelectItem[] getTipoobra() {
        return tipoobra;
    }

    public void setTipoobra(SelectItem[] tipoobra) {
        this.tipoobra = tipoobra;
    }

    public SelectItem[] getTiposolicitudobra() {
        return tiposolicitudobra;
    }

    public void setTiposolicitudobra(SelectItem[] tiposolicitudobra) {
        this.tiposolicitudobra = tiposolicitudobra;
    }

    public SelectItem[] getEvento() {
        return evento;
    }

    public void setEvento(SelectItem[] evento) {
        this.evento = evento;
    }

    public int getEventovalue() {
        return eventovalue;
    }

    public void setEventovalue(int eventovalue) {
        this.eventovalue = eventovalue;
    }

    public SelectItem[] getPeriodoevento() {
        return periodoevento;
    }

    public void setPeriodoevento(SelectItem[] periodoevento) {
        this.periodoevento = periodoevento;
    }

    public String getCodDepartamento() {
        return codDepartamento;
    }

    public void setCodDepartamento(String codDepartamento) {
        this.codDepartamento = codDepartamento;
    }

    public SelectItem[] getMunicipio() {
        return municipio;
    }

    public void setMunicipio(SelectItem[] municipio) {
        this.municipio = municipio;
    }

    public SelectItem[] getDepartamento() {
        return departamento;
    }

    public void setDepartamento(SelectItem[] departamento) {
        this.departamento = departamento;
    }
    
    public AtencionHumanitariaServiceAble getAtencionHumanitaria() {

        return getSessionBeanCobra().getAtencionhumanitariaService();
    }

    public GestionarSolicitudObra() {
        resumenSoli();
        inicializarvariables();
        getSessionBeanCobra().getSolicitudService().setJsfusuariogrupo(getSessionBeanCobra().getUsuarioService().
                encontrarGrupoxUsuarioxModulo(getSessionBeanCobra().getUsuarioObra().getUsuId(), 2));


//        getSessionBeanCobra().getSolicitudService().setEntidad(getSessionBeanCobra().getCobraService().
//                encontrarEntidadesxtercero(getSessionBeanCobra().getUsuarioObra().getTercero().getIntcodigo()).get(0));
//setTablasver variable que me determina si es solicitud temporal o solicitud aprobada
        switch (getSessionBeanCobra().getSolicitudService().getJsfusuariogrupo().getGrupo().getGruGid()) {
            case 5:
                valuegrupo = 5;
                llenadodecombos();
                verificarGoberAlcalde();
                guarda = false;
                getSessionBeanCobra().getSolicitudService().setTablasver(true);
                break;
            case 6:
                llenadodecombos();
                valuegrupo = 6;
                primerosreales();
                guarda = false;
                getSessionBeanCobra().getSolicitudService().setTablasver(true);
                break;
            case 7:
                llenadodecombos();
                valuegrupo = 7;
                primerosreales();
                primerosrealesobras();
                guarda = false;
                getSessionBeanCobra().getSolicitudService().setTablasver(false);
                break;
            case 8:
                llenadodecombos();
                valuegrupo = 8;
                primerosrealesobras();
                guarda = false;
                getSessionBeanCobra().getSolicitudService().setTablasver(false);
                break;
            case 9:
                llenadodecombos();
                valuegrupo = 9;
                primerosrealesobras();
                guarda = false;
                getSessionBeanCobra().getSolicitudService().setTablasver(false);
                break;
        }
    }

    public void tablaPentiente() {
        getSessionBeanCobra().getSolicitudService().setTablasver(true);
    }

    public void tablaAprobadas() {
        getSessionBeanCobra().getSolicitudService().setTablasver(false);
    }

    public void resumenSoli() {
        totalSolApro = getSessionBeanCobra().getSolicitudService().totalaprobadas();
        totalSolUsu = getSessionBeanCobra().getSolicitudService().totalsolicitudesUsu(getSessionBeanCobra().getUsuarioObra().getUsuId());
    }

    public void llenadodecombos() {
        iniciarfiltro();
        llenarDepartamento();
        if (!verdepartamento) {
            llenarMunicipios();
        }
        llenarEvento();
        llenarPeriodoEvento();
        llenarTiposolicitudObra();
        llenarTipoobras();
        llenarTipodocumentos();
        llenarEstadodocumentacion();
        llenarEstadosoli();
        llenarSubestadosolicitud();
        llenarUrgencia();
    }

    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }

    public String llenarDepartamento() {
        getSessionBeanCobra().getSolicitudService().setDepartamentos(getSessionBeanCobra().getAtencionhumanitariaService().encontrarDepartamentos());
        departamento = new SelectItem[getSessionBeanCobra().getSolicitudService().getDepartamentos().size()];
        int i = 0;
        for (Localidad depto : getSessionBeanCobra().getSolicitudService().getDepartamentos()) {
            SelectItem dep = new SelectItem(depto.getStrcodigolocalidad(), depto.getStrdepartamento());
            if (i == 0) {
                codDepartamento = depto.getStrcodigolocalidad();
            }
            departamento[i++] = dep;
        }
        return null;
    }

    public String llenarMunicipios() {
        getSessionBeanCobra().getSolicitudService().setMunicipios(getSessionBeanCobra().getAtencionhumanitariaService().encontrarMunicipios(codDepartamento));
        municipio = new SelectItem[getSessionBeanCobra().getSolicitudService().getMunicipios().size()];
        int i = 0;
        for (Localidad muni : getSessionBeanCobra().getSolicitudService().getMunicipios()) {
            SelectItem mun = new SelectItem(muni.getStrcodigolocalidad(), muni.getStrmunicipio());
            municipio[i++] = mun;
        }
        return null;
    }

    public String llenarDepartamentoFiltro() {
        getSessionBeanCobra().getSolicitudService().setDepartamentosfl(getSessionBeanCobra().getAtencionhumanitariaService().encontrarDepartamentos());
        departamentofiltro = new SelectItem[getSessionBeanCobra().getSolicitudService().getDepartamentos().size()+1];
        int i = 1;
        departamentofiltro[0]= new SelectItem("0", "Todos");
        filtro.setStrcoddepto("0");
        for (Localidad depto : getSessionBeanCobra().getSolicitudService().getDepartamentos()) {
            SelectItem dep = new SelectItem(depto.getStrcodigolocalidad(), depto.getStrdepartamento());
//            if (i == 0) {
//                filtro.setStrcoddepto(depto.getStrcodigolocalidad());
//            }
            departamentofiltro[i++] = dep;
        }


        return null;
    }

    public String llenarMunicipiosFiltro() {
        getSessionBeanCobra().getSolicitudService().setMunicipiosfl(getSessionBeanCobra().getAtencionhumanitariaService().encontrarMunicipios(filtro.getStrcoddepto()));
        municipiofiltro = new SelectItem[getSessionBeanCobra().getSolicitudService().getMunicipiosfl().size()];
        int i = 0;
        for (Localidad muni : getSessionBeanCobra().getSolicitudService().getMunicipiosfl()) {
            SelectItem mun = new SelectItem(muni.getStrcodigolocalidad(), muni.getStrmunicipio());
            municipiofiltro[i++] = mun;
        }
        return null;
    }

    public String llenarEstadosoli() {
        getSessionBeanCobra().getSolicitudService().setEstadosolicitudchs(getSessionBeanCobra().getSolicitudService().encontrarEstadosolicitudchs());
        estadosolicitudch = new SelectItem[getSessionBeanCobra().getSolicitudService().getEstadosolicitudchs().size()];
        int i = 0;
        for (Estadosolicitudch estadosoli : getSessionBeanCobra().getSolicitudService().getEstadosolicitudchs()) {
            SelectItem estadosol = new SelectItem(estadosoli.getOidestadosolicitud(), estadosoli.getStrnombreestadosolicitud());
            if (i == 0) {
                valueestadosolich = estadosoli.getOidestadosolicitud();
            }
            estadosolicitudch[i++] = estadosol;
        }
        return null;
    }

    public String llenarSubestadosolicitud() {
        getSessionBeanCobra().getSolicitudService().setSubestadosolicitudchs(getSessionBeanCobra().getAtencionhumanitariaService().encontrarSubestadosol(valueestadosolich));
        subestadosolicitudch = new SelectItem[getSessionBeanCobra().getSolicitudService().getSubestadosolicitudchs().size()];
        int i = 0;
        for (Subestadosolicitudch subestado : getSessionBeanCobra().getSolicitudService().getSubestadosolicitudchs()) {
            SelectItem subest = new SelectItem(subestado.getIntoidsubestadosolicitud(), subestado.getStrdescripcion());
            subestadosolicitudch[i++] = subest;
        }
        return null;
    }

    public String llenarEvento() {
        getSessionBeanCobra().getSolicitudService().setEvento(getSessionBeanCobra().getAtencionhumanitariaService().encontrarEvento());
        evento = new SelectItem[getSessionBeanCobra().getSolicitudService().getEvento().size()];
        int i = 0;
        for (Evento event : getSessionBeanCobra().getSolicitudService().getEvento()) {
            SelectItem even = new SelectItem(event.getIntidevento(), event.getStrdescripcion());
            if (i == 0) {
                eventovalue = event.getIntidevento();
            }
            evento[i++] = even;
        }
        return null;
    }

    public String llenarTiposolicitudObra() {
        getSessionBeanCobra().getSolicitudService().setTiposolicitudobras(getSessionBeanCobra().getSolicitudService().encontrarTiposolicitudobras());
        tiposolicitudobra = new SelectItem[getSessionBeanCobra().getSolicitudService().getTiposolicitudobras().size()];
        int i = 0;
        for (Tiposolicitudobra tiposoli : getSessionBeanCobra().getSolicitudService().getTiposolicitudobras()) {
            SelectItem tiposo = new SelectItem(tiposoli.getInttiposolicitud(), tiposoli.getStrdescripcion());
            tiposolicitudobra[i++] = tiposo;
        }
        return null;
    }

    public String llenarTipoobras() {
        getSessionBeanCobra().getSolicitudService().setTipoobras(getSessionBeanCobra().getSolicitudService().encontrarTipoobras());
        tipoobra = new SelectItem[getSessionBeanCobra().getSolicitudService().getTipoobras().size()];
        int i = 0;
        for (Tipoobra tipoob : getSessionBeanCobra().getSolicitudService().getTipoobras()) {
            SelectItem tipoo = new SelectItem(tipoob.getInttipoobra(), tipoob.getStrdesctipoobra());
            tipoobra[i++] = tipoo;
        }
        return null;
    }

    public String llenarTipodocumentos() {
        getSessionBeanCobra().getSolicitudService().setTipodocumentosolicituds(getSessionBeanCobra().getSolicitudService().encontrarTipodocumentosolicituds());
        tipodocumento = new SelectItem[getSessionBeanCobra().getSolicitudService().getTipodocumentosolicituds().size()];
        int i = 0;
        for (Tipodocumentosolicitud tipodoc : getSessionBeanCobra().getSolicitudService().getTipodocumentosolicituds()) {
            SelectItem tipod = new SelectItem(tipodoc.getInttipodocsol(), tipodoc.getStrdesctipodocsol());
            tipodocumento[i++] = tipod;
        }
        return null;
    }

    public String llenarEstadodocumentacion() {
        getSessionBeanCobra().getSolicitudService().setEstadodocumentacions(getSessionBeanCobra().getSolicitudService().encontrarEstadodocumentacions());
        estadodocumentacion = new SelectItem[getSessionBeanCobra().getSolicitudService().getEstadodocumentacions().size()];
        int i = 0;
        for (Estadodocumentacion estadodoc : getSessionBeanCobra().getSolicitudService().getEstadodocumentacions()) {
            SelectItem estadodo = new SelectItem(estadodoc.getIntestadodocumentacion(), estadodoc.getStrdescripcion());
            estadodocumentacion[i++] = estadodo;
        }
        return null;
    }

    public String llenarUrgencia() {
        getSessionBeanCobra().getSolicitudService().setUrgencia(getSessionBeanCobra().getSolicitudService().encontrarUrgencia());
        urgencia = new SelectItem[getSessionBeanCobra().getSolicitudService().getUrgencia().size()];
        int i = 0;
        for (Urgencia urgen : getSessionBeanCobra().getSolicitudService().getUrgencia()) {
            SelectItem urge = new SelectItem(urgen.getIntidurgencia(), urgen.getStrdescripcion());
            urgencia[i++] = urge;
        }
        return null;
    }

    public String llenarTipoOrdenPago() {
        getSessionBeanCobra().getSolicitudService().setTipoordendepagos(getSessionBeanCobra().getSolicitudService().encontrarTipoOrdenapgo());
        tipoordenpago = new SelectItem[getSessionBeanCobra().getSolicitudService().getTipoordendepagos().size()];
        int i = 0;
        for (Tipoordendepago tipoo : getSessionBeanCobra().getSolicitudService().getTipoordendepagos()) {
            SelectItem tipoor = new SelectItem(tipoo.getOidcodigotipoordenpago(), tipoo.getStrnombre());
            tipoordenpago[i++] = tipoor;
        }
        return null;
    }

    public String llenarPeriodoEvento() {
        getSessionBeanCobra().getSolicitudService().setPeriodoevento(getSessionBeanCobra().getAtencionhumanitariaService().encontrarPeriodoEvento(eventovalue));
        periodoevento = new SelectItem[getSessionBeanCobra().getSolicitudService().getPeriodoevento().size()];
        int i = 0;
        for (Periodoevento perieven : getSessionBeanCobra().getSolicitudService().getPeriodoevento()) {
            SelectItem perev = new SelectItem(perieven.getIntidperiodoevento(), perieven.getStrdescripcion());
            periodoevento[i++] = perev;
        }
        return null;
    }

    public String CrearSolicitud() {

        Date fecha = new Date();
        Periodoevento periodoeven = getAtencionHumanitaria().encontrarperiodoevento();

        if (fecha.after(periodoeven.getDatefechainiobra()) && fecha.before(periodoeven.getDatefechafinobra())) {

            regsolobra = true;
            inicializarvariables();
            return "crearSolicitud";
        } else {

            regsolobra = false;
            FacesUtils.addErrorMessage("Se encuentra por fuera de las fechas para solicitar");
            return null;
        }


    }

    public void inicializarvariables() {
        getSessionBeanCobra().getSolicitudService().getSolicitudObrach().setTipoobra(new Tipoobra());
        getSessionBeanCobra().getSolicitudService().getSolicitudObrach().setTiposolicitudobra(new Tiposolicitudobra());
        getSessionBeanCobra().getSolicitudService().getSolicitudObrach().setSubestadosolicitudch(new Subestadosolicitudch());
        getSessionBeanCobra().getSolicitudService().getSolicitudObrach().setEstadosolicitudch(new Estadosolicitudch());
        getSessionBeanCobra().getSolicitudService().getSolicitudObrach().setEstadodocumentacion(new Estadodocumentacion());
        getSessionBeanCobra().getSolicitudService().getSolicitudObrach().setUrgencia(new Urgencia());
        getSessionBeanCobra().getSolicitudService().getSolicitudObrach().setLocalidad(new Localidad());
        getSessionBeanCobra().getSolicitudService().getSolicitudObrach().getIntserial_temp().clear();
        getSessionBeanCobra().getSolicitudService().getOrdendepago().setTipoordendepago(new Tipoordendepago());
        getSessionBeanCobra().getSolicitudService().getImagensolicituds().clear();
        getSessionBeanCobra().getSolicitudService().setImagensolicitud(new Imagensolicitud());
        getSessionBeanCobra().getSolicitudService().getDocumentosolicituds().clear();
        getSessionBeanCobra().getSolicitudService().setDocumentosolicitud(new Documentosolicitud());
        getSessionBeanCobra().getSolicitudService().getDocumentosolicitud().setTipodocumentosolicitud(new Tipodocumentosolicitud());
        getSessionBeanCobra().getSolicitudService().setTipodocumentosolicitud(new Tipodocumentosolicitud());
        getSessionBeanCobra().getSolicitudService().getSolicitudObrach().setDoublevaloraprobado(BigDecimal.valueOf(0));
        guarda = false;
    }

    public String identificarentidad() {
        getSessionBeanCobra().getSolicitudService().setEntidad(getSessionBeanCobra().getSolicitudService().buscarTerceroxEntidad(getSessionBeanCobra().getUsuarioObra().getTercero().getIntcodigo()));
        return null;
    }

    public String validargurdado() {
        if (!getSessionBeanCobra().getSolicitudService().getSolicitudObrach().getDatefecharecibido().equals(null)
                || !getSessionBeanCobra().getSolicitudService().getSolicitudObrach().getStrnombresolicitud().equals("")
                || !getSessionBeanCobra().getSolicitudService().getSolicitudObrach().getTextobservaciones().equals("")
                || !getSessionBeanCobra().getSolicitudService().getSolicitudObrach().getDoublevalorsolicitado().equals(null)) {
            guarda = true;
            guardarSolicitud();
        } else {
            guarda = false;
            getSessionBeanCobra().getSolicitudService().setMensaje("diligencie bn los campos");
        }
        return null;
    }

    public String validaraprobacion() {
        if (!getSessionBeanCobra().getSolicitudService().getSolicitudObrach().getTextjustifica().equals("")
                || !getSessionBeanCobra().getSolicitudService().getSolicitudObrach().getDatefechajunta().equals(null)) {
            guarda = true;
            guardarAprobacion();
        } else {
            guarda = false;
            getSessionBeanCobra().getSolicitudService().setMensaje("diligencie bn los campos");
        }
        return null;
    }

    public String guardarAprobacion() {
        if (guarda) {
            getSessionBeanCobra().getSolicitudService().getSolicitudObrach().setEstadosolicitudch(new Estadosolicitudch(valueestadosolich, ""));
            if (getSessionBeanCobra().getSolicitudService().getDocumentosolicituds().size() > 0) {
                getSessionBeanCobra().getSolicitudService().getSolicitudObrach().setIntserialTempDoc(new HashSet(getSessionBeanCobra().getSolicitudService().getDocumentosolicituds()));
            }
            getSessionBeanCobra().getSolicitudService().guardarSolicitudObrachTemp(getSessionBeanCobra().getSolicitudService().getSolicitudObrach());
            getSessionBeanCobra().getSolicitudService().setMensaje("Guardo");
        } else {
            getSessionBeanCobra().getSolicitudService().setMensaje("No Guardo");
        }

        return null;
    }

    public String guardarSolicitud() {
        if (guarda) {
            validardocumentacion();
            getSessionBeanCobra().getSolicitudService().getSolicitudObrach().setBoolestaenvisor(false);
            getSessionBeanCobra().getSolicitudService().getSolicitudObrach().setBoolnuevo(true);
            getSessionBeanCobra().getSolicitudService().getSolicitudObrach().setBooleliminado(false);
            getSessionBeanCobra().getSolicitudService().getSolicitudObrach().setBoolmodificado(false);
            getSessionBeanCobra().getSolicitudService().getSolicitudObrach().setTercero(getSessionBeanCobra().getUsuarioObra().getTercero());
            getSessionBeanCobra().getSolicitudService().getSolicitudObrach().setEstadosolicitudch(new Estadosolicitudch(2, ""));
            getSessionBeanCobra().getSolicitudService().getSolicitudObrach().setSubestadosolicitudch(new Subestadosolicitudch(4, null, null));
            getSessionBeanCobra().getSolicitudService().getSolicitudObrach().setEstadodocumentacion(new Estadodocumentacion(2));
            getSessionBeanCobra().getSolicitudService().getSolicitudObrach().setUrgencia(new Urgencia(1));
            getSessionBeanCobra().getSolicitudService().getSolicitudObrach().setStrconsecutivo(getSessionBeanCobra().getSolicitudService().getSolicitudObrach().getCodresumido());
            if (getSessionBeanCobra().getSolicitudService().getImagensolicituds().size() > 0) {
                getSessionBeanCobra().getSolicitudService().getSolicitudObrach().getIntserial_temp().addAll(getSessionBeanCobra().getSolicitudService().getImagensolicituds());
            }
            if (getSessionBeanCobra().getSolicitudService().getDocumentosolicituds().size() > 0) {
                getSessionBeanCobra().getSolicitudService().getSolicitudObrach().setIntserialTempDoc(new HashSet(getSessionBeanCobra().getSolicitudService().getDocumentosolicituds()));
            }
            if (!verdepartamento) {
                getSessionBeanCobra().getSolicitudService().getSolicitudObrach().setLocalidad(new Localidad(getSessionBeanCobra().getUsuarioObra().getTercero().getLocalidadByStrcodigolocalidad().getStrcodigolocalidad()));
            }
            getSessionBeanCobra().getSolicitudService().getSolicitudObrach().setJsfUsuarioByIntreviso(new JsfUsuario(getSessionBeanCobra().getUsuarioObra().getUsuId(), "", ""));
            getSessionBeanCobra().getSolicitudService().guardarSolicitudObrachTemp(getSessionBeanCobra().getSolicitudService().getSolicitudObrach());

            getSessionBeanCobra().getSolicitudService().setMensaje("Guardo");
        } else {
            getSessionBeanCobra().getSolicitudService().setMensaje("No Guardo");
        }
        return null;
    }

    public String validardirectinfra() {
        if (getSessionBeanCobra().getSolicitudService().getSolicitudObrach().getBoolaprobado()) {
            if (getSessionBeanCobra().getSolicitudService().getSolicitudObrach().isBoolnuevo()) {
                if (getValidarpagos()) {
                    guarda = true;
                    getSessionBeanCobra().getSolicitudService().getSolicitudObrach().setDatefechacompleto(new Date());
                    getSessionBeanCobra().getSolicitudService().getSolicitudObrach().setBoolnuevo(false);
                    if (getSessionBeanCobra().getSolicitudService().getDocumentosolicituds().size() > 0) {
                        getSessionBeanCobra().getSolicitudService().getSolicitudObrach().setIntserialTempDoc(new HashSet(getSessionBeanCobra().getSolicitudService().getDocumentosolicituds()));
                    }
                    getSessionBeanCobra().getSolicitudService().guardarSolicitudObrachTemp(getSessionBeanCobra().getSolicitudService().getSolicitudObrach());
                    if (getSessionBeanCobra().getSolicitudService().getDocumentosolicituds().size() > 0) {
                        getSessionBeanCobra().getSolicitudService().getSolicitudObrachD().setDocumentosolicituds(new HashSet(getSessionBeanCobra().getSolicitudService().getDocumentosolicituds()));
                    }
                    getSessionBeanCobra().getSolicitudService().guardarSolicitudReal(getSessionBeanCobra().getSolicitudService().getSolicitudObrach().getIntserial());

                    SolicitudObrach soli = getSessionBeanCobra().getSolicitudService().encontrarSolicitudObraCh(getSessionBeanCobra().getSolicitudService().getSolicitudObrach().getIntserial());

                    getSessionBeanCobra().getSolicitudService().guardarPlanificacionpagosolicitud(getValorpago1(), getSessionBeanCobra().getUsuarioObra().getUsuId(), soli);
                    getSessionBeanCobra().getSolicitudService().guardarPlanificacionpagosolicitud(getValorpago2(), getSessionBeanCobra().getUsuarioObra().getUsuId(), soli);
                    getSessionBeanCobra().getSolicitudService().guardarPlanificacionpagosolicitud(getValorpago3(), getSessionBeanCobra().getUsuarioObra().getUsuId(), soli);
                    getSessionBeanCobra().getSolicitudService().setMensaje("Guardo");
                }
            } else {
                getSessionBeanCobra().getSolicitudService().guardarBackup(getSessionBeanCobra().getSolicitudService().getSolicitudObrach().getIntserial());
                guarda = false;
                getSessionBeanCobra().getSolicitudService().setMensaje("No Guardo");
            }
        }
        return null;
    }

    public String confirguada() {
        getSessionBeanCobra().getSolicitudService().setSolicitudObrach(new SolicitudObrachTemp());
        getSessionBeanCobra().getSolicitudService().setSolicitudObrachD(new SolicitudObrach());
        switch (getSessionBeanCobra().getSolicitudService().getJsfusuariogrupo().getGrupo().getGruGid()) {
            case 5:
                primerosreales();
                guarda = false;
                return "guardo";
            case 6:
                primerosreales();
                guarda = false;
                return "guardo";
            case 7:
                primerosreales();
                primerosrealesobras();
                guarda = false;
                return "guardo";
            case 8:
                primerosrealesobras();
                guarda = false;
                return "guardo";
            case 9:
                primerosrealesobras();
                guarda = false;
                return "guardo";
        }
        return null;
    }

    public void verificarGoberAlcalde() {
        if (getSessionBeanCobra().getUsuarioObra().getTercero().getTipoOrigen().getIntidtipoorigen() == 1) {
            setCodMunicipio(getSessionBeanCobra().getUsuarioObra().getTercero().getLocalidadByStrcodigolocalidad().getStrmunicipio());
            verdepartamento = false;
            deshabilitarmunicipios = true;
            deshabilitardepartamentos = true;
            primerosreales();
        }
        if (getSessionBeanCobra().getUsuarioObra().getTercero().getTipoOrigen().getIntidtipoorigen() == 2) {
            codDepartamento = getSessionBeanCobra().getUsuarioObra().getTercero().getLocalidadByStrcodigolocalidad().getStrcodigolocalidad();
            llenarMunicipios();
            verdepartamento = true;
            deshabilitarmunicipios = false;
            deshabilitardepartamentos = true;
            primerosreales();
        }
        if (getSessionBeanCobra().getUsuarioObra().getTercero().getTipoOrigen().getIntidtipoorigen() == 4) {
            llenarDepartamento();
            llenarMunicipios();
            verdepartamento = true;
            deshabilitarmunicipios = false;
            deshabilitardepartamentos = false;
            primerosreales();
        }
    }

    public String consultarSolicitud() {
        consul = true;
        SolicitudObrachTemp solicitud_obratemp = (SolicitudObrachTemp) tablaSolicitudes.getRowData();
        getSessionBeanCobra().getSolicitudService().setDocumentosolicituds(new ArrayList<Documentosolicitud>());
        getSessionBeanCobra().getSolicitudService().setImagensolicituds(new ArrayList<Imagensolicitud>());
        getSessionBeanCobra().getSolicitudService().setDocumentosolicituds(getSessionBeanCobra().getSolicitudService().encontrarDocumentxSoliTemp(solicitud_obratemp));
        getSessionBeanCobra().getSolicitudService().setImagensolicituds(getSessionBeanCobra().getSolicitudService().encontrarImagenxSoliTemp(solicitud_obratemp));
        getSessionBeanCobra().getSolicitudService().setSolicitudObrach(solicitud_obratemp);
        valueestadosolich = solicitud_obratemp.getEstadosolicitudch().getOidestadosolicitud();
        llenarSubestadosolicitud();
        codDepartamento = solicitud_obratemp.getLocalidad().getStrcodigolocalidad().substring(0, 5);
        llenarMunicipios();
        codMunicipio = solicitud_obratemp.getLocalidad().getStrcodigolocalidad();
        return "crearSolicitud";
    }

    public String editarSolicitud() {
        consul = false;
        SolicitudObrachTemp solicitud_obratemp = (SolicitudObrachTemp) tablaSolicitudes.getRowData();
        getSessionBeanCobra().getSolicitudService().setDocumentosolicituds(new ArrayList<Documentosolicitud>());
        getSessionBeanCobra().getSolicitudService().setImagensolicituds(new ArrayList<Imagensolicitud>());
        getSessionBeanCobra().getSolicitudService().setDocumentosolicituds(getSessionBeanCobra().getSolicitudService().encontrarDocumentxSoliTemp(solicitud_obratemp));
        getSessionBeanCobra().getSolicitudService().setImagensolicituds(getSessionBeanCobra().getSolicitudService().encontrarImagenxSoliTemp(solicitud_obratemp));
        getSessionBeanCobra().getSolicitudService().setSolicitudObrach(solicitud_obratemp);
        valueestadosolich = solicitud_obratemp.getEstadosolicitudch().getOidestadosolicitud();
        llenarSubestadosolicitud();
        codMunicipio = solicitud_obratemp.getLocalidad().getStrmunicipio();
        return "crearSolicitud";
    }

    public String consultarSolicitudDirector() {
        SolicitudObrach solicitud_obra = (SolicitudObrach) tablaSolicitudesObra.getRowData();
        getSessionBeanCobra().getSolicitudService().setSolicitudObrachD(solicitud_obra);

        if (solicitud_obra.getSubestadosolicitudch().getEstadosolicitudch().getOidestadosolicitud() == 1) {
            mostrarmov = true;
        }

        movimientosFinancierosSolicitud(solicitud_obra.getIntordenprioridad());
        valueestadosolich = solicitud_obra.getEstadosolicitudch().getOidestadosolicitud();
        llenarSubestadosolicitud();
        llenarTipoOrdenPago();
        codMunicipio = solicitud_obra.getLocalidad().getStrmunicipio();
        getSessionBeanCobra().getSolicitudService().getOrdendepago();
        return listapagos(solicitud_obra);
    }

    public String asociarProyectoaSolicitud() {
        SolicitudObrach solicitud_obra = (SolicitudObrach) tablaSolicitudesObra.getRowData();

        if (solicitud_obra.getObras().size() == 0) {
            getSessionBeanCobra().getCobraService().getProyectoSoli().setStrnombreobra(solicitud_obra.getStrnombresolicitud());
            getSessionBeanCobra().getCobraService().getProyectoSoli().setStrobjetoobra(solicitud_obra.getTextobservaciones());
            getSessionBeanCobra().getCobraService().getProyectoSoli().setFloatlatitud(solicitud_obra.getLocalidad().getFloatlatitud());
            getSessionBeanCobra().getCobraService().getProyectoSoli().setFloatlongitud(solicitud_obra.getLocalidad().getFloatlongitud());
            getSessionBeanCobra().getCobraService().getProyectoSoli().setNumvaltotobra(solicitud_obra.getDoublevaloraprobado());
            getSessionBeanCobra().getCobraService().getProyectoSoli().setSolicitud_obra(solicitud_obra);
            getSessionBeanCobra().getCobraService().getProyectoSoli().setClaseobra(new Claseobra(solicitud_obra.getTiposolicitudobra().getInttiposolicitud(), null, "", true));
            getSessionBeanCobra().getCobraService().getProyectoSoli().setTipoobra(new Tipoobra(14, null, ""));
            getSessionBeanCobra().getCobraService().getProyectoSoli().getTipoobra().setTipoproyecto(new Tipoproyecto(1, ""));
            getSessionBeanCobra().getCobraService().getProyectoSoli().getClaseobra().setFase(new Fase(2, ""));
            getSessionBeanCobra().getCobraService().getProyectoSoli().setTipoorigen(new Tipoorigen(getSessionBeanCobra().getUsuarioObra().getTercero().getTipoOrigen().getIntidtipoorigen(), ""));
            getSessionBeanCobra().getCobraService().getProyectoSoli().setTipoestadobra(new Tipoestadobra(0));
            getSessionBeanCobra().getCobraService().getProyectoSoli().setTercero(getSessionBeanCobra().getUsuarioObra().getTercero());
            getSessionBeanCobra().getCobraService().getProyectoSoli().setPeriodomedida(new Periodomedida());
            getSessionBeanCobra().getCobraService().getProyectoSoli().setLugarobra(new Lugarobra(1, ""));
            getSessionBeanCobra().getCobraService().getProyectoSoli().setPeriodoevento(new Periodoevento(1, null, null, null, null, null, false));
            getSessionBeanCobra().getCobraService().getProyectoSoli().setTipocosto(new Tipocosto(1, ""));
            getSessionBeanCobra().getCobraService().setSolibol(true);
            return "asociarproyectoasolici";
        } else {
            FacesUtils.addErrorMessage("Esta solicitud ya tiene un proyecto");
        }
        return null;
    }

    public String listapagos(SolicitudObrach solicitud_obra) {
        getSessionBeanCobra().getSolicitudService().setPlanificacionpagosolicituds(getSessionBeanCobra().getSolicitudService().encontrarPlanificacionxSolicitud(solicitud_obra));
        if (getSessionBeanCobra().getSolicitudService().getPlanificacionpagosolicituds().size() > 0) {
            value1 = getSessionBeanCobra().getSolicitudService().getPlanificacionpagosolicituds().get(0).getNumvlrpago();
            value2 = getSessionBeanCobra().getSolicitudService().getPlanificacionpagosolicituds().get(1).getNumvlrpago();
            value3 = getSessionBeanCobra().getSolicitudService().getPlanificacionpagosolicituds().get(2).getNumvlrpago();
        }
        return "consultardirec";
    }

    public BigDecimal getValorpago3() {
        BigDecimal temp = BigDecimal.ZERO;
        temp = getSessionBeanCobra().getSolicitudService().getSolicitudObrach().getDoublevaloraprobado().multiply(new BigDecimal(porpago3)).divide(new BigDecimal(100));
        return temp;
    }

    public BigDecimal getValorpago2() {
        BigDecimal temp = BigDecimal.ZERO;
        temp = getSessionBeanCobra().getSolicitudService().getSolicitudObrach().getDoublevaloraprobado().multiply(new BigDecimal(porpago2)).divide(new BigDecimal(100));
        return temp;
    }

    public BigDecimal getValorpago1() {
        BigDecimal temp = BigDecimal.ZERO;
        temp = getSessionBeanCobra().getSolicitudService().getSolicitudObrach().getDoublevaloraprobado().multiply(new BigDecimal(porpago1)).divide(new BigDecimal(100));
        return temp;
    }

    public boolean getValidarpagos() {
        double temp;
        boolean sumarpagos;
        temp = porpago1 + porpago2 + porpago3;
        if (temp > 100) {
            getSessionBeanCobra().getSolicitudService().setMensaje("Los Pagos han sobrepasado el 100%");
            return sumarpagos = false;
        } else {
            return sumarpagos = true;
        }
    }

    public int getValidarclaseobra() {
        int temp = 0;
        if (getSessionBeanCobra().getSolicitudService().getSolicitudObrach().getTiposolicitudobra().getInttiposolicitud() == 2) {
            temp = 2500000;
        } else {
            temp = 500000000;
        }
        return temp;
    }

    public boolean getResulbusqueda() {
//        boolean temp = false;
//        if (getSessionBeanCobra().getSolicitudService().getSolicitudObrachTemps().equals(null)) {
//            temp = false;
//        }
        if (getSessionBeanCobra().getSolicitudService().getSolicitudObrachTemps().size() <= 0) {
            getSessionBeanCobra().getSolicitudService().setMensaje(bundle.getString("notienesolicitud"));
            return true;
//            temp = true;
        }

        return false;
    }

    public String primerosreales() {
//        if (aplicafiltro) {
        getSessionBeanCobra().getSolicitudService().setSolicitudObrachTemps(getSessionBeanCobra().getSolicitudService().filtrarSolicitudesObraTemp(getSessionBeanCobra().getUsuarioObra().getUsuId(),
                getSessionBeanCobra().getSolicitudService().getJsfusuariogrupo().getGrupo().getGruGid(), filtro, 0, 5));
        totalfilas = getSessionBeanCobra().getSolicitudService().getNumSolicitudesObraTemp(getSessionBeanCobra().getUsuarioObra().getUsuId(),
                getSessionBeanCobra().getSolicitudService().getJsfusuariogrupo().getGrupo().getGruGid(), filtro);
//        } else {
//            if (getSessionBeanCobra().getSolicitudService().getJsfusuariogrupo().getGrupo().getGruGid() == 5) {
////                getSessionBeanCobra().getSolicitudService().setSolicitudObrachTemps(getSessionBeanCobra().getSolicitudService().SolicitudesObraTempxUsuid(getSessionBeanCobra().getUsuarioObra().getUsuId(), 0, 5));
////                totalfilas = getSessionBeanCobra().getSolicitudService().getNumSolicitudesObraTempxUsuid(getSessionBeanCobra().getUsuarioObra().getUsuId());
//            } else {
//                getSessionBeanCobra().getSolicitudService().setSolicitudObrachTemps(getSessionBeanCobra().getSolicitudService().encontrarTotalSolicitudesObraTemp(0, 5));
//                totalfilas = getSessionBeanCobra().getSolicitudService().getNumSolicitudes();
//            }
//        }

        pagina = 1;
        if (totalfilas <= 5) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 5;
            if (totalfilas % 5 > 1) {
                totalpaginas++;
            }
        }
        veranteriorreales = false;
        if (totalpaginas > 1) {
            verultimosreales = true;
        } else {
            verultimosreales = false;
        }
        return null;
    }

    public String siguientesReales() {
        int num = (pagina) * 5;
//        if (aplicafiltro) {
        getSessionBeanCobra().getSolicitudService().setSolicitudObrachTemps(getSessionBeanCobra().getSolicitudService().filtrarSolicitudesObraTemp(getSessionBeanCobra().getUsuarioObra().getUsuId(),
                getSessionBeanCobra().getSolicitudService().getJsfusuariogrupo().getGrupo().getGruGid(), filtro, num, num + 5));
        totalfilas = getSessionBeanCobra().getSolicitudService().getNumSolicitudesObraTemp(getSessionBeanCobra().getUsuarioObra().getUsuId(),
                getSessionBeanCobra().getSolicitudService().getJsfusuariogrupo().getGrupo().getGruGid(), filtro);
//        } else {
//            if (getSessionBeanCobra().getSolicitudService().getJsfusuariogrupo().getGrupo().getGruGid() == 5) {
//                getSessionBeanCobra().getSolicitudService().setSolicitudObrachTemps(getSessionBeanCobra().getSolicitudService().SolicitudesObraTempxUsuid(getSessionBeanCobra().getUsuarioObra().getUsuId(), num, num + 5));
//                totalfilas = getSessionBeanCobra().getSolicitudService().getNumSolicitudesObraTempxUsuid(getSessionBeanCobra().getUsuarioObra().getUsuId());
//            } else {
//                getSessionBeanCobra().getSolicitudService().setSolicitudObrachTemps(getSessionBeanCobra().getSolicitudService().encontrarTotalSolicitudesObraTemp(num, num + 5));
//                totalfilas = getSessionBeanCobra().getSolicitudService().getNumSolicitudes();
//            }
//        }

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
            verultimosreales = true;
        } else {
            verultimosreales = false;
        }
        veranteriorreales = true;
        return null;
    }

    public String anterioresReales() {
        pagina = pagina - 1;
        int num = (pagina - 1) * 5;
//        if (aplicafiltro) {
        getSessionBeanCobra().getSolicitudService().setSolicitudObrachTemps(getSessionBeanCobra().getSolicitudService().filtrarSolicitudesObraTemp(getSessionBeanCobra().getUsuarioObra().getUsuId(),
                getSessionBeanCobra().getSolicitudService().getJsfusuariogrupo().getGrupo().getGruGid(), filtro, num, num + 5));
        totalfilas = getSessionBeanCobra().getSolicitudService().getNumSolicitudesObraTemp(getSessionBeanCobra().getUsuarioObra().getUsuId(),
                getSessionBeanCobra().getSolicitudService().getJsfusuariogrupo().getGrupo().getGruGid(), filtro);
//        } else {
//            if (getSessionBeanCobra().getSolicitudService().getJsfusuariogrupo().getGrupo().getGruGid() == 5) {
//                getSessionBeanCobra().getSolicitudService().setSolicitudObrachTemps(getSessionBeanCobra().getSolicitudService().SolicitudesObraTempxUsuid(getSessionBeanCobra().getUsuarioObra().getUsuId(), num, num + 5));
//                totalfilas = getSessionBeanCobra().getSolicitudService().getNumSolicitudesObraTempxUsuid(getSessionBeanCobra().getUsuarioObra().getUsuId());
//            } else {
//                getSessionBeanCobra().getSolicitudService().setSolicitudObrachTemps(getSessionBeanCobra().getSolicitudService().encontrarTotalSolicitudesObraTemp(num, num + 5));
//                totalfilas = getSessionBeanCobra().getSolicitudService().getNumSolicitudes();
//            }
//        }
        if (totalfilas <= 5) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 5;
            if (totalfilas % 5 > 0) {
                totalpaginas++;
            }
        }

        if (pagina > 1) {
            veranteriorreales = true;
        } else {
            veranteriorreales = false;
        }
        verultimosreales = true;
        return null;
    }

    public String ultimoReales() {
//        if (aplicafiltro) {
        getSessionBeanCobra().getSolicitudService().setSolicitudObrachTemps(getSessionBeanCobra().getSolicitudService().filtrarSolicitudesObraTemp(getSessionBeanCobra().getUsuarioObra().getUsuId(),
                getSessionBeanCobra().getSolicitudService().getJsfusuariogrupo().getGrupo().getGruGid(), filtro, totalfilas - 5, totalfilas));
        totalfilas = getSessionBeanCobra().getSolicitudService().getNumSolicitudesObraTemp(getSessionBeanCobra().getUsuarioObra().getUsuId(),
                getSessionBeanCobra().getSolicitudService().getJsfusuariogrupo().getGrupo().getGruGid(), filtro);
//        } else {
//            if (getSessionBeanCobra().getSolicitudService().getJsfusuariogrupo().getGrupo().getGruGid() == 5) {
//                totalfilas = getSessionBeanCobra().getSolicitudService().getNumSolicitudesObraTempxUsuid(getSessionBeanCobra().getUsuarioObra().getUsuId());
//                getSessionBeanCobra().getSolicitudService().setSolicitudObrachTemps(getSessionBeanCobra().getSolicitudService().SolicitudesObraTempxUsuid(getSessionBeanCobra().getUsuarioObra().getUsuId(), totalfilas - 5, totalfilas));
//            } else {
//                totalfilas = getSessionBeanCobra().getSolicitudService().getNumSolicitudes();
//                getSessionBeanCobra().getSolicitudService().setSolicitudObrachTemps(getSessionBeanCobra().getSolicitudService().encontrarTotalSolicitudesObraTemp(totalfilas - 5, totalfilas));
//            }
//        }

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
            verultimosreales = true;
        } else {
            verultimosreales = false;
        }
        veranteriorreales = true;

        return null;
    }

    public String primerosrealesobras() {

//           if (aplicafiltro) {
        getSessionBeanCobra().getSolicitudService().setSolicitudObrachs(getSessionBeanCobra().getSolicitudService().filtrarSolicitudesObra(filtro, 0, 5));
        totalfilasobra = getSessionBeanCobra().getSolicitudService().getNumSolicitudesObra(filtro);
//        } else {
//            getSessionBeanCobra().getSolicitudService().setSolicitudObrachs(getSessionBeanCobra().getSolicitudService().encontrarTotalSolicitudesObra(0, 5));
//            totalfilasobra = getSessionBeanCobra().getSolicitudService().getNumSolicitudesobras();
//        }
        paginaobra = 1;
        if (totalfilasobra <= 5) {
            totalpaginasobra = 1;
        } else {
            totalpaginasobra = totalfilasobra / 5;
            if (totalfilasobra % 5 > 1) {
                totalpaginasobra++;
            }
        }
        veranteriorrealesobra = false;
        if (totalpaginasobra > 1) {
            verultimosrealesobra = true;
        } else {
            verultimosrealesobra = false;
        }
        return null;
    }

    public String siguientesRealesobras() {
        int num = (paginaobra) * 5;
//        if (aplicafiltro) {
            getSessionBeanCobra().getSolicitudService().setSolicitudObrachs(getSessionBeanCobra().getSolicitudService().filtrarSolicitudesObra(filtro, num, num + 5));
            totalfilasobra = getSessionBeanCobra().getSolicitudService().getNumSolicitudesObra(filtro);
//        } else {
//            getSessionBeanCobra().getSolicitudService().setSolicitudObrachs(getSessionBeanCobra().getSolicitudService().encontrarTotalSolicitudesObra(num, num + 5));
//            totalfilasobra = getSessionBeanCobra().getSolicitudService().getNumSolicitudesobras();
//        }


        if (totalfilasobra <= 5) {
            totalpaginasobra = 1;
        } else {
            totalpaginasobra = totalfilasobra / 5;
            if (totalfilasobra % 5 > 0) {
                totalpaginasobra++;
            }
        }
        paginaobra = paginaobra + 1;
        if (paginaobra < totalpaginasobra) {
            verultimosrealesobra = true;
        } else {
            verultimosrealesobra = false;
        }
        veranteriorrealesobra = true;
        return null;
    }

    public String anterioresRealesobras() {
        paginaobra = paginaobra - 1;
        int num = (paginaobra - 1) * 5;
//        if (aplicafiltro) {
            getSessionBeanCobra().getSolicitudService().setSolicitudObrachs(getSessionBeanCobra().getSolicitudService().filtrarSolicitudesObra(filtro, num, num + 5));
            totalfilasobra = getSessionBeanCobra().getSolicitudService().getNumSolicitudesObra(filtro);
//        } else {
//            getSessionBeanCobra().getSolicitudService().setSolicitudObrachs(getSessionBeanCobra().getSolicitudService().encontrarTotalSolicitudesObra(num, num + 5));
//            totalfilasobra = getSessionBeanCobra().getSolicitudService().getNumSolicitudesobras();
//        }

        if (totalfilasobra <= 5) {
            totalpaginasobra = 1;
        } else {
            totalpaginasobra = totalfilasobra / 5;
            if (totalfilasobra % 5 > 0) {
                totalpaginasobra++;
            }
        }

        if (paginaobra > 1) {
            veranteriorrealesobra = true;
        } else {
            veranteriorrealesobra = false;
        }
        verultimosrealesobra = true;
        return null;
    }

    public String ultimoRealesobras() {
//        if (aplicafiltro) {
            totalfilasobra = getSessionBeanCobra().getSolicitudService().getNumSolicitudesObra(filtro);
            getSessionBeanCobra().getSolicitudService().setSolicitudObrachs(getSessionBeanCobra().getSolicitudService().filtrarSolicitudesObra(filtro, totalfilasobra - 5, totalfilasobra));
//        } else {
//            totalfilasobra = getSessionBeanCobra().getSolicitudService().getNumSolicitudesobras();
//            getSessionBeanCobra().getSolicitudService().setSolicitudObrachs(getSessionBeanCobra().getSolicitudService().encontrarTotalSolicitudesObra(totalfilasobra - 5, totalfilasobra));
//        }


        if (totalfilasobra <= 5) {
            totalpaginasobra = 1;
        } else {
            totalpaginasobra = totalfilasobra / 5;
            if (totalfilasobra % 5 > 0) {
                totalpaginasobra++;
            }
        }

        paginaobra = totalpaginasobra;
        if (paginaobra < totalpaginasobra) {
            verultimosrealesobra = true;
        } else {
            verultimosrealesobra = false;
        }
        veranteriorrealesobra = true;

        return null;
    }

    public void subirImagenAtencion() {
        String realArchivoPath = "";
        String URL = "/resources/Documentos/Solicitud/Temporal/";

        ServletContext theApplicationsServletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String URLFINAL = theApplicationsServletContext.getRealPath("");

        if (subirImagenSolicitud.getSize() > 0) {
            realArchivoPath = theApplicationsServletContext.getRealPath(URL);
            try {

                File carpeta = new File(realArchivoPath);
                if (!carpeta.exists()) {
                    carpeta.mkdirs();
                }
            } catch (Exception ex) {
                FacesUtils.addErrorMessage(bundle.getString("nosepuedesubir"));
            }
        }

        subirImagenSolicitud.guardarArchivosTemporales(realArchivoPath, false);

        Iterator arch = subirImagenSolicitud.getFiles().iterator();
        while (arch.hasNext()) {
            Archivo nombreoriginal = (Archivo) arch.next();
            getSessionBeanCobra().getSolicitudService().getImagensolicitud().setStrurlimagen(URL + "/" + nombreoriginal.getOnlyName());
        }
    }

    public String borrarImagenAtencion() {
        String ArchivoPath = "";
        ServletContext contexto = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        if (pathImagen.compareTo("") != 0) {
            ArchivoPath = contexto.getRealPath(pathImagen);
            File archi = new File(ArchivoPath);
            if (archi.exists()) {
                archi.delete();
            }
            pathImagen = "/resources/imgs/bt_nodisponible.jpg";
        }
        subirImagenSolicitud.borrarDatosSubidos();
        return null;
    }

    /**
     * Elimina la imágen de la lista
     * @param filaSeleccionada Corresponde a la fila de la que proviene la
     * acción en la tabla
     * @return null
     */
    public String eliminarImagenLista(int filaSeleccionada) {
        SessionBeanCobra sessionBeanCobra = (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
        Imagensolicitud imagensolicitud = sessionBeanCobra.getSolicitudService().getImagensolicituds().get(filaSeleccionada);
        sessionBeanCobra.getSolicitudService().setImagensolicitud(imagensolicitud);
        getSessionBeanCobra().getSolicitudService().getImagensolicituds().remove(getSessionBeanCobra().getSolicitudService().getImagensolicitud());
        return null;
    }

    /**
     * Elimina el documento de la lista
     *
     * @param filaSeleccionada Corresponde a la fila de la que proviene la
     * acción en la tabla
     * @return null
     */
    public String eliminarDocumentoLista(int filaSeleccionada) {
        SessionBeanCobra sessionBeanCobra = (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
        Documentosolicitud documentosolicitud = sessionBeanCobra.getSolicitudService().getDocumentosolicituds().get(filaSeleccionada);
        getSessionBeanCobra().getSolicitudService().setDocumentosolicitud(documentosolicitud);
        getSessionBeanCobra().getSolicitudService().getDocumentosolicituds().remove(getSessionBeanCobra().getSolicitudService().getDocumentosolicitud());
        return null;
    }

    public String subirImagenaLista() {
        if (!getSessionBeanCobra().getSolicitudService().getImagensolicitud().getStrdescripcion().equals("")) {
            getSessionBeanCobra().getSolicitudService().getImagensolicitud().setSolicitudObrachTemp(getSessionBeanCobra().getSolicitudService().getSolicitudObrach());
            subirImagenAtencion();
            getSessionBeanCobra().getSolicitudService().getImagensolicituds().add(getSessionBeanCobra().getSolicitudService().getImagensolicitud());
            getSessionBeanCobra().getSolicitudService().setImagensolicitud(new Imagensolicitud());
            setSubirImagenSolicitud(new SubirArchivoBean(1, true, false));
        } else {
            FacesUtils.addErrorMessage(bundle.getString("debeproporcionaadescrip"));
        }
        return null;
    }

    public String subirDocumentoaLista() {

        try {



            if (!getSessionBeanCobra().getSolicitudService().getDocumentosolicitud().getStrdescripcion().equals("")) {

                // if (!getSessionBeanCobra().getSolicitudService().getDocumentosolicitud().getStrurldocumento().equals("")) {
                getSessionBeanCobra().getSolicitudService().getDocumentosolicitud().setSolicitudObrachTemp(getSessionBeanCobra().getSolicitudService().getSolicitudObrach());
                getSessionBeanCobra().getSolicitudService().setTipodocumentosolicitud(getSessionBeanCobra().getAtencionhumanitariaService().encontrarTipoDocPorId(tipodoc));
                subirDocumento();
                getSessionBeanCobra().getSolicitudService().getDocumentosolicitud().setTipodocumentosolicitud(getSessionBeanCobra().getSolicitudService().getTipodocumentosolicitud());
                getSessionBeanCobra().getSolicitudService().getDocumentosolicituds().add(getSessionBeanCobra().getSolicitudService().getDocumentosolicitud());
                getSessionBeanCobra().getSolicitudService().setDocumentosolicitud(new Documentosolicitud());
                setSubirDocumentoSolicitud(new SubirArchivoBean(1, true, false));

                //} else {
                //     FacesUtils.addErrorMessage(bundle.getString("debeproporcinarelnombredeldocu"));
                //}


            } else {

                FacesUtils.addErrorMessage(bundle.getString("debeproporcinarelnombredeldocu"));
            }

        } catch (Exception ex) {
        }

        return null;
    }

    public String subirActaaLista() {
        getSessionBeanCobra().getSolicitudService().getDocumentosolicitud().setSolicitudObrachTemp(getSessionBeanCobra().getSolicitudService().getSolicitudObrach());
        getSessionBeanCobra().getSolicitudService().setTipodocumentosolicitud(getSessionBeanCobra().getAtencionhumanitariaService().encontrarTipoDocPorId(7));
        subirDocumento();
        getSessionBeanCobra().getSolicitudService().getDocumentosolicitud().setTipodocumentosolicitud(getSessionBeanCobra().getSolicitudService().getTipodocumentosolicitud());
        getSessionBeanCobra().getSolicitudService().getDocumentosolicituds().add(getSessionBeanCobra().getSolicitudService().getDocumentosolicitud());
        getSessionBeanCobra().getSolicitudService().setDocumentosolicitud(new Documentosolicitud());
        setSubirActa(new SubirArchivoBean(1, true, false));
        return null;
    }

    public void subirDocumento() {
        String realArchivoPath = "";
        String URL = "/resources/Documentos/Solicitud/Temporal";

        ServletContext theApplicationsServletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        if (subirDocumentoSolicitud.getSize() > 0) {
            realArchivoPath = theApplicationsServletContext.getRealPath(URL);
            try {
                File carpeta = new File(realArchivoPath);
                if (!carpeta.exists()) {
                    carpeta.mkdirs();
                }
            } catch (Exception ex) {
                mensaje = (bundle.getString("nosepuedesubir"));//"Cannot upload file ");
            }
        }
        subirDocumentoSolicitud.guardarArchivosTemporales(realArchivoPath, false);
        Iterator arch = subirDocumentoSolicitud.getFiles().iterator();
        while (arch.hasNext()) {
            Archivo nombreoriginal = (Archivo) arch.next();
            getSessionBeanCobra().getSolicitudService().getDocumentosolicitud().setStrurldocumento(URL + "/" + nombreoriginal.getOnlyName());
        }
    }

    public void subirActa() {
        String realArchivoPath = "";
        String URL = "/resources/Documentos/Solicitud/Temporal";

        ServletContext theApplicationsServletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        if (subirActa.getSize() > 0) {
            realArchivoPath = theApplicationsServletContext.getRealPath(URL);
            try {
                File carpeta = new File(realArchivoPath);
                if (!carpeta.exists()) {
                    carpeta.mkdirs();
                }
            } catch (Exception ex) {
                mensaje = (bundle.getString("nosepuedesubir"));//"Cannot upload file ");
            }
        }
        subirActa.guardarArchivosTemporales(realArchivoPath, false);
        Iterator arch = subirActa.getFiles().iterator();
        while (arch.hasNext()) {
            Archivo nombreoriginal = (Archivo) arch.next();
            getSessionBeanCobra().getSolicitudService().getDocumentosolicitud().setStrurldocumento(URL + "/" + nombreoriginal.getOnlyName());
        }
    }

    public String borrarActaAtencion() {
        String ArchivoPath = "";
        ServletContext contexto = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        if (pathActa.compareTo("") != 0) {
            ArchivoPath = contexto.getRealPath(pathActa);
            File archi = new File(ArchivoPath);
            if (archi.exists()) {
                archi.delete();
            }
            pathActa = "/resources/imgs/bt_nodisponible.jpg";
        }
        subirActa.borrarDatosSubidos();
        return null;
    }

    public String eliminarsolicitud() {
        SolicitudObrachTemp solicitud_obra = (SolicitudObrachTemp) tablaSolicitudes.getRowData();
        getSessionBeanCobra().getSolicitudService().setSolicitudObrach(solicitud_obra);
        return null;
    }

    public String eliminaraceptar() {
        getSessionBeanCobra().getSolicitudService().getSolicitudObrach().setBooleliminado(true);
        getSessionBeanCobra().getSolicitudService().guardarSolicitudObrachTemp(getSessionBeanCobra().getSolicitudService().getSolicitudObrach());
        getSessionBeanCobra().getSolicitudService().setSolicitudObrach(new SolicitudObrachTemp());
        primerosreales();
        return null;
    }

    public String iniciarfiltro() {
        aplicafiltro = true;
        llenarEstadosoli();
        llenarTiposolicitudObra();
        llenarDepartamentoFiltro();
        llenarMunicipiosFiltro();
        llenarTipoobras();
        return null;
    }

    public String buscar() {
        aplicafiltro = true;
        primerosreales();
        primerosrealesobras();
        return null;
    }

    public String validarordenpago() {
        if (!getSessionBeanCobra().getSolicitudService().getOrdendepago().getStrordenpago().equals("")
                || !getSessionBeanCobra().getSolicitudService().getOrdendepago().getStrdescripcionordenpago().equals("")) {
            setGuarda(true);
            guardarOrdenPago();
            return null;
        } else {
            setGuarda(false);
            getSessionBeanCobra().getSolicitudService().setMensaje("debe diligenciar la orden de pago");
            return null;
        }
    }

    public String guardarOrdenPago() {
        if (isGuarda()) {
            getSessionBeanCobra().getSolicitudService().getOrdendepago().setBoolestadoordenpago(true);
            getSessionBeanCobra().getSolicitudService().getOrdendepago().setJsfUsuario(new JsfUsuario(getSessionBeanCobra().getUsuarioObra().getUsuId(), "", ""));
            getSessionBeanCobra().getSolicitudService().getOrdendepago().setTercero(new Tercero(getSessionBeanCobra().getUsuarioObra().getUsuId(), null, null, null, "", "", "", "", "", false, null, ""));
            getSessionBeanCobra().getSolicitudService().getOrdendepago().setNumvlrordenpago(getSessionBeanCobra().getSolicitudService().getSolicitudObrachD().getDoublevaloraprobado());
            getSessionBeanCobra().getSolicitudService().getOrdendepago().setSolicitud_obra(getSessionBeanCobra().getSolicitudService().getSolicitudObrachD());

            getSessionBeanCobra().getSolicitudService().guardarOrdenPago(getSessionBeanCobra().getSolicitudService().getOrdendepago());
            getSessionBeanCobra().getSolicitudService().setMensaje("Guardo");
        } else {
            getSessionBeanCobra().getSolicitudService().setMensaje("no Guardo");
        }
        return null;
    }

    public String guardarEncargoFiduciario() {
        getSessionBeanCobra().getSolicitudService().getEncargofiduciario().setDatefechacreacion(new Date());
        getSessionBeanCobra().getSolicitudService().getEncargofiduciario().setNumsaldoactual(getSessionBeanCobra().getSolicitudService().getSolicitudObrachD().getDoublevaloraprobado());
        getSessionBeanCobra().getSolicitudService().getEncargofiduciario().setNumsaldoinicial(getSessionBeanCobra().getSolicitudService().getSolicitudObrachD().getDoublevaloraprobado());
        getSessionBeanCobra().getSolicitudService().getEncargofiduciario().setJsfUsuarioByIntusucreacion(new JsfUsuario(getSessionBeanCobra().getUsuarioObra().getUsuId(), "", ""));
        getSessionBeanCobra().getSolicitudService().getEncargofiduciario().setTercero(getSessionBeanCobra().getSolicitudService().getSolicitudObrachD().getJsfUsuarioByIntreviso().getTercero());
        getSessionBeanCobra().getSolicitudService().getEncargofiduciario().setLocalidad(getSessionBeanCobra().getSolicitudService().getSolicitudObrachD().getLocalidad());
        getSessionBeanCobra().getSolicitudService().guardarEncargoFiduciario(getSessionBeanCobra().getSolicitudService().getEncargofiduciario());

//        getSessionBeanCobra().getSolicitudService().getOrdendepago().setEncargofiduciario(getSessionBeanCobra().getSolicitudService().getEncargofiduciario());
//        getSessionBeanCobra().getSolicitudService().guardarOrdenPago(getSessionBeanCobra().getSolicitudService().getOrdendepago());
        getSessionBeanCobra().getSolicitudService().setMensaje("Guardo");
        guarda = true;
        return null;
    }

    public String validardocumentacion() {
        getSessionBeanCobra().getSolicitudService().setTipodocumenObli(getSessionBeanCobra().getSolicitudService().encontrarTipodocumentoObligatorio());
        int i = 0;
        while (i < getSessionBeanCobra().getSolicitudService().getTipodocumenObli().size()) {
            int x = 0;
            while (x < getSessionBeanCobra().getSolicitudService().getDocumentosolicituds().size()) {
                if (getSessionBeanCobra().getSolicitudService().getTipodocumenObli().get(i).getInttipodocsol()
                        == getSessionBeanCobra().getSolicitudService().getDocumentosolicituds().get(x).getTipodocumentosolicitud().getInttipodocsol()) {
                    switch (getSessionBeanCobra().getSolicitudService().getDocumentosolicituds().get(x).getTipodocumentosolicitud().getInttipodocsol()) {
                        case 1:
                            getSessionBeanCobra().getSolicitudService().getSolicitudObrach().setBoolcartaasumiradicionales(true);
                            break;
                        case 2:
                            getSessionBeanCobra().getSolicitudService().getSolicitudObrach().setBooldeclaracionnosolicitudotraentidad(true);
                            break;
                        case 3:
                            getSessionBeanCobra().getSolicitudService().getSolicitudObrach().setBoolpresupuesto(true);
                            break;
                        case 4:
                            getSessionBeanCobra().getSolicitudService().getSolicitudObrach().setBoolviabilidadalasolicitud(true);
                            break;
                        case 5:
                            getSessionBeanCobra().getSolicitudService().getSolicitudObrach().setBoolanexaactaclopadocrepad(true);
                            break;
                        case 6:
                            getSessionBeanCobra().getSolicitudService().getSolicitudObrach().setBoolcartafirmada(true);
                            break;
                    }
                }
                x++;
            }
            i++;
        }
        cambiarDocumentacion();
        return null;
    }

    public String cambiarDocumentacion() {
        if (getSessionBeanCobra().getSolicitudService().getSolicitudObrach().isBoolcartaasumiradicionales()
                && getSessionBeanCobra().getSolicitudService().getSolicitudObrach().isBooldeclaracionnosolicitudotraentidad()
                && getSessionBeanCobra().getSolicitudService().getSolicitudObrach().isBoolpresupuesto()
                && getSessionBeanCobra().getSolicitudService().getSolicitudObrach().isBoolanexaactaclopadocrepad()
                && getSessionBeanCobra().getSolicitudService().getSolicitudObrach().isBoolcartafirmada()
                && getSessionBeanCobra().getSolicitudService().getSolicitudObrach().isBoolviabilidadalasolicitud()) {

            getSessionBeanCobra().getSolicitudService().getSolicitudObrach().getEstadodocumentacion().setIntestadodocumentacion(1);
            valueestadosolich = 1;
            llenarSubestadosolicitud();
        }
        return null;
    }

    public String llenarProyectosxSolicitud() {
        SolicitudObrach solicitud_obra = (SolicitudObrach) tablaSolicitudesObra.getRowData();
        getSessionBeanCobra().getSolicitudService().setListaproyectoasoci(getSessionBeanCobra().getSolicitudService().encontrarProyectosxSolicitud(solicitud_obra.getIntserial()));
        return null;
    }

    public String llenarProyectosxSolicitudTemp() {
        SolicitudObrachTemp solicitud_obra = (SolicitudObrachTemp) tablaSolicitudes.getRowData();
        getSessionBeanCobra().getSolicitudService().setListaproyectoasoci(getSessionBeanCobra().getSolicitudService().encontrarProyectosxSolicitud(solicitud_obra.getIntserial()));
        return null;
    }

    public String verProyectosSoli() throws Exception {
        Obra obra = (Obra) tablaProyectosAso.getRowData();
        // IngresarNuevaObra igre = new IngresarNuevaObra();

        if (obra.getTipoestadobra().getIntestadoobra() == 1) {
            getAdministrarObraNew().setObra(obra);
            return "verproyectocomple";
        } else {
            getIngresarNuevaObra().cargarObra(obra);
            return "verproyectoimcomple";
        }
    }

    protected AdministrarObraNew getAdministrarObraNew() {
        return (AdministrarObraNew) FacesUtils.getManagedBean("Supervisor$AdministrarObraNew");
    }

    protected IngresarNuevaObra getIngresarNuevaObra() {
        return (IngresarNuevaObra) FacesUtils.getManagedBean("Supervisor$IngresarNuevaObra");
    }

    public void movimientosFinancierosSolicitud(int codsolicitud) {
        getSessionBeanCobra().getSolicitudService().setListamovimientosolobra(getSessionBeanCobra().getSolicitudService().encontrarmovimientossolicitudobra(codsolicitud));

    }
}
