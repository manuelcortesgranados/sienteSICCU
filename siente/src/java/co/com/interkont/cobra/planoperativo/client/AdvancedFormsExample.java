/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client;




import co.com.interkont.cobra.planoperativo.client.dto.AlarmaDTO;
import co.com.interkont.cobra.planoperativo.client.dto.SemaforoDTO;
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
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtService;
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAsync;
import co.com.interkont.cobra.to.Sector;
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
 
    
    private final CobraGwtServiceAsync service= GWT.create(CobraGwtService.class);
    private Sector sec= new Sector();
    
    
     

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

        DateField fechafin = new DateField();
        fechafin.setWidth(cw);
        con.add(new FieldLabel(fechafin, "Fecha de fin"), new HtmlData(".ff"));
        con.add(enviar);
        final DialogBox dialogBox = new DialogBox();
        dialogBox.setText("Remote Procedure Call");
        con.add(dialogBox);
       
        
        final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
                dialogVPanel.add(serverResponseLabel);
                dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
                
       
        class Controlador implements ClickHandler,KeyUpHandler{
            

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
            
            private void enviarAlServer(){
                
                 String idsector =evento.getText();
                 
                 service.findAlarma(Integer.parseInt(idsector), new AsyncCallback<AlarmaDTO>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        dialogBox.setText("Remote Procedure Call - Failure" + caught.toString());
                    }

                    @Override
                    public void onSuccess(AlarmaDTO result) {

                        if (result != null) {

                            dialogBox.setText("sectir" + result.getIntidalarma() + "" + result.getSemaforos().size());
                            StringBuilder semaf = new StringBuilder();
                            Iterator it = result.getSemaforos().iterator();
                            while (it.hasNext()) {
                                SemaforoDTO sdto=(SemaforoDTO) it.next();
                                semaf.append("id semaforo=");
                                semaf.append(+sdto.getIntidsemaforo());
                              
                            }

                            dialogBox.setText(" lstSemaforo"+semaf);
////                            for (int i = 0; i < semaforos.size(); i++) {
////                                semaf.append(semaforos.get(i).getIntidsemaforo());
////
////                            }
//                            dialogBox.setText(""+semaforos.size());
                            serverResponseLabel.setHTML("id <br>Id : " + result.getIntidalarma() + "<br> nombre:" + result.getNumvalorini());

                        } else {
                            serverResponseLabel.setHTML("No employee with the specified id found");
                        }
                        dialogBox.center();
                    }
                });

//                 service.findContrato(Integer.parseInt(idsector), new AsyncCallback<Evento>() {
//
//                     @Override
//                     public void onFailure(Throwable caught) {
//                         dialogBox.setText("Remote Procedure Call - Failure"+caught.getCause());
//                     }
//
//                     @Override
//                     public void onSuccess(Evento result) {
//                          if(result!= null){
//                                dialogBox.setText("sectir" + result.getStrnombre());
//                                
//                                               serverResponseLabel.setHTML("id <br>Id : " + result.getStrnombre()+ "<br> nombre:"  );
////                                                                    
//                                                                }
//                                else{
//                                                                    serverResponseLabel.setHTML("No employee with the specified id found");
//                                                                }									
//								dialogBox.center();
//                     }
//                 });
//                 

//           
//                  service.findSector(Integer.parseInt(idsector), new AsyncCallback<Sector>() {
//
//                        @Override
//                        public void onFailure(Throwable caught) {
//                            dialogBox.setText("Remote Procedure Call - Failure"+caught.getCause());
//                        }
//
//                        @Override
//                        public void onSuccess(Sector result) {
//                            if(result!= null){
//                                dialogBox.setText("sectir" + result.getStrnombre());
//                                
//                                              serverResponseLabel.setHTML("id <br>Id : " + result.getIntsector()+ "<br> nombre:" + result.getStrnombre() );
////                                                                    
//                                                                }
//                                else{
//                                                                    serverResponseLabel.setHTML("No employee with the specified id found");
//                                                                }									
//								dialogBox.center();
////                                     
//                        }
//                    });
//  
                               
//                                    
//                                  service.hola(nameField.getText(), new  AsyncCallback<String>() {
//                                        @Override
//                                        public void onFailure(Throwable caught) {
//                                            dialogBox.setText(caught.toString());
//                                        }
//                    
//                         
        }}
        
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