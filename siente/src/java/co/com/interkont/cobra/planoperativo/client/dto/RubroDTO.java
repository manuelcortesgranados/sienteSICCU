/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client.dto;

import co.com.interkont.cobra.to.Rubro;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author desarrollo9
 */
public class RubroDTO implements Serializable{

     private int idrubro;
     private String strdescripcion;
     private Set obrafuenterecursosconvenioses = new HashSet(0);
     private Set montos = new HashSet(0);

    public RubroDTO() {
    }
    
    RubroDTO(Rubro rubro) {
       this.idrubro=rubro.getIdrubro();
       this.strdescripcion=rubro.getStrdescripcion();       
    }

    public RubroDTO(int idrubro, String strdescripcion,Set obrafuenterecursosconvenioses,Set montos) {
        this.idrubro = idrubro;
        this.strdescripcion = strdescripcion;
        this.montos=montos;
        this.obrafuenterecursosconvenioses=obrafuenterecursosconvenioses;
    }

    public int getIdrubro() {
        return idrubro;
    }

    public void setIdrubro(int idrubro) {
        this.idrubro = idrubro;
    }

    public String getStrdescripcion() {
        return strdescripcion;
    }

    public void setStrdescripcion(String strdescripcion) {
        this.strdescripcion = strdescripcion;
    }

    public Set getObrafuenterecursosconvenioses() {
        return obrafuenterecursosconvenioses;
    }

    public void setObrafuenterecursosconvenioses(Set obrafuenterecursosconvenioses) {
        this.obrafuenterecursosconvenioses = obrafuenterecursosconvenioses;
    }

    public Set getMontos() {
        return montos;
    }

    public void setMontos(Set montos) {
        this.montos = montos;
    }
    
    
    
    
}
