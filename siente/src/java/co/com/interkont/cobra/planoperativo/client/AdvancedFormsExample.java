/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client;

import co.com.interkont.cobra.planoperativo.client.dto.ContratoDTO;
import co.com.interkont.cobra.planoperativo.client.dto.FuenterecursosconvenioDTO;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.container.AbstractHtmlLayoutContainer.HtmlData;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAble;
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAbleAsync;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextBox;
import java.util.Iterator;

public class AdvancedFormsExample implements IsWidget, EntryPoint {

    private static final int COLUMN_FORM_WIDTH = 680;
    private VerticalPanel vp;
    private final CobraGwtServiceAbleAsync service = GWT.create(CobraGwtServiceAble.class);
    

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

        final TextBox nameField = new TextBox();
        nameField.setText("GWT User");
        RootPanel.get().add(nameField);
        nameField.setFocus(true);
        nameField.selectAll();

        final Button enviar = new Button("Enviar");

        FramedPanel panel = new FramedPanel();
        panel.setHeadingText("Ingresar Datos de Evento");
        panel.setWidth(COLUMN_FORM_WIDTH);

        HtmlLayoutContainer con = new HtmlLayoutContainer(getTableMarkup());
        panel.setWidget(con);

        int cw = (COLUMN_FORM_WIDTH / 2) - 12;

        final TextField evento = new TextField();
        evento.setAllowBlank(false);
        evento.setWidth(cw);
        con.add(new FieldLabel(evento, "nombre del evento"), new HtmlData(".ne"));

        TextField descripcion = new TextField();
        descripcion.setAllowBlank(false);
        descripcion.setWidth(cw);
        con.add(new FieldLabel(descripcion, "Descripcion del evento"), new HtmlData(".de"));

        DateField fechainicio = new DateField();
        fechainicio.setWidth(cw);
        con.add(new FieldLabel(fechainicio, "Fecha de inicio"), new HtmlData(".fi"));
        
          final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
                dialogVPanel.add(serverResponseLabel);
                dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);

        DateField fechafin = new DateField();
        fechafin.setWidth(cw);
        con.add(new FieldLabel(fechafin, "Fecha de fin"), new HtmlData(".ff"));
        con.add(enviar);
        final DialogBox dialogBox = new DialogBox();
        dialogBox.setText("Remote Procedure Call");
        con.add(dialogBox);

        class Controlador implements ClickHandler, KeyUpHandler {

            @Override
            public void onClick(ClickEvent event) {
                enviarAlServer();
            }

            @Override
            public void onKeyUp(KeyUpEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    enviarAlServer();
                }
            }

            private void enviarAlServer() {
                
                service.casteoContrato(new AsyncCallback<ContratoDTO>() {

                    @Override
                    public void onFailure(Throwable caught) {
                        
                        dialogBox.setText("sectir" + caught.toString());
                    }

                    @Override
                    public void onSuccess(ContratoDTO result) {
                        if (result != null) {

                            dialogBox.setText("sectir" + result.getFuenterecursosconvenios().size() );
                            StringBuilder semaf = new StringBuilder();
                            Iterator it = result.getFuenterecursosconvenios().iterator();
                            while (it.hasNext()) {
                                FuenterecursosconvenioDTO fuente = (FuenterecursosconvenioDTO) it.next();                
                                
                                semaf.append("idfuebnte o=");
                                semaf.append(+fuente.getIdfuenterecursosconvenio());
                              
                            }

                            dialogBox.setText(" lstSemaforo"+semaf);
////                            for (int i = 0; i < semaforos.size(); i++) {
////                                semaf.append(semaforos.get(i).getIntidsemaforo());
////
////                            }
//                            dialogBox.setText(""+semaforos.size());
                            serverResponseLabel.setHTML("id <br>Id : " + result.getIntidcontrato() + "<br> nombre:" + result.getStrnumcontrato());

                        } else {
                            serverResponseLabel.setHTML("No employee with the specified id found");
                        }
                        dialogBox.center();
                        dialogBox.setText("contrato" + result.getStrnumcontrato());
                        
                    }
                });


//                service.pruebacomGWTJSF(Integer.parseInt(evento.getText()), new AsyncCallback<Void>() {
//                    @Override
//                    public void onFailure(Throwable caught) {
//                        dialogBox.setText(caught.toString());
//                    }
//
//                    @Override
//                    public void onSuccess(Void result) {
//                        dialogBox.setText("Guarde");
//                    }
//                });
            }
            
            
        }

        Controlador contol = new Controlador();
        enviar.addClickHandler(contol);
        nameField.addKeyUpHandler(contol);
        vp.add(panel);
    }

    private native String getTableMarkup() /*-{
     return [ '<table width=100% cellpadding=0 cellspacing=0>',
     '<tr><td class=ne width=50%></td></tr>',
     '<tr><td class=de></td></tr>',
     '<tr><td class=fi></td></tr>',
     '<tr><td class=ff></td></tr>',
     '</table>' 
     ].join("");
     }-*/;
}