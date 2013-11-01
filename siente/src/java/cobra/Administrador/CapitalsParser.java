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
import java.net.URL;
import java.util.List;

import javax.faces.FacesException;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@ManagedBean
@ApplicationScoped
public class CapitalsParser {

    private List<Obra> capitalsList;
    
    @XmlRootElement(name = "capitals")
    private static final class CapitalsHolder {
        
        private List<Obra> capitals;
        
        @XmlElement(name = "capital")
        public List<Obra> getCapitals() {
            return capitals;
        }
        
        @SuppressWarnings("unused")
        public void setCapitals(List<Obra> capitals) {
            this.capitals = capitals;
        }
    }
    
    public synchronized List<Obra> getCapitalsList() {
        if (capitalsList == null) {
            ClassLoader ccl = Thread.currentThread().getContextClassLoader();
            URL resource = ccl.getResource("org/richfaces/demo/data/capitals/capitals.xml");
            JAXBContext context;
            try {
                context = JAXBContext.newInstance(CapitalsHolder.class);
                CapitalsHolder capitalsHolder = (CapitalsHolder) context.createUnmarshaller().unmarshal(resource);
                capitalsList = capitalsHolder.getCapitals();
            } catch (JAXBException e) {
                throw new FacesException(e.getMessage(), e);
            }
        }
        
        return capitalsList;
    }
}