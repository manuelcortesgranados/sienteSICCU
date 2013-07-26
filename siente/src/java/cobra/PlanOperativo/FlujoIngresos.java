/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.PlanOperativo;

import co.com.interkont.cobra.to.Fuenterecursosconvenio;
import co.com.interkont.cobra.to.Periodoflujocaja;
import co.com.interkont.cobra.to.Planificacionmovconvenioentidad;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author desarrollo2
 */
public class FlujoIngresos {
    Fuenterecursosconvenio fuenteRecursosConvenio;
    List<IngresoPeriodo> ingresosPeriodo;
    BigDecimal totalIngresosFuente;

    public FlujoIngresos() {
    }

    public Fuenterecursosconvenio getFuenteRecursosConvenio() {
        return fuenteRecursosConvenio;
    }

    public void setFuenteRecursosConvenio(Fuenterecursosconvenio fuenteRecursosConvenio) {
        this.fuenteRecursosConvenio = fuenteRecursosConvenio;
    }

    public List<IngresoPeriodo> getIngresosPeriodo() {
        return ingresosPeriodo;
    }

    public void setIngresosPeriodo(List<IngresoPeriodo> ingresosPeriodo) {
        this.ingresosPeriodo = ingresosPeriodo;
    }

    public BigDecimal getTotalIngresosFuente() {
        return totalIngresosFuente;
    }

    public void setTotalIngresosFuente(BigDecimal totalIngresosFuente) {
        this.totalIngresosFuente = totalIngresosFuente;
    }
    
    public void crearEstructuraFlujoIngresosEntidad(Fuenterecursosconvenio fuenterecursosconvenio, List<Periodoflujocaja> periodosFlujoCaja) {
        this.fuenteRecursosConvenio = fuenterecursosconvenio;
        
        for (Periodoflujocaja periodoFlujoCaja : periodosFlujoCaja) {
            IngresoPeriodo ingresoPeriodo = new IngresoPeriodo();
            ingresoPeriodo.setPeriodoFlujoCaja(periodoFlujoCaja);
            
            ingresosPeriodo.add(ingresoPeriodo);
        }
    }
    
    public void guardarFlujoIngresosEntidad() {
        for (IngresoPeriodo ingresoPeriodo : ingresosPeriodo) {
            Planificacionmovconvenioentidad planMovimientoConvenioEntidad = new Planificacionmovconvenioentidad();
            
            planMovimientoConvenioEntidad.setFuenterecursosconvenio(fuenteRecursosConvenio);
            planMovimientoConvenioEntidad.setPeriodoflujocaja(ingresoPeriodo.getPeriodoFlujoCaja());
            planMovimientoConvenioEntidad.setValor(ingresoPeriodo.getValorIngreso());
            
            
        }
    }
}
