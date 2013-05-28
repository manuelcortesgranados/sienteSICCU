/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.indicadores;

import co.com.interkont.cobra.to.AvanceAtencionHumanitaria;
import co.com.interkont.cobra.to.ConsolidadoGeneral;
import co.com.interkont.cobra.to.Localidad;
import co.com.interkont.cobra.to.LogConsolidado;
import co.com.interkont.cobra.to.PresupuestoEjecucion;
import co.com.interkont.cobra.to.Zonaespecifica;
import cobra.FiltroLocalidades;
import cobra.SessionBeanCobra;
import cobra.Supervisor.FacesUtils;
import com.interkont.cobra.util.DatoBarrasComparativo;
import com.interkont.cobra.util.DatoPie;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.model.SelectItem;

/**
 * <p>Fragment bean that corresponds to a similarly named JSP page fragment.
 * This class contains component definitions (and initialization code) for all
 * components that you have defined on this fragment, as well as lifecycle
 * methods and event handlers where you may add behavior to respond to incoming
 * events.</p>
 *
 * @version Alimentar.java
 * @version Created on 13-oct-2010, 22:27:32
 * @author carlosalbertoloaizaguerrero
 */
public class GraficarIndicadores  implements Serializable {
    // <editor-fold defaultstate="collapsed" desc="Managed Component Definition">

    private GraficoPie piepresupuesto;
    private GraficoRadar radarconvenios;
    private GraficoBarrasComparativo barrascomparativoentterr;
    private GraficoBarrasComparativo barrascomparativoentnac;
    private GraficoBarrasComparativo barrascomparativofinancieroentnac;
    private ResourceBundle bundle = getSessionBeanCobra().getBundle();
    private SelectItem[] itemsCortes;
    private int indicadorselec = 0;
    private List<AvanceAtencionHumanitaria> listaavanceshum = new ArrayList<AvanceAtencionHumanitaria>();
    private List<ConsolidadoGeneral> listaconsolidadosgen = new ArrayList<ConsolidadoGeneral>();
    private int seleccionmenu = 0;
    private SelectItem[] itemsIndicadores;
    private FiltroLocalidades filtrolocalidad;
    private SelectItem[] zonaespecifica;
    private SelectItem[] sedeptos;
    private SelectItem[] semuni;

    public FiltroLocalidades getFiltrolocalidad() {
        return filtrolocalidad;
    }

    public void setFiltrolocalidad(FiltroLocalidades filtrolocalidad) {
        this.filtrolocalidad = filtrolocalidad;
    }

    public SelectItem[] getItemsIndicadores() {
        return itemsIndicadores;
    }

    public void setItemsIndicadores(SelectItem[] itemsIndicadores) {
        this.itemsIndicadores = itemsIndicadores;
    }

    public int getSeleccionmenu() {
        return seleccionmenu;
    }

    public void setSeleccionmenu(int seleccionmenu) {
        this.seleccionmenu = seleccionmenu;
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public GraficoRadar getRadarconvenios() {
        return radarconvenios;
    }

    public void setRadarconvenios(GraficoRadar radarconvenios) {
        this.radarconvenios = radarconvenios;
    }

    public List<ConsolidadoGeneral> getListaconsolidadosgen() {
        return listaconsolidadosgen;
    }

    public void setListaconsolidadosgen(List<ConsolidadoGeneral> listaconsolidadosgen) {
        this.listaconsolidadosgen = listaconsolidadosgen;
    }

    public GraficoBarrasComparativo getBarrascomparativofinancieroentnac() {
        return barrascomparativofinancieroentnac;
    }

    public void setBarrascomparativofinancieroentnac(GraficoBarrasComparativo barrascomparativofinancieroentnac) {
        this.barrascomparativofinancieroentnac = barrascomparativofinancieroentnac;
    }

    public GraficoBarrasComparativo getBarrascomparativoentnac() {
        return barrascomparativoentnac;
    }

    public void setBarrascomparativoentnac(GraficoBarrasComparativo barrascomparativoentnac) {
        this.barrascomparativoentnac = barrascomparativoentnac;
    }

    public GraficoBarrasComparativo getBarrascomparativoentterr() {
        return barrascomparativoentterr;
    }

    public void setBarrascomparativoentterr(GraficoBarrasComparativo barrascomparativoentterr) {
        this.barrascomparativoentterr = barrascomparativoentterr;
    }

    public List<AvanceAtencionHumanitaria> getListaavanceshum() {
        return listaavanceshum;
    }

    public void setListaavanceshum(List<AvanceAtencionHumanitaria> listaavanceshum) {
        this.listaavanceshum = listaavanceshum;
    }

    public int getIndicadorselec() {
        return indicadorselec;
    }

    public void setIndicadorselec(int indicadorselec) {
        this.indicadorselec = indicadorselec;
    }

    public SelectItem[] getItemsCortes() {
        return itemsCortes;
    }

    public void setItemsCortes(SelectItem[] itemsCortes) {
        this.itemsCortes = itemsCortes;
    }

    public GraficoPie getPiepresupuesto() {
        return piepresupuesto;
    }

    public void setPiepresupuesto(GraficoPie piepresupuesto) {
        this.piepresupuesto = piepresupuesto;
    }

    /**
     * <p>Automatically managed component initialization.
     * <strong>WARNING:</strong> This method is automatically generated, so any
     * user-specified code inserted here is subject to being replaced.</p>
     */
    private void _init() throws Exception {
    }
    // </editor-fold>

    public GraficarIndicadores() {
        seleccionmenu = 0;


        filtrolocalidad = new FiltroLocalidades(getSessionBeanCobra().getCobraService().encontrarZonasEspecificas(), getSessionBeanCobra().getCobraService().encontrarDepartamentos(),
                getSessionBeanCobra().getCobraService().encontrarMunicipios("0"));
        llenarCortes();
        iniciarGrafico();
        llenarDeptos(getSessionBeanCobra().getCobraService().encontrarDepartamentos());
        llenarMunici(getSessionBeanCobra().getCobraService().encontrarMunicipios("16917"));



    }

    public String iniciarGrafico() {



        switch (indicadorselec) {
            case 0:
                List<PresupuestoEjecucion> listapresup = getSessionBeanCobra().getIndicadorService().
                        obtenerPresupuestoEjecucionesxcorte(filtrolocalidad);

                int i = 0;
                List<DatoPie> lista = new ArrayList<DatoPie>();
                while (i < listapresup.size()) {
                    DatoPie dato = new DatoPie(listapresup.get(i).getEtiqueta(), listapresup.get(i).getValor().toString(), "");

                    lista.add(dato);
                    i++;
                }
                piepresupuesto = new GraficoPie("etiqueta", "valor", "descripcion", "#FFFFFF", "0.8", "2", "25", "30", lista, "presupuestoejec");
                break;
            case 1:
                listaconsolidadosgen = getSessionBeanCobra().getIndicadorService().
                        obtenerConsolidadoGeneralxcorte(filtrolocalidad);
                break;
            case 2:
                listaavanceshum = getSessionBeanCobra().getIndicadorService().
                        obtenerAvancesAtencionHumanitariaxcorte(filtrolocalidad);
                break;

            case 3:
//                List<AvanceObrasTerritoriales> listaentterri = getSessionBeanCobra().getIndicadorService().
//                        obtenerAvancesObrasTerritoriales(filtrolocalidad);

                i = 0;
                List<DatoBarrasComparativo> lista2 = getSessionBeanCobra().getIndicadorService().
                        obtenerAvancesObrasTerritoriales(filtrolocalidad);
//                while (i < listaentterri.size()) {
//                    DatoBarrasComparativo dato = new DatoBarrasComparativo(listaentterri.get(i).getEtiqueta(), listaentterri.get(i).getNumAprobado().toString(), listaentterri.get(i).getNumTerminado().toString(), "");
//
//                    lista2.add(dato);
//                    i++;
//                }
                barrascomparativoentterr = new GraficoBarrasComparativo("etiqueta", "Aprobado", "Terminado", "25", "30", lista2, "comparatiobrasterr", true);
                break;

            case 4:
//                List<ProyectoConvenio> listaentnac = getSessionBeanCobra().getIndicadorService().
//                        obtenerAvancesObrasConvenios(filtrolocalidad);

                //i = 0;
                List<DatoBarrasComparativo> lista3 = getSessionBeanCobra().getIndicadorService().
                        obtenerAvancesObrasConvenios(filtrolocalidad);
//                while (i < listaentnac.size()) {
//                    DatoBarrasComparativo dato = new DatoBarrasComparativo(listaentnac.get(i).getEtiqueta(), listaentnac.get(i).getNumProyProg().toString(), listaentnac.get(i).getNumProyTerm().toString(), "");
//
//                    lista3.add(dato);
//                    i++;
//                }
                barrascomparativoentnac = new GraficoBarrasComparativo("etiqueta", "Aprobado", "Terminado", "25", "30", lista3, "comparatiobrasnac", false);
                break;

            case 5:
//                List<ProyectoConvenioFinan> listafinanentnac = getSessionBeanCobra().getIndicadorService().
//                        obtenerAvancesFinancierosObrasConvenios(filtrolocalidad);
//
//                i = 0;
                List<DatoBarrasComparativo> lista4 = getSessionBeanCobra().getIndicadorService().
                        obtenerAvancesFinancierosObrasConvenios(filtrolocalidad);
//                while (i < listafinanentnac.size()) {
//
//
//                    DatoBarrasComparativo dato = new DatoBarrasComparativo(listafinanentnac.get(i).getEtiqueta(), listafinanentnac.get(i).getValorAsignado().toString(),
//                            listafinanentnac.get(i).getValorEjecutado().toString(), "");
//
//                    lista4.add(dato);
//                    i++;
//                }
                barrascomparativofinancieroentnac = new GraficoBarrasComparativo("etiqueta", "Asignado", "Ejecutado", "25", "30", lista4, "comparatifinan", true);
                break;
            case 6:
//                List<ConvenioFisicoDpto> listaconvdepto = getSessionBeanCobra().getIndicadorService().
//                        obtenerConveniosFisicosDeptoxcorte(filtrolocalidad);
//
//                i = 0;
                List<DatoPie> listaconv = getSessionBeanCobra().getIndicadorService().
                        obtenerConveniosFisicosDeptoxcorte(filtrolocalidad);
//                while (i < listaconvdepto.size()) {
//                    DatoPie dato = new DatoPie(listaconvdepto.get(i).getEtiqueta(), listaconvdepto.get(i).getPorcentajeProm().toString(), "");
//
//                    listaconv.add(dato);
//                    i++;
//                }
                radarconvenios = new GraficoRadar("etiqueta", "valor", "%", "#FFFFFF", "0.8", "2", "25", "30", listaconv, "convdepto");
                break;
        }


        return null;
    }

    public void llenarCortes() {
        List<LogConsolidado> listacortes = getSessionBeanCobra().getIndicadorService().obtenerLogConsolidados();
        int i = 0;
        itemsCortes = new SelectItem[listacortes.size()];
        while (i < listacortes.size()) {

            SelectItem opt = new SelectItem(listacortes.get(i).getIdconsolidado(), "Consolidado " + listacortes.get(i).getIdconsolidado() + " - " + listacortes.get(i).getFechaCorte());
            if (i == (listacortes.size() - 1)) {

                filtrolocalidad.setIdconsolidadoselec(listacortes.get(i).getIdconsolidado());

            }
            itemsCortes[i] = opt;
            i++;
        }


    }

    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }

    public String verPresupuestoEjec() {
        indicadorselec = 0;
        iniciarGrafico();
        return null;
    }

    public String verAvanceAH() {
        indicadorselec = 2;
        iniciarGrafico();
        return null;
    }

    public String verAvanceOET() {
        indicadorselec = 3;
        iniciarGrafico();
        return null;
    }

    public String verAvanceOEN() {
        indicadorselec = 4;
        iniciarGrafico();
        return null;
    }

    public String verAvanceFinancieroOEN() {
        indicadorselec = 5;
        iniciarGrafico();
        return null;
    }

    public String verConsolidado() {
        indicadorselec = 1;
        iniciarGrafico();
        return null;
    }

    public String verRadarConvenio() {
        indicadorselec = 6;
        iniciarGrafico();
        return null;
    }
    boolean boolconsolidado = false;

    public boolean isBoolconsolidado() {
        return boolconsolidado;
    }

    public void setBoolconsolidado(boolean boolconsolidado) {
        this.boolconsolidado = boolconsolidado;
    }

    public String consolidar() {

        boolean con = getSessionBeanCobra().getIndicadorService().consolidar();
        if (con) {
            boolconsolidado = true;
        }
        return null;
    }

    public String menuConsolidados() {
        seleccionmenu = 0;
        indicadorselec = 0;
        iniciarGrafico();
        filtrolocalidad.setVerfiltroloc(false);
        return null;
    }

    public String menuAvanceAh() {
        seleccionmenu = 1;
        indicadorselec = 2;
        iniciarGrafico();
        filtrolocalidad.setVerfiltroloc(false);
        return null;
    }

    public String menuAvanceObras() {

        filtrolocalidad = new FiltroLocalidades(getSessionBeanCobra().getCobraService().encontrarZonasEspecificas(), getSessionBeanCobra().getCobraService().encontrarDepartamentos(),
                getSessionBeanCobra().getCobraService().encontrarMunicipios("0"));
        seleccionmenu = 2;
        indicadorselec = 3;
        iniciarGrafico();
        filtrolocalidad.setVerfiltroloc(true);
        return null;
    }

    public String menuAvanceConvenios() {
        indicadorselec = 4;
        iniciarGrafico();
        seleccionmenu = 3;
        filtrolocalidad.setVerfiltroloc(false);
        return null;
    }

    public String llenarMunicipios() {
        llenarMunici(getSessionBeanCobra().getCobraService().encontrarMunicipios(filtrolocalidad.getDeptoselec()));

        return null;
    }

    public String cambioIndicador() {
        filtrolocalidad = new FiltroLocalidades(getSessionBeanCobra().getCobraService().encontrarZonasEspecificas(), getSessionBeanCobra().getCobraService().encontrarDepartamentos(),
                getSessionBeanCobra().getCobraService().encontrarMunicipios("0"));
        switch (indicadorselec) {
            case 0:
            case 2:
            case 4:
                filtrolocalidad.setVerfiltroloc(false);
                break;
            case 1:
            case 3:
            case 5:
            case 6:
                filtrolocalidad.setVerfiltroloc(true);
                break;


        }

        return null;
    }

    public SelectItem[] getSedeptos() {
        return sedeptos;
    }

    public void setSedeptos(SelectItem[] sedeptos) {
        this.sedeptos = sedeptos;
    }

    public SelectItem[] getSemuni() {
        return semuni;
    }

    public void setSemuni(SelectItem[] semuni) {
        this.semuni = semuni;
    }

    public SelectItem[] getZonaespecifica() {
        return zonaespecifica;
    }

    public void setZonaespecifica(SelectItem[] zonaespecifica) {
        this.zonaespecifica = zonaespecifica;
    }

    public String llenarZonaEspecifica(List<Zonaespecifica> listazonas) {

        zonaespecifica = new SelectItem[listazonas.size()];
        int i = 0;
        for (Zonaespecifica zona : listazonas) {
            SelectItem zon = new SelectItem(zona.getIntidzonaespecifica(), zona.getStrdescripcionzona().toUpperCase());
            if (i == 0) {
                filtrolocalidad.setIntzona(0);
            }
            zonaespecifica[i++] = zon;
        }
        return null;
    }

    public String llenarDeptos(List<Localidad> listadeptos) {
        sedeptos = new SelectItem[listadeptos.size()];
        int i = 0;
        for (Localidad dept : listadeptos) {
            SelectItem dep = new SelectItem(dept.getStrcodigolocalidad(), dept.getStrdepartamento());
            sedeptos[i++] = dep;
        }

        return null;
    }

    public String llenarMunici(List<Localidad> listamunicipios) {

        semuni = new SelectItem[listamunicipios.size()];
        int i = 0;
        for (Localidad munic : listamunicipios) {
            SelectItem mun = new SelectItem(munic.getStrcodigolocalidad(), munic.getStrmunicipio());

            semuni[i++] = mun;
        }
        return null;
    }
}
