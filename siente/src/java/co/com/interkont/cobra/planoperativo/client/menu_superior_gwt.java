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
public class menu_superior_gwt implements IsWidget {
    
    protected CobraGwtServiceAbleAsync service;
    protected TreeStore<ActividadobraDTO> taskStore;
    protected ContratoDTO convenio;
    protected ListStore<DependenciaDTO> depStore;
    
    public menu_superior_gwt(final CobraGwtServiceAbleAsync service, final TreeStore<ActividadobraDTO> taskStore, final ContratoDTO convenio, final ListStore<DependenciaDTO> depStore) {
        this.service = service;
        this.taskStore = taskStore;
        this.convenio = convenio;
        this.depStore = depStore;
    }
    
    @Override
    public Widget asWidget() {
        return menuSuperior();
        
    }
    
    public ToolBar menuSuperior() {
        ToolBar tbarSuperior = new ToolBar();
        
        Hyperlink datosBasicos = new Hyperlink();
        datosBasicos.setStyleName("img-button-ncpo ikont-acl-datosbasicos");
        datosBasicos.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                service.setContratoDto(GanttDatos.estructurarDatosConvenio(convenio, taskStore, service, depStore), new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        service.setLog("Problema al transferir el objeto a JSF", null);
                    }
                    
                    @Override
                    public void onSuccess(Boolean result) {
                        service.setNavegacion(1, new AsyncCallback<Boolean>() {
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
        tbarSuperior.add(datosBasicos);
        
        
        Hyperlink planOperativo = new Hyperlink();
        planOperativo.setStyleName("img-button-ncpo ikont-acl-planoperativo");
        tbarSuperior.add(planOperativo);
        
        
        Hyperlink polizas = new Hyperlink();
        polizas.setStyleName("img-button-ncpo ikont-acl-polizas");
        polizas.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                  service.setContratoDto(GanttDatos.estructurarDatosConvenio(convenio, taskStore, service, depStore), new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        service.setLog("Problema al transferir el objeto a JSF", null);
                    }
                    
                    @Override
                    public void onSuccess(Boolean result) {
                        service.setNavegacion(3, new AsyncCallback<Boolean>() {
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
        tbarSuperior.add(polizas);
        
        Hyperlink documentos = new Hyperlink();
        documentos.setStyleName("img-button-ncpo ikont-acl-documentos");
        documentos.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                  service.setContratoDto(GanttDatos.estructurarDatosConvenio(convenio, taskStore, service, depStore), new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        service.setLog("Problema al transferir el objeto a JSF", null);
                    }
                    
                    @Override
                    public void onSuccess(Boolean result) {
                        service.setNavegacion(5, new AsyncCallback<Boolean>() {
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
        tbarSuperior.add(documentos);
        tbarSuperior.setStyleName("ikont-hpg-contentmenu-gwt");
        tbarSuperior.setWidth(980);
        return tbarSuperior;
        
        
    }
    
    public String retornarNuevoContrato() {
        
        return "/zoom/Supervisor/nuevoContratoPlanOperativo.xhtml";
        
    }
    
    public String retornarConfiguracionPagina() {
        return "menubar=si, location=false, resizable=no, scrollbars=si, status=no, dependent=true";
    }
}
