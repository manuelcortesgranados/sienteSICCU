/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cobra.Financiera;

import java.io.Serializable;

/**
 *
 * @author desarrollo5
 */
public class ExcelFinanciero implements Serializable{

    private String fecha;
    private String ordenPago;
    private String numeroEncargoFidu;
    private String fechaCreacionEncargoFidu;
    private String comprobanteFinanciero;
    private String nitBeneficiario;
    private String tipoBeneficiario;
    private String departamento;
    private String municipio;
    private String categoria;
    private String proyecto;
    private String nitContratista;
    private String nombreContratista;
    private String fechaInitRecursos;
    private String valorMovimiento;
    private String valorEjecutado;
    private String valorLegalizado;
    private String fechaLegalizacion;
    private String tipoMovimiento;
    private String numvlrreintegro;
    private int fila;
    

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getComprobanteFinanciero() {
        return comprobanteFinanciero;
    }

    public void setComprobanteFinanciero(String comprobanteFinanciero) {
        this.comprobanteFinanciero = comprobanteFinanciero;
    }
    
    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFechaCreacionEncargoFidu() {
        return fechaCreacionEncargoFidu;
    }

    public void setFechaCreacionEncargoFidu(String fechaCreacionEncargoFidu) {
        this.fechaCreacionEncargoFidu = fechaCreacionEncargoFidu;
    }

    public String getFechaInitRecursos() {
        return fechaInitRecursos;
    }

    public void setFechaInitRecursos(String fechaInitRecursos) {
        this.fechaInitRecursos = fechaInitRecursos;
    }

    public String getFechaLegalizacion() {
        return fechaLegalizacion;
    }

    public void setFechaLegalizacion(String fechaLegalizacion) {
        this.fechaLegalizacion = fechaLegalizacion;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getNitBeneficiario() {
        return nitBeneficiario;
    }

    public void setNitBeneficiario(String nitBeneficiario) {
        this.nitBeneficiario = nitBeneficiario;
    }

    public String getNitContratista() {
        return nitContratista;
    }

    public void setNitContratista(String nitContratista) {
        this.nitContratista = nitContratista;
    }

    public String getNombreContratista() {
        return nombreContratista;
    }

    public void setNombreContratista(String nombreContratista) {
        this.nombreContratista = nombreContratista;
    }

    public String getNumeroEncargoFidu() {
        return numeroEncargoFidu;
    }

    public void setNumeroEncargoFidu(String numeroEncargoFidu) {
        this.numeroEncargoFidu = numeroEncargoFidu;
    }

    public String getOrdenPago() {
        return ordenPago;
    }

    public void setOrdenPago(String ordenPago) {
        this.ordenPago = ordenPago;
    }

    public String getProyecto() {
        return proyecto;
    }

    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    public String getTipoBeneficiario() {
        return tipoBeneficiario;
    }

    public void setTipoBeneficiario(String tipoBeneficiario) {
        this.tipoBeneficiario = tipoBeneficiario;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public String getValorEjecutado() {
        return valorEjecutado;
    }

    public void setValorEjecutado(String valorEjecutado) {
        this.valorEjecutado = valorEjecutado;
    }

    public String getValorLegalizado() {
        return valorLegalizado;
    }

    public void setValorLegalizado(String valorLegalizado) {
        this.valorLegalizado = valorLegalizado;
    }

    public String getValorMovimiento() {
        return valorMovimiento;
    }

    public void setValorMovimiento(String valorMovimiento) {
        this.valorMovimiento = valorMovimiento;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public String getNumvlrreintegro() {
        return numvlrreintegro;
    }

    public void setNumvlrreintegro(String numvlrreintegro) {
        this.numvlrreintegro = numvlrreintegro;
    }

    
}
