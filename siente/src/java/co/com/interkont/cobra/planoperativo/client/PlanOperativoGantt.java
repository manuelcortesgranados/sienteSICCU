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
import co.com.interkont.cobra.planoperativo.client.dto.FuenterecursosconvenioDTO;
import co.com.interkont.cobra.planoperativo.client.dto.GanttDatos;
import co.com.interkont.cobra.planoperativo.client.dto.GanttDummyData;
import co.com.interkont.cobra.planoperativo.client.dto.ObrafuenterecursosconveniosDTO;
import co.com.interkont.cobra.planoperativo.client.resources.images.ExampleImages;
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAble;
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAbleAsync;
import com.gantt.client.event.DependencyContextMenuEvent;
import com.gantt.client.event.TaskResizeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.datepicker.client.CalendarUtil;
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
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.button.ToggleButton;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.BeforeStartEditEvent;
import com.sencha.gxt.widget.core.client.event.CompleteEditEvent;
import com.sencha.gxt.widget.core.client.event.HideEvent;
import com.sencha.gxt.widget.core.client.event.HideEvent.HideHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.event.ViewReadyEvent;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor;
import com.sencha.gxt.widget.core.client.form.SpinnerField;
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
import java.util.Iterator;

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
    ActividadobraDTO actividadAnterior;

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

        @ClassName("gwt-label")
        String estiloLabel();
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
    GwtMensajes msj = GWT.create(GwtMensajes.class);

    @SuppressWarnings("unchecked")
    @Override
    public Widget asWidget() {
        //service.setLog("As widget", null);

        final TreeStore<ActividadobraDTO> taskStore = new TreeStore<ActividadobraDTO>(props.key());
        taskStore.setAutoCommit(true);
        root = GanttDatos.getTareas(convenioDTO);


        for (ActividadobraDTO base : root.getChildren()) {
            taskStore.add(base);
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
        config.columnWidth = 60;
        // Enable task contextMenu
        config.taskContextMenuEnabled = true;
        // Enable dependency contextMenu
        config.dependencyContextMenuEnabled = true;
        config.eventContextMenuEnabled = false;
        config.showTaskLabel = true;
        config.mouseWheelZoomEnabled = false;

        config.useEndDate = false;





        /**
         * Ventana Modal Confirmar Eliminar Actividad
         */
        final ConfirmMessageBox boxConfim = new ConfirmMessageBox("Confirmar", "Esta seguro que desea eliminar esta actividad?");
        boxConfim.setHeight(150);
        boxConfim.addHideHandler(new HideHandler() {
            @Override
            public void onHide(HideEvent event) {
                if (boxConfim.getHideButton() == boxConfim.getButtonById(PredefinedButton.YES.name())) {
                    if (!tareaSeleccionada.isBoolobligatoria()) {
                        if (tareaSeleccionada.getTipoActividad() == 3) {
                            ActividadobraDTO actividadPadre = taskStore.getParent(tareaSeleccionada);
                            actividadPadre.getObra().setValorDisponible(actividadPadre.getObra().getValorDisponible().add(tareaSeleccionada.getContrato().getNumvlrcontrato()));
                        }
                        if (tareaSeleccionada.getTipoActividad() == 2) {
                            ActividadobraDTO actividadPadreConvenio = taskStore.getParent((taskStore.getParent(tareaSeleccionada)));
                            reembolsarFuenteRecursos(actividadPadreConvenio);
                        }
                        taskStore.remove(tareaSeleccionada);
                    } else {
                        AlertMessageBox d = new AlertMessageBox("Alerta", "La actividad seleccionada no puede ser eliminada, es de caracter obligatoria.");
                        d.show();
                    }
                }
            }
        });


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
                final Window crearProyectoDialog = new Window();
                crearProyectoDialog.setBlinkModal(true);
                crearProyectoDialog.setModal(true);
                final ProyectoForm1 proyectoForm1 = new ProyectoForm1(tareaSeleccionada, gantt, crearProyectoDialog, convenioDTO, props, taskStore);
                crearProyectoDialog.add(proyectoForm1);
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
                final Window crearContratoDialog = new Window();
                crearContratoDialog.setBlinkModal(true);
                crearContratoDialog.setModal(true);
                final ContratoForm contratoForm = new ContratoForm(tareaSeleccionada, gantt, crearContratoDialog, props, taskStore);
                crearContratoDialog.add(contratoForm);
                crearContratoDialog.show();

            }
        });
        config.taskContextMenu.add(menuItemContrato);

        final MenuItem menuItemEditarPry = new MenuItem("Editar proyecto");
        menuItemEditarPry.addSelectionHandler(new SelectionHandler<Item>() {
            @Override
            public void onSelection(SelectionEvent<Item> event) {
                final Window editarProyDialog = new Window();
                editarProyDialog.setBlinkModal(true);
                editarProyDialog.setModal(true);
                ProyectoForm1 proyectoForm = new ProyectoForm1(tareaSeleccionada, gantt, editarProyDialog, taskStore.getParent(tareaSeleccionada), props, taskStore);
                editarProyDialog.add(proyectoForm);
                editarProyDialog.show();

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
                final Window crearContratoDialog = new Window();
                crearContratoDialog.setBlinkModal(true);
                crearContratoDialog.setModal(true);
                ContratoForm contratoFormEditar = new ContratoForm(tareaSeleccionada, gantt, crearContratoDialog, taskStore.getParent(tareaSeleccionada), props, taskStore);
                crearContratoDialog.add(contratoFormEditar);
                crearContratoDialog.show();
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

        final MenuItem menuItemEliminarHito = new MenuItem("Eliminar hito");
        menuItemEliminarHito.addSelectionHandler(new SelectionHandler<Item>() {
            @Override
            public void onSelection(SelectionEvent<Item> event) {
                boxConfim.show();
            }
        });
        config.taskContextMenu.add(menuItemEliminarHito);

        final MenuItem menuItemAnadirHito = new MenuItem("Crear hito");
        menuItemAnadirHito.addSelectionHandler(new SelectionHandler<Item>() {
            @Override
            public void onSelection(SelectionEvent<Item> event) {
                final Window crearActDialog = new Window();
                crearActDialog.setBlinkModal(true);
                crearActDialog.setModal(true);
                final HitoForm actividadForm = new HitoForm(tareaSeleccionada, gantt, crearActDialog, convenioDTO, GanttConfig.TaskType.MILESTONE, 6, taskStore);
                crearActDialog.add(actividadForm);
                crearActDialog.show();
            }
        });
        config.taskContextMenu.add(menuItemAnadirHito);

        /**
         * Opciones generales para todas las actividades
         */
        final MenuItem menuItemAñadirTarea = new MenuItem("Añadir Actividad");
        menuItemAñadirTarea.addSelectionHandler(new SelectionHandler<Item>() {
            @Override
            public void onSelection(SelectionEvent<Item> event) {
                final Window crearActDialog = new Window();
                crearActDialog.setBlinkModal(true);
                crearActDialog.setModal(true);
                final ActividadForm actividadForm = new ActividadForm(tareaSeleccionada, gantt, crearActDialog, convenioDTO, GanttConfig.TaskType.LEAF, 4, taskStore);
                crearActDialog.add(actividadForm);
                crearActDialog.show();

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
            }
        ;

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
                /**
                 * Tipo Actividad : 1. Actividad Convenio - 2. Proyecto - 3.
                 * Contrato - 4 Actividad - 5 Etapa - 6 Hito - 7 actividades 8
                 * Ejecucion macro
                 *
                 */
                switch (tareaSeleccionada.getTipoActividad()) {
                    case 1:
                        menuItemProyecto.setVisible(true);
                        menuItemContrato.setVisible(false);
                        menuItemEliminarTarea.setVisible(false);
                        menuItemEditarPry.setVisible(false);
                        menuItemEliminarPry.setVisible(false);
                        menuItemEditarContrato.setVisible(false);
                        menuItemEliminarContrato.setVisible(false);
                        menuItemEliminarHito.setVisible(false);
                        break;
                    case 2:
                        menuItemEditarPry.setVisible(true);
                        menuItemContrato.setVisible(true);
                        menuItemProyecto.setVisible(false);
                        menuItemEliminarTarea.setVisible(false);
                        menuItemEliminarPry.setVisible(true);
                        // menuItemEliminarPry.setVisible(mostrarEliminar);
                        menuItemEditarContrato.setVisible(false);
                        menuItemEliminarContrato.setVisible(false);
                        menuItemEliminarHito.setVisible(false);
                        break;
                    case 3:
                        menuItemEditarPry.setVisible(false);
                        menuItemContrato.setVisible(false);
                        menuItemProyecto.setVisible(false);
                        menuItemEliminarTarea.setVisible(false);
                        menuItemEliminarPry.setVisible(false);
                        menuItemEditarContrato.setVisible(true);
                        menuItemEliminarContrato.setVisible(true);
                        menuItemEliminarHito.setVisible(false);
                        //  menuItemEliminarContrato.setVisible(mostrarEliminar);
                        break;
                    case 4:
                        menuItemEliminarTarea.setVisible(true);
                        // menuItemEliminarTarea.setVisible(mostrarEliminar);
                        menuItemContrato.setVisible(false);
                        menuItemProyecto.setVisible(false);
                        menuItemEditarPry.setVisible(false);
                        menuItemEliminarPry.setVisible(false);
                        menuItemEditarContrato.setVisible(false);
                        menuItemEliminarContrato.setVisible(false);
                        menuItemAñadirTarea.setVisible(false);
                        menuItemEliminarHito.setVisible(false);
                        break;
                    case 5:
                        menuItemProyecto.setVisible(false);
                        menuItemAñadirTarea.setVisible(true);
                        menuItemContrato.setVisible(false);
                        menuItemEliminarTarea.setVisible(false);
                        menuItemEditarPry.setVisible(false);
                        menuItemEliminarPry.setVisible(false);
                        menuItemEditarContrato.setVisible(false);
                        menuItemEliminarContrato.setVisible(false);
                        menuItemEliminarHito.setVisible(false);
                        break;
                    case 6:
                        menuItemProyecto.setVisible(false);
                        menuItemAñadirTarea.setVisible(false);
                        menuItemContrato.setVisible(false);
                        menuItemEliminarTarea.setVisible(false);
                        menuItemEditarPry.setVisible(false);
                        menuItemEliminarPry.setVisible(false);
                        menuItemEditarContrato.setVisible(false);
                        menuItemEliminarContrato.setVisible(false);
                        menuItemEliminarHito.setVisible(true);
                        break;
                    case 8:
                        menuItemProyecto.setVisible(true);
                        menuItemAñadirTarea.setVisible(true);
                        menuItemContrato.setVisible(false);
                        menuItemEliminarTarea.setVisible(false);
                        menuItemEditarPry.setVisible(false);
                        menuItemEliminarPry.setVisible(false);
                        menuItemEditarContrato.setVisible(false);
                        menuItemEliminarContrato.setVisible(false);
                        menuItemEliminarHito.setVisible(false);
                        menuItemAnadirHito.setVisible(false);
                        break;

                }
            }
        });

        /**
         * metodo que se encarga de obtener la dependencia seleccionada cuando
         * se activa el menu contextual
         */
        gantt.addDependencyContextMenuHandler(new DependencyContextMenuEvent.DependencyContextMenuHandler<DependenciaDTO>() {
            @Override
            public void onDependencyContextMenu(DependencyContextMenuEvent<DependenciaDTO> event) {
                dependenciaSeleccionada = event.getDependency();
            }
        });


        gantt.addTaskResizeHandler(new TaskResizeEvent.TaskResizeHandler<ActividadobraDTO>() {
            @Override
            public void onTaskResize(TaskResizeEvent<ActividadobraDTO> event) {
                ActividadobraDTO actiresi = event.getEventModel();
                Date copiaFecha = CalendarUtil.copyDate(actiresi.getStartDateTime());
                CalendarUtil.addDaysToDate(copiaFecha, actiresi.getDuration());
                props.endDateTime().setValue(actiresi, copiaFecha);
                GanttDatos.modificarFechaFin(taskStore.getParent(actiresi), taskStore, props);
            }
        });



        // Editing
        final GridInlineEditing<ActividadobraDTO> editing = new GridInlineEditing<ActividadobraDTO>(gantt.getLeftGrid());
        //editing.addEditor(config.leftColumns.getColumn(0), new TextField());
        editing.addEditor(config.leftColumns.getColumn(1), new DateField());
        editing.addEditor(config.leftColumns.getColumn(2), new DateField());
        editing.addEditor(config.leftColumns.getColumn(3), new SpinnerField<Integer>(new NumberPropertyEditor.IntegerPropertyEditor()));
        SpinnerField<Integer> spinner = new SpinnerField<Integer>(new NumberPropertyEditor.IntegerPropertyEditor());
        spinner.setMinValue(0);
        spinner.setMaxValue(100);
        spinner.setIncrement(1);
        editing.addEditor(config.leftColumns.getColumn(3), spinner);

        editing.addBeforeStartEditHandler(new BeforeStartEditEvent.BeforeStartEditHandler<ActividadobraDTO>() {
            @Override
            public void onBeforeStartEdit(BeforeStartEditEvent<ActividadobraDTO> event) {
                if (event.getEditCell().getRow() != 0) {
                    ListStore<ActividadobraDTO> store = editing.getEditableGrid().getStore();
                    actividadAnterior = new ActividadobraDTO(store.get(event.getEditCell().getRow()).getStartDateTime(), store.get(event.getEditCell().getRow()).getEndDateTime(), store.get(event.getEditCell().getRow()).getDuration());
                } else {
                    AlertMessageBox alerta = new AlertMessageBox("Error", "No debe modificar los datos del convenio marco.");
                    alerta.show();
                }
            }
        });


        editing.addCompleteEditHandler(new CompleteEditEvent.CompleteEditHandler<ActividadobraDTO>() {
            @Override
            public void onCompleteEdit(CompleteEditEvent<ActividadobraDTO> event) {
                ListStore<ActividadobraDTO> store = editing.getEditableGrid().getStore();
                ActividadobraDTO ac = store.get(event.getEditCell().getRow());

                /*verifico si el dato a modificar es a fecha de inicio de la actividad*/
                if (event.getEditCell().getCol() == 1) {
                    /*se verifia si la fecha de inicio es mayor que la fecha de fin en este caso seria un error*/
                    ActividadobraDTO actiPadre = taskStore.getParent(ac);
                    if (actiPadre != null) {
                        if (ac.getStartDateTime().compareTo(actiPadre.getStartDateTime()) >= 0 && ac.getStartDateTime().compareTo(actiPadre.getEndDateTime()) < 0) {
                            if (ac.getStartDateTime().compareTo(ac.getEndDateTime()) > 0) {
                                AlertMessageBox alerta = new AlertMessageBox("Error", "La fecha de inicio no puede ser mayor a la fecha de fin");
                                alerta.show();
                                props.startDateTime().setValue(ac, actividadAnterior.getStartDateTime());

                                gantt.getGanttPanel().getContainer().refresh();
                            } else if (ac.getStartDateTime().compareTo(ac.getEndDateTime()) < 0) {
                                props.duration().setValue(ac, gantt.getGanttPanel().getContainer().getTaskDuration(ac, new DateWrapper(ac.getStartDateTime()), new DateWrapper(ac.getEndDateTime())));
                                gantt.getGanttPanel().getContainer().refresh();
                            }
                        } else {
                            AlertMessageBox alerta = new AlertMessageBox("Error", "La fecha de inicio no puede ser menor que la fecha de inicio, ni superior a la fecha de fin");
                            alerta.show();
                            props.startDateTime().setValue(ac, actividadAnterior.getStartDateTime());
                            gantt.getGanttPanel().getContainer().refresh();

                        }
                    } else {
                        AlertMessageBox alerta = new AlertMessageBox("Error", "El convenio no se puede modificar, por favor dirijase a datos basicos");
                        alerta.show();
                        props.startDateTime().setValue(ac, actividadAnterior.getStartDateTime());
                        gantt.getGanttPanel().getContainer().refresh();

                    }
                }
                /*verifico si el dato a modificar es a fecha de fin de la actividad*/
                if (event.getEditCell().getCol() == 2) {
                    /*se verifia si la fecha fin  es mayor que la fecha de inicio en este caso seria un error*/
                    if (ac.getEndDateTime().compareTo(ac.getStartDateTime()) < 0) {
                        AlertMessageBox alerta = new AlertMessageBox("Error", "La fecha de fin no puede ser mayor que la fecha de inicio");
                        alerta.show();
                        props.endDateTime().setValue(ac, actividadAnterior.getEndDateTime());
                        gantt.getGanttPanel().getContainer().refresh();

                    } else if (ac.getEndDateTime().compareTo(ac.getStartDateTime()) > 0) {
                        props.duration().setValue(ac, gantt.getGanttPanel().getContainer().getTaskDuration(ac, new DateWrapper(ac.getStartDateTime()), new DateWrapper(ac.getEndDateTime())));
                        gantt.getGanttPanel().getContainer().refresh();

                    }
                }

                /*verifico si el dato a modificar es la duracion de la actividad y modifico la fecha fin*/
                if (event.getEditCell().getCol() == 3) {
                    if (actividadAnterior.getDuration() < ac.getDuration()) {
                        Date fechaCopia = CalendarUtil.copyDate(ac.getStartDateTime());
                        ac.setEndDateTime(fechaCopia);
                        CalendarUtil.addDaysToDate(ac.getEndDateTime(), ac.getDuration() - 1);
                    } else {
                        ac.getEndDateTime().setDate(ac.getEndDateTime().getDate() - ((actividadAnterior.getDuration() - ac.getDuration())) + 1);
                    }
                }


                GanttDatos.modificarFechaFin(taskStore.getParent(ac), taskStore, props);

            }
        });


        gantt.getLeftGrid().addViewReadyHandler(new ViewReadyEvent.ViewReadyHandler() {
            @Override
            public void onViewReady(ViewReadyEvent event) {
                ((TreeGrid<ActividadobraDTO>) gantt.getLeftGrid()).expandAll();
            }
        });

        DateWrapper dw = new DateWrapper(convenioDTO.getDatefechafin()).clearTime();
        // Set start and end date.
        //gantt.setStartEnd(dw.addDays(-2).asDate(), dw.addMonths(1).asDate());
        gantt.setStartEnd(new DateWrapper(convenioDTO.getDatefechaini()).clearTime().addDays(-2).asDate(), dw.addDays(2).asDate());


        FlowLayoutContainer main = new FlowLayoutContainer();
        main.getElement().setMargins(new Margins(5));
        HTML text = new HTML("Plan Operativo");
        main.add(text);
        ContentPanel cp = new ContentPanel();
        cp.setHeadingText("Plan Operativo");
        cp.getHeader().setIcon(ExampleImages.INSTANCE.table());
        //cp.setPixelSize(1000, 460);        

        cp.setHeight("460px");
        cp.getElement().setMargins(new Margins(5));

        VerticalLayoutContainer vc = new VerticalLayoutContainer();
        cp.setWidget(vc);
        vc.add(createToolBar(taskStore), new VerticalLayoutContainer.VerticalLayoutData(1, -1));
        vc.add(gantt, new VerticalLayoutContainer.VerticalLayoutData(1, 1));
        vc.add(createToolBarInferior());
        main.add(new ToolBarSuperior(service, gantt.getTreeStore(), convenioDTO));
        // main.setPagePosition(200, 0);
        main.setWidth("100%");
        main.add(cp);

        return main;
    }

    // Create ToolBar
    private ToolBar createToolBar(final TreeStore<ActividadobraDTO> tareas) {
//        ToggleButton permensual = new ToggleButton("Mensual");      
//        ToggleButton persemestral= new ToggleButton("6 meses");      
//        ToggleButton peranual = new ToggleButton("Anual");      


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

        // Button to endable/disable show CriticalPath
        final ToggleButton critical = new ToggleButton("Ruta Crítica");
        critical.setValue(false);
        critical.addSelectHandler(new SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                gantt.getConfig().showCriticalPath = critical.getValue();
                gantt.reconfigure(true);
            }
        });






//         permensual.addSelectHandler(new SelectEvent.SelectHandler() {
//            @Override
//            public void onSelect(SelectEvent event) {              
//                gantt.getConfig().timeResolutionUnit=Unit.MONTH;                
//                gantt.reconfigure(true);
//               
//            }
//        });
//        persemestral.addSelectHandler(new SelectEvent.SelectHandler() {
//            @Override
//            public void onSelect(SelectEvent event) {              
//                gantt.getConfig().timeResolutionUnit=Unit.QUATER;
//               
//                gantt.reconfigure(true);
//               
//
//            }
//        });
//        peranual.addSelectHandler(new SelectEvent.SelectHandler() {
//            @Override
//            public void onSelect(SelectEvent event) {              
//                gantt.getConfig().timeResolutionUnit=Unit.MONTH;
//                
//                gantt.reconfigure(true);
//                
//            }
//        }); 
        // tbar.add(cascade);
        tbar.add(critical);
//        tbar.add(permensual);
//        tbar.add(persemestral);
//        tbar.add(peranual);

        return tbar;
    }

    private ToolBar createToolBarInferior() {

        ToolBar tbarinferior = new ToolBar();

        final Button continuar = new Button();
        continuar.setText("Continuar");
        continuar.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
//                service.setContratoDto(GanttDatos.estructurarDatosConvenio(convenioDTO, gantt.getTreeStore(), service), new AsyncCallback<Boolean>() {
//                    @Override
//                    public void onFailure(Throwable caught) {
//                        service.setLog("Problema al transferir el objeto a JSF", null);
//                    }
//
//                    @Override
//                    public void onSuccess(Boolean result) {
//                        service.setNavegacion(2, new AsyncCallback<Boolean>() {
//                            @Override
//                            public void onFailure(Throwable caught) {
//                                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//                            }
//
//                            @Override
//                            public void onSuccess(Boolean result) {
//                               Window.open(retornarNuevoContrato(), "_parent", retornarConfiguracionPagina());
//                            }
//                        });
//                    }
//                });
            }
        });
        continuar.setStyleName("ikont-po-img-continuarGWTInferior");

        final Button finalizar = new Button();
        finalizar.setText("Finalizar");
        finalizar.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        finalizar.setStyleName("ikont-po-img-finalizarGWTInferior");

        tbarinferior.add(continuar);
        tbarinferior.add(finalizar);
        tbarinferior.setStyleName("ikont-po-tb");

        return tbarinferior;
    }

//    public String retornarNuevoContrato() {
//        return "/zoom/Supervisor/nuevoContratoPlanOperativo.xhtml";
//    }
//
//    public String retornarConfiguracionPagina() {
//        return "menubar=si, location=false, resizable=no, scrollbars=si, status=no, dependent=true";
//    }
//    // Creates the static columns

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

        ColumnConfig<ActividadobraDTO, Date> columnfin = new ColumnConfig<ActividadobraDTO, Date>(props.endDateTime());
        columnfin.setHeader("Fin");
        columnfin.setWidth(90);
        columnfin.setSortable(true);
        columnfin.setResizable(true);
        columnfin.setCell(new DateCell(DateTimeFormat.getFormat("yyyy-MM-dd")));
        configs.add(columnfin);

        ColumnConfig<ActividadobraDTO, Integer> column3 = new ColumnConfig<ActividadobraDTO, Integer>(props.duration());
        column3.setHeader("Duración");
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
        cm.addHeaderGroup(0, 0, new HeaderGroupConfig("Plan Operativo", 1,
                5));

        return cm;
    }

    @Override
    public void onModuleLoad() {
        service.setLog("Load module", null);
        cargar();
        //RootPanel.get().add(asWidget());
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
        service.obtenerContratoDTO(new AsyncCallback<ContratoDTO>() {
            @Override
            public void onFailure(Throwable caught) {
                service.setLog("Error cargando convenio", null);
            }

            @Override
            public void onSuccess(ContratoDTO result) {
                convenioDTO = result;
                if (validandoDatosBasicosConvenio()) {
                    // service.setLog("listado actividades = " + convenioDTO.getActividadobras().size(), null);
//                    AlertMessageBox d = new AlertMessageBox("Alerta","Cargando de nuevo");                   
//                    d.show();

//                    DateWrapper dw;
//                    dw = new DateWrapper(convenioDTO.getDatefechaini());
//                    
//                    while(dw.asDate().compareTo(convenioDTO.getDatefechafin())<=0)
//                    {
//                        dw=dw.addDays(30);
//                       service.setLog("dw = " + dw.asDate(),null);
//                    }    

//                    service.setLog("Size fuentes" + convenioDTO.getFuenterecursosconvenios().size(), null);
//                    service.setLog("Idcontrato" + convenioDTO.getIntidcontrato(), null);
//                    service.setLog("Nombre abreviado" + convenioDTO.getNombreAbreviado(), null);
//                    service.setLog("Numero contrato" + convenioDTO.getStrnumcontrato(), null);
//                    service.setLog("Objeto contrato" + convenioDTO.getTextobjeto(), null);
//                    service.setLog("Fecha ini" + convenioDTO.getDatefechaactaini(), null);
//                    service.setLog("Fecha fin" + convenioDTO.getDatefechafin(), null);
//                    service.setLog("Estado convenio" + convenioDTO.getEstadoConvenio(), null);
//                    service.setLog("Numero días" + convenioDTO.getIntduraciondias(), null);
//                    service.setLog("Montos" + convenioDTO.getMontos().size(), null);
//                    service.setLog("Valor contrato" + convenioDTO.getNumvlrcontrato(), null);
//                    service.setLog("Relacion obra fuente recursos" + convenioDTO.getRelacionobrafuenterecursoscontratos().size(), null);
//                    service.setLog("Tipo contrato" + convenioDTO.getTipocontrato().getInttipocontrato(), null);
//                    service.setLog("Valor disponible" + convenioDTO.getValorDisponible(), null);

                    RootPanel.get().add(asWidget());
                } else {
                    service.setLog(msg, null);
                    AlertMessageBox d = new AlertMessageBox("Alerta", msg);
                    d.show();
                }
            }
        });

    }

    public boolean validandoDatosBasicosConvenio() {
        //Validación valor contrato

        //Validación fechas obligatorias
        if (convenioDTO.getDatefechaini() == null || convenioDTO.getDatefechafin() == null) {
            msg = "Debe diligenciar las fechas del convenio";
            return false;
        }

//        if (convenioDTO.getNumvlrcontrato() != null) {
//        }
        return true;
    }

    public void cargarDatosEditarContrato() {
        ContratoDTO contrato = tareaSeleccionada.getContrato();

    }

    public void reembolsarFuenteRecursos(ActividadobraDTO actividadConvenio) {
        for (Iterator it = tareaSeleccionada.getObra().getObrafuenterecursosconvenioses().iterator(); it.hasNext();) {
            ObrafuenterecursosconveniosDTO obfrc = (ObrafuenterecursosconveniosDTO) it.next();
            if (obfrc.getTipoaporte() == 1) {
                FuenterecursosconvenioDTO fuenteModificada = encontrarFuenteRecurso(obfrc.getFuenterecursosconvenio());
                fuenteModificada.setValorDisponible(fuenteModificada.getValorDisponible().add(obfrc.getValor()));
            }
        }
    }

    public FuenterecursosconvenioDTO encontrarFuenteRecurso(FuenterecursosconvenioDTO fuente) {
        for (Iterator it = convenioDTO.getFuenterecursosconvenios().iterator(); it.hasNext();) {
            FuenterecursosconvenioDTO fuenteE = (FuenterecursosconvenioDTO) it.next();
            if (fuenteE.equals(fuente)) {
                return fuenteE;
            }
        }
        return null;
    }
}
