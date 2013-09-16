/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client;

import co.com.interkont.cobra.planoperativo.client.dto.ActividadobraDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ContratoDTO;
import co.com.interkont.cobra.planoperativo.client.dto.DependenciaDTO;
import co.com.interkont.cobra.planoperativo.client.resources.images.ExampleImages;
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAbleAsync;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

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
        this.planOperativo = planOperativo;
        service.setLog("aca estoy", null);
    }

    @Override
    public Widget asWidget() {
        ToolBar toolBarSuperior = new ToolBar();
        Button finalizarbasicos = new Button();
        Button guardarborrador = new Button();
        finalizarbasicos.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Window fullScreen = new Window();
                fullScreen.setBlinkModal(true);
                fullScreen.setModal(true);
                fullScreen.setWidth(1000);
                fullScreen.setHeight(550);
//                FlowLayoutContainer main = new FlowLayoutContainer();
//                main.getElement().setMargins(new Margins(0, 0, 0, 5));
//                main.setWidth(1000);
//                ContentPanel cp = new ContentPanel();
//                cp.setHeadingText("holaaaa");
//                cp.getHeader().setIcon(ExampleImages.INSTANCE.table());
//                cp.setPixelSize(1000, 800);
//                cp.getElement().setMargins(new Margins(5));
//                service.setLog("entre en 11", null);
//                VerticalLayoutContainer vc = new VerticalLayoutContainer();
//                cp.setWidget(vc);
//                vc.add(planOperativo.createToolBarPeriodo());
//                vc.add(planOperativo.createToolBar(taskStore), new VerticalLayoutContainer.VerticalLayoutData(1, -1));
//                vc.add(planOperativo.getGantt(), new VerticalLayoutContainer.VerticalLayoutData(1, 1));
//                main.setPagePosition(0, 0);
//                main.add(cp); 
//                service.setLog("entre en 12", null);
//                planOperativo.main=main;
//                fullScreen.add(planOperativo);
//                fullScreen.show();
//                service.setLog("entre en 1", null);
                fullScreen.add(planOperativo.asWidget());
                fullScreen.show();
            }
        });

        guardarborrador.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
//                service.setLog("entre en 2", null);
//                DialogBox fullScreen = new DialogBox();
//                VerticalPanel dialogContents = new VerticalPanel();
//                dialogContents.setSpacing(4);
//                fullScreen.setWidget(dialogContents);
//                dialogContents.add(planOperativo.asWidget());
//                fullScreen.setGlassEnabled(true);
//                fullScreen.setAnimationEnabled(true);
//                fullScreen.show();
                service.setLog("sali en 2", null);
            }
        });
        finalizarbasicos.setStyleName("ikont-po-img-posicion-borrador-finalizarGWT ikont-po-img-finalizarGWT");
        guardarborrador.setStyleName("ikont-po-img-posicion-borrador-finalizarGWT ikont-po-img-borradorGWT");
        toolBarSuperior.add(finalizarbasicos);
        toolBarSuperior.add(guardarborrador);
        toolBarSuperior.setStyleName("ikont-po-tb");
        toolBarSuperior.setWidth(980);
        return toolBarSuperior;
    }
}
