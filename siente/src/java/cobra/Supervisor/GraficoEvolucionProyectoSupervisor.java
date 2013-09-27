package cobra.Supervisor;

import co.com.interkont.cobra.to.utilidades.Propiedad;

/**
 * MBean encargado de gestionar la presentación del gráfico de evolución del
 * proyecto
 *
 * @author Jhon Eduard Ortiz S
 */
public class GraficoEvolucionProyectoSupervisor extends GraficoEvolucionProyecto {

    public GraficoEvolucionProyectoSupervisor() {
        graficar();
    }

    /**
     * Genera el grácico de evolución del proyecto estableciendo el código de la
     * gráfica en atributo codigoGrafico del objeto graficoEvolucionproyecto
     */
    private void graficar() {
        grafico.setTitulo(Propiedad.getValor("graevuproytitulo"));
        grafico.getEstilo().setVerScroll(true);
        preGraficar();
        grafico.generarGrafico();
    }
}
