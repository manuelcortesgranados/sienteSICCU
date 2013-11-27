/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client;

import co.com.interkont.cobra.planoperativo.client.dto.ActividadobraDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ContratoDTO;
import co.com.interkont.cobra.planoperativo.client.dto.DependenciaDTO;
import co.com.interkont.cobra.planoperativo.client.dto.GanttDatos;
import co.com.interkont.cobra.planoperativo.client.resources.images.ExampleImages;
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAbleAsync;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;
import com.google.gwt.user.client.Window;

/**
 *
 * @author Daniela
 */
public class ToolBarSuperior implements IsWidget {

    protected CobraGwtServiceAbleAsync service;
    protected TreeStore<ActividadobraDTO> taskStore;
    protected ContratoDTO convenio;
    protected ListStore<DependenciaDTO> depStore;
    protected PlanOperativoGantt planOperativo;

    public ToolBarSuperior(CobraGwtServiceAbleAsync service, TreeStore<ActividadobraDTO> taskStore, ContratoDTO convenio, ListStore<DependenciaDTO> depStore, PlanOperativoGantt planOperativo) {
        this.service = service;
        this.taskStore = taskStore;
        this.convenio = convenio;
        this.depStore = depStore;

    }

    @Override
    public Widget asWidget() {
        ToolBar toolBarSuperior = new ToolBar();
        Button finalizarbasicos = new Button();
        Button guardarborrador = new Button();
        Button fullScreen = new Button();
       
        finalizarbasicos.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                service.setContratoDto(GanttDatos.estructurarDatosConvenio(convenio, taskStore, service, depStore), new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        service.setLog(caught.getMessage(), null);
                    }

                    @Override
                    public void onSuccess(Boolean result) {

                        service.setNavegacion(2, new AsyncCallback<Boolean>() {
                            @Override
                            public void onFailure(Throwable caught) {
                                service.setLog(caught.getMessage(), null);
                            }

                            @Override
                            public void onSuccess(Boolean result) {
                                service.setGuardarconvenio(2, new AsyncCallback<Boolean>() {
                                    @Override
                                    public void onFailure(Throwable caught) {
                                        service.setLog(caught.getMessage(), null);
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

        guardarborrador.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                service.setContratoDto(GanttDatos.estructurarDatosConvenio(convenio, taskStore, service, depStore), new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        service.setLog(caught.getMessage(), null);
                    }

                    @Override
                    public void onSuccess(Boolean result) {
                        service.setNavegacion(2, new AsyncCallback<Boolean>() {
                            @Override
                            public void onFailure(Throwable caught) {
                                service.setLog(caught.getMessage(), null);
                            }

                            @Override
                            public void onSuccess(Boolean result) {
                                service.setGuardarconvenio(1, new AsyncCallback<Boolean>() {
                                    @Override
                                    public void onFailure(Throwable caught) {
                                        service.setLog(caught.getMessage(), null);
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
       
        fullScreen.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                service.setFullScreen(true, new AsyncCallback<Void>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public void onSuccess(Void result) {
                        service.setContratoDto(GanttDatos.estructurarDatosConvenio(convenio, taskStore, service, depStore), new AsyncCallback<Boolean>() {
                            @Override
                            public void onFailure(Throwable caught) {
                                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                            }

                            @Override
                            public void onSuccess(Boolean result) {
                               
                                Window.open(retornarPOFullScreen(), "_parent", retornarConfiguracionPagina());
                              
                            }
                        });
                    }
                });


            }
        });

        finalizarbasicos.setStyleName("ikont-po-img-posicion-borrador-finalizarGWT ikont-po-img-finalizarGWT");
        guardarborrador.setStyleName("ikont-po-img-posicion-borrador-finalizarGWT ikont-po-img-borradorGWT");
        fullScreen.setStyleName("ikont-po-img-posicion-borrador-finalizarGWT ikont-po-img-fullscreen-GWT");
        toolBarSuperior.add(finalizarbasicos);
        toolBarSuperior.add(guardarborrador);
        toolBarSuperior.add(fullScreen);
        toolBarSuperior.setStyleName("ikont-po-tb");
        toolBarSuperior.setWidth(980);
        return toolBarSuperior;
    }

    public String retornarNuevoContrato() {

        return "/zoom/Supervisor/nuevoContratoPlanOperativo.xhtml";

    }
    
     public String retornarPOFullScreen() {

        return "/zoom/Supervisor/PlanOFullScreen.xhtml";

    }

    public String retornarConfiguracionPagina() {
        return "menubar=no, location=false, resizable=no, scrollbars=si, status=no, dependent=true, fullscreen=yes";
    }
}
