/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client;

import co.com.interkont.cobra.planoperativo.client.dto.ActividadobraDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ContratoDTO;
import co.com.interkont.cobra.planoperativo.client.dto.MontoDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ObrafuenterecursosconveniosDTO;
import co.com.interkont.cobra.planoperativo.client.dto.RelacionobrafuenterecursoscontratoDTO;
import co.com.interkont.cobra.planoperativo.client.dto.RubroDTO;
import co.com.interkont.cobra.planoperativo.client.dto.TerceroDTO;
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAble;
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAbleAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.container.AbstractHtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.NumberField;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Daniela
 */
public class ModalAddMontos implements IsWidget {

    /*elementos para crear la pagina visualmente*/
    protected ListBox lstVigen = new ListBox(false);
    protected ListBox lstVigenFuente = new ListBox(false);
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
    protected int vigenciafuente;
    protected BigDecimal valorAuxiliar;
    protected ActividadobraDTO actividadObraPadre;
    protected List<MontoDTO> lstMontos;
    protected Window modalActual;
    protected int idTemp;
    protected ListBox lstE;
    protected CobraGwtServiceAbleAsync service = GWT.create(CobraGwtServiceAble.class);
    protected HashMap<String, List<Integer>> mapaRelacionEntidadVigencias;
    protected String entidadSeleccionada;
    protected ObrafuenterecursosconveniosDTO obraFrDto;
    private BigDecimal valorContratoO;

    public ModalAddMontos(ContratoDTO contrato, NumberField<BigDecimal> valorContrato, ActividadobraDTO actividadObraPadre, WidgetTablaMontos widTblMontos, Window modalActual, int idTemp) {
        lstRubrosDto = new ArrayList<RubroDTO>();

        lstE = new ListBox(false);
        lstVigenFuente = new ListBox(false);
        this.contrato = contrato;
        this.actividadObraPadre = actividadObraPadre;
        this.widTblMontos = widTblMontos;
        this.modalActual = modalActual;
        this.idTemp = idTemp;
        this.valorContratoO = valorContrato.getValue();
        this.valorContrato = valorContrato;

        Iterator it = actividadObraPadre.getObra().getObrafuenterecursosconvenioses().iterator();
        obraFrDto = (ObrafuenterecursosconveniosDTO) it.next();
        entidadSeleccionada = obraFrDto.getFuenterecursosconvenio().getTercero().getStrnombrecompleto();
        mapaRelacionEntidadVigencias = new HashMap<String, List<Integer>>();


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
        con.add(new FieldLabel(lstVigen, "Vigencia Fonade"), new AbstractHtmlLayoutContainer.HtmlData(".vigencia"));

        getComboCatRubros().setWidth("" + cw);
        getComboRubros().setWidth("" + cw);
        con.add(new FieldLabel(getComboCatRubros(), "Rubros"), new AbstractHtmlLayoutContainer.HtmlData(".rubrocont"));
        con.add(new FieldLabel(getComboRubros(), "Categorias"), new AbstractHtmlLayoutContainer.HtmlData(".rubrosub"));
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

        llenarComboEntidadesConvenio();
        lstE.setWidth(cw);
        lstE.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                entidadSeleccionada = lstE.getValue(lstE.getSelectedIndex());
                List<Integer> lstVigenciasEntidad = mapaRelacionEntidadVigencias.get(entidadSeleccionada);
                llenarVigenciaFuente(lstVigenciasEntidad);
            }
        });

        con.add(new FieldLabel(lstE, "Fuente de recursos"), new AbstractHtmlLayoutContainer.HtmlData(".fuenter"));


        lstVigenFuente.setWidth(cw);
        lstVigenFuente.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                vigenciafuente = Integer.parseInt(lstVigenFuente.getItemText(lstVigenFuente.getSelectedIndex()));
            }
        });
        con.add(new FieldLabel(lstVigenFuente, "Vigencia de la fuente"), new AbstractHtmlLayoutContainer.HtmlData(".vigenciafuente"));


        Button btnAdicionarRubros = new Button("Adicionar Rubro", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (validarObraFuenteConIgualEntidadViencia(entidadSeleccionada, vigencia)) {
                    obraFrDto = buscarFuenteRecursosDto(entidadSeleccionada, vigenciafuente);
                    if (obraFrDto != null) {
                        service.setLog("Valor disponible de obra fuente recursos:" + obraFrDto.getValorDisponible(), null);
                        RelacionobrafuenterecursoscontratoDTO relacionFuenteRecursos = new RelacionobrafuenterecursoscontratoDTO(idTemp, obraFrDto, valorRubros.getValue(), entidadSeleccionada, rubro, vigencia, vigenciafuente, rubro.getStrdescripcion());
                        String validacionDevuelta = validarFuenteRecurso(relacionFuenteRecursos);
                        if (!validacionDevuelta.equals("La fuente ha sido guardada")) {
                            AlertMessageBox d = new AlertMessageBox("Alerta", validacionDevuelta);
                            d.show();
                        } else {
                            limpiarMontos();
                            widTblMontos.getStore().add(relacionFuenteRecursos);
                            modalActual.hide();
                        }
                    }
                } else {
                    AlertMessageBox alerta = new AlertMessageBox("Alerta", "La entidad seleccionada ya se encuentra asociada a esta vigencia");
                    alerta.show();
                }
            }
        });

        con.add(btnAdicionarRubros);

    }

    public void llenarV() {
        final Integer fechaInicio = 2002;
        final Integer fechaFinal = 2027;
        for (int i = fechaInicio; i <= fechaFinal; i++) {
            lstVigen.addItem("" + i);
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
     '<tr><td class=fuenter></td><td class=vigenciafuente></td></tr>',
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

    public void llenarComboEntidadesConvenio() {
        for (Iterator it = actividadObraPadre.getObra().getObrafuenterecursosconvenioses().iterator(); it.hasNext();) {
            ObrafuenterecursosconveniosDTO obraFuenteRecurso = (ObrafuenterecursosconveniosDTO) it.next();
            TerceroDTO tercero = obraFuenteRecurso.getFuenterecursosconvenio().getTercero();
            if (mapaRelacionEntidadVigencias.size() > 0) {
                if (mapaRelacionEntidadVigencias.containsKey(tercero.getStrnombrecompleto())) {
                    List<Integer> lstVigenciasEntidades = mapaRelacionEntidadVigencias.get(tercero.getStrnombrecompleto());
                    lstVigenciasEntidades.add(obraFuenteRecurso.getFuenterecursosconvenio().getVigencia());
                    mapaRelacionEntidadVigencias.put(tercero.getStrnombrecompleto(), lstVigenciasEntidades);
                } else {
                    List<Integer> lstVigenciasEntidades = new ArrayList<Integer>();
                    lstVigenciasEntidades.add(obraFuenteRecurso.getFuenterecursosconvenio().getVigencia());
                    mapaRelacionEntidadVigencias.put(tercero.getStrnombrecompleto(), lstVigenciasEntidades);
                }
            } else {
                List<Integer> lstVigenciasEntidades = new ArrayList<Integer>();
                lstVigenciasEntidades.add(obraFuenteRecurso.getFuenterecursosconvenio().getVigencia());
                mapaRelacionEntidadVigencias.put(tercero.getStrnombrecompleto(), lstVigenciasEntidades);
            }

        }
        int c = 0;
        for (String entidad : mapaRelacionEntidadVigencias.keySet()) {
            if (c == 0) {
                entidadSeleccionada = entidad;
                List<Integer> lstVigenciasEntidad = mapaRelacionEntidadVigencias.get(entidad);
                llenarVigenciaFuente(lstVigenciasEntidad);
            }
            c++;
            lstE.addItem(entidad);
        }
    }

    public ObrafuenterecursosconveniosDTO buscarFuenteRecursosDto(String nombreEntidad, Integer vigencia) {
        for (Iterator it = actividadObraPadre.getObra().getObrafuenterecursosconvenioses().iterator(); it.hasNext();) {
            ObrafuenterecursosconveniosDTO obraFuenteRecurso = (ObrafuenterecursosconveniosDTO) it.next();
            if (obraFuenteRecurso.getFuenterecursosconvenio().getTercero().getStrnombrecompleto().equals(nombreEntidad) && obraFuenteRecurso.getFuenterecursosconvenio().getVigencia().equals(vigencia)) {
                return obraFuenteRecurso;
            }
        }
        return null;
    }

    public void llenarVigenciaFuente(List<Integer> lstVigenciasFuente) {
        lstVigenFuente.clear();
        for (Integer vigen : lstVigenciasFuente) {
            lstVigenFuente.addItem("" + vigen);
        }
        vigenciafuente = lstVigenciasFuente.get(0);
    }

    public boolean validarObraFuenteConIgualEntidadViencia(String nombreEntidad, Integer vigencia) {
        for (RelacionobrafuenterecursoscontratoDTO relacion : widTblMontos.getStore().getAll()) {
            if (relacion.getObrafuenterecursosconvenios().getFuenterecursosconvenio().getTercero().getStrnombrecompleto().equals(nombreEntidad) && relacion.getObrafuenterecursosconvenios().getFuenterecursosconvenio().getVigencia().equals(vigencia)) {
                return false;
            }
        }
        return true;


    }

    public String validarFuenteRecurso(RelacionobrafuenterecursoscontratoDTO relacionFuente) {
        if (relacionFuente.getValor().compareTo(obraFrDto.getValorDisponible()) > 0) {
            return "El valor ingresado supera el valor disponible de la fuente de recursos que aporta esta entidad" + "<br/>";
        } else {

            if (valorContratoO == null) {
                valorContratoO = BigDecimal.ZERO;
            }
            valorContratoO = valorContratoO.add(relacionFuente.getValor());
            valorContrato.setValue(valorContratoO);
            contrato.getRelacionobrafuenterecursoscontratos().add(relacionFuente);
            //modificarValorDisponible(relacionFuente);
            return "La fuente ha sido guardada";
        }

    }

    public void modificarValorDisponible(RelacionobrafuenterecursoscontratoDTO relacionFuente) {
        relacionFuente.getObrafuenterecursosconvenios().setValorDisponible(relacionFuente.getObrafuenterecursosconvenios().getValorDisponible().subtract(relacionFuente.getValor()));
        service.setLog("valor disponible despues de substraer:" + relacionFuente.getObrafuenterecursosconvenios().getValorDisponible(), null);
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

    /**
     * @return the valorContratoO
     */
    public BigDecimal getValorContratoO() {
        return valorContratoO;
    }

    /**
     * @param valorContratoO the valorContratoO to set
     */
    public void setValorContratoO(BigDecimal valorContratoO) {
        this.valorContratoO = valorContratoO;
    }
}
