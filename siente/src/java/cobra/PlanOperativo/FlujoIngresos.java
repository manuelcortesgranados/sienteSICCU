/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.PlanOperativo;

import co.com.interkont.cobra.to.Fuenterecursosconvenio;
import co.com.interkont.cobra.to.Itemflujocaja;
import co.com.interkont.cobra.to.Periodoflujocaja;
import co.com.interkont.cobra.to.Planificacionmovconvenio;
import co.com.interkont.cobra.to.Planificacionmovconvenioentidad;
import co.com.interkont.cobra.to.Tercero;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author desarrollo2
 */
public class FlujoIngresos {

    Fuenterecursosconvenio fuenteRecursosConvenio;
    Tercero entidadAportante;
    Itemflujocaja itemFlujoIngresos;
    boolean ingresoEntidad;
    List<Planificacionmovconvenioentidad> planMovimientosConvenioEntidad;
    List<Planificacionmovconvenio> planMovimientosIngresosConvenio;
    List<Planificacionmovconvenioentidad> movimientosConvenioEntidadEliminados;
    List<Planificacionmovconvenio> movimientosIngresosConvenioEliminados;
    BigDecimal totalIngresosFuente;

    public FlujoIngresos() {
    }

    public Fuenterecursosconvenio getFuenteRecursosConvenio() {
        return fuenteRecursosConvenio;
    }

    public void setFuenteRecursosConvenio(Fuenterecursosconvenio fuenteRecursosConvenio) {
        this.fuenteRecursosConvenio = fuenteRecursosConvenio;
    }

    public Tercero getEntidadAportante() {
        return entidadAportante;
    }

    public void setEntidadAportante(Tercero entidadAportante) {
        this.entidadAportante = entidadAportante;
    }

    public Itemflujocaja getItemFlujoIngresos() {
        return itemFlujoIngresos;
    }

    public void setItemFlujoIngresos(Itemflujocaja itemFlujoIngresos) {
        this.itemFlujoIngresos = itemFlujoIngresos;
    }

    public boolean isIngresoEntidad() {
        return ingresoEntidad;
    }

    public List<Planificacionmovconvenioentidad> getPlanMovimientosConvenioEntidad() {
        return planMovimientosConvenioEntidad;
    }

    public void setPlanMovimientosConvenioEntidad(List<Planificacionmovconvenioentidad> planMovimientosConvenioEntidad) {
        this.planMovimientosConvenioEntidad = planMovimientosConvenioEntidad;
    }

    public List<Planificacionmovconvenio> getPlanMovimientosIngresosConvenio() {
        return planMovimientosIngresosConvenio;
    }

    public void setPlanMovimientosIngresosConvenio(List<Planificacionmovconvenio> planMovimientosIngresosConvenio) {
        this.planMovimientosIngresosConvenio = planMovimientosIngresosConvenio;
    }

    public void setIngresoEntidad(boolean ingresoEntidad) {
        this.ingresoEntidad = ingresoEntidad;
    }

    public BigDecimal getTotalIngresosFuente() {
        return totalIngresosFuente;
    }

    public void setTotalIngresosFuente(BigDecimal totalIngresosFuente) {
        this.totalIngresosFuente = totalIngresosFuente;
    }

    public String getDescripcionFuenteIngreso() {
        if (!ingresoEntidad) {
            return itemFlujoIngresos.getStrdescripcion();
        }

        return entidadAportante.getStrnombrecompleto();
    }

    public void crearEstructuraFlujoIngresosEntidad(Fuenterecursosconvenio fuenterecursosconvenio, Tercero entidadAportante, List<Periodoflujocaja> periodosFlujoCaja) {
        this.planMovimientosConvenioEntidad = new ArrayList<Planificacionmovconvenioentidad>();
        this.fuenteRecursosConvenio = fuenterecursosconvenio;
        this.entidadAportante = entidadAportante;
        this.ingresoEntidad = true;
        
        for (Periodoflujocaja periodoFlujoCaja : periodosFlujoCaja) {
            Planificacionmovconvenioentidad planMovimientoEntidad = new Planificacionmovconvenioentidad();

            planMovimientoEntidad.setPeriodoflujocaja(periodoFlujoCaja);
            planMovimientoEntidad.setFuenterecursosconvenio(fuenterecursosconvenio);
            planMovimientoEntidad.setValor(BigDecimal.valueOf(0.0));

            this.planMovimientosConvenioEntidad.add(planMovimientoEntidad);
        }
    }

    public void crearEstructuraFlujoIngresosOtrosItems(Itemflujocaja itemFlujoIngresos, List<Periodoflujocaja> periodosFlujoCaja) {
        this.planMovimientosIngresosConvenio = new ArrayList<Planificacionmovconvenio>();
        this.itemFlujoIngresos = itemFlujoIngresos;
        this.ingresoEntidad = false;
        
        for (Periodoflujocaja periodoFlujoCaja : periodosFlujoCaja) {
            Planificacionmovconvenio planMovimientoIngreso = new Planificacionmovconvenio();
            
            planMovimientoIngreso.setPeriodoflujocaja(periodoFlujoCaja);
            planMovimientoIngreso.setItemflujocaja(itemFlujoIngresos);
            planMovimientoIngreso.setValor(BigDecimal.valueOf(0.0));

            this.planMovimientosIngresosConvenio.add(planMovimientoIngreso);
        }
    }

    public void actualizarPlanMovimientosEntidad(List<Periodoflujocaja> periodosFlujoCaja) {
        this.ingresoEntidad = true;
        int i = 0;

        if (this.planMovimientosConvenioEntidad.size() < periodosFlujoCaja.size()) {
            i = this.planMovimientosConvenioEntidad.size();


            while (i < periodosFlujoCaja.size()) {
                Planificacionmovconvenioentidad planMovimientoEntidad = new Planificacionmovconvenioentidad();

                planMovimientoEntidad.setPeriodoflujocaja(periodosFlujoCaja.get(i));
                planMovimientoEntidad.setValor(BigDecimal.valueOf(0.0));

                this.planMovimientosConvenioEntidad.add(planMovimientoEntidad);

                i++;
            }
        } else if (this.planMovimientosConvenioEntidad.size() > periodosFlujoCaja.size()) {
            Planificacionmovconvenioentidad planMovimientoEntidad;

            while (periodosFlujoCaja.size() < planMovimientosConvenioEntidad.size()) {
                planMovimientoEntidad = planMovimientosConvenioEntidad.remove(periodosFlujoCaja.size());

                movimientosConvenioEntidadEliminados.add(planMovimientoEntidad);
            }
        }
    }
    
    public void actualizarPlanMovimientosIngresosConvenio(List<Periodoflujocaja> periodosFlujoCaja) {
        this.ingresoEntidad = false;
        int i = 0;

        if (this.planMovimientosIngresosConvenio.size() < periodosFlujoCaja.size()) {
            i = this.planMovimientosIngresosConvenio.size();

            while (i < periodosFlujoCaja.size()) {
                Planificacionmovconvenio planMovimientoIngreso = new Planificacionmovconvenio();

                planMovimientoIngreso.setPeriodoflujocaja(periodosFlujoCaja.get(i));
                planMovimientoIngreso.setValor(BigDecimal.valueOf(0.0));

                this.planMovimientosIngresosConvenio.add(planMovimientoIngreso);

                i++;
            }
        } else if (this.planMovimientosIngresosConvenio.size() > periodosFlujoCaja.size()) {
            Planificacionmovconvenio planMovimientoIngreso;

            while (periodosFlujoCaja.size() < planMovimientosIngresosConvenio.size()) {
                planMovimientoIngreso = planMovimientosIngresosConvenio.remove(periodosFlujoCaja.size());

                movimientosIngresosConvenioEliminados.add(planMovimientoIngreso);
            }
        }
    }

    public void calcularTotalIngresosFuente(int cantidadPeriodos) {
        this.totalIngresosFuente = BigDecimal.valueOf(0.0);
        double totalIngresos = 0;
        int i = 0;

        while (i < cantidadPeriodos) {
            if (ingresoEntidad) {
                totalIngresos += planMovimientosConvenioEntidad.get(i).getValor().doubleValue();
            } else {
                totalIngresos += planMovimientosIngresosConvenio.get(i).getValor().doubleValue();
            }
            
            i++;
        }

        this.totalIngresosFuente = BigDecimal.valueOf(totalIngresos);
    }

    public void guardarFlujoIngresosEntidad() {
    }
}
