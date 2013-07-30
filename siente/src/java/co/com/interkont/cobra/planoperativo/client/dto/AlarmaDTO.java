/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client.dto;

import co.com.interkont.cobra.to.Alarma;
import co.com.interkont.cobra.to.Relaciontipoobraalarma;
import co.com.interkont.cobra.to.Semaforo;
import com.interkont.cobra.util.CobraUtil;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author desarrollo9
 */
public class AlarmaDTO implements Serializable{
    private int intidalarma;
    private BigDecimal numvalorini;
    private BigDecimal numvalorfin;
    private Set<SemaforoDTO> semaforos = new HashSet(0);
    private Set<RelaciontipoobraalarmaDTO> relaciontipoobraalarmas = new HashSet(0);

    public AlarmaDTO() {
    }

    public AlarmaDTO(int intidalarma, BigDecimal numvalorini,BigDecimal numvalorfin) {
        System.out.println("numvalorfin = " + intidalarma);
        this.intidalarma = intidalarma;
        this.numvalorini = numvalorini;
        this.numvalorfin = numvalorfin;
        
    }

    public AlarmaDTO(int intidalarma, BigDecimal numvalorini, BigDecimal numvalorfin, Set semaforos, Set relaciontipoobraalarmas) {
        System.out.println("relaciontipoobraalarmas = " );
        this.intidalarma = intidalarma;
        this.numvalorini = numvalorini;
        this.numvalorfin = numvalorfin;
        this.semaforos = semaforos;
        this.relaciontipoobraalarmas = relaciontipoobraalarmas;
    }

//    public AlarmaDTO(Alarma alarma){
//        this.intidalarma = alarma.getIntidalarma();
//        this.numvalorini = alarma.getNumvalorini();
//        this.numvalorfin = alarma.getNumvalorfin();       
//        
//
//    }

    public int getIntidalarma() {
        return this.intidalarma;
    }

    public void setIntidalarma(int intidalarma) {
        this.intidalarma = intidalarma;
    }

    public BigDecimal getNumvalorini() {
        return this.numvalorini;
    }

    public void setNumvalorini(BigDecimal numvalorini) {
        this.numvalorini = numvalorini;
    }

    public BigDecimal getNumvalorfin() {
        return this.numvalorfin;
    }

    public void setNumvalorfin(BigDecimal numvalorfin) {
        this.numvalorfin = numvalorfin;
    }

    public Set getSemaforos() {
        return this.semaforos;
    }

    public void setSemaforos(Set semaforos) {
        this.semaforos = semaforos;
    }

    public Set getRelaciontipoobraalarmas() {
        return this.relaciontipoobraalarmas;
    }

    public void setRelaciontipoobraalarmas(Set relaciontipoobraalarmas) {
        this.relaciontipoobraalarmas = relaciontipoobraalarmas;
    }
}
