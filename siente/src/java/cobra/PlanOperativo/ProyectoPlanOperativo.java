/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.PlanOperativo;

import co.com.interkont.cobra.to.Contrato;
import co.com.interkont.cobra.to.Obra;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de almacenar la información de un proyecto del plan operativo
 * @author desarrollo10
 */
public class ProyectoPlanOperativo {
    /**
     * Pryecto del plan operativo
     */
    private Obra proyecto;
    /**
     * Listado de contratos asociados al proyecto del plan operativo
     */
    private List<Contrato> contratosProyecto = new ArrayList<Contrato>();  

    public ProyectoPlanOperativo() {
    }

    public Obra getProyecto() {
        return proyecto;
    }

    public void setProyecto(Obra proyecto) {
        this.proyecto = proyecto;
    }

    public List<Contrato> getContratosProyecto() {
        return contratosProyecto;
    }

    public void setContratosProyecto(List<Contrato> contratosProyecto) {
        this.contratosProyecto = contratosProyecto;
    }
    
}
