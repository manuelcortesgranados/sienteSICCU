/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.PlanOperativo;

import co.com.interkont.cobra.to.Periodoflujocaja;
import java.math.BigDecimal;

/**
 *
 * @author desarrollo2
 */
public class IngresoPeriodo {
    Periodoflujocaja periodoFlujoCaja;
    BigDecimal valorIngreso;

    public IngresoPeriodo() {
    }

    public IngresoPeriodo(Periodoflujocaja periodoFlujoCaja) {
        this.periodoFlujoCaja = periodoFlujoCaja;
    }

    public Periodoflujocaja getPeriodoFlujoCaja() {
        return periodoFlujoCaja;
    }

    public void setPeriodoFlujoCaja(Periodoflujocaja periodoFlujoCaja) {
        this.periodoFlujoCaja = periodoFlujoCaja;
    }

    public BigDecimal getValorIngreso() {
        return valorIngreso;
    }

    public void setValorIngreso(BigDecimal valorIngreso) {
        this.valorIngreso = valorIngreso;
    }
}
