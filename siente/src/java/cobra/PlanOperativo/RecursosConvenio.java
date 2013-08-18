/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.PlanOperativo;

import co.com.interkont.cobra.to.Contrato;
import co.com.interkont.cobra.to.Fuenterecursosconvenio;
import co.com.interkont.cobra.to.Rolentidad;
import co.com.interkont.cobra.to.Tercero;
import cobra.Supervisor.FacesUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.model.SelectItem;

/**
 *
 * @author  Carlos Loaiza
 */
public class RecursosConvenio implements Serializable{
    private Fuenterecursosconvenio fuenteRecursoConvenio;
    private boolean boolguardofuente;
    private List<Fuenterecursosconvenio> lstFuentesRecursos;
    private SelectItem[] tipoAporte;
    private BigDecimal sumafuentes;
    

    public RecursosConvenio(Contrato contrato) {
        fuenteRecursoConvenio = new Fuenterecursosconvenio(new Tercero(), contrato, new Rolentidad());
        lstFuentesRecursos = new ArrayList<Fuenterecursosconvenio>();
        sumafuentes= BigDecimal.ZERO;
        llenarTipoAporte();
    }
    
     public void limpiarFuenteRecurso() {
        fuenteRecursoConvenio= new Fuenterecursosconvenio();        
        fuenteRecursoConvenio.setRolentidad(new Rolentidad());
        fuenteRecursoConvenio.setTercero(new Tercero());

    }
    
      /**
     * @return the tipoAporte
     */
    public SelectItem[] getTipoAporte() {
        return tipoAporte;
    }

    /**
     * @param tipoAporte the tipoAporte to set
     */
    public void setTipoAporte(SelectItem[] tipoAporte) {
        this.tipoAporte = tipoAporte;
    }

    /**
     * @return the boolguardofuente
     */
    public boolean isBoolguardofuente() {
        System.out.println("this = " + boolguardofuente);
        return boolguardofuente;
    }

    /**
     * @param boolguardofuente the boolguardofuente to set
     */
    public void setBoolguardofuente(boolean boolguardofuente) {
        this.boolguardofuente = boolguardofuente;
    }

    /**
     * @return the lstFuentesRecursos
     */
    public List<Fuenterecursosconvenio> getLstFuentesRecursos() {
        return lstFuentesRecursos;
    }

    /**
     * @param lstFuentesRecursos the lstFuentesRecursos to set
     */
    public void setLstFuentesRecursos(List<Fuenterecursosconvenio> lstFuentesRecursos) {
        this.lstFuentesRecursos = lstFuentesRecursos;
    }

    /**
     * @return the fuenteRecursoConvenio
     */
    public Fuenterecursosconvenio getFuenteRecursoConvenio() {
        return fuenteRecursoConvenio;
    }

    /**
     * @param fuenteRecursoConvenio the fuenteRecursoConvenio to set
     */
    public void setFuenteRecursoConvenio(Fuenterecursosconvenio fuenteRecursoConvenio) {
        this.fuenteRecursoConvenio = fuenteRecursoConvenio;
    }
    
    /*
     *metodo que se encarga de eliminar una fuente de recurso a las
     * fuente de recursos del convenio.
     *      
     */
    public void eliminarFuenteRecursos(int filaFuenteRecursoEliminar) {        
        
        lstFuentesRecursos.remove(filaFuenteRecursoEliminar);
    }
    
     /*
     *metodo que se encarga de adicionar una fuente de recurso a la
     * fuente de recursos del convenio.
     *      
     */
    public void adicionarFuenteRecursos(Tercero tercero) {
        fuenteRecursoConvenio.setTercero(tercero);
        //contrato.getFuenterecursosconvenios().add(getFuenteRecursoConvenio().clone());
        lstFuentesRecursos.add((Fuenterecursosconvenio) getFuenteRecursoConvenio().clone());
        sumafuentes=sumafuentes.add(fuenteRecursoConvenio.getValoraportado());
        limpiarFuenteRecurso();
        
    }
    
     public void calcularValorGerencia(ResourceBundle bundle) {
        getFuenteRecursoConvenio().setStrporcentajecuotagerencia("");

        switch (getFuenteRecursoConvenio().getTipoaporte()) {
            case 1://porcentual
                try {
                if (getFuenteRecursoConvenio().getValorcuotagerencia().doubleValue() < 100) {
                    getFuenteRecursoConvenio().setPorcentajecuotagerencia(
                            getFuenteRecursoConvenio().getValoraportado().doubleValue() * getFuenteRecursoConvenio().getValorcuotagerencia().doubleValue() / 100);
                    getFuenteRecursoConvenio().setStrporcentajecuotagerencia("$ " + getFuenteRecursoConvenio().getPorcentajecuotagerencia());
                } else {
                    FacesUtils.addErrorMessage(bundle.getString("validarporcentajefuente"));
                }
            } catch (ArithmeticException a) {
                getFuenteRecursoConvenio().setStrporcentajecuotagerencia("$ 0.0000");
            }
                break;
            case 2://Valor
                try {
                if (getFuenteRecursoConvenio().getValorcuotagerencia().doubleValue() < getFuenteRecursoConvenio().getValoraportado().doubleValue()) {
                    getFuenteRecursoConvenio().setPorcentajecuotagerencia(getFuenteRecursoConvenio().getValorcuotagerencia().doubleValue() / getFuenteRecursoConvenio().getValoraportado().doubleValue() * 100);
                    getFuenteRecursoConvenio().setStrporcentajecuotagerencia(getFuenteRecursoConvenio().getPorcentajecuotagerencia() + " %");
                } else {
                    FacesUtils.addErrorMessage(bundle.getString("validarvalorfuente"));
                }
            } catch (ArithmeticException a) {
                getFuenteRecursoConvenio().setStrporcentajecuotagerencia("0.0000 %");
            }
                break;
        }

    }
     
     /*
     *metodo que  carga los tipos de aportes en la lista de seleccion
     *      
     */
    public void llenarTipoAporte() {
        setTipoAporte(new SelectItem[]{new SelectItem(1, "Porcentual"), new SelectItem(2, "Valor")});
    }

    public BigDecimal getSumafuentes() {
        return sumafuentes;
    }

    public void setSumafuentes(BigDecimal sumafuentes) {
        this.sumafuentes = sumafuentes;
    }   
    
    
}
