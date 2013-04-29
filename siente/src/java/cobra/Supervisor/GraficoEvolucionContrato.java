package cobra.Supervisor;

import co.com.interkont.cobra.to.Movimiento;
import co.com.interkont.cobra.to.Planificacionpago;
import co.com.interkont.cobra.to.utilidades.Propiedad;
import cobra.SessionBeanCobra;
import cobra.graficos.ConjuntoDatosGrafico;
import cobra.graficos.DatoGrafico;
import cobra.graficos.EstiloGrafico;
import cobra.graficos.Grafico;
import cobra.graficos.GraficoSeries;
import cobra.graficos.GraficoSeriesAmCharts;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * MBean encargado de gestionar la presentación del gráfico de evolución del
 * contrato
 * @author Jhon Eduard Ortiz S
 */
public class GraficoEvolucionContrato  {

    private GraficoSeries grafico = new GraficoSeriesAmCharts("graficoEvolucionContrato");

    public GraficoSeries getGraficoEvolucionContrato() {
        return grafico;
    }

    public void setGraficoEvolucionContrato(GraficoSeries graficoEvolucionContrato) {
        this.grafico = graficoEvolucionContrato;
    }

    public GraficoEvolucionContrato() {
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

        /**
         * Generación del conjunto de datos ejecutados
         */
        grafico.setTipoDatoEjeX(Grafico.FECHA);
        grafico.setTitulo(Propiedad.getValor("graEvuContratoTitulo"));
        grafico.setTituloEjeX(Propiedad.getValor("graEvuContratoTituloEjeX"));
        grafico.setTituloEjeY(Propiedad.getValor("graEvuContratoTituloEjeY"));
        grafico.getEstilo().setVerLeyenda(true);
        grafico.getEstilo().setVerCursor(true);
        grafico.getEstilo().setVerScroll(true);

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

        /**
         * Generación del conjunto de datos ejecutados
         */
        List<Movimiento> movimientos = getSessionBeanCobra().getCobraService().encontrarMovimientosxContrato(getNuevoContratoBasico().getContrato().getIntidcontrato());
        Collections.sort(movimientos, new Comparator() {

            public int compare(Object o1, Object o2) {
                Movimiento m1 = (Movimiento) o1;
                Movimiento m2 = (Movimiento) o2;

                if (m1.getDatefechainirecursos().compareTo(m2.getDatefechainirecursos()) > 0) {
                    return 1;
                } else if (m1.getDatefechainirecursos().compareTo(m2.getDatefechainirecursos()) < 0) {
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

        if (movimientos != null) {
            for (Iterator it = movimientos.iterator(); it.hasNext();) {
                Movimiento movimiento = (Movimiento) it.next();
                DatoGrafico datoPlani = new DatoGrafico();
                datoPlani.setValorX("" + movimiento.getDatefechainirecursos().getTime());
                StringBuffer stringEtiDatoPlaniActual = new StringBuffer();
                if (conjuntoDatosEje.getListaDatos().contains(datoPlani)) {
                    datoPlani = conjuntoDatosEje.getListaDatos().get(conjuntoDatosEje.getListaDatos().indexOf(datoPlani));
                    if (movimiento.getEstadomovimiento().getIntestadomovimiento() == 1) {
                        acumuladoVlrEjecutado = acumuladoVlrEjecutado.add(movimiento.getNumvlrejecutado().divide(divisor));
                        stringEtiDatoPlaniActual.append(Propiedad.getValor("graEvuContratoEtiDato")).append(" ").append(movimiento.getDatefechainirecursos()).append(" ").append(Propiedad.getValor("graEvuContratoEtiDatoEje")).append(datoPlani.getValorY()).append(" ").append(movimiento.getNumvlrejecutado().divide(divisor).setScale(3, RoundingMode.HALF_UP).toPlainString());
                    }
                    if (movimiento.getEstadomovimiento().getIntestadomovimiento() == 2) {
                        acumuladoVlrEjecutado = acumuladoVlrEjecutado.subtract(movimiento.getNumvlrreintegro().divide(divisor));
                        stringEtiDatoPlaniActual.append(Propiedad.getValor("graEvuContratoEtiDato")).append(" ").append(movimiento.getDatefechainirecursos()).append(" ").append(Propiedad.getValor("graEvuContratoEtiDatoEje")).append(datoPlani.getValorY()).append(" ").append(movimiento.getNumvlrreintegro().multiply(BigDecimal.valueOf(-1)).divide(divisor).setScale(3, RoundingMode.HALF_UP).toPlainString());
                    }
                    datoPlani.setValorY(acumuladoVlrEjecutado.setScale(3, RoundingMode.HALF_UP).toPlainString());
                    datoPlani.setEtiqueta(stringEtiDatoPlaniActual.toString());
                } else {
                    if (movimiento.getEstadomovimiento().getIntestadomovimiento() == 1) {
                        acumuladoVlrEjecutado = acumuladoVlrEjecutado.add(movimiento.getNumvlrejecutado().divide(divisor));
                        stringEtiDatoPlaniActual.append(Propiedad.getValor("graEvuContratoEtiDato")).append(" ").append(movimiento.getDatefechainirecursos()).append(" ").append(Propiedad.getValor("graEvuContratoEtiDatoEje")).append(movimiento.getNumvlrejecutado().divide(divisor).setScale(3, RoundingMode.HALF_UP).toPlainString());
                    }
                    if (movimiento.getEstadomovimiento().getIntestadomovimiento() == 2) {
                        acumuladoVlrEjecutado = acumuladoVlrEjecutado.subtract(movimiento.getNumvlrreintegro().divide(divisor));
                        stringEtiDatoPlaniActual.append(Propiedad.getValor("graEvuContratoEtiDato")).append(" ").append(movimiento.getDatefechainirecursos()).append(" ").append(Propiedad.getValor("graEvuContratoEtiDatoEje")).append(movimiento.getNumvlrreintegro().multiply(BigDecimal.valueOf(-1)).divide(divisor).setScale(3, RoundingMode.HALF_UP).toPlainString());
                    }
                    datoPlani.setValorY(acumuladoVlrEjecutado.setScale(3, RoundingMode.HALF_UP).toPlainString());
                    datoPlani.setEtiqueta(stringEtiDatoPlaniActual.toString());
                    conjuntoDatosEje.getListaDatos().add(datoPlani);
                }
            }
        }
        grafico.getConjuntosDatosGrafico().add(conjuntoDatosEje);

        grafico.generarGrafico();
    }
}
