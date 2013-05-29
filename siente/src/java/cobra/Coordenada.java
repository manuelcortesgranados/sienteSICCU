/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra;

/**
 *
 * @author carlos
 */
public class Coordenada {
    private int grados = 0;
    private int minutos = 0;
    private float segundos = 0;
    private double coorddecima;

    public Coordenada(int grados, int minutos, float segundos) {
        this.grados=grados;
        this.minutos=minutos;
        this.segundos=segundos;
        convertirCoordenadaDecimal();
    }

    public Coordenada(double coorddecima) {
        this.coorddecima = coorddecima;
        descomponerCoordenada();
    }


    public double getCoorddecima() {
        convertirCoordenadaDecimal();
        return coorddecima;

    }

    public void setCoorddecima(double coorddecima) {
        this.coorddecima = coorddecima;
    }

    public int getGrados() {
        return grados;
    }

    public void setGrados(int grados) {
        this.grados = grados;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public float getSegundos() {
        return segundos;
    }

    public void setSegundos(float segundos) {
        this.segundos = segundos;
    }

    public void convertirCoordenadaDecimal()
    {

        
        double min=((double)minutos)/60;
        double seg=segundos/3600;        
        coorddecima=grados+min+segundos/3600;


    }

    public void descomponerCoordenada()
    {
                
        grados=(int) coorddecima;
        double min= (coorddecima - grados)*60;        
        minutos=(int) ((coorddecima - grados) * 60);
        double seg= (min -minutos)*60;        
        segundos=(float) seg;

        convertirCoordenadaDecimal();

    }


}
