/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client;

import co.com.interkont.cobra.planoperativo.client.dto.ContratoDTO;
import co.com.interkont.cobra.planoperativo.client.dto.FuenterecursosconvenioDTO;
import co.com.interkont.cobra.planoperativo.client.dto.GanttDatos;
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
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.container.AbstractHtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.event.BlurEvent;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.NumberField;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.TextField;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Daniela
 */
public class ModalRubrosProyecto implements IsWidget {

    protected ComboBox<TerceroDTO> lstEntidadesConvenio;
    protected VerticalPanel vp;
    protected ListBox lstTipoAporte;
    protected ListBox lstFormaP;
    protected ListBox lstE;
    protected TextArea campoTipoRecurso;
    protected TextField rubro;
    protected NumberField<BigDecimal> montoAportado;
    protected NumberField<BigDecimal> valorDisponibleFuente;
    protected ListBox lstVigen;
    private Window modalActual;
    EntidadProperties propse = GWT.create(EntidadProperties.class);
    protected final ListStore<TerceroDTO> entidades;
    protected TerceroDTO terceroDto;
    protected RubroDTO rubroDto;
    protected ContratoDTO contratoDto;
    protected int tipoAporte;
    protected int formaPago;
    protected int vigencia;
    protected ObraDTO proyectoDTO;
    protected FuenterecursosconvenioDTO fuenteRecursosConveDTO;
    protected int idTemp;
    protected int idTempObraRecurso;
    protected WidgetTablaRubrosPry tblrubros;
    private CobraGwtServiceAbleAsync service = GWT.create(CobraGwtServiceAble.class);
    protected HashMap<String, List<Integer>> mapaRelacionEntidadVigencias;
    protected String entidadSeleccionada;
    protected boolean editar;
    protected FieldLabel formapago;
    protected FieldLabel montoaportado;
    protected FieldLabel descripcionaporte;
    protected FieldLabel valordisponible;

    public ModalRubrosProyecto(ContratoDTO contratoDto, ObraDTO proyectoDTO, int idTemp, Window modalActual, WidgetTablaRubrosPry tblrubros, int idTempObraRecurso, boolean editar) {
        entidades = new ListStore<TerceroDTO>(propse.intcodigo());
        lstEntidadesConvenio = new ComboBox<TerceroDTO>(entidades, propse.strnombrecompleto());
        campoTipoRecurso = new TextArea();
        campoTipoRecurso.setWidth(150);
        //campoTipoRecurso.setEnabled(false);
        lstTipoAporte = new ListBox(false);
        lstFormaP = new ListBox(false);
        lstFormaP.setEnabled(true);
        montoAportado = (NumberField<BigDecimal>) new NumberField(new NumberPropertyEditor.BigDecimalPropertyEditor(NumberFormat.getDecimalFormat()));
        montoAportado.setEnabled(true);
        lstVigen = new ListBox(false);
        rubro = new TextField();
        tipoAporte = 0;
        formaPago = 0;
        lstE = new ListBox(false);
        valorDisponibleFuente = ((NumberField<BigDecimal>) new NumberField(new NumberPropertyEditor.BigDecimalPropertyEditor(NumberFormat.getDecimalFormat())));
        Iterator it = contratoDto.getFuenterecursosconvenios().iterator();
        fuenteRecursosConveDTO = (FuenterecursosconvenioDTO) it.next();
        valorDisponibleFuente.setValue(fuenteRecursosConveDTO.getValorDisponible());
        entidadSeleccionada = fuenteRecursosConveDTO.getTercero().getStrnombrecompleto();

        this.contratoDto = contratoDto;
        this.proyectoDTO = proyectoDTO;
        this.idTemp = idTemp;
        this.modalActual = modalActual;
        this.tblrubros = tblrubros;
        this.idTempObraRecurso = idTempObraRecurso;
        this.editar = editar;

        mapaRelacionEntidadVigencias = new HashMap<String, List<Integer>>();

    }

    @Override
    public Widget asWidget() {
        vp = new VerticalPanel();
        vp.setSpacing(10);
        vp.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
        vp.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
        vp.setStyleName("ikont-po-tb");
        crearModalRubros();

        return vp;

    }

    public void crearModalRubros() {
        final String cw = "200";
        Label tituloPagina = new Label("FINANCIAMIENTO DEL PROYECTO");
        tituloPagina.setStyleName("ikont-po-label");
        vp.add(tituloPagina);

        HtmlLayoutContainer con = new HtmlLayoutContainer(getTableMarkup());
        vp.add(con);

        llenarComboEntidadesConvenio();
        lstE.setWidth(cw);
        lstE.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                entidadSeleccionada = lstE.getValue(lstE.getSelectedIndex());
                List<Integer> lstVigenciasEntidad = mapaRelacionEntidadVigencias.get(entidadSeleccionada);
                llenarV(lstVigenciasEntidad);
                fuenteRecursosConveDTO = buscarFuenteRecursosDto(entidadSeleccionada, vigencia);
                valorDisponibleFuente.setValue(fuenteRecursosConveDTO.getValorDisponible());
            }
        });

        con.add(new FieldLabel(lstE, "Entidad"), new AbstractHtmlLayoutContainer.HtmlData(".entidad"));

        montoAportado.setEmptyText("Monto aportado");
        montoAportado.setWidth(cw);
        montoAportado.addBlurHandler(new BlurEvent.BlurHandler() {
            @Override
            public void onBlur(BlurEvent event) {
                if (formaPago == 1) {
                    if (montoAportado.getValue().compareTo(new BigDecimal(100)) > 0) {
                        AlertMessageBox d = new AlertMessageBox("Error", "el porcentaje ingresado no puede superar el 100%");
                        d.show();
                        montoAportado.clear();
                    }
                }
            }
        });
        montoaportado = new FieldLabel(montoAportado, "Monto");
        con.add(montoaportado, new AbstractHtmlLayoutContainer.HtmlData(".monto"));
        campoTipoRecurso.setWidth(cw);
        campoTipoRecurso.setEmptyText("Descripción aporte");
        descripcionaporte = new FieldLabel(campoTipoRecurso, "Descripción aporte");
        con.add(descripcionaporte, new AbstractHtmlLayoutContainer.HtmlData(".especie"));

        lstTipoAporte.setWidth("" + cw);
        llenarTipoAporte();
        lstTipoAporte.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                tipoAporte = lstTipoAporte.getSelectedIndex();
                if (tipoAporte == 0) {
                    montoaportado.setVisible(true);
                    formapago.setVisible(true);
                    descripcionaporte.setVisible(false);
                    valordisponible.setVisible(true);
                    /*montoAportado.setEnabled(true);
                     lstFormaP.setEnabled(true);
                     campoTipoRecurso.setEnabled(false);*/
                } else if (tipoAporte == 1) {
                    montoaportado.setVisible(false);
                    formapago.setVisible(false);
                    descripcionaporte.setVisible(true);
                    valordisponible.setVisible(false);
                    /*
                     campoTipoRecurso.setEnabled(true);
                     montoAportado.setEnabled(false);
                     lstFormaP.setEnabled(false);
                     */
                }
            }
        });
        con.add(new FieldLabel(lstTipoAporte, "Tipo aporte"), new AbstractHtmlLayoutContainer.HtmlData(".tipor"));
        lstFormaP.setWidth("" + cw);
        llenarFormaPa();
        lstFormaP.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                formaPago = lstFormaP.getSelectedIndex();
                if (formaPago == 0) {
                    montoAportado.setEmptyText("Monto aportado");
                } else if (formaPago == 1) {
                    montoAportado.setEmptyText("Porcentaje aportado");
                }
            }
        });
        formapago = new FieldLabel(lstFormaP, "Forma de pago");

        con.add(formapago, new AbstractHtmlLayoutContainer.HtmlData(".formapago"));

        rubro.setEmptyText("Rubro");
        rubro.setWidth(cw);
        con.add(new FieldLabel(rubro, "Rubro"), new AbstractHtmlLayoutContainer.HtmlData(".rubro"));

        lstVigen.setWidth(cw);
        lstVigen.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                vigencia = Integer.parseInt(lstVigen.getItemText(lstVigen.getSelectedIndex()));
                fuenteRecursosConveDTO = buscarFuenteRecursosDto(entidadSeleccionada, vigencia);
                valorDisponibleFuente.setValue(fuenteRecursosConveDTO.getValorDisponible());
            }
        });
        con.add(new FieldLabel(lstVigen, "Vigencia"), new AbstractHtmlLayoutContainer.HtmlData(".vigencia"));

        valorDisponibleFuente.setEmptyText("Valor disponible");
        valorDisponibleFuente.setWidth(cw);
        valorDisponibleFuente.setEnabled(false);
        valordisponible = new FieldLabel(valorDisponibleFuente, "Valor disponible");
        con.add(valordisponible, new AbstractHtmlLayoutContainer.HtmlData(".valordis"));

        Button botanAddRubros = new Button("Adicionar", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                ObrafuenterecursosconveniosDTO obraFuenteDto = null;
                String validacionDevuelta = "";
                if (validarObraFuenteConIgualEntidadViencia(entidadSeleccionada, vigencia)) {
                    fuenteRecursosConveDTO = buscarFuenteRecursosDto(entidadSeleccionada, vigencia);
                    if (fuenteRecursosConveDTO != null) {
                        if (tipoAporte == 0) {
                            String requeridos = validarRequeridos();
                            if (formaPago == 0) {
                                if (requeridos.equals("")) {
                                    obraFuenteDto = new ObrafuenterecursosconveniosDTO(montoAportado.getValue(), fuenteRecursosConveDTO, rubro.getValue(), idTemp, vigencia, tipoAporte, formaPago, idTempObraRecurso);
                                    obraFuenteDto.setValorFormato(GanttDatos.obtenerFormatoNumero(obraFuenteDto.getValorDisponible()));
                                    validacionDevuelta = validarMontosAportados(obraFuenteDto);
                                } else {
                                    validacionDevuelta = requeridos;
                                }
                            } else {
                                if (requeridos.equals("")) {
                                    obraFuenteDto = new ObrafuenterecursosconveniosDTO(calcularValor(), fuenteRecursosConveDTO, rubro.getValue(), idTemp, vigencia, montoAportado.getValue(), tipoAporte, formaPago);
                                    obraFuenteDto.setValorFormato(GanttDatos.obtenerFormatoNumero(obraFuenteDto.getValorDisponible()));
                                    validacionDevuelta = validarMontosAportados(obraFuenteDto);
                                } else {
                                    validacionDevuelta = requeridos;
                                }
                            }
                        } else {
                            validacionDevuelta = validarEspecieAportada();
                            if (validacionDevuelta.equals("El monto ha sido guardado")) {
                                obraFuenteDto = new ObrafuenterecursosconveniosDTO(campoTipoRecurso.getValue(), fuenteRecursosConveDTO, rubro.getValue(), idTemp, vigencia, idTempObraRecurso);
                                obraFuenteDto.setValor(BigDecimal.ZERO);
                                obraFuenteDto.setValorDisponible(BigDecimal.ZERO);
                                proyectoDTO.getObrafuenterecursosconvenioses().add(obraFuenteDto);
                                idTempObraRecurso++;
                                idTemp++;
                            }
                        }
                        if (!validacionDevuelta.equals("El monto ha sido guardado")) {
                            montoAportado.setEmptyText("Monto aportado");
                            campoTipoRecurso.setEmptyText("Descripción aporte");
                            AlertMessageBox d = new AlertMessageBox("Error", validacionDevuelta);
                            d.show();
                        } else {
                            tblrubros.getStore().add(obraFuenteDto);
                            modalActual.hide();
                        }
                        limpiarMontos();
                    }
                } else {
                    AlertMessageBox d = new AlertMessageBox("Alerta", "La entidad seleccionada ya se encuentra asociada a esta vigencia");
                    d.show();
                }
            }
        });
        montoaportado.setVisible(true);
        formapago.setVisible(true);
        descripcionaporte.setVisible(false);
        valordisponible.setVisible(true);

        vp.add(botanAddRubros);

    }

    public boolean validarObraFuenteConIgualEntidadViencia(String nombreEntidad, Integer vigencia) {
        if (tipoAporte == 0) {
            for (ObrafuenterecursosconveniosDTO obraFuenteRecurso : tblrubros.getStore().getAll()) {
                if (obraFuenteRecurso.getFuenterecursosconvenio().getTercero().getStrnombrecompleto().equals(nombreEntidad) && obraFuenteRecurso.getFuenterecursosconvenio().getVigencia().equals(vigencia)) {
                    return false;
                }
            }
        }
        return true;

    }

    public BigDecimal calcularValor() {
        BigDecimal multi = fuenteRecursosConveDTO.getValoraportado().multiply(montoAportado.getValue());
        return multi.divide(new BigDecimal(100));

    }

    public void limpiarMontos() {
        //entidades.clear();
        //llenarComboEntidadesConvenio(entidades);
        //llenarListaRubros(rubros);
        this.campoTipoRecurso.setText("");
        this.montoAportado.setText("");
    }

    public String validarRequeridos() {
        String msgVali = "";
        if (montoAportado.getValue() == null) {
            msgVali += "*Por favor ingrese el valor aportado" + "<br/>";
        }
        if (fuenteRecursosConveDTO == null) {
            msgVali += "*Por favor seleccione una entidad" + "<br/>";
        }
        if (rubro.getValue() == null) {
            msgVali += "*Por favor ingrese el rubro" + "<br/>";
        }
        return msgVali;

    }

    public String validarEspecieAportada() {
        String msgVali = "";
        if (fuenteRecursosConveDTO == null) {
            msgVali += "*Por favor seleccione una entidad" + "<br/>";
        }
        if (rubro.getValue() == null) {
            msgVali += "*Por favor ingrese el rubro" + "<br/>";
        }
        if (campoTipoRecurso.getValue() == null) {
            msgVali += "*Por favor ingrese la descripcion del aporte" + "<br/>";
        }
        if (msgVali.equals("")) {
            msgVali += "El monto ha sido guardado";
        }
        return msgVali;
    }

    public String validarMontosAportados(ObrafuenterecursosconveniosDTO obraFuenteDto) {
        if (obraFuenteDto.getFuenterecursosconvenio().getValorDisponible().compareTo(BigDecimal.ZERO) != 0) {
            if (obraFuenteDto.getValor().compareTo(obraFuenteDto.getFuenterecursosconvenio().getValorDisponible()) > 0) {
                return "El monto ingresado supera el valor disponible de la fuente de recursos:" + obraFuenteDto.getFuenterecursosconvenio().getValorDisponible();
            }
        } else {
            return "La Fuente de recursos seleccionada no cuenta con valor disponible";
        }
        proyectoDTO.getObrafuenterecursosconvenioses().add(obraFuenteDto);
        idTemp++;
        idTempObraRecurso++;
        modificarValorDisponible(obraFuenteDto);
        return "El monto ha sido guardado";
    }

    public void modificarValorDisponible(ObrafuenterecursosconveniosDTO obraFuenteDto) {
        if (proyectoDTO.getValor() == null) {
            proyectoDTO.setValor(BigDecimal.ZERO);
        }
        proyectoDTO.setValor(proyectoDTO.getValor().add(obraFuenteDto.getValor()));
        FuenterecursosconvenioDTO fuenteRecursos = GanttDatos.buscarFuenteRecursos(obraFuenteDto.getFuenterecursosconvenio().getTercero().getStrnombrecompleto(), obraFuenteDto.getFuenterecursosconvenio().getVigencia(), contratoDto);
        fuenteRecursos.setValorDisponible(fuenteRecursos.getValorDisponible().subtract(obraFuenteDto.getValor()));

    }


    /*metodo que se encarga de llenar el combo de entidades
     * con las entidades que tiene el convenio en las fuentes de recursos
     */
    public void llenarComboEntidadesConvenio() {
        for (Iterator it = contratoDto.getFuenterecursosconvenios().iterator(); it.hasNext();) {
            FuenterecursosconvenioDTO fuenteRecursosDTO = (FuenterecursosconvenioDTO) it.next();
            TerceroDTO tercero = fuenteRecursosDTO.getTercero();
            if (mapaRelacionEntidadVigencias.size() > 0) {
                if (mapaRelacionEntidadVigencias.containsKey(tercero.getStrnombrecompleto())) {
                    List<Integer> lstVigenciasEntidades = mapaRelacionEntidadVigencias.get(tercero.getStrnombrecompleto());
                    lstVigenciasEntidades.add(fuenteRecursosDTO.getVigencia());
                    mapaRelacionEntidadVigencias.put(tercero.getStrnombrecompleto(), lstVigenciasEntidades);
                } else {
                    List<Integer> lstVigenciasEntidades = new ArrayList<Integer>();
                    lstVigenciasEntidades.add(fuenteRecursosDTO.getVigencia());
                    mapaRelacionEntidadVigencias.put(tercero.getStrnombrecompleto(), lstVigenciasEntidades);
                }
            } else {
                List<Integer> lstVigenciasEntidades = new ArrayList<Integer>();
                lstVigenciasEntidades.add(fuenteRecursosDTO.getVigencia());
                mapaRelacionEntidadVigencias.put(tercero.getStrnombrecompleto(), lstVigenciasEntidades);
            }

        }
        int c = 0;
        for (String entidad : mapaRelacionEntidadVigencias.keySet()) {
            if (c == 0) {
                entidadSeleccionada = entidad;
                List<Integer> lstVigenciasEntidad = mapaRelacionEntidadVigencias.get(entidad);
                llenarV(lstVigenciasEntidad);
            }
            c++;
            lstE.addItem(entidad);
        }
    }

    public void llenarV(List<Integer> lstVigenciasEntidad) {
        lstVigen.clear();
        for (Integer vigen : lstVigenciasEntidad) {
            lstVigen.addItem("" + vigen);
        }
        vigencia = lstVigenciasEntidad.get(0);
    }

    public void llenarTipoAporte() {
        lstTipoAporte.addItem("Dinero", "0");
        lstTipoAporte.addItem("Especie", "1");
    }

    public void llenarFormaPa() {
        lstFormaP.addItem("Valor", "0");
        lstFormaP.addItem("Porcentaje", "1");
    }

    public FuenterecursosconvenioDTO buscarFuenteRecursosDto(String nombreEntidad, Integer vigencia) {
        for (Iterator it = contratoDto.getFuenterecursosconvenios().iterator(); it.hasNext();) {
            FuenterecursosconvenioDTO fuenteRecursosActual = (FuenterecursosconvenioDTO) it.next();
            if (fuenteRecursosActual.getTercero().getStrnombrecompleto().equals(nombreEntidad) && fuenteRecursosActual.getVigencia().equals(vigencia)) {
                return fuenteRecursosActual;
            }
        }
        return null;
    }

    /*
     * metodo que se encarga de buscar la fuente de recursos 
     * que se encuentra en detereminada posicion.
     */
    public FuenterecursosconvenioDTO buscarFuenteDto(int posicion) {
        List<FuenterecursosconvenioDTO> lstFuente = new ArrayList<FuenterecursosconvenioDTO>(contratoDto.getFuenterecursosconvenios());
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
     return ['<table width=100% cellpadding=0 cellspacing=15>',
     '<tr><td class=entidad ></td><td class=rubro></td></tr>',
     '<tr><td class=vigencia></td><td class=tipor></td></tr>',
     '<tr><td class=formapago></td><td class=monto></td></tr>',
     '<tr><td class=especie></td><td class=valordis></td></tr>',
     '</table>'
     ].join("");
     }-*/;
}
