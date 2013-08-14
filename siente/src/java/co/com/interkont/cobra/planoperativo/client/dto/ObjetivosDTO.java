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
public class ObjetivosDTO implements IsSerializable
{
    
     private int idobjetivo;
     private String descripcion;
     private Integer tipoobjetivo;
     private Boolean esobjetivo;
     private ObraDTO obra;

    public ObjetivosDTO() {
    }

	
	
    public ObjetivosDTO(int idobjetivo) {
        this.idobjetivo = idobjetivo;
    }
    public ObjetivosDTO(int idobjetivo, String descripcion, Integer tipoobjetivo, Boolean esobjetivo, ObraDTO obra) {
       this.idobjetivo = idobjetivo;
       this.descripcion = descripcion;
       this.tipoobjetivo = tipoobjetivo;
       this.esobjetivo = esobjetivo;
       this.obra = obra;
    }
   
     public ObjetivosDTO(String descripcion, Integer tipoobjetivo, Boolean esobjetivo) {
       this.descripcion = descripcion;
       this.tipoobjetivo = tipoobjetivo;
       this.esobjetivo = esobjetivo;
     
    }
     public ObjetivosDTO(String descripcion,Boolean esobjetivo) {
       this.descripcion = descripcion;
       this.esobjetivo = esobjetivo;
     
    }
   
    public int getIdobjetivo() {
        return this.idobjetivo;
    }
    
    public void setIdobjetivo(int idobjetivo) {
        this.idobjetivo = idobjetivo;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Integer getTipoobjetivo() {
        return this.tipoobjetivo;
    }
    
    public void setTipoobjetivo(Integer tipoobjetivo) {
        this.tipoobjetivo = tipoobjetivo;
    }
    public Boolean getEsobjetivo() {
        return this.esobjetivo;
    }
    
    public void setEsobjetivo(Boolean esobjetivo) {
        this.esobjetivo = esobjetivo;
    }
    public ObraDTO getobra() {
        return this.obra;
    }
    
    public void setobra(ObraDTO obra) {
        this.obra = obra;
    }


    
}
