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
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import co.com.interkont.cobra.planoperativo.client.dto.ActividadobraDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ActividadobraDTOProps;
import co.com.interkont.cobra.planoperativo.client.dto.ContratoDTO;
import co.com.interkont.cobra.planoperativo.client.dto.DependenciaDTOProps;
import co.com.interkont.cobra.planoperativo.client.dto.FuenterecursosconvenioDTO;
import co.com.interkont.cobra.planoperativo.client.dto.GanttDatos;
import co.com.interkont.cobra.planoperativo.client.dto.ObraDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ObrafuenterecursosconveniosDTO;
import co.com.interkont.cobra.planoperativo.client.dto.RelacionobrafuenterecursoscontratoDTO;
import co.com.interkont.cobra.planoperativo.client.resources.images.ExampleImages;
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAble;
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAbleAsync;
import com.gantt.client.event.DependencyContextMenuEvent;
import com.gantt.client.event.DependencyDnDEvent;
import com.gantt.client.event.TaskDnDEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.scheduler.client.core.TimeResolution.Unit;
import com.scheduler.client.core.config.SchedulerConfig.ResizeHandle;
import com.scheduler.client.core.timeaxis.TimeAxisGenerator;
import com.scheduler.client.core.timeaxis.preconfig.DayTimeAxisGenerator;
import com.scheduler.client.core.timeaxis.preconfig.MonthTimeAxisGenerator;
import com.scheduler.client.core.timeaxis.preconfig.WeekTimeAxisGenerator;
import com.scheduler.client.core.timeaxis.preconfig.YearTimeAxisGenerator;
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
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.button.ToggleButton;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.CompleteEditEvent;
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
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;
import com.sencha.gxt.widget.core.client.treegrid.TreeGrid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author desarrollo9
 */
public class PlanOperativoGantt implements IsWidget, EntryPoint {
//px 

    private boolean modolectura = false;

    public boolean isModolectura() {
        return modolectura;
    }

    public void setModolectura(boolean modolectura) {
        this.modolectura = modolectura;
    }

    public PlanOperativoGantt() {
    }

    public PlanOperativoGantt(ContratoDTO convenioDTO) {
        this.convenioDTO = convenioDTO;
        this.fullScreen = true;
    }
    /**
     * Declaración de Objetos propios para manejo del plan operativo
     */
    /**
     * Objeto contrato simple para manejo del convenio con todas sus relaciones
     * a nivel cliente
     */
    private ContratoDTO convenioDTO;
    ActividadobraDTO actividadAnterior;
    boolean fullScreen = Boolean.FALSE;
    protected int numeracionActividades;

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

    /**
     * @return the gantt
     */
    public Gantt<ActividadobraDTO, DependenciaDTO> getGantt() {
        return gantt;
    }

    /**
     * @param gantt the gantt to set
     */
    public void setGantt(Gantt<ActividadobraDTO, DependenciaDTO> gantt) {
        this.gantt = gantt;
    }

//    public interface GanttExampleStyle extends CssResource {
//
//        @ClassName("gwt-label")
//        String estiloLabel();
//    }
//
//    public interface GanttExampleResources extends ClientBundle {
//
//        @Source({"Gantt.css"})
//        GanttExampleStyle css();
//    }
    private Gantt<ActividadobraDTO, DependenciaDTO> gantt;
    final ListStore<DependenciaDTO> depStore = new ListStore<DependenciaDTO>(depProps.key());
    private static final ActividadobraDTOProps props = GWT.create(ActividadobraDTOProps.class);
    private static final DependenciaDTOProps depProps = GWT.create(DependenciaDTOProps.class);
    private static final GwtMensajes msgs = GWT.create(GwtMensajes.class);
    TreeStore<ActividadobraDTO> taskStore = new TreeStore<ActividadobraDTO>(props.key());
    /**
     * Almacena la tarea que ha sido seleccionada en el gantt
     */
    private ActividadobraDTO tareaSeleccionada;
    /**
     * Almacena la dependencia que ha sido seleccionada en el gantt
     */
    private DependenciaDTO dependenciaSeleccionada;
    GwtMensajes msj = GWT.create(GwtMensajes.class);
    GanttConfig config;

    @SuppressWarnings("unchecked")
    @Override
    public Widget asWidget() {
        //service.setLog("As widget", null);

        taskStore.setAutoCommit(true);
        root = GanttDatos.getTareas(convenioDTO);

        for (ActividadobraDTO base : root.getChildren()) {
            taskStore.add(base);
            if (base.hasChildren()) {
                processFolder(taskStore, base);
            }
        }

        numeracionActividades = (taskStore.getAllItemsCount()) + 1;
        depStore.clear();
        depStore.addAll(GanttDatos.getDependencia(convenioDTO));
        config = new GanttConfig();
        // ColumnModel for left static columns
        config.leftColumns = createStaticColumns();
        ArrayList<TimeAxisGenerator> headers = new ArrayList<TimeAxisGenerator>();
        headers.add(new WeekTimeAxisGenerator("MMM d"));
        headers.add(new DayTimeAxisGenerator("EEE"));
        config.timeHeaderConfig = headers;
        //Enable task resize
        if (!isModolectura()) {
            config.resizeHandle = ResizeHandle.BOTH;
        } else {
            config.resizeHandle = ResizeHandle.NONE;
        }

        // Enable dependency DnD
        config.dependencyDnDEnabled = !isModolectura();
        //config.dragCreateEnabled = !isModolectura();
        // Enable task DnD        
        //config.taskDnDEnabled = false;
        config.taskDnDEnabled = !isModolectura();
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
        config.taskContextMenuEnabled = !isModolectura();
        // Enable dependency contextMenu
        config.dependencyContextMenuEnabled = !isModolectura();
        config.eventContextMenuEnabled = false;
        config.showTaskLabel = true;
        //Gantt trabajando por duración
        config.useEndDate = false;
        config.clickCreateEnabled = false;
        //Dejar fijo sin cascada
        config.cascadeChanges = true;

        /**
         * Ventana Modal Confirmar Eliminar Actividad
         */
        final ConfirmMessageBox boxConfim = new ConfirmMessageBox("Confirmar", "Esta seguro que desea eliminar esta actividad?");
        boxConfim.getButtonById(PredefinedButton.YES.name()).setText("Confirmar");
        boxConfim.getButtonById(PredefinedButton.NO.name()).setText("Cancelar");
        boxConfim.setHeight(150);
        boxConfim.addHideHandler(new HideHandler() {
            @Override
            public void onHide(HideEvent event) {
                if (boxConfim.getHideButton() == boxConfim.getButtonById(PredefinedButton.YES.name())) {
                    if (!tareaSeleccionada.isBoolobligatoria()) {
                        eliminarDepStore();
                        service.setElimino(true, null);
                        if (tareaSeleccionada.getTipoActividad() == 2) {
                            ObraDTO obraEliminar = tareaSeleccionada.getObra();
                            reembolsarFuenteRecursos(obraEliminar);
                        }
                        if (tareaSeleccionada.getTipoActividad() == 3) {
                            ContratoDTO contratoActual = tareaSeleccionada.getContrato();
                            ActividadobraDTO actividadPadreProyecto = taskStore.getParent(tareaSeleccionada);
                            ObraDTO obraPadre = actividadPadreProyecto.getObra();
                            if (contratoActual != null && obraPadre != null) {
                                reembosarValorDisponibleAObra(contratoActual, obraPadre, actividadPadreProyecto, tareaSeleccionada.getName());
                            } else {
                                service.setLog("son null", null);
                            }

                        }
                        ActividadobraDTO actPadre = taskStore.getParent(tareaSeleccionada);
                        actPadre.getChildren().remove(tareaSeleccionada);
                        taskStore.remove(tareaSeleccionada);
                        GanttDatos.guardarBorradorConvenio(convenioDTO, service, gantt);
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
                final ProyectoForm proyectoForm1 = new ProyectoForm(tareaSeleccionada, getGantt(), crearProyectoDialog, convenioDTO, props, taskStore);
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
                final ContratoForm contratoForm = new ContratoForm(tareaSeleccionada, getGantt(), crearContratoDialog, props, taskStore, convenioDTO);
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
                //editarProyDialog.setClosable(false);
                ProyectoForm proyectoForm = new ProyectoForm(tareaSeleccionada, getGantt(), editarProyDialog, taskStore.getParent(tareaSeleccionada), props, taskStore, convenioDTO);
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
                //crearContratoDialog.setClosable(false);
                ContratoForm contratoFormEditar = new ContratoForm(tareaSeleccionada, getGantt(), crearContratoDialog, taskStore.getParent(tareaSeleccionada), props, taskStore, convenioDTO);
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

        final MenuItem menuItemConsultarContrato = new MenuItem("Consultar Contrato");
        menuItemConsultarContrato.addSelectionHandler(new SelectionHandler<Item>() {
            @Override
            public void onSelection(SelectionEvent<Item> event) {
                final Window crearContratoDialog = new Window();
                crearContratoDialog.setBlinkModal(true);
                crearContratoDialog.setModal(true);
                crearContratoDialog.setClosable(true);
                ContratoForm contratoFormEditar = new ContratoForm(tareaSeleccionada, getGantt(), crearContratoDialog, taskStore.getParent(tareaSeleccionada), props, taskStore, convenioDTO, true);
                crearContratoDialog.add(contratoFormEditar);
                crearContratoDialog.show();
            }
        });
        config.taskContextMenu.add(menuItemConsultarContrato);

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
                final HitoForm actividadForm = new HitoForm(tareaSeleccionada, getGantt(), crearActDialog, convenioDTO, GanttConfig.TaskType.MILESTONE, 6, taskStore);
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
                final ActividadForm actividadForm = new ActividadForm(tareaSeleccionada, getGantt(), crearActDialog, convenioDTO, GanttConfig.TaskType.LEAF, 4, taskStore);
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

        final AlertMessageBox d = new AlertMessageBox("Error", "La dependencia no puede ser eliminada");
        MenuItem menuItemEliminarDependencia = new MenuItem("Eliminar dependencia");
        menuItemEliminarDependencia.addSelectionHandler(new SelectionHandler<Item>() {
            @Override
            public void onSelection(SelectionEvent<Item> event) {

                if (dependenciaSeleccionada.isIsobligatoria() == false) {

                    encontrarActividadDependenciaAEliminar(dependenciaSeleccionada.getActividadFrom().getNumeracion(), dependenciaSeleccionada.getActividadTo().getNumeracion());
//                    dependenciaSeleccionada.getActividadTo().setPredecesor("");
//                   
//                    

                    if (dependenciaSeleccionada.getIdDependencia() != 0) {

                        dependenciaSeleccionada.getActividadFrom().setObra(null);
                        dependenciaSeleccionada.getActividadTo().setObra(null);
                        getService().adicionarDepenciatoEliminar(dependenciaSeleccionada, null);
                    }
                    depStore.remove(dependenciaSeleccionada);
                } else {
                    d.show();
                }

            }
        });
        config.dependencyContextMenu.add(menuItemEliminarDependencia);

        config.taskProperties = props;
        config.dependencyProperties = depProps;

        // Add zones to weekends
        ArrayList<ZoneGeneratorInt> zoneGenerators = new ArrayList<ZoneGeneratorInt>();
        zoneGenerators.add(new WeekendZoneGenerator()); // Create a zone every weekend
        config.zoneGenerators = zoneGenerators;
        // Create the Gxt Scheduler

        gantt = new Gantt<ActividadobraDTO, DependenciaDTO>(taskStore, depStore, config) {
            @Override
            public DependenciaDTO createDependencyModel(ActividadobraDTO fromTask, ActividadobraDTO toTask, GanttConfig.DependencyType type) {
                boolean band = false;
                if (!toTask.equals(fromTask)) {

                    if (toTask.getPredecesor() != null) {
                        if (!toTask.getPredecesor().isEmpty()) {
                            if (!validarPredecesor(toTask, fromTask.getNumeracion())) {

                                toTask.setPredecesor(toTask.getPredecesor() + "," + fromTask.getNumeracion());
                                band = true;
                            }
                            band = true;
                        } else {
                            toTask.setPredecesor(String.valueOf(fromTask.getNumeracion()));
                            band = true;
                        }
                    } else {
                        toTask.setPredecesor(String.valueOf(fromTask.getNumeracion()));
                        band = true;
                    }

                }
                if (band) {
                    return crearDependencia(fromTask.getNumeracion(), toTask, fromTask, 1, type);
                } else {
                    return new DependenciaDTO();
                }

            }
        };

        getGantt().addTaskContextMenuHandler(new TaskContextMenuEvent.TaskContextMenuHandler<ActividadobraDTO>() {
            @Override
            public void onTaskContextMenu(TaskContextMenuEvent<ActividadobraDTO> event) {
                tareaSeleccionada = event.getTask();
                /**
                 * Tipo Actividad : 1. Actividad Convenio - 2. Proyecto - 3.
                 * Contrato - 4 Actividad - 5 Etapa - 6 Hito - 7 actividades 8
                 * Ejecucion macro
                 *
                 */
                switch (tareaSeleccionada.getTipoActividad()) {
                    case 1:
                        menuItemProyecto.setVisible(false);
                        menuItemContrato.setVisible(false);
                        menuItemEliminarTarea.setVisible(false);
                        menuItemEditarPry.setVisible(false);
                        menuItemEliminarPry.setVisible(false);
                        menuItemEditarContrato.setVisible(false);
                        menuItemConsultarContrato.setVisible(false);
                        menuItemEliminarContrato.setVisible(false);
                        menuItemEliminarHito.setVisible(false);
                        menuItemAnadirHito.setVisible(false);
                        menuItemAñadirTarea.setVisible(false);
                        break;
                    case 2:
                        menuItemEditarPry.setVisible(true);
                        menuItemContrato.setVisible(true);
                        menuItemProyecto.setVisible(false);
                        menuItemEliminarTarea.setVisible(false);
                        menuItemEliminarPry.setVisible(true);
                        // menuItemEliminarPry.setVisible(mostrarEliminar);
                        menuItemEditarContrato.setVisible(false);
                        menuItemConsultarContrato.setVisible(false);
                        menuItemEliminarContrato.setVisible(false);
                        menuItemEliminarHito.setVisible(false);
                        menuItemAnadirHito.setVisible(true);
                        break;
                    case 3:
                        menuItemEditarPry.setVisible(false);
                        menuItemContrato.setVisible(false);
                        menuItemProyecto.setVisible(false);
                        menuItemEliminarTarea.setVisible(false);
                        menuItemEliminarPry.setVisible(false);
                        //Se deshabilitó la edición de contrato, mientras se soluciona el borrado y actualizado en cascada
                        // de obra fuente recurso contrato
                        menuItemEditarContrato.setVisible(false);
                        menuItemConsultarContrato.setVisible(true);
                        menuItemEliminarContrato.setVisible(true);
                        menuItemEliminarHito.setVisible(false);
                        menuItemAnadirHito.setVisible(true);
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
                        menuItemConsultarContrato.setVisible(false);
                        menuItemEliminarContrato.setVisible(false);
                        menuItemAñadirTarea.setVisible(false);
                        menuItemEliminarHito.setVisible(false);
                        menuItemAnadirHito.setVisible(false);
                        break;
                    case 5:
                        menuItemProyecto.setVisible(false);
                        menuItemAñadirTarea.setVisible(true);
                        menuItemContrato.setVisible(false);
                        menuItemEliminarTarea.setVisible(false);
                        menuItemEditarPry.setVisible(false);
                        menuItemEliminarPry.setVisible(false);
                        menuItemEditarContrato.setVisible(false);
                        menuItemConsultarContrato.setVisible(false);
                        menuItemEliminarContrato.setVisible(false);
                        menuItemEliminarHito.setVisible(false);
                        menuItemAnadirHito.setVisible(false);
                        break;
                    case 6:
                        menuItemProyecto.setVisible(false);
                        menuItemAñadirTarea.setVisible(false);
                        menuItemContrato.setVisible(false);
                        menuItemEliminarTarea.setVisible(false);
                        menuItemEditarPry.setVisible(false);
                        menuItemEliminarPry.setVisible(false);
                        menuItemEditarContrato.setVisible(false);
                        menuItemConsultarContrato.setVisible(false);
                        menuItemEliminarContrato.setVisible(false);
                        menuItemEliminarHito.setVisible(true);
                        menuItemAnadirHito.setVisible(false);
                        break;
                    case 8:
                        menuItemProyecto.setVisible(true);
                        menuItemAñadirTarea.setVisible(true);
                        menuItemContrato.setVisible(false);
                        menuItemEliminarTarea.setVisible(false);
                        menuItemEditarPry.setVisible(false);
                        menuItemEliminarPry.setVisible(false);
                        menuItemEditarContrato.setVisible(false);
                        menuItemConsultarContrato.setVisible(false);
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
        getGantt().addDependencyContextMenuHandler(new DependencyContextMenuEvent.DependencyContextMenuHandler<DependenciaDTO>() {
            @Override
            public void onDependencyContextMenu(DependencyContextMenuEvent<DependenciaDTO> event) {
                dependenciaSeleccionada = event.getDependency();
            }
        });
//        getGantt().addDependencyDnDHandler(new DependencyDnDEvent.DependencyDnDHandler<ActividadobraDTO>() {
//
//            @Override
//            public void onDependencyDnD(DependencyDnDEvent<ActividadobraDTO> event) {
//                
//            }
//        });

//        gantt.addBeforeTaskResizeHandler(new BeforeTaskResizeEvent.BeforeTaskResizeHandler<ActividadobraDTO>() {
//            @Override
//            public void onBeforeTaskResize(BeforeTaskResizeEvent<ActividadobraDTO> event) {
//                if (!event.getEventModel().isEsNoEditable()) {
//                    actividadAnterior = new ActividadobraDTO();
//                    actividadAnterior.setStartDateTime(event.getEventModel().getStartDateTime());
//                    actividadAnterior.setEndDateTime(event.getEventModel().getEndDateTime());
//                    actividadAnterior.setDuration(event.getEventModel().getDuration());
//                } else {
////                    gantt.getGanttPanel().disable();
////                    gantt.getGanttPanel().enableEvents();                    
////                    gantt.mask();
//                    alertaMensajes("Esta Actividad no se puede redimensionar");
//                }
//            }
//        });
//        getGantt().addTaskResizeHandler(new TaskResizeEvent.TaskResizeHandler<ActividadobraDTO>() {
//            @Override
//            public void onTaskResize(TaskResizeEvent<ActividadobraDTO> event) {
//
//                ActividadobraDTO actiresi = event.getEventModel();
//                if (!actiresi.isEsNoEditable()) {
//                    //se verifica si se movio de la parte final en este caso se modificaria la fache fin
//                    if (actiresi.getStartDateTime().compareTo(actividadAnterior.getStartDateTime()) == 0) {
//                        //se modifica fecha fin
//                        int duracion = CalendarUtil.getDaysBetween(actiresi.getStartDateTime(), actiresi.getEndDateTime());
//                        props.duration().setValue(actiresi, duracion);
//                        if (modificarFechaFin(actiresi)) {
//                            props.duration().setValue(actiresi, actividadAnterior.getDuration());
//                            props.endDateTime().setValue(actiresi, actividadAnterior.getEndDateTime());
//                            getGantt().getGanttPanel().getContainer().refresh();
//                        }
//                    } else {
//                        //se modifica fecha inicio
//                        int duracion = CalendarUtil.getDaysBetween(actiresi.getStartDateTime(), actiresi.getEndDateTime());
//                        props.duration().setValue(actiresi, duracion);
//                        boolean hayError = modificarFechaInicio(actiresi);
//                        if (hayError) {
//                            props.duration().setValue(actiresi, actividadAnterior.getDuration());
//                            props.endDateTime().setValue(actiresi, actividadAnterior.getEndDateTime());
//                            getGantt().getGanttPanel().getContainer().refresh();
//
//                        }
//                    }
//
//                }
//            }
//        });
        // Editing
        if (!isModolectura()) {
            final GridInlineEditing<ActividadobraDTO> editing = new GridInlineEditing<ActividadobraDTO>(getGantt().getLeftGrid());

            editing.addEditor(config.leftColumns.getColumn(1), new DateField());
            /*editing.addEditor(config.leftColumns.getColumn(2), new DateField());*/
            SpinnerField<Integer> spinnerduracion = new SpinnerField<Integer>(new NumberPropertyEditor.IntegerPropertyEditor());
            spinnerduracion.setMinValue(0);
            spinnerduracion.setIncrement(1);
            //spinnerduracion.setMaxValue(100);
            editing.addEditor(config.leftColumns.getColumn(3), spinnerduracion);
            editing.addEditor(config.leftColumns.getColumn(6), new TextField());
            /*SpinnerField<Integer> spinner = new SpinnerField<Integer>(new NumberPropertyEditor.IntegerPropertyEditor());

             spinner.setMinValue(0);
             spinner.setMaxValue(100);
             spinner.setIncrement(1);
             editing.addEditor(config.leftColumns.getColumn(4), spinner);*/
            //editing.addEditor(config.leftColumns.getColumn(0), new TextField());
//
//            editing.addBeforeStartEditHandler(
//                    new BeforeStartEditEvent.BeforeStartEditHandler<ActividadobraDTO>() {
//                        @Override
//                        public void onBeforeStartEdit(BeforeStartEditEvent<ActividadobraDTO> event) {
//                            ListStore<ActividadobraDTO> store = editing.getEditableGrid().getStore();
//                            ActividadobraDTO ac = store.get(event.getEditCell().getRow());
//                            if (ac.getEditable()) {
//                                if (ac.getTipoActividad() == 6) {
//                                    switch (event.getEditCell().getCol()) {
//                                        case 2:
//                                            ac.setEndDateTime(actividadAnterior.getEndDateTime());
//                                            alertaMensajes("No es posible modificar la fecha fin del hito");
//                                            break;
//                                        case 3:
//                                            ac.setDuration(actividadAnterior.getDuration());
//                                            alertaMensajes("No es posible modificar la duracion del hito");
//                                            break;
//                                    }
//                               } else {
//                                    actividadAnterior = new ActividadobraDTO(store.get(event.getEditCell().getRow()).getStartDateTime(), store.get(event.getEditCell().getRow()).getEndDateTime(), store.get(event.getEditCell().getRow()).getPredecesor(), store.get(event.getEditCell().getRow()).getDuration());
//                                }
//                            } else {
//                                event.setCancelled(true);
//                                alertaMensajes("Esta Actividad no se puede editar");
//                            }
//                        }
//                    });
//            
//            editing.addCompleteEditHandler(
//                    new CompleteEditEvent.CompleteEditHandler<ActividadobraDTO>() {
//                        @Override
//                        public void onCompleteEdit(CompleteEditEvent<ActividadobraDTO> event) {
//                            ListStore<ActividadobraDTO> store = editing.getEditableGrid().getStore();
//                            ActividadobraDTO ac = store.get(event.getEditCell().getRow());
//                            
//                            if (ac.getEditable()) {
//                                try {
//                                    
//                                    
//                                    modificarDatosActividad(ac, event.getEditCell().getCol());
//                                   
//                                   
//                                    ValidacionesPO.validarDuracionActividad(ac.getDuration(), convenioDTO.getIntduraciondias(), " la actividad");
//                                    //ValidacionesPO.validarRedimensionamientoFases(ac,taskStore,depStore,convenioDTO);
//                                    
//                                    ValidacionesPO.validarTareadentroFechasConvenio(ac.getStartDateTime(), ac.getEndDateTime(), convenioDTO.getDatefechaactaini(), convenioDTO.getDatefechafin(), " la actividad");
//                                    
//                                    GanttDatos.modificarFechaFin(taskStore.getParent(ac), taskStore, depStore,props, convenioDTO);
//                                   
//                                    
//                                   
//                                    
////recalcularFechaFinFases();
////debo revisar por que no pasa de modificar fin
////        try {
////            ValidacionesPO.validarRedimensionamientoFasesprincipales(taskStore.getRootItems().get(0).getChildren());
////            
////        } catch (ConvenioException c) {
////          //  taskStore=treecopia;
////            ac.setStartDateTime(actividadAnterior.getStartDateTime());
////                                    ac.setEndDateTime(actividadAnterior.getEndDateTime());
////                                    ac.setDuration(actividadAnterior.getDuration());
////            alertaMensajes(c.getMessage());
////            
////            
////        } finally {
////            getGantt().getGanttPanel().getContainer().refresh();
////            
////        }
//                                    getGantt().refresh();
//                                    //recalcularFechaFinTotalActividades();
//                                    //getGantt().refresh();
//                                    switch (event.getEditCell().getCol()) {
//                                        /*Editando Fecha de inicio - Restricciones : solo debe desplazar la tarea y sus padres mientras se conserven las  
//                                         restricciones de negocio y no modificar la duracion                                
//                                         */
//                                        case 1:
//                                            // modificarFechaInicio(ac);
//
//                                            break;
//                                        /*Editando Fecha de fin - Restricciones : debe redimensionar las tarea y sus padres mientras se conserven las  
//                                         restricciones de negocio y cambiar la duracion                              
//                                         */
//                                        case 2:
//                                            // modificarFechaFin(ac);
//
//                                            break;
//                                        /*Editando Duracion - Restricciones : debe redimensionar la tarea y sus padres mientras se conserven las  
//                                         restricciones de negocio debe modificar la fecha de fin                                
//                                         */
//                                        case 3:
//                                            //modificarPorDuracion(ac);
//
//                                            break;
//                                        /*
//                                         Modificando predecesores
//                                         */
//                                        case 6:
//                                            //modificarPredecesoresActividad(ac);
//                                            break;
//                                    }
//
//                                } catch (ConvenioException c) {
//                                    alertaMensajes(c.getMessage());
//                                    ac.setStartDateTime(actividadAnterior.getStartDateTime());
//                                    ac.setEndDateTime(actividadAnterior.getEndDateTime());
//                                    ac.setDuration(actividadAnterior.getDuration());
//                                }
//                            } else {
//                                alertaMensajes("Esta Actividad no se puede editar");
//                            }
//
//                        }
//                    });

//            editing.addCancelEditHandler(new CancelEditEvent.CancelEditHandler<ActividadobraDTO>() {
//                @Override
//                public void onCancelEdit(CancelEditEvent<ActividadobraDTO> event) {
//                    //service.setLog("entre en cancelar edicion", null);
//                }
//            });
            editing.addCompleteEditHandler(
                    new CompleteEditEvent.CompleteEditHandler<ActividadobraDTO>() {
                        @Override
                        public void onCompleteEdit(CompleteEditEvent<ActividadobraDTO> event) {//                           
                            validarDependencias();
                            redimensionarGantt();                            
                        }
                    });
        }
        getGantt().addTaskDnDHandler(new TaskDnDEvent.TaskDnDHandler<ActividadobraDTO>() {

            @Override
            public void onTaskDnD(TaskDnDEvent<ActividadobraDTO> event) {
               
                validarDependencias();
               redimensionarGantt();      
            }
        });
        
        getGantt().addDependencyDnDHandler(new DependencyDnDEvent.DependencyDnDHandler<ActividadobraDTO>() {

            @Override
            public void onDependencyDnD(DependencyDnDEvent<ActividadobraDTO> event) {
               
                service.setLog("Dependendia dnd", null);
               validarDependencias();
               redimensionarGantt();     
            }
        });
        
        getGantt().getLeftGrid().addViewReadyHandler(new ViewReadyEvent.ViewReadyHandler() {
            @Override
            public void onViewReady(ViewReadyEvent event) {
                ((TreeGrid<ActividadobraDTO>) getGantt().getLeftGrid()).expandAll();
            }
        });

        DateWrapper dw = new DateWrapper(convenioDTO.getDatefechafin()).clearTime();

        getGantt().setStartEnd(new DateWrapper(convenioDTO.getDatefechaini()).clearTime().addDays(-2).asDate(), dw.addDays(2).asDate());

        FlowLayoutContainer main;
        //if (!fullScreen) {
        main = new FlowLayoutContainer();
        //main.getElement().setMargins(new Margins(-780, 0, 0, -10));
        main.setWidth(900);
        if (!isModolectura()) {
            main.setStyleName("main-contenedor-gwt");
        } else {
            main.setStyleName("main-contenedor-gwt-en-ejecucion");
        }
        ContentPanel cp = new ContentPanel();
        cp.setHeadingText("Plan Operativo");
        cp.getHeader().setIcon(ExampleImages.INSTANCE.table());
        cp.setPixelSize(900, 460);
        cp.getElement().setMargins(new Margins(0));
        cp.setHeaderVisible(false);

        VerticalLayoutContainer vc1 = new VerticalLayoutContainer();
        vc1.setWidth("500");
        vc1.setPosition(40, 0);
        if (!isModolectura()) {
            Label tituloPrincipal = new Label(msgs.tituloPlanOperativo());
            tituloPrincipal.setStyleName("ikont-title-1-convenio-gwt");
            Label subTituloPrincipal = new Label(msgs.subtituloPlanOperativo());
            subTituloPrincipal.setStyleName("ikont-title2-convenio-gwt");
            Label mensajeG1 = new Label(msgs.msgGeneralPlanOperativo1());
            mensajeG1.setStyleName("ikont-title-3-convenio-gwt label_texto_convenio");
            vc1.add(tituloPrincipal);
            vc1.add(subTituloPrincipal);
            vc1.add(mensajeG1);

            HorizontalPanel linea = new HorizontalPanel();
            linea.addStyleName("ikont-hr-separador-convenio");
        }

        if (fullScreen) {
            main.setStyleName("main-contenedor-gwt-fullscreen");
            main.getElement().setMargins(new Margins(-50, 0, 0, 0));
            main.setWidth(1300);
            main.setHeight(600);
            cp.setPixelSize(1280, 650);
            vc1.setPosition(400, 0);
            main.setPagePosition(0, 0);
        }
        VerticalLayoutContainer vc = new VerticalLayoutContainer();
        cp.setWidget(vc);

        vc.add(createToolBarPeriodo());
//            vc.add(createToolBar(taskStore), new VerticalLayoutContainer.VerticalLayoutData(1, -1));
        vc.add(getGantt(), new VerticalLayoutContainer.VerticalLayoutData(1, 1));

        if (!isModolectura() && !fullScreen) {
            Menu_superior_gwt menuSupe = new Menu_superior_gwt(service, taskStore, convenioDTO, depStore);
            main.add(menuSupe.asWidget());
        }
        main.add(vc1);
        if (!fullScreen) {
            Sub_menu_gwt submenu = new Sub_menu_gwt(service, taskStore, convenioDTO, depStore);
            main.add(submenu.asWidget());
        }
        main.add(cp);
        if (!isModolectura() && !fullScreen) {
            ToolBarInferior toolinferior = new ToolBarInferior(service, taskStore, convenioDTO, depStore);
            main.add(toolinferior);
        }
        return main;
    }

    public ToolBar createToolBarPeriodo() {
        ToolBar tbar = new ToolBar();
        TextButton days = new TextButton("Días");

        days.addSelectHandler(new SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                redimensionarGantt();
                configurarDias();
            }
        });
        tbar.add(days);

        TextButton weeks = new TextButton("Semanas");
        weeks.addSelectHandler(new SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                redimensionarGantt();
                configurarSemanas();
            }
        });

        tbar.add(weeks);
        TextButton months = new TextButton("Meses");
        months.addSelectHandler(new SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                redimensionarGantt();
                configurarMeses();
            }
        });
        tbar.add(months);

        final ToggleButton critical = new ToggleButton("Ruta Crítica");
        critical.setValue(false);
        critical.addSelectHandler(new SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                redimensionarGantt();
                gantt.getConfig().showCriticalPath = critical.getValue();
                getGantt().reconfigure(true);
            }
        });

        tbar.add(critical);

        if (!isModolectura()) {

            ToolBarSuperior toolBar = new ToolBarSuperior(service, taskStore, convenioDTO, depStore, new PlanOperativoGantt(convenioDTO), fullScreen);
            //main.add(toolBar.asWidget());

            tbar.add(toolBar.asWidget());

        }

        return tbar;
    }

    public void configurarDias() {
        for (int i = 0; i <= 1; i++) {
            GanttConfig ganttConfig = getGantt().getConfig();
            ArrayList<TimeAxisGenerator> headers = new ArrayList<TimeAxisGenerator>();
            headers.add(new WeekTimeAxisGenerator("MMM d"));
            headers.add(new DayTimeAxisGenerator("EEE"));
            ganttConfig.timeHeaderConfig = headers;
            // Define "snap to" resolution
            ganttConfig.timeResolutionUnit = Unit.HOUR;
            ganttConfig.timeResolutionIncrement = 3;
            // Define the DateFormat of the tooltips
            ganttConfig.tipDateFormat = DateTimeFormat
                    .getFormat("MMM d HH:mm");
            ganttConfig.tipClock = true;
            getGantt().setConfig(ganttConfig, true);
        }
    }

    public void configurarSemanas() {
        for (int i = 0; i <= 1; i++) {
            GanttConfig ganttConfig = getGantt().getConfig();
            ArrayList<TimeAxisGenerator> headers = new ArrayList<TimeAxisGenerator>();
            headers.add(new MonthTimeAxisGenerator("MMMM y"));
            headers.add(new WeekTimeAxisGenerator("MMM d"));
            ganttConfig.timeHeaderConfig = headers;
            // Define "snap to" resolution
            ganttConfig.timeResolutionUnit = Unit.DAY;
            ganttConfig.timeResolutionIncrement = 1;
            // Define the DateFormat of the tooltips
            ganttConfig.tipDateFormat = DateTimeFormat
                    .getFormat("y-MM-d");
            ganttConfig.tipClock = false;
            getGantt().setConfig(ganttConfig, true);

        }
    }

    public void configurarMeses() {
        for (int i = 0; i <= 1; i++) {

            GanttConfig ganttConfig = getGantt().getConfig();
            ArrayList<TimeAxisGenerator> headers = new ArrayList<TimeAxisGenerator>();
            headers.add(new YearTimeAxisGenerator("y"));
            headers.add(new MonthTimeAxisGenerator("MMM y"));
            ganttConfig.timeHeaderConfig = headers;

            // Define "snap to" resolution
            ganttConfig.timeResolutionUnit = Unit.DAY;
            ganttConfig.timeResolutionIncrement = 1;
            // Define the DateFormat of the tooltips
            ganttConfig.tipDateFormat = DateTimeFormat
                    .getFormat("y-MM-d");
            ganttConfig.tipClock = false;
            getGantt().setConfig(ganttConfig, true);

        }
    }

// Creates the static columns
    @SuppressWarnings({"rawtypes", "unchecked"})
    private ColumnModel<ActividadobraDTO> createStaticColumns() {
        List<ColumnConfig<ActividadobraDTO, ?>> configs = new ArrayList<ColumnConfig<ActividadobraDTO, ?>>();

        ColumnConfig<ActividadobraDTO, ?> column = new ColumnConfig<ActividadobraDTO, String>(props.name());
        column.setHeader("Actividades");
        column.setWidth(200);
        column.setSortable(false);
        column.setResizable(true);
        configs.add(column);

        ColumnConfig<ActividadobraDTO, Date> column2 = new ColumnConfig<ActividadobraDTO, Date>(props.startDateTime());
        column2.setHeader("Inicio");
        column2.setWidth(90);
        column2.setSortable(false);
        column2.setResizable(true);
        column2.setCell(new DateCell(DateTimeFormat.getFormat("yyyy-MM-dd")));
        configs.add(column2);

        ColumnConfig<ActividadobraDTO, Date> columnfin = new ColumnConfig<ActividadobraDTO, Date>(props.endDateTime());
        columnfin.setHeader("Fin");
        columnfin.setWidth(90);
        columnfin.setSortable(false);
        columnfin.setResizable(true);
        columnfin.setCell(new DateCell(DateTimeFormat.getFormat("yyyy-MM-dd")));
        configs.add(columnfin);

        ColumnConfig<ActividadobraDTO, Integer> column3 = new ColumnConfig<ActividadobraDTO, Integer>(props.duration());
        column3.setHeader("Duración");
        column3.setWidth(60);
        column3.setSortable(false);
        column3.setResizable(true);
        configs.add(column3);
        /*
         ColumnConfig<ActividadobraDTO, Integer> column4 = new ColumnConfig<ActividadobraDTO, Integer>(props.percentDone());
         column4.setHeader("Peso");
         column4.setWidth(60);
         column4.setSortable(true);
         column4.setResizable(true);
         configs.add(column4);
         */
        ColumnConfig<ActividadobraDTO, ?> columnNumeracion = new ColumnConfig<ActividadobraDTO, Integer>(props.numeracion());
        columnNumeracion.setHeader("Nro");
        columnNumeracion.setWidth(35);
        columnNumeracion.setSortable(false);
        columnNumeracion.setResizable(true);
        configs.add(columnNumeracion);

        ColumnConfig<ActividadobraDTO, ?> columnPredecesor = new ColumnConfig<ActividadobraDTO, String>(props.predecesor());
        columnPredecesor.setHeader("Predecesor");
        columnPredecesor.setWidth(85);
        columnPredecesor.setSortable(false);
        columnPredecesor.setResizable(true);
        configs.add(columnPredecesor);

        ColumnModel cm = new ColumnModel(configs);
        cm.addHeaderGroup(0, 0, new HeaderGroupConfig("Plan Operativo", 1, 6));

        return cm;
    }

    @Override
    public void onModuleLoad() {
        cargar();
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
        service.isFullScreen(new AsyncCallback<Boolean>() {
            @Override
            public void onFailure(Throwable caught) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onSuccess(Boolean result) {
                fullScreen = result;
                service.obtenerContratoDTO(new AsyncCallback<ContratoDTO>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        service.setLog("Error cargando convenio", null);
                    }

                    @Override
                    public void onSuccess(ContratoDTO result) {
                        convenioDTO = result;
                        if (validandoDatosBasicosConvenio()) {
                            setModolectura(convenioDTO.isModolecturaplanop());
                            RootPanel.get().add(asWidget());
                            if (convenioDTO.isVermensajeguardado()) {
                                Info.display("", convenioDTO.getMensajeguardado());
                            } else if (convenioDTO.isVermensajeerror()) {
                                AlertMessageBox d = new AlertMessageBox("Alerta", convenioDTO.getMensajeguardado());
                                d.show();
                            }
                            convenioDTO.setVermensajeguardado(false);
                            convenioDTO.setVermensajeerror(false);
                            convenioDTO.setMensajeguardado("");

                        } else {
                            AlertMessageBox d = new AlertMessageBox("Alerta", msg);
                            d.show();
                        }
                    }
                });
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

    public Map<Integer, ActividadobraDTO> obtenerActividadesHijasHojasConvenio(ActividadobraDTO actiPadre) {
        Map<Integer, ActividadobraDTO> mapaActivi = new HashMap<Integer, ActividadobraDTO>();
        for (ActividadobraDTO act : actiPadre.getChildren()) {
            if (act.getName().equals("Acta de Inicio del Convenio")) {
                mapaActivi.put(1, act);
            } else if (act.getName().equals("Reglamento Comité Operativo")) {
                mapaActivi.put(2, act);
            } else if (act.getName().equals("Aprobación de Plan Operativo")) {
                mapaActivi.put(3, act);
            }
        }
        return mapaActivi;
    }

    /*
     * metodo que se encarga de reembolsar el valor de las fuentes de recursos asociadas 
     * al proyecto a las respectivas fuente de recursos de la convenio.
     *
     */
    public void reembolsarFuenteRecursos(ObraDTO obraEliminar) {
        for (ObrafuenterecursosconveniosDTO fuenteRecursosObraEliminar : obraEliminar.getObrafuenterecursosconvenioses()) {
            String nomEntidad = fuenteRecursosObraEliminar.getNombreEntidad();
            int vigencia = fuenteRecursosObraEliminar.getVigencia();
            FuenterecursosconvenioDTO fuenteRecursoPadre = encontrarFuenteRecurso(vigencia, nomEntidad);
            if (fuenteRecursoPadre != null) {
                fuenteRecursoPadre.setValorDisponible(fuenteRecursoPadre.getValorDisponible().add(fuenteRecursosObraEliminar.getValor()));
            }
        }
    }

    public FuenterecursosconvenioDTO encontrarFuenteRecurso(int vigencia, String entidad) {
        for (Iterator it = convenioDTO.getFuenterecursosconvenios().iterator(); it.hasNext();) {
            FuenterecursosconvenioDTO fuenteE = (FuenterecursosconvenioDTO) it.next();
            if (fuenteE.getVigencia() == vigencia && fuenteE.getTercero().getStrnombrecompleto().equals(entidad)) {
                return fuenteE;
            }
        }
        return null;
    }

    /*
     * metodo que se encarga de reembolsar el valor de las fuentes de recursos asociadas 
     * al contrato a las respectivas fuente de recursos de la obra padre
     *
     */
    public void reembosarValorDisponibleAObra(ContratoDTO contratoAEliminar, ObraDTO obraPadre, ActividadobraDTO actividadPadreProyecto, String nombreActividadSeleccionada) {

        if (contratoAEliminar.getRelacionobrafuenterecursoscontratos() != null) {
            for (Iterator it = contratoAEliminar.getRelacionobrafuenterecursoscontratos().iterator(); it.hasNext();) {
                RelacionobrafuenterecursoscontratoDTO fuenteDeContrato = (RelacionobrafuenterecursoscontratoDTO) it.next();
                if (fuenteDeContrato != null) {
                    int vigencia = fuenteDeContrato.getVigenciafuente();
                    String nombreEntidad = fuenteDeContrato.getNombreEntidad();
                    if (obraPadre.getObrafuenterecursosconvenioses() != null) {
                        ObrafuenterecursosconveniosDTO fuenteRecursoObraPadre = buscarRecursoDeObra(vigencia, nombreEntidad, obraPadre.getObrafuenterecursosconvenioses());
                        if (fuenteRecursoObraPadre != null) {
                            fuenteRecursoObraPadre.setValorDisponible(fuenteRecursoObraPadre.getValorDisponible().add(fuenteDeContrato.getValor()));
                            if (!validarEstaFuenteObraEnContratos(actividadPadreProyecto, vigencia, nombreEntidad, nombreActividadSeleccionada)) {
                                fuenteRecursoObraPadre.setEstaEnFuenteRecurso(false);
                            }

                        }
                    }
                }
            }
        } else {
            service.setLog("es null", null);
        }
    }

    /*
     * metodo que se encarga de validar  si en otro contrato se encuentra
     * asociada la misma fuente de recursos de la obra
     *
     */
    public boolean validarEstaFuenteObraEnContratos(ActividadobraDTO actividadObra, int vigencia, String nombreEntidad, String nombreContratoEliminar) {
        boolean estaAsociado = false;
        if (!actividadObra.getChildren().isEmpty()) {
            List<ContratoDTO> lstContratosHijos = new ArrayList<ContratoDTO>();
            for (ActividadobraDTO actiHija : actividadObra.getChildren()) {
                if (actiHija.getTipoActividad() == 3) {
                    if (!actiHija.getName().equals(nombreContratoEliminar)) {
                        lstContratosHijos.add(actiHija.getContrato());
                    }
                }
            }
            for (ContratoDTO contrac : lstContratosHijos) {
                if (contrac.getRelacionobrafuenterecursoscontratos() != null) {
                    for (Iterator ite = contrac.getRelacionobrafuenterecursoscontratos().iterator(); ite.hasNext();) {
                        RelacionobrafuenterecursoscontratoDTO relacionFuente = (RelacionobrafuenterecursoscontratoDTO) ite.next();
                        if (relacionFuente.getVigenciafuente() == vigencia && relacionFuente.getNombreEntidad().equals(nombreEntidad)) {
                            estaAsociado = true;
                        }
                    }
                }
            }
        }
        return estaAsociado;

    }

    /*
     * metodo que se encarga de buscar la ObrafuenterecursosconveniosDTO asociada 
     * a una fuente recurso del contrato que se desea eliminar.
     *
     */
    public ObrafuenterecursosconveniosDTO buscarRecursoDeObra(int vigencia, String nombreEntidad, Set lstFuenteRecursosObra) {
        for (Iterator it = lstFuenteRecursosObra.iterator(); it.hasNext();) {
            ObrafuenterecursosconveniosDTO fuenteRecursoObra = (ObrafuenterecursosconveniosDTO) it.next();
            if (fuenteRecursoObra.getVigencia() == vigencia && fuenteRecursoObra.getFuenterecursosconvenio().getTercero().getStrnombrecompleto().equals(nombreEntidad)) {
                return fuenteRecursoObra;
            }
        }
        return null;

    }

    public void alertaMensajes(String mensaje) {
        AlertMessageBox alerta = new AlertMessageBox("Alerta", mensaje);
        alerta.setModal(true);
        alerta.addHideHandler(new HideHandler() {
            @Override
            public void onHide(HideEvent event) {
//                gantt.getGanttPanel().enable();
//                gantt.getGanttPanel().enableEvents();
//                gantt.unmask();
//                if(!gantt.getGanttPanel().isLoadMask())
//                {
//                     gantt.unmask();
//                }    
            }
        });
        alerta.show();

    }

    /*
     *Metodo que se encarga de validar los Hijos del convenio que se encuentren en las fechas adecuadas
     *
     */
    public void validandoHijosDelConvenio(ActividadobraDTO actividadSeleccionada, boolean puedeEditar) {

        if (taskStore.getParent(actividadSeleccionada).getName().equals("Ejecución del Convenio")) {
            if (actividadSeleccionada.getStartDateTime().compareTo(GanttDatos.obtenerActividadDeRaiz(0, convenioDTO).getEndDateTime()) < 0) {
                puedeEditar = false;
                alertaMensajes("La fecha de inicio de la actividad debe ser mayor o igual a la fecha de finalizacion de la actividad dependiente 'Planeación del Convenio'");
                props.startDateTime().setValue(actividadSeleccionada, actividadAnterior.getStartDateTime());
                getGantt().getGanttPanel().getContainer().refresh();
            } else {
                if (actividadSeleccionada.getTipoActividad() == 4) {
                    if (GanttDatos.validarConLiquidacionConvenio(actividadSeleccionada, convenioDTO)) {
                        puedeEditar = false;
                        props.startDateTime().setValue(actividadSeleccionada, actividadAnterior.getStartDateTime());
                        props.endDateTime().setValue(actividadSeleccionada, actividadAnterior.getEndDateTime());
                        alertaMensajes("La fecha de la actividad debe ser menor o igual a la menor fecha de las actividades asociadas a la etapa de liquidación.");

                    }
                }

            }

        } else if (taskStore.getParent(actividadSeleccionada).getName().equals("Liquidación del Convenio Marco")) {
            if (actividadSeleccionada.getStartDateTime().compareTo(actividadAnterior.getStartDateTime()) != 0) {
                if (actividadSeleccionada.getStartDateTime().compareTo(GanttDatos.obtenerActividadDeRaiz(1, convenioDTO).getEndDateTime()) < 0) {
                    puedeEditar = false;
                    alertaMensajes("La fecha de inicio de la actividad debe ser mayor o igual a la fecha de finalizacion de la actividad dependiente 'Ejecución del Convenio'");
                    props.startDateTime().setValue(actividadSeleccionada, actividadAnterior.getStartDateTime());
                    getGantt().getGanttPanel().getContainer().refresh();
                }
            }
        }
    }

    public void modificarPorDuracion(ActividadobraDTO ac) {

        if (ac.getDuration() >= 0) {
            service.setLog("en modi duracion:" + ac.getDuration(), null);
            // if (actividadAnterior.getDuration() < ac.getDuration()) {
            //modificarFechaFin
            Date fechaCopia = CalendarUtil.copyDate(ac.getStartDateTime());
            CalendarUtil.addDaysToDate(fechaCopia, ac.getDuration() - 1);
            ac.setEndDateTime(fechaCopia);
            Date fechaModificada = CalendarUtil.copyDate(fechaCopia);
            //modificarFechaFin(ac);
            if (ac.getEndDateTime().compareTo(fechaModificada) != 0) {
                props.duration().setValue(ac, actividadAnterior.getDuration());

            } else {
                //GanttDatos.modificarFechaFin(taskStore.getParent(ac), taskStore, props, convenioDTO);
            }
            getGantt().getGanttPanel().getContainer().refresh();
        } else {
            alertaMensajes("Por favor ingrese valores positivos");
            props.duration().setValue(ac, actividadAnterior.getDuration());
            getGantt().getGanttPanel().getContainer().refresh();

        }
    }

    public DependenciaDTO crearDependencia(int numeracionPredecesor, ActividadobraDTO actividadTo, ActividadobraDTO actividadFrom, int tipoCreacion, GanttConfig.DependencyType type) {
        //ActividadobraDTO actividadFrom = buscarActividadObraPredecesora(numeracionPredecesor);
        DependenciaDTO dep = new DependenciaDTO();
        //if (actividadFrom != null) {

        dep.setId("" + dep.hashCode());
        dep.setFromId(actividadFrom.getId());
        dep.setToId(actividadTo.getId());
        if (type == null) {
            dep.setType(GanttConfig.DependencyType.ENDtoSTART);
        } else {
            dep.setType(type);
        }
        dep.setActividadFrom(actividadFrom);
        dep.setActividadTo(actividadTo);
        dep.setIsobligatoria(false);
//            if (tipoCreacion == 0) {
//                gantt.getGanttPanel().getContainer().getDependencyStore().add(dep);
//                gantt.getGanttPanel().getContainer().refresh();
//            }
        // }
        return dep;

    }

//    public Set<Integer> cargarSetLstPredecesoras(int numeracion) {
//        Set<Integer> lstPredecesores = new HashSet<Integer>();
//        for (DependenciaDTO dep : gantt.getGanttPanel().getContainer().getDependencyStore().getAll()) {
//            if (dep.getActividadFrom().getNumeracion() == numeracion) {
//                lstPredecesores.add(dep.getActividadTo().getNumeracion());
//            }
//        }
//        return lstPredecesores;
//    }
//    public String crearCadenaPredecesoresAnteriores(Set<Integer> lstPredecesoras) {
//        String cadena = "";
//        int count = lstPredecesoras.size();
//        List<Integer> lstAux = new ArrayList<Integer>(lstPredecesoras);
//        for (int i = 0; i < lstPredecesoras.size(); i++) {
//            if (i != count - 1) {
//                cadena = cadena + "" + lstAux.get(i) + ",";
//            } else {
//                cadena = cadena + "" + lstAux.get(i);
//            }
//        }
//        return cadena;
//    }
    public DependenciaDTO buscarDependencia(int idActividadFrom, int idActividadTo) {
        for (DependenciaDTO dep : gantt.getGanttPanel().getContainer().getDependencyStore().getAll()) {
            if (dep.getActividadFrom().getNumeracion() == idActividadFrom && dep.getActividadTo().getNumeracion() == idActividadTo) {
                return dep;
            }
        }
        return null;
    }

    public ActividadobraDTO buscarActividadObraPredecesora(int numeracionPredecesora) {
        for (ActividadobraDTO acti : taskStore.getAll()) {
            if (acti.getNumeracion() == numeracionPredecesora) {
                return acti;
            }
        }
        return null;
    }

    public boolean verificarNumeracionActividad(int numeracionPredecesora) {
        for (ActividadobraDTO acti : taskStore.getAll()) {
            if (acti.getNumeracion() == numeracionPredecesora) {
                return true;
            }
        }
        return false;
    }

    public boolean validarPredecesor(ActividadobraDTO ac, int num) {
        String[] temp;
        temp = ac.getPredecesor().split(",");
        for (String temp1 : temp) {
            if (temp1.compareTo(String.valueOf(num)) == 0) {
                return true;
            }
        }

        return false;
    }

    /*metodo que se encarga que predecesores se eliminaron de la actividad,
     * los remueve del gantt
     *
     * @param ActividadobraDTO ac, actividad que se esta modificando
     */
    public void eliminarPredecesorasActividad(ActividadobraDTO ac) {
        List<Integer> lstPredecesoresEliminados = GanttDatos.eliminarPredecesor(ac, actividadAnterior.getPredecesor(), actividadAnterior);
        if (lstPredecesoresEliminados != null) {
            if (!lstPredecesoresEliminados.isEmpty()) {
                for (int predecesorEliminado : lstPredecesoresEliminados) {
                    DependenciaDTO dependenciaEliminar = buscarDependencia(predecesorEliminado, ac.getNumeracion());
                    gantt.getGanttPanel().getContainer().getDependencyStore().remove(dependenciaEliminar);
                }
                gantt.refresh();
            }
        }
    }

    public void encontrarActividadDependenciaAEliminar(int numeracionFrom, int numeracionTo) {
        for (ActividadobraDTO acti : taskStore.getAll()) {
            if (acti.getNumeracion() == numeracionTo) {
                if (acti.getPredecesor() != null) {
                    String[] temp = acti.getPredecesor().split(",");
                    for (int i = 0; i < temp.length; i++) {
                        if (Integer.parseInt(temp[i]) == numeracionFrom) {
                            acti.getLstPredecesores().remove(Integer.parseInt(temp[i]));
                            acti.setPredecesor(acti.getPredecesor().replace("" + temp[i], ""));
                            encontratComaEliminar(acti);
                        }
                    }
                }
            }
        }
    }

    public void encontratComaEliminar(ActividadobraDTO acti) {
        if (acti.getPredecesor().charAt(0) == ',') {
            String predecesor = acti.getPredecesor().substring(1, acti.getPredecesor().length());
            acti.setPredecesor(predecesor);
        } else if (acti.getPredecesor().charAt(acti.getPredecesor().length() - 1) == ',') {
            String predecesor = acti.getPredecesor().substring(0, acti.getPredecesor().length() - 1);
            acti.setPredecesor(predecesor);
        } else {
            for (int i = 1; i < acti.getPredecesor().length() - 1; i++) {
                if (acti.getPredecesor().charAt(i) == ',' && acti.getPredecesor().charAt(i + 1) == ',') {
                    String predecesor1 = acti.getPredecesor().substring(0, i);
                    String predecesor2 = acti.getPredecesor().substring(i + 1, acti.getPredecesor().length());
                    String predecesor = predecesor1 + predecesor2;
                    acti.setPredecesor(predecesor);
                }
            }
        }

    }

    /*metodo que se encarga de carga todaas las actividades relacionadas
     * con la actividad seleccionada*
     */
    public void listaHijasDeActividadEliminada(ActividadobraDTO actividadSeleccionada, List<ActividadobraDTO> lstActividades) {
        lstActividades.add(actividadSeleccionada);

        if (actividadSeleccionada.hasChildren()) {
            for (ActividadobraDTO ac : actividadSeleccionada.getChildren()) {
                listaHijasDeActividadEliminada(ac, lstActividades);
            }
        }
    }

    /*metodo que se encarga de verificar cuales de las actividades a eliminar 
     * estan asociadas con dependencias para proceder a eliminarlas.
     */
    public List<DependenciaDTO> eliminarDependenciasActividadEliminar(List<ActividadobraDTO> lstActividades) {
        List<DependenciaDTO> lstDepenEliminar = new ArrayList<DependenciaDTO>();
        for (DependenciaDTO dep : depStore.getAll()) {
            if (lstActividades.contains(dep.getActividadTo())) {
                lstDepenEliminar.add(dep);
            } else {
                if (lstActividades.contains(dep.getActividadFrom())) {
                    lstDepenEliminar.add(dep);
                }

            }
        }
        return lstDepenEliminar;

    }

    /*metodo que se encarga de eliminar las dependencias
     * de las actividades a eliminar del depstore.
     */
    public void eliminarDepStore() {
        List<ActividadobraDTO> lstActividadesEliminar = new ArrayList<ActividadobraDTO>();
        listaHijasDeActividadEliminada(tareaSeleccionada, lstActividadesEliminar);
        List<DependenciaDTO> lstDepEliminar = eliminarDependenciasActividadEliminar(lstActividadesEliminar);

        for (DependenciaDTO dep : lstDepEliminar) {
            depStore.remove(dep);
        }

        Set dependencias = new HashSet(depStore.getAll());
        convenioDTO.setDependenciasGenerales(dependencias);
    }

    public void redimensionarGantt() {
        service.setLog("Redimensionar gantt", null);
        if (!getGantt().getGanttPanel().getStart().addDays(2).asDate().equals(getGantt().getFirstTask())
                || !getGantt().getGanttPanel().getEnd().addDays(-2).asDate().equals(getGantt().getLastTask())) {
            getGantt().setStartEnd(new DateWrapper(getGantt().getFirstTask()).clearTime().addDays(-2).asDate(),
                    new DateWrapper(getGantt().getLastTask()).addDays(2).asDate());
            getGantt().getGanttPanel().getContainer().reconfigure(true);
            
        }
    }

    public void validarDependencias( ) {

        List<DependenciaDTO> lista = getGantt().getDependencyStore().getAll();

        for(DependenciaDTO dep:lista)
        {    
            //DependenciaDTO dep = lista.get(i);
            if (dep.getType().equals(GanttConfig.DependencyType.ENDtoSTART) && dep.getActividadTo().getTaskType().equals(GanttConfig.TaskType.PARENT)) {
                DateWrapper dw = new DateWrapper(dep.getActividadFrom().getStartDateTime()).clearTime();
               
                dep.getActividadFrom().setEndDateTime(dw.addDays(dep.getActividadFrom().getDuration() - 1).asDate());
                if (dep.getActividadFrom().getChildren().isEmpty()) {
                    dw = new DateWrapper(dep.getActividadFrom().getEndDateTime()).clearTime();
                   
                } else {
                    dw = new DateWrapper(GanttDatos.obtenerMayorFechaFin(dep.getActividadFrom().getChildren())).clearTime();
                }
                
                dep.getActividadTo().setStartDateTime(dw.addDays(+1).asDate());
                dw = new DateWrapper(dep.getActividadTo().getStartDateTime()).clearTime();
                dep.getActividadTo().setEndDateTime(dw.addDays(dep.getActividadTo().getDuration() - 1).asDate());
                /**
                 * Modificar hijos
                 */
                for (ActividadobraDTO act : dep.getActividadTo().getChildren()) {
                    if (act.getStartDateTime().before(dep.getActividadTo().getStartDateTime())) {
                        act.setStartDateTime(dep.getActividadTo().getStartDateTime());
                        dw = new DateWrapper(act.getStartDateTime()).clearTime();
                        act.setEndDateTime(dw.addDays(act.getDuration() - 1).asDate());
                    }
                }
                service.setLog("******************************************", null);
                 service.setLog("act from"+dep.getActividadFrom().getName(), null);
                 service.setLog("fecha inicio from "+dep.getActividadFrom().getStartDateTime(), null);
                 service.setLog("fecha fin from "+dep.getActividadFrom().getEndDateTime(), null);
                 service.setLog("-------------------------------------------------------------", null);
                 service.setLog("act to"+dep.getActividadTo().getName(), null);
                 service.setLog("fecha inicio to "+dep.getActividadTo().getStartDateTime(), null);
                 service.setLog("fecha fin to "+dep.getActividadTo().getEndDateTime(), null);
                 
                 
            }   
        }
       getGantt().getGanttPanel().reconfigure();
        getGantt().refresh();
    }
}
