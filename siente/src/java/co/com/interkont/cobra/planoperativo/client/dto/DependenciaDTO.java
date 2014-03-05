/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client.dto;

import com.gantt.client.config.GanttConfig.DependencyType;
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author desarrollo9
 */
public class DependenciaDTO implements IsSerializable {

    String id;
    String fromId;
    String toId;
    DependencyType type;
    String css;
    private int idDependencia;
    boolean isobligatoria = false;
    private ActividadobraDTO actividadTo;
    private ActividadobraDTO actividadFrom;

    public DependenciaDTO() {
    }

    public DependenciaDTO(String id, String fromId, String toId, DependencyType type, ActividadobraDTO actividadFrom, ActividadobraDTO actividadTo) {
        this(id, fromId, toId, type, "", actividadFrom, actividadTo);
    }

    public DependenciaDTO(String id, String fromId, String toId,
            DependencyType type, String css, ActividadobraDTO actividadFrom, ActividadobraDTO actividadTo) {
        this.id = id;
        this.fromId = fromId;
        this.toId = toId;
        this.type = type;
        this.css = css;
        this.actividadFrom = actividadFrom;
        this.actividadTo = actividadTo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public DependencyType getType() {
        return type;
    }

    public void setType(DependencyType type) {
        this.type = type;
    }

    public String getCss() {
        return css;
    }

    public void setCss(String css) {
        this.css = css;
    }

    public boolean isIsobligatoria() {
        return isobligatoria;
    }

    public void setIsobligatoria(boolean isobligatoria) {
        this.isobligatoria = isobligatoria;
    }

    /**
     * @return the idDependencia
     */
    public int getIdDependencia() {
        return idDependencia;
    }

    /**
     * @param idDependencia the idDependencia to set
     */
    public void setIdDependencia(int idDependencia) {
        this.idDependencia = idDependencia;
    }

    /**
     * @return the actividadTo
     */
    public ActividadobraDTO getActividadTo() {
        return actividadTo;
    }

    /**
     * @param actividadTo the actividadTo to set
     */
    public void setActividadTo(ActividadobraDTO actividadTo) {
        this.actividadTo = actividadTo;
    }

    /**
     * @return the actividadFrom
     */
    public ActividadobraDTO getActividadFrom() {
        return actividadFrom;
    }

    /**
     * @param actividadFrom the actividadFrom to set
     */
    public void setActividadFrom(ActividadobraDTO actividadFrom) {
        this.actividadFrom = actividadFrom;
    }
    
    
}
