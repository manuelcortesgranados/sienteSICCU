package co.com.interkont.cobra.planoperativo.client;

import co.com.interkont.cobra.planoperativo.client.dto.ActividadobraDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ActividadobraDTOProps;
import co.com.interkont.cobra.planoperativo.client.dto.ContratoDTO;
import co.com.interkont.cobra.planoperativo.client.dto.DependenciaDTO;
import co.com.interkont.cobra.planoperativo.client.dto.FuenterecursosconvenioDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ObjetivosDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ObraDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ObrafuenterecursosconveniosDTO;
import co.com.interkont.cobra.planoperativo.client.dto.RubroDTO;
import co.com.interkont.cobra.planoperativo.client.dto.TerceroDTO;
import co.com.interkont.cobra.planoperativo.client.resources.images.ExampleImages;
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAble;
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAbleAsync;
import com.gantt.client.Gantt;
import com.gantt.client.config.GanttConfig;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.box.MultiLinePromptMessageBox;
import com.sencha.gxt.widget.core.client.container.AbstractHtmlLayoutContainer.HtmlData;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.event.ParseErrorEvent;
import com.sencha.gxt.widget.core.client.event.ParseErrorEvent.ParseErrorHandler;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel.LabelAlign;
import com.sencha.gxt.widget.core.client.form.FormPanelHelper;
import com.sencha.gxt.widget.core.client.form.NumberField;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor.BigDecimalPropertyEditor;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.validator.MaxLengthValidator;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.treegrid.TreeGrid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;

public class ProyectoForm1 implements IsWidget, EntryPoint {

    /*variables utilizadas para la captura de los datos desde la vista*/
    private TextField nombrePry;
    private TextField tipoRecurso;
    private TextArea objetivoGeneral;
    //private TextArea objetivoEspecifico;
    private ComboBox<TerceroDTO> lstEntidadesConvenio;
    private ListBox comboCatRubros = new ListBox();
    //private ComboBox<RubroDTO> comboCatRubros;
    private ListBox comboRubros = new ListBox();
    //private ComboBox<RubroDTO> comboRubros;
    //ListBox lstTipoAporte;
    private NumberField<BigDecimal> montoAportado;
    private TextField meta;
    private TextArea macroActividades;
    private DateField fechaInicio;
    private DateField fechaFin;
//    private static final int COLUMN_FORM_WIDTH = 686;
//    private static final int COLUMN_FORM_HEIGHT = 100;
    private VerticalPanel vp;
    /*Convenio principal el cual tiene las fuentes de los recursos y 
     * la informacion necesaria
     */
    ContratoDTO contratoDto;
    List<RubroDTO> lstRubrosDto;
    int idTemp;
    int idTempObj;
    int idTempMacroActividades;
    ObraDTO proyectoDTO;
    TerceroDTO terceroDto;
    RubroDTO rubroDto;
    FuenterecursosconvenioDTO fuenteRecursosConveDTO;
    ActividadobraDTO actividadObraPadre;
    Gantt<ActividadobraDTO, DependenciaDTO> gantt;
    Dialog modalPry;
    ActividadobraDTOProps propes;
    private CobraGwtServiceAbleAsync service = GWT.create(CobraGwtServiceAble.class);
    GwtMensajes msj = GWT.create(GwtMensajes.class);

    public ProyectoForm1(ContratoDTO contratoDtoP, ActividadobraDTO actividadobrapadre) {
        this.contratoDto = contratoDtoP;
        proyectoDTO = new ObraDTO();
        this.actividadObraPadre = actividadobrapadre;
        service.setLog("tarea Seleccionada=" + actividadobrapadre.getStartDateTime(), null);
        idTemp = 0;
        idTempObj = 0;
        idTempMacroActividades = 0;
    }

    public ProyectoForm1(ActividadobraDTO actividadobrapadre, Gantt<ActividadobraDTO, DependenciaDTO> gantt, Dialog di, ContratoDTO contratoDtoP) {
        this.actividadObraPadre = actividadobrapadre;
        this.gantt = gantt;
        modalPry = di;
        proyectoDTO = new ObraDTO();
        this.contratoDto = contratoDtoP;
        lstRubrosDto = new ArrayList<RubroDTO>();
        contratoDto.setValorDisponible(contratoDto.getNumvlrcontrato());
        service.setLog("convenio dis" + contratoDto.getValorDisponible(), null);
        idTemp = 0;
    }

    /**
     * @return the objetivoGeneral
     */
    public TextArea getObjetivoGeneral() {
        return objetivoGeneral;
    }

    /**
     * @param objetivoGeneral the objetivoGeneral to set
     */
    public void setObjetivoGeneral(TextArea objetivoGeneral) {
        this.objetivoGeneral = objetivoGeneral;
    }

    /**
     * @return the montoAportado
     */
    public NumberField<BigDecimal> getMontoAportado() {
        return montoAportado;
    }

    /**
     * @param montoAportado the montoAportado to set
     */
    public void setMontoAportado(NumberField<BigDecimal> montoAportado) {
        this.montoAportado = montoAportado;
    }

    /**
     * @return the meta
     */
    public TextField getMeta() {
        return meta;
    }

    /**
     * @param meta the meta to set
     */
    public void setMeta(TextField meta) {
        this.meta = meta;
    }

    /**
     * @return the macroActividades
     */
    public TextArea getMacroActividades() {
        return macroActividades;
    }

    /**
     * @param macroActividades the macroActividades to set
     */
    public void setMacroActividades(TextArea macroActividades) {
        this.macroActividades = macroActividades;
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
    EntidadProperties propse = GWT.create(EntidadProperties.class);
    final ListStore<TerceroDTO> entidades = new ListStore<TerceroDTO>(propse.intcodigo());
    RubroProperties props = GWT.create(RubroProperties.class);
    final ListStore<RubroDTO> rubros = new ListStore<RubroDTO>(props.idrubro());
    final ListStore<RubroDTO> catrubros = new ListStore<RubroDTO>(props.idrubro());

    @Override
    public Widget asWidget() {
        if (vp == null) {
            vp = new VerticalPanel();
            vp.setSpacing(10);
//            vp.setWidth("" + COLUMN_FORM_WIDTH);
//            vp.setHeight("" + COLUMN_FORM_HEIGHT);
            vp.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
            vp.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
            createColumnForm();
        }
        return vp;
    }

    @Override
    public void onModuleLoad() {
        RootPanel.get().add(asWidget());
    }

    private void createColumnForm() {

        vp.add(new Label(msj.planificacionpry()));

        HtmlLayoutContainer con = new HtmlLayoutContainer(getTableMarkup());
        vp.add(con);

        int cw = 238;

        nombrePry = new TextField();
        getNombrePry().setEmptyText("Nombre del proyecto");
        getNombrePry().setWidth(cw);
        getNombrePry().setAllowBlank(false);
        getNombrePry().addValidator(new MaxLengthValidator(250));
        getNombrePry().setAutoValidate(true);
        con.add(new FieldLabel(nombrePry, "INFORMACIÓN BASICA"), new HtmlData(".fn"));

        fechaInicio = new DateField();
        fechaInicio.setWidth(cw);
        fechaInicio.setEmptyText("Fecha inicio");
        fechaInicio.setAllowBlank(true);
        fechaInicio.setAutoValidate(true);
        con.add(fechaInicio, new HtmlData(".fechainicio"));

        fechaFin = new DateField();
        fechaFin.setWidth(cw);
        fechaFin.setEmptyText("Fecha fin");
        con.add(fechaFin, new HtmlData(".fechafin"));
//
//        objetivoGeneral = new TextArea();
//        getObjetivoGeneral().setHeight("" + 100);
//        getObjetivoGeneral().setWidth("" + (cw - 10));
//        getObjetivoGeneral().setText("General");
//        getObjetivoGeneral().addClickHandler(new ClickHandler() {
//            @Override
//            public void onClick(ClickEvent event) {
//                if (getObjetivoGeneral().getText().compareTo("General") == 0) {
//                    getObjetivoGeneral().setText("");
//                }
//
//            }
//        });
//        getObjetivoGeneral().addBlurHandler(new BlurHandler() {
//            @Override
//            public void onBlur(BlurEvent event) {
//                if (getObjetivoGeneral().getText().compareTo("") == 0) {
//                    getObjetivoGeneral().setText("General");
//                }
//            }
//        });
//        con.add(new FieldLabel(objetivoGeneral, "OBJETIVOS"), new HtmlData(".objetivog"));


//        setMeta(new TextField());
//        getMeta().setWidth(cw);
//        getMeta().setAllowBlank(false);
//        con.add(new FieldLabel(getMeta(), "*META O PRODUCTO ESPERADO"), new HtmlData(".meta"));
//
//        setMacroActividades(new TextArea());
//        getMacroActividades().setHeight("" + 90);
//        getMacroActividades().setWidth("" + cw);
//        con.add(new FieldLabel(getMacroActividades(), "*MACROACTIVIDADES"), new HtmlData(".macro"));

        final Dialog adicionarRubros = new Dialog();
        adicionarRubros.setHideOnButtonClick(true);
        adicionarRubros.setPredefinedButtons();
        adicionarRubros.setModal(true);
        adicionarRubros.setAnimCollapse(true);
        adicionarRubros.setResizable(false);
        final WidgetTablaRubrosPry tblRubros = new WidgetTablaRubrosPry(proyectoDTO, actividadObraPadre);
        con.add(tblRubros.asWidget(), new HtmlData(".tblroles"));


        PushButton btnAdicionarMonto = new PushButton(new Image(ExampleImages.INSTANCE.addbtnaddpry()), new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                ModalRubrosProyecto modalProyecto = new ModalRubrosProyecto(contratoDto, proyectoDTO, idTemp, adicionarRubros, tblRubros);
                adicionarRubros.add(modalProyecto.asWidget());
                adicionarRubros.show();
            }
        });
        btnAdicionarMonto.setWidth("" + 20);
        con.add(btnAdicionarMonto, new HtmlData(".btnaddmonto"));


        final WidgetTablaObjetivos tblObjetivosg = new WidgetTablaObjetivos(proyectoDTO, actividadObraPadre, "General", "*OBJETIVOS GENERALES", true);
        con.add(tblObjetivosg.asWidget(), new HtmlData(".tblobjge"));

        // StringFlexTableUnaColumna objetivosTable = new StringFlexTableUnaColumna("12em", 1, 1,tblObjetivos,proyectoDTO);

        PushButton btnAdicionarObjge = new PushButton(new Image(ExampleImages.INSTANCE.addbtnaddpry()), new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                WidgetAddObjetivos modalAddObj = new WidgetAddObjetivos(tblObjetivosg, proyectoDTO, "Objetivos Generales", "Por favor ingrese la descripción del objetivo:", 1, true, idTempObj);
                MultiLinePromptMessageBox modal = (MultiLinePromptMessageBox) modalAddObj.asWidget();
                modal.show();
            }
        });
        con.add(btnAdicionarObjge, new HtmlData(".objetivoge"));

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

//        PushButton btnAdicionarMeta = new PushButton(new Image(ExampleImages.INSTANCE.addbtnaddpry()), new ClickHandler() {
//            @Override
//            public void onClick(ClickEvent event) {
//                ObjetivosDTO metaDTO = new ObjetivosDTO(meta.getText(), false);
//                proyectoDTO.getObjetivoses().add(metaDTO);
//                meta.setText("");
//            }
//        });
//        btnAdicionarMeta.setWidth("" + 20);
//        con.add(btnAdicionarMeta, new HtmlData(".btnaddmeta"));
//
//        PushButton btnAdicionarMacro = new PushButton(new Image(ExampleImages.INSTANCE.addbtnaddpry()), new ClickHandler() {
//            @Override
//            public void onClick(ClickEvent event) {
//                ActividadobraDTO actividadobradto = new ActividadobraDTO(macroActividades.getText(), 7);
//
//                proyectoDTO.getActividadobras().add(actividadobradto);
//                limpiarMacroActi();
//            }
//        });
//        btnAdicionarMacro.setWidth("" + 20);
//        con.add(btnAdicionarMacro, new HtmlData(".btnaddmacro"));
//
        Button btnAdicionarPry = new Button("Añadir Proyecto", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                /*Se carga el proyectoDTO */
                cargarDatosProyectoDTO();
                boolean varErrorres = false;
                String msgerrores = "";
                if (proyectoDTO.getObrafuenterecursosconvenioses().isEmpty()) {
                    varErrorres = true;
                    msgerrores += "El proyecto debe de tener por lo menos un monto asociado" + "<br/>";
                }
                if (proyectoDTO.getFechaInicio() != null) {
                    if (proyectoDTO.getFechaInicio().compareTo(contratoDto.getDatefechaini()) < 0) {
                        varErrorres = true;
                        msgerrores += "*La fecha de inicio del proyecto no puede ser inferior a la fecha de suscripcion del convenio " + contratoDto.getDatefechaini().toString() + "<br/>";
                    }
                } else {
                    varErrorres = true;
                    msgerrores += "*La fecha de inicio del proyecto no puede estar vacia";
                }
                if (proyectoDTO.getFechaFin() != null) {
                    if (proyectoDTO.getFechaFin().compareTo(contratoDto.getDatefechafin()) > 0) {
                        varErrorres = true;
                        msgerrores += "*La fecha de fin del proyecto no puede ser superior a la fecha de finalizacion del convenio" + contratoDto.getDatefechaini().toString() + "<br/>";
                    }
                }

                if (!varErrorres) {
                    modalPry.hide();
                    crearActividadPry();
                } else {
                    AlertMessageBox d = new AlertMessageBox("Error", msgerrores);
                    d.show();
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
     * @return the nombrePry
     */
    public TextField getNombrePry() {
        return nombrePry;
    }

    private native String getTableMarkup() /*-{
     return ['<table width=100% cellpadding=0 cellspacing=10>',
     '<tr><td class=fn width=50%></td><td class=fechainicio></td></tr>',
     '<tr><td class=fechafin></td></tr>',
     '<tr><td class=tblroles colspan=2></td><td></td></tr>',
     '<tr><td class=btnaddmonto></td><tr>',
     '<tr><td class=tblobjge></td><td class=tblmetas></td></tr>',
     '<tr><td class=objetivoge></td><td class=metas></td><tr>',
     '<tr><td class=tblobjes></td><td class=tblmacro></td></tr>',
     '<tr><td class=objetivoes></td><td class=macro></td><tr>',
     '</table>'
     ].join("");
     }-*/;

    /**
     * @param nombrePry the nombrePry to set
     */
    public void setNombrePry(TextField nombrePry) {
        this.nombrePry = nombrePry;
    }

    /*metodo que se encarga de llenar el combo de entidades
     * con las entidades que tiene el convenio en las fuentes de recursos
     */
    public void llenarComboEntidadesConvenio(final ListStore<TerceroDTO> entidades) {
        int i = 0;
        for (Iterator it = contratoDto.getFuenterecursosconvenios().iterator(); it.hasNext();) {
            FuenterecursosconvenioDTO fuenteRecursosDTO = (FuenterecursosconvenioDTO) it.next();
            TerceroDTO tercero = fuenteRecursosDTO.getTercero();
            tercero.setCampoTemporalFuenteRecursos(i);
            entidades.add(tercero);
            i++;
        }
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
                service.setLog("Cargue rubros" + frc.getRolentidad().getStrnombre(), null);
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

    public void cargarDatosProyectoDTO() {
        proyectoDTO.setStrnombreobra(nombrePry.getValue());
        proyectoDTO.setFechaInicio(fechaInicio.getValue());
        proyectoDTO.setFechaFin(fechaFin.getValue());
        if (proyectoDTO.getValor() != null) {
            if (proyectoDTO.getValorDisponible() == null) {
                proyectoDTO.setValorDisponible(BigDecimal.ZERO);

            }
            proyectoDTO.setValorDisponible(proyectoDTO.getValorDisponible().add(proyectoDTO.getValor()));
        }
    }

    public void crearActividadPry() {
        ActividadobraDTO tareaNueva = new ActividadobraDTO(proyectoDTO.getStrnombreobra(), proyectoDTO.getFechaInicio(), calcularDuracion(),
                0, GanttConfig.TaskType.PARENT, 2, false, proyectoDTO);

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

    public void enlazaractividadesHijas(ActividadobraDTO actividadPadre, ActividadobraDTO actividadHija) {
        gantt.getGanttPanel().getContainer().getTreeStore().add(actividadPadre, actividadHija);
        propes.taskType().setValue(actividadPadre, GanttConfig.TaskType.PARENT);
        gantt.getGanttPanel().getContainer().getTreeStore().update(actividadPadre);
        ((TreeGrid<ActividadobraDTO>) gantt.getGanttPanel().getContainer().getLeftGrid()).setExpanded(actividadPadre, true);  //tareaSeleccionada.addChild(tareaNueva);

    }

    public void limpiarObjetivos() {
        //this.objetivoEspecifico.setText("");
        this.objetivoGeneral.setText("");
    }

    public void limpiarMacroActi() {
        this.macroActividades.setText("");
    }

    public ListBox getComboCatRubros() {
        return comboCatRubros;
    }

    public void setComboCatRubros(ListBox comboCatRubros) {
        this.comboCatRubros = comboCatRubros;
    }

    public ListBox getComboRubros() {
        return comboRubros;
    }

    public void setComboRubros(ListBox comboRubros) {
        this.comboRubros = comboRubros;
    }
}
