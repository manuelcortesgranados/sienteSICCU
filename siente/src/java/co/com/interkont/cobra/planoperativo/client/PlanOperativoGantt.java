/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client;

import co.com.interkont.cobra.planoperativo.client.dto.DependenciaDTO;
import com.gantt.client.Gantt;
import com.gantt.client.config.GanttConfig;
import com.gantt.client.event.TaskContextMenuEvent;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import co.com.interkont.cobra.planoperativo.client.dto.ActividadobraDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ActividadobraDTOProps;
import co.com.interkont.cobra.planoperativo.client.dto.DependenciaDTOProps;
import co.com.interkont.cobra.planoperativo.client.dto.GanttDummyData;
import co.com.interkont.cobra.planoperativo.client.resources.images.ExampleImages;
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAble;
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAbleAsync;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.scheduler.client.core.TimeResolution;
import com.scheduler.client.core.config.SchedulerConfig;
import com.scheduler.client.core.timeaxis.TimeAxisGenerator;
import com.scheduler.client.core.timeaxis.preconfig.DayTimeAxisGenerator;
import com.scheduler.client.core.timeaxis.preconfig.WeekTimeAxisGenerator;
import com.scheduler.client.zone.WeekendZoneGenerator;
import com.scheduler.client.zone.ZoneGeneratorInt;
import com.sencha.gxt.core.client.util.DateWrapper;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.button.ToggleButton;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.ViewReadyEvent;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor;
import com.sencha.gxt.widget.core.client.form.SpinnerField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.HeaderGroupConfig;
import com.sencha.gxt.widget.core.client.grid.editing.GridInlineEditing;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;
import com.sencha.gxt.widget.core.client.treegrid.TreeGrid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author desarrollo9
 */
public class PlanOperativoGantt implements IsWidget, EntryPoint {
//     private static final int COLUMN_FORM_WIDTH = 680;

    public interface GanttExampleStyle extends CssResource {
    }

    public interface GanttExampleResources extends ClientBundle {

        @Source({"Gantt.css"})
        GanttExampleStyle css();
    }
    @SuppressWarnings("unused")
    private static final GanttExampleResources resources = GWT.create(GanttExampleResources.class);
    private final CobraGwtServiceAbleAsync service = GWT.create(CobraGwtServiceAble.class);
    private Gantt<ActividadobraDTO, DependenciaDTO> gantt;
    private static final ActividadobraDTOProps props = GWT.create(ActividadobraDTOProps.class);
    private static final DependenciaDTOProps depProps = GWT.create(DependenciaDTOProps.class);
    ActividadobraDTO root;
//    private static final TaskProps props = GWT.create(TaskProps.class);
//    private static final DependencyProps depProps = GWT.create(DependencyProps.class);
    ListStore<ActividadobraDTO> taskStore;
    /**
     * Almacena la tarea que ha sido seleccionada en el gantt
     */
    private ActividadobraDTO tareaSeleccionada;

    @SuppressWarnings("unchecked")
    @Override
    public Widget asWidget() {
        final Button enviar = new Button("Enviar");

        final DialogBox dialogBox = new DialogBox();
        dialogBox.setText("Remote Procedure Call");

//        taskStore.setAutoCommit(true);

//        for (Task base : root.getChildren()) {
//            taskStore.add(base);
//            if (base.hasChildren()) {
//                processFolder(taskStore, base);
//            }
//        }


        final TreeStore<ActividadobraDTO> taskStore = new TreeStore<ActividadobraDTO>(props.key());
        taskStore.setAutoCommit(true);
        //ActividadobraDTO root = GanttDummyData.getTasks();         



        for (ActividadobraDTO base : root.getChildren()) {
            taskStore.add(base);
            if (base.hasChildren()) {
                processFolder(taskStore, base);
            }
        }



        ListStore<DependenciaDTO> depStore = new ListStore<DependenciaDTO>(depProps.key());
        depStore.addAll(GanttDummyData.getDependencies());

        GanttConfig config = new GanttConfig();
        // ColumnModel for left static columns
        config.leftColumns = createStaticColumns();
        ArrayList<TimeAxisGenerator> headers = new ArrayList<TimeAxisGenerator>();
        headers.add(new WeekTimeAxisGenerator("MMM d"));
        headers.add(new DayTimeAxisGenerator("EEE"));
        config.timeHeaderConfig = headers;
        // Enable task resize
        config.resizeHandle = SchedulerConfig.ResizeHandle.BOTH;
        // Enable dependency DnD
        config.dependencyDnDEnabled = true;
        // Only EndToStart allowed
        //		config.dependencyDnDTypes = DependencyDnDConstants.ENDtoSTART ;

        // Enable task DnD
        config.taskDnDEnabled = true;
        // Define "snap to" resolution
        config.timeResolutionUnit = TimeResolution.Unit.WEEK;
        config.timeResolutionIncrement = 1;
        // Define the DateFormat of the tooltips
        config.tipDateFormat = DateTimeFormat.getFormat("MMM d");
        // Disable time toolTip
        config.timeTipEnabled = false;
        // Defines if the timeAxis columns should be autosized to fit.
        config.fitColumns = false;
        // Define column width
        config.columnWidth = 40;
        // Enable task contextMenu
        config.taskContextMenuEnabled = true;
        config.dependencyContextMenuEnabled = true;
        config.eventContextMenuEnabled = true;



        /**
         * Diálogo Personalizado
         */
        final Dialog crearProyectoDialog = new Dialog();
        crearProyectoDialog.setHideOnButtonClick(true);
        crearProyectoDialog.setPredefinedButtons();
        crearProyectoDialog.setModal(true);
        crearProyectoDialog.setAnimCollapse(true);
//        FormPanel gt= new FormPanel

//        /**
//         * Formulario del Proyecto
//         */
        final ProyectoForm proyectoForm = new ProyectoForm();
        crearProyectoDialog.add(proyectoForm);

        /**
         * Personalizando el menú
         */
        config.taskContextMenu = new Menu();


        Menu crearProyectoMenuItem = new Menu();


        crearProyectoMenuItem.addSelectionHandler(
                new SelectionHandler<Item>() {
            @Override
            public void onSelection(SelectionEvent<Item> event) {
                proyectoForm.getNombreProyectoTextField().setText(tareaSeleccionada.getName());
                crearProyectoDialog.show();
            }
        });

        config.taskContextMenu.add(crearProyectoMenuItem);

        // Enable dependency contextMenu
        config.dependencyContextMenuEnabled = true;
        //
        config.taskProperties = props;
        config.dependencyProperties = depProps;

        // Cascade Changes
        config.cascadeChanges = true;

        // Add zones to weekends
        ArrayList<ZoneGeneratorInt> zoneGenerators = new ArrayList<ZoneGeneratorInt>();
        zoneGenerators.add(new WeekendZoneGenerator()); // Create a zone every weekend
        config.zoneGenerators = zoneGenerators;




        // Create the Gxt Scheduler


        gantt = new Gantt<ActividadobraDTO, DependenciaDTO>(taskStore, depStore,
                config) {
            @Override
            public DependenciaDTO createDependencyModel(ActividadobraDTO fromTask, ActividadobraDTO toTask, GanttConfig.DependencyType type) {
                return new DependenciaDTO(String.valueOf(new Date().getTime()), fromTask.getId(), toTask.getId(), type);
                //return new DependenciaDTO(1, fromTask,toTask, type);
//                        (String.valueOf(new Date().getTime()), toTask.getOidactiviobra(),  type);
            }

            ;
             
            @Override
            public ActividadobraDTO createTaskModel(String id, Date startDateTime, int duration) {
                // return new ActividadobraDTO(id, "Nueva Actividad", startDateTime, duration, 0, GanttConfig.TaskType.LEAF);
                return new ActividadobraDTO(id, "New Task", startDateTime, duration, 0, GanttConfig.TaskType.LEAF);
            }
        };

        gantt.addTaskContextMenuHandler(new TaskContextMenuEvent.TaskContextMenuHandler<ActividadobraDTO>() {
            @Override
            public void onTaskContextMenu(TaskContextMenuEvent<ActividadobraDTO> event) {
                tareaSeleccionada = event.getTask();
            }
        });

        // Editing
        GridInlineEditing<ActividadobraDTO> editing = new GridInlineEditing<ActividadobraDTO>(gantt.getLeftGrid());
        editing.addEditor(config.leftColumns.getColumn(0), new TextField());
        editing.addEditor(config.leftColumns.getColumn(1), new DateField());
        editing.addEditor(config.leftColumns.getColumn(2), new SpinnerField<Integer>(new NumberPropertyEditor.IntegerPropertyEditor()));
        SpinnerField<Integer> spinner = new SpinnerField<Integer>(new NumberPropertyEditor.IntegerPropertyEditor());
        spinner.setMinValue(0);
        spinner.setMaxValue(100);
        spinner.setIncrement(10);
        editing.addEditor(config.leftColumns.getColumn(3), spinner);

        gantt.getLeftGrid().addViewReadyHandler(new ViewReadyEvent.ViewReadyHandler() {
            @Override
            public void onViewReady(ViewReadyEvent event) {
                ((TreeGrid<ActividadobraDTO>) gantt.getLeftGrid()).expandAll();
            }
        });
 
        DateWrapper dw = new DateWrapper(new Date()).clearTime();
        // Set start and end date.
        gantt.setStartEnd(dw.addDays(-7).asDate(), dw.addMonths(1).asDate());

        FlowLayoutContainer main = new FlowLayoutContainer();
        main.getElement().setMargins(new Margins(5));
        HTML text = new HTML("Plan Operativo");
        main.add(text);
        ContentPanel cp = new ContentPanel();
        cp.setHeadingText("Plan Operativo");
        cp.getHeader().setIcon(ExampleImages.INSTANCE.table());
        cp.setPixelSize(1000, 460);
        cp.getElement().setMargins(new Margins(5));

        VerticalLayoutContainer vc = new VerticalLayoutContainer();
        cp.setWidget(vc);
        vc.add(createToolBar(), new VerticalLayoutContainer.VerticalLayoutData(1, -1));
        vc.add(gantt, new VerticalLayoutContainer.VerticalLayoutData(1, 1));
        vc.add(enviar);
        main.add(cp);
        return main;
    }

    // Create ToolBar
    private ToolBar createToolBar() {
        ToolBar tbar = new ToolBar();

        // Button to endable/disable cascadeChanges
        final ToggleButton cascade = new ToggleButton("Cambiar a Cascada");
        cascade.setValue(true);
        cascade.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                gantt.getConfig().cascadeChanges = cascade.getValue();
                gantt.reconfigure(false);
            }
        });
        tbar.add(cascade);

        // Button to endable/disable show CriticalPath
        final ToggleButton critical = new ToggleButton("Ruta Critica");
        critical.setValue(false);
        critical.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                gantt.getConfig().showCriticalPath = critical.getValue();
                gantt.reconfigure(true);
            }
        });
        tbar.add(critical);



        return tbar;
    }

    // Creates the static columns
    @SuppressWarnings({"rawtypes", "unchecked"})
    private ColumnModel<ActividadobraDTO> createStaticColumns() {
        List<ColumnConfig<ActividadobraDTO, ?>> configs = new ArrayList<ColumnConfig<ActividadobraDTO, ?>>();

        ColumnConfig<ActividadobraDTO, ?> column = new ColumnConfig<ActividadobraDTO, String>(props.name());
        column.setHeader("Actividades");
        column.setWidth(160);
        column.setSortable(true);
        column.setResizable(true);
        configs.add(column);

        ColumnConfig<ActividadobraDTO, Date> column2 = new ColumnConfig<ActividadobraDTO, Date>(props.startDateTime());
        column2.setHeader("Inicio");
        column2.setWidth(90);
        column2.setSortable(true);
        column2.setResizable(true);
        column2.setCell(new DateCell(DateTimeFormat.getFormat("yyyy-MM-dd")));
        configs.add(column2);

        ColumnConfig<ActividadobraDTO, Integer> column3 = new ColumnConfig<ActividadobraDTO, Integer>(props.duration());
        column3.setHeader("Duraccion");
        column3.setWidth(60);
        column3.setSortable(true);
        column3.setResizable(true);
        configs.add(column3);

        ColumnConfig<ActividadobraDTO, Integer> column4 = new ColumnConfig<ActividadobraDTO, Integer>(props.percentDone());
        column4.setHeader("Peso");
        column4.setWidth(60);
        column4.setSortable(true);
        column4.setResizable(true);
        configs.add(column4);



        ColumnModel cm = new ColumnModel(configs);
        cm.addHeaderGroup(0, 0, new HeaderGroupConfig("Plan Opertivo", 1,
                4));

        return cm;
    }

    @Override
    public void onModuleLoad() {
        cargar();
        RootPanel.get("planoperativoclient").add(asWidget());
    }

    private void processFolder(TreeStore<ActividadobraDTO> store, ActividadobraDTO folder) {
        for (ActividadobraDTO child : folder.getChildren()) {
            store.add(folder, child);
            if (child.hasChildren()) {
                processFolder(store, child);
            }
        }

    }
    
    public void cargar(){
        service.getActividadObraDTO(new AsyncCallback<ActividadobraDTO>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        
                    }

                    @Override
                    public void onSuccess(ActividadobraDTO result) {
                        root = result;
                        

                    }
                });
    }
}
