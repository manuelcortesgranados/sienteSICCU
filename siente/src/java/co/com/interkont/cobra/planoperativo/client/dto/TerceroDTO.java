/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author desarrollo9
 */
public class TerceroDTO implements IsSerializable{
    
    private int intcodigo;
    private String strnombrecompleto;  
    private int campoTemporalFuenteRecursos;

    public TerceroDTO() {
    }

    public TerceroDTO(int intcodigo, String strnombrecompleto) {
        this.intcodigo = intcodigo;
        this.strnombrecompleto = strnombrecompleto;
    }

    /**
     * @return the intcodigo
     */
    public int getIntcodigo() {
        return intcodigo;
    }

    /**
     * @param intcodigo the intcodigo to set
     */
    public void setIntcodigo(int intcodigo) {
        this.intcodigo = intcodigo;
    }

    /**
     * @return the strnombrecompleto
     */
    public String getStrnombrecompleto() {
        return strnombrecompleto;
    }

    /**
     * @param strnombrecompleto the strnombrecompleto to set
     */
    public void setStrnombrecompleto(String strnombrecompleto) {
        this.strnombrecompleto = strnombrecompleto;
    }

    /**
     * @return the campoTemporalFuenteRecursos
     */
    public int getCampoTemporalFuenteRecursos() {
        return campoTemporalFuenteRecursos;
    }

    /**
     * @param campoTemporalFuenteRecursos the campoTemporalFuenteRecursos to set
     */
    public void setCampoTemporalFuenteRecursos(int campoTemporalFuenteRecursos) {
        this.campoTemporalFuenteRecursos = campoTemporalFuenteRecursos;
    }
    
    
    
    
}
