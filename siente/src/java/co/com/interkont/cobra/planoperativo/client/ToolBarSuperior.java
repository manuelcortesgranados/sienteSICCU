/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos Loaiza
 */
public class ToolBarSuperior extends ToolBar {

    
    final Button finalizarbasicos = new Button();
    final Button guardarborrador = new Button();

    public ToolBarSuperior(final CobraGwtServiceAbleAsync service, final TreeStore<ActividadobraDTO> taskStore, final ContratoDTO convenio, final ListStore<DependenciaDTO> depStore) {
       

        finalizarbasicos.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });

        guardarborrador.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        finalizarbasicos.setStyleName("ikont-po-img-posicion-borrador-finalizarGWT ikont-po-img-finalizarGWT");
        guardarborrador.setStyleName("ikont-po-img-posicion-borrador-finalizarGWT ikont-po-img-borradorGWT");
        add(finalizarbasicos);
        add(guardarborrador);
        setStyleName("ikont-po-tb");
        setWidth(1000);

    }

    public String retornarNuevoContrato() {

        return "/zoom/Supervisor/nuevoContratoPlanOperativo.xhtml";

    }

    public String retornarConfiguracionPagina() {
        return "menubar=si, location=false, resizable=no, scrollbars=si, status=no, dependent=true";
    }

    private void addStyleOnOver(Button finalizarbasicos, String ff) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
