package cobra.PlanOperativo;

import co.com.interkont.cobra.to.Contrato;
import co.com.interkont.cobra.to.Itemflujocaja;
import co.com.interkont.cobra.to.Obra;
import co.com.interkont.cobra.to.Planificacionmovconvenio;
import co.com.interkont.cobra.to.Planificacionmovimientocontrato;
import co.com.interkont.cobra.to.Planificacionmovimientoproyecto;
import co.com.interkont.cobra.to.Planificacionmovimientoprydirecto;
import co.com.interkont.cobra.to.Planificacionmovimientopryotros;
import co.com.interkont.cobra.to.Relacioncontratoperiodoflujocaja;
import java.io.Serializable;
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
public class FlujoEgresos implements Serializable {

    Obra proyecto;
    Itemflujocaja itemFlujoEgresos = new Itemflujocaja();
    boolean egresoProyecto;
    boolean egresoContrato;
    boolean egresoOtros;
    boolean egresoPryDirecto;
    boolean egresoPryOtros;
    List<Planificacionmovimientoproyecto> planMovimientosProyecto;
    List<Planificacionmovconvenio> planMovimientosEgresosConvenio;
    List<Planificacionmovimientoproyecto> movimientosProyectoEliminados;
    List<Planificacionmovconvenio> movimientosEgresosConvenioEliminados;
    List<Planificacionmovimientocontrato> planMovimientosContrato = new ArrayList<Planificacionmovimientocontrato>();
    List<Planificacionmovimientocontrato> planMovimientosContratoEliminados = new ArrayList<Planificacionmovimientocontrato>();
    List<Planificacionmovimientoprydirecto> planMovimientosPryDirecto = new ArrayList<Planificacionmovimientoprydirecto>();
    List<Planificacionmovimientoprydirecto> planMovimientosPryDirectoEliminados = new ArrayList<Planificacionmovimientoprydirecto>();
    List<Planificacionmovimientopryotros> planMovimientosPryOtros = new ArrayList<Planificacionmovimientopryotros>();
    List<Planificacionmovimientopryotros> planMovimientosPryOtrosEliminados = new ArrayList<Planificacionmovimientopryotros>();
    BigDecimal totalEgresosFuente;
    BigDecimal totalEsperadoEgresosFuente;
    double contrapartida;
    private Contrato contrato;

    public BigDecimal getTotalEsperadoEgresosFuente() {
        return totalEsperadoEgresosFuente;
    }

    public void setTotalEsperadoEgresosFuente(BigDecimal totalEsperadoEgresosFuente) {
        this.totalEsperadoEgresosFuente = totalEsperadoEgresosFuente;
    }
    
    public boolean isEgresoProyecto() {
        return egresoProyecto;
    }

    public void setEgresoProyecto(boolean egresoProyecto) {
        this.egresoProyecto = egresoProyecto;
        if (egresoProyecto) {
            egresoContrato = false;
            egresoOtros = false;
            egresoPryDirecto = false;
            egresoPryOtros = false;
        }
    }

    public boolean isEgresoContrato() {
        return egresoContrato;
    }

    public void setEgresoContrato(boolean egresoContrato) {
        this.egresoContrato = egresoContrato;
        if (egresoContrato) {
            egresoProyecto = false;
            egresoOtros = false;
            egresoPryDirecto = false;
            egresoPryOtros = false;
        }
    }

    public boolean isEgresoOtros() {
        return egresoOtros;
    }

    public void setEgresoOtros(boolean egresoOtros) {
        this.egresoOtros = egresoOtros;
        if (egresoOtros) {
            egresoProyecto = false;
            egresoContrato = false;
            egresoPryDirecto = false;
            egresoPryOtros = false;
        }
    }

    public boolean isEgresoPryDirecto() {
        return egresoPryDirecto;
    }

    public void setEgresoPryDirecto(boolean egresoPryDirecto) {
        this.egresoPryDirecto = egresoPryDirecto;
        if (egresoPryDirecto) {
            egresoProyecto = false;
            egresoContrato = false;
            egresoOtros = false;
            egresoPryOtros = false;
        }
    }

    public boolean isEgresoPryOtros() {
        return egresoPryOtros;
    }

    public void setEgresoPryOtros(boolean egresoPryOtros) {
        this.egresoPryOtros = egresoPryOtros;
        if (egresoPryOtros) {
            egresoProyecto = false;
            egresoContrato = false;
            egresoPryDirecto = false;
            egresoOtros = false;
        }
    }

    public List<Planificacionmovimientoprydirecto> getPlanMovimientosPryDirecto() {
        return planMovimientosPryDirecto;
    }

    public void setPlanMovimientosPryDirecto(List<Planificacionmovimientoprydirecto> planMovimientosPryDirecto) {
        this.planMovimientosPryDirecto = planMovimientosPryDirecto;
    }

    public List<Planificacionmovimientoprydirecto> getPlanMovimientosPryDirectoEliminados() {
        return planMovimientosPryDirectoEliminados;
    }

    public void setPlanMovimientosPryDirectoEliminados(List<Planificacionmovimientoprydirecto> planMovimientosPryDirectoEliminados) {
        this.planMovimientosPryDirectoEliminados = planMovimientosPryDirectoEliminados;
    }

    public List<Planificacionmovimientopryotros> getPlanMovimientosPryOtros() {
        return planMovimientosPryOtros;
    }

    public void setPlanMovimientosPryOtros(List<Planificacionmovimientopryotros> planMovimientosPryOtros) {
        this.planMovimientosPryOtros = planMovimientosPryOtros;
    }

    public List<Planificacionmovimientopryotros> getPlanMovimientosPryOtrosEliminados() {
        return planMovimientosPryOtrosEliminados;
    }

    public void setPlanMovimientosPryOtrosEliminados(List<Planificacionmovimientopryotros> planMovimientosPryOtrosEliminados) {
        this.planMovimientosPryOtrosEliminados = planMovimientosPryOtrosEliminados;
    }
    
    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public List<Planificacionmovimientocontrato> getPlanMovimientosContrato() {
        return planMovimientosContrato;
    }

    public void setPlanMovimientosContrato(List<Planificacionmovimientocontrato> planMovimientosContrato) {
        this.planMovimientosContrato = planMovimientosContrato;
    }

    public List<Planificacionmovimientocontrato> getPlanMovimientosContratoEliminados() {
        return planMovimientosContratoEliminados;
    }

    public void setPlanMovimientosContratoEliminados(List<Planificacionmovimientocontrato> planMovimientosContratoEliminados) {
        this.planMovimientosContratoEliminados = planMovimientosContratoEliminados;
    }
    
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
            return itemFlujoEgresos.getStrdescripcion();
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
        itemFlujoEgresos.setStrdescripcion(proyecto.getStrnombrecrot());
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
     * Crear la estructura del flujo de egresos para un contrato.
     *
     * Al llamar este método y referenciar los parámetros, se crea un flujo de
     * egresos en los periodos del flujo de caja para el contrato asociado. Este
     * método inicializa el flujo de egresos del contrato al no existir alguno.
     *
     * @param contrato Contrato asociado a un proyecto del convenio.
     * @param periodosFlujoCaja Periodos del flujo de caja.
     */
    public void crearEstructuraFlujoEgresosContrato(Contrato contrato, List<Relacioncontratoperiodoflujocaja> periodosConvenio) {
        int numPeriodoFlujoEgresos = 0;
        this.planMovimientosContrato = new ArrayList<Planificacionmovimientocontrato>();
        this.planMovimientosContratoEliminados = new ArrayList<Planificacionmovimientocontrato>();
        this.contrato = contrato;
        setEgresoContrato(true);
        for (Relacioncontratoperiodoflujocaja periodoConvenio : periodosConvenio) {
            Planificacionmovimientocontrato planMovimientoContrato = new Planificacionmovimientocontrato();
            planMovimientoContrato.setPlanificacionmovimientoproyecto(planMovimientosProyecto.get(numPeriodoFlujoEgresos));
            planMovimientoContrato.setContrato(contrato);
            planMovimientoContrato.setValor(BigDecimal.ZERO);
            planMovimientosContrato.add(planMovimientoContrato);
            numPeriodoFlujoEgresos++;
        }
    }
    
    /**
     * Crear la estructura del flujo de egresos para un pago directo.
     *
     * Al llamar este método y referenciar los parámetros, se crea un flujo de
     * egresos en los periodos del flujo de caja para el pago directo asociado. Este
     * método inicializa el flujo de egresos del pago directo al no existir alguno.
     *
     * @param periodosFlujoCaja Periodos del flujo de caja.
     */
    public void crearEstructuraFlujoEgresosPryDirecto(List<Relacioncontratoperiodoflujocaja> periodosConvenio) {
        int numPeriodoFlujoEgresos = 0;
        this.planMovimientosPryDirecto = new ArrayList<Planificacionmovimientoprydirecto>();
        this.planMovimientosPryDirectoEliminados = new ArrayList<Planificacionmovimientoprydirecto>();
        setEgresoPryDirecto(true);
        for (Relacioncontratoperiodoflujocaja periodoConvenio : periodosConvenio) {
            Planificacionmovimientoprydirecto planMovimientoPryDirecto = new Planificacionmovimientoprydirecto();
            planMovimientoPryDirecto.setPlanificacionmovimientoproyecto(planMovimientosProyecto.get(numPeriodoFlujoEgresos));
            planMovimientoPryDirecto.setValor(BigDecimal.ZERO);
            planMovimientosPryDirecto.add(planMovimientoPryDirecto);
            numPeriodoFlujoEgresos++;
        }
    }
    
    /**
     * Crear la estructura del flujo de egresos para otros pagos del proyecto.
     *
     * Al llamar este método y referenciar los parámetros, se crea un flujo de
     * egresos en los periodos del flujo de caja para otros pagos del proyecto. Este
     * método inicializa el flujo de egresos de otros pagos del proyecto al no existir alguno.
     *
     * @param periodosFlujoCaja Periodos del flujo de caja.
     */
    public void crearEstructuraFlujoEgresosPryOtros(List<Relacioncontratoperiodoflujocaja> periodosConvenio) {
        int numPeriodoFlujoEgresos = 0;
        this.planMovimientosPryOtros = new ArrayList<Planificacionmovimientopryotros>();
        this.planMovimientosPryOtrosEliminados = new ArrayList<Planificacionmovimientopryotros>();
        setEgresoPryOtros(true);
        for (Relacioncontratoperiodoflujocaja periodoConvenio : periodosConvenio) {
            Planificacionmovimientopryotros planMovimientoPryOtros = new Planificacionmovimientopryotros();
            planMovimientoPryOtros.setPlanificacionmovimientoproyecto(planMovimientosProyecto.get(numPeriodoFlujoEgresos));
            planMovimientoPryOtros.setValor(BigDecimal.ZERO);
            planMovimientosPryOtros.add(planMovimientoPryOtros);
            numPeriodoFlujoEgresos++;
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
            } else if (egresoOtros){
                totalEgresos += planMovimientosEgresosConvenio.get(iterador).getValor().doubleValue();
            } else if (egresoContrato) {
                totalEgresos += planMovimientosContrato.get(iterador).getValor().doubleValue();
            } else if (egresoPryDirecto) {
                System.out.println("planMovimientosPryDirecto.size() = " + planMovimientosPryDirecto.size());
                totalEgresos += planMovimientosPryDirecto.get(iterador).getValor().doubleValue();
            } else if (egresoPryOtros) {
                totalEgresos += planMovimientosPryOtros.get(iterador).getValor().doubleValue();
            }

            iterador++;
        }

        this.totalEgresosFuente = BigDecimal.valueOf(totalEgresos);
    }

    /**
     * Refrescar los periodos del convenio. Refresca los periodos del convenio
     * relacionados con la planificación de egresos de acuerdo con tipo.
     *
     * @param periodosConvenio Lista de periodos generados y/o guardados.
     */
    public void refrescarPeriodos(List<Relacioncontratoperiodoflujocaja> periodosConvenio) {
        int iterador = 0;

        while (iterador < periodosConvenio.size()) {
            if (isEgresoProyecto()) {
                planMovimientosProyecto.get(iterador).setPeriodoflujocaja(periodosConvenio.get(iterador).getPeriodoflujocaja());
            } else if(isEgresoOtros()) {
                planMovimientosEgresosConvenio.get(iterador).setPeriodoflujocaja(periodosConvenio.get(iterador).getPeriodoflujocaja());
            } 
            iterador++;
        }
    }
}
