/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client;

import co.com.interkont.cobra.planoperativo.client.dto.ActividadobraDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ContratoDTO;
import co.com.interkont.cobra.planoperativo.client.dto.DependenciaDTO;
import co.com.interkont.cobra.planoperativo.client.dto.GanttDatos;
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAbleAsync;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

/**
 *
 * @author Daniela
 */
public class Sub_menu_gwt implements IsWidget {
    
    protected CobraGwtServiceAbleAsync service;
    protected TreeStore<ActividadobraDTO> taskStore;
    protected ContratoDTO convenio;
    protected ListStore<DependenciaDTO> depStore;
    final Button planificacion = new Button("Planificación");
    final Button flujodecaja = new Button("Flujo de Caja");
    final Button reportes = new Button("Reportes");
    
    public Sub_menu_gwt(final CobraGwtServiceAbleAsync service, final TreeStore<ActividadobraDTO> taskStore, final ContratoDTO convenio, final ListStore<DependenciaDTO> depStore) {
        this.service = service;
        this.taskStore = taskStore;
        this.convenio = convenio;
        this.depStore = depStore;
        planificacion.setEnabled(false);
       
    }
    
    @Override
    public Widget asWidget() {
        return subMenu();
        
    }
    
    public ToolBar subMenu() {
        ToolBar tbarSuperior = new ToolBar();
        
        planificacion.setStyleName("boton-planificacion-gwt");
        flujodecaja.setStyleName("boton-flujocaja-gwt");
        reportes.setStyleName("boton-reporte-gwt");
        flujodecaja.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                service.setContratoDto(GanttDatos.estructurarDatosConvenio(convenio, taskStore, service, depStore), new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        service.setLog("Problema al transferir el objeto a JSF", null);
                    }
                    
                    @Override
                    public void onSuccess(Boolean result) {
                        service.setNavegacion(6, new AsyncCallback<Boolean>() {
                            @Override
                            public void onFailure(Throwable caught) {
                                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                            }
                            
                            @Override
                            public void onSuccess(Boolean result) {
                                Window.open(retornarNuevoContrato(), "_parent", retornarConfiguracionPagina());
                            }
                        });
                    }
                });
                
                
            }
        });
        
        
        reportes.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                service.setContratoDto(GanttDatos.estructurarDatosConvenio(convenio, taskStore, service, depStore), new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        service.setLog("Problema al transferir el objeto a JSF", null);
                    }
                    
                    @Override
                    public void onSuccess(Boolean result) {
                        service.setNavegacion(9, new AsyncCallback<Boolean>() {
                            @Override
                            public void onFailure(Throwable caught) {
                                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                            }
                            
                            @Override
                            public void onSuccess(Boolean result) {
                                Window.open(retornarNuevoContrato(), "_parent", retornarConfiguracionPagina());
                            }
                        });
                    }
                });
                
                
            }
        });
        
        tbarSuperior.add(planificacion);
        tbarSuperior.add(flujodecaja);
        tbarSuperior.add(reportes);
        tbarSuperior.setStyleName("ikont-tbar-menu-inferior");
        tbarSuperior.setWidth(900);
        return tbarSuperior;
        
        
    }
    
    public String retornarNuevoContrato() {
        
        return "/zoom/Supervisor/nuevoContratoPlanOperativo.xhtml";
        
    }
    
    public String retornarConfiguracionPagina() {
        return "menubar=si, location=false, resizable=no, scrollbars=si, status=no, dependent=true";
    }
}
