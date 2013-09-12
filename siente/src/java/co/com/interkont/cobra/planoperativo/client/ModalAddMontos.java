/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client;

import co.com.interkont.cobra.planoperativo.client.dto.ActividadobraDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ContratoDTO;
import co.com.interkont.cobra.planoperativo.client.dto.MontoDTO;
import co.com.interkont.cobra.planoperativo.client.dto.RubroDTO;
import co.com.interkont.cobra.planoperativo.client.resources.images.ExampleImages;
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAble;
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAbleAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.interkont.cobra.dto.ActividadObraDTO;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.container.AbstractHtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.NumberField;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Daniela
 */
public class ModalAddMontos implements IsWidget {

    /*elementos para crear la pagina visualmente*/
    protected ListBox lstVigen = new ListBox(false);
    protected VerticalPanel vp;
    private ListBox comboCatRubros = new ListBox();
    private ListBox comboRubros = new ListBox();
    protected List<RubroDTO> lstRubrosDto;
    protected NumberField<BigDecimal> valorRubros;
    protected NumberField<BigDecimal> valorContrato;
    protected WidgetTablaMontos widTblMontos;
    /*elementos para cargar los datos ingresados por el usuario*/
    protected RubroDTO rubro;
    protected ContratoDTO contrato;
    protected int vigencia;
    protected BigDecimal valorAuxiliar;
    protected ActividadobraDTO actividadObraPadre;
    protected List<MontoDTO> lstMontos;
    protected Window modalActual;
    protected int idTemp;
    protected CobraGwtServiceAbleAsync service = GWT.create(CobraGwtServiceAble.class);

    public ModalAddMontos(ContratoDTO contrato, NumberField<BigDecimal> valorContrato, BigDecimal valorAuxiliar, ActividadobraDTO actividadObraPadre, WidgetTablaMontos widTblMontos, Window modalActual, int idTemp) {
        lstRubrosDto = new ArrayList<RubroDTO>();
        lstMontos = new ArrayList<MontoDTO>(contrato.getMontos());
        this.contrato = contrato;
        this.valorContrato = valorContrato;
        this.valorAuxiliar = valorAuxiliar;
        this.actividadObraPadre = actividadObraPadre;
        this.widTblMontos = widTblMontos;
        this.modalActual = modalActual;
        this.idTemp = idTemp;


    }

    @Override
    public Widget asWidget() {
        if (vp == null) {
            vp = new VerticalPanel();
            vp.setSpacing(10);
            vp.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
            vp.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
            vp.setStyleName("ikont-po-tb");
            crearModalMontos();
        }
        return vp;
    }

    public void crearModalMontos() {
        Label tituloPagina = new Label("Rubros");
        tituloPagina.setStyleName("ikont-po-label");
        vp.add(tituloPagina);

        String cw = "150";

        HtmlLayoutContainer con = new HtmlLayoutContainer(getTableMarkup());
        vp.add(con);
        lstVigen.setWidth(cw);
        llenarV();
        vigencia = Integer.parseInt(lstVigen.getItemText(0));
        lstVigen.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                vigencia = Integer.parseInt(lstVigen.getItemText(lstVigen.getSelectedIndex()));
            }
        });
        con.add(new FieldLabel(lstVigen, "Vigencia"), new AbstractHtmlLayoutContainer.HtmlData(".vigencia"));
        //con.add(lstVigen, new AbstractHtmlLayoutContainer.HtmlData(".vigencia"));

        getComboCatRubros().setWidth("" + cw);
        getComboRubros().setWidth("" + cw);
        con.add(new FieldLabel(getComboCatRubros(), "Rubros")  , new AbstractHtmlLayoutContainer.HtmlData(".rubrocont"));
        con.add(new FieldLabel(getComboRubros(), "Categorias"), new AbstractHtmlLayoutContainer.HtmlData(".rubrosub"));
//        con.add(getComboCatRubros(), new AbstractHtmlLayoutContainer.HtmlData(".rubrocont"));
//        con.add(getComboRubros(), new AbstractHtmlLayoutContainer.HtmlData(".rubrosub"));
        this.llenarCategorias();
        getComboCatRubros().setSelectedIndex(0);
        llenarRubroslista("123102");
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

        this.vigencia = Integer.parseInt(lstVigen.getItemText(0));

        setValorRubros((NumberField<BigDecimal>) new NumberField(new NumberPropertyEditor.BigDecimalPropertyEditor()));
        getValorRubros().setEmptyText("Valor");
        getValorRubros().setWidth(cw);
        con.add(new FieldLabel(getValorRubros(), "Valor"), new AbstractHtmlLayoutContainer.HtmlData(".valorubro"));
        //con.add(getValorRubros(), new AbstractHtmlLayoutContainer.HtmlData(".valorubro"));

         Button btnAdicionarRubros = new Button("Adicionar Rubro", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                MontoDTO monto = new MontoDTO(rubro, getValorRubros().getValue(), vigencia, idTemp, rubro.getStrdescripcion());
                String msgVal = validaRubros(monto);
                if (msgVal.equals("El rubro se registró correctamente")) {
                    contrato.getMontos().add(monto);
                    // actividadObraPadre.getObra().setValorDisponible(actividadObraPadre.getObra().getValorDisponible().subtract(monto.getValor()));
                    limpiarMontos();
                    idTemp = idTemp++;
                    widTblMontos.getStore().add(monto);
                    modalActual.hide();
                } else {
                    AlertMessageBox d = new AlertMessageBox("Error", msgVal);
                    d.show();
                }

            }
        });

        con.add(btnAdicionarRubros);

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

    public void limpiarMontos() {
        this.llenarCategorias();
        llenarRubroslista("123102");
        this.getValorRubros().clear();
        this.getValorRubros().setEmptyText("Valor");

    }

    private native String getTableMarkup() /*-{
     return [ '<table width=100% cellpadding=0 cellspacing=10>',
     '<tr><td class=rubrocont></td><td class=rubrosub></td></tr>',
     '<tr><td class=valorubro></td><td><table><tr><td class=vigencia></td></tr></table></td></tr>',
     '</table>'
     ].join("");
     }-*/;

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
                getComboRubros().setSelectedIndex(0);
                rubro = lstRubrosDto.get(getComboRubros().getSelectedIndex());
            }
        });

    }

    public String validaRubros(MontoDTO montoDto) {
        String msgVal = "";
        if (valorContrato.getValue() == null) {
            msgVal += "*Ingrese el valor del contrato" + "<br/>";
        } else {
            if (valorContrato.getValue().compareTo(actividadObraPadre.getObra().getValorDisponible()) <= 0) {
                valorAuxiliar = valorContrato.getValue();
                if (getValorRubros().getValue() != null) {
                    if (getValorRubros().getValue().compareTo(valorContrato.getValue()) <= 0) {
                        service.setLog("entreeeee", null);
                        if (!contrato.getMontos().isEmpty()) {
                            service.setLog("entre 1", null);
                            BigDecimal sumMontos = sumarRubros();
                            sumMontos = sumMontos.add(montoDto.getValor());
                            if (sumMontos.compareTo(valorContrato.getValue()) > 0) {
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
}
