/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.PlanOperativo;

import co.com.interkont.cobra.to.Itemflujocaja;
import co.com.interkont.cobra.to.Obra;
import co.com.interkont.cobra.to.Obrafuenterecursosconvenios;
import co.com.interkont.cobra.to.Periodoflujocaja;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author desarrollo2
 */
public class FlujoEgresos {

    Obrafuenterecursosconvenios fuenteRecursosConvenioObra;
    Obra proyecto;
    Itemflujocaja itemFlujoEgresos;
    boolean egresoProyecto;
    BigDecimal egresos[];
    BigDecimal totalEgresosFuente;
    double contrapartida;

    public FlujoEgresos() {
    }

    public Obrafuenterecursosconvenios getFuenteRecursosConvenioObra() {
        return fuenteRecursosConvenioObra;
    }

    public void setFuenteRecursosConvenioObra(Obrafuenterecursosconvenios fuenteRecursosConvenioObra) {
        this.fuenteRecursosConvenioObra = fuenteRecursosConvenioObra;
    }

    public Obra getProyecto() {
        return proyecto;
    }

    public void setProyecto(Obra proyecto) {
        this.proyecto = proyecto;
    }

    public Itemflujocaja getItemFlujoEgresos() {
        return itemFlujoEgresos;
    }

    public void setItemFlujoEgresos(Itemflujocaja itemFlujoEgresos) {
        this.itemFlujoEgresos = itemFlujoEgresos;
    }

    public boolean isEgresoProyecto() {
        return egresoProyecto;
    }

    public void setEgresoProyecto(boolean egresoProyecto) {
        this.egresoProyecto = egresoProyecto;
    }

    public BigDecimal[] getEgresos() {
        return egresos;
    }

    public void setEgresos(BigDecimal[] egresos) {
        this.egresos = egresos;
    }

    public BigDecimal getTotalEgresosFuente() {
        return totalEgresosFuente;
    }

    public void setTotalEgresosFuente(BigDecimal totalEgresosFuente) {
        this.totalEgresosFuente = totalEgresosFuente;
    }

    public double getContrapartida() {
        return contrapartida;
    }

    public void setContrapartida(double contrapartida) {
        this.contrapartida = contrapartida;
    }
    
    public String getDescripcionFuenteEgreso() {
        if (!egresoProyecto) {
            return itemFlujoEgresos.getStrdescriptcion();
        }
        
        return proyecto.getStrnombreobra();
    }

    public void crearEstructuraFlujoEgresosProyecto(Obrafuenterecursosconvenios fuenteRecursosConvenioObra, Obra proyecto, List<Periodoflujocaja> periodosFlujoCaja) {
        this.fuenteRecursosConvenioObra = fuenteRecursosConvenioObra;
        this.proyecto = proyecto;
        this.egresoProyecto = true;
        this.egresos = new BigDecimal[periodosFlujoCaja.size()];
        int i = 0;
        
        while (i < periodosFlujoCaja.size()) {
            egresos[i] = BigDecimal.valueOf(0.0);
            i++;
        }
    }
    
    public void calcularTotalEgresosFuente() {
        this.totalEgresosFuente = BigDecimal.valueOf(0.0);
        double totalEgresos = 0;
        int i = 0;

        while (i < egresos.length) {
            totalEgresos += egresos[i].doubleValue();

            i++;
        }

        this.totalEgresosFuente = BigDecimal.valueOf(totalEgresos);
    }
    
    public void guardarFlujoEgresosProyecto() {
    }
}
