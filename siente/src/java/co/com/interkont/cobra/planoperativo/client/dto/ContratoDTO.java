/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author desarrollo9
 */
public class ContratoDTO implements IsSerializable {

    private int intidcontrato;
    private Date datefechaini;
    private Date datefechafin;
    private Date datefechaactaini;
    private String strnumcontrato;
    private BigDecimal numvlrcontrato;
    private Date datefechacreacion;
    private String textobjeto;
    private int estadoConvenio;
    private int intduraciondias;
    //tipo contrato es para contrato solamente
    private TipocontratoDTO tipocontrato;
    private String nombreAbreviado;
    private BigDecimal valorDisponible;
    private Set actividadobras = new LinkedHashSet(0);
    private Set relacionobrafuenterecursoscontratos = new HashSet(0);
    private Set fuenterecursosconvenios = new HashSet(0);
    private Set montos = new HashSet(0);
    private Set dependenciasGenerales = new LinkedHashSet(0);

    public ContratoDTO() {
    }

    public ContratoDTO(int intidcontrato, Date datefechaini, Date datefechafin, Date datefechaactaini, String strnumcontrato, BigDecimal numvlrcontrato, Date datefechacreacion, String textobjeto, int estadoConvenio, int intduraciondias, TipocontratoDTO tipocontrato, String strnombrecorto) {
        this.intidcontrato = intidcontrato;
        this.datefechaini = datefechaini;
        this.datefechafin = datefechafin;
        this.datefechaactaini = datefechaactaini;
        this.strnumcontrato = strnumcontrato;
        this.numvlrcontrato = numvlrcontrato;
        this.datefechacreacion = datefechacreacion;
        this.textobjeto = textobjeto;
        this.estadoConvenio = estadoConvenio;
        this.intduraciondias = intduraciondias;
        this.tipocontrato = tipocontrato;
        this.nombreAbreviado = strnombrecorto;
    }

//    
//    public ContratoDTO(int intidcontrato, Date datefechaini, Date datefechafin, String strnumcontrato, BigDecimal numvlrcontrato, Date datefechaactaini, int intduraciondias) {
//        this.intidcontrato=intidcontrato;
//        this.datefechaini = datefechaini;
//        this.datefechafin = datefechafin;
//        this.strnumcontrato = strnumcontrato;
//        this.numvlrcontrato=numvlrcontrato;
//        this.datefechaactaini = datefechaactaini;
//        this.intduraciondias= intduraciondias;
//    }
//    
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

    /**
     * @return datefechaactaini Fecha acta de inicio
     */
    public Date getDatefechaactaini() {
        return datefechaactaini;
    }

    public void setDatefechaactaini(Date datefechaactaini) {
        this.datefechaactaini = datefechaactaini;
    }

    public int getIntduraciondias() {
        return intduraciondias;
    }

    public void setIntduraciondias(int intduraciondias) {
        this.intduraciondias = intduraciondias;
    }

    /**
     * @return the montos
     */
    public Set getMontos() {
        return montos;
    }

    /**
     * @param montos the montos to set
     */
    public void setMontos(Set montos) {
        this.montos = montos;
    }

    /**
     * @return the nombreAbreviado
     */
    public String getNombreAbreviado() {
        return nombreAbreviado;
    }

    /**
     * @param nombreAbreviado the nombreAbreviado to set
     */
    public void setNombreAbreviado(String nombreAbreviado) {
        this.nombreAbreviado = nombreAbreviado;
    }

    /**
     * @return the valorDisponible
     */
    public BigDecimal getValorDisponible() {
        return valorDisponible;
    }

    /**
     * @param valorDisponible the valorDisponible to set
     */
    public void setValorDisponible(BigDecimal valorDisponible) {
        this.valorDisponible = valorDisponible;
    }

    /**
     * @return the dependenciasGenerales
     */
    public Set getDependenciasGenerales() {
        return dependenciasGenerales;
    }

    /**
     * @param dependenciasGenerales the dependenciasGenerales to set
     */
    public void setDependenciasGenerales(Set dependenciasGenerales) {
        this.dependenciasGenerales = dependenciasGenerales;
    }
}
