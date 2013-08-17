/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.SupervisionExterna;


import co.com.interkont.cobra.to.Obra;
import co.com.interkont.cobra.to.Seguimiento;
import cobra.SessionBeanCobra;
import cobra.Supervisor.FacesUtils;
import cobra.Supervisor.ILifeCycleAware;
import java.io.IOException;
import java.io.Serializable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import org.richfaces.component.UIDataTable;


/**
 * Clase encargada de procesar las peticiones de la página de listado de seguimientos
 * @author Jhon Eduard Ortiz S.
 */
public class ListadoSeguimientos  implements ILifeCycleAware, Serializable {
    // <editor-fold defaultstate="collapsed" desc="Managed Component Definition">
    private String id = "";
    private UIDataTable tablaSeguimientos = new UIDataTable();
    private ResourceBundle bundle = getSessionBeanCobra().getBundle();

    public Obra getObra() {
        return getSessionBeanCobra().getCobraService().getObra();
    }

    public void setObra(Obra obra) {
        getSessionBeanCobra().getCobraService().setObra(obra);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UIDataTable getTablaSeguimientos() {
        return tablaSeguimientos;
    }

    public void setTablaSeguimientos(UIDataTable tablaSeguimientos) {
        this.tablaSeguimientos = tablaSeguimientos;
    }

    /**
     * <p>Automatically managed component initialization. <strong>WARNING:</strong>
     * This method is automatically generated, so any user-specified code inserted
     * here is subject to being replaced.</p>
     */
    private void _init() throws Exception {
    }
    // </editor-fold>

    public ListadoSeguimientos() {
    }
    
    @Override
    public void prerender() {
        id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        if (id != null ) {
            if (getObra() != null && id != null) {
                if (String.valueOf(getObra().getIntcodigoobra()).compareTo(id) != 0) {
                    setObra(getSessionBeanCobra().getCobraService().encontrarObraPorId(Integer.parseInt(id)));
                }
                llenarSeguimientosxProyectos();
            }
        }
    }
    /**
     * Obtiene el bean que contiene la información general del sistema
     * @return
     */
    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }

    /**
     * Obtiene el bean que maneja un seguimiento específico
     * @return
     */
    protected AdminSupervisionExterna getAdminSupervisionExterna() {
        return (AdminSupervisionExterna) FacesUtils.getManagedBean("Supervision$Externa");
    }

    /**
     * LLena los seguimientos realizados a un proyecto
     * @return
     */
    public String llenarSeguimientosxProyectos() {
        if (getObra().getSolicitud_obra() != null) {
            getSessionBeanCobra().getCobraService().setListaseguimientos(
                    getSessionBeanCobra().getCobraService().encontrarSeguimientosxProyecto(getObra()));
        }
        return null;
    }

    /**
     * Establece y direcciona a la página que presentará el detalle del
     * seguimiento seleccionado
     * @return
     */
    public String seguimientoconsul(int filaSeleccionada) {
        System.out.println("entre seguimiento");
        SessionBeanCobra sessionBeanCobra = (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
        Seguimiento segui = sessionBeanCobra.getCobraService().getListaseguimientos().get(filaSeleccionada);
        getAdminSupervisionExterna().setObjetoSeguimiento(segui);
        return "consuldetaseguimi";
    }

    /**
     * Procesa la petición para descargar el reporte del seguimiento
     * @return
     */
    public String seguimientoreport(int filaSeleccionada ) {
//       SessionBeanCobra sessionBeanCobra = (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
//       Seguimiento segui = sessionBeanCobra.getCobraService().getListaseguimientos().get(filaSeleccionada);
//        
       try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdfseguimiento") + getObra().getIntcodigoobra());
        } catch (IOException ex) {
            Logger.getLogger(Obra.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String reporteInterventoriaSeguimiento() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(bundle.getString("reportepdfseguimientointerventoria") + getObra().getIntcodigoobra());
        } catch (IOException ex) {
            Logger.getLogger(Obra.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
}
