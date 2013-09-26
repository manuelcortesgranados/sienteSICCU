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
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.richfaces.model.Arrangeable;

/**
 * Clase para manejar los datos que componen el Gantt del Plan Operativo
 *
 * @author desarrollo2
 */
public class GanttDatos {

    private static CobraGwtServiceAbleAsync service = GWT.create(CobraGwtServiceAble.class);
    private static Date fechaComparar;

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
        convenio.setDependenciasGenerales(lstDependencias);
        convenio.setDatefechaini(lista.get(0).getStartDateTime());
        convenio.setDatefechaactaini(obtenerActividadDeRaiz(0,convenio).getChildren().get(0).getStartDateTime());
        convenio.setDatefechafin(lista.get(0).endDateTime);
        convenio.setIntduraciondias(lista.get(0).duration);
        convenio.getActividadobras().clear();
        convenio.getActividadobras().add(lista.get(0));
        for (DependenciaDTO d : lstDependencias) {
            DependenciaDTO dep = new DependenciaDTO();
            dep.setId(""+d.getId());
            dep.setFromId(d.fromId);
            dep.setToId(d.toId);
            dep.setType(d.type);
            boolean encontro = false;
            for (int i = 0; i < lista.size() && !encontro; i++) {
                ActividadobraDTO act = lista.get(i);
                if (act.getId().equals(d.getFromId())) {
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

    

    public static void modificarFechaFin(ActividadobraDTO actividadPadre, TreeStore<ActividadobraDTO> taskStore, ActividadobraDTOProps props, ContratoDTO contrato) {
        if (actividadPadre != null) {
            if (actividadPadre.getTipoActividad() != 1) {
                List<ActividadobraDTO> listaHijas = taskStore.getChildren(actividadPadre);
                int duracion = CalendarUtil.getDaysBetween(obtenerMenorFechaInicio(listaHijas), obtenerMayorFechaFin(listaHijas));
                Date copiaFecha = CalendarUtil.copyDate(obtenerMenorFechaInicio(listaHijas));
                CalendarUtil.addDaysToDate(copiaFecha, duracion);
                props.endDateTime().setValue(actividadPadre, copiaFecha);
                if (actividadPadre.getName().equals("Planeación del Convenio")) {
                    fechaComparar = actividadPadre.getEndDateTime();
                    buscarActividad(contrato, 1, taskStore, props);
                } else if (actividadPadre.getName().equals("Ejecución del Convenio")) {
                    fechaComparar = actividadPadre.getEndDateTime();
                    buscarActividad(contrato, 2, taskStore, props);
                }

                modificarFechaFin(taskStore.getParent(actividadPadre), taskStore, props, contrato);

            }
        } else {
            service.setLog("entre en gant datos Con padre convenio", null);
        }

    }

    public static void buscarActividad(ContratoDTO contrato, int tipo, TreeStore<ActividadobraDTO> taskStore, ActividadobraDTOProps props) {
        List<ActividadobraDTO> lstActividades = new ArrayList<ActividadobraDTO>(contrato.getActividadobras());
        if (tipo == 1) {
            service.setLog("En buscar actividad 1:" + lstActividades.get(0).getChildren().get(1).getName(), null);
            odifi(lstActividades.get(0).getChildren().get(1), taskStore, props, contrato);
            fechaComparar=lstActividades.get(0).getChildren().get(1).endDateTime;
            service.setLog("En buscar actividad liquidacion:", null);
            odifi(lstActividades.get(0).getChildren().get(2), taskStore, props,contrato);
        } else {
            odifi(lstActividades.get(0).getChildren().get(2), taskStore, props, contrato);
        }
    }

    public static void odifi(ActividadobraDTO act, TreeStore<ActividadobraDTO> taskStore, ActividadobraDTOProps props, ContratoDTO contrato) {
        m(act, taskStore, props, contrato);

        if (!act.getChildren().isEmpty()) {
            for (ActividadobraDTO actiHija : act.getChildren()) {
                odifi(actiHija, taskStore, props, contrato);
            }
        }
    }

    public static void m(ActividadobraDTO actiHija, TreeStore<ActividadobraDTO> taskStore, ActividadobraDTOProps props, ContratoDTO contrato) {

        /*verifico el sentido en que tengo que hacer el movimiento de las
         * actividades si necesito aumentar la fecha de inicio o disminuirla*/
        if (fechaComparar.compareTo(actiHija.getStartDateTime()) > 0) {

            int duracion = CalendarUtil.getDaysBetween(actiHija.getStartDateTime(), fechaComparar);
            Date fecha = CalendarUtil.copyDate(actiHija.getStartDateTime());
            CalendarUtil.addDaysToDate(fecha, duracion);

            Date asigFin = CalendarUtil.copyDate(fecha);
            actiHija.setEndDateTime(asigFin);

            CalendarUtil.addDaysToDate(actiHija.getEndDateTime(), actiHija.getDuration());

            if (actiHija.getName().equals("Suscripcion acta de inicio")) {
                actiHija.getContrato().setDatefechaactaini(actiHija.getStartDateTime());

            }
            if (actiHija.getName().equals("Suscripcion del contrato")) {
                actiHija.getContrato().setDatefechaini(actiHija.getStartDateTime());
            }
            if(actiHija.getTipoActividad()==2){
                actiHija.getObra().setFechaInicio(actiHija.getStartDateTime());
                actiHija.getObra().setFechaFin(actiHija.startDateTime);
            }else if(actiHija.getTipoActividad()==3){
                actiHija.getContrato().setDatefechafin(actiHija.getEndDateTime());
            }
            props.startDateTime().setValue(actiHija, fecha);

            taskStore.update(actiHija);

        }
    }

    public static void modifi(ActividadobraDTO actiObraInicial, ActividadobraDTO actividadObraFinal, int etapaModificar, TreeStore<ActividadobraDTO> taskStore) {
        taskStore.getChildren(actiObraInicial);
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

    public static void enlazarActividadConDependencia(List<DependenciaDTO> lstDependencias, List<ActividadobraDTO> lstActividades) {
        for (DependenciaDTO dependencia : lstDependencias) {
            buscarActividadDependencia(dependencia.getFromId(), dependencia, lstActividades, 1);
        }
    }

    //busca donde esa dependencia la actividadobra origen y l destino
    //y agregarla a la respectiva lista
    public static void buscarActividadDependencia(String actibuscada, DependenciaDTO dependencia, List<ActividadobraDTO> lstActividades, int lstDependencia) {
        for (ActividadobraDTO act : lstActividades) {
            if (act.getId().equals(actibuscada)) {
                act.getDependenciasForFkActividadOrigen().add(dependencia);

            }
        }

    }

    public static ActividadobraDTO obtenerActividadDeRaiz(int i, ContratoDTO contratoDto) {
        Iterator it = contratoDto.getActividadobras().iterator();
        ActividadobraDTO actiRaiz = (ActividadobraDTO) it.next();
        return actiRaiz.getChildren().get(i);
    }

    public static void modificarFechaInicioConvenio(ContratoDTO convenioDTO, Date fechaInicio, Date fechaFin) {
        Iterator it = convenioDTO.getActividadobras().iterator();
        ActividadobraDTO ac = (ActividadobraDTO) it.next();
        ac.setStartDateTime(fechaInicio);
        ac.setEndDateTime(fechaFin);
    }
        }
