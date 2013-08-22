/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client;

import co.com.interkont.cobra.planoperativo.client.dto.ActividadobraDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ActividadobraDTOProps;
import co.com.interkont.cobra.planoperativo.client.dto.ContratoDTO;
import co.com.interkont.cobra.planoperativo.client.dto.DependenciaDTO;
import co.com.interkont.cobra.planoperativo.client.dto.MontoDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ObrafuenterecursosconveniosDTO;
import co.com.interkont.cobra.planoperativo.client.dto.RelacionobrafuenterecursoscontratoDTO;
import co.com.interkont.cobra.planoperativo.client.dto.RubroDTO;
import co.com.interkont.cobra.planoperativo.client.dto.TerceroDTO;
import co.com.interkont.cobra.planoperativo.client.dto.TipocontratoDTO;
import co.com.interkont.cobra.planoperativo.client.resources.images.ExampleImages;
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAble;
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAbleAsync;
import com.gantt.client.Gantt;
import com.gantt.client.config.GanttConfig;
import com.gantt.client.config.GanttConfig.TaskType;
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
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.container.AbstractHtmlLayoutContainer.HtmlData;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.event.BlurEvent;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.NumberField;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.treegrid.TreeGrid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ContratoForm implements IsWidget, EntryPoint {

    // <editor-fold defaultstate="collapsed" desc="Elementos visuales">
    private VerticalPanel vp;
    private TextArea objetoContrato;
    private NumberField<BigDecimal> valorContrato;
    private NumberField<BigDecimal> valorRubros;
    private NumberField<BigDecimal> valorFuenteRecurso;
    private TextField nombreAbre;
    private DateField fechaSuscripcionContrato;
    private DateField fechaSuscripcionActaInicio;
    private ListBox comboCatRubros = new ListBox();
    //private ComboBox<RubroDTO> comboCatRubros;
    private ListBox comboRubros = new ListBox();
    private ComboBox<TerceroDTO> lstTerceros;
    private ComboBox<Integer> lstVigencia;
    private ComboBox<TipocontratoDTO> lstTipoContrato;
    //ComboBox<EnumFormaPago> lstFormaPago;
    private NumberField<BigDecimal> valorFuente;
    private NumberField porcentajeFuente;
    private ListBox lstVigen;
    private ListBox lstFormaP;
    private static final int COLUMN_FORM_WIDTH = 686;
//    private static final int COLUMN_FORM_HEIGHT = 576;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Variables  para el cargue de los informacion en los combobox">
    EntidadProperties propse = GWT.create(EntidadProperties.class);
    protected final ListStore<TerceroDTO> entidades = new ListStore<TerceroDTO>(propse.intcodigo());
    RubroProperties props = GWT.create(RubroProperties.class);
    protected final ListStore<RubroDTO> rubros = new ListStore<RubroDTO>(props.idrubro());
    VigenciaProperties propsv = GWT.create(VigenciaProperties.class);
    protected final ListStore<Integer> vigencias = new ListStore<Integer>(propsv.vigencia());
    //FormaPagoProperties propsforma = GWT.create(FormaPagoProperties.class);
    //  protected final ListStore<EnumFormaPago> formasPago = new ListStore<EnumFormaPago>(propsforma.numFormaPago());
    TipoContratoProperties propstipoContrato = GWT.create(TipoContratoProperties.class);
    protected final ListStore<TipocontratoDTO> tiposContrato = new ListStore<TipocontratoDTO>(propstipoContrato.inttipocontrato());
    // </editor-fold>
    protected CobraGwtServiceAbleAsync service = GWT.create(CobraGwtServiceAble.class);
    protected GwtMensajes msj = GWT.create(GwtMensajes.class);
    protected ExampleImages imgs = GWT.create(ExampleImages.class);
    String msgValidacion = "";
    /*Convenio padre**/
    ContratoDTO convenioDto;
    /*ActividadObra seleccionada*/
    ActividadobraDTO actividadObraPadre;
    /*gantt que contiene todas las actividades*/
    protected Gantt<ActividadobraDTO, DependenciaDTO> gantt;
    /*modal que contiene este formulario*/
    protected Dialog modalContrato;
    int vigencia;
    TerceroDTO terceroDto;
    ObrafuenterecursosconveniosDTO obraFrDto;
    ContratoDTO contrato;
    //si forma de pago es uno es valor si es dos es porcentaje
    int formaPago;
    RubroDTO rubro;
    TipocontratoDTO tipocontrato;
    ActividadobraDTOProps propes;
    List<RubroDTO> lstRubrosDto;
    BigDecimal valorAuxiliar;
    ActividadobraDTO actividadObraEditar;

    public ContratoForm() {
    }

    public ContratoForm(ActividadobraDTO actividadobrapadre, Gantt<ActividadobraDTO, DependenciaDTO> gantt, Dialog di, ActividadobraDTOProps propes) {
        this.actividadObraPadre = actividadobrapadre;
        this.gantt = gantt;
        modalContrato = di;
        contrato = new ContratoDTO();
        this.propes = propes;
        service.setLog("2", null);
        lstRubrosDto = new ArrayList<RubroDTO>();

    }

    public ContratoForm(ActividadobraDTO actividadobraContratoEditar, Gantt<ActividadobraDTO, DependenciaDTO> gantt, Dialog di) {
        this.actividadObraEditar = actividadobraContratoEditar;
        this.gantt = gantt;
        modalContrato = di;
        this.contrato = actividadobraContratoEditar.getContrato();
    }

    @Override
    public Widget asWidget() {
        if (getVp() == null) {
            setVp(new VerticalPanel());
            getVp().setSpacing(70);
            getVp().setWidth("" + COLUMN_FORM_WIDTH);
            getVp().setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
            getVp().setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
            crearFormulario();
        }
        return getVp();
    }

    @Override
    public void onModuleLoad() {

        RootPanel.get().add(asWidget());

    }

    private void crearFormulario() {
        getVp().add(new Label("Añadir contrato"));

        HtmlLayoutContainer con = new HtmlLayoutContainer(getTableMarkup());
        getVp().add(con);

        int cw = 238;

        Label tObj = new Label("*OBJETIVOS");
        con.add(tObj, new HtmlData(".tobj"));

        Label tRubros = new Label("*RUBROS");
        con.add(tRubros, new HtmlData(".trubros"));

        Label tFuentes = new Label("*FUENTES DE RECURSOS");
        con.add(tFuentes, new HtmlData(".tfuente"));

        setObjetoContrato(new TextArea());
        getObjetoContrato().setWidth("" + cw);
        getObjetoContrato().setHeight("" + 80);
        getObjetoContrato().setText("Objeto");
        con.add(getObjetoContrato(), new HtmlData(".objetoC"));

        setValorContrato((NumberField<BigDecimal>) new NumberField(new NumberPropertyEditor.BigDecimalPropertyEditor()));
        getValorContrato().setWidth(cw);
        getValorContrato().setEmptyText("Valor estimado");
        getValorContrato().setWidth(cw);

        getValorContrato().addBlurHandler(new BlurEvent.BlurHandler() {
            @Override
            public void onBlur(BlurEvent event) {
                if (sumarRubros().compareTo(getValorContrato().getValue()) >= 0) {
                    AlertMessageBox d = new AlertMessageBox("Error", "La suma de los rubros supera el valor del contrato modificado");
                    d.show();
                    getValorContrato().setValue(valorAuxiliar);

                }
            }
        });
        con.add(getValorContrato(), new HtmlData(".valor"));



        llenarListaTipoContrato(tiposContrato);
        setLstTipoContrato(new ComboBox<TipocontratoDTO>(tiposContrato, propstipoContrato.strdesctipocontrato()));
        getLstTipoContrato().setEmptyText("Tipo contratacion");
        getLstTipoContrato().setWidth(cw);
        getLstTipoContrato().setTypeAhead(true);
        getLstTipoContrato().setTriggerAction(TriggerAction.ALL);
        getLstTipoContrato().addSelectionHandler(new SelectionHandler<TipocontratoDTO>() {
            @Override
            public void onSelection(SelectionEvent<TipocontratoDTO> event) {
                tipocontrato = event.getSelectedItem();
            }
        });
        con.add(getLstTipoContrato(), new HtmlData(".tipocontrato"));

        setNombreAbre(new TextField());
        getNombreAbre().setEmptyText("Nombre Abreviado");
        getNombreAbre().setWidth(cw);
        con.add(getNombreAbre(), new HtmlData(".nomabreviado"));


        setFechaSuscripcionContrato(new DateField());
        getFechaSuscripcionContrato().setWidth(cw);
        getFechaSuscripcionContrato().setEmptyText("Fecha de suscripcion");
        con.add(getFechaSuscripcionContrato(), new HtmlData(".fechasuscont"));

        setFechaSuscripcionActaInicio(new DateField());
        getFechaSuscripcionActaInicio().setWidth(cw);
        getFechaSuscripcionActaInicio().setEmptyText("Fecha de suscripcion acta inicio");
        con.add(getFechaSuscripcionActaInicio(), new HtmlData(".fechasusacta"));

        setLstVigen(new ListBox(false));
        getLstVigen().setWidth("" + cw);
        llenarV();
        getLstVigen().addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                vigencia = Integer.parseInt(getLstVigen().getItemText(getLstVigen().getSelectedIndex()));
            }
        });
        con.add(getLstVigen(), new HtmlData(".vigencia"));



        getComboCatRubros().setWidth("" + cw);
        getComboRubros().setWidth("" + cw);
        con.add(getComboCatRubros(), new HtmlData(".rubrocont"));
        con.add(getComboRubros(), new HtmlData(".rubrosub"));
        this.llenarCategorias();
        getComboCatRubros().setSelectedIndex(0);
        getComboCatRubros().addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                llenarRubroslista(getComboCatRubros().getValue(getComboCatRubros().getSelectedIndex()));
            }
        });

        getComboRubros().addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                rubro = lstRubrosDto.get(getComboRubros().getSelectedIndex());
            }
        });

        llenarRubroslista("123102");


        setValorRubros((NumberField<BigDecimal>) new NumberField(new NumberPropertyEditor.BigDecimalPropertyEditor()));
        getValorRubros().setEmptyText("Valor");
        getValorRubros().setWidth(cw);
        con.add(getValorRubros(), new HtmlData(".valorubro"));

        PushButton btnAdicionarRubros = new PushButton(new Image(ExampleImages.INSTANCE.addbtnaddpry()), new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                MontoDTO monto = new MontoDTO(rubro, getValorRubros().getValue(), vigencia);
                String msgVal = validaRubros(monto);
                if (msgVal.equals("El rubro se registró correctamente")) {
                    contrato.getMontos().add(monto);
                    service.setLog("" + contrato.getMontos().size(), null);
                    limpiarMontos();
                    MessageBox msg = new MessageBox("Confirmación", msgVal);
                    msg.setModal(true);
                    msg.show();

                } else {
                    AlertMessageBox d = new AlertMessageBox("Error", msgVal);
                    d.show();
                }

            }
        });
        con.add(btnAdicionarRubros, new HtmlData(".addr"));

//        PushButton btnVerRubros = new PushButton(new Image(ExampleImages.INSTANCE.addbtnVerMas()), new ClickHandler() {
//            @Override
//            public void onClick(ClickEvent event) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//            }
//        });
//        con.add(btnVerRubros, new HtmlData(".verr"));

        llenarFuenteRecursosContrato(entidades);


        setLstTerceros(new ComboBox<TerceroDTO>(entidades, propse.strnombrecompleto()));
        getLstTerceros().setEmptyText("Entidad");
        getLstTerceros().setWidth(cw);
        getLstTerceros().setTypeAhead(true);
        getLstTerceros().setTriggerAction(TriggerAction.ALL);
        getLstTerceros().addSelectionHandler(new SelectionHandler<TerceroDTO>() {
            @Override
            public void onSelection(SelectionEvent<TerceroDTO> event) {
                terceroDto = event.getSelectedItem();
                obraFrDto = buscarFuenteDto(terceroDto.getCampoTemporalFuenteRecursos());

            }
        });
        con.add(getLstTerceros(), new HtmlData(".entidad"));

        setLstFormaP(new ListBox(false));
        getLstFormaP().setWidth("" + cw);
        llenarFormaPa();
        getLstFormaP().addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                formaPago = getLstFormaP().getSelectedIndex();
                service.setLog("" + formaPago, null);
                if (formaPago == 1) {
                    getValorFuenteRecurso().setEmptyText("Porcentaje");
                } else if (formaPago == 0) {
                    getValorFuenteRecurso().setEmptyText("Valor");
                }
            }
        });
        con.add(getLstFormaP(), new HtmlData(".formapago"));

        setValorFuenteRecurso((NumberField<BigDecimal>) new NumberField(new NumberPropertyEditor.BigDecimalPropertyEditor()));
        getValorFuenteRecurso().setEmptyText("Valor");
        getValorFuenteRecurso().setWidth(cw);
        getValorFuenteRecurso().addBlurHandler(new BlurEvent.BlurHandler() {
            @Override
            public void onBlur(BlurEvent event) {
                if (formaPago == 1) {
                    if (getValorFuenteRecurso().getValue().compareTo(new BigDecimal("100")) > 0) {
                        getValorFuenteRecurso().setValue(BigDecimal.ZERO);
                        AlertMessageBox d = new AlertMessageBox("Error", "El porcentaje ingresado no puede superar el 100%");
                        d.show();
                    }
                }
            }
        });
        con.add(getValorFuenteRecurso(), new HtmlData(".valorfuente"));


        PushButton btnAdicionarFuentes;
        btnAdicionarFuentes = new PushButton(new Image(ExampleImages.INSTANCE.addbtnaddpry()), new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                RelacionobrafuenterecursoscontratoDTO rofr;
                if (formaPago == 1) {
                    BigDecimal valorP = obraFrDto.getValor().multiply(getValorFuenteRecurso().getValue()).divide(new BigDecimal(100));
                    service.setLog("yu" + valorP, null);
                    rofr = new RelacionobrafuenterecursoscontratoDTO(obraFrDto, valorP, formaPago);
                } else {
                    rofr = new RelacionobrafuenterecursoscontratoDTO(obraFrDto, getValorFuenteRecurso().getValue(), formaPago);
                }
                String validacionDevuelta = validarFuenteRecurso(rofr);
                service.setLog(msgValidacion, null);
                if (!validacionDevuelta.equals("La fuente ha sido guardada")) {
                    AlertMessageBox d = new AlertMessageBox("Error", validacionDevuelta);
                    d.show();
                } else {
                    MessageBox msg = new MessageBox("Confirmación", validacionDevuelta);
                    msg.setModal(true);
                    msg.show();
                    limpiarFuentes();
                }


                //contrato.getRelacionobrafuenterecursoscontratos().add(rofr);
                service.setLog("dsdfsdf" + contrato.getRelacionobrafuenterecursoscontratos().size(), null);
            }
        });
        con.add(btnAdicionarFuentes, new HtmlData(".addf"));
//
//        PushButton btnVerFuente = new PushButton(new Image(ExampleImages.INSTANCE.addbtnVerMas()), new ClickHandler() {
//            @Override
//            public void onClick(ClickEvent event) {
//            }
//        });
//        con.add(btnVerFuente, new HtmlData(".verf"));
//
//
        Button btnAdicionarContrato = new Button("Añadir Contrato", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                CrearContrato();
                if (validaciones()) {
                    AlertMessageBox d = new AlertMessageBox("Error", msgValidacion);
                    d.show();
                } else {
                    modalContrato.hide();
                    crearTareaContrato();
                }
            }
        });

        btnAdicionarContrato.setWidth("" + 150);
        con.add(btnAdicionarContrato);

    }

    private native String getTableMarkup() /*-{
     return [ '<table width=100% cellpadding=0 cellspacing=10>',
     '<tr><td class=tobj></td></tr>',
     '<tr><td class=objetoC></td><td><table cellpadding=0><tr><td class=valor></td></tr><tr height=10><tr><td class=tipocontrato></td></tr></table></td></tr>',
     '<tr><td class=nomabreviado></td><td class=fechasuscont></td>',
     '<tr><td></td><td class=fechasusacta></td></tr>',
     '<tr><td class=trubros></td></tr>',
     '<tr><td class=rubrocont></td><td class=rubrosub></td></tr>',
     '<tr><td class=valorubro></td><td><table><tr><td class=vigencia></td><td class=addr></td><td class=verr></td></tr></table></td></tr>',
     '<tr><td class=tfuente></td></tr>',
     '<tr><td class=entidad></td><td class=formapago></td></tr>',
     '<tr><td><table><tr><td class=valorfuente></td><td class=addf></td><td class=verf></td></tr></table></td></tr>',  
     '</table>'
     ].join("");
     }-*/;

    // <editor-fold defaultstate="collapsed" desc="Metodos encargados de llenar los combobox">
    /*
     * metodo que se encarga de llenar el combo rubros 
     */
    public void llenarListaRubros(final ListStore<RubroDTO> rubros) {
        service.obtenerRubros("12", new AsyncCallback<List<RubroDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                service.setLog("Error al cargar los rubros", null);
            }

            @Override
            public void onSuccess(List<RubroDTO> result) {
                rubros.addAll(result);
                service.setLog("Cargue rubros" + rubros.size(), null);

            }
        });

    }

    public void llenarFuenteRecursosContrato(final ListStore<TerceroDTO> entidades) {
        int i = 0;
        for (Iterator it = actividadObraPadre.getObra().getObrafuenterecursosconvenioses().iterator(); it.hasNext();) {
            ObrafuenterecursosconveniosDTO obraFuenteRecurso = (ObrafuenterecursosconveniosDTO) it.next();
            TerceroDTO tercero = obraFuenteRecurso.getFuenterecursosconvenio().getTercero();
            tercero.setCampoTemporalFuenteRecursos(i);
            entidades.add(tercero);
            i++;
        }
    }

    public void llenarV() {
        Date ahora = new Date();
        service.setLog("AHORA" + ahora.getYear(), null);
//        int año = Calendar.getInstance().getTime().getYear();
        int año = 2013;

        getLstVigen().addItem("" + año);
        for (int i = 0; i < 14; i++) {
            año = año + 1;
            getLstVigen().addItem("" + año);
        }
    }

    public void llenarFormaPa() {
        getLstFormaP().addItem("Valor", "1");
        getLstFormaP().addItem("Porcentaje", "2");
    }

    /*
     * metodo que se encarga de llenar el combo rubros 
     */
    public void llenarListaTipoContrato(final ListStore<TipocontratoDTO> tiposContrato) {
        service.obtenerTiposContrato(new AsyncCallback<List>() {
            @Override
            public void onFailure(Throwable caught) {
                service.setLog("Error al cargar los tipos de contrato", null);
            }

            @Override
            public void onSuccess(List result) {
                tiposContrato.addAll(result);
                service.setLog("Cargue tipos Contrato" + tiposContrato.size(), null);

            }
        });

    }

    public ObrafuenterecursosconveniosDTO buscarFuenteDto(int posicion) {
        int i = 0;
        for (Iterator it = actividadObraPadre.getObra().getObrafuenterecursosconvenioses().iterator(); it.hasNext();) {
            if (posicion == i) {
                ObrafuenterecursosconveniosDTO frc = (ObrafuenterecursosconveniosDTO) it.next();
                service.setLog("Cargue pos" + frc.getFuenterecursosconvenio().getTercero().getStrnombrecompleto(), null);
                return frc;
            }
            i++;
        }
        return null;
    }

    public void CrearContrato() {
        contrato.setTextobjeto(getObjetoContrato().getText());
        contrato.setNombreAbreviado(getNombreAbre().getValue());
        contrato.setDatefechaini(getFechaSuscripcionContrato().getValue());
        contrato.setDatefechaactaini(getFechaSuscripcionActaInicio().getValue());
        contrato.setTipocontrato(tipocontrato);
        contrato.setNumvlrcontrato(getValorContrato().getValue());
        contrato.setValorDisponible(getValorContrato().getValue());
    }

    public void crearTareaContrato() {
        actividadObraPadre.getObra().setValorDisponible(actividadObraPadre.getObra().getValorDisponible().subtract(contrato.getNumvlrcontrato()));
        ActividadobraDTO actividadObraContrato = new ActividadobraDTO(contrato.getNombreAbreviado(), contrato.getDatefechaini(), actividadObraPadre.getDuration(), 0, TaskType.PARENT, 3, false, contrato);

        List<ActividadobraDTO> lstHijos = new ArrayList<ActividadobraDTO>();
        ActividadobraDTO hitoFechaSuscripcion = new ActividadobraDTO("Suscripcion del contrato", fechaSuscripcionContrato.getValue(), actividadObraPadre.getDuration(), 0, TaskType.MILESTONE, 6, true);
        lstHijos.add(hitoFechaSuscripcion);//el pade de esta es actividadObraContrato
        ActividadobraDTO hitoFechaSuscripcionActa = new ActividadobraDTO("Suscripcion acta de inicio", fechaSuscripcionActaInicio.getValue(), actividadObraPadre.getDuration(), 0, TaskType.MILESTONE, 6, true);
        lstHijos.add(hitoFechaSuscripcionActa);

        ActividadobraDTO precontractual = new ActividadobraDTO("Precontractual", contrato.getDatefechaini(),1, 0, TaskType.PARENT, 5, true);
        lstHijos.add(precontractual);

        List<ActividadobraDTO> lstHijosPrecontra = new ArrayList<ActividadobraDTO>();
        ActividadobraDTO revTecnica = new ActividadobraDTO("Revisión técnica de documentos", contrato.getDatefechaini(), 1, 0, TaskType.PARENT, 4, true);
        lstHijosPrecontra.add(revTecnica);
        ActividadobraDTO elaboPliegos = new ActividadobraDTO("Elaboración de pliegos de condiciones", contrato.getDatefechaini(), 1, 0, TaskType.LEAF, 4, true);
        lstHijosPrecontra.add(elaboPliegos);
        ActividadobraDTO evaPropuestas = new ActividadobraDTO("Evaluación de propuestas", contrato.getDatefechaini(), 1, 0, TaskType.LEAF, 4, true);
        lstHijosPrecontra.add(evaPropuestas);
        ActividadobraDTO elaContrato = new ActividadobraDTO("Elaboración de contratos", contrato.getDatefechaini(), 1, 0, TaskType.LEAF, 4, true);
        lstHijosPrecontra.add(elaContrato);

        ActividadobraDTO contractua = new ActividadobraDTO("Contractual", contrato.getDatefechaini(), 1, 0, TaskType.PARENT, 5, true);
        lstHijos.add(contractua);

        ActividadobraDTO Liquidaciones = new ActividadobraDTO("Liquidaciones", contrato.getDatefechaini(), 1, 0, TaskType.PARENT, 5, true);
        lstHijos.add(Liquidaciones);



        /*Se cargan el Panel del Gantt con la actividad Creada*/
        gantt.getGanttPanel().getContainer().getTreeStore().add(actividadObraPadre, actividadObraContrato);
        propes.taskType().setValue(actividadObraPadre, GanttConfig.TaskType.PARENT);
        gantt.getGanttPanel().getContainer().getTreeStore().update(actividadObraPadre);
        ((TreeGrid<ActividadobraDTO>) gantt.getGanttPanel().getContainer().getLeftGrid()).setExpanded(actividadObraPadre, true);  //tareaSeleccionada.addChild(tareaNueva);

        gantt.getGanttPanel().getContainer().getTreeStore().add(actividadObraContrato, lstHijos);
        propes.taskType().setValue(actividadObraContrato, GanttConfig.TaskType.PARENT);
        gantt.getGanttPanel().getContainer().getTreeStore().update(actividadObraContrato);
        ((TreeGrid<ActividadobraDTO>) gantt.getGanttPanel().getContainer().getLeftGrid()).setExpanded(actividadObraContrato, true);  //tareaSeleccionada.addChild(tareaNueva);

        gantt.getGanttPanel().getContainer().getTreeStore().add(precontractual, lstHijosPrecontra);
        propes.taskType().setValue(precontractual, GanttConfig.TaskType.PARENT);
        gantt.getGanttPanel().getContainer().getTreeStore().update(precontractual);
        ((TreeGrid<ActividadobraDTO>) gantt.getGanttPanel().getContainer().getLeftGrid()).setExpanded(precontractual, true);  //tareaSeleccionada.addChild(tareaNueva);



    }

    public boolean validaciones() {
        boolean hayError = false;
        msgValidacion = new String();
        if (contrato.getDatefechaini() == null) {
            hayError = true;
            msgValidacion += "*por favor ingrese la fecha de suscripcion" + "<br/>";
        } else {

            if (contrato.getDatefechaini().compareTo(actividadObraPadre.getObra().getFechaInicio()) < 0) {
                hayError = true;
                msgValidacion += "*La fecha de inicio no puede ser inferior a la fecha de suscripcion del proyecto" + "<br/>";
            }
        }
        if (contrato.getDatefechaactaini() == null) {
            hayError = true;
            msgValidacion += "*por favor ingrese la fecha de suscripcion del acta de inicio" + "<br/>";
        } else {

            if (contrato.getDatefechaactaini().compareTo(actividadObraPadre.getObra().getFechaInicio()) < 0) {
                hayError = true;
                msgValidacion += "*La fecha de suscripcion del acta no puede ser inferior a la fecha de inicio del proyecto" + "<br/>";
            }
        }
        if (contrato.getTextobjeto().equals("Objeto")) {
            hayError = true;
            msgValidacion += "*por favor ingrese el objeto del contrato" + "<br/>";
        }
        service.setLog(contrato.getNombreAbreviado(),null);
        if (contrato.getNombreAbreviado().equals("")) {
            hayError = true;
            msgValidacion += "*por favor ingrese nombre abreviado del contrato" + "<br/>";
        }

        if (contrato.getTipocontrato() == null) {
            hayError = true;
            msgValidacion += "*por favor seleccione un tipo de contrato" + "<br/>";
        }

        if (contrato.getMontos().isEmpty()) {
            hayError = true;
            msgValidacion += "*El contrato debe de tener por lo menos un monto asociado" + "<br/>";
        }
        if (contrato.getRelacionobrafuenterecursoscontratos().isEmpty()) {
            hayError = true;
            msgValidacion += "*El contrato debe de tener por lo menos una fuente de recursos asociado" + "<br/>";
        }
        if (contrato.getNumvlrcontrato() != null) {
            if (contrato.getNumvlrcontrato().compareTo(sumarRubros()) != 0) {
                hayError = true;
                msgValidacion += "*La suma de los rubros asociados debe ser igual al valor del contrato" + "<br/>";
            }
        }
        return hayError;

    }

    public String validarFuenteRecurso(RelacionobrafuenterecursoscontratoDTO relacionFuente) {
        service.setLog("entre 1" + actividadObraPadre.getObra().getValorDisponible(), null);
        if (relacionFuente.getValor().compareTo(relacionFuente.getObrafuenterecursosconvenios().getValorDisponible()) >= 0) {
            return "El valor ingresado supera el valor disponible de la fuente de recursos que aporta esta entidad" + "<br/>";
        } else {
            service.setLog("entre 2", null);
            if (!contrato.getRelacionobrafuenterecursoscontratos().isEmpty()) {
                BigDecimal sumaValorAportado = BigDecimal.ZERO;
                for (Object obr : contrato.getRelacionobrafuenterecursoscontratos()) {
                    RelacionobrafuenterecursoscontratoDTO obrc = (RelacionobrafuenterecursoscontratoDTO) obr;
                    sumaValorAportado = sumaValorAportado.add(obrc.getValor());
                }
                sumaValorAportado = sumaValorAportado.add(relacionFuente.getValor());
                if (sumaValorAportado.compareTo(actividadObraPadre.getObra().getValor()) > 0) {
                    return "Valor no registrado, la suma de las fuentes de recursos supera  el valor del proyecto" + "<br/>";
                }
            }
            service.setLog("entre 3", null);
            contrato.getRelacionobrafuenterecursoscontratos().add(relacionFuente);
            modificarValorDisponible(relacionFuente);
            return "La fuente ha sido guardada";
        }

    }

    public void modificarValorDisponible(RelacionobrafuenterecursoscontratoDTO relacionFuente) {
        service.setLog("entre 4", null);
//        actividadObraPadre.getObra().setValorDisponible(actividadObraPadre.getObra().getValorDisponible().subtract(relacionFuente.getValor()));
        relacionFuente.getObrafuenterecursosconvenios().setValorDisponible(relacionFuente.getObrafuenterecursosconvenios().getValorDisponible().subtract(relacionFuente.getValor()));
        service.setLog("entre 5", null);
    }

    private void llenarCategorias() {
        service.obtenerCategoriasRubros(new AsyncCallback<List<RubroDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                service.setLog("Error al cargar las categorías de los rubros" + caught.getMessage() + "" + caught.getCause(), null);
            }

            @Override
            public void onSuccess(List<RubroDTO> result) {
                for (RubroDTO rb : result) {
                    getComboCatRubros().addItem(rb.getStrdescripcion(), rb.getIdrubro());
                }
            }
        });
    }

    private void llenarRubroslista(String cod) {
        //Limpiamos el combo de la escuela

        this.getComboRubros().clear();
        service.obtenerRubros(cod, new AsyncCallback<List<RubroDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onSuccess(List<RubroDTO> result) {
                lstRubrosDto.clear();
                lstRubrosDto = result;
                for (RubroDTO rb : result) {
                    getComboRubros().addItem(rb.getStrdescripcion(), rb.getIdrubro());
                }
            }
        });

    }

    public String validaRubros(MontoDTO montoDto) {
        String msgVal = "";
        if (getValorContrato().getValue() == null) {
            msgVal += "*Ingrese el valor del contrato" + "<br/>";
        } else {
            if (getValorContrato().getValue().compareTo(actividadObraPadre.getObra().getValorDisponible()) <= 0) {
                valorAuxiliar = getValorContrato().getValue();
                if (getValorRubros().getValue() != null) {
                    if (getValorRubros().getValue().compareTo(getValorContrato().getValue()) <= 0) {
                        service.setLog("entreeeee", null);
                        if (!contrato.getMontos().isEmpty()) {
                            service.setLog("entre 1", null);
                            BigDecimal sumMontos = sumarRubros();
                            sumMontos = sumMontos.add(montoDto.getValor());
                            if (sumMontos.compareTo(getValorContrato().getValue()) > 0) {
                                msgVal += "*La suma de los rubros asociados superan el valor del contrato" + "<br/>";
                            }
                        }
                    } else {
                        msgVal += "*El valor del rubro no puede ser superior al valor del contrato" + "<br/>";
                    }
                } else {
                    msgVal += "*Ingrese el valor del rubro" + "<br/>";
                }
            } else {
                msgVal += "*El valor del contrato es superior al valor disponible del proyecto" + "<br/>";
            }
        }
        if (rubro == null) {
            msgVal += "*Seleccione un rubro" + "<br/>";
        }
        service.setLog("entre 2", null);
        if (msgVal.equals("")) {
            service.setLog("entre 3", null);
            msgVal += "El rubro se registró correctamente";
        }

        return msgVal;
    }

    public BigDecimal sumarRubros() {
        BigDecimal sumMontos = BigDecimal.ZERO;
        for (Iterator it = contrato.getMontos().iterator(); it.hasNext();) {
            MontoDTO monto = (MontoDTO) it.next();
            sumMontos = sumMontos.add(monto.getValor());
        }
        return sumMontos;
    }

    public void limpiarMontos() {
        this.llenarCategorias();
        llenarRubroslista("123102");
        this.getValorRubros().clear();
        this.getValorRubros().setEmptyText("Valor");

    }

    public void limpiarFuentes() {
        this.getValorFuenteRecurso().clear();
        this.getValorFuenteRecurso().setEmptyText("Valor");
        entidades.clear();
        llenarFuenteRecursosContrato(entidades);
    }
    // </editor-fold>

    /**
     * @return the vp
     */
    public VerticalPanel getVp() {
        return vp;
    }

    /**
     * @param vp the vp to set
     */
    public void setVp(VerticalPanel vp) {
        this.vp = vp;
    }

    /**
     * @return the objetoContrato
     */
    public TextArea getObjetoContrato() {
        return objetoContrato;
    }

    /**
     * @param objetoContrato the objetoContrato to set
     */
    public void setObjetoContrato(TextArea objetoContrato) {
        this.objetoContrato = objetoContrato;
    }

    /**
     * @return the valorContrato
     */
    public NumberField<BigDecimal> getValorContrato() {
        return valorContrato;
    }

    /**
     * @param valorContrato the valorContrato to set
     */
    public void setValorContrato(NumberField<BigDecimal> valorContrato) {
        this.valorContrato = valorContrato;
    }

    /**
     * @return the valorRubros
     */
    public NumberField<BigDecimal> getValorRubros() {
        return valorRubros;
    }

    /**
     * @param valorRubros the valorRubros to set
     */
    public void setValorRubros(NumberField<BigDecimal> valorRubros) {
        this.valorRubros = valorRubros;
    }

    /**
     * @return the valorFuenteRecurso
     */
    public NumberField<BigDecimal> getValorFuenteRecurso() {
        return valorFuenteRecurso;
    }

    /**
     * @param valorFuenteRecurso the valorFuenteRecurso to set
     */
    public void setValorFuenteRecurso(NumberField<BigDecimal> valorFuenteRecurso) {
        this.valorFuenteRecurso = valorFuenteRecurso;
    }

    /**
     * @return the nombreAbre
     */
    public TextField getNombreAbre() {
        return nombreAbre;
    }

    /**
     * @param nombreAbre the nombreAbre to set
     */
    public void setNombreAbre(TextField nombreAbre) {
        this.nombreAbre = nombreAbre;
    }

    /**
     * @return the fechaSuscripcionContrato
     */
    public DateField getFechaSuscripcionContrato() {
        return fechaSuscripcionContrato;
    }

    /**
     * @param fechaSuscripcionContrato the fechaSuscripcionContrato to set
     */
    public void setFechaSuscripcionContrato(DateField fechaSuscripcionContrato) {
        this.fechaSuscripcionContrato = fechaSuscripcionContrato;
    }

    /**
     * @return the fechaSuscripcionActaInicio
     */
    public DateField getFechaSuscripcionActaInicio() {
        return fechaSuscripcionActaInicio;
    }

    /**
     * @param fechaSuscripcionActaInicio the fechaSuscripcionActaInicio to set
     */
    public void setFechaSuscripcionActaInicio(DateField fechaSuscripcionActaInicio) {
        this.fechaSuscripcionActaInicio = fechaSuscripcionActaInicio;
    }

    /**
     * @return the comboCatRubros
     */
    public ListBox getComboCatRubros() {
        return comboCatRubros;
    }

    /**
     * @param comboCatRubros the comboCatRubros to set
     */
    public void setComboCatRubros(ListBox comboCatRubros) {
        this.comboCatRubros = comboCatRubros;
    }

    /**
     * @return the comboRubros
     */
    public ListBox getComboRubros() {
        return comboRubros;
    }

    /**
     * @param comboRubros the comboRubros to set
     */
    public void setComboRubros(ListBox comboRubros) {
        this.comboRubros = comboRubros;
    }

    /**
     * @return the lstTerceros
     */
    public ComboBox<TerceroDTO> getLstTerceros() {
        return lstTerceros;
    }

    /**
     * @param lstTerceros the lstTerceros to set
     */
    public void setLstTerceros(ComboBox<TerceroDTO> lstTerceros) {
        this.lstTerceros = lstTerceros;
    }

    /**
     * @return the lstVigencia
     */
    public ComboBox<Integer> getLstVigencia() {
        return lstVigencia;
    }

    /**
     * @param lstVigencia the lstVigencia to set
     */
    public void setLstVigencia(ComboBox<Integer> lstVigencia) {
        this.lstVigencia = lstVigencia;
    }

    /**
     * @return the lstTipoContrato
     */
    public ComboBox<TipocontratoDTO> getLstTipoContrato() {
        return lstTipoContrato;
    }

    /**
     * @param lstTipoContrato the lstTipoContrato to set
     */
    public void setLstTipoContrato(ComboBox<TipocontratoDTO> lstTipoContrato) {
        this.lstTipoContrato = lstTipoContrato;
    }

    /**
     * @return the valorFuente
     */
    public NumberField<BigDecimal> getValorFuente() {
        return valorFuente;
    }

    /**
     * @param valorFuente the valorFuente to set
     */
    public void setValorFuente(NumberField<BigDecimal> valorFuente) {
        this.valorFuente = valorFuente;
    }

    /**
     * @return the porcentajeFuente
     */
    public NumberField getPorcentajeFuente() {
        return porcentajeFuente;
    }

    /**
     * @param porcentajeFuente the porcentajeFuente to set
     */
    public void setPorcentajeFuente(NumberField porcentajeFuente) {
        this.porcentajeFuente = porcentajeFuente;
    }

    /**
     * @return the lstVigen
     */
    public ListBox getLstVigen() {
        return lstVigen;
    }

    /**
     * @param lstVigen the lstVigen to set
     */
    public void setLstVigen(ListBox lstVigen) {
        this.lstVigen = lstVigen;
    }

    /**
     * @return the lstFormaP
     */
    public ListBox getLstFormaP() {
        return lstFormaP;
    }

    /**
     * @param lstFormaP the lstFormaP to set
     */
    public void setLstFormaP(ListBox lstFormaP) {
        this.lstFormaP = lstFormaP;
    }
}