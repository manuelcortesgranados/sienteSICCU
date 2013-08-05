/*
 * Gxt Gantt 3.0.1 
 * Copyright(c) 2009-2012, Rasmus Ersmarker Consulting.
 * rasmus@gxt-scheduler.com
 * 
 * http://www.gxt-scheduler/license
 */
package co.com.interkont.cobra.planoperativo.client.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gantt.client.config.GanttConfig.DependencyType;
import com.gantt.client.config.GanttConfig.TaskType;
import com.sencha.gxt.core.client.util.DateWrapper;

public class GanttDummyData {

    public static ActividadobraDTO getTasks() {
        	
		DateWrapper dw = new DateWrapper(new Date()).clearTime().addDays(-7);
		ActividadobraDTO t = new ActividadobraDTO("Project_X", dw.addDays(1).asDate(), 10,
				30, TaskType.PARENT);
                t.setTipoActividad(1);
		ActividadobraDTO t2 = new ActividadobraDTO("Planning", dw.addDays(1).asDate(), 5, 40,
				TaskType.PARENT);
                t2.setTipoActividad(2);
		t2.addChild(new ActividadobraDTO("Prestudy", dw.addDays(1).asDate(), 2, 100,
				TaskType.PARENT));
		t2.addChild(new ActividadobraDTO("Fesabillity_Study", dw.addDays(3).asDate(), 1, 10,
				TaskType.LEAF));
		t2.addChild(new ActividadobraDTO("Resource_allocation", dw.addDays(4).asDate(), 2, 30,
				TaskType.LEAF));


		ActividadobraDTO t3 = new ActividadobraDTO("Execution", dw.addDays(6).asDate(), 5, 0,
				TaskType.PARENT);
                t3.setTipoActividad(2);
		t3.addChild(new ActividadobraDTO("Task-1", dw.addDays(6).asDate(), 1, 0,
				TaskType.LEAF));
		t3.addChild(new ActividadobraDTO("Task-2", dw.addDays(7).asDate(), 4, 0,
				TaskType.LEAF));
		t3.addChild(new ActividadobraDTO("Task-3", dw.addDays(7).asDate(), 2, 0,
				TaskType.LEAF));
		ActividadobraDTO t4 = new ActividadobraDTO("Task-4", dw.addDays(11).asDate(), 5, 0,
				TaskType.PARENT);
                t4.setTipoActividad(3);
		t4.addChild(new ActividadobraDTO("Subtask-1", dw.addDays(11).asDate(), 3, 0,
				TaskType.LEAF));
		t4.addChild(new ActividadobraDTO("Subtask-2", dw.addDays(14).asDate(), 2, 0,
				TaskType.LEAF));
		t3.addChild(t4);
		t3.addChild(new ActividadobraDTO("Task-5", dw.addDays(16).asDate(), 2, 0,
				TaskType.LEAF));
		
		t.addChild(t2);
		t.addChild(new ActividadobraDTO("M2", dw.addDays(6).asDate(), 0,
				0, TaskType.MILESTONE));
		t.addChild(t3);
		t.addChild(new ActividadobraDTO("Project-End", dw.addDays(18).asDate(), 0,
				0, TaskType.MILESTONE));
		
		ArrayList<ActividadobraDTO> list = new ArrayList<ActividadobraDTO>();
		list.add(t);
		ActividadobraDTO root = new ActividadobraDTO(list);
		return root;
	}

		// Create the dependencies
		public static List<DependenciaDTO> getDependencies() {
			List<DependenciaDTO> list = new ArrayList<DependenciaDTO>();
			list.add(new DependenciaDTO("5", "Prestudy", "Fesabillity_Study",
					DependencyType.ENDtoSTART));
			list.add(new DependenciaDTO("6", "Fesabillity_Study", "Resource_allocation",
					DependencyType.ENDtoSTART));
			list.add(new DependenciaDTO("0", "Resource_allocation", "M2",
					DependencyType.ENDtoSTART));
			list.add(new DependenciaDTO("7", "M2", "Task-1",
					DependencyType.ENDtoSTART));
			list.add(new DependenciaDTO("1", "Task-1", "Task-2",
					DependencyType.ENDtoSTART));
			list.add(new DependenciaDTO("2", "Task-1", "Task-3",
					DependencyType.ENDtoSTART));
			list.add(new DependenciaDTO("8", "Task-2",
					"Subtask-1", DependencyType.ENDtoSTART));
			list.add(new DependenciaDTO("4", "Task-3", "Subtask-1",
					DependencyType.ENDtoSTART));
			list.add(new DependenciaDTO("3", "Subtask-1",
					"Subtask-2", DependencyType.ENDtoSTART));
			list.add(new DependenciaDTO("9", "Subtask-2",
					"Task-5", DependencyType.ENDtoSTART));
			list.add(new DependenciaDTO("9", "Task-5",
					"Project-End", DependencyType.ENDtoSTART));
			return list;
		}

}
