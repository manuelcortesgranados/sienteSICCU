/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client.dto;

import co.com.interkont.cobra.to.Contrato;
import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author desarrollo9
 */
public class ContratoDTO implements Serializable {

    private int intidcontrato;
    private Date datefechaini;
    private Date datefechafin;
    private String strnumcontrato;
    private BigDecimal numvlrcontrato;
    private Date datefechacreacion;
    private Set fuenterecursosconvenios = new HashSet(0);
    private Set actividadobras = new HashSet(0);
    
    public ContratoDTO() {
    }
    
    
    public ContratoDTO(Date datefechaini, Date datefechafin, String strnumcontrato, BigDecimal numvlrcontrato, Date datefechacreacion) {
      
        this.datefechaini = datefechaini;
        this.datefechafin = datefechafin;
        this.strnumcontrato = strnumcontrato;
        this.numvlrcontrato = numvlrcontrato;
        this.datefechacreacion = datefechacreacion;
    }

    /**
     * @return the intidcontrato
     */
    public int getIntidcontrato() {
        return intidcontrato;
    }

    /**
     * @param intidcontrato the intidcontrato to set
     */
    public void setIntidcontrato(int intidcontrato) {
        this.intidcontrato = intidcontrato;
    }

    /**
     * @return the datefechaini
     */
    public Date getDatefechaini() {
        return datefechaini;
    }

    /**
     * @param datefechaini the datefechaini to set
     */
    public void setDatefechaini(Date datefechaini) {
        this.datefechaini = datefechaini;
    }

    /**
     * @return the datefechafin
     */
    public Date getDatefechafin() {
        return datefechafin;
    }

    /**
     * @param datefechafin the datefechafin to set
     */
    public void setDatefechafin(Date datefechafin) {
        this.datefechafin = datefechafin;
    }

    /**
     * @return the strnumcontrato
     */
    public String getStrnumcontrato() {
        return strnumcontrato;
    }

    /**
     * @param strnumcontrato the strnumcontrato to set
     */
    public void setStrnumcontrato(String strnumcontrato) {
        this.strnumcontrato = strnumcontrato;
    }

    /**
     * @return the numvlrcontrato
     */
    public BigDecimal getNumvlrcontrato() {
        return numvlrcontrato;
    }

    /**
     * @param numvlrcontrato the numvlrcontrato to set
     */
    public void setNumvlrcontrato(BigDecimal numvlrcontrato) {
        this.numvlrcontrato = numvlrcontrato;
    }

    /**
     * @return the datefechacreacion
     */
    public Date getDatefechacreacion() {
        return datefechacreacion;
    }

    /**
     * @param datefechacreacion the datefechacreacion to set
     */
    public void setDatefechacreacion(Date datefechacreacion) {
        this.datefechacreacion = datefechacreacion;
    }

    /**
     * @return the fuenterecursosconvenios
     */
    public Set getFuenterecursosconvenios() {
        return fuenterecursosconvenios;
    }

    /**
     * @param fuenterecursosconvenios the fuenterecursosconvenios to set
     */
    public void setFuenterecursosconvenios(Set fuenterecursosconvenios) {
        this.fuenterecursosconvenios = fuenterecursosconvenios;
    }

    /**
     * @return the actividadobras
     */
    public Set getActividadobras() {
        return actividadobras;
    }

    /**
     * @param actividadobras the actividadobras to set
     */
    public void setActividadobras(Set actividadobras) {
        this.actividadobras = actividadobras;
    }
    
    
}
