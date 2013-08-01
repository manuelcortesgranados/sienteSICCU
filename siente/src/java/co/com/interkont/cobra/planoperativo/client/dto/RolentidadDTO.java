/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Daniela
 */
public class RolentidadDTO implements IsSerializable{
    
     private int idrolentidad;
     private String strnombre;
     private Set fuenterecursosconvenios = new HashSet(0);

    public RolentidadDTO() {
    }
	
    public RolentidadDTO(int idrolentidad) {
        this.idrolentidad = idrolentidad;
    }
    public RolentidadDTO(int idrolentidad, String strnombre) {
       this.idrolentidad = idrolentidad;
       this.strnombre = strnombre;
       
    }
   
    public int getIdrolentidad() {
        return this.idrolentidad;
    }
    
    public void setIdrolentidad(int idrolentidad) {
        this.idrolentidad = idrolentidad;
    }
    public String getStrnombre() {
        return this.strnombre;
    }
    
    public void setStrnombre(String strnombre) {
        this.strnombre = strnombre;
    }
    public Set getFuenterecursosconvenios() {
        return this.fuenterecursosconvenios;
    }
    
    public void setFuenterecursosconvenios(Set fuenterecursosconvenios) {
        this.fuenterecursosconvenios = fuenterecursosconvenios;
    }


    
}
