/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.ControlGerencial;

import co.com.interkont.cobra.tipo.Dashboard;
import co.com.interkont.cobra.tipo.DashboardAh;
import co.com.interkont.cobra.tipo.DashboardConvenios;
import co.com.interkont.cobra.to.Imagenevolucionobra;
import co.com.interkont.cobra.to.Localidad;
import co.com.interkont.cobra.to.LogConsolidado;
import co.com.interkont.cobra.to.Zonaespecifica;
import co.com.interkont.cobra.to.utilidades.Propiedad;
import cobra.FiltroGerencial;
import cobra.SessionBeanCobra;
import cobra.Supervisor.FacesUtils;

import cobra.indicadores.GraficoBarrasHorizontal;
import cobra.indicadores.GraficoColumnaPorcentual;
import cobra.indicadores.GraficoPieDashboard;
import com.googlecode.gmaps4jsf.services.ReverseGeocoderServiceImpl.*;
import com.interkont.cobra.util.DatoPie;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

/**
 * <p>Page bean that corresponds to a similarly named JSP page. This class
 * contains component definitions (and initialization code) for all components
 * that you have defined on this page, as well as lifecycle methods and event
 * handlers where you may add behavior to respond to incoming events.</p>
 *
 * @version IngresarNuevaObra.java
 * @version Created on 16/11/2008, 04:41:01 PM
 * @author Carlos Alberto Loaiza Guerrerro
 */
public class ControlGerencial implements Serializable {

    private String deptos;
    private String muni;
    private SelectItem[] zonaespecifica;
    private SelectItem[] sedeptos;
    private SelectItem[] semuni;
    private int intzona = 0;
    private int vistaicom;
    private GraficoColumnaPorcentual columnatotalgeneral;
    private GraficoColumnaPorcentual columnapagocontratistas;
    private GraficoColumnaPorcentual columnalegalizacion;
    private GraficoColumnaPorcentual columnaproyectoconsolidado;
    private GraficoPieDashboard piecontrol;
    private GraficoBarrasHorizontal barrasatencion;
    private GraficoBarrasHorizontal barraspersonas;
    private GraficoBarrasHorizontal barrashogares;
    private FiltroGerencial filtrogeren = new FiltroGerencial();
    private SelectItem[] itemsCortes;
    private int idconsolidadoselec;
    private int porejecutado;
    //private BigDecimal inversion = BigDecimal.ZERO;
    //private BigDecimal ejecutadoconvenio = BigDecimal.ZERO;
    //private BigDecimal porcentageEje = BigDecimal.ZERO;
    //private BigDecimal intPorcentageEje = BigDecimal.ZERO;
    private List<Imagenevolucionobra> listaimagenesslider = new ArrayList<Imagenevolucionobra>();
    private boolean invporregion = false;
    private BigDecimal totalPago = BigDecimal.ZERO;
    private BigDecimal porcentajePago;
    private BigDecimal totalLegalizado;
    private BigDecimal porcentajeLegalizado;
    private BigDecimal totalCartera = BigDecimal.ZERO;
    private BigDecimal porcentajeCartera = BigDecimal.ZERO;
    //private BigDecimal inversionRegion = BigDecimal.ZERO;
    static final BigDecimal CIEN = BigDecimal.valueOf(Double.valueOf(100));
    static final BigDecimal CERO = BigDecimal.valueOf(Double.valueOf(0));
    private List<DatoPie> listaServiciosAHAtender = new ArrayList<DatoPie>();
    private List<DatoPie> listaServiciosAHAtendidas = new ArrayList<DatoPie>();
    ///Variables valores por fase
    //private BigDecimal totalah = BigDecimal.ZERO;
    //private BigDecimal totalinfraestructura = BigDecimal.ZERO;
    private BigDecimal porcentaEjeNacional = BigDecimal.ZERO;
    private List<DatoPie> listaPersonas = new ArrayList<DatoPie>();
    private List<DatoPie> listaHogares = new ArrayList<DatoPie>();
    private Dashboard informaciondash = new Dashboard();

    public Dashboard getInformaciondash() {
        return informaciondash;
    }

    public void setInformaciondash(Dashboard informaciondash) {
        this.informaciondash = informaciondash;
    }
    private DashboardConvenios informaciondashconvenios = new DashboardConvenios();

    public DashboardConvenios getInformaciondashconvenios() {
        return informaciondashconvenios;
    }

    public void setInformaciondashconvenios(DashboardConvenios informaciondashconvenios) {
        this.informaciondashconvenios = informaciondashconvenios;
    }
    private DashboardAh informaciondashah = new DashboardAh();

    public DashboardAh getInformaciondashah() {
        return informaciondashah;
    }

    public void setInformaciondashah(DashboardAh informaciondashah) {
        this.informaciondashah = informaciondashah;
    }
    /**
     * Numero de proyectos aprobados
     */
    private BigDecimal numAprobado;
    /**
     * Número de proyectos terminados
     */
    private BigDecimal numTerminado;
    /**
     * Valor de recursos aprobado
     */
    private BigDecimal valorAprobado;
    /**
     * Valor de recursos ejecutados
     */
    private BigDecimal valorEjecutado;
    /**
     * Cantidad de proyectos en estado crítico
     */
    private BigDecimal numProyCriticos;

    public BigDecimal getNumAprobado() {
        return numAprobado;
    }

    public void setNumAprobado(BigDecimal numAprobado) {
        this.numAprobado = numAprobado;
    }

    public BigDecimal getNumTerminado() {
        return numTerminado;
    }

    public void setNumTerminado(BigDecimal numTerminado) {
        this.numTerminado = numTerminado;
    }

    public BigDecimal getValorAprobado() {
        return valorAprobado;
    }

    public void setValorAprobado(BigDecimal valorAprobado) {
        this.valorAprobado = valorAprobado;
    }

    public BigDecimal getValorEjecutado() {
        return valorEjecutado;
    }

    public void setValorEjecutado(BigDecimal valorEjecutado) {
        this.valorEjecutado = valorEjecutado;
    }

    public BigDecimal getNumProyCriticos() {
        return numProyCriticos;
    }

    public void setNumProyCriticos(BigDecimal numProyCriticos) {
        this.numProyCriticos = numProyCriticos;
    }

    public BigDecimal getPorcentaEjeNacional() {
        return porcentaEjeNacional;
    }

    public void setPorcentaEjeNacional(BigDecimal porcentaEjeNacional) {
        this.porcentaEjeNacional = porcentaEjeNacional;
    }

    public List<DatoPie> getListaHogares() {
        return listaHogares;
    }

    public void setListaHogares(List<DatoPie> listaHogares) {
        this.listaHogares = listaHogares;
    }

    public List<DatoPie> getListaPersonas() {
        return listaPersonas;
    }

    public void setListaPersonas(List<DatoPie> listaPersonas) {
        this.listaPersonas = listaPersonas;
    }

//    public BigDecimal getTotalah() {
//        return totalah;
//    }
//
//    public void setTotalah(BigDecimal totalah) {
//        this.totalah = totalah;
//    }
//
//    public BigDecimal getTotalinfraestructura() {
//        return totalinfraestructura;
//    }
//
//    public void setTotalinfraestructura(BigDecimal totalinfraestructura) {
//        this.totalinfraestructura = totalinfraestructura;
//    }
//    public BigDecimal getInversionRegion() {
//        return inversionRegion;
//    }
//
//    public void setInversionRegion(BigDecimal inversionRegion) {
//        this.inversionRegion = inversionRegion;
//    }
    public BigDecimal getPorcentajeCartera() {
        return porcentajeCartera.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getPorcentajePago() {
        return porcentajePago.setScale(2, RoundingMode.HALF_UP);
    }

    public void setPorcentajePago(BigDecimal porcentajePago) {
        this.porcentajePago = porcentajePago;
    }

    public BigDecimal getTotalPago() {
        return totalPago.setScale(2, RoundingMode.HALF_UP);
    }

    public void setTotalPago(BigDecimal totalPago) {
        this.totalPago = totalPago;
    }

    public void setPorcentajeCartera(BigDecimal porcentajeCartera) {
        this.porcentajeCartera = porcentajeCartera;
    }

    public BigDecimal getTotalCartera() {
        return totalCartera.setScale(2, RoundingMode.HALF_UP);
    }

    public void setTotalCartera(BigDecimal totalCartera) {
        this.totalCartera = totalCartera;
    }

    public boolean isInvporregion() {
        return invporregion;
    }

    public void setInvporregion(boolean invporregion) {
        this.invporregion = invporregion;
    }

    public List<Imagenevolucionobra> getListaimagenesslider() {
        return listaimagenesslider;
    }

    public void setListaimagenesslider(List<Imagenevolucionobra> listaimagenesslider) {
        this.listaimagenesslider = listaimagenesslider;
    }

    public BigDecimal getPorcentajeLegalizado() {
        return porcentajeLegalizado.setScale(2, RoundingMode.HALF_UP);
    }

    public void setPorcentajeLegalizado(BigDecimal porcentajeLegalizadoObra) {
        this.porcentajeLegalizado = porcentajeLegalizadoObra;
    }

    public BigDecimal getTotalLegalizado() {
        return totalLegalizado.setScale(2, RoundingMode.HALF_UP);
    }

    public void setTotalLegalizado(BigDecimal totalLegalizadoObra) {
        this.totalLegalizado = totalLegalizadoObra;
    }

    public int getPorejecutado() {
        return porejecutado;
    }

    public void setPorejecutado(int porejecutado) {
        this.porejecutado = porejecutado;
    }

//    public BigDecimal getEjecutadoconvenio() {
//        return ejecutadoconvenio;
//    }
//
//    public void setEjecutadoconvenio(BigDecimal ejecutadoconvenio) {
//        this.ejecutadoconvenio = ejecutadoconvenio;
//    }
//    public BigDecimal getPorcentageEje() {
//        return porcentageEje;
//    }
//
//    public void setPorcentageEje(BigDecimal porcentageEje) {
//        this.porcentageEje = porcentageEje;
//    }
//    public BigDecimal getInversion() {
//        return inversion;
//    }
//
//    public void setInversion(BigDecimal inversion) {
//        this.inversion = inversion;
//    }
    public int getIdconsolidadoselec() {
        return idconsolidadoselec;
    }

    public void setIdconsolidadoselec(int idconsolidadoselec) {
        this.idconsolidadoselec = idconsolidadoselec;
    }

    public SelectItem[] getItemsCortes() {
        return itemsCortes;
    }

    public void setItemsCortes(SelectItem[] itemsCortes) {
        this.itemsCortes = itemsCortes;
    }

    public FiltroGerencial getFiltrogeren() {
        return filtrogeren;
    }

    public void setFiltrogeren(FiltroGerencial filtrogeren) {
        this.filtrogeren = filtrogeren;
    }

    public GraficoBarrasHorizontal getBarrasatencion() {
        return barrasatencion;
    }

    public void setBarrasatencion(GraficoBarrasHorizontal barrasatencion) {
        this.barrasatencion = barrasatencion;
    }

    public GraficoBarrasHorizontal getBarrashogares() {
        return barrashogares;
    }

    public void setBarrashogares(GraficoBarrasHorizontal barrashogares) {
        this.barrashogares = barrashogares;
    }

    public GraficoBarrasHorizontal getBarraspersonas() {
        return barraspersonas;
    }

    public void setBarraspersonas(GraficoBarrasHorizontal barraspersonas) {
        this.barraspersonas = barraspersonas;
    }

    public GraficoColumnaPorcentual getColumnaproyectoconsolidado() {
        return columnaproyectoconsolidado;
    }

    public void setColumnaproyectoconsolidado(GraficoColumnaPorcentual columnaproyectoconsolidado) {
        this.columnaproyectoconsolidado = columnaproyectoconsolidado;
    }

    public GraficoPieDashboard getPiecontrol() {
        return piecontrol;
    }

    public void setPiecontrol(GraficoPieDashboard piecontrol) {
        this.piecontrol = piecontrol;
    }

    public GraficoColumnaPorcentual getColumnalegalizacion() {
        return columnalegalizacion;
    }

    public void setColumnalegalizacion(GraficoColumnaPorcentual columnalegalizacion) {
        this.columnalegalizacion = columnalegalizacion;
    }

    public GraficoColumnaPorcentual getColumnapagocontratistas() {
        return columnapagocontratistas;
    }

    public void setColumnapagocontratistas(GraficoColumnaPorcentual columnapagocontratistas) {
        this.columnapagocontratistas = columnapagocontratistas;
    }

    public GraficoColumnaPorcentual getColumnatotalgeneral() {
        return columnatotalgeneral;
    }

    public void setColumnatotalgeneral(GraficoColumnaPorcentual columnatotalgeneral) {
        this.columnatotalgeneral = columnatotalgeneral;
    }

    public String getDeptos() {
        return deptos;
    }

    public void setDeptos(String deptos) {
        this.deptos = deptos;
    }

    public String getMuni() {
        return muni;
    }

    public void setMuni(String muni) {
        this.muni = muni;
    }

    public SelectItem[] getSemuni() {
        return semuni;
    }

    public void setSemuni(SelectItem[] semuni) {
        this.semuni = semuni;
    }

    public SelectItem[] getSedeptos() {
        return sedeptos;
    }

    public void setSedeptos(SelectItem[] sedeptos) {
        this.sedeptos = sedeptos;
    }

    public int getVistaicom() {
        return vistaicom;
    }

    public void setVistaicom(int vistaicom) {
        this.vistaicom = vistaicom;
    }

    public int getIntzona() {
        return intzona;
    }

    public void setIntzona(int intzona) {
        this.intzona = intzona;
    }

    public SelectItem[] getZonaespecifica() {
        return zonaespecifica;
    }

    public void setZonaespecifica(SelectItem[] zonaespecifica) {
        this.zonaespecifica = zonaespecifica;
    }

    public List<DatoPie> getListaServiciosAHAtender() {
        return listaServiciosAHAtender;
    }

    public void setListaServiciosAHAtender(List<DatoPie> listaServiciosAHAtender) {
        this.listaServiciosAHAtender = listaServiciosAHAtender;
    }

    public List<DatoPie> getListaServiciosAHAtendidas() {
        return listaServiciosAHAtendidas;
    }

    public void setListaServiciosAHAtendidas(List<DatoPie> listaServiciosAHAtendidas) {
        this.listaServiciosAHAtendidas = listaServiciosAHAtendidas;
    }

    public ControlGerencial() {
        llenarZonaEspecifica();
        llenarDeptos();
        llenarMunici();
        llenarCortes();
        filtrogeren.setLocalidad("169");
        filtrogeren.setZona(0);
        informaciondash = getSessionBeanCobra().getIndicadorService().obtenerInformacionDashboard(filtrogeren);

    }

    public String iniciargerencial() {

        vistaicom = 0;
        filtrogeren.setLocalidad("169");
        filtrogeren.setZona(0);
        filtrogeren.setLog(idconsolidadoselec);

        iniciarSlider();
        //calcularInversion();
        //Graficosbarras();
        informaciondash = getSessionBeanCobra().getIndicadorService().obtenerInformacionDashboard(filtrogeren);
        GraficarAtencionHogaresPersonas();
        GraficarPie();
        cargarServiciosAtencionHumanitaria();
        //cargarValoresxFase();  


        return "tablero";
    }

//    public void llenarTacometro() {
//        DatoPie datopietacometro = getSessionBeanCobra().getIndicadorService().PieProyectoConvenioFinanciero(filtrogeren);
//        ejecutadoconvenio = BigDecimal.valueOf(Double.valueOf(datopietacometro.getEtiqueta()));
//        porcentageEje = BigDecimal.valueOf(Double.valueOf(datopietacometro.getValor()));
//    }
    public void llenarCortes() {
        List<LogConsolidado> listacortes = getSessionBeanCobra().getIndicadorService().obtenerLogConsolidados();
        int i = 0;
        itemsCortes = new SelectItem[listacortes.size()];
        while (i < listacortes.size()) {

            SelectItem opt = new SelectItem(listacortes.get(i).getIdconsolidado(), "Consolidado " + listacortes.get(i).getIdconsolidado() + " - " + listacortes.get(i).getFechaCorte());
            if (i == 0) {
                idconsolidadoselec = listacortes.get(i).getIdconsolidado();
            }
            itemsCortes[i] = opt;
            i++;
        }

    }

    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }

    public String llenarZonaEspecifica() {
        getSessionBeanCobra().getCobraService().setListazonaespecificas(getSessionBeanCobra().getCobraService().encontrarZonaespecifica());
        zonaespecifica = new SelectItem[getSessionBeanCobra().getCobraService().getListazonaespecificas().size()];
        int i = 0;
        for (Zonaespecifica zona : getSessionBeanCobra().getCobraService().getListazonaespecificas()) {
            SelectItem zon = new SelectItem(zona.getIntidzonaespecifica(), zona.getStrdescripcionzona().toUpperCase());
            if (i == 0) {
                intzona = 0;
            }
            zonaespecifica[i++] = zon;
        }
        return null;
    }

    public String llenarDeptos() {
        getSessionBeanCobra().getCobraService().setListadeptosah(getSessionBeanCobra().getCobraService().encontrarDepartamentos());
        sedeptos = new SelectItem[getSessionBeanCobra().getCobraService().getListadeptosah().size()];
        int i = 0;
        for (Localidad dept : getSessionBeanCobra().getCobraService().getListadeptosah()) {
            SelectItem dep = new SelectItem(dept.getStrcodigolocalidad(), dept.getStrdepartamento());
            if (i == 0) {
                deptos = "0";
            }
            sedeptos[i++] = dep;
        }
        return null;
    }

    public String llenarMunici() {
        getSessionBeanCobra().getCobraService().setListamuniciah(getSessionBeanCobra().getCobraService().encontrarMunicipios(deptos));
        semuni = new SelectItem[getSessionBeanCobra().getCobraService().getListamuniciah().size()];
        int i = 0;
        for (Localidad munic : getSessionBeanCobra().getCobraService().getListamuniciah()) {
            SelectItem mun = new SelectItem(munic.getStrcodigolocalidad(), munic.getStrmunicipio());
            if (i == 0) {
                muni = "0";
            }
            semuni[i++] = mun;
        }
        return null;
    }
    /*
     public void calcularInversion() {
     FiltroGerencial filtroNacional = new FiltroGerencial(0, "0", filtrogeren.getLog());
     inversion = BigDecimal.valueOf(Double.valueOf(getSessionBeanCobra().getIndicadorService().PieConsolidadoGeneral_InversionTotal(filtroNacional).getValor()));
     inversionRegion = BigDecimal.valueOf(Double.valueOf(getSessionBeanCobra().getIndicadorService().PieConsolidadoGeneral_InversionTotal(filtrogeren).getValor()));
     }
     */

    public void generarGraficos() {

        if (vistaicom == 1) {
            filtrogeren.setZona(intzona);
            invporregion = true;
            if (intzona == 0) {
                filtrogeren.setLocalidad("169");
                invporregion = false;
            } else {
                filtrogeren.setLocalidad("0");
            }

        } else {
            if (!muni.equals("0")) {
                filtrogeren.setLocalidad(muni);
            } else {
                filtrogeren.setLocalidad(deptos);
            }
            filtrogeren.setZona(0);
            invporregion = true;
            if (deptos.equals("0")) {
                filtrogeren.setLocalidad("169");
                invporregion = false;
            }

        }

        filtrogeren.setLog(idconsolidadoselec);
//        if (deptos.compareTo("0") != 0 || muni.compareTo("0") != 0) {
//            invporregion = true;
//        } else {
//            invporregion = false;
//        }

        //calcularInversion();
        //Graficosbarras();   
        informaciondash = getSessionBeanCobra().getIndicadorService().obtenerInformacionDashboard(filtrogeren);
        cargarServiciosAtencionHumanitaria();

        GraficarAtencionHogaresPersonas();
        GraficarPie();
        //llenarTacometro();
        iniciarSlider();
        //cargarValoresxFase();



    }

    public void Graficosbarras() {

        List<DatoPie> listaTotales = new ArrayList<DatoPie>();
        DatoPie datoPieCartera = new DatoPie();
        DatoPie datoPiePagos = new DatoPie();
        DatoPie datoPieLegalizado = new DatoPie();
        DatoPie datoPieDiferencia = new DatoPie();

        //Consultar totales en IndicadorService
        listaTotales = getSessionBeanCobra().getIndicadorService().PieDashboardAvanceFinanciero(filtrogeren);
        //Configurar variables de totales.
        for (Iterator<DatoPie> it = listaTotales.iterator(); it.hasNext();) {
            DatoPie elemento = it.next();
            //Configurar cada DatoPie
            if ((elemento.getEtiqueta().equalsIgnoreCase("cartera"))) {
                datoPieCartera.setEtiqueta(elemento.getEtiqueta());
                if (elemento.getValor() != null && elemento.getValor().compareTo("null") != 0) {
                    datoPieCartera.setValor(elemento.getValor());
                    totalCartera = BigDecimal.valueOf(Double.valueOf(elemento.getValor()));
                } else {
                    datoPieCartera.setValor("0");
                    totalCartera = CERO;
                }
            } else if ((elemento.getEtiqueta().equalsIgnoreCase("pagos"))) {
                datoPiePagos.setEtiqueta(elemento.getEtiqueta());
                if (elemento.getValor() != null && elemento.getValor().compareTo("null") != 0) {
                    datoPiePagos.setValor(elemento.getValor());
                    totalPago = BigDecimal.valueOf(Double.valueOf(elemento.getValor()));
                } else {
                    datoPiePagos.setValor("0");
                    totalPago = CERO;
                }
            } else if ((elemento.getEtiqueta().equalsIgnoreCase("legalizado"))) {
                datoPieLegalizado.setEtiqueta(elemento.getEtiqueta());
                if (elemento.getValor() != null && elemento.getValor().compareTo("null") != 0) {
                    datoPieLegalizado.setValor(elemento.getValor());
                    totalLegalizado = BigDecimal.valueOf(Double.valueOf(elemento.getValor()));
                } else {
                    datoPieLegalizado.setValor("0");
                    totalLegalizado = CERO;
                }

            }

        }
        //Calcular porcentajes
        //Porcentaje de cartera vs inversión por región
        if (informaciondash.getInversionTotalRegion().equals(CERO)) {
            porcentajeCartera = CERO;
        } else {
            porcentajeCartera = totalCartera.multiply(CIEN).divide(informaciondash.getInversionTotalRegion(), RoundingMode.FLOOR);
        }
        //Porcentaje pago a contratistas y legalización vs cartera (por separado)
        if (datoPieCartera.getValor().equalsIgnoreCase("0")) {
            porcentajePago = CERO;
            porcentajeLegalizado = CERO;
        } else {
            porcentajePago = totalPago.multiply(CIEN).divide(totalCartera, RoundingMode.FLOOR);
            porcentajeLegalizado = totalLegalizado.multiply(CIEN).divide(totalCartera, RoundingMode.FLOOR);
        }
        //Configurar gráficos
        //Gráfico de cartera
        List<DatoPie> listaCartera = new ArrayList<DatoPie>();
        datoPieDiferencia.setValor(String.valueOf(100 - porcentajeCartera.intValue()));
        //datoPieDiferencia.setValor( inversionRegion.subtract(totalCartera).toString());
        datoPieDiferencia.setDescripcion("#4169E1");
        datoPieCartera.setDescripcion("#87CEFA");
        datoPieCartera.setValor(String.valueOf(porcentajeCartera.intValue()));
        listaCartera.add(datoPieCartera);
        listaCartera.add(datoPieDiferencia);
        columnatotalgeneral = new GraficoColumnaPorcentual("inversion", "valor", "25", "30", listaCartera, "grafcolumnatotal");

        //Gráfico pago a contratista
        List<DatoPie> listaPagos = new ArrayList<DatoPie>();
        datoPieDiferencia.setValor(String.valueOf(100 - porcentajePago.intValue()));
        //datoPieDiferencia.setValor(totalCartera.subtract(totalPago).toString());
        datoPieDiferencia.setDescripcion("#D2691E");
        datoPiePagos.setDescripcion("#F4A460");
        datoPiePagos.setValor(String.valueOf(porcentajePago.intValue()));
        listaPagos.add(datoPiePagos);
        listaPagos.add(datoPieDiferencia);
        columnapagocontratistas = new GraficoColumnaPorcentual("pago", "valor", "25", "30", listaPagos, "grafcontratistas");

        //Gráfico legalización
        List<DatoPie> listaLegalizacion = new ArrayList<DatoPie>();
        datoPieDiferencia.setValor(String.valueOf(100 - porcentajeLegalizado.intValue()));
        //datoPieDiferencia.setValor(totalCartera.subtract(totalLegalizado).toString());
        datoPieDiferencia.setDescripcion("#32CD32");
        datoPieLegalizado.setDescripcion("#ADFF2F");
        datoPieLegalizado.setValor(String.valueOf(porcentajeLegalizado.intValue()));
        listaLegalizacion.add(datoPieLegalizado);
        listaLegalizacion.add(datoPieDiferencia);
        columnalegalizacion = new GraficoColumnaPorcentual("legalizado", "valor", "25", "30", listaLegalizacion, "graflegalizacion");

        List<DatoPie> listaproyectoscon = new ArrayList<DatoPie>();
        DatoPie datoproyectoconsolidado = new DatoPie();
        datoproyectoconsolidado = getSessionBeanCobra().getIndicadorService().PieAvanceObrasTerritoriales(filtrogeren);
        /**
         * TODO: Se convierte null a 0 en datoproyectoconsolidado.getValor() por
         * excepcion que se estaba presentando, queda pendiente revisar si es
         * correcto que se presente null en datoproyectoconsolidado.getValor()
         */
        porejecutado = Double.valueOf(datoproyectoconsolidado.getValor().equals("null") ? "0" : datoproyectoconsolidado.getValor()).intValue();
        datoproyectoconsolidado.setValor(String.valueOf(porejecutado));
        datoproyectoconsolidado.setDescripcion("#4169E1");
        listaproyectoscon.add(datoproyectoconsolidado);

        DatoPie datoproyectoconsolidado2 = new DatoPie();
        datoproyectoconsolidado2.setDescripcion("#87CEFA");
        datoproyectoconsolidado2.setValor(String.valueOf(100 - porejecutado));
        listaproyectoscon.add(datoproyectoconsolidado2);

        columnaproyectoconsolidado = new GraficoColumnaPorcentual("ProyectosConsolidados", "valor", "25", "30", listaproyectoscon, "grafconsolidado");
    }

    public void GraficarPie() {

        List<DatoPie> lista = new ArrayList<DatoPie>();
        //lista = getSessionBeanCobra().getIndicadorService().PieConsolidadoGeneral(filtrogeren);
        DatoPie datopie = new DatoPie();
        datopie.setEtiqueta("Obra menor");
        datopie.setValor(String.valueOf(informaciondash.getInversionObraMenor()));
        lista.add(datopie);
        datopie = new DatoPie();
        datopie.setEtiqueta("Obra mayor");
        datopie.setValor(String.valueOf(informaciondash.getInversionObraMayor()));
        lista.add(datopie);
        datopie = new DatoPie();
        datopie.setEtiqueta("Cuerpo de agua");
        datopie.setValor(String.valueOf(informaciondash.getInversionCuerposAgua()));
        lista.add(datopie);

        piecontrol = new GraficoPieDashboard("proyectospie", "valor", "descripcion", "#FFFFFF", "0.8", "2", "20", "30", lista, "proyectospie");
    }

    public void GraficarAtencionHogaresPersonas() {

        //listaPersonas = getSessionBeanCobra().getIndicadorService().PieDashboardAtencionHumanitaria_Personas(filtrogeren);
        listaPersonas = new ArrayList<DatoPie>();
        DatoPie datopie = new DatoPie();
        datopie.setEtiqueta("Damnificadas");
        datopie.setValor(String.valueOf(informaciondash.getPersdamnif()));
        listaPersonas.add(datopie);
        datopie = new DatoPie();
        datopie.setEtiqueta("Afectadas");
        datopie.setValor(String.valueOf(informaciondash.getPersafect()));
        listaPersonas.add(datopie);
        barraspersonas = new GraficoBarrasHorizontal("chartpersonas", " #ff9735\",\"#ff9735", "#266085", listaPersonas);


        //listaHogares = getSessionBeanCobra().getIndicadorService().PieDashboardAtencionHumanitaria_Hogares(filtrogeren);
        listaHogares = new ArrayList<DatoPie>();
        datopie = new DatoPie();
        datopie.setEtiqueta("Damnificadas");
        datopie.setValor(String.valueOf(informaciondash.getHogdamnif()));
        listaHogares.add(datopie);
        datopie = new DatoPie();
        datopie.setEtiqueta("Afectadas");
        datopie.setValor(String.valueOf(informaciondash.getHogafect()));
        listaHogares.add(datopie);
        barrashogares = new GraficoBarrasHorizontal("charthogares", "#4DAA42\",\"#4DAA42", "#266085", listaHogares);
    }

    public void iniciarSlider() {
        listaimagenesslider = new ArrayList<Imagenevolucionobra>();
        listaimagenesslider = getSessionBeanCobra().getCobraService().obtenerUltimasImagenesObrasAlimentadas(filtrogeren);

    }

    /**
     * Obtiene una lista de objetos de tipo DatosPie con la cantidad de
     * proyectos correspondiete a cada tipo de servicio de colombia humanitaria
     */
    public void cargarServiciosAtencionHumanitaria() {
        //getSessionBeanCobra().getIndicadorService().setFaatender(new ArrayList<DatoPie>());
        //getSessionBeanCobra().getIndicadorService().setFaatendidas(new ArrayList<DatoPie>());

        //getSessionBeanCobra().getIndicadorService().VistaFamiliasAtenderAtendidas(filtrogeren);
        listaServiciosAHAtender = new ArrayList<DatoPie>();
        listaServiciosAHAtendidas = new ArrayList<DatoPie>();
        DatoPie datopie = new DatoPie();
        datopie.setEtiqueta("Mercados y kit de aseo");
        datopie.setValor(String.valueOf(informaciondash.getMercyaseo()));
        listaServiciosAHAtendidas.add(datopie);

        datopie = new DatoPie();
        datopie.setEtiqueta("Arriendos entregados");
        datopie.setValor(String.valueOf(informaciondash.getArriendosentreg()));
        listaServiciosAHAtendidas.add(datopie);

        datopie = new DatoPie();
        datopie.setEtiqueta("Albergues construidos");
        datopie.setValor(String.valueOf(informaciondash.getAlojconstruidos()));
        listaServiciosAHAtendidas.add(datopie);

        datopie = new DatoPie();
        datopie.setEtiqueta("Viviendas reparadas");
        datopie.setValor(String.valueOf(informaciondash.getVivireparadas()));
        listaServiciosAHAtendidas.add(datopie);


        //listaServiciosAHAtender = getSessionBeanCobra().getIndicadorService().getFaatender();
        //listaServiciosAHAtendidas = getSessionBeanCobra().getIndicadorService().getFaatendidas();
    }

//    public void cargarValoresxFase() {
//        List<DatoPie> list = getSessionBeanCobra().getIndicadorService().obtenerInversionFasesxLocalidad(filtrogeren);
//        if (list.size() > 0) {
//            for (DatoPie obj : list) {
//                if (obj.getEtiqueta().compareTo("infraestructura") == 0) {
//                    totalinfraestructura = BigDecimal.valueOf(Double.valueOf(obj.getValor()));
//                } else if (obj.getEtiqueta().compareTo("atencionhum") == 0) {
//                    totalah = BigDecimal.valueOf(Double.valueOf(obj.getValor()));
//                } else {
//                   
//                    if (invporregion) {
//                        porcentageEje = informaciondash.getInversionConvenios().divide(informaciondash.getInversionTotalRegion(), RoundingMode.HALF_UP);
//                    } else {
//                        porcentageEje = informaciondash.getInversionConvenios().divide(informaciondash.getInversionTotalNal(), RoundingMode.HALF_UP);
//                    }
//                }
//            }
//        }
//        
//
//    }
    /**
     * Carga la información correspondiente a la modal del botón ver + de la
     * sección de proyectos de rehabilitación
     *
     * @return null
     */
    public String obtenerConsolidadoRehabilitacion() {
        Iterator<DatoPie> itLista = getSessionBeanCobra().getIndicadorService().obtenerConsolidadoRehabilitacion(filtrogeren).iterator();
        while (itLista.hasNext()) {
            DatoPie datoPie = itLista.next();
            if (datoPie.getEtiqueta().equals("numAprobado")) {
                numAprobado = new BigDecimal(datoPie.getValor()).setScale(0, RoundingMode.HALF_UP);
            }
            if (datoPie.getEtiqueta().equals("numTerminado")) {
                numTerminado = new BigDecimal(datoPie.getValor()).setScale(0, RoundingMode.HALF_UP);
            }
            if (datoPie.getEtiqueta().equals("valorAprobado")) {
                valorAprobado = new BigDecimal(datoPie.getValor()).setScale(0, RoundingMode.HALF_UP);
            }
            if (datoPie.getEtiqueta().equals("valorEjecutado")) {
                valorEjecutado = new BigDecimal(datoPie.getValor()).setScale(0, RoundingMode.HALF_UP);
            }
            if (datoPie.getEtiqueta().equals("numProyCriticos")) {
                numProyCriticos = new BigDecimal(datoPie.getValor()).setScale(0, RoundingMode.HALF_UP);
            }
        }
        return null;
    }

    /**
     * Obtiene el valor invertido respecto al total nacional en infraestructura
     *
     * @return
     */
    public BigDecimal getTotalNacionalInvertido() {
        BigDecimal totalNacionalInvertido = BigDecimal.ZERO;
        if (valorAprobado != null) {
            totalNacionalInvertido = valorAprobado.divide(informaciondash.getInversionTotalNal(), 2, RoundingMode.HALF_UP);
        }
        return totalNacionalInvertido;
    }

    /**
     * Genera el reporte de balance general para rehabilitacion
     *
     * @return
     */
    public String reportePdfFichaCorto() {
        try {
            if (vistaicom == 0) {
                if (deptos.equals("0")) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(Propiedad.getValor("reportepdfbalancedashrehabilitacion") + "&munici=169");
                } else {


                    if (muni.equals("0")) {
                        FacesContext.getCurrentInstance().getExternalContext().redirect(Propiedad.getValor("reportepdfbalancedashrehabilitacion") + "&munici=" + deptos);
                    } else {
                        FacesContext.getCurrentInstance().getExternalContext().redirect(Propiedad.getValor("reportepdfbalancedashrehabilitacion") + "&munici=" + muni);
                    }
                }
            } else {
                if (intzona == 0) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(Propiedad.getValor("reportepdfbalancedashrehabilitacion") + "&munici=169");
                } else {

                    FacesContext.getCurrentInstance().getExternalContext().redirect(Propiedad.getValor("reportepdfbalancedashrehabilitacionzona") + "&zona=" + intzona);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ControlGerencial.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Genera el reporte con el listado de entidades territoriales para la modal
     * de rehabilitacion
     *
     * @return
     */
    public String reportePdfFichaConvenio() {
        try {
            if (vistaicom == 0) {
                if (deptos.equals("0")) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(Propiedad.getValor("reportepdftotalfichaconvenio") + "&localidad=169");//select
                } else {

                    if (muni.equals("0")) {
                        FacesContext.getCurrentInstance().getExternalContext().redirect(Propiedad.getValor("reportepdftotalfichaconvenio") + "&localidad=" + deptos);//select
                    } else {
                        FacesContext.getCurrentInstance().getExternalContext().redirect(Propiedad.getValor("reportepdftotalfichaconvenio") + "&localidad=" + muni);//select
                    }
                }


            } else {
                if (intzona == 0) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(Propiedad.getValor("reportepdftotalfichaconvenio") + "&localidad=169");
                } else {

                    FacesContext.getCurrentInstance().getExternalContext().redirect(Propiedad.getValor("reportepdftotalfichaconvenio") + "&zona=" + intzona);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ControlGerencial.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String reportePdfFichaObras() {
        try {

            if (vistaicom == 0) {

                if (deptos.equals("0")) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(Propiedad.getValor("reportepdftotalfichaobras") + "&localidad=169");//select
                } else {

                    if (muni.equals("0")) {
                        FacesContext.getCurrentInstance().getExternalContext().redirect(Propiedad.getValor("reportepdftotalfichaobras") + "&localidad=" + deptos);//select
                    } else {
                        FacesContext.getCurrentInstance().getExternalContext().redirect(Propiedad.getValor("reportepdftotalfichaobras") + "&localidad=" + muni);//select
                    }
                }
            } else {
                if (intzona == 0) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(Propiedad.getValor("reportepdftotalfichaobras") + "&localidad=169");
                } else {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(Propiedad.getValor("reportepdftotalfichaobras") + "&zona=" + intzona);//select
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ControlGerencial.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Genera el reporte de balance general para convenios
     *
     * @return
     */
    public String reportePdfFichaBalanceConvenio() {
        try {
            if (vistaicom == 0) {
                if (deptos.equals("0")) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(Propiedad.getValor("reportepdfbalancedashconvenios") + "&munici=169");
                } else {


                    if (muni.equals("0")) {
                        FacesContext.getCurrentInstance().getExternalContext().redirect(Propiedad.getValor("reportepdfbalancedashconvenios") + "&munici=" + deptos);
                    } else {
                        FacesContext.getCurrentInstance().getExternalContext().redirect(Propiedad.getValor("reportepdfbalancedashconvenios") + "&munici=" + muni);
                    }
                }
            } else {
                if (intzona == 0) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(Propiedad.getValor("reportepdfbalancedashconvenios") + "&munici=169");
                } else {

                    FacesContext.getCurrentInstance().getExternalContext().redirect(Propiedad.getValor("reportepdfbalancedashconvenioszona") + "&zona=" + intzona);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ControlGerencial.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Genera el reporte de balance general para atención humanitaria
     *
     * @return
     */
    public String reportePdfFichaBalanceAh() {
        try {
            if (vistaicom == 0) {
                if (deptos.equals("0")) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(Propiedad.getValor("reportepdfbalancedashah") + "&munici=169");
                } else {


                    if (muni.equals("0")) {
                        FacesContext.getCurrentInstance().getExternalContext().redirect(Propiedad.getValor("reportepdfbalancedashah") + "&munici=" + deptos);
                    } else {
                        FacesContext.getCurrentInstance().getExternalContext().redirect(Propiedad.getValor("reportepdfbalancedashah") + "&munici=" + muni);
                    }
                }
            } else {
                if (intzona == 0) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(Propiedad.getValor("reportepdfbalancedashah") + "&munici=169");
                } else {

                    FacesContext.getCurrentInstance().getExternalContext().redirect(Propiedad.getValor("reportepdfbalancedashahzona") + "&zona=" + intzona);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ControlGerencial.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Carga la información correspondiente a la modal del botón ver + de la
     * sección de convenios
     *
     * @return null
     */
    public String obtenerConsolidadoConvenios() {

        informaciondashconvenios = getSessionBeanCobra().getIndicadorService().obtenerInformacionDashboardConvenios(filtrogeren);
        return null;
    }

    /**
     * Carga la información correspondiente a la modal del botón ver + de la
     * sección de Atención Humanitaria
     *
     * @return null
     */
    public String obtenerConsolidadoAh() {

        informaciondashah = getSessionBeanCobra().getIndicadorService().obtenerInformacionDashboardAh(filtrogeren);
        return null;
    }
}
