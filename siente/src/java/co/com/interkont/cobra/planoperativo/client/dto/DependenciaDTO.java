/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client.dto;

import co.com.interkont.cobra.to.Dependencia;
import com.gantt.client.config.GanttConfig.DependencyType;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author desarrollo9
 */
public class DependenciaDTO implements Serializable {

   String id;
	String fromId;
	String toId;
	DependencyType type;
	String css;

	
	public DependenciaDTO(String id, String fromId, String toId, DependencyType type) {
		this(id, fromId, toId, type, "");
	}

	public DependenciaDTO(String id, String fromId, String toId,
			DependencyType type, String css) {
		this.id = id;
		this.fromId = fromId;
		this.toId = toId;
		this.type = type;
		this.css = css;
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
//    
//        private int idDependencia;
//    private ActividadobraDTO actividadobraByFkActividadDestino;
//    private ActividadobraDTO actividadobraByFkActividadOrigen;
//    private int tipoDepencia;
//
//    public DependenciaDTO() {
//    }
//
//    public DependenciaDTO(Dependencia dependencia) {
//        this.idDependencia = dependencia.getIdDependencia();
//        this.actividadobraByFkActividadDestino =new ActividadobraDTO(dependencia.getActividadobraByFkActividadDestino());
//        this.actividadobraByFkActividadOrigen =new ActividadobraDTO(dependencia.getActividadobraByFkActividadOrigen());
//        this.tipoDepencia = dependencia.getTipoDepencia();
//
//    }
//
//    public DependenciaDTO(int idDependencia, ActividadobraDTO actividadobraByFkActividadDestino, ActividadobraDTO actividadobraByFkActividadOrigen, int tipoDepencia) {
//        this.idDependencia =idDependencia;
//        this.actividadobraByFkActividadDestino = actividadobraByFkActividadDestino;
//        this.actividadobraByFkActividadOrigen = actividadobraByFkActividadOrigen;
//        this.tipoDepencia = tipoDepencia;
//    }
//
//    /**
//     * @return the idDependencia
//     */
//    public int getIdDependencia() {
//        return idDependencia;
//    }
//
//    /**
//     * @param idDependencia the idDependencia to set
//     */
//    public void setIdDependencia(int idDependencia) {
//        this.idDependencia = idDependencia;
//    }
//
//    /**
//     * @return the actividadobraByFkActividadDestino
//     */
//    public ActividadobraDTO getActividadobraByFkActividadDestino() {
//        return actividadobraByFkActividadDestino;
//    }
//
//    /**
//     * @param actividadobraByFkActividadDestino the actividadobraByFkActividadDestino to set
//     */
//    public void setActividadobraByFkActividadDestino(ActividadobraDTO actividadobraByFkActividadDestino) {
//        this.actividadobraByFkActividadDestino = actividadobraByFkActividadDestino;
//    }
//
//    /**
//     * @return the actividadobraByFkActividadOrigen
//     */
//    public ActividadobraDTO getActividadobraByFkActividadOrigen() {
//        return actividadobraByFkActividadOrigen;
//    }
//
//    /**
//     * @param actividadobraByFkActividadOrigen the actividadobraByFkActividadOrigen to set
//     */
//    public void setActividadobraByFkActividadOrigen(ActividadobraDTO actividadobraByFkActividadOrigen) {
//        this.actividadobraByFkActividadOrigen = actividadobraByFkActividadOrigen;
//    }
//
//    /**
//     * @return the tipoDepencia
//     */
//    public int getTipoDepencia() {
//        return tipoDepencia;
//    }
//
//    /**
//     * @param tipoDepencia the tipoDepencia to set
//     */
//    public void setTipoDepencia(int tipoDepencia) {
//        this.tipoDepencia = tipoDepencia;
//    }
//        
}
