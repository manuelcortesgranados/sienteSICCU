/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.Supervisor;

import co.com.interkont.cobra.to.Alimentacion;
import co.com.interkont.cobra.to.Contrato;
import co.com.interkont.cobra.to.Obra;
import co.com.interkont.cobra.to.Periodo;
import co.com.interkont.cobra.to.Periodohisto;
import co.com.interkont.cobra.to.utilidades.Propiedad;
import cobra.SessionBeanCobra;
import cobra.graficos.ConjuntoDatosGrafico;
import cobra.graficos.DatoGrafico;
import cobra.graficos.Grafico;
import cobra.graficos.GraficoSeries;
import cobra.graficos.GraficoSeriesAmCharts;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author ricardo.fajardo
 */
public class GraficoEvolucionFisicoConvenio implements Serializable {

    private GraficoSeries grafico = new GraficoSeriesAmCharts("grafEvoFisConvenio");

    public GraficoSeries getGrafico() {
        return grafico;
    }

    public void setGrafico(GraficoSeries grafico) {
        this.grafico = grafico;
    }

    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }

    protected NuevoContratoBasico getContratoBasico() {
        return (NuevoContratoBasico) FacesUtils.getManagedBean("Supervisor$Contrato");
    }

    public GraficoEvolucionFisicoConvenio() {

        graficar();
    }

    /**
     * Genera el grácico de evolución del proyecto estableciendo el código de la gráfica en atributo codigoGrafico del
     * objeto graficoEvolucionproyecto
     */
    protected void preGraficar() {

        Contrato convenio = getSessionBeanCobra().getCobraService().encontrarContratoPorNumero(getContratoBasico().getContrato().getStrnumcontrato());
        List<Obra> obraxContratos = getSessionBeanCobra().getCobraService().encontrarObraxContratos(convenio.getIntidcontrato(), " ", 0, 20, convenio.getBooltipocontratoconvenio(), getSessionBeanCobra().getUsuarioObra(), true);

        BigDecimal divisor = new BigDecimal(1);
        BigDecimal vlrEjecutado;
        BigDecimal acumuladoVlrEjecutado = new BigDecimal(0);
        BigDecimal acumuladoVlrPlanificado = new BigDecimal(0);
        BigDecimal acumuladoVlrPlanificadoIni = new BigDecimal(0);

        ConjuntoDatosGrafico conjuntoDatosPlanActual = new ConjuntoDatosGrafico();
        conjuntoDatosPlanActual.setCodigo("planactual");
        conjuntoDatosPlanActual.setEtiqueta(Propiedad.getValor("graevuproyplani"));
        conjuntoDatosPlanActual.getEstilo().setColorSerie(Propiedad.getValor("graevuproyecolor1"));

        ConjuntoDatosGrafico conjuntoDatosEjecucionActual = new ConjuntoDatosGrafico();
        conjuntoDatosEjecucionActual.setCodigo("ejeactual");
        conjuntoDatosEjecucionActual.setEtiqueta(Propiedad.getValor("graevuproyejec"));
        conjuntoDatosEjecucionActual.getEstilo().setColorSerie(Propiedad.getValor("graevuproyecolor2"));

        ConjuntoDatosGrafico conjuntoDatosPlanIni = new ConjuntoDatosGrafico();
        conjuntoDatosPlanIni.setCodigo("planini");
        conjuntoDatosPlanIni.setEtiqueta(Propiedad.getValor("graevuproyplaniini"));
        conjuntoDatosPlanIni.getEstilo().setColorSerie("#c9dfb0");
        List<Periodo> periodosActuales = new ArrayList<Periodo>();
        List<Periodohisto> periodoshisto = new ArrayList<Periodohisto>();
        BigDecimal vlrConvenio = convenio.getNumvlrcontrato();

        for (Obra obra : obraxContratos) {

            /**
             * Se obtienen los periodos de la obra y se ordenan de acuerdo a la fecha de finalización del periodo
             */
            int intcodigoobra = obra.getIntcodigoobra();
            periodosActuales.addAll(getSessionBeanCobra().getCobraService().obtenerPeriodosObra(intcodigoobra));
            /**
             * Se obtienen los periodos de la primera planificación del proyecto y si existen, se genera la línea
             * correspondiente al planificado inicial
             */
            List<Periodohisto> periodosPrimerHistoricoObra = getSessionBeanCobra().getModificarProyectoService().obtenerPeriodosPrimerHistoricoObra(intcodigoobra);
            if (periodosPrimerHistoricoObra != null) {

                periodoshisto.addAll(periodosPrimerHistoricoObra);
            }

        }

        //Ordenar Periodos Actuales por fecha
        orderPeriodosActuales(periodosActuales);
        //Ordenar Periodos Histoiricos por fecha
        orderPeriodoshisto(periodoshisto);

        if (periodoshisto != null && periodoshisto.size() > 0) {

            boolean esPrimerPeriodo = true;
            for (Periodohisto periodoObra : periodoshisto) {
                if (esPrimerPeriodo) {
                    DatoGrafico datoPlaniIni = new DatoGrafico();
                    datoPlaniIni.setValorX("" + periodoObra.getDatefeciniperiodo().getTime());
                    datoPlaniIni.setValorY("0");
                    StringBuilder stringEtiDatoPlaniActual = new StringBuilder();
                    stringEtiDatoPlaniActual.append(Propiedad.getValor("graevuproyeti1dato"))
                            .append(" ").append(periodoObra.getDatefeciniperiodo())
                            .append(" ").append(Propiedad.getValor("graevuproyetidatoplanini"))
                            .append("0");
                    datoPlaniIni.setEtiqueta(stringEtiDatoPlaniActual.toString());
                    conjuntoDatosPlanIni.getListaDatos().add(datoPlaniIni);
                    esPrimerPeriodo = false;
                }
                DatoGrafico datoPlaniIni = new DatoGrafico();
                datoPlaniIni.setValorX("" + periodoObra.getDatefecfinperiodo().getTime());
                acumuladoVlrPlanificadoIni = acumuladoVlrPlanificadoIni.add(periodoObra.getNumvaltotplanif().divide(divisor));
                datoPlaniIni.setValorY(acumuladoVlrPlanificadoIni.setScale(3, RoundingMode.HALF_UP).toPlainString());
                StringBuilder stringEtiDatoPlaniActual = new StringBuilder();
                stringEtiDatoPlaniActual.append(Propiedad.getValor("graevuproyeti1dato")).append(" ").append(periodoObra.getDatefeciniperiodo()).append(" ").append(Propiedad.getValor("graevuproyeti2dato")).append(" ").append(periodoObra.getDatefecfinperiodo()).append(" ").append(Propiedad.getValor("graevuproyetidatoplanini")).append(periodoObra.getNumvaltotplanif().divide(divisor).setScale(3, RoundingMode.HALF_UP).toPlainString());
                datoPlaniIni.setEtiqueta(stringEtiDatoPlaniActual.toString());
                conjuntoDatosPlanIni.getListaDatos().add(datoPlaniIni);
            }

        }

        boolean esPrimerPeriodo = true;
        for (Periodo periodoObra : periodosActuales) {
            if (esPrimerPeriodo) {
                DatoGrafico datoPlaniActual = new DatoGrafico();
                datoPlaniActual.setValorX("" + periodoObra.getDatefeciniperiodo().getTime());
                datoPlaniActual.setValorY("0");
                StringBuilder stringEtiDatoPlaniActual = new StringBuilder();
                stringEtiDatoPlaniActual.append(Propiedad.getValor("graevuproyeti1dato"))
                        .append(" ").append(periodoObra.getDatefeciniperiodo())
                        .append(" ").append(Propiedad.getValor("graevuproyetidatoplan"))
                        .append("0");
                datoPlaniActual.setEtiqueta(stringEtiDatoPlaniActual.toString());
                conjuntoDatosPlanActual.getListaDatos().add(datoPlaniActual);
                esPrimerPeriodo = false;
            }
            DatoGrafico datoPlaniActual = new DatoGrafico();
            datoPlaniActual.setValorX("" + periodoObra.getDatefecfinperiodo().getTime());

            //porcentaje que el valor planeado representa para el cronograma    
            BigDecimal valPlan = periodoObra.getNumvaltotplanif().multiply(new BigDecimal(100)).divide(periodoObra.getObra().getNumvaltotobra(), 2, RoundingMode.HALF_UP);
            //porcentaje que el cronograma representa para el convenio
            BigDecimal rateCronoToConv = periodoObra.getObra().getNumvaltotobra().multiply(new BigDecimal(100)).divide(vlrConvenio, 2, RoundingMode.HALF_UP);
            //porcentaje que el cronograma aporta al convenio
            BigDecimal ratePlan = rateCronoToConv.multiply(valPlan).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);

            acumuladoVlrPlanificado = acumuladoVlrPlanificado.add(ratePlan);
            datoPlaniActual.setValorY(acumuladoVlrPlanificado.setScale(3, RoundingMode.HALF_UP).toPlainString());
            StringBuilder stringEtiDatoPlaniActual = new StringBuilder();
            stringEtiDatoPlaniActual.append(Propiedad.getValor("graevuproyeti1dato"))
                    .append(" ").append(periodoObra.getDatefeciniperiodo())
                    .append(" ").append(Propiedad.getValor("graevuproyeti2dato"))
                    .append(" ").append(periodoObra.getDatefecfinperiodo())
                    .append(" ").append(Propiedad.getValor("graevuproyetidatoplan"))
                    .append(periodoObra.getNumvaltotplanif().divide(divisor).setScale(3, RoundingMode.HALF_UP).toPlainString());
            datoPlaniActual.setEtiqueta(stringEtiDatoPlaniActual.toString());
            conjuntoDatosPlanActual.getListaDatos().add(datoPlaniActual);

            /**
             * Se obtienen las alimentaciones realizadas para el periodo
             */
            Iterator alimentacionIterador = getSessionBeanCobra().getCobraService().obtenerAlimentacionxFechaPeriodo(periodoObra.getDatefeciniperiodo(), periodoObra.getDatefecfinperiodo(), periodoObra.getObra().getIntcodigoobra()).iterator();
            vlrEjecutado = BigDecimal.ZERO;
            boolean existeAlimentacion = false;
            while (alimentacionIterador.hasNext()) {
                existeAlimentacion = true;
                Alimentacion alimenta = (Alimentacion) alimentacionIterador.next();
                vlrEjecutado = vlrEjecutado.add(alimenta.getNumtotalejec());

                if (!alimenta.getDatefecha().equals(periodoObra.getDatefecfinperiodo())) {
                    /**
                     * Si el sistema está configurado para alimentar por fecha
                     */
                    if (Boolean.valueOf(Propiedad.getValor("varalimentacionxfecha"))) {

                        //porcentaje que el valor ejecutado representa para el cronograma    
                        BigDecimal valEje = vlrEjecutado.multiply(new BigDecimal(100)).divide(periodoObra.getObra().getNumvaltotobra(), 2, RoundingMode.HALF_UP);
                        BigDecimal rateEje = rateCronoToConv.multiply(valEje).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
                        /**
                         * Se construye el dato correspondiente a la línea del valor ejecutado actual del proyecto
                         */
                        DatoGrafico datoEjeActual = new DatoGrafico();
                        datoEjeActual.setValorX("" + alimenta.getDatefecha().getTime());
                        acumuladoVlrEjecutado = acumuladoVlrEjecutado.add(rateEje.divide(divisor));
                        datoEjeActual.setValorY(acumuladoVlrEjecutado.setScale(3, RoundingMode.HALF_UP).toPlainString());
                        StringBuilder stringEtiDatoEjeActual = new StringBuilder();
                        stringEtiDatoEjeActual.append(Propiedad.getValor("graevuproyeti1dato"))
                                .append(" ").append(periodoObra.getDatefeciniperiodo())
                                .append(" ").append(Propiedad.getValor("graevuproyeti2dato"))
                                .append(" ").append(periodoObra.getDatefecfinperiodo())
                                .append(" ").append(Propiedad.getValor("graevuproyetidatoeje"))
                                .append(vlrEjecutado.divide(divisor).setScale(3, RoundingMode.HALF_UP).toPlainString());
                        datoEjeActual.setEtiqueta(stringEtiDatoEjeActual.toString());
                        conjuntoDatosEjecucionActual.getListaDatos().add(datoEjeActual);
                    }
                }

            }

            if (!Boolean.valueOf(Propiedad.getValor("varalimentacionxfecha"))) {
                /**
                 * Se construye el dato correspondiente a la línea del valor ejecutado actual del proyecto
                 */
                if (existeAlimentacion) {
                    DatoGrafico datoEjeActual = new DatoGrafico();
                    datoEjeActual.setValorX("" + periodoObra.getDatefecfinperiodo().getTime());

                    //porcentaje que el valor ejecutado representa para el cronograma    
                    BigDecimal valEje = vlrEjecutado.multiply(new BigDecimal(100)).divide(periodoObra.getObra().getNumvaltotobra(), 2, RoundingMode.HALF_UP);
                    BigDecimal rateEje = rateCronoToConv.multiply(valEje).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);

                    acumuladoVlrEjecutado = acumuladoVlrEjecutado.add(rateEje.divide(divisor));
                    datoEjeActual.setValorY(acumuladoVlrEjecutado.setScale(3, RoundingMode.HALF_UP).toPlainString());
                    StringBuilder stringEtiDatoEjeActual = new StringBuilder();
                    stringEtiDatoEjeActual.append(Propiedad.getValor("graevuproyeti1dato")).append(" ").append(periodoObra.getDatefeciniperiodo()).append(" ").append(Propiedad.getValor("graevuproyeti2dato")).append(" ").append(periodoObra.getDatefecfinperiodo()).append(" ").append(Propiedad.getValor("graevuproyetidatoeje")).append(vlrEjecutado.divide(divisor).setScale(3, RoundingMode.HALF_UP).toPlainString());
                    datoEjeActual.setEtiqueta(stringEtiDatoEjeActual.toString());
                    conjuntoDatosEjecucionActual.getListaDatos().add(datoEjeActual);
                }
            }

        }

//        grafico.getConjuntosDatosGrafico().add(conjuntoDatosPlanIni);
        grafico.getConjuntosDatosGrafico().add(conjuntoDatosPlanActual);
        grafico.getConjuntosDatosGrafico().add(conjuntoDatosEjecucionActual);

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

    }

    private void graficar() {
        preGraficar();
        grafico.generarGrafico();
    }

    private void orderPeriodoshisto(List<Periodohisto> periodoshisto) {

        Collections.sort(periodoshisto, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Periodohisto obj1 = (Periodohisto) o1;
                Periodohisto obj2 = (Periodohisto) o2;

                if (obj1.getDatefecfinperiodo().compareTo(obj2.getDatefecfinperiodo()) > 0) {
                    return 1;
                } else if (obj1.getDatefecfinperiodo().compareTo(obj2.getDatefecfinperiodo()) < 0) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

    }

    private void orderPeriodosActuales(List<Periodo> periodosActuales) {

        Collections.sort(periodosActuales, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Periodo obj1 = (Periodo) o1;
                Periodo obj2 = (Periodo) o2;

                if (obj1.getDatefecfinperiodo().compareTo(obj2.getDatefecfinperiodo()) > 0) {
                    return 1;
                } else if (obj1.getDatefecfinperiodo().compareTo(obj2.getDatefecfinperiodo()) < 0) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

    }

}
