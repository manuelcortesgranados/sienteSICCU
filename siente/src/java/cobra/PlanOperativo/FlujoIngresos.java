/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.PlanOperativo;

import co.com.interkont.cobra.to.Contrato;
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
        this.movimientosConvenioEntidadEliminados = new ArrayList<Planificacionmovconvenioentidad>();
        this.fuenteRecursosConvenio = fuenterecursosconvenio;
        this.entidadAportante = entidadAportante;
        this.ingresoEntidad = true;
        
        for (Periodoflujocaja periodoFlujoCaja : periodosFlujoCaja) {
            Planificacionmovconvenioentidad planMovimientoEntidad = new Planificacionmovconvenioentidad();

            planMovimientoEntidad.setPeriodoflujocaja(periodoFlujoCaja);
            planMovimientoEntidad.setFuenterecursosconvenio(fuenterecursosconvenio);
            planMovimientoEntidad.setValor(BigDecimal.ZERO);

            this.planMovimientosConvenioEntidad.add(planMovimientoEntidad);
        }
    }

    public void crearEstructuraFlujoIngresosOtrosItems(Itemflujocaja itemFlujoIngresos, List<Periodoflujocaja> periodosFlujoCaja, Contrato convenio) {
        this.planMovimientosIngresosConvenio = new ArrayList<Planificacionmovconvenio>();
        this.movimientosIngresosConvenioEliminados = new ArrayList<Planificacionmovconvenio>();
        this.itemFlujoIngresos = itemFlujoIngresos;
        this.ingresoEntidad = false;
        
        for (Periodoflujocaja periodoFlujoCaja : periodosFlujoCaja) {
            Planificacionmovconvenio planMovimientoIngreso = new Planificacionmovconvenio();
            
            planMovimientoIngreso.setItemflujocaja(itemFlujoIngresos);
            planMovimientoIngreso.setPeriodoflujocaja(periodoFlujoCaja);
            planMovimientoIngreso.setContrato(convenio);
            planMovimientoIngreso.setValor(BigDecimal.ZERO);

            this.planMovimientosIngresosConvenio.add(planMovimientoIngreso);
        }
    }

    public void actualizarPlanMovimientosEntidad(List<Periodoflujocaja> periodosFlujoCaja, Fuenterecursosconvenio fuenteRecursosConvenio) {
        this.movimientosConvenioEntidadEliminados = new ArrayList<Planificacionmovconvenioentidad>();
        this.fuenteRecursosConvenio = fuenteRecursosConvenio;
        this.ingresoEntidad = true;
        int iterador;

        if (this.planMovimientosConvenioEntidad.size() < periodosFlujoCaja.size()) {
            iterador = this.planMovimientosConvenioEntidad.size();


            while (iterador < periodosFlujoCaja.size()) {
                Planificacionmovconvenioentidad planMovimientoEntidad = new Planificacionmovconvenioentidad();

                planMovimientoEntidad.setFuenterecursosconvenio(fuenteRecursosConvenio);
                planMovimientoEntidad.setPeriodoflujocaja(periodosFlujoCaja.get(iterador));
                planMovimientoEntidad.setValor(BigDecimal.ZERO);

                this.planMovimientosConvenioEntidad.add(planMovimientoEntidad);

                iterador++;
            }
        } else if (this.planMovimientosConvenioEntidad.size() > periodosFlujoCaja.size()) {
            Planificacionmovconvenioentidad planMovimientoEntidad;

            while (periodosFlujoCaja.size() < planMovimientosConvenioEntidad.size()) {
                planMovimientoEntidad = planMovimientosConvenioEntidad.remove(periodosFlujoCaja.size());

                movimientosConvenioEntidadEliminados.add(planMovimientoEntidad);
            }
        }
    }
    
    public void actualizarPlanMovimientosIngresosConvenio(List<Periodoflujocaja> periodosFlujoCaja, Contrato convenio) {
        this.movimientosIngresosConvenioEliminados = new ArrayList<Planificacionmovconvenio>();
        this.ingresoEntidad = false;
        int iterador;

        if (this.planMovimientosIngresosConvenio.size() < periodosFlujoCaja.size()) {
            iterador = this.planMovimientosIngresosConvenio.size();

            while (iterador < periodosFlujoCaja.size()) {
                Planificacionmovconvenio planMovimientoIngreso = new Planificacionmovconvenio();

                planMovimientoIngreso.setItemflujocaja(this.itemFlujoIngresos);
                planMovimientoIngreso.setPeriodoflujocaja(periodosFlujoCaja.get(iterador));
                planMovimientoIngreso.setContrato(convenio);
                planMovimientoIngreso.setValor(BigDecimal.ZERO);

                this.planMovimientosIngresosConvenio.add(planMovimientoIngreso);

                iterador++;
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
        this.totalIngresosFuente = BigDecimal.ZERO;
        double totalIngresos = 0;
        int iterador = 0;

        while (iterador < cantidadPeriodos) {
            if (ingresoEntidad) {
                totalIngresos += planMovimientosConvenioEntidad.get(iterador).getValor().doubleValue();
            } else {
                totalIngresos += planMovimientosIngresosConvenio.get(iterador).getValor().doubleValue();
            }
            
            iterador++;
        }

        this.totalIngresosFuente = BigDecimal.valueOf(totalIngresos);
    }

    public void guardarFlujoIngresosEntidad() {
        
    }
}
