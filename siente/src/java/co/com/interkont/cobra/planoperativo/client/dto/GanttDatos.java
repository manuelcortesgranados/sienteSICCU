/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client.dto;

import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAble;
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAbleAsync;
import com.gantt.client.config.GanttConfig.TaskType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.sencha.gxt.core.client.util.DateWrapper;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.TreeStore;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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

    public static ArrayList getDependencia(ContratoDTO convenio) {
        ArrayList<DependenciaDTO> list = new ArrayList<DependenciaDTO>(convenio.getDependenciasGenerales());
        return list;
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
    public static ContratoDTO estructurarDatosConvenio(final ContratoDTO convenio, TreeStore<ActividadobraDTO> taskStore, CobraGwtServiceAbleAsync service, ListStore<DependenciaDTO> depStore) {
        //ContratoDTO convenio = contratodto;

        List<ActividadobraDTO> lista = taskStore.getAll();
        limpiarActividadesListaDependencia(lista);
        Set<DependenciaDTO> lstDependencias = new HashSet<DependenciaDTO>(depStore.getAll());
        convenio.setDatefechaini(lista.get(0).getStartDateTime());
        convenio.setDatefechafin(lista.get(0).endDateTime);
        convenio.setIntduraciondias(lista.get(0).duration);
        convenio.getActividadobras().clear();
        // convenio.setDependenciasGenerales(new LinkedHashSet(0));
        convenio.getActividadobras().add(lista.get(0));
        for (DependenciaDTO d : lstDependencias) {
            DependenciaDTO dep = new DependenciaDTO();
            dep.setId((String.valueOf(new Date().getTime())));
            dep.setActividadFrom(d.getActividadFrom());
            dep.setActividadTo(d.getActividadTo());
            dep.setFromId(d.fromId);
            dep.setToId(d.toId);
            dep.setType(d.type);
            boolean encontro = false;
            for (int i = 0; i < lista.size() && !encontro; i++) {
                ActividadobraDTO act = lista.get(i);
                if (act.getName().equals(d.getFromId())) {
                    if (act.getDependenciasForFkActividadOrigen() != null) {
                        act.getDependenciasForFkActividadOrigen().add(dep);
                        encontro = true;

                    }


                }
            }

        }
        return convenio;
    }

    public static void limpiarActividadesListaDependencia(List<ActividadobraDTO> lista) {
        for (ActividadobraDTO act : lista) {
            act.getDependenciasForFkActividadOrigen().clear();
        }

    }

    public static void organizarListaDependencias(List<DependenciaDTO> lstDependencias) {
        List<Integer> listaPosEliminar = new ArrayList<Integer>();
        for (int i = 0; i < lstDependencias.size(); i++) {
            DependenciaDTO dep = lstDependencias.get(i);
            masDeone(dep, i, lstDependencias, listaPosEliminar);
        }
        service.setLog("eliminar lista" + listaPosEliminar.size(), null);

    }

    public static void masDeone(DependenciaDTO dependencia, int posi, List<DependenciaDTO> lstDependencias, List<Integer> listaPosEliminar) {
        for (int i = 0; i < lstDependencias.size(); i++) {
            DependenciaDTO dep = lstDependencias.get(i);
            if (i != posi) {
                if (dep.getFromId().equals(dependencia.getFromId()) && dep.getToId().equals(dependencia.getToId())) {
                    listaPosEliminar.get(i);
                }
            }
        }
    }

    public static void modificarFechaFin(ActividadobraDTO actividadPadre, TreeStore<ActividadobraDTO> taskStore, ActividadobraDTOProps props) {
        if (actividadPadre != null) {
            List<ActividadobraDTO> listaHijas = taskStore.getChildren(actividadPadre);
            service.setLog("actividad:" + actividadPadre.getName(), null);
            int duracion = CalendarUtil.getDaysBetween(obtenerMenorFechaInicio(listaHijas), obtenerMayorFechaFin(listaHijas));
            Date copiaFecha = CalendarUtil.copyDate(obtenerMenorFechaInicio(listaHijas));
            CalendarUtil.addDaysToDate(copiaFecha, duracion);
            props.endDateTime().setValue(actividadPadre, copiaFecha);
            modificarFechaFin(taskStore.getParent(actividadPadre), taskStore, props);

        } else {
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
        service.setLog("menor:" + menor, null);
        return CalendarUtil.copyDate(menor);
    }

    public static Date obtenerMayorFechaFin(List<ActividadobraDTO> listaHijas) {
        Date mayor = listaHijas.get(0).getEndDateTime();
        for (int i = 1; i < listaHijas.size(); i++) {
            if (listaHijas.get(i).getEndDateTime().compareTo(mayor) > 0) {
                mayor = listaHijas.get(i).getEndDateTime();
            }
        }
        service.setLog("mayor:" + mayor, null);
        return CalendarUtil.copyDate(mayor);
    }

    public static void enlazarActividadConDependencia(List<DependenciaDTO> lstDependencias, List<ActividadobraDTO> lstActividades) {
        service.setLog("entre enlazar acti dep", null);
        for (DependenciaDTO dependencia : lstDependencias) {
            service.setLog("entre enlazar acti dep 1", null);
            buscarActividadDependencia(dependencia.getActividadFrom(), dependencia, lstActividades, 1);
            //buscarActividadDependencia(dependencia.getActividadTo(), dependencia, lstActividades, 2);
        }
        service.setLog("sali enlazar acti dep", null);
    }

    //busca donde esa dependencia la actividadobra origen y l destino
    //y agregarla a la respectiva lista
    public static void buscarActividadDependencia(ActividadobraDTO actividadObraBuscada, DependenciaDTO dependencia, List<ActividadobraDTO> lstActividades, int lstDependencia) {
        service.setLog("entre buscarActividadDependencia", null);
        for (ActividadobraDTO act : lstActividades) {
            if (act.getName().equals(actividadObraBuscada.getName())) {
                service.setLog("entre buscarActividadDependencia 1" + act.getDependenciasForFkActividadOrigen().size(), null);
                act.getDependenciasForFkActividadOrigen().add(dependencia);
                service.setLog("entre buscarActividadDependencia 3", null);

            }
        }
        service.setLog("sali buscarActividadDependencia", null);
    }
}
