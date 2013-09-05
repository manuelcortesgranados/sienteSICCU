package co.com.interkont.cobra.planoperativo.client;

import co.com.interkont.cobra.planoperativo.client.dto.ActividadobraDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ContratoDTO;
import co.com.interkont.cobra.planoperativo.client.dto.MontoDTO;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.core.client.GWT;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.safecss.shared.SafeStyles;
import com.google.gwt.safecss.shared.SafeStylesUtils;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.TextButtonCell;
import com.sencha.gxt.core.client.IdentityValueProvider;
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
import java.math.BigDecimal;

public class WidgetTablaMontos implements IsWidget {

    protected ContratoDTO contrato;
    /**
     * @return the store
     */
    public WidgetTablaMontos(ContratoDTO contrato) {
        this.contrato = contrato;
    }

    public ListStore<MontoDTO> getStore() {
        return store;
    }

    /**
     * @param store the store to set
     */
    public void setStore(ListStore<MontoDTO> store) {
        this.store = store;
    }

    interface PlaceProperties extends PropertyAccess<MontoDTO> {

        @Path("idmonto")
        ModelKeyProvider<MontoDTO> key();

        ValueProvider<MontoDTO, Integer> idmonto();

        ValueProvider<MontoDTO, BigDecimal> valor();

        ValueProvider<MontoDTO, String> descripcionRubro();

        ValueProvider<MontoDTO, String> eliminar();

        ValueProvider<MontoDTO, Integer> vigencia();
    }
    private static final PlaceProperties properties = GWT.create(PlaceProperties.class);
    private ListStore<MontoDTO> store;

    @Override
    public Widget asWidget() {

        SafeStyles textStyles = SafeStylesUtils.fromTrustedString("padding: 1px 3px;");


        ColumnConfig<MontoDTO, String> nameColumn = new ColumnConfig<MontoDTO, String>(properties.descripcionRubro(), 120, "Rubro");
        nameColumn.setColumnTextClassName(CommonStyles.get().inlineBlock());
        nameColumn.setColumnTextStyle(textStyles);


        ColumnConfig<MontoDTO, BigDecimal> valor = new ColumnConfig<MontoDTO, BigDecimal>(properties.valor(), 100, "Valor");
        valor.setColumnTextStyle(textStyles);

        ColumnConfig<MontoDTO, Integer> vigencia = new ColumnConfig<MontoDTO, Integer>(properties.vigencia(), 100, "Vigencia");
        vigencia.setColumnTextStyle(textStyles);

        ColumnConfig<MontoDTO, String> eliminar = new ColumnConfig<MontoDTO, String>(properties.eliminar(), 100, "Eliminar");
        nameColumn.setColumnTextClassName(CommonStyles.get().inlineBlock());
        nameColumn.setColumnTextStyle(textStyles);

        TextButtonCell button = new TextButtonCell();
        button.addSelectHandler(new SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                Context c = event.getContext();
                int row = c.getIndex();
                contrato.getMontos().remove(store.get(row));
                getStore().remove(store.get(row));
            }
        });
        eliminar.setCell(button);

        List<ColumnConfig<MontoDTO, ?>> l = new ArrayList<ColumnConfig<MontoDTO, ?>>();
        l.add(nameColumn);
        l.add(valor);
        l.add(vigencia);
        l.add(eliminar);

        ColumnModel<MontoDTO> cm = new ColumnModel<MontoDTO>(l);

        setStore(new ListStore<MontoDTO>(properties.key()));

        List<MontoDTO> plants = new ArrayList<MontoDTO>(contrato.getMontos());
        getStore().addAll(plants);

        final Grid<MontoDTO> grid = new Grid<MontoDTO>(getStore(), cm);
        grid.setBorders(true);
        grid.getView().setAutoExpandColumn(nameColumn);
        grid.getView().setTrackMouseOver(false);
        grid.getView().setEmptyText("Por favor ingrese los montos del contrato");
        grid.getView().setColumnLines(true);

        FramedPanel cp = new FramedPanel();
        cp.setHeadingText("*Rubros");
        cp.setWidget(grid);
        cp.setCollapsible(true);
        cp.setAnimCollapse(true);
        cp.setExpanded(true);
        cp.setPixelSize(500, 150);
        cp.addStyleName("margin-10");

        return cp;
    }
}