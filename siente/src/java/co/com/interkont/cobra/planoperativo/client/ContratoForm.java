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
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.NumberField;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.treegrid.TreeGrid;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ContratoForm implements IsWidget, EntryPoint {

    // <editor-fold defaultstate="collapsed" desc="Elementos visuales">
    private VerticalPanel vp;
    TextArea objetoContrato;
    NumberField<BigDecimal> valorContrato;
    NumberField<BigDecimal> valorRubros;
    NumberField<BigDecimal> valorFuenteRecurso;
    TextField nombreAbre;
    DateField fechaSuscripcionContrato;
    DateField fechaSuscripcionActaInicio;
    private ListBox comboCatRubros = new ListBox();
    //private ComboBox<RubroDTO> comboCatRubros;
    private ListBox comboRubros = new ListBox();
    ComboBox<TerceroDTO> lstTerceros;
    ComboBox<Integer> lstVigencia;
    ComboBox<TipocontratoDTO> lstTipoContrato;
    //ComboBox<EnumFormaPago> lstFormaPago;
    NumberField<BigDecimal> valorFuente;
    NumberField porcentajeFuente;
    ListBox lstVigen;
    ListBox lstFormaP;
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
    
    @Override
    public Widget asWidget() {
        if (vp == null) {
            vp = new VerticalPanel();
            vp.setSpacing(70);
            vp.setWidth("" + COLUMN_FORM_WIDTH);
            vp.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
            vp.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
            crearFormulario();
        }
        return vp;
    }
    
    @Override
    public void onModuleLoad() {
        
        RootPanel.get().add(asWidget());
        
    }
    
    private void crearFormulario() {
        vp.add(new Label("Añadir contrato"));
        
        HtmlLayoutContainer con = new HtmlLayoutContainer(getTableMarkup());
        vp.add(con);
        
        int cw = 238;
        
        Label tObj = new Label("*OBJETIVOS");
        con.add(tObj, new HtmlData(".tobj"));
        
        Label tRubros = new Label("*RUBROS");
        con.add(tRubros, new HtmlData(".trubros"));
        
        Label tFuentes = new Label("*FUENTES DE RECURSOS");
        con.add(tFuentes, new HtmlData(".tfuente"));
        
        objetoContrato = new TextArea();
        objetoContrato.setWidth("" + cw);
        objetoContrato.setHeight("" + 80);
        objetoContrato.setText("Objeto");
        con.add(objetoContrato, new HtmlData(".objetoC"));
        
        valorContrato = new NumberField(new NumberPropertyEditor.BigDecimalPropertyEditor());
        valorContrato.setWidth(cw);
        valorContrato.setAllowBlank(false);
        valorContrato.setEmptyText("Valor estimado");
        valorContrato.setWidth(cw);
        con.add(valorContrato, new HtmlData(".valor"));
        
        llenarListaTipoContrato(tiposContrato);
        lstTipoContrato = new ComboBox<TipocontratoDTO>(tiposContrato, propstipoContrato.strdesctipocontrato());
        lstTipoContrato.setEmptyText("Tipo contratacion");
        lstTipoContrato.setWidth(cw);
        lstTipoContrato.setAllowBlank(false);
        lstTipoContrato.setTypeAhead(true);
        lstTipoContrato.setTriggerAction(TriggerAction.ALL);
        lstTipoContrato.addSelectionHandler(new SelectionHandler<TipocontratoDTO>() {
            @Override
            public void onSelection(SelectionEvent<TipocontratoDTO> event) {
                tipocontrato = event.getSelectedItem();
            }
        });
        con.add(lstTipoContrato, new HtmlData(".tipocontrato"));
        
        nombreAbre = new TextField();
        nombreAbre.setEmptyText("Nombre Abreviado");
        nombreAbre.setWidth(cw);
        nombreAbre.setAllowBlank(false);
        con.add(nombreAbre, new HtmlData(".nomabreviado"));
        
        
        fechaSuscripcionContrato = new DateField();
        fechaSuscripcionContrato.setWidth(cw);
        fechaSuscripcionContrato.setEmptyText("Fecha de suscripcion");
        fechaSuscripcionContrato.setAllowBlank(true);
        fechaSuscripcionContrato.setAutoValidate(true);
        con.add(fechaSuscripcionContrato, new HtmlData(".fechasuscont"));
        
        fechaSuscripcionActaInicio = new DateField();
        fechaSuscripcionActaInicio.setWidth(cw);
        fechaSuscripcionActaInicio.setEmptyText("Fecha de suscripcion acta inicio");
        fechaSuscripcionActaInicio.setAllowBlank(true);
        fechaSuscripcionActaInicio.setAutoValidate(true);
        con.add(fechaSuscripcionActaInicio, new HtmlData(".fechasusacta"));
        
        lstVigen = new ListBox(false);
        lstVigen.setWidth("" + cw);
        llenarV();
        lstVigen.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                vigencia = Integer.parseInt(lstVigen.getItemText(lstVigen.getSelectedIndex()));
            }
        });
        con.add(lstVigen, new HtmlData(".vigencia"));
        
        
        
        comboCatRubros.setWidth("" + cw);
        comboRubros.setWidth("" + cw);
        con.add(comboCatRubros, new HtmlData(".rubrocont"));
        con.add(comboRubros, new HtmlData(".rubrosub"));
        this.llenarCategorias();
        comboCatRubros.setSelectedIndex(0);
        comboCatRubros.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                llenarRubroslista(comboCatRubros.getValue(comboCatRubros.getSelectedIndex()));
            }
        });
        
        comboRubros.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                rubro = lstRubrosDto.get(comboRubros.getSelectedIndex());
            }
        });
        
        llenarRubroslista("123102");
        
        
        valorRubros = (NumberField<BigDecimal>) new NumberField(new NumberPropertyEditor.BigDecimalPropertyEditor());
        valorRubros.setEmptyText("Valor");
        valorRubros.setWidth(cw);
        valorRubros.setAllowBlank(false);
        con.add(valorRubros, new HtmlData(".valorubro"));
        
        PushButton btnAdicionarRubros = new PushButton(new Image(ExampleImages.INSTANCE.addbtnaddpry()), new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                MontoDTO monto = new MontoDTO(rubro, valorRubros.getValue(), vigencia);
                contrato.getMontos().add(monto);
                service.setLog("" + contrato.getMontos().size(), null);
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
        
        
        lstTerceros = new ComboBox<TerceroDTO>(entidades, propse.strnombrecompleto());
        lstTerceros.setEmptyText("Entidad");
        lstTerceros.setWidth(cw);
        lstTerceros.setAllowBlank(false);
        lstTerceros.setTypeAhead(true);
        lstTerceros.setTriggerAction(TriggerAction.ALL);
        lstTerceros.addSelectionHandler(new SelectionHandler<TerceroDTO>() {
            @Override
            public void onSelection(SelectionEvent<TerceroDTO> event) {
                terceroDto = event.getSelectedItem();
                obraFrDto = buscarFuenteDto(terceroDto.getCampoTemporalFuenteRecursos());
                
            }
        });
        con.add(lstTerceros, new HtmlData(".entidad"));
        
        lstFormaP = new ListBox(false);
        lstFormaP.setWidth("" + cw);
        llenarFormaPa();
        lstFormaP.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                formaPago = lstFormaP.getSelectedIndex();
                service.setLog("" + formaPago, null);
                if (formaPago == 1) {
                    valorFuenteRecurso.setEmptyText("Porcentaje");
                } else if (formaPago == 0) {
                    valorFuenteRecurso.setEmptyText("Valor");
                }
            }
        });
        con.add(lstFormaP, new HtmlData(".formapago"));
        
        valorFuenteRecurso = new NumberField(new NumberPropertyEditor.BigDecimalPropertyEditor());
        valorFuenteRecurso.setEmptyText("Valor");
        valorFuenteRecurso.setWidth(cw);
        valorFuenteRecurso.setAllowBlank(false);
        con.add(valorFuenteRecurso, new HtmlData(".valorfuente"));
        
        
        PushButton btnAdicionarFuentes;
        btnAdicionarFuentes = new PushButton(new Image(ExampleImages.INSTANCE.addbtnaddpry()), new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                RelacionobrafuenterecursoscontratoDTO rofr;
                if (formaPago == 1) {
                    BigDecimal valorP = obraFrDto.getValor().multiply(valorFuenteRecurso.getValue()).divide(new BigDecimal(100));
                    rofr = new RelacionobrafuenterecursoscontratoDTO(obraFrDto, valorP, formaPago);
                } else {
                    rofr = new RelacionobrafuenterecursoscontratoDTO(obraFrDto, valorFuenteRecurso.getValue(), formaPago);
                }
                String validacionDevuelta = validarFuenteRecurso(rofr);
                service.setLog(msgValidacion, null);
                if (!validacionDevuelta.equals("La fuente ha sido guardado")) {
                    AlertMessageBox d = new AlertMessageBox("Error", validacionDevuelta);
                    d.show();
                } else {
                    MessageBox msg = new MessageBox("Confirmación", validacionDevuelta);
                    msg.setModal(true);
                    msg.show();
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
    
    public void llenarVigencia(final ListStore<Integer> vigencias) {
        int año = Calendar.YEAR;
        vigencias.add(año);
        for (int i = 0; i < 14; i++) {
            vigencias.add(año + 1);
        }
    }
    
    public void llenarV() {
//        int año = Calendar.getInstance().getTime().getYear();
        int año = 2013;
        
        lstVigen.addItem("" + año);
        for (int i = 0; i < 14; i++) {
            año = año + 1;
            lstVigen.addItem("" + año);
        }
    }
    
    public void llenarFormaPa() {
        lstFormaP.addItem("Valor", "1");
        lstFormaP.addItem("Porcentaje", "2");
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
        contrato.setTextobjeto(objetoContrato.getText());
        contrato.setNombreAbreviado(nombreAbre.getValue());
        contrato.setDatefechaini(fechaSuscripcionContrato.getValue());
        contrato.setDatefechaactaini(fechaSuscripcionActaInicio.getValue());
        contrato.setTipocontrato(tipocontrato);
        contrato.setNumvlrcontrato(valorContrato.getValue());
        contrato.setValorDisponible(valorContrato.getValue());
    }
    
    public void crearTareaContrato() {
        ActividadobraDTO actividadObraContrato = new ActividadobraDTO(contrato.getTextobjeto(), contrato.getDatefechaini(), actividadObraPadre.getDuration(), 0, TaskType.PARENT, 3, false, contrato);
        
        List<ActividadobraDTO> lstHijos = new ArrayList<ActividadobraDTO>();
        ActividadobraDTO hitoFechaSuscripcion = new ActividadobraDTO("Suscripcion del contrato", fechaSuscripcionContrato.getValue(), actividadObraPadre.getDuration(), 0, TaskType.MILESTONE, 6, true);
        lstHijos.add(hitoFechaSuscripcion);//el pade de esta es actividadObraContrato
        ActividadobraDTO hitoFechaSuscripcionActa = new ActividadobraDTO("Suscripcion acta de inicio", fechaSuscripcionActaInicio.getValue(), actividadObraPadre.getDuration(), 0, TaskType.MILESTONE, 6, true);
        lstHijos.add(hitoFechaSuscripcionActa);
        
        ActividadobraDTO precontractual = new ActividadobraDTO("Precontractual", contrato.getDatefechaini(), actividadObraPadre.getDuration(), 0, TaskType.PARENT, 5, true);
        lstHijos.add(precontractual);
        
        List<ActividadobraDTO> lstHijosPrecontra = new ArrayList<ActividadobraDTO>();
        ActividadobraDTO revTecnica = new ActividadobraDTO("Revisión técnica de documentos", contrato.getDatefechaini(), actividadObraPadre.getDuration(), 0, TaskType.PARENT, 4, true);
        lstHijosPrecontra.add(revTecnica);
        ActividadobraDTO elaboPliegos = new ActividadobraDTO("Elaboración de pliegos de condiciones", contrato.getDatefechaini(), actividadObraPadre.getDuration(), 0, TaskType.LEAF, 4, true);
        lstHijosPrecontra.add(elaboPliegos);
        ActividadobraDTO evaPropuestas = new ActividadobraDTO("Evaluación de propuestas", contrato.getDatefechaini(), actividadObraPadre.getDuration(), 0, TaskType.LEAF, 4, true);
        lstHijosPrecontra.add(evaPropuestas);
        ActividadobraDTO elaContrato = new ActividadobraDTO("Elaboración de contratos", contrato.getDatefechaini(), actividadObraPadre.getDuration(), 0, TaskType.LEAF, 4, true);
        lstHijosPrecontra.add(elaContrato);
        
        ActividadobraDTO contractua = new ActividadobraDTO("Contractual", contrato.getDatefechaini(), actividadObraPadre.getDuration(), 0, TaskType.PARENT, 5, true);
        lstHijos.add(contractua);
        
        ActividadobraDTO Liquidaciones = new ActividadobraDTO("Liquidaciones", contrato.getDatefechaini(), actividadObraPadre.getDuration(), 0, TaskType.PARENT, 5, true);
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
        msgValidacion=new String();
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
        
        if (contrato.getMontos().isEmpty()) {
            hayError = true;
            msgValidacion += "*El contrato debe de tener por lo menos un monto asociado" + "<br/>";
        }
        
        if (contrato.getRelacionobrafuenterecursoscontratos().isEmpty()) {
            hayError = true;
            msgValidacion += "*El contrato debe de tener por lo menos una fuente de recursos asociado" + "<br/>";
        }
        return hayError;
        
    }
    
    public String validarFuenteRecurso(RelacionobrafuenterecursoscontratoDTO relacionFuente) {
       service.setLog("entre 1"+actividadObraPadre.getObra().getValorDisponible(), null);
        if (actividadObraPadre.getObra().getValorDisponible().compareTo(relacionFuente.getValor()) > 0) {
            if (relacionFuente.getValor().compareTo(relacionFuente.getObrafuenterecursosconvenios().getValor()) > 0) {
                return "El valor ingresado supera el valor de la fuente de recursos que aporta esta entidad" + "<br/>";
            } else {
                service.setLog("entre 2", null);
                if (!contrato.getRelacionobrafuenterecursoscontratos().isEmpty()) {
                    BigDecimal sumaValorAportado = BigDecimal.ZERO;
                    for (Object obr : contrato.getRelacionobrafuenterecursoscontratos()) {
                        ObrafuenterecursosconveniosDTO obrc = (ObrafuenterecursosconveniosDTO) obr;
                        sumaValorAportado = sumaValorAportado.add(obrc.getValor());
                    }
                    sumaValorAportado = sumaValorAportado.add(relacionFuente.getValor());
                    if (sumaValorAportado.compareTo(actividadObraPadre.getObra().getValor()) > 0) {
                        return "Valor no registrado, la suma de las fuentes de recursos supera  el valor del proyecto" + "<br/>";
                    }
                }
            }
            service.setLog("entre 3", null);                
            contrato.getRelacionobrafuenterecursoscontratos().add(relacionFuente);
            modificarValorDisponible(relacionFuente);
            return "La fuente ha sido guardado";
        } else {
            return "El ingresado supera el valor disponible del proyecto" + "<br/>";
        }
    }
    
    public void modificarValorDisponible(RelacionobrafuenterecursoscontratoDTO relacionFuente) {
         service.setLog("entre 4", null);                
        actividadObraPadre.getObra().setValorDisponible(actividadObraPadre.getObra().getValorDisponible().subtract(relacionFuente.getValor()));
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
                    comboCatRubros.addItem(rb.getStrdescripcion(), rb.getIdrubro());
                }
            }
        });
    }
    
    private void llenarRubroslista(String cod) {
        //Limpiamos el combo de la escuela

        this.comboRubros.clear();
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
                    comboRubros.addItem(rb.getStrdescripcion(), rb.getIdrubro());
                }
            }
        });
        
    }
    // </editor-fold>
}