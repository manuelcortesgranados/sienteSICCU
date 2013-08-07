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
import co.com.interkont.cobra.planoperativo.client.dto.ContratoDTO;
import co.com.interkont.cobra.planoperativo.client.dto.DependenciaDTOProps;
import co.com.interkont.cobra.planoperativo.client.dto.GanttDummyData;
import co.com.interkont.cobra.planoperativo.client.resources.images.ExampleImages;
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAble;
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAbleAsync;
import com.gantt.client.event.DependencyContextMenuEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.scheduler.client.core.TimeResolution.Unit;
import com.scheduler.client.core.config.SchedulerConfig.ResizeHandle;
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
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.button.ToggleButton;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.HideEvent;
import com.sencha.gxt.widget.core.client.event.HideEvent.HideHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.event.ViewReadyEvent;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor;
import com.sencha.gxt.widget.core.client.form.SpinnerField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.HeaderGroupConfig;
import com.sencha.gxt.widget.core.client.grid.editing.GridInlineEditing;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;
import com.sencha.gxt.widget.core.client.treegrid.TreeGrid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;

/**
 *
 * @author desarrollo9
 */
public class PlanOperativoGantt implements IsWidget, EntryPoint {

    /**
     * Declaración de Objetos propios para manejo del plan operativo
     */
    /**
     * Objeto contrato simple para manejo del convenio con todas sus relaciones
     * a nivel cliente
     */
    private ContratoDTO convenioDTO;

    public ContratoDTO getConvenioDTO() {
        return convenioDTO;
    }

    public void setConvenioDTO(ContratoDTO convenioDTO) {
        this.convenioDTO = convenioDTO;
    }
    /**
     * Objeto actividad obra simple, replica de convenio, es la raiz del plan
     * operativo
     */
    private ActividadobraDTO root;

    public ActividadobraDTO getRoot() {
        return root;
    }

    public void setRoot(ActividadobraDTO root) {
        this.root = root;
    }
    /**
     * Servicio que permite la IOC de spring en gwt
     */
    private CobraGwtServiceAbleAsync service = GWT.create(CobraGwtServiceAble.class);

    public CobraGwtServiceAbleAsync getService() {
        return service;
    }

    public void setService(CobraGwtServiceAbleAsync service) {
        this.service = service;
    }

    /**
     * Variable para mensaje de validaciones
     */
    private String msg = "";

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
//     private static final int COLUMN_FORM_WIDTH = 680;
    public interface GanttExampleStyle extends CssResource {
    }

    public interface GanttExampleResources extends ClientBundle {

        @Source({"Gantt.css"})
        GanttExampleStyle css();
    }
    private Gantt<ActividadobraDTO, DependenciaDTO> gantt;
    private static final ActividadobraDTOProps props = GWT.create(ActividadobraDTOProps.class);
    private static final DependenciaDTOProps depProps = GWT.create(DependenciaDTOProps.class);
//    private static final TaskProps props = GWT.create(TaskProps.class);
//    private static final DependencyProps depProps = GWT.create(DependencyProps.class);
    //ListStore<ActividadobraDTO> taskStore;
    /**
     * Almacena la tarea que ha sido seleccionada en el gantt
     */
    private ActividadobraDTO tareaSeleccionada;
    /**
     * Almacena la dependencia que ha sido seleccionada en el gantt
     */
    private DependenciaDTO dependenciaSeleccionada;

    @SuppressWarnings("unchecked")
    @Override
    public Widget asWidget() {
        service.setLog("As widget", null);
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
        final ActividadobraDTO project=  new ActividadobraDTO(convenioDTO.getStrnumcontrato(), convenioDTO.getDatefechaini(), 100,
                100, GanttConfig.TaskType.PARENT);
        
        service.obtenerActividadesObligatorias(convenioDTO.getDatefechaini(), 100,new AsyncCallback<List<ActividadobraDTO>>() {

            @Override
            public void onFailure(Throwable caught) {
                service.setLog("Error al leer las actividades obligatorias", null);
            }

            @Override
            public void onSuccess(List<ActividadobraDTO> result) {                
                project.setChildren(result);
            }
        });
        ArrayList<ActividadobraDTO> list = new ArrayList<ActividadobraDTO>();
        list.add(project);
        //root = new ActividadobraDTO(list);
        root = GanttDummyData.getTasks();



        for (ActividadobraDTO base : root.getChildren()) {
            taskStore.add(base);
            service.setLog("Base: "+base.getName()+"Tiene hijos" +base.hasChildren(), null);
            if (base.hasChildren()) {
                processFolder(taskStore, base);
            }
        }



        final ListStore<DependenciaDTO> depStore = new ListStore<DependenciaDTO>(depProps.key());
        depStore.addAll(GanttDummyData.getDependencies());

        GanttConfig config = new GanttConfig();
        // ColumnModel for left static columns
        config.leftColumns = createStaticColumns();
        ArrayList<TimeAxisGenerator> headers = new ArrayList<TimeAxisGenerator>();
        headers.add(new WeekTimeAxisGenerator("MMM d"));
        headers.add(new DayTimeAxisGenerator("EEE"));
        config.timeHeaderConfig = headers;
        // Enable task resize
        config.resizeHandle = ResizeHandle.BOTH;
        // Enable dependency DnD
        config.dependencyDnDEnabled = true;
        // Only EndToStart allowed
        //		config.dependencyDnDTypes = DependencyDnDConstants.ENDtoSTART ;

        // Enable task DnD
        config.taskDnDEnabled = true;
        // Define "snap to" resolution
        config.timeResolutionUnit = Unit.DAY;
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
        // Enable dependency contextMenu
        config.dependencyContextMenuEnabled = true;
        config.eventContextMenuEnabled = true;
        config.showTaskLabel=false;
        
      
        


        /**
         * Ventana Modal Confirmar Eliminar Actividad
         */
        final ConfirmMessageBox boxConfim = new ConfirmMessageBox("Confirmar", "Esta seguro que desea eliminar esta actividad?");
        boxConfim.setHeight(150);
        boxConfim.addHideHandler(new HideHandler() {
            @Override
            public void onHide(HideEvent event) {
                if (boxConfim.getHideButton() == boxConfim.getButtonById(PredefinedButton.YES.name())) {
                    taskStore.remove(tareaSeleccionada);
                }
            }
        });


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


        /**
         * Opciones de la actividad Convenio
         */
        final MenuItem menuItemProyecto = new MenuItem("Crear proyecto");
        menuItemProyecto.addSelectionHandler(new SelectionHandler<Item>() {
            @Override
            public void onSelection(SelectionEvent<Item> event) {
                //proyectoForm.getNombreProyectoTextField().setText(tareaSeleccionada.getName());
                crearProyectoDialog.show();
            }
        });
        config.taskContextMenu.add(menuItemProyecto);

        /**
         * Opciones de la actividad proyecto
         */
        final MenuItem menuItemContrato = new MenuItem("Añadir Contrato");
        menuItemContrato.addSelectionHandler(new SelectionHandler<Item>() {
            @Override
            public void onSelection(SelectionEvent<Item> event) {
                proyectoForm.getNombreProyectoTextField().setText(tareaSeleccionada.getName());
                crearProyectoDialog.show();
            }
        });
        config.taskContextMenu.add(menuItemContrato);

        final MenuItem menuItemEditarPry = new MenuItem("Editar proyecto");
        menuItemEditarPry.addSelectionHandler(new SelectionHandler<Item>() {
            @Override
            public void onSelection(SelectionEvent<Item> event) {
                proyectoForm.getNombreProyectoTextField().setText(tareaSeleccionada.getName());
                crearProyectoDialog.show();
            }
        });
        config.taskContextMenu.add(menuItemEditarPry);

        final MenuItem menuItemEliminarPry = new MenuItem("Eliminar proyecto");
        menuItemEliminarPry.addSelectionHandler(new SelectionHandler<Item>() {
            @Override
            public void onSelection(SelectionEvent<Item> event) {
                boxConfim.show();
            }
        });
        config.taskContextMenu.add(menuItemEliminarPry);

        /**
         * Opciones de la actividad Contrato
         */
        final MenuItem menuItemEditarContrato = new MenuItem("Editar Contrato");
        menuItemEditarContrato.addSelectionHandler(new SelectionHandler<Item>() {
            @Override
            public void onSelection(SelectionEvent<Item> event) {
                proyectoForm.getNombreProyectoTextField().setText(tareaSeleccionada.getName());
                crearProyectoDialog.show();
            }
        });
        config.taskContextMenu.add(menuItemEditarContrato);

        final MenuItem menuItemEliminarContrato = new MenuItem("Eliminar Contrato");
        menuItemEliminarContrato.addSelectionHandler(new SelectionHandler<Item>() {
            @Override
            public void onSelection(SelectionEvent<Item> event) {
                boxConfim.show();
            }
        });
        config.taskContextMenu.add(menuItemEliminarContrato);

        /**
         * Opciones generales para todas las actividades
         */
        final MenuItem menuItemAñadirTarea = new MenuItem("Añadir Actividad");
        menuItemAñadirTarea.addSelectionHandler(new SelectionHandler<Item>() {
            @Override
            public void onSelection(SelectionEvent<Item> event) {
                proyectoForm.getNombreProyectoTextField().setText(tareaSeleccionada.getName());
                crearProyectoDialog.show();
            }
        });
        config.taskContextMenu.add(menuItemAñadirTarea);

        final MenuItem menuItemEliminarTarea = new MenuItem("Eliminar Actividad");
        menuItemEliminarTarea.addSelectionHandler(new SelectionHandler<Item>() {
            @Override
            public void onSelection(SelectionEvent<Item> event) {
                boxConfim.show();

            }
        });
        config.taskContextMenu.add(menuItemEliminarTarea);

        /*Se crea el menu asociado a las dependencias**/
        config.dependencyContextMenu = new Menu();

        MenuItem menuItemEliminarDependencia = new MenuItem("Eliminar dependencia");
        menuItemEliminarDependencia.addSelectionHandler(new SelectionHandler<Item>() {
            @Override
            public void onSelection(SelectionEvent<Item> event) {
                depStore.remove(dependenciaSeleccionada);
            }
        });
        config.dependencyContextMenu.add(menuItemEliminarDependencia);




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

//                variable para definir si se muestran o no no las opciones de eliminar
//                boolean mostrarEliminar=false;
//                
//                /*se verifica si el estado del convenio es en estructuracion en tal caso si 
//                * es posible eliminar las diferentes actividades
//                */
//              if(convenioDTO.getEstadoConvenio()==1){
//                mostrarEliminar=true;
//                }

                if (tareaSeleccionada.getTipoActividad() == 1) {
                    menuItemProyecto.setVisible(true);
                    menuItemContrato.setVisible(false);
                    menuItemEliminarTarea.setVisible(false);
                    menuItemEditarPry.setVisible(false);
                    menuItemEliminarPry.setVisible(false);
                    menuItemEditarContrato.setVisible(false);
                    menuItemEliminarContrato.setVisible(false);

                } else if (tareaSeleccionada.getTipoActividad() == 2) {
                    menuItemEditarPry.setVisible(true);
                    menuItemContrato.setVisible(true);
                    menuItemProyecto.setVisible(false);
                    menuItemEliminarTarea.setVisible(false);
                    menuItemEliminarPry.setVisible(true);
                    // menuItemEliminarPry.setVisible(mostrarEliminar);
                    menuItemEditarContrato.setVisible(false);
                    menuItemEliminarContrato.setVisible(false);
                } else if (tareaSeleccionada.getTipoActividad() == 3) {
                    menuItemEditarPry.setVisible(false);
                    menuItemContrato.setVisible(false);
                    menuItemProyecto.setVisible(false);
                    menuItemEliminarTarea.setVisible(false);
                    menuItemEliminarPry.setVisible(false);
                    menuItemEditarContrato.setVisible(true);
                    menuItemEliminarContrato.setVisible(true);
                    //  menuItemEliminarContrato.setVisible(mostrarEliminar);
                } else {
                    menuItemEliminarTarea.setVisible(true);
                    // menuItemEliminarTarea.setVisible(mostrarEliminar);
                    menuItemContrato.setVisible(false);
                    menuItemProyecto.setVisible(false);
                    menuItemEditarPry.setVisible(false);
                    menuItemEliminarPry.setVisible(false);
                    menuItemEditarContrato.setVisible(false);
                    menuItemEliminarContrato.setVisible(false);
                    menuItemAñadirTarea.setVisible(false);
                }
            }
        });
        
       
        
        /**metodo que se encarga de obtener la dependencia seleccionada cuando se activa el menu contextual*/
            gantt.addDependencyContextMenuHandler(new DependencyContextMenuEvent.DependencyContextMenuHandler<DependenciaDTO>() {
            @Override
            public void onDependencyContextMenu(DependencyContextMenuEvent<DependenciaDTO> event) {
              dependenciaSeleccionada = event.getDependency();
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
        critical.addSelectHandler(new SelectHandler() {
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
        List<ColumnConfig<ActividadobraDTO,?>> configs = new ArrayList<ColumnConfig<ActividadobraDTO, ?>>();

        ColumnConfig<ActividadobraDTO,?> column = new ColumnConfig<ActividadobraDTO, String>(props.name());
        column.setHeader("Actividades");
        column.setWidth(160);
        column.setSortable(true);
        column.setResizable(true);
        configs.add(column);

        ColumnConfig<ActividadobraDTO,Date> column2 = new ColumnConfig<ActividadobraDTO, Date>(props.startDateTime());
        column2.setHeader("Inicio");
        column2.setWidth(90);
        column2.setSortable(true);
        column2.setResizable(true);
        column2.setCell(new DateCell(DateTimeFormat.getFormat("yyyy-MM-dd")));
        configs.add(column2);

        ColumnConfig<ActividadobraDTO,Integer> column3 = new ColumnConfig<ActividadobraDTO, Integer>(props.duration());
        column3.setHeader("Duración");
        column3.setWidth(60);
        column3.setSortable(true);
        column3.setResizable(true);
        configs.add(column3);

        ColumnConfig<ActividadobraDTO,Integer> column4 = new ColumnConfig<ActividadobraDTO, Integer>(props.percentDone());
        column4.setHeader("Peso");
        column4.setWidth(60);
        column4.setSortable(true);
        column4.setResizable(true);
        configs.add(column4);



        ColumnModel cm = new ColumnModel(configs);
        cm.addHeaderGroup(0, 0, new HeaderGroupConfig("Plan Operativo", 1,
                4));

        return cm;
    }
    
   

    @Override
    public void onModuleLoad() {
        service.setLog("Load module", null);
        cargar();
        //RootPanel.get("planoperativoclient").add(asWidget());
    }

    private void processFolder(TreeStore<ActividadobraDTO> store, ActividadobraDTO folder) {
        for (ActividadobraDTO child : folder.getChildren()) {
            store.add(folder, child);
            if (child.hasChildren()) {
                processFolder(store, child);
            }
        }
    }

    public void cargar() {
        //Cargando el convenio
        service.getContratoDTO(new AsyncCallback<ContratoDTO>() {

            @Override
            public void onFailure(Throwable caught) {
                service.setLog("Error cargando convenio", null);
            }

            @Override
            public void onSuccess(ContratoDTO result) {
                convenioDTO = result;
                if(validandoDatosBásicosConvenio())
                {
                    RootPanel.get().add(asWidget());
                }
                else
                {
                    service.setLog(msg, null);
                    AlertMessageBox d = new AlertMessageBox("Alerta",msg);
                   // d.addHideHandler(hideHandler);
                    d.show();
                }    
            }
        });
        
    }

    public boolean validandoDatosBásicosConvenio() {
        //Validación valor contrato
        
        //Validación fechas obligatorias
        if(convenioDTO.getDatefechaini()==null || convenioDTO.getDatefechafin()==null)
        {
            msg="Debe diligenciar las fechas del convenio";
            return false;
        }    
//        if (convenioDTO.getNumvlrcontrato() != null) {
//        }

        return true;
    }
}
