/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.PlanOperativo;

import co.com.interkont.cobra.to.Contrato;
import co.com.interkont.cobra.to.Itemflujocaja;
import co.com.interkont.cobra.to.Obra;
import co.com.interkont.cobra.to.Obrafuenterecursosconvenios;
import co.com.interkont.cobra.to.Periodoflujocaja;
import co.com.interkont.cobra.to.Planificacionmovconvenio;
import co.com.interkont.cobra.to.Planificacionmovimientoproyecto;
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
    List<Planificacionmovimientoproyecto> planMovimientosProyecto;
    List<Planificacionmovconvenio> planMovimientosEgresosConvenio;
    List<Planificacionmovimientoproyecto> movimientosProyectoEliminados;
    List<Planificacionmovconvenio> movimientosEgresosConvenioEliminados;
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

    public List<Planificacionmovimientoproyecto> getPlanMovimientosProyecto() {
        return planMovimientosProyecto;
    }

    public void setPlanMovimientosProyecto(List<Planificacionmovimientoproyecto> planMovimientosProyecto) {
        this.planMovimientosProyecto = planMovimientosProyecto;
    }

    public List<Planificacionmovconvenio> getPlanMovimientosEgresosConvenio() {
        return planMovimientosEgresosConvenio;
    }

    public void setPlanMovimientosEgresosConvenio(List<Planificacionmovconvenio> planMovimientosEgresosConvenio) {
        this.planMovimientosEgresosConvenio = planMovimientosEgresosConvenio;
    }

    public List<Planificacionmovimientoproyecto> getMovimientosProyectoEliminados() {
        return movimientosProyectoEliminados;
    }

    public void setMovimientosProyectoEliminados(List<Planificacionmovimientoproyecto> movimientosProyectoEliminados) {
        this.movimientosProyectoEliminados = movimientosProyectoEliminados;
    }

    public List<Planificacionmovconvenio> getMovimientosEgresosConvenioEliminados() {
        return movimientosEgresosConvenioEliminados;
    }

    public void setMovimientosEgresosConvenioEliminados(List<Planificacionmovconvenio> movimientosEgresosConvenioEliminados) {
        this.movimientosEgresosConvenioEliminados = movimientosEgresosConvenioEliminados;
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
            return itemFlujoEgresos.getStrdescripcion();
        }

        return proyecto.getStrnombreobra();
    }

    public void crearEstructuraFlujoEgresosProyecto(Obrafuenterecursosconvenios fuenteRecursosConvenioObra, Obra proyecto, List<Periodoflujocaja> periodosFlujoCaja) {
        this.planMovimientosProyecto = new ArrayList<Planificacionmovimientoproyecto>();
        this.movimientosProyectoEliminados = new ArrayList<Planificacionmovimientoproyecto>();
        this.fuenteRecursosConvenioObra = fuenteRecursosConvenioObra;
        this.proyecto = proyecto;
        this.egresoProyecto = true;

        for (Periodoflujocaja periodoFlujoCaja : periodosFlujoCaja) {
            Planificacionmovimientoproyecto planMovimientoProyecto = new Planificacionmovimientoproyecto();

            planMovimientoProyecto.setPeriodoflujocaja(periodoFlujoCaja);
            planMovimientoProyecto.setObra(this.proyecto);
            planMovimientoProyecto.setValor(BigDecimal.ZERO);

            planMovimientosProyecto.add(planMovimientoProyecto);
        }
    }

    public void crearEstructuraFlujoEgresosOtrosItems(Itemflujocaja itemFlujoEgresos, List<Periodoflujocaja> periodosFlujoCaja, Contrato convenio) {
        this.planMovimientosEgresosConvenio = new ArrayList<Planificacionmovconvenio>();
        this.movimientosEgresosConvenioEliminados = new ArrayList<Planificacionmovconvenio>();
        this.itemFlujoEgresos = itemFlujoEgresos;
        this.egresoProyecto = false;

        for (Periodoflujocaja periodoFlujoCaja : periodosFlujoCaja) {
            Planificacionmovconvenio planMovimientoEgreso = new Planificacionmovconvenio();

            planMovimientoEgreso.setPeriodoflujocaja(periodoFlujoCaja);
            planMovimientoEgreso.setItemflujocaja(itemFlujoEgresos);
            planMovimientoEgreso.setContrato(convenio);
            planMovimientoEgreso.setValor(BigDecimal.ZERO);

            planMovimientosEgresosConvenio.add(planMovimientoEgreso);
        }
    }
    
    public void actualizarPlanMovimientosProyecto(List<Periodoflujocaja> periodosFlujoCaja) {
        this.movimientosProyectoEliminados = new ArrayList<Planificacionmovimientoproyecto>();
        this.egresoProyecto = true;
        int iterador;

        if (this.planMovimientosProyecto.size() < periodosFlujoCaja.size()) {
            iterador = this.planMovimientosProyecto.size();


            while (iterador < periodosFlujoCaja.size()) {
                Planificacionmovimientoproyecto planMovimientoProyecto = new Planificacionmovimientoproyecto();

                planMovimientoProyecto.setPeriodoflujocaja(periodosFlujoCaja.get(iterador));
                planMovimientoProyecto.setObra(this.proyecto);
                planMovimientoProyecto.setValor(BigDecimal.ZERO);

                this.planMovimientosProyecto.add(planMovimientoProyecto);

                iterador++;
            }
        } else if (this.planMovimientosProyecto.size() > periodosFlujoCaja.size()) {
            Planificacionmovimientoproyecto planMovimientoProyecto;

            while (periodosFlujoCaja.size() < planMovimientosProyecto.size()) {
                planMovimientoProyecto = planMovimientosProyecto.remove(periodosFlujoCaja.size());

                movimientosProyectoEliminados.add(planMovimientoProyecto);
            }
        }
    }
    
    public void actualizarPlanMovimientosEgresosConvenio(List<Periodoflujocaja> periodosFlujoCaja, Contrato convenio) {
        this.movimientosEgresosConvenioEliminados = new ArrayList<Planificacionmovconvenio>();
        this.egresoProyecto = false;
        int iterador;

        if (this.planMovimientosEgresosConvenio.size() < periodosFlujoCaja.size()) {
            iterador = this.planMovimientosEgresosConvenio.size();


            while (iterador < periodosFlujoCaja.size()) {
                Planificacionmovconvenio planMovimientoEgresoConvenio = new Planificacionmovconvenio();

                planMovimientoEgresoConvenio.setItemflujocaja(this.itemFlujoEgresos);
                planMovimientoEgresoConvenio.setPeriodoflujocaja(periodosFlujoCaja.get(iterador));
                planMovimientoEgresoConvenio.setContrato(convenio);
                planMovimientoEgresoConvenio.setValor(BigDecimal.ZERO);

                this.planMovimientosEgresosConvenio.add(planMovimientoEgresoConvenio);

                iterador++;
            }
        } else if (this.planMovimientosEgresosConvenio.size() > periodosFlujoCaja.size()) {
            Planificacionmovconvenio planMovimientoEgresoConvenio;

            while (periodosFlujoCaja.size() < planMovimientosEgresosConvenio.size()) {
                planMovimientoEgresoConvenio = planMovimientosEgresosConvenio.remove(periodosFlujoCaja.size());

                movimientosEgresosConvenioEliminados.add(planMovimientoEgresoConvenio);
            }
        }
    }

    public void calcularTotalEgresosFuente(int cantidadPeriodos) {
        this.totalEgresosFuente = BigDecimal.ZERO;
        double totalEgresos = 0;
        int iterador = 0;

        while (iterador < cantidadPeriodos) {
            if (egresoProyecto) {
                totalEgresos += planMovimientosProyecto.get(iterador).getValor().doubleValue();
            } else {
                totalEgresos += planMovimientosEgresosConvenio.get(iterador).getValor().doubleValue();
            }

            iterador++;
        }

        this.totalEgresosFuente = BigDecimal.valueOf(totalEgresos);
    }

    public void guardarFlujoEgresosProyecto() {
    }
}
