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
import co.com.interkont.cobra.to.Relacioncontratoobra;
import co.com.interkont.cobra.to.utilidades.Propiedad;
import co.com.interkont.cobra.vista.VistaObraMapa;
import cobra.SessionBeanCobra;
import cobra.graficos.ConjuntoDatosGrafico;
import cobra.graficos.DatoGrafico;
import cobra.graficos.EstiloGrafico;
import cobra.graficos.GraficoSeries;
import cobra.graficos.GraficoSeriesAmCharts;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
public class GraficoAvanceFisicoConvenio implements Serializable {

    private GraficoSeries grafico = new GraficoSeriesAmCharts("grafAvaFiConvenio");
    private String semaforo = "";

    public GraficoSeries getGrafico() {
        return grafico;
    }

    public void setGrafico(GraficoSeries grafico) {
        this.grafico = grafico;
    }

    public GraficoAvanceFisicoConvenio() {
        graficar();
    }

    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }

    protected NuevoContratoBasico getContratoBasico() {
        return (NuevoContratoBasico) FacesUtils.getManagedBean("Supervisor$Contrato");
    }

    protected AdministrarObraNew getAdministrarObraNew() {
        return (AdministrarObraNew) FacesUtils.getManagedBean("Supervisor$AdministrarObraNew");
    }

    public void setSemaforo(String semaforo) {
        this.semaforo = semaforo;

    }

    public void setSemaforo(BigDecimal acumuladoVlrEjecutado) {

        if (acumuladoVlrEjecutado.compareTo(new BigDecimal(30)) == -1) {
            this.semaforo = "ROJO";
        } else if (acumuladoVlrEjecutado.compareTo(new BigDecimal(60)) == -1) {
            this.semaforo = "AMARILLO";
        } else if (acumuladoVlrEjecutado.compareTo(new BigDecimal(60)) == 1) {
            this.semaforo = "VERDE";
        }

    }

    public String getSemaforo() {
        return semaforo;
    }

    /**
     * Genera el gráfico de avance físico
     */
    private void graficar() {

        Contrato convenio = getSessionBeanCobra().getCobraService().encontrarContratoPorNumero(getContratoBasico().getContrato().getStrnumcontrato());
        List<Obra> obraxContratos = getSessionBeanCobra().getCobraService().encontrarObraxContratos(convenio.getIntidcontrato(), " ", 0, 20, convenio.getBooltipocontratoconvenio(), getSessionBeanCobra().getUsuarioObra(), true);

        BigDecimal divisor = new BigDecimal(1);
        BigDecimal vlrEjecutado;
        BigDecimal vlrConvenio = convenio.getNumvlrcontrato();
        BigDecimal acumuladoVlrEjecutado = new BigDecimal(0);
        BigDecimal acumuladoVlrPlanificado = new BigDecimal(0);

        List<Periodo> periodosActuales = new ArrayList<Periodo>();
        List<Periodohisto> periodoshisto = new ArrayList<Periodohisto>();

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

        for (Periodo periodoObra : periodosActuales) {
            DatoGrafico datoPlaniActual = new DatoGrafico();
            datoPlaniActual.setValorX("" + periodoObra.getDatefecfinperiodo().getTime());
            //porcentaje que el valor planeado representa para el cronograma    
            BigDecimal valPlan = periodoObra.getNumvaltotplanif().multiply(new BigDecimal(100)).divide(periodoObra.getObra().getNumvaltotobra(), 2, RoundingMode.HALF_UP);
            //porcentaje que el cronograma representa para el convenio
            BigDecimal rateCronoToConv = periodoObra.getObra().getNumvaltotobra().multiply(new BigDecimal(100)).divide(vlrConvenio, 2, RoundingMode.HALF_UP);
            //porcentaje que el cronograma aporta al convenio
            BigDecimal ratePlan = rateCronoToConv.multiply(valPlan).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
            acumuladoVlrPlanificado = acumuladoVlrPlanificado.add(ratePlan);
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
                        acumuladoVlrEjecutado = acumuladoVlrEjecutado.add(rateEje.divide(divisor));
                    }
                }
            }

            if (!Boolean.valueOf(Propiedad.getValor("varalimentacionxfecha"))) {
                /**
                 * Se construye el dato correspondiente a la línea del valor ejecutado actual del proyecto
                 */
                if (existeAlimentacion) {
                    //porcentaje que el valor ejecutado representa para el cronograma    
                    BigDecimal valEje = vlrEjecutado.multiply(new BigDecimal(100)).divide(periodoObra.getObra().getNumvaltotobra(), 2, RoundingMode.HALF_UP);
                    BigDecimal rateEje = rateCronoToConv.multiply(valEje).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);

                    acumuladoVlrEjecutado = acumuladoVlrEjecutado.add(rateEje.divide(divisor));

                }
            }

        }

        grafico.setTipoGrafico(GraficoSeries.TIPO_COLUMNAS);
        grafico.setValorYMaximo("100");
        grafico.getEstilo().setPorcentaje(true);
        grafico.getEstilo().setAnimado(true);
        grafico.getEstilo().setTresD(false);
        grafico.getEstilo().setOcultarEjeY(true);
        grafico.getEstilo().setAvancefisicosiente(Boolean.valueOf(Propiedad.getValor("graavafissiente")));
        grafico.getEstilo().setAvancefisicozoom(Boolean.valueOf(Propiedad.getValor("graavafiszoom")));
        grafico.getEstilo().setColorTexto(Propiedad.getValor("graavafiscolortexto"));
        grafico.getEstilo().setTipoTexto(Propiedad.getValor("graavafisfamilytexto"));
        grafico.getEstilo().setTamanoTexto(Propiedad.getValor("graavafissizetexto"));
        grafico.getEstilo().setTipoPila(Propiedad.getValor("graavafisstacktype"));
        grafico.getEstilo().setRotate(Boolean.valueOf(Propiedad.getValor("graavafisrotate")));
        grafico.getEstilo().setGridalpha(Propiedad.getValor("graavafisfondogrid"));
        grafico.getEstilo().setColorlineasplano(Propiedad.getValor("graavafisbgfondo"));
        grafico.getEstilo().setDegradadohorizontal(Boolean.valueOf(Propiedad.getValor("graavafisdegradadohorizontal")));

        ConjuntoDatosGrafico conjuntoDatosAvanceFisico = new ConjuntoDatosGrafico();
        conjuntoDatosAvanceFisico.setCodigo("avancefisico");
        EstiloGrafico estiloAvance = new EstiloGrafico();
        estiloAvance.setColorSerie(Propiedad.getValor("graevuproyecolor1"));
        estiloAvance.setColorSerie2(Propiedad.getValor("graevuproyecolor2"));
        estiloAvance.setColorTexto(Propiedad.getValor("graavafiscolortextocolumna"));
        estiloAvance.setPorcentaje(true);
        conjuntoDatosAvanceFisico.setEstilo(estiloAvance);

        DatoGrafico datoEje = new DatoGrafico();
        datoEje.setValorX(Propiedad.getValor("graavafiseje"));
        datoEje.setValorY(acumuladoVlrEjecutado.toString());
        datoEje.setEtiqueta(Propiedad.getValor("graavafiseje"));
        conjuntoDatosAvanceFisico.getListaDatos().add(datoEje);

        DatoGrafico datoPlani = new DatoGrafico();
        datoPlani.setValorX(Propiedad.getValor("graavafisplani"));
        datoPlani.setValorY(acumuladoVlrPlanificado.toString());
        datoPlani.setEtiqueta(Propiedad.getValor("graavafisplani"));
        conjuntoDatosAvanceFisico.getListaDatos().add(datoPlani);
        
        setSemaforo(acumuladoVlrEjecutado);

        grafico.getConjuntosDatosGrafico().add(conjuntoDatosAvanceFisico);
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
