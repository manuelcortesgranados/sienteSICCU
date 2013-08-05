/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author Daniela
 */
public class TipocontratoDTO implements IsSerializable{
     private int inttipocontrato;
     private String strdesctipocontrato;
     
     

    public TipocontratoDTO() {
    }
    
    

    public TipocontratoDTO(int inttipocontrato, String strdesctipocontrato) {
        this.inttipocontrato = inttipocontrato;
        this.strdesctipocontrato = strdesctipocontrato;
    }

    /**
     * @return the inttipocontrato
     */
    public int getInttipocontrato() {
        return inttipocontrato;
    }

    /**
     * @param inttipocontrato the inttipocontrato to set
     */
    public void setInttipocontrato(int inttipocontrato) {
        this.inttipocontrato = inttipocontrato;
    }

    /**
     * @return the strdesctipocontrato
     */
    public String getStrdesctipocontrato() {
        return strdesctipocontrato;
    }

    /**
     * @param strdesctipocontrato the strdesctipocontrato to set
     */
    public void setStrdesctipocontrato(String strdesctipocontrato) {
        this.strdesctipocontrato = strdesctipocontrato;
    }
    
}
