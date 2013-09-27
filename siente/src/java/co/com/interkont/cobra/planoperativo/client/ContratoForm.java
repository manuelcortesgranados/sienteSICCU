/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client;

import co.com.interkont.cobra.planoperativo.client.dto.ActividadobraDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ActividadobraDTOProps;
import co.com.interkont.cobra.planoperativo.client.dto.ContratoDTO;
import co.com.interkont.cobra.planoperativo.client.dto.DependenciaDTO;
import co.com.interkont.cobra.planoperativo.client.dto.GanttDatos;
import co.com.interkont.cobra.planoperativo.client.dto.MontoDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ObraDTO;
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
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtml;
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
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.core.client.util.DateWrapper;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
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
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ContratoForm implements IsWidget, EntryPoint {

    // <editor-fold defaultstate="collapsed" desc="Elementos visuales">
    private VerticalPanel vp;
    private TextArea objetoContrato = new TextArea();
    private NumberField<BigDecimal> valorContrato = (NumberField<BigDecimal>) new NumberField(new NumberPropertyEditor.BigDecimalPropertyEditor());
    private NumberField<BigDecimal> valorRubros;
    private NumberField<BigDecimal> valorFuenteRecurso;
    private TextField nombreAbre = new TextField();
    private DateField fechaSuscripcionContrato = new DateField();
    private DateField fechaSuscripcionActaInicio = new DateField();
    private DateField fechaFinalizacion = new DateField();
    private ListBox comboCatRubros = new ListBox();
    //private ComboBox<RubroDTO> comboCatRubros;
    private ListBox comboRubros = new ListBox();
    private ComboBox<TerceroDTO> lstTerceros;
    private ComboBox<Integer> lstVigencia;
    private ComboBox<TipocontratoDTO> lstTipoContrato;
    //ComboBox<EnumFormaPago> lstFormaPago;
    private NumberField<BigDecimal> valorFuente;
    private NumberField porcentajeFuente;
    private ListBox lstVigen = new ListBox(false);
    private ListBox lstFormaP = new ListBox(false);
    private static final int COLUMN_FORM_WIDTH = 686;
//    private static final int COLUMN_FORM_HEIGHT = 576;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Variables  para el cargue de los informacion en los combobox">
    EntidadProperties propse = GWT.create(EntidadProperties.class);
    protected final ListStore<TerceroDTO> entidades = new ListStore<TerceroDTO>(propse.intcodigo());
    RubroProperties props = GWT.create(RubroProperties.class);
    protected final ListStore<RubroDTO> rubros = new ListStore<RubroDTO>(props.idrubro());
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
    protected Window modalContrato;
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
    private ActividadobraDTO actividadObraEditar;
    boolean editar = false;
    private int idtempRelacionRecursos;
    private int idtempRubros;
    boolean fechaSusError;
    boolean fechaActaError;
    boolean fechaFinError;
    Date fechaContrato;
    TreeStore<ActividadobraDTO> taskStore;

    public ContratoForm() {
    }

    public ContratoForm(ActividadobraDTO actividadobrapadre, Gantt<ActividadobraDTO, DependenciaDTO> gantt, Window di, ActividadobraDTOProps propes, TreeStore<ActividadobraDTO> taskStore, ContratoDTO convenio) {
        this.actividadObraPadre = actividadobrapadre;
        this.gantt = gantt;
        modalContrato = di;
        contrato = new ContratoDTO();
        this.propes = propes;
        service.setLog("2", null);
        lstRubrosDto = new ArrayList<RubroDTO>();
        idtempRelacionRecursos = 0;
        idtempRubros = 0;
        this.taskStore = taskStore;
        this.convenioDto = convenio;
    }

    public ContratoForm(ActividadobraDTO actividadobraContratoEditar, Gantt<ActividadobraDTO, DependenciaDTO> gantt, Window di, ActividadobraDTO actividadObraPadre, ActividadobraDTOProps propes, TreeStore<ActividadobraDTO> taskStore, ContratoDTO convenio) {
        this.actividadObraEditar = actividadobraContratoEditar;
        this.gantt = gantt;
        modalContrato = di;
        this.contrato = actividadobraContratoEditar.getContrato();
        this.actividadObraPadre = actividadObraPadre;
        this.editar = true;
        fechaContrato = CalendarUtil.copyDate(contrato.getDatefechaini());
        lstRubrosDto = new ArrayList<RubroDTO>();
        this.propes = propes;
        this.taskStore = taskStore;
        this.convenioDto = convenio;
        CargarFormularioEditar();
        seEdito(actividadObraEditar);
    }

    private void seEdito(ActividadobraDTO actiEditar) {
        actiEditar.setSeEdito(false);
        for (ActividadobraDTO actiHija : actiEditar.getChildren()) {
            seEdito(actiHija);
        }
    }

    private void CargarFormularioEditar() {
        this.objetoContrato.setText(contrato.getTextobjeto());
        this.nombreAbre.setText(contrato.getNombreAbreviado());
        this.fechaSuscripcionActaInicio.setValue(contrato.getDatefechaactaini());
        this.fechaSuscripcionContrato.setValue(contrato.getDatefechaini());
        this.fechaFinalizacion.setValue(contrato.getDatefechafin());
        this.valorContrato.setValue(contrato.getNumvlrcontrato());
    }

    @Override
    public Widget asWidget() {
        if (getVp() == null) {
            setVp(new VerticalPanel());
            getVp().setSpacing(10);
            getVp().setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
            getVp().setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
            getVp().setStyleName("ikont-po-tb");
            crearFormulario();
        } else if (editar) {
            lstTipoContrato.setValue(contrato.getTipocontrato());
        }
        return getVp();
    }

    @Override
    public void onModuleLoad() {

        RootPanel.get().add(asWidget());

    }

    private void crearFormulario() {
        String tituloPantalla;
        if (!editar) {
            tituloPantalla = "Añadir contrato";
        } else {
            tituloPantalla = "Editar contrato";
        }

        Label tituloPagina = new Label(tituloPantalla);
        tituloPagina.setStyleName("ikont-po-label");

        vp.add(tituloPagina);

        HtmlLayoutContainer con = new HtmlLayoutContainer(getTableMarkup());
        con.setStyleName("ikont-po-tb");
        getVp().add(con);

        int cw = 238;

//        Label tObj = new Label("*Objetivos");
//        con.add(tObj, new HtmlData(".tobj"));

        getObjetoContrato().setWidth("" + cw);
        getObjetoContrato().setHeight("" + 80);
        con.add(new FieldLabel(getObjetoContrato(), "*Objetivos"), new HtmlData(".objetoC"));

        getValorContrato().setWidth(cw);
        //getValorContrato().setWidth(cw);

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
        con.add(new FieldLabel(getValorContrato(), "Valor estimado"), new HtmlData(".valor"));
        //con.add(getValorContrato(), new HtmlData(".valor"));



        llenarListaTipoContrato(tiposContrato);
        setLstTipoContrato(new ComboBox<TipocontratoDTO>(tiposContrato, propstipoContrato.strdesctipocontrato()));
        getLstTipoContrato().setWidth(cw);
        getLstTipoContrato().setTypeAhead(true);
        getLstTipoContrato().setTriggerAction(TriggerAction.ALL);
        getLstTipoContrato().addSelectionHandler(new SelectionHandler<TipocontratoDTO>() {
            @Override
            public void onSelection(SelectionEvent<TipocontratoDTO> event) {
                tipocontrato = event.getSelectedItem();
            }
        });
        con.add(new FieldLabel(getLstTipoContrato(), "Tipo contrato"), new HtmlData(".tipocontrato"));
        //con.add(getLstTipoContrato(), new HtmlData(".tipocontrato"));

        getNombreAbre().setWidth(cw);
        con.add(new FieldLabel(getNombreAbre(), "Nombre abreviado"), new HtmlData(".nomabreviado"));
        //con.add(getNombreAbre(), new HtmlData(".nomabreviado"));


        getFechaSuscripcionContrato().setWidth(cw);
        fechaSuscripcionContrato.addKeyDownHandler(new KeyDownHandler() {
            @Override
            public void onKeyDown(KeyDownEvent event) {
                if (fechaSuscripcionActaInicio.getValue() != null) {
                    if (fechaSuscripcionContrato.getValue().compareTo(fechaSuscripcionActaInicio.getValue()) > 0) {
                        AlertMessageBox d = new AlertMessageBox("Error", "La fecha de suscripcion no puede ser mayor a la fecha del acta de inicio");
                        d.show();
                    }
                }
            }
        });

        con.add(new FieldLabel(getFechaSuscripcionContrato(), "Fecha suscripción"), new HtmlData(".fechasuscont"));
        //con.add(getFechaSuscripcionContrato(), new HtmlData(".fechasuscont"));

        getFechaSuscripcionActaInicio().setWidth(cw);
        con.add(new FieldLabel(getFechaSuscripcionActaInicio(), "Fecha de suscripción acta inicio"), new HtmlData(".fechasusacta"));
        //con.add(getFechaSuscripcionActaInicio(), new HtmlData(".fechasusacta"));

        getFechaFinalizacion().setWidth(cw);
        con.add(new FieldLabel(getFechaFinalizacion(), "Fecha finalización"), new HtmlData(".fechafin"));
        final WidgetTablaMontos tblMontos = new WidgetTablaMontos(contrato);

        con.add(tblMontos.asWidget(), new HtmlData(".tblmontos"));


        PushButton btnAdicionarRubros = new PushButton(new Image(ExampleImages.INSTANCE.addbtnaddpry()), new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Window adicionarMontos = new Window();
                adicionarMontos.setBlinkModal(true);
                adicionarMontos.setModal(true);
                ModalAddMontos modalMontos = new ModalAddMontos(contrato, valorContrato, valorAuxiliar, actividadObraPadre, tblMontos, adicionarMontos, idtempRubros);
                adicionarMontos.add(modalMontos);
                adicionarMontos.show();
                service.setLog("montos en modal" + contrato.getMontos().size(), null);
            }
        });
        con.add(btnAdicionarRubros, new HtmlData(".addr"));

        final WidgetTablaFuenteR cel = new WidgetTablaFuenteR(contrato, actividadObraPadre);
        con.add(cel.asWidget(), new HtmlData(".tblfuen"));

        PushButton btnVerFuente = new PushButton(new Image(ExampleImages.INSTANCE.addbtnaddpry()), new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Window adicionarFuentes = new Window();
                adicionarFuentes.setBlinkModal(true);
                adicionarFuentes.setModal(true);
                ModalAddFuentes mo = new ModalAddFuentes(actividadObraPadre, contrato, cel, adicionarFuentes, idtempRelacionRecursos);
                adicionarFuentes.add(mo);
                adicionarFuentes.show();
            }
        });
        con.add(btnVerFuente, new HtmlData(".verf"));


        String nombreBotonPrincipal = "";

        if (!editar) {
            nombreBotonPrincipal = "Añadir Contrato";
            getObjetoContrato().setText("Objeto");
            getValorContrato().setEmptyText("Valor estimado");
            getLstTipoContrato().setEmptyText("Tipo contratacion");
            getNombreAbre().setEmptyText("Nombre Abreviado");
            getFechaSuscripcionContrato().setEmptyText("Fecha de suscripcion");
            getFechaSuscripcionActaInicio().setEmptyText("Fecha de suscripcion acta inicio");
            getFechaFinalizacion().setEmptyText("Fecha finalización");

        } else {
            nombreBotonPrincipal = "Editar Contrato";
            lstTipoContrato.setValue(contrato.getTipocontrato());
        }

        Button btnAdicionarContrato = new Button(nombreBotonPrincipal, new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (!editar) {
                    service.setLog("Entre aca en onclick", null);
                    CrearContrato();
                    service.setLog("sali aca en onclick", null);
                    if (validaciones()) {
                        AlertMessageBox d = new AlertMessageBox("Error", msgValidacion);
                        d.show();
                    } else {
                        modalContrato.hide();
                        crearTareaContrato();
                        gantt.getGanttPanel().runCascadeChanges();
                    }
                } else {
                    editarContrato();
                    boolean error = false;
                    if (fechaActaError) {
                        error = true;
                        msgValidacion += "*La fecha de acta de inicio no puede ser menor que la fecha del proyecto" + "<br/>";
                    }
                    if (fechaSusError) {
                        error = true;
                        msgValidacion += "*La fecha de suscripción no puede ser menor que la fecha del proyecto" + "<br/>";
                    }
                    if (fechaFinError) {
                        error = true;
                        msgValidacion += "*La fecha de finalización no puede ser menor que la fecha de inicio y la fecha de acta de inicio" + "<br/>";

                    }
                    if (!error) {
                        actividadObraEditar.setContrato(contrato);
                        modalContrato.hide();
                        gantt.getGanttPanel().getContainer().refresh();
                        modalContrato.hide();
                    } else {
                        AlertMessageBox d = new AlertMessageBox("Error", msgValidacion);
                        d.show();
                    }

                }
            }
        });

        btnAdicionarContrato.setWidth("" + 150);
        con.add(btnAdicionarContrato);


    }

    public boolean validarCambiofechaSuscripcionContrato() {
        if (contrato.getDatefechaini().compareTo(fechaSuscripcionContrato.getValue()) != 0) {
            if (fechaSuscripcionContrato.getValue().compareTo(actividadObraPadre.getStartDateTime()) >= 0) {
                service.setLog("es mayor al padre", null);
                contrato.setDatefechaini(fechaSuscripcionContrato.getValue());
                odifi(actividadObraEditar);


            } else {
                service.setLog("es menor al padre", null);
                fechaSuscripcionContrato.setValue(contrato.getDatefechaini());
                return true;
            }
        }
        return false;

    }
    int cualerror = 0;

    public boolean validarCambiofechaFinContrato() {
        /*se verifica si se modifico la fecha de finalizacion del contrato*/
        if (contrato.getDatefechafin() != null) {
            service.setLog("fecha 1" + contrato.getDatefechafin(), null);
            if (contrato.getDatefechafin().compareTo(fechaFinalizacion.getValue()) != 0) {
                service.setLog("fecha2", null);
                /*se verifica que la fecha modificada no sea mayor que la fecha fin del convenio*/
                if (fechaFinalizacion.getValue().compareTo(actividadObraPadre.getEndDateTime()) <= 0) {
                    /*se verifica si la fecha modificada es mayor o igual que la mayor fecha de finalizacion de los actividades hijas*/
                    if (contrato.getDatefechafin().compareTo(contrato.getDatefechaini()) > 0) {
                        if (contrato.getDatefechafin().compareTo(contrato.getDatefechaactaini()) > 0) {
                            if (modificarFechaFin()) {
                                contrato.setDatefechafin(fechaFinalizacion.getValue());
                                propes.endDateTime().setValue(actividadObraEditar, contrato.getDatefechafin());
                                propes.duration().setValue(actividadObraEditar, CalendarUtil.getDaysBetween(contrato.getDatefechaactaini(), contrato.getDatefechafin()));
                            } else {
                                return true;

                            }
                        } else {
                            return true;
                        }
                    } else {
                        return true;
                    }
                } else {
                    return true;

                }
            }
        }
        return false;
    }

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
        if (fechaSuscripcionContrato.getValue().compareTo(fechaContrato) > 0) {
            if (!actiHija.isSeEdito()) {
                int duracion = CalendarUtil.getDaysBetween(fechaContrato, fechaSuscripcionContrato.getValue());
                CalendarUtil.addDaysToDate(actiHija.getStartDateTime(), duracion);

                Date asigFin = CalendarUtil.copyDate(actiHija.getStartDateTime());
                actiHija.setEndDateTime(asigFin);

                ActividadobraDTO actObra = taskStore.getParent(actiHija);


                service.setLog("Actividad fecha fin primera" + actiHija.getEndDateTime(), null);
                CalendarUtil.addDaysToDate(actiHija.getEndDateTime(), actiHija.getDuration());
                service.setLog("Actividad fecha fin segunda" + actiHija.getEndDateTime(), null);

                if (actiHija.getName().equals("Suscripcion acta de inicio")) {
                    contrato.setDatefechaactaini(actiHija.getStartDateTime());

                }
                actiHija.setSeEdito(true);
            }
        } else {
            if (!actiHija.isSeEdito()) {
                int duracion = CalendarUtil.getDaysBetween(fechaSuscripcionContrato.getValue(), fechaContrato);

                Date fechaI = CalendarUtil.copyDate(actiHija.getStartDateTime());
                fechaI.setDate(fechaI.getDate() - duracion);

                Date asigFin = CalendarUtil.copyDate(fechaI);
                actiHija.setEndDateTime(asigFin);

                ActividadobraDTO actObra = taskStore.getParent(actiHija);

                propes.startDateTime().setValue(actiHija, fechaI);


                Date dateFin = CalendarUtil.copyDate(actiHija.getEndDateTime());
                dateFin.setDate(dateFin.getDate() + actiHija.getDuration());
                propes.endDateTime().setValue(actiHija, dateFin);

                if (actObra.getEndDateTime().compareTo(actiHija.getEndDateTime()) < 0) {
                    int duraNueva = CalendarUtil.getDaysBetween(actiHija.getStartDateTime(), actObra.getEndDateTime());
                    service.setLog(msgValidacion, null);
                    Date copiaFechaI = CalendarUtil.copyDate(actiHija.getStartDateTime());
                    actiHija.setEndDateTime(copiaFechaI);
                    actiHija.setDuration(duraNueva);
                    CalendarUtil.addDaysToDate(copiaFechaI, duraNueva);
                    propes.endDateTime().setValue(actiHija, copiaFechaI);
                    propes.duration().setValue(actiHija, duraNueva);

                }

                if (actiHija.getName().equals("Suscripcion acta de inicio")) {
                    contrato.setDatefechaactaini(actiHija.getStartDateTime());

                }

                actiHija.setSeEdito(true);

            }
        }
    }

    public boolean validarCambiofechaActaContrato() {
        if (contrato.getDatefechaactaini().compareTo(fechaSuscripcionActaInicio.getValue()) != 0) {
            if (fechaSuscripcionActaInicio.getValue().compareTo(actividadObraPadre.getObra().getFechaInicio()) >= 0) {
                contrato.setDatefechaactaini(fechaSuscripcionActaInicio.getValue());
                propes.startDateTime().setValue(actividadObraEditar.getChildren().get(1), fechaSuscripcionActaInicio.getValue());
                Date copia = CalendarUtil.copyDate(fechaSuscripcionActaInicio.getValue());
                CalendarUtil.addDaysToDate(copia, 1);
                propes.endDateTime().setValue(actividadObraEditar.getChildren().get(1), copia);

            } else {
                fechaSuscripcionActaInicio.setValue(contrato.getDatefechaactaini());
                return true;
            }
        }
        return false;

    }

    public void editarContrato() {

        if (!contrato.getTextobjeto().equals(objetoContrato.getText())) {
            contrato.setTextobjeto(objetoContrato.getText());
        }

        fechaSusError = validarCambiofechaSuscripcionContrato();
        //fechaFinError = validarCambiofechaFinContrato();
        // fechaActaError = validarCambiofechaActaContrato();
        if (!contrato.getNombreAbreviado().equals(nombreAbre.getText())) {
            contrato.setNombreAbreviado(nombreAbre.getText());
            propes.name().setValue(actividadObraEditar, contrato.getNombreAbreviado());
        }
        if (contrato.getNumvlrcontrato().compareTo(valorContrato.getValue()) != 0) {
            contrato.setNumvlrcontrato(valorContrato.getValue());
        }

        if (contrato.getTipocontrato() != tipocontrato) {
            contrato.setTipocontrato(tipocontrato);
        }



    }

    private native String getTableMarkup() /*-{
     return [ '<table width=100% cellpadding=0>',
     '<tr><td class=objetoC width=50%></td><td class=valor></td></tr>',
     '<tr><td class=tipocontrato></td><td class=nomabreviado></td></tr>',
     '<tr><td class=fechasuscont></td><td class=fechasusacta></td></tr>',
     '<tr><td class=fechafin></td></tr>',
     '<tr><td class=tblmontos colspan=2></td><td></td></tr>',
     '<tr><td class=addr></td></tr>',
     '<tr><td class=tblfuen colspan=2></td><td></td></tr>', 
     '<tr><td  width=30% class=verf></td></tr>',
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
        contrato.setDatefechafin(getFechaFinalizacion().getValue());
        contrato.setTipocontrato(tipocontrato);
        contrato.setNumvlrcontrato(getValorContrato().getValue());
        contrato.setValorDisponible(getValorContrato().getValue());
       
    }

    public void crearTareaContrato() {
        actividadObraPadre.getObra().setValorDisponible(actividadObraPadre.getObra().getValorDisponible().subtract(contrato.getNumvlrcontrato()));
        ActividadobraDTO actividadObraContrato = new ActividadobraDTO(contrato.getNombreAbreviado(), fechaSuscripcionContrato.getValue(), CalendarUtil.getDaysBetween(fechaSuscripcionContrato.getValue(), fechaSuscripcionActaInicio.getValue()) + 1, 0, TaskType.PARENT, 3, false, contrato);

      
        List<ActividadobraDTO> lstHijos = new ArrayList<ActividadobraDTO>();
        ActividadobraDTO hitoFechaSuscripcion = new ActividadobraDTO("Suscripcion del contrato", fechaSuscripcionContrato.getValue(), 1, 0, TaskType.MILESTONE, 6, true);
        lstHijos.add(hitoFechaSuscripcion);//el pade de esta es actividadObraContrato
        ActividadobraDTO hitoFechaSuscripcionActa = new ActividadobraDTO("Suscripcion acta de inicio", fechaSuscripcionActaInicio.getValue(), 1, 0, TaskType.MILESTONE, 6, true);
        lstHijos.add(hitoFechaSuscripcionActa);

        ActividadobraDTO precontractual = new ActividadobraDTO("Precontractual", fechaSuscripcionContrato.getValue(), 1, 0, TaskType.PARENT, 5, true);
        lstHijos.add(precontractual);

        List<ActividadobraDTO> lstHijosPrecontra = new ArrayList<ActividadobraDTO>();
        ActividadobraDTO revTecnica = new ActividadobraDTO("Revisión técnica de documentos", fechaSuscripcionContrato.getValue(), 1, 0, TaskType.PARENT, 4, true);
        lstHijosPrecontra.add(revTecnica);
        ActividadobraDTO elaboPliegos = new ActividadobraDTO("Elaboración de pliegos de condiciones", fechaSuscripcionContrato.getValue(), 1, 0, TaskType.LEAF, 4, true);
        lstHijosPrecontra.add(elaboPliegos);
        ActividadobraDTO evaPropuestas = new ActividadobraDTO("Evaluación de propuestas", fechaSuscripcionContrato.getValue(), 1, 0, TaskType.LEAF, 4, true);
        lstHijosPrecontra.add(evaPropuestas);
        ActividadobraDTO elaContrato = new ActividadobraDTO("Elaboración de contratos", fechaSuscripcionContrato.getValue(), 1, 0, TaskType.LEAF, 4, true);
        lstHijosPrecontra.add(elaContrato);

        ActividadobraDTO contractua = new ActividadobraDTO("Contractual", fechaSuscripcionContrato.getValue(), 1, 0, TaskType.PARENT, 5, true);
        lstHijos.add(contractua);

        ActividadobraDTO Liquidaciones = new ActividadobraDTO("Liquidaciones", fechaSuscripcionContrato.getValue(), 1, 0, TaskType.PARENT, 5, true);
        lstHijos.add(Liquidaciones);

        actividadObraContrato.setChildren(lstHijos);
        precontractual.setChildren(lstHijosPrecontra);

        taskStore.add(actividadObraPadre, actividadObraContrato);

        if (actividadObraContrato.hasChildren()) {
            processFolder(taskStore, actividadObraContrato);
        }
        
         actividadObraPadre.addChild(actividadObraContrato);
         gantt.getGanttPanel().getContainer().getTreeStore().update(actividadObraPadre);
       
    }

    private void processFolder(TreeStore<ActividadobraDTO> store, ActividadobraDTO folder) {
        for (ActividadobraDTO child : folder.getChildren()) {
            store.add(folder, child);
            if (child.hasChildren()) {
                processFolder(store, child);
            }
        }
    }

//    public boolean validaciones() {
//        service.setLog("validaciones", null);
//        boolean hayError = false;
//        msgValidacion = new String();
//        if (contrato.getDatefechaini() == null) {
//            hayError = true;
//            msgValidacion += "*por favor ingrese la fecha de suscripcion" + "<br/>";
//        } else {
//
//            if (contrato.getDatefechaini().compareTo(actividadObraPadre.getObra().getFechaInicio()) < 0) {
//                hayError = true;
//                msgValidacion += "*La fecha de inicio no puede ser inferior a la fecha de suscripcion del proyecto" + "<br/>";
//            }
//        }
//        if (contrato.getDatefechaactaini() == null) {
//            hayError = true;
//            msgValidacion += "*por favor ingrese la fecha de suscripcion del acta de inicio" + "<br/>";
//        } else {
//
//            if (contrato.getDatefechaactaini().compareTo(actividadObraPadre.getObra().getFechaInicio()) < 0) {
//                hayError = true;
//                msgValidacion += "*La fecha de suscripcion del acta no puede ser inferior a la fecha de inicio del proyecto" + "<br/>";
//            }
//        }
//        if (contrato.getDatefechafin() == null) {
//            hayError = true;
//            msgValidacion += "*por favor ingrese la fecha de finalizacion" + "<br/>";
//        } else {
//            if (contrato.getDatefechafin().compareTo(actividadObraPadre.getObra().getFechaInicio()) < 0) {
//                hayError = true;
//                msgValidacion += "*La fecha de finalizacion no puede ser inferior a la fecha de inicio del proyecto" + "<br/>";
//            }
//        }
//        if (contrato.getDatefechafin().compareTo(contrato.getDatefechaactaini()) < 0) {
//            hayError = true;
//            msgValidacion += "*La fecha de finalizacion no puede ser inferior a la fecha de inicio del contrato" + "<br/>";
//
//        }
////        if (contrato.getDatefechafin().compareTo(contrato.getDatefechaactaini()) < 0) {
////            hayError = true;
////            msgValidacion += "*La fecha de finalizacion no puede ser inferior a la fecha del acta de inicio del contrato" + "<br/>";
////
////        }
//
//        if (contrato.getTextobjeto().equals("Objeto")) {
//            hayError = true;
//            msgValidacion += "*por favor ingrese el objeto del contrato" + "<br/>";
//        }
//
//        if (nombreAbre.getValue() == null) {
//            hayError = true;
//            msgValidacion += "*por favor ingrese nombre abreviado del contrato" + "<br/>";
//        }
//
//        if (contrato.getTipocontrato() == null) {
//            hayError = true;
//            msgValidacion += "*por favor seleccione un tipo de contrato" + "<br/>";
//        }
//
//        if (contrato.getMontos().isEmpty()) {
//            hayError = true;
//            msgValidacion += "*El contrato debe de tener por lo menos un monto asociado" + "<br/>";
//        }
//        if (contrato.getRelacionobrafuenterecursoscontratos().isEmpty()) {
//            hayError = true;
//            msgValidacion += "*El contrato debe de tener por lo menos una fuente de recursos asociado" + "<br/>";
//        }
//        if (contrato.getNumvlrcontrato() != null) {
//            if (contrato.getNumvlrcontrato().compareTo(sumarRubros()) != 0) {
//                hayError = true;
//                msgValidacion += "*La suma de los rubros asociados debe ser igual al valor del contrato" + "<br/>";
//            }
//        }
//         service.setLog("validaciones 1", null);
//        return hayError;
//
//    }
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

        if (nombreAbre.getValue() == null) {
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
        if (relacionFuente.getValor().compareTo(relacionFuente.getObrafuenterecursosconvenios().getValorDisponible()) >= 0) {
            return "El valor ingresado supera el valor disponible de la fuente de recursos que aporta esta entidad" + "<br/>";
        } else {
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
            contrato.getRelacionobrafuenterecursoscontratos().add(relacionFuente);
            modificarValorDisponible(relacionFuente);
            return "La fuente ha sido guardada";
        }

    }

    public void modificarValorDisponible(RelacionobrafuenterecursoscontratoDTO relacionFuente) {
        relacionFuente.getObrafuenterecursosconvenios().setValorDisponible(relacionFuente.getObrafuenterecursosconvenios().getValorDisponible().subtract(relacionFuente.getValor()));

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
                service.setLog("error obteniendo los rubros", null);
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

    // <editor-fold defaultstate="collapsed" desc="Eliminar Fuente recursos>
    public void eliminarFuenteRecursos(RelacionobrafuenterecursoscontratoDTO relacionFuente) {
        contrato.getFuenterecursosconvenios().remove(relacionFuente);
        relacionFuente.getObrafuenterecursosconvenios().setValorDisponible(relacionFuente.getObrafuenterecursosconvenios().getValorDisponible().add(relacionFuente.getValor()));
    }

    public boolean modificarFechaFin() {
        boolean esMenor = false;
        List<ActividadobraDTO> lstHijosProyecto = taskStore.getChildren(actividadObraEditar);
        for (ActividadobraDTO act : lstHijosProyecto) {
            if (act.getEndDateTime().compareTo(fechaFinalizacion.getValue()) <= 0) {
                esMenor = true;
            } else {
                esMenor = false;
            }
        }
        return esMenor;
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

    /**
     * @return the idtempRelacionRecursos
     */
    public int getIdtempRelacionRecursos() {
        return idtempRelacionRecursos;
    }

    /**
     * @param idtempRelacionRecursos the idtempRelacionRecursos to set
     */
    public void setIdtempRelacionRecursos(int idtempRelacionRecursos) {
        this.idtempRelacionRecursos = idtempRelacionRecursos;
    }

    /**
     * @return the idtempRubros
     */
    public int getIdtempRubros() {
        return idtempRubros;
    }

    /**
     * @param idtempRubros the idtempRubros to set
     */
    public void setIdtempRubros(int idtempRubros) {
        this.idtempRubros = idtempRubros;
    }

    public DateField getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(DateField fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }
}