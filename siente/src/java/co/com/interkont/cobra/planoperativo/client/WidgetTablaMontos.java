package co.com.interkont.cobra.planoperativo.client;

import co.com.interkont.cobra.planoperativo.client.dto.ContratoDTO;
import co.com.interkont.cobra.planoperativo.client.dto.GanttDatos;
import co.com.interkont.cobra.planoperativo.client.dto.ObrafuenterecursosconveniosDTO;
import co.com.interkont.cobra.planoperativo.client.dto.RelacionobrafuenterecursoscontratoDTO;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.core.client.GWT;
import java.util.ArrayList;
import java.util.List;

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
import com.sencha.gxt.widget.core.client.form.NumberField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import java.math.BigDecimal;
import java.util.Set;

public class WidgetTablaMontos implements IsWidget {

    protected ContratoDTO contrato;
    protected boolean editar;
    protected NumberField<BigDecimal> valorContrato;
    protected boolean sololectura=false;
    

    /**
     * Constructor solo lectura
     * @param contrato
     * @param editar
     * @param valorContrato
     * @param readonly
     */
    public WidgetTablaMontos(ContratoDTO contrato, boolean editar, NumberField<BigDecimal> valorContrato, boolean readonly) {
        this.contrato = contrato;
        this.editar = editar;
        this.valorContrato = valorContrato;
        this.sololectura=readonly;
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

        ValueProvider<RelacionobrafuenterecursoscontratoDTO, String> valorFormato();

        ValueProvider<RelacionobrafuenterecursoscontratoDTO, String> descripcionRubro();

        ValueProvider<RelacionobrafuenterecursoscontratoDTO, String> nombreEntidad();

        ValueProvider<RelacionobrafuenterecursoscontratoDTO, Integer> vigenciafonade();

        ValueProvider<RelacionobrafuenterecursoscontratoDTO, Integer> vigenciafuente();

        ValueProvider<RelacionobrafuenterecursoscontratoDTO, String> eliminar();
    }
    private static final PlaceProperties properties = GWT.create(PlaceProperties.class);
    private ListStore<RelacionobrafuenterecursoscontratoDTO> store;

    @Override
    public Widget asWidget() {

        SafeStyles textStyles = SafeStylesUtils.fromTrustedString("padding: 1px 3px;");


        ColumnConfig<RelacionobrafuenterecursoscontratoDTO, String> nameColumn = new ColumnConfig<RelacionobrafuenterecursoscontratoDTO, String>(properties.descripcionRubro(), 350, "Rubro");
        nameColumn.setColumnTextStyle(textStyles);



        ColumnConfig<RelacionobrafuenterecursoscontratoDTO, String> valor = new ColumnConfig<RelacionobrafuenterecursoscontratoDTO, String>(properties.valorFormato(), 100, "Valor");
        valor.setColumnTextStyle(textStyles);

        ColumnConfig<RelacionobrafuenterecursoscontratoDTO, Integer> vigencia = new ColumnConfig<RelacionobrafuenterecursoscontratoDTO, Integer>(properties.vigenciafonade(), 100, "Vigencia");
        vigencia.setColumnTextStyle(textStyles);

        ColumnConfig<RelacionobrafuenterecursoscontratoDTO, String> entidad = new ColumnConfig<RelacionobrafuenterecursoscontratoDTO, String>(properties.nombreEntidad(), 200, "Fuente");
        entidad.setColumnTextStyle(textStyles);


        ColumnConfig<RelacionobrafuenterecursoscontratoDTO, Integer> vigenciaFuente = new ColumnConfig<RelacionobrafuenterecursoscontratoDTO, Integer>(properties.vigenciafuente(), 100, "Vigencia fuente");
        vigencia.setColumnTextStyle(textStyles);

        ColumnConfig<RelacionobrafuenterecursoscontratoDTO, String> eliminar = new ColumnConfig<RelacionobrafuenterecursoscontratoDTO, String>(properties.eliminar(), 100, "Eliminar");
        nameColumn.setColumnTextClassName(CommonStyles.get().inlineBlock());
        nameColumn.setColumnTextStyle(textStyles);

        TextButtonCell button = new TextButtonCell();
       
        button.addSelectHandler(new SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                Context c = event.getContext();
                int row = c.getIndex();
                ObrafuenterecursosconveniosDTO obraFuenteRecursos = store.get(row).getObrafuenterecursosconvenios();
                if (editar) {
                    obraFuenteRecursos.setEstaEnFuenteRecurso(false);
                }
                BigDecimal valor = valorContrato.getValue();
                obraFuenteRecursos.setValorDisponible(obraFuenteRecursos.getValorDisponible().add(store.get(row).getValor()));
                valor = valor.subtract(store.get(row).getValor());
                valorContrato.setValue(valor);
                //GanttDatos.adicionarRelacionObraFuenteRecursoContratoEliminar(store.get(row));
                contrato.getRelacionobrafuenterecursoscontratos().remove(store.get(row));
                getStore().remove(store.get(row));

            }
        });
        if(!sololectura)
        {    
        eliminar.setCell(button);
        }
        List<ColumnConfig<RelacionobrafuenterecursoscontratoDTO, ?>> l = new ArrayList<ColumnConfig<RelacionobrafuenterecursoscontratoDTO, ?>>();
        l.add(nameColumn);
        l.add(valor);
        l.add(vigencia);
        l.add(entidad);
        l.add(vigenciaFuente);
        if(!sololectura)
        { 
        l.add(eliminar);
        }
        ColumnModel<RelacionobrafuenterecursoscontratoDTO> cm = new ColumnModel<RelacionobrafuenterecursoscontratoDTO>(l);

        setStore(new ListStore<RelacionobrafuenterecursoscontratoDTO>(properties.key()));

        List<RelacionobrafuenterecursoscontratoDTO> plants = new ArrayList<RelacionobrafuenterecursoscontratoDTO>(obtenerRelacionFuenteContratoFormato(contrato.getRelacionobrafuenterecursoscontratos()));
        getStore().addAll(plants);

        final Grid<RelacionobrafuenterecursoscontratoDTO> grid = new Grid<RelacionobrafuenterecursoscontratoDTO>(getStore(), cm);
        grid.setBorders(true);
        grid.getView().setTrackMouseOver(false);
        grid.getView().setEmptyText("Por favor ingrese los montos del contrato");
        grid.getView().setColumnLines(true);
        grid.getView().setAdjustForHScroll(true);

        FramedPanel cp = new FramedPanel();
        cp.setHeadingText("*Rubros");
        cp.setWidget(grid);
        cp.setCollapsible(true);
        cp.setAnimCollapse(true);
        cp.setExpanded(true);
        cp.setPixelSize(600, 150);
        cp.addStyleName("margin-10");

        return cp;
    }

    public Set<RelacionobrafuenterecursoscontratoDTO> obtenerRelacionFuenteContratoFormato(Set<RelacionobrafuenterecursoscontratoDTO> lstFuenteRecursosContrato) {
        for (RelacionobrafuenterecursoscontratoDTO relacionFuente : lstFuenteRecursosContrato) {
            relacionFuente.setValorFormato(GanttDatos.obtenerFormatoNumero(relacionFuente.getValor()));
        }
        return lstFuenteRecursosContrato;
    }
}