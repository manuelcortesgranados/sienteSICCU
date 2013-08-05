/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client.dto;

import co.com.interkont.cobra.to.Estadoconvenio;
import co.com.interkont.cobra.to.Tercero;
import co.com.interkont.cobra.to.Tipocontrato;
import com.google.gwt.user.client.rpc.IsSerializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author desarrollo9
 */
public class ContratoDTO implements IsSerializable {

    private int intidcontrato;
    private Date datefechaini;
    private Date datefechafin;
    private String strnumcontrato;
    private BigDecimal numvlrcontrato;
    private Date datefechacreacion;
    private String textobjeto;
    private TerceroDTO gerenteconvenio;
    private Set fuenterecursosconvenios = new HashSet(0);
    private int estadoConvenio;
    //tipo contrato es para contrato solamente
    private TipocontratoDTO tipocontrato;
    private Boolean booltipocontratoconvenio;
    private Set actividadobras = new HashSet(0);
    private Set relacionobrafuenterecursoscontratos = new HashSet(0);

    public ContratoDTO() {
    }

    public ContratoDTO(Date datefechaini, Date datefechafin, String strnumcontrato, BigDecimal numvlrcontrato, Date datefechacreacion) {

        this.datefechaini = datefechaini;
        this.datefechafin = datefechafin;
        this.strnumcontrato = strnumcontrato;
        this.numvlrcontrato=numvlrcontrato;
        this.datefechacreacion = datefechacreacion;
    }
    
     public ContratoDTO(int intidcontrato,String textobjeto,BigDecimal numvlrcontrato) {
        this.intidcontrato=intidcontrato;
        this.textobjeto=textobjeto;
        this.numvlrcontrato=numvlrcontrato;
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

    /**
     * @return the relacionobrafuenterecursoscontratos
     */
    public Set getRelacionobrafuenterecursoscontratos() {
        return relacionobrafuenterecursoscontratos;
    }

    /**
     * @param relacionobrafuenterecursoscontratos the
     * relacionobrafuenterecursoscontratos to set
     */
    public void setRelacionobrafuenterecursoscontratos(Set relacionobrafuenterecursoscontratos) {
        this.relacionobrafuenterecursoscontratos = relacionobrafuenterecursoscontratos;
    }

    /**
     * @return the textobjeto
     */
    public String getTextobjeto() {
        return textobjeto;
    }

    /**
     * @param textobjeto the textobjeto to set
     */
    public void setTextobjeto(String textobjeto) {
        this.textobjeto = textobjeto;
    }

    /**
     * @return the gerenteconvenio
     */
    public TerceroDTO getGerenteconvenio() {
        return gerenteconvenio;
    }

    /**
     * @param gerenteconvenio the gerenteconvenio to set
     */
    public void setGerenteconvenio(TerceroDTO gerenteconvenio) {
        this.gerenteconvenio = gerenteconvenio;
    }

    
    /**
     * @return the tipocontrato
     */
    public TipocontratoDTO getTipocontrato() {
        return tipocontrato;
    }

    /**
     * @param tipocontrato the tipocontrato to set
     */
    public void setTipocontrato(TipocontratoDTO tipocontrato) {
        this.tipocontrato = tipocontrato;
    }

    /**
     * @return the booltipocontratoconvenio
     */
    public Boolean getBooltipocontratoconvenio() {
        return booltipocontratoconvenio;
    }

    /**
     * @param booltipocontratoconvenio the booltipocontratoconvenio to set
     */
    public void setBooltipocontratoconvenio(Boolean booltipocontratoconvenio) {
        this.booltipocontratoconvenio = booltipocontratoconvenio;
    }

    /**
     * @return the estadoConvenio
     */
    public int getEstadoConvenio() {
        return estadoConvenio;
    }

    /**
     * @param estadoConvenio the estadoConvenio to set
     */
    public void setEstadoConvenio(int estadoConvenio) {
        this.estadoConvenio = estadoConvenio;
    }

   
}
