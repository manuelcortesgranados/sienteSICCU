/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.Supervisor;

import co.com.interkont.cobra.to.Desembolso;
import co.com.interkont.cobra.to.Movimiento;
import co.com.interkont.cobra.to.Planificacionpago;
import cobra.SessionBeanCobra;
import cobra.graficos.Data;
import cobra.graficos.Datasets;
import cobra.graficos.Estructuragraphic;
import cobra.graficos.JSChart;
import cobra.graficos.Optionset;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.google.gson.Gson;
import java.io.Serializable;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 *
 * @author felipe
 */
public class GraficosEvolucion implements Serializable{

    public String rutaGrafico = "";
    public List<Movimiento> movimientos;
    private List<Desembolso> desembolsos;
 
    public GraficosEvolucion() {
        desembolsos = getSessionBeanCobra().getCobraService().encontrarDesembolsoxContrato(getNuevoContratoBasico().getContrato());
    }

    private void GenerarJson() {

        List<Datasets> linea = new ArrayList();

        List<Data> dato1 = new ArrayList();
        List<Data> dato2 = new ArrayList();


        List<Optionset> optionset = new ArrayList();
        optionset.add(new Optionset("setSize", "850, 400"));
        optionset.add(new Optionset("setTitle", "'Grafico Evolucion Financiero'"));
        optionset.add(new Optionset("setAxisNameFontSize", "10"));
        optionset.add(new Optionset("setAxisNameX", "'Periodo Planificacion'"));
        optionset.add(new Optionset("setAxisNameY", "'MILLONES $'"));
        optionset.add(new Optionset("setShowXValues", "false"));
        optionset.add(new Optionset("setTitleColor", "'#666666'"));
        optionset.add(new Optionset("setAxisValuesColor", "'#666666'"));
        optionset.add(new Optionset("setLineColor", "'#FF0000', 'green'"));
        optionset.add(new Optionset("setLineColor", "'#336699', 'gray'"));
        optionset.add(new Optionset("setFlagColor", "'#666666'"));
        optionset.add(new Optionset("setFlagRadius", "14"));
        optionset.add(new Optionset("setAxisPaddingLeft", "80"));
        optionset.add(new Optionset("setLegendShow", "true"));
        optionset.add(new Optionset("setLegendPosition", "200, 80"));
        optionset.add(new Optionset("setLabelFontSize", "7"));
        optionset.add(new Optionset("setLegendForLine", "'blue', 'Planificado'"));
//        optionset.add(new Optionset("setBackgroundColor","'#BCD0FF'"));
        //optionset.add(new Optionset("setLegendForLine","'green', 'Otro'"));
        optionset.add(new Optionset("setLegendForLine", "'green', 'Ejecutado'"));

        List<Planificacionpago> planificacionpago = getSessionBeanCobra().getCobraService().encontrarPlanificacionpagoxContrato(getNuevoContratoBasico().getContrato());
        movimientos = getSessionBeanCobra().getCobraService().encontrarMovimientosxContrato(getNuevoContratoBasico().getContrato().getIntidcontrato());
        Collections.sort(planificacionpago, new Comparator() {
            public int compare(Object o1, Object o2) {
                Planificacionpago e1 = (Planificacionpago) o1;
                Planificacionpago e2 = (Planificacionpago) o2;

                if (e1.getDatefechapago().compareTo(e2.getDatefechapago()) > 0) {
                    return 1;
                } else if (e1.getDatefechapago().compareTo(e2.getDatefechapago()) < 0) {
                    return -1;
                } else {
                    return 0;
                }

            }
        });

        optionset.add(new Optionset("setIntervalStartX", "0"));
        optionset.add(new Optionset("setAxisValuesNumberX", "" + planificacionpago.size()));

        String valorplani;
        String periodo;

        BigDecimal valorplanificado = new BigDecimal(0);
        BigDecimal valoracumulado = new BigDecimal(0);
        BigDecimal divisor = new BigDecimal(1000000);

        String valoreje = "";
        String periodoeje = "";
        BigDecimal totalacumovimi = new BigDecimal(0);
        BigDecimal totalvalormovimiento = new BigDecimal(0);

        BigDecimal totalplanificado = getNuevoContratoBasico().getContrato().getNumvlrcontrato().divide(divisor);
        optionset.add(new Optionset("setIntervalStartY", "0"));
        optionset.add(new Optionset("setIntervalEndY", "" + totalplanificado.setScale(4, RoundingMode.HALF_UP)));
        optionset.add(new Optionset("setAxisValuesNumberY", "" + planificacionpago.size()));

        int i = 0;

        dato1.add(new Data(String.valueOf(i), String.valueOf(valoracumulado)));
        i += 1;

        if (!movimientos.isEmpty() && movimientos.size() > 0) {

            //encontrar movimientos antes del primer periodo de planificacion
            if (!planificacionpago.isEmpty() && planificacionpago.get(0).getDatefechapago().after(movimientos.get(0).getDatefechainirecursos())) {

                int nummovimiinicio = 1;
                totalacumovimi = totalacumovimi.add(movimientos.get(0).getNumvlrejecutado());
                totalvalormovimiento = totalacumovimi.divide(divisor);
                valoreje = String.valueOf(totalvalormovimiento.setScale(3, RoundingMode.HALF_UP));

                //ingreso el promer tooltip de la linea
                if (movimientos.get(0).getEstadomovimiento().getIntestadomovimiento() == 1) {

                    if (movimientos.size() > 1) {
                        if (movimientos.get(0).getDatefechainirecursos().equals(movimientos.get(1).getDatefechainirecursos())) {
                            dato2.add(new Data("0", "0"));
                        } else {
                            optionset.add(new Optionset("setTooltip", "[" + 0 + ", xxFecha Inicial:" + movimientos.get(0).getDatefechainirecursos() + " Valor ejecutado en millones $" + valoreje + "xx , xxgreenxx ]"));
                            //ingreso el valor del punto del tooltip
                            dato2.add(new Data("0", valoreje));
                        }
                    } else {
                        optionset.add(new Optionset("setTooltip", "[" + 0 + ", xxFecha Inicial:" + movimientos.get(0).getDatefechainirecursos() + " Valor ejecutado en millones $" + valoreje + "xx , xxgreenxx ]"));
                        //ingreso el valor del punto del tooltip
                        dato2.add(new Data("0", valoreje));
                    }



                }

                int diasM = fechasDiferenciaEnDias(movimientos.get(0).getDatefechainirecursos(), planificacionpago.get(0).getDatefechapago());

                //se buscan los movimientos  entre el primer movimiento y la primer planificacion desde la posicion 1
                for (int k = 1; k < movimientos.size(); k++) {

                    float promedio;
                    if (movimientos.get(k).getDatefechainirecursos().before(planificacionpago.get(0).getDatefechapago())) {

                        nummovimiinicio = nummovimiinicio + 1;
                        int dias = fechasDiferenciaEnDias(movimientos.get(k).getDatefechainirecursos(), planificacionpago.get(0).getDatefechapago());
                        promedio = ((dias * 10) / diasM) / 10;

                        if (dias == diasM) {
                            periodoeje = "0";
                        } else {
                            periodoeje = String.valueOf(promedio);
                        }

                        if (movimientos.get(k).getEstadomovimiento().getIntestadomovimiento() == 1) {
                            totalacumovimi = totalacumovimi.add(movimientos.get(k).getNumvlrejecutado());
                            totalvalormovimiento = totalacumovimi.divide(divisor);
                            valoreje = String.valueOf(totalvalormovimiento.setScale(3, RoundingMode.HALF_UP));
                        }

                        if (movimientos.get(k).getEstadomovimiento().getIntestadomovimiento() == 2) {
                            totalacumovimi = totalacumovimi.subtract(movimientos.get(k).getNumvlrreintegro());
                            totalvalormovimiento = totalacumovimi.divide(divisor);
                            valoreje = String.valueOf(totalvalormovimiento.setScale(3, RoundingMode.HALF_UP));
                        }


                        optionset.add(new Optionset("setTooltip", "[" + periodoeje + ", xxFecha Inicial:" + movimientos.get(k).getDatefechainirecursos() + " Valor ejecutado en millones $" + valoreje + "xx , xxgreenxx ]"));
                        dato2.add(new Data(periodoeje, valoreje));


                    }

                }

            }

            //encontrar movimientos que estan dentro de los periodos de planificacion
            for (int j = 0; j < planificacionpago.size(); j++) {

                periodo = String.valueOf(i);

                optionset.add(new Optionset("setLabelX", "[" + i + ", xxp" + i + "xx ]"));

                valorplanificado = valorplanificado.add(planificacionpago.get(j).getNumvlrpago());
                valoracumulado = valorplanificado.divide(divisor);

                valorplani = String.valueOf(valoracumulado.setScale(3, RoundingMode.HALF_UP));

                dato1.add(new Data(periodo, valorplani));
                optionset.add(new Optionset("setTooltip", "[" + periodo + ", xxFecha :" + planificacionpago.get(j).getDatefechapago() + " Valor planificado en millones $" + valorplani + "xx , xxbluexx ]"));

                if ((j + 1) < planificacionpago.size()) {

                    int diasM = fechasDiferenciaEnDias(planificacionpago.get(j).getDatefechapago(), planificacionpago.get(j + 1).getDatefechapago());

                    for (int l = 0; l < movimientos.size(); l++) {

                        if ((movimientos.get(l).getDatefechainirecursos().before(planificacionpago.get(j + 1).getDatefechapago()) && movimientos.get(l).getDatefechainirecursos().after(planificacionpago.get(j).getDatefechapago())) || movimientos.get(l).getDatefechainirecursos().equals(planificacionpago.get(j).getDatefechapago()) || movimientos.get(l).getDatefechainirecursos().equals(planificacionpago.get(j + 1).getDatefechapago())) {

                            int dias = fechasDiferenciaEnDias(movimientos.get(l).getDatefechainirecursos(), planificacionpago.get(j + 1).getDatefechapago());
                            float promedio = ((dias * 10) / diasM) / 10;
                            promedio = promedio + i;
                            periodoeje = String.valueOf(promedio);

                            if (movimientos.get(l).getEstadomovimiento().getIntestadomovimiento() == 1) {
                                totalacumovimi = totalacumovimi.add(movimientos.get(l).getNumvlrejecutado());
                                totalvalormovimiento = totalacumovimi.divide(divisor);
                                valoreje = String.valueOf(totalvalormovimiento.setScale(3, RoundingMode.HALF_UP));
                            }

                            if (movimientos.get(l).getEstadomovimiento().getIntestadomovimiento() == 2) {
                                totalacumovimi = totalacumovimi.subtract(movimientos.get(l).getNumvlrreintegro());
                                totalvalormovimiento = totalacumovimi.divide(divisor);
                                valoreje = String.valueOf(totalvalormovimiento.setScale(3, RoundingMode.HALF_UP));
                            }

                            optionset.add(new Optionset("setTooltip", "[" + periodoeje + ", xxFecha Inicial:" + movimientos.get(l).getDatefechainirecursos() + " Valor ejecutado en millones $" + valoreje + "xx , xxgreenxx ]"));
                            dato2.add(new Data(periodoeje, valoreje));

                        }
                    }

                }

                i += 1;


            }

            //encontrar los movimientos despues del ultimo periodo de planificacion
            if (!planificacionpago.isEmpty() && planificacionpago.get(planificacionpago.size() - 1).getDatefechapago().before(movimientos.get(movimientos.size() - 1).getDatefechainirecursos())) {



                int diasM = fechasDiferenciaEnDias(planificacionpago.get(planificacionpago.size() - 1).getDatefechapago(), movimientos.get(movimientos.size() - 1).getDatefechainirecursos());

                for (int t = 0; t < movimientos.size(); t++) {

                    if (planificacionpago.get(planificacionpago.size() - 1).getDatefechapago().before(movimientos.get(t).getDatefechainirecursos())) {

                        int dias = fechasDiferenciaEnDias(planificacionpago.get(planificacionpago.size() - 1).getDatefechapago(), movimientos.get(t).getDatefechainirecursos());
                        float promedio = ((dias * 10) / diasM) / 10;
                        promedio = promedio + i;
                        periodoeje = String.valueOf(promedio);

                        if (movimientos.get(t).getEstadomovimiento().getIntestadomovimiento() == 1) {
                            totalacumovimi = totalacumovimi.add(movimientos.get(t).getNumvlrejecutado());
                            totalvalormovimiento = totalacumovimi.divide(divisor);
                            valoreje = String.valueOf(totalvalormovimiento.setScale(3, RoundingMode.HALF_UP));
                        }

                        if (movimientos.get(t).getEstadomovimiento().getIntestadomovimiento() == 2) {
                            totalacumovimi = totalacumovimi.subtract(movimientos.get(t).getNumvlrreintegro());
                            totalvalormovimiento = totalacumovimi.divide(divisor);
                            valoreje = String.valueOf(totalvalormovimiento.setScale(3, RoundingMode.HALF_UP));
                        }

                        optionset.add(new Optionset("setTooltip", "[" + periodoeje + ", xxFecha Inicial:" + movimientos.get(t).getDatefechainirecursos() + " Valor ejecutado en millones $" + valoreje + "xx , xxgreenxx ]"));
                        dato2.add(new Data(periodoeje, valoreje));

                    }
                }


            }

            linea.add(new Datasets("line", "green", dato2));


        } else {

            Iterator ite = planificacionpago.iterator();
            while (ite.hasNext()) {
                /*Calendar cal=Calendar.getInstance();

                cal.setTime(planitemp.getDatefechapago());
                // System.out.println("numero = " + cal.getTimeInMillis()+"fecha: " +cal.getTime());*/

                periodo = String.valueOf(i);

                Planificacionpago planitemp = (Planificacionpago) ite.next();
                optionset.add(new Optionset("setLabelX", "[" + i + ", xxp" + i + "xx ]"));

                valorplanificado = valorplanificado.add(planitemp.getNumvlrpago());
                valoracumulado = valorplanificado.divide(divisor);

                valorplani = String.valueOf(valoracumulado.setScale(3, RoundingMode.HALF_UP));

                dato1.add(new Data(periodo, valorplani));
                optionset.add(new Optionset("setTooltip", "[" + i + ", xxFecha :" + planitemp.getDatefechapago() + " Valor planificado en millones $" + valorplani + "xx , xxbluexx ]"));


                i += 1;
            }
        }



        linea.add(new Datasets("line", "blue", dato1));

        Estructuragraphic estruc = new Estructuragraphic();
        estruc.setJSChart(new JSChart(linea, optionset));

        Gson gson = new Gson();
        String jsonOutput = gson.toJson(estruc);
        String temp = jsonOutput.replace("xx", "\'");

        ServletContext theApplicationsServletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

        String realArchivoPath = theApplicationsServletContext.getRealPath("/resources/json/datafinanciera.json");
        //System.out.println("realArchivoPath = " + realArchivoPath);

        FileManager filejson = new FileManager();
        filejson.writeInFile(temp, realArchivoPath);
        this.rutaGrafico = "/"+getSessionBeanCobra().getBundle().getString("versioncobra")+ "/resources/json/datafinanciera.json";


    }

    public String getRutaGrafico() {
        return rutaGrafico;
    }

    public void setRutaGrafico(String rutaGrafico) {
        this.rutaGrafico = rutaGrafico;
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }

    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }

    protected NuevoContratoBasico getNuevoContratoBasico() {
        return (NuevoContratoBasico) FacesUtils.getManagedBean("Supervisor$Contrato");
    }

    public static int fechasDiferenciaEnDias(Date fechaInicial, Date fechaFinal) {

        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
        String fechaInicioString = df.format(fechaInicial);
        try {
            fechaInicial = df.parse(fechaInicioString);
        } catch (ParseException ex) {
        }

        String fechaFinalString = df.format(fechaFinal);
        try {
            fechaFinal = df.parse(fechaFinalString);
        } catch (ParseException ex) {
        }

        long fechaInicialMs = fechaInicial.getTime();
        long fechaFinalMs = fechaFinal.getTime();
        long diferencia = fechaFinalMs - fechaInicialMs;
        double dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));
        return ((int) dias);
    }

    /**
     * @return the desembolsos
     */
    public List<Desembolso> getDesembolsos() {
        return desembolsos;
    }

    /**
     * @param desembolsos the desembolsos to set
     */
    public void setDesembolsos(List<Desembolso> desembolsos) {
        this.desembolsos = desembolsos;
    }
}
