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
import co.com.interkont.cobra.to.Tercero;
import com.gantt.client.Gantt;
import com.gantt.client.config.GanttConfig;
import com.gantt.client.core.GanttPanel;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
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
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.box.MessageBox;
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
import com.sencha.gxt.widget.core.client.form.PropertyEditor;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.Validator;
import com.sencha.gxt.widget.core.client.form.validator.MaxLengthValidator;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.treegrid.TreeGrid;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Handler;

public class ProyectoForm1 implements IsWidget, EntryPoint {

    /*variables utilizadas para la captura de los datos desde la vista*/
    private TextField nombrePry;
    private TextField tipoRecurso;
    private TextArea objetivoGeneral;
    private TextArea objetivoEspecifico;
    private ComboBox<TerceroDTO> lstEntidadesConvenio;
    private ComboBox<RubroDTO> comboRubros;
    ListBox lstTipoAporte;
    private NumberField<BigDecimal> montoAportado;
    private TextField meta;
    private TextArea macroActividades;
    private DateField fechaInicio;
    private DateField fechaFin;
    private static final int COLUMN_FORM_WIDTH = 686;
    private static final int COLUMN_FORM_HEIGHT = 576;
    private VerticalPanel vp;
    /*Convenio principal el cual tiene las fuentes de los recursos y 
     * la informacion necesaria
     */
    ContratoDTO contratoDto;
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
    }

    public ProyectoForm1(ActividadobraDTO actividadobrapadre, Gantt<ActividadobraDTO, DependenciaDTO> gantt, Dialog di, ContratoDTO contratoDtoP) {
        this.actividadObraPadre = actividadobrapadre;
        this.gantt = gantt;
        modalPry = di;
        proyectoDTO = new ObraDTO();
        this.contratoDto = contratoDtoP;
        contratoDto.setValorDisponible(contratoDto.getNumvlrcontrato());
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
     * @return the objetivoEspecifico
     */
    public TextArea getObjetivoEspecifico() {
        return objetivoEspecifico;
    }

    /**
     * @param objetivoEspecifico the objetivoEspecifico to set
     */
    public void setObjetivoEspecifico(TextArea objetivoEspecifico) {
        this.objetivoEspecifico = objetivoEspecifico;
    }

    /**
     * @return the comboRubros
     */
    public ComboBox<RubroDTO> getComboRubros() {
        return comboRubros;
    }

    /**
     * @param comboRubros the comboRubros to set
     */
    public void setComboRubros(ComboBox<RubroDTO> comboRubros) {
        this.comboRubros = comboRubros;
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

    @Override
    public Widget asWidget() {
        if (vp == null) {
            vp = new VerticalPanel();
            vp.setSpacing(70);
            vp.setWidth("" + COLUMN_FORM_WIDTH);
            vp.setHeight("" + COLUMN_FORM_HEIGHT);
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
        con.add(new FieldLabel(nombrePry, "//INFORMACIÓN BASICA"), new HtmlData(".fn"));

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


        llenarComboEntidadesConvenio(entidades);

        lstEntidadesConvenio = new ComboBox<TerceroDTO>(entidades, propse.strnombrecompleto());
        lstEntidadesConvenio.setEmptyText("Entidad");
        lstEntidadesConvenio.setWidth(cw);
        lstEntidadesConvenio.setAllowBlank(false);
        lstEntidadesConvenio.setTypeAhead(true);
        lstEntidadesConvenio.setTriggerAction(TriggerAction.ALL);
        lstEntidadesConvenio.addSelectionHandler(new SelectionHandler<TerceroDTO>() {
            @Override
            public void onSelection(SelectionEvent<TerceroDTO> event) {
                terceroDto = event.getSelectedItem();
                fuenteRecursosConveDTO = buscarFuenteDto(terceroDto.getCampoTemporalFuenteRecursos());
                service.setLog("selec ter" + fuenteRecursosConveDTO.getTercero().getStrnombrecompleto(), null);
            }
        });
        con.add(new FieldLabel(lstEntidadesConvenio, "*ANADIR ROLES Y ENTIDADES"), new HtmlData(".entidad"));


        tipoRecurso = new TextField();
        tipoRecurso.setEmptyText("Tipo recurso");
        tipoRecurso.setAllowBlank(false);
        tipoRecurso.setWidth(cw);
        con.add(tipoRecurso, new HtmlData(".tipor"));

        objetivoGeneral = new TextArea();
        getObjetivoGeneral().setHeight("" + 100);
        getObjetivoGeneral().setWidth("" + (cw - 10));
        getObjetivoGeneral().setText("General");
        con.add(new FieldLabel(objetivoGeneral, "OBJETIVOS"), new HtmlData(".objetivog"));

        objetivoEspecifico = new TextArea();
        getObjetivoEspecifico().setHeight("" + 100);
        getObjetivoEspecifico().setWidth("" + (cw - 10));
        getObjetivoEspecifico().setText("Especifico");
        con.add(getObjetivoEspecifico(), new HtmlData(".objetivoes"));


        llenarListaRubros(rubros);

        setComboRubros(new ComboBox<RubroDTO>(rubros, props.strdescripcion()));
        getComboRubros().setEmptyText("Seleccione Rubro");
        getComboRubros().setWidth(cw);
        getComboRubros().setTypeAhead(true);
        getComboRubros().setTriggerAction(TriggerAction.ALL);
        getComboRubros().setAllowBlank(false);
        comboRubros.addSelectionHandler(new SelectionHandler<RubroDTO>() {
            @Override
            public void onSelection(SelectionEvent<RubroDTO> event) {
                rubroDto = event.getSelectedItem();

            }
        });
        con.add(getComboRubros(), new HtmlData(".rubro"));


        setMontoAportado((NumberField<BigDecimal>) new NumberField(new BigDecimalPropertyEditor()));
        getMontoAportado().addParseErrorHandler(new ParseErrorHandler() {
            @Override
            public void onParseError(ParseErrorEvent event) {
                Info.display("Error", "Ingrese un valor valido");
            }
        });
        getMontoAportado().setAllowBlank(false);
        getMontoAportado().setEmptyText("Monto aportado");
        getMontoAportado().setWidth(cw);
        con.add(getMontoAportado(), new HtmlData(".monto"));

        setMeta(new TextField());
        getMeta().setWidth(cw);
        getMeta().setAllowBlank(false);
        con.add(new FieldLabel(getMeta(), "*META O PRODUCTO ESPERADO"), new HtmlData(".meta"));

        setMacroActividades(new TextArea());
        getMacroActividades().setHeight("" + 90);
        getMacroActividades().setWidth("" + cw);
        con.add(new FieldLabel(getMacroActividades(), "*MACROACTIVIDADES"), new HtmlData(".macro"));


        PushButton btnAdicionarObjetivos = new PushButton(new Image(ExampleImages.INSTANCE.addbtnaddpry()), new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (!objetivoGeneral.getText().equals("General")) {
                    ObjetivosDTO objetivosGeneral = new ObjetivosDTO(objetivoGeneral.getText(), 1, true);
                    proyectoDTO.getObjetivoses().add(objetivosGeneral);
                }
                if (!objetivoEspecifico.getText().equals("Especifico")) {
                    ObjetivosDTO objetivosEspecifico = new ObjetivosDTO(objetivoGeneral.getText(), 2, true);
                    proyectoDTO.getObjetivoses().add(objetivosEspecifico);
                }
                limpiarObjetivos();
            }
        });
        btnAdicionarObjetivos.setWidth("" + 20);
        con.add(btnAdicionarObjetivos, new HtmlData(".addobj"));


        PushButton btnAdicionarMonto = new PushButton(new Image(ExampleImages.INSTANCE.addbtnaddpry()), new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                ObrafuenterecursosconveniosDTO obraFuenteDto = new ObrafuenterecursosconveniosDTO(1, montoAportado.getValue(), fuenteRecursosConveDTO, rubroDto);
                String validacionDevuelta = validarMontosAportados(obraFuenteDto);
                if (!validacionDevuelta.equals("El monto ha sido guardado")) {
                    AlertMessageBox d = new AlertMessageBox("Error", validacionDevuelta);
                    d.show();
                } else {
                    MessageBox msg = new MessageBox("Confirmación", validacionDevuelta);
                    msg.setModal(true);
                    msg.show();
                }
                limpiarMontos();
            }
        });
        btnAdicionarMonto.setWidth("" + 20);
        con.add(btnAdicionarMonto, new HtmlData(".btnaddmonto"));


        PushButton btnAdicionarMeta = new PushButton(new Image(ExampleImages.INSTANCE.addbtnaddpry()), new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                ObjetivosDTO metaDTO = new ObjetivosDTO(meta.getText(), false);
                proyectoDTO.getObjetivoses().add(metaDTO);
                meta.setText("");
            }
        });
        btnAdicionarMeta.setWidth("" + 20);
        con.add(btnAdicionarMeta, new HtmlData(".btnaddmeta"));


        PushButton btnAdicionarMacro = new PushButton(new Image(ExampleImages.INSTANCE.addbtnaddpry()), new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                ActividadobraDTO actividadobradto = new ActividadobraDTO(macroActividades.getText(), 7);


                proyectoDTO.getActividadobras().add(actividadobradto);
                limpiarMacroActi();
            }
        });
        btnAdicionarMacro.setWidth("" + 20);
        con.add(btnAdicionarMacro, new HtmlData(".btnaddmacro"));


        Button btnAdicionarPry = new Button("Añadir Proyecto", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                /*Se carga el proyectoDTO */
                cargarDatosProyectoDTO();
                boolean varErrorres = false;
                String msgerrores = "";
                if (proyectoDTO.getObrafuenterecursosconvenioses().isEmpty()) {
                    varErrorres = true;
                    msgerrores += "El proyecto debe de tener por lo menos un monto asociado \n";
                }
                if (proyectoDTO.getFechaInicio() != null) {
                    if (proyectoDTO.getFechaInicio().compareTo(contratoDto.getDatefechaini()) < 0) {
                        varErrorres = true;
                        msgerrores += "*La fecha de inicio del proyecto no puede ser inferior a la fecha de suscripcion del convenio " + contratoDto.getDatefechaini().toString() + "\n";
                    }
                } else {
                    varErrorres = true;
                    msgerrores += "*La fecha de inicio del proyecto no puede estar vacia";
                }
                if (proyectoDTO.getFechaFin() != null) {
                    if (proyectoDTO.getFechaFin().compareTo(contratoDto.getDatefechafin()) > 0) {
                        varErrorres = true;
                        msgerrores += "*La fecha de fin del proyecto no puede ser superior a la fecha de finalizacion del convenio" + contratoDto.getDatefechaini().toString() + "\n";
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
     '<tr><td class=fn width=50%></td><td width=50% class=entidad></td></tr>',
     '<tr><td class=lblobjetivos width=50%</td><td width=50% class=tipor></td></tr>',
     '<tr><td class=objetivog></td><td><table cellpadding=0><tr><td class=rubro></td></tr><tr height=10></tr><tr><td class=monto></td><td class=btnaddmonto></td></tr><tr height=5></tr><tr><td class=meta></td><td class=btnaddmeta></td></tr></table></td><tr>',
     '<tr><td><table cellpadding=0><tr><td class=objetivoes></td><td class=addobj></td></tr></table></td><td><table cellpadding=0 cellspacing=0><tr><td class=macro></td><td class=btnaddmacro></td></tr></table></td>',
     '<tr><td class=fechainicio></td><td class=fechafin></td></tr>',
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
     * metodo que se encarga de llenar el combo rubros 
     */
    public void llenarListaRubros(final ListStore<RubroDTO> rubros) {
        service.obtenerRubros(new AsyncCallback<List>() {
            @Override
            public void onFailure(Throwable caught) {
                service.setLog("Error al cargar los rubros", null);
            }

            @Override
            public void onSuccess(List result) {
                rubros.addAll(result);
                service.setLog("Cargue rubros" + rubros.size(), null);

            }
        });

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
    }

    public void crearActividadPry() {
        ActividadobraDTO tareaNueva = new ActividadobraDTO(proyectoDTO.getStrnombreobra(), proyectoDTO.getFechaInicio(), calcularDuracion(),
                0, GanttConfig.TaskType.PARENT, 2, false, proyectoDTO);

        /*Se cargan el Panel del Gantt con la actividad Creada*/
        gantt.getGanttPanel().getContainer().getTreeStore().add(actividadObraPadre, tareaNueva);
        propes.taskType().setValue(actividadObraPadre, GanttConfig.TaskType.PARENT);
        gantt.getGanttPanel().getContainer().getTreeStore().update(actividadObraPadre);
        ((TreeGrid<ActividadobraDTO>) gantt.getGanttPanel().getContainer().getLeftGrid()).setExpanded(actividadObraPadre, true);  //tareaSeleccionada.addChild(tareaNueva);

    }

    public void limpiarObjetivos() {
        this.objetivoEspecifico.setText("");
        this.objetivoGeneral.setText("");
    }

    public void limpiarMontos() {
        entidades.clear();
        llenarComboEntidadesConvenio(entidades);
        llenarListaRubros(rubros);
        this.tipoRecurso.setText("");
        this.montoAportado.setText("");
    }

    public void limpiarMacroActi() {
        this.macroActividades.setText("");
    }

    public String validarMontosAportados(ObrafuenterecursosconveniosDTO obraFuenteDto) {
        if (obraFuenteDto.getValor().compareTo(contratoDto.getValorDisponible()) < 0) {
            if (obraFuenteDto.getValor().compareTo(obraFuenteDto.getFuenterecursosconvenio().getValorcuotagerencia()) > 0) {
                return "El monto ingresado supera el valor de la fuente de recursos";
            } else {
                if (!proyectoDTO.getObrafuenterecursosconvenioses().isEmpty()) {
                    BigDecimal sumaValorAportado = BigDecimal.ZERO;
                    for (Object obr : proyectoDTO.getObrafuenterecursosconvenioses()) {
                        ObrafuenterecursosconveniosDTO obrc = (ObrafuenterecursosconveniosDTO) obr;
                        sumaValorAportado = sumaValorAportado.add(obrc.getValor());
                    }
                    sumaValorAportado = sumaValorAportado.add(obraFuenteDto.getValor());
                    if (sumaValorAportado.compareTo(contratoDto.getNumvlrcontrato()) > 0) {
                        return "Monto no registrado, la suma de los montos aportados supera el valor del convenio";
                    }
                }
            }
            proyectoDTO.getObrafuenterecursosconvenioses().add(obraFuenteDto);
//            contratoDto.setValorDisponible(contratoDto.getValorDisponible().subtract(obraFuenteDto.getValor()));
//            proyectoDTO.setValor(proyectoDTO.getValor().add(obraFuenteDto.getValor()));
            return "El monto ha sido guardado";
        }
        return "El convenio seleccionado no cuenta con valor disponible";
        }

    
}