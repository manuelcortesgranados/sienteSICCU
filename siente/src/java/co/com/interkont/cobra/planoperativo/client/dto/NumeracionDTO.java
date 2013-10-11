/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client.dto;

/**
 *
 * @author Daniela
 */
public class NumeracionDTO {
    private int numeracion;
    
    
    

    public NumeracionDTO(int numeracion) {
        this.numeracion=numeracion;
    }

    /**
     * @return the numeracion
     */
    public int getNumeracion() {
        return numeracion;
    }

    /**
     * @param numeracion the numeracion to set
     */
    public void setNumeracion(int numeracion) {
        this.numeracion = numeracion;
    }
}
