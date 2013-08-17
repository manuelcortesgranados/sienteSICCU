/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.math.BigDecimal;

/**
 *
 * @author desarrollo9
 */
public class MontoDTO implements IsSerializable{
    
     private int idmonto;
     private ContratoDTO contratodto;
     private RubroDTO rubrodto;
     private BigDecimal valor;
     private int vigencia;

    public MontoDTO() {
    }

    public MontoDTO(int idmonto, ContratoDTO contratodto, RubroDTO rubro, BigDecimal valor, int vigencia) {
        this.idmonto = idmonto;
        this.contratodto = contratodto;
        this.rubrodto = rubro;
        this.valor = valor;
        this.vigencia = vigencia;
    }
    
     public MontoDTO( RubroDTO rubro, BigDecimal valor, int vigencia) {
        this.rubrodto = rubro;
        this.valor = valor;
        this.vigencia = vigencia;
    }
     
   
    /**
     * @return the idmonto
     */
    public int getIdmonto() {
        return idmonto;
    }

    /**
     * @param idmonto the idmonto to set
     */
    public void setIdmonto(int idmonto) {
        this.idmonto = idmonto;
    }

    /**
     * @return the contratodto
     */
    public ContratoDTO getContratodto() {
        return contratodto;
    }

    /**
     * @param contratodto the contratodto to set
     */
    public void setContratodto(ContratoDTO contratodto) {
        this.contratodto = contratodto;
    }

    public RubroDTO getRubrodto() {
        return rubrodto;
    }

    public void setRubrodto(RubroDTO rubrodto) {
        this.rubrodto = rubrodto;
    }



    /**
     * @return the valor
     */
    public BigDecimal getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    /**
     * @return the vigencia
     */
    public int getVigencia() {
        return vigencia;
    }

    /**
     * @param vigencia the vigencia to set
     */
    public void setVigencia(int vigencia) {
        this.vigencia = vigencia;
    }
       
       
    
}
