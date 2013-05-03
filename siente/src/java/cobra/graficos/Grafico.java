/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cobra.graficos;

/**
 * Clase genérica para la construcción de gráficos
 * @author Jhon Eduard Ortiz S
 */
public abstract class Grafico {
    
    public static int CADENA = 1;
    public static int NUMERO = 2;
    public static int FECHA = 3;


    /**
     * Indica el tipo de dato del eje X de la gráfica
     */
    int tipoDatoEjeX;

    /**
     * Variable en la que se almacena el código del gráfico para insertar en
     * la ventana en donde se desea mostrar el gráfico
     */
    protected String scriptGrafico;

    /**
     * Título que se presentará en el gráfico
     */
    protected String titulo;
    /**
     * Título que se presentará en la línea de valores del eje X
     */
    protected String tituloEjeX;
    /**
     * Título que se presentará en la línea de valores del eje y
     */
    protected String tituloEjeY;
    
    /**
     * Valor máximo que puede asignarse al eje Y
     */
    protected String valorYMaximo;

    /**
     * EstiloGrafico general que será aplicado al gráfico
     */
    protected EstiloGrafico estilo = new EstiloGrafico();

    public int getTipoDatoEjeX() {
        return tipoDatoEjeX;
    }

    public void setTipoDatoEjeX(int tipoDatoEjeX) {
        this.tipoDatoEjeX = tipoDatoEjeX;
    }

    public EstiloGrafico getEstilo() {
        return estilo;
    }

    public void setEstilo(EstiloGrafico estilo) {
        this.estilo = estilo;
    }

    public String getScriptGrafico() {
        return scriptGrafico;
    }

    public void setScriptGrafico(String scriptGrafico) {
        this.scriptGrafico = scriptGrafico;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTituloEjeX() {
        return tituloEjeX;
    }

    public void setTituloEjeX(String tituloEjeX) {
        this.tituloEjeX = tituloEjeX;
    }

    public String getTituloEjeY() {
        return tituloEjeY;
    }

    public void setTituloEjeY(String tituloEjeY) {
        this.tituloEjeY = tituloEjeY;
    }

    public String getValorYMaximo() {
        return valorYMaximo;
    }

    public void setValorYMaximo(String valorYMaximo) {
        this.valorYMaximo = valorYMaximo;
    }
    
    /**
     * Genera la interfaz correspondiente al gráfico
     */
    public abstract void generarGrafico();
}
