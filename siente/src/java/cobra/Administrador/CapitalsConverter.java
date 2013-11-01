/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.Administrador;

/**
 *
 * @author desarrollo4
 */
import co.com.interkont.cobra.to.Obra;
import co.com.interkont.cobra.to.Tercero;
import javax.el.ELContext;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("CapitalsConverter")

public class CapitalsConverter implements Converter{

 private CapitalsParser capitalsParser;
 
    public Object getAsObject(FacesContext facesContext, UIComponent component, String s) {
        for (Obra capital : getCapitalsParser(facesContext).getCapitalsList()) {
            if (capital.getStrnombreobra().equals(s)) {
                return capital;
            }
        }
        return null;
    }
 
    public String getAsString(FacesContext facesContext, UIComponent component, Object o) {
        if (o == null) return null;
        return ((Obra) o).getStrnombreobra();
    }
 
    private CapitalsParser getCapitalsParser(FacesContext facesContext) {
        if (capitalsParser == null) {
            ELContext elContext = facesContext.getELContext();
            capitalsParser = (CapitalsParser) elContext.getELResolver().getValue(elContext, null, "capitalsParser");
        }
        return capitalsParser;
    }
}
