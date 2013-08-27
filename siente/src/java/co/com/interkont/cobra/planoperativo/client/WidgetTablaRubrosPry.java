package co.com.interkont.cobra.planoperativo.client;

import co.com.interkont.cobra.planoperativo.client.dto.ActividadobraDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ContratoDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ObraDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ObrafuenterecursosconveniosDTO;
import co.com.interkont.cobra.planoperativo.client.dto.RelacionobrafuenterecursoscontratoDTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.i18n.shared.DateTimeFormat.PredefinedFormat;
import com.google.gwt.safecss.shared.SafeStyles;
import com.google.gwt.safecss.shared.SafeStylesUtils;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.ProgressBarCell;
import com.sencha.gxt.cell.core.client.ResizeCell;
import com.sencha.gxt.cell.core.client.SliderCell;
import com.sencha.gxt.cell.core.client.TextButtonCell;
import com.sencha.gxt.cell.core.client.form.CheckBoxCell;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.cell.core.client.form.DateCell;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.resources.CommonStyles;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.theme.base.client.colorpalette.ColorPaletteBaseAppearance;
import com.sencha.gxt.widget.core.client.ColorPaletteCell;
import com.sencha.gxt.widget.core.client.ColorPaletteCell.ColorPaletteAppearance;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.AbstractHtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.event.CellSelectionEvent;
import com.sencha.gxt.widget.core.client.event.ColumnWidthChangeEvent;
import com.sencha.gxt.widget.core.client.event.ColumnWidthChangeEvent.ColumnWidthChangeHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.DateTimePropertyEditor;
import com.sencha.gxt.widget.core.client.grid.CheckBoxSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import java.math.BigDecimal;

public class WidgetTablaRubrosPry implements IsWidget {

    protected ObraDTO obraDto;
    protected ActividadobraDTO actividadObraPadre;

    /**
     * @return the store
     */
    public WidgetTablaRubrosPry(ObraDTO obraDto, ActividadobraDTO actividadObraPadre) {
        this.obraDto = obraDto;
        this.actividadObraPadre = actividadObraPadre;
    }

    public ListStore<ObrafuenterecursosconveniosDTO> getStore() {
        return store;
    }

    /**
     * @param store the store to set
     */
    public void setStore(ListStore<ObrafuenterecursosconveniosDTO> store) {
        this.store = store;
    }

    interface PlaceProperties extends PropertyAccess<ObrafuenterecursosconveniosDTO> {

        @Path("idobrafuenterecursos")
        ModelKeyProvider<ObrafuenterecursosconveniosDTO> key();

        ValueProvider<ObrafuenterecursosconveniosDTO, Integer> idobrafuenterecursos();

        ValueProvider<ObrafuenterecursosconveniosDTO, BigDecimal> valor();

        ValueProvider<ObrafuenterecursosconveniosDTO, String> rubro();

        ValueProvider<ObrafuenterecursosconveniosDTO, String> nombreEntidad();

        ValueProvider<ObrafuenterecursosconveniosDTO, String> eliminar();

        ValueProvider<ObrafuenterecursosconveniosDTO, String> descripcionaporte();

        ValueProvider<ObrafuenterecursosconveniosDTO, Integer> vigencia();
    }
    private static final PlaceProperties properties = GWT.create(PlaceProperties.class);
    private ListStore<ObrafuenterecursosconveniosDTO> store;

    @Override
    public Widget asWidget() {

        SafeStyles textStyles = SafeStylesUtils.fromTrustedString("padding: 1px 3px;");

        ColumnConfig<ObrafuenterecursosconveniosDTO, String> nameColumn = new ColumnConfig<ObrafuenterecursosconveniosDTO, String>(properties.nombreEntidad(), 120, "Entidad");
        nameColumn.setColumnTextClassName(CommonStyles.get().inlineBlock());
        nameColumn.setColumnTextStyle(textStyles);

        ColumnConfig<ObrafuenterecursosconveniosDTO, String> rubro = new ColumnConfig<ObrafuenterecursosconveniosDTO, String>(properties.rubro(), 100, "Rubro");
        rubro.setColumnTextStyle(textStyles);

        ColumnConfig<ObrafuenterecursosconveniosDTO, String> tipoAporte = new ColumnConfig<ObrafuenterecursosconveniosDTO, String>(properties.descripcionaporte(), 75, "Tipo aporte");
        tipoAporte.setColumnTextStyle(textStyles);

        ColumnConfig<ObrafuenterecursosconveniosDTO, Integer> vigencia = new ColumnConfig<ObrafuenterecursosconveniosDTO, Integer>(properties.vigencia(), 55, "Vigencia");
        vigencia.setColumnTextStyle(textStyles);

        ColumnConfig<ObrafuenterecursosconveniosDTO, BigDecimal> valor = new ColumnConfig<ObrafuenterecursosconveniosDTO, BigDecimal>(properties.valor(), 85, "Valor");
        tipoAporte.setColumnTextStyle(textStyles);

        ColumnConfig<ObrafuenterecursosconveniosDTO, String> eliminar = new ColumnConfig<ObrafuenterecursosconveniosDTO, String>(properties.eliminar(), 50, "Eliminar");
        nameColumn.setColumnTextClassName(CommonStyles.get().inlineBlock());
        nameColumn.setColumnTextStyle(textStyles);

        TextButtonCell button = new TextButtonCell();
        button.addSelectHandler(new SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                Context c = event.getContext();
                int row = c.getIndex();
                if(store.get(row).getTipoaporte()==0){
                obraDto.setValor(obraDto.getValor().subtract(store.get(row).getValor()));
                obraDto.setValorDisponible(obraDto.getValor());
                }
                obraDto.getObrafuenterecursosconvenioses().remove(store.get(row));
                getStore().remove(store.get(row));

            }
        });
        eliminar.setCell(button);

        List<ColumnConfig<ObrafuenterecursosconveniosDTO, ?>> l = new ArrayList<ColumnConfig<ObrafuenterecursosconveniosDTO, ?>>();
        l.add(nameColumn);
        l.add(rubro);
        l.add(tipoAporte);
        l.add(valor);
        l.add(vigencia);
        l.add(eliminar);

        ColumnModel<ObrafuenterecursosconveniosDTO> cm = new ColumnModel<ObrafuenterecursosconveniosDTO>(l);
        setStore(new ListStore<ObrafuenterecursosconveniosDTO>(properties.key()));

        List<ObrafuenterecursosconveniosDTO> plants = new ArrayList<ObrafuenterecursosconveniosDTO>(obraDto.getObrafuenterecursosconvenioses());
        getStore().addAll(plants);

        final Grid<ObrafuenterecursosconveniosDTO> grid = new Grid<ObrafuenterecursosconveniosDTO>(getStore(), cm);
        grid.setBorders(true);
        grid.getView().setAutoExpandColumn(nameColumn);
        grid.getView().setTrackMouseOver(false);
        grid.getView().setEmptyText("Por favor ingrese los roles y enidades");
        grid.getView().setColumnLines(true);

        FramedPanel cp = new FramedPanel();
        cp.setHeadingText("*ANADIR ROLES Y ENTIDADES:");
        cp.setWidget(grid);
        cp.setCollapsible(true);
        cp.setAnimCollapse(true);
        cp.setExpanded(false);
        cp.setPixelSize(500, 150);
        cp.addStyleName("margin-10");

        return cp;
    }
}