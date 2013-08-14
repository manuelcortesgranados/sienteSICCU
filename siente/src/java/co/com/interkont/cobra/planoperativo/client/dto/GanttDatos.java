/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client.dto;

import com.gantt.client.config.GanttConfig;
import com.sencha.gxt.core.client.util.DateWrapper;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clase para manejar los datos que componen el Gantt del Plan Operativo
 * @author desarrollo2
 */
public class GanttDatos {
    /**     
     * Método para construir toda la estructura jerárquica de las tareas del plan operativo     
     * @author Carlos Loaiza     
     * @param listaact Lista de tareas para construir el árbol    
     * @param convenio  Objeto convenio raiz del gantt  
     * @return ActividadobraDTO Raíz de todo el plan operativo, con sus childrens seteados
     */    
    public static ActividadobraDTO getTareas(ContratoDTO convenio) {        
        DateWrapper dw;       
        ArrayList<ActividadobraDTO> list = new ArrayList<ActividadobraDTO>();
        
        ///Actividad Principal - ConvenioDTO
        dw = new DateWrapper(convenio.getDatefechaini());
        
        ActividadobraDTO t = new ActividadobraDTO(convenio.getStrnumcontrato(), convenio.getDatefechaini(), convenio.getIntduraciondias(),
				0, GanttConfig.TaskType.PARENT,1,false);
                t.setTipoActividad(1);                               
                
               
                
                t.setChildren(new ArrayList<ActividadobraDTO>(convenio.getActividadobras()));
                 list.add(t);
//        for(ActividadobraDTO act: convenio.getActividadobras())
//        {
//            
//            ActividadobraDTO t2 = new ActividadobraDTO(act.getName(), dw.addDays(1).asDate(), 10,
//				30, GanttConfig.TaskType.PARENT);
//                t2.setTipoActividad(1);                
//                t.addChild(t2);
//        }    
      
        /*
        DateWrapper dw = new DateWrapper(new Date()).clearTime().addDays(-7);
		ActividadobraDTO t = new ActividadobraDTO("Project_X", dw.addDays(1).asDate(), 10,
				30, GanttConfig.TaskType.PARENT);
                t.setTipoActividad(1);
		ActividadobraDTO t2 = new ActividadobraDTO("Planning", dw.addDays(1).asDate(), 5, 40,
				GanttConfig.TaskType.PARENT);
                t2.setTipoActividad(2);
		t2.addChild(new ActividadobraDTO("Prestudy", dw.addDays(1).asDate(), 2, 100,
				GanttConfig.TaskType.PARENT));
		t2.addChild(new ActividadobraDTO("Fesabillity_Study", dw.addDays(3).asDate(), 1, 10,
				GanttConfig.TaskType.LEAF));
		t2.addChild(new ActividadobraDTO("Resource_allocation", dw.addDays(4).asDate(), 2, 30,
				GanttConfig.TaskType.LEAF));


		ActividadobraDTO t3 = new ActividadobraDTO("Execution", dw.addDays(6).asDate(), 5, 0,
				GanttConfig.TaskType.PARENT);
                t3.setTipoActividad(2);
		t3.addChild(new ActividadobraDTO("Task-1", dw.addDays(6).asDate(), 1, 0,
				GanttConfig.TaskType.LEAF));
		t3.addChild(new ActividadobraDTO("Task-2", dw.addDays(7).asDate(), 4, 0,
				GanttConfig.TaskType.LEAF));
		t3.addChild(new ActividadobraDTO("Task-3", dw.addDays(7).asDate(), 2, 0,
				GanttConfig.TaskType.LEAF));
		ActividadobraDTO t4 = new ActividadobraDTO("Task-4", dw.addDays(11).asDate(), 5, 0,
				GanttConfig.TaskType.PARENT);
                t4.setTipoActividad(3);
		t4.addChild(new ActividadobraDTO("Subtask-1", dw.addDays(11).asDate(), 3, 0,
				GanttConfig.TaskType.LEAF));
		t4.addChild(new ActividadobraDTO("Subtask-2", dw.addDays(14).asDate(), 2, 0,
				GanttConfig.TaskType.LEAF));
		t3.addChild(t4);
		t3.addChild(new ActividadobraDTO("Task-5", dw.addDays(16).asDate(), 2, 0,
				GanttConfig.TaskType.LEAF));
		
		t.addChild(t2);
		t.addChild(new ActividadobraDTO("M2", dw.addDays(6).asDate(), 0,
				0, GanttConfig.TaskType.MILESTONE));
		t.addChild(t3);
		t.addChild(new ActividadobraDTO("Project-End", dw.addDays(18).asDate(), 0,
				0, GanttConfig.TaskType.MILESTONE));
		
		               
                ArrayList<ActividadobraDTO> list = new ArrayList<ActividadobraDTO>();
                */
//		list.add(t);
         	
	ActividadobraDTO root = new ActividadobraDTO(list);
                
        return root;
    }
}
