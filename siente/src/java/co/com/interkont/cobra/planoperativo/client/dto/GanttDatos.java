/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client.dto;

import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAble;
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAbleAsync;
import com.gantt.client.Gantt;
import com.gantt.client.config.GanttConfig.DependencyType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.CurrencyData;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.i18n.client.impl.CurrencyDataImpl;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.sencha.gxt.core.client.util.DateWrapper;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.TreeStore;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase para manejar los datos que componen el Gantt del Plan Operativo
 *
 * @author desarrollo2
 */
public class GanttDatos {

    private static CobraGwtServiceAbleAsync service = GWT.create(CobraGwtServiceAble.class);
    private static Date fechaComparar;
    private static String msg;

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
        convenio.setDatefechaactaini(obtenerActividadDeRaiz(0, convenio).getChildren().get(0).getStartDateTime());
        convenio.setDatefechafin(lista.get(0).endDateTime);
        convenio.setIntduraciondias(lista.get(0).duration);
        convenio.getActividadobras().clear();
        convenio.getActividadobras().add(lista.get(0));
        for (DependenciaDTO d : lstDependencias) {
            DependenciaDTO dep = new DependenciaDTO();
            dep.setId("" + d.getId());
            dep.setFromId(d.fromId);
            dep.setToId(d.toId);
            dep.setType(d.type);
            dep.setActividadFrom(d.getActividadFrom());
            dep.setActividadTo(d.getActividadTo());
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

    public static void modificarFechaFin(ActividadobraDTO actividadPadre, TreeStore<ActividadobraDTO> taskStore, ListStore<DependenciaDTO> depsStore,ActividadobraDTOProps props, ContratoDTO contrato) {

//        if (actividadPadre != null) {
//            //if (actividadPadre.getTipoActividad() != 1) {
//            service.setLog(" modificando "+actividadPadre.getName(), null);
//                List<ActividadobraDTO> listaHijas = taskStore.getChildren(actividadPadre);
//                if (!listaHijas.isEmpty()) {
//                    Date menor = obtenerMenorFechaInicio(listaHijas);
//                    Date mayor = obtenerMayorFechaFin(listaHijas);
//                    if (menor != null && mayor != null) {
//                        int duracion = CalendarUtil.getDaysBetween(menor, mayor) + 1;
//                        props.duration().setValue(actividadPadre, duracion);
//                        DateWrapper dw = new DateWrapper(actividadPadre.getStartDateTime()).clearTime();
////                       int diferenciaduracion=0;
////                        if (mayor.compareTo(actividadPadre.getEndDateTime()) > 0) {
////                            diferenciaduracion = CalendarUtil.getDaysBetween(actividadPadre.getEndDateTime(),mayor);
////                        }
////                        service.setLog("Diferencia "+diferenciaduracion, null);
//                        props.endDateTime().setValue(actividadPadre, dw.addDays(actividadPadre.getDuration() - 1).asDate());
//                        
//                        
//                                   
//                        //if (mayor.compareTo(actividadPadre.getEndDateTime()) > 0) {
//                            //Obtener hermanos
//                        
//                            //List<ActividadobraDTO> listaact = taskStore.getParent(actividadPadre).getChildren();
////                        if(diferenciaduracion>0)
////                        {    
////                        List<ActividadobraDTO> listaact = encontrarActividadDestinoDependeciaFinInicio(depsStore, actividadPadre.getId(), diferenciaduracion);
////                            for (ActividadobraDTO acto : listaact) {
////                                service.setLog("Actividadpadre " + acto.getName(), null);
////                                service.setLog("Inicio " + acto.getStartDateTime(), null);
////                                service.setLog("Fin " + acto.getEndDateTime(), null);
////                                if(acto.getChildren().isEmpty())
////                                {
////                                     modificarFechaFin(acto, taskStore, depsStore, props, contrato);
////                                }
//////                                List<ActividadobraDTO> listaHijashermanos = taskStore.getChildren(acto);
//////                                if (listaHijas.isEmpty()) {
//////
//////                                }
////                            }
////                        }
//                        modificarFechaFin(taskStore.getParent(actividadPadre), taskStore, depsStore, props, contrato);
//                    }
//                }
//            //}
//        }

    }

    //    public static void buscarActividad(ContratoDTO contrato, int tipo, TreeStore<ActividadobraDTO> taskStore, ActividadobraDTOProps props) {
    //        List<ActividadobraDTO> lstActividades = new ArrayList<ActividadobraDTO>(contrato.getActividadobras());
    //        if (tipo == 1) {
    //            service.setLog("En buscar actividad 1:" + lstActividades.get(0).getChildren().get(1).getName(), null);
    //            odifi(lstActividades.get(0).getChildren().get(1), taskStore, props, contrato);
    //            fechaComparar = lstActividades.get(0).getChildren().get(1).endDateTime;
    //            service.setLog("En buscar actividad liquidacion:", null);
    //            odifi(lstActividades.get(0).getChildren().get(2), taskStore, props, contrato);
    //        } else {
    //            odifi(lstActividades.get(0).getChildren().get(2), taskStore, props, contrato);
    //        }
    //    }
    //    public static void odifi(ActividadobraDTO act, TreeStore<ActividadobraDTO> taskStore, ActividadobraDTOProps props, ContratoDTO contrato) {
    //        m(act, taskStore, props, contrato);
    //
    //        if (!act.getChildren().isEmpty()) {
    //            for (ActividadobraDTO actiHija : act.getChildren()) {
    //                odifi(actiHija, taskStore, props, contrato);
    //            }
    //        }
    //    }
    //    public static void m(ActividadobraDTO actiHija, TreeStore<ActividadobraDTO> taskStore, ActividadobraDTOProps props, ContratoDTO contrato) {
    //
    //        /*verifico el sentido en que tengo que hacer el movimiento de las
    //         * actividades si necesito aumentar la fecha de inicio o disminuirla*/
    //        if (fechaComparar.compareTo(actiHija.getStartDateTime()) > 0) {
    //
    //            int duracion = CalendarUtil.getDaysBetween(actiHija.getStartDateTime(), fechaComparar);
    //            Date fecha = CalendarUtil.copyDate(actiHija.getStartDateTime());
    //            CalendarUtil.addDaysToDate(fecha, duracion);
    //
    //            Date asigFin = CalendarUtil.copyDate(fecha);
    //            actiHija.setEndDateTime(asigFin);
    //
    //            CalendarUtil.addDaysToDate(actiHija.getEndDateTime(), actiHija.getDuration());
    //
    //            if (actiHija.getName().equals("Suscripción acta de inicio")) {
    //                actiHija.getContrato().setDatefechaactaini(actiHija.getStartDateTime());
    //
    //            }
    //            if (actiHija.getName().equals("Suscripción del contrato")) {
    //                actiHija.getContrato().setDatefechaini(actiHija.getStartDateTime());
    //            }
    //            if (actiHija.getTipoActividad() == 2) {
    //                actiHija.getObra().setFechaInicio(actiHija.getStartDateTime());
    //                actiHija.getObra().setFechaFin(actiHija.startDateTime);
    //            } else if (actiHija.getTipoActividad() == 3) {
    //                actiHija.getContrato().setDatefechafin(actiHija.getEndDateTime());
    //            }
    //            props.startDateTime().setValue(actiHija, fecha);
    //
    //            taskStore.update(actiHija);
    //
    //        }
    //    }
    //    public static void modifi(ActividadobraDTO actiObraInicial, ActividadobraDTO actividadObraFinal, int etapaModificar, TreeStore<ActividadobraDTO> taskStore) {
    //        taskStore.getChildren(actiObraInicial);
    //    }
    public static Date obtenerMenorFechaInicio(List<ActividadobraDTO> listaHijas) {
        if (!listaHijas.isEmpty()) {
            Date menor = listaHijas.get(0).getStartDateTime();
            for (int i = 1; i < listaHijas.size(); i++) {
                if (listaHijas.get(i).getStartDateTime().compareTo(menor) < 0) {
                    menor = listaHijas.get(i).getStartDateTime();
                }
            }
            return CalendarUtil.copyDate(menor);
        }
        return null;
    }

    public static Date obtenerMayorFechaFin(List<ActividadobraDTO> listaHijas) {
        if (!listaHijas.isEmpty()) {
            Date mayor = listaHijas.get(0).getEndDateTime();
            for (int i = 1; i < listaHijas.size(); i++) {
                if (listaHijas.get(i).getEndDateTime().compareTo(mayor) > 0) {
                    mayor = listaHijas.get(i).getEndDateTime();
                }
            }
            return CalendarUtil.copyDate(mayor);
        }
        return null;
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

//    public static void modificarFechaInicioConvenio(ContratoDTO convenioDTO, Date fechaInicio, Date fechaFin) {
//        Iterator it = convenioDTO.getActividadobras().iterator();
//        ActividadobraDTO ac = (ActividadobraDTO) it.next();
//        ac.setStartDateTime(fechaInicio);
//        ac.setEndDateTime(fechaFin);
//    }
    public static DependenciaDTO crearDependencia(ActividadobraDTO actividadFrom, ActividadobraDTO actividadTo) {
        DependenciaDTO dep = new DependenciaDTO();
        dep.setId("" + dep.hashCode());
        dep.setFromId(actividadFrom.getId());
        dep.setToId(actividadTo.getId());
        dep.setType(DependencyType.ENDtoSTART);
        dep.setActividadFrom(actividadFrom);
        dep.setActividadTo(actividadTo);
        dep.setIsobligatoria(true);
        return dep;
    }

    public static Boolean verificarModificacionFechasContrato(TreeStore<ActividadobraDTO> taskStore, ActividadobraDTO actividadSeleccionada, ActividadobraDTO actividadSConvenio) {
        boolean error = false;
        msg = "";

        if (taskStore.getParent(actividadSeleccionada).getName().equals("Precontractual")) {
            ActividadobraDTO actividadPadrePrecontractual = taskStore.getParent(actividadSeleccionada);

            //porque etapa precontractual inicia con la fecha del contrato
            //posicion 0
            if (actividadSeleccionada.getName().equals("Revisión técnica de documentos")) {
                if (actividadSeleccionada.getStartDateTime().compareTo(actividadPadrePrecontractual.getChildren().get(1).getStartDateTime()) > 0) {
                    setMsg(getMsg() + "La fecha de inicio no puede ser mayor que la actividad Elaboración de pliegos de condiciones:" + darFormatoAfecha(actividadPadrePrecontractual.getChildren().get(1).getStartDateTime()));
                    error = true;
                }
                if (actividadSeleccionada.getStartDateTime().compareTo(actividadPadrePrecontractual.getStartDateTime()) < 0) {
                    setMsg(getMsg() + "La fecha de inicio no puede ser menor que la actividad Precontractual:" + darFormatoAfecha(actividadPadrePrecontractual.getStartDateTime()));
                    error = true;
                }
                if (actividadSeleccionada.getEndDateTime().compareTo(taskStore.getParent(actividadPadrePrecontractual).getEndDateTime()) > 0) {
                    setMsg(getMsg() + "La fecha de fin no puede ser mayor que la fecha de fin del contrato:" + darFormatoAfecha(taskStore.getParent(actividadPadrePrecontractual).getEndDateTime()));
                    error = true;
                }
                if (actividadSeleccionada.getEndDateTime().compareTo(actividadPadrePrecontractual.getChildren().get(1).getStartDateTime()) > 0) {
                    setMsg(getMsg() + "La fecha de fin no puede ser mayor que la fecha de inicio de Elaboración de pliegos de condiciones:" + darFormatoAfecha(actividadPadrePrecontractual.getChildren().get(1).getStartDateTime()));
                    error = true;
                }

                //posicion 1
            } else if (actividadSeleccionada.getName().equals("Elaboración de pliegos de condiciones")) {
                if (actividadSeleccionada.getStartDateTime().compareTo(actividadPadrePrecontractual.getChildren().get(0).getEndDateTime()) < 0) {
                    setMsg(getMsg() + "La fecha de inicio no puede ser menor que la Revisión técnica de documentos:" + darFormatoAfecha(actividadPadrePrecontractual.getChildren().get(0).getEndDateTime()));
                    error = true;
                }
                if (actividadSeleccionada.getStartDateTime().compareTo(actividadPadrePrecontractual.getChildren().get(2).getStartDateTime()) > 0) {
                    setMsg(getMsg() + "La fecha de inicio no puede ser mayor que la Evaluación de propuestas:" + darFormatoAfecha(actividadPadrePrecontractual.getChildren().get(2).getStartDateTime()));
                    error = true;
                }
                if (actividadSeleccionada.getEndDateTime().compareTo(actividadPadrePrecontractual.getChildren().get(2).getStartDateTime()) > 0) {
                    setMsg(getMsg() + "La fecha de fin no puede ser mayor que la Evaluación de propuestas:" + darFormatoAfecha(actividadPadrePrecontractual.getChildren().get(2).getStartDateTime()));
                    error = true;
                }
                //posicion 2
            } else if (actividadSeleccionada.getName().equals("Evaluación de propuestas")) {
                if (actividadSeleccionada.getStartDateTime().compareTo(actividadPadrePrecontractual.getChildren().get(1).getEndDateTime()) < 0) {
                    setMsg(getMsg() + "La fecha de inicio no puede ser menor que la Elaboración de pliegos de condiciones:" + darFormatoAfecha(actividadPadrePrecontractual.getChildren().get(1).getEndDateTime()));
                    error = true;
                }
                if (actividadSeleccionada.getStartDateTime().compareTo(actividadPadrePrecontractual.getChildren().get(3).getStartDateTime()) > 0) {
                    setMsg(getMsg() + "La fecha de inicio no puede ser mayor que la Elaboración de contratos:" + darFormatoAfecha(actividadPadrePrecontractual.getChildren().get(3).getStartDateTime()));
                    error = true;
                }
                if (actividadSeleccionada.getEndDateTime().compareTo(actividadPadrePrecontractual.getChildren().get(3).getStartDateTime()) > 0) {
                    setMsg(getMsg() + "La fecha de fin no puede ser mayor que la Elaboración de contratos:" + darFormatoAfecha(actividadPadrePrecontractual.getChildren().get(3).getStartDateTime()));
                    error = true;
                }
                //posicion 3
            } else {
                service.setLog("modificar el ultimo contractual", null);
                if (actividadSeleccionada.getStartDateTime().compareTo(actividadPadrePrecontractual.getChildren().get(2).getEndDateTime()) < 0) {
                    setMsg(getMsg() + "La fecha de inicio no puede ser menor que la Evaluación de propuestas:" + darFormatoAfecha(actividadPadrePrecontractual.getChildren().get(2).getEndDateTime()));
                    error = true;
                }

                if (actividadSeleccionada.getStartDateTime().compareTo(taskStore.getParent(actividadPadrePrecontractual).getChildren().get(1).getStartDateTime()) > 0) {
                    setMsg(getMsg() + "La fecha de inicio no puede ser mayor que la fecha inicio de la etapa contractual:" + darFormatoAfecha(taskStore.getParent(actividadPadrePrecontractual).getChildren().get(1).getStartDateTime()));
                    error = true;
                }

                if (actividadSeleccionada.getEndDateTime().compareTo(taskStore.getParent(actividadPadrePrecontractual).getChildren().get(1).getStartDateTime()) > 0) {
                    setMsg(getMsg() + "La fecha de fin no puede ser mayor que la fecha inicio de la etapa contractual:" + darFormatoAfecha(taskStore.getParent(actividadPadrePrecontractual).getChildren().get(1).getStartDateTime()));
                    error = true;
                }
            }

        } else if (taskStore.getParent(actividadSeleccionada).getName().equals("Contractual")) {

            if (actividadSeleccionada.getTipoActividad() == 6) {
                if (actividadSeleccionada.getName().equals("Suscripción del contrato")) {
                    if (actividadSeleccionada.getStartDateTime().compareTo(taskStore.getParent(actividadSeleccionada).getChildren().get(1).getStartDateTime()) > 0) {
                        error = true;
                        setMsg(getMsg() + "La fecha inicio de la Suscripción del contrato no puede ser mayor que la fecha de inicio de la Suscripción del acta:" + darFormatoAfecha(taskStore.getParent(actividadSeleccionada).getChildren().get(1).getStartDateTime()));

                    } else {

                        if (actividadSeleccionada.getStartDateTime().compareTo(taskStore.getParent(taskStore.getParent(actividadSeleccionada)).getChildren().get(0).getEndDateTime()) < 0) {
                            error = true;
                            setMsg(getMsg() + "La fecha inicio de la Suscripción del contrato no puede ser menor que fecha fin de etapa precontactual:" + darFormatoAfecha(taskStore.getParent(taskStore.getParent(actividadSeleccionada)).getChildren().get(0).getEndDateTime()));

                        }
                    }
                    if (actividadSeleccionada.getEndDateTime().compareTo(taskStore.getParent(actividadSeleccionada).getChildren().get(1).getStartDateTime()) > 0) {
                        error = true;
                        setMsg(getMsg() + "La fecha fin de la Suscripción del contrato no puede ser mayor que la fecha de inicio de la Suscripción del acta de inicio:" + darFormatoAfecha(taskStore.getParent(actividadSeleccionada).getChildren().get(1).getStartDateTime()));
                    }

                } else if (actividadSeleccionada.getName().equals("Suscripción acta de inicio")) {
                    if (actividadSeleccionada.getStartDateTime().compareTo(taskStore.getParent(actividadSeleccionada).getChildren().get(0).getEndDateTime()) < 0) {
                        error = true;
                        setMsg(getMsg() + "La fecha inicio de la Suscripción del acta no puede ser menor que la fecha de fin de la Suscripción del contrato:" + darFormatoAfecha(taskStore.getParent(actividadSeleccionada).getChildren().get(0).getEndDateTime()));

                    }
                    ActividadobraDTO actiLiquidacion = taskStore.getParent(taskStore.getParent(actividadSeleccionada)).getChildren().get(2);
                    if (!actiLiquidacion.getChildren().isEmpty()) {
                        Date menorHijosLiquidacion = obtenerMenorFechaInicio(actiLiquidacion.getChildren());
                        service.setLog("en suscripcion", null);

                        if (actividadSeleccionada.getStartDateTime().compareTo(menorHijosLiquidacion) > 0) {
                            error = true;
                            String df1 = DateTimeFormat.getShortDateFormat().format(menorHijosLiquidacion);
                            setMsg(getMsg() + "La fecha inicio de la Suscripción del acta no puede ser mayor que el menor de los hijos de la etapa de liquidación:" + df1);
                        }

                        if (actividadSeleccionada.getEndDateTime().compareTo(menorHijosLiquidacion) > 0) {
                            error = true;
                            String df1 = DateTimeFormat.getShortDateFormat().format(menorHijosLiquidacion);
                            setMsg(getMsg() + "La fecha fin de la Suscripción del acta no puede ser mayor que el menor de los hijos de la etapa de liquidación:" + df1);
                        }

                    } else {
                        service.setLog("entre en el elsee de aca", null);
                        error = true;
                        validarModificacionFechasEtapaContractual(actividadSeleccionada, actividadSConvenio);
                    }
                } else {
                    ActividadobraDTO actiLiquidacion = taskStore.getParent(taskStore.getParent(actividadSeleccionada)).getChildren().get(2);
                    if (!actiLiquidacion.getChildren().isEmpty()) {
                        Date menorHijosLiquidacion = obtenerMenorFechaInicio(actiLiquidacion.getChildren());
                        if (actividadSeleccionada.getStartDateTime().compareTo(menorHijosLiquidacion) > 0) {
                            error = true;
                            String df1 = DateTimeFormat.getShortDateFormat().format(menorHijosLiquidacion);
                            setMsg(getMsg() + "La fecha inicio de la actividad no puede ser mayor que el menor de los hijos de la etapa de liquidación:" + df1);
                        }

                        if (actividadSeleccionada.getEndDateTime().compareTo(menorHijosLiquidacion) > 0) {
                            error = true;
                            String df1 = DateTimeFormat.getShortDateFormat().format(menorHijosLiquidacion);
                            setMsg(getMsg() + "La fecha inicio no puede ser mayor que el menor de los hijos de la etapa de liquidación:" + df1);
                        }
                    } else {
                        error = true;
                        validarModificacionFechasEtapaContractual(actividadSeleccionada, actividadSConvenio);

                    }
                }

            }
        } else if (taskStore.getParent(actividadSeleccionada).getName().equals("Liquidaciones")) {
            ActividadobraDTO actiEjecucion = taskStore.getParent(taskStore.getParent(actividadSeleccionada)).getChildren().get(1);
            if (actiEjecucion.getEndDateTime().compareTo(actividadSeleccionada.getStartDateTime()) > 0) {
                error = true;
                String df1 = DateTimeFormat.getShortDateFormat().format(actiEjecucion.getStartDateTime());
                setMsg(getMsg() + "La fecha inicio de la actividad no puede ser menor que la fecha fin de la etapa de contractual:" + df1);
            } else {
                if (actiEjecucion.getStartDateTime().compareTo(actividadSeleccionada.getStartDateTime()) > 0) {
                    error = true;
                    String df1 = DateTimeFormat.getShortDateFormat().format(actiEjecucion.getStartDateTime());
                    setMsg(getMsg() + "La fecha inicio de la actividad no puede ser menor que  la etapa de contractual:" + df1);
                } else {
                    ActividadobraDTO actiLiquiConvenio = actividadSConvenio.getChildren().get(2);
                    if (!actiLiquiConvenio.getChildren().isEmpty()) {
                        Date menorHijosLiquidacionConvenio = obtenerMenorFechaInicio(actiLiquiConvenio.getChildren());
                        String df1 = DateTimeFormat.getShortDateFormat().format(menorHijosLiquidacionConvenio);
                        if (actividadSeleccionada.getStartDateTime().compareTo(menorHijosLiquidacionConvenio) > 0) {
                            error = true;
                            setMsg(getMsg() + "La fecha inicio de la actividad no puede ser superior a la actividad menor de la etapa de liquidacion del convenio macro:" + df1);
                        } else {
                            if (actividadSeleccionada.getEndDateTime().compareTo(menorHijosLiquidacionConvenio) > 0) {
                                error = true;
                                setMsg(getMsg() + "La fecha inicio de la actividad no puede ser superior a la actividad menor de la etapa de liquidacion del convenio macro:" + df1);
                            }
                        }
                    }
                }
            }
        }
        return error;
    }

//    public static boolean validacionParaTodasLasActividades(ActividadobraDTO ac, ContratoDTO convenioDTO) {
//        if (ac.getStartDateTime().compareTo(convenioDTO.getDatefechaini()) < 0) {
//            return false;
//        }
//        if (ac.getEndDateTime().compareTo(convenioDTO.getDatefechafin()) > 0) {
//            return false;
//        }
//        return true;
//    }

    public static Boolean verificacionModificacionFechaFin(TreeStore<ActividadobraDTO> taskStore, ActividadobraDTO actividadSeleccionada) {
        if (actividadSeleccionada.getTipoActividad() == 2) {
        } else if (actividadSeleccionada.getTipoActividad() == 3) {
        }
        return false;

    }

    public static Boolean validacionCadenaPredecesoras(String cadena) {
        if (cadena != null) {
            RegExp pat = RegExp.compile("^[\\d+]$|(([\\d]+)([,])?(([\\d]+)$))");
            if (pat.test(cadena)) {
                return true;
            }
        } else {
            return true;
        }
        return false;
    }

    public static List<Integer> eliminarPredecesor(ActividadobraDTO acti, String predecesorAnterior, ActividadobraDTO actividadAnterior) {
        service.setLog("en eliminar predecesores" + predecesorAnterior, null);
        List<Integer> lstPredecesoresEliminados = null;
        if (predecesorAnterior != null) {
            if (!predecesorAnterior.isEmpty() && acti.getPredecesor() != null) {
                service.setLog("en eliminar predecesores anteriores:" + predecesorAnterior, null);
                lstPredecesoresEliminados = new ArrayList<Integer>();
                String[] tempNuevo = acti.getPredecesor().split(",");
                String[] tempAnterior = predecesorAnterior.split(",");
                for (int i = 0; i < tempAnterior.length; i++) {
                    if (!contienePredecesor(tempNuevo, tempAnterior[i], acti)) {
                        lstPredecesoresEliminados.add(Integer.parseInt(tempAnterior[i]));
                        acti.getLstPredecesores().remove(Integer.parseInt(tempAnterior[i]));
                        actividadAnterior.setPredecesor(actividadAnterior.getPredecesor().replace("" + tempAnterior[i], ""));
                    }
                }
            } else {
                service.setLog("aca en else de eliminar", null);
                lstPredecesoresEliminados = new ArrayList<Integer>();
                String[] tempAnterior = predecesorAnterior.split(",");
                for (int i = 0; i < tempAnterior.length; i++) {
                    lstPredecesoresEliminados.add(Integer.parseInt(tempAnterior[i]));
                    acti.getLstPredecesores().remove(Integer.parseInt(tempAnterior[i]));
                }
            }
        }
        service.setLog("lsta predecesores" + acti.getLstPredecesores().size(), null);
        return lstPredecesoresEliminados;
    }

    public static boolean contienePredecesor(String[] tempNuevo, String predecesor, ActividadobraDTO acti) {
        boolean estaPredecesor = false;
        for (int i = 0; i < tempNuevo.length; i++) {
            if (tempNuevo[i].equals(predecesor)) {
                estaPredecesor = true;
            }
        }
        return estaPredecesor;
    }

    public static Map<Boolean, Set<Integer>> separarPredecesores(ActividadobraDTO acti) {
        Map<Boolean, Set<Integer>> mapaPredecesores = new HashMap<Boolean, Set<Integer>>();
        if (acti.getPredecesor() != null) {
            String[] temp;
            temp = acti.getPredecesor().split(",");
            Set<Integer> lstTemporalPredecesores = new HashSet<Integer>();
            boolean boolEstaPredecesor = false;
            for (int i = 0; i < temp.length; i++) {
                Integer predecesor = Integer.parseInt(temp[i]);
                if (!acti.getLstPredecesores().contains(predecesor)) {
                    lstTemporalPredecesores.add(predecesor);
                } else {
                    if (cantidadVecesPredecesor(temp, predecesor) > 1) {
                        boolEstaPredecesor = true;
                    }
                }
            }

            if (!boolEstaPredecesor) {
                acti.getLstPredecesores().addAll(lstTemporalPredecesores);
            }
            mapaPredecesores.put(boolEstaPredecesor, lstTemporalPredecesores);
        }
        return mapaPredecesores;
    }

    public static int cantidadVecesPredecesor(String[] temp, int predecesor) {
        int contador = 0;
        for (int i = 0; i < temp.length; i++) {
            if (Integer.parseInt(temp[i]) == predecesor) {
                contador++;
            }
        }
        return contador;
    }

    public static String validarHijosLiquidacionConvenioMacro(ActividadobraDTO actiSeleccionada, ContratoDTO convenio, TreeStore<ActividadobraDTO> taskStore, ActividadobraDTO actividadAnterior) {
        String mensaje = "continuar";
        if (actiSeleccionada.getStartDateTime().compareTo(convenio.getDatefechafin()) >= 0) {
            mensaje = "la fecha de la actividad no puede ser superior a la fecha final del convenio:" + darFormatoAfecha(convenio.getDatefechafin());

        }
        if (actiSeleccionada.getEndDateTime().compareTo(actiSeleccionada.getStartDateTime()) <= 0) {
            mensaje = "la fecha fin de la actividad no puede ser inferior a la fecha de inicio de la misma:" + darFormatoAfecha(actiSeleccionada.getStartDateTime());
        } else {
            if (actiSeleccionada.getStartDateTime().compareTo(actiSeleccionada.getEndDateTime()) >= 0) {
                mensaje = "la fecha inicio de la actividad no puede ser superior a la fecha de fin de la misma:" + darFormatoAfecha(actiSeleccionada.getEndDateTime());
            } else {
                if (actiSeleccionada.getEndDateTime().compareTo(convenio.getDatefechafin()) > 0) {
                    mensaje = "la fecha de la actividad no puede ser superior a la fecha final del convenio:" + darFormatoAfecha(convenio.getDatefechafin());
                } else {
                    ActividadobraDTO actiEjecucionConvenio = taskStore.getParent(actiSeleccionada).getChildren().get(1);
                    if (actiSeleccionada.getStartDateTime().compareTo(actiEjecucionConvenio.getEndDateTime()) < 0) {
                        mensaje = "la fecha de la actividad no puede ser inferior a la fecha final de la ejecucion del convenio:" + darFormatoAfecha(actiEjecucionConvenio.getEndDateTime());
                    } else {
                        if (actiSeleccionada.getEndDateTime().compareTo(actiEjecucionConvenio.getEndDateTime()) < 0) {
                            mensaje = "la fecha de la actividad no puede ser inferior a la fecha final de la ejecucion del convenio:" + darFormatoAfecha(actiEjecucionConvenio.getEndDateTime());
                        }
                    }

                }
            }

        }

        return mensaje;

    }

    public static List calcularDuracionActividad(ActividadobraDTO actividadPadre, Date fechaInicioHijo, Date fechaFinHijo) {
        List lstInfo = new ArrayList();
        Date fechaInicio = actividadPadre.getStartDateTime();
        service.setLog("Padre FI:" + fechaInicio + "hijo FI:" + fechaInicioHijo, null);
        Date fechaFin = actividadPadre.getEndDateTime();
        service.setLog("Padre FF:" + fechaFin + "Hijo FF" + fechaFinHijo, null);
        if (fechaInicioHijo.compareTo(actividadPadre.getStartDateTime()) < 0) {
            fechaInicio = fechaInicioHijo;
        }
        if (fechaFinHijo.compareTo(actividadPadre.getEndDateTime()) > 0) {
            fechaFin = fechaFinHijo;
        }
        service.setLog("FI:" + fechaInicio, null);
        service.setLog("FF:" + fechaFin, null);
        lstInfo.add((CalendarUtil.getDaysBetween(fechaInicio, fechaFin)));
        lstInfo.add(fechaInicio);
        lstInfo.add(fechaFin);

        return lstInfo;
    }

    /**
     * @return the msg
     */
    public static String getMsg() {
        return msg;
    }

    /**
     * @param aMsg the msg to set
     */
    public static void setMsg(String aMsg) {
        msg = aMsg;
    }

    public static String darFormatoAfecha(Date fecha) {
        service.setLog("dando formato a fecha " + fecha.toString(), null);
        return DateTimeFormat.getShortDateFormat().format(fecha);
    }

    public static void asignarNumeracion(TreeStore<ActividadobraDTO> taskStore, int numeracionAnterior, int tipo) {
        Integer numeracion = new Integer(numeracionAnterior + 1);
        service.setLog("numeracon anterior:" + numeracion, null);
        List<ActividadobraDTO> lstActividades = new ArrayList<ActividadobraDTO>();
        cargarActividadesTaskStore(taskStore.getAll().get(0), lstActividades);

        for (ActividadobraDTO acti : lstActividades) {
            service.setLog("como coloca la lista" + acti.getName(), null);
            if (acti.getNumeracion() > numeracionAnterior) {
                service.setLog("aca tengo cumplo" + numeracion + "en num ante:" + acti.getNumeracion(), null);
                acti.setNumeracion(numeracion);
                reasignarPredecesores(acti, numeracionAnterior, tipo);
                numeracion++;
            }

        }

    }

    public static void reasignarPredecesores(ActividadobraDTO actividad, int numeracionAnterior, int tipo) {
        Set<Integer> lstPredecesores = new HashSet<Integer>();
        for (Integer numPredecesor : actividad.getLstPredecesores()) {
            if (numPredecesor > numeracionAnterior) {
                if (tipo != 3) {
                    numPredecesor++;
                } else {
                    numPredecesor = numPredecesor + 10;
                }
            }
            service.setLog("predecesor en L:" + numPredecesor, null);
            lstPredecesores.add(numPredecesor);
        }
        if (!lstPredecesores.isEmpty()) {
            actividad.setLstPredecesores(lstPredecesores);
            modificarPredecesor(actividad);
        }
    }

    public static void modificarPredecesor(ActividadobraDTO acti) {
        StringBuilder predecesorNuevo = new StringBuilder();
        Iterator it = acti.getLstPredecesores().iterator();
        for (int i = 0; i < acti.getLstPredecesores().size(); i++) {
            if (it.hasNext()) {
                predecesorNuevo.append(it.next());
                if (i != acti.getLstPredecesores().size() - 1) {
                    predecesorNuevo.append(',');
                }
            }
        }
        service.setLog("modif:" + predecesorNuevo.toString(), null);
        acti.setPredecesor(predecesorNuevo.toString());

    }

    public static void cargarActividadesTaskStore(ActividadobraDTO actiRaiz, List<ActividadobraDTO> lstActividades) {
        for (ActividadobraDTO acti : actiRaiz.getChildren()) {
            lstActividades.add(acti);
            if (acti.hasChildren()) {
                cargarActividadesTaskStore(acti, lstActividades);
            }

        }
    }

    public static int modificarEnCascadaNumeracion(ActividadobraDTO act) {
        int numeracionActual = 0;
        if (!act.hasChildren()) {
            numeracionActual = act.getNumeracion();
            service.setLog("ultimo 1:" + numeracionActual, null);
        } else {
            service.setLog("entre en else", null);
            int ultimo = act.getChildren().size() - 1;
            service.setLog("ultimo sd:" + ultimo, null);
            numeracionActual = act.getChildren().get(ultimo).getNumeracion();
            service.setLog("ultimo 2:" + numeracionActual, null);
        }
        return numeracionActual;
    }

    public static boolean validarNombreActividad(String nombre, TreeStore<ActividadobraDTO> taskStore) {
        for (ActividadobraDTO act : taskStore.getAll()) {
            if (act.getName().equals(nombre)) {
                return false;
            }
        }
        return true;

    }

    public static void guardarBorradorConvenio(final ContratoDTO convenio, final CobraGwtServiceAbleAsync service, Gantt<ActividadobraDTO, DependenciaDTO> gantt) {
        service.setContratoDto(estructurarDatosConvenio(convenio, gantt.getTreeStore(), service, gantt.getDependencyStore()), new AsyncCallback<Boolean>() {
            @Override
            public void onFailure(Throwable caught) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onSuccess(Boolean result) {
                service.setNavegacion(2, new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public void onSuccess(Boolean result) {
                        service.setGuardarconvenio(1, new AsyncCallback<Boolean>() {
                            @Override
                            public void onFailure(Throwable caught) {
                                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                            }

                            @Override
                            public void onSuccess(Boolean result) {
                                com.google.gwt.user.client.Window.open(retornarNuevoContrato(), "_parent", retornarConfiguracionPagina());
                            }
                        });
                    }
                });
            }
        });

    }

    public static String retornarNuevoContrato() {

        return "/zoom/Supervisor/nuevoContratoPlanOperativo.xhtml";

    }

    public static String retornarConfiguracionPagina() {
        return "menubar=si, location=false, resizable=no, scrollbars=si, status=no, dependent=true";
    }

    public static String obtenerFormatoNumero(BigDecimal valor) {
        NumberFormat fmt = NumberFormat.getDecimalFormat();
        return fmt.format(valor);
    }

    public static Date obtenerMenorFechaActividad(ActividadobraDTO actividadSeleccionada) {

        if (actividadSeleccionada.hasChildren()) {
            return GanttDatos.obtenerMenorFechaInicio(actividadSeleccionada.getChildren());

        }
        return null;
    }

    public static boolean validarConLiquidacionConvenio(ActividadobraDTO actiSeleccionada, ContratoDTO convenioDTO) {
        Date fechaMenor = obtenerMenorFechaActividad(obtenerActividadDeRaiz(2, convenioDTO));
        service.setLog("en valida pry:" + fechaMenor, null);
        if (fechaMenor != null) {
            service.setLog("fecha din del pry:" + actiSeleccionada.getStartDateTime(), null);
            if (actiSeleccionada.getStartDateTime().compareTo(fechaMenor) > 0) {
                return true;
            }
        }
        return false;

    }

//    public static boolean validarActividadaEstaDentroConvenio(ActividadobraDTO acti, ContratoDTO convenioDto, ActividadobraDTO actividadAnterior) {
//        service.setLog("entre aca en vvalida General", null);
//        if (acti.getStartDateTime().compareTo(convenioDto.getDatefechaini()) < 0) {
//            service.setLog("entre aca en vvalida General 1", null);
//            return true;
//        }
//        if (acti.getStartDateTime().compareTo(convenioDto.getDatefechafin()) >= 0) {
//            return true;
//        }
//        if (acti.getEndDateTime().compareTo(actividadAnterior.getEndDateTime()) != 0) {
//            if (acti.getEndDateTime().compareTo(convenioDto.getDatefechafin()) > 0) {
//                service.setLog("entre aca en vvalida General 2", null);
//                return true;
//            }
//        } else {
//            service.setLog("entre aca en vvalida General 4", null);
//            Date copiaFechaInicio = CalendarUtil.copyDate(acti.getStartDateTime());
//            CalendarUtil.addDaysToDate(copiaFechaInicio, acti.getDuration());
//            if (copiaFechaInicio.compareTo(convenioDto.getDatefechafin()) > 0) {
//                return true;
//            }
//        }
//        service.setLog("entre aca en vvalida General 3", null);
//        return false;
//
//    }
    public static void validarModificacionFechasEtapaContractual(ActividadobraDTO actividadSeleccionada, ActividadobraDTO actividadSConvenio) {
        if (actividadSeleccionada.getStartDateTime().compareTo(actividadSConvenio.getChildren().get(2).getStartDateTime()) >= 0) {
            service.setLog("entre en el elsee de aca 1", null);
            String df1 = DateTimeFormat.getShortDateFormat().format(actividadSConvenio.getChildren().get(2).getStartDateTime());
            setMsg(getMsg() + "La fecha inicio no puede ser mayor que la fecha inicio de la etapa de liquidación:" + df1);

        } else if (actividadSeleccionada.getEndDateTime().compareTo(actividadSConvenio.getChildren().get(2).getStartDateTime()) > 0) {
            service.setLog("entre en el elsee de aca 2", null);
            String df1 = DateTimeFormat.getShortDateFormat().format(actividadSConvenio.getChildren().get(2).getStartDateTime());
            setMsg(getMsg() + "La fecha fin no puede ser mayor que la fecha inicio de la etapa de liquidación:" + df1);
        }

    }

    public static FuenterecursosconvenioDTO buscarFuenteRecursos(String nombreEntidad, int vigencia, ContratoDTO convenio) {
        for (Iterator it = convenio.getFuenterecursosconvenios().iterator(); it.hasNext();) {
            FuenterecursosconvenioDTO fuente = (FuenterecursosconvenioDTO) it.next();
            service.setLog("en buscar" + fuente.getTercero().getStrnombrecompleto() + "vigencia:" + fuente.getVigencia(), null);
            if (fuente.getTercero().getStrnombrecompleto().equals(nombreEntidad) && fuente.getVigencia() == vigencia) {
                return fuente;
            }

        }
        return null;

    }

    public static String parserCurrencyLocale(Double number) {
        CurrencyData cu = new CurrencyDataImpl("COP", "$", 0);

        String format = NumberFormat.getCurrencyFormat(cu).format(number);

        return format;
    }

    public static String parserCurrencyLocale(BigDecimal number) {
        CurrencyData cu = new CurrencyDataImpl("COP", "$", 0);

        String format = NumberFormat.getCurrencyFormat(cu).format(number);
        return format;
    }

    public static List<ActividadobraDTO> encontrarActividadDestinoDependeciaFinInicio(ListStore<DependenciaDTO> depsStore, String id, int difduracion)
    {
        List<ActividadobraDTO> lista=new ArrayList<ActividadobraDTO>();
        for(DependenciaDTO dep:depsStore.getAll())
        {
            if(dep.getActividadFrom().getId().compareTo(id)==0 && dep.getType()==DependencyType.ENDtoSTART)
            {
                int dur=CalendarUtil.getDaysBetween(dep.getActividadFrom().getEndDateTime(), dep.getActividadTo().getStartDateTime());
                service.setLog("dur "+dur, null);
                if(dur<difduracion)
                {    
                lista.add(dep.getActividadTo());
                }
                else
                {
                    service.setLog("Hay espacio", null);
                }    
            }    
        }    
        return lista;
    }        
}
