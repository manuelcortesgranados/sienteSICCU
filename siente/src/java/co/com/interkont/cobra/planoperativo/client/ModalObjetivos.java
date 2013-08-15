/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client;

import co.com.interkont.cobra.planoperativo.client.dto.ObjetivosDTO;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daniela
 */
public class ModalObjetivos implements IsWidget, EntryPoint {

    @Override
    public Widget asWidget() {

        ObjetivosProperties propsObj = GWT.create(ObjetivosProperties.class);
//         MontosProperties propsMonto= GWT.create(MontosProperties.class);
//         MacroActiProperties propsMacro= GWT.create(MacroActiProperties.class);

        ColumnConfig<ObjetivosDTO, String> descripcion = new ColumnConfig<ObjetivosDTO, String>(propsObj.descripcion(), 150, "Descripcion");
        ColumnConfig<ObjetivosDTO, String> strtipoObj = new ColumnConfig<ObjetivosDTO, String>(propsObj.strtipoObj(), 150, "Tipo");

        List<ColumnConfig<ObjetivosDTO, ?>> l = new ArrayList<ColumnConfig<ObjetivosDTO, ?>>();
        l.add(descripcion);
        l.add(strtipoObj);
        
        ColumnModel<ObjetivosDTO> cm = new ColumnModel<ObjetivosDTO>(l);
        
//        ListStore<ObjetivosDTO> store = new ListStore<ObjetivosDTO>(propsObj.posicion());

        return null;
    }

    @Override
    public void onModuleLoad() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
