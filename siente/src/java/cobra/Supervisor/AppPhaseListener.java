/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.Supervisor;

/**
 *
 * @author felipe
 */
import cobra.SessionBeanCobra;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.NavigationHandler;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import javax.faces.event.PhaseListener;

import javax.faces.event.PhaseEvent;

import javax.faces.event.PhaseId;

public class AppPhaseListener implements PhaseListener {

    public AppPhaseListener() {
    }

    @Override
    public void beforePhase(PhaseEvent pe) {
        FacesContext facesContext = pe.getFacesContext();
        
        if (pe.getPhaseId() == PhaseId.RENDER_RESPONSE) {

            String viewId = pe.getFacesContext().getViewRoot().getViewId();           
            if (viewId.endsWith(".xhtml")) {
                String ManagedBeanName = this.getFileName(viewId);

                if (ManagedBeanName != null) {
                    if ( //ManagedBeanName.compareTo("administrarObraNew") == 0 || 
                            ManagedBeanName.compareTo("detalleObra") == 0
                            || ManagedBeanName.compareTo("documentoObra") == 0 || ManagedBeanName.compareTo("imagenObra") == 0) {
                        AdministrarObraNew admin = (AdministrarObraNew) facesContext.getApplication().evaluateExpressionGet(facesContext, "#{Supervisor$AdministrarObraNew}", AdministrarObraNew.class);
                        if (ManagedBeanName.compareTo("imagenObra") == 0) {
                            admin.setOpcion(4);
                        }

                        if (ManagedBeanName.compareTo("documentoObra") == 0) {
                            admin.setOpcion(3);
                        }
                        if (ManagedBeanName.compareTo("detalleObra") == 0) {
                            admin.setOpcion(0);
                        }

                        ManagedBeanName = "Supervisor$AdministrarObraNew";
                        SessionBeanCobra bean = (SessionBeanCobra) facesContext.getApplication().evaluateExpressionGet(facesContext, "#{SessionBeanCobra}", SessionBeanCobra.class);
                        bean.cargarpermisosmodulo(6);

                    }

                    if (ManagedBeanName.compareTo("participacionCiudadano") == 0) {
                        ManagedBeanName = "Ciudadano$PerfilCiudadano";
                    }

                    if (ManagedBeanName.compareTo("tipificacionproyecto") == 0 || ManagedBeanName.compareTo("datosBasicos") == 0) {
                        ManagedBeanName = "Supervisor$IngresarNuevaObra";
                    }

                    if (ManagedBeanName.compareTo("home_indicadores") == 0) {

                        SessionBeanCobra bean = (SessionBeanCobra) facesContext.getApplication().evaluateExpressionGet(facesContext, "#{SessionBeanCobra}", SessionBeanCobra.class);
                        bean.cargarpermisosmodulo(9);
                    }
                    if (ManagedBeanName.compareTo("home_gestion") == 0) {

                        SessionBeanCobra bean = (SessionBeanCobra) facesContext.getApplication().evaluateExpressionGet(facesContext, "#{SessionBeanCobra}", SessionBeanCobra.class);

                        if (bean.isIniciamapa() && !bean.isLogueado()) {
                            bean.iniciarDesdeMapa();
                        }
                        bean.cargarpermisosmodulo(6);

                    }
                    if (ManagedBeanName.compareTo("homeAtencionHumanitaria") == 0) {

                        SessionBeanCobra bean = (SessionBeanCobra) facesContext.getApplication().evaluateExpressionGet(facesContext, "#{SessionBeanCobra}", SessionBeanCobra.class);
                        bean.cargarpermisosmodulo(1);
                    }

                    if (ManagedBeanName.compareTo("listadoSeguimientos") == 0) {
                        ManagedBeanName = "SupervisionExterna$ListadoSeguimientos";
                        SessionBeanCobra bean = (SessionBeanCobra) facesContext.getApplication().evaluateExpressionGet(facesContext, "#{SessionBeanCobra}", SessionBeanCobra.class);
                        bean.cargarpermisosmodulo(6);
                    }
                    if (ManagedBeanName.compareTo("nuevoContratoPlanOperativo") == 0) {
                        ManagedBeanName = "Supervisor$Contrato";
                    }
                    if (ManagedBeanName.compareTo("planO") == 0) {
                        SessionBeanCobra bean = (SessionBeanCobra) facesContext.getApplication().evaluateExpressionGet(facesContext, "#{SessionBeanCobra}", SessionBeanCobra.class);
                        bean.setCargarcontrato(true);
                    }

                }

                Object object = (Object) FacesUtils.getManagedBean(ManagedBeanName);
                if (object == null) {
                } else {
                    ILifeCycleAware lifeCycleAwareBean = (ILifeCycleAware) object;
                    lifeCycleAwareBean.prerender();
                }
            }
        }

    }

    public String getManagedName(String path) {

        String fileName = null;
        String separator = "/";
        int pos = path.lastIndexOf(separator);
        int pos2 = path.lastIndexOf(".");
        if (pos2 > - 1) {
            fileName = path.substring(pos + 1, pos2);
        } else {
            fileName = path.substring(pos + 1);
        }
        return fileName;

    }//Fin del metodo getFileName

    public String getFileName(String path) {

        String fileName = null;
        String separator = "/";
        int sep = path.lastIndexOf(separator);
        int dot = path.lastIndexOf(".");
        fileName = path.substring(sep + 1, dot);

        return fileName.substring(0, 1).toLowerCase() + fileName.substring(1);

    }//Fin del metodo getFileName

    @Override
    public void afterPhase(PhaseEvent pe) {

        FacesContext fc = pe.getFacesContext();

        if (pe.getPhaseId() == PhaseId.RESTORE_VIEW) {

            SessionBeanCobra bean = (SessionBeanCobra) fc.getApplication().evaluateExpressionGet(fc, "#{SessionBeanCobra}", SessionBeanCobra.class);

            boolean loginPage = fc.getViewRoot().getViewId().lastIndexOf("login") > -1 ? true : false;

            if (!loginPage && !loggedIn()) {

                NavigationHandler nh = fc.getApplication().getNavigationHandler();

                nh.handleNavigation(fc, null, "logout");

            }

        }

    }

    public void recorrerListaComponentes(Iterator lista) {
        while (lista.hasNext()) {
            UIComponent objeto = (UIComponent) lista.next();
            if (objeto.getId().compareTo("btSoporte") == 0) {
                objeto.setRendered(false);
            }

            recorrerListaComponentes(objeto.getFacetsAndChildren());
        }
    }

    /*public String prueba(FacesContext fc)
     {
    
    
     SessionBeanCobra bean = (SessionBeanCobra) fc.getApplication().evaluateExpressionGet(fc, "#{SessionBeanCobra}", SessionBeanCobra.class);
    
    
     Iterator lista=fc.getViewRoot().getFacetsAndChildren();
    
     recorrerListaComponentes(lista);   
    
     return null;
     } */
    private boolean loggedIn() {

        //TODO
        //implementar un metodo de validacion de login
        return true;
    }

    public PhaseId getPhaseId() {

        return PhaseId.ANY_PHASE;

    }
}
