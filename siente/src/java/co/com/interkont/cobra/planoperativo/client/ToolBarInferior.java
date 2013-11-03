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
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

/**
 *
 * @author desarrollo9
 */
public class ToolBarInferior extends ToolBar {

    ToolBar tbarinferior = new ToolBar();
    final Button continuar = new Button();
    final Button finalizar = new Button();

    public ToolBarInferior(final CobraGwtServiceAbleAsync service, final TreeStore<ActividadobraDTO> taskStore, final ContratoDTO convenio, final ListStore<DependenciaDTO> depStore) {

        continuar.setText("Continuar");
        continuar.addClickHandler(new ClickHandler() {
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
        continuar.setStyleName("ikont-po-img-continuarGWTInferior");


        finalizar.setText("Finalizar");
        finalizar.addClickHandler(new ClickHandler() {        
            @Override
            public void onClick(ClickEvent event) {
                service.setContratoDto(GanttDatos.estructurarDatosConvenio(convenio, taskStore, service, depStore), new AsyncCallback<Boolean>() {
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
                                service.setGuardarconvenio(2, new AsyncCallback<Boolean>() {
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
            }
        });
        finalizar.setStyleName("ikont-po-img-finalizarGWTInferior");

        add(continuar);
        add(finalizar);
        setStyleName("ikont-po-tb");
        setWidth(980);

    }

    public String retornarNuevoContrato() {
        return "/zoom/Supervisor/nuevoContratoPlanOperativo.xhtml";
    }
//

    public String retornarConfiguracionPagina() {
        return "menubar=si, location=false, resizable=no, scrollbars=si, status=no, dependent=true";
    }
}
