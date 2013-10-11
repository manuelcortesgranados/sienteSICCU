/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client.dto;

import com.gantt.client.config.GanttConfig.TaskType;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.sencha.gxt.core.client.util.DateWrapper;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import sun.management.VMManagement;

/**
 *
 * @author desarrollo9
 */
public class ActividadobraDTO implements IsSerializable {
    /*
     Atributos con los nombres que relacion gxt en los props
     */

    String id;
    String name;
    Date startDateTime;
    Date endDateTime;
    int duration;
    int percentDone;
    private int tipoActividad;
    private boolean seEdito;
    private boolean esNoEditable;
    private int numeracion;
    private String predecesor;
    /**
     * Objeto que posee enum con los tipos de tareas Leaf=Hoja, Parent= Padre,
     * Milestone=Hito
     */
    TaskType taskType;
    /**
     * Variable para establecer la obligatoriedad de la tarea
     */
    private boolean boolobligatoria = false;
    /**
     * Atributos del objeto actividadObraDto
     */
    private long oidactiviobra;
    private Double peso;
    private Integer estado;
    private List<DependenciaDTO> dependenciasForFkActividadOrigen = new ArrayList<DependenciaDTO>();
    private List<DependenciaDTO> dependenciasForFkActividadDestino = new ArrayList<DependenciaDTO>();
    private ContratoDTO contrato;
    private ObraDTO obra;
    private String eliminar = "Eliminar";
    private List<ActividadobraDTO> children = new ArrayList<ActividadobraDTO>();

    public long getOidactiviobra() {
        return oidactiviobra;
    }

    public void setOidactiviobra(long oidactiviobra) {
        this.oidactiviobra = oidactiviobra;
    }

    public ActividadobraDTO(String strdescactividad, int tipoActividad, int id) {
        this.name = strdescactividad;
        this.tipoActividad = tipoActividad;
        this.id = "" + id;
        this.eliminar = "Eliminar";
    }

//     public ActividadobraDTO(String strdescactividad,int tipoActividad,Date fechaInicio,Date fechaFin,ObraDTO obra) {
//        this.strdescactividad=strdescactividad;
//        this.tipoActividad=tipoActividad;
//        this.fechaInicio=fechaInicio;
//        this.fechaFin=fechaFin;
//        this.obra=obra;
//    }
    public ActividadobraDTO(String id, String name, Date start, int duration,
            int percentDone, TaskType taskType) {
        this.id =""+this.hashCode();
        this.name = name;
        this.startDateTime = start;
        this.duration = duration;
        this.percentDone = percentDone;
        this.taskType = taskType;
    }

    public ActividadobraDTO() {
    }

    public ActividadobraDTO(Date fechaInicio, Date fechaFin, int duracion) {
        this.startDateTime = fechaInicio;
        this.endDateTime = fechaFin;
        this.duration = duracion;
    }

    public ActividadobraDTO(Date fechaInicio, Date fechaFin, int duracion, String name) {
        this.startDateTime = fechaInicio;
        this.endDateTime = fechaFin;
        this.duration = duracion;
        this.name = name;
    }
//    
//    public ActividadobraDTO(Date fechaInicio,Date fechaFin,int duracion) {
//        this.startDateTime=fechaInicio;
//        this.endDateTime=fechaFin;
//        this.duration=duracion;
//    }

    public List<ActividadobraDTO> getChildren() {
        return children;
    }

    public void setChildren(List<ActividadobraDTO> children) {
        this.children = children;
    }

    public ActividadobraDTO(List<ActividadobraDTO> children) {
        setChildren(children);

    }

    public ActividadobraDTO(String name, Date start, int duration, int percentDone,
            TaskType taskType, int tipoActividad, boolean boolobligatoria) {
        this(name, name, start, duration, percentDone, taskType, tipoActividad, boolobligatoria);
    }

    public ActividadobraDTO(String id, String name, Date start, int duration,
            int percentDone, TaskType taskType, int tipoActividad, boolean boolobligatoria) {
        this.id =""+this.hashCode();
        this.name = name;
        this.duration = duration;
        this.percentDone = percentDone;
        this.taskType = taskType;
        this.tipoActividad = tipoActividad;
        this.boolobligatoria = boolobligatoria;
        DateWrapper dw = new DateWrapper(start).clearTime();
        this.startDateTime = dw.asDate();
        this.endDateTime = dw.addDays(duration).asDate();

    }

    public ActividadobraDTO(String name, Date start, int duration, int percentDone,
            TaskType taskType, int tipoActividad, boolean boolobligatoria, ObraDTO obra) {
        this(name, name, start, duration, percentDone, taskType, tipoActividad, boolobligatoria, obra);
    }

    public ActividadobraDTO(String name, Date start, Date fin, int percentDone,
            TaskType taskType, int tipoActividad, boolean boolobligatoria, ObraDTO obra) {
        this(name, name, start, fin, percentDone, taskType, tipoActividad, boolobligatoria, obra);
    }

    public ActividadobraDTO(String name, Date start, int duration, int percentDone,
            TaskType taskType, int tipoActividad, boolean boolobligatoria, ContratoDTO contrato) {
        this(name, name, start, duration, percentDone, taskType, tipoActividad, boolobligatoria, contrato);
    }

    public ActividadobraDTO(String id, String name, Date start, int duration,
            int percentDone, TaskType taskType, int tipoActividad, boolean boolobligatoria, ObraDTO obraDto) {
        this.id = "" + this.hashCode();
        this.name = name;
        this.startDateTime = start;
        this.duration = duration;
        this.percentDone = percentDone;
        this.taskType = taskType;
        this.tipoActividad = tipoActividad;
        this.boolobligatoria = boolobligatoria;
        this.obra = obraDto;
        Date copiaFecha= CalendarUtil.copyDate(start);
        CalendarUtil.addDaysToDate(copiaFecha, duration);
        this.endDateTime =copiaFecha; 
    }

    public ActividadobraDTO(String id, String name, Date start, Date fin,
            int percentDone, TaskType taskType, int tipoActividad, boolean boolobligatoria, ObraDTO obraDto) {
        this.id = "" + this.hashCode();
        this.name = name;
        this.startDateTime = start;
        this.endDateTime = fin;
        this.duration = calcularDuracion();
        this.percentDone = percentDone;
        this.taskType = taskType;
        this.tipoActividad = tipoActividad;
        this.boolobligatoria = boolobligatoria;
        this.obra = obraDto;
    }

    public ActividadobraDTO(String id, String name, Date start, int duration,
            int percentDone, TaskType taskType, int tipoActividad, boolean boolobligatoria, ContratoDTO contratoDto) {
        this.id =""+this.hashCode();
        this.name = name;
        this.startDateTime = start;
        this.duration = duration;
        this.percentDone = percentDone;
        this.taskType = taskType;
        this.tipoActividad = tipoActividad;
        this.boolobligatoria = boolobligatoria;
        this.contrato = contratoDto;
        DateWrapper dw = new DateWrapper(start).clearTime();
        this.endDateTime = dw.addDays(duration).asDate();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getPercentDone() {
        return percentDone;
    }

    public void setPercentDone(int percentDone) {
        this.percentDone = percentDone;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public void addChild(ActividadobraDTO child) {
        getChildren().add(child);
    }

    public boolean hasChildren() {
        return !children.isEmpty();
    }

    public int calcularDuracion() {
        if (this.getStartDateTime() != null && this.getEndDateTime() != null) {

            long diferencia = this.getEndDateTime().getTime() - this.getStartDateTime().getTime();
            if (this.getStartDateTime().compareTo(this.getEndDateTime()) != 0) {
                double dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));
                return ((int) dias);
            } else {
                return 1;
            }
        }
        return 0;
    }

    /**
     * @return the obra
     */
    public ObraDTO getObra() {
        return obra;
    }

    /**
     * @param obra the obra to set
     */
    public void setObra(ObraDTO obra) {
        this.obra = obra;
    }

    /**
     * @return the estado
     */
    public Integer getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    /**
     * @return the contrato
     */
    public ContratoDTO getContrato() {
        return contrato;
    }

    /**
     * @param contrato the contrato to set
     */
    public void setContrato(ContratoDTO contrato) {
        this.contrato = contrato;
    }

    /**
     * @return the tipoActividad
     */
    public int getTipoActividad() {
        return tipoActividad;
    }

    /**
     * @param tipoActividad the tipoActividad to set
     */
    public void setTipoActividad(int tipoActividad) {
        this.tipoActividad = tipoActividad;
    }

    public boolean isBoolobligatoria() {
        return boolobligatoria;
    }

    public void setBoolobligatoria(boolean boolobligatoria) {
        this.boolobligatoria = boolobligatoria;
    }

    /**
     * @return the eliminar
     */
    public String getEliminar() {
        return eliminar;
    }

    /**
     * @param eliminar the eliminar to set
     */
    public void setEliminar(String eliminar) {
        this.eliminar = eliminar;
    }

    /**
     * @return the dependenciasForFkActividadOrigen
     */
    public List<DependenciaDTO> getDependenciasForFkActividadOrigen() {
        return dependenciasForFkActividadOrigen;
    }

    /**
     * @param dependenciasForFkActividadOrigen the
     * dependenciasForFkActividadOrigen to set
     */
    public void setDependenciasForFkActividadOrigen(List<DependenciaDTO> dependenciasForFkActividadOrigen) {
        this.dependenciasForFkActividadOrigen = dependenciasForFkActividadOrigen;
    }

    /**
     * @return the dependenciasForFkActividadDestino
     */
    public List<DependenciaDTO> getDependenciasForFkActividadDestino() {
        return dependenciasForFkActividadDestino;
    }

    /**
     * @param dependenciasForFkActividadDestino the
     * dependenciasForFkActividadDestino to set
     */
    public void setDependenciasForFkActividadDestino(List<DependenciaDTO> dependenciasForFkActividadDestino) {
        this.dependenciasForFkActividadDestino = dependenciasForFkActividadDestino;
    }

    /**
     * @return the seEdito
     */
    public boolean isSeEdito() {
        return seEdito;
    }

    /**
     * @param seEdito the seEdito to set
     */
    public void setSeEdito(boolean seEdito) {
        this.seEdito = seEdito;
    }

    /**
     * @return the esNoEditable
     */
    public boolean isEsNoEditable() {
        return esNoEditable;
    }

    /**
     * @param esNoEditable the esNoEditable to set
     */
    public void setEsNoEditable(boolean esNoEditable) {
        this.esNoEditable = esNoEditable;

    }

    /**
     * @return the numeracion
     */
    public int getNumeracion() {
        return numeracion;
    }

    /**
     * @param numeracion the numeracion to set
     */
    public void setNumeracion(int numeracion) {
        this.numeracion = numeracion;
    }

    /**
     * @return the predecesor
     */
    public String getPredecesor() {
        return predecesor;
    }

    /**
     * @param predecesor the predecesor to set
     */
    public void setPredecesor(String predecesor) {
        this.predecesor = predecesor;
    }
}
