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
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAble;
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAbleAsync;
import com.gantt.client.Gantt;
import com.gantt.client.config.GanttConfig.TaskType;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.DateWrapper;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.container.AbstractHtmlLayoutContainer.HtmlData;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.validator.EmptyValidator;
import com.sencha.gxt.widget.core.client.treegrid.TreeGrid;
import java.util.Date;

/**
 *
 * @author desarrollo9
 */
public class HitoForm implements IsWidget, EntryPoint {

    // <editor-fold defaultstate="collapsed" desc="Elementos visuales">
    private VerticalPanel vp;
    private TextArea descripcionActividad;
    private DateField fechainicioActividad;
    private TextField peso;
    private TaskType tipo;
    private int tipoactividad;
    //private static final int COLUMN_FORM_WIDTH = 100;
    Window modalAct;
    // </editor-fold>
    protected ActividadobraDTO actividadObraPadre;
    protected ActividadobraDTO actividacreada;
    protected Gantt<ActividadobraDTO, DependenciaDTO> gantt;
    protected ActividadobraDTOProps propes = GWT.create(ActividadobraDTOProps.class);
    protected ContratoDTO contratoDto;
    protected TreeStore<ActividadobraDTO> taskStore;
    private final CobraGwtServiceAbleAsync service = GWT.create(CobraGwtServiceAble.class);
    protected GwtMensajes msj = GWT.create(GwtMensajes.class);
    int numeracionActividad;
    protected int numeracionActual = 0;

    public VerticalPanel getVp() {
        return vp;
    }

    public void setVp(VerticalPanel vp) {
        this.vp = vp;
    }

    public TextArea getDescripcionActividad() {
        return descripcionActividad;
    }

    public void setDescripcionActividad(TextArea descripcionActividad) {
        this.descripcionActividad = descripcionActividad;
    }

    public DateField getFechainicioActividad() {
        return fechainicioActividad;
    }

    public void setFechainicioActividad(DateField fechainicioActividad) {
        this.fechainicioActividad = fechainicioActividad;
    }

    public ActividadobraDTO getActividadObraPadre() {
        return actividadObraPadre;
    }

    public void setActividadObraPadre(ActividadobraDTO actividadObraPadre) {
        this.actividadObraPadre = actividadObraPadre;
    }

    public ActividadobraDTO getActividacreada() {
        return actividacreada;
    }

    public void setActividacreada(ActividadobraDTO actividacreada) {
        this.actividacreada = actividacreada;
    }

    public TextField getPeso() {
        return peso;
    }

    public void setPeso(TextField peso) {
        this.peso = peso;
    }

    public HitoForm(ActividadobraDTO actividadobrapadre, Gantt<ActividadobraDTO, DependenciaDTO> gantt, Window dialog, ContratoDTO contratoDtoP, TaskType tipo, int tipoactividad, TreeStore<ActividadobraDTO> taskStore) {
        this.actividadObraPadre = actividadobrapadre;
        this.gantt = gantt;
        modalAct = dialog;
        this.contratoDto = contratoDtoP;
        actividacreada = new ActividadobraDTO();
        this.tipo = tipo;
        this.tipoactividad = tipoactividad;
        this.taskStore = taskStore;
        this.numeracionActividad = taskStore.getAll().size()+1;

    }

    private void crearFormulario() {

        getVp().add(new Label("Añadir hito"));

        HtmlLayoutContainer con = new HtmlLayoutContainer(getTableMarkup());
        getVp().add(con);

        int cw = 200;

        setDescripcionActividad(new TextArea());
        getDescripcionActividad().setHeight("50px");
        getDescripcionActividad().setWidth("200px");
        con.add(new FieldLabel(getDescripcionActividad(), "Descripción:"), new HtmlData(".descrip"));

        setFechainicioActividad(new DateField());
        getFechainicioActividad().setWidth(cw);
        getFechainicioActividad().addValidator(new EmptyValidator<Date>());
        getFechainicioActividad().setAutoValidate(true);

        getFechainicioActividad().setEmptyText("Fecha");
        con.add(new FieldLabel(getFechainicioActividad(), "Fecha:"), new HtmlData(".fechaini"));



        Button btnAdicionarActividad = new Button("Añadir Hito", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                AlertMessageBox d;

                if (getDescripcionActividad().getValue() != null && getDescripcionActividad().getValue().compareTo("") != 0) {
                    if (getFechainicioActividad().getValue() != null) {

                        if (getFechainicioActividad().getValue().compareTo(actividadObraPadre.getStartDateTime()) >= 0) {
                           if(GanttDatos.validarNombreActividad(getDescripcionActividad().getValue(), taskStore)){
                            modalAct.hide();
                            crearActividad();
                           }else{
                            d = new AlertMessageBox("Alerta", "Por favor verifique el nombre del hito, ya se encuentra un hito con este nombre:"+getDescripcionActividad().getValue());
                            d.show();
                           }
                        } else {
                            d = new AlertMessageBox("Alerta", "La fecha de inicio no puede ser inferior a "
                                    + obtenerFecha(actividadObraPadre.getStartDateTime()));
                            d.show();
                        }

                    } else {
                        d = new AlertMessageBox("Alerta", "Debe diligenciar la fecha ");
                        d.show();
                    }

                } else {
                    d = new AlertMessageBox("Error", "Debe ingresar una descripción");
                    d.show();
                }
            }
        });

        btnAdicionarActividad.setWidth("150px");
        con.add(btnAdicionarActividad);

    }

    @Override
    public Widget asWidget() {
        if (getVp() == null) {
            setVp(new VerticalPanel());
            // getVp().setSpacing(70);
            //getVp().setWidth("" + COLUMN_FORM_WIDTH);
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

    private native String getTableMarkup() /*-{
     return ['<table width=100% cellpadding=0 cellspacing=10>',     
     '<tr><td class=descrip width=50%></td></tr>',
     '<tr><td class=peso></td></tr>',
     '</tr><td class=fechaini ></td></tr>',
     '</tr><td class=fechafin></td></tr>',
     '</table>'
     ].join("");
     }-*/;

    public void cargarDatosActividad() {
        actividacreada.setName(getDescripcionActividad().getValue());
        actividacreada.setStartDateTime(getFechainicioActividad().getValue());
        actividacreada.setEndDateTime(getFechainicioActividad().getValue());
        //actividacreada.setPeso(getPeso();

    }

    public void crearActividad() {
       // numeracionActual=GanttDatos.modificarEnCascadaNumeracion(actividadObraPadre);
        cargarDatosActividad();
        ActividadobraDTO tareaNueva = new ActividadobraDTO(actividacreada.getName(), actividacreada.getStartDateTime(), 0, 0, tipo, tipoactividad, false);
        tareaNueva.setNumeracion(numeracionActividad);
        
        /*Se cargan el Panel del Gantt con la actividad Creada*/
        gantt.getGanttPanel().getContainer().getTreeStore().insert(actividadObraPadre, taskStore.getChildren(actividadObraPadre).size(), tareaNueva);
        actividadObraPadre.addChild(tareaNueva);
        //GanttDatos.modificarFechaFin(actividadObraPadre, taskStore, propes, contratoDto);
        gantt.getGanttPanel().getContainer().getTreeStore().update(actividadObraPadre);
        ((TreeGrid<ActividadobraDTO>) gantt.getGanttPanel().getContainer().getLeftGrid()).setExpanded(actividadObraPadre, true);  //tareaSeleccionada.addChild(tareaNueva);
       // GanttDatos.asignarNumeracion(taskStore, numeracionActual,6);
        GanttDatos.guardarBorradorConvenio(contratoDto, service, gantt);
    }

    public String obtenerFecha(Date fecha) {
        DateWrapper dw = new DateWrapper(fecha).clearTime();

        return String.valueOf(dw.getFullYear()) + "-" + String.valueOf(dw.getMonth() + 1) + "-" + String.valueOf(dw.getDate());
    }

    public boolean validarHito() {
        for (ActividadobraDTO act : taskStore.getAll()) {
            if (act.getName().equals(getDescripcionActividad().getValue())) {
                return false;
            }
        }
        return true;

    }
}
