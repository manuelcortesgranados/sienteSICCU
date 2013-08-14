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
 * Determina el flujo de ingresos del flujo de caja.
 *
 * Incluye entidades según las fuentes de recursos o los items de naturaleza
 * ingreso. Cada objeto de esta clase representa un registro de ingreso con sus
 * distribución en los periodos del flujo de caja, que determinan la
 * planificación de los ingresos.
 *
 * @author Yeison Osorio
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

    /**
     * Descripción de la fuente de ingreso.
     *
     * Condicional que devuelve el nombre de la fuente, ya sea de la entidad o
     * del item de naturaleza ingreso ('I').
     *
     * @return Nombre de la fuente de ingresos.
     */
    public String getDescripcionFuenteIngreso() {
        if (!ingresoEntidad) {
            return itemFlujoIngresos.getStrdescripcion();
        }

        return entidadAportante.getStrnombrecompleto();
    }

    /**
     * Crear la estructura del flujo de ingresos por entidad.
     *
     * Al llamar este método y referenciar los parámetros, se crea un flujo de
     * ingresos en los periodos del flujo de caja para la entidad aportante.
     * Este método inicializa el flujo de ingresos de la entidad al no existir
     * alguno.
     *
     * @param fuenterecursosconvenio Fuente de recursos del convenio.
     * @param entidadAportante Tercero de la entidad aportante.
     * @param periodosFlujoCaja Periodos del flujo de caja.
     */
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

    /**
     * Crear la estructura del flujo de ingresos por item de ingreso.
     *
     * Al llamar este método y referenciar los parámetros, se crea un flujo de
     * ingresos en los periodos del flujo de caja para la entidad aportante.
     * Este método inicializa el flujo de ingresos de la entidad al no existir
     * alguno.
     *
     * @param itemFlujoIngresos Item de flujo de caja de naturaleza ingreso.
     * @param periodosFlujoCaja Periodos del flujo de caja.
     * @param convenio Convenio marco.
     */
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

    /**
     * Actualizar la estructura del flujo de ingresos por entidad.
     *
     * Con base en una lista de ingresos consultados se actualiza el flujo de
     * ingresos por entidad.
     *
     * @param periodosFlujoCaja Periodos del flujo de caja.
     * @param fuenteRecursosConvenio Fuente de recursos del convenio.
     */
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

    /**
     * Actualizar el flujo de ingresos por item de ingreso.
     *
     * Con base en una lista de ingresos consultados se actualiza el flujo de
     * ingresos por item de ingreso.
     *
     * @param periodosFlujoCaja Periodos del flujo de caja.
     * @param convenio Convenio marco.
     */
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

    /**
     * Calcular el total de ingresos por fuente.
     *
     * De acuerdo con la cantidad de periodos, se recorre la lista de ingresos
     * por periodo en la fuente y totalizan los valores definidos.
     *
     * @param cantidadPeriodos Cantidad de periodos de flujo de caja.
     */
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
}
