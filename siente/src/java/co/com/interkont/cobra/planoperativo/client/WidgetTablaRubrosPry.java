package co.com.interkont.cobra.planoperativo.client;

import co.com.interkont.cobra.planoperativo.client.dto.ActividadobraDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ContratoDTO;
import co.com.interkont.cobra.planoperativo.client.dto.FuenterecursosconvenioDTO;
import co.com.interkont.cobra.planoperativo.client.dto.GanttDatos;
import co.com.interkont.cobra.planoperativo.client.dto.ObraDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ObrafuenterecursosconveniosDTO;
import co.com.interkont.cobra.planoperativo.client.dto.RelacionobrafuenterecursoscontratoDTO;
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAble;
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAbleAsync;
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
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import java.util.Iterator;
import java.util.Set;

public class WidgetTablaRubrosPry implements IsWidget {
    
    protected ObraDTO obraDto;
    protected ActividadobraDTO actividadObraPadre;
    protected ActividadobraDTO actividadObraEditar;
    protected TreeStore<ActividadobraDTO> taskStore;
    protected boolean editar;
    private CobraGwtServiceAbleAsync service = GWT.create(CobraGwtServiceAble.class);

    /**
     * @return the store
     */
    public WidgetTablaRubrosPry(ObraDTO obraDto, ActividadobraDTO actividadObraPadre, TreeStore<ActividadobraDTO> taskStore, boolean editar, ActividadobraDTO actividadObraEditar) {
        this.obraDto = obraDto;
        this.actividadObraPadre = actividadObraPadre;
        this.taskStore = taskStore;
        this.editar = editar;
        this.actividadObraEditar = actividadObraEditar;
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
        
        ValueProvider<ObrafuenterecursosconveniosDTO, String> valorFormato();
        
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
        
        ColumnConfig<ObrafuenterecursosconveniosDTO, String> nameColumn = new ColumnConfig<ObrafuenterecursosconveniosDTO, String>(properties.nombreEntidad(), 278, "Entidad");
        nameColumn.setColumnTextClassName(CommonStyles.get().inlineBlock());
        nameColumn.setColumnTextStyle(textStyles);
        
        ColumnConfig<ObrafuenterecursosconveniosDTO, String> rubro = new ColumnConfig<ObrafuenterecursosconveniosDTO, String>(properties.rubro(), 100, "Rubro");
        rubro.setColumnTextStyle(textStyles);
        
        ColumnConfig<ObrafuenterecursosconveniosDTO, String> tipoAporte = new ColumnConfig<ObrafuenterecursosconveniosDTO, String>(properties.descripcionaporte(), 75, "Tipo aporte");
        tipoAporte.setColumnTextStyle(textStyles);
        
        ColumnConfig<ObrafuenterecursosconveniosDTO, Integer> vigencia = new ColumnConfig<ObrafuenterecursosconveniosDTO, Integer>(properties.vigencia(), 55, "Vigencia");
        vigencia.setColumnTextStyle(textStyles);
        
        ColumnConfig<ObrafuenterecursosconveniosDTO, String> valor = new ColumnConfig<ObrafuenterecursosconveniosDTO, String>(properties.valorFormato(), 85, "Valor");
        valor.setColumnTextStyle(textStyles);
        
        
        ColumnConfig<ObrafuenterecursosconveniosDTO, String> eliminar = new ColumnConfig<ObrafuenterecursosconveniosDTO, String>(properties.eliminar(), 80, "Eliminar");
        nameColumn.setColumnTextClassName(CommonStyles.get().inlineBlock());
        nameColumn.setColumnTextStyle(textStyles);
        
        TextButtonCell button = new TextButtonCell();
        button.addSelectHandler(new SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                Context c = event.getContext();
                int row = c.getIndex();
                if (!editar) {
                    obraDto.getObrafuenterecursosconvenioses().remove(store.get(row));
                    obraDto.setValor(obraDto.getValor().subtract(store.get(row).getValor()));
                    getStore().remove(store.get(row));
                } else {
                    if (!store.get(row).isEstaEnFuenteRecurso()) {
                        FuenterecursosconvenioDTO fuenteRecursos = store.get(row).getFuenterecursosconvenio();
                        fuenteRecursos.setValorDisponible(fuenteRecursos.getValorDisponible().add(store.get(row).getValor()));
                        service.setLog("en eliminar fuente pry:" + fuenteRecursos.getValorDisponible(), null);
                        obraDto.getObrafuenterecursosconvenioses().remove(store.get(row));
                        obraDto.setValor(obraDto.getValor().subtract(store.get(row).getValor()));
                        getStore().remove(store.get(row));
                    } else {
                        AlertMessageBox alerta = new AlertMessageBox("Error", "No se puede eliminar la fuente de recurso porque esta asociada a un contrato!");
                        alerta.show();
                    }                    
                }
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
        
        List<ObrafuenterecursosconveniosDTO> plants = new ArrayList<ObrafuenterecursosconveniosDTO>(agregarValorDisponibleConFormato(obraDto.getObrafuenterecursosconvenioses()));
        getStore().addAll(plants);
        
        final Grid<ObrafuenterecursosconveniosDTO> grid = new Grid<ObrafuenterecursosconveniosDTO>(getStore(), cm);
        grid.setBorders(true);
//        grid.getView().setAutoExpandColumn(nameColumn);
//        grid.getView().setAutoExpandColumn(tipoAporte);
//        grid.getView().setAutoExpandColumn(valor);
//        grid.getView().setAutoExpandColumn(vigencia);
//        grid.getView().setAutoExpandColumn(eliminar);
        grid.getView().setTrackMouseOver(false);
        grid.getView().setEmptyText("Por favor ingrese los roles y enidades");
        grid.getView().setColumnLines(true);
        grid.getView().setAdjustForHScroll(true);
        
        FramedPanel cp = new FramedPanel();
        cp.setHeadingText("*FINANCIAMIENTO DEL PROYECTO");
        cp.setWidget(grid);
        cp.setCollapsible(true);
        cp.setAnimCollapse(true);
        cp.setExpanded(true);
        cp.setPixelSize(550, 150);
        cp.addStyleName("margin-10");
        
        
        return cp;
    }
    
    public boolean estaEncontrato(ObrafuenterecursosconveniosDTO obr) {
        service.setLog("entre estaContrato", null);
        boolean estaRelacionado = false;
        List<ActividadobraDTO> lstHijas = taskStore.getChildren(actividadObraEditar);
        for (ActividadobraDTO act : lstHijas) {
            if (act.getTipoActividad() == 4) {
                ContratoDTO contrato = act.getContrato();
                for (Iterator it = contrato.getRelacionobrafuenterecursoscontratos().iterator(); it.hasNext() && !estaRelacionado;) {
                    RelacionobrafuenterecursoscontratoDTO rfrc = (RelacionobrafuenterecursoscontratoDTO) it.next();
                    if (rfrc.getObrafuenterecursosconvenios().equals(obr)) {
                        estaRelacionado = true;
                    }
                }
            }
        }
        return estaRelacionado;
    }
    
    public Set<ObrafuenterecursosconveniosDTO> agregarValorDisponibleConFormato(Set<ObrafuenterecursosconveniosDTO> lstObraFuenteRecursos) {
        for (ObrafuenterecursosconveniosDTO obraFuenteRec : lstObraFuenteRecursos) {
            obraFuenteRec.setValorFormato(GanttDatos.obtenerFormatoNumero(obraFuenteRec.getValor()));
        }
        return lstObraFuenteRecursos;
    }
}