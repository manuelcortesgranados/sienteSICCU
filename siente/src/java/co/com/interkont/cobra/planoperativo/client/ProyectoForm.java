/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widget.client.TextButton;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.container.AbstractHtmlLayoutContainer.HtmlData;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;

public class ProyectoForm implements IsWidget, EntryPoint {

    private VerticalPanel vp;
    private TextField nombreProyectoTextField = new TextField();
    private TextField descripcion;
    private DateField fechainicio;
    private DateField fechafin;

    public TextField getNombreProyectoTextField() {
        return nombreProyectoTextField;
    }

    public void setNombreProyectoTextField(TextField nombreProyectoTextField) {
        this.nombreProyectoTextField = nombreProyectoTextField;
    }


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
        FramedPanel panel = new FramedPanel();
        panel.setHeadingText("Ingresar Datos del Proyecto");

        HtmlLayoutContainer con = new HtmlLayoutContainer(getTableMarkup());
        panel.setWidget(con);


        nombreProyectoTextField.setAllowBlank(false);
        con.add(new FieldLabel(nombreProyectoTextField, "nombre del proyecto"), new HtmlData(".ne"));

        descripcion = new TextField();
        descripcion.setAllowBlank(false);
        con.add(new FieldLabel(descripcion, "Descripcion del proyecto"), new HtmlData(".de"));

        fechainicio = new DateField();
        con.add(new FieldLabel(fechainicio, "Fecha de inicio"), new HtmlData(".fi"));

        fechafin = new DateField();
        con.add(new FieldLabel(fechafin, "Fecha de fin"), new HtmlData(".ff"));
        con.add(new TextButton("Guardar"), new HtmlData(".botones"));

        vp.add(panel);
    }
    
//     private void createTabForm() {
//         
//         
//     }
    
    private native String getTableMarkup() /*-{
    return [ '<table width=100% cellpadding=0 cellspacing=0>',

     '<tr><td class=ne width=50%></td></tr>',        '<tr><td class=de></td></tr>',
        '<tr><td class=fi></td></tr>',

     '<tr><td class=ff></td></tr>',
     '<tr><td class=botones></td></tr>',        '</table>'
    ].join("");
  }-*/;
}