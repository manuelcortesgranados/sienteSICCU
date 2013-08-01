/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client.dto;

import com.gantt.client.config.GanttConfig.TaskType;
import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    /**
    Objeto que posee enum con los tipos de tareas Leaf=Hoja, Parent= Padre, Milestone=Hito
    */
    TaskType taskType;
    /**
    Atributos del objeto actividadObraDto
    */
     private long oidactiviobra;
    private String strdescactividad;
    private Date fechaInicio;
    private Date fechaFin;
    private Integer peso;
    private Double duracion;
    private Integer estado;
    private Set dependenciasForFkActividadOrigen = new HashSet(0);
    private Set dependenciasForFkActividadDestino = new HashSet(0);
    private ContratoDTO contrato;

    
    
    
    public ActividadobraDTO() {
    }

    private List<ActividadobraDTO> children = new ArrayList<ActividadobraDTO>();

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
            TaskType taskType) {
        this(name, name, start, duration, percentDone, taskType);
    }

    public ActividadobraDTO(String id, String name, Date start, int duration,
            int percentDone, TaskType taskType) {
        this.id = id;
        this.name = name;
        this.startDateTime = start;
        this.duration = duration;
        this.percentDone = percentDone;
        this.taskType = taskType;
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

//
//    
//    public ActividadobraDTO(long oidactiviobra, String strdescactividad, Date fechaInicio, Date fechaFin, Integer peso, Double duracion, Integer estado, ContratoDTO contrato, Set dependenciasForFkActividadOrigen, Set dependenciasForFkActividadDestino) {
//        this.oidactiviobra = oidactiviobra;
//        this.strdescactividad = strdescactividad;
//        this.fechaInicio = fechaInicio;
//        this.fechaFin = fechaFin;
//        this.peso = peso;
//        this.duracion = duracion;
//        this.estado = estado;
//        this.contrato = contrato;
//        this.dependenciasForFkActividadDestino = dependenciasForFkActividadDestino;
//        this.dependenciasForFkActividadOrigen = dependenciasForFkActividadOrigen;
//    }
//    
//     public ActividadobraDTO(long oidactiviobra, String strdescactividad, Date fechaInicio, Date fechaFin, Integer peso, Double duracion, Integer estado, ContratoDTO contrato) {
//        this.oidactiviobra = oidactiviobra;
//        this.strdescactividad = strdescactividad;
//        this.fechaInicio = fechaInicio;
//        this.fechaFin = fechaFin;
//        this.peso = peso;
//        this.duracion = duracion;
//        this.estado = estado;
//        this.contrato = contrato;
//         }
//
//   
//
//    /**
//     * @return the oidactiviobra
//     */
//    public long getOidactiviobra() {
//        return oidactiviobra;
//    }
//
//    /**
//     * @param oidactiviobra the oidactiviobra to set
//     */
//    public void setOidactiviobra(long oidactiviobra) {
//        this.oidactiviobra = oidactiviobra;
//    }
//
//    /**
//     * @return the strdescactividad
//     */
//    public String getStrdescactividad() {
//        return strdescactividad;
//    }
//
//    /**
//     * @param strdescactividad the strdescactividad to set
//     */
//    public void setStrdescactividad(String strdescactividad) {
//        this.strdescactividad = strdescactividad;
//    }
//    /**
//     * @return the fechaInicio
//     */
//    public Date getFechaInicio() {
//        return fechaInicio;
//    }
//
//    /**
//     * @param fechaInicio the fechaInicio to set
//     */
//    public void setFechaInicio(Date fechaInicio) {
//        this.fechaInicio = fechaInicio;
//    }
//
//    /**
//     * @return the fechaFin
//     */
//    public Date getFechaFin() {
//        return fechaFin;
//    }
//
//    /**
//     * @param fechaFin the fechaFin to set
//     */
//    public void setFechaFin(Date fechaFin) {
//        this.fechaFin = fechaFin;
//    }
//
//    /**
//     * @return the peso
//     */
//    public Integer getPeso() {
//        return peso;
//    }
//
//    /**
//     * @param peso the peso to set
//     */
//    public void setPeso(Integer peso) {
//        this.peso = peso;
//    }
//
//    /**
//     * @return the duracion
//     */
//    public Double getDuracion() {
//        return duracion;
//    }
//
//    /**
//     * @param duracion the duracion to set
//     */
//    public void setDuracion(Double duracion) {
//        this.duracion = duracion;
//    }
//
//    /**
//     * @return the estado
//     */
//    public Integer getEstado() {
//        return estado;
//    }
//
//    /**
//     * @param estado the estado to set
//     */
//    public void setEstado(Integer estado) {
//        this.estado = estado;
//    }
//
//    /**
//     * @return the dependenciasForFkActividadOrigen
//     */
//    public Set getDependenciasForFkActividadOrigen() {
//        return dependenciasForFkActividadOrigen;
//    }
//    /**
//     * @param dependenciasForFkActividadOrigen the
//     * dependenciasForFkActividadOrigen to set
//     */
//    public void setDependenciasForFkActividadOrigen(Set dependenciasForFkActividadOrigen) {
//        this.dependenciasForFkActividadOrigen = dependenciasForFkActividadOrigen;
//    }
//
//    /**
//     * @return the dependenciasForFkActividadDestino
//     */
//    public Set getDependenciasForFkActividadDestino() {
//        return dependenciasForFkActividadDestino;
//    }
//
//    /**
//     * @param dependenciasForFkActividadDestino the
//     * dependenciasForFkActividadDestino to set
//     */
//    public void setDependenciasForFkActividadDestino(Set dependenciasForFkActividadDestino) {
//        this.dependenciasForFkActividadDestino = dependenciasForFkActividadDestino;
//    }
//
//    /**
//     * @return the contrato
//     */
//    public ContratoDTO getContrato() {
//        return contrato;
//    }
//
//    /**
//     * @param contrato the contrato to set
//     */
//    public void setContrato(ContratoDTO contrato) {
//        this.contrato = contrato;
//    }
  
}
