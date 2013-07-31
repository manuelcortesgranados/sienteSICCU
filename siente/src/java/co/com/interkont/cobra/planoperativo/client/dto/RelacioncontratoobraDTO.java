/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client.dto;

import co.com.interkont.cobra.to.Contrato;
import co.com.interkont.cobra.to.Obra;
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author Daniela
 */
public class RelacioncontratoobraDTO implements IsSerializable
{

    private int intidserial;
    private ContratoDTO contrato;
    private ObraDTO obra;

    public RelacioncontratoobraDTO() {
    }

    public RelacioncontratoobraDTO( ContratoDTO contrato, ObraDTO obra) {
        this.contrato = contrato;
        this.obra = obra;
    }

    /**
     * @return the intidserial
     */
    public int getIntidserial() {
        return intidserial;
    }

    /**
     * @param intidserial the intidserial to set
     */
    public void setIntidserial(int intidserial) {
        this.intidserial = intidserial;
    }

    /**
     * @return the contrato
     */
    public ContratoDTO getContrato() {
        return contrato;
    }

    /**
     * @param contrato the contrato to set
     */
    public void setContrato(ContratoDTO contrato) {
        this.contrato = contrato;
    }

    /**
     * @return the obra
     */
    public ObraDTO getObra() {
        return obra;
    }

    /**
     * @param obra the obra to set
     */
    public void setObra(ObraDTO obra) {
        this.obra = obra;
    }
    
    
    
}
