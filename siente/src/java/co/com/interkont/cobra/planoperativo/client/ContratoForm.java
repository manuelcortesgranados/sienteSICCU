/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client;

import co.com.interkont.cobra.planoperativo.client.dto.ActividadobraDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ContratoDTO;
import co.com.interkont.cobra.planoperativo.client.dto.DependenciaDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ObrafuenterecursosconveniosDTO;
import co.com.interkont.cobra.planoperativo.client.dto.RubroDTO;
import co.com.interkont.cobra.planoperativo.client.dto.TerceroDTO;
import co.com.interkont.cobra.planoperativo.client.dto.TipocontratoDTO;
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAble;
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAbleAsync;
import com.gantt.client.Gantt;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widget.client.TextButton;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.container.AbstractHtmlLayoutContainer.HtmlData;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.NumberField;
import com.sencha.gxt.widget.core.client.form.TextField;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ContratoForm implements IsWidget, EntryPoint {

    private VerticalPanel vp;
    TextField objetoContrato;
    NumberField valorContrato;
    NumberField valorRubros;
    NumberField valorFuenteRecurso;
    ComboBox<RubroDTO> lstRubros;
    ComboBox<TerceroDTO> lstTerceros;
    ComboBox<Integer> lstVigencia;
    ComboBox<TipocontratoDTO> lstTipoContrato;
    EntidadProperties propse = GWT.create(EntidadProperties.class);
    final ListStore<TerceroDTO> entidades = new ListStore<TerceroDTO>(propse.intcodigo());
    RubroProperties props = GWT.create(RubroProperties.class);
    final ListStore<RubroDTO> rubros = new ListStore<RubroDTO>(props.idrubro());
    VigenciaProperties propsv = GWT.create(VigenciaProperties.class);
    final ListStore<Integer> vigencias = new ListStore<Integer>(propsv.vigencia());
    private CobraGwtServiceAbleAsync service = GWT.create(CobraGwtServiceAble.class);
    GwtMensajes msj = GWT.create(GwtMensajes.class);
    private TextField nombreProyectoTextField = new TextField();
    private TextField descripcion;
    private DateField fechainicio;
    private DateField fechafin;
    ContratoDTO convenioDto;
    ActividadobraDTO actividadObraPadre;
    Gantt<ActividadobraDTO, DependenciaDTO> gantt;
    Dialog modalContrato;

    public ContratoForm() {
    }

    public ContratoForm(ActividadobraDTO actividadobrapadre, Gantt<ActividadobraDTO, DependenciaDTO> gantt, Dialog di, ContratoDTO convenio) {
        this.actividadObraPadre = actividadobrapadre;
        this.gantt = gantt;
        modalContrato = di;
        this.convenioDto = convenio;
    }

    public TextField getNombreProyectoTextField() {
        return nombreProyectoTextField;
    }

    public void setNombreProyectoTextField(TextField nombreProyectoTextField) {
        this.nombreProyectoTextField = nombreProyectoTextField;
    }

    @Override
    public Widget asWidget() {
        if (vp == null) {
            vp = new VerticalPanel();
            vp.setSpacing(10);
            crearFormulario();
            //createTabForm();
        }
        return vp;
    }

    @Override
    public void onModuleLoad() {

        RootPanel.get().add(asWidget());

    }

    private void crearFormulario() {
        FramedPanel panel = new FramedPanel();
        panel.setHeadingText("Ingresar Datos del Proyecto");

        HtmlLayoutContainer con = new HtmlLayoutContainer(getTableMarkup());
        panel.setWidget(con);


        nombreProyectoTextField.setAllowBlank(false);
        con.add(new FieldLabel(nombreProyectoTextField, "nombre del proyecto"), new HtmlData(".ne"));

        descripcion = new TextField();
        descripcion.setAllowBlank(false);
        con.add(new FieldLabel(descripcion, "Descripcion del proyecto"), new HtmlData(".de"));

        fechainicio = new DateField();
        con.add(new FieldLabel(fechainicio, "Fecha de inicio"), new HtmlData(".fi"));

        fechafin = new DateField();
        con.add(new FieldLabel(fechafin, "Fecha de fin"), new HtmlData(".ff"));
        con.add(new TextButton("Guardar"), new HtmlData(".botones"));

        vp.add(panel);
    }


    private native String getTableMarkup() /*-{
     return [ '<table width=100% cellpadding=0 cellspacing=0>',

     '<tr><td class=ne width=50%></td></tr>',        '<tr><td class=de></td></tr>',
     '<tr><td class=fi></td></tr>',

     '<tr><td class=ff></td></tr>',
     '<tr><td class=botones></td></tr>',        '</table>'
     ].join("");
     }-*/;

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
            vigencias.add(año+1);
        }
    }
    
    
}