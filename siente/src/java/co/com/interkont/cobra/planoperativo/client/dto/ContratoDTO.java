/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client.dto;

import co.com.interkont.cobra.to.Contrato;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author desarrollo9
 */
public class ContratoDTO implements Serializable {
    
 private int intidcontrato;
 private String strnombre;
 private Set fuenterecursosconvenios = new HashSet(0);
 private Set montos = new HashSet(0);
 private Set actividadobras = new HashSet(0);
 private Set relacionobrafuenterecursoscontratos = new HashSet(0);
 private Set obras = new HashSet(0);
 private Set relacioncontratoobras = new HashSet(0);

    public ContratoDTO() {
    }
    

    public ContratoDTO(Contrato contrato) {
        this.intidcontrato = contrato.getIntidcontrato();
        this.strnombre = contrato.getStrnombre();               
    }
    
    public ContratoDTO(int intidcontrato, String strnombre) {
        this.intidcontrato = intidcontrato;
        this.strnombre = strnombre;
    }
    public ContratoDTO(int intidcontrato, String strnombre, Set fuenterecursosconvenios, Set montos, Set actividadobras, Set relacionobrafuenterecursoscontratos, Set obras, Set relacioncontratoobras    ) {
        this.intidcontrato = intidcontrato;
        this.strnombre = strnombre;
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
     * @return the strnombre
     */
    public String getStrnombre() {
        return strnombre;
    }

    /**
     * @param strnombre the strnombre to set
     */
    public void setStrnombre(String strnombre) {
        this.strnombre = strnombre;
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
     * @param relacionobrafuenterecursoscontratos the relacionobrafuenterecursoscontratos to set
     */
    public void setRelacionobrafuenterecursoscontratos(Set relacionobrafuenterecursoscontratos) {
        this.relacionobrafuenterecursoscontratos = relacionobrafuenterecursoscontratos;
    }

    /**
     * @return the obras
     */
    public Set getObras() {
        return obras;
    }

    /**
     * @param obras the obras to set
     */
    public void setObras(Set obras) {
        this.obras = obras;
    }

    /**
     * @return the relacioncontratoobras
     */
    public Set getRelacioncontratoobras() {
        return relacioncontratoobras;
    }

    /**
     * @param relacioncontratoobras the relacioncontratoobras to set
     */
    public void setRelacioncontratoobras(Set relacioncontratoobras) {
        this.relacioncontratoobras = relacioncontratoobras;
    }
    

    
}
