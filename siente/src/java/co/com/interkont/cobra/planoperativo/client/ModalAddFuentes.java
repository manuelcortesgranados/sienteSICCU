/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client;

import co.com.interkont.cobra.planoperativo.client.dto.ActividadobraDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ContratoDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ObrafuenterecursosconveniosDTO;
import co.com.interkont.cobra.planoperativo.client.dto.RelacionobrafuenterecursoscontratoDTO;
import co.com.interkont.cobra.planoperativo.client.dto.TerceroDTO;
import com.google.gwt.core.client.EntryPoint;
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
import com.sencha.gxt.widget.core.client.event.BlurEvent;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.NumberField;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor;
import java.math.BigDecimal;
import java.util.Iterator;

/**
 *
 * @author Daniela
 */
public class ModalAddFuentes implements IsWidget, EntryPoint {

    private VerticalPanel vp;
    EntidadProperties propse = GWT.create(EntidadProperties.class);
    protected final ListStore<TerceroDTO> entidades = new ListStore<TerceroDTO>(propse.intcodigo());
    /*ActividadObra seleccionada*/
    ActividadobraDTO actividadObraPadre;
    TerceroDTO terceroDto;
    ObrafuenterecursosconveniosDTO obraFrDto;
    ContratoDTO contrato;
    private ComboBox<TerceroDTO> lstTerceros;
    private ListBox lstFormaP = new ListBox(false);
    private NumberField<BigDecimal> valorFuenteRecurso;
    //si forma de pago es uno es valor si es dos es porcentaje
    int formaPago;
    WidgetTablaFuenteR modal;
    Dialog modalActual;
    int cont;
    int idTemp;

    public ModalAddFuentes(ActividadobraDTO actividadObraPadre, ContratoDTO contrato, WidgetTablaFuenteR tabla, Dialog modalActual,int idTemp) {
        this.actividadObraPadre = actividadObraPadre;
        this.contrato = contrato;
        this.modal = tabla;
        this.modalActual=modalActual;
        this.idTemp=idTemp;
    }

    @Override
    public Widget asWidget() {
           vp = new VerticalPanel();
            vp.setSpacing(10);
            vp.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
            vp.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
            crearFormulario();
            return vp;
    }

    public void crearFormulario() {
        vp.add(new Label("Fuente Recursos"));
        HtmlLayoutContainer con = new HtmlLayoutContainer(getTableMarkup());
        vp.add(con);

        int cw = 238;
        llenarFuenteRecursosContrato(entidades);
        lstTerceros = (new ComboBox<TerceroDTO>(entidades, propse.strnombrecompleto()));
        lstTerceros.setEmptyText("Entidad");
        lstTerceros.setWidth(cw);
        lstTerceros.setTypeAhead(true);
        lstTerceros.setTriggerAction(ComboBoxCell.TriggerAction.ALL);
        lstTerceros.addSelectionHandler(new SelectionHandler<TerceroDTO>() {
            @Override
            public void onSelection(SelectionEvent<TerceroDTO> event) {
                terceroDto = event.getSelectedItem();
                obraFrDto = buscarFuenteDto(terceroDto.getCampoTemporalFuenteRecursos());

            }
        });
        con.add(lstTerceros, new AbstractHtmlLayoutContainer.HtmlData(".entidad"));

        lstFormaP.setWidth("" + cw);
        llenarFormaPa();
        lstFormaP.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                formaPago = lstFormaP.getSelectedIndex();
                if (formaPago == 1) {
                    valorFuenteRecurso.setEmptyText("Porcentaje");
                } else if (formaPago == 0) {
                    valorFuenteRecurso.setEmptyText("Valor");
                }
            }
        });
        con.add(lstFormaP, new AbstractHtmlLayoutContainer.HtmlData(".formapago"));

        valorFuenteRecurso = ((NumberField<BigDecimal>) new NumberField(new NumberPropertyEditor.BigDecimalPropertyEditor()));
        valorFuenteRecurso.setEmptyText("Valor");
        valorFuenteRecurso.setWidth(cw);
        valorFuenteRecurso.addBlurHandler(new BlurEvent.BlurHandler() {
            @Override
            public void onBlur(BlurEvent event) {
                if (formaPago == 1) {
                    if (valorFuenteRecurso.getValue().compareTo(new BigDecimal("100")) > 0) {
                        valorFuenteRecurso.setValue(BigDecimal.ZERO);
                        AlertMessageBox d = new AlertMessageBox("Error", "El porcentaje ingresado no puede superar el 100%");
                        d.show();
                    }
                }
            }
        });
        con.add(valorFuenteRecurso, new AbstractHtmlLayoutContainer.HtmlData(".valorfuente"));

        Button btnadd = new Button("Adicionar", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                RelacionobrafuenterecursoscontratoDTO rofr;
                if (formaPago == 1) {
                    BigDecimal valorP = obraFrDto.getValor().multiply(valorFuenteRecurso.getValue()).divide(new BigDecimal(100));
                    rofr = new RelacionobrafuenterecursoscontratoDTO(idTemp,obraFrDto, valorP, formaPago,obraFrDto.getFuenterecursosconvenio().getTercero().getStrnombrecompleto(),"Porcentaje");
                    idTemp=idTemp++;
                } else {
                    rofr = new RelacionobrafuenterecursoscontratoDTO(idTemp,obraFrDto, valorFuenteRecurso.getValue(), formaPago,obraFrDto.getFuenterecursosconvenio().getTercero().getStrnombrecompleto(),"Valor");
                    idTemp=idTemp++;
                }
                String validacionDevuelta = validarFuenteRecurso(rofr);
                if (!validacionDevuelta.equals("La fuente ha sido guardada")) {
                    AlertMessageBox d = new AlertMessageBox("Error", validacionDevuelta);
                    d.show();
                } else {
                     limpiarFuentes();
                     modalActual.hide();


                }


            }
        });

        vp.add(btnadd);


    }

    public void limpiarFuentes() {
        valorFuenteRecurso.clear();
        valorFuenteRecurso.setEmptyText("Valor");
        entidades.clear();
        llenarFuenteRecursosContrato(entidades);
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
            modal.getStore().add(relacionFuente);
            return "La fuente ha sido guardada";
        }

    }

    public void modificarValorDisponible(RelacionobrafuenterecursoscontratoDTO relacionFuente) {
        relacionFuente.getObrafuenterecursosconvenios().setValorDisponible(relacionFuente.getObrafuenterecursosconvenios().getValorDisponible().subtract(relacionFuente.getValor()));
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

    public ObrafuenterecursosconveniosDTO buscarFuenteDto(int posicion) {
        int i = 0;
        for (Iterator it = actividadObraPadre.getObra().getObrafuenterecursosconvenioses().iterator(); it.hasNext();) {
            if (posicion == i) {
                ObrafuenterecursosconveniosDTO frc = (ObrafuenterecursosconveniosDTO) it.next();
                return frc;
            }
            i++;
        }
        return null;
    }

    public void llenarFormaPa() {
        lstFormaP.addItem("Valor", "1");
        lstFormaP.addItem("Porcentaje", "2");
    }

    @Override
    public void onModuleLoad() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private native String getTableMarkup() /*-{
     return [ '<table width=100% cellpadding=0 cellspacing=10>',
     '<tr><td class=entidad></td><td class=formapago></td></tr>',
     '<tr><td class=valorfuente></td></tr>',  
     '</table>'
     ].join("");
     }-*/;
}
