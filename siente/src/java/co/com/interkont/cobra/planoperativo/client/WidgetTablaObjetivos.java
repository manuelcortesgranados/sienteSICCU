package co.com.interkont.cobra.planoperativo.client;

import co.com.interkont.cobra.planoperativo.client.dto.ActividadobraDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ObjetivosDTO;
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
        cp.setExpanded(true);
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
                    if(obj.getTipoobjetivo()!=1){
                    lst.add(obj);
                    }
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