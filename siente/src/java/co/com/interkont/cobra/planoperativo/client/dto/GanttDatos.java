/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client.dto;

import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAbleAsync;
import com.gantt.client.config.GanttConfig;
import com.sencha.gxt.core.client.util.DateWrapper;
import com.sencha.gxt.data.shared.TreeStore;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase para manejar los datos que componen el Gantt del Plan Operativo
 *
 * @author desarrollo2
 */
public class GanttDatos {

    /**
     * Método para construir toda la estructura jerárquica de las tareas del
     * plan operativo
     *
     * @author Carlos Loaiza
     * @param convenio Objeto convenio raiz del gantt
     * @return ActividadobraDTO Raíz de todo el plan operativo, con sus
     * childrens seteados
     */
    public static ActividadobraDTO getTareas(ContratoDTO convenio) {
        
        ArrayList<ActividadobraDTO> list = new ArrayList<ActividadobraDTO>();

        ///Actividad Principal - ConvenioDTO
        //DateWrapper dw;
        //dw = new DateWrapper(convenio.getDatefechaini());
        
        ActividadobraDTO t = new ActividadobraDTO(convenio.getStrnumcontrato(), convenio.getDatefechaini(), convenio.getIntduraciondias(),
                0, GanttConfig.TaskType.PARENT, 1, false);
        t.setTipoActividad(1);
        
        t.setChildren(new ArrayList<ActividadobraDTO>(convenio.getActividadobras()));
        ActividadobraDTO t1 = new ActividadobraDTO("probandooo", convenio.getDatefechaini(), convenio.getIntduraciondias(),
                0, GanttConfig.TaskType.PARENT, 2, false);
        
        t.getChildren().add(t1);
        list.add(t);
        ActividadobraDTO root = new ActividadobraDTO(list);
        
        return root;
    }

    /**
     * Método para leer el taskStore y llevar los datos a convenio plan
     * operativo
     *
     * @author Carlos Loaiza
     * @param contratodto Objeto convenio raiz del gantt
     * @return ActividadobraDTO Raíz de todo el plan operativo, con sus
     * childrens seteados
     */
    public static ContratoDTO estructurarDatosConvenio(ContratoDTO contratodto, TreeStore<ActividadobraDTO> taskStore, CobraGwtServiceAbleAsync service) {
        ContratoDTO convenio = contratodto;
        
        List<ActividadobraDTO> lista = taskStore.getAll();
        for (ActividadobraDTO act : lista) {
            service.setLog("act: " + act.getStrdescactividad() + " desc: " + act.getName() + " fechaini: " + act.startDateTime + " act hijas: " + act.getChildren().size(), null);
        }        
        return convenio;
    }    
}
