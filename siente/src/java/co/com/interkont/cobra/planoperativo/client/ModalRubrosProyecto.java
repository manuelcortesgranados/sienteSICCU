/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client;

import co.com.interkont.cobra.planoperativo.client.dto.ContratoDTO;
import co.com.interkont.cobra.planoperativo.client.dto.FuenterecursosconvenioDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ObraDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ObrafuenterecursosconveniosDTO;
import co.com.interkont.cobra.planoperativo.client.dto.RubroDTO;
import co.com.interkont.cobra.planoperativo.client.dto.TerceroDTO;
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAble;
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAbleAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.container.AbstractHtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.NumberField;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor;
import com.sencha.gxt.widget.core.client.form.TextField;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Daniela
 */
public class ModalRubrosProyecto implements IsWidget {

    private ComboBox<TerceroDTO> lstEntidadesConvenio;
    EntidadProperties propse = GWT.create(EntidadProperties.class);
    final ListStore<TerceroDTO> entidades;
    TerceroDTO terceroDto;
    RubroDTO rubroDto;
    ContratoDTO contratoDto;
    int tipoAporte;
    int vigencia;
    ObraDTO proyectoDTO;
    FuenterecursosconvenioDTO fuenteRecursosConveDTO;
    private VerticalPanel vp;
    private ListBox lstTipoAporte;
    private TextField campoTipoRecurso;
    private TextField rubro;
    private NumberField<BigDecimal> montoAportado;
    private ListBox lstVigen;
    Dialog modalActual;
    int idTemp;
    WidgetTablaRubrosPry tblrubros;
     private CobraGwtServiceAbleAsync service = GWT.create(CobraGwtServiceAble.class);
   

    public ModalRubrosProyecto(ContratoDTO contratoDto, ObraDTO proyectoDTO, int idTemp, Dialog modalActual, WidgetTablaRubrosPry tblrubros) {
        entidades = new ListStore<TerceroDTO>(propse.intcodigo());
        lstEntidadesConvenio = new ComboBox<TerceroDTO>(entidades, propse.strnombrecompleto());
        campoTipoRecurso = new TextField();
        lstTipoAporte = new ListBox(false);
        montoAportado = (NumberField<BigDecimal>) new NumberField(new NumberPropertyEditor.BigDecimalPropertyEditor());
        lstVigen = new ListBox(false);
        rubro = new TextField();

        this.contratoDto = contratoDto;
        this.proyectoDTO = proyectoDTO;
        this.idTemp = idTemp;
        this.modalActual = modalActual;
        this.tblrubros = tblrubros;

    }

    @Override
    public Widget asWidget() {
        vp = new VerticalPanel();
        vp.setSpacing(10);
        vp.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
        vp.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
        crearModalRoles();
        return vp;
    }

    public void crearModalRoles() {
        String cw = "200";
        vp.add(new Label("ROLES Y ENTIDADES"));

        HtmlLayoutContainer con = new HtmlLayoutContainer(getTableMarkup());
        vp.add(con);

        llenarComboEntidadesConvenio(entidades);
        lstEntidadesConvenio.setEmptyText("Entidad");
        lstEntidadesConvenio.setWidth(cw);
        lstEntidadesConvenio.setAllowBlank(false);
        lstEntidadesConvenio.setTypeAhead(true);
        lstEntidadesConvenio.setTriggerAction(ComboBoxCell.TriggerAction.ALL);
        lstEntidadesConvenio.addSelectionHandler(new SelectionHandler<TerceroDTO>() {
            @Override
            public void onSelection(SelectionEvent<TerceroDTO> event) {
                terceroDto = event.getSelectedItem();
                fuenteRecursosConveDTO = buscarFuenteDto(terceroDto.getCampoTemporalFuenteRecursos());
                
            }
        });
        con.add(lstEntidadesConvenio, new AbstractHtmlLayoutContainer.HtmlData(".entidad"));


//        lstTipoAporte.setWidth("" + cw);
//        llenarTipoAporte();
//        lstTipoAporte.addChangeHandler(new ChangeHandler() {
//            @Override
//            public void onChange(ChangeEvent event) {
//                tipoAporte = lstTipoAporte.getSelectedIndex();
//                if (tipoAporte == 0) {
//                    campoTipoRecurso.setEmptyText("Dinero");
//                } else if (tipoAporte == 1) {
//                    campoTipoRecurso.setEmptyText("Especie");
//                }
//            }
//        });
//        con.add(lstTipoAporte, new AbstractHtmlLayoutContainer.HtmlData(".tipor"));
//
//        campoTipoRecurso.setEmptyText("Dinero");
//        campoTipoRecurso.setWidth(cw);
//        con.add(campoTipoRecurso, new AbstractHtmlLayoutContainer.HtmlData(".recursos"));


        rubro.setEmptyText("Rubro");
        rubro.setWidth(cw);
        con.add(rubro, new AbstractHtmlLayoutContainer.HtmlData(".rubro"));


        montoAportado.setEmptyText("Monto aportado");
        montoAportado.setWidth(cw);
        con.add(montoAportado, new AbstractHtmlLayoutContainer.HtmlData(".monto"));

        lstVigen.setWidth(cw);
        llenarV();
        lstVigen.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                vigencia = Integer.parseInt(lstVigen.getItemText(lstVigen.getSelectedIndex()));
            }
        });
        con.add(lstVigen, new AbstractHtmlLayoutContainer.HtmlData(".vigencia"));


        Button botanAddRubros = new Button("Adicionar", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                ObrafuenterecursosconveniosDTO obraFuenteDto = new ObrafuenterecursosconveniosDTO(montoAportado.getValue(), fuenteRecursosConveDTO, rubro.getValue(), idTemp, vigencia);
                String validacionDevuelta = validarMontosAportados(obraFuenteDto);
                if (!validacionDevuelta.equals("El monto ha sido guardado")) {
                    AlertMessageBox d = new AlertMessageBox("Error", validacionDevuelta);
                    d.show();
                }else {
                    tblrubros.getStore().add(obraFuenteDto);
                    modalActual.hide();
                }
                limpiarMontos();
            }
        });

        vp.add(botanAddRubros);


    }

    public void limpiarMontos() {
        //entidades.clear();
        //llenarComboEntidadesConvenio(entidades);
        //llenarListaRubros(rubros);
        this.campoTipoRecurso.setText("");
        this.montoAportado.setText("");
    }

    public String validarMontosAportados(ObrafuenterecursosconveniosDTO obraFuenteDto) {
        if (obraFuenteDto.getValor().compareTo(contratoDto.getValorDisponible()) < 0) {
            if (montoAportado.getValue().compareTo(obraFuenteDto.getFuenterecursosconvenio().getValoraportado()) > 0) {
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
            idTemp++;
            modificarValorDisponible(obraFuenteDto);
            return "El monto ha sido guardado";
        }
        return "El convenio seleccionado no cuenta con valor disponible";
    }

    public void modificarValorDisponible(ObrafuenterecursosconveniosDTO obraFuenteDto) {
        if (proyectoDTO.getValor() == null) {
            proyectoDTO.setValor(BigDecimal.ZERO);
        }
        proyectoDTO.setValor(proyectoDTO.getValor().add(obraFuenteDto.getValor()));
        obraFuenteDto.getFuenterecursosconvenio().setValorDisponible(obraFuenteDto.getFuenterecursosconvenio().getValorDisponible().subtract(obraFuenteDto.getValor()));
        contratoDto.setValorDisponible(contratoDto.getValorDisponible().subtract(obraFuenteDto.getValor()));

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

    public void llenarV() {
        Date ahora = new Date();
        int año = 2013;

        lstVigen.addItem("" + año);
        for (int i = 0; i < 14; i++) {
            año = año + 1;
            lstVigen.addItem("" + año);
        }
    }

    public void llenarTipoAporte() {
        lstTipoAporte.addItem("Dinero", "0");
        lstTipoAporte.addItem("Especie", "1");
    }

    /*
     * metodo que se encarga de buscar la fuente de recursos 
     * que se encuentra en detereminada posicion.
     */
    public FuenterecursosconvenioDTO buscarFuenteDto(int posicion) {
        List<FuenterecursosconvenioDTO> lstFuente=new ArrayList<FuenterecursosconvenioDTO>(contratoDto.getFuenterecursosconvenios());
        return lstFuente.get(posicion);
    }

    /**
     * @return the lstEntidadesConvenio
     */
    public ComboBox<TerceroDTO> getLstEntidadesConvenio() {
        return lstEntidadesConvenio;
    }

    /**
     * @param lstEntidadesConvenio the lstEntidadesConvenio to set
     */
    public void setLstEntidadesConvenio(ComboBox<TerceroDTO> lstEntidadesConvenio) {
        this.lstEntidadesConvenio = lstEntidadesConvenio;
    }

    private native String getTableMarkup() /*-{
     return ['<table width=100% cellpadding=0 cellspacing=10>',
     '<tr><td class=entidad></td><td class=rubro></td></tr>',
     '<tr><td class=vigencia></td><td class=monto></td></tr>',
     '</table>'
     ].join("");
     }-*/;
}
