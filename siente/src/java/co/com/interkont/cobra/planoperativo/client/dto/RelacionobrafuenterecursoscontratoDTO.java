package co.com.interkont.cobra.planoperativo.client.dto;
// Generated Jul 12, 2013 4:10:17 PM by Hibernate Tools 3.2.1.GA


import com.google.gwt.user.client.rpc.IsSerializable;
import java.math.BigDecimal;




/**
 * Relacionobrafuenterecursoscontrato generated by hbm2java
 */
public class RelacionobrafuenterecursoscontratoDTO  implements IsSerializable {


     private int idrelacionobracontrato;
     private ContratoDTO contrato;
     private ObrafuenterecursosconveniosDTO obrafuenterecursosconvenios;
     private BigDecimal valor;
     private int tipoaporte;
     private String nombreEntidad;
     private String nombreTipo;
     private String eliminar;

    public RelacionobrafuenterecursoscontratoDTO() {
    }	
   
    
    public RelacionobrafuenterecursoscontratoDTO(int idrelacionobracontrato,  ObrafuenterecursosconveniosDTO obrafuenterecursosconvenios, BigDecimal valor) {
       this.idrelacionobracontrato = idrelacionobracontrato;
       this.obrafuenterecursosconvenios = obrafuenterecursosconvenios;
       this.valor = valor;
    }
    
    public RelacionobrafuenterecursoscontratoDTO(int idrelacionobracontrato, BigDecimal valor) {
       this.idrelacionobracontrato = idrelacionobracontrato;
       this.valor = valor;
    }
    
     public RelacionobrafuenterecursoscontratoDTO(int id, ObrafuenterecursosconveniosDTO obrafuenterecursosconvenios, BigDecimal valor,int tipor,String nombreEntidad,String nombreTipo) {
       this.tipoaporte=tipor;
       this.obrafuenterecursosconvenios = obrafuenterecursosconvenios;
       this.valor = valor;
       this.idrelacionobracontrato=id;
       this.nombreEntidad=nombreEntidad;
       this.nombreTipo=nombreTipo;
       this.eliminar="Eliminar";
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

    /**
     * @return the tipoaporte
     */
    public int getTipoaporte() {
        return tipoaporte;
    }

    /**
     * @param tipoaporte the tipoaporte to set
     */
    public void setTipoaporte(int tipoaporte) {
        this.tipoaporte = tipoaporte;
    }

    /**
     * @return the nombreEntidad
     */
    public String getNombreEntidad() {
        return nombreEntidad;
    }

    /**
     * @param nombreEntidad the nombreEntidad to set
     */
    public void setNombreEntidad(String nombreEntidad) {
        this.nombreEntidad = nombreEntidad;
    }

    /**
     * @return the nombreTipo
     */
    public String getNombreTipo() {
        return nombreTipo;
    }

    /**
     * @param nombreTipo the nombreTipo to set
     */
    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

   
    /**
     * @return the eliminar
     */
    public String getEliminar() {
        return eliminar;
    }

    /**
     * @param eliminar the eliminar to set
     */
    public void setEliminar(String eliminar) {
        this.eliminar = eliminar;
    }
    
    
   
    



}


