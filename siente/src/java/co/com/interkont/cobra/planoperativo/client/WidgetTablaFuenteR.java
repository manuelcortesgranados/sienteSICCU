package co.com.interkont.cobra.planoperativo.client;

import co.com.interkont.cobra.planoperativo.client.dto.ActividadobraDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ContratoDTO;
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
import com.sencha.gxt.widget.core.client.info.Info;
import java.math.BigDecimal;
import java.util.Iterator;

public class WidgetTablaFuenteR implements IsWidget {

    protected ContratoDTO contrato;
    protected ActividadobraDTO actividadObraPadre;

    /**
     * @return the store
     */
    public WidgetTablaFuenteR(ContratoDTO contrato,ActividadobraDTO actividadObraPadre) {
        this.contrato = contrato;
        this.actividadObraPadre=actividadObraPadre;
    }

    public ListStore<RelacionobrafuenterecursoscontratoDTO> getStore() {
        return store;
    }

    /**
     * @param store the store to set
     */
    public void setStore(ListStore<RelacionobrafuenterecursoscontratoDTO> store) {
        this.store = store;
    }

    interface PlaceProperties extends PropertyAccess<RelacionobrafuenterecursoscontratoDTO> {

        @Path("idrelacionobracontrato")
        ModelKeyProvider<RelacionobrafuenterecursoscontratoDTO> key();

        ValueProvider<RelacionobrafuenterecursoscontratoDTO, Integer> idrelacionobracontrato();

        ValueProvider<RelacionobrafuenterecursoscontratoDTO, BigDecimal> valor();

        ValueProvider<RelacionobrafuenterecursoscontratoDTO, String> nombreTipo();

        ValueProvider<RelacionobrafuenterecursoscontratoDTO, String> nombreEntidad();

        ValueProvider<RelacionobrafuenterecursoscontratoDTO, String> eliminar();
    }
    private static final PlaceProperties properties = GWT.create(PlaceProperties.class);
    private ListStore<RelacionobrafuenterecursoscontratoDTO> store;

    @Override
    public Widget asWidget() {

        SafeStyles textStyles = SafeStylesUtils.fromTrustedString("padding: 1px 3px;");

        ColumnConfig<RelacionobrafuenterecursoscontratoDTO, String> nameColumn = new ColumnConfig<RelacionobrafuenterecursoscontratoDTO, String>(properties.nombreEntidad(), 120, "Entidad");
        nameColumn.setColumnTextClassName(CommonStyles.get().inlineBlock());
        nameColumn.setColumnTextStyle(textStyles);


        ColumnConfig<RelacionobrafuenterecursoscontratoDTO, String> tipoAporte = new ColumnConfig<RelacionobrafuenterecursoscontratoDTO, String>(properties.nombreTipo(), 100, "Tipo aporte");
        tipoAporte.setColumnTextStyle(textStyles);

        ColumnConfig<RelacionobrafuenterecursoscontratoDTO, BigDecimal> valor = new ColumnConfig<RelacionobrafuenterecursoscontratoDTO, BigDecimal>(properties.valor(), 100, "Valor");
        tipoAporte.setColumnTextStyle(textStyles);

        ColumnConfig<RelacionobrafuenterecursoscontratoDTO, String> eliminar = new ColumnConfig<RelacionobrafuenterecursoscontratoDTO, String>(properties.eliminar(), 100, "Eliminar");
        nameColumn.setColumnTextClassName(CommonStyles.get().inlineBlock());
        nameColumn.setColumnTextStyle(textStyles);

        TextButtonCell button = new TextButtonCell();
        button.addSelectHandler(new SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                Context c = event.getContext();
                int row = c.getIndex();
                buscarFuenteDto(store.get(row).getObrafuenterecursosconvenios(),store.get(row).getValor());
                contrato.getRelacionobrafuenterecursoscontratos().remove(store.get(row));
                getStore().remove(store.get(row));

            }
        });
        eliminar.setCell(button);

        List<ColumnConfig<RelacionobrafuenterecursoscontratoDTO, ?>> l = new ArrayList<ColumnConfig<RelacionobrafuenterecursoscontratoDTO, ?>>();
        l.add(nameColumn);
        l.add(tipoAporte);
        l.add(valor);
        l.add(eliminar);

        ColumnModel<RelacionobrafuenterecursoscontratoDTO> cm = new ColumnModel<RelacionobrafuenterecursoscontratoDTO>(l);
        setStore(new ListStore<RelacionobrafuenterecursoscontratoDTO>(properties.key()));

        List<RelacionobrafuenterecursoscontratoDTO> plants = new ArrayList<RelacionobrafuenterecursoscontratoDTO>(contrato.getRelacionobrafuenterecursoscontratos());
        getStore().addAll(plants);

        final Grid<RelacionobrafuenterecursoscontratoDTO> grid = new Grid<RelacionobrafuenterecursoscontratoDTO>(getStore(), cm);
        grid.setBorders(true);
        grid.getView().setAutoExpandColumn(nameColumn);
        grid.getView().setTrackMouseOver(false);
        grid.getView().setEmptyText("Por favor ingrese las fuentes de recursos");
        grid.getView().setColumnLines(true);

        FramedPanel cp = new FramedPanel();
        cp.setHeadingText("*FUENTES DE RECURSOS");
        cp.setWidget(grid);
        cp.setCollapsible(true);
        cp.setAnimCollapse(true);
        cp.setPixelSize(500, 150);
        cp.addStyleName("margin-10");

        return cp;
    }

    public void buscarFuenteDto(ObrafuenterecursosconveniosDTO obraFuenteRecurso, BigDecimal valor) {
        for (Iterator it = actividadObraPadre.getObra().getObrafuenterecursosconvenioses().iterator(); it.hasNext();) {
            ObrafuenterecursosconveniosDTO frc = (ObrafuenterecursosconveniosDTO) it.next();
            if (obraFuenteRecurso.equals(frc)) {
                frc.getValorDisponible().add(frc.getValorDisponible().add(valor));
            }
        }

    }
}