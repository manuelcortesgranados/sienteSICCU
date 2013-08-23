/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client;

import co.com.interkont.cobra.planoperativo.client.dto.ActividadobraDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ActividadobraDTOProps;
import co.com.interkont.cobra.planoperativo.client.dto.ContratoDTO;
import co.com.interkont.cobra.planoperativo.client.dto.DependenciaDTO;
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAble;
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAbleAsync;
import com.gantt.client.Gantt;
import com.gantt.client.config.GanttConfig;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.container.AbstractHtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.AbstractHtmlLayoutContainer.HtmlData;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.treegrid.TreeGrid;

/**
 *
 * @author desarrollo9
 */
public class ActividadForm implements IsWidget, EntryPoint {

    // <editor-fold defaultstate="collapsed" desc="Elementos visuales">
    private VerticalPanel vp;
    private TextField descripcionActividad;
    private DateField fechainicioActividad;
    private DateField fechafinActividad;
    private TextField peso;
    private static final int COLUMN_FORM_WIDTH = 100;
    Dialog modalPry;
    // </editor-fold>
    ActividadobraDTO actividadObraPadre;
    ActividadobraDTO actividacreada;
    Gantt<ActividadobraDTO, DependenciaDTO> gantt;
    ActividadobraDTOProps propes;
    ContratoDTO contratoDto;
    private CobraGwtServiceAbleAsync service = GWT.create(CobraGwtServiceAble.class);
    GwtMensajes msj = GWT.create(GwtMensajes.class);

    public VerticalPanel getVp() {
        return vp;
    }

    public void setVp(VerticalPanel vp) {
        this.vp = vp;
    }

    public TextField getDescripcionActividad() {
        return descripcionActividad;
    }

    public void setDescripcionActividad(TextField descripcionActividad) {
        this.descripcionActividad = descripcionActividad;
    }

    public DateField getFechainicioActividad() {
        return fechainicioActividad;
    }

    public void setFechainicioActividad(DateField fechainicioActividad) {
        this.fechainicioActividad = fechainicioActividad;
    }

    public DateField getFechafinActividad() {
        return fechafinActividad;
    }

    public void setFechafinActividad(DateField fechafinActividad) {
        this.fechafinActividad = fechafinActividad;
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

    public ActividadForm()
    {}
    
    public ActividadForm(ActividadobraDTO actividadobrapadre, Gantt<ActividadobraDTO, DependenciaDTO> gantt, Dialog di, ContratoDTO contratoDtoP) {
        this.actividadObraPadre = actividadobrapadre;
        this.gantt = gantt;
        modalPry = di;
        this.contratoDto = contratoDtoP;
        contratoDto.setValorDisponible(contratoDto.getNumvlrcontrato());
        service.setLog("convenio dis" + contratoDto.getValorDisponible(), null);
    }

    public ActividadForm(ActividadobraDTO actividadobrapadre, ContratoDTO contratoDtoP) {
        this.actividadObraPadre = actividadobrapadre;
        this.contratoDto = contratoDtoP;
        service.setLog("tarea Seleccionada=" + actividadobrapadre.getStartDateTime(), null);
    }

    private void crearFormulario() {
        getVp().add(new Label("Añadir contrato"));

        HtmlLayoutContainer con = new HtmlLayoutContainer(getTableMarkup());
        getVp().add(con);


        int cw = 200;

        setDescripcionActividad(new TextField());
        getDescripcionActividad().setEmptyText("Nombre de la Actividad");
        getDescripcionActividad().setWidth(cw);
        con.add(getDescripcionActividad(), new HtmlData(".descrip"));

        setFechainicioActividad(new DateField());
        getFechainicioActividad().setWidth(cw);
        getFechainicioActividad().setEmptyText("Fecha de Inicio");
        con.add(getFechainicioActividad(), new HtmlData(".fechaini"));

        setFechafinActividad(new DateField());
        getFechafinActividad().setWidth(cw);
        getFechafinActividad().setEmptyText("Fecha de Inicio");
        con.add(getFechafinActividad(), new HtmlData(".fechafin"));

//        setPeso(new TextField());
//        getPeso().setEmptyText("Peso");
//        getPeso().setWidth(cw);
//        con.add(getPeso(),new HtmlData(".peso") );
// 
        Button btnAdicionarActividad = new Button("Añadir Actividad", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                cargarDatosActividad();
                boolean varErrorres = false;
                String msgerrores = "";
                if (actividacreada.getStrdescactividad().isEmpty()) {
                    varErrorres = true;
                    msgerrores += "Debe Ingresar una descripción" + "<br/>";
                }
                if (actividacreada.getFechaInicio() != null) {
                    if (actividacreada.getFechaInicio().compareTo(contratoDto.getDatefechaini()) < 0) {
                        varErrorres = true;
                        msgerrores += "*La fecha de inicio de la actividad no puede ser inferior a la fecha de suscripcion del convenio " + actividadObraPadre.getFechaInicio().toString() + "<br/>";
                    }
                } else {
                    varErrorres = true;
                    msgerrores += "*La fecha de inicio no puede estar vacia";
                }
                if (actividacreada.getFechaFin() != null) {
                    if (actividacreada.getFechaFin().compareTo(contratoDto.getDatefechafin()) > 0) {
                        varErrorres = true;
                        msgerrores += "*La fecha de fin de la actividad no puede ser superior a la fecha de finalizacion del convenio" + actividadObraPadre.getFechaFin().toString() + "<br/>";
                    }
                }

                if (!varErrorres) {
                    crearActividad();
                } else {
                    AlertMessageBox d = new AlertMessageBox("Error", msgerrores);
                    d.show();
                }

            }
        });

        btnAdicionarActividad.setWidth("" + 150);
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
     '</tr><td class=fechafin ></td></tr>',
     '</tr><td class=fechaini></td></tr>',
     '</table>'
     ].join("");
     }-*/;

    public void cargarDatosActividad() {
        actividacreada.setStrdescactividad(getDescripcionActividad().getValue());
        actividacreada.setFechaInicio(getFechainicioActividad().getCurrentValue());
        actividacreada.setFechaFin(getFechafinActividad().getCurrentValue());
        //actividacreada.setPeso(getPeso();
    }

    public int calcularDuracion() {
        if (actividacreada.getFechaInicio() != null && actividacreada.getFechaFin() != null) {
            long diferencia = actividacreada.getFechaFin().getTime() - actividacreada.getFechaInicio().getTime();
            double dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));
            return ((int) dias);
        }
        return actividadObraPadre.getDuration();
    }

    public void crearActividad() {
        ActividadobraDTO tareaNueva = new ActividadobraDTO(actividacreada.getStrdescactividad(), actividacreada.getFechaInicio(), calcularDuracion(), 0, GanttConfig.TaskType.LEAF, 4, false);
        /*Se cargan el Panel del Gantt con la actividad Creada*/
        gantt.getGanttPanel().getContainer().getTreeStore().add(actividadObraPadre, tareaNueva);
        propes.taskType().setValue(actividadObraPadre, GanttConfig.TaskType.PARENT);
        gantt.getGanttPanel().getContainer().getTreeStore().update(actividadObraPadre);
        ((TreeGrid<ActividadobraDTO>) gantt.getGanttPanel().getContainer().getLeftGrid()).setExpanded(actividadObraPadre, true);  //tareaSeleccionada.addChild(tareaNueva);

    }
}
