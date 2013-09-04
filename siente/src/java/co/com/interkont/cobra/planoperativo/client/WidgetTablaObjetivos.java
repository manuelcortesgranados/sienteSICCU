package co.com.interkont.cobra.planoperativo.client;

import co.com.interkont.cobra.planoperativo.client.dto.ActividadobraDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ContratoDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ObjetivosDTO;
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
import java.util.Iterator;

public class WidgetTablaObjetivos implements IsWidget {

    protected ObraDTO obraDto;
    protected ActividadobraDTO actividadObraPadre;
    protected String emptyText;
    protected String nombreTbl;
    protected boolean esObjetivo;
    protected boolean esAct;

    /**
     * @return the store
     */
    public WidgetTablaObjetivos(ObraDTO obraDto, ActividadobraDTO actividadObraPadre, String emptyText, String nombreTbl, boolean esObjetivo) {
        this.obraDto = obraDto;
        this.actividadObraPadre = actividadObraPadre;
        this.emptyText = emptyText;
        this.nombreTbl = nombreTbl;
        this.esObjetivo = esObjetivo;
    }

    public ListStore<ObjetivosDTO> getStore() {
        return store;
    }

    /**
     * @param store the store to set
     */
    public void setStore(ListStore<ObjetivosDTO> store) {
        this.store = store;
    }

    interface PlaceProperties extends PropertyAccess<ObjetivosDTO> {

        @Path("idobjetivo")
        ModelKeyProvider<ObjetivosDTO> key();

        ValueProvider<ObjetivosDTO, Integer> idobjetivo();

        ValueProvider<ObjetivosDTO, String> descripcion();

        ValueProvider<ObjetivosDTO, String> strtipoObj();

        ValueProvider<ObjetivosDTO, String> eliminar();
    }
    private static final PlaceProperties properties = GWT.create(PlaceProperties.class);
    private ListStore<ObjetivosDTO> store;

    @Override
    public Widget asWidget() {

        SafeStyles textStyles = SafeStylesUtils.fromTrustedString("padding: 1px 3px;");

        ColumnConfig<ObjetivosDTO, String> nameColumn = new ColumnConfig<ObjetivosDTO, String>(properties.descripcion(), 157, "Descripción");
        nameColumn.setColumnTextClassName(CommonStyles.get().inlineBlock());
        nameColumn.setResizable(false);
        nameColumn.setColumnTextStyle(textStyles);


        ColumnConfig<ObjetivosDTO, String> eliminar = new ColumnConfig<ObjetivosDTO, String>(properties.eliminar(), 55, "Eliminar");
        eliminar.setResizable(false);
        nameColumn.setColumnTextClassName(CommonStyles.get().inlineBlock());
        nameColumn.setColumnTextStyle(textStyles);

        TextButtonCell button = new TextButtonCell();
        button.addSelectHandler(new SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                Context c = event.getContext();
                int row = c.getIndex();
                obraDto.getObjetivoses().remove(store.get(row));
                getStore().remove(store.get(row));

            }
        });
        eliminar.setCell(button);

        List<ColumnConfig<ObjetivosDTO, ?>> l = new ArrayList<ColumnConfig<ObjetivosDTO, ?>>();
        l.add(nameColumn);
        l.add(eliminar);

        ColumnModel<ObjetivosDTO> cm = new ColumnModel<ObjetivosDTO>(l);
        setStore(new ListStore<ObjetivosDTO>(properties.key()));

        List<ObjetivosDTO> plants = new ArrayList<ObjetivosDTO>(cargarInformacionEspecificos());
        getStore().addAll(plants);

        final Grid<ObjetivosDTO> grid = new Grid<ObjetivosDTO>(getStore(), cm);
        grid.setBorders(true);
        grid.getView().setAutoExpandColumn(nameColumn);
        grid.getView().setTrackMouseOver(false);
        grid.getView().setEmptyText(emptyText);
        grid.getView().setColumnLines(true);

        FramedPanel cp = new FramedPanel();
        cp.setHeadingText(nombreTbl);
        cp.setWidget(grid);
        cp.setCollapsible(true);
        cp.setExpanded(false);
        cp.setAnimCollapse(true);
        cp.setPixelSize(215, 150);
        cp.addStyleName("margin-10");

        return cp;
    }

    public List cargarInformacionEspecificos() {
        List<ObjetivosDTO> lst = new ArrayList<ObjetivosDTO>();
        if (esObjetivo) {
            for (Iterator it = obraDto.getObjetivoses().iterator(); it.hasNext();) {
                ObjetivosDTO obj = (ObjetivosDTO) it.next();
                if (obj.getEsobjetivo()) {
                    lst.add(obj);
                }
            }
        } else {
            for (Iterator it = obraDto.getObjetivoses().iterator(); it.hasNext();) {
                ObjetivosDTO obj = (ObjetivosDTO) it.next();
                if (!obj.getEsobjetivo()) {
                    lst.add(obj);
                }
            }

        }
        return lst;
    }
}