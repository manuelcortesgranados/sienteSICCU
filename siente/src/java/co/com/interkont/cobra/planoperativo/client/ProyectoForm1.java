package co.com.interkont.cobra.planoperativo.client;

import co.com.interkont.cobra.planoperativo.client.dto.ContratoDTO;
import co.com.interkont.cobra.planoperativo.client.dto.FuenterecursosconvenioDTO;
import co.com.interkont.cobra.planoperativo.client.dto.RubroDTO;
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAble;
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAbleAsync;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.widget.core.client.container.AbstractHtmlLayoutContainer.HtmlData;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel.LabelAlign;
import com.sencha.gxt.widget.core.client.form.FormPanelHelper;
import com.sencha.gxt.widget.core.client.form.TextField;
import java.util.Iterator;

public class ProyectoForm1 implements IsWidget, EntryPoint {

    private TextField nombrePry = new TextField();
    private TextField entidad = new TextField();
    private TextField objetivos = new TextField();
    private TextField tipoAporte = new TextField();
    private TextField tipoRecurso = new TextField();
    private TextArea objetivoGeneral = new TextArea();
    private TextArea objetivoEspecifico = new TextArea();
    private ListBox lstEntidadesConvenio = new ListBox(false);
    private ComboBox<RubroDTO> comboRubros;
    ContratoDTO contratoDto;
    private CobraGwtServiceAbleAsync service = GWT.create(CobraGwtServiceAble.class);

    public ProyectoForm1(ContratoDTO contratoDtoP) {
        this.contratoDto = contratoDtoP;
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
     * @return the lstEntidadesConvenio
     */
    public ListBox getLstEntidadesConvenio() {
        return lstEntidadesConvenio;
    }

    /**
     * @param lstEntidadesConvenio the lstEntidadesConvenio to set
     */
    public void setLstEntidadesConvenio(ListBox lstEntidadesConvenio) {
        this.lstEntidadesConvenio = lstEntidadesConvenio;
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

    interface RubroProperties extends PropertyAccess<RubroDTO> {

        ModelKeyProvider<RubroDTO> idrubro();

        LabelProvider<RubroDTO> strdescripcion();
    }

    interface ComboBoxTemplates extends XTemplates {

        @XTemplates.XTemplate("<div qtip=\"{id}\" qtitle=\"State Slogan\">{name}</div>")
        SafeHtml rubro(int id, String name);
    }
    private static final int COLUMN_FORM_WIDTH = 500;
    private VerticalPanel vp;

    public Widget asWidget() {
        if (vp == null) {
            vp = new VerticalPanel();
            vp.setSpacing(50);
            vp.setWidth("" + COLUMN_FORM_WIDTH);
            vp.setHorizontalAlignment(vp.ALIGN_CENTER);
            createColumnForm();
        }
        return vp;
    }

    public void onModuleLoad() {
        RootPanel.get().add(asWidget());
    }

    private void createColumnForm() {

        vp.add(new Label("PLANIFICACION DE PROYECTOS"));

        HtmlLayoutContainer con = new HtmlLayoutContainer(getTableMarkup());
        vp.add(con);


        int cw = (COLUMN_FORM_WIDTH / 2) - 12;


        getNombrePry().setText("Nombre del proyecto");
        getNombrePry().setAllowBlank(false);
        getNombrePry().setWidth(cw);
        con.add(new FieldLabel(nombrePry, "//INFORMACIÓN BASICA"), new HtmlData(".fn"));


        llenarComboEntidadesConvenio();
        getLstEntidadesConvenio().setWidth("" + cw);
        con.add(new FieldLabel(getLstEntidadesConvenio(), "ANADIR ROLES Y ENTIDADES"), new HtmlData(".entidad"));

        tipoRecurso.setText("Tipo recurso");
        tipoRecurso.setAllowBlank(false);
        tipoRecurso.setWidth(cw);
        con.add(tipoRecurso, new HtmlData(".tipor"));

        getObjetivoGeneral().setVisibleLines(15);
        getObjetivoGeneral().setWidth("" + cw);
        getObjetivoGeneral().setText("General");
        con.add(new FieldLabel(getObjetivoGeneral(), "OBJETIVOS"), new HtmlData(".objetivog"));

        getObjetivoEspecifico().setVisibleLines(15);
        getObjetivoEspecifico().setWidth("" + cw);
        getObjetivoEspecifico().setText("Especifico");
        con.add(getObjetivoEspecifico(), new HtmlData(".objetivoes"));

        RubroProperties props = GWT.create(RubroProperties.class);
        final ListStore<RubroDTO> rubros = new ListStore<RubroDTO>(props.idrubro());

        llenarListaRubros(rubros);
                
        setComboRubros(new ComboBox<RubroDTO>(rubros, props.strdescripcion()));
        getComboRubros().setEmptyText("Seleccione Rubro");
        getComboRubros().setWidth(cw);
        getComboRubros().setTypeAhead(true);
        getComboRubros().setTriggerAction(TriggerAction.ALL);
        con.add(getComboRubros(), new HtmlData(".email"));






        // need to call after everything is constructed
        List<FieldLabel> labels = FormPanelHelper.getFieldLabels(vp);
        for (FieldLabel lbl : labels) {
            lbl.setLabelAlign(LabelAlign.TOP);
        }

    }

//    private native String getTableMarkup() /*-{
//     return [ '<table width=100% cellpadding=10 cellspacing=20>',
//     '<tr><td class=fn width=50%></td><td class=ln width=50%></td></tr>',
//     '<tr><td class=company></td><td class=email></td></tr>',
//     '<tr><td class=birthday></td><td class=user></td></tr>',
//     '<tr><td class=editor colspan=2></td></tr>', '</table>'
// 
//     ].join("");
//     }-*/;
    /**
     * @return the nombrePry
     */
    public TextField getNombrePry() {
        return nombrePry;
    }

    private native String getTableMarkup() /*-{
     return [ '<table width=100% cellpadding=10 cellspacing=20>',
     '<tr><td class=fn width=50%></td><td width=50%><table><tr><td class=entidad></td></tr><tr><td class=tipor></td></tr></table></td></tr>',
     '<tr><td><table><tr><td class=objetivog></td><tr><tr><td class=objetivoes></td><tr></table></td> <td class=email></td></tr>',
     '<tr><td class=birthday></td><td class=user></td></tr>',
     '<tr><td class=editor colspan=2></td></tr>', '</table>'
 
     ].join("");
     }-*/;

    /**
     * @param nombrePry the nombrePry to set
     */
    public void setNombrePry(TextField nombrePry) {
        this.nombrePry = nombrePry;
    }

    /**
     * @return the entidad
     */
    public TextField getEntidad() {
        return entidad;
    }

    /**
     * @param entidad the entidad to set
     */
    public void setEntidad(TextField entidad) {
        this.entidad = entidad;
    }

    /**
     * @return the objetivos
     */
    public TextField getObjetivos() {
        return objetivos;
    }

    /**
     * @param objetivos the objetivos to set
     */
    public void setObjetivos(TextField objetivos) {
        this.objetivos = objetivos;
    }

    /**
     * @return the tipoAporte
     */
    public TextField getTipoAporte() {
        return tipoAporte;
    }

    /**
     * @param tipoAporte the tipoAporte to set
     */
    public void setTipoAporte(TextField tipoAporte) {
        this.tipoAporte = tipoAporte;
    }

    public void llenarComboEntidadesConvenio() {
        int posFuenteRecurso = 0;
        for (Iterator it = contratoDto.getFuenterecursosconvenios().iterator(); it.hasNext();) {
            FuenterecursosconvenioDTO fuenteRecursosDTO = (FuenterecursosconvenioDTO) it.next();
            getLstEntidadesConvenio().addItem(fuenteRecursosDTO.getTercero().getStrnombrecompleto(), "" + posFuenteRecurso);
            posFuenteRecurso++;
        }
    }

    public void llenarListaRubros(final ListStore<RubroDTO> rubros) {
        service.obtenerRubros(new AsyncCallback<List>() {
            @Override
            public void onFailure(Throwable caught) {
                service.setLog("Error al cargar los rubros", null);
            }

            @Override
            public void onSuccess(List result) {
                 rubros.addAll(result);
               
            }
        });

    }
}