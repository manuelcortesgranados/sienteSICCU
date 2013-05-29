package cobra;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author carlos
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.*;

public class GestionarXML implements Serializable {

    private Document doc;
    private Element raiz;

    public Element getRaiz() {
        raiz = doc.getRootElement();
        return raiz;
    }

    public Document getDoc() {
        return doc;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }

    public void cargarDocumento(String url) {
        try {
            // Creamos el builder basado en SAX  
            SAXBuilder builder = new SAXBuilder();
            // Construimos el arbol DOM a partir del fichero xml  
            doc = builder.build(new FileInputStream(url));
            //doc = builder.build(url);         
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Element> getHijos(Element raiz) {
        List<Element> hijosRaiz = raiz.getChildren();
        return hijosRaiz;
    }

    public boolean guardarDocumento(Document docNuevo, String url) {
        try {

            XMLOutputter out = new XMLOutputter();
            FileOutputStream file = new FileOutputStream(url);
            out.output(docNuevo, file);
            file.flush();
            file.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }


    }
}
