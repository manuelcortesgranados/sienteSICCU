/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client.dto;

import co.com.interkont.cobra.to.Contrato;
import co.com.interkont.cobra.to.Fuenterecursosconvenio;
import com.google.gwt.user.client.rpc.IsSerializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author desarrollo9
 */
public class FuenterecursosconvenioDTO implements IsSerializable {

    private int idfuenterecursosconvenio;
    private TerceroDTO tercero;
    private ContratoDTO contrato;
    private RolentidadDTO rolentidad;
    private BigDecimal valoraportado;
    private BigDecimal otrasreservas;
    private BigDecimal reservaiva;
    private BigDecimal valorcuotagerencia;
    private Integer tipoaporte;
    private Set obrafuenterecursosconvenioses = new HashSet(0);
    private BigDecimal valorDisponible;
    private Integer vigencia;
    private boolean estaEnFuenteRecurso;

    public FuenterecursosconvenioDTO() {
    }
    
    

    
    public FuenterecursosconvenioDTO(int idfuenterecursosconvenio, ContratoDTO contrato, BigDecimal valoraportado, BigDecimal otrasreservas, BigDecimal reservaiva, BigDecimal valorcuotagerencia, Integer tipoaporte,BigDecimal valorDisponible) {
        this.idfuenterecursosconvenio = idfuenterecursosconvenio;
        this.contrato = contrato;
        this.valoraportado = valoraportado;
        this.otrasreservas = otrasreservas;
        this.reservaiva = reservaiva;
        this.valorcuotagerencia = valorcuotagerencia;
        this.tipoaporte = tipoaporte;
        this.valorDisponible=valorDisponible;
    }
    
    
    public FuenterecursosconvenioDTO(int idfuenterecursosconvenio, TerceroDTO tercero, ContratoDTO contrato, BigDecimal valoraportado, BigDecimal otrasreservas, BigDecimal reservaiva, BigDecimal valorcuotagerencia, Integer tipoaporte) {
        this.idfuenterecursosconvenio = idfuenterecursosconvenio;
        this.tercero = tercero;
        this.contrato = contrato;
        this.valoraportado = valoraportado;
        this.otrasreservas = otrasreservas;
        this.reservaiva = reservaiva;
        this.valorcuotagerencia = valorcuotagerencia;
        this.tipoaporte = tipoaporte;
    }
    
   
    

    /**
     * @return the idfuenterecursosconvenio
     */
    public int getIdfuenterecursosconvenio() {
        return idfuenterecursosconvenio;
    }

    /**
     * @param idfuenterecursosconvenio the idfuenterecursosconvenio to set
     */
    public void setIdfuenterecursosconvenio(int idfuenterecursosconvenio) {
        this.idfuenterecursosconvenio = idfuenterecursosconvenio;
    }

    /**
     * @return the tercero
     */
    public TerceroDTO getTercero() {
        return tercero;
    }

    /**
     * @param tercero the tercero to set
     */
    public void setTercero(TerceroDTO tercero) {
        this.tercero = tercero;
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
     * @return the valoraportado
     */
    public BigDecimal getValoraportado() {
        return valoraportado;
    }

    /**
     * @param valoraportado the valoraportado to set
     */
    public void setValoraportado(BigDecimal valoraportado) {
        this.valoraportado = valoraportado;
    }

    /**
     * @return the otrasreservas
     */
    public BigDecimal getOtrasreservas() {
        return otrasreservas;
    }

    /**
     * @param otrasreservas the otrasreservas to set
     */
    public void setOtrasreservas(BigDecimal otrasreservas) {
        this.otrasreservas = otrasreservas;
    }

    /**
     * @return the reservaiva
     */
    public BigDecimal getReservaiva() {
        return reservaiva;
    }

    /**
     * @param reservaiva the reservaiva to set
     */
    public void setReservaiva(BigDecimal reservaiva) {
        this.reservaiva = reservaiva;
    }

    /**
     * @return the valorcuotagerencia
     */
    public BigDecimal getValorcuotagerencia() {
        return valorcuotagerencia;
    }

    /**
     * @param valorcuotagerencia the valorcuotagerencia to set
     */
    public void setValorcuotagerencia(BigDecimal valorcuotagerencia) {
        this.valorcuotagerencia = valorcuotagerencia;
    }

    /**
     * @return the tipoaporte
     */
    public Integer getTipoaporte() {
        return tipoaporte;
    }

    /**
     * @param tipoaporte the tipoaporte to set
     */
    public void setTipoaporte(Integer tipoaporte) {
        this.tipoaporte = tipoaporte;
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
     * @return the rolentidad
     */
    public RolentidadDTO getRolentidad() {
        return rolentidad;
    }

    /**
     * @param rolentidad the rolentidad to set
     */
    public void setRolentidad(RolentidadDTO rolentidad) {
        this.rolentidad = rolentidad;
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
     * @return the vigencia
     */
    public Integer getVigencia() {
        return vigencia;
    }

    /**
     * @param vigencia the vigencia to set
     */
    public void setVigencia(Integer vigencia) {
        this.vigencia = vigencia;
    }

    /**
     * @return the estaEnFuenteRecurso
     */
    public boolean isEstaEnFuenteRecurso() {
        return estaEnFuenteRecurso;
    }

    /**
     * @param estaEnFuenteRecurso the estaEnFuenteRecurso to set
     */
    public void setEstaEnFuenteRecurso(boolean estaEnFuenteRecurso) {
        this.estaEnFuenteRecurso = estaEnFuenteRecurso;
    }
    
}
