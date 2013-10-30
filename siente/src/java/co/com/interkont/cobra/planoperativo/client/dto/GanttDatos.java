/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client.dto;

import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAble;
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAbleAsync;
import com.gantt.client.config.GanttConfig.DependencyType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.TreeStore;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
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
            fechaComparar = lstActividades.get(0).getChildren().get(1).endDateTime;
            service.setLog("En buscar actividad liquidacion:", null);
            odifi(lstActividades.get(0).getChildren().get(2), taskStore, props, contrato);
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
            if (actiHija.getTipoActividad() == 2) {
                actiHija.getObra().setFechaInicio(actiHija.getStartDateTime());
                actiHija.getObra().setFechaFin(actiHija.startDateTime);
            } else if (actiHija.getTipoActividad() == 3) {
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

    public static void modificarFechaInicioConvenio(ContratoDTO convenioDTO, Date fechaInicio, Date fechaFin) {
        Iterator it = convenioDTO.getActividadobras().iterator();
        ActividadobraDTO ac = (ActividadobraDTO) it.next();
        ac.setStartDateTime(fechaInicio);
        ac.setEndDateTime(fechaFin);
    }

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

    public static Boolean verificarModificacionFechasContrato(TreeStore<ActividadobraDTO> taskStore, ActividadobraDTO actividadSeleccionada) {
        boolean error = false;
        msg = "";
        if (taskStore.getParent(actividadSeleccionada).getName().equals("Precontractual")) {
            ActividadobraDTO actividadPadrePrecontractual = taskStore.getParent(actividadSeleccionada);

            //porque etapa precontractual inicia con la fecha del contrato
            //posicion 0
            if (actividadSeleccionada.getName().equals("Revisión técnica de documentos")) {
                if (actividadSeleccionada.getStartDateTime().compareTo(actividadPadrePrecontractual.getChildren().get(1).getStartDateTime()) > 0) {
                    setMsg(getMsg() + "La fecha de inicio no puede ser mayor que la actividad Elaboración de pliegos de condiciones");
                    error = true;
                }
                if (actividadSeleccionada.getStartDateTime().compareTo(actividadPadrePrecontractual.getStartDateTime()) < 0) {
                    setMsg(getMsg() + "La fecha de inicio no puede ser menor que la actividad Precontractual");
                    error = true;
                }
                if (actividadSeleccionada.getEndDateTime().compareTo(taskStore.getParent(actividadPadrePrecontractual).getEndDateTime()) > 0) {
                    setMsg(getMsg() + "La fecha de fin no puede ser mayor que la fecha de fin del contrato");
                    error = true;
                }
                if (actividadSeleccionada.getEndDateTime().compareTo(actividadPadrePrecontractual.getChildren().get(1).getStartDateTime()) > 0) {
                    setMsg(getMsg() + "La fecha de fin no puede ser mayor que la fecha de inicio de Elaboración de pliegos de condiciones");
                    error = true;
                }

                //posicion 1
            } else if (actividadSeleccionada.getName().equals("Elaboración de pliegos de condiciones")) {
                if (actividadSeleccionada.getStartDateTime().compareTo(actividadPadrePrecontractual.getChildren().get(0).getEndDateTime()) < 0) {
                    setMsg(getMsg() + "La fecha de inicio no puede ser menor que la Revisión técnica de documentos ");
                    error = true;
                }
                if (actividadSeleccionada.getStartDateTime().compareTo(actividadPadrePrecontractual.getChildren().get(2).getStartDateTime()) > 0) {
                    setMsg(getMsg() + "La fecha de inicio no puede ser mayor que la Evaluación de propuestas ");
                    error = true;
                }
                if (actividadSeleccionada.getEndDateTime().compareTo(actividadPadrePrecontractual.getChildren().get(2).getStartDateTime()) > 0) {
                    setMsg(getMsg() + "La fecha de fin no puede ser mayor que la Evaluación de propuestas");
                    error = true;
                }
                //posicion 2
            } else if (actividadSeleccionada.getName().equals("Evaluación de propuestas")) {
                if (actividadSeleccionada.getStartDateTime().compareTo(actividadPadrePrecontractual.getChildren().get(1).getEndDateTime()) < 0) {
                    setMsg(getMsg() + "La fecha de inicio no puede ser menor que la Elaboración de pliegos de condiciones ");
                    error = true;
                }
                if (actividadSeleccionada.getStartDateTime().compareTo(actividadPadrePrecontractual.getChildren().get(3).getStartDateTime()) > 0) {
                    setMsg(getMsg() + "La fecha de inicio no puede ser mayor que la Elaboración de contratos ");
                    error = true;
                }
                if (actividadSeleccionada.getEndDateTime().compareTo(actividadPadrePrecontractual.getChildren().get(3).getStartDateTime()) > 0) {
                    setMsg(getMsg() + "La fecha de fin no puede ser mayor que la Elaboración de contratos");
                    error = true;
                }
                //posicion 3
            } else {
                service.setLog("modificar el ultimo contractual", null);
                if (actividadSeleccionada.getStartDateTime().compareTo(actividadPadrePrecontractual.getChildren().get(2).getEndDateTime()) < 0) {
                    setMsg(getMsg() + "La fecha de inicio no puede ser menor que la Evaluación de propuestas ");
                    error = true;
                }

                if (actividadSeleccionada.getStartDateTime().compareTo(taskStore.getParent(actividadPadrePrecontractual).getChildren().get(1).getStartDateTime()) > 0) {
                    setMsg(getMsg() + "La fecha de inicio no puede ser mayor que la fecha inicio de la etapa contractual");
                    error = true;
                }

                if (actividadSeleccionada.getEndDateTime().compareTo(taskStore.getParent(actividadPadrePrecontractual).getChildren().get(1).getStartDateTime()) > 0) {
                    setMsg(getMsg() + "La fecha de fin no puede ser mayor que la fecha inicio de la etapa contractual");
                    error = true;
                }
            }

        } else if (taskStore.getParent(actividadSeleccionada).getName().equals("Contractual")) {

            if (actividadSeleccionada.getTipoActividad() == 6) {
                service.setLog("enter 6", null);
                if (actividadSeleccionada.getName().equals("Suscripcion del contrato")) {
                    if (actividadSeleccionada.getStartDateTime().compareTo(taskStore.getParent(actividadSeleccionada).getChildren().get(1).getStartDateTime()) > 0) {
                        error = true;
                        setMsg(getMsg() + "La fecha inicio de la Suscripcion del contrato no puede ser mayor que la fecha de inicio de la Suscripcion del acta");

                    } else {
                        ActividadobraDTO actiP = taskStore.getParent(actividadSeleccionada);
                        if (actividadSeleccionada.getStartDateTime().compareTo(taskStore.getParent(taskStore.getParent(actividadSeleccionada)).getChildren().get(0).getEndDateTime()) < 0) {
                            error = true;
                            setMsg(getMsg() + "La fecha inicio de la Suscripcion del contrato no puede ser menor que fecha fin de etapa precontractual");

                        }
                    }
                    if (actividadSeleccionada.getEndDateTime().compareTo(taskStore.getParent(actividadSeleccionada).getChildren().get(1).getStartDateTime()) > 0) {
                        error = true;
                        setMsg(getMsg() + "La fecha fin de la Suscripcion del contrato no puede ser mayor que la fecha de inicio de la Suscripcion del acta de inicio");
                    }

                } else if (actividadSeleccionada.getName().equals("Suscripcion acta de inicio")) {
                    service.setLog("en suscripicion acata ini", null);
                    if (actividadSeleccionada.getStartDateTime().compareTo(taskStore.getParent(actividadSeleccionada).getChildren().get(0).getEndDateTime()) < 0) {
                        error = true;
                        setMsg(getMsg() + "La fecha inicio de la Suscripcion del acta no puede ser menor que la fecha de fin de la Suscripcion del contrato");

                    }
                    ActividadobraDTO actiLiquidacion = taskStore.getParent(taskStore.getParent(actividadSeleccionada)).getChildren().get(2);
                    service.setLog("en suscripicion acata ini 1:" + actiLiquidacion.getName(), null);
                    if (!actiLiquidacion.getChildren().isEmpty()) {
                        Date menorHijosLiquidacion = obtenerMenorFechaInicio(actiLiquidacion.getChildren());
                        if (actividadSeleccionada.getStartDateTime().compareTo(menorHijosLiquidacion) > 0) {
                            error = true;
                            String df1 = DateTimeFormat.getShortDateFormat().format(menorHijosLiquidacion);
                            setMsg(getMsg() + "La fecha inicio de la Suscripcion del acta no puede ser mayor que el menor de los hijos de la etapa de liquidación:" + df1);
                        }

                        if (actividadSeleccionada.getEndDateTime().compareTo(menorHijosLiquidacion) > 0) {
                            error = true;
                            String df1 = DateTimeFormat.getShortDateFormat().format(menorHijosLiquidacion);
                            setMsg(getMsg() + "La fecha fin de la Suscripcion del acta no puede ser mayor que el menor de los hijos de la etapa de liquidación:" + df1);
                        }

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
                    }
                }

            }
        } else if (taskStore.getParent(actividadSeleccionada).getName().equals("Liquidaciones")) {
            service.setLog("sali a validar modificacion contrato", null);
            ActividadobraDTO actiEjecucion = taskStore.getParent(taskStore.getParent(actividadSeleccionada)).getChildren().get(1);
            service.setLog("ejecucion:" + actiEjecucion.getEndDateTime(), null);
            service.setLog("seleccionada:" + actividadSeleccionada.getStartDateTime(), null);
            if (actiEjecucion.getEndDateTime().compareTo(actividadSeleccionada.getStartDateTime()) > 0) {
                service.setLog("entre aca r445", null);
                error = true;
                setMsg(getMsg() + "La fecha inicio de la actividad no puede ser menor que la fecha fin de la etapa de contractual");
            } else {
                service.setLog("entre aca r445 yyyy", null);
            }
//            ActividadobraDTO actiRaiz = taskStore.getParent(taskStore.getParent(taskStore.getParent(taskStore.getParent(actividadSeleccionada))));
//            if (actividadSeleccionada.getEndDateTime().compareTo(actiRaiz.getChildren().get(2).getStartDateTime()) > 0) {
//                error = true;
//                setMsg(getMsg() + "La fecha fin de la actividad no puede ser superior a la fecha inicio de la Liqidacion del convenio macro");
//            }
//
//            if (actividadSeleccionada.getStartDateTime().compareTo(actiRaiz.getChildren().get(2).getStartDateTime()) > 0) {
//                error = true;
//                setMsg(getMsg() + "La fecha fin de la actividad no puede ser superior a la fecha inicio de la Liqidacion del convenio macro");
//            }

        }
        return error;
    }

    public static boolean validacionParaTodasLasActividades(ActividadobraDTO ac, ContratoDTO convenioDTO) {
        if (ac.getStartDateTime().compareTo(convenioDTO.getDatefechaini()) < 0) {
            return false;
        }
        if (ac.getEndDateTime().compareTo(convenioDTO.getDatefechafin()) > 0) {
            return false;
        }
        return true;
    }

    public static Boolean verificacionModificacionFechaFin(TreeStore<ActividadobraDTO> taskStore, ActividadobraDTO actividadSeleccionada) {
        if (actividadSeleccionada.getTipoActividad() == 2) {
        } else if (actividadSeleccionada.getTipoActividad() == 3) {
        }
        return false;

    }

    public static Boolean validacionCadenaPredecesoras(String cadena) {
        RegExp pat = RegExp.compile("^[\\d+]$|(([\\d]+)([,])?(([\\d]+)$))");
        if (pat.test(cadena)) {
            return true;
        }
        return false;
    }

    public static List<Integer> eliminarPredecesor(ActividadobraDTO acti, String predecesorAnterior, ActividadobraDTO actividadAnterior) {
        service.setLog("en eliminar predecesores" + predecesorAnterior, null);
        List<Integer> lstPredecesoresEliminados = null;
        if (predecesorAnterior != null) {
            if (!predecesorAnterior.isEmpty()) {
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
}
