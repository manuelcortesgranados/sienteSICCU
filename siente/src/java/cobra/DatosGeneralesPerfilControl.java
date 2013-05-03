/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra;

import co.com.interkont.cobra.to.ControlPanel;
import cobra.Supervisor.FacesUtils;
import cobra.service.CobraServiceAble;
import java.io.Serializable;

/**
 *
 * @author desarrollo2
 */
public class DatosGeneralesPerfilControl  implements Serializable {

    public DatosGeneralesPerfilControl() {
    }
    private ControlPanel inversiontotal;
    private ControlPanel ejecuciontotal;
    private ControlPanel personasbeneficiadas;
    private ControlPanel empleosgenerados;
    private ControlPanel arriendosentregados;
    private ControlPanel viviendasreparadas;
    private ControlPanel unidadeshabconstruidas;
    private ControlPanel totalinversionconvenio;
    private ControlPanel obrassolicitadas;
    private ControlPanel obrasaprobadas;
    private ControlPanel obrasenejecucion;
    private ControlPanel obrasfinalizadas;
    private ControlPanel totalpagado;

    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }

    public ControlPanel getArriendosentregados() {
        return arriendosentregados;
    }

    public void setArriendosentregados(ControlPanel arriendosentregados) {
        this.arriendosentregados = arriendosentregados;
    }

    public ControlPanel getTotalpagado() {
        return totalpagado;
    }

    public void setTotalpagado(ControlPanel totalpagado) {
        this.totalpagado = totalpagado;
    }

    public ControlPanel getUnidadeshabconstruidas() {
        return unidadeshabconstruidas;
    }

    public void setUnidadeshabconstruidas(ControlPanel unidadeshabconstruidas) {
        this.unidadeshabconstruidas = unidadeshabconstruidas;
    }

    public ControlPanel getViviendasreparadas() {
        return viviendasreparadas;
    }

    public void setViviendasreparadas(ControlPanel viviendasreparadas) {
        this.viviendasreparadas = viviendasreparadas;
    }

    public ControlPanel getEjecuciontotal() {
        return ejecuciontotal;
    }

    public void setEjecuciontotal(ControlPanel ejecuciontotal) {
        this.ejecuciontotal = ejecuciontotal;
    }

    public ControlPanel getEmpleosgenerados() {
        return empleosgenerados;
    }

    public void setEmpleosgenerados(ControlPanel empleosgenerados) {
        this.empleosgenerados = empleosgenerados;
    }

    public ControlPanel getObrasaprobadas() {
        return obrasaprobadas;
    }

    public void setObrasaprobadas(ControlPanel obrasaprobadas) {
        this.obrasaprobadas = obrasaprobadas;
    }

    public ControlPanel getObrasenejecucion() {
        return obrasenejecucion;
    }

    public void setObrasenejecucion(ControlPanel obrasenejecucion) {
        this.obrasenejecucion = obrasenejecucion;
    }

    public ControlPanel getObrasfinalizadas() {
        return obrasfinalizadas;
    }

    public void setObrasfinalizadas(ControlPanel obrasfinalizadas) {
        this.obrasfinalizadas = obrasfinalizadas;
    }

    public ControlPanel getObrassolicitadas() {
        return obrassolicitadas;
    }

    public void setObrassolicitadas(ControlPanel obrassolicitadas) {
        this.obrassolicitadas = obrassolicitadas;
    }

    public ControlPanel getPersonasbeneficiadas() {
        return personasbeneficiadas;
    }

    public void setPersonasbeneficiadas(ControlPanel personasbeneficiadas) {
        this.personasbeneficiadas = personasbeneficiadas;
    }

    public ControlPanel getTotalinversionconvenio() {
        return totalinversionconvenio;
    }

    public void setTotalinversionconvenio(ControlPanel totalinversionconvenio) {
        this.totalinversionconvenio = totalinversionconvenio;
    }

    public ControlPanel getInversiontotal() {
        return inversiontotal;
    }

    public void setInversiontotal(ControlPanel inversiontotal) {
        this.inversiontotal = inversiontotal;
    }

    public String encontrarEtiqueta() {

       
        inversiontotal = getSessionBeanCobra().getCobraService().buscarxetiqueta("inversiontotal");
        ejecuciontotal = getSessionBeanCobra().getCobraService().buscarxetiqueta("ejecuciontotal");
        personasbeneficiadas = getSessionBeanCobra().getCobraService().buscarxetiqueta("personasbeneficiadas");
        empleosgenerados = getSessionBeanCobra().getCobraService().buscarxetiqueta("empleosgenerados");
        arriendosentregados = getSessionBeanCobra().getCobraService().buscarxetiqueta("arriendosentregados");
        viviendasreparadas = getSessionBeanCobra().getCobraService().buscarxetiqueta("viviendasreparadas");
        unidadeshabconstruidas= getSessionBeanCobra().getCobraService().buscarxetiqueta("unidadhabconstruidas");
        totalinversionconvenio= getSessionBeanCobra().getCobraService().buscarxetiqueta("convenios");
        obrasaprobadas=getSessionBeanCobra().getCobraService().buscarxetiqueta("aprobadas");
        obrasenejecucion=getSessionBeanCobra().getCobraService().buscarxetiqueta("enejecucion");
        obrasfinalizadas=getSessionBeanCobra().getCobraService().buscarxetiqueta("finalizadas");
        obrassolicitadas=getSessionBeanCobra().getCobraService().buscarxetiqueta("obrassolicitadas");
        totalpagado=getSessionBeanCobra().getCobraService().buscarxetiqueta("totalpagado");

        return null;

    }
}
