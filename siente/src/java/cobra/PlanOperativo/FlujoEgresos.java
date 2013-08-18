package cobra.PlanOperativo;

import co.com.interkont.cobra.to.Contrato;
import co.com.interkont.cobra.to.Itemflujocaja;
import co.com.interkont.cobra.to.Obra;
import co.com.interkont.cobra.to.Obrafuenterecursosconvenios;
import co.com.interkont.cobra.to.Periodoflujocaja;
import co.com.interkont.cobra.to.Planificacionmovconvenio;
import co.com.interkont.cobra.to.Planificacionmovimientoproyecto;
import co.com.interkont.cobra.to.Relacioncontratoperiodoflujocaja;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Determina el flujo de egresos del flujo de caja.
 *
 * Incluye proyectos relacionados con los convenios o los items de naturaleza
 * egreso. Cada objeto de esta clase representa un registro de egreso con sus
 * distribución en los periodos del flujo de caja, que determinan la
 * planificación de los egresos.
 *
 * @author Yeison Osorio
 */
public class FlujoEgresos {

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

    /**
     * Descripción de la fuente de egreso.
     *
     * Condicional que devuelve el nombre de la fuente, ya sea del proyecto o
     * del item de naturaleza egreso ('E').
     *
     * @return Nombre de la fuente de egresos.
     */
    public String getDescripcionFuenteEgreso() {
        if (!egresoProyecto) {
            return itemFlujoEgresos.getStrdescripcion();
        }

        return proyecto.getStrnombreobra();
    }

    /**
     * Crear la estructura del flujo de ingresos por entidad.
     *
     * Al llamar este método y referenciar los parámetros, se crea un flujo de
     * egresos en los periodos del flujo de caja para el proyecto asociado. Este
     * método inicializa el flujo de egresos del proyecto al no existir alguno.
     *
     * @param proyecto Proyecto asociado al convenio marco.
     * @param periodosFlujoCaja Periodos del flujo de caja.
     */
    public void crearEstructuraFlujoEgresosProyecto(Obra proyecto, List<Relacioncontratoperiodoflujocaja> periodosConvenio) {
        this.planMovimientosProyecto = new ArrayList<Planificacionmovimientoproyecto>();
        this.movimientosProyectoEliminados = new ArrayList<Planificacionmovimientoproyecto>();
        this.proyecto = proyecto;
        this.egresoProyecto = true;

        for (Relacioncontratoperiodoflujocaja periodoConvenio : periodosConvenio) {
            Planificacionmovimientoproyecto planMovimientoProyecto = new Planificacionmovimientoproyecto();

            planMovimientoProyecto.setPeriodoflujocaja(periodoConvenio.getPeriodoflujocaja());
            planMovimientoProyecto.setObra(this.proyecto);
            planMovimientoProyecto.setValor(BigDecimal.ZERO);

            planMovimientosProyecto.add(planMovimientoProyecto);
        }
    }

    /**
     * Crear la estructura del flujo de ingresos por entidad.
     *
     * Al llamar este método y referenciar los parámetros, se crea un flujo de
     * egresos en los periodos del flujo de caja para el item definido. Este
     * método inicializa el flujo de egresos del item al no existir alguno.
     *
     * @param itemFlujoEgresos Item del flujo de caja de naturaleza egreso.
     * @param periodosFlujoCaja Periodos del flujo de caja.
     * @param convenio Convenio marco.
     */
    public void crearEstructuraFlujoEgresosOtrosItems(Itemflujocaja itemFlujoEgresos, List<Relacioncontratoperiodoflujocaja> periodosConvenio, Contrato convenio) {
        this.planMovimientosEgresosConvenio = new ArrayList<Planificacionmovconvenio>();
        this.movimientosEgresosConvenioEliminados = new ArrayList<Planificacionmovconvenio>();
        this.itemFlujoEgresos = itemFlujoEgresos;
        this.egresoProyecto = false;

        for (Relacioncontratoperiodoflujocaja periodoConvenio : periodosConvenio) {
            Planificacionmovconvenio planMovimientoEgreso = new Planificacionmovconvenio();

            planMovimientoEgreso.setPeriodoflujocaja(periodoConvenio.getPeriodoflujocaja());
            planMovimientoEgreso.setItemflujocaja(itemFlujoEgresos);
            planMovimientoEgreso.setContrato(convenio);
            planMovimientoEgreso.setValor(BigDecimal.ZERO);

            planMovimientosEgresosConvenio.add(planMovimientoEgreso);
        }
    }

    /**
     * Actualizar el flujo de egresos del proyecto.
     *
     * Con base en una lista de egresos consultada, se actualiza el flujo de
     * egresos del proyecto.
     *
     * @param periodosFlujoCaja Periodos del flujo de caja.
     */
    public void actualizarPlanMovimientosProyecto(List<Relacioncontratoperiodoflujocaja> periodosConvenio) {
        this.movimientosProyectoEliminados = new ArrayList<Planificacionmovimientoproyecto>();
        this.egresoProyecto = true;
        int iterador;

        if (this.planMovimientosProyecto.size() < periodosConvenio.size()) {
            iterador = this.planMovimientosProyecto.size();


            while (iterador < periodosConvenio.size()) {
                Planificacionmovimientoproyecto planMovimientoProyecto = new Planificacionmovimientoproyecto();

                planMovimientoProyecto.setPeriodoflujocaja(periodosConvenio.get(iterador).getPeriodoflujocaja());
                planMovimientoProyecto.setObra(this.proyecto);
                planMovimientoProyecto.setValor(BigDecimal.ZERO);

                this.planMovimientosProyecto.add(planMovimientoProyecto);

                iterador++;
            }
        } else if (this.planMovimientosProyecto.size() > periodosConvenio.size()) {
            Planificacionmovimientoproyecto planMovimientoProyecto;

            while (periodosConvenio.size() < planMovimientosProyecto.size()) {
                planMovimientoProyecto = planMovimientosProyecto.remove(periodosConvenio.size());

                if (planMovimientoProyecto.getIdplanificacionmovpry() > 0) {
                    movimientosProyectoEliminados.add(planMovimientoProyecto);
                }
            }
        }
    }

    /**
     * Actualizar el flujo de egresos por item de egreso.
     *
     * Con base en una lista de egresos consultados se actualiza el flujo de
     * egresos por item de egreso.
     *
     * @param periodosFlujoCaja Periodos del flujo de caja.
     * @param convenio Convenio marco.
     */
    public void actualizarPlanMovimientosEgresosConvenio(List<Relacioncontratoperiodoflujocaja> periodosConvenio, Contrato convenio) {
        this.movimientosEgresosConvenioEliminados = new ArrayList<Planificacionmovconvenio>();
        this.egresoProyecto = false;
        int iterador;

        if (this.planMovimientosEgresosConvenio.size() < periodosConvenio.size()) {
            iterador = this.planMovimientosEgresosConvenio.size();


            while (iterador < periodosConvenio.size()) {
                Planificacionmovconvenio planMovimientoEgresoConvenio = new Planificacionmovconvenio();

                planMovimientoEgresoConvenio.setItemflujocaja(this.itemFlujoEgresos);
                planMovimientoEgresoConvenio.setPeriodoflujocaja(periodosConvenio.get(iterador).getPeriodoflujocaja());
                planMovimientoEgresoConvenio.setContrato(convenio);
                planMovimientoEgresoConvenio.setValor(BigDecimal.ZERO);

                this.planMovimientosEgresosConvenio.add(planMovimientoEgresoConvenio);

                iterador++;
            }
        } else if (this.planMovimientosEgresosConvenio.size() > periodosConvenio.size()) {
            Planificacionmovconvenio planMovimientoEgresoConvenio;

            while (periodosConvenio.size() < planMovimientosEgresosConvenio.size()) {
                planMovimientoEgresoConvenio = planMovimientosEgresosConvenio.remove(periodosConvenio.size());

                if (planMovimientoEgresoConvenio.getIdplanificacionmovimientoconv() > 0) {
                    movimientosEgresosConvenioEliminados.add(planMovimientoEgresoConvenio);
                }
            }
        }
    }

    /**
     * Calcular el total de la fuente de egresos.
     *
     * Con base en la cantidad de periodos del flujo de caja, se recorren los
     * egresos de la fuente para determinar el total de la misma.
     *
     * @param cantidadPeriodos Cantidad de periodos del flujo de caja.
     */
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
}
