/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client;

import co.com.interkont.cobra.planoperativo.client.resources.images.ExampleImages;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.box.MultiLinePromptMessageBox;
import com.sencha.gxt.widget.core.client.container.AbstractHtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.HideEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos Loaiza
 */
public class StringFlexTableUnaColumna extends FlexTable {

    FlexCellFormatter cellFormatter = this.getFlexCellFormatter();
    PushButton btnAdicionar = new PushButton(new Image(ExampleImages.INSTANCE.addbtnaddpry()), new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
            final MultiLinePromptMessageBox box = new MultiLinePromptMessageBox("Objetivos Específicos", "Por favor ingrese la descripción del objetivo:");

            box.addHideHandler(new HideEvent.HideHandler() {
                @Override
                public void onHide(HideEvent event) {
                    Dialog btn = (Dialog) event.getSource();
                    if (btn.getHideButton().getText().compareTo("OK") == 0 && box.getValue() != null && box.getValue().compareTo("") != 0) {
                        //String v = Format.ellipse(box.getValue(), 80);
                        //String msg = Format.substitute("{0}", new Params(v));
                        addRow(box.getValue());
                    }
                }
            });
            box.show();

//                if (!objetivoGeneral.getText().equals("General")) {
//                    ObjetivosDTO objetivosGeneral = new ObjetivosDTO(objetivoGeneral.getText(), 1, true);
//                    proyectoDTO.getObjetivoses().add(objetivosGeneral);
//                }
//                if (!objetivoEspecifico.getText().equals("Especifico")) {
//                    ObjetivosDTO objetivosEspecifico = new ObjetivosDTO(objetivoGeneral.getText(), 2, true);
//                    proyectoDTO.getObjetivoses().add(objetivosEspecifico);
//                }
//                limpiarObjetivos();
        }
    });

    PushButton btnRemover = new PushButton(new Image(ExampleImages.INSTANCE.delete()), new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {

            removeRow();
        }
    });

    public StringFlexTableUnaColumna(String width, int cellspacing, int cellpadding) {
        this.addStyleName("flexTable");
        this.setWidth(width); //"32em"
        this.setCellSpacing(cellspacing);
        this.setCellPadding(cellpadding);
        btnAdicionar.setWidth("" + 20);
        btnRemover.setWidth("" + 20);

        cellFormatter.setHorizontalAlignment(
                0, 0, HasHorizontalAlignment.ALIGN_LEFT);
        this.setHTML(0, 0, "<b>Objetivos Específicos:</b>");
        cellFormatter.setColSpan(0, 0, 2);

        VerticalPanel buttonPanel = new VerticalPanel();
        buttonPanel.setStyleName("flexTable-buttonPanel");
        this.setWidget(0, 0, buttonPanel);
        cellFormatter.setVerticalAlignment(0, 0,
                HasVerticalAlignment.ALIGN_TOP);

    }

    /**
     * Agregar una fila descriptiva a la tabla
     */
    public void addRow(String texto) {
        int numRows = this.getRowCount();
        this.setText(numRows, 0, texto);

    }

    /**
     * Remover la última fila de la tabla
     */
    public void removeRow() {
        int numRows = this.getRowCount();
        if (numRows > 1) {
            this.removeRow(numRows - 1);
            this.getFlexCellFormatter().setRowSpan(0, 1, numRows - 1);
        }

    }

    public List<String> obtenerDatosListaString() {
        int i = 0;
        List<String> datos = new ArrayList<String>();
        while (i < this.getRowCount()) {
            datos.add(this.getText(i, 0));
            i++;
        }
        return datos;

    }

    public HorizontalLayoutContainer obtenerTablaScrooll(String width, String height) {
        HorizontalLayoutContainer hl = new HorizontalLayoutContainer();
        VerticalPanel vp = new VerticalPanel();
        vp.add(btnAdicionar);
        vp.add(btnRemover);
         ScrollPanel scrollPanel = new ScrollPanel(this);
        scrollPanel.setSize(width, height);
        hl.add(scrollPanel);
        hl.add(vp);
        
       
        return hl;
    }

}
