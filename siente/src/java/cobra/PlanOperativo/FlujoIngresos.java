/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.PlanOperativo;

import co.com.interkont.cobra.to.Fuenterecursosconvenio;
import co.com.interkont.cobra.to.Itemflujocaja;
import co.com.interkont.cobra.to.Periodoflujocaja;
import co.com.interkont.cobra.to.Tercero;
import java.math.BigDecimal;
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
    BigDecimal ingresos[];
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

    public void setIngresoEntidad(boolean ingresoEntidad) {
        this.ingresoEntidad = ingresoEntidad;
    }

    public BigDecimal getTotalIngresosFuente() {
        return totalIngresosFuente;
    }

    public void setTotalIngresosFuente(BigDecimal totalIngresosFuente) {
        this.totalIngresosFuente = totalIngresosFuente;
    }

    public BigDecimal[] getIngresos() {
        return ingresos;
    }

    public void setIngresos(BigDecimal[] ingresos) {
        this.ingresos = ingresos;
    }

    public String getDescripcionFuenteIngreso() {
        if (!ingresoEntidad) {
            return itemFlujoIngresos.getStrdescriptcion();
        }

        return entidadAportante.getStrnombrecompleto();
    }

    public void crearEstructuraFlujoIngresosEntidad(Fuenterecursosconvenio fuenterecursosconvenio, Tercero entidadAportante, List<Periodoflujocaja> periodosFlujoCaja) {
        this.fuenteRecursosConvenio = fuenterecursosconvenio;
        this.entidadAportante = entidadAportante;
        this.ingresoEntidad = true;
        this.ingresos = new BigDecimal[periodosFlujoCaja.size()];
        int i = 0;

        while (i < periodosFlujoCaja.size()) {
            ingresos[i] = BigDecimal.valueOf(0.0);
            i++;
        }
    }

    public void calcularTotalIngresosEntidad() {
        this.totalIngresosFuente = BigDecimal.valueOf(0.0);
        double totalIngresos = 0;
        int i = 0;

        while (i < ingresos.length) {
            totalIngresos += ingresos[i].doubleValue();

            i++;
        }

        this.totalIngresosFuente = BigDecimal.valueOf(totalIngresos);
    }

    public void guardarFlujoIngresosEntidad() {
    }
}
