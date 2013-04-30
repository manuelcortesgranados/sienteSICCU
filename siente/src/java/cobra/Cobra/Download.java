/*
 * Download.java
 *
 * Created on 29-nov-2008, 21:49:27
 */
package cobra.Cobra;

import cobra.SessionBeanCobra;


import cobra.Supervisor.FacesUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>Page bean that corresponds to a similarly named JSP page. This class
 * contains component definitions (and initialization code) for all components
 * that you have defined on this page, as well as lifecycle methods and event
 * handlers where you may add behavior to respond to incoming events.</p>
 *
 * @author Jhon
 */
public class Download implements Serializable{
    // <editor-fold defaultstate="collapsed" desc="Managed Component Definition">

    /**
     * <p>Automatically managed component initialization.
     * <strong>WARNING:</strong>
     * This method is automatically generated, so any user-specified code
     * inserted here is subject to being replaced.</p>
     */
    private void _init() throws Exception {
    }

    // </editor-fold>
    /**
     * <p>Construct a new Page bean instance.</p>
     */
    public Download() {
    }

    public String onDownload() {

        // hard coded in this example. In a production environment, you
        // want to read the documentName location from a context parameter
        // in web.xml
        String URL_ARCHIVO = this.getSessionBeanCobra().getUrlAbri();


        FacesContext fctx = FacesContext.getCurrentInstance();
        // read documentName name from the request
        ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String nombreArchivo = null;
        int indexNombreArchivo = URL_ARCHIVO.lastIndexOf('/');
        if (indexNombreArchivo >= 0) {
            nombreArchivo = URL_ARCHIVO.substring(indexNombreArchivo + 1);
        }
        if (nombreArchivo != null) {
            String realPath = context.getRealPath(URL_ARCHIVO);
            File fileArchivo = new File(realPath);

            if (fileArchivo.exists()) {

                FileInputStream fis;
                byte[] b;

                HttpServletResponse response = (HttpServletResponse) fctx.getExternalContext().getResponse();
                // file should be downloaded without display
                response.setContentType("application/x-download");
                response.setHeader("Content-Disposition", "attachment; filename=\"" + nombreArchivo + "\"");
                response.setContentLength((new Long(fileArchivo.length())).intValue());
                OutputStream out;

                try {
                    out = response.getOutputStream();
                } catch (IOException e) {
                    // TODO
                    e.printStackTrace();
                    // no error message shown to the user. Consider adding a Faces
                    // Message
                    return null;
                }
                try {
                    fis = new FileInputStream(fileArchivo);

                    int n;
                    while ((n = fis.available()) > 0) {
                        b = new byte[n];
                        int result = fis.read(b);
                        out.write(b, 0, b.length);
                        if (result == -1) {
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    // TODO
                }
                fctx.responseComplete();
            }
        }

        return null;
    }

    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }
}
