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
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.container.AbstractHtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.event.BlurEvent;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.NumberField;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sun.imageio.plugins.common.BogusColorSpace;
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

    protected ComboBox<TerceroDTO> lstEntidadesConvenio;
    protected VerticalPanel vp;
    protected ListBox lstTipoAporte;
    protected ListBox lstFormaP;
    protected TextField campoTipoRecurso;
    protected TextField rubro;
    protected NumberField<BigDecimal> montoAportado;
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

    public ModalRubrosProyecto(ContratoDTO contratoDto, ObraDTO proyectoDTO, int idTemp, Window modalActual, WidgetTablaRubrosPry tblrubros,int idTempObraRecurso) {
        entidades = new ListStore<TerceroDTO>(propse.intcodigo());
        lstEntidadesConvenio = new ComboBox<TerceroDTO>(entidades, propse.strnombrecompleto());
        campoTipoRecurso = new TextField();
        campoTipoRecurso.setEnabled(false);
        lstTipoAporte = new ListBox(false);
        lstFormaP = new ListBox(false);
        lstFormaP.setEnabled(true);
        montoAportado = (NumberField<BigDecimal>) new NumberField(new NumberPropertyEditor.BigDecimalPropertyEditor());
        montoAportado.setEnabled(true);
        lstVigen = new ListBox(false);
        rubro = new TextField();
        tipoAporte = 0;
        formaPago = 0;
        

        this.contratoDto = contratoDto;
        this.proyectoDTO = proyectoDTO;
        this.idTemp = idTemp;
        this.modalActual = modalActual;
        this.tblrubros = tblrubros;
        this.idTempObraRecurso=idTempObraRecurso;

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
        final String cw = "200";
        vp.add(new Label("ROLES Y ENTIDADES"));

        HtmlLayoutContainer con = new HtmlLayoutContainer(getTableMarkup());
        vp.add(con);

        llenarComboEntidadesConvenio(entidades);
        lstEntidadesConvenio.setEmptyText("Entidad");
        lstEntidadesConvenio.setWidth(cw);
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

        montoAportado.setEmptyText("Monto aportado");
        montoAportado.setWidth(cw);
        montoAportado.addBlurHandler(new BlurEvent.BlurHandler() {
            @Override
            public void onBlur(BlurEvent event) {
               if(formaPago==1){
               if(montoAportado.getValue().compareTo(new BigDecimal(100))>0){
               AlertMessageBox d = new AlertMessageBox("Error", "el porcentaje ingresado no puede superar el 100%");
               d.show();
               montoAportado.clear();
               }
               }
            }
        });

        con.add(montoAportado, new AbstractHtmlLayoutContainer.HtmlData(".monto"));

        campoTipoRecurso.setWidth(cw);
        campoTipoRecurso.setEmptyText("Descripcion aporte");
        con.add(campoTipoRecurso, new AbstractHtmlLayoutContainer.HtmlData(".especie"));

        con.add(new Label("Forma de pago"), new AbstractHtmlLayoutContainer.HtmlData(".tituloformap"));
        lstTipoAporte.setWidth("" + cw);
        llenarTipoAporte();
        lstTipoAporte.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                tipoAporte = lstTipoAporte.getSelectedIndex();
                if (tipoAporte == 0) {
                    montoAportado.setEnabled(true);
                    lstFormaP.setEnabled(true);
                    campoTipoRecurso.setEnabled(false);
                } else if (tipoAporte == 1) {
                    campoTipoRecurso.setEnabled(true);
                    montoAportado.setEnabled(false);
                    lstFormaP.setEnabled(false);
                }
            }
        });
        con.add(lstTipoAporte, new AbstractHtmlLayoutContainer.HtmlData(".tipor"));

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
        con.add(lstFormaP, new AbstractHtmlLayoutContainer.HtmlData(".formapago"));


        rubro.setEmptyText("Rubro");
        rubro.setWidth(cw);
        con.add(rubro, new AbstractHtmlLayoutContainer.HtmlData(".rubro"));



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
                ObrafuenterecursosconveniosDTO obraFuenteDto = null;
                String validacionDevuelta = "";
                if (tipoAporte == 0) {
                    String requeridos = validarRequeridos();
                    if (formaPago == 0) {
                        if (requeridos.equals("")) {
                            obraFuenteDto = new ObrafuenterecursosconveniosDTO(montoAportado.getValue(), fuenteRecursosConveDTO, rubro.getValue(), idTemp, vigencia, tipoAporte, formaPago,idTempObraRecurso);
                            validacionDevuelta = validarMontosAportados(obraFuenteDto);
                         } else {
                            validacionDevuelta = requeridos;
                        }
                    } else {
                        if (requeridos.equals("")) {
                            obraFuenteDto = new ObrafuenterecursosconveniosDTO(calcularValor(), fuenteRecursosConveDTO, rubro.getValue(), idTemp, vigencia, montoAportado.getValue(), tipoAporte, formaPago);
                            validacionDevuelta = validarMontosAportados(obraFuenteDto);
                        } else {
                            validacionDevuelta = requeridos;
                        }
                    }
                } else {
                    validacionDevuelta = validarEspecieAportada();
                    if (validacionDevuelta.equals("El monto ha sido guardado")) {
                        obraFuenteDto = new ObrafuenterecursosconveniosDTO(campoTipoRecurso.getValue(), fuenteRecursosConveDTO, rubro.getValue(), idTemp, vigencia,idTempObraRecurso);
                        proyectoDTO.getObrafuenterecursosconvenioses().add(obraFuenteDto);
                        idTempObraRecurso++;
                        idTemp++;
                    }
                }
                if (!validacionDevuelta.equals("El monto ha sido guardado")) {
                    montoAportado.setEmptyText("Monto aportado");
                    campoTipoRecurso.setEmptyText("descripción aporte");
                    AlertMessageBox d = new AlertMessageBox("Error", validacionDevuelta);
                    d.show();
                } else {
                    tblrubros.getStore().add(obraFuenteDto);
                    modalActual.hide();
                }
                limpiarMontos();
            }
        });
        
        vigencia = Integer.parseInt(lstVigen.getItemText(0));

        vp.add(botanAddRubros);


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
       if (obraFuenteDto.getValor().compareTo(contratoDto.getValorDisponible()) < 0) {
            if (montoAportado.getValue().compareTo(obraFuenteDto.getFuenterecursosconvenio().getValoraportado()) > 0) {
                return "El monto ingresado supera el valor de la fuente de recursos";
            } else {
                if (!proyectoDTO.getObrafuenterecursosconvenioses().isEmpty()) {
                    BigDecimal sumaValorAportado = BigDecimal.ZERO;
                    for (Object obr : proyectoDTO.getObrafuenterecursosconvenioses()) {
                        ObrafuenterecursosconveniosDTO obrc = (ObrafuenterecursosconveniosDTO) obr;
                        if(obrc.getTipoaporte()==0){
                        sumaValorAportado = sumaValorAportado.add(obrc.getValor());
                        }
                    }
                    sumaValorAportado = sumaValorAportado.add(obraFuenteDto.getValor());
                    if (sumaValorAportado.compareTo(contratoDto.getNumvlrcontrato()) > 0) {
                        return "Monto no registrado, la suma de los montos aportados supera el valor del convenio";
                    }
                }
            }
            proyectoDTO.getObrafuenterecursosconvenioses().add(obraFuenteDto);
            idTemp++;
            idTempObraRecurso++;
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

    public void llenarFormaPa() {
        lstFormaP.addItem("Valor", "0");
        lstFormaP.addItem("Porcentaje", "1");
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
     return ['<table width=100% cellpadding=0 cellspacing=10>',
     '<tr><td class=entidad></td><td class=rubro></td></tr>',
     '<tr><td class=vigencia></td></tr>',
     '<tr><td class=tituloformap></td></tr>',
     '<tr><td class=tipor></td><td class=formapago></td></tr>',
     '<tr><td class=monto></td><td class=especie></td></tr>',
     '</table>'
     ].join("");
     }-*/;
}
