package cobra.Supervisor;

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
import java.math.RoundingMode;
import java.util.Date;

/**
 * MBean encargado de gestionar la presentación del gráfico de avance físico
 * @author Jhon Eduard Ortiz S
 */
public class GraficoAvanceFisico implements Serializable{

    private GraficoSeries grafico = new GraficoSeriesAmCharts("graficoAvanceFisico");

    public GraficoSeries getGrafico() {
        return grafico;
    }

    public void setGrafico(GraficoSeries grafico) {
        this.grafico = grafico;
    }

    public GraficoAvanceFisico() {
        graficar();
    }

    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }

    protected AdministrarObraNew getAdministrarObraNew() {
        return (AdministrarObraNew) FacesUtils.getManagedBean("Supervisor$AdministrarObraNew");
    }

    /**
     * Genera el gráfico de avance físico
     */
    private void graficar() {

        VistaObraMapa vistaObraMapa = getSessionBeanCobra().getCobraService().obtenerVistaObraMapaxid(getAdministrarObraNew().getObra().getIntcodigoobra());
        
        BigDecimal porcentajePlanificado = vistaObraMapa.getDeberiaestar().setScale(2, RoundingMode.HALF_UP);
        BigDecimal porcentajeEjecutado = vistaObraMapa.getNumvalejecobra().divide(vistaObraMapa.getNumvaltotobra() ,2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
        
        if(new Date().after(getAdministrarObraNew().getObra().getDatefecfinobra())) {
            porcentajePlanificado = BigDecimal.valueOf(100).setScale(2,RoundingMode.HALF_UP);
        }

        grafico.setTipoGrafico(GraficoSeries.TIPO_COLUMNAS);
        grafico.setValorYMaximo("100");
        grafico.getEstilo().setPorcentaje(true);
        grafico.getEstilo().setAnimado(true);
        grafico.getEstilo().setTresD(false);
        grafico.getEstilo().setOcultarEjeY(true);
        grafico.getEstilo().setAvancefisico(true);
        grafico.getEstilo().setColorTexto("#000");
        grafico.getEstilo().setTipoTexto("open");        
        grafico.getEstilo().setTamanoTexto("0.8em");
        grafico.getEstilo().setTipoPila("none");
        grafico.getEstilo().setRotate(true);
        grafico.getEstilo().setGridalpha("0");
        grafico.getEstilo().setColorlineasplano("transparent");
        grafico.getEstilo().setDegradadohorizontal(true);
        

        ConjuntoDatosGrafico conjuntoDatosAvanceFisico = new ConjuntoDatosGrafico();
        conjuntoDatosAvanceFisico.setCodigo("avancefisico");
        EstiloGrafico estiloAvance = new EstiloGrafico();
        estiloAvance.setColorSerie("#257500");
        estiloAvance.setColorSerie2("#9CFF00");
        estiloAvance.setColorTexto("#000");
        estiloAvance.setPorcentaje(true);
        conjuntoDatosAvanceFisico.setEstilo(estiloAvance);

        DatoGrafico datoEje = new DatoGrafico();
        datoEje.setValorX(Propiedad.getValor("graavafiseje"));
        datoEje.setValorY(porcentajeEjecutado.toPlainString());
        datoEje.setEtiqueta(Propiedad.getValor("graavafiseje"));
        conjuntoDatosAvanceFisico.getListaDatos().add(datoEje);

        DatoGrafico datoPlani = new DatoGrafico();
        datoPlani.setValorX(Propiedad.getValor("graavafisplani"));
        datoPlani.setValorY(porcentajePlanificado.toPlainString());
        datoPlani.setEtiqueta(Propiedad.getValor("graavafisplani"));
        conjuntoDatosAvanceFisico.getListaDatos().add(datoPlani);

        grafico.getConjuntosDatosGrafico().add(conjuntoDatosAvanceFisico);
        grafico.generarGrafico();
    }
}
