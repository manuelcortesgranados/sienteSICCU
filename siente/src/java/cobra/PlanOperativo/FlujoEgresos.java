/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.PlanOperativo;

import co.com.interkont.cobra.to.Obrafuenterecursosconvenios;
import co.com.interkont.cobra.to.Periodoflujocaja;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author desarrollo2
 */
public class FlujoEgresos {
    Obrafuenterecursosconvenios fuenteRecursosConvenioObra;
    List<EgresoPeriodo> egresosPeriodo;
    BigDecimal sumatoriaEgresosPeriodos;

    public FlujoEgresos() {
    }

    public Obrafuenterecursosconvenios getFuenteRecursosConvenioObra() {
        return fuenteRecursosConvenioObra;
    }

    public void setFuenteRecursosConvenioObra(Obrafuenterecursosconvenios fuenteRecursosConvenioObra) {
        this.fuenteRecursosConvenioObra = fuenteRecursosConvenioObra;
    }

    public List<EgresoPeriodo> getEgresosPeriodo() {
        return egresosPeriodo;
    }

    public void setEgresosPeriodo(List<EgresoPeriodo> egresosPeriodo) {
        this.egresosPeriodo = egresosPeriodo;
    }

    public BigDecimal getSumatoriaEgresosPeriodos() {
        return sumatoriaEgresosPeriodos;
    }

    public void setSumatoriaEgresosPeriodos(BigDecimal sumatoriaEgresosPeriodos) {
        this.sumatoriaEgresosPeriodos = sumatoriaEgresosPeriodos;
    }
    
    public void crearFlujoEgresosProyecto(Obrafuenterecursosconvenios fuenteRecursosConvenioObra, List<Periodoflujocaja> periodosFlujoCaja) {
        this.fuenteRecursosConvenioObra = fuenteRecursosConvenioObra;
        
         for (Periodoflujocaja periodoFlujoCaja : periodosFlujoCaja) {
             EgresoPeriodo egresoPeriodo = new EgresoPeriodo();
             egresoPeriodo.setPeriodoFlujoCaja(periodoFlujoCaja);
             
             egresosPeriodo.add(egresoPeriodo);
         }
    }
}
