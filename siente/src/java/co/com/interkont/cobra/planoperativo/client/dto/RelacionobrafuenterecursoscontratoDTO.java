package co.com.interkont.cobra.planoperativo.client.dto;
// Generated Jul 12, 2013 4:10:17 PM by Hibernate Tools 3.2.1.GA

import co.com.interkont.cobra.to.*;
import java.math.BigDecimal;




/**
 * Relacionobrafuenterecursoscontrato generated by hbm2java
 */
public class RelacionobrafuenterecursoscontratoDTO  implements java.io.Serializable {


     private int idrelacionobracontrato;
     private ContratoDTO contrato;
     private ObrafuenterecursosconveniosDTO obrafuenterecursosconvenios;
     private BigDecimal valor;

    public RelacionobrafuenterecursoscontratoDTO() {
    }	
   
    
    public RelacionobrafuenterecursoscontratoDTO(int idrelacionobracontrato, ContratoDTO contrato, ObrafuenterecursosconveniosDTO obrafuenterecursosconvenios, BigDecimal valor) {
       this.idrelacionobracontrato = idrelacionobracontrato;
       this.contrato = contrato;
       this.obrafuenterecursosconvenios = obrafuenterecursosconvenios;
       this.valor = valor;
    }
    
     public RelacionobrafuenterecursoscontratoDTO(Relacionobrafuenterecursoscontrato rofr) {
       this.idrelacionobracontrato = rofr.getIdrelacionobracontrato();
       this.contrato = new ContratoDTO(rofr.getContrato());
       this.obrafuenterecursosconvenios =new ObrafuenterecursosconveniosDTO(rofr.getObrafuenterecursosconvenios());
       this.valor = rofr.getValor();
               
    }

    /**
     * @return the idrelacionobracontrato
     */
    public int getIdrelacionobracontrato() {
        return idrelacionobracontrato;
    }

    /**
     * @param idrelacionobracontrato the idrelacionobracontrato to set
     */
    public void setIdrelacionobracontrato(int idrelacionobracontrato) {
        this.idrelacionobracontrato = idrelacionobracontrato;
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
     * @return the obrafuenterecursosconvenios
     */
    public ObrafuenterecursosconveniosDTO getObrafuenterecursosconvenios() {
        return obrafuenterecursosconvenios;
    }

    /**
     * @param obrafuenterecursosconvenios the obrafuenterecursosconvenios to set
     */
    public void setObrafuenterecursosconvenios(ObrafuenterecursosconveniosDTO obrafuenterecursosconvenios) {
        this.obrafuenterecursosconvenios = obrafuenterecursosconvenios;
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
    
    
   
    



}


