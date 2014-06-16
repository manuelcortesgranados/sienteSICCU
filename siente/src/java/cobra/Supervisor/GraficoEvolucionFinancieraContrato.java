/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cobra.Supervisor;

/**
 *
 * @author maritzabell1
 */

import co.com.interkont.cobra.to.Desembolso;
import co.com.interkont.cobra.to.Planificacionpago;
import co.com.interkont.cobra.to.utilidades.Propiedad;
import cobra.SessionBeanCobra;
import cobra.graficos.ConjuntoDatosGrafico;
import cobra.graficos.DatoGrafico;
import cobra.graficos.EstiloGrafico;
import cobra.graficos.Grafico;
import cobra.graficos.GraficoSeries;
import cobra.graficos.GraficoSeriesAmCharts;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class GraficoEvolucionFinancieraContrato implements Serializable{
    
    private GraficoSeries grafico = new GraficoSeriesAmCharts("graficoEvolucionFinancieraContrato");

    public GraficoSeries getGraficoEvolucionFinancieraContrato() {
        return grafico;
    }

    public void setGraficoEvolucionFinancieraContrato(GraficoSeries graficoEvolucionFinancieraContrato) {
        this.grafico = graficoEvolucionFinancieraContrato;
    }

    public GraficoEvolucionFinancieraContrato() {
        graficar();
    }

    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }

    protected NuevoContratoBasico getNuevoContratoBasico() {
        return (NuevoContratoBasico) FacesUtils.getManagedBean("Supervisor$Contrato");
    }

    /**
     * Genera el grácico de evolución del proyecto estableciendo el código de
     * la gráfica en atributo codigoGrafico del objeto graficoEvolucionproyecto
     */
    private void graficar() {
        BigDecimal divisor = new BigDecimal(1000000);
        BigDecimal acumuladoVlrEjecutado = new BigDecimal(0);
        BigDecimal acumuladoVlrPlanificado = new BigDecimal(0);

        List<Planificacionpago> planificacionesPago = getSessionBeanCobra().getCobraService().encontrarPlanificacionpagoxContrato(getNuevoContratoBasico().getContrato());
        Collections.sort(planificacionesPago, new Comparator() {

            public int compare(Object o1, Object o2) {
                Planificacionpago p1 = (Planificacionpago) o1;
                Planificacionpago p2 = (Planificacionpago) o2;

                if (p1.getDatefechapago().compareTo(p2.getDatefechapago()) > 0) {
                    return 1;
                } else if (p1.getDatefechapago().compareTo(p2.getDatefechapago()) < 0) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        
        ConjuntoDatosGrafico conjuntoDatosPlani = new ConjuntoDatosGrafico();
        conjuntoDatosPlani.setCodigo("plan");
        conjuntoDatosPlani.setEtiqueta(Propiedad.getValor("graEvuContratoPlani"));
        EstiloGrafico estiloDatosPlani = new EstiloGrafico();
        estiloDatosPlani.setColorSerie("#336699");
        conjuntoDatosPlani.setEstilo(estiloDatosPlani);

        if (planificacionesPago != null) {
            for (Iterator it = planificacionesPago.iterator(); it.hasNext();) {
                Planificacionpago planificacionpago = (Planificacionpago) it.next();
                DatoGrafico datoPlani = new DatoGrafico();
                datoPlani.setValorX("" + planificacionpago.getDatefechapago().getTime());
                acumuladoVlrPlanificado = acumuladoVlrPlanificado.add(planificacionpago.getNumvlrpago().divide(divisor));
                datoPlani.setValorY(acumuladoVlrPlanificado.setScale(3, RoundingMode.HALF_UP).toPlainString());
                StringBuffer stringEtiDatoPlaniActual = new StringBuffer();
                stringEtiDatoPlaniActual.append(Propiedad.getValor("graEvuContratoEtiDato")).append(" ").append(planificacionpago.getDatefechapago()).append(" ").append(Propiedad.getValor("graEvuContratoEtiDatoPlan")).append(planificacionpago.getNumvlrpago().divide(divisor).setScale(3, RoundingMode.HALF_UP).toPlainString());
                datoPlani.setEtiqueta(stringEtiDatoPlaniActual.toString());
                conjuntoDatosPlani.getListaDatos().add(datoPlani);
            }
        }
        grafico.getConjuntosDatosGrafico().add(conjuntoDatosPlani);
        
        List<Desembolso> desembolsos = getSessionBeanCobra().getCobraService().encontrarDesembolsoxContrato(getNuevoContratoBasico().getContrato());
        Collections.sort(desembolsos, new Comparator() {

            public int compare(Object o1, Object o2) {
                Desembolso p1 = (Desembolso) o1;
                Desembolso p2 = (Desembolso) o2;

                if (p1.getFecha().compareTo(p2.getFecha()) > 0) {
                    return 1;
                } else if (p1.getFecha().compareTo(p2.getFecha()) < 0) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        
        ConjuntoDatosGrafico conjuntoDatosEje = new ConjuntoDatosGrafico();
        conjuntoDatosEje.setCodigo("eje");
        conjuntoDatosEje.setEtiqueta(Propiedad.getValor("graEvuContratoEjec"));
        EstiloGrafico estiloDatosEje = new EstiloGrafico();
        estiloDatosEje.setColorSerie("#FF0000");
        conjuntoDatosEje.setEstilo(estiloDatosEje);

        if (desembolsos != null) {
            for (Iterator it = desembolsos.iterator(); it.hasNext();) {
                Desembolso desembolso = (Desembolso) it.next();
                DatoGrafico datoEje = new DatoGrafico();
                datoEje.setValorX("" + desembolso.getFecha().getTime());
                acumuladoVlrEjecutado = acumuladoVlrEjecutado.add(desembolso.getValor().divide(divisor));
                datoEje.setValorY(acumuladoVlrEjecutado.setScale(3, RoundingMode.HALF_UP).toPlainString());

                StringBuffer stringEtiDatoEjecutado = new StringBuffer();
                stringEtiDatoEjecutado.append(Propiedad.getValor("graEvuContratoEtiDato")).append(" ").append(desembolso.getFecha()).append(" ").append(Propiedad.getValor("graEvuContratoEtiDatoPlan")).append(desembolso.getValor().divide(divisor).setScale(3, RoundingMode.HALF_UP).toPlainString());
                datoEje.setEtiqueta(stringEtiDatoEjecutado.toString());
                conjuntoDatosEje.getListaDatos().add(datoEje);
            }
        }
        grafico.getConjuntosDatosGrafico().add(conjuntoDatosEje);

        
        
        grafico.setTipoGrafico(GraficoSeries.TIPO_LINEAS);
        grafico.getEstilo().setVerLeyenda(true);
        grafico.getEstilo().setVerCursor(true);
        grafico.setTipoDatoEjeX(Grafico.FECHA);
        grafico.getEstilo().setUselinealegenda(true);
        grafico.getEstilo().setNumcolumnaslegenda("2");
        grafico.getEstilo().setZoomcursor(true);
        grafico.getEstilo().setColortextocursor(Propiedad.getValor("graevuproyetextocursor"));
        grafico.getEstilo().setColorfondoocursor(Propiedad.getValor("graevuproyecolorcursor"));
        grafico.getEstilo().setAnimado(true);
        grafico.getEstilo().setEvolucionproyectociudadano(true);
        grafico.setTituloEjeY(Propiedad.getValor("graEvuContratoTituloEjeYMillones"));
        
        
        grafico.generarGrafico();
    }
    
}
