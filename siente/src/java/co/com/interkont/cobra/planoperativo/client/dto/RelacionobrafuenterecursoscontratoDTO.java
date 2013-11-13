package co.com.interkont.cobra.planoperativo.client.dto;
// Generated Jul 12, 2013 4:10:17 PM by Hibernate Tools 3.2.1.GA


import co.com.interkont.cobra.to.Rubro;
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
     private String nombreEntidad;
      private RubroDTO rubro;
    private int vigenciafonade;
    private int vigenciafuente;
     private String eliminar;
    private String descripcionRubro;
     private String valorFormato;

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
    
     public RelacionobrafuenterecursoscontratoDTO(int id, ObrafuenterecursosconveniosDTO obrafuenterecursosconvenios, BigDecimal valor,String nombreEntidad,RubroDTO rubro,Integer vigenciafonade,Integer vigenciafuente,String descripcionRubro) {
       this.obrafuenterecursosconvenios = obrafuenterecursosconvenios;
       this.valor = valor;
       this.idrelacionobracontrato=id;
       this.nombreEntidad=nombreEntidad;
       this.rubro=rubro;
       this.vigenciafonade=vigenciafonade;
       this.vigenciafuente=vigenciafuente;
       this.descripcionRubro=descripcionRubro;
       this.eliminar="Eliminar";
    }
     
      public RelacionobrafuenterecursoscontratoDTO(int id, ObrafuenterecursosconveniosDTO obrafuenterecursosconvenios,String nombreEntidad,RubroDTO rubro,int vigenciafonade,int vigenciafuente) {
       this.obrafuenterecursosconvenios = obrafuenterecursosconvenios;
       this.idrelacionobracontrato=id;
       this.nombreEntidad=nombreEntidad;       
       this.rubro=rubro;
       this.vigenciafonade=vigenciafonade;
       this.vigenciafuente=vigenciafuente;
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

    /**
     * @return the rubro
     */
    public RubroDTO getRubro() {
        return rubro;
    }

    /**
     * @param rubro the rubro to set
     */
    public void setRubro(RubroDTO rubro) {
        this.rubro = rubro;
    }

    /**
     * @return the vigenciafonade
     */
    public int getVigenciafonade() {
        return vigenciafonade;
    }

    /**
     * @param vigenciafonade the vigenciafonade to set
     */
    public void setVigenciafonade(int vigenciafonade) {
        this.vigenciafonade = vigenciafonade;
    }

    /**
     * @return the vigenciafuente
     */
    public int getVigenciafuente() {
        return vigenciafuente;
    }

    /**
     * @param vigenciafuente the vigenciafuente to set
     */
    public void setVigenciafuente(int vigenciafuente) {
        this.vigenciafuente = vigenciafuente;
    }

    /**
     * @return the descripcionRubro
     */
    public String getDescripcionRubro() {
        return descripcionRubro;
    }

    /**
     * @param descripcionRubro the descripcionRubro to set
     */
    public void setDescripcionRubro(String descripcionRubro) {
        this.descripcionRubro = descripcionRubro;
    }
    
       /**
     * @return the valorFormato
     */
    public String getValorFormato() {
        return valorFormato;
    }

    /**
     * @param valorFormato the valorFormato to set
     */
    public void setValorFormato(String valorFormato) {
        this.valorFormato = valorFormato;
    }
    
   
    



}


