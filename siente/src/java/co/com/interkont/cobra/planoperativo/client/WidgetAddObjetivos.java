/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client;

import co.com.interkont.cobra.planoperativo.client.dto.ActividadobraDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ObjetivosDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ObraDTO;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.box.MultiLinePromptMessageBox;
import com.sencha.gxt.widget.core.client.event.HideEvent;
import com.sencha.gxt.widget.core.client.form.TextArea;

/**
 *
 * @author Daniela
 */
public class WidgetAddObjetivos implements IsWidget {

    protected WidgetTablaObjetivos tblObjetivos;
    protected TextArea txtObjG;
    protected WidgetTablaMacro tblMacro;
    protected ObraDTO proyecto;
    protected String titulodialogo;
    protected String mensajedialogo;
    protected int tipoObjetivo;
    protected boolean esObjetivo;
    protected boolean esActividad;
    protected int idTemp;
    

    @Override
    public Widget asWidget() {
        return crearFormulario();
    }

    public WidgetAddObjetivos(WidgetTablaObjetivos tblObjetivos, ObraDTO proyecto, String titulodialogo, String mensajedialogo, int tipoObjetivo, boolean esObjetivo, int idTemp) {
        this.tblObjetivos = tblObjetivos;
        this.proyecto = proyecto;
        this.titulodialogo = titulodialogo;
        this.mensajedialogo = mensajedialogo;
        this.tipoObjetivo = tipoObjetivo;
        this.esObjetivo = esObjetivo;
        this.idTemp = idTemp;
       
    }
    
     public WidgetAddObjetivos(TextArea tblObjetivos, ObraDTO proyecto, String titulodialogo, String mensajedialogo, int tipoObjetivo, boolean esObjetivo, int idTemp) {
        this.txtObjG = tblObjetivos;
        this.proyecto = proyecto;
        this.titulodialogo = titulodialogo;
        this.mensajedialogo = mensajedialogo;
        this.tipoObjetivo = tipoObjetivo;
        this.esObjetivo = esObjetivo;
        this.idTemp = idTemp;
       
    }

    public WidgetAddObjetivos(WidgetTablaObjetivos tblObjetivos, ObraDTO proyecto, String titulodialogo, String mensajedialogo, boolean esObjetivo, int idTemp) {
        this.tblObjetivos = tblObjetivos;
        this.proyecto = proyecto;
        this.titulodialogo = titulodialogo;
        this.mensajedialogo = mensajedialogo;
        this.esObjetivo = esObjetivo;
        this.idTemp = idTemp;
    }

    public WidgetAddObjetivos(WidgetTablaMacro tblMacro, ObraDTO proyecto, String titulodialogo, String mensajedialogo, boolean esObjetivo, int idTemp, boolean esacti) {
        this.tblMacro = tblMacro;
        this.proyecto = proyecto;
        this.titulodialogo = titulodialogo;
        this.mensajedialogo = mensajedialogo;
        this.esObjetivo = esObjetivo;
        this.idTemp = idTemp;
        this.esActividad = esacti;
    }

    public MultiLinePromptMessageBox crearFormulario() {
        final MultiLinePromptMessageBox box = new MultiLinePromptMessageBox(titulodialogo, mensajedialogo);
        box.addHideHandler(new HideEvent.HideHandler() {
            @Override
            public void onHide(HideEvent event) {
                Dialog btn = (Dialog) event.getSource();
                if (btn.getHideButton().getText().compareTo("OK") == 0 && box.getValue() != null && box.getValue().compareTo("") != 0) {
                    if (!esActividad) {
                        ObjetivosDTO objetivos = new ObjetivosDTO(box.getValue(), tipoObjetivo, esObjetivo, idTemp);
                        idTemp++;
                        proyecto.getObjetivoses().add(objetivos);
                        if(objetivos.getTipoobjetivo()!=1){
                        tblObjetivos.getStore().add(objetivos);                        
                        }else{
                        txtObjG.setValue(objetivos.getDescripcion());
                        }
                    }else{
                        ActividadobraDTO actividad=new ActividadobraDTO(box.getValue(), 7,idTemp);
                        idTemp++;
                        proyecto.getActividadobras().add(actividad);
                        tblMacro.getStore().add(actividad);                        
                    }                    
                }
            }
        });

        return box;
    }
}
