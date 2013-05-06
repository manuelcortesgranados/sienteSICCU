package cobra.Supervisor;

/**
 * MBean encargado de gestionar la presentación del gráfico de evolución del
 * proyecto
 *
 * @author Jhon Eduard Ortiz S
 */
public class GraficoEvolucionProyectoCiudadano extends GraficoEvolucionProyecto {

    public GraficoEvolucionProyectoCiudadano() {
        graficar();
    }

    /**
     * Genera el grácico de evolución del proyecto estableciendo el código de la
     * gráfica en atributo codigoGrafico del objeto graficoEvolucionproyecto
     */
    private void graficar() {
        preGraficar();
        grafico.generarGrafico();
    }
}
