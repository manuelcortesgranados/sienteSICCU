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
public class ObraDTO implements IsSerializable {

    private int intcodigoobra;
    private String strnombreobra;
    private Date fechaInicio;
    private Date fechaFin;
    private Set objetivoses = new HashSet(0);
    private Set obrafuenterecursosconvenioses = new HashSet(0);
    private Set actividadobras = new LinkedHashSet(0);
    private BigDecimal valorDisponible;
    private BigDecimal valor;
     private BigDecimal pagodirecto;
    private BigDecimal otrospagos;

    public ObraDTO() {
    }
    

    public ObraDTO(int intcodigoobra, String strnombreobra) {
        this.intcodigoobra = intcodigoobra;
        this.strnombreobra = strnombreobra;
        this.valor=BigDecimal.ZERO;
        this.valorDisponible=BigDecimal.ZERO;
    }
   
    /**
     * @return the intcodigoobra
     */
    public int getIntcodigoobra() {
        return intcodigoobra;
    }

    /**
     * @param intcodigoobra the intcodigoobra to set
     */
    public void setIntcodigoobra(int intcodigoobra) {
        this.intcodigoobra = intcodigoobra;
    }

    
    /**
     * @return the objetivoses
     */
    public Set getObjetivoses() {
        return objetivoses;
    }

    /**
     * @param objetivoses the objetivoses to set
     */
    public void setObjetivoses(Set objetivoses) {
        this.objetivoses = objetivoses;
    }

    /**
     * @return the obrafuenterecursosconvenioses
     */
    public Set getObrafuenterecursosconvenioses() {
        return obrafuenterecursosconvenioses;
    }

    /**
     * @param obrafuenterecursosconvenioses the obrafuenterecursosconvenioses to set
     */
    public void setObrafuenterecursosconvenioses(Set obrafuenterecursosconvenioses) {
        this.obrafuenterecursosconvenioses = obrafuenterecursosconvenioses;
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
     * @return the strnombreobra
     */
    public String getStrnombreobra() {
        return strnombreobra;
    }

    /**
     * @param strnombreobra the strnombreobra to set
     */
    public void setStrnombreobra(String strnombreobra) {
        this.strnombreobra = strnombreobra;
    }

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
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
     * @return the pagodirecto
     */
    public BigDecimal getPagodirecto() {
        return pagodirecto;
    }

    /**
     * @param pagodirecto the pagodirecto to set
     */
    public void setPagodirecto(BigDecimal pagodirecto) {
        this.pagodirecto = pagodirecto;
    }

    /**
     * @return the otrospagos
     */
    public BigDecimal getOtrospagos() {
        return otrospagos;
    }

    /**
     * @param otrospagos the otrospagos to set
     */
    public void setOtrospagos(BigDecimal otrospagos) {
        this.otrospagos = otrospagos;
    }
    
    
}
