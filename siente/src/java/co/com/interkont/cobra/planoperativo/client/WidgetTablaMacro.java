package co.com.interkont.cobra.planoperativo.client;

import co.com.interkont.cobra.planoperativo.client.dto.ActividadobraDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ObraDTO;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.safecss.shared.SafeStyles;
import com.google.gwt.safecss.shared.SafeStylesUtils;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.TextButtonCell;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.resources.CommonStyles;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import java.util.Iterator;

public class WidgetTablaMacro implements IsWidget {

    protected ObraDTO obraDto;
    protected ActividadobraDTO actividadObraPadre;

    /**
     * @return the store
     */
    public WidgetTablaMacro(ObraDTO obraDto, ActividadobraDTO actividadObraPadre) {
        this.obraDto = obraDto;
        this.actividadObraPadre = actividadObraPadre;
    }

    public ListStore<ActividadobraDTO> getStore() {
        return store;
    }

    /**
     * @param store the store to set
     */
    public void setStore(ListStore<ActividadobraDTO> store) {
        this.store = store;
    }

    interface PlaceProperties extends PropertyAccess<ActividadobraDTO> {

        @Path("id")
        ModelKeyProvider<ActividadobraDTO> key();

        ValueProvider<ActividadobraDTO, String> name();

        ValueProvider<ActividadobraDTO, String> eliminar();
    }
    private static final PlaceProperties properties = GWT.create(PlaceProperties.class);
    private ListStore<ActividadobraDTO> store;

    @Override
    public Widget asWidget() {

        SafeStyles textStyles = SafeStylesUtils.fromTrustedString("padding: 1px 3px;");

        ColumnConfig<ActividadobraDTO, String> nameColumn = new ColumnConfig<ActividadobraDTO, String>(properties.name(), 157, "Descripción");
        nameColumn.setColumnTextClassName(CommonStyles.get().inlineBlock());
        nameColumn.setColumnTextStyle(textStyles);
        nameColumn.setResizable(false);

        ColumnConfig<ActividadobraDTO, String> eliminar = new ColumnConfig<ActividadobraDTO, String>(properties.eliminar(), 55, "Eliminar");
        nameColumn.setColumnTextClassName(CommonStyles.get().inlineBlock());
        nameColumn.setColumnTextStyle(textStyles);
        nameColumn.setResizable(false);

        TextButtonCell button = new TextButtonCell();
        button.addSelectHandler(new SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                Context c = event.getContext();
                int row = c.getIndex();
                obraDto.getActividadobras().remove(store.get(row));
                getStore().remove(store.get(row));

            }
        });
        eliminar.setCell(button);

        List<ColumnConfig<ActividadobraDTO, ?>> l = new ArrayList<ColumnConfig<ActividadobraDTO, ?>>();
        l.add(nameColumn);
        l.add(eliminar);

        ColumnModel<ActividadobraDTO> cm = new ColumnModel<ActividadobraDTO>(l);
        setStore(new ListStore<ActividadobraDTO>(properties.key()));

        List<ActividadobraDTO> plants = new ArrayList<ActividadobraDTO>(cargarMacroActividades());
        getStore().addAll(plants);

        final Grid<ActividadobraDTO> grid = new Grid<ActividadobraDTO>(getStore(), cm);
        grid.setBorders(true);
        grid.getView().setAutoExpandColumn(nameColumn);
        grid.getView().setTrackMouseOver(false);
        grid.getView().setEmptyText("Por favor ingrese las macro actividades");
        grid.getView().setColumnLines(true);

        FramedPanel cp = new FramedPanel();
        cp.setHeadingText("MACROACTIVIDADES");
        cp.setWidget(grid);
        cp.setCollapsible(true);
        cp.setExpanded(true);
        cp.setAnimCollapse(true);
        cp.setPixelSize(600, 130);
        cp.addStyleName("margin-10");

        return cp;
    }

    public List cargarMacroActividades() {
        List<ActividadobraDTO> lstMacroActividades = new ArrayList<ActividadobraDTO>();
        for (Iterator it = obraDto.getActividadobras().iterator(); it.hasNext();) {
            ActividadobraDTO actividadobraDTO = (ActividadobraDTO) it.next();            
            if (actividadobraDTO.getTipoActividad() == 7) {
                lstMacroActividades.add(actividadobraDTO);
                
            }
        }
        return lstMacroActividades;
    }
}