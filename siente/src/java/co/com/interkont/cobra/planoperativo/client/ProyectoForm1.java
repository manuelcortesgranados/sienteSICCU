package co.com.interkont.cobra.planoperativo.client;

import co.com.interkont.cobra.planoperativo.client.dto.ActividadobraDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ActividadobraDTOProps;
import co.com.interkont.cobra.planoperativo.client.dto.ContratoDTO;
import co.com.interkont.cobra.planoperativo.client.dto.DependenciaDTO;
import co.com.interkont.cobra.planoperativo.client.dto.FuenterecursosconvenioDTO;
import co.com.interkont.cobra.planoperativo.client.dto.GanttDatos;
import co.com.interkont.cobra.planoperativo.client.dto.ObjetivosDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ObraDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ObrafuenterecursosconveniosDTO;
import co.com.interkont.cobra.planoperativo.client.resources.images.ExampleImages;
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAble;
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAbleAsync;
import com.gantt.client.Gantt;
import com.gantt.client.config.GanttConfig;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.box.MultiLinePromptMessageBox;
import com.sencha.gxt.widget.core.client.container.AbstractHtmlLayoutContainer.HtmlData;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel.LabelAlign;
import com.sencha.gxt.widget.core.client.form.FormPanelHelper;
import com.sencha.gxt.widget.core.client.form.NumberField;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.treegrid.TreeGrid;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ProyectoForm1 implements IsWidget, EntryPoint {

    /*variables utilizadas para la captura de los datos desde la vista*/
    private TextField nombrePry;
    private DateField fechaInicio;
    private DateField fechaFin;
    private NumberField<BigDecimal> pagodirecto;
    private NumberField<BigDecimal> otrospagos;
    private VerticalPanel vp;
    TextArea txtObjeG;
    String msgerrores = "";
    /*Convenio principal el cual tiene las fuentes de los recursos y 
     * la informacion necesaria
     */
    protected ContratoDTO contratoDto;
    /*
     * Proyecto que se esta creando
     */
    protected ObraDTO proyectoDTO;
    /*
     * Actividad obra padre del GANTT
     */
    protected ActividadobraDTO actividadObraPadre;
    protected Gantt<ActividadobraDTO, DependenciaDTO> gantt;
    //protected Dialog modalPry;
    protected Window modalPry;
    GwtMensajes msj = GWT.create(GwtMensajes.class);
    private CobraGwtServiceAbleAsync service = GWT.create(CobraGwtServiceAble.class);
    /*
     * id temporal de objeto, actividad macro, y obra fuente recurso
     */
    protected int idTemp;
    protected int idTempObj;
    protected int idTempMacroActividades;
    protected int idobraRecursos;
    boolean editar = false;
    protected int posObjGeneral;
    protected Date fechaProyecto;
    /*
     *Elementos para la hora de editar
     */
    protected ActividadobraDTO actividadobraProyectoEditar;
    protected ActividadobraDTOProps propes;
    protected TreeStore<ActividadobraDTO> taskStore;
    protected  int numeroFuentes;
    protected Set<ObrafuenterecursosconveniosDTO> relacionObraFuenteRecursosCopia;
    
    protected int numeracionActividad;

    public ProyectoForm1(ActividadobraDTO actividadobraProyectoEditar, Gantt<ActividadobraDTO, DependenciaDTO> gantt, Window di, ActividadobraDTO actividadObraPadre, ActividadobraDTOProps propes, TreeStore<ActividadobraDTO> taskStore, ContratoDTO convenio) {
        this.actividadobraProyectoEditar = actividadobraProyectoEditar;
        this.gantt = gantt;
        this.modalPry = di;
        this.proyectoDTO = actividadobraProyectoEditar.getObra();
        this.actividadObraPadre = actividadObraPadre;
        this.contratoDto = convenio;
        this.propes = propes;
        this.idTemp = 0;
        this.idTempObj = 0;
        this.idTempMacroActividades = 0;
        this.idobraRecursos = 0;
        this.editar = true;
        this.taskStore = taskStore;
        fechaProyecto = CalendarUtil.copyDate(actividadobraProyectoEditar.getStartDateTime());
        numeroFuentes = proyectoDTO.getObrafuenterecursosconvenioses().size();
        relacionObraFuenteRecursosCopia = ((Set) ((HashSet) proyectoDTO.getObrafuenterecursosconvenioses()).clone());
        instanciarElementosPantalla();
        cargarFormularioEditar();
    }

    public ProyectoForm1(ContratoDTO contratoDtoP, ActividadobraDTO actividadobrapadre) {
        this.contratoDto = contratoDtoP;
        this.proyectoDTO = new ObraDTO();
        this.actividadObraPadre = actividadobrapadre;
        this.idTemp = 0;
        this.idTempObj = 0;
        this.idTempMacroActividades = 0;
        this.idobraRecursos = 0;
    }

    public ProyectoForm1(ActividadobraDTO actividadobrapadre, Gantt<ActividadobraDTO, DependenciaDTO> gantt, Window di, ContratoDTO contratoDtoP, ActividadobraDTOProps propes, TreeStore<ActividadobraDTO> taskStore,int numeracionActividad) {
        this.actividadObraPadre = actividadobrapadre;
        this.gantt = gantt;
        this.modalPry = di;
        this.proyectoDTO = new ObraDTO();
        this.contratoDto = contratoDtoP;
        contratoDto.setValorDisponible(contratoDto.getNumvlrcontrato());
        this.idTemp = 0;
        this.idTempObj = 0;
        this.idTempMacroActividades = 0;
        this.idobraRecursos = 0;
        this.propes = propes;
        this.taskStore = taskStore;
        this.numeracionActividad=numeracionActividad;
        instanciarElementosPantalla();
    }

    private void cargarFormularioEditar() {
        this.nombrePry.setText(proyectoDTO.getStrnombreobra());
        this.fechaInicio.setValue(proyectoDTO.getFechaInicio());
        this.fechaFin.setValue(proyectoDTO.getFechaFin());
        this.pagodirecto.setValue(proyectoDTO.getPagodirecto());
        this.otrospagos.setValue(proyectoDTO.getOtrospagos());
        cargarObjetivoGeneral();
    }

    public void cargarObjetivoGeneral() {
        posObjGeneral = 0;
        boolean encontro = false;
        for (Iterator it = proyectoDTO.getObjetivoses().iterator(); it.hasNext() && !encontro;) {
            ObjetivosDTO obj = (ObjetivosDTO) it.next();
            service.setLog("objetivos tipo:" + obj.getTipoobjetivo(), null);
            if (obj.getTipoobjetivo() == 1) {
                txtObjeG.setValue(obj.getDescripcion());
                encontro = true;
            }
            posObjGeneral++;
        }
    }

    private void instanciarElementosPantalla() {
        /*se instancian todos los elementos de la pantalla*/
        nombrePry = new TextField();
        fechaInicio = new DateField();
        fechaFin = new DateField();
        pagodirecto = (NumberField<BigDecimal>) new NumberField(new NumberPropertyEditor.BigDecimalPropertyEditor());
        otrospagos = (NumberField<BigDecimal>) new NumberField(new NumberPropertyEditor.BigDecimalPropertyEditor());
        txtObjeG = new TextArea();
        txtObjeG.setEmptyText("Objetivo General");
    }

    @Override
    public Widget asWidget() {
        if (vp == null) {
            vp = new VerticalPanel();
            vp.setSpacing(10);
            vp.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
            vp.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
            vp.setStyleName("ikont-po-tb");
            createColumnForm();
        }
        return vp;
    }

    @Override
    public void onModuleLoad() {
        RootPanel.get().add(asWidget());
    }

    private void createColumnForm() {
        String tituloPantalla;
        if (!editar) {
            tituloPantalla = "Planificacion de proyectos";
        } else {
            tituloPantalla = "Editar Proyecto";
        }

        Label tituloPagina = new Label(tituloPantalla);
        tituloPagina.setStyleName("ikont-po-label");
        //tituloPagina.setStyleName(CssRecursos.INSTANCE.commonsCss().estiloLabel());


        vp.add(tituloPagina);


        HtmlLayoutContainer con = new HtmlLayoutContainer(getTableMarkup());
        con.setStyleName("ikont-po-tb");
        vp.add(con);

        int cw = 238;


        getNombrePry().setEmptyText("Nombre del proyecto");
        getNombrePry().setWidth(500);
        getNombrePry().setAutoValidate(true);
        con.add(new FieldLabel(nombrePry, "INFORMACIÓN BASICA"), new HtmlData(".fn"));


        fechaInicio.setWidth(cw);
        fechaInicio.setEmptyText("Fecha inicio");
        fechaInicio.setStyleName("gwt-DatePicker");
        con.add(new FieldLabel(fechaInicio, "Fecha inicio"), new HtmlData(".fechainicio"));
        // con.add(fechaInicio, new HtmlData(".fechainicio"));


        fechaFin.setWidth(cw);
        fechaFin.setEmptyText("Fecha fin");
        con.add(new FieldLabel(fechaFin, "Fecha fin"), new HtmlData(".fechafin"));

        pagodirecto.setWidth(cw);
        pagodirecto.setEmptyText("Pago directo");
        con.add(new FieldLabel(pagodirecto, "Pago directo"), new HtmlData(".pagodirecto"));
        //con.add(pagodirecto, new HtmlData(".pagodirecto"));

        otrospagos.setWidth(cw);
        otrospagos.setEmptyText("Otros pagos");
        con.add(new FieldLabel(otrospagos, "Otros pagos"), new HtmlData(".otrospagos"));
        //con.add(otrospagos, new HtmlData(".otrospagos"));



        final WidgetTablaRubrosPry tblRubros = new WidgetTablaRubrosPry(proyectoDTO, actividadObraPadre, taskStore, editar, actividadobraProyectoEditar);
        con.add(tblRubros.asWidget(), new HtmlData(".tblroles"));


        PushButton btnAdicionarMonto = new PushButton(new Image(ExampleImages.INSTANCE.addbtnaddpry()), new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Window adicionarRubros = new Window();
                adicionarRubros.setBlinkModal(true);
                adicionarRubros.setModal(true);
                ModalRubrosProyecto modalProyecto = new ModalRubrosProyecto(contratoDto, proyectoDTO, idTemp, adicionarRubros, tblRubros, idobraRecursos, editar);
                adicionarRubros.add(modalProyecto.asWidget());
                adicionarRubros.show();
                
            }
        });
        btnAdicionarMonto.setWidth("" + 20);
        con.add(btnAdicionarMonto, new HtmlData(".btnaddmonto"));


        txtObjeG.setWidth(cw);
        txtObjeG.setHeight("" + 150);
        con.add(new FieldLabel(txtObjeG, "Objetivo General"), new HtmlData(".tblobjge"));
        //con.add(txtObjeG, new HtmlData(".tblobjge"));


        final WidgetTablaObjetivos tblObjetivos = new WidgetTablaObjetivos(proyectoDTO, actividadObraPadre, "Especifico", "*OBJETIVOS ESPECIFICOS", true);
        con.add(tblObjetivos.asWidget(), new HtmlData(".tblobjes"));

        PushButton btnAdicionarObje = new PushButton(new Image(ExampleImages.INSTANCE.addbtnaddpry()), new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                WidgetAddObjetivos modalAddObj = new WidgetAddObjetivos(tblObjetivos, proyectoDTO, "Objetivos Especificos", "Por favor ingrese la descripción del objetivo:", 2, true, idTempObj);
                MultiLinePromptMessageBox modal = (MultiLinePromptMessageBox) modalAddObj.asWidget();
                modal.show();

            }
        });
        con.add(btnAdicionarObje, new HtmlData(".objetivoes"));

        final WidgetTablaObjetivos tblMetas = new WidgetTablaObjetivos(proyectoDTO, actividadObraPadre, "Metas", "*META O PRODUCTO ESPERADO", false);
        con.add(tblMetas.asWidget(), new HtmlData(".tblmetas"));

        PushButton btnAdicionarMetas = new PushButton(new Image(ExampleImages.INSTANCE.addbtnaddpry()), new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                WidgetAddObjetivos modalAddMetas = new WidgetAddObjetivos(tblMetas, proyectoDTO, "Metas o producto", "Por favor ingrese la meta o porducto", false, idTempObj);
                MultiLinePromptMessageBox modal = (MultiLinePromptMessageBox) modalAddMetas.asWidget();
                modal.show();
            }
        });
        con.add(btnAdicionarMetas, new HtmlData(".metas"));

        final WidgetTablaMacro tblMacroActividades = new WidgetTablaMacro(proyectoDTO, actividadObraPadre);
        con.add(tblMacroActividades.asWidget(), new HtmlData(".tblmacro"));

        PushButton btnAdicionarMacro = new PushButton(new Image(ExampleImages.INSTANCE.addbtnaddpry()), new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                WidgetAddObjetivos modalAddMacro = new WidgetAddObjetivos(tblMacroActividades, proyectoDTO, "MacroActividades", "Por favor ingrese la macro actividad", false, idTempMacroActividades, true);
                MultiLinePromptMessageBox modal = (MultiLinePromptMessageBox) modalAddMacro.asWidget();
                modal.show();
            }
        });
        con.add(btnAdicionarMacro, new HtmlData(".macro"));
        String nombreBotonPrincipal = "";
        if (!editar) {
            nombreBotonPrincipal = "Añadir Proyecto";
        } else {
            nombreBotonPrincipal = "Editar Proyecto";
        }

        Button btnAdicionarPry = new Button(nombreBotonPrincipal, new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                service.setLog("entre proyecto", null);
                if (!editar) {
                    /*Se carga el proyectoDTO */
                    cargarDatosProyectoDTO();
                    boolean varErrorres = false;
                   String fechasmostrar;

                    if (nombrePry.getValue() == null) {
                        varErrorres = true;
                        msgerrores += "*Por favor ingrese el nombre del proyecto" + "<br/>";
                    }
                    if (proyectoDTO.getObrafuenterecursosconvenioses().isEmpty()) {
                        varErrorres = true;
                        msgerrores += "*El proyecto debe de tener por lo menos un monto asociado" + "<br/>";
                    } else {
                        if (proyectoDTO.getValor() == null) {
                            varErrorres = true;
                            msgerrores += "*Ingrese por lo menos un monto de tipo recurso dinero" + "<br/>";
                        }
                    }
                    if (proyectoDTO.getFechaInicio() != null) {
                        service.setLog("entre en fecha inicio no null:" + proyectoDTO.getFechaInicio() + "Fecha contrato:" + contratoDto.getDatefechaini(), null);
                        if (proyectoDTO.getFechaInicio().compareTo(contratoDto.getDatefechaini()) < 0) {
                            service.setLog("entre en fecha inicio no menor 1", null);
                            varErrorres = true;
                            fechasmostrar = DateTimeFormat.getShortDateFormat().format(contratoDto.getDatefechaini());
                            msgerrores += "*La fecha de inicio del proyecto no puede ser inferior a la fecha de suscripcion del convenio " +  fechasmostrar+ "<br/>";
                        }
                        
                        Date actividadDependenciaFrom = GanttDatos.obtenerActividadDeRaiz(0, contratoDto).getEndDateTime();
                        if(proyectoDTO.getFechaInicio().compareTo(actividadDependenciaFrom)<0){
                            service.setLog("entre en fecha inicio menor dep", null);
                            varErrorres = true;
                            fechasmostrar = DateTimeFormat.getShortDateFormat().format(actividadDependenciaFrom);
                            msgerrores +="La fecha de inicio de la actividad debe ser mayor o igual a la fecha de finalizacion de la actividad dependiente planeación del convenio:" + fechasmostrar + "<br/>";
                         }
                    } else {
                        varErrorres = true;
                        msgerrores += "*La fecha de inicio del proyecto no puede estar vacia" + "<br/>";
                    }

                    if (proyectoDTO.getFechaFin() != null) {
                        service.setLog("entre en fecha fin no null", null);
                        if (proyectoDTO.getFechaFin().compareTo(contratoDto.getDatefechafin()) > 0) {
                            varErrorres = true;
                            fechasmostrar= DateTimeFormat.getShortDateFormat().format(contratoDto.getDatefechafin());
                            msgerrores += "*La fecha de fin del proyecto no puede ser superior a la fecha de finalizacion del convenio " + fechasmostrar+ "<br/>";
                        }
                    }
                    if (proyectoDTO.getFechaInicio() != null && proyectoDTO.getFechaFin() != null) {
                        if (proyectoDTO.getFechaFin().compareTo(proyectoDTO.getFechaInicio()) <= 0) {
                            varErrorres = true;
                            fechasmostrar= DateTimeFormat.getShortDateFormat().format(proyectoDTO.getFechaInicio());
                            msgerrores += "*La fecha de finalizacion del proyecto no puede ser inferior a  " + fechasmostrar+ "<br/>";
                        }
                    }
                    if (txtObjeG.getValue() == null) {
                        varErrorres = true;
                        msgerrores += "*Ingrese  un objetivo general" + "<br/>";
                    } else {
                        proyectoDTO.getObjetivoses().add(new ObjetivosDTO(txtObjeG.getValue()));
                    }
                    if (tblObjetivos.getStore().size() == 0) {
                        varErrorres = true;
                        msgerrores += "*Ingrese  un objetivo especifico" + "<br/>";
                    }
                    if (tblMetas.getStore().size() == 0) {
                        varErrorres = true;
                        msgerrores += "*Ingrese  una meta" + "<br/>";
                    }

                    if (!varErrorres) {
                        modificarValorDisponibleCreando();
                        modalPry.hide();
                        crearActividadPry();
                    } else {
                        AlertMessageBox d = new AlertMessageBox("Error", msgerrores);
                        d.show();
                        msgerrores = "";
                        
                    }


                } else {
                    if (proyectoDTO.getObrafuenterecursosconvenioses().isEmpty()) {
                        ventanaDeError("El proyecto debe de tener al menos una fuente de recursos asociada!");
                    }else{
                    editarProyecto();
                    actividadobraProyectoEditar.setObra(proyectoDTO);
                    modalPry.hide();
                    gantt.getGanttPanel().runCascadeChanges();
                    gantt.getGanttPanel().getContainer().refresh();
                    }
                }
            }
     
        });




        btnAdicionarPry.setWidth("" + 150);
        con.add(btnAdicionarPry);

        // need to call after everything is constructed
        List<FieldLabel> labels = FormPanelHelper.getFieldLabels(vp);
        for (FieldLabel lbl : labels) {
            lbl.setLabelAlign(LabelAlign.TOP);
        }

    }

    /**
     * Método para editar la diferente información de los proyectos plan
     * operativo
     *
     * @author dgarcia
     */
    public void editarProyecto() {
        if (!proyectoDTO.getStrnombreobra().equals(nombrePry.getText())) {
            proyectoDTO.setStrnombreobra(nombrePry.getText());
            propes.name().setValue(actividadobraProyectoEditar, proyectoDTO.getStrnombreobra());
        }
        /*se verifica si se modifico la fecha de finalizacion del proyecto*/
        if (proyectoDTO.getFechaFin() != null) {
            if (proyectoDTO.getFechaFin().compareTo(fechaFin.getValue()) != 0) {
                /*se verifica que la fecha modificada no sea mayor que la fecha fin del convenio*/
                if (fechaFin.getValue().compareTo(actividadObraPadre.getEndDateTime()) <= 0) {
                    /*se verifica si la fecha modificada es mayor o igual que la mayor fecha de finalizacion de los actividades hijas*/
                    if (modificarFechaFin()) {
                        proyectoDTO.setFechaFin(fechaFin.getValue());
                        propes.endDateTime().setValue(actividadobraProyectoEditar, proyectoDTO.getFechaFin());
                        propes.duration().setValue(actividadobraProyectoEditar, CalendarUtil.getDaysBetween(proyectoDTO.getFechaInicio(), proyectoDTO.getFechaFin()));
                    } else {
                        ventanaDeError("La fecha de fin no puede ser menor que la mayor fecha de fin de las actividades hijas");
                    }
                } else {
                    ventanaDeError("La fecha de fin no puede superar la fecha fin del convenio");
                }
            }
        }
        if (proyectoDTO.getFechaInicio().compareTo(fechaInicio.getValue()) != 0) {
            if (fechaInicio.getValue().compareTo(actividadObraPadre.getStartDateTime()) >= 0) {
                odifi(actividadobraProyectoEditar);
            } else {
                ventanaDeError("La fecha de inicio no puede superar la fecha inicio del convenio");
            }
        }
        if (otrospagos.getValue() != null) {
            if (proyectoDTO.getOtrospagos() != otrospagos.getValue()) {
                proyectoDTO.setOtrospagos(otrospagos.getValue());
            }
        }
        if (pagodirecto.getValue() != null) {
            if (proyectoDTO.getPagodirecto() != pagodirecto.getValue()) {
                proyectoDTO.setPagodirecto(pagodirecto.getValue());
            }
        }

    }

    /**
     * Método para editar la actividad padre proyecto y modificar los hijos de
     * esta.
     *
     * @param ActividadobraDTO actividad obra principal que se va a editar.
     *
     * @author dgarcia
     */
    public void odifi(ActividadobraDTO act) {
        m(act);

        if (!act.getChildren().isEmpty()) {
            for (ActividadobraDTO actiHija : act.getChildren()) {
                odifi(actiHija);
            }
        }
    }

    public void m(ActividadobraDTO actiHija) {

        /*verifico el sentido en que tengo que hacer el movimiento de las
         * actividades si necesito aumentar la fecha de inicio o disminuirla*/
        if (fechaInicio.getValue().compareTo(fechaProyecto) > 0) {
            int duracion = CalendarUtil.getDaysBetween(fechaProyecto, fechaInicio.getValue());

            Date fechaI = CalendarUtil.copyDate(actiHija.getStartDateTime());
            CalendarUtil.addDaysToDate(fechaI, duracion);

            Date asigFin = CalendarUtil.copyDate(fechaI);
            actiHija.setEndDateTime(asigFin);

            propes.startDateTime().setValue(actiHija, fechaI);


            Date dateFin = CalendarUtil.copyDate(actiHija.getEndDateTime());
            CalendarUtil.addDaysToDate(dateFin, actiHija.getDuration());
            propes.endDateTime().setValue(actiHija, dateFin);


            if (actiHija.getTipoActividad() == 2) {
                proyectoDTO.setFechaInicio(actiHija.getStartDateTime());
                proyectoDTO.setFechaFin(actiHija.getEndDateTime());
            } else if (actiHija.getTipoActividad() == 3) {
                actiHija.getContrato().setDatefechaini(actiHija.getStartDateTime());
                actiHija.getContrato().setDatefechafin(actiHija.getEndDateTime());
            }

            ActividadobraDTO actObraPadre = taskStore.getParent(actiHija);

            GanttDatos.modificarFechaFin(actObraPadre, taskStore, propes, contratoDto);

        } else {

            int duracion = CalendarUtil.getDaysBetween(fechaInicio.getValue(), fechaProyecto);
            Date fechaI = CalendarUtil.copyDate(actiHija.getStartDateTime());
            fechaI.setDate(fechaI.getDate() - duracion);
            Date asigFin = CalendarUtil.copyDate(fechaI);
            actiHija.setEndDateTime(asigFin);


            propes.startDateTime().setValue(actiHija, fechaI);


            Date dateFin = CalendarUtil.copyDate(actiHija.getEndDateTime());
            dateFin.setDate(dateFin.getDate() + actiHija.getDuration());
            propes.endDateTime().setValue(actiHija, dateFin);

            if (actiHija.getTipoActividad() == 2) {
                proyectoDTO.setFechaInicio(actiHija.getStartDateTime());
                proyectoDTO.setFechaFin(actiHija.getEndDateTime());
            } else if (actiHija.getTipoActividad() == 3) {
                actiHija.getContrato().setDatefechaini(actiHija.getStartDateTime());
                actiHija.getContrato().setDatefechafin(actiHija.getEndDateTime());
            }

            ActividadobraDTO actObraPadre = taskStore.getParent(actiHija);

            GanttDatos.modificarFechaFin(actObraPadre, taskStore, propes, contratoDto);


        }
    }

    public void ventanaDeError(String mensaje) {
        AlertMessageBox alerta = new AlertMessageBox("Error", mensaje);
        alerta.show();
    }

    public boolean modificarFechaFin() {
        boolean esMenor = false;
        List<ActividadobraDTO> lstHijosProyecto = taskStore.getChildren(actividadobraProyectoEditar);
        for (ActividadobraDTO act : lstHijosProyecto) {
            if (act.getEndDateTime().compareTo(fechaFin.getValue()) <= 0) {
                esMenor = true;
            } else {
                esMenor = false;
            }
        }
        return esMenor;
    }

    /*
     * metodo que se encarga de buscar la fuente de recursos 
     * que se encuentra en detereminada posicion.
     */
    public FuenterecursosconvenioDTO buscarFuenteDto(int posicion) {
        int i = 0;
        for (Iterator it = contratoDto.getFuenterecursosconvenios().iterator(); it.hasNext();) {
            if (posicion == i) {
                FuenterecursosconvenioDTO frc = (FuenterecursosconvenioDTO) it.next();
                return frc;
            }
            i++;
        }
        return null;
    }

    public int calcularDuracion() {
        if (proyectoDTO.getFechaInicio() != null && proyectoDTO.getFechaFin() != null) {
            long diferencia = proyectoDTO.getFechaFin().getTime() - proyectoDTO.getFechaInicio().getTime();
            double dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));
            return ((int) dias);
        }
        return actividadObraPadre.getDuration();
    }

    /*
     *Metodo que se encarga de llenar el objeto obra con los datos ingresados 
     *
     */
    public void cargarDatosProyectoDTO() {
        proyectoDTO.setStrnombreobra(nombrePry.getValue());
        proyectoDTO.setFechaInicio(fechaInicio.getValue());
        proyectoDTO.setFechaFin(fechaFin.getValue());
        proyectoDTO.setOtrospagos(otrospagos.getValue());
        proyectoDTO.setPagodirecto(pagodirecto.getValue());

    }

    /*
     *Metodo que se encarga de Crear la actividad proyeco
     *
     */
    public void crearActividadPry() {
        ActividadobraDTO tareaNueva = null;
        if (fechaFin.getValue() == null) {
            tareaNueva = new ActividadobraDTO(proyectoDTO.getStrnombreobra(), proyectoDTO.getFechaInicio(), calcularDuracion(),
                    0, GanttConfig.TaskType.PARENT, 2, false, proyectoDTO);
            tareaNueva.setNumeracion(numeracionActividad);
            numeracionActividad++;

        } else {
            tareaNueva = new ActividadobraDTO(proyectoDTO.getStrnombreobra(), proyectoDTO.getFechaInicio(), proyectoDTO.getFechaFin(),
                    0, GanttConfig.TaskType.PARENT, 2, false, proyectoDTO);
             tareaNueva.setNumeracion(numeracionActividad);
             numeracionActividad++;


        }
        if (actividadObraPadre.getTipoActividad() == 1) {
            for (ActividadobraDTO act : actividadObraPadre.getChildren()) {
                if (act.getName().equals("Ejecución del Convenio")) {
                      enlazaractividadesHijas(act, tareaNueva);
                 
                }
            }

        } else {
                enlazaractividadesHijas(actividadObraPadre, tareaNueva);
            
        }

    }

    /*
     *Metodo que se encarga de enlazar la actividad padre con la nueva activida proyecto creada y mostrarla en el panel del GANTT
     *
     */
    public void enlazaractividadesHijas(ActividadobraDTO actividadPadre, ActividadobraDTO actividadHija) {
        gantt.getGanttPanel().getContainer().getTreeStore().insert(actividadPadre, taskStore.getChildren(actividadPadre).size(), actividadHija);
        actividadPadre.addChild(actividadHija);
        GanttDatos.modificarFechaFin(actividadPadre, taskStore, propes, contratoDto);
        gantt.getGanttPanel().getContainer().getTreeStore().update(actividadPadre);
        ((TreeGrid<ActividadobraDTO>) gantt.getGanttPanel().getContainer().getLeftGrid()).setExpanded(actividadPadre, true);  //tareaSeleccionada.addChild(tareaNueva);
    }

    public void modificarPadre(ActividadobraDTO actividadPadre, ActividadobraDTO actividadHija) {
        if (propes.endDateTime().getValue(actividadHija).compareTo(propes.endDateTime().getValue(actividadPadre)) > 0) {
            int diferencia = CalendarUtil.getDaysBetween(propes.endDateTime().getValue(actividadPadre), propes.endDateTime().getValue(actividadHija));
            Date copiaFecha = propes.endDateTime().getValue(actividadPadre);
            CalendarUtil.addDaysToDate(copiaFecha, diferencia);
            propes.endDateTime().setValue(actividadPadre, copiaFecha);
        }
    }

    private native String getTableMarkup() /*-{
     return ['<table width=100% cellpadding=0 cellspacing=10>',
     '<tr><td class=fn colspan=2></td><td></td></tr>',
     '<tr><td class=fechainicio></td><td class=fechafin></td></tr>',
     '<tr><td class=pagodirecto></td><td class=otrospagos></td></tr>',
     '<tr><td class=tblroles colspan=2></td><td></td></tr>',
     '<tr><td class=btnaddmonto></td><tr>',
     '<tr><td class=tblobjge></td><td class=tblmetas></td></tr>',
     '<tr><td class=objetivoge></td><td class=metas></td><tr>',
     '<tr><td class=tblobjes></td><td class=tblmacro></td></tr>',
     '<tr><td class=objetivoes></td><td class=macro></td><tr>',
     '</table>'
     ].join("");
     }-*/;

   
    public void modificarValorDisponibleCreando() {
        for (Iterator it = proyectoDTO.getObrafuenterecursosconvenioses().iterator(); it.hasNext();) {
            service.setLog("entre en modificar valor disponible editar nuevos", null);
            ObrafuenterecursosconveniosDTO obrafuenterecursoscontratoDTO = (ObrafuenterecursosconveniosDTO) it.next();
            obrafuenterecursoscontratoDTO.getFuenterecursosconvenio().setValorDisponible(obrafuenterecursoscontratoDTO.getFuenterecursosconvenio().getValorDisponible().subtract(obrafuenterecursoscontratoDTO.getValor()));
            obrafuenterecursoscontratoDTO.getFuenterecursosconvenio().setEstaEnFuenteRecurso(true);
            service.setLog("valor disponible despues de substraer:" + obrafuenterecursoscontratoDTO.getFuenterecursosconvenio().getValorDisponible(), null);

        }
    }

    /**
     * @return the nombrePry
     */
    public TextField getNombrePry() {
        return nombrePry;
    }

    /**
     * @param nombrePry the nombrePry to set
     */
    public void setNombrePry(TextField nombrePry) {
        this.nombrePry = nombrePry;
    }

    /**
     * @return the fechaInicio
     */
    public DateField getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(DateField fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public DateField getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(DateField fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the pagodirecto
     */
    public NumberField<BigDecimal> getPagodirecto() {
        return pagodirecto;
    }

    /**
     * @param pagodirecto the pagodirecto to set
     */
    public void setPagodirecto(NumberField<BigDecimal> pagodirecto) {
        this.pagodirecto = pagodirecto;
    }

    /**
     * @return the otrospagos
     */
    public NumberField<BigDecimal> getOtrospagos() {
        return otrospagos;
    }

    /**
     * @param otrospagos the otrospagos to set
     */
    public void setOtrospagos(NumberField<BigDecimal> otrospagos) {
        this.otrospagos = otrospagos;
    }
}
