/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client.dto;

import co.com.interkont.cobra.to.Tercero;
import java.io.Serializable;

/**
 *
 * @author desarrollo9
 */
public class TerceroDTO implements Serializable{
    
    private int intcodigo;
    private String strnombrecompleto;

    TerceroDTO(Tercero tercero) {
       this.intcodigo=tercero.getIntcodigo();
       this.strnombrecompleto=tercero.getStrnombrecompleto();
    }

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
    
    
    
    
}
