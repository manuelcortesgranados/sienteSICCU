/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client.dto;

import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAble;
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAbleAsync;
import com.gantt.client.config.GanttConfig;
import com.gantt.client.config.GanttConfig.TaskType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.sencha.gxt.core.client.util.DateWrapper;
import com.sencha.gxt.data.shared.TreeStore;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clase para manejar los datos que componen el Gantt del Plan Operativo
 *
 * @author desarrollo2
 */
public class GanttDatos {
    
    
    private static CobraGwtServiceAbleAsync service = GWT.create(CobraGwtServiceAble.class);

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

        ArrayList<ActividadobraDTO> list = new ArrayList<ActividadobraDTO>(convenio.getActividadobras());

//        ///Actividad Principal - ConvenioDTO
//        //DateWrapper dw;
//        //dw = new DateWrapper(convenio.getDatefechaini());
//        
//        ActividadobraDTO t = new ActividadobraDTO(convenio.getStrnumcontrato(), convenio.getDatefechaini(), convenio.getIntduraciondias(),
//                0, GanttConfig.TaskType.PARENT, 1, false);
//        t.setTipoActividad(1);
//        
//        t.setChildren(new ArrayList<ActividadobraDTO>(convenio.getActividadobras()));
//        
//        list.add(t);
        //list.get(0).addChild(new ActividadobraDTO("M2", list.get(0).getStartDateTime(), 0,0, TaskType.MILESTONE,5,true));

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
    public static ContratoDTO estructurarDatosConvenio(final ContratoDTO convenio, TreeStore<ActividadobraDTO> taskStore, CobraGwtServiceAbleAsync service) {
        //ContratoDTO convenio = contratodto;

        List<ActividadobraDTO> lista = taskStore.getAll();
        convenio.setDatefechaini(lista.get(0).getStartDateTime());
        convenio.setDatefechafin(lista.get(0).endDateTime);
        convenio.setIntduraciondias(lista.get(0).duration);
        convenio.getActividadobras().clear();

        convenio.getActividadobras().add(lista.get(0));

        return convenio;
    }

    public static void modificarFechaFin(ActividadobraDTO actividadPadre, TreeStore<ActividadobraDTO> taskStore, ActividadobraDTOProps props) {
        if (actividadPadre != null) {
            List<ActividadobraDTO> listaHijas = taskStore.getChildren(actividadPadre);
            int duracion = CalendarUtil.getDaysBetween(obtenerMenorFechaInicio(listaHijas), obtenerMayorFechaFin(listaHijas));
            Date copiaFecha = CalendarUtil.copyDate(obtenerMenorFechaInicio(listaHijas));
            CalendarUtil.addDaysToDate(copiaFecha, duracion);
            props.endDateTime().setValue(actividadPadre, copiaFecha);
            modificarFechaFin(taskStore.getParent(actividadPadre),taskStore,props);
        }else{
         service.setLog("entre en gant datos 2 null", null);
        }

    }

    public static Date obtenerMenorFechaInicio(List<ActividadobraDTO> listaHijas) {
        Date menor = listaHijas.get(0).getStartDateTime();
        for (int i = 1; i < listaHijas.size(); i++) {
            if (listaHijas.get(i).getStartDateTime().compareTo(menor) < 0) {
                menor = listaHijas.get(i).getStartDateTime();
            }
        }

        return CalendarUtil.copyDate(menor);
    }

    public static Date obtenerMayorFechaFin(List<ActividadobraDTO> listaHijas) {
        Date mayor = listaHijas.get(0).getEndDateTime();
        for (int i = 1; i < listaHijas.size(); i++) {
            if (listaHijas.get(i).getEndDateTime().compareTo(mayor) > 0) {
                mayor = listaHijas.get(i).getEndDateTime();
            }
        }

        return CalendarUtil.copyDate(mayor);
    }
}
