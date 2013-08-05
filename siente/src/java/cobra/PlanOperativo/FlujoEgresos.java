/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.PlanOperativo;

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
        this.fuenteRecursosConvenioObra = fuenteRecursosConvenioObra;
        this.proyecto = proyecto;
        this.egresoProyecto = true;

        for (Periodoflujocaja periodoFlujoCaja : periodosFlujoCaja) {
            Planificacionmovimientoproyecto planMovimientoProyecto = new Planificacionmovimientoproyecto();

            planMovimientoProyecto.setPeriodoflujocaja(periodoFlujoCaja);
            planMovimientoProyecto.setObra(proyecto);
            planMovimientoProyecto.setValor(BigDecimal.valueOf(0.0));

            planMovimientosProyecto.add(planMovimientoProyecto);
        }
    }

    public void crearEstructuraFlujoEgresosOtrosItems(Itemflujocaja itemFlujoEgresos, List<Periodoflujocaja> periodosFlujoCaja) {
        this.planMovimientosEgresosConvenio = new ArrayList<Planificacionmovconvenio>();
        this.itemFlujoEgresos = itemFlujoEgresos;
        this.egresoProyecto = false;

        for (Periodoflujocaja periodoFlujoCaja : periodosFlujoCaja) {
            Planificacionmovconvenio planMovimientoEgreso = new Planificacionmovconvenio();

            planMovimientoEgreso.setPeriodoflujocaja(periodoFlujoCaja);
            planMovimientoEgreso.setItemflujocaja(itemFlujoEgresos);
            planMovimientoEgreso.setValor(BigDecimal.valueOf(0.0));

            planMovimientosEgresosConvenio.add(planMovimientoEgreso);
        }
    }
    
    public void actualizarPlanMovimientosProyecto(List<Periodoflujocaja> periodosFlujoCaja) {
        this.egresoProyecto = true;
        int i = 0;

        if (this.planMovimientosProyecto.size() < periodosFlujoCaja.size()) {
            i = this.planMovimientosProyecto.size();


            while (i < periodosFlujoCaja.size()) {
                Planificacionmovimientoproyecto planMovimientoProyecto = new Planificacionmovimientoproyecto();

                planMovimientoProyecto.setPeriodoflujocaja(periodosFlujoCaja.get(i));
                planMovimientoProyecto.setValor(BigDecimal.valueOf(0.0));

                this.planMovimientosProyecto.add(planMovimientoProyecto);

                i++;
            }
        } else if (this.planMovimientosProyecto.size() > periodosFlujoCaja.size()) {
            Planificacionmovimientoproyecto planMovimientoProyecto;

            while (periodosFlujoCaja.size() < planMovimientosProyecto.size()) {
                planMovimientoProyecto = planMovimientosProyecto.remove(periodosFlujoCaja.size());

                movimientosProyectoEliminados.add(planMovimientoProyecto);
            }
        }
    }
    
    public void actualizarPlanMovimientosEgresosConvenio(List<Periodoflujocaja> periodosFlujoCaja) {
        this.egresoProyecto = false;
        int i = 0;

        if (this.planMovimientosEgresosConvenio.size() < periodosFlujoCaja.size()) {
            i = this.planMovimientosEgresosConvenio.size();


            while (i < periodosFlujoCaja.size()) {
                Planificacionmovconvenio planMovimientoEgresoConvenio = new Planificacionmovconvenio();

                planMovimientoEgresoConvenio.setPeriodoflujocaja(periodosFlujoCaja.get(i));
                planMovimientoEgresoConvenio.setValor(BigDecimal.valueOf(0.0));

                this.planMovimientosEgresosConvenio.add(planMovimientoEgresoConvenio);

                i++;
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
        this.totalEgresosFuente = BigDecimal.valueOf(0.0);
        double totalEgresos = 0;
        int i = 0;

        while (i < cantidadPeriodos) {
            if (egresoProyecto) {
                totalEgresos += planMovimientosProyecto.get(i).getValor().doubleValue();
            } else {
                totalEgresos += planMovimientosEgresosConvenio.get(i).getValor().doubleValue();
            }

            i++;
        }

        this.totalEgresosFuente = BigDecimal.valueOf(totalEgresos);
    }

    public void guardarFlujoEgresosProyecto() {
    }
}
